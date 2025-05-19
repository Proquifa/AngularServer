/**
 * 
 */
package com.proquifa.net.negocio.ventas.visitas.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.comun.Folio;
import com.proquifa.net.modelo.comun.NivelIngreso;
import com.proquifa.net.modelo.comun.Pendiente;
import com.proquifa.net.modelo.comun.Referencia;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.ventas.visitas.DocumentoSolicitudVisita;
import com.proquifa.net.modelo.ventas.visitas.SolicitudVisita;
import com.proquifa.net.negocio.ventas.visitas.SolicitudVisitaService;
import com.proquifa.net.persistencia.comun.EmpleadoDAO;
import com.proquifa.net.persistencia.comun.FolioDAO;
import com.proquifa.net.persistencia.comun.FuncionDAO;
import com.proquifa.net.persistencia.comun.NivelIngresoDAO;
import com.proquifa.net.persistencia.comun.PendienteDAO;
import com.proquifa.net.persistencia.comun.ReferenciaDAO;
import com.proquifa.net.persistencia.ventas.visitas.SolicitudVisitaDAO;

/**
 * @author ymendez 
 *
 */
@Service("solicitudVisitaService")
public class SolicitudVisitaServiceImpl implements SolicitudVisitaService{

	final Logger log = LoggerFactory.getLogger(SolicitudVisitaServiceImpl.class);
	
	@Autowired
	FolioDAO folioDAO;	
	@Autowired
	SolicitudVisitaDAO solicitudVisitaDAO;
	@Autowired
	PendienteDAO pendienteDAO;
	@Autowired
	FuncionDAO funcionDAO;
	@Autowired
	EmpleadoDAO empleadoDAO;
	@Autowired
	ReferenciaDAO referenciaDAO;	
	@Autowired
	NivelIngresoDAO nivelIngresoDAO;
//	private final static Logger logger = Logger.getLogger(SolicitudVisitaServiceImpl.class);
	
	/*
	 * (non-Javadoc)
	 * @see mx.com.proquifa.proquifanet.negocio.ventas.visitas.SolicitudVisitaService#insertarSolicitudVisita(mx.com.proquifa.proquifanet.modelo.ventas.visitas.SolicitudVisita)
	 */
	
	@Transactional
	public Long insertarSolicitudVisita(SolicitudVisita solicitudVisita)
	throws ProquifaNetException {
		Funcion funcion = new Funcion();
		String concepto = "Solicitud de visita";

		Long res=0L;
		try{
			folioDAO.actualizarValorConsecutivo(concepto, folioDAO.obtenerConsecutivoFolio(concepto));
			Folio folio = folioDAO.obtenerFolioPorConcepto(concepto,false);
			//logger.info("Folio completo: "+folio.getFolioCompleto());
			solicitudVisita.setFolio(folio.getFolioCompleto());
			solicitudVisita.setIdSolicitudVisita(solicitudVisitaDAO.insertarSolicitudVisita(solicitudVisita, folio));

			Pendiente pendiente = setPendiente(solicitudVisita);
			pendienteDAO.guardarPendiente(pendiente);

			concepto = "Referencia solicitud visita";
			for (Referencia ref : solicitudVisita.getReferencias()) {
				//logger.info("Entro al for para las referencias!!");
				ref.setOrigen("SolicitudVisita");
				ref.setIdIncidente(null);
				Folio folioReferencia = new Folio();
				
				folioDAO.actualizarValorConsecutivo(concepto, folioDAO.obtenerConsecutivoFolio(concepto));
				folioReferencia = folioDAO.obtenerFolioPorConcepto(concepto,false);
				
				ref.setFolio(folioReferencia.getFolioCompleto());
				ref.setIdSolicitudVisita(solicitudVisita.getIdSolicitudVisita());					
				res = this.referenciaDAO.insertarReferencia(ref);
				
				if(res == -1L){
					throw new ProquifaNetException("Error al insertar la referencia...");
				}else{
					//logger.info("Referencia insertada...ok.. con folio: "+ res);
					funcion.copiarArchivo(ref.getBytes(), res + "." + ref.getExtensionArchivo(), "SolicitudVisita");
					//logger.info("Archivo copiado..." + " Nombre: " + res + "." + ref.getExtensionArchivo() + " Tama������oo: " + ref.getBytes().length);
					//throw new ProquifaNetException("Error al insertar la referencia...");
				}
			}
			return solicitudVisita.getIdSolicitudVisita();
			
		}catch(ProquifaNetException e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			//logger.info(e.getMessage());
			return 0L;
		}
	}

	private Pendiente setPendiente(SolicitudVisita solicitudVisita){
		Pendiente pendiente = new Pendiente();
		pendiente.setDocto(solicitudVisita.getIdSolicitudVisita().toString());
		pendiente.setPartida(solicitudVisita.getFolio());
		pendiente.setFechaInicio(new Date());
		pendiente.setTipoPendiente("GestionarVisita");
		
		//logger.info("Ejecutivo de ventas: "+ solicitudVisita.getCliente().getIdEjecutivoVenta());
		//String responsable = funcionDAO.getEmpleadoXIdFuncionHabilitado(funcionDAO.getidFuncionXNombre("Gerente de Ventas"));
		Empleado responsable = empleadoDAO.obtenerEmpleadoPorId(Long.valueOf(solicitudVisita.getCliente().getIdEjecutivoVenta()));
		pendiente.setResponsable(responsable.getUsuario());
		return pendiente;
	}
	
	public Boolean descartarSolicitudVisita(Long idSolicitudVisita,
			String folio, String justificacion) throws ProquifaNetException {
		String concepto = "GestionarVisita";
		solicitudVisitaDAO.descartarSolicitudVisita(idSolicitudVisita, justificacion);
		return pendienteDAO.cerrarPendiente(idSolicitudVisita.toString(), folio, concepto);
	}
	
	public String buscarFolioxId(Long idSolicitudVisita){
		return solicitudVisitaDAO.buscarFolioxId(idSolicitudVisita);
	}
	
	public Boolean actualiarSolicitudVisita(SolicitudVisita solicitudVisita)
			throws ProquifaNetException {
		return solicitudVisitaDAO.actualiarSolicitudVisita(solicitudVisita);
	}
	
	public List<SolicitudVisita> consultarSolicitudesVisita(Long idEjecutivoVentas)throws ProquifaNetException {
		String parametros="";
		List<NivelIngreso> niveles = nivelIngresoDAO.findLimitesNivelIngreso();
		if(idEjecutivoVentas>0){
			parametros=" WHERE Clientes.FK01_EV="+idEjecutivoVentas;
		}
		return this.solicitudVisitaDAO.findSolicitudesVisita(parametros, niveles);
	}

	public List<Referencia> consultarReferenciaSolicitud(Long idSolicitudVisita)throws ProquifaNetException {
		return this.solicitudVisitaDAO.findReferenciaSolicitud(idSolicitudVisita);
	}
	
	public boolean generarSolicitudVisita(SolicitudVisita solicitudVisita)throws ProquifaNetException {
		try {
			
			Funcion funcion = new Funcion();
			
			int idSolicitud = this.solicitudVisitaDAO.insertSolicitudVisita(solicitudVisita,"solicitud");
			
			for (DocumentoSolicitudVisita documento : solicitudVisita.getDocumentos()) {
				if (documento.getArchivo() != null && documento.getArchivo().length > 0) {
					Integer idDocumento = this.solicitudVisitaDAO.insertDocumentoSolicitudVisita(documento, idSolicitud);

					String dirreccion = funcion.obtenerRutaServidor("solicitudvisitadocumento", idSolicitud +""); 
					String direccionDocumento = dirreccion + idDocumento + ".pdf";
					
					documento.setNombre(idDocumento.toString());
					
					File direccionF = new File(dirreccion);
					
					if (!direccionF.exists()) {
						direccionF.mkdirs();
					}
					
					OutputStream out = new FileOutputStream(direccionDocumento);
					
					out.write(documento.getArchivo());
					out.close();
					
				}
			}
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public int generarSolicitudVisita(SolicitudVisita solicitudVisita,String tipo)throws ProquifaNetException {
		try {
			
			Funcion funcion = new Funcion();
			
			int idSolicitud = this.solicitudVisitaDAO.insertSolicitudVisita(solicitudVisita,tipo);
			log.info("generarSolicitudVisita: "+idSolicitud);
			for (DocumentoSolicitudVisita documento : solicitudVisita.getDocumentos()) {
				if (documento.getArchivo() != null && documento.getArchivo().length > 0) {
					String dirreccion = funcion.obtenerRutaServidor("solicitudvisitadocumento", idSolicitud +""); 
					String direccionDocumento = dirreccion + documento.getTitulo() + ".pdf";
					
					documento.setNombre(documento.getTitulo());
					
					File direccionF = new File(dirreccion);
					
					if (!direccionF.exists()) {
						direccionF.mkdir();
					}
					
					OutputStream out = new FileOutputStream(direccionDocumento);
					
					out.write(documento.getArchivo());
					out.close();
					
					this.solicitudVisitaDAO.insertDocumentoSolicitudVisita(documento, idSolicitud);
				}
			}
			
			return idSolicitud;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
}