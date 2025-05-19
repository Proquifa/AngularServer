package com.proquifa.net.resource.inspeccion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.HeaderParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proquifa.net.modelo.comun.StatusMessage;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.despachos.ContadorPiezasXPrioridad;
import com.proquifa.net.modelo.despachos.PartidaInspeccion;
import com.proquifa.net.modelo.despachos.parametrosInspeccion;
import com.proquifa.net.modelo.despachos.totalesInspeccionProducto;
import com.proquifa.net.negocio.despachos.InspeccionService;



@RestController
@CrossOrigin
public class InspeccionarResource {
	
	final Logger log = LoggerFactory.getLogger(InspeccionarResource.class);
	
	@Autowired
	InspeccionService inspeccionService;
	
	@PostMapping("/consultaPartidasInspeccion")
	public ResponseEntity<StatusMessage> consultaPartidasInspeccion(){
		StatusMessage mensaje = new StatusMessage();
		try {
			 log.info("obtener partida en inspeccion");
			 mensaje.setStatus(HttpStatus.OK.value());
			 mensaje.setMessage("ok");
			 List<PartidaInspeccion> listPart = new ArrayList<PartidaInspeccion>();	
			 mensaje.setCurrent(inspeccionService.consultaPartidasInspeccion());
			 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
				
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	//Servicio para devolver el total de piezas por inspector.
		@PostMapping("/consultaDeTotalesPorInspector")
		public ResponseEntity<StatusMessage> consultaDeTotalesPorInspector(@RequestBody String inspector){
			log.info("inspector: " + inspector);
			StatusMessage mensaje = new StatusMessage();
			try {
				 log.info("consulta De Totales Por Inspector");
				 mensaje.setStatus(HttpStatus.OK.value());
				 mensaje.setMessage("ok");
				 totalesInspeccionProducto totalesInspector= new totalesInspeccionProducto();
				 totalesInspector = inspeccionService.consultaDeTotalesPorInspector(inspector);
				 mensaje.setCurrent(totalesInspector);
				 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
			 mensaje.setMessage("Error");
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
		}
		
		@PostMapping("/obtenerModoInspeccion")
		public ResponseEntity<StatusMessage> obtenerModoInspeccion(){
			StatusMessage mensaje = new StatusMessage();
			try {
				log.info("obtener modo inspeccion");
				mensaje.setStatus(HttpStatus.OK.value());
				mensaje.setMessage("ok");
				String modoInspeccion = "";
				modoInspeccion = inspeccionService.obtenerModoInspeccion();
				mensaje.setCurrent(modoInspeccion);
				return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);


			} catch (Exception e) {
				// TODO: handle exception
			}

			mensaje.setStatus(HttpStatus.FORBIDDEN.value());
			mensaje.setMessage("Error");
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
		}
		
		@PostMapping("/obtenerTotalesDeinspecionPorUsuario")
		public ResponseEntity<StatusMessage> obtenerTotalesDeinspecionPorUsuario(@RequestBody String usuario){
			StatusMessage mensaje = new StatusMessage();
			try {
				log.info("obtener totales");
				mensaje.setStatus(HttpStatus.OK.value());
				mensaje.setMessage("ok");
				totalesInspeccionProducto totales = new totalesInspeccionProducto();
				log.info("usuario:" + usuario);
				totales = inspeccionService.obtenerTotalesDeinspecionPorUsuario(usuario);
				mensaje.setCurrent(totales);
				log.info("",totales);
				return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);


			} catch (Exception e) {
				// TODO: handle exception
			}

			mensaje.setStatus(HttpStatus.FORBIDDEN.value());
			mensaje.setMessage("Error");
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
		}
		
		@PostMapping("/obtenerPiezasInspeccionadasHoy")
		public ResponseEntity<StatusMessage> obtenerTotalPiezasInspeccionadasHoy(@RequestBody String usuario){
			StatusMessage mensaje = new StatusMessage();
			try {
				log.info("obtener total piezas Inspeccionadas hoy.");
				mensaje.setStatus(HttpStatus.OK.value());
				mensaje.setMessage("ok");
				long pzasTotales = inspeccionService.piezasInspeccionadasHoy(usuario);
				mensaje.setCurrent(pzasTotales);
				return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
			} catch (Exception e) {
				// TODO: handle exception
			}
			mensaje.setStatus(HttpStatus.FORBIDDEN.value());
			mensaje.setMessage("Error");
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
		}

	@PostMapping("/ordenarPartidasInspeccion")
		public ResponseEntity<StatusMessage> ordenarPartidasInspeccion(@RequestBody String inspector){
			StatusMessage mensaje = new StatusMessage();
			try {
				 log.info("inspector:"+ inspector);
				 mensaje.setStatus(HttpStatus.OK.value());
				 mensaje.setMessage("ok");	
				 PartidaInspeccion listPart  = inspeccionService.ordenarPartidasInspeccion(inspector);
				 mensaje.setCurrent(listPart);
				 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);			
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
			 mensaje.setMessage("Error");
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
		}
	

	@PostMapping("/sumarPiezasInspeccionadasYPorInspeccionar")
	public ResponseEntity<StatusMessage> sumaPiezasInspeccionadasyPorInspeccion(@RequestBody String usuario){
		log.info("Entro al metodo.");
		StatusMessage mensaje = new StatusMessage();
		try {
			log.info("obtener total piezas Inspeccionadas hoy.");
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			long pzasTotales = inspeccionService.sumaPiezasInspeccionadasyPorInspeccion(usuario);
			mensaje.setCurrent(pzasTotales);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			log.info("Entro al catch");
			log.error(e.getMessage());
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}



			@PostMapping("/finalizarInspeccionDePartidas")
		public ResponseEntity<StatusMessage> finalizarInspeccionDePartidas(@RequestBody parametrosInspeccion param){
			StatusMessage mensaje = new StatusMessage();
			log.info("",param);
			try {
				log.info("finalizarInspeccionDePartidas");
				mensaje.setStatus(HttpStatus.OK.value());
				mensaje.setMessage("ok");
				boolean respuesta;
				
				mensaje.setCurrent(inspeccionService.finalizarInspeccionDePartidas(param));
				return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);


			} catch (Exception e) {
				e.printStackTrace();
			}

			mensaje.setStatus(HttpStatus.FORBIDDEN.value());
			mensaje.setMessage("Error");
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
		}
			
		@PostMapping("/obtenerNumPendientesInspeccion")
		public ResponseEntity<StatusMessage> obtenerNumPendientesInspeccion(){
			StatusMessage mensaje = new StatusMessage();
			try {
//				 log.info("obtener partida en inspeccion");
				 mensaje.setStatus(HttpStatus.OK.value());
				 mensaje.setMessage("ok");
				 Integer numPendientes = 0;	
				 numPendientes = inspeccionService.contadorSeccionArribados();
				 mensaje.setCurrent(numPendientes);
				 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
				
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
			 mensaje.setMessage("Error");
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
		}
	
	//test
	@PostMapping("/obtenerPiezasPorPrioridad")
	public ResponseEntity<StatusMessage> obtenerPiezasPorPrioridad(){
		StatusMessage mensaje = new StatusMessage();
//		log.info("entro al test");
		try {
//			 log.info("obtener partida en inspeccion");
			 mensaje.setStatus(HttpStatus.OK.value());
			 mensaje.setMessage("ok");
			 ContadorPiezasXPrioridad contPartidas = new ContadorPiezasXPrioridad();	
			 contPartidas = inspeccionService.obtenerPiezasPorPrioridad();
			 mensaje.setCurrent(contPartidas);
			 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
				
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	@PostMapping("/obtenerPiezasXFecha")
	public ResponseEntity<StatusMessage> obtenerPiezasXFecha(){
		StatusMessage mensaje = new StatusMessage();
		try {
			 log.info("obtener Piezas X Fecha");
			 mensaje.setStatus(HttpStatus.OK.value());
			 mensaje.setMessage("ok");
			 List<PartidaInspeccion> listPart = new ArrayList<PartidaInspeccion>();	
			 listPart = inspeccionService.obtenerPiezasXFecha();
			 mensaje.setCurrent(listPart);
			 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
				
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	

	//test
	@PostMapping("/obtenerConsecutivoDeLoteInspeccion")
	public ResponseEntity<StatusMessage> obtenerConsecutivoDeLoteInspeccion(@RequestBody String concepto){
		StatusMessage mensaje = new StatusMessage();
		try {
			log.info("obtener Consecutivo De Lote Inspeccion");
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			String folio = inspeccionService.obtenerConsecutivoDeLoteInspeccion(concepto);
			mensaje.setCurrent(folio);
			log.info(folio);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}

	@PostMapping("/nombreArchivo")
	public ResponseEntity<StatusMessage> nombreArchivo(@RequestBody String documento ) throws  ProquifaNetException{
		StatusMessage mensaje = new StatusMessage();
		try {
				ObjectMapper mapper = new ObjectMapper();
				Map<String, Object> map = new HashMap<String, Object>();
				
				 map =  mapper.readValue(documento,new TypeReference<Map<String, Object>>(){});
			     String cad  = inspeccionService.convierteArr(map);


			log.info(cad);
			mensaje.setCurrent(cad);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);

		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}


	@PostMapping("/guardarArchivo/{folioDocumentacion}/{subCarpeta}/{tipoArchivo}")
	public ResponseEntity<StatusMessage> guardarArchivo(@HeaderParam("x-auth-token") String token,@RequestParam LinkedList <MultipartFile> file , @PathVariable("folioDocumentacion") String folioDocumentacion,
			@PathVariable("subCarpeta") String subCarpeta, @PathVariable ("tipoArchivo") String tipoArchivo ) throws  ProquifaNetException{
	
		StatusMessage mensaje = new StatusMessage();
		log.info("",file);
		int i = 0;
		for (i = 0; i<file.size(); i++) {	
			
			String respuesta;
			try {
				
				respuesta = inspeccionService.enviaArchivo(file.get(i).getInputStream(), folioDocumentacion, subCarpeta, tipoArchivo);
				mensaje.setCurrent(respuesta);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
			
		}
			
	
		return null;
	}
	
	@PostMapping("/obtenerUbicacionInspeccion")
	public ResponseEntity<StatusMessage> obtenerUbicacionInspeccion(@RequestBody String data ) throws  ProquifaNetException{
		StatusMessage mensaje = new StatusMessage();
		try {
				ObjectMapper mapper = new ObjectMapper();
				Map<String, Object> map = new HashMap<String, Object>();
				
				 map =  mapper.readValue(data,new TypeReference<Map<String, Object>>(){});
			     String cad  = inspeccionService.obtenerUbicacionInspeccion(map);

			log.info(cad);
			mensaje.setCurrent(cad);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);

		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	@PostMapping("/guardarExistenciaUbicacion")
	public ResponseEntity<StatusMessage> guardarExistenciaUbicacion(@RequestBody String data ) throws  ProquifaNetException{
		StatusMessage mensaje = new StatusMessage();
		try {
				ObjectMapper mapper = new ObjectMapper();
				Map<String, Object> map = new HashMap<String, Object>();
				
				 map =  mapper.readValue(data,new TypeReference<Map<String, Object>>(){});
			     Boolean cad  = inspeccionService.guardarExistenciaUbicacion(map);

			log.info("",cad);
			mensaje.setCurrent(cad);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);

		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/guardarConsumible")
	public ResponseEntity<StatusMessage> guardarConsumible(@RequestBody String data ) throws  ProquifaNetException{
		StatusMessage mensaje = new StatusMessage();
		try {
				ObjectMapper mapper = new ObjectMapper();
				Map<String, Object> map = new HashMap<String, Object>();
				
				 map =  mapper.readValue(data,new TypeReference<Map<String, Object>>(){});
			     Boolean respuesta  = inspeccionService.guardarConsumible(map);

			log.info("guardar consumible: "+respuesta);
			mensaje.setCurrent(respuesta);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);

		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/inspeccionar/apartarInspeccion")
	public ResponseEntity<StatusMessage> apartarInspeccion(@RequestBody Integer idUsuario) throws ProquifaNetException {
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setCurrent(inspeccionService.obtenerPartida(idUsuario));
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);

		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	//// Nuevo
	@RequestMapping(value = "/inspeccionar/uploadImage/{tipo}", method = RequestMethod.POST)
	public ResponseEntity<StatusMessage> subirArchivos(@RequestParam LinkedList<MultipartFile> file, @PathVariable String tipo) {
		StatusMessage mensaje = new StatusMessage();
		try {
			 mensaje.setStatus(HttpStatus.OK.value());
			 mensaje.setMessage("ok");
			 String folio;
			 String folioCompleto = "";
			 Funcion funcion = new Funcion();
			 String ruta = funcion.obtenerRutaServidor("imagenRechazo","");
			 
			 for(MultipartFile pdf : file)
			 {
				 folio = inspeccionService.obtenerConsecutivoDeLoteInspeccion(tipo);
				 Funcion.copyFile(folio + ".png", ruta, pdf.getInputStream());
				 folioCompleto += folio + "|";
			 }
			 
			 mensaje.setCurrent(folioCompleto);
			 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	@PostMapping("/inspeccionar/obtenerComboUnidad")
	public ResponseEntity<StatusMessage> obtenerComboUnidad(@RequestBody String tipo ) throws  ProquifaNetException{
		StatusMessage mensaje = new StatusMessage();
		try {
				
			mensaje.setCurrent(inspeccionService.obtenerComboUbicaciones(tipo));
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);

		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/inspeccionar/verificarApartado")
	public ResponseEntity<StatusMessage> verificarApartado(@RequestBody parametrosInspeccion param) throws ProquifaNetException{
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setCurrent(inspeccionService.verificarApartado(param));
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			mensaje.setStatus(HttpStatus.FORBIDDEN.value());
			mensaje.setMessage("Error");
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.FORBIDDEN);
		}
	}

}
	
	
	
	
	
	
	
	
	

