package com.proquifa.net.negocio.catalogos;

import java.awt.Point;
import java.util.List;

import com.proquifa.net.modelo.catalogos.FormulaPrecio;
import com.proquifa.net.modelo.catalogos.clientes.ParametrosOfertaCliente;
import com.proquifa.net.modelo.catalogos.proveedores.ClasificacionConfiguracionPrecio;
import com.proquifa.net.modelo.catalogos.proveedores.ConfiguracionPrecioProducto;
//impor mx.com.proquifa.proquifanet.modelo.compras.Compra;
import com.proquifa.net.modelo.comun.Cartera;
import com.proquifa.net.modelo.comun.CarteraCliente;
import com.proquifa.net.modelo.comun.CatalogoItem;
import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.Comentario;
import com.proquifa.net.modelo.comun.Contrato;
import com.proquifa.net.modelo.comun.Direccion;
import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.comun.Horario;
import com.proquifa.net.modelo.comun.NivelIngreso;
import com.proquifa.net.modelo.comun.ValorCombo;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.tableros.cliente.EntregaCatClientes;

/**
 * @author bryan.magana
 *
 */
public interface CatalogoClientesService {

	List<Cliente> obtenerClientes(Long habilitado) throws ProquifaNetException;
	/**
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Cliente> obtenerClientesSinCorporativo() throws ProquifaNetException;
	/**
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Cliente> obtenerClientesXUsuario(Empleado usuario,Long habilitado) throws ProquifaNetException;
 	/**
 	 * @param cliente
 	 * @return
 	 * @throws ProquifaNetException
 	 */
 	boolean actualizarClienteEmpresa(Cliente cliente) throws ProquifaNetException;
 	
 	/**
 	 * @param cliente
 	 * @return
 	 * @throws ProquifaNetException
 	 */
 	boolean actulizarClienteCredito(Cliente cliente) throws ProquifaNetException;
 	
 	/**
 	 * @param cliente
 	 * @return
 	 * @throws ProquifaNetException
 	 */
 	Boolean actualizarClienteFacturacion(Cliente cliente) throws ProquifaNetException;
 	/***
 	 * Obtiene los horarios relacionados a una direccion de cliente
 	 * @param direccion
 	 * @param tipo
 	 * @return
 	 * @throws ProquifaNetException
 	 */
 	List<Horario> listarHorariosDireccionVisitaCliente(Long direccion) throws ProquifaNetException ;
 	
 	/***
	 * utilizando la hora inicio y la hora final, se calcula el cuadro de inicio y el cuadro de fin que se marcara en la vista
	 * @param inicio
	 * @param fin
	 * @return
	 * @throws ProquifaNetException
	 */
	Point obtenerCoordenadaHorario (String inicio, String fin) throws ProquifaNetException;
 	
	/**
 	 * @param cliente
 	 * @return
 	 */
 	Boolean actualizarClienteHorario(List<Horario> horarios) throws ProquifaNetException;
 	
 	/**
 	 * @param direccion
 	 * @return
 	 * @throws ProquifaNetException
 	 */
 	Boolean actualizarDirreccionVisitaCliente(Direccion direccion) throws ProquifaNetException;
 	/***
 	 * obtiene la configuracion de precio de los productos relacionados a un proveedor, se calculan los precios dependiendo el cliente y el tipo de nivel de ingreso
 	 * Todos los productos
 	 * @param idProveedor
 	 * @param Cliente
 	 * @param tipo
 	 * @return
 	 * @throws ProquifaNetException
 	 */
 	List<ConfiguracionPrecioProducto> obtenerConfiguracionPrecioCliente(Long idProveedor, Long Cliente, String tipoNivelIngreso, Long idConfigPrecio) throws ProquifaNetException;

 	/**
 	 * @param idProveedor
 	 * @param Cliente
 	 * @param tipoNivelIngreso
 	 * @param idConfigPrecio
 	 * @return
 	 * @throws ProquifaNetException
 	 */
 	List<ConfiguracionPrecioProducto> obtenerConfiguracionPrecioClientePorProducto(Long idProveedor, Long Cliente, String tipoNivelIngreso, Long idConfigPrecio, Long idProducto) throws ProquifaNetException;
 	/***
 	 * Actualiza el factor del cliente segun la categoria:Familia/Costo/Clasificación/Producto
 	 * @param parametros
 	 * @return
 	 * @throws ProquifaNetException
 	 */
 	Boolean actualizarFactorCliente(ParametrosOfertaCliente parametros) throws ProquifaNetException;
	
 	/***
	 * Obtiene la coniguracion Precio que viene por ID
	 * @param idProveedor
	 * @return Long idConfig, Long idCliente, String tipoNivel
	 * @throws ProquifaNetException
	 */
	ConfiguracionPrecioProducto getConfiguracionPrecioCliente(Long idConfiguracion,Long CategoriaPrecio, Long idCliente, String tipoNivelIngreso) throws ProquifaNetException;
	
	/***
	 * Obtiene la coniguracion de nivel Familia, con respecto a un id de Configuracion y el Cliente
	 * @param idProveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	List<ConfiguracionPrecioProducto> cargarConfiguracionFamiliaCliente(Long idConfig, Long idCliente, String tipoNivel) throws ProquifaNetException;
	
	 /***
	  * Obtiene los factores desglosados que se utilizan en el caculo de los precios por Costo
	  * @param idProveedor
	  * @param idProducto
	  * @return
	  * @throws ProquifaNetException
	  */
	 FormulaPrecio obtenerInformacionFormulaPrecioCliente(Long idProveedor, Long idProducto, String nivel, Long idCliente, Long idConfig) throws ProquifaNetException;
	 /***
	  * Obtiene los factores desglosados que se utilizan en el caculo de los precios por Costo  con la configuración temporal
	  * @param idProveedor
	  * @param idProducto
	  * @return
	  * @throws ProquifaNetException
	  */
	 FormulaPrecio obtenerInformacionFormulaPrecioClienteTemp(Long idProveedor, Long idProducto, String nivel, Long idCliente, Long idConfig) throws ProquifaNetException;
	 /***
	  * Obtiene la configuracion por costo
	  * @param idConfiguracionCosto
	  * @param CategoriaPrecio
	  * @param idCliente
	  * @param tipoNivelIngreso
	  * @return
	  * @throws ProquifaNetException
	  */
	 ConfiguracionPrecioProducto getConfiguracionPrecioClienteCosto(Long idConfiguracionCosto,Long CategoriaPrecio, Long idCliente, String tipoNivelIngreso) throws ProquifaNetException;
	 /***
	  * Regresa la informacion de configuracion precio agrupadas por costo
	  * @param idConfigFam
	  * @return
	  * @throws ProquifaNetException
	  */
	 List<ConfiguracionPrecioProducto> obtenerConfiguracionesPrecioCostoCliente(Long idConfigFam, Long idCliente, String tipoNivelIngreso) throws ProquifaNetException;
	 /***
		 * Obtiene la coniguracion Precio que viene por ID
		 * @param idProveedor
		 * @return 
		 * @throws ProquifaNetException
		 */
	ConfiguracionPrecioProducto obtenerConfiguracionPrecioClasificacionCliente(Long idConfigFam,Long idClasificacion, Long idCliente, String tipoNivelIngreso) throws ProquifaNetException;
	/***
	 * 
	 * @param idClasificacion
	 * @return
	 * @throws ProquifaNetException
	 */
	List<ConfiguracionPrecioProducto> obtenerClasificacionPrecioProductoPorCatPrecioCliente(Long idClasificacion, String tipoNivelIngreso, Long idCliente) throws ProquifaNetException;
	/***
	 * Obtiene los factores desglosados que se utilizan en el caculo de los precios para clasificacion
	 * @param idProveedor
	 * @param idProducto
	 * @param nivel
	 * @param idConfigFam
	 * @return
	 * @throws ProquifaNetException
	 */
	FormulaPrecio obtenerInformacionFormulaPrecioClasificacionCliente(Long idProveedor,Long idProducto, String nivel, Long idConfigFam, Long idCliente) throws ProquifaNetException;
	/***
	 * Obtiene los factores desglosados que se utilizan en el caculo de los precios para clasificacion Configuración Temporal
	 * @param idProveedor
	 * @param idProducto
	 * @param nivel
	 * @param idConfigFam
	 * @return
	 * @throws ProquifaNetException
	 */
	FormulaPrecio obtenerInformacionFormulaPrecioClasificacionTempCliente(Long idProveedor,Long idProducto, String nivel, Long idConfigFam, Long idCliente) throws ProquifaNetException;
	/***
	 * Obtiene todas las clasificaciones asociadas a la familia, 
	 * @param idConfigFamilia
	 * @return
	 * @throws ProquifaNetException
	 */
	List<ClasificacionConfiguracionPrecio> obtenerConfiguracionesPrecioClasificacionCliente(Long idConfigFamilia, Long idCliente) throws ProquifaNetException;
	/***
	 * Borra todas las configuraciones Cliente de un nivel, del cliente indicado
	 * @param nivelConfig
	 * @param idConfigFamilia
	 * @param idCliente
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean restableceTodasConfiguracionesPorNivel(String nivelConfig, Long idConfigFamilia, Long idCliente) throws ProquifaNetException;
	/**
	 * @param horario
	 * @return
	 * @throws ProquifaNetException
	 */
	Horario obtenerHorarioPorPuntos(Horario horario) throws ProquifaNetException;
	/**
	 * Obtiene las direcciones asociadas a un cliente
	 * @param idCliente
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Direccion> listarDireccionesVisitaCliente(Long idCliente) throws ProquifaNetException;
	
	
	Cliente obtenerClienteXId(Long idCliente) throws ProquifaNetException;
	
	/***
	 * Obtiene los comentarios relacionados al cliente
	 * @param idCliente
	 * @return
	 * @throws ProquifaNetException
	 */
	
	List<Comentario> listarComentariosCliente(Long idCliente) throws ProquifaNetException;
	
	List<Direccion> obtenerDireccionCliente(Long idCliente ) throws ProquifaNetException;
	
	/**
	 * 'inserta y modifica una cartera no publicada regresa el id de la categoria 
	 * @param cartera
	 * @param idUsuario
	 * @return
	 * @throws ProquifaNetException
	 */
	Long ActualizarCartera(Cartera cartera , Long idUsuario) throws ProquifaNetException;
	
	/**
	 * 'elimina una cartera no publicada debuelve booleano
	 * @param idcartera
	 * @param idUsuario
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean EliminarCartera(long idcartera, long idUsuario) throws ProquifaNetException;

	/**
	 * debuelve lista de clientes que no pertenecen a ninguna cartera publicada.
	 * @return
	 * @throws ProquifaNetException
	 */
	List <CarteraCliente> obtenerClientesSinCartera (boolean sinCartera, String area) throws ProquifaNetException;
	
	/**
	 * obtiene todas las carteras publicas y borradores
	 * @return
	 * @throws ProquifaNetException
	 */
	List <CarteraCliente> obtenerCarteras () throws ProquifaNetException;
	
	String obtenFoliosPendientesCartera(String tipo) throws ProquifaNetException;
	
	/**
	 * recibe el nivel y el id de usuario y debuelve solo los clientes que le corresponden buscando por los registros de la tabla cartera (para cartera nuevo busca registro ano actual y responsable de la tabla de clientes. )
	 * @param idfuncion
	 * @param idResponsable
	 * @return
	 * @throws ProquifaNetException
	 */
	List <CarteraCliente> obtenerCarterasporColaborador(Long idfuncion, Long idResponsable , Long idCartera) throws ProquifaNetException;
	
	/**
	 * @return
	 * @throws ProquifaNetException
	 */
	List<ValorCombo> obtenerComboEsacNombreCartera () throws ProquifaNetException;
	/**
	 * 
	 * @param cartera
	 * @param idCliente
	 * @param idUsuario
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean asignarClienteACartera(Cartera cartera,long idCliente,long idUsuario) throws ProquifaNetException;

	List<CarteraCliente> obtenerCarterasyClientes(Long idfuncion,Long idResponsable) throws ProquifaNetException;
	
	List<CarteraCliente> calcularCostosDeCartera(List<CarteraCliente> carteras,String parametros, List<NivelIngreso> niveles) throws ProquifaNetException;
	
	List<Direccion> obtenerdDireccionesXidCliente(Long idCliente) throws ProquifaNetException;
	
	List<Horario> listarHorariosDireccionRecoleccion(Long idDireccion,String tipo) throws ProquifaNetException;
	
	Boolean insertarDireciconCliente(Direccion direccion) throws ProquifaNetException;
	
	List<Direccion> obtenerDireccionesDeVisitaXidCliente(Long idCliente) throws ProquifaNetException;
	
	boolean actualizarClienteCartera(Long idCliente, Long idCartera, Long idCarteraAnt) throws ProquifaNetException;
	
	boolean validarContrasena(Empleado empleado, String cliente, String razones, String tipo, String solicitante) throws ProquifaNetException;
	
	boolean guardarActualizarEntregaCliente(EntregaCatClientes entregaCliente) throws ProquifaNetException;
	
	EntregaCatClientes consultarDatosEntregaPoridCliente (Long idCliente) throws ProquifaNetException;
	
	Contrato registroActualizacionDeContrato(Contrato contrato) throws ProquifaNetException;
	
	List<Contrato> obtenerContratosXidCliente(Long idCliente) throws ProquifaNetException;
	
	
	boolean eliminarContratoClienteXidCliente(Contrato contrato) throws ProquifaNetException;
	
	
	public Long generarDocumentoContrato(Contrato contrato,String rutaCliente, String nivelIngreso) throws ProquifaNetException;
	
	
	public Long finalizarContrato(Contrato contrato,byte[] contratoFirmado,boolean renovarContrato) throws ProquifaNetException;
	
	/**
	 * Método que llena el combo UsoCFDI
	 * @return
	 * @throws ProquifaNetException
	 */
	public List<CatalogoItem> obtenerUsoCFDI() throws ProquifaNetException;
	
	/**
	 * Método que lena el Combo MetodoPago
	 * @return
	 * @throws ProquifaNetException
	 */
	public List<CatalogoItem> obtenerMetodoPago() throws ProquifaNetException;
	/**
	 * Metodo que llena el combo ClaveUnidad
	 * @return
	 * @throws ProquifaNetException
	 */
	public List<CatalogoItem> obtenerClaveUnidad() throws ProquifaNetException;
	/**
	 * Metodo que llena el combo ClaveProdServ
	 * @return
	 * @throws ProquifaNetException
	 */
	public List<CatalogoItem> obtenerClaveProdServ() throws ProquifaNetException;

	
	
	
}
