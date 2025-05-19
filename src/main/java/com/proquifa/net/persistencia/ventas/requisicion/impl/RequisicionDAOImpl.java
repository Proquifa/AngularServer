/**
 * 
 */
package com.proquifa.net.persistencia.ventas.requisicion.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.ventas.requisicion.PRequisicion;
import com.proquifa.net.modelo.ventas.requisicion.Requisicion;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.ventas.requisicion.RequisicionDAO;
import com.proquifa.net.persistencia.ventas.requisicion.impl.mapper.RequisicionRowMapper;

/**
 * @author fmartinez
 *
 */
@Repository
public class RequisicionDAOImpl extends DataBaseDAO implements RequisicionDAO {
	
	private Funcion util = null;
	
	@SuppressWarnings("unchecked")
	public List<Requisicion> finRequisiciones(Long idEmpleado) {
		util = new Funcion();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idEmpleado", idEmpleado);
		
			String empleado = (String) super.jdbcTemplate.queryForObject("SELECT Usuario FROM Empleados WHERE Clave="+ idEmpleado,map, String.class);
			String query="SELECT cl.Clave AS idCliente,cl.Nombre,cl.Pais,cl.Ruta,dr.Folio,dr.Ingreso,dr.FHOrigen,dr.Fecha,"+
						 util.obtenerTiempoTranscurridoSQL("dr.FHOrigen","dr.Fecha", "Dias") + "  AS TT " +
						 "FROM DoctosR AS dr "+
						 "LEFT JOIN(SELECT Clave,Nombre,Pais,Ruta FROM Clientes) AS cl ON cl.Clave=dr.Empresa "+
						 "WHERE dr.FProceso IS NULL AND dr.Docto='Requisición' AND dr.Estado IS NULL "+
						 "AND dr.Fecha>'20080101 00:00' AND DR.RPor='"+ empleado +"' ORDER BY cl.Nombre ASC";
			return super.jdbcTemplate.query(query, new RequisicionRowMapper());
		} catch (Exception e) {
			//logger.error("Error: "+ e.getMessage());
			return null;
		}
	}
	
	public Integer insertaDoctoR(Requisicion req) throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("req", req);
			int doctoR=0;
			Object[]params= {req.getIdCliente(),req.getNombreEsac(),req.getNombreEmpleadoEv(), req.getComentarios(), req.getIdContacto()};
			String qry = "INSERT INTO DoctosR(Fecha,Manejo,Origen,Empresa,RPor,Medio,Docto,Ingreso,FOrigen,FHOrigen,Observa,idContacto) VALUES(GETDATE(),'Entrante','Cliente',?,?,'PQFMovil','Requisición',?,0,GETDATE(),?,?)";
				super.jdbcTemplate.update(qry, map);
			
			doctoR = super.queryForInt("SELECT IDENT_CURRENT ('DoctosR')", map);
			return doctoR;
		}catch(RuntimeException e){
			//log.info(e.getMessage());
			return -1;
		}
	}
	
	public Boolean insertarRequisicionMovil(Requisicion requisicion) throws ProquifaNetException{
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("requisicion", requisicion);
			Object params[]={requisicion.getIdDoctoR(),requisicion.getIdCliente(),requisicion.getIdContacto(),requisicion.getIdEmpleadoEv(),
							 requisicion.getIdEmpleadoEsac(), requisicion.getComentarios(), requisicion.getImporte().isNaN()  ? 0 : requisicion.getImporte(), requisicion.getFecha(),requisicion.getFolio(),  requisicion.getIdVisita() != null && requisicion.getIdVisita() > 0 ? requisicion.getIdVisita() : null};
			String sql = "INSERT INTO Requisicion(FK01_DoctoR,FK02_Cliente,FK03_Contacto,FK04_EV,FK05_ESAC,ComentariosAdicionales,Importe,Fecha,Folio, FK06_Visita) VALUES(?,?,?,?,?,?,?,?,?,?)";
			super.jdbcTemplate.update(sql,  map);
			requisicion.setIdRequi(super.queryForInt("SELECT IDENT_CURRENT ('Requisicion')",map) );
			return true;
		}catch(RuntimeException e){
			//log.info(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public Boolean actualizaRequisicionMovil(Requisicion requisicion){
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("requisicion", requisicion);
			Object params[]={requisicion.getComentarios(), requisicion.getFolio(), requisicion.getIdVisita()};
			String sql = "UPDATE Requisicion set ComentariosAdicionales = ?, Folio = ? WHERE FK06_Visita = ?";
			super.jdbcTemplate.update(sql, map);
			requisicion.setIdRequi(super.queryForInt("SELECT IDENT_CURRENT ('Requisicion')"));
			return true;
		}catch(RuntimeException e){
			//log.info(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public Boolean insertarPrequisicionMovil(PRequisicion preq) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("preq", preq);
			Object params[]= {preq.getMarca(),preq.getConcepto(),preq.getPiezasACotizar(),preq.getCantidad(),preq.getUnidad(),preq.getIdDoctor(),preq.getPrecioU()};
			String sql="INSERT INTO PRequisicion (Marca,Concepto,Cant,Cantidad,Unidad,idDoctoR,Precio) VALUES (?,?,?,?,?,?,?)";
			super.jdbcTemplate.update(sql, map);
			return true;
		}catch(RuntimeException e){
			//log.info(e.getMessage());
			return null;
		}
	}
}