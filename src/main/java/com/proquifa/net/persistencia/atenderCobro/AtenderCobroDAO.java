package com.proquifa.net.persistencia.atenderCobro;

import java.util.List;
import java.util.Map;

import com.proquifa.net.modelo.catalogos.cobrosypagos.facturista.CuentaBanco;
import com.proquifa.net.modelo.catalogos.cobrosypagos.facturista.Depositos;
import com.proquifa.net.modelo.catalogos.cobrosypagos.facturista.Excedentes;
import com.proquifa.net.modelo.catalogos.cobrosypagos.facturista.Factura;
import com.proquifa.net.modelo.cobrosypagos.facturista.NotaCredito;
import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;

public interface AtenderCobroDAO {

	public Map<String, List<Factura>> atenderCobro(Integer idCliente) throws ProquifaNetException;
	
	public Map<String, List<NotaCredito>> obtenerNotas(Integer idCliente ) throws ProquifaNetException ;
	
	public Map<String, List<Factura>> generarGraFicaCobro(Parametro parametro) throws ProquifaNetException;
	
	public Map<String, List<Excedentes>> consultarExcedentes(Parametro parametro) throws ProquifaNetException;
	
	
	public Map<String, List<Depositos>> consultarDepositos(Parametro parametro) throws ProquifaNetException;
	
	public Map<String, List<CuentaBanco>> consultarBancos() throws ProquifaNetException;
	
	public int  registrarDeposito(Depositos deposito) throws ProquifaNetException;
}
