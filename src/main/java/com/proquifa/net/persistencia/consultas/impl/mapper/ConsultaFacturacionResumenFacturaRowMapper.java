package com.proquifa.net.persistencia.consultas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.TiempoProceso;

import org.springframework.jdbc.core.RowMapper;

public class ConsultaFacturacionResumenFacturaRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		TiempoProceso tiempo = new TiempoProceso();
		
		tiempo.setId(rs.getInt("ID"));
		tiempo.setEtiquetas(rs.getString("Etiqueta"));
		tiempo.setFechaFacturacion(rs.getTimestamp("FechaFacturacion"));
		tiempo.setResponsable(rs.getString("Facturo"));
		tiempo.setTCambio(rs.getDouble("TCambio"));
		tiempo.setTipo(rs.getString("Tipo"));
		tiempo.setMedio(rs.getString("Medio"));
		tiempo.setFechaRevision(rs.getTimestamp("FechaRevision"));
		tiempo.setCommentRevision(rs.getString("ComentariosRevision"));
		tiempo.setMensajero(rs.getString("Mensajero"));
		tiempo.setDocsCierre(rs.getString("DocumentoCierre"));
		tiempo.setDoscResult1(rs.getString("Documento"));
		tiempo.setDoscResult2(rs.getString("Documento1"));
		tiempo.setFechaEntrega(rs.getTimestamp("FechaEntrega"));
		tiempo.setConforme(rs.getString("Conforme"));
		tiempo.setZonaMensajeria(rs.getString("Zona"));
		tiempo.setRutaMensajeria(rs.getString("Ruta"));
		tiempo.setEntregaRevision(rs.getBoolean("EntregaRevision"));
		tiempo.setRefacturacion(rs.getString("Refacturacion"));
		tiempo.setMonto(rs.getDouble("Monto"));
		tiempo.setFechaCobro(rs.getTimestamp("FechaCobro"));
		tiempo.setFactura(rs.getString("Factura"));
		tiempo.setMoroso(rs.getBoolean("Moroso"));
		tiempo.setMoneda(rs.getString("Moneda"));
		tiempo.setFolio(rs.getString("FolioNC"));
		tiempo.setFpor(rs.getString("FPor"));
		tiempo.setDocumento(rs.getString("FolioCompPago"));
		return tiempo;
	}

}
