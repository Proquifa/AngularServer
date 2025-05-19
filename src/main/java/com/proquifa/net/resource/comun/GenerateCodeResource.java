package com.proquifa.net.resource.comun;

import com.proquifa.net.modelo.comun.StatusMessage;
import com.proquifa.net.negocio.comun.GenerateCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.proquifa.net.modelo.comun.Parametro;


@RestController
@CrossOrigin
public class GenerateCodeResource {
    final Logger logger = LoggerFactory.getLogger(GenerateCodeResource.class);
    private static final String URL = "/generateCode/";

    @Autowired
    GenerateCodeService generateCodeService;

    @PostMapping(URL + "getQRCode")
    public ResponseEntity<StatusMessage> getQRCode(@RequestBody Parametro parameter) {
        logger.info("@getQRCode: inicio");
        StatusMessage mensaje = new StatusMessage();
        try {
            mensaje.setStatus(HttpStatus.OK.value());
            mensaje.setMessage("ok");
            mensaje.setCurrent(generateCodeService.getQRCode(parameter.getCode()));
            return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
        }
        mensaje.setStatus(HttpStatus.FORBIDDEN.value());
        mensaje.setMessage("Error");
        return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
    }

    @PostMapping(URL + "/getBarcode")
    public ResponseEntity<StatusMessage> getBarcode(@RequestBody Parametro parameter) {
        logger.info("@getBarcode: inicio");
        StatusMessage mensaje = new StatusMessage();
        try {
            mensaje.setStatus(HttpStatus.OK.value());
            mensaje.setMessage("ok");
            mensaje.setCurrent(generateCodeService.getBarcode(parameter.getCode()));
            return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
        }
        mensaje.setStatus(HttpStatus.FORBIDDEN.value());
        mensaje.setMessage("Error");
        return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
    }
}
