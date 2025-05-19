package com.proquifa.net.resource.ventas;

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

import com.proquifa.net.modelo.comun.DocumentoRecibido;
import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.StatusMessage;
import com.proquifa.net.negocio.ventas.admoncomunicacion.RecepcionDocumentosService;
@RestController
@CrossOrigin
public class RecepcionDocumentosResource {
	@Autowired
	RecepcionDocumentosService recepcionDocumentosService;
	
	final Logger log = LoggerFactory.getLogger(RecepcionDocumentosResource.class);
	
	
	@PostMapping("/buscarDocumentoRecibidoPorFolio")
	public ResponseEntity<StatusMessage> buscarDocumentoRecibidoPorFolio(@RequestBody Parametro parametro){
		log.info("on buscarDocumentoRecibidoPorFolio");
		StatusMessage mensaje = new StatusMessage();
		
		log.info("Folio:");
		log.info(parametro.getFolio());
		log.info("porFolio:");
		log.info("",parametro.getPorFolio());
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			List<DocumentoRecibido> listaDocumentosRecibidos= recepcionDocumentosService.buscarDocumentoRecibidoPorFolio(parametro.getFolio(), parametro.getPorFolio());
						mensaje.setCurrent(listaDocumentosRecibidos);                                            
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			log.info(e.toString());
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/buscarDocumentosXBA")
	public ResponseEntity<StatusMessage> buscarDocumentosXBA(@RequestBody Parametro parametro){
		log.info("on buscarDocumentosXBA");
		StatusMessage mensaje = new StatusMessage();
		
		log.info("Fecha inicio:");
		log.info("",parametro.getFinicio());
		log.info("Fecha fin:");
		log.info("",parametro.getFfin());
		log.info("Empresa:");
		log.info(parametro.getEmpresa());
		log.info("Referencia:");
		log.info(parametro.getReferencia());
		log.info("Destinatario:");
		log.info(parametro.getDestinatario());
		log.info("Tipo:");
		log.info(parametro.getTipo());
		log.info("abiertoCerrado:");
		log.info(parametro.getAbiertoCerrado());
		log.info("abiertoCerrado:");
		log.info(parametro.getAbiertoCerrado());
		log.info("cPago:");
		log.info(parametro.getcPago());
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

 
			List<DocumentoRecibido> listaDocumentosXBA= recepcionDocumentosService.buscarDocumentosXBA(parametro);
						mensaje.setCurrent(listaDocumentosXBA);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
}
