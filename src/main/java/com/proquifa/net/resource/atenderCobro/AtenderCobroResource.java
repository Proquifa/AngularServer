package com.proquifa.net.resource.atenderCobro;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.proquifa.net.modelo.catalogos.cobrosypagos.facturista.CuentaBanco;
import com.proquifa.net.modelo.catalogos.cobrosypagos.facturista.Depositos;
import com.proquifa.net.modelo.catalogos.cobrosypagos.facturista.Excedentes;
import com.proquifa.net.modelo.catalogos.cobrosypagos.facturista.Factura;
import com.proquifa.net.modelo.cobrosypagos.facturista.NotaCredito;
import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.StatusMessage;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.negocio.atenderCobro.atenderCobroService;

@RestController
@CrossOrigin
public class AtenderCobroResource {

	final Logger log = LoggerFactory.getLogger(AtenderCobroResource.class);
	
	@Autowired
	atenderCobroService atenderCobroService;
	
	@PostMapping("/atenderCobro")
	public ResponseEntity<StatusMessage> atenderCobro(@RequestBody String idCliente){
		log.info("on obtenerListaEmpresasParaCotizar");
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			int idusuario = Integer.parseInt(idCliente);
			Map<String, List<Factura>> mapReturn = new HashMap<String, List<Factura>>();
			mapReturn = atenderCobroService.atenderCobro(idusuario);
			mensaje.setCurrent(mapReturn);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	@PostMapping("/obtenerNotasDeCredito")
	public ResponseEntity<StatusMessage> obtenerNotas(@RequestBody 	String idCliente){
		StatusMessage mensaje = new StatusMessage();
		try {
			System.out.print(idCliente);
				 mensaje.setStatus(HttpStatus.OK.value());
				 mensaje.setMessage("ok");				 
				 int idusuario = Integer.parseInt(idCliente);
				 Map<String, List<NotaCredito>> mapReturn = new HashMap<String, List<NotaCredito>>();
				  mapReturn = atenderCobroService.obtenerNotas(idusuario);
				 mensaje.setCurrent(mapReturn);
				 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	@PostMapping("/generarGrafica")
	public ResponseEntity<StatusMessage> obtenerNotas(@RequestBody Parametro parametro){
		StatusMessage mensaje = new StatusMessage();
		try {
				 mensaje.setStatus(HttpStatus.OK.value());
				 mensaje.setMessage("ok");				 
				 Map<String, List<Factura>> mapReturn = new HashMap<String, List<Factura>>();
				 mapReturn =  atenderCobroService.generarGraficaCobro(parametro);
				 mensaje.setCurrent(mapReturn);
				 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	@PostMapping("/consultarExcedentes")
	public ResponseEntity<StatusMessage> consultarExcedentes(@RequestBody Parametro parametro){
		StatusMessage mensaje = new StatusMessage();
		try {
				 mensaje.setStatus(HttpStatus.OK.value());
				 mensaje.setMessage("ok");				 
				 Map<String, List<Excedentes>> mapReturn = new HashMap<String, List<Excedentes>>();
				 mapReturn =  atenderCobroService.consultarExcedentes(parametro);
				 mensaje.setCurrent(mapReturn);
				 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/consultarDepositos")
	public ResponseEntity<StatusMessage> consultarDepositos(@RequestBody Parametro parametro){
		StatusMessage mensaje = new StatusMessage();
		try {
				 mensaje.setStatus(HttpStatus.OK.value());
				 mensaje.setMessage("ok");				 
				 Map<String, List<Depositos>> mapReturn = new HashMap<String, List<Depositos>>();
				 mapReturn =  atenderCobroService.consultarDepositos(parametro);
				 mensaje.setCurrent(mapReturn);
				 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	

	@GetMapping("/consultarBancos")
	public ResponseEntity<StatusMessage> consultarBancos(){
		StatusMessage mensaje = new StatusMessage();
		try {
				 mensaje.setStatus(HttpStatus.OK.value());
				 mensaje.setMessage("ok");				 
				 Map<String, List<CuentaBanco>> mapReturn = new HashMap<String, List<CuentaBanco>>();
				 mapReturn =  atenderCobroService.consultarBancos();
				 mensaje.setCurrent(mapReturn);
				 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/registrarDepositos")
	public ResponseEntity<StatusMessage> registrarDepositos(@RequestBody Depositos deposito){
		StatusMessage mensaje = new StatusMessage();
		try {
				 mensaje.setStatus(HttpStatus.OK.value());
				 mensaje.setMessage("ok");				 
				 
				 int idDeposito =  atenderCobroService.registrarDeposito(deposito);
				 mensaje.setCurrent(idDeposito);
				 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value = "/at/documentoReferencia/{idDeposito}", method = RequestMethod.POST)
	public ResponseEntity<StatusMessage> subirArchivos(@RequestParam LinkedList<MultipartFile> file, @PathVariable Integer idDeposito) {
		StatusMessage mensaje = new StatusMessage();
		try {
			 mensaje.setStatus(HttpStatus.OK.value());
			 mensaje.setMessage("ok");
			 String idP ="Deposito_"+ idDeposito.toString() + ".pdf";
			 Funcion funcion = new Funcion();
			 String ruta = funcion.obtenerRutaCompletaDocumento("", "Deposito", "");
//			 
			 MultipartFile pdf = file.get(0);
			 Funcion.copyFile(idP, ruta, pdf.getInputStream());
			 mensaje.setMessage("true");
			 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("false");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
}
