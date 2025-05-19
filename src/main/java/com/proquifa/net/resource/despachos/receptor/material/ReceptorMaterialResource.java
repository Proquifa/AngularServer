/**
 * 
 */
package com.proquifa.net.resource.despachos.receptor.material;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.proquifa.net.modelo.comun.StatusMessage;
import com.proquifa.net.negocio.despachos.receptor.material.ReceptorMaterialService;

/**
 * @author ymendez
 *
 */
@RestController
@CrossOrigin
public class ReceptorMaterialResource {

	@Autowired
	ReceptorMaterialService receptorMaterialService;
	
	@PostMapping("/receptorMaterial/datosGrafica")
	public ResponseEntity<StatusMessage> getDatosGrafica(@RequestBody Integer idUsuario){
		StatusMessage mensaje = new StatusMessage();
		try {
			 mensaje.setStatus(HttpStatus.OK.value());
			 mensaje.setMessage("ok");
			 mensaje.setCurrent(receptorMaterialService.getDatosGrafica(idUsuario));
			 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/receptorMaterial/getGuias")
	public ResponseEntity<StatusMessage> getGuias(@RequestBody Integer idUsuario){
		StatusMessage mensaje = new StatusMessage();
		try {
			 mensaje.setStatus(HttpStatus.OK.value());
			 mensaje.setMessage("ok");
			 mensaje.setCurrent(receptorMaterialService.getGuias(idUsuario));
			 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/receptorMaterial/finalizar")
	public ResponseEntity<StatusMessage> finalizarEnvio(@RequestBody Map<String, Object> data){
		StatusMessage mensaje = new StatusMessage();
		try {
			 mensaje.setStatus(HttpStatus.OK.value());
			 mensaje.setMessage("ok");
			 mensaje.setCurrent(receptorMaterialService.finalizarEnvio((String) data.get("guia"), (Integer) data.get("idUsuario"), (Integer) data.get("idPendiente")));
			 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
}
