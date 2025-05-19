package com.proquifa.net.negocio.consultas.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proquifa.net.modelo.cobrosypagos.Cobros;
import com.proquifa.net.modelo.cobrosypagos.facturista.HistorialFactura;
import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.comun.NivelIngreso;
import com.proquifa.net.modelo.comun.TiempoProceso;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.consultas.comun.ParametrosBusquedaCobros;
import com.proquifa.net.modelo.consultas.comun.ResumenConsulta;
import com.proquifa.net.negocio.consultas.ConsultaCobrosService;
import com.proquifa.net.persistencia.comun.EmpleadoDAO;
import com.proquifa.net.persistencia.comun.NivelIngresoDAO;
import com.proquifa.net.persistencia.consultas.ConsultaCobrosDAO;

@Service("consultaCobrosService")
public class ConsultaCobrosServiceImpl implements ConsultaCobrosService {

	final Logger log = LoggerFactory.getLogger(ConsultaCobrosServiceImpl.class);
	
	@Autowired
	ConsultaCobrosDAO cobrosDAO;
	@Autowired
	NivelIngresoDAO nivelIngresoDAO;
	@Autowired
	EmpleadoDAO empleadoDAO;

	private Funcion funcion;

	@Override
	public List<Cobros> obtenerCobros(ParametrosBusquedaCobros parametros) throws ProquifaNetException {
		String restricciones = "";
		String fechas = "";

		List<NivelIngreso> niveles = nivelIngresoDAO.findLimitesNivelIngreso();

		try {
			funcion = new Funcion();
			if ((parametros.getFechaInicio() != null) && (parametros.getFechaFin() != null)) {
				if (parametros.getBusquedaCR()) {
					fechas = " AND COBROREAL.FPago >= " + funcion.convertirDosFechasAString(parametros.getFechaInicio(),
							parametros.getFechaFin(), "COBROREAL.FPago");
				} else {
					fechas = " AND F.Fecha >= " + funcion.convertirDosFechasAString(parametros.getFechaInicio(),
							parametros.getFechaFin(), "F.Fecha");
				}
			}

			if (parametros.getIdCliente() > 0) {
				restricciones += " AND F.Cliente =" + parametros.getIdCliente();
			}
			if (!parametros.getMedioPago().equals("")) {
				restricciones += " AND COALESCE(PAGOP.Medio, INCONS.MedioPago, CASE WHEN F.CPago = 'PREPAGO 100%' THEN F.TPago END, 'Pendiente') = '"
						+ parametros.getMedioPago() + "'";
			}
			if (!parametros.getFpor().equals("")) {
				restricciones += " AND F.FPor = '" + parametros.getFpor() + "'";
			}
			if (!parametros.getEstado().equals("")) {
				if (parametros.getEstado().equals("Abierto"))
					restricciones += " AND (F.Estado = 'Por Cobrar' OR F.Estado = 'A refacturación' )";
				if (parametros.getEstado().equals("Cerrado"))
					restricciones += " AND (F.Estado = 'Cobrada' OR F.Estado = 'Cancelada' )";
				else
					restricciones += "";

			}
			if (!parametros.getCpago().equals("")) {
				restricciones += " AND F.CPago = '" + parametros.getCpago() + "'";
			}
			if (!parametros.getFactura().equals("")) {
				restricciones += " AND F.Factura = '" + parametros.getFactura() + "'";
			}
			if (parametros.getUuid() != null && !parametros.getUuid().equals("")) {
				restricciones += " AND F.UUID LIKE '%" + parametros.getUuid() + "%'";
			}
			switch (parametros.getDrc()) {
			case 0:
				restricciones += " AND DATEDIFF(DAY, GETDATE(),COALESCE(PAGOP.FEPago, INCONS.FechaPago, CASE WHEN F.CPago = 'PREPAGO 100%' THEN F.Fecha ELSE NULL END)) > 4 AND F.Estado <> 'Cobrada'";
				break;
			case 1:
				restricciones += " AND DATEDIFF(DAY, GETDATE(),COALESCE(PAGOP.FEPago, INCONS.FechaPago, CASE WHEN F.CPago = 'PREPAGO 100%' THEN F.Fecha ELSE NULL END)) IN (2,3,4) AND F.Estado <> 'Cobrada'";
				break;
			case 2:
				restricciones += " AND DATEDIFF(DAY, GETDATE(),COALESCE(PAGOP.FEPago, INCONS.FechaPago, CASE WHEN F.CPago = 'PREPAGO 100%' THEN F.Fecha ELSE NULL END)) IN (-1,0,1) AND F.Estado <> 'Cobrada'";
				break;
			case 3:
				restricciones += " AND DATEDIFF(DAY, GETDATE(),COALESCE(PAGOP.FEPago, INCONS.FechaPago, CASE WHEN F.CPago = 'PREPAGO 100%' THEN F.Fecha ELSE NULL END)) IN (-4,-3,-2) AND F.Estado <> 'Cobrada'";
				break;
			case 4:
				restricciones += " AND DATEDIFF(DAY, GETDATE(),COALESCE(PAGOP.FEPago, INCONS.FechaPago, CASE WHEN F.CPago = 'PREPAGO 100%' THEN F.Fecha ELSE NULL END)) < -4 AND F.Estado <> 'Cobrada' ";
				break;
			case 5:
				restricciones += " ";
				break;
			default:
				restricciones += " ";
				break;
			}

			Empleado e = empleadoDAO.obtenerEmpleadoPorId(parametros.getIdUsuarioLogueado());
			if (e.getIdFuncion() == 40) {
				restricciones += " AND CLI.Cobrador = " + parametros.getIdUsuarioLogueado();
			} else if (e.getIdFuncion() == 37) {
				List<String> equipo = empleadoDAO.finEquipoESAC(parametros.getIdUsuarioLogueado());
				restricciones += "AND CLI.Vendedor in (";
				for (String eq : equipo) {
					restricciones += "'" + eq + "',";
				}
				restricciones += "'" + e.getUsuario() + "')";
			} else if (e.getIdFuncion() == 5) {
				restricciones += " AND CLI.Vendedor = '" + e.getUsuario() + "'";
			}

			if (parametros.getCobrador() > 0) {
				e = empleadoDAO.obtenerEmpleadoPorId(parametros.getCobrador());
				restricciones += " AND CLI.Cobrador = '" + parametros.getCobrador() + "'";
			}

			if (parametros.getCuenta() != null && !parametros.getCuenta().equals("")) {
				restricciones += " AND CB.NoCuenta like '%" + parametros.getCuenta() + "%'";
			}
			// log.info(parametros.getBanco()+"=========================================");
			if (parametros.getBanco() != null && !parametros.getBanco().equals("")) {
				restricciones += " AND CB.Banco like '%" + parametros.getBanco() + "%'";

			}

			return this.cobrosDAO.findCobros(fechas, restricciones, niveles);
		} catch (Exception e) {
			funcion = new Funcion();
			log.info(e.getMessage());
			funcion.enviarCorreoAvisoExepcion(e);
			return null;
		}
	}

	public List<HistorialFactura> obtenerHistorialFactura(String factura, String fpor) throws ProquifaNetException {
		try {
			String restricciones = "WHERE Factura = '" + factura + "' AND FPor='" + fpor + "' ";
			return this.cobrosDAO.findHistorial(restricciones);
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			return null;
		}

	}

	public List<TiempoProceso> obtenerLineaTiempoResumen(String factura, String cpedido) throws ProquifaNetException {
		try {
			List<TiempoProceso> tiempo = this.cobrosDAO.findLineaTiempoResumen(factura, cpedido);
			log.info("/**************----------- lenght 1 "+ tiempo.size());
			//TiempoProceso[] arrayTiempo= tiempo.toArray(new TiempoProceso[tiempo.size()]);
			List<TiempoProceso> arrayTiempo = new ArrayList<TiempoProceso>();
			arrayTiempo.addAll(tiempo);
			List<TiempoProceso> _arrTemp = new ArrayList<TiempoProceso>();
			boolean cobradaInterFacturacion = false;
			int contador = 0;int indiceCobrada = 0;
			for(TiempoProceso currenItem : tiempo) {
				if(currenItem.getEtapa().compareTo("COBRO") == 0  && currenItem.getEtapaPadre().compareTo("1") == 0){ // 1 es sin credito
					_arrTemp.add(currenItem);
				}else if(currenItem.getEtapa().compareTo("COOBRADA") == 0  && currenItem.getEtapaPadre().compareTo("5") ==0 ){ // 5 es sin credito
					_arrTemp.add(currenItem);
					currenItem.setFechaInicio((arrayTiempo.get(arrayTiempo.size() - 1)).getFechaInicio()) ;
					currenItem.setFechaFin( (arrayTiempo.get(arrayTiempo.size() - 1) ).getFechaFin());
					currenItem.setTotalProceso((arrayTiempo.get(arrayTiempo.size() - 1) ).getTotalProceso());
					indiceCobrada = contador;
				}else if(currenItem.getEtapa().compareTo("COOBRADA") == 0 && currenItem.getEtapaPadre().compareTo("8") == 0){ // 5 es sin credito
					currenItem.setFechaInicio((arrayTiempo.get(arrayTiempo.size() - 1) ).getFechaInicio());
					currenItem.setFechaFin((arrayTiempo.get(arrayTiempo.size() - 1) ).getFechaFin());
					currenItem.setTotalProceso((arrayTiempo.get(arrayTiempo.size() - 1) ).getTotalProceso());
					indiceCobrada = contador;
				}else 	if(currenItem.getEtapa().compareTo("COOBRADA_SC") == 0){
					log.info("Remove");
					arrayTiempo.remove(currenItem);
				}else if(currenItem.getEtapa().compareTo("ENTREGA") == 0 ){
					if(currenItem.getFechaFin() == null){
						if(currenItem.getConforme().compareTo("NO DISPONIBLE") == 0)
							currenItem.setConforme( "Pendiente");
					}else{
						if(currenItem.getConforme().compareTo( "NO DISPONIBLE") == 0)
							currenItem.setConforme( "ND");
					}
				}else if(currenItem.getEtapa().compareTo("COBRADA INTER") == 0 ){ 
					cobradaInterFacturacion = true;
					
					currenItem.setFechaCobro(currenItem.getFechaFin());
					currenItem.setFechaDispobible(currenItem.getFechaInicio());
					currenItem.setTotalProceso((long) Funcion.regresaDiferenciaEntreFechasEnDias(currenItem.getFechaInicio(),currenItem.getFechaFin()));
					
					arrayTiempo.remove(currenItem);
				}
				contador++;
			}
			/*if(cobradaInterFacturacion) {
				log.info("Remove2");
				arrayTiempo.remove(indiceCobrada);
			}*/
			/*COOBRADA 8*/
			 /* arrayTiempo.get(k).getEtapaPadre().compareTo("1")!=0*/ 
			for(int k= 0; k<arrayTiempo.size() ; k++){
				log.info("Etapa------ 0"+arrayTiempo.get(k).getEtapa()+"Etapa padre----- "+arrayTiempo.get(k).getEtapaPadre());
				if(arrayTiempo.get(k).getEtapaPadre()==null || arrayTiempo.get(k).getEtapa().compareTo("COOBRADA")> 0 || arrayTiempo.get(k).getEtapaPadre().compareTo("0")==0 ){
					_arrTemp.add(arrayTiempo.get(k));
				}
			}
			log.info("/**************----------- lenght 2 "+ _arrTemp.size());
			return _arrTemp;

		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			return null;
		}
	}

	public List<TiempoProceso> obtenerInspectorEntrega(String factura, String cpedido) throws ProquifaNetException {
		try {
			return this.cobrosDAO.findInspectorEntrega(factura, cpedido);
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			return null;
		}
	}

	public List<TiempoProceso> obtenerInspectorRevision(String factura, String cpedido) throws ProquifaNetException {
		try {
			return this.cobrosDAO.findInspectorRevision(factura, cpedido);
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			return null;
		}
	}

	public List<TiempoProceso> obtenerInspectorCobro(String factura, String cpedido, String SC)
			throws ProquifaNetException {
		try {
			List<TiempoProceso> cobro = null;
			List<TiempoProceso> cobroTemp = null;
			List<TiempoProceso> co = null;
			if (SC.equals("0")) {
				cobro = this.cobrosDAO.findInspectorCobroGral(factura, cpedido);
				cobroTemp = this.cobrosDAO.findInspectorCobroProgramacion(factura, cpedido);
				for (TiempoProceso r : cobroTemp) {
					cobro.add(r);
					log.info("Etapa---- "+r.getEtapa());
				}
				cobroTemp = this.cobrosDAO.findInspectorCobroSolicitud(factura, cpedido);
				for (TiempoProceso r : cobroTemp) {
					cobro.add(r);
					log.info("Etapa---- "+r.getEtapa());
				}
				cobroTemp = this.cobrosDAO.findInspectorCobroEjecucion(factura, cpedido);
				for (TiempoProceso r : cobroTemp) {
					cobro.add(r);
					log.info("Etapa---- "+r.getEtapa());
				}
				cobroTemp = this.cobrosDAO.findInspectorCobroMonitoreo(factura, cpedido);
				for (TiempoProceso r : cobroTemp) {
					cobro.add(r);
					log.info("Etapa---- "+r.getEtapa());
				}
				cobroTemp = this.cobrosDAO.findInspectorCobroHistorial(factura, cpedido);
				for (TiempoProceso r : cobroTemp) {
					cobro.add(r);
					log.info("Etapa---- "+r.getEtapa());
				
				}
			} else {
				log.info("Segunda opción");
				cobro = this.cobrosDAO.findInspectorCobroSC(factura, cpedido);
				
			}
			log.info("Tamaño--------"+cobro.size());
		
			return cobro;

		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			return null;
		}
	}

	public List<ResumenConsulta> obtenerComparativasDPeriodos(ParametrosBusquedaCobros parametros)
			throws ProquifaNetException {
		funcion = new Funcion();
		try {
			List<ResumenConsulta> totales = null;
			String restricciones = "";
			if (parametros.getIdCliente() > 0) {
				restricciones += " AND F.Cliente=" + parametros.getIdCliente();
			}
			if (!parametros.getFpor().equals("")) {
				restricciones += " AND F.FPor='" + parametros.getFpor() + "'";
			}
			if (!parametros.getCpago().equals("")) {
				restricciones += " AND F.CPago='" + parametros.getCpago() + "'";
			}
			if (!parametros.getEstado().equals("")) {
				if (parametros.getEstado().equals("Abierto"))
					restricciones += " AND F.Estado='Por Cobrar'";
				if (parametros.getEstado().equals("Cerrada"))
					restricciones += " AND F.Estado='Cobrada'";
				else
					restricciones += "";
			}
			if (!parametros.getMedioPago().equals("")) {
				restricciones += " AND COALESCE(PAGOP.Medio, INCONS.MedioPago, CASE WHEN F.CPago = 'PREPAGO 100%' THEN F.TPago END, 'Pendiente') = '"
						+ parametros.getMedioPago() + "'";
			}

			switch (parametros.getDrc()) {
			case 0:
				restricciones += " AND DATEDIFF(DAY, GETDATE(),COALESCE(PAGOP.FEPago, INCONS.FechaPago, CASE WHEN F.CPago = 'PREPAGO 100%' THEN F.Fecha ELSE NULL END)) > 4 AND F.Estado <> 'Cobrada'";
				break;
			case 1:
				restricciones += " AND DATEDIFF(DAY, GETDATE(),COALESCE(PAGOP.FEPago, INCONS.FechaPago, CASE WHEN F.CPago = 'PREPAGO 100%' THEN F.Fecha ELSE NULL END)) IN (2,3,4) AND F.Estado <> 'Cobrada'";
				break;
			case 2:
				restricciones += " AND DATEDIFF(DAY, GETDATE(),COALESCE(PAGOP.FEPago, INCONS.FechaPago, CASE WHEN F.CPago = 'PREPAGO 100%' THEN F.Fecha ELSE NULL END)) IN (-1,0,1) AND F.Estado <> 'Cobrada'";
				break;
			case 3:
				restricciones += " AND DATEDIFF(DAY, GETDATE(),COALESCE(PAGOP.FEPago, INCONS.FechaPago, CASE WHEN F.CPago = 'PREPAGO 100%' THEN F.Fecha ELSE NULL END)) IN (-4,-3,-2) AND F.Estado <> 'Cobrada'";
				break;
			case 4:
				restricciones += " AND DATEDIFF(DAY, GETDATE(),COALESCE(PAGOP.FEPago, INCONS.FechaPago, CASE WHEN F.CPago = 'PREPAGO 100%' THEN F.Fecha ELSE NULL END)) < -4 AND F.Estado <> 'Cobrada' ";
				break;
			default:
				restricciones += " ";
				break;
			}

			if (parametros.getFechaInicio() != null && parametros.getFechaFin() != null) {
				String fechas = "";
				if (parametros.getIndividual()) {
					fechas = " AND F.Fecha >=" + funcion.convertirDosFechasAString(parametros.getFechaInicio(),
							parametros.getFechaFin(), "F.Fecha");
					totales = cobrosDAO.findComparativasDPeriodos(fechas + " " + restricciones);
					for (ResumenConsulta r : totales) {
						r.setEtiqueta("Actual");
						r.setFechaInicio(parametros.getFechaInicio());
						r.setFechaFinal(parametros.getFechaFin());
					}
				} else {
					String fechas2 = "";
					String fechas3 = "";
					Date Fini2, Ffin2, Fini3, Ffin3;
					Date[] rango = null;

					fechas = " AND F.Fecha >=" + funcion.convertirDosFechasAString(parametros.getFechaInicio(),
							parametros.getFechaFin(), "F.Fecha");
					rango = funcion.calcularFechasPeriodoAnterior(parametros.getFechaInicio(),
							parametros.getFechaFin());
					fechas2 = " AND F.Fecha >=" + funcion.convertirDosFechasAString(rango[0], rango[1], "F.Fecha");
					Fini2 = rango[0];
					Ffin2 = rango[1];
					rango = funcion.calcularFechasPeriodoAnterior(rango[0], rango[1]);
					fechas3 = " AND F.Fecha >=" + funcion.convertirDosFechasAString(rango[0], rango[1], "F.Fecha");
					Fini3 = rango[0];
					Ffin3 = rango[1];

					totales = cobrosDAO.findComparativasDPeriodos(fechas + " " + restricciones);
					for (ResumenConsulta r : totales) {
						r.setEtiqueta("Actual");
						r.setFechaInicio(parametros.getFechaInicio());
						r.setFechaFinal(parametros.getFechaFin());
					}

					List<ResumenConsulta> temp = cobrosDAO.findComparativasDPeriodos(fechas2 + " " + restricciones);
					for (ResumenConsulta r : temp) {
						r.setEtiqueta("Pasado");
						r.setFechaInicio(Fini2);
						r.setFechaFinal(Ffin2);
						totales.add(r);
					}

					temp.clear();
					temp = cobrosDAO.findComparativasDPeriodos(fechas3 + " " + restricciones);
					for (ResumenConsulta r : temp) {
						r.setEtiqueta("Postpasado");
						r.setFechaInicio(Fini3);
						r.setFechaFinal(Ffin3);
						totales.add(r);
					}
				}
			}
			return totales;
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			return null;
		}
	}
}
