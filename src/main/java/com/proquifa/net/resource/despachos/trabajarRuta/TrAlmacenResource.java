package com.proquifa.net.resource.despachos.trabajarRuta;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.StatusMessage;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.despachos.trabajarRuta.TrContactoCliente;
import com.proquifa.net.modelo.despachos.trabajarRuta.TrPackingList;
import com.proquifa.net.negocio.despachos.trabajarRuta.TrAlmacenService;

@RestController
@CrossOrigin
public class TrAlmacenResource {
	@Autowired
	TrAlmacenService trAlmacenService;

	@PostMapping("/trAlmacen/obtenerObjetivos")
	public ResponseEntity<StatusMessage> obtenerObjetivos(){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			
			mensaje.setCurrent(trAlmacenService.getObjetivos());
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/trAlmacen/obtenerMontosTab")
	public ResponseEntity<StatusMessage> obtenerMontosTab(){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			
			mensaje.setCurrent(trAlmacenService.getMontosTab());
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/trAlmacen/obtenerInfoGraficas")
	public ResponseEntity<StatusMessage> obtenerInfoGraficas(){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			
			mensaje.setCurrent(trAlmacenService.getGraficas());
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/trAlmacen/infoClientes")
	public ResponseEntity<StatusMessage> obtenerInfoClientes(){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			
			mensaje.setCurrent(trAlmacenService.getClientes());
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/trAlmacen/obtenerPackingListClient")
	public ResponseEntity<StatusMessage> obtenerPackingListClient(@RequestBody int idUsuario){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			
			mensaje.setCurrent(trAlmacenService.getPackingListClient(idUsuario));
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/trAlmacen/finalizarEjecutarRutaAlmacen")
	public ResponseEntity<StatusMessage> finalizarEjecutarRutaAlmacen(@RequestBody TrContactoCliente trContactoCliente){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			
			mensaje.setCurrent(trAlmacenService.finalizarEjecutarRutaAlmacen(trContactoCliente));
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/ObtenerEstadisticaUsuarioPL")
	public ResponseEntity<StatusMessage> EstadisticaUsuarioEmbalar(@RequestBody Parametro parametro){
		StatusMessage mensaje = new StatusMessage();
		try {
			Map<String, List<TrPackingList>> mapReturn = new HashMap<String, List<TrPackingList>>();
			 mensaje.setStatus(HttpStatus.OK.value());
			 mensaje.setMessage("ok");	
			 mapReturn  = trAlmacenService.obtenerEstadisticasUsuarioTR(parametro);
			 mensaje.setCurrent(mapReturn);
			 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);			
		} catch (Exception e) {
			// TODO: handle exception
		}		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value = "/trAlmacen/uploadFile/{numGuia}", method = RequestMethod.POST)
	public ResponseEntity<StatusMessage> subirArchivos(@RequestParam LinkedList<MultipartFile> file, @PathVariable String numGuia) {
		StatusMessage mensaje = new StatusMessage();
		try {
			 mensaje.setStatus(HttpStatus.OK.value());
			 mensaje.setMessage("ok");
			 
			 Funcion funcion = new Funcion();
			 String ruta = funcion.obtenerRutaCompletaDocumento("", "almacen", "");
//			 
			 MultipartFile pdf = file.get(0);
			 Funcion.copyFile(numGuia+".pdf", ruta, pdf.getInputStream());
			 
			 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
}
