package com.proquifa.net.resource.consultas;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.proquifa.net.modelo.cobrosypagos.Cobros;
import com.proquifa.net.modelo.comun.StatusMessage;
import com.proquifa.net.modelo.comun.TiempoProceso;
import com.proquifa.net.modelo.consultas.comun.ParametrosBusquedaCobros;
import com.proquifa.net.modelo.consultas.comun.ResumenConsulta;
import com.proquifa.net.negocio.consultas.ConsultaCobrosService;

@RestController
@CrossOrigin
public class ConsultaCobrosResource {
	
	final Logger log = LoggerFactory.getLogger(ConsultaCobrosResource.class);
	
	@Autowired
	ConsultaCobrosService consultaCobroService;
	
	
	@PostMapping("/consultaObtenerCobros")
	public ResponseEntity<StatusMessage> consultaAvanzadaFacturacion(@RequestBody ParametrosBusquedaCobros parametro){
		log.info("on consultaObtenerCobros");
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			
			log.info("Fecha inicio:");
			log.info("",parametro.getFechaInicio());
			log.info("Fecha fin:");
			log.info("",parametro.getFechaFin());
			log.info("id_Cliente:");
			log.info("",parametro.getBusquedaCR());
			log.info("Medio Pago:");
			log.info(parametro.getEstado());
			log.info("fPor:");
			log.info(parametro.getFpor());
			log.info("Estado:");
			log.info(parametro.getEstado());
			log.info("cPago:");
			log.info(parametro.getCpago());
			log.info("busquedaCR");
			log.info("",parametro.getBusquedaCR());
			log.info("Factura:");
			log.info(parametro.getFactura());
			log.info("UUID:");
			log.info(parametro.getUuid());
			log.info("DRC:");
			log.info("",parametro.getDrc());
			log.info("Id Usuario Logueado:");
			log.info("",parametro.getIdUsuarioLogueado());
			log.info("Cobrador:");
			log.info("",parametro.getCobrador());
			log.info("Cuenta:");
			log.info(parametro.getCuenta());
			log.info("Banco:");
			log.info(parametro.getBanco());
			
			List<Cobros> listaCobros = consultaCobroService.obtenerCobros(parametro);
			mensaje.setCurrent(listaCobros);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/obtenerLineaTiempoResumen")
	public ResponseEntity<StatusMessage> obtenerLineaTiempoResumen(@RequestBody ParametrosBusquedaCobros parametro){
		log.info("on obtenerLineaTiempoResumen");
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			
			log.info("Factura:");
			log.info(parametro.getFactura());
			log.info("Cpedido:");
			log.info(parametro.getCpedido());
			List<TiempoProceso> tiempo = consultaCobroService.obtenerLineaTiempoResumen(parametro.getFactura(), parametro.getCpedido());
			mensaje.setCurrent(tiempo);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	@PostMapping("/obtenerInspectorEntrega")
	public ResponseEntity<StatusMessage> obtenerInspectorEntrega(@RequestBody ParametrosBusquedaCobros parametro){
		log.info("on obtenerInspectorEntrega");
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			
			log.info("Factura:");
			log.info(parametro.getFactura());
			log.info("Cpedido:");
			log.info(parametro.getCpedido());
			List<TiempoProceso> tiempo = consultaCobroService.obtenerInspectorEntrega(parametro.getFactura(), parametro.getCpedido());
			mensaje.setCurrent(tiempo);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/obtenerInspectorRevision")
	public ResponseEntity<StatusMessage> obtenerInspectorRevision(@RequestBody ParametrosBusquedaCobros parametro){
		log.info("on obtenerInspectorRevision");
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			
			log.info("Factura:");
			log.info(parametro.getFactura());
			log.info("Cpedido:");
			log.info(parametro.getCpedido());
			List<TiempoProceso> tiempo = consultaCobroService.obtenerInspectorRevision(parametro.getFactura(), parametro.getCpedido());
			mensaje.setCurrent(tiempo);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	@PostMapping("/obtenerInspectorCobro")
	public ResponseEntity<StatusMessage> obtenerInspectorCobro(@RequestBody ParametrosBusquedaCobros parametro){
		log.info("on obtenerInspectorCobro");
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			
			log.info("Factura:");
			log.info(parametro.getFactura());
			log.info("Cpedido:");
			log.info(parametro.getCpedido());
			log.info("SC:");
			log.info(parametro.getSC());
			List<TiempoProceso> tiempo = consultaCobroService.obtenerInspectorCobro(parametro.getFactura(), parametro.getCpedido(), parametro.getSC());
			mensaje.setCurrent(tiempo);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	
	@PostMapping("/obtenerComparativasDPeriodos")
	public ResponseEntity<StatusMessage>  obtenerComparativasDPeriodos(@RequestBody ParametrosBusquedaCobros parametro){
		log.info("on  obtenerComparativasDPeriodos");
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			
			log.info("Fecha inicio:");
			log.info("",parametro.getFechaInicio());
			log.info("Fecha fin:");
			log.info("",parametro.getFechaFin());
			log.info("id_Cliente:");
			log.info("",parametro.getBusquedaCR());
			log.info("Medio Pago:");
			log.info(parametro.getEstado());
			log.info("fPor:");
			log.info(parametro.getFpor());
			log.info("Estado:");
			log.info(parametro.getEstado());
			log.info("cPago:");
			log.info(parametro.getCpago());
			log.info("busquedaCR");
			log.info("",parametro.getBusquedaCR());
			log.info("Factura:");
			log.info(parametro.getFactura());
			log.info("UUID:");
			log.info(parametro.getUuid());
			log.info("DRC:");
			log.info("",parametro.getDrc());
			log.info("Id Usuario Logueado:");
			log.info("",parametro.getIdUsuarioLogueado());
			log.info("Cobrador:");
			log.info("",parametro.getCobrador());
			log.info("Cuenta:");
			log.info(parametro.getCuenta());
			log.info("Banco:");
			log.info(parametro.getBanco());
			//FechaInicio, FechaFin, idCliente,
			// medioPago, fpor, cpago, drc, estado, individual
			List<ResumenConsulta> comparativas = consultaCobroService.obtenerComparativasDPeriodos(parametro);
			mensaje.setCurrent(comparativas);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
}
