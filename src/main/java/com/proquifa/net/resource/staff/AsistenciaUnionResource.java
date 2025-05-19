package com.proquifa.net.resource.staff;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.StatusMessage;
import com.proquifa.net.modelo.staff.Asistencia;
import com.proquifa.net.negocio.staff.AsistenciaUnionService;

@RestController
@CrossOrigin
public class AsistenciaUnionResource {
	@Autowired
	AsistenciaUnionService asistenciaUnionService;
	
	@PostMapping("/getAsistenciaUnion")
	public ResponseEntity<StatusMessage> getAsistenciaUnion(@RequestBody Parametro parametro){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			List<Asistencia> lstAsistencia = asistenciaUnionService.getAsistenciaUnion(parametro.getFechaInicio(), parametro.getFechaFin(), parametro.getIdTrabajador(), parametro.getTipoChecada(), parametro.getArea(), parametro.getDepto(), parametro.getCategoria(), parametro.getIncidencia(), parametro.getLocalidad(), parametro.getNombreTrabajador());
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
