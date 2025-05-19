package com.proquifa.net.negocio.despachos.trabajarRuta.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.Pendiente;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.despachos.trabajarRuta.TrAlmacen;
import com.proquifa.net.modelo.despachos.trabajarRuta.TrContactoCliente;
import com.proquifa.net.modelo.despachos.trabajarRuta.TrPackingList;
import com.proquifa.net.modelo.despachos.trabajarRuta.TrTotalGraficasAlmacen;
import com.proquifa.net.negocio.despachos.trabajarRuta.TrAlmacenService;
import com.proquifa.net.persistencia.comun.PendienteDAO;
import com.proquifa.net.persistencia.despachos.trabajarRuta.TrAlmacenDAO;

@Service("trAlmacenService")
public class TrAlmacenServiceImpl implements TrAlmacenService {
	@Autowired
	TrAlmacenDAO trAlmacenDAO; 
	@Autowired
	PendienteDAO pendienteDAO;

	@Override
	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public  Map<String,Integer> getObjetivos() throws ProquifaNetException{
		try {
			return trAlmacenDAO.getObjetivos();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	@Override
	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public  Map<String,Integer> getMontosTab() throws ProquifaNetException{
		try {
			return trAlmacenDAO.getMontosTab();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public Map<String, Map<String, List<TrTotalGraficasAlmacen>>> getGraficas() throws ProquifaNetException{
		try {
			return trAlmacenDAO.getGraficas();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	@Override
	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public List<TrAlmacen> getClientes() throws ProquifaNetException{
		try {
			return trAlmacenDAO.getClientes();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	@Override
	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public Map<String, List<TrPackingList>> getPackingListClient(int idCliente) throws ProquifaNetException{
		try {
			return trAlmacenDAO.getPackingListClient(idCliente);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	@Override
	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public boolean finalizarEjecutarRutaAlmacen(TrContactoCliente trContactoCliente) throws ProquifaNetException {
		if(trAlmacenDAO.insertEjecutarRutaAlmacen(trContactoCliente)) {
			Pendiente pendiente = new Pendiente();
			pendiente.setFolio(new Long(trContactoCliente.getIdPendiente()));
			pendienteDAO.actualizarPendienteEnvioAlmacen(pendiente, "Entregado");
			trAlmacenDAO.actualizarTrAlmacen(trContactoCliente);
		}
		return true;
	}
	
	
	@Override
	public Map<String, List<TrPackingList>> obtenerEstadisticasUsuarioTR(Parametro parametro) throws ProquifaNetException{
		try{
			Map<String, List<TrPackingList>> mapReturn = new HashMap<String, List<TrPackingList>>();
			mapReturn = trAlmacenDAO.obtenerEstadisticaUsuario(parametro);
			return mapReturn;
		}catch (Exception e) {
			// logger.error(e.getMessage());
			return null;
		}
	}
	
}
