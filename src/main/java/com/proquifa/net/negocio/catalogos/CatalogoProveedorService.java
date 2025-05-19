/**
 * 
 */
package com.proquifa.net.negocio.catalogos;

import java.util.List;

import com.proquifa.net.modelo.catalogos.FormulaPrecio;
import com.proquifa.net.modelo.catalogos.proveedores.ClasificacionConfiguracionPrecio;
import com.proquifa.net.modelo.catalogos.proveedores.ConfiguracionPrecio;
import com.proquifa.net.modelo.catalogos.proveedores.ConfiguracionPrecioProducto;
import com.proquifa.net.modelo.catalogos.proveedores.Flete;
import com.proquifa.net.modelo.catalogos.proveedores.InformacionPagos;
import com.proquifa.net.modelo.catalogos.proveedores.Licencia;
import com.proquifa.net.modelo.catalogos.proveedores.Logistica;
import com.proquifa.net.modelo.comun.Campana;
import com.proquifa.net.modelo.comun.Direccion;
import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.comun.Empresa;
import com.proquifa.net.modelo.comun.Fabricante;
import com.proquifa.net.modelo.comun.Horario;
import com.proquifa.net.modelo.comun.Producto;
import com.proquifa.net.modelo.comun.Proveedor;
import com.proquifa.net.modelo.comun.ValorAdicional;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;


/**
 * @author orosales
 *
 */
public interface CatalogoProveedorService {
	/***
	 * Trae una lista de todos los proveedores regulares para el catalogo.
	 * en el parametro puedes pedir que sea habilitado, deshabilitado, nacional, extranjero, solo separandolo por un - o solo una palabra
	 * ej. "Habilitado-Nacional"	"Extranjero-Deshabilitado"	"Habilitado"	"Todos"
	 * @param parametro
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Proveedor> listarProveedores(String parametro) throws ProquifaNetException;
	/**
	 * @param usuario
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Proveedor> listarProveedoresXUsuario(Empleado usuario) throws ProquifaNetException;
	/***
	 * Obtiene  informacion general sobre el proveedor.
	 * @param idProveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	Proveedor obtenerInformacionGeneralProveedor(Long idProveedor) throws ProquifaNetException;
	/***
	 * Inserta un proveedor nuevo
	 * @param nuevoProveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	Long agregarNuevoProveedor(Proveedor nuevoProveedor) throws ProquifaNetException;
	/***
	 * Actualiza la informacion de un proveedor
	 * @param infoProveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean actualizarProveedor(Proveedor infoProveedor) throws ProquifaNetException;

	/***
	 * 
	 * @param idProveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean deshabilitarProveedor(Long idProveedor) throws ProquifaNetException;	

	/***
	 * 
	 * @param idProveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean habilitarProveedor(Long idProveedor,Boolean relacionComercial) throws ProquifaNetException;

	/***
	 * Inserta los tipos de productos que vende el proveedor.
	 * @param cpp
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean guardarProductosVende(ConfiguracionPrecioProducto producto) throws ProquifaNetException;

	/***
	 * Obtiene todas las posibles configuraciones de productos que vende el proveedor.
	 * @param idProveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	List<ConfiguracionPrecioProducto> listarConfiguracionProductosVende(Long idProveedor) throws ProquifaNetException;

	/***
	 * Elimina el registro de los productos que vende el proveedor  y todo lo relacionado a la misma configuracion.
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean eliminarConfiguracionProducto(Long idConfiguracionPrecio) throws ProquifaNetException;

	/***
	 * Obtiene todos los productos del vendedor que coincidan con la configuracion seleccionada.
	 * @param idProveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	List<ConfiguracionPrecioProducto> listarConfiguracionOferta(Long idConfigPrecio) throws ProquifaNetException;

	/***
	 * Asigna costo factor a configuracion de precio
	 * @param configProducto
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean asignarCostoFactorAConfiguracionPrecio(ConfiguracionPrecioProducto configProducto) throws ProquifaNetException;

	/***
	 * Guarda la configuracion de la siguiente etapa de oferta.
	 * @param productos
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean guardarConfiguracionOferta(ConfiguracionPrecioProducto productos) throws ProquifaNetException;

	/***
	 * Restablecer la configuracion a un paso anterior.
	 * @param idConfiguracion
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean reintegrarConfiguracion(Long idConfiguracion,String nivel) throws ProquifaNetException;

	/**Cuando el metodo recibe 0, mostrara todos los fabricantes Habilitados, cuando sea mayor a 0 mostrara solo los fabricantes asignados al proveedor
	 * @param idProveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Fabricante> consultarFabricantes(Long idProveedor) throws ProquifaNetException;

	/**Metodo para insertar agregar o eliminar un fabricante al Proveedor
	 * @param idProveedor
	 * @param idFabricante
	 * @param estado
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean verificarFabricante(Long idProveedor,Long idFabricante,Boolean estado) throws ProquifaNetException;

	/***
	 * Obtiene la coniguracion de nivel Familia, con respecto a un id de Configuracion
	 * @param idProveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	List<ConfiguracionPrecioProducto> cargarConfiguracionFamilia(Long idConfig) throws ProquifaNetException;

	/***
	 * Obtiene la coniguracion de nivel Familia, con respecto a un id de Configuracion
	 * @param idProveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean actualizarCostoPorcentajeNivel (ConfiguracionPrecioProducto config, Boolean tipo, String precio,  boolean editarCosto, boolean editarTransito) throws ProquifaNetException;

	/***
	 * Obtiene la coniguracion Precio que viene por ID
	 * @param idProveedor
	 * @return 
	 * @throws ProquifaNetException
	 */
	ConfiguracionPrecioProducto getConfiguracionPrecio(Long idConfiguracion,Long CategoriaPrecio, String licencia) throws ProquifaNetException;

	/***
	 * Obtiene la coniguracion Precio que viene por ID
	 * @param idProveedor
	 * @return 
	 * @throws ProquifaNetException
	 */
	ConfiguracionPrecioProducto obtenerConfiguracionPrecioClasificacion(Long idConfiguracion,Long idClasificacion) throws ProquifaNetException;

	/***
	 * Obtiene la coniguracion Precio que viene por ID
	 * @param idProveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean operacionesFabricante(Fabricante infoFabricante, String operacion) throws ProquifaNetException;

	//	 /***
	//	  * actualiza el monto minimo de orden de compra en moneda de venta de la configuracion del nivel de la Familia del proveedor
	//	  * @param idConfig
	//	  * @param monto
	//	  * @return
	//	  * @throws ProquifaNetException
	//	  */
	//	 Boolean actualizarMontoMinimoOCMV(Long idConfig, Double monto, Double flete, Double descuento) throws ProquifaNetException;
	//	 
	//	 /***
	//	  * obtine el Monto Minimo de Orden de Compra en Moneda Venta de la configuracion del nivel de la familia del proveedor
	//	  * @param idConfig
	//	  * @return
	//	  * @throws ProquifaNetException
	//	  */
	//	 ConfiguracionPrecio obtenerMontoMinimoOCMV(Long idConfig) throws ProquifaNetException;

	/***
	 * Obtiene los factores desglosados que se utilizan en el caculo de los precios por costo
	 * @param idProveedor
	 * @param idProducto
	 * @return
	 * @throws ProquifaNetException
	 */
	FormulaPrecio obtenerInformacionFormulaPrecio(Long idProveedor, Long idProducto, String nivel, Long idConfig) throws ProquifaNetException;
	/***
	 * Obtine los factores desglosados que se utilizan en el calculo de los precios por clasificacion
	 * @param idProveedor
	 * @param idProducto
	 * @param nivel
	 * @param idConfig
	 * @return
	 * @throws ProquifaNetException
	 */
	FormulaPrecio obtenerInformacionFormulaPrecioClasificacion(Long idProveedor, Long idProducto, String nivel, Long idConfig) throws ProquifaNetException;
	/***
	 * Obtiene los factores de Licencia relacionadas a un proveedor. Si recibe un -1, eso significa que esta deshabilitado el factor.
	 * @param idProveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	Licencia obtenerLicenciasProveedor(Long idProveedor) throws ProquifaNetException;
	/***
	 * Inserta los factores si no existen en BD, actualiza los factores, si recibe valores -1 es para indicar que ese factor esta deshabilitado.
	 * @param licencia
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean actualizarLicenciasProveedor(Licencia licencia) throws ProquifaNetException;
	/***
	 * Lista todas las configuraciones de familia de un proveedor
	 * @param idProveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	List<ConfiguracionPrecio> listarConfiguracionPrecioProveedor (Long idProveedor) throws ProquifaNetException;
	/***
	 * Obtiene las configuraciones por costo
	 * @param idConfiguracion
	 * @return
	 * @throws ProquifaNetException
	 */
	List<ConfiguracionPrecioProducto> listarConfiguracionPrecioProductoCosto(Long idConfiguracion) throws ProquifaNetException;
	/***
	 * Obtiene el Costo de Compra de un producto
	 * @param idProducto
	 * @return
	 * @throws ProquifaNetException
	 */
	Double obtenerCostoProductoCompra(Long idProducto) throws ProquifaNetException;
	/***
	 * inserta o actualiza los valores de Tiempo Entrega y Fletes relacionados a un proveedor.
	 * @param logistica
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean actualizarLogistica(Logistica logistica) throws ProquifaNetException;
	/***
	 * obtiene la informacion de Logistica asociada a un proveedor
	 * @param idProveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	Logistica obtenerLogisticaProveedor (Long idProveedor) throws ProquifaNetException;
	/***
	 * obtiene la lista de fletes asociados a un proveedor
	 * @param idProveedor
	 * @return
	 * @throws ProquifaNetException
	 */

	List<Flete> listarFletesProveedor (Long idProveedor) throws ProquifaNetException;
	/***
	 * obtiene la lista de Tiempo entrega asociads a un proveedor
	 * @param idProveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	//	 List<ProveedorTiempoEntrega> listarTiempoEntregaProveedor (Long idProveedor) throws ProquifaNetException;
	/***
	 * Solo actualiza la restriccion para stock, flete express, distibuidor y la fecha de caducidad cuando es diferente de null
	 * @param configuracionPrecio
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean actualizarPropiedadesConfiguracionPrecio(ConfiguracionPrecio configuracionPrecio) throws ProquifaNetException;
	/***
	 * Regresa la informacion de configuracion precio agrupadas por costo
	 * @param idConfigFam
	 * @return
	 * @throws ProquifaNetException
	 */
	List<ConfiguracionPrecioProducto> obtenerConfiguracionesPrecioCosto(Long idConfigFam) throws ProquifaNetException;
	/***
	 * lista los conceptos de clasificacion asociados a una configuracion de precio
	 * @param idConfigFam
	 * @return
	 * @throws ProquifaNetException
	 */
	List<ClasificacionConfiguracionPrecio> listarConceptoClasifConfigPrecio(Long idConfigFam) throws ProquifaNetException;
	/***
	 * actualiza la lista de conceptos clasificacion, inserta y modifica
	 * @return
	 * @throws ProquifaNetException
	 */
	Integer actualizarConceptoClasifConfigPrecio(List<ClasificacionConfiguracionPrecio> conceptos) throws ProquifaNetException;
	/***
	 * Obtiene todas las clasificaciones asociadas a la familia
	 * @param idConfigFamilia
	 * @return
	 * @throws ProquifaNetException
	 */
	List<ClasificacionConfiguracionPrecio> obtenerConfiguracionesPrecioClasificacion(Long idConfigFamilia) throws ProquifaNetException;
	/***
	 * obtiene los precios para las direfentes categorias de precios que hay en una clasificacion
	 * @param idClasificacion
	 * @return
	 * @throws ProquifaNetException
	 */
	List<ConfiguracionPrecioProducto> obtenerClasificacionPrecioProductoPorCatPrecio(Long idClasificacion) throws ProquifaNetException;
	/***
	 * obtiene todas las empresas y su relacion de compra con el proveedor
	 * @param idProveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Empresa> obtenerRelacionEmpresasProveedor(Long idProveedor) throws ProquifaNetException;
	/***
	 * actualiza o inserta la informacion sobre la relacion entre empresas y proveedores
	 * @param empresas
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean actualizarRelacionEmpresasProveedor(List<Empresa> empresas) throws ProquifaNetException;

	/***
	 * Obtiene la informacion de pagos de un proveedor
	 * @param idProveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	InformacionPagos obtenerInformacionPagosProveedor(Long idProveedor) throws ProquifaNetException;
	/***
	 * actualiza la informacion de pagos de un proveedor
	 * @param informacion
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean actualizarInformacionPagoProveeedor( InformacionPagos informacion) throws ProquifaNetException;
	/***
	 * Borrar todas las configuraciones en un nivel indicado
	 * @param nivelConfig
	 * @param idConfigFamilia
	 * @param catalogo
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean restableceTodasConfiguracionesPorNivel(String nivelConfig, Long idConfigFamilia) throws ProquifaNetException;
	
	/**
	 * 
	 * @param idProveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	Double comprasAnualAnteriorxProveedor(Long idProveedor) throws ProquifaNetException;
	/**
	 * 
	 * @param idProveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	Double obtenerObjetivoTranscurrido(Long idProveedor) throws ProquifaNetException;
	/**
	 * 
	 * @param idProveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	Double obtenerObjetivoMontoTranscurrido(Long idProveedor) throws ProquifaNetException;
	/***
	 * Trae una lista de tipo valores adicionales con el nombreProveedor , idProveedor , tipoProducto , tipo = "Tipo Producto"
	 * @return
	 * @throws ProquifaNetException
	 */
	List<ValorAdicional> listarProveedoresTipoProducto() throws ProquifaNetException;
	/***
	 * Trae una lista de tipo valores adicionales con el nombreProveedor , idProveedor , NombreComercial  , NombreLegal  , tipo = "Agente Aduanal"
	 * @return
	 * @throws ProquifaNetException
	 */
	List<ValorAdicional> listarProveedoresAgenteAduanal() throws ProquifaNetException;
	/**
	 * Actualiza, inserta o elimina la direccion de Recoleccion, solo para Proveedores Nacionales
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean actualizarDireccionRecoleccion(Direccion d) throws ProquifaNetException;
	/**
	 * Obtiene la Direccion de Recoleccion del Proveedor
	 * @param idProveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	Direccion obtenerDireccionRecoleccion(Long idProveedor) throws ProquifaNetException ;
	/**
	 * Actualiza, inserta o elimina el/los horarios de la Direccion de Recoleccion
	 * @param h
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean actualizarHorarioRecoleccion(List<Horario> h) throws ProquifaNetException;
	/**
	 * Obtiene los horarios de la direccion
	 * @param idDireccion
	 * @param tipo
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Horario> listarHorariosDireccionRecoleccion(Long idDireccion, String tipo) throws ProquifaNetException;
	/**
	 * Obtiene los folios de todos los Pendientes que tiene que ver con el Inspector
	 * @param idProveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	List<String> obtenerFoliosPendientesInspector(Long idProveedor)  throws ProquifaNetException;
	
	public String prueba();
	/**
	 * Obtiene todas las clasificaciones del proveedor
	 * @param idProveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	List<ClasificacionConfiguracionPrecio> obtenerClasificacionesXidProveedor(Long idProveedor) throws ProquifaNetException;
	/**
	 * Actualiza y agrega una campaña del proveedor
	 * @param camapana
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean agregarActualizarCampana(Campana campana) throws ProquifaNetException;
	
	List<Campana> obtenerCampanasComercialesPorIdProveedor(Long idProveedor) throws ProquifaNetException;
	
	List<Producto> obtenerTodosLosProductosDelProveedor(Integer idProveedor) throws ProquifaNetException;
	
	boolean eliminarCampanaComercial(Campana campanaA) throws ProquifaNetException;
	
	List<Producto> obtenerProductosConCampana(Long idCliente, String nivel) throws ProquifaNetException;
	
	List<Campana> obtenerCampanasConTodosSusProductos(Long idCliente, String nivelCliente) throws ProquifaNetException;
	
	/***
	 * obtiene todas las empresas a las cuales se pueden cotizar 
	 * @param 
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Empresa> obtenerListaEmpresasParaCotizar() throws ProquifaNetException;
	
	Producto obtenerPrecioMinYMaximoDeProducto(Long idCliente,String nivel,String producto) throws ProquifaNetException;
	
	
}