package com.proquifa.net.negocio.comun;

import java.util.List;

import com.proquifa.net.modelo.cobrosypagos.cfdi.FacturaTurboPac;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.facturaElectronica.FacturaElectronica;
import com.proquifa.net.modelo.comun.facturaElectronica.PFacturaElectronica;
import com.proquifa.net.modelo.comun.facturaElectronica.RespuestaSap;

import TurboPac.RespuestaTimbrado;
import TurboPac.ResultadoConsulta;

public interface FacturaElectronicaService {

	/***
	 * 
	 * @param xmlString
	 * @return
	 * @throws Exception
	 */
	RespuestaTimbrado timbrarFactura33(String xmlString) throws Exception;

	/***
	 * 
	 * @return
	 */
	String facturar(FacturaElectronica factura, Boolean isSap);

	/***
	 * 
	 * @param f
	 * @return
	 */
	Object insertarFactura(FacturaElectronica f, Boolean isSap, Boolean isFlex);

	FacturaTurboPac timbrarCFDI40(String xmlString,String tipoComprobante) throws Exception;

	/***
	 * 
	 * @param idFactura
	 * @return
	 * @throws Exception
	 */
	RespuestaSap facturaSap(int idFactura) throws Exception;

	/***
	 * 
	 * @param f
	 * @return
	 */
	int timbrarFactura(FacturaElectronica f);

	/**
	 * @param idFacturaElectronica
	 * @param partidas
	 * @return
	 * @throws ProquifaNetException
	 */
	Integer insertarFacturaGenerada(Integer idFacturaElectronica, List<PFacturaElectronica> partidas)
			throws ProquifaNetException;
public String nombre() throws ProquifaNetException;
}
