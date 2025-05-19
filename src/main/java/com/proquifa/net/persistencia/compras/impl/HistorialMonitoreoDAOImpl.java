/**
 * 
 */
package com.proquifa.net.persistencia.compras.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.proquifa.net.persistencia.DataBaseDAO;

import com.proquifa.net.modelo.compras.HistorialMonitoreo;
import com.proquifa.net.persistencia.compras.HistorialMonitoreoDAO;
import com.proquifa.net.persistencia.compras.impl.mapper.HistorialMonitoreoRowMapper;

/**
 * @author vromero
 *
 */
@Repository
public class HistorialMonitoreoDAOImpl extends DataBaseDAO implements HistorialMonitoreoDAO {

	@SuppressWarnings("unchecked")
	public List<HistorialMonitoreo> obteberHistorialMonitoreo(
			String ordenCompra, Integer partida) {
		
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ordenCompra", ordenCompra);
			map.put("partida", partida);
			
			String query = 	"SELECT Origen,Fecha,Gestor,Tipo,Comentarios FROM PCompraHistorial" +
							" WHERE idCompra = '"+ordenCompra+"' AND idPCompra ="+partida+"  order by Fecha Desc";
			
			return super.jdbcTemplate.query(query, map, new HistorialMonitoreoRowMapper());
		}catch(RuntimeException e) {
			return null;
		}

	}

}
