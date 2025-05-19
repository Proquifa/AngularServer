/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.Proveedor;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author amartinez
 *
 */
public class ProveedorRowMapper implements RowMapper {

	/* (non-Javadoc) 
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Proveedor proveedor = new Proveedor();

		proveedor.setHabilitado(rs.getBoolean("habilitado"));
		proveedor.setNombre(rs.getString("Nombre"));
		proveedor.setIdProveedor(rs.getLong("clave"));
		proveedor.setObserva(rs.getString("Observa"));
		proveedor.setPais(rs.getString("Pais"));
		proveedor.setRSCalle(rs.getString("rscalle"));
		proveedor.setCalle(rs.getString("Calle"));
		proveedor.setDelegacion(rs.getString("Delegacion") );
		proveedor.setEstado(rs.getString("Estado") );
		proveedor.setCp(rs.getString("CP"));
		proveedor.setRazonSocial(rs.getString("RSocial"));
		proveedor.setMoneda(rs.getString("Moneda"));
		proveedor.setCPago(rs.getString("cpago"));
		proveedor.setCheque(rs.getBoolean("cheque"));
		proveedor.setTransferencia(rs.getBoolean("transferencia"));
		proveedor.setTarjeta(rs.getBoolean("tarjeta"));
		proveedor.setDeposito(rs.getBoolean("deposito"));
		proveedor.setDescripcionAmpliada(rs.getString("descripcionAmpliada"));
		proveedor.setSegundaDireccion(rs.getString("Direccion2"));
		proveedor.setCiudad(rs.getString("Ciudad"));
		proveedor.setSocioComercial(rs.getBoolean("existeRelacionComercial"));
		proveedor.setMonedaCompra(rs.getString("moneda"));
		proveedor.setMonedaVenta(rs.getString("monedaventa"));
		proveedor.setTaxId(rs.getString("TaxID"));
		proveedor.setAplicaRecoleccion(rs.getBoolean("aplicaRecoleccion"));
		//proveedor.setImagen(rs.getBoolean("imagen"));
		
		return proveedor;
	}

}
