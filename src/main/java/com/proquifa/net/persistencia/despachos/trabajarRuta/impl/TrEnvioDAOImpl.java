package com.proquifa.net.persistencia.despachos.trabajarRuta.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.ParametroEnvio;
import com.proquifa.net.modelo.comun.Pendiente;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.despachos.colectarPartidas;
import com.proquifa.net.modelo.despachos.trabajarRuta.TrAlmacen;
import com.proquifa.net.modelo.despachos.trabajarRuta.TrEnvio;
import com.proquifa.net.modelo.despachos.trabajarRuta.TrPackingList;
import com.proquifa.net.modelo.despachos.trabajarRuta.TrTotalGraficasAlmacen;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.comun.PendienteDAO;
import com.proquifa.net.persistencia.despachos.trabajarRuta.TrEnvioDAO;

@Repository
public class TrEnvioDAOImpl extends DataBaseDAO implements TrEnvioDAO {

	@Autowired
	PendienteDAO pendienteDAO;
	
	final Logger log = LoggerFactory.getLogger(TrEnvioDAOImpl.class);

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public  Map<String, Integer> getObjetivos() throws ProquifaNetException{
		try{
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append(" \n");
			sbQuery.append("DECLARE @TABLA TABLE (Folio varchar(100), FPEntrega datetime, FFin datetime) \n");
			sbQuery.append("INSERT INTO @TABLA  \n");
			sbQuery.append("SELECT PL.Folio, CAST(PP.FPEntrega as DATE) FPentrega, PEN.FFin \n");
			sbQuery.append("FROM Pendiente PEN  \n");
			sbQuery.append("INNER JOIN PackingList PL ON PL.Folio = PEN.Docto COLLATE Modern_Spanish_CI_AS \n"); 
			sbQuery.append("INNER JOIN PPackingList PPL ON PPL.FK01_PackingList = PL.PK_PackingList  \n");
			sbQuery.append("INNER JOIN EmbalarPedido EP ON  EP.Estado = 'Embalado' AND EP.PK_EmbalarPedido = PPL.FK02_EmbalarPEdido \n"); 
			sbQuery.append("INNER JOIN PPedidos PP ON PP.idPPedido = EP.FK01_PPedido  \n");
			sbQuery.append("INNER JOIN PEDIDOS P ON P.Cpedido = PP.Cpedido \n");
			sbQuery.append("WHERE PEN.Tipo = 'Por Enviar'  AND (P.GuiaXCliente = 0 OR P.GuiaXCliente IS NULL) AND (PEN.Partida IS NULL OR PEN.Partida = 0) \n");
			sbQuery.append("GROUP BY PL.Folio, CAST(PP.FPEntrega as DATE), PEN.FFin \n");
			sbQuery.append("SELECT 'Objetivo' tipo, COALESCE(MAX(cant),0) cant FROM ( \n"); 
			sbQuery.append("SELECT COUNT(Folio) cant FROM @Tabla \n");
			sbQuery.append("WHERE FFin IS NOT NULL AND CAST(FFin as DATE) < CAST(GETDATE() as DATE) \n");
			sbQuery.append("GROUP BY CAST(FFin as DATE)) Objetivo  \n");
			sbQuery.append("UNION ALL \n");
			sbQuery.append("SELECT 'Hoy' tipo, COUNT(Folio) cant FROM @Tabla \n");
			sbQuery.append("WHERE CAST(FFin as DATE) = CAST(GETDATE() as DATE) \n");
			sbQuery.append("UNION ALL \n");
			sbQuery.append("SELECT 'Deseadas' AS tipo, COUNT(Folio) cant FROM @Tabla \n");
			sbQuery.append("WHERE (FFin IS NULL OR CAST(FFin as DATE) = CAST(GETDATE() AS DATE)) \n");
			sbQuery.append("UNION ALL \n");
			sbQuery.append("SELECT tipo, CASE WHEN cant = 0 THEN 1 ELSE cant END cant FROM ( \n");
			sbQuery.append("SELECT TOP 1 'Minimo' tipo, COALESCE(COUNT(Folio),0)/2 cant FROM @Tabla \n");
			sbQuery.append("WHERE FFin IS NOT NULL AND CAST(FFin as DATE) < CAST(GETDATE() as DATE) \n");
			sbQuery.append("GROUP BY CAST(FFin as DATE) ORDER BY cant desc) Minimo \n");

			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Integer> mapReturn = new HashMap<String, Integer>();
			jdbcTemplate.query(sbQuery.toString(), map, new RowMapper() {
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					mapReturn.put(rs.getString("tipo"),  rs.getInt("cant"));

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
	public  Map<String, Integer> getMontosTab() throws ProquifaNetException{
		try{
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append(" \n");
			sbQuery.append("DECLARE @variableManana as date\n" + 
					"SET @variableManana = DATEADD(DAY, 1, getdate())\n" + 
					"DECLARE @variablePasado as date\n" + 
					"SET @variablePasado = DATEADD(DAY, 2, getdate())\n" + 
					"DECLARE @variableHoy as date\n" + 
					"SET @variableHoy = getdate()\n" + 
					"DECLARE @TABLA TABLE (docto varchar(100), FPEntrega datetime)\n" + 
					"insert into @TABLA \n" + 
					"SELECT p.docto, pp.FPEntrega FROM Pendiente p\n" + 
					"LEFT JOIN PackingList pl on pl.Folio COLLATE DATABASE_DEFAULT = p.Docto COLLATE DATABASE_DEFAULT\n" + 
					"LEFT JOIN PPackingList ppl ON ppl.FK01_PackingList = pl.PK_PackingList\n" + 
					"LEFT JOIN EmbalarPedido ep ON ep.PK_EmbalarPedido = ppl.FK02_EmbalarPedido\n" + 
					"LEFT JOIN Ppedidos pp ON pp.idPPedido = ep.FK01_PPedido\n" + 
					"LEFT JOIN Pedidos pd ON pd.Cpedido = pp.Cpedido\n" + 
					"LEFT JOIN Clientes c ON c.Clave = pd.idCliente\n" + 
					"WHERE p.tipo = 'Por Enviar' AND p.FFin is null AND c.clave is not null AND (P.Partida IS NULL OR P.Partida = 0) GROUP BY p.docto,  pp.FPEntrega;\n" + 
					"SELECT 'Hoy' as tab, COUNT(*) as num FROM @TABLA\n" + 
					"where FPEntrega <= @variableHoy\n" + 
					"UNION\n" + 
					"SELECT 'Mañana' as tab, COUNT(*) FROM @TABLA\n" + 
					"where FPEntrega = @variableManana\n" + 
					"UNION\n" + 
					"SELECT 'Pasado' as tab, COUNT(*) FROM @TABLA\n" + 
					"where FPEntrega = @variablePasado\n" + 
					"UNION\n" + 
					"SELECT 'Futuro' as tab, COUNT(*) FROM @TABLA\n" + 
					"where FPEntrega > @variablePasado\n" + 
					"UNION\n" + 
					"SELECT 'Todo' as tab, COUNT(*) FROM @TABLA");

			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Integer> mapReturn = new HashMap<String, Integer>();
			jdbcTemplate.query(sbQuery.toString(), map, new RowMapper() {
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					mapReturn.put(rs.getString("tab"),  rs.getInt("num"));

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
	public Map<String, Map<String, List<TrTotalGraficasAlmacen>>> getGraficas() throws ProquifaNetException{
		try{
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append(" \n");
			sbQuery.append("DECLARE @variableManana as date\n" + 
					"SET @variableManana = DATEADD(DAY, 1, getdate())\n" + 
					"DECLARE @variablePasado as date\n" + 
					"SET @variablePasado = DATEADD(DAY, 2, getdate())\n" + 
					"DECLARE @variableHoy as date\n" + 
					"SET @variableHoy = getdate()\n" + 
					"DECLARE @TABLA TABLE (Tipo varchar(100), FFin datetime, clave int, nombre varchar(200), Prioridad varchar(10), Cant int, precio decimal(10,2), FPEntrega datetime)\n" + 
					"insert into @TABLA \n" + 
					"SELECT p.Tipo, p.FFin,c.clave, c.nombre, ep.Prioridad, pp.Cant, pp.precio, pp.FPEntrega FROM Pendiente p\n" + 
					"LEFT JOIN PackingList pl on pl.Folio COLLATE DATABASE_DEFAULT = p.Docto COLLATE DATABASE_DEFAULT\n" + 
					"LEFT JOIN PPackingList ppl ON ppl.FK01_PackingList = pl.PK_PackingList\n" + 
					"LEFT JOIN EmbalarPedido ep ON ep.PK_EmbalarPedido = ppl.FK02_EmbalarPedido\n" + 
					"LEFT JOIN Ppedidos pp ON pp.idPPedido = ep.FK01_PPedido\n" + 
					"LEFT JOIN Pedidos pd ON pd.Cpedido = pp.Cpedido\n" + 
					"LEFT JOIN Clientes c ON c.Clave = pd.idCliente\n" + 
					"WHERE p.tipo = 'Por Enviar' AND p.FFin is null AND c.clave is not null AND (P.Partida IS NULL OR P.Partida = 0) \n" + 
					"SELECT 'Todo' AS Filtro, 'Clientes' AS tipo, SUM(Cant) cant, SUM(cant * precio) AS monto, Nombre COLLATE DATABASE_DEFAULT  AS titulo\n" + 
					"FROM @TABLA\n" + 
					"GROUP BY clave, Nombre \n" + 
					"UNION\n" + 
					"SELECT 'Todo' AS Filtro, 'Prioridades' AS tipo, SUM(Cant) cant, SUM(cant * precio) AS monto, Prioridad COLLATE DATABASE_DEFAULT  AS titulo\n" + 
					"FROM @TABLA\n" + 
					"GROUP BY Prioridad \n" + 
					"UNION\n" + 
					"SELECT 'Todo' AS Filtro, 'P1' AS tipo, SUM(Cant) cant, SUM(cant * precio) AS monto, Nombre COLLATE DATABASE_DEFAULT  AS titulo\n" + 
					"FROM @TABLA\n" + 
					"WHERE Prioridad = 'P1' GROUP BY clave, Nombre, Prioridad \n" + 
					"UNION\n" + 
					"SELECT 'Todo' AS Filtro, 'P2' AS tipo, SUM(Cant) cant, SUM(cant * precio) AS monto, Nombre COLLATE DATABASE_DEFAULT  AS titulo\n" + 
					"FROM @TABLA\n" + 
					"WHERE Prioridad = 'P2' GROUP BY clave, Nombre, Prioridad \n" + 
					"UNION\n" + 
					"SELECT 'Todo' AS Filtro, 'P3' AS tipo, SUM(Cant) cant, SUM(cant * precio) AS monto, Nombre COLLATE DATABASE_DEFAULT  AS titulo\n" + 
					"FROM @TABLA\n" + 
					"WHERE Prioridad = 'P3' GROUP BY clave, Nombre, Prioridad\n" + 
					"UNION\n" + 
					"SELECT 'Hoy' AS Filtro, 'Clientes' AS tipo, SUM(Cant) cant, SUM(cant * precio) AS monto, Nombre COLLATE DATABASE_DEFAULT  AS titulo\n" + 
					"FROM @TABLA\n" + 
					"where FPEntrega <= @variableHoy\n" + 
					"GROUP BY clave, Nombre \n" + 
					"UNION\n" + 
					"SELECT 'Hoy' AS Filtro, 'Prioridades' AS tipo, SUM(Cant) cant, SUM(cant * precio) AS monto, Prioridad COLLATE DATABASE_DEFAULT  AS titulo\n" + 
					"FROM @TABLA\n" + 
					"where FPEntrega <= @variableHoy\n" + 
					"GROUP BY Prioridad \n" + 
					"UNION\n" + 
					"SELECT 'Hoy' AS Filtro, 'P1' AS tipo, SUM(Cant) cant, SUM(cant * precio) AS monto, Nombre COLLATE DATABASE_DEFAULT  AS titulo\n" + 
					"FROM @TABLA\n" + 
					"WHERE Prioridad = 'P1' AND FPEntrega <= @variableHoy GROUP BY clave, Nombre, Prioridad \n" + 
					"UNION\n" + 
					"SELECT 'Hoy' AS Filtro, 'P2' AS tipo, SUM(Cant) cant, SUM(cant * precio) AS monto, Nombre COLLATE DATABASE_DEFAULT  AS titulo\n" + 
					"FROM @TABLA\n" + 
					"WHERE Prioridad = 'P2' AND FPEntrega <= @variableHoy GROUP BY clave, Nombre, Prioridad \n" + 
					"UNION\n" + 
					"SELECT 'Hoy' AS Filtro, 'P3' AS tipo, SUM(Cant) cant, SUM(cant * precio) AS monto, Nombre COLLATE DATABASE_DEFAULT  AS titulo\n" + 
					"FROM @TABLA\n" + 
					"WHERE Prioridad = 'P3' AND FPEntrega <= @variableHoy GROUP BY clave, Nombre, Prioridad\n" + 
					"UNION ALL\n" + 
					"SELECT 'Mañana' AS Filtro, 'Clientes' AS tipo, SUM(Cant) cant, SUM(cant * precio) AS monto, Nombre COLLATE DATABASE_DEFAULT  AS titulo\n" + 
					"FROM @TABLA\n" + 
					"WHERE FPEntrega = @variableManana\n" + 
					"GROUP BY clave, Nombre \n" + 
					"UNION\n" + 
					"SELECT 'Mañana' AS Filtro, 'Prioridades' AS tipo, SUM(Cant) cant, SUM(cant * precio) AS monto, Prioridad COLLATE DATABASE_DEFAULT  AS titulo\n" + 
					"FROM @TABLA\n" + 
					"WHERE FPEntrega = @variableManana\n" + 
					"GROUP BY Prioridad \n" + 
					"UNION\n" + 
					"SELECT 'Mañana' AS Filtro, 'P1' AS tipo, SUM(Cant) cant, SUM(cant * precio) AS monto, Nombre COLLATE DATABASE_DEFAULT  AS titulo\n" + 
					"FROM @TABLA\n" + 
					"WHERE Prioridad = 'P1' AND FPEntrega = @variableManana GROUP BY clave, Nombre, Prioridad \n" + 
					"UNION\n" + 
					"SELECT 'Mañana' AS Filtro, 'P2' AS tipo, SUM(Cant) cant, SUM(cant * precio) AS monto, Nombre COLLATE DATABASE_DEFAULT  AS titulo\n" + 
					"FROM @TABLA\n" + 
					"WHERE Prioridad = 'P2' AND FPEntrega = @variableManana GROUP BY clave, Nombre, Prioridad \n" + 
					"UNION\n" + 
					"SELECT 'Mañana' AS Filtro, 'P3' AS tipo, SUM(Cant) cant, SUM(cant * precio) AS monto, Nombre COLLATE DATABASE_DEFAULT  AS titulo\n" + 
					"FROM @TABLA\n" + 
					"WHERE Prioridad = 'P3' AND FPEntrega = @variableManana GROUP BY clave, Nombre, Prioridad\n" + 
					"UNION \n" + 
					"SELECT 'Pasado' AS Filtro, 'Clientes' AS tipo, SUM(Cant) cant, SUM(cant * precio) AS monto, Nombre COLLATE DATABASE_DEFAULT  AS titulo\n" + 
					"FROM @TABLA\n" + 
					"WHERE FPEntrega = @variablePasado\n" + 
					"GROUP BY clave, Nombre \n" + 
					"UNION\n" + 
					"SELECT 'Pasado' AS Filtro, 'Prioridades' AS tipo, SUM(Cant) cant, SUM(cant * precio) AS monto, Prioridad COLLATE DATABASE_DEFAULT  AS titulo\n" + 
					"FROM @TABLA\n" + 
					"WHERE FPEntrega = @variablePasado\n" + 
					"GROUP BY Prioridad \n" + 
					"UNION\n" + 
					"SELECT 'Pasado' AS Filtro, 'P1' AS tipo, SUM(Cant) cant, SUM(cant * precio) AS monto, Nombre COLLATE DATABASE_DEFAULT  AS titulo\n" + 
					"FROM @TABLA\n" + 
					"WHERE Prioridad = 'P1' AND FPEntrega = @variablePasado GROUP BY clave, Nombre, Prioridad \n" + 
					"UNION\n" + 
					"SELECT 'Pasado' AS Filtro, 'P2' AS tipo, SUM(Cant) cant, SUM(cant * precio) AS monto, Nombre COLLATE DATABASE_DEFAULT  AS titulo\n" + 
					"FROM @TABLA\n" + 
					"WHERE Prioridad = 'P2' AND FPEntrega = @variablePasado GROUP BY clave, Nombre, Prioridad \n" + 
					"UNION\n" + 
					"SELECT 'Pasado' AS Filtro, 'P3' AS tipo, SUM(Cant) cant, SUM(cant * precio) AS monto, Nombre COLLATE DATABASE_DEFAULT  AS titulo\n" + 
					"FROM @TABLA\n" + 
					"WHERE Prioridad = 'P3' AND FPEntrega = @variablePasado GROUP BY clave, Nombre, Prioridad\n" + 
					"UNION\n" + 
					"SELECT 'futuro' AS Filtro, 'Clientes' AS tipo, SUM(Cant) cant, SUM(cant * precio) AS monto, Nombre COLLATE DATABASE_DEFAULT  AS titulo\n" + 
					"FROM @TABLA\n" + 
					"WHERE FPEntrega > @variablePasado\n" + 
					"GROUP BY clave, Nombre \n" + 
					"UNION\n" + 
					"SELECT 'futuro' AS Filtro, 'Prioridades' AS tipo, SUM(Cant) cant, SUM(cant * precio) AS monto, Prioridad COLLATE DATABASE_DEFAULT  AS titulo\n" + 
					"FROM @TABLA\n" + 
					"WHERE FPEntrega > @variablePasado\n" + 
					"GROUP BY Prioridad \n" + 
					"UNION\n" + 
					"SELECT 'futuro' AS Filtro, 'P1' AS tipo, SUM(Cant) cant, SUM(cant * precio) AS monto, Nombre COLLATE DATABASE_DEFAULT  AS titulo\n" + 
					"FROM @TABLA\n" + 
					"WHERE Prioridad = 'P1' AND FPEntrega > @variablePasado GROUP BY clave, Nombre, Prioridad \n" + 
					"UNION\n" + 
					"SELECT 'futuro' AS Filtro, 'P2' AS tipo, SUM(Cant) cant, SUM(cant * precio) AS monto, Nombre COLLATE DATABASE_DEFAULT  AS titulo\n" + 
					"FROM @TABLA\n" + 
					"WHERE Prioridad = 'P2' AND FPEntrega > @variablePasado GROUP BY clave, Nombre, Prioridad \n" + 
					"UNION\n" + 
					"SELECT 'futuro' AS Filtro, 'P3' AS tipo, SUM(Cant) cant, SUM(cant * precio) AS monto, Nombre COLLATE DATABASE_DEFAULT  AS titulo\n" + 
					"FROM @TABLA\n" + 
					"WHERE Prioridad = 'P3' AND FPEntrega > @variablePasado GROUP BY clave, Nombre, Prioridad");

			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Map<String, List<TrTotalGraficasAlmacen>>> mapReturn = new HashMap<String, Map<String, List<TrTotalGraficasAlmacen>>>();
			jdbcTemplate.query(sbQuery.toString(), map, new RowMapper() {
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					TrTotalGraficasAlmacen totalGrafica = new TrTotalGraficasAlmacen();
					totalGrafica.setMonto(rs.getDouble("monto"));
					totalGrafica.setPiezas(rs.getInt("cant"));
					totalGrafica.setTitulo(rs.getString("titulo"));

					if (mapReturn.get(rs.getString("filtro")) != null) {
						if(mapReturn.get(rs.getString("filtro")).get(rs.getString("tipo")) != null) {
							mapReturn.get(rs.getString("filtro")).get(rs.getString("tipo")).add(totalGrafica);
						}else {
							mapReturn.get(rs.getString("filtro")).put(rs.getString("tipo"), new ArrayList<TrTotalGraficasAlmacen>());
							mapReturn.get(rs.getString("filtro")).get(rs.getString("tipo")).add(totalGrafica);
						}


					} else {
						mapReturn.put(rs.getString("filtro"), new  HashMap<String, List<TrTotalGraficasAlmacen>>());
						mapReturn.get(rs.getString("filtro")).put(rs.getString("tipo"), new ArrayList<TrTotalGraficasAlmacen>());
						mapReturn.get(rs.getString("filtro")).get(rs.getString("tipo")).add(totalGrafica);
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
	public int getClientePrioridad() throws ProquifaNetException {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			String query = "SELECT  TOP 1 c.clave FROM Pendiente p\n" + 
					"LEFT JOIN PackingList pl on pl.Folio COLLATE DATABASE_DEFAULT = p.Docto COLLATE DATABASE_DEFAULT\n" + 
					"LEFT JOIN PPackingList ppl ON ppl.FK01_PackingList = pl.PK_PackingList\n" + 
					"INNER JOIN FacturaElectronica AS FE ON FE.PK_Factura = PPL.FK03_FacturaElectronica \n" +
					"INNER JOIN Factura_FElectronica AS FFE ON FFE.FK02_FacturaElectronica = FE.PK_Factura \n" +
					"INNER JOIN Facturas AS F ON F.idFactura = FFE.FK01_Factura \n" +
					"LEFT JOIN EmbalarPedido ep ON ep.PK_EmbalarPedido = ppl.FK02_EmbalarPedido\n" + 
					"LEFT JOIN Ppedidos pp ON pp.idPPedido = ep.FK01_PPedido\n" + 
					"LEFT JOIN Pedidos pd ON pd.Cpedido = pp.Cpedido\n" + 
					"LEFT JOIN Clientes c ON c.Clave = pd.idCliente\n" + 
					"WHERE p.tipo = 'Por Enviar' AND (P.Partida IS NULL OR P.Partida = 0) AND p.FFin is null AND c.clave is not null \n" +
					"AND CASE WHEN F.CPago IN ('PAGO CONTRA ENTREGA', 'PREPAGO 100%') THEN 1 ELSE 0 END = CASE WHEN F.Estado = 'Cobrada' THEN 1 ELSE 0 END \n" +
					"GROUP BY c.clave, p.Folio, ppl.Folio, p.Docto, pp.Cant, ppl.Tipo, CAST(pd.ComentariosA AS NVARCHAR(MAX)), pp.FPEntrega, ep.Prioridad ORDER BY pp.FPEntrega, ep.Prioridad";
			log.info("getClientePrioridad\n" + query);
			return (int) super.jdbcTemplate.queryForObject(query, map, int.class);

		}catch (Exception e) {
			log.info(e.getMessage());
			return 0;
		}		
	}

	@Override
	public colectarPartidas obtenerClientePorEnvio(Integer idCliente) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCliente", idCliente);
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("SELECT TOP 1 CLI.Nombre Cliente, CON.Contacto, PE.Ruta, PE.ZonaMensajeria, CON.Puesto \n");
			sbQuery.append("FROM PEndiente PEN \n");
			sbQuery.append("INNER JOIN PackingList PL ON PEn.Docto = PL.Folio COLLATE SQL_Latin1_General_CP1_CI_AS AND PEN.Tipo = 'Por Enviar' \n");
			sbQuery.append("INNER JOIN PPackingList PPL ON PPL.FK01_PackingList = PL.PK_PackingList \n");
			sbQuery.append("INNER JOIN EmbalarPEdido EP ON EP.PK_EmbalarPEdido = PPL.FK02_EmbalarPEdido \n");
			sbQuery.append("INNER JOIN PPEdidos PP ON PP.idPPedido = EP.FK01_PPedido \n");
			sbQuery.append("INNER JOIN Pedidos PE ON PE.CPedido = PP.CPedido \n");
			sbQuery.append("INNER JOIN Clientes CLI ON CLI.Clave = PE.idCliente \n");
			sbQuery.append("INNER JOIN Contactos CON ON CON.idContacto = PE.idContacto \n");
			sbQuery.append("WHERE PE.idCliente = :idCliente AND (PEN.Partida IS NULL OR PEN.Partida = 0) \n");
			sbQuery.append(" \n");

			return super.jdbcTemplate.queryForObject(sbQuery.toString(), map, new BeanPropertyRowMapper<>(colectarPartidas.class));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, List<TrPackingList>> getPackingListClient(int idCliente) throws ProquifaNetException {
		try{
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append(" \n");
			obtenerPackingList(sbQuery, "cliente");
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, List<TrPackingList>> mapReturn = new HashMap<String, List<TrPackingList>>();
			jdbcTemplate.query(sbQuery.toString(), map, new RowMapper() {
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					TrPackingList trPackingList = new TrPackingList();
					trPackingList.setIdClave(rs.getInt("clave"));
					trPackingList.setCliente(rs.getString("Cliente"));
					trPackingList.setIdPendiente(rs.getInt("idPendiente"));
					trPackingList.setPackingList(rs.getString("Docto"));
					trPackingList.setCant(rs.getInt("cant"));
					trPackingList.setFolio(rs.getString("Folio"));
					trPackingList.setTipo(rs.getString("Tipo"));
					trPackingList.setComentario(rs.getString("ComentariosA"));
					trPackingList.setP1(rs.getInt("P1"));
					trPackingList.setP2(rs.getInt("P2"));
					trPackingList.setP3(rs.getInt("P3"));

					trPackingList.setMensajeria(rs.getString("zonamensajeria"));
					trPackingList.setPais(rs.getString("pais"));
					trPackingList.setCalle(rs.getString("calle"));
					trPackingList.setDelegacion(rs.getString("delegacion"));
					trPackingList.setEstado(rs.getString("estado"));
					trPackingList.setCp(rs.getString("cp"));
					trPackingList.setTel(rs.getString("tel"));
					trPackingList.setMail(rs.getString("mail"));
					trPackingList.setPuesto(rs.getString("puesto"));
					trPackingList.setDepartamento(rs.getString("departamento"));
					trPackingList.setContacto(rs.getString("contacto"));
					trPackingList.setRuta(rs.getString("ruta"));
					trPackingList.setCodPais(rs.getString("CodigoPais"));
					trPackingList.setFactura(rs.getString("factura"));
					
					ParametroEnvio envio = new ParametroEnvio();
					trPackingList.setEnvio(envio);
					envio.setWidth(rs.getInt("Ancho"));
					envio.setHeight(rs.getInt("Alto"));
					envio.setPeso(rs.getDouble("Peso"));
					envio.setLength(rs.getInt("Largo"));
					
					envio.setTipo(rs.getString("GuiaCliente"));
					trPackingList.setGuia(rs.getString("Guia"));
					trPackingList.setNumero(rs.getString("Numero"));

					String folioPL = rs.getString("Docto");

					if (mapReturn.get(folioPL) != null) {
						mapReturn.get(folioPL).add(trPackingList);
					} else {
						mapReturn.put(folioPL, new ArrayList<TrPackingList>());
						mapReturn.get(folioPL).add(trPackingList);
					}

					return null;
				}
			});

			return mapReturn;
		}catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}


	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })	
	public Map<String, List<TrEnvio>> obtenerEstadisticaUsuario(Parametro  parametro) throws ProquifaNetException{
		try{
			StringBuilder sbQuery = new StringBuilder(" \n");
			obtenerDatosPrioridades(sbQuery, "clientes");
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("idUsuario", parametro.getIdUsuarioLogueado());
			Map<String, List<TrEnvio>> mapReturn = new HashMap<String, List<TrEnvio>>();
			getJdbcTemplate().query(sbQuery.toString(),parametros,new RowMapper() {
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					TrEnvio usuario = new TrEnvio();
					String tiempo = rs.getString("Tiempo");
					try{	      				    			   
						if(tiempo.equals("Prioridad")){
							usuario.setPrioridad(rs.getString("Prioridad"));
							usuario.setTotalPrioridad(rs.getInt("Total"));
							usuario.setTotalPiezas(rs.getInt("TotalPiezas"));
						}else{
							usuario.setTiempo(rs.getInt("dia"));  
							usuario.setTotalPl(rs.getInt("TotalPL"));
							usuario.setTotalPiezas(rs.getInt("TotalPiezas"));
						}
					}catch (Exception e) {
					}
					if(tiempo.equals("Quincena")){
						if(mapReturn.get("Quincena") != null){
							mapReturn.get("Quincena").add(usuario);
						}
						else {
							List<TrEnvio> list = new ArrayList<TrEnvio>();
							mapReturn.put("Quincena", list );
							mapReturn.get("Quincena").add(usuario);
						}
					} 

					if(tiempo.equals("Mes")){
						if(mapReturn.get("Mes") != null){
							mapReturn.get("Mes").add(usuario);
						} else {
							List<TrEnvio> list = new ArrayList<TrEnvio>();
							mapReturn.put("Mes", list );
							mapReturn.get("Mes").add(usuario);
						}
					}
					if(tiempo.equals("Year")){
						if(mapReturn.get("Year") != null){
							mapReturn.get("Year").add(usuario);
						}else {
							List<TrEnvio> list = new ArrayList<TrEnvio>();
							mapReturn.put("Year", list );
							mapReturn.get("Year").add(usuario);
						}	    			   
					}  
					if(tiempo.equals("AllYears")){
						if(mapReturn.get("AllYears") != null){
							mapReturn.get("AllYears").add(usuario);
						}else {
							List<TrEnvio> list = new ArrayList<TrEnvio>();
							mapReturn.put("AllYears", list );
							mapReturn.get("AllYears").add(usuario);
						}	    			   
					} 
					if(tiempo.equals("Prioridad")){
						if(mapReturn.get("Prioridad") != null){
							mapReturn.get("Prioridad").add(usuario);
						} else {
							List<TrEnvio> list = new ArrayList<TrEnvio>();
							mapReturn.put("Prioridad", list );
							mapReturn.get("Prioridad").add(usuario);
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


	//	@Override
	public boolean registrarEnvio(TrEnvio envio) throws ProquifaNetException{
		try{			
			StringBuilder sbQuery = new StringBuilder(" \n");
			Map<String, Object> parametros = new HashMap<String, Object>();
			sbQuery = new StringBuilder(" \n");
			sbQuery.append("INSERT INTO TrEnvio( FK01_PEndiente, FK02_Usuario, NumGuia, FechaInicio, FK01_PackingList, mensajeria) VALUES (:idPendiente, :idUsuario, :numGuia, GETDATE(), (SELECT PK_PackingList FROM PackingList WHERE Folio = :packingList), :mensajeria) \n");				
			if(envio.getIdPendiente() != null) {
				parametros.put("idPendiente", envio.getIdPendiente());
				if(envio.getIdUsuario() != null) {
					parametros.put("idUsuario", envio.getIdUsuario());
				}
				if(envio.getNumGuia() != null) {
					parametros.put("numGuia", envio.getNumGuia());
					parametros.put("packingList", envio.getPackingList());
					parametros.put("mensajeria", envio.getMensajeria());
					super.jdbcTemplate.update(sbQuery.toString(),parametros);
					return true;
				}
			}
			return false;
		}catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();			
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Boolean actualizarEnvio( TrEnvio envio ) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();	
			map.put("idPendiente", envio.getIdPendiente());
			super.jdbcTemplate.update("UPDATE TrEnvio SET FechaFin = GETDATE() WHERE FK01_PEndiente = :idPendiente", map);

			String query = "SELECT DISTINCT (SELECT Alias FROM Empresa WHERE PK_Empresa = fe.EmpresaEmisor) as FPor, fe.Folio as Factura,  (SELECT usuario FROM Empleados WHERE clave = cl.cobrador) as Cobrador \n" + 
					"FROM TREnvio tre \n" + 
					"LEFT JOIN PackingList pl ON pl.PK_PackingList = tre.FK01_PackingList \n" + 
					"LEFT JOIN PPackingList ppl ON ppl.FK01_PackingList = pl.PK_PackingList \n" +
					"LEFT JOIN EmbalarPedido AS EP ON EP.PK_EmbalarPedido = PPL.FK02_EmbalarPedido \n" +
					"LEFT JOIN PPedidos AS PP ON PP.idPPedido = EP.FK01_PPedido \n" +
					"INNER JOIN Productos AS Prod ON Prod.idProducto = PP.FK02_Producto \n" +
					"LEFT JOIN FacturaElectronica fe ON fe.PK_Factura = ppl.FK03_FacturaElectronica \n" + 
					"LEFT JOIN Factura_FElectronica AS FEE ON FEE.FK02_FacturaElectronica = FE.PK_Factura \n" +					
					"LEFT JOIN Facturas AS F ON F.idFactura = FEE.FK01_Factura \n" +
					"LEFT JOIN Clientes cl ON cl.Clave = fe.Cliente \n" + 
					"WHERE tre.FK01_PEndiente = :idPendiente AND tre.FK01_PackingList IS NOT NULL AND F.Estado = 'Por Cobrar'";
			map = new HashMap<String, Object>();
			map.put("idPendiente", envio.getIdPendiente());
			jdbcTemplate.query(query, map, new RowMapper() {
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					try {
						String factura = rs.getString("Factura");
						String fpor = rs.getString("FPor");
						String cobrador =  rs.getString("Cobrador");
						pendienteDAO.guardarPendiente_angular(new Pendiente(fpor, "Programar Cobro", factura, new Date(), cobrador, null));
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;
				}
			});
			
			map = new HashMap<String, Object>();
			map.put("idPendiente", envio.getIdPendiente());
			String queryUpdate = "update ppedidos set estado = 'Enviado' where idPPedido in(  \n" + 
					"select ep.FK01_PPedido from pendiente p  \n" + 
					"left join PPackingList pp on pp.FK01_PackingList = (select PK_PackingList from PackingList pl where pl.folio collate SQL_Latin1_General_CP1_CI_AS = p.docto collate SQL_Latin1_General_CP1_CI_AS) \n" + 
					"left join embalarPedido ep on ep.PK_EmbalarPedido = pp.FK02_EmbalarPedido  \n" + 
					"where p.folio = :idPendiente)";
			super.jdbcTemplate.update(queryUpdate, map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}


	@Override
	public Boolean actualizarEnvioGDL( TrEnvio envio ) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();	
			map.put("idPendiente", envio.getIdPendiente());
			super.jdbcTemplate.update("UPDATE TrEnvio SET FechaFin = GETDATE() WHERE FK01_PEndiente = :idPendiente", map);

			map = new HashMap<String, Object>();
			map.put("idPendiente", envio.getIdPendiente());
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("UPDATE PPedidos SET Estado = 'Enviado' WHERE idPPEdido IN ( \n");
			sbQuery.append("SELECT FK01_PPEdido FROM EmbalarPEdido WHERE NoGuia IN (SELECT Docto COLLATE Modern_Spanish_CI_AS FROM Pendiente WHERE Folio = :idPendiente )) \n");
			sbQuery.append(" \n");
			super.jdbcTemplate.update(sbQuery.toString(), map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}


	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })	
	public Map<String, List<TrEnvio>> obtenerTiempoTrabajoEnvio() throws ProquifaNetException{
		try{
			StringBuilder sbQuery = new StringBuilder(" \n");
			obtenerTiempo(sbQuery, "clientes");

			Map<String, Object> parametros = new HashMap<String, Object>();
			Map<String, List<TrEnvio>> mapReturn = new HashMap<String, List<TrEnvio>>();
			getJdbcTemplate().query(sbQuery.toString(),parametros,new RowMapper() {
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					TrEnvio usuario = new TrEnvio();
					String tiempo = rs.getString("Etiqueta");
					try{	      				    			   
						if(tiempo.equals("Tiempo")){
							usuario.setTiempo(rs.getInt("Tiempo"));
						}else{
							usuario.setTotalPiezas(rs.getInt("numPiezas"));
							usuario.setPrioridad(rs.getString("Prioridad"));
						}
					}catch (Exception e) {
					}
					if(tiempo.equals("Tiempo")){
						if(mapReturn.get("Tiempo") != null){
							mapReturn.get("Tiempo").add(usuario);
						}
						else {
							List<TrEnvio> list = new ArrayList<TrEnvio>();
							mapReturn.put("Tiempo", list );
							mapReturn.get("Tiempo").add(usuario);
						}
					} 

					if(tiempo.equals("Piezas")){
						if(mapReturn.get("Piezas") != null){
							mapReturn.get("Piezas").add(usuario);
						} else {
							List<TrEnvio> list = new ArrayList<TrEnvio>();
							mapReturn.put("Piezas", list );
							mapReturn.get("Piezas").add(usuario);
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
	public List<TrAlmacen> obtenerClientes() throws ProquifaNetException{
		try {
			StringBuilder sbQuery = new StringBuilder("SELECT PL.Clave, PL.Nombre, SUM(PL.Cant) Cant, COUNT(PL.Docto) numPL, \n"); 
			sbQuery.append("COUNT(CASE WHEN PL.Prioridad= 'P1' THEN 1 END) AS P1, \n");
			sbQuery.append("COUNT(CASE WHEN PL.Prioridad= 'P2' THEN 1 END) AS P2, \n");
			sbQuery.append("COUNT(CASE WHEN PL.Prioridad= 'P3' THEN 1 END) AS P3 \n");
			sbQuery.append("FROM (  \n");
			sbQuery.append("SELECT C.Clave, C.Nombre, SUM(PP.Cant) Cant, P.Docto, EP.Prioridad, \n");
			sbQuery.append("CASE WHEN PEC.PK_Folio IS NOT NULL THEN 'Guia' WHEN PEXC.PK_Folio IS NOT NULL THEN 'Cuenta' ELSE 'Normal' END Tipo, \n");
			sbQuery.append("CASE WHEN PEC.PK_Folio IS NOT NULL AND PEN.Folio IS NOT NULL THEN '1'  \n");
			sbQuery.append("WHEN PEXC.PK_Folio IS NOT NULL THEN '1' ELSE '0' END PendienteCerrado \n");
			sbQuery.append("FROM Pendiente p \n");
			sbQuery.append("INNER JOIN PackingList pl on pl.Folio COLLATE DATABASE_DEFAULT = p.Docto COLLATE DATABASE_DEFAULT \n");
			sbQuery.append("INNER JOIN PPackingList ppl ON ppl.FK01_PackingList = pl.PK_PackingList \n");
			sbQuery.append("INNER JOIN EmbalarPedido ep ON ep.PK_EmbalarPedido = ppl.FK02_EmbalarPedido \n");
			sbQuery.append("INNER JOIN Ppedidos pp ON pp.idPPedido = ep.FK01_PPedido \n");
			sbQuery.append("INNER JOIN Pedidos pd ON pd.Cpedido = pp.Cpedido \n");
			sbQuery.append("INNER JOIN Clientes c ON c.Clave = pd.idCliente \n");
			sbQuery.append("LEFT JOIN Pendiente PEN ON PEN.Docto = PL.Folio AND PEN.Tipo = 'Cargar Guia Envio' AND PEN.FFIN IS NOT NULL \n");
			sbQuery.append("LEFT JOIN Pedido_EnvioXCliente PEC ON PEC.FK01_PackingList = PL.PK_PackingList \n");
			sbQuery.append("LEFT JOIN Pedido_EnvioXCliente PEXC ON PEXC.FK02_Pedido = PD.idPedido \n");
			sbQuery.append("WHERE p.tipo = 'Por Enviar' AND p.FFin is null AND c.clave is not null \n");
			sbQuery.append("AND pd.GuiaXCliente = 1 \n");
			sbQuery.append("GROUP BY C.Clave, C.Nombre,P.Docto, EP.Prioridad, CASE WHEN PEC.PK_Folio IS NOT NULL THEN 'Guia' WHEN PEXC.PK_Folio IS NOT NULL THEN 'Cuenta' ELSE 'Normal' END, \n");
			sbQuery.append("CASE WHEN PEC.PK_Folio IS NOT NULL AND PEN.Folio IS NOT NULL THEN '1'  WHEN PEXC.PK_Folio IS NOT NULL THEN '1' ELSE '0' END  \n");
			sbQuery.append(") PL \n");
			sbQuery.append("WHERE PendienteCerrado = '1' \n");
			sbQuery.append("GROUP BY PL.Clave, PL.Nombre ORDER BY P1 DESC,P2 DESC, P3 DESC \n");
			
			log.info("obtenerClientes()\n" + sbQuery.toString());
			
			Map<String, Object> map = new HashMap<String, Object>();
			List<TrAlmacen> mapReturn = new ArrayList<TrAlmacen>();
			jdbcTemplate.query(sbQuery.toString(), map, new RowMapper() {
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					TrAlmacen trAlmacen = new TrAlmacen();
					trAlmacen.setIdCliente(rs.getInt("clave"));
					trAlmacen.setNombreCliente(rs.getString("Nombre"));
					trAlmacen.setCant(rs.getInt("cant"));
					trAlmacen.setNumPL(rs.getInt("numPL"));
					trAlmacen.setP1(rs.getInt("P1"));
					trAlmacen.setP2(rs.getInt("P2"));
					trAlmacen.setP3(rs.getInt("P3"));

					mapReturn.add(trAlmacen);
					return mapReturn;
				}
			});
			return mapReturn;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	private void obtenerPackingList(StringBuilder sbQuery, String tipo) throws ProquifaNetException{
		try {
			sbQuery.append("DECLARE @TABLA TABLE (idCliente INT, Zona VARCHAR(MAX), Pais VARCHAR(MAX), Calle VARCHAR(MAX), Delegacion VARCHAR(MAX), \n");
			sbQuery.append("Estado VARCHAR(MAX), CP INT, Contacto VARCHAR(MAX), Ruta VARCHAR(MAX), CodigoPais VARCHAR(MAX)) \n");
			sbQuery.append("INSERT INTO @TABLA \n");
			
			if(tipo.equals("Por cliente")){
				sbQuery.append("SELECT TOP 1 Guia.idCliente, Guia.zonamensajeria, Guia.pais, Guia.calle, Guia.delegacion, Guia.estado, COALESCE(TRY_CAST (Guia.cp AS INT), 0), Guia.contacto, Guia.ruta, Guia.codigo FROM ( \n");
				sbQuery.append("SELECT pd.idCliente, pd.zonamensajeria, pd.pais, pd.calle, pd.delegacion, pd.estado, pd.cp, pd.contacto, pd.ruta, codp.codigo , \n");
				sbQuery.append("CASE WHEN PEC.PK_Folio IS NOT NULL AND PEN.Folio IS NOT NULL THEN '1'   \n");
				sbQuery.append("WHEN PEXC.PK_Folio IS NOT NULL THEN '1' ELSE '0' END PendienteCerrado \n");
			} else {
				sbQuery.append("SELECT TOP 1 pd.idCliente, pd.zonamensajeria, pd.pais, pd.calle, pd.delegacion, pd.estado, COALESCE(TRY_CAST (pd.cp AS INT), 0), pd.contacto, pd.ruta, codp.codigo  \n");
			}
			
			sbQuery.append("FROM Pendiente p \n");
			sbQuery.append("INNER JOIN PackingList pl on pl.Folio COLLATE DATABASE_DEFAULT = p.Docto COLLATE DATABASE_DEFAULT \n");
			sbQuery.append("INNER JOIN PPackingList ppl ON ppl.FK01_PackingList = pl.PK_PackingList \n");
			sbQuery.append("INNER JOIN FacturaElectronica AS FE ON FE.PK_Factura = PPL.FK03_FacturaElectronica \n"); 
			sbQuery.append("INNER JOIN Factura_FElectronica AS FFE ON FFE.FK02_FacturaElectronica = FE.PK_Factura \n"); 
			sbQuery.append("INNER JOIN Facturas AS F ON F.idFactura = FFE.FK01_Factura \n");
			sbQuery.append("INNER JOIN EmbalarPedido ep ON ep.PK_EmbalarPedido = ppl.FK02_EmbalarPedido \n");
			sbQuery.append("INNER JOIN Ppedidos pp ON pp.idPPedido = ep.FK01_PPedido \n");
			sbQuery.append("INNER JOIN Pedidos pd ON pd.Cpedido = pp.Cpedido \n");
			sbQuery.append("LEFT JOIN CodigoPais codp ON codp.pais = pd.pais COLLATE Modern_Spanish_CI_AS \n");
			
			sbQuery.append("LEFT JOIN Pendiente PEN ON PEN.Docto = PL.Folio AND PEN.Tipo = 'Cargar Guia Envio' AND PEN.FFIN IS NOT NULL \n"); 
			sbQuery.append("LEFT JOIN Pedido_EnvioXCliente PEC ON PEC.FK01_PackingList = PL.PK_PackingList  \n");
			sbQuery.append("LEFT JOIN Pedido_EnvioXCliente PEXC ON PEXC.FK02_Pedido = PD.idPedido  \n");
			
			sbQuery.append("WHERE p.tipo = 'Por Enviar' AND p.FFin is null \n");
			if(tipo.equals("Por cliente")) {
				sbQuery.append("AND pd.GuiaXCliente = 1 AND pd.idCliente = :idCliente\n");
			} else 
			if (tipo.equals("cliente")) {
				sbQuery.append("aND pd.idCliente is not null \n");
				sbQuery.append("AND (pd.GuiaXCliente = 0 OR pd.GuiaXCliente IS NULL) AND (P.Partida IS NULL OR P.Partida = 0) \n");
				sbQuery.append("AND CASE WHEN F.CPago IN ('PAGO CONTRA ENTREGA', 'PREPAGO 100%') THEN 1 ELSE 0 END = CASE WHEN F.Estado = 'Cobrada' THEN 1 ELSE 0 END \n"); 
			}
			sbQuery.append("GROUP BY pd.idCliente, pd.zonamensajeria, pd.pais, pd.calle, pd.delegacion, pd.estado, pd.cp, pd.contacto, pd.ruta, pp.FPEntrega, ep.Prioridad, codp.codigo  \n");
			if(tipo.equals("Por cliente")) {
				sbQuery.append(",CASE WHEN PEC.PK_Folio IS NOT NULL AND PEN.Folio IS NOT NULL THEN '1'  \n");
				sbQuery.append("WHEN PEXC.PK_Folio IS NOT NULL THEN '1' ELSE '0' END ) \n");
				sbQuery.append("Guia WHERE PendienteCerrado > 0  \n");
				
			}else {
				sbQuery.append("ORDER BY pp.FPEntrega, ep.Prioridad \n");
			}
			if(tipo.equals("Por cliente"))
				sbQuery.append("SELECT * FROM ( \n");
			sbQuery.append("SELECT c.clave, C.Nombre Cliente, p.Folio as idPendiente, p.Docto, sum(pp.Cant) AS cant, ppl.Folio, ppl.Tipo, \n");
			sbQuery.append("CAST(pd.ComentariosA AS NVARCHAR(MAX)) AS ComentariosA, \n");
			sbQuery.append("COUNT(CASE WHEN ep.Prioridad= 'P1' THEN 1 END) AS P1, \n");
			sbQuery.append("COUNT(CASE WHEN ep.Prioridad= 'P2' THEN 1 END) AS P2, \n");
			sbQuery.append("COUNT(CASE WHEN ep.Prioridad= 'P3' THEN 1 END) AS P3, \n");
			sbQuery.append("COALESCE(PEC.Mensajeria, PEXC.Mensajeria, pd.zonamensajeria COLLATE Modern_Spanish_CI_AS) zonamensajeria, pd.pais, pd.calle, pd.delegacion, pd.estado, pd.cp, pd.Tel, pd.Mail, pd.puesto, pd.departamento, \n");
			sbQuery.append("pd.contacto, pd.ruta, fe.Folio as factura, pd.idPedido, PM.CodigoPais, PEC.PEso, PEC.Alto, PEC.Largo, PEC.Ancho,  \n");
			
			sbQuery.append("CASE WHEN PEXC.PK_Folio IS NOT NULL THEN 'Cuenta' WHEN PEC.PK_Folio IS NOT NULL THEN 'Guia' ELSE 'Normal' END GuiaCliente,  \n");
			sbQuery.append("CASE WHEN PEC.PK_Folio IS NOT NULL AND PEN.Folio IS NOT NULL THEN '1'   \n");
			sbQuery.append("WHEN PEXC.PK_Folio IS NOT NULL THEN '1' ELSE '0' END PendienteCerrado, \n");
			sbQuery.append("COALESCE(PEC.Guia, PEXC.Guia) Guia, COALESCE(PEC.Numero, PEXC.Numero) Numero \n");
			
			sbQuery.append("FROM Pendiente p \n");
			sbQuery.append("INNER JOIN PackingList pl on pl.Folio COLLATE DATABASE_DEFAULT = p.Docto COLLATE DATABASE_DEFAULT \n");
			sbQuery.append("INNER JOIN PPackingList ppl ON ppl.FK01_PackingList = pl.PK_PackingList \n");
			sbQuery.append("LEFT JOIN FacturaElectronica fe ON fe.PK_Factura = ppl.FK03_FacturaElectronica \n");
			sbQuery.append("INNER JOIN EmbalarPedido ep ON ep.PK_EmbalarPedido = ppl.FK02_EmbalarPedido \n");
			sbQuery.append("INNER JOIN Ppedidos pp ON pp.idPPedido = ep.FK01_PPedido \n");
			sbQuery.append("INNER JOIN Pedidos PD ON PD.Cpedido = pp.Cpedido \n");
			sbQuery.append("INNER JOIN @TABLA PM ON PM.idCliente = PD.idCliente AND PD.ZonaMensajeria = PM.Zona COLLATE SQL_Latin1_General_CP1_CI_AS \n");
			sbQuery.append("AND PD.Pais = PM.Pais COLLATE SQL_Latin1_General_CP1_CI_AS AND PD.Calle = PM.Calle COLLATE SQL_Latin1_General_CP1_CI_AS  \n");
			sbQuery.append("AND PD.Delegacion = PM.Delegacion COLLATE SQL_Latin1_General_CP1_CI_AS AND PD.Estado = PM.Estado COLLATE SQL_Latin1_General_CP1_CI_AS \n");
			sbQuery.append("AND COALESCE(TRY_CAST(PD.CP AS INT), 0) = PM.CP AND PM.Contacto = PD.Contacto COLLATE SQL_Latin1_General_CP1_CI_AS AND PD.Ruta = PM.Ruta COLLATE SQL_Latin1_General_CP1_CI_AS \n");
			sbQuery.append("LEFT JOIN Clientes c ON c.Clave = pd.idCliente \n");
			
			sbQuery.append("LEFT JOIN Pendiente PEN ON PEN.Docto = PL.Folio AND PEN.Tipo = 'Cargar Guia Envio' AND PEN.FFIN IS NOT NULL \n");
			sbQuery.append("LEFT JOIN Pedido_EnvioXCliente PEC ON PEC.FK01_PackingList = PL.PK_PackingList \n");
			sbQuery.append("LEFT JOIN Pedido_EnvioXCliente PEXC ON PEXC.FK02_Pedido = PD.idPedido \n");
			
			sbQuery.append("WHERE p.tipo = 'Por Enviar' AND p.FFin is null \n");
			if(tipo.equals("Por cliente")) {
				sbQuery.append("AND pd.GuiaXCliente = 1 \n");
			}if (tipo.equals("cliente")) {
				sbQuery.append("AND (pd.GuiaXCliente = 0 OR pd.GuiaXCliente IS NULL) AND (P.Partida IS NULL OR P.Partida = 0) \n");
			}
			sbQuery.append("GROUP BY C.Nombre, c.clave, p.Folio, ppl.Folio, p.Docto, ppl.Tipo, CAST(pd.ComentariosA AS NVARCHAR(MAX)), \n");
			sbQuery.append("COALESCE(PEC.Mensajeria, PEXC.Mensajeria, pd.zonamensajeria COLLATE Modern_Spanish_CI_AS), pd.pais, pd.calle, pd.delegacion, pd.estado, pd.cp, pd.Tel, pd.Mail, pd.puesto, \n");
			sbQuery.append("pd.departamento, pd.contacto, pd.ruta, fe.Folio, pd.idPedido, PM.CodigoPais, PEC.PEso, PEC.Alto, PEC.Largo, PEC.Ancho, \n");	
			sbQuery.append("CASE WHEN PEXC.PK_Folio IS NOT NULL THEN 'Cuenta' WHEN PEC.PK_Folio IS NOT NULL THEN 'Guia' ELSE 'Normal' END, \n");
			sbQuery.append("CASE WHEN PEC.PK_Folio IS NOT NULL AND PEN.Folio IS NOT NULL THEN '1'   \n");
			sbQuery.append("WHEN PEXC.PK_Folio IS NOT NULL THEN '1' ELSE '0' END , COALESCE(PEC.Guia, PEXC.Guia), COALESCE(PEC.Numero, PEXC.Numero) \n");
			if(tipo.equals("Por cliente"))
				sbQuery.append(") Guia WHERE PendienteCerrado > 0 \n");
			log.info("obtenerPackingList\n" + sbQuery.toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, List<TrPackingList>> packingListPorCliente(int idCliente) throws ProquifaNetException {
		try{
			StringBuilder sbQuery = new StringBuilder(" \n");
			obtenerPackingList(sbQuery, "Por cliente");

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCliente", idCliente);
			Map<String, List<TrPackingList>> mapReturn = new HashMap<String, List<TrPackingList>>();
			jdbcTemplate.query(sbQuery.toString(), map, new RowMapper() {
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					TrPackingList trPackingList = new TrPackingList();
					trPackingList.setIdClave(rs.getInt("clave"));
					trPackingList.setCliente(rs.getString("Cliente"));
					trPackingList.setIdPendiente(rs.getInt("idPendiente"));
					trPackingList.setPackingList(rs.getString("Docto"));
					trPackingList.setCant(rs.getInt("cant"));
					trPackingList.setFolio(rs.getString("Folio"));
					trPackingList.setTipo(rs.getString("Tipo"));
					trPackingList.setComentario(rs.getString("ComentariosA"));
					trPackingList.setP1(rs.getInt("P1"));
					trPackingList.setP2(rs.getInt("P2"));
					trPackingList.setP3(rs.getInt("P3"));

					trPackingList.setMensajeria(rs.getString("zonamensajeria"));
					trPackingList.setPais(rs.getString("pais"));
					trPackingList.setCalle(rs.getString("calle"));
					trPackingList.setDelegacion(rs.getString("delegacion"));
					trPackingList.setEstado(rs.getString("estado"));
					trPackingList.setCp(rs.getString("cp"));
					trPackingList.setTel(rs.getString("tel"));
					trPackingList.setMail(rs.getString("mail"));
					trPackingList.setPuesto(rs.getString("puesto"));
					trPackingList.setDepartamento(rs.getString("departamento"));
					trPackingList.setContacto(rs.getString("contacto"));
					trPackingList.setRuta(rs.getString("ruta"));
					trPackingList.setCodPais(rs.getString("CodigoPais"));
					trPackingList.setFactura(rs.getString("factura"));
					ParametroEnvio envio = new ParametroEnvio();
					trPackingList.setEnvio(envio);
					envio.setWidth(rs.getInt("Ancho"));
					envio.setHeight(rs.getInt("Alto"));
					envio.setPeso(rs.getDouble("Peso"));
					envio.setLength(rs.getInt("Largo"));
					
					envio.setTipo(rs.getString("GuiaCliente"));
					trPackingList.setGuia(rs.getString("Guia"));
					trPackingList.setNumero(rs.getString("Numero"));

					String folioPL = rs.getString("Docto");

					if (mapReturn.get(folioPL) != null) {
						mapReturn.get(folioPL).add(trPackingList);
					} else {
						mapReturn.put(folioPL, new ArrayList<TrPackingList>());
						mapReturn.get(folioPL).add(trPackingList);
					}

					return null;
				}
			});

			return mapReturn;
		}catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, Integer> obtenerTotales() throws ProquifaNetException{
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("SELECT 'Objetivo' tipo, COALESCE(MAX(cant),0)cant \n");
			sbQuery.append("FROM ( \n");
			sbQuery.append("SELECT COUNT(Folio) cant, FFin FROM ( \n");
			sbQuery.append("SELECT PL.Folio, CAST(PEN.FFin as DATE) FFin \n");
			sbQuery.append("FROM Pendiente PEN \n");
			sbQuery.append("INNER JOIN PackingList PL ON PL.Folio = PEN.Docto COLLATE Modern_Spanish_CI_AS \n");
			sbQuery.append("INNER JOIN PPackingList PPL ON PPL.FK01_PackingList = PL.PK_PackingList \n");
			sbQuery.append("INNER JOIN EmbalarPedido EP ON EP.PK_EmbalarPedido = PPL.FK02_EmbalarPedido \n");
			sbQuery.append("INNER JOIN Ppedidos PP ON PP.idPPedido = EP.FK01_PPedido \n");
			sbQuery.append("INNER JOIN Pedidos PD ON PD.Cpedido = PP.Cpedido AND PD.GuiaXCliente = 1 \n");
			sbQuery.append("WHERE PEN.Tipo = 'Por Enviar' AND PEN.FFin IS NOT NULL \n");
			sbQuery.append("GROUP BY PL.Folio, CAST(PEN.FFin as DATE)) PL \n");
			sbQuery.append("GROUP BY FFin ) Objetivo \n");
			sbQuery.append("UNION ALL \n");
			sbQuery.append("SELECT  'Hoy' tipo, \n");
			sbQuery.append("CASE WHEN COUNT(Folio)  IS NULL THEN 0 ELSE COUNT(Folio) END \n");
			sbQuery.append("AS cant \n");
			sbQuery.append("FROM ( \n");
			sbQuery.append("SELECT PL.Folio, CAST(PEN.FFin as DATE) FFin \n");
			sbQuery.append("FROM Pendiente PEN \n");
			sbQuery.append("INNER JOIN PackingList PL ON PL.Folio = PEN.Docto COLLATE Modern_Spanish_CI_AS \n");
			sbQuery.append("INNER JOIN PPackingList PPL ON PPL.FK01_PackingList = PL.PK_PackingList \n");
			sbQuery.append("INNER JOIN EmbalarPedido EP ON EP.PK_EmbalarPedido = PPL.FK02_EmbalarPedido \n");
			sbQuery.append("INNER JOIN Ppedidos PP ON PP.idPPedido = EP.FK01_PPedido \n");
			sbQuery.append("INNER JOIN Pedidos PD ON PD.Cpedido = PP.Cpedido AND PD.GuiaXCliente = 1 \n");
			sbQuery.append("WHERE PEN.Tipo = 'Por Enviar' AND CAST(PEN.FFin as DATE) = CAST(GETDATE() as DATE)  \n");
			sbQuery.append("GROUP BY PL.Folio, CAST(PEN.FFin as DATE)) PL \n");
			sbQuery.append("UNION ALL \n");
			sbQuery.append("SELECT 'Deseadas' tipo, \n");
			sbQuery.append("CASE WHEN COUNT(Folio)  IS NULL THEN 0 ELSE COUNT(Folio) END \n");
			sbQuery.append("AS cant \n");
			sbQuery.append("FROM( \n");
			sbQuery.append("SELECT PL.Folio, CAST(PEN.FFin as Date) FFin  \n");
			sbQuery.append("FROM Pendiente PEN \n");
			sbQuery.append("INNER JOIN PackingList PL ON PL.Folio = PEN.Docto COLLATE Modern_Spanish_CI_AS  \n");
			sbQuery.append("INNER JOIN PPackingList PPL ON PPL.FK01_PackingList = PL.PK_PackingList \n");
			sbQuery.append("INNER JOIN EmbalarPedido EP ON EP.PK_EmbalarPedido = PPL.FK02_EmbalarPedido \n");
			sbQuery.append("INNER JOIN Ppedidos PP ON PP.idPPedido = EP.FK01_PPedido \n");
			sbQuery.append("INNER JOIN Pedidos PD ON PD.Cpedido = PP.Cpedido AND PD.GuiaXCliente = 1 \n");
			sbQuery.append("WHERE PEN.Tipo = 'Por Enviar' AND (PEN.FFin IS NULL OR CAST(PEN.FFin as DATE) = CAST(GETDATE() AS DATE)) \n");
			sbQuery.append("GROUP BY PL.Folio, CAST(PEN.FFin as DATE)) PL \n");
			sbQuery.append("UNION ALL \n");
			sbQuery.append("SELECT TOP 1 'Minimo' tipo, COUNT(Folio) /2 cant FROM ( \n");
			sbQuery.append("SELECT PL.Folio, CAST(PEN.FFin as DATE) FFin \n");
			sbQuery.append("FROM Pendiente PEN \n");
			sbQuery.append("INNER JOIN PackingList PL ON PL.Folio = PEN.Docto COLLATE Modern_Spanish_CI_AS \n");
			sbQuery.append("INNER JOIN PPackingList PPL ON PPL.FK01_PackingList = PL.PK_PackingList \n");
			sbQuery.append("INNER JOIN EmbalarPedido EP ON EP.PK_EmbalarPedido = PPL.FK02_EmbalarPedido \n");
			sbQuery.append("INNER JOIN Ppedidos PP ON PP.idPPedido = EP.FK01_PPedido \n");
			sbQuery.append("INNER JOIN Pedidos PD ON PD.Cpedido = PP.Cpedido AND PD.GuiaXCliente = 1 \n");
			sbQuery.append("WHERE PEN.Tipo = 'Por Enviar'  AND PEN.FFin IS NOT NULL \n");
			sbQuery.append("GROUP BY PL.Folio, CAST(PEN.FFin as DATE))PL \n");
			sbQuery.append("ORDER BY cant desc \n");
			sbQuery.append(" \n");
			
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Integer> mapReturn = new HashMap<String, Integer>();
			
			jdbcTemplate.query(sbQuery.toString(), map, new RowMapper() {
				Integer totHoy = 0;
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					if(rs.getString("tipo").equals("Hoy")) {
						totHoy = rs.getInt("cant");					
					}
					if(rs.getString("tipo").equals("Minimo")) {
						if(totHoy < rs.getInt("cant")) {
							mapReturn.put(rs.getString("tipo"),  totHoy);
						} else {
							mapReturn.put(rs.getString("tipo"),  rs.getInt("cant"));
						}
					} else {
					mapReturn.put(rs.getString("tipo"),  rs.getInt("cant"));
					}
					return null;
				}
			});
			
			return mapReturn;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ProquifaNetException();
			
		}
	}
	private void obtenerTiempo(StringBuilder sbQuery, String tipo) throws ProquifaNetException{
		try {
			sbQuery.append("SELECT COALESCE(SUM(DATEDIFF(SECOND, TRE.FechaInicio, TRE.FEchaFin))/COUNT(PL.PK_PackingList)/60,0) AS Tiempo, \n");
			sbQuery.append("null 'numPiezas',    null 'Prioridad', 'Tiempo'  Etiqueta \n");
			sbQuery.append("FROM Pendiente PEN \n");
			sbQuery.append("INNER JOIN PackingList PL ON PL.Folio = PEN.Docto COLLATE Modern_Spanish_CI_AS   AND PEN.Tipo = 'Por Envio' \n");
			sbQuery.append("INNER JOIN TrEnvio TRE ON TRE.FK01_Pendiente = PEN.Folio \n");
			sbQuery.append("UNION ALL \n");
			sbQuery.append("SELECT null ,COUNT(PL.Folio) as numPiezas, PL.Prioridad, 'Piezas' \n");
			sbQuery.append("FROM ( \n");
			sbQuery.append("SELECT PL.Folio, EP.Prioridad \n");
			sbQuery.append("FROM Pendiente PEN \n");
			sbQuery.append("INNER JOIN PackingList PL ON PL.Folio = PEN.Docto COLLATE Modern_Spanish_CI_AS \n");
			sbQuery.append("INNER JOIN PPackingList PPL ON PPL.FK01_PackingList = PL.PK_PackingList \n");
			sbQuery.append("INNER JOIN EmbalarPedido EP ON  EP.Estado = 'Embalado' AND EP.PK_EmbalarPedido = PPL.FK02_EmbalarPEdido \n");
			sbQuery.append("INNER JOIN PPedidos PP ON PP.idPPedido = EP.FK01_PPedido \n");
			sbQuery.append("INNER JOIN Pedidos P ON P.CPEDIDO = PP.CPedido \n");
			sbQuery.append("WHERE PEN.Tipo = 'Por Enviar' AND PEN.FFin IS NULL \n");
			if(tipo.equals("Por cliente")) {
				sbQuery.append("AND P.GuiaXCliente = 1 \n");
			} 
			else if (tipo.equals("clientes")) {
				sbQuery.append("AND (P.GuiaXCliente = 0 OR P.GuiaXCliente IS NULL) \n");
			}
			sbQuery.append("GROUP BY PL.Folio, EP.Prioridad ) PL \n");
			sbQuery.append("GROUP BY PL.Prioridad  \n");
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, List<TrEnvio>> obtenerTiempoXCliente() throws ProquifaNetException{
		try {
			StringBuilder sbQuery = new StringBuilder();
			obtenerTiempo(sbQuery, "Por cliente");
			Map<String, Object> parametros = new HashMap<String, Object>();
			Map<String, List<TrEnvio>> mapReturn = new HashMap<String, List<TrEnvio>>();
			jdbcTemplate.query(sbQuery.toString(), parametros, new RowMapper() {
				@Override
				public Object mapRow(ResultSet rs, int arg) throws SQLException{
					TrEnvio usuario = new TrEnvio();
					String tiempo = rs.getString("Etiqueta");
					try{	      				    			   
						if(tiempo.equals("Tiempo")){
							usuario.setTiempo(rs.getInt("Tiempo"));
						}else{
							usuario.setTotalPiezas(rs.getInt("numPiezas"));
							usuario.setPrioridad(rs.getString("Prioridad"));
						}
					}catch (Exception e) {
					}
					if(tiempo.equals("Tiempo")){
						if(mapReturn.get("Tiempo") != null){
							mapReturn.get("Tiempo").add(usuario);
						}
						else {
							List<TrEnvio> list = new ArrayList<TrEnvio>();
							mapReturn.put("Tiempo", list );
							mapReturn.get("Tiempo").add(usuario);
						}
					} 

					if(tiempo.equals("Piezas")){
						if(mapReturn.get("Piezas") != null){
							mapReturn.get("Piezas").add(usuario);
						} else {
							List<TrEnvio> list = new ArrayList<TrEnvio>();
							mapReturn.put("Piezas", list );
							mapReturn.get("Piezas").add(usuario);
						}
					}
					return null;
				}
			});
			return mapReturn;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	private void obtenerDatosPrioridades(StringBuilder sbQuery, String tipo) {
		try {
			sbQuery.append("SELECT 'Quincena' Tiempo, DAY(P.FFin) dia,  COUNT(PKL.folio) TotalPL, SUM(PP.cant) TotalPiezas, NULL Prioridad, NULL Total\n"); 
			sbQuery.append("FROM PackingList PKL \n");
			sbQuery.append("INNER JOIN EmbalarPedido EP ON PKL.Folio =  EP.FolioTemporal COLLATE Modern_Spanish_CI_AS\n"); 
			sbQuery.append("INNER JOIN PPedidos PP ON EP.FK01_PPEdido = PP.idPPEdido \n");
			sbQuery.append("INNER JOIN Pedidos PD ON PD.Cpedido = PP.Cpedido \n");
			sbQuery.append("INNER JOIN Pendiente P  ON  PKL.folio = P.Docto collate SQL_Latin1_General_CP1_CI_AS\n");
			sbQuery.append("INNER JOIN TREnvio TRE ON  TRE.FK01_Pendiente =  P.Folio\n");
			sbQuery.append("WHERE Ep.Estado = 'Embalado' AND YEAR(P.FFin) = YEAR(GETDATE())  AND  MONTH(P.FFin) = MONTH(GETDATE()) AND  TRE.FK02_Usuario = :idUsuario AND  P.FFin IS NOT NULL AND P.Tipo = 'Por Enviar'\n");
			sbQuery.append("AND CASE WHEN DAY(P.FFin) <= 15 THEN 1 ELSE 2  END = CASE WHEN DAY(GETDATE()) <= 15 THEN 1 ELSE 2 END  \n");
			if(tipo.equals("Por cliente")) {
				sbQuery.append("AND PD.GuiaXCliente = 1 \n");
			} else if(tipo.equals("clientes")) {
				sbQuery.append("AND (PD.GuiaXCliente = 0 OR PD.GuiaXCliente IS NULL) \n");
			}
			sbQuery.append("GROUP BY DAY(P.FFin)\n");
			sbQuery.append("UNION ALL\n");

			sbQuery.append("SELECT 'Mes', DAY(P.FFin) dia, COUNT(PKL.Folio) TotalPL, SUM(PP.cant) TotalPiezas, null, null\n"); 
			sbQuery.append("FROM PackingList PKL \n");
			sbQuery.append("INNER JOIN EmbalarPedido EP ON PKL.Folio =  EP.FolioTemporal COLLATE Modern_Spanish_CI_AS\n");
			sbQuery.append("INNER JOIN PPedidos PP ON EP.FK01_PPEdido = PP.idPPEdido \n");
			sbQuery.append("INNER JOIN Pedidos PD ON PD.Cpedido = PP.Cpedido \n");
			sbQuery.append("INNER JOIN Pendiente P  ON  PKL.folio = P.Docto collate SQL_Latin1_General_CP1_CI_AS\n");
			sbQuery.append("INNER JOIN TREnvio TRE ON  TRE.FK01_Pendiente =  P.Folio\n");
			sbQuery.append("WHERE EP.Estado='Embalado' AND MONTH(P.FFin) = MONTH(GETDATE()) AND P.FFin IS NOT NULL AND YEAR(P.FFin ) = YEAR(GETDATE())  AND TRE.FK02_Usuario = :idUsuario  AND P.Tipo = 'Por Enviar'\n");
			if(tipo.equals("Por cliente")) {
				sbQuery.append("AND PD.GuiaXCliente = 1 \n");
			} else if(tipo.equals("clientes")) {
				sbQuery.append("AND (PD.GuiaXCliente = 0 OR PD.GuiaXCliente IS NULL) \n");
			}
			sbQuery.append("GROUP BY DAY(P.FFin)\n");

			sbQuery.append("UNION ALL \n");

			sbQuery.append("SELECT 'AllYears', MONTH(P.FFin) Mes, COUNT(PKL.Folio) TotalPL, SUM(PP.cant) TotalPiezas, null, null \n");
			sbQuery.append("FROM PackingList PKL \n");
			sbQuery.append("INNER JOIN EmbalarPedido EP ON PKL.Folio =  EP.FolioTemporal COLLATE Modern_Spanish_CI_AS\n"); 
			sbQuery.append("INNER JOIN PPedidos PP ON EP.FK01_PPEdido = PP.idPPEdido \n");
			sbQuery.append("INNER JOIN Pedidos PD ON PD.Cpedido = PP.Cpedido \n");
			sbQuery.append("INNER JOIN Pendiente P  ON  PKL.folio = P.Docto collate SQL_Latin1_General_CP1_CI_AS\n");
			sbQuery.append("INNER JOIN TREnvio TRE ON  TRE.FK01_Pendiente =  P.Folio\n");
			sbQuery.append("WHERE  ep.Estado = 'Embalado' AND TRE.FK02_Usuario = :idUsuario AND P.FFin IS NOT NULL  AND P.Tipo = 'Por Enviar'\n");
			if(tipo.equals("Por cliente")) {
				sbQuery.append("AND PD.GuiaXCliente = 1 \n");
			} else if(tipo.equals("clientes")) {
				sbQuery.append("AND (PD.GuiaXCliente = 0 OR PD.GuiaXCliente IS NULL) \n");
			}
			sbQuery.append("GROUP BY MONTH( P.FFin)  \n");

			sbQuery.append("UNION ALL\n");


			sbQuery.append("SELECT 'Year', MONTH(P.FFin) Mes, COUNT(PKL.Folio) TotalPL, SUM(pp.Cant) TotalPiezas, null, null \n");
			sbQuery.append("FROM PackingList PKL \n");
			sbQuery.append("INNER JOIN EmbalarPedido EP ON PKL.Folio =  EP.FolioTemporal COLLATE Modern_Spanish_CI_AS \n"); 
			sbQuery.append("INNER JOIN PPedidos PP ON EP.FK01_PPEdido = PP.idPPEdido \n");
			sbQuery.append("INNER JOIN Pedidos PD ON PD.Cpedido = PP.Cpedido \n");
			sbQuery.append("INNER JOIN Pendiente P  ON  PKL.folio = P.Docto collate SQL_Latin1_General_CP1_CI_AS \n");
			sbQuery.append("INNER JOIN TREnvio TRE ON  TRE.FK01_Pendiente =  P.Folio \n");
			sbQuery.append("WHERE  ep.Estado = 'Embalado' AND TRE.FK02_Usuario = :idUsuario AND YEAR(P.FFin ) =  YEAR(GETDATE()) AND P.FFin IS NOT NULL  AND P.Tipo = 'Por Enviar' \n");
			if(tipo.equals("Por cliente")) {
				sbQuery.append("AND PD.GuiaXCliente = 1 \n");
			} else if(tipo.equals("clientes")) {
				sbQuery.append("AND (PD.GuiaXCliente = 0 OR PD.GuiaXCliente IS NULL) \n");
			}
			sbQuery.append("GROUP BY MONTH(P.FFin) \n");

			sbQuery.append("UNION ALL \n");

			sbQuery.append("SELECT 'Prioridad', null, null, SUM(pp.Cant), ep.Prioridad, COUNT(ep.Prioridad) \n");
			sbQuery.append("FROM PackingList PKL  \n");
			sbQuery.append("INNER JOIN EmbalarPedido EP ON PKL.Folio =  EP.FolioTEmporal COLLATE Modern_Spanish_CI_AS \n"); 
			sbQuery.append("INNER JOIN PPedidos PP ON EP.FK01_PPEdido = PP.idPPEdido  \n");
			sbQuery.append("INNER JOIN Pedidos PD ON PD.Cpedido = PP.Cpedido \n");
			sbQuery.append("INNER JOIN Pendiente P  ON  PKL.folio = P.Docto collate SQL_Latin1_General_CP1_CI_AS \n");
			sbQuery.append("INNER JOIN TREnvio TRE ON  TRE.FK01_Pendiente =  P.Folio \n");
			sbQuery.append("WHERE  ep.Estado = 'Embalado'  AND TRE.FK02_Usuario = :idUsuario AND P.FFin IS NOT NULL  AND P.Tipo = 'Por Enviar' \n");
			if(tipo.equals("Por cliente")) {
				sbQuery.append("AND PD.GuiaXCliente = 1 \n");
			} else if(tipo.equals("clientes")) {
				sbQuery.append("AND (PD.GuiaXCliente = 0 OR PD.GuiaXCliente IS NULL) \n");
			}
			sbQuery.append("GROUP BY  ep.Prioridad \n");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, List<TrEnvio>> PrioridadesPagoCliente(Integer IdUsuario) throws ProquifaNetException{
		try {
			StringBuilder sbQuery = new StringBuilder();
			obtenerDatosPrioridades(sbQuery, "Por cliente");
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("idUsuario", IdUsuario);
			Map<String, List<TrEnvio>> mapReturn = new HashMap<String, List<TrEnvio>>();
			jdbcTemplate.query(sbQuery.toString(),parametros,new RowMapper() {
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					TrEnvio usuario = new TrEnvio();
					String tiempo = rs.getString("Tiempo");
					try{	      				    			   
						if(tiempo.equals("Prioridad")){
							usuario.setPrioridad(rs.getString("Prioridad"));
							usuario.setTotalPrioridad(rs.getInt("Total"));
							usuario.setTotalPiezas(rs.getInt("TotalPiezas"));
						}else{
							usuario.setTiempo(rs.getInt("dia"));  
							usuario.setTotalPl(rs.getInt("TotalPL"));
							usuario.setTotalPiezas(rs.getInt("TotalPiezas"));
						}
					}catch (Exception e) {
					}
					if(tiempo.equals("Quincena")){
						if(mapReturn.get("Quincena") != null){
							mapReturn.get("Quincena").add(usuario);
						}
						else {
							List<TrEnvio> list = new ArrayList<TrEnvio>();
							mapReturn.put("Quincena", list );
							mapReturn.get("Quincena").add(usuario);
						}
					} 

					if(tiempo.equals("Mes")){
						if(mapReturn.get("Mes") != null){
							mapReturn.get("Mes").add(usuario);
						} else {
							List<TrEnvio> list = new ArrayList<TrEnvio>();
							mapReturn.put("Mes", list );
							mapReturn.get("Mes").add(usuario);
						}
					}
					if(tiempo.equals("Year")){
						if(mapReturn.get("Year") != null){
							mapReturn.get("Year").add(usuario);
						}else {
							List<TrEnvio> list = new ArrayList<TrEnvio>();
							mapReturn.put("Year", list );
							mapReturn.get("Year").add(usuario);
						}	    			   
					}  
					if(tiempo.equals("AllYears")){
						if(mapReturn.get("AllYears") != null){
							mapReturn.get("AllYears").add(usuario);
						}else {
							List<TrEnvio> list = new ArrayList<TrEnvio>();
							mapReturn.put("AllYears", list );
							mapReturn.get("AllYears").add(usuario);
						}	    			   
					} 
					if(tiempo.equals("Prioridad")){
						if(mapReturn.get("Prioridad") != null){
							mapReturn.get("Prioridad").add(usuario);
						} else {
							List<TrEnvio> list = new ArrayList<TrEnvio>();
							mapReturn.put("Prioridad", list );
							mapReturn.get("Prioridad").add(usuario);
						}
					}

					return null;
				}    	   
			});	
			return mapReturn;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public String obtenerComentarios(String packingList) throws ProquifaNetException{
		try {
			StringBuilder sbQuery = new StringBuilder("\n");
			sbQuery.append("SELECT STUFF( (SELECT '\n· ' + CAST(P.ComentariosA as VARCHAR(MAX))  \n");
			sbQuery.append("FROM PackingList	PK \n");
			sbQuery.append("INNER JOIN EmbalarPedido EP ON PK.Folio =  EP.FolioTemporal COLLATE Modern_Spanish_CI_AS \n");
			sbQuery.append("INNER JOIN PPEDIDOS PP ON EP.FK01_PPEdido = PP.idPPEdido \n");
			sbQuery.append("INNER JOIN PEDIDOS P ON P.Cpedido = PP.Cpedido \n");
			sbQuery.append("WHERE PK.Folio IN (").append(packingList).append(")  AND CAST(P.ComentariosA AS VARCHAR(MAX)) <> '' \n");
			sbQuery.append("GROUP BY  CAST(P.ComentariosA as VARCHAR(MAX)) \n");
			sbQuery.append("FOR XML PATH ('')), 1, 1, '') \n");
			log.info(sbQuery.toString());
			Map <String,Object> parametros = new HashMap<String, Object>();
			parametros.put("packingList", packingList);
			return jdbcTemplate.queryForObject(sbQuery.toString(),parametros, String.class);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean guardarPesoPaquete(Parametro parametro) throws ProquifaNetException {
		try {
			Map <String,Object> parametros = new HashMap<String, Object>();
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("INSERT INTO Pedido_EnvioXCliente(FK01_PackingList, FormaEnvio, Peso, Alto, Largo, Ancho) VALUES(:packingList, 'NoGuia', :peso, :alto, :largo, :ancho) \n");
			
			parametros.put("packingList", parametro.getIdPackingList());
			parametros.put("peso", parametro.getEnvio().getPeso());
			parametros.put("ancho", parametro.getEnvio().getWidth());
			parametros.put("alto", parametro.getEnvio().getHeight());
			parametros.put("largo", parametro.getEnvio().getLength());
			
			jdbcTemplate.update(sbQuery.toString(), parametros);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	
	@Override
	public boolean guardarPackingList(Parametro parametro) throws ProquifaNetException {
		try {
			Map <String,Object> parametros = new HashMap<String, Object>();
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("UPDATE Pedido_EnvioXCliente SET FK01_PackingList = :packingList WHERE FK02_Pedido = :idPedido \n");
			
			parametros.put("packingList", parametro.getIdPackingList());
			parametros.put("idPedido", parametro.getIdPedido());
			
			jdbcTemplate.update(sbQuery.toString(), parametros);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
}
