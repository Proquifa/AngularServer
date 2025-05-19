package com.proquifa.net.negocio.cobrosypagos.facturista.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proquifa.net.modelo.cobrosypagos.facturista.ConceptoFactura;
import com.proquifa.net.modelo.cobrosypagos.facturista.Factura;
import com.proquifa.net.modelo.cobrosypagos.facturista.Facturacion;
import com.proquifa.net.modelo.cobrosypagos.facturista.HistorialFactura;
import com.proquifa.net.modelo.cobrosypagos.facturista.PartidaFactura;
import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.comun.Empresa;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.negocio.cobrosypagos.facturista.FacturacionService;
import com.proquifa.net.persistencia.cobrosypagos.facturista.FacturacionDAO;
import com.proquifa.net.persistencia.cobrosypagos.facturista.PfacturasDAO;
import com.proquifa.net.persistencia.comun.EmpleadoDAO;
import com.proquifa.net.persistencia.comun.NotaCreditoDAO;


/**
 * @author fmartinez
 *
 */
@Service("facturacionService")
public class FacturacionServiceImpl implements FacturacionService {
	@Autowired
	FacturacionDAO facturacionDAO;
	@Autowired
	EmpleadoDAO empleadoDAO;
	@Autowired
	NotaCreditoDAO notaDAO;
	@Autowired
	PfacturasDAO pfacturasDAO;
	
	Funcion funcion;
	
	final Logger log = LoggerFactory.getLogger(FacturacionServiceImpl.class);
	


	public List<Facturacion> consultaFacturacion (String factura, String busqueda, Date finicio, Date ffin, String cliente, String medio, String facturo, String estado, String tipo, String refacturada) throws ProquifaNetException{
		List<Facturacion> fac = this.facturacionDAO.consultaFacturacion(factura, busqueda, finicio, ffin, cliente, medio, facturo, estado, tipo, refacturada);
		return fac;
	}

	public List<HistorialFactura> listarHistorialXFactura(Long idFactura)
			throws ProquifaNetException {
		List<HistorialFactura> historial = this.facturacionDAO.findHistorialXFactura(idFactura);
		return historial;
	}
	
	public List<Empresa> obtenerFolios() throws ProquifaNetException {
		List<Empresa> folios= this.facturacionDAO.consultaFolios();
		return folios;
	}
	
	public List<Empresa> infoEmpresa() {
		List <Empresa> info = this.facturacionDAO.getInfoEmpresa();
		return info;		
	}

	public List<ConceptoFactura> obtenerConceptosFactura(Integer idEmpresa)
			throws ProquifaNetException {
		List<ConceptoFactura> conceptos = this.facturacionDAO.obtenerConceptosFactura(idEmpresa);	
		return conceptos;
	}

	public List<Cliente> obtenerClientesHabilitados()
			throws ProquifaNetException {		
		List<Cliente> clientes = this.facturacionDAO.obtenerClientesHabilitados();
		return clientes;
	}

	public Float getTipoCambio(String moneda) throws ProquifaNetException {		
		Float tipoca = this.facturacionDAO.getTipoCambio(moneda);
		return tipoca;
	}

	public List<Factura> consultarFacturasEmitidasPQNet()
			throws ProquifaNetException {
		return this.facturacionDAO.findFacturasEmitidasPQNet();
	}

	@Override
	public List<Factura> listarFacturasEmitidas(Date finicio, Date ffin,
			Long idCliente, String fpor, String estado, Boolean dentroSistema, 
			Long factura, Long idUsuarioLogueado, Long cobrador, String uuid)
			throws ProquifaNetException {
		String periodo= "";
		String condiciones = "";
		funcion = new Funcion();
			
		if(dentroSistema){
			condiciones += "\n WHERE f.DeSistema =  1" ;
		}else{
			condiciones += "\n WHERE f.DeSistema =  0" ;
		}
		if((finicio != null)&&(ffin != null)){
			periodo = " AND F.Fecha >= " + funcion.convertirDosFechasAString(finicio, ffin, "F.Fecha");
			condiciones += "\n " + periodo;
		}
		
		if(idCliente > 0)
			condiciones += "\n AND C.Clave = " + idCliente;
		
		if(factura > 0)
			condiciones += "\n  AND F.Factura = " + factura;

		if(uuid != null && !uuid.equals(""))
			condiciones += "\n  AND F.UUID LIKE '%" + uuid + "%'";
		
		if(!fpor.equals(""))
			condiciones += "\n AND F.FPor = '"+ fpor +"'";
		
		if(!estado.equals(""))
			condiciones += "\n AND F.Estado = '"+ estado +"'";
		
		Empleado e = empleadoDAO.obtenerEmpleadoPorId(idUsuarioLogueado);
		if(e.getIdFuncion() == 40){
			condiciones += " AND C.Cobrador = " + idUsuarioLogueado;
		}else if(e.getIdFuncion() == 37){
			List<String> equipo = empleadoDAO.finEquipoESAC(idUsuarioLogueado);
			condiciones += " AND C.Vendedor in (" ;
			for(String eq : equipo){
				condiciones += "'" + eq + "',";
			}
			condiciones += "'" + e.getUsuario() + "')" ;
		}else if(e.getIdFuncion() == 5){
			condiciones += " AND C.Vendedor = '" + e.getUsuario() + "'";
		}
		
		if(cobrador > 0){
			e = empleadoDAO.obtenerEmpleadoPorId(cobrador);
			condiciones += " AND C.Cobrador = '" + cobrador+ "'";
		}
		
		return this.facturacionDAO.findFacturasEmitidas(condiciones,dentroSistema);
	}

	@Override
	public Factura obtenerTotalFacturadoClienteEmpresa(String empresa, Long idCliente, Date ffinicio, Date ffin, int folioFactura) throws ProquifaNetException {
		Factura factura = new Factura();
		String periodo = "";
		String condiciones = "";
		funcion = new Funcion();
		
		if((ffinicio != null)&&(ffin != null)){
			periodo = " AND F.Fecha >= " + funcion.convertirDosFechasAString(ffinicio, ffin, "F.Fecha");
			condiciones += periodo;
		}
		if(!empresa.equals("")){
			condiciones += " AND PF.FPor = '" + empresa + "'";
		}
		if(idCliente > 0){
			condiciones += " AND F.Cliente = " + idCliente;
		}
		factura.setMontoAPagar(this.facturacionDAO.getTotalFacturadoClienteEmpresa(condiciones));
		factura.setUuid(this.facturacionDAO.getUuid(folioFactura, empresa));
		return factura;
	}

	@Override
	public Double calcularMontoFacturacionNotaCredito(String empresa,
			Long idCliente, Date ffinicio, Date ffin)
			throws ProquifaNetException {
		Double facturacion = 0.00;
		Double nota = 0.00;
		Factura factura = new Factura();
		
		factura = obtenerTotalFacturadoClienteEmpresa(empresa, idCliente, ffinicio, ffin, 0);
		
		facturacion = factura.getMontoAPagar();
		
		String periodo = "";
		String condiciones = "";
		funcion = new Funcion();
		
		if((ffinicio != null)&&(ffin != null)){
			periodo = " AND NC.Fecha >= " + funcion.convertirDosFechasAString(ffinicio, ffin, "NC.Fecha");
			condiciones += periodo;
		}
		if(!empresa.equals("")){
			condiciones += " AND E.Alias = '" + empresa + "'";
		}
		if(idCliente > 0){
			condiciones += " AND NC.FK01_Cliente = " + idCliente;
		}
		
		nota = notaDAO.getTotalNotaCreditoClienteEmpresa(condiciones);
		
		return facturacion - nota;
	}

	@Override
	public Factura obtenerMontoFactura(Long idFactura, String empresa)
			throws ProquifaNetException {
		// TODO Auto-generated method stub
		Factura factura = new Factura();
		factura.setMontoAPagar(this.facturacionDAO.getMontoFactura(idFactura, empresa));
		factura.setUuid(this.facturacionDAO.getUuid(Integer.parseInt(idFactura.toString()), empresa));
		return factura;
	}
	@Override
	@Transactional
	public Long GuardarDocumentoFiscal(Factura doctoFiscal) throws ProquifaNetException {
		try {
			Integer numPartida=1;
			Long facInsertada = this.facturacionDAO.insertDocumentoFiscalTemporal(doctoFiscal);
			//logger.info("ID de la factura que se inserto: " + facInsertada);
			//logger.info("No de partidas del DoctoFiscal: " + doctoFiscal.getPartidas().size());
			doctoFiscal.setIdFactura(facInsertada);
			for (PartidaFactura partidaFactura :doctoFiscal.getPartidas()) {
				partidaFactura.setDoctoFacturacionTemporal(facInsertada);
				Long partidaInsertada = this.pfacturasDAO.insertPDocumentoFiscalTemporal(partidaFactura);
				partidaFactura.setIdPFactura(partidaInsertada);
				
				//logger.info("ID de la partida de la factura que se inserto: " + partidaInsertada);
				numPartida = numPartida + 1;
			}
				return facInsertada; 
				
		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
			return  -1L;
		}
	}
	@Override
	@Transactional
	public boolean actualizarDocumentoFiscal(Factura doctoFiscal)
			throws ProquifaNetException {
		try {
			
			
			Integer numPartida=1;
			this.facturacionDAO.updateDocumentoFiscalTemporal(doctoFiscal);
			//logger.info("e actualizo Correctamente el docto: " + actualizado);
			////logger.info("No de partidas del DoctoFiscal: " + doctoFiscal.getPartidas().size());
			
			for (PartidaFactura partidaFactura :doctoFiscal.getPartidas()) {
				if (partidaFactura.getIdPFactura()==null ) {
					// la partida no esta en la base de datos; se debe insertar 
					Long partidaInsertada = this.pfacturasDAO.insertPDocumentoFiscalTemporal(partidaFactura);
					// actualiza el idPFactura al objeto
					partidaFactura.setIdPFactura(partidaInsertada);
				} else {
					// se actualizar��� datos de la partida 
					 this.pfacturasDAO.updatePDocumentoFiscalTemporal(partidaFactura);
					//logger.info("Se actualiz��� la partida folio: " + partidaFactura.getIdPFactura() + " de forma correcta : " + resultado);
				}
				numPartida = numPartida + 1;
			}
			Factura doctoAnterior = this.facturacionDAO.obtenerDocumentoFiscalTemporalxidDocto(doctoFiscal.getIdDocumentoFiscal());
			List <PartidaFactura> partidas = this.pfacturasDAO.findPartidasXidDocumentoFiscalTemporal(doctoAnterior.getIdDocumentoFiscal());
			doctoAnterior.setPartidas(partidas);

			if (doctoAnterior.getPartidas().size() > doctoFiscal.getPartidas().size()  ){
				//se buscan que partidas de base de datos ya no existen en el nuevo objeto para ser eliminadas 
				for (PartidaFactura partidaFAnt :doctoAnterior.getPartidas()) {
					boolean igual = false ;
					for (PartidaFactura partidaFAct :doctoFiscal.getPartidas()) {
						if (partidaFAnt.getIdPFactura().equals(partidaFAct.getIdPFactura())){
							igual = true ;
							break;
						}
					}
					if (igual ==false){
						this.pfacturasDAO.deletePartidaxidPDoctoFiscalTemporal(partidaFAnt.getIdPFactura());
					}
				}
			}  
				return true ; 
				
		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
			return  false;
		}
	}

	@Override
	@Transactional
	public boolean eliminarDocumentoFiscal(Long idDoctoFiscal)
			throws ProquifaNetException {
		try {
			
			return this.facturacionDAO.deleteDocumentoFiscalTemporal( idDoctoFiscal);
		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
			return  false;
		}
	}

	@Override
	public List<Factura> obtenerComprobantesFiscales(String fpor, Long idCliente)
			throws ProquifaNetException {
		try {
			String cadena = "\n WHERE DeSistema =  0 " ;
			if(!fpor.equals(""))
				cadena += "\n AND FPor = '"+ fpor +"'";
			if(idCliente > 0L)
				cadena += "\n AND Cliente = " + idCliente;
			List<Factura> fac = this.facturacionDAO.findDocumentosFiscalTemporal(cadena);
			for (Factura factura :fac) {
				Integer numPartida =  1 ;
				//logger.info("doctoFiscal: " + factura.getIdDocumentoFiscal());
				List <PartidaFactura> partidas = this.pfacturasDAO.findPartidasXidDocumentoFiscalTemporal(factura.getIdDocumentoFiscal());
				factura.setPartidas(partidas);
				numPartida = numPartida + 1;
			}
			return fac;		
		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
			return  null;
		}
	}



}
