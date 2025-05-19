package com.proquifa.net.scheduled;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.negocio.consultas.ConsultaPromsyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
@Component
public class StockAutomatico {
    final Logger log = LoggerFactory.getLogger(StockAutomatico.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    @Autowired
    ConsultaPromsyService consultaPromsyService;
   //@Scheduled(cron = "50 7 * * *")
  //  @Scheduled(cron = "0 */2 * * * *")
    public void descontarProductosCaducadosStock() throws ProquifaNetException {
     /*   if (consultaPromsyService.descontarProductosCaducadosStock()){
        log.info("Descuento de Stock se ejecuto a las;  " + dateFormat.format(new Date()));}
        else{
            log.info("Descuento de Stock se ejecuto a las;  " + dateFormat.format(new Date()) +" pero no hubo actualizacion de productos");
        }*/
        log.info("Descuento de Stock se ejecuto a las;  " + dateFormat.format(new Date()));
    }
}
