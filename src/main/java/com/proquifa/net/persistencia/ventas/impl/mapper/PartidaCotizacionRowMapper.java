/**
 * 
 */
package com.proquifa.net.persistencia.ventas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.proquifa.net.modelo.ventas.PartidaCotizacion;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author ernestogonzalezlozada
 *
 */
public class PartidaCotizacionRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		PartidaCotizacion partidaCotizacion = new PartidaCotizacion();
		String hEnvioS = "";
		DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		partidaCotizacion.setIdPartidaCotizacion(rs.getLong("idPCotiza"));
		partidaCotizacion.setCotizacion(rs.getString("Clave"));
		partidaCotizacion.setPartida(rs.getLong("Partida"));
		partidaCotizacion.setCantidad(rs.getInt("Cant"));
		partidaCotizacion.setCodigo(rs.getString("Codigo"));
		partidaCotizacion.setPrecio(rs.getFloat("Precio"));
		partidaCotizacion.setEstado(rs.getString("Estado"));
		partidaCotizacion.setIva(rs.getDouble("IVA"));
		partidaCotizacion.setCosto(rs.getFloat("Costo"));
		partidaCotizacion.setFabrica(rs.getString("Fabrica"));
		partidaCotizacion.setNota(rs.getString("Nota"));
		partidaCotizacion.setClasificacion(rs.getString("Clasif"));
		partidaCotizacion.setDestino(rs.getString("Destino"));
		hEnvioS = rs.getString("HEnvio");
		Date fecha = null;
		if(hEnvioS != null){			
			try {
				fecha = formatoFecha.parse(hEnvioS);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		partidaCotizacion.setHoraEnvio(fecha);
		partidaCotizacion.setMedioEnvio(rs.getString("MEnvio"));
		partidaCotizacion.setFolio(rs.getLong("Folio"));
		partidaCotizacion.setObservaE(rs.getString("ObservaE"));
		partidaCotizacion.setHCancelacion(rs.getDate("HCancelacion"));
		partidaCotizacion.setFechaGeneracion(rs.getDate("FGeneracion"));
		partidaCotizacion.setIndicePrecio(rs.getLong("IndicePrecio"));
		partidaCotizacion.setPresentacion(rs.getString("Presentacion"));
		partidaCotizacion.setUnidades(rs.getString("Unidades"));
		partidaCotizacion.setTiempoEntrega(rs.getString("TEntrega"));
		partidaCotizacion.setFueraSistema(rs.getBoolean("FS"));
		partidaCotizacion.setPrecioInvestigacion(rs.getFloat("PrecioI"));
		partidaCotizacion.setNotaCancelacion(rs.getString("NotasCancelacion"));
		partidaCotizacion.setConcepto(rs.getString("Concepto"));
		partidaCotizacion.setImporte(partidaCotizacion.getPrecio()*partidaCotizacion.getCantidad());
		
		return partidaCotizacion;
	}

}
