package com.proquifa.net.resource.consultas;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.StatusMessage;
import com.proquifa.net.modelo.ventas.enviodocumentos.EnvioDocumentos;
import com.proquifa.net.negocio.consultas.ConsultaEnvioDocumentosService;
@RestController
@CrossOrigin
public class ConsultaEnvioDocumentosResourcer {
	
	final Logger log = LoggerFactory.getLogger(ConsultaEnvioDocumentosResourcer.class);
	
	@Autowired
	ConsultaEnvioDocumentosService consultaEnvioDocumentosService;
	@PostMapping("/obtenerEnvioCorreoDocumentos")
	public ResponseEntity<StatusMessage> obtenerEnvioCorreoDocumentos(@RequestBody Parametro parametro){
		log.info("on obtenerEnvioCorreoDocumentos");
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			
			log.info("Feche inicio:");
			log.info("",parametro.getFinicio());
			log.info("Fecha fin:");
		    log.info("",parametro.getFfin());
		    log.info("Destino:");
		    log.info("",parametro.getDestino());
		    log.info("Origen:");
		    log.info(parametro.getOrigen());
		    log.info("Tipo:");
		    log.info( parametro.getTipo());
		    log.info("Folio Documento:");
		    log.info( parametro.getFolioDocumento());
		    
		    
			List<EnvioDocumentos> documentos = consultaEnvioDocumentosService.obtenerEnvioCorreoDocumentos(parametro.getFinicio(), parametro.getFfin(), parametro.getDestino(),parametro.getOrigen(), parametro.getTipo(), parametro.getFolioDocumento());
			mensaje.setCurrent(documentos);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
}
