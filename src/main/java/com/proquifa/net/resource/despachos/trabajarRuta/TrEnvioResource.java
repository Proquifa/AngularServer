package com.proquifa.net.resource.despachos.trabajarRuta;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.StatusMessage;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.despachos.trabajarRuta.TrEnvio;
import com.proquifa.net.negocio.despachos.trabajarRuta.TrEnvioService;

@RestController
@CrossOrigin
public class TrEnvioResource {
	@Autowired
	TrEnvioService trEnvioService;
	
	@PostMapping("/trEnvio/obtenerObjetivos")
	public ResponseEntity<StatusMessage> obtenerObjetivos(){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			
			mensaje.setCurrent(trEnvioService.getObjetivos());
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/trEnvio/obtenerMontosTab")
	public ResponseEntity<StatusMessage> obtenerMontosTab(){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			
			mensaje.setCurrent(trEnvioService.getMontosTab());
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/trEnvio/obtenerInfoGraficas")
	public ResponseEntity<StatusMessage> obtenerInfoGraficas(){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			
			mensaje.setCurrent(trEnvioService.getGraficas());
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/trEnvio/obtenerPackingListClient")
	public ResponseEntity<StatusMessage> obtenerPackingListClient(){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			
			mensaje.setCurrent(trEnvioService.getPackingListClient());
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/ObtenerEstadisticaUsuarioEnvioPL")
	public ResponseEntity<StatusMessage> EstadisticaUsuarioEmbalar(@RequestBody Parametro parametro){
		StatusMessage mensaje = new StatusMessage();
		try {
			Map<String, List<TrEnvio>> mapReturn = new HashMap<String, List<TrEnvio>>();
			 mensaje.setStatus(HttpStatus.OK.value());
			 mensaje.setMessage("ok");	
			 mapReturn  = trEnvioService.obtenerEstadisticasUsuarioTR(parametro);
			 mensaje.setCurrent(mapReturn);
			 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);			
		} catch (Exception e) {
			// TODO: handle exception
		}		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	@PostMapping("/RegistrarTrEnvio")
	public ResponseEntity<StatusMessage> RegistrarEnvio(@RequestBody TrEnvio envio){
		StatusMessage mensaje = new StatusMessage();
		try {
			
			 mensaje.setStatus(HttpStatus.OK.value());
			 mensaje.setMessage("ok");	
			 boolean registro  = trEnvioService.registrarEnvio(envio);
			 if (registro) {
				 trEnvioService.finalizarEjecutarRutaEnvio(envio);
				 trEnvioService.actualizarEnvio(envio);
			 }
			 mensaje.setCurrent(registro);
			 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);			
		} catch (Exception e) {
			// TODO: handle exception
		}		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/ActualizarTrEnvio")
	public ResponseEntity<StatusMessage> ActualizarEnvio(@RequestBody TrEnvio envio){
		StatusMessage mensaje = new StatusMessage();
		try {
			
			 mensaje.setStatus(HttpStatus.OK.value());
			 mensaje.setMessage("ok");	
			 boolean registro  = trEnvioService.actualizarEnvio(envio);
			 mensaje.setCurrent(registro);
			 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);			
		} catch (Exception e) {
			// TODO: handle exception
		}		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
//	@GetMapping
	@PostMapping("/obtenerTiempoTrabajoEnvio")
	public ResponseEntity<StatusMessage> obtenerTiempoTrabajoEnvio(){
		StatusMessage mensaje = new StatusMessage();
		try {
			Map<String, List<TrEnvio>> mapReturn = new HashMap<String, List<TrEnvio>>();
			 mensaje.setStatus(HttpStatus.OK.value());
			 mensaje.setMessage("ok");	
			 mapReturn  = trEnvioService.obtenerTiempoTrabajoEnvio();
			 mensaje.setCurrent(mapReturn);
			 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);			
		} catch (Exception e) {
			// TODO: handle exception
		}		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value = "/trEnvio/uploadFile/{numGuia}/{tipo}", method = RequestMethod.POST)
	public ResponseEntity<StatusMessage> subirArchivos(@RequestParam LinkedList<MultipartFile> file, @PathVariable String numGuia, @PathVariable String tipo) {
		StatusMessage mensaje = new StatusMessage();
		try {
			 mensaje.setStatus(HttpStatus.OK.value());
			 mensaje.setMessage("ok");
			 
			 Funcion funcion = new Funcion();
			 String ruta = funcion.obtenerRutaCompletaDocumento("", tipo, "Otra");
			 MultipartFile pdf = file.get(0);
			 Funcion.copyFile(numGuia + ".pdf", ruta, pdf.getInputStream());
			 
			 String ruta2 = funcion.obtenerRutaCompletaDocumento("", "guias", "");
			 MultipartFile pdf2 = file.get(0);
			 Funcion.copyFile(numGuia + ".pdf", ruta2, pdf2.getInputStream());
			 
			 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);		
		} catch (Exception e) {
			e.printStackTrace();
		}		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	@PostMapping(value = "/trEnvioXCliente/clientes")
	public ResponseEntity<StatusMessage> obtenerClientes() {
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			
			mensaje.setCurrent(trEnvioService.obtenerCliente());
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping(value = "/trEnvioXCliente/obtenerPackingPorCliente")
	public ResponseEntity<StatusMessage> obtenerPackingPorCliente(@RequestBody Integer idCliente){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			mensaje.setCurrent(trEnvioService.packingListPorCliente(idCliente));
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping(value = "/trEnvioXCliente/obtenerTotObjetivos")
	public ResponseEntity<StatusMessage> obtenerTotObjetivos(){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			mensaje.setCurrent(trEnvioService.obtenerTotalesObjetivos());
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	@PostMapping(value = "/trEnvioXCliente/tiempoPagoXCliente")
	public ResponseEntity<StatusMessage> obtenerTiempoPagoXCliente(){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("OK");
			mensaje.setCurrent(trEnvioService.obtenerTiempoXCliente());
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	@PostMapping("/trEnvioXCliente/ObtenerEstadisticaUsuarioEnvioPL")
	public ResponseEntity<StatusMessage> EstadisticaUsuario(@RequestBody Integer idUsuario){
		StatusMessage mensaje = new StatusMessage();
		try {
			Map<String, List<TrEnvio>> mapReturn = new HashMap<String, List<TrEnvio>>();
			 mensaje.setStatus(HttpStatus.OK.value());
			 mensaje.setMessage("ok");	
			 mapReturn  = trEnvioService.estadisticasPagoCliente(idUsuario);
			 mensaje.setCurrent(mapReturn);
			 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);			
		} catch (Exception e) {
			// TODO: handle exception
		}		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
}
