/**
 * 
 */
package com.proquifa.net.negocio.compras;

import java.util.List;
import java.util.Map;

import com.proquifa.net.modelo.compras.RegistroConfirmacion;
import com.proquifa.net.modelo.compras.SaldoAFavor;
import com.proquifa.net.modelo.comun.Empresa;
import com.proquifa.net.modelo.comun.Proveedor;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;

/**
 * @author ymendez
 *
 */
public interface SaldoAFavorService {

	/**
	 * @param saldo
	 * @return
	 * @throws ProquifaNetException
	 */
	String generarSaldo(SaldoAFavor saldo) throws ProquifaNetException;

	/**
	 * @param habilitado
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Proveedor> obtenerProveedores(Integer habilitado) throws ProquifaNetException;

	/**
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Empresa> obtenerEmpresas(Integer compra) throws ProquifaNetException;

	/**
	 * @param tipo 
	 * @return
	 * @throws ProquifaNetException
	 */
	Map<String, List<SaldoAFavor>> obtenerListaSaldo(String tipo) throws ProquifaNetException;

	void crearPdfAvisoDeCambios(List<RegistroConfirmacion> info) throws ProquifaNetException;

	/**
	 * @param habilitado
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean habilitarSaldo(SaldoAFavor saldo) throws ProquifaNetException;

}
