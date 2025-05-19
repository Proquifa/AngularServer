/**
 * 
 */
package com.proquifa.net.negocio.ventas.requisicion;

import java.util.List;


import com.proquifa.net.modelo.comun.Contacto;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.ventas.requisicion.PRequisicion;
import com.proquifa.net.modelo.ventas.requisicion.Requisicion;

/**
 * @author fmartinez
 *
 */
public interface RequisicionService {
	/***
	 * Obtener las requisiciones por cotizar.
	 * @param idEmpleado
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Requisicion> filtrarRequisiciones(Long idEmpleado) throws ProquifaNetException;
	/***
	 * Insertar requisicion movil
	 * @param Requisicion requisicion
	 * @return Boolean true or false
	 * @throws ProquifaNetException
	 */
	String insertarRequisicionMovil(Requisicion requisicion, List<PRequisicion> prequisicion, Contacto contacto) throws ProquifaNetException;

	String actualizaRequisicionMovil(Requisicion requisicion, List<PRequisicion> prequisicion, Contacto contacto) throws ProquifaNetException;

}