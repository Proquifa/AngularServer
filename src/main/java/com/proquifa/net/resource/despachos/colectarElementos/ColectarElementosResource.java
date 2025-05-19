package com.proquifa.net.resource.despachos.colectarElementos;

import java.util.ArrayList;
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
import com.proquifa.net.modelo.despachos.EmbalarPedido;
import com.proquifa.net.negocio.despachos.ColectarElementosService;

@RestController
@CrossOrigin
public class ColectarElementosResource {
	@Autowired
	ColectarElementosService colectarElementosService;
	
	final Logger log = LoggerFactory.getLogger(ColectarElementosResource.class);

	@PostMapping("/obtenerElementosColectar")
	public ResponseEntity<StatusMessage> consultaPartidasEmbalar(@RequestBody String estadoPedido){
		StatusMessage mensaje = new StatusMessage();
		try {
			List<EmbalarPedido> embalarPedido = new ArrayList<EmbalarPedido>();
			 log.info("estadoPedido:"+ estadoPedido);
			 mensaje.setStatus(HttpStatus.OK.value());
			 mensaje.setMessage("ok");	
			 embalarPedido  = colectarElementosService.obtenerElementosColectar(estadoPedido);
			 mensaje.setCurrent(embalarPedido);
			 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
}
