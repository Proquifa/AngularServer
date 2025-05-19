package com.proquifa.net.persistencia.contabilidad.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.cuentaContable.Poliza;

public class PolizaRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Poliza poliza = new Poliza();
		poliza.setIdPoliza(rs.getInt("PK_Poliza"));
		poliza.setIdEmpresa(rs.getInt("FK01_Empresa"));
		poliza.setIdCliente(rs.getInt("FK02_Cliente"));
		poliza.setIdProveedor(rs.getInt("FK03_Proveedor"));
		poliza.setTipo(rs.getInt("Tipo"));
		poliza.setFolio(rs.getString("Folio"));
		poliza.setDescripcion(rs.getString("Descripcion"));
		poliza.setFecha(rs.getDate("Fecha"));
		poliza.setMonto(rs.getDouble("Monto"));
		poliza.setIva(rs.getDouble("Iva"));
		poliza.setTotal(rs.getDouble("Total"));
		poliza.setAplicada(rs.getBoolean("Aplicada"));
		poliza.setActiva(rs.getBoolean("Activa"));
		poliza.setReferencia(rs.getString("Referencia"));
		return poliza;
	}


}
