package com.proquifa.net.persistencia.despachos.mensajero.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.despachos.mensajero.PendientesMensajero;

public class PendientesCerradosRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		PendientesMensajero pendiente = new PendientesMensajero();
    	pendiente.setEvento(rs.getString("TEvento"));
    	pendiente.setIdCliente(rs.getLong("Clave"));
    	pendiente.setEmpresa(rs.getString("Nombre"));
    	pendiente.setFolioEvento(rs.getString("FolioEvento"));
    	pendiente.setIdProveedor(rs.getLong("idProveedor"));
    	pendiente.setRuta(rs.getString("ZonaMensajeria"));
    	pendiente.setDiferenteDireccion(rs.getBoolean("DifDireccion"));
    	pendiente.setDireccion(rs.getString("Direccion"));
    	if(rs.getString("RazonesEntrega")!=null){
    		pendiente.setJustificacion(rs.getString("RazonesEntrega"));
    	}else{
    		pendiente.setJustificacion("");
    	}
    	pendiente.setTipoJustificacion(rs.getString("TipoJustificacion"));
    	if(rs.getString("Entrega")!=null && !rs.getString("Entrega").equals("No realizada")){
    		pendiente.setEstadoEntrega(rs.getString("Entrega"));  
    		pendiente.setRealizado(true);
    	}else if(rs.getString("Entrega")==null){
    		pendiente.setEstadoEntrega("Pendiente");
    		pendiente.setRealizado(false);
    	}else if(rs.getString("Entrega").equals("No realizada")){
    		pendiente.setEstadoEntrega(rs.getString("Entrega"));
    		pendiente.setRealizado(false);
    	}
    	if(rs.getString("EstadoRuta").equals("ACerrar")){
    		pendiente.setEstadoPendiente("Cerrado");
    	}else if(rs.getString("EstadoRuta").equals("AEjecutar")){
    		pendiente.setEstadoPendiente("Abierto");
    	}else if(rs.getString("EstadoRuta").equals("EnEjecucion")){
    		pendiente.setEstadoPendiente("En cierre");
    	}
    	if(rs.getString("Prioridad")!=null){
    		pendiente.setPrioridad(rs.getString("Prioridad"));     		
    	}else{
    		pendiente.setPrioridad("Normal");
    	}
    	
       	if(rs.getString("folioPPKL") != null) {
    		pendiente.setFolioProducto(rs.getString("folioPPKL"));
    	}else {
    		pendiente.setFolioProducto("N/A");
    	}
    	if(rs.getString("folioPKL") != null) {
			if (rs.getString("folioPKL").contains("PL-")){
				pendiente.setFolioDocumento(rs.getString("folioPKL"));
			}else
				pendiente.setFolioDocumento("N/A");
		}else
			pendiente.setFolioDocumento("N/A");
    	
    	pendiente.setRealizadoTxt(rs.getString("Entrega"));
        return pendiente;

	}

}
