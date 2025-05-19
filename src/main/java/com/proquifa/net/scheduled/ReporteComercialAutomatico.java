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
public class ReporteComercialAutomatico {
    @Autowired
    ConsultaPromsyService consultaPromsyService;
   // @Scheduled(cron = "0 */5 * * * *")
   //@Scheduled(cron = "55 16 * * * ?")
   @Scheduled(cron = "0 55 16 ? * MON-FRI")
    public void generarReporteDiario5PM()
    {
        System.out.println("Ejecuta metodo");
        try {
            consultaPromsyService.reporteComercial();
        } catch (ProquifaNetException e) {
            throw new RuntimeException(e);
        }
    }
}
