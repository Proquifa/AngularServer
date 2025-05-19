package com.proquifa.net.negocio.despachos.mensajero.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import com.proquifa.net.modelo.comun.Documento;
import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.comun.Folio;
import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.Pendiente;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.comun.util.GenerarPDF;
import com.proquifa.net.modelo.despachos.mensajero.AsignarMensajero;
import com.proquifa.net.modelo.despachos.mensajero.ColectarMensajero;
import com.proquifa.net.modelo.despachos.mensajero.ComentaiosRutaDP;
import com.proquifa.net.modelo.despachos.mensajero.ConfirmacionEntrega;
import com.proquifa.net.modelo.despachos.mensajero.PendientesMensajero;
import com.proquifa.net.modelo.despachos.mensajero.PersonalAlmacenCliente;
import com.proquifa.net.modelo.despachos.mensajero.Recorrido;
import com.proquifa.net.modelo.despachos.mensajero.Ruta;
import com.proquifa.net.modelo.despachos.mensajero.TotalMensajero;
import com.proquifa.net.modelo.ventas.admoncomunicacion.Correo;
import com.proquifa.net.negocio.despachos.EmbalarService;
import com.proquifa.net.negocio.despachos.mensajero.MensajeroService;
import com.proquifa.net.persistencia.comun.EmpleadoDAO;
import com.proquifa.net.persistencia.comun.FolioDAO;
import com.proquifa.net.persistencia.comun.FuncionDAO;
import com.proquifa.net.persistencia.comun.PendienteDAO;
import com.proquifa.net.persistencia.despachos.mensajero.MensajeroDAO;
import com.proquifa.net.persistencia.despachos.mensajero.RutaDAO;
import com.proquifa.net.resource.despachos.mensajero.MensajeroResoutce;

@Service("mensajeroService")
public class MensajeroServiceImpl implements MensajeroService {
	@Autowired
	MensajeroDAO mensajeroDAO;
	@Autowired
	EmpleadoDAO empleadoDAO;
	@Autowired
	FolioDAO folioDAO;

	@Autowired
	EmbalarService embalarService;

	@Autowired
	RutaDAO rutaDAO;

	@Autowired
	PendienteDAO pendienteDAO;

	@Autowired
	FuncionDAO funcionDAO;

	final Logger log = LoggerFactory.getLogger(MensajeroServiceImpl.class);


	@Autowired
	private JavaMailSenderImpl sender;
	Funcion funcion;
	static final String RUTA_CONFIRMACION = Funcion.RUTA_DOCUMENTOS + "ConfirmacionEntrega/";
	//static final String RUTA_CONFIRMACION = "/Users/david.garcia/Desktop/";
	static final String RUTA_FIRMAS = Funcion.RUTA_FIRMAS;
	//static final String RUTA_FIRMAS = "/Users/david.garcia/Desktop/Firmas_SAC/";
	//static final String HOST = "mail.proquifa.com.mx";
	static final String HOST = "mail.proquifa.net";
	//public static final String DOMINIO = "@proquifa.com.mx";
	public static final String DOMINIO = "@proquifa.net";

		public List<PendientesMensajero> consultarPendientesDeMensajero(String responsable) throws ProquifaNetException {

		try {
			String tipoPendiente = "Ruta a ejecutar";
			List<String> roles = empleadoDAO.getRolesEmpleado(responsable);

			for (String r : roles) {
				if (r.equals("Mensajero_Urgencias")) {
					tipoPendiente = "Ruta a ejecutar Urgente";
				}
			}

			List<PendientesMensajero> pendientes = mensajeroDAO
					.findPendientesDeRutas(responsable, tipoPendiente);

			for (PendientesMensajero pm : pendientes) {
				log.info("PM"+pm.toString());
				int dif = 0;
				if (pm.getDiferenteDireccion() != null
						&& pm.getDiferenteDireccion() == true) {
					dif = 1;
				}
				List<Documento> doctos = consultarDocumentosDeRuta(responsable,
						pm.getEvento(), pm.getIdCliente(), dif,
						pm.getFolioEvento());
				if (doctos.isEmpty() || doctos == null) {
					doctos = new ArrayList<Documento>();
				}
				pm.setDocumentos(doctos);
			}
			return pendientes;
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			log.info(e.getMessage());
			return null;// TODO: handle exception
		}
	}

	public List<PendientesMensajero> consultarPendientesDeMensajeroPL(

			String responsable, String estadoRuta) throws ProquifaNetException {

		try {
			String tipoPendiente = "Ruta a ejecutar";
			//			List<String> roles = empleadoDAO.getRolesEmpleado(responsable);
			//
			//			for (String r : roles) {
			//				if (r.equals("Mensajero_Urgencias")) {
			//					tipoPendiente = "Ruta a ejecutar Urgente";
			//				}
			//			}
			Empleado empleado = this.empleadoDAO.obtenerEmpleadoPorUsuario(responsable);
			List<PendientesMensajero> pendientes = mensajeroDAO
					.findPendientesDeRutasPL(empleado, tipoPendiente, estadoRuta);

			for (PendientesMensajero pm : pendientes) {
				log.info("PM"+pm.toString());
				int dif = 0;
				if (pm.getDiferenteDireccion() != null
						&& pm.getDiferenteDireccion() == true) {
					dif = 1;
				}
				List<Documento> doctos = consultarDocumentosDeRuta(responsable,
						pm.getEvento(), pm.getIdCliente(), dif,
						pm.getFolioEvento());
				if (doctos.isEmpty() || doctos == null) {
					doctos = new ArrayList<Documento>();
				}
				pm.setDocumentos(doctos);
			}
			return pendientes;
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			log.info(e.getMessage());
			return null;// TODO: handle exception
		}
	}

	@Override
	public List<ColectarMensajero> colectarMensajeroPL(Parametro parametro ) throws ProquifaNetException {
		try {
			String tipoPendiente = "Ruta a ejecutar";
			//						List<String> roles = empleadoDAO.getRolesEmpleado(responsable);
			//			
			//						for (String r : roles) {
			//							if (r.equals("Mensajero_Urgencias")) {
			//								tipoPendiente = "Ruta a ejecutar Urgente";
			//							}
			//						}
			List<ColectarMensajero> pendientes = mensajeroDAO.colectarMensajero(parametro);
			return pendientes;
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			log.info(e.getMessage());
			return null;// TODO: handle exception
		}
	}




	public List<Documento> consultarDocumentosDeRuta(String responsable,
			String evento, Long idCliente, int diferente, String folio)
					throws ProquifaNetException {
		List<Documento> documentos = null;
		String tipoPendiente = "Ruta a ejecutar";
		List<String> roles = empleadoDAO.getRolesEmpleado(responsable);

		for (String r : roles) {
			if (r.equals("Mensajero_Urgencias")) {
				tipoPendiente = "Ruta a ejecutar Urgente";
			}
		}

		if ((evento.equals("Entrega")) || (evento.equals("Entrega especial"))) {
			documentos = mensajeroDAO.findDocumentosEntrega(idCliente,
					diferente, responsable, folio, tipoPendiente);
		} else if (evento.equals("Revision")) {
			documentos = mensajeroDAO.findDocumentosRevision(idCliente,
					diferente, responsable, folio, tipoPendiente);
		} else if (evento.equals("Cobro")) {
			documentos = mensajeroDAO.findDocumentosCobro(idCliente, diferente,
					responsable, folio, tipoPendiente);
		} else if (evento.equals("Recolección")) {
			documentos = mensajeroDAO.findDocumentosRecoleccion(idCliente,
					diferente, responsable, folio, tipoPendiente);
		} else if (evento.equals("Recoleccion especial")) {
			documentos = mensajeroDAO.findDocumentosRecoleccionEspecial(
					idCliente, diferente, responsable, folio, tipoPendiente);
		}

		return documentos;
	}

	public Boolean ejecutarRutaMensajero(PendientesMensajero pendiente,
			String usuario) throws ProquifaNetException {
		log.info(pendiente.toString());
		return mensajeroDAO.ejecutarRutaMensajero(pendiente.getEvento(),
				pendiente.getFolioEvento(), pendiente.getIdCliente(), usuario);
	}

	//	@Override
	public Boolean ejecutarRutaMensajeroPL(List<PendientesMensajero> pendiente) throws ProquifaNetException {
		boolean respuesta = true;
		for(PendientesMensajero r : pendiente) {
			if(!mensajeroDAO.ejecutarRutaMensajeroPL(r)) {
				respuesta = false;
			}
		}
		return respuesta;	
	}

	public Boolean validarCoordenadasGPS(List<PendientesMensajero> pendientes)
			throws ProquifaNetException {
		log.info("Tamaño de la lista->"+pendientes.size());
		for (PendientesMensajero p : pendientes) {

			log.info("Pendiente de la lista->"+pendientes.toString());
			boolean resultado= 	this.mensajeroDAO.updateCoordenadasGPS(p);
			log.info("Resultado->"+resultado);
		}
		return true;
	}

	public List<PendientesMensajero> consultarPendientesEnCierre(
			String responsable) throws ProquifaNetException {
		try {
			String tipoPendiente = "Ruta a ejecutar";
			List<String> roles = empleadoDAO.getRolesEmpleado(responsable);

			for (String r : roles) {
				if (r.equals("Mensajero_Urgencias")) {
					tipoPendiente = "Ruta a ejecutar Urgente";
				}
			}

			List<PendientesMensajero> pendientes = mensajeroDAO
					.findPendientesEnCierre(responsable, tipoPendiente);
			if (pendientes.isEmpty() || pendientes == null) {
				pendientes = new ArrayList<PendientesMensajero>();
			}
			return pendientes;
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			return new ArrayList<PendientesMensajero>();
		}
	}


	public List<PendientesMensajero> consultarPendientesEnCierrePL(String responsable) throws ProquifaNetException {
		try {
			String tipoPendiente = "Ruta a ejecutar";
			/*List<String> roles = empleadoDAO.getRolesEmpleado(responsable);

			for (String r : roles) {
				if (r.equals("Mensajero_Urgencias")) {
					tipoPendiente = "Ruta a ejecutar Urgente";
				}
			}*/
			Empleado empleado = this.empleadoDAO.obtenerEmpleadoPorUsuario(responsable);
			List<PendientesMensajero> pendientes = mensajeroDAO
					.findPendientesEnCierrePL(empleado, tipoPendiente);
			if (pendientes.isEmpty() || pendientes == null) {
				pendientes = new ArrayList<PendientesMensajero>();
			}
			return pendientes;
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			return new ArrayList<PendientesMensajero>();
		}
	}


	public Boolean concluirEjecucionDeRuta(
			List<PendientesMensajero> pendientesXCerrar, String Usuario)
					throws ProquifaNetException {
		String tipoPendiente = "Ruta a ejecutar";
		// String rutasDP = "(";
		List<String> roles = empleadoDAO.getRolesEmpleado(Usuario);

		for (String r : roles) {
			if (r.equals("Mensajero_Urgencias")) {
				tipoPendiente = "Ruta a ejecutar Urgente";
			}
		}
		for (PendientesMensajero pendiente : pendientesXCerrar) {
			if (pendiente.getRealizado() == null) {
				throw new ProquifaNetException(
						"Error no se especifica la realizacion del evento "
								+ pendiente.getFolioEvento());
			}
			if (!pendiente.getRealizado()) {
				if (pendiente.getTipoJustificacion() == null
						|| pendiente.getTipoJustificacion().equals("")) {
					throw new ProquifaNetException(
							"No se especificó Cliente, Mensajero o Solicitante del pendiente "
									+ pendiente.getFolioEvento());
				}
			} else {
				pendiente.setJustificacion(null);
				pendiente.setTipoJustificacion(null);
			}
			mensajeroDAO.concluirEjecucionDeRuta(pendiente, Usuario,
					tipoPendiente);

			if (!pendiente.getRealizado()) {
			} else {
				if (pendiente.getFolioEvento().contains("DP-")) {
					enviarConfirmacionesEntrega(pendiente.getFolioEvento(), false); // Confirmacion
					// de
					// Entrega
					// por
					// evento(RutaDP)
				}
			}
		}

		return true;
	}

	@Override
	public Boolean concluirEjecucionDeRutaPL(List<PendientesMensajero> pendientesXCerrar, String Usuario, String usuarioNombre)
			throws ProquifaNetException {
		System.out.println("Entra Metodo concluirEjecucionDeRutaPL");
		String tipoPendiente = "Ruta a ejecutar";
		List<String> roles = empleadoDAO.getRolesEmpleado(Usuario);

		System.out.println("tipo pendiente:"+tipoPendiente);

		for (String r : roles) {
			if (r.equals("Mensajero_Urgencias")) {
				tipoPendiente = "Ruta a ejecutar Urgente";
			}
			System.out.println(r);
		}

		for (PendientesMensajero pendiente : pendientesXCerrar) {

			System.out.println("tipo pendiente: "+ pendiente.getFolioEvento() + ", "+pendiente.getFoliosPL()+", "+pendiente.getRealizadoTxt());
			// tipo pendiente: DP-021425-9914, null, null
			// NO HAY FOLIOS PL NI REALIZADOTxT

			if (pendiente.getRealizadoTxt() == null) {
				System.out.println("Error no se especifica la realizacion del evento ");
				throw new ProquifaNetException("Error no se especifica la realizacion del evento "	+ pendiente.getFolioEvento());
			}
			if (pendiente.getRealizadoTxt().equals("No realizada")) {

				if (pendiente.getTipoJustificacion() == null
						|| pendiente.getTipoJustificacion().equals("")) {
					System.out.println("No se especificó Cliente, Mensajero o Solicitante del pendiente ");
					throw new ProquifaNetException(
							"No se especificó Cliente, Mensajero o Solicitante del pendiente "
									+ pendiente.getFolioEvento());
				}
			} else {
				pendiente.setJustificacion(null);
				pendiente.setTipoJustificacion(null);
			}
			System.out.println("ejecuta concluirEjecucionDeRutaPL del DAO,Datos:"+pendiente+", "+Usuario+", "+", "+usuarioNombre+", "+tipoPendiente);
			mensajeroDAO.concluirEjecucionDeRutaPL(pendiente, Usuario, usuarioNombre,tipoPendiente);

			Ruta ruta = new Ruta();
			ruta.setFolio(pendiente.getIdRuta());
			ruta.setIdResponsable(Integer.parseInt(Usuario));
			//rutaDAO.estadoAGenerar(ruta, pendiente.getFolioEvento());
			System.out.println("ejecuta mensajeroGDL, DATOS:"+pendiente.getIdRuta());
			String mensajero = pendienteDAO.mensajeroGDL(pendiente.getIdRuta());
			System.out.println("Ejecuta estadoAGenerar DAO, Datos:"+pendiente+", "+mensajero);
			rutaDAO.estadoAGenerar(pendiente, mensajero);

			if (pendiente.getRealizadoTxt().equals("Realizada")) {
				System.out.println("Entra If Pendiente:"+pendiente.getRealizadoTxt());
				if (pendiente.getFolioEvento().contains("DP-")) {
					System.out.println("El Pendiene contiene la cadena DP-");
					System.out.println("Ejecuta enviarConfirmacionesEntrega, Datos "+pendiente.getFolioEvento());
					enviarConfirmacionesEntrega(pendiente.getFolioEvento(), true); // Confirmacion de Entrega por evento(RutaDP)
				}
			}

			if(mensajeroDAO.finalizoRuta(pendiente.getIdRuta()) && !(mensajero.equals("MensajeroGDL") && pendiente.getRealizadoTxt().equals("No realizada"))) {
				pendienteDAO.cerrarPendiente_angular(pendiente.getIdRuta(), null, "Ruta a ejecutar");
			}
		}

		return true;
	}

	@Override
	public Boolean guardarComentariosRutaDP(List<ComentaiosRutaDP> lstComentaiosRutaDP) throws ProquifaNetException{
		try {
			for (ComentaiosRutaDP comentaiosRutaDP : lstComentaiosRutaDP) {
				mensajeroDAO.insertComentariosRutaDP(comentaiosRutaDP);
			}
			return true; 
		} catch (Exception e) {
			return false;
		}
	}

	public List<PendientesMensajero> listarPendientesCerrados(String Usuario)
			throws ProquifaNetException {
		try {
			String tipoPendiente = "Ruta a ejecutar";
			List<String> roles = empleadoDAO.getRolesEmpleado(Usuario);

			for (String r : roles) {
				if (r.equals("Mensajero_Urgencias")) {
					tipoPendiente = "Ruta a ejecutar Urgente";
				}
			}
			return mensajeroDAO.findPendientesCerrados(Usuario, tipoPendiente);
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			log.info(e.getMessage());
			return null;// TODO: handle exception
		}
	}


	public List<PendientesMensajero> listarPendientesCerradosPL(String Usuario)
			throws ProquifaNetException {
		try {
			String tipoPendiente = "Ruta a ejecutar";
			//			List<String> roles = empleadoDAO.getRolesEmpleado(Usuario);
			//
			//			for (String r : roles) {
			//				if (r.equals("Mensajero_Urgencias")) {
			//					tipoPendiente = "Ruta a ejecutar Urgente";
			//				}
			//			}
			return mensajeroDAO.findPendientesCerradosPL(Usuario, tipoPendiente);
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			log.info(e.getMessage());
			return null;// TODO: handle exception
		}
	}

	public List<PendientesMensajero> listarPendientesEstadistica(String Usuario)
			throws ProquifaNetException {
		try {
			String tipoPendiente = "Ruta a ejecutar";
			List<String> roles = empleadoDAO.getRolesEmpleado(Usuario);

			for (String r : roles) {
				if (r.equals("Mensajero_Urgencias")) {
					tipoPendiente = "Ruta a ejecutar Urgente";
				}		
			}
			List<PendientesMensajero> pendientes = mensajeroDAO
					.findPendientesDeRutas(Usuario, tipoPendiente);
			List<PendientesMensajero> estadisticas = mensajeroDAO
					.findPendientesCerrados(Usuario, tipoPendiente);
			estadisticas.addAll(pendientes);
			return estadisticas;
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			return new ArrayList<PendientesMensajero>();
		}
	}





	@Override
	public Boolean enviarConfirmacionesEntrega(String ruta, Boolean usaPL)
			throws ProquifaNetException {
		List<ConfirmacionEntrega> con = usaPL ? mensajeroDAO.obtenerInformacionEntregaPL(ruta): mensajeroDAO.obtenerInformacionEntrega(ruta);
		List<String> correosExtra = mensajeroDAO.getContactosExtrasNotificacion(ruta);
		Correo correo = new Correo();
		String cuerpo = "", productos = "";
		funcion = new Funcion();
		List<ConfirmacionEntrega> resumen = mensajeroDAO.obtenerSumaPiezasFabrica(ruta);

		int i = 0, piezas = 0, fuera = 0, dentro = 0, a = 0, diasAtraso = 0;
		for (ConfirmacionEntrega c : con) {			
			String baseCertificado = "";
			String baseHojaSeguridad="";
			String documentos = "";
			if(c.getIdFabricante() != null){
				String rutaCertificadoServidor = funcion.obtenerRutaServidor("certificados", c.getIdFabricante()+"");				
				String rutaHojaSeguridadServidor = funcion.obtenerRutaServidor("hojasseguridad", c.getIdFabricante()+"");

				if(c.getCatalogo() != null){
					rutaHojaSeguridadServidor += c.getCatalogo()+".pdf";
					if(c.getLote() != null){
						rutaCertificadoServidor += c.getCatalogo()+"-"+c.getLote()+".pdf";
						File validaCertificado = new File(rutaCertificadoServidor);
						File validaHoja = new File(rutaHojaSeguridadServidor);
						if (validaCertificado != null && validaCertificado.isFile())
							//baseCertificado = "http://proquifa.com.mx:51725/SAP/Certificados/"+c.getIdFabricante()+"/"+(c.getCatalogo()+"-"+c.getLote()+".pdf");
							baseCertificado = "http://189.203.160.185:51725/SAP/Certificados/"+c.getIdFabricante()+"/"+(c.getCatalogo()+"-"+c.getLote()+".pdf");
						if (validaHoja != null && validaHoja.isFile())
							//baseHojaSeguridad = "http://proquifa.com.mx:51725/SAP/HojasSeguridad/"+c.getIdFabricante()+"/"+(c.getCatalogo()+".pdf");
							baseHojaSeguridad = "http://189.203.160.185:51725/SAP/HojasSeguridad/"+c.getIdFabricante()+"/"+(c.getCatalogo()+".pdf");
					}
				}
			}


			if(!baseHojaSeguridad.equals("") || !baseCertificado.equals("")){
				if(!baseHojaSeguridad.equals("")){
					if(!baseCertificado.equals("")){
						documentos="<td><table>"
								+"<tr>"
								+"<td><a href='"+baseCertificado+"'><img src='http://187.189.39.50:51725/SAP/Imagenes/descargar.png' style='width: 15px;' /></a></td>"
								+"<td style='font-size: 10px; font-family: Avenir;'><a style='text-decoration: none; color: #000' href='"+baseCertificado+"'>Certificado</a></td>"
								+"</tr>"
								+"<tr style='height: 10px;'> </tr>"
								+"<tr>"
								+"<td><a href='"+baseHojaSeguridad+"'><img src='http://187.189.39.50:51725/SAP/Imagenes/descargar.png' style='width: 15px;' /></a></td>"
								+"<td style='font-size: 10px; font-family: Avenir;'><a style='text-decoration: none; color: #000' href='"+baseHojaSeguridad+"'>Hoja de seguridad</a></td>"
								+"</tr>"
								+"</table></td>";
					}else{
						documentos="<td><table>"
								+"<tr>"
								+"<td><a href='"+baseHojaSeguridad+"'><img src='http://187.189.39.50:51725/SAP/Imagenes/descargar.png' style='width: 15px;' /></a></td>"
								+"<td style='font-size: 10px; font-family: Avenir;'><a style='text-decoration: none; color: #000' href='"+baseHojaSeguridad+"'>Hoja de seguridad</a></td>"
								+"</tr></table></td>";
					}
				}else if(!baseCertificado.equals("")){
					documentos="<td><table>"
							+"<tr>"
							+"<td><a href='"+baseCertificado+"'><img src='http://187.189.39.50:51725/SAP/Imagenes/descargar.png' style='width: 15px;' /></a></td>"
							+"<td style='font-size: 10px; font-family: Avenir;'><a style='text-decoration: none; color: #000' href='"+baseCertificado+"'>Certificado</a></td>"
							+"</tr></table></td>";
				}				
			}else{
				documentos="	 <td><table>"
						+"<tr><td><img src='http://187.189.39.50:51725/SAP/Imagenes/descargar.png' style='width: 15px; opacity: 0.5;' /></td>"
						+"<td style='font-size: 10px; font-family: Avenir; color: #8B8B8B;'>Sin documentos relacionados</td>"
						+"</tr></table></td>";
			}
			i++;
			piezas += c.getCantidad();

			if (c.getDiasAtraso() > 0) {
				diasAtraso = c.getDiasAtraso();
			} else {
				diasAtraso = 0;
			}
			productos += "      <tr>"
					+ // /////------------------------------------------------------>
					// Aqui van los productos
					"        <td align='center'>" + i + "</td>"
					+ "        <td align='center'>" + c.getCantidad() + "</td>"
					+ "        <td>" + c.getProducto() + "</td>"
					+ documentos
					+ "        <td align='center'>"
					+ funcion.obtenerFormatoFecha(c.getFechaEsperadaEntrega())
					+ "</td>" + "        <td align='center'>" + diasAtraso
					+ "</td>";
			if (c.getTiempoEntrega().equals("Dentro")) {
				productos += "        <td align='center'  style='font-family:; font-size:24px; color:#009000'>&bull;</td>";
				dentro = dentro + c.getCantidad();
			} else {
				productos += "        <td align='center'  style='font-family:; font-size:24px; color:#DC061B'>&bull;</td>";
				fuera = fuera + c.getCantidad();
			}
			productos += "      </tr> "; // /////------------------------------------------------------>

		}

		if (i > 10) {
			productos = "";
			for (ConfirmacionEntrega c : resumen) {
				a++;
				productos += "      <tr>"
						+ // /////------------------------------------------------------>
						// Aqui el resumen
						"        <td align='center'>" + a + "</td>"
						+ "        <td align='center'>" + c.getCantidad()
						+ "</td>" + "        <td>" + c.getProducto() + "</td>"
						+ "      </tr> "; // /////------------------------------------------------------>
			}

		}

		int porciento = 0;
		if (dentro > 0) {
			porciento = (dentro * 100) / piezas;
		} else {
			porciento = 0;
		}

		/***
		 * Genera el PDF Se genera pdf para documentar la entrega, si son menos
		 * de 10 productos el archivo creado se envia como archivo adjunto
		 */
		Folio folio = folioDAO.obtenerFolioPorConcepto("ConfirmacionEntrega",false);

		folioDAO.actualizarValorConsecutivo("ConfirmacionEntrega",
				Long.parseLong(folio.getValor()));

		GenerarPDF pdf = new GenerarPDF(folio.getFolioCompleto(),
				"ConfirmacionEntrega");
		pdf.confirmacionEntrega(con, folio.getFolioCompleto(), piezas, fuera,
				dentro, porciento);

		mensajeroDAO.updateRutaDPfolioNE(ruta, folio.getFolioCompleto());

		correo.setAsunto("Notificado de Entrega: " + folio.getFolioCompleto());
		correo.setMedio("Correo electrónico");
		correo.setOrigen("ventas");
		correo.setTipo("confirmacionentrega");
		correo.setArchivoAdjunto(folio.getFolioCompleto());

		correo.setIdEmpleadoString(con.get(0).getVendedor());
		correo.setCorreo(con.get(0).getEmail());		
		String[] cco = new String[3];
		cco[0]= con.get(0).getVendedor() + "@proquifa.net";
		cco[1]= con.get(0).getEv() + "@proquifa.net";
		cco[2]= "oscar.cardona@ryndem.mx";
		//correo.setCocorreo(cco);
		correo.setCocorreo(cco[0] + ";"+ cco[1] + ";oscar.cardona@ryndem.mx"); // Copias Ocultas

		//		correo.setCorreo("oscar.cardona@ryndem.mx");									
		//		correo.setCocorreo("");
		if (correosExtra != null && correosExtra.size() > 0) { // Contactos
			// extras
			String correosE = correosExtra.toString();
			correosE = correosE.replace("[", "");
			correosE = correosE.replace("]", "");
			correo.setCcorreo(correosE);
		}


		/***
		 * Genera correo
		 */
		log.info("Ruta para logo de proquifa--->"+RUTA_FIRMAS+"logo.jpg");

		cuerpo = "<table width='100%' align='center' cellpadding='4' style='font-family:Avenir; font-size:14px; font-weight:lighter' border='1' bordercolor='#FFFFFF' >"
				+ " <tr align='left' style='border-bottom-color:#cccccc; '>"
				+ "    <td colspan='2' ><img src='cid:Proquifa'  /></td>"
				+ "  </tr>"
				+ "  <tr >"
				+ "    <td colspan='2' align='center' style='font-size:22px;'>N O T I F I C A D O  &nbsp; D E &nbsp;  E N T R E G A </td>"
				+ "  </tr>"
				+ "  <tr valign='middle'>"
				+ "    <td colspan='2' align='center' bgcolor='#007E92' style='color:#FFFFFF; font-size:18px'>CLIENTE </td>"
				+ "  </tr>"
				+ "  <tr style='color:#8B8B8B'>"
				+ "    <td colspan='2' align='center' style='font-size:18px' > "
				+ con.get(0).getCliente().toUpperCase()
				+ " </td>"
				+ "  </tr>"
				+ "  <tr>"
				+ "    <td colspan='2' align='center' bgcolor='#007E92'  style='color:#FFFFFF; font-size:18px'>CONTACTO</td>"
				+ "  </tr>"
				+ "  <tr>"
				+ "    <td colspan='2' align='center' style='color:#007E92;font-size:18px'>"
				+ con.get(0).getContacto().toUpperCase()
				+ "</td>"
				+ "  </tr>"
				+ "  <tr style='padding:0'>"
				+ "    <td height='1' colspan='2' bgcolor='#177E91' ></td>"
				+ "  </tr>"
				+ "  <tr style='padding:0'>"
				+ "    <td width='35%' valign='top' style='padding:0'><br /><table width='100%' border='0' align='center' cellpadding='4'>"
				+ "      <tr  style='color:#8B8B8B; font-size:17px'>"
				+ "        <td align='center'>FOLIO:</td>"
				+ "      </tr>"
				+ "      <tr style='font-family:Helvetica; font-size:17px; font-weight:100'>"
				+ "        <td align='center'>"
				+ folio.getFolioCompleto()
				+ "</td>"
				+ "      </tr>"
				+ "      <tr style='color:#8B8B8B;font-size:17px'>"
				+ "        <td align='center'>ORDEN DE COMPRA:</td>"
				+ "      </tr>"
				+ "      <tr style='font-family:Helvetica; font-size:17px; font-weight:100;'>"
				+ "        <td align='center'>"
				+ con.get(0).getPedidoCliente()
				+ "<br /></td>"
				+ "      </tr>"
				+ "      <tr style='color:#8B8B8B;font-size:17px'>"
				+ "        <td align='center'>REPORTE DE PIEZAS:</td>"
				+ "      </tr>"
				+ "      <tr style='font-family:Helvetica; font-size:17px; font-weight:100;'>"
				+ "        <td align='center'>"
				+ piezas
				+ "<br /></td>"
				+ "      </tr>"
				+ "      <tr style='color:#8B8B8B;font-size:17px'>"
				+ "        <td align='center'>REFERENCIA:</td>"
				+ "      </tr>"
				+ "      <tr style='font-family:Helvetica; font-size:17px; font-weight:100;'>"
				+ "        <td align='center'>"
				+ con.get(0).getCpedido()
				+ "<br /></td>"
				+ "      </tr>"
				+ "      <tr style='color:#8B8B8B;font-size:17px'>"
				+ "        <td align='center'>FACTURA:</td>"
				+ "      </tr>"
				+ "      <tr style='font-family:Helvetica; font-size:17px; font-weight:100;'>"
				+ "        <td align='center'>"
				+ con.get(0).getFactura()
				+ "<br /></td>"
				+ "      </tr>"
				+ "      <tr style='color:#8B8B8B;font-size:17px'>"
				+ "        <td align='center'>ENTREGADO</td>"
				+ "      </tr>"
				+ "      <tr style='font-family:Helvetica; font-size:17px; font-weight:100;'>"
				+ "        <td align='center'>"
				+ funcion.obtenerFormatoFecha(con.get(0).getFecha())
				+ "<br /></td>"
				+ "      </tr>"
				+ "      <tr style='color:#8B8B8B;font-size:17px'>"
				+ "        <td align='center'>RECIBI&Oacute;:</td>"
				+ "      </tr>"
				+ "      <tr style='font-family:Helvetica; font-size:17px; font-weight:100;'>";

		cuerpo += "        <td align='center'>"
				+ con.get(0).getPersonaRecibio() + "</td>";

		cuerpo += "      </tr>"
				+ "    </table></td>"
				+ "    <td valign='top' style='border-left-color:#cccccc; padding-left:0; padding-top:0; padding-bottom:0; margin:0'>"
				+ "    <table width='100%' border='0' cellpadding='8' cellspacing='0' style='font-size:10px; '>";

		if (i > 10) {
			cuerpo += "      <tr >"
					+ "        <td  colspan='3' style=' font-size:16px' valign='top'>RELACI&Oacute;N DE PRODUCTOS</td>"
					+ "        </tr>"
					+ "      <tr bgcolor='#007E92' style='color:#FFFFFF; font-size:9px; font-weight:900'>"
					+ "        <td width='4%' align='center'>#</td>"
					+ "        <td width='10%' align='center'>Cantidad</td>"
					+ "        <td width='86%'>Marca</td>" + "      </tr>";
		} else {
			cuerpo += "      <tr >"
					+ "        <td  colspan='7' style=' font-size:16px' valign='top'>RELACI&Oacute;N DE PRODUCTOS</td>"
					+ "        </tr>"
					+ "      <tr bgcolor='#007E92' style='color:#FFFFFF; font-size:9px; font-weight:900'>"
					+ "        <td width='4%' align='center'>#</td>"
					+ "        <td width='10%' align='center'>Cantidad</td>"
					+ "        <td width='33%'>Descripci&oacute;n</td>"
					+ "        <td width='17%'>Documentos</td>"
					+ "        <td width='16%' align='center'>FEE</td>"
					+ "        <td width='16%' align='center'>D&iacute;as de atraso</td>"
					+ "        <td width='4%' align='center'>&nbsp;</td>"
					+ "      </tr>";
		}
		cuerpo += productos;

		cuerpo += "    </table></td>"
				+ "  </tr>"
				+ "  <tr>"
				+ "    <td colspan='2' ><table width='100%' border='1' bordercolor='#FFFFFF' cellpadding='0' cellspacing='0' style='font-size:9px;font-family: Tahoma, Geneva, sans-serif' >"
				+ "      <tr  style='color:#FFFFFF;font-weight:900; border-color:#177E91; '>"
				+ "        <td colspan='3' align='center' bgcolor='#177E91'>"
				+ "          <table width='100%' border='0' cellspacing='2' cellpadding='0' style='font-family: Tahoma, Geneva, sans-serif; font-size:11px'>"
				+ "            <tr bordercolor='#FFFFFF'  style='color:#FFFFFF;font-weight:900; border-color:#177E91'>"
				+ "              <td width='33%' align='center' bgcolor='#177E91'>En Tiempo</td>"
				+ "              <td width='33%' align='center' bgcolor='#177E91'>Fuera de Tiempo</td>"
				+ "              <td width='33%' align='center' bgcolor='#177E91'>Efectividad</td>"
				+ "            </tr>" + "          </table></td>"
				+ "        </tr>"
				+ "      <tr style='font-family:Helvetica; font-size:11px' >"
				+ "        <td width='33%' align='center' > ";








		if (dentro > 1) {
			cuerpo += dentro + " piezas ";
		} else if (dentro == 1) {
			cuerpo += " 1 pieza";
		} else {
			cuerpo += " 0 piezas ";
		}
		;
		cuerpo += " </td>" + "        <td width='34%' align='center'> ";
		if (fuera > 1) {
			cuerpo += fuera + " piezas ";
		} else if (fuera == 1) {
			cuerpo += "1 pieza";
		} else {
			cuerpo += " 0 piezas ";
		}
		cuerpo += "</td>"
				+ "        <td width='33%' align='center' style='font-weight:900'>";
		cuerpo += porciento;
		cuerpo += "%</td>"
				+ "      </tr>"
				+ "      <tr style='border-bottom-color:#cccccc;'>"
				+ "        <td colspan='3' align='center' style='color:#8B8B8B; padding:0'> <img src='cid:Marcas' width='100%' /> </td>"
				+ "        </tr>"
				+ "    </table></td>"
				+ "  </tr>"
				+ "  <tr>"
				+ "  <td colspan='2' style='font-size:10px; '>"
				+ "<font color='#177E91'>*</font> Para ver detalle consulte archivo adjunto. <br /> <img src='cid:pdf' />";
		cuerpo += "  </td>"
				+ "  </tr>"
				+ "  <tr style=' padding:0; margin:0' >"
				+ "  <td colspan='2' align='center' style='font-size:18px' > <hr color='#177E91' /> GRACIAS POR SU PREFERENCIA <hr color='#177E91' />"
				+ "  </td>"
				+ "  </tr>"
				+ "  <tr style=' padding:0; margin:0' >"
				+ "    <td colspan='2' align='center' style='font-size:8px; color:#177E91; font-weight:600; font-family:Tahoma, Geneva, sans-serif; letter-spacing:1' >"
				+ " Contacto: Ciudad de M&eacute;xico (55 13151498) Guadalajara (33 30700302) <br /> Interior (800 6811440) Resto del mundo (ventas@proquifa.com.mx)</td>"
				+ "  </tr>" + "</table>";



		if (correo.getIdEmpleadoString() != null){
			// se crea un bodypart para el pie de pagina CON FIRMA    
			String nameImg = correo.getIdEmpleadoString().toString();
			String tex = " Este correo electr&oacute;nico y/o el material anexo son para uso exclusivo de la persona o entidad a la que expresamente se le ha enviado, y puede contener informaci&oacute;n confidencial y/o material privilegiado. Si usted no es el destinatario leg&iacute;timo del mismo, deber&aacute; notificar  inmediatamente al remitente del correo y borrar este mensaje y sus anexos en su totalidad permanentemente de su sistema. Cualquier revisi&oacute;n, retransmisi&oacute;n, difusi&oacute;n o cualquier otro uso de este correo, por personas o entidades distintas a las del destinatario leg&iacute;timo, queda expresamente prohibido. Este correo electr&oacute;nico no pretende ni debe ser considerado como constructivo de ninguna relaci&oacute;n legal, contractual o de otra &iacute;ndole similar.";
			String text1 = " This e-mail message and/or any attachments are intended solely for the exclusive use of the individual named on this mail and may be confidential and/or copyrighted. If you are not the intended recipient and received this message, please notify immediately to us and then delete it from your system. Any revision or any other use of this mail, by persons or entities other than the intended recipient is forbidden. This mail is not intending to be considered as abiding to any legal relationship, contractual or any other similar kind.";
			cuerpo +="<div><em><font color='#808080' face='Helvetica' size='1'><img src='cid:"+nameImg+"'  width='100%' /><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>"+tex+"</font></em></div><em><font color='#808080' face='Helvetica' size='1'>"+text1+"</font></em><br><br>";



		}else{
			// se crea un bodypart para el pie de pagina SIN FIRMA    

			String tex = " Este correo electr&oacute;nico y/o el material anexo son para uso exclusivo de la persona o entidad a la que expresamente se le ha enviado, y puede contener informaci&oacute;n confidencial y/o material privilegiado. Si usted no es el destinatario leg&iacute;timo del mismo, deber&aacute; notificar  inmediatamente al remitente del correo y borrar este mensaje y sus anexos en su totalidad permanentemente de su sistema. Cualquier revisi&oacute;n, retransmisi&oacute;n, difusi&oacute;n o cualquier otro uso de este correo, por personas o entidades distintas a las del destinatario leg&iacute;timo, queda expresamente prohibido. Este correo electr&oacute;nico no pretende ni debe ser considerado como constructivo de ninguna relaci&oacute;n legal, contractual o de otra &iacute;ndole similar.";
			String text1 = " This e-mail message and/or any attachments are intended solely for the exclusive use of the individual named on this mail and may be confidential and/or copyrighted. If you are not the intended recipient and received this message, please notify immediately to us and then delete it from your system. Any revision or any other use of this mail, by persons or entities other than the intended recipient is forbidden. This mail is not intending to be considered as abiding to any legal relationship, contractual or any other similar kind.";
			cuerpo+="<p><em><font color='#808080' face='Helvetica' size='1'><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>"+tex+"</font></em></p><em><font color='#808080' face='Helvetica' size='1'>"+text1+"</font></em>";
		}


		correo.setCuerpoCorreo(cuerpo);
		correo.setConFormato(1);

		if ((con.get(0).getEstadoEntrega().equals("No realizada"))
				&& (!con.get(0).getTipoJustificacion().equals("Cliente") || !con
						.get(0).getTipoJustificacion().equals("Cliente   "))) {
			return false;
		} else {
			// cambiar acentos por Simbolos HTML (&acute) solo para correo;
			correo.setCuerpoCorreo(correo.getCuerpoCorreo().replaceAll("á", "&aacute;"));  
			correo.setCuerpoCorreo(correo.getCuerpoCorreo().replaceAll("é", "&eacute;"));  
			correo.setCuerpoCorreo(correo.getCuerpoCorreo().replaceAll("í", "&iacute;"));  
			correo.setCuerpoCorreo(correo.getCuerpoCorreo().replaceAll("ó", "&oacute;"));  
			correo.setCuerpoCorreo(correo.getCuerpoCorreo().replaceAll("ú", "&uacute;"));  
			correo.setCuerpoCorreo(correo.getCuerpoCorreo().replaceAll("ñ", "&ntilde;")); 
			//				log.info("Correo--->"+ correo.toString());

			//en produccion se debe habilitar
			MensajeroResoutce enviarCorreo = new MensajeroResoutce();
			try {
				sendEmail(correo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//funcion.enviarCorreoViaSpringBoot(correo);
			mensajeroDAO.updateRutaDPfolioNE(ruta, folio.getFolioCompleto());

			return true;
		}

	}

	public Boolean actualizarPersonalAlmacenCliente(List<PersonalAlmacenCliente> personal) throws ProquifaNetException {
		Boolean r = false;
		try {
			for (PersonalAlmacenCliente contacto : personal) {
				if (contacto.getIdPersonal() != null && contacto.getIdPersonal() > 0) {
					if (contacto.getBorrar() == false) {
						r = mensajeroDAO.updatePersonalAlmacenCliente(contacto);
					} else {
						r = mensajeroDAO.deletePersonalAlmacenCliente(contacto.getIdPersonal());
					}
				} else {
					r = mensajeroDAO.insertPersonalAlmacenCliente(contacto);
				}
			}
			return r;
		} catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus()
			.setRollbackOnly();
			log.info(e.getMessage());
			return false;
		}
	}

	public List<PersonalAlmacenCliente> obtenerPersonalAlmacenCliente(
			Long idCliente) throws ProquifaNetException {
		try {
			return mensajeroDAO.getPersonalAlmacenCliente(idCliente);
		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}

	// Consultas Pendientes
	@Override
	public List<PendientesMensajero> obtenerPendientesRutaTrabajar(
			String usuario) throws ProquifaNetException {
		try {
			String condiciones = "P.Tipo = 'Ruta a trabajar' AND R.EstadoRuta = 'AColectar' and P.Responsable = '" + usuario + "'";
			return mensajeroDAO.findPendientesPorUsuario(condiciones);
		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}

	@Override
	public List<PendientesMensajero> obtenerPendientesPlanificar(String usuario)
			throws ProquifaNetException {
		try {
			String condiciones = "P.Tipo = 'Ruta a planear' AND R.EstadoRuta = 'APlanear' and P.Responsable = '" + usuario + "'";
			return mensajeroDAO.findPendientesPorUsuario(condiciones);
		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}

	// -------

	@Override
	public Boolean cerrarPendienteRutaTrabajar(
			PendientesMensajero pendientesMensajero)
					throws ProquifaNetException {
		try {
			return mensajeroDAO.updateCerrarPendiente(pendientesMensajero,
					"Ruta a trabajar", "Ruta a planear", "AColectar");
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

	@Override
	public Boolean cerrarPendienteRutaPlanear(
			PendientesMensajero pendientesMensajero)
					throws ProquifaNetException {
		try {
			return mensajeroDAO.updateCerrarPendiente(pendientesMensajero,
					"Ruta a planear", "Ruta a ejecutar", "APlanear");
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

	@Override
	public String obtenerLigaDocumentacion(String idRutaDP) throws ProquifaNetException {
		// TODO Auto-generated method stub
		return null;
	}


	public void sendEmail(Correo correo) throws Exception{
		//correo.setOrigen("serviciosti");

		boolean isProquifa = true;
		sender.setHost("smtp.gmail.com");
		sender.setPort(25);



		if(correo.getRemitente() != null && correo.getRemitente().toLowerCase().equals("pharma")){//Si es remitente Pharma
			/*	props.put("mail.imap.user", "purchase@phsus.com");
				props.put("mail.password", "Pharma-1");
				props.put("mail.smtp.host", "smtp.gmail.com");
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.port", "587");*/

			sender.setHost("smtp.gmail.com");
			sender.setPort(587);
			sender.setUsername("purchase@phsus.com");
			sender.setPassword("Pharma-1");

			isProquifa = false;
		}else {

			if (correo.getOrigen().toLowerCase() != null && correo.getOrigen().toLowerCase().equals("ventas") || correo.getOrigen().toLowerCase().equals("credito") || correo.getOrigen().toLowerCase().equals("compras")) {

				/*
				props.put("mail.imap.user", "ocardona@proquifa.net");
				props.put("mail.password", "2hs3kMxXjEyVLqHU");
				props.put("mail.smtp.host", "smtp-relay.sendinblue.com");
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.port", "587");	*/	

				sender.setHost("smtp-relay.sendinblue.com");
				sender.setPort(587);
				sender.setUsername("ocardona@proquifa.net");
				sender.setPassword("2hs3kMxXjEyVLqHU");								

			}else {

				if (correo.getRemitente() != null && correo.getRemitente().toLowerCase().equals("rm trading")) {// Si el remitente es rmtrading																					
					/*	props.put("mail.imap.user", "purchase@rmtrade.net");
				props.put("mail.password", "rmtrading-1");*/
					sender.setUsername("purchase@rmtrade.net");
					sender.setPassword("rmtrading-1");

					isProquifa = false;
				}else{
					/*	props.put("mail.smtp.host", HOST);
				props.put("mail.imap.user", correo.getOrigen().toLowerCase() + DOMINIO);	*/	
					sender.setHost(HOST);
					sender.setUsername(correo.getOrigen().toLowerCase() + DOMINIO);

					if (correo.getOrigen().toLowerCase().equals("serviciosti")) {					
						//props.put("mail.password", "123456");
						sender.setPassword("123456");
					}else {	sender.setPassword("cambiame");
					//	props.put("mail.password", "cambiame");
					}
				}

			}

		}

		log.info("En el servicio de correo metodo");

		log.info("host-->"+sender.getHost()+"---from-->"+sender.getUsername()+"----Origen-->"+correo.getOrigen());
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message,true);

		if (correo.getOrigen() != null && correo.getOrigen().toLowerCase().equals("ventas") || correo.getOrigen().toLowerCase().equals("compras") || correo.getOrigen().toLowerCase().equals("credito")) {
			helper.setFrom(new InternetAddress(correo.getOrigen().toLowerCase() +"@proquifa.com.mx"));
			message.setFrom(new InternetAddress(correo.getOrigen().toLowerCase() +"@proquifa.com.mx"));
		} else if (correo.getRemitente() != null && correo.getRemitente().toLowerCase().equals("pharma")) {// Si es remitente Pharma
			helper.setFrom(new InternetAddress("purchase@phsus.com"));
		} else if (correo.getRemitente() != null && correo.getRemitente().toLowerCase().equals("rm trading")) {// Si es RMTrading
			helper.setFrom(new InternetAddress("purchase@rmtrade.net"));
		}else{
			helper.setFrom(new InternetAddress(correo.getOrigen().toLowerCase() + DOMINIO));
		}


		helper.setTo(correo.getCorreo());
		//helper.setTo("david.garcia@ryndem.mx");
		if (correo.getCcorreo() != null && !correo.getCcorreo().equals("")){
			helper.setCc(correo.getCcorreo());
		}

		String [] cco = correo.getCocorreo().split(";");
		helper.setBcc(cco);



		// helper.addAttachment( correo.getArchivoAdjunto(), );
		//   File file= new File("/Users/josephat.reyes/desktop/NE-072518-84.pdf");
		FileSystemResource file = new FileSystemResource(RUTA_CONFIRMACION + correo.getArchivoAdjunto()+".pdf");

		log.info("Archivo-->"+file);


		//helper.addAttachment(file.getFilename(), file);
		//   helper.addAttachment("/Users/josephat.reyes/desktop/NE-072518-84.pdf", new ByteArrayResource(getBytesFromFile(file)) );
		helper.setText("<html> <body>"+ correo.getCuerpoCorreo()+"</body> </html>", true);
		helper.setSubject(correo.getAsunto());


		if (file.exists()){
			helper.addInline("pdf", file);
		}

		if(correo.getConFormato() == 1){

			log.info("Si es con formato-->");
			//MimeBodyPart imagen2 = null;
			//imagen2 = new MimeBodyPart();
			String ruta = RUTA_FIRMAS + "logo.jpg";
			File firma = new File(ruta);
			FileSystemResource res = new FileSystemResource(firma);

			if (firma.exists()){
				helper.addInline("Proquifa", res);
			}

			MimeBodyPart imagen3 = null;
			//	imagen3 = new MimeBodyPart();
			ruta = RUTA_FIRMAS + "franja_Marcas.jpg";

			File marcas = new File(ruta);
			FileSystemResource res2 = new FileSystemResource(marcas);
			//File marcas = new File(ruta);
			if (marcas.exists()){

				helper.addInline("Marcas",res2);
			}
		}

		if (!correo.isSinPiePagina()) {

			if (correo.getIdEmpleadoString() != null){

				String ruta3 = RUTA_FIRMAS+correo.getIdEmpleadoString().toString()+".png";
				log.info ("ruta3:" + ruta3);
				File firma = new File(ruta3);
				FileSystemResource res3 = new FileSystemResource(firma);
				//File marcas = new File(ruta);
				if (firma.exists()){

					helper.addInline(""+correo.getIdEmpleadoString().toString(),res3);
				}


			}
		}else{
			String tex = " ";
			String text1 = "";

		}




		sender.send(message);
	}


	@Override
	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public Map<String, List<TotalMensajero>> findPendientesMensajero(Integer idResponsable) throws ProquifaNetException {
		try {
			return mensajeroDAO.findPendientesMensajero(idResponsable);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public List<AsignarMensajero> obtenerDatosDetalleAsignarMensajero(Integer idUsuario, String estado) throws ProquifaNetException {
		try {
			return mensajeroDAO.obtenerDatosDetalleAsignarMensajero(idUsuario, estado);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	@Transactional(readOnly=false, transactionManager = "ds1TransactionManager", rollbackFor = Exception.class)
	public boolean guardarAsignarMensajero(List<AsignarMensajero> rutas) throws ProquifaNetException {
		try {
			for (AsignarMensajero ruta : rutas) {
				if (ruta.getZona() == null) {
					mensajeroDAO.actualizarAlmacen(ruta);
				} else if (ruta.getIdAsignarMensajero() != null &&
						ruta.getIdAsignarMensajero() > 0) {
					if(ruta.getIdMensajero() == 0) {
						pendienteDAO.borrarAsignarMensajero(ruta.getFolio());
					}else {
						ruta.setEstado("Abierto");
						mensajeroDAO.actualizarAsignarMensajero(ruta);
					}
				}else {
					if(ruta.getIdMensajero() != 0) {
						mensajeroDAO.guardarAsignarMensajero(ruta);
					}
				}
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
	}


	@Transactional(readOnly=false, transactionManager = "ds1TransactionManager")
	@Override
	public boolean publicarRuta(List<List<AsignarMensajero>> rutas) throws ProquifaNetException {
		try {
			boolean isGuadalajara = false;
			String folios = "'-1'";
			List<String[]> dataLines = new ArrayList<>();
			dataLines.add(new String[] {"#", "Mensajero", "Fecha", "Cliente", "Folio PL", "Calle", "CP", "Delegacion", "Altitud", "Longitud", "Latitud"});
			for (List<AsignarMensajero> lista : rutas) {
				if (lista != null && lista.size() > 0) {
					Folio folio = folioDAO.obtenerFolioPorConcepto("RL", true);
					Integer idMensajero = 0;
					for (AsignarMensajero ruta : lista) {
						idMensajero = ruta.getIdMensajero();
						ruta.setEstado("Publicado");
						ruta.setRuta(folio.getFolioCompleto());

						for (String folioRuta : ruta.getFolios()) {
							mensajeroDAO.actualizarEstadoAsignarMensajero(ruta, folioRuta);
							ruta.setEvento(folioRuta);

							if (folioRuta.contains("PL")) {
								ruta.setEvento("Entrega");
								ruta.setFolio(folioRuta);
								rutaDAO.saveRutaEvento(ruta);
								//Folio folioDP = folioDAO.obtenerFolioPorConcepto("Despachos", true);
								if (ruta.isGuadalajara()) {
									rutaDAO.saveRutaDP(folio.getFolioCompleto(), "", folioRuta, true);
									isGuadalajara = true;
								}
								else
									rutaDAO.saveRutaDP(folio.getFolioCompleto(), "", folioRuta);
								//								rutaDAO.saveRutaDP(folio.getFolioCompleto(), folioDP.getFolioCompleto(), folioRuta);
								pendienteDAO.cerrarPendiente_angular(folioRuta, null, "Asignar Mensajero");
							} else {
								String tabla = "";
								String campo = "";
								if (folioRuta.contains("PR")) {
									tabla = "RutaPR";
									campo = "idPR";
									ruta.setEvento("Revisiones");
								}  else if (folioRuta.contains("ES")) {
									tabla = "RutaES";
									campo = "idES";
									ruta.setEvento("Entrega Especial");
								}  else if (folioRuta.contains("RE")) {
									tabla = "RutaRE";
									campo = "idRE";
									ruta.setEvento("Revision Especial");
								}  else if (folioRuta.contains("RM")) {
									tabla = "RutaRM";
									campo = "idRM";
									ruta.setEvento("Recoleccion Material");
								}  else if (folioRuta.contains("PC")) {
									tabla = "RutaPC";
									campo = "idPC";
									ruta.setEvento("Cobro");
								}  else if (folioRuta.contains("ET")) {
									tabla = "RutaDP";
									campo = "idEntrega";
									ruta.setEvento("Entrega");
								}
								ruta.setFolio(folioRuta);
								rutaDAO.saveRutaEvento(ruta);
								rutaDAO.actualizarEvento(folio.getFolioCompleto(), ruta.getFolio(), tabla, campo);
								pendienteDAO.cerrarPendiente_angular(folioRuta, null, "A concluir planeacion");
							}
						}
					}

					Pendiente pendiente = new Pendiente(null, "Ruta a ejecutar", folio.getFolioCompleto(), new Date(), empleadoDAO.obtenerEmpleadoPorId(idMensajero.longValue()).getUsuario(), null );
					pendienteDAO.guardarPendiente_angular(pendiente);
					
					if (!isGuadalajara) {
						folios += ",'" + folio.getFolioCompleto() + "'";
					}
					
				}
			}
			
			if (!isGuadalajara) {
				dataLines.addAll(rutaDAO.reporteAsingarMensajero(folios));
			}

			senMailMensajero(dataLines);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
	}
	
	
	private void senMailMensajero(List<String[]> dataLines) throws ProquifaNetException {
		try {
			File csvOutputFile = new File("/tmp/AsignarMensajero.csv");
		    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
		        dataLines.stream().map(this::convertToCSV).forEach(pw::println);
		    }
		    Funcion funcion = new Funcion();
		    Correo correo = new Correo();
		    correo.setOrigen("ventas");
		    correo.setCuerpoCorreo("");
		    correo.setAsunto("Asignar Mensajero");		    
		    correo.setCorreo("iperez@proquifa.net; lrosas@proquifa.net; oscar.cardona@ryndem.mx");
		    correo.setArchivoAdjunto("AsignarMensajero.csv");
		    correo.setTipo("csvasignarmensajero");
		    funcion.enviarCorreo(correo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String convertToCSV(String[] data) {
	    return Stream.of(data)
	      .map(this::escapeSpecialCharacters)
	      .collect(Collectors.joining(","));
	}
	
	private String escapeSpecialCharacters(String data) {
	    String escapedData = data.replaceAll("\\R", " ");
	    if (data.contains(",") || data.contains("\"") || data.contains("'")) {
	        data = data.replace("\"", "\"\"");
	        escapedData = "\"" + data + "\"";
	    }
	    return escapedData;
	}
	

	@Override
	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public Map<String, Object> obtenerDatosVistaInicial(Integer idUsuario) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("grafica", rutaDAO.obtenerDatosGraficaCerrarRuta(idUsuario));
			map.put("lista", rutaDAO.obtenerDatosCerrarRuta(idUsuario));
			return map;
		} catch (Exception e) {
			return null;
		}
	}


	@Override
	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public List<AsignarMensajero> obtenerComparacionRuta(Integer idMensajero) throws ProquifaNetException {
		try {
			return rutaDAO.obtenerComparacionRuta(idMensajero);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	@Override
	@Transactional(readOnly=false, transactionManager = "ds1TransactionManager")
	public boolean guardarCerrarRuta(Ruta ruta) throws ProquifaNetException {
		try {
			rutaDAO.guardarRuta(ruta);
			//			rutaDAO.estadoAGenerar(ruta);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}

		return false;
	}


	@Override
	public  String saveFotosPDF(String t, int n, String dir)
			throws DocumentException, MalformedURLException, IOException, InterruptedException {
		try {
			Funcion funcion = new Funcion();
			String dirSAP = "";
			dirSAP = funcion.obtenerRutaServidor("doctoscierrert", "");

			String FILE_NAME = dirSAP + t + ".pdf";
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(new File(FILE_NAME)));
			document.open();
			PdfContentByte cb = writer.getDirectContent();

			Font f = new Font();
			f.setStyle(Font.BOLD);
			f.setSize(8);

			document.setMargins(20, 20, 20, 20);

			for (int i = 0; i < n; i++) {
				Image imagen = Image.getInstance(dir + t + "-" + i + ".png");
				imagen.scaleToFit(500f, 800f);
				document.add(imagen);
			}

			for (int i = 0; i < n; i++) {
				document.close();
				File origen = new File(	dir + t + "-" + i + ".png");
				origen.delete();
			}
			return "Ok";
		}catch(Exception e) {
			log.info(e.toString());
			return null;
		}
	}

	@Override
	public  String savePDF(String t, String dir) {

		String FILE_NAME = dir + t + ".pdf";
		System.out.print(" " + FILE_NAME);
		//		String FILE_NAME = "/Users/david.garcia/Desktop/PROQUIFANET/CO/BACK-END_ANGULAR/ProquifaNet/Reportes/"
		//
		//				+ t + ".pdf";

		Rectangle pageSize = new Rectangle(900f, 1000f); // ancho y alto
		Document document = new Document(pageSize);
		try {

			PdfWriter.getInstance(document, new FileOutputStream(new File(FILE_NAME)));
			document.open();
			Font f = new Font();
			f.setStyle(Font.BOLD);
			f.setSize(8);
			document.setMargins(20, 20, 20, 20);
			document.add(new Paragraph(t, f));
			document.add(Image.getInstance("EvidenciaEntrega/" + t + ".png"));
			document.close();

			Thread.sleep(1000);
			File origen = new File(
					FILE_NAME + t
					+ ".pdf");
			File imagen = new File(
					FILE_NAME + t
					+ ".png");
			File destino = new File(FILE_NAME + t + ".pdf");

			try {
				InputStream in = new FileInputStream(origen);
				OutputStream out = new FileOutputStream(destino);
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}

				in.close();
				out.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}

			Thread.sleep(1000);
			origen.delete();
			imagen.delete();
			log.info("Done");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public  String saveImgArribo(byte[] byteArray, String nombre, String path, String [] lstId, String tipo) throws ProquifaNetException  {

		try {

			if(tipo.equalsIgnoreCase("FE")) {
				for(int i = 0; i < lstId.length ; i++) {
					String id = lstId[i];
					Folio folio;

					folio = folioDAO.obtenerFolioPorConcepto("FE", true);

					String nombreArchivo = folio.getFolioCompleto();

					File file = new File(path);
					File targetFile = new File(path + "/" + nombreArchivo + ".png");
					if (!file.exists()) {
						file.mkdirs();
					}
					OutputStream outStream;
					outStream = new FileOutputStream(targetFile);
					outStream.write(byteArray);
					outStream.close();

					if( !rutaDAO.actualizarRutaDP(id, tipo, folio.getFolioCompleto())){
						return null;
					}
				}
			}else {
				String id = lstId[0];

				String nombreArchivo =  nombre;

				File file = new File(path);
				File targetFile = new File(path + "/" + nombreArchivo + ".png");
				if (!file.exists()) {
					file.mkdirs();
				}
				OutputStream outStream;
				outStream = new FileOutputStream(targetFile);
				outStream.write(byteArray);
				outStream.close();

			}	


			return "Exito";

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}

	@Override
	public String saveDocumento3(String id, String tipo) throws ProquifaNetException{
		// TODO Auto-generated method stub
		try {
			Folio folio;

			folio = folioDAO.obtenerFolioPorConcepto("RT", true);

			if( !rutaDAO.actualizarRutaDP(id, tipo, folio.getFolioCompleto())){
				return null;
			}

			return folio.getFolioCompleto();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String savePDF(String t) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean insertarRecorrido(Recorrido recorrido) throws ProquifaNetException {
		return mensajeroDAO.insertarRecorrido(recorrido);
	}
	@Override
	public Map<String, Object> obtenerFacturasFolio(String[] folios){
		try {
			Map<String, Object> mapFolio = new HashMap<String, Object>();
			
			for(String folio: folios) {
				mapFolio.put(folio, mensajeroDAO.obtenerFacturasFolio(folio));
			}
			return mapFolio;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Map<String, Integer> obtenerTotales(Integer idResponsable) throws ProquifaNetException{
		try {
			return mensajeroDAO.totalesGeneral(idResponsable);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

}
