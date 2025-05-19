package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.comun.Cliente;

public class ClientesRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		String a = "";		
		Cliente cliente = new Cliente();
		cliente.setIdCliente(rs.getLong("clave"));
		cliente.setNombre(rs.getString("Nombre"));
		cliente.setRol(rs.getString("Rol"));
		cliente.setSector(rs.getString("sector"));
		cliente.setIndustria(rs.getString("Industria"));
		cliente.setNivelIngreso(rs.getString("Nivel"));
		cliente.setEstado(rs.getString("Estado"));
		cliente.setPais(rs.getString("Pais"));
		cliente.setRazonSocial(rs.getString("RSocial"));
		cliente.setFechaActualizacion(rs.getTimestamp("FUActual"));
		cliente.setVendedor(rs.getString("ESAC"));
		cliente.setNomCobrador(rs.getString("COBRA"));
		cliente.setEjecutivoVenta(rs.getString("EV"));
		cliente.setObjectivoCrecimiento(rs.getString("objetivoCrecimiento"));
		cliente.setObjetivoCrecimientoFundamental(rs.getString("ObjetivoCrecimientoFundamental"));
		cliente.setCondicionesPago(rs.getString("CPago"));
		cliente.setComentarios(rs.getString("Comenta"));
		cliente.setMedioPago(rs.getString("MedioPago")); 
		if (rs.getString("NumCtaPago") != null) cliente.setNumeroDeCuenta(rs.getString("NumCtaPago"));
		if(rs.getString("LimiteCredito") != null && !rs.getString("LimiteCredito").equals("")){
			a = rs.getString("LimiteCredito").replace(",", "");
			a = a.replace("$", "");
			cliente.setLimiteCredito(Double.valueOf(a));
		}
		if(rs.getString("LineaCredito") != null && !rs.getString("LineaCredito").equals("")){
			a = rs.getString("LineaCredito").replace(",", "");
			a = a.replace("$", "");
			cliente.setLineaCredito(Double.valueOf(a));
		}
		cliente.setRfc(rs.getString("RFC"));
		cliente.setMonedaFactura(rs.getString("MonedaFactura"));
		cliente.setEmpresaFactura(rs.getString("Factura"));
		cliente.setPaisFiscal(rs.getString("RSPais"));
		cliente.setEstadoFiscal(rs.getString("RSEstado"));
		cliente.setCalleFiscal(rs.getString("RSCalle"));
		cliente.setDelegacionFiscal(rs.getString("RSDel"));
		cliente.setCodigoPostalFiscal(rs.getString("RSCP"));
		cliente.setFacturaPortal(rs.getBoolean("FacturaPortal"));
		cliente.setCorreoElectronico(rs.getString("MailFElectronica"));
		cliente.setComentaFacturacion(rs.getString("ComentaFacturacion"));
		cliente.setHabilitado(rs.getBoolean("Habilitado"));
		cliente.setNivelPrecio(rs.getString("NivelPrecio"));
		cliente.setIdEjecutivoVenta(rs.getInt("FK01_EV"));
		cliente.setComentariosCredito(rs.getString("ComentaCredito"));
		cliente.setEnviarEmail(rs.getBoolean("FElectronica"));
		if(rs.getString("imagen") != null && !rs.getString("imagen").equals("")){
			cliente.setImagen(rs.getString("imagen"));
		}
		cliente.setIdCobrador(rs.getLong("cobrador"));
		cliente.setIdEsacCli(rs.getLong("ClaveESAC"));
		cliente.setIdEVCli(rs.getLong("ClaveEV"));
		cliente.setImportancia(rs.getInt("Importancia"));
		cliente.setCobrador(rs.getString("COBRA"));
		cliente.setNombreCarteras(rs.getString("NombresCarteras"));
		
		if(rs.getLong("totalCarteras") > 0){
		    cliente.setNumeroDeCarteras(rs.getLong("totalCarteras"));
			cliente.setTieneCartera(true);
		}
		else{
			cliente.setNumeroDeCarteras(0L);
			cliente.setTieneCartera(false);
		}
			
			
		
		if(rs.getString("idCorporativo") != null && !rs.getString("idCorporativo").equals("")){
			cliente.setIdCorporativo(rs.getLong("idCorporativo"));
			cliente.setNombreCorporativo(rs.getString("nombreCorporativo"));
		}
		
		try {
			cliente.setRuta(rs.getString("ruta"));
		} catch (Exception e) {
		}
		
		try {
			cliente.setNum_visitas_ano(rs.getLong("total_Visitas_ano"));
		} catch (Exception e) {
		}
		
		cliente.setNombreEjecutivoVenta(rs.getString("ev_Nombre"));
		cliente.setNombreESAC(rs.getString("esac_Nombre"));
		cliente.setNombreCobrador(rs.getString("COBRADOR_Nombre"));
		try {
		cliente.setIdcartera(rs.getLong("idcartera"));
		} catch (Exception e) {
		}
		try {cliente.setTotalContactos(rs.getInt("TotalContactos"));} catch (Exception e) {}
		
		return cliente;
	}

}
