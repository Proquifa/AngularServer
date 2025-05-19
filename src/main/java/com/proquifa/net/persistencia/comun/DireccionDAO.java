package com.proquifa.net.persistencia.comun;

import java.util.List;


import com.proquifa.net.modelo.comun.Direccion;
import com.proquifa.net.modelo.comun.Horario;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;

public interface DireccionDAO {
	
	/**
	 * Obtiene la direccion de Recoleccion del Proveedor
	 * @param idProveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	Direccion getDireccionRecoleccion(Long idProveedor) throws ProquifaNetException;
	/**
	 * Inserta en Direccion
	 * @param d
	 * @return
	 * @throws ProquifaNetException
	 */
	Long insertDireccion(Direccion d) throws ProquifaNetException ;
	
	/**
	 * Actializa la direccion 
	 * @param d
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean updateDireccion(Direccion d) throws ProquifaNetException;
	
	/**
	 * Elimina la direccion
	 * @param idDireccion
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean deleteDireccion(Long idDireccion) throws ProquifaNetException;
	
	/**
	 * 
	 * @param horario
	 * @return
	 * @throws ProquifaNetException
	 */
	Long insertHorario(Horario horario) throws ProquifaNetException;
	
	/**
	 * 
	 * @param horario
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean updateHorario(Horario horario) throws ProquifaNetException;
	
	/**
	 * Elimina el Horario
	 * @param idHorario
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean deleteHorario(Long idHorario) throws ProquifaNetException;
	
	/**
	 * Elimina todos los horarios de la Direccion especificada
	 * @param idDireccion
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean deleteHorariosDireccion(Long idDireccion) throws ProquifaNetException;
	
	/**
	 * Obtiene los horarios
	 * @param idDireccion
	 * @param tipo
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Horario> getHorario(Long idDireccion, String tipo) throws ProquifaNetException;
	 /**
	  * Obtiene los horarios segun idCliente con o sin tipo
	  * @param idCliente 
	  * @param tipo Puede ser null si no se requiere algun tipo como entrega o revision
	  * @return
	  * @throws ProquifaNetException
	  */
	List<Direccion> getHorarioyLugarXidClienteYTipo(Long idCliente, String tipo) throws ProquifaNetException;
}
