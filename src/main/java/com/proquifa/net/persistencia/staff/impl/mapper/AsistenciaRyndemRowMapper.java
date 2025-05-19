package com.proquifa.net.persistencia.staff.impl.mapper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.staff.Asistencia;

public class AsistenciaRyndemRowMapper {
	
	public static List<Asistencia>mapearAistencia(List<Object[]> list) throws ParseException{
		List<Asistencia> lstAsistencia =  new ArrayList<Asistencia>();
		
			for (Object[] object : list){
				Asistencia asistencia = new Asistencia();
				asistencia.setIdTrabajador(Long.parseLong(object[0].toString()));
				asistencia.setNombreCorto(object[1].toString());
				if (object[2] != null){
					String sFecha = object[2].toString();
					SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
					Date dFecha = formatoFecha.parse(sFecha);
					asistencia.setFecha(dFecha);
				}
				if (object[3] != null){
					asistencia.setHora(object[3].toString());
				}
				if (object[4] != null){
					String sFecha = object[4].toString();
					DateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					Date dChecada = formatoFecha.parse(sFecha);
					asistencia.setChecada(dChecada);
				}
				if (object[5] != null){
					asistencia.setTipoChecada(object[5].toString());
				}
				asistencia.setArea(object[6].toString());
				asistencia.setDepto(object[7].toString());
				asistencia.setCategoria(object[8].toString());
				
				if (object[9] != null){
					asistencia.setRotacion(object[9].toString());
				}
				if (object[10] != null){
					asistencia.setIncidencia(object[10].toString());
				}
				asistencia.setLocalidad("CUERNAVACA");
				lstAsistencia.add(asistencia);
			}
		return lstAsistencia;
	}

}
