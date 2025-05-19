package com.proquifa.net.negocio.comun.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.proquifa.net.modelo.comun.Folio;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.negocio.comun.FolioService;
import com.proquifa.net.persistencia.comun.FolioDAO;

@Transactional(readOnly=true, transactionManager="ds1TransactionManager")
public class FolioServiceImpl  implements FolioService {
	@Autowired
	private FolioDAO folioDAO;
	/* (non-Javadoc)
	 * @see mx.com.proquifa.proquifanet.negocio.comun.FolioService#obtenerFolioPorConcepto(java.lang.String)
	 */
	
	public Folio obtenerFolioPorConcepto(String concepto)
			throws ProquifaNetException {
		return this.folioDAO.obtenerFolioPorConcepto(concepto,false);
	}


}
