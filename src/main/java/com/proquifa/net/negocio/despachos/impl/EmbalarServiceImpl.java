package com.proquifa.net.negocio.despachos.impl;

import java.awt.Color;  
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.WordUtils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.ContentHandler;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.proquifa.net.modelo.cobrosypagos.facturista.PartidaFactura;
import com.proquifa.net.modelo.compras.PartidaCompra;
import com.proquifa.net.modelo.compras.Pieza;
import com.proquifa.net.modelo.comun.Archivo;
import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.comun.Etiqueta;
import com.proquifa.net.modelo.comun.Folio;
import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.Pendiente;
import com.proquifa.net.modelo.comun.Producto;
import com.proquifa.net.modelo.comun.ValidarCFDI;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.facturaElectronica.FacturaElectronica;
import com.proquifa.net.modelo.comun.util.BodyFileEmail;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.despachos.BolsaParaEmbalar;
import com.proquifa.net.modelo.despachos.DocumentoXLS;
import com.proquifa.net.modelo.despachos.EmbalarPedido;
import com.proquifa.net.modelo.despachos.EstadisticaUsuarioEmbalar;
import com.proquifa.net.modelo.despachos.OrdenEntrega;
import com.proquifa.net.modelo.despachos.PRemision;
import com.proquifa.net.modelo.despachos.PackingListJasper;
import com.proquifa.net.modelo.despachos.PartidaInspeccion;
import com.proquifa.net.modelo.despachos.Remision;
import com.proquifa.net.modelo.despachos.TotalEmbalar;
import com.proquifa.net.modelo.despachos.colectarPartidas;
import com.proquifa.net.modelo.despachos.etiquetasYfolios;
import com.proquifa.net.modelo.despachos.totalesInspeccionProducto;
import com.proquifa.net.modelo.despachos.packinglist.PackingList;
import com.proquifa.net.modelo.despachos.packinglist.PartidaPackingList;
import com.proquifa.net.modelo.despachos.trabajarRuta.TrPackingList;
import com.proquifa.net.modelo.ventas.PartidaPedido;
import com.proquifa.net.modelo.ventas.Pedido;
import com.proquifa.net.modelo.ventas.admoncomunicacion.Correo;
import com.proquifa.net.negocio.comun.FacturaElectronicaService;
import com.proquifa.net.negocio.despachos.EmbalarService;
import com.proquifa.net.persistencia.cobrosypagos.facturista.FacturacionDAO;
import com.proquifa.net.persistencia.cobrosypagos.facturista.PfacturasDAO;
import com.proquifa.net.persistencia.compras.CompraDAO;
import com.proquifa.net.persistencia.compras.PartidaCompraDAO;
import com.proquifa.net.persistencia.comun.ClienteDAO;
import com.proquifa.net.persistencia.comun.EmpleadoDAO;
import com.proquifa.net.persistencia.comun.FolioDAO;
import com.proquifa.net.persistencia.comun.FuncionDAO;
import com.proquifa.net.persistencia.comun.PendienteDAO;
import com.proquifa.net.persistencia.comun.ProductoDAO;
import com.proquifa.net.persistencia.comun.ReportesDAO;
import com.proquifa.net.persistencia.despachos.EmbalarDAO;
import com.proquifa.net.persistencia.despachos.trabajarRuta.TrEnvioDAO;
import com.proquifa.net.persistencia.ventas.PartidaPedidoDAO;
import com.proquifa.net.persistencia.ventas.PedidoDAO;

import net.itsystem.ConvertNumber;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@Service("embalarService")
public class EmbalarServiceImpl implements EmbalarService {

	@Autowired
	EmbalarDAO embalarDAO;
	@Autowired
	EmpleadoDAO empleadoDAO;
	@Autowired
	FuncionDAO funcionDAO;
	@Autowired
	ProductoDAO productoDAO;
	@Autowired
	PartidaCompraDAO partidaCompraDAO;
	@Autowired
	PartidaPedidoDAO partidaPedidoDAO;
	@Autowired
	PendienteDAO pendienteDAO;
	@Autowired
	CompraDAO compraDAO;
	@Autowired
	FacturacionDAO facturacionDAO;
	@Autowired
	PfacturasDAO pfacturasDAO;
	@Autowired
	FolioDAO folioDAO;
	@Autowired
	ClienteDAO clienteDAO;
	@Autowired
	PedidoDAO pedidoDAO;

	@Autowired
	TrEnvioDAO trEnvioDAO;
	
	@Autowired
	FacturaElectronicaService facturaElectronicaService;
	
	@Autowired
	ReportesDAO reportesDAO;
	
	final Logger log = LoggerFactory.getLogger(EmbalarServiceImpl.class);

	Funcion util = new Funcion();
	PartidaCompra pcompraOriginal = null;
	PartidaPedido ppedidoOriginal = null;
	// private final static Logger logger =
	// Logger.getLogger(EmbalarServiceImpl.class);

	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public List<PartidaInspeccion> listarPartidasAInspeccion(Long idEmpleado, String compra)
			throws ProquifaNetException {
		Empleado e = this.empleadoDAO.obtenerEmpleadoPorId(idEmpleado);
		List<PartidaInspeccion> partidaXInsp = this.embalarDAO.finPartidasAInspeccionar(e.getUsuario(),
				e.getNombreFuncion(), compra);
		for (PartidaInspeccion p : partidaXInsp) {
			if (p.getIdComplemento() == 0) {
				p.setDescripcionProducto(this.productoDAO.obtenerDescripcionProducto(p.getCodigo(), p.getFabrica()));
			} else {
				p.setDescripcionProducto(this.productoDAO.obtenerDescripcionProductoComplemento(p.getIdComplemento()));
			}
		}
		return partidaXInsp;
	}

	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public Boolean regresarPartidAMonitoreo(Long idPCompra) throws ProquifaNetException {
		Boolean resultado = false;
		PartidaInspeccion partMonitoreo = new PartidaInspeccion();
		partMonitoreo = this.embalarDAO.getPCAMonitoreo(idPCompra);
		if (partMonitoreo != null) {
			pcompraOriginal = this.partidaCompraDAO.getPCompraXid(partMonitoreo.getIdPartidaCompra());
			pcompraOriginal.setEstado(partMonitoreo.getEdoPrevioPC());
			this.partidaCompraDAO.actualizarPCompra(pcompraOriginal);
			if (partMonitoreo.getInspector().equals("Almacenista de Entradas")) {
				ppedidoOriginal = this.partidaPedidoDAO.getPPedidoXid(partMonitoreo.getIdPPedido(), true, true);
				ppedidoOriginal.setEstado(partMonitoreo.getEdoPrevioPP());
				this.partidaPedidoDAO.actualizarPPedido(ppedidoOriginal);
				this.embalarDAO.eliminarEstadoPPedidoI(partMonitoreo.getIdPPedido());
			}
			this.embalarDAO.eliminarEstadoPCompraI(idPCompra);
			this.embalarDAO.eliminarInspeccionOC(idPCompra, partMonitoreo.getInspector());
			resultado = true;
		}
		return resultado;
	}

	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public Boolean cerrarPendientesInspeccionMonitoreo(String folioOC, String funcionUsurio)
			throws ProquifaNetException {
		Boolean status = false;
		status = this.embalarDAO.cerrarPendienteInspeccionOC(folioOC, funcionUsurio);
		status = this.compraDAO.cerrarPendienteDeMonitoreoOC(folioOC);
		return status;
	}

	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public Boolean actualizarPartidaInspeccion(PartidaInspeccion partidaEnInspeccion, Boolean rechazoDocto,
			byte[] bytes) throws ProquifaNetException {
		if (rechazoDocto) {
			// logger.info("Comprobando pendiente.");
			Long numPendientes = this.pendienteDAO.obtenerNumeroPendientes(
					partidaEnInspeccion.getIdPartidaCompra().toString(), "ComPHS-USA",
					"Rechazo por documentacion PHS-USA", true);
			if (numPendientes > 0) {
				// logger.info("El pendiente ya existe..");
			} else {
				Pendiente pendiente = new Pendiente();
				pendiente.setDocto(partidaEnInspeccion.getIdPartidaCompra().toString());
				pendiente.setResponsable("ComPHS-USA");
				pendiente.setTipoPendiente("Rechazo por documentacion PHS-USA");
				pendiente.setPartida(null);
				this.pendienteDAO.guardarPendiente(pendiente);
				// logger.info("Se levanta el pendiente de rechazo por
				// documento");
			}
		}
		String tabla = "";
		String edoPCompra = "";
		if (partidaEnInspeccion.getInspector().equals("Pharma")) {
			tabla = "InspeccionOCPhs";
			edoPCompra = "En tránsito PhS-USA";
		} else if (partidaEnInspeccion.getInspector().equals("Matriz")) {
			tabla = "InspeccionOC";
			edoPCompra = "En tránsito Matriz";
		}
		// logger.info("Buscando partida de compra y partida de pedido
		// original.");
		pcompraOriginal = this.partidaCompraDAO.getPCompraXid(partidaEnInspeccion.getIdPartidaCompra());
		ppedidoOriginal = this.partidaPedidoDAO.getPPedidoXid(partidaEnInspeccion.getIdPPedido(), true, true);
		PartidaCompra nuevaPC = this.partidaCompraDAO.copiarPCompra(pcompraOriginal);
		PartidaPedido nuevaPP = this.partidaPedidoDAO.copiarPPedido(ppedidoOriginal);

		// Si la cantidad de producto no fue la que se esperaba, se debera de
		// dividir.
		if (!partidaEnInspeccion.getCantidadCompra().toString()
				.equals(pcompraOriginal.getCantidadCompra().toString())) {
			Integer ppedidoSiguiente = this.partidaPedidoDAO.getMaxPPedido(ppedidoOriginal.getCpedido());
			nuevaPP.setPart(ppedidoSiguiente);
			nuevaPP.setEstado("Compra");
			nuevaPP.setCant(ppedidoOriginal.getCantidadPedida() - partidaEnInspeccion.getCantidadCompra());
			nuevaPP.setFaltan(ppedidoOriginal.getCantidadPedida() - partidaEnInspeccion.getCantidadCompra());
			this.partidaPedidoDAO.insertarPPedido(nuevaPP);
			// logger.info("Se dividir la partida del pedido y se creo el
			// idPPedido:"+ nuevoPPedido);

			Integer pcompraSiguiente = this.partidaCompraDAO.getMaxPcompra(partidaEnInspeccion.getCompra());
			nuevaPC.setPartida(pcompraSiguiente);
			nuevaPC.setPartidaPedido(ppedidoSiguiente);
			nuevaPC.setCantidadCompra(pcompraOriginal.getCantidadCompra() - partidaEnInspeccion.getCantidadCompra());
			nuevaPC.setEstado(edoPCompra);
			this.partidaCompraDAO.insertarPCompra(nuevaPC);
			// logger.info("Se dividir la partida de la compra y se creo el
			// idPCompra:"+ nuevoPcompra);
			PartidaFactura pf = this.pfacturasDAO.getPFacturaXCPedido(ppedidoOriginal.getCpedido(),
					ppedidoOriginal.getPart());
			if (pf != null) {
				pf.setCantidad(partidaEnInspeccion.getCantidadCompra());
				this.pfacturasDAO.actualizarPFacturas(pf);
				Integer pfacSiguiente = this.pfacturasDAO.getMaxPFacturas(pf.getFactura(), pf.getFpor());
				pf.setCantidad(pcompraOriginal.getCantidadCompra() - partidaEnInspeccion.getCantidadCompra());
				pf.setPartidaFactura(pfacSiguiente);
				pf.setPpedido(ppedidoSiguiente);
				this.pfacturasDAO.insertarPFacturas(pf);
				// logger.info("Existia factura por lo tanto se divide,
				// actualiza e inserta la nueva idPFactura: "+ nuevaPFactura);
			}
		}
		// Boolean updateInsert = false;
		pcompraOriginal.setCantidadCompra(partidaEnInspeccion.getCantidadCompra());
		this.partidaCompraDAO.actualizarPCompra(pcompraOriginal);
		// logger.info("Se actualizo la partida de la compra original: "+
		// updateInsert);
		ppedidoOriginal.setCantidadPedida(partidaEnInspeccion.getCantidadCompra());
		ppedidoOriginal.setFaltan(partidaEnInspeccion.getCantidadCompra());
		this.partidaPedidoDAO.actualizarPPedido(ppedidoOriginal);
		// logger.info("Se actualizo la partida del pedido original: "+
		// updateInsert);

		if (bytes != null) {
			Character especiales[] = { '\\', '/', ':' };
			String folioDoc = pcompraOriginal.getCodigo() + "-" + pcompraOriginal.getFabrica();
			for (int i = 0; i < especiales.length; i++) {
				folioDoc = folioDoc.replace(especiales[i], '_');
			}

			Producto producto = this.productoDAO.obtenerProductoXId(partidaEnInspeccion.getIdProducto());
			producto.setDocumentacion(folioDoc);
			this.productoDAO.actualizarProducto(producto);
			partidaEnInspeccion.setDocumento(folioDoc);
			// logger.info("Se esta recibiendo un archivo y por lo tanto se
			// tiene que subir al servidor.");
			util.copiarArchivo(bytes, folioDoc + ".pdf", "docInspeccion");
			// logger.info("Se actualizo el producto con el documento nuevo");
		}

		partidaEnInspeccion.setFechaFinInspeccion(null);
		partidaEnInspeccion.setInspector(null);
		if (this.embalarDAO.validarPartidaInspeccionOC(partidaEnInspeccion.getIdPartidaCompra(), tabla)) {
			this.embalarDAO.actualizarInspeccionOC(partidaEnInspeccion, tabla);
			// logger.info("Se actualizo el registro en la tabla de:"+ tabla);
		} else {
			this.embalarDAO.insertarInspeccionOC(partidaEnInspeccion, tabla);
			// logger.info("Se inserto el registro en la tabla "+ tabla +", ya
			// que este no existia en la misma.");
		}
		return true;
	}

	// ------------------------------------------------------------------------
	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public Boolean guardarPiezasCompra(List<Pieza> piezasCompra, String accion, String inspector)
			throws ProquifaNetException {
		String tabla = "";
		if (inspector.equals("Pharma")) {
			tabla = "PiezaPHS";
		} else if (inspector.equals("Matriz")) {
			tabla = "Pieza";
		}
		for (Pieza objPieza : piezasCompra) {
			if (objPieza.getEdoPieza().equals("Pendiente")) {
				objPieza.setDespachable(null);
			} else if (objPieza.getEdoPieza().equals("Despachable")) {
				objPieza.setDespachable(true);
			} else if (objPieza.getEdoPieza().equals("No Despachable")) {
				objPieza.setDespachable(false);
			}
			// logger.info("Numero de archivos: " +
			// objPieza.getListArchivos().size() + " de la pieza: " +
			// objPieza.getIdPieza());
			if (objPieza.getListArchivos() != null) {
				ListIterator<Archivo> lista = objPieza.getListArchivos().listIterator();
				while (lista.hasNext()) {
					Archivo archivoPieza = lista.next();
					// logger.info("Archivo: " + archivoPieza.getDescripcion());
					String folioNuevo = nombreArchivo(archivoPieza, objPieza.getCodigo() + "-" + objPieza.getFabrica(),
							tabla, objPieza.getIdPieza());
					if (folioNuevo != null && !folioNuevo.equals("")) {
						archivoPieza.setFolio(folioNuevo);
					} else {
						lista.remove();
					}
				}
			}
			if (accion.equals("Guardar")) {
				Long idPieza = this.embalarDAO.insertarPiezas(tabla, objPieza);
				if (idPieza == -1) {
					return false;
				}
				// logger.info("Pieza insertada: "+ idPieza);
			} else if (accion.equals("Actualizar")) {
				Boolean ok = this.embalarDAO.actualizarPieza(objPieza, tabla);
				if (ok) {
					// logger.info("Pieza Actualizada: "+
					// objPieza.getIdPieza());
				} else {
					return false;
				}
			}
		}
		return true;
	}

	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	private String nombreArchivo(Archivo documento, String folioCertificado, String tabla, Long idPieza)
			throws ProquifaNetException {
		Date fecha = new Date();
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd");
		Character especiales[] = { '\\', '/', ':' };
		Long consecutivo = null;
		String folioGuardado = this.embalarDAO.getFolioDocPieza(idPieza, tabla, documento.getDescripcion());

		for (int i = 0; i < especiales.length; i++) {
			documento.setFolio(documento.getFolio().replace(especiales[i], '_'));
			folioCertificado = folioCertificado.replace(especiales[i], '_');
		}
		String folioObtenido = documento.getFolio();
		if (documento.getDescripcion().equals("Certificado")
				|| documento.getDescripcion().equals("Hoja de seguridad")) {
			if (!documento.getFolio().equals(folioCertificado)) {
				if (folioGuardado == null || !folioGuardado.contains("DI-")) {
					consecutivo = this.folioDAO.obtenerConsecutivoFolio("DI");
					this.folioDAO.actualizarValorConsecutivo("DI", consecutivo);
					folioObtenido = "DI-" + formatoFecha.format(fecha) + "-" + consecutivo;
				} else {
					folioObtenido = folioGuardado;
				}
				documento.setExtension(".pdf");
			}
		} else if (!documento.getDescripcion().equals("")) {
			if (folioGuardado == null || !folioGuardado.contains("FR-")) {
				consecutivo = this.folioDAO.obtenerConsecutivoFolio("FR");
				this.folioDAO.actualizarValorConsecutivo("FR", consecutivo);
				folioObtenido = "FR-" + formatoFecha.format(fecha) + "-" + consecutivo;
			} else {
				folioObtenido = folioGuardado;
			}
			documento.setExtension(".jpg");
		} else {
			folioObtenido = "";
		}

		if (tabla.equals("PiezaPHS") && !folioObtenido.contains("-PHS")) {
			folioObtenido = folioObtenido + "-PHS";
		}
		if (documento.getCadenaDeBytes() != null) {
			try {
				if (!folioObtenido.equals("")) {
					// logger.info("Se esta recibiendo un archivo y por lo tanto
					// se tiene que subir al servidor.");
					if (folioObtenido.contains("FR-")) {
						util.copiarArchivo(documento.getCadenaDeBytes(), folioObtenido + documento.getExtension(),
								"imagenRechazo");
					} else {
						util.copiarArchivo(documento.getCadenaDeBytes(), folioObtenido + documento.getExtension(),
								"docPieza");
					}
				}
			} catch (ProquifaNetException e) {
				// logger.error(e.getMessage());
			}
		}
		return folioObtenido;
	}

	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public List<Pieza> listarPiezasPCompra(Long idPCompra, String inspector) throws ProquifaNetException {
		String tabla = "";
		if (inspector.equals("Pharma")) {
			tabla = "PiezaPHS";
		} else if (inspector.equals("Matriz")) {
			tabla = "Pieza";
		}
		List<Pieza> piezas = this.embalarDAO.finPiezasPCompra(idPCompra, tabla);
		return piezas;
	}

	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public List<Etiqueta> finalizarInspeccionPharma(PartidaInspeccion partida) throws ProquifaNetException {
		String estadofinal = "";
		pcompraOriginal = this.partidaCompraDAO.getPCompraXid(partida.getIdPartidaCompra());
		ppedidoOriginal = this.partidaPedidoDAO.getPPedidoXid(partida.getIdPPedido(), true, true);
		List<Pieza> piezasPCompra = this.embalarDAO.finPiezasPCompra(partida.getIdPartidaCompra(), "PiezaPHS");
		PartidaCompra pcompraNueva = this.partidaCompraDAO.copiarPCompra(pcompraOriginal);
		PartidaPedido ppedidoNuevo = this.partidaPedidoDAO.copiarPPedido(ppedidoOriginal);
		ArrayList<Etiqueta> foliosFD = new ArrayList<Etiqueta>();
//		Etiqueta fd = null;
		Pendiente pendienteRechazo = null;

		this.pendienteDAO.cerrarPendiente(partida.getIdPartidaCompra().toString(), null, "Monitorear reposici��n");
		this.pendienteDAO.cerrarPendiente(partida.getIdPartidaCompra().toString(), null, "Monitorear reclamo");

		// logger.info("lo que manda jimmy: "+ partida.getCantidadCompra());
		// logger.info("Lo que obtengo yo: "+
		// pcompraOriginal.getCantidadCompra());
		if (!partida.getCantidadCompra().toString().equals(pcompraOriginal.getCantidadCompra().toString())) {
			Integer ppedidoSiguiente = this.partidaPedidoDAO.getMaxPPedido(ppedidoOriginal.getCpedido());
			ppedidoNuevo.setPart(ppedidoSiguiente);
			ppedidoNuevo.setEstado("Compra");
			ppedidoNuevo.setCantidadPedida(ppedidoOriginal.getCantidadPedida() - partida.getCantidadCompra());
			ppedidoNuevo.setFaltan(ppedidoOriginal.getCantidadPedida() - partida.getCantidadCompra());
			this.partidaPedidoDAO.insertarPPedido(ppedidoNuevo);
			// logger.info("Nuevo idPPedido:"+ nuevoPPedido);
			Integer pcompraSiguiente = this.partidaCompraDAO.getMaxPcompra(partida.getCompra());
			pcompraNueva.setPartida(pcompraSiguiente);
			pcompraNueva.setPartidaPedido(ppedidoSiguiente);
			pcompraNueva.setCantidadCompra(pcompraOriginal.getCantidadCompra() - partida.getCantidadCompra());
			pcompraNueva.setUbicacion("Cuarentena");
			pcompraNueva.setFolioInspeccionPHS(partida.getFolioInspeccionPHS());
			Long nuevoPcompra = this.partidaCompraDAO.insertarPCompra(pcompraNueva);
			// logger.info("Nuevo idPCompra:"+ nuevoPcompra);
			// logger.info("Guardo estado Pcompras.");
			this.embalarDAO.insertarPCompraFolioInspeccion(nuevoPcompra, partida.getFolioInspeccionPHS(),
					"En tr��nsito PhS-USA");
			this.embalarDAO.insertarEstadoPCompra(nuevoPcompra, "En inspecci��n Phs");
			for (Pieza pieza : piezasPCompra) {
				if (!pieza.getDespachable()) {
					// logger.info("Actualizando todas las piezas no
					// despachables");
					pieza.setIdPCompra(nuevoPcompra);
					this.embalarDAO.actualizarPieza(pieza, "PiezaPHS");
					pendienteRechazo = new Pendiente();
					pendienteRechazo.setDocto(nuevoPcompra.toString());
					pendienteRechazo.setPartida(pieza.getIdPieza().toString());
					pendienteRechazo.setResponsable("PLozada");
					String tipoPendiente = "";
					if (partida.getTipo().equals("Estandares") || partida.getTipo().equals("Reactivos")
							|| partida.getTipo().equals("Biol��gicos")) {
						if (!pieza.getRevisoCatalogo() || !pieza.getRevisoDescripcion()
								|| !pieza.getRevisoFisicamenteC() || !pieza.getRevisoPresentacion()
								|| !pieza.getRevisoLote() || !pieza.getRevisoCaducidad()) {
							tipoPendiente = "Rechazo por inspeccion PHS-USA";
						} else if (!pieza.getRevisoDocumentacion()) {
							tipoPendiente = "Rechazo por documentacion PHS-USA";
						}
					}
					if (partida.getTipo().equals("Labware")) {
						if (!pieza.getRevisoCatalogo() || !pieza.getRevisoDescripcion()
								|| !pieza.getRevisoFisicamenteC() || !pieza.getRevisoPresentacion()) {
							tipoPendiente = "Rechazo por inspeccion PHS-USA";
						} else if (!pieza.getRevisoDocumentacion()) {
							tipoPendiente = "Rechazo por documentacion PHS-USA";
						}
					}
					if (partida.getTipo().equals("Publicaciones")) {
						if (!pieza.getRevisoCatalogo() || !pieza.getRevisoDescripcion() || !pieza.getRevisoEdicion()
								|| !pieza.getRevisoIdioma() || !pieza.getRevisoFisicamenteC()) {
							tipoPendiente = "Rechazo por inspeccion PHS-USA";
						} else if (!pieza.getRevisoDocumentacion()) {
							tipoPendiente = "Rechazo por documentacion PHS-USA";
						}
					}
					pendienteRechazo.setTipoPendiente(tipoPendiente);
					this.pendienteDAO.guardarPendiente(pendienteRechazo);
				}
			}
			// logger.info("Insertar InspeccionOC.");
			partida.setFechaFinInspeccion(new Date());
			partida.setIdPartidaCompra(nuevoPcompra);
			partida.setInspector(null);
			this.embalarDAO.insertarInspeccionOC(partida, "InspeccionOCPhs");
			Etiqueta fd = new Etiqueta();
			//Descomentar estas lineas 
//			fd.setBarcode("FD-" + partida.getPedido() + "-" + ppedidoSiguiente);
//			fd.setFolioArchivo(nuevoPcompra.toString());
//			fd.setEstatus("No Despachable");
			foliosFD.add(fd);
		}
		if (partida.getEstadoRechazo().equals("Despachable")) {
			// logger.info("Actualizar tabla InspeccionOC");
			estadofinal = "Recibido PHS";
			partida.setFechaFinInspeccion(new Date());
			partida.setInspector("InsPHS-USA");
			partida.setIdPartidaCompra(pcompraOriginal.getIdPartidaCompra());
			if (this.embalarDAO.validarPartidaInspeccionOC(pcompraOriginal.getIdPartidaCompra(), "InspeccionOCPhs")) {
				this.embalarDAO.actualizarInspeccionOC(partida, "InspeccionOCPhs");
			} else {
				this.embalarDAO.insertarInspeccionOC(partida, "InspeccionOCPhs");
			}
			// logger.info("Actualizar tabla EstadoPCompra");
			this.embalarDAO.actualizarEstadoPCompra(pcompraOriginal.getIdPartidaCompra());
		} else {
			estadofinal = "En inspecci��n Phs";
			for (Pieza pieza : piezasPCompra) {
				if (!pieza.getDespachable()) {
					pendienteRechazo = new Pendiente();
					pendienteRechazo.setDocto(pcompraOriginal.getIdPartidaCompra().toString());
					pendienteRechazo.setPartida(pieza.getIdPieza().toString());
					pendienteRechazo.setResponsable("PLozada");
					String tipoPendiente = "";
					if (partida.getTipo().equals("Estandares") || partida.getTipo().equals("Reactivos")
							|| partida.getTipo().equals("Biol��gicos")) {
						if (!pieza.getRevisoCatalogo() || !pieza.getRevisoDescripcion()
								|| !pieza.getRevisoFisicamenteC() || !pieza.getRevisoPresentacion()
								|| !pieza.getRevisoLote() || !pieza.getRevisoCaducidad()) {
							tipoPendiente = "Rechazo por inspeccion PHS-USA";
						} else if (!pieza.getRevisoDocumentacion()) {
							tipoPendiente = "Rechazo por documentacion PHS-USA";
						}
					}
					if (partida.getTipo().equals("Labware")) {
						if (!pieza.getRevisoCatalogo() || !pieza.getRevisoDescripcion()
								|| !pieza.getRevisoFisicamenteC() || !pieza.getRevisoPresentacion()) {
							tipoPendiente = "Rechazo por inspeccion PHS-USA";
						} else if (!pieza.getRevisoDocumentacion()) {
							tipoPendiente = "Rechazo por documentacion PHS-USA";
						}
					}
					if (partida.getTipo().equals("Publicaciones")) {
						if (!pieza.getRevisoCatalogo() || !pieza.getRevisoDescripcion() || !pieza.getRevisoEdicion()
								|| !pieza.getRevisoIdioma() || !pieza.getRevisoFisicamenteC()) {
							tipoPendiente = "Rechazo por inspeccion PHS-USA";
						} else if (!pieza.getRevisoDocumentacion()) {
							tipoPendiente = "Rechazo por documentacion PHS-USA";
						}
					}
					pendienteRechazo.setTipoPendiente(tipoPendiente);
					this.pendienteDAO.guardarPendiente(pendienteRechazo);
				}
			}
		}
		// logger.info("Actualizar tabla PCompra");
		pcompraOriginal.setCantidadCompra(partida.getCantidadCompra());
		pcompraOriginal.setEstado(estadofinal);
		pcompraOriginal.setUbicacion(partida.getUbicacion());
		if (partida.getAnaquelLetra() == null) {
			pcompraOriginal.setAnaquelLetra(null);
		} else {
			pcompraOriginal.setAnaquelLetra(partida.getAnaquelLetra());
		}
		if (partida.getAnaquelNumero().toString().equals("0")) {
			pcompraOriginal.setAnaquelNumero(null);
		} else {
			pcompraOriginal.setAnaquelNumero(partida.getAnaquelNumero());
		}
		pcompraOriginal.setAnaquelNumero(partida.getAnaquelNumero());
		pcompraOriginal.setFolioInspeccionPHS(partida.getFolioInspeccionPHS());
		this.partidaCompraDAO.actualizarPCompra(pcompraOriginal);
		// logger.info("Actualizar tabla PPedidos");
		ppedidoOriginal.setCantidadPedida(partida.getCantidadCompra());
		ppedidoOriginal.setFaltan(partida.getCantidadCompra());
		this.partidaPedidoDAO.actualizarPPedido(ppedidoOriginal);
//		fd = new Etiqueta();
//		fd.setBarcode("FD-" + partida.getPedido() + "-" + partida.getPartidaPedido());
//		fd.setFolioArchivo(partida.getIdPartidaCompra().toString());
//		fd.setEstatus(partida.getEstadoRechazo());
//		foliosFD.add(fd);
		if (partida.getEstadoRechazo().equals("Despachable")) {
			Long countPendiente = 0L;
			Pendiente nuevoPendiente = null;
			// logger.info("Investigar que pendiente se levantara......");
			if (partida.getPedidoFacturadoPor().equals("Pharma")) {
				countPendiente = this.pendienteDAO.obtenerNumeroPendientes(pcompraOriginal.getPedido(), null,
						"Evaluar env��o", true);
				if (countPendiente <= 0) {
					nuevoPendiente = new Pendiente();
					nuevoPendiente.setDocto(partida.getPedido());
					nuevoPendiente.setPartida("CI");
					nuevoPendiente.setResponsable("LRosas");
					nuevoPendiente.setTipoPendiente("Evaluar env��o");
					this.pendienteDAO.guardarPendiente(nuevoPendiente);
					// logger.info("El pendiente levantado fue: Evaluar
					// env������o");
				}
			} else {
				countPendiente = this.pendienteDAO.obtenerNumeroPendientes(pcompraOriginal.getCompra(), null,
						"Especificar tr��fico", true);
				if (countPendiente <= 0) {
					nuevoPendiente = new Pendiente();
					nuevoPendiente.setDocto(partida.getCompra());
					nuevoPendiente.setPartida(partida.getFolioInspeccionPHS());
					nuevoPendiente.setResponsable("LRosas");
					nuevoPendiente.setTipoPendiente("Especificar tr��fico");
					this.pendienteDAO.guardarPendiente(nuevoPendiente);
					// logger.info("El pendiente levantado fue: Especificar
					// tr������fico");
				}
			}
		}
		// logger.info("Finalizo la inspeccion....");
		return foliosFD;
	}

	// Partida de Inspeccion
	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public List<PartidaInspeccion> consultaPartidasEmbalar(String estadoPedido) throws ProquifaNetException {
		try {
			List<PartidaInspeccion> partidasEmbalar = new ArrayList<PartidaInspeccion>();
			partidasEmbalar = embalarDAO.obtenerPartidaEmbalar(estadoPedido);
			for (int i = 0; i < partidasEmbalar.size(); i++) {
				partidasEmbalar.get(i).setConcepto(productoDAO.obtenerDescripcionProducto(
						partidasEmbalar.get(i).getCodigo(), partidasEmbalar.get(i).getFabrica()));
			}
			return partidasEmbalar;
		} catch (Exception e) {
			// logger.error(e.getMessage());
			return null;
		}
	}

	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public Boolean cambiaEstadoPedido(ArrayList<Integer> idsPPedidos, String estadoPedido) throws ProquifaNetException {
		try {
			Boolean actualizoEstadoPedido = false;
			actualizoEstadoPedido = embalarDAO.cambiaEstadoPedidoaPorColectar(idsPPedidos, estadoPedido);
			return actualizoEstadoPedido;
		} catch (Exception e) {
			// logger.error(e.getMessage());
			return null;
		}
	}
	// public Boolean generaOrdenEntrega(ArrayList<Integer> idsPPedidos, String
	// estadoPedido) throws ProquifaNetException {
	//
	// int clave;
	//
	// try {
	// Boolean insertoPOrdenEntrega = false;
	// Boolean actualizoEstadoPedido = false;
	//
	// Folio folio = folioDAO.obtenerFolioPorConcepto("OrdenEntrega",true);
	//
	// clave = embalarDAO.generaOrdenEntrega(folio);
	//
	// if(clave > 0)
	//// insertoPOrdenEntrega = embalarDAO.generaPOrdenEntrega(clave,
	// idsPPedidos);
	//
	// if(insertoPOrdenEntrega == true)
	// actualizoEstadoPedido = embalarDAO.cambiaEstadoPedido(idsPPedidos,
	// estadoPedido);
	//
	// return actualizoEstadoPedido;
	//
	// }
	// catch (Exception e) {
	// logger.error(e.getMessage());
	// return null;
	// }
	// }

	// Contador de Partidas de Inspección
	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public Integer contadorSeccionEmbalar(String estadoPedido) throws ProquifaNetException {
		try {
			return embalarDAO.obtenerContadorPartidasAEmbalar(estadoPedido);
		} catch (Exception e) {
			// logger.error(e.getMessage());
			return null;
		}
	}

	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public String obtenerConsecutivoDeLoteInspeccion() throws ProquifaNetException {
		try {
			return folioDAO.obtenerFolioPorConcepto("Grabacion Lote Inspeccion", true).getFolioCompleto();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public etiquetasYfolios generarEtiquetaET(PartidaInspeccion part) throws ProquifaNetException {
		try {
			etiquetasYfolios listEylistF = new etiquetasYfolios();
			List<byte[]> lstEtiquetas = new ArrayList<byte[]>();
			List<String> lstFolios = new ArrayList<String>();
			Folio folio = folioDAO.obtenerFolioPorConcepto("Folio entrega", true);
			part.setCodBarras(folio.getFolioCompleto());
//			lstEtiquetas = generarCodigos(part);

			lstFolios.add(folio.getFolioCompleto());
			listEylistF.setEtiquetas(lstEtiquetas);
			listEylistF.setFolios(lstFolios);
			return listEylistF;
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return null;
	}

	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public boolean crearCodigo(String codigo) {
		try {
			Funcion funcion = new Funcion();
			BitMatrix matrix;
			matrix = new Code128Writer().encode(codigo, BarcodeFormat.CODE_128, 0, 47, null);

			BufferedImage image = new BufferedImage(matrix.getWidth(), matrix.getHeight(), BufferedImage.TYPE_INT_RGB);
			image.createGraphics();
			String rutaGuardar = funcion.obtenerRutaServidor("etiqueta", "");

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
			file = new File(rutaGuardar + codigo + ".png");
			ImageIO.write(image, "png", file);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	

//	@Transactional
//	private List<byte[]> generarCodigos(PartidaInspeccion part) throws ProquifaNetException {
//		try {
//			Funcion funcion = new Funcion();
//			byte[] imageA = null;
//			List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
//			List<byte[]> listaImagenes = new ArrayList<byte[]>();
//			String rutaPlantilla = funcion.obtenerRutaServidor("plantillaEtiquetas", "");
//			String rutaEtiqueta = funcion.obtenerRutaServidor("etiqueta", "");
//			InputStream inputStream = new FileInputStream(rutaPlantilla + "etiquetaET.jrxml");
//			JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
//			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
//			String rutaLogo = rutaPlantilla + "logo_proquifa_iso_chica.jpg";
//			part.setCodBarras(part.getCodBarras());
//			crearCodigo(part.getCodBarras());
//			Map<String, Object> parametros = new HashMap<String, Object>();
//			parametros.put("codigoCP", part.getCodBarras());
//			parametros.put("urlImagen", rutaEtiqueta + part.getCodBarras() + ".png");
//			parametros.put("cliente", part.getCliente());
//			parametros.put("almacen", part.getManejo());
//			parametros.put("ruta", part.getRuta().toUpperCase());
//			parametros.put("logo", rutaLogo);
//			// NOTA: ESTAS 3 LINEAS SIEMPRE TIENEN QUE IR
//			List<PartidaCotizacion> list = new ArrayList<PartidaCotizacion>();
//			list.add(new PartidaCotizacion());
//			JRDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(list, true);
//			File file = new File(rutaEtiqueta);
//			if (!file.exists())
//				file.mkdirs();
//			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, beanCollectionDataSource);
//			JasperExportManager.exportReportToPdfFile(jasperPrint, rutaEtiqueta + part.getCodBarras() + ".pdf");
//			PDDocument document = PDDocument.load(rutaEtiqueta + part.getCodBarras() + ".pdf");
//			List<PDPage> list1 = document.getDocumentCatalog().getAllPages();
//			if (list1.size() > 0) {
//				PDPage page = list1.get(0);
//				BufferedImage image = page.convertToImage();
//				ByteArrayOutputStream baos = new ByteArrayOutputStream();
//				ImageIO.write(image, "png", baos);
//				baos.flush();
//				byte[] imageInByte = baos.toByteArray();
//				baos.close();
//				imageA = imageInByte;
//				listaImagenes.add(imageA);
//			}
//			// esta linea se omite ya que no se quiere que se guarde el archivo
//			JasperExportManager.exportReportToPdfFile(jasperPrint, rutaEtiqueta + part.getCodBarras() + ".pdf");
//			jasperPrintList.add(jasperPrint);
//			// elimina la imagen del codigo de barras que fue generado
//			File etiqueta = new File(rutaEtiqueta + part.getCodBarras() + ".png");
//			etiqueta.delete();
//
//			log.info("Finalizo");
//			return listaImagenes;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}

	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public Boolean finalizarEmbalar(List<BolsaParaEmbalar> bolsas, String estadoPedido, Date fechaInicioEmbalaje,
			String usuario) throws ProquifaNetException {
		try {
			Integer idOrden_Entrega = 0;
			Long id_bolsa = 0L;
			Boolean resultado = false;
			Date fechaFin = new Date();
			Date tiempo_aux = new Date();
			Long tiempoEmbalaje = 0L;
			String zonaC = "";
			String rutaC = "";
			Integer idCliente = 0;
			Calendar dateInicio = Calendar.getInstance();
			dateInicio.setTime(fechaInicioEmbalaje);

			Calendar dateFin = Calendar.getInstance();
			dateFin.setTime(fechaFin);

			log.info("",dateInicio.get(Calendar.SECOND) * -1);
			dateFin.add(Calendar.SECOND, (dateInicio.get(Calendar.SECOND) * -1));
			dateFin.add(Calendar.MINUTE, (dateInicio.get(Calendar.MINUTE) * -1));
			dateFin.add(Calendar.HOUR_OF_DAY, (dateInicio.get(Calendar.HOUR_OF_DAY) * -1));

			tiempo_aux = dateFin.getTime();
			tiempoEmbalaje = calcularTiempo(tiempo_aux);

			Folio folio = folioDAO.obtenerFolioPorConcepto("OrdenEntrega", true);
			Folio folioPk = folioDAO.obtenerFolioPorConcepto("PackingList", true);
			log.info("Folio:" + folio.getFolioCompleto());
			log.info("Folio Packing:" + folioPk.getFolioCompleto());

			zonaC = bolsas.get(0).getArrayProductos().get(0).getZonaMensajeria();
			rutaC = bolsas.get(0).getArrayProductos().get(0).getRuta();
			idCliente = bolsas.get(0).getArrayProductos().get(0).getIdCliente();

			log.info("idCliente:" + idCliente);
			log.info("Ruta:" + rutaC);
			log.info("Zona:" + zonaC);

			idOrden_Entrega = embalarDAO.generaOrdenEntrega(folio, tiempoEmbalaje, usuario, zonaC, rutaC,
					folioPk.getFolioCompleto(), idCliente);
			log.info("id orden entrega:" + idOrden_Entrega);
			for (BolsaParaEmbalar bolsa : bolsas) {
				log.info("Entro a ciclo");
				log.info("Datos:" + bolsa.getSerial() + "..." + bolsa.getManejo() + "...." + idOrden_Entrega);
				id_bolsa = embalarDAO.insertarBolsa(bolsa.getSerial(), bolsa.getManejo(), idOrden_Entrega);
				log.info("idBolsa:" + id_bolsa);
				if (id_bolsa > 0) {
					log.info("Entra a actualizar");
					resultado = embalarDAO.generaPOrdenEntrega(idOrden_Entrega, bolsa.getArrayProductos(), id_bolsa);
					log.info("resultado generar POrdenEntrega:" + resultado);
					if (resultado)
						resultado = embalarDAO.cambiaEstadoPedido(bolsa.getArrayProductos(), estadoPedido);
				}
			}
			generarPkLins(idOrden_Entrega.longValue());
			log.info("Finalizo");
			return resultado;
		} catch (Exception e) {
			log.info("Fallo");
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return null;
		}
	}

	@SuppressWarnings("static-access")
	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	private Long calcularTiempo(Date tiempo) throws ProquifaNetException {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
			String fecha = simpleDateFormat.format(tiempo);
			Integer horas = 0;
			Integer min = 0;
			Integer seg = 0;
			Integer horasSeg = 0;
			Integer minSeg = 0;
			Integer totalSeg = 0;
			String[] time = fecha.split(":");
			horas = horas.valueOf(time[0]);
			min = min.valueOf(time[1]);
			seg = seg.valueOf(time[2]);

			if (horas > 0) {
				horasSeg = (horas * 60) * 60;
			}
			if (min > 0) {
				minSeg = min * 60;
			}
			totalSeg = horasSeg + minSeg + seg;
			return totalSeg.longValue();
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public totalesInspeccionProducto consultaDeTotalesPorRespoEmbalaje(String resEmbalaje) throws ProquifaNetException {
		try {
			totalesInspeccionProducto totales = new totalesInspeccionProducto();
			List<PartidaInspeccion> list = new ArrayList<PartidaInspeccion>();
			Long mes = 0L;
			Date fechaHoy = new Date();
			Integer numDias = 0;
			Date fechaInicioQuincena = new Date();
			Date fechaFinQuincena = new Date();
			Date fechaInicioMes = new Date();
			Date fechaFinMes = new Date();

			switch (fechaHoy.getMonth()) {
			case 0:
			case 2:
			case 4:
			case 6:
			case 7:
			case 9:
			case 11:
				numDias = 31;
				break;
			case 3:
			case 5:
			case 8:
			case 10:
				numDias = 30;
				break;
			case 1:
				if ((fechaHoy.getYear() % 4 == 0 && fechaHoy.getYear() % 100 != 0) || fechaHoy.getYear() % 400 == 0) {
					numDias = 29;
				} else {
					numDias = 28;
				}
				break;
			default:
				log.info("\nEl mes " + mes + " es incorrecto.");
				break;
			}

			Calendar diffInicioQ = Calendar.getInstance();

			if (fechaHoy.getDay() <= 15) {
				diffInicioQ.setTime(fechaInicioQuincena);
				diffInicioQ.set(Calendar.HOUR_OF_DAY, 00);
				diffInicioQ.set(Calendar.MINUTE, 00);
				diffInicioQ.set(Calendar.DATE, 1);
				fechaInicioQuincena = diffInicioQ.getTime();
				diffInicioQ.setTime(fechaFinQuincena);
				diffInicioQ.set(Calendar.HOUR_OF_DAY, 23);
				diffInicioQ.set(Calendar.MINUTE, 59);
				diffInicioQ.set(Calendar.DATE, 15);
				fechaFinQuincena = diffInicioQ.getTime();
			} else {
				diffInicioQ.setTime(fechaInicioQuincena);
				diffInicioQ.set(Calendar.HOUR_OF_DAY, 00);
				diffInicioQ.set(Calendar.MINUTE, 00);
				diffInicioQ.set(Calendar.DATE, 16);
				fechaInicioQuincena = diffInicioQ.getTime();
				diffInicioQ.setTime(fechaFinQuincena);
				diffInicioQ.set(Calendar.HOUR_OF_DAY, 23);
				diffInicioQ.set(Calendar.MINUTE, 59);
				diffInicioQ.set(Calendar.DATE, numDias);
				fechaFinQuincena = diffInicioQ.getTime();
			}
			diffInicioQ.setTime(fechaInicioMes);
			diffInicioQ.set(Calendar.HOUR_OF_DAY, 00);
			diffInicioQ.set(Calendar.MINUTE, 00);
			diffInicioQ.set(Calendar.DATE, 1);
			fechaInicioMes = diffInicioQ.getTime();
			diffInicioQ.setTime(fechaFinMes);
			diffInicioQ.set(Calendar.HOUR_OF_DAY, 23);
			diffInicioQ.set(Calendar.MINUTE, 59);
			diffInicioQ.set(Calendar.DATE, numDias);
			fechaFinMes = diffInicioQ.getTime();
			totales = embalarDAO.obtenerTotalesEmbalaje(resEmbalaje, fechaInicioMes, fechaFinMes);
			list.addAll(embalarDAO.probarobtenerTotalesPorfecha("ano", null, null, resEmbalaje));
			list.addAll(embalarDAO.probarobtenerTotalesPorfecha("mes", null, null, resEmbalaje));
			list.addAll(embalarDAO.probarobtenerTotalesPorfecha("quincena", fechaInicioMes, fechaFinMes, resEmbalaje));
			list.addAll(embalarDAO.probarobtenerTotalesPorfecha("dia", null, null, resEmbalaje));
			// for (int i = 0; i < list.size(); i++) {
			//
			// log.info(list.get(i).getPrioridad_p());
			//
			// }
			totales.setList_Inspeccion(list);
			PartidaInspeccion pi = new PartidaInspeccion();
			Long cantidadPOrdenE = 0L;
			// si se queda
			totales.setT_partidas(embalarDAO.obtenerTotalPartidasEmpaquetadas(resEmbalaje));
			// se queda
			pi = embalarDAO.obtenerIdOrdenCompraDePenultimoEmbalaje();
			cantidadPOrdenE = embalarDAO.obtenerTotalDePiezasPorOrdenEntrega(pi.getIdOrdenEntrega());
			Long horas = 0L;
			if (pi.getTiempoEmbalajeEnSegundos() != null) {
				if (pi.getTiempoEmbalajeEnSegundos() > 3600) {
					horas = pi.getTiempoEmbalajeEnSegundos() / 3600;
				}
			}
			Long prom = 0L;
			if (horas > 0)
				prom = cantidadPOrdenE / horas;
			if (prom == 0) {
				prom = 1L;
			}
			totales.setPromXpieza(1L);
			log.info("Termino de obtener totales");
			return totales;
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return null;

	}

	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	private Boolean generarPkLins(Long idOrdenEntrega) throws ProquifaNetException {
		try {
			List<PartidaInspeccion> partidas = embalarDAO.obtenerPartidasPoridOrdenEntrega(idOrdenEntrega);
			OrdenEntrega OEntrega = embalarDAO.obtenerOrdenEntregaXid(idOrdenEntrega);
			Cliente cliente = clienteDAO.obtenerClienteXId(OEntrega.getIdCliente());
			Pedido p = this.pedidoDAO.obtenerPedidoXCPedido(partidas.get(0).getCpedido());
			Funcion funcion = new Funcion();
			String rutaPlantilla = funcion.obtenerRutaServidor("plantillaRuta", "");
			String rutaEtiqueta = funcion.obtenerRutaServidor("ruta", "");
			Integer totalAmbiente = 0;
			Integer totalRefrigeracion = 0;
			Integer totalCongelacion = 0;
			Integer cantidadTotal = 0;
			for (PartidaInspeccion partidaInspeccion : partidas) {
				if (partidaInspeccion.getManejo().equalsIgnoreCase("ambiente")) {
					totalAmbiente = totalAmbiente + partidaInspeccion.getCant();
				} else if (partidaInspeccion.getManejo().equalsIgnoreCase("congelación")) {
					totalCongelacion = totalCongelacion + partidaInspeccion.getCant();
				}
				if (partidaInspeccion.getManejo().equalsIgnoreCase("refrigeración")) {
					totalRefrigeracion = totalRefrigeracion + partidaInspeccion.getCant();
				}
				cantidadTotal = cantidadTotal + partidaInspeccion.getCant();
			}
//			JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
//			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			String logo = rutaPlantilla + "logo_proquifa.png";
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("logo", logo);
			parametros.put("ruta", rutaPlantilla);
			parametros.put("listaPartidas", partidas);
			parametros.put("ambiente", totalAmbiente);
			parametros.put("congelacion", totalCongelacion);
			parametros.put("refrigeracion", totalRefrigeracion);
			parametros.put("cliente", cliente.getNombre());
			parametros.put("destino", OEntrega.getZona());
			parametros.put("entrega", p.getZonaMensajeria());
			parametros.put("totalPiezas", cantidadTotal);
			parametros.put("contacto", p.getContacto());
			parametros.put("packingList", OEntrega.getFolio_packingList());

			File file = new File(rutaEtiqueta);
			if (!file.exists())
				file.mkdirs();
//			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());
//			JasperExportManager.exportReportToPdfFile(jasperPrint,
//					rutaEtiqueta + OEntrega.getFolio_packingList() + ".pdf");
			log.info("Finalizo");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return false;

	}

	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public boolean validarUsuarioEmbalar(String usuario, String pass) throws ProquifaNetException {
		try {
		} catch (Exception e) {
		}
		return true;
	}

	@Override
	public List<PartidaInspeccion> consultaPartidasColectar() throws ProquifaNetException {
		try {
			List<PartidaInspeccion> partidasColectar = new ArrayList<PartidaInspeccion>();
			partidasColectar = embalarDAO.obtenerPartidaColectar();
			for (int i = 0; i < partidasColectar.size(); i++) {
				partidasColectar.get(i).setConcepto(productoDAO.obtenerDescripcionProducto(
						partidasColectar.get(i).getCodigo(), partidasColectar.get(i).getFabrica()));
			}
			return partidasColectar;
		} catch (Exception e) {
			// logger.error(e.getMessage());
			return null;
		}
	}
	
	
	@Override
	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public Map<String, List<TotalEmbalar>> obtenerTotalEmbalar() throws ProquifaNetException{
		try{
			Map<String, List<TotalEmbalar>> mapReturn = new HashMap<String, List<TotalEmbalar>>();
			mapReturn = embalarDAO.obtenerTotalEmbalar();
			return mapReturn;
		}catch (Exception e) {
			// logger.error(e.getMessage());
			return null;
		}
	}
	
	@Override
	public Map<String, List<EstadisticaUsuarioEmbalar>> obtenerEstadisticasUsuarioEmbalar(Empleado usuario) throws ProquifaNetException{
		try{
			Map<String, List<EstadisticaUsuarioEmbalar>> mapReturn = new HashMap<String, List<EstadisticaUsuarioEmbalar>>();
			mapReturn = embalarDAO.obtenerEstadisticaUsuario(usuario);
			return mapReturn;
		}catch (Exception e) {
			// logger.error(e.getMessage());
			return null;
		}
	}

	@Override
	public Map<String, List<colectarPartidas>> colectarPartidas(Parametro parametro) throws ProquifaNetException{
		try{
			Map<String, List<colectarPartidas>> mapReturn = new HashMap<String, List<colectarPartidas>>();
			mapReturn = embalarDAO.colectarPartidas(parametro);
//			Folio folio = folioDAO.obtenerFolioPorConcepto("PackingList", true);
//			PackingList  packingList = new  PackingList();
//			packingList.setFolio(folio.getFolioCompleto());
//			embalarDAO.savePackingList(packingList);
			return mapReturn;
		}catch (Exception e) {
			// logger.error(e.getMessage());
			return null;
		}
	}
	
	@Override
	@Transactional(readOnly=false, transactionManager = "ds1TransactionManager", rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
	public boolean registrarEmbalarPedido(Parametro parametro) throws ProquifaNetException{
		try{
			Folio folio = folioDAO.obtenerFolioPorConcepto("PackingList", true);
			parametro.setFolio(folio.getFolioCompleto());
			return embalarDAO.registrarEmbalarPedido(parametro);
		}catch(Exception e){
			e.printStackTrace();
			throw new ProquifaNetException(e);
		}
	}
	
	
	@Override
	@Transactional(readOnly=false, transactionManager = "ds1TransactionManager", rollbackFor = Exception.class)
	public boolean pedidosGDL(Parametro parametro) throws ProquifaNetException{
		try{
			cambiarEstado(parametro);
			if (!parametro.isGenerGuia()) {
				Folio folio = folioDAO.obtenerFolioPorConcepto("PackingList", true);
				parametro.setFolio(folio.getFolioCompleto());
				return embalarDAO.pedidosGDL(parametro);
			}
		}catch(Exception e){
			//e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public List <EmbalarPedido> ObtenerFolioPorUsuario(Integer idUsuario) throws ProquifaNetException {
		try {						
			List <EmbalarPedido> lstResult = embalarDAO.obtenerFolio(idUsuario);			
			return lstResult;
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly=false, transactionManager = "ds1TransactionManager", rollbackFor = Exception.class)
	public boolean cambiarEstado(Parametro parametro) throws ProquifaNetException {
		try {					
			 boolean modificar = embalarDAO.actualizarEstado(parametro);
			 
			 if (parametro.getEstado().equals("Por Colectar") || 
					 parametro.getEstado().equals("A Embalar") ||
					 parametro.getEstado().equals("Embalado")) {
				 
				 partidaPedidoDAO.updateEstadoxEmbalarPedido(parametro);
			 }
			 
			 return modificar;
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return false;
	}

	@Override
	public String consultarEstado(Integer idUsuario) throws ProquifaNetException {
		try {	
			String estado = embalarDAO.consultarEstado(idUsuario);
			if(estado == null || estado.equalsIgnoreCase("Embalado")) {
				estado = "Nuevo";
			}
			return estado;
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return "Nuevo";
	}
	
	@Override
	@Transactional(readOnly=false, transactionManager = "ds1TransactionManager", rollbackFor = Exception.class )
	public Object insertarPackingList(PackingList packingList, Map<String, String> idPedidosFactura) throws ProquifaNetException {
		try {
			
		Map<String, String> idPedidosFacturaPrepago = new HashMap<String, String>();
		String idsEmbalarPedido = "";
		for (PartidaPackingList partida : packingList.getPartidaPackingList()) {
			if (!partida.getEmbalar().isRemisionar())
				idsEmbalarPedido += partida.getEmbalar().getIdEmbalarPedido().toString() + ",";
				
		}
		idsEmbalarPedido += "0";
		
		idPedidosFacturaPrepago = embalarDAO.obtenerPartidasYaFacturadas(idsEmbalarPedido);
		this.generarPackingList(packingList, idPedidosFacturaPrepago, idPedidosFactura);
		
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return -1;
		}
		return null;
	}
	
	@Override
	public Map<String, String> generarFacturaElectronica(PackingList packingList) throws ProquifaNetException {
		try {
			List<FacturaElectronica> listaReturn = new ArrayList<FacturaElectronica>();
			Map<String, String> idPedidosFactura = new HashMap<String, String>();
			Long idFactura;
			Integer idFacturas = 0;
			Pendiente pendiente = new Pendiente();
			
			String idsEmbalarPedido = "";
			for (PartidaPackingList partida : packingList.getPartidaPackingList()) {
				if (!partida.getEmbalar().isRemisionar())
					idsEmbalarPedido += partida.getEmbalar().getIdEmbalarPedido().toString() + ",";
			}
			idsEmbalarPedido += "0";
			
//			listaReturn =  new ArrayList<FacturaElectronica>();
			listaReturn =  embalarDAO.generarFacturaEmbalar(idsEmbalarPedido);
			Integer idPpedido = packingList.getPartidaPackingList().get(0).getEmbalar().getIdPedido();
			String usuarioEmp = embalarDAO.obtenerDatosCliente(idPpedido);
			
			if(listaReturn.size() > 0) {
				String idFacturasContraEntrega = "";
				for (FacturaElectronica facturaElectronica : listaReturn) {
					idFacturas = (int) facturaElectronicaService.insertarFactura(facturaElectronica, false, false);
					log.info("",idFacturas);
					if(idFacturas > 0 ) {
						if (facturaElectronica.getCondicionesPago().equals("PAGO CONTRA ENTREGA"))
							idFacturasContraEntrega += idFacturas + ",";
						idPedidosFactura.put(idFacturas.toString(), facturaElectronica.getIdPPedidos());
						if(usuarioEmp != null) {
							idFactura = embalarDAO.obtenerIdFactura(idFacturas); 
							pendiente.setDocto(idFactura.toString());
							pendiente.setResponsable(usuarioEmp);
							pendiente.setTipoPendiente("SubirFacturaPortal"); 
							pendienteDAO.guardarPendiente_angular(pendiente);
						}
					} else {
						throw new ProquifaNetException();
					}
				}
				
				idFacturasContraEntrega += "0";
				embalarDAO.generarPendienteContraEntrega(idFacturasContraEntrega);
				
			}
			
			return idPedidosFactura;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
 
	@SuppressWarnings("unused")
	public Object generarPackingList(PackingList packingList, Map<String, String> idPedidosFacturaPrepago, Map<String, String> idPedidosFactura) throws ProquifaNetException {
		try {
			Integer idFacturas = 0;
			Integer idUsuario = 0;
			
			List <Integer> listaidPedidos = new ArrayList<Integer>();
			Parametro  parametro = new Parametro();
			embalarDAO.savePackingList(packingList);
			Integer idPackingList = embalarDAO.consultarIDPackingList();
			String idsEmbalarPedido = "";
			String idsPedidosRemisionar = "";
			boolean isRemision = false;
			if(idPackingList != 0) {
				for (PartidaPackingList partida : packingList.getPartidaPackingList()) {
					partida.setPartidaPackingList(idPackingList);
					listaidPedidos.add(partida.getEmbalar().getIdPedido());
					embalarDAO.savePartidaPackingList(partida);	
					embalarDAO.guardarUbicacion(partida.getEmbalar().getIdPedido());
					if (!partida.getEmbalar().isRemisionar())
						idsEmbalarPedido += partida.getEmbalar().getIdEmbalarPedido().toString() + ",";
					else {
						idsPedidosRemisionar += partida.getEmbalar().getIdEmbalarPedido().toString() + ",";
						isRemision = true;
					}
				}
				idsEmbalarPedido += "0";
				idsPedidosRemisionar += "0";
			}
			
			PartidaPackingList partida = null;
			Folio folio = null;
			if (packingList.getPartidaPackingList().size() > 0) {
				partida = packingList.getPartidaPackingList().get(0);
				parametro.setEstado(partida.getEmbalar().getEstado());
				idUsuario = partida.getEmbalar().getUsuario();
				parametro.setIdUsuarioLogueado(Long.parseLong(idUsuario.toString()));
			}
			
			parametro.setLista(listaidPedidos);
			if(embalarDAO.actualizarEstado(parametro)) {
				String idPPedido = embalarDAO.consultarPedidosEmbalar(idUsuario);
				if(idPPedido != null  && !idPPedido.equals("") ) {
					folio = folioDAO.obtenerFolioPorConcepto("PackingList", true);
					embalarDAO.actualizarAEmbalarPendiente(idPPedido, folio.getFolioCompleto(), idUsuario);				
				}
			}
			
			
			for (Map.Entry<String, String> entry : idPedidosFacturaPrepago.entrySet()) {
				if (entry.getKey().contains("-")) {
					try {
						idFacturas = Integer.parseInt(entry.getKey().split("-")[1]);
					} catch (Exception e) { 
					}
				} else {
					Integer idFactura = 0;
					if (entry.getKey().contains("|")) {
						try {
							idFactura = Integer.parseInt(entry.getKey().split("\\|")[1]);
						} catch (Exception e) {
						}
					}
					idFacturas = embalarDAO.saveFacturaElectronicaPrepago(entry.getValue());
					embalarDAO.saveFactura_FElectronicaPrepago(idFactura, idFacturas);

				}
				if (idFacturas > 0)
					idPedidosFactura.put(idFacturas.toString(), entry.getValue());
		    }		

			for (Map.Entry<String, String> entry : idPedidosFactura.entrySet()) {
				log.info(entry.getKey() + ":" + entry.getValue());
				embalarDAO.actualizarPackingList(Integer.parseInt(entry.getKey()), entry.getValue());
			}				
			

			List<Remision> lstRemision = embalarDAO.obtenerDatosRemision(idsPedidosRemisionar);
			for (Remision remision : lstRemision) {
				Folio folioRemision = folioDAO.obtenerFolioPorConcepto("Remision", true);
				remision.setFactura(Integer.parseInt(folioRemision.getValor()));
				Integer idRemision = embalarDAO.guardarRemision(remision);
				remision.setIdRemision(idRemision);
				int cont = 0;
				for (PRemision premision : remision.getPartidaRemisiones()) {
					premision.setPart(++cont);
					premision.setIdRemision(idRemision);
					premision.setFactura(remision.getFactura());
					embalarDAO.guardarPartidaRemision(premision);
				}
				
				embalarDAO.actualizarPackingListRemision(idRemision, remision.getIdPPedidos());
				pendienteDAO.guardarPendiente_angular(new Pendiente(remision.getFpor(), "Facturar remisión", remision.getFactura().toString(), new Date(), embalarDAO.obtenerCobradorPorCliente(remision.getIdPPedidos()), null));
				
				//PDF - -Remision
				Funcion funcion = new Funcion();
				String rutaJasper = funcion.obtenerRutaServidor("remision","JasperReports");
				String ruta = rutaJasper + "Remision.jrxml";
				String rutaPDF = funcion.obtenerRutaServidor("remision",remision.getFpor());
				File file = new File(rutaPDF);
				if (!file.exists()) {
					file.mkdirs();
				}
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy", new  Locale("es","ES"));
				
				rutaPDF += remision.getFactura().toString() + ".pdf";
				ConvertNumber convert = new ConvertNumber();
				DecimalFormat df2 = new DecimalFormat( "#,##0.00" );
				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("fecha", WordUtils.capitalize(dateFormat.format(remision.getFecha()).toLowerCase()));
				parametros.put("folio", remision.getFactura().toString());
				parametros.put("cliente", remision.getCliente());
				parametros.put("direccion", remision.getDireccion());
				parametros.put("rfc", remision.getRfc());
				parametros.put("cpago", remision.getCpago());
				parametros.put("pedido", remision.getPedido());
				parametros.put("partidas", remision.getPartidaRemisiones());
				parametros.put("importe", df2.format(Double.parseDouble(remision.getImporte().replace(",", ""))));
				parametros.put("iva", remision.getIva().toString());
				parametros.put("total", df2.format(Double.parseDouble(remision.getImporte().replace(",", ""))));
				remision.setTotal(df2.format((Double.parseDouble(remision.getImporte().replace(",", "")) * remision.getIva()) + Double.parseDouble(remision.getImporte().replace(",", ""))));
				remision.setLetraTotal(convert.convertir(remision.getTotal().replace(",", ""), true, remision.getMoneda()));
				parametros.put("letra", remision.getLetraTotal());
				parametros.put("logo", rutaJasper + "recursos/logo.svg");
				
//				FileInputStream jasperReportPackingListPDF = new FileInputStream(ruta);
//				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReportPackingListPDF, parametros, new JREmptyDataSource());
//				JasperExportManager.exportReportToPdfFile( jasperPrint, rutaPDF);
				
				InputStream inputStream = new FileInputStream(ruta);
		        JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
		        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
				
		        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());
				JasperExportManager.exportReportToPdfFile(jasperPrint, rutaPDF);
				
				
			}
			
			//PDF - PackingList

			Funcion funcion = new Funcion();
			String ruta = funcion.obtenerRutaServidor("jasperordendespacho","") + "PackingListv2.jrxml";
			String rutaPDF = funcion.obtenerRutaServidor("ordendespacho_packinglist","");
			File file = new File(rutaPDF);
			if (!file.exists()) {
				file.mkdirs();
			}
			
			rutaPDF += packingList.getFolio() + ".pdf";
			Map<String, Object> parametros = llenaParametros(embalarDAO.obtenerDatosJasperPackingList(packingList.getFolio(), false, false), isRemision, true);
			
			if(parametros.size() > 0 ) {

				String rutaSubreporte = funcion.obtenerRutaServidor("jasperordendespacho","");
				parametros.put("subReporte", funcion.obtenerRutaServidor("jasperordendespacho","") + "SubPackingList.jasper");
				
//				FileInputStream jasperReportPackingListPDF = new FileInputStream(ruta);
//				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReportPackingListPDF, parametros, new JREmptyDataSource());
//				JasperExportManager.exportReportToPdfFile( jasperPrint, rutaPDF);

				JasperDesign jasperDesign2 = JRXmlLoader.load(rutaSubreporte + "SubPackingList.jrxml");
				JasperReport jasperReport2 = JasperCompileManager.compileReport(jasperDesign2);
				JRSaver.saveObject(jasperReport2, rutaSubreporte + "SubPackingList" + ".jasper");


				InputStream inputStream = new FileInputStream(ruta);
				JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
				JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());
				JasperExportManager.exportReportToPdfFile(jasperPrint, rutaPDF);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
		return null;
	}
	
	
	private Map<String, Object> llenaParametros(List<PackingListJasper> partidas, boolean isRemision, boolean isPackingList) throws ProquifaNetException {
		Map<String, Object> jasperMap = new HashMap<String, Object>();
		PackingListJasper packing = partidas.get(0);
		Funcion funcion = new Funcion();
		String ruta = funcion.obtenerRutaServidor("jasperordendespacho","");
		jasperMap.put("logo", "");
		jasperMap.put("ruta", ruta);
		
		jasperMap.put("listaPartidas",packing.getEmpresa().getRazonSocial());
		jasperMap.put("listaPartidas", partidas);
		jasperMap.put("cliente", packing.getCliente());
			StringBuilder dirC = new StringBuilder("");
			if(packing.getCli().getDireccion().getCalle() != null) {
				dirC.append("CALLE ").append(packing.getCli().getDireccion().getCalle());
			}
			if(packing.getCli().getDireccion().getCodigoPostal() != null) {
				dirC.append(" C.P. ").append(packing.getCli().getDireccion().getCodigoPostal());
			}
			if(packing.getCli().getDireccion().getCiudad() != null) {
				dirC.append(" CIUDAD ").append(packing.getCli().getDireccion().getCiudad());
			}
			if(packing.getCli().getDireccion().getMunicipio() != null) {
				dirC.append(" MUNICIPIO ").append(packing.getCli().getDireccion().getMunicipio());
			}
			if(packing.getCli().getDireccion().getPais() != null) {
				dirC.append(" PAÍS ").append(packing.getCli().getDireccion().getPais());
			}
			jasperMap.put("entrega", dirC.toString());
		jasperMap.put("destino", packing.getDestino());
		jasperMap.put("contacto", packing.getContacto());
		jasperMap.put("totalPiezas", packing.getAmbiente() + packing.getRefrigeracion() + packing.getCongelacion());
		jasperMap.put("ambiente", packing.getAmbiente());
		jasperMap.put("refrigeracion", packing.getRefrigeracion());
		jasperMap.put("congelacion", packing.getCongelacion());
		jasperMap.put("packingList", packing.getFolio());
		jasperMap.put("isRemision", isRemision);
		jasperMap.put("rfc", packing.getEmpresa().getRfcEmpresa());
		jasperMap.put("nombreEmisor", packing.getEmpresa().getRazonSocial());
		StringBuilder dir = new StringBuilder();
		if(packing.getEmpresa().getCalle() != null) {
			dir.append("CALLE: ").append(packing.getEmpresa().getCalle());
		}
		if(packing.getEmpresa().getColonia() != null) {
			dir.append(" COLONIA: ").append(packing.getEmpresa().getColonia());
		}
		if(packing.getEmpresa().getDelegacion() !=  null) {
			dir.append(" ALCALDIA: ").append(packing.getEmpresa().getDelegacion());
		}
		if(packing.getEmpresa().getCp() != null) {
			dir.append(" C.P. ").append(packing.getEmpresa().getCp());
		}
		if(packing.getEmpresa().getCiudad() != null) {
			dir.append(" CIUDAD: ").append(packing.getEmpresa().getCiudad());
		}
		if(packing.getEmpresa().getPais() != null){
			dir.append(" PAÍS: ").append(packing.getEmpresa().getPais());
		}
		jasperMap.put("direccion", dir.toString());
		jasperMap.put("lugarExp", packing.getEmpresa().getCp());
		return jasperMap;
	}

	
	@Override
	@Transactional(readOnly=false, transactionManager = "ds1TransactionManager", rollbackFor = Exception.class)
	public PackingList insertarPPackingList(PackingList packingList) throws ProquifaNetException {
		try {
			for (PartidaPackingList partida : packingList.getPartidaPackingList()) {
				embalarDAO.savePartidaPackingList(partida);
			}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return null;
	}
	
	@Override
	public Map<String, List<DocumentoXLS>> generarDocumentos(Parametro parametro) throws ProquifaNetException{
		try{
			Map<String, List<DocumentoXLS>> mapReturn = new HashMap<String, List<DocumentoXLS>>();
			mapReturn = embalarDAO.generarDocumentos(parametro);
			return mapReturn;
		}catch (Exception e) {
			// logger.error(e.getMessage());
			return null;
		}
	}	
	
	
	@Override
	@Transactional(readOnly=false, transactionManager = "ds1TransactionManager", rollbackFor = Exception.class)
	public Long insertarPendiente(Parametro parametro) throws ProquifaNetException {
		try {
			
			if(parametro.getDestinoAlmacen().equalsIgnoreCase("Recoge en PROQUIFA")){
				Long idUsuario = funcionDAO.getidFuncionXNombre("Almacenista de Salidas");
				Pendiente pendiente = new Pendiente();				
				if(idUsuario != 0) {
					pendiente.setResponsable(funcionDAO.getEmpleadoXIdFuncion(idUsuario));
				}
				pendiente.setDocto(parametro.getFolio());
				pendiente.setTipoPendiente("Por Entregar");
				pendiente.setPartida(null);
				if(pendiente.getDocto() != null) {
					if(cambiarEstado(parametro)) {
						return pendienteDAO.guardarPendiente_angular(pendiente);
					}
				}
			}
			
			else if(parametro.getRuta().equalsIgnoreCase("local") ) {
				Long idUsuario = funcionDAO.getidFuncionXNombre("Gestor de ruta");
				Pendiente pendiente = new Pendiente();
				if(idUsuario != 0) {
					pendiente.setResponsable(funcionDAO.getEmpleadoXIdFuncion(idUsuario));
				}
				pendiente.setDocto(parametro.getFolio());
				pendiente.setTipoPendiente("Asignar Mensajero");
				pendiente.setPartida(null);
				if(pendiente.getDocto() != null) {
					if(cambiarEstado(parametro)) {
						return pendienteDAO.guardarPendiente_angular(pendiente);
					}
				}
			} else {
				Long idUsuario = funcionDAO.getidFuncionXNombre("Almacenista de Salidas");
				Pendiente pendiente = new Pendiente();				
				if(idUsuario != 0) {
					pendiente.setResponsable(funcionDAO.getEmpleadoXIdFuncion(idUsuario));
				}
				pendiente.setDocto(parametro.getFolio());
				pendiente.setTipoPendiente("Por Enviar");
				pendiente.setPartida(null);
				if(pendiente.getDocto() != null) {
					if(cambiarEstado(parametro)) {
						if (parametro.getEnvio() != null) {
							Cliente cliente = clienteDAO.obtenerClienteXId(parametro.getIdCliente());
							PackingList pack = embalarDAO.obtenerPackingListxFolio(parametro.getFolio());
							pendienteDAO.guardarPendiente_angular(new Pendiente(pack.getIdPackingList().toString(), "Cargar Guia Envio", parametro.getFolio(), new Date(), cliente.getVendedor(), null));
							parametro.setIdPackingList(pack.getIdPackingList());
							trEnvioDAO.guardarPesoPaquete(parametro);
						}
						else if (parametro.getIdPedido() != null) {
							PackingList pack = embalarDAO.obtenerPackingListxFolio(parametro.getFolio());
							parametro.setIdPackingList(pack.getIdPackingList());
							trEnvioDAO.guardarPackingList(parametro);
						}
						
						return pendienteDAO.guardarPendiente_angular(pendiente);
					}
				}
			}

			return null;
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return null;
	}
	
	@Override
	public Map<String, Object> pathFilePedidos (Integer idUsuario, String estado) throws ProquifaNetException{
		log.info("pathFilePedidos");
		Map<String, Object> pedidos = new HashMap<String, Object>();
		ArrayList<String> rutas = null;
		ArrayList<String> rutaPedidos = null;
		try{
			String  documento = embalarDAO.obtenerFilePedido(idUsuario, estado);
			
			if (documento != null) {
				rutas = obtenerArchivoMailBot(documento, "");
			}
			
			List<String> doctoR = embalarDAO.obtenerPedidoDoctoR(idUsuario, estado);
			if (doctoR != null) {
				rutaPedidos = new ArrayList<String>();
				for (String docto : doctoR) {
					Parametro parametro = new Parametro();
					parametro.setTabla("DoctosR");
					parametro.setFecha("Fecha");
					parametro.setCampo("Folio");
					parametro.setFolio(docto);
					
					String year = reportesDAO.obtenerDate(parametro);
					
					URL u = new URL(Funcion.IP_SERVER + "Doctos/" + year + docto + ".pdf");
				    HttpURLConnection huc =  (HttpURLConnection)  u.openConnection(); 
				    huc.setRequestMethod("HEAD"); 
				    huc.connect();
				    if (huc.getResponseCode() == HttpURLConnection.HTTP_OK) {
				    	rutaPedidos.add(u.toString());
				    }
				}
				
				
			} 
			
			if (rutaPedidos != null && rutaPedidos.size() > 0)
				pedidos.put("Pedido", rutaPedidos);
			
			if (rutas != null){
				pedidos.put("MailBot", rutas);
			}
			
		}catch (Exception e){
			e.printStackTrace();
		}
		return pedidos;
	}
	
	
	
	public ArrayList<String> obtenerArchivoMailBot(String documento, String subcarpeta) throws ProquifaNetException {
        try {
        	 ArrayList<String> lstBase64 = new ArrayList<String>();	
        	
			String arch[];
            arch = documento.split("\\|");
	    	
        	byte[] file;
        	String path = "";
        	
        	for (int i = 0; i< arch.length; i++){
        		String archivo ="/" + arch[i];
	          	  
        		String ruta = util.obtenerRutaCompletaDocumento(archivo, "mailbot", "");
             // doc.setArchivo(util.getBytesFromFile(new File(ruta)));
        		log.info("ruta: " + ruta);
//        		file = util.getBytesFromFile(new File(ruta));
//        		path = copiarArchivo(file, archivo, "pedidos_mailbot", subcarpeta);
//        		log.info("path: " + path);
          	  	lstBase64.add(ruta);
        	}
     
            return lstBase64;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
	public String copiarArchivo(byte[] bytes, String folioDocumento,
			String tipoArchivo, String subcarpeta) throws ProquifaNetException {
		String ruta= "";
		try {
	
	            
			ruta = util.obtenerRutaServidor(tipoArchivo, subcarpeta);
			//			String ruta2 = obtenerRutaServidorFTP(tipoArchivo, null);
			File file = new File(ruta);
			File targetFile = new File(ruta + folioDocumento);
			FileOutputStream fos;
			if (!file.exists()) {
				file.mkdirs();
				log.info("Carpeta creada");
			}
			fos = new FileOutputStream(targetFile);
			fos.write(bytes);
			fos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ruta + folioDocumento;
	}

	@Override
	public Map<String,Integer> numeroCopias(Integer idUusuario, String estado) throws ProquifaNetException {
		try {
			return embalarDAO.numeroCopias(idUusuario, estado);
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		return null;
	}
	
	
	@Override
	public TrPackingList obtenerDatosContacto() throws ProquifaNetException {
		try {
			return embalarDAO.obtenerDatosContacto();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	@Override
	@Transactional(readOnly=false, transactionManager = "ds1TransactionManager", rollbackFor = Exception.class)
	public boolean finalizarGDL(String noGuia, String mensajeria) throws ProquifaNetException {
		try {
			embalarDAO.finalizarGDL(noGuia);
			Pendiente pendiente = new Pendiente();				
			 pendiente.setResponsable(funcionDAO.getEmpleadoXIdFuncion(41L));
			 pendiente.setDocto(noGuia);
			 pendiente.setTipoPendiente("Receptor de Materiales");
			 pendiente.setPartida(mensajeria);
			 pendienteDAO.guardarPendiente_angular(pendiente);
//			ENVIAR CORREO
			List<String[]> dataLines = new ArrayList<>();
			dataLines.add(new String[] {"#", "PackingList", "Cliente", "CP", "Manejo", "Fecha Estimada de Entrega","Dirección de Entrega", "Horario"});
			dataLines.addAll(embalarDAO.obtenerDatosGDL(noGuia));
			log.info("Archivo csv de envios Guadalajara: '" + dataLines);
			sendMailMensajero(dataLines, noGuia);
			return true; 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public List<ValidarCFDI> obtenerCDF(String estado, Long idUsuario) throws ProquifaNetException{
		try {
			List<ValidarCFDI> list = embalarDAO.obtenerCFDI(estado, idUsuario);
			for (ValidarCFDI validarCFDI : list) {
				if (validarCFDI.getSello().equals("1")) {
					InputStream is = null;
					try {
						String url = "http://187.189.39.50:51725/SAP/Facturas/" + validarCFDI.getFPor() + "/" + validarCFDI.getFactura() + ".pdf";
						is = new URL(url).openStream();
						ContentHandler contenthandler = new BodyContentHandler();
						Metadata metadata = new Metadata();
						PDFParser pdfparser = new PDFParser();
						pdfparser.parse(is, contenthandler, metadata, new ParseContext());
						String cadena = contenthandler.toString();
						String[] total = cadena.split(" TOTAL");
						if (total.length > 0) {
							total = total[1].split("\n");
							if (total.length > 0) {
								total = total[18].toLowerCase().split("tasa");
								if (total.length > 0)
									validarCFDI.setTotal(total[0].replace(",", ""));
							}
						}
						String[] fe = cadena.split(" CADENA ORIGINAL");
						if (fe.length > 0) {
							validarCFDI.setSello(fe[0].substring(fe[0].length()-8, fe[0].length()));
						}
						String[] uuid = cadena.split("SAT:");
						if (uuid.length > 0) {
							uuid = uuid[3].split("\\|");
							if (uuid.length > 0)
								validarCFDI.setFolioF(uuid[3]);
						}
					}
					catch (Exception e) {
						e.printStackTrace();
					}
					finally {
						if (is != null) is.close();
					}
				}
			}
			return list;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void generarPackingList(List<String> lstPackingList) {
		try {
			Funcion funcion = new Funcion();
			String ruta = funcion.obtenerRutaServidor("jasperordendespacho","") + "PackingListv2.jasper";
			log.info("\n ruta: "+ ruta);
			String rutas = funcion.obtenerRutaServidor("ordendespacho_packinglist","");
			log.info("\n rutas: "+ rutas);
			File file = new File(rutas);
			if (!file.exists()) {
				file.mkdirs();
			}
			
			for (String string : lstPackingList) {
				String rutaPDF = rutas + string + ".pdf";
				Map<String, Object> parametros = llenaParametros(embalarDAO.obtenerDatosJasperPackingList(string, true, false), false, true);
				
				if(parametros.size() > 0 ) {
					parametros.put("subReporte", funcion.obtenerRutaServidor("jasperordendespacho","") + "SubPackingList.jasper");
					FileInputStream jasperReportPackingListPDF = new FileInputStream(ruta);
					JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReportPackingListPDF, parametros, new JREmptyDataSource());
					JasperExportManager.exportReportToPdfFile( jasperPrint, rutaPDF);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean generarRegistroEntregaControlados (String folioPL){		
		try {			
			
			Funcion funcion = new Funcion();
			String ruta = funcion.obtenerRutaServidor("jasperEntrega","") + "RegistroEntrega.jrxml";
			String rutaSub = funcion.obtenerRutaServidor("jasperEntrega","");
			String rutas = funcion.obtenerRutaServidor("entregasControlado","");
			Map<String, Object> parametros = llenaParametros(embalarDAO.obtenerDatosJasperPackingList(folioPL, true, true), false, false);
			if(parametros.size() > 0 ) {				
				String rutaPDF = rutas + folioPL.replace("PL", "RE") + ".pdf";
				parametros.put("subReporte", funcion.obtenerRutaServidor("jasperEntrega","") + "SubRegistroEntrega.jasper");
				JasperDesign jasperDesign2 = JRXmlLoader.load(rutaSub + "SubRegistroEntrega.jrxml");
				JasperReport jasperReport2 = JasperCompileManager.compileReport(jasperDesign2);
				JRSaver.saveObject(jasperReport2, rutaSub + "SubRegistroEntrega" + ".jasper");
				
				InputStream inputStream = new FileInputStream(ruta);
				JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
				JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());
				JasperExportManager.exportReportToPdfFile(jasperPrint, rutaPDF);
				return true;
			}
		} catch (Exception e) {			
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public boolean actualizarFactura(String idFactura) {
		try {
			embalarDAO.actualizarFactura(idFactura);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return true;
	}
	
	@Override
	public boolean enviarCorreo (Correo datosC) {
		try {			
			Funcion funcion = new Funcion();
			Correo correo = new Correo();
			
            correo.setOrigen("ventas");                
            correo.setAsunto(datosC.getAsunto());                
            correo.setCuerpoCorreo(datosC.getCuerpoCorreo());                
            correo.setCorreo(datosC.getCorreo());
            correo.setCocorreo(datosC.getCocorreo());
            correo.setCcorreo(datosC.getCcorreo());
            correo.setConFormato(0);
            correo.setTipo("EvidenciaCorreoFactura");
            correo.setFacturadaPor(datosC.getFacturadaPor());
            correo.setArchivoAdjunto(datosC.getArchivoAdjunto());
            
            funcion.enviarCorreo(correo);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}
	
	@Override
	public void generarPDFRemision(String idsPedidosRemisionar, List<Integer> facturas, List<Integer> idRemisiones) {
		try {
			int i = 0;
			List<Remision> lstRemision = embalarDAO.obtenerDatosRemision(idsPedidosRemisionar);
			for (Remision remision : lstRemision) {
				remision.setFactura(facturas.get(i));
				Integer idRemision = idRemisiones.get(i++);
				remision.setIdRemision(idRemision);
				int cont = 0;
				for (PRemision premision : remision.getPartidaRemisiones()) {
					premision.setPart(++cont);
					premision.setIdRemision(idRemision);
					premision.setFactura(remision.getFactura());
				}
				
				//PDF - -Remision
				Funcion funcion = new Funcion();
				String rutaJasper = funcion.obtenerRutaServidor("remision","JasperReports");
				String ruta = rutaJasper + "Remision.jasper";
				String rutaPDF = funcion.obtenerRutaServidor("remision",remision.getFpor());
				File file = new File(rutaPDF);
				if (!file.exists()) {
					file.mkdirs();
				}
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy", new  Locale("es","ES"));
				
				rutaPDF += remision.getFactura().toString() + ".pdf";
				ConvertNumber convert = new ConvertNumber();
				DecimalFormat df2 = new DecimalFormat( "#,##0.00" );
				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("fecha", WordUtils.capitalize(dateFormat.format(remision.getFecha()).toLowerCase()));
				parametros.put("folio", remision.getFactura().toString());
				parametros.put("cliente", remision.getCliente());
				parametros.put("direccion", remision.getDireccion());
				parametros.put("rfc", remision.getRfc());
				parametros.put("cpago", remision.getCpago());
				parametros.put("pedido", remision.getPedido());
				parametros.put("partidas", remision.getPartidaRemisiones());
				parametros.put("importe", df2.format(Double.parseDouble(remision.getImporte().replace(",", ""))));
				parametros.put("iva", remision.getIva().toString());
				parametros.put("total", df2.format(Double.parseDouble(remision.getImporte().replace(",", ""))));
				remision.setTotal(df2.format((Double.parseDouble(remision.getImporte().replace(",", "")) * remision.getIva()) + Double.parseDouble(remision.getImporte().replace(",", ""))));
				remision.setLetraTotal(convert.convertir(remision.getTotal().replace(",", ""), true, remision.getMoneda()));
				parametros.put("letra", remision.getLetraTotal());
				parametros.put("logo", rutaJasper + "recursos/logo.svg");
				
				
				FileInputStream jasperReportPackingListPDF = new FileInputStream(ruta);
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReportPackingListPDF, parametros, new JREmptyDataSource());
				JasperExportManager.exportReportToPdfFile( jasperPrint, rutaPDF);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void sendMailMensajero(List<String[]> dataLines, String numGuia) throws ProquifaNetException {
		try {
			log.info("Inicio envio de correo: 'Envios Guadalajara'" + numGuia);
			Funcion funcion = new Funcion();
			String nameFile =  "Envios_Guadalajara_NoGuia_"+ numGuia + ".csv";
			File csvOutputFile = new File("/tmp/" + nameFile);
		    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
		        dataLines.stream().map(this:: convertToCSV).forEach(pw::println);
		    }
		    Correo correo = new Correo();
		    correo.setOrigen("ventas");
		    correo.setCuerpoCorreo("");
		    correo.setAsunto("Envios Guadalajara");		    
		    //correo.setCorreo("etrevino@proquifa.net; magomez@proquifa.net; ejtrinidad@proquifa.net; rbernal@proquifa.net");
		    correo.setCorreo("etrevino@proquifa.net; magomez@proquifa.net; jlolivares@proquifa.net; gvaladez@proquifa.net; barias@proquifa.net");
		    correo.setArchivoAdjunto(nameFile);
		    correo.setTipo("csvasignarmensajero");
		    funcion.enviarCorreo(correo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String convertToCSV(String[] data) {
	    return Stream.of(data)
	      .map(this::escapeSpecialCharacters)
	      .collect(Collectors.joining(","));
	}
	
	private String escapeSpecialCharacters(String data) {
		 String escapedData = data;
		if(data != null) {
		    if (data.contains(",") || data.contains("\"") || data.contains("'") || data.contains("\n")) {
		        data = data.replace("\"", "\"\"");
		        escapedData = "\"" + data + "\"";
		    }
		} else {
			escapedData = "";
		}
	    return escapedData;
	}
	@Override
	public  Map<String, Integer> obtenerTot() {
		try {
			return embalarDAO.totalesHoy();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	@Override	
	public boolean esPLdeControlado(String folioPackingList) throws ProquifaNetException{		
		return embalarDAO.esPLdeControlado(folioPackingList);
	}
	
	@Override
	public boolean esPLRecogeEnPROQUIFA(String folioPackingList) throws ProquifaNetException{
		return embalarDAO.isPLRecogeEnPROQUIFA(folioPackingList);
	}
	
	@Override
	public void enviarCorreoRecogeEnProquifa (String folioPL) throws ProquifaNetException{
		Correo result  = embalarDAO.obtenerDatosCorreoRecogeEnProquifa(folioPL);
		if( result != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", new  Locale("es","ES"));
			String fecha =  dateFormat.format(result.getFechaInicio());
			Correo correo = new Correo();
			BodyFileEmail enviarEmail = new BodyFileEmail();			
			correo.setCorreo(result.getCorreo());			
			correo.setOrigen(fecha);
			correo.setAsunto(result.getCuerpoCorreo());
			Long idPedido = result.getIdContacto();      
			
			Boolean EnvioC = embalarDAO.actualizarEnvioCorreo_RecogeEnProquifa(idPedido);

			if(EnvioC) {
				System.out.println("actualizarEnvioCorreo_RecogeEnProquifa TRUE " + correo.getCorreo());
				enviarEmail.bodyPdfInspeccion(correo);
			}
		} 
	}

	@Override
	public boolean cargarArchivoClientesCFDI(MultipartFile csv) throws ProquifaNetException, IOException {
	String csvFilePath = "C:\\Users\\Fernando Betanzos\\Downloads\\"+csv.getOriginalFilename();
		//String csvFilePath=	Funcion.RUTA_DOCUMENTOS+"/CFDI_4.0/"+csv.getOriginalFilename();
		FileReader fileReader = new FileReader(csvFilePath);
		CSVParser csvParser = new CSVParser(fileReader, CSVFormat.EXCEL);
		Boolean handleCSV=false;
		try {
			for (CSVRecord csvRecord : csvParser) {
				/*
				handleCSV=true;
				// Procesar cada fila del archivo CSV
				String columna1 = csvRecord.get(0);
				String rfc = csvRecord.get(1);
				String regimenSocietario = csvRecord.get(9) ;
				String noRegimen= csvRecord.get(10);
				String RSocial= csvRecord.get(6) + " "+ regimenSocietario;
				System.out.println("RFC:"+rfc);
					System.out.println("RegimenSocietario:"+regimenSocietario);
					System.out.println("NoRegimen:"+noRegimen);
					System.out.println("RSocial:"+RSocial);
				// ...
				if (embalarDAO.actualizaInfoCFDI(regimenSocietario,noRegimen,rfc,RSocial)){
					System.out.println("Datos CFDI 4.0 cargados correctamente");
				} else{
					System.out.println("Ocurrio un error en la carga de Info. CFDI4.0");
				}*/

				String c_Municipio=csvRecord.get(0);
				String c_Estado=csvRecord.get(1);
				String Desc=csvRecord.get(2);
				if (c_Estado.equals("MOR")||c_Estado.equals("JAL")||Desc.equals("Toluca")){
						System.out.println(c_Estado+", "+c_Municipio+", "+Desc);
				}
				/*if(embalarDAO.actualizaEdoPpedidos(cpedido,codigo)&&embalarDAO.actualizaEdoPcompras(cpedido,codigo)){
					System.out.println(cpedido+","+codigo);
					System.out.println("Actualizado exitoso");

				}else{
					System.out.println("ocurrio un error");
				}*/
			}

			csvParser.close();
			fileReader.close();

		}catch (Exception e) {
		handleCSV=false;
		}


		return handleCSV;
	}

	@Override
	public boolean pedidoConProdsDeStock(Integer usuarioEmbalar) throws ProquifaNetException {
		return embalarDAO.pedidoConProdsDeStock(usuarioEmbalar);
	}


	public boolean mandarAStock(List<Integer> IdPPedido) throws ProquifaNetException {
		System.out.println("entra al metodo");
			//embalarDAO.mandarAStock(Stock);
		for (Integer Id:IdPPedido ) {
			System.out.println("Resultado: " + embalarDAO.mandarAStock(Id));
		}
		return false;
	}
}
