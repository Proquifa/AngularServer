package com.proquifa.net.resource.compras;

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

import com.proquifa.net.modelo.compras.PartidaCompra;
import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.StatusMessage;
import com.proquifa.net.modelo.comun.TiempoProceso;
import com.proquifa.net.negocio.compras.CompraService;
import com.proquifa.net.negocio.compras.PartidaCompraService;
import com.proquifa.net.negocio.consultas.ConsultaCobrosService;

@RestController
@CrossOrigin
public class PartidaCompraResource {
	
	@Autowired
	PartidaCompraService partidaCompraService;
	
	@Autowired
	CompraService comprasService; 
	
	@Autowired
	ConsultaCobrosService consultaCobroService;
	
	final Logger log = LoggerFactory.getLogger(PartidaCompraResource.class);
	

	@PostMapping("/obtenerPatidasCompraParaCEspecifica")
	public ResponseEntity<StatusMessage> obtenerPatidasCompraParaCEspecifica(@RequestBody Parametro parametro){
		log.info("on obtenerPatidasCompraParaCEspecifica");
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("Orden de compra-> "+ parametro.getordenCompra());
			List<PartidaCompra> listaPartidaCompra = partidaCompraService.obtenerPatidasCompraParaCEspecifica(parametro.getordenCompra());
			log.info("lista Partida Compras-> "+ listaPartidaCompra.size());
			mensaje.setCurrent(listaPartidaCompra);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	
	@PostMapping("/obtenerTiempoProcesoPorPartida")
	public ResponseEntity<StatusMessage> obtenerTiempoProcesoPorPartida(@RequestBody Parametro parametro){
		log.info("on obtenerTiempoProcesoPorPartida");
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info( "idPCompra-> " + parametro.getidPCompra()+ " ordenCompra->" + parametro.getordenCompra());
			List<TiempoProceso> listaTIempoProceso = partidaCompraService.obtenerTiempoProcesoPorPartida(parametro.getidPCompra(), parametro.getordenCompra());
			log.info("lista Tiempo Proceso-> "+ listaTIempoProceso.size());
			mensaje.setCurrent(listaTIempoProceso);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	

	
}