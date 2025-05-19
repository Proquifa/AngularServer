/**
 * 
 */
package com.proquifa.net.resource.operaciones;

import java.util.HashMap;
import java.util.List;
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
import com.proquifa.net.modelo.operaciones.Prioridades;
import com.proquifa.net.negocio.operaciones.PrioridadesService;

/**
 * @author ymendez
 *
 */
@RestController
@CrossOrigin
public class ConsolaPrioridadesResource {

	@Autowired
	PrioridadesService prioridadesService;
	
	@PostMapping("/prioridades/obtenerPrioridades")
	public ResponseEntity<StatusMessage> obtenerPrioridades(){
		StatusMessage mensaje = new StatusMessage();
		try {
			Map<String, List<Prioridades>> mapReturn = new HashMap<String, List<Prioridades>>();
			 mensaje.setStatus(HttpStatus.OK.value());
			 mensaje.setMessage("ok");	
			 mapReturn = prioridadesService.obtenerListado();
			 mapReturn.put("barra", prioridadesService.obtenerBotoneraPrioridades());
			 mensaje.setCurrent(mapReturn);
			 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);			
		} catch (Exception e) {
			// TODO: handle exception
		}		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/prioridades/envio/obtenerPrioridades")
	public ResponseEntity<StatusMessage> obtenerPrioridadesEnvio(){
		StatusMessage mensaje = new StatusMessage();
		try {
			Map<String, List<Prioridades>> mapReturn = new HashMap<String, List<Prioridades>>();
			 mensaje.setStatus(HttpStatus.OK.value());
			 mensaje.setMessage("ok");	
			 mapReturn = prioridadesService.obtenerListadoEnvio();
			 mensaje.setCurrent(mapReturn);
			 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);			
		} catch (Exception e) {
			// TODO: handle exception
		}		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/prioridades/stock/obtenerPrioridades")
	public ResponseEntity<StatusMessage> obtenerPrioridadesStock(){
		StatusMessage mensaje = new StatusMessage();
		try {
			Map<String , List<Prioridades>> mapReturn = new HashMap<String, List<Prioridades>>();
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			mapReturn = prioridadesService.obtenerListadoStock();
			mapReturn.put("MARCAS", prioridadesService.obtenerMarcas());
			mensaje.setCurrent(mapReturn);
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			mensaje.setStatus(HttpStatus.FORBIDDEN.value());
			 mensaje.setMessage("Error");
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
		}
	}
	@PutMapping("/prioridades/actualizarUrgencia")
	public ResponseEntity<StatusMessage> actualizarUrgencia(@RequestBody Map<String, Object> data){
		StatusMessage mensaje = new StatusMessage();
		try {
			 mensaje.setStatus(HttpStatus.OK.value());
			 mensaje.setMessage("ok");	
			 mensaje.setCurrent(prioridadesService.guardarUrgencia(data.get("idPPedidos").toString(), Integer.parseInt(data.get("urgencia").toString())));
			 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}

	
	@PutMapping("/prioridades/actualizarPausado")
	public ResponseEntity<StatusMessage> guardarPausa(@RequestBody Map<String, Object> data){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");	
			mensaje.setCurrent(prioridadesService.guardarPausa(data.get("idPPedidos").toString(), Integer.parseInt(data.get("pausado").toString())));
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PutMapping("/prioridades/reanudarEnvio")
	public ResponseEntity<StatusMessage> reanudarEnvio(@RequestBody Map<String, Object> data){
		StatusMessage mensaje = new StatusMessage();
		try {
			 mensaje.setStatus(HttpStatus.OK.value());
			 mensaje.setMessage("ok");	
			 mensaje.setCurrent(prioridadesService.guardarUrgenciaEnvio(data.get("idPPedidos").toString(), Integer.parseInt(data.get("urgencia").toString())));
			 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	@PutMapping("/prioridades/habilitarEntrega")
	public ResponseEntity<StatusMessage> guardarFacturaRemision(@RequestBody Map<String, Object> data) {
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");	
			mensaje.setCurrent(prioridadesService.guardarFacturaRemision(data.get("idPPedidos").toString(), Integer.parseInt(data.get("facturaRemision").toString()), Integer.parseInt(data.get("remisionar").toString())));
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PutMapping("/prioridades/stock/updateStock")
	public ResponseEntity<StatusMessage> updateStock(@RequestBody Prioridades data) {
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			mensaje.setCurrent(prioridadesService.updateStock(data));
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/prioridades/destruccion/obtenerListado")
	public ResponseEntity<StatusMessage> obtenerLista(){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			mensaje.setCurrent(prioridadesService.obtenerListadoDestruccion());
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/prioridades/destruccion/cerrarPendientes")
	public ResponseEntity<StatusMessage> enviarDestruccion(@RequestBody List<Prioridades> lst) {
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			mensaje.setCurrent(prioridadesService.enviarDestruccion(lst));
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.FORBIDDEN);
	}
}
