/**
 * 
 */
package com.proquifa.net.negocio.reportes;

import java.util.List;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.cobrosypagos.Cobros;

/**
 * @author ymendez
 *
 */
public interface ReporteCobrosService {
	/**Reporte de cobros y parametros de busqueda
	 * @param factura
	 * @param pedido
	 * @param cliente
	 * @param facturo
	 * @param cpago
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Cobros> reporteCobros(String factura, String cpedido, Long idUsuarioLogueado) throws ProquifaNetException;
	
}
