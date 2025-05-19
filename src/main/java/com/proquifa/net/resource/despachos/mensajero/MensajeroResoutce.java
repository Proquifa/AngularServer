package com.proquifa.net.resource.despachos.mensajero;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;
import javax.ws.rs.HeaderParam;

import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.codec.Base64;
import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.StatusMessage;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.despachos.mensajero.AsignarMensajero;
import com.proquifa.net.modelo.despachos.mensajero.ColectarMensajero;
import com.proquifa.net.modelo.despachos.mensajero.PendientesMensajero;
import com.proquifa.net.modelo.despachos.mensajero.PersonalAlmacenCliente;
import com.proquifa.net.modelo.despachos.mensajero.Recorrido;
import com.proquifa.net.modelo.despachos.mensajero.Ruta;
import com.proquifa.net.modelo.ventas.admoncomunicacion.Correo;
import com.proquifa.net.negocio.despachos.mensajero.MensajeroService;
import springfox.documentation.spring.web.json.Json;

import com.fasterxml.jackson.databind.ObjectMapper;
@RestController
@CrossOrigin
public class MensajeroResoutce {
	@Autowired
	MensajeroService mensajeroService;

	@Autowired
	private JavaMailSender sender;
	
	final Logger log = LoggerFactory.getLogger(MensajeroResoutce.class);

	@PostMapping("/consultarPendientesDeMensajero")
	public ResponseEntity<StatusMessage> consultarPendientesDeMensajero(@RequestBody Parametro parametro) {
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("responsable:");
			log.info(parametro.getValor());
			List<PendientesMensajero> lstResumenRevision = mensajeroService
					.consultarPendientesDeMensajero(parametro.getValor() + "");
			mensaje.setCurrent(lstResumenRevision);
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.FORBIDDEN);
	}

	@PostMapping("/consultarPendientesEnCierre")
	public ResponseEntity<StatusMessage> consultarPendientesEnCierre(@RequestBody Parametro parametro) {
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("responsable:");
			log.info(parametro.getValor());
			List<PendientesMensajero> lstResumenRevision = mensajeroService
					.consultarPendientesEnCierre(parametro.getValor() + "");
			mensaje.setCurrent(lstResumenRevision);
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.FORBIDDEN);
	}

	@PostMapping("/listarPendientesCerrados")
	public ResponseEntity<StatusMessage> listarPendientesCerrados(@RequestBody Parametro parametro) {
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("responsable:");
			log.info(parametro.getValor());
			List<PendientesMensajero> lstResumenRevision = mensajeroService
					.listarPendientesCerrados(parametro.getValor() + "");
			mensaje.setCurrent(lstResumenRevision);
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.FORBIDDEN);
	}

	@PostMapping("/listarPendientesEstadistica")
	public ResponseEntity<StatusMessage> listarPendientesEstadistica(@RequestBody Parametro parametro) {
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("responsable:");
			log.info(parametro.getValor());
			List<PendientesMensajero> lstResumenRevision = mensajeroService
					.listarPendientesEstadistica(parametro.getValor() + "");
			mensaje.setCurrent(lstResumenRevision);
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.FORBIDDEN);
	}

	@PostMapping("/ejecutarRutaMensajero")
	public ResponseEntity<StatusMessage> ejecutarRutaMensajero(@RequestBody Parametro parametro) {
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("responsable:");
			log.info(parametro.getValor());

			log.info("Pendiente:");
			log.info(parametro.getPendiente().toString());
			Boolean respuesta = mensajeroService.ejecutarRutaMensajero(parametro.getPendiente(),
					parametro.getIdUsuario() + "");
			mensaje.setCurrent(respuesta);
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.FORBIDDEN);
	}


	@PostMapping("/validarCoordenadasGPS")
	public ResponseEntity<StatusMessage> validarCoordenadasGPS(@RequestBody Parametro parametro) {
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("responsable:");
			log.info(parametro.getValor());

			log.info("Pendientes:");
			log.info(parametro.toString());
			Boolean respuesta = mensajeroService.validarCoordenadasGPS(parametro.getPendientes());
			mensaje.setCurrent(respuesta);
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.FORBIDDEN);
	}

	@PostMapping("/concluirEjecucionDeRuta")
	public ResponseEntity<StatusMessage> concluirEjecucionDeRuta(@RequestBody Parametro parametro) {
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("responsable:");
			log.info("",parametro.getIdUsuario());
			Boolean respuesta = mensajeroService.concluirEjecucionDeRuta(parametro.getPendientes(),
					parametro.getIdUsuario() + "");
			mensaje.setCurrent(respuesta);
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.FORBIDDEN);
	}

	@PostMapping("/actualizarPersonalAlmacenCliente")
	public ResponseEntity<StatusMessage> actualizarPersonalAlmacenCliente(@RequestBody Parametro parametro) {
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("responsable:");
			log.info(parametro.getValor());
			Boolean respuesta = mensajeroService.actualizarPersonalAlmacenCliente(parametro.getPersonal());
			mensaje.setCurrent(respuesta);
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.FORBIDDEN);
	}

	/***
	 * Obtiene los contactos
	 * 
	 * @param idCliente
	 * @return
	 * @throws ProquifaNetException
	 */

	@PostMapping("/obtenerPersonalAlmacenCliente")
	public ResponseEntity<StatusMessage> obtenerPersonalAlmacenCliente(@RequestBody Parametro parametro) {
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("responsable:");
			log.info(parametro.getValor());
			List<PersonalAlmacenCliente> lstPersonal = mensajeroService
					.obtenerPersonalAlmacenCliente(parametro.getIdCliente());
			mensaje.setCurrent(lstPersonal);
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.FORBIDDEN);
	}

	@PostMapping("/EnviarCorreo")
	public ResponseEntity<StatusMessage> EnivarCorreo(@RequestBody String correo) {
		StatusMessage mensaje = new StatusMessage();
		log.info("En el servicio de correo service");
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			// sendEmail();

			mensaje.setCurrent("Se envio el correo");
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.FORBIDDEN);
	}

	public JavaMailSender getSender() {

		return sender;
	}

	public void sendEmail(Correo correo) throws Exception {

		log.info("En el servicio de correo metodo");
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setTo("josephat94@gmail.com");
		// helper.addAttachment( correo.getArchivoAdjunto(), );
		File file = new File("/Users/josephat.reyes/desktop/" + correo.getArchivoAdjunto() + ".pdf");
		helper.addAttachment(correo.getArchivoAdjunto() + ".pdf", new ByteArrayResource(getBytesFromFile(file)));
		helper.setText(
				"<html> <body><table width='100%' align='center' cellpadding='4' style='font-family:Avenir; font-size:14px; font-weight:lighter' border='1' bordercolor='#FFFFFF' > <tr align='center' style='border-bottom-color:#cccccc; '>    <td colspan='2'  ><img src='cid:Proquifa'  /></td>  </tr>  <tr >    <td colspan='2' align='center' style='font-size:22px;'>N O T I F I C A D O  &nbsp; D E &nbsp;  E N T R E G A </td>  </tr>  <tr valign='middle'>    <td colspan='2' align='center' bgcolor='#007E92' style='color:#FFFFFF; font-size:18px'>CLIENTE </td>  </tr>  <tr style='color:#8B8B8B'>    <td colspan='2' align='center' style='font-size:18px' > OLNATURA </td>  </tr>  <tr>    <td colspan='2' align='center' bgcolor='#007E92'  style='color:#FFFFFF; font-size:18px'>CONTACTO</td>  </tr>  <tr>    <td colspan='2' align='center' style='color:#007E92;font-size:18px'>LIC ERIKA REGALADO</td>  </tr>  <tr style='padding:0'>    <td height='1' colspan='2' bgcolor='#177E91' ></td>  </tr>  <tr style='padding:0'>    <td width='35%' valign='top' style='padding:0'><br /><table width='100%' border='0' align='center' cellpadding='4'>      <tr  style='color:#8B8B8B; font-size:17px'>        <td align='center'>FOLIO:</td>      </tr>      <tr style='font-family:Helvetica; font-size:17px; font-weight:100'>        <td align='center'>NE-072518-68</td>      </tr>      <tr style='color:#8B8B8B;font-size:17px'>        <td align='center'>ORDEN DE COMPRA:</td>      </tr>      <tr style='font-family:Helvetica; font-size:17px; font-weight:100;'>        <td align='center'>264<br /></td>      </tr>      <tr style='color:#8B8B8B;font-size:17px'>        <td align='center'>REPORTE DE PIEZAS:</td>      </tr>      <tr style='font-family:Helvetica; font-size:17px; font-weight:100;'>        <td align='center'>1<br /></td>      </tr>      <tr style='color:#8B8B8B;font-size:17px'>        <td align='center'>REFERENCIA:</td>      </tr>      <tr style='font-family:Helvetica; font-size:17px; font-weight:100;'>        <td align='center'>030716-4203<br /></td>      </tr>      <tr style='color:#8B8B8B;font-size:17px'>        <td align='center'>FACTURA:</td>      </tr>      <tr style='font-family:Helvetica; font-size:17px; font-weight:100;'>        <td align='center'>38919<br /></td>      </tr>      <tr style='color:#8B8B8B;font-size:17px'>        <td align='center'>ENTREGADO</td>      </tr>      <tr style='font-family:Helvetica; font-size:17px; font-weight:100;'>        <td align='center'>25-07-2018<br /></td>      </tr>      <tr style='color:#8B8B8B;font-size:17px'>        <td align='center'>RECIBI&Oacute;:</td>      </tr>      <tr style='font-family:Helvetica; font-size:17px; font-weight:100;'>        <td align='center'>Veronica colin garcia</td>      </tr>    </table></td>    <td valign='top' style='border-left-color:#cccccc; padding-left:0; padding-top:0; padding-bottom:0; margin:0'>    <table width='100%' border='0' cellpadding='8' cellspacing='0' style='font-size:10px; '>      <tr >        <td  colspan='7' style=' font-size:16px' valign='top'>RELACI&Oacute;N DE PRODUCTOS</td>        </tr>      <tr bgcolor='#007E92' style='color:#FFFFFF; font-size:9px; font-weight:900'>        <td width='4%' align='center'>#</td>        <td width='10%' align='center'>Cantidad</td>        <td width='33%'>Descripci&oacute;n</td>        <td width='17%'>Documentos</td>        <td width='16%' align='center'>FEE</td>        <td width='16%' align='center'>D&iacute;as de atraso</td>        <td width='4%' align='center'>&nbsp;</td>      </tr>      <tr>        <td align='center'>1</td>        <td align='center'>1</td>        <td>ASB-00004130-010 Deoxyactein, 27-(P) (10 mg) CHROMADEX</td>	 <td><table><tr><td><img src='http://201.161.12.60:51725/SAP/Imagenes/descargar.png' style='width: 15px; opacity: 0.5;' /></td><td style='font-size: 10px; font-family: Avenir; color: #8B8B8B;'>Sin documentos relacionados</td></tr></table></td>        <td align='center'>25-10-2017</td>        <td align='center'>195</td>        <td align='center'  style='font-family:; font-size:24px; color:#DC061B'>&bull;</td>      </tr>     </table></td>  </tr>  <tr>    <td colspan='2' ><table width='100%' border='1' bordercolor='#FFFFFF' cellpadding='0' cellspacing='0' style='font-size:9px;font-family: Tahoma, Geneva, sans-serif' >      <tr  style='color:#FFFFFF;font-weight:900; border-color:#177E91; '>        <td colspan='3' align='center' bgcolor='#177E91'>          <table width='100%' border='0' cellspacing='2' cellpadding='0' style='font-family: Tahoma, Geneva, sans-serif; font-size:11px'>            <tr bordercolor='#FFFFFF'  style='color:#FFFFFF;font-weight:900; border-color:#177E91'>              <td width='33%' align='center' bgcolor='#177E91'>En Tiempo</td>              <td width='33%' align='center' bgcolor='#177E91'>Fuera de Tiempo</td>              <td width='33%' align='center' bgcolor='#177E91'>Efectividad</td>            </tr>          </table></td>        </tr>      <tr style='font-family:Helvetica; font-size:11px' >        <td width='33%' align='center' >  0 piezas  </td>        <td width='34%' align='center'> 1 pieza</td>        <td width='33%' align='center' style='font-weight:900'>0%</td>      </tr>      <tr style='border-bottom-color:#cccccc;'>        <td colspan='3' align='center' style='color:#8B8B8B; padding:0'> <img src='cid:Marcas' width='800' /> </td>        </tr>    </table></td>  </tr>  <tr>  <td colspan='2' style='font-size:10px; '><font color='#177E91'>*</font> Para ver detalle consulte archivo adjunto. <br /> <img src='cid:Archivo_0' />  </td>  </tr>  <tr style=' padding:0; margin:0' >  <td colspan='2' align='center' style='font-size:18px' > <hr color='#177E91' /> GRACIAS POR SU PREFERENCIA <hr color='#177E91' />  </td>  </tr>  <tr style=' padding:0; margin:0' >    <td colspan='2' align='center' style='font-size:8px; color:#177E91; font-weight:600; font-family:Tahoma, Geneva, sans-serif; letter-spacing:1' > Contacto: Ciudad de M&eacute;xico (55 13151498) Guadalajara (01 33 30700302) <br /> Interior (01 800 6811440) Resto del mundo (ventas@proquifa.com.mx)</td>  </tr></table></body> </html>",
				true);
		helper.setSubject("Hi");
		sender.send(message);
	}

	@SuppressWarnings("resource")
	public byte[] getBytesFromFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);

		long length = file.length();

		if (length > Long.MAX_VALUE) {
			// File is too large
		}

		// Create the byte array to hold the data
		byte[] bytes = new byte[(int) length];

		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}

		// Ensure all the bytes have been read in
		if (offset < bytes.length) {
			throw new IOException("Could not completely read file " + file.getName());
		}

		// Close the input stream and return bytes
		is.close();
		return bytes;
	}

	@PostMapping("/asignarMensajero/datosGrafica")
	public ResponseEntity<StatusMessage> asignarMensajero(@RequestBody Integer idUsuario) {
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("OK");

			mensaje.setCurrent(mensajeroService.findPendientesMensajero(idUsuario));
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}

		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.FORBIDDEN);

	}

	@PostMapping("/asignarMensajero/detalle")
	public ResponseEntity<StatusMessage> obtenerDetalle(@RequestBody Integer idUsuario) {
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("OK");

			mensaje.setCurrent(mensajeroService.obtenerDatosDetalleAsignarMensajero(idUsuario, null));
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}

		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.FORBIDDEN);

	}

	@PutMapping("/asignarMensajero/guardar")
	public ResponseEntity<StatusMessage> guardarAsignarMensajero(@RequestBody List<AsignarMensajero> rutas) {
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("OK");

			mensaje.setCurrent(mensajeroService.guardarAsignarMensajero(rutas));
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}

		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.FORBIDDEN);

	}

	@PutMapping("/asignarMensajero/publicar")
	public ResponseEntity<StatusMessage> publicarAsignarMensajero(@RequestBody List<List<AsignarMensajero>> rutas) {
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("OK");

			mensaje.setCurrent(mensajeroService.publicarRuta(rutas));
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}

		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.FORBIDDEN);

	}

	@PostMapping("/cerrarRuta/datosGrafica")
	public ResponseEntity<StatusMessage> obtenerDatosCerrarRuta(@RequestBody Integer idUsuario) {
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("OK");

			mensaje.setCurrent(mensajeroService.obtenerDatosVistaInicial(idUsuario));
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}

		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.FORBIDDEN);

	}

	@PostMapping("/cerrarRuta/compararRuta")
	public ResponseEntity<StatusMessage> obtenerComparacionRuta(@RequestBody Integer idMensajero) {
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("OK");

			mensaje.setCurrent(mensajeroService.obtenerComparacionRuta(idMensajero));
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}

		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.FORBIDDEN);

	}

	@PutMapping("/cerrarRuta/calificarRuta")
	public ResponseEntity<StatusMessage> guardarRuraCalificada(@RequestBody Ruta ruta) {
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("OK");

			mensaje.setCurrent(mensajeroService.guardarCerrarRuta(ruta));
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}

		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.FORBIDDEN);

	}
	// end points de la app movil 


	@PostMapping("/consultarPendientesDeMensajeroPL")
	public ResponseEntity<StatusMessage> consultarPendientesDeMensajeroPL(@RequestBody Parametro parametro) {
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("-------------consultarPendientesDeMensajeroPL-------------");
			log.info("responsable:");
			log.info(parametro.getValor());
			List<PendientesMensajero> lstResumenRevision = mensajeroService
					.consultarPendientesDeMensajeroPL(parametro.getValor() + "", parametro.getEstado());
			mensaje.setCurrent(lstResumenRevision);
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.FORBIDDEN);
	}




	@PostMapping("/consultarPendientesEnCierrePL")
	public ResponseEntity<StatusMessage> consultarPendientesEnCierrePL(@RequestBody Parametro parametro) {
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("responsable:");
			log.info(parametro.getValor());
			List<PendientesMensajero> lstResumenRevision = mensajeroService
					.consultarPendientesEnCierrePL(parametro.getValor() + "");
			mensaje.setCurrent(lstResumenRevision);
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.FORBIDDEN);
	}


	@PostMapping("/listarPendientesCerradosPL")
	public ResponseEntity<StatusMessage> listarPendientesCerradosPL(@RequestBody Parametro parametro) {
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("responsable:");
			log.info(parametro.getValor());
			List<PendientesMensajero> lstResumenRevision = mensajeroService
					.listarPendientesCerradosPL(parametro.getValor() + "");
			mensaje.setCurrent(lstResumenRevision);
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.FORBIDDEN);
	}


	@PostMapping("/ejecutarRutaMensajeroPL")
	public ResponseEntity<StatusMessage> ejecutarRutaMensajeroPL(@RequestBody List<PendientesMensajero> pendiente) {
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			Boolean respuesta = mensajeroService.ejecutarRutaMensajeroPL(pendiente);
			mensaje.setCurrent(respuesta);
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.FORBIDDEN);
	}

	@PostMapping("/concluirEjecucionDeRutaPL")
	public ResponseEntity<StatusMessage> concluirEjecucionDeRutaPL(@RequestBody Parametro parametro) {
		System.out.println("JSON DE concluirEjecucionDeRutaPL: "+parametro);
		StatusMessage mensaje = new StatusMessage();
		System.out.println("Peticion servicio concluirEjecucionDeRutaPL");
		System.out.println(parametro.getPendientes()+", "+parametro.getIdUsuario()+", "+parametro.getValor());
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("responsable:");
			log.info(parametro.getValor());
			Boolean respuesta = mensajeroService.concluirEjecucionDeRutaPL(parametro.getPendientes(),
					parametro.getIdUsuario() + "", parametro.getValor());
			mensaje.setCurrent(respuesta);
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.FORBIDDEN);
	}

	@PostMapping("/guardarComentariosRutaDP")
	public ResponseEntity<StatusMessage> guardarComentariosRutaDP(@RequestBody Parametro parametro) {
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("responsable:");
			log.info(parametro.getValor());
			Boolean respuesta = mensajeroService.guardarComentariosRutaDP(parametro.getComentaiosRutaDP());
			mensaje.setCurrent(respuesta);
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.FORBIDDEN);
	}

	@PostMapping("/colectarMensajero")
	public ResponseEntity<StatusMessage> colectarMensajero(@RequestBody Parametro parametro) {
		StatusMessage mensaje = new StatusMessage();
		try {			
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			List <ColectarMensajero>  lista =  mensajeroService.colectarMensajeroPL(parametro);
			mensaje.setCurrent(lista);
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.FORBIDDEN);
	}



	// De aqui es para guardar el pdf

	@PostMapping("/guardarFirma")
	public ResponseEntity<StatusMessage> recibirIMG(@HeaderParam("x-auth-token") String token, @RequestBody Map<String, Object> map)
			throws ProquifaNetException, IOException, DocumentException, InterruptedException {
		StatusMessage mensaje = new StatusMessage();
		try {			
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			log.info("En el servicio de recibirIMG service");
			//log.info(">" + map);
			//System.out.print("contenido de data 1 " + map.get("data1").toString());

			// String dir = "/Users/Shared/Documentacion/" + map.get("path").toString();
			String dir = "/mnt/SAP/Documentacion/" + map.get("path").toString();
			List<String> stockList = (List<String>) map.values().toArray()[3];

			String[] lstString = new String[stockList.size()];
			lstString = (String[]) stockList.toArray(lstString);


			File carpeta = new File(dir);
			if (!carpeta.exists())
				carpeta.mkdirs();


			String t = map.get("nombre").toString();
			mensajeroService.saveImgArribo((byte[]) Base64.decode(map.get("data1").toString()), t + ".png", dir, lstString, map.get("tipo").toString());

			mensaje.setCurrent("");
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		} catch (Exception e) {
			log.info(e.toString());
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.FORBIDDEN);
	}

	@PostMapping("/guardarFotos")
	public ResponseEntity<StatusMessage> recibirIMGS(@RequestBody Parametro param)
			throws ProquifaNetException, IOException, DocumentException, InterruptedException {

		StatusMessage mensaje = new StatusMessage();
		try {		
			//				String dir = "/Users/david.garcia/Desktop/Repositorio/PROQUIFANET/CO/BACK-END_ANGULAR/ProquifaNet/Reportes";
			// String dir = "/Users/Shared/Documentacion/" + param.getValor();
			String dir = "/mnt/SAP/Documentacion/" + param.getValor();
			File carpeta = new File(dir);
			if (!carpeta.exists())
				carpeta.mkdirs();
			String t = param.getArchivos()[0].toString();

			log.info("En el servicio de recibirIMGS service");
			log.info("Evidencia: " + param.getEvidencia());
			//log.info(param.getFotos());
			int n = param.getFotos().size();
			String folio = mensajeroService.saveDocumento3(param.getArchivos()[0],param.getTipo());

			for (int i = 0; i < param.getFotos().size(); i++) {
				mensajeroService.saveImgArribo((byte[]) Base64.decode(param.getFotos().get(i).toString()), folio + "-" + i , dir, param.getArchivos(), param.getTipo());
			}

			//Thread.sleep(1000);
			mensajeroService.saveFotosPDF(folio, n, dir+"/");
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		} catch (Exception e) {
			log.info(e.toString());
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.FORBIDDEN);

	}

	@PostMapping("/insertarRecorrido")
	public ResponseEntity<StatusMessage> insertarRecorrido(@RequestBody Recorrido recorrido){
		StatusMessage mensaje = new StatusMessage();
		try {	
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("responsable:");
			log.info(recorrido.getIdRuta());
			mensaje.setCurrent(mensajeroService.insertarRecorrido(recorrido));
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		} catch (Exception e) {
			log.info(e.toString());
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.FORBIDDEN);
	}

	@PostMapping("/asignarMensajero/obtenerFacturasFolio")
	public ResponseEntity<StatusMessage> obtenerFacturasFolio(@RequestBody String[] folios){
		StatusMessage mensaje = new StatusMessage();
		try {	
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			mensaje.setCurrent(mensajeroService.obtenerFacturasFolio(folios));
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		}catch (Exception e) {
			log.info(e.toString());
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.FORBIDDEN);
	}
	// hassta a aqui es para guardar el pdf
	
	@PostMapping("mensajero/obtenerTotales")
	public ResponseEntity<StatusMessage> obtenerTotales(@RequestBody Integer idResponsable) {
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			mensaje.setCurrent(mensajeroService.obtenerTotales(idResponsable));
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.FORBIDDEN);
	}

}
