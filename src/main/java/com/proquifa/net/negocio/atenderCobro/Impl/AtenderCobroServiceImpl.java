package com.proquifa.net.negocio.atenderCobro.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.proquifa.net.modelo.catalogos.cobrosypagos.facturista.CuentaBanco;
import com.proquifa.net.modelo.catalogos.cobrosypagos.facturista.Depositos;
import com.proquifa.net.modelo.catalogos.cobrosypagos.facturista.Excedentes;
import com.proquifa.net.modelo.catalogos.cobrosypagos.facturista.Factura;
import com.proquifa.net.modelo.cobrosypagos.facturista.NotaCredito;
import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.negocio.atenderCobro.atenderCobroService;
import com.proquifa.net.persistencia.atenderCobro.AtenderCobroDAO;

@Service("atenderCobroService")
public class AtenderCobroServiceImpl  implements atenderCobroService{
	@Autowired
	AtenderCobroDAO atenderCobroDAO;
	
	
	@Override
	public Map<String, List<Factura>> atenderCobro(int idCliente) throws ProquifaNetException{
		try{
			Map<String, List<Factura>> mapReturn = new HashMap<String, List<Factura>>();
			mapReturn =  atenderCobroDAO.atenderCobro(idCliente);
			return mapReturn;
		}catch (Exception e) {
			// logger.error(e.getMessage());
			return null;
		}
	}
	
	@Override
	public Map<String, List<NotaCredito>>obtenerNotas(int idCliente) throws ProquifaNetException {
		try {					
			Map<String, List<NotaCredito>> mapReturn = new HashMap<String, List<NotaCredito>>();
			mapReturn = atenderCobroDAO.obtenerNotas(idCliente);			
			return mapReturn;
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return null;
	}
	
	@Override
	public Map<String, List<Factura>> generarGraficaCobro(Parametro parametros) throws ProquifaNetException{
		try{
			Map<String, List<Factura>> mapReturn = new HashMap<String, List<Factura>>();
			mapReturn =  atenderCobroDAO.generarGraFicaCobro(parametros);
			return mapReturn;
		}catch (Exception e) {
			// logger.error(e.getMessage());
			return null;
		}
	}
	
	@Override
	public Map<String, List<Excedentes>> consultarExcedentes(Parametro parametros) throws ProquifaNetException{
		try{
			Map<String, List<Excedentes>> mapReturn = new HashMap<String, List<Excedentes>>();
			mapReturn =  atenderCobroDAO.consultarExcedentes(parametros);
			return mapReturn;
		}catch (Exception e) {
			// logger.error(e.getMessage());
			return null;
		}
	}
	
	@Override
	public Map<String, List<Depositos>> consultarDepositos(Parametro parametros) throws ProquifaNetException{
		try{
			Map<String, List<Depositos>> mapReturn = new HashMap<String, List<Depositos>>();
			mapReturn =  atenderCobroDAO.consultarDepositos(parametros);
			return mapReturn;
		}catch (Exception e) {
			// logger.error(e.getMessage());
			return null;
		}
	}
	
	@Override
	public Map<String, List<CuentaBanco>> consultarBancos() throws ProquifaNetException{
		try{
			Map<String, List<CuentaBanco>> mapReturn = new HashMap<String, List<CuentaBanco>>();
			mapReturn =  atenderCobroDAO.consultarBancos();
			return mapReturn;
		}catch (Exception e) {
			// logger.error(e.getMessage());
			return null;
		}
	}
	
	@Override
	@Transactional(readOnly=false, transactionManager = "ds1TransactionManager", rollbackFor = Exception.class)
	public int registrarDeposito(Depositos deposito) throws ProquifaNetException{
		try{
			int idDeposito = 0;
			idDeposito =  atenderCobroDAO.registrarDeposito(deposito);
			return idDeposito;
		}catch (Exception e) {
			// logger.error(e.getMessage());
			return 0;
		}
		
	}
	

}
