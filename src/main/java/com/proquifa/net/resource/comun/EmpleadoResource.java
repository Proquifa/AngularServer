package com.proquifa.net.resource.comun;

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

import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.StatusMessage;
import com.proquifa.net.negocio.comun.EmpleadoService;

@RestController
@CrossOrigin
public class EmpleadoResource {
	@Autowired
	EmpleadoService empleadoService;
	
	final Logger log = LoggerFactory.getLogger(EmpleadoResource.class);

	@PostMapping("/obtenerEmpleadosPorTipo")
	public ResponseEntity<StatusMessage> obtenerEmpleadosPorTipo(@RequestBody Parametro parametro){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			
			log.info("tipo:");
			log.info(parametro.getTipo());
			
			List<Empleado> lstEmpleado = empleadoService.obtenerEmpleadosPorTipo(parametro.getTipo());
			mensaje.setCurrent(lstEmpleado);
			
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
}
