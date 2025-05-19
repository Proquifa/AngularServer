/**
 * 
 */
package com.proquifa.net.persistencia.catalogos;

import java.util.List;


import com.proquifa.net.modelo.catalogos.FormulaPrecio;
import com.proquifa.net.modelo.catalogos.MedioPago;
import com.proquifa.net.modelo.catalogos.proveedores.ClasificacionConfiguracionPrecio;
import com.proquifa.net.modelo.catalogos.proveedores.ConfiguracionPrecio;
import com.proquifa.net.modelo.catalogos.proveedores.ConfiguracionPrecioProducto;
import com.proquifa.net.modelo.catalogos.proveedores.CostoFactor;
import com.proquifa.net.modelo.catalogos.proveedores.Flete;
import com.proquifa.net.modelo.catalogos.proveedores.InformacionPagos;
import com.proquifa.net.modelo.catalogos.proveedores.Licencia;
import com.proquifa.net.modelo.catalogos.proveedores.MultiusosValores;
import com.proquifa.net.modelo.catalogos.proveedores.TiempoEntrega;
import com.proquifa.net.modelo.comun.Campana;
import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.comun.Empresa;
import com.proquifa.net.modelo.comun.Fabricante;
import com.proquifa.net.modelo.comun.Producto;
import com.proquifa.net.modelo.comun.Proveedor;
import com.proquifa.net.modelo.comun.ValorAdicional;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;

/**
 * @author orosales
 *
 */
public interface CatalogoProveedorDAO {
	/***
	 * Regresa la lista de todos los proveedores regulares. 
	 * @return
	 */
	List<Proveedor> findProveedores(String parametro);
	/**
	 * @param parametro
	 * @return
	 */
	List<Proveedor> findProveedoresXUsuario(Empleado usuario);
	/***
	 * Regresa toda su informacion referente al proveedor buscado
	 * @param idProveedor
	 * @return
	 */
	Proveedor getInformacionGeneralProveedor(Long idProveedor);
	/***
	 * Ingresa un nuevo proveedor
	 * @param nombre
	 * @param rSocial
	 * @return
	 */
	Long insertarProveedor(Proveedor proveedor);
	/***
	 * Actualiza el proveedor seleccionado
	 * @param proveedor
	 * @return
	 */
	Boolean actualizarProveedor(Proveedor proveedor);
	/***
	 * 
	 * @param idProveedor
	 * @return
	 */
	Boolean deshabilitarProveedor(Long idProveedor);	
	/***
	 * 
	 * @param idProveedor
	 * @return
	 */
	Boolean habilitarProveedor(Long idProveedor,Boolean relacionComercial);
	/***
	 * Inserta los tipos de productos que vende el proveedor.
	 * @param config
	 * @return
	 */
	Long insertarConfiguracionProductosArbol(ConfiguracionPrecioProducto config, Boolean actualizarProductos);
	
	/***
	 * Inserta los tipos de productos que vende el proveedor.
	 * @param config
	 * @return
	 */
	Long insertarConfiguracionProductosVende(ConfiguracionPrecioProducto config, Boolean actualizarProductos);
	
	/***
	 * Obtiene todas las posibles configuraciones de productos que vende el proveedor.
	 * @param idProveedor
	 * @return
	 */
	List<ConfiguracionPrecioProducto> findConfiguracionProductosVende(Long idProveedor);
	
	/***
	 * Elimina el registro de los productos que vende el proveedor  y todo lo relacionado a la misma configuracion.
	 * @param idConfiguracionPrecio
	 * @return
	 */
	Boolean eliminarConfiguracionProductoVende(Long idConfiguracionPrecio);
	
	/***
	 * Elimina el registro de los productos que vende el proveedor  y todo lo relacionado a la misma configuracion.
	 * @param idConfiguracionPrecio
	 * @return
	 */
	Boolean eliminarConfiguraciones(Long idConfiguracionPrecio);

	/***
	 * Obtiene todos los productos del vendedor que coincidan con la configuracion seleccionada.
	 * @param idProveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	List<ConfiguracionPrecioProducto> findConfiguracionOferta(Long idConfigPrecio) throws ProquifaNetException;

	/****
	 * Inserta los costos  y factores para la despues asignarlo alguna configuracion de proveedor.
	 * @param costoFactor
	 * @return
	 */
	Long insertarCostoFactor(CostoFactor costoFactor, Boolean licencia, Boolean compuestaCostoF, Boolean compuestaFactorU) throws ProquifaNetException;
	
	/***
	 * Actualiza el coto factor del producto
	 * @param costoFactor
	 * @return
	 */
	Boolean actualizarCostoFactor(CostoFactor costoFactor, Long idConfig, Boolean Licencia, Boolean compuestaCostoF, Boolean compuestaFactorU) throws ProquifaNetException;
	
	/***
	 * Inserta el tiempo de entrega para un determinado grupo de productos 
	 * @param tiempoEntrega
	 * @return
	 */
	Long insertarTiempoEntrega(TiempoEntrega tiempoEntrega);
	
	/**
	 * @param tiempoEntrega
	 * @return
	 */
	Boolean insertarTiempoEntregaRuta(TiempoEntrega tiempoEntrega,Long idCliente);
	
	/***
	 * Actualiza la configuracion seleccionada.
	 * @return
	 */
	Boolean actualizarConfiguracionPrecio(Long idConfig, String nivel, Long costoFactor, Long tiempoEntrega);
	
	/***
	 * Guarda la configuracion de la siguiente etapa de oferta.
	 * @param idProducto
	 * @param idConfiguracion
	 * @return
	 */
	Boolean setConfiguracionPrecioAProducto(Long idProducto, Long idConfiguracion);
	
	/***
	 * Restablecer la configuracion a un paso anterior.
	 * @param idConfiguracion
	 * @param origenRestablecer
	 * @return
	 */
	Boolean reintegrarConfiguracion(Long idConfiguracion);
	
	/**
	 * @param idProveedor
	 * @param idFabricante
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean existeFabricanteProveedor(Long idProveedor, Long idFabricante) throws ProquifaNetException;
	
	/**
	 * @param idProveedor
	 * @param idFabricante
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean insertarFabricanteProveedor(Long idProveedor,Long idFabricante) throws ProquifaNetException;
	/**
	 * @param idProveedor
	 * @param idFabricante
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean eliminarFabricanteProveedor(Long idProveedor,Long idFabricante) throws ProquifaNetException;
	/**
	 * @param idProveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Fabricante> findFabricantesProveedor(Long idProveedor) throws ProquifaNetException;
	/**
	 * @param idProveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean actualizarTiempoEntrega(TiempoEntrega tiempoEntrega, Long idConfig) throws ProquifaNetException;
	/**
	 * @param tiempoEntrega
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean actualizarTiempoEntregaRuta(TiempoEntrega tiempoEntrega,Long idCliente) throws ProquifaNetException;
	/**
	 * @param idProveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	List<ConfiguracionPrecioProducto> findConfiguracionFamilia(Long idConfig, Long idCliente, String tipoNivel) throws ProquifaNetException;
	/**
	 * @param idProveedor
	 * @param Costo
	 * @return
	 * @throws ProquifaNetException
	 */
	List<MultiusosValores> getIdCategoriaPreciolista(Long idCategoria ) throws ProquifaNetException;
	 /**
	  * @param idConfigActual
	  * @param idConfigAntiguo
	  * @return
	  * @throws ProquifaNetException
	  */
	 Boolean actualizarProductosAntiguaConfiguracion(Long idConfigActual,Long idConfigAntiguo, String nivel) throws ProquifaNetException;
	 /**
	  * @param idCategoria
	  * @return
	  * @throws ProquifaNetException
	  */
	 Boolean deleteCategoriaPrecioLista(Long idCategoria) throws ProquifaNetException;
	 /**
	  * @param idCategoria
	  * @param costo
	  * @return
	  * @throws ProquifaNetException
	  */
	 Boolean actualizarCategoriaPrecioLista(Long idCategoria, String costo) throws ProquifaNetException;
	 /**
	  * @param idConfigActual
	  * @param idConfigAntiguo
	  * @param monto
	  * @return
	  * @throws ProquifaNetException
	  */
	 Boolean actualizarProductosConfiguracionMonto(Long idConfigActual,Long idConfigAntiguo,String monto) throws ProquifaNetException;
	 /**
	  * @param config
	  * @param costo
	  * @return
	  * @throws ProquifaNetException
	  */
	 List<MultiusosValores> getListaCategoriaPreciolista(ConfiguracionPrecioProducto config, String costo) throws ProquifaNetException;
	 /**
	  * @param idCategoria
	  * @return
	  * @throws ProquifaNetException
	  */
	 List<MultiusosValores> getListaProductosXCategoriaPrecio(Long idCategoria) throws ProquifaNetException;
	 /**
	  * @param costoAnterior
	  * @param costoActual
	  * @param idProducto
	  * @return
	  * @throws ProquifaNetException
	  */
	 Boolean modificarHistorialCambioPrecio(Double costoAnterior, Double costoActual, Long idProducto) throws ProquifaNetException;
	 /**
	  * @param costoAnterior
	  * @param costoActual
	  * @param idProducto
	  * @return
	  * @throws ProquifaNetException
	  */
	 boolean insertarHistorialCambioPrecio(Double costoAnterior, Double costoActual, Long idProducto) throws ProquifaNetException;
	 /**
	  * @param config
	  * @param costo
	  * @return
	  * @throws ProquifaNetException
	  */
	 long obtenerIdCategoriaConPrecioNuevo(ConfiguracionPrecioProducto config, Double costo) throws ProquifaNetException;
	 
	 /***
	  * Valida si existe una categoria con esas caracteristicas
	  * @param config
	  * @param costo
	  * @return
	  * @throws ProquifaNetException
	  */
	 boolean existCategoriaConPrecioNuevo(ConfiguracionPrecioProducto config, Double costo) throws ProquifaNetException;
	 
	 /**
	  * @param idProducto
	  * @param idCambiar
	  * @param monto
	  * @return
	  * @throws ProquifaNetException
	  */
	 boolean actualizarProductoyCategoriaPrecioxProducto(Long idProducto,Long idCambiar, String monto) throws ProquifaNetException;
	 /**
	  * @param idCategoriaPrecio
	  * @return
	  * @throws ProquifaNetException
	  */
	 List<MultiusosValores> verificarExistenHermanosCategoriaPrecio(Long idCategoriaPrecio) throws ProquifaNetException;
	 /**
	  * @param ConfiguracionProducto
	  * @param Costo
	  * @return
	  * @throws ProquifaNetException
	  */
	 long insertarCategoriaPrecioLista(ConfiguracionPrecioProducto config, Double costo) throws ProquifaNetException;
	 /**
	  * @param IdConfiguracion
	  * @return
	  * @throws ProquifaNetException
	  */
//	 List<MultiusosValores> getListaBorrarConfiguracion( Long idConfiguracion) throws ProquifaNetException;
	 /**
	  * @param IdConfiguracion
	  * @return
	  * @throws ProquifaNetException
	  */
	 List<ConfiguracionPrecioProducto> obtenerConfiguracionPrecioXId( Long idConfiguracion, Long idCliente, String tipoNivelIngreso, Long CategoriaPrecio, String licencia) throws ProquifaNetException;
	 /**
	  * @param IdConfiguracion
	  * @return
	  * @throws ProquifaNetException
	  */
	 List<ConfiguracionPrecioProducto> obtenerConfiguracionClasificacion( Long idConfiguracion, Long idCliente, String tipoNivelIngreso, Long idClasificacion) throws ProquifaNetException;
	 /**
	  * @param idCategoria
	  * @return
	  * @throws ProquifaNetException
	  */
	 Double getCostoXCategoriaPrecioLista( Long idCategoria) throws ProquifaNetException; 
	 /**
	  * @param idCategoria
	  * @return
	  * @throws ProquifaNetException
	  */
	 Double getCostoPorIdProducto( Long idProducto) throws ProquifaNetException; 
	 /**
	  * @param ConfiguracionPrecioProducto config
	  * @return
	  * @throws ProquifaNetException
	  */
	 boolean updateConfPrecioProducto_cambioPrecio(ConfiguracionPrecioProducto config) throws ProquifaNetException;
	 /**
	  * @param ConfiguracionPrecioProducto config
	  * @return
	  * @throws ProquifaNetException
	  */
	 Boolean updateConfPrecioCosto_cambioPrecio(Long idConfCosto, Long idConfFamilia, String costoNuevo, Long idProveedor) throws ProquifaNetException;
	 /**
	  * @param ConfiguracionPrecioProducto config
	  * @return
	  * @throws ProquifaNetException
	  */
	 List<MultiusosValores> obtenerDatosConfigXidProducto(Long idProducto, ConfiguracionPrecioProducto config  ) throws ProquifaNetException;
	 /**
	  * @param ConfiguracionPrecioProducto config
	  * @return
	  * @throws ProquifaNetException
	  */
	 Boolean updateProductoConfig(Long idProducto,Long idFamilia,Long idCosto) throws ProquifaNetException;
	 /**
	  * @param ConfiguracionPrecioProducto config
	  * @return
	  * @throws ProquifaNetException
	  */
	 List<MultiusosValores> obtenerProductosXIdCosto(Long idCosto ) throws ProquifaNetException; 
	 /**
	  * @param Fabricante info
	  * @return
	  * @throws ProquifaNetException
	  */
	 Long insertarFabricanteMarca(Fabricante info) throws ProquifaNetException; 
//	 /**
//	  * @param idConfig
//	  * @return
//	  * @throws ProquifaNetException
//	  */
//	 Boolean updateMontoMinimoOCMV(Long idConfig, Double monto, Double flete, Double descuento) throws ProquifaNetException;
//	 
//	 /***
//	  * @param idConfig
//	  * @return
//	  * @throws ProquifaNetException
//	  */
//	 ConfiguracionPrecio getMontoMinimoOCMV(Long idConfig) throws ProquifaNetException;
	 /***
	  * 
	  * @param idProveedor
	  * @param idProducto
	  * @return
	  * @throws ProquifaNetException
	  */
	 FormulaPrecio getInformacionFormulaPrecio(Long idProveedor, Long idProducto, int stock_flete, String nivel, Long idCliente, Long idConfig) throws ProquifaNetException;
	 /**
	  * 
	  * @param idProveedor
	  * @return
	  * @throws ProquifaNetException
	  */
	 Licencia getLicenciasProveedor(Long idProveedor) throws ProquifaNetException;
	 /***
	  * 
	  * @param licencia
	  * @return
	  * @throws ProquifaNetException
	  */
	 Boolean updateLicencuasProveedor(Licencia licencia) throws ProquifaNetException;
	 /***
	  * 
	  * @param idProveedor
	  * @return
	  * @throws ProquifaNetException
	  */
	 List<ConfiguracionPrecio> getListaConfiguracionPrecioProveedor (Long idProveedor) throws ProquifaNetException;
	 /***
	  * Actualiza la informacion de Tiempo Entrega Proveedor utilizando un idProveedorTiempoEntrega
	  * @param pTiempoEntrega
	  * @return
	  * @throws ProquifaNetException
	  */
//	 Integer updateProveedorTiempoEntrega(ProveedorTiempoEntrega pTiempoEntrega) throws ProquifaNetException;
	 /***
	  * Actualiza la informacion de Flete de un Proveedor utilizando un idFlete
	  * @param flete
	  * @return
	  * @throws ProquifaNetException
	  */
	 Integer updateProveedorFlete(Flete flete) throws ProquifaNetException;
	 /***
	  * 
	  * @param pTiempoEntrega
	  * @return
	  * @throws ProquifaNetException
	  */
//	 Long insertProveedorTiempoEntrega(ProveedorTiempoEntrega pTiempoEntrega, Long idProveedor) throws ProquifaNetException;
	 /***
	  * 
	  * @param flete
	  * @return
	  * @throws ProquifaNetException
	  */
	 Long insertProveedorFlete(Flete flete, Long idProveedor) throws ProquifaNetException;
	 /***
	  * 
	  * @param flete
	  * @param idProveedor
	  * @return
	  * @throws ProquifaNetException
	  */
	 Long insertProveedorFleteExpress(Flete flete, Long idProveedor) throws ProquifaNetException;
	 /***
	  * 
	  * @param idProveedor
	  * @return
	  * @throws ProquifaNetException
	  */
	 List<Flete> findFleteProveedor (Long idProveedor) throws ProquifaNetException;
	 /***
	  * 
	  * @param idProveedor
	  * @return
	  * @throws ProquifaNetException
	  */
	 Flete findFleteExpressProveedor (Long idProveedor) throws ProquifaNetException;
	 /***
	  * 
	  * @param idProveedor
	  * @return
	  * @throws ProquifaNetException
	  */
//	 List<ProveedorTiempoEntrega> findTiempoEntregaProveedor (Long idProveedor) throws ProquifaNetException;
	 /***
	  * actualiza los valores de Restringir Stock, FExpress y Distribuidor
	  * @param configuracionPrecio
	  * @return
	  * @throws ProquifaNetException
	  */
	 Boolean updateRestringirConfiguracionPrecio(ConfiguracionPrecio configuracionPrecio) throws ProquifaNetException;
	 /***
	  * actualiza la fecha de caducidad de los productos asociados a una configuracion precio
	  * @param configuracionPrecio
	  * @return
	  * @throws ProquifaNetException
	  */
	 Boolean updateCaducidadProductosConfiguracionPrecio(ConfiguracionPrecio configuracionPrecio) throws ProquifaNetException;
	 /***
	  * 
	  * @param idConfigPrecio
	  * @return
	  * @throws ProquifaNetException
	  */
	 List<ConfiguracionPrecioProducto> findConfiguracionesPrecioCosto(Long idConfigFam) throws ProquifaNetException;
	 /***
	  * 
	  * @param idConfigFam
	  * @return
	  * @throws ProquifaNetException
	  */
	 List<ClasificacionConfiguracionPrecio> findConceptoClasifConfigPrecio(Long idConfigFam,Long idCliente) throws ProquifaNetException;
	 /***
	  * inserta nuevos conceptos de clasificacion
	  * @param clasificacion
	  * @return
	  * @throws ProquifaNetException
	  */
	 Integer insertConceptoClasifConfigPrecio(ClasificacionConfiguracionPrecio clasificacion) throws ProquifaNetException;
	 /***
	  * modifica concepto de clasificacion
	  * @param clasificacion
	  * @return
	  * @throws ProquifaNetException
	  */
	 Integer updateConceptoClasifConfigPrecio(ClasificacionConfiguracionPrecio clasificacion) throws ProquifaNetException;
	 /***
	  * borra las clasificaciones que no estan relacionadas con ningun producto
	  * @param clasificacion
	  * @return
	  * @throws ProquifaNetException
	  */
	 Integer deleteConceptoClasifConfigPrecio(ClasificacionConfiguracionPrecio clasificacion) throws ProquifaNetException;
	 /***
	  * 
	  * @param idClasificacion
	  * @return
	  * @throws ProquifaNetException
	  */
	 List<ConfiguracionPrecioProducto> findClasificacionPrecioProductoPorCatPrecio(Long idClasificacion) throws ProquifaNetException;
	 /***
	  * 
	  * @param idProveedor
	  * @param idProducto
	  * @param stock_flete
	  * @param nivel
	  * @param idCliente
	  * @param idConfig
	  * @return
	  * @throws ProquifaNetException
	  */
	 FormulaPrecio getInformacionFormulaPrecioClasificacion(Long idProveedor, Long idProducto, int stock_flete, String nivel, Long idCliente, Long idConfig) throws ProquifaNetException;
	 /***
	  * 
	  * @param idProveedor
	  * @return
	  * @throws ProquifaNetException
	  */
	 List<Empresa> findRelacionEmpresasProveedor(Long idProveedor) throws ProquifaNetException;
	 /***
	  * 
	  * @param empresas
	  * @return
	  * @throws ProquifaNetException
	  */
	 Boolean updateRelacionEmpresasProveedor(Empresa empresas) throws ProquifaNetException;
	 /***
	  * 
	  * @param empresas
	  * @return
	  * @throws ProquifaNetException
	  */
	 Boolean insertRelacionEmpresasProveedor(Empresa empresas) throws ProquifaNetException;
	 /***
	  * Obtiene los productos asociados a una clasificacion
	  * @param idClasificacion
	  * @return
	  * @throws ProquifaNetException
	  */
	 List<Producto> getProductosPorClasificacion(Long idClasificacion) throws ProquifaNetException;
	 /***
	  * Actualiza 
	  * @param idClasificacion
	  * @param idConfigAntiguo
	  * @param monto
	  * @return
	  * @throws ProquifaNetException
	  */
	 Boolean actualizarProductosMontoPorClasificacion(Long idClasificacion,Long idConfigAntiguo,Double monto) throws ProquifaNetException;
	 /***
	  * Borra las configuraciones Costo que no tengan ConfiguracionesPrecioProducto relacionadas.
	  * @return
	  * @throws ProquifaNetException
	  */
	 Boolean cleanConfiguracionPrecioCosto () throws ProquifaNetException;

	 /***
	  * Obtiene la informacion general de pagos de un proveedor
	  * @param idProveedor
	  * @return
	  * @throws ProquifaNetException
	  */
	 InformacionPagos getInformacionPagosProveedor(Long idProveedor) throws ProquifaNetException;
	 /***
	  * Obtiene una lista de medios de pagos asociados a un proveedor
	  * @param idProveedor
	  * @return
	  * @throws ProquifaNetException
	  */
	 List<MedioPago> getMediosDePagoProveedor(Long idProveedor) throws ProquifaNetException;
	 
	 Boolean updateInformacionPagoProveeedor( InformacionPagos informacion) throws ProquifaNetException;
	 
	 Boolean updateMediosDePagoProveedor( MedioPago medio) throws ProquifaNetException;
	 
	 Boolean insertMediosDePagoProveedor( MedioPago medio) throws ProquifaNetException;

	 /***
	  * Obtiene los pendientes del Responsable de Cobros que estan en estado abierto.
	  * @return una lista de String (Docto) de los pendientes en caso de no, sera un NULL
	  * @throws ProquifaNetException
	  */
	 List<String> getPendientesRespCobroPraPagador(String idProveedor) throws ProquifaNetException;
	 /***
	  * Se actualiza el campo Responsable por medio del Folio del pendiente.
	  * @return TRUE fue con exito, FALSE no se realizo 
	  * @throws ProquifaNetException
	  */
	 Boolean actualizarResponsablePendienteXfolio(String folio, String responsable) throws ProquifaNetException;
	
	 /**
	 * @param nivel
	 * @param idConfigFamilia
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Long> findListaConfiguracionesNivel(String nivel, Long idConfigFamilia) throws ProquifaNetException;
	
	/**
	 * @param proveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	List<String> getPendientesRespComprador(Long proveedor) throws ProquifaNetException;	
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
	Double obtenerObjetivoFundamentalTranscurrido(Long idProveedor) throws ProquifaNetException;
	/**
	 * 
	 * @param idProveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	Double obtenerObjetivoMontoTranscurrido(Long idProveedor) throws ProquifaNetException;
	Double obtenerObjetivoMontoFundamentalTranscurrido(Long idProveedor) throws ProquifaNetException;
	/**
	 * 
	 * @param idProveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean existeObjetivoAnioActual(Long idProveedor) throws ProquifaNetException;
	/**
	 * 
	 * @param proveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean updateObjetivoCreciminetoProveedor(Proveedor proveedor) throws ProquifaNetException;
	boolean updateObjetivoCrecimientoFundamentalProveedor(Proveedor proveedor) throws ProquifaNetException;
	/**
	 * 
	 * @param proveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean insertObjetivoCrecimientoProveedor(Proveedor proveedor) throws ProquifaNetException;
	/**
	 * @param parametro
	 * @return
	 * @throws ProquifaNetException
	 */
	List<ValorAdicional> findValorAdicional(String parametro) throws ProquifaNetException;
	/**
	 * @param Nombre
	 * @return
	 * @throws ProquifaNetException
	 */
	long getIdFabricantexNombreFabricante (String Nombre) throws ProquifaNetException;
	/**
	 * Obtiene los Pendientes del Inspector relacionados con el Proveedor
	 * @param proveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	List<String> getPendientesInspector(Long proveedor) throws ProquifaNetException;
	/**
	 * Obtiene la lista de tiempos entrega de una configuracion
	 * @return
	 * @throws ProquifaNetException
	 */
	List<TiempoEntrega> findTiempoEntregaPorConfiguracion(Long idConfiguracion) throws ProquifaNetException;
	
	List<ClasificacionConfiguracionPrecio> findConceptoClasifConfigPrecioXidProveedor(Long idProoveedor, Long idCliente) throws ProquifaNetException;
	
	Long insertarCampanaComercial (Campana campana) throws ProquifaNetException;
	
	boolean insertarListaCampana(Campana cam, int size) throws ProquifaNetException;
	
	boolean eliminarConfiguracionCampana(Long idCampana) throws ProquifaNetException;
	
	boolean actualizaCampanaComercial(Campana cam) throws ProquifaNetException;
	
	List<Campana> obtenerCampanasDeProveedor(Long idProveedor) throws ProquifaNetException;
	
	List<ConfiguracionPrecio> obtenerInformacionDeCampanaComercialTipoFamilia(Long idProveedor) throws ProquifaNetException;
	
	List<ClasificacionConfiguracionPrecio> obtenerInformacionDeCampanaTipoClasificacion(Long idCampana) throws ProquifaNetException;
	
	List<Producto> obtenerInformacionDeCampañaTipoProducto(Long idCampana) throws ProquifaNetException;
	
	boolean eliminarCampanaComercalProveedor(Long idCampana) throws ProquifaNetException;
	
	List<Campana> obtenerTodasLasCampanasComerciales() throws ProquifaNetException;
	
	String obtenerProductosConCampana(Long idCampana) throws ProquifaNetException;
	/**
	 * Obtiene la lista de empresas a las que se les puede realizar una cotizacion
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Empresa> obtenerEmpresasParaCotizar() throws ProquifaNetException;

}