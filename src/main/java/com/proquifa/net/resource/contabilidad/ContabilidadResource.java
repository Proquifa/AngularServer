package com.proquifa.net.resource.contabilidad;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.proquifa.net.modelo.comun.CatalogoItem;
import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.StatusMessage;
import com.proquifa.net.modelo.cuentaContable.CentroCosto;
import com.proquifa.net.modelo.cuentaContable.ContableCaracteristica;
import com.proquifa.net.modelo.cuentaContable.CuentaContable;
import com.proquifa.net.modelo.cuentaContable.Poliza;
import com.proquifa.net.negocio.contabilidad.ContabilidadService;

@RestController
@CrossOrigin
public class ContabilidadResource {

	@Autowired
	private ContabilidadService contabilidadService;
	
	@PostMapping("/obtenerCuentasContablesEmpresa")
	public ResponseEntity<StatusMessage> obtenerCuentasContablesEmpresa(@RequestBody Parametro parametros){
		StatusMessage mensaje = new StatusMessage();
		try {
			List<CuentaContable> lst = new ArrayList<CuentaContable>();
			Integer idEmpresa = parametros.getIdEmpresa();
			lst = contabilidadService.obtenerCuentasContablesEmpresa(idEmpresa);
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("Ok");
			mensaje.setCurrent(lst);
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}

	@PostMapping("/obtenerContablesCaracteristicas")
	public ResponseEntity<StatusMessage> obtenerContablesCaracteristicas(){
		StatusMessage mensaje = new StatusMessage();
		try {
			List<ContableCaracteristica> lst = new ArrayList<ContableCaracteristica>();
			lst = contabilidadService.obtenerContablesCaracteristicas();
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("Ok");
			mensaje.setCurrent(lst);
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}

	@PostMapping("/obtenerClientesCuentasContables")
	public ResponseEntity<StatusMessage> obtenerClientesCuentasContables(@RequestBody Parametro parametros){
		StatusMessage mensaje = new StatusMessage();
		try {
			List<CatalogoItem> lst = new ArrayList<CatalogoItem>();
			lst = contabilidadService.obtenerClientesCuentasContables(parametros.getValorAdicional());
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("Ok");
			mensaje.setCurrent(lst);
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}

	@PostMapping("/obtenerProveedoresCuentasContables")
	public ResponseEntity<StatusMessage> obtenerProveedoresCuentasContables(@RequestBody Parametro parametros){
		StatusMessage mensaje = new StatusMessage();
		try {
			List<CatalogoItem> lst = new ArrayList<CatalogoItem>();
			lst = contabilidadService.obtenerProveedoresCuentasContables(parametros.getValorAdicional());
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("Ok");
			mensaje.setCurrent(lst);
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}

	@PostMapping("/desactivarCuentaContable")
	public ResponseEntity<StatusMessage> desactivarCuentaContable(@RequestBody Parametro parametros){
		StatusMessage mensaje = new StatusMessage();
		try {
			Integer idCuenta = parametros.getIdCuenta();
			boolean response = contabilidadService.desactivarCuentaContable(idCuenta);
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("Ok");
			mensaje.setCurrent(response);
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}

	@PostMapping("/agregarCuentaContable")
	public ResponseEntity<StatusMessage> agregarCuentaContable(@RequestBody CuentaContable cuenta){
		StatusMessage mensaje = new StatusMessage();
		try {
			if (cuenta.getIdCuentaContable() == 0) {
				cuenta = contabilidadService.agregarCuentaContable(cuenta);
			} else {
				cuenta = contabilidadService.actualizarCuentaContable(cuenta);
			}
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("Ok");
			mensaje.setCurrent(cuenta);
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}

	@PostMapping("/agregarPoliza")
	public ResponseEntity<StatusMessage> agregarPoliza(@RequestBody Poliza poliza){
		StatusMessage mensaje = new StatusMessage();
		try {

			if(poliza.getIdPoliza() > 0) {
				poliza = contabilidadService.actualizarPoliza(poliza);
			} else {
				poliza = contabilidadService.agregarPoliza(poliza);
			}
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("Ok");
			mensaje.setCurrent(poliza);
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}

		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}

	@PostMapping("/obtenerPoliza")
	public ResponseEntity<StatusMessage> obtenerPoliza(@RequestBody Parametro parametro){
		StatusMessage mensaje = new StatusMessage();
		try {
			Integer idPoliza = parametro.getIdPoliza();
			Poliza poliza = contabilidadService.obtenerPoliza(idPoliza);
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("Ok");
			mensaje.setCurrent(poliza);
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/obtenerLstPolizasEmpresa")
	public ResponseEntity<StatusMessage> obtenerLstPolizasEmpresa(@RequestBody Parametro parametros){
		StatusMessage mensaje = new StatusMessage();
		try {
			Integer idEmpresa = parametros.getIdEmpresa();
			List<Poliza> lst = contabilidadService.obtenerLstPolizasEmpresa(idEmpresa);
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("Ok");
			mensaje.setCurrent(lst);
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}

	@PostMapping("/obtenerLstCentroCostos")
	public ResponseEntity<StatusMessage> obtenerLstCentroCostos(){
		StatusMessage mensaje = new StatusMessage();
		try {
			List<CentroCosto> lst = contabilidadService.obtenerCentroCostos();
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("Ok");
			mensaje.setCurrent(lst);
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}

	@PostMapping("/generarPDFContabilidad")
	public ResponseEntity<StatusMessage> generarPDFContabilidad(@RequestBody Parametro parametro){
		StatusMessage mensaje = new StatusMessage();
		try {
			byte[] pdf = contabilidadService.generarPDFContabilidad(parametro);
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("Ok");
			mensaje.setCurrent(pdf);
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
}
