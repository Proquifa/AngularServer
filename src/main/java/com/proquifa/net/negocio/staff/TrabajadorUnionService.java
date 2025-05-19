package com.proquifa.net.negocio.staff;

import java.util.List;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.staff.Trabajador;

/**
 * Declaracion del Servicio para obtener los datos de Trabajador
 * de ambas base de datos (Proquifa t Rymdem)
 * @author Isaac Garcia
 *
 */
public interface TrabajadorUnionService {

	List<Trabajador>getTrabajadorUnion() throws ProquifaNetException;
}
