package com.proquifa.net.resource.finanzas;

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
import com.proquifa.net.modelo.finanzas.Paybook;
import com.proquifa.net.negocio.finanzas.PaybookService;

@RestController
@CrossOrigin
public class PaybookResource {
	
	@Autowired
	PaybookService paybookService;
	
	final Logger log = LoggerFactory.getLogger(PaybookResource.class);
	
	@PostMapping("/reportePaybook")
	public ResponseEntity<StatusMessage> atenderCobro(@RequestBody Parametro parametro){
		log.info("reportePaybook");
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			List<Paybook> mapReturn = paybookService.obtenerTransactions(parametro.getFinicio(), parametro.getFfin());
			
			mensaje.setCurrent(mapReturn);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}

}
