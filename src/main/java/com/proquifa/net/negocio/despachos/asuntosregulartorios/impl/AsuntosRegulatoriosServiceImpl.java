/**
 * 
 */
package com.proquifa.net.negocio.despachos.asuntosregulartorios.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.despachos.asuntosregulatorios.GestionarPap;
import com.proquifa.net.modelo.despachos.asuntosregulatorios.PermisoAdquisicion;
import com.proquifa.net.modelo.despachos.asuntosregulatorios.TotalesPAP;
import com.proquifa.net.negocio.despachos.asuntosregulartorios.AsuntosRegulatoriosService;
import com.proquifa.net.persistencia.comun.PendienteDAO;
import com.proquifa.net.persistencia.despachos.asuntosregulatorios.AsuntosRegulatoriosDAO;

/**
 * @author ymendez
 *
 */
@Service("asuntosRegulatoriosService")
public class AsuntosRegulatoriosServiceImpl implements AsuntosRegulatoriosService {

	@Autowired
	AsuntosRegulatoriosDAO asuntosRegulatoriosDAO;
	
	@Autowired
	PendienteDAO pendienteDAO;
	
	/* (non-Javadoc)
	 * @see com.proquifa.net.negocio.despachos.asuntosregulartorios.AsuntosRegulatoriosService#obtenerPendientesPap(java.lang.Integer)
	 */
	@Override
	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public List<GestionarPap> obtenerPendientesPap(Integer idUsuario) throws ProquifaNetException {
		try {
			return asuntosRegulatoriosDAO.obtenerPendientesPap(idUsuario);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public Map<String, List<TotalesPAP>> obtenerDatosGrafica(Integer idUsuario) throws ProquifaNetException {
		try {
			return asuntosRegulatoriosDAO.obtenerDatosGrafica(idUsuario);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly=false, transactionManager = "ds1TransactionManager", rollbackFor = Exception.class)
	public Integer saveGestionarPAP(PermisoAdquisicion permiso) throws ProquifaNetException {
		try {
			pendienteDAO.cerrarPendiente_angular(permiso.getIdPedido().toString(), permiso.getIdPPedido().toString(), "PAP por cargar");
			return asuntosRegulatoriosDAO.saveGestionarPAP(permiso);
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return 0;
	}

}
