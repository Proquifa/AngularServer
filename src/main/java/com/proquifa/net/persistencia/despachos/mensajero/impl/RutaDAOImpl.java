/**
 * 
 */
package com.proquifa.net.persistencia.despachos.mensajero.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import org.apache.poi.util.SystemOutLogger;
import org.bouncycastle.operator.bc.BcSignerOutputStream;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.comun.Folio;
import com.proquifa.net.modelo.comun.Pendiente;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.despachos.RutaDP;
import com.proquifa.net.modelo.despachos.mensajero.AsignarMensajero;
import com.proquifa.net.modelo.despachos.mensajero.PendientesMensajero;
import com.proquifa.net.modelo.despachos.mensajero.Ruta;
import com.proquifa.net.modelo.despachos.mensajero.TotalMensajero;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.comun.FolioDAO;
import com.proquifa.net.persistencia.comun.FuncionDAO;
import com.proquifa.net.persistencia.comun.PendienteDAO;
import com.proquifa.net.persistencia.despachos.mensajero.MensajeroDAO;
import com.proquifa.net.persistencia.despachos.mensajero.RutaDAO;

/**
 * @author ymendez
 *
 */
@Repository
public class RutaDAOImpl extends DataBaseDAO implements RutaDAO {

	@Autowired
	PendienteDAO pendienteDAO;
	
	@Autowired
	MensajeroDAO mensajeroDAO;

	@Autowired
	FuncionDAO funcionDAO;

	@Autowired
	FolioDAO folioDAO;
	
	final Logger log = LoggerFactory.getLogger(RutaDAOImpl.class);

	/* (non-Javadoc)
	 * @see com.proquifa.net.persistencia.despachos.mensajero.RutaDAO#saveRutaEvento()
	 */
	@Override
	public boolean saveRutaEvento(AsignarMensajero ruta) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("INSERT INTO Ruta_Evento(AK01_Ruta, FK01_Evento, Tipo) VALUES(:ruta, :evento, :tipo) \n");
			sbQuery.append(" \n");

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ruta", ruta.getRuta());
			map.put("evento", ruta.getFolio());

			map.put("tipo", ruta.getEvento());
			
			log.info(sbQuery.toString()+ " :"+ruta.getRuta()+ " :"+ruta.getFolio()+ " :"+ ruta.getEvento());

			super.jdbcTemplate.update(sbQuery.toString(), map);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}

	/* (non-Javadoc)
	 * @see com.proquifa.net.persistencia.despachos.mensajero.RutaDAO#actualizarEvento(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean actualizarEvento(String ruta, String folio, String tabla, String campo) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("UPDATE ").append(tabla).append(" SET idRuta = :ruta, EstadoRuta = 'AEjecutar' WHERE ").append(campo).append(" = :folio");
			if (tabla.equals("RutaDP"))
				sbQuery.append(" AND EstadoRuta IS NULL AND idRuta IS NULL \n");

			sbQuery.append(" \n");

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ruta", ruta);
			map.put("folio", folio);

			super.jdbcTemplate.update(sbQuery.toString(), map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}

	@Override
	public boolean saveRutaDP(String idRuta, String idRutaDP, String packingList) throws ProquifaNetException {

		StringBuilder sbQuery = new StringBuilder(" \n");
		sbQuery.append(" \n");
		sbQuery.append("SELECT COUNT(DP.idDP) FROM RutaDP DP \n");
		sbQuery.append("INNER JOIN PackingList PL ON PL.PK_PackingList = DP.FK02_PackingList \n");
		sbQuery.append("WHERE PL.Folio = :Folio AND DP.Entrega = 'No Realizada' \n");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Folio", packingList);
		map.put("idRuta", idRuta);
		Integer exiteDP = jdbcTemplate.queryForObject(sbQuery.toString(), map, Integer.class);
		if (exiteDP > 0) {
			sbQuery = new StringBuilder(" \n");
			sbQuery.append("UPDATE DP SET idRuta = :idRuta, Entrega = NULL, EstadoRuta = 'AEjecutar' FROM RutaDP DP \n");
			sbQuery.append("INNER JOIN PackingList PL ON PL.PK_PackingList = DP.FK02_PackingList \n");
			sbQuery.append("WHERE PL.Folio = :Folio AND DP.Entrega = 'No Realizada' \n");
			jdbcTemplate.update(sbQuery.toString(), map);
			return true;
		}
		return saveRutaDP(idRuta, idRutaDP, packingList, false); 
	}

	@Override
	public boolean saveRutaDP(String idRuta, String idRutaDP, String packingList, boolean isGuadalajara) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder();
			sbQuery.append("SELECT DISTINCT :idRuta idRuta, '' idSurtido, PED.idCliente, F.Factura, F.FPor, RE.Factura folioRemision, RE.FPor facturadoRemision, RE.PK_Remision idRemision, \n"); 
			sbQuery.append("chkDiferente, mapa, Destino, Pais, PED.Estado, Calle, Delegacion, CP, PED.Ruta, ZonaMensajeria, Diario, DiaDe1, DiaA1, DiaDe2, \n");
			sbQuery.append("DiaA2, Lunes, LuDe1, LuA1, LuDe2, LuA2, Martes, MaDe1, MaA1, MaDe2, MaA2, Miercoles, MiDe1, MiA1, MiDe2, MiA2, Jueves, JuDe1, JuA1, JuDe2, JuA2, \n"); 
			sbQuery.append("Viernes, ViDe1, ViA1, ViDe2, ViA2, GETDATE() Fecha,'' Comentarios,'' ComentarioSurtir,0 FacturaRemision, PL.PK_PackingList idPackingList, 'AEjecutar' EstadoRuta, PED.FK01_Horario idHorario \n");
			sbQuery.append("FROM PackingList PL \n");
			sbQuery.append("INNER JOIN PPackingList PPL ON PPL.Fk01_PackingList = PL.PK_PAckingList \n");
			sbQuery.append("INNER JOIN EmbalarPEdido EP ON EP.PK_EmbalarPEdido = PPL.FK02_EmbalarPEdido \n");
			sbQuery.append("INNER JOIN PPEdidos PP ON PP.idPPedido = EP.FK01_PPedido \n");
			sbQuery.append("INNER JOIN PEdidos PED ON PED.CPedido = PP.CPedido \n");
			sbQuery.append("LEFT JOIN (SELECT PF.* FROM Facturas F INNER JOIN PFacturas PF ON F.Factura = PF.Factura AND F.FPor = PF.Fpor WHERE F.Estado IS NULL OR (F.Estado <> 'Por Cancelar' AND F.Estado <> 'Cancelada')) F ON PP.CPedido = F.CPedido AND F.PPedido = PP.Part \n");
			sbQuery.append("LEFT JOIN Remisiones RE ON RE.PK_Remision = FK04_Remision \n");
			sbQuery.append("WHERE PL.Folio = :Folio \n");
			sbQuery.append("ORDER BY F.Factura \n");
			sbQuery.append(" \n");

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idRutaDP", idRutaDP);
			map.put("idRuta", idRuta);
			map.put("Folio", packingList);

			List<RutaDP> listDP = jdbcTemplate.query(sbQuery.toString(), map, new BeanPropertyRowMapper<>(RutaDP.class));

			for (RutaDP rutaDP : listDP) {
				Folio folioDP = folioDAO.obtenerFolioPorConcepto("Despachos", true);
				rutaDP.setIdRuta(folioDP.getFolioCompleto());

				sbQuery = new StringBuilder("INSERT INTO RutaDP (idDP, idRuta, idSurtido, idCliente, Factura, FPor, chkDiferente, Mapa, Destino, Pais, Estado, ");
				sbQuery.append("Calle, Delegacion, CP,Ruta, ZonaMensajeria, Diario, DiaDe1, DiaA1, DiaDe2, DiaA2, Lunes, LuDe1, LuA1, LuDe2, LuA2, Martes, MaDe1, MaA1, MaDe2, ");
				sbQuery.append("MaA2, Miercoles, MiDe1, MiA1, MiDe2, MiA2, Jueves, JuDe1, JuA1, JuDe2, JuA2, Viernes, ViDe1, ViA1, ViDe2, ViA2, Fecha, Comentarios, ComentarioSurtir, FacturaORemision, FK02_PackingList, EstadoRuta,FK01_Direccion) \n");

				sbQuery.append("SELECT DISTINCT :idRutaDP idRutaDP, :idRuta idRuta, '' idSurtido, PED.idCliente, :factura, :fpor, \n"); 
				sbQuery.append("chkDiferente, mapa, Destino, Pais, PED.Estado, Calle, Delegacion, CP, PED.Ruta, ZonaMensajeria, Diario, DiaDe1, DiaA1, DiaDe2, \n");
				sbQuery.append("DiaA2, Lunes, LuDe1, LuA1, LuDe2, LuA2, Martes, MaDe1, MaA1, MaDe2, MaA2, Miercoles, MiDe1, MiA1, MiDe2, MiA2, Jueves, JuDe1, JuA1, JuDe2, JuA2, \n"); 
				sbQuery.append("Viernes, ViDe1, ViA1, ViDe2, ViA2, GETDATE() Fecha,'' Comentarios,'' ComentarioSurtir, :remision FacturaRemision, PL.PK_PackingList idPackingList, '").append(isGuadalajara ? "Colectado" : "AEjecutar").append("' EstadoRuta, PED.FK01_Horario idHorario \n");
				sbQuery.append("FROM PackingList PL \n");
				sbQuery.append("INNER JOIN PPackingList PPL ON PPL.Fk01_PackingList = PL.PK_PAckingList \n");
				sbQuery.append("INNER JOIN EmbalarPEdido EP ON EP.PK_EmbalarPEdido = PPL.FK02_EmbalarPEdido \n");
				sbQuery.append("INNER JOIN PPEdidos PP ON PP.idPPedido = EP.FK01_PPedido \n");
				sbQuery.append("INNER JOIN PEdidos PED ON PED.CPedido = PP.CPedido \n");
				sbQuery.append("LEFT JOIN (SELECT PF.* FROM Facturas F INNER JOIN PFacturas PF ON F.Factura = PF.Factura AND F.FPor = PF.Fpor WHERE F.Estado IS NULL OR (F.Estado <> 'Por Cancelar' AND F.Estado <> 'Cancelada')) F ON PP.CPedido = F.CPedido AND F.PPedido = PP.Part \n");
				sbQuery.append("LEFT JOIN Remisiones RE ON RE.PK_Remision = FK04_Remision \n");
				sbQuery.append("WHERE PL.Folio = :Folio \n");
				if (rutaDP.getIdRemision() != null && rutaDP.getIdRemision() > 0)
					sbQuery.append("AND RE.PK_Remision = :idRemision \n");
				else
					sbQuery.append("AND F.Factura = :factura AND F.FPor = :fpor \n");
				sbQuery.append(" \n");

				map = new HashMap<String, Object>();
				map.put("idRutaDP", rutaDP.getIdRuta());
				map.put("idRuta", idRuta);
				if (rutaDP.getIdRemision() != null && rutaDP.getIdRemision() > 0) {
					map.put("factura", rutaDP.getFolioRemision());
					map.put("fpor", rutaDP.getFacturadoRemision());
					map.put("remision", 1);
					map.put("idRemision", rutaDP.getIdRemision());
				} else {
					map.put("factura", rutaDP.getFactura());
					map.put("fpor", rutaDP.getFpor());
					map.put("remision", 0);
				}

				map.put("Folio", packingList);
				System.out.println("Insert RUTADP: "+sbQuery.toString());
				System.out.println("Id Remision : "+rutaDP.getIdRemision());
				jdbcTemplate.update(sbQuery.toString(), map);
			}


			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}


	@Override
	public List<AsignarMensajero> obtenerDatosCerrarRuta(Integer idUsuario) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");

			sbQuery.append(" \n");
			sbQuery.append("SELECT CR.idMensajero, CR.Mensajero, COUNT(idCliente) totalClientes, COUNT(CR.Zona) totalZonas, COUNT(CR.Ruta) totalRutas, \n");
			sbQuery.append("SUM(CASE WHEN CR.Entrega = 0 THEN 1 ELSe 0 END ) totalNoEntregado, SUM(CASE WHEN CR.Entrega = 1 THEN 1 ELSe 0 END ) totalEntregado \n");
			sbQuery.append("FROM ( \n");
			sbQuery.append("SELECT CR.idMensajero, CR.Mensajero, CR.idCliente, CASE WHEN Entrega = 'Realizado' THEN 1 ELSE 0 END Entrega, CR.Zona, CR.Ruta \n");
			sbQuery.append("FROM CerrarRuta CR \n");
			sbQuery.append("GROUP BY CR.idMensajero, CR.Mensajero, CR.idCliente, CR.EstadoRuta, CR.Zona, CR.Ruta, CR.Entrega ) CR \n");
			sbQuery.append("GROUP BY CR.idMensajero, CR.Mensajero \n");

			Map<String, Object> map = new HashMap<String, Object>();
			//map.put("idResponsable", idUsuario);

			List<AsignarMensajero> lista =  getJdbcTemplate().query(sbQuery.toString(), map, new BeanPropertyRowMapper<>(AsignarMensajero.class));

			return lista;

		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, List<TotalMensajero>> obtenerDatosGraficaCerrarRuta(Integer idUsuario) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("DECLARE @TABLA TABLE (Cant int, Monto real, Mensajero varchar(300), Cliente varchar(300), Prioridad varchar(300)) \n");
			sbQuery.append("insert into @TABLA SELECT CR.Cant, CR.Monto, CR.Mensajero, CR.Cliente, CR.Prioridad FROM CerrarRuta CR \n");

			sbQuery.append("SELECT 'Mensajero' Etiqueta, COALESCE(SUM(CR.Cant),0) Piezas, COALESCE(SUM(CR.Monto * CR.Cant),0) Monto, CR.Mensajero Tipo, 0 Orden \n");
			sbQuery.append("FROM @TABLA CR  \n");
			sbQuery.append("GROUP BY CR.Mensajero \n"); 
			sbQuery.append("UNION ALL  \n");
			sbQuery.append("SELECT 'Clientes' Etiqueta, COALESCE(SUM(CR.Cant),0) Piezas, COALESCE(SUM(CR.Monto * CR.Cant),0) Monto, CR.Cliente Tipo , 0 Orden \n");
			sbQuery.append("FROM @TABLA CR  \n");
			sbQuery.append("GROUP BY CR.Cliente  \n");
			sbQuery.append("UNION ALL  \n");
			sbQuery.append("SELECT Etiqueta, SUM(Pieza), SUM(Monto), Tipo, Orden FROM ( \n");
			sbQuery.append("SELECT 'Prioridad' Etiqueta, 0 Pieza, 0 Monto, 'P1' Tipo, 1 Orden \n");
			sbQuery.append("UNION ALL  \n");
			sbQuery.append("SELECT 'Prioridad' Etiqueta, 0 Pieza, 0 Monto, 'P2' Tipo, 2 Orden \n");
			sbQuery.append("UNION ALL  \n");
			sbQuery.append("SELECT 'Prioridad' Etiqueta, 0 Pieza, 0 Monto, 'P3' Tipo, 3 Orden \n");
			sbQuery.append("UNION ALL  \n");
			sbQuery.append("SELECT 'Prioridad' Etiqueta, COALESCE(SUM(CR.Cant),0) Piezas, COALESCE(SUM(CR.Monto * CR.Cant),0) Monto, CR.Prioridad COLLATE Modern_Spanish_CI_AS Tipo, \n"); 
			sbQuery.append("CASE WHEN CR.Prioridad = 'P1' THEN 1 WHEN CR.Prioridad = 'P2' THEN 2 ELSE 3 END Orden \n");
			sbQuery.append("FROM @TABLA CR  \n");
			sbQuery.append("GROUP BY CR.Prioridad \n"); 
			sbQuery.append(") Prioridad GROUP BY Etiqueta, Tipo, Orden \n");
			sbQuery.append("UNION ALL  \n");
			sbQuery.append("SELECT 'P1' Etiqueta, COALESCE(SUM(CR.Cant),0) Piezas, COALESCE(SUM(CR.Monto * CR.Cant),0) Monto, CR.Cliente Tipo, 0 Orden \n");
			sbQuery.append("FROM @TABLA CR  \n");
			sbQuery.append("WHERE CR.Prioridad = 'P1' \n"); 
			sbQuery.append("GROUP BY CR.Cliente  \n");
			sbQuery.append("UNION ALL  \n");
			sbQuery.append("SELECT 'P2' Etiqueta, COALESCE(SUM(CR.Cant),0) Piezas, COALESCE(SUM(CR.Monto * CR.Cant),0) Monto, CR.Cliente Tipo, 0 Orden \n");
			sbQuery.append("FROM @TABLA CR  \n");
			sbQuery.append("WHERE CR.Prioridad = 'P2' \n"); 
			sbQuery.append("GROUP BY CR.Cliente  \n");
			sbQuery.append("UNION ALL   \n");
			sbQuery.append("SELECT 'P3' Etiqueta, COALESCE(SUM(CR.Cant),0) Piezas, COALESCE(SUM(CR.Monto * CR.Cant),0) Monto, CR.Cliente Tipo, 0 Orden \n");
			sbQuery.append("FROM @TABLA CR  \n");
			sbQuery.append("WHERE CR.Prioridad = 'P3' \n"); 
			sbQuery.append("GROUP BY CR.Cliente  \n");
			sbQuery.append("ORDER BY Orden, Etiqueta, Monto DESC, Piezas DESC \n");
			sbQuery.append(" \n");

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idResponsable", idUsuario);
			Map<String, List<TotalMensajero>> mapReturn = new HashMap<String, List<TotalMensajero>>();
			jdbcTemplate.query(sbQuery.toString(), map, new RowMapper() {
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					TotalMensajero total = new TotalMensajero();
					total.setTotalPiezas(rs.getInt("Piezas"));
					total.setTotalMonto(rs.getDouble("Monto"));

					String etiqueta = rs.getString("Etiqueta");
					String tipo = rs.getString("Tipo");

					if (etiqueta.equals("Mensajero")) {
						total.setMensajero(tipo);
					} else if (etiqueta.equals("Clientes")) {
						total.setCliente(tipo);
					} else if (etiqueta.equals("Prioridad")) {
						total.setPrioridad(tipo);
					} else {
						total.setCliente(tipo);
					}

					if (mapReturn.get(etiqueta) != null) {
						mapReturn.get(etiqueta).add(total);
					} else {
						mapReturn.put(etiqueta, new ArrayList<TotalMensajero>());
						mapReturn.get(etiqueta).add(total);
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

	@SuppressWarnings("unchecked")
	@Override
	public List<AsignarMensajero> obtenerComparacionRuta(Integer idMensajero) throws ProquifaNetException {
		try {
			/*StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("SELECT 'Planeado' Etiqueta, CR.idCliente, CR.Cliente, CR.idMensajero, CR.Mensajero, CR.Folio, CR.Zona, CR.Monto * CR.Cant Monto, CR.Calle, \n");
			sbQuery.append("CR.Estado, CR.Pais, CR.Evento, CR.OrdenPlanificado Orden, CR.Prioridad, COALESCE(HyL.Altitud, CR.Altitud) Altitud, COALESCE(CR.Altitud,HyL.Longitud) Longitud, COALESCE(CR.Latitud,HyL.Latitud) Latitud, COALESCE(CR.Entrega,'Realizado') Entrega \n");
			sbQuery.append("FROM CerrarRuta CR \n");
			sbQuery.append("LEFT JOIN (SELECT * FROM HorarioyLugar WHERE idHorario IN (SELECT MAX(idHorario) idHorario \n");  
			sbQuery.append("FROM HorarioyLugar WHERE Tipo = 'Entrega' AND IdCliente IS NOT NULL GROUP BY idCliente )) HyL ON HyL.idCliente = CR.idCliente \n"); 
			sbQuery.append("WHERE CR.idMensajero = :idMensajero \n");

			sbQuery.append("UNION ALL \n");

			sbQuery.append("SELECT 'Ejecutado' Etiqueta, CR.idCliente, CR.Cliente, CR.idMensajero, CR.Mensajero, CR.Folio, CR.Zona, CR.Monto * CR.Cant Monto, CR.Calle, \n");
			sbQuery.append("CR.Estado, CR.Pais, CR.Evento, COALESCE(CR.OrdenEjecutado,0), CR.Prioridad, COALESCE(HyL.Altitud, CR.Altitud) Altitud, COALESCE(CR.Altitud,HyL.Longitud) Longitud, COALESCE(CR.Latitud,HyL.Latitud) Latitud, COALESCE(CR.Entrega,'Realizado') Entrega \n");
			sbQuery.append("FROM CerrarRuta CR \n");
			sbQuery.append("LEFT JOIN (SELECT * FROM HorarioyLugar WHERE idHorario IN (SELECT MAX(idHorario) idHorario \n");  
			sbQuery.append("FROM HorarioyLugar WHERE Tipo = 'Entrega' AND IdCliente IS NOT NULL GROUP BY idCliente )) HyL ON HyL.idCliente = CR.idCliente \n"); 
			sbQuery.append("WHERE CR.idMensajero = :idMensajero \n");
			sbQuery.append("ORDER BY Etiqueta DESC, idCliente, Altitud DESC \n");
			sbQuery.append(" \n");*/

			String query = "DECLARE @idMensajero VARCHAR(50) = :idMensajero\n" + 
					"	DECLARE @nombreMensajero VARCHAR(50) = (SELECT Nombre FROM Empleados WHERE Clave = @idMensajero)\n" + 
					"	DECLARE @idRuta VARCHAR(50) = (\n" + 
					"	SELECT TOP 1 Docto FROM Pendiente \n" + 
					"	WHERE year(FInicio) >= '2019' AND Responsable = (SELECT Usuario FROM Empleados WHERE Clave = @idMensajero) \n" + 
					"	AND Tipo = 'Ruta a Ejecutar'\n" + 
					"	AND  (SELECT PK_Ruta FROM Ruta WHERE Folio COLLATE Modern_Spanish_CI_AS = Docto COLLATE Modern_Spanish_CI_AS ) IS NULL\n" + 
					"	AND FFin IS NOT NULL \n" + 
					"	AND (SELECT COUNT(*) FROM (\n" + 
					"	SELECT idDP FROM RutaDP WHERE idruta = Docto AND FK02_PackingList IS NOT NULL\n" + 
					"	UNION\n" + 
					"	SELECT idES FROM RutaES WHERE idruta = Docto AND (SELECT PK_AsignarMensajero FROM AsignarMensajero WHERE Evento =  idES) IS NOT NULL\n" + 
					"	UNION\n" + 
					"	SELECT idPC FROM RutaPC WHERE idruta = Docto AND (SELECT PK_AsignarMensajero FROM AsignarMensajero WHERE Evento =  idPC) IS NOT NULL\n" + 
					"	UNION\n" + 
					"	SELECT idPR FROM RutaPR WHERE idruta = Docto AND (SELECT PK_AsignarMensajero FROM AsignarMensajero WHERE Evento =  idPR) IS NOT NULL\n" + 
					"	UNION\n" + 
					"	SELECT idRE FROM RutaRE WHERE idruta = Docto AND (SELECT PK_AsignarMensajero FROM AsignarMensajero WHERE Evento =  idRE) IS NOT NULL\n" + 
					"	UNION\n" + 
					"	SELECT idRM FROM RutaRM WHERE idruta = Docto AND (SELECT PK_AsignarMensajero FROM AsignarMensajero WHERE Evento =  idRM) IS NOT NULL) AS Tabla) <> 0\n" + 
					"	ORDER BY FInicio DESC\n" + 
					"	)\n" + 
					"	SELECT ruta.idCliente, cl.nombre, @idMensajero AS idMensajero, @nombreMensajero as Mensajero, ruta.idRuta as Folio, ruta.FK01_Direccion, ruta.ZonaMensajeria as Zona,\n" + 
					"	SUM(CASE WHEN (PE.TCambio = 0 or PE.TCambio is null) THEN \n" + 
					"		CASE WHEN PE.Moneda = 'Pesos' THEN ROUND(PP.Precio/MON.PDolar, 0)\n" + 
					"		WHEN PE.Moneda = 'Dolares' THEN ROUND(PP.Precio,0)\n" + 
					"		WHEN PE.Moneda = 'Euros' THEN ROUND(PP.Precio*MON.EDolar, 0) END\n" + 
					"	ELSE CASE WHEN PE.Moneda = 'Pesos' THEN ROUND(PP.Precio/PE.TCambio,0)\n" + 
					"		WHEN PE.Moneda = 'Dolares' THEN ROUND(PP.Precio,0)\n" + 
					"		WHEN PE.Moneda = 'Euros' THEN ROUND(PP.Precio*MON.EDolar, 0) END END) AS Monto,\n" + 
					"	 ruta.Calle, ruta.Estado, ruta.Pais, 'Entrega' as Evento, ruta.OrdenPlan, AM.Orden, HL.Altitud, HL.Longitud, HL.Latitud, ruta.Entrega, \n" + 
					"	(SELECT TOP 1 FechaHora FROM Recorridos FI WHERE Tipo = 'I' AND FI.FK01_Cliente = ruta.idCliente AND FI.FK02_Direccion = ruta.FK01_Direccion ORDER BY FI.FechaHora ASC) AS FInicio,\n" + 
					"	(SELECT TOP 1 FechaHora FROM Recorridos FF WHERE Tipo = 'F' AND FF.FK01_Cliente = ruta.idCliente AND FF.FK02_Direccion = ruta.FK01_Direccion ORDER BY FF.FechaHora DESC) AS FFin, ruta.FFin as FCierre\n" + 
					"	FROM RutaDP ruta \n" + 
					"	LEFT JOIN Clientes cl ON clave = ruta.idCliente\n" + 
					"	LEFT JOIN PPackingList PPL ON ruta.FK02_PackingList  = PPL.FK01_PackingList  AND PPL.Fk03_facturaElectronica  = (select PK_Factura from FacturaElectronica  FE1 where FE1.Folio collate SQL_Latin1_General_CP1_CI_AS  = RUTA.Factura collate SQL_Latin1_General_CP1_CI_AS )\n" + 
					"	LEFT JOIN EmbalarPedido EP ON EP.PK_EmbalarPedido = PPL.Fk02_EmbalarPedido\n" + 
					"	LEFT JOIN PPedidos PP ON PP.idPPedido = EP.FK01_PPedido\n" + 
					"	LEFT JOIN Pedidos PE ON PE.CPedido = PP.CPedido\n" + 
					"	LEFT JOIN Monedas AS MON ON CAST(MON.Fecha as date) = CAST(PE.FPedido as DATE)\n" + 
					"	LEFT JOIN HorarioyLugar AS HL ON ruta.FK01_Direccion = idHorario\n" + 
					"	LEFT JOIN AsignarMensajero AS AM ON (SELECT Folio FROM PackingList WHERE PK_PackingList = ruta.FK02_PackingList) = AM.Evento\n" + 
					"	WHERE ruta.idRuta = @idRuta AND ruta.FK02_PackingList IS NOT NULL GROUP BY ruta.idCliente, cl.nombre, ruta.idRuta, ruta.FK01_Direccion, ruta.ZonaMensajeria, ruta.Calle, ruta.Estado, ruta.pais, ruta.OrdenPlan, AM.Orden, HL.Altitud, HL.Longitud, HL.Latitud, ruta.entrega, ruta.FFin\n" + 
					"	UNION\n" + 
					"	SELECT ruta.idCliente, cl.nombre, @idMensajero AS idMensajero, @nombreMensajero as Mensajero, ruta.idRuta as Folio, ruta.FK01_Direccion, ruta.Zona, 0 as Monto, ruta.Calle, ruta.Estado, ruta.Pais, 'Entrega Especial' \n" + 
					"	as Evento, ruta.OrdenPlan, AM.Orden, HL.Altitud, HL.Longitud, HL.Latitud, ruta.Entrega, \n" + 
					"	(SELECT TOP 1 FechaHora FROM Recorridos FI WHERE Tipo = 'I' AND FI.FK01_Cliente = ruta.idCliente AND FI.FK02_Direccion = ruta.FK01_Direccion ORDER BY FI.FechaHora ASC) AS FInicio,\n" + 
					"	(SELECT TOP 1 FechaHora FROM Recorridos FF WHERE Tipo = 'F' AND FF.FK01_Cliente = ruta.idCliente AND FF.FK02_Direccion = ruta.FK01_Direccion ORDER BY FF.FechaHora DESC) AS FFin, ruta.FFin as FCierre \n" + 
					"	FROM RutaES ruta\n" + 
					"	LEFT JOIN Clientes cl ON clave = ruta.idCliente\n" + 
					"	LEFT JOIN HorarioyLugar AS HL ON ruta.FK01_Direccion = idHorario\n" + 
					"	LEFT JOIN AsignarMensajero AS AM ON ruta.idES = AM.Evento\n" + 
					"	WHERE ruta.idRuta = @idRuta GROUP BY ruta.idCliente, cl.nombre, ruta.idRuta, ruta.FK01_Direccion, ruta.Zona, ruta.Calle, ruta.Estado, ruta.pais, ruta.OrdenPlan, AM.Orden, HL.Altitud, HL.Longitud, HL.Latitud, ruta.entrega, ruta.FFin\n" + 
					"	UNION\n" + 
					"	SELECT ruta.idCliente, cl.nombre, @idMensajero AS idMensajero, @nombreMensajero as Mensajero, ruta.idRuta as Folio, ruta.FK01_Direccion, ruta.Zona, 0 as Monto, ruta.Calle, ruta.Estado, ruta.Pais, 'Cobro' as Evento,\n" + 
					"	ruta.OrdenPlan, AM.Orden, HL.Altitud, HL.Longitud, HL.Latitud, ruta.Entrega, \n" + 
					"	(SELECT TOP 1 FechaHora FROM Recorridos FI WHERE Tipo = 'I' AND FI.FK01_Cliente = ruta.idCliente AND FI.FK02_Direccion = ruta.FK01_Direccion ORDER BY FI.FechaHora ASC) AS FInicio,\n" + 
					"	(SELECT TOP 1 FechaHora FROM Recorridos FF WHERE Tipo = 'F' AND FF.FK01_Cliente = ruta.idCliente AND FF.FK02_Direccion = ruta.FK01_Direccion ORDER BY FF.FechaHora DESC) AS FFin, ruta.FFin as FCierre \n" + 
					"	FROM RutaPC ruta\n" + 
					"	LEFT JOIN Clientes cl ON clave = ruta.idCliente\n" + 
					"	LEFT JOIN HorarioyLugar AS HL ON ruta.FK01_Direccion = idHorario\n" + 
					"	LEFT JOIN AsignarMensajero AS AM ON ruta.idPC = AM.Evento\n" + 
					"	WHERE ruta.idRuta = @idRuta GROUP BY ruta.idCliente, cl.nombre, ruta.idRuta, ruta.FK01_Direccion, ruta.Zona, ruta.Calle, ruta.Estado, ruta.pais, ruta.OrdenPlan, AM.Orden, HL.Altitud, HL.Longitud, HL.Latitud, ruta.entrega, ruta.FFin\n" + 
					"	UNION\n" + 
					"	SELECT ruta.idCliente, cl.nombre, @idMensajero AS idMensajero, @nombreMensajero as Mensajero, ruta.idRuta as Folio, ruta.FK01_Direccion, ruta.Zona, 0 as Monto, ruta.Calle, ruta.Estado, ruta.Pais, 'Revision' as \n" + 
					"	Evento, ruta.OrdenPlan, AM.Orden, HL.Altitud, HL.Longitud, HL.Latitud, ruta.Entrega, \n" + 
					"	(SELECT TOP 1 FechaHora FROM Recorridos FI WHERE Tipo = 'I' AND FI.FK01_Cliente = ruta.idCliente AND FI.FK02_Direccion = ruta.FK01_Direccion ORDER BY FI.FechaHora ASC) AS FInicio,\n" + 
					"	(SELECT TOP 1 FechaHora FROM Recorridos FF WHERE Tipo = 'F' AND FF.FK01_Cliente = ruta.idCliente AND FF.FK02_Direccion = ruta.FK01_Direccion ORDER BY FF.FechaHora DESC) AS FFin, ruta.FFin as FCierre \n" + 
					"	FROM RutaPR ruta\n" + 
					"	LEFT JOIN Clientes cl ON clave = ruta.idCliente\n" + 
					"	LEFT JOIN HorarioyLugar AS HL ON ruta.FK01_Direccion = idHorario\n" + 
					"	LEFT JOIN AsignarMensajero AS AM ON ruta.idPR = AM.Evento\n" + 
					"	WHERE ruta.idRuta = @idRuta GROUP BY ruta.idCliente, cl.nombre, ruta.idRuta, ruta.FK01_Direccion, ruta.Zona, ruta.Calle, ruta.Estado, ruta.pais, ruta.OrdenPlan, AM.Orden, HL.Altitud, HL.Longitud, HL.Latitud, \n" + 
					"	ruta.entrega, ruta.FFin\n" + 
					"	UNION\n" + 
					"	SELECT ruta.idCliente, cl.nombre, @idMensajero AS idMensajero, @nombreMensajero as Mensajero, ruta.idRuta as Folio, ruta.FK01_Direccion, ruta.Zona, 0 as Monto, ruta.Calle, ruta.Estado, ruta.Pais, \n" + 
					"	'Recolecciom Especial' as Evento, ruta.OrdenPlan, AM.Orden, HL.Altitud, HL.Longitud, HL.Latitud, ruta.Entrega, \n" + 
					"	(SELECT TOP 1 FechaHora FROM Recorridos FI WHERE Tipo = 'I' AND FI.FK01_Cliente = ruta.idCliente AND FI.FK02_Direccion = ruta.FK01_Direccion ORDER BY FI.FechaHora ASC) AS FInicio,\n" + 
					"	(SELECT TOP 1 FechaHora FROM Recorridos FF WHERE Tipo = 'F' AND FF.FK01_Cliente = ruta.idCliente AND FF.FK02_Direccion = ruta.FK01_Direccion ORDER BY FF.FechaHora DESC) AS FFin, ruta.FFin as FCierre \n" + 
					"	FROM RutaRE ruta\n" + 
					"	LEFT JOIN Clientes cl ON clave = ruta.idCliente\n" + 
					"	LEFT JOIN HorarioyLugar AS HL ON ruta.FK01_Direccion = idHorario\n" + 
					"	LEFT JOIN AsignarMensajero AS AM ON ruta.idRE = AM.Evento\n" + 
					"	WHERE ruta.idRuta = @idRuta GROUP BY ruta.idCliente, cl.nombre, ruta.idRuta, ruta.FK01_Direccion, ruta.Zona, ruta.Calle, ruta.Estado, ruta.pais, ruta.OrdenPlan, AM.Orden, HL.Altitud, HL.Longitud, HL.Latitud, \n" + 
					"	ruta.entrega, ruta.FFin\n" + 
					"	UNION\n" + 
					"	SELECT ruta.idProveedor, pv.nombre, @idMensajero AS idMensajero, @nombreMensajero as Mensajero, ruta.idRuta as Folio, ruta.FK01_Direccion, ruta.Zona, 0 as Monto, ruta.Calle, ruta.Estado, ruta.Pais, \n" + 
					"	'Recoleccion de Material' as Evento, ruta.OrdenPlan, AM.Orden, HL.Altitud, HL.Longitud, HL.Latitud, ruta.Entrega, \n" + 
					"	(SELECT TOP 1 FechaHora FROM Recorridos FI WHERE Tipo = 'I' AND FI.FK01_Cliente = ruta.idProveedor AND FI.FK02_Direccion = ruta.FK01_Direccion ORDER BY FI.FechaHora ASC) AS FInicio,\n" + 
					"	(SELECT TOP 1 FechaHora FROM Recorridos FF WHERE Tipo = 'F' AND FF.FK01_Cliente = ruta.idProveedor AND FF.FK02_Direccion = ruta.FK01_Direccion ORDER BY FF.FechaHora DESC) AS FFin, ruta.FFin as FCierre \n" + 
					"	FROM RutaRM ruta\n" + 
					"	LEFT JOIN Proveedores pv ON clave = ruta.idProveedor\n" + 
					"	LEFT JOIN HorarioyLugar AS HL ON ruta.FK01_Direccion = idHorario\n" + 
					"	LEFT JOIN AsignarMensajero AS AM ON ruta.idRM = AM.Evento\n" + 
					"	WHERE ruta.idRuta = @idRuta GROUP BY ruta.idProveedor, pv.nombre, ruta.idRuta, ruta.FK01_Direccion, ruta.Zona, ruta.Calle, ruta.Estado, ruta.pais, ruta.OrdenPlan, AM.Orden, HL.Altitud, HL.Longitud, HL.Latitud,\n" + 
					"	ruta.entrega, ruta.FFin";

			log.info(query);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idMensajero", idMensajero);

			List<AsignarMensajero> lstAsignarMensajero = new ArrayList<AsignarMensajero>();
			lstAsignarMensajero= super.jdbcTemplate.query(query, map, new RowMapper(){
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException{
					AsignarMensajero asignarMensajero = new AsignarMensajero();
					try{
						asignarMensajero.setIdCliente(rs.getInt("idCliente"));
						asignarMensajero.setCliente(rs.getString("nombre"));
						asignarMensajero.setIdMensajero(rs.getInt("idMensajero"));
						asignarMensajero.setMensajero(rs.getString("Mensajero"));
						asignarMensajero.setFolio(rs.getString("Folio"));
						asignarMensajero.setIdHorario(rs.getInt("FK01_Direccion"));
						asignarMensajero.setZona(rs.getString("Zona"));
						asignarMensajero.setMonto(rs.getDouble("Monto"));
						asignarMensajero.setCalle(rs.getString("Calle"));
						asignarMensajero.setEstado(rs.getString("Estado"));
						asignarMensajero.setPais(rs.getString("Pais"));
						asignarMensajero.setEvento(rs.getString("Evento"));
						asignarMensajero.setOrdenReal(rs.getInt("OrdenPlan"));
						asignarMensajero.setOrden(rs.getInt("orden"));
						asignarMensajero.setAltitud(rs.getDouble("Altitud"));
						asignarMensajero.setLongitud(rs.getDouble("Longitud"));
						asignarMensajero.setLatitud(rs.getDouble("Latitud"));
						asignarMensajero.setEntrega(rs.getString("Entrega"));
						asignarMensajero.setfInicio(rs.getString("FInicio"));
						asignarMensajero.setfFin(rs.getString("FFin"));
						asignarMensajero.setfCierre(rs.getString("FCierre"));

						return asignarMensajero;
					}
					catch (Exception e) {
					}
					return asignarMensajero;
				}
			});

			return lstAsignarMensajero;
			//return getJdbcTemplate().query(query, map, new BeanPropertyRowMapper<>(AsignarMensajero.class));

		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}


	@Override
	public Integer guardarRuta(Ruta ruta) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("INSERT INTO Ruta(Fecha, Folio, Justificacion, Calificacion) VALUES(GETDATE(), :folio, :justificacion, :calificacion) \n");
			sbQuery.append(" \n");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("folio", ruta.getFolio());
			map.put("justificacion", ruta.getJustificacion());
			map.put("calificacion", ruta.getCalificacion());

			jdbcTemplate.update(sbQuery.toString(), map);

			//pendienteDAO.cerrarPendiente_angular(ruta.getFolio(), null, "Ruta a ejecutar");

			map = new HashMap<String, Object>();
			sbQuery = new StringBuilder();
			sbQuery.append("SELECT IDENT_CURRENT ('Ruta') \n");

			return super.jdbcTemplate.queryForObject(sbQuery.toString(), map, Integer.class);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean estadoAGenerar(PendientesMensajero pendiente, String mensajero) throws ProquifaNetException {
		try {
			String query = ""; 
			String tabla = "";
			String campo = "";
			System.out.print("Entre a estado," + pendiente);
			if(pendiente.getFolioEvento().contains("DP")) {
				System.out.println("Entre a IF  DP," + pendiente.getFolioEvento());
				System.out.println("vALOR DE rEALÑIOZADOtXT," +  pendiente.getRealizadoTxt());

				//Tiene nulo en FacturaORemision
				if(pendiente.getFacturaORemision() && pendiente.getRealizadoTxt().equals("No realizada")) {
					String gestorRuta = "";
					Long idUsuario = funcionDAO.getidFuncionXNombre("Gestor de ruta");
					if(idUsuario != 0) {
						gestorRuta = funcionDAO.getEmpleadoXIdFuncion(idUsuario);
					}
					if(!mensajero.equals("MensajeroGDL")) {
						pendienteDAO.guardarPendiente_angular(new Pendiente(null, "Asignar Mensajero", pendiente.getFolioDocumento(), new Date(), gestorRuta, null));
						pendienteDAO.borrarAsignarMensajero(pendiente.getFolioDocumento());
					}else {
						pendiente.setEstadoPendiente("Colectado");
						mensajeroDAO.ejecutarRutaMensajeroPL(pendiente);
					}
				}

				query = "DECLARE @responsable VARCHAR(100) = (SELECT TOP 1 usuario FROM Empleados WHERE nivel=6 AND Fase>0)" +
						"SELECT  ruta.FPor, ruta.Factura,  (select usuario from Empleados where clave = CL.cobrador) as Cobrador, ruta.Entrega, @responsable Responsable, f.Estado, f.CPago \n" + 
						"FROM rutadp ruta \n" + 
						"LEFT JOIN Clientes CL ON CL.Clave = ruta.idCliente \n" + 
						"LEFT JOIN Facturas f ON f.Factura = ruta.Factura AND f.FPor = ruta.Fpor \n" +
						"where estadoruta = 'ACerrar' AND idDP = '" + pendiente.getFolioEvento() + "'";
				//insert de registro

				//Map<String, Object> map1 = new HashMap<String, Object>();
				//map1.put("AceptaEyR_OriginalV",pendiente.getAceptaEyR());
				//map1.put("EntregaRevision_OriginalV", pendiente.getEntregaRevision());
				//String qry = "INSERT INTO IncidenciasProgramarCobro (AceptaEyR_Original, EntregaRevision_Original)VALUES (:AceptaEyR_OriginalV, :EntregaRevision_OriginalV)";
				//jdbcTemplate.update(qry, map1);
				//log.info("------------------------------------Se hizo un insert a IncidenciasProgramarCobro");
				//Map<String, Object> map2 = new HashMap<String, Object>();
				//qry="SELECT TOP 1 IdIncidenciasProgramaCobro FROM IncidenciasProgramarCobro ORDER BY IdIncidenciasProgramaCobro DESC";


				Map<String, Object> map = new HashMap<String, Object>();
				if(!pendiente.getFacturaORemision()) {
					jdbcTemplate.query(query, map, new RowMapper() {
						@Override
						public Object mapRow(ResultSet rs, int arg1) throws SQLException {
							try {
								log.info("------------------------------------Entra al if de getFacturaORemision--------------------------");
								log.info("------------------------------------Variable: "+pendiente.getFacturaORemision());
								System.out.print("Entre a querycillo," +  rs);
								String factura = rs.getString("Factura");
								String fpor = rs.getString("FPor");
								String cobrador =  rs.getString("Cobrador");
								String entrega = rs.getString("Entrega");
								String responsable = rs.getString("Responsable");
								String estadoFactura = rs.getString("Estado");
								String condicionPago = rs.getString("CPago");
								String gestorRuta = "";
								if (entrega.equals("No realizada")) {
									Long idUsuario = funcionDAO.getidFuncionXNombre("Gestor de ruta");
									if(idUsuario != 0) {
										gestorRuta = funcionDAO.getEmpleadoXIdFuncion(idUsuario);
									}
									if(!mensajero.equals("MensajeroGDL")) {
										Pendiente p = new Pendiente();
										p.setDocto(pendiente.getFolioDocumento());
										p.setResponsable(gestorRuta);
										p.setTipoPendiente("Asignar Mensajero");
										if(pendienteDAO.validarPendienteAsignar(p) == 0) {
											pendienteDAO.guardarPendiente_angular(new Pendiente(null, "Asignar Mensajero", pendiente.getFolioDocumento(), new Date(), gestorRuta, null));
											pendienteDAO.borrarAsignarMensajero(pendiente.getFolioDocumento());
										}
									} else {
										pendiente.setEstadoPendiente("Colectado");
										mensajeroDAO.ejecutarRutaMensajeroPL(pendiente);
									}
								}else if(!estadoFactura.equals("Cobrada") && !condicionPago.equals("PAGO CONTRA ENTREGA")) {
									boolean aceptaEYR = true;
									boolean entregaRevision = true;
									//String idinconsistenciasPC = "";
									//String qry="";
									//Integer id = 0;
									if(pendiente.getAceptaEyR()==null){
										aceptaEYR=false;
									}
									if(pendiente.getEntregaRevision()==null) {
										entregaRevision = false;
									}
									//if(pendiente.getAceptaEyR()) {
									if(aceptaEYR) {
										//if(pendiente.getEntregaRevision()) {
										if(entregaRevision) {
											Pendiente pendiente = new Pendiente(fpor, "Programar Cobro", factura, new Date(), cobrador, null);
											/*Map<String, Object> map1 = new HashMap<String, Object>();
											//Map<String, Object> map2 = new HashMap<String, Object>();
											qry = "SELECT TOP 1 IdIncidenciasProgramarCobro FROM IncidenciasProgramarCobro ORDER BY IdIncidenciasProgramarCobro DESC";
											idinconsistenciasPC = jdbcTemplate.query(qry,map1,new RowMapper(){
												@Override
												public Object mapRow(ResultSet rs, int arg1) throws SQLException{
													Integer id = 0;
													id = rs.getInt("IdIncidenciasProgramarCobro");
													return id;
												}
											}).toString();
											//idinconsistenciasPC = idinconsistenciasPC.replace('[',' ').replace(']',' ');
											idinconsistenciasPC = idinconsistenciasPC.trim();
											log.info("------------------------------------Id InconsistenciaPC a modificar: "+idinconsistenciasPC);
											map1.put("id",idinconsistenciasPC);
											//id = Integer.parseInt(idinconsistenciasPC);
											qry = "UPDATE IncidenciasProgramarCobro SET PendienteGenerado='Programar Cobro' WHERE IdIncidenciasProgramarCobro=:id";
											jdbcTemplate.update(qry, map1);
											 */
											if(pendienteDAO.validarPendiente(pendiente) == 0) {
												pendienteDAO.guardarPendiente_angular(pendiente);
											}
										}else {
											Pendiente pendiente = new Pendiente(fpor, "Programar Revisión", factura, new Date(), cobrador, null);
											/*Map<String, Object> map1 = new HashMap<String, Object>();
											qry = "SELECT TOP 1 IdIncidenciasProgramarCobro FROM IncidenciasProgramarCobro ORDER BY IdIncidenciasProgramarCobro DESC";
											idinconsistenciasPC = jdbcTemplate.query(qry,map1,new RowMapper(){
												@Override
												public Object mapRow(ResultSet rs, int arg1) throws SQLException{
													String inconsistenciasPC2 = new String();
													inconsistenciasPC2 = rs.getString("IdIncidenciasProgramarCobro");
													return inconsistenciasPC2;
												}
											}).toString();
											id = Integer.parseInt(idinconsistenciasPC);
											log.info("------------------------------------Id InconsistenciaPC a modificar: "+id);
											qry = "UPDATE IncidenciasProgramarCobro SET PendienteGenerado='Programar Cobro' WHERE IdIncidenciasProgramarCobro="+id;
											jdbcTemplate.update(qry, map1);
											*/
											if(pendienteDAO.validarPendiente(pendiente) == 0) {
												pendienteDAO.guardarPendiente_angular(pendiente);
											}
										}
 									}else {
										Pendiente pendiente = new Pendiente(fpor, "Programar Cobro", factura, new Date(), cobrador, null);
										/*Map<String, Object> map1 = new HashMap<String, Object>();
										qry = "SELECT TOP 1 IdIncidenciasProgramarCobro FROM IncidenciasProgramarCobro ORDER BY IdIncidenciasProgramarCobro DESC";
										idinconsistenciasPC = jdbcTemplate.query(qry,map1,new RowMapper(){
											@Override
											public Object mapRow(ResultSet rs, int arg1) throws SQLException{
												String inconsistenciasPC2 = new String();
												inconsistenciasPC2 = rs.getString("IdIncidenciasProgramarCobro");
												return inconsistenciasPC2;
											}
										}).toString();
										id = Integer.parseInt(idinconsistenciasPC);
										log.info("------------------------------------Id InconsistenciaPC a modificar: "+id);
										qry = "UPDATE IncidenciasProgramarCobro SET PendienteGenerado='Programar Cobro' WHERE IdIncidenciasProgramarCobro="+id;
										jdbcTemplate.update(qry, map1);
										*/
										if(pendienteDAO.validarPendiente(pendiente) == 0) {
											pendienteDAO.guardarPendiente_angular(pendiente);
										}
									}

								}

							} catch (Exception e) {
								e.printStackTrace();
								throw new SQLException();
							}
							return null;
						}
					});
				}
			}else if(pendiente.getFolioEvento().contains("PR")){
				query = "DECLARE @responsable VARCHAR(100) = (SELECT TOP 1 usuario FROM Empleados WHERE nivel=6 AND Fase>0) " +
						"SELECT  ruta.FPor, ruta.Factura,  (select usuario from Empleados where clave = CL.cobrador) as Cobrador, ruta.Entrega, @responsable Responsable, f.Estado \n" + 
						"FROM rutaPR ruta \n" + 
						"LEFT JOIN Clientes CL ON CL.Clave = ruta.idCliente \n" + 
						"LEFT JOIN Facturas f ON f.Factura = ruta.Factura \n" +
						"where estadoruta = 'ACerrar' AND idPR = '" + pendiente.getFolioEvento() + "'"; 
				Map<String, Object> map = new HashMap<String, Object>();
				jdbcTemplate.query(query, map, new RowMapper() {
					@Override
					public Object mapRow(ResultSet rs, int arg1) throws SQLException {
						try {
							String factura = rs.getString("Factura");
							String fpor = rs.getString("FPor");
							String cobrador =  rs.getString("Cobrador");
							String entrega = rs.getString("Entrega");
							String estadoFactura = rs.getString("Estado");

							if(!estadoFactura.equals("Cobrada")) {
								if (entrega.equals("No realizada")) {
									Long idUsuario = funcionDAO.getidFuncionXNombre("Gestor de ruta");
									Pendiente pendienteNuevo = new Pendiente();				
									if(idUsuario != 0) {
										pendienteNuevo.setResponsable(funcionDAO.getEmpleadoXIdFuncion(idUsuario));
									}
									pendienteNuevo.setDocto(pendiente.getFolioEvento());
									pendienteNuevo.setTipoPendiente("A concluir planeacion");
									pendiente.setPartida(null);
									pendienteDAO.guardarPendiente_angular(pendienteNuevo);
								}else {
									Pendiente pendiente = new Pendiente(fpor, "Programar Cobro", factura, new Date(), cobrador, null);
									if(pendienteDAO.validarPendiente(pendiente) == 0) {
										pendienteDAO.guardarPendiente_angular(pendiente);
									}
								}
							}

						} catch (Exception e) {
							e.printStackTrace();
							throw new SQLException();
						}
						return null;
					}
				});
			}else {
				if (pendiente.getRealizadoTxt().equals("No realizada")) {
					Long idUsuario = funcionDAO.getidFuncionXNombre("Gestor de ruta");
					Pendiente pendienteNuevo = new Pendiente();				
					if(idUsuario != 0) {
						pendienteNuevo.setResponsable(funcionDAO.getEmpleadoXIdFuncion(idUsuario));
					}
					pendienteNuevo.setDocto(pendiente.getFolioEvento());
					pendienteNuevo.setTipoPendiente("A concluir planeacion");
					pendiente.setPartida(null);
					pendienteDAO.guardarPendiente_angular(pendienteNuevo);
				}
			}

			if (pendiente.getFolioEvento().contains("DP")) {
				tabla = "RutaDP";
				campo = "idDP";
				if(!(mensajero.equals("MensajeroGDL") && pendiente.getRealizadoTxt().equals("No realizada")))
					cerrarEvento(pendiente.getFolioEvento(), tabla, campo);
			} else if (pendiente.getFolioEvento().contains("PR")) {
				tabla = "RutaPR";
				campo = "idPR";
				cerrarEvento(pendiente.getFolioEvento(), tabla, campo);
			}else if (pendiente.getFolioEvento().contains("ES")) {
				tabla = "RutaES";
				campo = "idES";
				cerrarEvento(pendiente.getFolioEvento(), tabla, campo);
			}  else if (pendiente.getFolioEvento().contains("RE")) {
				tabla = "RutaRE";
				campo = "idRE";
				cerrarEvento(pendiente.getIdRuta(), tabla, campo);
			}  else if (pendiente.getFolioEvento().contains("RM")) {
				tabla = "RutaRM";
				campo = "idRM";
				cerrarEvento(pendiente.getFolioEvento(), tabla, campo);
			}  else if (pendiente.getFolioEvento().contains("PC")) {
				tabla = "RutaPC";
				campo = "idPC";
				cerrarEvento(pendiente.getFolioEvento(), tabla, campo);
			}  

			log.info(query);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}

	}

	//@SuppressWarnings({ "unchecked", "rawtypes" })
	//@Override
	/*public boolean estadoAGenerar(Ruta ruta, String idRuta) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("DECLARE @responsable VARCHAR(100) = (SELECT TOP 1 usuario FROM Empleados WHERE nivel=6 AND Fase>0) \n");
			sbQuery.append(" \n");
			sbQuery.append("SELECT * FROM ( \n");
			sbQuery.append("SELECT CL.CPago, CL.EntregayRevision, RE.FK01_Evento Folio, RE.AK01_Ruta idRuta, COALESCE(F.Factura, RDP.Factura, RES.Factura, RPC.Factura, RPR.Factura, RRE.Factura) Factura, COALESCE(F.FPor, RDP.FPor, RES.FPor, RPC.FPor, RPR.FPor, RRE.FPor) Fpor, RDP.idDP, \n");
			sbQuery.append("CASE WHEN RE.FK01_Evento LIKE 'PC-%' THEN 'FCobro' \n");
			sbQuery.append("WHEN PRevision.Docto IS NULL THEN   \n");
			sbQuery.append("CASE WHEN CL.EntregayRevision = 1 THEN 'ICobro' ELSE 'IRevision' END \n"); 
			sbQuery.append("WHEN PRevision.Docto IS NOT NULL AND PRevision.FFin IS NULL THEN 'FRevision' \n");
			sbQuery.append("WHEN PRevision.FFin IS NOT NULL AND PCobro.Docto IS NULL THEN 'ICobro' END TipoPendiente, \n"); 
			sbQuery.append("COALESCE(RDP.Entrega,RES.Entrega,RPC.Entrega,RPR.Entrega,RRE.Entrega,'') Entrega, @responsable Responsable, (select usuario from Empleados where clave = CL.cobrador) as Cobrador \n");

			sbQuery.append("FROM Pendiente PEN \n");
			sbQuery.append("INNER JOIN Ruta_Evento RE ON RE.AK01_Ruta = PEN.Docto COLLATE Modern_Spanish_CI_AS \n");
			//sbQuery.append("INNER JOIN AsignarMensajero AM ON AM.Evento = RE.FK01_Evento COLLATE Modern_Spanish_CI_AS \n");
			sbQuery.append("LEFT JOIN PackingList PL ON PL.Folio = RE.FK01_Evento COLLATE Modern_Spanish_CI_AS \n");
			sbQuery.append("LEFT JOIN (SELECT * FROM RutaDP WHERE EstadoRuta = 'ACerrar' ) RDP ON RDP.idRuta = RE.AK01_Ruta COLLATE Modern_Spanish_CI_AS AND (RDP.idDP = RE.FK01_Evento OR RDP.FK02_PackingList = PL.PK_PackingList ) AND idDP = '" + idRuta + "' \n");
			sbQuery.append("LEFT JOIN (SELECT * FROM RutaES WHERE EstadoRuta = 'ACerrar' ) RES ON RES.idRuta = RE.AK01_Ruta COLLATE Modern_Spanish_CI_AS AND RES.idES = RE.FK01_Evento AND idES = '" + idRuta + "' \n");
			sbQuery.append("LEFT JOIN (SELECT * FROM RutaPC WHERE EstadoRuta = 'ACerrar' ) RPC ON RPC.idRuta = RE.AK01_Ruta COLLATE Modern_Spanish_CI_AS AND RPC.idPC = RE.FK01_Evento AND idPC = '" + idRuta + "' \n");
			sbQuery.append("LEFT JOIN (SELECT * FROM RutaPR WHERE EstadoRuta = 'ACerrar' ) RPR ON RPR.idRuta = RE.AK01_Ruta COLLATE Modern_Spanish_CI_AS AND RPR.idPR = RE.FK01_Evento AND idPR = '" + idRuta + "' \n");
			sbQuery.append("LEFT JOIN (SELECT * FROM RutaRE WHERE EstadoRuta = 'ACerrar' ) RRE ON RRE.idRuta = RE.AK01_Ruta COLLATE Modern_Spanish_CI_AS AND RRE.idRE = RE.FK01_Evento AND idRE = '" + idRuta + "' \n");
			sbQuery.append("LEFT JOIN (SELECT * FROM RutaRM WHERE EstadoRuta = 'ACerrar' ) RRM ON RRM.idRuta = RE.AK01_Ruta COLLATE Modern_Spanish_CI_AS AND RRM.idRM = RE.FK01_Evento AND idRM = '" + idRuta + "' \n");
			sbQuery.append("LEFT JOIN PPackingList PPL ON PPL.FK01_PackingList = PL.PK_PackingList \n");
			sbQuery.append("LEFT JOIN Factura_FElectronica FFE ON FFE.FK02_FacturaElectronica = PPL.FK03_FacturaElectronica \n");
			sbQuery.append("LEFT JOIN Facturas F ON F.idFactura = FK01_Factura \n");
			sbQuery.append("INNER JOIN Clientes CL ON CL.Clave = COALESCE(RDP.idCliente, RES.idCliente, RPC.idCliente, RPR.idCliente, RRE.idCliente) \n");
			sbQuery.append("LEFT JOIN PEndiente PRevision ON PRevision.Tipo = 'Programar Revisión' AND PRevision.Docto = COALESCE(F.idFactura, RDP.Factura, RES.Factura, RPC.Factura, RPR.Factura, RRE.Factura) \n"); 
			sbQuery.append("AND PRevision.Partida = COALESCE(F.FPor, RDP.FPor, RES.FPor, RPC.FPor, RPR.FPor, RRE.FPor)  \n");
			sbQuery.append("LEFT JOIN PEndiente PCobro ON PCobro.Tipo = 'Programar Cobro' AND PCobro.Docto = COALESCE(F.idFactura, RDP.Factura, RES.Factura, RPC.Factura, RPR.Factura, RRE.Factura) \n");
			sbQuery.append("AND PCobro.Partida = COALESCE(F.FPor, RDP.FPor, RES.FPor, RPC.FPor, RPR.FPor, RRE.FPor) AND PRevision.FFin IS NOT NULL \n");
			sbQuery.append("INNER JOIN EMpleados Resp ON Resp.Usuario = PEN.Responsable \n");
			sbQuery.append("WHERE PEN.Tipo = 'Ruta a ejecutar' AND PEN.FFin IS NULL AND Resp.Clave = :idResponsable AND PEN.Docto = :idRuta AND F.Estado = 'Por Cobrar' \n");
			sbQuery.append(" ) Ruta \n");
			sbQuery.append("GROUP BY CPago, EntregayRevision, Folio, idRuta, Factura, FPor, idRuta, Factura, idDP, TipoPendiente, Entrega, Responsable, Cobrador \n");

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idResponsable", ruta.getIdResponsable());
			map.put("idRuta", ruta.getFolio());

			log.info(sbQuery.toString());

			jdbcTemplate.query(sbQuery.toString(), map, new RowMapper() {

				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					String tipoPendiente = rs.getString("TipoPendiente");
					String factura = rs.getString("Factura");
					String fpor = rs.getString("Fpor");
					String folio = rs.getString("Folio");
					String idDP = rs.getString("idDP");
					try {
						//Long idFuncion = funcionDAO.getidFuncionXNombre("Gestor de ruta");
						//String usuario = funcionDAO.getEmpleadoXIdFuncion(idFuncion);
						String usuario =  rs.getString("Cobrador");
						String entrega = rs.getString("Entrega");

						if (entrega.equals("No realizada")) {
							if (!folio.contains("PL")) {
								Long idUsuario = funcionDAO.getidFuncionXNombre("Gestor de ruta");
								Pendiente pendiente = new Pendiente();				
								if(idUsuario != 0) {
									pendiente.setResponsable(funcionDAO.getEmpleadoXIdFuncion(idUsuario));
								}
								pendiente.setDocto(folio);
								pendiente.setTipoPendiente("A concluir planeacion");

								pendiente.setPartida(null);
								pendienteDAO.guardarPendiente_angular(pendiente);
							} else {
								String responsable = rs.getString("Responsable");
								pendienteDAO.guardarPendiente_angular(new Pendiente(null, "Producto no entregado", idDP, new Date(), responsable, null));
							}
							cerrarEvento(folio, "RutaDP", "idDP");
						}
						else {
							switch (tipoPendiente) {
							case "IRevision":{
								pendienteDAO.guardarPendiente_angular(new Pendiente(fpor, "Programar Revisión", factura, new Date(), usuario, null));
								break;
							}
							case "ICobro":{
								pendienteDAO.guardarPendiente_angular(new Pendiente(fpor, "Programar Cobro", factura, new Date(), usuario, null));
								break;
							}
							case "FRevision":{
								pendienteDAO.cerrarPendiente_angular(factura, fpor, "Programar Revisión");
								pendienteDAO.guardarPendiente_angular(new Pendiente(fpor, "Programar Cobro", factura, new Date(), usuario, null));
								break;
							}
							case "FCobro":{
								pendienteDAO.cerrarPendiente_angular(factura, fpor, "Programar Cobro");
								break;
							}

							default:
								break;
							}

							String tabla = "";
							String campo = "";
							if (folio.contains("PR")) {
								tabla = "RutaPR";
								campo = "idPR";
							}  else if (folio.contains("ES")) {
								tabla = "RutaES";
								campo = "idES";
							}  else if (folio.contains("RE")) {
								tabla = "RutaRE";
								campo = "idRE";
							}  else if (folio.contains("RM")) {
								tabla = "RutaRM";
								campo = "idRM";
							}  else if (folio.contains("PC")) {
								tabla = "RutaPC";
								campo = "idPC";
							}  else if (folio.contains("DP")) {
								tabla = "RutaDP";
								campo = "idDP";
							} 
							if (folio.contains("PL")) {
								tabla = "RutaDP";
								campo = "idDP";
								cerrarEvento(idDP, tabla, campo);
							} else {
								cerrarEvento(folio, tabla, campo);
							}

						}
					} catch (ProquifaNetException e) {
						e.printStackTrace();
						throw new SQLException();
					}
					return null;
				}
			} );

			//pendienteDAO.cerrarPendiente_angular(ruta.getFolio(), null, "Ruta a ejecutar");

			return true;

		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}*/

	public boolean cerrarEvento(String folio, String tabla, String campo) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("UPDATE ").append(tabla).append(" SET EstadoRuta = 'Cerrada' WHERE ").append(campo).append(" = :folio");
			sbQuery.append(" \n");

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("folio", folio);

			super.jdbcTemplate.update(sbQuery.toString(), map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}


	@Override
	public Boolean actualizarRutaDP(String id, String tipo, String folio ) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String tipoPendiente = id.substring(0, 2);
			if(tipo.equalsIgnoreCase("FE")) {
				super.jdbcTemplate.update("UPDATE RUTADP SET FirmaElectronica  = '" + folio + "' WHERE idDP = '" + id + "'", map);
				return true;
			}else if(tipo.equalsIgnoreCase("RT")){
				switch(tipoPendiente) {
				case "DP":
					super.jdbcTemplate.update("UPDATE rutaDP SET FolioDoctos3 = '" + folio + "' WHERE idDP = '" + id + "'", map);
					break;
				case "PR":
					super.jdbcTemplate.update("UPDATE rutaPR SET FolioDoctos3 = '" + folio + "' WHERE idPR = '" + id + "'", map);
					break;
				case "PC":
					super.jdbcTemplate.update("UPDATE rutaPC SET FolioDoctos = '" + folio + "' WHERE idPC = '" + id + "'", map);
					break;
				case "RM":
					super.jdbcTemplate.update("UPDATE rutaRM SET FolioDoctos = '" + folio + "' WHERE idRM = '" + id + "'", map);
					break;
				case "ES":
					super.jdbcTemplate.update("UPDATE rutaES SET FolioDoctos = '" + folio + "' WHERE idES = '" + id + "'", map);
					break;
				case "RE":
					super.jdbcTemplate.update("UPDATE rutaRE SET FolioDoctos = '" + folio + "' WHERE idRE = '" + id + "'", map);
					break;
				}
				return true;
			}

			return false;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}

	}
	
	@Override
	public List<String[]> reporteAsingarMensajero(String idRuta) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("SELECT ROW_NUMBER() OVER(ORDER BY PEN.Responsable ASC) AS #, PEN.Responsable Mensajero, PEN.FInicio Fecha, COALESCE(CL.Nombre,PRORM.Nombre) Cliente, COALESCE(HyL.Altitud, '') Altitud, COALESCE(HyL.Longitud, '') Longitud, COALESCE(HyL.Latitud, '') Latitud, \n");
			sbQuery.append("HyL.ZonaMensajeria, HyL.Ruta, HyL.Calle, HyL.Municipio, HyL.CP, HyL.Estado, HyL.Pais, COALESCE(RES.idES, RPC.idPC, RPR.idPR, RRE.idRE, RRM.idRM, PL.Folio, '') Folio \n");
			sbQuery.append("FROM Pendiente PEN \n");
			sbQuery.append("LEFT JOIN (SELECT * FROM RutaDP  ) DP ON DP.idRuta = PEN.Docto \n");
			sbQuery.append("LEFT JOIN (SELECT * FROM RutaES  ) RES ON RES.idRuta = PEN.Docto \n");
			sbQuery.append("LEFT JOIN (SELECT * FROM RutaPC ) RPC ON RPC.idRuta = PEN.Docto \n");
			sbQuery.append("LEFT JOIN (SELECT * FROM RutaPR  ) RPR ON RPR.idRuta = PEN.Docto \n");
			sbQuery.append("LEFT JOIN (SELECT * FROM RutaRE ) RRE ON RRE.idRuta = PEN.Docto \n");
			sbQuery.append("LEFT JOIN (SELECT * FROM RutaRM  ) RRM ON RRM.idRuta = PEN.Docto \n");
			sbQuery.append("LEFT JOIN Proveedores PRORM ON PRORM.Clave = RRM.idProveedor \n");
			sbQuery.append("LEFT JOIN HorarioyLugar HyL ON HyL.idHorario = COALESCE(DP.FK01_Direccion,RES.FK01_Direccion,RPC.FK01_Direccion,RPR.FK01_Direccion,RRE.FK01_Direccion,RRM.FK01_Direccion) \n");
			sbQuery.append("LEFT JOIN Clientes CL ON CL.Clave = COALESCE(DP.idCliente,RES.idCliente, RPC.idCliente, RPR.idCliente, RRE.idCliente) \n");
			sbQuery.append("LEFT JOIN PackingList PL ON PL.PK_PackingList = DP.FK02_PackingList \n");
			sbQuery.append("WHERE PEN.Docto IN(").append(idRuta).append(") AND PEN.Tipo = 'Ruta a ejecutar' \n");
			sbQuery.append("GROUP BY PEN.Responsable, PEN.FInicio, CL.Nombre, HyL.Altitud, HyL.Longitud, HyL.Latitud, PRORM.Nombre, \n");
			sbQuery.append("HyL.ZonaMensajeria, HyL.Ruta, HyL.Ruta, HyL.Calle, HyL.Municipio, HyL.CP, HyL.Estado, HyL.Pais, RES.idES, RPC.idPC, RPR.idPR, RRE.idRE, RRM.idRM, PL.Folio \n");
			sbQuery.append("ORDER BY PEN.Responsable \n");
			sbQuery.append(" \n");
			
			Map<String, Object> map = new HashMap<String, Object>();
			return super.jdbcTemplate.query(sbQuery.toString(), map, new RowMapper<String[]>(){
				@Override
				public String[] mapRow(ResultSet rs, int arg1) throws SQLException{
					String[] arreglo = new String [] { 
							rs.getString("#"),
							rs.getString("Mensajero"),
							rs.getString("Fecha"),
							rs.getString("Cliente"),
							rs.getString("Folio"),
							rs.getString("Calle"),
							rs.getString("CP"),
							rs.getString("Municipio"),
							rs.getString("Altitud"),
							rs.getString("Longitud"),
							rs.getString("Latitud"),
					};
					return arreglo;
				}
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
