/**
 * 
 */
package com.proquifa.net.persistencia.consultas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.cobrosypagos.facturista.Factura;
import com.proquifa.net.modelo.despachos.Ruta;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author vromero
 *
 */
public class ConsultaEntregasRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Factura fac = new  Factura();
		Ruta ruta = new Ruta();
		Funcion f = new Funcion();
	
		ruta.setIdEntrega(rs.getString("idEntrega"));
		ruta.setIdRuta(rs.getString("idRuta"));
		ruta.setIdEvento(rs.getString("idDP"));
		ruta.setRutaMensajeria(f.convertirPalabrasClaves(rs.getString("Ruta")));
		ruta.setZonaMensajeria(rs.getString("ZonaMensajeria"));
		ruta.setResponsable(rs.getString("Responsable"));
		ruta.setFer(rs.getTimestamp("FER"));
		ruta.setFr(rs.getTimestamp("FR"));
		ruta.setFee(rs.getTimestamp("FEE"));
		ruta.setEstadoRuta(rs.getString("EstadoDP"));
		ruta.setConforme(rs.getString("Conformidad"));
		ruta.setTiempoRealizacion(rs.getString("Tiempo"));
		ruta.setNumeroPartidasRuta(rs.getInt("NumeroPartidas"));
		ruta.setFecha(rs.getTimestamp("FRENTREGA"));
		
		fac.setIdCliente(rs.getLong("idCliente"));
		fac.setNombre_Cliente(rs.getString("Nombre"));
		fac.setIdFactura(rs.getLong("idFactura"));
		fac.setRemOfact(rs.getInt("FacturaORemision"));
		fac.setNumeroFactura(rs.getLong("Factura"));
		fac.setFacturadoPor(rs.getString("FPor"));
		fac.setCpedido(rs.getString("Pedido"));
		fac.setMonedaPedido(rs.getString("MonedaPedido"));
		fac.setMontoTotalPedido(Math.rint((rs.getDouble("MontoPedido")) * 1e2) / 1e2);
		fac.setRutaRelacionada(ruta);
		fac.setImporte(rs.getFloat("MontoFactura"));
		fac.setMoneda(rs.getString("MonedaFactura"));
		fac.setNumeroPiezasFactura(rs.getInt("PiezasFactura"));
		fac.setNumeroPiezasPedidoRelacionado(rs.getInt("PiezasPedido"));
		fac.setNivelIngresocliente(rs.getString("NivelIngreso"));
		fac.setMontoFacturaDLS(Math.rint((rs.getDouble("TotalDLS")) * 1e2) / 1e2);
		
		return fac;
	}
}