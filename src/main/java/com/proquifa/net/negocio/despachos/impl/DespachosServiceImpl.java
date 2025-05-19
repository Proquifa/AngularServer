/**
 * 
 */
package com.proquifa.net.negocio.despachos.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.negocio.despachos.DespachosService;
import com.proquifa.net.persistencia.despachos.DespachosDAO;

/**
 * @author ymendez
 *
 */
@Service("despachosService")
public class DespachosServiceImpl implements DespachosService {

	
	@Autowired
	DespachosDAO despachosDAO;
	
	/* (non-Javadoc)
	 * @see com.proquifa.net.negocio.despachos.DespachosService#guardarHistorialPrioridades()
	 */
	@Override
	@Transactional(readOnly=false, transactionManager = "ds1TransactionManager", rollbackFor = Exception.class)
	public void guardarHistorialPrioridades() throws ProquifaNetException {
		try {
			despachosDAO.guardarHistorialPrioridades();
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}

	}

}
