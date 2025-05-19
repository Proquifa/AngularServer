package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.comun.CarteraCliente;


public class CarterasClientesRowMapper implements RowMapper {
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		CarteraCliente c = new CarteraCliente () ;
		
		 c.setIdCliente(rs.getLong("idCliente"));
		 c.setIdCartera(rs.getLong("idCartera"));
		 
		 c.setArea(rs.getString("Area"));
		 
		 c.setCart_idEv(rs.getLong("cart_idev"));
		 c.setCart_idEVT(rs.getLong("cart_idevt"));
		 c.setCart_idEsac(rs.getLong("cart_idesac"));
		 c.setCart_idCobrador(rs.getLong("cart_idcobrador"));
		 c.setIdCreador(rs.getLong("cart_idElaboro"));
		 c.setCreador(rs.getString("creador"));
		 c.setIdIndustria(rs.getLong("idIndustria"));
		 c.setCli_idCobrador(rs.getLong("idcobrador"));
		 c.setCli_idEsac(rs.getLong("idesac"));
		 c.setCli_idEv(rs.getLong("idev"));
		 c.setCli_idEVT(rs.getLong("idevt"));
		 c.setCart_sistema(rs.getBoolean("sistema"));
		 c.setCart_corporativo(rs.getBoolean("Corporativo"));
		 c.setCart_internacional(rs.getBoolean("internacional"));
		 
		 c.setCart_nombre(rs.getString("cart_Nombre"));
		 c.setNombre(rs.getString("nombre"));
		 c.setIndustria(rs.getString("industria"));
//		 c.setNivelIngreso(rs.getString("nivel"));
		 c.setRuta(rs.getString("ruta"));
//		 c.setCli_categoria(rs.getString("categoria"));
		 c.setCli_nombreCobrador(rs.getString("cobrador"));
		 c.setCli_nombreEsac(rs.getString("esac"));
		 c.setCli_nombreEv(rs.getString("ev"));
		 c.setCli_nombreEVT(rs.getString("evt"));
		 c.setCart_nombreCobrador(rs.getString("cart_cobrador"));
		 c.setCart_nombreEsac(rs.getString("cart_esac"));
		 c.setCart_nombreEv(rs.getString("cart_ev"));
		 c.setCart_nombreEVT(rs.getString("cart_evt"));
//		 c.setCart_nombreElaboro(rs.getString("cart_usuario"));
		 
		 c.setCart_publicada(rs.getBoolean("publicada"));
		 
//	     c.setCli_proyeccionVenta(rs.getDouble("cli_proyeccionventa"));
//		 c.setCli_promedioFacturacion(rs.getDouble("cli_Promediofacturado"));
//		 c.setCli_factGlobal(rs.getDouble("facturacionGlobal"));
//		 c.setCli_debemos(rs.getDouble("debemos"));
//		 c.setCli_deben(rs.getDouble("debe"));
		 if (rs.getString("folio")!= null) {
			 c.setFolio(rs.getString("folio"));
		 }
		 if (rs.getLong("idcorporativo")!=0L){
			 c.setIdCorporativo(rs.getLong("idcorporativo"));
//			 c.setNombreCorporativo(rs.getString("nombreCorporativo"));
		 }

		return c;
	}
}
