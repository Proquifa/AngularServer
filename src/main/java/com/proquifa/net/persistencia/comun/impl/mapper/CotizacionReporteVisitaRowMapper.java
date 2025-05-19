package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.Contacto;
import com.proquifa.net.modelo.ventas.Cotizacion;

import org.springframework.jdbc.core.RowMapper;

public class CotizacionReporteVisitaRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		Cotizacion cot = new Cotizacion();
		
		cot.setNombreCotizacion(rs.getString("Nombre"));
		cot.setFolioCotizacion(rs.getString("Clave"));
		cot.setIdCotizacion(rs.getLong("PK_Folio"));
		cot.setVendedor(rs.getString("Vendedor"));
		cot.setMoneda(rs.getString("Moneda"));
		cot.setParciales(rs.getString("Parciales"));
		cot.setCpago(rs.getString("CPago"));
		cot.setZona(rs.getString("Zona"));
		cot.setEstado(rs.getString("Estado"));
		cot.setFEnvio(rs.getDate("FEnvio"));
		cot.setImoneda(rs.getString("IMoneda"));
		cot.setCotizo(rs.getString("Cotizo"));
		cot.setFactura(rs.getString("Factura"));
		cot.setHEntrada(rs.getString("HEntrada"));
		cot.setMEntrada(rs.getString("MEntrada"));
		cot.setHSalida(rs.getString("HSalida"));
		cot.setMSalida(rs.getString("MSalida"));
		cot.setIdContacto(rs.getLong("idContacto"));
		cot.setInfoFacturacion(rs.getBoolean("InfoFacturacion"));
		cot.setFuersaSistema(rs.getBoolean("FS"));
		cot.setGravaIva(rs.getBoolean("GravaIVA"));
		cot.setDoctoR(rs.getInt("FK02_DoctosR"));
		cot.setIdVisita(rs.getLong("FK03_idVisita"));
		cot.setGenerada(rs.getBoolean("Generada"));
		cot.setTipoCotizacion(rs.getString("Tipo"));
		cot.setNumCotizacion(rs.getLong("Orden"));
		cot.setDeSistema(rs.getBoolean("DeSistema"));
		cot.setNombreCotizacion(rs.getString("Nombre"));
		cot.setIdContacto(rs.getLong("idContacto"));
		
		Contacto contacto = new Contacto();
		contacto.setIdContacto(rs.getLong("idContacto"));
		cot.setContactos(contacto);
		
		Cliente cli = new Cliente();
		cli.setIdCliente(rs.getLong("FK01_idCliente"));
		cot.setCliente(cli);
		
		return cot;
	}

}

