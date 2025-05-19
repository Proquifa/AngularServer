package com.proquifa.net.resource.consultas;




import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.proquifa.net.modelo.cobrosypagos.facturista.Facturacion;
import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.StatusMessage;
import com.proquifa.net.modelo.comun.TiempoProceso;
import com.proquifa.net.negocio.consultas.ConsultaFacturacionService;

@RestController
@CrossOrigin
public class ConsultaFacturacionResource {
	@Autowired
	ConsultaFacturacionService consultaFacturacionService;
	
	final Logger log = LoggerFactory.getLogger(ConsultaFacturacionResource.class);

	@PostMapping("/consultaAvanzadaFacturacion")
	public ResponseEntity<StatusMessage> consultaAvanzadaFacturacion(@RequestBody Parametro parametro){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			log.info("Fecha inicio:");
			log.info("",parametro.getFinicio());
			log.info("Fecha fin:");
			log.info("",parametro.getFfin());
			log.info("Cliente:");
			log.info(parametro.getCliente());
			log.info("Estado:");
			log.info(parametro.getEstado());
			log.info("Estado:");
			log.info(parametro.getRefacturada());
			log.info("Facturo:");
			log.info(parametro.getFacturo());
			log.info("Tipo:");
			log.info(parametro.getTipo());
			log.info("Medio:");
			log.info(parametro.getMedio());
			log.info("Medio:");
			log.info(parametro.getcPago());
			log.info("Usuario logueado:");
			log.info("",parametro.getIdUsuarioLogueado());
			log.info("Cobrador:");
			log.info("",parametro.getCobrador());
			List<Facturacion> lstFacturacion = consultaFacturacionService.consultaAvanzadaFacturacion(parametro.getFinicio(),parametro.getFfin(),parametro.getCliente(),parametro.getEstado(),parametro.getRefacturada(),parametro.getFacturo(),parametro.getTipo(),parametro.getMedio(),parametro.getcPago(),parametro.getIdUsuarioLogueado(), parametro.getCobrador());
			mensaje.setCurrent(lstFacturacion);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	@PostMapping("/consultaRapidaFacturacion")
	public ResponseEntity<StatusMessage> consultaRapidaFacturacion(@RequestBody Parametro parametro){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("Factura:");
			log.info(parametro.getFacturaS());
			log.info("CPedido:");
			log.info(parametro.getcPedido());
			log.info("Uuid:");
			log.info(parametro.getUuid());
			log.info("Fpor:");
			log.info(parametro.getFpor());
			log.info("ID Usuario Logueado:");
			log.info("",parametro.getIdUsuarioLogueado());
			List<Facturacion> lstFacturacionRapida = consultaFacturacionService.consultaRapidaFacturacion(parametro.getFacturaS(),parametro.getcPedido(),parametro.getUuid(),parametro.getFpor(),parametro.getIdUsuarioLogueado());
			mensaje.setCurrent(lstFacturacionRapida);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}





	@PostMapping("/obtenerResumen")
	public ResponseEntity<StatusMessage> obtenerResumen(@RequestBody Parametro parametro){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("Factura:");
			log.info(parametro.getFacturaS());
			log.info("Facturado por:");
			log.info(parametro.getFpor());
			List<TiempoProceso> lstResumen = consultaFacturacionService.obtenerResumen(parametro.getFacturaS(), parametro.getFpor());
			mensaje.setCurrent(lstResumen);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}

	@PostMapping("/obtenerResumenEntrega")
	public ResponseEntity<StatusMessage> obtenerResumenEntrega(@RequestBody Parametro parametro){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("Factura:");
			log.info(parametro.getFacturaS());
			log.info("Facturado por:");
			log.info(parametro.getFpor());
			List<TiempoProceso> lstResumenEntrega = consultaFacturacionService.obtenerResumenEntrega(parametro.getFacturaS(), parametro.getFpor());
			mensaje.setCurrent(lstResumenEntrega);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}


	@PostMapping("/obtenerResumenRevision")
	public ResponseEntity<StatusMessage> obtenerResumenRevision(@RequestBody Parametro parametro){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("Factura:");
			log.info(parametro.getFacturaS());
			log.info("Facturado por:");
			log.info(parametro.getFpor());
			List<TiempoProceso> lstResumenRevision = consultaFacturacionService.obtenerResumenRevision(parametro.getFacturaS(), parametro.getFpor());
			mensaje.setCurrent(lstResumenRevision);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}

	@PostMapping("/obtenerResumenCobro")
	public ResponseEntity<StatusMessage> obtenerResumenCobro(@RequestBody Parametro parametro){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("Factura:");
			log.info(parametro.getFacturaS());
			log.info("Facturado por:");
			log.info(parametro.getFpor());
			log.info("SC:");
			log.info(parametro.getSC());
			List<TiempoProceso> lstResumenCobro = consultaFacturacionService.obtenerResumenCobro(parametro.getFacturaS(), parametro.getFpor(), parametro.getSC());
			mensaje.setCurrent(lstResumenCobro);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}

	@PostMapping("/obtenerResumenMonitoreoCobro")
	public ResponseEntity<StatusMessage> obtenerResumenMonitoreoCobro(@RequestBody Parametro parametro){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("Factura:");
			log.info(parametro.getFacturaS());
			log.info("Facturado por:");
			log.info(parametro.getFpor());
			List<Facturacion> lstResumenCobro = consultaFacturacionService.obtenerResumenMonitoreoCobro(parametro.getFacturaS(), parametro.getFpor());
			mensaje.setCurrent(lstResumenCobro);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}

	@PostMapping("/obtenerResumenCancelacion")
	public ResponseEntity<StatusMessage> obtenerResumenCancelacion(@RequestBody Parametro parametro){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("Factura:");
			log.info(parametro.getFacturaS());
			log.info("Facturado por:");
			log.info(parametro.getFpor());
			List<Facturacion> lstResumenCancelacion = consultaFacturacionService.obtenerResumenCancelacion(parametro.getFacturaS(), parametro.getFpor());
			mensaje.setCurrent(lstResumenCancelacion);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}

	@PostMapping("/obtenerResumenFactura")
	public ResponseEntity<StatusMessage> obtenerResumenFactura(@RequestBody Parametro parametro){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("Factura:");
			log.info(parametro.getFacturaS());
			log.info("Facturado por:");
			log.info(parametro.getFpor());
			List<TiempoProceso> lstResumenFactura = consultaFacturacionService.obtenerResumenFactura(parametro.getFacturaS(), parametro.getFpor());
			mensaje.setCurrent(lstResumenFactura);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}

	@PostMapping("/obtenerLineaTiempoPrepago")
	public ResponseEntity<StatusMessage> obtenerLineaTiempoPrepago(@RequestBody Parametro parametro){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("Factura:");
			log.info(parametro.getFacturaS());
			log.info("Facturado por:");
			log.info(parametro.getFpor());
			List<TiempoProceso> lstPrepago = consultaFacturacionService.obtenerLineaTiempoPrepago(parametro.getFacturaS(), parametro.getFpor());
			mensaje.setCurrent(lstPrepago);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}

	@PostMapping("/obtenerResumenFacturaPrepago")
	public ResponseEntity<StatusMessage> obtenerResumenFacturaPrepago(@RequestBody Parametro parametro){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("Factura:");
			log.info(parametro.getFacturaS());
			log.info("Facturado por:");
			log.info(parametro.getFpor());
			List<Facturacion> lstPrepago = consultaFacturacionService.obtenerResumenFacturaPrepago(parametro.getFacturaS(), parametro.getFpor());
			mensaje.setCurrent(lstPrepago);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}

	@PostMapping("/obtenerResumenFacturaRemision")
	public ResponseEntity<StatusMessage> obtenerResumenFacturaRemision(@RequestBody Parametro parametro){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("Factura:");
			log.info(parametro.getFacturaS());
			log.info("Facturado por:");
			log.info(parametro.getFpor());
			List<Facturacion> lstPrepago = consultaFacturacionService.obtenerResumenFacturaRemision(parametro.getFacturaS(), parametro.getFpor());
			mensaje.setCurrent(lstPrepago);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}


	@PostMapping("/obtenerResumenRefacturacion")
	public ResponseEntity<StatusMessage> obtenerResumenRefacturacion(@RequestBody Parametro parametro){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("Factura:");
			log.info(parametro.getFacturaS());
			log.info("Facturado por:");
			log.info(parametro.getFpor());
			List<Facturacion> lstPrepago = consultaFacturacionService.obtenerResumenRefacturacion(parametro.getFacturaS(), parametro.getFpor());
			mensaje.setCurrent(lstPrepago);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}

	@PostMapping("/obtenerResumenFacturacionXAdelantado")
	public ResponseEntity<StatusMessage> obtenerResumenFacturacionXAdelantado(@RequestBody Parametro parametro){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("Factura:");
			log.info(parametro.getFacturaS());
			log.info("Facturado por:");
			log.info(parametro.getFpor());
			List<Facturacion> lstPrepago = consultaFacturacionService.obtenerResumenFacturacionXAdelantado(parametro.getFacturaS(), parametro.getFpor());
			mensaje.setCurrent(lstPrepago);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}

	//	@PostMapping("/generarZip")
	//	public ResponseEntity<StatusMessage> generarZip(@RequestBody Parametro parametro){
	//		StatusMessage mensaje = new StatusMessage();
	//		try {
	////			mensaje.setStatus(HttpStatus.OK.value());
	////			mensaje.setMessage("ok");
	//			log.info("Entro a generar ZIP");
	//			ByteArrayOutputStream buffer = consultaFacturacionService.generarZip(parametro.getArchivos(), parametro.getNombres());
	//			if(buffer != null){
	//				ResponseBuilder respuesta = Response.ok(Base64.encodeBase64(buffer.toByteArray()))
	//						.header("Content-Disposition", "attachment; filename=facturas.zip");
	//				Response responseBuilder = respuesta.build();
	//				mensaje.setCurrent(responseBuilder);
	//			}
	//			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
	//		} catch (Exception e) {
	//			log.info(e.toString());
	//			// TODO: handle exception
	//		}
	//		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
	//		mensaje.setMessage("Error");
	//		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	//	}

	@PostMapping(value="/generarZip")
	public ResponseEntity<Resource> obtenerArchivo(@RequestBody Parametro parametro)throws Exception {
		try {
			String zipFile = consultaFacturacionService.generarZip(parametro.getArchivosClientes(), parametro.getnombreArchivo());
			File file = new File("archivos/" + zipFile + ".zip");
			Path path = Paths.get(file.getAbsolutePath());
			
			ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
			
			HttpHeaders headers = new HttpHeaders();
			FileUtils.deleteDirectory(new File("archivos/" + parametro.getnombreArchivo()));
			headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
			return ResponseEntity.ok()
		            .headers(headers)
		            .contentLength(file.length())
		            .contentType(MediaType.APPLICATION_OCTET_STREAM)
		            .body(resource);
		} catch (Exception e) {	
			e.printStackTrace();
		}

		StatusMessage statusMessage = new StatusMessage();
		statusMessage.setStatus(Status.FORBIDDEN.getStatusCode());
		statusMessage.setMessage(
				"Access Denied for this functionality !!!");
		return null;
	}
	
	@PostMapping(value="/eliminarZip")
	public ResponseEntity<StatusMessage> borrarArchivoZip(@RequestBody Parametro parametro) throws Exception {
		try {
			FileUtils.forceDelete(new File("archivos/" + parametro.getnombreArchivo()+".zip"));
			StatusMessage statusMessage = new StatusMessage();
			statusMessage.setStatus(Status.OK.getStatusCode());
			statusMessage.setMessage("Archivo eliminado correctamente");
			return new ResponseEntity<StatusMessage>(statusMessage,HttpStatus.OK);
		} catch (Exception e) {	
			e.printStackTrace();
		}

		StatusMessage statusMessage = new StatusMessage();
		statusMessage.setStatus(Status.FORBIDDEN.getStatusCode());
		statusMessage.setMessage(
				"Access Denied for this functionality !!!");
		return new ResponseEntity<StatusMessage>(statusMessage,HttpStatus.BAD_REQUEST);
	}

}