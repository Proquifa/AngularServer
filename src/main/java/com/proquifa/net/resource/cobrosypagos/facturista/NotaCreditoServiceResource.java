package com.proquifa.net.resource.cobrosypagos.facturista;

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

import com.proquifa.net.modelo.cobrosypagos.facturista.NotaCredito;
import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.StatusMessage;
import com.proquifa.net.negocio.cobrosypagos.facturista.NotaCreditoService;


@RestController
@CrossOrigin

public class NotaCreditoServiceResource {

	@Autowired
	NotaCreditoService notaCreditoservice;
	
	final Logger log = LoggerFactory.getLogger(NotaCreditoServiceResource.class);
	
	@PostMapping("/getNotaCreditoAvanzada")
	public ResponseEntity<StatusMessage> getNotaCreditoAvanzado (@RequestBody Parametro Parametro){
		log.info("on getNotaCreditoAvanzada");
		StatusMessage mensaje = new StatusMessage();
		log.info("Fecha Inicio");
		log.info("",Parametro.getFechaInicio());
		log.info("Fecha Fin");
		log.info("",Parametro.getFechaFin());
		log.info("Id Cleinte");
		log.info("",Parametro.getIdCliente());
		log.info("Nombre Facturo");
		log.info(Parametro.getFpor());
		log.info("Estado");
		log.info(Parametro.getEstado());
		log.info("Id Usuario Logueado");
		log.info("",Parametro.getIdUsuarioLogueado());
		log.info("Cobrador");
		log.info("",Parametro.getCobrador());
	
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			List<NotaCredito> listaNotasCredito = notaCreditoservice.getNotaCreditoAvanzada(Parametro.getFechaInicio(),Parametro.getFechaFin(),Parametro.getIdCliente(),Parametro.getFpor(),Parametro.getEstado(),Parametro.getIdUsuarioLogueado(),Parametro.getCobrador());
						mensaje.setCurrent(listaNotasCredito);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/getNotaCreditoRapida")
	public ResponseEntity<StatusMessage> getNotaCreditoRapida (@RequestBody Parametro Parametro){
		log.info("on getNotaCreditoRapida");
		StatusMessage mensaje = new StatusMessage();
		log.info("Folio Nota");
		log.info("",Parametro.getFolioNota());
		log.info("S Pedido Interno");
		log.info(Parametro.getcPedido());
		log.info("Id Usuario Logueado");
		log.info("",Parametro.getIdUsuarioLogueado());
	
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			List<NotaCredito> listaNotasCredito = notaCreditoservice.getNotaCreditoRapida(Parametro.getFolioNota(), Parametro.getcPedido(),Parametro.getIdUsuarioLogueado());
						mensaje.setCurrent(listaNotasCredito);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
}
