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

import com.proquifa.net.modelo.compras.PermisoImportacion;
import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.ResumenPermisoImportacion;
import com.proquifa.net.modelo.comun.StatusMessage;
import com.proquifa.net.modelo.comun.TiempoProceso;
import com.proquifa.net.negocio.consultas.ConsultaPermisoImportacionService;

@RestController
@CrossOrigin
public class ConsultaPermisoImportacionResource {
	
	final Logger log = LoggerFactory.getLogger(ConsultaPermisoImportacionResource.class);
	
	@Autowired
	ConsultaPermisoImportacionService consultaPermisoImportacionService;
	
	///Aqui
	@PostMapping("/consultaPermisos")
	public ResponseEntity<StatusMessage>consultaPermisos(@RequestBody Parametro parametro){
		log.info("on consultaPermisos");
		StatusMessage mensaje = new StatusMessage();
			try {
				mensaje.setStatus(HttpStatus.OK.value());
				mensaje.setMessage("ok");
				
				log.info("Fecha inicio:");
				log.info("",parametro.getFinicio());
				log.info("Fecha fin:");
				log.info("",parametro.getFfin());
				log.info("Provedor:");
				log.info("",parametro.getproveedor());
				log.info("Catalogo:");
				log.info(parametro.getCatalogo());
				log.info("Tipo Permiso:");
				log.info(parametro.getTipoPermiso());
				log.info("Tipo Produccion:");
				log.info(parametro.getTipoProducto());
				log.info("SubTipo:");
				log.info(parametro.getSubTipo());
				log.info("Clasificacion:");
				log.info(parametro.getClasificacion());
				log.info("Control:");
				log.info(parametro.getControl());
				log.info("Estado Fisico:");
				log.info(parametro.getEstadoFisico());
				log.info("Estado:");
				log.info(parametro.getEstado());
				
				List<PermisoImportacion> lstPermisosImportacion = consultaPermisoImportacionService.consultaPermisos(
								parametro.getFinicio(),					
								parametro.getFfin(),
								parametro.getproveedor(), 
								parametro.getCatalogo(),
								parametro.getTipoPermiso(),
								parametro.getTipoProducto(),
								parametro.getSubTipo(),
								parametro.getClasificacion(),
								parametro.getControl(),
								parametro.getEstadoFisico(),
								parametro.getEstado());
				
				for(PermisoImportacion  item : lstPermisosImportacion) {
					log.info("/*-------------------------------*/");
					log.info("",item.getCosto());
				}
				log.info("/*-------------------------------*/");
				mensaje.setCurrent(lstPermisosImportacion);
				return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
			}catch(Exception e) {
				// TODO: handle exception
			}
			mensaje.setStatus(HttpStatus.FORBIDDEN.value());
			mensaje.setMessage("Error");
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
		}
	
	//Aqui

	@PostMapping("/consultaPermisosBusquedaRapida")
	public ResponseEntity<StatusMessage> consultaPermisosBusquedaRapida(@RequestBody Parametro parametro){
		log.info("on consultaPermisosBusquedaRapida");
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("Catalogo:");
			log.info(parametro.getCatalogo());
			List<PermisoImportacion> lstPermisosImportacionRapida = consultaPermisoImportacionService.consultaPermisosBusquedaRapida(
					parametro.getCatalogo());

			mensaje.setCurrent(lstPermisosImportacionRapida);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	
	//Aqui
	
	@PostMapping("/obtenerLineaTiempo")
	public ResponseEntity<StatusMessage> obtenerLineaTiempo(@RequestBody Parametro parametro){
		log.info("on obtenerLineaTiempo");
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("Id Producto:");
			log.info("",parametro.getIdProducto());
			List<TiempoProceso> lstPermisosImportacionLineaTiempo = consultaPermisoImportacionService.obtenerLineaTiempo(
					parametro.getIdProducto());

			mensaje.setCurrent(lstPermisosImportacionLineaTiempo);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	
	
	@PostMapping("/resumenPrevioTramitar")
	public ResponseEntity<StatusMessage> resumenPrevioTramitar(@RequestBody Parametro parametro){
		log.info("on obtenerLineaTiempo");
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("Id Producto:");
			log.info("",parametro.getIdProducto());
			List<ResumenPermisoImportacion> lstPermisosImportacionResumenPrevioTramitar = consultaPermisoImportacionService.resumenPrevioTramitar(
					parametro.getIdProducto());

			mensaje.setCurrent(lstPermisosImportacionResumenPrevioTramitar);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	@PostMapping("/resumenTramitarPermiso")
	public ResponseEntity<StatusMessage> resumenTramitarPermiso(@RequestBody Parametro parametro){
		log.info("on obtenerLineaTiempo");
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("Id Producto:");
			log.info("",parametro.getIdProducto());
			List<ResumenPermisoImportacion> lstPermisosImportacionResumenTramitar = consultaPermisoImportacionService.resumenTramitarPermiso(
					parametro.getIdProducto());

			mensaje.setCurrent(lstPermisosImportacionResumenTramitar);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	@PostMapping("/resumenEnAutorizacion")
	public ResponseEntity<StatusMessage> resumenEnAutorizacion(@RequestBody Parametro parametro){
		log.info("on obtenerLineaTiempo");
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("Id Producto:");
			log.info("",parametro.getIdProducto());
			List<ResumenPermisoImportacion> lstPermisosImportacionResumenEnAutorizacion = consultaPermisoImportacionService.resumenTramitarPermiso(
					parametro.getIdProducto());

			mensaje.setCurrent(lstPermisosImportacionResumenEnAutorizacion);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	
	
	@PostMapping("/resumenMonitorearResumen")
	public ResponseEntity<StatusMessage> resumenMonitorearResumen(@RequestBody Parametro parametro){
		log.info("on obtenerLineaTiempo");
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("Id Producto:");
			log.info("",parametro.getIdProducto());
			List<ResumenPermisoImportacion> lstPermisosImportacionMonitorearResumen = consultaPermisoImportacionService.resumenMonitorearResumen(
					parametro.getIdProducto());

			mensaje.setCurrent(lstPermisosImportacionMonitorearResumen);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	@PostMapping("/resumenPermiso")
	public ResponseEntity<StatusMessage> resumenPermiso(@RequestBody Parametro parametro){
		log.info("on obtenerLineaTiempo");
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("Id Producto:");
			log.info("",parametro.getIdProducto());
			List<ResumenPermisoImportacion> lstPermisosImportacionResumenPermiso = consultaPermisoImportacionService.resumenPermiso(
					parametro.getIdProducto());

			mensaje.setCurrent(lstPermisosImportacionResumenPermiso);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	
	//Aqui
	@PostMapping("/obtenerDetallesGrafica")
	public ResponseEntity<StatusMessage> obtenerDetallesGrafica(@RequestBody Parametro parametro){
		log.info("on obtenerLineaTiempo");
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("Fecha inicio:");
			log.info("",parametro.getFinicio());
			log.info("Fecha fin:");
			log.info("",parametro.getFfin());
			log.info("Provedor:");
			log.info("",parametro.getproveedor());
			log.info("Catalogo:");
			log.info(parametro.getCatalogo());
			log.info("Tipo Permiso:");
			log.info(parametro.getTipoPermiso());
			log.info("Tipo Produccion:");
			log.info(parametro.getTipoProducto());
			log.info("SubTipo:");
			log.info(parametro.getSubTipo());
			log.info("Clasificacion:");
			log.info(parametro.getClasificacion());
			log.info("Control:");
			log.info(parametro.getControl());
			log.info("Estado Fisico:");
			log.info(parametro.getEstadoFisico());
			log.info("Estado:");
			log.info(parametro.getEstado());
			
			List<PermisoImportacion> lstPermisosImportacionResumenPermiso =
					consultaPermisoImportacionService.obtenerDetallesGrafica(
					parametro.getFinicio(),					
					parametro.getFfin(),
					parametro.getproveedor(), 
					parametro.getCatalogo(),
					parametro.getTipoPermiso(),
					parametro.getTipoProducto(),
					parametro.getSubTipo(),
					parametro.getClasificacion(),
					parametro.getControl(),
					parametro.getEstadoFisico(),
					parametro.getEstado(),
					parametro.getIndividual());
		
			mensaje.setCurrent(lstPermisosImportacionResumenPermiso);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
}
