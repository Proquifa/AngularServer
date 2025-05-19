package com.proquifa.net.persistencia.comun;

import java.util.List;


import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.Corporativo;
import com.proquifa.net.modelo.comun.Industria;
import com.proquifa.net.modelo.comun.NivelIngreso;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;

public interface CorporativoDAO {

	/**
	 * @param corporativo
	 * @return
	 * @throws ProquifaNetException
	 */
	Long insertarCorporativo(Corporativo corporativo) throws ProquifaNetException;
	
	/**
	 * @param corporativo
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean updateCorporativo(Corporativo corporativo) throws ProquifaNetException;
	
	/**
	 * @param idCorporativo
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Cliente> obtenerClientesXCorporativo(Long idCorporativo) throws ProquifaNetException;
	
	/**
	 * @param idCorporativo
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean eliminarCorporativo(Long idCorporativo) throws ProquifaNetException;
	
	/**
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Corporativo> obtenerCorporativos(List<NivelIngreso> niveles) throws ProquifaNetException;
	/**
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Cliente> obtenerClientesPorCorporativo(Long corporativo,List<NivelIngreso> niveles) throws ProquifaNetException;
	/***
	 * obtiene las insdustrias asociadas a un corporativo
	 * @param idCorporativo
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Industria> findIndustriasCorporativo (Long idCorporativo) throws ProquifaNetException;

	/**
	 * Inserta un registro en la tabla de modificacion, al insertar o modificar un corporativo. guardando el id del usuario el lugar donde se hizo el llamado de servicio y los cambios que sufrio el corporativo.
	 * @param usuario
	 * @param idCorporativo
	 * @param modificaciones
	 * @param llamadoPor
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean insertarModificacionesCorporativo (Long usuario , Long idCorporativo , String modificaciones , String llamadoPor )throws ProquifaNetException;
	/***
	 * 
	 * @param idCliente
	 * @return
	 * @throws ProquifaNetException
	 */
	Long getCorporativoPorCliente(Long idCliente) throws ProquifaNetException;
}
