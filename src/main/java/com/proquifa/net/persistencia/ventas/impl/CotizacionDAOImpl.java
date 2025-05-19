/**
 * 
 */
package com.proquifa.net.persistencia.ventas.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.ventas.Cotizacion;
import com.proquifa.net.modelo.ventas.DoctoR;
import com.proquifa.net.modelo.ventas.PartidaCotizacion;
import com.proquifa.net.modelo.ventas.reportes.seguimientos.SeguimientoCotizacion;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.ventas.CotizacionDAO;
import com.proquifa.net.persistencia.ventas.impl.mapper.CotizacionAbiertaCerradaRowMapper;
import com.proquifa.net.persistencia.ventas.impl.mapper.CotizacionRowMapper;
import com.proquifa.net.persistencia.ventas.impl.mapper.DoctoRRowMapper;
import com.proquifa.net.persistencia.ventas.impl.mapper.ReporteCotizacionRowMapper;

/**
 * @author ernestogonzalezlozada
 *
 */
@Repository
public class CotizacionDAOImpl extends DataBaseDAO implements CotizacionDAO {

	public SimpleDateFormat formatterTime = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
	public SimpleDateFormat formatterDate = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	final Logger log = LoggerFactory.getLogger(CotizacionDAOImpl.class);
	
	/*
	 * (non-Javadoc)
	 * 
	 * @seemx.com.proquifa.proquifanet.persistencia.ventas.CotizacionDAO#
	 * obtenerCotizacionesPorClienteUsuario(java.lang.Long, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<Cotizacion> obtenerCotizacionPorFolio(String folio) {
		String query =  "SELECT * FROM Cotizas WHERE Clave <> '0' AND Clave LIKE '%" + folio + "%'  AND fecha >= '20090101'";	
		return super.jdbcTemplate.query(query, new CotizacionRowMapper());
	}

	@SuppressWarnings("unchecked")
	public List<Cotizacion> findCotizacionesParaReporte(String condiciones) throws ProquifaNetException {
		try{
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("condiciones", condiciones);
		
		String query = 
				" \n SELECT DISTINCT COTI.Fecha,COTI.Cliente,COTI.Contacto,COTI.Vendedor,COTI.Clave, COTI.IMoneda AS Moneda,COTI.CPago, " +
				" \n COALESCE(montoCotiza.Monto,'0') AS Monto,COALESCE(musd.MontoUSD,'0') AS MontoUSD , " +
				" \n DR.Folio AS Requisicion,(CASE WHEN (COALESCE(Gestionando.Gestionando,'0')+COALESCE(PartidasTerminadas.PCPedidas,'0'))=PartidasEnCero.Ceros THEN 'Cerrado' ELSE 'Abierto' END) AS Estado, " +
				" \n (CASE WHEN PCOT_0.PART_0=COALESCE(PCOT99_A.PART_A,'0') + COALESCE(PCOT99_B.PART_B,'0')THEN CASE WHEN COTI.MSalida IS NULL OR COTI.MSalida='' THEN 'Pendiente' ELSE CASE WHEN COTI.MSalida='C' THEN 'Correo' ELSE 'Fax' END END ELSE 'Pendiente' END) AS MSalida, " + 
				" \n (CASE WHEN Dr.Medio=NULL OR Dr.Medio='PQFMovil' THEN 'Requisición Movil' ELSE CASE WHEN COALESCE(pd.NumP,'0') > 0 THEN 'Pedido a cotizar' ELSE 'Requisición a Cotizar' END END) AS PendienteOrigen," + 
				" \n DR.Fecha AS FechaRegistro, COALESCE(Dr.FHOrigen,DR.Fecha) AS FHOrigen, " +
				" \n CASE WHEN PCOT0_A.PART_A >0" +
				" \n 	THEN" +
				" \n 		CASE WHEN PCOT99_A.PART_A = PCOT0_A.PART_A" +
				" \n 				THEN" +
				" \n 					CASE WHEN (DATEDIFF(SS, DR_F.FECHA, TOTINFO_A.FMAX_A)<=10800)" +
				" \n 							THEN 1" +
				" \n 							ELSE" +
				" \n 								(CASE WHEN (DATEDIFF(DAY, DR_F.FECHA, TOTINFO_A.FMAX_A) >1)" +
				" \n 										THEN" +
				" \n 											(CASE WHEN DATENAME(DW, DR_F.FECHA)='Viernes'" +
				" \n 													THEN" +
				" \n 														(CASE WHEN ((DATEDIFF(SS, DR_F.FECHA, TOTINFO_A.FMAX_A)-223200)<=10800)" +
				" \n 																THEN 1" +
				" \n 																ELSE 0" +
				" \n 														END)" +
				"        												ELSE 0" +
				" \n 											END)" +
				" \n 										ELSE" +
				" \n 											CASE WHEN (CASE WHEN DATEDIFF(SS,DR_F.FECHA,FHFIN.FECHA)>0" +
				" \n 																THEN" +
				" \n 																	DATEDIFF(SS,DR_F.FECHA,FHFIN.FECHA)" +
				" \n 																ELSE 0" +
				" \n 														END + DATEDIFF(SS,LIMHRS_A.FMAX_A,TOTINFO_A.FMAX_A))<=10800" +
				" \n 												THEN 1" +
				" \n 												ELSE 0" +
				" \n 											END" +
				" \n 								END)" +
				" \n 					END" +
				" \n 				ELSE" +
				" \n 					CASE WHEN (DATEDIFF(SS, COALESCE(DR.FHOrigen,DR.FECHA), GETDATE())<=10800)" +
				" \n 							THEN 1" +
				" \n 							ELSE" +
				" \n 								(CASE WHEN (DATEDIFF(DAY, COALESCE(DR.FHOrigen,DR.FECHA), GETDATE()) >1)" +
				" \n 										THEN" +
				" \n 											(CASE WHEN DATENAME(DW, COALESCE(DR.FHOrigen,DR.FECHA))='Viernes'" +
				" \n 													THEN" +
				" \n 														(CASE WHEN ((DATEDIFF(SS, COALESCE(DR.FHOrigen,DR.FECHA), GETDATE())-223200)<=10800)" +
				" \n 																THEN 1" +
				" \n 																ELSE 0" +
				" \n 														END)" +
				" \n 		        									ELSE 0" +
				" \n 											END)" +
				" \n 										ELSE" +
				" \n 											CASE WHEN ((CASE WHEN DATEDIFF(SS,COALESCE(DR.FHOrigen,DR.FECHA),FHFIN.FECHA)>0" +
				" \n 																THEN" +
				" \n 																	DATEDIFF(SS,COALESCE(DR.FHOrigen,DR.FECHA),FHFIN.FECHA)" +
				" \n 																ELSE 0" +
				" \n 														END)+ DATEDIFF(SS,LIMHRS_A.FMAX_A,TOTINFO_A.FMAX_A))<=10800" +
				" \n 												THEN 1" +
				" \n 												ELSE 0" +
				" \n 											END" +
				" \n 								END)" +
				" \n 					END" +
				" \n 		END" +
				" \n END AS ET_A, " +
				" \n CASE WHEN PCOT0_B.PART_B >0" +
				" \n 		THEN" +
				" \n 			CASE WHEN PCOT99_B.PART_B = PCOT0_B.PART_B" +
				" \n 					THEN" +
				" \n 						CASE WHEN DATEDIFF(SS, DR_F.FECHA,TOTINFO_B.FMAX_B)<=259200" +
				" \n 								THEN 1" +
				" \n 								ELSE" +
				" \n 									CASE WHEN DATEDIFF(SS, DR_F.FECHA,TOTINFO_B.FMAX_B)>259200 AND (DATEPART(WEEKDAY,DR_F.FECHA)=1 OR DATEPART(WEEKDAY,DR_F.FECHA)= 2)" +
				" \n 											THEN 0" +
				" \n 											ELSE" +
				" \n 												CASE" +
				" \n 													WHEN((DATEDIFF(SS,DR_F.FECHA,TOTINFO_B.FMAX_B)-172800)<=259200)" +
				" \n 														THEN 1" +
				" \n 														ELSE 0" +
				" \n 												END" +
				" \n 									END" +
				" \n 						END" +
				" \n 					ELSE" +
				" \n 						CASE WHEN PCOT_0GES.PART_GES>0 AND COALESCE(PCOT99_B.PART_B,'0')>0 AND (COALESCE(PCOT99_B.PART_B,'0') + COALESCE(PCOT_0GES.PART_GES,'0'))= PCOT0_B.PART_B" +
				" \n 								THEN" +
				" \n 									CASE WHEN DATEDIFF(SS, DR_F.FECHA,TOTINFO_B.FMAX_B)<=259200 AND DATEDIFF(SS,DR_F.Fecha,PGESTION.FECHA)<=259200" +
				" \n 											THEN 1" +
				" \n 											ELSE" +
				" \n 												CASE WHEN DATEDIFF(SS, DR_F.FECHA,TOTINFO_B.FMAX_B)>259200 OR DATEDIFF(SS, DR_F.FECHA,PGESTION.FECHA)>259200 AND (DATEPART(WEEKDAY,DR_F.FECHA)=1 OR DATEPART(WEEKDAY,DR_F.FECHA)= 2)" +
				" \n 														THEN 0" +
				" \n 														ELSE" +
				" \n 															CASE" +
				" \n 																WHEN(DATEDIFF(SS,DR_F.FECHA,TOTINFO_B.FMAX_B)-172800 <=259200 AND DATEDIFF(SS,DR_F.FECHA,PGESTION.FECHA)-172800 =259200 )" +
				" \n 																	THEN 1" +
				" \n 																	ELSE 0" +
				" \n 															END" +
				" \n 												END" +
				" \n 									END" +
				" \n 								ELSE" +
				" \n 									CASE WHEN PCOT_0GES.PART_GES>0 AND PCOT_0GES.PART_GES = PCOT0_B.PART_B" +
				" \n 										THEN" +
				" \n 											CASE WHEN DATEDIFF(SS,DR_F.Fecha,PGESTION.FECHA)<=259200" +
				" \n 													THEN 1" +
				" \n 													ELSE" +
				" \n 														CASE WHEN DATEDIFF(SS,DR_F.Fecha,PGESTION.FECHA)>259200 AND (DATEPART(WEEKDAY,DR_F.FECHA)=1 OR DATEPART(WEEKDAY,DR_F.FECHA)= 2)" +
				" \n 																THEN 0" +
				" \n 																ELSE" +
				" \n 																	CASE" +
				" \n 																		WHEN(DATEDIFF(SS,DR_F.FECHA,PGESTION.FECHA)-172800<=259200)" +
				" \n 																			THEN 1" +
				" \n 																			ELSE 0" +
				" \n 																	END" +
				" \n 														END" +
				" \n 											END" +
				" \n 										ELSE" +
				" \n 										CASE WHEN PCOT_0GES.PART_GES>0" +
				" \n 												THEN" +
				" \n 													CASE WHEN DATEDIFF(SS, DR_F.FECHA,GETDATE())<=259200 AND DATEDIFF(SS,DR_F.Fecha,PGESTION.FECHA)<=259200" +
				" \n 															THEN 1" +
				" \n 															ELSE" +
				" \n 																CASE WHEN DATEDIFF(SS, DR_F.FECHA,GETDATE())>259200 OR DATEDIFF(SS,DR_F.Fecha,PGESTION.FECHA)>259200 AND (DATEPART(WEEKDAY,DR_F.FECHA)=1 OR DATEPART(WEEKDAY,DR_F.FECHA)=2)" +
				" \n 																		THEN 0" +
				" \n 																		ELSE" +
				" \n 																			CASE" +
				" \n 																				WHEN((DATEDIFF(SS,DR_F.FECHA,GETDATE())-172800)<=259200 AND DATEDIFF(SS,DR_F.FECHA,PGESTION.FECHA)<=259200)" +
				" \n 																					THEN 1" +
				" \n 																					ELSE 0" +
				" \n 																			END" +
				" \n 																END" +
				" \n 													END" +
				" \n 												ELSE" +
				" \n 													CASE WHEN DATEDIFF(SS, DR_F.FECHA,GETDATE())<=259200" +
				" \n 															THEN 1" +
				" \n 															ELSE" +
				" \n 																CASE WHEN DATEDIFF(SS, DR_F.FECHA,GETDATE())>259200 AND (DATEPART(WEEKDAY,DR_F.FECHA)=1 OR DATEPART(WEEKDAY,DR_F.FECHA)=2)" +
				" \n 																		THEN 0" +
				" \n 																		ELSE" +
				" \n 																			CASE" +
				" \n 																				WHEN((DATEDIFF(SS,DR_F.FECHA,GETDATE())-172800)<=259200 )" +
				" \n 																					THEN 1" +
				" \n 																					ELSE 0" +
				" \n 																			END" +
				" \n 																END" +
				" \n 													END" +
				" \n                                              END" +	
				" \n 									END" +
				" \n 						END" +
				" \n 			END" +
				" \n 		ELSE" +
				" \n 			CASE WHEN PCOT_0GES.PART_GES>0" +
				" \n 					THEN" +
				" \n 						CASE WHEN DATEDIFF(SS, DR_F.FECHA,PGESTION.FECHA)<=259200" +
				" \n 								THEN 1" +
				" \n 								ELSE" +
				" \n 									CASE WHEN DATEDIFF(SS, DR_F.FECHA,PGESTION.FECHA)>259200 AND (DATEPART(WEEKDAY,DR_F.FECHA)=1 OR DATEPART(WEEKDAY,DR_F.FECHA)= 2)" +
				" \n 											THEN 0" +
				" \n 											ELSE" +
				" \n 												CASE" +
				" \n 													WHEN((DATEDIFF(SS,DR_F.FECHA,PGESTION.FECHA)-172800)<=259200)" +
				" \n 														THEN 1" +
				" \n 														ELSE 0" +
				" \n 												END" +
				" \n 									END" +
				" \n 						END" +
				" \n 			END" +
				" \n END	AS ET_B" +
				" \n FROM Cotizas  COTI " +
				" \n LEFT JOIN (SELECT Clave,Nombre,Vendedor,FK01_EV FROM Clientes) AS CLI ON CLI.Nombre = COTI.Cliente   " +
				" \n LEFT JOIN (SELECT DATEADD(HH,18, CAST(CAST(MAX(Fecha) AS DATE) AS DATETIME)) AS FECHA, Numero FROM DoctosR GROUP BY Numero ) AS FHFIN ON FHFIN.Numero = COTI.Clave  " +
				" \n LEFT JOIN (SELECT Clave, DATEADD(HH,8, CAST(CAST(MAX(FGeneracion) AS DATE) AS DATETIME)) AS FMAX_A FROM PCotizas  WHERE Clasif='A' AND Folio=99 GROUP BY Clave) AS LIMHRS_A ON LIMHRS_A.Clave = COTI.Clave  " +
				" \n LEFT JOIN (SELECT Clave,  DATEADD(HH,8, CAST(CAST(COALESCE(MAX(FGeneracion),GETDATE()) AS DATE) AS DATETIME)) AS FMAX_B FROM PCotizas  WHERE Clasif<>'A' AND Folio=99 GROUP BY Clave) AS LIMHRS_B ON LIMHRS_B.Clave = COTI.Clave  " +
				" \n LEFT JOIN (SELECT COUNT(*) AS PART_A,Clave   FROM PCotizas WHERE Folio = 99 AND Clasif = 'A' GROUP BY Clave) AS PCOT99_A ON PCOT99_A.Clave= COTI.Clave   " +
				" \n LEFT JOIN (SELECT COUNT(*) AS PART_A,Clave   FROM PCotizas WHERE Folio = 0  AND Clasif = 'A'  GROUP BY Clave) AS PCOT0_A ON PCOT0_A.Clave=COTI.Clave  " +
				" \n LEFT JOIN (SELECT COUNT(*) AS PART_0,Clave   FROM PCotizas WHERE Folio = 0  AND ((Destino<>'Gestionando' AND Destino<>'No Cotizar') or Destino IS NULL) GROUP BY Clave) AS PCOT_0 ON PCOT_0.Clave = COTI.Clave  " +
				" \n LEFT JOIN (SELECT COUNT(*) AS PART_GES,Clave FROM PCotizas WHERE Folio = 0  AND Destino='Gestionando' GROUP BY Clave) AS PCOT_0GES ON PCOT_0GES.Clave = COTI.Clave  " +
				" \n LEFT JOIN (SELECT COUNT(*) AS PART_B,Clave   FROM PCotizas WHERE Folio = 99 AND Clasif <> 'A'  GROUP BY Clave) AS PCOT99_B ON PCOT99_B.Clave= COTI.Clave  " +
				" \n LEFT JOIN (SELECT COUNT(*) AS PART_B,Clave   FROM PCotizas WHERE Folio = 0  AND Clasif <> 'A'  GROUP BY Clave) AS PCOT0_B ON PCOT0_B.Clave= COTI.Clave  " +
				" \n LEFT JOIN (SELECT Clave, COALESCE(MAX(FGeneracion),GETDATE()) AS FMAX_A FROM PCotizas WHERE Clasif='A' AND Folio=99 GROUP BY Clave) AS TOTINFO_A ON TOTINFO_A.Clave = COTI.Clave  " +
				" \n LEFT JOIN (SELECT Clave, COALESCE(MAX(FGeneracion),GETDATE()) AS FMAX_B FROM PCotizas WHERE Clasif<>'A' AND Folio=99 GROUP BY Clave) AS TOTINFO_B ON TOTINFO_B.Clave = COTI.Clave  " +
				" \n LEFT JOIN (SELECT Numero, MAX(Folio) Folio, MAX(Fecha) Fecha, MAX(FHOrigen) FHOrigen, Medio FROM DoctosR GROUP BY Numero, Medio) AS DR ON DR.Numero = COTI.Clave  " +
				" \n LEFT JOIN (SELECT COUNT(Folio) AS NumP,Docto FROM Pendiente WHERE Tipo='Pedido a cotizar' GROUP BY Docto) AS pd ON pd.Docto=DR.Folio  " +
				" \n LEFT JOIN (SELECT Numero, MAX(FECHA) FECHA, MAX(FHOrigen) FHOrigen FROM DoctosR GROUP BY Numero) AS DR_F ON DR_F.Numero = COTI.Clave  " +
				" \n LEFT JOIN (SELECT SUM(PCotizas.Precio * PCotizas.Cant) AS Monto,Cotizas.Clave FROM Cotizas,PCotizas WHERE PCotizas.Clave=Cotizas.Clave AND Folio=99 GROUP BY Cotizas.Clave,Cotizas.Moneda) AS montoCotiza ON montoCotiza.Clave=COTI.Clave  " +
				" \n LEFT JOIN (SELECT SUM(pc.Cant * CASE WHEN c.IMoneda = 'Euros' THEN pc.Precio * moneda.EDolar WHEN c.IMoneda = 'Pesos' THEN pc.Precio / moneda.PDolar ELSE pc.Precio END) AS MontoUSD,c.Clave FROM PCotizas AS pc,Cotizas AS c,Monedas AS moneda  " +
				" \n					WHERE CAST(moneda.Fecha AS DATE)=CAST(c.Fecha AS DATE) AND pc.Clave=c.Clave AND pc.Folio=99 GROUP BY C.Clave) AS musd ON musd.Clave = COTI.Clave  " +
				" \n LEFT JOIN (SELECT COUNT(*) AS Gestionando,Clave FROM PCotizas WHERE (Destino='Gestionando' OR Destino='No Cotizar') AND Folio = 0 GROUP BY CLave) AS Gestionando ON Gestionando.Clave = COTI.Clave  " +
				" \n LEFT JOIN (SELECT COUNT(*) AS PCPedidas,Clave FROM PCotizas WHERE Folio = 99 AND (Estado='Pedido' OR Estado='Cancelada' OR Estado='Pedido cotizado' OR Estado='Recotizada') GROUP BY CLave) AS PartidasTerminadas ON PartidasTerminadas.Clave=COTI.Clave  " +
				" \n LEFT JOIN (SELECT COUNT(*) AS Ceros,Clave FROM PCotizas WHERE Folio = 0  GROUP BY CLave) AS PartidasEnCero ON PartidasEnCero.Clave=COTI.Clave  " +
				" \n LEFT JOIN (SELECT PCO.Clave, MAX(CASE WHEN PCO.FGeneracion IS NOT NULL THEN PCO.FGeneracion   ELSE CASE WHEN PCO.HEnvio <> '' AND PCO.HEnvio <> NULL  THEN  CAST(PHENVIO.HEnvio AS DATETIME) ELSE PHA.FFin END  END) AS FECHA FROM PCotizas AS PCO  " +
				" \n			 LEFT JOIN (SELECT FFin,Cotiza,Part FROM PCotPharma) AS PHA ON PHA.Cotiza= PCO.Clave AND PHA.Part = PCO.Partida  " +
				" \n			 LEFT JOIN (SELECT Destino,Clave,Partida,HEnvio FROM PCotizas) AS PHENVIO ON PHENVIO.Clave = PCO.Clave AND PCO.Partida=PHENVIO.Partida AND PHENVIO.Destino = PCO.Destino  " +
				" \n			 WHERE PCO.Destino  = 'Gestionando' GROUP BY PCO.Clave) AS PGESTION ON PGESTION.Clave = COTI.Clave " +
				" \n WHERE ";
		
//			logger.info(query + condiciones +" ORDER BY COTI.Fecha");
			//log.info(query + condiciones +" ORDER BY COTI.Fecha");
			return super.jdbcTemplate.query(query + condiciones +" ORDER BY COTI.Fecha" , new ReporteCotizacionRowMapper());
		} catch (Exception e) {
		//	logger.error("Error: " + e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Cotizacion> cotizacionAbiertaCerrada(String folio) throws ProquifaNetException {
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("folio", folio);
			String query="";
			String condicion=" ";

			if(folio!=null && !folio.equals("")){
				condicion = " AND C.Clave='"+ folio +"' ";
			}
			query = "SELECT C.Clave,(CASE WHEN Cero IS NULL THEN '0' ELSE Cero END) AS FolioCero,(CASE WHEN Nueve IS NULL THEN '0' ELSE Nueve END) AS FolioNueve,"+
					"(CASE WHEN (CASE WHEN Cero IS NULL THEN '0' ELSE Cero END)<=(CASE WHEN Nueve IS NULL THEN '0' ELSE Nueve END) AND (CASE WHEN Nueve IS NULL THEN '0' ELSE Nueve END)>0 "+
					"OR (CASE WHEN Nueve IS NULL THEN '0' ELSE Nueve END)=0 AND(CASE WHEN Cero IS NULL THEN '0' ELSE Cero END)=0 "+
					"THEN 1 ELSE 0 END) AS AbiertasCerradas "+
					"FROM Cotizas AS C "+
					"LEFT JOIN (SELECT COUNT(*) AS Cero,Clave FROM PCotizas WHERE Clave IN(SELECT Clave FROM Cotizas WHERE Fecha >= '20090101') AND Folio='0' AND Estado<>'Cancelada' AND ((Destino<>'Gestionando' AND Destino<>'No Cotizar') or Destino IS NULL) GROUP BY Clave) "+
					"AS P ON C.Clave=P.Clave "+
					"LEFT JOIN (SELECT COUNT(*) AS Nueve,Clave FROM PCotizas WHERE Clave IN(SELECT Clave FROM Cotizas WHERE Fecha >= '20090101') AND Folio='99' GROUP BY Folio,Clave) "+
					"AS N ON C.Clave=N.Clave "+
					"WHERE C.Fecha>='20090101'";
			return super.jdbcTemplate.query(query + condicion + "ORDER BY C.Clave ASC", new  CotizacionAbiertaCerradaRowMapper());
		} catch (Exception e) {
		//	logger.error("Error: " + e.getMessage());
			return null;
		}
	}

	@Override
	public DoctoR saveDoctoR(DoctoR docto) throws ProquifaNetException {
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Part", docto.getPart());
			map.put("Manejo", docto.getManejo());
			map.put("Origen", docto.getOrigen());
			map.put("IdCliente", docto.getCliente().getIdCliente());
			map.put("Medio",  docto.getMedio());
			map.put("docto.getDocto", docto.getDocto());
			map.put("Numero", docto.getNumero());
			
			map.put("Observa", docto.getObserva());
			map.put("Fproceso", docto.getFproceso() == null ? null : formatterTime.format(docto.getFproceso()));
			map.put("Ingreso", docto.getIngreso());
			map.put("Forigen",   docto.getForigen());
			map.put("Estado", docto.getEstado());
			map.put("FolioPadre", docto.getFolioPadre());
			
			map.put("isHijo", docto.isHijo());
			map.put("getFhorigen", docto.getFhorigen() == null ? null : formatterTime.format(docto.getFhorigen()));
			map.put("ClienteNuevo", docto.getClienteNuevo());
			map.put("Contacto",docto.getContacto() == null ? null : docto.getContacto().getIdContacto());
			map.put("Fechaps",docto.getFechaps() == null ? null : formatterTime.format(docto.getFechaps()));
			Object[] params = {docto.getPart(), docto.getManejo(), docto.getOrigen(), docto.getCliente().getIdCliente(), docto.getRpor(), docto.getMedio(), docto.getDocto(), docto.getNumero(), docto.getObserva(),
					docto.getFproceso() == null ? null : formatterTime.format(docto.getFproceso()), docto.getIngreso(), docto.getForigen(), docto.getEstado(), docto.getFolioPadre(), docto.isHijo(), 
					docto.getFhorigen() == null ? null : formatterTime.format(docto.getFhorigen()), docto.getClienteNuevo(), docto.getContacto() == null ? null : docto.getContacto().getIdContacto(), 
					docto.getFechaps() == null ? null : formatterTime.format(docto.getFechaps())};
			
			StringBuilder sbQuery = new StringBuilder("INSERT INTO DoctosR (Part, Fecha, Manejo, Origen, Empresa, RPor, Medio, Docto, Numero, Observa, FProceso, Ingreso, FOrigen, Estado, FolioPadre, esHijo, FHOrigen, CienteNuevo, idContacto, FechaPS, MontoDLS, SinReferencia, CodigoCampana) VALUES( \n");
			sbQuery.append("?,GETDATE(),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, NULL, NULL, NULL ");
			sbQuery.append(") ");
			
			super.jdbcTemplate.update(sbQuery.toString(), map);
			Integer folio = super.queryForInt("SELECT IDENT_CURRENT ('DoctosR')");
			docto.setFolio(folio);
			log.info("DOCTOR: " + folio);
			return docto;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}

	@Override
	public Cotizacion saveCotizacion(Cotizacion cotizacion, boolean VieneDeRepotarVisita) throws ProquifaNetException {
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("FolioCotizacion",cotizacion.getFolioCotizacion());
			map.put("Cliente", cotizacion.getCliente().getNombre());
			map.put("Contacto",cotizacion.getContactos().getNombre());
			map.put("Vendedor", cotizacion.getVendedor());
			map.put("Vigencia",	cotizacion.getVigencia());
			map.put("Moneda", cotizacion.getMoneda());
			map.put("Parciales",cotizacion.getParciales());
			map.put("CPago", cotizacion.getCpago());
			map.put("Lugar",cotizacion.getLugar());
			map.put("Zona", cotizacion.getZona());
			map.put("Estado",cotizacion.getEstado());
			map.put("FEnvio", cotizacion.getFEnvio() != null ? formatterTime.format(cotizacion.getFEnvio()) : null);
			map.put("Observa",cotizacion.getObserva());
			map.put("IMoneda", cotizacion.getImoneda());
			map.put("Cotizo", cotizacion.getCotizo());
			map.put("Factura",cotizacion.getFactura() );
			map.put("HEntrada",cotizacion.getHEntrada() );
			map.put("MEntrada",cotizacion.getMEntrada() );
			map.put("HSalida", cotizacion.getHSalida());
			map.put("MSalida", cotizacion.getMSalida());
			map.put("Confirmo",cotizacion.getConfirmo() );
			map.put("ObservaC",cotizacion.getObservaC() );
			map.put("FechaClasificacion", cotizacion.getFechaClasificacion() != null ? formatterTime.format(cotizacion.getFechaClasificacion()) : null );
			map.put("idContacto",cotizacion.getContactos().getIdContacto() );
			map.put("Canceladesde",cotizacion.getCanceladaDesde() );
			map.put("InfoFacturacion",cotizacion.getInfoFacturacion() );
			map.put("FEchaCierre",cotizacion.getFechaCierre() != null ? formatterTime.format(cotizacion.getFechaCierre()) : null );
			map.put("Abierto", cotizacion.getAbierto());
			map.put("FK01:idCliente ", cotizacion.getCliente().getIdCliente());
			map.put("isFuersaSistema",cotizacion.isFuersaSistema() );
			map.put("isGravaIva", cotizacion.isGravaIva());
			map.put("FKDoctoR",cotizacion.getDoctoR() );
			map.put("IdVisita",cotizacion.getIdVisita() );
			map.put("isGenerada", cotizacion.isGenerada());
			map.put("TipoCotizacion",cotizacion.getTipoCotizacion());
			map.put("NumCotizacion",cotizacion.getNumCotizacion() > 0 ? cotizacion.getNumCotizacion() : null);
			map.put("isDeSistema",cotizacion.isDeSistema());
			map.put("NombreCotizacion",cotizacion.getNombreCotizacion() );
			Object[] params = {
					cotizacion.getFolioCotizacion(), //Clave 
					cotizacion.getCliente().getNombre(), //Cliente
					cotizacion.getContactos().getNombre(),  //Contacto
					cotizacion.getVendedor(), //Vendedor
					cotizacion.getVigencia(), //Vigencia
					cotizacion.getMoneda(), //Moneda
					cotizacion.getParciales(), //Parciales
					cotizacion.getCpago(), //CPago
					cotizacion.getLugar(), //Lugar
					cotizacion.getZona(), //Zona
					cotizacion.getEstado(), //Estado
					cotizacion.getFEnvio() != null ? formatterTime.format(cotizacion.getFEnvio()) : null, //FEnvio
					cotizacion.getObserva(), //Observa 
					cotizacion.getImoneda(), //IMoneda
					cotizacion.getCotizo(), //Cotizo
					cotizacion.getFactura(),  //Factura
					cotizacion.getHEntrada(), //HEntrada
					cotizacion.getMEntrada(), //MEntrada
					cotizacion.getHSalida(), //HSalida
					cotizacion.getMSalida(), //MSAlida
					cotizacion.getConfirmo(), //Confirmo
					cotizacion.getObservaC(), //ObservaC
					cotizacion.getFechaClasificacion() != null ? formatterTime.format(cotizacion.getFechaClasificacion()) : null, //FechaClasificacion 
					cotizacion.getContactos().getIdContacto(), // idContacto
					cotizacion.getCanceladaDesde(), //Canceladesde
					cotizacion.getInfoFacturacion(), //InfoFacturacion
					cotizacion.getFechaCierre() != null ? formatterTime.format(cotizacion.getFechaCierre()) : null,//FEchaCierre 
					cotizacion.getAbierto(), // Abierto
					
					cotizacion.getCliente().getIdCliente(), //FK01:idCliente 
					cotizacion.isFuersaSistema(),  //FS
					cotizacion.isGravaIva(), // GravaIVA
					cotizacion.getDoctoR(), //FK02_DoctosR
					cotizacion.getIdVisita(),//FK03_idVisita
					cotizacion.isGenerada(), // Generada
					cotizacion.getTipoCotizacion(), // Tipo
					cotizacion.getNumCotizacion() > 0 ? cotizacion.getNumCotizacion() : null, // Orden
					cotizacion.isDeSistema(), //DeSitema
					cotizacion.getNombreCotizacion() // Nombre
					};
			
			StringBuilder sbQuery = new StringBuilder();
					
		
			sbQuery = new StringBuilder("INSERT INTO ");
			sbQuery.append("Cotizas (Clave, Fecha,Cliente,Contacto,Vendedor,Vigencia,Moneda,Parciales,CPago,Lugar,Zona,Estado,FEnvio,Observa,IMoneda,Cotizo,Factura,HEntrada,MEntrada,HSalida,MSalida,Confirmo,ObservaC,FechaClasif,idContacto,"
					+ "CanceladaDesde,InfoFacturacion,FechaCierre,Abierto,FK01_idCliente,FS,GravaIVA,FK02_DoctosR,FK03_idVisita,Generada,Tipo,Orden,DeSistema,Nombre)");
			sbQuery.append(" VALUES(?,GETDATE(),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )");
				
				
			
			//logger.info("doctor:" + cotizacion.getDoctoR());	
			//logger.info(sbQuery);
			jdbcTemplate.update(sbQuery.toString(), map);
			Long idCotiza = super.queryForLong("SELECT IDENT_CURRENT ('Cotizas')");
			cotizacion.setIdCotizacion(idCotiza);
			log.info("Cotizacion: " + idCotiza);
			return cotizacion;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}

	@Override
	public List<PartidaCotizacion> savePartidasCotizacion(List<PartidaCotizacion> partidas)
			throws ProquifaNetException {
		return null;
	}
	
	@Override
	public List<SeguimientoCotizacion> saveSegCotiza(List<SeguimientoCotizacion> segCotizas) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("segCotizas",segCotizas);
		
			
			for (SeguimientoCotizacion segCotiza : segCotizas) {
				Object[] params = {segCotiza.getFolioCotizacion(), segCotiza.getUsuarioVendedor(), segCotiza.getNombreContacto(), segCotiza.getRazon(), segCotiza.getTipoSeguimiento(), 
						segCotiza.getFechaCotizacion(), segCotiza.getSituacion(), segCotiza.getFuaccion(), segCotiza.getEstadoSeguimiento(), segCotiza.getDoctoAmpara(), segCotiza.getIdCotizacion()};
				
				StringBuilder sbQuery = new StringBuilder("INSERT INTO SegCotiza VALUES(?, ?, GETDATE(), ?, ?, ?, ?, ?, ?, ?, ?, ?)");
				
				jdbcTemplate.update(sbQuery.toString(), map);
				Integer idSegCotiza = super.queryForInt("SELECT IDENT_CURRENT ('SegCotiza')");
				segCotiza.setIdSegCotiza(idSegCotiza);
				log.info("SegCotiza: " + idSegCotiza);
			}
			return segCotizas;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}

	@Override
	public DoctoR obtenerDatosContactoParaDoctoR(Integer contacto) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("contacto",contacto);
		
			
			StringBuilder sbQuery = new StringBuilder("SELECT CL.Clave Empresa, CL.Vendedor RPor, C.idContacto, CL.Nombre Cliente, C.Contacto, \n");
			sbQuery.append("CL.MonedaFactura IMoneda, CL.Zona, CL.CPago, CL.Factura, CL.Parciales, E.Nombre Empleado, C.eMail, CL.Calle, C.Fax, C.Tel1 \n");
			sbQuery.append("FROM Contactos C \n");
			sbQuery.append("INNER JOIN Clientes CL ON CL.Clave = C.FK02_Cliente \n");
			sbQuery.append("LEFT JOIN Empleados E ON E.Usuario = CL.Vendedor \n");
			sbQuery.append("WHERE C.idContacto = ").append(contacto);
			sbQuery.append(" \n");
			log.info(sbQuery.toString());
			DoctoR docto = (DoctoR) super.jdbcTemplate.queryForObject(sbQuery.toString(), map, new DoctoRRowMapper());
			return docto;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	
	@Override
	public boolean actualizarCotizacion(Cotizacion cotizacion) throws ProquifaNetException {
			try {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("cotizacion",cotizacion);
				
				StringBuilder sbQuery = new StringBuilder("\n");
				
					sbQuery.append("UPDATE Cotizas SET Generada = 1");
					sbQuery.append(", HSalida = '").append(cotizacion.getHSalida());
					sbQuery.append("' WHERE PK_Folio =").append(cotizacion.getIdCotizacion());
				
				jdbcTemplate.update(sbQuery.toString(), map);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ProquifaNetException();
			}
			return false;
		}
	
	
	
	@Override
	public boolean eliminarPartidasCotizacionPoridCotizacion(Long idCotizacion) throws ProquifaNetException {
			try {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("idCotizacion",idCotizacion);
				
				StringBuilder sbQuery = new StringBuilder("\n");
					sbQuery.append("DELETE FROM PCotizas WHERE FK02_Cotiza ='").append(idCotizacion);
					sbQuery.append("'");
				jdbcTemplate.update(sbQuery.toString(),map );
			} catch (Exception e) {
				e.printStackTrace();
				throw new ProquifaNetException();
			}
			return false;
		}
	
	
	@Override
	public boolean eliminarCotizacionPorIdVisita(Long idVisitaCliente) throws ProquifaNetException {
			try {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("idVisitaCliente",idVisitaCliente);
				

				StringBuilder sbQuery = new StringBuilder("\n");
					sbQuery.append("DELETE FROM Cotizas WHERE FK03_idVisita =").append(idVisitaCliente);
				
				jdbcTemplate.update(sbQuery.toString(),map );
			} catch (Exception e) {
				e.printStackTrace();
				throw new ProquifaNetException();
			}
			return false;
		}
	
	
	@Override
	public boolean actualizarDoctosRidCotizacion(String folioCot, Integer folio) throws ProquifaNetException {
			try {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("folioCot",folioCot);
				map.put("folio",folio);

				StringBuilder sbQuery = new StringBuilder("\n");
				
					sbQuery.append("UPDATE DoctosR SET Numero = '").append(folioCot);
					sbQuery.append("' WHERE Folio =").append(folio);
				
				jdbcTemplate.update(sbQuery.toString(),map);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ProquifaNetException();
			}
			return false;
		}
	
	
	@Override
	public boolean EliminarDoctors(String folios) throws ProquifaNetException {
			try {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("folios",folios);

				StringBuilder sbQuery = new StringBuilder("\n");
					sbQuery.append("DELETE FROM DoctosR WHERE Folio in (").append(folios);
					sbQuery.append(")");
				jdbcTemplate.update(sbQuery.toString(),map);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ProquifaNetException();
			}
			return false;
		}
	
	
	@Override
	 public String obtenerFoliosDoctoR(Long idVisita) throws ProquifaNetException {
		
		StringBuilder sbQuery = new StringBuilder(" ");
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idVisita",idVisita);
			
			  sbQuery.append("SELECT folios = STUFF(( " );
			  sbQuery.append(" \n SELECT ','+ CAST(P.FK02_DoctosR as varchar(max)) FROM ( ");
			  sbQuery.append(" \n  SELECT FK02_DoctosR from Cotizas where FK03_idVisita = ").append(idVisita);
			  sbQuery.append(" \n  GROUP BY FK02_DoctosR ) P  " );
			  sbQuery.append(" \n  FOR XML PATH('') ), 1, 1, '') " );
			  
			  log.info(sbQuery.toString());
			String lista = (String) super.jdbcTemplate.queryForObject(sbQuery.toString(), map, String.class);
			
			return  lista;
		}catch(Exception e){
		//	logger.error("Error: " + e.getMessage());
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	

	
}
	
	
