/**
 * 
 */
package com.proquifa.net.persistencia.comun;

import java.util.List;


import com.proquifa.net.modelo.comun.Direccion;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;

/**
 * @author vromero
 *
 */
public interface DireccionesClienteDAO {
	/***
	 * Obtiene la direccion de facturacion de un cliente
	 * @param idCliente
	 * @return
	 * @throws ProquifaNetException
	 */
	String getDatosDeFacturacion(Long idCliente) throws ProquifaNetException;
	/***
	 * Obtiene la direccion de entrega segun el cliente y segun que se indique en el pedido se obtiene la direccion de entrega
	 * @param idCliente
	 * @param idPedido
	 * @return
	 * @throws ProquifaNetException
	 */
	String getDatosDeEntrega(Long idCliente, Long idPedido) throws ProquifaNetException;
	/***
	 * obtiene si la direccion de entrega es diferente a la de catalogo
	 * @param idPedido
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean getDiferenteDireccionDeEntrega(Long idPedido) throws ProquifaNetException;
	
	/***
	 * Obtiene las direcciones de de cada cliente
	 * @param idCliente
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Direccion> findDireccionesCliente(Integer idCliente) throws ProquifaNetException;
	/***
	 * Agrega una direccion al cliente en la Tabla HorarioyLugar
	 * @param Contacto contacto
	 * @return Integer idDireccion
	 * @throws ProquifaNetException
	 */
	Integer agregarDireccionCliente(Direccion direccion) throws ProquifaNetException;
	/***
	 * Modificar una direccion al cliente en la Tabla HorarioyLugar
	 * @param Contacto contacto
	 * @return Integer idDireccion
	 * @throws ProquifaNetException
	 */
	Boolean modificarDireccionCliente(Direccion direccion) throws ProquifaNetException;
	
	/**
	 * @param idLugar
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean deleteDireccionCliente(Long idLugar) throws ProquifaNetException;
	
	/**
	 * @param idCliente
	 * @return
	 * @throws ProquifaNetException
	 */
	Direccion getDireccionEmpresa (Long idCliente)throws ProquifaNetException;
	/***
	 * 
	 * @param idCliente
	 * @return
	 * @throws ProquifaNetException
	 */
	Direccion getDireccionFacturacion (Long idCliente)throws ProquifaNetException;

	/**
	 * @param direccion
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean updateDireccionEmpresa (Direccion direccion)throws ProquifaNetException;
	/**
	 * @param direccion
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean updateDireccionFacturacion (Direccion direccion)throws ProquifaNetException;
	
	List<Direccion> getDireccionCliente (Long idCliente)throws ProquifaNetException;
	
	List<Direccion> getDireccionesTipoVisitaPorIdCliente(Long idCliente) throws ProquifaNetException;
	
	Direccion obtenerDireccionPorTipoyidCliente(Long idCliente) throws ProquifaNetException;
	
	
}
