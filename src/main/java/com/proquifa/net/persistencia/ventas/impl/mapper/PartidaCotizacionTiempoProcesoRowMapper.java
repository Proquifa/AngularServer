/**
 * 
 */
package com.proquifa.net.persistencia.ventas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.Producto;
import com.proquifa.net.modelo.comun.TiempoProceso;
import com.proquifa.net.modelo.ventas.PartidaCotizacion;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author fmartinez
 *
 */
public class PartidaCotizacionTiempoProcesoRowMapper implements RowMapper {
	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		PartidaCotizacion pc = new PartidaCotizacion();
		TiempoProceso pcotizaTP = new TiempoProceso();
		Producto prod = new Producto();
		String clienteProveedor ="";
		String comentarios="";

		pcotizaTP.setProceso(rs.getString("Proceso"));
		pcotizaTP.setFechaInicio(rs.getTimestamp("FechaInicio"));
		pcotizaTP.setFechaFin(rs.getTimestamp("FechaFin"));
		pcotizaTP.setMedio(rs.getString("Medio"));
		clienteProveedor = rs.getString("ClienteProveedor");
		if(pcotizaTP.getProceso().equals("Registro")){
			pcotizaTP.setCliente(clienteProveedor);
		}else if(pcotizaTP.getProceso().equals("Investigación") || pcotizaTP.getProceso().equals("Evaluar respuesta")){
			pcotizaTP.setProveedor(clienteProveedor);
		}else{
			pcotizaTP.setCliente("No Aplica");
			pcotizaTP.setProveedor("No Aplica");
		}
		pcotizaTP.setContacto(rs.getString("Contacto"));
		pcotizaTP.setResponsable(rs.getString("Responsable"));
		pcotizaTP.setReferencia(rs.getString("Referencia"));	
		pc.setClasifOrigen(rs.getString("ClasificacionInicial"));
		pc.setClasifFinal(rs.getString("ClasificacionFinal"));
		pcotizaTP.setTipoProveedor(rs.getString("TProvee"));
		prod.setTipo(rs.getString("Tipo"));
		pc.setCodigo(rs.getString("Catalogo"));
		pc.setDescripcion(rs.getString("Concepto"));
		pc.setPresentacion(rs.getString("Presentacion"));
		pc.setFabrica(rs.getString("Marca"));
		pc.setPrecio(rs.getFloat("Precio"));
		prod.setDisponibilidad(rs.getString("Disponibilidad"));
		prod.setManejo(rs.getString("Manejo"));
		pc.setTiempoEntrega(rs.getString("TEntrega"));
		pc.setCargosEnviosAdicionales(rs.getString("CargosEnviosAdicionales"));
		pc.setHieloSeco(rs.getString("HieloSeco"));
		comentarios = rs.getString("ComentariosAdicionales");
		pc.setComentariosAdicionales(comentarios);
		pc.setRechazoESAC(rs.getString("RechazoESAC"));
		pc.setRechazoPharma(rs.getString("RechazoPharma"));
		pc.setEnSTOCK(rs.getString("EnSTOCK"));
		pc.setUltimaEdicion(rs.getString("UltimaEdicion"));
		pc.setCostosAdicionales(rs.getString("CostosAdicionales"));
		pc.setEstadoPedido(rs.getString("SituacionPedido"));
		pc.setProducto(prod);
		pcotizaTP.setPcotiza(pc);
		pcotizaTP.setTotalProceso(rs.getLong("TT"));
		pcotizaTP.setComentarios(comentarios);
		
		return pcotizaTP;
	}
}
