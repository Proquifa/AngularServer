/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;


import com.proquifa.net.modelo.comun.Empresa;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author orosales
 *
 */
public class InformacionEmpresaRowMapper  implements RowMapper
		 {

	@Override
	public Object mapRow(ResultSet res, int arg1) throws SQLException {
		
		Empresa em = new Empresa();
		
		em.setIdEmpresa(res.getInt("PK_Empresa"));
		em.setRfcEmpresa(res.getString("RFC"));
		em.setNomenclaturaEmpresa(res.getString("Prefijo"));
		em.setAlias(res.getString("Alias"));
		em.setCalle(res.getString("Calle"));
		em.setColonia(res.getString("Colonia"));
		em.setDelegacion(res.getString("Delegacion"));
		em.setEstado(res.getString("Estado"));
		em.setCiudad(res.getString("Ciudad"));
		em.setPais(res.getString("Pais"));
		em.setCp(res.getString("CP"));
		em.setRazonSocial(res.getString("RazonSocial"));
		em.setFacturaElectronica(res.getBoolean("FacturacionElectronica"));
		em.setFacturaMatriz(res.getBoolean("FacturacionMatriz"));
		em.setVendedor(res.getBoolean("Vende"));
		em.setComprador(res.getBoolean("Compra"));
		em.setExportador(res.getBoolean("Exportador"));
		em.setImportador(res.getBoolean("Importador"));
		em.setPadronImportador(res.getBoolean("Padron_Importador"));

		return em;
	}
}
