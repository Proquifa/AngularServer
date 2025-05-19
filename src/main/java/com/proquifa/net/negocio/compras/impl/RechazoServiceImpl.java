/**
 * 
 */
package com.proquifa.net.negocio.compras.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proquifa.net.modelo.compras.rechazos.Rechazos;
import com.proquifa.net.modelo.comun.Contacto;
import com.proquifa.net.modelo.comun.Folio;
import com.proquifa.net.modelo.comun.Pendiente;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.ventas.admoncomunicacion.Correo;
import com.proquifa.net.negocio.compras.RechazoService;
import com.proquifa.net.persistencia.compras.RechazosDAO;
import com.proquifa.net.persistencia.comun.EmpleadoDAO;
import com.proquifa.net.persistencia.comun.FolioDAO;
import com.proquifa.net.persistencia.comun.PendienteDAO;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 * @author ymendez
 *
 */
@Service("rechazoService")
public class RechazoServiceImpl implements RechazoService {

	@Autowired
	RechazosDAO rechazosDAO;
	
	@Autowired
	PendienteDAO pendienteDAO;
	
	@Autowired
	EmpleadoDAO empleadoDAO;
	
	@Autowired
	FolioDAO folioDAO;
	
	final Logger log = LoggerFactory.getLogger(RechazoServiceImpl.class);
	
	@Override
	public List<Rechazos> obtenerDocumentacionFaltante(String responsable) throws ProquifaNetException {
		try {
			return rechazosDAO.obtenerDocumentacionFaltante(responsable);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Map<String, Object> obtenerGraficaDocumentacionFaltante(String responsable) throws ProquifaNetException {
		try {
			return rechazosDAO.obtenerGraficaDocumentacionFaltante(responsable);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new HashMap<String, Object>();
	}
	
	@Override
	public List<Contacto> obtenerProveedoresPorProveedor(Integer idProveedor) throws ProquifaNetException {
		try {
			return rechazosDAO.obtenerProveedoresPorProveedor(idProveedor);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Rechazos> documentoFaltantePorProveedor(Integer idProveedor, String responsable)
			throws ProquifaNetException {
		try {
			return rechazosDAO.documentoFaltantePorProveedor(idProveedor, responsable);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public boolean actualizarEstadoAInspeccion(String codigo, String lote, String hoja) throws ProquifaNetException {
		try {
			return rechazosDAO.actualizarEstadoAInspeccion(codigo, lote, hoja);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Rechazos> obtenerPiezasRechazadas(Rechazos datos) throws ProquifaNetException {
		try {
			return rechazosDAO.obtenerPiezasRechazadas(datos);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Override
	public Map<String, Object> obtenerGraficaRechazoInspeccion(Rechazos datos) throws ProquifaNetException {
		try {
			return rechazosDAO.obtenerGraficaRechazoInspeccion(datos);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	@Override
	public List<Rechazos> obtenerPiezasRechazadasPorInspeccion(Integer idProveedor, String tipo) throws ProquifaNetException {
		try {
			return rechazosDAO.obtenerPiezasRechazadasPorInspeccion(idProveedor, tipo);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public boolean finalizarCuarentena(Map<String, Object> data) throws ProquifaNetException {
		try {
			Integer idPieza = Integer.parseInt(data.get("idPieza").toString());
			String instrucciones = data.get("instrucciones").toString();
			String idPCompra = data.get("idPCompra").toString();
			String idPPedido = data.get("idPPedido").toString();
			String accion = data.get("accion").toString();
			Long idProveedor = Long.parseLong(data.get("idProveedor").toString());
			
			rechazosDAO.updatePieza(idPieza, instrucciones);
			pendienteDAO.cerrarPendiente_angular(idPCompra, idPieza.toString(), "Rechazo Por Inspeccion");
			
			if (accion.equals("Reclamar")) {
				String responsable = empleadoDAO.getCompradorporProveedor(idProveedor); 
				pendienteDAO.guardarPendiente_angular(new Pendiente(idPieza.toString(), "Producto a reclamo", idPCompra, new Date(), responsable, null));
			} else {
				if (pendienteDAO.obtenerNumeroPendientes(idPCompra, null, "Rechazo Por Documentacion", true) <= 0 ) {
					rechazosDAO.updateInspeccion(idPCompra, idPPedido);
				}
			}
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public boolean finalizarReclamo(Map<String, Object> data) throws ProquifaNetException {
		try {
			Folio folio = folioDAO.obtenerFolioPorConcepto("Reclamo", true);
			Integer idPieza = Integer.parseInt(data.get("idPieza").toString());
			String idPCompra = data.get("idPCompra").toString();
			pendienteDAO.cerrarPendiente_angular(idPCompra, idPieza.toString(), "Producto a reclamo");
			Long idProveedor = Long.parseLong(data.get("idProveedor").toString());
			String responsable = empleadoDAO.getCompradorporProveedor(idProveedor);
			pendienteDAO.guardarPendiente_angular(new Pendiente(idPieza.toString(), "Reclamo abierto", idPCompra, new Date(), responsable, null));
////			Insertar en Reclamo
			rechazosDAO.insertarReclamo(data, folio.getFolioCompleto());
			this.generarReclamo(idPCompra, idPieza.toString(), data);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public void generarReclamo(String idPCompra, String idPieza, Map<String, Object> data)  {
		try {
			String []imagenes = new String[3];
			Funcion funcion = new Funcion();
			String rutaImg = funcion.obtenerRutaServidor("imagenRechazo", "");
			String rutaGeneral = funcion.obtenerRutaServidor("jasperProductoReclamo","");
			String ruta = rutaGeneral + "ProductoReclamo.jasper";
			String rutaPDF = funcion.obtenerRutaServidor("producto_reclamo","");
			File file = new File(rutaPDF);
			if (!file.exists()) {
				file.mkdirs();
			}
			Rechazos dataJasper = rechazosDAO.obtenerDatosJasper(idPCompra, idPieza);
			if(dataJasper.getImagenRechazo() != null && dataJasper.getImagenRechazo() != "") {
				imagenes = dataJasper.getImagenRechazo().split("\\|");
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("rutaLogo", rutaGeneral + "logo_proquifa.png");
			param.put("rutaCertificado", rutaGeneral + "iso.png");
			param.put("rutaAmerican", rutaGeneral + "amex.png");
			param.put("rutaNecc", rutaGeneral + "neec.png");
			param.put("date", dataJasper.getFeeFormato());
			param.put("destinatario", dataJasper.getDestino());
			param.put("remitente", dataJasper.getProveedor());
			param.put("producto", dataJasper.getPedido());
			param.put("idReclamo", dataJasper.getCodigo());
			param.put("notes", dataJasper.getCausa());
			param.put("rutaFront", rutaImg + imagenes[0] + ".png");
			param.put("rutaAbove", rutaImg + imagenes[1] + ".png");
			param.put("rutaBelow", rutaImg + imagenes[2] + ".png");
			param.put("compra", dataJasper.getCompra());
			
//			Generación de PDF
			rutaPDF += dataJasper.getCodigo() + ".pdf";
			FileInputStream jasperProductoReclamoPDF = new FileInputStream(ruta);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperProductoReclamoPDF, param, new JREmptyDataSource());
			JasperExportManager.exportReportToPdfFile( jasperPrint, rutaPDF);
			
			if(data.get("MEnvio").toString().equalsIgnoreCase("Mail/Fax")) {
				@SuppressWarnings("unchecked")
				Map<String, Object> datosCorreo =  (Map<String, Object>) data.get("datosCorreo");
				datosCorreo.put("folio", dataJasper.getCodigo() );
				this.enviarCorreo(datosCorreo);
				log.info("",datosCorreo);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public boolean enviarCorreo(Map<String, Object> datos) throws ProquifaNetException{
		try {
			Funcion funcion = new Funcion();
			Correo correo = new Correo();
			
            correo.setOrigen("ventas");                
            correo.setAsunto("Evidencia de Reclamo");                
            correo.setCuerpoCorreo(datos.get("cuerpoCorreo").toString());                
//            correo.setCorreo("sara.sanchez@ryndem.mx");
            correo.setCorreo(datos.get("correo").toString());
            correo.setCocorreo(datos.get("cocorreo").toString());
            correo.setCcorreo(datos.get("ccorreo").toString());
            correo.setConFormato(0);
            correo.setTipo("producto_reclamo");
            correo.setArchivoAdjunto(datos.get("folio").toString());
            
            funcion.enviarCorreo(correo);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public Map<String, Object> obtenerTotalesSeccion(String idUsuario) throws ProquifaNetException{
		try {
			return rechazosDAO.obtenerTotales(idUsuario);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
}
