/**
 * 
 */
package com.proquifa.net.resource.compras;

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
import com.proquifa.net.negocio.compras.RechazoService;

/**
 * @author ymendez
 *
 */
@RestController
@CrossOrigin
public class GestorComprasResource {

	@Autowired
	RechazoService rechazoService;
	
	@PostMapping("/gestorCompras/documentacionFaltante")
	public ResponseEntity<StatusMessage> obtenerDocumentacionFaltante(@RequestBody Map<String, Object> data){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("OK");
			String responsable = (String) data.get("responsable");
			Map<String, Object> map = rechazoService.obtenerGraficaDocumentacionFaltante(responsable);
			map.put("lista", rechazoService.obtenerDocumentacionFaltante(responsable));
			mensaje.setCurrent(map);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/gestorCompras/contactoProveedor")
	public ResponseEntity<StatusMessage> obtenerProveedoresPorProveedor(@RequestBody Integer idProveedor){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("OK");
			mensaje.setCurrent(rechazoService.obtenerProveedoresPorProveedor(idProveedor));
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/gestorCompras/obtenerProductosFaltantesPorProveedor")
	public ResponseEntity<StatusMessage> obtenerProductosFaltantesPorProveedo(@RequestBody Map<String, Object> data){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("OK");
			mensaje.setCurrent(rechazoService.documentoFaltantePorProveedor(Integer.parseInt(data.get("idProveedor").toString()), data.get("responsable").toString()));
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	@PutMapping("/gestorCompras/finalizarDocumentacionFaltante")
	public ResponseEntity<StatusMessage> finalizarDocumentacionFaltante(@RequestBody Map<String, Object> data){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("OK");
			mensaje.setCurrent(rechazoService.actualizarEstadoAInspeccion(data.get("codigo").toString(), data.get("lote").toString(), data.get("hoja").toString()));
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Aqui esta el error");
			System.out.println(e);
			e.printStackTrace();
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/gestorCompras/obtenerTotales")
	public ResponseEntity<StatusMessage> obtenerTotales(@RequestBody String idUsuario){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("OK");
			mensaje.setCurrent(rechazoService.obtenerTotalesSeccion(idUsuario));
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
}
