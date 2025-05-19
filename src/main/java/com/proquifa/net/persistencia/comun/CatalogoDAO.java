/**
 * 
 */
package com.proquifa.net.persistencia.comun;

import java.util.List;

import com.proquifa.net.modelo.comun.CatalogoItem;
import com.proquifa.net.modelo.comun.NominaCatalogo;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;


/**
 * DAO para el acceso a todos los catalogos de la aplicaci-n. Metodos solo de lectura
 * @author ernestogonzalezlozada
 *
 */
public interface CatalogoDAO {
	/**
	 * Metodo que obtiene las unidades sumando el valor adicional a la consulta 
	 * @param valorAdicional --TODOS-- --NINGUNO--
	 * @return
	 */
	List<CatalogoItem> obtenerUnidades(String valorAdicional) throws ProquifaNetException;
	/**
	 * M-todo que obtiene los fabricantes sumando el valor adicional a la consulta
	 * @param valorAdicional --TODOS-- --NINGUNO--
	 * @return
	 */
	List<CatalogoItem> obtenerFabricantes(String valorAdicional) throws ProquifaNetException;
	/**
	 * M-todo que obtiene clientes sumando el valor adicional a la consulta
	 * @param valorAdicional --TODOS-- --NINGUNO--
	 * @return
	 */
	List<CatalogoItem> obtenerClientes(String valorAdicional) throws ProquifaNetException;
	/**
	 * M-todo que obtiene los tipos de producto m-s el valor adicional a la consulta
	 * @param valorAdicional
	 * @return
	 */
	List<CatalogoItem> obtenerTipoProductos(String valorAdicional) throws ProquifaNetException;
	/**
	 * M-todo que obtiene los proveedores habilitados m-s el valor adicional a la consulta
	 * @param valorAdicional
	 * @return
	 */
	List<CatalogoItem> obtenerProveedores(String valorAdicional) throws ProquifaNetException;
	/**
	 * M-todo que obtiene los proveedores habilitados dependiendo del tipo, temporales o regulares m-s el valor adicional a la consulta
	 * @param valorAdicional
	 * @return
	 */
	List<CatalogoItem> obtenerProveedoresPorTipo(String valorAdicional, String tipo) throws ProquifaNetException;
	/**
	 * M-todo que obtiene los empleados habilitados m-s el valor adicional a la consulta
	 * @param valorAdicional
	 * @return
	 */
	List<CatalogoItem> obtenerEmpleados(String valorAdicional) throws ProquifaNetException;
	/**
	 * M-todo que obtiene los empleados por tipo m-s el valor adicional a la consulta
	 * @param tipo
	 * @param valorAdicional
	 * @return
	 */
	List<CatalogoItem> obtenerEmpleadosPorTipo(String tipo, String valorAdicional) throws ProquifaNetException;
	/**
	 * M-todo que obtiene un ESAC mediante la empresa
	 * @param nombreEmpresa
	 * @return
	 */
	CatalogoItem obtenerVendedorPorEmpresa(String idCliente) throws ProquifaNetException;
	
	/***
	 * M-todo para obtener la lista de los productos de un proveedor.
	 * @param idProveedor
	 * @param valorAdicional
	 * @return
	 * @throws ProquifaNetException
	 */
	List<CatalogoItem> obtenerProductosXIdProveedor(Long idProveedor, String valorAdicional) throws ProquifaNetException;
	
	/***
	 * M-todo para obtener la lista de los SubProcesos en BD
	 * @param valorAdicional
	 * @return
	 * @throws ProquifaNetException
	 */
	List<CatalogoItem> obtenerSubProcesos(String valorAdicional) throws ProquifaNetException;
	/***
	 * M-todo para obtener la lista de los empleados dependiendo del su nivel
	 * @param nivel
	 * @param valorAdicional
	 * @return
	 * @throws ProquifaNetException
	 */
	List<CatalogoItem> obtenerEmpleadosXNivel(String nivel, String valorAdicional) throws ProquifaNetException;
	/**
	 * M-todo que obtiene los empleados por tipo m-s el valor adicional a la consulta, regresa tambien el tipo de usuario
	 * @param tipo
	 * @param valorAdicional
	 * @return
	 */
	List<CatalogoItem> obtenerEmpleadosPorTipoTablero(String tipo, String valorAdicional) throws ProquifaNetException;
	/***
	 * Obtiene los paises para llenar un combo
	 * @return ValorCombo
	 * @throws ProquifaNetException
	 */
	List<CatalogoItem> findPais(String valorAdicional );
	/**
	 * @param valorAdicional
	 * @return
	 */
	List<CatalogoItem> findRutas(String valorAdicional );
	/**
	 * M-todo que obtiene clientes sumando el valor adicional a la consulta
	 * @param valorAdicional --TODOS-- --NINGUNO--
	 * @return
	 */
	List<CatalogoItem> obtenerClientesEstado(String valorAdicional,String tipo)  throws ProquifaNetException;
	
	/**
	 * M-todo que obtiene catalogo de prodcutos por medio de proveedores
	 * @param valorAdicional --TODOS-- --NINGUNO--
	 * @return
	 */
	List<CatalogoItem> obtenerCatalogoPorProveedor(String condiciones)  throws ProquifaNetException;
	/**
	 * M-todo que obtiene catalogo Tiempo de Entrega
	 * @return
	 */
	List<CatalogoItem> obtenerCatalogoTiempoEntrega(String valorAdicional)  throws ProquifaNetException;
	/***
	 * 
	 * @param condiciones
	 * @return
	 * @throws ProquifaNetException
	 */
	List<CatalogoItem> getClientesPorIdUsuarioRol(String condiciones) throws ProquifaNetException;
	/***
	 * 
	 * @param condiciones
	 * @return
	 * @throws ProquifaNetException
	 */
	List<CatalogoItem> getProveedoresPorIdUsuarioRol(String condiciones) throws ProquifaNetException;
	/***
	 * 
	 * @param idProveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	List<CatalogoItem> getCondicionesDePagoProveedor() throws ProquifaNetException;
	/***
	 * 
	 * @return
	 * @throws ProquifaNetException
	 */
	
	List<CatalogoItem> getBancosClientes(String condiciones) throws ProquifaNetException;
	/**
	 * @return
	 * @throws ProquifaNetException
	 */
	
	List<CatalogoItem> getCuentasBancoClientes(String banco, String fpor) throws ProquifaNetException;
	/**
	 * @return
	 * @throws ProquifaNetException
	 */
	
	List<CatalogoItem> getBancos() throws ProquifaNetException;
	
	/**
	 * @return
	 * @throws ProquifaNetException
	 */
	List<CatalogoItem> getCuentas(String banco) throws ProquifaNetException;
	/**
	 * @return
	 * @throws ProquifaNetException
	 */

	List<CatalogoItem> getCorporativos(String usuario) throws ProquifaNetException;
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
	 * Obtiene las Empresas que compran o no compran dependiendo del parametro True o False
	 * @param bCompra
	 * @return
	 */
	List<CatalogoItem>obtenerEmpresaCompra(Boolean bCompra,String valorAdicional) throws ProquifaNetException;
	
	List<CatalogoItem> listClientesDistribuidores(String condiciones) throws ProquifaNetException;
	
	/**
	 * @return
	 * @throws ProquifaNetException
	 */
	List<CatalogoItem> obtenerNivelIngreso() throws ProquifaNetException;
	/***
	 * 
	 * @param condiciones
	 * @return
	 * @throws ProquifaNetException
	 */
	List<CatalogoItem> getAgentesAduanales(String condiciones,String condicionesDos) throws ProquifaNetException;
	/**
	 * Obtiene a los Usuarios que son Inspectores Nivel 31 y Funcion 11
	 * @param condiciones
	 * @return
	 * @throws ProquifaNetException
	 */
	List<CatalogoItem> getInspectores(String condiciones) throws ProquifaNetException;
	
	public List<CatalogoItem> obtenerFamiliasPorProveedor(int idProveedor);
	
	public List<CatalogoItem> obtenerFamiliasPorTipo(int tipo);
	
	public List<CatalogoItem> obtenerFamiliasPorAgenteAduanal(int idAgenteAduanal);
	/**
	 * regresa todos los origenes de los productos
	 * @return
	 */
	public List<CatalogoItem> getOrigenProductos();
	
	NominaCatalogo getNominaCatalogoID (Integer idNominaCatalogo) throws ProquifaNetException;
	List<CatalogoItem> getEmpresasContabilidad() throws ProquifaNetException;
	List<CatalogoItem> getPaisesCodPost() throws ProquifaNetException;
	List<NominaCatalogo> getNominaCatalogoTipo(String tipo) throws ProquifaNetException;
	
	
}