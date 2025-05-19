package com.proquifa.net.negocio.ventas.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;


import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.comun.Funcion;
import com.proquifa.net.modelo.comun.TiempoProceso;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.ventas.PartidaCotizacion;
import com.proquifa.net.negocio.ventas.PartidaCotizacionService;

import com.proquifa.net.persistencia.comun.EmpleadoDAO;
import com.proquifa.net.persistencia.comun.ProductoDAO;

import com.proquifa.net.persistencia.ventas.PartidaCotizacionDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author eakoji
 * fecha: lunes 25 de abril, 2011
 */
@Service("partidaCotizacionService")
public class PartidaCotizacionServiceImpl implements PartidaCotizacionService {

	/**
	 * Objeto para acceso a la lista de partidas de cotizacion
	 */
	@Autowired
	PartidaCotizacionDAO partidaCotizacionDAO;
	@Autowired
	ProductoDAO productoDAO;

	@Autowired
	EmpleadoDAO empleadoDAO;
	
	private Funcion util = null;
	
	
	/* (non-Javadoc)
	 * @see mx.com.proquifa.proquifanet.negocio.ventas.PartidaCotizacionService#obtenerPartidaCotizacion(java.lang.String)
	 */
	public List<PartidaCotizacion> obtenerPartidaCotizacion(
			String folioCotizacion, String tipo) throws ProquifaNetException {
		List<PartidaCotizacion> listaDePartida = partidaCotizacionDAO.obtenerPartidasCotizacion(folioCotizacion, tipo);
		return listaDePartida;
	}
	
	public PartidaCotizacion obtenerPartidaPorId(String folioDeCotizacion, String idPCotiza){
		return null;
	}
	
	public List<PartidaCotizacion> obtenerPCotizasParaReporteCotizacion(String cotizacion) throws ProquifaNetException {
/*		util = new Funcion();
		List<Date> dFestivos = this.automaticoDAO.findDiasFestivos();
		List<PartidaCotizacion> pcotizas =  this.partidaCotizacionDAO.finConsultaPCotizas(cotizacion);
		for(PartidaCotizacion partida:pcotizas ){
			if(partida.getCodigo()!=null && partida.getFabrica()!=null){partida.setConcepto(this.productoDAO.obtenerDescripcionProducto(partida.getCodigo(), partida.getFabrica()));}
			List<TiempoProceso> tiempo = this.partidaCotizacionDAO.obtenTiempoProcesoPCotiza(partida,false);
			if(tiempo.get(0).getFechaFin()!=null){
				if(!partida.getClasifOrigen().equals("A")){
					partida.setTiempoProceo(tiempo.get(0).getTotalProceso().toString() + ":00");
				}else{
					partida.setTiempoProceo(util.calcularDifHorasSoloHabiles(tiempo.get(0).getFechaInicio(), tiempo.get(0).getFechaFin(), dFestivos));
				}
				if(partida.getClasificacion().equals("A")){
					partida.setConforme(util.enTiempo(partida.getTiempoProceo(), 3)); 
					//log.info("");
				}else{
					partida.setConforme(util.enTiempo(partida.getTiempoProceo(), 72));
					//log.info("");
				}
			}else{
				partida.setTiempoProceo(null);
				partida.setConforme(null); 
			}
		}*/
//		return pcotizas;
		return null;
	}

	public List<TiempoProceso> tiempoProcesoPCotiza(Long idPartida)
			throws ProquifaNetException {
			int iValorFlujo;			
			//iValorFlujo = this.partidaCotizacionDAO.definirFlujoInvestiga(idPartida);              //********* Descomentar Cuando se suba Vista *********
			iValorFlujo = 0;                                                                        //********** Comentar cuando se Suba Vista ************
			if (iValorFlujo == 0) { //Flujo de Investigacion Antiguo
				
				TiempoProceso reg=null;
				String clasifOrigen="",clasifinal="";
				PartidaCotizacion partida = this.partidaCotizacionDAO.obtenerPCotizaXid(idPartida);
				if(!partida.getClasificacion().equals("A")){
					if(partida.getFolio()==99){
						clasifOrigen = this.partidaCotizacionDAO.obtenerPCotizaClasificacionOrigen(partida.getCotizacion(), partida.getCodigo(), partida.getFabrica());
					}else{
						clasifOrigen = partida.getClasificacion();
					}
				}else{
					clasifOrigen="A";
				}
				partida.setClasifOrigen(clasifOrigen);
				List<TiempoProceso> lista = this.partidaCotizacionDAO.obtenTiempoProcesoPCotiza(partida,true);
				List<TiempoProceso> CancePedRecot = this.partidaCotizacionDAO.findConsultaTiempoPedCancRecot(partida);
				for (TiempoProceso r:CancePedRecot){
					lista.add(r);
				}
				ListIterator<TiempoProceso> lt = lista.listIterator();
				while(lt.hasNext()){
					reg = lt.next();
					if((reg.getFechaInicio()==null && reg.getFechaFin()==null) || (reg.getFechaInicio()==null)){
						lt.remove();
					}else{
						if(reg.getProceso().equals("Clasificación")){
							clasifinal=reg.getPcotiza().getClasifFinal();
						}else if(reg.getProceso().equals("Validación pDp") && !clasifinal.equals("D")){
							lt.remove();
						}else if((reg.getProceso().equals("Confirmación") || reg.getProceso().equals("Seguimiento")) && clasifinal.equals("D")){
							lt.remove();
						}else if(reg.getProceso().equals("Tramitación") && reg.getFechaFin()==null){
							lt.remove();
						}else if(partida.getDestino()!= null){ 
							if((reg.getProceso().equals("Investigación") || reg.getProceso().equals("Evaluar respuesta")) && partida.getDestino().equals("Gestionando"))
								lt.remove();
						}
					}
				}
				return lista;
			}
			else{ // Flujo de Investigacion Nuevo
				try{
					List<TiempoProceso> lista = this.partidaCotizacionDAO.obtenerTiempoProcesoNuevo(idPartida);
					List<TiempoProceso> listaFinal = new ArrayList<TiempoProceso>();
					Long iTTInvest=0L, iTTAltaProd=0L, iTTEvaluarRes=0L, iTTInternacional=0L, iTTPdp=0L;				
					Boolean bInves = false, bAltaProd = false, bEvaluarRes = false, bInter = false, bPdP = false;
					
					TiempoProceso tpInvest = null;
					TiempoProceso tpAltaProd = null;
					TiempoProceso tpEvaluarRes = null;
					TiempoProceso tpInternacional = null;
					TiempoProceso tpPdp = null;
					
					
					for(TiempoProceso p:lista){
						if (p.getProceso().equals("GENERALES")) {
							listaFinal.add(p);						
						}
						else{						
							if (p.getProceso().equals("ATENDER INVESTIGACIÓN")) {
								if (!bInves){ //Si es el primer registro de Atender Investigacion
									tpInvest = new TiempoProceso();
									tpInvest.setProceso(p.getProceso());
									tpInvest.setTipoFlujo(p.getTipoFlujo());
									tpInvest.setFechaInicio(p.getFechaInicio());
									tpInvest.setResponsable(p.getResponsable());
									tpInvest.setProveedor(p.getProveedor());
									tpInvest.setPcotiza(p.getPcotiza());
									bInves = true;
								}
								tpInvest.setFechaFin(p.getFechaFin()); //Se asigna la fecha siempre por que al final se va a quedar con la ultima
								//if (p.getFechaFin() != null) {tpInvest.setFechaFin(p.getFechaFin());} 
								iTTInvest = iTTInvest + p.getTotalProceso();
							}
							else if (p.getProceso().equals("ATENDER ALTA DE PRODUCTO")) {
								if (!bAltaProd){ //Si es el primer registro de Atender Investigacion
									tpAltaProd = new TiempoProceso();
									tpAltaProd.setProceso(p.getProceso());
									tpAltaProd.setTipoFlujo(p.getTipoFlujo());
									tpAltaProd.setFechaInicio(p.getFechaInicio());
									tpAltaProd.setResponsable(p.getResponsable());
									tpAltaProd.setProveedor(p.getProveedor());
									tpAltaProd.setPcotiza(p.getPcotiza());
									bAltaProd = true;
								}
								tpAltaProd.setFechaFin(p.getFechaFin());//Se asigna la fecha siempre por que al final se va a quedar con la ultima
								//if (p.getFechaFin() != null) {tpAltaProd.setFechaFin(p.getFechaFin());} 
								iTTAltaProd = iTTAltaProd + p.getTotalProceso();
							}
							else if (p.getProceso().equals("EVALUAR RESULTADOS")) {
								if (!bEvaluarRes){ //Si es el primer registro de Atender Investigacion
									tpEvaluarRes = new TiempoProceso();
									tpEvaluarRes.setProceso(p.getProceso());
									tpEvaluarRes.setTipoFlujo(p.getTipoFlujo());
									tpEvaluarRes.setFechaInicio(p.getFechaInicio());
									tpEvaluarRes.setResponsable(p.getResponsable());
									tpEvaluarRes.setComentarios(p.getComentarios());
									bEvaluarRes = true;
								}
								tpEvaluarRes.setFechaFin(p.getFechaFin()); //Se asigna la fecha siempre por que al final se va a quedar con la ultima
								//if (p.getFechaFin() != null) {tpEvaluarRes.setFechaFin(p.getFechaFin());} 
								iTTEvaluarRes = iTTEvaluarRes + p.getTotalProceso();							
							}
							else if (p.getProceso().equals("CONFIRMAR DATOS INTERNACIONAL")) {
								if (!bInter){ //Si es el primer registro de Atender Investigacion
									tpInternacional = new TiempoProceso();
									tpInternacional.setProceso(p.getProceso());
									tpInternacional.setTipoFlujo(p.getTipoFlujo());
									tpInternacional.setFechaInicio(p.getFechaInicio());
									tpInternacional.setResponsable(p.getResponsable());
									tpInternacional.setProveedor(p.getProveedor());
									tpInternacional.setPcotiza(p.getPcotiza());
									bInter = true;
								}
								tpInternacional.setFechaFin(p.getFechaFin());//Se asigna la fecha siempre por que al final se va a quedar con la ultima
								//if (p.getFechaFin() != null) {tpInternacional.setFechaFin(p.getFechaFin());} 
								iTTInternacional = iTTInternacional + p.getTotalProceso();								
							}
							else if (p.getProceso().equals("VALIDAR pDp")) {
								if (!bPdP){ //Si es el primer registro de Atender Investigacion
									tpPdp = new TiempoProceso();
									tpPdp.setProceso(p.getProceso());
									tpPdp.setTipoFlujo(p.getTipoFlujo());
									tpPdp.setFechaInicio(p.getFechaInicio());
									tpPdp.setResponsable(p.getResponsable());
									tpPdp.setComentarios(p.getComentarios());
									tpPdp.setEstado(p.getEstado());
									bPdP = true;
								}
								tpPdp.setFechaFin(p.getFechaFin());//Se asigna la fecha siempre por que al final se va a quedar con la ultima
								//if (p.getFechaFin() != null) {tpPdp.setFechaFin(p.getFechaFin());} 
								iTTPdp = iTTPdp + p.getTotalProceso();								
							}						
						}
					}
					
					if (tpInvest != null) {
						tpInvest.setTotalProceso(iTTInvest);
						listaFinal.add(tpInvest);
					}
					if (tpAltaProd != null) { 
						tpAltaProd.setTotalProceso(iTTAltaProd);
						listaFinal.add(tpAltaProd); 
					}
					if (tpEvaluarRes != null) { 
						tpEvaluarRes.setTotalProceso(iTTEvaluarRes);
						listaFinal.add(tpEvaluarRes); 					
					}
					if (tpInternacional != null) { 
						tpInternacional.setTotalProceso(iTTInternacional);
						listaFinal.add(tpInternacional); 					
					}
					if (tpPdp != null) { 
						tpPdp.setTotalProceso(iTTPdp);
						listaFinal.add(tpPdp); 
					}
					
					//******* Al final se indico que se necesitaban algunos procesos del anterior *******
					
					TiempoProceso reg=null;
					String clasifOrigen="",clasifinal="";
					PartidaCotizacion partida = this.partidaCotizacionDAO.obtenerPCotizaXid(idPartida);
					if(!partida.getClasificacion().equals("A")){
						if(partida.getFolio()==99){
							clasifOrigen = this.partidaCotizacionDAO.obtenerPCotizaClasificacionOrigen(partida.getCotizacion(), partida.getCodigo(), partida.getFabrica());
						}else{
							clasifOrigen = partida.getClasificacion();
						}
					}else{
						clasifOrigen="A";
					}
					partida.setClasifOrigen(clasifOrigen);
					List<TiempoProceso> lista2 = this.partidaCotizacionDAO.obtenTiempoProcesoPCotiza(partida,true);
					List<TiempoProceso> CancePedRecot = this.partidaCotizacionDAO.findConsultaTiempoPedCancRecot(partida);
					for (TiempoProceso r:CancePedRecot){
						lista2.add(r);
					}
					ListIterator<TiempoProceso> lt = lista2.listIterator();
					while(lt.hasNext()){
						reg = lt.next();
						//Se agrega el Orden de como VISTA lo va a mostrar, el 3 lo tiene Generales (Alta de Producto en Vista)
						if(reg.getProceso().equals("Registro")){ reg.setOrdenProceso(1); }
						else if(reg.getProceso().equals("Clasificación")){ reg.setOrdenProceso(2); }
						else if(reg.getProceso().equals("Envío")){ reg.setOrdenProceso(4); }
						else if(reg.getProceso().equals("Confirmación")){ reg.setOrdenProceso(5); }
						else if(reg.getProceso().equals("Seguimiento")){ reg.setOrdenProceso(6); }
						else if(reg.getProceso().equals("Tramitación")){ reg.setOrdenProceso(7); }
						else if(reg.getProceso().equals("CANCELADA") || reg.getProceso().equals("PEDIDO") || reg.getProceso().equals("RECOTIZADA")){ reg.setOrdenProceso(8); }
						
						if((reg.getFechaInicio()==null && reg.getFechaFin()==null) || (reg.getFechaInicio()==null)){
							lt.remove();
						}else{
							if(reg.getProceso().equals("Clasificación")){
								clasifinal=reg.getPcotiza().getClasifFinal();
							}else if(reg.getProceso().equals("Validación pDp") || reg.getProceso().equals("Investigación") || reg.getProceso().equals("Evaluar respuesta") || reg.getProceso().equals("Confirmar Datos PHS") || reg.getProceso().equals("Ingresó en catálogo")){
								lt.remove();
							}else if((reg.getProceso().equals("Confirmación") || reg.getProceso().equals("Seguimiento")) && clasifinal.equals("D")){
								lt.remove();
							}else if(reg.getProceso().equals("Tramitación") && reg.getFechaFin()==null){
								lt.remove();
							}
						}						
					}					
					//****************
					
					listaFinal.addAll(lista2);
					return listaFinal;
				
				}catch (Exception e) {
					//log.info("Erro: " + e.getMessage());
					return null;
				}
				
			}		
	}
	
	public List<PartidaCotizacion> obtenerPartidasCotizaGraficaETyFT(Date Finicio,
				Date Ffin, String Estatus,String Estado,String Cotizo,String Cliente,
				String MEnvio,String cotizacion,Long idEmpleado) throws ProquifaNetException {
		try{
				util = new Funcion();
				SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyyMMdd");
				String condiciones="";
				
				if(cotizacion!= null && !cotizacion.equals("")){
					condiciones = condiciones + " AND COTI.Clave LIKE '%"+ cotizacion +"%'";
				}else{
					//CONSULTA PARA LA BUSQUEDA POR FECHAS
					if(Finicio!=null && Ffin!=null){
						condiciones = " AND COTI.Fecha BETWEEN '"+ formatoDeFecha.format(Finicio) +" 00:00:00' AND '"+ formatoDeFecha.format(Ffin) +" 23:59:59'";
						if( Cliente!= null && !Cliente.equals("--TODOS--") && !Cliente.equals("")){
							condiciones+= " AND COTI.Cliente='"+Cliente+"'";
						}
						if(MEnvio!= null && !MEnvio.equals("--TODOS--") && !MEnvio.equals("")){
							if(MEnvio.equals("Correo")){
								condiciones+=" AND COTI.MSalida='C' ";
							}else if(MEnvio.equals("Fax")){
								condiciones += " AND COTI.MSalida='F' ";
							}else if(MEnvio.equals("Pendiente")){
								condiciones += " AND (COTI.MSalida IS NULL OR COTI.MSalida='') ";
							}
						}
						
						if( Estado!= null && !Estado.equals("--TODOS--") && !Estado.equals("")){
							if(Estado.equals("Cerrado")){
								condiciones+= " AND 'CERRADO'=(CASE WHEN cero.Clasif='A' THEN CASE WHEN (nueve.Estado='Cancelada' OR nueve.Estado='Pedido' OR nueve.Estado='Pedido cotizado' OR nueve.Estado= 'Recotizada') OR (nueve.Destino='Gestionando' OR nueve.Destino='No Cotizar') THEN 'CERRADO' ELSE 'ABIERTO' END ELSE " + 
											  " CASE WHEN partidaBC.Folio IS NULL THEN CASE WHEN pcotPHS.Estado='Gestionando' OR pcotPHS.Estado='No Cotizar' THEN 'CERRADO' ELSE 'ABIERTO' END ELSE  " +
											  " CASE WHEN (partidaBC.Estado IS NULL OR partidaBC.Estado='Cancelada' OR partidaBC.Estado='Pedido' OR partidaBC.Estado='Pedido cotizado') OR (partidaBC.Destino='Gestionando' OR partidaBC.Destino='No Cotizar')  " +
											  " THEN 'CERRADO' ELSE CASE WHEN partidaBC.Estado IS NOT NULL AND partidaBC.Estado='Recotizada' THEN 'CERRADO' ELSE 'ABIERTO' END END END END)";
							}else{
								condiciones+= " AND 'ABIERTO'=(CASE WHEN cero.Clasif='A' THEN CASE WHEN (nueve.Estado='Cancelada' OR nueve.Estado='Pedido' OR nueve.Estado='Pedido cotizado' OR nueve.Estado= 'Recotizada') OR (nueve.Destino='Gestionando' OR nueve.Destino='No Cotizar') THEN 'CERRADO' ELSE 'ABIERTO' END ELSE " + 
								  " CASE WHEN partidaBC.Folio IS NULL THEN CASE WHEN pcotPHS.Estado='Gestionando' OR pcotPHS.Estado='No Cotizar' THEN 'CERRADO' ELSE 'ABIERTO' END ELSE  " +
								  " CASE WHEN (partidaBC.Estado IS NULL OR partidaBC.Estado='Cancelada' OR partidaBC.Estado='Pedido' OR partidaBC.Estado='Pedido cotizado') OR (partidaBC.Destino='Gestionando' OR partidaBC.Destino='No Cotizar')  " +
								  " THEN 'CERRADO' ELSE CASE WHEN partidaBC.Estado IS NOT NULL AND partidaBC.Estado='Recotizada' THEN 'CERRADO' ELSE 'ABIERTO' END END END END)";
							}
						}
						
						if( Estatus!= null && !Estatus.equals("--TODOS--") && !Estatus.equals("")){
							
							condiciones+=" AND '"+Estatus+"'=(CASE WHEN cero.Clasif='A' THEN nueve.Estado ELSE (CASE WHEN partidaBC.Folio=99 AND partidaBC.Estado<>'Por Gestionar' THEN partidaBC.Estado ELSE pcotPHS.Estado END)END)";
						}
						
					}				
				}
				
				Empleado e = this.empleadoDAO.obtenerEmpleadoPorId(idEmpleado);
				if(!e.getNivelGeneral().equals("Directivo") && !e.getNivelGeneral().equals("Gerente")){
					if(e.getNombreFuncion().equals("Ejecutivo de Servicio a Clientes Master")){
						if(Cotizo!= null && !Cotizo.equals("--TODOS--") && !Cotizo.equals("") ){
							condiciones+= " AND COTI.Cotizo='"+ Cotizo +"'";
						}else{
							Integer count = 0;
							condiciones += " AND (";
		//					Como el string recibido es numerico, se trata de un esac master
		//					asi que se buscaran todos los empleados relacionados a este grupo
							List<String> usuarios = empleadoDAO.finEquipoESAC(idEmpleado);
							for(String user:usuarios){
								count ++;
								condiciones += "COTI.Cotizo='" + user + "'";
								if(count<usuarios.size()){
									condiciones +=" OR ";
								}							
							}
							condiciones +=") ";
						}
					}else{
						if(e.getNombreFuncion().equals("Ejecutivo de Ventas")){
							condiciones += " AND CLI.FK01_EV = " + e.getIdEmpleado();
						}else if(e.getNombreFuncion().equals("Ejecutivo de Servicio a Clientes")){
							condiciones += " AND CLI.VENDEDOR='" + e.getUsuario() + "'";
						}
					}
				}else{
					if(Cotizo!= null && !Cotizo.equals("--TODOS--") && !Cotizo.equals("")){
						condiciones+= " AND COTI.Cotizo='"+ Cotizo +"'";
					}
				}
				
				List<PartidaCotizacion> pcotizas =  this.partidaCotizacionDAO.findConsultaPartidaResumenGrafica(condiciones);
				
				
		//		for(PartidaCotizacion partida:pcotizas ){
		//			List<TiempoProceso> tiempo = this.partidaCotizacionDAO.obtenTiempoProcesoPCotiza(partida,false);
		//			if(!partida.getFechaGeneracion().equals(null)){
		//				if(!partida.getClasifOrigen().equals("A")){
		//					partida.setTiempoProceo(tiempo.get(0).getTotalProceso().toString() + ":00");
		//				}else{
		//					partida.setTiempoProceo(util.calcularDifHorasSoloHabiles(tiempo.get(0).getFechaInicio(), tiempo.get(0).getFechaFin()));
		//				}
		//				if(partida.getClasificacion().equals("A")){
		//					partida.setConforme(util.enTiempo(partida.getTiempoProceo(), 3)); 
		//				}else{
		//					partida.setConforme(util.enTiempo(partida.getTiempoProceo(), 72)); 
		//				}
		//			}else{
		//				partida.setTiempoProceo(null);
		//				partida.setConforme(null); 
		//			}
		//		}
				
				return pcotizas;
		}catch (Exception e) {
		
			return null;
		}
	}


}