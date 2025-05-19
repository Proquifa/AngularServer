/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;


import com.proquifa.net.modelo.comun.Gestion;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.comun.GestionDAO;
import com.proquifa.net.persistencia.comun.impl.mapper.GestionRowMapper;


/**
 * @author amartinez
 *
 */
@Repository
public class GestionDAOImpl extends DataBaseDAO implements GestionDAO {

	/* (non-Javadoc)
	 * @see mx.com.proquifa.proquifanet.persistencia.comun.GestionDAO#actualizarGestion(mx.com.proquifa.proquifanet.modelo.comun.Gestion)
	 */
	public Boolean actualizarGestion(Gestion gestion) throws ProquifaNetException {
		try{
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("gestion", gestion);
		//	logger.info("String aceptado: " + gestion.getAceptadoString());
			if(gestion.getAceptadoString().equals("nulo")){
				gestion.setAceptado(null);
			}else if(gestion.getAceptadoString().equals("falso")){
				gestion.setAceptado(false);
			}else if(gestion.getAceptadoString().equals("verdadero")){
				gestion.setAceptado(true);
			}
			Object[] params =  {gestion.getIncidente(), gestion.getEmpleado(), gestion.getCliente(), gestion.getProveedor(), gestion.getSubProceso(), gestion.getProducto()
					, gestion.getTipo(), gestion.getImpactoCliente(), gestion.getImpactoProducto(), gestion.getImpactoSistema(), gestion.getDescripcion()
					, gestion.getQuien(), gestion.getLugar(), gestion.getCuando(), gestion.getComo(), gestion.getCausa(), gestion.getSolucion(), gestion.getAceptado()
					, gestion.getJustificacion(), gestion.getFecha(), gestion.getNombreContacto(), gestion.getDecision(), gestion.getAnalisis(), gestion.getContacto(), gestion.getCorreoContacto(), gestion.getProblema()
					, gestion.getNAProcedimiento(), gestion.getIdGestion()};
			String query = "UPDATE gestion SET fk01_incidente = ?, fk02_empleado = ?, fk03_cliente = ?, fk04_proveedor = ?, fk05_subproceso = ?, fk06_producto = ?" +
					", tipo = ?, impacto_cliente = ?, impacto_producto = ?, impacto_sistema = ?, descripcion = ?, quien = ?, lugar = ?, cuando = ?, como = ?, causa = ?" +
					", solucion = ?, aceptado = ?, justificacion = ?, fecha = ?, contacto =?, decision = ?, analisis = ?, fk07_contacto =?, correo_contacto = ?, problema = ?, NAProcedimiento = ? WHERE pk_gestion = ? "; 
			super.jdbcTemplate.update(query, map);
			return true;
		}catch(RuntimeException rte){
			//logger.info("Error..." + rte.getMessage());
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see mx.com.proquifa.proquifanet.persistencia.comun.GestionDAO#insertarGestion(mx.com.proquifa.proquifanet.modelo.comun.Gestion)
	 */
	public Long insertarGestion(Gestion gestion) throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("gestion", gestion);
			Object[] params =  {gestion.getIncidente(), gestion.getEmpleado(), gestion.getCliente(), gestion.getProveedor(), gestion.getSubProceso(), gestion.getProducto()
					, gestion.getTipo(), gestion.getImpactoCliente(), gestion.getImpactoProducto(), gestion.getImpactoSistema(), gestion.getDescripcion()
					, gestion.getQuien(), gestion.getLugar(), gestion.getCuando(), gestion.getComo(), gestion.getCausa(), gestion.getSolucion()
					, gestion.getJustificacion(), gestion.getFecha(), gestion.getNombreContacto(), gestion.getDecision(), gestion.getContacto(), gestion.getCorreoContacto(), gestion.getProblema(), gestion.getNAProcedimiento()};
			String query = "INSERT INTO gestion (FK01_incidente, FK02_Empleado, FK03_Cliente, FK04_proveedor, FK05_subproceso, FK06_producto, tipo, impacto_cliente, " +
					"impacto_producto, impacto_sistema, descripcion, quien, lugar, cuando, como, causa, solucion, justificacion, fecha, contacto, decision, fk07_contacto, correo_contacto, problema,NAProcedimiento) VALUES ";
			query+= "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			super.jdbcTemplate.update(query, map);
			Long idGestion = super.queryForLong("SELECT IDENT_CURRENT ('gestion')");
			return idGestion;
		}catch(RuntimeException rte){
			return -1L;
		}
	}

	/* (non-Javadoc)
	 * @see mx.com.proquifa.proquifanet.persistencia.comun.GestionDAO#obtenerGestionXId(java.lang.Long)
	 */
	public Gestion obtenerGestionXId(Long idGestion)
			throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idGestion", idGestion);
			String query = "SELECT * FROM gestion WHERE pk_gestion = " + idGestion;
			return(Gestion)super.jdbcTemplate.queryForObject(query,map,  new GestionRowMapper());
		}catch(RuntimeException rte){
			return null;
		}
	}

	public Gestion obtenerGestionXIdIncidente(Long idIncidente)
			throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idIncidente", idIncidente);
			Gestion gestion = (Gestion)super.jdbcTemplate.queryForObject("SELECT * FROM Gestion WHERE fk01_incidente = " + idIncidente,map,  new GestionRowMapper());
			return gestion;
		}catch(RuntimeException rte){
			return null;
		}
	}

	public Gestion obtenerGestionXFolioIncidente(String folio)
			throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("folio", folio);
			Gestion gestion = (Gestion)super.jdbcTemplate.queryForObject("SELECT * FROM Gestion WHERE FK01_Incidente IN (SELECT PK_Incidente FROM Incidente WHERE Folio = '"+ folio +"')",map, new GestionRowMapper());
			return gestion;
		}catch(RuntimeException rte){
			return null;
		}
	}
}