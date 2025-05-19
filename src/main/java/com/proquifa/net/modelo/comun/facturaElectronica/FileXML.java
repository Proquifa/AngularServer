package com.proquifa.net.modelo.comun.facturaElectronica;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.proquifa.net.modelo.comun.facturaElectronica.addenda.Asofarma.AddendaAsofarma;
import com.proquifa.net.modelo.comun.facturaElectronica.addenda.Asofarma.AddendaAsofarmaPartida;
import com.proquifa.net.modelo.comun.facturaElectronica.addenda.Darier.AddendaDarier;
import com.proquifa.net.modelo.comun.facturaElectronica.addenda.Darier.AddendaDarierConcepto;
import com.proquifa.net.modelo.comun.facturaElectronica.addenda.Darier.AddendaDarierCondicionesDePago;
import com.proquifa.net.modelo.comun.facturaElectronica.addenda.Darier.AddendaDarierDescuentoRecargo;
import com.proquifa.net.modelo.comun.facturaElectronica.addenda.Darier.AddendaDarierDocumento;
import com.proquifa.net.modelo.comun.facturaElectronica.addenda.Darier.AddendaDarierEmisor;
import com.proquifa.net.modelo.comun.facturaElectronica.addenda.Darier.AddendaDarierIdentificacion;
import com.proquifa.net.modelo.comun.facturaElectronica.addenda.Darier.AddendaDarierImpuesto;
import com.proquifa.net.modelo.comun.facturaElectronica.addenda.Darier.AddendaDarierInfoAduanera;
import com.proquifa.net.modelo.comun.facturaElectronica.addenda.Darier.AddendaDarierResConceptos;
import com.proquifa.net.modelo.comun.facturaElectronica.addenda.Darier.AddendaDarierResDescuentosRecargos;
import com.proquifa.net.modelo.comun.facturaElectronica.addenda.Darier.AddendaDarierResImpuestos;
import com.proquifa.net.modelo.comun.facturaElectronica.addenda.Darier.AddendaDarierSubCantidad;
import com.proquifa.net.modelo.comun.facturaElectronica.addenda.Darier.AddendaDarierTotales;
import com.proquifa.net.modelo.comun.facturaElectronica.addenda.Mavi.AddendaMavi;
import com.proquifa.net.modelo.comun.facturaElectronica.addenda.Pfizer.AddendaPfizer;
import com.proquifa.net.modelo.comun.facturaElectronica.addenda.Pfizer.AddendaPfizerLinea;
import com.proquifa.net.modelo.comun.facturaElectronica.addenda.Sanofi.AddendaSanofi;
import com.proquifa.net.modelo.comun.facturaElectronica.addenda.Sanofi.AddendaSanofiDetails;
import com.proquifa.net.modelo.comun.facturaElectronica.addenda.Sanofi.AddendaSanofiHeader;
import com.proquifa.net.modelo.comun.util.Funcion;

public class FileXML {
	Document cfdi;
	private Funcion funcion = new Funcion();
	//private double impuesto;
	private BigDecimal impuesto=BigDecimal.ZERO;
	//private double base;
	private BigDecimal base=BigDecimal.ZERO;
	public FileXML() {
		try {
			cfdi = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public Document getCfdi(){
		try {
			cfdi = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		}catch(Exception e){
			e.printStackTrace();
		}
		return cfdi;
	}

	public Element crearComprobante(String version, String serie, String folio, String fecha,
									String formaPago, String noCertificado, String certificado, String condicionesPago,
									String subtotalTXT, String moneda, String tipoCambio, String totalTXT, String tipoComprobante, String metodoPago,
									String lugarExpedicion) throws IOException {
		Properties propiedades = new Properties();
		InputStream entrada = null;

		entrada = new FileInputStream(Funcion.RUTA_DOCUMENTOS+"configuraciones.properties");

		propiedades.load(entrada);
		String total = propiedades.getProperty("totalErroneo");
		String totalReal = propiedades.getProperty("totalCorrecto");

		Element comprobante = cfdi.createElement( "cfdi:Comprobante" );
		if (tipoComprobante.equals("I")) {
			if(Funcion.FACTURACION_4){
				comprobante.setAttribute("xsi:schemaLocation", "http://www.sat.gob.mx/cfd/4 http://www.sat.gob.mx/sitio_internet/cfd/4/cfdv40.xsd");
			}else {
				comprobante.setAttribute("xsi:schemaLocation", "http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd");
			}
		} else if (tipoComprobante.equals("P")){
			comprobante.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			comprobante.setAttribute("xmlns:pago20", "http://www.sat.gob.mx/Pagos20");
			if(Funcion.FACTURACION_4){
				comprobante.setAttribute("xsi:schemaLocation", "http://www.sat.gob.mx/cfd/4 http://www.sat.gob.mx/sitio_internet/cfd/4/cfdv40.xsd http://www.sat.gob.mx/Pagos20 http://www.sat.gob.mx/sitio_internet/cfd/Pagos/Pagos20.xsd");
			} else {
				comprobante.setAttribute("xsi:schemaLocation", "http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd http://www.sat.gob.mx/Pagos http://www.sat.gob.mx/sitio_internet/cfd/Pagos/Pagos10.xsd");
			}
		} else if (tipoComprobante.equals("E")){
			if(Funcion.FACTURACION_4){
				comprobante.setAttribute("xsi:schemaLocation", "http://www.sat.gob.mx/cfd/4 http://www.sat.gob.mx/sitio_internet/cfd/4/cfdv40.xsd");
			}else{
				comprobante.setAttribute("xsi:schemaLocation", "http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd");
			}
		} else if (tipoComprobante.equals("T")) {
			comprobante.setAttribute("xsi:schemaLocation","http://www.sat.gob.mx/cfd/4 http://www.sat.gob.mx/sitio_internet/cfd/4/cfdv40.xsd http://www.sat.gob.mx/CartaPorte30 http://www.sat.gob.mx/sitio_internet/cfd/CartaPorte/CartaPorte30.xsd");
			comprobante.setAttribute("xmlns:cartaporte30","http://www.sat.gob.mx/CartaPorte30");

		}
		comprobante.setAttribute("Version", version.replaceAll("3.3","4.0"));
		comprobante.setAttribute("Serie", serie);
		comprobante.setAttribute("Folio", folio);
		comprobante.setAttribute("Fecha", fecha);
		if (formaPago != null && !formaPago.equals("") ) {
			comprobante.setAttribute("FormaPago", formaPago);
		}
		comprobante.setAttribute("NoCertificado", noCertificado);
		comprobante.setAttribute("Certificado", certificado);
		if (condicionesPago != null && !condicionesPago.equals("") ) {
			comprobante.setAttribute("CondicionesDePago", condicionesPago);
		}
		comprobante.setAttribute("SubTotal", subtotalTXT);
		comprobante.setAttribute("Moneda", moneda);
		if(!moneda.equals("MXN") && !moneda.equals("XXX")) {
			comprobante.setAttribute("TipoCambio", tipoCambio);
		}
		if (totalTXT.equals(total)){
			comprobante.setAttribute("Total", totalReal);
		}else {
			comprobante.setAttribute("Total", totalTXT);
		}
		comprobante.setAttribute("TipoDeComprobante", tipoComprobante);
		if (metodoPago != null && !metodoPago.equals("") ) {
			comprobante.setAttribute("MetodoPago", metodoPago);
		}
		comprobante.setAttribute("LugarExpedicion", lugarExpedicion);
		comprobante.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		if(Funcion.FACTURACION_4){
			comprobante.setAttribute("xmlns:cfdi", "http://www.sat.gob.mx/cfd/4");
		} else {
			comprobante.setAttribute("xmlns:cfdi", "http://www.sat.gob.mx/cfd/3");
		}
		comprobante.setAttribute("Sello", "Sin Sellar");
		comprobante.setAttribute("Exportacion", "01");
		if (entrada != null) {
			try {
				entrada.close();
			} catch (IOException e) {
				e.printStackTrace();
			}}
		return comprobante;
	}

	public Element crearCfdiRelacionados(String tipo) {
		Element cfdiRelacionados = cfdi.createElement("cfdi:CfdiRelacionados");
		cfdiRelacionados.setAttribute("TipoRelacion", tipo);
		return cfdiRelacionados;
	}

	public Element crearCfdiRelacionado(String uuid) {
		Element cfdiRelacionado = cfdi.createElement("cfdi:CfdiRelacionado");
		cfdiRelacionado.setAttribute("UUID", uuid);
		return cfdiRelacionado;
	}

	public Element crearEmisor(String rfcEmisor, String nombreEmisor, String regimenFiscalEmisor) {
		Element emisor = cfdi.createElement("cfdi:Emisor");
		emisor.setAttribute("Rfc", rfcEmisor);
		emisor.setAttribute("Nombre", nombreEmisor);
		emisor.setAttribute("RegimenFiscal", regimenFiscalEmisor);

		return emisor;
	}

	public Element crearReceptor(String rfcReceptor, String nombreReceptor, String usoCFDIReceptor, String cp, String regimenFiscal) {
		Element receptor = cfdi.createElement("cfdi:Receptor");
		receptor.setAttribute("Rfc", rfcReceptor);
		receptor.setAttribute("Nombre", nombreReceptor);
		receptor.setAttribute("UsoCFDI", usoCFDIReceptor);
		// Nuevos atributos para el 4.0
		if(Funcion.FACTURACION_4) {
			receptor.setAttribute("DomicilioFiscalReceptor", cp); // codigo postal
			receptor.setAttribute("RegimenFiscalReceptor", regimenFiscal); // Regimen fiscal

		}

		return receptor;
	}

	public Element crearConceptos() {
		Element conceptos = cfdi.createElement("cfdi:Conceptos");

		return conceptos;
	}

	public Element crearConcepto(PFacturaElectronica pFacturaElectronica, String objectImp,String tipoObjImp) {
		Element concepto = cfdi.createElement("cfdi:Concepto");
		concepto.setAttribute("ClaveProdServ", pFacturaElectronica.getClaveProdServ());
		if (pFacturaElectronica.getNoIdentificacion() != null && !pFacturaElectronica.getNoIdentificacion().equals("")) {
			concepto.setAttribute("NoIdentificacion", pFacturaElectronica.getNoIdentificacion());
		}
		concepto.setAttribute("Cantidad", pFacturaElectronica.getCantidad());
		concepto.setAttribute("ClaveUnidad", pFacturaElectronica.getClaveUnidad());
		if (pFacturaElectronica.getUnidad() != null && !pFacturaElectronica.getUnidad().equals("")) {
			concepto.setAttribute("Unidad", pFacturaElectronica.getUnidad());
		}
		concepto.setAttribute("Descripcion", pFacturaElectronica.getDescripcion());
		concepto.setAttribute("ValorUnitario", pFacturaElectronica.getValorUnitario());
		concepto.setAttribute("Importe", pFacturaElectronica.getImporte());
		// Se agrego el siguiente atributo, ya que es requerido para el 4.0
		if(tipoObjImp.equals("P")||tipoObjImp.equals("T")){
			concepto.setAttribute("ObjetoImp", "01");
		}else {
			concepto.setAttribute("ObjetoImp", objectImp);
		}
		return concepto;
	}

	public Element crearImpuestos() {
		Element impuestos = cfdi.createElement("cfdi:Impuestos");

		return impuestos;
	}

	public Element crearImpuestos(String totalImpuestosTrasladados, String totalImpuestosRetenidos) throws IOException {
		Element impuestos = cfdi.createElement("cfdi:Impuestos");
		Properties propiedades = new Properties();
		InputStream entrada = null;

		entrada = new FileInputStream(Funcion.RUTA_DOCUMENTOS+"configuraciones.properties");

		propiedades.load(entrada);
		if(!totalImpuestosTrasladados.equals("")){
			if(totalImpuestosTrasladados.equals(propiedades.getProperty("totalImpuestosTrasladadosErroneo"))) {
				impuestos.setAttribute("TotalImpuestosTrasladados", propiedades.getProperty("totalImpuestosTrasladadosCorrecto"));
			}
			else {
				impuestos.setAttribute("TotalImpuestosTrasladados",	totalImpuestosTrasladados);
			}
		}
		if(!totalImpuestosRetenidos.equals("")){
			impuestos.setAttribute("TotalImpuestosRetenidos", totalImpuestosRetenidos);
		}
		if (entrada != null) {
			try {
				entrada.close();
			} catch (IOException e) {
				e.printStackTrace();
			}}
		return impuestos;
	}

	public Element crearTraslados() {
		Element traslados = cfdi.createElement("cfdi:Traslados");

		return traslados;
	}

	public Element crearTraslado(String base, String impuesto, String tipoFactor, String tasaOCuota, String importe) throws IOException {
		Element traslado = cfdi.createElement("cfdi:Traslado");
		Properties propiedades = new Properties();
		InputStream entrada = null;

		entrada = new FileInputStream(Funcion.RUTA_DOCUMENTOS+"configuraciones.properties");

		propiedades.load(entrada);
		String impErroneo=propiedades.getProperty("importeErroneo");
		String impCorrecto=propiedades.getProperty("importeCorrecto");
		String impErroneo2=propiedades.getProperty("importeErroneo2");
		String importeCorrecto2=propiedades.getProperty("importeCorrecto2");


		if(!base.equals("")){
			traslado.setAttribute("Base", base);
		}

		traslado.setAttribute("Impuesto", impuesto);
		traslado.setAttribute("TipoFactor", tipoFactor);
		traslado.setAttribute("TasaOCuota", tasaOCuota);
		if (importe.equals(impErroneo)){
			traslado.setAttribute("Importe", impCorrecto);
		}else {
			if(importe.equals(impErroneo2)) {
				traslado.setAttribute("Importe", importeCorrecto2);
			}else{
				traslado.setAttribute("Importe", importe);
			}

		}
		if (entrada != null) {
			try {
				entrada.close();
			} catch (IOException e) {
				e.printStackTrace();
			}}
		return traslado;
	}

	public Element crearInformacionAduanera(String numPedimento) {
		Element infoAduanera = cfdi.createElement("cfdi:InformacionAduanera");
		infoAduanera.setAttribute("NumeroPedimento", numPedimento);

		return infoAduanera;
	}

	/*Addendas*/

	public Element crearAddenda() {
		Element addenda = cfdi.createElement("cfdi:Addenda");

		return addenda;
	}

	/*Addenda Darier / Stiefel*/

	public Element crearDarierGSKDocMX(AddendaDarier addendaDarier) {
		Element GSKDocMX = cfdi.createElement("GSKDocMX");

		GSKDocMX.appendChild(crearDarierDocumento(addendaDarier.getDocumento()));

		GSKDocMX.appendChild(crearDarierConceptos(addendaDarier.getConceptos()));

		GSKDocMX.appendChild(crearDarierTotales(addendaDarier.getTotales()));

		return GSKDocMX;
	}

	public Element crearDarierDocumento(AddendaDarierDocumento documento) {
		Element Documento = cfdi.createElement("Documento");

		Element Version = cfdi.createElement("Version");
		Version.appendChild(cfdi.createTextNode(documento.getVersion()));
		Documento.appendChild(Version);

		Documento.appendChild(crearDarierIdentificacion(documento.getIdentificacion()));

		Documento.appendChild(crearDarierCondicionesDePago(documento.getCondicionesDePago()));

		return Documento;
	}

	public Element crearDarierIdentificacion(AddendaDarierIdentificacion identificacion) {
		Element Identificacion = cfdi.createElement("Identificacion");

		Element TipoDeOrdenDeCompra = cfdi.createElement("TipoDeOrdenDeCompra");
		TipoDeOrdenDeCompra.appendChild(cfdi.createTextNode(identificacion.getTipoDeOrdenDeCompra()));
		Identificacion.appendChild(TipoDeOrdenDeCompra);

		Element NumeroDeOrdenDeCompra = cfdi.createElement("NumeroDeOrdenDeCompra");
		NumeroDeOrdenDeCompra.appendChild(cfdi.createTextNode(identificacion.getNumeroDeOrdenDeCompra()));
		Identificacion.appendChild(NumeroDeOrdenDeCompra);

		Element FechaDeOrdenDeCompra = cfdi.createElement("FechaDeOrdenDeCompra");
		FechaDeOrdenDeCompra.appendChild(cfdi.createTextNode(identificacion.getFechaDeOrdenDeCompra()));
		Identificacion.appendChild(FechaDeOrdenDeCompra);

		if(!identificacion.getNumeroContrato().equals("")) {
			Element NumeroContrato = cfdi.createElement("NumeroContrato");
			NumeroContrato.appendChild(cfdi.createTextNode(identificacion.getNumeroContrato()));
			Identificacion.appendChild(NumeroContrato);
		}

		Identificacion.appendChild(crearDarierEmisor(identificacion.getEmisor()));

		Element Receptor = cfdi.createElement("Receptor");
		Element EANReceptor = cfdi.createElement("EANReceptorEANReceptor");
		EANReceptor.appendChild(cfdi.createTextNode(identificacion.getReceptor().getEanReceptor()));
		Receptor.appendChild(EANReceptor);
		Identificacion.appendChild(Receptor);

		Element Skind = cfdi.createElement("Skind");
		Skind.appendChild(cfdi.createTextNode(identificacion.getSkind()));
		Identificacion.appendChild(Skind);

		return Identificacion;
	}

	public Element crearDarierEmisor(AddendaDarierEmisor emisor) {
		Element Emisor = cfdi.createElement("Emisor");

		Element NumeroDeProveedor = cfdi.createElement("NumeroDeProveedor");
		NumeroDeProveedor.appendChild(cfdi.createTextNode(emisor.getNumeroDeProveedor()));
		Emisor.appendChild(NumeroDeProveedor);

		if(!emisor.getRegistroPublico().equals("")) {
			Element RegistroPublico = cfdi.createElement("RegistroPublico");
			RegistroPublico.appendChild(cfdi.createTextNode(emisor.getRegistroPublico()));
			Emisor.appendChild(RegistroPublico);
		}

		if(!emisor.getCodigoDeConvenioBancario().equals("")) {
			Element CodigoDeConvenioBancario = cfdi.createElement("CodigoDeConvenioBancario");
			CodigoDeConvenioBancario.appendChild(cfdi.createTextNode(emisor.getCodigoDeConvenioBancario()));
			Emisor.appendChild(CodigoDeConvenioBancario);
		}

		if(!emisor.getEanEmisor().equals("")) {
			Element EANEmisor = cfdi.createElement("EANEmisor");
			EANEmisor.appendChild(cfdi.createTextNode(emisor.getEanEmisor()));
			Emisor.appendChild(EANEmisor);
		}

		Element EmailContacto1 = cfdi.createElement("EmailContacto1");
		EmailContacto1.appendChild(cfdi.createTextNode(emisor.getEmailContacto1()));
		Emisor.appendChild(EmailContacto1);

		if(!emisor.getEmailContacto2().equals("")) {
			Element crearEmailContacto2 = cfdi.createElement("crearEmailContacto2");
			crearEmailContacto2.appendChild(cfdi.createTextNode(emisor.getEmailContacto2()));
			Emisor.appendChild(crearEmailContacto2);
		}

		return Emisor;
	}

	public Element crearDarierCondicionesDePago(AddendaDarierCondicionesDePago condicionesPago) {
		Element CondicionesDePago = cfdi.createElement("CondicionesDePago");

		if(!condicionesPago.getFechaDePago().equals("")) {
			Element FechaDePago = cfdi.createElement("FechaDePago");
			FechaDePago.appendChild(cfdi.createTextNode(condicionesPago.getFechaDePago()));
			CondicionesDePago.appendChild(FechaDePago);
		}

		if(!condicionesPago.getDiasDePago().equals("")) {
			Element DiasDePago = cfdi.createElement("DiasDePago");
			DiasDePago.appendChild(cfdi.createTextNode(condicionesPago.getDiasDePago()));
			CondicionesDePago.appendChild(DiasDePago);
		}

		return CondicionesDePago;
	}

	public Element crearDarierConceptos(List<AddendaDarierConcepto> conceptos) {
		Element Conceptos = cfdi.createElement("Conceptos");

		for (AddendaDarierConcepto item : conceptos) {
			Conceptos.appendChild(crearDarierConcepto(item));
		}

		return Conceptos;
	}

	public Element crearDarierConcepto(AddendaDarierConcepto concepto) {
		Element Concepto = cfdi.createElement("Concepto");

		Element LineaDeOrdenDeCompra = cfdi.createElement("LineaDeOrdenDeCompra");
		LineaDeOrdenDeCompra.appendChild(cfdi.createTextNode(concepto.getLineaDeOrdenDeCompra()));
		Concepto.appendChild(LineaDeOrdenDeCompra);

		Element Cantidad = cfdi.createElement("Cantidad");
		Cantidad.appendChild(cfdi.createTextNode(concepto.getCantidad()));
		Concepto.appendChild(Cantidad);

		Element UnidadDeMedida = cfdi.createElement("UnidadDeMedida");
		UnidadDeMedida.appendChild(cfdi.createTextNode(concepto.getUnidadDeMedida()));
		Concepto.appendChild(UnidadDeMedida);

		if(concepto.getSubCantidad() != null) {
			Concepto.appendChild(crearDarierSubCantidad(concepto.getSubCantidad()));
		}

		Element Descripcion = cfdi.createElement("Descripcion");
		Descripcion.appendChild(cfdi.createTextNode(concepto.getDescripcion()));
		Concepto.appendChild(Descripcion);

		Element ValorUnitario = cfdi.createElement("ValorUnitario");
		ValorUnitario.appendChild(cfdi.createTextNode(concepto.getValorUnitario()));
		Concepto.appendChild(ValorUnitario);

		Element Importe = cfdi.createElement("Importe");
		Importe.appendChild(cfdi.createTextNode(concepto.getImporte()));
		Concepto.appendChild(Importe);

		if(!concepto.getPrecioLista().equals("")) {
			Element PrecioLista = cfdi.createElement("PrecioLista");
			PrecioLista.appendChild(cfdi.createTextNode(concepto.getPrecioLista()));
			Concepto.appendChild(PrecioLista);
		}

		if(!concepto.getNumeroDeLote().equals("")) {
			Element NumeroDeLote = cfdi.createElement("NumeroDeLote");
			NumeroDeLote.appendChild(cfdi.createTextNode(concepto.getNumeroDeLote()));
			Concepto.appendChild(NumeroDeLote);
		}

		if(!concepto.getCantidadDeLote().equals("")) {
			Element CantidadDeLote = cfdi.createElement("CantidadDeLote");
			CantidadDeLote.appendChild(cfdi.createTextNode(concepto.getCantidadDeLote()));
			Concepto.appendChild(CantidadDeLote);
		}

		if(!concepto.getFechaLote().equals("")) {
			Element FechaDelLote = cfdi.createElement("FechaDelLote");
			FechaDelLote.appendChild(cfdi.createTextNode(concepto.getFechaLote()));
			Concepto.appendChild(FechaDelLote);
		}

		if(!concepto.getFechaExpiracionLote().equals("")) {
			Element FechaDeExpiracionDelLote = cfdi.createElement("FechaDeExpiracionDelLote");
			FechaDeExpiracionDelLote.appendChild(cfdi.createTextNode(concepto.getFechaExpiracionLote()));
			Concepto.appendChild(FechaDeExpiracionDelLote);
		}

		Element CodigoReceptor = cfdi.createElement("CodigoReceptor");
		CodigoReceptor.appendChild(cfdi.createTextNode(concepto.getCodigoReceptor()));
		Concepto.appendChild(CodigoReceptor);

		if(!concepto.getCodigoBarra().equals("")) {
			Element CodigoDeBarra = cfdi.createElement("CodigoDeBarra");
			CodigoDeBarra.appendChild(cfdi.createTextNode(concepto.getCodigoBarra()));
			Concepto.appendChild(CodigoDeBarra);
		}

		if(!concepto.getCodigoProveedor().equals("")) {
			Element CodigoProveedor = cfdi.createElement("CodigoProveedor");
			CodigoProveedor.appendChild(cfdi.createTextNode(concepto.getCodigoProveedor()));
			Concepto.appendChild(CodigoProveedor);
		}

		if(concepto.getInfoAduanera() != null) {
			Concepto.appendChild(crearDarierDatosDeImportacion(concepto.getInfoAduanera()));
		}

		if(!concepto.getImporteLista().equals("")) {
			Element ImporteLista = cfdi.createElement("ImporteLista");
			ImporteLista.appendChild(cfdi.createTextNode(concepto.getImporteLista()));
			Concepto.appendChild(ImporteLista);
		}

		if(concepto.getDescuentoORecargo() != null) {
			Concepto.appendChild(crearDarierDescuentosYRecargos(concepto.getDescuentoORecargo()));
		}

		if(concepto.getImpuestos() != null) {
			Concepto.appendChild(crearDarierImpuestos(concepto.getImpuestos()));
		}

		if(!concepto.getImporteLista().equals("")) {
			Element TextosDePosicion = cfdi.createElement("TextosDePosicion");

			if(!concepto.getTextosPosicion().equals("")) {
				Element Texto = cfdi.createElement("Texto");
				Texto.appendChild(cfdi.createTextNode(concepto.getTextosPosicion()));
				TextosDePosicion.appendChild(Texto);
			}
		}

		return Concepto;
	}

	public Element crearDarierSubCantidad(AddendaDarierSubCantidad subCantidad) {
		Element SubCantidad = cfdi.createElement("SubCantidad");

		if(!subCantidad.getUnidad().equals("")) {
			Element Unidad = cfdi.createElement("Unidad");
			Unidad.appendChild(cfdi.createTextNode(subCantidad.getUnidad()));
			SubCantidad.appendChild(Unidad);
		}

		if(!subCantidad.getCantidad().equals("")) {
			Element Cantidad = cfdi.createElement("Cantidad");
			Cantidad.appendChild(cfdi.createTextNode(subCantidad.getCantidad()));
			SubCantidad.appendChild(Cantidad);
		}

		return SubCantidad;
	}

	public Element crearDarierDatosDeImportacion(AddendaDarierInfoAduanera infoAduanera) {
		Element DatosDeImportacion = cfdi.createElement("DatosDeImportacion");
		Element InformacionAduanera = cfdi.createElement("InformacionAduanera");

		if(!infoAduanera.getNumeroDePedimento().equals("")) {
			Element NumeroDePedimento = cfdi.createElement("NumeroDePedimento");
			NumeroDePedimento.appendChild(cfdi.createTextNode(infoAduanera.getNumeroDePedimento()));
			InformacionAduanera.appendChild(NumeroDePedimento);
		}

		if(!infoAduanera.getFechaDePedimento().equals("")) {
			Element FechaDePedimento = cfdi.createElement("FechaDePedimento");
			FechaDePedimento.appendChild(cfdi.createTextNode(infoAduanera.getFechaDePedimento()));
			InformacionAduanera.appendChild(FechaDePedimento);
		}

		if(!infoAduanera.getNombreDeAduana().equals("")) {
			Element NombreDeAduana = cfdi.createElement("NombreDeAduana");
			NombreDeAduana.appendChild(cfdi.createTextNode(infoAduanera.getNombreDeAduana()));
			InformacionAduanera.appendChild(NombreDeAduana);
		}

		DatosDeImportacion.appendChild(InformacionAduanera);

		return DatosDeImportacion;
	}

	public Element crearDarierDescuentosYRecargos(AddendaDarierDescuentoRecargo descuentoORecargo) {
		Element DescuentosYRecargos = cfdi.createElement("DescuentosYRecargos");
		Element DescuentoORecargo = cfdi.createElement("DescuentoORecargo");

		if(!descuentoORecargo.getOperacion().equals("")) {
			Element Operacion = cfdi.createElement("Operacion");
			Operacion.appendChild(cfdi.createTextNode(descuentoORecargo.getOperacion()));
			DescuentoORecargo.appendChild(Operacion);
		}

		if(!descuentoORecargo.getImputacion().equals("")) {
			Element Imputacion = cfdi.createElement("Imputacion");
			Imputacion.appendChild(cfdi.createTextNode(descuentoORecargo.getImputacion()));
			DescuentoORecargo.appendChild(Imputacion);
		}

		if(!descuentoORecargo.getServicio().equals("")) {
			Element Servicio = cfdi.createElement("Servicio");
			Servicio.appendChild(cfdi.createTextNode(descuentoORecargo.getServicio()));
			DescuentoORecargo.appendChild(Servicio);
		}

		if(!descuentoORecargo.getDescripcion().equals("")) {
			Element Descripcion = cfdi.createElement("Descripcion");
			Descripcion.appendChild(cfdi.createTextNode(descuentoORecargo.getDescripcion()));
			DescuentoORecargo.appendChild(Descripcion);
		}

		if(!descuentoORecargo.getBase().equals("")) {
			Element Base = cfdi.createElement("Base");
			Base.appendChild(cfdi.createTextNode(descuentoORecargo.getBase()));
			DescuentoORecargo.appendChild(Base);
		}

		if(!descuentoORecargo.getTasa().equals("")) {
			Element Tasa = cfdi.createElement("Tasa");
			Tasa.appendChild(cfdi.createTextNode(descuentoORecargo.getTasa()));
			DescuentoORecargo.appendChild(Tasa);
		}

		if(!descuentoORecargo.getMonto().equals("")) {
			Element Monto = cfdi.createElement("Monto");
			Monto.appendChild(cfdi.createTextNode(descuentoORecargo.getMonto()));
			DescuentoORecargo.appendChild(Monto);
		}

		DescuentosYRecargos.appendChild(DescuentosYRecargos);

		return DescuentosYRecargos;
	}

	public Element crearDarierImpuestos(List<AddendaDarierImpuesto> impuestos) {
		Element Impuestos = cfdi.createElement("Impuestos");

		for (AddendaDarierImpuesto item : impuestos) {
			Impuestos.appendChild(crearDarierImpuesto(item));
		}

		return Impuestos;
	}

	public Element crearDarierImpuesto(AddendaDarierImpuesto impuesto) {
		Element Impuesto = cfdi.createElement("Impuesto");

		if(!impuesto.getContexto().equals("")) {
			Element Contexto = cfdi.createElement("Contexto");
			Contexto.appendChild(cfdi.createTextNode(impuesto.getContexto()));
			Impuesto.appendChild(Contexto);
		}

		if(!impuesto.getOperacion().equals("")) {
			Element Operacion = cfdi.createElement("Operacion");
			Operacion.appendChild(cfdi.createTextNode(impuesto.getOperacion()));
			Impuesto.appendChild(Operacion);
		}

		if(!impuesto.getCodigo().equals("")) {
			Element Codigo = cfdi.createElement("Codigo");
			Codigo.appendChild(cfdi.createTextNode(impuesto.getCodigo()));
			Impuesto.appendChild(Codigo);
		}

		if(!impuesto.getBase().equals("")) {
			Element Base = cfdi.createElement("Base");
			Base.appendChild(cfdi.createTextNode(impuesto.getBase()));
			Impuesto.appendChild(Base);
		}

		if(!impuesto.getTasa().equals("")) {
			Element Tasa = cfdi.createElement("Tasa");
			Tasa.appendChild(cfdi.createTextNode(impuesto.getTasa()));
			Impuesto.appendChild(Tasa);
		}

		if(!impuesto.getMonto().equals("")) {
			Element Monto = cfdi.createElement("Monto");
			Monto.appendChild(cfdi.createTextNode(impuesto.getMonto()));
			Impuesto.appendChild(Monto);
		}

		return Impuesto;
	}

	public Element crearDarierTotales(AddendaDarierTotales totales) {
		Element Totales = cfdi.createElement("Totales");

		Element Moneda = cfdi.createElement("Moneda");
		Moneda.appendChild(cfdi.createTextNode(totales.getMoneda()));
		Totales.appendChild(Moneda);

		Element SubTotalBruto = cfdi.createElement("SubTotalBruto");
		SubTotalBruto.appendChild(cfdi.createTextNode(totales.getSubTotalBruto()));
		Totales.appendChild(SubTotalBruto);

		Element SubTotal = cfdi.createElement("SubTotal");
		SubTotal.appendChild(cfdi.createTextNode(totales.getSubTotal()));
		Totales.appendChild(SubTotal);

		if(totales.getDescuentoORecargo() != null) {
			Totales.appendChild(crearDarierDescuentosYRecargos(totales.getDescuentoORecargo()));
		}

		Totales.appendChild(crearDarierResumenDeDescuentosYRecargos(totales.getResDescuentosRecargos()));

		if(totales.getImpuestos() != null) {
			Totales.appendChild(crearDarierImpuestos(totales.getImpuestos()));
		}

		Totales.appendChild(crearDarierResumenDeImpuestos(totales.getResumenImpuestos()));

		Element ImporteEnLetras = cfdi.createElement("ImporteEnLetras");
		ImporteEnLetras.appendChild(cfdi.createTextNode(totales.getImporteEnLetras()));
		Totales.appendChild(ImporteEnLetras);

		Element Total = cfdi.createElement("Total");
		Total.appendChild(cfdi.createTextNode(totales.getTotal()));
		Totales.appendChild(Total);

		Totales.appendChild(crearDarierResumenDeConceptos(totales.getResumenConceptos()));

		return Totales;
	}

	public Element crearDarierResumenDeDescuentosYRecargos(AddendaDarierResDescuentosRecargos resDescuentosRecargos) {
		Element ResumenDeDescuentosYRecargos = cfdi.createElement("ResumenDeDescuentosYRecargos");

		if(!resDescuentosRecargos.getTotalDescuentos().equals("")) {
			Element TotalDescuentos = cfdi.createElement("TotalDescuentos");
			TotalDescuentos.appendChild(cfdi.createTextNode(resDescuentosRecargos.getTotalDescuentos()));
			ResumenDeDescuentosYRecargos.appendChild(TotalDescuentos);
		}

		if(!resDescuentosRecargos.getTotalRecargos().equals("")) {
			Element TotalRecargos = cfdi.createElement("TotalRecargos");
			TotalRecargos.appendChild(cfdi.createTextNode(resDescuentosRecargos.getTotalRecargos()));
			ResumenDeDescuentosYRecargos.appendChild(TotalRecargos);
		}

		return ResumenDeDescuentosYRecargos;
	}

	public Element crearDarierResumenDeImpuestos(AddendaDarierResImpuestos resumenImpuestos) {
		Element ResumenDeImpuestos = cfdi.createElement("ResumenDeImpuestos");

		if(!resumenImpuestos.getTotalTrasladosFederales().equals("")) {
			Element TotalTrasladosFederales = cfdi.createElement("TotalTrasladosFederales");
			TotalTrasladosFederales.appendChild(cfdi.createTextNode(resumenImpuestos.getTotalTrasladosFederales()));
			ResumenDeImpuestos.appendChild(TotalTrasladosFederales);
		}

		if(!resumenImpuestos.getTotalIVATrasladado().equals("")) {
			Element TotalIVATrasladado = cfdi.createElement("TotalIVATrasladado");
			TotalIVATrasladado.appendChild(cfdi.createTextNode(resumenImpuestos.getTotalIVATrasladado()));
			ResumenDeImpuestos.appendChild(TotalIVATrasladado);
		}

		if(!resumenImpuestos.getTotalIEPSTrasladado().equals("")) {
			Element TotalIEPSTrasladado = cfdi.createElement("TotalIEPSTrasladado");
			TotalIEPSTrasladado.appendChild(cfdi.createTextNode(resumenImpuestos.getTotalIEPSTrasladado()));
			ResumenDeImpuestos.appendChild(TotalIEPSTrasladado);
		}

		if(!resumenImpuestos.getTotalRetencionesFederales().equals("")) {
			Element TotalRetencionesFederales = cfdi.createElement("TotalRetencionesFederales");
			TotalRetencionesFederales.appendChild(cfdi.createTextNode(resumenImpuestos.getTotalRetencionesFederales()));
			ResumenDeImpuestos.appendChild(TotalRetencionesFederales);
		}

		if(!resumenImpuestos.getTotalISRRetenido().equals("")) {
			Element TotalISRRetenido = cfdi.createElement("TotalISRRetenido");
			TotalISRRetenido.appendChild(cfdi.createTextNode(resumenImpuestos.getTotalISRRetenido()));
			ResumenDeImpuestos.appendChild(TotalISRRetenido);
		}

		if(!resumenImpuestos.getTotalIVARetenido().equals("")) {
			Element TotalIVARetenido = cfdi.createElement("TotalIVARetenido");
			TotalIVARetenido.appendChild(cfdi.createTextNode(resumenImpuestos.getTotalIVARetenido()));
			ResumenDeImpuestos.appendChild(TotalIVARetenido);
		}

		if(!resumenImpuestos.getTotalTrasladosLocales().equals("")) {
			Element TotalTrasladosLocales = cfdi.createElement("TotalTrasladosLocales");
			TotalTrasladosLocales.appendChild(cfdi.createTextNode(resumenImpuestos.getTotalTrasladosLocales()));
			ResumenDeImpuestos.appendChild(TotalTrasladosLocales);
		}

		if(!resumenImpuestos.getTotalRetencionesLocales().equals("")) {
			Element TotalRetencionesLocales = cfdi.createElement("TotalRetencionesLocales");
			TotalRetencionesLocales.appendChild(cfdi.createTextNode(resumenImpuestos.getTotalRetencionesLocales()));
			ResumenDeImpuestos.appendChild(TotalRetencionesLocales);
		}

		return ResumenDeImpuestos;
	}

	public Element crearDarierResumenDeConceptos(AddendaDarierResConceptos resConceptos) {
		Element ResumenDeConceptos = cfdi.createElement("ResumenDeConceptos");

		if(!resConceptos.getCantidadDeLineas().equals("")) {
			Element CantidadDeLineas = cfdi.createElement("CantidadDeLineas");
			CantidadDeLineas.appendChild(cfdi.createTextNode(resConceptos.getCantidadDeLineas()));
			ResumenDeConceptos.appendChild(CantidadDeLineas);
		}

		if(!resConceptos.getCantidadDePiezas().equals("")) {
			Element CantidadDePiezas = cfdi.createElement("CantidadDePiezas");
			CantidadDePiezas.appendChild(cfdi.createTextNode(resConceptos.getCantidadDePiezas()));
			ResumenDeConceptos.appendChild(CantidadDePiezas);
		}

		if(!resConceptos.getCantidadDeCajas().equals("")) {
			Element CantidadDeCajas = cfdi.createElement("CantidadDeCajas");
			CantidadDeCajas.appendChild(cfdi.createTextNode(resConceptos.getCantidadDeCajas()));
			ResumenDeConceptos.appendChild(CantidadDeCajas);
		}

		return ResumenDeConceptos;
	}

	/*Fin Addenda Darier / Stiefel*/

	/*Addenda Mavi*/

	public Element crearMavi(AddendaMavi addendaMavi) {
		Element Mavi = cfdi.createElement("Mavi");

		Element RfcProveedor = cfdi.createElement("RfcProveedor");
		RfcProveedor.appendChild(cfdi.createTextNode(addendaMavi.getRfcProveedor()));
		Mavi.appendChild(RfcProveedor);

		Element NumProveedor = cfdi.createElement("NumProveedor");
		NumProveedor.appendChild(cfdi.createTextNode(addendaMavi.getNumProveedor()));
		Mavi.appendChild(NumProveedor);

		Element FechaFacturacion = cfdi.createElement("FechaFacturacion");
		FechaFacturacion.appendChild(cfdi.createTextNode(addendaMavi.getFechaFacturacion()));
		Mavi.appendChild(FechaFacturacion);

		Element NumPedido = cfdi.createElement("NumPedido");
		NumPedido.appendChild(cfdi.createTextNode(addendaMavi.getNumPedido()));
		Mavi.appendChild(NumPedido);

		Element CodMoneda = cfdi.createElement("CodMoneda");
		CodMoneda.appendChild(cfdi.createTextNode(addendaMavi.getCodMoneda()));
		Mavi.appendChild(CodMoneda);

		Element MontoTotal = cfdi.createElement("MontoTotal");
		MontoTotal.appendChild(cfdi.createTextNode(addendaMavi.getMontoTotal()));
		Mavi.appendChild(MontoTotal);

		Element IVA = cfdi.createElement("IVA");
		IVA.appendChild(cfdi.createTextNode(addendaMavi.getIva()));
		Mavi.appendChild(IVA);

		Element PorcentajeIVA = cfdi.createElement("PorcentajeIVA");
		PorcentajeIVA.appendChild(cfdi.createTextNode(addendaMavi.getPorcentajeIVA()));
		Mavi.appendChild(PorcentajeIVA);

		Element NumFactura = cfdi.createElement("NumFactura");
		NumFactura.appendChild(cfdi.createTextNode(addendaMavi.getNumFactura()));
		Mavi.appendChild(NumFactura);

		Element Serie = cfdi.createElement("Serie");
		Serie.appendChild(cfdi.createTextNode(addendaMavi.getSerie()));
		Mavi.appendChild(Serie);

		Element Folio = cfdi.createElement("Folio");
		Folio.appendChild(cfdi.createTextNode(addendaMavi.getFolio()));
		Mavi.appendChild(Folio);

		return Mavi;
	}

	/*Fin Addenda Mavi*/

	/*Addenda Pfizer*/

	public Element crearPfizer(AddendaPfizer addendaPfizer) {
		Element Pfizer = cfdi.createElement("Pfizer_Ebox");
		Pfizer.setAttribute("TipoAddenda", addendaPfizer.getTipoAddenda());

		Element PfizerPO = cfdi.createElement("PfizerPO");
		PfizerPO.setAttribute("InstruccionesAdicionales", addendaPfizer.getInstruccionesAdicionales());

		for(AddendaPfizerLinea item : addendaPfizer.getLineas()) {
			Element pfizerLinea = cfdi.createElement("Lineas");
			pfizerLinea.setAttribute("PO_NUMBER", item.getPoNumber());
			pfizerLinea.setAttribute("QUANTITY", item.getQuantity());
			pfizerLinea.setAttribute("LINE_NO", item.getLineNo());
			pfizerLinea.setAttribute("AMOUNT", item.getAmount());
			pfizerLinea.setAttribute("TAX_CODE", item.getTaxCode());
			PfizerPO.appendChild(pfizerLinea);
		}

		Pfizer.appendChild(PfizerPO);

		return Pfizer;
	}

	/*Fin Addenda Pfizer*/

	/*Addenda SANOFI*/

	public Element crearSanofi(AddendaSanofi addendaSanofi) {
		Element Sanofi = cfdi.createElement("sanofi:sanofi");
		Sanofi.setAttribute("xmlns:sanofi", "https://mexico.sanofi.com/schemas");
		Sanofi.setAttribute("xsi:schemaLocation", "https://mexico.sanofi.com/schemas https://mexico.sanofi.com/schemas/sanofi.xsd");
		Sanofi.setAttribute("version", "1.0");

		Sanofi.appendChild(crearHeader(addendaSanofi.getHeader()));

		for (AddendaSanofiDetails item : addendaSanofi.getLstDetails()) {
			Sanofi.appendChild(crearDetails(item));
		}

		return Sanofi;
	}

	public Element crearHeader( AddendaSanofiHeader addendaSanofiHeader) {
		Element Header = cfdi.createElement("sanofi:header");

		Element TIPO_DOCTO = cfdi.createElement("sanofi:TIPO_DOCTO");
		TIPO_DOCTO.appendChild(cfdi.createTextNode(addendaSanofiHeader.getTipoDocto()));
		Header.appendChild(TIPO_DOCTO);

		Element NUM_ORDEN = cfdi.createElement("sanofi:NUM_ORDEN");
		NUM_ORDEN.appendChild(cfdi.createTextNode(addendaSanofiHeader.getNumOrden()));
		Header.appendChild(NUM_ORDEN);

		Element NUM_PROVEEDOR = cfdi.createElement("sanofi:NUM_PROVEEDOR");
		NUM_PROVEEDOR.appendChild(cfdi.createTextNode(addendaSanofiHeader.getNumProveedor()));
		Header.appendChild(NUM_PROVEEDOR);

		Element FCTCONV = cfdi.createElement("sanofi:FCTCONV");
		FCTCONV.appendChild(cfdi.createTextNode(addendaSanofiHeader.getFctconv()));
		Header.appendChild(FCTCONV);

		Element MONEDA = cfdi.createElement("sanofi:MONEDA");
		MONEDA.appendChild(cfdi.createTextNode(addendaSanofiHeader.getMoneda()));
		Header.appendChild(MONEDA);

		Element CTA_CORREO = cfdi.createElement("sanofi:CTA_CORREO");
		CTA_CORREO.appendChild(cfdi.createTextNode(addendaSanofiHeader.getCtaCorreo()));
		Header.appendChild(CTA_CORREO);

		Element IMP_RETENCION = cfdi.createElement("sanofi:IMP_RETENCION");
		IMP_RETENCION.appendChild(cfdi.createTextNode(addendaSanofiHeader.getImpRetencion()));
		Header.appendChild(IMP_RETENCION);

		Element IMP_TOTAL = cfdi.createElement("sanofi:IMP_TOTAL");
		IMP_TOTAL.appendChild(cfdi.createTextNode(addendaSanofiHeader.getImpTotal()));
		Header.appendChild(IMP_TOTAL);

		if(addendaSanofiHeader.getObservaciones()!= null && !addendaSanofiHeader.getObservaciones().equals("")) {
			Element OBSERVACIONES = cfdi.createElement("OBSERVACIONES");
			OBSERVACIONES.appendChild(cfdi.createTextNode(addendaSanofiHeader.getObservaciones()));
			Header.appendChild(OBSERVACIONES);
		}


		Element DISPONIBLE_1 = cfdi.createElement("sanofi:DISPONIBLE_1");
		DISPONIBLE_1.appendChild(cfdi.createTextNode(addendaSanofiHeader.getDisponible1()));
		Header.appendChild(DISPONIBLE_1);

		Element DISPONIBLE_2 = cfdi.createElement("sanofi:DISPONIBLE_2");
		DISPONIBLE_2.appendChild(cfdi.createTextNode(addendaSanofiHeader.getDisponible2()));
		Header.appendChild(DISPONIBLE_2);

		Element DISPONIBLE_3 = cfdi.createElement("sanofi:DISPONIBLE_3");
		DISPONIBLE_3.appendChild(cfdi.createTextNode(addendaSanofiHeader.getDisponible3()));
		Header.appendChild(DISPONIBLE_3);

		Element DISPONIBLE_4 = cfdi.createElement("sanofi:DISPONIBLE_4");
		DISPONIBLE_4.appendChild(cfdi.createTextNode(addendaSanofiHeader.getDisponible4()));
		Header.appendChild(DISPONIBLE_4);

		Element CORREO_SANOFI = cfdi.createElement("sanofi:CORREO_SANOFI");
		CORREO_SANOFI.appendChild(cfdi.createTextNode(addendaSanofiHeader.getCorreoSanofi()));
		Header.appendChild(CORREO_SANOFI);

		return Header;
	}

	public Element crearDetails(AddendaSanofiDetails addendaSanofiDetails) {
		Element Details = cfdi.createElement("sanofi:details");

		Element NUM_LINEA = cfdi.createElement("sanofi:NUM_LINEA");
		NUM_LINEA.appendChild(cfdi.createTextNode(addendaSanofiDetails.getNumLinea()));
		Details.appendChild(NUM_LINEA);

		if(!addendaSanofiDetails.getNumEntrada().equals("")) {
			Element NUM_ENTRADA = cfdi.createElement("sanofi:NUM_ENTRADA");
			NUM_ENTRADA.appendChild(cfdi.createTextNode(addendaSanofiDetails.getNumEntrada()));
			Details.appendChild(NUM_ENTRADA);
		}

		Element CUENTA_PUENTE = cfdi.createElement("sanofi:CUENTA_PUENTE");
		CUENTA_PUENTE.appendChild(cfdi.createTextNode(addendaSanofiDetails.getCuentaPuente()));
		Details.appendChild(CUENTA_PUENTE);

		Element UNIDADES = cfdi.createElement("sanofi:UNIDADES");
		UNIDADES.appendChild(cfdi.createTextNode(addendaSanofiDetails.getUnidades()));
		Details.appendChild(UNIDADES);

		Element PRECIO_UNITARIO = cfdi.createElement("sanofi:PRECIO_UNITARIO");
		PRECIO_UNITARIO.appendChild(cfdi.createTextNode(addendaSanofiDetails.getPrecioUnitario()));
		Details.appendChild(PRECIO_UNITARIO);

		Element IMPORTE = cfdi.createElement("sanofi:IMPORTE");
		IMPORTE.appendChild(cfdi.createTextNode(addendaSanofiDetails.getImporte()));
		Details.appendChild(IMPORTE);

		Element UNIDAD_MEDIDA = cfdi.createElement("sanofi:UNIDAD_MEDIDA");
		UNIDAD_MEDIDA.appendChild(cfdi.createTextNode(addendaSanofiDetails.getUnidadMedida()));
		Details.appendChild(UNIDAD_MEDIDA);

		Element TASA_IVA = cfdi.createElement("sanofi:TASA_IVA");
		TASA_IVA.appendChild(cfdi.createTextNode(addendaSanofiDetails.getTasaIva()));
		Details.appendChild(TASA_IVA);

		Element IMPORTE_IVA = cfdi.createElement("sanofi:IMPORTE_IVA");
		IMPORTE_IVA.appendChild(cfdi.createTextNode(addendaSanofiDetails.getImporteIva()));
		Details.appendChild(IMPORTE_IVA);

		Element DISPONIBLE_1 = cfdi.createElement("sanofi:DISPONIBLE_1");
		DISPONIBLE_1.appendChild(cfdi.createTextNode(addendaSanofiDetails.getDisponible1()));
		Details.appendChild(DISPONIBLE_1);

		Element DISPONIBLE_2 = cfdi.createElement("sanofi:DISPONIBLE_2");
		DISPONIBLE_2.appendChild(cfdi.createTextNode(addendaSanofiDetails.getDisponible2()));
		Details.appendChild(DISPONIBLE_2);

		Element DISPONIBLE_3 = cfdi.createElement("sanofi:DISPONIBLE_3");
		DISPONIBLE_3.appendChild(cfdi.createTextNode(addendaSanofiDetails.getDisponible3()));
		Details.appendChild(DISPONIBLE_3);

		Element DISPONIBLE_4 = cfdi.createElement("sanofi:DISPONIBLE_4");
		DISPONIBLE_4.appendChild(cfdi.createTextNode(addendaSanofiDetails.getDisponible4()));
		Details.appendChild(DISPONIBLE_4);

		Element DISPONIBLE_5 = cfdi.createElement("sanofi:DISPONIBLE_5");
		DISPONIBLE_5.appendChild(cfdi.createTextNode(addendaSanofiDetails.getDisponible5()));
		Details.appendChild(DISPONIBLE_5);

		Element DISPONIBLE_6 = cfdi.createElement("sanofi:DISPONIBLE_6");
		DISPONIBLE_6.appendChild(cfdi.createTextNode(addendaSanofiDetails.getDisponible6()));
		Details.appendChild(DISPONIBLE_6);

		return Details;
	}

	/*Fin Addenda SANOFI*/

	/*Addenda ASOFARMA*/

	public Element crearAsonioscoc(AddendaAsofarma addendaAsofarma) {
		Element ASONIOSCOC = cfdi.createElement("ASONIOSCOC");
		ASONIOSCOC.setAttribute("tipoProveedor", addendaAsofarma.getTipoProveedor());
		ASONIOSCOC.setAttribute("noProveedor", addendaAsofarma.getNoProveedor());
		ASONIOSCOC.setAttribute("serie", addendaAsofarma.getSerie());
		ASONIOSCOC.setAttribute("folio", addendaAsofarma.getFolio());
		ASONIOSCOC.setAttribute("ordenCompra", addendaAsofarma.getOrdenCompra());

		ASONIOSCOC.appendChild(crearPartidas(addendaAsofarma.getPartidas()));

		return ASONIOSCOC;
	}

	public Element crearPartidas(List<AddendaAsofarmaPartida> partidas) {
		Element Partidas = cfdi.createElement("Partidas");

		for(AddendaAsofarmaPartida item: partidas) {
			Partidas.appendChild(crearPartida(item));
		}

		return Partidas;
	}

	public Element crearPartida(AddendaAsofarmaPartida partida) {
		Element Partida = cfdi.createElement("Partida");
		Partida.setAttribute("noPartida", partida.getNoPartida());
		Partida.setAttribute("ivaAcreditable", partida.getIvaAcreditable());
		Partida.setAttribute("ivaDevengado", partida.getIvaDevengado());
		Partida.setAttribute("Otros", partida.getOtros());

		return Partida;
	}

	public Element crearComplemento() {
		Element complemento = cfdi.createElement("cfdi:Complemento");

		return complemento;
	}

	/*Fin Addenda ASOFARMA*/

	/*Complemento Pago*/
	public Element crearPagos() {
		/*
		Element pagos = cfdi.createElement("pago10:Pagos");
		pagos.setAttribute("Version", "1.0");*/
		Element pagos = cfdi.createElement("pago20:Pagos");
		pagos.setAttribute("Version", "2.0");

		return pagos;
	}

	public Element crearPago(ComplementoPago cp) {
		Element pago = cfdi.createElement("pago20:Pago");
		pago.setAttribute("FechaPago", cp.getFechaPago());
		pago.setAttribute("FormaDePagoP", cp.getFormaDePagoP());
		pago.setAttribute("MonedaP", cp.getMonedaP());
		if(!cp.getMonedaP().equals("MXN")) {
			pago.setAttribute("TipoCambioP", cp.getTipoCambioP().toString());
		}else{
			pago.setAttribute("TipoCambioP","1");
		}
		if(cp.getMonto() != null  && !cp.getMonto().equals("")) {
			pago.setAttribute("Monto", cp.getMonto().toString());
		}
		if (cp.getCtaOrdenante() != null && !cp.getCtaOrdenante().equals("")){
			pago.setAttribute("CtaOrdenante", cp.getCtaOrdenante());
		}
		if (cp.getCtaOrdenante() != null && !cp.getCtaOrdenante().equals("")) {
			pago.setAttribute("NomBancoOrdExt", cp.getNomBancoOrdExt());
		}
		if (cp.getCtaOrdenante() != null && !cp.getCtaOrdenante().equals("")) {
			pago.setAttribute("RfcEmisorCtaOrd", cp.getRfcEmisorCtaOrd());
		}
		if (cp.getNumOperacion() != null && !cp.getNumOperacion().equals("")) {
			pago.setAttribute("NumOperacion", cp.getNumOperacion());
		}
		/*
		Element pago = cfdi.createElement("pago20:Pago");
		pago.setAttribute("FechaPago", "");
		pago.setAttribute("FormaDePagoP", "");
		pago.setAttribute("MonedaP","");

		pago.setAttribute("TipoCambioP", "");
		*/
		return pago;

	}

	public Element crearDocumentoRelacionado(CPDocRelacionado doc, String monedaP,String cambioDR,String aplicaImpuesto) throws IOException {
		Properties propiedades = new Properties();
		InputStream entrada = null;

		entrada = new FileInputStream(Funcion.RUTA_DOCUMENTOS+"configuraciones.properties");

		propiedades.load(entrada);
		String impInsolutoErroneo=propiedades.getProperty("importeInsolutoErroneo");
		String impInsolutoCorrecto=propiedades.getProperty("importeInsolutoCorrecto");
				try {


					DecimalFormat df = new DecimalFormat("0.00");
					DecimalFormat equivalenciaDR = new DecimalFormat("0.0000000000");
					Element pago = cfdi.createElement("pago20:DoctoRelacionado");
					pago.setAttribute("IdDocumento", doc.getIdDocumento().toString());
					pago.setAttribute("Serie", doc.getSerie());
					pago.setAttribute("Folio", doc.getFolio());
					pago.setAttribute("MonedaDR", doc.getMonedaDR());
					//pago.setAttribute("MetodoDePagoDR", doc.getMetodoPagoDR());
					if (doc.getMonedaDR().equalsIgnoreCase(monedaP)) {
						pago.setAttribute("EquivalenciaDR", "1");
					} else {
						pago.setAttribute("EquivalenciaDR", equivalenciaDR.format(Double.parseDouble(cambioDR)));
						//pago.setAttribute("EquivalenciaDR", "1");
					}
					pago.setAttribute("NumParcialidad", doc.getNumParcialidad().toString());
					System.out.println("ImpSaldoAnt" + df.format(doc.getImpSaldoAnt()));
					pago.setAttribute("ImpSaldoAnt", df.format(doc.getImpSaldoAnt()));
					pago.setAttribute("ImpPagado", df.format(doc.getImpPagado()));
					if (aplicaImpuesto.equals("1")) {
						System.out.println("Aplica impuesto el documento Rel");
						pago.setAttribute("ObjetoImpDR", "02");
					} else {
						pago.setAttribute("ObjetoImpDR", "01");
					}

					if (doc.getImpSaldoInsoluto() != null) {
						System.out.println("saldo insoluto:" + doc.getImpSaldoInsoluto());
						if (doc.getImpSaldoInsoluto().toString().replaceAll("0.0", "0.00").equals(impInsolutoErroneo)) {
							pago.setAttribute("ImpSaldoInsoluto", impInsolutoCorrecto);
						} else {
							pago.setAttribute("ImpSaldoInsoluto", doc.getImpSaldoInsoluto().toString().replaceAll("0.0", "0.00"));
						}
					} else {
						pago.setAttribute("ImpSaldoInsoluto", "0.00");
					}
					return pago;
				}catch (Exception e){
					e.printStackTrace();
				}finally{
					entrada.close();
				}

		return null;
	}

	//////////////////// Nodos nuevos para Complemento 2.0////////////////////////

	public Element creartotales(String monto,String moneda,String tipoCambio,String aplicaImpuestos,String impuestoDocR,boolean aplicaExento) throws IOException {
		Element total= cfdi.createElement("pago20:Totales");
		BigDecimal resultado= BigDecimal.ZERO;
		BigDecimal baseIVA= BigDecimal.ZERO;
		DecimalFormat df = new DecimalFormat("0.00");
		DecimalFormat dfImpIVA = new DecimalFormat("0.00");
		BigDecimal impuestosIVA=BigDecimal.ZERO;
		Properties propiedades = new Properties();
		InputStream entrada = null;


		entrada = new FileInputStream(Funcion.RUTA_DOCUMENTOS+"configuraciones.properties");
		propiedades.load(entrada);
		String Erroneo=propiedades.getProperty("TotalTrasladosImpuestoIVA16Erroneo");
		String Correcto=propiedades.getProperty("TotalTrasladosImpuestoIVA16Correcto");


		String trasladoErroneo=propiedades.getProperty("TotalTrasladosBaseIVA16Erroneo");
		String trasladoCorrecto=propiedades.getProperty("TotalTrasladosBaseIVA16Correcto");

		try {
			if (aplicaImpuestos.equals("1")) {

				if (moneda.equals("USD")) {

					resultado= new BigDecimal(monto).multiply(new BigDecimal(tipoCambio));

					baseIVA = new BigDecimal(tipoCambio).multiply(base);

					impuestosIVA= new BigDecimal(tipoCambio).multiply(impuesto);
				} else {
					resultado= new BigDecimal(monto);
					baseIVA=base;
					impuestosIVA=impuesto;
				}

				if (df.format(baseIVA).equals(trasladoErroneo)){///condicion solo para errores de timbrado por decimales que no cuadran en complemento de pago
					total.setAttribute("TotalTrasladosBaseIVA16",trasladoCorrecto);
				}else {

					total.setAttribute("TotalTrasladosBaseIVA16", redondearCantidad(baseIVA).toString());

				}

				if(df.format(impuestosIVA).equals(Erroneo)){///condicion solo para errores de timbrado por decimales que no cuadran en complemento de pago
					total.setAttribute("TotalTrasladosImpuestoIVA16", Correcto);
				}else {
					total.setAttribute("TotalTrasladosImpuestoIVA16", redondearCantidad(impuestosIVA).toString());
				}
				}else{

				if (moneda.equals("USD")) {
					resultado = (new BigDecimal(monto).multiply( new BigDecimal(tipoCambio)));
				} else {

					resultado = new BigDecimal (monto);
				}
			}

			total.setAttribute("MontoTotalPagos", df.format(resultado));
			if (aplicaExento==false){
				total.setAttribute("TotalTrasladosBaseIVAExento",df.format(resultado));
				total.removeAttribute("TotalTrasladosImpuestoIVA16");
				total.removeAttribute("TotalTrasladosBaseIVA16");
			}

			return total;
		}catch(Exception e){
			System.out.println(e);
		}finally {
			/*impuesto=0.00;
			base=0.00;*/
			impuesto=BigDecimal.ZERO;
			base=BigDecimal.ZERO;
			entrada.close();

		}
		return null;
	}

	public Element crearImpuestosDR(String baseDR,String monedaP,String tipoCambioP,String monedaDR,String impuestoDocR,boolean AplicaExento){
		Element impDR= cfdi.createElement("pago20:ImpuestosDR");
		Element trasladosDR =cfdi.createElement("pago20:TrasladosDR");
		Element trasladoDR=cfdi.createElement("pago20:TrasladoDR");
		
		

		DecimalFormat df=null;
		DecimalFormat dfBaseDR=null;
		BigDecimal conversion=BigDecimal.ZERO;
		BigDecimal importeDR= BigDecimal.ZERO;
		BigDecimal IVA= new BigDecimal(impuestoDocR);


			//si la moneda ddel pago es en dolares, nmo se hace una conversion
			if (monedaP.equals("MXN") && monedaDR.equals("USD")) {
				df = new DecimalFormat("0.0000");
				dfBaseDR = new DecimalFormat("0.0000");

				conversion = (new BigDecimal(tipoCambioP).multiply(new BigDecimal(baseDR)));
				importeDR = (conversion.multiply(IVA));
			} else if (monedaP.equals("USD") && monedaDR.equals("MXN")) {
				conversion.divide(new BigDecimal(tipoCambioP));
				df = new DecimalFormat("0.00");
				dfBaseDR = new DecimalFormat("0.00");

				importeDR = (conversion.multiply(IVA));
			} else if (monedaP.equals("MXN") && monedaDR.equals("MXN")) {
				conversion = new BigDecimal(baseDR);
				df = new DecimalFormat("0.0000");
				dfBaseDR = new DecimalFormat("0.0000");

				importeDR = (conversion.multiply(IVA));
			} else {
				conversion = new BigDecimal(baseDR);
				df = new DecimalFormat("0.00");
				dfBaseDR = new DecimalFormat("0.00");
				importeDR = new BigDecimal(df.format(conversion.multiply(IVA)));
			}

			trasladoDR.setAttribute("BaseDR",dfBaseDR.format(conversion));//Subtotal de la factura relacionada
			trasladoDR.setAttribute("ImporteDR",df.format(importeDR));

		///////Valores fijos en el xml/////////////////////////////////////////////////////////////
		trasladoDR.setAttribute("ImpuestoDR","002");
		trasladoDR.setAttribute("TipoFactorDR","Tasa");
		///////////////////////////////////////////////////////////////////////////////////////////
		trasladoDR.setAttribute("TasaOCuotaDR",impuestoDocR);

		if (AplicaExento==false) {
			trasladoDR.removeAttribute("ImporteDR");
			trasladoDR.removeAttribute("TipoFactorDR");
			trasladoDR.removeAttribute("TasaOCuotaDR");
			//trasladoDR.setAttribute("ImporteDR","0.0000");
			trasladoDR.setAttribute("TipoFactorDR","Exento");
		}
		trasladosDR.appendChild(trasladoDR);
		impDR.appendChild(trasladosDR);
///////////////////////////////////////////////////////////////////////////////////////////
		
		impuesto =impuesto.add(importeDR);
		base =base.add(conversion);
		return impDR;
	}

	public  double obtenerTercerDigitoDecimal(double valor) {
		//
		//
		DecimalFormat df= new DecimalFormat("0.00");
		String cifraString = String.valueOf(valor);
		int indicePuntoDecimal = cifraString.indexOf('.');
		double suma = 0.0;
		char cuartoDigitoChar='0';

		if (indicePuntoDecimal >= 0 && cifraString.length() > indicePuntoDecimal + 3) {
			char tercerDigitoChar = cifraString.charAt(indicePuntoDecimal + 3);

			if ((indicePuntoDecimal + 4)<cifraString.length()){
				cuartoDigitoChar = cifraString.charAt(indicePuntoDecimal + 4);
			}
			System.out.println(cuartoDigitoChar);
			//if(Character.getNumericValue(cuartoDigitoChar)>5) {
				if (Character.getNumericValue(tercerDigitoChar) == 5) {
					suma = valor + 0.001;
					System.out.println("Valor redondeado:" + suma);
					return suma;
				} else if (Character.getNumericValue(cuartoDigitoChar)>= 5 && Character.getNumericValue(tercerDigitoChar)<=5){

					suma = valor +0.001;
					return suma;
				//}
			}else {
				suma=valor;
				return suma;
			}

		}

		return valor; // Si no hay tercer dígito decimal
	}

	public BigDecimal redondearCantidad(BigDecimal cantidad){
		BigDecimal redondeado = cantidad.setScale(2, RoundingMode.HALF_UP);

				return redondeado;

	}
	public Element crearImpuestosP(String tipoMonedaP,String tipoMonedaDR,String impuestoDocR,boolean AplicaExento) throws IOException {
		Properties propiedades = new Properties();
		InputStream entrada = null;

		entrada = new FileInputStream(Funcion.RUTA_DOCUMENTOS+"configuraciones.properties");
		try {
			Element impuestos = cfdi.createElement("pago20:ImpuestosP");
			DecimalFormat importeP=null;
			DecimalFormat BaseP=null;
			Double conversion=0.00;
			Double conversionImp=0.00;



			propiedades.load(entrada);
			String impPErroneo=propiedades.getProperty("importePErroneo");
			String impPCorrecto=propiedades.getProperty("importePCorrecto");
			if(tipoMonedaP.equals("MXN")&&tipoMonedaDR.equals("USD")){

				importeP = new DecimalFormat("0.0000");
				BaseP= new DecimalFormat("0.0000");

			}else if(tipoMonedaP.equals("USD")&&tipoMonedaDR.equals("MXN")){
				importeP = new DecimalFormat("0.00");
				BaseP= new DecimalFormat("0.00");
			}
			else if (tipoMonedaP.equals("MXN")&&tipoMonedaDR.equals("MXN")){
				importeP = new DecimalFormat("0.0000");
				BaseP= new DecimalFormat("0.0000");
			}
			else
			{
				importeP = new DecimalFormat("0.00");
				BaseP= new DecimalFormat("0.00");
			}

			Element traslados = cfdi.createElement("pago20:TrasladosP");
			Element traslado = cfdi.createElement("pago20:TrasladoP");
			traslado.setAttribute("BaseP", BaseP.format(base));
			traslado.setAttribute("ImpuestoP", "002");
			traslado.setAttribute("TipoFactorP", "Tasa");
			if (AplicaExento==false){
				traslado.setAttribute("TasaOCuotaP", "0.160000");
			}else {
				traslado.setAttribute("TasaOCuotaP", impuestoDocR);
			}
			System.out.println("Valor del impuesto erroneo: "+impuesto);
			if (importeP.format(impuesto).equals(impPErroneo)){
				System.out.println("Entra a la condicion del impuesto");
				traslado.setAttribute("ImporteP", impPCorrecto);
			}else {
				traslado.setAttribute("ImporteP", importeP.format(impuesto));
			}
			if (AplicaExento==false){
				traslado.removeAttribute("ImporteP");
				traslado.removeAttribute("TipoFactorP");
				traslado.removeAttribute("TasaOCuotaP");
				traslado.setAttribute("TipoFactorP","Exento");

			}
			traslados.appendChild(traslado);
			impuestos.appendChild(traslados);
			return impuestos;
		}catch(Exception e){
			return null;
		}finally {
			entrada.close();
		}

	}
	public Element crearInformacionGlobal(){
		LocalDate currentDate = LocalDate.now();
		int month = currentDate.getMonthValue();
		int year = currentDate.getYear();
		String formattedMonth = (month < 10) ? "0" + month : String.valueOf(month);
		Element infoGlobal = cfdi.createElement("cfdi:InformacionGlobal");
		infoGlobal.setAttribute("Periodicidad", "01");

		infoGlobal.setAttribute("Meses", formattedMonth);
		infoGlobal.setAttribute("Año", String.valueOf(year));

		return infoGlobal;
	}
	/*Fin Complemento Pago*/


	/**************************Nodos CartaPorte***********************************/

	public  Element crearInfoAduanera(String Pedimento){
		Element infoAduanera= cfdi.createElement("NumeroPedimento");
		infoAduanera.setAttribute("NumeroPedimento:",Pedimento);
		return  infoAduanera;
	}

	public Element crearCartaPorte(String distRecorrida,String idCCP){
		Element cartaPorte = cfdi.createElement("cartaporte30:CartaPorte");
		cartaPorte.setAttribute("IdCCP","CCC"+idCCP);
		cartaPorte.setAttribute("TotalDistRec", distRecorrida);
		cartaPorte.setAttribute("Version","3.0");
		cartaPorte.setAttribute("TranspInternac","No");

		return cartaPorte;
	}

	public Element crearUbicaciones (){
		Element ubicaciones= cfdi.createElement("cartaporte30:Ubicaciones");
		return ubicaciones;
	}
	public Element crearUbicacion(String RFCRemitente,String horaSalida,String calle,String numExt,String colonia,String municipio,String estado,String cPostal,String distRecorrida,int tipoUbicacion){
		Element ubicacion= cfdi.createElement("cartaporte30:Ubicacion");
		Element domicilio = cfdi.createElement("cartaporte30:Domicilio");
		Date hoy = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'18:00:00");

		if(tipoUbicacion==0) {
			ubicacion.setAttribute("TipoUbicacion","Origen");
			ubicacion.setAttribute("FechaHoraSalidaLlegada",horaSalida);
			ubicacion.setAttribute("RFCRemitenteDestinatario","PQF910416FB3");

			domicilio.setAttribute("Calle", "JOSE MARIA MORELOS");
			domicilio.setAttribute("NumeroExterior", "164");
			//domicilio.setAttribute("Colonia", "2052");
			domicilio.setAttribute("Municipio", "012");
			domicilio.setAttribute("Estado", "CMX");
			domicilio.setAttribute("CodigoPostal", "14080");
		}else{
					String c_Estado=null;
					String c_Municipio=null;
					if (Integer.parseInt(cPostal)>=44000 & Integer.parseInt(cPostal)<=49999){
						c_Estado="JAL";
						c_Municipio="039";
					} else if (Integer.parseInt(cPostal)>=62000 & Integer.parseInt(cPostal)<=62999) {
						c_Estado="MOR";
						c_Municipio="011";
					}else {
						c_Estado="MEX";
						c_Municipio="106";
					}
			ubicacion.setAttribute("TipoUbicacion","Destino");
			ubicacion.setAttribute("RFCRemitenteDestinatario",RFCRemitente);
			ubicacion.setAttribute("FechaHoraSalidaLlegada", dateFormat.format(hoy));
			ubicacion.setAttribute("DistanciaRecorrida",distRecorrida);

			domicilio.setAttribute("Calle", calle);
			domicilio.setAttribute("NumeroExterior", numExt);
			//domicilio.setAttribute("Colonia", colonia);
			domicilio.setAttribute("Municipio", c_Municipio);/**Corregir por el numero correcto de municipio**/
			domicilio.setAttribute("Estado", c_Estado);
			domicilio.setAttribute("CodigoPostal", cPostal);

		}
		domicilio.setAttribute("Pais", "MEX");

		ubicacion.appendChild(domicilio);
		return ubicacion;
	}
	public Element crearMercancias (String peso,String totalPzas){
		Element mercancias = cfdi.createElement("cartaporte30:Mercancias");
		mercancias.setAttribute("PesoBrutoTotal",peso);
		mercancias.setAttribute("UnidadPeso","XBX");
		mercancias.setAttribute("PesoNetoTotal",peso);
		mercancias.setAttribute("NumTotalMercancias",totalPzas);
		return mercancias;
	}
	public Element crearMercancia (String descripcion,String cant,String cUnidad,String unidad,String peso,String moneda,String bienesTransp,String valorUnitario){
		Element mercancia = cfdi.createElement("cartaporte30:Mercancia");
		mercancia.setAttribute("BienesTransp",bienesTransp);
		mercancia.setAttribute("Descripcion",descripcion);
		mercancia.setAttribute("Cantidad",cant+".0");
		mercancia.setAttribute("ClaveUnidad",cUnidad);
	//	mercancia.setAttribute("MaterialPeligroso","No");
		mercancia.setAttribute("PesoEnKg",peso);
		mercancia.setAttribute("Moneda",moneda);
		mercancia.setAttribute("ValorMercancia",valorUnitario);
	//	Element canTrans = cfdi.createElement("cartaporte30:CantidadTransporta");
	//	canTrans.setAttribute("Cantidad",cant);
	//	mercancia.appendChild(canTrans);

		return mercancia;
	}
	public Element crearAutoTransporte(String permSCT,String numPermSCT,String configVehicular,String placaVM,String anioMod,String aseguraRespCivil,String polizaRespCivil){
		Element autoTransporte= cfdi.createElement("cartaporte30:Autotransporte");
		Element identificacionVehicular =cfdi.createElement("cartaporte30:IdentificacionVehicular");
		Element seguros=cfdi.createElement("cartaporte30:Seguros");
		autoTransporte.setAttribute("PermSCT",permSCT);
		autoTransporte.setAttribute("NumPermisoSCT",numPermSCT);
		identificacionVehicular.setAttribute("ConfigVehicular",configVehicular);
		identificacionVehicular.setAttribute("PlacaVM",placaVM);
		identificacionVehicular.setAttribute("AnioModeloVM",anioMod);
		identificacionVehicular.setAttribute("PesoBrutoVehicular","1");
		autoTransporte.appendChild(identificacionVehicular);
		seguros.setAttribute("AseguraRespCivil",aseguraRespCivil);
		seguros.setAttribute("PolizaRespCivil",polizaRespCivil);
		autoTransporte.appendChild(seguros);
		return autoTransporte;
	}
	public Element crearFiguraTransporte(String rfcMensajero,String licencia,String nombreMensajero,String numExt,String numInt,String calle){
		Element figuraTransporte = cfdi.createElement("cartaporte30:FiguraTransporte");
		Element tiposFigura =cfdi.createElement("cartaporte30:TiposFigura");
		Element domicilio =cfdi.createElement("cartaporte30:Domicilio");
		tiposFigura.setAttribute("TipoFigura","01");
		tiposFigura.setAttribute("RFCFigura",rfcMensajero);
		tiposFigura.setAttribute("NumLicencia",licencia);
		tiposFigura.setAttribute("NombreFigura",nombreMensajero);

		domicilio.setAttribute("Calle",calle);
		domicilio.setAttribute("NumeroExterior",numExt);
		domicilio.setAttribute("NumeroInterior",numInt);
		domicilio.setAttribute("Estado","CMX");
		domicilio.setAttribute("Pais","MEX");
		domicilio.setAttribute("CodigoPostal","14080");

		//tiposFigura.appendChild(domicilio);
		figuraTransporte.appendChild(tiposFigura);
		return figuraTransporte;
	}


}
