package com.proquifa.net.resource.consultas;

import com.proquifa.net.modelo.comun.StatusMessage;
import com.proquifa.net.negocio.consultas.ConsultaVentaDigitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class ConsultaVentaDigitalResorce {
    @Autowired
    ConsultaVentaDigitalService consultaVentaDigitalService;
    @GetMapping("/ventaDigital/trackingOrder/{CPedido}")
    public ResponseEntity<StatusMessage> trackingOrder(@PathVariable String CPedido){
        StatusMessage mensaje = new StatusMessage();
        try{
            mensaje.setStatus(HttpStatus.OK.value());
            mensaje.setMessage("ok");
            mensaje.setCurrent(consultaVentaDigitalService.trackingOrderByItems(CPedido));
            return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            mensaje.setStatus(HttpStatus.FORBIDDEN.value());
            mensaje.setMessage("Error");

            return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.FORBIDDEN);
        }
    }
}
