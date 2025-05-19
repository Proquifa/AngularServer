/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.comun.Folio;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.comun.FolioDAO;
import com.proquifa.net.persistencia.comun.impl.mapper.ConsecutivoDosDigitosRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.ConsecutivoMasUnoRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.ConsecutivoRowMapper;

/**
 * @author ernestogonzalezlozada
 *
 */

@Repository
public class FolioDAOImpl extends DataBaseDAO implements FolioDAO {
	private Date fecha;
	
	final Logger log = LoggerFactory.getLogger(FolioDAOImpl.class);	
	
	@SuppressWarnings("unchecked")
	public Folio obtenerFolioPorConcepto(String concepto,boolean masUno) {
		fecha = new Date();
		Format formatter = new SimpleDateFormat("MMddyy");
		Folio folio = null;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("concepto", concepto);
		if(concepto.equals("ConfirmacionEntrega")){
			folio = (Folio)super.jdbcTemplate.queryForObject("SELECT valor FROM consecutivos WHERE concepto = :concepto",map, new ConsecutivoDosDigitosRowMapper());
		}else if(masUno){
			if( concepto.equalsIgnoreCase("RT")) {
				folio = (Folio) super.jdbcTemplate.queryForObject("SELECT   valor  FROM consecutivos WHERE letras = :concepto",map, new ConsecutivoMasUnoRowMapper());
			}else {
				String query ="SELECT valor FROM consecutivos WHERE concepto = :concepto"; 
				log.info(query);
				folio = (Folio) super.jdbcTemplate.queryForObject(query, map, new ConsecutivoMasUnoRowMapper());
			}
			if( folio != null && folio.getValor() != null && !folio.getValor().equals("") && concepto.equalsIgnoreCase("RT")) {
				super.jdbcTemplate.update("UPDATE Consecutivos SET Valor = " + folio.getValor() +" WHERE letras = :concepto",map);
			}else if (folio != null && folio.getValor() != null && !folio.getValor().equals("")) {
				super.jdbcTemplate.update("UPDATE Consecutivos SET Valor = " + folio.getValor() +" WHERE concepto = :concepto",map);
			}
		}else{
			folio = (Folio) super.jdbcTemplate.queryForObject("SELECT valor FROM consecutivos WHERE concepto = :concepto",map, new ConsecutivoRowMapper());
		}
			
		if(concepto.toLowerCase().equals("cotizas") || concepto.toLowerCase().equals("pedidos")){
			folio.setFolioCompleto(formatter.format(fecha) + "-" + folio.getValor());
		}else if(concepto.toLowerCase().equals("incidente")){
			folio.setFolioCompleto("INC-" + formatter.format(fecha) + "-" + folio.getValor());
		}else if(concepto.toLowerCase().equals("requisicionpharma")){
			folio.setFolioCompleto("RPH-" + formatter.format(fecha) + "-" + folio.getValor());
		}else if(concepto.toLowerCase().equals("proformas")){
			folio.setFolioCompleto("PR-" + formatter.format(fecha) + "-" + folio.getValor());
		}else if(concepto.toLowerCase().equals("cnc real")){
			folio.setFolioCompleto("CNC-" + formatter.format(fecha) + "-" + folio.getValor());
		}else if(concepto.toLowerCase().equals("requisicionmovil")){
			folio.setFolioCompleto("R-" + formatter.format(fecha) + "-" + folio.getValor());
		}else if(concepto.toLowerCase().equals("solicitud de visita")){
			folio.setFolioCompleto("SVT-" + formatter.format(fecha) + "-" + folio.getValor());
		}else if(concepto.toLowerCase().equals("visita")){
			folio.setFolioCompleto("VIS-" + formatter.format(fecha) + "-" + folio.getValor());
		}else if(concepto.toLowerCase().equals("referencia solicitud visita")){
			folio.setFolioCompleto("RSV-" + formatter.format(fecha) + "-" + folio.getValor());
		}else if(concepto.toLowerCase().equals("expofarma")){
			folio.setFolioCompleto("EXP-" + formatter.format(fecha) + "-" + folio.getValor());
		}else if(concepto.toLowerCase().equals("confirmacionentrega")){
			folio.setFolioCompleto("NE-" + formatter.format(fecha) + "-" + folio.getValor());
		}else if(concepto.toLowerCase().equals("factura proveedor")){
			folio.setFolioCompleto("FP-" + formatter.format(fecha) + "-" + folio.getValor());
		}else if(concepto.toLowerCase().equals("factura proveedor otros")){
			folio.setFolioCompleto("OFP-" + formatter.format(fecha) + "-" + folio.getValor());
		}else if(concepto.toLowerCase().equals("pago")){
			folio.setFolioCompleto("PG-" + formatter.format(fecha) + "-" + folio.getValor());
		}else if(concepto.toLowerCase().equals("listaarribo")){
			folio.setFolioCompleto("LA-" + formatter.format(fecha) + "-" + folio.getValor());
		}else if(concepto.equalsIgnoreCase("OrdenDespacho")){
			folio.setFolioCompleto("OD-" + formatter.format(fecha) + "-" + folio.getValor());
		}else if(concepto.equalsIgnoreCase("Acuse de Recibo")){
			folio.setFolioCompleto("AR-" + formatter.format(fecha) + "-" + folio.getValor());
		}else if(concepto.equalsIgnoreCase("PackingList")){
			folio.setFolioCompleto("PL-" + formatter.format(fecha) + "-" + folio.getValor() );
		}else if(concepto.equalsIgnoreCase("DocumentoPedimento")){
			folio.setFolioCompleto("PD-" + formatter.format(fecha) + "-" + folio.getValor() );
		}else if(concepto.equalsIgnoreCase("NT")){
			folio.setFolioCompleto("NT-" + formatter.format(fecha) + "-" + folio.getValor() );
		}else if(concepto.equalsIgnoreCase("RespuestaAvisoDeCambios")){
			folio.setFolioCompleto("RAC-" + formatter.format(fecha) + "-" + folio.getValor() );
		}else if(concepto.equalsIgnoreCase("Compras")){
			folio.setFolioCompleto(formatter.format(fecha) +"-"+ folio.getValor());
		}else if(concepto.equalsIgnoreCase("Inspeccion")){
			folio.setFolioCompleto("LI-"+formatter.format(fecha) +"-"+ folio.getValor());
		}else if(concepto.equalsIgnoreCase("PL")){
			folio.setFolioCompleto("PL-"+formatter.format(fecha) +"-"+ folio.getValor());
		}else if(concepto.equalsIgnoreCase("ContratoCliente")){
			folio.setFolioCompleto(formatter.format(fecha) +"-"+ folio.getValor());
		}else if(concepto.equalsIgnoreCase("Grabacion Lote Inspeccion")){
			folio.setFolioCompleto("GLI-"+formatter.format(fecha) +"-"+ folio.getValor());
		}else if (concepto.equalsIgnoreCase("Grabacion Embalar")){
			folio.setFolioCompleto("GLE-" + formatter.format(fecha) + "-" + folio.getValor());
		}else if(concepto.equalsIgnoreCase("OrdenEntrega")){
		folio.setFolioCompleto("OE-"+formatter.format(fecha) +"-"+ folio.getValor());
		}else if(concepto.equalsIgnoreCase("Folio entrega")){
			folio.setFolioCompleto("ET-"+formatter.format(fecha) +"-"+ folio.getValor());
		}else if(concepto.equalsIgnoreCase("Folio rechazo")){
			folio.setFolioCompleto("FR-"+formatter.format(fecha) +"-"+ folio.getValor());
		}else if(concepto.equalsIgnoreCase("Despachos")){
			folio.setFolioCompleto("DP-"+formatter.format(fecha) +"-"+ folio.getValor());
		}else if(concepto.equalsIgnoreCase("RL")){
			folio.setFolioCompleto("RL-"+formatter.format(fecha) +"-"+ folio.getValor());
	}else if(concepto.equalsIgnoreCase("FE")){
		folio.setFolioCompleto("FE-"+formatter.format(fecha) +"-"+ folio.getValor());
	}else if(concepto.equalsIgnoreCase("RT")){
		folio.setFolioCompleto("RT-"+formatter.format(fecha) +"-"+ folio.getValor());
	} else if(concepto.equalsIgnoreCase("Reclamo")) {
		folio.setFolioCompleto("RA-" + formatter.format(fecha) + "-" + folio.getValor());
	} else if(concepto.equalsIgnoreCase("SaldoAFavor")) {
		folio.setFolioCompleto("SAF-" + formatter.format(fecha) + "-" + folio.getValor());
	} else if(concepto.equalsIgnoreCase("Destrucción producto")) {
		folio.setFolioCompleto("DS-" + formatter.format(fecha) + "-" + folio.getValor());
	}
		
//		logger.info(folio.getFolioCompleto());
		return folio;
	}

	public Long obtenerConsecutivoFolio(String concepto) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("concepto", concepto);
		return super.queryForLong("SELECT valor FROM consecutivos WHERE concepto = :concepto ");
	}

	public void actualizarValorConsecutivo(String concepto, Long valor) {
		try {
		String query = "";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("concepto", concepto);
		if(concepto.equals("ConfirmacionEntrega") && valor > 99){
			valor = 0L;
		}

		query = "UPDATE consecutivos SET valor = " + (valor + 1) + " WHERE concepto = :concepto";
		
		super.jdbcTemplate.update(query,map);
		} catch (Exception e) {
			//logger.info("Error: "+ e.getMessage());
			e.printStackTrace();
		}
	}

	public Long getConsecutivoMasUnoXConcepto(String concepto) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("concepto", concepto);
			
			String query = "DECLARE @formato varchar(10) = '0000' " +
					"SELECT (CASE WHEN RIGHT(@formato + CAST(cs.Valor AS varchar), 4) > '9999' THEN '0000' ELSE RIGHT(@formato + CAST((cs.Valor + 1) AS varchar), 4) END ) AS Valor " +
					"FROM Consecutivos AS cs " +
					"WHERE cs.Concepto= :concepto";
			log.info(query);
			return super.queryForLong(query, map);  
		} catch (Exception e) {
			//logger.info("Error: "+ e.getMessage());
			return null;
		}
	}
	
	@Override
	public Long getConsecutivoMasUnoXConceptoFolio(String concepto) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("concepto", concepto);
			
			String query = "Select valor from consecutivos WHERE Concepto = :concepto";
			log.info(query);
			return super.queryForLong(query, map);  
		} catch (Exception e) {
			//logger.info("Error: "+ e.getMessage());
			return null;
		}
	}

	public long getEstaBloqueadoXConcepto(String concepto) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("concepto", concepto);
			String query = "DECLARE @formato varchar(10) = '0000' " +
					"SELECT count(cs.esBloqueado) " +
					"FROM Consecutivos AS cs " +
					"WHERE cs.Concepto= :concepto and cs.esBloqueado = 1";
			return super.queryForLong(query, map);
		} catch (Exception e) {
			//logger.info("Error: "+ e.getMessage());
			return 0L;
		}
	}

	@Override
	public int bloquear(String concepto, Boolean bloqueo) {
		try {
			int bloquear;
			if(bloqueo==true) bloquear = 1;
			else bloquear= 0;
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("concepto", concepto);
			map.put("bloquear", bloquear);
			
			String query = "UPDATE Consecutivos SET esBloqueado = :bloquear  WHERE Concepto = :concepto";
			//			logger.info(query);
			return super.jdbcTemplate.update(query,map);
		} catch (Exception e) {
//			logger.info("Error: "+ e.getMessage());
			return 0;
		}

	}
}