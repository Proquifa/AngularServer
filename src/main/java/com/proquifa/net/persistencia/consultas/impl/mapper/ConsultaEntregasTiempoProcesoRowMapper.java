/**
 * 
 */
package com.proquifa.net.persistencia.consultas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.comun.TiempoProceso;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author vromero
 *
 */
public class ConsultaEntregasTiempoProcesoRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		TiempoProceso proceso = new TiempoProceso();
		Funcion f = new Funcion();
		
		proceso.setProceso(rs.getString("Evento"));
		proceso.setFechaInicio(rs.getTimestamp("FechaInicio"));
		proceso.setFechaFin(rs.getTimestamp("FechaFin"));
		proceso.setResponsable(rs.getString("Responsable"));
		proceso.setEtiquetas(rs.getString("Etiqueta"));
		proceso.setFechaFacturacion(rs.getTimestamp("FechaFacturacion"));
		proceso.setFechaTramitacion(rs.getTimestamp("FechaTramitacion"));
		proceso.setFechaSurtido(rs.getTimestamp("FechaSurtido"));
		proceso.setFechaEjecucion(rs.getTimestamp("FechaEjecucion"));
		proceso.setFechaCierre(rs.getTimestamp("FechaCierre"));		
		proceso.setComentarios(rs.getString("ComentariosGestor"));
		proceso.setFolio(rs.getString("CajaColectora"));
		proceso.setDocumento(rs.getString("DocumentosResultantes"));
		proceso.setEntregaRevision(rs.getBoolean("EntregayRevision"));
		proceso.setRefacturacion(rs.getString("Refacturacion"));
		if (rs.getString("Ruta")!=null && !rs.getString("Ruta").equals("") ) {
			proceso.setRutaMensajeria(f.convertirPalabrasClaves(rs.getString("Ruta")));
		}
		proceso.setZonaMensajeria(rs.getString("Zona"));
		proceso.setConforme(rs.getString("Conforme"));
		proceso.setEntrega(rs.getString("Entrega"));
		proceso.setEstadoRuta(rs.getString("EstadoRuta"));
		proceso.setFpor(rs.getString("FPor"));
		proceso.setTiempoProceso(rs.getString("TP"));
		try {
			proceso.setReferencia(rs.getString("FolioNE"));
		} catch (Exception e) {
		}
		return proceso;
	}
}