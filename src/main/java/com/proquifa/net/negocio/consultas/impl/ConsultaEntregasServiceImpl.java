/**
 * 
 */
package com.proquifa.net.negocio.consultas.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proquifa.net.modelo.cobrosypagos.facturista.Factura;
import com.proquifa.net.modelo.comun.NivelIngreso;
import com.proquifa.net.modelo.comun.TiempoProceso;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.consultas.comun.ResumenConsulta;
import com.proquifa.net.modelo.despachos.HistorialPNE;
import com.proquifa.net.negocio.consultas.ConsultaEntregasService;
import com.proquifa.net.persistencia.comun.ClienteDAO;
import com.proquifa.net.persistencia.comun.NivelIngresoDAO;
import com.proquifa.net.persistencia.consultas.ConsultaEntregasDAO;

/**
 * @author vromero
 *
 */
@Service("consultaEntregasService")
public class ConsultaEntregasServiceImpl implements ConsultaEntregasService {
	
	@Autowired
	ConsultaEntregasDAO entregasDAO;
	@Autowired
	ClienteDAO clienteDAO;
	@Autowired
	NivelIngresoDAO nivelIngresoDAO;
	
	final Logger log = LoggerFactory.getLogger(ConsultaEntregasServiceImpl.class);
	
	
	SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyyMMdd");
	Funcion util = new Funcion();
	private String definirCondiciones(Long idCliente, String estado, String mensajero, String ruta, String conforme, 
			Date finicio, Date ffin, String factura, String cpedido, Boolean conFechas){
		String restriccion="";

		if(factura!=null && !factura.equals("")){
			restriccion += " WHERE rdp.Factura=" + factura;
		}else if(cpedido!=null && !cpedido.equals("")){
			restriccion += " WHERE (REM.CPedido LIKE '%" + cpedido + "%' OR FAC.CPedido LIKE '%" + cpedido + "%')";
		}else{
			if(conFechas){
				restriccion += " WHERE rdp.Fecha BETWEEN '"+ formatoDeFecha.format(finicio) +" 00:00' AND '"+ formatoDeFecha.format(ffin) +" 23:59'";
			}
			if(!estado.equals("--TODOS--")){
				restriccion += " AND (CASE WHEN rdp.Entrega='Realizada' AND rdp.FFin IS NOT NULL THEN 'Cerrado' ELSE CASE WHEN facturaCancelada.FFin IS NOT NULL THEN 'Cerrado' ELSE 'Abierto' END END)='" + estado+"'";
			}
			if(!ruta.equals("--TODOS--")){
				restriccion += " AND rdp.Ruta='" + ruta + "'";
			}
			if(idCliente > 0){
				restriccion += " AND rdp.idCliente=" + idCliente;
			}
			if(!conforme.equals("--TODOS--")){
				if(conforme.equals("SI")){
					restriccion += " AND rdp.Conforme=1";
				}else if(conforme.equals("NO")){
					restriccion += " AND rdp.Conforme=0";
				}else{
					restriccion += " AND (CASE WHEN rdp.Conforme IS NULL THEN CASE WHEN (rdp.EstadoRuta='Cerrada' AND rdp.Entrega='Realizada') " +
							"OR (rdp.ZonaMensajeria='Almacén') THEN 'NA' ELSE 'Pendiente' END END)='" + conforme +"'";
				}
			}
			if(!mensajero.equals("--TODOS--")){
				restriccion += " AND coalesce(usuario.Responsable,RutaEjecutar.Responsable ,'Pendiente' )='" + mensajero+"'";				
			}			
		}
		
		return restriccion;
	}
	
	
	private String definirCondicionesXRuta(Long idCliente, String estado, String mensajero, String ruta, String conforme, 
			Date finicio, Date ffin, String factura, String cpedido, Boolean conFechas, Boolean infoPNE){
		String restriccion="";
		util = new Funcion();

		if(factura!=null && !factura.equals("")){
			restriccion += " WHERE rdp.Factura=" + factura;
		}else if(cpedido!=null && !cpedido.equals("")){
			restriccion += " WHERE (REM.CPedido LIKE '%" + cpedido + "%' OR FAC.CPedido LIKE '%" + cpedido + "%')";
		}else{
			if(conFechas){
				
//				restriccion += " WHERE ( CASE WHEN RutaTrabajarUrgente.FInicio IS NOT NULL THEN RutaTrabajarUrgente.FInicio  ELSE RutaTrabajar.FInicio END)  BETWEEN '"+ formatoDeFecha.format(finicio) +" 00:00' AND '"+ formatoDeFecha.format(ffin) +" 23:59'";
				restriccion += 	" WHERE ((RutaTrabajar.FInicio BETWEEN '"+ formatoDeFecha.format(util.calcularNuevaFecha(finicio, -1)) +" 00:00' AND '"+ formatoDeFecha.format(util.calcularNuevaFecha(ffin, -1)) +" 23:59') or " +
								" (RutaEjecutar.FInicio BETWEEN '"+ formatoDeFecha.format(finicio) +" 00:00' AND '"+ formatoDeFecha.format(ffin) +" 23:59') )";
			}
			if(!estado.equals("--TODOS--")){
				restriccion += " AND (CASE WHEN rdp.Entrega='Realizada' AND rdp.FFin IS NOT NULL THEN 'Cerrado' ELSE CASE WHEN facturaCancelada.FFin IS NOT NULL THEN 'Cerrado' ELSE 'Abierto' END END)='" + estado+"'";
			}
			if(!ruta.equals("--TODOS--")){
				restriccion += " AND rdp.Ruta='" + ruta + "'";
			}
			if(idCliente > 0){
				restriccion += " AND rdp.idCliente=" + idCliente;
			}
			if(!conforme.equals("--TODOS--")){
				if(conforme.equals("SI")){
					restriccion += " AND rdp.Conforme=1";
				}else if(conforme.equals("NO")){
					restriccion += " AND rdp.Conforme=0";
				}else{
					restriccion += " AND (CASE WHEN rdp.Conforme IS NULL THEN CASE WHEN (rdp.EstadoRuta='Cerrada' AND rdp.Entrega='Realizada') " +
							"OR (rdp.ZonaMensajeria='Almacén') THEN 'NA' ELSE 'Pendiente' END END)='" + conforme +"'";
				}
			}
			if(!mensajero.equals("--TODOS--")){
				//restriccion += " AND (CASE WHEN usuario.Responsable IS NOT NULL THEN usuario.Responsable ELSE 'Pendiente' END)='" + mensajero+"'";
				if (infoPNE){
					// las graficas de motivos e intentos de PNE requieren este cambio 
					restriccion += " AND coalesce(usuario.Responsable,RutaEjecutar.Responsable ,'Pendiente' )='" + mensajero+"'";
				}else {
					restriccion += " AND  usuario.Responsable ='" + mensajero+"'";
				}
			}			
		}
		
		return restriccion;
	}

	public List<Factura> obtenerEntregas(Long idCliente, String estado,
			String mensajero, String ruta, String conforme, Date finicio,
			Date ffin, String factura, String cpedido)
			throws ProquifaNetException {
		String condiciones = new String();
		log.info("pasa 1");
		if(ruta != null){
			ruta = util.convertirPalabrasClaves(ruta);
		}
		condiciones = definirCondicionesXRuta(idCliente,estado,mensajero,ruta,conforme,finicio,ffin,factura,cpedido,true,false);
		List<NivelIngreso> niveles = nivelIngresoDAO.findLimitesNivelIngreso();
		return this.entregasDAO.findEntregas(condiciones, niveles);
	}
	
	
	public List<TiempoProceso> obtenerTiempoDeProceso(String idDP) throws ProquifaNetException {
		log.info("pasa 2");
		TiempoProceso reg = null;
		Boolean existeRCierre = false;
		List<TiempoProceso> lista = this.entregasDAO.findTiempoProceso(idDP);
		ListIterator<TiempoProceso> lt = lista.listIterator();
		while(lt.hasNext()){
			reg = lt.next();
			if(reg.getFechaInicio()==null && reg.getFechaFin()==null){
				lt.remove();
			}
			if(reg.getProceso().equals("Cierre") && reg.getFechaFin() != null){existeRCierre = true;}
			if(reg.getProceso().equals("Entrega")){
				if(reg.getFechaFin()==null || !existeRCierre){
					lt.remove();
				}
			}
		}
		return lista;
	}
	
	public List<HistorialPNE> obtenerHistorialProductoNoEntregado(String idDP)
			throws ProquifaNetException {		
		log.info("pasa 3");
		return this.entregasDAO.findHistorial(idDP);
	}

	public List<ResumenConsulta> obtenerComparativasDPeriodos(Long idCliente,
			String estado, String mensajero, String ruta, String conforme,
			String factura, String cpedido, Date finicio, Date ffin,
			Boolean individual) throws ProquifaNetException {
		log.info("pasa 4");
		String condiciones = "";
		List<ResumenConsulta> totales = null;
		util = new Funcion();
		ruta = util.convertirPalabrasClaves(ruta);
		condiciones = definirCondiciones(idCliente,estado,mensajero,ruta,conforme,null,null,factura,cpedido,false);
		if(finicio != null && ffin != null){
			String fechas = "";
			if(individual){
//				fechas = "WHERE rdp.Fecha >=" + util.convertirDosFechasAString(finicio, ffin, "rdp.Fecha");
				fechas = "WHERE ((usuario.FInicio > ="+ util.convertirDosFechasAString(util.calcularNuevaFecha(finicio, -1), util.calcularNuevaFecha(ffin, -1), "usuario.FInicio")+ ") or (RutaEjecutar.FInicio  > = " + util.convertirDosFechasAString(finicio, ffin, "RutaEjecutar.FInicio") + "))";
				totales = entregasDAO.findComparativasDPeriodos(fechas + " " + condiciones);
				for (ResumenConsulta r : totales) {
					r.setEtiqueta("Actual");
					r.setFechaInicio(finicio);
					r.setFechaFinal(ffin);
				}				
			}else{	
				String fechas2 = "";
				String fechas3 = "";
				Date Fini2, Ffin2,Fini3,Ffin3;
				Date [] rango = null;
				
//				fechas = "WHERE rdp.Fecha >=" + util.convertirDosFechasAString(finicio, ffin, "rdp.Fecha");
				fechas = "WHERE ((usuario.FInicio > ="+ util.convertirDosFechasAString(util.calcularNuevaFecha(finicio, -1), util.calcularNuevaFecha(ffin, -1), "usuario.FInicio")+ ") or (RutaEjecutar.FInicio  > = " + util.convertirDosFechasAString(finicio, ffin, "RutaEjecutar.FInicio") + "))";
				rango = util.calcularFechasPeriodoAnterior(finicio,ffin);
//				fechas2 = "WHERE rdp.Fecha >="+ util.convertirDosFechasAString(rango[0], rango[1],"rdp.Fecha");
				fechas2 = "WHERE RutaEjecutar.FInicio >="+ util.convertirDosFechasAString(rango[0], rango[1],"RutaEjecutar.FInicio");
				Fini2 = rango[0];
				Ffin2 = rango[1];
				rango = util.calcularFechasPeriodoAnterior(rango[0],rango[1]);
//				fechas3 = "WHERE rdp.Fecha >="+ util.convertirDosFechasAString(rango[0], rango[1],"rdp.Fecha");
				fechas3 = "WHERE RutaEjecutar.FInicio >="+ util.convertirDosFechasAString(rango[0], rango[1],"RutaEjecutar.FInicio");
				Fini3 = rango[0];
				Ffin3 = rango[1];
				
				totales = entregasDAO.findComparativasDPeriodos(fechas + " " + condiciones);
				for (ResumenConsulta r : totales) {
					r.setEtiqueta("Actual");
					r.setFechaInicio(finicio);
					r.setFechaFinal(ffin);
				}
				
				List<ResumenConsulta> temp = entregasDAO.findComparativasDPeriodos(fechas2 + " " + condiciones);
				for (ResumenConsulta r : temp) {
					r.setEtiqueta("Pasado");
					r.setFechaInicio(Fini2);
					r.setFechaFinal(Ffin2);
					totales.add(r);
				}
				
				temp.clear();
				temp = entregasDAO.findComparativasDPeriodos(fechas3 + " " + condiciones);
				for (ResumenConsulta r : temp) {
					r.setEtiqueta("Postpasado");
				 	r.setFechaInicio(Fini3);
				 	r.setFechaFinal(Ffin3);
					totales.add(r);
				}
			}
		}
		return totales;
	}

	public List<Factura> obtenerGraficosEntregas(Long idCliente, String estado,
			String mensajero, String ruta, String conforme, Date finicio,
			Date ffin, String factura, String cpedido)
			throws ProquifaNetException {
		String condiciones="";
		log.info("pasa 5");
		/*
		ruta = util.convertirPalabrasClaves(ruta);
		condiciones = definirCondiciones(idCliente,estado,mensajero,ruta,conforme,finicio,ffin,factura,cpedido,true);
		return this.entregasDAO.findGraficosEntregas(condiciones);*/
		
		ruta = util.convertirPalabrasClaves(ruta);
		condiciones = definirCondicionesXRuta(idCliente,estado,mensajero,ruta,conforme,finicio,ffin,factura,cpedido,true,true);
		return this.entregasDAO.findGraficosEntregas(condiciones);
	}
	
	/**
	 * Graficos de Consulta de Entregas Con Avisos de Cambios
	 */
	public List<Factura> obtenerEntregasCAviso(Long idCliente, String estado, String mensajero, String ruta, String conforme, Date finicio, 
			Date ffin, String factura, String cpedido) throws ProquifaNetException{
		log.info("pasa 6");
		String condiciones = new String();
		ruta = util.convertirPalabrasClaves(ruta);
		condiciones = " INNER JOIN (SELECT PPED.CPedido, PPED.PART,PCOM.PPedido, PCOM.Compra, PCOM.Partida, pcHIST.idCompra, pcHIST.idPCompra " +
				"FROM PPedidos PPED " +
				" INNER JOIN (SELECT CPedido, PPedido, Compra, Partida FROM PCompras WHERE Estado <> 'Cancelada') AS PCOM ON PCOM.CPedido = PPED.CPedido AND PCOM.PPedido = PPED.PART " +
				" INNER JOIN (SELECT DISTINCT idCompra, idPCompra FROM PCompraHistorial WHERE Tipo LIKE '%Aviso de cambio%') AS pcHIST ON pcHIST.idCompra = PCOM.Compra AND pcHIST.idPCompra = PCOM.Partida) " + 
				" AS CAvisoCambio on CAvisoCambio.CPedido = FAC.CPedido ";
		condiciones += definirCondiciones(idCliente,estado,mensajero,ruta,conforme,finicio,ffin,factura,cpedido,true);
		 List<NivelIngreso> niveles = nivelIngresoDAO.findLimitesNivelIngreso();	
		if (condiciones.contains(",RutaEjecutar.Responsable")) {
			condiciones = condiciones.replace(",RutaEjecutar.Responsable", "");
		}
		return this.entregasDAO.findEntregas(condiciones, niveles);
	}
	
	/**
	 * Graficos de Consulta de Entregas SIN Avisos de Cambios
	 */
	public List<Factura> obtenerEntregasSinAviso(Long idCliente, String estado, String mensajero, String ruta, String conforme, Date finicio, 
			Date ffin, String factura, String cpedido) throws ProquifaNetException{
		log.info("pasa 7");
		String condiciones = new String();
		ruta = util.convertirPalabrasClaves(ruta);
		condiciones += definirCondiciones(idCliente,estado,mensajero,ruta,conforme,finicio,ffin,factura,cpedido,true);
		condiciones += " AND FAC.CPedido NOT IN (SELECT PPED.CPedido " +
				" FROM PPedidos PPED " +
				" INNER JOIN (SELECT CPedido, PPedido, Compra, Partida FROM PCompras WHERE Estado <> 'Cancelada') AS PCOM ON PCOM.CPedido = PPED.CPedido AND PCOM.PPedido = PPED.PART " +
				" INNER JOIN (SELECT DISTINCT idCompra, idPCompra FROM PCompraHistorial WHERE Tipo LIKE '%Aviso de cambio%') AS pcHIST ON pcHIST.idCompra = PCOM.Compra AND pcHIST.idPCompra = PCOM.Partida) ";
		 List<NivelIngreso> niveles = nivelIngresoDAO.findLimitesNivelIngreso();	
		 if (condiciones.contains(",RutaEjecutar.Responsable")) {
			 condiciones = condiciones.replace(",RutaEjecutar.Responsable", "");
		}
		return this.entregasDAO.findEntregas(condiciones, niveles);
	}
}