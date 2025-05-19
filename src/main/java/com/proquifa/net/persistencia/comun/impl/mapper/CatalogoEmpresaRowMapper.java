/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.proquifa.net.modelo.comun.CatalogoItem;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author misael.camanos
 *
 */
public class CatalogoEmpresaRowMapper implements RowMapper{

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		CatalogoItem catalogoItem = new CatalogoItem();
		catalogoItem.setLlave(rs.getLong("clave"));
		catalogoItem.setValor(rs.getString("empresa"));
		catalogoItem.setValor1(rs.getString("rfc"));
		catalogoItem.setValor2(rs.getString("razon"));
		catalogoItem.setActivo(rs.getBoolean("activo"));
		try {
			catalogoItem.setNombre(rs.getString("prefijo"));
			List<CatalogoItem> lstFoliosPoliza = new ArrayList<CatalogoItem>();
			CatalogoItem ciIngreso = new CatalogoItem();
			ciIngreso.setNombre("I");//Se usa para validar en VISTA
			ciIngreso.setValor3(rs.getInt("pIngreso"));
			CatalogoItem ciEgreso = new CatalogoItem();
			ciEgreso.setNombre("E");//Se usa para validar en VISTA
			ciEgreso.setValor3(rs.getInt("pEgreso"));
			CatalogoItem ciDiario = new CatalogoItem();
			ciDiario.setNombre("D");//Se usa para validar en VISTA
			ciDiario.setValor3(rs.getInt("pDiario"));
			lstFoliosPoliza.add(ciIngreso);
			lstFoliosPoliza.add(ciEgreso);
			lstFoliosPoliza.add(ciDiario);
			catalogoItem.setLstFoliosPoliza(lstFoliosPoliza);
			}catch(Exception e) {}
		return catalogoItem;
	}

}
