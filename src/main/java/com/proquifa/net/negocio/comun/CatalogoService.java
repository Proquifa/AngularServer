/**
 * 
 */
package com.proquifa.net.negocio.comun;

import java.util.List;

import com.proquifa.net.modelo.comun.CatalogoItem;
import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.comun.NominaCatalogo;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;


/**
 * Servicio para el acceso a todos los catalogos de la aplicaci-n. Metodos solo de lectura
 * @author ernestogonzalezlozada
 *
 */

public interface CatalogoService {
	/**
	 * M-todo que recupera las unidades
	 * @param valorAdicional
	 * @return
	 * @throws ProquifaNetException
	 */
	List<CatalogoItem> obtenerUnidades(String valorAdicional) throws ProquifaNetException;
	/**
	 * M-todo que recupera los tipos de productos
	 * @param valorAdicional
	 * @return
	 * @throws ProquifaNetException
	 */
	List<CatalogoItem> obtenerTipoProductos(String valorAdicional) throws ProquifaNetException;
	/**
	 * Recupera los fabricantes habilitados 
	 * @return
	 * @throws ProquifaNetException
	 */
	List<CatalogoItem> obtenerFabricantes(String valorAdicional) throws ProquifaNetException;
	/**
	 * Recupera los clientes habilitados 
	 * @return
	 * @throws ProquifaNetException
	 */
	List<CatalogoItem> obtenerClientes(String valorAdicional) throws ProquifaNetException;
	/**
	 * Recupera los proveedores habilitados 
	 * @return
	 * @throws ProquifaNetException
	 */
	List<CatalogoItem> obtenerProveedores(String valorAdicional) throws ProquifaNetException;
	/**
	 * Recupera los proveedores temporales  o regulares
	 * @return
	 * @throws ProquifaNetException
	 */
	List<CatalogoItem> obtenerProveedoresPorTipo(String valorAdicional, String tipo) throws ProquifaNetException;
	/**
	 * Recupera todo los empleados habiltados
	 * @return
	 * @throws ProquifaNetException
	 */
	List<CatalogoItem> obtenerEmpleados(String valorAdicional) throws ProquifaNetException;
	/**
	 * Recuperar los empleados de acuerdo al tipo 
	 * @param tipo
	 * @return
	 * @throws ProquifaNetException
	 */
	List<CatalogoItem> obtenerEmpleadosPorTipo(String tipo, String valorAdicional) throws ProquifaNetException;
	/**
	 * REcupera el vendedor con base en el nombre de la empresa
	 * @param nombreEmpresa
	 * @return
	 * @throws ProquifaNetException
	 */
	CatalogoItem obtenerVendedorPorNombreEmpresa(String idCliente) throws ProquifaNetException;
	/**
	 * Reecuepera los productos de un proveedor
	 * @param idProveedor
	 * @param valorAdicional
	 * @return
	 * @throws ProquifaNetException
	 */
	List<CatalogoItem> obtenerProductosXIdProveedor(Long idProveedor, String valorAdicional) throws ProquifaNetException;
	/**
	 * Recupera los subprocesos 
	 * @param valorAdicional
	 * @return
	 * @throws ProquifaNetException
	 */
	List<CatalogoItem> obtenerSubprocesos(String valorAdicional) throws ProquifaNetException;
	
	/***
	 * Recupera los empleados de un nivel determinado. 
	 * @param nivel
	 * @param valorAdicional
	 * @return
	 * @throws ProquifaNetException
	 */
	List<CatalogoItem> obtenerEmpleadosPorNivel(String nivel, String valorAdicional) throws ProquifaNetException;
	/**
	 * Recuperar los empleados de acuerdo al tipo. Regresa el usuario, clave y tipo
	 * @param tipo
	 * @return
	 * @throws ProquifaNetException
	 */
	List<CatalogoItem> obtenerEmpleadosPorTipoTablero(String tipo, String valorAdicional) throws ProquifaNetException;
	/***
	 * Obtiene los paises para llenar un combo
	 * @return CatalogoItem
	 * @throws ProquifaNetException
	 */
	List<CatalogoItem> findPais(String valorAdicional) throws ProquifaNetException;
	/***
	 * 
	 * @return
	 * @throws ProquifaNetException
	 */
	List<CatalogoItem> listarRutas(String valorAdicional) throws ProquifaNetException;
	/**
	 * Recupera los clientes habilitados 
	 * @return
	 * @throws ProquifaNetException
	 */
	List<CatalogoItem> obtenerClientesEstado(String valorAdicional,String tipo) throws ProquifaNetException;
	/**
	 * Recupera los productos por proveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	List<CatalogoItem> obtenerCatalogoProductosProveedores(String idProveedor) throws ProquifaNetException;
	/**
	 * Recupera los productos por proveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	List<CatalogoItem> obtenerCatalogoTiemposEntrega(String valorAdicional) throws ProquifaNetException;
	/**
	 * Recupera los clientes habilitados segun su funcion
	 * @return
	 * @throws ProquifaNetException
	 */
	List<CatalogoItem> obtenerClientesPorIdUsuarioRol(Long idUsuarioLogeado) throws ProquifaNetException;
	/**
	 * Recupera los proveedores habilitados segun su funcion
	 * @return
	 * @throws ProquifaNetException
	 */
	List<CatalogoItem> obtenerProveedoresPorIdUsuarioRol(Long idUsuarioLogeado) throws ProquifaNetException;
	/***
	 * Obtiene las diferentes condiciones de pago que puede asignarse a un proveedor
	 * @param idProveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	List<CatalogoItem> obtenerCondicionesDePagoProveedor() throws ProquifaNetException;
	/***
	 * Obtiene los bancos que estan en la BD
	 * @return
	 * @throws ProquifaNetException
	 */
	List<CatalogoItem> obtenerBancosClientes(String fpor) throws ProquifaNetException;
	/**
	 * @return
	 * @throws ProquifaNetException
	 */
	
	List<CatalogoItem> obtenerCuentasBancoClientes(String banco, String fpor) throws ProquifaNetException;
	/**
	 * @return
	 * @throws ProquifaNetException
	 */
	List<CatalogoItem> obtenerBancos() throws ProquifaNetException;
	
	/**
	 * @return
	 * @throws ProquifaNetException
	 */
	List<CatalogoItem> obtenerCuentas(String banco) throws ProquifaNetException;
	/**
	 * @return
	 * @throws ProquifaNetException
	 */

	List<CatalogoItem> obtenerCorporativo(String usuario) throws ProquifaNetException;
	/**
	 * @param valorAdicional
	 * @return
	 * @throws ProquifaNetException
	 */
	List<CatalogoItem> obtenerEmpresas() throws ProquifaNetException;
	/**
	 * @return
	 * @throws ProquifaNetException
	 */
	List<CatalogoItem> obtenerIndustrias() throws ProquifaNetException;
	/**
	 * Obtiene las Empresas que compran a Proveedores o las que no Compran a Proveedores segun el parametro True o False
	 * @param bCompra
	 * @return
	 * @throws ProquifaNetException
	 */
	List<CatalogoItem>obtenerEmpresaCompra(Boolean bCompra, String valorAdicional) throws ProquifaNetException;
	/***
	 * Obtiene los Clientes Distribuidores
	 * @param habilitado
	 * @return
	 * @throws ProquifaNetException
	 */
	List<CatalogoItem> obtenerClientesDistribuidores(Boolean habilitado, Empleado empleado) throws ProquifaNetException;
	/**
	 * Regresa los niveles de ingreso de la tabla con el mismo nombre
	 * @return
	 * @throws ProquifaNetException
	 */
	List<CatalogoItem> obtenerNivelIngresos() throws ProquifaNetException;
	/***
	 * 
	 * @param valorAdicional
	 * @return
	 * @throws ProquifaNetException
	 */
	List<CatalogoItem> obtenerAgentesAduanales(String valorAdicional) throws ProquifaNetException;
	/**
	 * Obtiene a los Usuarios que son Inspectores Nivel 31 y Funcion 11
	 * @param valorAdicional
	 * @return
	 * @throws ProquifaNetException
	 */
	List<CatalogoItem> obtenerInspectores(String valorAdicional) throws ProquifaNetException;
	
	public List<CatalogoItem> obtenerFamilias(Integer idProveedor,Integer idAgenteAduanal, Integer tipo) throws ProquifaNetException;
	/**
	 * obtene todos los origenes de los productos
	 * @return
	 * @throws ProquifaNetException
	 */
	public List<CatalogoItem> obtenerOrigenProductos(String valorAdicional) throws ProquifaNetException;
	
	NominaCatalogo obtenerNominaCatalogoID(Integer idNominaCatalogo) throws ProquifaNetException;
	List<CatalogoItem> obtenerEmpresasContabilidad() throws ProquifaNetException;
	List<CatalogoItem> obtenerPaisesCodPost() throws ProquifaNetException;
	List<NominaCatalogo> obtenerNominaCatalogo(String tipo) throws ProquifaNetException;
	
}
