package com.proquifa.net.resource.comun;

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

import com.proquifa.net.modelo.comun.CatalogoItem;
import com.proquifa.net.modelo.comun.NominaCatalogo;
import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.StatusMessage;
import com.proquifa.net.modelo.comun.Usuario;
import com.proquifa.net.modelo.consultas.comun.ParametrosBusquedaCobros;
import com.proquifa.net.negocio.comun.CatalogoService;

@RestController
@CrossOrigin
public class CatalogoResource {
	@Autowired
	CatalogoService catalogoService;
	
	final Logger log = LoggerFactory.getLogger(CatalogoResource.class);

	@PostMapping("/obtenerClientesPorIdUsuarioRol")
	public ResponseEntity<StatusMessage> obtenerClientesPorIdUsuarioRol(@RequestBody Usuario usuario){
		StatusMessage mensaje = new StatusMessage();
		try {
			log.info("idUsuario:");
			log.info("",usuario.getIdUsuario());

			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			List<CatalogoItem> lstClientes = catalogoService.obtenerClientesPorIdUsuarioRol(new Long(usuario.getIdUsuario()));
			mensaje.setCurrent(lstClientes);

			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);

		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/obtenerBancosDeClientes")
	public ResponseEntity<StatusMessage> obtenerBancosClientes(@RequestBody ParametrosBusquedaCobros parametros){
		StatusMessage mensaje = new StatusMessage();
		try {
			log.info("fpor:");
			log.info(parametros.getFpor());

			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			List<CatalogoItem> lstCatalogosItems = catalogoService.obtenerBancosClientes(parametros.getFpor());
			mensaje.setCurrent(lstCatalogosItems);

			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);

		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/obtenerFabricantes")
	public ResponseEntity<StatusMessage> obtenerFabricantes(@RequestBody Parametro parametros){
		StatusMessage mensaje = new StatusMessage();
		try {
			log.info("Valor Adicional:");
			log.info(parametros.getValorAdicional());

			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			List<CatalogoItem> lstCatalogosItems = catalogoService.obtenerFabricantes(parametros.getValorAdicional());
			mensaje.setCurrent(lstCatalogosItems);

			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);

		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	@PostMapping("/obtenerCuentasBancoDeClientes")
	public ResponseEntity<StatusMessage> obtenerCuentasBancoClientes(@RequestBody ParametrosBusquedaCobros parametros){
		StatusMessage mensaje = new StatusMessage();
		try {
			log.info("fpor:");
			log.info(parametros.getFpor());
			
			log.info("banco:");
			log.info(parametros.getBanco());

			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			List<CatalogoItem> lstCatalogosItems = catalogoService.obtenerCuentasBancoClientes(parametros.getBanco(), parametros.getFpor());
			mensaje.setCurrent(lstCatalogosItems);

			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);

		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	
	
	@PostMapping("/obtenerProveedores")
	public ResponseEntity<StatusMessage> obtenerProveedores(@RequestBody Parametro parametro){
		StatusMessage mensaje = new StatusMessage();
		try {
			log.info("ValorAdicional:");
			log.info(parametro.getValorAdicional());
			

			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			List<CatalogoItem> lstCatalogos = catalogoService.obtenerProveedores(parametro.getValorAdicional());
			mensaje.setCurrent(lstCatalogos);

			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);

		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}

	@PostMapping("/obtenerEmpleadosPorTipoTablero")
	public ResponseEntity<StatusMessage> obtenerEmpleadosPorTipoTablero(@RequestBody Parametro parametros){
		StatusMessage mensaje = new StatusMessage();
		try {
			log.info("tipo:");
			log.info(parametros.getValor());
			
			log.info("valor adicional:");
			log.info(parametros.getValorAdicional());

			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			List<CatalogoItem> lstCatalogosItems = catalogoService.obtenerEmpleadosPorTipoTablero(parametros.getValor(), parametros.getValorAdicional());
			mensaje.setCurrent(lstCatalogosItems);

			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);

		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/obtenerEmpresasContabilidad")
	public ResponseEntity<StatusMessage> obtenerEmpresasContabilidad(){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			List<CatalogoItem> lstCatalogosItems = catalogoService.obtenerEmpresasContabilidad();
			mensaje.setCurrent(lstCatalogosItems);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		}catch(Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/obtenerNominaCatalogo")
	public ResponseEntity<StatusMessage> obtenerNominaCatalogo(@RequestBody Parametro parametros){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			String tipo = parametros.getTipo();
			List<NominaCatalogo> lst = catalogoService.obtenerNominaCatalogo(tipo);
			mensaje.setCurrent(lst);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
}
