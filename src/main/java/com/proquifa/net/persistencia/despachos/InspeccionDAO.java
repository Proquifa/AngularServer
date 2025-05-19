package com.proquifa.net.persistencia.despachos;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.proquifa.net.modelo.comun.Pendiente;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.despachos.PartidaInspeccion;
import com.proquifa.net.modelo.despachos.parametrosInspeccion;
import com.proquifa.net.modelo.despachos.totalesInspeccionProducto;
import com.proquifa.net.modelo.ventas.admoncomunicacion.Correo;

public interface InspeccionDAO {
	
	/***
	 * @return
	 * @throws ProquifaNetException 
	 */
	String obtenerResponsableInspeccion() throws ProquifaNetException;
	/***
	 * @return
	 */
	public List<PartidaInspeccion> obtenerPartidaInspeccion() throws ProquifaNetException;
	/***
	 * 
	 * @param compra
	 * @return
	 */
	
	PartidaInspeccion obtenerPartidaAInspeccionar() throws ProquifaNetException;
	
	
	Pendiente verificaExistePendienteDeCompra(String compra) throws ProquifaNetException;
	/***
	 * 
	 * @param partida
	 * @param docto
	 * @return
	 */
	Boolean actualizarPendienteCompra(String Partida, String Docto) throws ProquifaNetException;
	/***
	 * @return
	 */
	public Integer obtenerContadorPartidasEnInspeccion();
	
	/***
	 * 
	 * @param idCompra
	 * @return
	 * @throws ProquifaNetException
	 */
	PartidaInspeccion obtenerInspeccionOCxIdCompra(Long idPCompra);
	/***
	 * 
	 * @param idCompra
	 * @return
	 * @throws ProquifaNetException
	 */
	Date obtenerFechaInicioDelEstadoPCompra(Long idPCompra);
	/***
	 * 
	 * @param idPpedido
	 * @return
	 * @throws ProquifaNetException
	 */
	Date obtenerFechaInicioDelEstadoPpedido(Long idPpedido);
	/***
	 * 
	 * @param idPpedido
	 * @param tipo
	 * @param fechaInicio
	 * @return
	 * @throws ProquifaNetException
	 */
	Long insertarEstadoPPedido(Long idPpedido, String tipo, Date fechaInicio) throws ProquifaNetException;
	/***
	 * 
	 * @param lstPieza
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean insertaPiezasInspeccionadas(List<PartidaInspeccion> lstPieza) throws ProquifaNetException;
	/***
	 * 
	 * @param idPCompra
	 * @param tipo
	 * @param fechaInicio
	 * @return
	 * @throws ProquifaNetException
	 */
	Long insertarEstadoPCompraDeInspeccion(Long idPCompra, String tipo, Date fechaInicio) throws ProquifaNetException;
	/***
	 * 
	 * @param pIns
	 * @param tabla
	 * @return
	 * @throws ProquifaNetException
	 */
	Long insertarInspeccionOCxInspeccion(PartidaInspeccion pIns, String tabla);
	
	
	Boolean actualizarInspeccionOCPorInspeccion(String manejo, String lote, String inspector, String manejoT,
			Long piezas, Long tiempoDe_Inspeccion, Long idCompra, String video, String documento, String documentoSDS, String prioridad, boolean aplicaDocumentos, String imagenRechazo, String idioma, String edicion,
			String comentario);
	
	/***
	 * 
	 * @param idPpedido
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean actualizarEstadoPPedido(Long idPpedido) throws ProquifaNetException;
	/***
	 * 
	 * @param idPCompra
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean actualizarEstadoPCompra(Long idPCompra) throws ProquifaNetException;
	/***
	 * 
	 * @param pIns
	 * @param tabla
	 * @return
	 * @throws ProquifaNetException
	 */
	Long insertarInspeccionOC(PartidaInspeccion pIns, String tabla);
	/***
	 * 
	 * @param idPCompra
	 * @return
	 * @throws ProquifaNetException
	 */
	String obtenerFolioDeInspecionPoridCompra(Long idPCompra);
	/***
	 * 
	 * @param Docto
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean cerrarPendienteOC(String Docto) throws ProquifaNetException;
	/***
	 * 
	 * @param inspector
	 * @param iQuincena
	 * @param fQuincena
	 * @return
	 * @throws ProquifaNetException
	 */
	totalesInspeccionProducto obtenerTotalesInspeccion(String inspector, Date iQuincena, Date fQuincena) throws ProquifaNetException;
	
	List<PartidaInspeccion> obtenerPiezasPorFecha(String tipo, Date fechaI, Date fechaF, String inspector) throws ProquifaNetException;
	
	
	Long obtenerTotalPartidasInspeccionadas(String inspector);
	
	Date obtenerFechaDePenultimaInspeccion() throws ProquifaNetException;
	/***
	 * @return
	 */
	
	totalesInspeccionProducto obtenerTotatalesDepiezasInspeccionadasyTiempoTotaldeInspeccionde30diasAtras() throws ProquifaNetException;
	/***
	 * 
	 * @param Inspector
	 * @return
	 */
     Long obtenerNumPiezasMaximasDeinspeccionPorUsuario(String inspector) throws ProquifaNetException;
     /***
 	 * 
 	 * @param Inspector
 	 * @return
 	 */
	Long obtenerPiezasPromedioPorUsuario(String inspector) throws ProquifaNetException;
	 /***
 	 * 
 	 * @param Inspector
 	 * @return
 	 */
	Long cantidadDePiezasInspeccionadasPorHoy(String inspector) throws ProquifaNetException;
	/***
	 * @return
	 */
	Long PiezasAInspeccionarHoy() throws ProquifaNetException;

	String obtenerFolioPorPeso() throws ProquifaNetException;

	String obtenerTipoInspeccion(String compra) throws ProquifaNetException;
	
	String obtenerTipoInspeccionXidPcompra(int Pcompra) throws ProquifaNetException;

	String obtenerIdPcompraInspeccionTemporal() throws ProquifaNetException;
	
	String obtenerFolioInspeccionTemporal() throws ProquifaNetException;

	String obtenerFolioXInspectorTablaTemp(Long clave) throws ProquifaNetException ;
	
	Boolean borrarRegistrosTablaInspeccionTemporal(Long clave) throws ProquifaNetException ;

	Boolean insertarTablaInspeccionTempXDocumento(String folio, String tipo, long clave) throws ProquifaNetException;
	
	Boolean insertarTablaInspeccionTempXPrioridad(int fk01_idPcompra, String tipo, Long clave) throws ProquifaNetException;

	Boolean actualizarTipoDocumentoInspeccion(String tipo, String folio)throws ProquifaNetException ;

	Integer obtenerTotalPartidasXCompra(String compra) throws ProquifaNetException;
	
	PartidaInspeccion obtenerPartidaInspeccionPorPuntos(String folio) throws ProquifaNetException;

	Long obtenerClaveInspector(String inspector) throws ProquifaNetException;
	
	Integer obtenerPzasPorPrioridad(String prioridad)throws ProquifaNetException;

	String obtenerUbicacion (Map<String, Object> data)throws ProquifaNetException;
	
	Boolean guardarExistenciaUbicacion (String idUbicacion, int pieza)  throws ProquifaNetException;
	
	String obtenerSubCarpeta (String idProducto)throws ProquifaNetException ;
	
	Boolean guardarConsumible(Integer cantidad, String tipoBolsa)throws ProquifaNetException ;
	/**
	 * @param tipo
	 * @param fechaI
	 * @param fechaF
	 * @param inspector
	 * @return
	 * @throws ProquifaNetException
	 */
	List<PartidaInspeccion> obtenerPiezasEstadisticas(String tipo, Date fechaI, Date fechaF, String inspector)
			throws ProquifaNetException;
	
	
	/**
	 * @param idUsuario
	 * @return
	 * @throws ProquifaNetException
	 */
	public PartidaInspeccion obtenerPartida(Integer idUsuario) throws ProquifaNetException;
	
	/**
	 * @param idPartidaInspeccionOC
	 * @param idUsuario
	 * @return
	 * @throws ProquifaNetException
	 */
	public boolean apartarPartida(Integer idPartidaInspeccionOC, Integer idUsuario) throws ProquifaNetException;
	public boolean insertarStock(Integer cantidad, Long idPPedido,  Long PCompra) throws ProquifaNetException;
	public Integer consultarStock(Long idPPedido) throws ProquifaNetException;
	public boolean actualizarTablas(Long idPPedido, Long PCompra) throws ProquifaNetException;
	public boolean insertarPiezasStock(Long idPCompra, Integer cantidad, List<PartidaInspeccion> lstPieza) throws ProquifaNetException;
	public Correo obtenerDatosCorreo(Long idPPedido) throws ProquifaNetException;
	public Boolean acualizarEnvioCorreo(Long idPedido) throws ProquifaNetException;
	
	/**
	 * @param idPPedido
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean actualizarPartidaRemision(Long idPPedido) throws ProquifaNetException;
	boolean insertarRestantes(Integer cantidad, Long idPPedido, Long PCompra) throws ProquifaNetException;
	boolean actualizarRestantes(Integer cantidad, Long idPPedido, Long PCompra) throws ProquifaNetException;
	/**
	 * @param idPCompra
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean existePartidaInspeccionOC(Integer idPCompra) throws ProquifaNetException;
	/**
	 * @param param
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean insertarInspeccionOC(parametrosInspeccion param) throws ProquifaNetException;
	/**
	 * @param pza
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean insertaPiezas(PartidaInspeccion pza) throws ProquifaNetException;
	
	/**
	 * @param cantidad
	 * @param idPCompra
	 * @return
	 * @throws ProquifaNetException
	 */
	List<String> obtenerPartidasInsertadasStock(Integer cantidad, Integer idPCompra, String cpedido) throws ProquifaNetException;
	boolean actualizarPCompraStock(parametrosInspeccion param);
	Integer consultaPrioridad1() throws ProquifaNetException;
	Boolean verificarEstado(Long idPCompra) throws ProquifaNetException;
	Boolean updateApartado(parametrosInspeccion inspeccion);

	
}
