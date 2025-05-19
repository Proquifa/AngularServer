package com.proquifa.net.persistencia.despachos;

import java.util.List;

import com.proquifa.net.modelo.despachos.EmbalarPedido;

public interface ColectarElementosDAO {

	/**
	 * 
	 * @param idUsuario
	 * @return
	 */
	List<EmbalarPedido> obtenerElementosColectar(String idUsuario);

}
