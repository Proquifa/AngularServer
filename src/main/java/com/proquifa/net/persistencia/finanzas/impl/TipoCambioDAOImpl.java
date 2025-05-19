package com.proquifa.net.persistencia.finanzas.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.finanzas.TipoCambioDAO;

@Repository
public class TipoCambioDAOImpl extends DataBaseDAO implements TipoCambioDAO {
	Funcion funcion = new Funcion();
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean existTCambioDOF(Date fecha) throws ProquifaNetException{
		String fActualS = funcion.obtenerFechaConFormato("yyyyMMdd", fecha);
		String query = "SELECT COUNT(*) FROM Monedas WHERE Fecha = '"+ fActualS +"'";
			
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		long r = super.jdbcTemplate.queryForObject(query, parametros, long.class);						
				
		if(r > 0){
			return true;
		}else{
			return false;
		}			
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean insertTCambioDOF(Date fecha, String tCambio) throws ProquifaNetException{
		String fActualS = funcion.obtenerFechaConFormato("yyyyMMdd", fecha);
		String query = "INSERT INTO Monedas (Fecha, DOF) VALUES ('"+ fActualS + "', "+ tCambio +" )";	
		
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			super.jdbcTemplate.update(query, map);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return false;
		}					
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean updateTCambioDOF(Date fecha, String tCambio) throws ProquifaNetException{
		String fActualS = funcion.obtenerFechaConFormato("yyyyMMdd", fecha);
		String query = "UPDATE Monedas SET DOF="+ tCambio +" WHERE Fecha='"+fActualS +"'";	
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			super.jdbcTemplate.update(query, map);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return false;
		}		
	}
		
}
