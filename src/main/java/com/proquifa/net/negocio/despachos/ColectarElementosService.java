package com.proquifa.net.negocio.despachos;

import java.util.List;

import com.proquifa.net.modelo.despachos.EmbalarPedido;

public interface ColectarElementosService {

	/**
	 * 
	 * @param idUsuario
	 * @return
	 */
	List<EmbalarPedido> obtenerElementosColectar(String idUsuario);

}
