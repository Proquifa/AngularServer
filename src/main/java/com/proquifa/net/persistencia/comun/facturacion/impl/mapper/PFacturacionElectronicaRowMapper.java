package com.proquifa.net.persistencia.comun.facturacion.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.comun.facturaElectronica.PFacturaElectronica;

public class PFacturacionElectronicaRowMapper implements RowMapper {
	public Object mapRow(ResultSet rs, int arg1) throws SQLException{
		PFacturaElectronica pFacturaElectronica = new PFacturaElectronica();
		pFacturaElectronica.setIdPFactura(rs.getInt("PK_PFacturaElectronica"));
		pFacturaElectronica.setClaveProdServ(rs.getString("ClaveProdServ"));
		pFacturaElectronica.setNoIdentificacion(rs.getString("NoIdentificacion"));
		pFacturaElectronica.setCantidad(rs.getString("Cantidad"));
		pFacturaElectronica.setClaveUnidad(rs.getString("ClaveUnidad"));
		pFacturaElectronica.setUnidad(rs.getString("Unidad"));
		pFacturaElectronica.setDescripcion(rs.getString("Descripcion"));
		pFacturaElectronica.setValorUnitario(rs.getString("ValorUnitario"));
		
		pFacturaElectronica.setImporte(rs.getString("Importe"));
		pFacturaElectronica.setImpuestoBase(rs.getString("ImpBase"));
		pFacturaElectronica.setImpuestoClave(rs.getString("ImpImpuesto"));
		pFacturaElectronica.setImpuestoTipoFactor(rs.getString("ImpTipoFactor"));
		pFacturaElectronica.setImpuestoTasaOCuota(rs.getString("ImpTasaOCuota"));
		pFacturaElectronica.setImpuestoImporte(rs.getString("ImpImporte"));
		
		pFacturaElectronica.setAdnNumeroPedimento(rs.getString("AdnNumeroPedimento"));
		
		try {
			pFacturaElectronica.setComentario(rs.getString("Comentario"));
		} catch (Exception e) {
			pFacturaElectronica.setComentario("");
		}
		
		return pFacturaElectronica;
	}
}
