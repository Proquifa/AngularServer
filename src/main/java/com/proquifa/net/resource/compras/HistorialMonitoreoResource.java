package com.proquifa.net.resource.compras;

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

import com.proquifa.net.modelo.compras.HistorialMonitoreo;
import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.StatusMessage;
import com.proquifa.net.negocio.compras.CompraService;
import com.proquifa.net.negocio.compras.HistorialMonitoreoService;
import com.proquifa.net.negocio.consultas.ConsultaCobrosService;

@RestController
@CrossOrigin
public class HistorialMonitoreoResource {
	
	final Logger log = LoggerFactory.getLogger(HistorialMonitoreoResource.class);
	
	@Autowired 
	HistorialMonitoreoService historialMonitoreoService;
	
	@Autowired
	CompraService comprasService; 
	
	@Autowired
	ConsultaCobrosService consultaCobroService;

	@PostMapping("/obtenerHistorialMonitoreo")
	public ResponseEntity<StatusMessage> obtenerHistorialMonitoreo(@RequestBody Parametro parametro){
		log.info("on obtenerHistorialMonitoreo");
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			List<HistorialMonitoreo> listaHistorialMonitoreo = historialMonitoreoService.obtenerHistorialMonitoreo(parametro.getordenCompra(),parametro.getpartida());
			mensaje.setCurrent(listaHistorialMonitoreo);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
}