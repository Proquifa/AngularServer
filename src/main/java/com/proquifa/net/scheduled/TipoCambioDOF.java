package com.proquifa.net.scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.negocio.finanzas.TipoCambioService;

@Component
public class TipoCambioDOF {

	@Autowired
	TipoCambioService tipoCambioService;
		
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	final Logger log = LoggerFactory.getLogger(TipoCambioDOF.class);
	
	@Scheduled(cron = "0 0 09 ? * MON-FRI")
	public void descargarTipoCambio() {
		log.info("Descargar tipo de cambio DOF;  " + dateFormat.format(new Date()));
		try {
			tipoCambioService.obtenerTCambioDOF();
		} catch (ProquifaNetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
