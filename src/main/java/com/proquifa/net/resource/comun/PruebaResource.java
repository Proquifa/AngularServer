package com.proquifa.net.resource.comun;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.Empresa;
import com.proquifa.net.modelo.comun.StatusMessage;
import com.proquifa.net.modelo.comun.facturaElectronica.FacturaElectronica;
import com.proquifa.net.modelo.comun.facturaElectronica.PFacturaElectronica;
import com.proquifa.net.negocio.cobrosypagos.facturista.UtilFacturaService;
import com.proquifa.net.negocio.cobrosypagos.facturista.UtilPedidoService;
import com.proquifa.net.negocio.comun.FacturaElectronicaService;
import com.proquifa.net.negocio.contabilidad.impl.ContabilidadServiceImpl;

import TurboPac.ITurboPacWs;
import TurboPac.ResultadoConsulta;
import TurboPac.TurboPacWs;

@RestController
@CrossOrigin
public class PruebaResource {
	
	final Logger log = LoggerFactory.getLogger(PruebaResource.class);

	@Autowired
	FacturaElectronicaService facturaElectronicaService;
	@Autowired
	UtilFacturaService utilFacturaService;
	@Autowired
	UtilPedidoService UtilPedidosService;

	@PostMapping("/pruebaFactura")
	public ResponseEntity<StatusMessage> pruebaFactura(){
		log.info("on obtenerPatidasCompraParaCEspecifica");
		StatusMessage mensaje = new StatusMessage();
		try {
			/*mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			//String resp = facturaElectronicaService.facturar();
			//log.info("lista Partida Compras-> "+ resp);
			utilFacturaService.generarFacturaPDF("70931");
			utilFacturaService.generarFacturaPDF("70932");
			utilFacturaService.generarFacturaPDF("70934");
			utilFacturaService.generarFacturaPDF("70936");
			mensaje.setCurrent("");
			*/
			
			TurboPacWs service = new TurboPacWs();
			ITurboPacWs portType = service.getBasicHttpsBindingITurboPacWs();
			//return portType.timbraCfdi33("test2", "BB8C171B2DABFFE71D0854138B759FEE25483936", xmlString);
			ResultadoConsulta r = portType.obtenerStatusUuid("RYNDEM", "D2A923DE6E15E5E7D4FEF0477F11E60A046B0504", "098b707a-37a6-4fb2-80ba-db65b2e5fc8c");
			mensaje.setCurrent("");
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@Autowired
	ContabilidadServiceImpl contabilidadServiceImpl;
	
	
	@PostMapping("/generarPDFFactura")
	public ResponseEntity<StatusMessage> generarPDFFactura(@RequestBody String[] array){
		log.info("on obtenerPatidasCompraParaCEspecifica");
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			//String resp = facturaElectronicaService.facturar();
			//log.info("lista Partida Compras-> "+ resp);
			for(int i= 0; i<array.length ; i++) {
				utilFacturaService.generarFacturaPDF(array[i]);
			}
			mensaje.setCurrent("");
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/timbrar")
	public ResponseEntity<StatusMessage> timbrar(){
		log.info("Timbrar");
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");		
			String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><cfdi:Comprobante xmlns:cfdi=\"http://www.sat.gob.mx/cfd/3\" xmlns:implocal=\"http://www.sat.gob.mx/implocal\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" Certificado=\"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\" CondicionesDePago=\"90 DIAS\" Fecha=\"2019-01-28T11:16:28\" Folio=\"1861\" FormaPago=\"99\" LugarExpedicion=\"14080\" MetodoPago=\"PPD\" Moneda=\"USD\" NoCertificado=\"00001000000411996941\" Sello=\"bFJTn5UM3kVdw4QRQ5tpRoEuPLFzgdrCIufJN1Xv2Xgo2GgjnXkEyHhvVujHWWg4N2TKk+LGASyEZSdsB/WDCDiUFCdl98bFc1EvQztotSYfcjwykSjYYUJBZ8cYplRz2ODFzCccUlvUUkRuqYDBfJQI8fzlqO6RH0Q9XxOKKdqzUtRNMdmiBR63PiNrW9wHI9gyrZHNiH5i/ud0kcNyrNa5jCG33iASAywWN/8jK7awIuoNCTzKqggHjCUq9gFfBpT+q+GmU/oXZgkVYCxCOjGJjmzEv0CgWWbwewy9xJ+CCX/py6noaziLKTJJIzT/C2oTAwoVgwEkmhalUVzRow==\" Serie=\"A\" SubTotal=\"2040.00\" TipoCambio=\"1.0\" TipoDeComprobante=\"I\" Total=\"2366.40\" Version=\"3.3\" xsi:schemaLocation=\"http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd\"><cfdi:Emisor Nombre=\"PROVEEDORA QUIMICO FARMACEUTICA, S.A. DE C.V.\" RegimenFiscal=\"612\" Rfc=\"VATM9010184K2\" /><cfdi:Receptor Nombre=\"PROVEEDORA QUIMICO FARMACEUTICA, S.A. DE C.V.\" Rfc=\"PQF910416FB3\" UsoCFDI=\"G03\" /><cfdi:Conceptos><cfdi:Concepto Cantidad=\"5.0\" ClaveProdServ=\"41116107\" ClaveUnidad=\"MGM\" Descripcion=\"Tizanidine Hydrochloride (200 mg)\" Importe=\"2040.00\" NoIdentificacion=\"1667905\" Unidad=\"Pieza\" ValorUnitario=\"408.00\"><cfdi:Impuestos><cfdi:Traslados><cfdi:Traslado Base=\"2040.00\" Importe=\"326.40\" Impuesto=\"002\" TasaOCuota=\"0.160000\" TipoFactor=\"Tasa\" /></cfdi:Traslados></cfdi:Impuestos></cfdi:Concepto></cfdi:Conceptos><cfdi:Impuestos TotalImpuestosTrasladados=\"326.40\"><cfdi:Traslados><cfdi:Traslado Importe=\"326.40\" Impuesto=\"002\" TasaOCuota=\"0.160000\" TipoFactor=\"Tasa\" /></cfdi:Traslados></cfdi:Impuestos><cfdi:Complemento></cfdi:Complemento></cfdi:Comprobante>";
			mensaje.setCurrent("");
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}

	@PostMapping("/insertarFactura")
	public ResponseEntity<StatusMessage> insertarFactura(){
		log.info("on obtenerPatidasCompraParaCEspecifica");
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");

			FacturaElectronica f = new FacturaElectronica();
			f.setFormaPago("99");
			f.setCondicionesPago("60 DIAS");
			f.setSubtotal("312.00");
			f.setMoneda("USD");
			f.setTotal("361.92");
			f.setTipoComprobante("I");
			f.setMetodoPago("PPD");
			f.setLugarExpedicion("14080");
			Empresa empresa = new Empresa();
//			empresa.setIdEmpresa(2);
//			empresa.setAlias("Proveedora");
//			empresa.setRazonSocial("PROVEEDORA QUIMICO FARMACEUTICA, S.A. DE C.V.");
//			empresa.setRfcEmpresa("PQF910416FB3");
//			empresa.setRegimenFiscal("601");
			empresa.setIdEmpresa(4);
			empresa.setAlias("Golocaer");
			empresa.setRazonSocial("GOLOCAER S.A. DE C.V.");
			empresa.setRfcEmpresa("GOL120717DJ7");
			empresa.setRegimenFiscal("601");
			f.setEmpresa(empresa);
			f.setImpuestosTotalTraslados("49.92");
			f.setImpuestosClave("002");
			f.setImpuestosTipoFactor("Tasa");
			f.setImpuestosTasaOCuota("0.160000");
			f.setImpuestosImporte("49.92");
			Cliente cliente = new Cliente();
			cliente.setIdCliente(90L);
			cliente.setUsoCFDI("G03");
			cliente.setRazonSocial("LABORATORIOS PISA SA DE CV");
			cliente.setRfc("LPI830527KJ2");
			f.setCliente(cliente);
			f.setEstado("Por Timbrar");
			f.setTipoCambio("20.2500");
			f.setPedido("076129-00");
			f.setCpedido("110618-8669");

			List<PFacturaElectronica> lstConceptos = new ArrayList<PFacturaElectronica>();
			PFacturaElectronica pfe = new PFacturaElectronica();
			pfe.setClaveProdServ("41116107");
			pfe.setNoIdentificacion("Y0001269");
			pfe.setCantidad("1.00");
			pfe.setClaveUnidad("H87");
			pfe.setUnidad("Pieza");
			pfe.setDescripcion("Aciclovir for peak identification 2 (0.004 mg) EP Lote:5.0 Catálogo: Y0001269");
			pfe.setValorUnitario("156.00");
			pfe.setImporte("156.00");
			pfe.setImpuestoBase("156.00");
			pfe.setImpuestoClave("002");
			pfe.setImpuestoTipoFactor("Tasa");
			pfe.setImpuestoTasaOCuota("0.160000");
			pfe.setImpuestoImporte("24.96");
			pfe.setAdnNumeroPedimento("18  24  1618  8001214");
			lstConceptos.add(pfe);

			pfe.setClaveProdServ("41116107");
			pfe.setNoIdentificacion("Y0001269");
			pfe.setCantidad("1.00");
			pfe.setClaveUnidad("H87");
			pfe.setUnidad("Pieza");
			pfe.setDescripcion("Aciclovir for peak identification 2 (0.004 mg) EP Lote:5.0 Catálogo: Y0001269");
			pfe.setValorUnitario("156.00");
			pfe.setImporte("156.00");
			pfe.setImpuestoBase("156.00");
			pfe.setImpuestoClave("002");
			pfe.setImpuestoTipoFactor("Tasa");
			pfe.setImpuestoTasaOCuota("0.160000");
			pfe.setImpuestoImporte("24.96");
			pfe.setAdnNumeroPedimento("18  47  1618  8000035");
			lstConceptos.add(pfe);

			f.setLstConceptos(lstConceptos);

			int resp = (int) facturaElectronicaService.insertarFactura(f, false, false);


			//String resp = facturaElectronicaService.facturar();
			log.info("lista Partida Compras-> "+ resp);
			mensaje.setCurrent(resp);
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			log.info(e.toString());
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/agregarCLaveCliente")
	public ResponseEntity<StatusMessage> agregarCLaveCliente(){
		log.info("on obtenerPatidasCompraParaCEspecifica");
		StatusMessage mensaje = new StatusMessage();
		try {
			
			contabilidadServiceImpl.crearClavesClientes();
			
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			mensaje.setCurrent("");
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/agregarCLaveProveedor")
	public ResponseEntity<StatusMessage> agregarCLaveProveedor(){
		log.info("on obtenerPatidasCompraParaCEspecifica");
		StatusMessage mensaje = new StatusMessage();
		try {
			
			contabilidadServiceImpl.crearClavesProveedores();
			
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			mensaje.setCurrent("");
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}
	
	
	@PostMapping("/PDFPoliza")
	public ResponseEntity<StatusMessage> PDFPoliza(@RequestBody int idPoliza){
		StatusMessage mensaje = new StatusMessage();
		try {
			
			contabilidadServiceImpl.generarPDFPoliza(idPoliza);
			
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			mensaje.setCurrent("");
			return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mensaje.setStatus(HttpStatus.FORBIDDEN.value());
		mensaje.setMessage("Error");
		return new ResponseEntity<StatusMessage>(mensaje,HttpStatus.FORBIDDEN);
	}

	@PostMapping("/generarPDFPedido")
	public ResponseEntity<StatusMessage> PDFPedido(@RequestBody String[] array) {
		log.info("Entra generar PDF de pedido");
		StatusMessage mensaje = new StatusMessage();
		try {
			mensaje.setStatus(HttpStatus.OK.value());

			for (int i = 0; i < array.length; i++) {
				UtilPedidosService.generarPDFPedido(array[i]);
			}
			mensaje.setMessage("PDF GENERADO CORRECTAMENTE");
			mensaje.setCurrent("");
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			mensaje.setStatus(HttpStatus.FORBIDDEN.value());
			mensaje.setMessage("Error: " + e.getMessage());
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.FORBIDDEN);

		}
	}
}
