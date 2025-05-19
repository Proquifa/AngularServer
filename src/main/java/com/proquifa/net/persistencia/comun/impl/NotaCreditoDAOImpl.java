/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.cobrosypagos.facturista.NotaCredito;
import com.proquifa.net.modelo.comun.Empresa;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.cobrosypagos.facturista.impl.mapper.infoEmpresaNotaCreditoRowMapper;
import com.proquifa.net.persistencia.comun.NotaCreditoDAO;
import com.proquifa.net.persistencia.comun.impl.mapper.NotaCreditoAvanzadaRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.NotaCreditoRowMapper;

/**
 * @author vromero
 *
 */
@Repository
public class NotaCreditoDAOImpl extends DataBaseDAO implements NotaCreditoDAO {
	
	final Logger log = LoggerFactory.getLogger(NotaCreditoDAOImpl.class);

	@SuppressWarnings("unchecked")
	public List<NotaCredito> obtenerNotasDeCreditoPorCliente(Long idcliente) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idcliente", idcliente);
			String query = 	" \n SELECT *, case when NC.Estado is null then 'POR APLICAR' else 'APLICADA' end EstadoNC , COALESCE(NC.tipo,'POR FLUJO') tipoNC," +
							" \n CASE WHEN NC.Moneda = 'Dolares' OR NC.Moneda = 'USD' THEN NC.Importe WHEN NC.Moneda = 'EUR' OR NC.Moneda = 'Euros' THEN M.EDolar * NC.Importe ELSE NC.Importe  END IMPORTEUDS" +
							" \n FROM NotaCredito NC" +
							" \n LEFT JOIN (SELECT Clave, Nombre, CPago, Factura FROM Clientes) AS CLI ON CLI.Clave = NC.FK01_Cliente" +
							" \n LEFT JOIN (SELECT idFactura, Factura AS NoFac, CPedido FROM Facturas ) AS FAC ON FAC.idFactura = NC.FK02_Factura" +
							" \n LEFT JOIN (SELECT * FROM Monedas) M ON YEAR(M.Fecha) = YEAR(NC.Fecha) AND MONTH(M.Fecha) = MONTH(NC.Fecha) AND DAY(M.Fecha) = DAY(NC.Fecha)" +
							" \n WHERE NC.FK01_Cliente = :idcliente ";
			//logger.info(query);
			return super.jdbcTemplate.query(query, new NotaCreditoRowMapper());
		}catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}
	
	/**
	 * Consulta Avanzada de Notas de Credito
	 */
	@SuppressWarnings("unchecked")
	public List<NotaCredito> notaCreditoAvanzada(Date fechaInicio, Date fechaFin, String condiciones){
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd");
		
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			String query = 
					" \n SELECT CONVERT(DATE,CONVERT(VARCHAR,NC.Fecha,111)) AS Fecha, C.Nombre AS Cliente, NC.AK_Folio AS NotaCredito, COALESCE(F.FPor COLLATE Modern_Spanish_CI_AS,E.Alias ) AS Facturo, " +
					" \n CASE WHEN NC.Moneda = 'Pesos' THEN NC.Importe/NC.TCambio ELSE NC.Importe END AS Importe, " +
					" \n F.Factura, F.CPedido, NC.Tipo, " +
					" \n CASE WHEN NC.Estado <> 'Cancelada' THEN 'POR APLICAR' WHEN NC.Estado IS NULL THEN 'POR APLICAR' " +
					" \n WHEN NC.Estado ='Cancelada' THEN 'APLICADA' ELSE NC.Estado END AS Estado, COALESCE(c.Vendedor,'No asignado') Vendedor, COALESCE( emp.Usuario,'No asignado') Cobrador, " +
					" \n FNC.Factura AS FacturaDestino " +
					" \n FROM NotaCredito NC " +
					" \n LEFT JOIN Facturas F ON NC.FK02_Factura = F.idFactura " +
					" \n INNER JOIN Clientes C ON NC.FK01_Cliente = C.Clave " +
					" \n LEFT JOIN Empleados emp ON emp.Clave = c.Cobrador " +
					" \n LEFT JOIN Empresa E ON E.PK_Empresa = NC.FK03_Empresa " +
					" \n LEFT JOIN Factura_NotaCredito ON NC.PK_Nota=Factura_NotaCredito.FK02_NotaCredito " +
					" \n LEFT JOIN ( SELECT FA.Factura,FA.idFactura,FN.FK02_NotaCredito from Factura_NotaCredito FN LEFT JOIN Facturas FA  ON FA.idFactura=FN.FK01_Factura) FNC ON FNC.FK02_NotaCredito=Factura_NotaCredito.FK02_NotaCredito " +
					" \n WHERE (NC.Fecha >= '" +  formatoFecha.format(fechaInicio) + " 00:00' " +
					" \n AND NC.Fecha <= '" + formatoFecha.format(fechaFin) + " 23:59') " +
					" \n  " + condiciones +" \n ORDER BY NC.Fecha";
			
			//logger.info(query.toString());
			
			return super.jdbcTemplate.query(query, new NotaCreditoAvanzadaRowMapper());
			
		}catch (Exception e) {
			log.info("Error: " + e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<NotaCredito> notaCreditoRapida(Long iFolioNotaCredito, String sPedidoInterno,String condiciones){
		try {
			
			String query = 
					" \n SELECT CONVERT(DATE,CONVERT(VARCHAR,NC.Fecha,111)) AS Fecha, C.Nombre AS Cliente, NC.AK_Folio AS NotaCredito, COALESCE(F.FPor COLLATE Modern_Spanish_CI_AS,E.Alias ) AS Facturo, " +
					" \n CASE WHEN NC.Moneda = 'Pesos' THEN NC.Importe/NC.TCambio ELSE NC.Importe END AS Importe, " +
					" \n F.Factura, F.CPedido, NC.Tipo, COALESCE(c.Vendedor,'No asignado') Vendedor,  COALESCE( emp.Usuario,'No asignado') Cobrador, " +
					" \n CASE WHEN NC.Estado <> 'Cancelada' THEN 'POR APLICAR' WHEN NC.Estado IS NULL THEN 'POR APLICAR' " +
					" \n WHEN NC.Estado ='Cancelada' THEN 'APLICADA' ELSE NC.Estado END AS Estado, " +
					" \n FNC.Factura AS FacturaDestino " +
					" \n FROM NotaCredito NC " +
					" \n LEFT JOIN Facturas F ON NC.FK02_Factura = F.idFactura " +
					" \n INNER JOIN Clientes C ON NC.FK01_Cliente = C.Clave " +
					" \n LEFT JOIN Empleados emp ON emp.Clave = c.Cobrador " +
					" \n LEFT JOIN Empresa E ON E.PK_Empresa = NC.FK03_Empresa " +
					" \n LEFT JOIN Factura_NotaCredito ON NC.PK_Nota=Factura_NotaCredito.FK02_NotaCredito " +
					" \n LEFT JOIN ( SELECT FA.Factura,FA.idFactura,FN.FK02_NotaCredito from Factura_NotaCredito FN LEFT JOIN Facturas FA  ON FA.idFactura=FN.FK01_Factura) FNC ON FNC.FK02_NotaCredito=Factura_NotaCredito.FK02_NotaCredito ";

			if (iFolioNotaCredito > 0){
				query += " \n WHERE NC.AK_Folio = " + iFolioNotaCredito.toString() + " "; 
			}
			else{
				log.info("Entro a F.Pedito con valor de: " + sPedidoInterno);
				query += " \n WHERE F.CPedido = '" + sPedidoInterno.toString() + "' ";
			}
			
			query += condiciones;
			query += " \n ORDER BY NC.Fecha";
			
			//logger.info(query.toString());
			return super.jdbcTemplate.query(query.toString(),  new NotaCreditoAvanzadaRowMapper());
					
		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Empresa> getFoliosNotaCredito() throws ProquifaNetException {
		try {
			String query = 
					" \n SELECT EM.*,EM.Alias, COALESCE((CON.Valor - ASI.FolioInicio)+1,0) AS FActual,COALESCE((ASI.FolioFinal-ASI.FolioInicio)+1,0) AS FoliosAsignados," +
					" \n COALESCE(NC_U.Usados,0)  AS FUsadosMes, COALESCE( MONTO.TOTALMONTODLL, 0) TOTALMONTODLL, COALESCE(NC_CAN.TOTCANCELADAS,0) TOTCANCELADAS, " +
					" \n CASE WHEN EM.FacturacionElectronica = 1 AND EM.FacturacionMatriz=1 AND EM.PK_Empresa = ASI.FK01_Empresa AND CF.FK01_Empresa=EM.PK_Empresa THEN 1 ELSE 0 END AS FacHab " +
					" \n FROM Empresa EM " +
					" \n LEFT JOIN (SELECT CASE WHEN F.FPor = 'ProquifaServicios' THEN 'Proquifa Servicios' WHEN F.FPor = 'ProquifaElSalvador' THEN 'Proquifa El Salvador' ELSE F.FPor	END AS Fpor, " +
					" \n 			SUM(CASE WHEN NC.Moneda='USD' OR NC.Moneda = 'Dolares' THEN  NC.Importe  WHEN NC.Moneda= 'EUR' OR NC.Moneda = 'Euros'  THEN  NC.Importe * MO.EDolar " +
					" \n 				ELSE  NC.Importe / CASE WHEN NC.TCambio IS NULL OR NC.TCambio = 0 THEN 1 ELSE NC.TCambio END  END) AS TOTALMONTODLL" +
					" \n 			FROM NotaCredito AS NC" +
					" \n 			LEFT JOIN (SELECT idFactura, FPor FROM Facturas) F ON F.idFactura = NC.FK02_Factura " +
					" \n 			LEFT JOIN (SELECT * FROM Monedas) AS MO ON CAST(MO.Fecha AS DATE)=CAST(NC.Fecha AS DATE) " +
					" \n 			WHERE (NC.Estado<>'Cancelada' OR NC.Estado IS NULL) AND YEAR(NC.Fecha)=YEAR(GETDATE()) AND MONTH(NC.Fecha)=MONTH(GETDATE()) GROUP BY F.FPor) AS MONTO ON MONTO.FPor = (EM.Alias COLLATE Modern_Spanish_CI_AS) " +
					" \n LEFT JOIN (SELECT PK_Empresa, 'Nota de credito ' + CASE WHEN Alias='Proquifa Servicios'	THEN 'ProquifaServicios' WHEN Alias = 'Proquifa El Salvador' THEN 'ProquifaElSalvador' ELSE Alias END COLLATE Modern_Spanish_CI_AS AS PF FROM EMPRESA ) " +
					" \n 		  AS CPCONSE ON CPCONSE.PK_Empresa = EM.PK_Empresa " +
					" \n LEFT JOIN (SELECT * FROM Consecutivos)AS CON ON CON.Concepto= CPCONSE.PF " +
					" \n LEFT JOIN (SELECT CASE WHEN FPor = 'ProquifaServicios' THEN 'Proquifa Servicios' WHEN FPor = 'ProquifaElSalvador' THEN 'Proquifa El Salvador' ELSE FPor END AS Fpor,COUNT(*) AS Usados " +
					" \n 		   FROM NotaCredito AS NC " +
					" \n 		   LEFT JOIN (SELECT FPor, idFactura FROM Facturas) FA ON FA.idFactura = NC.FK02_Factura" +
					" \n 		   WHERE YEAR(NC.Fecha)=YEAR(GETDATE()) AND MONTH(NC.Fecha)=MONTH(GETDATE()) GROUP BY FPor) AS NC_U ON NC_U.FPor = (EM.Alias COLLATE Modern_Spanish_CI_AS) " +
					" \n LEFT JOIN (SELECT CASE WHEN FA.FPor = 'ProquifaServicios' THEN 'Proquifa Servicios' WHEN FPor = 'ProquifaElSalvador' THEN 'Proquifa El Salvador' ELSE  FA.FPor END AS Fpor,COUNT (*) AS TOTCANCELADAS " +
					" \n 			FROM NotaCredito AS NC " +
					" \n 			LEFT JOIN (SELECT FPor, idFactura FROM Facturas) FA ON FA.idFactura = NC.FK02_Factura" +
					" \n 			WHERE NC.Estado='Cancelada' AND YEAR(NC.Fecha)=YEAR(GETDATE()) AND MONTH(NC.Fecha)=MONTH(GETDATE()) GROUP BY FA.FPor) AS NC_CAN ON NC_CAN.FPor = (EM.Alias COLLATE Modern_Spanish_CI_AS) " +
					" \n LEFT JOIN (SELECT * FROM AsignacionFolio WHERE Asignado=1 AND Tipo = 'Nota credito') AS ASI ON ASI.FK01_Empresa = EM.PK_Empresa " +
					" \n LEFT JOIN (SELECT FK01_Empresa FROM ConceptoFactura GROUP BY FK01_Empresa) AS CF ON CF.FK01_Empresa = EM.PK_Empresa " +
					" \n ORDER BY FacHab";
			
			//logger.info(query);
			return super.jdbcTemplate.query(query, new infoEmpresaNotaCreditoRowMapper());

		} catch (Exception e) {
			log.info( e.getMessage());
			return null;
		}
	}

	@Override
	public Double getTotalNotaCreditoClienteEmpresa(String condiciones)
			throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("condiciones", condiciones);
			String query = 	"	SELECT coalesce(SUM (CASE WHEN NC.Moneda = 'Dolares' OR NC.Moneda = 'USD' THEN NC.Importe WHEN NC.Moneda = 'EUR' OR NC.Moneda = 'Euros' THEN M.EDolar * NC.Importe ELSE NC.Importe END),0) T " +
							"	FROM NotaCredito NC" +
							"	LEFT JOIN (SELECT * FROM Monedas) M ON YEAR(M.Fecha) = YEAR(NC.Fecha) AND MONTH(M.Fecha) = MONTH(NC.Fecha) AND DAY(M.Fecha) = DAY(NC.Fecha)" +
							"	LEFT JOIN (SELECT * FROM Empresa) E ON E.PK_Empresa= NC.FK03_Empresa" +
							"	WHERE NC.Estado IS NULL " + condiciones;
			//logger.info(query);
			return (Double) super.jdbcTemplate.queryForObject(query,map, Double.class);
		}catch(Exception e){
			log.info(e.getMessage());
			return null;
		}
	}

	@Override
	public Boolean existNotaCreditoPeriodo(String condiciones)
			throws ProquifaNetException {
		try{
			String query = 	" 	SELECT COUNT(NC.PK_Nota) FROM NotaCredito NC" +
							"	LEFT JOIN (SELECT * FROM Empresa) E ON E.PK_Empresa = NC.FK03_Empresa" +
							"	WHERE NC.Tipo = 'Periodo' " + condiciones;
			//logger.info(query);
			int r = super.queryForInt(query);
			if(r > 0){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			log.info(e.getMessage());
			return null;
		}
	}

	@Override
	public Long insertNotaCreditoPorFactura(Long idEmpresa, Long idCliente,
			Long idFactura, Double monto, Double iva, String folio, 
			String moneda, Double tipoCambio, String serie) throws ProquifaNetException {
		
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idEmpresa", idEmpresa);
			map.put("idCliente", idCliente);
			map.put("idFactura", idFactura);
			map.put("monto", monto);
			map.put("iva", iva);
			map.put("folio", folio);
			map.put("moneda", moneda);
			map.put("tipoCambio", tipoCambio);
			map.put("serie", serie);
			String query = 	" INSERT INTO NotaCredito(FECHA,FK01_Cliente, FK02_Factura, Estado, Importe, IVA, TCambio, Moneda, Serie, Medio, FK03_Empresa, Tipo, AK_Folio)" +
							" VALUES (GETDATE(), " + idCliente + ", " + idFactura + ", NULL, " + monto + ", " + iva + ", " + tipoCambio + ", '" + moneda + "', '" + serie + "', 'Electronica', " + idEmpresa + ", 'Factura' , " + folio + " )" ;
							
			super.jdbcTemplate.update(query,map);
			
			return super.queryForLong("SELECT IDENT_CURRENT ('NotaCredito')");
		}catch (Exception e) {
			log.info(e.getMessage());
			return -1L;// TODO: handle exception
		}
		
	}

	@Override
	public Long insertNotaCretitoPorPeriodo(Long idEmpresa, Long idCliente,
			Date ffinicio, Date ffin, Double monto, Double iva, String folio, 
			String moneda, Double tipoCambio, String serie) throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idEmpresa", idEmpresa);
			map.put("idCliente", idCliente);
			map.put("ffinicio", ffinicio);
			map.put("ffin", ffin);
			map.put("monto", monto);
			map.put("iva", iva);
			map.put("folio", folio);
			map.put("moneda", moneda);
			map.put("tipoCambio", tipoCambio);
			map.put("serie", serie);
		
			String query = 	"INSERT INTO NotaCredito(FECHA,FK01_Cliente, FK02_Factura, Estado, Importe, IVA, TCambio, Moneda, Serie, Medio, FK03_Empresa, Tipo, InicioPeriodo, FinPeriodo, AK_Folio)"+
							"VALUES (GETDATE(), ?, NULL, NULL, ?, ?, ? , ?, ?, 'Electronica', ?, 'Periodo', ?, ? , ?) ";
			super.jdbcTemplate.update(query, map);
			
			//logger.info(query);
			return (Long) super.queryForLong("SELECT IDENT_CURRENT ('NotaCredito')");
			
		}catch (Exception e) {
			log.info(e.getMessage());
			return -1L;// TODO: handle exception
		}
		
	}

	@Override
	public Double getMontoNotaCreditoPorFactura(Long factura, String empresa)
			throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("factura", factura);
			map.put("empresa", empresa);
			String query = 	"	SELECT COALESCE(SUM(CASE WHEN NC.Moneda = 'Dolares' OR NC.Moneda = 'USD' THEN NC.Importe WHEN NC.Moneda = 'EUR' OR NC.Moneda = 'Euros' THEN M.EDolar * NC.Importe ELSE NC.Importe / NC.TCambio END) ,0) T" +
							"	FROM NotaCredito NC" +
							"	LEFT JOIN (SELECT * FROM Facturas) F ON F.idFactura = NC.FK02_Factura" +
							"	LEFT JOIN (SELECT * FROM Monedas) M ON YEAR(M.Fecha) = YEAR(NC.Fecha) AND MONTH(M.Fecha) = MONTH(NC.Fecha) AND DAY(M.Fecha) = DAY(NC.Fecha)" +
							"	WHERE NC.Estado IS NULL AND F.Factura = :factura  AND F.FPor = ':empresa '";
			//logger.info(query);
			return (Double) super.jdbcTemplate.queryForObject(query,map, Double.class);
		}catch (Exception e) {
			log.info(e.getMessage());
			return null;// TODO: handle exception
		}
	}

	@Override
	public Boolean updateFacturaEstadoNotaCredito(String factura, String fpor)
			throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("factura", factura);
			map.put("fpor", fpor);
		
			
			String query = "UPDATE PFacturas SET Estado = 'Nota credito' WHERE Factura = ? AND FPor = ? AND Estado IS NULL";
			//logger.info(query);
			this.jdbcTemplate.update(query, map);
			return true;
		
		} catch (Exception e) {
			//logger.info(e.getMessage());
			return false;
		}
	}

	@Override
	public Boolean deleteNotaCredito(Long idNota) throws ProquifaNetException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idNota", idNota);
		this.jdbcTemplate.update("	DELETE NotaCredito WHERE PK_Nota =idNota",map);
		//logger.info("DELETE NotaCredito WHERE PK_Nota = " + idNota);
		
		return true;
	}


}
