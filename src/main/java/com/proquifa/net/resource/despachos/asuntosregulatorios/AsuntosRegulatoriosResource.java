/**
 * 
 */
package com.proquifa.net.resource.despachos.asuntosregulatorios;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
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

import com.proquifa.net.modelo.comun.StatusMessage;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.despachos.asuntosregulatorios.GestionarPap;
import com.proquifa.net.modelo.despachos.asuntosregulatorios.PermisoAdquisicion;
import com.proquifa.net.modelo.despachos.asuntosregulatorios.TotalesPAP;
import com.proquifa.net.negocio.despachos.asuntosregulartorios.AsuntosRegulatoriosService;

/**
 * @author ymendez
 *
 */

@RestController
@CrossOrigin
public class AsuntosRegulatoriosResource {

	@Autowired
	AsuntosRegulatoriosService asuntosRegulatoriosService;

	@PostMapping("/pap/getPendietesPAP")
	public ResponseEntity<StatusMessage> obtenerPendientesPap(@RequestBody Map<String, Object> data){
		StatusMessage mensaje = new StatusMessage();
		try {
			List<GestionarPap> listGestion = asuntosRegulatoriosService.obtenerPendientesPap((Integer) data.get("idUsuario"));
			Map<String, List<TotalesPAP>> listTotales = asuntosRegulatoriosService.obtenerDatosGrafica((Integer) data.get("idUsuario"));
			Map<String, Object> mapReturn = new HashMap<String, Object>();
			mapReturn.put("lista", listGestion);
			mapReturn.put("graficas", listTotales);
			mensaje.setCurrent(mapReturn);
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}

	
	@PostMapping("/pap/cerrarPendietesPAP")
	public ResponseEntity<StatusMessage> cerrarPendientesPap(@RequestBody PermisoAdquisicion data){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setCurrent(asuntosRegulatoriosService.saveGestionarPAP(data));
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	@RequestMapping(value = "/pap/uploadFile/{idProducto}/{tipo}/{fPor}", method = RequestMethod.POST)
	public ResponseEntity<StatusMessage> subirArchivos(@RequestParam LinkedList<MultipartFile> file, @PathVariable Integer idProducto, @PathVariable String tipo, @PathVariable String fPor) {
		StatusMessage mensaje = new StatusMessage();
		try {
			 mensaje.setStatus(HttpStatus.OK.value());
			 mensaje.setMessage("ok");
			 String idP = idProducto.toString() + ".pdf";
			 Funcion funcion = new Funcion();
			 String ruta = funcion.obtenerRutaCompletaDocumento("", tipo, fPor);
//			 
			 MultipartFile pdf = file.get(0);
			 Funcion.copyFile(idP, ruta, pdf.getInputStream());
			 
			 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
}
