/**
 * 
 */
package com.proquifa.net.persistencia.cobrosypagos.facturista.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.cobrosypagos.facturista.PartidaFactura;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.cobrosypagos.facturista.PfacturasDAO;
import com.proquifa.net.persistencia.cobrosypagos.facturista.impl.mapper.PDocumentoFiscalTemporalRowMapper;
import com.proquifa.net.persistencia.cobrosypagos.facturista.impl.mapper.PFacturasRowMapper;


/**
 * @author fmartinez
 *
 */
@Repository
public class PfacturasDAOImpl extends DataBaseDAO implements PfacturasDAO {
	
	final Logger log = LoggerFactory.getLogger(PfacturasDAOImpl.class);
	
	/* (non-Javadoc)
	 * @see mx.com.proquifa.proquifanet.persistencia.ventas.PCotizaHistorialDAO#obtenerPCotizaHistorial(mx.com.proquifa.proquifanet.modelo.ventas.PartidaCotizacion)
	 */
	public Boolean actualizarPFacturas(PartidaFactura pfActualizada) {		
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Cant", pfActualizada.getCantidad());
			map.put("Nota", pfActualizada.getNota());
			map.put("Estado", pfActualizada.getEstado());
			map.put("idPFactura", pfActualizada.getIdPFactura());
			super.jdbcTemplate.update("UPDATE PFac  bturas SET Cant=?,Nota=?,Estado=? WHERE idPFactura=?", map);
			return true;
		} catch (Exception e) {
			//logger.info("Error: " + e.getMessage());
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public List<PartidaFactura> finPFacturasXFactura(String factura, String fpor) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Factura",factura);
			map.put("FPor", fpor);
			return (List<PartidaFactura>) super.jdbcTemplate.query("SELECT * FROM PFacturas WHERE Factura= :factura  AND FPor=':fpor'", new PFacturasRowMapper());
		} catch (Exception e) {
			//logger.info("Error: " + e.getMessage());
			return null;
		}
	}
	
	public long insertarPFacturas(PartidaFactura pfactura) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Part",pfactura.getPartidaFactura());
			map.put("Factura",pfactura.getFactura());
			map.put("FPor",pfactura.getFpor());
			map.put("CPedido",pfactura.getCpedido());
			map.put("PPedido",pfactura.getPpedido());
			map.put("Importe",pfactura.getImporte());
			map.put("Cant",pfactura.getCantidad());
			map.put("Fabrica",pfactura.getFabrica());
			map.put("Codigo",pfactura.getCodigo());
			map.put("Concepto",pfactura.getConceptoPartida());
			map.put("Cotiza",pfactura.getCotiza());
			map.put("Nota",pfactura.getNota());
			
			String query = "INSERT INTO PFacturas (Part,Factura,FPor,CPedido,PPedido,Importe,Cant,Fabrica,Codigo,Concepto,Cotiza,Nota) VALUES ";
			query+= "(?,?,?,?,?,?,?,?,?,?,?,?)";
			super.jdbcTemplate.update(query, map);

			Long idPCompra = super.queryForLong("SELECT IDENT_CURRENT ('PFacturas')");
			return idPCompra;
		} catch (Exception e) {
			//logger.info("Error: "+ e.getMessage());
			return -1L;
		}
	}

	public Integer getMaxPFacturas(String factura, String fpor) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("factura",factura);
			map.put("fpor",fpor);
			return super.queryForInt("SELECT MAX(Part)+1 AS Partida FROM PFacturas WHERE Factura= :factura  AND FPor=':fpor'");
		} catch (Exception e) {
			//logger.info("Error: " + e.getMessage());
			return null;
		}
	}

	public PartidaFactura getPFacturaXCPedido(String cpedido, Integer ppedido) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cpedido",cpedido);
			map.put("ppedido",ppedido);
			return (PartidaFactura) super.jdbcTemplate.queryForObject("SELECT * FROM PFacturas WHERE CPedido=':cpedido' AND PPedido= :ppedido", map,new PFacturasRowMapper());
		} catch (Exception e) {
			//logger.info("No existe Factura para este pedido: " + cpedido + " -- " +  e.getMessage());
			return null;
		}
	}
	
	public Boolean actualizarTodoPFacturas(String factura, String fpor,
			String paranmetros) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("factura",factura);
			map.put("fpor",fpor);
			map.put("paranmetros",paranmetros);
			super.jdbcTemplate.update("UPDATE PFacturas SET "+ paranmetros +" WHERE Factura="+ factura +" AND FPor='"+ fpor +"'",map);
			return true;
		} catch (Exception e) {
			//logger.info("Error: "+ e.getMessage());
			return false;
		}
	}
	public Long insertPDocumentoFiscalTemporal(PartidaFactura pfactura) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Part",pfactura.getPartidaFactura());
			map.put("Factura",pfactura.getFactura());
			map.put("FPor",pfactura.getFpor());
			map.put("CPedido",pfactura.getCpedido());
			map.put("PPedido",pfactura.getPpedido());
			map.put("Importe",pfactura.getImporte());
			map.put("Cant",pfactura.getCantidad());
			map.put("Fabrica",pfactura.getFabrica());
			map.put("Codigo",pfactura.getCodigo());
			map.put("Concepto",pfactura.getConceptoPartida());
			map.put("Cotiza",pfactura.getCotiza());
			map.put("Nota",pfactura.getDoctoFacturacionTemporal());
			map.put("FK01_DocumentoFiscalTemporal",pfactura.getNota());
		
			String query = "INSERT INTO PDocumentoFiscalTemporal (Part,Factura,FPor,CPedido,PPedido,Importe,Cant,Fabrica,Codigo,Concepto,Cotiza,Nota, FK01_DocumentoFiscalTemporal) VALUES ";
			query+= "(?,?,?,?,?,?,?,?,?,?,?,?,?)";
			//logger.info(query + "- "+ parametro);
			super.jdbcTemplate.update(query, map);
			Long idPCompra = super.queryForLong("SELECT IDENT_CURRENT ('PDoctoFiscalTemporal')");
			return idPCompra;
		} catch (Exception e) {
			log.info("Error: "+ e.getMessage());
			return -1L;
		}
	}
	@Override
	public Boolean updatePDocumentoFiscalTemporal(PartidaFactura pfactura) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Part",pfactura.getPartidaFactura());
			map.put("Factura",pfactura.getFactura());
			map.put("FPor",pfactura.getFpor());
			map.put("CPedido",pfactura.getCpedido());
			map.put("PPedido",pfactura.getPpedido());
			map.put("Importe",pfactura.getImporte());
			map.put("Cant",pfactura.getCantidad());
			map.put("Fabrica",pfactura.getFabrica());
			map.put("Codigo",pfactura.getCodigo());
			map.put("Concepto",pfactura.getConceptoPartida());
			map.put("Cotiza",pfactura.getCotiza());
			map.put("Nota",pfactura.getDoctoFacturacionTemporal());
			map.put("FK01_DocumentoFiscalTemporal",pfactura.getNota());
			map.put("PK_PDocumentoFiscalTemporal", pfactura.getIdPFactura());
			Object[] params =  {pfactura.getFactura(),pfactura.getPartidaFactura(),pfactura.getFpor(),pfactura.getCpedido(),
					pfactura.getPpedido(),pfactura.getImporte(),pfactura.getCantidad(),pfactura.getFabrica(),pfactura.getCodigo(),
					pfactura.getConceptoPartida(),pfactura.getCotiza(),pfactura.getNota(), pfactura.getDoctoFacturacionTemporal(), pfactura.getIdPFactura()};
			
			String query = "Update PDocumentoFiscalTemporal SET factura  =? ,  Part=?,FPor=?,CPedido=?,PPedido=?,Importe=?,Cant=?,Fabrica=?,Codigo=?,Concepto=?,Cotiza = ?,Nota=? Where  FK01_DocumentoFiscalTemporal=? and PK_PDocumentoFiscalTemporal = ? ";
			//logger.info(query + " - " + parametros ); 
			super.jdbcTemplate.update(query, map);

			return true;
		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PartidaFactura> findPartidasXidDocumentoFiscalTemporal(Long idDocumentoFiscal) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idDocumentoFiscal",idDocumentoFiscal);
			return super.jdbcTemplate.query("SELECT * FROM PDocumentoFiscalTemporal where FK01_DocumentoFiscalTemporal = :idDocumentoFiscal", new PDocumentoFiscalTemporalRowMapper());
		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
			return null;
		}
	}

	@Override
	public Boolean deletePartidaxidPDoctoFiscalTemporal(
			Long idPDoctoFiscalTemporal) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idPDoctoFiscalTemporal",idPDoctoFiscalTemporal);
		try {
			String query = " DELETE FROM PDocumentoFiscalTemporal Where PK_PDocumentoFiscalTemporal = " + idPDoctoFiscalTemporal;
			//logger.info(query ); 
			super.jdbcTemplate.update(query,map);
			return true; 
		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
			return false;
		}
	}
}