package com.proquifa.net.persistencia.compras.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.compras.ResumenCompra;

public class ResumenCompraRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ResumenCompra rc = new ResumenCompra();
		rc.setCompra(rs.getString("orden"));
		rc.setNombreP(rs.getString("nombreP"));
		rc.setIdProveedor(rs.getInt("idProveedor"));
		rc.setNum_cliente(rs.getString("num_cliente"));
		rc.setNombreR(rs.getString("nombreR"));
		rc.setContacto(rs.getString("contacto"));
		rc.setFax(rs.getString("fax"));
		rc.setTEL(rs.getString("TEL"));
		rc.setEmail(rs.getString("email"));
		rc.setMoneda(rs.getString("moneda"));
		rc.setSimboloMoneda(rs.getString("simboloMoneda"));
		rc.setDireccion(rs.getString("direccion"));
		rc.setSUBTOTAL(rs.getString("SUBTOTAL"));
		rc.setIVA(rs.getString("IVA"));
		rc.setTOTAL(rs.getString("TOTAL"));
		rc.setFacturara(rs.getString("facturara"));
		rc.setAliasFacturarA(rs.getString("AliasFacturarA"));
		rc.setDirfactu(rs.getString("Dirfactu"));
		rc.setEmpresa(rs.getString("empresa"));
		rc.setrSocialEmpresa(rs.getString("RSocial"));
		rc.setFechaR(rs.getString("fechaR"));
		rc.setEsIngles(rs.getBoolean("enIngles"));
		
		return rc;
	}

}
