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

import com.proquifa.net.modelo.cobrosypagos.facturista.Factura;
import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.StatusMessage;
import com.proquifa.net.modelo.comun.TiempoProceso;
import com.proquifa.net.modelo.consultas.comun.ResumenConsulta;
import com.proquifa.net.modelo.despachos.HistorialPNE;
import com.proquifa.net.negocio.consultas.ConsultaEntregasService;
@RestController
@CrossOrigin


public class ConsultaEntregasResource {
	@Autowired
	ConsultaEntregasService consultaEntregasService;
	
	final Logger log = LoggerFactory.getLogger(ConsultaEntregasResource.class);
	
	@PostMapping("/obtenerEntregas")
	public ResponseEntity<StatusMessage> obtenerEntregas(@RequestBody Parametro parametro){
		log.info("on obtenerEntregas");
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("IdCliente:");
			log.info("",parametro.getIdCliente());
			log.info("Estado:");
			log.info(parametro.getEstado());
			log.info("Mensajero:");
			log.info(parametro.getMensajero());
			log.info("Ruta:");
			log.info(parametro.getRuta());
			log.info("Conforme:");
			log.info(parametro.getConforme());
			log.info("FechaInicio:");
			log.info("",parametro.getFechaInicio());
			log.info("FechaFin:");
			log.info("",parametro.getFechaFin());
			log.info("Factura:");
			log.info(parametro.getFacturaS());
			log.info("Cpedido:");
			log.info(parametro.getcPedido());
			List<Factura> listaEntregas = consultaEntregasService.obtenerEntregas(parametro.getIdCliente(),parametro.getEstado(), parametro.getMensajero(), parametro.getRuta(), parametro.getConforme(), parametro.getFechaInicio(), parametro.getFechaFin(), parametro.getFacturaS(), parametro.getcPedido());
			mensaje.setCurrent(listaEntregas);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	
	
	@PostMapping("/obtenerTiempoDeProceso")
	public ResponseEntity<StatusMessage> obtenerTiempoDeProceso(@RequestBody Parametro parametro){
		log.info("on obtenerTiempoDeProceso");
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("IdPD:");
			log.info(parametro.getIdPD());
			List<TiempoProceso> listaTiempoProceso = consultaEntregasService.obtenerTiempoDeProceso(parametro.getIdPD());
			mensaje.setCurrent(listaTiempoProceso);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	@PostMapping("/obtenerHistorialProductoNoEntregado")
	public ResponseEntity<StatusMessage> obtenerHistorialProductoNoEntregado(@RequestBody Parametro parametro){
		log.info("on obtenerHistorialProductoNoEntregado");
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("IdPD:");
			log.info(parametro.getIdPD());
			List<HistorialPNE> listaProducto = consultaEntregasService.obtenerHistorialProductoNoEntregado(parametro.getIdPD());
			mensaje.setCurrent(listaProducto);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/obtenerComparativasDPeriodos1")
	public ResponseEntity<StatusMessage> obtenerComparativasDPeriodos(@RequestBody Parametro parametro){
		log.info("on obtenerComparativasDPeriodos");
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("IdCliente:");
			log.info("",parametro.getIdCliente());
			

			log.info("Estado:");
			log.info(parametro.getEstado());
			log.info("Mensajero:");
			log.info(parametro.getMensajero());
			log.info("Ruta:");
			log.info(parametro.getRuta());
			log.info("Conforme:");
			log.info(parametro.getConforme());
			log.info("FechaInicio:");
			log.info("",parametro.getFechaInicio());
			log.info("FechaFin:");
			log.info("",parametro.getFechaFin());
			log.info("Factura:");
			log.info(parametro.getFacturaS());
			log.info("Individual:");
			log.info("",parametro.getIndividual());
			List<ResumenConsulta> listaComparativas = consultaEntregasService.obtenerComparativasDPeriodos(parametro.getIdCliente(), parametro.getEstado(), parametro.getMensajero(), parametro.getRuta(),parametro.getConforme(), parametro.getFacturaS(), parametro.getcPedido(), parametro.getFechaInicio(), parametro.getFechaFin(), parametro.getIndividual());
			mensaje.setCurrent(listaComparativas);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/obtenerGraficosEntregas")
	public ResponseEntity<StatusMessage> obtenerGraficosEntregas(@RequestBody Parametro parametro){
		log.info("on obtenerGraficosEntregas");
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("IdCliente:");
			log.info("",parametro.getIdCliente());
			log.info("Estado:");
			log.info(parametro.getEstado());
			log.info("Mensajero:");
			log.info(parametro.getMensajero());
			log.info("Ruta:");
			log.info(parametro.getRuta());
			log.info("Conforme:");
			log.info(parametro.getConforme());
			log.info("FechaInicio:");
			log.info("",parametro.getFechaInicio());
			log.info("FechaFin:");
			log.info("",parametro.getFechaFin());
			log.info("Factura:");
			log.info(parametro.getFacturaS());
			log.info("Cpedido:");
			log.info(parametro.getcPedido());
			List<Factura> listaGraficos = consultaEntregasService.obtenerGraficosEntregas(parametro.getIdCliente(), parametro.getEstado(), parametro.getMensajero(), parametro.getRuta(), parametro.getConforme(), parametro.getFechaInicio(), parametro.getFechaFin(), parametro.getFacturaS(), parametro.getcPedido());
			mensaje.setCurrent(listaGraficos);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/obtenerEntregasSinAviso")
	public ResponseEntity<StatusMessage> obtenerEntregasSinAviso(@RequestBody Parametro parametro){
		log.info("on obtenerEntregasSinAviso");
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("IdCliente:");
			log.info("",parametro.getIdCliente());
			log.info("Estado:");
			log.info(parametro.getEstado());
			log.info("Mensajero:");
			log.info(parametro.getMensajero());
			log.info("Ruta:");
			log.info(parametro.getRuta());
			log.info("Conforme:");
			log.info(parametro.getConforme());
			log.info("FechaInicio:");
			log.info("",parametro.getFechaInicio());
			log.info("FechaFin:");
			log.info("",parametro.getFechaFin());
			log.info("Factura:");
			log.info(parametro.getFacturaS());
			log.info("Cpedido:");
			log.info(parametro.getcPedido());
			List<Factura> listaEntregasS = consultaEntregasService.obtenerEntregasSinAviso(parametro.getIdCliente(), parametro.getEstado(), parametro.getMensajero(), parametro.getRuta(), parametro.getConforme(), parametro.getFechaInicio(), parametro.getFechaFin(), parametro.getFacturaS(), parametro.getcPedido());
			mensaje.setCurrent(listaEntregasS);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	@PostMapping("/obtenerEntregasCAviso")
	public ResponseEntity<StatusMessage> obtenerEntregasCAviso(@RequestBody Parametro parametro){
		log.info("on obtenerEntregasCAviso");
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("IdCliente:");
			log.info("",parametro.getIdCliente());
			log.info("Estado:");
			log.info(parametro.getEstado());
			log.info("Mensajero:");
			log.info(parametro.getMensajero());
			log.info("Ruta:");
			log.info(parametro.getRuta());
			log.info("Conforme:");
			log.info(parametro.getConforme());
			log.info("FechaInicio:");
			log.info("",parametro.getFechaInicio());
			log.info("FechaFin:");
			log.info("",parametro.getFechaFin());
			log.info("Factura:");
			log.info(parametro.getFacturaS());
			log.info("Cpedido:");
			log.info(parametro.getcPedido());
			List<Factura> listaEntrejaC = consultaEntregasService.obtenerEntregasCAviso(parametro.getIdCliente(), parametro.getEstado(), parametro.getMensajero(), parametro.getRuta(), parametro.getConforme(), parametro.getFechaInicio(), parametro.getFechaFin(), parametro.getFacturaS(), parametro.getcPedido());
			mensaje.setCurrent(listaEntrejaC);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
}
