/**
 * 
 */
package com.proquifa.net.persistencia.comun;

import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.comun.ActividadPendiente;
import com.proquifa.net.modelo.comun.Pendiente;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;


/**
 * @author ernestogonzalezlozada
 *
 */
/**
 * @author david.garcia
 *
 */
/**
 * @author david.garcia
 *
 */
/**
 * @author david.garcia
 *
 */
public interface PendienteDAO {
	/**
	 * Metodo que obtiene pendientes por folio 
	 * @param Docto
	 * @param tipoPendiente
	 * @return
	 */
	Pendiente obtenerPendienteXFolio (Long folio);
	/**
	 * Metodo que obtiene el pendientes por los medio de los parametros 
	 * @param Docto
	 * @param tipoPendiente
	 * @return
	 */
	Pendiente obtenerPendienteXTipoDocto (String docto, String tipo);
	/**
	 * Metodo que obtiene el pendientes por los medio de los parametros 
	 * @param Docto
	 * @param tipoPendiente
	 * @param responsable
	 * @return
	 */
	Pendiente obtenerPendienteXTipoDoctoResponsable (String docto, String responsable, String tipo);
	/**
	 * Metodo que obtiene los pendientes de un usuario y el tipo de pendiente
	 * @param usuario
	 * @param tipoPendiente
	 * @return
	 */
	List<Pendiente> obtenerPendientesPorUsuario(String usuario, String tipoPendiente);
	/**
	 * Registrar un pendiente en la base de datos
	 * @param pendiente
	 */
	long guardarPendiente(Pendiente pendiente);
	/**
	 * Actualiza un pendiente en la base de datos
	 * @param pendiente
	 */
	Boolean actualizarPendiente(Pendiente pendiente);
	/**
	 * Obtener pendientes por enviar de la base de datos
	 * @param tipo
	 * @param usuario
	 * @return
	 */
	List<Pendiente> obtenerPendientesPorEnviar(String tipo, String usuario);
	/**
	 * Obtener todos los pendientes por enviar de la base de datos
	 * @param usuario
	 * @return
	 */
	List<Pendiente> findPendientesPorEnviarTodos(String usuario);
	/**
	 * Obtener pendientes de la base de datos mediante los parametros especificados
	 * @param medio
	 * @param origen
	 * @param destino
	 * @param tipoPendiente
	 * @param usuario
	 * @param tiempo
	 * @return
	 */
	List<Pendiente> obtenerPendientesPorEnviar(String medio, String origen, String destino, String tipoPendiente, String usuario, String tiempo);
	/**
	 * Obtener pendientes de la base de datos por medio de los parametros indicados 
	 * @param folioDocumento
	 * @param tipoPendiente
	 * @param usuario
	 * @return
	 */
	List<Pendiente> obtenerPendientesPorEnviarFolio(String folioDocumento, String tipoPendiente, String usuario);
	/**
	 * Metodo que obtiene el numero de pendientes mediante el usuario y el tipo de pendiente
	 * @param usuario
	 * @param tipo
	 * @return
	 */
	Long obtenerNumeroPendientes(String usuario, String tipo);
	/**
	 * Metodo que borra de la base de datos un pendiente
	 * @param pendiente
	 */
	void borrarPendiente(Pendiente pendiente);
	/**
	 * Metodo que asigna otro responsable al pendiente
	 * @param pendiente
	 */
	Boolean actualizarResponsablePendiente(Pendiente pendiente);
	/**
	 * Metodo que asigna otro responsable al pendiente
	 * @param idPendiente, responsable
	 */
	Boolean actualizarResponsablePendienteXId(Long idPendiente, String responsable);
	/**
	 * Obtiene el numero de pendientes mediante los parametros indicados
	 * @param docto Obligatorio
	 * @param usuario
	 * @param tipo
	 * @param abierto
	 * @return
	 */
	Long obtenerNumeroPendientes(String docto, String usuario, String tipo, Boolean abierto);
	/**
	 * Metodo que obtiene la fecha de realizacion por su idRuda o idEntrega
	 * @param idRuta,IdEntrega,Factura,fPor
	 * @return
	 */
	Date obtenerFechaRealizacion(String idRuta, String idEntrega, Long Factura, String fPor, String ruta, String cPedido);
	/**
	 * Metodo para obtener el timepo Proceso de un pedido a facturacien por su Pendiente 
	 * @param cPedido , idPCompra
	 * @return
	 */
	Pendiente obtenerFechaPendienteFacturacion(String cPedido); 
	/**
	 * Metodo para obtener fecha pendiente de pcompra
	 * @param idPCompra
	 * @return
	 */
	Pendiente obtenerFechaPendiente(Long idPCompra);
	/**
	 * Metodo para obtener la fecha fin de un pedido a surtir
	 * @param cPedido
	 * @return
	 */
	Date obtenerFechaPendienteSurtir(String cPedido);
	/**
	 * Metodo para obtener la fecha fin de un pedido a entrega
	 * @param cPedido
	 * @return  
	 */
	Date obtenerFechaPendienteEntrega(String cPedido);
	/**
	 * 
	 * @param Usuario
	 * @return
	 */
	List<ActividadPendiente> findActividadesXusuario(String Usuario);	
	
	/****
	 * Regresa el  numero de pendientes a confirmar del usuario ComPHS-USA.
	 * @param tipo
	 * @return
	 */
	Long finNoPendientesProductoAConfirmar (String tipo);
	
	/***
	 * Regresa el numero de pendiente de proformas a
	 * @param tipo
	 * @param idUsuario
	 * @return
	 */
	Long finNoPendientesProformas(String tipo, Long idUsuario);
	/***
	 * cierra un pendiente 
	 * @param datos
	 * @return
	 */
	Boolean cerrarPendiente(String docto,String partida,String tipo);
	/***
	 * Sobrecarga para anular el campo partida
	 * @param docto
	 * @param tipo
	 * @return
	 */
	Boolean cerrarPendiente(String docto,String tipo);
	/***
	 * Recibe el tipo, docto y partida, Modifica fecha y partida del pendeinte
	 * @param docto
	 * @param tipo
	 * @param partida
	 * @return
	 */
	Boolean reabrirPendiente(String docto, String tipo, String partida);
	Boolean cerrarPendienteGestionarAvisoCambios(String docto , String partida);
    Boolean insertaEnHistorialAvisosCam(long folio, String idPCompra, Date fee);
	
    
	/**
	 * @param pendiente
	 * @return
	 */
	public long guardarPendiente_angular(Pendiente pendiente);
	/**
	 * @param docto
	 * @param partida
	 * @param tipo
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean cerrarPendiente_angular(String docto, String partida, String tipo) throws ProquifaNetException;
	Boolean actualizarPendienteEnvioAlmacen(Pendiente pendiente, String tipo);
	int validarPendiente(Pendiente pendiente);
	int validarPendienteAsignar(Pendiente pendiente);
	Boolean actualizarFfinProductosBO(Long idProducto, Boolean tipo);
	void borrarAsignarMensajero(String evento);
	String mensajeroGDL(String idRuta);
    
    
    
}