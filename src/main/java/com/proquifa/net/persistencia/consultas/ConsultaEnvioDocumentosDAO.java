package com.proquifa.net.persistencia.consultas;

import java.util.List;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.ventas.enviodocumentos.EnvioDocumentos;

public interface ConsultaEnvioDocumentosDAO {

	List<EnvioDocumentos> findDocumentosEnviados( String condiciones) throws ProquifaNetException; 
	
	
	
}
