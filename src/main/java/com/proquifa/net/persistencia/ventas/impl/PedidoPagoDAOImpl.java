/**
 * 
 */
package com.proquifa.net.persistencia.ventas.impl;

import com.proquifa.net.modelo.ventas.PedidoPago;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.ventas.PedidoPagoDAO;
import com.proquifa.net.persistencia.ventas.impl.mapper.PedidoPagoRowMapper;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * @author fmartinez
 *
 */
@Repository
public class PedidoPagoDAOImpl extends DataBaseDAO implements PedidoPagoDAO {

	/* (non-Javadoc)
	 * @see mx.com.proquifa.proquifanet.persistencia.comun.PedidoPagoDAO#obtenerPedidoPago(java.lang.Long)
	 */
	public PedidoPago obtenerPedidoPago(Long idPago) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idPago", idPago);
			String query = "SELECT * FROM PedidoPago WHERE Pago = " + idPago;
			return (PedidoPago) super.jdbcTemplate.queryForObject(query,map, new PedidoPagoRowMapper());
		}catch (RuntimeException rte){
//			new Funcion().enviarCorreoAvisoExepcion(rte,"\nidPago: "+idPago);
//			return new PedidoPago();
			return null;
		}
	}
}
