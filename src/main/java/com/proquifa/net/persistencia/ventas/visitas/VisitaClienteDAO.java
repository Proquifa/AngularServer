package com.proquifa.net.persistencia.ventas.visitas;

import java.util.ArrayList;
import java.util.List;


import com.proquifa.net.modelo.comun.Direccion;
import com.proquifa.net.modelo.comun.DocumentoAdjunto;
import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.comun.HallazgosAcciones;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.ventas.Cotizacion;
import com.proquifa.net.modelo.ventas.PartidaCotizacion;
import com.proquifa.net.modelo.ventas.Sprint;
import com.proquifa.net.modelo.ventas.requisicion.PRequisicion;
import com.proquifa.net.modelo.ventas.requisicion.Requisicion;
import com.proquifa.net.modelo.ventas.visitas.AsistenciaDailyScrum;
import com.proquifa.net.modelo.ventas.visitas.DailyScrum;
import com.proquifa.net.modelo.ventas.visitas.ObtenerPrecios;
import com.proquifa.net.modelo.ventas.visitas.ObtenerVisitas;
import com.proquifa.net.modelo.ventas.visitas.ReportarVisita;
import com.proquifa.net.modelo.ventas.visitas.SolicitudVisita;
import com.proquifa.net.modelo.ventas.visitas.VisitaCliente;

public interface VisitaClienteDAO {
	
	/**
	 * 
	 * @param usuario
	 * @return
	 */
	List<SolicitudVisita> obtenerPoolVisitas(Long usuario) throws ProquifaNetException;
	 /**
	  * 
	  * @return
	  * @throws ProquifaNetException
	  */
	Long registrarAgrupacionSolicitudVisitas(SolicitudVisita solicitud) throws ProquifaNetException;
	
	/**
	 * 
	 * @param solicitud
	 * @return
	 * @throws ProquifaNetException
	 */
	
	Boolean actualizarVisitaClienteASolicitud(List<SolicitudVisita> solicitud, Long idVisitaCliente) throws ProquifaNetException;
	
	/**
	 * 
	 * @param usuario
	 * @return
	 * @throws ProquifaNetException
	 */
	List<VisitaCliente> obtenerVisitasClienteAgrupadas(Long usuario) throws ProquifaNetException;
	
	/**
	 * 
	 * @param usuario
	 * @return
	 * @throws ProquifaNetException
	 */
	List<VisitaCliente> consultarVisitasClientePorUsuario(Long usuario) throws ProquifaNetException;
	
	/**
	 * 
	 * @param idVisitaCliente
	 * @return
	 * @throws ProquifaNetException
	 */
	List<SolicitudVisita> consultarSolicitudesVisitaPorVisitaCliente(Long idVisitaCliente) throws ProquifaNetException;

	/**
	 * 
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Sprint> consultarUltimosSprintsAbiertos() throws ProquifaNetException;
	
	/**
	 * 
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean updateVisitaClienteGuardadasPlanificar(Long usuario,Long idSprint,String condicion) throws ProquifaNetException;
	
	/**
	 * 
	 * @param usuario
	 * @param idSprint
	 * @param condicion
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean limpiarVisitasGuardadasPlanificar(Long usuario) throws ProquifaNetException;
	
	/**
	 * 
	 * @param usuario
	 * @param idSprint
	 * @param condicion
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean updatePlanificarSprintEV(Long usuario,Long idSprint,String condicion) throws ProquifaNetException;
	
	/**
	 * 
	 * @param usuario
	 * @return
	 * @throws ProquifaNetException
	 */
	int consultaContadorPendientesPoolVisitas(Long usuario) throws ProquifaNetException;
	
	/**
	 * 
	 * @return
	 * @throws ProquifaNetException
	 */
	int consultaContadorPendientesPlanificarSprint(Long usuario) throws ProquifaNetException;
	
	int consultaContadorPendientesAsignarSprint() throws ProquifaNetException;
	
	int consultarNumeroSprintAbierto() throws ProquifaNetException;
	
	int consultarPendientesAgendarVisita(Long usuario) throws ProquifaNetException;
	
	int consultarPendientesReportarVisita(Long usuario) throws ProquifaNetException;

	int consultarContadorControlarPendientes(Long usuario) throws ProquifaNetException;
	
	int consultarContadorControlarAcciones(Long usuario) throws ProquifaNetException;
	
	int consultarPendientesJuntaCierre() throws ProquifaNetException;
	
	List<VisitaCliente> consultasVisitasClienteDeCoordinador() throws ProquifaNetException;
	
	/**
	 * 
	 * @param condicionValor
	 * @param condicionCredito
	 * @param condicionLlaves
	 * @param condicionAsignar
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean asignarVisitasSprintEV(String condicionValor,String condicionCredito,String condicionLlaves,String condicionAsignar) throws ProquifaNetException;
	
	/**
	 * 
	 * @param condicionValor
	 * @param condicionCredito
	 * @param condicionLlaves
	 * @param condicionAsignar
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean limpiarVisitasSinPlanificar(String condicionLlaves) throws ProquifaNetException;
	
	/**
	 * 
	 * @param usuario
	 * @return
	 * @throws ProquifaNetException
	 */
	List<VisitaCliente> consultarVisitasAsignadasPorEV(Long usuario) throws ProquifaNetException;
	
	/**
	 * 
	 * @param conidionesLlave
	 * @param condicionFecha
	 * @param condicionHoraInicio
	 * @param condicionHoraFin
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean asignarHorarioVisitas(String conidionesLlave,String condicionFecha,String condicionHoraInicio,String condicionHoraFin) throws ProquifaNetException;
	
	/**
	 * 
	 * @param idVisita
	 * @param usuario
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean guardarChecadoVisita(Long idVisita,Long usuario) throws ProquifaNetException;
	
	/**
	 * 
	 * @param usuario
	 * @return
	 * @throws ProquifaNetException
	 */
	List<VisitaCliente> obtenerVisitasConCheckIn(Long usuario) throws ProquifaNetException;
	
	Boolean insertarDocumentacionVisita(DocumentoAdjunto documento,Long idVisita) throws ProquifaNetException;
	Boolean updateEstadoDeVisitaCliente(Long idVisita,String Reporte, Long calificacion) throws ProquifaNetException;

		
	/**
	 * 
	 * @return
	 * @throws ProquifaNetException
	 */
	List<VisitaCliente> getVisitasAsignadas() throws ProquifaNetException;
	
	/**
	 * 
	 * @param daily
	 * @return
	 * @throws ProquifaNetException
	 */
	int insertarDailyScrum(DailyScrum daily) throws ProquifaNetException;
	
	/**
	 * 
	 * @param queryAsistenciaValues
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean insertarRegistroAsistencia(String queryAsistenciaValues) throws ProquifaNetException;
	
	/**
	 * 
	 * @param idVisitaCliente
	 * @return
	 * @throws ProquifaNetException
	 */
	List<DocumentoAdjunto> obtenerDocumentosReportes(Long idVisitaCliente) throws ProquifaNetException;
	
	int revisarDailyRealizado() throws ProquifaNetException;

	Boolean insertarHallazgosYAccionesReporteVisita(String descripcion, String Tipo, Long idVisita) throws ProquifaNetException;
	
	List<VisitaCliente> obtenerVisitasClienteAgrupadasReportarVisita(Long usuario) throws ProquifaNetException;
	
	List<Integer> obtenerUltimasVisitasDeClientePoridCliente(Long idCliente) throws ProquifaNetException;
	
	List<HallazgosAcciones> obtenerUltimosHallazgoAccionesDeVisitaCliente(Long idCliente,Long idVisita,String tipo) throws ProquifaNetException;
	
	List<DocumentoAdjunto> obtenerUltimosDocumentosDeVisitaCliente(Long idCliente,Long idVisita) throws ProquifaNetException;
	
	List<String> obtenerMotivosVisita(Long idCliente, Long idVisita) throws ProquifaNetException;

	boolean updateContactoSolicitudVisita(SolicitudVisita solicitud) throws ProquifaNetException;
	
	List<VisitaCliente> obtenerVisitasAgrupadasAgendarVisita(Long usuario) throws ProquifaNetException;
	
	boolean reagendarVisita(Long idVisita) throws ProquifaNetException;

	Sprint getSprintEnCurso() throws ProquifaNetException;
	
	int actualizarRealizacionVisita(VisitaCliente visita) throws ProquifaNetException;
	
	List<VisitaCliente> obtenerVisitasRealizadas(Long usuario) throws ProquifaNetException;
	
	boolean actualizarSprintEnCurso(Long idSprint) throws ProquifaNetException;
	
	boolean cerrarSprintEnCurso(Long idSprint) throws ProquifaNetException;
	
	Long obteneridDeSprintEnCurso() throws ProquifaNetException;
	
	Long obtenerPartidasPorSprint(Long idSprint) throws ProquifaNetException;

	List<VisitaCliente> obtenerTodaVisitasParaCierre() throws ProquifaNetException;
	
	int obtenerNumeroVisitasPendientes() throws ProquifaNetException;
	
	List<VisitaCliente> getVisitaConCheckIn(Long usuario) throws ProquifaNetException;

	Sprint obtenerSprintEnCurso() throws ProquifaNetException;
	
	List<Empleado> getVendedoresConVisitas() throws ProquifaNetException;
	
	DailyScrum getDailylScrumActual() throws ProquifaNetException;
	
	List<AsistenciaDailyScrum> getAsistenciaAactual() throws ProquifaNetException;
	
	boolean actualizarCodigoAsistencia(String codigo,int idAsistencia) throws ProquifaNetException;
	
	boolean actualizarAsistenciaDailyScrum(int asistencia,Long idEmpleado, int idDailyScrum) throws ProquifaNetException;
	
	boolean actualizarObservacionesDailyScrum(String observaciones,int idDailyScrum) throws ProquifaNetException;
	
	List<HallazgosAcciones> obtenerHallazgoAccionesDeVisitaCliente(Long idVisita,String tipo) throws ProquifaNetException;
	
	boolean actualizarFechaCierreVisitaCliente(String idsVisitaCliente,Long idEmpleado) throws ProquifaNetException;
	
	boolean actualizarSprintAntiguo(Long idSprint,Long idVisitaCliente) throws ProquifaNetException;
	
	List<Sprint> consultarUltimosSprintsAbiertosSinPlanificar(Long idUsuario) throws ProquifaNetException;
	
	List<Sprint> ConsultaDeSprintSinCerrar() throws ProquifaNetException;

	Long registrarAgrupacionExtratemporal(SolicitudVisita solicitud,Long sprintAsignado) throws ProquifaNetException;
	/**
	 * @param idHorario
	 * @return
	 * @throws ProquifaNetException
	 */
	public boolean updateLongitudyLatitud(Direccion direccion) throws ProquifaNetException;
	/**
	 * @param usuario
	 * @return
	 * @throws ProquifaNetException
	 */
	public List<VisitaCliente> obtenerTodasVisitasPorSprint(Long usuario) throws ProquifaNetException;
	/**
	 * @param visita
	 * @return
	 * @throws ProquifaNetException
	 */
	public List<VisitaCliente> obtenerDatosSeccionReporte(int visita) throws ProquifaNetException;

	/**
	 * @param visita
	 * @return
	 * @throws ProquifaNetException
	 */
	public List<HallazgosAcciones> obtenerDatosSeccionHallazgos(int visita) throws ProquifaNetException;
	
	/**
	 * @param idVisita
	 * @return
	 * @throws ProquifaNetException
	 */
	public ReportarVisita obtenerReportarVisita(Integer idVisita) throws ProquifaNetException;
	/**
	 * @param requerimientos
	 * @return
	 * @throws ProquifaNetException
	 */
	public boolean updateRequerimientos(List<SolicitudVisita> requerimientos) throws ProquifaNetException;
	
	/**
	 * @param pendientes
	 * @param idVisita 
	 * @return
	 */
	public boolean updatePendientes(List<HallazgosAcciones> pendientes, Integer idVisita);
	/**
	 * @param hallazgos
	 * @param idVisita
	 * @return
	 */
	public  List<HallazgosAcciones> updateHallazgos(List<HallazgosAcciones> hallazgos, Integer idVisita);
	/**
	 * @param idVisita
	 * @return
	 */
	public boolean eliminaHallazgos(Integer idVisita);
	
	boolean guardaObservaciones(HallazgosAcciones listaHallazgos)
			throws ProquifaNetException;
	
	boolean eliminaRequisiciones(Integer idVisita);
	
	boolean ReinsertaRequisiciones(Requisicion requisicion);
	
	List<ObtenerVisitas> obtenerAsuntos(Integer idVisita, Integer idContacto) throws ProquifaNetException;
	
	boolean insertarCierreSprint(Long idSprint, long idEmpleado, String observaciones, Integer calificacionEV)
			throws ProquifaNetException;
	
	List<ObtenerPrecios> obtenerPrecios(Integer idProducto, Integer idCliente, Integer idProveedor, String tipo,
			String subTipo, String control);
	
	List<Cotizacion> cotizacionesLista(Long idVisita, Integer generada) throws ProquifaNetException;
	
	List<PartidaCotizacion> obtenerListaDePartidasCO(Long idCotizacion) throws ProquifaNetException;
	
	Long insertarAutorizacion(String autorizo, String solicitante, String tipo,String razones, String docto) throws ProquifaNetException;
	
	Long insertarPendienteConfirmacion(String folio, String vendedor,String contacto, Long idCotizacion, String medio) throws ProquifaNetException;
	
	Long obtenerCalificacion(Long idVisita) throws ProquifaNetException;
	
	String obtenerNotasDeVisita(Long idVisita) throws ProquifaNetException;
	
	VisitaCliente obtenerReporteDeVisita(int visita) throws ProquifaNetException;
	
	Long obtenerIdContactoDeVisita(Long idVisita) throws ProquifaNetException;
	
}
