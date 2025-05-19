package com.proquifa.net.resource.cobrosypagos.facturista;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.proquifa.net.modelo.comun.StatusMessage;
import com.proquifa.net.negocio.cobrosypagos.facturista.ComplementoPagoService;


@RestController
@CrossOrigin
public class ComplementoPagoResource {
	@Autowired
	ComplementoPagoService complementoService;
	
	@PostMapping("prueba/complementoPago")
	public ResponseEntity<StatusMessage> complementoPago(@RequestBody String idFactura){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("OK");
			mensaje.setCurrent(complementoService.generarComplemento(idFactura));
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("prueba/notaCredito")
	public ResponseEntity<StatusMessage> notaCredito(@RequestBody String idFactura) {
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("OK");
			mensaje.setCurrent(complementoService.generarNotaCredito(idFactura));
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
}
