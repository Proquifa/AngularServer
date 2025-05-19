package com.proquifa.net.persistencia.staff;

import java.util.List;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.staff.Categoria;

/**
 * 
 * @author Isaac Garcia
 *
 */
public interface CategoriaDAO {
	
	List<Categoria> consultaCategoria() throws ProquifaNetException;
	
	public List<Categoria> consultaCategoriaRyndem();
	
	public List<Categoria> consultaCategoriaGDL();

}
