package com.proquifa.net.persistencia.consultas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.comun.ResumenPermisoImportacion;

public class ConsultaResumenPermisoImportacionRowMapper implements RowMapper  {
	

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ResumenPermisoImportacion resumen = new ResumenPermisoImportacion();
		
		String etiqueta = rs.getString("Etiqueta");
		
		resumen.setEstado(rs.getString("Estado"));
		if (etiqueta.equals("Previo")){
			
			resumen.setFechaIngresoCatalogo(rs.getTimestamp("FechaIngreso"));
			resumen.setFechaPrevioTramitacion(rs.getTimestamp("FechaTramitacion"));
			resumen.setTipoPermiso(rs.getString("TipoPermiso"));
			resumen.setClasificacion(rs.getString("ClasificacionProducto"));
			resumen.setTramitarPermiso(rs.getString("Tramitar"));
			resumen.setFet(rs.getDate("fet"));
		}
		else if(etiqueta.equals("A Tramitar") || etiqueta.equals("Solicitud") ){
			resumen.setFechaPrevioTramitacion(rs.getTimestamp("FechaTramitacion"));
			resumen.setFechaTramitarPermiso(rs.getTimestamp("FechaTramitar"));
			resumen.setLote(rs.getString("Lote"));
			resumen.setTipoPermiso(rs.getString("TipoPermiso"));
			resumen.setSolicitud(rs.getString("Solicitud"));
			resumen.setCatalogo(rs.getString("Codigo"));
			resumen.setConcepto(rs.getString("Concepto"));
			resumen.setFet(rs.getTimestamp("fet"));
			resumen.setFolioDocto(rs.getString("folioDocto"));
			
		}else if(etiqueta.equals("En Autorizacion")){
			resumen.setFechaTramitarPermiso(rs.getTimestamp("FechaTramitar"));
			resumen.setFechaPorSometer(rs.getTimestamp("PorSometer"));
			resumen.setTipoPermiso(rs.getString("TipoPermiso"));
			resumen.setLote(rs.getString("Lote"));
			resumen.setSolicitud(rs.getString("Solicitud"));
			resumen.setFechaSometio(rs.getTimestamp("FechaSometio"));
			resumen.setFel(rs.getDate("FEL"));
			resumen.setNoEntrada(rs.getString("NoEntrada"));
			resumen.setDocEntrada(rs.getString("DoctoEntrada"));
		}
		else if(etiqueta.equals("Monitorear")){
			resumen.setFechaAutorizacion(rs.getTimestamp("FechaAutorizacion"));
			resumen.setFel(rs.getDate("fel"));
			resumen.setFechaLiberacion(rs.getDate("FLiberacion"));
			resumen.setLote(rs.getString("Lote"));
			resumen.setSolicitud(rs.getString("Solicitud"));
			resumen.setVigencia(rs.getDate("Vigencia"));
			resumen.setNoPermiso(rs.getString("Numero"));
			resumen.setDocPermiso(rs.getString("Documento"));
		}
		else if (etiqueta.equals("Permiso")){
			resumen.setFechaIngresoCatalogo(rs.getTimestamp("FechaIngreso"));
			resumen.setFechaPrevioTramitacion(rs.getTimestamp("FechaTramitacion"));
			resumen.setFet(rs.getDate("fet"));
			resumen.setTipoPermiso(rs.getString("TipoPermiso"));
			resumen.setClasificacion(rs.getString("ClasificacionProducto"));
			resumen.setConcepto(rs.getString("Concepto"));
			resumen.setFechaTramitarPermiso(rs.getTimestamp("FechaTramitar"));
			resumen.setLote(rs.getString("Lote"));
			resumen.setSolicitud(rs.getString("Solicitud"));
			resumen.setFechaSometio(rs.getTimestamp("FechaSometio"));
			resumen.setFel(rs.getDate("FEL"));
			resumen.setNoEntrada(rs.getString("NoEntrada"));
			resumen.setDocEntrada(rs.getString("DoctoEntrada"));
			resumen.setFechaAutorizacion(rs.getTimestamp("FechaAutorizacion"));
			resumen.setFechaLiberacion(rs.getDate("FLiberacion"));
			resumen.setVigencia(rs.getDate("Vigencia"));
			resumen.setNoPermiso(rs.getString("Numero"));
			resumen.setDocPermiso(rs.getString("Documento"));
			
		}
		return resumen;
	}
	
}
