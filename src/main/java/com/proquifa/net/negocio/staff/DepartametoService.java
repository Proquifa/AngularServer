package com.proquifa.net.negocio.staff;

import java.util.List;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.staff.Departamento;

/**
 * Declaracion del Servicio para obtener los datos de Departamento
 * @author Isaac Garcia
 * @return
 * @throws ProquifaNetException
 */

public interface DepartametoService {

	List<Departamento> getConsultaDepartamento() throws ProquifaNetException;
}
