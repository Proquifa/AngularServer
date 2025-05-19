/**
 * 
 */
package com.proquifa.net.resource.comun;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

import com.proquifa.net.modelo.compras.RegistroConfirmacion;
import com.proquifa.net.modelo.comun.Contacto;
import com.proquifa.net.modelo.comun.StatusMessage;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.negocio.compras.SaldoAFavorService;
import com.proquifa.net.negocio.comun.ReportesService;

/**
 * @author ymendez
 *
 */

@RestController
@CrossOrigin
public class ReportesResource {

	@Autowired
	ReportesService reportesService;
	
	@Autowired
	SaldoAFavorService saldoAFavorService;
	
	@PostMapping("reportes/Seguimiento")
	public ResponseEntity<StatusMessage> obtenerSeguimientos()throws ProquifaNetException{
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("OK");
			mensaje.setCurrent(reportesService.obtenerSeguimientos());
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("reportes/ScoringInspeccion")
	public ResponseEntity<StatusMessage> scoringInspeccionar( )throws ProquifaNetException{
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("OK");
			mensaje.setCurrent(reportesService.getScoringInspeccionar());
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("reportes/ScoringDespachos")
	public ResponseEntity<StatusMessage> scoringDespachos( )throws ProquifaNetException{
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("OK");
			mensaje.setCurrent(reportesService.getScoringDespachos());
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("reportes/obtenerOrdenDespacho/{ordenDespacho}")
	public ResponseEntity<StatusMessage> obtenerOrdenDespacho(@PathVariable String ordenDespacho)throws ProquifaNetException{
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("OK");
			mensaje.setCurrent(reportesService.getOrdenDespacho(ordenDespacho));
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	@PostMapping("reportes/consultaCompras/{compra}")
	public ResponseEntity<StatusMessage> obtenerHSCodeCompra(@PathVariable String compra)throws ProquifaNetException{
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("OK");
			mensaje.setCurrent(reportesService.getConsultaCompra(compra));
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	@RequestMapping(value = "/comun/uploadFile/{nombre}/{tipo}/{fPor}", method = RequestMethod.POST)
	public ResponseEntity<StatusMessage> subirArchivos(@RequestParam LinkedList<MultipartFile> file, @PathVariable String nombre, @PathVariable String tipo, @PathVariable String fPor) {
		StatusMessage mensaje = new StatusMessage();
		try {
			 mensaje.setStatus(HttpStatus.OK.value());
			 mensaje.setMessage("ok");
			 
			 Funcion funcion = new Funcion();
			 String ruta = funcion.obtenerRutaServidor(tipo, fPor);
//			 
			 MultipartFile pdf = file.get(0);
			 Funcion.copyFile(nombre, ruta, pdf.getInputStream());
			 
			 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		 mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		 mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping(value= "/comun/obtenerFecha")
	public ResponseEntity<StatusMessage> getYear(@RequestBody Map<String, Object> data){
		StatusMessage mensaje = new StatusMessage();
		try {
			System.out.println("Entro");
			 mensaje.setStatus(HttpStatus.OK.value());
			 mensaje.setMessage("ok");
			 mensaje.setCurrent(reportesService.obtenerDate(data.get("tipo").toString(), data.get("folio").toString(), data.get("carpeta").toString() ));
			
			 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			mensaje.setStatus(HttpStatus.FORBIDDEN.value());
			 mensaje.setMessage("Error");
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
		}
	}
	
	@PostMapping(value= "/comun/generarAvisoCambios")
	public ResponseEntity<StatusMessage> generarAvisoCambios(@RequestBody Map<String, Object> data){
		StatusMessage mensaje = new StatusMessage();
		try {
			System.out.println("Entro");
			 mensaje.setStatus(HttpStatus.OK.value());
			 mensaje.setMessage("ok");
			 List<RegistroConfirmacion> list = new ArrayList<RegistroConfirmacion>();
				RegistroConfirmacion c = new RegistroConfirmacion();
				c.setContacto(new Contacto());
				c.setFolioNT("NT-010320-7584");
				c.setCliente("LAB PISA MÉXICO");
				c.getContacto().setNombre("LIC. GABRIELA ROBLEDO");
				c.setPedido("110119-5079");
				c.setCpedido("4502072763");
				c.setTipoPartida("Cancelada");
				
				c.setComentarios("El fabricante reporta que no puede realizar la síntesis de este producto, por lo que se cancela de su orden de compra. Lamentamos los inconvenientes que este comunicado le ocasiona.");
				c.setNombreVendedor("Biridiana Arias Solis");
				c.setFporCliente("Proveedora");
				c.setExt("2141");
				c.setIdProducto(30190L);
				c.setIdComplemento(0L);
				
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
//				c.setFechaEstimadaEntrega(formatter.parse("17-Ago-2016"));
//				c.setFechaProveedor(formatter.parse("04-04-2016 00:00:00.0"));
				
				
				c.setCantidadCompra(2);
				c.setTotalPiezas(17l);
				//c.setfechae("RyndemPrueba S.A de C.V.");
//				c.setFechaNueva(formatter.parse("04-04-2016 00:00:00.0"));
				//c.setContacto("asd");
				list.add(c);
				
				saldoAFavorService.crearPdfAvisoDeCambios(list);
			
			 return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			mensaje.setStatus(HttpStatus.FORBIDDEN.value());
			 mensaje.setMessage("Error");
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
		}
	}
	
	@PostMapping("reportes/doctoRecibido/{folioDoctor}")
	public ResponseEntity<StatusMessage> obtenerInfoMailDoctor(@PathVariable long folioDoctor)throws ProquifaNetException{
		StatusMessage mensaje = new StatusMessage();
		try {			
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("OK");
			mensaje.setCurrent(reportesService.getMailInfoDoctor(folioDoctor));
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
}
