package com.proquifa.net.persistencia.comun.facturacion.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.proquifa.net.modelo.comun.facturaElectronica.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.catalogos.cobrosypagos.facturista.Factura;
import com.proquifa.net.modelo.catalogos.cobrosypagos.facturista.PartidaFactura;
import com.proquifa.net.modelo.comun.ReferenciaBancaria;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.comun.facturacion.FacturacionElectronicaDAO;
import com.proquifa.net.persistencia.comun.facturacion.impl.mapper.FacturacionElectronicaRowMapper;
import com.proquifa.net.persistencia.comun.facturacion.impl.mapper.PFacturacionElectronicaRowMapper;

@Repository
public class FacturacionElectronicaDAOImpl extends DataBaseDAO implements FacturacionElectronicaDAO {

	final Logger log = LoggerFactory.getLogger(FacturacionElectronicaDAOImpl.class);

	@Override
	public FacturaElectronica insertarFactura(FacturaElectronica f){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Version", f.getVersion());
			map.put("Serie", f.getSerie());
			map.put("Folio", f.getFolio());
			map.put("Fecha", f.getFecha());
			map.put("FormaPago", f.getFormaPago());
			map.put("NoCertificado", f.getNoCertificado());
			map.put("Certificado", f.getCertificado());
			map.put("CondicionesPago", f.getCondicionesPago());
			map.put("Subtotal", f.getSubtotal());
			map.put("Moneda", f.getMoneda());
			map.put("Total", f.getTotal());
			map.put("TipoComprobante", f.getTipoComprobante());
			map.put("MetodoPago", f.getMetodoPago());
			map.put("LugarExpedicion", f.getLugarExpedicion());
			map.put("Sello", f.getSello());
			map.put("CadenaOriginal", f.getCadenaOriginal());
			map.put("EmpresaEmisor", f.getEmpresa().getIdEmpresa());
			map.put("ImpuestosTotalTraslados", f.getImpuestosTotalTraslados() != null ? f.getImpuestosTotalTraslados() : 0);
			map.put("ImpuestosClave", f.getImpuestosClave() != null ? f.getImpuestosClave() : 0);
			map.put("ImpuestosTipoFactor", f.getImpuestosTipoFactor() != null ? f.getImpuestosTipoFactor() : 0);
			map.put("ImpuestosTasaOCuota", f.getImpuestosTasaOCuota() != null ? f.getImpuestosTasaOCuota() : 0);
			map.put("ImpuestosImporte", f.getImpuestosImporte() != null ? f.getImpuestosImporte() : 0);
			map.put("FolioPedidoCliente", f.getFolioPedidoCliente());
			map.put("TotalTexto", f.getTotalTexto());
			map.put("Cliente", f.getCliente().getIdCliente());
			map.put("XML", f.getXml());
			map.put("TipoCambio", f.getTipoCambio());
			map.put("UsoCFDI", f.getCliente().getUsoCFDI());
			map.put("Comentario", f.getComentario());
			map.put("RRazonSocial", f.getCliente().getRazonSocial());
			map.put("RRFC", f.getCliente().getRfc());

			String query = "INSERT INTO FacturaElectronica(  "
					+ "Version, Serie, Folio, Fecha, FormaPago, NoCertificado, Certificado, "
					+ "CondicionesPago, Subtotal, Moneda, Total, TipoComprobante, MetodoPago, "
					+ "LugarExpedicion, Sello, CadenaOriginal, EmpresaEmisor, "
					+ "ImpuestosTotalTraslados, ImpuestosClave, ImpuestosTipoFactor, "
					+ "ImpuestosTasaOCuota, ImpuestosImporte, FolioPedidoCliente, "
					+ "TotalTexto, Cliente, XML, TipoCambio, UsoCFDI, Comentario, RRazonSocial, RRFC) "
					+ "VALUES( "
					+ ":Version, :Serie, :Folio, :Fecha, :FormaPago, :NoCertificado, "
					+ ":Certificado, :CondicionesPago, :Subtotal, :Moneda, :Total, "
					+ ":TipoComprobante, :MetodoPago, :LugarExpedicion, :Sello, "
					+ ":CadenaOriginal, :EmpresaEmisor, "
					+ ":ImpuestosTotalTraslados, :ImpuestosClave, :ImpuestosTipoFactor, "
					+ ":ImpuestosTasaOCuota, :ImpuestosImporte, :FolioPedidoCliente, "
					+ ":TotalTexto, :Cliente, :XML, :TipoCambio, :UsoCFDI, :Comentario, :RRazonSocial, :RRFC)";

			String query2 = "INSERT INTO FacturaElectronica( "
					+ "Version, Serie, Folio, Fecha, FormaPago, NoCertificado, Certificado, "
					+ "CondicionesPago, Subtotal, Moneda, Total, TipoComprobante, MetodoPago, "
					+ "LugarExpedicion, Sello, CadenaOriginal, EmpresaEmisor, "
					+ "ImpuestosTotalTraslados, ImpuestosClave, ImpuestosTipoFactor, "
					+ "ImpuestosTasaOCuota, ImpuestosImporte, FolioPedidoCliente, "
					+ "TotalTexto, Cliente, XML, TipoCambio, UsoCFDI, Comentario, RRazonSocial, RRFC) "
					+ "VALUES('"+f.getVersion()+"','"+f.getSerie()+"','"+f.getFolio()+"','"+f.getFecha()+"','"+f.getFormaPago()+"','"+f.getNoCertificado()
					+"','"+f.getCertificado()+"','"+f.getCondicionesPago()+"','"+f.getSubtotal()+"','"+f.getMoneda()+"','"+f.getTotal()+"','"+f.getTipoComprobante()+"','"+f.getMetodoPago()
					+"','"+f.getLugarExpedicion()+"','"+f.getSello()+"','"+f.getCadenaOriginal()+"','"+f.getEmpresa().getIdEmpresa()+"','"+f.getImpuestosTotalTraslados()
					+"','"+f.getImpuestosClave()+"','"+f.getImpuestosTipoFactor()+"','"+f.getImpuestosTasaOCuota()+"','"+f.getImpuestosImporte()+"','"+f.getFolioPedidoCliente()
					+"','"+f.getTotalTexto()+"','"+f.getCliente().getIdCliente()+"','"+f.getXml()+"','"+f.getTipoCambio()+"','"+f.getCliente().getUsoCFDI()+"','"+f.getComentario()
					+"','"+f.getCliente().getRazonSocial()+"','"+f.getCliente().getRfc()+"')";

			log.info(query2);
			super.jdbcTemplate.update(query,map);

			int r = super.queryForInt("SELECT IDENT_CURRENT ('FacturaElectronica')");
			f.setIdFactura(r);

		} catch (Exception e) {
			log.info("Error: "+ e.getMessage());
			return null;
		}

		return f;
	}

	@Override
	public Boolean updateErrorFactura(int idFactura) {
		try {
			String query = "UPDATE facturaElectronica set Folio = ISNULL(Folio,'') + '_Error' WHERE PK_Factura = :id";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", idFactura);
			super.jdbcTemplate.update(query,map);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public int insertarTablaFactura(Factura f){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Factura", f.getNumeroFactura());
		map.put("Fecha", f.getFecha());
		map.put("CPago", f.getCondicionesPago());
		map.put("Cliente", f.getIdCliente());
		map.put("Moneda", f.getMoneda());
		map.put("Importe", f.getImporte());
		map.put("IVA", f.getIva());
		map.put("Estado", f.getEstado());
		map.put("FPor", f.getFacturadoPor());
		map.put("Cotiza", "");
		map.put("Pedido", f.getPedido());
		map.put("TCambio", f.getTipoCambio());
		map.put("CPedido", f.getCpedido());
		map.put("ImprimirTC", f.getImprimirTipoCambio());
		map.put("DeRemision", f.getRemision());
		map.put("OrdenCompra", f.getOrdenCompra());
		map.put("Tipo", f.getTipo());
		map.put("Serie", f.getSerie());
		map.put("Medio", f.getMedio());
		map.put("DeSistema", f.getCpedido() == null ? 0:1);
		map.put("UUID", f.getUuid());
		map.put("PDFGenerado", 0);

		String query = "INSERT INTO Facturas( Factura, Fecha, CPago, Cliente, Moneda, "
				+ " Importe, IVA, Estado, FPor, Cotiza, Pedido, TCambio, CPedido, ImprimirTC, "
				+ " DeRemision, OrdenCompra, Tipo, Serie, Medio, DeSistema, UUID, PDFGenerado) "
				+ " values (:Factura, :Fecha, :CPago, :Cliente, :Moneda, :Importe, :IVA, "
				+ " :Estado, :FPor, :Cotiza, :Pedido, :TCambio, :CPedido, :ImprimirTC, "
				+ " :DeRemision, :OrdenCompra, :Tipo, :Serie, :Medio, :DeSistema, :UUID, "
				+ " :PDFGenerado)";
		super.jdbcTemplate.update(query,map);

		int id = super.queryForInt("SELECT MAX(idFactura) FROM Facturas WHERE Cliente = :Cliente GROUP BY Cliente", map);

		return id;
	}

	@Override
	public Boolean relacionarFactura_FElectronica(int idFactura, int idFElectronica){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("FK01_Factura",idFactura);
		map.put("FK02_FacturaElectronica",idFElectronica);

		String query = "INSERT INTO Factura_FElectronica (FK01_Factura, FK02_FacturaElectronica) "
				+ " values (:FK01_Factura, :FK02_FacturaElectronica)";
		super.jdbcTemplate.update(query,map);

		return true;
	}

	@Override
	public Boolean insertarTablaPFacturas(PartidaFactura pf){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Factura", pf.getFactura());
		map.put("Cant", pf.getCantidad());
		map.put("Importe", pf.getImporte());
		map.put("Part", pf.getPartidaFactura());
		map.put("FPor", pf.getFpor());
		map.put("idPCompra", pf.getIdPCompra());
		map.put("iva", pf.getSubTotal());
		map.put("FK01_Factura", pf.getIdFactura());
		map.put("ppedido", pf.getPpedido());
		map.put("cpedido", pf.getCpedido());

		String query = "INSERT INTO PFacturas( Factura, Cant, Importe, Part, FPor, PPedido, CPedido, "
				+ " idPCompra, iva, FK01_Factura) "
				+ " values (:Factura, :Cant, :Importe, :Part, :FPor, :ppedido, :cpedido, :idPCompra, :iva, :FK01_Factura)";


		String query2 = "INSERT INTO PFacturas( Factura, Cant, Importe, Part, FPor, PPedido, CPedido, idPCompra, iva, FK01_Factura) "
				+ " values ('"+pf.getFactura()+"', '"+pf.getCantidad()+"', '"+pf.getImporte()+"', '"+pf.getPartidaFactura()+"', '"+pf.getFpor()+"', '"+pf.getPpedido()+"', '"+pf.getCpedido()+"', '"+pf.getIdPCompra()+"', '"+pf.getSubTotal()+"', '"+pf.getIdFactura()+"')";

		super.jdbcTemplate.update(query,map);

		return true;
	}

	@Override
	public PFacturaElectronica insertarPFactura(PFacturaElectronica f){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ClaveProdServ", f.getClaveProdServ());
			map.put("NoIdentificacion", f.getNoIdentificacion() != null? f.getNoIdentificacion():"");
			map.put("Cantidad", f.getCantidad());
			map.put("ClaveUnidad", f.getClaveUnidad());
			map.put("Unidad", f.getUnidad() != null ? f.getUnidad():"");
			map.put("Descripcion", f.getDescripcion());
			map.put("ValorUnitario", f.getValorUnitario());
			map.put("Importe", f.getImporte());
			map.put("ImpBase", f.getImpuestoBase() != null ? f.getImpuestoBase(): "");
			map.put("ImpImpuesto", f.getImpuestoClave() != null ? f.getImpuestoClave(): "");
			map.put("ImpTipoFactor", f.getImpuestoTipoFactor() != null ? f.getImpuestoTipoFactor(): "");
			map.put("ImpTasaOCuota", f.getImpuestoTasaOCuota() != null ? f.getImpuestoTasaOCuota(): "");
			map.put("ImpImporte", f.getImpuestoImporte() != null ? f.getImpuestoImporte(): "");
			map.put("AdnNumeroPedimento", f.getAdnNumeroPedimento());
			map.put("FK01_FacturaElectronica", f.getFactura());
			map.put("Comentario", f.getComentario());


			String query = "INSERT INTO PFacturaElectronica(ClaveProdServ, "
					+ " NoIdentificacion, Cantidad, ClaveUnidad, Unidad, Descripcion, "
					+ " ValorUnitario, Importe, ImpBase, ImpImpuesto, ImpTipoFactor, "
					+ " ImpTasaOCuota, ImpImporte, AdnNumeroPedimento, FK01_FacturaElectronica, Comentario) "
					+ " VALUES ( "
					+ " :ClaveProdServ, :NoIdentificacion, :Cantidad, :ClaveUnidad, :Unidad, "
					+ " :Descripcion, :ValorUnitario, :Importe, :ImpBase, :ImpImpuesto, :ImpTipoFactor, "
					+ " :ImpTasaOCuota, :ImpImporte, :AdnNumeroPedimento, :FK01_FacturaElectronica, :Comentario )";


			String query2 = "INSERT INTO PFacturaElectronica(ClaveProdServ, "
					+ " NoIdentificacion, Cantidad, ClaveUnidad, Unidad, Descripcion, "
					+ " ValorUnitario, Importe, ImpBase, ImpImpuesto, ImpTipoFactor, "
					+ " ImpTasaOCuota, ImpImporte, AdnNumeroPedimento, FK01_FacturaElectronica, Comentario) "
					+ "VALUES('"+f.getClaveProdServ()+"','"+f.getNoIdentificacion()+"','"+f.getCantidad()+"','"+f.getClaveUnidad()+"','"+f.getUnidad()+"','"+f.getDescripcion()
					+"','"+f.getValorUnitario()+"','"+f.getImporte()+"','"+f.getImpuestoBase()+"','"+f.getImpuestoClave()+"','"+f.getImpuestoTipoFactor()+"','"+f.getImpuestoTasaOCuota()+"','"+f.getImpuestoImporte()
					+"','"+f.getAdnNumeroPedimento()+"','"+f.getFactura()+"','"+f.getComentario()+"')";

			log.info(query2);
			super.jdbcTemplate.update(query,map);

		} catch (Exception e) {
			log.info("Error: "+ e.getMessage());
			return null;
		}

		return f;
	}

	@Override
	public CfdiRelacionado insertarCfdiRelacionado(CfdiRelacionado cfdiRelacionado){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tipoRelacion", cfdiRelacionado.getTipoRelacion());
			map.put("uuid", cfdiRelacionado.getUuid());
			map.put("idFacturaElectronica", cfdiRelacionado.getIdFacturaElectronica());

			String query = "INSERT INTO CfdiRelacionado (TipoRelacion, UUID, FK01_FacturaElectronica) VALUES (:tipoRelacion, :uuid, :idFacturaElectronica)";

			super.jdbcTemplate.update(query,map);

			int r = super.queryForInt("SELECT IDENT_CURRENT ('CfdiRelacionado')");
			cfdiRelacionado.setIdCfdiRelacionado( new Long(r));

			return cfdiRelacionado;
		} catch (Exception e) {
			log.info("Error: "+ e.getMessage());
			return null;
		}
	}

	@Override
	public ComplementoPago insertarComplementoPago(ComplementoPago cp){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("fechaPago", cp.getFechaPago());
			map.put("formaDePagoP", cp.getFormaDePagoP());
			map.put("monedaP", cp.getMonedaP());
			map.put("tipoCambioP", cp.getTipoCambioP());
			map.put("monto", cp.getMonto());
			map.put("ctaOrdenante", cp.getCtaOrdenante());
			map.put("nomBancoOrdExt", cp.getNomBancoOrdExt());
			map.put("rfcEmisorCtaOrd", cp.getRfcEmisorCtaOrd());
			map.put("idFacturaElectronica", cp.getIdFactura());
			map.put("idDeposito", cp.getIdDeposito());
			map.put("numOperacion", cp.getNumOperacion());

			String query = "INSERT INTO ComplementoPago (FechaPago, FormaDePagoP, MonedaP, TipoCambioP, Monto, CtaOrdenante, NomBancoOrdExt, RfcEmisorCtaOrd, FK01_FacturaElectronica, FK02_Deposito, NumOperacion) "
					+ " VALUES (:fechaPago, :formaDePagoP, :monedaP, :tipoCambioP, :monto, :ctaOrdenante, :nomBancoOrdExt, :rfcEmisorCtaOrd, :idFacturaElectronica, :idDeposito, :numOperacion)";
			super.jdbcTemplate.update(query,map);

			int r = super.queryForInt("SELECT IDENT_CURRENT ('ComplementoPago')");
			log.info( "INSERT INTO ComplementoPago (FechaPago, FormaDePagoP, MonedaP, TipoCambioP, Monto, CtaOrdenante, NomBancoOrdExt, RfcEmisorCtaOrd, FK01_FacturaElectronica, FK02_Deposito, NumOperacion) "
					+ " VALUES (:fechaPago, :formaDePagoP, :monedaP, :tipoCambioP, :monto, :ctaOrdenante, :nomBancoOrdExt, :rfcEmisorCtaOrd, :idFacturaElectronica, :idDeposito, :numOperacion)");
			cp.setIdComplementoPago( new Long(r));

			return cp;
		} catch (Exception e) {
			log.info("Error: "+ e.getMessage());
			return null;
		}
	}

	@Override
	public CPDocRelacionado insertarCPDocRelacionado(CPDocRelacionado cpdr){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idDocumento", cpdr.getIdDocumento());
			map.put("serie", cpdr.getSerie());
			map.put("folio", cpdr.getFolio());
			map.put("monedaDR", cpdr.getMonedaDR());
			map.put("metodoDePagoDR", cpdr.getMetodoPagoDR());
			map.put("numParcialidad", cpdr.getNumParcialidad());
			map.put("impSaldoAnt", cpdr.getImpSaldoAnt());
			map.put("impPagado", cpdr.getImpPagado());
			map.put("impSaldoInsoluto", cpdr.getImpSaldoInsoluto());
			map.put("tipoCambioDR",cpdr.getTipoCambioDR());
			map.put("idComplementoPago", cpdr.getIdComplementoPago());
			map.put("tipoCambioP",cpdr.getTipoCambioP());

			String query = "INSERT INTO CP_DocRelacionados (IdDocumento, Serie, Folio, MonedaDR, MetodoDePagoDR, NumParcialidad, ImpSaldoAnt, ImpPagado, ImpSaldoInsoluto, FK03_Deposito, TipoCambioDR,tipoCambioP) "
					+ " VALUES (:idDocumento, :serie, :folio, :monedaDR, :metodoDePagoDR, :numParcialidad, :impSaldoAnt, :impPagado, :impSaldoInsoluto, :idComplementoPago, :tipoCambioDR,:tipoCambioP)";

			super.jdbcTemplate.update(query,map);

			int r = super.queryForInt("SELECT IDENT_CURRENT ('CP_DocRelacionados')");
			cpdr.setIdCPDocRelacionado( new Long(r));

			return cpdr;
		} catch (Exception e) {
			log.info("Error: "+ e.getMessage());
			return null;
		}
	}

	@Override
	public FacturaElectronica modificarFactura(FacturaElectronica f){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idFactura", f.getIdFactura());
			map.put("Version", f.getVersion());
			map.put("Serie", f.getSerie());
			map.put("Folio", f.getFolio());
			map.put("Fecha", f.getFecha());
			map.put("FormaPago", f.getFormaPago());
			map.put("NoCertificado", f.getNoCertificado());
			map.put("CondicionesPago", f.getCondicionesPago());
			map.put("Subtotal", f.getSubtotal());
			map.put("Moneda", f.getMoneda());
			map.put("Total", f.getTotal());
			map.put("TipoComprobante", f.getTipoComprobante());
			map.put("MetodoPago", f.getMetodoPago());
			map.put("LugarExpedicion", f.getLugarExpedicion());
			map.put("Sello", f.getSello());
			map.put("CadenaOriginal", f.getCadenaOriginal());
			map.put("EmpresaEmisor", f.getEmpresa().getIdEmpresa());
			map.put("ImpuestosTotalTraslados", f.getImpuestosTotalTraslados() != null ? f.getImpuestosTotalTraslados() : 0);
			map.put("ImpuestosClave", f.getImpuestosClave() != null ? f.getImpuestosClave() : 0);
			map.put("ImpuestosTipoFactor", f.getImpuestosTipoFactor() != null ? f.getImpuestosTipoFactor() : 0);
			map.put("ImpuestosTasaOCuota", f.getImpuestosTasaOCuota() != null ? f.getImpuestosTasaOCuota() : 0);
			map.put("ImpuestosImporte", f.getImpuestosImporte() != null ? f.getImpuestosImporte() : 0);
			map.put("SatVersion", f.getSatVersion());
			map.put("SatUUID", f.getSatUUID());
			map.put("SatFechaTimbrado", f.getSatFechaTimbrado());
			map.put("SatRfcProvCertif", f.getSatRfcProvCertif());
			map.put("SatSelloCFD", f.getSatSelloCFD());
			map.put("SatNoCertificadoSAT", f.getSatNoCertificadoSAT());
			map.put("SatSelloSAT", f.getSatSelloSAT());
			map.put("SatCadenaOriginal", f.getSatCadenaOriginal());
			map.put("CodeQR", f.getCodeQR());
			map.put("FolioPedidoCliente", f.getFolioPedidoCliente());
//			map.put("TotalTexto", f.getTotalTexto());
			map.put("Cliente", f.getCliente().getIdCliente());
			map.put("XML", f.getXml());
			map.put("Certificado", f.getCertificado());
			map.put("TipoCambio", f.getTipoCambio());
			map.put("Estado", f.getEstado());

			String query = "UPDATE FacturaElectronica SET Version = :Version, Serie = :Serie, Folio = :Folio, Fecha = :Fecha, "
					+ " FormaPago = :FormaPago, NoCertificado = :NoCertificado, CondicionesPago = :CondicionesPago, Subtotal = :Subtotal, "
					+ " Moneda = :Moneda, Total = :Total, TipoComprobante = :TipoComprobante, MetodoPago = :MetodoPago, LugarExpedicion = :LugarExpedicion, "
					+ " Sello = :Sello, CadenaOriginal = :CadenaOriginal, EmpresaEmisor = :EmpresaEmisor, ImpuestosTotalTraslados = :ImpuestosTotalTraslados, "
					+ " ImpuestosClave = :ImpuestosClave, ImpuestosTipoFactor = :ImpuestosTipoFactor, ImpuestosTasaOCuota = :ImpuestosTasaOCuota, ImpuestosImporte = :ImpuestosImporte, "
					+ " SatVersion = :SatVersion, SatUUID = :SatUUID, SatFechaTimbrado = :SatFechaTimbrado, SatRfcProvCertif = :SatRfcProvCertif, "
					+ " SatSelloCFD = :SatSelloCFD, SatNoCertificadoSAT = :SatNoCertificadoSAT, SatSelloSAT = :SatSelloSAT, SatCadenaOriginal = :SatCadenaOriginal, "
					+ " CodeQR = :CodeQR, FolioPedidoCliente = :FolioPedidoCliente, Cliente = :Cliente, XML = :XML, "
					+ " Certificado = :Certificado, TipoCambio = :TipoCambio, Estado = :Estado WHERE PK_Factura = :idFactura";
			super.jdbcTemplate.update(query,map);


		} catch (Exception e) {
			log.info("Error: "+ e.getMessage());
			return null;
		}
		return f;
	}

	@Override
	public FacturaElectronica obtenerFacturaElectronica() {
		try {
			FacturaElectronica facturaElectronica = new FacturaElectronica();

			String query = "SELECT FE.*, E.PK_Empresa IdEmpresa, E.RazonSocial, E.RegimenFiscal, E.Alias, E.RFC, C.Clave idCliente, C.RSocial, C.CURP, VC.Tipo UsoCFDI FROM FacturaElectronica FE "
					+ " LEFT JOIN Empresa E ON FE.EmpresaEmisor = E.PK_Empresa "
					+ " LEFT JOIN Clientes C ON FE.Cliente = C.Clave "
					+ " LEFT JOIN valorCombo VC ON C.FK04_UsoCFDI = VC.PK_Folio "
					+ " WHERE FE.Estado = 'Por Timbrar'";

			List<FacturaElectronica> lstFacturaElectronica = super.jdbcTemplate.query(query,new FacturacionElectronicaRowMapper());

			if(lstFacturaElectronica != null) {
				facturaElectronica = lstFacturaElectronica.get(0);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("factura", facturaElectronica.getIdFactura());
				String query2 = "SELECT * FROM PFActuraElectronica WHERE FK01_FacturaElectronica = :factura";
				facturaElectronica.setLstConceptos(super.jdbcTemplate.query(query2, map, new PFacturacionElectronicaRowMapper()));
			}
			log. info("AQUI--------------"+facturaElectronica);
			return facturaElectronica;

		} catch (RuntimeException e) {
			log.info(e.toString());
			return null;
		}

	}

	@Override
	public FacturaElectronica getFElectronicaByFactura(int idFactura) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idFactura", idFactura);

			String query = "SELECT CAST (CASE WHEN Controlados.Cuantos IS NOT NULL AND Controlados.Cuantos > 0 THEN 1 ELSE 0 END AS bit) AS Controlada, fe.*, E.PK_Empresa IdEmpresa, E.RazonSocial, E.RegimenFiscal, E.Alias, E.RFC, E.Calle, E.Colonia, E.Delegacion, E.Estado, E.Ciudad, E.Pais, E.CP, C.Clave idCliente, fe.RRazonSocial AS NombreFiscalP, fe.RRFC AS RFCFiscalP, " +
					" fe.UsoCFDI, f.pedido, f.CPedido, f.OrdenCompra, p.NotaFE, fe.comentario, P.Calle AS CalleEnt, P.Delegacion AS DelEnt, P.Estado AS EdoEnt, P.Pais AS PaisENt, P.CP AS CPEnt FROM FacturaElectronica fe\n" +
					" LEFT JOIN Empresa E ON fe.EmpresaEmisor = E.PK_Empresa \n" +
					" LEFT JOIN Clientes C ON fe.Cliente = C.Clave \n" +
					" LEFT JOIN Factura_FElectronica ffe ON ffe.FK02_FacturaElectronica = fe.PK_Factura\n" +
					" LEFT JOIN facturas f ON ffe.FK01_Factura = f.idFactura \n" +
					" LEFT JOIN pedidos p ON p.cpedido = f.cpedido \n" +
					" LEFT JOIN ( \n" +
					"SELECT COUNT(*) AS cuantos, PF.FK01_Factura FROM PFacturas AS PF \n" +
					"LEFT JOIN PPedidos AS PP ON PP.Cpedido = PF.Cpedido AND PF.PPedido = PP.Part \n" +
					"LEFT JOIN Productos AS PROD ON Prod.idProducto = PP.FK02_Producto \n" +
					"WHERE Prod.Control IN ('Mundiales', 'Nacionales') GROUP BY PF.FK01_Factura \n" +
					") AS Controlados ON Controlados.FK01_Factura = f.idFactura \n" +
					" WHERE ffe.FK01_Factura = :idFactura";

			List<FacturaElectronica> facturaElectronica  = super.jdbcTemplate.query(query, map, new FacturacionElectronicaRowMapper());

			if(facturaElectronica != null) {
				map = new HashMap<String, Object>();
				map.put("factura", facturaElectronica.get(0).getIdFactura());
				String query2 = "SELECT * FROM PFActuraElectronica WHERE FK01_FacturaElectronica = :factura";
				facturaElectronica.get(0).setLstConceptos(super.jdbcTemplate.query(query2, map, new PFacturacionElectronicaRowMapper()));
			}


			return facturaElectronica.get(0);
		} catch (RuntimeException e) {
			log.info(e.toString());
			return null;
		}
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ReferenciaBancaria> obtenerReferenciaBancaria(int idCliente, String empresa) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCliente", idCliente);
			map.put("empresa", empresa);

			String query = "DECLARE @idCliente AS Int= :idCliente\n" +
					"DECLARE @quienFactura AS Varchar(30)=:empresa\n" +
					"DECLARE @formato varchar(10) = '0000'  \n" +
					"SELECT (CASE WHEN cb.Moneda='M.N.' THEN 'MXN' WHEN cb.Moneda='DLS' THEN 'USD' END) AS Moneda,cb.Banco,cb.Sucursal,  \n" +
					"cb.NoCuenta,cb.Clabe,(CASE WHEN cb.Banco<>'Banamex' THEN cl.Nombre ELSE ( \n" +
					"CASE WHEN codCliente.Cod1=' ' THEN 'X' ELSE codCliente.Cod1 END +  \n" +
					"CASE WHEN codCliente.Cod2=' ' THEN 'X' ELSE codCliente.Cod2 END +  \n" +
					"CASE WHEN codCliente.Cod3=' ' THEN 'X' ELSE codCliente.Cod3 END +  \n" +
					"RIGHT(@formato + CAST(codCliente.Clave AS varchar), 4) + codCliente.Cod + CASE WHEN codCliente.Moneda='M' THEN 'P' ELSE 'D' END + codCliente.CodValidador)END) AS refCliente  \n" +
					"FROM CuentaBanco AS cb  \n" +
					"LEFT JOIN(SELECT * FROM Empresa) AS emp ON emp.Alias=@quienFactura  \n" +
					"LEFT JOIN(SELECT Clave,Nombre FROM Clientes) AS cl ON cl.Clave=@idCliente  \n" +
					"LEFT JOIN(SELECT * FROM CuentaCliente) AS cc ON cc.claveCliente=@idCliente AND cc.idCuenta=cb.idCuenta  \n" +
					"LEFT JOIN(SELECT SUBSTRING(UPPER(SUBSTRING(Clientes.Nombre,1,1)),1,1) as Cod1, SUBSTRING(UPPER(SUBSTRING(Clientes.Nombre,2,2)),1,1) as Cod2, SUBSTRING(UPPER(SUBSTRING  \n" +
					"(Clientes.Nombre,3,3)),1,1) as Cod3, Clientes.Clave, Bancos.Codigo as Cod, SUBSTRING(CuentaBanco.Moneda,1,1) as Moneda,codValidador,CuentaBanco.idCuenta  \n" +
					"FROM CuentaCliente JOIN Clientes on Clientes.Clave = claveCliente JOIN CuentaBanco ON CuentaBanco.idCuenta = CuentaCliente.idCuenta JOIN Bancos ON Bancos.Banco = CuentaBanco.Banco)  \n" +
					"AS codCliente ON codCliente.Clave=@idCliente AND codCliente.idCuenta=CB.idCuenta  \n" +
					"WHERE cb.activo=1 AND  \n" +
					"cb.Beneficiario=(emp.Prefijo COLLATE Modern_Spanish_CI_AS)";
			List<ReferenciaBancaria> mapReturn = new ArrayList<ReferenciaBancaria>();
			jdbcTemplate.query(query, map, new RowMapper() {
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					ReferenciaBancaria rb = new ReferenciaBancaria();
					rb.setMoneda(rs.getString("Moneda"));
					rb.setBanco(rs.getString("Banco"));
					rb.setSucursal(rs.getString("Sucursal"));
					rb.setCuenta(rs.getString("NoCuenta"));
					rb.setClabe(rs.getString("Clabe"));
					rb.setRefCliente(rs.getString("refCliente"));

					mapReturn.add(rb);
					return null;
				}
			});
			return mapReturn;
		} catch (RuntimeException e) {
			log.info(e.toString());
			return null;
		}
	}

	@Override
	public String obtenerNumProveedor(int idCliente, String empresa) {
		try{

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCliente", idCliente);
			map.put("empresa", empresa);

			String query = 	"SELECT * FROM NumProveedor WHERE FK01_Cliente = :idCliente AND (Empresa IS NULL OR Empresa = :empresa)" ;

			return (String) super.jdbcTemplate.queryForObject(query,map ,String.class);


		}catch (Exception e){
			//e.printStackTrace();
			return null;
		}
	}


	@Override
	public String obtenerTipoCambio() {
		try{

			Map<String, Object> map = new HashMap<String, Object>();
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String format = formatter.format(date);

			String query = "SELECT TOP 1 Pdolar FROM Monedas WHERE cast(fecha as date) <= '"+format+"' ORDER BY Fecha DESC" ;
			log.info(query);
			return (String) super.jdbcTemplate.queryForObject(query,map ,String.class);


		}catch (Exception e){
			//e.printStackTrace();
			return null;
		}
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public RespuestaSap facturaSap(int idFactura) {
		try{

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idFactura", idFactura);

			String query = "SELECT Folio, Serie, SatUUID, ErrorFactura FROM facturaElectronica WHERE PK_Factura = :idFactura";
			log.info(query);

			RespuestaSap resSap = new RespuestaSap();
			jdbcTemplate.query(query, map, new RowMapper() {
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					resSap.setFolio(rs.getString("Folio"));
					resSap.setSerie(rs.getString("Serie"));
					resSap.setUuid(rs.getString("SatUUID"));
					resSap.setError(rs.getString("ErrorFactura"));
					resSap.setIdFacturaE(idFactura);

					return null;
				}
			});
			return resSap;


		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Boolean insertarErrorFactura(String error, int idFactura){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("error", error);
			map.put("idFactura", idFactura);

			String query = "UPDATE facturaElectronica set ErrorFactura = :error WHERE PK_Factura = :idFactura";

			super.jdbcTemplate.update(query,map);
			return true;
		} catch (Exception e) {
			log.info("Error: "+ e.getMessage());
			return false;
		}
	}

	@Override
	public String validarClienteInternacional(FacturaElectronica factura) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			StringBuilder sbQuery = new StringBuilder(" \n");
			if (factura.getPedido() != null && !factura.getPedido().equals("")) {
				sbQuery.append("SELECT PaisFiscalP FROM Pedidos WHERE CPedido = :cpedido \n");
				map.put("cpedido", factura.getCpedido());
			} else {
				sbQuery.append("SELECT RSPais FROM Clientes WHERE Clave = :idCliente \n");
				map.put("idCliente", factura.getCliente().getIdCliente());
			}
			String pais = (String) jdbcTemplate.queryForObject(sbQuery.toString(), map, String.class);
			if (pais.equalsIgnoreCase("mexico") || pais.equalsIgnoreCase("méxico")) {
				return "";
			}
			return "XEXX010101000";
		} catch (Exception e) {

		}
		return "";
	}
	// TODO: Obtener CP del cliente
	@Override
	public String obtenerCPCliente(String rfc)throws ProquifaNetException{
		System.out.println("entra el metodo!!");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("SELECT TOP 1 RSCP FROM CLIENTES WHERE CURP =:rfc");
			map.put("rfc", rfc);
			System.out.println("CPCliente query:"+ sbQuery);
			String cp = jdbcTemplate.queryForObject(sbQuery.toString(), map, String.class);
			return cp;
		} catch (Exception e) {
			// TODO: handle exception
			log.info("Error: "+ e.getMessage());
			return "";
		}
	}

	// TODO: Obtener Nombre del Cliente de la tabla Clientes
	@Override
	public String getNameCliente(String rfc) throws ProquifaNetException{
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("SELECT TOP 1 RSocial FROM CLIENTES WHERE CURP =:rfc");
			map.put("rfc", rfc);
			String name =  jdbcTemplate.queryForObject(sbQuery.toString(), map, String.class);
			System.out.printf("Query name:" + name);
			return  super.jdbcTemplate.queryForObject(sbQuery.toString(), map, String.class);

		} catch (Exception e) {
			// TODO: handle exception
			log.info("Error: "+ e.getMessage());
			return "";
		}
	}

	// TODO: Obtener Regimen Fiscal del Cliente de la tabla Clientes
	@Override
	public String getRegimenF(String rfc) throws ProquifaNetException{
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			StringBuilder sbQuery = new StringBuilder("\n");
			sbQuery.append("SELECT TOP 1 RegimenFiscal FROM CLIENTES WHERE CURP =:rfc");
			map.put("rfc", rfc);
			return (String) jdbcTemplate.queryForObject(sbQuery.toString(), map, String.class);
		} catch (Exception e) {
			// TODO: handle exception
			log.info("Error: "+ e.getMessage());
			return "";
		}
	}

	@Override
	public String getImpuestoClave(String idDocumento) throws ProquifaNetException {
		try {
			String TipoComprobante="I";
			Map<String, Object> map = new HashMap<String, Object>();
			StringBuilder sbQuery = new StringBuilder("\n");
			map.put("satUUID", idDocumento);
			//map.put("TipoComprobante",TipoComprobante);
			sbQuery.append("select ImpuestosTasaOCuota from  FacturaElectronica where satUUID=:satUUID");
			return jdbcTemplate.queryForObject(sbQuery.toString(), map,String.class);
		}catch (Exception e){
			log.info("Error --->aplicaImpuestos:" + e);
			return null;
		}
	}

	@Override
	public List<String> impuestos(String UUID) throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("SatUUID", UUID.replaceAll(",'$",""));
			System.out.println("idDocumentoFiscal:"+map.get("SatUUID"));
			String Query="SELECT ImpuestosTasaOCuota FROM FacturaElectronica WHERE SatUUID in (:SatUUID)";
			String[] idDocumentos = UUID.split(",");
			List <String> UUIDS = new ArrayList<>();
			for (String uuid: idDocumentos){
				UUIDS.add(uuid);

			}
			MapSqlParameterSource parameters = new MapSqlParameterSource();
			parameters.addValue("SatUUID", UUIDS);
			return  super.jdbcTemplate.queryForList(Query,parameters, String.class);
		}catch (Exception e){
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public double obtenerSubtotalFactura(String Folio) throws ProquifaNetException {
		double subtotal=0;
		try{
			String TipoComprobante="I";
			log.info("Folio docto relacionado:"+Folio);
			Map<String, Object> map = new HashMap<String, Object>();
			StringBuilder sbQuery = new StringBuilder("\n");
			map.put("Folio", Folio);
			map.put("TipoComprobante",TipoComprobante);
			sbQuery.append("SELECT Subtotal FROM FacturaElectronica WHERE SatUUID =:Folio and TipoComprobante=:TipoComprobante");
			log.info(sbQuery.toString());
			String variable=jdbcTemplate.queryForObject(sbQuery.toString(), map, String.class);
			log.info(variable);
			Double variable2 =Double.parseDouble(variable);
			log.info("Valor variable 2: "+variable2);
			return subtotal=Double.parseDouble( jdbcTemplate.queryForObject(sbQuery.toString(), map, String.class));
		}catch(Exception e){
			log.info("Error--->obtenerSubtotalFactura: "+ e.getMessage());
		}

		return subtotal;
	}

	@Override
	public double obtenertipoCambioDR(String Folio) throws ProquifaNetException {
		double cambioDR=0;
		try{
			log.info("Folio docto relacionado:"+Folio);
			Map<String,Object> map =new HashMap<String, Object>();
			StringBuilder sbQuery= new StringBuilder("\n");
			map.put("Folio",Folio);
			sbQuery.append("SELECT top 1 TipoCambioDR from CP_DocRelacionados where Folio=:Folio order by PK_FacturaDocRel desc");
			log.info(sbQuery.toString());
			return cambioDR= Double.parseDouble(jdbcTemplate.queryForObject(sbQuery.toString(), map,String.class));
		}catch(Exception e){
			log.info("Error --->obtnerTipoCambioDR:" + e);
		}
		return cambioDR;
	}

	@Override
	public String aplicaImpuestos(String satUUID) throws ProquifaNetException {

		try {
			String TipoComprobante="I";
			Map<String, Object> map = new HashMap<String, Object>();
			StringBuilder sbQuery = new StringBuilder("\n");
			map.put("satUUID", satUUID);
			map.put("TipoComprobante",TipoComprobante);
			sbQuery.append("select case when impuestosClave='002' Then 1 else 0 end as Aplica_impuestos  from  FacturaElectronica where satUUID=:satUUID and TipoComprobante=:TipoComprobante");
			return jdbcTemplate.queryForObject(sbQuery.toString(), map,String.class);
		}catch (Exception e){
			log.info("Error --->aplicaImpuestos:" + e);
			return null;
		}
		//return null;
	}

	@Override
	public String obtnerUsoCFDICliente(String rfc) throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			StringBuilder sbQuery = new StringBuilder("\n");
			map.put("Curp", rfc);
			sbQuery.append("select top 1 vc.tipo from clientes c inner join ValorCombo vc on vc.PK_Folio=c.FK04_UsoCFDI where c.Curp=:Curp");
			return jdbcTemplate.queryForObject(sbQuery.toString(), map,String.class);
		}catch(Exception e){
			log.info("Error obtenerUsoCFDICliente------>"+e);
			return null;
		}
	}


}
