package com.proquifa.net.negocio.reportes.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.comun.NivelIngreso;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.ventas.reportes.seguimientos.HistorialPartidaEnSeguimiento;
import com.proquifa.net.modelo.ventas.reportes.seguimientos.PartidaCotizacionEnSeguimiento;
import com.proquifa.net.modelo.ventas.reportes.seguimientos.SeguimientoCotizacion;
import com.proquifa.net.negocio.reportes.SeguimientoCotizacionesService;
import com.proquifa.net.persistencia.comun.EmpleadoDAO;
import com.proquifa.net.persistencia.comun.NivelIngresoDAO;
import com.proquifa.net.persistencia.reportes.SeguimientoCotizacionesDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("seguimientoCotizacionesService")
public class SeguimientoCotizacionesServiceImpl implements SeguimientoCotizacionesService{
	@Autowired
	SeguimientoCotizacionesDAO seguimientoCotizacionesDAO;
	@Autowired
	EmpleadoDAO empleadoDAO;
	@Autowired
	NivelIngresoDAO nivelIngresoDAO;
	
private	Funcion f;	
	public List<SeguimientoCotizacion> obtenerCotizacionesEnSeguimiento(
			String Cliente, String Empleado,int confirmacion) throws ProquifaNetException {
		try{
			f = new Funcion();
		List<NivelIngreso> niveles = nivelIngresoDAO.findLimitesNivelIngreso();
		return seguimientoCotizacionesDAO.findSeguimientoCotizacion(Cliente, Empleado, confirmacion, niveles);}
		catch(Exception e){
			f.enviarCorreoAvisoExepcion(e, Cliente,Empleado, "Confirmacion:"+confirmacion);
			return new ArrayList<SeguimientoCotizacion>();
		}
	}
	
	public List<PartidaCotizacionEnSeguimiento> obtenerPartidasPorCotizacionEnSeguimiento(
			String FolioCotizacion) throws ProquifaNetException {
		try{
		return seguimientoCotizacionesDAO.findPartidasPorCotizacionEnSeguimiento(FolioCotizacion);
		}
		catch(Exception e){
			f.enviarCorreoAvisoExepcion(e, FolioCotizacion);
			return new ArrayList<PartidaCotizacionEnSeguimiento>();		
		}
	}
		
	
	public List<HistorialPartidaEnSeguimiento> obtenerHistorialSeguimientoXPartida(
			Long idPCotiza) throws ProquifaNetException {
		try{
		return seguimientoCotizacionesDAO.finHistorialSeguimientoXPartida(idPCotiza);
		}
		catch(Exception e){
			f.enviarCorreoAvisoExepcion(e, idPCotiza);
			return new ArrayList<HistorialPartidaEnSeguimiento>();
		}
		}
	
	public List<SeguimientoCotizacion> obtenerCotizacionesEnSeguimientoEnPartidas(int confirmacion,String Cliente, String empleado,
			String Tipo,String Marca,String Control, int Master)throws ProquifaNetException {
		try{
			SeguimientoCotizacion reg = null;
			String condiciones="";
			String nivelEmpleado="";
			Integer count=0;
			if(Tipo!=null && !Tipo.equals("")){
				condiciones+=" AND PROD.Tipo='"+Tipo+"'";
			}
			if(Marca!=null && !Marca.equals("")){
				condiciones+=" AND PROD.Fabrica='"+Marca+"'";
			}
			if(Control!=null && !Control.equals("")){
				condiciones+=" AND PROD.Control='"+Control+"'";
			}
			if (!empleado.equals(null) && !empleado.equals("")){
				Empleado e = empleadoDAO.obtenerEmpleadoPorUsuario(empleado);
				nivelEmpleado=e.getNivelGeneral();
				if(!e.getNivelGeneral().equals("Directivo") && !e.getNivelGeneral().equals("Gerente")){
					if(!e.getNombreFuncion().equals("Ejecutivo de Ventas")){
						if(empleado!=null && !empleado.equals("") && Master==1){					
							List<String> usuarios = empleadoDAO.finEquipoESAC(e.getIdEmpleado());
							condiciones+= " AND (";
							for(String user:usuarios){
									count ++;
									condiciones += "Cotizas.Cotizo='" + user + "'";
									if(count<usuarios.size()){
										condiciones +=" OR ";
									}
							}
							condiciones +=") ";
						}else if (e.getNombreFuncion().equals("Ejecutivo de Servicio a Clientes")){
							condiciones+= " AND Cotizas.Cotizo='"+ empleado +"' ";
						}
					}
				}
			}
			List<NivelIngreso> niveles = nivelIngresoDAO.findLimitesNivelIngreso();
			List<SeguimientoCotizacion> lista = seguimientoCotizacionesDAO.findSeguimientoCotizacionEnPartida(confirmacion, Cliente, empleado, condiciones,Master, niveles);
			if(!nivelEmpleado.equals("Directivo") && !nivelEmpleado.equals("Gerente") && !nivelEmpleado.equals("")){
				if(Master==0){
					ListIterator<SeguimientoCotizacion> lt = lista.listIterator();
					while(lt.hasNext()){
						reg = lt.next();
						if(reg.getTipoSeguimiento().equals("Cancelable")){
							lt.remove();
						}
					}
				}	
			}
		return lista;}
		catch(Exception e){
			f.enviarCorreoAvisoExepcion(e, "Confirmacion:"+confirmacion,Cliente,empleado,Tipo,Marca,Control,"Master:"+Master);
			return new ArrayList<SeguimientoCotizacion>();
		}
	}
}