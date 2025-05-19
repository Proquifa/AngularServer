package com.proquifa.net.persistencia.comun;
import java.util.List;

import com.proquifa.net.modelo.comun.Fabricante;

public interface FabricanteDAO {
	
	/**
	 * Metodo que obtiene todos los registros de la tabla fabricante
	 * @return
	 */
	List<Fabricante> consultarTodos();
	/**
	 * Metodo que obtiene los fabricantes habilitados de la base de datos
	 * @return
	 */
	List<Fabricante> consultarHabilitados(Long idProveedor);
	/**
	 * Metodo que obtiene un fabricante mediante su id
	 * @param idFabricante
	 * @return
	 */
	Fabricante obtenerPorId(Long idFabricante);
	/***
	 * actualiza la informacion del fabricante
	 * @param f
	 * @return
	 */
	Boolean updateFabricante(Fabricante f);
	/**
	 * obtine un fabriante por su nombre
	 * @param nombre
	 * @return
	 */
	public Fabricante getFabricantePorNombre(String nombre);
}