/**
 * 
 */
package com.proquifa.net.negocio.cobrosypagos.facturista.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proquifa.net.modelo.cobrosypagos.facturista.Factura;
import com.proquifa.net.modelo.cobrosypagos.facturista.NotaCredito;
import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.comun.Empresa;
import com.proquifa.net.modelo.comun.Referencia;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.negocio.cobrosypagos.facturista.NotaCreditoService;
import com.proquifa.net.persistencia.cobrosypagos.facturista.FacturacionDAO;
import com.proquifa.net.persistencia.comun.EmpleadoDAO;
import com.proquifa.net.persistencia.comun.EmpresaDAO;
import com.proquifa.net.persistencia.comun.NotaCreditoDAO;

/**
 * @author vromero
 *
 */
@Service("notaCreditoService")
public class NotaCreditoServiceImpl implements NotaCreditoService {

		@Autowired
		NotaCreditoDAO creditoDAO;
		@Autowired
		EmpleadoDAO empleadoDAO;
		@Autowired
		FacturacionDAO facturacionDAO;
		@Autowired
		EmpresaDAO empresaDAO;
		
		Funcion funcion;
		
		final Logger log = LoggerFactory.getLogger(NotaCreditoServiceImpl.class);
		
//		private final static Logger logger = Logger.getLogger(NotaCreditoServiceImpl.class);
		
	public List<NotaCredito> obtenerNotasDeCreditoPorCliente(Long idCliente)
			throws ProquifaNetException {
		return this.creditoDAO.obtenerNotasDeCreditoPorCliente(idCliente);
	}
	
	public List<NotaCredito> getNotaCreditoAvanzada(Date fechaInicio, Date fechaFin, Long idCliente, String nombreFacturo, String estado, 
			Long idUsuarioLogueado, Long cobrador) throws ProquifaNetException{
		
		String condiciones = "";
		
		if (idCliente > 0 ){
			condiciones += " AND NC.FK01_Cliente = " + idCliente.toString() + " "; //LA CLAVE DEL CLIENTE
		}
		
		if (nombreFacturo != ""){
			condiciones += " AND F.FPor = '" + nombreFacturo + "' "; //Nombre del Combo Facturo
		}
		
		if (estado != ""){
			if (estado.toUpperCase().equals("POR APLICAR")){
				condiciones += " AND (NC.Estado <> 'Cancelada' OR NC.Estado IS NULL) ";
			}
			else if (estado.toUpperCase().equals("APLICADA")){
				condiciones += " AND NC.Estado = 'Cancelada' ";
			}						
		}
		
		Empleado e = empleadoDAO.obtenerEmpleadoPorId(idUsuarioLogueado);
		if(e.getIdFuncion() == 40){
			condiciones += " AND c.Cobrador = " + idUsuarioLogueado;
		}else if(e.getIdFuncion() == 37){
			List<String> equipo = empleadoDAO.finEquipoESAC(idUsuarioLogueado);
			condiciones += "AND c.Vendedor in (" ;
			for(String eq : equipo){
				condiciones += "'" + eq + "',";
			}
			condiciones += "'" + e.getUsuario() + "')" ;
		}else if(e.getIdFuncion() == 5){
			condiciones += " AND c.Vendedor = '" + e.getUsuario() + "'";
		}
		
		if(cobrador > 0){
			e = empleadoDAO.obtenerEmpleadoPorId(cobrador);
			condiciones += " AND c.Cobrador = '" + cobrador+ "'";
		}
		
		log.info(condiciones);
		return this.creditoDAO.notaCreditoAvanzada(fechaInicio, fechaFin, condiciones);
	}

	public List<NotaCredito> getNotaCreditoRapida(Long folioNotaCredito, String sPedidoInterno,Long idUsuarioLogueado) throws ProquifaNetException{
		String condiciones = "";
		Empleado e = empleadoDAO.obtenerEmpleadoPorId(idUsuarioLogueado);
		if(e.getIdFuncion() == 40){
			condiciones += " AND c.Cobrador = " + idUsuarioLogueado;
		}else if(e.getIdFuncion() == 37){
			List<String> equipo = empleadoDAO.finEquipoESAC(idUsuarioLogueado);
			condiciones += "AND c.Vendedor in (" ;
			for(String eq : equipo){
				condiciones += "'" + eq + "',";
			}
			condiciones += "'" + e.getUsuario() + "')" ;
		}else if(e.getIdFuncion() == 5){
			condiciones += " AND c.Vendedor = '" + e.getUsuario() + "'";
		}
		return this.creditoDAO.notaCreditoRapida(folioNotaCredito, sPedidoInterno,condiciones);
	}

	@Override
	public List<Empresa> obtenerFoliosNotaCredito() throws ProquifaNetException {
		List<Empresa> folios= this.creditoDAO.getFoliosNotaCredito();
		return folios;
	}

	@Override
	public Double obtenerTotalNotaCreditoClienteEmpresa(String empresa,
			Long idCliente, Date ffinicio, Date ffin)
			throws ProquifaNetException {
		
		String periodo = "";
		String condiciones = "";
		funcion = new Funcion();
		
		if((ffinicio != null)&&(ffin != null)){
			periodo = " AND NC.Fecha >= " + funcion.convertirDosFechasAString(ffinicio, ffin, "NC.Fecha");
			condiciones += periodo;
		}
		if(empresa != null && !empresa.equals("")){
			condiciones += " AND E.Alias ='" + empresa + "'";
		}
		if(idCliente > 0){
			condiciones += " AND NC.FK01_Cliente = " + idCliente;
		}
		
		
		return this.creditoDAO.getTotalNotaCreditoClienteEmpresa(condiciones);
	}

	@Override
	public Boolean existeNotaCreditoPeriodo(String empresa, Long idCliente,
			Date ffinicio, Date ffin) throws ProquifaNetException {
		String periodo = "";
		String condiciones = "";
		
		funcion = new Funcion();
		
		if((ffinicio != null)&&(ffin != null)){
			periodo = 	" AND ((NC.InicioPeriodo <= '" + funcion.convertirFechaAString(ffinicio)  +"' AND NC.FinPeriodo >= '" + funcion.convertirFechaAString(ffinicio) +"') " +
						"	OR (NC.InicioPeriodo <= '" + funcion.convertirFechaAString(ffin) + "' AND NC.FinPeriodo >= '" + funcion.convertirFechaAString(ffin) + "') " +
						"	OR (NC.InicioPeriodo >= '" + funcion.convertirFechaAString(ffinicio) +"' AND NC.FinPeriodo <= '" + funcion.convertirFechaAString(ffin)  + "'))" ;
			condiciones += periodo;
		}
		if(!empresa.equals("")){
			condiciones += " AND E.Alias ='" + empresa + "'";
		}
		if(idCliente > 0){
			condiciones += " AND NC.FK01_Cliente = " + idCliente;
		}
		return this.creditoDAO.existNotaCreditoPeriodo(condiciones);
	}

	@Override
	public Boolean agregarNotaCredito(String empresa, Long idCliente,
			Date ffinicio, Date ffin, String factura, Double monto, Double iva, 
			Referencia documento, String folio, String moneda, Double tipoCambio,
			String serie)throws ProquifaNetException {
		
		Empresa e = this.empresaDAO.getInformacionEmpresaPorAlias(empresa);
		Long idEmpresa = Long.parseLong(e.getIdEmpresa().toString());
		Factura f = new Factura();
		
		if(iva > 0){
			iva = 0.16;
		}
		
		if(factura == null || factura.equals("")){
			f.setIdCliente(idCliente);
			f.setFacturadoPor(empresa);
		}else{
			f = this.facturacionDAO.getFactura(factura.toString(), empresa);
		}
		funcion = new Funcion();
		Boolean r = false;
		Long idNota = 0L;
		
		if((ffinicio != null)&&(ffin != null)){
			idNota = this.creditoDAO.insertNotaCretitoPorPeriodo(idEmpresa, idCliente, ffinicio, ffin, monto, iva, folio, moneda, tipoCambio, serie);
			if(idNota > 0){
				f.setEstado(null);
				List<Factura> listaFacturas = this.facturacionDAO.findFacturas(f, ffinicio, ffin);	
				Boolean r2 = true;
				for(Factura fac : listaFacturas){
					if(!this.creditoDAO.updateFacturaEstadoNotaCredito(fac.getNumeroFactura().toString(), fac.getFacturadoPor())){
						r2 = false;
					}
				}
				if(r2){
					r = true;
				}else{
					this.creditoDAO.deleteNotaCredito(idNota);
				}
			}
			
		}else if(f.getIdFactura() > 0){
			idNota = this.creditoDAO.insertNotaCreditoPorFactura(idEmpresa, idCliente, f.getIdFactura(), monto, iva, folio, moneda, tipoCambio, serie);
			if(idNota > 0){
				if(this.creditoDAO.updateFacturaEstadoNotaCredito(factura, empresa)){
					r = true;
				}else{
					this.creditoDAO.deleteNotaCredito(idNota);
				}
			}
		}
		
		if(r){
			if (documento != null ){ // RM Trading validar espacio
				
				//logger.info(idNota + "." + documento.getExtensionArchivo());
				//logger.info("notaCredito" + empresa.replace(" ", ""));
				
				funcion.copiarArchivo(documento.getBytes(), "ANC-" + idNota + "." + documento.getExtensionArchivo(), "notaCredito" + empresa.replace(" ", ""));
			}
		}
		
		return r;
	}

	@Override
	public Double obtenerMontoNotaCreditoPorFactura(Long factura,
			String empresa) throws ProquifaNetException {

		return this.creditoDAO.getMontoNotaCreditoPorFactura(factura, empresa);
	}
}
