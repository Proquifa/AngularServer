package com.proquifa.net.negocio.despachos.mensajero;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import com.lowagie.text.DocumentException;
import com.proquifa.net.modelo.comun.Documento;
import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.despachos.mensajero.AsignarMensajero;
import com.proquifa.net.modelo.despachos.mensajero.ColectarMensajero;
import com.proquifa.net.modelo.despachos.mensajero.ComentaiosRutaDP;
import com.proquifa.net.modelo.despachos.mensajero.PendientesMensajero;
import com.proquifa.net.modelo.despachos.mensajero.PersonalAlmacenCliente;
import com.proquifa.net.modelo.despachos.mensajero.Recorrido;
import com.proquifa.net.modelo.despachos.mensajero.Ruta;
import com.proquifa.net.modelo.despachos.mensajero.TotalMensajero;

public interface MensajeroService {
	/***
	 * busca todos los pendientes de un mensajero
	 * @param responsable
	 * @return
	 */
	List<PendientesMensajero> consultarPendientesDeMensajero(String responsable) throws ProquifaNetException;
	List<PendientesMensajero> consultarPendientesDeMensajeroPL(String responsable, String estadoRuta) throws ProquifaNetException;
	/***
	 * busca los documentos relacionados a una ruta de un cliente 
	 * @param responsable
	 * @param evento
	 * @param idCliente
	 * @param diferente
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Documento> consultarDocumentosDeRuta(String responsable, String evento, Long idCliente, int diferente, String folio) throws ProquifaNetException;
	/**	
	 * 
	 * @param pendiente
	 * @param usuario
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean ejecutarRutaMensajero(PendientesMensajero pendiente, String usuario ) throws ProquifaNetException;
	/**
	 * 	
	 * @param pendiente
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean validarCoordenadasGPS(List<PendientesMensajero> pendientes) throws ProquifaNetException;
	/**
	 * 
	 * @param responsable
	 * @return
	 * @throws ProquifaNetExceptionz
	 */
	List<PendientesMensajero> consultarPendientesEnCierre(String responsable) throws ProquifaNetException;
	/**
	 * 
	 * @param pendientesXCerrar
	 * @param Usuario
	 * @return
	 * @throws ProquifaNetException
	 */
	
	Boolean concluirEjecucionDeRuta(List<PendientesMensajero> pendientesXCerrar, String Usuario) throws ProquifaNetException;
	/**
	 * 
	 * @param Usuario
	 * @param TipoConsulta
	 * @return
	 * @throws ProquifaNetException
	 */
	List<PendientesMensajero> listarPendientesCerrados(String Usuario) throws ProquifaNetException;
	/**
	 * 
	 * @param Usuario
	 * @param TipoConsulta
	 * @return
	 * @throws ProquifaNetException
	 */
	List<PendientesMensajero> listarPendientesEstadistica(String Usuario) throws ProquifaNetException;
	/***
	 * Obtiene la informacion relacionada a los productos entregados en un evento de ruta, 
	 * se envia la informacion y confirmacion de la entrega al contacto relaciondo del pedido
	 * @param ruta
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean enviarConfirmacionesEntrega(String ruta, Boolean usaPL) throws ProquifaNetException;
	/***
	 * Agrega/actualiza/borra un contacto de personal de almacen de un cliente
	 * @param contacto
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean  actualizarPersonalAlmacenCliente(List<PersonalAlmacenCliente> personal) throws ProquifaNetException;
	/***
	 * Obtiene los contactos
	 * @param idCliente
	 * @return
	 * @throws ProquifaNetException
	 */
	List<PersonalAlmacenCliente> obtenerPersonalAlmacenCliente(Long idCliente) throws ProquifaNetException;
	/***
	 * Obtiene informacion de la ruta a trabajar
	 * @param usuario
	 * @return
	 * @throws ProquifaNetException
	 */
	List<PendientesMensajero> obtenerPendientesRutaTrabajar (String usuario) throws ProquifaNetException;
	
	/***
	 * cierra pendiente de ruta
	 * @param id
	 * @return
	 * @throws ProquifaNetException
	 */

	Boolean cerrarPendienteRutaTrabajar(PendientesMensajero pendientesMensajero) throws ProquifaNetException;
	/**
	 * 
	 * @param usuario
	 * @return
	 * @throws ProquifaNetException
	 */
	public List<PendientesMensajero> obtenerPendientesPlanificar(String usuario) throws ProquifaNetException;
	/**
	 * 
	 * @param pendientesMensajero
	 * @return
	 * @throws ProquifaNetException
	 */
	public Boolean cerrarPendienteRutaPlanear(PendientesMensajero pendientesMensajero) throws ProquifaNetException;
	
	/***
	 * obtiene la liga a la pagina web donde puede ver la documentacion de los productos, debe de mandar el idRutaDP sin el DP
	 * @param idRutaDP
	 * @return
	 * @throws ProquifaNetException
	 */
	public String obtenerLigaDocumentacion(String idRutaDP) throws ProquifaNetException;
	/**
	 * @param idResponsable
	 * @return
	 * @throws ProquifaNetException
	 */
	Map<String, List<TotalMensajero>> findPendientesMensajero(Integer idResponsable) throws ProquifaNetException;
	
	/**
	 * @param idUsuario
	 * @return
	 * @throws ProquifaNetException
	 */
	List<AsignarMensajero> obtenerDatosDetalleAsignarMensajero(Integer idUsuario, String estado) throws ProquifaNetException;
	
	/**
	 * @param rutas
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean guardarAsignarMensajero(List<AsignarMensajero> rutas) throws ProquifaNetException;
	
	/**
	 * @param rutas
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean publicarRuta(List<List<AsignarMensajero>> rutas) throws ProquifaNetException;
	/**
	 * @param idUsuario
	 * @return
	 * @throws ProquifaNetException
	 */
	Map<String, Object> obtenerDatosVistaInicial(Integer idUsuario) throws ProquifaNetException;
	/**
	 * @param idMensajero
	 * @return
	 * @throws ProquifaNetException
	 */
	List<AsignarMensajero> obtenerComparacionRuta(Integer idMensajero) throws ProquifaNetException;
	
	/**
	 * @param ruta
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean guardarCerrarRuta(Ruta ruta) throws ProquifaNetException;

	// metodos para generar el pdf 
	
	public  String saveFotosPDF(String t, int n, String dir) throws DocumentException, MalformedURLException, IOException, InterruptedException;
	
	public  String savePDF(String t);
	
	public  String saveImgArribo(byte[] byteArray, String nombre, String path, String [] id, String tipo) throws ProquifaNetException  ;

	
	public List<PendientesMensajero> consultarPendientesEnCierrePL(String responsable) throws ProquifaNetException;
	
	public List<PendientesMensajero> listarPendientesCerradosPL(String Usuario) throws ProquifaNetException;
	
	
	public Boolean ejecutarRutaMensajeroPL(List <PendientesMensajero> pendiente) throws ProquifaNetException;
	
	public List<ColectarMensajero> colectarMensajeroPL(Parametro parametro ) throws ProquifaNetException;
	String savePDF(String t, String dir);
	Boolean concluirEjecucionDeRutaPL(List<PendientesMensajero> pendientesXCerrar, String Usuario, String usuarioNombre)
			throws ProquifaNetException;
	Boolean guardarComentariosRutaDP(List<ComentaiosRutaDP> lstComentaiosRutaDP) throws ProquifaNetException;
	String saveDocumento3(String id, String tipo) throws ProquifaNetException;
	
	/**
	 * 
	 * @param recorrido
	 * @return
	 * @throws ProquifaNetException
	 */
	public boolean insertarRecorrido(Recorrido recorrido) throws ProquifaNetException;
	
	/**
	 * 
	 * @param folios
	 * @return
	 */
	public Map<String, Object> obtenerFacturasFolio(String[] folios);

	/**
	 * 
	 * @param idResponsable
	 * @return
	 * @throws ProquifaNetException
	 */
	Map<String, Integer> obtenerTotales(Integer idResponsable) throws ProquifaNetException;

}