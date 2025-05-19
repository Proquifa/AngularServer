package com.proquifa.net.scheduled;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.proquifa.net.modelo.despachos.mensajero.AsignarMensajero;
import com.proquifa.net.negocio.despachos.mensajero.MensajeroService;

/**
 * @author ymendez
 *
 */
@Component
public class AsignarMensajeroAutomatico {
	
	final Logger log = LoggerFactory.getLogger(AsignarMensajeroAutomatico.class);
	
	@Autowired
	MensajeroService mensajeroService;

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	@Scheduled(cron = "0 0 19 * * *")
	public void publicarRuta() {
		log.info("Publicar AsignarMensajero se ejecuto a las;  " + dateFormat.format(new Date()));
		
		try {
			List<AsignarMensajero> lista = mensajeroService.obtenerDatosDetalleAsignarMensajero(0, "Abierto");
			List<List<AsignarMensajero>> listaPublicar = new ArrayList<List<AsignarMensajero>>();
			Map<String, List<AsignarMensajero>> map = new HashMap<String, List<AsignarMensajero>>();
			Map<String, List<String>> mapClientes = new HashMap<String, List<String>>();
			
			for (AsignarMensajero asignarMensajero : lista) {
				if (map.get(asignarMensajero.getIdMensajero().toString()) != null) {
					map.get(asignarMensajero.getIdMensajero().toString()).add(asignarMensajero);
					if (mapClientes.get(asignarMensajero.getCliente()) != null) {
						mapClientes.get(asignarMensajero.getCliente()).add(asignarMensajero.getFolio());
					}
				} else {
					List<AsignarMensajero> ruta = new ArrayList<AsignarMensajero>();
					map.put(asignarMensajero.getIdMensajero().toString(), ruta);
					map.get(asignarMensajero.getIdMensajero().toString()).add(asignarMensajero);
					List<String> folios = new ArrayList<String>();
					folios.add(asignarMensajero.getFolio());
					asignarMensajero.setFolios(folios);
					listaPublicar.add(ruta);
					mapClientes.put(asignarMensajero.getCliente(), folios);
				}
			}
			
//			mensajeroService.publicarRuta(listaPublicar);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
