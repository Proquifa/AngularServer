package com.proquifa.net.persistencia.staff.impl.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.staff.Turno;

public class TurnoRyndemRowMapper {
	
	public static List<Turno>mapearTurno(List<Object[]> list) throws ParseException{
		List<Turno> lstTurno = new ArrayList<Turno>();
		
		for (Object[] object : list){
			Turno turno =  new Turno();
			
			turno.setTurno(object[0].toString()); //Turno
			
			if (object[1] != null){ //Entrada Hasta
				String sFecha = object[1].toString();
				SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Date dFecha = formatoFecha.parse(sFecha);
				turno.setEntradaHasta(dFecha);
			}
			
			if (object[2] != null){ //Salida
				String sFecha = object[2].toString();
				SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Date dFecha = formatoFecha.parse(sFecha);
				turno.setSalida(dFecha);
			}
			
			if (object[3] != null){ //Comida
				String sFecha = object[3].toString();
				SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Date dFecha = formatoFecha.parse(sFecha);
				turno.setComida(dFecha);
			}
			
			if (object[4] != null){ //Comida Regreso
				String sFecha = object[4].toString();
				SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Date dFecha = formatoFecha.parse(sFecha);
				turno.setComidaRegreso(dFecha);
			}
			
			if (object[5] != null){ //Comida Tiempo
				String sFecha = object[5].toString();
				SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Date dFecha = formatoFecha.parse(sFecha);
				turno.setComidaTiempo(dFecha);
			}
			
			lstTurno.add(turno);
		}
		
		return lstTurno;
	}

}
