package com.proquifa.net.resource.consultas;

import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.Producto;
import com.proquifa.net.modelo.comun.StatusMessage;
import com.proquifa.net.modelo.despachos.EstadisticaUsuarioEmbalar;
import com.proquifa.net.negocio.comun.impl.FacturaElectronicaServiceImpl;
import com.proquifa.net.negocio.consultas.ConsultaPromsyService;
import com.proquifa.net.persistencia.consultas.impl.ConsultasPromsyDAOImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class ConsultasPromsyResource {
    @Autowired
    ConsultaPromsyService consultaPromsyService;
    final Logger log = LoggerFactory.getLogger(ConsultasPromsyResource.class);

    @PostMapping("/consultaPromsy/unidadesVendidasTrimestre")
    public ResponseEntity<StatusMessage>unidadesVendidasTrimestre(@RequestBody Parametro parametro){
        StatusMessage mensaje = new StatusMessage();
        try {
            mensaje.setStatus(HttpStatus.OK.value());
            if (consultaPromsyService.unidadesVendidasTrimestre(parametro)==0){
                mensaje.setMessage("No se encontro ningun registro.");
            }else
            mensaje.setMessage("ok");
            mensaje.setCurrent(consultaPromsyService.unidadesVendidasTrimestre(parametro));
            return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
        } catch (Exception e) {

            log.info("Error->ConsultasPromsyResource:unidadesVendidasTrimestre");
        }
        mensaje.setStatus(HttpStatus.FORBIDDEN.value());
        mensaje.setMessage("Error");
        return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
    }
    @PostMapping("/consultaPromsy/montosVendidosTrimestre")
    public ResponseEntity<StatusMessage>montosVendidosTrimestre(@RequestBody Parametro parametro){
        StatusMessage mensaje = new StatusMessage();
        try {
            mensaje.setStatus(HttpStatus.OK.value());
            if (consultaPromsyService.montosVendidosTrimestre(parametro).compareTo(BigDecimal.ZERO)==0){
                mensaje.setMessage("No se encontro ningun registro.");
            }else
            mensaje.setMessage("ok");

            mensaje.setCurrent(consultaPromsyService.montosVendidosTrimestre(parametro));
            return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
        } catch (Exception e) {

            log.info("Error->ConsultasPromsyResource:montosVendidosTrimestre");
        }
        mensaje.setStatus(HttpStatus.FORBIDDEN.value());
        mensaje.setMessage("Error");
        return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
    }
    @PostMapping("/consultaPromsy/comparativaTrimestres")
    public ResponseEntity<StatusMessage>comparativaTrimestres(@RequestBody Parametro parametro){
        StatusMessage mensaje = new StatusMessage();
        try {
            mensaje.setStatus(HttpStatus.OK.value());
            if(consultaPromsyService.comparativaTrimestre(parametro)==null){
                mensaje.setMessage("No se encontro ningun registro.");
            }else
            mensaje.setMessage("ok");

            mensaje.setCurrent(consultaPromsyService.comparativaTrimestre(parametro));
            return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
        } catch (Exception e) {

            log.info("Error->ConsultasPromsyResource:montosVendidosTrimestre");
        }
        mensaje.setStatus(HttpStatus.FORBIDDEN.value());
        mensaje.setMessage("Error");
        return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
    }
    @PostMapping("/consultaPromsy/comparativaProveedorVSTodos")
    public ResponseEntity<StatusMessage>comparativaProveedorVSTodos(@RequestBody Parametro parametro){
        StatusMessage mensaje = new StatusMessage();
        try {
            mensaje.setStatus(HttpStatus.OK.value());
            if(consultaPromsyService.comparativaProveedorVSTodos(parametro)==null){
                mensaje.setMessage("No se encontro ningun registro.");
            }else
            mensaje.setMessage("ok");
            mensaje.setCurrent(consultaPromsyService.comparativaProveedorVSTodos(parametro));
            return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
        } catch (Exception e) {

            log.info("Error->ConsultasPromsyResource:comparativaProveedorVSTodos");
            e.printStackTrace();
        }
        mensaje.setStatus(HttpStatus.FORBIDDEN.value());
        mensaje.setMessage("Error");
        return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
    }
    @PostMapping("/consultaPromsy/top5ProductosMasVendidos")
    public ResponseEntity<StatusMessage>top5ProductosMasVendidos(@RequestBody Parametro parametro){
        StatusMessage mensaje = new StatusMessage();
        try {
            mensaje.setStatus(HttpStatus.OK.value());
            if (consultaPromsyService.top5ProductosMasVendidos(parametro).size()==0 || consultaPromsyService.top5ProductosMasVendidos(parametro).isEmpty() || consultaPromsyService.top5ProductosMasVendidos(parametro)==null){
                mensaje.setMessage("No se encontro ningun registro.");
            }else
            mensaje.setMessage("ok");
            mensaje.setCurrent(consultaPromsyService.top5ProductosMasVendidos(parametro));
            return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
        } catch (Exception e) {

            log.info("Error->ConsultasPromsyResource:comparativaProveedorVSTodos");
            e.printStackTrace();
        }
        mensaje.setStatus(HttpStatus.FORBIDDEN.value());
        mensaje.setMessage("Error");
        return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
    }
    @PostMapping("/consultaPromsy/unidadesVendidasPorMes")
    public ResponseEntity<StatusMessage>unidadesVendidasPorMes(@RequestBody Parametro parametro){
        StatusMessage mensaje = new StatusMessage();
        try {
            mensaje.setStatus(HttpStatus.OK.value());
            if(consultaPromsyService.unidadesVendidasPorMes(parametro).size()==0 || consultaPromsyService.unidadesVendidasPorMes(parametro).isEmpty()){
                mensaje.setMessage("No se encontro ningun registro.");
            }else
            mensaje.setMessage("ok");
            mensaje.setCurrent(consultaPromsyService.unidadesVendidasPorMes(parametro));
            return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
        } catch (Exception e) {

            log.info("Error->ConsultasPromsyResource:comparativaProveedorVSTodos");
            e.printStackTrace();
        }
        mensaje.setStatus(HttpStatus.FORBIDDEN.value());
        mensaje.setMessage("Error");
        return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
    }

    @PostMapping("/consultaPromsy/pruebas")
    public ResponseEntity<StatusMessage>pruebas(){
        StatusMessage mensaje = new StatusMessage();
        try {
            mensaje.setStatus(HttpStatus.OK.value());

                mensaje.setMessage("ok");
            ConsultasPromsyDAOImpl consulta = new ConsultasPromsyDAOImpl();
            //mensaje.setCurrent(consultaPromsyService.descontarProductosCaducadosStock());
            mensaje.setCurrent(consultaPromsyService.reporteComercial());
            return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
        } catch (Exception e) {

            log.info("Error->ConsultasPromsyResource:comparativaProveedorVSTodos");
            e.printStackTrace();
        }
        mensaje.setStatus(HttpStatus.FORBIDDEN.value());
        mensaje.setMessage("Error");
        return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
    }
}
