package com.proquifa.net.resource.catalogos;

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

import com.proquifa.net.modelo.comun.Campana;
import com.proquifa.net.modelo.comun.Empresa;
import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.Producto;
import com.proquifa.net.modelo.comun.StatusMessage;
import com.proquifa.net.negocio.catalogos.CatalogoProveedorService;

@RestController
@CrossOrigin
public class CatalogoProveedorResource {
	
	final Logger log = LoggerFactory.getLogger(CatalogoProveedorResource.class);
	
	@Autowired
	CatalogoProveedorService catalogoProveedorService;
	
	@PostMapping("/obtenerListaEmpresasParaCotizar")
	public ResponseEntity<StatusMessage> obtenerListaEmpresasParaCotizar(){
		log.info("on obtenerListaEmpresasParaCotizar");
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			List<Empresa> list = catalogoProveedorService.obtenerListaEmpresasParaCotizar();
			mensaje.setCurrent(list);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/obtenerCampanasConTodosSusProductos")
	public ResponseEntity<StatusMessage> obtenerCampanasConTodosSusProductos(@RequestBody Parametro parametro){
		log.info("on obtenerCampanasConTodosSusProductos");
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			List<Campana> list = catalogoProveedorService.obtenerCampanasConTodosSusProductos(parametro.getIdCliente(), parametro.getNivelCliente());
			mensaje.setCurrent(list);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			log.info(e.toString());
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/obtenerProductosConCampana")
	public ResponseEntity<StatusMessage> obtenerProductosConCampana(@RequestBody Parametro parametro){
		log.info("on obtenerProductosConCampana");
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			List<Producto> list = catalogoProveedorService.obtenerProductosConCampana(parametro.getIdCliente(), parametro.getNivelCliente());
			mensaje.setCurrent(list);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			log.info(e.toString());
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}

}
