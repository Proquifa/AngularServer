package com.proquifa.net.persistencia.staff.impl.mapper;

import java.util.ArrayList;
import java.util.List;

import com.proquifa.net.modelo.staff.Trabajador;

public class TrabajadorRowMapper2 {

	public static List<Trabajador>mapearTrabajador(List<Object[]> list){
		List<Trabajador> lstTrabajador = new ArrayList<Trabajador>();
		
		for (Object[] object : list){
			Trabajador trabajador = new Trabajador();
			trabajador.setIdTrabajador(Long.parseLong(object[0].toString()));
			trabajador.setNombreCorto(object[1].toString());
			lstTrabajador.add(trabajador);
		}
		return lstTrabajador;
	}
}
