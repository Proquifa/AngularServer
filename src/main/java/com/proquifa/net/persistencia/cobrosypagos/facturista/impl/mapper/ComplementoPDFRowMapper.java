package com.proquifa.net.persistencia.cobrosypagos.facturista.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.catalogos.cobrosypagos.facturista.ComplementoPago;
import com.proquifa.net.modelo.cobrosypagos.facturista.ConceptoFactura;
import com.proquifa.net.modelo.comun.util.Funcion;


public class ComplementoPDFRowMapper implements RowMapper{
	@Override
	public Object mapRow(ResultSet res, int arg1) throws SQLException {
		Funcion funcion = new Funcion();
		ComplementoPago datos = new ComplementoPago();
		try {
			datos.setClave(res.getString("clave"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			Double pesos = res.getDouble("cantidad");
			datos.setCantidad(pesos.intValue());
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			datos.setVersion(res.getString("version"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			datos.setClaveUnidad(res.getString("claveUnidad"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			datos.setDescripcion(res.getString("descripcion"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			Double imp = (double) res.getFloat("importe");
			String importe = funcion.formatoMoneda(imp);
			datos.setImporte(importe);
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			Double valU = (double)res.getFloat("valorUnitario");
			String valor = funcion.formatoMoneda(valU);
			datos.setValorUnitario(valor);
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			datos.setMonto(res.getFloat("monto"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			datos.setFechaP(res.getString("fechaP"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			datos.setMoneda(res.getString("moneda"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			Double base = Double.parseDouble(res.getString("impBase"));
			datos.setImpBase(funcion.formatoMoneda(base));
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			datos.setUnidad(res.getString("Unidad"));
			datos.setImpuesto(res.getString("ImpImpuesto"));
			datos.setTipoFactor(res.getString("ImpTipoFactor"));
			datos.setTasa(res.getString("ImpTasaOcuota"));
			datos.setIdentificador(res.getString("NoIdentificacion"));
			datos.setComentario(res.getString("comentario"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			Double importeTrans = 	Double.parseDouble(res.getString("ImpImporte"));
			datos.setImpTranslado(funcion.formatoMoneda(importeTrans));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return datos;
	}
}
