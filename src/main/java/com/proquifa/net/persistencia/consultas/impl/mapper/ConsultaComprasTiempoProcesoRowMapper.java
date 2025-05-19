/**
 * 
 */
package com.proquifa.net.persistencia.consultas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.proquifa.net.modelo.comun.TiempoProceso;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author vromero
 *
 */
public class ConsultaComprasTiempoProcesoRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		String des = "";
		TiempoProceso tp = new TiempoProceso();
	
	try{
		tp.setEtapa(rs.getString("ETAPA"));
		tp.setNivel(rs.getInt("NIVEL"));
		tp.setId(rs.getInt("ID"));
		tp.setPadre(rs.getInt("PADRE"));
		if(rs.getDate("FINICIO") != null){
			tp.setFechaInicio(rs.getTimestamp("FINICIO"));
		}
		if(rs.getDate("FFIN") != null){
			tp.setFechaFin(rs.getTimestamp("FFIN"));
		}
		tp.setTotalProceso(rs.getLong("TT"));
		tp.setContacto(rs.getString("CONTACTO"));
		tp.setResponsable(rs.getString("RESPONSABLE"));//Creador
		tp.setReferencia(rs.getString("REFERENCIA"));
		tp.setComentarios(rs.getString("COMENTARIOS"));
		tp.setLote(rs.getString("LOTE"));
		tp.setEdicion(rs.getString("EDICION"));
		tp.setIdioma(rs.getString("IDIOMA"));
		tp.setEtiquetas(rs.getString("ETIQUETAS"));
		tp.setFpor(rs.getString("QUIENCOMPRA"));//Gestiono
		
		
		if(tp.getEtapa().equals("AVISO_PHS") || tp.getEtapa().equals("AVISO_MATRIZ") ){
			tp.setPedimento(rs.getString("MANEJO")); //Decision
			tp.setMedio(rs.getString("PROVEEDOR"));//Origen
			tp.setTipo(rs.getString("DOCUMENTACION"));//Tipo
			tp.setDocumento(rs.getString("PEDIMENTO"));//documentacion
			
		}
		else if(tp.getEtapa().equals("AVISO CAMBIOS")){
			tp.setMedio(rs.getString("PROVEEDOR"));//Origen
			tp.setTipo(rs.getString("Tipo"));//Tipo
			 if(rs.getInt("idPAviso") > 0){
						tp.setCambioActual(true);
					}
			 
			 if(rs.getInt("idPAviso") > 0){
					tp.setCambioActual(true);
				}
			
			
		}
		
		else{
			tp.setManejo(rs.getString("MANEJO")); 
			tp.setProveedor(rs.getString("PROVEEDOR"));			
			tp.setDocumento(rs.getString("DOCUMENTACION"));
			tp.setPedimento(rs.getString("PEDIMENTO"));
		}
		
		if(rs.getString("CADU_MES") == null || rs.getString("CADU_MES").equals("--NA--") || rs.getString("CADU_MES").equals("--ND--")){
			tp.setCaducidad("No especificada");
		}else{
			tp.setCaducidad(rs.getString("CADU_MES") + " " + rs.getString("CADU_ANO"));
		}
		if(rs.getString("DESPACHABLES") != null ){
			if(rs.getInt("DESPACHABLES") == 1){
				des += rs.getString("DESPACHABLES") + " Pza Despachables ";
			}else{
				des += rs.getString("DESPACHABLES") + " Pzas Despachables ";
			}
		}
		if(rs.getString("NODESPACHABLES") != null){
			if(rs.getInt("NODESPACHABLES") == 1){
				des += rs.getString("NODESPACHABLES") + " Pza No Despachables ";
			}else{
				des += rs.getString("NODESPACHABLES") + " Pzas No Despachables ";
			}
		}
		tp.setFechaEsperadaArribo(rs.getTimestamp("FEA"));
		tp.setFechaRealArribo(rs.getTimestamp("FRA"));
		tp.setDespachable(des);
		tp.setTrafico(rs.getString("TRAFICO"));
		tp.setGestor_agente(rs.getString("GESTORAGENTE"));
		
		if(rs.getDate("FechaEstimadaArribo") != null){
	    	tp.setFechaEstimadaArribo(rs.getTimestamp("FechaEstimadaArribo"));
	     }
	    
	    if(rs.getDate("FechaSalidaAduana") != null){
			tp.setFechaSalidaAduana(rs.getTimestamp("FechaSalidaAduana"));
		}
	    
//	    if(rs.getDate("FechaProxGestion") != null){
//			tp.setProxFGestion(rs.getTimestamp("FechaProxGestion"));
//		}
	    
	    
	    if(rs.getDate("FechaPlanificar") != null){
	    	tp.setFechaPlanificacion(rs.getTimestamp("FechaPlanificar"));
	     }
	    
	     if(rs.getInt("idCompra") > 0)
	    {
	    	tp.setIdCompra(rs.getInt("idCompra"));
	    }
	    else {
	    	tp.setIdCompra(0);
	    }
	    
	   
	    if(rs.getString("PaisProveedor") != null)
	    {
	    	tp.setPaisProveedor(rs.getString("PaisProveedor"));
	    }
	    else {
	    	tp.setPaisProveedor(rs.getString(" "));
	    }
	    
	    try {
	    	if(rs.getDate("FechaPedimento") != null)
		    {
		    	tp.setFechaPedimento(rs.getDate("FechaPedimento"));
		    }
		} catch (Exception e) {
			tp.setFechaPedimento(null);
		}
	    
	    try {
	    	if(rs.getDate("FechaProxGestion") != null)
		    {
		    	tp.setProxFGestion(rs.getDate("FechaProxGestion"));
		    }
		} catch (Exception e) {
			tp.setProxFGestion(null);
		}
	    
	    
	    tp.setFletera(rs.getString("Fletera"));
	    tp.setOrdenDespacho(rs.getString("OrdenDespacho"));
	    tp.setAduna(rs.getString("aduana"));
	    tp.setAgenteAduanal(rs.getString("agenteAdunal"));
	    tp.setGuiaEmbarque(rs.getString("GuiaEmbarque"));
	    tp.setFolio(rs.getString("Folio"));
	    tp.setTotalPiezas(rs.getInt("TotalPiezas"));
	    tp.setPiezasSobrantes(rs.getInt("PiezasSobrantes"));
	    tp.setPiezasFaltantes(rs.getInt("PiezasFaltantes"));
	    tp.setRespuesta(rs.getInt("Respuesta"));
	    tp.setEntregarEn(rs.getString("EntregarEn"));

		return tp;
	
	}catch (Exception e) {
		
		return null;
	}
	}

}
