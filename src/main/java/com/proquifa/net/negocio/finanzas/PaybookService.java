package com.proquifa.net.negocio.finanzas;

import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.finanzas.Paybook;

public interface PaybookService {

	List<Paybook> obtenerTransactions(Date inicio, Date fin) throws ProquifaNetException;

}
