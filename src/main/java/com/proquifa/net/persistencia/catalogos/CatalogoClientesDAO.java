package com.proquifa.net.persistencia.catalogos;

import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.catalogos.FormulaPrecio;
import com.proquifa.net.modelo.catalogos.clientes.ParametrosOfertaCliente;
import com.proquifa.net.modelo.catalogos.proveedores.ConfiguracionPrecioProducto;
import com.proquifa.net.modelo.comun.Cartera;
import com.proquifa.net.modelo.comun.CarteraCliente;
import com.proquifa.net.modelo.comun.CatalogoItem;
import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.Comentario;
import com.proquifa.net.modelo.comun.Direccion;
import com.proquifa.net.modelo.comun.Horario;
import com.proquifa.net.modelo.comun.NivelIngreso;
import com.proquifa.net.modelo.comun.ValorCombo;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;

public interface CatalogoClientesDAO {

	List<CarteraCliente> obtenerCarterasyClientes(String parametros, List<NivelIngreso> niveles) throws ProquifaNetException;

	/**
	 * @param parametros
	 * @param niveles
	 * @return
	 * @throws ProquifaNetException
	 */
	List<CarteraCliente> findMontosGeneralesCarteras (String parametros, List<NivelIngreso> niveles) throws ProquifaNetException;

	List<CarteraCliente> queryCarteraCliente(String parametros,List<NivelIngreso> niveles, Long idCartera) throws ProquifaNetException;

	/**
	 * se busca el idcorporativo a partir del idcartera
	 * @param idCartera
	 * @return
	 * @throws ProquifaNetException
	 */
	Long getidCorporativoporidCartera(Long idCartera)throws ProquifaNetException;

	/**
	 * @param cartera
	 * @param idUsuario
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean updateCarteraPublicada (Cartera cartera, String actualizaPendientes, String actualizaPendientesEVT, String actualizaPendientesCobrador, String clientes ,long idUsuario) throws ProquifaNetException;

	/**
	 * @param idCartera
	 * @param cartera
	 * @param idUsuario
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean publicaCartera (long idCartera, String actualizaPendientes, String actualizaPendientesEVT, String actualizaPendientesCobrador, String clientes , Cartera cartera, Long idUsuario) throws ProquifaNetException;

	/**
	 * actualiza una cartera existente en base a su idcategoria
	 * @param cartera
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean updateCartera(Cartera cartera) throws ProquifaNetException;

	/**
	 * se guarda una cartera debuelve id de la cartera
	 * @param cartera
	 * @return
	 * @throws ProquifaNetException
	 */
	Long insertCartera(Cartera cartera) throws ProquifaNetException;

	/**
	 * @param idCartera
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Long> findClientesCartera (Long idCartera) throws ProquifaNetException;

	/**
	 * Elimina una categoria por su idcategoria
	 * @param idCartera
	 * @param esCorporativo
	 * @param listaClientes
	 * @param llamadoCorporativo
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean deleteCartera(Long idCartera, Long idCorporativo,Long idUsuario ,String  listaClientes , boolean llamadoCorporativo) throws ProquifaNetException;

	/**
	 * debuelve una lista de clientes que no estan seleccionados en ninguna cartera publicada
	 * @param parametros
	 * @return
	 * @throws ProquifaNetException
	 */
	List<CarteraCliente> findClientesSinCartera (String parametros, List<NivelIngreso> niveles, String area) throws ProquifaNetException;

	/**
	 * @return
	 * @throws ProquifaNetException
	 */
	List<ValorCombo> obtenerComboEsacNombreCartera () throws ProquifaNetException;

	/**
	 * @param Long idCliente
	 * @return Cliente
	 * @throws ProquifaNetException
	 */
	Cliente  obtenerInfoCliente(Long idCliente) throws ProquifaNetException;

	/**
	 * @param Long idCliente
	 * @return Cliente
	 * @throws ProquifaNetException
	 */
	List<String> getPendientesRespCobrador(String idCliente) throws ProquifaNetException;

	/***
	 * Obtiene la lista de folios con los pendientes de un Cliente
	 * @param idCliente
	 * @return
	 * @throws ProquifaNetException
	 */
	public List<String> getPendientesRespESAC(String idCliente) throws ProquifaNetException;

	/***
	 * Actualiza el responsable ESAC para pendientes que no estan dentro de la tabla pendiente
	 * @param idCliente
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean updatePendientesCotizacionESAC(String idCliente, String esac) throws ProquifaNetException;

	/***
	 * 
	 * @param idCliente
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Long> obteneridsCarterasPoridCliente(Long idCliente) throws ProquifaNetException;

	/**
	 * elimina un cliente de la tabla cliente cartera y actualiza el folio de la cartera
	 * @param idcliente
	 * @param idcartera
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean deleteClienteCartera (Long idcliente , Long idcartera) throws ProquifaNetException;

	/**
	 * @param cliente
	 * @return
	 */
	Boolean updateCliente(Cliente cliente) throws ProquifaNetException;

	/**
	 * @param cliente
	 * @return
	 */
	Boolean updateClienteCredito(Cliente cliente) throws ProquifaNetException;

	/**
	 * @param cliente
	 * @return
	 */
	Boolean updateClienteFacturacion(Cliente cliente) throws ProquifaNetException;

	/**
	 * @param cliente
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean updateClienteCorporativo(Cliente cliente) throws ProquifaNetException;

	/***
	 * 
	 * @param idHorario
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean deleteHorarioCliente(Long idHorario) throws ProquifaNetException;

	/**
	 * @param horario
	 * @return
	 * @throws ProquifaNetException 
	 */
	Boolean updateClienteHorario(Horario horario) throws ProquifaNetException;

	/***
	 * 
	 * @param horario
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean agregarHorarioCliente(Horario horario) throws ProquifaNetException;

	/**
	 * @param idCliente
	 * @return
	 * @throws ProquifaNetException 
	 */
	Boolean fechaActualizacionCliente(Long idCliente) throws ProquifaNetException;

	/**
	 * @param cliente
	 * @return
	 */
	List<Horario> obtenerHorarioCliente(Long idDireccion) throws ProquifaNetException;

	/**
	 * 
	 * @param idDireccion
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean deleteHorarioClientePorIdDIreccion(Long idDireccion) throws ProquifaNetException;

	/**
	 * 
	 * @param clientes
	 * @param idMensajero
	 * @return
	 */
	Boolean actualizarMensajeroDeClientes(List<CarteraCliente> clientes, long idMensajero);

	/***
	 * 
	 * @param idLugar
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean deleteDireccionCliente(Long idLugar) throws ProquifaNetException;

	/***
	 * 
	 * @param d
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean updateDireccionCliente(Direccion d)	throws ProquifaNetException;

	/***
	 * 
	 * @param direccion
	 * @return
	 * @throws ProquifaNetException
	 */
	Long agregarDireccionCliente(Direccion direccion) throws ProquifaNetException;

	/***
	 * 
	 * @param idProveedor
	 * @param idCliente
	 * @param tipoNivelIngreso
	 * @return
	 * @throws ProquifaNetException
	 */
	List<ConfiguracionPrecioProducto> findConfiguracionPrecioProductoCliente(Long idProveedor, Long idCliente, String tipoNivelIngreso, Long idConfigPrecio ) throws ProquifaNetException;

	/**
	 * @param idProveedor
	 * @param idCliente
	 * @param tipoNivelIngreso
	 * @param idConfigPrecio
	 * @param idProducto
	 * @return
	 * @throws ProquifaNetException
	 */
	public List<ConfiguracionPrecioProducto> findConfiguracionPrecioProductoClientePorProducto(Long idProveedor, Long idCliente, String tipoNivelIngreso, Long idConfigPrecio, Long idProducto) throws ProquifaNetException;

	/***
	 * 
	 * @param idProveedor
	 * @param idCliente
	 * @param tipoNivelIngreso
	 * @param idConfigPrecio
	 * @param idProducto
	 * @param configuracion
	 * @param nivel
	 * @return
	 * @throws ProquifaNetException
	 */
	List<ConfiguracionPrecioProducto> findConfiguracionPrecioProductoClientePorConfiguracion( Long idProveedor, Long idCliente, String tipoNivelIngreso,Long idConfigPrecio, String idProducto, Long configuracion,String nivel) throws ProquifaNetException;

	/***
	 * Verifica si un cliente tiene una configuracion 
	 * @param idConfiguracionPProducto
	 * @param idCliente
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean existConfiguracionCliente(Long idConfiguracionPProducto, Long idCliente, String colNivelConfigPrecio) throws ProquifaNetException;

	/***
	 * Verifica si un cliente tiene una configuracion temporal
	 * @param idConfiguracionPProducto
	 * @param idCliente
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean existConfiguracionClienteTemporal(Long idConfiguracionPProducto, Long idCliente, String colNivelConfigPrecio) throws ProquifaNetException;

	/***
	 * Actualiza la configuracion cliente, pero se��alando que la configuracion es Factoe temporal, Costo Fijo Temporal y que DateTime caduca 
	 * @param p
	 * @param colNivelConfigPrecio
	 * @return
	 * @throws ProquifaNetException
	 */
	Integer updateConfiguracionClienteTemporal( ParametrosOfertaCliente p, String colNivelConfigPrecio, Date caduca) throws ProquifaNetException;

	/***
	 * inserta los valores especificos para un cliente configuraci��n temporal
	 * @param idConfigPrecioProducto
	 * @param idCliente
	 * @param tipoNivelIngreso
	 * @param factor
	 * @param colNivelConfigPrecio
	 * @param nivelConfigPrecio
	 * @param idAgente
	 * @param idLugar
	 * @param idConcepto
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean insertFactorClienteTemporal(ParametrosOfertaCliente parametros, String colNivelConfigPrecio,  Date caducidad) throws ProquifaNetException;

	/***
	 * Usando el idConfigPrecioProducto, se busca el id en el campo del nivelConfigPrecio, con ese id y el idCliente, busca en Cliente_ConfigPrecio y modifica el factor y el nivel de ingreso
	 * @param idConfigPrecioProducto
	 * @param idCliente
	 * @param tipoNivelIngreso
	 * @param factor
	 * @param nivelConfigPrecio
	 * @return
	 * @throws ProquifaNetException
	 */
	Integer updateFactorCliente(ParametrosOfertaCliente parametros, String colNivelConfigPrecio, Long idCliente, String nivelIngreso) throws ProquifaNetException;

	/***
	 * 
	 * @param idCliente
	 * @param ni
	 * @return
	 * @throws ProquifaNetException
	 */
	Cliente obtenerClienteXId(Long idCliente, List<NivelIngreso> ni) throws ProquifaNetException;

	/**
	 * @param idConfigPrecioProd
	 * @param idCliente
	 * @param colCliente
	 * @return
	 * @throws ProquifaNetException
	 */
	Long getClienteConfiguracionPrecio(Long idConfigPrecioProd,Long idCliente,String colCliente) throws ProquifaNetException;

	/**
	 * @param idConfiguracion
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean eliminarRegistroTiempoEntregaRuta(Long idConfiguracion) throws ProquifaNetException;

	/**
	 * @param idConfiguracionPrecio
	 * @return
	 * @throws ProquifaNetException
	 */
	Long getCantidadTiemposEntrega(Long idConfiguracionPrecio,Long idCliente) throws ProquifaNetException;

	/***
	 * inserta los valores especificos para un cliente
	 * @param idConfigPrecioProducto
	 * @param idCliente
	 * @param tipoNivelIngreso
	 * @param factor
	 * @param colNivelConfigPrecio
	 * @param nivelConfigPrecio
	 * @param idAgente
	 * @param idLugar
	 * @param idConcepto
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean insertFactorCliente(ParametrosOfertaCliente parametros, String colNivelConfigPrecio, Long idCliente, String nivelIngreso) throws ProquifaNetException;

	/***
	 * 
	 * @param idProveedor
	 * @param idProducto
	 * @return
	 * @throws ProquifaNetException
	 */
	FormulaPrecio getInformacionFormulaPrecioTemp(Long idProveedor, Long idProducto, int stock_flete, String nivel, Long idCliente, Long idConfig) throws ProquifaNetException;

	/***
	 * 
	 * @param idConfiguracionCosto
	 * @param CategoriaPrecio
	 * @param idCliente
	 * @param tipoNivelIngreso
	 * @return
	 * @throws ProquifaNetException
	 */
	List<ConfiguracionPrecioProducto> getConfiguracionPrecioClienteCosto(Long idConfiguracionCosto,Long CategoriaPrecio, Long idCliente, String tipoNivelIngreso) throws ProquifaNetException;

	/***
	 * 
	 * @param idConfigPrecio
	 * @return
	 * @throws ProquifaNetException
	 */
	List<ConfiguracionPrecioProducto> findConfiguracionesPrecioCostoCliente(Long idConfigFam, Long idCliente, String tipoNivelIngreso) throws ProquifaNetException;

	/**
	 * @param IdConfiguracion
	 * @return
	 * @throws ProquifaNetException
	 */
	List<ConfiguracionPrecioProducto> findConfiguracionClasificacionCliente( Long idConfigFam, Long idCliente, String tipoNivelIngreso, Long idClasificacion) throws ProquifaNetException;

	/***
	 * 
	 * @param idClasificacion
	 * @return
	 * @throws ProquifaNetException
	 */
	List<ConfiguracionPrecioProducto> findClasificacionPrecioProductoPorCatPrecioCliente(Long idClasificacion, String tipoNivelIngreso, Long idCliente) throws ProquifaNetException;

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
	FormulaPrecio getInformacionFormulaPrecioClasificacionTemp(Long idProveedor, Long idProducto, int stock_flete, String nivel, Long idCliente, Long idConfig) throws ProquifaNetException;

	/***
	 * Borrar las configuraciones creadas segun el nivel, cliente, configFamilia
	 * @param columna
	 * @param idCliente
	 * @param idConfigFamilia
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean reintegrarConfiguracionesPorNivel(String columna, Long idCliente, Long idConfigFamilia) throws ProquifaNetException; 
	
	/***
	 * 
	 * @param idCliente
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Direccion> findDireccionesCliente(Long idCliente) throws ProquifaNetException;
	
	/***
	 * 
	 * @param idCliente
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Comentario> findComentariosCliente(Long idCliente) throws ProquifaNetException;
	
	/**
	 * obtiene todas las carteras borradores o clientes.
	 * @param parametros
	 * @return
	 * @throws ProquifaNetException
	 */
	List<CarteraCliente> findCarteras (String parametros, List<NivelIngreso> niveles) throws ProquifaNetException;
	
	/**
	 * 
	 * @return
	 * @throws ProquifaNetException
	 */
	public List<CatalogoItem> obtenerUsoCFDI() throws ProquifaNetException;
	
	/***
	 * 
	 * @return
	 * @throws ProquifaNetException
	 */
	public List<CatalogoItem> obtenerMetodoPago() throws ProquifaNetException;
	
	/***
	 * 
	 * @return
	 * @throws ProquifaNetException
	 */
	public List<CatalogoItem> obtenerClaveUnidad() throws ProquifaNetException;
	
	/***
	 * 
	 * @return
	 * @throws ProquifaNetException
	 */
	public List<CatalogoItem> obtenerClaveProdServ() throws ProquifaNetException;

}
