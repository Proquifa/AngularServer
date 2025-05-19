package com.proquifa.net.persistencia.despachos.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.despachos.EmbalarPedido;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.despachos.ColectarElementosDAO;
import com.proquifa.net.persistencia.despachos.impl.mapper.ColectarElementosRowMapper;

@Repository
public class ColectarElementosDAOImpl extends DataBaseDAO implements ColectarElementosDAO{
	
	final Logger log = LoggerFactory.getLogger(ColectarElementosDAOImpl.class);
	
	@Override
	public List<EmbalarPedido> obtenerElementosColectar(String idUsuario) {
		List<EmbalarPedido> embalarPedido = new ArrayList<EmbalarPedido>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idUsuario", idUsuario);
			
			String query = "SELECT pce.*, ep.FK03_UsuarioEmbalar FROM PartidaCompraPorEmbalar pce "
					+ " LEFT JOIN EmbalarPedido ep ON ep.FK01_PPedido = pce.idPPedido"
					+ " WHERE pce.ESTADO = 'Por Colectar' AND ep.FK03_UsuarioEmbalar = :idUsuario";
			
			embalarPedido = (List<EmbalarPedido>)super.jdbcTemplate.query(query, map, new ColectarElementosRowMapper());  
			
			return embalarPedido;
		} catch (Exception e) {
			log.info("Error: "+ e.getMessage());
			return null;
		}
	}
	
}
