package com.proquifa.net.negocio.contabilidad.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.proquifa.net.modelo.comun.CatalogoItem;
import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.Empresa;
import com.proquifa.net.modelo.comun.NominaCatalogo;
import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.Proveedor;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.facturaElectronica.FacturaElectronica;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.cuentaContable.BalanceGeneral;
import com.proquifa.net.modelo.cuentaContable.CentroCosto;
import com.proquifa.net.modelo.cuentaContable.ContableCaracteristica;
import com.proquifa.net.modelo.cuentaContable.CuentaContable;
import com.proquifa.net.modelo.cuentaContable.EstadoResultado;
import com.proquifa.net.modelo.cuentaContable.PCuentaAuxilar;
import com.proquifa.net.modelo.cuentaContable.PPoliza;
import com.proquifa.net.modelo.cuentaContable.PPolizaPDF;
import com.proquifa.net.modelo.cuentaContable.Poliza;
import com.proquifa.net.negocio.comun.CatalogoService;
import com.proquifa.net.negocio.contabilidad.ContabilidadService;
import com.proquifa.net.persistencia.comun.ClienteDAO;
import com.proquifa.net.persistencia.comun.EmpresaDAO;
import com.proquifa.net.persistencia.comun.ProveedorDAO;
import com.proquifa.net.persistencia.comun.facturacion.FacturacionElectronicaDAO;
import com.proquifa.net.persistencia.contabilidad.ContabilidadDAO;

@Service("contabilidadService")
public class ContabilidadServiceImpl implements ContabilidadService {

	final Logger log = LoggerFactory.getLogger(ContabilidadServiceImpl.class);
	
	@Autowired
	private ContabilidadDAO contabilidadDAO;

	@Autowired
	private FacturacionElectronicaDAO facturacionElectronicaDAO;

	@Autowired
	private ClienteDAO clienteDAO;

	@Autowired
	private EmpresaDAO empresaDAO;

	@Autowired
	private ProveedorDAO proveedorDAO;

	@Autowired
	private CatalogoService catalogoService;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private Funcion funcion = new Funcion();
	
	/*-----------------------------Cuentas Contables-----------------------------*/

	@Override
	public CuentaContable obtenerCuentaContable(Integer idCuentaContable) throws ProquifaNetException {
		try {
			CuentaContable cuentaContable = new CuentaContable();
			cuentaContable = contabilidadDAO.getCuentaContable(idCuentaContable);
			List<ContableCaracteristica> lstCaract = new ArrayList<ContableCaracteristica>();
			lstCaract = contabilidadDAO.getContableCaracteristica();
			for (ContableCaracteristica itemC : lstCaract ) {
				if (cuentaContable.getDetalleInt() == itemC.getIdContableCaracteristica()) {
					cuentaContable.setDetalle(itemC);
				}
				if (cuentaContable.getTipoInt() == itemC.getIdContableCaracteristica()) {
					cuentaContable.setTipo(itemC);
				}
			}
			return cuentaContable;
		}catch(Exception e) {
			e.printStackTrace();
			return new CuentaContable();
		}
	}

	@Override
	public List<ContableCaracteristica> obtenerContablesCaracteristicas() throws ProquifaNetException {
		try {
			List<ContableCaracteristica> lst = new ArrayList<ContableCaracteristica>();
			lst = contabilidadDAO.getContableCaracteristica();
			return lst;
		}catch(Exception e) {
			e.printStackTrace();
			return new ArrayList<ContableCaracteristica>();
		}
	}

	@Override
	public List<CatalogoItem> obtenerClientesCuentasContables(String tipoCliente) throws ProquifaNetException {
		try {
			List<CatalogoItem> lst = new ArrayList<CatalogoItem>();
			lst = contabilidadDAO.getClientesCuentasContables(tipoCliente);
			return lst;
		}catch(Exception e) {
			e.printStackTrace();
			return new ArrayList<CatalogoItem>();
		}
	}

	@Override
	public List<CatalogoItem> obtenerProveedoresCuentasContables(String tipoCliente) throws ProquifaNetException {
		try {
			List<CatalogoItem> lst = new ArrayList<CatalogoItem>();
			lst = contabilidadDAO.getProveedoresCuentasContables(tipoCliente);
			return lst;
		}catch(Exception e) {
			e.printStackTrace();
			return new ArrayList<CatalogoItem>();
		}
	}

	@Transactional(readOnly=false, transactionManager = "ds1TransactionManager", rollbackFor = Exception.class)
	public boolean desactivarCuentaContable(Integer idCuenta) throws ProquifaNetException {
		try {
			return contabilidadDAO.disableCuentaContable(idCuenta);
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
	}

	@Transactional(readOnly=false, transactionManager = "ds1TransactionManager", rollbackFor = Exception.class)
	public CuentaContable agregarCuentaContable(CuentaContable cuenta) throws ProquifaNetException {
		try {
			cuenta.setIdCuentaContable(contabilidadDAO.addCuentaContable(cuenta));
			if (cuenta.getIdCuentaContable() > 0) {
				if (cuenta.getIdBanco() != null && cuenta.getIdBanco() > 0) {
					Integer idCuentaCBanco = contabilidadDAO.addCuentaContableBanco(cuenta);
				}
				if (cuenta.getIdCliente() != null && cuenta.getIdCliente() > 0) {
					Integer idCuentaCCliente = contabilidadDAO.addCuentaContableCliente(cuenta);
				}
				if (cuenta.getIdProveedor() != null && cuenta.getIdProveedor() > 0) {
					Integer idCuentaCProveedor = contabilidadDAO.addCuentaContableProveedor(cuenta);
				}
			}
			return cuenta;
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return cuenta;
		}
	}

	@Transactional(readOnly=false, transactionManager = "ds1TransactionManager", rollbackFor = Exception.class)
	public CuentaContable actualizarCuentaContable(CuentaContable cuenta) throws ProquifaNetException {
		try {
			boolean isUpdate = contabilidadDAO.updateCuentaContable(cuenta);
			if (isUpdate) {
				return cuenta;
			} else {
				return new CuentaContable();
			}
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return cuenta;
		}
	}
	
	public  List<CuentaContable> obtenerCuentasContablesSaldoInicial(Integer idEmpresa, Date fechaInicial,Date fechaFinal) {
		try {
			List<CuentaContable> lst = new ArrayList<CuentaContable>();
			lst = contabilidadDAO.getCuentasContablesSaldoInicial(idEmpresa, fechaInicial);
			List<ContableCaracteristica> lstCaract = new ArrayList<ContableCaracteristica>();
			lstCaract = contabilidadDAO.getContableCaracteristica();
			for (CuentaContable item : lst) {
				for (ContableCaracteristica itemC : lstCaract ) {
					if (item.getDetalleInt() == itemC.getIdContableCaracteristica()) {
						item.setDetalle(itemC);
					}
					if (item.getTipoInt() == itemC.getIdContableCaracteristica()) {
						item.setTipo(itemC);
					}
				}
				item.setLstPP(obtenerLstPPolizaCuentaContable(item.getIdCuentaContable(), idEmpresa, fechaInicial, fechaFinal));
			}
			return lst;
		}catch(Exception e) {
			e.printStackTrace();
			return new ArrayList<CuentaContable>();
		}
	}
	
	public  List<CuentaContable> obtenerCuentasContablesSaldoAcumulado(Integer idEmpresa, Date fechaInicial,Date fechaFinal) {
		try {
			List<CuentaContable> lst = new ArrayList<CuentaContable>();
			lst = contabilidadDAO.getCuentasContablesSaldoAcumulado(idEmpresa, fechaInicial);
			List<ContableCaracteristica> lstCaract = new ArrayList<ContableCaracteristica>();
			lstCaract = contabilidadDAO.getContableCaracteristica();
			for (CuentaContable item : lst) {
				for (ContableCaracteristica itemC : lstCaract ) {
					if (item.getDetalleInt() == itemC.getIdContableCaracteristica()) {
						item.setDetalle(itemC);
					}
					if (item.getTipoInt() == itemC.getIdContableCaracteristica()) {
						item.setTipo(itemC);
					}
				}
				item.setLstPP(obtenerLstPPolizaCuentaContable(item.getIdCuentaContable(), idEmpresa, fechaInicial, fechaFinal));
			}
			return lst;
		}catch(Exception e) {
			e.printStackTrace();
			return new ArrayList<CuentaContable>();
		}
	}
	
	public List<PPoliza> obtenerLstPPolizaCuentaContable(Integer idCuentaContable, Integer idEmpresa, Date fechaInicial, Date fechaFinal) throws Exception{
		List<PPoliza> lstPP = contabilidadDAO.getLstPPolizaCuentaContable(idCuentaContable, idEmpresa, fechaInicial, fechaFinal);
		for (PPoliza pp : lstPP) {
			pp.setCentroCosto(obtenerCentroCosto(pp.getIdCentroCosto()));
			pp.setPoliza(obtenerPoliza(pp.getIdPoliza()));
			pp.getPoliza().setLstPPoliza(null);
		}
		return lstPP;
	}
	
	@Override
	public List<CuentaContable> obtenerCuentasContablesEmpresa(Integer idEmpresa) throws ProquifaNetException {
		try {
			List<CuentaContable> lst = new ArrayList<CuentaContable>();
			log.info("",idEmpresa);
			lst = contabilidadDAO.getCuentasContablesEmpresa(idEmpresa);
			List<ContableCaracteristica> lstCaract = new ArrayList<ContableCaracteristica>();
			lstCaract = contabilidadDAO.getContableCaracteristica();
			for (CuentaContable item : lst) {
				for (ContableCaracteristica itemC : lstCaract ) {
					if (item.getDetalleInt() == itemC.getIdContableCaracteristica()) {
						item.setDetalle(itemC);
					}
					if (item.getTipoInt() == itemC.getIdContableCaracteristica()) {
						item.setTipo(itemC);
					}
				}
			}
			return lst;
		}catch(Exception e) {
			e.printStackTrace();
			return new ArrayList<CuentaContable>();
		}
	}
	
	@Override
	public CuentaContable obtenerCuentaContableIdObjeto(Integer nivel1, Integer idEmpresa, Long id, String objeto) throws ProquifaNetException {
		try {
			CuentaContable cuentaContable = new CuentaContable();
			cuentaContable = contabilidadDAO.getCuentaContableIdObjeto(nivel1, idEmpresa, id, objeto);
			if (cuentaContable.getIdCuentaContable() > 0) {
				List<ContableCaracteristica> lstCaract = new ArrayList<ContableCaracteristica>();
				lstCaract = contabilidadDAO.getContableCaracteristica();
				for (ContableCaracteristica itemC : lstCaract ) {
					if (cuentaContable.getDetalleInt() == itemC.getIdContableCaracteristica()) {
						cuentaContable.setDetalle(itemC);
					}
					if (cuentaContable.getTipoInt() == itemC.getIdContableCaracteristica()) {
						cuentaContable.setTipo(itemC);
					}
				}
			}
			return cuentaContable;
		}catch(Exception e) {
			e.printStackTrace();
			return new CuentaContable();
		}
	}

	@Override
	public CuentaContable obtenerCuentaContableNivel(Integer idEmpresa, Integer nivel1, Integer nivel2, Integer nivel3) throws ProquifaNetException {
		try {
			CuentaContable cuentaContable = new CuentaContable();
			cuentaContable = contabilidadDAO.getCuentaContableNivel(idEmpresa, nivel1, nivel2, nivel3);
			List<ContableCaracteristica> lstCaract = new ArrayList<ContableCaracteristica>();
			lstCaract = contabilidadDAO.getContableCaracteristica();
			for (ContableCaracteristica itemC : lstCaract ) {
				if (cuentaContable.getDetalleInt() == itemC.getIdContableCaracteristica()) {
					cuentaContable.setDetalle(itemC);
				}
				if (cuentaContable.getTipoInt() == itemC.getIdContableCaracteristica()) {
					cuentaContable.setTipo(itemC);
				}
			}
			return cuentaContable;
		}catch(Exception e) {
			e.printStackTrace();
			return new CuentaContable();
		}
	}
	
	@Override
	public double calcularSaldoCuentaContable(Integer idCuentaContable, Date fechaInicial, Date fechaFinal) throws ProquifaNetException{
		return contabilidadDAO.calculateSaldoCuentaContable(idCuentaContable, fechaInicial, fechaFinal);
	}
	
	/*-----------------------------Pólizas-----------------------------*/

	@Transactional(readOnly=false, transactionManager = "ds1TransactionManager", rollbackFor = Exception.class)
	public Poliza agregarPoliza(Poliza poliza) throws ProquifaNetException {
		try {
			List<PPoliza> lstPPoliza = poliza.getLstPPoliza();
			poliza = contabilidadDAO.addPoliza(poliza);
			Poliza p = new Poliza();
			p.setIdPoliza(poliza.getIdPoliza());
			for (PPoliza pp : lstPPoliza) {
				pp.setPoliza(p);
				pp.setIdPPoliza(contabilidadDAO.addPPoliza(pp));
			}
			poliza.setLstPPoliza(lstPPoliza);
			byte[] pdf = generarPDFPoliza(poliza.getIdPoliza());
			if(pdf != null) {
				log.info("PDF idPoliza: " + poliza.getIdPoliza() + " Generado correctamente");
			} else {
				log.info("ERROR al Generar PDF idPoliza: " + poliza.getIdPoliza());
			}
			return poliza;
		}catch(ProquifaNetException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new Poliza();
		}
	}

	@Transactional(readOnly=false, transactionManager = "ds1TransactionManager", rollbackFor = Exception.class)
	public Poliza actualizarPoliza(Poliza poliza) throws ProquifaNetException {
		try {
			List<PPoliza> lstPPoliza = poliza.getLstPPoliza();
			contabilidadDAO.deletePPolizas(poliza.getIdPoliza());
			poliza = contabilidadDAO.updatePoliza(poliza);
			Poliza p = new Poliza();
			p.setIdPoliza(poliza.getIdPoliza());
			for (PPoliza pp : lstPPoliza) {
				pp.setPoliza(p);
				pp.setIdPPoliza(contabilidadDAO.addPPoliza(pp));
			}
			poliza.setLstPPoliza(lstPPoliza);
			byte[] pdf = generarPDFPoliza(poliza.getIdPoliza());
			if(pdf != null) {
				log.info("PDF idPoliza: " + poliza.getIdPoliza() + " Generado correctamente");
			} else {
				log.info("ERROR al Generar PDF idPoliza: " + poliza.getIdPoliza());
			}
			return poliza;
		}catch(ProquifaNetException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new Poliza();
		}
	}

	@Override
	public Poliza obtenerPoliza(Integer idPoliza) throws ProquifaNetException {
		try {
			Poliza poliza = contabilidadDAO.getPoliza(idPoliza);
			if (poliza.getIdCliente() > 0) {
				poliza.setCliente(clienteDAO.obtenerClienteXId(poliza.getIdCliente().longValue()));
			}
			if (poliza.getIdEmpresa() > 0) {
				poliza.setEmpresa(empresaDAO.getInformacionEmpresaPorId(poliza.getIdEmpresa().longValue()));
			}
			if (poliza.getIdProveedor() > 0) {
				poliza.setProveedor(proveedorDAO.obtenerProveedorPorId(poliza.getIdProveedor().longValue()));
			}
			poliza.setLstPPoliza(contabilidadDAO.getPPoliza(idPoliza));
			for (int i = 0; i < poliza.getLstPPoliza().size(); i++) {
				if (poliza.getLstPPoliza().get(i).getIdCentroCosto() > 0) {
					poliza.getLstPPoliza().get(i).setCentroCosto(obtenerCentroCosto(poliza.getLstPPoliza().get(i).getIdCentroCosto()));
				}
				poliza.getLstPPoliza().get(i).setCuentaContable(obtenerCuentaContable(poliza.getLstPPoliza().get(i).getIdCuentaContable()));
			}
			
			return poliza;
		}catch(ProquifaNetException e) {
			e.printStackTrace();
			return new Poliza();
		}
	}

	@Override
	public List<CentroCosto> obtenerCentroCostos() throws ProquifaNetException {
		try {
			List<CentroCosto> lst = new ArrayList<CentroCosto>();
			lst = contabilidadDAO.getCentroCostos();
			return lst;
		}catch(ProquifaNetException e) {
			e.printStackTrace();
			return new ArrayList<CentroCosto>();
		}
	}

	@Override
	public CentroCosto obtenerCentroCosto(Integer idCentroCosto) throws ProquifaNetException {
		try {
			CentroCosto centroCosto = new CentroCosto();
			centroCosto = contabilidadDAO.getCentroCosto(idCentroCosto);
			return centroCosto;
		}catch(ProquifaNetException e) {
			e.printStackTrace();
			return new CentroCosto();
		}
	}

	@Override
	public List<Poliza> obtenerLstPolizasEmpresa(Integer idEmpresa) throws ProquifaNetException {
		try {
			List<Poliza> lst = contabilidadDAO.getLstPolizasEmpresa(idEmpresa);
			for (Poliza p : lst) {
				if (p.getIdCliente() > 0) {
					p.setCliente(clienteDAO.obtenerClienteXId(p.getIdCliente().longValue()));
				}
				if (p.getIdEmpresa() > 0) {
					p.setEmpresa(empresaDAO.getInformacionEmpresaPorId(p.getIdEmpresa().longValue()));
				}
				if (p.getIdProveedor() > 0) {
					p.setProveedor(proveedorDAO.obtenerProveedorPorId(p.getIdProveedor().longValue()));
				}
				p.setLstPPoliza(contabilidadDAO.getPPoliza(p.getIdPoliza()));
				for (PPoliza pp: p.getLstPPoliza()) {
					if (pp.getIdCentroCosto() > 0) {
						pp.setCentroCosto(obtenerCentroCosto(pp.getIdCentroCosto()));
					}
					pp.setCuentaContable(obtenerCuentaContable(pp.getIdCuentaContable()));
				}
			}
			return lst;
		}catch(ProquifaNetException e) {
			e.printStackTrace();
			return new ArrayList<Poliza>();
		}
	}

	@Override
	public boolean agregarPolizaAutomatica(Integer caso, Integer idEmpresa, Long idCliente, Long idProveedor, Long idBanco, Integer idFactura) throws ProquifaNetException {
		try{
			Poliza poliza = new Poliza();
			Empresa empresa = empresaDAO.getInformacionEmpresaPorId(idEmpresa.longValue());
			Cliente cliente = (idCliente != null && idCliente > 0) ? clienteDAO.obtenerClienteXId(idCliente.longValue()) : null;
			Proveedor proveedor = (idProveedor != null && idProveedor > 0) ? proveedorDAO.obtenerProveedorPorId(idProveedor.longValue()) : null;
			FacturaElectronica factura = (idFactura != null && idFactura > 0) ? facturacionElectronicaDAO.getFElectronicaByFactura(idFactura) : null;
			CuentaContable cCont = null;
			NominaCatalogo banco = (idBanco != null && idBanco > 0) ? catalogoService.obtenerNominaCatalogoID(idBanco.intValue()) : null;

			double monto = (factura != null && factura.getIdFactura() > 0) ? Double.parseDouble(factura.getSubtotal()) : 0.0;
			double iva = (factura != null && factura.getIdFactura() > 0) ? Double.parseDouble(factura.getTotal()) - Double.parseDouble(factura.getSubtotal()) : 0.0;
			double total = (factura != null && factura.getIdFactura() > 0) ? Double.parseDouble(factura.getTotal()) : 0.0;

			poliza.setActiva(true);
			poliza.setFecha(new Date());
			poliza.setEmpresa(empresa);
			poliza.setCliente(cliente);
			poliza.setProveedor(proveedor);
			poliza.setMonto(0);
			poliza.setIva(0);
			poliza.setTotal(0);

			List<PPoliza> lstPPoliza = new ArrayList<PPoliza>();
			String descripcion = null;
			Integer tipo = null;

			switch (caso) {
			case 1:
				/*
				 * VENTA - GENERACION DE FACTURA
				 * 	POLIZA DIARIO - 3
				 * 	|   DEBE - Cargo - false |------------------------------------ |   HABER - Abono - true   |
				 * 	Clientes: 105 || Clientes nacionales: 1
				 * 																	Ingresos:401 || Ventas y/o servicios gravados a la tasa general de contado: 2
				 * 																	Impuestos trasladados no cobrados: 209 || IVA trasladado no cobrado: 1
				 */
				tipo = 3;
				descripcion = "VENTA - GENERACION DE FACTURA - " + total;
				cCont = obtenerCuentaContableIdObjeto(105, idEmpresa, idCliente, "Cliente");
				if (cCont.getIdCuentaContable() == 0 ) {
					cCont = obtenerCuentaContableNivel(idEmpresa, 105, (cliente.getPais().indexOf("mex") != -1) ? 1 : 2, 0);
					cCont.setDescripcion(cliente.getRazonSocial());
					cCont.setEmpresa(empresa);
					cCont.setIdCliente(idCliente);
					cCont.setIdBanco(null);
					cCont.setIdProveedor(null);
					cCont.setNivel(3);
					cCont = agregarCuentaContable(cCont);
					if (cCont.getIdCuentaContable() == 0) {
						log.info("ERROR agregarPolizaDinamica() tipo = " + caso + "; idEmpresa = " + idEmpresa + "; idCliente = " + idCliente + "; idProveedor = " + idProveedor + "; idFactura = " + idFactura);
						return false;
					}
				}
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), total, 0, false, false));
				cCont = obtenerCuentaContableNivel(idEmpresa, 401, 2, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), monto, 0, true, false));
				cCont = obtenerCuentaContableNivel(idEmpresa, 209, 1, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), iva, 0, true, false));
				break;
			case 2:
				/*
				 *	COBRO PARCIAL DE LA FACTURA
				 * 		POLIZA INGRESO - 1
				 * 	|   DEBE - Cargo - false |-------------------------------------------- |   HABER - Abono - true   |
				 * 	Bancos: 102 || Bancos nacionales: 1 
				 * 																			Clientes: 105 || Clientes nacionales: 1
				 * 	Impuestos trasladados no cobrados: 209 || IVA trasladado no cobrado: 1
				 * 																			Impuestos trasladados cobrados: 208 || IVA trasladado cobrado: 1
				 */
				tipo = 1;
				descripcion = "COBRO PARCIAL DE LA FACTURA - " + total;
				double montoPagado = 0.0; // Revisar de donde sacar el monto pagado, ¿puede ser tabla cobros?
				double montoPagadoIVACob = 0.0;
				double montoPagadoIVANoCob = 0.0;
				cCont = obtenerCuentaContableIdObjeto(102, idEmpresa, idBanco, "Banco");
				if (cCont.getIdCuentaContable() == 0 ) {
					cCont = obtenerCuentaContableNivel(idEmpresa, 102, 1, 0);
					cCont.setDescripcion(banco.getDescripcion().split(" - ")[0]);
					cCont.setEmpresa(empresa);
					cCont.setIdCliente(null);
					cCont.setIdBanco(idBanco.intValue());
					cCont.setIdProveedor(null);
					cCont.setNivel(3);
					cCont = agregarCuentaContable(cCont);
					if (cCont.getIdCuentaContable() == 0) {
						log.info("ERROR agregarPolizaDinamica() tipo = " + caso + "; idEmpresa = " + idEmpresa + "; idCliente = " + idCliente + "; idProveedor = " + idProveedor + "; idFactura = " + idFactura);
						return false;
					}
				}
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), montoPagado, 0, true, false));
				cCont = obtenerCuentaContableIdObjeto(105, idEmpresa, idCliente, "Cliente");
				if (cCont.getIdCuentaContable() == 0 ) {
					cCont = obtenerCuentaContableNivel(idEmpresa, 105, (cliente.getPais().indexOf("mex") != -1) ? 1 : 2, 0);
					cCont.setDescripcion(cliente.getRazonSocial());
					cCont.setEmpresa(empresa);
					cCont.setIdCliente(idCliente);
					cCont.setIdBanco(null);
					cCont.setIdProveedor(null);
					cCont.setNivel(3);
					cCont = agregarCuentaContable(cCont);
					if (cCont.getIdCuentaContable() == 0) {
						log.info("ERROR agregarPolizaDinamica() tipo = " + caso + "; idEmpresa = " + idEmpresa + "; idCliente = " + idCliente + "; idProveedor = " + idProveedor + "; idFactura = " + idFactura);
						return false;
					}
				}
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), montoPagado, 0, true, false));
				cCont = obtenerCuentaContableNivel(idEmpresa, 209, 1, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), montoPagadoIVACob, 0, false, false));
				cCont = obtenerCuentaContableNivel(idEmpresa, 208, 1, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), montoPagadoIVANoCob, 0, true, false));
				break;
			case 3:
				/*
				 *	PROVISION DE LA FACTURA DEL PROVEEDOR
				 * 		POLIZA DIARIO
				 *  |   DEBE - Cargo   |-------------------------------------------------- |   HABER - Abono   |
				 *  Inventario: 115 || Mercancías en tránsito: 5
				 *  											Proveedores: 201 || Proveedores nacionales:1
				 */
				cCont = obtenerCuentaContableNivel(idEmpresa, 115, 5, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, false, false));
				cCont = obtenerCuentaContableIdObjeto(201, idEmpresa, idProveedor, "Proveedor");
				if (cCont.getIdCuentaContable() == 0 ) {
					cCont = obtenerCuentaContableNivel(idEmpresa, 201, 1, 0);
					cCont.setDescripcion(proveedor.getRazonSocial());
					cCont.setEmpresa(empresa);
					cCont.setIdCliente(null);
					cCont.setIdBanco(null);
					cCont.setIdProveedor(idProveedor.intValue());
					cCont.setNivel(3);
					cCont = agregarCuentaContable(cCont);
					if (cCont.getIdCuentaContable() == 0) {
						log.info("ERROR agregarPolizaDinamica() tipo = " + caso + "; idEmpresa = " + idEmpresa + "; idCliente = " + idCliente + "; idProveedor = " + idProveedor + "; idFactura = " + idFactura);
						return false;
					}
				}
				
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, true, false));
				break;
			case 4:
				/*
				 *	PAGO DE PEDIMENTO Y CRUCE DE MERCANCIA
				 * 		POLIZA EGRESO
				 *  |   DEBE - Cargo   |-------------------------------------------------------------- |   HABER - Abono   |
				 * 	Costo de venta y/o servicio: 501 || Costo de venta: 1 
				 * 																				   		Inventario: 115 || Mercancías en tránsito: 5
				 *  Otras cuentas de costos: 504 || Otras cuentas de costos: 25
				 * 	Impuestos acreditables pagados: 118 || IVA acreditable de importación pagado: 2
				 * 																					    Bancos: 102 || Bancos nacionales: 1 
				 */
				cCont = obtenerCuentaContableNivel(idEmpresa, 501, 1, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, false, false));
				cCont = obtenerCuentaContableNivel(idEmpresa, 115, 5, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, true, false));
				cCont = obtenerCuentaContableNivel(idEmpresa, 504, 25, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, false, false));
				cCont = obtenerCuentaContableNivel(idEmpresa, 118, 2, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, false, false));
				cCont = obtenerCuentaContableIdObjeto(102, idEmpresa, idBanco, "Banco");
				if (cCont.getIdCuentaContable() == 0 ) {
					cCont = obtenerCuentaContableNivel(idEmpresa, 102, 1, 0);
					cCont.setDescripcion(banco.getDescripcion().split(" - ")[0]);
					cCont.setEmpresa(empresa);
					cCont.setIdCliente(null);
					cCont.setIdBanco(idBanco.intValue());
					cCont.setIdProveedor(null);
					cCont.setNivel(3);
					cCont = agregarCuentaContable(cCont);
					if (cCont.getIdCuentaContable() == 0) {
						log.info("ERROR agregarPolizaDinamica() tipo = " + caso + "; idEmpresa = " + idEmpresa + "; idCliente = " + idCliente + "; idProveedor = " + idProveedor + "; idFactura = " + idFactura);
						return false;
					}
				}
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, true, false));
				break;
			case 5:
				/*
				 *	PAGO DE LA FACTURA AL PROVEEDOR
				 * 		POLIZA EGRESO
				 * 	|   DEBE - Cargo   |-------------------------------------------------- |   HABER - Abono   |
				 * 	Proveedores: 201 || Proveedores nacionales: 1
				 * 	Gastos financieros: 701 || Pérdida cambiaria: 1
				 * 																			Bancos: 102 || Bancos nacionales: 1 
				 */
				cCont = obtenerCuentaContableNivel(idEmpresa, 114, 1, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, false, false));
				cCont = obtenerCuentaContableNivel(idEmpresa, 114, 1, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, false, false));
				cCont = obtenerCuentaContableNivel(idEmpresa, 114, 1, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, true, false));
				break;
			case 6:
				/*
				 *	PROVISION DE PAGO DE IMPUESTOS
				 * 		POLIZA DIARIO
				 * |   DEBE - Cargo   |-------------------------------------------------- |   HABER - Abono   |
				 * 	Impuestos trasladados cobrados: 208 || IVA trasladado cobrado: 1
				 * 																		  Impuestos acreditables pagados: 118 || IVA acreditable pagado: 2  (Aduana 7000.00)
				 * 																		  Impuestos y derechos por pagar: 213 || IVA por pagar: 1
				 */
				cCont = obtenerCuentaContableNivel(idEmpresa, 114, 1, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, false, false));
				cCont = obtenerCuentaContableNivel(idEmpresa, 114, 1, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, true, false));
				cCont = obtenerCuentaContableNivel(idEmpresa, 114, 1, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, true, false));
				break;
			case 7:
				/*
				 *	PAGO DE IMPUESTOS
				 * 		POLIZA EGRESO
				 *  |   DEBE - Cargo   |-------------------------------------------------- |   HABER - Abono   |
				 *  Impuestos y derechos por pagar: 213 || IVA por pagar: 1
				 * 																			Bancos: 102 || Bancos nacionales: 1
				 */
				cCont = obtenerCuentaContableNivel(idEmpresa, 114, 1, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, false, false));
				cCont = obtenerCuentaContableNivel(idEmpresa, 114, 1, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, true, false));
				break;
			case 8:
				/*
				 *	REGISTRO DE COMPRA DE INVERSION
				 * 		POLIZA EGRESO
				 *  |   DEBE - Cargo   |-------------------------------------------------- |   HABER - Abono   |
				 * 	Inversiones: 103 || Inversiones temporales: 1
				 * 																			Bancos: 102 || Bancos nacionales: 1
				 */
				cCont = obtenerCuentaContableNivel(idEmpresa, 114, 1, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, false, false));
				cCont = obtenerCuentaContableNivel(idEmpresa, 114, 1, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, true, false));
				break;
			case 9:
				/*
				 *	REGISTRO DE LOS INTERESES GANADOS
				 * 		POLIZA DIARIO
				 *  |   DEBE - Cargo   |-------------------------------------------------- |   HABER - Abono   | 
				 * 	Inversiones: 103 || Inversiones temporales: 1
				 * 	Impuestos a favor: 113 || Otros impuestos a favor: 8																   
				 * 																			Productos financieros: 702 ||  Intereses a favor bancarios extranjero: 5
				 */
				cCont = obtenerCuentaContableNivel(idEmpresa, 114, 1, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, false, false));
				cCont = obtenerCuentaContableNivel(idEmpresa, 114, 1, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, false, false));
				cCont = obtenerCuentaContableNivel(idEmpresa, 114, 1, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, true, false));
				break;
			case 10:
				/*
				 *	REGISTRO DE VENTA DE INVERSION
				 * 		POLIZA INGRESO
				 *  |   DEBE - Cargo   |-------------------------------------------------- |   HABER - Abono   | 
				 *  Bancos: 102 || Bancos nacionales: 1
				 * 																		   Inversiones: 103 || Inversiones temporales: 1
				 */
				cCont = obtenerCuentaContableNivel(idEmpresa, 114, 1, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, false, false));
				cCont = obtenerCuentaContableNivel(idEmpresa, 114, 1, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, true, false));
				break;
			case 11:
				/*
				 *	PROVISION DE IMPUESTOS
				 * 		POLIZA DIARIO
				 *  |   DEBE - Cargo   |-------------------------------------------------- |   HABER - Abono   | 
				 *  Pagos provisionales: 114 Pagos provisionales de ISR: 1
				 * 	Gastos generales: 601 || Cuotas al IMSS: 26
				 *  Gastos generales: 601 || Aportaciones al SAR: 28
				 *  Gastos generales: 601 || Aportaciones al infonavit: 27
				 *  Gastos generales: 601 || Impuesto estatal sobre nóminas: 29
				 *  					     												Impuestos y derechos por pagar: 213 || ISR por pagar: 3
				 *  																		Provisión de contribuciones de seguridad social por pagar 211 || Provisión de IMSS patronal por pagar: 1
				 *  																		Provisión de contribuciones de seguridad social por pagar 211 || Provisión de SAR por pagar: 2
				 * 																			Provisión de contribuciones de seguridad social por pagar 211 || Provisión de infonavit por pagar: 3
				 * 																			Impuestos y derechos por pagar: 213 || Impuesto estatal sobre nómina por pagar: 4
				 */
				cCont = obtenerCuentaContableNivel(idEmpresa, 114, 1, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, false, false));
				cCont = obtenerCuentaContableNivel(idEmpresa, 114, 1, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, false, false));
				cCont = obtenerCuentaContableNivel(idEmpresa, 114, 1, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, false, false));
				cCont = obtenerCuentaContableNivel(idEmpresa, 114, 1, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, false, false));
				cCont = obtenerCuentaContableNivel(idEmpresa, 114, 1, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, false, false));
				cCont = obtenerCuentaContableNivel(idEmpresa, 114, 1, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, true, false));
				cCont = obtenerCuentaContableNivel(idEmpresa, 114, 1, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, true, false));
				cCont = obtenerCuentaContableNivel(idEmpresa, 114, 1, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, true, false));
				cCont = obtenerCuentaContableNivel(idEmpresa, 114, 1, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, true, false));
				cCont = obtenerCuentaContableNivel(idEmpresa, 114, 1, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, true, false));
				break;
			case 12:
				/*
				 *	PROVISION DE NOMINAS
				 * 		POLIZA DIARIO
				 *  |   DEBE - Cargo   |-------------------------------------------------- |   HABER - Abono   |
				 * 	Gastos generales 601 || Sueldos y salarios: 1
				 *  																	   Provisión de sueldos y salarios por pagar: 210 || Provisión de sueldos y salarios por pagar: 1
				 * 																		   Impuestos retenidos: 216 || Retenciones de IMSS a los trabajadores: 11
				 * 																		   Impuestos retenidos: 216 || Impuestos retenidos de ISR por sueldos y salarios: 1
				 */
				tipo = 3;
				descripcion = "PROVISION DE NOMINAS";
				cCont = obtenerCuentaContableNivel(idEmpresa, 601, 1, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, false, false));
				cCont = obtenerCuentaContableNivel(idEmpresa, 210, 1, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, true, false));
				cCont = obtenerCuentaContableNivel(idEmpresa, 216, 11, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, true, false));
				cCont = obtenerCuentaContableNivel(idEmpresa, 216, 1, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, true, false));
				break;
			case 13:
				/*
				 *	PROVISIONES DE GASTOS
				 * 		POLIZA DIARIO
				 *  |   DEBE - Cargo   |----------------------------------------------------------------- |   HABER - Abono   | 
				 * 	Gastos generales: 601 || Mantenimiento y conservación: 56
				 * 	Gastos generales: 601 || Honorarios a personas físicas residentes nacionales: 34
				 * 	Gastos generales: 601 || Limpieza: 54
				 * 	Gastos generales: 601 || Servicios administrativos: 32
				 * 	Impuestos acreditables por pagar: 119 || IVA pendiente de pago: 1
				 * 																							Acreedores diversos a corto plazo: 205 || Socios, accionistas o representante legal: 1
				 *																			 				Acreedores diversos a corto plazo: 205 || Socios, accionistas o representante legal: 1
				 *																							Acreedores diversos a corto plazo: 205 || Socios, accionistas o representante legal: 1
				 *																							Acreedores diversos a corto plazo: 205 || Socios, accionistas o representante legal: 1
				 */
				tipo = 3;
				descripcion = "PROVISIONES DE GASTOS";
				cCont = obtenerCuentaContableNivel(idEmpresa, 601, 56, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, false, false));
				cCont = obtenerCuentaContableNivel(idEmpresa, 601, 34, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, false, false));
				cCont = obtenerCuentaContableNivel(idEmpresa, 601, 54, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, false, false));
				cCont = obtenerCuentaContableNivel(idEmpresa, 601, 32, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, false, false));
				cCont = obtenerCuentaContableNivel(idEmpresa, 119, 1, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, false, false));
				cCont = obtenerCuentaContableNivel(idEmpresa, 205, 1, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, true, false));
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, true, false));
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, true, false));
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, true, false));
				break;
			case 14:
				/*
				 *	COMPRA DE COMPUTADORA
				 * 		POLIZA DIARIO
				 *  |   DEBE - Cargo   |-------------------------------------------------- |   HABER - Abono   | 
				 *  Equipo de cómputo: 156 || Equipo de cómputo: 1 
				 *  Impuestos acreditables por pagar: 119 || IVA pendiente de pago: 1	
				 * 																		   Acreedores diversos a corto plazo: 205 || Socios, accionistas o representante legal: 1
				 */
				descripcion = "COMPRA DE COMPUTADORA";
				tipo = 3;
				cCont = obtenerCuentaContableNivel(idEmpresa, 156, 1, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, false, false));
				cCont = obtenerCuentaContableNivel(idEmpresa, 119, 1, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, false, false));
				cCont = obtenerCuentaContableNivel(idEmpresa, 205, 1, 0);
				lstPPoliza.add(new PPoliza(cCont, null, cCont.getDescripcion(), 0, 0, true, false));
				break;
			}
			poliza.setDescripcion(descripcion);
			poliza.setFolio(crearFolioPolizaEmpresa(tipo, idEmpresa, empresa.getNomenclaturaEmpresa()));
			poliza.setTipo(tipo);
			poliza.setLstPPoliza(lstPPoliza);
			poliza = agregarPoliza(poliza);
			if (poliza.getIdPoliza() > 0) {
				return true;
			} else {
				return false;
			}
		}catch(Exception e) {
			return false;
		}
	}

	

	@Override
	public String crearFolioPolizaEmpresa(Integer tipo, Integer idEmpresa, String prefijo) throws ProquifaNetException {
		LocalDate fecha = LocalDate.now();
		String letra = (tipo == 1) ? "I" : (tipo == 2) ? "E" : "D";
		String folio = null;
		String separador = "-";
		try {
			int valor = contabilidadDAO.createFolioPolizaEmpresa(tipo, idEmpresa);
			folio = prefijo.toUpperCase() + separador + letra + separador + fecha.getYear() + separador + funcion.formatoFolio(valor, 5);
			return folio;
		}catch(Exception e) {
			return folio;
		}
	}
	
	/*-----------------------------Reportes-----------------------------*/

	@Override
	public byte[] generarPDFContabilidad(Parametro parametro) throws ProquifaNetException {
		try {
			byte[] pdf = null;
			int tipo = parametro.getTipoPDF();
			int idEmpresa = parametro.getIdEmpresa();
			Date fechaInicial = null;
			Date fechaFinal = null;
			switch(tipo) {
			case 0: //Reporte Diario
				Integer idPoliza = parametro.getIdPoliza();
				pdf = generarPDFPoliza(idPoliza);
				break;
			case 1: //Cuenta Auxiliar
				fechaInicial = parametro.getFechaInicial();
				fechaFinal = parametro.getFechaFinal();
				idEmpresa = parametro.getIdEmpresa();
				pdf = generarPDFCuentaAuxiliar(idEmpresa, fechaInicial, fechaFinal);
				break;
			case 2: //Estado Resultado
				fechaInicial = parametro.getFechaInicial();
				fechaFinal = parametro.getFechaFinal();
				pdf = generarPDFEstadoResultado(idEmpresa, fechaInicial, fechaFinal);
				break;
			case 3: //Balance General
				fechaInicial = parametro.getFechaInicial();
				fechaFinal = parametro.getFechaFinal();
				pdf = generarPDFBalanceGral(idEmpresa, fechaInicial, fechaFinal);
				break;
			}
			return pdf;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public byte[] generarPDFPoliza(Integer idPoliza) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			byte[] pdf = null;
			Poliza poliza = obtenerPoliza(idPoliza);
			List<PPolizaPDF> lstPP = new ArrayList<PPolizaPDF>();
			double cargo = 0;
			double abono = 0;
			for (PPoliza pp : poliza.getLstPPoliza()) {
				PPolizaPDF ppPDF = new PPolizaPDF();
				ppPDF.setFecha(sdf.format(poliza.getFecha()));
				ppPDF.setNum(pp.getIdPPoliza().toString());
				if (pp.isTipo()) {
					abono += pp.getMonto();
					ppPDF.setAbono(funcion.formatoMoneda(pp.getMonto()));
					ppPDF.setTipo("Abono");
					ppPDF.setCargo("-");
				} else {
					ppPDF.setCargo(funcion.formatoMoneda(pp.getMonto()));
					cargo += pp.getMonto();
					ppPDF.setTipo("Cargo");
					ppPDF.setAbono("-");
				}
				ppPDF.setDescripcion(pp.getDescripcion());
				lstPP.add(ppPDF);
			}
			Integer idEmpresa = poliza.getEmpresa().getIdEmpresa();
			String logo = Funcion.RUTA_DOCUMENTOS + "Template/imgs/logo_proquifa.svg";
			switch (idEmpresa) {
			case 4:logo = Funcion.RUTA_DOCUMENTOS + "Template/imgs/logo_golocaer.png";break;
			case 5:logo = Funcion.RUTA_DOCUMENTOS + "Template/imgs/logo_mungen.png";break;
			case 8:logo = Funcion.RUTA_DOCUMENTOS + "Template/imgs/logo_ryndem.svg";break;
			}
			map.put("idPoliza", poliza.getIdPoliza());
			map.put("idEmpresa", idEmpresa);
			map.put("empresa", poliza.getEmpresa().getRazonSocial());
			map.put("logo", logo);
			map.put("fecha", sdf.format(poliza.getFecha()));
			map.put("descripcion", poliza.getDescripcion());
			map.put("totalAbono", funcion.formatoMoneda(abono));
			map.put("totalCargo", funcion.formatoMoneda(cargo));
			map.put("lstPP", lstPP);
			pdf = funcion.crearArchivoPDF(map, "ReporteDiario" + idEmpresa);
			return pdf;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public byte[] generarPDFCuentaAuxiliar(Integer idEmpresa, Date fechaInicial, Date fechaFinal) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			byte[] pdf = null;
			Empresa empresa = empresaDAO.getInformacionEmpresaPorId(idEmpresa.longValue());
			List<CuentaContable> lstCuentaCont = obtenerCuentasContablesSaldoAcumulado(idEmpresa, fechaInicial, fechaFinal);
			// CuentaAuxilar cAux = new CuentaAuxilar();
			List<CuentaContable> lstAux1 = new ArrayList<CuentaContable>();
			List<CuentaContable> lstAux2 = new ArrayList<CuentaContable>();
			List<CuentaContable> lstAux3 = new ArrayList<CuentaContable>();
			
			Double totalI = 0.0;
			Double totalF = 0.0;
			
			lstAux1 = lstCuentaCont.stream().filter(cc -> cc.getNivel() == 1).collect(Collectors.toList());
			lstAux2 = lstCuentaCont.stream().filter(cc -> cc.getNivel() == 2).collect(Collectors.toList());
			lstAux3 = lstCuentaCont.stream().filter(cc -> cc.getNivel() == 3).collect(Collectors.toList());

			/*for (CuentaContable cc : lstCuentaCont) {
				switch (cc.getNivel()) {
				case 1: 
					lstAux1.add(cc);
					break;
				case 2: 
					lstAux2.add(cc);
					break;
				case 3: 
					lstAux3.add(cc);
					break;
				}
			}*/
			
			List<PCuentaAuxilar> lstCC = new ArrayList<PCuentaAuxilar>();
			for (CuentaContable ccN1 : lstAux1) { // Se Agregan las cuentas contables nivel 1
				PCuentaAuxilar itemCCN1 = new PCuentaAuxilar();
				double montoN1 = 0;
				itemCCN1.setCodigo(funcion.crearCodigoCC(ccN1));
				itemCCN1.setDescripcion(ccN1.getDescripcion());
				List<PCuentaAuxilar> lstCCN2 = new ArrayList<PCuentaAuxilar>();
				for(CuentaContable ccN2 : lstAux2) { // Se Agregan las cuentas contables nivel 2
					if(ccN1.getNivel1() == ccN2.getNivel1()) {
						PCuentaAuxilar itemCCN2 = new PCuentaAuxilar();
						double montoI2 = 0.0;
						double montoI2Aux = ccN2.getMonto();
						double montoN2 = ccN2.getMonto();
						itemCCN2.setCodigo(funcion.crearCodigoCC(ccN2));
						itemCCN2.setDescripcion(ccN2.getDescripcion());
						montoI2 += ccN2.getTotalAbono() - ccN2.getTotalCargo();
												
						List<PCuentaAuxilar> lstCCN3 = new ArrayList<PCuentaAuxilar>();
						for (PPoliza pp : ccN2.getLstPP()) { // Se agregan las ppolizas del nivel 2
							PCuentaAuxilar itemCCN3 = new PCuentaAuxilar();
							itemCCN3.setFecha(sdf.format(pp.getPoliza().getFecha()));
							String tipo = (pp.getPoliza().getTipo() == 1) ? "Ingreso" : (pp.getPoliza().getTipo() == 2) ? "Egreso" : "Diario";
							itemCCN3.setTipo(tipo);
							itemCCN3.setNum(pp.getIdPPoliza()+"");
							itemCCN3.setNombre(ccN2.getDescripcion());
							itemCCN3.setUbicacion(pp.getCentroCosto().getDescripcion());
							itemCCN3.setDescripcion(ccN2.getDescripcion());
							String monto = (pp.isTipo()) ? funcion.formatoNumero(pp.getMonto()) : funcion.formatoNumero(-1 * pp.getMonto()) + "";
							itemCCN3.setMonto(monto);
							montoI2Aux +=  pp.isTipo() ? pp.getMonto() : (-1 * pp.getMonto());;
							itemCCN3.setSaldoFinal(funcion.formatoNumero(montoI2Aux)+"");
							montoN2 += pp.isTipo() ? pp.getMonto() : (-1 * pp.getMonto());
							lstCCN3.add(itemCCN3);
						}
						for(CuentaContable ccN3 : lstAux3) { // Se agregan las ppolizas del nivel 3
							if(ccN2.getNivel1() == ccN3.getNivel1() && ccN2.getNivel2() == ccN3.getNivel2() ) {
								for (PPoliza pp : ccN3.getLstPP()) {
									PCuentaAuxilar itemCCN3 = new PCuentaAuxilar();
									itemCCN3.setFecha(sdf.format(pp.getPoliza().getFecha()));
									String tipo = (pp.getPoliza().getTipo() == 1) ? "Ingreso" : (pp.getPoliza().getTipo() == 2) ? "Egreso" : "Diario";
									itemCCN3.setTipo(tipo);
									itemCCN3.setNum(pp.getIdPPoliza()+"");
									itemCCN3.setNombre(ccN3.getDescripcion());
									itemCCN3.setUbicacion(pp.getCentroCosto().getDescripcion());
									itemCCN3.setDescripcion(ccN3.getDescripcion());
									String monto = (pp.isTipo()) ? funcion.formatoNumero(pp.getMonto()) : "-" + funcion.formatoNumero(pp.getMonto());
									itemCCN3.setMonto(monto);
									itemCCN3.setSaldoFinal(funcion.formatoNumero(pp.getMonto()));
									montoI2 = pp.isTipo() ? pp.getMonto() : (-1 * pp.getMonto());
									montoN2 += montoI2;
									itemCCN3.setSaldoInicial(funcion.formatoNumero(ccN3.getMonto())+"");
									itemCCN3.setSaldoFinal(funcion.formatoNumero(montoN2) + "");
									lstCCN3.add(itemCCN3);
								}
							}
						}
						itemCCN2.setMonto(funcion.formatoNumero(montoN2));
						itemCCN2.setLstPP(lstCCN3);
						itemCCN2.setSaldoInicial(funcion.formatoNumero(ccN2.getMonto())+"");
						itemCCN2.setSaldoFinal(funcion.formatoNumero(montoN2));
						montoN1 += Double.parseDouble(itemCCN2.getSaldoFinal());
						lstCCN2.add(itemCCN2);
					}
				}
				
				itemCCN1.setSaldoInicial(funcion.formatoNumero(ccN1.getMonto())+"");
				totalI += ccN1.getMonto();
				itemCCN1.setSaldoFinal(funcion.formatoNumero(montoN1));
				totalF += montoN1;
				itemCCN1.setLstCCN2(lstCCN2);
				lstCC.add(itemCCN1);
			}
			String logo = Funcion.RUTA_DOCUMENTOS + "Template/imgs/logo_proquifa.png";
			switch (idEmpresa) {
			case 4:logo = Funcion.RUTA_DOCUMENTOS + "Template/imgs/logo_golocaer.png";break;
			case 5:logo = Funcion.RUTA_DOCUMENTOS + "Template/imgs/logo_mungen.png";break;
			case 8:logo = Funcion.RUTA_DOCUMENTOS + "Template/imgs/logo_ryndem.svg";break;
			}
			map.put("fecha", funcion.fechaTextoRango(fechaInicial, fechaFinal));
			map.put("logo", logo);
			map.put("idEmpresa", idEmpresa);
			map.put("empresa", empresa.getRazonSocial());
			map.put("totalA", funcion.formatoNumero(totalI)+"");
			map.put("totalB", funcion.formatoNumero(totalF)+"");
			map.put("lstCC", lstCC);
			pdf = funcion.crearArchivoPDF(map, "CuentaAuxiliar" + idEmpresa);
			return pdf;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public byte[] generarPDFEstadoResultado(Integer idEmpresa, Date fechaInicial, Date fechaFinal) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			Empresa empresa = empresaDAO.getInformacionEmpresaPorId(idEmpresa.longValue());
			String fecha = funcion.fechaTextoRango(fechaInicial, fechaFinal);
			String NEFinanciero = "ESTADO DE RESULTADOS";
			String PEFinanciero = "'COSTO DE VENTAS'";
			List<CuentaContable> lstCostoVentas = contabilidadDAO.getCuentasContablesEstadosFinan(idEmpresa, NEFinanciero, PEFinanciero, fechaInicial, fechaFinal);
			PEFinanciero = "'IMPUESTOS'";
			List<CuentaContable> lstImpuestos = contabilidadDAO.getCuentasContablesEstadosFinan(idEmpresa, NEFinanciero, PEFinanciero, fechaInicial, fechaFinal);
			PEFinanciero = "'COSTO INTREGRAL DE FINANCIAMIENTO'";
			List<CuentaContable> lstCostoIntFinan = contabilidadDAO.getCuentasContablesEstadosFinan(idEmpresa, NEFinanciero, PEFinanciero, fechaInicial, fechaFinal);
			PEFinanciero = "'GASTOS DE VENTA', 'GASTOS DE ADMINISTRACIÓN', 'GASTOS GENERALES', 'GASTOS DE FABRICACIÓN'";
			List<CuentaContable> lstGastos = contabilidadDAO.getCuentasContablesEstadosFinan(idEmpresa, NEFinanciero, PEFinanciero, fechaInicial, fechaFinal);
			PEFinanciero = "'VENTAS'";
			List<CuentaContable> lstVentas = contabilidadDAO.getCuentasContablesEstadosFinan(idEmpresa, NEFinanciero, PEFinanciero, fechaInicial, fechaFinal);
			String logo = Funcion.RUTA_DOCUMENTOS + "Template/imgs/logo_proquifa.svg";
			switch (idEmpresa) {
			case 4:logo = Funcion.RUTA_DOCUMENTOS + "Template/imgs/logo_golocaer.png";break;
			case 5:logo = Funcion.RUTA_DOCUMENTOS + "Template/imgs/logo_mungen.png";break;
			case 8:logo = Funcion.RUTA_DOCUMENTOS + "Template/imgs/logo_ryndem.svg";break;
			}
			
			EstadoResultado estadoRes = new EstadoResultado();
			List<EstadoResultado> lstN1 = new ArrayList<EstadoResultado>();
			List<EstadoResultado> lstN2 = new ArrayList<EstadoResultado>();
			double monto = 0.0;
			
			/*Ventas*/
			estadoRes.setNombre("Ventas");
			estadoRes.setSubnombre("Total Ventas");
			monto = 0.0;
			lstN2 = new ArrayList<EstadoResultado>();
			log.info("lstVentas: " + lstVentas.size());
			for (CuentaContable item : lstVentas) {
				EstadoResultado estadoResN2 = new EstadoResultado();
				estadoResN2.setNombre(item.getDescripcion());
				estadoResN2.setMonto( funcion.formatoMoneda( item.getTotalAbono() - item.getTotalCargo() ) );
				estadoResN2.setPorcentaje("- N/A");
				monto +=  item.getTotalAbono() - item.getTotalCargo();
				lstN2.add(estadoResN2);
			}
			estadoRes.setLstN2(lstN2);
			estadoRes.setMonto(funcion.formatoMoneda(monto) + " - 100%");
			
			lstN1.add(estadoRes);
			
			/*Costos Ventas*/
			estadoRes = new EstadoResultado();
			estadoRes.setNombre("Costo Ventas");
			estadoRes.setSubnombre("Total Costo Ventas");
			monto = 0.0;
			lstN2 = new ArrayList<EstadoResultado>();
			
			log.info("lstCostoVentas: " + lstCostoVentas.size());
			for (CuentaContable item : lstCostoVentas) {
				EstadoResultado estadoResN2 = new EstadoResultado();
				estadoResN2.setNombre(item.getDescripcion());
				estadoResN2.setMonto( funcion.formatoMoneda( item.getTotalAbono() - item.getTotalCargo() ) );
				estadoResN2.setPorcentaje("- N/A");
				monto +=  item.getTotalAbono() - item.getTotalCargo();
				lstN2.add(estadoResN2);
			}
			estadoRes.setLstN2(lstN2);
			estadoRes.setMonto(funcion.formatoMoneda(monto) + " - 100%");
			
			lstN1.add(estadoRes);
			
			/*Gastos Operacionales*/
			estadoRes = new EstadoResultado();
			estadoRes.setNombre("Gastos Operacionales");
			estadoRes.setSubnombre("Total Gastos Operacionales");
			monto = 0.0;
			lstN2 = new ArrayList<EstadoResultado>();
			
			log.info("lstGastos: " + lstGastos.size());
			for (CuentaContable item : lstGastos) {
				EstadoResultado estadoResN2 = new EstadoResultado();
				estadoResN2.setNombre(item.getDescripcion());
				estadoResN2.setMonto( funcion.formatoMoneda( item.getTotalAbono() - item.getTotalCargo() ) );
				estadoResN2.setPorcentaje("- N/A");
				monto +=  item.getTotalAbono() - item.getTotalCargo();
				lstN2.add(estadoResN2);
			}
			estadoRes.setLstN2(lstN2);
			estadoRes.setMonto(funcion.formatoMoneda(monto) + " - 100%");
			
			lstN1.add(estadoRes);
			
			/*COSTO INTREGRAL DE FINANCIAMIENTO*/
			estadoRes = new EstadoResultado();
			estadoRes.setNombre("Costo Integral de Financiamiento");
			estadoRes.setSubnombre("Total Costo Integral de Financiamiento");
			monto = 0.0;
			lstN2 = new ArrayList<EstadoResultado>();
			
			log.info("lstCostoIntFinan: " + lstCostoIntFinan.size());
			for (CuentaContable item : lstCostoIntFinan) {
				EstadoResultado estadoResN2 = new EstadoResultado();
				estadoResN2.setNombre(item.getDescripcion());
				estadoResN2.setMonto( funcion.formatoMoneda( item.getTotalAbono() - item.getTotalCargo() ) );
				estadoResN2.setPorcentaje("- N/A");
				monto +=  item.getTotalAbono() - item.getTotalCargo();
				lstN2.add(estadoResN2);
			}
			estadoRes.setLstN2(lstN2);
			estadoRes.setMonto(funcion.formatoMoneda(monto) + " - 100%");
			
			lstN1.add(estadoRes);
			
			/*IMPUESTOS*/
			estadoRes = new EstadoResultado();
			estadoRes.setNombre("Impuestos");
			estadoRes.setSubnombre("Total Impuestos");
			monto = 0.0;
			lstN2 = new ArrayList<EstadoResultado>();
			
			log.info("lstImpuestos: " + lstImpuestos.size());
			for (CuentaContable item : lstImpuestos) {
				EstadoResultado estadoResN2 = new EstadoResultado();
				estadoResN2.setNombre(item.getDescripcion());
				estadoResN2.setMonto( funcion.formatoMoneda( item.getTotalAbono() - item.getTotalCargo() ) );
				estadoResN2.setPorcentaje("- N/A");
				monto +=  item.getTotalAbono() - item.getTotalCargo();
				lstN2.add(estadoResN2);
			}
			estadoRes.setLstN2(lstN2);
			estadoRes.setMonto(funcion.formatoMoneda(monto) + " - 100%");
			
			lstN1.add(estadoRes);
			
			
			map.put("idEmpresa", idEmpresa);
			map.put("fecha", fecha);
			map.put("empresa", empresa.getRazonSocial());
			map.put("logo", logo);
			map.put("lstN1", lstN1);
			
			return funcion.crearArchivoPDF(map, "EstadoResultado" + idEmpresa);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public byte[] generarPDFBalanceGral(Integer idEmpresa, Date fechaInicial, Date fechaFinal) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String fecha = funcion.fechaTextoRango(fechaInicial, fechaFinal);
			
			String NEFinanciero = "ESTADO DE RESULTADOS";
			String PEFinanciero = "('COSTO DE VENTAS')";
			List<CuentaContable> lstVentas = contabilidadDAO.getCuentasContablesEstadosFinan(idEmpresa, NEFinanciero, PEFinanciero, fechaInicial, fechaFinal);
			
			BalanceGeneral balance = new BalanceGeneral();
			map.put("fecha", fecha);
			byte[] pdf = null;
			return pdf;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public boolean crearClavesClientes() {
		try {
			char valor1 = 'K';
			char valor2 = 'A';
			char valor3 = '0';
			char valor4 = '0';
			
			String claveCuentaContable = "";
			String clave = "";
			
			List<Cliente> lst = contabilidadDAO.obtenerClientes();
			
			for(Cliente item: lst) {
				
				if(valor4 == '9') {
					valor4 = '0';
					if(valor3 == '9') {
						valor3 = '0';
						if(valor2 == 'Z') {
							valor2 = 'A';
							if(valor1 == 'Z') {
								valor1 = 'A';
							}else {
								valor1++;
							}
						}else {
							valor2++;
						}
					}else {
						valor3++;
					}
				}else {
					valor4++;
				}
				char[] chars = {valor1,valor2,valor3,valor4};
				claveCuentaContable = String.copyValueOf(chars);
				contabilidadDAO.crearClavesClientes(claveCuentaContable, item.getIdCliente().toString());
			}
			
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean crearClavesProveedores() {
		try {
			char valor1 = 'A';
			char valor2 = 'A';
			char valor3 = '0';
			char valor4 = '0';
			
			String claveCuentaContable = "";
			String clave = "";
			
			List<Proveedor> lst = contabilidadDAO.obtenerProveedores();
			
			for(Proveedor item: lst) {
				
				if(valor4 == '9') {
					valor4 = '0';
					if(valor3 == '9') {
						valor3 = '0';
						if(valor2 == 'Z') {
							valor2 = 'A';
							if(valor1 == 'Z') {
								valor1 = 'A';
							}else {
								valor1++;
							}
						}else {
							valor2++;
						}
					}else {
						valor3++;
					}
				}else {
					valor4++;
				}
				char[] chars = {valor1,valor2,valor3,valor4};
				claveCuentaContable = String.copyValueOf(chars);
				contabilidadDAO.crearClavesProveedores(claveCuentaContable, item.getIdProveedor().toString());
			}
			
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}


}
