/**
 * 
 */
package com.proquifa.net.persistencia.compras;

import java.util.List;
import java.util.Map;

import com.proquifa.net.modelo.compras.SaldoAFavor;
import com.proquifa.net.modelo.comun.Complemento;
import com.proquifa.net.modelo.comun.Producto;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;

/**
 * @author ymendez
 *
 */
public interface SaldoAFavorDAO {

	/**
	 * @param saldo
	 * @return
	 * @throws ProquifaNetException
	 */
	String generarSaldo(SaldoAFavor saldo) throws ProquifaNetException;

	/**
	 * @param tipo 
	 * @return
	 * @throws ProquifaNetException
	 */
	Map<String, List<SaldoAFavor>> obtenerListaSaldo(String tipo) throws ProquifaNetException;

	Producto obtenerDescProducto(Long idProducto) throws ProquifaNetException;

	/**
	 * @param idComplemento
	 * @return
	 */
	Complemento obtenerDescComplemento(Long idComplemento);

	/**
	 * @param habilitado
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean habilitarSaldo(SaldoAFavor saldo) throws ProquifaNetException;

}
