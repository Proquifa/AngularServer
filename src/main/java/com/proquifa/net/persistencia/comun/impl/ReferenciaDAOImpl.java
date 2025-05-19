/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.comun.Referencia;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.comun.ReferenciaDAO;
import com.proquifa.net.persistencia.comun.impl.mapper.ReferenciaRowMapper;


/**
 * @author amartinez
 *
 */
@Repository
public class ReferenciaDAOImpl extends DataBaseDAO implements ReferenciaDAO {
	/* (non-Javadoc)
	 * @see mx.com.proquifa.proquifanet.persistencia.comun.ReferenciaDAO#insertarReferencia(mx.com.proquifa.proquifanet.modelo.comun.Referencia)
	 */
	public Long insertarReferencia(Referencia referencia) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("referencia", referencia);
			Object[] params =  {referencia.getIdIncidente(), referencia.getExtensionArchivo(), referencia.getFolio(), referencia.getOrigen(),
					referencia.getIdUsuario(), referencia.getJustificacion(),referencia.getNombre(),referencia.getIdAccion()};
			StringBuilder sbQuery = new StringBuilder("INSERT INTO referencia (FK01_Incidente, extension_archivo, folio, origen, FK01_Usuario,Justificacion,Nombre,");
			sbQuery.append("FK02_Accion) VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
			super.jdbcTemplate.update(sbQuery.toString(), map);
			Long idReferencia = super.queryForLong("SELECT IDENT_CURRENT ('referencia')");
			return idReferencia;
		} catch (RuntimeException e) {
			return -1L;
		}
	}

	/* (non-Javadoc)
	 * @see mx.com.proquifa.proquifanet.persistencia.comun.ReferenciaDAO#obtenerReferenciaXIdIncidente(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public List<Referencia> obtenerReferenciaXIdIncidente(Long idIncidente) {
		String query = "";
		try {
			query = "SELECT PK_Referencia,FK01_Incidente,Extension_archivo,Folio,Origen,FK01_Usuario,Cast(Justificacion as nvarchar(max)) as Justificacion"+
					",NULL AS Usuario FROM Referencia WHERE FK01_Usuario IS NULL AND FK01_Incidente="+ idIncidente + " UNION "+
					"SELECT R.PK_Referencia,R.FK01_Incidente,R.Extension_archivo,R.Folio,R.Origen,R.FK01_Usuario,Cast(R.Justificacion as nvarchar(max)) as Justificacion,"+
					"E.Usuario FROM Referencia AS R, Empleados AS E WHERE R.FK01_Usuario=E.Clave AND FK01_Incidente="+ idIncidente + " "+
					"ORDER BY PK_Referencia ASC";
			List<Referencia>  referencias = super.jdbcTemplate.query(query, new ReferenciaRowMapper());
			return referencias;
		} catch (Exception e) {
		//	logger.error("Error: " + e.getMessage());
			return null;
		}		
	}

	public Boolean borrarReferencia(Referencia referencia) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("referencia", referencia);
			super.jdbcTemplate.update("DELETE referencia WHERE pk_referencia = " + referencia.getIdReferencia(), map);
			return true;
		}catch(RuntimeException e){
			return false;
		}
	}

	public Boolean existeReferencia(Referencia referencia) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("referencia", referencia);
			String query = "";
			query = "SELECT R.PK_Referencia,R.FK01_Incidente,R.Extension_archivo,R.Folio,R.Origen,R.FK01_Usuario,Cast(R.Justificacion as nvarchar(max)) as Justificacion,"+
					",E.Usuario FROM Referencia AS R, Empleados AS E WHERE R.FK01_Usuario=E.Clave AND PK_Referencia="+ referencia.getIdReferencia() +" UNION "+
					"SELECT R.PK_Referencia,R.FK01_Incidente,R.Extension_archivo,R.Folio,R.Origen,R.FK01_Usuario,Cast(R.Justificacion as nvarchar(max)) as Justificacion,"+
					",NULL AS Usuario FROM Referencia AS R, Empleados AS E WHERE R.FK01_Usuario IS NULL AND PK_Referencia="+ referencia.getIdReferencia();
			Referencia temp = (Referencia) super.jdbcTemplate.queryForObject(query, map, new ReferenciaRowMapper());
			Boolean existe = true;
			if(temp == null){
				existe = false;
			}
			return existe;
		}catch(RuntimeException e){
			return false;
		}
	}

	public Boolean actualizarReferencia(Referencia nueva) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nueva", nueva);
			Object params [] = {nueva.getIdIncidente(), nueva.getExtensionArchivo(), nueva.getFolio(), nueva.getOrigen(), nueva.getJustificacion(), nueva.getIdReferencia()};
			super.jdbcTemplate.update("UPDATE referencia SET FK01_incidente = ?, extension_archivo = ?, folio = ?, origen = ? , Justificacion = ? WHERE pk_referencia = ?", map);
			return true;
		}catch(RuntimeException e){
			return false;
		}
	}
}