package com.proquifa.net.resource.staff;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proquifa.net.modelo.comun.StatusMessage;
import com.proquifa.net.modelo.staff.Trabajador;
import com.proquifa.net.negocio.staff.TrabajadorUnionService;

@RestController
@CrossOrigin
public class TrabajadorUnionResource {
	@Autowired
	TrabajadorUnionService trabajadorUnionService;
	
	@PostMapping("/getTrabajadorUnion")
	public ResponseEntity<StatusMessage> getTrabajadorUnion(){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			List<Trabajador> lstAsistencia = trabajadorUnionService.getTrabajadorUnion();
			mensaje.setCurrent(lstAsistencia);
			
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}

}
