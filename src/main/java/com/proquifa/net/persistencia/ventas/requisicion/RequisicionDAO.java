/**
 * 
 */
package com.proquifa.net.persistencia.ventas.requisicion;

import java.util.List;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.ventas.requisicion.PRequisicion;
import com.proquifa.net.modelo.ventas.requisicion.Requisicion;

/**
 * @author fmartinez
 *
 */
public interface RequisicionDAO {
	
	/***
	 * Hace un filtrado de todas las requisiciones 
	 * @param idEmpleado
	 * @return
	 */
	List<Requisicion> finRequisiciones(Long idEmpleado);
	/***
	 * Insertar documento recibido en la tabla doctoR para una requisicion
	 * @param List<Prequisicion> prequisicion
	 * @return Boolean true or false
	 * @throws ProquifaNetException
	 */
	Integer insertaDoctoR(Requisicion requisicion) throws ProquifaNetException;
	/***
	 * Insertar requisicion movil
	 * @param Requisicion requisicion
	 * @return Boolean true or false
	 * @throws ProquifaNetException
	 */
	Boolean insertarRequisicionMovil(Requisicion requisicion) throws ProquifaNetException;
	/***
	 * Actualizar requisicion movil
	 * @param Requisicion requisicion
	 * @return Boolean true or false
	 * @throws ProquifaNetException
	 */
	Boolean actualizaRequisicionMovil(Requisicion requisicion);
	/***
	 * Insertar Las partidas de la requisicion movil
	 * @param List<Prequisicion> prequisicion
	 * @return Boolean true or false
	 * @throws ProquifaNetException
	 */
	Boolean insertarPrequisicionMovil(PRequisicion prequisicion);
}