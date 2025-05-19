package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;


import com.proquifa.net.modelo.comun.Producto;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.persistencia.comun.ProductoDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

public class ProductoRowMapper implements RowMapper  {
	@Autowired
	ProductoDAO productoDAO;
	
	Funcion funcion; 
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		funcion = new Funcion();
		Producto producto = new Producto();
		String cantidad = "";
		String unidad = "";
		Long pureza = 0L;
		
		producto.setIdProducto(rs.getLong("idProducto"));
		producto.setTipo(rs.getString("tipo"));
		producto.setFabrica(rs.getString("fabrica"));
		producto.setCodigo(rs.getString("codigo"));
		producto.setCosto(rs.getDouble("costo"));
		producto.setConcepto(rs.getString("concepto"));
		producto.setProveedor(rs.getLong("proveedor"));
		producto.setControl(rs.getString("control"));
		
		try {producto.setMoneda(rs.getString("moneda"));} catch (Exception e) {}
		try {producto.setDescuento(rs.getLong("descuento"));}catch (Exception e) {}
		try {producto.setTiempoEntrega(rs.getString("tEntrega"));}catch (Exception e) {}
		try {producto.setVigente(rs.getBoolean("vigente"));}catch (Exception e) {}
		try {producto.setDocumentacion(rs.getString("documentacion"));}catch (Exception e) {}
		try {producto.setManejo(rs.getString("manejo"));}catch (Exception e) {}
		try {producto.setDisponibilidad(rs.getString("disponibilidad"));}catch (Exception e) {}
		try {producto.setEstado(rs.getString("estado"));}catch (Exception e) {}
		try {producto.setPrecioFijo(rs.getDouble("PFijo"));}catch (Exception e) {}
		try {producto.setCostoMinimo(rs.getDouble("CMinimo"));}catch (Exception e) {}
		try {producto.setIva(rs.getDouble("IVA"));}catch (Exception e) {}
		try {producto.setCaduca(rs.getTimestamp("Caduca"));}catch (Exception e) {}
		try {producto.setFecha(rs.getTimestamp("Fecha"));}catch (Exception e) {}
		cantidad = rs.getString("cantidad");
		try {producto.setTipoPresentacion(rs.getString("tipoPresentacion"));} catch (Exception e) {}
		try {producto.setOrigen(rs.getString("origen"));} catch (Exception e) {}
		
		try {producto.setCas(rs.getString("Cas"));} catch (Exception e) {}
		try {producto.setLote(rs.getString("Lote"));} catch (Exception e) {}
		
		try {producto.setSubtipo(rs.getString("Subtipo"));} catch (Exception e) {}
		
		
		if(rs.wasNull()){
			producto.setCantidad("");
		}else{
			producto.setCantidad(cantidad);
		}
		
		try {
		unidad = rs.getString("unidad");} catch (Exception e) {}
		
		if(rs.wasNull()){
			producto.setUnidad("");
		}else{
			producto.setUnidad(unidad);
		}
		try {
		pureza = rs.getLong("pureza");} catch (Exception e) {}
		if(rs.wasNull()){			
			producto.setPureza(null);
		}else{
			producto.setPureza(pureza);
		}
		producto.setDescripcion(funcion.obtenerDescripcionProducto(producto));
		
		try {
			producto.setIdFabricante(rs.getString("FK02_Fabricante"));
		} catch (Exception e) {
		}

		try {
			producto.setTipo(rs.getString("TipoProducto"));
		} catch (Exception e) {
		}
	
		return producto;
	}
}