package com.proquifa.net.persistencia.contabilidad.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.cuentaContable.CuentaContable;

public class CuentaContableRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		CuentaContable cuenta = new CuentaContable();
		try {
			Integer nivel = rs.getInt("Nivel");
			cuenta.setIdCuentaContable(rs.getInt("PK_CuentaContable"));
			cuenta.setIdEmpresa(rs.getInt("FK01_Empresa"));
			cuenta.setNivel(nivel);
			cuenta.setNivel1(rs.getInt("Nivel_1"));
			cuenta.setNivel2(rs.getInt("Nivel_2"));
			cuenta.setNivel3(rs.getString("Nivel_3"));
			cuenta.setDescripcion(rs.getString("Descripcion"));
			cuenta.setOrigen(rs.getString("Origen"));
			cuenta.setNaturaleza(rs.getString("Naturaleza"));
			
			cuenta.setTipoInt(rs.getInt("Tipo"));
			cuenta.setDetalleInt(rs.getInt("Detalle"));
			cuenta.setActivo(rs.getBoolean("Activo"));
			
			try {
				int countB = rs.getInt("countB");
				int countC = rs.getInt("countC");
				int countP = rs.getInt("countP");
				int countPol = rs.getInt("countPol");
				boolean editable = ((countB + countC + countP) == 0 && nivel > 2) ? true : false; 
				boolean eliminable = (countPol == 0 && nivel > 2) ? true : false;
				cuenta.setEditable(editable);
				cuenta.setEliminable(eliminable);
				cuenta.setCantPolizas(countPol);
			}catch(Exception e) {}
			try {
				if (rs.getObject("totalCargo") != null) {
					cuenta.setTotalCargo(rs.getDouble("totalCargo"));
				}
				if (rs.getObject("totalAbono") != null) {
					cuenta.setTotalAbono(rs.getDouble("totalAbono"));
				}
				
			}catch(Exception e) {}
			try {
				if (rs.getObject("Monto") != null) {
					cuenta.setMonto(rs.getDouble("Monto"));
				}				
			}catch(Exception e) {}
			cuenta.setSaldoInicial( cuenta.getTotalAbono() - cuenta.getTotalCargo() );
			return cuenta;
		}catch(Exception e) {
			e.printStackTrace();
			return cuenta;
		}
	}

}
