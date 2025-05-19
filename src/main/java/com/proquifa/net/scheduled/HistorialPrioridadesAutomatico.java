/**
 * 
 */
package com.proquifa.net.scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.proquifa.net.negocio.despachos.DespachosService;

/**
 * @author ymendez
 *
 */
@Component
public class HistorialPrioridadesAutomatico {
	
	final Logger log = LoggerFactory.getLogger(HistorialPrioridadesAutomatico.class);

	@Autowired
	DespachosService despachosService;

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Scheduled(cron = "0 0 02 * * *")
	public void guardarPrioridades() {
		log.info("Historial de Prioridades se ejecuto a las;  " + dateFormat.format(new Date()));
		try {
			despachosService.guardarHistorialPrioridades();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
