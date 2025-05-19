package com.proquifa.net.resource.comun;

import java.util.LinkedList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.proquifa.net.modelo.comun.StatusMessage;
import com.proquifa.net.modelo.comun.util.Funcion;

@RestController
@CrossOrigin
public class ComunResource {

	
	@RequestMapping(value = "/comun/subirArchivo/{tipo}/{carpeta}/{nombre}", method = RequestMethod.POST)
	public ResponseEntity<StatusMessage> subirArchivos(@RequestParam LinkedList<MultipartFile> file, @PathVariable String tipo, 
			@PathVariable String carpeta, @PathVariable String nombre) {
		StatusMessage mensaje = new StatusMessage();
		try {
			 mensaje.setStatus(HttpStatus.OK.value());
			 mensaje.setMessage("ok");
			 
			 Funcion funcion = new Funcion();
			 String ruta = funcion.obtenerRutaCompletaDocumento("", tipo, carpeta);
			 MultipartFile pdf = file.get(0);
			 Funcion.copyFile(nombre, ruta, pdf.getInputStream());
			 
			 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);		
		} catch (Exception e) {
			e.printStackTrace();
		}		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
}
