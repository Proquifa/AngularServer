package com.proquifa.net.persistencia.finanzas;

import java.util.Date;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;

public interface TipoCambioDAO {

	public boolean insertTCambioDOF(Date fecha, String tCambio) throws ProquifaNetException;

	public boolean updateTCambioDOF(Date fecha, String tCambio) throws ProquifaNetException;
	
	public boolean existTCambioDOF(Date fecha) throws ProquifaNetException;
}
