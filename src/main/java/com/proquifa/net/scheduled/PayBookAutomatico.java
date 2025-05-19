package com.proquifa.net.scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.finanzas.Paybook;
import com.proquifa.net.negocio.finanzas.PaybookService;

@Component
public class PayBookAutomatico {

	@Autowired
	PaybookService paybookService;
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	final Logger log = LoggerFactory.getLogger(PayBookAutomatico.class);
	
	@Scheduled(cron = "0 0 8 * * *")
	public void enviarReporte() {
		log.info("Reporte de PayBook se ejecuto a las;  " + dateFormat.format(new Date()));
//		try {
//			List<Paybook> mapReturn = paybookService.obtenerTransactions(null, null);
//		} catch (ProquifaNetException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
}
