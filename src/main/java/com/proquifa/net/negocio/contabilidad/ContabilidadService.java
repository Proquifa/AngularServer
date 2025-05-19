package com.proquifa.net.negocio.contabilidad;

import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.comun.CatalogoItem;
import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.cuentaContable.CentroCosto;
import com.proquifa.net.modelo.cuentaContable.ContableCaracteristica;
import com.proquifa.net.modelo.cuentaContable.CuentaContable;
import com.proquifa.net.modelo.cuentaContable.Poliza;

public interface ContabilidadService {
	
	public CuentaContable obtenerCuentaContable(Integer idCuentaContable) throws ProquifaNetException;
	public List<ContableCaracteristica> obtenerContablesCaracteristicas() throws ProquifaNetException;

	public List<CatalogoItem> obtenerClientesCuentasContables(String tipoCliente) throws ProquifaNetException;
	
	public List<CatalogoItem> obtenerProveedoresCuentasContables(String tipoCliente) throws ProquifaNetException;

	public boolean desactivarCuentaContable(Integer idCuenta) throws ProquifaNetException;

	public CuentaContable agregarCuentaContable(CuentaContable cuenta) throws ProquifaNetException;

	public CuentaContable actualizarCuentaContable(CuentaContable cuenta) throws ProquifaNetException;

	public Poliza agregarPoliza(Poliza poliza) throws ProquifaNetException;

	public Poliza obtenerPoliza(Integer idPoliza) throws ProquifaNetException;

	public Poliza actualizarPoliza(Poliza poliza) throws ProquifaNetException;
	
	public List<CentroCosto> obtenerCentroCostos() throws ProquifaNetException;

	public CentroCosto obtenerCentroCosto(Integer idCentroCosto) throws ProquifaNetException;

	public byte[] generarPDFContabilidad(Parametro parametro) throws ProquifaNetException;
	
	public byte[] generarPDFPoliza(Integer idPoliza) throws ProquifaNetException;
	
	public byte[] generarPDFCuentaAuxiliar(Integer idEmpresa, Date fechaInicial, Date fechaFinal) throws ProquifaNetException;
	
	public byte[] generarPDFBalanceGral(Integer idEmpresa, Date fechaInicial, Date fechaFinal) throws ProquifaNetException;
	
	public byte[] generarPDFEstadoResultado(Integer idEmpresa, Date fechaInicial, Date fechaFinal) throws ProquifaNetException;

	public List<CuentaContable> obtenerCuentasContablesEmpresa(Integer idEmpresa) throws ProquifaNetException;

	public List<Poliza> obtenerLstPolizasEmpresa(Integer idEmpresa) throws ProquifaNetException;

	public double calcularSaldoCuentaContable(Integer idCuentaContable, Date fechaInicial, Date fechaFinal) throws ProquifaNetException;
	
	public boolean agregarPolizaAutomatica(Integer caso, Integer idEmpresa, Long idCliente, Long idProveedor, Long idBanco, Integer idFactura) throws ProquifaNetException;
	
	public CuentaContable obtenerCuentaContableNivel(Integer idEmpresa, Integer nivel1, Integer nivel2, Integer nivel3) throws ProquifaNetException;
	
	public CuentaContable obtenerCuentaContableIdObjeto(Integer nivel1, Integer idEmpresa, Long id, String objeto) throws ProquifaNetException;
	
	public String crearFolioPolizaEmpresa(Integer tipo, Integer idEmpresa, String prefijo) throws ProquifaNetException;

}
