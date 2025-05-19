package com.proquifa.net.negocio.finanzas;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;

public interface TipoCambioService {

	boolean obtenerTCambioDOF() throws ProquifaNetException;
	
}
