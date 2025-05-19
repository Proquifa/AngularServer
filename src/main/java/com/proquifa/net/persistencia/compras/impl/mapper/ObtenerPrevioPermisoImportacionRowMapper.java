package com.proquifa.net.persistencia.compras.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.compras.PermisoImportacion;

import org.springframework.jdbc.core.RowMapper;

public class ObtenerPrevioPermisoImportacionRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		PermisoImportacion per = new PermisoImportacion();
		
		per.setProveedor(rs.getString("Nombre"));
		per.setTipoProducto(rs.getString("TIPO"));
		per.setTipoSolicitudPermiso(rs.getString("TipoPermiso"));
		per.setReqPermiso(rs.getString("PermisoImp"));
		per.setCodigoProducto(rs.getString("Codigo"));
		per.setNombreProducto(rs.getString("NOMBRE"));
		per.setSistema(rs.getString("SISTEMA"));
		per.setOrigen(rs.getString("ORIGEN"));
		per.setConcepto(rs.getString("Concepto"));
		per.setSubTipo(rs.getString("Subtipo"));
		per.setControlProducto(rs.getString("Control"));
		per.setClasificacionSolictud(rs.getString("Clasificacion"));
		per.setCantidadString(rs.getString("Cantidad"));
		per.setUnidad(rs.getString("unidad"));
		per.setDepositarioInternacional(rs.getString("DepositarioInternacional"));
		per.setCas(rs.getString("CAS"));
		per.setEstadoFisico(rs.getString("EstadoFisico"));
		per.setFraccionArancelaria(rs.getString("FraccionArancelaria"));
		per.setTipoPermiso(rs.getString("TipoPermiso"));
		per.setClasificacionProducto(rs.getString("ClasificacionProducto"));
		per.setDatosToxicologicos(rs.getString("DatosToxicologicos"));
		per.setPais(rs.getString("Pais"));
		per.setMonto(rs.getDouble("MONTO"));
		per.setPiezas(rs.getLong("PIEZAS"));
		per.setFolio(rs.getString("Folio"));
		per.setPartida(rs.getString("Partida"));
		per.setDocto(rs.getString("Docto"));
		per.setEstadoPermiso(rs.getString("Estadopermiso"));
		per.setIdProducto(rs.getLong("idProducto"));
		per.setIdpcotiza(rs.getString("IDPCOTIZA"));
		
		return per;
	}

}
