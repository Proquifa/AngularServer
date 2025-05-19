package com.proquifa.net.persistencia.despachos.mensajero;

import java.util.List;
import java.util.Map;

import com.proquifa.net.modelo.comun.Documento;
import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.despachos.mensajero.AsignarMensajero;
import com.proquifa.net.modelo.despachos.mensajero.ColectarMensajero;
import com.proquifa.net.modelo.despachos.mensajero.ComentaiosRutaDP;
import com.proquifa.net.modelo.despachos.mensajero.ConfirmacionEntrega;
import com.proquifa.net.modelo.despachos.mensajero.PendientesMensajero;
import com.proquifa.net.modelo.despachos.mensajero.PersonalAlmacenCliente;
import com.proquifa.net.modelo.despachos.mensajero.Recorrido;
import com.proquifa.net.modelo.despachos.mensajero.TotalMensajero;

public interface MensajeroDAO {
	/***
	 * Regresa la lista de pendientes de los diferentes tipo de Ruta
	 * @param responsable
	 * @return PendientesMensajero
	 */
	List<PendientesMensajero> findPendientesDeRutas(String responsable, String tipoPendiente) throws ProquifaNetException;
	/***
	 * Regresa la lista de documentos que estan en Entrega y en Entrega Especial
	 * @param idCliente
	 * @return
	 */
	List<Documento> findDocumentosEntrega(Long idCliente, int diferente, String responsable,String folio, String tipoPendiente) throws ProquifaNetException;
    /***
	 * Regresa la lista de documentos que estann en Revision
	 * @param idCliente
	 * @return
	 */
	List<Documento> findDocumentosRevision(Long idCliente, int diferente, String responsable,String folio, String tipoPendiente) throws ProquifaNetException;
	/***
	 * Regresa la lista de documentos que estan para Cobrar
	 * @param idCliente
	 * @return
	 */
	List<Documento> findDocumentosCobro(Long idCliente, int diferente, String responsable,String folio, String tipoPendiente) throws ProquifaNetException;
    /***
	 * Regresa la lista de documentos que estan en Recoleccion
	 * @param idCliente
	 * @return
	 */
	List<Documento> findDocumentosRecoleccion(Long idCliente, int diferente, String responsable,String folio, String tipoPendiente) throws ProquifaNetException;
	/***
	 * Regresa la lista de documentos que estan en Recoleccion
	 * @param idCliente
	 * @return
	 */
	List<Documento> findDocumentosRecoleccionEspecial(Long idCliente, int diferente,String responsable,String folio, String tipoPendiente) throws ProquifaNetException;
	/***
	 * 
	 * @param idEvento
	 * @param idCliente
	 * @param Usuario
	 * @return
	 */	
	Boolean ejecutarRutaMensajero(String evento ,String idEvento, Long idCliente, String Usuario) throws ProquifaNetException;	
	/**
	 * 
	 * @param pendiente
	 * @return
	 */
	Boolean insertarCoordenadasGPS(PendientesMensajero pendiente) throws ProquifaNetException;	
	/***
	 * 
	 * @param usuario
	 * @return
	 */
	List<PendientesMensajero> findPendientesEnCierre(String usuario, String tipoPendiente) throws ProquifaNetException;	
	/***
	 * 
	 * @param pendiente
	 * @param Usuario
	 * @return
	 */
	Boolean concluirEjecucionDeRuta(PendientesMensajero pendiente, String Usuario, String tipoPendiente) throws ProquifaNetException;	
	/***
	 * 
	 * @param Usuario
	 * @param TipoConsulta
	 * @return
	 */
	List<PendientesMensajero> findPendientesCerrados(String Usuario, String tipoPendiente) throws ProquifaNetException;
	/***
	 * Obtiene la informacion de las piezas entregadas, los contactos q los que se debe de informar de la entrega
	 * @param ruta
	 * @return
	 * @throws ProquifaNetException
	 */
	List<ConfirmacionEntrega> obtenerInformacionEntrega(String ruta) throws ProquifaNetException;
	/**
	 * Obtiene la suma de piezas por fabrica en un evento de Entrega
	 * @param ruta
	 * @return
	 * @throws ProquifaNetException
	 */
	List<ConfirmacionEntrega> obtenerSumaPiezasFabrica(String ruta) throws ProquifaNetException;
	/***
	 * Actualiza la rutaDP, se relaciona la ruta con la notificacion de entrega
	 * @param ruta
	 * @param folio
	 * @return
	 * @throws ProquifaNetException
	 */
	void updateRutaDPfolioNE(String ruta, String folio) throws ProquifaNetException;
	/***
	 * Inserta un contacto nuevo de PersonalAlmacenCliente
	 * @param contacto
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean insertPersonalAlmacenCliente(PersonalAlmacenCliente contacto) throws ProquifaNetException;
	/***
	 * Actualiza los datos de un contacto personal almacen cliente
	 * @param contacto
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean updatePersonalAlmacenCliente(PersonalAlmacenCliente contacto) throws ProquifaNetException;
	/***
	 * Borra contacto de personal almacen cliente
	 * @param idPersonal
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean deletePersonalAlmacenCliente(Long idPersonal) throws ProquifaNetException;
	
	/***
	 * 
	 * @param idCliente
	 * @return
	 * @throws ProquifaNetException
	 */
	List<PersonalAlmacenCliente> getPersonalAlmacenCliente(Long idCliente) throws ProquifaNetException;
	/***
	 * Obtiene los contactos extras que se indicaron en el pedido para notificar
	 * @param ruta
	 * @return
	 * @throws ProquifaNetException
	 */
	List<String> getContactosExtrasNotificacion(String ruta) throws ProquifaNetException;
	
	/***
	 * Obtiene zona, identrega y fecha inicio
	 * @param usuario
	 * @return
	 * @throws ProquifaNetException
	 */
	
	List<PendientesMensajero> findPendientesPorUsuario (String condiciones) throws ProquifaNetException;
	
	/***
	 * Cierra Pendiente
	 * @param 
	 * @return
	 * @throws ProquifaNetException
	 */
	
	Boolean updateCerrarPendiente (PendientesMensajero pendientesMensajero,String tipoActual,String tipoActualizar,String estadoActualRuta) throws ProquifaNetException;
	/**
	 *  Actualiza el estado de los pendientes
	 * @param nombreTabla
	 * @return
	 * @throws ProquifaNetException
	 */
	public boolean updateRutaPorPerndiente(String nombreTabla,String estadoActual,String estadoRuta,String idNombre,String docto) throws ProquifaNetException;
	/**
	 * 
	 * @param docto
	 * @param tipo
	 * @return
	 * @throws ProquifaNetException
	 */
	public boolean updatePendienteFechaFin(String docto,String tipo) throws ProquifaNetException;
	/**
	 * 
	 * @param docto
	 * @param partida
	 * @param responsable
	 * @param tipo
	 * @return
	 * @throws ProquifaNetException
	 */
	public boolean insertPendiente(String docto, String partida, String responsable, String tipo) throws ProquifaNetException;
	/**
	 * @param idResponsable
	 * @return
	 * @throws ProquifaNetException
	 */
	Map<String, List<TotalMensajero>> findPendientesMensajero(Integer idResponsable) throws ProquifaNetException;
	
	/**
	 * @param idUsuario
	 * @param estado 
	 * @return
	 * @throws ProquifaNetException
	 */
	List<AsignarMensajero> obtenerDatosDetalleAsignarMensajero(Integer idUsuario, String estado) throws ProquifaNetException;
	
	/**
	 * @param ruta
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean guardarAsignarMensajero(AsignarMensajero ruta) throws ProquifaNetException;
	
	/**
	 * @param ruta
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean actualizarAsignarMensajero(AsignarMensajero ruta) throws ProquifaNetException;
	
	/**
	 * @param ruta
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean actualizarAlmacen(AsignarMensajero ruta) throws ProquifaNetException;
	/**
	 * @param ruta
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean actualizarEstadoAsignarMensajero(AsignarMensajero ruta, String folio) throws ProquifaNetException;
	
	public List<PendientesMensajero> findPendientesDeRutasPL(Empleado responsable, String tipoPendiente, String estadoRuta) throws ProquifaNetException;
	
	public List<PendientesMensajero> findPendientesEnCierrePL(Empleado usuario, String tipoPendiente) throws ProquifaNetException;
	
	public List<PendientesMensajero> findPendientesCerradosPL(String Usuario, String tipoPendiente) throws ProquifaNetException;
	

	public Boolean ejecutarRutaMensajeroPL(PendientesMensajero pendiente)  throws ProquifaNetException;
	
	public List<ColectarMensajero> colectarMensajero(Parametro parametro);
	public Boolean concluirEjecucionDeRutaPL(PendientesMensajero pendiente, String Usuario, String usuarioNombre, String tipoPendiente)
			throws ProquifaNetException;
	public Boolean insertComentariosRutaDP(ComentaiosRutaDP comentaiosRutaDP) throws ProquifaNetException;
	public int obtenerNumeroOrden(String Usuario);
	
	/**
	 * 
	 * @param pendiente
	 * @return
	 * @throws ProquifaNetException
	 */
	public Boolean updateCoordenadasGPS(PendientesMensajero pendiente) throws ProquifaNetException;
	
	/**
	 * 
	 * @param ruta
	 * @return
	 */
	public boolean finalizoRuta(String ruta);
	
	/**
	 * 
	 * @param recorrido
	 * @return
	 * @throws ProquifaNetException
	 */
	public boolean insertarRecorrido(Recorrido recorrido) throws ProquifaNetException;
	
	/**
	 * 
	 * @param ruta
	 * @return
	 * @throws ProquifaNetException
	 */
	public List<ConfirmacionEntrega> obtenerInformacionEntregaPL(String ruta) throws ProquifaNetException;
	
	/**
	 * 
	 * @param folio
	 * @return
	 */
	public List<String> obtenerFacturasFolio(String folio);
	Map<String, Integer> totalesGeneral(Integer idResponsable) throws ProquifaNetException;

}
