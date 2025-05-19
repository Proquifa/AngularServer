package com.proquifa.net.persistencia.compras.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.compras.PermisoImportacion;

import org.springframework.jdbc.core.RowMapper;

public class ObtenerPermisoImportacionRowMapper implements RowMapper {

	
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		PermisoImportacion per = new PermisoImportacion();
		per.setFabrica(rs.getString("Fabrica"));
		per.setFechaEL(rs.getDate("FEL"));
		per.setFechaET(rs.getTimestamp("FET"));
		per.setFinicio(rs.getTimestamp("FInicio"));
		per.setEtiqueta(rs.getString("ETIQUETA"));
		per.setNombreProducto(rs.getString("NOMBRE"));
		per.setTipoPermiso(rs.getString("TipoPermiso"));
		per.setPrecioUnitario(rs.getDouble("PU"));
		per.setTipoSolicitudPermiso(rs.getString("TipoSolicitud"));
		per.setOrigenFolio(rs.getString("OrigenFolio"));
		per.setOrigen(rs.getString("Origen"));
		per.setCas(rs.getString("CAS"));
		per.setEstadoFisico(rs.getString("EstadoFisico"));
		per.setFraccionArancelaria(rs.getString("FraccionArancelaria"));
		per.setClasificacionSolictud(rs.getString("ClasifSolicitud"));
		per.setTipoProducto(rs.getString("TipoProducto"));
		per.setSubTipo(rs.getString("Subtipo"));
		per.setControlProducto(rs.getString("Control"));
		per.setClasificacionProducto(rs.getString("Clasificacion"));
		per.setDiasFiltro(rs.getString("DiasFiltro"));
		per.setCodigoProducto(rs.getString("Codigo"));
		per.setCantidad(rs.getDouble("CANT"));
		per.setIdSolicitud(rs.getString("SOLICITUD"));
		per.setPedCot(rs.getString("PEDCOT"));
		per.setFolioLote(rs.getString("FolioLote"));
		per.setFechaSometio(rs.getString("FSometio"));
		per.setDoctoEntrada(rs.getString("DoctoEntrada"));
		per.setNoEntrada(rs.getString("NoEntrada"));
		per.setFolioSolicitud(rs.getString("FolioConjunto"));
		per.setEstadoPermiso(rs.getString("EstadoPermiso"));
		per.setJustificacion(rs.getString("Justificacion"));
		per.setMarca(rs.getString("Marca"));
		
		
		return per;
	}
	

}
