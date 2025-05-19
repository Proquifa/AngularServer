package com.proquifa.net.negocio.cobrosypagos.facturista.impl;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import net.sf.jasperreports.engine.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.proquifa.net.modelo.catalogos.cobrosypagos.facturista.ComplementoPago;
import com.proquifa.net.modelo.cobrosypagos.facturista.Factura;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.negocio.cobrosypagos.facturista.ComplementoPagoService;
import com.proquifa.net.persistencia.cobrosypagos.facturista.FacturacionDAO;

import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@Service("complementoPagoService")
public class ComplementoPagoServiceImpl  implements ComplementoPagoService{
	@Autowired
	FacturacionDAO facturacionDAO;
	
	final Logger log = LoggerFactory.getLogger(ComplementoPagoServiceImpl.class);
	
	@Override
	public boolean generarComplemento(String id) throws ProquifaNetException{
		try {
			StringBuilder datosDigitales = new StringBuilder();
			String Qr;
			String logo = "";
			Factura datosPrin = facturacionDAO.obtenerDatosCliente(id);
			List<ComplementoPago> listaPagos = facturacionDAO.pagosComplemento(id);
			Map<String, Object> lst = facturacionDAO.obtenerListasComplemento(id);
			Funcion funcion = new Funcion();
			String aliasEmpresa = datosPrin.getFacturarA();
			String rutaGeneral = funcion.obtenerRutaServidor("complemento_pago","");
			String ruta = rutaGeneral + "Complemento.jrxml";
			String rutaPDF = funcion.obtenerRutaServidor("complementoPago", aliasEmpresa);
			File file = new File(rutaPDF);
			log.info("Se crea la carpeta");
			if (!file.exists()) {
				file.mkdirs();
			}
			
			JasperDesign jasperDesign2 = JRXmlLoader.load(rutaGeneral + "PagosComplemento.jrxml");
            JasperReport jasperReport2 = JasperCompileManager.compileReport(jasperDesign2);
            JRSaver.saveObject(jasperReport2, rutaGeneral + "PagosComplemento" + ".jasper");
          
          InputStream inputStream = new FileInputStream(ruta);
          JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
          JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
          
			logo = obtenerLogo(datosPrin.getRfc_FPor());
			String fe= datosPrin.getCondicionesPago().substring((datosPrin.getCondicionesPago().length()-8), datosPrin.getCondicionesPago().length());
			Qr = "https://verificacfdi.facturaelectronica.sat.gob.mx/default.aspx?id="+ datosPrin.getUuid() + "&re=" + datosPrin.getRfc_FPor()
			+ "&rr=" + datosPrin.getRfc_Cliente() + "&tt="+ datosPrin.getMedioPago() + "&fe=" + fe;
			String imagenQr = crearCodigo(Qr);
			datosDigitales.append("NÚMERO DE SERIE DEL CSD DEL EMISOR:\n");
			datosDigitales.append("SELLO DIGITAL DEL SAT");
			String total = funcion.formatoMoneda(Double.parseDouble(datosPrin.getNivelIngresocliente()));
			Double sub = (double)datosPrin.getImporte();
			String subTotal = funcion.formatoMoneda(sub);
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("rutaSubReporte", rutaGeneral + "PagosComplemento.jasper");
			param.put("listaPagos", listaPagos);
			param.put("listaRefBancarias", lst.get("lstProd"));
			param.put("ruta", rutaGeneral);
			param.put("rutaLogo", rutaGeneral + logo);
			param.put("claveRfc", "FFFF");
			param.put("rfcReceptor", datosPrin.getRfc_Cliente());
			param.put("serie", datosPrin.getSerie());
			param.put("lugarExpedicion", datosPrin.getContraRecibo());
			param.put("folio", datosPrin.getFolioEntrega());
			param.put("nombreEmisor", datosPrin.getRazonSocialFPor());
			param.put("rfcEmisor", datosPrin.getRfc_FPor());
			param.put("regimenFiscal", datosPrin.getCpedido());
			param.put("usoCfdi", datosPrin.getEstado());
			param.put("version", datosPrin.getMedio());
			param.put("cliente",datosPrin.getCobrador());
			param.put("folioFiscal", datosPrin.getUuid());
			param.put("rutaQr", imagenQr);
			param.put("datosDigitales", datosDigitales.toString());
			param.put("serieSat", datosPrin.getFolioPC());
			param.put("serieCSD", datosPrin.getEsac());
			param.put("selloSat", datosPrin.getIdioma());
			param.put("selloCFDI", datosPrin.getTipo());
			param.put("certificacionDigital", datosPrin.getObservaciones());
			param.put("subTotal", subTotal);
			param.put("total", total);
			param.put("moneda", datosPrin.getMoneda());
			param.put("totalTexto", datosPrin.getMontoConLetra());
			param.put("fechaExpedicion", datosPrin.getEntregarEn());
			param.put("datosCertificacion", datosPrin.getMedioEnvio());
			param.put("tipoComprobante", datosPrin.getPuntoEntrega());
			param.put("tipoRelacion", datosPrin.getTipoRelacion());
			param.put("UUID", datosPrin.getRelacionUUID());
			param.put("CP",datosPrin.getCP());
			param.put("RegimenFiscal",obtenerRegimenFiscalTxt(datosPrin.getRegimenFiscal()));

			//
//			Generación de PDF
			rutaPDF += datosPrin.getFolioEntrega() + ".pdf";
			log.info("GENERAR COMPLEMENTO: Se terminó de llenar los datos ");

			log.info("Complemento: Ya se environ los datos a Jasper");
			try {
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, param, new JREmptyDataSource());
				JasperExportManager.exportReportToPdfFile(jasperPrint, rutaPDF);
			}catch(JRException jre){
				//System.out.println("nohayerrores, entiende SARA!!!!!!!!"+jre);

			}
			log.info("Complemento: Se genero el pdf y se guardo");
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	public String obtenerRegimenFiscalTxt(String regimenFiscal){
		String regimenReceptorTxt=null;
		if(regimenFiscal != null){

			if(regimenFiscal.equals("601")){
				regimenReceptorTxt = "General de ley Personas Morales";
			} else if(regimenFiscal.equals("603")){
				regimenReceptorTxt = "Personas Morales con Fines no Lucrativos";
			}else if(regimenFiscal.equals("605")){
				regimenReceptorTxt = "Sueldos y Salarios e Ingresos Asimilados a Salarios";
			}else if(regimenFiscal.equals("606")){
				regimenReceptorTxt = "Arrendamiento";
			}else if(regimenFiscal.equals("607")){
				regimenReceptorTxt = "Régimen de Enajenación o Adquisición de Bienes";
			}else if(regimenFiscal.equals("608")){
				regimenReceptorTxt = "Demás ingresos";
			}else if(regimenFiscal.equals("609")){
				regimenReceptorTxt = "Consolidación";
			}else if(regimenFiscal.equals("610")){
				regimenReceptorTxt = "Residentes en el Extranjero sin Establecimiento Permanente en México";
			}else if(regimenFiscal.equals("611")){
				regimenReceptorTxt = "Ingresos por Dividendos (socios y accionistas)";
			}else if(regimenFiscal.equals("612")){
				regimenReceptorTxt = "Personas Físicas con Actividades Empresariales y Profesionales";
			}else if(regimenFiscal.equals("614")){
				regimenReceptorTxt = "Ingresos por intereses";
			}else if(regimenFiscal.equals("615")){
				regimenReceptorTxt = "Régimen de los ingresos por obtención de premios";
			}else if(regimenFiscal.equals("616")){
				regimenReceptorTxt = "Sin obligaciones fiscales";
			}else if(regimenFiscal.equals("620")){
				regimenReceptorTxt = "Sociedades Cooperativas de Producción que optan por diferir sus ingresos";
			}else if(regimenFiscal.equals("621")){
				regimenReceptorTxt = "Incorporación Fiscal";
			}else if(regimenFiscal.equals("622")){
				regimenReceptorTxt = "Actividades Agrícolas, Ganaderas, Silvícolas y Pesqueras";
			}else if(regimenFiscal.equals("623")){
				regimenReceptorTxt = "Opcional para Grupos de Sociedades";
			}else if(regimenFiscal.equals("624")){
				regimenReceptorTxt = "Coordinados";
			}else if(regimenFiscal.equals("625")){
				regimenReceptorTxt = "Régimen de las Actividades Empresariales con ingresos a través de Plataformas Tecnológicas";
			}else if(regimenFiscal.equals("626")){
				regimenReceptorTxt = "Régimen Simplificado de Confianza";
			}else if(regimenFiscal.equals("628")){
				regimenReceptorTxt = "Hidrocarburos";
			}else if(regimenFiscal.equals("629")){
				regimenReceptorTxt = "De los Regímenes Fiscales Preferentes y de las Empresas Multinacionales";
			}else if(regimenFiscal.equals("630")){
				regimenReceptorTxt = "Enajenación de acciones en bolsa de valores";
			}

		}
		return regimenReceptorTxt;
	}
	public String crearCodigo(String codigo) throws ProquifaNetException {
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

			File tempFile = File.createTempFile("miFichero", ".png");
			ImageIO.write(image, "png", tempFile);
//			log.info("=====Soy path====");
//			log.info(tempFile.getAbsolutePath());	
			return tempFile.getAbsolutePath();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public boolean generarNotaCredito(String idFactura) throws ProquifaNetException{
		try {
			Factura datosGenerales = facturacionDAO.obtenerDatosCliente(idFactura);
			Map<String, Object> lst = facturacionDAO.obtenerListasComplemento(idFactura);
			Funcion funcion = new Funcion();
			String aliasEmpresa = datosGenerales.getFacturarA();
			String rutaGeneral = funcion.obtenerRutaServidor("jaspernotacredito","");
			String ruta = rutaGeneral + "NotaDeCredito.jasper";
			String rutaPDF = funcion.obtenerRutaServidor("notacredito",aliasEmpresa);
			File file = new File(rutaPDF);
			if (!file.exists()) {
				file.mkdirs();
			}
			Double sub = (double)datosGenerales.getImporte();
			String fe= datosGenerales.getCondicionesPago().substring((datosGenerales.getCondicionesPago().length()-8), datosGenerales.getCondicionesPago().length());
			String Qr = "https://verificacfdi.facturaelectronica.sat.gob.mx/default.aspx?id="+ datosGenerales.getUuid() + "&re=" + datosGenerales.getRfc_FPor()
			+ "&rr=" + datosGenerales.getRfc_Cliente() + "&tt="+ datosGenerales.getMedioPago() + "&fe=" + fe;
			String imagenQr = crearCodigo(Qr);
			StringBuilder datosDig = new StringBuilder();
			datosDig.append("NÚMERO DE SERIE DEL CERTIFICADO DEL SAT :").append(datosGenerales.getFolioPC());
			datosDig.append("NÚMERO DE SERIE DEL CSD DEL EMISOR :").append(datosGenerales.getEsac());
			datosDig.append("SELLO DIGITAL DEL SAT: ").append(datosGenerales.getIdioma());
			datosDig.append("SELLO DIGITAL DEL CFDI:").append(datosGenerales.getTipo());
			datosDig.append("CADENA ORIGINAL DEL COMPLEMENTO DE CERTIFICACIÓN DIGITAL DEL SAT:").append(datosGenerales.getObservaciones());

			Map<String, Object> datosJasper = new HashMap<String, Object>();
			datosJasper.put("rutaLogo", rutaGeneral + obtenerLogo(datosGenerales.getRfc_FPor()));
			datosJasper.put("rutaG", rutaGeneral);
			datosJasper.put("nombreEmisor", datosGenerales.getRazonSocialFPor());
			datosJasper.put("lugarExpedicion", datosGenerales.getContraRecibo());
			datosJasper.put("nombreReceptor",datosGenerales.getCobrador());
			datosJasper.put("rfcReceptor", datosGenerales.getRfc_Cliente());
			datosJasper.put("rfcEmisor", datosGenerales.getRfc_FPor());
			datosJasper.put("usoCfdi", datosGenerales.getEstado());
			datosJasper.put("serie", datosGenerales.getSerie());
			datosJasper.put("folio", datosGenerales.getFolioEntrega());
			datosJasper.put("versionNota", datosGenerales.getMedio());
			datosJasper.put("regimenFiscal", datosGenerales.getCpedido());
			datosJasper.put("tipoComprobante", datosGenerales.getPuntoEntrega());
			datosJasper.put("folioFiscal", datosGenerales.getUuid());
			datosJasper.put("rutaQr", imagenQr);
			datosJasper.put("fechaExpedicion", datosGenerales.getEntregarEn());
			datosJasper.put("fechaCertificacion", datosGenerales.getMedioEnvio());
			datosJasper.put("formaPago", datosGenerales.getTipoPago());
			datosJasper.put("metodoPago", datosGenerales.getMetodoPago());
			datosJasper.put("condicionesPago", datosGenerales.getOrdenCompra());
			datosJasper.put("tipoRelacion", datosGenerales.getTipoRelacion());
			datosJasper.put("UUID", datosGenerales.getRelacionUUID());
			datosJasper.put("datosDigitales", datosDig.toString());
			datosJasper.put("moneda", datosGenerales.getMoneda());
			datosJasper.put("totalLetra", datosGenerales.getMontoConLetra());
			datosJasper.put("listaPartidas", lst.get("lstProd"));
			datosJasper.put("subTotal", funcion.formatoMoneda(sub));
			datosJasper.put("total", funcion.formatoMoneda(Double.parseDouble(datosGenerales.getNivelIngresocliente())));
			datosJasper.put("tipoCambio", datosGenerales.getTipoCambioF());
			datosJasper.put("impuestoTrasladado", funcion.formatoMoneda(Double.parseDouble(datosGenerales.getTotalTraslados())));
			datosJasper.put("tipoImpuesto", datosGenerales.getTipoImpuesto());
			datosJasper.put("tasa", datosGenerales.getTasa());
			datosJasper.put("tipoFactor", datosGenerales.getTipoFactor());
//			Generación de PDF
			rutaPDF += datosGenerales.getFolioEntrega() + ".pdf";
			FileInputStream jasperNotaPDF = new FileInputStream(ruta);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperNotaPDF, datosJasper, new JREmptyDataSource());
			JasperExportManager.exportReportToPdfFile( jasperPrint, rutaPDF);
			return true;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
	
	public String obtenerLogo(String rfc) throws ProquifaNetException{
		try {
			String logo = "";
			if (rfc.equalsIgnoreCase("GOL120717DJ7")) {
				logo = "golocaer.png";
			} else if (rfc.equalsIgnoreCase("MUN1204264I5")) {
				logo = "Mungen.png";
			}else if (rfc.equalsIgnoreCase("RYN120426HH6")) {
				logo = "logo_ryndem.svg";
			} else {
				logo = "Proquifa.jpg";
			}
			return logo;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
}
