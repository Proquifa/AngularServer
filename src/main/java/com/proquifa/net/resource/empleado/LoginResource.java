package com.proquifa.net.resource.empleado;

import java.util.Map;

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
import com.proquifa.net.modelo.comun.StatusMessage;
import com.proquifa.net.negocio.comun.EmpleadoService;

@RestController
@CrossOrigin
public class LoginResource {
	@Autowired
	EmpleadoService empleadoService;
	
	final Logger log = LoggerFactory.getLogger(LoginResource.class);
	
	@PostMapping("/validaUsuario")
	public ResponseEntity<StatusMessage> validarUsuario(@RequestBody Empleado usuario){
		
		StatusMessage mensaje = new StatusMessage();
		 log.info(usuario.getUsuario());
		 log.info(usuario.getPassword());
		 
		 Empleado aux = new Empleado();
		 Boolean emp = false;
		 
		try {
			log.info("",empleadoService);
			 emp = empleadoService.validarContrasena(usuario);
			 if (emp){
	
				  aux=empleadoService.obtenerEmpleadoXUsuario(usuario.getUsuario());
				  mensaje.setMessage("Usuario Valido");
				  mensaje.setCurrent(aux);
				  mensaje.setStatus(HttpStatus.OK.value());
				  mensaje.setMessage("ok");		 
				 
				 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
			 }
			 else
			  {
				  mensaje.setMessage("Usuario Invalido");
				  mensaje.setCurrent(null);
			  }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
		
	}
	
	@PostMapping("/validaProceso")
	public ResponseEntity<StatusMessage> validaProceso(@RequestBody Map<String, Long> data){
		
		StatusMessage mensaje = new StatusMessage();
		try {
			if (data.containsKey("idProceso")) {
			 mensaje.setStatus(HttpStatus.OK.value());
			 mensaje.setMessage("ok");
			 mensaje.setCurrent(empleadoService.obtenerResponsableDeSubProceso((Long) data.get("idProceso")));
			 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}

}
