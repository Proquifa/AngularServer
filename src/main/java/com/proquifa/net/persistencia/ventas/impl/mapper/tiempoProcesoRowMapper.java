package com.proquifa.net.persistencia.ventas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.comun.Producto;
import com.proquifa.net.modelo.comun.TiempoProceso;
import com.proquifa.net.modelo.ventas.PartidaCotizacion;

public class tiempoProcesoRowMapper implements RowMapper {
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		TiempoProceso tiempoPro = new TiempoProceso();
		PartidaCotizacion pc = new PartidaCotizacion();
		Producto prod = new Producto();
		
		tiempoPro.setProceso(rs.getString("Proceso"));
		tiempoPro.setTipoFlujo("Nuevo");
		if(rs.getTimestamp("FInicio") != null){ tiempoPro.setFechaInicio(rs.getTimestamp("FInicio")); }
		if(rs.getTimestamp("FFin") != null){ tiempoPro.setFechaFin(rs.getTimestamp("FFin")); }
		
		if (rs.getString("Proceso").equals("GENERALES")){
			tiempoPro.setOrdenProceso(3); //Se asigna el Oreden 3 para que VISTA lo coloque en la tercera posicion
			if(rs.getTimestamp("FRecepcion") != null){ tiempoPro.setFechaRecepcion(rs.getTimestamp("FRecepcion")); } 			
		}
		else if (rs.getString("Proceso").equals("EVALUAR RESULTADOS")){
			tiempoPro.setTotalProceso(rs.getLong("TimepoTotal"));
			tiempoPro.setResponsable(rs.getString("Responsable"));
			tiempoPro.setComentarios(rs.getString("ObservaP"));
		}
		else if (rs.getString("Proceso").equals("VALIDAR pDp")){
			tiempoPro.setTotalProceso(rs.getLong("TimepoTotal"));
			tiempoPro.setResponsable(rs.getString("Responsable"));
			tiempoPro.setComentarios(rs.getString("Observa"));
			tiempoPro.setEstado(rs.getString("Estado"));
		}
		else{
			tiempoPro.setTotalProceso(rs.getLong("TimepoTotal"));
			tiempoPro.setResponsable(rs.getString("Responsable"));
			tiempoPro.setProveedor(rs.getString("Proveedor"));
			prod.setProveedor(rs.getLong("idProveedor"));
			
			prod.setFabrica(rs.getString("Fabrica"));
			prod.setCodigo(rs.getString("Codigo"));
			prod.setDescripcion(rs.getString("Concepto"));
			prod.setManejo(rs.getString("Manejo"));
			prod.setTipo(rs.getString("Tipo"));
			prod.setSubtipo(rs.getString("Subtipo"));
			prod.setControl(rs.getString("Control"));
			prod.setClasificacion(rs.getString("Clasificacion"));
			
			if(rs.getString("Subtipo") != null ) {
				if(rs.getString("Subtipo").equals("Químico") || rs.getString("Subtipo").equals("Biológico")){
					prod.setCas(rs.getString("CAS"));
					prod.setDepositarioInter("ND");
				}
				else{
					prod.setCas("ND");
					prod.setDepositarioInter(rs.getString("DepositarioInternacional"));
				}
			}
			prod.setEstadoFisico(rs.getString("EdoFisico"));
			prod.setCosto(rs.getDouble("Precio"));
			prod.setMoneda(rs.getString("Moneda"));
			
			if (rs.getString("Proceso").equals("ATENDER ALTA DE PRODUCTO") || rs.getString("Proceso").equals("CONFIRMAR DATOS INTERNACIONAL")){
				prod.setFraccionArancelaria(rs.getString("FraccionArancelaria"));
				prod.setDTA(rs.getDouble("DTA"));
				prod.setIGI(rs.getDouble("IGI"));
				prod.setRequierePermisoImp(rs.getString("PermisoImp"));
				prod.setTipoPermiso(rs.getString("TipoPermiso"));
				
				if (rs.getString("Proceso").equals("ATENDER ALTA DE PRODUCTO")){
					if (rs.getTimestamp("FIngreso") != null){ prod.setFecha(rs.getTimestamp("FIngreso"));} //Fecha de Ingreso
				}
			}			
			pc.setProducto(prod);
			tiempoPro.setPcotiza(pc);
		}
		
		return tiempoPro;
	}
}
