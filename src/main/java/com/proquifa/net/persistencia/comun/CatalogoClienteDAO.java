package com.proquifa.net.persistencia.comun;

import java.util.List;

import com.proquifa.net.modelo.comun.Cartera;
import com.proquifa.net.modelo.comun.CarteraCliente;
import com.proquifa.net.modelo.comun.NivelIngreso;
import com.proquifa.net.modelo.comun.ValorCombo;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;

public interface CatalogoClienteDAO {

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
	
	Boolean actualizarMensajeroDeClientes(List<CarteraCliente> clientes, long idMensajero);
}
