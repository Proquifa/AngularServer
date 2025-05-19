package com.proquifa.net.negocio.envio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.proquifa.net.modelo.comun.StatusMessage;

@RestController
@CrossOrigin
public class RealizarEnvioResourse {
//	@Autowired
//	ShipWebServiceClient shipWebServiceClient;
	
	final static Logger log = LoggerFactory.getLogger(RealizarEnvioResourse.class);

	/*
	 * @Autowired ShipService shipService; ShipServiceLocator locator;
	 * ShipWebServiceClient shipWebServiceClient; ProcessShipmentRequest
	 * processShipmentRequest;
	 * 
	 * @PostMapping("/addShipper") public ResponseEntity<StatusMessage>
	 * addShipper(@RequestBody String estadoPedido){ StatusMessage mensaje = new
	 * StatusMessage(); try {
	 * 
	 * mensaje.setStatus(HttpStatus.OK.value()); mensaje.setMessage("ok"); Party
	 * partidasEmbalar = shipWebServiceClient.addShipper();
	 * mensaje.setCurrent(partidasEmbalar); // return new
	 * ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK); } catch (Exception
	 * e) { // TODO: handle exception }
	 * 
	 * mensaje.setStatus(HttpStatus.FORBIDDEN.value());
	 * mensaje.setMessage("Error"); // return new
	 * ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN); return null;
	 * }
	 * 
	 */
	// CompletedShipmentDetail completedShipmentDetail;
	@PostMapping("/enviarTrackingNumber")
	public ResponseEntity<StatusMessage> enviarTrackingNumber() {
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			String datos;
			datos = ShipWebServiceClient.enviarTrackingNumber();
			log.info("Entro al metodo enviar tracking");
			log.info(datos);
			mensaje.setCurrent(datos);
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}

		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.FORBIDDEN);

	}

	@PostMapping("/obtenerrArchivo")
	public ResponseEntity<StatusMessage> obtenerrArchivo() {
		StatusMessage mensaje = new StatusMessage();
		try {
			File guia;
			// ByteArrayOutputStream guia;
			guia = ShipWebServiceClient.enviarFileGuideShip();
			// guia=ShipWebServiceClient.obtenerrArchivo();

			log.info("Entro al metodo enviar guia");
			log.info("",guia);
			
			byte[] bytes = loadFile(guia);
			byte[] encoded = Base64.encodeBase64(bytes);
			String encodedString = new String(encoded);
	
			// mensaje.setCurrent(Base64.encodeBase64(guia.toByteArray()));
			log.info(encodedString);
			mensaje.setCurrent(encodedString);
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.FORBIDDEN);

	}
	
	private static byte[] loadFile(File file) throws IOException {
	    InputStream is = new FileInputStream(file);

	    long length = file.length();
	    if (length > Integer.MAX_VALUE) {
	        // File is too large
	    }
	    byte[] bytes = new byte[(int)length];
	    
	    int offset = 0;
	    int numRead = 0;
	    while (offset < bytes.length
	           && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
	        offset += numRead;
	    }

	    if (offset < bytes.length) {
	        throw new IOException("Could not completely read file "+file.getName());
	    }

	    is.close();
	    return bytes;
}

	@PostMapping("/envioUPS")
	public ResponseEntity<StatusMessage> envioUPS( @RequestBody String parametro) throws IOException {
		StatusMessage mensaje = new StatusMessage();
		log.info("Info");
		log.info(parametro);
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			log.info("entro al metodo de UPS");
		RealizarEnvioResourse.enviarURL(parametro);
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.FORBIDDEN);
	}

	
	public static URL enviarURL(String dato) throws IOException {
		URL url = new URL("https://wwwcie.ups.com/rest/Ship"+dato);
		URLConnection con = url.openConnection();
		HttpURLConnection http = (HttpURLConnection) con;

		http.setRequestMethod("POST"); // PUT is another valid option
		http.setDoOutput(true);
		http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		http.connect();
		log.info("entro al metodo de UPS (url)");
		log.info("",url);
		return url;
			
		}
 

}
