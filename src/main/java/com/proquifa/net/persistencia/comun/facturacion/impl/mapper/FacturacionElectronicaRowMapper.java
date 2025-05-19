package com.proquifa.net.persistencia.comun.facturacion.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.Direccion;
import com.proquifa.net.modelo.comun.Empresa;
import com.proquifa.net.modelo.comun.facturaElectronica.FacturaElectronica;

public class FacturacionElectronicaRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException{
		FacturaElectronica facturaElectronica = new FacturaElectronica();
		facturaElectronica.setIdFactura(rs.getInt("PK_Factura"));
		facturaElectronica.setEsControlada(rs.getBoolean("Controlada"));		
		facturaElectronica.setVersion(rs.getString("Version"));
		facturaElectronica.setSerie(rs.getString("Serie"));
		facturaElectronica.setFolio(rs.getString("Folio"));
		facturaElectronica.setFecha(rs.getString("Fecha"));
		facturaElectronica.setFormaPago(rs.getString("FormaPago"));
		facturaElectronica.setNoCertificado(rs.getString("NoCertificado"));
		facturaElectronica.setCondicionesPago(rs.getString("CondicionesPago"));
		facturaElectronica.setCertificado(rs.getString("Certificado"));
		facturaElectronica.setSubtotal(rs.getString("Subtotal"));
		facturaElectronica.setMoneda(rs.getString("Moneda"));
		facturaElectronica.setTipoCambio(rs.getString("TipoCambio"));
		facturaElectronica.setTotal(rs.getString("Total"));
		facturaElectronica.setTipoComprobante(rs.getString("TipoComprobante"));
		facturaElectronica.setMetodoPago(rs.getString("MetodoPago"));
		facturaElectronica.setLugarExpedicion(rs.getString("LugarExpedicion"));
		facturaElectronica.setSello(rs.getString("Sello"));
		facturaElectronica.setCadenaOriginal(rs.getString("CadenaOriginal"));

		Empresa empresa = new Empresa();
		empresa.setIdEmpresa(rs.getInt("IdEmpresa"));
		empresa.setAlias(rs.getString("Alias"));
		empresa.setRazonSocial(rs.getString("RazonSocial"));
		empresa.setRegimenFiscal(rs.getString("RegimenFiscal"));
		empresa.setRfcEmpresa(rs.getString("RFC"));
		empresa.setCalle(rs.getString("Calle"));
		empresa.setColonia(rs.getString("Colonia"));
		empresa.setDelegacion(rs.getString("Delegacion"));
		empresa.setEstado(rs.getString("Estado"));
		empresa.setCiudad(rs.getString("Ciudad"));
		empresa.setPais(rs.getString("Pais"));
		empresa.setCp(rs.getString("CP"));
		facturaElectronica.setEmpresa(empresa);

		Cliente cliente = new Cliente();
		cliente.setIdCliente(rs.getLong("idCliente"));
		cliente.setRazonSocial(rs.getString("NombreFiscalP"));
		cliente.setRfc(rs.getString("RFCFiscalP"));
		cliente.setUsoCFDI(rs.getString("UsoCFDI"));
		Direccion dirEntrega = new Direccion();
		dirEntrega.setCalle(rs.getString("CalleEnt"));
		dirEntrega.setMunicipio(rs.getString("DelEnt"));		
		dirEntrega.setEstado(rs.getString("EdoEnt"));
		dirEntrega.setPais(rs.getString("PaisENt"));
		dirEntrega.setCodigoPostal(rs.getString("CPEnt"));
		cliente.setDireccion(dirEntrega);
		facturaElectronica.setCliente(cliente);
		

		facturaElectronica.setImpuestosTotalTraslados(rs.getString("ImpuestosTotalTraslados"));
		facturaElectronica.setImpuestosClave(rs.getString("ImpuestosClave"));
		facturaElectronica.setImpuestosTipoFactor(rs.getString("ImpuestosTipoFactor"));
		facturaElectronica.setImpuestosTasaOCuota(rs.getString("ImpuestosTasaOCuota"));
		facturaElectronica.setImpuestosImporte(rs.getString("ImpuestosImporte"));


		facturaElectronica.setSatVersion(rs.getString("SatVersion"));
		facturaElectronica.setSatUUID(rs.getString("SatUUID"));
		facturaElectronica.setSatFechaTimbrado(rs.getString("SatFechaTimbrado"));
		facturaElectronica.setSatRfcProvCertif(rs.getString("SatRfcProvCertif"));
		facturaElectronica.setSatSelloCFD(rs.getString("SatSelloCFD"));
		facturaElectronica.setSatNoCertificadoSAT(rs.getString("SatNoCertificadoSAT"));
		facturaElectronica.setSatSelloSAT(rs.getString("SatSelloSAT"));
		facturaElectronica.setCadenaOriginal(rs.getString("SatCadenaOriginal"));	

		facturaElectronica.setCodeQR(rs.getString("CodeQR"));
		facturaElectronica.setFolioPedidoCliente(rs.getString("FolioPedidoCliente"));
		facturaElectronica.setTotalTexto(rs.getString("TotalTexto"));

		facturaElectronica.setXml(rs.getString("XML"));
		facturaElectronica.setEstado(rs.getString("Estado"));
		
		facturaElectronica.setNotaFE(rs.getString("NotaFE"));
		
		try {
			facturaElectronica.setComentario(rs.getString("comentario"));
		}catch (Exception e){
			facturaElectronica.setComentario("");
		}

		try {
			facturaElectronica.setPedido(rs.getString("pedido"));
		}catch (Exception e){
			facturaElectronica.setPedido("");
		}
		
		try {
			facturaElectronica.setOrdenCompra(rs.getString("OrdenCompra"));
		}catch (Exception e){
			facturaElectronica.setOrdenCompra("");
		}

		facturaElectronica.setCpedido(rs.getString("CPedido"));

		return facturaElectronica;
	}
}
