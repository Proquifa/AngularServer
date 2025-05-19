package com.proquifa.net.negocio.staff;

import java.util.List;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.staff.Categoria;

/**
 * Declaracion del Servicio para obtener los datos de Categoria
 * @author Isaac Garcia
 * @return
 * @throws ProquifaNetException
 */

public interface CategoriaService {

	List<Categoria> getConsultaCategoria() throws ProquifaNetException;
}
