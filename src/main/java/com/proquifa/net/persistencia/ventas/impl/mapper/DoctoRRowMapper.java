/**
 * 
 */
package com.proquifa.net.persistencia.ventas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.Contacto;
import com.proquifa.net.modelo.ventas.DoctoR;


/**
 * @author yosimar.mendez
 *
 */
public class DoctoRRowMapper  implements RowMapper{

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		DoctoR docto = new DoctoR();
		docto.setRpor(rs.getString("RPor"));
		
		docto.setContacto(new Contacto(rs.getLong("idContacto")));
		docto.getContacto().setNombre(rs.getString("Contacto"));
		docto.getContacto().setEMail(rs.getString("Email"));
		docto.getContacto().setFax(rs.getString("fax"));
		docto.getContacto().setTelefono(rs.getString("tel1"));
		
		docto.setCliente(new Cliente(rs.getLong("Empresa")));
		docto.getCliente().setNombre(rs.getString("Cliente"));
		docto.getCliente().setMonedaFactura(rs.getString("IMoneda"));
		docto.getCliente().setZona(rs.getString("Zona"));
		docto.getCliente().setCondicionesPago(rs.getString("CPago"));
		docto.getCliente().setEmpresaFactura(rs.getString("Factura"));
		docto.getCliente().setParciales(rs.getString("Parciales"));
		docto.getCliente().setVendedor(rs.getString("RPor"));
		docto.getCliente().setCalle(rs.getString("calle"));
		
		docto.getCliente().setNombreESAC(rs.getString("Empleado"));
		return docto;
	}

}
