/**
 * 
 */
package com.proquifa.net.persistencia.despachos.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.despachos.DespachosDAO;

/**
 * @author ymendez
 *
 */
@Repository
public class DespachosDAOImpl extends DataBaseDAO implements DespachosDAO {

	/* (non-Javadoc)
	 * @see com.proquifa.net.persistencia.despachos.DespachosDAO#guardarHistorialPrioridades()
	 */
	@Override
	public void guardarHistorialPrioridades() throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append(" \n");
			
			sbQuery.append("INSERT INTO HistorialPrioridades(Fecha, FK01_PPedido, FK02_PCompra, Prioridad, Estado, Puntos) \n");
			sbQuery.append("SELECT GETDATE(), idPPedido, idPcompra, Prioridad, Estado, Puntos FROM PartidadeCompraenInspeccion \n");
			sbQuery.append("WHERE Estado = 'En inspección' \n");
			
			sbQuery.append("INSERT INTO HistorialPrioridades(Fecha, FK01_PPedido, FK02_PCompra, Prioridad, Estado, Puntos) \n");
			sbQuery.append("SELECT GETDATE(), idPPedido, idPcompra, Prioridad, Estado, Puntos FROM PartidaCompraPorEmbalar \n");
			
			Map<String, Object> map = new HashMap<String, Object>();
			jdbcTemplate.update(sbQuery.toString(), map);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}

}
