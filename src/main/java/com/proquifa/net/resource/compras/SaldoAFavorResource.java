/**
 * 
 */
package com.proquifa.net.resource.compras;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.proquifa.net.modelo.compras.SaldoAFavor;
import com.proquifa.net.modelo.comun.StatusMessage;
import com.proquifa.net.negocio.compras.SaldoAFavorService;

/**
 * @author ymendez
 *
 */
@RestController
@CrossOrigin
public class SaldoAFavorResource {

	@Autowired
	SaldoAFavorService saldoAFavorService;
	
	@PostMapping("/generarSaldo")
	public ResponseEntity<StatusMessage> generarSaldo(@RequestBody SaldoAFavor saldo){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			mensaje.setCurrent(saldoAFavorService.generarSaldo(saldo));
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}

	
	@PostMapping("/comun/obtenerProveedores")
	public ResponseEntity<StatusMessage> obtenerProveedores(@RequestBody Map<String, Integer> data){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			
			mensaje.setCurrent(saldoAFavorService.obtenerProveedores(data.get("habilitado")));
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/comun/obtenerEmpresas")
	public ResponseEntity<StatusMessage> obtenerEmpresas(@RequestBody Map<String, Integer> data){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			
			mensaje.setCurrent(saldoAFavorService.obtenerEmpresas(data.get("compra")));
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/saldoFavor/obtenerEmpresaProveedor")
	public ResponseEntity<StatusMessage> obtenerEmpresaProveedor(@RequestBody Map<String, Integer> data){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			Map<String, Object> datos = new HashMap<String, Object>();
			datos.put("proveedores", saldoAFavorService.obtenerProveedores(data.get("habilitado")));
			datos.put("empresas", saldoAFavorService.obtenerEmpresas(data.get("compra")));
			mensaje.setCurrent(datos);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/saldoFavor/obtenerListado")
	public ResponseEntity<StatusMessage> obtenerListado(@RequestBody SaldoAFavor saldo){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			
			mensaje.setCurrent(saldoAFavorService.obtenerListaSaldo(saldo.getTipo()));
			
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}

	
	@PostMapping("/saldoFavor/habilitarSaldo")
	public ResponseEntity<StatusMessage> habilitarSaldo(@RequestBody SaldoAFavor saldo){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			
			mensaje.setCurrent(saldoAFavorService.habilitarSaldo(saldo));
			
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
}
