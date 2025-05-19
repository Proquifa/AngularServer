package com.proquifa.net.persistencia.despachos.trabajarRuta.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.Pendiente;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.despachos.EstadisticaUsuarioEmbalar;
import com.proquifa.net.modelo.despachos.trabajarRuta.TrAlmacen;
import com.proquifa.net.modelo.despachos.trabajarRuta.TrContactoCliente;
import com.proquifa.net.modelo.despachos.trabajarRuta.TrEnvio;
import com.proquifa.net.modelo.despachos.trabajarRuta.TrPackingList;
import com.proquifa.net.modelo.despachos.trabajarRuta.TrTotalGraficasAlmacen;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.comun.PendienteDAO;
import com.proquifa.net.persistencia.despachos.trabajarRuta.TrAlmacenDAO;

@Repository
public class TrAlmacenDAOImpl extends DataBaseDAO implements TrAlmacenDAO {

	@Autowired
	PendienteDAO pendienteDAO;
	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public  Map<String, Integer> getObjetivos() throws ProquifaNetException{
		try{
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append(" \n");
			sbQuery.append("SELECT 'Objetivo' tipo, COALESCE(MAX(cant),0) cant FROM ( \n"); 
			sbQuery.append("SELECT COUNT(PL.Folio) cant  \n");
			sbQuery.append("FROM Pendiente PEN  \n");
			sbQuery.append("INNER JOIN PackingList PL ON PL.Folio = PEN.Docto COLLATE Modern_Spanish_CI_AS \n"); 
			sbQuery.append("WHERE PEN.Tipo = 'Por Entregar' AND PEN.FFin IS NOT NULL  \n");
			sbQuery.append("GROUP BY CAST(PEN.FFin as DATE) ) Objetivo  \n");
			sbQuery.append("UNION ALL \n");
			sbQuery.append("SELECT 'Hoy' tipo, COALESCE(COUNT(PL.Folio),0) cant \n"); 
			sbQuery.append("FROM Pendiente PEN  \n");
			sbQuery.append("INNER JOIN PackingList PL ON PL.Folio = PEN.Docto COLLATE Modern_Spanish_CI_AS \n"); 
			sbQuery.append("WHERE PEN.Tipo = 'Por Entregar' AND CAST(PEN.FFin as DATE) = CAST(GETDATE() as DATE) \n"); 
			sbQuery.append("UNION ALL \n");
			sbQuery.append("SELECT 'Deseadas' AS tipo, COUNT(PL.Folio) cant \n"); 
			sbQuery.append("FROM Pendiente PEN  \n");
			sbQuery.append("INNER JOIN PackingList PL ON PL.Folio = PEN.Docto COLLATE Modern_Spanish_CI_AS \n"); 
			sbQuery.append("WHERE PEN.Tipo = 'Por Entregar' AND (PEN.FFin IS NULL OR CAST(PEN.FFin as DATE) = CAST(GETDATE() AS DATE)) \n"); 
			sbQuery.append("UNION ALL \n");
			sbQuery.append("SELECT TOP 1 'Minimo' tipo, COALESCE(COUNT(PL.Folio),0)/2 cant \n"); 
			sbQuery.append("FROM Pendiente PEN  \n");
			sbQuery.append("INNER JOIN PackingList PL ON PL.Folio = PEN.Docto COLLATE Modern_Spanish_CI_AS \n"); 
			sbQuery.append("WHERE PEN.Tipo = 'Por Entregar' AND PEN.FFin IS NOT NULL  \n");
			sbQuery.append("GROUP BY CAST(PEN.FFin as DATE) ORDER BY cant desc \n");


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
			sbQuery.append("DECLARE @variableManana as date \n"); 
			sbQuery.append("SET @variableManana = DATEADD(DAY, 1, getdate()) \n"); 
			sbQuery.append("DECLARE @variablePasado as date  \n");
			sbQuery.append("SET @variablePasado = DATEADD(DAY, 2, getdate()) \n"); 
			sbQuery.append("DECLARE @variableHoy as date  \n");
			sbQuery.append("SET @variableHoy = getdate()  \n");
			sbQuery.append("DECLARE @TABLA TABLE (docto varchar(100), FPEntrega datetime) \n"); 
			sbQuery.append("insert into @TABLA   \n");
			sbQuery.append("SELECT p.docto, pp.FPEntrega FROM Pendiente p \n"); 
			sbQuery.append("LEFT JOIN PackingList pl on pl.Folio COLLATE DATABASE_DEFAULT = p.Docto COLLATE DATABASE_DEFAULT \n"); 
			sbQuery.append("LEFT JOIN PPackingList ppl ON ppl.FK01_PackingList = pl.PK_PackingList  \n");
			sbQuery.append("LEFT JOIN EmbalarPedido ep ON ep.PK_EmbalarPedido = ppl.FK02_EmbalarPedido  \n");
			sbQuery.append("LEFT JOIN Ppedidos pp ON pp.idPPedido = ep.FK01_PPedido  \n");
			sbQuery.append("LEFT JOIN Pedidos pd ON pd.Cpedido = pp.Cpedido  \n");
			sbQuery.append("LEFT JOIN Clientes c ON c.Clave = pd.idCliente  \n");
			sbQuery.append("WHERE p.tipo = 'Por Entregar' AND p.FFin is null AND c.clave is not null GROUP BY p.docto,  pp.FPEntrega; \n"); 
			
			sbQuery.append("SELECT 'Hoy' as tab, COUNT(*) as num FROM ( \n");
			sbQuery.append("SELECT Docto FROM @TABLA  \n");
			sbQuery.append("where FPEntrega <= @variableHoy \n"); 
			sbQuery.append("GROUP BY Docto ) Hoy \n");
			sbQuery.append("UNION  \n");
			sbQuery.append("SELECT 'Mañana' as tab, COUNT(*) FROM ( \n");
			sbQuery.append("SELECT Docto FROM @TABLA  \n");
			sbQuery.append("where FPEntrega = @variableManana \n"); 
			sbQuery.append("GROUP BY Docto ) Ma \n");
			sbQuery.append("UNION  \n");
			sbQuery.append("SELECT 'Pasado' as tab, COUNT(*) FROM ( \n");
			sbQuery.append("SELECT Docto FROM @TABLA  \n");
			sbQuery.append("where FPEntrega = @variablePasado \n"); 
			sbQuery.append("GROUP BY Docto ) Pasado \n");
			sbQuery.append("UNION  \n");
			sbQuery.append("SELECT 'Futuro' as tab, COUNT(*) FROM ( \n");
			sbQuery.append("SELECT Docto FROM @TABLA  \n");
			sbQuery.append("where FPEntrega > @variablePasado \n"); 
			sbQuery.append("GROUP BY Docto ) Futuro \n");
			sbQuery.append("UNION  \n");
			sbQuery.append("SELECT 'Todo' as tab, COUNT(*) FROM ( \n");
			sbQuery.append("SELECT Docto FROM @TABLA \n");
			sbQuery.append("GROUP BY Docto ) Todo \n");

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
					"WHERE p.tipo = 'Por Entregar' AND p.FFin is null AND c.clave is not null \n" + 
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<TrAlmacen> getClientes() throws ProquifaNetException{
		try{
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append(" \n");
			sbQuery.append("SELECT PL.Clave, PL.Nombre, SUM(PL.Cant) Cant, COUNT(PL.Docto) numPL, \n");
			sbQuery.append("COUNT(CASE WHEN PL.Prioridad= 'P1' THEN 1 END) AS P1,  \n");
			sbQuery.append("COUNT(CASE WHEN PL.Prioridad= 'P2' THEN 1 END) AS P2,  \n");
			sbQuery.append("COUNT(CASE WHEN PL.Prioridad= 'P3' THEN 1 END) AS P3 \n");
			sbQuery.append("FROM ( \n");
			sbQuery.append("SELECT C.Clave, C.Nombre, SUM(PP.Cant) Cant, P.Docto, EP.Prioridad \n");
			sbQuery.append("FROM Pendiente p \n");
			sbQuery.append("LEFT JOIN PackingList pl on pl.Folio COLLATE DATABASE_DEFAULT = p.Docto COLLATE DATABASE_DEFAULT \n");
			sbQuery.append("LEFT JOIN PPackingList ppl ON ppl.FK01_PackingList = pl.PK_PackingList \n");
			sbQuery.append("LEFT JOIN EmbalarPedido ep ON ep.PK_EmbalarPedido = ppl.FK02_EmbalarPedido \n");
			sbQuery.append("LEFT JOIN Ppedidos pp ON pp.idPPedido = ep.FK01_PPedido \n");
			sbQuery.append("LEFT JOIN Pedidos pd ON pd.Cpedido = pp.Cpedido \n");
			sbQuery.append("LEFT JOIN Clientes c ON c.Clave = pd.idCliente \n");
			sbQuery.append("WHERE p.tipo = 'Por Entregar' AND p.FFin is null AND c.clave is not null \n");
			sbQuery.append("GROUP BY C.Clave, C.Nombre,P.Docto, EP.Prioridad) PL \n");
			sbQuery.append("GROUP BY PL.Clave, PL.Nombre ORDER BY P1 DESC,P2 DESC, P3 DESC \n");


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
	public Map<String, List<TrPackingList>> getPackingListClient(int idCliente) throws ProquifaNetException {
		try{
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append(" \n");
			sbQuery.append("SELECT c.clave, p.Folio as idPendiente, p.Docto, sum(pp.Cant) AS cant, ppl.Folio, ppl.Tipo, CAST(pd.ComentariosA AS NVARCHAR(MAX)) AS ComentariosA,\n" + 
					"COUNT(CASE WHEN ep.Prioridad= 'P1' THEN 1 END) AS P1, \n" + 
					"COUNT(CASE WHEN ep.Prioridad= 'P2' THEN 1 END) AS P2, \n" + 
					"COUNT(CASE WHEN ep.Prioridad= 'P3' THEN 1 END) AS P3\n" + 
					"FROM Pendiente p\n" + 
					"LEFT JOIN PackingList pl on pl.Folio COLLATE DATABASE_DEFAULT = p.Docto COLLATE DATABASE_DEFAULT\n" + 
					"LEFT JOIN PPackingList ppl ON ppl.FK01_PackingList = pl.PK_PackingList\n" + 
					"LEFT JOIN EmbalarPedido ep ON ep.PK_EmbalarPedido = ppl.FK02_EmbalarPedido\n" + 
					"LEFT JOIN Ppedidos pp ON pp.idPPedido = ep.FK01_PPedido\n" + 
					"LEFT JOIN Pedidos pd ON pd.Cpedido = pp.Cpedido\n" + 
					"LEFT JOIN Clientes c ON c.Clave = pd.idCliente\n" + 
					"WHERE p.tipo = 'Por Entregar' AND p.FFin is null AND c.clave = '"+ idCliente +"'\n"
					+ " GROUP BY c.clave, p.Folio, ppl.Folio, p.Docto, pp.Cant, ppl.Tipo, CAST(pd.ComentariosA AS NVARCHAR(MAX))");

			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, List<TrPackingList>> mapReturn = new HashMap<String, List<TrPackingList>>();
			jdbcTemplate.query(sbQuery.toString(), map, new RowMapper() {
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					TrPackingList trPackingList = new TrPackingList();
					trPackingList.setIdClave(rs.getInt("clave"));
					trPackingList.setIdPendiente(rs.getInt("idPendiente"));
					trPackingList.setPackingList(rs.getString("Docto"));
					trPackingList.setCant(rs.getInt("cant"));
					trPackingList.setFolio(rs.getString("Folio"));
					trPackingList.setTipo(rs.getString("Tipo"));
					trPackingList.setComentario(rs.getString("ComentariosA"));
					trPackingList.setP1(rs.getInt("P1"));
					trPackingList.setP2(rs.getInt("P2"));
					trPackingList.setP3(rs.getInt("P3"));

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
	public boolean insertEjecutarRutaAlmacen(TrContactoCliente trContactoCliente) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idPendiente", trContactoCliente.getIdPendiente());
		map.put("contacto", trContactoCliente.getContacto());
		map.put("tel", trContactoCliente.getTel());
		map.put("puesto", trContactoCliente.getPuesto());
		map.put("email", trContactoCliente.getEmail());
		map.put("idUsuario", trContactoCliente.getIdUsuario());
		map.put("packingList", trContactoCliente.getPackingList());

		try {
			super.jdbcTemplate.update("INSERT INTO TrContactoCliente (FK01_Pendiente, Contacto, Tel, Puesto, Email, FK02_Usuario, FK03_PackingList) VALUES (:idPendiente, :contacto, :tel, :puesto, :email, :idUsuario, (SELECT PK_PackingList FROM PackingList WHERE Folio = :packingList))", map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return false;
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Boolean actualizarTrAlmacen( TrContactoCliente trContactoCliente ) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();	
			
			String query = "SELECT DISTINCT (SELECT Alias FROM Empresa WHERE PK_Empresa = fe.EmpresaEmisor) as FPor, fe.Folio as Factura,  (SELECT usuario FROM Empleados WHERE clave = cl.cobrador) as Cobrador \n" + 
					"FROM TRContactoCliente tre \n" + 
					"LEFT JOIN PackingList pl ON pl.PK_PackingList = tre.FK03_PackingList \n" + 
					"LEFT JOIN PPackingList ppl ON ppl.FK01_PackingList = pl.PK_PackingList \n" + 
					"LEFT JOIN FacturaElectronica fe ON fe.PK_Factura = ppl.FK03_FacturaElectronica \n" + 
					"LEFT JOIN Empresa AS Emp ON Emp.PK_Empresa = FE.EmpresaEmisor \n" +
					"LEFT JOIN Facturas AS F ON F.Factura COLLATE SQL_Latin1_General_CP1_CI_AS = FE.Folio AND F.Fpor COLLATE SQL_Latin1_General_CP1_CI_AS = Emp.Alias \n" + 
					"LEFT JOIN Clientes cl ON cl.Clave = fe.Cliente \n" + 
					"WHERE tre.FK01_PEndiente = :idPendiente AND tre.FK03_PackingList IS NOT NULL AND F.Estado='Por Cobrar'";
			map.put("idPendiente", trContactoCliente.getIdPendiente());
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
						throw new SQLException();
					}
					return null;
				}
			});
			
			map = new HashMap<String, Object>();
			map.put("idPendiente", trContactoCliente.getIdPendiente());
			String queryUpdate = "update ppedidos set estado = 'Entregado' where idPPedido in(  \n" + 
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
	@SuppressWarnings({ "unchecked", "rawtypes" })	
	public Map<String, List<TrPackingList>> obtenerEstadisticaUsuario(Parametro  parametro) throws ProquifaNetException{
		try{
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("SELECT 'Quincena' Tiempo, DAY(P.FFin) dia,  COUNT(PKL.folio) TotalPL, SUM(PP.cant) TotalPiezas, NULL Prioridad, NULL Total\n"); 
			sbQuery.append("FROM PackingList PKL \n");
			sbQuery.append("INNER JOIN EmbalarPedido EP ON PKL.Folio =  EP.FolioTemporal COLLATE Modern_Spanish_CI_AS\n"); 
			sbQuery.append("INNER JOIN PPedidos PP ON EP.FK01_PPEdido = PP.idPPEdido \n");
			sbQuery.append("INNER JOIN Pendiente P  ON  PKL.folio = P.Docto collate SQL_Latin1_General_CP1_CI_AS\n");
			sbQuery.append("INNER JOIN TRContactoCliente TRCC ON  p.Folio = TRCC.FK01_Pendiente \n");
			sbQuery.append("WHERE Ep.Estado = 'Embalado' AND YEAR(P.FFin) = YEAR(GETDATE())  AND  MONTH(P.FFin) = MONTH(GETDATE()) AND  TRCC.FK02_Usuario = :idUsuario AND  P.FFin IS NOT NULL AND P.Tipo = 'Por Entregar'\n");
			sbQuery.append("AND CASE WHEN DAY(P.FFin) <= 15 THEN 1 ELSE 2  END = CASE WHEN DAY(GETDATE()) <= 15 THEN 1 ELSE 2 END  \n");
			sbQuery.append("GROUP BY DAY(P.FFin)\n");
			sbQuery.append("UNION ALL \n");
			sbQuery.append("SELECT 'Mes', DAY(P.FFin) dia, COUNT(PKL.Folio) TotalPL, SUM(PP.cant) TotalPiezas, null, null\n"); 
			sbQuery.append("FROM PackingList PKL \n");
			sbQuery.append("INNER JOIN EmbalarPedido EP ON PKL.Folio =  EP.FolioTemporal COLLATE Modern_Spanish_CI_AS\n");
			sbQuery.append("INNER JOIN PPedidos PP ON EP.FK01_PPEdido = PP.idPPEdido \n");
			sbQuery.append("INNER JOIN Pendiente P  ON  PKL.folio = P.Docto collate SQL_Latin1_General_CP1_CI_AS\n");
			sbQuery.append("INNER JOIN TRContactoCliente TRCC ON  p.Folio = TRCC.FK01_Pendiente \n");
			sbQuery.append("WHERE EP.Estado='Embalado' AND MONTH(P.FFin) = MONTH(GETDATE()) AND P.FFin IS NOT NULL AND YEAR(P.FFin ) = YEAR(GETDATE())  AND TRCC.FK02_Usuario = :idUsuario  AND P.Tipo = 'Por Entregar'\n");
			sbQuery.append("GROUP BY DAY(P.FFin)\n");
			sbQuery.append("UNION ALL \n");
			sbQuery.append("SELECT 'AllYears', MONTH(P.FFin) Mes, COUNT(PKL.Folio) TotalPL, SUM(PP.cant) TotalPiezas, null,null\n");
			sbQuery.append("FROM PackingList PKL \n");
			sbQuery.append("INNER JOIN EmbalarPedido EP ON PKL.Folio =  EP.FolioTemporal COLLATE Modern_Spanish_CI_AS\n"); 
			sbQuery.append("INNER JOIN PPedidos PP ON EP.FK01_PPEdido = PP.idPPEdido \n");
			sbQuery.append("INNER JOIN Pendiente P  ON  PKL.folio = P.Docto collate SQL_Latin1_General_CP1_CI_AS\n");
			sbQuery.append("INNER JOIN TRContactoCliente TRCC ON  p.Folio = TRCC.FK01_Pendiente \n");
			sbQuery.append("WHERE  ep.Estado = 'Embalado' AND TRCC.FK02_Usuario = :idUsuario AND P.FFin IS NOT NULL  AND P.Tipo = 'Por Entregar'\n");
			sbQuery.append("GROUP BY MONTH( P.FFin)  \n");
			sbQuery.append("UNION ALL \n");
			sbQuery.append("SELECT 'Year', MONTH(P.FFin) Mes, COUNT(PKL.Folio) TotalPL, SUM(pp.Cant) TotalPiezas,null,null\n");
			sbQuery.append("FROM PackingList PKL \n");
			sbQuery.append("INNER JOIN EmbalarPedido EP ON PKL.Folio =  EP.FolioTemporal COLLATE Modern_Spanish_CI_AS\n"); 
			sbQuery.append("INNER JOIN PPedidos PP ON EP.FK01_PPEdido = PP.idPPEdido \n");
			sbQuery.append("INNER JOIN Pendiente P  ON  PKL.folio = P.Docto collate SQL_Latin1_General_CP1_CI_AS\n");
			sbQuery.append("INNER JOIN TRContactoCliente TRCC ON  p.Folio = TRCC.FK01_Pendiente \n");
			sbQuery.append("WHERE  ep.Estado = 'Embalado' AND TRCC.FK02_Usuario = :idUsuario AND YEAR(P.FFin ) =  YEAR(GETDATE()) AND P.FFin IS NOT NULL  AND P.Tipo = 'Por Entregar'\n");
			sbQuery.append("GROUP BY MONTH(P.FFin)\n");
			sbQuery.append("UNION ALL\n");
			sbQuery.append("SELECT 'Prioridad', null, null, SUM(pp.Cant), ep.Prioridad, COUNT(ep.Prioridad)\n");
			sbQuery.append("FROM PackingList PKL \n");
			sbQuery.append("INNER JOIN EmbalarPedido EP ON PKL.Folio =  EP.FolioTEmporal COLLATE Modern_Spanish_CI_AS\n"); 
			sbQuery.append("INNER JOIN PPedidos PP ON EP.FK01_PPEdido = PP.idPPEdido \n");
			sbQuery.append("INNER JOIN Pendiente P  ON  PKL.folio = P.Docto collate SQL_Latin1_General_CP1_CI_AS\n");
			sbQuery.append("INNER JOIN TRContactoCliente TRCC ON  p.Folio = TRCC.FK01_Pendiente \n");
			sbQuery.append("WHERE  ep.Estado = 'Embalado'  AND TRCC.FK02_Usuario = :idUsuario AND P.FFin IS NOT NULL  AND P.Tipo = 'Por Entregar'\n");
			sbQuery.append("GROUP BY  ep.Prioridad\n");
			
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("idUsuario", parametro.getIdUsuarioLogueado());
			Map<String, List<TrPackingList>> mapReturn = new HashMap<String, List<TrPackingList>>();
		    getJdbcTemplate().query(sbQuery.toString(),parametros,new RowMapper() {
		    	@Override
		           public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    		TrPackingList usuario = new TrPackingList();
		    		   String tiempo = rs.getString("Tiempo");
		    		   try{	      				    			   
		    			   if(tiempo.equals("Prioridad")){
		    				  usuario.setPrioridad(rs.getString("Prioridad"));
		    				  usuario.setTotalPrioridad(rs.getInt("Total"));
		    				  usuario.setTotalPiezas(rs.getInt("TotalPiezas"));
		    			   }else{
		    				   usuario.setTiempo(rs.getInt("dia"));  
		    				   usuario.setTotalPL(rs.getInt("TotalPL"));
		    				   usuario.setTotalPiezas(rs.getInt("TotalPiezas"));
		    			   }
		    		   }catch (Exception e) {
		               }
		    		   if(tiempo.equals("Quincena")){
		    			   if(mapReturn.get("Quincena") != null){
		    				   mapReturn.get("Quincena").add(usuario);
		    			   }
		    			   else {
			    			   List<TrPackingList> list = new ArrayList<TrPackingList>();
			    			   mapReturn.put("Quincena", list );
			    			   mapReturn.get("Quincena").add(usuario);
			    		   }
		    		   } 
		    		   
		    		   if(tiempo.equals("Mes")){
		    			   if(mapReturn.get("Mes") != null){
		    				   mapReturn.get("Mes").add(usuario);
		    			   } else {
			    			   List<TrPackingList> list = new ArrayList<TrPackingList>();
			    			   mapReturn.put("Mes", list );
			    			   mapReturn.get("Mes").add(usuario);
			    		   }
		    		   }
		    		   if(tiempo.equals("Year")){
		    			   if(mapReturn.get("Year") != null){
		    				   mapReturn.get("Year").add(usuario);
		    			   }else {
			    			   List<TrPackingList> list = new ArrayList<TrPackingList>();
			    			   mapReturn.put("Year", list );
			    			   mapReturn.get("Year").add(usuario);
			    		   }	    			   
		    		   }  
		    		   if(tiempo.equals("AllYears")){
		    			   if(mapReturn.get("AllYears") != null){
		    				   mapReturn.get("AllYears").add(usuario);
		    			   }else {
			    			   List<TrPackingList> list = new ArrayList<TrPackingList>();
			    			   mapReturn.put("AllYears", list );
			    			   mapReturn.get("AllYears").add(usuario);
			    		   }	    			   
		    		   } 
		    		   if(tiempo.equals("Prioridad")){
		    			   if(mapReturn.get("Prioridad") != null){
		    				   mapReturn.get("Prioridad").add(usuario);
		    			   } else {
			    			   List<TrPackingList> list = new ArrayList<TrPackingList>();
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

}
