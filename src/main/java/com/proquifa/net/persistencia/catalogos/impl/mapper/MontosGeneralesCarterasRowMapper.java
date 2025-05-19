/**
 * 
 */
package com.proquifa.net.persistencia.catalogos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.CarteraCliente;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author bryan.magana
 *
 */
public class MontosGeneralesCarterasRowMapper implements RowMapper {
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		CarteraCliente c = new CarteraCliente () ;
		
		c.setIdCartera(rs.getLong("idCartera"));
		
		c.setCart_publicada(rs.getBoolean("publicada"));
		
		c.setImportancia(rs.getInt("importancia"));
		c.setDificultad(rs.getInt("dificultad"));
		
		c.setCart_debemos(rs.getDouble("debemos"));
		c.setCart_deben(rs.getDouble("debe"));
		c.setCart_facturacionAct(rs.getDouble("cart_MontoActual"));
		c.setCart_facturacionAnt(rs.getDouble("cart_MontoAnterior"));
		c.setCart_promedioFacturacion(rs.getDouble("cart_PF"));
		c.setCart_proyeccionVenta(rs.getDouble("cart_pv"));
		c.setCart_objetivoDeseado(rs.getDouble("ObjetivoCrecimiento"));
		c.setCart_objetivoFundamental(rs.getDouble("objetivoCrecimientoFundamental"));
		c.setCart_montoDeseado(rs.getDouble("monto_D"));
		c.setCart_montoFundamental(rs.getDouble("monto_F"));
		
		return c;
	}
}
