package com.proquifa.net.resource.UPS;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.pdf.codec.Base64;
import com.proquifa.net.modelo.comun.StatusMessage;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
@RestController
@CrossOrigin
public class UPSResource {
	
	@SuppressWarnings("unchecked")
	@PostMapping("/guardarArchivo")
	public ResponseEntity<StatusMessage> obtenerImagenUPS(@RequestBody Map<String, Object> map) throws ProquifaNetException {
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("OK");
			String nombreArchivo = map.get("nombre").toString();
			List<Integer> pendientes = null;
			if ( map.get("pendientes") != null)
				pendientes = (List<Integer>) map.get("pendientes");
			
			Funcion funcion = new Funcion();
			String dir = funcion.obtenerRutaCompletaDocumento("", "ups", "");

			if (pendientes != null) {
				for (Integer pendiente : pendientes) {
					Funcion.subirArchivo((byte[]) Base64.decode( map.get("data1").toString()), "Guia-" + pendiente + ".png", dir);
					Funcion.convertirPNGtoPDF(dir + "Guia-" + pendiente + ".png", dir, "Guia-" + pendiente + ".pdf");
					mensaje.setCurrent(pendiente + ".pdf");
					
					String dir2 = funcion.obtenerRutaCompletaDocumento("", "guias", "");
					Funcion.subirArchivo((byte[]) Base64.decode( map.get("data1").toString()), "Guia-" + pendiente + ".png", dir2);
					Funcion.convertirPNGtoPDF(dir2 + "Guia-" + pendiente + ".png", dir2, "Guia-" + pendiente + ".pdf");
					
				} 
			} else {
				Funcion.subirArchivo((byte[]) Base64.decode( map.get("data1").toString()), nombreArchivo + ".png", dir);
				Funcion.convertirPNGtoPDF(dir + nombreArchivo + ".png", dir, nombreArchivo + ".pdf");
				mensaje.setCurrent(nombreArchivo + ".pdf");
				
				String dir2 = funcion.obtenerRutaCompletaDocumento("", "guias", "");
				Funcion.subirArchivo((byte[]) Base64.decode( map.get("data1").toString()), nombreArchivo + ".png", dir2);
				Funcion.convertirPNGtoPDF(dir2 + nombreArchivo + ".png", dir2, nombreArchivo + ".pdf");
			}
			
			
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.FORBIDDEN);
	}

}
