package com.proquifa.net.persistencia.catalogos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.CarteraCliente;

import org.springframework.jdbc.core.RowMapper;

public class ClientesSinCarteraRowMapper implements RowMapper {
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		CarteraCliente c = new CarteraCliente () ;
		
		 c.setIdCliente(rs.getLong("idcliente"));
		 c.setCli_idEv(rs.getLong("idev"));
		 c.setCli_idEsac(rs.getLong("idesac"));
		 c.setCli_idCobrador(rs.getLong("idcobrador"));
		 c.setIdIndustria(rs.getLong("idIndustria"));
		 
		 c.setNombre(rs.getString("nombre"));
		 c.setIndustria(rs.getString("industria"));
		 c.setCli_nombreEsac(rs.getString("esac"));
		 c.setCli_nombreEv(rs.getString("ev"));
		 c.setCli_nombreCobrador(rs.getString("cobrador"));
		 c.setNivelIngreso(rs.getString("nivel"));
		 c.setRuta(rs.getString("Ruta"));
		 
//		 c.setCart_publicada(rs.getBoolean("publicada"));
		 
		 c.setCli_facturacionAct(rs.getDouble("montoVenta"));
		 c.setCli_facturacionAnt(rs.getDouble("montoVentaAnt"));
		 c.setCli_monto_ObjetivoDeseado(rs.getDouble("monto_D"));
		 c.setCli_monto_ObjetivoFundamental(rs.getDouble("monto_F"));
		 c.setCli_objetivoDeseado(rs.getDouble("objetivocrecimientoFundamental"));
		 c.setCli_objetivoFundamental(rs.getDouble("objetivoCrecimientoFundamental"));
		 if (rs.getLong("idcorporativo")!= 0L){
			c.setCli_corporativo(rs.getString("nombrecorporativo"));
		 	c.setIdCorporativo(rs.getLong("idcorporativo"));
		 }
		return c;
	}

}
