/**
 * 
 */
package com.proquifa.net.persistencia.despachos.mensajero.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.despachos.mensajero.AsignarMensajero;

/**
 * @author ymendez
 *
 */
public class AsignarMensajeroDetalleRowMapper implements RowMapper<AsignarMensajero> {

	@Override
	public AsignarMensajero mapRow(ResultSet rs, int rowNum) throws SQLException {
		AsignarMensajero mensajero = new AsignarMensajero();
		
		mensajero.setIdAsignarMensajero(rs.getInt("ID"));
		mensajero.setAltitud(rs.getDouble("Altitud"));
		mensajero.setCalle(rs.getString("Calle"));
		mensajero.setCliente(rs.getString("Cliente"));
		mensajero.setDia(rs.getString("Dia"));
		mensajero.setEstado(rs.getString("Estado"));
		mensajero.setFolio(rs.getString("Folio"));
		mensajero.setHorarioA(rs.getString("A"));
		mensajero.setHorarioDe(rs.getString("De"));
		mensajero.setIdCliente(rs.getInt("IdCliente"));
		mensajero.setIdMensajero(rs.getInt("IdMensajero"));
		mensajero.setLatitud(rs.getDouble("Latitud"));
		mensajero.setLongitud(rs.getDouble("Longitud"));
		mensajero.setMensajero(rs.getString("Nombre"));
		mensajero.setPais(rs.getString("Pais"));
		mensajero.setUsuario(rs.getString("Usuario"));
		mensajero.setZona(rs.getString("Zona"));
		mensajero.setEvento(rs.getString("Evento"));
		mensajero.setOrden(rs.getInt("Orden"));
		mensajero.setMonto(rs.getDouble("Monto"));
		mensajero.setPrioridad(rs.getString("Prioridad"));
		
		try {
			mensajero.setIdDP(rs.getString("idDP"));
		} catch (Exception e) {
		}
		
		try {
			mensajero.setDias(rs.getString("Dias").trim().replaceAll(" ", " · "));
			mensajero.setActivo(rs.getBoolean("Activo"));
		} catch (Exception e) {
		}
		try {
			mensajero.setFee(rs.getString("fee"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return mensajero;
	}

}
