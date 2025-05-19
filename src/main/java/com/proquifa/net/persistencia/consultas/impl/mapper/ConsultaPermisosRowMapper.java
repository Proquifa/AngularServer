package com.proquifa.net.persistencia.consultas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;


import com.proquifa.net.modelo.compras.PermisoImportacion;

public class ConsultaPermisosRowMapper implements RowMapper {
	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		PermisoImportacion permiso = new PermisoImportacion();
		
		permiso.setIdProducto(rs.getLong("idProducto"));
		permiso.setFecha(rs.getDate("Fecha"));
		permiso.setProveedor(rs.getString("Proveedor"));
		permiso.setCodigoProducto(rs.getString("Catalogo"));
		permiso.setNombreProducto(rs.getString("Producto"));
		permiso.setTipoProducto(rs.getString("Tipo"));
		permiso.setSubTipo(rs.getString("Subtipo"));
		permiso.setTipoPermiso(rs.getString("TipoPermiso"));
		permiso.setClasificacionProducto(rs.getString("Clasificacion"));
		permiso.setControlProducto(rs.getString("Control"));
		permiso.setEstadoFisico(rs.getString("EstadoFisico"));
		permiso.setEstado(rs.getString("Estado"));
		permiso.setFechaET(rs.getDate("FET"));
		permiso.setCas(rs.getString("CAS"));
		permiso.setFraccionArancelaria(rs.getString("FA"));
		permiso.setOrigenFolio(rs.getString("Origen"));
		permiso.setFechaEL(rs.getDate("FEL"));
		permiso.setFechaLiberacion(rs.getDate("FLiberacion"));
		permiso.setFabrica(rs.getString("Fabrica"));
		permiso.setCosto(rs.getFloat("Costo"));
		permiso.setCantidad(rs.getDouble("NoPiezas"));
		permiso.setNoProductos(rs.getInt("NoProductos"));
		permiso.setEstadoPermiso(rs.getString("EstadoPermiso"));
		permiso.setMoneda(rs.getString("moneda"));
		return permiso;
	}
}
