package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.cobrosypagos.facturista.AsignacionFolio;
import org.springframework.jdbc.core.RowMapper;

public class AsignacionFolioRowMapper implements RowMapper {

	public Object mapRow(ResultSet res, int arg1) throws SQLException {
	
		AsignacionFolio folios = new AsignacionFolio();
		
		folios.setFecha(res.getDate("Fecha"));
		folios.setFolioInicio(res.getInt("FolioInicio"));
		folios.setFolioFinal(res.getInt("FolioFinal"));
		folios.setIdEmpresa(res.getInt("FK01_Empresa"));
		folios.setIdEmpresa(res.getInt("FK02_Empleado"));
		folios.setNombreEmpleado(res.getString("NEmpleado"));
		folios.setRangoFoliosAsignados(res.getInt("RFAsignados"));
		folios.setFoliosConsumidos(res.getInt("FConsumidos"));
		folios.setAsignado(res.getBoolean("Asignado"));
		folios.setTipo(res.getString("Tipo"));
		
		return folios;
	}



}
