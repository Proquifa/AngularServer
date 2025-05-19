/**
 * 
 */
package com.proquifa.net.persistencia.ventas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.ventas.Cotizacion;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author fmartinez
 *
 */
public class ReporteCotizacionRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Cotizacion datos = new Cotizacion();
		Long folioDocotR = null;
		
		datos.setFecha(rs.getDate("Fecha"));
		datos.setNombreCliente(rs.getString("Cliente"));
		datos.setContacto(rs.getString("Contacto"));
		datos.setCotizo(rs.getString("Vendedor"));
		datos.setFolioCotizacion(rs.getString("Clave"));
		datos.setMontoCotiza(rs.getDouble("Monto"));
		datos.setMontoDLSCotiza(rs.getDouble("MontoUSD"));
		datos.setMoneda(rs.getString("Moneda"));
		datos.setCpago(rs.getString("CPago"));
		folioDocotR = rs.getLong("Requisicion");
		if(rs.wasNull()){
			datos.setRequisicion(null);
		}else{
			datos.setRequisicion(folioDocotR);
		}

		datos.setEstado(rs.getString("Estado"));
		datos.setMSalida(rs.getString("MSalida"));
		datos.setPendienteOrigen(rs.getString("PendienteOrigen"));
		datos.setFechaRegistro(rs.getTimestamp("FechaRegistro"));
		datos.setFechaOrigen(rs.getTimestamp("FHOrigen"));
		
		if(!(rs.getString("ET_A") ==null) && !(rs.getString("ET_B") ==null)){
			
			if(!rs.getBoolean("ET_A"))
				datos.setEnTiempoFueraDeTiempo(false);
			else
				datos.setEnTiempoFueraDeTiempo(rs.getBoolean("ET_B"));
		}else if(rs.getString("ET_B")==null)
				  datos.setEnTiempoFueraDeTiempo(rs.getBoolean("ET_A"));
			  else
				  datos.setEnTiempoFueraDeTiempo(rs.getBoolean("ET_B"));
			
		return datos;
	}
}