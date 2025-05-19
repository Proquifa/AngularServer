package com.proquifa.net.resource.reportes;


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
import com.proquifa.net.modelo.cobrosypagos.facturista.HistorialFactura;
import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.StatusMessage;
import com.proquifa.net.negocio.cobrosypagos.facturista.FacturacionService;
import com.proquifa.net.negocio.reportes.ReporteCobrosService;


@RestController
@CrossOrigin

public class ReporteCobrosResource {
	
	final Logger log = LoggerFactory.getLogger(ReporteCobrosResource.class);
	
	@Autowired
	ReporteCobrosService reporteCobrosService;
	@Autowired
	FacturacionService facturacionService;
	@PostMapping("/reporteCobros")
	public ResponseEntity<StatusMessage> reporteCobros(@RequestBody Parametro parametro){
		log.info("on reporteCobros");
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			
			log.info("Factura:");
			log.info(parametro.getFacturaS());
			log.info("CPedido:");
			log.info( parametro.getcPedido());
			log.info("Usuario logueado:");
			log.info("",parametro.getIdUsuarioLogueado());
			List<Cobros> reporteCobros = reporteCobrosService.reporteCobros(parametro.getFacturaS(), parametro.getcPedido(), parametro.getIdUsuarioLogueado());
			mensaje.setCurrent(reporteCobros);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	@PostMapping("/listarHistorialXFactura")
	public ResponseEntity<StatusMessage> listarHistorialXFactura(@RequestBody Parametro parametro){
		log.info("on listarHistorialXFactura");
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			
			
			log.info("id Factura:");
			log.info("",parametro.getIdFactura());
			Long factura= parametro.getIdFactura() ;
			List<HistorialFactura> lsthitorial = facturacionService.listarHistorialXFactura(factura);
			mensaje.setCurrent(lsthitorial);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			log.info("Error lsthitorial "+ e );
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
}
