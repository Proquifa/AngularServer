/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.Gestion;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author amartinez
 *
 */
public class GestionRowMapper implements RowMapper {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Boolean aceptado = null;
		Gestion gestion = new Gestion();
		gestion.setIdGestion(rs.getLong("pk_gestion"));
		gestion.setIncidente(rs.getLong("fk01_incidente"));
		gestion.setEmpleado(rs.getLong("fk02_empleado"));
		gestion.setCliente(rs.getLong("fk03_cliente"));
		gestion.setProveedor(rs.getLong("fk04_proveedor"));
		gestion.setSubProceso(rs.getLong("fk05_subproceso"));
		gestion.setProducto(rs.getLong("fk06_producto"));
		gestion.setContacto(rs.getLong("fk07_contacto"));
		gestion.setTipo(rs.getString("tipo"));
		gestion.setImpactoCliente(rs.getBoolean("impacto_cliente"));
		gestion.setImpactoProducto(rs.getBoolean("impacto_producto"));
		gestion.setImpactoSistema(rs.getBoolean("impacto_sistema"));
		gestion.setDescripcion(rs.getString("descripcion"));
		gestion.setQuien(rs.getString("quien"));
		gestion.setLugar(rs.getString("lugar"));
		gestion.setCuando(rs.getString("cuando"));
		gestion.setComo(rs.getString("como"));
		gestion.setCausa(rs.getString("causa"));
		gestion.setSolucion(rs.getString("solucion"));
		aceptado = rs.getBoolean("aceptado");
		if(rs.wasNull()){			
			gestion.setAceptado(null);
			gestion.setSeleccionado(false);
		}else{
			gestion.setAceptado(aceptado);
			gestion.setSeleccionado(true);
		}
		gestion.setJustificacion(rs.getString("justificacion"));
		gestion.setFecha(rs.getTimestamp("fecha"));
		gestion.setNombreContacto(rs.getString("contacto"));
		gestion.setDecision(rs.getBoolean("decision"));
		gestion.setAnalisis(rs.getBoolean("analisis"));
		gestion.setCorreoContacto(rs.getString("correo_contacto"));
		gestion.setProblema(rs.getString("problema"));
		gestion.setNAProcedimiento(rs.getBoolean("NAProcedimiento"));
		if(rs.wasNull()){
			gestion.setProcedimientosAsociados("nulo");
		}else{
			if(rs.getBoolean("NAProcedimiento") == true){
				gestion.setProcedimientosAsociados("verdadero");
			}else if(rs.getBoolean("NAProcedimiento") == false){
				gestion.setProcedimientosAsociados("falso");
			}
		}
		return gestion;
	}

}
