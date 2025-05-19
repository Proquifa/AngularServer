/**
 * 
 */
package com.proquifa.net.persistencia.consultas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.cobrosypagos.facturista.Factura;
import com.proquifa.net.modelo.despachos.Ruta;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author fmartinez
 *
 */
public class ConsultaGraficasEntregasRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Ruta ruta = new Ruta();
		ruta.setIntentosEntrega(rs.getInt("NumIntentos"));
		ruta.setMotivoClientes(rs.getBoolean("MotivoClientes"));
		ruta.setMotivoMensajeros(rs.getBoolean("MotivosMensajero"));
		ruta.setMotivoSolicitante(rs.getBoolean("MotivoSolicitante"));
		ruta.setMotivoND(rs.getBoolean("MotivosND"));
		ruta.setNumeroPartidasRuta(rs.getInt("NumeroPartidas"));
		
		Factura fac = new Factura();
		fac.setMontoFacturaDLS(Math.rint((rs.getDouble("TotalDLS")) * 1e2) / 1e2);
		fac.setNumeroPiezasFactura(rs.getInt("PiezasFactura"));
		fac.setNombre_Cliente(rs.getString("Cliente"));
		fac.setRutaRelacionada(ruta);

		return fac;
	}
}