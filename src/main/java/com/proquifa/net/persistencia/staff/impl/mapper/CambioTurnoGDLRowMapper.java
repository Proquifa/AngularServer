package com.proquifa.net.persistencia.staff.impl.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.staff.CambioTurno;

public class CambioTurnoGDLRowMapper {
	
	public static List<CambioTurno>mapearCambioTurno(List<Object[]> list) throws ParseException{
		List<CambioTurno> lstCambioTurno = new ArrayList<CambioTurno>();
		
		for (Object[] object : list){
			CambioTurno cambioTurno = new CambioTurno();
			
			cambioTurno.setIdTrabajador(Long.parseLong(object[0].toString()));
		
			if (object[1] != null){
				String sFecha = object[1].toString();
				SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
				Date dFecha = formatoFecha.parse(sFecha);
				cambioTurno.setFecha(dFecha);
			}
			if (object[2] != null){
				cambioTurno.setTurno(object[2].toString());
			}
			lstCambioTurno.add(cambioTurno);
		}
		
		return lstCambioTurno;
	}

}
