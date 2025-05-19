/**
 * 
 */
package com.proquifa.net.negocio.operaciones;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proquifa.net.modelo.comun.Folio;
import com.proquifa.net.modelo.comun.Pendiente;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.operaciones.Prioridades;
import com.proquifa.net.persistencia.comun.FolioDAO;
import com.proquifa.net.persistencia.comun.impl.FolioDAOImpl;
import com.proquifa.net.persistencia.operaciones.PrioridadesDAO;

import javassist.expr.NewArray;

/**
 * @author ymendez
 *
 */


@Service("prioridadesService")
public class PrioridadesSeriviceImpl implements PrioridadesService {

	@Autowired
	PrioridadesDAO prioridadesDAO;
	@Autowired
	FolioDAO folioDAO;
	/* (non-Javadoc)
	 * @see com.proquifa.net.negocio.operaciones.PrioridadesService#obtenerBotoneraPrioridades()
	 */
	@Override
	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public List<Prioridades> obtenerBotoneraPrioridades() throws ProquifaNetException {
		try {
			List<Prioridades> lstReturn = prioridadesDAO.obtenerBotoneraPrioridades();
			Prioridades todas = new Prioridades();
			todas.setZona("TODAS");
			Integer total = 0;
			for (Prioridades prioridades : lstReturn) {
				if(!prioridades.getZona().equalsIgnoreCase("INSPECCIÓN") && !prioridades.getZona().equalsIgnoreCase("EMBALAR"))
				total+= prioridades.getTotalClientes();
			}
			if (total > 0) {
				todas.setTotalClientes(total);
				lstReturn.add(0, todas);
			}
			return lstReturn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<Prioridades>();
	}

	@Override
	public Map<String, List<Prioridades>> obtenerListado() throws ProquifaNetException {
		try {
			return prioridadesDAO.obtenerListado();
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, List<Prioridades>> mapReturn = new HashMap<String, List<Prioridades>>();
			mapReturn.put("TODAS", new ArrayList<Prioridades>());
			return mapReturn;
		}
	}

	@Override
	public boolean guardarUrgencia(String idPedidos, Integer urgencia) throws ProquifaNetException {
		try {
			return prioridadesDAO.guardarUrgencia(idPedidos, urgencia);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	
	@Override
	public boolean guardarPausa(String idPedidos, Integer urgencia) throws ProquifaNetException {
		try {
			return prioridadesDAO.guardarPausa(idPedidos, urgencia);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public Map<String, List<Prioridades>> obtenerListadoEnvio() throws ProquifaNetException {
		try {
			return prioridadesDAO.obtenerListadoEnvio();
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, List<Prioridades>> mapReturn = new HashMap<String, List<Prioridades>>();
			mapReturn.put("TODAS", new ArrayList<Prioridades>());
			return mapReturn;
		}
	}
	
	@Override
	public boolean guardarUrgenciaEnvio(String idPedidos, Integer urgencia) throws ProquifaNetException {
		try {
			return prioridadesDAO.guardarUrgenciaEnvio(idPedidos, urgencia);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean guardarFacturaRemision(String idPedidos, Integer facturaRemision, Integer remisionar) throws ProquifaNetException {
		try {
			return prioridadesDAO.guardarFacturaRemision(idPedidos, facturaRemision, remisionar);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public Map<String, List<Prioridades>> obtenerListadoStock() throws ProquifaNetException{
		try {
			return prioridadesDAO.obtenerListadoStock();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Map<String, List<Prioridades>> mapReturn = new HashMap<String, List<Prioridades>>();
			mapReturn.put("TODAS", new ArrayList<Prioridades>());
			return mapReturn;
		}
	}
	
	@Override
	public List<Prioridades> obtenerMarcas() throws ProquifaNetException {
		try {
			return prioridadesDAO.obtenerMarcasStock();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new ArrayList<Prioridades>();
	}
	
	@Override
	public Boolean updateStock(Prioridades producto) {
		try {
			Boolean insert= prioridadesDAO.deleteStock(producto);
		
			if(insert) {
				Pendiente pendiente = new Pendiente();
				pendiente.setDocto(String.valueOf(producto.getIdPCompra()));
				pendiente.setPartida(String.valueOf(producto.getPiezas()));
				
				return prioridadesDAO.insertarPendiente(pendiente);
			} else {
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public Map<String, Object> obtenerListadoDestruccion() {
		Map<String, Object> mapReturn = new HashMap<String, Object>();
		try {
			mapReturn.put("Todas", prioridadesDAO.obtenerListadoDestruccion());
			mapReturn.put("Marcas", prioridadesDAO.obtenerMarcasDestruccion());
			return mapReturn;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return  mapReturn;
	}
	
	@Override
	public String enviarDestruccion(List<Prioridades> productos) throws ProquifaNetException{
		try {
			Folio folio = folioDAO.obtenerFolioPorConcepto("Destrucción producto", true);
			System.out.println("Soy la lista  ==> ");
			for(int i = 0; i < productos.size(); i++) {
				prioridadesDAO.cerrarPendiente(productos.get(i), folio.getFolioCompleto());
				System.out.println(folio.getFolioCompleto());
			}
			
			return folio.getFolioCompleto();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}
		return null;
	}

}
