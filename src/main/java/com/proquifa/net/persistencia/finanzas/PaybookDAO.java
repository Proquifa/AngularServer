package com.proquifa.net.persistencia.finanzas;

import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.finanzas.Paybook;

public interface PaybookDAO {

	/**
	 * 
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Paybook> obtenerTransactions(Date inicio, Date fin) throws ProquifaNetException;

}
