/**
 * 
 */
package com.proquifa.net.persistencia.catalogos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.Proveedor;

import org.springframework.jdbc.core.RowMapper;

public class ProveedorPQNetRowMapper implements RowMapper {

	/* (non-Javadoc) 
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Proveedor proveedor = new Proveedor();

		proveedor.setIdProveedor(rs.getLong("clave"));
		proveedor.setNombre(rs.getString("Nombre"));
		proveedor.setRazonSocial(rs.getString("RSocial"));
		proveedor.setCalle(rs.getString("RSCalle")); 
		proveedor.setDelegacion(rs.getString("RSDel"));
		proveedor.setSegundaDireccion(rs.getString("Direccion2"));
		proveedor.setCiudad(rs.getString("Ciudad"));
		proveedor.setPais(rs.getString("Pais"));
		proveedor.setEstado(rs.getString("Estado"));
		proveedor.setHabilitado(rs.getBoolean("habilitado"));
		proveedor.setCp(rs.getString("CP"));
		proveedor.setDescripcionAmpliada(rs.getString("descripcionAmpliada"));
		proveedor.setFechaActualizacion(rs.getTimestamp("FUActual"));
		proveedor.setSocioComercial(rs.getBoolean("existeRelacionComercial"));
		proveedor.setImagen(rs.getString("imagen"));
		proveedor.setFUAProductos(rs.getTimestamp("FUA_Productos"));
		proveedor.setPagador(rs.getLong("Pagador"));
		proveedor.setComprador(rs.getLong("FK01_Empleado"));
		proveedor.setColocarPhs(rs.getBoolean("ColocarPhs"));
		proveedor.setInspector(rs.getLong("FK03_Inspector"));
		proveedor.setMesInicioFiscal(rs.getInt("MesInicioFiscal"));
		proveedor.setClavePais(rs.getInt("FK05_Pais"));
		proveedor.setNombrePaisIngles(rs.getString("Ingles"));
		
		try {proveedor.setTaxId(rs.getString("TaxID"));} catch (Exception e) {}
		
		return proveedor;
	}
}