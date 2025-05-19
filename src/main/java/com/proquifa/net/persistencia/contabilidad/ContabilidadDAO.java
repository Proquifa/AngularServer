package com.proquifa.net.persistencia.contabilidad;

import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.comun.CatalogoItem;
import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.Proveedor;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.cuentaContable.CentroCosto;
import com.proquifa.net.modelo.cuentaContable.ContableCaracteristica;
import com.proquifa.net.modelo.cuentaContable.CuentaContable;
import com.proquifa.net.modelo.cuentaContable.PPoliza;
import com.proquifa.net.modelo.cuentaContable.Poliza;

public interface ContabilidadDAO {
	
	public List<ContableCaracteristica> getContableCaracteristica() throws ProquifaNetException;

	public List<CatalogoItem> getClientesCuentasContables(String tipoCliente) throws ProquifaNetException;
	
	public List<CatalogoItem> getProveedoresCuentasContables(String tipoCliente) throws ProquifaNetException;

	public boolean disableCuentaContable(Integer idCuenta) throws ProquifaNetException;

	public Integer addCuentaContable(CuentaContable cuenta) throws ProquifaNetException;

	public Integer addCuentaContableBanco(CuentaContable cuenta) throws ProquifaNetException;
	
	public Integer addCuentaContableCliente(CuentaContable cuenta) throws ProquifaNetException;
	
	public Integer addCuentaContableProveedor(CuentaContable cuenta) throws ProquifaNetException;

	public boolean updateCuentaContable(CuentaContable cuenta) throws ProquifaNetException;

	public Poliza addPoliza(Poliza poliza) throws ProquifaNetException;

	public Integer addPPoliza(PPoliza pp) throws ProquifaNetException;
	
	public boolean deletePPolizas(Integer idPoliza) throws ProquifaNetException;
	
	public Poliza updatePoliza(Poliza poliza) throws ProquifaNetException;

	public Poliza getPoliza(Integer idPoliza) throws ProquifaNetException;

	public List<PPoliza> getPPoliza(Integer idPoliza) throws ProquifaNetException;

	public CuentaContable getCuentaContable(Integer idCuentaContable) throws ProquifaNetException;
	
	public List<CentroCosto> getCentroCostos() throws ProquifaNetException;

	public CentroCosto getCentroCosto(Integer idCentroCosto) throws ProquifaNetException;

	public List<CuentaContable> getCuentasContablesArbol(Integer idEmpresa) throws ProquifaNetException;

	public List<PPoliza> getLstPPolizaCuentaContable(Integer idCuentaContable, Integer idEmpresa, Date fechaInicial, Date fechaFinal) throws ProquifaNetException;

	public List<CuentaContable> getCuentasContablesEmpresa(Integer idEmpresa) throws ProquifaNetException;

	public List<Poliza> getLstPolizasEmpresa(Integer idEmpresa) throws ProquifaNetException;

	public double calculateSaldoCuentaContable(Integer idCuentaContable, Date fechaInicial, Date fechaFinal) throws ProquifaNetException;

	public CuentaContable getCuentaContableNivel(Integer idEmpresa, Integer nivel1, Integer nivel2, Integer nivel3) throws ProquifaNetException;

	public CuentaContable getCuentaContableIdObjeto(Integer nivel1, Integer idEmpresa, Long id, String objeto) throws ProquifaNetException;

	public List<CuentaContable> getCuentasContablesSaldoInicial(Integer idEmpresa, Date fechaInicial) throws ProquifaNetException;

	public int createFolioPolizaEmpresa(Integer tipo, Integer idEmpresa) throws ProquifaNetException;

	public List<CuentaContable> getCuentasContablesEstadosFinan(Integer idEmpresa, String NEFinanciero, String PEFinanciero, Date fechaInicial, Date fechaFinal) throws ProquifaNetException;

	boolean crearClavesClientes(String claveCuentaContable, String clave);

	public List<Cliente> obtenerClientes();

	public List<Proveedor> obtenerProveedores();

	public boolean crearClavesProveedores(String claveCuentaContable, String clave);

	public List<CuentaContable> getCuentasContablesSaldoAcumulado(Integer idEmpresa, Date fechaInicial)
			throws ProquifaNetException;


}
