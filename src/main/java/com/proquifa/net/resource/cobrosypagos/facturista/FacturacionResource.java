package com.proquifa.net.resource.cobrosypagos.facturista;


import java.util.List;

import com.proquifa.net.modelo.comun.facturaElectronica.*;
import com.proquifa.net.negocio.comun.impl.CartaPorteServiceImpl;
import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;
import org.asynchttpclient.Request;
import org.asynchttpclient.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proquifa.net.modelo.cobrosypagos.facturista.Factura;
import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.StatusMessage;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.negocio.cobrosypagos.facturista.FacturacionService;
import com.proquifa.net.negocio.comun.FacturaElectronicaService;

import TurboPac.ITurboPacWs;
import TurboPac.ResultadoConsulta;
import TurboPac.TurboPacWs;

@RestController
@CrossOrigin
public class FacturacionResource {

	final Logger log = LoggerFactory.getLogger(FacturacionResource.class);

	@Autowired
	FacturacionService facturacionService;

	@Autowired
	FacturaElectronicaService facturaElectronicaService;
	@Autowired
	CartaPorteServiceImpl
			cartaPorteService;


	@PostMapping("/listarFacturasEmitidas")
	public ResponseEntity<StatusMessage> listarFacturasEmitidas(@RequestBody Parametro parametro){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("Fecha inicio:");
			log.info("",parametro.getFinicio());
			log.info("Fecha Fin:");
			log.info("",parametro.getFfin());
			log.info("ID cliente:");
			log.info("",parametro.getIdCliente());
			log.info("Fpor:");
			log.info(parametro.getFpor());
			log.info("Estado:");
			log.info(parametro.getEstado());
			log.info("Dentro sistema:");
			log.info("",parametro.getDentroSistema());
			log.info("Factura:");
			log.info("",parametro.getFactura());
			log.info("Cobrador:");
			log.info("",parametro.getCobrador());
			log.info("Uuid:");
			log.info(parametro.getUuid());
			log.info("id usuario loguado:");
			log.info("",parametro.getIdUsuarioLogueado());
			List<Factura> lstFactura = facturacionService.listarFacturasEmitidas(parametro.getFinicio(),
			 parametro.getFfin() ,parametro.getIdCliente(),parametro.getFpor(),parametro.getEstado(),parametro.getDentroSistema(),
			 parametro.getFactura(),parametro.getIdUsuarioLogueado(),parametro.getCobrador(), parametro.getUuid());
			 mensaje.setCurrent(lstFactura);

			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}

	@PostMapping("/timbrarSap")
	public ResponseEntity<StatusMessage> timbrarSap(@RequestBody FacturaElectronica facturaElectronica){
		log.info("Si llega");
		StatusMessage mensaje = new StatusMessage();
		try {
			RespuestaSap respSap = new RespuestaSap();
			int idFactura = (int) facturaElectronicaService.insertarFactura(facturaElectronica, true, false);

			//int idFactura = 1825;
			log.info("timbrarSap/idFactura: " + idFactura);
			if(idFactura > 0) {
				if (facturaElectronica.getTipoComprobante().equals("P")) {
					String jsonContent = String.valueOf(idFactura);
					AsyncHttpClient HTTP_CLIENT = Dsl.asyncHttpClient();
					Request unboundGetRequest = Dsl.post("http://localhost:8080/ProquifaNet/prueba/complementoPago").setHeader("Content-Type","application/json").setBody(jsonContent).build();

					HTTP_CLIENT.executeRequest(unboundGetRequest, new AsyncCompletionHandler<Integer>() {
						@Override
						public Integer onCompleted(Response response) {

							int resposeStatusCode = response.getStatusCode();
							return resposeStatusCode;
						}
					});
				}

				respSap = facturaElectronicaService.facturaSap(idFactura);
			} else {
				respSap = facturaElectronicaService.facturaSap(idFactura*-1);
				respSap.setFolio("Error");
			}

			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			mensaje.setCurrent(respSap);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}

	@PostMapping("/timbrarFlex1")
	public ResponseEntity<StatusMessage> timbrarFlex1(@RequestBody FacturaElectronica facturaElectronica){
		log.info("timbrarFlex1");
		StatusMessage mensaje = new StatusMessage();
		try {
			RespuestaSap respSap = new RespuestaSap();
			FacturaElectronica idFactura = (FacturaElectronica) facturaElectronicaService.insertarFactura(facturaElectronica, false, true);

			//int idFactura = 1825;
			log.info("timbrarSap/idFactura: " + idFactura.getFolio());
			/*if(idFactura > 0) {
				respSap = facturaElectronicaService.facturaSap(idFactura);
			} else {
				respSap = facturaElectronicaService.facturaSap(idFactura*-1);
				respSap.setFolio("Error");
			}*/

			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			mensaje.setCurrent(idFactura);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}

	@PostMapping("/timbrarFlex2")
	public ResponseEntity<StatusMessage> timbrarFlex2(@RequestBody FacturaElectronica facturaElectronica) {
		log.info("timbrarFlex2");
		StatusMessage mensaje = new StatusMessage();
		try {
			RespuestaSap respSap = new RespuestaSap();
			int idFactura = facturaElectronicaService.timbrarFactura(facturaElectronica);

			log.info("timbrarSap/idFactura: " + idFactura);
			if(idFactura > 0) {
				respSap = facturaElectronicaService.facturaSap(idFactura);

				List<PFacturaElectronica> partidas = (List<PFacturaElectronica>) facturaElectronica.getLstConceptos();

				facturaElectronicaService.insertarFacturaGenerada(facturaElectronica.getIdFactura(), partidas);
			} else {
				respSap = facturaElectronicaService.facturaSap(idFactura*-1);
				respSap.setFolio("Error");
			}

			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			mensaje.setCurrent(respSap);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}


	@PostMapping("/verificarTimbrado")
	public ResponseEntity<StatusMessage> verificarTimbrado(@RequestBody int idFactura){
		log.info("verificarTimbrado");
		StatusMessage mensaje = new StatusMessage();
		try {
			RespuestaSap respSap = new RespuestaSap();
				//int idFactura = 1825;
			log.info("timbrarSap/idFactura: " + idFactura);

			respSap = facturaElectronicaService.facturaSap(idFactura);

			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			mensaje.setCurrent(respSap);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}


	@PostMapping("/detalleFactura")
	public ResponseEntity<StatusMessage> pruebaFactura(@RequestBody String uuid){
		log.info("pruebaFactura---///");
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			TurboPacWs service = new TurboPacWs();
			ITurboPacWs portType = service.getBasicHttpsBindingITurboPacWs();
			//return portType.timbraCfdi33("test2", "BB8C171B2DABFFE71D0854138B759FEE25483936", xmlString);
			uuid = "782cdb31-ba33-400f-943f-349a077cf8a5";
			ResultadoConsulta r = portType.obtenerStatusUuid(Funcion.USUARIO_TURBOPAC, Funcion.CONTRASENA_TURBOPAC, uuid);

			mensaje.setCurrent(r.getComprobante());
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println ("entró a catch");
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}

	@PostMapping("/cancelarFactura")
	public ResponseEntity<StatusMessage> cancelarFactura(@RequestBody String uuid){
		log.info("cancelarFactura---///");
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			/*TurboPacWs service = new TurboPacWs();
			ITurboPacWs portType = service.getBasicHttpsBindingITurboPacWs();
			RespuestaCancelacion r = portType.cancelaCfdi(Funcion.USUARIO_TURBOPAC, Funcion.CONTRASENA_TURBOPAC, uuid);
			mensaje.setCurrent(r.getStatusUuids());*/
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	/*@PostMapping("/generarCartaPorte")
	public ResponseEntity<StatusMessage> generarXMLCartaPorte(@RequestBody CartaPorte cartaPorte){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus((HttpStatus.OK.value()));
			mensaje.setMessage("OK" + cartaPorteService.generarXMLCartaPorte(cartaPorte));
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		}catch (Exception e){
			System.out.println("Error:"+e);
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}*/
@PostMapping("/obtenerPendientesMensajero")
	public ResponseEntity obtenerPendientesMensajero (){
	StatusMessage mensaje = new StatusMessage();
	try {
		mensaje.setStatus((HttpStatus.OK.value()));
		mensaje.setMessage("OK");
	//	List <Object> carta = cartaPorteService.obtenerPendientesMensajero();
		mensaje.setCurrent(cartaPorteService.obtenerPendientesMensajero());
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
	}catch (Exception e){
		System.out.println("Error:"+e);
	}
	mensaje.setStatus(HttpStatus.FORBIDDEN.value());
	mensaje.setMessage("Error");
	return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);

	}
@PostMapping("/obtenerInfoMensajero")
	public ResponseEntity obtenerInfoMensajero(){
	StatusMessage mensaje = new StatusMessage();
		try{
			mensaje.setStatus((HttpStatus.OK.value()));
			mensaje.setMessage("OK");
			mensaje.setCurrent(cartaPorteService.obtenerInfoMensajero());
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		}catch(Exception e){
			System.out.println("Ocurrio un error:"+e);
		}
	mensaje.setStatus(HttpStatus.FORBIDDEN.value());
	mensaje.setMessage("Error");
	return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
}
	@PostMapping("/obtenerVehiculos")
	public ResponseEntity obtenerVehiculos(){
		StatusMessage mensaje = new StatusMessage();
		try{
			mensaje.setStatus((HttpStatus.OK.value()));
			mensaje.setMessage("OK");
			mensaje.setCurrent(cartaPorteService.obtenerVehiculos());
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		}catch(Exception e){
			System.out.println("Ocurrio un error:"+e);
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	@PostMapping("/obtenerClientesPorMensajero")
	public ResponseEntity<StatusMessage> obtenerClientesPorMensajero(@RequestBody String mensajero){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus((HttpStatus.OK.value()));
			mensaje.setMessage("OK" );
			mensaje.setCurrent(cartaPorteService.obtenerClientesPorMensajero(mensajero));
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		}catch (Exception e){
			System.out.println("Error:"+e);
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	@PostMapping("/pruebaGenerarCartaPortePDF")
	public ResponseEntity<StatusMessage>pruebaGenerarCartaPortePDF(){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus((HttpStatus.OK.value()));
			mensaje.setMessage("OK" );
			mensaje.setCurrent(cartaPorteService.pruebasFacturas());
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		}catch (Exception e){
			System.out.println("Error:"+e);
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	@PostMapping("/obtenerProductosXPackingList")
	public ResponseEntity<StatusMessage> obtenerProductosXPackingList(@RequestBody String packingList){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus((HttpStatus.OK.value()));
			mensaje.setMessage("OK" );
			mensaje.setCurrent(cartaPorteService.obtenerProductosXPackingList(packingList));
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		}catch (Exception e){
			System.out.println("Error:"+e);
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}

	@PostMapping("/timbrarCartaPorte")
	public ResponseEntity<StatusMessage> timbrarCartaPorte(@RequestBody FacturaElectronica fe){
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus((HttpStatus.OK.value()));
			mensaje.setMessage("OK" );
			mensaje.setCurrent(cartaPorteService.insertarFacturaElectronicaCartaPorte(fe,false,true,fe.getIdVehiculo(),fe.getIdMensajero(), fe.getResponsable()));
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);

		}catch (Exception e){
			e.printStackTrace();
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	@PostMapping("/nombre")
	public ResponseEntity<StatusMessage> nombreCliente(@RequestBody String cifra){
		StatusMessage mensaje = new StatusMessage();
		FileXML fileXML = new FileXML();
		try {
			mensaje.setStatus((HttpStatus.OK.value()));
			mensaje.setMessage("OK" );
			//mensaje.setCurrent(cartaPorteService.generarCodigo());
			mensaje.setCurrent(cartaPorteService.actualizarValoresConsecutivamente());
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);

		}catch (Exception e){
			e.printStackTrace();
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}

	@PostMapping("/obtenerPFacturas")
	public ResponseEntity<StatusMessage>obtenerPartidasFElectronica(@RequestBody int FElectronica){
		StatusMessage mensaje = new StatusMessage();

		try {
			mensaje.setStatus((HttpStatus.OK.value()));
			mensaje.setMessage("OK" );
			mensaje.setCurrent(cartaPorteService.obtenerPartidasFElectronica(FElectronica));
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);

		}catch (Exception e){
			e.printStackTrace();
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
}


