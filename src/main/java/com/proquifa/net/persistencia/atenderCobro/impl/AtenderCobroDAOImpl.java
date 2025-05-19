package com.proquifa.net.persistencia.atenderCobro.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.catalogos.cobrosypagos.facturista.CuentaBanco;
import com.proquifa.net.modelo.catalogos.cobrosypagos.facturista.Depositos;
import com.proquifa.net.modelo.catalogos.cobrosypagos.facturista.Excedentes;
import com.proquifa.net.modelo.catalogos.cobrosypagos.facturista.Factura;
import com.proquifa.net.modelo.cobrosypagos.facturista.NotaCredito;
import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.atenderCobro.AtenderCobroDAO;

import ucar.nc2.units.DateFromString;

@Repository
public class AtenderCobroDAOImpl extends DataBaseDAO implements AtenderCobroDAO{

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, List<Factura>> atenderCobro(Integer idCliente) throws ProquifaNetException{
		try{
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append(" \n");		

//			sbQuery.append("SELECT F.Factura, F.PInterno,F.CPago, f.Fecha, F.FRevision, F.CRecibo, F.FEPago, F.Moneda,\n"); 
//			sbQuery.append("STR(CASE WHEN F.saldoInsoluto IS NULL THEN \n");
//			sbQuery.append("CASE WHEN (F.Tcambio = 0 OR  F.Tcambio IS NULL) THEN\n"); 
//
//			sbQuery.append("CASE WHEN F.Moneda = 'MXN' THEN (F.Importe * F.IVA + F.Importe)  /F.PDolar\n");
//			sbQuery.append("WHEN F.Moneda = 'Pesos' THEN(F.Importe * F.IVA + F.Importe) /F.PDolar\n");
//			sbQuery.append("WHEN F.Moneda = 'M.N.' THEN(F.Importe * F.IVA + F.Importe) /F.PDolar\n");
//			sbQuery.append("WHEN F.Moneda = 'USD' THEN (F.Importe * F.IVA + F.Importe)\n");
//			sbQuery.append("WHEN F.Moneda = 'Dolares' THEN (F.Importe * F.IVA + F.Importe)\n");
//			sbQuery.append("WHEN F.Moneda = 'EUR' THEN (F.Importe * F.IVA  +  F.Importe) * F.EDolar\n"); 
//			sbQuery.append("WHEN F.Moneda = 'Euros' THEN (F.Importe * F.IVA + F.Importe) * F.EDolar END\n");
//	   
//			sbQuery.append("WHEN F.Moneda = 'MXN' THEN(F.Importe * F.IVA  + F.Importe)/F.TCambio\n");
//			sbQuery.append("WHEN F.Moneda = 'Pesos' THEN(F.Importe * F.IVA  + F.Importe) /F.TCambio\n");
//			sbQuery.append("WHEN F.Moneda = 'M.N.' THEN(F.Importe * F.IVA  + F.Importe) /F.TCambio\n");
//			sbQuery.append("WHEN F.Moneda = 'Dolares' THEN(F.Importe * F.IVA + F.Importe )\n");
//			sbQuery.append("WHEN F.Moneda = 'USD' THEN(F.Importe * F.IVA + F.Importe)\n");
//			sbQuery.append("WHEN F.Moneda = 'Euros' THEN(F.Importe * F.IVA  + F.Importe) * F.TCambio\n"); 
//			sbQuery.append("WHEN F.Moneda = 'EUR' THEN(F.Importe * F.IVA + F.Importe)* F.TCambio  END\n");
//
//			sbQuery.append("ELSE CASE WHEN (F.Tcambio = 0 OR  F.Tcambio IS NULL) THEN\n");
//			sbQuery.append("CASE WHEN F.Moneda = 'MXN' THEN(F.saldoInsoluto  /F.PDolar)\n");
//			sbQuery.append("WHEN F.Moneda = 'Pesos' THEN(F.saldoInsoluto  /F.PDolar)\n");
//			sbQuery.append("WHEN F.Moneda = 'M.N.' THEN(F.saldoInsoluto  /F.PDolar)\n");
//			sbQuery.append("WHEN F.Moneda = 'Dolares' THEN(F.saldoInsoluto )\n");
//			sbQuery.append("WHEN F.Moneda = 'USD' THEN(F.saldoInsoluto ) \n");
//			sbQuery.append("WHEN F.Moneda = 'Euros' THEN(F.saldoInsoluto * F.EDolar)\n"); 
//			sbQuery.append("WHEN F.Moneda = 'EUR' THEN(F.saldoInsoluto * F.EDolar) END\n");
//	        
//			sbQuery.append("WHEN F.Moneda = 'MXN' THEN(F.saldoInsoluto /F.TCambio)\n");
//			sbQuery.append("WHEN F.Moneda = 'Pesos' THEN(F.saldoInsoluto /F.TCambio)\n");
//			sbQuery.append("WHEN F.Moneda = 'M.N.' THEN(F.saldoInsoluto /F.TCambio)\n");
//			sbQuery.append("WHEN F.Moneda = 'Dolares' THEN(F.saldoInsoluto )\n");
//			sbQuery.append("WHEN F.Moneda = 'USD' THEN(F.saldoInsoluto )\n");
//			sbQuery.append("WHEN F.Moneda = 'Euros' THEN(F.saldoInsoluto * F.TCambio)\n");
//			sbQuery.append("WHEN F.Moneda = 'EUR' THEN(F.saldoInsoluto * F.TCambio) END END,\n");
//			sbQuery.append("LEN(CASE WHEN F.saldoInsoluto IS NULL THEN \n");
//			sbQuery.append("CASE WHEN (F.Tcambio = 0 OR  F.Tcambio IS NULL) THEN\n"); 
//			sbQuery.append("CASE WHEN F.Moneda = 'MXN' THEN(F.saldoInsoluto * F.IVA + F.saldoInsoluto)  /F.PDolar\n");
//			sbQuery.append("WHEN F.Moneda = 'Pesos' THEN(F.Importe * F.IVA + F.Importe) /F.PDolar\n");
//			sbQuery.append("WHEN F.Moneda = 'M.N.' THEN(F.Importe * F.IVA + F.Importe) /F.PDolar\n");
//			sbQuery.append("WHEN F.Moneda = 'USD' THEN(F.Importe * F.IVA + F.Importe)\n");
//			sbQuery.append("WHEN F.Moneda = 'Dolares' THEN(F.Importe * F.IVA + F.Importe)\n");
//			sbQuery.append("WHEN F.Moneda = 'EUR' THEN(F.Importe * F.IVA  +  F.Importe) * F.EDolar\n");
//			sbQuery.append("WHEN F.Moneda = 'Euros' THEN(F.Importe * F.IVA + F.Importe) * F.EDolar END\n");
//			sbQuery.append("WHEN F.Moneda = 'MXN' THEN(F.Importe * F.IVA  + F.Importe)/F.TCambio\n");
//			sbQuery.append("WHEN F.Moneda = 'Pesos' THEN(F.Importe * F.IVA  + F.Importe) /F.TCambio\n");
//			sbQuery.append("WHEN F.Moneda = 'M.N.' THEN(F.Importe * F.IVA  + F.Importe) /F.TCambio\n");
//			sbQuery.append("WHEN F.Moneda = 'Dolares' THEN(F.Importe * F.IVA + F.Importe)\n");
//			sbQuery.append("WHEN F.Moneda = 'USD' THEN(F.Importe * F.IVA + F.Importe)\n");
//			sbQuery.append("WHEN F.Moneda = 'Euros' THEN(F.Importe * F.IVA  + F.Importe) * F.TCambio\n"); 
//			sbQuery.append("WHEN F.Moneda = 'EUR' THEN(F.Importe * F.IVA + F.Importe) * F.TCambio  END\n");
//			sbQuery.append("ELSE CASE WHEN (F.Tcambio = 0 OR  F.Tcambio IS NULL) THEN\n");
//			sbQuery.append("CASE WHEN F.Moneda = 'MXN' THEN(F.saldoInsoluto  /F.PDolar)\n");
//			sbQuery.append("WHEN F.Moneda = 'Pesos' THEN(F.saldoInsoluto  /F.PDolar)\n");
//			sbQuery.append("WHEN F.Moneda = 'M.N.' THEN(F.saldoInsoluto  /F.PDolar)\n");
//			sbQuery.append("WHEN F.Moneda = 'Dolares' THEN(F.saldoInsoluto )\n");
//			sbQuery.append("WHEN F.Moneda = 'USD' THEN(F.saldoInsoluto ) \n");
//			sbQuery.append("WHEN F.Moneda = 'Euros' THEN(F.saldoInsoluto * F.EDolar)\n"); 
//			sbQuery.append("WHEN F.Moneda = 'EUR' THEN(F.saldoInsoluto * F.EDolar) END \n"); 
//			    
//			sbQuery.append("WHEN F.Moneda = 'MXN' THEN(F.saldoInsoluto /F.TCambio) \n");
//			sbQuery.append("WHEN F.Moneda = 'Pesos' THEN(F.saldoInsoluto /F.TCambio)\n");
//			sbQuery.append("WHEN F.Moneda = 'M.N.' THEN(F.saldoInsoluto /F.TCambio)\n");
//			sbQuery.append("WHEN F.Moneda = 'Dolares' THEN(F.saldoInsoluto )\n");
//			sbQuery.append("WHEN F.Moneda = 'USD' THEN(F.saldoInsoluto )\n");
//			sbQuery.append("WHEN F.Moneda = 'Euros' THEN(F.saldoInsoluto * F.TCambio)\n");
//			sbQuery.append("WHEN F.Moneda = 'EUR' THEN(F.saldoInsoluto * F.TCambio) END END) + 15, 2 ) AS ImporteUSD,\n");
//			
//
//			
//			sbQuery.append("CASE WHEN F.Moneda = 'USD' OR F.Moneda = 'Dolares' THEN(\n");
//			sbQuery.append("STR(CASE WHEN F.saldoInsoluto IS NULL  THEN  F.Importe * F.IVA + Importe\n");
//			sbQuery.append("ELSE  F.saldoInsoluto END, LEN(CASE WHEN F.saldoInsoluto IS NULL  THEN  F.Importe * F.IVA + Importe\n");
//			sbQuery.append("ELSE  F.saldoInsoluto END ) + 15, 2) * F.TCambio )\n");
//			sbQuery.append("WHEN F.Moneda = 'MXN' OR F.Moneda = 'Pesos' OR F.Moneda = 'M.N.' THEN(\n");
//			sbQuery.append("STR(CASE WHEN F.saldoInsoluto IS NULL  THEN  F.Importe * F.IVA + Importe\n");
//			sbQuery.append("ELSE  F.saldoInsoluto END, LEN(CASE WHEN F.saldoInsoluto IS NULL  THEN  F.Importe * F.IVA + Importe\n");
//			sbQuery.append("ELSE  F.saldoInsoluto END ) + 15, 2))END AS ImporteM,\n");
//			
//			sbQuery.append("STR(CASE WHEN F.saldoInsoluto IS NULL THEN\n");
//			sbQuery.append("F.Importe * F.IVA + Importe \n"); 		
//			sbQuery.append("ELSE  F.saldoInsoluto END, LEN( CASE WHEN F.saldoInsoluto IS NULL THEN\n");
//			sbQuery.append("F.Importe * F.IVA + Importe \n");		
//			sbQuery.append("ELSE  F.saldoInsoluto END) + 15, 2 ) AS MonedaO,F.Fpor, F.DRC,\n");
//			
//			sbQuery.append("STR(CASE WHEN (F.Tcambio = 0 OR  F.Tcambio IS NULL) THEN\n");
//			sbQuery.append("CASE WHEN F.Moneda = 'MXN' THEN 0\n");
//			sbQuery.append("WHEN F.Moneda = 'Pesos' THEN 0\n");
//			sbQuery.append("WHEN F.Moneda = 'M.N.' THEN 0\n");
//			sbQuery.append("WHEN F.Moneda = 'USD' THEN F.Pdolar\n");	
//			sbQuery.append("WHEN F.Moneda = 'Dolares' THEN F.Pdolar\n");
//			sbQuery.append("WHEN F.Moneda = 'EUR' THEN  F.EDolar \n");
//			sbQuery.append("WHEN F.Moneda = 'Euros' THEN  F.EDolar END\n"); 
//			sbQuery.append("ELSE  CASE WHEN F.Moneda = 'MXN' THEN 0\n");
//			sbQuery.append("WHEN F.Moneda = 'Pesos' THEN 0\n");
//			sbQuery.append("WHEN F.Moneda = 'M.N.' THEN 0 \n");
//			sbQuery.append("ELSE F.Tcambio  END END, LEN(CASE WHEN (F.Tcambio = 0 OR  F.Tcambio IS NULL) THEN\n");
//			sbQuery.append("CASE WHEN F.Moneda = 'MXN' THEN 0\n");
//			sbQuery.append("WHEN F.Moneda = 'Pesos' THEN 0\n");
//			sbQuery.append("WHEN F.Moneda = 'M.N.' THEN 0\n");
//			sbQuery.append("WHEN F.Moneda = 'USD' THEN F.Pdolar\n");	
//			sbQuery.append("WHEN F.Moneda = 'Dolares' THEN F.Pdolar\n");
//			sbQuery.append("WHEN F.Moneda = 'EUR' THEN  F.EDolar \n");
//			sbQuery.append("WHEN F.Moneda = 'Euros' THEN  F.EDolar END\n"); 
//			sbQuery.append("ELSE  CASE WHEN F.Moneda = 'MXN' THEN 0\n");
//			sbQuery.append("WHEN F.Moneda = 'Pesos' THEN 0\n");
//			sbQuery.append("WHEN F.Moneda = 'M.N.' THEN 0 \n");
//			sbQuery.append("ELSE F.Tcambio  END END  ) + 15, 2 ) AS TCambio\n");
//			
//			sbQuery.append("FROM\n");
//			sbQuery.append("(SELECT F.Factura, F.CPedido as PInterno, F.CPago, F.Fecha, F.FRevision, F.CRecibo, PP.Monto, PP.FEPago, F.Moneda,  DATEDIFF(day, GETDATE(), PP.FEPago) AS DRC, FDR.MonedaDR,\n");
//			sbQuery.append("(SELECT TOP 1 MIN(FD.ImpSaldoinsoluto) AS saldoInsoluto  FROM factura_DocRel FD \n");
//			sbQuery.append("LEFT JOIN (SELECT MAX(Factura) AS Factura, F.cliente  FROM Facturas F  \n");
//			sbQuery.append("LEFT JOIN (SELECT Cliente, MAX(Fecha) Fecha FROM Facturas \n");
//			sbQuery.append("WHERE Estado = 'Por Cobrar' \n");
//			sbQuery.append("GROUP BY Cliente) CL ON CL.Cliente = F.Cliente AND F.Fecha = CL.Fecha\n");  
//			sbQuery.append("WHERE F.cliente = :idUsuario \n");
//			sbQuery.append("GROUP BY F.Cliente, Factura) FA  ON FA.Factura COLLATE Modern_Spanish_CI_AS  = FD.idDocumento COLLATE Modern_Spanish_CI_AS\n");
//			sbQuery.append("WHERE folio COLLATE Modern_Spanish_CI_AS  = F.Factura COLLATE Modern_Spanish_CI_AS \n");
//			sbQuery.append("GROUP BY FD.Folio,  FD.ImpSaldoAnt, FD.ImpPagado, FD.ImpSaldoinsoluto, FA.Factura) AS saldoInsoluto, F.Importe, FE.Total, M.PDolar, M.EDolar, f.TCambio, F.IVA, F.FPor\n"); 
//			sbQuery.append("FROM Facturas  F\n");
//			sbQuery.append("LEFT JOIN (SELECT MonedaDR, Folio FROM Factura_DocRel) FDR ON FDR.folio		COLLATE Modern_Spanish_CI_AS = F.Factura COLLATE Modern_Spanish_CI_AS\n");
//			sbQuery.append("LEFT JOIN  (SELECT * FROM  facturaElectronica) FE ON FE.Folio COLLATE Modern_Spanish_CI_AS = F.Factura COLLATE Modern_Spanish_CI_AS\n");
//			sbQuery.append("LEFT JOIN (SELECT * FROM Monedas ) M ON M.Fecha = f.Fecha  OR M.Fecha = FE.Fecha \n");
//			sbQuery.append("LEFT JOIN  (SELECT * FROM Pendiente) P ON P.Docto  = f.Factura AND p.Partida = F.Fpor \n"); 
//			sbQuery.append("LEFT JOIN (SELECT  FEPago, Medio, Monto, Moneda, QuienFactura, Factura FROM PagoPendiente GROUP BY FEPago, Medio, Monto, Moneda, QuienFactura, Factura) AS PP ON (PP.QuienFactura = F.FPor AND  PP.Factura = P.Docto)\n"); 
//			sbQuery.append("WHERE F.cliente = :idUsuario AND \n");
//			sbQuery.append("F.Estado = 'Por Cobrar' AND P.Tipo = 'Monitorear cobro' AND p.FFIN IS NULL AND F.Desistema = 1  ) F\n"); 
			
			
			
			
			
			sbQuery.append("SELECT F.Factura, F.PInterno,F.CPago, f.Fecha, F.FRevision, F.CRecibo, F.FEPago, F.Moneda,\n"); 
			sbQuery.append("STR(CASE WHEN F.saldoInsoluto IS NULL THEN \n"); 
			sbQuery.append("CASE WHEN (F.Tcambio = 0 OR  F.Tcambio IS NULL) THEN\n"); 

			sbQuery.append("CASE WHEN F.Moneda = 'MXN' THEN (F.Importe * F.IVA + F.Importe)  /F.PDolar\n"); 
			sbQuery.append("WHEN F.Moneda = 'Pesos' THEN(F.Importe * F.IVA + F.Importe) /F.PDolar\n"); 
			sbQuery.append("WHEN F.Moneda = 'M.N.' THEN(F.Importe * F.IVA + F.Importe) /F.PDolar\n"); 
			sbQuery.append("WHEN F.Moneda = 'USD' THEN (F.Importe * F.IVA + F.Importe)\n"); 
			sbQuery.append("WHEN F.Moneda = 'Dolares' THEN (F.Importe * F.IVA + F.Importe)\n"); 
			sbQuery.append("WHEN F.Moneda = 'EUR' THEN (F.Importe * F.IVA  +  F.Importe) * F.EDolar\n"); 
			sbQuery.append("WHEN F.Moneda = 'Euros' THEN (F.Importe * F.IVA + F.Importe) * F.EDolar END\n"); 
	   
			sbQuery.append("WHEN F.Moneda = 'MXN' THEN(F.Importe * F.IVA  + F.Importe)/F.TCambio\n"); 
			sbQuery.append("WHEN F.Moneda = 'Pesos' THEN(F.Importe * F.IVA  + F.Importe) /F.TCambio\n"); 
			sbQuery.append("WHEN F.Moneda = 'M.N.' THEN(F.Importe * F.IVA  + F.Importe) /F.TCambio\n"); 
			sbQuery.append("WHEN F.Moneda = 'Dolares' THEN(F.Importe * F.IVA + F.Importe )\n"); 
			sbQuery.append("WHEN F.Moneda = 'USD' THEN(F.Importe * F.IVA + F.Importe)\n"); 
			sbQuery.append("WHEN F.Moneda = 'Euros' THEN(F.Importe * F.IVA  + F.Importe) * F.TCambio\n"); 
			sbQuery.append("WHEN F.Moneda = 'EUR' THEN(F.Importe * F.IVA + F.Importe)* F.TCambio  END\n"); 

			sbQuery.append("ELSE CASE WHEN (F.Tcambio = 0 OR  F.Tcambio IS NULL) THEN\n"); 
			sbQuery.append("CASE WHEN F.Moneda = 'MXN' THEN(F.saldoInsoluto  /F.PDolar)\n"); 
			sbQuery.append("WHEN F.Moneda = 'Pesos' THEN(F.saldoInsoluto  /F.PDolar)\n"); 
			sbQuery.append("WHEN F.Moneda = 'M.N.' THEN(F.saldoInsoluto  /F.PDolar)\n"); 
			sbQuery.append("WHEN F.Moneda = 'Dolares' THEN(F.saldoInsoluto )\n"); 
			sbQuery.append("WHEN F.Moneda = 'USD' THEN(F.saldoInsoluto ) \n"); 
			sbQuery.append("WHEN F.Moneda = 'Euros' THEN(F.saldoInsoluto * F.EDolar)\n"); 
			sbQuery.append("WHEN F.Moneda = 'EUR' THEN(F.saldoInsoluto * F.EDolar) END\n"); 
	        
			sbQuery.append("WHEN F.Moneda = 'MXN' THEN(F.saldoInsoluto /F.TCambio)\n"); 
			sbQuery.append("WHEN F.Moneda = 'Pesos' THEN(F.saldoInsoluto /F.TCambio)\n"); 
			sbQuery.append("WHEN F.Moneda = 'M.N.' THEN(F.saldoInsoluto /F.TCambio)\n"); 
			sbQuery.append("WHEN F.Moneda = 'Dolares' THEN(F.saldoInsoluto )\n"); 
			sbQuery.append("WHEN F.Moneda = 'USD' THEN(F.saldoInsoluto )\n"); 
			sbQuery.append("WHEN F.Moneda = 'Euros' THEN(F.saldoInsoluto * F.TCambio)\n"); 
			sbQuery.append("WHEN F.Moneda = 'EUR' THEN(F.saldoInsoluto * F.TCambio) END END,\n"); 
			sbQuery.append("LEN(CASE WHEN F.saldoInsoluto IS NULL THEN \n"); 
			sbQuery.append("CASE WHEN (F.Tcambio = 0 OR  F.Tcambio IS NULL) THEN\n"); 
			sbQuery.append("CASE WHEN F.Moneda = 'MXN' THEN(F.saldoInsoluto * F.IVA + F.saldoInsoluto)  /F.PDolar\n"); 
			sbQuery.append("WHEN F.Moneda = 'Pesos' THEN(F.Importe * F.IVA + F.Importe) /F.PDolar\n"); 
			sbQuery.append("WHEN F.Moneda = 'M.N.' THEN(F.Importe * F.IVA + F.Importe) /F.PDolar\n"); 
			sbQuery.append("WHEN F.Moneda = 'USD' THEN(F.Importe * F.IVA + F.Importe)\n"); 
			sbQuery.append("WHEN F.Moneda = 'Dolares' THEN(F.Importe * F.IVA + F.Importe)\n"); 
			sbQuery.append("WHEN F.Moneda = 'EUR' THEN(F.Importe * F.IVA  +  F.Importe) * F.EDolar\n"); 
			sbQuery.append("WHEN F.Moneda = 'Euros' THEN(F.Importe * F.IVA + F.Importe) * F.EDolar END\n"); 
			sbQuery.append("WHEN F.Moneda = 'MXN' THEN(F.Importe * F.IVA  + F.Importe)/F.TCambio\n"); 
			sbQuery.append("WHEN F.Moneda = 'Pesos' THEN(F.Importe * F.IVA  + F.Importe) /F.TCambio\n"); 
			sbQuery.append("WHEN F.Moneda = 'M.N.' THEN(F.Importe * F.IVA  + F.Importe) /F.TCambio\n"); 
			sbQuery.append("WHEN F.Moneda = 'Dolares' THEN(F.Importe * F.IVA + F.Importe)\n"); 
			sbQuery.append("WHEN F.Moneda = 'USD' THEN(F.Importe * F.IVA + F.Importe)\n"); 
			sbQuery.append("WHEN F.Moneda = 'Euros' THEN(F.Importe * F.IVA  + F.Importe) * F.TCambio\n"); 
			sbQuery.append("WHEN F.Moneda = 'EUR' THEN(F.Importe * F.IVA + F.Importe) * F.TCambio  END\n"); 
			sbQuery.append("ELSE CASE WHEN (F.Tcambio = 0 OR  F.Tcambio IS NULL) THEN\n"); 
			sbQuery.append("CASE WHEN F.Moneda = 'MXN' THEN(F.saldoInsoluto  /F.PDolar)\n"); 
			sbQuery.append("WHEN F.Moneda = 'Pesos' THEN(F.saldoInsoluto  /F.PDolar)\n"); 
			sbQuery.append("WHEN F.Moneda = 'M.N.' THEN(F.saldoInsoluto  /F.PDolar)\n"); 
			sbQuery.append("WHEN F.Moneda = 'Dolares' THEN(F.saldoInsoluto )\n"); 
			sbQuery.append("WHEN F.Moneda = 'USD' THEN(F.saldoInsoluto ) \n"); 
			sbQuery.append("WHEN F.Moneda = 'Euros' THEN(F.saldoInsoluto * F.EDolar)\n"); 
			sbQuery.append("WHEN F.Moneda = 'EUR' THEN(F.saldoInsoluto * F.EDolar) END \n"); 
			    
			sbQuery.append("WHEN F.Moneda = 'MXN' THEN(F.saldoInsoluto /F.TCambio) \n"); 
			sbQuery.append("WHEN F.Moneda = 'Pesos' THEN(F.saldoInsoluto /F.TCambio)\n"); 
			sbQuery.append("WHEN F.Moneda = 'M.N.' THEN(F.saldoInsoluto /F.TCambio)\n"); 
			sbQuery.append("WHEN F.Moneda = 'Dolares' THEN(F.saldoInsoluto )\n"); 
			sbQuery.append("WHEN F.Moneda = 'USD' THEN(F.saldoInsoluto )\n"); 
			sbQuery.append("WHEN F.Moneda = 'Euros' THEN(F.saldoInsoluto * F.TCambio)\n"); 
			sbQuery.append("WHEN F.Moneda = 'EUR' THEN(F.saldoInsoluto * F.TCambio) END END) + 15, 2 ) AS ImporteUSD,\n"); 

			
			sbQuery.append("STR(CASE WHEN  F.saldoInsoluto IS NULL THEN \n"); 
			sbQuery.append("CASE WHEN (F.Tcambio = 0 OR  F.Tcambio IS NULL) THEN\n");  
			sbQuery.append("CASE WHEN  (F.Moneda = 'USD' OR  F.Moneda = 'Dolares' OR F.Moneda = 'Dolar')  THEN (F.Importe * F.IVA + F.Importe)  *  F.PDolar\n"); 
			sbQuery.append("WHEN (F.Moneda = 'EUR' OR F.Moneda = 'Euros')  THEN ((F.importe * F.IVA + F.Importe) * F.EDolar) * F.PDolar  \n"); 
			sbQuery.append("WHEN (F.Moneda = 'MN' OR F.Moneda = 'Pesos' OR F.Moneda = 'M.N.' OR F.Moneda = 'MXN') THEN (F.importe * F.IVA + F.Importe)  END\n"); 
			sbQuery.append("ELSE \n"); 
			sbQuery.append("CASE WHEN  (F.Moneda = 'USD' OR  F.Moneda = 'Dolares' OR F.Moneda = 'Dolar')  THEN (F.importe * F.IVA + F.Importe) * F.TCambio\n");   
			sbQuery.append("WHEN (F.Moneda = 'EUR' OR F.Moneda = 'Euros')  THEN ((F.importe * F.IVA + F.Importe)  * F.TCambio)  * F.Pdolar \n"); 
			sbQuery.append("WHEN (F.Moneda = 'MXN' OR F.Moneda = 'Pesos' OR F.Moneda = 'M.N.'  OR F.Moneda = 'MN') THEN (F.importe * F.IVA + F.Importe) END END\n"); 
			sbQuery.append("ELSE \n"); 
			sbQuery.append("CASE WHEN (F.Tcambio = 0 OR  F.Tcambio IS NULL) THEN\n");  
			sbQuery.append("CASE WHEN  (F.MonedaDR = 'USD' OR  F.MonedaDR = 'Dolares' OR F.MonedaDR = 'Dolar')  THEN F.saldoInsoluto *  F.PDolar\n"); 
			sbQuery.append("WHEN (F.MonedaDR = 'EUR' OR F.MonedaDR = 'Euros')  THEN (F.saldoInsoluto * F.EDolar) * F.PDolar  \n"); 
			sbQuery.append("WHEN (F.MonedaDR = 'MN' OR F.MonedaDR = 'Pesos' OR F.MonedaDR = 'M.N.' OR F.MonedaDR = 'MXN') THEN F.saldoInsoluto  END\n"); 
			sbQuery.append("ELSE \n"); 
			sbQuery.append("CASE WHEN  (F.MonedaDR = 'USD' OR  F.MonedaDR = 'Dolares' OR F.MonedaDR = 'Dolar')  THEN F.saldoInsoluto * F.TCambio\n");   
			sbQuery.append("WHEN (F.MonedaDR = 'EUR' OR F.MonedaDR = 'Euros')  THEN (F.saldoInsoluto  * F.TCambio)  * F.Pdolar \n"); 
			sbQuery.append("WHEN (F.MonedaDR = 'MXN' OR F.MonedaDR = 'Pesos' OR F.MonedaDR = 'M.N.'  OR F.MonedaDR = 'MN') THEN F.saldoInsoluto END END END,\n");  
			sbQuery.append("LEN(CASE WHEN  F.saldoInsoluto IS NULL THEN \n"); 
			sbQuery.append("CASE WHEN (F.Tcambio = 0 OR  F.Tcambio IS NULL) THEN\n");  
			sbQuery.append("CASE WHEN  (F.Moneda = 'USD' OR  F.Moneda = 'Dolares' OR F.Moneda = 'Dolar')  THEN (F.Importe * F.IVA + F.Importe)  *  F.PDolar\n"); 
			sbQuery.append("WHEN (F.Moneda = 'EUR' OR F.Moneda = 'Euros')  THEN ((F.importe * F.IVA + F.Importe) * F.EDolar) * F.PDolar  \n"); 
			sbQuery.append("WHEN (F.Moneda = 'MN' OR F.Moneda = 'Pesos' OR F.Moneda = 'M.N.' OR F.Moneda = 'MXN') THEN (F.importe * F.IVA + F.Importe)  END\n"); 
			sbQuery.append("ELSE \n"); 
			sbQuery.append("CASE WHEN  (F.Moneda = 'USD' OR  F.Moneda = 'Dolares' OR F.Moneda = 'Dolar')  THEN (F.importe * F.IVA + F.Importe) * F.TCambio\n");   
			sbQuery.append("WHEN (F.Moneda = 'EUR' OR F.Moneda = 'Euros')  THEN ((F.importe * F.IVA + F.Importe)  * F.TCambio)  * F.Pdolar \n"); 
			sbQuery.append("WHEN (F.Moneda = 'MXN' OR F.Moneda = 'Pesos' OR F.Moneda = 'M.N.'  OR F.Moneda = 'MN') THEN (F.importe * F.IVA + F.Importe) END END\n"); 
			sbQuery.append("ELSE \n"); 
			sbQuery.append("CASE WHEN (F.Tcambio = 0 OR  F.Tcambio IS NULL) THEN\n");  
			sbQuery.append("CASE WHEN  (F.MonedaDR = 'USD' OR  F.MonedaDR = 'Dolares' OR F.MonedaDR = 'Dolar')  THEN F.saldoInsoluto *  F.PDolar\n"); 
			sbQuery.append("WHEN (F.MonedaDR = 'EUR' OR F.MonedaDR = 'Euros')  THEN (F.saldoInsoluto * F.EDolar) * F.PDolar  \n"); 
			sbQuery.append("WHEN (F.MonedaDR = 'MN' OR F.MonedaDR = 'Pesos' OR F.MonedaDR = 'M.N.' OR F.MonedaDR = 'MXN') THEN F.saldoInsoluto  END\n"); 
			sbQuery.append("ELSE \n"); 
			sbQuery.append("CASE WHEN  (F.MonedaDR = 'USD' OR  F.MonedaDR = 'Dolares' OR F.MonedaDR = 'Dolar')  THEN F.saldoInsoluto * F.TCambio\n");   
			sbQuery.append("WHEN (F.MonedaDR = 'EUR' OR F.MonedaDR = 'Euros')  THEN (F.saldoInsoluto  * F.TCambio)  * F.Pdolar \n"); 
			sbQuery.append("WHEN (F.MonedaDR = 'MXN' OR F.MonedaDR = 'Pesos' OR F.MonedaDR = 'M.N.'  OR F.MonedaDR = 'MN') THEN F.saldoInsoluto END END END) + 15,2)  AS ImporteMP,\n"); 

	
			sbQuery.append("STR(CASE WHEN F.saldoInsoluto IS NULL THEN\n"); 
			sbQuery.append("F.Importe * F.IVA + Importe \n"); 
			sbQuery.append("ELSE  F.saldoInsoluto END, \n"); 
			sbQuery.append("LEN( CASE WHEN F.saldoInsoluto IS NULL THEN F.Importe * F.IVA + Importe ELSE  F.saldoInsoluto END) + 15, 2 ) AS MonedaO,F.Fpor, F.DRC,\n"); 
			
			sbQuery.append("STR(CASE WHEN (F.Tcambio = 0 OR  F.Tcambio IS NULL) THEN \n"); 
			sbQuery.append("CASE WHEN F.Moneda = 'MXN' THEN F.PDolar\n"); 
			sbQuery.append("WHEN F.Moneda = 'Pesos' THEN F.PDolar\n"); 
			sbQuery.append("WHEN F.Moneda = 'M.N.' THEN F.PDolar\n"); 
			sbQuery.append("WHEN F.Moneda = 'USD' THEN F.PDolar\n"); 
			sbQuery.append("WHEN F.Moneda = 'Dolar' THEN F.PDolar\n"); 
			sbQuery.append("WHEN F.Moneda = 'Dolares' THEN F.PDolar\n"); 
			sbQuery.append("WHEN F.Moneda = 'EUR' THEN  F.EDolar \n"); 
			sbQuery.append("WHEN F.Moneda = 'Euros' THEN  F.EDolar END\n"); 
			sbQuery.append("ELSE \n"); 
			sbQuery.append("CASE WHEN F.Moneda = 'MXN' THEN F.TCambio\n"); 
			sbQuery.append("WHEN F.Moneda = 'Pesos' THEN F.TCambio\n"); 
			sbQuery.append("WHEN F.Moneda = 'M.N.' THEN F.TCambio\n"); 
			sbQuery.append("WHEN F.Moneda = 'MN' THEN F.TCambio\n"); 
			sbQuery.append("WHEN F.Moneda = 'USD' THEN F.TCambio\n"); 
			sbQuery.append("WHEN F.Moneda = 'Dolar' THEN F.TCambio\n"); 
			sbQuery.append("WHEN F.Moneda = 'Dolares' THEN F.TCambio\n"); 
			sbQuery.append("WHEN F.Moneda = 'EUR' THEN  F.TCambio \n"); 
			sbQuery.append("WHEN F.Moneda = 'Euros' THEN  F.TCambio  END END,\n");  
			sbQuery.append("LEN(CASE WHEN (F.Tcambio = 0 OR  F.Tcambio IS NULL) THEN\n");  
			sbQuery.append("CASE WHEN F.Moneda = 'MXN' THEN F.PDolar\n"); 
			sbQuery.append("WHEN F.Moneda = 'Pesos' THEN F.PDolar\n"); 
			sbQuery.append("WHEN F.Moneda = 'M.N.' THEN F.PDolar\n"); 
			sbQuery.append("WHEN F.Moneda = 'USD' THEN F.PDolar\n"); 
			sbQuery.append("WHEN F.Moneda = 'Dolar' THEN F.PDolar\n"); 
			sbQuery.append("WHEN F.Moneda = 'Dolares' THEN F.PDolar\n"); 
			sbQuery.append("WHEN F.Moneda = 'EUR' THEN  F.EDolar \n"); 
			sbQuery.append("WHEN F.Moneda = 'Euros' THEN  F.EDolar END\n"); 
			sbQuery.append("ELSE \n"); 
			sbQuery.append("CASE WHEN F.Moneda = 'MXN' THEN F.TCambio\n"); 
			sbQuery.append("WHEN F.Moneda = 'Pesos' THEN F.TCambio\n"); 
			sbQuery.append("WHEN F.Moneda = 'M.N.' THEN F.TCambio\n"); 
			sbQuery.append("WHEN F.Moneda = 'MN' THEN F.TCambio\n"); 
			sbQuery.append("WHEN F.Moneda = 'USD' THEN F.TCambio\n"); 
			sbQuery.append("WHEN F.Moneda = 'Dolar' THEN F.TCambio\n"); 
			sbQuery.append("WHEN F.Moneda = 'Dolares' THEN F.TCambio\n"); 
			sbQuery.append("WHEN F.Moneda = 'EUR' THEN  F.TCambio \n"); 
			sbQuery.append("WHEN F.Moneda = 'Euros' THEN  F.TCambio  END END) + 15, 2)  AS TCambio,\n"); 
			
			sbQuery.append("STR(CASE WHEN (F.Tcambio = 0 OR F.Tcambio IS NULL) THEN\n");  
			sbQuery.append("CASE WHEN F.Moneda = 'EUR' THEN  F.EDolar\n"); 
			sbQuery.append("WHEN F.Moneda = 'Euros' THEN  F.EDolar END\n"); 
			sbQuery.append("ELSE \n"); 
			sbQuery.append("CASE WHEN F.Moneda = 'EUR' THEN  F.TCambio\n"); 
			sbQuery.append("WHEN F.Moneda = 'Euros' THEN  F.TCambio END  END,\n");  
			sbQuery.append("LEN(CASE WHEN (F.Tcambio = 0 OR F.Tcambio IS NULL) THEN\n");  
			sbQuery.append("CASE WHEN F.Moneda = 'EUR' THEN  F.EDolar\n"); 
			sbQuery.append("WHEN F.Moneda = 'Euros' THEN  F.EDolar END\n"); 
			sbQuery.append("ELSE \n"); 
			sbQuery.append("CASE WHEN F.Moneda = 'EUR' THEN  F.TCambio\n"); 
			sbQuery.append("WHEN F.Moneda = 'Euros' THEN  F.TCambio END  END) + 15, 2) AS ETCambio\n"); 
			sbQuery.append("FROM\n"); 
			sbQuery.append("(SELECT F.Factura, F.CPedido as PInterno, F.CPago, F.Fecha, F.FRevision, F.CRecibo, PP.Monto, PP.FEPago, F.Moneda,  DATEDIFF(day, GETDATE(), PP.FEPago) AS DRC, FDR.MonedaDR,\n"); 
			sbQuery.append("(SELECT TOP 1 MIN(FD.ImpSaldoinsoluto) AS saldoInsoluto  FROM factura_DocRel FD \n"); 
			sbQuery.append("LEFT JOIN (SELECT MAX(Factura) AS Factura, F.cliente  FROM Facturas F  \n"); 
			sbQuery.append("LEFT JOIN (SELECT Cliente, MAX(Fecha) Fecha FROM Facturas \n"); 
			sbQuery.append("WHERE Estado = 'Por Cobrar' \n"); 
			sbQuery.append("GROUP BY Cliente) CL ON CL.Cliente = F.Cliente AND F.Fecha = CL.Fecha\n");  
			sbQuery.append("WHERE F.cliente = :idUsuario \n"); 
			sbQuery.append("GROUP BY F.Cliente, Factura) FA  ON FA.Factura COLLATE Modern_Spanish_CI_AS  = FD.idDocumento COLLATE Modern_Spanish_CI_AS\n"); 
			sbQuery.append("WHERE folio COLLATE Modern_Spanish_CI_AS  = F.Factura COLLATE Modern_Spanish_CI_AS \n"); 
			sbQuery.append("GROUP BY FD.Folio,  FD.ImpSaldoAnt, FD.ImpPagado, FD.ImpSaldoinsoluto, FA.Factura) AS saldoInsoluto, F.Importe, FE.Total, M.PDolar, M.EDolar, f.TCambio, F.IVA, F.FPor\n"); 
			sbQuery.append("FROM Facturas  F\n"); 
			sbQuery.append("LEFT JOIN (SELECT MonedaDR, Folio FROM Factura_DocRel) FDR ON FDR.folio			COLLATE Modern_Spanish_CI_AS = F.Factura COLLATE Modern_Spanish_CI_AS\n"); 
			sbQuery.append("LEFT JOIN  (SELECT * FROM  facturaElectronica) FE ON FE.Folio COLLATE Modern_Spanish_CI_AS = F.Factura COLLATE Modern_Spanish_CI_AS\n"); 
			sbQuery.append("LEFT JOIN (SELECT * FROM Monedas ) M ON M.Fecha = f.Fecha  OR M.Fecha = FE.Fecha \n"); 
			sbQuery.append("LEFT JOIN  (SELECT * FROM Pendiente) P ON P.Docto  = f.Factura AND p.Partida = F.Fpor \n"); 
			sbQuery.append("LEFT JOIN (SELECT  FEPago, Medio, Monto, Moneda, QuienFactura, Factura FROM PagoPendiente GROUP BY FEPago, Medio, Monto, Moneda, QuienFactura, Factura) AS PP ON (PP.QuienFactura = F.FPor AND  PP.Factura = P.Docto)\n"); 
			sbQuery.append("WHERE F.cliente = :idUsuario AND \n"); 
			sbQuery.append("F.Estado = 'Por Cobrar' AND P.Tipo = 'Monitorear cobro' AND p.FFIN IS NULL AND F.Desistema = 1\n");  
			sbQuery.append("--AND  DATEDIFF(day, GETDATE(), PP.FEPago) > = 0  \n"); 
			sbQuery.append(") F\n"); 

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("idUsuario", idCliente);
			Map<String, List<Factura>> mapReturn = new HashMap<String, List<Factura>>();
		
		    getJdbcTemplate().query(sbQuery.toString(),parametros ,new RowMapper() {
		    	   
		    	@Override
		           public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    		   Factura f = new Factura();
		    		   SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy", new java.util.Locale("es", "ES"));
//		    		   String tipo = rs.getString("etiqueta");
		    		   String empresa = rs.getString("Fpor");
		    		   try{	      
		    			   
		    				   f.setFacturarA(rs.getString("Factura"));
			    			   f.setPedido(rs.getString("PInterno"));
			    			   f.setcPago(rs.getString("CPago"));
			    			   if(rs.getString("fecha") != null ) {
			    				   f.setFechaFactura(dateFormat.format(rs.getDate("Fecha")));
			    			   }
			    			   if(rs.getString("FRevision") != null ) {
			    				   f.setFechaRevision(dateFormat.format(rs.getDate("FRevision")));
			    			   }		    	
			    			   f.setContraRecibo(rs.getString("CRecibo"));
			    			   if(rs.getString("FEPago") != null ) {
			    				   f.setFechaPago(dateFormat.format(rs.getDate("FEPago")));
			    			   }
			    			   f.setMoneda(rs.getString("Moneda"));  
			    			   f.setImporteUSD(Double.parseDouble(rs.getString("ImporteUSD")));
			    			   f.setImporteM(Double.parseDouble(rs.getString("ImporteMP")));
			    			   f.setMonedaO(rs.getDouble("MonedaO"));
			    			   f.setFacturadoPor(rs.getString("Fpor"));	    			   	    	
			    			   f.setDrc(rs.getInt("DRC"));
			    			   f.settCambio(rs.getDouble("TCambio"));
			    			   f.settCambioE(rs.getDouble("ETCambio"));

		    		   }catch (Exception e) {
		    			   e.toString();
		               }
		    		   if( empresa != null && empresa.equalsIgnoreCase("Proquifa") ){
		    			   if(mapReturn.get("Proquifa") != null){
		    				   mapReturn.get("Proquifa").add(f);
		    			   }
			    		   else {
			    			   List<Factura> list = new ArrayList<Factura>();
			    			   mapReturn.put("Proquifa", list );
			    			   mapReturn.get("Proquifa").add(f);
			    		   }
		    		   }
		    		   if(empresa != null && empresa.equalsIgnoreCase("Proveedora")){
		    			   if(mapReturn.get("Proveedora") != null){
		    				   mapReturn.get("Proveedora").add(f);
		    			   }
			    		   else {
			    			   List<Factura> list = new ArrayList<Factura>();
			    			   mapReturn.put("Proveedora", list );
			    			   mapReturn.get("Proveedora").add(f);
			    		   }
		    		   }
		    		   if(empresa != null && empresa.equalsIgnoreCase("Golocaer")){
		    			   if(mapReturn.get("Golocaer") != null){
		    				   mapReturn.get("Golocaer").add(f);
		    			   }
			    		   else {
			    			   List<Factura> list = new ArrayList<Factura>();
			    			   mapReturn.put("Golocaer", list );
			    			   mapReturn.get("Golocaer").add(f);
			    		   }
		    		   }
		    		   if(empresa != null && empresa.equalsIgnoreCase("Mungen")){
		    			   if(mapReturn.get("Mungen") != null){
		    				   mapReturn.get("Mungen").add(f);
		    			   }
			    		   else {
			    			   List<Factura> list = new ArrayList<Factura>();
			    			   mapReturn.put("Mungen", list );
			    			   mapReturn.get("Mungen").add(f);
			    		   }
		    		   }
		    		   if(empresa != null && empresa.equalsIgnoreCase("Pharma")){
		    			   if(mapReturn.get("Pharma") != null){
		    				   mapReturn.get("Pharma").add(f);
		    			   }
			    		   else {
			    			   List<Factura> list = new ArrayList<Factura>();
			    			   mapReturn.put("Pharma", list );
			    			   mapReturn.get("Pharma").add(f);
			    		   }
		    		   }

		    		 

		    		   return null;
		    	}    	   
		      });	

		    return mapReturn; 	 
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		} 	
	}
	
	
	
	@Override
	@SuppressWarnings("unchecked")
	public Map<String, List<NotaCredito>> obtenerNotas(Integer idCliente ) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n  ");

    		sbQuery.append("SELECT NC.FK02_Factura, NC.pk_Nota, STR(nc.TotalM, LEN(nc.TotalM) + 15,2) AS TotalM, NC.Moneda,  NC.Fecha,\n"); 
    		sbQuery.append("STR(nc.TDolar, LEN(nc.TDolar) + 15,2) AS TDolar,nc.ImportePesos,  NC.Fpor,  STR(NC.MCambio, LEN(MCambio) + 15,2) AS TCambio, NC.ETCambio\n");
    		sbQuery.append("FROM\n");
    		sbQuery.append("(SELECT nc.FK02_Factura, nc.Importe * nc.IVA + nc.Importe AS TotalM, nc.Moneda, nc.TCambio,nc.Fecha,f.Fpor, nc.pk_Nota,\n"); 
    		sbQuery.append("CASE WHEN (nc.Tcambio = 0 OR  nc.Tcambio IS NULL ) THEN \n");
    		sbQuery.append("CASE WHEN nc.Moneda = 'M.N.' THEN(nc.Importe * nc.IVA + nc.Importe) /m.PDolar\n");
    		sbQuery.append("WHEN nc.Moneda = 'Pesos' THEN(nc.Importe * nc.IVA + nc.Importe) /m.PDolar\n");
    		sbQuery.append("WHEN nc.Moneda = 'MN' THEN(nc.Importe * nc.IVA + nc.Importe) /m.PDolar\n");
    		sbQuery.append("WHEN nc.Moneda = 'Dolar' THEN(nc.Importe * nc.IVA + nc.Importe)\n");
    		sbQuery.append("WHEN nc.Moneda = 'Dolares' THEN(nc.Importe * nc.IVA + nc.Importe) END\n");
			 
			 
    		sbQuery.append("WHEN nc.Moneda = 'MN' THEN(nc.Importe * nc.IVA + nc.Importe) /nc.TCambio\n");
    		sbQuery.append("WHEN nc.Moneda = 'Pesos' THEN(nc.Importe * nc.IVA  + nc.Importe) /nc.TCambio\n");
    		sbQuery.append("WHEN nc.Moneda = 'M.N.' THEN(nc.Importe * nc.IVA  + nc.Importe) /nc.TCambio\n");
    		sbQuery.append("WHEN nc.Moneda = 'Dolares' THEN(nc.Importe * nc.IVA + nc.Importe )\n");
    		sbQuery.append("WHEN nc.Moneda = 'Dolar' THEN(nc.Importe * nc.IVA + nc.Importe)\n");
    		sbQuery.append("WHEN nc.Moneda = 'USD' THEN(nc.Importe * nc.IVA + nc.Importe) END AS TDolar,\n");
    		
    		sbQuery.append("STR(CASE WHEN (nc.Tcambio = 0 OR  nc.Tcambio IS NULL) THEN \n");
    		sbQuery.append("CASE WHEN  (nc.Moneda = 'USD' OR  nc.Moneda = 'Dolares' OR nc.Moneda = 'Dolar')  THEN (nc.importe * nc.IVA + nc.Importe)  *  M.PDolar\n");
    		sbQuery.append("WHEN (nc.Moneda = 'EUR' OR nc.Moneda = 'Euros')  THEN ((nc.importe * nc.IVA + nc.Importe) * M.EDolar) * M.PDolar  \n");
    		sbQuery.append("WHEN (nc.Moneda = 'MN' OR nc.Moneda = 'Pesos' OR nc.Moneda = 'M.N.' OR nc.Moneda = 'MXN') THEN (nc.importe * nc.IVA + nc.Importe) END\n"); 
    		sbQuery.append("ELSE \n");
    		sbQuery.append("CASE WHEN  (nc.Moneda = 'USD' OR  nc.Moneda = 'Dolares' OR nc.Moneda = 'Dolar')  THEN (nc.importe * nc.IVA + nc.Importe) * nc.TCambio\n");  
    		sbQuery.append("WHEN (nc.Moneda = 'EUR' OR nc.Moneda = 'Euros')  THEN ((nc.importe * nc.IVA + nc.Importe)  * nc.TCambio)  * M.Pdolar \n");
    		sbQuery.append("WHEN (nc.Moneda = 'MXN' OR nc.Moneda = 'Pesos' OR nc.Moneda = 'M.N.'  OR nc.Moneda = 'MN') THEN (nc.importe * nc.IVA + nc.Importe) END END,\n");  
    		sbQuery.append("LEN(CASE WHEN (nc.Tcambio = 0 OR  nc.Tcambio IS NULL) THEN \n");
    		sbQuery.append("CASE WHEN  (nc.Moneda = 'USD' OR  nc.Moneda = 'Dolares' OR nc.Moneda = 'Dolar')  THEN (nc.importe * nc.IVA + nc.Importe)  *  M.PDolar\n");
    		sbQuery.append("WHEN (nc.Moneda = 'EUR' OR nc.Moneda = 'Euros')  THEN ((nc.importe * nc.IVA + nc.Importe) * M.EDolar) * M.PDolar  \n");
    		sbQuery.append("WHEN (nc.Moneda = 'MXN' OR nc.Moneda = 'Pesos' OR nc.Moneda = 'M.N.' OR nc.Moneda = 'MN') THEN (nc.importe * nc.IVA + nc.Importe) END\n"); 
    		sbQuery.append("ELSE \n");
    		sbQuery.append("CASE WHEN  (nc.Moneda = 'USD' OR  nc.Moneda = 'Dolares' OR nc.Moneda = 'Dolar')  THEN (nc.importe * nc.IVA + nc.Importe) * nc.TCambio\n");  
    		sbQuery.append("WHEN (nc.Moneda = 'EUR' OR nc.Moneda = 'Euros')  THEN ((nc.importe * nc.IVA + nc.Importe)  * nc.TCambio)  * M.Pdolar \n");
    		sbQuery.append("WHEN (nc.Moneda = 'MXN' OR nc.Moneda = 'Pesos' OR nc.Moneda = 'M.N.' OR nc.Moneda = 'MN') THEN (nc.importe * nc.IVA + nc.Importe)  END END + 15 ), 2) AS ImportePesos,\n");
			
    		sbQuery.append("CASE WHEN (nc.Tcambio = 0 OR  nc.Tcambio IS NULL) THEN \n");
    		sbQuery.append("CASE WHEN nc.Moneda = 'MXN' THEN m.PDolar\n");
    		sbQuery.append("WHEN nc.Moneda = 'Pesos' THEN m.PDolar\n");
    		sbQuery.append("WHEN nc.Moneda = 'M.N.' THEN m.PDolar\n");
    		sbQuery.append("WHEN nc.Moneda = 'MN' THEN m.PDolar\n");
    		sbQuery.append("WHEN nc.Moneda = 'USD' THEN m.PDolar\n");
    		sbQuery.append("WHEN nc.Moneda = 'Dolar' THEN m.PDolar\n");
    		sbQuery.append("WHEN nc.Moneda = 'Dolares' THEN m.PDolar\n");
    		sbQuery.append("WHEN nc.Moneda = 'EUR' THEN  m.EDolar \n");
    		sbQuery.append("WHEN nc.Moneda = 'Euros' THEN  m.EDolar END\n");
    		sbQuery.append("ELSE \n");
    		sbQuery.append("CASE WHEN nc.Moneda = 'MXN' THEN nc.TCambio\n");
    		sbQuery.append("WHEN nc.Moneda = 'Pesos' THEN nc.TCambio\n");
    		sbQuery.append("WHEN nc.Moneda = 'M.N.' THEN nc.TCambio\n");
    		sbQuery.append("WHEN nc.Moneda = 'MN' THEN nc.TCambio\n");
    		sbQuery.append("WHEN nc.Moneda = 'USD' THEN nc.TCambio\n");
    		sbQuery.append("WHEN nc.Moneda = 'Dolar' THEN nc.TCambio\n");
    		sbQuery.append("WHEN nc.Moneda = 'Dolares' THEN nc.TCambio\n");
    		sbQuery.append("WHEN nc.Moneda = 'EUR' THEN  nc.TCambio \n");
    		sbQuery.append("WHEN nc.Moneda = 'Euros' THEN  nc.TCambio  END END AS MCambio,\n");
			
    		sbQuery.append("CASE WHEN (nc.Tcambio = 0 OR nc.Tcambio IS NULL) THEN \n");
    		sbQuery.append("CASE WHEN nc.Moneda = 'EUR' THEN  m.EDolar\n");
    		sbQuery.append("WHEN nc.Moneda = 'Euros' THEN  m.EDolar END\n");
    		sbQuery.append("ELSE \n");
    		sbQuery.append("CASE WHEN nc.Moneda = 'EUR' THEN  nc.TCambio\n");
    		sbQuery.append("WHEN nc.Moneda = 'Euros' THEN  nc.TCambio END  END AS ETCambio\n");
						 
    		sbQuery.append("FROM notaCredito nc \n");
    		sbQuery.append("INNER JOIN Facturas f ON nc.FK02_Factura  =  f.idFactura\n");
    		sbQuery.append("LEFT JOIN Monedas m ON nc.Fecha = m.Fecha \n");
    		sbQuery.append("WHERE nc.FK01_Cliente = :idCliente AND \n");
    		sbQuery.append("nc.Estado IS NULL  OR (nc.InicioPeriodo IS NOT NULL  AND DATEDIFF(day, GETDATE(), nc.FinPeriodo) > 0)) NC\n");  

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("idCliente", idCliente);
			 Map<String, List<NotaCredito>> mapReturn = new HashMap<String, List<NotaCredito>>();
				
			    getJdbcTemplate().query(sbQuery.toString(),parametros ,new RowMapper() {
			    	   
			    	@Override
			           public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			    		   SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy", new java.util.Locale("es", "ES"));
			    		   String empresa = rs.getString("Fpor");
			    		   NotaCredito n = new NotaCredito();
			    		   try{	      
			    				
			    				n.setFactura(rs.getLong("FK02_Factura"));
			    				n.setIdNota(rs.getLong("pk_Nota"));
			    				n.setmO(rs.getDouble("TotalM"));
			    				if(rs.getString("Moneda") != null && (rs.getString("Moneda").equalsIgnoreCase("Dolares") || rs.getString("Moneda").equalsIgnoreCase("Dolar") ) ) {
			    					n.setMoneda("USD");
			    				}else if (rs.getString("Moneda") != null &&(rs.getString("Moneda").equalsIgnoreCase("M.N.") || rs.getString("Moneda").equalsIgnoreCase("MN") || rs.getString("Moneda").equalsIgnoreCase("Pesos"))) {
			    					n.setMoneda("MXN");
			    				} else {
			    					n.setMoneda("EUR");
			    				}
			    				if(rs.getString("Fecha") != null ) 
			    					n.setFechaNota(dateFormat.format(rs.getDate("Fecha")));
			    				n.setmUSD(rs.getDouble("TDolar"));
			    				n.setmPesos(rs.getDouble("ImportePesos"));
			    				n.setFpor(rs.getString("Fpor"));
			    				if(rs.getDouble("TCambio") > 0 ) {
			    					n.setTCambio(rs.getDouble("TCambio"));
			    				}else {
			    					n.setTCambio(0.0);
			    				}
			    				if(rs.getDouble("ETCambio") > 0 )
			    					n.settCambioEuro(rs.getDouble("ETCambio"));
			    				else 
			    					n.settCambioEuro(0.0);

			    		   }catch (Exception e) {
			    			   e.toString();
			               }
			    		   if( empresa != null && empresa.equalsIgnoreCase("Proquifa") ){
			    			   if(mapReturn.get("Proquifa") != null){
			    				   mapReturn.get("Proquifa").add(n);
			    			   }
				    		   else {
				    			   List<NotaCredito> list = new ArrayList<NotaCredito>();
				    			   mapReturn.put("Proquifa", list );
				    			   mapReturn.get("Proquifa").add(n);
				    		   }
			    		   }
			    		   if(empresa != null && (empresa.equalsIgnoreCase("Proveedora") ||  empresa.equalsIgnoreCase("ProveedoraX"))){
			    			   if(mapReturn.get("Proveedora") != null){
			    				   mapReturn.get("Proveedora").add(n);
			    			   }
				    		   else {
				    			   List<NotaCredito> list = new ArrayList<NotaCredito>();
				    			   mapReturn.put("Proveedora", list );
				    			   mapReturn.get("Proveedora").add(n);
				    		   }
			    		   }
			    		   if(empresa != null && empresa.equalsIgnoreCase("Golocaer")){
			    			   if(mapReturn.get("Golocaer") != null){
			    				   mapReturn.get("Golocaer").add(n);
			    			   }
				    		   else {
				    			   List<NotaCredito> list = new ArrayList<NotaCredito>();
				    			   mapReturn.put("Golocaer", list );
				    			   mapReturn.get("Golocaer").add(n);
				    		   }
			    		   }
			    		   if(empresa != null && empresa.equalsIgnoreCase("Mungen")){
			    			   if(mapReturn.get("Mungen") != null){
			    				   mapReturn.get("Mungen").add(n);
			    			   }
				    		   else {
				    			   List<NotaCredito> list = new ArrayList<NotaCredito>();
				    			   mapReturn.put("Mungen", list );
				    			   mapReturn.get("Mungen").add(n);
				    		   }
			    		   }
			    		   if(empresa != null && empresa.equalsIgnoreCase("Pharma")){
			    			   if(mapReturn.get("Pharma") != null){
			    				   mapReturn.get("Pharma").add(n);
			    			   }
				    		   else {
				    			   List<NotaCredito> list = new ArrayList<NotaCredito>();
				    			   mapReturn.put("Pharma", list );
				    			   mapReturn.get("Pharma").add(n);
				    		   }
			    		   }

			    		   return null;
			    	}    	   
			      });	

			    return mapReturn; 	 
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	

	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })	
	public Map<String, List<Factura>> generarGraFicaCobro(Parametro parametro) throws ProquifaNetException{
		try{
			StringBuilder sbQuery = new StringBuilder(" \n");	
    		sbQuery.append("SELECT \n");
    		sbQuery.append("M.TotalPorCobrar,M.NumFacturas, M.idcliente,M.Nombre,M.FEPago,\n"); 
    		sbQuery.append("STR(	SUM(CASE WHEN (M.TcambioD > 0 OR  M.TcambioD IS NOT NULL) AND  M.MontoActual IS NOT NULL AND M.MontoActual > 0  THEN\n"); 
    		sbQuery.append("CASE WHEN M.monedaD = 'M.N.' THEN M.MontoActual / M.TcambioD \n");
    		sbQuery.append("WHEN M.monedaD = 'DLS' THEN M.MontoActual END ELSE 0 END ), \n");
    		sbQuery.append("LEN(	SUM(CASE WHEN (M.TcambioD > 0 OR  M.TcambioD IS NOT NULL) AND  M.MontoActual IS NOT NULL AND M.MontoActual > 0  THEN\n"); 
    		sbQuery.append("CASE WHEN M.monedaD = 'M.N.' THEN M.MontoActual / M.TcambioD \n");
    		sbQuery.append("WHEN M.monedaD = 'DLS' THEN M.MontoActual END ELSE 0 END )) + 15,2  ) AS Totalmonto, M.Etiqueta\n");
    		sbQuery.append("FROM (\n");
    		sbQuery.append("SELECT F.TotalPorCobrar, F.NumFacturas, F.idcliente,F.Nombre,F.FEPago,\n");  
    		sbQuery.append("COALESCE(M2.Pdolar,(SELECT PDolar FROM Monedas WHERE Fecha = ( SELECT MAX(Fecha) FROM Monedas))) AS TcambioD, CB.Moneda AS monedaD, D.MontoActual,\n");
    		sbQuery.append("CASE WHEN D.FK01_Cliente IS NOT NULL AND D.Activo = 1 THEN  'Con Deposito' \n");
    		sbQuery.append("ELSE 'Sin Deposito' END AS Etiqueta \n");
    		sbQuery.append("FROM (\n");
    		sbQuery.append("SELECT  \n");  
    		sbQuery.append("STR(SUM(CASE WHEN D.saldoInsoluto IS NULL THEN  D.ImporteUSD ELSE  D.ImporteUSD - D.saldoInsoluto END),\n"); 
    		sbQuery.append("LEN(SUM(CASE WHEN D.saldoInsoluto IS NULL THEN 0 ELSE D.saldoInsoluto END)) + 15,2  ) AS TotalPorCobrar,\n");
    		sbQuery.append("COUNT(D.Factura) AS NumFacturas,D.idcliente, D.Nombre, MIN(D.FEPago) AS FEPago\n");
    		sbQuery.append("FROM (\n");
    		sbQuery.append("SELECT F.idcliente,\n"); 
    		sbQuery.append("CASE WHEN F.saldoInsoluto IS NULL THEN\n"); 
    		sbQuery.append("CASE WHEN (F.Tcambio = 0 OR  F.Tcambio IS NULL) THEN\n"); 
    		sbQuery.append("CASE WHEN F.Moneda = 'MXN' THEN (F.Importe * F.IVA + F.Importe)  /F.PDolar\n");
    		sbQuery.append("WHEN F.Moneda = 'Pesos' THEN (F.Importe * F.IVA + F.Importe) /F.PDolar\n");
    		sbQuery.append("WHEN F.Moneda = 'M.N.' THEN (F.Importe * F.IVA + F.Importe) /F.PDolar\n");
    		sbQuery.append("WHEN F.Moneda = 'USD' THEN (F.Importe * F.IVA + F.Importe)\n");
    		sbQuery.append("WHEN F.Moneda = 'Dolares' THEN (F.Importe * F.IVA + F.Importe)\n");
    		sbQuery.append("WHEN F.Moneda = 'EUR' THEN (F.Importe * F.IVA  +  F.Importe) * F.EDolar\n"); 
    		sbQuery.append("WHEN F.Moneda = 'Euros' THEN (F.Importe * F.IVA + F.Importe) * F.EDolar END\n");

    		sbQuery.append(" WHEN F.Moneda = 'MXN' THEN (F.Importe * F.IVA  + F.Importe)/F.TCambio\n");
    		sbQuery.append("WHEN F.Moneda = 'Pesos' THEN (F.Importe * F.IVA  + F.Importe) /F.TCambio\n");
    		sbQuery.append("WHEN F.Moneda = 'M.N.' THEN (F.Importe * F.IVA  + F.Importe) /F.TCambio\n");
    		sbQuery.append("WHEN F.Moneda = 'Dolares' THEN (F.Importe * F.IVA + F.Importe )\n");
    		sbQuery.append("WHEN F.Moneda = 'USD' THEN (F.Importe * F.IVA + F.Importe)\n");
    		sbQuery.append("WHEN F.Moneda = 'Euros' THEN (F.Importe * F.IVA  + F.Importe) * F.TCambio\n"); 
    		sbQuery.append("WHEN F.Moneda = 'EUR' THEN (F.Importe * F.IVA + F.Importe) * F.TCambio  END\n");

    		sbQuery.append("ELSE CASE WHEN (F.Tcambio = 0 OR  F.Tcambio IS NULL) THEN\n");
    		sbQuery.append("CASE WHEN F.Moneda = 'MXN' THEN (F.saldoInsoluto  /F.PDolar)\n");
    		sbQuery.append("WHEN F.Moneda = 'Pesos' THEN (F.saldoInsoluto  /F.PDolar)\n");
    		sbQuery.append("WHEN F.Moneda = 'M.N.' THEN (F.saldoInsoluto  /F.PDolar)\n");
    		sbQuery.append("WHEN F.Moneda = 'Dolares' THEN (F.saldoInsoluto )\n");
    		sbQuery.append("WHEN F.Moneda = 'USD' THEN (F.saldoInsoluto ) \n");
    		sbQuery.append("WHEN F.Moneda = 'Euros' THEN (F.saldoInsoluto * F.EDolar)\n"); 
    		sbQuery.append("WHEN F.Moneda = 'EUR' THEN (F.saldoInsoluto * F.EDolar) END\n");
    
    		sbQuery.append("WHEN F.Moneda = 'MXN' THEN (F.saldoInsoluto /F.TCambio)\n");
    		sbQuery.append("WHEN F.Moneda = 'Pesos' THEN (F.saldoInsoluto /F.TCambio)\n");
    		sbQuery.append("WHEN F.Moneda = 'M.N.' THEN (F.saldoInsoluto /F.TCambio)\n");
    		sbQuery.append("WHEN F.Moneda = 'Dolares' THEN (F.saldoInsoluto )\n");
    		sbQuery.append("WHEN F.Moneda = 'USD' THEN (F.saldoInsoluto)\n");
    		sbQuery.append("WHEN F.Moneda = 'Euros' THEN (F.saldoInsoluto * F.EDolar)\n");
    		sbQuery.append("WHEN F.Moneda = 'EUR' THEN (F.saldoInsoluto * F.EDolar) END END AS ImporteUSD,\n");
    		sbQuery.append("F.saldoInsoluto, F.Nombre, F.FEPago,F.factura \n");
    		sbQuery.append("FROM (\n");
    		sbQuery.append("SELECT CL.Clave AS idCliente, F.Importe, FE.Total,F.Tcambio, F.Moneda,F.IVA, CL.Nombre, PP.FEPago, F.Factura,\n");
    		sbQuery.append("CASE WHEN f.Moneda = 'Euros' THEN  M.EDolar\n");
    		sbQuery.append("WHEN f.Moneda = 'EUR'  THEN M.EDolar\n");
    		sbQuery.append("ELSE NULL END  AS EDolar,\n");
    		sbQuery.append("CASE WHEN f.Moneda = 'Pesos' THEN M.PDolar\n");
    		sbQuery.append("WHEN f.Moneda = 'M.N.' THEN M.PDolar \n");
    		sbQuery.append("WHEN f.Moneda = 'MXN' THEN M.PDolar \n");
    		sbQuery.append("ELSE NULL END AS PDolar,\n");
    		sbQuery.append("(SELECT TOP 1 MIN(FD.ImpSaldoinsoluto) AS saldoInsoluto  FROM factura_DocRel FD\n");
    		sbQuery.append("LEFT JOIN (SELECT MAX(Factura) AS Factura, F.cliente  FROM Facturas F \n");
    		sbQuery.append("LEFT JOIN (SELECT Cliente, MAX(Fecha) Fecha FROM Facturas\n");
    		sbQuery.append("WHERE Estado = 'Por Cobrar'\n");
    		sbQuery.append("GROUP BY Cliente) CL ON CL.Cliente = F.Cliente AND F.Fecha = CL.Fecha\n"); 
    		sbQuery.append("GROUP BY F.Cliente, Factura) FA  ON FA.Factura COLLATE Modern_Spanish_CI_AS  = FD.idDocumento COLLATE Modern_Spanish_CI_AS\n");
    		sbQuery.append("WHERE folio COLLATE Modern_Spanish_CI_AS  = F.Factura COLLATE Modern_Spanish_CI_AS \n");
    		sbQuery.append("GROUP BY FD.Folio,  FD.ImpSaldoAnt, FD.ImpPagado, FD.ImpSaldoinsoluto, FA.Factura) AS saldoInsoluto\n");

    		sbQuery.append("FROM Facturas F\n");  
    		sbQuery.append("INNER JOIN  (SELECT Clave, Nombre FROM clientes) CL ON F.Cliente = CL.Clave\n");
    		sbQuery.append("LEFT JOIN (SELECT ImpPagado, idDocumento, ImpSaldoInsoluto FROM Factura_DocRel) FDR ON FDR.idDocumento COLLATE Modern_Spanish_CI_AS = F.Factura COLLATE Modern_Spanish_CI_AS\n");
    		sbQuery.append("LEFT JOIN (SELECT folio,  Moneda,  Total,  TipoCambio, Fecha  FROM FacturaElectronica ) FE ON  F.Factura COLLATE Modern_Spanish_CI_AS =  FE.Folio COLLATE Modern_Spanish_CI_AS\n");
    		sbQuery.append("LEFT JOIN (SELECT Fecha, EDolar, PDolar FROM  Monedas) M ON M.Fecha = F.Fecha  OR  M.Fecha =  FE.Fecha\n");
    		sbQuery.append("LEFT JOIN (SELECT * FROM Pendiente) P ON P.Docto = F.Factura AND P.Partida = F.FPor \n");
    		sbQuery.append("LEFT JOIN (SELECT FEPago, Medio, Monto, Moneda, QuienFactura, Factura FROM PagoPendiente GROUP BY FEPago, Medio, Monto, Moneda, QuienFactura, Factura) AS  PP  ON (PP.QuienFactura = F.FPor AND  PP.Factura = P.Docto)\n");
    		if(parametro.getEstado().equalsIgnoreCase("")) {
    		sbQuery.append("WHERE P.Tipo = 'Monitorear cobro' AND P.FFin IS NULL AND F.Desistema = 1 --AND DATEDIFF(day, GETDATE(), PP.FEPago)  >=  0\n");
    		}
    		if(parametro.getEstado().equalsIgnoreCase("No Vigente")) {
    		sbQuery.append("WHERE P.Tipo = 'Monitorear cobro' AND P.FFin IS NULL AND F.Desistema = 1  AND DATEDIFF(day, GETDATE(), PP.FEPago)  < 0 AND WHERE CL.idCliente = :idUsuario \n");
				}	
    		sbQuery.append("GROUP BY   CL.Clave , F.Importe, FE.Total,F.Tcambio, F.Moneda,F.IVA, CL.Nombre, PP.FEPago, F.Factura, M.EDolar, M.PDolar\n");
    		sbQuery.append(") F\n");
    		sbQuery.append("GROUP BY F.idcliente, F.EDolar, F.Tcambio, F.Importe, F.saldoInsoluto, F.Moneda, F.IVA, F.PDolar, F.Nombre, F.FEPago, F.Factura\n");
    		sbQuery.append(") D \n");
    		sbQuery.append("GROUP BY D.idCLIENTE,D.Nombre  ) F\n"); 
    		sbQuery.append("LEFT JOIN (SELECT FK01_cliente, Activo, MontoActual,FK02_CuentaBanco,FechaPago  FROM Depositos) AS D ON D.FK01_Cliente = F.idCliente\n"); 
    		sbQuery.append("LEFT JOIN (SELECT Fecha, EDolar, PDolar FROM  Monedas) M2 ON M2.Fecha = D.FechaPago \n");
    		sbQuery.append("LEFT JOIN (SELECT idCuenta,Moneda FROM CuentaBanco) CB ON CB.idCuenta = D.FK02_CuentaBanco\n");
    		sbQuery.append("WHERE (D.Activo = 1 OR  D.Activo IS NULL) )M\n");
    		sbQuery.append("GROUP BY  M.TotalPorCobrar, M.NumFacturas, M.idcliente, M.Nombre,M.FEPago, M.Etiqueta ORDER BY  M.TotalPorCobrar\n");
			
			
			

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("idUsuario", parametro.getIdCliente());
			Map<String, List<Factura>> mapReturn = new HashMap<String, List<Factura>>();
			
		    getJdbcTemplate().query(sbQuery.toString(),parametros,new RowMapper() {
		    	@Override
		           public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    		Factura f = new Factura();
		    		String tipo = rs.getString("Etiqueta");
		    		
		    		   SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy", new java.util.Locale("es", "ES"));
		    		   SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MMM/yyyy", Locale.ENGLISH);
		    		   try{	      				    			   

		    				   f.setTotalMonto(rs.getDouble("Totalmonto"));
		    				   f.setTotalPorCobrar(rs.getDouble("TotalPorCobrar"));
		    				   f.setIdCliente2(rs.getInt("idcliente"));
		    				   f.setNombre_Cliente(rs.getString("Nombre"));
		    				   f.setNumFacturas(rs.getInt("NumFacturas")); 
		    				   if(rs.getDate("FEPago") != null) {
		    					   f.setFechaPago(dateFormat.format(rs.getDate("FEPago")));
			    				   f.setFechaIng(dateFormat2.format(rs.getDate("FEPago")));
		    				   }
		    				  		    				  
		    			   
		    			   
		    		   }catch (Exception e) {
		               }
		    		   	if(tipo.equals("Con Deposito") && tipo != null) {
		    		   	 if(mapReturn.get("Con Deposito") != null){
		    				   mapReturn.get("Con Deposito").add(f);
		    			   }
		    			   else {
			    			   List<Factura> list = new ArrayList<Factura>();
			    			   mapReturn.put("Con Deposito", list );
			    			   mapReturn.get("Con Deposito").add(f);
			    		   }
		    		   	}
		    		   	if(tipo.equals("Sin Deposito") && tipo != null) {
			    			   if(mapReturn.get("Sin Deposito") != null){
			    				   mapReturn.get("Sin Deposito").add(f);
			    			   }
			    			   else {
				    			   List<Factura> list = new ArrayList<Factura>();
				    			   mapReturn.put("Sin Deposito", list );
				    			   mapReturn.get("Sin Deposito").add(f);
				    		   }
		    		   	}
		    		   return null;
		    		   
		    	}  
		    	
		      });	    
		    return mapReturn; 	 
		}catch (Exception e){
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	
	
	
	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })	
	public Map<String, List<Excedentes>> consultarExcedentes(Parametro parametro) throws ProquifaNetException{
		try{
			StringBuilder sbQuery = new StringBuilder(" \n");
			
			sbQuery.append("SELECT  EP.idExcedente, EP.Monto, EP.Moneda as MonedaEX, CB.Banco, CB.NoCuenta, CB.Moneda, D.FechaPago, D.ReferenciaBancaria, EM.Alias, EP.FK02_Factura,\n");   
			sbQuery.append("STR(CASE WHEN (F.Tcambio = 0 OR  F.Tcambio IS NULL) THEN \n");
			sbQuery.append("CASE WHEN  (EP.Moneda = 'USD' OR  EP.Moneda = 'Dolares')  THEN EP.Monto *  M.PDolar\n");
			sbQuery.append("WHEN (EP.Moneda = 'EUR' OR EP.Moneda = 'Euros')  THEN  EP.Monto * M.EDolar * M.PDolar \n");
			sbQuery.append("WHEN (EP.Moneda = 'MXN' OR EP.Moneda = 'Pesos' OR EP.Moneda = 'M.N.' OR EP.Moneda = 'MN') THEN  EP.Monto END\n"); 
			sbQuery.append("ELSE \n");
			sbQuery.append("CASE WHEN  (EP.Moneda = 'USD' OR  EP.Moneda = 'Dolares')  THEN EP.Monto * F.TCambio\n");  
			sbQuery.append("WHEN (EP.Moneda = 'EUR' OR EP.Moneda = 'Euros')  THEN  EP.Monto * F.TCambio * M.PDolar \n");
			sbQuery.append("WHEN (EP.Moneda = 'MXN' OR EP.Moneda = 'Pesos' OR EP.Moneda = 'M.N.' OR EP.Moneda = 'MN') THEN  EP.Monto END END,\n");  
			sbQuery.append("LEN(CASE WHEN (F.Tcambio = 0 OR  F.Tcambio IS NULL) THEN \n");
			sbQuery.append("CASE WHEN  (EP.Moneda = 'USD' OR  EP.Moneda = 'Dolares')  THEN EP.Monto *  M.PDolar\n");
			sbQuery.append("WHEN (EP.Moneda = 'EUR' OR EP.Moneda = 'Euros')  THEN  EP.Monto * M.EDolar * M.PDolar \n");
			sbQuery.append("WHEN (EP.Moneda = 'MXN' OR EP.Moneda = 'Pesos' OR EP.Moneda = 'M.N.' OR EP.Moneda = 'MN') THEN  EP.Monto END\n"); 
			sbQuery.append("ELSE \n");
			sbQuery.append("CASE WHEN  (EP.Moneda = 'USD' OR  EP.Moneda = 'Dolares')  THEN EP.Monto * F.TCambio\n");  
			sbQuery.append("WHEN (EP.Moneda = 'EUR' OR EP.Moneda = 'Euros')  THEN  EP.Monto * F.TCambio * M.PDolar \n");
			sbQuery.append("WHEN (EP.Moneda = 'MXN' OR EP.Moneda = 'Pesos' OR EP.Moneda = 'M.N.' OR EP.Moneda = 'MN') THEN  EP.Monto END END + 15 ), 2) AS MontoPesos,\n");
			
			sbQuery.append("STR(CASE WHEN (F.Tcambio = 0 OR  F.Tcambio IS NULL) THEN \n");
			sbQuery.append("CASE WHEN EP.Moneda = 'MXN' THEN EP.Monto  /M.PDolar\n");
			sbQuery.append("WHEN EP.Moneda = 'Pesos' THEN EP.Monto /M.PDolar\n");
			sbQuery.append("WHEN EP.Moneda = 'M.N.' THEN EP.Monto /M.PDolar\n");
			sbQuery.append("WHEN EP.Moneda = 'USD' THEN EP.Monto\n");
			sbQuery.append("WHEN EP.Moneda = 'Dolares' THEN EP.Monto\n");
			sbQuery.append("WHEN EP.Moneda = 'EUR' THEN EP.Monto * M.EDolar\n"); 
			sbQuery.append("WHEN EP.Moneda = 'Euros' THEN EP.Monto * M.EDolar END\n");
	   
			sbQuery.append("WHEN EP.Moneda = 'MXN' THEN EP.Monto /F.TCambio\n");
			sbQuery.append("WHEN EP.Moneda = 'Pesos' THEN EP.Monto /F.TCambio\n");
			sbQuery.append("WHEN EP.Moneda = 'M.N.' THEN EP.Monto /F.TCambio\n");
			sbQuery.append("WHEN EP.Moneda = 'Dolares' THEN EP.Monto\n");
			sbQuery.append("WHEN EP.Moneda = 'USD' THEN EP.Monto\n");
			sbQuery.append("WHEN EP.Moneda = 'Euros' THEN EP.Monto * F.TCambio\n"); 
			sbQuery.append("WHEN EP.Moneda = 'EUR' THEN EP.Monto * F.TCambio  END, \n");
			sbQuery.append("LEN(CASE WHEN (F.Tcambio = 0 OR  F.Tcambio IS NULL) THEN \n");
			sbQuery.append("CASE WHEN EP.Moneda = 'MXN' THEN EP.Monto  /M.PDolar\n");
			sbQuery.append("WHEN EP.Moneda = 'Pesos' THEN EP.Monto /M.PDolar\n");
			sbQuery.append("WHEN EP.Moneda = 'M.N.' THEN EP.Monto /M.PDolar\n");
			sbQuery.append("WHEN EP.Moneda = 'USD' THEN EP.Monto\n");
			sbQuery.append("WHEN EP.Moneda = 'Dolares' THEN EP.Monto\n");
			sbQuery.append("WHEN EP.Moneda = 'EUR' THEN EP.Monto * M.EDolar\n"); 
			sbQuery.append("WHEN EP.Moneda = 'Euros' THEN EP.Monto * M.EDolar END\n");
	   
			sbQuery.append("WHEN EP.Moneda = 'MXN' THEN EP.Monto /F.TCambio\n");
			sbQuery.append("WHEN EP.Moneda = 'Pesos' THEN EP.Monto /F.TCambio\n");
			sbQuery.append("WHEN EP.Moneda = 'M.N.' THEN EP.Monto /F.TCambio\n");
			sbQuery.append("WHEN EP.Moneda = 'Dolares' THEN EP.Monto\n");
			sbQuery.append("WHEN EP.Moneda = 'USD' THEN EP.Monto\n");
			sbQuery.append("WHEN EP.Moneda = 'Euros' THEN EP.Monto * F.TCambio\n"); 
			sbQuery.append("WHEN EP.Moneda = 'EUR' THEN EP.Monto * F.TCambio  END + 15), 2) AS MontoUSD,\n");
			
			sbQuery.append("STR(CASE WHEN (F.Tcambio = 0 OR F.Tcambio IS NULL) THEN \n");
			sbQuery.append("CASE WHEN EP.Moneda = 'MXN' THEN m.PDolar\n");
			sbQuery.append("WHEN EP.Moneda = 'Pesos' THEN m.PDolar\n");
			sbQuery.append("WHEN EP.Moneda = 'M.N.' THEN m.PDolar\n");
			sbQuery.append("WHEN EP.Moneda = 'MN' THEN m.PDolar\n");
			sbQuery.append("WHEN EP.Moneda = 'USD' THEN m.PDolar\n");
			sbQuery.append("WHEN EP.Moneda = 'Dolar' THEN m.PDolar\n");
			sbQuery.append("WHEN EP.Moneda = 'Dolares' THEN m.PDolar\n");
			sbQuery.append("WHEN EP.Moneda = 'EUR' THEN  m.EDolar \n");
			sbQuery.append("WHEN EP.Moneda = 'Euros' THEN  m.EDolar END\n");
			sbQuery.append("ELSE \n");
			sbQuery.append("CASE WHEN EP.Moneda = 'MXN' THEN F.TCambio\n");
			sbQuery.append("WHEN EP.Moneda = 'Pesos' THEN F.TCambio\n");
			sbQuery.append("WHEN EP.Moneda = 'M.N.' THEN F.TCambio\n");
			sbQuery.append("WHEN EP.Moneda = 'MN' THEN F.TCambio\n");
			sbQuery.append("WHEN EP.Moneda = 'USD' THEN F.TCambio\n");
			sbQuery.append("WHEN EP.Moneda = 'Dolar' THEN F.TCambio\n");
			sbQuery.append("WHEN EP.Moneda = 'Dolares' THEN F.TCambio\n");
			sbQuery.append("WHEN EP.Moneda = 'EUR' THEN  F.TCambio \n");
			sbQuery.append("WHEN EP.Moneda = 'Euros' THEN  F.TCambio  END END,\n"); 
			sbQuery.append("LEN(CASE WHEN (F.Tcambio = 0 OR F.Tcambio IS NULL) THEN\n"); 
			sbQuery.append("CASE WHEN EP.Moneda = 'MXN' THEN m.PDolar\n");
			sbQuery.append("WHEN EP.Moneda = 'Pesos' THEN m.PDolar\n");
			sbQuery.append("WHEN EP.Moneda = 'M.N.' THEN m.PDolar\n");
			sbQuery.append("WHEN EP.Moneda = 'MN' THEN m.PDolar\n");
			sbQuery.append("WHEN EP.Moneda = 'USD' THEN m.PDolar\n");
			sbQuery.append("WHEN EP.Moneda = 'Dolar' THEN m.PDolar\n");
			sbQuery.append("WHEN EP.Moneda = 'Dolares' THEN m.PDolar\n");
			sbQuery.append("WHEN EP.Moneda = 'EUR' THEN  m.EDolar \n");
			sbQuery.append("WHEN EP.Moneda = 'Euros' THEN  m.EDolar END\n");
			sbQuery.append("ELSE \n");
			sbQuery.append("CASE WHEN EP.Moneda = 'MXN' THEN F.TCambio\n");
			sbQuery.append("WHEN EP.Moneda = 'Pesos' THEN F.TCambio\n");
			sbQuery.append("WHEN EP.Moneda = 'M.N.' THEN F.TCambio\n");
			sbQuery.append("WHEN EP.Moneda = 'MN' THEN F.TCambio\n");
			sbQuery.append("WHEN EP.Moneda = 'USD' THEN F.TCambio\n");
			sbQuery.append("WHEN EP.Moneda = 'Dolar' THEN F.TCambio\n");
			sbQuery.append("WHEN EP.Moneda = 'Dolares' THEN F.TCambio\n");
			sbQuery.append("WHEN EP.Moneda = 'EUR' THEN  F.TCambio \n");
			sbQuery.append("WHEN EP.Moneda = 'Euros' THEN  F.TCambio  END END) + 15, 2)  AS MCambio,\n");
					
					
			sbQuery.append("CASE WHEN (F.Tcambio = 0 OR F.Tcambio IS NULL) THEN \n");
			sbQuery.append("CASE WHEN EP.Moneda = 'EUR' THEN  M.EDolar\n");
			sbQuery.append("WHEN EP.Moneda = 'Euros' THEN  M.EDolar END\n");
			sbQuery.append("ELSE \n");
			sbQuery.append("CASE WHEN EP.Moneda = 'EUR' THEN  F.TCambio\n");
			sbQuery.append("WHEN EP.Moneda = 'Euros' THEN  F.TCambio END END AS ETCambio\n");

    
			sbQuery.append("FROM EXCEdenteDePago EP\n");
			sbQuery.append("INNER JOIN  Facturas F  ON  EP.FK02_Factura =  F.idFactura\n");
			sbQuery.append("INNER JOIN Monedas M ON M.fecha = F.Fecha \n");
			sbQuery.append("INNER JOIN Depositos D ON  EP.FK01_Deposito = D.PK_Deposito\n"); 
			sbQuery.append("INNER JOIN  cuentaBanco CB  ON  D.FK02_CuentaBanco = CB.idCuenta\n"); 
			sbQuery.append("INNER JOIN  Empresa EM  ON CB.FK01_Empresa = EM.PK_Empresa\n");
			sbQuery.append("WHERE  EP.Estado = 1  AND CB.Activo = 1 --AND  D.fk01_Cliente = :idUsuario \n");
			

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("idUsuario", parametro.getIdCliente());
			Map<String, List<Excedentes>> mapReturn = new HashMap<String, List<Excedentes>>();
			
		    getJdbcTemplate().query(sbQuery.toString(),parametros,new RowMapper() {
		    	@Override
		           public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    		Excedentes e = new Excedentes();
		    		

		    		   SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy", new java.util.Locale("es", "ES"));
		    		   String empresa = rs.getString("Alias"); 
		    		   try{	      		
		    			   	  
		    			   	   e.setIdExcedente(rs.getInt("idExcedente"));
		    			   	   e.setMonto(rs.getDouble("Monto"));
		    			   	   e.setMoneda(rs.getString("MonedaEX"));
		    			   	   e.setBanco(rs.getString("Banco"));
		    			   	   e.setNoCuenta(rs.getInt("NoCuenta"));
		    			   	   e.setMonedaBanco(rs.getString("Moneda"));
		    			   	   if(rs.getDate("FechaPago") != null)
		    			   	   e.setFecha(dateFormat.format(rs.getDate("FechaPago")));
		    			   	   e.setReferenciaBancaria(rs.getInt("ReferenciaBancaria"));
		    			   	   e.setNoFactura(rs.getLong("FK02_Factura"));
		    			   	   e.setfPor(rs.getString("Alias"));
		    			   	   e.setMonedaPesos(rs.getDouble("MontoPesos"));
		    			   	   e.setMonedaUSD(rs.getDouble("MontoUSD"));
		    			   	   if(rs.getDouble("MCambio") > 0)
		    			   	   e.settCambio(rs.getDouble("MCambio"));
		    			   	   else 
		    			   		   e.settCambio(0.0);
		    			   	   if(rs.getDouble("ETCambio") > 0)
		    			   		   e.seteTCambio(rs.getDouble("ETCambio"));
		    			   	   else 
		    			   		   e.seteTCambio(0.0);
		    			   	   e.setEmpresa(rs.getString("Alias"));
		    			   	   
  
		    		   }catch (Exception ex) {
		    			   ex.toString();
		               }

		    			   
		    			   if( empresa != null && empresa.equalsIgnoreCase("Proquifa") ){
			    			   if(mapReturn.get("Proquifa") != null){
			    				   mapReturn.get("Proquifa").add(e);
			    			   }
				    		   else {
				    			   List<Excedentes> list = new ArrayList<Excedentes>();
				    			   mapReturn.put("Proquifa", list );
				    			   mapReturn.get("Proquifa").add(e);
				    		   }
			    		   }
			    		   if(empresa != null && (empresa.equalsIgnoreCase("Proveedora") ||  empresa.equalsIgnoreCase("ProveedoraX"))){
			    			   if(mapReturn.get("Proveedora") != null){
			    				   mapReturn.get("Proveedora").add(e);
			    			   }
				    		   else {
				    			   List<Excedentes> list = new ArrayList<Excedentes>();
				    			   mapReturn.put("Proveedora", list );
				    			   mapReturn.get("Proveedora").add(e);
				    		   }
			    		   }
			    		   if(empresa != null && empresa.equalsIgnoreCase("Golocaer")){
			    			   if(mapReturn.get("Golocaer") != null){
			    				   mapReturn.get("Golocaer").add(e);
			    			   }
				    		   else {
				    			   List<Excedentes> list = new ArrayList<Excedentes>();
				    			   mapReturn.put("Golocaer", list );
				    			   mapReturn.get("Golocaer").add(e);
				    		   }
			    		   }
			    		   if(empresa != null && empresa.equalsIgnoreCase("Mungen")){
			    			   if(mapReturn.get("Mungen") != null){
			    				   mapReturn.get("Mungen").add(e);
			    			   }
				    		   else {
				    			   List<Excedentes> list = new ArrayList<Excedentes>();
				    			   mapReturn.put("Mungen", list );
				    			   mapReturn.get("Mungen").add(e);
				    		   }
			    		   }
			    		   if(empresa != null && empresa.equalsIgnoreCase("Pharma")){
			    			   if(mapReturn.get("Pharma") != null){
			    				   mapReturn.get("Pharma").add(e);
			    			   }
				    		   else {
				    			   List<Excedentes> list = new ArrayList<Excedentes>();
				    			   mapReturn.put("Pharma", list );
				    			   mapReturn.get("Pharma").add(e);
				    		   }
			    		   }
		    		   
		    		   return null;
		    		   
		    	}  
		    	
		      });	    
		    return mapReturn; 	 
		}catch (Exception e){
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })	
	public Map<String, List<Depositos>> consultarDepositos(Parametro parametro) throws ProquifaNetException{
		try{
			StringBuilder sbQuery = new StringBuilder(" \n");

//			sbQuery.append("SELECT  D.ReferenciaBancaria, CB.Banco, CB.Moneda, D.Monto, CB.Moneda AS MonedaMonto, D.FechaPago, E.Alias, CB.RFCBanco, 'Tranferencia' MetodoPago, M.PDolar, CB.NoCuenta  \n"); 
//			sbQuery.append("FROM Depositos D \n");
//			sbQuery.append("INNER JOIN  CuentaBanco CB ON  D.FK02_CuentaBanco = CB.idCuenta\n");
//			sbQuery.append("INNER JOIN Empresa E ON  CB.FK01_Empresa =  E.PK_Empresa\n");
//			sbQuery.append("INNER JOIN Monedas M ON  CAST(D.FechaPago AS DATE)= CAST(M.Fecha AS DATE)\n");
//			sbQuery.append("WHERE  D.FK01_Cliente =:idCliente AND D.Activo = 1 AND CB.Activo = 1\n");
//			
			
			sbQuery.append("SELECT  D.ReferenciaBancaria, CB.Banco, CB.Moneda, D.Monto, CB.Moneda AS MonedaMonto, D.FechaPago, E.Alias, CB.RFCBanco, 'Tranferencia' MetodoPago,\n"); 
			sbQuery.append("COALESCE(M.PDolar,(SELECT PDolar FROM Monedas WHERE Fecha =(SELECT MAX(Fecha) FROM Monedas ))) AS PDolar , CB.NoCuenta, D.FK01_Cliente   \n");
			sbQuery.append("FROM Depositos D \n");
			sbQuery.append("INNER JOIN  CuentaBanco CB ON  D.FK02_CuentaBanco = CB.idCuenta\n");
			sbQuery.append("INNER JOIN Empresa E ON  CB.FK01_Empresa =  E.PK_Empresa\n");
			sbQuery.append("LEFT JOIN Monedas M ON  CAST(D.FechaPago AS DATE)= CAST(M.Fecha AS DATE)\n");
			sbQuery.append("WHERE  D.FK01_Cliente = :idCliente AND D.Activo = 1 AND CB.Activo = 1\n");
			

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("idCliente", parametro.getIdCliente());
			Map<String, List<Depositos>> mapReturn = new HashMap<String, List<Depositos>>();
			
		    getJdbcTemplate().query(sbQuery.toString(),parametros,new RowMapper() {
		    	@Override
		           public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    		Depositos d = new Depositos();
		    		

		    		   SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy", new java.util.Locale("es", "ES"));
		    		   SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MMM/yyyy", Locale.ENGLISH);
		    		   String empresa = rs.getString("Alias"); 
		    		   try{	      		
		    			   	  
		    			   	   d.setReferenciaBanco(rs.getInt("ReferenciaBancaria"));
		    			   	   d.setBanco(rs.getString("Banco"));
		    			   	   d.setMonedaBanco(rs.getString("Moneda"));
		    			   	   d.setMonto(rs.getDouble("Monto"));
		    			   	   d.setMoneda(rs.getString("MonedaMonto"));
		    			   	   if(rs.getDate("FechaPago") != null)
		    			   	   d.setFechaPago(dateFormat.format(rs.getDate("FechaPago")));
		    			   	   d.setfPor(rs.getString("Alias"));
		    			   	   d.setRFCBanco(rs.getLong("RFCBanco"));
		    			   	   d.setFormaPago(rs.getString("MetodoPago"));
		    			   	   d.setTcambio(rs.getDouble("PDolar"));
		    			   	   d.setNoCuenta(rs.getLong("NoCuenta"));
		    			   	   if(rs.getDate("FechaPago") != null)
		    			   	   d.setFechaIn(dateFormat2.format(rs.getDate("FechaPago")));

		    		   }catch (Exception ex) {
		    			   ex.toString();
		               }

		    			   
		    			   if( empresa != null && empresa.equalsIgnoreCase("Proquifa") ){
			    			   if(mapReturn.get("Proquifa") != null){
			    				   mapReturn.get("Proquifa").add(d);
			    			   }
				    		   else {
				    			   List<Depositos> list = new ArrayList<Depositos>();
				    			   mapReturn.put("Proquifa", list );
				    			   mapReturn.get("Proquifa").add(d);
				    		   }
			    		   }
			    		   if(empresa != null && (empresa.equalsIgnoreCase("Proveedora") ||  empresa.equalsIgnoreCase("ProveedoraX"))){
			    			   if(mapReturn.get("Proveedora") != null){
			    				   mapReturn.get("Proveedora").add(d);
			    			   }
				    		   else {
				    			   List<Depositos> list = new ArrayList<Depositos>();
				    			   mapReturn.put("Proveedora", list );
				    			   mapReturn.get("Proveedora").add(d);
				    		   }
			    		   }
			    		   if(empresa != null && empresa.equalsIgnoreCase("Golocaer")){
			    			   if(mapReturn.get("Golocaer") != null){
			    				   mapReturn.get("Golocaer").add(d);
			    			   }
				    		   else {
				    			   List<Depositos> list = new ArrayList<Depositos>();
				    			   mapReturn.put("Golocaer", list );
				    			   mapReturn.get("Golocaer").add(d);
				    		   }
			    		   }
			    		   if(empresa != null && empresa.equalsIgnoreCase("Mungen")){
			    			   if(mapReturn.get("Mungen") != null){
			    				   mapReturn.get("Mungen").add(d);
			    			   }
				    		   else {
				    			   List<Depositos> list = new ArrayList<Depositos>();
				    			   mapReturn.put("Mungen", list );
				    			   mapReturn.get("Mungen").add(d);
				    		   }
			    		   }
			    		   if(empresa != null && empresa.equalsIgnoreCase("Pharma")){
			    			   if(mapReturn.get("Pharma") != null){
			    				   mapReturn.get("Pharma").add(d);
			    			   }
				    		   else {
				    			   List<Depositos> list = new ArrayList<Depositos>();
				    			   mapReturn.put("Pharma", list );
				    			   mapReturn.get("Pharma").add(d);
				    		   }
			    		   }
		    		   
		    		   return null;
		    		   
		    	}  
		    	
		      });	    
		    return mapReturn; 	 
		}catch (Exception e){
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })	
	public Map<String, List<CuentaBanco>> consultarBancos() throws ProquifaNetException{
		try{
			StringBuilder sbQuery = new StringBuilder(" \n");
		
			sbQuery.append("SELECT idCuenta, Banco,  Moneda, NoCuenta, RFCBanco  FROM CuentaBanco WHERE Activo = 1\n");
			
			Map<String, Object> parametros = new HashMap<String, Object>();
			Map<String, List<CuentaBanco>> mapReturn = new HashMap<String, List<CuentaBanco>>();			
		    getJdbcTemplate().query(sbQuery.toString(),parametros,new RowMapper() {
		    	@Override
		           public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    		CuentaBanco cb = new CuentaBanco();

		    		   try{	      		
		    			   	  
		    			   cb.setIdCuenta(rs.getLong("idCuenta"));
		    			   cb.setBanco(rs.getString("Banco"));
		    			   cb.setMoneda(rs.getString("Moneda"));
		    			   cb.setNoCuenta(rs.getString("NoCuenta"));
		    			   cb.setRfcBanco(rs.getLong("RFCBanco"));
		    

		    		   }catch (Exception ex) {
		    			   ex.toString();
		               }

		    			   
			    			   if(mapReturn.get("Banco") != null){
			    				   mapReturn.get("Banco").add(cb);
			    			   }
				    		   else {
				    			   List<CuentaBanco> list = new ArrayList<CuentaBanco>();
				    			   mapReturn.put("Banco", list );
				    			   mapReturn.get("Banco").add(cb);
				    		   }
		    		   
		    		   return null;
		    		   
		    	}  
		    	
		      });	    
		    return mapReturn; 	 
		}catch (Exception e){
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	
	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })	
	public int  registrarDeposito(Depositos deposito) throws ProquifaNetException {
		try {
			Map<String, Object> parametros = new HashMap<String, Object>();

			StringBuilder sbQuery = new StringBuilder(" \n");
			
			sbQuery.append("SELECT IDENT_CURRENT('Depositos')\n");
			int idDeposito= super.jdbcTemplate.queryForObject(sbQuery.toString(),parametros, Integer.class);
			
			if(idDeposito > 0) {
				StringBuilder sbQuery2 = new StringBuilder(" \n");
				sbQuery2.append("INSERT INTO Depositos (Fecha, ReferenciaBancaria, CuentaOrigen,FechaPago, FormaPago,Monto, MontoActual,Activo, Complemento, FK01_cliente\n"); 
				sbQuery2.append(",FK02_CuentaBanco ,TCambio ) \n");
				sbQuery2.append("VALUES (GETDATE(),:referenciaBanco,:cuentaOrigen,  :fechaPago, :formaPago, :monto,:montoActual,1,0,:idCliente,:idCuentaBanco, :tCambio )\n");
				
				
				parametros.put("referenciaBanco", deposito.getReferenciaBanco());
				parametros.put("cuentaOrigen", deposito.getCuentaOrigen());
				parametros.put("fechaPago", deposito.getFechaPago());
				parametros.put("formaPago", deposito.getFormaPago());
				parametros.put("monto", deposito.getMonto());
				parametros.put("montoActual", deposito.getMonto());
				parametros.put("idCliente", deposito.getIdCliente());
				parametros.put("idCuentaBanco", deposito.getIdCuentaBanco());
				parametros.put("tCambio", deposito.getTcambio());
				
				super.jdbcTemplate.update(sbQuery2.toString(),parametros);
			}
						
			sbQuery.append("SELECT IDENT_CURRENT('Depositos')\n");
			int idDeposito2 = super.jdbcTemplate.queryForObject(sbQuery.toString(),parametros, Integer.class);
			
			if(idDeposito == idDeposito2 ) {
				return 0;
			}
			return idDeposito2;


		}catch (Exception e){
			e.printStackTrace();
			throw new ProquifaNetException();
			
		}
		
	}
	
	
	
	
	public Double  aplicarNotasCredito (NotaCredito nota ) {
		StringBuilder sbQuery = new StringBuilder(" \n");
		sbQuery.append("SELECT ImporteTotal  FROM  NotaCredito WHERE PK_Nota =:idNota \n");
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idNota", nota.getIdNota());
		Double  importe = super.jdbcTemplate.queryForObject(sbQuery.toString(),parametros, Double.class);
		Double total =  nota.getImporteTotal();
		if(total > 0) {
			if(nota.getModificado() == true && importe > 0 ) {
				importe =+  importe - nota.getImporte();
				total =+ total - nota.getImporte();
				sbQuery.append("UPDATE NotaCredito SET ImporteTotal =:importe  WHERE PK_Nota = :idNota \n");
				parametros.put("idNota", nota.getIdNota());
				parametros.put("importe", importe);
				if(importe > 0 ) {
					sbQuery.append("UPDATE NotaCredito SET Estado = 'Aplicada' WHERE PK_Nota = :idNota \n");
					parametros.put("idNota", nota.getIdNota());
				}

			}else if(importe > 0) {
				importe =+  importe - nota.getImporte();
				total =+ total - nota.getImporte();
				sbQuery.append("UPDATE NotaCredito SET ImporteTotal =:importe  WHERE PK_Nota = :idNota \n");
				parametros.put("idNota", nota.getIdNota());
				parametros.put("importe", importe);
				if(importe > 0 ) {
					sbQuery.append("UPDATE NotaCredito SET Estado = 'Aplicada' WHERE PK_Nota = :idNota \n");
					parametros.put("idNota", nota.getIdNota());
				}
				
			}
			
		}else {
			return total ;
		}
		return total;
		
	}
	
	

	
	
	
	public Double  aplicarExcedente ( Excedentes ex ) {
		StringBuilder sbQuery = new StringBuilder(" \n");
		sbQuery.append("select Monto from excedenteDePago where idExcedente = :idExcedente \n");
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idExcedente", ex.getIdExcedente());
		Double  importe = super.jdbcTemplate.queryForObject(sbQuery.toString(),parametros, Double.class);
		Double total =  ex.getTotal();
		if(total > 0) {		   	   
			if(ex.getModificado() == true && importe > 0 ) {
				importe =+  importe - ex.getMonto();
				total =+ total - ex.getTotal();
				sbQuery.append("Update NotaCredito SET ImporteTotal =:importe  WHERE PK_Nota = :idExcedente \n");
				parametros.put("idExcedente", ex.getIdCliente());
				parametros.put("importe", importe);
				if(importe > 0 ) {
					sbQuery.append("UPDATE ExcedenteDePago  SET  Estado = 0 WHERE idExcedente = :idExcedente \n");
					parametros.put("idExcedente", ex.getIdExcedente());
				}

			}else if(importe > 0) {
				importe =+  importe -  ex.getMonto();
				total =+ total - ex.getTotal();
				sbQuery.append("Update NotaCredito SET ImporteTotal =:importe  WHERE PK_Nota = :idExcedente \n");
				parametros.put("idExcedente", ex.getIdExcedente());
				parametros.put("importe", importe);
				if(importe > 0 ) {
					sbQuery.append("UPDATE ExcedenteDePago  SET  Estado = 0 WHERE idExcedente = :idExcedente \n");
					parametros.put("idExcedente", ex.getIdExcedente());
				}
				
				
			}
			
		}else {
			return total;
		}
	 
		return total;
		
	}
	
	
	
	
	
	
}
