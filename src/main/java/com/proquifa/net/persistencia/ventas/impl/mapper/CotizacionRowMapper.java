package com.proquifa.net.persistencia.ventas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.proquifa.net.modelo.ventas.Cotizacion;
import org.springframework.jdbc.core.RowMapper;

public class CotizacionRowMapper implements RowMapper {
	
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Cotizacion cotiza = new Cotizacion();
		cotiza.setIdCotizacion(rs.getLong("PK_Folio"));
		cotiza.setFolioCotizacion(rs.getString("clave"));
		cotiza.setFecha(rs.getDate("Fecha"));
		cotiza.setNombreCliente(rs.getString("Cliente"));
		cotiza.setContacto(rs.getString("Contacto"));
		cotiza.setVendedor(rs.getString("Vendedor"));
		cotiza.setVigencia(rs.getString("Vigencia"));
		cotiza.setMoneda(rs.getString("Moneda"));
		cotiza.setParciales(rs.getString("Parciales"));
		cotiza.setCpago(rs.getString("CPago"));
		cotiza.setLugar(rs.getString("Lugar"));
		cotiza.setZona(rs.getString("Zona"));
		cotiza.setEstado(rs.getString("Estado"));	
		cotiza.setFEnvio(rs.getDate("FEnvio"));
		cotiza.setObserva(rs.getString("Observa"));
		cotiza.setImoneda(rs.getString("IMoneda"));
		cotiza.setCotizo(rs.getString("Cotizo"));
		cotiza.setFactura(rs.getString("Factura"));
		cotiza.setMSalida(rs.getString("MSalida"));
		cotiza.setHEntrada(rs.getString("HEntrada"));
		cotiza.setMEntrada(rs.getString("MEntrada"));
		cotiza.setHSalida(rs.getString("HSalida"));
		cotiza.setMSalida(rs.getString("MSalida"));
		cotiza.setConfirmo(rs.getString("Confirmo"));
		cotiza.setObservaC(rs.getString("ObservaC"));
		cotiza.setFechaClasificacion(rs.getDate("FechaClasif"));
		cotiza.setIdContacto(rs.getLong("idContacto"));
		cotiza.setCanceladaDesde(rs.getString("CanceladaDesde"));
		cotiza.setInfoFacturacion(rs.getBoolean("InfoFacturacion"));
		
		return cotiza;
	}
}
