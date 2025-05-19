package com.proquifa.net.negocio.cobrosypagos.facturista;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;

public interface UtilFacturaService {

	void generarFacturaPDF(String folio) throws ProquifaNetException;
}
