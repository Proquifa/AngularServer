/**
 * 
 */
package com.proquifa.net.persistencia.comun;

import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.cobrosypagos.facturista.NotaCredito;
import com.proquifa.net.modelo.comun.Empresa;

/**
 * @author vromero
 *
 */
public interface NotaCreditoDAO {
	/***
	 * obtiene una lista de Notas de Credito por cliente que no esten canceladas
	 * @param idcliente
	 * @return
	 */
	List<NotaCredito> obtenerNotasDeCreditoPorCliente(Long idcliente) throws ProquifaNetException;
	
	/**
	 * Conusulta Avanzada para las Notas de Credito
	 * @param fechaInicio
	 * @param fechaFin
	 * @param idCliente
	 * @param nombreFacturo
	 * @param estado
	 * @return
	 * @throws ProquifaNetException
	 */
	List<NotaCredito> notaCreditoAvanzada(Date fechaInicio, Date fechaFin, String condiciones) throws ProquifaNetException;
	
	/**
	 * Consulta Rapida para las Notas de Credito, solo se aplica uno de los 2 filtros
	 * @param iFolioNotaCredito -- hace referencia al campo AK_Folio de la tabla NotaCredito
	 * @param sPedidoInterno -- hace referencia al campo CPedido de Facturas
	 * @return
	 * @throws ProquifaNetException
	 */
	List<NotaCredito> notaCreditoRapida(Long iFolioNotaCredito, String sPedidoInterno, String condiciones) throws ProquifaNetException;
	/***
	 * 
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Empresa> getFoliosNotaCredito() throws ProquifaNetException;
	/***
	 * 
	 * @param condiciones
	 * @return
	 * @throws ProquifaNetException
	 */
	Double getTotalNotaCreditoClienteEmpresa (String condiciones) throws ProquifaNetException;
	/***
	 * Busca una nota de credito en un perido determinado
	 * @param condiciones
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean existNotaCreditoPeriodo(String condiciones) throws ProquifaNetException;
	/***
	 * Inserta una nota de credito tipo factura
	 * @param idEmpresa
	 * @param idCliente
	 * @param idFactura
	 * @param monto
	 * @param iva
	 * @return
	 * @throws ProquifaNetException
	 */
	Long insertNotaCreditoPorFactura(Long idEmpresa, Long idCliente, Long idFactura, Double monto, Double iva, String folio, String moneda, Double tipoCambio, String serie) throws ProquifaNetException;
	/***
	 * Inserta una nota de credito tipo periodo
	 * @param idEmpresa
	 * @param idCliente
	 * @param ffinicio
	 * @param ffin
	 * @param monto
	 * @param iva
	 * @return
	 * @throws ProquifaNetException
	 */
	Long insertNotaCretitoPorPeriodo(Long idEmpresa, Long idCliente, Date ffinicio, Date ffin, Double monto, Double iva, String folio, String moneda, Double tipoCambio, String serie) throws ProquifaNetException;
	/***
	 * Se actualiza la tabla de PFacturas, estado = 'Nota credito' 
	 * @param factura
	 * @param fpor
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean updateFacturaEstadoNotaCredito(String factura, String fpor)throws ProquifaNetException;
	
	Boolean deleteNotaCredito(Long idNota) throws ProquifaNetException;
	
	Double getMontoNotaCreditoPorFactura(Long factura,String empresa) throws ProquifaNetException;
}
