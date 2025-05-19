/**
 * 
 */
package com.proquifa.net.persistencia.consultas;

import java.util.List;

import com.proquifa.net.modelo.cobrosypagos.facturista.Factura;
import com.proquifa.net.modelo.comun.NivelIngreso;
import com.proquifa.net.modelo.comun.TiempoProceso;
import com.proquifa.net.modelo.consultas.comun.ResumenConsulta;
import com.proquifa.net.modelo.despachos.HistorialPNE;

/**
 * @author vromero
 *
 */
public interface ConsultaEntregasDAO {
	/***
	 * Obtiene la informacion general del reporte de Entregas, segun las condiciones 
	 * @param condiciones
	 * @return
	 */
	List<Factura> findEntregas(String condiciones, List<NivelIngreso> niveles);
	/***
	 * Obtiene la lista de eventos y su duraciones de una entrega
	 * @param idDP
	 * @param idEntrega
	 * @param idRuta
	 * @return
	 */
	List<TiempoProceso> findTiempoProceso(String idDP);
	/***
	 * Obtiene la lista de doctos hechos por que el ProductoNoEntregado relacionados a una ruta
	 * @param idDP
	 * @return
	 */
	List<HistorialPNE> findHistorial(String idDP);
	/***
	 * 
	 * @param condiciones
	 * @return
	 */
	List<Factura> findGraficosEntregas(String condiciones);
	/***
	 * 
	 * @param condiciones
	 * @return
	 */
	List<ResumenConsulta> findComparativasDPeriodos(String condiciones);
}