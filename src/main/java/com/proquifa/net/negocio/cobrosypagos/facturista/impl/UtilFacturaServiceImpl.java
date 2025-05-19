package com.proquifa.net.negocio.cobrosypagos.facturista.impl;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.proquifa.net.modelo.cobrosypagos.facturista.Factura;
import com.proquifa.net.modelo.comun.CatalogoItem;
import com.proquifa.net.modelo.comun.FacturaPQF;
import com.proquifa.net.modelo.comun.PartidaFacturaPqf;
import com.proquifa.net.modelo.comun.ReferenciaBancaria;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.facturaElectronica.FacturaElectronica;
import com.proquifa.net.modelo.comun.facturaElectronica.PFacturaElectronica;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.ventas.PartidaCotizacion;
import com.proquifa.net.negocio.cobrosypagos.facturista.UtilFacturaService;
import com.proquifa.net.persistencia.cobrosypagos.facturista.FacturacionDAO;
import com.proquifa.net.persistencia.cobrosypagos.facturista.impl.FacturacionDAOImpl;
import com.proquifa.net.persistencia.comun.facturacion.FacturacionElectronicaDAO;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@SuppressWarnings("deprecation")
@Service("utilFacturaService")
public class UtilFacturaServiceImpl implements  UtilFacturaService{
	@Autowired
	FacturacionDAO facturacionDAO;

	@Autowired
	FacturacionElectronicaDAO facturacionElectronicaDAO;
	
	final Logger log = LoggerFactory.getLogger(UtilFacturaServiceImpl.class);

	@Async	
	@Override
	public void generarFacturaPDF(String idFactura) throws ProquifaNetException {
		try {
			List<Factura> factura1 = new ArrayList<Factura>();
			factura1 = facturacionDAO.findFacturaPendiente(idFactura);
			if (factura1 != null && factura1.size() > 0) {
				for (Factura facturaTmp : factura1) {
					try{
						InputStream input = null;

						FacturacionDAOImpl fd = new FacturacionDAOImpl();
						Funcion funcion = new Funcion();
						List<ReferenciaBancaria> bancaria = new ArrayList<ReferenciaBancaria>();
						List<PartidaFacturaPqf> partidaFactura = new ArrayList<PartidaFacturaPqf>();
						String fPor = facturaTmp.getFacturadoPor();
						Long facturaa = facturaTmp.getNumeroFactura();

						FacturaElectronica felectronica = facturacionElectronicaDAO.getFElectronicaByFactura(facturaTmp.getIdFactura().intValue());
						
						String rfcInternacional = facturacionElectronicaDAO.validarClienteInternacional(felectronica);
						
						if (rfcInternacional != null && !rfcInternacional.equals("")) {
							felectronica.getCliente().setRfc(rfcInternacional);
						}

						// RUTAS
						String rutaPlantilla = funcion.obtenerRutaServidor("plantillafactura", "");
						String rutaEtiqueta = funcion.obtenerRutaServidor("facturapqf", "");
						String rutaFacturas = funcion.obtenerRutaServidor("factura",fPor.replaceAll(" ", "")); 

						log.info (rutaFacturas + facturaa + ".xml");
						log.info ("#Factura: "+facturaa );
						// Se carga el documento.
						//File archivo = new File(documento); 
						DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
						DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
						//Document document = documentBuilder.parse(archivo);
						//document.getDocumentElement().normalize();

						FacturaPQF Fpq = new FacturaPQF();

						// Datos de la factura
						Fpq.setVersion(felectronica.getVersion());
						Fpq.setFechaFactura(felectronica.getFecha());
						Fpq.setSello(felectronica.getSello());
						Fpq.setNoCertificado(felectronica.getNoCertificado());
						Fpq.setCertificado(felectronica.getCertificado());
						Fpq.setCondicionesPago(felectronica.getCondicionesPago());
						Fpq.setSubTotal( Double.parseDouble(felectronica.getSubtotal()));
						Fpq.setcPedido(felectronica.getCpedido());

						String moneda = felectronica.getMoneda();
						if	(moneda.toLowerCase().equals("mxn")){
							Fpq.setMoneda("MXN-Peso Mexicano");
						} else if (moneda.toLowerCase().equals("usd")){
							Fpq.setMoneda("USD- Dolar americano");
						} else if (moneda.toLowerCase().equals("eur")){
							Fpq.setMoneda("EUR- Euro");
						} else {
							Fpq.setMoneda(moneda);
						}

						Fpq.setTipoCambio(felectronica.getTipoCambio());
						Fpq.setTotal(felectronica.getTotal());

						String tipoComprobante = felectronica.getTipoComprobante();
						if (tipoComprobante.equals("I")) {
							tipoComprobante = "I-Ingreso";
						}
						Fpq.setTipoComprobante(tipoComprobante);

						String metodoPago = felectronica.getMetodoPago();
						if (metodoPago.equals("PPD")) {
							metodoPago = "PPD-Pago en parcialidades o diferido";
							Fpq.setMetodoPago(metodoPago);
						} else if (metodoPago.equals("PUE")) {
							metodoPago = "PUE-Pago en una sola exhibición";
							Fpq.setMetodoPago(metodoPago);
						} else if (metodoPago.equals("PIP")) {
							metodoPago = "PIP-Pago inicial y parcialidades";
							Fpq.setMetodoPago(metodoPago);
						}else {
							Fpq.setMetodoPago(metodoPago);
						}

						Fpq.setLugarExpedicion(felectronica.getLugarExpedicion());
						Fpq.setSerie(felectronica.getSerie());
						Fpq.setFolio(felectronica.getFolio());


						// Datos del emisor
						Fpq.setRfcEmisor(felectronica.getEmpresa().getRfcEmpresa());
						Fpq.setNombreEmisor(felectronica.getEmpresa().getRazonSocial());						
						String regimenFiscalEmisor = felectronica.getEmpresa().getRegimenFiscal();
						if (regimenFiscalEmisor.equals("601")) {
							regimenFiscalEmisor = "601-General de Ley Personas Morales";
						}
						Fpq.setRegimenFiscalEmisor(regimenFiscalEmisor);


						// Obteniendo datos del receptor
						Fpq.setRfcReceptor(felectronica.getCliente().getRfc());
						String nombreReceptor = felectronica.getCliente().getRazonSocial();
						Fpq.setNombreReceptor(nombreReceptor.replace("&", "&amp;"));

						String usoCfdi = felectronica.getCliente().getUsoCFDI();
						Fpq.setUsoCfdi(usoCfdi);

						// Obteniendo datos de ref Bancarias
						bancaria = facturacionElectronicaDAO.obtenerReferenciaBancaria(felectronica.getCliente().getIdCliente().intValue(), felectronica.getEmpresa().getAlias());

						// Obteniendo datos de los traslados

						String impuesto = felectronica.getImpuestosClave();
						if (impuesto.equals("002")) {
							impuesto = "002-IVA";
							Fpq.setImpuesto(impuesto);
						} else {
							Fpq.setImpuesto(impuesto);
						}

						Fpq.setTipoFactor(felectronica.getImpuestosTipoFactor());
						Fpq.setTasaOCuota(felectronica.getImpuestosTasaOCuota());
						Double importeTrasladoConcepto = Double.parseDouble(felectronica.getImpuestosImporte());
						Fpq.setImporteTrasladoConcepto(importeTrasladoConcepto);

						// Obteniendo Número de pedimento.
						Fpq.setNumeroPedimento("setNumeroPedimento");

						// Caso especial SANOFI 
						String proveedor = facturacionElectronicaDAO.obtenerNumProveedor(felectronica.getCliente().getIdCliente().intValue(), felectronica.getEmpresa().getAlias());
						if(proveedor != null) {
							Fpq.setNumeroProveedor(proveedor);
							Fpq.setOrdenCompra(felectronica.getPedido());
						}else {
							Fpq.setNumeroProveedor(null);
							Fpq.setOrdenCompra(null);
						}

						// Obteniendo timbre fiscalDigital
						Fpq.setNoCertificadoSat(felectronica.getSatNoCertificadoSAT());
						Fpq.setSelloSat(felectronica.getSatSelloSAT());
						Fpq.setSelloCFD(felectronica.getSatSelloCFD());
	
						// Obteniendo cadena Original de factura
						Fpq.setCadenaOriginal(felectronica.getCadenaOriginal());
						
						// Obteniendo total Impuesto Federal
						Double totalImpuestos = Double.parseDouble(felectronica.getImpuestosTotalTraslados());
						Fpq.setTotalImpuestos(totalImpuestos);

						// Obteniendo número de pedido
						Fpq.setPedido(felectronica.getPedido());
						Fpq.setoC(felectronica.getOrdenCompra());

						// Obteniendo la lista de partidas.
						PartidaFacturaPqf pfpq = new PartidaFacturaPqf();
						for(PFacturaElectronica pfe: felectronica.getLstConceptos()) {
							pfpq = new PartidaFacturaPqf();
							pfpq.setCantidad(pfe.getCantidad());
							pfpq.setClaveUnidad(pfe.getClaveUnidad());
							pfpq.setUnidadMedida(pfe.getUnidad());
							pfpq.setClaveProdServ(pfe.getClaveProdServ());
							pfpq.setCodigo(pfe.getNoIdentificacion());
							String descripcionPartida = pfe.getDescripcion();
							pfpq.setDescripcion(descripcionPartida.replace("&amp;", "&"));
							Double importe = Double.parseDouble(pfe.getImporte());
							pfpq.setImporte(funcion.formatoMoneda(importe));
							
							if(pfe.getAdnNumeroPedimento() != null && !pfe.getAdnNumeroPedimento().equals("")) {
								pfpq.setNumeroPedimento(pfe.getAdnNumeroPedimento());
							}else {
								pfpq.setNumeroPedimento("N / A");
							}
							
							Double valorUnitario = Double.parseDouble(pfe.getValorUnitario());
							pfpq.setValorUnitario(funcion.formatoMoneda(valorUnitario));
							
							Double base = Double.parseDouble(pfe.getImpuestoBase());
							pfpq.setBaseTraslado(funcion.formatoMoneda(base));
							
							String impuestoTraslado = (pfe.getImpuestoClave());
							if (impuestoTraslado.equals("002")) {
								impuestoTraslado = "002-IVA";
								pfpq.setImpuestoTraslado(impuestoTraslado);
							} else {
								pfpq.setImpuestoTraslado(impuestoTraslado);
							}
							
							pfpq.setTipoFactorTraslado(pfe.getImpuestoTipoFactor());
							pfpq.setTasaOCuotaTraslado(pfe.getImpuestoTasaOCuota());

							Double importeTraslado = Double.parseDouble(pfe.getImpuestoImporte());
							pfpq.setImporteTrasladoConcepto(funcion.formatoMoneda(importeTraslado));
							
							if(pfe.getComentario() != null && !pfe.getComentario().equals("")) {
								pfpq.setNotas(pfe.getComentario());
							} else {
								pfpq.setNotas(" ");
							}
							
							
							//falta agregar notas
							if(pfe == felectronica.getLstConceptos().get(felectronica.getLstConceptos().size() -1 )) {
								if(felectronica.getNotaFE() == null) {
									felectronica.setNotaFE(" ");
								}
								
								pfpq.setNotas(pfpq.getNotas() + "\n" + felectronica.getNotaFE());
							}
							
							
							partidaFactura.add(pfpq);
						}

						// Obteniendo condiciones de pago
						String formaPago =felectronica.getFormaPago();
						CatalogoItem c = this.facturacionDAO.obtenerFormaPago(formaPago);
						Fpq.setCodigoTerminoPago(c.getValor());
						

						// Obteniendo totales
						Double totalFactura = Double.parseDouble(felectronica.getTotal());
						Fpq.setTotalFactura(totalFactura);
						Fpq.setTotalLetra(felectronica.getTotalTexto());
				

						// Obteniendo datos timbreFiscalDigital
						Fpq.setFolioFiscal(felectronica.getSatUUID());
						Fpq.setNoCertificadoSAT(felectronica.getSatNoCertificadoSAT());
						

						// Variables adicionales
						String datosDigitales = "NÚMERO DE SERIE DEL CERTIFICADO DEL SAT:" + Fpq.getNoCertificadoSat()
						+ " NÚMERO DE SERIE DEL CSD DEL EMISOR:" + Fpq.getNoCertificado()
						+ " SELLO DIGITAL DEL SAT:" + Fpq.getSelloSat() 
						+ " SELLO DIGITAL DEL CFDI:"+ Fpq.getSelloCFD() 
						+ " CADENA ORIGINAL DEL COMPLEMENTO DE CERTIFICACIÓN DIGITAL DEL SAT:"+ Fpq.getCadenaOriginal();
						Fpq.setDatosDigitales(datosDigitales);

						String fe= Fpq.getSelloCFD().substring((Fpq.getSelloCFD().length()-8), Fpq.getSelloCFD().length());
						Fpq.setCodigoQR( "https://verificacfdi.facturaelectronica.sat.gob.mx/default.aspx?id="+ Fpq.getFolioFiscal() + "&re=" + Fpq.getRfcEmisor()
						+ "&rr=" + Fpq.getRfcReceptor() + "&tt="+ Fpq.getTotalFactura() + "&fe=" + fe);
						crearCodigo(Fpq.getCodigoQR());		

						String logo = "";
						if (fPor.toLowerCase().equals("golocaer")) {
							logo = "golocaer.png";
						} else if (fPor.toLowerCase().equals("mungen")) {
							logo = "Mungen.png";
						} else if (fPor.toLowerCase().equals("ryndem")) {
							logo = "logo_ryndem.svg";
						} else {
							logo = "Proquifa.jpg";
						}

						// Ruta para guardar el documento

//						InputStream inputStream = new FileInputStream(rutaPlantilla + "FacturaPQF_Nuevo.jrxml");
						InputStream inputStream = new FileInputStream(rutaPlantilla + "FacturaPQF_v3.jrxml");
						JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
						JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
						Map<String, Object> parametros = new HashMap<String, Object>();

						parametros.put("nombreEmisor", Fpq.getNombreEmisor());
						parametros.put("rfcEmisor", Fpq.getRfcEmisor());
						parametros.put("lugarExpedicion", Fpq.getLugarExpedicion());
						parametros.put("nombreReceptor", Fpq.getNombreReceptor());
						parametros.put("rfcReceptor", Fpq.getRfcReceptor());
						parametros.put("usoCfdi", Fpq.getUsoCfdi());
						parametros.put("serie", Fpq.getSerie());
						parametros.put("version", Fpq.getVersion());
						parametros.put("fechaFactura", Fpq.getFechaFactura());
						parametros.put("metodoPago", Fpq.getMetodoPago());
						parametros.put("condicionesPago", Fpq.getCondicionesPago());
						parametros.put("regimenFiscal", Fpq.getRegimenFiscalEmisor());
						parametros.put("folio", Fpq.getFolio());
						parametros.put("folioFiscal", Fpq.getFolioFiscal());
						parametros.put("formaPago", Fpq.getCodigoTerminoPago());
						parametros.put("tipoComprobante", Fpq.getTipoComprobante());
						parametros.put("certificadoSat", Fpq.getNoCertificadoSat());
						parametros.put("selloCfd", Fpq.getSelloCFD());
						parametros.put("selloDigitalSat", Fpq.getSello());
						parametros.put("totalLetra", Fpq.getTotalLetra());
						parametros.put("impuesto", Fpq.getImpuesto());
						parametros.put("tipoFactor", Fpq.getTipoFactor());
						parametros.put("tasaOCuota", Fpq.getTasaOCuota());
						parametros.put("subtotal", funcion.formatoMoneda(Fpq.getSubTotal()));
						// Nuevo de CFDI 4.0 CP del Receptor
						//if(felectronica.getCliente().getDireccion().getCodigoPostal() != null){
						//	parametros.put("cpReceptor", felectronica.getCliente().getDireccion().getCodigoPostal());
						//}else {
							parametros.put("cpReceptor", facturacionElectronicaDAO.obtenerCPCliente(Fpq.getRfcReceptor()));
							felectronica.getCliente().getIdCliente();
						//}
						if (Fpq.getPedido() != null){
							parametros.put("pedido", Fpq.getPedido());
						} else {
							parametros.put("pedido", "");
						}
						
						if (Fpq.getoC() != null){
							parametros.put("OC", Fpq.getoC());
						} else {
							parametros.put("OC", "");
						}

						parametros.put("numProveedor", Fpq.getNumeroProveedor());
						parametros.put("totalFactura", funcion.formatoMoneda(Fpq.getTotalFactura()));
						parametros.put("moneda", Fpq.getMoneda());
						parametros.put("datosDigitales", Fpq.getDatosDigitales());
						parametros.put("tipoCambio", Fpq.getTipoCambio());

						if (Fpq.getTotalImpuestos() != null)
							parametros.put("totalTraslados", funcion.formatoMoneda(Fpq.getTotalImpuestos()));

						// Listas
						parametros.put("listaPartidas", partidaFactura);
						parametros.put("listaRefBancarias", bancaria);
						// Imagenes
						parametros.put("edomBlanco", rutaPlantilla + "edom.png");
						parametros.put("feum", rutaPlantilla + "feum_blanco-01.png");
						parametros.put("british", rutaPlantilla + "british_blanco-01.png");
						parametros.put("usp", rutaPlantilla + "usp_blanco-01.png");
						parametros.put("micro", rutaPlantilla + "microbiologics_blanco-01.png");
						parametros.put("apacor", rutaPlantilla + "apacor_blanco-01.png");
						parametros.put("chata", rutaPlantilla + "CHATA_blanco-01.png");
						parametros.put("pharma", rutaPlantilla + "PHARMAFFILIATES_blanco-01.png");
						parametros.put("codQr", rutaPlantilla + "codigoBarras.png");
						parametros.put("iso", rutaPlantilla + "iso.png");
						parametros.put("neec", rutaPlantilla + "neec.png");
						parametros.put("amex", rutaPlantilla + "Amex.png");
						parametros.put("ruta", rutaPlantilla + logo);
						StringBuilder dirEntrega = new StringBuilder("");

						//Nuevo
						if(felectronica.getCliente().getDireccion().getCalle() != null) {
							dirEntrega.append("CALLE ").append(felectronica.getCliente().getDireccion().getCalle()); 
						}
						
						if(felectronica.getCliente().getDireccion().getCodigoPostal() != null) {
							dirEntrega.append(" C.P. ").append(felectronica.getCliente().getDireccion().getCodigoPostal());
						}

						if(felectronica.getCliente().getDireccion().getCiudad() != null){
							dirEntrega.append(" CIUDAD ").append(felectronica.getCliente().getDireccion().getCiudad());
						}

						if(felectronica.getCliente().getDireccion().getMunicipio() != null) {
							dirEntrega.append(" MUNICIPIO ").append(felectronica.getCliente().getDireccion().getMunicipio());
						}

						if(felectronica.getCliente().getDireccion().getPais() != null){
							dirEntrega.append(" PAÍS ").append(felectronica.getCliente().getDireccion().getPais());
						}

						parametros.put("dirEntrega", dirEntrega.toString());
						parametros.put("dirEmpresa", "CALLE: " + felectronica.getEmpresa().getCalle() + " COLONIA: " + felectronica.getEmpresa().getColonia() + " ALCALDÍA: " + felectronica.getEmpresa().getDelegacion() + " C.P. " + felectronica.getEmpresa().getCp() + " CIUDAD: " + felectronica.getEmpresa().getCiudad() + " PAÍS: " + felectronica.getEmpresa().getPais()); //Nuevo
						
						parametros.put("pedidoI", Fpq.getcPedido());
						parametros.put("controlado", felectronica.isEsControlada());
						String regimenReceptor;
						String regimenReceptorTxt ="";
						if(Fpq.getRfcReceptor() != null){
							regimenReceptor = facturacionElectronicaDAO.getRegimenF(Fpq.getRfcReceptor());
							if(regimenReceptor != null){
								if(regimenReceptor.equals("601")){
									regimenReceptorTxt = "General de ley Personas Morales";
								} else if(regimenReceptor.equals("603")){
									regimenReceptorTxt = "Personas Morales con Fines no Lucrativos";
								}else if(regimenReceptor.equals("605")){
									regimenReceptorTxt = "Sueldos y Salarios e Ingresos Asimilados a Salarios";
								}else if(regimenReceptor.equals("606")){
									regimenReceptorTxt = "Arrendamiento";
								}else if(regimenReceptor.equals("607")){
									regimenReceptorTxt = "Régimen de Enajenación o Adquisición de Bienes";
								}else if(regimenReceptor.equals("608")){
									regimenReceptorTxt = "Demás ingresos";
								}else if(regimenReceptor.equals("609")){
									regimenReceptorTxt = "Consolidación";
								}else if(regimenReceptor.equals("610")){
									regimenReceptorTxt = "Residentes en el Extranjero sin Establecimiento Permanente en México";
								}else if(regimenReceptor.equals("611")){
									regimenReceptorTxt = "Ingresos por Dividendos (socios y accionistas)";
								}else if(regimenReceptor.equals("612")){
									regimenReceptorTxt = "Personas Físicas con Actividades Empresariales y Profesionales";
								}else if(regimenReceptor.equals("614")){
									regimenReceptorTxt = "Ingresos por intereses";
								}else if(regimenReceptor.equals("615")){
									regimenReceptorTxt = "Régimen de los ingresos por obtención de premios";
								}else if(regimenReceptor.equals("616")){
									regimenReceptorTxt = "Sin obligaciones fiscales";
								}else if(regimenReceptor.equals("620")){
									regimenReceptorTxt = "Sociedades Cooperativas de Producción que optan por diferir sus ingresos";
								}else if(regimenReceptor.equals("621")){
									regimenReceptorTxt = "Incorporación Fiscal";
								}else if(regimenReceptor.equals("622")){
									regimenReceptorTxt = "Actividades Agrícolas, Ganaderas, Silvícolas y Pesqueras";
								}else if(regimenReceptor.equals("623")){
									regimenReceptorTxt = "Opcional para Grupos de Sociedades";
								}else if(regimenReceptor.equals("624")){
									regimenReceptorTxt = "Coordinados";
								}else if(regimenReceptor.equals("625")){
									regimenReceptorTxt = "Régimen de las Actividades Empresariales con ingresos a través de Plataformas Tecnológicas";
								}else if(regimenReceptor.equals("626")){
									regimenReceptorTxt = "Régimen Simplificado de Confianza";
								}else if(regimenReceptor.equals("628")){
									regimenReceptorTxt = "Hidrocarburos";
								}else if(regimenReceptor.equals("629")){
									regimenReceptorTxt = "De los Regímenes Fiscales Preferentes y de las Empresas Multinacionales";
								}else if(regimenReceptor.equals("630")){
									regimenReceptorTxt = "Enajenación de acciones en bolsa de valores";
								}
							}
						}
						parametros.put("regimenReceptor", regimenReceptorTxt); // Nuevo del CFDI 4.0
						// NOTA: ESTAS 3 LINEAS SIEMPRE TIENEN QUE IR
						List<PartidaCotizacion> list = new ArrayList<PartidaCotizacion>();
						list.add(new PartidaCotizacion());
						JRDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(list, true);

						File file = new File(rutaEtiqueta);
						if (!file.exists())
							file.mkdirs();

						JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros,beanCollectionDataSource);
						JasperExportManager.exportReportToPdfFile(jasperPrint,rutaFacturas + Fpq.getFolio() + ".pdf");
						facturacionDAO.actualizarPDFGenerado(facturaTmp);

						inputStream.close();
						Runtime.getRuntime().gc();

					} catch(Exception e){
						e.printStackTrace();
						log.info("NO se encontro el documento XML");

					}
				}
			} else {
				log.info("No hay facturas pendientes por generar !! ");
			}

			log.info("Facturas generadas correctamente .. !! ");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean crearCodigo(String codigo) throws ProquifaNetException {
		try {
			Funcion funcion = new Funcion();
			// Esto es para código Code_128
			// BitMatrix matrix;
			// matrix = new Code128Writer().encode(codigo,
			// BarcodeFormat.CODE_128, 0, 47, null);

			QRCodeWriter writer = new QRCodeWriter();
			BitMatrix matrix = writer.encode(codigo, com.google.zxing.BarcodeFormat.QR_CODE, 360, 360);

			BufferedImage image = new BufferedImage(matrix.getWidth(), matrix.getHeight(), BufferedImage.TYPE_INT_RGB);
			image.createGraphics();
			String rutaGuardar = funcion.obtenerRutaServidor("plantillafactura", "");

			Graphics2D graphics = (Graphics2D) image.getGraphics();
			graphics.setColor(Color.WHITE);
			graphics.fillRect(0, 0, matrix.getWidth(), matrix.getHeight());
			graphics.setColor(Color.BLACK);

			for (int i = 0; i < matrix.getWidth(); i++) {
				for (int j = 0; j < matrix.getHeight(); j++) {
					if (matrix.get(i, j)) {
						graphics.fillRect(i, j, 1, 1);
					}
				}
			}

			File file = new File(rutaGuardar);
			if (!file.exists())
				file.mkdirs();
			file = new File(rutaGuardar + "codigoBarras.png");
			ImageIO.write(image, "png", file);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
