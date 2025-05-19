package com.proquifa.net.persistencia.comun.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.proquifa.net.modelo.comun.Direccion;
import com.proquifa.net.modelo.comun.Horario;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.comun.DireccionDAO;
import com.proquifa.net.persistencia.comun.impl.mapper.DireccionRecoleccionRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.HorarioRecoleccionRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.HorarioyLugarRevisionRowMapper;


import org.springframework.stereotype.Repository;

/**
 * 
 * @author isaac.garcia
 *
 */

@Repository
public class DireccionDAOImpl extends DataBaseDAO implements DireccionDAO {

	public Direccion getDireccionRecoleccion(Long idProveedor) throws ProquifaNetException{
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idProveedor", idProveedor);
			
			String sQuery = "";
			
			sQuery = "SELECT * FROM Direccion WHERE FK02_Proveedor = " + idProveedor;
			
			Direccion d = (Direccion) super.jdbcTemplate.queryForObject(sQuery, map, new DireccionRecoleccionRowMapper());
			
			if(d != null) {
				return d; 
			}else{
				return null;
			}
			
		} catch (Exception e) {
			//logger.info("Error: " + e.getMessage());
			return null;
		}
	}
	
	@Override	
	public Long insertDireccion(Direccion d) throws ProquifaNetException {
		try {	
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("d", d);
			Object[] params =  {d.getPais(), d.getCodigoPostal(), d.getEstado(), d.getMunicipio(), d.getCiudad(), d.getCalle(), d.getColonia(), d.getZona(), d.getRuta(),
								d.getMapa(), d.getAltitud(), d.getLatitud(), d.getLongitud(), d.getIdCliente(), d.getIdProveedor()};
			super.jdbcTemplate.update("INSERT INTO Direccion (Pais, CP, Estado, Municipio, Ciudad, CalleNum, Colonia, Zona, Ruta, Mapa, Altitud, Latitud, Longitud, FUA, FK01_Cliente, FK02_Proveedor)VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,getdate(),?,?)", map);
			Long idHorario = super.queryForLong("SELECT IDENT_CURRENT ('Direccion')");
			return idHorario;
		} catch (RuntimeException e) {			
			//logger.info("Error: " + e.getMessage());
			throw new ProquifaNetException();
		}
	}
	
	@Override	
	public boolean updateDireccion(Direccion d) throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("d", d);
			Object[] params = {d.getPais(), d.getCodigoPostal(), d.getEstado(), d.getMunicipio(), d.getCiudad(), d.getCalle(), d.getColonia(), d.getZona(), d.getRuta(),
					d.getMapa(), d.getAltitud(), d.getLatitud(), d.getLongitud(), d.getIdCliente(),d.getIdProveedor(),d.getIdDireccion()};
			
			super.jdbcTemplate.update("UPDATE Direccion SET Pais=?, CP=?, Estado=?, Municipio=?, Ciudad=?, CalleNum=?, Colonia=?, Zona=?, Ruta=?, Mapa=?, Altitud=?, Latitud=?, Longitud=?, FUA=getdate(), FK01_Cliente=?, FK02_Proveedor=? WHERE PK_Direccion= ?", map);

			return true;
		}catch(RuntimeException e){			
			//logger.info(e.getMessage());
			throw new ProquifaNetException();
		}
	}
	
	@Override	
	public boolean deleteDireccion(Long idDireccion) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idDireccion", idDireccion);
			String sQuery = "DELETE FROM Direccion WHERE PK_Direccion = " + idDireccion;
			
			super.jdbcTemplate.update(sQuery, map);
			
			return true;
		} catch (Exception e) {			
			//logger.info(e.getMessage());
			throw new ProquifaNetException();
		}
	}
	
	@Override	
	public Long insertHorario(Horario horario) throws ProquifaNetException {
		try {	
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("horario", horario);
			Object[] params =  {horario.getLunes(), horario.getLuDe1(), horario.getLuDe2(), horario.getLuA1(), horario.getLuA2(),
					horario.getMartes(), horario.getMaDe1(), horario.getMaDe2(), horario.getMaA1(), horario.getMaA2(),horario.getMiercoles(),
					horario.getMiDe1(),horario.getMiDe2(), horario.getMiA1(), horario.getMiA2(), horario.getJueves(), horario.getJuDe1(), horario.getJuDe2(),
					horario.getJuA1(), horario.getJuA2(), horario.getViernes(), horario.getViDe1(), horario.getViDe2(),  
					horario.getViA1(), horario.getViA2(), horario.getTipo() , horario.getIdDireccion()};
			
			String query = 	" INSERT INTO Horario(lunes,lude1,lude2,lua1,lua2,martes,made1,made2,maa1,maa2,miercoles,mide1,mide2,mia1,mia2," +
							" jueves,jude1,jude2,jua1,jua2,viernes,vide1,vide2,via1, via2,Tipo,FK01_Direccion, FUA) " +
							" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, getdate())";
			
			////logger.info(query);			
			super.jdbcTemplate.update(query, map);
			
			Long idHorario = super.queryForLong("SELECT IDENT_CURRENT ('Horario')");
			
			return idHorario;
		} catch (RuntimeException e) {			
		//	logger.error("Error: " + e.getMessage());
			throw new ProquifaNetException();
		}
	}
	
	@Override	
	public boolean updateHorario(Horario horario) throws ProquifaNetException{
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("horario", horario);
			Object[] params = {
					horario.getLunes(), horario.getLuDe1(), horario.getLuDe2(), horario.getLuA1(), horario.getLuA2(),
					horario.getMartes(), horario.getMaDe1(), horario.getMaDe2(), horario.getMaA1(), horario.getMaA2(),horario.getMiercoles(),
					horario.getMiDe1(),horario.getMiDe2(), horario.getMiA1(), horario.getMiA2(), horario.getJueves(), horario.getJuDe1(), horario.getJuDe2(),
					horario.getJuA1(), horario.getJuA2(), horario.getViernes(), horario.getViDe1(), horario.getViDe2(),  
					horario.getViA1(), horario.getViA2(), horario.getIdHorario()};
			
			StringBuilder sbQuery = new StringBuilder("UPDATE Horario SET FUA = getdate(), ");
			sbQuery.append("lunes = ?, lude1 = ?, lude2 = ?, lua1 = ?, lua2 = ?, martes = ?, made1 = ?, made2 = ?,");
			sbQuery.append("maa1 = ?, maa2 = ?, miercoles = ?, mide1 =?, mide2 = ?, mia1 = ?, mia2 = ?, ");
			sbQuery.append("jueves = ?, jude1 = ?, jude2 = ?, jua1 = ?, jua2 = ?, viernes = ?, vide1 = ?, vide2 = ?,");
			sbQuery.append("via1 = ?, via2 = ? WHERE PK_Horario = ?");
			
			jdbcTemplate.update(sbQuery.toString(), map);
			////logger.info(sbQuery.toString());
			return true;
		} catch (Exception e) {			
		//	logger.error(e.getMessage());
			throw new ProquifaNetException();
		}
	}

	@Override
	public boolean deleteHorario(Long idHorario) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idHorario", idHorario);
			String sQuery = "DELETE FROM Horario WHERE PK_Horario = " + idHorario ;
			super.jdbcTemplate.update(sQuery, map);
			
			return true;
		} catch (Exception e) {			
			//logger.info(e.getMessage());
			throw new ProquifaNetException();
		}
	}
	
	@Override	
	public boolean deleteHorariosDireccion(Long idDireccion) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idDireccion", idDireccion);
			String sQuery = "DELETE FROM Horario WHERE FK01_Direccion = " + idDireccion ;
			super.jdbcTemplate.update(sQuery, map);
			
			return true;
		} catch (Exception e) {			
			//logger.info(e.getMessage());
			throw new ProquifaNetException();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Horario> getHorario(Long idDireccion, String tipo) throws ProquifaNetException{
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idDireccion", idDireccion);
			map.put("tipo", tipo);
			Object[] param = {tipo, idDireccion};
			
			String query = 	" SELECT  *" +
							" FROM Horario " +
							" WHERE tipo = ? AND FK01_Direccion = ?";

			return jdbcTemplate.query(query, map, new HorarioRecoleccionRowMapper());
			
		} catch (Exception e) {
		//7	logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Direccion> getHorarioyLugarXidClienteYTipo(Long idCliente, String tipo)
			throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCliente", idCliente);
			map.put("tipo", tipo);
			if(tipo == null){
				Object[] param = {idCliente};
				
				String query = 	" SELECT  * FROM HorarioyLugar " +
								" WHERE idCliente = ?";
			//	logger.info(query);
				return jdbcTemplate.query(query, map, new HorarioyLugarRevisionRowMapper());
			}
			else{
				Object[] param = {idCliente,tipo};
				
				String query = 	" SELECT  * FROM HorarioyLugar " +
								" WHERE idCliente = ? AND Tipo = ?";
			//	logger.info(query);
				return jdbcTemplate.query(query, map, new HorarioyLugarRevisionRowMapper());
			}

		} catch (Exception e) {
			//logger.error(e.getMessage());
			return null;
		}
	}


}
