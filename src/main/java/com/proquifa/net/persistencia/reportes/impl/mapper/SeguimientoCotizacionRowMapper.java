package com.proquifa.net.persistencia.reportes.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.proquifa.net.modelo.ventas.reportes.seguimientos.SeguimientoCotizacion;

import org.springframework.jdbc.core.RowMapper;

public class SeguimientoCotizacionRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {		
		SeguimientoCotizacion sc = new SeguimientoCotizacion();
		sc.setTipoSeguimiento(rs.getString("Tipo"));
		sc.setFolioCotizacion(rs.getString("Cotiza"));
		sc.setNombreContacto(rs.getString("Contacto"));
		sc.setMonedaCotiza(rs.getString("IMoneda"));
		Date fecha= rs.getDate("Fecha");
		sc.setFechaCotizacion(fecha);
		String status = rs.getString("SegEstatus");
		if(status!=null){
			sc.setEstadoSeguimiento(status);
		}else{
			sc.setEstadoSeguimiento("Por Realizar");
		}
		sc.setNombreCliente(rs.getString("Cliente"));
		sc.setNombreVendedor(rs.getString("Vendedor"));
		sc.setPiezas(rs.getLong("Piezas"));
		sc.setMontoCotizacion(rs.getDouble("Monto"));
		sc.setPartidas(rs.getLong("Partidas"));
		sc.setNivelIngreso(rs.getString("NIVEL"));
		sc.setMarca(rs.getString("Fabrica"));
		sc.setTipoProduct(rs.getString("TipoPro"));
		sc.setControl(rs.getString("CONTROL"));
		sc.setFolio(rs.getString("Folio"));
		sc.setMaster(rs.getString("MASTER"));
		
		if (rs.getString("FechaSiguiente") != null){
			sc.setFechaSiguienteContacto(rs.getDate("FechaSiguiente"));
		}
		sc.setDiasAtraso(rs.getLong("DiasAtraso"));
		sc.setEnTiempo(rs.getString("EnTiempo"));

		String ejecutivoAsignado = rs.getString("EV");
		if(rs.wasNull()){
			sc.setNombreEV("");
		}else{
			sc.setNombreEV(ejecutivoAsignado);
		}
		
		ejecutivoAsignado = rs.getString("EVT");
		if(rs.wasNull()){
			sc.setNombreEVT("");
		}else{
			sc.setNombreEVT(ejecutivoAsignado);
		}
		
		

		return sc;
	}
}