/**
 * 
 */
package com.proquifa.net.persistencia.cobrosypagos.facturista.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.comun.Empresa;

/**
 * @author orosales
 *
 */
public class infoEmpresaFacturaRowMapper  implements RowMapper{
	
	final Logger log = LoggerFactory.getLogger(infoEmpresaFacturaRowMapper.class);

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
		empFac.setTelefono(res.getString("Telefono"));

		empFac.setFacturaElectronica(res.getBoolean("FacturacionElectronica"));
		empFac.setFacturaMatriz(res.getBoolean("FacturacionMatriz"));
		
		//Estos son llenados con null en el metodo ifoempresa
		// En el metodo obtener folios si se les asigna un valor. 
		
		empFac.setFolioActual(res.getInt("FActual"));
		empFac.setFoliosUsadosMes(res.getInt("FUsadosMes"));
		empFac.setTotMontoMes(res.getDouble("TOTALMONTODLL"));
		empFac.setTotCanceladasMes(res.getInt("TOTCANCELADAS"));
		empFac.setFacturacionHabilitada(res.getBoolean("FacHab"));
		
		try{
			empFac.setVendedor(res.getBoolean("Vende"));
		}catch(Exception e){
		}
		try{
			empFac.setComprador(res.getBoolean("Compra"));
		}catch(Exception e){
		}
		
		try {empFac.setExportador(res.getBoolean("Exportador"));} catch (Exception e) {log.info("pasa1");}
		try {empFac.setImportador(res.getBoolean("Importador"));} catch (Exception e) {log.info("pasa2");}
		try {empFac.setPadronImportador(res.getBoolean("Padron_Importador"));} catch (Exception e) {log.info("pasa3");}
		
		
		return empFac;
	}
}
