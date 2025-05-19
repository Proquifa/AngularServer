package com.proquifa.net.persistencia.catalogos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.catalogos.FormulaPrecio;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author orosales
 *
 */

public class FormulaPrecioRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {

		FormulaPrecio f = new FormulaPrecio();
		
		f.setCostoConsularizacion((rs.getDouble("C")));
		f.setCostoFijo((rs.getDouble("F")));
		f.setDescuento(rs.getDouble("R"));
		f.setDta((rs.getDouble("K")));
		f.setFlete((rs.getDouble("B")));
		f.setFleteDocumentacion((rs.getDouble("D")));
		f.setHonorariosAgenteA((rs.getDouble("L")));
		f.setIgi((rs.getDouble("J")));
		f.setLicenciamiento((rs.getDouble("Q")));
		f.setMinimoOrdebCompra((rs.getDouble("O")));
		f.setNumeroPiezas((rs.getDouble("N")));
		f.setPermiso((rs.getDouble("M")));
		f.setUtilidad((rs.getDouble("G")) );
		f.setPrecioLista(Math.rint(((rs.getDouble("PRECIOLISTA"))* 1e2) / 1e2));
		f.setValorAduana((rs.getDouble("VA")));
		f.setValor(rs.getDouble("Valor"));
		f.setFleteDestino((rs.getDouble("I")));
		f.setCompuestaCostoF(rs.getBoolean("CompuestaCostoF"));
		f.setCompuestaFactorU(rs.getBoolean("CompuestaFactorU"));
		f.setPrecioListaAnterior(rs.getBoolean("PrecioListaAnterior"));		
		
		return f;

	}
}
