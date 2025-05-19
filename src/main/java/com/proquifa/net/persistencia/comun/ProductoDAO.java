/**
 * 
 */
package com.proquifa.net.persistencia.comun;
import com.proquifa.net.modelo.catalogos.proveedores.ConfiguracionPrecioProducto;
import com.proquifa.net.modelo.comun.Producto;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.documentoXls.ProductoMicrobiologics;

import java.util.List;

/**
 * @author amartinez
 *
 */
public interface ProductoDAO {
	/**
	 * Recupera un registro de la tabla productos
	 * @param codigo
	 * @param fabrica
	 * @param tipoConsulta - Descripcion
	 * @return
	 */
	Producto obtenerProducto(String codigo, String fabrica);
	/**
	 * 
	 * @param codigo
	 * @param fabrica
	 * @return
	 */
	String obtenerDescripcionProducto(String codigo, String fabrica);
	
	String obtenerDescripcionProductoPorId(Long idProducto) ;
	/**
	 * Regresa la descripcion de un producto en la tabla complemento
	 * @param codigo
	 * @param fabrica
	 * @return
	 */
	String obtenerDescripcionProductoComplemento(Long idComplemento);
	/**
	 * 
	 * @param idProveedor
	 * @param periodo
	 * @return
	 */
	List<Producto> obtenerProductosXProveedor(Long idProveedor, String periodo);
	/**
	 * Metodo para obtener el producto por su ID
	 * @param idProducto
	 * @return
	 */	
	Producto obtenerProductoXId(Long idProducto) throws ProquifaNetException;
	/****
	 * Metodo para obtener la lista de los productos de una marca en especial
	 * @param nombreFabricante
	 * @param valorAdicional
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Producto> obtenerProductosXMarca(Long idFabricante) throws ProquifaNetException;
	
	/***
	 * Actualizar el producto.
	 * @param idProducto
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean actualizarProducto(Producto prod) throws ProquifaNetException;
	/***
	 * 
	 * @param Codigo
	 * @return
	 * @throws ProquifaNetException
	 */

	
	/**
	 * Se obtiene el concepto de la tabla de productos.
	 * @param idproducto
	 * @param codigo
	 * @return
	 */
	String getDescripcionProdUSP (long idproducto , String codigo) throws ProquifaNetException;

	long insertarProductoUSP(Producto producto) throws ProquifaNetException;
	
	long insertarProductoPharmaffiliates(Producto producto) throws ProquifaNetException;
	/**
	 * actualizarPrecioProductoPorCatalogo
	 * @param idProducto
	 * @param lote
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean borrarProductoMicrobiologics() throws ProquifaNetException;
	
	boolean insertarProductoMicrobiologics(ProductoMicrobiologics producto) throws ProquifaNetException;
	
	long insertarLoteAnterior(Long idProducto, String lote) throws ProquifaNetException;
	
	boolean insertarConfiguracionPrecioPorCategoriaPrecio(long idCP,long idProducto) throws ProquifaNetException;
	
	boolean actualizarFKCategoriaPrecioListaDeProducto(long idConfiguracionPrecio,long idProducto) throws ProquifaNetException;
	
	boolean insertarConfiguracionPrecioNuevoProductoUSP(ConfiguracionPrecioProducto config)  throws ProquifaNetException;
	/**
	 * obtiene los productos de usp para dercagar
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Producto> obtenerProductosDescargaDocumentos(int idProveedor)throws ProquifaNetException;
	/**
	 * Actualiza el campo 'Documentacion' para USP 
	 * @param idProducto
	 * @param documento
	 * @return
	 * @throws ProquifaNetException
	 */
	public boolean actualizarDocumentacionPructos(long idProducto, String documento,String campo, String version) throws ProquifaNetException;
	/**
	 * 
	 * @param producto
	 * @return
	 * @throws ProquifaNetException
	 */
	public boolean insertUpdateProductosPharmaffiliates(Producto producto)throws ProquifaNetException;
	/**
	 * 
	 * @param prodcuto
	 * @return
	 * @throws ProquifaNetException
	 */
	public boolean updateProductoCartaUso(Producto prodcuto)  throws ProquifaNetException;
	
	/**
	 * @param productos
	 * @return
	 * @throws ProquifaNetException
	 */
	public boolean updateLoteProductos(List<Producto> productos) throws ProquifaNetException;
	
	/**
	 * @return
	 * @throws ProquifaNetException
	 */
	public boolean updateValidoHasta() throws ProquifaNetException;
	
	/**
	 * @param idProveedor
	 * @param letra
	 * @return
	 * @throws ProquifaNetException
	 */
	public List<Producto> obtenerProductoParaDisponibilidad(Integer idProveedor, String letra) throws ProquifaNetException;
	
	/**
	 * @param productos
	 * @return
	 * @throws ProquifaNetException
	 */
	public boolean updateProductoDisponibilidad(List<Producto> productos) throws ProquifaNetException;
	
	Long obtenerProductosEnStock(String catalogo, String fabrica) throws ProquifaNetException;
	
	public boolean actualizarPrecioProductoPorCatalogo(long idProducto, String fechaCaducidad) throws ProquifaNetException ;
	
	public Boolean existCodigoProducto(String Codigo, String fabrica)	throws ProquifaNetException;
	/**
	 * @param prod
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean actualizarManejoyPresencacionProducto(Producto prod) throws ProquifaNetException;
	/**
	 * @param productos
	 * @param vigente
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean updateProductoVigencia(List<Producto> productos, Integer vigente) throws ProquifaNetException;
	public boolean actualizarCertificado(Producto prod) throws ProquifaNetException;
	
}