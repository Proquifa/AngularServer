/**
 * 
 */
package com.proquifa.net.persistencia.reportes.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.cobrosypagos.Cobros;
import com.proquifa.net.modelo.comun.NivelIngreso;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.reportes.ReporteCobrosDAO;
import com.proquifa.net.persistencia.reportes.impl.mapper.ReporteCobrosRowMapper;
/**
 * @author ymendez
 *
 */
@Repository
public class ReporteCobrosDAOImpl extends DataBaseDAO implements ReporteCobrosDAO {

	Funcion f ;
	
	final Logger log = LoggerFactory.getLogger(ReporteCobrosDAOImpl.class);
	
	@SuppressWarnings( "unchecked" )
	public List<Cobros> findCobros(String sbCondicion, List<NivelIngreso> niveles, Long idUsuarioLogueado) throws ProquifaNetException {
		
		try{
			
			f = new Funcion();
			
			StringBuilder sbQuery = new StringBuilder("");
			sbQuery.append("\n SELECT Distinct(F.idFactura), F.CPedido, F.Factura, F.FPor, COALESCE(PA.FFin,F.Fecha) FechaFacturacion, F.CPago, ");
			sbQuery.append("\n C.Vendedor ESAC, CASE WHEN C.FK01_EV = 0 OR C.FK01_EV IS NULL THEN 'No asignado' ELSE E.Usuario END EV, C.Nombre NombreCliente, PP.FEPago,  ");
			sbQuery.append("\n DATEDIFF(DAY, GETDATE(),PP.FEPago) AS DRC, ");
			sbQuery.append("\n ROUND(CASE WHEN F.Moneda = 'Dolares' OR F.Moneda = 'USD' THEN COALESCE(PF.Importe,F.Importe,0) "); 
			sbQuery.append("\n WHEN F.Moneda = 'EUR' OR F.Moneda = 'Euros' THEN COALESCE(PF.Importe,F.Importe,0)* COALESCE(MON.EDolar,0) ELSE (COALESCE(PF.Importe,F.Importe,0) /  "); 
			sbQuery.append("\n CASE WHEN F.TCambio = 0 THEN 1 ELSE F.TCambio END) END + ");
			sbQuery.append("\n (CASE WHEN F.Moneda = 'Dolares' OR F.Moneda = 'USD' THEN COALESCE(PF.Importe,F.Importe,0)  "); 
			sbQuery.append("\n WHEN F.Moneda = 'EUR' OR F.Moneda = 'Euros' THEN COALESCE(PF.Importe,F.Importe,0)* COALESCE(MON.EDolar,0) ELSE (COALESCE(PF.Importe,F.Importe,0) /  "); 
			sbQuery.append("\n CASE WHEN F.TCambio = 0 THEN 1 ELSE F.TCambio END) END * F.IVA),2) AS CONVERSIONUSD,   ");
			sbQuery.append("\n COALESCE(PP.Medio,C.MedioPago,F.MPago) MPAGO, " + f.sqlLimitesNivelIngreso(niveles, "NIVEL.VentasUSD") + " AS NIVEL, PC.FFin FECHA_PROGRAMACION, RPC.FechaFin FECHA_REVISION, ");
			sbQuery.append("\n CASE  WHEN Co.Moroso = 1 THEN 'SI' WHEN CO.Moroso = 0 THEN 'NO' ELSE NULL END Moroso, PF.Cant Piezas, CASE WHEN P.FFin IS NULL AND P.FInicio IS NOT NULL THEN 'Por Cobrar' ");
			sbQuery.append("\n 		 WHEN F.Estado = 'Cobrada' THEN 'Cobrada' ELSE 'Cancelada'  END Estado, PF.Partidas, CASE WHEN C.Cobrador = 0 OR C.Cobrador IS NULL THEN 'No asignado' ELSE EC.Usuario END Cobrador  ");
			sbQuery.append("\n FROM Facturas F  ");
			sbQuery.append("\n LEFT JOIN Pendiente P ON P.Docto = F.Factura AND P.Partida = F.FPor AND P.Tipo='Monitorear cobro' ");
			sbQuery.append("\n LEFT JOIN Pendiente PA ON PA.Docto = F.CPedido AND PA.Tipo = 'A Facturar' AND PA.FFin IS NOT NULL ");
			sbQuery.append("\n LEFT JOIN Clientes C ON C.Clave = F.Cliente  ");
			sbQuery.append("\n LEFT JOIN Empleados E ON E.Clave = C.FK01_EV  "); 
			sbQuery.append("\n LEFT JOIN Empleados EC ON EC.Clave = C.Cobrador  "); 
			sbQuery.append("\n LEFT JOIN PagoPendiente PP ON PP.Factura = F.Factura AND PP.QuienFactura = F.FPor  "); 
			sbQuery.append("\n LEFT JOIN Monedas AS MON ON MON.Fecha = F.Fecha  ");
			sbQuery.append("\n LEFT JOIN (SELECT COALESCE(SUM(CASE WHEN F.Moneda='Dolares' or F.Moneda='USD' then F.Importe WHEN F.Moneda='Euros' OR F.Moneda='EUR' THEN F.Importe * M.EDolar ");    
			sbQuery.append("\n WHEN F.Moneda='Pesos' OR F.Moneda='M.N.' Then F.Importe/F.TCambio END), 0 )as VentasUSD, Cliente FROM Facturas as F	 LEFT JOIN (SELECT * FROM Monedas) AS M ON M.Fecha = F.Fecha ");    
			sbQuery.append("\n WHERE YEAR(F.fecha)=DATEPART(year, GETDATE())-1 AND F.Estado NOT LIKE 'Cancela%' and F.DeSistema = 1  GROUP BY F.Cliente )AS NIVEL ON NIVEL.Cliente = F.Cliente  "); 
			sbQuery.append("\n LEFT JOIN (SELECT (COALESCE(CO.COTIZACIONES, 0) + COALESCE(PED.PEDIDOS,0)) As noOperaciones, Clave FROM Clientes AS C  ");    
			sbQuery.append("\n LEFT JOIN (SELECT COUNT(1) AS COTIZACIONES, Cliente FROM Cotizas WHERE YEAR(Fecha)=(YEAR(GETDATE())-1) GROUP BY Cliente) AS CO ON CO.Cliente = C.Nombre  ");    
			sbQuery.append("\n LEFT JOIN (SELECT COUNT(1) AS PEDIDOS, idCliente FROM Pedidos WHERE YEAR(FPedido) = (YEAR(GETDATE())-1) GROUP BY idCliente) AS PED ON PED.idCliente = C.Clave)  ");  
			sbQuery.append("\n AS OPER ON OPER.Clave = F.Cliente  ");
			sbQuery.append("\n LEFT JOIN (SELECT Docto,Partida,FInicio,MAX(FFin) AS FFin FROM Pendiente AS P WHERE Tipo='Programar cobro'  "); 
			sbQuery.append("\n GROUP BY Docto,Partida,FInicio HAVING FInicio=(Select MAX(FInicio) from Pendiente  "); 
			sbQuery.append("\n WHERE Docto=P.Docto AND Partida=P.Partida AND Tipo='Programar cobro')) AS PC ON F.Factura=PC.Docto AND F.FPor=PC.Partida  ");
			sbQuery.append("\n LEFT JOIN (SELECT Factura,FPor,Fecha,MAX(FFin) AS FechaFin FROM RutaDP AS RPC GROUP BY Factura,FPor,Fecha HAVING Fecha=(Select MAX(Fecha)  "); 
			sbQuery.append("\n FROM RutaDP WHERE  Factura=RPC.Factura AND FPor=RPC.FPor)) AS RPC ON F.Factura=RPC.Factura AND F.FPor=RPC.FPor  "); 
			sbQuery.append("\n LEFT JOIN (SELECT Factura,FPor,Fecha,MAX(FFin) AS FechaFin FROM RutaPR AS RPR GROUP BY Factura,FPor,Fecha  "); 
			sbQuery.append("\n HAVING Fecha=(Select MAX(Fecha) FROM RutaPR WHERE  Factura=RPR.Factura AND FPor=RPR.FPor)) AS RPR ON F.Factura=RPR.Factura AND F.FPor=RPR.FPor  "); 
			sbQuery.append("\n LEFT JOIN Cobro CO ON CO.Factura = F.Factura AND Co.FPor = F.FPor ");
			sbQuery.append("\n LEFT JOIN (SELECT PF.Factura, PF.FPor, SUM(PF.Cant) Cant, SUM(PF.Importe * PF.Cant) Importe, Count(PF.Part) Partidas FROM Facturas F ");
			sbQuery.append("\n 		LEFT JOIN (select * from PFacturas where CPedido is not null) PF ON PF.Factura = F.Factura AND PF.FPor = F.FPor AND PF.CPedido=F.CPedido ");
			sbQuery.append("\n where F.DeSistema = 1  ");
			sbQuery.append("\n GROUP BY PF.Factura, PF.FPor) PF ON PF.Factura = F.Factura AND PF.FPor = F.FPor ");
			sbQuery.append(sbCondicion);
			sbQuery.append(" \n ORDER BY C.Nombre ");
			log.info(sbQuery.toString());
			
			List<Cobros> cobros=super.jdbcTemplate.query(sbQuery.toString(), new ReporteCobrosRowMapper());
			return cobros;
		}catch(Exception e){
			log.info(e.getMessage());
			f.enviarCorreoAvisoExepcion(e,"Condicion:"+sbCondicion, "idUsuariologueado:"+idUsuarioLogueado);
			return new ArrayList<Cobros>();
		}
	}
	
	
}
