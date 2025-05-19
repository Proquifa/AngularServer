/**
 * 
 */
package com.proquifa.net.resource.comun;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.proquifa.net.modelo.comun.StatusMessage;
import com.proquifa.net.negocio.comun.GestionarBOService;

/**
 * @author ymendez
 *
 */
@RestController
@CrossOrigin
public class GestionarBOResource {
	
	@Autowired
	GestionarBOService gestionarBOService;

	@PostMapping("/gestionarBO/obtenerGraficaProveedor")
	public ResponseEntity<StatusMessage> obtenerGraficaProveedor(){
		StatusMessage mensaje = new StatusMessage();
		try {
			 mensaje.setStatus(HttpStatus.OK.value());
			 mensaje.setMessage("ok");
			 Map<String, Object> map = gestionarBOService.obtenerGraficaProveedor();
			 mensaje.setCurrent(map);
			 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);			
		} catch (Exception e) {
			// TODO: handle exception
		}		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	@PostMapping("/gestionarBO/obtenerProductosProveedor")
	public ResponseEntity<StatusMessage> obtenerProductosBOPorProveedor(@RequestBody Integer idProveedor){
		StatusMessage mensaje = new StatusMessage();
		try {
			 mensaje.setStatus(HttpStatus.OK.value());
			 mensaje.setMessage("ok");
			 mensaje.setCurrent(gestionarBOService.obtenerProductosBOPorProveedor(idProveedor));
			 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);			
		} catch (Exception e) {
			// TODO: handle exception
		}		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	@PutMapping("/gestionarBO/finalizarProductoBO")
	public ResponseEntity<StatusMessage> finalizarProductoBO(@RequestBody Map<String, Object> data){
		StatusMessage mensaje = new StatusMessage();
		try {
			 mensaje.setStatus(HttpStatus.OK.value());
			 mensaje.setMessage("ok");
			 mensaje.setCurrent(gestionarBOService.finalizarProductoBO(data));
			 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);			
		} catch (Exception e) {
			// TODO: handle exception
		}		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
}
