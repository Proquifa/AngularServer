/**
 * 
 */
package com.proquifa.net.resource.rechazo;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.proquifa.net.modelo.compras.rechazos.Rechazos;
import com.proquifa.net.modelo.comun.StatusMessage;
import com.proquifa.net.negocio.compras.RechazoService;

/**
 * @author ymendez
 *
 */
@RestController
@CrossOrigin
public class GestorRechazoResource {
	
	@Autowired
	RechazoService rechazoService;

	@PostMapping("/gestorQuarentena/piezasRechazadas")
	public ResponseEntity<StatusMessage> obtenerDocumentacionFaltant(@RequestBody Rechazos datos){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("OK");
			Map<String, Object> map = rechazoService.obtenerGraficaRechazoInspeccion(datos);
			map.put("lista", rechazoService.obtenerPiezasRechazadas(datos));
			mensaje.setCurrent(map);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	@PostMapping("/gestorQuarentena/piezasRechazadasPorProveedor")
	public ResponseEntity<StatusMessage> obtenerDocumentacionFaltante(@RequestBody Rechazos datos){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("OK");
			mensaje.setCurrent(rechazoService.obtenerPiezasRechazadasPorInspeccion(datos.getIdProveedor(), datos.getConcepto()));
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PutMapping("/gestorQuarentena/finalizarCuarentena")
	public ResponseEntity<StatusMessage> finalizarCuarentena(@RequestBody Map<String, Object> data){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("OK");
			
			mensaje.setCurrent(rechazoService.finalizarCuarentena(data));
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PutMapping("/reclamoProducto/finalizarReclamo")
	public ResponseEntity<StatusMessage> finalizarReclamo(@RequestBody Map<String, Object> data){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("OK");
			mensaje.setCurrent(rechazoService.finalizarReclamo(data));
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
}
