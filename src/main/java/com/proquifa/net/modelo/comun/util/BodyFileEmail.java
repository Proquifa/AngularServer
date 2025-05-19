package com.proquifa.net.modelo.comun.util;

import java.io.File;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.proquifa.net.modelo.ventas.admoncomunicacion.Correo;
import com.proquifa.net.modelo.ventas.admoncomunicacion.MiAutenticador;

public class BodyFileEmail {

	public static final String DOMINIO = "@proquifa.net";
	static final String HOST = "mail.proquifa.net";
	private MiAutenticador authenticator;
	private Session mailSession;
	static final String RUTA_FIRMAS = Funcion.RUTA_FIRMAS;
	
	public void bodyPdfInspeccion(Correo correoD) {
		Correo correo = new Correo();
		StringBuilder cuerpo = new StringBuilder("\n");
		cuerpo.append("<html> \n");
		cuerpo.append("<body> \n");
		cuerpo.append("<div style=\"width: 530px;height:100%; margin: 0 auto\"> \n");
		cuerpo.append("<table style='width: 100%;border-spacing: 0;border: 1px solid #EFEFEF;border-radius: 1em;overflow: hidden;'>\n");
		cuerpo.append(" <tr style='height: 99px;background: #1F3849;'> \n");
		cuerpo.append("<th><div style='margin: 0 auto;'><img height='45px' src='http://187.188.98.194/DESARROLLO/Imagenes/proquifa.png' onError=\"this.src='http://187.188.98.194/DESARROLLO/Imagenes/logo_proquifa.png'\"></div></th> \n");
		cuerpo.append(" </tr> \n");
		cuerpo.append("<tr style='height: 11px;background: #008894;color: #FFFFFF;text-align: justify;'> \n");
		cuerpo.append(" <th style='padding-left:40px;font-family:sans-serif;font-weight: 600;'> ").append("</th> \n");
		cuerpo.append("</tr> \n");
		cuerpo.append(" <tr style='text-align: justify;height: 350px;background: #FFFFFF'> \n");
		cuerpo.append("<td style='padding-left: 40px;font-size: 18px;font-family: sans-serif;font-weight: 500;padding-top: 45px;padding-bottom: 96px;line-height: 1.4;'> \n");
		cuerpo.append("<label style='color:#008894;font-size:21px;font-weight: 600;'>¡Estimado Cliente!</label><br><br><br> \n");
		cuerpo.append("<label>Por medio del presente le comunicamos que su</label><br> \n");
		cuerpo.append("<label>orden de compra con no. de referencia: <label style='font-weight: bold'># ").append(correoD.getAsunto()).append("</label></label><br> \n");
		cuerpo.append("<label>Ha sido programada para su entrega en nuestro </label><br> \n");
		cuerpo.append("<label>almacén a partir de este momento, en un horario </label><br> \n");
		cuerpo.append("<label style='color: #008894'>de 9:00 hrs a 13:00 y de 15:00 a 17:00 hrs.</label> \n");
		cuerpo.append("</td> \n");
		cuerpo.append("</tr> \n");
		cuerpo.append("<tr style='height: 90px;background: #F2F5F7;color:#969595;font-size: 10px;'> \n");
		cuerpo.append(" <td style='text-align: center;'> \n");
		cuerpo.append("<label>© PROQUIFA. Todos los derechos reservados.</label><br><br> \n");
		cuerpo.append("<label>Número único nacional sin costo: (55) 1315 1498 · Aviso de Privacidad</label><br><br> \n");
		cuerpo.append(" <img height='13px' src='http://187.188.98.194/DESARROLLO/Imagenes/powered.png' onError=\"this.src='http://187.188.98.194/DESARROLLO/Imagenes/powered.png'\"> \n");
		cuerpo.append("</td> \n");
		cuerpo.append("</tr> \n");
		cuerpo.append("</table> \n");
		cuerpo.append("<div style='width: 530px;color: #87858A; font-size: 7px;font-family: sans-serif;font-weight: 500;text-align:justify;padding-top: 15px;'> \n");
		cuerpo.append("Este correo electrónico y/o el material anexo son para uso exclusivo de la persona o entidad a la que expresamente se le ha enviado y puede contener información confidencial y/o material privilegiado. Si usted no es el destinatario legítimo del mismo, deberá notificar inmediatamente al remitente del correo y borrar este mensaje y sus anexos en su totalidad permanentemente de su sistema. Cualquier revisión, retransmisión, difusión o cualquier otro uso de este correo, por personas o entidades distintas a las del destinatario legítimo, queda expresamente prohibido. Este correo electrónico no pretende ni debe ser considerado como constructivo de ninguna relación legal, contractual o de otra índole similar. \n");
		cuerpo.append("</div> \n");
		cuerpo.append(" </div> \n");
		cuerpo.append("</body> \n");
		cuerpo.append("</html>");
		
		correo.setCorreo(correoD.getCorreo());
		correo.setCuerpoCorreo(cuerpo.toString());
		correo.setSinPiePagina(true);
		correo.setConFormato(0);
        correo.setArchivoAdjunto("");
        correo.setOrigen("ventas"); 
        correo.setAsunto(correoD.getAsunto());
		this.envioCorreo(correo);
		// return cuerpo.toString();
	}
	
	@SuppressWarnings("unused")
	public Boolean envioCorreo(Correo correo) {
		try {
			Funcion funcion = new Funcion();
			//Quitar
			if (!Funcion.PRODUCCION)
				correo.setCorreo("dofus@mailinator.com;");
			else
				correo.setCocorreo("dofus@mailinator.com;");
			//sara.sanchez@ryndem.mx;dofus@mailinator.com;yosimar.mendez@gmail.com;ymendez15@hotmail.com;oscar.cardona@ryndem.mx;yosimar.mendez@ryndem.mx;
			boolean isProquifa = true;
			Properties props = new Properties();
			String archivo = "";
			props.put("mail.transport.protocol", "smtp");
//
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "25");
			if(correo.getRemitente() != null && correo.getRemitente().toLowerCase().equals("pharma")){//Si es remitente Pharma
				props.put("mail.imap.user", "purchase@phsus.com");
				props.put("mail.password", "Pharma-1");
				props.put("mail.smtp.host", "smtp.gmail.com");
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.port", "587");
				isProquifa = false;
			}else if (correo.getOrigen().toLowerCase() != null && correo.getOrigen().toLowerCase().equals("ventas") || correo.getOrigen().toLowerCase().equals("credito") || correo.getOrigen().toLowerCase().equals("compras")) {
				props.put("mail.imap.user", "ocardona@proquifa.net");
				props.put("mail.password", "2hs3kMxXjEyVLqHU");
				props.put("mail.smtp.host", "smtp-relay.sendinblue.com");
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.port", "587");			
			}else if (correo.getRemitente() != null && correo.getRemitente().toLowerCase().equals("rm trading")) {// Si el remitente es rmtrading																					
				props.put("mail.imap.user", "purchase@rmtrade.net");
				props.put("mail.password", "rmtrading-1");
				isProquifa = false;
			}else{
				props.put("mail.smtp.host", HOST);
				props.put("mail.imap.user", correo.getOrigen().toLowerCase() + DOMINIO);				
				if (correo.getOrigen().toLowerCase().equals("serviciosti"))
					props.put("mail.password", "123456");
				else
					props.put("mail.password", "cambiame");
			}
			authenticator = new MiAutenticador(props.getProperty("mail.imap.user"), props.getProperty("mail.password"));


			mailSession = Session.getInstance(props, authenticator);
			mailSession.setDebug(false);			


			Message msg = null;
				msg = new MimeMessage(mailSession);

				if (correo.getOrigen() != null && correo.getOrigen().toLowerCase().equals("ventas") || correo.getOrigen().toLowerCase().equals("compras") || correo.getOrigen().toLowerCase().equals("credito")) {
					msg.setFrom(new InternetAddress(correo.getOrigen().toLowerCase() +"@proquifa.com.mx"));
				} else if (correo.getRemitente() != null
						&& correo.getRemitente().toLowerCase().equals("pharma")) {// Si
																					// es
																					// remitente
																					// Pharma
					msg.setFrom(new InternetAddress("purchase@phsus.com"));
				} else if (correo.getRemitente() != null
						&& correo.getRemitente().toLowerCase().equals("rm trading")) {// Si
																						// es
																						// RMTrading
					msg.setFrom(new InternetAddress("purchase@rmtrade.net"));
				}else{
					msg.setFrom(new InternetAddress(correo.getOrigen().toLowerCase() + DOMINIO));
				}

			msg.setSubject(correo.getAsunto());
			
			String direccionesString = correo.getCorreo().toLowerCase().replace(",", ";");			

			StringTokenizer tokensCorreos = new StringTokenizer(direccionesString, ";");
			int nContactos = tokensCorreos.countTokens();
			InternetAddress[] toAddrs = new InternetAddress[nContactos];

			for (int i = 0; i < nContactos; i++) {
				toAddrs[i] = (new InternetAddress(tokensCorreos.nextToken()));
			}

			msg.setRecipients(Message.RecipientType.TO, toAddrs);

			if (correo.getCcorreo() != null) {
				if (!correo.getCcorreo().equals("")) {					
					StringTokenizer tokensCCorreos = new StringTokenizer(correo.
							getCcorreo().toLowerCase().replace(",", ";"), ";");
					int nCContactos = tokensCCorreos.countTokens();
					InternetAddress[] CCAddrs = new InternetAddress[nCContactos];
					for (int i = 0; i < nCContactos; i++) {
						CCAddrs[i] = new InternetAddress(tokensCCorreos
								.nextToken());
					}
					msg.setRecipients(Message.RecipientType.CC, CCAddrs);
				}
			}

			if (correo.getCocorreo() != null) {
				if (!correo.getCocorreo().equals("")) {
					StringTokenizer tokensCOCorreos = new StringTokenizer(correo
							.getCocorreo().toLowerCase().replace(",", ";"), ";");
					int nCOContactos = tokensCOCorreos.countTokens();
					InternetAddress[] CCOAddrs = new InternetAddress[nCOContactos];
					for (int i = 0; i < nCOContactos; i++) {
						CCOAddrs[i] = new InternetAddress(tokensCOCorreos
								.nextToken());
					}
					msg.setRecipients(Message
							.RecipientType.BCC, CCOAddrs);
				}
			}

			MimeBodyPart cuerpoCorreo = new MimeBodyPart();
			MimeMultipart multipart;

			String nameImg ="";

			BodyPart texto = new MimeBodyPart();

			if (!correo.isSinPiePagina()) {

				if (correo.getIdEmpleadoString() != null){
					// se crea un bodypart para el pie de pagina CON FIRMA    
					nameImg = correo.getIdEmpleadoString().toString();
					String tex = " Este correo electr&oacute;nico y/o el material anexo son para uso exclusivo de la persona o entidad a la que expresamente se le ha enviado, y puede contener informaci&oacute;n confidencial y/o material privilegiado. Si usted no es el destinatario leg&iacute;timo del mismo, deber&aacute; notificar  inmediatamente al remitente del correo y borrar este mensaje y sus anexos en su totalidad permanentemente de su sistema. Cualquier revisi&oacute;n, retransmisi&oacute;n, difusi&oacute;n o cualquier otro uso de este correo, por personas o entidades distintas a las del destinatario leg&iacute;timo, queda expresamente prohibido. Este correo electr&oacute;nico no pretende ni debe ser considerado como constructivo de ninguna relaci&oacute;n legal, contractual o de otra &iacute;ndole similar.";
					String text1 = " This e-mail message and/or any attachments are intended solely for the exclusive use of the individual named on this mail and may be confidential and/or copyrighted. If you are not the intended recipient and received this message, please notify immediately to us and then delete it from your system. Any revision or any other use of this mail, by persons or entities other than the intended recipient is forbidden. This mail is not intending to be considered as abiding to any legal relationship, contractual or any other similar kind.";
					texto.setContent("<p><em><font color='#808080' face='Helvetica' size='1'><img src='cid:"+nameImg+"' /><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>"+tex+"</font></em></p><em><font color='#808080' face='Helvetica' size='1'>"+text1+"</font></em><br><br>","text/html; charset=\"utf-8\"");

				}else{
					// se crea un bodypart para el pie de pagina SIN FIRMA    

					String tex = " Este correo electr&oacute;nico y/o el material anexo son para uso exclusivo de la persona o entidad a la que expresamente se le ha enviado, y puede contener informaci&oacute;n confidencial y/o material privilegiado. Si usted no es el destinatario leg&iacute;timo del mismo, deber&aacute; notificar  inmediatamente al remitente del correo y borrar este mensaje y sus anexos en su totalidad permanentemente de su sistema. Cualquier revisi&oacute;n, retransmisi&oacute;n, difusi&oacute;n o cualquier otro uso de este correo, por personas o entidades distintas a las del destinatario leg&iacute;timo, queda expresamente prohibido. Este correo electr&oacute;nico no pretende ni debe ser considerado como constructivo de ninguna relaci&oacute;n legal, contractual o de otra &iacute;ndole similar.";
					String text1 = " This e-mail message and/or any attachments are intended solely for the exclusive use of the individual named on this mail and may be confidential and/or copyrighted. If you are not the intended recipient and received this message, please notify immediately to us and then delete it from your system. Any revision or any other use of this mail, by persons or entities other than the intended recipient is forbidden. This mail is not intending to be considered as abiding to any legal relationship, contractual or any other similar kind.";
					texto.setContent("<p><em><font color='#808080' face='Helvetica' size='1'><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>"+tex+"</font></em></p><em><font color='#808080' face='Helvetica' size='1'>"+text1+"</font></em>","text/html; charset=\"utf-8\"");
				}
			}else{
				String tex = " ";
				String text1 = "";
				texto.setContent("","");
			}


			String sCuerpoCorreo = correo.getCuerpoCorreo();			
			//sCuerpoCorreo = sCuerpoCorreo.replace("\n", "<br>"); // Remplaza los Saltos de Linea por <br> para que en el correo se vea con formato
			//sCuerpoCorreo = sCuerpoCorreo.replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"); // Remplaza los tabuladores por espacios en blanco, No hay codigo HTML para TAB

			cuerpoCorreo.setText("\n" + sCuerpoCorreo + "\n" + texto.getContent(),"utf-8");
			cuerpoCorreo.setHeader("Content-Type","text/html; charset=\"utf-8\"");
			cuerpoCorreo.setHeader("Content-Transfer-Encoding","quoted-printable");
			multipart = new MimeMultipart("related");
			multipart.addBodyPart(cuerpoCorreo );




			StringTokenizer tokensDocumentos = new StringTokenizer(correo.getArchivoAdjunto(), ",");
			int nDocs = tokensDocumentos.countTokens();
			MimeBodyPart adjunto1 = null;
			DataSource source1 = null;
			//			logger.info("Adjuntando archivos a correo..." + correo.getArchivoAdjunto());
			for (int i = 0; i < nDocs; i++) {
				archivo = funcion.obtenerRutaCompletaDocumento(tokensDocumentos.nextToken(), correo.getTipo(), correo.getFacturadaPor());				
				File f = new File(archivo);
				if (f.exists()) {
					adjunto1 = new MimeBodyPart();
					source1 = new FileDataSource(archivo);
					adjunto1.setDataHandler(new DataHandler(source1));
					adjunto1.setFileName(f.getName());
					if(correo.getConFormato() == 1){
						adjunto1.setHeader("Content-ID","<Archivo_" + i + ">");
					}
					multipart.addBodyPart(adjunto1);
				}
			}
			

			if(correo.getConFormato() == 1){
				MimeBodyPart imagen2 = null;
				imagen2 = new MimeBodyPart();
				String ruta = RUTA_FIRMAS + "logo.jpg";
				File firma = new File(ruta);
				if (firma.exists()){
					imagen2.attachFile (RUTA_FIRMAS + "logo.jpg");
					imagen2.setHeader("Content-ID","<Proquifa>");
					multipart.addBodyPart(imagen2);
				}

				MimeBodyPart imagen3 = null;
				imagen3 = new MimeBodyPart();
				ruta = RUTA_FIRMAS + "franja_Marcas.jpg";
				File marcas = new File(ruta);
				if (marcas.exists()){
					imagen3.attachFile (RUTA_FIRMAS + "franja_Marcas.jpg");
					imagen3.setHeader("Content-ID","<Marcas>");
					multipart.addBodyPart(imagen3);
				}
			}

			msg.setContent(multipart);
				Transport.send(msg);

			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
