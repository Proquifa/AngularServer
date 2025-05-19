package com.proquifa.net.negocio.consultas;

import java.util.Date; 
import java.util.List;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.ventas.enviodocumentos.EnvioDocumentos;

/**
 * @author miguelangeldamianlopez
 *
 */

public interface ConsultaEnvioDocumentosService {

	List<EnvioDocumentos> obtenerEnvioCorreoDocumentos(Date finicio, Date ffin, int destino, String origen, String tipo, String folio)
			throws ProquifaNetException;
	
}

	