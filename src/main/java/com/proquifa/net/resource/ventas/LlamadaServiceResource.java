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

import com.proquifa.net.modelo.comun.StatusMessage;
import com.proquifa.net.modelo.ventas.admoncomunicacion.Llamada;
import com.proquifa.net.negocio.ventas.LlamadaService;

@RestController
@CrossOrigin

public class LlamadaServiceResource {
	
	final Logger log = LoggerFactory.getLogger(LlamadaServiceResource.class);

	@Autowired
	LlamadaService llamadaservice;
	
	@PostMapping("/obtenerLlamadas")
	public ResponseEntity<StatusMessage> obtenerLlamadas(@RequestBody Llamada llamada){
		log.info("on obtenerLlamadas");
		StatusMessage mensaje = new StatusMessage();
		log.info("Fecha Inicio");
		log.info("",llamada.getFechaInicio());
		log.info("Fecha Fin");
		log.info("",llamada.getFechaFin());
		log.info("Destino");
		log.info(llamada.getDestino());
		log.info("Empresa");
		log.info(llamada.getEmpresa());
		log.info("Atendio");
		log.info(llamada.getAtendio());
		log.info("Estado");
		log.info(llamada.getEstado());
				
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			List<Llamada> listaLlamadas = llamadaservice.obtenerLlamadas(llamada);
						mensaje.setCurrent(listaLlamadas);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
}
