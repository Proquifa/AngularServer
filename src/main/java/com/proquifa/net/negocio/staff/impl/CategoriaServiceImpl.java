package com.proquifa.net.negocio.staff.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.staff.Categoria;
import com.proquifa.net.negocio.staff.CategoriaService;
import com.proquifa.net.persistencia.staff.CategoriaDAO;

@Service("categoriaService")
public class CategoriaServiceImpl implements CategoriaService{
	
	@Autowired
	CategoriaDAO categoriaDAO;
	
	public List<Categoria> getConsultaCategoria() throws ProquifaNetException{
		
		List<Categoria> categorias = new ArrayList<Categoria>();
		
		List<Categoria> categoriasProquifa = null;
		List<Categoria> categoriasRyndem = null;
		List<Categoria> categoriasGDL = null;
		
		try {
			categoriasProquifa = this.categoriaDAO.consultaCategoria();
			categoriasRyndem = this.categoriaDAO.consultaCategoriaRyndem();
			categoriasGDL = this.categoriaDAO.consultaCategoriaGDL();
		} catch (Exception e) {}
		
		
		if (categoriasProquifa != null && categoriasProquifa.size() > 0) {
			categorias.addAll(categoriasProquifa);
		}
		
		if (categoriasRyndem != null && categoriasRyndem.size() > 0) {
			for (Categoria categoriaRyndem : categoriasRyndem) {
				boolean repetido = false;
				for (Categoria categoria : categorias) {
					if (categoriaRyndem.getClaveCategoria().equals(categoria.getClaveCategoria())
							&& categoriaRyndem.getNombreCategoria().equals(categoria.getNombreCategoria())) {
						repetido = true;
						
					}
				}
				if (!repetido) {
					categorias.add(categoriaRyndem);
				}
			}
		}
		
		if (categoriasGDL != null && categoriasGDL.size() > 0) {
			for (Categoria categoriaGDL : categoriasGDL) {
				boolean repetido = false;
				for (Categoria categoria : categorias) {
					if (categoriaGDL.getClaveCategoria().equals(categoria.getClaveCategoria())
							&& categoriaGDL.getNombreCategoria().equals(categoria.getNombreCategoria())) {
						repetido = true;
						
					}
				}
				if (!repetido) {
					categorias.add(categoriaGDL);
				}
			}
		}
		
		
		
		
		
		return this.categoriaDAO.consultaCategoria();
	}

}
