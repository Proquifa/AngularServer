package com.proquifa.net.persistencia.staff.impl.mapper;

import java.util.ArrayList;
import java.util.List;

import com.proquifa.net.modelo.staff.Categoria;

public class CategoriaHibernateRowMapper {

	public static List<Categoria> mapearCategoia(List<Object[]> list){
		List<Categoria> categorias = new ArrayList<Categoria>();
		for (Object[] object : list){
			Categoria categoria = new Categoria();
			categoria.setClaveCategoria(object[0].toString());
			categoria.setNombreCategoria(object[1].toString());
			categorias.add(categoria);
		}
		return categorias;
	}
	
}
