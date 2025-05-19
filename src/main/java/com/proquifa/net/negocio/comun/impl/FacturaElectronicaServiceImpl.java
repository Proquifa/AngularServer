package com.proquifa.net.negocio.comun.impl;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.proquifa.net.modelo.cobrosypagos.cfdi.FacturaTurboPac;
import com.proquifa.net.negocio.comun.CartaPorteService;
import org.apache.commons.ssl.Base64;
import org.apache.commons.ssl.PKCS8Key;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.*;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;

import com.proquifa.net.modelo.catalogos.cobrosypagos.facturista.Factura;
import com.proquifa.net.modelo.catalogos.cobrosypagos.facturista.PartidaFactura;
import com.proquifa.net.modelo.comun.Pendiente;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.facturaElectronica.CPDocRelacionado;
import com.proquifa.net.modelo.comun.facturaElectronica.CfdiRelacionado;
import com.proquifa.net.modelo.comun.facturaElectronica.ComplementoPago;
import com.proquifa.net.modelo.comun.facturaElectronica.FacturaElectronica;
import com.proquifa.net.modelo.comun.facturaElectronica.FileXML;
import com.proquifa.net.modelo.comun.facturaElectronica.PFacturaElectronica;
import com.proquifa.net.modelo.comun.facturaElectronica.RespuestaSap;
import com.proquifa.net.modelo.comun.facturaElectronica.addenda.Asofarma.AddendaAsofarma;
import com.proquifa.net.modelo.comun.facturaElectronica.addenda.Asofarma.AddendaAsofarmaPartida;
import com.proquifa.net.modelo.comun.facturaElectronica.addenda.Darier.AddendaDarier;
import com.proquifa.net.modelo.comun.facturaElectronica.addenda.Mavi.AddendaMavi;
import com.proquifa.net.modelo.comun.facturaElectronica.addenda.Pfizer.AddendaPfizer;
import com.proquifa.net.modelo.comun.facturaElectronica.addenda.Pfizer.AddendaPfizerLinea;
import com.proquifa.net.modelo.comun.facturaElectronica.addenda.Sanofi.AddendaSanofi;
import com.proquifa.net.modelo.comun.facturaElectronica.addenda.Sanofi.AddendaSanofiDetails;
import com.proquifa.net.modelo.comun.facturaElectronica.addenda.Sanofi.AddendaSanofiHeader;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.ventas.admoncomunicacion.Correo;
import com.proquifa.net.negocio.cobrosypagos.facturista.ComplementoPagoService;
import com.proquifa.net.negocio.cobrosypagos.facturista.UtilFacturaService;
import com.proquifa.net.negocio.comun.FacturaElectronicaService;
import com.proquifa.net.persistencia.cobrosypagos.facturista.FacturacionDAO;
import com.proquifa.net.persistencia.comun.ClienteDAO;
import com.proquifa.net.persistencia.comun.FolioDAO;
import com.proquifa.net.persistencia.comun.PendienteDAO;
import com.proquifa.net.persistencia.comun.facturacion.FacturacionElectronicaDAO;
import TurboPac.ITurboPacWs;
import TurboPac.RespuestaTimbrado;
import TurboPac.TurboPacWs;

@Service("facturaElectronicaServiceImpl")
public class FacturaElectronicaServiceImpl implements FacturaElectronicaService {
	@Autowired
	FolioDAO folioDAO;
	@Autowired
	FacturacionElectronicaDAO facturacionElectronicaDAO;
	@Autowired
	UtilFacturaService utilFacturaService;
	@Autowired
	ComplementoPagoService complementoPagoService;
	@Autowired
	CartaPorteService cartaPorteService;

	@Autowired
	ClienteDAO clienteDAO;

	@Autowired
	FacturacionDAO facturacionDAO;

	@Autowired
	PendienteDAO pendienteDAO;

	private Funcion funcion = new Funcion();

	private FileXML fileXML = new FileXML();

	String errorFactura = "";

	final Logger log = LoggerFactory.getLogger(FacturaElectronicaServiceImpl.class);

	@Override
	public Object insertarFactura(FacturaElectronica f, Boolean isSap, Boolean isFlex) {
		try {
			if(Funcion.FACTURACION_4) {
				f.setVersion("4.0");
			} else {
				f.setVersion("3.3");
			}

			Date hoy = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			log.info("Insertar FacturaElectronica" + dateFormat.format(hoy));
			f.setFecha(dateFormat.format(hoy));
			f.setTotalTexto(funcion.ConvertirNumerosALetras(f.getTotal(), f.getMoneda()));

			String rfcInternacional = facturacionElectronicaDAO.validarClienteInternacional(f);

			if (rfcInternacional != null && !rfcInternacional.equals("")) {
				f.getCliente().setRfc(rfcInternacional);
			}

			if (isFlex) {
				String folio = obtenerFolio(f.getEmpresa().getAlias(), f.getTipoComprobante());
				f.setFolio(folio);
			}

			f = facturacionElectronicaDAO.insertarFactura(f);

			for (PFacturaElectronica pf : f.getLstConceptos()) {
				pf.setFactura(f.getIdFactura());
				pf = facturacionElectronicaDAO.insertarPFactura(pf);
			}

			if (f.getLstCfdiRel() != null && f.getLstCfdiRel().size() > 0) {
				for (CfdiRelacionado cr : f.getLstCfdiRel()) {
					cr.setIdFacturaElectronica(new Long(f.getIdFactura()));
					cr = facturacionElectronicaDAO.insertarCfdiRelacionado(cr);
				}
			}

			if (f.getTipoComprobante().equals("P")) {
				for (ComplementoPago cp : f.getLstComplementoPago()) {
					cp.setIdFactura(f.getIdFactura());
					cp = facturacionElectronicaDAO.insertarComplementoPago(cp);

					for (CPDocRelacionado cpdr : cp.getLstDoctRelacionados()) {
						cpdr.setIdComplementoPago(cp.getIdComplementoPago());
						cpdr = facturacionElectronicaDAO.insertarCPDocRelacionado(cpdr);
					}

				}
			}

			if (isFlex) {
				return f;
			}
			//f.getMoneda().equals("USD") & f.getTipoCambio()
			if (!facturar(f, isSap).equals("Error")) {
				return f.getIdFactura();
			} else {
				facturacionElectronicaDAO.updateErrorFactura(f.getIdFactura());
				return -f.getIdFactura();
			}

			//else return valor que indica que la factura es USD y tiene tipo de cambio 1.0
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int timbrarFactura(FacturaElectronica f) {
		try {
			if (!facturar(f, true).equals("Error")) {
				return f.getIdFactura();
			} else {
				facturacionElectronicaDAO.updateErrorFactura(f.getIdFactura());
				return -f.getIdFactura();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public String facturar(FacturaElectronica facturaElectronica, Boolean isSap) {
		String xmlString=null;

		try {
			String estadoFactura = null;
			FacturaElectronica f;
			log.info("isSAP: " + isSap );
			if (facturaElectronica == null) {
				f = facturacionElectronicaDAO.obtenerFacturaElectronica();
				log.info("facturaElectronica is null " + f.getIdFactura());
			} else {
				f = facturaElectronica;
				log.info("f.idFactura: " + f.getIdFactura());
			}

			log.info("Empresa: "+ f.getEmpresa().getAlias() + " folio: "+ f.getFolio() );

			if (f.getFolio() == null || f.getFolio().equals("")) {
				String folio = obtenerFolio(f.getEmpresa().getAlias(), f.getTipoComprobante());
				log.info(folio);
				if (folio.equals("bloqued")) {
					facturacionElectronicaDAO.insertarErrorFactura("Bloqueado", f.getIdFactura());
					return "Error";
				}
				/*
				 * while (folio.equals("")) { folio = obtenerFolio(f.getEmpresa().getAlias(),
				 * f.getTipoComprobante()); }
				 */

				f.setFolio(folio);
			}
			DecimalFormat df2 = new DecimalFormat("0.00");

			if (f.getTipoComprobante().equals("I")) {
				f.setSerie("A");
			} else if (f.getTipoComprobante().equals("P")) {
				f.setSerie("P");
			} else {
				f.setSerie("B");
			}

			Date hoy = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			f.setFecha(dateFormat.format(hoy));

			String ruta = Funcion.RUTA_DOCUMENTOS + "Keys/Timbrado/FIRMAS ELECTRONICAS/";
			String rutaCer = "";
			String rutaKey = "";
			String p4ss = "";
			if(!Funcion.PRODUCCION) {
				rutaCer = ruta
						+ "Prueba/CSD_Escuela_Kemper_Urgate_EKU9003173C9_20190617_131753s.cer";
				rutaKey = ruta
						+ "Prueba/CSD_Escuela_Kemper_Urgate_EKU9003173C9_20190617_131753.key";
				p4ss = "12345678a";
			} else{
				System.out.println("Empresa: "+f.getEmpresa().getAlias());
				switch (f.getEmpresa().getAlias()) {
					case "Proveedora":
						rutaCer = ruta
								+ "Proveedora/CSD_PROVEEDORA_QUIMICO_FARMACEUTICA,_S.A._DE_C.V._PQF910416FB3_20170816_152418s.cer";
						rutaKey = ruta
								+ "Proveedora/CSD_PROVEEDORA_QUIMICO_FARMACEUTICA,_S.A._DE_C.V._PQF910416FB3_20170816_152418.key";
						p4ss = "gamaleon";
						break;
					case "Golocaer":
						rutaCer = ruta + "Golocaer/CSD_golocaer_GOL120717DJ7_20160901_085456s.cer";
						rutaKey = ruta + "Golocaer/CSD_golocaer_GOL120717DJ7_20160901_085456.key";
						p4ss = "cheyenes1";
						break;
					case "Mungen":
						rutaCer = ruta + "Mungen/00001000000403543617.cer";
						rutaKey = ruta + "Mungen/CSD_mungen_MUN1204264I5_20160831_182500.key";
						p4ss = "cheyenes1";
						break;
					case "Proquifa":
						rutaCer = ruta + "Proquifa/CSD_PROQUIFA,_S.A._DE_C.V._PRO970821ML3_20170822_122829s.cer";
						rutaKey = ruta + "Proquifa/CSD_PROQUIFA,_S.A._DE_C.V._PRO970821ML3_20170822_122829.key";
						p4ss = "cheyenes1";
						break;
					case "Ryndem":
						rutaCer = ruta + "Ryndem/CSD_RYNDEM_RYN120426HH6_20160831_181748s.cer";
						rutaKey = ruta + "Ryndem/CSD_RYNDEM_RYN120426HH6_20160831_181748.key";
						p4ss = "cheyenes1";
						break;
					case "ProquifaServicios":
						rutaCer = ruta + "Proquifa Servicios/CSD_Unidad_PSE090227966_20160524_152109s.cer";
						rutaKey = ruta + "Proquifa Servicios/CSD_Unidad_PSE090227966_20160524_152109.key";
						p4ss = "GAMALEON3";
						break;
				}
			}

			InputStream is = new FileInputStream(rutaCer);
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			X509Certificate noPREcertificado = (X509Certificate) cf.generateCertificate(is);
			byte[] byteArray = noPREcertificado.getSerialNumber().toByteArray();
			f.setNoCertificado(new String(byteArray));
			File file = new File(rutaCer);
			FileInputStream fileInputStream = new FileInputStream(file);
			byte[] fileBytes = new byte[fileInputStream.available()];
			fileInputStream.read(fileBytes);
			fileInputStream.close();
			f.setCertificado(DatatypeConverter.printBase64Binary(fileBytes));
			// String condicionesPago = (e.getPedido().getCliente().getCredito() != null) ?
			// e.getPedido().getCliente().getCredito().getDiasCredito() + " días" :
			// "Contado" ;

			String subtotalTXT = "";
			String totalTXT = "";

			if (!f.getTipoComprobante().equals("P")) {
				subtotalTXT = df2.format(Double.parseDouble(f.getSubtotal()));
				/*if(f.getTotal().equals("154018.93")){
					totalTXT =df2.format(Double.parseDouble("154018.94"));
				} else {
					totalTXT = df2.format(Double.parseDouble(f.getTotal()));
				}*/
				totalTXT = df2.format(Double.parseDouble(f.getTotal()));
			} else {
				subtotalTXT = "0";
				totalTXT = "0";
			}

			/* Armado de CFDI Nativo SAT */
			log.info("--------------------------------Generar XML--------------------------------");
			Document cfdi= fileXML.getCfdi();
			if(f.getTipoComprobante().equals("T")){
			cfdi=cartaPorteService.generarXMLCartaPorte(f);
			}
			else{
				cfdi = fileXML.getCfdi();
				Element comprobante = fileXML.crearComprobante(f.getVersion(), f.getSerie(), f.getFolio(), f.getFecha(),
						f.getFormaPago(), f.getNoCertificado(), f.getCertificado(), f.getCondicionesPago(), subtotalTXT,
  						f.getMoneda(), f.getTipoCambio(), totalTXT, f.getTipoComprobante(), f.getMetodoPago()       ,
						f.getLugarExpedicion());

				if (f.getLstCfdiRel() != null && f.getLstCfdiRel().size() > 0) {
					Element cfdRelacionados = fileXML.crearCfdiRelacionados(f.getLstCfdiRel().get(0).getTipoRelacion());
					for (CfdiRelacionado cr : f.getLstCfdiRel()) {
						Element cfdRelacionado = fileXML.crearCfdiRelacionado(cr.getUuid());
						cfdRelacionados.appendChild(cfdRelacionado);
					}
					comprobante.appendChild(cfdRelacionados);
				}
				//VARIABLE PARA CAMBIAR EL NOMBRE DEL EMISOR//
				String razonSocialE = f.getEmpresa().getRazonSocial();
				if (Funcion.FACTURACION_4) {
					// Validación sin expresion regular
					String[] resultRazonS = f.getEmpresa().getRazonSocial().split("S.A. DE C.V.");
					if (resultRazonS.length > 0) {
						System.out.println(resultRazonS[0].trim());
						razonSocialE = resultRazonS[0].trim();
					}
					// VERIFICAR SI COINCIDE CON LA EXPRESIÓN REGULAR
					if (f.getEmpresa().getRazonSocial().matches("(\\.|\\,|\\s)+[SAPIRLCV][.,]?\\s*([SAPIRLCV][.,]?\\s*|\\s*)\\s*([SDAPIRLCV][.,]?\\s*[SAPIRLCV]?[.,]?\\s*)?(DE\\s*[SAPIRLCV][.,]?\\s*[SAPIRLCV][.,]?\\s*(DE\\s*[SAPIRLCV][.,]?\\s*[SAPIRLCV][.,]?\\s*|))*$")) {
						log.info("Nombre Emisor Incorrecto: " + f.getEmpresa().getRazonSocial());
					}
				}
				/////
				log.info("Emisor: " + f.getEmpresa().getRfcEmpresa() + " " + razonSocialE + " "
						+ f.getEmpresa().getRegimenFiscal());
			/*Element emisor = fileXML.crearEmisor(f.getEmpresa().getRfcEmpresa(), f.getEmpresa().getRazonSocial(),
					f.getEmpresa().getRegimenFiscal());*/ // Se comento para cambiar el nombre de la razón social por el nuevo formato
			/*Element emisor = fileXML.crearEmisor(f.getEmpresa().getRfcEmpresa(), razonSocialE,
					f.getEmpresa().getRegimenFiscal());*/
				// EL SIGUIENTE ES PARA PRUEBAS, EN ANTERIOR COMENTADO ES EL QUE QUEDARA
				if (Funcion.PRODUCCION) {
					if (f.getCliente().getRfc().equals("XAXX010101000")) {
						Element infoGlogal = fileXML.crearInformacionGlobal();
						comprobante.appendChild(infoGlogal);
					}
					Element emisor = fileXML.crearEmisor(f.getEmpresa().getRfcEmpresa(), f.getEmpresa().getRazonSocial(),
							f.getEmpresa().getRegimenFiscal());
					comprobante.appendChild(emisor);
				} else {
					Element emisor = fileXML.crearEmisor("EKU9003173C9", "ESCUELA KEMPER URGATE",
							"601");
					comprobante.appendChild(emisor);
				}

				log.info("Receptor: " + f.getCliente().getRfc() + " " + f.getCliente().getRazonSocial() + " "
						+ f.getCliente().getUsoCFDI());
				// Obtener nuevos datos para Facturación 4.0
				// VERIFICAR SI COINCIDE CON LA EXPRESIÓN REGULAR
				if (f.getCliente().getRazonSocial().matches("(\\.|\\,|\\s)+[SAPIRLCV][.,]?\\s*([SAPIRLCV][.,]?\\s*|\\s*)\\s*([SDAPIRLCV][.,]?\\s*[SAPIRLCV]?[.,]?\\s*)?(DE\\s*[SAPIRLCV][.,]?\\s*[SAPIRLCV][.,]?\\s*(DE\\s*[SAPIRLCV][.,]?\\s*[SAPIRLCV][.,]?\\s*|))*$")) {
					log.info("Nombre Emisor Incorrecto: " + f.getCliente().getRazonSocial());
				}
				String rfc = f.getCliente().getRfc();
				System.out.println("codigo Postal" + rfc);
				String cpReceptor = facturacionElectronicaDAO.obtenerCPCliente(rfc);
				log.info("CP: " + cpReceptor);
				String nameReceptor = facturacionElectronicaDAO.getNameCliente(f.getCliente().getRfc()).replaceAll("\\?", "Ñ");
				log.info("#Cliente: ", f.getCliente().getIdCliente());


				log.info("Nombre Cliente: " + nameReceptor);
				String regimenF = facturacionElectronicaDAO.getRegimenF(f.getCliente().getRfc());
				log.info("Uso CFDI Cliente: " + facturacionElectronicaDAO.obtnerUsoCFDICliente(f.getCliente().getRfc()));
				String usoCFDI = facturacionElectronicaDAO.obtnerUsoCFDICliente(f.getCliente().getRfc());
				//
			/*Element receptor = fileXML.crearReceptor(f.getCliente().getRfc(), f.getCliente().getRazonSocial(),
					f.getCliente().getUsoCFDI(),cpReceptor, regimenF);*/
			/*Element receptor = fileXML.crearReceptor(f.getCliente().getRfc(), nameReceptor,
					f.getCliente().getUsoCFDI(),cpReceptor, regimenF);*/
				// Receptor nuevo de prueba, la línea anterior es la que se dejara en productivo.
			/*Element receptor = fileXML.crearReceptor(f.getCliente().getRfc(), nameReceptor,
					f.getCliente().getUsoCFDI(),cpReceptor, regimenF);*/
				Element receptor = null;
				if (!f.getTipoComprobante().equals("P")) {
					receptor = fileXML.crearReceptor(f.getCliente().getRfc(), nameReceptor,
							//f.getCliente().getUsoCFDI()
							usoCFDI
							, cpReceptor, regimenF);
				} else {
					receptor = fileXML.crearReceptor(f.getCliente().getRfc(), nameReceptor,
							"CP01", cpReceptor, regimenF);
				}

				comprobante.appendChild(receptor);

				Element conceptos = fileXML.crearConceptos();
				// Se agrego la siguiente linea para asignar e valor a objectImport
				String objectImport = f.getEmpresa().getRegimenFiscal().equalsIgnoreCase("GOL120717DJ7") ? "01" : "02";
				//
				for (PFacturaElectronica item : f.getLstConceptos()) {
					// Se agrego al nodo crearConcepto el atributo objectImport
					Element concepto = fileXML.crearConcepto(item, objectImport, f.getTipoComprobante());
					if (!f.getTipoComprobante().equals("P") ) {
						Element impuestos = fileXML.crearImpuestos();
						Element traslados = fileXML.crearTraslados();
						Element traslado = fileXML.crearTraslado(item.getImpuestoBase(), item.getImpuestoClave(),
								item.getImpuestoTipoFactor(), item.getImpuestoTasaOCuota(), item.getImpuestoImporte());
						traslados.appendChild(traslado);
						impuestos.appendChild(traslados);
						concepto.appendChild(impuestos);
						if (item.getAdnNumeroPedimento() != null && !item.getAdnNumeroPedimento().equals("")) {
							Element infoAduanera = fileXML.crearInformacionAduanera(item.getAdnNumeroPedimento());
							concepto.appendChild(infoAduanera);
						}
					}
					conceptos.appendChild(concepto);
				}

				comprobante.appendChild(conceptos);

				if (!f.getTipoComprobante().equals("P")) {
					Element impuestos = fileXML.crearImpuestos(f.getImpuestosTotalTraslados(), "");
					Element traslados = fileXML.crearTraslados();
					Element traslado = fileXML.crearTraslado(f.getSubtotal(), f.getImpuestosClave(), f.getImpuestosTipoFactor(),
							f.getImpuestosTasaOCuota(), f.getImpuestosImporte()); // Se cambio agregando el subtotal para la base, se sustituyo por la siguiente linea comentada.
				/*Element traslado = fileXML.crearTraslado("", f.getImpuestosClave(), f.getImpuestosTipoFactor(),
						f.getImpuestosTasaOCuota(), f.getImpuestosImporte());*/
					traslados.appendChild(traslado);
					impuestos.appendChild(traslados);
					comprobante.appendChild(impuestos);
				}
				String base = null;
				String cambioDR = null;
				String montoTotales = null;
				String monedaTotales = null;
				String tipoCambio = null;
				String aplicaImpuestos = null;
				String tipoMonedaP = null;
				String tipoMonedaDR = null;
				String tipoCambioP = null;
				String impuesto= null;
				List<String> baseP = new ArrayList<>();
				String folios ="";
				Boolean aplica=false;
				if (f.getTipoComprobante().equals("P")) {
					Element complemento = fileXML.crearComplemento();
					Element pagos = fileXML.crearPagos();
					for (ComplementoPago item : f.getLstComplementoPago()) {
						Element pago = fileXML.crearPago(item);

						for (CPDocRelacionado item2 : item.getLstDoctRelacionados()) {
							folios += item2.getIdDocumento()+",";
						}
						for (CPDocRelacionado item2 : item.getLstDoctRelacionados()) {
							log.info("Folio Docto Rel:" + item2.getFolio());
							log.info("tipoCambioPago:" + item.getTipoCambioP());
							log.info("moneda pago:" + item.getMonedaP());

							System.out.println("Folios:" + folios);
							aplica=facturacionElectronicaDAO.impuestos(folios).stream().anyMatch(fe -> fe.equals("0.160000"));
							System.out.println("aplica:"+ aplica);
							aplicaImpuestos = facturacionElectronicaDAO.aplicaImpuestos(item2.getIdDocumento());
							cambioDR = String.valueOf(facturacionElectronicaDAO.obtenertipoCambioDR(item2.getFolio()));

							Element docRel = fileXML.crearDocumentoRelacionado(item2, item.getMonedaP(), cambioDR, aplicaImpuestos);

							log.info("Nuevos nodos para complemento2.0");
							tipoMonedaP = item.getMonedaP();
							tipoMonedaDR = item2.getMonedaDR();
							log.info("aplica imp:" + aplicaImpuestos);
							impuesto=facturacionElectronicaDAO.getImpuestoClave(item2.getIdDocumento());
							if (aplicaImpuestos.equals("1")) {
								log.info("aplica impuesto: nodo impuestosDR");
								base = String.valueOf(facturacionElectronicaDAO.obtenerSubtotalFactura(item2.getIdDocumento()));
								log.info("Datos nodo impuestos DR: " + base + " " + item.getMonedaP() + " " + item2.getTipoCambioP() + " " + item2.getMonedaDR());
								Element impuestos = fileXML.crearImpuestosDR(base, item.getMonedaP(), item.getTipoCambioP(), item2.getMonedaDR(),impuesto,aplica);
								docRel.appendChild(impuestos);
								//baseP.add(base);
							}
							pago.appendChild(docRel);
							tipoCambioP = item2.getTipoCambioP();

						}
						if (aplicaImpuestos.equals("1")) {
							log.info("aplica impuesto: nodo impuestosP");
							Element impuestosP = fileXML.crearImpuestosP(tipoMonedaP, tipoMonedaDR, impuesto,aplica);

							pago.appendChild(impuestosP);
						}
						montoTotales = item.getMonto();
						monedaTotales = item.getMonedaP();

						Element totales = fileXML.creartotales(montoTotales, monedaTotales, tipoCambioP, aplicaImpuestos,impuesto,aplica);
						pagos.appendChild(totales);

						pagos.appendChild(pago);
					}

					log.info("Termina de agregar los nuevos nodos al xml");
					complemento.appendChild(pagos);
					comprobante.appendChild(complemento);
				}

				cfdi.appendChild(comprobante);
			}
			/* Fin Armado de CFDI SAT SIN TIMBRAR */

			File xslt = new File(
					Funcion.RUTA_DOCUMENTOS + "Keys/Timbrado/cadenaoriginal-3_3xslt/cadenaoriginal_3_3.xslt");
			if(Funcion.FACTURACION_4) {
				xslt = new File(
						Funcion.RUTA_DOCUMENTOS + "Keys/Timbrado/cadenaoriginal-4_0xslt/cadenaoriginal_4_0.xslt");
			}
			StreamSource sourceXSL = new StreamSource(xslt);
			DOMSource source = new DOMSource(cfdi);
			String rutaFacturas = "";
			if (f.getTipoComprobante().equals("I")) {
				rutaFacturas = funcion.obtenerRutaServidor("factura", f.getEmpresa().getAlias().replaceAll(" ", ""));
			} else if (f.getTipoComprobante().equals("P")) {
				rutaFacturas = funcion.obtenerRutaServidor("complementoPago",
						f.getEmpresa().getAlias().replaceAll(" ", ""));
			} else if (f.getTipoComprobante().equals("E")) {
				rutaFacturas = funcion.obtenerRutaServidor("notacredito",
						f.getEmpresa().getAlias().replaceAll(" ", ""));
			} else if (f.getTipoComprobante().equals("T")) {
				rutaFacturas= funcion.obtenerRutaServidor("cartaporte","");

			}

			File fileAux = new File(rutaFacturas);
			if (!fileAux.exists())
				fileAux.mkdirs();

			File tempFile = new File(rutaFacturas + f.getFolio() + ".xml");
			FileWriter writer = new FileWriter(tempFile);
			StreamResult sourceXML = new StreamResult(writer);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			transformer.transform(source, sourceXML);

			// CFDI Temporal
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			StreamResult cadenaOriginal = new StreamResult(bos);
			File xmlFile = new File(rutaFacturas + f.getFolio() + ".xml");
			StreamSource sourceXML2 = new StreamSource(xmlFile);
			Transformer transformer2 = TransformerFactory.newInstance().newTransformer(sourceXSL);
			transformer2.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer2.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer2.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			transformer2.transform(sourceXML2, cadenaOriginal);
			File keyFile = new File(rutaKey);
			InputStream keyFileInput = new FileInputStream(keyFile);
			PKCS8Key pkcs8 = new PKCS8Key(keyFileInput, p4ss.toCharArray());
			KeyFactory privateKeyFactory = KeyFactory.getInstance("RSA");
			PKCS8EncodedKeySpec pkcs8Encoded = new PKCS8EncodedKeySpec(pkcs8.getDecryptedBytes());
			PrivateKey privateKey = privateKeyFactory.generatePrivate(pkcs8Encoded);
			Signature signature = Signature.getInstance("SHA256withRSA");
			signature.initSign(privateKey);
			// Se limpia la cadena para la cadena original
			String bosAux = bos.toString().replace("\r","").replace("\n","").replace("\t","").trim();
			// byte[] cadenaOriginalArray = bos.toByteArray();
			byte[] cadenaOriginalArray = bosAux.getBytes("UTF-8"); // Nueva validacion para limpiar la cadena original
			signature.update(cadenaOriginalArray);
			String firma = new String(Base64.encodeBase64(signature.sign()), "UTF-8");
			log.info(
					"---------------------------------------------------- Inicio Agregar Factura ----------------------------------------------------");
			log.info("------------------ Cadena Original: " + bos.toString());
			log.info("---------Firma: " + firma);
			f.setSello(firma);
			f.setCadenaOriginal(bos.toString().replace("\r","").replace("\n","").replace("\t","").trim());
			f.setEstado("Por Timbrar");
			/*Prueba de generar Sello valido*/
			/**/
			xmlString = convertirString(cfdi);
			xmlString = xmlString.replaceAll("Sin Sellar", firma);
			f.setXml(xmlString);

			if (f.getIdFactura() > 0) {
				// consecutivosDAO.updateConsecutivos("Factura", folioNuevo);
				f = facturacionElectronicaDAO.modificarFactura(f);
				for (PFacturaElectronica pf :  f.getLstConceptos()) {
					pf.setFactura(f.getIdFactura());
					// pf = facturaDAO.addPFactura(pf);
				}
				estadoFactura = "Factura Guardada/Por Facturar en Base de datos id: " + f.getIdFactura();
			} else {
				estadoFactura = "Error al agregar factura";
			}

			if (facturaElectronica != null) {
				if (timbrarFactura(f, !isSap)) {
					estadoFactura = "Timbrada";
				} else {
					estadoFactura = "Error";
				}
			} else {
				if (timbrarFactura(f, false)) {
					estadoFactura = "Timbrada";
				} else {
					estadoFactura = "Error";
				}
			}

			return estadoFactura;
		} catch (Exception e) {

			e.printStackTrace();
			Correo correo=new Correo();
			correo.setCorreo("acerezo@proquifa.net;sara.sanchez@ryndem.mx");
			correo.setOrigen("ventas");
			correo.setAsunto("Error en timbrado");
			correo.setCuerpoCorreo(xmlString+"\n"+"error:"+e);
			correo.setTipo("");

			funcion = new Funcion();
		//	funcion.enviarCorreo(correo);

			return "Error al facturar";
		}
	}

	@Transactional(rollbackFor = { Exception.class })
	public boolean timbrarFactura(FacturaElectronica f, Boolean relacionar) throws Exception {
		try {
			// Util util = new Util();
			String estadoFactura = "Por Timbrar";
			log.info("--------------------------------Envio a Timbrar--------------------------------");
			RespuestaTimbrado r = null;
			FacturaTurboPac res = new FacturaTurboPac();
			if(true){
				res = timbrarCFDI40(f.getXml(),f.getTipoComprobante());
			} else {
				r = timbrarFactura33(f.getXml());
			}
			// String respCFID = r.getCfdi().getValue();
			String respCFID = res.getXMLTimbrado();

			// String resTimbre = r.getCadenaTimbre().getValue();
			String resTimbre = "";
			// Boolean resValido = r.isValido();
			Boolean resValido = true;//r.isValido();
			if (respCFID == null || res.getIncidencias() > 0) {
				resValido = false;
			}
			log.info("--------------------------------TurboPAC--------------------------------");
			log.info("--------------------------------respCFID--------------------------------");
			log.info(respCFID);
			log.info("--------------------------------resTimbre--------------------------------");
			log.info(resTimbre);
			log.info("--------------------------------resValido--------------------------------");
			log.info("",resValido);

			/* Addenda */
			Document document;
			DOMImplementationLS domImplLS;
			LSSerializer serializer;
			Element addenda = fileXML.crearAddenda();
			Formatter fmt = new Formatter();
			String addendaTxt = "";
			if (f.getTipoComprobante().equals("I")) {
				switch (f.getCliente().getIdCliente().toString()) {
					case "14":
						AddendaAsofarma asofarma = new AddendaAsofarma();
						asofarma.setTipoProveedor("2");
						if (f.getEmpresa().getAlias().equals("Proveedora")) {
							asofarma.setNoProveedor("220476");
						} else if (f.getEmpresa().getAlias().equals("Golocaer")) {
							asofarma.setNoProveedor("221961");
						}

						asofarma.setSerie("A");
						asofarma.setFolio(f.getFolio());
						asofarma.setOrdenCompra(f.getPedido());
						asofarma.setPartidas(new ArrayList<AddendaAsofarmaPartida>());

						for (int i = 0; i < f.getLstConceptos().size(); i++) {
							AddendaAsofarmaPartida asofarmaPartida = new AddendaAsofarmaPartida();
							asofarmaPartida.setNoPartida(f.getLstConceptos().get(i).getLineaDeOrden());
							asofarmaPartida.setIvaAcreditable(f.getLstConceptos().get(i).getImpuestoImporte().substring(0,
									(f.getLstConceptos().get(i).getImpuestoImporte().indexOf('.') + 3)));
							asofarmaPartida.setIvaDevengado("0.00");
							asofarmaPartida.setOtros("0");

							asofarma.getPartidas().add(asofarmaPartida);
						}

						Element addendaAsofarma = fileXML.crearAsonioscoc(asofarma);
						addenda.appendChild(addendaAsofarma);

						document = addenda.getOwnerDocument();
						domImplLS = (DOMImplementationLS) document.getImplementation();
						serializer = domImplLS.createLSSerializer();
						addendaTxt = serializer.writeToString(addenda);
						addendaTxt = addendaTxt.replace("<?xml version=\"1.0\" encoding=\"UTF-16\"?>\n", "");
						break;
					case "39":
						Element addendaDarier = fileXML.crearAddenda();
						AddendaDarier darier = new AddendaDarier();
						break;
					case "78":
						AddendaMavi mavi = new AddendaMavi();
						mavi.setRfcProveedor(f.getEmpresa().getRfcEmpresa());
						mavi.setNumProveedor("704");
						mavi.setFechaFacturacion(f.getFecha().substring(0, f.getFecha().indexOf('T')));
						mavi.setNumPedido(f.getPedido());
						mavi.setCodMoneda(f.getMoneda());
						mavi.setMontoTotal(f.getTotal());
						mavi.setIva(f.getImpuestosImporte());
						if (f.getImpuestosImporte().equals("0.00")) {
							mavi.setPorcentajeIVA("0.16");
						} else {
							mavi.setPorcentajeIVA("0.00");
						}
						mavi.setNumFactura(f.getSerie() + f.getFolio());
						mavi.setSerie(f.getSerie());
						mavi.setFolio(f.getFolio());
						Element addendaMavi = fileXML.crearMavi(mavi);
						addenda.appendChild(addendaMavi);

						document = addenda.getOwnerDocument();
						domImplLS = (DOMImplementationLS) document.getImplementation();
						serializer = domImplLS.createLSSerializer();
						addendaTxt = serializer.writeToString(addenda);
						addendaTxt = addendaTxt.replace("<?xml version=\"1.0\" encoding=\"UTF-16\"?>\n", "");
						// comprobante.appendChild( addenda );
						break;
					case "106":  //SANOFI
					case "1754": //SANOFI PASTEUR
					case "4277": //AZTECA VACUNAS
						AddendaSanofi sanofi = new AddendaSanofi();
						sanofi.setHeader(new AddendaSanofiHeader());
						sanofi.setLstDetails(new ArrayList<AddendaSanofiDetails>());
						sanofi.getHeader().setTipoDocto("01");
						sanofi.getHeader().setNumOrden(f.getPedido());
						sanofi.getHeader().setNumProveedor("0001050470");
						sanofi.getHeader().setFctconv("1.000");
						sanofi.getHeader().setMoneda(f.getMoneda());
						sanofi.getHeader().setCtaCorreo("credito@proquifa.com.mx");
						sanofi.getHeader().setImpRetencion("");
						sanofi.getHeader().setImpTotal(f.getTotal());
						sanofi.getHeader().setDisponible1("0.00");
						sanofi.getHeader().setDisponible2("0.00");
						sanofi.getHeader().setDisponible3("0.00");
						sanofi.getHeader().setDisponible4("0.00");

						if (f.getCorreoAddenda() != null && !f.getCorreoAddenda().equals("")) {
							sanofi.getHeader().setCorreoSanofi(f.getCorreoAddenda());
						} else {
							sanofi.getHeader().setCorreoSanofi("Paola.Espinoza@sanofi.com");
						}

						for (int i = 0; i < f.getLstConceptos().size(); i++) {
							fmt = new Formatter();
							AddendaSanofiDetails sanofiDetail = new AddendaSanofiDetails();
							sanofiDetail.setNumLinea(fmt.format("%04d", f.getLstConceptos().get(i).getPart()) + "");
							sanofiDetail.setNumEntrada("0000000000");
							sanofiDetail.setCuentaPuente("0000000000");
							sanofiDetail.setUnidades(f.getLstConceptos().get(i).getCantidad() + "00");
							sanofiDetail.setPrecioUnitario(f.getLstConceptos().get(i).getValorUnitario());
							sanofiDetail.setImporte(f.getLstConceptos().get(i).getImporte());
							sanofiDetail.setUnidadMedida(f.getLstConceptos().get(i).getUnidad());
							sanofiDetail.setTasaIva(f.getLstConceptos().get(i).getImpuestoTasaOCuota().substring(0, 4));
							sanofiDetail.setImporteIva(f.getLstConceptos().get(i).getImpuestoImporte());
							sanofiDetail.setDisponible1("0");
							sanofiDetail.setDisponible2("0");
							sanofiDetail.setDisponible3("0");
							sanofiDetail.setDisponible4("0");
							sanofiDetail.setDisponible5("0");
							sanofiDetail.setDisponible6("0");
							sanofi.getLstDetails().add(sanofiDetail);
						}
						Element addendaSanofi = fileXML.crearSanofi(sanofi);
						addenda.appendChild(addendaSanofi);

						document = addenda.getOwnerDocument();
						domImplLS = (DOMImplementationLS) document.getImplementation();
						serializer = domImplLS.createLSSerializer();
						addendaTxt = serializer.writeToString(addenda);
						addendaTxt = addendaTxt.replace("<?xml version=\"1.0\" encoding=\"UTF-16\"?>\n", "");
						break;
					case "129":
						AddendaPfizer pfizer = new AddendaPfizer();
						pfizer.setTipoAddenda("1");
						pfizer.setInstruccionesAdicionales("N/A");
						pfizer.setLineas(new ArrayList<AddendaPfizerLinea>());
						for (int i = 0; i < f.getLstConceptos().size(); i++) {
							AddendaPfizerLinea linea = new AddendaPfizerLinea();
							linea.setPoNumber(f.getPedido());
							linea.setQuantity(f.getLstConceptos().get(i).getCantidad());
							linea.setLineNo("" + f.getLstConceptos().get(i).getPart());
							linea.setAmount("" + f.getLstConceptos().get(i).getImporte());
							if (f.getLstConceptos().get(i).getImpuestoTasaOCuota().equals("0.000000")) {
								linea.setTaxCode("F1");
							} else {
								linea.setTaxCode("F2");
							}
							pfizer.getLineas().add(linea);
						}

						Element addendaPfizer = fileXML.crearPfizer(pfizer);
						addenda.appendChild(addendaPfizer);

						document = addenda.getOwnerDocument();
						domImplLS = (DOMImplementationLS) document.getImplementation();
						serializer = domImplLS.createLSSerializer();
						addendaTxt = serializer.writeToString(addenda);
						addendaTxt = addendaTxt.replace("<?xml version=\"1.0\" encoding=\"UTF-16\"?>\n", "");

						break;
				}
			}

			if (!addendaTxt.equals("") && respCFID != null) {
				respCFID = respCFID.replace("</cfdi:Complemento>", "</cfdi:Complemento>" + addendaTxt);
				log.info("--------------------------------respCFIDAddenda--------------------------------");
				log.info(respCFID);
			}

			if (resValido) {
				estadoFactura = "Timbrada";



				String rutaFacturas = "";
				if (f.getTipoComprobante().equals("I")) {
					rutaFacturas = funcion.obtenerRutaServidor("factura",
							f.getEmpresa().getAlias().replaceAll(" ", ""));
				} else if (f.getTipoComprobante().equals("P")) {
					rutaFacturas = funcion.obtenerRutaServidor("complementoPago",
							f.getEmpresa().getAlias().replaceAll(" ", ""));
				} else if (f.getTipoComprobante().equals("E")) {
					rutaFacturas = funcion.obtenerRutaServidor("notacredito",
							f.getEmpresa().getAlias().replaceAll(" ", ""));
				}else if (f.getTipoComprobante().equals("T")){
					rutaFacturas=funcion.obtenerRutaServidor("cartaporte","");
				}

				String ruta = rutaFacturas + f.getFolio() + ".xml";
				log.info(
						"--------------------------------XML Guardado en " + ruta + "--------------------------------");
				byte[] dec = respCFID.getBytes();
				File fileFinal = new File(ruta);
				FileOutputStream fos;
				fos = new FileOutputStream(fileFinal);
				fos.write(dec);
				fos.close();
				f.setXml(respCFID);

				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder;
				builder = factory.newDocumentBuilder();
				Document xmlResponseDoc = builder.parse(new InputSource(new StringReader(respCFID)));
				NodeList nodes = xmlResponseDoc.getElementsByTagName("tfd:TimbreFiscalDigital");
				NamedNodeMap nAtr = nodes.item(0).getAttributes();

				log.info("Version " + nAtr.getNamedItem("Version").getNodeValue());
				log.info("UUID " + nAtr.getNamedItem("UUID").getNodeValue());
				log.info("FechaTimbrado " + nAtr.getNamedItem("FechaTimbrado").getNodeValue());
				log.info("RfcProvCertif " + nAtr.getNamedItem("RfcProvCertif").getNodeValue());
				log.info("SelloCFD " + nAtr.getNamedItem("SelloCFD").getNodeValue());
				log.info("NoCertificadoSAT " + nAtr.getNamedItem("NoCertificadoSAT").getNodeValue());
				log.info("SelloSAT " + nAtr.getNamedItem("SelloSAT").getNodeValue());

				f.setSatCadenaOriginal(resTimbre);
				f.setSatVersion(nAtr.getNamedItem("Version").getNodeValue() + "");
				f.setSatUUID(nAtr.getNamedItem("UUID").getNodeValue());
				f.setSatFechaTimbrado(nAtr.getNamedItem("FechaTimbrado").getNodeValue());
				f.setSatRfcProvCertif(nAtr.getNamedItem("RfcProvCertif").getNodeValue());
				f.setSatSelloCFD(nAtr.getNamedItem("SelloCFD").getNodeValue());
				f.setSatNoCertificadoSAT(nAtr.getNamedItem("NoCertificadoSAT").getNodeValue());
				f.setSatSelloSAT(nAtr.getNamedItem("SelloSAT").getNodeValue());
				f.setEstado(estadoFactura);

				int tamFirma = f.getSello().length();
				String linkQR = "https://verificacfdi.facturaelectronica.sat.gob.mx/default.aspx?id=" + f.getSatUUID()
						+ "&re=" + f.getEmpresa().getRfcEmpresa() + "&rr=" + f.getCliente().getRfc() + "&tt="
						+ f.getTotal() + "&fe=" + f.getSello().substring(tamFirma - 8, tamFirma);
				f.setCodeQR(linkQR);

				f = facturacionElectronicaDAO.modificarFactura(f);

				if (relacionar && !f.getTipoComprobante().equals("T")) {
					try {
						Factura factura = new Factura();
						factura.setNumeroFactura(Long.parseLong(f.getFolio(), 10));
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
						factura.setFecha(formatter.parse(f.getFecha()));
						factura.setCondicionesPago(f.getCondicionesPago());
						factura.setIdCliente(f.getCliente().getIdCliente());
						if (f.getMoneda().equals("MXN")) {
							factura.setMoneda("M.N.");
							f.setTipoCambio(facturacionElectronicaDAO.obtenerTipoCambio());
						} else {
							factura.setMoneda(f.getMoneda());
						}
						factura.setImporte(Float.parseFloat(f.getSubtotal()));
						factura.setIva(Double.parseDouble(f.getImpuestosTasaOCuota()));
						factura.setEstado("Por Cobrar");
						factura.setFacturadoPor(f.getEmpresa().getAlias());
						factura.setPedido(f.getPedido());
						factura.setTipoCambio(Double.parseDouble(f.getTipoCambio()));
						factura.setCpedido(f.getCpedido());
						factura.setImprimirTipoCambio(false);
						factura.setRemision(false);
						factura.setOrdenCompra(null);
						factura.setTipo("Normal");
						factura.setSerie(f.getSerie());
						factura.setMedio("Electronica");
						factura.setUuid(f.getSatUUID());
						int idFactura = facturacionElectronicaDAO.insertarTablaFactura(factura);
						int pos = 0;
						for (PFacturaElectronica pf : f.getLstConceptos()) {
							pos++;
							PartidaFactura partidaFactura = new PartidaFactura();
							partidaFactura.setFactura(f.getFolio());
							Double num = Double.parseDouble(pf.getCantidad());
							partidaFactura.setCantidad(num.intValue());
							partidaFactura.setImporte(Double.parseDouble(pf.getValorUnitario()));
							partidaFactura.setPartidaFactura(pos);
							partidaFactura.setFpor(f.getEmpresa().getAlias());
							partidaFactura.setIdPCompra(null);
			 				partidaFactura.setSubTotal(Double.parseDouble(pf.getImpuestoTasaOCuota()));
							partidaFactura.setIdFactura(idFactura);
							partidaFactura.setPpedido(pf.getPart());
							partidaFactura.setCpedido(f.getCpedido());
							facturacionElectronicaDAO.insertarTablaPFacturas(partidaFactura);
						}
						for (PFacturaElectronica pf : f.getLstComplementos()) {
							pos++;
							PartidaFactura partidaFactura = new PartidaFactura();
							partidaFactura.setFactura(f.getFolio());
							Double num = Double.parseDouble(pf.getCantidad());
							partidaFactura.setCantidad(num.intValue());
							partidaFactura.setImporte(Double.parseDouble(pf.getValorUnitario()));
							partidaFactura.setPartidaFactura(pos);
							partidaFactura.setFpor(f.getEmpresa().getAlias());
							partidaFactura.setIdPCompra(null);
							partidaFactura.setSubTotal(Double.parseDouble(pf.getImpuestoTasaOCuota()));
							partidaFactura.setIdFactura(idFactura);
							partidaFactura.setPpedido(pf.getPart());
							partidaFactura.setCpedido(f.getCpedido());
							facturacionElectronicaDAO.insertarTablaPFacturas(partidaFactura);
						}

						facturacionElectronicaDAO.relacionarFactura_FElectronica(idFactura, f.getIdFactura());

						utilFacturaService.generarFacturaPDF(idFactura + "");
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

				String tipo = "";
				if (f.getTipoComprobante().equals("I")) {
					tipo = "T";
				} else if (f.getTipoComprobante().equals("P")) {
					tipo = "P";
					//complementoPagoService.generarComplemento(f.getIdFactura() + "");
				} else if(f.getTipoComprobante().equals("T"))
				{
					tipo="TcPorte";
					cartaPorteService.pruebaGenerarCartaPortePDF(f);
				}
				else{
					tipo = "NC";
					complementoPagoService.generarNotaCredito(f.getIdFactura() + "");
				}

				switch (f.getEmpresa().getAlias()) {
					case "Proquifa":
						folioDAO.actualizarValorConsecutivo(tipo + "Proquifa", (Long.parseLong(f.getFolio())));
						folioDAO.bloquear(tipo + "Proquifa", false);
						break;
					case "Proveedora":
						folioDAO.actualizarValorConsecutivo(tipo + "Proveedora", (Long.parseLong(f.getFolio())));
						folioDAO.bloquear(tipo + "Proveedora", false);
						break;
					case "ProquifaServicios":
						folioDAO.actualizarValorConsecutivo(tipo + "ProquifaServicios", (Long.parseLong(f.getFolio())));
						folioDAO.bloquear(tipo + "ProquifaServicios", false);
						break;
					case "Golocaer":
						folioDAO.actualizarValorConsecutivo(tipo + "Golocaer", (Long.parseLong(f.getFolio())));
						folioDAO.bloquear(tipo + "Golocaer", false);
						break;
					case "Mungen":
						folioDAO.actualizarValorConsecutivo(tipo + "Mungen", (Long.parseLong(f.getFolio())));
						folioDAO.bloquear(tipo + "Mungen", false);
						break;
					case "Pharma":
						folioDAO.actualizarValorConsecutivo(tipo + "Pharma", (Long.parseLong(f.getFolio())));
						folioDAO.bloquear(tipo + "Pharma", false);
						break;
					case "Proquifa El Salvador":
						folioDAO.actualizarValorConsecutivo(tipo + "RPHS", (Long.parseLong(f.getFolio())));
						folioDAO.bloquear(tipo + "RPHS", false);
						break;
					case "Ryndem":
						folioDAO.actualizarValorConsecutivo(tipo + "Ryndem", (Long.parseLong(f.getFolio())));
						folioDAO.bloquear(tipo + "Ryndem", false);
						break;
					case "RM Trading":
						folioDAO.actualizarValorConsecutivo(tipo + "RM Trading", (Long.parseLong(f.getFolio())));
						folioDAO.bloquear(tipo + "RM Trading", false);
						break;
				}

				return true;
			} else {
				log.info("--------------------------------resDesc--------------------------------");
				//JAXBElement<ArrayOfstring resDesc = r.getDescripcionError();
				ArrayList<String> resDesc = res.getErrores();
				estadoFactura = "Error al timbrar: ";
				for (String resDescItem : resDesc) {
					estadoFactura += "\n" + resDescItem;
					errorFactura += resDescItem + " ";
					log.info(resDescItem);
				}
				//Codigo para servico de Sidetec
				/*for (String resDescItem : resDesc.getValue().getString()) {
					estadoFactura += "\n" + resDescItem;
					errorFactura += resDescItem + " ";
					log.info(resDescItem);
				}*/

				facturacionElectronicaDAO.insertarErrorFactura(errorFactura, f.getIdFactura());

				String tipo = "";
				if (f.getTipoComprobante().equals("I")) {
					tipo = "T";
				} else if (f.getTipoComprobante().equals("P")) {
					tipo = "P";
				} else {
					tipo = "NC";
				}
				switch (f.getEmpresa().getAlias()) {
					case "Proquifa":
						folioDAO.bloquear(tipo + "Proquifa", false);
						break;
					case "Proveedora":
						folioDAO.bloquear(tipo + "Proveedora", false);
						break;
					case "ProquifaServicios":
						folioDAO.bloquear(tipo + "ProquifaServicios", false);
						break;
					case "Golocaer":
						folioDAO.bloquear(tipo + "Golocaer", false);
						break;
					case "Mungen":
						folioDAO.bloquear(tipo + "Mungen", false);
						break;
					case "Pharma":
						folioDAO.bloquear(tipo + "Pharma", false);
						break;
					case "Proquifa El Salvador":
						folioDAO.bloquear(tipo + "RPHS", false);
						break;
					case "Ryndem":
						folioDAO.bloquear(tipo + "Ryndem", false);
						break;
					case "RM Trading":
						folioDAO.bloquear(tipo + "RM Trading", false);
						break;
				}

				return false;
			}
		} catch (Exception e) {

			String tipo = "";
			if (f.getTipoComprobante().equals("I")) {
				tipo = "T";
			} else if (f.getTipoComprobante().equals("P")) {
				tipo = "P";
			} else {
				tipo = "NC";
			}

			switch (f.getEmpresa().getAlias()) {
				case "Proquifa":
					folioDAO.bloquear(tipo + "Proquifa", false);
					break;
				case "Proveedora":
					folioDAO.bloquear(tipo + "Proveedora", false);
					break;
				case "ProquifaServicios":
					folioDAO.bloquear(tipo + "ProquifaServicios", false);
					break;
				case "Golocaer":
					folioDAO.bloquear(tipo + "Golocaer", false);
					break;
				case "Mungen":
					folioDAO.bloquear(tipo + "Mungen", false);
					break;
				case "Pharma":
					folioDAO.bloquear(tipo + "Pharma", false);
					break;
				case "Proquifa El Salvador":
					folioDAO.bloquear(tipo + "RPHS", false);
					break;
				case "Ryndem":
					folioDAO.bloquear(tipo + "Ryndem", false);
					break;
				case "RM Trading":
					folioDAO.bloquear(tipo + "RM Trading", false);
					break;
			}

			e.printStackTrace();
			Correo correo=new Correo();
			correo.setCorreo("fernando.serdan@ryndem.mx;sara.sanchez@ryndem.mx");
			correo.setOrigen("ventas");
			correo.setAsunto("Error en timbrado");
			correo.setCuerpoCorreo("error:"+e);
			//correo.setTipo("");

			funcion = new Funcion();
			//funcion.enviarCorreo(correo);
			return false;
		}
	}

	private String convertirString(Document xml) {
		try {
			String xmlResult = "";
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(xml), new StreamResult(writer));
			xmlResult = writer.getBuffer().toString();
			return xmlResult;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public RespuestaTimbrado timbrarFactura33(String xmlString) throws Exception {
		try {
			log.info(xmlString);
			TurboPacWs service = new TurboPacWs();
			ITurboPacWs portType = service.getBasicHttpsBindingITurboPacWs();
			if(Funcion.FACTURACION_4){
				portType = service.getBasicHttpBindingITurboPacWs();
			}
			// return portType.timbraCfdi33("test2",
			// "76988887D99BE90534236A1441D5ED7AE9FE23CA", xmlString);
			log.info(xmlString);
			if(Funcion.FACTURACION_4) {
				try{
					return portType.timbraCfdi40(Funcion.USUARIO_TURBOPAC, Funcion.CONTRASENA_TURBOPAC, xmlString);
				}catch (Exception e){
					e.printStackTrace();
					return new RespuestaTimbrado();
				}

			} else {
				return portType.timbraCfdi33(Funcion.USUARIO_TURBOPAC, Funcion.CONTRASENA_TURBOPAC, xmlString);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new RespuestaTimbrado();
		}
	}
	@Override
	public FacturaTurboPac timbrarCFDI40(String xmlString,String tipoComprobante) throws Exception{
		try {
			// DOCS: Generar Token
			String token = generarToken(tipoComprobante);
			if(token != null){
				// DOCS: Mandar a timbrar
				String xmlTimbrado = null;
				String xml = generateBase64(xmlString);
				FacturaTurboPac response = this.generarCFDI(xml, token,tipoComprobante);

				return response;
			} else {
				return null;
			}
		}catch (Exception e){
			System.out.println(e);
			return null;
		}
	}
	public String generarToken(String tipoComprobante)throws ProquifaNetException{
		try {	Properties propiedades = new Properties();
			InputStream entrada = null;

			entrada = new FileInputStream(Funcion.RUTA_DOCUMENTOS+"configuraciones.properties");

			propiedades.load(entrada);
			String produccion=propiedades.getProperty("CartaPorteProd");
			String request=null;
			//if(produccion.equals("false")&&tipoComprobante.equals("T")) {
			if(produccion.equals("false")) {
				System.out.println("genera token de pruebas");
				 request = "https://uat.turbopacmx.com/TPAPITimbrador40/api/Token?username=ryndem&password=OYvdCO1jdEUYvUfVz0Q6mw=="; //Pruebas
			}else {
				request = "https://bovedaemision.turbopacmx.com/TPApiTimbradoV40/api/Token?username=ryndem&password=OYvdCO1jdEUYvUfVz0Q6mw==";
			}
				//String request = "https://bovedaemision.turbopacmx.com/TPApiTimbradoV40/api/Token?username=ryndem&password=OYvdCO1jdEUYvUfVz0Q6mw==";
			if (entrada != null) {
				try {
					entrada.close();
				} catch (IOException e) {
					e.printStackTrace();
				}}
			URL url = new URL( request );
			URLConnection con = url.openConnection();
			HttpURLConnection http = (HttpURLConnection) con;
			http.setRequestMethod("POST");
			http.setDoOutput(true);
			Map<String, String> body = new HashMap<>();
			//body.put("username", "prueba");
			//body.put("password", "prueba");
			StringJoiner sj = new StringJoiner("&");
			for(Map.Entry<String,String> entry : body.entrySet())
				sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
						+ URLEncoder.encode(entry.getValue(), "UTF-8"));
			byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
			int length = out.length;

			http.setFixedLengthStreamingMode(length);
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			http.connect();
			try(OutputStream os = http.getOutputStream()) {
				os.write(out);
			}
			int HttpResult = http.getResponseCode();
			if (HttpResult == HttpURLConnection.HTTP_OK) {
				//Respuesta Correcta
				System.out.println("Conexion éxitosa");
				// Lee la respuesta
				final StringBuffer response;
				try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
					String inputLine;
					response = new StringBuffer();
					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}
				}
				ObjectMapper mapper =  new ObjectMapper();
				JsonNode rootNode =  mapper.readTree(response.toString());
				String token=rootNode.get("Access_Token").asText();
				if(token != null){
					return token;
				} else {
					return null;
				}
			} else {
				System.out.println("Error de Conexion");
				System.out.println(HttpResult);
				return null;
			}
		}catch (Exception e){
			System.out.println(e);
			return null;
		}
	}
	public FacturaTurboPac generarCFDI(String xml, String token, String tipoComprobante) throws ProquifaNetException{
		try {

			Properties propiedades = new Properties();
			InputStream entrada = null;

			entrada = new FileInputStream(Funcion.RUTA_DOCUMENTOS+"configuraciones.properties");

			propiedades.load(entrada);
			String produccion=propiedades.getProperty("CartaPorteProd");
			String request=null;
			//if (produccion.equals("false")&&tipoComprobante.equals("T")) {
			if(produccion.equals("false")){
				System.out.println("Recibe token de pruebas para timbrar");
				request = "https://uat.turbopacmx.com/TPAPITimbrador40/api/Timbrado?access_token=" + token;//Pruebas
			}else {
				request = "https://bovedaemision.turbopacmx.com/TPApiTimbradoV40/api/Timbrado?access_token="+token;
			}
			System.out.printf(request);
			URL url = new URL(request);
			Date fechaATimbrar = new Date();
			SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
			log.info("Referencia(generarCFDI):" + formatoFecha.format(fechaATimbrar)+" "+formatoHora.format(fechaATimbrar));
			// Abrimos una conexión HTTP con la URL
			HttpURLConnection conexion = (HttpURLConnection) url.openConnection();

			// Establecemos el método de la solicitud
			conexion.setRequestMethod("POST");
			// Establecemos las cabeceras de la solicitud
			conexion.setRequestProperty("Content-Type", "application/json");
			// Armamos el body
			ObjectMapper mapperT = new ObjectMapper();
			ObjectNode requestBody = mapperT.createObjectNode();
			requestBody.put("Referencia",formatoFecha.format(fechaATimbrar)+" "+formatoHora.format(fechaATimbrar));
			LocalDate fechaActual = LocalDate.now();
			requestBody.put("Fecha",fechaActual.toString());
			ArrayNode facturasNode = mapperT.createArrayNode();
			ObjectNode facturaNode = mapperT.createObjectNode();
			facturaNode.put("Base64",xml);
			facturasNode.add(facturaNode);
			requestBody.set("Facturas", facturasNode);
			String requestBodyJson = mapperT.writeValueAsString(requestBody);
			// Establecer el cuerpo de la solicitud y enviarla
			conexion.setDoOutput(true);
			try (OutputStream os = conexion.getOutputStream()) {
				byte[] input = requestBodyJson.getBytes("utf-8");
				os.write(input, 0, input.length);
			}
			// Manejar la respuesta, el estatus
			int statusCode = conexion.getResponseCode();
			FacturaTurboPac factura = new FacturaTurboPac();
			if(statusCode == 200){
				// Lee la respuesta
				final StringBuffer responseF;
				try (BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()))) {
					String inputLine;
					responseF = new StringBuffer();
					while ((inputLine = in.readLine()) != null) {
						responseF.append(inputLine);
					}
				}
				ObjectMapper mapper =  new ObjectMapper();
				JsonNode rootNode =  mapper.readTree(responseF.toString());
				String xmlBase64 = rootNode.get("Facturas").get(0).get("XMLTimbrado").asText();
				/*Convertir de Base64 a xml*/
				byte[] decodeBytes = org.apache.commons.codec.binary.Base64.decodeBase64(xmlBase64);
				String xmlResponse = new String(decodeBytes);
				System.out.println("===========XML CONVERTIDO========");
				System.out.println(xmlResponse);
				Integer incidencias = rootNode.get("CifrasControl").get("TotalIncidentes").asInt();
				JsonNode err = rootNode.get("Facturas").get(0).get("ReporteIncidentes").get("Incidentes");
				ArrayList<String> listError = new ArrayList<>();
				if(err != null){
					for (JsonNode e: err){
						String error = e.toString();
						listError.add(error);
					}
				}
				factura.setXMLTimbrado(xmlResponse);
				factura.setIncidencias(incidencias);
				factura.setErrores(listError);
			}
			System.out.println(statusCode);
			if (entrada != null) {
				try {
					entrada.close();
				} catch (IOException e) {
					e.printStackTrace();
				}}
			// Aquí realizas la desconeccion del servicio
			conexion.disconnect();
			return factura;
		}catch (Exception e){
			return null;
		}
	}


	public String XMLParser (String xml){
		String atributo1Valor=null;

		System.out.println("Valor del String: "+xml);
		try {

			byte[] decodeBytes = org.apache.commons.codec.binary.Base64.decodeBase64(xml);
			String xmlResponse = new String(decodeBytes);
			System.out.println("===========XML CONVERTIDO========");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			//Document document = builder.parse(new InputSource(new StringReader(xml)));

			Document document = builder.parse(new ByteArrayInputStream(xmlResponse.getBytes()));

			//Element root = document.getDocumentElement();

			NodeList elementos = document.getElementsByTagName("cfdi:Comprobante");
			System.out.println("Tamaño"+elementos.getLength());
			if (elementos.getLength() > 0) {
				Element comprobante = (Element) elementos.item(0);

				// Obtener el valor del atributo TipoDeComprobante
				atributo1Valor	= comprobante.getAttribute("TipoDeComprobante");
				System.out.println("Tipo de Comprobante: " + atributo1Valor);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return atributo1Valor;
	}
	public String generateBase64(String data) {

		try {
			String cadenaBase64 = java.util.Base64.getEncoder().encodeToString(data.getBytes());
			System.out.println(cadenaBase64); // Impresion del dato convetido a base64
			return cadenaBase64;
		}catch (Exception e){
			return null;
		}
	}
	public String obtenerFolio(String alias, String tipoComprobante) throws ProquifaNetException {
		String folio = "";
		String rutaXML = "";
		String tipo = "";
		File fileXMLAux;
		if (tipoComprobante.equals("I")) {
			tipo = "T";
		} else if (tipoComprobante.equals("P")) {
			tipo = "P";
		}else if(tipoComprobante.equals("T")){
			tipo="TcPorte";
		}else {
			tipo = "NC";
		}
		switch (alias) {
			case "Proquifa":
				if (folioDAO.getEstaBloqueadoXConcepto(tipo + "Proquifa") == 0) {
					folio = folioDAO.getConsecutivoMasUnoXConceptoFolio(tipo + "Proquifa") + "";
					folioDAO.bloquear(tipo + "Proquifa", true);
				} else {
					return "bloqued";
				}
				break;
			case "Proveedora":
				if (folioDAO.getEstaBloqueadoXConcepto(tipo + "Proveedora") == 0) {
					folio = folioDAO.getConsecutivoMasUnoXConceptoFolio(tipo + "Proveedora") + "";
					folioDAO.bloquear(tipo + "Proveedora", true);
				} else {
					return "bloqued";
				}
				break;
			case "ProquifaServicios":
				if (folioDAO.getEstaBloqueadoXConcepto(tipo + "ProquifaServicios") == 0) {
					folio = folioDAO.getConsecutivoMasUnoXConceptoFolio(tipo + "ProquifaServicios") + "";
					folioDAO.bloquear(tipo + "ProquifaServicios", true);
				} else {
					return "bloqued";
				}
				break;
			case "Golocaer":
				if (folioDAO.getEstaBloqueadoXConcepto(tipo + "Golocaer") == 0) {
					folio = folioDAO.getConsecutivoMasUnoXConceptoFolio(tipo + "Golocaer") + "";
					folioDAO.bloquear(tipo + "Golocaer", true);
				} else {
					return "bloqued";
				}
				break;
			case "Mungen":
				if (folioDAO.getEstaBloqueadoXConcepto(tipo + "Mungen") == 0) {
					folio = folioDAO.getConsecutivoMasUnoXConceptoFolio(tipo + "Mungen") + "";
					folioDAO.bloquear(tipo + "Mungen", true);
				} else {
					return "bloqued";
				}
				break;
			case "Pharma":
				if (folioDAO.getEstaBloqueadoXConcepto(tipo + "Pharma") == 0) {
					folio = folioDAO.getConsecutivoMasUnoXConceptoFolio(tipo + "Pharma") + "";
					folioDAO.bloquear(tipo + "Pharma", true);
				} else {
					return "bloqued";
				}
				break;
			case "Proquifa El Salvador":
				if (folioDAO.getEstaBloqueadoXConcepto(tipo + "RPHS") == 0) {
					folio = folioDAO.getConsecutivoMasUnoXConceptoFolio(tipo + "RPHS") + "";
					folioDAO.bloquear(tipo + "RPHS", true);
				} else {
					return "bloqued";
				}
				break;
			case "Ryndem":
				if (folioDAO.getEstaBloqueadoXConcepto(tipo + "Ryndem") == 0) {
					folio = folioDAO.getConsecutivoMasUnoXConceptoFolio(tipo + "Ryndem") + "";
					folioDAO.bloquear(tipo + "Ryndem", true);
				} else {
					return "bloqued";
				}
				break;
			case "RM Trading":
				if (folioDAO.getEstaBloqueadoXConcepto(tipo + "RM Trading") == 0) {
					folio = folioDAO.getConsecutivoMasUnoXConceptoFolio(tipo + "RM Trading") + "";
					folioDAO.bloquear(tipo + "RM Trading", true);
				} else {
					return "bloqued";
				}
				break;
		}

		if (tipoComprobante.equals("I")) {
			rutaXML = funcion.obtenerRutaServidor("factura", alias.replaceAll(" ", "")) + folio + ".xml";
		} else if (tipoComprobante.equals("P")) {
			rutaXML = funcion.obtenerRutaServidor("complementopago", alias.replaceAll(" ", "")) + folio + ".xml";
		} else if (tipoComprobante.equals("E")) {
			rutaXML = funcion.obtenerRutaServidor("notacredito", alias.replaceAll(" ", "")) + folio + ".xml";
		} else if (tipoComprobante.equals("T")) {
			rutaXML=funcion.obtenerRutaServidor("cartaporte","");

		}

		fileXMLAux = new File(rutaXML);
		if (fileXMLAux.exists()) {
			Correo correo = new Correo();
			correo.setArchivoAdjunto(rutaXML);
			correo.setCorreo("oscar.cardona@ryndem.mx");
			correo.setOrigen("ventas");
			correo.setAsunto("XML Repetido");
			correo.setCuerpoCorreo("Se trato de sobrescribir el siguiente archivo: " + rutaXML);
			correo.setTipo("");

			funcion = new Funcion();
		//	funcion.enviarCorreo(correo);

			// fileXMLAux.delete();

		}
		return folio;
	}

	@Override
	public RespuestaSap facturaSap(int idFactura) throws Exception {
		try {
			RespuestaSap respSap = facturacionElectronicaDAO.facturaSap(idFactura);

			return respSap;
		} catch (Exception e) {
			e.printStackTrace();
			RespuestaSap respSap = new RespuestaSap();
			respSap.setFolio("Error");
			return respSap;
		}
	}

	@Override
	public Integer insertarFacturaGenerada(Integer idFacturaElectronica, List<PFacturaElectronica> partidas) throws ProquifaNetException {
		try {
			Integer numPartida = 1;
			com.proquifa.net.modelo.cobrosypagos.facturista.Factura factura = facturacionDAO.obtenerFacturaElectronica(idFacturaElectronica);
			factura.setIdFacturaElectronica(idFacturaElectronica);
			factura.setPartidas(new ArrayList<com.proquifa.net.modelo.cobrosypagos.facturista.PartidaFactura>());
			for (PFacturaElectronica partida : partidas) {
				com.proquifa.net.modelo.cobrosypagos.facturista.PartidaFactura pfactura = new com.proquifa.net.modelo.cobrosypagos.facturista.PartidaFactura();
				pfactura.setFactura(factura.getNumeroFactura().toString());
				pfactura.setFpor(factura.getFacturadoPor());
				pfactura.setCantidad(Integer.parseInt(partida.getCantidad()));
				pfactura.setConceptoPartida(partida.getDescripcion());
				pfactura.setImporte(Double.parseDouble(partida.getValorUnitario().toString()));
				pfactura.setPartidaFactura(numPartida++);
				pfactura.setNota(partida.getNotas());
				factura.getPartidas().add(pfactura);
			}
			Integer idFactura = facturacionDAO.insertarFactura(factura);
			//generarFacturaPDF(idFactura);

			Pendiente pendiente = new Pendiente();
			Date fecha = new Date();
			String sResponsable = this.clienteDAO.obtenerCobradorCliente(factura.getIdCliente());

			pendiente.setDocto(String.valueOf(idFactura));
			pendiente.setFechaInicio(fecha);
			pendiente.setResponsable(sResponsable);
			pendiente.setTipoPendiente("Monitorear Cobro FS");
			this.pendienteDAO.guardarPendiente_angular(pendiente);

			return idFactura;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}
public String nombre () throws ProquifaNetException {
	return facturacionElectronicaDAO.getNameCliente("");
}
}
