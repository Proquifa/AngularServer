/**
 * 
 */
package com.proquifa.net.negocio.cobrosypagos.facturista;

import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.cobrosypagos.facturista.NotaCredito;
import com.proquifa.net.modelo.comun.Empresa;
import com.proquifa.net.modelo.comun.Referencia;

/**
 * @author vromero
 *
 */
public interface NotaCreditoService {
	/***
	 * Obtiene la lista de notas que estan relacionadas a un cliente
	 * @param idCliente
	 * @return
	 * @throws ProquifaNetException
	 */
	List<NotaCredito> obtenerNotasDeCreditoPorCliente(Long idCliente) throws ProquifaNetException;
	
	List<NotaCredito> getNotaCreditoAvanzada(Date fechaInicio, Date fechaFin, Long idCliente, String nombreFacturo, String estado, Long idUsuarioLogueado, Long cobrador) throws ProquifaNetException;
	
	List<NotaCredito> getNotaCreditoRapida(Long folioNotaCredito, String sPedidoInterno,Long idUsuarioLogueado) throws ProquifaNetException;

	/***
	 * Obtiene el numero de folios Asignados, Total de usados, y los usados en el mes actual
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Empresa> obtenerFoliosNotaCredito() throws ProquifaNetException;
	/**
	 * Obtiene el total de notas de credito de un cliente, por empresa en un 
	 * @param empresa
	 * @param idCliente
	 * @param ffinicio
	 * @param ffin
	 * @return
	 * @throws ProquifaNetException
	 */
	Double obtenerTotalNotaCreditoClienteEmpresa (String empresa, Long idCliente, Date ffinicio, Date ffin) throws ProquifaNetException;
	/**
	 * Manda true si existe una nota de credito para el cliente y la empresa en el periodo que se manda
	 * @param empresa
	 * @param idCliente
	 * @param ffinicio
	 * @param ffin
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean existeNotaCreditoPeriodo (String empresa, Long idCliente, Date ffinicio, Date ffin) throws ProquifaNetException;
	/***
	 * Inserta una nota de credito, ya sea tipo Periodo o Factura
	 * @param empresa
	 * @param idCliente
	 * @param ffinicio
	 * @param ffin
	 * @param idFactura
	 * @param monto
	 * @param iva
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean agregarNotaCredito(String empresa, Long idCliente, Date ffinicio, Date ffin,  String factura, Double monto, Double iva,Referencia documento, String folio, String moneda, Double tipoCambio, String serie) throws ProquifaNetException;
	/***
	 * obtiene el monto de una Nota de Credito, relacionada una factura
	 * @param factura
	 * @param empresa
	 * @return
	 * @throws ProquifaNetException
	 */
	Double obtenerMontoNotaCreditoPorFactura(Long  factura, String empresa) throws ProquifaNetException;
}
