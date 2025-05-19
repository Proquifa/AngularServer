package com.proquifa.net.resource.embalaje;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.StatusMessage;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.despachos.DocumentoXLS;
import com.proquifa.net.modelo.despachos.EmbalarPedido;
import com.proquifa.net.modelo.despachos.EstadisticaUsuarioEmbalar;
import com.proquifa.net.modelo.despachos.TotalEmbalar;
import com.proquifa.net.modelo.despachos.colectarPartidas;
import com.proquifa.net.modelo.despachos.packinglist.PackingList;
import com.proquifa.net.modelo.ventas.admoncomunicacion.Correo;
import com.proquifa.net.negocio.despachos.EmbalarService;
import org.springframework.web.multipart.MultipartFile;


@RestController
@CrossOrigin
public class EmbalarResource {
	@Autowired
	EmbalarService embalarService;
	 
	
	@PostMapping("/ConsultaTotalEmbalar")
	public ResponseEntity<StatusMessage> consultaTotalEmbalar(){
		StatusMessage mensaje = new StatusMessage();
		try {
			Map<String, List<TotalEmbalar>> mapReturn = new HashMap<String, List<TotalEmbalar>>();
			 mensaje.setStatus(HttpStatus.OK.value());
			 mensaje.setMessage("ok");	
			 mapReturn  = embalarService.obtenerTotalEmbalar();
			 mensaje.setCurrent(mapReturn);
			 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);			
		} catch (Exception e) {
			// TODO: handle exception
		}		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/ConsultaEstadisticaUsuarioEmbalar")
	public ResponseEntity<StatusMessage> EstadisticaUsuarioEmbalar(@RequestBody Empleado usuario){
		StatusMessage mensaje = new StatusMessage();
		try {
			Map<String, List<EstadisticaUsuarioEmbalar>> mapReturn = new HashMap<String, List<EstadisticaUsuarioEmbalar>>();
			 mensaje.setStatus(HttpStatus.OK.value());
			 mensaje.setMessage("ok");	
			 mapReturn  = embalarService.obtenerEstadisticasUsuarioEmbalar(usuario);
			 mensaje.setCurrent(mapReturn);
			 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);			
		} catch (Exception e) {
			// TODO: handle exception
		}		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	@PostMapping("/colectarPartidas")
	public ResponseEntity<StatusMessage> colectarPartidas(@RequestBody Parametro parametro){
		StatusMessage mensaje = new StatusMessage();
		try {
			Map<String, List<colectarPartidas>> mapReturn = new HashMap<String, List<colectarPartidas>>();
			 mensaje.setStatus(HttpStatus.OK.value());
			 mensaje.setMessage("ok");	
			 mapReturn  = embalarService.colectarPartidas(parametro);
			 mensaje.setCurrent(mapReturn);
			 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);			
		} catch (Exception e) {
			// TODO: handle exception
		}		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	@PostMapping("/generarDocumentos")
	public ResponseEntity<StatusMessage> generarDocumentos(@RequestBody Parametro parametro){
		StatusMessage mensaje = new StatusMessage();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, List<DocumentoXLS>> mapReturn = new HashMap<String, List<DocumentoXLS>>();
			 mensaje.setStatus(HttpStatus.OK.value());
			 mensaje.setMessage("ok");	
			 mapReturn  = embalarService.generarDocumentos(parametro);
			 map.put("documentos", mapReturn);
			 map.put("restricciones", embalarService.numeroCopias(parametro.getIdUsuarioLogueado().intValue(), parametro.getEstado()));
			 map.put("CFDI", embalarService.obtenerCDF(parametro.getEstado(), parametro.getIdUsuarioLogueado()));
			 mensaje.setCurrent(map);
			 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	
	
	
	@PostMapping("/registrarEmbalarPedido")
	public ResponseEntity<StatusMessage> registrarEmbalarPedido(@RequestBody Parametro parametro){
		StatusMessage mensaje = new StatusMessage();
		try {
			boolean registro = false;
			try {
				registro = embalarService.registrarEmbalarPedido(parametro);
			} catch (Exception e) {
				if (e.getMessage().contains("fue elegida como sujeto del interbloqueo. Ejecute de nuevo la transacción.;")) {
					registro = embalarService.registrarEmbalarPedido(parametro);
				}
			}

			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			mensaje.setCurrent(registro);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);


		} catch (Exception e) {
			e.printStackTrace();
		}		
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("embalar/pedidosGDL")
	public ResponseEntity<StatusMessage> pedidosGDL(@RequestBody Parametro parametro){
		StatusMessage mensaje = new StatusMessage();
		try {
				 mensaje.setStatus(HttpStatus.OK.value());
				 mensaje.setMessage("ok");
				 mensaje.setCurrent(embalarService.pedidosGDL(parametro));
				 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	
	@PostMapping("/obtenerFolioPorUsuario")
	public ResponseEntity<StatusMessage> obtenerFolioPorUsuario(@RequestBody String usuario){
		StatusMessage mensaje = new StatusMessage();
		try {
			System.out.print(usuario);
				 mensaje.setStatus(HttpStatus.OK.value());
				 mensaje.setMessage("ok");				 
				 Integer idusuario = Integer.parseInt(usuario);
				 List <EmbalarPedido>  lstResult = embalarService.ObtenerFolioPorUsuario(idusuario);
				 mensaje.setCurrent(lstResult);
				 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	@PostMapping("/consultarEstado")
	public ResponseEntity<StatusMessage> consultarEstado(@RequestBody String idUsuario){
		StatusMessage mensaje = new StatusMessage();
		try {
				 mensaje.setStatus(HttpStatus.OK.value());
				 mensaje.setMessage("ok");						 
				 //Long idUsuario = parametro.getIdUsuarioLogueado();
				 Integer idusuario = Integer.parseInt(idUsuario);
				 String estado = embalarService.consultarEstado(idusuario);
				 mensaje.setCurrent(estado); 
				 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	@PostMapping("/actualizarEstado")
	public ResponseEntity<StatusMessage> actualizarEstado(@RequestBody Parametro parametro){
		StatusMessage mensaje = new StatusMessage();
		try {
				 mensaje.setStatus(HttpStatus.OK.value());
				 mensaje.setMessage("ok");
				 boolean actualizar = embalarService.cambiarEstado(parametro);
				 mensaje.setCurrent(actualizar);
				 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/generarPackingList")
	public ResponseEntity<StatusMessage> generarPackingList(@RequestBody PackingList packingList){
		StatusMessage mensaje = new StatusMessage();
		try {
				 mensaje.setStatus(HttpStatus.OK.value());
				 mensaje.setMessage("OK");
				 Map<String, String> idPedidosFactura = embalarService.generarFacturaElectronica(packingList);
				 if (idPedidosFactura != null) {
					Object obj = embalarService.insertarPackingList(packingList, idPedidosFactura);
					if (embalarService.esPLRecogeEnPROQUIFA(packingList.getFolio())) 
						embalarService.enviarCorreoRecogeEnProquifa(packingList.getFolio());
					
					if (embalarService.esPLdeControlado(packingList.getFolio()))
						embalarService.generarRegistroEntregaControlados(packingList.getFolio());					 	 
					 mensaje.setCurrent(obj);
				 } else {
					 mensaje.setCurrent(-1);
				 }
				 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/generarRegEntregaControlado")
	public ResponseEntity<StatusMessage> generarRegEntregaControlado(@RequestBody String folioPL){
		StatusMessage mensaje = new StatusMessage();
		try {
			System.out.println("\nfolioPL: " + folioPL);
				if (embalarService.generarRegistroEntregaControlados(folioPL)){
					mensaje.setStatus(HttpStatus.OK.value());
					mensaje.setMessage("OK");					
					return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
				}
				return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			// TODO: handle exception
		}		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	@PostMapping("/actualizarEstadoInsertarPendiente")
	public ResponseEntity<StatusMessage> insertarPendiente(@RequestBody Parametro parametro){
		StatusMessage mensaje = new StatusMessage();
		try {
				 mensaje.setStatus(HttpStatus.OK.value());
				 mensaje.setMessage("OK");
				 mensaje.setCurrent(embalarService.insertarPendiente(parametro));
				 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}

	@PostMapping("/pathFilePedidos")
	public ResponseEntity<StatusMessage> pathFilePedidos(@RequestBody Parametro parametro) throws  ProquifaNetException{
		StatusMessage mensaje = new StatusMessage();
		try {	
				 mensaje.setCurrent(embalarService.pathFilePedidos(parametro.getIdUsuario().intValue(), parametro.getEstado()));
				
				return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}

	@PostMapping("embalar/obtenerDatosContacto")
	public ResponseEntity<StatusMessage> obtenerDatosContacto() throws  ProquifaNetException{
		StatusMessage mensaje = new StatusMessage();
		try {
				 mensaje.setStatus(HttpStatus.OK.value());
				 mensaje.setMessage("OK");
				 mensaje.setCurrent(embalarService.obtenerDatosContacto());
				 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
		}		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PutMapping("embalar/finalizarGDL")
	public ResponseEntity<StatusMessage> finalizarGDL(@RequestBody Map<String, String> data) throws  ProquifaNetException{
		StatusMessage mensaje = new StatusMessage();
		try {
				 mensaje.setStatus(HttpStatus.OK.value());
				 mensaje.setMessage("OK");
				 mensaje.setCurrent(embalarService.finalizarGDL(data.get("noGuia"), data.get("mensajeria")));
				 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
		}		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	@PostMapping("embalar/test")
	public ResponseEntity<StatusMessage> generarPackingList(@RequestBody List<String> lstPackingList) throws  ProquifaNetException{
		StatusMessage mensaje = new StatusMessage();
		try {
				 mensaje.setStatus(HttpStatus.OK.value());
				 mensaje.setMessage("OK");
				 embalarService.generarPackingList(lstPackingList);
				 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
		}		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}

	@SuppressWarnings("unchecked")
	@PostMapping("embalar/generarPDFRemision")
	public ResponseEntity<StatusMessage> generarPDFRemision(@RequestBody Map<String, Object> data) throws  ProquifaNetException{
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("OK");
			embalarService.generarPDFRemision(data.get("idEmbalar").toString(), (ArrayList<Integer>) data.get("facturas"), (ArrayList<Integer>) data.get("idRemision"));
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
		}		
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("embalar/finalizarEvidenciaFac")
	public ResponseEntity<StatusMessage> finalizarEvidenciaFac(@RequestBody String idFactura)throws ProquifaNetException{
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("OK");
			mensaje.setCurrent(embalarService.actualizarFactura(idFactura));
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("embalar/enviarCorreo")
	public ResponseEntity<StatusMessage>enviarCorreo(@RequestBody Correo datosCorreo)throws ProquifaNetException{
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("OK");
			mensaje.setCurrent(embalarService.enviarCorreo(datosCorreo));
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("embalar/totalesGeneral")
	public ResponseEntity<StatusMessage> totalesGeneral() throws ProquifaNetException{
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("OK");
			mensaje.setCurrent(embalarService.obtenerTot());
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.FORBIDDEN);
	}

	@PostMapping("embalar/cargarArchivo")
	public ResponseEntity<StatusMessage> cargarArchivo(@RequestParam("file") MultipartFile file) throws ProquifaNetException {

		StatusMessage mensaje = new StatusMessage();
		try {
			System.out.println("Entro al metodo cargarArchivo");
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("OK");
			embalarService.cargarArchivoClientesCFDI(file);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {

		}
		return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.FORBIDDEN);
	}
	@PostMapping("embalar/prodsDeStock")
	public ResponseEntity<StatusMessage>pedidoConProdsDeStock(@RequestBody Integer usuarioEmbalar)throws ProquifaNetException{
		StatusMessage mensaje = new StatusMessage();
		try{
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("OK");
			mensaje.setCurrent(embalarService.pedidoConProdsDeStock(usuarioEmbalar));
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		}catch(Exception e){

		}
		return new ResponseEntity<>(mensaje,HttpStatus.FORBIDDEN);
	}
	@PostMapping("embalar/mandarAStock")
	public ResponseEntity<StatusMessage>mandarAStock(@RequestBody List<Integer> idPPedido)throws ProquifaNetException{
		StatusMessage mensaje = new StatusMessage();
		try{
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("OK");
			mensaje.setCurrent(embalarService.mandarAStock(idPPedido));
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		}catch(Exception e){

		}
		return new ResponseEntity<>(mensaje,HttpStatus.FORBIDDEN);
	}
}
	



	
