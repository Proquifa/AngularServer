/**
 * 
 */
package com.proquifa.net.persistencia.cobrosypagos.facturista;

import java.util.List;


import com.proquifa.net.modelo.cobrosypagos.facturista.PartidaFactura;


/**
 * @author fmartinez
 *
 */
public interface PfacturasDAO {

	/***
	 * Encontrar la partidas de la factura.
	 * @param factura
	 * @param fpor
	 * @return
	 */
	List<PartidaFactura> finPFacturasXFactura(String factura, String fpor);
	
	/***
	 * Inserta una partida de factura.
	 * @param pfactura
	 */
	long insertarPFacturas (PartidaFactura pfactura);
	
	/***
	 * Actualiza las propiedades de la partida de una factura.
	 * @param idPFactura
	 * @return
	 */
	Boolean actualizarPFacturas (PartidaFactura pfActualizada);
	/****
	 * Obtiene la partida maxima de la factura
	 * @param factura
	 * @param fpor
	 * @return
	 */
	Integer getMaxPFacturas(String factura, String fpor);
	/***
	 * 
	 * @param cpedido
	 * @param ppedido
	 * @return
	 */
	PartidaFactura getPFacturaXCPedido(String cpedido, Integer ppedido);
	
	/***
	 * Se actualizan todas las partidas de la factura con los mismos campos.
	 * @param factura
	 * @param fpor
	 * @param paranmetros
	 * @return
	 */
	Boolean actualizarTodoPFacturas(String factura, String fpor, String paranmetros);
	Long insertPDocumentoFiscalTemporal(PartidaFactura pfactura) ;

	/**
	 * @param pfactura
	 * @return
	 */
	Boolean updatePDocumentoFiscalTemporal(PartidaFactura pfactura);
	
	/**
	 * @param idDocumentoFiscal
	 * @return
	 */
	List<PartidaFactura> findPartidasXidDocumentoFiscalTemporal ( Long idDocumentoFiscal);
	
	/**
	 * se elimina una partida en especial en base al id de DoctoFiscal (FacturaTemporal)
	 * @param idPDoctoFiscalTemporal
	 * @return
	 */
	Boolean deletePartidaxidPDoctoFiscalTemporal (Long  idPDoctoFiscalTemporal);
}