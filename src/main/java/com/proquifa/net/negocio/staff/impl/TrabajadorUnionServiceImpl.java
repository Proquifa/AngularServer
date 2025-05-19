package com.proquifa.net.negocio.staff.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.staff.Trabajador;
import com.proquifa.net.negocio.staff.TrabajadorUnionService;
import com.proquifa.net.persistencia.staff.TrabajadorDAO;
import com.proquifa.net.persistencia.staff.TrabajadorDAO2;

/**
 * Consulta los trabajadores de Proquifa y Ryndem uniendolos
 * @author Isaac Garcia
 *
 */
@Service("trabajadorUnionService")
public class TrabajadorUnionServiceImpl implements TrabajadorUnionService {
	
	final Logger log = LoggerFactory.getLogger(TrabajadorUnionServiceImpl.class);
	
	@Autowired
	TrabajadorDAO trabajadorDAO;
	@Autowired
	TrabajadorDAO2 trabDAO2;
	
	public List<Trabajador>getTrabajadorUnion() throws ProquifaNetException{
		
		List<Trabajador> trabajadores = new ArrayList<Trabajador>();
		
		List<Trabajador> trabajadoresProquifa = null;
		List<Trabajador> trabajadoresRyndem = null;
		List<Trabajador> trabajadoresGuadalajara = null;
		
		try {
			trabajadoresProquifa = this.trabajadorDAO.consultaTrabajador();
			trabajadoresRyndem = trabDAO2.consultarTrabajador2();
			trabajadoresGuadalajara = trabDAO2.consultarTrabajadorGDL();
		} catch (Exception e) {
			log.info("Error Union");
			log.info(e.toString());
		}
		
		
		if (trabajadoresProquifa != null && trabajadoresProquifa.size() > 0) {
			trabajadores.addAll(trabajadoresProquifa);
		}
		
		if (trabajadoresRyndem != null && trabajadoresRyndem.size() > 0) {
			for (Trabajador trabajadorRyndem : trabajadoresRyndem) {
				boolean repetido = false;
				for (Trabajador trabajador : trabajadores) {
					if (trabajador.getIdTrabajador().equals(trabajadorRyndem.getIdTrabajador()) && 
							trabajador.getNombreCorto().toString().equals(trabajadorRyndem.getNombreCorto())) {
						repetido = true;
					}
				}
				if (!repetido) {
					trabajadores.add(trabajadorRyndem);
				}
			}
		}
		
		if (trabajadoresGuadalajara != null && trabajadoresGuadalajara.size() > 0) {
			for (Trabajador trabajadorGuadalajara : trabajadoresGuadalajara) {
				boolean repetido = false;
				for (Trabajador trabajador : trabajadores) {
					if (trabajador.getIdTrabajador().equals(trabajadorGuadalajara.getIdTrabajador()) && 
							trabajador.getNombreCorto().toString().equals(trabajadorGuadalajara.getNombreCorto())) {
						repetido = true;
					}
				}
				log.info("",trabajadorGuadalajara.getIdTrabajador());
				if (!repetido) {
					trabajadores.add(trabajadorGuadalajara);
				}
			}
		}
		
		return trabajadores;
	}
}
