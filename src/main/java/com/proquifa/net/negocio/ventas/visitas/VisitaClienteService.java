package com.proquifa.net.negocio.ventas.visitas;

import java.util.List;


import com.proquifa.net.modelo.comun.Direccion;
import com.proquifa.net.modelo.comun.DocumentoAdjunto;
import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.comun.HallazgosAcciones;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.ventas.Cotizacion;
import com.proquifa.net.modelo.ventas.Sprint;
import com.proquifa.net.modelo.ventas.admoncomunicacion.Correo;
import com.proquifa.net.modelo.ventas.visitas.DailyScrum;
import com.proquifa.net.modelo.ventas.visitas.ObtenerPrecios;
import com.proquifa.net.modelo.ventas.visitas.ObtenerVisitas;
import com.proquifa.net.modelo.ventas.visitas.ReportarVisita;
import com.proquifa.net.modelo.ventas.visitas.ResumenSolicitudVisitasyReporteVisita;
import com.proquifa.net.modelo.ventas.visitas.SolicitudVisita;
import com.proquifa.net.modelo.ventas.visitas.VisitaCliente;

public interface VisitaClienteService {

	/**
	 * 
	 * @param usuario	
	 * @return
	 * @throws ProquifaNetException
	 */
	List<SolicitudVisita> consultarPoolVisitas(Long usuario)throws ProquifaNetException;
	
	/**
	 * 
	 * @param solicitudes
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean agruparSolicitudesVisita(List<SolicitudVisita> solicitudes,Boolean visitaExtratemporal)throws ProquifaNetException;
	
	/**
	 * 
	 * @param solicitudes
	 * @return
	 * @throws ProquifaNetException
	 */
	Long agruparSolicitudesVisitaCopia(List<SolicitudVisita> solicitudes)throws ProquifaNetException;
	
	/**
	 * 
	 * @param usuario
	 * @return
	 * @throws ProquifaNetException
	 */
	List <VisitaCliente> obtenerTotalesVisitaPorCliente(Long usuario) throws ProquifaNetException;
	
	/**
	 * 
	 * @param usuario
	 * @return
	 * @throws ProquifaNetException
	 */
	List<VisitaCliente> obtenerVisitasClientePorUsuario(Long usuario) throws ProquifaNetException;
	
	/**
	 * 
	 * @param usuario
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Sprint> obtenerUltimosSprintAbiertos() throws ProquifaNetException;
	
	/**
	 * 
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean actualizarVisitaClienteGuardadasPlanificar(Long usuario,List<VisitaCliente> visita1,List<VisitaCliente> visita2,List<VisitaCliente> visita3,List<VisitaCliente> visita4,List<VisitaCliente> visita5) throws ProquifaNetException;
	
	/**
	 * 
	 * @param usuario
	 * @param idSprint
	 * @param visitas
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean planificarSprintVisitaCliente(Long usuario,Long idSprint,List<VisitaCliente> visitas) throws ProquifaNetException;
	
	/**
	 * 
	 * @param usuario
	 * @return
	 * @throws ProquifaNetException
	 */
	List<String> pendientesVisitaCliente(Long usuario) throws ProquifaNetException;
	
	/**
	 * 
	 * @return
	 * @throws ProquifaNetException
	 */
	List<VisitaCliente> consultarVisitasParaAsignarSprint() throws ProquifaNetException;
	 /**
	  * 
	  * @param visita
	  * @return
	  * @throws ProquifaNetException
	  */
	Boolean guardarAsignarSprint(List<VisitaCliente> visita,Boolean asignar,List<VisitaCliente> visitasEliminadas) throws ProquifaNetException;
	
	/**
	 * 
	 * @param usuario
	 * @return
	 * @throws ProquifaNetException
	 */
	List<VisitaCliente> obtenerVisitasAsignadasPorEV(Long usuario) throws ProquifaNetException;
	
	/**
	 * 
	 * @param visita
	 * @param usuarios
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean guardarVisitasAgendadas(List<VisitaCliente> visita,Long usuarios) throws ProquifaNetException;
	
	/**
	 * 
	 * @param visita
	 * @param usuarios
	 * @return
	 * @throws ProquifaNetException
	 */
	String registrarFechaCheckIn(VisitaCliente visita,Long usuarios) throws ProquifaNetException;
	
	/**
	 * 
	 * @param usuarios
	 * @return
	 * @throws ProquifaNetException
	 */
	List<VisitaCliente> obtenerVisitasEjecutadas(Long usuarios) throws ProquifaNetException;
	/**
	 *  @param documentos
	 * @return
	 * @throws ProquifaNetException
	 */
	
	/**
	 * 
	 * @param usuarios
	 * @return
	 * @throws ProquifaNetException
	 */
	List<VisitaCliente> obtenerVisitasEjecutadasRealizadas(Long usuarios) throws ProquifaNetException;
	
	/**
	 * 
	 * @param documentos
	 * @param idVisita
	 * @param Reporte
	 * @param Hallazgos
	 * @param Acciones
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean insertarDocumentacionDeVisita(List<DocumentoAdjunto> documentos,Long idVisita, String Reporte, List<String> Hallazgos, List<String> Acciones ) throws ProquifaNetException;

	/**
	 * 
	 * @return
	 * @throws ProquifaNetException
	 */
	List<VisitaCliente> obtenerTodasVisitasAsignadas() throws ProquifaNetException;
	
	/**
	 * 
	 * @param visitas
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean registrarDailyScrum(DailyScrum daily) throws ProquifaNetException;
	
	int revisarBloqueoDailyScrum() throws ProquifaNetException;

	List <VisitaCliente> obtenerTotalesReportarVisita(Long usuario) throws ProquifaNetException;

	ResumenSolicitudVisitasyReporteVisita obtenerInformacionVisitaxidCliente(Long idCliente) throws ProquifaNetException;

	boolean actualizarContactoSolicitud(SolicitudVisita solicitud) throws ProquifaNetException;
	
	List <VisitaCliente> obtenerTotalesAgendarVisita(Long usuario) throws ProquifaNetException;
	
	boolean desagendarVisitaCliente(Long idVisita) throws ProquifaNetException;
	
	Sprint obtenerSprintEnCurso() throws ProquifaNetException;
	
	int registrarRealizacionVisita(VisitaCliente visita) throws ProquifaNetException;

	boolean VerificarSprint( Long idSprint) throws ProquifaNetException;
	
	List <VisitaCliente> obtenerTodasVisitasCierre() throws ProquifaNetException;
	
	void verificarSprintEnCurso() throws ProquifaNetException;
	
	void verificarCierreSprint() throws ProquifaNetException;
	
	List<VisitaCliente> obtenerVisitasParaEjecucion(Long usuarios) throws ProquifaNetException;
	
	List<Empleado> iniciarDailyScrum() throws ProquifaNetException;
	
	Boolean acutalizarObservacionesDailyScrum(DailyScrum daily) throws ProquifaNetException;
	
	int cerrarSprintPorEV(Long idSprint,Long idEmpleado,String observaciones,List<VisitaCliente> visitas, Integer calificacionEV) throws ProquifaNetException;
	
	List<Sprint> obtenerUltimosSprintAbiertosNoPlanificados(Long idUsuario) throws ProquifaNetException;
	
	List<Sprint> obtenerSprintSinCerrar() throws ProquifaNetException;

	/**
	 * @param direccion
	 * @return
	 * @throws ProquifaNetException
	 */
	public boolean actualizarLatitudyLongitud(Direccion direccion) throws ProquifaNetException;
	
	/**
	 * @param usuario
	 * @return
	 * @throws ProquifaNetException
	 */
	public List <VisitaCliente> obtenerTodasVisitasPorSprint(Long usuario) throws ProquifaNetException;

	public List <VisitaCliente> obtenerDatosSeccionReporte(int visita) throws ProquifaNetException;

	public List <HallazgosAcciones> obtenerDatosSeccionHallazgos(int visita) throws ProquifaNetException;
	
	Boolean guardaObservaciones(List<HallazgosAcciones> listaHallazgos) throws ProquifaNetException;
	
	/**
	 * @param idVisita
	 * @return
	 * @throws ProquifaNetException
	 */
	public ReportarVisita obtenerReportarVisita(Integer idVisita, Integer generada) throws ProquifaNetException;

	/**
	 * @param reporte
	 * @return
	 * @throws ProquifaNetException
	 */
	public boolean guardarTodasSeccionesReportarVisita(ReportarVisita reporte, Integer idVisita,boolean finalizarVisita, boolean eliminarCotizaciones, VisitaCliente visitaCli) throws ProquifaNetException;

	List<ObtenerVisitas> obtenerAsuntos(Integer idVisita, Integer idContacto) throws ProquifaNetException;

	List<ObtenerPrecios> obtenerPrecios(Integer idProducto, Integer idCliente, Integer idProveedor, String tipo,String subTipo, String control) throws ProquifaNetException;

   public Long guardarGenerarCotizacion(List<Cotizacion> cotizaciones, Long idVisitaCliente, Correo correo, Integer idContacto) throws ProquifaNetException;
  
   public List<Cotizacion> obtenerListaDeCotizacionesPoridVisitaCliente(Long idVisitaCliente, Integer generada) throws ProquifaNetException;
   
   public boolean eliminarCotizacionesPoridVisita(Long idVisitaCliente) throws ProquifaNetException; 
   
   public Long validarContrasena(Empleado empleado, String cliente, String razones, String tipo, String solicitante) throws ProquifaNetException;

}
