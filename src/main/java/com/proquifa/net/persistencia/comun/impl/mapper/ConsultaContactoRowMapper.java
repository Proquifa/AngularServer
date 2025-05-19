/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.comun.Cliente;

/**
 * @author bamagana
 *
 */
public class ConsultaContactoRowMapper implements RowMapper{

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Format formatter = new SimpleDateFormat("MMddyy"); 
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumIntegerDigits(4);
		nf.setGroupingUsed(false);
		Cliente cliente = new Cliente();
		
		cliente.setIdCliente(rs.getLong("Clave"));
		cliente.setNombre(rs.getString("Nombre"));
		cliente.setVendedor(rs.getString("Vendedor"));
		cliente.setHabilitado(rs.getBoolean("Habilitado"));
		cliente.setFechaActualizacion(rs.getDate("FUActual"));
		cliente.setTipo(rs.getString("Tipo"));
		cliente.setRuta(rs.getString("Ruta"));
		cliente.setMapa(rs.getString("Mapa"));
		cliente.setPais(rs.getString("Pais"));
		cliente.setEstado(rs.getString("Estado"));
		cliente.setCalle(rs.getString("Calle"));
		cliente.setDelegacion(rs.getString("Delegacion"));
		cliente.setCodigoPostal(rs.getString("CP"));
		cliente.setZona(rs.getString("Zona"));
		cliente.setRazonSocial(rs.getString("RSocial"));
		cliente.setPaisFiscal(rs.getString("RSPais"));
		cliente.setEstadoFiscal(rs.getString("RSEstado"));
		cliente.setCalleFiscal(rs.getString("RSCalle"));
		cliente.setDelegacionFiscal(rs.getString("RSDel"));
		cliente.setEmpresaFactura(rs.getString("Factura"));
		cliente.setCodigoPostalFiscal(rs.getString("RSCP"));
		cliente.setAceptaParciales(rs.getBoolean("Parciales"));
		cliente.setCondicionesPago(rs.getString("CPago"));
		cliente.setMedioPago(rs.getString("MedioPago"));
		cliente.setEntregaYRevision(rs.getBoolean("EntregayRevision"));
		cliente.setMonedaFactura(rs.getString("MonedaFactura"));
		cliente.setFechaRegistro(rs.getDate("FechaRegistro"));
		cliente.setComentaFacturacion(rs.getString("ComentaFacturacion"));
		cliente.setNumContactos(rs.getLong("NumContactos"));
		cliente.setCorreoElectronico(rs.getString("MailFElectronica"));
		
		String sector="",rol="";
		sector=rs.getString("Sector");
		rol=rs.getString("Rol");
		if(sector==null || sector.equals("") || sector.equals("--NINGUNO--")){cliente.setSector("ND");}else{cliente.setSector(sector);}
		if(rol==null || rol.equals("") || rol.equals("--NINGUNO--")){cliente.setRol("ND");}else{cliente.setRol(rol);}
		
		if(cliente.getFechaRegistro() == null){
			cliente.setFolio("CT-010212-" + nf.format(cliente.getIdCliente()));
		}else{
			cliente.setFolio("CT-" + formatter.format(cliente.getFechaRegistro()) + "-" + nf.format(cliente.getIdCliente()));
		}
		cliente.setRfc(rs.getString("CURP"));
		cliente.setCorreoElectronico(rs.getString("MailFElectronica"));
		cliente.setNumeroDeCuenta(rs.getString("NumCtaPago"));
		try {
			cliente.setIndustria(rs.getString("industria"));
		} catch (Exception e) {
		}
		
		return cliente;
	}
}