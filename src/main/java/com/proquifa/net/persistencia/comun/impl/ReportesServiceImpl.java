/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proquifa.net.modelo.comun.DocumentoRecibido;
import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.Reporte;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.negocio.comun.ReportesService;
import com.proquifa.net.persistencia.comun.ReportesDAO;

/**
 * @author ymendez
 *
 */
@Service("reportesService")
public class ReportesServiceImpl implements ReportesService {

	@Autowired
	ReportesDAO reportesDAO;
	
	@Override
	@Transactional(readOnly = true, transactionManager = "ds1TransactionManager")
	public List<?> getScoringInspeccionar() throws ProquifaNetException {
		try {
			return reportesDAO.getScoringInspeccionar();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Override
	@Transactional(readOnly = true, transactionManager = "ds1TransactionManager")
	public List<?> obtenerSeguimientos() throws ProquifaNetException {
		try {
			return reportesDAO.getSeguimientos();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true, transactionManager = "ds1TransactionManager")
	public List<?> getScoringDespachos() throws ProquifaNetException {
		try {
			return reportesDAO.getScoringDespachos();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true, transactionManager = "ds1TransactionManager")
	public List<Reporte> getOrdenDespacho(String ordenDespacho) throws ProquifaNetException {
		try {
			return reportesDAO.getOrdenDespacho(ordenDespacho);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	@Override
	@Transactional(readOnly = true, transactionManager = "ds1TransactionManager")
	public List<Reporte> getConsultaCompra(String compra) throws ProquifaNetException {
		try {
			return reportesDAO.getConsultaCompra(compra);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public String obtenerDate(String tipo, String folio, String carpeta) {
		try {
			Funcion function = new Funcion();
			String ruta = "";
			Parametro parametro = new Parametro();
			if (tipo.equals("facturas")) {
				tipo = "factura";
				parametro.setTabla("Facturas");
				parametro.setFecha("Fecha");
				parametro.setCampo("Factura");
				parametro.setZona("FPor");
				parametro.setFacturo(carpeta);
				parametro.setFolio(folio);
				ruta = folio + ".pdf";
				
			} else if (tipo.equals("envio")) {
				tipo = "doctoscierrert";
				parametro.setTabla("TREnvio");
				parametro.setFecha("FechaFin");
				parametro.setCampo("FK01_Pendiente");
				parametro.setFolio(folio);
				ruta = "Guia-" + folio + ".pdf";
			} else if (tipo.equals("Remisiones")) {
				tipo = "remision";
				parametro.setTabla("Remisiones");
				parametro.setFecha("Fecha");
				parametro.setCampo("Factura");
				parametro.setZona("FPor");
				parametro.setFacturo(carpeta);
				parametro.setFolio(folio);
				ruta = folio + ".pdf";
			} else if (tipo.equals("doctosr")) {
				tipo = "doctosr";
				parametro.setTabla("DoctosR");
				parametro.setFecha("Fecha");
				parametro.setCampo("Folio");
				parametro.setFolio(folio);
				ruta = folio + ".pdf";
			}
			
			
			ruta = Funcion.IP_SERVER + function.obtenerRutaServidorApartirDeSAP(tipo, carpeta) + reportesDAO.obtenerDate(parametro) + ruta;
			
			return ruta;
			
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	@Override
	@Transactional(readOnly = true, transactionManager = "ds1TransactionManager")
	public List<DocumentoRecibido> getMailInfoDoctor(long folioDoctor) throws ProquifaNetException {
		try {
			return reportesDAO.getMailInfoDoctor(folioDoctor);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
