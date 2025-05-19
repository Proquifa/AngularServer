package com.proquifa.net.resource.ventas;

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

import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.StatusMessage;
import com.proquifa.net.modelo.comun.TiempoProceso;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.ventas.Cotizacion;
import com.proquifa.net.modelo.ventas.reportes.seguimientos.HistorialPartidaEnSeguimiento;
import com.proquifa.net.modelo.ventas.reportes.seguimientos.PartidaCotizacionEnSeguimiento;
import com.proquifa.net.modelo.ventas.reportes.seguimientos.SeguimientoCotizacion;
import com.proquifa.net.negocio.compras.CompraService;
import com.proquifa.net.negocio.consultas.ConsultaCobrosService;
import com.proquifa.net.negocio.reportes.SeguimientoCotizacionesService;
import com.proquifa.net.negocio.ventas.CotizacionService;
import com.proquifa.net.negocio.ventas.PartidaCotizacionService;

@RestController
@CrossOrigin
public class CotizacionResource {
	
	final Logger log = LoggerFactory.getLogger(CotizacionResource.class);
	
	@Autowired
	CompraService comprasService; 
	
	@Autowired 
	CotizacionService cotizacionService;
	
	@Autowired
	ConsultaCobrosService consultaCobroService;
	
	@Autowired
	PartidaCotizacionService partidaCotizacionService;
	
	@Autowired
	SeguimientoCotizacionesService seguimientoService;
	
	/**
	 * Metodo que recupera la cotizacion por folio
	 * @param folio
	 * @return
	 * @throws 
	 */
	@PostMapping("/buscarCotizacionPorFolio")
	public ResponseEntity<StatusMessage> buscarCotizacionPorFolio(@RequestBody Parametro parametro){
		log.info("on buscarCotizacionPorFolio");
		StatusMessage mensaje = new StatusMessage();
		
		log.info("Foilio");
		log.info(parametro.getFolio());
		
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			List<Cotizacion> listaCompra = cotizacionService.buscarCotizacionPorFolio(parametro.getFolio());
						mensaje.setCurrent(listaCompra);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	/**
	 *Metodo que recupera un listado de cotizaciones por ciertos parametros.
	 * @throws 
	 */
	
	@PostMapping("/buscarCotizacionesParaReporte")
	public ResponseEntity<StatusMessage> buscarCotizacionesParaReporte(@RequestBody Parametro parametro){
		
		//Date finicio, Date ffin, Cotizacion cotizacion, Long idEmpleado
		log.info("on buscarCotizacionesParaReporte");
		
		log.info("cotizacion");
		log.info(parametro.getCotizacion().toString());
		
		log.info("estado Cotizacion");
		log.info(parametro.getCotizacion().getEstado());
		log.info("cliente Coti");
		log.info(parametro.getCotizacion().getNombreCliente());
		
		log.info("cotizo Coti");
		log.info(parametro.getCotizacion().getCotizo());
		
		log.info("mSalida Coti");
		log.info(parametro.getCotizacion().getMSalida());
		
		
		log.info("fFin");
		log.info("",parametro.getFfin());
		log.info("f Inicio");
		log.info("",parametro.getFinicio());
		log.info("idEmpleado");
		log.info("",parametro.getIdEmpleado());

		
		
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			List<Cotizacion> listaCompra = cotizacionService.buscarCotizacionesParaReporte(parametro.getFinicio(), parametro.getFfin(), parametro.getCotizacion(), parametro.getIdEmpleado() );
						mensaje.setCurrent(listaCompra);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	/***
	 * Recupera un listado con las partidas de una cotizacion por su folio
	 * @param FolioCotizacion
	 * @return
	 */
	@PostMapping("/obtenerPartidasPorCotizacionEnSeguimiento")
	public ResponseEntity<StatusMessage> obtenerPartidasPorCotizacionEnSeguimiento(@RequestBody Parametro parametro){
		log.info("on buscarCotizacionPorFolio");
		StatusMessage mensaje = new StatusMessage();
		
		log.info("Foilio");
		log.info(parametro.getFolio());
		
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			List<PartidaCotizacionEnSeguimiento> ListaPartidasCotizacionXfolioCotizacion = seguimientoService.obtenerPartidasPorCotizacionEnSeguimiento(parametro.getFolio());
						mensaje.setCurrent(ListaPartidasCotizacionXfolioCotizacion);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	/***
	 * 
	 * @param idPCotiza
	 * @return
	 * @throws ProquifaNetException
	 */
	@PostMapping("/obtenerHistorialSeguimientoXPartida")
	public ResponseEntity<StatusMessage> obtenerHistorialSeguimientoXPartida(@RequestBody Parametro parametro){
		log.info("obtenerHistorialSeguimientoXPartida");
		StatusMessage mensaje = new StatusMessage();
		
		log.info("IdPCotiza");
		log.info("",parametro.getIdpcotiza());
		
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			List<HistorialPartidaEnSeguimiento> ListaPartidasCotizacionXfolioCotizacion = seguimientoService.obtenerHistorialSeguimientoXPartida(parametro.getIdpcotiza());
						mensaje.setCurrent(ListaPartidasCotizacionXfolioCotizacion);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	/***
	 * 
	 * @param confirmacion
	 * @param Cliente
	 * @param Empleado
	 * @param Tipo
	 * @param Marca
	 * @param Control
	 * @return
	 * @throws ProquifaNetException
	 */

	@PostMapping("/obtenerCotizacionesEnSeguimientoEnPartidas")
	public ResponseEntity<StatusMessage> obtenerCotizacionesEnSeguimientoEnPartidas(@RequestBody Parametro parametro){
		
		
		log.info("obtenerCotizacionesEnSeguimientoEnPartidas");
		StatusMessage mensaje = new StatusMessage();
		
		log.info("confirmacion");
		log.info("",parametro.getConfirmacion());
		log.info("Cliente");
		log.info(parametro.getCliente());
		log.info("Empleado");
		log.info(parametro.getEmpleadoString());
		log.info("Tipo");
		log.info(parametro.getTipo());
		log.info("Marca");
		log.info(parametro.getMarca());
		log.info("Control");
		log.info(parametro.getControl());
		log.info("Master");
		log.info("",parametro.getMaster());
		
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			List<SeguimientoCotizacion> ListaCotizacionesEnSeguimientoEnPartidas = seguimientoService.obtenerCotizacionesEnSeguimientoEnPartidas(parametro.getConfirmacion(), parametro.getCliente(), parametro.getEmpleadoString(), parametro.getTipo(), parametro.getMarca(), parametro.getControl(), parametro.getMaster());
						mensaje.setCurrent(ListaCotizacionesEnSeguimientoEnPartidas);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	/**
	 * Determinar el tiempo de proceso de una partida de cotizacion
	 * @param Partida Cotizacion
	 * @return
	 * @throws ProquifaNetException
	 */

	@PostMapping("/tiempoProcesoPCotiza")
	public ResponseEntity<StatusMessage> tiempoProcesoPCotiza(@RequestBody Parametro parametro){
		log.info("tiempoProcesoPCotiza");
		StatusMessage mensaje = new StatusMessage();
		
		log.info("IdPCotiza");
		log.info("",parametro.getIdpcotiza());
		
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			List<TiempoProceso> ListaTiempoProceso = partidaCotizacionService.tiempoProcesoPCotiza(parametro.getIdpcotiza());
						mensaje.setCurrent(ListaTiempoProceso);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
}