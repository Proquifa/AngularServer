package com.proquifa.net.persistencia.cobrosypagos.proforma;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;

public interface ProformaDAO {
	
	String findFolioProforma(String condiciones) throws ProquifaNetException;

}
