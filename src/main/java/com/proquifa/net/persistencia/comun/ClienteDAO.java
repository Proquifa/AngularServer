/**
 * 
 */
package com.proquifa.net.persistencia.comun;

import java.util.List;

import com.proquifa.net.modelo.catalogos.clientes.ParametrosOfertaCliente;
import com.proquifa.net.modelo.catalogos.cobrosypagos.facturista.ReferenciasBancarias;
import com.proquifa.net.modelo.catalogos.proveedores.ConfiguracionPrecioProducto;
import com.proquifa.net.modelo.catalogos.proveedores.TiempoEntrega;
import com.proquifa.net.modelo.comun.Cartera;
import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.ConfiguracionContrato;
import com.proquifa.net.modelo.comun.Contrato;
import com.proquifa.net.modelo.comun.NivelIngreso;
import com.proquifa.net.modelo.comun.Producto;
import com.proquifa.net.modelo.comun.Proveedor;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.tableros.cliente.EntregaCatClientes;


/**
 * @author ernestogonzalezlozada
 *
 */

public interface ClienteDAO {
	/**
	 * Metodo que obtiene un cliente mediante su nombre
	 * @param nombre
	 * @return
	 */
	Cliente obtenerClientePorNombre(String nombre);
	/**
	 * Metodo que obtiene un cliente mediante clave
	 * @param Clave
	 * @return
	 */
	Cliente obtenerClienteXId(Long idcliente);
	/**
	 * Metodo que obtiene unicamente el cliente por su id 
	 * @param Clave
	 * @return
	 */
	String obtenerClientePorID(Long idcliente);
	/**
	 * Metodo que obtiene un cliente por el folio de pedido
	 * @param CPedido
	 * @return
	 */
	Cliente obtenerClienteXFolioPedido(String cPedido);
	/***
	 * Regres la suma de ventas relacionadas a un cliente en un periodo predeterinado
	 * @param periodo
	 * @param idCliente
	 * @return
	 */
	Float obtenerTotalVentasPorPerido(String periodo, Long idCliente);
	/***
	 * Obtiene el numero de operaciones realiadas por un cliente en el ano anterior
	 * @param idCliente
	 * @return
	 */
	int obtenerOperacionesRealizadas(Long idCliente);
	
	/***
	 * Regresa el nivel de ingresos del cliente
	 * @param idCliente
	 * @return
	 */
	String determinarNivelIngresosCliente(Long idCliente, List<NivelIngreso> niveles);
	
	/***
	 * Obtiene datos complementarios para la facturacion electronica.
	 * @param idcliente
	 * @return
	 */
	Cliente getdatosParaCFDI(Long idcliente);
	
	/***
	 * @param empresa
	 * @param idCliente
	 * @return
	 */
	List<ReferenciasBancarias> findReferenciasBancariasCliente(String proveedor, Long idCliente);
	/***
	 * Inserta un nuevo cliente por medio de vista requisicion movil
	 * @param Cliente,Direccion,Contacto
	 * @return true | false 
	 * @throws ProquifaNetException
	 */
	Long insertarCliente(Cliente cliente);
	/***
	 * Obtiene una lista de los Contactos x cliente
	 * @return
	 */
	List<Cliente> FindObtenerContactosXCliente();
	/**
	 * Metodo que registra un cliente en la base de datos 
	 * @param nombre
	 * @return
	 */
	Long insertarCliente(String nombre);
	
	
	/**
	 * Obtener los clientes
	 * @param habilitado
	 * @return
	 */
	List<Cliente> findObtenerClientes(List<NivelIngreso> niveles, String condiciones) throws ProquifaNetException;

	/**
	 * Obtiene todos los Clientes Activos
	 * @return
	 */
	//List<Cliente>comboCliente();
	
	/**
	 * Obtien el Cobrador del Cliente, recibe como paramentro el id del Cliente
	 * @param idcliente
	 * @return
	 */
	String obtenerCobradorCliente(Long idcliente);
	
	/**
	 * Obtener los clientes utilizado en responsable de cobros y pagos
	 * @return
	 */
	List<Cliente> obtenerClientesPorUsuario(Long idUsuarioLogueado);
	/***
	 * Obtiene el Monto Anual Facturado en el ano anterior de un cliente 
	 * @param cliente
	 * @return
	 * @throws ProquifaNetException
	 */
	public Double getMontoAnualAnteriorPorCliente(Long cliente) throws ProquifaNetException;
	
	/***
 	 * Cuando se cambia el cliente de deshabilitado a habilitado la fecha de creacion se actualiza con la fecha actual
 	 * @param idClienete
 	 * @return
 	 * @throws ProquifaNetException
 	 */
 	Boolean updateFechaCreacionCliente(Long idClienete) throws ProquifaNetException;
 	/**
 	 * Asigna un cliente a un cartera
 	 * @param idCliente
 	 * @param idUsuario
 	 * @return
 	 * @throws ProquifaNetException
 	 */
 	boolean asignarClienteACartera(long idCliente,long idCartera) throws ProquifaNetException;
 	/**
 	 * 
 	 * @param idCliente
 	 * @param idESAC
 	 * @param idEV
 	 * @param idCobrador
 	 * @return
 	 * @throws ProquifaNetException
 	 */
 	boolean updateResponsablesClienteCartera(long idCliente,long idESAC,long idEV,long idCobrador) throws ProquifaNetException;
 	/**
 	 * 
 	 * @param pendientesESAC
 	 * @param pendientesCobrador
 	 * @return
 	 */
 	boolean actualizarPendientesAsignarClienteACartera(String pendientes,long idCliente,long idResponsable);
 	/**
 	 * Obtiene la cotizacion de todos los clientes
 	 * @return
 	 * @throws ProquifaNetException
 	 */
 	List<Cliente> getCotizacionClientes() throws ProquifaNetException;
 	/**
 	 * Deshabilita una lista de clientes
 	 * @return
 	 * @throws ProquifaNetException
 	 */
 	boolean deshabilitarClientes(List<Cliente> clientes) throws ProquifaNetException;
 	/**
 	 * 
 	 * @return
 	 * @throws ProquifaNetException
 	 */
 	public List<Cliente> getVentasPorMesesCliente() throws ProquifaNetException;
 	
 	boolean insetarHistorialDeVentaClientes(List<Cliente> clientesVentas) throws ProquifaNetException;
 	
	
 	boolean eliminarClienteDeCartera(Long idcliente, Long idcartera) throws ProquifaNetException;
 	
 	
	Cartera obtenerInformacionDeCarteraPorId(Long idCartera)throws ProquifaNetException;
	
	boolean actualizarResponsableCartera(String query) throws ProquifaNetException;
	
	boolean insertarAutorizacion(String autorizo, String solicitante, String tipo, String razones, String docto) throws ProquifaNetException;
	
	boolean insertaEntregaCliente(EntregaCatClientes entregaCliente) throws ProquifaNetException;
	
	boolean actualizarDatosEntregaCliente(EntregaCatClientes entregaCliente) throws ProquifaNetException;
	
	EntregaCatClientes obtenerInformacionDeEntregaCliente(Long idCliente) throws ProquifaNetException;
	
    boolean actualizarEntregayRevicionyParcialesCliente(boolean entregayRevicion, boolean parciales,String destino, Long idCliente) throws ProquifaNetException;
    
	Long insertaContrato(Contrato contrato) throws ProquifaNetException;
	
	boolean insertaMarcasContrato(Proveedor proveedor, Long idcontrato) throws ProquifaNetException;
	
	boolean eliminarMarcas(Contrato contrato) throws ProquifaNetException;
	
	List<Contrato> obtenerContratosPorIdCliente(Long idCliente) throws ProquifaNetException;
	
	List<Proveedor> getMarcasContratoCliente(Long idContrato) throws ProquifaNetException;
	
	Long actualizarContratoCliente(Contrato contrato) throws ProquifaNetException;
	
	boolean eliminarContratoCliente(Contrato contrato) throws ProquifaNetException;
	
	boolean insertarConfContrato(Long idContrato,ParametrosOfertaCliente parOferta, Long idConf) throws ProquifaNetException;
	
	boolean eliminarConfiguracionesContrato(Contrato contrato,ParametrosOfertaCliente parOferta) throws ProquifaNetException;
	
	List<ConfiguracionContrato> obtenerMarcasContrato(Long idContrato);
	
	List<Producto> obtieneProductosContrato(ConfiguracionPrecioProducto confPro);
	
	List<ConfiguracionPrecioProducto> obtenerIdConfProducto(Long idProveedor, Long idContrato) throws ProquifaNetException;
	
	TiempoEntrega obtenerTiempoEntregaDeProducto(Long idconf, Long idCliente) throws ProquifaNetException;
	
	List<ConfiguracionPrecioProducto> obtenerConfiguracionesDeContrato(Long idContrato) throws ProquifaNetException;
	
	String obtenerEsacDelCliente(Long idcliente);
	
	
 	
}