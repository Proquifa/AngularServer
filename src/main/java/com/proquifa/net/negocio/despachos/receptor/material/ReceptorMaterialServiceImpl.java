/**
 * 
 */
package com.proquifa.net.negocio.despachos.receptor.material;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.proquifa.net.modelo.comun.Pendiente;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.despachos.ReceptorMaterial;
import com.proquifa.net.modelo.despachos.mensajero.AsignarMensajero;
import com.proquifa.net.modelo.despachos.trabajarRuta.TrEnvio;
import com.proquifa.net.negocio.despachos.mensajero.MensajeroService;
import com.proquifa.net.persistencia.comun.PendienteDAO;
import com.proquifa.net.persistencia.despachos.receptor.material.ReceptorMaterialDAO;
import com.proquifa.net.persistencia.despachos.trabajarRuta.TrEnvioDAO;

/**
 * @author ymendez
 *
 */
@Service("receptorMaterialService")
public class ReceptorMaterialServiceImpl implements ReceptorMaterialService {

	@Autowired
	ReceptorMaterialDAO receptorMaterialDAO;

	@Autowired
	MensajeroService mensajeroService;

	@Autowired
	PendienteDAO pendienteDAO;

	@Autowired
	TrEnvioDAO trEnvioDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.proquifa.net.negocio.despachos.receptor.material.ReceptorMaterialService#
	 * getDatosGrafica(java.lang.Integer)
	 */
	@Override
	@Transactional(readOnly = true, transactionManager = "ds1TransactionManager")
	public Map<String, List<ReceptorMaterial>> getDatosGrafica(Integer idUsuario) throws ProquifaNetException {
		try {
			return receptorMaterialDAO.getDatosGrafica(idUsuario);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true, transactionManager = "ds1TransactionManager")
	public List<?> getGuias(Integer idUsuario) throws ProquifaNetException {
		try {
			return receptorMaterialDAO.getGuias(idUsuario);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional(readOnly = false, transactionManager = "ds1TransactionManager", rollbackFor = Exception.class)
	public Object finalizarEnvio(String guia, Integer idUsuario, Integer idPendiente) throws ProquifaNetException {
		try {
			List<String> folios = receptorMaterialDAO.obtenerFolio(guia);
			List<List<AsignarMensajero>> rutas = new ArrayList<List<AsignarMensajero>>();
			List<AsignarMensajero> lista = new ArrayList<AsignarMensajero>();
			Boolean error = false;
			rutas.add(lista);
			AsignarMensajero ruta = new AsignarMensajero();
			lista.add(ruta);

			ruta.setFolios(folios);
			ruta.setIdMensajero(idUsuario);
			ruta.setGuadalajara(true);

			for (List<AsignarMensajero> items : rutas) {
				if (lista != null && lista.size() > 0) {
					for (AsignarMensajero item : lista) {
						if (item != null && item.getFolios().size() > 0) {
							for (String folio : item.getFolios()) {
								Pendiente pendiente = new Pendiente();
								pendiente.setResponsable("GestorRutaGDL");
								pendiente.setTipoPendiente("Asignar Mensajero");
								pendiente.setDocto(folio);
								pendiente.setPartida(null);

								if (pendienteDAO.guardarPendiente_angular(pendiente) == 0L) {
									error = true;
								}
							}
						}
					}
				}
			}
			
			if (!error) {
				//			if (mensajeroService.publicarRuta(rutas)) {
				pendienteDAO.cerrarPendiente_angular(guia, null, "Receptor de Materiales");

				TrEnvio envio = new TrEnvio();
				envio.setIdPendiente(idPendiente);
				envio.setNumGuia(guia);
				envio.setIdUsuario(idUsuario);
				trEnvioDAO.registrarEnvio(envio);
				trEnvioDAO.actualizarEnvioGDL(envio);
			}
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return null;
	}

}
