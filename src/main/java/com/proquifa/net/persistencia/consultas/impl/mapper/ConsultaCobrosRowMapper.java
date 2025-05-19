/**
 * 
 */
package com.proquifa.net.persistencia.consultas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.cobrosypagos.Cobros;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author vromero
 *
 */
public class ConsultaCobrosRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		Cobros c = new Cobros();
		c.setFpor(rs.getString("FPor"));
		c.setIdCliente(rs.getLong("IDCLIENTE"));
		c.setNombreCliente(rs.getString("Nombre"));
		c.setCpedido(rs.getString("CPedido"));
		c.setFactura(rs.getLong("Factura"));
		c.setMedioFactura(rs.getString("Medio"));
		c.setCpago(rs.getString("CPago"));
		c.setContrarecibo(rs.getString("CONTRARECIBO"));
		c.setFechaFacturacion(rs.getTimestamp("FECHAFACTURACION"));
		c.setFechaRevision(rs.getTimestamp("FECHAREVISION"));
		c.setFechaEsperadaPago(rs.getTimestamp("FEP"));
		c.setDiasRestantesCobro(rs.getInt("DRC"));
		c.setMedioPago(rs.getString("MEDIOPAGO"));
		c.setMontoEsperadoCobro(Math.rint((rs.getDouble("MONTOESPERADO")) * 1e2) / 1e2);
		c.setMonedaEsperadaCobro(rs.getString("MONEDAESPERADO"));
		c.setMonedaRealCobro(rs.getString("MONEDAREAL"));
		c.setMontoRealCobro(Math.rint((rs.getDouble("MONTOREAL")) * 1e2) / 1e2);
		c.setFechaPago(rs.getDate("FECHAREAL"));
		c.setEstado(rs.getString("Estado"));
		c.setMontoDolares(Math.rint((rs.getDouble("MONTODLLS")) * 1e2) / 1e2);
		c.setFolioPC(rs.getString("FOLIOPC"));
		c.setNivelIngreso(rs.getString("NIVEL"));
		c.setPiezas(rs.getLong("PIEZAS"));
		c.setMoroso(rs.getString("MOROSO"));
		c.setPartidas(rs.getLong("PARTIDAS"));
		c.setReferencia(rs.getString("REFERENCIA"));
		c.setCobrador(rs.getString("userCobrador"));
		c.setEsac(rs.getString("Vendedor"));
		c.setTipoCambio(Math.rint((rs.getDouble("TCambio")) * 1e2) / 1e2);
		c.setOrdenCompra(rs.getString("OrdenCompra"));
		c.setResultadoEntrega(rs.getString("Entrega"));
		c.setUuid(rs.getString("UUID"));
		
		c.setIvaPesos(Math.rint((rs.getDouble("IVAPESOS")) * 1e2) / 1e2);
		c.setSubtotalPesos(Math.rint((rs.getDouble("SUBTOTALPESOS")) * 1e2) / 1e2);
		c.setTotalPesos(Math.rint((rs.getDouble("TOTALPESOS")) * 1e2) / 1e2);
		c.setIvaDlls(Math.rint((rs.getDouble("IVADLLS")) * 1e2) / 1e2);
		c.setSubTotalDlls(Math.rint((rs.getDouble("SUBTOTALDLLS")) * 1e2) / 1e2);
		c.setTotalDlls(Math.rint((rs.getDouble("TOTALDLLS")) * 1e2) / 1e2);
		if (rs.getString("NoCuenta").equals("ND")){
			c.setNoCuenta(	"ND");
		}
		else{
			c.setNoCuenta(rs.getString("NoCuenta").substring(rs.getString("NoCuenta").length() -4, rs.getString("NoCuenta").length()));
		}
			
		c.setBanco(rs.getString("Banco"));
		try{
			if(rs.getDate("FFin")== null){
				c.setFechaUEntrega(null);
			}else {
				c.setFechaUEntrega(rs.getDate("FFin"));
			}
		}catch(Exception e){}
		
		try {c.setFechaPEntrega(rs.getDate("FPEntrega"));} catch (Exception e) {}
		
		
		return c;
	}
}