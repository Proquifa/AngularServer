/**
 * 
 */
package com.proquifa.net.persistencia.ventas.admoncomunicacion.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.proquifa.net.modelo.ventas.PartidaCotizacion;
import com.proquifa.net.modelo.ventas.admoncomunicacion.Llamada;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.ventas.admoncomunicacion.LlamadaDAO;
import com.proquifa.net.persistencia.ventas.admoncomunicacion.impl.mapper.LlamadaRowMapper;

import org.springframework.stereotype.Repository;

/**
 * @author ernestogonzalezlozada
 *
 */
@Repository
public class LlamadaDAOImpl extends DataBaseDAO implements LlamadaDAO {

	/* (non-Javadoc)
	 * @see mx.com.proquifa.proquifanet.persistencia.admoncomunicacion.LlamadaDAO#guardarLlamada(mx.com.proquifa.proquifanet.modelo.admoncomunicacion.Llamada)
	 */
	public void guardarLlamada(Llamada llamada){
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("llamada", llamada);
			map.put("recibo", llamada.getRecibio());
			map.put("Destino", llamada.getDestino());
			map.put("Empresa", llamada.getEmpresa());
			map.put("Contacto", llamada.getContacto());
			map.put("Asunto", llamada.getAsunto());
			map.put("Atendio", llamada.getAtendio());
			map.put("Respuesta", llamada.getRespuesta());
			map.put("Mensaje", llamada.getMensaje());
			map.put("Origen", llamada.getOrigen());
			map.put("Estado", llamada.getEstado());
			SimpleDateFormat simpleDateF = new SimpleDateFormat("yyyyMMdd HH:mm");
			Date fecha = simpleDateF.parse(llamada.getFechaHora());
			map.put("fecha",fecha);
			map.put("simpleDateF", simpleDateF);
//			logger.debug("Fecha a insertar: " + fecha);
			Object[] params =  {fecha, llamada.getRecibio(), llamada.getDestino(), llamada.getEmpresa(),
					llamada.getContacto(), llamada.getAsunto(), llamada.getAtendio(), llamada.getRespuesta(), 
					llamada.getMensaje(), llamada.getOrigen(), llamada.getEstado()};
			String query = "INSERT INTO Llamadas (Fecha,Recibio,Destino,Empresa,Contacto,Asunto,Atendio,Respuesta,Mensaje,TOrigen, Estado) VALUES ";
			query += "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
			super.jdbcTemplate.update(query, map);
		}catch(ParseException exception){
			exception.printStackTrace();
		}
	}
	/* (non-Javadoc)
	 * @see mx.com.proquifa.proquifanet.persistencia.admoncomunicacion.LlamadaDAO#obtenerLlamadas()
	 */
	
	@SuppressWarnings("unchecked")
	public List<Llamada> obtenerLlamadas(Llamada parametrosLlamada) {
		String condiciones = "";
		SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyyMMdd");
		try {
			if(parametrosLlamada!=null){
				if(parametrosLlamada.getFechaInicio()!=null && parametrosLlamada.getFechaFin()!=null){
					condiciones = condiciones + " AND (Fecha>='"+ formatoDeFecha.format(parametrosLlamada.getFechaInicio()) +" 00:00' AND Fecha <= '"+ formatoDeFecha.format(parametrosLlamada.getFechaFin()) +" 23:59')";
					if(parametrosLlamada.getEmpresa()!=null && !parametrosLlamada.getEmpresa().equals("--TODOS--")){
						condiciones = condiciones + " AND Empresa = '"+ parametrosLlamada.getEmpresa() +"'";
					}
					if(parametrosLlamada.getDestino()!=null && !parametrosLlamada.getDestino().equals("--TODOS--")){
						condiciones = condiciones + " AND Destino = '"+ parametrosLlamada.getDestino() +"'";
					}
					if(parametrosLlamada.getAtendio()!=null && !parametrosLlamada.getAtendio().equals("--TODOS--")){
						condiciones = condiciones + " AND Atendio = '"+ parametrosLlamada.getAtendio() +"'";
					}
					if(parametrosLlamada.getEstado()!=null && !parametrosLlamada.getEstado().equals("--TODOS--")){
						if (parametrosLlamada.getEstado().equals("ABIERTA")){
							condiciones = condiciones + " AND LLAM.Estado = 'EN ESPERA'";
						}
						if (parametrosLlamada.getEstado().equals("CERRADA")){
							condiciones = condiciones + " AND LLAM.Estado = 'CERRADA'";
						}
					}					
				}else{
					condiciones = " AND Atendio = '"+ parametrosLlamada.getAtendio() +"' AND Estado = 'EN ESPERA'";
				}
			}
			
			String query= " SELECT *,CASE WHEN LLAM.Estado='CERRADA' THEN 'NA' ELSE CASE WHEN (DATEDIFF(DAY, LLAM.FECHA, GETDATE()) >= ENT.TIEMPO) THEN 'FT' ELSE 'ET' END END ENTIEMPO, "+
						  " CASE WHEN Cotiza IS NULL THEN '0' ELSE 1 END Coti " +
						  " FROM Llamadas AS LLAM "+
						  " LEFT JOIN (SELECT CASE  WHEN DATENAME(weekday,GETDATE()) ='Lunes' THEN 4 "+
						  " WHEN DATENAME(weekday, GETDATE()) ='Martes' THEN 4 ELSE 2 END AS TIEMPO,Folio,Estado FROM Llamadas WHERE Estado='EN ESPERA')   "+
						  " AS ENT ON ENT.Folio=LLAM.Folio WHERE  Fecha >= '20100101 00:00'" + condiciones;
			
			return super.jdbcTemplate.query(query, new LlamadaRowMapper());
		} catch (Exception e) {
			//logger.error("Error: " + e.getMessage());
			return null;
		}		
	}

	public Boolean actulizarLLamada(Llamada llamada) {
		try{		
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("llamada", llamada);
			map.put("Recibo", llamada.getRecibio());
			map.put("Destino", llamada.getDestino());
			map.put("Empresa", llamada.getEmpresa());
			map.put("Contacto", llamada.getContacto());
			map.put("Asunto", llamada.getAsunto());
			map.put("Atendio", llamada.getAtendio());
			map.put("Respuesta", llamada.getRespuesta());
			map.put("Mensaje", llamada.getMensaje());
			map.put("Origen", llamada.getOrigen());
			map.put("Cotiza", llamada.getCotiza());
			map.put("Estado", llamada.getEstado());
			map.put("EnteradoNotificacion", llamada.getEnteradoNotificacion());
			map.put("Comentarios", llamada.getComentarios());
			map.put("Folio", llamada.getFolio());
			if(llamada.getEnteradoNotificacion()==true){
				llamada.setEstado("CERRADA");
			}else if(llamada.getEnteradoNotificacion()==false){
				llamada.setEstado("EN ESPERA");
			}
			
			Object [] params = {new Date(),llamada.getRecibio(),llamada.getDestino(),llamada.getEmpresa(),llamada.getContacto(),
					llamada.getAsunto(),llamada.getAtendio(),llamada.getRespuesta(),llamada.getMensaje(),llamada.getOrigen(),
					llamada.getCotiza(),llamada.getEstado(),llamada.getEnteradoNotificacion(),llamada.getComentarios(),llamada.getFolio()};
			String query = "UPDATE Llamadas SET Fecha = ?,Recibio = ?,Destino = ?,Empresa = ?, Contacto = ?, Asunto = ?, Atendio = ?, Respuesta = ?, Mensaje = ?,"+
				"TOrigen = ?, Cotiza = ?, Estado = ?, chkEstado = ?, Comentarios = ? WHERE Folio = ?";
			super.jdbcTemplate.update(query, map);
			return true;
		}catch(RuntimeException rte){
			//logger.error("Error: " + rte.getMessage());
			return false;
		} 
	}

	public void insertarPartidaLlamada(PartidaCotizacion pcotiza, Long folioDoctoR) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pcotiza", pcotiza);
			map.put("folioDoctoR", folioDoctoR);
			map.put("Partida", pcotiza.getPartida());
			map.put("Cantidad", pcotiza.getCantidad());
			map.put("Codigo", pcotiza.getCodigo());
			map.put("Concepto", pcotiza.getConcepto());
			map.put("ComentariosRequisicion", pcotiza.getComentariosRequisicion());
			map.put("Fabrica", pcotiza.getFabrica());
			Object[] params = {folioDoctoR,pcotiza.getPartida(),pcotiza.getCantidad(),pcotiza.getCodigo(),
					pcotiza.getConcepto(),pcotiza.getComentariosRequisicion(),pcotiza.getFabrica()};
			String query = "INSERT INTO DoctoLLamada (Folio,Part,Cant,Codigo,Concepto,Comenta,Fabrica) VALUES ";
			query += "(?, ?, ?, ?, ?, ?, ?)";
			super.jdbcTemplate.update(query, map);
		}catch(RuntimeException exception){
			exception.printStackTrace();
		}
	}

}