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
import com.proquifa.net.modelo.despachos.colectarPartidas;
import com.proquifa.net.modelo.despachos.trabajarRuta.TrAlmacen;
import com.proquifa.net.modelo.despachos.trabajarRuta.TrEnvio;
import com.proquifa.net.modelo.despachos.trabajarRuta.TrPackingList;
import com.proquifa.net.modelo.despachos.trabajarRuta.TrTotalGraficasAlmacen;
import com.proquifa.net.negocio.despachos.trabajarRuta.TrEnvioService;
import com.proquifa.net.persistencia.comun.ClienteDAO;
import com.proquifa.net.persistencia.comun.PendienteDAO;
import com.proquifa.net.persistencia.despachos.trabajarRuta.TrEnvioDAO;

@Service("TrEnvioService")
public class TrEnvioServiceImpl implements TrEnvioService{

	@Autowired
	TrEnvioDAO trEnvioDAO;
	
	@Autowired
	ClienteDAO clienteDAO;
	
	@Autowired
	PendienteDAO pendienteDAO;
	
	@Override
	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public  Map<String,Integer> getObjetivos() throws ProquifaNetException{
		try {
			return trEnvioDAO.getObjetivos();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public  Map<String,Integer> getMontosTab() throws ProquifaNetException{
		try {
			return trEnvioDAO.getMontosTab();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	@Override
	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public Map<String, Map<String, List<TrTotalGraficasAlmacen>>> getGraficas() throws ProquifaNetException{
		try {
			return trEnvioDAO.getGraficas();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	@Override
	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public Map<String, Object> getPackingListClient() throws ProquifaNetException{
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			int idCliente = trEnvioDAO.getClientePrioridad();
			String packingList = "";
			Map<String, List<TrPackingList>> mapReturn = trEnvioDAO.getPackingListClient(idCliente);
			for (Map.Entry<String, List<TrPackingList>> entry : mapReturn.entrySet()) {
				packingList += "'" + entry.getKey() + "'";
				packingList += ",";
			}
			map.put("PackingList", mapReturn);
			map.put("Comentarios", trEnvioDAO.obtenerComentarios(packingList.substring(0, packingList.length()-1)));
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	@Override
	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public boolean finalizarEjecutarRutaEnvio(TrEnvio envio) {
		for (int i = 0; i < envio.getPendientes().size(); i++) {
			envio.setIdPendiente(envio.getPendientes().get(i));
			envio.setPackingList(envio.getLstPackingList().get(i));
			Pendiente pendiente = new Pendiente();
			pendiente.setFolio(envio.getPendientes().get(i).longValue());
			pendienteDAO.actualizarPendienteEnvioAlmacen(pendiente , "Enviado");
		}
		
		return true;
	}
	
	
	
	@Override
	public Map<String, List<TrEnvio>> obtenerEstadisticasUsuarioTR(Parametro parametro) throws ProquifaNetException{
		try{
			Map<String, List<TrEnvio>> mapReturn = new HashMap<String, List<TrEnvio>>();
			mapReturn = trEnvioDAO.obtenerEstadisticaUsuario(parametro);
			return mapReturn;
		}catch (Exception e) {
			// logger.error(e.getMessage());
			return null;
		}
	}
	
	@Override
	public boolean registrarEnvio(TrEnvio envio) throws ProquifaNetException{
		try{
			boolean registro = false;
			for (int i = 0; i < envio.getPendientes().size(); i++) {
				envio.setIdPendiente(envio.getPendientes().get(i));
				envio.setPackingList(envio.getLstPackingList().get(i));
				registro = trEnvioDAO.registrarEnvio(envio);
			}
			return registro;
		}catch(Exception e){
			return false;
		}
	}
	
	@Override
	public boolean actualizarEnvio(TrEnvio envio) throws ProquifaNetException{
		try{
			boolean registro = false;
			for (int i = 0; i < envio.getPendientes().size(); i++) {
				envio.setIdPendiente(envio.getPendientes().get(i));
				envio.setPackingList(envio.getLstPackingList().get(i));
				registro = trEnvioDAO.actualizarEnvio(envio);
			}
			return registro;
		}catch(Exception e){
			return false;
		}
	}
	
	
	@Override
	public Map<String, List<TrEnvio>> obtenerTiempoTrabajoEnvio() throws ProquifaNetException{
		try{
			Map<String, List<TrEnvio>> mapReturn = new HashMap<String, List<TrEnvio>>();
			mapReturn = trEnvioDAO.obtenerTiempoTrabajoEnvio();
			return mapReturn;
		}catch (Exception e) {
			// logger.error(e.getMessage());
			return null;
		}
	}
	@Override
	public List<TrAlmacen> obtenerCliente() throws ProquifaNetException{
		try {
			return trEnvioDAO.obtenerClientes();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public Map<String, Object> packingListPorCliente(Integer idCliente) throws ProquifaNetException{
		try {
			String packingList = "";
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, List<TrPackingList>> mapReturn = trEnvioDAO.packingListPorCliente(idCliente);
			for (Map.Entry<String, List<TrPackingList>> entry : mapReturn.entrySet()) {
				packingList += "'" + entry.getKey() + "'";
				packingList += ",";
			}
			map.put("PackingList", mapReturn);
			map.put("Comentarios", trEnvioDAO.obtenerComentarios(packingList.substring(0, packingList.length()-1)));
			return map;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	@Override
	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public Map<String, Integer> obtenerTotalesObjetivos() throws ProquifaNetException{
		try {
			return trEnvioDAO.obtenerTotales();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	@Override
	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public Map<String, List<TrEnvio>> obtenerTiempoXCliente() throws ProquifaNetException{
		try {
			return trEnvioDAO.obtenerTiempoXCliente();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public Map<String, List<TrEnvio>> estadisticasPagoCliente(Integer IdUsuario) throws ProquifaNetException{
		try{
			Map<String, List<TrEnvio>> mapReturn = new HashMap<String, List<TrEnvio>>();
			mapReturn = trEnvioDAO.PrioridadesPagoCliente(IdUsuario);
			return mapReturn;
		}catch (Exception e) {
			// logger.error(e.getMessage());
			return null;
		}
	}
}
