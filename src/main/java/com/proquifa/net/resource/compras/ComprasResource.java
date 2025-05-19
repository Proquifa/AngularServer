package com.proquifa.net.resource.compras;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.proquifa.net.modelo.compras.Compra;
import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.StatusMessage;
import com.proquifa.net.negocio.compras.CompraService;
import com.proquifa.net.negocio.consultas.ConsultaCobrosService;

@RestController
@CrossOrigin
public class ComprasResource {
	
	final Logger log = LoggerFactory.getLogger(ComprasResource.class);
	
	@Autowired
	CompraService comprasService; 
	
	@Autowired
	ConsultaCobrosService consultaCobroService;
	
	@PostMapping("/ObtenerReporteCompra")
	public ResponseEntity<StatusMessage> obtenerReporteCompra(@RequestBody Parametro parametro){
		log.info("on ObtenerReportes");
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			List<Compra> listaCompra = comprasService.obtenerReporteCompra(parametro.getFechaInicio(), parametro.getFechaFin(), parametro.getproveedor(),parametro.getestadoInt(), parametro.getcoloco()
														, parametro.getordenCompra());
						mensaje.setCurrent(listaCompra);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/MonitorearOC/validarFEE/{idPCompra}")
	public ResponseEntity<StatusMessage> validarFEE(@PathVariable String idPCompra){
		log.info("on ObtenerReportes");
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			mensaje.setCurrent(comprasService.validarHorarioCliente(idPCompra));
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/MonitorearOC/asuetos")
	public ResponseEntity<StatusMessage> asuetos(){
		log.info("on ObtenerReportes");
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			mensaje.setCurrent(comprasService.obtenerAsuetos());
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	@PostMapping("/MonitorearOC/obtenerDiaFinMes/{cliente}")
	public ResponseEntity<StatusMessage> obtenerDiaFinMes(@PathVariable String cliente){
		log.info("on ObtenerReportes");
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			mensaje.setCurrent(comprasService.obtenerDiaFinMes(cliente));
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@GetMapping("/tramitarOC/{folioOC}")
	public ResponseEntity<StatusMessage> generarDocumentoOC(@PathVariable String folioOC) {
		StatusMessage mensaje = new StatusMessage();
		try {

			if (comprasService.generarDocumentoOC(folioOC)) {
				mensaje.setStatus(HttpStatus.OK.value());
				mensaje.setMessage("ok");
				mensaje.setCurrent("PDF GENERADO");
				return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
			} else {
				mensaje.setStatus(HttpStatus.BAD_REQUEST.value());
				mensaje.setMessage("ERROR");
				return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			mensaje.setStatus(HttpStatus.FORBIDDEN.value());
			mensaje.setMessage("Error");
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.FORBIDDEN);
		}

	}
}