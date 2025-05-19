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

import com.proquifa.net.modelo.compras.Compra;
import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.StatusMessage;
import com.proquifa.net.modelo.consultas.comun.ResumenConsulta;
import com.proquifa.net.negocio.consultas.ConsultaCobrosService;
import com.proquifa.net.negocio.consultas.ConsultaComprasService;

@RestController
@CrossOrigin
public class ConsultaComprasResource {
	
	final Logger log = LoggerFactory.getLogger(ConsultaComprasResource.class);
	
	@Autowired
	ConsultaCobrosService consultaCobroService;
		
	@Autowired
	ConsultaComprasService consultaComprasService;
	
	@PostMapping("/obtenerCompras")
	public ResponseEntity<StatusMessage> obtenerCompras(@RequestBody Parametro parametro){
		log.info("on obtenerCompras");
		StatusMessage mensaje = new StatusMessage();
		try {

			//log.info(parametro.getFinicio());
			//log.info(parametro.getFfin());
			//7log.info(parametro.getFechaInicio());
			//log.info(parametro.getFechaInicio());
	
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("Estado en controller---->"+parametro.getestadoInt());
			
			List<Compra> listaCobros = consultaComprasService.obtenerCompras(parametro.getFinicio(),parametro.getFfin(), parametro.getproveedor(), parametro.getestadoInt(), parametro.getordenCompra(),parametro.getIdUsuario(),parametro.getempresaCompra());
			
			log.info("Tamaño lista ---->"+listaCobros.size());
			mensaje.setCurrent(listaCobros);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	@PostMapping("/obtenerGraficaXCompra")
	public ResponseEntity<StatusMessage> obtenerGraficaXCompra(@RequestBody Parametro parametro){
		log.info("on obtenerGraficaXCompra");
		StatusMessage mensaje = new StatusMessage();
		try {

			//log.info(parametro.getFinicio());
			//log.info(parametro.getFfin());
			//7log.info(parametro.getFechaInicio());
			//log.info(parametro.getFechaInicio());
	
				
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			/*log.info("Estado en string---->"+parametro);
			log.info("Estado en fInicio---->"+parametro.getFinicio());
			log.info("Estado en fFin---->"+parametro.getFinicio());
			log.info("Estado en proveedor---->"+parametro.getproveedor());
			log.info("Estado en estado---->"+parametro.getestadoInt());
			log.info("Estado en coloco---->"+parametro.getcoloco());
			log.info("Estado en ordenCompra---->"+parametro.getordenCompra());
			log.info("Estado en idUusrioa---->"+parametro.getIdUsuario());
			*/
		//    List<Compra> listaCompras = consultaComprasService.obtenerGraficaXCompra(null, null, 0L, 0, 0, "010614-5167", 0L);
		List<Compra> listaCompras = consultaComprasService.obtenerGraficaXCompra(parametro.getFinicio(), parametro.getFfin(), parametro.getproveedor(), parametro.getestadoInt(), parametro.getcoloco(), parametro.getordenCompra(), parametro.getIdUsuario());
			
			
			
			log.info("Tamaño lista ---->"+listaCompras.size());
			mensaje.setCurrent(listaCompras);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	//List<ResumenConsulta> obtenerResumenDeConsultaCompras(Date FechaInicio, Date FechaFin, Long proveedor, Integer estado, Integer coloco, String ordenCompra, Long usuario) throws ProquifaNetException;
	@PostMapping("/obtenerResumenDeConsultaCompras")
	public ResponseEntity<StatusMessage> obtenerResumenDeConsultaCompras(@RequestBody Parametro parametro){
		log.info("on obtenerGraficaXCompra");
		StatusMessage mensaje = new StatusMessage();
		try {

			//log.info(parametro.getFinicio());
			//log.info(parametro.getFfin());
			//7log.info(parametro.getFechaInicio());
			//log.info(parametro.getFechaInicio());
	
				
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			/*log.info("Estado en string---->"+parametro);
			log.info("Estado en fInicio---->"+parametro.getFinicio());
			log.info("Estado en fFin---->"+parametro.getFinicio());
			log.info("Estado en proveedor---->"+parametro.getproveedor());
			log.info("Estado en estado---->"+parametro.getestadoInt());
			log.info("Estado en coloco---->"+parametro.getcoloco());
			log.info("Estado en ordenCompra---->"+parametro.getordenCompra());
			log.info("Estado en idUusrioa---->"+parametro.getIdUsuario());
			*/
		//    List<Compra> listaCompras = consultaComprasService.obtenerGraficaXCompra(null, null, 0L, 0, 0, "010614-5167", 0L);
		List<ResumenConsulta> listaResumenConsulta = consultaComprasService.obtenerResumenDeConsultaCompras(parametro.getFinicio(), parametro.getFfin(), parametro.getproveedor(), parametro.getestadoInt(), parametro.getcoloco(), parametro.getordenCompra(), parametro.getIdUsuario());
			
			
			
			log.info("Tamaño lista ---->"+listaResumenConsulta.size());
			mensaje.setCurrent(listaResumenConsulta);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
}
