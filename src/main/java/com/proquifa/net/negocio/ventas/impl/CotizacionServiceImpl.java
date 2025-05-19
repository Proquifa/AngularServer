

package com.proquifa.net.negocio.ventas.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.comun.Producto;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.ventas.Cotizacion;
import com.proquifa.net.modelo.ventas.DoctoR;
import com.proquifa.net.modelo.ventas.PartidaCotizacion;
import com.proquifa.net.negocio.ventas.CotizacionService;
import com.proquifa.net.persistencia.comun.DocumentoRecibidoDAO;
import com.proquifa.net.persistencia.comun.EmpleadoDAO;
import com.proquifa.net.persistencia.comun.FolioDAO;
import com.proquifa.net.persistencia.comun.PendienteDAO;
import com.proquifa.net.persistencia.comun.ProductoDAO;
import com.proquifa.net.persistencia.ventas.CotizacionDAO;
import com.proquifa.net.persistencia.ventas.PartidaCotizacionDAO;

@Service("cotizacionService")
public class CotizacionServiceImpl implements CotizacionService {
	@Autowired
	CotizacionDAO cotizacionDAO;
	@Autowired 
	DocumentoRecibidoDAO documentoRecibidoDAO;
	@Autowired
	PartidaCotizacionDAO partidaCotizacionDAO;
	/*@Autowired
	PartidaCotizacionPharmaDAO partidaCotizacionPharmaDAO;*/
	@Autowired
	EmpleadoDAO empleadoDAO;
	
	@Autowired
	FolioDAO folioDAO;
	
	@Autowired
	ProductoDAO productoDAO;
	
	@Autowired
	PendienteDAO pendienteDAO;
	
	/*@Autowired
	CatalogoClientesService catalogoClientesDAO;*/
	
	private Funcion funcion = new Funcion();
	
//	private final static Logger logger = Logger.getLogger(CotizacionServiceImpl.class);


public List<Cotizacion> buscarCotizacionesParaReporte(Date finicio,
			Date ffin, Cotizacion cotizacion, Long idEmpleado)
					throws ProquifaNetException {
		try {
			String condiciones = "";
			SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyyMMdd");
			
			//CONSULTA PARA LA BUSQUEDA RAPIDA
			if(cotizacion.getFolioCotizacion()!=null && !cotizacion.getFolioCotizacion().equals("")){
				condiciones = condiciones + " COTI.Clave LIKE '%"+ cotizacion.getFolioCotizacion() +"%'";
			}else{
				//CONSULTA PARA LA BUSQUEDA POR FECHAS
				if(finicio!=null && ffin!=null){
					condiciones = " COTI.Fecha BETWEEN '"+ formatoDeFecha.format(finicio) +" 00:00:00' AND '"+ formatoDeFecha.format(ffin) +" 23:59:59'";
					if(cotizacion.getNombreCliente()!=null && !cotizacion.getNombreCliente().equals("--TODOS--")){
						condiciones+= " AND COTI.Cliente='"+ cotizacion.getNombreCliente() +"'";
					}
					if(cotizacion.getMSalida()!=null && !cotizacion.getMSalida().equals("--TODOS--")){
						if(cotizacion.getMSalida().equals("Correo")){
							condiciones+=" AND COTI.MSalida='C' ";
						}else if(cotizacion.getMSalida().equals("Fax")){
							condiciones += " AND COTI.MSalida='F' ";
						}else if(cotizacion.getMSalida().equals("Pendiente")){
							condiciones += " AND (COTI.MSalida IS NULL OR COTI.MSalida='') ";
						}
					}

					if(cotizacion.getEstado()!=null && !cotizacion.getEstado().equals("--TODOS--")){
						if(cotizacion.getEstado().equals("Cerrado")){
							condiciones+=" AND (COALESCE(Gestionando.Gestionando,'0')+COALESCE(PartidasTerminadas.PCPedidas,'0'))=PartidasEnCero.Ceros";
						}else{
							condiciones+=" AND (COALESCE(Gestionando.Gestionando,'0')+COALESCE(PartidasTerminadas.PCPedidas,'0'))<>PartidasEnCero.Ceros";
						}
					}
				}				
			}

			Empleado e = this.empleadoDAO.obtenerEmpleadoPorId(idEmpleado);
			if(!e.getNivelGeneral().equals("Directivo") && !e.getNivelGeneral().equals("Gerente")){
				if(e.getNombreFuncion().equals("Ejecutivo de Servicio a Clientes Master")){
					if(cotizacion.getCotizo()!=null && !cotizacion.getCotizo().equals("--TODOS--")){
						condiciones+= " AND CLI.Vendedor='"+ cotizacion.getCotizo() +"'";
					}else{
						Integer count = 0;
						condiciones += " AND CLI.Vendedor in (";
						//						Como el string recibido es numerico, se trata de un esac master
						//						asi que se buscaran todos los empleados relacionados a este grupo
						List<String> usuarios = empleadoDAO.finEquipoESAC(idEmpleado);
						for(String user:usuarios){
							count ++;
							condiciones += "'" + user + "'";
							if(count<usuarios.size()){
								condiciones +=" , ";
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
				if(cotizacion.getCotizo()!=null && !cotizacion.getCotizo().equals("--TODOS--") && !cotizacion.getCotizo().equals("")){
					condiciones+= " AND COTI.Cotizo='"+ cotizacion.getCotizo() +"'";
				}
			}

			return this.cotizacionDAO.findCotizacionesParaReporte(condiciones);

		}catch (Exception e) {
			//logger.error(e.getMessage());
			return null;
		}

	}



	@Override
	public List<Cotizacion> buscarCotizacionPorFolio(String folio) throws ProquifaNetException {
		List<Cotizacion> resultado = this.cotizacionDAO.obtenerCotizacionPorFolio(folio);
		return resultado;
	}

	@Override
	public boolean generarCotizacionPconnect(Integer idContacto, Integer idProducto) throws ProquifaNetException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DoctoR generarDoctoR(DoctoR docto) throws ProquifaNetException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cotizacion guardarCotizacion(Cotizacion cotizacion) throws ProquifaNetException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PartidaCotizacion> guardarPartidasCotizacion(List<PartidaCotizacion> partidas)
			throws ProquifaNetException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean generarCotizacionReporte(Integer idContacto, List<Producto> productos, Boolean previsualizar,
			String empresa) throws ProquifaNetException {
		// TODO Auto-generated method stub
		return false;
	}
	
}