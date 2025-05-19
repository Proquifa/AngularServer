/**
 * 
 */
package com.proquifa.net.persistencia.comun;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;

import java.util.List;

import com.proquifa.net.modelo.comun.Proveedor;

/**
 * @author ernestogonzalezlozada
 *
 */
public interface ProveedorDAO {
	/**
	 * Metodo que obtiene un proveedor por nombre
	 * @param nombre
	 * @return
	 */
	Proveedor obtenerProveedorPorNombre (String nombre);
	/**
	 * Metodo que obtiene un proveedor por clave 
	 * @param idproveedor
	 * @return
	 */
	Proveedor obtenerProveedorPorId(Long idProveedor);
	/**
	 * Determina los adeudos conocidos de un proveedor
	 * @param idProveedor
	 * @param periodo
	 * @return
	 */
	Long obtenerAdeudosDeProveedor(Long idProveedor, String periodo);
	/**
	 * Determina el numero de facturas emitidos por un proveedor
	 * @param idProveedor
	 * @param periodo
	 * @return
	 */
	Long obtenerFacturasDeProveedor(Long idProveedor, String periodo);	
	/***
	 * 
	 * @param idProveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	String getPagadorProveedor(Long idProveedor) throws ProquifaNetException;
	/***
	 * 
	 * @param idProveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	String getCompradorProveedor(Long idProveedor) throws ProquifaNetException;
	
	/**
	 * @param habilitado
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Proveedor> getProveedores(Integer habilitado) throws ProquifaNetException;
}