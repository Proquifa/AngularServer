package com.proquifa.net.persistencia.despachos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;



import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.despachos.PartidaInspeccion;

public class PartidaInspeccionxPieza implements RowMapper {
	
	private PartidaInspeccion part;

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		this.part = new PartidaInspeccion();
		
		try {
		part.setTotalPiezas(rs.getLong("Piezas"));
		} catch (Exception e) {
		}
		
		try {
			part.setTiempoInspeccionEnSegundos(rs.getLong("Tiempo_Inspeccion"));

		} catch (Exception e) {
		}

		try {
			part.setTipo(rs.getString("Tipo_Producto"));

		} catch (Exception e) {
		}

		try {
			part.setFechaEstadisticas(rs.getString("Fecha"));

		} catch (Exception e) {
		}

		try {
			part.setPrioridad_p(rs.getInt("Prioridad"));

		} catch (Exception e) {
		}
		
		
		try{
			part.setIdOrdenEntrega(rs.getLong("ordenEntrega"));
		}catch (Exception e) {
		}
		
		try {
			part.setTiempoEmbalajeEnSegundos(rs.getLong("Tiempo_embalaje"));

		} catch (Exception e) {
		}


		
		return part;
	}
	
	/**
	 * @return the partidaInspeccion
	 */
	public PartidaInspeccion getPartidaIns() {
		return part;
	}
}

