package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.Corporativo;

import org.springframework.jdbc.core.RowMapper;

public class CorporativoRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		Corporativo corporativo = new Corporativo();
		
		corporativo.setIdCorporativo(rs.getLong("PK_Corporativo"));
		corporativo.setNombre(rs.getString("Corporativo"));
		corporativo.setFua(rs.getTimestamp("fua"));
		corporativo.setAreaCorporativo(rs.getString("Area")); 
		
		Cliente cliente = new Cliente();
		cliente.setIdCliente(rs.getLong("Clave"));
		cliente.setNombre(rs.getString("Nombre"));
		cliente.setVendedor(rs.getString("Vendedor"));
		cliente.setEjecutivoVenta(rs.getString("NombreEV"));
		cliente.setIdEjecutivoVenta(rs.getInt("FK01_EV"));
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
		cliente.setIdCobrador(rs.getLong("Cobrador"));
		cliente.setCobrador(rs.getString("UsuarioCobrador"));
		
		cliente.setRfc(rs.getString("CURP"));
		cliente.setCorreoElectronico(rs.getString("MailFElectronica"));
		cliente.setNumeroDeCuenta(rs.getString("NumCtaPago"));
		
		cliente.setRol("");
		if (rs.getString("Rol") != null){
			cliente.setRol(rs.getString("Rol"));
		}
		cliente.setSector("");
		if (rs.getString("sector") != null){
			cliente.setSector(rs.getString("sector"));
		}
		cliente.setIndustria("");
		if (rs.getString("Industria") != null){
			cliente.setIndustria(rs.getString("Industria"));
		}
		
		cliente.setNivelIngreso(rs.getString("Nivel"));
		cliente.setNivelIngresos(rs.getString("Nivel"));
		
		cliente.setImportancia(rs.getInt("importancia"));
		
		corporativo.setCliente(cliente);
		
		return corporativo;
	}

}
