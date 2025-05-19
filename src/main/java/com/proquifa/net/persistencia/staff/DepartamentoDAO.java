package com.proquifa.net.persistencia.staff;

import java.util.List;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.staff.Departamento;

/**
 * Delcaracion del DAO de Departamento
 * @author Isaac Garcia
 *
 */
public interface DepartamentoDAO {

	List<Departamento> consultaDepartamento() throws ProquifaNetException;
	
	public List<Departamento> consultaDepartamentoRyndem();
	
	public List<Departamento> consultaDepartamentoGDL();
}
