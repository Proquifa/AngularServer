package com.proquifa.net.negocio.atenderCobro;

import java.util.List;
import java.util.Map;

import com.proquifa.net.modelo.catalogos.cobrosypagos.facturista.CuentaBanco;
import com.proquifa.net.modelo.catalogos.cobrosypagos.facturista.Depositos;
import com.proquifa.net.modelo.catalogos.cobrosypagos.facturista.Excedentes;
import com.proquifa.net.modelo.catalogos.cobrosypagos.facturista.Factura;
import com.proquifa.net.modelo.cobrosypagos.facturista.NotaCredito;
import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;

public interface atenderCobroService {
	
	public Map<String, List<Factura>> atenderCobro(int idCliente) throws ProquifaNetException;
	
	public Map<String, List<NotaCredito>>obtenerNotas(int idCliente) throws ProquifaNetException ;
	
	public Map<String, List<Factura>> generarGraficaCobro(Parametro parametro) throws ProquifaNetException;
	
	public Map<String, List<Excedentes>> consultarExcedentes(Parametro parametros) throws ProquifaNetException;
	
	public Map<String, List<Depositos>> consultarDepositos(Parametro parametros) throws ProquifaNetException;
	
	public Map<String, List<CuentaBanco>> consultarBancos() throws ProquifaNetException;

	
	public int registrarDeposito(Depositos deposito) throws ProquifaNetException;
}
