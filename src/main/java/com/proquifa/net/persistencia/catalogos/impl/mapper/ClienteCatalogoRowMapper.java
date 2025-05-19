package com.proquifa.net.persistencia.catalogos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.Cliente;

import org.springframework.jdbc.core.RowMapper;


public class ClienteCatalogoRowMapper implements RowMapper {

	private Cliente cliente;
	
	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {

			String a = "";
			String usoCFDI = "";
			String metodoPago = "";
			
			this.cliente = new Cliente();
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
			cliente.setEjecutivoVenta(rs.getString("EV"));
			cliente.setObjectivoCrecimiento(rs.getString("objetivoCrecimiento"));
			cliente.setObjetivoCrecimientoFundamental(rs.getString("ObjetivoCrecimientoFundamental"));
			cliente.setCondicionesPago(rs.getString("CPago"));
			cliente.setComentarios(rs.getString("Comenta"));
				a = rs.getString("LimiteCredito").replace(",", "");
				a = a.replace("$", "");
			cliente.setLimiteCredito(Double.valueOf(a));
				a = rs.getString("LineaCredito").replace(",", "");
				a = a.replace("$", "");
			cliente.setLineaCredito(Double.valueOf(a));
			cliente.setRfc(rs.getString("RFC"));
			cliente.setMonedaFactura(rs.getString("MonedaFactura"));
			cliente.setEmpresaFactura(rs.getString("Factura"));
			cliente.setPaisFiscal(rs.getString("RSPais"));
			cliente.setEstadoFiscal(rs.getString("RSEstado"));
			cliente.setCalleFiscal(rs.getString("RSCalle"));
			cliente.setDelegacionFiscal(rs.getString("RSDel"));
			cliente.setCodigoPostalFiscal(rs.getString("RSCP"));
			cliente.setFacturaPortal(rs.getBoolean("FacturaPortal"));
			cliente.setEntregaYRevision(rs.getBoolean("EntregayRevision"));
			cliente.setCorreoElectronico(rs.getString("MailFElectronica"));
			cliente.setComentaFacturacion(rs.getString("ComentaFacturacion"));
			cliente.setHabilitado(rs.getBoolean("Habilitado"));
			cliente.setNivelPrecio(rs.getString("NivelPrecio"));
			cliente.setIdEjecutivoVenta(rs.getInt("FK01_EV"));
			cliente.setComentariosCredito(rs.getString("ComentaCredito"));
			cliente.setEnviarEmail(rs.getBoolean("FElectronica"));
			cliente.setImagen(rs.getString("imagen"));
			cliente.setIdCobrador(rs.getLong("cobrador"));
			cliente.setImportancia(rs.getInt("Importancia"));
			cliente.setNombreCarteras(rs.getString("NombresCarteras"));
			cliente.setIdEsacCli(rs.getLong("ClaveESAC"));
			cliente.setIdEVCli(rs.getLong("ClaveEV"));
			cliente.setNomCobrador(rs.getString("Nombre_cobrador"));
			cliente.setUsoCFDI(rs.getString("UsoCFDI"));
			cliente.setMetodoDePago(rs.getString("MetodoPago")); 
			
			if(rs.getLong("totalCarteras") > 0){
			    cliente.setNumeroDeCarteras(rs.getLong("totalCarteras"));
				cliente.setTieneCartera(true);
			}
			else{
				cliente.setNumeroDeCarteras(0L);
				cliente.setTieneCartera(false);
			}
			try {cliente.setColonia(rs.getString("Colonia"));} catch (Exception e) {}
			try {
				if (rs.getString("idCorporativo") != null){
				cliente.setNombreCorporativo(rs.getString("NombreCorporativo"));
				cliente.setIdCorporativo(rs.getLong("idCorporativo"));
				}
			} catch (Exception e) {
			}
			try {
				cliente.setRuta(rs.getString("ruta"));
			} catch (Exception e) {
			}
			
			try {
				cliente.setAceptaParciales(rs.getBoolean("Parciales"));
			} catch (Exception e) {
			}
			
			try {
				cliente.setDestino(rs.getString("Destino"));
			} catch (Exception e) {
			}
			
			if(rs.getLong("totalContratos") > 0){
			    cliente.setNumeroDeContratos(rs.getLong("totalContratos"));
				cliente.setTieneContrato(true);
			}
			else{
				cliente.setNumeroDeContratos(0L);
				cliente.setTieneContrato(false);
			}
			
			
		return cliente;
	}
	
	/**
	 * @return the cliente
	 */
	public Cliente getCliente() {
		return cliente;
	}
	
}
