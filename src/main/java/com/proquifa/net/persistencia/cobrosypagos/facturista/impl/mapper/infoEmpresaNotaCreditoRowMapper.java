/**
 * 
 */
package com.proquifa.net.persistencia.cobrosypagos.facturista.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;


import com.proquifa.net.modelo.comun.Empresa;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author orosales
 *
 */
public class infoEmpresaNotaCreditoRowMapper  implements RowMapper
		 {

	@Override
	public Object mapRow(ResultSet res, int arg1) throws SQLException {
		
		Empresa empFac = new Empresa();
		
		empFac.setIdEmpresa(res.getInt("PK_Empresa"));
		empFac.setRfcEmpresa(res.getString("RFC"));
		empFac.setNomenclaturaEmpresa(res.getString("Prefijo"));
		empFac.setAlias(res.getString("Alias"));
		empFac.setCalle(res.getString("Calle"));
		empFac.setColonia(res.getString("Colonia"));
		empFac.setDelegacion(res.getString("Delegacion"));
		empFac.setEstado(res.getString("Estado"));
		empFac.setCiudad(res.getString("Ciudad"));
		empFac.setPais(res.getString("Pais"));
		empFac.setCp(res.getString("CP"));
		empFac.setFoliosAsignados(res.getInt("FoliosAsignados"));
		empFac.setRazonSocial(res.getString("RazonSocial"));
		empFac.setFacturaElectronica(res.getBoolean("FacturacionElectronica"));
		empFac.setFacturaMatriz(res.getBoolean("FacturacionMatriz"));
		empFac.setFolioActual(res.getInt("FActual"));
		empFac.setFoliosUsadosMes(res.getInt("FUsadosMes"));
		empFac.setTotMontoMes(res.getDouble("TOTALMONTODLL"));
		empFac.setTotCanceladasMes(res.getInt("TOTCANCELADAS"));
		empFac.setFacturacionHabilitada(res.getBoolean("FacHab"));
		
		return empFac;
	}
}
