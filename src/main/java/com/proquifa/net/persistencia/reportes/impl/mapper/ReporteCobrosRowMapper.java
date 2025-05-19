/**
 * 
 */
package com.proquifa.net.persistencia.reportes.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.cobrosypagos.Cobros;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author jmcamanos
 *
 */
public class ReporteCobrosRowMapper implements RowMapper{

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		Cobros cobros=new Cobros();
		cobros.setCpedido(rs.getString("CPEDIDO"));
		cobros.setIdFactura(rs.getLong("IDFACTURA"));
		cobros.setFactura(rs.getLong("FACTURA"));
		cobros.setFechaFacturacion(rs.getTimestamp("FECHAFACTURACION"));
		cobros.setFechaEsperadaPago(rs.getTimestamp("FEPAGO"));
		cobros.setCpago(rs.getString("CPAGO"));
		cobros.setPiezas(rs.getLong("PIEZAS"));
		cobros.setEsac(rs.getString("ESAC"));
		cobros.setEv(rs.getString("EV"));
		cobros.setNombreCliente(rs.getString("NOMBRECLIENTE"));
		cobros.setEstado(rs.getString("ESTADO"));
		cobros.setDiasRestantesCobro(rs.getInt("DRC"));
		cobros.setMontoDolares(Math.rint((rs.getDouble("CONVERSIONUSD")) * 1e2) / 1e2);
		cobros.setMedioPago(rs.getString("MPAGO"));
		cobros.setFpor(rs.getString("FPOR"));
		cobros.setFechaRevision(rs.getTimestamp("FECHA_REVISION"));
		cobros.setFechaProgramacion(rs.getTimestamp("FECHA_PROGRAMACION"));
		cobros.setMoroso(rs.getString("Moroso")); 
		cobros.setNivelIngreso(rs.getString("nivel"));
		cobros.setPartidas(rs.getLong("Partidas"));
		cobros.setCobrador(rs.getString("Cobrador"));
		return cobros;
	}

}
