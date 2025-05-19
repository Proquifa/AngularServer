package com.proquifa.net.resource.ventas.visitas;

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

import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.StatusMessage;
import com.proquifa.net.modelo.ventas.Sprint;
import com.proquifa.net.modelo.ventas.visitas.ReportarVisita;
import com.proquifa.net.modelo.ventas.visitas.VisitaCliente;
import com.proquifa.net.negocio.ventas.visitas.VisitaClienteService;
@RestController
@CrossOrigin
public class VisitaClienteResource {

	@Autowired
	VisitaClienteService visitaClienteService;
	
	//Obtiene las visitas paraEjecucion asignadas a un usuario
	
	final Logger log = LoggerFactory.getLogger(VisitaClienteResource.class);
	
	@PostMapping("/obtenerVisitasParaEjecucion")
	public ResponseEntity<StatusMessage> obtenerVisitasParaEjecucion(@RequestBody Parametro parametro){
		log.info("on obtenerVisitasParaEjecucion");
		StatusMessage mensaje = new StatusMessage();
		
		log.info("Usuario:");
		log.info("",parametro.getIdUsuario());
		
		log.info("Folio:");
		log.info(parametro.getFolio());
		log.info("porFolio:");
		log.info("", parametro.getPorFolio());
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			List<VisitaCliente> listaVisitaCliente= visitaClienteService.obtenerVisitasParaEjecucion(parametro.getIdUsuario());
						mensaje.setCurrent(listaVisitaCliente);                                            
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			log.info(e.toString());
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
//Obitiene el lsitado de visitas asignadas por EV
	//List<VisitaCliente> obtenerVisitasAsignadasPorEV(Long usuario)
	@PostMapping("/obtenerVisitasAsignadasPorEV")
	public ResponseEntity<StatusMessage> obtenerVisitasAsignadasPorEV(@RequestBody Parametro parametro){
		log.info("on obtenerVisitasAsignadasPorEV");
		StatusMessage mensaje = new StatusMessage();
		
		log.info("Usuario:");
		log.info("",parametro.getIdUsuario());
		
		log.info("Folio:");
		log.info(parametro.getFolio());
		log.info("porFolio:");
		log.info("", parametro.getPorFolio());
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			List<VisitaCliente> listaVisitaCliente= visitaClienteService.obtenerVisitasAsignadasPorEV(parametro.getIdUsuario());
						mensaje.setCurrent(listaVisitaCliente);                                            
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			log.info(e.toString());
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	//	public String registrarFechaCheckIn(VisitaCliente visita, Long usuario)
	@PostMapping("/registrarFechaCheckIn")
	public ResponseEntity<StatusMessage> registrarFechaCheckIn(@RequestBody Parametro parametro){
		log.info("on RegistrarFechaCheckin");
		StatusMessage mensaje = new StatusMessage();
		
		log.info("Usuario:");
		log.info("",parametro.getIdUsuario());
		
		log.info("Folio:");
		log.info(parametro.getFolio());
		log.info("porFolio:");
		log.info("", parametro.getPorFolio());
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			 String resultado= visitaClienteService.registrarFechaCheckIn(parametro.getVisitaCliente(), parametro.getIdUsuario());
						mensaje.setCurrent(resultado);                                            
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			log.info(e.toString());
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	
	//	int registrarRealizacionVisita(VisitaCliente visita) throws ProquifaNetException;
	
	@PostMapping("/registrarRealizacionVisita")
	public ResponseEntity<StatusMessage> registrarRealizacionVisita(@RequestBody Parametro parametro){
		log.info("on registrarRealizacionVisita");
		StatusMessage mensaje = new StatusMessage();
		
		log.info("Usuario:");
		log.info("",parametro.getIdUsuario());
		
		log.info("Folio:");
		log.info(parametro.getFolio());
		log.info("porFolio:");
		log.info("", parametro.getPorFolio());
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			  int resultado = visitaClienteService.registrarRealizacionVisita(parametro.getVisitaCliente());
						mensaje.setCurrent(resultado);                                            
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			log.info(e.toString());
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
//	public boolean actualizarLatitudyLongitud(Direccion direccion) throws ProquifaNetException {
	
	@PostMapping("/actualizarLatitudyLongitud")
	public ResponseEntity<StatusMessage> actualizarLatitudyLongitud(@RequestBody Parametro parametro){
		log.info("on actualizarLatitudyLongitud");
		StatusMessage mensaje = new StatusMessage();
		
		log.info("Usuario:");
		log.info("",parametro.getIdUsuario());
		
		log.info("Folio:");
		log.info(parametro.getFolio());
		log.info("porFolio:");
		log.info("", parametro.getPorFolio());
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			  boolean resultado = visitaClienteService.actualizarLatitudyLongitud(parametro.getDireccion());
						mensaje.setCurrent(resultado);                                            
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			log.info(e.toString());
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	//	public Sprint obtenerSprintEnCurso() throws ProquifaNetException {
	
	
	@PostMapping("/obtenerSprintEnCurso")
	public ResponseEntity<StatusMessage> obtenerSprintEnCurso(@RequestBody Parametro parametro){
		log.info("on obtenerSprintEnCurso");
		StatusMessage mensaje = new StatusMessage();
		
		log.info("Usuario:");
		log.info("",parametro.getIdUsuario());
		
		log.info("Folio:");
		log.info(parametro.getFolio());
		log.info("porFolio:");
		log.info("", parametro.getPorFolio());
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			  Sprint resultado = visitaClienteService.obtenerSprintEnCurso();
						mensaje.setCurrent(resultado);                                            
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			log.info(e.toString());
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/pendientesVisitaCliente")
	public ResponseEntity<StatusMessage> pendientesVisitaCliente(@RequestBody Parametro parametro){
		log.info("on buscarCotizacionPorFolio");
		StatusMessage mensaje = new StatusMessage();
		
		log.info("IdUsuario");
		log.info("",parametro.getIdUsuario());
		
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			List<String> listaCompra = visitaClienteService.pendientesVisitaCliente(parametro.getIdUsuario());
						mensaje.setCurrent(listaCompra);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	@PostMapping("/obtenerTodasVisitasPorSprint")
	public ResponseEntity<StatusMessage> obtenerTodasVisitasPorSprint(@RequestBody Parametro parametro){
		log.info("on buscarCotizacionPorFolio");
		StatusMessage mensaje = new StatusMessage();
		
		log.info("IdUsuario");
		log.info("",parametro.getIdUsuario());
		
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			List<VisitaCliente> listaCompra = visitaClienteService.obtenerTodasVisitasPorSprint(parametro.getIdUsuario());
						mensaje.setCurrent(listaCompra);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/obtenerReportarVisita")
	public ResponseEntity<StatusMessage> obtenerReportarVisita(@RequestBody Parametro parametro){
		log.info("on buscarCotizacionPorFolio");
		StatusMessage mensaje = new StatusMessage();
		
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			ReportarVisita listaCompra = visitaClienteService.obtenerReportarVisita(parametro.getIdVisita(), parametro.getGenerada());
						mensaje.setCurrent(listaCompra);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
}
