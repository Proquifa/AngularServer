/**
 * 
 */
package com.proquifa.net.negocio.consultas;

import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.cobrosypagos.facturista.Factura;
import com.proquifa.net.modelo.comun.TiempoProceso;
import com.proquifa.net.modelo.consultas.comun.ResumenConsulta;
import com.proquifa.net.modelo.despachos.HistorialPNE;

/**
 * @author vromero
 *
 */
public interface ConsultaEntregasService {
	/***
	 * Obtiene la informacion general de entregas, puede ser delimitado por la fecha, el estado, el mensajero, la ruta, si esta conforme
	 * @param idCliente
	 * @param estado
	 * @param mensajero
	 * @param ruta
	 * @param Conforme
	 * @param anio
	 * @param periodo
	 * @param tipoPeriodo
	 * @param mes
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Factura> obtenerEntregas(Long idCliente, String estado, String mensajero, String ruta, String conforme, Date finicio, Date ffin, String factura, String cpedido) throws ProquifaNetException;
	/***
	 * Obtiene el tiempo que se tardo una entrega en cada proceso
	 * @param idDP
	 * @param idEntrega
	 * @param idRuta
	 * @return
	 * @throws ProquifaNetException
	 */
	List<TiempoProceso> obtenerTiempoDeProceso(String idDP) throws ProquifaNetException;
	/***
	 * Obtiene el historia de entregas de una entregas 
	 * @param idDP
	 * @return
	 * @throws ProquifaNetException
	 */
	List<HistorialPNE> obtenerHistorialProductoNoEntregado(String idDP) throws ProquifaNetException;
	/***
	 * 
	 * @param idCliente
	 * @param estado
	 * @param mensajero
	 * @param ruta
	 * @param conforme
	 * @param finicio
	 * @param ffin
	 * @param factura
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Factura> obtenerGraficosEntregas(Long idCliente, String estado, String mensajero, String ruta, String conforme, Date finicio, Date ffin, String factura, String cpedido) throws ProquifaNetException;
	/***
	 * 
	 * @param idCliente
	 * @param estado
	 * @param mensajero
	 * @param ruta
	 * @param conforme
	 * @param finicio
	 * @param ffin
	 * @param factura
	 * @return
	 * @throws ProquifaNetException
	 */
	List<ResumenConsulta> obtenerComparativasDPeriodos (Long idCliente, String estado, String mensajero, String ruta, String conforme, String factura, String cpedido, Date finicio, Date ffin, Boolean individual) throws ProquifaNetException;
	
	/***
	 * 
	 * @param idCliente
	 * @param estado
	 * @param mensajero
	 * @param ruta
	 * @param conforme
	 * @param finicio
	 * @param ffin
	 * @param factura
	 * @param cpedido
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Factura> obtenerEntregasCAviso(Long idCliente, String estado, String mensajero, String ruta, String conforme, Date finicio, Date ffin, String factura, String cpedido) throws ProquifaNetException;
	
	/***
	 * 
	 * @param idCliente
	 * @param estado
	 * @param mensajero
	 * @param ruta
	 * @param conforme
	 * @param finicio
	 * @param ffin
	 * @param factura
	 * @param cpedido
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Factura> obtenerEntregasSinAviso(Long idCliente, String estado, String mensajero, String ruta, String conforme, Date finicio, Date ffin, String factura, String cpedido) throws ProquifaNetException;
}
