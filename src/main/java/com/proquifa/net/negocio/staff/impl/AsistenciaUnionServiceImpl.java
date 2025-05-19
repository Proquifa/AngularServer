package com.proquifa.net.negocio.staff.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.staff.Asistencia;
import com.proquifa.net.modelo.staff.CambioTurno;
import com.proquifa.net.modelo.staff.Turno;
import com.proquifa.net.negocio.staff.AsistenciaUnionService;
import com.proquifa.net.persistencia.staff.AsistenciaDAO;
import com.proquifa.net.persistencia.staff.CambioTurnoDAO;
import com.proquifa.net.persistencia.staff.TurnoDAO;

@Service("asistenciaUnionService")
public class AsistenciaUnionServiceImpl implements AsistenciaUnionService{
	
	@Autowired
	AsistenciaDAO asistenciaDAO;
	@Autowired
	CambioTurnoDAO cambioTurnoDAO;
	@Autowired
	TurnoDAO turnoDAO;
	
	Funcion funcion;
	
	public List<Asistencia> getconsultaAsistenciaProquifa(Date fechaInicio, Date fechaFin, Long idTrabajador, String tipoChecada, String area, String depto, String categoria, String incidencia, String nombreTrabajador) throws ProquifaNetException{
		try {
			return this.asistenciaDAO.consultarAsistenciaProquifa(fechaInicio, fechaFin, idTrabajador, tipoChecada, area, depto, categoria, incidencia, nombreTrabajador);
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			return new ArrayList<Asistencia>();
		}  
	}
	
	public List<Asistencia>getConsultaAsistenciaRyndem(Date fechaInicio, Date fechaFin, Long idTrabajador, String tipoChecada, String area, String depto, String categoria, String incidencia, String nombreTrabajador) throws ProquifaNetException{
		try {
			return this.asistenciaDAO.consultarAsistenciaRyndem(fechaInicio, fechaFin, idTrabajador, tipoChecada, area, depto, categoria, incidencia, nombreTrabajador);
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			return new ArrayList<Asistencia>();
		}
	}
	
	@SuppressWarnings("deprecation")
	public List<Asistencia>getAsistenciaUnion(Date fechaInicio, Date fechaFin, Long idTrabajador, String tipoChecada, String area, String depto, String categoria, String incidencia, String localidad, String nombreTrabajador) throws ProquifaNetException, ParseException{
		
		List<CambioTurno> cambioTur = null;
		List<CambioTurno> cambioTurRyn = null;
		List<CambioTurno> cambioTurGDL = null;
		List<Turno> turno = null;
		List<Turno> turnoRyn = null;
		List<Turno> turnoGDL = null;
		
		List<Asistencia> asis1 = null;
		List<Asistencia> asis2 = null;
		List<Asistencia> asis3 = null;
		List<Asistencia> lstUnion = new ArrayList<Asistencia>();
		
		String fechaString = fechaInicio.toString();
		
		try{
			
			if (localidad.equals("") || localidad.equals("DISTRITO FEDERAL")){ //Si es del DF trae los datos de Proquifa
				try {
					asis1 = this.asistenciaDAO.consultarAsistenciaProquifa(fechaInicio, fechaFin, idTrabajador, tipoChecada, area, depto, categoria, incidencia, nombreTrabajador);
					cambioTur = this.asistenciaDAO.consultarCambioTurnoProquifa(new Date(fechaString), fechaFin);
					turno = this.asistenciaDAO.consultarTurnoProquifa();
				} catch (Exception e) {System.out.print("DISTRITO FEDERAL" + e.toString());}
			}
			
			if(localidad.equals("") || localidad.equals("CUERNAVACA")){ //Si es Cuernavaca trae los Datos de Ryndem
				try {
					asis2 = asistenciaDAO.consultarAsistenciaRyndem(new Date(fechaString), fechaFin, idTrabajador, tipoChecada, area, depto, categoria, incidencia, nombreTrabajador);
					cambioTurRyn =  asistenciaDAO.consultarCambioTurnoRyndem(new Date(fechaString), fechaFin);
					turnoRyn = asistenciaDAO.consultarTurnoRyndem();
				} catch (Exception e) { System.out.print("CUERNAVACA" + e.toString());}
				
			}
			
			if(localidad.equals("") || localidad.equals("GUADALAJARA")){ //Si es Guadalajara trae los Datos de Guadalajara
				try {
					asis3 = asistenciaDAO.consultarAsistenciaGDL(new Date(fechaString), fechaFin, idTrabajador, tipoChecada, area, depto, categoria, incidencia, nombreTrabajador);
					cambioTurGDL =  asistenciaDAO.consultarCambioTurnoGDL(new Date(fechaString), fechaFin);
					turnoGDL = asistenciaDAO.consultarTurnoGDL();
				} catch (Exception e) {System.out.print("GUADALAJARA" + e.toString());}
				
			}
			
			//Agregamos a la lstUnion las Assitencias de Proquifa y Ryndem
			if (asis1 != null){
				if (!asis1.isEmpty()){
					System.out.print("CDMX =====>" + asis1.size());
					lstUnion.addAll(asis1);
				}
			}
			
			if (asis2 != null){
				if (!asis2.isEmpty()){
					System.out.print("CUERNAVACA =====>" + asis2.size());
					lstUnion.addAll(asis2);
				}
			}
			
			if (asis3 != null){
				if (!asis3.isEmpty()){
					System.out.print("GUADALAJARA =====>" + asis3.size());
					lstUnion.addAll(asis3);
				}
			}
			
			if (!lstUnion.isEmpty()){
				for (int i = 0; i < lstUnion.size(); i++){
					
					if (lstUnion.get(i).getIncidencia() == null) { lstUnion.get(i).setIncidencia("NINGUNA");}
					
					if (lstUnion.get(i).getTipoChecada() != null){
						String sTipoChecada = lstUnion.get(i).getTipoChecada().toString().toUpperCase();
						Date fecChecada = lstUnion.get(i).getChecada();
						Long idTra = lstUnion.get(i).getIdTrabajador();
						Date fec = lstUnion.get(i).getFecha();
						String rota = lstUnion.get(i).getRotacion();
						String sLocalidadUser = lstUnion.get(i).getLocalidad();
						
						if (sLocalidadUser.equals("DISTRITO FEDERAL")) {
							if (cambioTur != null) {
								if (!cambioTur.isEmpty()){
									for (int x = 0; x<cambioTur.size(); x++){
										if (cambioTur.get(x).getIdTrabajador() == idTra && cambioTur.get(x).getFecha().compareTo(fec) == 0){	
											rota = cambioTur.get(x).getTurno();
											break;
										}
									}
								}
							}
						}
						else if (sLocalidadUser.equals("CUERNAVACA")){
							if (cambioTurRyn != null) {
								if (!cambioTurRyn.isEmpty()){
									for (int x = 0; x<cambioTurRyn.size(); x++){
										if (cambioTurRyn.get(x).getIdTrabajador() == idTra && cambioTurRyn.get(x).getFecha().compareTo(fec) == 0){	
											rota = cambioTurRyn.get(x).getTurno();
											break;
										}
									}
								}
							}
						}
						else if (sLocalidadUser.equals("GUADALAJARA")){
							if (cambioTurGDL != null) {
								if (!cambioTurGDL.isEmpty()){
									for (int x = 0; x<cambioTurGDL.size(); x++){
										if (cambioTurGDL.get(x).getIdTrabajador() == idTra && cambioTurGDL.get(x).getFecha().compareTo(fec) == 0){	
											rota = cambioTurGDL.get(x).getTurno();
											break;
										}
									}
								}
							}
						}
						
						
						if (rota != null && (turno!= null || turnoRyn != null || turnoGDL != null)){
							Date entradaHasta = null;
							Date salida = null;
							Date comida = null;
							Date comidaRegreso = null;
							Date comidaTiempo = null;
							
							if (sLocalidadUser.equals("DISTRITO FEDERAL")){
								if (!turno.isEmpty()){
									for (int x = 0; x < turno.size(); x++){
										if (turno.get(x).getTurno().equals(rota)){
											entradaHasta = turno.get(x).getEntradaHasta();
											salida = turno.get(x).getSalida();
											comida = turno.get(x).getComida();
											comidaRegreso = turno.get(x).getComidaRegreso();
											comidaTiempo = turno.get(x).getComidaTiempo();
											break;
										}
									}
								}
							}
							else if (sLocalidadUser.equals("CUERNAVACA")){
								if (!turnoRyn.isEmpty()){
									for (int x = 0; x < turnoRyn.size(); x++){
										if (turnoRyn.get(x).getTurno().equals(rota)){
											entradaHasta = turnoRyn.get(x).getEntradaHasta();
											salida = turnoRyn.get(x).getSalida();
											comida = turnoRyn.get(x).getComida();
											comidaRegreso = turnoRyn.get(x).getComidaRegreso();
											comidaTiempo = turnoRyn.get(x).getComidaTiempo();
											break;
										}
									}
								}
							}
							else if (sLocalidadUser.equals("GUADALAJARA")){
								if (!turnoGDL.isEmpty()){
									for (int x = 0; x < turnoGDL.size(); x++){
										if (turnoGDL.get(x).getTurno().equals(rota)){
											entradaHasta = turnoGDL.get(x).getEntradaHasta();
											salida = turnoGDL.get(x).getSalida();
											comida = turnoGDL.get(x).getComida();
											comidaRegreso = turnoGDL.get(x).getComidaRegreso();
											comidaTiempo = turnoGDL.get(x).getComidaTiempo();
											break;
										}
									}
								}
							}
							
							//Date fecChecada = lstUnion.get(i).getChecada();
							if (sTipoChecada.equals("E") || sTipoChecada.equals("S")) { //En caso de que en tipo de Chcecada tenga "E" o "S"
								
								if (i>0) {
									if (lstUnion.get(i-1).getTipoChecada() != null){ //Si es != null tambien trea Hora, Checada
								
										if (lstUnion.get(i).getIdTrabajador().equals(lstUnion.get(i-1).getIdTrabajador()) && lstUnion.get(i).getFecha().equals(lstUnion.get(i-1).getFecha()) && lstUnion.get(i).getNombreCorto().equals(lstUnion.get(i-1).getNombreCorto())){//Si es el mismo trabajador y el mismo dia de chacada
											
											String sTipoChec = lstUnion.get(i-1).getTipoChecada();
											SimpleDateFormat formatoFecha = new SimpleDateFormat ("hh:mm aa");//Formato de hora : minutos AM/PM
											
											//Se obtiene primero las horas por que tienen diferente fecha y se vuelven a pasar a Date para que tengan la misma fecha 
											//y poder comparar las horas.
											if (comida == null){
												 continue;
											}
											String sHoraComida =  formatoFecha.format(comida);
											String sHoraComidaReg = formatoFecha.format(comidaRegreso);
											String sHoraChecada = formatoFecha.format(fecChecada);
											String sHoraSalida = formatoFecha.format(salida);
											
											Date fechComida1 = formatoFecha.parse(sHoraComida);
											Date fechComidaRegreso = formatoFecha.parse(sHoraComidaReg);
											Date fechChek = formatoFecha.parse(sHoraChecada);
											Date fecSalida = formatoFecha.parse(sHoraSalida);
											
											//Si la anterior es ENTRADA DE TURNO entonces la actual es SALIDA A COMER solo si esta en el rango de comida
											if (sTipoChec.equals("ENTRADA DE TURNO")){
												
												int comparaComida = fechChek.compareTo(fechComida1); //Compara la hora checada con la hora de la comida
												int comparaCRegreso = fechChek.compareTo(fechComidaRegreso); //Compara la hora checada con la hora del Regreso de Comer
												
												if ((comparaComida >= 0) && (comparaCRegreso <= 0)){
													lstUnion.get(i).setTipoChecada("SALIDA A COMER");
												}
												else{ //Si la checada Actual no es "SALIDA A COMER"
													int iSalir = fechChek.compareTo(fecSalida);
													
													if(iSalir >= 0){
														lstUnion.get(i).setTipoChecada("SALIDA DE TURNO");
													}
													else{lstUnion.get(i).setTipoChecada("SALIDA IMPREVISTA");}
												}
											}
											else if (sTipoChec.equals("SALIDA A COMER")){//Si la anterior es SALIDA A COMER entonces la actual es ENTRADA DE COMER
												
												int iComparaRegComer = fechChek.compareTo(fecSalida); //Comparamos con la Salida para poder determinar posteriormente si se tardo mas del tiempo de Comer
												
												if (iComparaRegComer < 0){
													lstUnion.get(i).setTipoChecada("ENTRADA DE COMER");
												}
												else{lstUnion.get(i).setTipoChecada("SALIDA DE TURNO");}
											
											}
											else if (sTipoChec.equals("ENTRADA DE COMER")){//Si la anterios es ENTRADA DE COMER la actual es SALIDA DE TURNO
												int iComparaSalida = fechChek.compareTo(fecSalida);
												
												if (iComparaSalida >= 0){
													lstUnion.get(i).setTipoChecada("SALIDA DE TURNO");
												}
												else{lstUnion.get(i).setTipoChecada("SALIDA IMPREVISTA");} //Si salio antes de su Hora de Salida
											}
											else if (sTipoChec.equals("SALIDA IMPREVISTA")){ //Si la anterior es SALIDA IMPREVISTA entonces la actual es ENTRADA IMPREVISTA
												
												int iComparaSalida = fechChek.compareTo(fecSalida);
												
												if (iComparaSalida < 0){
													lstUnion.get(i).setTipoChecada("ENTRADA IMPREVISTA");
												}
												else{lstUnion.get(i).setTipoChecada("SALIDA DE TURNO");}
												
											}
											else if (sTipoChec.equals("ENTRADA IMPREVISTA")){ //Si la anterior es ENTRADA IMPREVISTA entonces la actual es SALIDA DE TURNO
												
												int comparaComida = fechChek.compareTo(fechComida1); //Compara la hora checada con la hora de la comida
												int comparaCRegreso = fechChek.compareTo(fechComidaRegreso); //Compara la hora checada con la hora del Regreso de Comer
												
												if ((comparaComida >= 0) && (comparaCRegreso <= 0)){
													lstUnion.get(i).setTipoChecada("SALIDA A COMER");
												}
												else{
													int iComparaSalida = fechChek.compareTo(fecSalida);
													
													if (iComparaSalida >= 0){
														lstUnion.get(i).setTipoChecada("SALIDA DE TURNO");
													}
													else{lstUnion.get(i).setTipoChecada("SALIDA IMPREVISTA");}
												}
											}
											else if (sTipoChec.equals("SALIDA DE TURNO")) {
												lstUnion.get(i).setTipoChecada("SALIDA DE TURNO");
											}
											else{//Si no es ninguna de las anteriores entnces es ENTRADA DE TURNO
												lstUnion.get(i).setTipoChecada("ENTRADA DE TURNO");
											}
											
										}
										else{//Si el anterior es otro trabjador y es E o S entonces es Entrada de Turno
											lstUnion.get(i).setTipoChecada("ENTRADA DE TURNO");
										}
									}
									else{//Si es el primer registro del Trabajador y es "E" o "S"
										lstUnion.get(i).setTipoChecada("ENTRADA DE TURNO");
									}
								} // ---- TERMINA -- if (i>0){
								
								else{// Si es el primer registro de toda la consulta y es "E" o "S"
									lstUnion.get(i).setTipoChecada("ENTRADA DE TURNO");
								} //----- TERMINA -- IF Y ELSE DE if (i>0) {
						}
							//Si no es E, ni S, -sino ENTRADA IMPREVISTA
							else if (sTipoChecada.equals("ENTRADA IMPREVISTA")){
								SimpleDateFormat formatoFecha = new SimpleDateFormat ("hh:mm aa");//Formato de hora : minutos AM/PM
								String sHoraSalida = formatoFecha.format(salida);
								String sHoraChecada = formatoFecha.format(fecChecada);
										
								Date fechChek = formatoFecha.parse(sHoraChecada);
								Date fecSalida = formatoFecha.parse(sHoraSalida);
								
								if (i>0) {
									
									//Si son distintos trabajadores entonces quiere decir que es el primer registro del trabajdor de ese dia
									if (!lstUnion.get(i).getIdTrabajador().equals(lstUnion.get(i-1).getIdTrabajador())){
										
										int iComparaSalida = fechChek.compareTo(fecSalida);
										
										if (iComparaSalida >= 0){
											lstUnion.get(i).setTipoChecada("SALIDA DE TURNO");
										}else {
											lstUnion.get(i).setTipoChecada("ENTRADA DE TURNO");
										}
									}
								}
								else{
									int iComparaSalida = fechChek.compareTo(fecSalida);
									
									if (iComparaSalida >= 0){
										lstUnion.get(i).setTipoChecada("SALIDA DE TURNO");
									}else {
										lstUnion.get(i).setTipoChecada("ENTRADA DE TURNO");
									}
								}
							}
								
							//YA QUE TIENEN ASIGNADO CORRECTAMENTE EL TIPO DE CHECADA SE EVALUAN LOS TIPOS DE INCIDENCIA
							sTipoChecada = lstUnion.get(i).getTipoChecada().toString().toUpperCase();
									
							if (sTipoChecada.equals("ENTRADA DE TURNO")){
								int hora = lstUnion.get(i).getChecada().getHours();
								int minuto = lstUnion.get(i).getChecada().getMinutes();
								int horaEntHasta = entradaHasta.getHours();
								int minutoEntHasta = entradaHasta.getMinutes();
										
								if (hora == horaEntHasta && minuto > minutoEntHasta){
									lstUnion.get(i).setIncidencia("RETARDO");
								}
								else if (hora > horaEntHasta){
									lstUnion.get(i).setIncidencia("RETARDO");
								}
								}
								else if (sTipoChecada.equals("ENTRADA DE COMER")){
										
									if (i > 0 && lstUnion.get(i-1).getChecada() != null){ //Se verifica que sea >0 por que se toma una posicion anterior
										String sTipoChecadaBack = lstUnion.get(i-1).getTipoChecada().toString().toUpperCase();
										
										if (sTipoChecadaBack.equals("SALIDA A COMER")){
												Calendar cal1 = Calendar.getInstance();
												Calendar cal2 = Calendar.getInstance();
												//Registro anterior
												int yearBack = lstUnion.get(i-1).getChecada().getYear();
												int mesBack = lstUnion.get(i-1).getChecada().getMonth();
												int diaBack = lstUnion.get(i-1).getChecada().getDay();
												int horaBack = lstUnion.get(i-1).getChecada().getHours();
												int minutoBack = lstUnion.get(i-1).getChecada().getMinutes();
											
												//El registro actual
												int year = lstUnion.get(i).getChecada().getYear();
												int mes = lstUnion.get(i).getChecada().getMonth();
												int dia = lstUnion.get(i).getChecada().getDay();
												int hora = lstUnion.get(i).getChecada().getHours();
												int minuto = lstUnion.get(i).getChecada().getMinutes();
										
												cal1.set(yearBack, mesBack, diaBack, horaBack, minutoBack);
												cal2.set(year, mes, dia, hora, minuto);
											
												long milis1 = cal1.getTimeInMillis();
												long milis2 = cal2.getTimeInMillis();
												long diffMil = milis2 - milis1;
											
												long diffMinutos = diffMil / (60 * 1000);
													
												int horaCom = comidaTiempo.getHours();
												int minCom = comidaTiempo.getMinutes();
												
												int minutosComida = (horaCom * 60) + minCom; //Total de Minutos que tiene de Comida
												
												if (diffMinutos > minutosComida){
													lstUnion.get(i).setIncidencia("FUERA DE TIEMPO");
												}
											}
										}
									}
						} // ---------- TERMINA -- if (rota != null && !turno.isEmpty()){
							
					}
					else{ // Si Tipo de Checada es NULL, entonces es Falta
						if (lstUnion.get(i).getIncidencia().equals("NINGUNA")){
							lstUnion.get(i).setIncidencia("FALTA");
						}
					}
				}
			}

			//Se aplican los filtros de Incidencia y Tipo de Checada, ya que fue necesario hacerlo en codigo y no en el query
			if (!lstUnion.isEmpty()){
				
				if (!incidencia.equals("") && tipoChecada.equals("")){
					//Se verifica que sea solo por estos tipos ya que estos no se pueden filtrar en el query
					if (incidencia.equals("FALTA") || incidencia.equals("RETARDO") || incidencia.equals("FUERA DE TIEMPO") || incidencia.equals("NINGUNA")){
						List<Asistencia> lstAux = new ArrayList<Asistencia>();
						
						for (int i = 0; i < lstUnion.size(); i++){
							if (lstUnion.get(i).getIncidencia() != null){
								if (lstUnion.get(i).getIncidencia().equals(incidencia.toUpperCase())){
									lstAux.add(lstUnion.get(i));
								}
							}
						}
						if (!lstAux.isEmpty()){
							lstUnion.clear();
							lstUnion.addAll(lstAux);
						}
						else{
							//Se lumpia la lista, como trae datos y no hubo coincidencia con el filtro entonces tiene que ir vacia
							lstUnion.clear();
						}
					}
				}
				else if (incidencia.equals("") && !tipoChecada.equals("")){
					List<Asistencia> lstAux = new ArrayList<Asistencia>();
					
					for (int i = 0; i < lstUnion.size(); i++){
						if (lstUnion.get(i).getTipoChecada() != null){
							if (lstUnion.get(i).getTipoChecada().equals(tipoChecada.toUpperCase())){
								lstAux.add(lstUnion.get(i));
							}		
						}
					}
					if (!lstAux.isEmpty()){
						lstUnion.clear();
						lstUnion.addAll(lstAux);
					}
					else{
						//Se lumpia la lista, como trae datos y no hubo coincidencia con el filtro entonces tiene que ir vacia
						lstUnion.clear();
					}
				}
				else if (!incidencia.equals("") && !tipoChecada.equals("")){
					
					if (incidencia.equals("FALTA") || incidencia.equals("RETARDO") || incidencia.equals("FUERA DE TIEMPO") || incidencia.equals("NINGUNA")){
						List<Asistencia> lstAux = new ArrayList<Asistencia>();
						
						for (int i = 0; i < lstUnion.size(); i++){
							if (lstUnion.get(i).getIncidencia() != null && lstUnion.get(i).getTipoChecada() != null){
								if (lstUnion.get(i).getIncidencia().equals(incidencia.toUpperCase()) && lstUnion.get(i).getTipoChecada().equals(tipoChecada.toUpperCase())){
									lstAux.add(lstUnion.get(i));
								}
							}
						}
						
						if (!lstAux.isEmpty()){
							lstUnion.clear();
							lstUnion.addAll(lstAux);
						}
						else{
							//Se lumpia la lista, como trae datos y no hubo coincidencia con el filtro entonces tiene que ir vacia
							lstUnion.clear();
						}
					}
					else{//Solo se toma el tipo de checada
						List<Asistencia> lstAux = new ArrayList<Asistencia>();
						
						for (int i = 0; i < lstUnion.size(); i++){
							if (lstUnion.get(i).getTipoChecada() != null){
								if (lstUnion.get(i).getTipoChecada().equals(tipoChecada.toUpperCase())){
									lstAux.add(lstUnion.get(i));
								}		
							}
						}
						
						if (!lstAux.isEmpty()){
							lstUnion.clear();
							lstUnion.addAll(lstAux);
						}
						else{
							//Se lumpia la lista, como trae datos y no hubo coincidencia con el filtro entonces tiene que ir vacia
							lstUnion.clear();
						}
					}	
				}
			}
			return lstUnion;
			
		}catch(Exception e){
			new Funcion().enviarCorreoAvisoExepcion(e, "\nFechaInicio: "+ fechaInicio,"\nFechaFin: "+fechaFin,"\nidTrabajador: "+idTrabajador,
					"\nTipoChecada: "+tipoChecada,"\nArea: "+area,"\nDepto: "+depto,"\nCategoria: "+categoria,"\nIncidencia: "+incidencia,
					"\nNombreTrabajador: "+nombreTrabajador);
			return new ArrayList<Asistencia>();
		}
	}

}
