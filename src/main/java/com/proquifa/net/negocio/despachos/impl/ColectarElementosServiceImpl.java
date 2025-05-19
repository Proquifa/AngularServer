package com.proquifa.net.negocio.despachos.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proquifa.net.modelo.despachos.EmbalarPedido;
import com.proquifa.net.negocio.despachos.ColectarElementosService;
import com.proquifa.net.persistencia.despachos.ColectarElementosDAO;

@Service("colectarElementosService")
public class ColectarElementosServiceImpl implements ColectarElementosService{
	
	@Autowired
	ColectarElementosDAO colectarElementosDAO;
	
	@Override
	public List<EmbalarPedido> obtenerElementosColectar(String idUsuario) {
		
		return colectarElementosDAO.obtenerElementosColectar(idUsuario);
	}
	
}
