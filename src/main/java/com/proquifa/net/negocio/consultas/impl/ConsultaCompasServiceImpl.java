/**
 * 
 */
package com.proquifa.net.negocio.consultas.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proquifa.net.modelo.compras.Compra;
import com.proquifa.net.modelo.compras.PartidaCompra;
import com.proquifa.net.modelo.comun.TiempoProceso;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.consultas.comun.ResumenConsulta;
import com.proquifa.net.negocio.consultas.ConsultaComprasService;
import com.proquifa.net.persistencia.comun.ProductoDAO;
import com.proquifa.net.persistencia.consultas.ConsultaComprasDAO;

/**
 * @author vromero
 *
 */
@Service("consultaComprasService")
public class ConsultaCompasServiceImpl implements ConsultaComprasService {
	@Autowired
	ConsultaComprasDAO comprasDAO;
	@Autowired
	ProductoDAO productoDAO;
	
	private Funcion funcion;
	
	public List<Compra> obtenerCompras(Date FechaInicio, Date FechaFin,
			Long proveedor, Integer estado, String ordenCompra, Long usuario, String empresaCompra)
			throws ProquifaNetException {
		try{
			//log.info("aqui essta el servicio");
			String periodo = new String();
			String proveedorS = new String();
			String estadoS = new String();
			String sEmpresa = "";
			String ordenCompraS = new String();
			String usuarioS = new String();
			String vAND = new String();
			
			SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyyMMdd");
				
			if(FechaInicio != null){
				
				periodo = " C.Fecha >= '"+formatoDeFecha.format(FechaInicio)+" 00:00' ";
				vAND = " AND ";
			}
			if(FechaInicio != null){
				periodo = periodo + vAND + " C.Fecha <= '"+formatoDeFecha.format(FechaFin)+" 23:59' ";
				vAND = " AND ";
			}
			if(proveedor != null && proveedor > 0){
				proveedorS = vAND + " PROV.Clave = " + proveedor.toString();
				vAND = " AND ";
			}
		
			switch(estado){
				case 0 :	estadoS = "";
			
					break;
				case 1 :	estadoS = vAND + " EDO.T = 0 ";
							vAND = " AND ";
						
					break;
				case 2 :	estadoS = vAND + " EDO.T > 0";
							vAND = " AND ";
							
					break;
			}		
			
			if(!ordenCompra.equals("")){
				ordenCompraS = vAND + " C.clave like '%" + ordenCompra + "%'";
				vAND = " AND ";
			}
			if(usuario != null && usuario > 0){
				usuarioS = vAND + " EM.Clave =" + usuario;
			}
						
			if (empresaCompra != null && !empresaCompra.equals("")){
				sEmpresa = " AND C.FacturarA = '" + empresaCompra + "' ";
			}
			
			List<Compra> compras = this.comprasDAO.findCompras(periodo, proveedorS, estadoS, ordenCompraS, usuarioS,sEmpresa);
			
			return compras;
		
		}catch (Exception e) {
		//	Logger.getLogger(ConsultaComprasService.class).info("Error:" + e.getMessage());
			funcion = new Funcion();
			//funcion.enviarCorreoAvisoExepcion(e);
			return null;
		}		
	}

	public List<PartidaCompra> obtenerPatidasCompraParaCEspecifica(
			String ordenCompra) throws ProquifaNetException {
		try {
			List<PartidaCompra> paList = this.comprasDAO.obtenerPatidasCompraParaCEspecifica(ordenCompra);
			for( PartidaCompra p:paList){	
					p.setDescripcionProducto(productoDAO.obtenerDescripcionProducto(p.getCodigo(),p.getFabrica()));
					p.setInTime(comprasDAO.obtenerEnTiempo(p.getIdPartidaCompra()));
				if(p.getDescripcionProducto() == null){
					p.setDescripcionProducto(productoDAO.obtenerDescripcionProductoComplemento(p.getIdComplemento()));
				}	
			}
			return paList;
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			return null;
		}
	}

	public List<TiempoProceso> obtenerTiempoProcesoPorPartida(Long idPCompra) throws ProquifaNetException {

		try {
			List<TiempoProceso>  temp=  this.comprasDAO.obtenerTiempoProcesoPorPartida(idPCompra);
			List<TiempoProceso>  obj=  this.comprasDAO.obtenerHistorialPhsMatriz(idPCompra);
			String despPhs=null,responsable=null;
			String despMat=null;
			
			ArrayList<TiempoProceso> resultadoFinal = new ArrayList<TiempoProceso>();
			for (TiempoProceso renglonPrincipal:temp) {
				resultadoFinal.add(renglonPrincipal);
				if(renglonPrincipal.getEtapa().equals("INSPECCIÓN PHS")){
					 despPhs=renglonPrincipal.getDespachable();
					for (TiempoProceso renglonSecundarioA:obj) {
						if(renglonSecundarioA.getEtapa().equals("HISTORIAL PHS")){
							resultadoFinal.add(renglonSecundarioA);
						}
					}
				}
				if(renglonPrincipal.getEtapa().equals("INSPECCIÓN MATRIZ")){
					despMat=renglonPrincipal.getDespachable();
					for (TiempoProceso renglonSecundarioB:obj) {
						if(renglonSecundarioB.getEtapa().equals("HISTORIAL MATRIZ")){
							resultadoFinal.add(renglonSecundarioB);
						}
					}
				}
				if(renglonPrincipal.getEtapa().equals("TRÁNSITO PHS") && !renglonPrincipal.getEtapa().equals(null) 
						&& !renglonPrincipal.getEtapa().equals("")){
					responsable=renglonPrincipal.getResponsable();
				}
				if(renglonPrincipal.getEtapa().equals("TRÁNSITO MATRIZ")){
					responsable=renglonPrincipal.getResponsable();
				}
			}
			   for(TiempoProceso r:resultadoFinal){
				   if(r.getEtapa().equalsIgnoreCase("RECIBIDO")){
						r.setDespachable(despPhs);
						r.setEdicion(despMat);
						r.setResponsable(responsable);
				   }
			}
			   return resultadoFinal;
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			return null;
		}
		
		
	}



	public List<ResumenConsulta> obtenerResumenDeConsultaCompras(Date FechaInicio,
			Date FechaFin, Long proveedor, Integer estado, Integer coloco,
			String ordenCompra, Long usuario) throws ProquifaNetException {
		
		try {
			funcion = new Funcion();
			String periodo = new String();
			String proveedorS = new String();
			String proveedorS2 = new String();
			String proveedorS3 = new String();
			String estadoS = new String();
			String colocoS = new String();
			String ordenCompraS = new String();
			String usuarioS = new String();
			String vAND =  " AND ";
					
			if(proveedor > 0){
				proveedorS = vAND + " PROV.Clave = " + proveedor.toString();
				proveedorS2 = vAND + " PROD.Proveedor = " + proveedor.toString();
				proveedorS3 = vAND + " PRO.Proveedor = " + proveedor.toString();
			}

			switch(estado){
				case 0 :	estadoS = "";
					break;
				case 1 :	estadoS = vAND + " EDO.T = 0 ";
					break;
				case 2 :	estadoS = vAND + " EDO.T > 0";
					break;
			}
			
			switch(coloco){
				case 0 :	colocoS = "";
					break;
				case 1 :	colocoS = vAND + " C.ColocarDesde = 'Ciudad de México' ";
					break;
				case 2 :	colocoS = vAND + " C.ColocarDesde = 'Laredo'";
					break;
				case 3 :	colocoS = vAND + " C.ColocarDesde = ''";
				break;
			}
			
			if(!ordenCompra.equals("")){
				ordenCompraS = vAND + " C.clave like '%" + ordenCompra + "%'";
			}
			if(usuario > 0){
				usuarioS = vAND + " EM.Clave =" + usuario;
			}
			
			
			if(FechaInicio != null && FechaFin != null){
				periodo = " C.Fecha >= "+ funcion.convertirDosFechasAString(FechaInicio, FechaFin,"C.Fecha");
			}
			
			   List<ResumenConsulta> compras = this.comprasDAO.findResumenCompras(periodo, proveedorS3, estadoS, colocoS, ordenCompraS, usuarioS);
			   List<ResumenConsulta> temp = this.comprasDAO.findTopProveedores(periodo, proveedorS, estadoS, colocoS, ordenCompraS, usuarioS);
			   for(ResumenConsulta r:temp){
				   compras.add(r);
			   }
			   temp = this.comprasDAO.findTopProductos(periodo, proveedorS2, estadoS, colocoS, ordenCompraS, usuarioS);
			   for(ResumenConsulta r:temp){
				   compras.add(r);
			   }
			return compras;
			
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			return null;
		}
		
	}


	public List<ResumenConsulta> findTotalesCompraPorPeriodo(Date finicio, Date ffin,
			Long proveedor, Integer estado, Integer coloco, String ordenCompra,
			Long usuario, Boolean individual) throws ProquifaNetException {
			
		try {
			funcion = new Funcion();
			String periodo = new String();
			//String proveedorS = new String();
			//String proveedorS2 = new String();
			String proveedorS3 = new String();
			String estadoS = new String();
			String colocoS = new String();
			String ordenCompraS = new String();
			String usuarioS = new String();
			String vAND = new String();
			vAND = " AND ";
			
			if(proveedor > 0){
				proveedorS3 = vAND + " PRO.Proveedor = " + proveedor.toString();
			}

			switch(estado){
				case 0 :	estadoS = "";
					break;
				case 1 :	estadoS = vAND + " EDO.T = 0 ";

					break;
				case 2 :	estadoS = vAND + " EDO.T > 0";

					break;
			}
			
			switch(coloco){
				case 0 :	colocoS = "";
					break;
				case 1 :	colocoS = vAND + " C.ColocarDesde = 'Ciudad de México' ";

					break;
				case 2 :	colocoS = vAND + " C.ColocarDesde = 'Laredo'";

					break;
				case 3 :	colocoS = vAND + " C.ColocarDesde = ''";

				break;
			}
			
			if(!ordenCompra.equals("")){
				ordenCompraS = vAND + " C.clave like '%" + ordenCompra + "%'";

			}
			if(usuario > 0){
				usuarioS = vAND + " EM.Clave =" + usuario;
			}
			
			if(finicio != null && ffin != null){
				
				List<ResumenConsulta> totales = null;
				ResumenConsulta temp1 = new ResumenConsulta();
				ResumenConsulta temp2 = new ResumenConsulta();
				ResumenConsulta temp3 = new ResumenConsulta();
				int con=0;

				if(individual){
					
					periodo = funcion.convertirDosFechasAString(finicio, ffin, "C.Fecha");
						totales = this.comprasDAO.findTotalesComprasPorPeriodo(periodo, proveedorS3, estadoS, colocoS, ordenCompraS, usuarioS);
						
						for (ResumenConsulta r : totales) {
							con++;
							//r.setEtiqueta("Actual");
							temp1.setEtiqueta("Actual");
							if(con==1)
								temp1.setTotalFolios(r.getTotalFolios());
							else if(con ==2)
								temp1.setTotal(r.getTotal());
							else
								temp1.setPiezasTotal(r.getPiezasTotal());
						}
						temp1.setFechaInicio(finicio);
						temp1.setFechaFinal(ffin);
						
						totales.clear();
						totales.add(temp1);
					return totales;
				
				}else{

					String periodo2 = "";
					String periodo3 = "";
					Date Fini2, Ffin2,Fini3,Ffin3;
					Date [] rango = null;
					
					periodo = funcion.convertirDosFechasAString(finicio, ffin, "C.Fecha");
					rango = funcion.calcularFechasPeriodoAnterior(finicio,ffin);
					periodo2 =funcion.convertirDosFechasAString(rango[0], rango[1],"C.Fecha");
					Fini2 = rango[0];
					Ffin2 = rango[1];
					rango = funcion.calcularFechasPeriodoAnterior(rango[0],rango[1]);
					periodo3 =funcion.convertirDosFechasAString(rango[0], rango[1],"C.Fecha");
					Fini3 = rango[0];
					Ffin3 = rango[1];
						
					 totales =  this.comprasDAO.findTotalesComprasPorPeriodo(periodo, proveedorS3, estadoS, colocoS, ordenCompraS, usuarioS);
					for (ResumenConsulta r : totales) {
							con++;
							if(con==1){
								temp1.setEtiqueta("Actual");
								temp1.setTotalFolios(r.getTotalFolios());
								temp1.setFechaInicio(finicio);
								temp1.setFechaFinal(ffin);
							}	
							else if(con ==2)
								temp1.setTotal(r.getTotal());
							else
								temp1.setPiezasTotal(r.getPiezasTotal());
					}
					totales.clear();
					totales.add(temp1);
					con =0;
					
					 List<ResumenConsulta> temp =  this.comprasDAO.findTotalesComprasPorPeriodo(periodo2, proveedorS3, estadoS, colocoS, ordenCompraS, usuarioS);
					 for (ResumenConsulta r : temp) {
						 con++;
							if(con==1){
								temp2.setEtiqueta("Pasado");
								temp2.setTotalFolios(r.getTotalFolios());
								temp2.setFechaInicio(Fini2);
								temp2.setFechaFinal(Ffin2);
							}	
							else if(con ==2)
								temp2.setTotal(r.getTotal());
							else
								temp2.setPiezasTotal(r.getPiezasTotal());
					}
					 totales.add(temp2);
					 temp.clear();
					 con =0;
					temp =  this.comprasDAO.findTotalesComprasPorPeriodo(periodo3, proveedorS3, estadoS, colocoS, ordenCompraS, usuarioS);
					 for (ResumenConsulta r : temp) {
						 	con++;
								if(con==1){
									temp3.setEtiqueta("Postpasado");
									temp3.setTotalFolios(r.getTotalFolios());
									temp3.setFechaInicio(Fini3);
									temp3.setFechaFinal(Ffin3);
								}	
								else if(con ==2)
									temp3.setTotal(r.getTotal());
								else
									temp3.setPiezasTotal(r.getPiezasTotal());
						}
					 totales.add(temp3);
					 return totales;
				}

			}
		return null;
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			return null;
		}
		
		
		
	}

	@Override
	public List<Compra> obtenerGraficaXCompra(Date FechaInicio, Date FechaFin,
			Long proveedor, Integer estado, Integer coloco, String ordenCompra,
			Long usuario) throws ProquifaNetException {
		
		try {
			String periodo = new String();
			String proveedorS = new String();
			String estadoS = new String();
			String colocoS = new String();
			String ordenCompraS = new String();
			String usuarioS = new String();
			String vAND = new String();
			
			SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyyMMdd");
				
			if(FechaInicio != null){
				periodo = " C.Fecha >= '"+formatoDeFecha.format(FechaInicio)+" 00:00' ";
				vAND = " AND ";
			}
			if(FechaInicio != null){
				periodo = periodo + vAND + " C.Fecha <= '"+formatoDeFecha.format(FechaFin)+" 23:59' ";
				vAND = " AND ";
			}
			if(proveedor > 0){
				proveedorS = vAND + " PROV.Clave = " + proveedor.toString();
				vAND = " AND ";
			}

			switch(estado){
				case 0 :	estadoS = "";
					break;
				case 1 :	estadoS = vAND + " EDO.T = 0 ";
							vAND = " AND ";
					break;
				case 2 :	estadoS = vAND + " EDO.T > 0";
							vAND = " AND ";
					break;
			}
			
			switch(coloco){
				case 0 :	colocoS = "";
					break;
				case 1 :	colocoS = vAND + " C.ColocarDesde = 'Ciudad de México' ";
							vAND = " AND ";
					break;
				case 2 :	colocoS = vAND + " C.ColocarDesde = 'Laredo'";
							vAND = " AND ";
					break;
				case 3 :	colocoS = vAND + " C.ColocarDesde = ''";
							vAND = " AND ";
				break;
			}
			
			if(!ordenCompra.equals("")){
				ordenCompraS = vAND + " C.clave like '%" + ordenCompra + "%'";
				vAND = " AND ";
			}
			if(usuario > 0){
				usuarioS = vAND + " EM.Clave =" + usuario;
			}
			
			return comprasDAO.obtenerGraficaXCompra(periodo, proveedorS, estadoS, colocoS, ordenCompraS, usuarioS);
		} catch (Exception e) {
			e.printStackTrace();
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			return null;
		}
	}

	@Override
	public List<Compra> obtenerReporteCompra(Date FechaInicio, Date FechaFin, Long proveedor, Integer estado,
			Integer coloco, String ordenCompra) throws ProquifaNetException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
