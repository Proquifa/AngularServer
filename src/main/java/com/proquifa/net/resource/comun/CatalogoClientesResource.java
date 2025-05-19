package com.proquifa.net.resource.comun;

import java.util.ArrayList;
import java.util.HashMap;
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

import com.proquifa.net.modelo.comun.CarteraCliente;
import com.proquifa.net.modelo.comun.CatalogoItem;
import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.comun.GrupoCarterasDeClientes;
import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.StatusMessage;
import com.proquifa.net.modelo.comun.ValorCombo;
import com.proquifa.net.negocio.catalogos.CatalogoClientesService;
import com.proquifa.net.negocio.comun.CatalogoService;
import com.proquifa.net.negocio.comun.EmpleadoService;

@RestController
@CrossOrigin
public class CatalogoClientesResource {
	
	final Logger log = LoggerFactory.getLogger(CatalogoClientesResource.class);
	
	@Autowired
	CatalogoClientesService catalogoClienteService;
	
	@Autowired
	CatalogoService catalogoService;
	
	@Autowired
	EmpleadoService empleadoService;

	@PostMapping("/obtenerCarterasYClientes")
	public ResponseEntity<StatusMessage> obtenerCarterasYClientes(@RequestBody Parametro parametros){
		StatusMessage mensaje = new StatusMessage();
		try {
			log.info("idFuncion:");
			log.info("",parametros.getIdFuncion());
			
			log.info("idResponsable:");
			log.info("",parametros.getIdResponsable());

			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			List<CarteraCliente> lstCarterasCliente = catalogoClienteService.obtenerCarterasyClientes(parametros.getIdFuncion(), parametros.getIdResponsable());
			mensaje.setCurrent(lstCarterasCliente);

			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);

		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/obtenerCarterasPorColaborador")
	public ResponseEntity<StatusMessage> obtenerCarterasPorColaborador(@RequestBody Parametro parametros){
		StatusMessage mensaje = new StatusMessage();
		try {
			log.info("idFuncion:");
			log.info("",parametros.getIdFuncion());
			
			log.info("idResponsable:");
			log.info("",parametros.getIdResponsable());

			log.info("idCartera:");
			log.info("",parametros.getIdCartera());
			
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			List<CarteraCliente> lstCarterasCliente = catalogoClienteService.obtenerCarterasporColaborador(parametros.getIdFuncion(), parametros.getIdResponsable(), parametros.getIdCartera());
			
			/*	 Código para agrupación en 
				@Override
				public int compare(CarteraCliente o1, CarteraCliente o2) {
					// TODO Auto-generated method stub
					return o1.getIdCartera().compareTo(o2.getIdCartera());
				}
			});
			
			int posIni = 0;
			List<GrupoCarterasDeClientes> gruposCarteras = new ArrayList<GrupoCarterasDeClientes>();
			GrupoCarterasDeClientes grupoCartera = new GrupoCarterasDeClientes();
			log.info(lstCarterasCliente.size());
			for(int i = 0; i < lstCarterasCliente.size()-1; i++){
				if(lstCarterasCliente.get(i).getIdCartera() == lstCarterasCliente.get(i+1).getIdCartera()){
					log.info(grupoCartera.getFacturacionActual());
					log.info(lstCarterasCliente.get(i).getCli_facturacionAct());
					grupoCartera.setFacturacionActual(0d);
					grupoCartera.setFacturacionAnterior(grupoCartera.getFacturacionAnterior() + lstCarterasCliente.get(i).getCli_facturacionAnt());
					grupoCartera.setObjetivoFundamental(grupoCartera.getObjetivoFundamental() + lstCarterasCliente.get(i).getCli_objetivoFundamental());
					grupoCartera.setObjetivoDeseado(grupoCartera.getObjetivoDeseado() + lstCarterasCliente.get(i).getCli_objetivoDeseado());
					grupoCartera.setProyeccionVenta(grupoCartera.getProyeccionVenta() + lstCarterasCliente.get(i).getCli_proyeccionVenta());
					grupoCartera.setDebe(grupoCartera.getDebe() + lstCarterasCliente.get(i).getCli_deben());
					grupoCartera.setDebemos(grupoCartera.getDebemos() + lstCarterasCliente.get(i).getCli_debemos());
					//falta promedioFacturación, ESAC, EVT, EV, COBRADOR, ELABORÓ, ESTRELLA, TRIANGULO, NOMBRECORPORATIVO, FOLIO
				} else {
					List<CarteraCliente> subLista = lstCarterasCliente.subList(posIni, i+1);
					grupoCartera.setClientes(subLista);
					grupoCartera.setNombreCartera(lstCarterasCliente.get(i).getNombreCarteras());
					grupoCartera.setNombreCorporativo(lstCarterasCliente.get(i).getNombreCorporativo());
					grupoCartera.setFolio(lstCarterasCliente.get(i).getFolio());
					gruposCarteras.add(grupoCartera);
					grupoCartera = new GrupoCarterasDeClientes();
					posIni = i + 1;
				}
			}
			
			
			mensaje.setCurrent(gruposCarteras);
			*/
			mensaje.setCurrent(lstCarterasCliente);

			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	@PostMapping("/actualizarCartera")
	public ResponseEntity<StatusMessage> actualizarCartera(@RequestBody Parametro parametros){
		StatusMessage mensaje = new StatusMessage();
		try {
			log.info("idUsuario:");
			log.info("",parametros.getIdUsuario());
			
			log.info("idCartera:");
			log.info("",parametros.getCartera().getIdcartera());
			
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			Long longValue = catalogoClienteService.ActualizarCartera(parametros.getCartera(), parametros.getIdUsuario());
			mensaje.setCurrent(longValue);

			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);

		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/eliminarCartera")
	public ResponseEntity<StatusMessage> eliminarCartera(@RequestBody Parametro parametros){
		StatusMessage mensaje = new StatusMessage();
		try {
			log.info("idUsuario:");
			log.info("",parametros.getIdUsuario());
			
			log.info("idCartera:");
			log.info("",parametros.getIdCartera());
			
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			Boolean result = catalogoClienteService.EliminarCartera(parametros.getIdCartera(), parametros.getIdUsuario());
			mensaje.setCurrent(result);

			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);

		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/obtenerClientesSinCartera")
	public ResponseEntity<StatusMessage> obtenerClientesSinCartera(@RequestBody Parametro parametros){
		StatusMessage mensaje = new StatusMessage();
		try{
			log.info("sinCartera:");
			log.info("",parametros.getSinCartera());
			
			log.info("area:");
			log.info(parametros.getArea());
			
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			
			List<CarteraCliente> clientesSinCartera = catalogoClienteService.obtenerClientesSinCartera(parametros.getSinCartera(), parametros.getArea());
			mensaje.setCurrent(clientesSinCartera);
			
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		}catch ( Exception e){
			
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/obtenerClientesXUsuario")
	public ResponseEntity<StatusMessage> obtenerClientesXUsuario(@RequestBody Parametro parametros){
		StatusMessage mensaje = new StatusMessage();
		try{
			log.info("Empleado:");
			if(parametros.getEmpleado() != null){
				log.info(parametros.getEmpleado().getUsuario());
			} else {
				log.info("",parametros.getEmpleado());
			}
			
			log.info("Habilitado:");
			log.info("",parametros.getHabilitado());
			
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			
			List<Cliente> listaClientes = catalogoClienteService.obtenerClientesXUsuario(parametros.getEmpleado(), parametros.getHabilitado());
			mensaje.setCurrent(listaClientes);
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		}catch (Exception e){
			
		}
		
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/obtenerComboEsacNombreCartera")
	public ResponseEntity<StatusMessage> obtenerComboEsacNombreCartera(@RequestBody Parametro parametros){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			
			List<ValorCombo> listaCombos = catalogoClienteService.obtenerComboEsacNombreCartera();
			mensaje.setCurrent(listaCombos);
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		}catch (Exception e){
			
		}
		
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/obtenerCombosNuevaCartera")
	public ResponseEntity<StatusMessage> obtenerCombosNuevaCartera(@RequestBody Parametro parametros){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			
			List<ValorCombo> listaCombos = catalogoClienteService.obtenerComboEsacNombreCartera();
			List<CatalogoItem> lstCatalogosItems = catalogoService.obtenerEmpleadosPorTipoTablero("ev", "");
			List<Empleado> lstEvt = empleadoService.obtenerEmpleadosPorTipo("evt");
			List<Empleado> lstCobradores = empleadoService.obtenerEmpleadosPorTipo("contabilidad");
			List<Empleado> lstMensajeros = empleadoService.obtenerMensajeros();
			for(Empleado e : lstEvt) {
				ValorCombo vc = new ValorCombo();
				vc.setConcepto(e.getUsuario());
				vc.setValor(e.getClave()+"");
				vc.setTipo("evt");
				listaCombos.add(vc);
			}
			for(Empleado e : lstCobradores) {
				ValorCombo vc = new ValorCombo();
				vc.setConcepto(e.getUsuario());
				vc.setValor(e.getClave()+"");
				vc.setTipo("cobrador");
				listaCombos.add(vc);
			}
			for(Empleado e: lstMensajeros) {
				ValorCombo vc = new ValorCombo();
				vc.setConcepto(e.getUsuario());
				vc.setValor(e.getClave()+"");
				vc.setTipo("msj");
				listaCombos.add(vc);
			}
			for(CatalogoItem ci: lstCatalogosItems) {
				ValorCombo vc = new ValorCombo();
				vc.setConcepto(ci.getValor());
				vc.setValor(ci.getLlave()+"");
				vc.setTipo("ev");
				listaCombos.add(vc);
			}
			mensaje.setCurrent(listaCombos);
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		}catch (Exception e){
			
		}
		
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	@PostMapping("/obtenerCarteras")
	public ResponseEntity<StatusMessage> obtenerCarteras(@RequestBody Parametro parametros){
		StatusMessage mensaje = new StatusMessage();
		try {
			log.info("idFuncion:");
			log.info("",parametros.getIdFuncion());
			
			log.info("idResponsable:");
			log.info("",parametros.getIdResponsable());

			log.info("idCartera:");
			log.info("",parametros.getIdCartera());
			
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			List<CarteraCliente> lstCarterasCliente = catalogoClienteService.obtenerCarterasporColaborador(parametros.getIdFuncion(), parametros.getIdResponsable(), parametros.getIdCartera());
			
			List<GrupoCarterasDeClientes> gruposCarteras = new ArrayList<GrupoCarterasDeClientes>();
			List<Long> listaIdCarteras = new ArrayList<>();
			GrupoCarterasDeClientes grupoCartera;
			log.info("",lstCarterasCliente.size());
			for(int i = 0; i < lstCarterasCliente.size(); i++){
				int index = listaIdCarteras.indexOf(lstCarterasCliente.get(i).getIdCartera());
				if(index == -1){
					grupoCartera = new GrupoCarterasDeClientes();
					grupoCartera.setIdCartera(lstCarterasCliente.get(i).getIdCartera());
					grupoCartera.setNombreCartera(lstCarterasCliente.get(i).getCart_nombre());
					grupoCartera.setFolio(lstCarterasCliente.get(i).getFolio());
					grupoCartera.setFacturacionActual(0d);
					grupoCartera.setEsac(lstCarterasCliente.get(i).getCart_nombreEsac());
					grupoCartera.setEv(lstCarterasCliente.get(i).getCart_nombreEv());
					grupoCartera.setCobrador(lstCarterasCliente.get(i).getCart_nombreCobrador());
					//falta promedioFacturación, ESAC, EVT, EV, COBRADOR, ELABORÓ, ESTRELLA, TRIANGULO, NOMBRECORPORATIVO, FOLIO
					grupoCartera.setNumeroClientes(1);
					gruposCarteras.add(grupoCartera);
					listaIdCarteras.add(lstCarterasCliente.get(i).getIdCartera());
				} else {
					grupoCartera = gruposCarteras.get(index);
					grupoCartera.setNumeroClientes(grupoCartera.getNumeroClientes()+1);
				}
			}
			
			
			mensaje.setCurrent(gruposCarteras);
			//mensaje.setCurrent(lstCarterasCliente);

			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/moverClienteDeCartera")
	public ResponseEntity<StatusMessage> moverClienteDeCartera(@RequestBody Parametro parametros){
		StatusMessage mensaje = new StatusMessage();
		try {
			log.info("idCliente:");
			log.info("",parametros.getIdCliente());
			
			log.info("idCartera:");
			log.info("",parametros.getIdCartera());

			log.info("idCartera anterior:");
			log.info("",parametros.getIdCarteraAnt());
			
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			boolean carteraBorrada = false;
			boolean clienteFueMovido = catalogoClienteService.actualizarClienteCartera(parametros.getIdCliente(), parametros.getIdCartera(), parametros.getIdCarteraAnt());
			List<CarteraCliente> lstCarterasCliente = catalogoClienteService.obtenerCarterasporColaborador(0l, 0l, parametros.getIdCarteraAnt());
			if(lstCarterasCliente.isEmpty()) {
				log.info("========== Se elimina cartera vacía ===========");
				carteraBorrada = catalogoClienteService.EliminarCartera(parametros.getIdCarteraAnt(), 0l);
			}
			HashMap<String, Boolean> respuestaMap = new HashMap<>();
			respuestaMap.put("moverCliente", clienteFueMovido);
			respuestaMap.put("carteraBorrada", carteraBorrada);
			mensaje.setCurrent(respuestaMap);
			//mensaje.setCurrent(lstCarterasCliente);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
}
