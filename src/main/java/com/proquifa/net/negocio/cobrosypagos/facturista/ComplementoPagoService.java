package com.proquifa.net.negocio.cobrosypagos.facturista;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;

public interface ComplementoPagoService {


	boolean generarComplemento(String id) throws ProquifaNetException;

	boolean generarNotaCredito(String idFactura) throws ProquifaNetException;

}
