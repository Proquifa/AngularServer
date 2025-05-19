/**
 * 
 */
package com.proquifa.net.persistencia.ventas.visitas.impl.mapper;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.comun.CatalogoItem;
import com.proquifa.net.modelo.comun.Contacto;
import com.proquifa.net.modelo.comun.HallazgosAcciones;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.ventas.requisicion.PRequisicion;
import com.proquifa.net.modelo.ventas.requisicion.Requisicion;
import com.proquifa.net.modelo.ventas.visitas.DocumentoSolicitudVisita;
import com.proquifa.net.modelo.ventas.visitas.SolicitudVisita;

/**
 * @author yosimar.mendez
 *
 */
public class ReportarVisitaRowMapper implements RowMapper {
	
	private Map<String, Object> lstEtapa;

	@SuppressWarnings("unchecked")
	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		if (lstEtapa == null )
			lstEtapa = new HashMap<String, Object>();
		
		Funcion function = new Funcion();
		String tipoConsulta = rs.getString("ETAPA");
		if (tipoConsulta.equals("REQUERIMIENTOS")) {
			SolicitudVisita solicitud = new SolicitudVisita();
			solicitud.setIdSolicitudVisita(rs.getLong("PK_SolicitudVisita"));
			solicitud.setMotivo(rs.getString("Motivo"));
			solicitud.setAsunto(rs.getString("Asunto"));
			solicitud.setCalificacion(rs.getInt("Calificacion"));
			solicitud.setRespuesta(rs.getString("Respuesta"));
			solicitud.setFechaSolicitud(rs.getDate("Fecha"));
			solicitud.setNombreSolicitante(rs.getString("Nombre"));
			solicitud.setUsuarioSolicitante(rs.getString("Usuario"));
			try {
				String ruta = function.obtenerRutaServidor("requerimientos", "");
				if (new File(ruta + solicitud.getIdSolicitudVisita().toString() + ".pdf").exists()) {
					solicitud.setDocumento(new DocumentoSolicitudVisita("requerimientos.pdf"));
				}
			} catch (ProquifaNetException e) {
				e.printStackTrace();
			}
			Contacto contacto = new Contacto();
			contacto.setNombre(rs.getString("Contacto"));
			contacto.setIdContacto(rs.getLong("idContacto"));
			solicitud.setContacto(contacto);
			
			if (lstEtapa.containsKey("REQUERIMIENTOS")) {
				((List<SolicitudVisita>) lstEtapa.get("REQUERIMIENTOS")).add(solicitud);
			} else {
				lstEtapa.put("REQUERIMIENTOS", new ArrayList<SolicitudVisita>());
				((List<SolicitudVisita>) lstEtapa.get("REQUERIMIENTOS")).add(solicitud);
			}
		}
		else if (tipoConsulta.equals("PENDIENTES")) {
			HallazgosAcciones hallazgos = new HallazgosAcciones();
			hallazgos.setIdHallazgoAccion(rs.getInt("PK_HallazgosAcciones"));
			hallazgos.setDescripcion(rs.getString("Descripcion"));
			hallazgos.setFerealizacion(rs.getDate("FERealizacion"));
			hallazgos.setObservaciones(rs.getString("Observaciones"));
			
			if (lstEtapa.containsKey("PENDIENTES")) {
				((List<HallazgosAcciones>) lstEtapa.get("PENDIENTES")).add(hallazgos);
			} else {
				lstEtapa.put("PENDIENTES", new ArrayList<SolicitudVisita>());
				((List<HallazgosAcciones>) lstEtapa.get("PENDIENTES")).add(hallazgos);
			}
		}
		else if (tipoConsulta.equals("HALLAZGOS")) {
			HallazgosAcciones hallazgos = new HallazgosAcciones();
			hallazgos.setIdHallazgoAccion(rs.getInt("PK_HallazgosAcciones"));
			hallazgos.setDescripcion(rs.getString("Descripcion"));
			hallazgos.setFerealizacion(rs.getDate("FERealizacion"));
			hallazgos.setObservaciones(rs.getString("Observaciones"));
			hallazgos.setTipoHallazgo(rs.getString("TipoHallazgo"));
			hallazgos.setCompetencia(rs.getString("Competencia"));
			hallazgos.setIdVisita(rs.getLong("FK01_Visita"));
			hallazgos.setDescripcionAccion(rs.getString("Desc_Accion"));
			hallazgos.setMotivoDescartado(rs.getString("MotivoDescartado"));
			
			try {
				String ruta = function.obtenerRutaServidor("hallazgos", "");
				if (new File(ruta + hallazgos.getIdHallazgoAccion().toString() + ".pdf").exists()) {
					hallazgos.setDocumento(new DocumentoSolicitudVisita("hallazgos.pdf"));
				}
			} catch (ProquifaNetException e) {
				e.printStackTrace();
			}
			
			CatalogoItem proveedor = new CatalogoItem();
			proveedor.setLlave(rs.getLong("FK02_Marca"));
//			proveedor.setRazonSocial(rs.getString("RSocial"));
			proveedor.setValor(rs.getString("Nombre"));
			proveedor.setSelected(true);
			
			if (lstEtapa.containsKey("HALLAZGOS")) {
				if (((Map<String, Object>) lstEtapa.get("HALLAZGOS")).containsKey(hallazgos.getIdHallazgoAccion().toString())) {
					hallazgos = (HallazgosAcciones) ((Map<String, Object>) lstEtapa.get("HALLAZGOS")).get(hallazgos.getIdHallazgoAccion().toString());
					hallazgos.getMarcas().add(proveedor);
				}
				else {
					((Map<String, Object>) lstEtapa.get("HALLAZGOS")).put(hallazgos.getIdHallazgoAccion().toString(), hallazgos);
					hallazgos = (HallazgosAcciones) ((Map<String, Object>) lstEtapa.get("HALLAZGOS")).get(hallazgos.getIdHallazgoAccion().toString());
					hallazgos.setMarcas(new ArrayList<CatalogoItem>());
					hallazgos.getMarcas().add(proveedor);
				}
			} else {
				lstEtapa.put("HALLAZGOS", new HashMap<String, Object>());
				((Map<String, Object>) lstEtapa.get("HALLAZGOS")).put(hallazgos.getIdHallazgoAccion().toString(), hallazgos);
				hallazgos = (HallazgosAcciones) ((Map<String, Object>) lstEtapa.get("HALLAZGOS")).get(hallazgos.getIdHallazgoAccion().toString());
				hallazgos.setMarcas(new ArrayList<CatalogoItem>());
				hallazgos.getMarcas().add(proveedor);
				
			}
		}
		else if (tipoConsulta.equals("REQUISICIONES")) {
			Requisicion requisicion = new Requisicion();
			requisicion.setIdRequi(rs.getInt("FK01_Requisicion"));
			requisicion.setIdCliente(rs.getLong("FK02_Cliente"));
			requisicion.setIdContacto(rs.getLong("FK03_Contacto"));
			requisicion.setIdEmpleadoEv(rs.getInt("FK04_EV"));
			requisicion.setIdEmpleadoEsac(rs.getInt("FK05_ESAC"));
			requisicion.setIdVisita(rs.getInt("FK06_Visita"));
			requisicion.setIdDoctoR(rs.getInt("idDoctoR"));
			
			
			PRequisicion prequisicion = new PRequisicion();
			prequisicion.setMarca(rs.getString("Marca"));
			prequisicion.setCodigo(rs.getString("Codigo"));
			prequisicion.setConcepto(rs.getString("Concepto"));
			prequisicion.setPiezasACotizar(rs.getInt("Cant"));
			prequisicion.setCantidad(rs.getInt("Cantidad"));
			prequisicion.setUnidad(rs.getString("Unidad"));
			prequisicion.setPrecioU(rs.getFloat("Precio"));
			prequisicion.setIdRequisicion(rs.getInt("FK01_Requisicion"));
			
			
			
			if(prequisicion.getPiezasACotizar() != null && prequisicion.getPiezasACotizar() > 0){
				if (lstEtapa.containsKey("REQUISICIONES")) {
					if (((Requisicion) lstEtapa.get("REQUISICIONES")).getPartidaRequisicion() != null &&  
							!((Requisicion) lstEtapa.get("REQUISICIONES")).getPartidaRequisicion().isEmpty()) {
						((Requisicion) lstEtapa.get("REQUISICIONES")).getPartidaRequisicion().add(prequisicion);
					} else {
						((Requisicion) lstEtapa.get("REQUISICIONES")).setPartidaRequisicion(new ArrayList<PRequisicion>());
						((Requisicion) lstEtapa.get("REQUISICIONES")).getPartidaRequisicion().add(prequisicion);
					}
				} else {
					lstEtapa.put("REQUISICIONES", requisicion);
					((Requisicion) lstEtapa.get("REQUISICIONES")).setPartidaRequisicion(new ArrayList<PRequisicion>());
					((Requisicion) lstEtapa.get("REQUISICIONES")).getPartidaRequisicion().add(prequisicion);
				}
			}else {
				//if(!lstEtapa.containsKey("REQUISICIONES")){
				lstEtapa.put("REQUISICIONES", requisicion);
			}
		}
		
		return null;
	}

	/**
	 * @return the lstEtapa
	 */
	public Map<String, Object> getLstEtapa() {
		return lstEtapa;
	}

}
