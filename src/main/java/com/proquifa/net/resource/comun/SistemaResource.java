package com.proquifa.net.resource.comun;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.proquifa.net.modelo.comun.StatusMessage;
import com.proquifa.net.negocio.comun.SistemaService;

@RestController
@CrossOrigin
public class SistemaResource {
	@Autowired
	SistemaService sistemaService;
	
	@Autowired
    private Environment env; // Contains Properties Load by @PropertySource

	@PostMapping("/versionSistema")
	public ResponseEntity<StatusMessage> obtenerVersionSistema(@RequestBody String nombre){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			mensaje.setCurrent(sistemaService.obtenerVersionSistema(nombre));
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@GetMapping("/systemVersion")
	public String getPropertyValue(){	
		return env.getProperty("system.version");
	}
	
}
