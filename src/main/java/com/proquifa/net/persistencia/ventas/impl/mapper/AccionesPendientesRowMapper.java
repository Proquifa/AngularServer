package com.proquifa.net.persistencia.ventas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.ventas.AccionesPendientes;
import com.proquifa.net.modelo.ventas.Pedido;

public class AccionesPendientesRowMapper implements RowMapper{

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		AccionesPendientes Accion = new AccionesPendientes();
		Accion.setPk_HallazgosAcciones(rs.getInt("PK_HallazgosAcciones"));
		Accion.setDescripcion(rs.getString("Descripcion"));
		Accion.setDescripcionAccion(rs.getString("Desc_Accion"));
		Accion.setTipoHallazgoAccion(rs.getString("Tipo"));
		Accion.setFk01_Visita(rs.getInt("FK01_Visita"));
		Accion.setFerealizacion(rs.getDate("FERealizacion"));
		Accion.setFk02_Cotizacion(rs.getInt("FK02_Cotizacion"));
		Accion.setFk03_Pedido(rs.getInt("FK03_Pedido"));
		Accion.setFk04_VisitaVinculada(rs.getInt("FK04_VisitaVinculada"));
		Accion.setIdContacto(rs.getInt("idContacto"));
		Accion.setContacto(rs.getString("Contacto"));
		Accion.setTel1(rs.getString("Tel1"));
		Accion.setExtension1(rs.getString("Extension1"));
		Accion.setEmail(rs.getString("eMail"));
		Accion.setIdCliente(rs.getInt("Clave"));
		Accion.setEmpresa(rs.getString("Empresa"));
		Accion.setFolio(rs.getString("Folio"));
		Accion.setEstrategia(rs.getString("Estrategia"));
		Accion.setComentarios(rs.getString("Comentarios"));	
		Accion.setClasificacion_Cliente(rs.getString("Clasificacion_Cliente"));
		Accion.setTipo_Visita(rs.getString("Tipo_Visita"));
		Accion.setFecha(rs.getDate("Fecha"));
		Accion.setFecha_Visita(rs.getDate("Fecha_Visita"));
		Accion.setHora_Visita(rs.getTime("Hora_Visita"));
		Accion.setHora_Llegada(rs.getTime("Hora_Llegada"));
		Accion.setFk03_Empleado(rs.getInt("FK03_Empleado"));
		Accion.setEtapa(rs.getString("Etapa"));
		Accion.setTipo(rs.getString("Tipo"));
		Accion.setFechaEstimada(rs.getDate("FechaEstimada"));
		Accion.setAsunto(rs.getString("Asunto"));
		Accion.setDocumentos(rs.getInt("Documentos"));
		Accion.setEstado(rs.getString("Estado"));
		Accion.setSprintAsignado(rs.getInt("SprintAsignado"));
		Accion.setCreditos(rs.getDouble("Creditos"));
		Accion.setValor(rs.getDouble("Valor"));
		Accion.setHora_Visita_Fin(rs.getTime("Hora_Visita_Fin"));
		Accion.setFecha_CheckIn(rs.getDate("Fecha_CheckIn"));
		Accion.setReporte(rs.getString("Reporte"));
		Accion.setVisitaRealizada(rs.getBoolean("VisitaRealizada"));
		Accion.setJustificacionCancelacion(rs.getString("JustificacionCancelacion"));
		Accion.setTipoCancelacion(rs.getString("TipoCancelacion"));
		Accion.setFechaCierre(rs.getDate("FechaCierre"));
		Accion.setFk04_SprintAntiguo(rs.getInt("FK04_SprintAntiguo"));
		Accion.setFk05_CotizasMateriales(rs.getInt("FK05_CotizasMateriales"));
		Accion.setFk06_CotizasPublicaciones(rs.getInt("FK06_CotizasPublicaciones"));
		Accion.setColorFecha(rs.getString("colorFecha"));
		Accion.setFiltroFecha(rs.getString("filtroFecha"));
		Accion.setNumSprint(rs.getInt("NumeroSprint"));
		Accion.setFechaFormateada(rs.getString("fechaFormateada"));
		
		return Accion;
	}
}
