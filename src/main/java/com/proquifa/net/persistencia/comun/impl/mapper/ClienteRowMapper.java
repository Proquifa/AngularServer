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
 * @author ernestogonzalezlozada
 *
 */
public class ClienteRowMapper implements RowMapper {

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
		cliente.setVendedor(rs.getString("vendedor"));
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
		try {
			cliente.setUsoCFDI(rs.getString("FK04_UsoCFDI"));
		} catch (Exception e) {
		}
		try {
			cliente.setMetodoDePago(rs.getString("FK05_MetodoPago"));
		} catch (Exception e) {
		}
		try {
			cliente.setEjecutivoVenta(rs.getString("NOM_EV"));
		} catch (Exception e) {
		}
		cliente.setComentaFacturacion(rs.getString("ComentaFacturacion"));
		cliente.setIdCobrador(rs.getLong("Cobrador"));
		if(cliente.getFechaRegistro() == null){
			cliente.setFolio("CT-010212-" + nf.format(cliente.getIdCliente()));
		}else{
			cliente.setFolio("CT-" + formatter.format(cliente.getFechaRegistro()) + "-" + nf.format(cliente.getIdCliente()));
		}
		cliente.setRfc(rs.getString("CURP"));
		cliente.setCorreoElectronico(rs.getString("MailFElectronica"));
		cliente.setNumeroDeCuenta(rs.getString("NumCtaPago"));
		try {
			cliente.setImportancia(rs.getInt("IMPORTANCIA_NUM"));
		} catch (Exception e) {
		}
		
		try {
			cliente.setDificultad(rs.getInt("Dificultad"));
		} catch (Exception e) {
		}
		try {
			cliente.setMantenimiento(rs.getInt("Mantenimiento"));
		} catch (Exception e) {
		}
		
		try {
			cliente.setFacturaPortal(rs.getBoolean("FacturaPortal"));
		} catch (Exception e) {			
		}
		try {
			cliente.setNivelIngreso(rs.getString("NIVEL"));
		} catch (Exception e) {			
		}
		try {
			if(rs.getString("idCorporativo") != null && !rs.getString("idCorporativo").equals("")){
			cliente.setIdCorporativo(rs.getLong("idCorporativo"));
			cliente.setNombreCorporativo(rs.getString("nombreCorporativo"));
			}
		} catch (Exception e) {	
		}
		cliente.setObjectivoCrecimiento(rs.getString("ObjetivoCrecimiento"));
		if(cliente.getObjectivoCrecimiento() == null)
			cliente.setObjectivoCrecimiento("0");
		cliente.setObjetivoCrecimientoFundamental(rs.getString("ObjetivoCrecimientoFundamental"));
		if(cliente.getObjetivoCrecimientoFundamental() == null)
			cliente.setObjetivoCrecimientoFundamental("0");
		return cliente;
	}
}