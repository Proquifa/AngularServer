package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.Producto;
import com.proquifa.net.modelo.ventas.PartidaCotizacion;

import org.springframework.jdbc.core.RowMapper;

public class PartidaCotizacionReRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		PartidaCotizacion part = new PartidaCotizacion();
		
		part.setCantidad(rs.getInt("Cant"));
		part.setCodigo(rs.getString("Codigo"));
		part.setPrecio(rs.getFloat("Precio"));
		part.setConcepto(rs.getString("Concepto"));
		part.setPrecioDolarProducto(rs.getFloat("PrecioDolares"));
		part.setEstado(rs.getString("Estado"));
		part.setIva(rs.getDouble("IVA"));
		part.setCosto(rs.getFloat("Costo"));
		part.setFabrica(rs.getString("Fabrica"));
		part.setNota(rs.getString("Nota"));
		part.setClasificacion(rs.getString("Clasif"));
		part.setDestino(rs.getString("Destino"));
		part.setPresentacion(rs.getString("Presentacion"));
		part.setMedioEnvio(rs.getString("MEnvio"));
		part.setTiempoEntrega(rs.getString("TEntrega"));
		part.setNota(rs.getString("Nota"));
		
		Producto prod = new Producto();
		prod.setIdProducto(rs.getLong("FK03_idProducto"));
		prod.setIdFabricante(rs.getString("FK04_Fabricante").toString());
		prod.setCosto(rs.getDouble("Precio"));
		prod.setCantPiezas(rs.getLong("Cant"));
		prod.setCodigo(rs.getString("Codigo"));
		prod.setConcepto(rs.getString("Concepto"));
		prod.setFabrica(rs.getString("Fabrica"));
		prod.setTiempoEntrega(rs.getString("TEntrega"));
		prod.setPdolar(rs.getDouble("PDolar"));
		prod.setEdolar(rs.getDouble("EDolar"));
		prod.setTipo(rs.getString("tipo"));
		prod.setCostoAux(rs.getDouble("Precio"));
		prod.setSubtipo(rs.getString("subtipo"));
		prod.setControl(rs.getString("control"));
		prod.setProveedor(rs.getLong("proveedor"));
		prod.setTipoPresentacion(rs.getString("Presentacion"));
		part.setProducto(prod);
		part.setIdProducto(rs.getLong("FK03_idProducto"));
		part.setIndicePrecio(rs.getLong("IndicePrecio"));
		
		
	
		
		return part;
		
	}

}

