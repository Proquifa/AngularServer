/**
 * 
 */
package com.proquifa.net.persistencia.reportes;

import java.util.List;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.cobrosypagos.Cobros;
import com.proquifa.net.modelo.comun.NivelIngreso;

/**
 * @author ymendez
 *
 */
public interface ReporteCobrosDAO {
	/**Listado de cobros usado para el ReporteCobros
	 * @param parametrosA
	 * @param parametrosB
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Cobros> findCobros(String sbCondicion, List<NivelIngreso> niveles, Long idUsuarioLogueado) throws ProquifaNetException;
	
}
