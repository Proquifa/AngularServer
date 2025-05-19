/*package com.proquifa.net.resource.reportes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.StatusMessage;
import com.proquifa.net.modelo.comun.TiempoProceso;
import com.proquifa.net.modelo.ventas.reportes.seguimientos.PartidaCotizacionEnSeguimiento;
import com.proquifa.net.modelo.ventas.reportes.seguimientos.SeguimientoCotizacion;
import com.proquifa.net.negocio.consultas.ConsultaEntregasService;
import com.proquifa.net.negocio.reportes.SeguimientoCotizacionesService;

@RestController
@CrossOrigin

public class SeguimientoCotizacionesResource {
	@Autowired
	SeguimientoCotizacionesService seguimientoCotizacionesService;
	
	@PostMapping("/obtenerCotizacionesEnSeguimientoEnPartidas")
	public ResponseEntity<StatusMessage> obtenerCotizacionesEnSeguimientoEnPartidas(@RequestBody Parametro parametro){
		log.info("on obtenerCotizacionesEnSeguimientoEnPartidas");
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			
			log.info("Confirmación:");
			log.info(parametro.getConfirmacion());
			log.info("Cliente:");
			log.info( parametro.getCliente());
			log.info("Empleado:");
			log.info( parametro.getEmpleadoString());
			log.info("Tipo:");
			log.info( parametro.getTipo());
			log.info("Marca:");
			log.info( parametro.getMarca());
			log.info("Control:");
			log.info( parametro.getControl());
			log.info("Master:");
			log.info( parametro.getMaster());
			List<SeguimientoCotizacion> seguimientoCotizaciones = seguimientoCotizacionesService.obtenerCotizacionesEnSeguimientoEnPartidas(parametro.getConfirmacion(), parametro.getCliente(), parametro.getEmpleadoString(), parametro.getTipo(), parametro.getMarca(), parametro.getControl(), parametro.getMaster());
			mensaje.setCurrent(seguimientoCotizaciones);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/obtenerPartidasPorCotizacionEnSeguimiento")
	public ResponseEntity<StatusMessage> obtenerPartidasPorCotizacionEnSeguimiento(@RequestBody Parametro parametro){
		log.info("on obtenerPartidasPorCotizacionEnSeguimiento");
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			
			log.info("Confirmación:");
			log.info(parametro.getFolio());

			List<PartidaCotizacionEnSeguimiento> partidasSeguimiento = seguimientoCotizacionesService.obtenerPartidasPorCotizacionEnSeguimiento(parametro.getFolio());
			mensaje.setCurrent(partidasSeguimiento);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
}
*/