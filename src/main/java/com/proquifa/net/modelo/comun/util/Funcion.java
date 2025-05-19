package com.proquifa.net.modelo.comun.util;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.imageio.ImageIO;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.xmlbeans.impl.tool.Extension.Param;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;
import com.proquifa.net.modelo.comun.ExepcionEnvio;
import com.proquifa.net.modelo.comun.Horario;
import com.proquifa.net.modelo.comun.NivelIngreso;
import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.Producto;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.documentoXls.CatalogoUSP;
import com.proquifa.net.modelo.cuentaContable.CuentaContable;
import com.proquifa.net.modelo.tableros.comun.DatosCompLineal;
import com.proquifa.net.modelo.ventas.admoncomunicacion.Correo;
import com.proquifa.net.modelo.ventas.admoncomunicacion.MiAutenticador;
import com.proquifa.net.resource.despachos.mensajero.MensajeroResoutce;
import com.sun.mail.util.BASE64DecoderStream;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;


public class Funcion {

	public static final boolean PRODUCCION = true;
	//NUEVA VARIABLE PARA FACTURACION 4.0//|
	public static final boolean FACTURACION_4 = true;
	//
	final static org.slf4j.Logger log = LoggerFactory.getLogger(Funcion.class);

	/************* PRODUCCION ******************/

	public static final String RUTA_DOCUMENTOS ="/mnt/SAP/";
	static final String RUTA_LOGOS = "/mnt/SAP/";
	public static final String RUTA_FIRMAS = "/mnt/SAP/Firmas_SAC/";
	public static final String RUTA_FIRMASDIGITALES = "/mnt/SAP/Imagenes/FirmasDigitales/";
	static final String RUTA_DOCUMENTOS_ftp = "/";
	static final String RUTA_FUENTE = "/mnt/SAP/Fuentes/";
	public static final String RUTA_DOCUMENTOS_ = "/mnt/SAP/";
	public static final String RUTA_MAIL_BOTS = "/mnt/SAP/Correos/attached";


	public static final String USUARIO_TURBOPAC ="RYNDEM";
	public static final String CONTRASENA_TURBOPAC ="D2A923DE6E15E5E7D4FEF0477F11E60A046B0504";

//	public static final String USUARIO_TURBOPAC ="test2";
//	public static final String CONTRASENA_TURBOPAC = "BB8C171B2DABFFE71D0854138B759FEE25483936"; // Contrseña para el 4.0
//	public static final String CONTRASENA_TURBOPAC ="76988887D99BE90534236A1441D5ED7AE9FE23CA"; // Contraseña de la primer versión

	//public static final String IP_SERVER = "http://proquifa.com.mx:51725/SAP/";

	public static final String IP_SERVER = "http://189.203.160.185:51725/SAP/";


	/************* Prueba Local******************/
/*
	public static final String RUTA_DOCUMENTOS = "/Users/glassfish4/glassfish/domains/domain1/docroot/SAP/";
	static final String RUTA_LOGOS = "/Users/Documents/glassfish4/glassfish/domains/domain1/docroot/";
	public static final String RUTA_FIRMAS = "/Volumes/Data/Firmas_SAC/";
	public static final String RUTA_FIRMASDIGITALES = "/Users/glassfish4/glassfish/domains/domain1/docroot/SAP/Imagenes/FirmasDigitales/";
	static final String RUTA_DOCUMENTOS_ftp = "/";
	static final String RUTA_FUENTE = "/Users/glassfish4/glassfish/domains/domain1/docroot/OrdenDespacho/font/";
	static final String RUTA_MAIL_BOTS = "/Users/glassfish4/glassfish/domains/domain1/docroot/Correos/attached";
	public static final String RUTA_DOCUMENTOS_ = "/Users/glassfish4/glassfish/domains/domain1/docroot/SAP/";

*/
	/************* DEV RYNDEM(192.168.2.41 tomcat) ******************/

/*
	public static final String RUTA_DOCUMENTOS = "/opt/glassfish4/glassfish/domains/domain1/docroot/SAP/";
	static final String RUTA_LOGOS = "/opt/glassfish4/glassfish/domains/domain1/docroot/";
	public static final String RUTA_FIRMAS = "/opt/glassfish4/glassfish/domains/domain1/docroot";
	public static final String RUTA_FIRMASDIGITALES = "/opt/glassfish4/glassfish/domains/domain1/docroot/SAP/Imagenes/FirmasDigitales/";
	static final String RUTA_DOCUMENTOS_ftp = "/";
	static final String RUTA_FUENTE = "/opt/glassfish4/glassfish/domains/domain1/docroot/OrdenDespacho/font/";
	static final String RUTA_MAIL_BOTS = "/opt/glassfish4/glassfish/domains/domain1/docroot/Correos/attached";
	public static final String RUTA_DOCUMENTOS_ = "/opt/glassfish4/glassfish/domains/domain1/docroot/SAP/";
*/
	/************* UAT RYNDEM( tomcat) ******************/
/*

	public static final String RUTA_DOCUMENTOS = "/opt/docroot/SAP/";
	static final String RUTA_LOGOS = "/opt/docroot/";
	public static final String RUTA_FIRMAS = "/opt/docroot";
	public static final String RUTA_FIRMASDIGITALES = "/opt/docroot/SAP/Imagenes/FirmasDigitales/";
	static final String RUTA_DOCUMENTOS_ftp = "/";
	static final String RUTA_FUENTE = "/opt/docroot/OrdenDespacho/font/";
	static final String RUTA_MAIL_BOTS = "/opt/docroot/Correos/attached";
	public static final String RUTA_DOCUMENTOS_ = "/opt/docroot/SAP/";
*/


	@Autowired
	private JavaMailSender sender;

	static final long MILLISECS_PER_DAY = 1000L * 60L * 60L * 24L;
	static final String HOST = "mail.proquifa.net";
	public static final String DOMINIO = "@proquifa.net";
	private MiAutenticador authenticator;
	private Session mailSession;
	static final Double montosOperativo[] = { 1913.8, 3827.6, 5741.4, 7655.2,
			9569.0, 11482.8, 13396.6, 15310.4, 17224.2, 19138.0 };
	static final Double montosGerente[] = { 11550.0, 23100.0, 34650.0, 46200.0,
			57750.0, 69300.0, 80850.0, 92400.0, 103950.0, 115500.0 };
	static final Double montosDirectivo[] = { 24000.0, 48000.0, 72000.0,
			96000.0, 120000.0, 144000.0, 168000.0, 192000.0, 216000.0, 240000.0 };
	private final String[] UNIDADES = {"", "un ", "dos ", "tres ", "cuatro ", "cinco ", "seis ", "siete ", "ocho ", "nueve "};
	private final String[] DECENAS = {"diez ", "once ", "doce ", "trece ", "catorce ", "quince ", "dieciseis ",
			"diecisiete ", "dieciocho ", "diecinueve", "veinte ", "treinta ", "cuarenta ",
			"cincuenta ", "sesenta ", "setenta ", "ochenta ", "noventa "};
	private final String[] CENTENAS = {"", "ciento ", "doscientos ", "trecientos ", "cuatrocientos ", "quinientos ", "seiscientos ",
			"setecientos ", "ochocientos ", "novecientos "};

	String server = "mail.proquifa.com.mx";
	int port = 21;
	String user = "sap";
	String pass = "cambiame";


	private final static Logger logger = Logger
			.getLogger(com.proquifa.net.modelo.comun.util.Funcion.class
					.getName());
	/*
	 * (non-Javadoc)
	 *
	 * @seemx.com.proquifa.proquifanet.negocio.comun.util.Funcion#
	 * obtenerRutaCompletaDocumento(java.lang.String)
	 */

	public static int regresaDiferenciaEntreFechasEnDiasRestandoFines(Date fechaMasActual, Date fechaMasLejana ){
		int diasHabiles = 0;
		if(fechaMasActual == null || fechaMasLejana == null){
			return diasHabiles;
		}
		int diferenciaDiasConFines = 0;
		int diaSemana = fechaMasLejana.getDay();  /*se obtiene el dia de la semana  de la segunda fecha, como se pretende que es la fecha2 sea la mas lejana apartir de ese dia se empieza a contar*/
		diferenciaDiasConFines = (int) (( fechaMasActual.getTime() - fechaMasLejana.getTime() ) / ( 1000 * 60 * 60 * 24 )); /*se obtiene la diferencia de los en bruto contando sabados y domingos*/
		for(int i = 0; i < diferenciaDiasConFines; i++ ){ //se hace un recorrido de los dias en bruto, sumando cada vez un dia a los dias habiles, validando que
			//si el dia es 7 como tope maximo, el contador se regresa a 0 y la segunda validacion es para que, el dia  6 (sabado) o 0 (domingo)  no sean contados
			if (diaSemana == 7) {
				diaSemana = 0;
			}
			if ( diaSemana != 6 && diaSemana != 0 ) {
				diasHabiles++;
			}
			diaSemana++;
		}
		return diasHabiles;
	}
	public static int regresaDiferenciaEntreFechasEnDias(Date fechaMasPequenia, Date fechaMasGrande){
		int diferenciaDias = 0;
		if(fechaMasPequenia == null || fechaMasGrande == null){
			return diferenciaDias;
		}
		diferenciaDias = (int) ((fechaMasPequenia.getTime()-fechaMasGrande.getTime())/(1000*60*60*24));
		return -diferenciaDias;
	}


	public String getRutaDocumentos(){
		return RUTA_DOCUMENTOS;
	}

	public Boolean subirArchivoFTP(String localFile, String remoteFile) throws ProquifaNetException{
		try{
			FTPClient ftpClient = new FTPClient();
			ftpClient.connect(server, port);
			ftpClient.login(user, pass);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

			File firstLocalFile = new File(localFile);

			InputStream inputStream = new FileInputStream(firstLocalFile);

			boolean done = ftpClient.storeFile(remoteFile, inputStream);
			inputStream.close();

			ftpClient.sendSiteCommand("chmod " + "777 " + remoteFile);

			if (done) {
				//                 logger.info("The first file is uploaded successfully. Prueba" );
			}

			return true;
		}catch(Exception e){
			//    		logger.info(e.getMessage());
			return false;
		}



	}
	public Boolean subirArchivoSFTP(String localFile, String remoteFile) throws ProquifaNetException{
		String SFTPHOST = "201.160.106.178";
		int    SFTPPORT = 22;
		String SFTPUSER = "sap";
		String SFTPPASS = "brEZu3h3";

		String SFTPWORKINGDIR = remoteFile;

		String FILETOTRANSFER = localFile;


		com.jcraft.jsch.Session     session     = null;
		Channel     channel     = null;
		ChannelSftp channelSftp = null;

		try{
			JSch jsch = new JSch();
			session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);//(SFTPUSER,SFTPHOST,SFTPPORT);
			session.setPassword(SFTPPASS);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			channel = session.openChannel("sftp");
			channel.connect();
			channelSftp = (ChannelSftp)channel;
			channelSftp.cd(SFTPWORKINGDIR);
			File f = new File(FILETOTRANSFER);
			channelSftp.put(new FileInputStream(f), f.getName());
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}


		//		- See more at: http://kodehelp.com/java-program-for-uploading-file-to-sftp-server/#sthash.pq5xkrKB.dpuf
		return true;



	}

	public String obtenerRutaCompletaDocumento(String documento,
											   String tipoCorreo, String fPor) throws ProquifaNetException {
		String ubicacion = "";
		if (documento.equals("Aviso clientes Microbiologics")
				|| documento.equals("Carta fin de anio Proquifa")
				|| documento.equals("Carta Controlados Nacionales")
				|| documento.equals("Carta_caducidad_MBL")
				|| documento.equals("Carta_Cierre_2013")
				|| documento.equals("Aviso_cursoUSP2012")
				|| documento.equals("USP_37")
		) {
			ubicacion = RUTA_DOCUMENTOS + "Cartas/" + documento + ".pdf";
		} else {
			if (tipoCorreo.toLowerCase().equals("notificaciones")) {
				if (documento.substring(0, 2).equals("NT")) {
					ubicacion = RUTA_DOCUMENTOS + "AvisoDeCambios/"
							+ documento + ".pdf";
				} else if (documento.length() >= 11) {
					ubicacion = RUTA_DOCUMENTOS + "Pedidos/" + documento + ".pdf";
				} else {
					ubicacion = RUTA_DOCUMENTOS + "Doctos/" + documento + ".pdf";
				}
			} else if (tipoCorreo.toLowerCase().equals("pedidos")) {
				ubicacion = RUTA_DOCUMENTOS + "Pedidos/" + documento + ".pdf";
			} else if (tipoCorreo.toLowerCase().equals("cotizaciones")) {
				ubicacion = RUTA_DOCUMENTOS + "Cotizaciones/" + documento+ ".pdf";
			} else if (tipoCorreo.toLowerCase().equals("compras")) {
				documento = documento + "-P";
				ubicacion = RUTA_DOCUMENTOS + "OrdenesDeCompra/" + documento+ ".pdf";
			} else if (tipoCorreo.toLowerCase().equals("facturas")) {
				ubicacion = RUTA_DOCUMENTOS + "Facturas/" + fPor + "/" + documento + ".pdf";
			} else if (tipoCorreo.toLowerCase().equals("documentorecibido")) {
				ubicacion = RUTA_DOCUMENTOS + "Doctos/" + documento + ".pdf";
			} else if(tipoCorreo.toLowerCase().equals("requisicionphs")){
				ubicacion = RUTA_DOCUMENTOS + "RequisicionPhs/" + documento + ".pdf";
			}else if(tipoCorreo.toLowerCase().equals("confirmacionentrega")){
				ubicacion = RUTA_DOCUMENTOS + "ConfirmacionEntrega/" + documento + ".pdf";
			}else if (tipoCorreo.toLowerCase().equals("catalogousp")){
				ubicacion  = RUTA_DOCUMENTOS + "Doctos/catalogoUSP/" + documento ;
			}else if (tipoCorreo.toLowerCase().equals("catalogoep")){
				ubicacion  = RUTA_DOCUMENTOS + "Doctos/catalogoEP/" + documento ;
			}else if (tipoCorreo.toLowerCase().equals("evento")){
				ubicacion  = RUTA_LOGOS + "FoliosEventos/" + documento ;
			}else if (tipoCorreo.toLowerCase().equals("reportepartidasft")){
				ubicacion  = RUTA_DOCUMENTOS + "ReportesPartidasFT/" + documento;
			}else if (tipoCorreo.toLowerCase().equals("facturasproveedor")){
				ubicacion  = RUTA_DOCUMENTOS + "Facturas/Proveedor/" + documento + ".pdf";
			}else if (tipoCorreo.toLowerCase().equals("facturasproveedorotro")){
				ubicacion  = RUTA_DOCUMENTOS + "Facturas/Proveedor/Otros/" + documento;
			}else if (tipoCorreo.toLowerCase().equals("pagos")){
				ubicacion  = RUTA_DOCUMENTOS + "Pagos/" + documento + ".pdf";
			}else if (tipoCorreo.toLowerCase().equals("local")){
				ubicacion  = "/Users/ocardona/Pictures/Mailing/EncuestaUSP/" + documento;
			}else if (tipoCorreo.toLowerCase().equals("ordendespachotempotal")){
				ubicacion  = RUTA_DOCUMENTOS + "OrdenDespacho/Temporal/" + documento;
			}else if (tipoCorreo.toLowerCase().equals("pconnectimagencorreovalidado")){
				ubicacion  = RUTA_DOCUMENTOS + "Imagenes/PConnect/CorreoConfirmacion/" + documento;
			}else if (tipoCorreo.toLowerCase().equals("ordendespacho")){
				ubicacion  = RUTA_DOCUMENTOS +"OrdenDespacho/";
			}else if (tipoCorreo.toLowerCase().equals("registrarvisita")){
				ubicacion  = RUTA_DOCUMENTOS + "OrdenDespacho/Visitas/";
			}else if (tipoCorreo.toLowerCase().equals("cartasuso")){
				ubicacion  = RUTA_DOCUMENTOS + "JasperReports/Compras/PlantillasCartaUso/";
			}else if (tipoCorreo.toLowerCase().equals("avisodecambios")){
				ubicacion  = RUTA_DOCUMENTOS + "AvisoDeCambios/" + documento;
			}else if (tipoCorreo.toLowerCase().equals("solicitudservicios")){
				ubicacion  = RUTA_DOCUMENTOS + "DocumentoServicios/"+ fPor + "/" + documento;
			}else if (tipoCorreo.toLowerCase().equals("correonormal")){
				ubicacion  = RUTA_DOCUMENTOS + "DocumentosCorreoTemporales/"+fPor + "/"+ documento;

			} else if (tipoCorreo.toLowerCase().equals("mailbot")) {
				ubicacion = RUTA_MAIL_BOTS + documento;
			} else if (tipoCorreo.toLowerCase().equals("ups")) {
				ubicacion = RUTA_DOCUMENTOS + "DoctosCierre/RT/";
			} else if (tipoCorreo.toLowerCase().equals("fedex")) {
				ubicacion = RUTA_DOCUMENTOS + "DoctosCierre/RT/";
			} else if (tipoCorreo.toLowerCase().equals("paqueteria")) {
				ubicacion = RUTA_DOCUMENTOS + "DoctosCierre/RT/";
			} else if (tipoCorreo.toLowerCase().equals("guias")) {
				ubicacion = RUTA_DOCUMENTOS + "Guias/";
			}else if (tipoCorreo.toLowerCase().equals("almacen")) {
				ubicacion = RUTA_DOCUMENTOS + "Almacen/";
			} else if (tipoCorreo.toLowerCase().equals("productopap")) {
				ubicacion = RUTA_DOCUMENTOS + "PermisoAquisicion";
			} else if (tipoCorreo.toLowerCase().equals("evidenciafactura")) {
				ubicacion = RUTA_DOCUMENTOS + "OrdenDespacho/Evidencia/" + fPor;
			}else if (tipoCorreo.toLowerCase().equals("evidenciacorreofactura")) {
				ubicacion = RUTA_DOCUMENTOS + "OrdenDespacho/Evidencia/" + fPor + '/' + documento + ".pdf" ;
			} else if(tipoCorreo.toLowerCase().equals("deposito")) {
				ubicacion = RUTA_DOCUMENTOS + "Depositos";
			} else if(tipoCorreo.toLowerCase().equals("csvasignarmensajero")) {
				ubicacion = "/tmp/" + documento;
			}else if (tipoCorreo.toLowerCase().equals("producto_reclamo")) {
				ubicacion = RUTA_DOCUMENTOS_ + "Reclamos/" + documento + ".pdf";
			}else if (tipoCorreo.toLowerCase().equals("saldoafavor")) {
				ubicacion = RUTA_DOCUMENTOS_ + "SaldoAFavor/";
			}else if(tipoCorreo.toLowerCase().equals("csvreportecomercial")) {
				ubicacion = "/tmp/" + documento;
				//ubicacion = "C:\\Users\\Fernando Betanzos\\Downloads\\" + documento;
			}
			else {

				ubicacion = documento;
			}
		}
		if (ubicacion.equals("")) {
			throw new ProquifaNetException(
					"No existe ubicacion para ese tipo de archivo");
		}
		return ubicacion;
	}
	public String obtenerRutaCompletaDocumentoFTP(String documento,
												  String tipoCorreo, String fPor) throws ProquifaNetException {
		String ubicacion = "";
		if (documento.equals("Aviso clientes Microbiologics")
				|| documento.equals("Carta fin de anio Proquifa")
				|| documento.equals("Carta Controlados Nacionales")
				|| documento.equals("Carta_caducidad_MBL")
				|| documento.equals("Carta_Cierre_2013")
				|| documento.equals("Aviso_cursoUSP2012")
				|| documento.equals("USP_37")
		) {
			ubicacion = RUTA_DOCUMENTOS_ftp + "Cartas/" + documento + ".pdf";
		} else {
			if (tipoCorreo.toLowerCase().equals("notificaciones")) {
				if (documento.substring(0, 2).equals("NT")) {
					ubicacion = RUTA_DOCUMENTOS_ftp + "AvisoDeCambios/"
							+ documento + ".pdf";
				} else if (documento.length() >= 11) {
					ubicacion = RUTA_DOCUMENTOS_ftp + "Pedidos/" + documento + ".pdf";
				} else {
					ubicacion = RUTA_DOCUMENTOS_ftp + "Doctos/" + documento + ".pdf";
				}
			} else if (tipoCorreo.toLowerCase().equals("pedidos")) {
				ubicacion = RUTA_DOCUMENTOS_ftp + "Pedidos/" + documento + ".pdf";
			} else if (tipoCorreo.toLowerCase().equals("cotizaciones")) {
				ubicacion = RUTA_DOCUMENTOS_ftp + "Cotizaciones/" + documento+ ".pdf";
			} else if (tipoCorreo.toLowerCase().equals("compras")) {
				documento = documento + "-P";
				ubicacion = RUTA_DOCUMENTOS_ftp + "OrdenesDeCompra/" + documento+ ".pdf";
			} else if (tipoCorreo.toLowerCase().equals("facturas")) {
				ubicacion = RUTA_DOCUMENTOS_ftp + "Facturas/" + fPor + "/" + documento + ".pdf";
			} else if (tipoCorreo.toLowerCase().equals("documentorecibido")) {
				ubicacion = RUTA_DOCUMENTOS_ftp + "Doctos/" + documento + ".pdf";
			} else if(tipoCorreo.toLowerCase().equals("requisicionphs")){
				ubicacion = RUTA_DOCUMENTOS_ftp + "RequisicionPhs/" + documento + ".pdf";
			}else if(tipoCorreo.toLowerCase().equals("confirmacionentrega")){
				ubicacion = RUTA_DOCUMENTOS_ftp + "ConfirmacionEntrega/" + documento + ".pdf";
			}
		}
		if (ubicacion.equals("")) {
			throw new ProquifaNetException(
					"No existe ubicacion para ese tipo de archivo");
		}
		return ubicacion;
	}

	public String obtenerRutaServidor(String tipoArchivo, String subCarpeta)
			throws ProquifaNetException {
		String ubicacion = "";
		if (tipoArchivo.toLowerCase().equals("incidente")) {
			ubicacion = RUTA_DOCUMENTOS + "Incidentes/";
		}else if (tipoArchivo.equals("docInspeccion")) {
			ubicacion = RUTA_DOCUMENTOS + "InspeccionOC/";
		}else if(tipoArchivo.equals("docPieza")){
			ubicacion = RUTA_DOCUMENTOS + "InspeccionOC/InspeccionPiezas/";
		}else if(tipoArchivo.equals("imagenRechazo")){
			ubicacion = RUTA_DOCUMENTOS + "InspeccionOC/ImagenesRechazo/";
		}else if(tipoArchivo.equals("cncreal")){
			ubicacion = RUTA_DOCUMENTOS + "Incidentes/Ponderacion/";
		}else if(tipoArchivo.toLowerCase().equals("factura")){
			ubicacion = RUTA_DOCUMENTOS + "Facturas/"+ subCarpeta + "/";
		}else if(tipoArchivo.toLowerCase().equals("complementopago")){
			ubicacion = RUTA_DOCUMENTOS + "ComplementoPago/"+ subCarpeta + "/";
		}else if(tipoArchivo.toLowerCase().equals("notacredito")){
			ubicacion = RUTA_DOCUMENTOS + "NotaCredito/"+ subCarpeta + "/";
		}else if(tipoArchivo.toLowerCase().equals("nota_de_credito")){
			ubicacion = RUTA_DOCUMENTOS + "NotaCredito/"+ subCarpeta + "/";
		}else if(tipoArchivo.toLowerCase().equals("notacreditoproquifa")){
			ubicacion = RUTA_DOCUMENTOS + "NotaCredito/Proquifa/";
		}else if(tipoArchivo.toLowerCase().equals("notacreditoryndem")){
		}else if(tipoArchivo.toLowerCase().equals("notacreditoproveedora")){
			ubicacion = RUTA_DOCUMENTOS + "NotaCredito/Proveedora/";
		}else if(tipoArchivo.toLowerCase().equals("notacreditogolocaer")){
			ubicacion = RUTA_DOCUMENTOS + "NotaCredito/Golocaer/";
		}else if(tipoArchivo.toLowerCase().equals("notacreditomungen")){
			ubicacion = RUTA_DOCUMENTOS + "NotaCredito/Mungen/";
		}else if(tipoArchivo.toLowerCase().equals("notacreditopharma")){
			ubicacion = RUTA_DOCUMENTOS + "NotaCredito/Pharma/";
		}else if(tipoArchivo.toLowerCase().equals("notacreditormtrading")){
			ubicacion = RUTA_DOCUMENTOS + "NotaCredito/RMTrading/";
		}else if(tipoArchivo.toLowerCase().equals("solicitudvisita")){
			ubicacion = RUTA_DOCUMENTOS + "ReferenciasSolicitudVisita/";
		}else if(tipoArchivo.toLowerCase().equals("confirmacionentrega")){
			ubicacion = RUTA_DOCUMENTOS + "ConfirmacionEntrega/";
		}else if(tipoArchivo.toLowerCase().equals("solicitudti")){
			ubicacion = RUTA_DOCUMENTOS + "DireccionTI/"+ subCarpeta + "/";
		}else if(tipoArchivo.toLowerCase().equals("tareati")){
			ubicacion = RUTA_DOCUMENTOS + "DireccionTI/"+ subCarpeta + "/";
		}else if(tipoArchivo.toLowerCase().equals("imagenproveedor")){
			ubicacion = RUTA_LOGOS + "logoProveedores/";    								//OSCAR	O
		}else if(tipoArchivo.toLowerCase().equals("imagenmarca")){
			ubicacion = RUTA_LOGOS + "logoMarcas/";    										//OSCAR	O
		}else if(tipoArchivo.toLowerCase().equals("imagencliente")){
			ubicacion = RUTA_LOGOS + "logoClientes/";    									//OSCAR	O	
		}else if(tipoArchivo.toLowerCase().equals("imagenagente")){
			ubicacion = RUTA_LOGOS + "logoAgentes/";    									//OSCAR	O
		}else if (tipoArchivo.toLowerCase().equals("logosordendespacho")){
			ubicacion = RUTA_LOGOS + "OrdenDespacho/";
		}else if(tipoArchivo.toLowerCase().equals("jasperordenescompra")){
			ubicacion = RUTA_DOCUMENTOS + "JasperReports/Compras/OrdenCompra/"; 			//ALEX
		}else if(tipoArchivo.toLowerCase().equals("rutapdfordenescompra")){
			ubicacion = RUTA_DOCUMENTOS + "OrdenesDeCompra/";    							//ALEX
		}else if(tipoArchivo.toLowerCase().equals("doctoscierredc")){
			ubicacion = RUTA_DOCUMENTOS + "DoctosCierre/DC/";
		}else if(tipoArchivo.toLowerCase().equals("doctoscierrert")){
			ubicacion = RUTA_DOCUMENTOS + "DoctosCierre/RT/";
		}else if(tipoArchivo.toLowerCase().equals("doctoscierrertmac")){
			ubicacion = RUTA_DOCUMENTOS + "DoctosCierre/RT/";
		}else if(tipoArchivo.toLowerCase().equals("doctoscierrear")){
			ubicacion = RUTA_DOCUMENTOS + "DoctosCierre/AR/";
		}else if(tipoArchivo.toLowerCase().equals("catalogousp")){
			ubicacion = RUTA_DOCUMENTOS + "Doctos/catalogoUSP/";
		}else if(tipoArchivo.toLowerCase().equals("catalogoep")){
			ubicacion = RUTA_DOCUMENTOS + "Doctos/catalogoEP/";
		}else if(tipoArchivo.toLowerCase().equals("catalogomicrobiologics")){
			ubicacion = RUTA_DOCUMENTOS + "Doctos/catalogoMicro/";
		}else if (tipoArchivo.toLowerCase().equals("reportepartidasft")){
			ubicacion  = RUTA_DOCUMENTOS + "ReportesPartidasFT/";
		}else if (tipoArchivo.toLowerCase().equals("facturasproveedor")){
			ubicacion  = RUTA_DOCUMENTOS + "Facturas/Proveedor/";
		}else if (tipoArchivo.toLowerCase().equals("facturasproveedorotro")){
			ubicacion  = RUTA_DOCUMENTOS + "Facturas/Proveedor/Otros/";
		}else if (tipoArchivo.toLowerCase().equals("pagos")){
			ubicacion  = RUTA_DOCUMENTOS + "Pagos/";
		}else if (tipoArchivo.toLowerCase().equals("listaarribo")){
			ubicacion  = RUTA_DOCUMENTOS + "ListaArribo/";
		}else if (tipoArchivo.toLowerCase().equals("certificados")){
			ubicacion  = RUTA_DOCUMENTOS + "Certificados/"+ subCarpeta + "/";
		}else if (tipoArchivo.toLowerCase().equals("hojasseguridad")){
			ubicacion  = RUTA_DOCUMENTOS + "HojasSeguridad/"+ subCarpeta + "/";
		}else if (tipoArchivo.toLowerCase().equals("ordendespacho")){
			ubicacion  = RUTA_DOCUMENTOS + "OrdenDespacho/"+ subCarpeta.toUpperCase() + "/";
		}else if (tipoArchivo.toLowerCase().equals("ordendespachotempotal")){
			ubicacion  = RUTA_DOCUMENTOS + "OrdenDespacho/Temporal/";
		}else if (tipoArchivo.toLowerCase().equals("ordendespachoadicional")){
			ubicacion  = RUTA_DOCUMENTOS + "OrdenDespacho/Adicionales/";
		}else if (tipoArchivo.toLowerCase().equals("fuentes")){
			ubicacion  = RUTA_FUENTE;
		}else if (tipoArchivo.toLowerCase().equals("sellofiscal")){
			ubicacion  = RUTA_DOCUMENTOS + "SelloFiscal/";
		}else if (tipoArchivo.toLowerCase().equals("pharmaffiliatescatalogo")){
			ubicacion  = RUTA_DOCUMENTOS + "Proveedores/Pharmaffiliates/" + subCarpeta + "/";
		}else if (tipoArchivo.toLowerCase().equals("uspcatalogoerror")){
			ubicacion  = RUTA_DOCUMENTOS + "Proveedores/USP/CatalogoError/";
		}else if (tipoArchivo.toLowerCase().equals("pconnectimagencorreoValidado")){
			ubicacion  = RUTA_DOCUMENTOS + "Imagenes/PConnect/CorreoConfirmacion/";
		}else if (tipoArchivo.toLowerCase().equals("solicitudvisitadocumento")){
			ubicacion  = RUTA_DOCUMENTOS + "Visita/Solicitud/" + subCarpeta + "/";
		}else if (tipoArchivo.toLowerCase().equals("avisodecambios")){
			ubicacion  = RUTA_DOCUMENTOS + "AvisoDeCambios/";
		}else if (tipoArchivo.toLowerCase().equals("respuestaac")){
			ubicacion  = RUTA_DOCUMENTOS + "Respuesta Notificado/";
		}else if (tipoArchivo.toLowerCase().equals("plantillaavisodecambios")){
			ubicacion  = RUTA_DOCUMENTOS + "JasperReports/Compras/AvisoDeCambios/"+subCarpeta;
		}else if (tipoArchivo.toLowerCase().equals("plantillaconfirmarpedido")){
			ubicacion  = RUTA_DOCUMENTOS + "JasperReports/Compras/ConfirmarPedido/"+subCarpeta;
		}else if (tipoArchivo.toLowerCase().equals("pedidos")){
			ubicacion  = RUTA_DOCUMENTOS + "Pedidos/"+subCarpeta;
		}else if (tipoArchivo.toLowerCase().equals("contactos")){
			ubicacion  = RUTA_DOCUMENTOS + "Contactos/"+subCarpeta + "/";
		}else if (tipoArchivo.toLowerCase().equals("solicitudservicio")){
			ubicacion  = RUTA_DOCUMENTOS + "DocumentoServicios/"+subCarpeta + "/";
		}else if (tipoArchivo.toLowerCase().equals("documentotemporal")){
			ubicacion  = RUTA_DOCUMENTOS + "DocumentosCorreoTemporales/"+subCarpeta + "/";
		}else if (tipoArchivo.toLowerCase().equals("reportesvisitaclientes")){
			ubicacion  = RUTA_DOCUMENTOS + "Visita/DocumentoReporte/"+subCarpeta +"/";
		}else if (tipoArchivo.toLowerCase().equals("plantillacotizacion")){
			ubicacion  = RUTA_DOCUMENTOS + "JasperReports/Cotizacion/";
		}else if (tipoArchivo.toLowerCase().equals("cotizacion")) {
			ubicacion  = RUTA_DOCUMENTOS + "Cotizaciones/";
		}else if (tipoArchivo.toLowerCase().equals("contratos")) {
			ubicacion  = RUTA_DOCUMENTOS + "Contratos/";
		}else if (tipoArchivo.toLowerCase().equals("pendientes"))
			ubicacion  = RUTA_DOCUMENTOS + "Pendientes/" + subCarpeta +"/";
		else if (tipoArchivo.toLowerCase().equals("acciones"))
			ubicacion  = RUTA_DOCUMENTOS + "Acciones/" + subCarpeta +"/";
		else if (tipoArchivo.toLowerCase().equals("requerimientos"))
			ubicacion  = RUTA_DOCUMENTOS + "Visita/RespuestaSolicitud/";
		else if (tipoArchivo.toLowerCase().equals("hallazgos"))
			ubicacion  = RUTA_DOCUMENTOS + "Visita/Hallazgos/";
		else if (tipoArchivo.toLowerCase().equals("doctos"))
			ubicacion  = RUTA_DOCUMENTOS + "Doctos/";
		else if (tipoArchivo.toLowerCase().equals("plantillaetiquetas")){
			ubicacion  = RUTA_DOCUMENTOS + "JasperReports/Etiqueta/";
		}
		else if (tipoArchivo.toLowerCase().equals("etiqueta")) {
			ubicacion  = RUTA_DOCUMENTOS + "Etiqueta/";
		}
		else if (tipoArchivo.toLowerCase().equals("plantillafactura")) {
			ubicacion = RUTA_DOCUMENTOS + "JasperReports/FacturaPQF/";
		} else if (tipoArchivo.toLowerCase().equals("facturapqf")) {
			ubicacion = RUTA_DOCUMENTOS + "FacturaPQF/";
		}else if(tipoArchivo.equals("videoPartida")){
			ubicacion = RUTA_DOCUMENTOS + "InspeccionOC/videoPartida/";
		}

		else if (tipoArchivo.toLowerCase().equals("jasperordendespacho")) {
			ubicacion = RUTA_DOCUMENTOS + "OrdenDespacho/JasperResports/";
		} else if (tipoArchivo.toLowerCase().equals("ordendespacho_packinglist")){
			ubicacion = RUTA_DOCUMENTOS + "OrdenDespacho/PackingList/";
		}else if (tipoArchivo.toLowerCase().equals("pedidos_mailbot")){
			ubicacion  = RUTA_DOCUMENTOS_ + "PedidosBot/"+subCarpeta;
		}else if (tipoArchivo.toLowerCase().equals("remision")){
			ubicacion  = RUTA_DOCUMENTOS_ + "Remisiones/"+subCarpeta + "/";
		} else if (tipoArchivo.equals("videoEmbalar")){
			ubicacion = RUTA_DOCUMENTOS_ + "OrdenDespacho/Evidencia/VideoEmbalar/" ;
		}else if(tipoArchivo.toLowerCase().equals("jasperproductoreclamo")) {
			ubicacion = RUTA_DOCUMENTOS_ + "Reclamos/OrdenReclamo/";
		} else if (tipoArchivo.toLowerCase().equals("producto_reclamo")) {
			ubicacion = RUTA_DOCUMENTOS_ + "Reclamos/";
		} else if (tipoArchivo.toLowerCase().equals("complemento_pago")) {
			ubicacion = RUTA_DOCUMENTOS_ + "JasperReports/ComplementoPQF/";
		} else if (tipoArchivo.toLowerCase().equals("complementopdf")) {
			ubicacion = RUTA_DOCUMENTOS_ + "JasperReports/ComplementoPQF/";
		} else if(tipoArchivo.toLowerCase().equals("jaspernotacredito")) {
			ubicacion = RUTA_DOCUMENTOS + "JasperReports/NotaCreditoPQF/";
		} else if(tipoArchivo.toLowerCase().equals("notacreditopdf")) {
			ubicacion = RUTA_DOCUMENTOS + "NotaCredito/";

		} else if (tipoArchivo.toLowerCase().equals("jasperentrega")) {
			ubicacion = RUTA_DOCUMENTOS + "JasperReports/RegistroEntregaControlados/";
		} else if(tipoArchivo.toLowerCase().equals("entregascontrolado")) {
			ubicacion = RUTA_DOCUMENTOS + "RegistroEntregaControlados/";
		} else if (tipoArchivo.toLowerCase().equals("cartaporte")) {
			ubicacion = RUTA_DOCUMENTOS + "CartaPorte/";
		}else if (tipoArchivo.toLowerCase().equals("jaspercartaporte")){
			ubicacion=RUTA_DOCUMENTOS+"JasperReports/CartaPorte/";
		}
		// logger.info(ubicacion);
		if (ubicacion.equals("")) {
			throw new ProquifaNetException("No existe ubicacion para ese tipo de archivo");
		}
		return ubicacion;
	}


	public String obtenerRutaServidorApartirDeSAP(String tipoArchivo, String subCarpeta)
			throws ProquifaNetException {
		String ubicacion = "";
		if (tipoArchivo.toLowerCase().equals("incidente")) {
			ubicacion = "Incidentes/";
		}else if (tipoArchivo.equals("docInspeccion")) {
			ubicacion = "InspeccionOC/";
		}else if(tipoArchivo.equals("docPieza")){
			ubicacion = "InspeccionOC/InspeccionPiezas/";
		}else if(tipoArchivo.equals("imagenRechazo")){
			ubicacion = "InspeccionOC/ImagenesRechazo/";
		}else if(tipoArchivo.equals("cncreal")){
			ubicacion = "Incidentes/Ponderacion/";
		}else if(tipoArchivo.toLowerCase().equals("factura")){
			ubicacion = "Facturas/"+ subCarpeta + "/";
		}else if(tipoArchivo.toLowerCase().equals("nota_de_credito")){
			ubicacion = "NotaCredito/"+ subCarpeta + "/";
		}else if(tipoArchivo.toLowerCase().equals("notacreditoproquifa")){
			ubicacion = "NotaCredito/Proquifa/";
		}else if(tipoArchivo.toLowerCase().equals("notacreditoryndem")){
		}else if(tipoArchivo.toLowerCase().equals("notacreditoproveedora")){
			ubicacion = "NotaCredito/Proveedora/";
		}else if(tipoArchivo.toLowerCase().equals("notacreditogolocaer")){
			ubicacion = "NotaCredito/Golocaer/";
		}else if(tipoArchivo.toLowerCase().equals("notacreditomungen")){
			ubicacion = "NotaCredito/Mungen/";
		}else if(tipoArchivo.toLowerCase().equals("notacreditopharma")){
			ubicacion = "NotaCredito/Pharma/";
		}else if(tipoArchivo.toLowerCase().equals("notacreditormtrading")){
			ubicacion = "NotaCredito/RMTrading/";
		}else if(tipoArchivo.toLowerCase().equals("solicitudvisita")){
			ubicacion = "ReferenciasSolicitudVisita/";
		}else if(tipoArchivo.toLowerCase().equals("confirmacionentrega")){
			ubicacion = "ConfirmacionEntrega/";
		}else if(tipoArchivo.toLowerCase().equals("solicitudti")){
			ubicacion = "DireccionTI/"+ subCarpeta + "/";
		}else if(tipoArchivo.toLowerCase().equals("tareati")){
			ubicacion = "DireccionTI/"+ subCarpeta + "/";
		}else if(tipoArchivo.toLowerCase().equals("doctoscierredc")){
			ubicacion = "DoctosCierre/DC/";
		}else if(tipoArchivo.toLowerCase().equals("doctoscierrert")){
			ubicacion = "DoctosCierre/RT/";
		}else if(tipoArchivo.toLowerCase().equals("doctoscierrear")){
			ubicacion = "DoctosCierre/AR/";
		}else if(tipoArchivo.toLowerCase().equals("catalogousp")){
			ubicacion = "Doctos/catalogoUSP/";
		}else if (tipoArchivo.toLowerCase().equals("reportepartidasft")){
			ubicacion  = "ReportesPartidasFT/";
		}else if (tipoArchivo.toLowerCase().equals("facturasproveedor")){
			ubicacion  = "Facturas/Proveedor/";
		}else if (tipoArchivo.toLowerCase().equals("facturasproveedorotro")){
			ubicacion  = "Facturas/Proveedor/Otros/";
		}else if (tipoArchivo.toLowerCase().equals("pagos")){
			ubicacion  = "Pagos/";
		}else if (tipoArchivo.toLowerCase().equals("listaarribo")){
			ubicacion  = "ListaArribo/";
		}else if (tipoArchivo.toLowerCase().equals("certificados")){
			ubicacion  = "Certificados/"+ subCarpeta.toUpperCase() + "/";
		}else if (tipoArchivo.toLowerCase().equals("hojasseguridad")){
			ubicacion  = "HojasSeguridad/"+ subCarpeta.toUpperCase() + "/";
		}else if (tipoArchivo.toLowerCase().equals("ordendespacho_packinglist")){
			ubicacion  = "OrdenDespacho/"+ subCarpeta.toUpperCase() + "/PackingList/";
		}else if (tipoArchivo.toLowerCase().equals("ordendespacho_nafta")){
			ubicacion  = "OrdenDespacho/"+ subCarpeta.toUpperCase() + "/NAFTA/";
		}else if (tipoArchivo.toLowerCase().equals("ordendespacho_otros")){
			ubicacion  = "OrdenDespacho/"+ subCarpeta.toUpperCase() + "/Otros/";
		} else if (tipoArchivo.toLowerCase().equals("remision")){
			ubicacion  =  "Remisiones/" + subCarpeta + "/";
		} else if (tipoArchivo.toLowerCase().equals("doctosr")){
			ubicacion =  "Doctos/";
		}




		if (ubicacion.equals("")) {
			throw new ProquifaNetException(
					"No existe ubicacion para ese tipo de archivo: " + tipoArchivo);
		}
		return ubicacion;
	}

	public String obtenerRutaServidorFTP(String tipoArchivo, String subCarpeta)
			throws ProquifaNetException {
		String ubicacion = "";
		if (tipoArchivo.toLowerCase().equals("incidente")) {
			ubicacion = RUTA_DOCUMENTOS_ftp + "Incidentes/";
		}else if (tipoArchivo.equals("docInspeccion")) {
			ubicacion = RUTA_DOCUMENTOS_ftp + "InspeccionOC/";
		}else if(tipoArchivo.equals("docPieza")){
			ubicacion = RUTA_DOCUMENTOS_ftp + "InspeccionOC/InspeccionPiezas/";
		}else if(tipoArchivo.equals("imagenRechazo")){
			ubicacion = RUTA_DOCUMENTOS_ftp + "InspeccionOC/ImagenesRechazo/";
		}else if(tipoArchivo.equals("cncreal")){
			ubicacion = RUTA_DOCUMENTOS_ftp + "Incidentes/Ponderacion/";
		}else if(tipoArchivo.toLowerCase().equals("factura")){
			ubicacion = RUTA_DOCUMENTOS_ftp + "Facturas/"+ subCarpeta + "/";
		}else if(tipoArchivo.toLowerCase().equals("nota_de_credito")){
			ubicacion = RUTA_DOCUMENTOS_ftp + "NotaCredito/"+ subCarpeta + "/";
		}else if(tipoArchivo.toLowerCase().equals("notacreditoproquifa")){
			ubicacion = RUTA_DOCUMENTOS_ftp + "NotaCredito/Proquifa/";
		}else if(tipoArchivo.toLowerCase().equals("notacreditoryndem")){
		}else if(tipoArchivo.toLowerCase().equals("notacreditoproveedora")){
			ubicacion = RUTA_DOCUMENTOS_ftp + "NotaCredito/Proveedora/";
		}else if(tipoArchivo.toLowerCase().equals("notacreditogolocaer")){
			ubicacion = RUTA_DOCUMENTOS_ftp + "NotaCredito/Golocaer/";
		}else if(tipoArchivo.toLowerCase().equals("notacreditomungen")){
			ubicacion = RUTA_DOCUMENTOS_ftp + "NotaCredito/Mungen/";
		}else if(tipoArchivo.toLowerCase().equals("notacreditopharma")){
			ubicacion = RUTA_DOCUMENTOS_ftp + "NotaCredito/Pharma/";
		}else if(tipoArchivo.toLowerCase().equals("notacreditormtrading")){
			ubicacion = RUTA_DOCUMENTOS_ftp + "NotaCredito/RMTrading/";
		}else if(tipoArchivo.toLowerCase().equals("solicitudvisita")){
			ubicacion = RUTA_DOCUMENTOS_ftp + "ReferenciasSolicitudVisita/";
		}else if(tipoArchivo.toLowerCase().equals("confirmacionentrega")){
			ubicacion = RUTA_DOCUMENTOS_ftp + "ConfirmacionEntrega/";
		}else if(tipoArchivo.toLowerCase().equals("solicitudti")){
			ubicacion = RUTA_DOCUMENTOS_ftp + "DireccionTI/"+ subCarpeta + "/";
		}else if(tipoArchivo.toLowerCase().equals("tareati")){
			ubicacion = RUTA_DOCUMENTOS_ftp + "DireccionTI/"+ subCarpeta + "/";
		}else if(tipoArchivo.toLowerCase().equals("imagenproveedor")){
			ubicacion = RUTA_LOGOS + "logoProveedores/";    								//OSCAR	O
		}else if(tipoArchivo.toLowerCase().equals("imagenmarca")){
			ubicacion = RUTA_LOGOS + "logoMarcas/";    										//OSCAR	O
		}else if(tipoArchivo.toLowerCase().equals("imagencliente")){
			ubicacion = RUTA_LOGOS + "logoClientes/";    									//OSCAR	O	
		}else if(tipoArchivo.toLowerCase().equals("imagenagente")){
			ubicacion = RUTA_LOGOS + "logoAgentes/";    									//OSCAR	O
		}else if(tipoArchivo.toLowerCase().equals("doctoscierredc")){
			ubicacion = RUTA_DOCUMENTOS_ftp + "DoctosCierre/DC/";
		}else if(tipoArchivo.toLowerCase().equals("doctoscierrert")){
			ubicacion = RUTA_DOCUMENTOS_ftp + "DoctosCierre/RT/";
		}else if(tipoArchivo.toLowerCase().equals("doctoscierrear")){
			ubicacion = RUTA_DOCUMENTOS_ftp + "DoctosCierre/AR/";
		}
		else if (tipoArchivo.toLowerCase().equals("plantillafactura")) {
			ubicacion = RUTA_DOCUMENTOS + "JasperReports/FacturaPQF/";
		} else if (tipoArchivo.toLowerCase().equals("facturapqf")) {
			ubicacion = RUTA_DOCUMENTOS + "FacturaPQF/";
		}
		//		logger.info(ubicacion);
		if (ubicacion.equals("")) {
			throw new ProquifaNetException(
					"No existe ubicacion para ese tipo de archivo");
		}
		return ubicacion;
	}

	@SuppressWarnings("resource")
	public byte[] getBytesFromFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);

		long length = file.length();

		if (length > Long.MAX_VALUE) {
			// File is too large
		}

		// Create the byte array to hold the data
		byte[] bytes = new byte[(int) length];

		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
				&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}

		// Ensure all the bytes have been read in
		if (offset < bytes.length) {
			throw new IOException("Could not completely read file "
					+ file.getName());
		}

		// Close the input stream and return bytes
		is.close();
		return bytes;
	}

	public void copiarArchivo(byte[] bytes, String folioDocumento,
							  String tipoArchivo) throws ProquifaNetException {
		try {
			String ruta = obtenerRutaServidor(tipoArchivo, null);
			//			String ruta2 = obtenerRutaServidorFTP(tipoArchivo, null);
			File file =  new File(ruta);
			File fileAux = new File(ruta + folioDocumento);
			FileOutputStream fos;
			if (!file.exists()) {
				file.mkdirs();
				log.info("Carpeta creada");
			}

			fos = new FileOutputStream(fileAux);
			fos.write(bytes);
			fos.close();
			//			logger.info("GUardar a ftp...");
			//			subirArchivoSFTP(ruta + folioDocumento, ruta2 + folioDocumento);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String subirArchivo(File archivo, String folioDocumento, String tipoArchivo)throws ProquifaNetException {
		try {
			String dir = obtenerRutaServidor(tipoArchivo, null);
			File file = new File(dir);
			File targetFile = new File(dir + folioDocumento);
			if (!file.exists()) {
				file.mkdirs();
			}
			OutputStream outStream;
			outStream = new FileOutputStream(targetFile);
			byte[] array = Files.readAllBytes(archivo.toPath());
			outStream.write(array);
			outStream.close();
			return "Exito";
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			throw new ProquifaNetException();
		}
	}

	@SuppressWarnings("deprecation")
	public Long calcularDiasAtraso(Date fecha, Integer diasAdicionales) {
		fecha.setHours(0);
		fecha.setMinutes(0);
		fecha.setSeconds(0);
		Calendar fechaInicio, fechaFin;
		Date fechaActual = new Date();
		fechaInicio = Calendar.getInstance();
		fechaInicio.setTime(fecha);
		fechaInicio.add(Calendar.DAY_OF_WEEK, 1);
		fechaFin = Calendar.getInstance();
		fechaFin.setTime(fechaActual);
		long diasAtraso = 0;
		long total = 0;
		// Si las fechas son iguales regresa cero...
		if (fechaInicio.getTimeInMillis() >= fechaFin.getTimeInMillis()) {
			return 0L;
		}
		do {
			if (fechaInicio.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
					&& fechaInicio.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
				diasAtraso++;
			}
			fechaInicio.add(Calendar.DAY_OF_WEEK, 1);
		} while (fechaInicio.getTimeInMillis() < fechaFin.getTimeInMillis());
		total = diasAtraso - diasAdicionales;
		if (total < 0) {
			total = 0;
		}
		return total;
	}

	public Boolean enviarCorreo(Correo correo) {
		try {
			//Quitar
			//correo.setCorreo("sara.sanchez@ryndem.mx;yosimar.mendez@ryndem.mx");
			//
			boolean isProquifa = true;
			Properties props = new Properties();
			String archivo = "";
			props.put("mail.transport.protocol", "smtp");
//
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "25");
			if(correo.getRemitente() != null && correo.getRemitente().toLowerCase().equals("pharma")){//Si es remitente Pharma
				props.put("mail.imap.user", "purchase@phsus.com");
				props.put("mail.password", "Pharma-1");
				props.put("mail.smtp.host", "smtp.gmail.com");
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.port", "587");
				isProquifa = false;
			}else if (correo.getOrigen().toLowerCase() != null && correo.getOrigen().toLowerCase().equals("ventas") || correo.getOrigen().toLowerCase().equals("credito") || correo.getOrigen().toLowerCase().equals("compras")) {
				props.put("mail.imap.user", "ocardona@proquifa.net");
				props.put("mail.password", "2hs3kMxXjEyVLqHU");
				props.put("mail.smtp.host", "smtp-relay.sendinblue.com");
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.port", "587");
			}else if (correo.getRemitente() != null && correo.getRemitente().toLowerCase().equals("rm trading")) {// Si el remitente es rmtrading																					
				props.put("mail.imap.user", "purchase@rmtrade.net");
				props.put("mail.password", "rmtrading-1");
				isProquifa = false;
			}else{

				props.put("mail.smtp.host", HOST);
				props.put("mail.imap.user", correo.getOrigen().toLowerCase() + DOMINIO);
				if (correo.getOrigen().toLowerCase().equals("serviciosti"))
					props.put("mail.password", "123456");
				else
					props.put("mail.password", "cambiame");
			}
			authenticator = new MiAutenticador(props.getProperty("mail.imap.user"), props.getProperty("mail.password"));


			mailSession = Session.getInstance(props, authenticator);
			mailSession.setDebug(false);


			Message msg = null;
			msg = new MimeMessage(mailSession);

			if (correo.getOrigen() != null && correo.getOrigen().toLowerCase().equals("ventas") || correo.getOrigen().toLowerCase().equals("compras") || correo.getOrigen().toLowerCase().equals("credito")) {
				msg.setFrom(new InternetAddress(correo.getOrigen().toLowerCase() +"@proquifa.com.mx"));
			} else if (correo.getRemitente() != null
					&& correo.getRemitente().toLowerCase().equals("pharma")) {// Si
				// es
				// remitente
				// Pharma
				msg.setFrom(new InternetAddress("purchase@phsus.com"));
			} else if (correo.getRemitente() != null
					&& correo.getRemitente().toLowerCase().equals("rm trading")) {// Si
				// es
				// RMTrading
				msg.setFrom(new InternetAddress("purchase@rmtrade.net"));
			}else{
				msg.setFrom(new InternetAddress(correo.getOrigen().toLowerCase() + DOMINIO));
			}

			msg.setSubject(correo.getAsunto());
			/*correo.setCorreo("oscar.cardona@ryndem.mx");
			correo.setCcorreo(null);
			correo.setCocorreo(null);*/
			String direccionesString = correo.getCorreo().toLowerCase().replace(",", ";");

			StringTokenizer tokensCorreos = new StringTokenizer(direccionesString, ";");
			int nContactos = tokensCorreos.countTokens();
			InternetAddress[] toAddrs = new InternetAddress[nContactos];

			for (int i = 0; i < nContactos; i++) {
				toAddrs[i] = (new InternetAddress(tokensCorreos.nextToken()));
			}

			msg.setRecipients(Message.RecipientType.TO, toAddrs);

			if (correo.getCcorreo() != null) {
				if (!correo.getCcorreo().equals("")) {
					StringTokenizer tokensCCorreos = new StringTokenizer(correo.
							getCcorreo().toLowerCase().replace(",", ";"), ";");
					int nCContactos = tokensCCorreos.countTokens();
					InternetAddress[] CCAddrs = new InternetAddress[nCContactos];
					for (int i = 0; i < nCContactos; i++) {
						CCAddrs[i] = new InternetAddress(tokensCCorreos
								.nextToken());
					}
					msg.setRecipients(Message.RecipientType.CC, CCAddrs);
				}
			}

			if (correo.getCocorreo() != null) {
				if (!correo.getCocorreo().equals("")) {
					StringTokenizer tokensCOCorreos = new StringTokenizer(correo
							.getCocorreo().toLowerCase().replace(",", ";"), ";");
					int nCOContactos = tokensCOCorreos.countTokens();
					InternetAddress[] CCOAddrs = new InternetAddress[nCOContactos];
					for (int i = 0; i < nCOContactos; i++) {
						CCOAddrs[i] = new InternetAddress(tokensCOCorreos
								.nextToken());
					}
					msg.setRecipients(Message
							.RecipientType.BCC, CCOAddrs);
				}
			}

			MimeBodyPart cuerpoCorreo = new MimeBodyPart();
			MimeMultipart multipart;

			String nameImg ="";

			BodyPart texto = new MimeBodyPart();

			if (!correo.isSinPiePagina()) {

				if (correo.getIdEmpleadoString() != null){
					// se crea un bodypart para el pie de pagina CON FIRMA    
					nameImg = correo.getIdEmpleadoString().toString();
					String tex = " Este correo electr&oacute;nico y/o el material anexo son para uso exclusivo de la persona o entidad a la que expresamente se le ha enviado, y puede contener informaci&oacute;n confidencial y/o material privilegiado. Si usted no es el destinatario leg&iacute;timo del mismo, deber&aacute; notificar  inmediatamente al remitente del correo y borrar este mensaje y sus anexos en su totalidad permanentemente de su sistema. Cualquier revisi&oacute;n, retransmisi&oacute;n, difusi&oacute;n o cualquier otro uso de este correo, por personas o entidades distintas a las del destinatario leg&iacute;timo, queda expresamente prohibido. Este correo electr&oacute;nico no pretende ni debe ser considerado como constructivo de ninguna relaci&oacute;n legal, contractual o de otra &iacute;ndole similar.";
					String text1 = " This e-mail message and/or any attachments are intended solely for the exclusive use of the individual named on this mail and may be confidential and/or copyrighted. If you are not the intended recipient and received this message, please notify immediately to us and then delete it from your system. Any revision or any other use of this mail, by persons or entities other than the intended recipient is forbidden. This mail is not intending to be considered as abiding to any legal relationship, contractual or any other similar kind.";
					texto.setContent("<p><em><font color='#808080' face='Helvetica' size='1'><img src='cid:"+nameImg+"' /><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>"+tex+"</font></em></p><em><font color='#808080' face='Helvetica' size='1'>"+text1+"</font></em><br><br>","text/html; charset=\"utf-8\"");

				}else{
					// se crea un bodypart para el pie de pagina SIN FIRMA    

					String tex = " Este correo electr&oacute;nico y/o el material anexo son para uso exclusivo de la persona o entidad a la que expresamente se le ha enviado, y puede contener informaci&oacute;n confidencial y/o material privilegiado. Si usted no es el destinatario leg&iacute;timo del mismo, deber&aacute; notificar  inmediatamente al remitente del correo y borrar este mensaje y sus anexos en su totalidad permanentemente de su sistema. Cualquier revisi&oacute;n, retransmisi&oacute;n, difusi&oacute;n o cualquier otro uso de este correo, por personas o entidades distintas a las del destinatario leg&iacute;timo, queda expresamente prohibido. Este correo electr&oacute;nico no pretende ni debe ser considerado como constructivo de ninguna relaci&oacute;n legal, contractual o de otra &iacute;ndole similar.";
					String text1 = " This e-mail message and/or any attachments are intended solely for the exclusive use of the individual named on this mail and may be confidential and/or copyrighted. If you are not the intended recipient and received this message, please notify immediately to us and then delete it from your system. Any revision or any other use of this mail, by persons or entities other than the intended recipient is forbidden. This mail is not intending to be considered as abiding to any legal relationship, contractual or any other similar kind.";
					texto.setContent("<p><em><font color='#808080' face='Helvetica' size='1'><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>"+tex+"</font></em></p><em><font color='#808080' face='Helvetica' size='1'>"+text1+"</font></em>","text/html; charset=\"utf-8\"");
				}
			}else{
				String tex = " ";
				String text1 = "";
				texto.setContent("","");
			}


			String sCuerpoCorreo = correo.getCuerpoCorreo();
			sCuerpoCorreo = sCuerpoCorreo.replace("\n", "<br>"); // Remplaza los Saltos de Linea por <br> para que en el correo se vea con formato
			sCuerpoCorreo = sCuerpoCorreo.replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"); // Remplaza los tabuladores por espacios en blanco, No hay codigo HTML para TAB

			cuerpoCorreo.setText("\n" + sCuerpoCorreo + "\n" + texto.getContent(),"utf-8");
			cuerpoCorreo.setHeader("Content-Type","text/html; charset=\"utf-8\"");
			cuerpoCorreo.setHeader("Content-Transfer-Encoding","quoted-printable");
			multipart = new MimeMultipart("related");
			multipart.addBodyPart(cuerpoCorreo );




			StringTokenizer tokensDocumentos = new StringTokenizer(correo.getArchivoAdjunto(), ",");
			int nDocs = tokensDocumentos.countTokens();
			MimeBodyPart adjunto1 = null;
			DataSource source1 = null;
			//			logger.info("Adjuntando archivos a correo..." + correo.getArchivoAdjunto());
			for (int i = 0; i < nDocs; i++) {
				archivo = obtenerRutaCompletaDocumento(tokensDocumentos.nextToken(), correo.getTipo(), correo.getFacturadaPor());
				File f = new File(archivo);
				if (f.exists()) {
					adjunto1 = new MimeBodyPart();
					source1 = new FileDataSource(archivo);
					adjunto1.setDataHandler(new DataHandler(source1));
					adjunto1.setFileName(f.getName());
					if(correo.getConFormato() == 1){
						adjunto1.setHeader("Content-ID","<Archivo_" + i + ">");
					}
					multipart.addBodyPart(adjunto1);
				}
			}
			if (correo.getIdEmpleadoString() != null){

				// Procesar la imagen
				MimeBodyPart imagen = null;
				imagen = new MimeBodyPart();
				String ruta = RUTA_FIRMAS + correo.getIdEmpleadoString().toString()+".png";
				File firma = new File(ruta);
				if (firma.exists()){
					imagen.attachFile (RUTA_FIRMAS + correo.getIdEmpleadoString().toString()+".png");
					imagen.setHeader("Content-ID","<"+nameImg+">");
					multipart.addBodyPart(imagen);
				}
			}

			if(correo.getConFormato() == 1){
				MimeBodyPart imagen2 = null;
				imagen2 = new MimeBodyPart();
				String ruta = RUTA_FIRMAS + "logo.jpg";
				File firma = new File(ruta);
				if (firma.exists()){
					imagen2.attachFile (RUTA_FIRMAS + "logo.jpg");
					imagen2.setHeader("Content-ID","<Proquifa>");
					multipart.addBodyPart(imagen2);
				}

				MimeBodyPart imagen3 = null;
				imagen3 = new MimeBodyPart();
				ruta = RUTA_FIRMAS + "franja_Marcas.jpg";
				File marcas = new File(ruta);
				if (marcas.exists()){
					imagen3.attachFile (RUTA_FIRMAS + "franja_Marcas.jpg");
					imagen3.setHeader("Content-ID","<Marcas>");
					multipart.addBodyPart(imagen3);
				}
			}

			msg.setContent(multipart);
			Transport.send(msg);

			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


	public boolean enviarCorreoViaSpringBoot(Correo correo) {


		MensajeroResoutce MR= new MensajeroResoutce();
		try {

			sender= MR.getSender();
			log.info("En el servicio de correo metodo en funcion enviarCorreoViaSpringBoot");
			MimeMessage message = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message,true);
			helper.setTo(correo.getCorreo());
			String RutaPDF="/Users/josephat.reyes/desktop/";
// helper.addAttachment( correo.getArchivoAdjunto(), );
//   File file= new File("/Users/josephat.reyes/desktop/NE-072518-84.pdf");
			log.info("Aqui boto antes de FileSystem-->");
			FileSystemResource file = new FileSystemResource(RutaPDF+correo.getArchivoAdjunto()+".pdf");
			log.info("Archivo-->");
			log.info("Archivo-->"+file);
			helper.addAttachment(file.getFilename(), file);
	
	
	/*
	if (correo.getIdEmpleadoString() != null){

		// Procesar la imagen
		MimeBodyPart imagen = null;
		imagen = new MimeBodyPart();
		String ruta = RUTA_FIRMAS + correo.getIdEmpleadoString().toString()+".png";
		
		File firma = new File(ruta);
		FileSystemResource res = new FileSystemResource(firma);
		
		if (firma.exists()){
			helper.addInline("identifier1234", res);
			//multipart.addBodyPart(imagen);
		}
	}
	
	*/




//   helper.addAttachment("/Users/josephat.reyes/desktop/NE-072518-84.pdf", new ByteArrayResource(getBytesFromFile(file)) );
			helper.setText("<html> <body>"+ correo.getCuerpoCorreo()+"</body> </html>", true);
			helper.setSubject("Hi");




			if(correo.getConFormato() == 1){

				log.info("Si es con formato-->");
				//MimeBodyPart imagen2 = null;
				//imagen2 = new MimeBodyPart();
				String ruta = RUTA_FIRMAS + "logo.jpg";
				File firma = new File(ruta);
				FileSystemResource res = new FileSystemResource(firma);

				if (firma.exists()){
					helper.addInline("Proquifa", res);
				}

				MimeBodyPart imagen3 = null;
				//	imagen3 = new MimeBodyPart();
				ruta = RUTA_FIRMAS + "franja_Marcas.jpg";

				File marcas = new File(ruta);
				FileSystemResource res2 = new FileSystemResource(marcas);
				//File marcas = new File(ruta);
				if (marcas.exists()){

					helper.addInline("Marcas",res2);
				}
			}

			sender.send(message);
			return true;
		}catch (Exception e) {
			// TODO: handle exception
			log.info("excep-->"+e);
			return false;
		}

	}




	public Long calcularDiferencia(Date fechaInicial, Date fechaFinal) {
		Calendar fechaInicio, fechaFin;
		Date fechaActual = fechaFinal;
		fechaInicio = Calendar.getInstance();
		fechaInicio.setTime(fechaInicial);
		fechaInicio.add(Calendar.DAY_OF_WEEK, 1);
		fechaFin = Calendar.getInstance();
		fechaFin.setTime(fechaActual);

		long diasAtraso = 0;

		if (fechaInicio.getTimeInMillis() >= fechaFin.getTimeInMillis()) {
			return 0L;
		}
		// if (fechaInicio.getTimeInMillis() > fechaFin.getTimeInMillis()) {
		// fechaInicio.setTime(fechaActual);
		// fechaFin.setTime(fechaInicial);
		// }
		do {
			if (fechaInicio.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
					&& fechaInicio.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
				diasAtraso++;
			}
			fechaInicio.add(Calendar.DAY_OF_WEEK, 1);
		} while (fechaInicio.getTimeInMillis() <= fechaFin.getTimeInMillis());
		return diasAtraso;
		// Long diferencia = 0L;
		// diferencia = (fechaFinal.getTime() - fechaInicial.getTime()) /
		// MILLISECS_PER_DAY;
		// if(diferencia < 0){
		// diferencia = 0L;
		// }
		// return diferencia;
	}

	public Long calcularDiasDeAtraso(Date fechaInicial, Date fechaFinal) {
		Calendar fechaInicio, fechaFin;
		Date fechaActual = fechaFinal;
		fechaInicio = Calendar.getInstance();
		fechaInicio.setTime(fechaInicial);
		//fechaInicio.add(Calendar.DAY_OF_WEEK, 1);
		fechaFin = Calendar.getInstance();
		fechaFin.setTime(fechaActual);

		fechaInicio.set(Calendar.HOUR, 0);
		fechaInicio.set(Calendar.MINUTE, 0);
		fechaInicio.set(Calendar.SECOND, 0);
		fechaInicio.set(Calendar.MILLISECOND, 0);

		fechaFin.set(Calendar.HOUR, 0);
		fechaFin.set(Calendar.MINUTE, 0);
		fechaFin.set(Calendar.SECOND, 0);
		fechaFin.set(Calendar.MILLISECOND, 0);

		long diasAtraso = 0;

		if (fechaInicio.getTimeInMillis() >= fechaFin.getTimeInMillis()) {
			return 0L;
		}

		do {
			if (fechaInicio.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
					&& fechaInicio.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
				diasAtraso++;
			}
			fechaInicio.add(Calendar.DAY_OF_WEEK, 1);
		} while (fechaInicio.getTimeInMillis() < fechaFin.getTimeInMillis());
		return diasAtraso;
	}

	public Double calcularMontoBono(Double bonoCompleto, Double costoNoCalidad,
									String nivel) {
		Double montoFinal = 0.0;
		Double montos[] = null;
		if (nivel.equals("Operativo")) {
			montos = montosOperativo;
		} else if (nivel.equals("Gerente")) {
			montos = montosGerente;
		} else if (nivel.equals("Directivo")) {
			montos = montosDirectivo;
		}
		if (costoNoCalidad == 0.0) {
			montoFinal = bonoCompleto / 12;
		} else {
			for (int i = 0; i < montos.length && montoFinal == 0; i++) {
				if (costoNoCalidad <= montos[i]) {
					montoFinal = (bonoCompleto / 12)
							* ((montos.length - (i + 1)) / 10.0);
				}
			}
		}
		return montoFinal;
	}


	public Double calcularPorcentajeBono(Double bonoCompleto,
										 Double costoNoCalidad, String nivel) {
		Double porcentaje = 0.0;
		Double montos[] = null;
		if (nivel.equals("Operativo")) {
			montos = montosOperativo;
		} else if (nivel.equals("Gerente")) {
			montos = montosGerente;
		} else if (nivel.equals("Directivo")) {
			montos = montosDirectivo;
		}
		if (costoNoCalidad == 0.0) {
			porcentaje = 1.0;
		} else {
			for (int i = 0; i < montos.length && porcentaje == 0; i++) {
				if (costoNoCalidad <= montos[i]) {
					porcentaje = ((montos.length - (i + 1)) / 10.0);
				}
			}
		}
		return porcentaje * 100.0;
	}

	public Double calcularPorcentajeBonoV2(Double bonoCompleto,
										   Double horasHombre) {
		Double porcentaje = 0.0;

		if (horasHombre == 0.0) {
			porcentaje = 1.0;
		}else if((horasHombre >= 6.2) && (horasHombre < 12.4)){
			porcentaje = 0.9;
		}else if((horasHombre >= 12.4) && (horasHombre < 18.6)){
			porcentaje = 0.8;
		}else if((horasHombre >= 18.6) && (horasHombre < 24.8)){
			porcentaje = 0.7;
		}else if((horasHombre >= 24.8) && (horasHombre < 31.0)){
			porcentaje = 0.6;
		}else if((horasHombre >= 31.0) && (horasHombre < 37.2)){
			porcentaje = 0.5;
		}else if((horasHombre >= 37.2) && (horasHombre < 43.4)){
			porcentaje = 0.4;
		}else if((horasHombre >= 43.4) && (horasHombre < 49.6)){
			porcentaje = 0.3;
		}else if((horasHombre >= 49.6) && (horasHombre < 55.8)){
			porcentaje = 0.2;
		}else if((horasHombre >= 55.8) && (horasHombre < 62.0)){
			porcentaje = 0.1;
		}else{
			porcentaje = 0.0;
		}

		return porcentaje * 100.0;
	}

	@SuppressWarnings("deprecation")
	public String convertirDosFechasAString(Date finicio, Date ffin, String fechaTabla){

		String fecha = "";
		String finicioString = "", ffinString = "";
		int year=0, month=0;


		if(finicio != null && ffin != null){
			year = finicio.getYear() + 1900;
			month = finicio.getMonth() + 1;
			if(month < 10){
				if(finicio.getDate() < 10){
					finicioString = year + "0" + month + "0" + finicio.getDate();
				}else{
					finicioString = year + "0" + month + "" + finicio.getDate();
				}
			}else{
				if(finicio.getDate() < 10){
					finicioString = year + "" + month + "0" + finicio.getDate();
				}else{
					finicioString = year + "" + month + "" + finicio.getDate();
				}
			}
			year = ffin.getYear() + 1900;
			month = ffin.getMonth() + 1;

			if(month < 10){
				if(ffin.getDate() < 10){
					ffinString = year + "0" + month + "0" + ffin.getDate();
				}else{
					ffinString = year + "0" + month + "" + ffin.getDate();
				}
			}else{
				if(ffin.getDate() < 10){
					ffinString = year + "" + month + "0" + ffin.getDate();
				}else{
					ffinString = year + "" + month + "" + ffin.getDate();
				}
			}
			fecha = " '" + finicioString + " 00:00'  AND " + fechaTabla + " <= '" + ffinString + " 23:59'";
		}
		return fecha;
	}

	@SuppressWarnings("deprecation")
	public String convertirFechaAString(Date finicio){

		String fecha = "";
		String finicioString = "";
		int year=0, month=0;


		if(finicio != null ){
			year = finicio.getYear() + 1900;
			month = finicio.getMonth() + 1;
			if(month < 10){
				if(finicio.getDate() < 10){
					finicioString = year + "0" + month + "0" + finicio.getDate();
				}else{
					finicioString = year + "0" + month + "" + finicio.getDate();
				}
			}else{
				if(finicio.getDate() < 10){
					finicioString = year + "" + month + "0" + finicio.getDate();
				}else{
					finicioString = year + "" + month + "" + finicio.getDate();
				}
			}

			fecha = finicioString + " 00:00";
		}
		return fecha;
	}

	@SuppressWarnings("deprecation")
	public String obtenerFormatoFecha(Date finicio){

		String fecha = "";
		String finicioString = "";
		int year=0, month=0;


		if(finicio != null ){
			year = finicio.getYear() + 1900;
			month = finicio.getMonth() + 1;
			if(month < 10){
				if(finicio.getDate() < 10){
					finicioString = "0" + finicio.getDate() + "-0" + month + "-" + year;
				}else{
					finicioString = finicio.getDate() + "-0" + month + "-" + year;
				}
			}else{
				if(finicio.getDate() < 10){
					finicioString ="0" + finicio.getDate() + "-" + month + "-" + year;
				}else{
					finicioString = finicio.getDate() + "-" + month + "-" + year;
				}
			}
			fecha = finicioString;
		}
		return fecha;
	}


	public String obtenerFechas(Long anio, String periodo, Integer tipoPeriodo,
								Long mes, String fechaTabla) {
		try{
			String fechas = "";
			Integer diaFinMes = 0;
			NumberFormat nf = NumberFormat.getInstance();
			if (periodo.toLowerCase().equals("anual")) {
				fechas = anio + "0101 00:00' and " + fechaTabla + " <= '" + anio + "1231 23:59";

			} else if (periodo.toLowerCase().equals("anualproveedor")) {
				Long mesInicial = mes;
				Long mesFinal = mes - 1;
				if(mesFinal == 0 ){ mesFinal = 1L;}

				fechas = anio.toString() + (mesInicial < 10 ? "0" + mesInicial.toString() : mesInicial.toString())  + "01 00:00' and " + fechaTabla + " <= '" + (anio + 1) + (mesFinal < 10 ? "0" + mesFinal.toString(): mesFinal.toString())  +"30 23:59";

			} else if (periodo.toLowerCase().equals("semestral")) {
				if (tipoPeriodo == 1) {
					fechas = anio + "0101 00:00' and " +  fechaTabla +" <= '" + anio + "0630 23:59";
				} else {
					fechas = anio + "0701 00:00' and " +  fechaTabla +" <= '" + anio + "1231 23:59";
				}
			} else if (periodo.toLowerCase().equals("trimestral")) {
				switch (tipoPeriodo) {
					case 1:
						fechas = anio + "0101 00:00' and " +  fechaTabla +" <= '" + anio + "0331 23:59";
						break;
					case 2:
						fechas = anio + "0401 00:00' and " +  fechaTabla +" <= '" + anio + "0630 23:59";
						break;
					case 3:
						fechas = anio + "0701 00:00' and " +  fechaTabla +" <= '" + anio + "0930 23:59";
						break;
					case 4:
						fechas = anio + "1001 00:00' and " +  fechaTabla +" <= '" + anio + "1231 23:59";
						break;
				}
			}else if(periodo.toLowerCase().equals("bimestral")){
				switch(tipoPeriodo){
					case 1:
						fechas = anio + "0101 00:00' and " +  fechaTabla +" <= '" + anio + "0228 23:59";
						break;
					case 2:
						fechas = anio + "0301 00:00' and " +  fechaTabla +" <= '" + anio + "0430 23:59";
						break;
					case 3:
						fechas = anio + "0501 00:00' and " +  fechaTabla +" <= '" + anio + "0630 23:59";
						break;
					case 4:
						fechas = anio + "0701 00:00' and " +  fechaTabla +" <= '" + anio + "0831 23:59";
						break;
					case 5:
						fechas = anio + "0901 00:00' and " +  fechaTabla +" <= '" + anio + "1031 23:59";
						break;
					case 6:
						fechas = anio + "1101 00:00' and " +  fechaTabla +" <= '" + anio + "1231 23:59";
						break;
				}
			}else if(periodo.toLowerCase().equals("quincenal")){

				if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8
						|| mes == 10 || mes == 12) {
					diaFinMes = 31;
				} else if (mes == 4 || mes == 6 || mes == 9 || mes == 11) {
					diaFinMes = 30;
				} else {
					diaFinMes = 28;
				}

				nf.setMinimumIntegerDigits(2);
				nf.setGroupingUsed(false);
				String mesT = nf.format(mes);

				switch(tipoPeriodo){
					case 1:
						fechas = anio + mesT + "01 00:00' and " + fechaTabla + " <= '" + anio + mesT
								+ "15 23:59";
						break;
					case 2:
						fechas = anio + mesT + "16 00:00' and " + fechaTabla + " <= '" + anio + mesT
								+ diaFinMes + " 23:59";
						break;
				}



			}else if (periodo.toLowerCase().equals("mensual")) {
				if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8
						|| mes == 10 || mes == 12) {
					diaFinMes = 31;
				} else if (mes == 4 || mes == 6 || mes == 9 || mes == 11) {
					diaFinMes = 30;
				} else {
					diaFinMes = 28;
				}
				nf.setMinimumIntegerDigits(2);
				nf.setGroupingUsed(false);
				String mesT = nf.format(mes);
				fechas = anio + mesT + "01 00:00' and " + fechaTabla + " <= '" + anio + mesT
						+ diaFinMes + " 23:59";
			}
			return fechas;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}


	}

	public String obtenerFechasBetween(Long anio, String periodo, Integer tipoPeriodo,
									   Long mes, String fechaTabla) {
		String fechas = "";
		if (periodo.toLowerCase().equals("anual")) {
			fechas = "AND HISVEN.Anio='" + anio + "'";
		} else if (periodo.toLowerCase().equals("semestral")) {
			if (tipoPeriodo == 1) {
				fechas = "AND HISVEN.Anio='" + anio + "' AND HISVEN.Mes IN ('Enero','Febrero','Marzo','Abril','Mayo','Junio') ";
			} else {
				fechas = "AND HISVEN.Anio='" + anio + "' AND HISVEN.Mes IN ('Julio','Agosto','Septiembre','Octubre','Noviembre','Diciembre') ";
			}
		} else if (periodo.toLowerCase().equals("trimestral")) {
			switch (tipoPeriodo) {
				case 1:
					fechas = "AND HISVEN.Anio IN ('" + anio + "')  AND HISVEN.Mes IN ('Enero','Febrero','Marzo') ";
					break;
				case 2:
					fechas = "AND HISVEN.Anio IN ('" + anio + "')  AND HISVEN.Mes IN ('Abril','Mayo','Junio') ";
					break;
				case 3:
					fechas = "AND HISVEN.Anio IN ('" + anio + "')  AND HISVEN.Mes IN ('Julio','Agosto','Septiembre') ";
					break;
				case 4:
					fechas = "AND HISVEN.Anio IN ('" + anio + "')  AND HISVEN.Mes IN ('Octubre','Noviembre','Diciembre') ";
					break;
			}
		}else if(periodo.toLowerCase().equals("bimestral")){
			switch(tipoPeriodo){
				case 1:
					fechas = "AND HISVEN.Anio IN ('" + anio + "')  AND HISVEN.Mes IN ('Enero','Febrero') ";
					break;
				case 2:
					fechas = "AND HISVEN.Anio IN ('" + anio + "')  AND HISVEN.Mes IN ('Marzo','Abril') ";
					break;
				case 3:
					fechas = "AND HISVEN.Anio IN ('" + anio + "')  AND HISVEN.Mes IN ('Mayo','Junio') ";
					break;
				case 4:
					fechas = "AND HISVEN.Anio IN ('" + anio + "')  AND HISVEN.Mes IN ('Julio','Agosto') ";
					break;
				case 5:
					fechas = "AND HISVEN.Anio IN ('" + anio + "')  AND HISVEN.Mes IN ('Septiembre','Octubre') ";
					break;
				case 6:
					fechas = "AND HISVEN.Anio IN ('" + anio + "')  AND HISVEN.Mes IN ('Noviembre','Diciembre') ";
					break;
			}
		}else if (periodo.toLowerCase().equals("mensual")) {

			if (mes==1){
				fechas = "AND HISVEN.Anio IN ('" + anio + "')  AND HISVEN.Mes IN ('Enero') ";
			}
			if (mes==2){
				fechas = "AND HISVEN.Anio IN ('" + anio + "')  AND HISVEN.Mes IN ('Febrero') ";
			}
			if (mes==3){
				fechas = "AND HISVEN.Anio IN ('" + anio + "')  AND HISVEN.Mes IN ('Marzo') ";
			}
			if (mes==4){
				fechas = "AND HISVEN.Anio IN ('" + anio + "')  AND HISVEN.Mes IN ('Abril') ";
			}
			if (mes==5){
				fechas = "AND HISVEN.Anio IN ('" + anio + "')  AND HISVEN.Mes IN ('Mayo') ";
			}
			if (mes==6){
				fechas = "AND HISVEN.Anio IN ('" + anio + "')  AND HISVEN.Mes IN ('Junio') ";
			}
			if (mes==7){
				fechas = "AND HISVEN.Anio IN ('" + anio + "')  AND HISVEN.Mes IN ('Julio') ";
			}
			if (mes==8){
				fechas = "AND HISVEN.Anio IN ('" + anio + "')  AND HISVEN.Mes IN ('Agosto') ";
			}
			if (mes==9){
				fechas = "AND HISVEN.Anio IN ('" + anio + "')  AND HISVEN.Mes IN ('Septiembre') ";
			}
			if (mes==10){
				fechas = "AND HISVEN.Anio IN ('" + anio + "')  AND HISVEN.Mes IN ('Octubre') ";
			}
			if (mes==11){
				fechas = "AND HISVEN.Anio IN ('" + anio + "')  AND HISVEN.Mes IN ('Noviembre') ";
			}
			if (mes==12){
				fechas = "AND HISVEN.Anio IN ('" + anio + "')  AND HISVEN.Mes IN ('Diciembre') ";
			}

		}
		return fechas;
	}

	@SuppressWarnings("deprecation")
	public String convertirDosFechasAStringPeriodoAnterior(Date finicio, Date ffin, String fechaTabla){

		String fecha = "";
		String finicioString = "", ffinString = "";
		int year=0, month=0;


		if(finicio != null && ffin != null){
			year = finicio.getYear() + 1900;
			month = finicio.getMonth() + 1;
			if(month < 10){
				if(finicio.getDate() < 10){
					finicioString = year + "0" + month + "0" + finicio.getDate();
				}else{
					finicioString = year + "0" + month + "" + finicio.getDate();
				}
			}else{
				if(finicio.getDate() < 10){
					finicioString = year + "" + month + "0" + finicio.getDate();
				}else{
					finicioString = year + "" + month + "" + finicio.getDate();
				}
			}

			year = ffin.getYear() + 1900;
			month = ffin.getMonth() + 1;

			if(month < 10){
				if(ffin.getDate() < 10){
					ffinString = year + "0" + month + "0" + ffin.getDate();
				}else{
					ffinString = year + "0" + month + "" + ffin.getDate();
				}
			}else{
				if(ffin.getDate() < 10){
					ffinString = year + "" + month + "0" + ffin.getDate();
				}else{
					ffinString = year + "" + month + "" + ffin.getDate();
				}
			}
			if(ffin.getHours() > 0){
				ffinString += " " + ffin.getHours() + ":" + ffin.getMinutes();
				//finicioString += " 23:59";
			}//       
			fecha = " DATEADD(DAY, - DATEDIFF(DAY,'" + finicioString + "', '" + ffinString + "') -1, '" + finicioString + "' ) AND " + fechaTabla + " <= DATEADD(DAY, -1,'" + finicioString + " 23:59')";

		}
		return fecha;
	}

	public Integer obtenerMes(String periodo, Integer tipoPeriodo,Long mes, String inicialoFinal){
		Integer  valor=0;
		if (periodo.toLowerCase().equals("anual")) {
			if(inicialoFinal.equals("Inicial")){valor = 1;}else{valor = 12;}
		} else if (periodo.toLowerCase().equals("semestral")) {
			if (tipoPeriodo == 1) {
				if(inicialoFinal.equals("Inicial")){valor = 1;}else{valor = 6;}
			} else {
				if(inicialoFinal.equals("Inicial")){valor = 7;}else{valor = 12;}
			}
		} else if (periodo.toLowerCase().equals("trimestral")) {
			switch (tipoPeriodo) {
				case 1:
					if(inicialoFinal.equals("Inicial")){valor = 1;}else{valor = 3;}
					break;
				case 2:
					if(inicialoFinal.equals("Inicial")){valor = 4;}else{valor = 6;}
					break;
				case 3:
					if(inicialoFinal.equals("Inicial")){valor = 7;}else{valor = 9;}
					break;
				case 4:
					if(inicialoFinal.equals("Inicial")){valor = 10;}else{valor = 12;}
					break;
			}
		}else if(periodo.toLowerCase().equals("bimestral")){
			switch(tipoPeriodo){
				case 1:
					if(inicialoFinal.equals("Inicial")){valor = 1;}else{valor = 2;}
					break;
				case 2:
					if(inicialoFinal.equals("Inicial")){valor = 3;}else{valor = 4;}
					break;
				case 3:
					if(inicialoFinal.equals("Inicial")){valor = 5;}else{valor = 6;}
					break;
				case 4:
					if(inicialoFinal.equals("Inicial")){valor = 7;}else{valor = 8;}
					break;
				case 5:
					if(inicialoFinal.equals("Inicial")){valor = 9;}else{valor = 10;}
					break;
				case 6:
					if(inicialoFinal.equals("Inicial")){valor = 11;}else{valor = 12;}
					break;
			}
		}else if (periodo.toLowerCase().equals("mensual")) {
			if (mes == 1){valor = 1;}
			else if (mes == 2){valor = 2;}
			else if (mes == 3){valor = 3;}
			else if (mes == 4){valor = 4;}
			else if (mes == 5){valor = 5;}
			else if (mes == 6){valor = 6;}
			else if (mes == 7){valor = 7;}
			else if (mes == 8){valor = 8;}
			else if (mes == 9){valor = 9;}
			else if (mes == 10){valor = 10;}
			else if (mes == 11){valor = 11;}
			else if (mes == 12){valor = 12;}
		}
		return valor;
	}

	public String obtenerPrimerAlmacen(Boolean almacenUE, Boolean almacenUSA,
									   Boolean almacenMatriz) {
		String primerAlmacen = "";
		if (almacenUE) {
			primerAlmacen = "AlmacenUE";
		} else if (almacenUSA) {
			primerAlmacen = "AlmacenUSA";
		} else if (almacenMatriz) {
			primerAlmacen = "AlmacenMatriz";
		}
		return primerAlmacen;
	}

	public String obtenerDescripcionProducto(Producto producto) {
		String descripcion = "";
		//		como estamos validando unicamente que sea proveedor 44 podrian entrar los que tienen fabrica usp controlado mundial (asesorias) y estas no se ven afectadas


		descripcion = 		producto.getCodigo() + " "+
				producto.getConcepto();
		if (producto.getCantidad() != null && !producto.getCantidad().isEmpty() &&
				producto.getUnidad() != null && !producto.getUnidad().isEmpty() &&
				( producto.getProveedor() != 44 ||
						(producto.getProveedor() == 44 &&
								!isNumeric(producto.getCodigo().trim())))) {
			descripcion += " (" + producto.getCantidad() + " " + producto.getUnidad() + ") ";
		} else if (producto.getCantidad() != null && !producto.getCantidad().isEmpty() && ( producto.getProveedor() != 44 || (producto.getProveedor() == 44 && !isNumeric(producto.getCodigo().trim())))) {
			descripcion += " (" + producto.getCantidad() + ") ";
		} else {
			descripcion += " ";
		}
		descripcion += producto.getFabrica();
		//		if(producto.getTiempoEntrega()!=null && !producto.getTiempoEntrega().equals("")){descripcion += " " + producto.getTiempoEntrega();}
		if(producto.getTipo() != null){
			if (producto.getTipo().toLowerCase().equals("estandares")
					|| producto.getTipo().equals("reactivos")) {
				if (producto.getPureza() != null) {
					if (producto.getPureza() == 0) {
						descripcion += " S/Pureza";
					} else if (producto.getPureza() == 1) {
						descripcion += " C/Pureza";
					}
				}
			}
		}
		return descripcion;
	}

	public String obtenerTiempoRespuesta(Date fechaAlmacen, Date fechaArribo) {
		String tiempoRespuesta = "";
		if (fechaArribo == null || fechaAlmacen == null) {
			tiempoRespuesta = "N/D";
		} else {
			if (fechaArribo.before(fechaAlmacen)
					|| fechaArribo.equals(fechaAlmacen)) {
				tiempoRespuesta = "T";
			} else {
				tiempoRespuesta = "R";
			}
		}
		return tiempoRespuesta;
	}

	public String leerCodigoBarras(byte[] imagen) throws Exception {

		Reader reader = new MultiFormatReader();
		InputStream in = new ByteArrayInputStream(imagen);
		BufferedImage barCodeBufferedImage = ImageIO.read(in);
		LuminanceSource source = new BufferedImageLuminanceSource(barCodeBufferedImage);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

		Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
		hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
		Result result = reader.decode(bitmap, hints);

		return result.getText();
	}

	public Date calcularFechaSiguiente(Date fechaInicio){
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(fechaInicio);

		if(calendario.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) calendario.add(Calendar.DAY_OF_WEEK, 3);
		else calendario.add(Calendar.DAY_OF_WEEK, 1);
		return calendario.getTime();
	}
	/***
	 * Calcula la nueva fecha, sumando o restando los dias, si la nueva fecha cae en fin de semana, se calcula para que 
	 * @param date
	 * @param days
	 * @return
	 */
	public Date calcularNuevaFecha(Date date, int days)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days); //minus number would decrement the days
		if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
			cal.add(Calendar.DATE, -2);
		}
		if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
			cal.add(Calendar.DATE, -1);
		}

		return cal.getTime();
	}

	@SuppressWarnings("deprecation")
	public void sumarPartidas(Date fechaPrimerAlmacen, Date fechaActual,
							  Integer[][] sumasTotales) {
		if (fechaActual.before(fechaPrimerAlmacen)
				|| fechaActual.equals(fechaPrimerAlmacen)) {
			sumasTotales[fechaPrimerAlmacen.getMonth()][1]++;
		} else {
			sumasTotales[fechaPrimerAlmacen.getMonth()][1]++;
		}
	}


	/**
	 * Devuelve tres dias habiles de una fecha inicial
	 * @param fechaInicio
	 * @return
	 */
	public Date calcularTresDiasDespues(Date fechaInicio){

		Calendar calendario = Calendar.getInstance();
		calendario.setTime(fechaInicio);

		if (calendario.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY
				|| calendario.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY
				|| calendario.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY){
			calendario.add(Calendar.DAY_OF_WEEK, 5);
		}else{
			calendario.add(Calendar.DAY_OF_WEEK, 3);
		}
		return calendario.getTime();
	}
	public String calcularDifHorasSoloHabiles(Date fInicio, Date fFin, List<Date> dFestivos){
		try{
			Integer diasHabiles=0, finSemana = 0;
			String tiempoH="",tiempoM="";

			Calendar fechaInicial = Calendar.getInstance();
			Calendar fechafinal = Calendar.getInstance();
			fechaInicial.setTime(fInicio);
			fechafinal.setTime(fFin);
			//long miliFI = fechaInicial.getTimeInMillis();
			long diff = fechafinal.getTimeInMillis()-fechaInicial.getTimeInMillis();

			boolean viernes=false;

			//			log.info(fechaInicial.getTime());

			if(fechafinal.compareTo(fechaInicial)<0){
				return "00:00";
			}
			else if(fechaInicial.get(Calendar.YEAR)==fechafinal.get(Calendar.YEAR)&&
					fechaInicial.get(Calendar.MONTH)==fechafinal.get(Calendar.MONTH) &&
					fechaInicial.get(Calendar.DAY_OF_MONTH)== fechafinal.get(Calendar.DAY_OF_MONTH) &&
					fechaInicial.get(Calendar.DAY_OF_WEEK) == fechafinal.get(Calendar.DAY_OF_WEEK) &&
					fechaInicial.get(Calendar.DAY_OF_WEEK)!=Calendar.SATURDAY &&
					fechaInicial.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY){
				if((diff/(60*1000))/60<10){
					tiempoH ="0"+Integer.toString((int) ((diff/(60*1000))/60));
				}else{
					tiempoH =Integer.toString((int) ((diff/(60*1000))/60));
				}

				if((diff/(60*1000))%60<10){
					tiempoM ="0"+Integer.toString((int) ((diff/(60*1000))%60));
				}else{
					tiempoM =Integer.toString((int) ((diff/(60*1000))%60));
				}
			}else{
				Calendar fechaFinalDiasHabiles = Calendar.getInstance();
				fechaFinalDiasHabiles.setTime(fFin);
				fechaFinalDiasHabiles.set(Calendar.HOUR_OF_DAY, 23);

				while(fechaInicial.before(fechaFinalDiasHabiles)){
					boolean diaFestivo = false;
					for(Date df : dFestivos){
						if(fechaInicial.equals(df)){
							diaFestivo = true;
						}
					}

					if(fechaInicial.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && fechaInicial.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && diaFestivo == false){
						diasHabiles++;
					}

					if(fechaInicial.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY){
						viernes = true;
					}

					fechaInicial.add(Calendar.DATE, 1);

					if(fechaInicial.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY && viernes) {
						finSemana++;
						viernes = false;
					}
				}

				int diaInicio = 21600000;
				int diaFin = 28800000;
				diasHabiles = diasHabiles - 2;
				if(diasHabiles < 0){ diasHabiles = 0;}

				diff = diff-((diasHabiles*50400000)+(finSemana*172800000) + diaInicio + diaFin);


				if(diff < 0){
					diff = 0;
				}

				if(((diff/(60*1000))/60) < 10){
					tiempoH ="0"+Integer.toString((int) ((diff/(60*1000))/60));
				}else{
					tiempoH =Integer.toString((int) ((diff/(60*1000))/60));
				}

				if((diff/(60*1000))%60<10){
					tiempoM ="0"+Integer.toString((int) ((diff/(60*1000))%60));
				}else{
					tiempoM =Integer.toString((int) ((diff/(60*1000))%60));
				}
			}

			return tiempoH +":"+ tiempoM;
		}catch(Exception e){
			logger.warning(e.getMessage());
			return "00:00";
		}

	}
	public String calcularDiferenciaDeHoras(Date fInicio, Date fFin) {
		SimpleDateFormat formatoFechaHora = new SimpleDateFormat("yyyyMMdd HH:mm");
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd");
		Integer diasHabiles=0, horasNoHabiles=null;
		String tiempoH="",tiempoM="";

		try {
			//Dar el formato a la fecha de solo horas y minutos quitando los 
			//segundos para hacer la suma y resta correctamente.
			Date fI = formatoFechaHora.parse(formatoFechaHora.format(fInicio));
			Date fF = formatoFechaHora.parse(formatoFechaHora.format(fFin));
			Date horaOchoAm = formatoFechaHora.parse(formatoFecha.format(fFin) + " 08:00");
			Date horaSeisPm = formatoFechaHora.parse(formatoFecha.format(fInicio) + " 18:00");

			Calendar fechaInicial = Calendar.getInstance();
			Calendar fechafinal = Calendar.getInstance();
			fechaInicial.setTime(fI);
			fechafinal.setTime(fF);

			if(fechafinal.compareTo(fechaInicial)==1){
				do{
					//Si el dia de la semana de la fecha minima es igual a sabado o domingo 
					if (fechaInicial.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && fechaInicial.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
						diasHabiles =  diasHabiles + 1;
					}
					fechaInicial.add(Calendar.DAY_OF_WEEK, 1);
				}while (fechaInicial.getTimeInMillis() < fechafinal.getTimeInMillis());
				diasHabiles = diasHabiles - 1;
				long diferenciaMils = 0;
				if(diasHabiles!=0){
					if(diasHabiles>1){
						horasNoHabiles = (diasHabiles) * (36000000);
					}else{
						horasNoHabiles = 0;
					}
					diferenciaMils = (horaSeisPm.getTime() - fI.getTime()) + (fF.getTime() - horaOchoAm.getTime());
					diferenciaMils = diferenciaMils + horasNoHabiles;
				}else{
					diferenciaMils = fF.getTime() - fI.getTime();
				}
				long segundos = diferenciaMils / 1000;
				long horas = segundos / 3600;
				segundos -= horas * 3600;
				long minutos = segundos / 60;
				segundos -= minutos * 60;

				if(horas<10){tiempoH = "0" + horas;}else{tiempoH = Long.toString(horas);}
				if(minutos<10){tiempoM = "0" + minutos;}else{tiempoM = Long.toString(minutos);}

				return tiempoH +":"+ tiempoM;
			}else{
				return "00:00";
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String calcularDiferenciaDeHorasCompletas(Date fInicio, Date fFin) { //24 hrs solo dias habiles
		SimpleDateFormat formatoFechaHora = new SimpleDateFormat("yyyyMMdd HH:mm");
		//		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd");
		Integer diasNOHabiles=0, horasNoHabiles=null;
		String tiempoH="",tiempoM="";

		try {
			//Dar el formato a la fecha de solo horas y minutos quitando los 
			//segundos para hacer la suma y resta correctamente.
			Date fI = formatoFechaHora.parse(formatoFechaHora.format(fInicio));
			Date fF = formatoFechaHora.parse(formatoFechaHora.format(fFin));

			Calendar fechaInicial = Calendar.getInstance();
			Calendar fechafinal = Calendar.getInstance();
			fechaInicial.setTime(fI);
			fechafinal.setTime(fF);

			if(fechafinal.compareTo(fechaInicial)==1){
				do{
					//Si el dia de la semana de la fecha minima es igual a sabado o domingo 
					if (fechaInicial.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && fechaInicial.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
					}else{
						diasNOHabiles =  diasNOHabiles + 1;
					}
					fechaInicial.add(Calendar.DAY_OF_WEEK, 1);
				}while (fechaInicial.getTimeInMillis() < fechafinal.getTimeInMillis());

				long diferenciaMils = 0;
				diferenciaMils = fF.getTime() - fI.getTime();
				if(diasNOHabiles!=0){
					if(diasNOHabiles>0){
						horasNoHabiles = (diasNOHabiles) * (86400000);
					}else{
						horasNoHabiles = 0;
					}

					diferenciaMils = diferenciaMils - horasNoHabiles;
				}


				long segundos = diferenciaMils / 1000;
				long horas = segundos / 3600;
				segundos -= horas * 3600;
				long minutos = segundos / 60;
				segundos -= minutos * 60;

				if(horas<10){tiempoH = "0" + horas;}else{tiempoH = Long.toString(horas);}
				if(minutos<10){tiempoM = "0" + minutos;}else{tiempoM = Long.toString(minutos);}

				return tiempoH +":"+ tiempoM;
			}else{
				return "00:00";
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("deprecation")
	public String sumarDoshoras(String primerHora, String segundaHora){
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		String hora = "";
		int tHoras=0;
		String[] horas1;
		String[] horas2;

		if(primerHora == null || primerHora.equals("")){
			return segundaHora;
		}else if(segundaHora == null || segundaHora.equals("")){
			return primerHora;
		}else{
			try {
				Date fechaUno = new SimpleDateFormat("HH:mm").parse(primerHora);
				Date fechaDos = new SimpleDateFormat("HH:mm").parse(segundaHora);
				fechaUno.setHours(0);
				fechaDos.setHours(0);
				c1.setTime(fechaUno);
				c2.setTime(fechaDos);
				Calendar cTotal = (Calendar) c1.clone();

				horas1 =  primerHora.split(":");
				horas2 = segundaHora.split(":");
				tHoras = Integer.parseInt(horas1[0]) + Integer.parseInt(horas2[0]);

				cTotal.add(Calendar.MINUTE, c2.get(Calendar.MINUTE));

				tHoras =  cTotal.getTime().getHours() + tHoras;
				hora = tHoras + ":" + cTotal.getTime().getMinutes();

			} catch (ParseException ex) {
				log.info("Error: " + ex.getMessage());
				hora = null;
			}
			return hora;
		}
	}

	public Boolean enTiempo(String tiempo, Integer horas){
		Boolean resultado = false;
		String[] temp;
		String delimiter = ":";
		temp = tiempo.split(delimiter);
		try {
			//			int x = Integer.parseInt(temp[0]);
			if(Integer.parseInt(temp[0])<horas){
				resultado = true;
			}else if(Integer.parseInt(temp[0])==horas && Integer.parseInt(temp[1])==0){
				resultado = true;
			}else
				resultado = false;
			return resultado;
		}
		catch (Exception e) {
			logger.warning(e.getMessage());
			return null;
		}
	}

	public String obtenerMesConLetra(Integer mes){
		String valor="";
		if(mes==1){
			valor="Enero";
		}else if(mes==2){
			valor="Febrero";
		}else if(mes==3){
			valor="Marzo";
		}else if(mes==4){
			valor="Abril";
		}else if(mes==5){
			valor="Mayo";
		}else if(mes==6){
			valor="Junio";
		}else if(mes==7){
			valor="Julio";
		}else if(mes==8){
			valor="Agosto";
		}else if(mes==9){
			valor="Septiembre";
		}else if(mes==10){
			valor="Octubre";
		}else if(mes==11){
			valor="Noviembre";
		}else if(mes==12){
			valor="Diciembre";
		}
		return valor;
	}

	public String obtenerTiempoTranscurridoSQL(String tablaFInicio, String tablaFFin, String medidaTiempo){
		String sql = "";
		tablaFFin = "COALESCE("+ tablaFFin +",GETDATE())";
		if(medidaTiempo.equals("Minutos")){
			sql =" (SELECT DATEDIFF(MI," + tablaFInicio + ", " + tablaFFin + ") - DATEDIFF(week," + tablaFInicio + "," + tablaFFin + ") * 2 * 24 * 60 - " +
					" CASE WHEN DATENAME(dw, " + tablaFInicio + ") <> 'Saturday' AND DATENAME(dw, " + tablaFFin + ") = 'Saturday' THEN 24 * 60  " +
					" WHEN DATENAME(dw, " + tablaFInicio + ") = 'Saturday' AND DATENAME(dw, " + tablaFFin + ") <> 'Saturday' THEN -24 * 60 ELSE 0 END)";
		}else if(medidaTiempo.equals("Horas")){
			sql =" (SELECT DATEDIFF(HH," + tablaFInicio + ", " + tablaFFin + ") - DATEDIFF(week," + tablaFInicio + "," + tablaFFin + ") * 2 * 24  - " +
					" CASE WHEN DATENAME(dw, " + tablaFInicio + ") <> 'Saturday' AND DATENAME(dw, " + tablaFFin + ") = 'Saturday' THEN 24   " +
					" WHEN DATENAME(dw, " + tablaFInicio + ") = 'Saturday' AND DATENAME(dw, " + tablaFFin + ") <> 'Saturday' THEN -24  ELSE 0 END)";

		}else if(medidaTiempo.equals("Dias")){
			sql =" (SELECT DATEDIFF(D," + tablaFInicio + ", " + tablaFFin + ") - DATEDIFF(week," + tablaFInicio + "," + tablaFFin + ") * 2  - " +
					" CASE WHEN DATENAME(dw, " + tablaFInicio + ") <> 'Saturday' AND DATENAME(dw, " + tablaFFin + ") = 'Saturday' THEN 1  " +
					" WHEN DATENAME(dw, " + tablaFInicio + ") = 'Saturday' AND DATENAME(dw, " + tablaFFin + ") <> 'Saturday' THEN -1 ELSE 0 END)";

		}
		return sql;
	}

	public String getCodigoPais(String pais){
		//Identificar el codigo del pais con el ISO 3166-1 alpha-2
		String codigoPostalDelReceptor="";
		if(pais.equals("PERU")){
			codigoPostalDelReceptor="PE";
		}else if(pais.equals("PANAMA")){
			codigoPostalDelReceptor="PA";
		}else if(pais.equals("EL SALVADOR")){
			codigoPostalDelReceptor="SV";
		}else if(pais.equals("VENEZUELA")){
			codigoPostalDelReceptor="VE";
		}else if(pais.equals("COSTA RICA")){
			codigoPostalDelReceptor="CR";
		}else if(pais.equals("MEXICO")){
			codigoPostalDelReceptor="MX";
		}else if(pais.equals("ECUADOR")){
			codigoPostalDelReceptor="EC";
		}else if(pais.equals("CUBA")){
			codigoPostalDelReceptor="CU";
		}else if(pais.equals("CHILE")){
			codigoPostalDelReceptor="CL";
		}else if(pais.equals("GUATEMALA")){
			codigoPostalDelReceptor="GT";
		}else if(pais.equals("SIRIA")){
			codigoPostalDelReceptor="SY";
		}else if(pais.equals("ESTADOS UNIDOS")){
			codigoPostalDelReceptor="US";
		}else if(pais.equals("NICARAGUA")){
			codigoPostalDelReceptor="NI";
		}else if(pais.equals("REPUBLICA DOMINICANA")){
			codigoPostalDelReceptor="DO";
		}else if(pais.equals("PARAGUAY")){
			codigoPostalDelReceptor="PY";
		}else if(pais.equals("COLOMBIA")){
			codigoPostalDelReceptor="CO";
		}else if(pais.equals("ARGENTINA")){
			codigoPostalDelReceptor="AR";
		}

		return codigoPostalDelReceptor;
	}

	public void previewPDF(File archivo, String nombreArchivo){
		try {
			//prepare Pdf document
			PDDocument document = PDDocument.load(archivo);
			FlexiablePdfImageWriter imageWriter = new FlexiablePdfImageWriter(document);

			//se puede cambiar la configuracion por default
			//imageWriter.setImageFormat("png");
			//imageWriter.setResolution(64);
			//Tamano original PDFBox, calcula su tamano de pagina, margenes y otros.
			BufferedImage orginalImage = imageWriter.getOriginalImageForSinglePage(1);

			//generar imagenes en miniatura, tamano: 180 * 236
			BufferedImage thumbnail = imageWriter.resizeImage(orginalImage, 180, 200);
			imageWriter.writeImagte(thumbnail, "/Users/fmartinez/Desktop/" + nombreArchivo + ".jpg");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List<String> splitEqually(String text, int size) {
		List<String> ret = new ArrayList<String>((text.length() + size - 1) / size);
		for (int start = 0; start < text.length(); start += size) {
			ret.add(text.substring(start, Math.min(text.length(), start + size)));
		}
		return ret;
	}

	public String decodificarBase64(String base64String,String tipoComprobante,String subCarpeta,
									String nombreArchivo,String extArchivo) throws ProquifaNetException {
		try {
			String ruta = obtenerRutaServidor(tipoComprobante,subCarpeta) + nombreArchivo + "." + extArchivo;
			byte[] dec = BASE64DecoderStream.decode(base64String.getBytes());
			File file = new File(ruta);
			FileOutputStream fos;
			fos = new FileOutputStream(file);
			fos.write(dec);
			fos.close();
			return ruta;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "No existe ubicacion para ese tipo de archivo";
		} catch (IOException e) {
			e.printStackTrace();
			return "No existe ubicacion para ese tipo de archivo";
		}
	}

	public Boolean validaRFC(String rfc){
		try {
			Boolean validaExpReg = false;
			Pattern personaMoral = Pattern.compile("^[A-Z,&,']{3}(\\d{6})((\\D|\\d){3})$");
			Matcher objRegExp = personaMoral.matcher(rfc);
			if(objRegExp.find()){
				validaExpReg = true;
			}else{
				Pattern personaFisica = Pattern.compile("^[A-Z,&,]{4}(\\d{6})((\\D|\\d){3})$");
				Matcher objRegExpFisica = personaFisica.matcher(rfc);
				if(objRegExpFisica.find()){
					validaExpReg = true;
				}
			}
			return validaExpReg;
		} catch (Exception e) {
			//			logger.info("Error: " + e.getMessage());
			return false;
		}
	}

	public String ConvertirNumerosALetras(String numero,String moneda) {
		String literal = "";
		String parte_decimal;
		if(moneda.equals("USD")){
			moneda="Dolares";
		}else if(moneda.equals("MXN")){
			moneda="Pesos";
		}else if(moneda.equals("EUR")){
			moneda="Euros";
		}

		//si el numero no tiene parte decimal, se le agrega ,00
		if(numero.indexOf(".")==-1){
			numero = numero + ".00";
		}
		//se valida formato de entrada -> 0.00 y 999 999 999.00
		if (Pattern.matches("\\d{1,9}.\\d{1,2}", numero)) {
			//se divide el numero 0000000.00 -> entero y decimal
			String[] Num = numero.split("\\.");
			//de da formato al numero decimal
			parte_decimal = " " + Num[1] + "/100";
			//se convierte el numero a literal
			if (Integer.parseInt(Num[0]) == 0) {//si el valor es cero
				literal = "cero ";
			} else if (Integer.parseInt(Num[0]) > 999999) {//si es millon
				literal = getMillones(Num[0]);
			} else if (Integer.parseInt(Num[0]) > 999) {//si es miles
				literal = getMiles(Num[0]);
			} else if (Integer.parseInt(Num[0]) > 99) {//si es centena
				literal = getCentenas(Num[0]);
			} else if (Integer.parseInt(Num[0]) > 9) {//si es decena
				literal = getDecenas(Num[0]);
			} else {//sino unidades -> 9
				literal = getUnidades(Num[0]);
			}
			return (literal + moneda + parte_decimal).toUpperCase();
		} else {//error, no se puede convertir
			return literal = null;
		}
	}

	/*funciones para convertir los numeros*/
	private String getUnidades(String numero) {// 1 - 9
		//si tuviera algun 0 antes se lo quita -> 09 = 9 o 009=9
		String num = numero.substring(numero.length() - 1);
		return UNIDADES[Integer.parseInt(num)];
	}

	private String getDecenas(String num) {// 99                        
		int n = Integer.parseInt(num);
		if (n < 10) {//para casos como -> 01 - 09
			return getUnidades(num);
		} else if (n > 19) {//para 20...99
			String u = getUnidades(num);
			if (u.equals("")) { //para 20,30,40,50,60,70,80,90
				return DECENAS[Integer.parseInt(num.substring(0, 1)) + 8];
			} else {
				return DECENAS[Integer.parseInt(num.substring(0, 1)) + 8] + "y " + u;
			}
		} else {//numeros entre 11 y 19
			return DECENAS[n - 10];
		}
	}

	private String getCentenas(String num) {// 999 o 099
		if( Integer.parseInt(num)>99 ){//es centena
			if (Integer.parseInt(num) == 100) {//caso especial
				return " cien ";
			} else {
				return CENTENAS[Integer.parseInt(num.substring(0, 1))] + getDecenas(num.substring(1));
			}
		}else{//por Ej. 099 
			//se quita el 0 antes de convertir a decenas
			return getDecenas(Integer.parseInt(num)+"");
		}
	}

	private String getMiles(String numero) {// 999 999
		//obtiene las centenas
		String c = numero.substring(numero.length() - 3);
		//obtiene los miles
		String m = numero.substring(0, numero.length() - 3);
		String n="";
		//se comprueba que miles tenga valor entero
		if (Integer.parseInt(m) > 0) {
			n = getCentenas(m);
			return n + "mil " + getCentenas(c);
		} else {
			return "" + getCentenas(c);
		}
	}

	private String getMillones(String numero) { //000 000 000        
		//se obtiene los miles
		String miles = numero.substring(numero.length() - 6);
		//se obtiene los millones
		String millon = numero.substring(0, numero.length() - 6);
		String n = "";
		if(millon.length()>1){
			n = getCentenas(millon) + "millones ";
		}else{
			n = getUnidades(millon) + "millon ";
		}
		return n + getMiles(miles);
	}

	public String formatoNumero(Double valor){
		DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
		decimalFormatSymbols.setDecimalSeparator('.');
		DecimalFormat decimalFormat = new DecimalFormat("#.##",decimalFormatSymbols);
		return decimalFormat.format(valor);
	}

	public double convertirFormatoMoneda(Double valor){
		BigDecimal bd = new BigDecimal(valor);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public boolean isNumeric(String cadena){
		try {Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}

	public String obtenerLinealFecha(Long anio, String periodo, Integer tipoPeriodo,
									 Long mes) {
		String fechas = "";
		Integer diaFinMes = 0;
		NumberFormat nf = NumberFormat.getInstance();
		if (periodo.toLowerCase().equals("anual")) {
			fechas = anio-1 + "" ;
		} else if (periodo.toLowerCase().equals("semestral")) {
			if (tipoPeriodo == 1) {
				fechas = anio + "0101 00:00' ";
			} else {
				fechas = anio + "0701 00:00' ";
			}
		} else if (periodo.toLowerCase().equals("trimestral")) {
			switch (tipoPeriodo) {
				case 1:
					fechas = anio + "0101 00:00' and 0331 23:59";
					break;
				case 2:
					fechas = anio + "0401 00:00' and 0630 23:59";
					break;
				case 3:
					fechas = anio + "0701 00:00' and 0930 23:59";
					break;
				case 4:
					fechas = anio + "1001 00:00' and 1231 23:59";
					break;
			}
		}else if(periodo.toLowerCase().equals("bimestral")){
			switch(tipoPeriodo){
				case 1:
					fechas = anio + "0101 00:00' and 0228 23:59";
					break;
				case 2:
					fechas = anio + "0301 00:00' and 0430 23:59";
					break;
				case 3:
					fechas = anio + "0501 00:00' and 0630 23:59";
					break;
				case 4:
					fechas = anio + "0701 00:00' and 0831 23:59";
					break;
				case 5:
					fechas = anio + "0901 00:00' and1031 23:59";
					break;
				case 6:
					fechas = anio + "1101 00:00' and 1231 23:59";
					break;
			}
		}else if (periodo.toLowerCase().equals("mensual")) {
			if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8
					|| mes == 10 || mes == 12) {
				diaFinMes = 31;
			} else if (mes == 4 || mes == 6 || mes == 9 || mes == 11) {
				diaFinMes = 30;
			} else {
				diaFinMes = 28;
			}
			nf.setMinimumIntegerDigits(2);
			nf.setGroupingUsed(false);
			String mesT = nf.format(mes);
			fechas = anio + mesT + "01 00:00' and " + anio + mesT
					+ diaFinMes + " 23:59";
		}
		return fechas;
	}



	public DatosCompLineal obtenerDatosLineal (Long idCliente,
											   Long anio, String periodo, Integer tipoPeriodo, Long mes,
											   String esac, Long eventas) {

		DatosCompLineal datos = new DatosCompLineal();

		datos.setAnio(anio);
		datos.setAnio1(anio);
		datos.setAnio2(anio);
		datos.setAnio3(anio);
		datos.setAnio4(anio);
		datos.setAnio5(anio);
		datos.setMes(mes);
		datos.setMes1(mes);
		datos.setMes2(mes);
		datos.setMes3(mes);
		datos.setMes4(mes);
		datos.setMes5(mes);
		datos.setPeriodo(periodo);
		datos.setPeriodo1(periodo);
		datos.setPeriodo2(periodo);
		datos.setPeriodo3(periodo);
		datos.setPeriodo4(periodo);
		datos.setPeriodo5(periodo);

		datos.setTipoperiodo(tipoPeriodo);
		datos.setTipoperiodo1(tipoPeriodo);
		datos.setTipoperiodo2(tipoPeriodo);
		datos.setTipoperiodo3(tipoPeriodo);
		datos.setTipoperiodo4(tipoPeriodo);
		datos.setTipoperiodo5(tipoPeriodo);


		if (periodo.toLowerCase().equals("anual")) {
			datos.setAnio1(anio-1);
			datos.setTipoperiodo1(tipoPeriodo);
			datos.setAnio2(anio-2);
			datos.setTipoperiodo2(tipoPeriodo);
			datos.setAnio3(anio-3);
			datos.setTipoperiodo3(tipoPeriodo);
			datos.setAnio4(anio-4);
			datos.setTipoperiodo4(tipoPeriodo);
			datos.setAnio5(anio-5);
			datos.setTipoperiodo5(tipoPeriodo);

		} else if (periodo.toLowerCase().equals("semestral")) {
			if (tipoPeriodo == 1) {
				datos.setAnio1(anio-1);
				datos.setTipoperiodo1(2);
				datos.setAnio2(anio-1);
				datos.setTipoperiodo2(1);
				datos.setAnio3(anio-2);
				datos.setTipoperiodo3(2);
				datos.setAnio4(anio-2);
				datos.setTipoperiodo4(1);
				datos.setAnio5(anio-3);
				datos.setTipoperiodo5(2);

			} else {
				datos.setAnio1(anio);
				datos.setTipoperiodo1(1);
				datos.setAnio2(anio-1);
				datos.setTipoperiodo2(2);
				datos.setAnio3(anio-1);
				datos.setTipoperiodo3(1);
				datos.setAnio4(anio-2);
				datos.setTipoperiodo4(2);
				datos.setAnio5(anio-2);
				datos.setTipoperiodo5(1);

			}
		} else if (periodo.toLowerCase().equals("trimestral")) {
			switch (tipoPeriodo) {
				case 1:
					datos.setAnio1(anio-1);
					datos.setTipoperiodo1(4);
					datos.setAnio2(anio-1);
					datos.setTipoperiodo2(3);
					datos.setAnio3(anio-1);
					datos.setTipoperiodo3(2);
					datos.setAnio4(anio-1);
					datos.setTipoperiodo4(1);
					datos.setAnio5(anio-2);
					datos.setTipoperiodo5(4);
					break;
				case 2:
					datos.setAnio1(anio);
					datos.setTipoperiodo1(1);
					datos.setAnio2(anio-1);
					datos.setTipoperiodo2(4);
					datos.setAnio3(anio-1);
					datos.setTipoperiodo3(3);
					datos.setAnio4(anio-1);
					datos.setTipoperiodo4(2);
					datos.setAnio5(anio-1);
					datos.setTipoperiodo5(1);
					break;
				case 3:
					datos.setAnio1(anio);
					datos.setTipoperiodo1(2);
					datos.setAnio2(anio);
					datos.setTipoperiodo2(1);
					datos.setAnio3(anio-1);
					datos.setTipoperiodo3(4);
					datos.setAnio4(anio-1);
					datos.setTipoperiodo4(3);
					datos.setAnio5(anio-1);
					datos.setTipoperiodo5(2);
					break;
				case 4:
					datos.setAnio1(anio);
					datos.setTipoperiodo1(3);
					datos.setAnio2(anio);
					datos.setTipoperiodo2(2);
					datos.setAnio3(anio);
					datos.setTipoperiodo3(1);
					datos.setAnio4(anio-1);
					datos.setTipoperiodo4(4);
					datos.setAnio5(anio-1);
					datos.setTipoperiodo5(3);
					break;
			}
		}else if (periodo.toLowerCase().equals("mensual")) {

			if (mes == 1) {
				datos.setAnio1(anio-1);
				datos.setMes1(12L);
				datos.setAnio2(anio-1);
				datos.setMes2(11L);
				datos.setAnio3(anio-1);
				datos.setMes3(10L);
				datos.setAnio4(anio-1);
				datos.setMes4(9L);
				datos.setAnio5(anio-1);
				datos.setMes5(8L);

			}else if (mes == 2) {
				datos.setAnio1(anio);
				datos.setMes1(1L);
				datos.setAnio2(anio-1);
				datos.setMes2(12L);
				datos.setAnio3(anio-1);
				datos.setMes3(11L);
				datos.setAnio4(anio-1);
				datos.setMes4(10L);
				datos.setAnio5(anio-1);
				datos.setMes5(9L);

			}else if (mes == 3) {
				datos.setAnio1(anio);
				datos.setMes1(2L);
				datos.setAnio2(anio);
				datos.setMes2(1L);
				datos.setAnio3(anio-1);
				datos.setMes3(12L);
				datos.setAnio4(anio-1);
				datos.setMes4(11L);
				datos.setAnio5(anio-1);
				datos.setMes5(10L);

			}else if (mes == 4) {
				datos.setAnio1(anio);
				datos.setMes1(3L);
				datos.setAnio2(anio);
				datos.setMes2(2L);
				datos.setAnio3(anio);
				datos.setMes3(1L);
				datos.setAnio4(anio-1);
				datos.setMes4(12L);
				datos.setAnio5(anio-1);
				datos.setMes5(11L);

			}else if (mes == 5) {
				datos.setAnio1(anio);
				datos.setMes1(4L);
				datos.setAnio2(anio);
				datos.setMes2(3L);
				datos.setAnio3(anio);
				datos.setMes3(2L);
				datos.setAnio4(anio);
				datos.setMes4(1L);
				datos.setAnio5(anio-1);
				datos.setMes5(12L);

			}
			else{
				datos.setAnio1(anio);
				datos.setMes1(mes-1);
				datos.setAnio2(anio);
				datos.setMes2(mes-2);
				datos.setAnio3(anio);
				datos.setMes3(mes-3);
				datos.setAnio4(anio);
				datos.setMes4(mes-4);
				datos.setAnio5(anio);
				datos.setMes5(mes-5);
			}
		}else if (periodo.toLowerCase().equals("quincenal")) {
			if(tipoPeriodo == 1){
				datos.setTipoperiodo1(2);
				datos.setTipoperiodo2(1);
				datos.setTipoperiodo3(2);
				datos.setTipoperiodo4(1);
				datos.setTipoperiodo5(2);

				if(mes == 1){
					datos.setAnio1(anio - 1);
					datos.setAnio2(anio - 1);
					datos.setAnio3(anio - 1);
					datos.setAnio4(anio - 1);
					datos.setAnio5(anio - 1);

					datos.setMes1(12L);
					datos.setMes2(12L);
					datos.setMes3(11L);
					datos.setMes4(11L);
					datos.setMes5(10L);

				}else if(mes == 2){
					datos.setAnio3(anio - 1);
					datos.setAnio4(anio - 1);
					datos.setAnio5(anio - 1);

					datos.setMes1(mes - 1);
					datos.setMes2(mes -1 );
					datos.setMes3(12L);
					datos.setMes4(12L);
					datos.setMes5(11L);
				}else if(mes == 3){
					datos.setAnio3(anio - 1);
					datos.setAnio4(anio - 1);
					datos.setAnio5(anio - 1);

					datos.setMes1(mes - 1);
					datos.setMes2(mes - 1);
					datos.setMes3(mes - 2);
					datos.setMes4(mes - 2);
					datos.setMes5(12L);
				}else {
					datos.setMes1(mes - 1);
					datos.setMes2(mes - 1);
					datos.setMes3(mes - 2);
					datos.setMes4(mes - 2);
					datos.setMes5(mes - 3);
				}

			}else{
				datos.setTipoperiodo1(1);
				datos.setTipoperiodo2(2);
				datos.setTipoperiodo3(1);
				datos.setTipoperiodo4(2);
				datos.setTipoperiodo5(1);

				if(mes == 1){
					datos.setAnio2(anio - 1);
					datos.setAnio3(anio - 1);
					datos.setAnio4(anio - 1);
					datos.setAnio5(anio - 1);

					datos.setMes2(12L);
					datos.setMes3(12L);
					datos.setMes4(11L);
					datos.setMes5(11L);

				}else if(mes == 2){
					datos.setAnio4(anio - 1);
					datos.setAnio5(anio - 1);

					datos.setMes2(mes - 1);
					datos.setMes3(mes - 1);
					datos.setMes4(12L);
					datos.setMes5(12L);
				}else {

					datos.setMes2(mes - 1);
					datos.setMes3(mes - 1);
					datos.setMes4(mes - 2);
					datos.setMes5(mes - 2);
				}


			}

		}
		return datos;
	}

	public Date [] calcularFechasPeriodoAnterior(Date finicio, Date ffin){

		int diasPeriodo=0,count =0;
		Date [] periodo=new Date[2];

		Calendar fIniCa = Calendar.getInstance();
		fIniCa.setTime(finicio);
		Calendar fFinCa = Calendar.getInstance();
		fFinCa.setTime(ffin);


		if(fIniCa.equals(fFinCa)){
			//if(fIniCa.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY  && fIniCa.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY ){
			do{
				fIniCa.add(Calendar.DAY_OF_WEEK, -1);
				if(fIniCa.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY  && fIniCa.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY ){
					//if(count < 1){
					//fechas = "= '" + funcion.convertirFechaAString(fInicio.getTime()) + "' ";
					periodo[0] = periodo[1]=fIniCa.getTime();
					count++;
				}
			}while(count<1);
			//}
		}
		else {

			while(fIniCa.before(fFinCa) || fIniCa.equals(fFinCa)){

				if(fIniCa.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && fIniCa.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
					diasPeriodo++;

				fIniCa.add(Calendar.DATE, 1);
			}

			periodo[0]= calcularFechaInicioAnteriorXNumDias(finicio,diasPeriodo);

			Calendar fFinalCa = Calendar.getInstance();
			fFinalCa.setTime(finicio);
			fFinalCa.add(Calendar.DAY_OF_WEEK,-1);

			if(fFinalCa.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
				fFinalCa.add(Calendar.DATE, -2);
			else if(fFinalCa.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
				fFinalCa.add(Calendar.DATE, -1);

			periodo[1]=fFinalCa.getTime();
		}

		return periodo;
	}

	private Date calcularFechaInicioAnteriorXNumDias(Date finicio,
													 int diasPeriodo) {

		Calendar fechaInicio;
		fechaInicio = Calendar.getInstance();
		fechaInicio.setTime(finicio);
		int dias;

		fechaInicio.add(Calendar.DAY_OF_WEEK, -1);

		if(fechaInicio.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && fechaInicio.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
			dias = 1;
		else
			dias=0;

		while(dias<diasPeriodo){

			fechaInicio.add(Calendar.DATE, -1);
			if(fechaInicio.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && fechaInicio.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
				dias++;
		}

		return fechaInicio.getTime();
	}
	public Long calcularDiferenciaDeDias(Date fechaInicial, Date fechaFinal) {
		Calendar fechaInicio, fechaFin;
		Date fechaActual = fechaFinal;
		fechaInicio = Calendar.getInstance();
		fechaInicio.setTime(fechaInicial);
		fechaInicio.add(Calendar.DAY_OF_WEEK, 1);
		fechaFin = Calendar.getInstance();
		fechaFin.setTime(fechaActual);

		long diasAtraso = 0;

		if (fechaInicio.getTimeInMillis() >= fechaFin.getTimeInMillis()) {
			return 0L;
		}
		do {
			if (fechaInicio.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
					&& fechaInicio.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
				diasAtraso++;
			}
			fechaInicio.add(Calendar.DAY_OF_WEEK, 1);
		} while (fechaInicio.getTimeInMillis() <= fechaFin.getTimeInMillis());
		return diasAtraso;
	}

	public String sqlConversionMonedasADolar(String monedaOriginal, String cantidad, String costo, String tablaMoneda, String cambioAlternativoPesos, String cambioAlternativoTodos, Boolean tipoCambioActual){
		String conversion = " ";
		String actual1 = "";
		String aEuro = "";
		String aLibra= "";
		String aDlCan= "";
		String aYenes= "";
		String aPDolar = "";
		// 		String aCHFDolar = "";

		if(tipoCambioActual){
			actual1 = 	" COALESCE(";
			aEuro = 	",@EDOLAR)";
			aLibra=	 	",@LDOLAR)";
			aDlCan= 	",@DCDOLAR)";
			aYenes= 	",@YDOLAR)";
			aPDolar = 	",@PDOLAR)";
			// 	 		aCHFDolar = ",@CHFDOLAR)";

		}
		conversion =    cantidad +" * ( CASE " +
				" WHEN "+ monedaOriginal +" = 'Pesos' OR " + monedaOriginal + " = 'MXN' OR " + monedaOriginal + " = 'M.N.' OR " + monedaOriginal + " = 'M.N'	" +
				"THEN (("+ costo +") / ";
		if((cambioAlternativoPesos.equals("")) && (cambioAlternativoTodos.equals(""))){
			conversion +=	actual1 + tablaMoneda +".PDolar " + aPDolar + ")" ;
		}else if(cambioAlternativoTodos.equals("")){
			conversion +=	" COALESCE(CASE WHEN " + cambioAlternativoPesos + " = 0 THEN "  + tablaMoneda +".PDolar ELSE " + cambioAlternativoPesos + " END , " + tablaMoneda +".PDolar))" ;
		}else{

		}
		conversion +=
				" \n WHEN "+ monedaOriginal +" = 'Euros' OR " + monedaOriginal + " = 'EUR'		" +
						" THEN "+ costo +" * (" + actual1 + tablaMoneda +".EDolar" + aEuro + ")" +

						" \n WHEN "+ monedaOriginal +" = 'Libras' " +
						"THEN "+ costo +" * (" + actual1 + tablaMoneda +".LDolar" + aLibra + ")" +

						" \n WHEN "+ monedaOriginal +" = 'DlCan'  " +
						"THEN "+ costo +" * (" + actual1 + tablaMoneda +".DDolar" + aDlCan + ") " +

						" \n WHEN "+ monedaOriginal +" = 'Yenes'  " +
						"THEN "+ costo +" * (" + actual1 + tablaMoneda +".YDolar" + aYenes + ") " +

//				" \n WHEN "+ monedaOriginal +" = 'FrancoSuizo' "+
//						"THEN "+ cantidad +" * "+ costo +" * (" + actual1 + tablaMoneda +".CHFDolar" + CHF + ") " +
						" \n ELSE "+ costo +" END )";

		// 		conversion =	" CASE"+
		// 				" WHEN "+ monedaOriginal +" = 'Pesos' OR " + monedaOriginal + " = 'MXN' OR " + monedaOriginal + " = 'M.N.' OR " + monedaOriginal + " = 'M.N'	" +
		// 				"THEN (("+ cantidad +" * "+ costo +") / ";
		// 		if((cambioAlternativoPesos.equals("")) && (cambioAlternativoTodos.equals(""))){
		// 			conversion +=	actual1 + tablaMoneda +".PDolar " + aPDolar + ")" ;
		// 		}else if(cambioAlternativoTodos.equals("")){
		// 			conversion +=	 cambioAlternativoPesos +")" ;
		// 		}else{
		// 			
		// 		}	
		// 		conversion +=	" \n WHEN "+ monedaOriginal +" = 'Euros' OR " + monedaOriginal + " = 'EUR'		" +
		// 				" THEN "+ cantidad +" * "+ costo +" * (" + actual1 + tablaMoneda +".EDolar" + aEuro + ")" +
		// 				
		//						" \n WHEN "+ monedaOriginal +" = 'Libras' " +
		//						"THEN "+ cantidad +" * "+ costo +" * (" + actual1 + tablaMoneda +".LDolar" + aLibra + ")" +
		//						
		//						" \n WHEN "+ monedaOriginal +" = 'DlCan'  " +
		//						"THEN "+ cantidad +" * "+ costo +" * (" + actual1 + tablaMoneda +".DDolar" + aDlCan + ") " +
		//						
		//						" \n WHEN "+ monedaOriginal +" = 'Yenes'  " +
		//						"THEN "+ cantidad +" * "+ costo +" * (" + actual1 + tablaMoneda +".YDolar" + aYenes + ") " +
		//						
		////						" \n WHEN "+ monedaOriginal +" = 'FrancoSuizo' "+
		////								"THEN "+ cantidad +" * "+ costo +" * (" + actual1 + tablaMoneda +".CHFDolar" + CHF + ") " +
		//" \n ELSE "+ cantidad +" * "+ costo +" END";

		return conversion;

	}

	public String sqlTipoDeCambioActual(){
		/***
		 * Obtiene el sql para obtener el tipo de cambio mas actual que hay en la BD
		 */

		String sql = "";

		sql = 	" \n DECLARE @PDOLAR   float = (SELECT TOP  1 PDolar FROM Monedas ORDER BY Fecha DESC)" +
				" \n DECLARE @EDOLAR   float = (SELECT TOP  1 EDolar FROM Monedas ORDER BY Fecha DESC)" +
				" \n DECLARE @LDOLAR   float = (SELECT TOP  1 LDolar FROM Monedas ORDER BY Fecha DESC)" +
				" \n DECLARE @YDOLAR   float = (SELECT TOP  1 YDolar FROM Monedas ORDER BY Fecha DESC)" +
				" \n DECLARE @DCDOLAR   float = (SELECT TOP  1 DDolar FROM Monedas ORDER BY Fecha DESC)" ;
		//				" \n DECLARE @CHFDOLAR float = (SELECT TOP  1 CHFDolar FROM Monedas ORDER BY Fecha DESC)" ;

		return sql;

	}

	public String sqlLimitesNivelIngresoClientesClaveVSResto(List<NivelIngreso> ni, String campo){
		/***
		 * en la variable campo, debe de traer el campo donde la consulta donde la suma de las ventas en dolares a un cliente
		 * la lista de nivel ingreso debe de traer todos los niveles en los que se puede clasificar un cliente, asi como el limite maximo y el minimo de ventas
		 * Los clientes CLAVE son: AAplus, AA, AM, AB
		 * Los clientes RESTO son: MA,MM,MB,Bajo,Distribuidor,Publico
		 * Por lo tanto se utiliza el monto minimo de AB para obtener los Clave, y el monto maximo de MA para obtener RESTO
		 */
		String claveMin = "", restoMax = "", sql="";

		for(NivelIngreso n : ni){
			if(n.getNivel().toLowerCase().equals("ab")){
				claveMin = " \n WHEN  COALESCE(" + campo + ",0) >= " + n.getMin() + " THEN 'CLIENTE CLAVE' ";
			}else if(n.getNivel().toLowerCase().equals("ma")){
				restoMax = " \n WHEN COALESCE(" + campo + ",0) <= " + n.getMax() + " THEN 'RESTO' ";

			}


		}

		sql = 	" (CASE" + claveMin + restoMax + " END )";

		return sql;
	}
	public String sqlLimitesNivelIngresoClientesCategorias(List<NivelIngreso> ni, String campo){
		/***
		 * Las categorias son: Altos, Medios y Distribuidores, bajos y nuevos 
		 * en la variable campo, debe de traer el campo donde la consulta donde la suma de las ventas en dolares a un cliente
		 * la lista de nivel ingreso debe de traer todos los niveles en los que se puede clasificar un cliente, asi como el limite maximo y el minimo de ventas
		 *
		 * Los clientes Altos son: AAplus, AA, AM, AB
		 * Los clientes Medios son: MA,MM,MB,
		 * Los clientes Bajos son: unicamente Bajos  y nos clientes nuevos 
		 * Los clientes Nuevos son: aquellos que el anio de su fechaRegistro es el mismo al anio actual. 
		 * Los clientes Distribuidores, solo se consideraran de esta categoria si la fecha de creacion es distinta del anio del actual
		 *
		 * Por lo tanto se utiliza el monto minimo de AB para obtener los Clave, y el monto maximo de MA para obtener RESTO
		 */
		String altoMin = "", medioMax = "", medioMin = "";
		String sql=	"  ";

		for(NivelIngreso n : ni){
			if(n.getNivel().toLowerCase().equals("ab")){
				altoMin = " \n WHEN  COALESCE(" + campo + ",0) >= " + n.getMin() + " THEN 'ALTOS' ";
			}else if(n.getNivel().toLowerCase().equals("ma")){
				medioMax = " \n WHEN COALESCE(" + campo + ",0) <= " + n.getMax();
			}else if(n.getNivel().toLowerCase().equals("bajo")){
				medioMin = " \n AND COALESCE(" + campo + ",0) > " + n.getMax() +" THEN 'MEDIOS'";
			}
		}
		sql = 	" CASE WHEN CLI.Rol = 'DISTRIBUIDOR' THEN 'DISTRIBUIDOR' ELSE (CASE WHEN YEAR(FechaRegistro) = YEAR(GETDATE()) THEN 'NUEVOS' " + altoMin + medioMax + medioMin + " ELSE 'BAJO' END ) END ";

		return sql;
	}

	public String sqlLimitesNivelIngreso(List<NivelIngreso> ni, String campo){
		/***
		 * en la variable campo, debe de traer el campo donde la consulta donde la suma de las ventas en dolares a un cliente
		 * la lista de nivel ingreso debe de traer todos los niveles en los que se puede clasificar un cliente, asi como el limite maximo y el minimo de ventas
		 */
		String sql = "", aaplus = "", aa = "", am = "", ab = "", ma = "", mm = "", mb = "", bajo= "";

		for(NivelIngreso n : ni){
			if(n.getNivel().toLowerCase().equals("aaplus")){
				aaplus = " \n WHEN " + campo + " >= " + n.getMin() + " THEN 'AAplus' ";
			}else if(n.getNivel().toLowerCase().equals("aa")){
				aa = " \n WHEN " + campo + " >= " + n.getMin() + " AND " + campo + " <= " + n.getMax() + " THEN 'AA' ";

			}else if(n.getNivel().toLowerCase().equals("am")){
				am = " \n WHEN " + campo + " >= " + n.getMin() + " AND " + campo + " <= " + n.getMax() + " THEN 'AM' ";

			}else if(n.getNivel().toLowerCase().equals("ab")){
				ab = " \n WHEN " + campo + " >= " + n.getMin() + " AND " + campo + " <= " + n.getMax() + " THEN 'AB' ";

			}else if(n.getNivel().toLowerCase().equals("ma")){
				ma = " \n WHEN " + campo + " >= " + n.getMin() + " AND " + campo + " <= " + n.getMax() + " THEN 'MA' ";

			}else if(n.getNivel().toLowerCase().equals("mm")){
				mm = " \n WHEN " + campo + " >= " + n.getMin() + " AND " + campo + " <= " + n.getMax() + " THEN 'MM' ";

			}else if(n.getNivel().toLowerCase().equals("mb")){
				mb = " \n WHEN " + campo + " >= " + n.getMin() + " AND " + campo + " <= " + n.getMax() + " THEN 'MB' ";

			}else if(n.getNivel().toLowerCase().equals("bajo")){
				bajo += " \n ELSE 'Bajo' ";
			}
		}

		sql = 	" (CASE" + aaplus + aa + am + ab + ma + mm + mb + bajo + " END )";

		return sql;
	}
	public String sqlLimitesNivelIngresoConNuevos(List<NivelIngreso> ni, String campo, String fregistrioCliente){
		/***
		 * en la variable campo, debe de traer el campo donde la consulta donde la suma de las ventas en dolares a un cliente
		 * la lista de nivel ingreso debe de traer todos los niveles en los que se puede clasificar un cliente, asi como el limite maximo y el minimo de ventas
		 */
		String sql = "", aaplus = "", aa = "", am = "", ab = "", ma = "", mm = "", mb = "", bajo= "";

		for(NivelIngreso n : ni){
			if(n.getNivel().toLowerCase().equals("aaplus")){
				aaplus = " \n WHEN " + campo + " >= " + n.getMin() + " AND YEAR(" + fregistrioCliente + ") <> YEAR(GETDATE()) THEN 'AAplus' ";
			}else if(n.getNivel().toLowerCase().equals("aa")){
				aa = " \n WHEN " + campo + " >= " + n.getMin() + " AND " + campo + " <= " + n.getMax() + " AND YEAR(" + fregistrioCliente + ") <> YEAR(GETDATE())  THEN 'AA' ";

			}else if(n.getNivel().toLowerCase().equals("am")){
				am = " \n WHEN " + campo + " >= " + n.getMin() + " AND " + campo + " <= " + n.getMax() + " AND YEAR(" + fregistrioCliente + ") <> YEAR(GETDATE())  THEN 'AM' ";

			}else if(n.getNivel().toLowerCase().equals("ab")){
				ab = " \n WHEN " + campo + " >= " + n.getMin() + " AND " + campo + " <= " + n.getMax() + " AND YEAR(" + fregistrioCliente + ") <> YEAR(GETDATE())  THEN 'AB' ";

			}else if(n.getNivel().toLowerCase().equals("ma")){
				ma = " \n WHEN " + campo + " >= " + n.getMin() + " AND " + campo + " <= " + n.getMax() + " AND YEAR(" + fregistrioCliente + ") <> YEAR(GETDATE())  THEN 'MA' ";

			}else if(n.getNivel().toLowerCase().equals("mm")){
				mm = " \n WHEN " + campo + " >= " + n.getMin() + " AND " + campo + " <= " + n.getMax() + " AND YEAR(" + fregistrioCliente + ") <> YEAR(GETDATE())  THEN 'MM' ";

			}else if(n.getNivel().toLowerCase().equals("mb")){
				mb = " \n WHEN " + campo + " >= " + n.getMin() + " AND " + campo + " <= " + n.getMax() + " AND YEAR(" + fregistrioCliente + ") <> YEAR(GETDATE())  THEN 'MB' ";

			}else if(n.getNivel().toLowerCase().equals("bajo")){
				bajo = " \n WHEN YEAR(" + fregistrioCliente + ") = YEAR(GETDATE()) THEN 'Nuevo'   ";
				bajo += " \n ELSE 'Bajo' ";
			}
		}

		sql = 	" (CASE" + aaplus + aa + am + ab + ma + mm + mb + bajo + " END )";

		return sql;
	}
	public String sqlLimitesNivelIngresoCClientesNuevos(List<NivelIngreso> ni, String campo, String fecha , boolean monto , boolean insNuevo ){
		/***
		 * se agrega el nivel de ingreso "clientesNuevos", siendo un subgrupo de clientes bajos con la caracteristica de no tener registro en historialventa y la fecha de 
		 * registro de la tabla cliente debe ser igual al ano de facturacion 
		 */
		String sql = "",distribuidor = "" , nuevo = "" ,  aaplus = "", aa = "", am = "", ab = "", ma = "", mm = "", mb = "", bajo= "";
		if (monto) {


			if (fecha.equals("") ){
				distribuidor = " WHEN CLI.Rol = 'DISTRIBUIDOR' AND (" + campo + "  is null or " + campo + "  = 0) THEN 0";
				distribuidor += " WHEN CLI.Rol = 'DISTRIBUIDOR' AND (" + campo + "  is not null or " + campo + "  > 0) THEN " + campo + " ELSE ((";
				nuevo = "  WHEN  " + campo + "  IS NULL AND YEAR (CLI.fecharegistro) = YEAR (GETDATE()) THEN 0 " ;
			}else{
				distribuidor = " WHEN CLI.Rol = 'DISTRIBUIDOR' AND (" + campo + "  is null or " + campo + "  = 0) THEN 0";
				distribuidor += " WHEN CLI.Rol = 'DISTRIBUIDOR' AND (" + campo + "  is not null or " + campo + "  > 0) THEN " + campo + " ELSE ((";
				nuevo = "  WHEN  " + campo + "  IS NULL AND YEAR (CLI.fecharegistro) = " + fecha  + " THEN 0 " ;
			}
			for(NivelIngreso n : ni){
				if(n.getNivel().toLowerCase().equals("aaplus")){
					aaplus = " \n WHEN " + campo + " >= " + n.getMin() + " THEN " + campo + " ";
				}else if(n.getNivel().toLowerCase().equals("aa")){
					aa = " \n WHEN " + campo + " >= " + n.getMin() + " AND " + campo + " <= " + n.getMax() + " THEN " + campo + " ";

				}else if(n.getNivel().toLowerCase().equals("am")){
					am = " \n WHEN " + campo + " >= " + n.getMin() + " AND " + campo + " <= " + n.getMax() + " THEN " + campo + " ";

				}else if(n.getNivel().toLowerCase().equals("ab")){
					ab = " \n WHEN " + campo + " >= " + n.getMin() + " AND " + campo + " <= " + n.getMax() + " THEN " + campo + " ";

				}else if(n.getNivel().toLowerCase().equals("ma")){
					ma = " \n WHEN " + campo + " >= " + n.getMin() + " AND " + campo + " <= " + n.getMax() + " THEN " + campo + " ";

				}else if(n.getNivel().toLowerCase().equals("mm")){
					mm = " \n WHEN " + campo + " >= " + n.getMin() + " AND " + campo + " <= " + n.getMax() + " THEN " + campo + " ";

				}else if(n.getNivel().toLowerCase().equals("mb")){
					mb = " \n WHEN " + campo + " >= " + n.getMin() + " AND " + campo + " <= " + n.getMax() + " THEN " + campo + " ";

				}else if(n.getNivel().toLowerCase().equals("bajo")){
					bajo = " \n ELSE 600 ";
				}
			}
		}else{


			if (fecha.equals("") ){
				distribuidor = " WHEN CLI.Rol = 'DISTRIBUIDOR'   THEN 'Distribuidor' ELSE ((";
				nuevo = "  WHEN  YEAR(CLI.fecharegistro) = YEAR (GETDATE()) THEN 'ClientesNuevos' " ;
			}else{
				distribuidor = " WHEN CLI.Rol = 'DISTRIBUIDOR'  THEN 'Distribuidor' ELSE ((";
				nuevo = "  WHEN  YEAR(CLI.fecharegistro) = " + fecha  + " THEN 'ClientesNuevos' " ;
			}
			for(NivelIngreso n : ni){
				if(n.getNivel().toLowerCase().equals("aaplus")){
					aaplus = " \n WHEN " + campo + " >= " + n.getMin() + " THEN 'AAplus' ";
				}else if(n.getNivel().toLowerCase().equals("aa")){
					aa = " \n WHEN " + campo + " >= " + n.getMin() + " AND " + campo + " <= " + n.getMax() + " THEN 'AA' ";

				}else if(n.getNivel().toLowerCase().equals("am")){
					am = " \n WHEN " + campo + " >= " + n.getMin() + " AND " + campo + " <= " + n.getMax() + " THEN 'AM' ";

				}else if(n.getNivel().toLowerCase().equals("ab")){
					ab = " \n WHEN " + campo + " >= " + n.getMin() + " AND " + campo + " <= " + n.getMax() + " THEN 'AB' ";

				}else if(n.getNivel().toLowerCase().equals("ma")){
					ma = " \n WHEN " + campo + " >= " + n.getMin() + " AND " + campo + " <= " + n.getMax() + " THEN 'MA' ";

				}else if(n.getNivel().toLowerCase().equals("mm")){
					mm = " \n WHEN " + campo + " >= " + n.getMin() + " AND " + campo + " <= " + n.getMax() + " THEN 'MM' ";

				}else if(n.getNivel().toLowerCase().equals("mb")){
					mb = " \n WHEN " + campo + " >= " + n.getMin() + " AND " + campo + " <= " + n.getMax() + " THEN 'MB' ";

				}else if(n.getNivel().toLowerCase().equals("bajo")){
					bajo = " \n ELSE 'Bajo' ";
				}
			}
		}
		if (insNuevo) {
			sql = 	" CASE" + distribuidor + " CASE " + nuevo + aaplus + aa + am + ab + ma + mm + mb + bajo + " END )) END ";
		}else{
			sql = 	" CASE" + distribuidor + " CASE " + aaplus + aa + am + ab + ma + mm + mb + bajo + " END )) END ";
		}
		return sql;
	}
	public String convertArrayToStringComas(ArrayList<String> num) {
		StringBuffer ret = new StringBuffer("");
		for (int i = 0; num != null && i < num.size(); i++) {
			ret.append(num.get(i));
			if (i < num.size() - 1) {
				ret.append(',');
			}
		}
		return ret.toString();
	}

	public String convertirPalabrasClaves(String palabras){
		String cambia = palabras;


		if(palabras.equals("AAplus")){
			cambia = "AA+";
		}
		if(palabras.equals("AA+")){
			cambia = "AAplus";
		}
		if(palabras.equals("A destrucci������������������n")){
			cambia = "A destruccion";
		}
		if(palabras.equals("A destruccion")){
			cambia = "A destrucci������������������n";
		}
		if(palabras.equals("En inspecci������������������n")){
			cambia = "En inspeccion";
		}
		if(palabras.equals("En inspeccion")){
			cambia = "En inspecci������������������n";
		}
		if(palabras.equals("Cl������������������nico")){
			cambia = "Clinico";
		}
		if(palabras.equals("Centroam������������������rica")){
			cambia = "Centroamerica";
		}
		if(palabras.equals("Centroamerica")){
			cambia = "Centroam������������������rica";
		}
		if(palabras.equals("Sudam������������������rica")){
			cambia = "Sudamerica";
		}
		if(palabras.equals("Sudamerica")){
			cambia = "Sudam������������������rica";
		}
		if(palabras.equals("")){
			cambia = "";
		}
		if(palabras.equals("")){
			cambia = "";
		}

		return cambia;
	}

	public String quitarAcentos(String palabras){
		String cambia = palabras;


		if(palabras.equals("A destrucci������������������n")){
			cambia = "A destruccion";
		}

		if(palabras.equals("En inspecci������������������n")){
			cambia = "En inspeccion";
		}

		if(palabras.equals("Cl������������������nico")){
			cambia = "Clinico";
		}
		if(palabras.equals("Centroam������������������rica")){
			cambia = "Centroamerica";
		}

		if(palabras.equals("Sudam������������������rica")){
			cambia = "Sudamerica";
		}

		if(palabras.equals("")){
			cambia = "";
		}
		if(palabras.equals("")){
			cambia = "";
		}

		return cambia;
	}


	public String obtenerEmpresasProquifa(String tablaCliente){

		// 		201		PHARMA SCIENTIFIC
		// 		347		PRO
		// 		348		PQF
		// 		987		PROVEEDORA
		// 		1595	PROQUIFA SERVICIOS
		// 		2281	GOLOCAER
		// 		2321	RYNDEM
		// 		2322	MUNGEN
		// 		2523	GAMALIEL GONZALEZ LEON
		// 		2675	RM TRADING

		String empresas = tablaCliente + " NOT IN (201,347,348,987,1595,2281,2322,2321,2523,2675) ";
		return empresas;
	}

	public String formatoMoneda(Double valor){
		DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
		decimalFormatSymbols.setDecimalSeparator('.');
		decimalFormatSymbols.setGroupingSeparator(',');
		DecimalFormat decimalFormat = new DecimalFormat("#,###,##0.00",decimalFormatSymbols);
		return decimalFormat.format(valor);
	}


	public Horario obtenerHorarioPorPuntos(Horario horario){
		//Obtener las fechas de inicio y fin de los dias o de todos los dias de los dos rangos
		try{
			if(!horario.getPlunes().isEmpty()){

				horario.setLunes(true);
				Integer inicio = horario.getPlunes().get(0);
				Integer fin = inicio, inicio2 = -30, fin2=0;
				int tmp = inicio-1;
				for(int i : horario.getPlunes()){
					tmp++;
					if(tmp  == i){
						fin = i;

					}else{
						if(inicio2 == -30){
							inicio2 = i;
						}
						fin2 =i;
					}

				}
				horario.setLuDe1(obtenerInicioTiempo(inicio));
				horario.setLuA1(obtenerInicioTiempo(fin+1));
				horario.setLuDe2(obtenerInicioTiempo(inicio2));
				horario.setLuA2(obtenerInicioTiempo(fin2+1));
				//	 			logger.info("L1D" + horario.getLuDe1());
				//	 			logger.info("L1A" + horario.getLuA1());
				//	 			logger.info("l2D" + horario.getLuDe2());
				//	 			logger.info("L2A" + horario.getLuA2());
			}
			if(!horario.getPmartes().isEmpty()){
				horario.setMartes(true);
				Integer inicio = horario.getPmartes().get(0);
				Integer fin = inicio, inicio2 = -30, fin2=0;
				int tmp = inicio-1;
				for(int i : horario.getPmartes()){
					tmp++;
					if(tmp  == i){
						fin = i;

					}else{
						if(inicio2 == -30){
							inicio2 = i;
						}
						fin2 =i;
					}

				}
				horario.setMaDe1(obtenerInicioTiempo(inicio));
				horario.setMaA1(obtenerInicioTiempo(fin+1));
				horario.setMaDe2(obtenerInicioTiempo(inicio2));
				horario.setMaA2(obtenerInicioTiempo(fin2+1));
			}
			if(!horario.getPmiercoles().isEmpty()){
				horario.setMiercoles(true);
				Integer inicio = horario.getPmiercoles().get(0);
				Integer fin = inicio, inicio2 = -30, fin2=0;
				int tmp = inicio-1;
				for(int i : horario.getPmiercoles()){
					tmp++;
					if(tmp  == i){
						fin = i;

					}else{
						if(inicio2 == -30){
							inicio2 = i;
						}
						fin2 =i;
					}

				}
				horario.setMiDe1(obtenerInicioTiempo(inicio));
				horario.setMiA1(obtenerInicioTiempo(fin+1));
				horario.setMiDe2(obtenerInicioTiempo(inicio2));
				horario.setMiA2(obtenerInicioTiempo(fin2+1));
			}
			if(!horario.getPjueves().isEmpty()){
				horario.setJueves(true);
				Integer inicio = horario.getPjueves().get(0);
				Integer fin = inicio, inicio2 = -30, fin2=0;
				int tmp = inicio-1;
				for(int i : horario.getPjueves()){
					tmp++;
					if(tmp  == i){
						fin = i;

					}else{
						if(inicio2 == -30){
							inicio2 = i;
						}
						fin2 =i;
					}

				}
				horario.setJuDe1(obtenerInicioTiempo(inicio));
				horario.setJuA1(obtenerInicioTiempo(fin+1));
				horario.setJuDe2(obtenerInicioTiempo(inicio2));
				horario.setJuA2(obtenerInicioTiempo(fin2+1));
			}
			if(!horario.getPviernes().isEmpty()){
				horario.setViernes(true);
				Integer inicio = horario.getPviernes().get(0);
				Integer fin = inicio, inicio2 = -30, fin2=0;
				int tmp = inicio-1;
				for(int i : horario.getPviernes()){
					tmp++;
					if(tmp  == i){
						fin = i;

					}else{
						if(inicio2 == -30){
							inicio2 = i;
						}
						fin2 =i;
					}

				}
				horario.setViDe1(obtenerInicioTiempo(inicio));
				horario.setViA1(obtenerInicioTiempo(fin+1));
				horario.setViDe2(obtenerInicioTiempo(inicio2));
				horario.setViA2(obtenerInicioTiempo(fin2+1));
			}

			return horario;
		}catch (Exception e) {
			//	 		logger.info(e.getMessage());
			return horario;
		}
	}

	public String obtenerInicioTiempo(Integer i){
		String hora = "07:00";

		switch (i){
			case -2:
				hora = "07:00";
				break;
			case -1:
				hora = "07:30";
				break;
			case 0:
				hora = "08:00";
				break;
			case 1:
				hora = "08:30";
				break;
			case 2:
				hora = "09:00";
				break;
			case 3:
				hora = "09:30";
				break;
			case 4:
				hora = "10:00";
				break;
			case 5:
				hora = "10:30";
				break;
			case 6:
				hora = "11:00";
				break;
			case 7:
				hora = "11:30";
				break;
			case 8:
				hora = "12:00";
				break;
			case 9:
				hora = "12:30";
				break;
			case 10:
				hora = "13:00";
				break;
			case 11:
				hora = "13:30";
				break;
			case 12:
				hora = "14:00";
				break;
			case 13:
				hora = "14:30";
				break;
			case 14:
				hora = "15:00";
				break;
			case 15:
				hora = "15:30";
				break;
			case 16:
				hora = "16:00";
				break;
			case 17:
				hora = "16:30";
				break;
			case 18:
				hora = "17:00";
				break;
			case 19:
				hora = "17:30";
				break;
			case 20:
				hora = "18:00";
				break;
			case 21:
				hora = "18:30";
				break;
			case 22:
				hora = "19:00";
				break;
			case 23:
				hora = "19:30";
				break;
			case 24:
				hora = "20:00";
				break;
			case 25:
				hora = "20:30";
				break;
			case 26:
				hora = "21:00";
				break;
			case 27:
				hora = "21:30";
				break;
			case 28:
				hora = "22:00";
				break;
			case 29:
				hora = "22:30";
				break;
		}
		return hora;
	}

	public Point obtenerCoordenadaHorario(String inicio, String fin)
			throws ProquifaNetException {
		String strHrI = "", strHrF = "";
		Point p = new Point();
		strHrI =  inicio.substring(0, 2);
		strHrF =  fin.substring(0, 2);
		Double x = 0.0, y = 0.0;//, x1 = 0.0, y1 = 0.0;

		int hr = Integer.parseInt(strHrI);
		int hr2 = Integer.parseInt(strHrF);
		x = obtenerPunto(hr);
		y = obtenerPunto(hr2);
		if(x != -30.0 || y != -30.0){

			strHrI =  inicio.substring(3, 5);
			strHrF =  fin.substring(3, 5);
			hr = Integer.parseInt(strHrI);
			hr2 = Integer.parseInt(strHrF);
			//			x1 = obtenerPunto(hr);
			//			y1 = obtenerPunto(hr2);
			if(hr2 == 30)
			{
				hr2 = 0;
			}
			else if(hr2 == 0)
			{
				strHrF = fin.substring(0, 2);
				hr2 = Integer.parseInt(strHrF);
				hr2--;
				y = obtenerPunto(hr2);
				hr2 = 30;
			}


			if(hr > 0){
				x++;
			}

			if(hr2 > 0){
				y++;
			}

			p.setLocation(x,y);



			return p;
		}else{
			return null;
		}
	}

	public Double obtenerPunto ( Integer i){
		Double x = -30.0;

		switch (i){
			case 7:
				x = -2.0;
				break;
			case 8:
				x = 0.0;
				break;
			case 9:
				x = 2.0;
				break;
			case 10:
				x = 4.0;
				break;
			case 11:
				x = 6.0;
				break;
			case 12:
				x = 8.0;
				break;
			case 13:
				x = 10.0;
				break;
			case 14:
				x = 12.0;
				break;
			case 15:
				x = 14.0;
				break;
			case 16:
				x = 16.0;
				break;
			case 17:
				x = 18.0;
				break;
			case 18:
				x = 20.0;
				break;
			case 19:
				x = 22.0;
				break;
			case 20:
				x = 24.0;
				break;
			case 21:
				x = 26.0;
				break;
		}


		return x;
	}

	public String sqlAgenteAduanalxProveedor (String nombreColumna){
		if (nombreColumna.equals("")) {
			nombreColumna = "prov";
		}
		String query = "\n	LEFT JOIN (SELECT  FK01_Proveedor,  agente.* from Configuracion_Precio AS CP " +
				"\n		LEFT JOIN (SELECT FK01_AgenteAduanal, FK02_ConfiguracionPrecio FROM AA_ConfiguracionPrecio ) AS AA_CP ON AA_CP.FK02_ConfiguracionPrecio = CP.PK_Configuracion_Precio " +
				"\n		LEFT JOIN (SELECT PK_AgenteAduanal,NombreComercial , NombreLegal  FROM AgenteAduanal where Habilitado =  1 ) AS Agente ON Agente.PK_AgenteAduanal = AA_CP.FK01_AgenteAduanal " +
				"\n		WHERE cp.nivel = 'Familia' and aa_cp.FK01_AgenteAduanal is not null GROUP BY FK01_Proveedor , PK_AgenteAduanal , NombreComercial , NombreLegal )AS Agente on Agente.FK01_Proveedor = " + nombreColumna + ".clave ";

		return query;


	}
	public String obtenerFechasFacturacion(Long anio, String periodo, Integer tipoPeriodo,
										   Long mes, String fechaTabla) {
		//FUNCION MODIFICADA PARA MANDAR UN INTERVALO EN FECHA, CON RESPECTO AL ULTIMO DIA DEL MES ANTERIOR PARA EL AniO EN CURSO. (En desuso.Al dia 27 de octubre del 2014)
		String fechas = "'";
		Integer diaFinMes = 0;
		NumberFormat nf = NumberFormat.getInstance();

		if (periodo.toLowerCase().equals("anual")) {
			fechas +=  anio + "0101 00:00' and " + fechaTabla + " <= @ANIO "  ;
		} else if (periodo.toLowerCase().equals("semestral")) {
			if (tipoPeriodo == 1) {
				fechas += anio + "0101 00:00' and " +  fechaTabla+ " <=@ANIO "  ;
			} else {
				fechas += anio + "0701 00:00' and " +  fechaTabla + " <= @ANIO "  ;
			}
		} else if (periodo.toLowerCase().equals("trimestral")) {
			switch (tipoPeriodo) {
				case 1:
					fechas += anio + "0101 00:00' and " +  fechaTabla +" <= @ANIO "  ;
					break;
				case 2:
					fechas += anio + "0401 00:00' and " +  fechaTabla +" <= @ANIO "  ;
					break;
				case 3:
					fechas += anio + "0701 00:00' and " +  fechaTabla + " <= @ANIO "  ;
					break;
				case 4:
					fechas += anio + "1001 00:00' and " +  fechaTabla + " <= @ANIO "  ;
					break;
			}
		}else if(periodo.toLowerCase().equals("bimestral")){
			switch(tipoPeriodo){
				case 1:
					fechas += anio + "0101 00:00' and " +  fechaTabla + " <= @ANIO "  ;
					break;
				case 2:
					fechas += anio + "0301 00:00' and " +  fechaTabla + " <=@ANIO "  ;
					break;
				case 3:
					fechas += anio + "0501 00:00' and " +  fechaTabla + " <= @ANIO "  ;
					break;
				case 4:
					fechas += anio + "0701 00:00' and " +  fechaTabla + " <= @ANIO "  ;
					break;
				case 5:
					fechas += anio + "0901 00:00' and " +  fechaTabla + " <= @ANIO "  ;
					break;
				case 6:
					fechas += anio + "1101 00:00' and " +  fechaTabla + " <= @ANIO "  ;
					break;
			}
		}else if(periodo.toLowerCase().equals("quincenal")){

			if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8
					|| mes == 10 || mes == 12) {
				diaFinMes = 31;
			} else if (mes == 4 || mes == 6 || mes == 9 || mes == 11) {
				diaFinMes = 30;
			} else {
				diaFinMes = 28;
			}

			nf.setMinimumIntegerDigits(2);
			nf.setGroupingUsed(false);
			String mesT = nf.format(mes);

			switch(tipoPeriodo){
				case 1:
					fechas += anio + mesT + "01 00:00' and " + fechaTabla + " <= '" + anio + mesT + "'"
							+ "15 23:59";
					break;
				case 2:
					fechas += anio + mesT + "16 00:00' and " + fechaTabla + " <= '" + anio + mesT + "'"
							+ diaFinMes + " 23:59";
					break;
			}



		}else if (periodo.toLowerCase().equals("mensual")) {
			if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8
					|| mes == 10 || mes == 12) {
				diaFinMes = 31;
			} else if (mes == 4 || mes == 6 || mes == 9 || mes == 11) {
				diaFinMes = 30;
			} else {
				diaFinMes = 28;
			}
			nf.setMinimumIntegerDigits(2);
			nf.setGroupingUsed(false);
			String mesT = nf.format(mes);
			fechas += anio + mesT + "01 00:00' and " + fechaTabla + " <= '" + anio + mesT
					+ diaFinMes + " 23:59'";
		}
		return fechas;
	}

	public int convertirValorTiempoAsegundos(String sValorTiempo){
		final int HORAS_SEMANA = 168; // 7 dias tiene 168 horas
		int iSegundos = 0, iValor;
		String sCadenaAux = "";

		if(sValorTiempo.indexOf("horas") > -1){ // Convertir Horas a Segundos

			//Se le quita la palabra ' horas' u 'hora' para dejar solo el numero
			sCadenaAux = sValorTiempo.replaceAll(" hora","");
			sCadenaAux = sCadenaAux.replaceAll("s",""); // Se le quita la 's' si es que tenia 'horas'

			if(isNumeric(sCadenaAux)){
				iValor = Integer.parseInt(sCadenaAux);
				iSegundos = (iValor * 60) * 60;
			}

		}else if(sValorTiempo.indexOf("semana") > -1){ // Convertir Semanas en Segundos

			//Se le quita la palabra ' horas' para dejar solo el numero
			sCadenaAux = sValorTiempo.replaceAll(" semana","");
			sCadenaAux = sCadenaAux.replaceAll("s",""); // Se le quita la 's' si es que tenia 'semanas'

			if(isNumeric(sCadenaAux)){
				iValor = Integer.parseInt(sCadenaAux);
				iSegundos = ((iValor * HORAS_SEMANA) * 60) * 60;
			}

		}else{
			iSegundos = -1;
		}

		return iSegundos;
	}

	public String convertirSegundosAvalorTiempo(double  iSegundos){
		final int SEGUNDOS_HORA = 3600;
		final int SEGUNDOS_SEMANA = 604800;
		String sValorTiempo = "";
		double iConversion;

		if(iSegundos >= SEGUNDOS_SEMANA){ // La conversion es en Semana(s)
			iConversion = iSegundos / SEGUNDOS_SEMANA;
			if(iConversion > 1){
				sValorTiempo = iConversion + " semanas";
			}else{
				sValorTiempo = iConversion + " semana";
			}
		}else{ //La conversion es en Hora(s)
			iConversion = iSegundos / SEGUNDOS_HORA;
			if(iConversion > 1){
				sValorTiempo = iConversion + " horas";
			}else{
				sValorTiempo = iConversion + " hora";
			}
		}

		return sValorTiempo;
	}

	public String sqlDescripcionProductos(String campo, String campo2){
		/***
		 * en la variable campo, debe de traer el campo donde la consulta donde la suma de las ventas en dolares a un cliente
		 * la lista de nivel ingreso debe de traer todos los niveles en los que se puede clasificar un cliente, asi como el limite maximo y el minimo de ventas
		 */
		String sql = "";
		sql = " \n COALESCE((case when " + campo + ".Cantidad <> '' AND " + campo + ".Unidad <> '' AND (" + campo + ".Proveedor <> 44 OR (" + campo + ".Proveedor = 44 AND  ISNUMERIC(" + campo + ".Codigo) = 0)) ";
		sql += " \n		THEN (CAST(" + campo + ".Codigo AS VARCHAR(250)) + ' ' + CAST(" + campo + ".Concepto AS VARCHAR(250)) + ' [' +  CAST(" + campo + ".Cantidad AS VARCHAR(5)) + ' ' + CAST(" + campo + ".Unidad AS VARCHAR(5)) + '] ' + CAST(" + campo + ".Fabrica AS VARCHAR(250))) ";
		sql += " \n		when (" + campo + ".Cantidad <> '' AND " + campo + ".Cantidad <> '0') AND " + campo + ".Unidad = '' AND (" + campo + ".Proveedor <> 44 OR (" + campo + ".Proveedor = 44 AND  ISNUMERIC(" + campo + ".Codigo) = 0)) ";
		sql += " \n		THEN (CAST(" + campo + ".Codigo AS VARCHAR(250)) + ' ' + CAST(" + campo + ".Concepto AS VARCHAR(250)) + ' [' +  CAST(" + campo + ".Cantidad AS VARCHAR(5)) + '] ' + CAST(" + campo + ".Fabrica AS VARCHAR(250)) ) ";
		sql += " \n		ELSE  (CAST(" + campo + ".Codigo AS VARCHAR(250)) + ' ' + CAST(" + campo + ".Concepto AS VARCHAR(250)) + ' ' + CAST(" + campo + ".Fabrica AS VARCHAR(250))) END), (select 'C-'+ case when idComplemento < 999 then '0' end  + CAST(idComplemento as VARCHAR(250))  +' '+ Descripcion as Descripcion from Complemento where idComplemento = Right( " + campo2 + ".Codigo, 4))) ";

		return sql;
	}

	public String sqlDescripcionProductosCotiza(String campo, String campo2){
		/***
		 * en la variable campo, debe de traer el campo donde la consulta donde la suma de las ventas en dolares a un cliente
		 * la lista de nivel ingreso debe de traer todos los niveles en los que se puede clasificar un cliente, asi como el limite maximo y el minimo de ventas
		 */
		String sql = "";
		sql = " \n COALESCE((case when " + campo + ".Cantidad <> '' AND " + campo + ".Unidad <> '' AND (" + campo + ".Proveedor <> 44 OR (" + campo + ".Proveedor = 44 AND  ISNUMERIC(" + campo + ".Codigo) = 0)) ";
		sql += " \n		THEN (CAST(" + campo + ".Codigo AS VARCHAR(250)) + ' ' + CAST(" + campo + ".Concepto AS VARCHAR(250)) + ' [' +  CAST(" + campo + ".Cantidad AS VARCHAR(5)) + ' ' + CAST(" + campo + ".Unidad AS VARCHAR(5)) + '] ' + CAST(" + campo + ".Fabrica AS VARCHAR(250))) ";
		sql += " \n		when (" + campo + ".Cantidad <> '' AND " + campo + ".Cantidad <> '0') AND " + campo + ".Unidad = '' AND (" + campo + ".Proveedor <> 44 OR (" + campo + ".Proveedor = 44 AND  ISNUMERIC(" + campo + ".Codigo) = 0)) ";
		sql += " \n		THEN (CAST(" + campo + ".Codigo AS VARCHAR(250)) + ' ' + CAST(" + campo + ".Concepto AS VARCHAR(250)) + ' [' +  CAST(" + campo + ".Cantidad AS VARCHAR(5)) + '] ' + CAST(" + campo + ".Fabrica AS VARCHAR(250)) ) ";
		sql += " \n		ELSE  (CAST(" + campo + ".Codigo AS VARCHAR(250)) + ' ' + CAST(" + campo + ".Concepto AS VARCHAR(250)) + ' ' + CAST(" + campo + ".Fabrica AS VARCHAR(250))) END), 'Pendiente') ";

		return sql;
	}

	@SuppressWarnings("rawtypes")
	public boolean enviarCorreoAvisoExepcion(Exception e, Object... objetos) {

		try {
			List<Object> listaObjetos = new ArrayList<Object>();
			List<String> listaStrings = new ArrayList<String>();

			for (Object objeto : objetos) {
				if (objeto != null) {
					if (objeto instanceof String) {
						listaStrings.add((String) objeto);
					} else if (!(objeto instanceof ArrayList) && !(objeto instanceof Integer)
							&& !(objeto instanceof Param) && !(objeto instanceof Long)
							&& !(objeto instanceof Collection && !(objeto instanceof Float))
							&& !(objeto instanceof List) && !(objeto instanceof Double)
							&& !(objeto instanceof Boolean) && !(objeto instanceof Character)
							&& !(objeto instanceof Byte)) {
						listaObjetos.add(objeto);
					}

				}
			}

			Writer result = new StringWriter();
			PrintWriter printWriter = new PrintWriter(result);
			e.printStackTrace(printWriter);
			String detallesS = result.toString();

			StackTraceElement[] stackTraceElements = Thread.currentThread()
					.getStackTrace();

			String cuerpoCorreo = "<b>Ocurrio una Exepción: Servicio</b> \n\n"
					+ "<b>Error:</b> "
					+ ((e.getMessage() == null || e.getMessage().equals("")) ? "Error Desconocido \n"
					: e.getMessage())
					+ " \n"
					+ "<b>Servicio:</b> "
					+ ((stackTraceElements[3].getClassName().contains("Test") || stackTraceElements[3].getClassName().contains("sun.reflect"))
					? stackTraceElements[2].getClassName()
					: stackTraceElements[3].getClassName())
					+ " \n"
					+ "<b>Archivo:</b> "
					+ stackTraceElements[2].getClassName()
					+ ".java \n"
					+ "<b>Método:</b> "
					+ stackTraceElements[2].getMethodName()
					+ " \n\n"
					+ "<b>Detalles:</b> "
					+ ((detallesS == null || detallesS.equals("")) ? "No hay detalles"
					: "\n" + detallesS + " \n\n");

			if (!listaObjetos.isEmpty()) {
				cuerpoCorreo += "<b>---------------------------------Objetos de Metodo---------------------------------------------</b>\n\n";
			}

			for (int i = 0; i < listaObjetos.size(); i++) {
				Object objeto = listaObjetos.get(i);
				Method[] metodos = objeto.getClass().getDeclaredMethods();
				List<Method> metodosGet = new ArrayList<Method>();

				for (Method metodo : metodos) {
					if (metodo.getName().contains("get")) {
						metodosGet.add(metodo);
					}
				}

				cuerpoCorreo += "Objeto Tipo: " + objeto.getClass()
						+ "--------\n\n";

				for (Method metodo : metodosGet) {
					Class[] argumentos = metodo.getParameterTypes();
					cuerpoCorreo += "Metodo: " + metodo.getName() + " ------------------ Valor: "
							+ metodo.invoke(objeto, argumentos) + "\n";
				}
				cuerpoCorreo += "\n\n";
			}
			cuerpoCorreo += "\n\n";

			if (!listaStrings.isEmpty()) {
				cuerpoCorreo += "<b>---------------------------------Variables de Metodo---------------------------------------------</b>\n\n";
			}

			for (int i = 0; i < listaStrings.size(); i++) {
				cuerpoCorreo += listaStrings.get(i) + "\n";
			}

			Correo correo = new Correo();
			correo.setOrigen("serviciosti");
			correo.setCuerpoCorreo(cuerpoCorreo);
			correo.setAsunto("Error pqNetAngular: "
					+ stackTraceElements[2].getMethodName());
			correo.setCorreo("oscar.cardona@ryndem.mx");
			correo.setMedio("Correo electrónico");
			correo.setTipo("");
			correo.setArchivoAdjunto("");
			return enviarCorreo(correo);
			//return true;
		} catch (Exception x) {
			log.info("error enviarCorreoAvisoExcepcion: " + x);
			return false;
		}

	}

	public boolean enviarCorreoAvisoExepcionVista(ExepcionEnvio exepcionEnvio) {
		try {
			String cuerpoCorreo = "<b>Ocurrio una Excepción: Vista</b> \n\n"
					+ "<b>Mensaje: </b> "
					+ exepcionEnvio.getMensaje() + "\n"
					+ "<b>Servicio: </b> " +  exepcionEnvio.getServicio()+ "\n"
					+ "<b>Método: </b> " +exepcionEnvio.getMetodo()+ "\n\n";

			if (!exepcionEnvio.getParametros().isEmpty()) {
				cuerpoCorreo += "<b>---------------------------------Parámetros---------------------------------------------</b>\n\n";
			}

			for (Object parametro: exepcionEnvio.getParametros()) {
				cuerpoCorreo += "Parámetro: " + parametro;
			}

			Correo correo = new Correo();
			correo.setOrigen("serviciosti");
			correo.setCuerpoCorreo(cuerpoCorreo);
			correo.setAsunto("Error: " + exepcionEnvio.getServicio());
			correo.setCorreo("nelida.baron@ryndem.mx");
			correo.setMedio("Correo electronico");
			correo.setTipo("");
			correo.setArchivoAdjunto("");
			return enviarCorreo(correo);
			//return true;
		} catch (Exception e) {
			return false;
		}
	}



	public String condicionesPeriodoHistorialVentas(int anio,String periodo,int tipoPeriodo,int mes){
		String condiciones = "Anio = " + anio;
		if(periodo.toLowerCase().equals("anual")){
			condiciones += " AND periodo = 'Anual'";
		}else if(periodo.toLowerCase().equals("semestral")){
			condiciones += " AND periodo = 'Mensual' AND MES IN";
			if(tipoPeriodo == 1){
				condiciones += " ('Enero','Febrero','Marzo','Abril','Mayo','Junio')";
			}else if(tipoPeriodo == 2){
				condiciones += " ('Julio','Agosto','Septiembre','Octubre','Noviembre','Diciembre')";
			}
		}else if(periodo.toLowerCase().equals("trimestral")){
			condiciones += " AND periodo = 'Mensual' AND MES IN";
			if(tipoPeriodo == 1){
				condiciones += " ('Enero','Febrero','Marzo')";
			}else if(tipoPeriodo == 2){

				condiciones += " ('Abril','Mayo','Junio')";
			}else if(tipoPeriodo == 3){
				condiciones += " ('Julio','Agosto','Septiembre')";
			}else if(tipoPeriodo == 4){
				condiciones += " ('Octubre','Noviembre','Diciembre')";
			}
		}else if(periodo.toLowerCase().equals("mensual") || periodo.toLowerCase().equals("quincenal")){
			Map<Integer,String> meses = new HashMap<Integer, String>();
			meses.put(1, "Enero");meses.put(2, "Febrero");meses.put(3, "Marzo");
			meses.put(4, "Abril");meses.put(5, "Mayo");meses.put(6, "Junio");
			meses.put(7, "Julio");meses.put(8, "Agosto");meses.put(9, "Septiembre");
			meses.put(10, "Octubre");meses.put(11, "Noviembre");meses.put(12, "Diciembre");
			if(mes > 0 && mes < 13)
				condiciones += " AND periodo = 'Mensual' AND MES = '" + meses.get(mes) + "'";
		}
		return condiciones;
	}

	public void imprimirDatosClase(Object objeto){
		String datos = "--------------------------------------------------------------------------\n";
		datos += "Clase: " + objeto.getClass() + "\n\n";
		Method[] metodos = objeto.getClass().getDeclaredMethods();
		List<Method> metodosGet = new ArrayList<Method>();

		for (Method metodo : metodos) {
			if (metodo.getName().contains("get")) {
				metodosGet.add(metodo);
			}
		}

		boolean _ = true;

		for (Method metodo : metodosGet) {
			Class[] argumentos = metodo.getParameterTypes();
			try {

				datos += "Metodo: " + metodo.getName() + (_ ? " ---------------------: " : " - - - - - - - - - - -: ")
						+ metodo.invoke(objeto, argumentos) + "\n";
				_ = !_;
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		log.info(datos);
	}


	public int descomponerCadenaDescripcionCatalogoUSP(Producto producto){
		try {
			if (producto.getTipo().equals("Estandares") && (producto.getUnidad() == null || producto.getUnidad().equals("") ||
					producto.getCantidad() == null || producto.getCantidad().equals(""))) {

				if (producto.getConcepto() == null || producto.getConcepto().equals("")) {
					return 4;
				}

				String cadena = producto.getConcepto();

				if (!cadena.contains("(") && !cadena.contains(")") ) {
					return 1;
				}else if(!cadena.contains("(")){
					return 2;
				}else if(!cadena.contains(")")){
					return 2;
				}else if(cadena.indexOf("(") > cadena.indexOf(")")){
					return 2;
				}

				boolean medida =false;

				while(cadena.contains("(") && cadena.contains(")")){
					String datos = cadena.substring(cadena.indexOf("(")+1,cadena.indexOf(")")).trim();
					String[] valores;

					//log.info(datos);

					if (datos.contains("ml") || datos.contains("mg") || datos.contains("mL") || datos.contains("cm")) {
						medida = true;
					}

					if (datos.contains(";") && datos.contains("/")) {
						datos = datos.substring(0,datos.indexOf("/"));
					}

					if (datos.contains("/")) {
						datos = datos.substring(0,datos.indexOf("/"));
					}


					valores = datos.split(" ");

					if (valores.length == 2) {
						try {
							Double.parseDouble(valores[0]);

							producto.setCantidad(valores[0]);
							producto.setUnidad(valores[1]);
							return 0;

						} catch (Exception e) {}
					}else if (valores.length == 4 && (datos.contains(" x ") || datos.contains(" X "))){
						producto.setCantidad(datos.substring(0,datos.indexOf(valores[3])));
						producto.setUnidad(valores[3]);
						return 0;
					}else{

					}
					cadena = cadena.substring(cadena.indexOf(")")+1, cadena.length());
				}
				if (medida) {
					return 6;
				}else{
					return 3;
				}
			}else{
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.enviarCorreoAvisoExepcion(e);
			return 5;
		}
	}

	public int descomponerCadenaDescripcionCatalogoUSPObjet(CatalogoUSP producto){
		try {
			if (producto.getUnidad() == null || producto.getUnidad().equals("") ||
					producto.getCantidad() == null || producto.getCantidad().equals("")) {

				if (producto.getConceptoUSP() == null || producto.getConceptoUSP().equals("")) {
					return 4;
				}

				String cadena = producto.getConceptoUSP();

				if (!cadena.contains("(") && !cadena.contains(")") ) {
					return 1;
				}else if(!cadena.contains("(")){
					return 2;
				}else if(!cadena.contains(")")){
					return 2;
				}else if(cadena.indexOf("(") > cadena.indexOf(")")){
					return 2;
				}

				boolean medida =false;

				while(cadena.contains("(") && cadena.contains(")")){
					String datos = cadena.substring(cadena.indexOf("(")+1,cadena.indexOf(")")).trim();
					String[] valores;

					//log.info(datos);

					if (datos.contains("ml") || datos.contains("mg") || datos.contains("mL") || datos.contains("cm")) {
						medida = true;
					}

					if (datos.contains(";") && datos.contains("/")) {
						datos = datos.substring(0,datos.indexOf("/"));
					}

					if (datos.contains("/")) {
						datos = datos.substring(0,datos.indexOf("/"));
					}


					valores = datos.split(" ");

					if (valores.length == 2) {
						try {
							Double.parseDouble(valores[0]);

							producto.setCantidad(valores[0]);
							producto.setUnidad(valores[1]);
							return 0;

						} catch (Exception e) {}
					}else if (valores.length == 4 && (datos.contains(" x ") || datos.contains(" X "))){
						producto.setCantidad(datos.substring(0,datos.indexOf(valores[3])));
						producto.setUnidad(valores[3]);
						return 0;
					}else{

					}
					cadena = cadena.substring(cadena.indexOf(")")+1, cadena.length());
				}
				if (medida) {
					return 6;
				}else{
					return 3;
				}
			}else{
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.enviarCorreoAvisoExepcion(e);
			return 5;
		}
	}



	public String obtenerFechaConFormato(String formato){
		return String.format(new SimpleDateFormat(formato).format(new Date()));
	}

	public String obtenerFechaConFormato(String formato,Date fecha){
		return String.format(new SimpleDateFormat(formato).format(fecha));
	}


	public String encriptarLink(String cadena) {
		String encriptado="";
		for(int i=0; i<cadena.length(); i++){
			if(i==0){
				encriptado=this.encriptarCaracter(cadena.substring(i,i+1), cadena.length(), i);
			}else if(i==(cadena.length()-1)){
				encriptado+=cadena.substring(i,i+1);
			}else if(i!=0 && i!=(cadena.length()-1)){
				encriptado+=this.encriptarCaracter(cadena.substring(i,i+1), cadena.length(), i);
			}
		}
		return encriptado;
	}


	public String encriptarCaracter(String caracter, int variable, int indice) {
		String busqueda = "8fWX09AwB7JCpDud5E6FQGlxnHMbcI3K0LeUN4z1ms2PtRvSVk-qirTYaghZjoy";
		String key = "wxVkBU8nIGjFl-m7f0AH1bcK3h5diWJ4ZLCpDeMvuXqraYE6gosyNzOP2RSt9TQ";
		int indi;
		if(busqueda.indexOf(caracter)!=-1){
			indi=(busqueda.indexOf(caracter)+variable+indice)%busqueda.length();
			return key.substring(indi,indi+1);
		}
		return caracter;
	}

	public static void main(String[] args)
			throws IOException, TikaException, SAXException {

		InputStream is = null;
		try {
			is = new URL("http://187.189.39.50:51725/SAP/Facturas/Proveedora/20265.pdf").openStream();
			ContentHandler contenthandler = new BodyContentHandler();
			Metadata metadata = new Metadata();
			PDFParser pdfparser = new PDFParser();
			pdfparser.parse(is, contenthandler, metadata, new ParseContext());
			log.info(contenthandler.toString());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (is != null) is.close();
		}
	}

	public byte[] crearDocumentosXLS(List<List<String>> datos)
	{
		//datos = llenarLista();
		log.info("Inicia\n" + new Date());
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("PrecioProductos");
		File archivo = new File(RUTA_DOCUMENTOS + "/tmp");
		if (!archivo.exists())
		{
			archivo.mkdirs();
		}
		archivo = new File(RUTA_DOCUMENTOS + "tmp/PrecioProductos.xls");

		Row fila;
		Cell celda;

		CellStyle estiloEncabezado = estiloEncabezado(workbook);
		CellStyle primeraColumna = primeraColumna(workbook);
		CellStyle currencyFormat = currencyFormat(workbook);

		for (int i = 0; i < datos.size(); i++)
		{
			fila = sheet.createRow(i);
			for (int j = 0; j < datos.get(i).size(); j++)
			{
				celda = fila.createCell(j);
				celda.setCellValue(datos.get(i).get(j));
				if (i == 0)
				{
					celda.setCellStyle(estiloEncabezado);
				}
				else if (j == 0)
				{
					celda.setCellStyle(primeraColumna);
				}
				else if (j == 6 || j == 7)
				{
					celda.setCellValue(Double.parseDouble(datos.get(i).get(j)));
					celda.setCellStyle(currencyFormat);
				}
				if (i == (datos.size()-1))
				{
					sheet.autoSizeColumn(j);
				}

			}
		}
		try {
			FileOutputStream out = new FileOutputStream(archivo);
			workbook.write(out);
			out.close();
			log.info("FINALIZO");
			log.info("", new Date());
			return workbook.getBytes();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private CellStyle estiloEncabezado(HSSFWorkbook libro)
	{
		CellStyle cellStyle = libro.createCellStyle();
		HSSFPalette palette = libro.getCustomPalette();
		palette.setColorAtIndex(HSSFColor.AQUA.index, (byte) 37, (byte) 125, (byte) 146);
		palette.setColorAtIndex(HSSFColor.GREY_40_PERCENT.index, (byte) 170, (byte) 170, (byte) 170);
		Font cellFont = libro.createFont();
		cellFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		cellFont.setColor(HSSFColor.WHITE.index);
		cellStyle.setFont(cellFont);
		cellStyle.setFillForegroundColor(HSSFColor.AQUA.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		borderCelda(cellStyle);
		cellStyle.setDataFormat((short) 1);

		return cellStyle;
	}

	private CellStyle primeraColumna(HSSFWorkbook libro) {
		CellStyle cellStyle = libro.createCellStyle();
		HSSFPalette palette = libro.getCustomPalette();
		palette.setColorAtIndex(HSSFColor.GREY_25_PERCENT.index, (byte) 234, (byte) 234, (byte) 234);
		palette.setColorAtIndex(HSSFColor.GREY_40_PERCENT.index, (byte) 170, (byte) 170, (byte) 170);
		Font cellFont = libro.createFont();
		cellFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		cellStyle.setFont(cellFont);
		cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		borderCelda(cellStyle);
		cellStyle.setDataFormat((short) 1);

		return cellStyle;
	}

	private void borderCelda(CellStyle cellStyle)
	{
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyle.setBottomBorderColor(HSSFColor.GREY_40_PERCENT.index);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle.setRightBorderColor(HSSFColor.GREY_40_PERCENT.index);
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle.setLeftBorderColor(HSSFColor.GREY_40_PERCENT.index);
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		cellStyle.setTopBorderColor(HSSFColor.GREY_40_PERCENT.index);
	}

	private CellStyle currencyFormat(HSSFWorkbook libro)
	{
		CellStyle cellStyle = libro.createCellStyle();
		HSSFDataFormat dataFormat = libro.createDataFormat();
		cellStyle.setDataFormat(dataFormat.getFormat("$#,##0.00"));
		//cellStyle.setDataFormat((short) 7);
		return cellStyle;
	}

	private static List<List<String>> llenarLista()
	{
		List<List<String>> datos = new ArrayList<List<String>>();
		for (int i = 0; i < 3210; i++) {
			List<String> filas = new ArrayList<String>();
			for (int j = 0; j < (Math.floor(Math.random() * (50 - 10 + 1)) + 10); j++) {
				String celda = "" + j;
				filas.add(celda);
			}
			datos.add(filas);
		}

		return datos;
	}

	public static Boolean subirArchivo(byte[] bytes, String nombre, String carpeta) throws ProquifaNetException {
		try {

			File file = new File(carpeta);
			if (! file.exists()){
				file.mkdirs();
			}

			file = new File(carpeta + nombre);
			FileOutputStream fos;

			fos = new FileOutputStream(file);
			fos.write(bytes);
			fos.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}


	}

	public static String quitaApostrofes(String cadena) throws ProquifaNetException {
		String cadSinApostrofes = "";
		cadSinApostrofes = cadena.replace("'", "''");

		return cadSinApostrofes;
	}

	public int generarCodigo4Digitos(){

		Random rnd = new Random();
		int numrandom = (int) (rnd.nextDouble() * 9000 + 1000);
		log.info("", numrandom);

		return numrandom;
	}


	public String cuerpoNotificadoPConnect(Producto producto) {

		String mensaje = "";
		String lote = "";
		String icono = "";
		if (producto.getTipo().equals("loteCaduco")) {
			lote = "Lote Caducado";
			mensaje = "ha rebasado su fecha de expiracio&oacute;n.";
			icono = "https://codlpw.bl3301.livefilestore.com/y3mN_2fAQP4jmfrURiJzgh7LbyNSKnWwGfqe9LLvBLRHHeOKI74IxIILXl9Eu2mPsIivy2sNNwwyeXm8AhGV85Q38jsWD8ryBSRRDoLGkLTe90eXgxMApRvcOCddXI1BZwvcTR1nbpG4oiBexBKrq4s3y7x3MFsCWmqQkqDQX1hWNw";
		} else if (producto.getTipo().equals("lotePrevio")) {
			mensaje = "ha pasado a lote previo con m&aacute;s de 30 d&iacute;as de caducidad.";
			lote = "Lote previo (m&aacute;s de 30 d&iacute;as)";
			icono = "https://s5.postimg.org/mzsng6o8j/ico_Filtro_Mas_De_Un_Mes_Gris.png";
		} else{
			mensaje = "se encuentra menos de 30 d&iacute;as de su fecha de expiracio&oacute;n.";
			lote = "ha pasado de lote previo con m&aacute;s de 30 d&iacute;as de caducidad a lote previo con 30 o menos d&iacute;as de caducidad.";
			icono = "https://s5.postimg.org/4y9iidu7n/ico_Filtro_Menos_De_Un_Mes_Gris.png";
		}

		String cuerpo = "";
		cuerpo += "<html > ";
		cuerpo += "<head><style> .pos {position: relative;} .abs{position: absolute; width: 100%; top: 0;}</style></head> ";
		cuerpo += " <body style='max-width: 700px;'>";
		cuerpo += "    <div style='border: 1px solid #F0F0F0; width: 700px; border-radius: 35px; box-shadow: 0px 14px 21px -4px rgba(0,0,0,0.18);'>";
		cuerpo += "        <div style='height: 145px;'><img src='https://uznioa.bl3302.livefilestore.com/y3mcukfGaGVogAaAxSFo2buYEE9DVNhKlVXV3UCioKbxFcPxVdTTAk82YIYLMeilFUFlnwUYm9OAAmXl47O9H6BZHW_N168dbFFmrW1264J7rCwJ9nYqoqtVYxHClai-zwsuLHokmpO4YWZ7oIUbJ05NaKWgfARSefL0VTJlmznZds' /></div>";
		cuerpo += "        <div style='background: #414044; height: 45px; display: flex; align-items: center; padding-left: 30px;'>";
		cuerpo += "            <img style='width: 31px; height: 29px;' src='" + icono + "'>";
		cuerpo += "            <span style='color: #FFFFFF; font-size: 26px; font-weight: bold; font-family: Arial; margin-left: 20px;'>" + lote + "</span>";
		cuerpo += "        </div>";
		cuerpo += "        <div style='margin: 30px;'>";
		cuerpo += "            <span style='color: #008b9d; font-size: 26px; font-weight: bold; font-family: Arial;'>Estimado Usuario</span>";
		cuerpo += "        </div>";
		cuerpo += "        <div style='color: #666666; font-size: 18px; font-weight: normal; font-family: Arial; margin: 0 30px; text-align: justify;'>";
		cuerpo += "            <span>Le informamos que el Est&aacute;ndar <span style='font-weight: bold;'>" + producto.getConcepto() + ")</span> de la marca <span style='font-weight: bold;'>" + producto.getFabrica() + "</span> con el lote <span style='font-weight: bold;'>" + producto.getLote() + "</span> " + mensaje + "</span>";
		cuerpo += "        </div>";
		cuerpo += "        <div style='margin-top: 45px; margin-left: 45px; display: flex;'>";
		cuerpo += "            <img style='height: 160px;' src='" + obtenerImagenPorProveedor(producto) + "' />";
		cuerpo += "            <div style='margin-left: 35px; color: #008b9d; font-size: 16px; font-weight: normal; font-family: Arial; display: inline-block; margin-top: 10px;'>";
		cuerpo += "                <span style='margin: 5px 0; display: block;'>Marca.<span style='color: #666666; font-size: 18px;'>" + producto.getFabrica() + "</span></span>";
		cuerpo += "               <span style='margin: 5px 0; display: block;'>No. Cat.<span style='color: #666666; font-size: 18px;'>" + producto.getCodigo() + "</span></span>";
		cuerpo += "                <span style='margin: 5px 0; display: block;'>Lote.<span style='color: #666666; font-size: 18px;'>" + producto.getLote() + "</span></span>";
		cuerpo += "                <span style='margin: 5px 0; display: block;'>Control.<span style='color: #666666; font-size: 18px;'>" + producto.getControl() + "</span></span>";
		cuerpo += "            </div> ";
		cuerpo += "        </div> ";
		cuerpo += "        <div class='pos' style='height: 120px; margin-top: 35px;'> ";
		cuerpo += "        	   <div><img src='https://s5.postimg.org/xsdoywg1j/footer.png' /></div>";
		cuerpo += "        </div> ";
		cuerpo += "    </div> ";
		cuerpo += " </body> ";
		cuerpo += "</html> ";

		return cuerpo;
	}

	private String obtenerImagenPorProveedor(Producto producto) {
		String imagen = "";
		if (producto.getProveedor() == 44) {
			if (producto.getTipoPresentacion()==null)
				imagen = "https://s5.postimg.org/p5icwy2b7/vial1.png";
			else{
				if (producto.getTipoPresentacion().equalsIgnoreCase("ampolleta"))
					imagen = "https://s5.postimg.org/m9w2yljjn/ampolleta1.png";
				else if (producto.getTipoPresentacion().equalsIgnoreCase("blister"))
					imagen = "https://s5.postimg.org/6gvpvvm0j/blister1.png";
				else if (producto.getTipoPresentacion().equalsIgnoreCase("bolsa de aluminio"))
					imagen = "https://s5.postimg.org/5jpan0aib/bolsa_de_aluminio1.png";
				else if (producto.getTipoPresentacion().equalsIgnoreCase("caja"))
					imagen = "https://s5.postimg.org/b0cjv4t9v/caja1.png";
				else
					imagen = "https://s5.postimg.org/p5icwy2b7/vial1.png";
			}
		}
		else if (producto.getProveedor() == 45)
			imagen = "https://s5.postimg.org/vn5uu722f/edqm_lleno.png";
		else if (producto.getProveedor() == 46)
			imagen = "https://s5.postimg.org/rfb2lg0mv/tbc1.png";
		else if (producto.getProveedor() == 50)
			imagen = "https://s5.postimg.org/muow6igxj/feum02_lleno.png";
		else if (producto.getProveedor() == 46)
			imagen = "https://s5.postimg.org/do6lj8bp3/trc1_2.png";

		return imagen;
	}

	public String cambiarTextoMoneda(String moneda) {
		if (moneda.equalsIgnoreCase("Dolares"))
			return "USD";
		else if (moneda.equalsIgnoreCase("EUR") || moneda.equalsIgnoreCase("Euros"))
			return "EURO";
		else
			return "M.N.";
	}

	public static boolean convertirPNGtoPDF(String rutaImagen, String rutaPDF, String nombrePDF) throws ProquifaNetException {
		try {
			File file = new File(rutaPDF);
			if (!file.exists())
				file.mkdirs();

			Document document = new Document(PageSize.LETTER);
			PdfWriter.getInstance(document, new FileOutputStream(rutaPDF + nombrePDF));
			document.open();
			Image image = Image.getInstance(rutaImagen);
			image.scaleToFit(850, 850);
			image.setRotationDegrees(270);
			document.add(image);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void copyFile(String fileName, String ruta, InputStream in) {
		try {
			File file = new File(ruta + "/");
			if (!file.exists()) {
				file.mkdirs();
			}
			OutputStream out = new FileOutputStream(new File(ruta + "/" + fileName));
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			in.close();
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
	}

	/*---------------*/

	public byte[] crearArchivoPDF(Map<String, Object> arguments, String tipo) {
		try {
			log.info("crearArchivoPDF ---- Tipo de PDF " +  tipo);
			List<JasperPrint> list = new ArrayList<JasperPrint>();
			List<String> templates = listaJasperPrint(tipo);
			Integer pag = 1;
			for(String t : templates){
				list.add(generarPDF(arguments, Funcion.RUTA_DOCUMENTOS + "Template" +  t, pag.toString()));
				pag++;
			}
			File file;
			File targetFile;
			if(tipo.indexOf("ReporteDiario") != -1){
				String dir = Funcion.RUTA_DOCUMENTOS + "/Empresa/" + arguments.get("idEmpresa") + "/Contabilidad/Polizas";
				file = new File(dir);
				targetFile = new File(dir + "/" + "Poliza_"+ arguments.get("idPoliza") + ".pdf");
			}else if(tipo.indexOf("CuentaAuxiliar") != -1){
				String dir = Funcion.RUTA_DOCUMENTOS + "/Empresa/" + arguments.get("idEmpresa") + "/Contabilidad/CuentaAuxilares";
				file = new File(dir);
				targetFile = new File(dir + "/" + "CuentaAuxiliar.pdf");
			}else {
				file = new File(Funcion.RUTA_DOCUMENTOS);
				targetFile = new File(Funcion.RUTA_DOCUMENTOS + "/" + "archivo.pdf");
			}
			if (!file.exists())
			{
				file.mkdirs();
			}
			log.info("---+++Ruta a donde se guardara: " + targetFile.getAbsolutePath());
			OutputStream outStream;
			byte[] bytes = generateReport(list);
			outStream = new FileOutputStream(targetFile);
			outStream.write(bytes);
			outStream.close();
			log.info("---+++Finalizo");
			return bytes;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String crearCodigoCC(CuentaContable cc) {
		try {
			String codigo = null;
			switch (cc.getNivel()) {
				case 1:
					codigo = cc.getNivel1() + "";
					break;
				case 2:
					codigo = cc.getNivel1() + ".";
					codigo += (cc.getNivel2() < 10) ? "00" + cc.getNivel2() : (cc.getNivel2() > 9 && cc.getNivel2() < 100) ? "0" + cc.getNivel2() : cc.getNivel2() + "";
					break;
				case 3:
					codigo = cc.getNivel1() + ".";
					codigo += (cc.getNivel2() < 10) ? "00" + cc.getNivel2() : (cc.getNivel2() > 9 && cc.getNivel2() < 100) ? "0" + cc.getNivel2() : cc.getNivel2() + "";
					codigo += ".";
					codigo += (cc.getNivel3().length() == 1) ? "000" + cc.getNivel3() : (cc.getNivel3().length() == 2) ? "00" + cc.getNivel3() : (cc.getNivel3().length() == 3) ? "0" + cc.getNivel3() : cc.getNivel3().toString();
					break;
			}
			return codigo;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String fechaTextoRango(Date fechaInicial, Date fechaFinal) throws Exception{
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(fechaInicial);
		Calendar calendar2 = new GregorianCalendar();
		calendar2.setTime(fechaFinal);
		String fecha = "Del " + fechaInicial.getDay() +
				" de " + obtenerMesTexto(fechaInicial.getMonth()) +
				" de " +  calendar.get(Calendar.YEAR) +//fechaInicial.getYear() +
				" al " + fechaFinal.getDay() +
				" de " + obtenerMesTexto(fechaFinal.getMonth()) +
				" de " + calendar2.get(Calendar.YEAR); //fechaInicial.getYear();
		return fecha;
	}

	public String formatoFolio(Integer id, int tamFolio) {
		String folio = null;
		int tamID = id.toString().length();
		folio = (tamFolio > tamID) ? new String(new char[tamFolio - tamID]).replace("\0", "0") : id.toString();
		return folio;
	}

	private JasperPrint generarPDF(Map<String, Object> arguments, String path, String index) {
		JasperPrint jasperPrint = null;
		try {
			JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(path);
			List<Parametro> lst = new ArrayList<Parametro>();
			lst.add(new Parametro());
			JRDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(lst, true);
			jasperPrint = JasperFillManager.fillReport(jasperReport, arguments, beanCollectionDataSource);// .fillReport(jasperReport, arguments,(AsistentesDataSource) arguments.get("dataSource"));
			return jasperPrint;
		} catch (JRException e) {
			e.printStackTrace();
		}
		return null;
	}

	public byte[] generateReport(List<JasperPrint> jasperPrintList)throws JRException {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			JRPdfExporter exporter = new JRPdfExporter();
			ExporterInput exporterInput = SimpleExporterInput.getInstance(jasperPrintList);
			exporter.setExporterInput(exporterInput);
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
			SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
			configuration.setCreatingBatchModeBookmarks(true);
			exporter.setConfiguration(configuration);
			exporter.exportReport();
			return baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private List<String> listaJasperPrint(String tipo){
		List<String> templates = new ArrayList<String>();
		switch (tipo) {
			case "ReporteDiario1":
			case "ReporteDiario2":
			case "ReporteDiario3":
				templates.clear();
				templates.add("/reporteDiarioPQ.jasper");
				break;
			case "ReporteDiario4":
				templates.clear();
				templates.add("/reporteDiarioGO.jasper");
				break;
			case "ReporteDiario5":
				templates.clear();
				templates.add("/reporteDiarioMU.jasper");
				break;
			case "ReporteDiario8":
				templates.clear();
				templates.add("/reporteDiarioRY.jasper");
				break;

			case "CuentaAuxiliar1":
			case "CuentaAuxiliar2":
			case "CuentaAuxiliar3":
				templates.clear();
				templates.add("/cuentaAuxiliarPQ.jasper");
				break;
			case "CuentaAuxiliar4":
				templates.clear();
				templates.add("/cuentaAuxiliarGO.jasper");
				break;
			case "CuentaAuxiliar5":
				templates.clear();
				templates.add("/cuentaAuxiliarMU.jasper");
				break;
			case "CuentaAuxiliar8":
				templates.clear();
				templates.add("/cuentaAuxiliarRY.jasper");
				break;

			case "EstadoResultado1":
			case "EstadoResultado2":
			case "EstadoResultado3":
				templates.clear();
				templates.add("/estadoResultadoPQ.jasper");
				break;
			case "EstadoResultado4":
				templates.clear();
				templates.add("/estadoResultadoGO.jasper");
				break;
			case "EstadoResultado5":
				templates.clear();
				templates.add("/estadoResultadoMU.jasper");
				break;
			case "EstadoResultado8":
				templates.clear();
				templates.add("/estadoResultadoRY.jasper");
				break;
		}
		return templates;
	}

	public String obtenerMesTexto (Integer mes) throws Exception {
		String mesT = "";
		switch(mes) {
			case 0: mesT = "Enero"; break;
			case 1: mesT = "Febrero"; break;
			case 2: mesT = "Marzo"; break;
			case 3: mesT = "Abril"; break;
			case 4: mesT = "Mayo"; break;
			case 5: mesT = "Junio"; break;
			case 6: mesT = "Julio"; break;
			case 7: mesT = "Agosto"; break;
			case 8: mesT = "Septiembre"; break;
			case 9: mesT = "Octubre"; break;
			case 10: mesT = "Noviembre"; break;
			case 11: mesT = "Diciembre"; break;
		}
		return mesT;
	}

}
