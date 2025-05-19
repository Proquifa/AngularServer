/**
 * 
 */
package com.proquifa.net.persistencia.compras.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.WordUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.compras.rechazos.Rechazos;
import com.proquifa.net.modelo.comun.Contacto;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.despachos.trabajarRuta.TrPackingList;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.compras.RechazosDAO;

/**
 * @author ymendez
 *
 */
@Repository
public class RechazosDAOImpl extends DataBaseDAO implements RechazosDAO {

	@Override
	public List<Rechazos> obtenerDocumentacionFaltante(String responsable) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("SELECT RE.idProveedor, RE.Proveedor, SUM(totalProducto) totalProducto, COUNT(RE.Compra) totalOC, MIN(RE.FechaInspeccion) FechaInspeccion, MIN(RE.FechaInspeccionFormato) FechaInspeccionFormato FROM ( \n"); 
			sbQuery.append("SELECT RE.idProveedor, RE.Proveedor, RE.Compra, COUNT(RE.idProducto) totalProducto, MIN(RE.FechaInspeccion) FechaInspeccion, MIN(RE.FechaInspeccion) FechaInspeccionFormato  \n");
			sbQuery.append("FROM (  \n");
			sbQuery.append("SELECT PROV.Clave idProveedor, PROV.Nombre Proveedor, PC.Compra, PC.FK01_Producto idProducto, MIN(CAST(PE.FInicio as DATE)) FechaInspeccion \n"); 
			sbQuery.append("FROM Pendiente PE  \n");
			sbQuery.append("INNER JOIN PCompras PC ON PC.idPCompra = PE.Docto \n"); 
			sbQuery.append("INNER JOIN Productos PROD ON PROD.idProducto = PC.FK01_Producto \n"); 
			sbQuery.append("INNER JOIN Proveedores PROV ON PROV.Clave = PROD.Proveedor \n"); 
			sbQuery.append("LEFT JOIN InspeccionOC IOC ON IOC.idPcompra = PC.idPCompra \n"); 
			sbQuery.append("WHERE PE.Tipo = 'Rechazo Por Documentacion'  \n");
			sbQuery.append("AND PE.FFIn IS NULL AND PE.Responsable = :responsable \n");
			sbQuery.append("GROUP BY PROV.Nombre, PC.Compra, PC.FK01_Producto, PROV.Clave, IOC.Lote ) RE \n"); 
			sbQuery.append("GROUP BY RE.idProveedor, RE.Proveedor, RE.Compra ) RE  \n");
			sbQuery.append("GROUP BY RE.idProveedor, RE.Proveedor \n");

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("responsable", responsable);

			return super.jdbcTemplate.query(sbQuery.toString(), map, new BeanPropertyRowMapper<>(Rechazos.class));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}

	private void viewRechazoDocumentacion(StringBuilder sbQuery) throws ProquifaNetException {
		try {
			sbQuery.append("DECLARE @TABLA TABLE (Proveedor varchar(500), totalCompra int, totalProducto int) \n");
			sbQuery.append("INSERT INTO @TABLA \n");
			sbQuery.append("SELECT RE.Nombre, SUM(RE.TotalCompra) TotalCompra, SUM(RE.TotalPRoductos) TotalPRoductos \n");
			sbQuery.append("FROM ( \n");
			sbQuery.append("SELECT RE.Nombre, COUNT(RE.Compra) TotalCompra, SUM(RE.TotalProductos) TotalProductos \n"); 
			sbQuery.append("FROM (  \n");
			sbQuery.append("SELECT RE.Nombre, RE.Compra, COUNT(RE.Codigo) TotalProductos \n"); 
			sbQuery.append("FROM (  \n");
			sbQuery.append("SELECT PROV.Nombre, PC.Compra,PROD.Codigo \n"); 
			sbQuery.append("FROM Pendiente PE  \n");
			sbQuery.append("INNER JOIN PCompras PC ON PC.idPCompra = PE.Docto \n"); 
			sbQuery.append("INNER JOIN Productos PROD ON PROD.idProducto = PC.FK01_Producto \n"); 
			sbQuery.append("INNER JOIN Proveedores PROV ON PROV.Clave = PROD.Proveedor  \n");
			sbQuery.append("LEFT JOIN InspeccionOC IOC ON IOC.idPcompra = PC.idPCompra  \n");
			sbQuery.append("WHERE PE.Tipo = 'Rechazo Por Documentacion'  \n");
			sbQuery.append("AND PE.FFIn IS NULL AND PE.Responsable = :responsable \n");
			sbQuery.append("GROUP BY PROV.Nombre, PC.Compra, PROD.Codigo, IOC.Lote ) RE  \n");
			sbQuery.append("GROUP BY RE.Nombre, Re.Compra ) RE  \n");
			sbQuery.append("GROUP BY RE.Nombre ) RE \n");
			sbQuery.append("GROUP BY RE.Nombre \n");

		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}

	@Override
	public Map<String, Object> obtenerGraficaDocumentacionFaltante(String responsable) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			viewRechazoDocumentacion(sbQuery);
			sbQuery.append("SELECT 'Proveedor' Etiqueta, RE.Proveedor, RE.totalCompra totalOC, RE.totalProducto, 1 TotalProveedores FROM @TABLA RE ORDER BY RE.totalProducto DESC \n");

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("responsable", responsable);

			Map<String, Object> mapReturn = new HashMap<String, Object>();
			List<Rechazos> lstGrafica = super.jdbcTemplate.query(sbQuery.toString(), map, new BeanPropertyRowMapper<>(Rechazos.class));
			mapReturn.put("grafica", lstGrafica);

			sbQuery = new StringBuilder("  \n");
			viewRechazoDocumentacion(sbQuery);
			sbQuery.append("SELECT 'Total' Etiqueta, '' proveedor, COALESCE(SUM(RE.totalCompra),0) totalOC, COALESCE(SUM(RE.totalProducto),0) totalProducto, COUNT(RE.Proveedor) TotalProveedores FROM @TABLA RE \n");
			sbQuery.append(" \n");
			Rechazos totalGrafica = super.jdbcTemplate.queryForObject(sbQuery.toString(), map, new BeanPropertyRowMapper<>(Rechazos.class));

			mapReturn.put("totales", totalGrafica);

			return mapReturn;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}


	@Override
	public List<Contacto> obtenerProveedoresPorProveedor(Integer idProveedor) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("SELECT idContacto, Titulo, Contacto nombre, Puesto, Depto departamento, Tel1 telefono, Tel2 telefonoN, Fax, eMail, Extension1, Extension2 \n");
			sbQuery.append("FROM Contactos CON WHERE CON.FK01_Proveedor = :idProveedor \n");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idProveedor", idProveedor);

			return super.jdbcTemplate.query(sbQuery.toString(), map, new BeanPropertyRowMapper<>(Contacto.class));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}


	@Override
	public List<Rechazos> documentoFaltantePorProveedor(Integer idProveedor, String responsable) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("SELECT PC.Codigo + '-' + IOC.Lote identificador, PC.Codigo, IOC.Lote, PROD.Concepto, PC.Compra, COALESCE(IOC.Inspector,'') Inspector, PP.CPedido, PROD.FK02_Fabricante idFabrica, \n");
			sbQuery.append("CAST(IOC.Fecha as DATE) Fecha, PROD.Tipo, CAST(PP.FPEntrega as DATE) FPEntrega, COALESCE(DATEDIFF(day, GETDATE(), PP.FPEntrega),0) AS DRE \n");
			sbQuery.append("FROM Pendiente PE \n");
			sbQuery.append("INNER JOIN PCompras PC ON PC.idPCompra = PE.Docto \n");
			sbQuery.append("INNER JOIN PPEdidos PP ON PP.idPPedido = FK03_PPedido \n");
			sbQuery.append("LEFT JOIN InspeccionOC IOC ON IOC.idPcompra = PC.idPCompra \n");
			sbQuery.append("INNER JOIN Productos PROD ON PROD.idProducto = PC.FK01_Producto \n");
			sbQuery.append("INNER JOIN Proveedores PROV ON PROV.Clave = PROD.Proveedor \n");
			sbQuery.append("WHERE PE.Tipo = 'Rechazo Por Documentacion' \n");
			sbQuery.append("AND PROV.Clave = :proveedor \n");
			sbQuery.append("AND PE.FFin IS NULL \n");
			sbQuery.append("AND PE.Responsable = :responsable \n");
			sbQuery.append("ORDER BY Codigo, DRE \n");
			sbQuery.append(" \n");

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("proveedor", idProveedor);
			map.put("responsable", responsable);

			Map<String, Rechazos> mapReturn = new HashMap<String, Rechazos>();
			List<Rechazos> lstReturn = new ArrayList<Rechazos>();
			super.jdbcTemplate.query(sbQuery.toString(), map, new RowMapper<Object>() {

				@Override
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					Rechazos rechazo = new Rechazos();

					rechazo.setCodigo(rs.getString("Codigo"));
					rechazo.setLote(rs.getString("Lote"));
					rechazo.setConcepto(rs.getString("Concepto"));
					rechazo.setCompra(rs.getString("Compra"));
					rechazo.setInspector(rs.getString("Inspector"));
					rechazo.setFechaInspeccion(rs.getTimestamp("Fecha"));
					rechazo.setFechaInspeccionFormato(rs.getString("Fecha"));
					rechazo.setTipo(rs.getString("Tipo"));
					rechazo.setFee(rs.getTimestamp("FPEntrega"));
					rechazo.setFeeFormato(rs.getString("FPEntrega"));
					rechazo.setDre(rs.getInt("DRE"));
					rechazo.setIdFabrica(rs.getInt("idFabrica"));
					rechazo.setCpedido(rs.getString("CPedido"));
					rechazo.setIdentificador(rs.getString("identificador"));

					String key = rechazo.getCodigo() + "-" + rechazo.getLote();

					if (mapReturn.get(key) != null) {
						mapReturn.get(key).getLstRechazos().add(rechazo);
					} else {
						lstReturn.add(rechazo);
						mapReturn.put(key, rechazo);
						mapReturn.get(key).setLstRechazos(new ArrayList<Rechazos>());
					}

					return null;
				}
			});

			return lstReturn;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}


	@Override
	public boolean actualizarEstadoAInspeccion(String codigo, String lote, String hoja) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("DECLARE @TABLA TABLE (idPPedido int, idPCompra int, Folio int, idProducto int) \n");
			sbQuery.append("INSERT INTO @TABLA \n");
			sbQuery.append("SELECT PP.idPPedido, PC.idPCompra, PE.Folio, PC.FK01_Producto \n");
			sbQuery.append("FROM Pendiente PE \n");
			sbQuery.append("INNER JOIN PCompras PC ON PC.idPCompra = PE.Docto \n");
			sbQuery.append("INNER JOIN PPEdidos PP ON PP.idPPedido = FK03_PPedido \n");
			sbQuery.append("LEFT JOIN InspeccionOC IOC ON IOC.idPcompra = PC.idPCompra \n");
			sbQuery.append("WHERE PE.Tipo = 'Rechazo Por Documentacion' \n");
			sbQuery.append("AND PC.Codigo = :codigo AND IOC.Lote = :lote \n");

			sbQuery.append("SELECT STUFF( ( \n");
			sbQuery.append("SELECT ',' + CAST(PP.idPPedido as VARCHAR(MAX)) FROM @TABLA PP \n");
			sbQuery.append("FOR XML PATH ('')), 1, 1, '') pedido,  \n");
			sbQuery.append("STUFF( ( \n");
			sbQuery.append("SELECT ',' + CAST(PC.idPCompra as VARCHAR(MAX)) FROM @TABLA Pc \n");
			sbQuery.append("FOR XML PATH ('')), 1, 1, '') compra, \n");
			sbQuery.append("STUFF( ( \n");
			sbQuery.append("SELECT ',' + CAST(PE.Folio as VARCHAR(MAX)) FROM @TABLA PE \n");
			sbQuery.append("FOR XML PATH ('')), 1, 1, '') pendiente, \n");
			sbQuery.append("STUFF( ( \n");
			sbQuery.append("SELECT ',' + CAST(PROD.idProducto as VARCHAR(MAX)) FROM @TABLA PROD \n");
			sbQuery.append("FOR XML PATH ('')), 1, 1, '') codigo \n");
			sbQuery.append(" \n");

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("codigo", codigo);
			map.put("lote", lote);

			Rechazos rechazo = jdbcTemplate.queryForObject(sbQuery.toString(), map, new BeanPropertyRowMapper<>(Rechazos.class));

			sbQuery = new StringBuilder();
			sbQuery.append("UPDATE PPedidos SET Estado = 'En inspección' WHERE idPPedido IN (").append(rechazo.getPedido()).append(") \n");
			sbQuery.append("AND idPPedido NOT IN (SELECT PP.idPPEdido FROM Pendiente PEN \n");
			sbQuery.append("INNER JOIN PCompras PC ON PC.idPCompra = PEN.Docto \n");
			sbQuery.append("INNER JOIN PPEdidos PP ON PP.idPPedido = PC.FK03_PPedido \n");
			sbQuery.append("WHERE PEN.Tipo = 'Rechazo Por Inspeccion' AND PEN.FFin IS NULL 	AND PEN.Docto IN (").append(rechazo.getCompra()).append(")) \n");
			sbQuery.append("UPDATE PCompras SET Estado = 'En inspección' WHERE idPcompra IN (").append(rechazo.getCompra()).append(") \n");
			sbQuery.append("AND idPcompra NOT IN (SELECT Docto FROM Pendiente WHERE Tipo = 'Rechazo Por Inspeccion' AND FFin IS NULL AND Docto IN (").append(rechazo.getCompra()).append(")) \n");
			sbQuery.append("UPDATE Pendiente SET FFIN = GETDATE() WHERE Folio IN (").append(rechazo.getPendiente()).append(") \n");
			sbQuery.append("UPDATE Productos SET Documentacion = :documentacion WHERE idProducto IN(").append(rechazo.getCodigo()).append(")  \n");
			if (hoja.equals("S"))
				sbQuery.append("UPDATE Productos SET SDS = :sds WHERE idProducto IN(").append(rechazo.getCodigo()).append(")  \n");
			sbQuery.append("UPDATE InspeccionOC SET Apartado = NULL WHERE idPcompra IN (").append(rechazo.getCompra()).append(") \n");

			map.put("documentacion", codigo + "-" + lote);
			map.put("sds", codigo);

			jdbcTemplate.update(sbQuery.toString(), map);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}

	@Override
	public List<Rechazos> obtenerPiezasRechazadas(Rechazos datos) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append(" \n");
			sbQuery.append("SELECT RE.idProveedor, RE.Proveedor, SUM(totalProducto) totalProducto, COUNT(RE.Compra) totalOC, MIN(RE.FechaInspeccion) FechaInspeccion, MIN(RE.FechaInspeccionFormato) FechaInspeccionFormato FROM ( \n");  
			sbQuery.append("SELECT RE.idProveedor, RE.Proveedor, RE.Compra, COUNT(RE.idProducto) totalProducto, MIN(RE.FechaInspeccion) FechaInspeccion, MIN(RE.FechaInspeccion) FechaInspeccionFormato   \n");
			sbQuery.append("FROM (   \n");
			sbQuery.append("SELECT PROV.Clave idProveedor, PROV.Nombre Proveedor, PC.Compra, PC.FK01_Producto idProducto, MIN(CAST(PE.FInicio as DATE)) FechaInspeccion \n");  
			sbQuery.append("FROM Pendiente PE   \n");
			sbQuery.append("INNER JOIN PCompras PC ON PC.idPCompra = PE.Docto \n");  
			sbQuery.append("INNER JOIN Productos PROD ON PROD.idProducto = PC.FK01_Producto \n");  
			sbQuery.append("INNER JOIN Proveedores PROV ON PROV.Clave = PROD.Proveedor   \n");
			sbQuery.append("LEFT JOIN InspeccionOC IOC ON IOC.idPcompra = PC.idPCompra   \n");
			if (datos.getConcepto().equalsIgnoreCase("cuarentena")) {
				sbQuery.append("WHERE PE.Tipo = 'Rechazo por Inspeccion'   \n");
			} else if (datos.getConcepto().equalsIgnoreCase("reclamo")) {
				sbQuery.append("WHERE PE.Tipo = 'Producto a reclamo'  \n");
			}
			sbQuery.append("AND PE.FFIn IS NULL  AND PE.Responsable = :idRespondable\n");
			sbQuery.append("GROUP BY PROV.Nombre, PC.Compra, PC.FK01_Producto, PROV.Clave, IOC.Lote ) RE \n");  
			sbQuery.append("GROUP BY RE.idProveedor, RE.Proveedor, RE.Compra ) RE   \n");
			sbQuery.append("GROUP BY RE.idProveedor, RE.Proveedor  \n");

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idRespondable", datos.getInspector());
			return super.jdbcTemplate.query(sbQuery.toString(), map, new BeanPropertyRowMapper<>(Rechazos.class));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}

	private void viewRechazoInspeccion(StringBuilder sbQuery, String tipo) throws ProquifaNetException {
		try {
			sbQuery.append("DECLARE @TABLA TABLE (Proveedor varchar(500), totalCompra int, totalProducto int) \n");
			sbQuery.append("INSERT INTO @TABLA \n");
			sbQuery.append("SELECT RE.Nombre, SUM(RE.TotalCompra) TotalCompra, SUM(RE.TotalPRoductos) TotalPRoductos \n");
			sbQuery.append("FROM ( \n");
			sbQuery.append("SELECT RE.Nombre, COUNT(RE.Compra) TotalCompra, SUM(RE.TotalProductos) TotalProductos \n"); 
			sbQuery.append("FROM (  \n");
			sbQuery.append("SELECT RE.Nombre, RE.Compra, COUNT(RE.Codigo) TotalProductos \n"); 
			sbQuery.append("FROM (  \n");
			sbQuery.append("SELECT PROV.Nombre, PC.Compra,PROD.Codigo \n"); 
			sbQuery.append("FROM Pendiente PE  \n");
			sbQuery.append("INNER JOIN PCompras PC ON PC.idPCompra = PE.Docto \n"); 
			sbQuery.append("INNER JOIN Productos PROD ON PROD.idProducto = PC.FK01_Producto \n"); 
			sbQuery.append("INNER JOIN Proveedores PROV ON PROV.Clave = PROD.Proveedor  \n");
			sbQuery.append("LEFT JOIN InspeccionOC IOC ON IOC.idPcompra = PC.idPCompra  \n");
			if(tipo.equalsIgnoreCase("cuarentena")) {
				sbQuery.append("WHERE PE.Tipo = 'Rechazo Por Inspeccion'  \n");
			} else if (tipo.equalsIgnoreCase("reclamo")) {
				sbQuery.append("WHERE PE.Tipo = 'Producto a reclamo'  \n");
			}
			sbQuery.append("AND PE.FFIn IS NULL AND PE.Responsable = :responsable\n");
			sbQuery.append("GROUP BY PROV.Nombre, PC.Compra, PROD.Codigo, IOC.Lote ) RE  \n");
			sbQuery.append("GROUP BY RE.Nombre, Re.Compra ) RE  \n");
			sbQuery.append("GROUP BY RE.Nombre ) RE \n");
			sbQuery.append("GROUP BY RE.Nombre \n");

		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}

	@Override
	public Map<String, Object> obtenerGraficaRechazoInspeccion(Rechazos datos) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			viewRechazoInspeccion(sbQuery, datos.getConcepto());
			sbQuery.append("SELECT 'Proveedor' Etiqueta, RE.Proveedor, RE.totalCompra totalOC, RE.totalProducto, 1 TotalProveedores FROM @TABLA RE ORDER BY RE.totalProducto DESC \n");

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("responsable", datos.getInspector());
			Map<String, Object> mapReturn = new HashMap<String, Object>();
			List<Rechazos> lstGrafica = super.jdbcTemplate.query(sbQuery.toString(), map, new BeanPropertyRowMapper<>(Rechazos.class));
			mapReturn.put("grafica", lstGrafica);

			sbQuery = new StringBuilder("  \n");
			viewRechazoInspeccion(sbQuery, datos.getConcepto());
			sbQuery.append("SELECT 'Total' Etiqueta, '' proveedor, SUM(RE.totalCompra) totalOC, SUM(RE.totalProducto) totalProducto, COUNT(RE.Proveedor) TotalProveedores FROM @TABLA RE \n");
			sbQuery.append(" \n");
			Rechazos totalGrafica = super.jdbcTemplate.queryForObject(sbQuery.toString(), map, new BeanPropertyRowMapper<>(Rechazos.class));

			mapReturn.put("totales", totalGrafica);

			mapReturn.put("barra", obtenerGraficaBarraRechazoInspeccion(datos));

			return mapReturn;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}

	public List<Rechazos> obtenerGraficaBarraRechazoInspeccion(Rechazos datos) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append(" \n");
			sbQuery.append("SELECT Tipo, SUM(totalProducto) totalProducto, SUM(Orden) Orden FROM ( \n");
			sbQuery.append("SELECT PROD.Tipo, COUNT(PROD.Tipo) totalProducto, 0 Orden \n");
			sbQuery.append("FROM Pendiente PE   \n");
			sbQuery.append("INNER JOIN PCompras PC ON PC.idPCompra = PE.Docto \n");  
			sbQuery.append("INNER JOIN Productos PROD ON PROD.idProducto = PC.FK01_Producto \n");  
			sbQuery.append("INNER JOIN Proveedores PROV ON PROV.Clave = PROD.Proveedor   \n");
			sbQuery.append("LEFT JOIN InspeccionOC IOC ON IOC.idPcompra = PC.idPCompra   \n");
			if(datos.getConcepto().equalsIgnoreCase("cuarentena")) {
				sbQuery.append("WHERE PE.Tipo = 'Rechazo Por Inspeccion' AND PE.Responsable = :idResponsable \n");
			} else if (datos.getConcepto().equalsIgnoreCase("reclamo")) {
				sbQuery.append("WHERE PE.Tipo = 'Producto a reclamo' AND PE.Responsable = :idResponsable \n");
			}
			sbQuery.append("AND PE.FFIn IS NULL \n");
			sbQuery.append("GROUP BY PROD.Tipo \n");
			sbQuery.append("UNION SELECT 'Estandares', 0, 1 \n");
			sbQuery.append("UNION SELECT 'Reactivos', 0, 2 \n");
			sbQuery.append("UNION SELECT 'Publicaciones', 0, 3 \n");
			sbQuery.append("UNION SELECT 'Labware', 0, 4 ) BA  \n");
			sbQuery.append("GROUP BY Tipo  \n");
			sbQuery.append("ORDER BY Orden \n");

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idResponsable", datos.getInspector());
			return super.jdbcTemplate.query(sbQuery.toString(), map, new BeanPropertyRowMapper<>(Rechazos.class));
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Rechazos>();
		}
	}


	@Override
	public List<Rechazos> obtenerPiezasRechazadasPorInspeccion(Integer idProveedor, String tipo) throws ProquifaNetException {


		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("SELECT PC.Compra, PROD.Codigo, CAST(PE.FInicio as DATE) FechaInspeccion, IOC.Inspector, \n");
			sbQuery.append("PROD.Concepto, CASE WHEN COM.AlmacenUSA = 1 THEN 'Matriz-PHS' ELSE 'Matriz' END Matriz, \n");
			sbQuery.append("CAST(PP.FPentrega AS DATE) FEE, COALESCE(DATEDIFF(day, GETDATE(), PP.FPEntrega),0) AS DRE, \n");
			sbQuery.append("PROD.Tipo, PP.CPedido, PROD.Manejo, PROD.Fabrica, PROV.Pais Origen, \n");
			sbQuery.append("CASE WHEN PZ.Catalogo = 0 THEN 'Catálogo, ' ELSE '' END + \n");
			sbQuery.append("CASE WHEN PZ.Lote = 0 THEN 'Lote, ' ELSE '' END + \n");
			sbQuery.append("CASE WHEN PZ.Descripcion = 0 THEN 'Descripción, ' ELSE '' END + \n");
			sbQuery.append("CASE WHEN PZ.Caducidad = 0 THEN 'Caducidad, ' ELSE '' END + \n");
			sbQuery.append("CASE WHEN PZ.Presentacion = 0 THEN 'Presentación, ' ELSE '' END + \n");
			sbQuery.append("CASE WHEN PZ.FisicamenteC = 0 THEN 'Físicamente conforme, ' ELSE '' END Causa, \n");
			sbQuery.append("PZ.Rechazos, IOC.ImagenRechazo, PZ.idPieza Identificador, PC.idPCompra, \n");
			sbQuery.append("CASE WHEN PED.TCambio IS NULL OR PED.TCambio = 0 THEN  \n");
			sbQuery.append("CASE WHEN PED.Moneda = 'Pesos' THEN ROUND(PP.Precio/MON.PDolar, 0) \n");
			sbQuery.append("WHEN PED.Moneda = 'Dolares' THEN ROUND(PP.Precio,0) \n");
			sbQuery.append("WHEN PED.Moneda = 'Euros' THEN ROUND(PP.Precio*MON.EDolar, 0) END \n");
			sbQuery.append("ELSE CASE WHEN PED.Moneda = 'Pesos' THEN ROUND(PP.Precio/PED.TCambio,0) \n");
			sbQuery.append("WHEN PED.Moneda = 'Dolares' THEN ROUND(PP.Precio,0) \n");
			sbQuery.append("WHEN PED.Moneda = 'Euros' THEN ROUND(PP.Precio*MON.EDolar, 0) END END AS Monto, PP.idPPedido \n");
			sbQuery.append("FROM Pendiente PE   \n");
			sbQuery.append("INNER JOIN PCompras PC ON PC.idPCompra = PE.Docto \n"); 
			sbQuery.append("INNER JOIN Compras COM ON COM.Clave = PC.Compra \n");
			sbQuery.append("INNER JOIN PPEdidos PP ON PP.idPPedido = PC.FK03_PPedido \n");
			sbQuery.append("INNER JOIN Productos PROD ON PROD.idProducto = PC.FK01_Producto \n"); 
			sbQuery.append("LEFT JOIN InspeccionOC IOC ON IOC.idPcompra = PC.idPCompra  \n");
			sbQuery.append("INNER JOIN Pieza PZ ON PZ.idPCompra = PC.idPCompra  AND  PZ.IdPieza = PE.Partida\n");
			sbQuery.append("INNER JOIN Proveedores PROV ON PROV.Clave = PROD.Proveedor \n");  
			sbQuery.append("INNER JOIN Pedidos PED ON PED.CPedido = PP.CPedido \n");  
			sbQuery.append("LEFT JOIN Monedas AS MON ON CAST(MON.Fecha as date) = CAST(PED.FPedido as DATE) \n");  
			if(tipo.equals("cuarentena")) {
				sbQuery.append("WHERE PE.Tipo = 'Rechazo Por Inspeccion'   \n");
			} else if (tipo.equalsIgnoreCase("reclamo")) {
				sbQuery.append("WHERE PE.Tipo = 'Producto a reclamo'   \n");
			}
			sbQuery.append("AND PE.FFIn IS NULL AND PROV.Clave = :idProveedor \n");
			sbQuery.append("ORDER BY FEE \n");
			sbQuery.append(" \n");

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("idProveedor", idProveedor);
			Map<String, Rechazos> mapReturn = new HashMap<String, Rechazos>();
			List<Rechazos> lstReturn = new ArrayList<Rechazos>();
			super.jdbcTemplate.query(sbQuery.toString(), parametros, new RowMapper<Rechazos>() {
				@Override
				public Rechazos mapRow(ResultSet rs, int arg1) throws SQLException {
					
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy", new  Locale("es","ES"));
					String etiqueta = rs.getString("Compra");
					Rechazos rechazo = new Rechazos();
					rechazo.setCompra(rs.getString("Compra"));
					rechazo.setCodigo(rs.getString("Codigo"));
					rechazo.setFechaInspeccion(rs.getTimestamp("FechaInspeccion"));
					rechazo.setFechaInspeccionFormato(WordUtils.capitalize(dateFormat.format(rs.getDate("FechaInspeccion"))));
					rechazo.setInspector(rs.getString("Inspector"));
					rechazo.setConcepto(rs.getString("Concepto"));
					rechazo.setDestino(rs.getString("Matriz"));
					rechazo.setFee(rs.getTimestamp("FEE"));
					rechazo.setFeeFormato(WordUtils.capitalize(dateFormat.format(rs.getDate("FEE"))));
					rechazo.setDre(rs.getInt("DRE"));
					rechazo.setCpedido(rs.getString("CPEdido"));
					rechazo.setTipo(rs.getString("Tipo"));
					rechazo.setManejo(rs.getString("Manejo"));
					rechazo.setProveedor(rs.getString("Fabrica"));
					rechazo.setOrigen(rs.getString("Origen"));
					rechazo.setCausa(rs.getString("Causa"));
					rechazo.setRechazo(rs.getString("Rechazos"));
					rechazo.setImagenRechazo(rs.getString("ImagenRechazo"));
					rechazo.setIdentificador(rs.getString("Identificador"));
					rechazo.setIdPCompra(rs.getInt("idPCompra"));
					rechazo.setCosto(rs.getDouble("Monto"));
					rechazo.setIdPPedido(rs.getInt("idPPedido"));
					
					if(tipo.equalsIgnoreCase("cuarentena")) {
						if (mapReturn.get(etiqueta) != null) {
							mapReturn.get(etiqueta).getLstRechazos().add(rechazo);
						} else {
							lstReturn.add(rechazo);
							mapReturn.put(etiqueta, rechazo);
							mapReturn.get(etiqueta).setLstRechazos(new ArrayList<Rechazos>());
							Rechazos prechazo = new Rechazos(rechazo);
							mapReturn.get(etiqueta).getLstRechazos().add(prechazo);
						}
					} else if (tipo.equalsIgnoreCase("reclamo")) {
						lstReturn.add(rechazo);
					}
					
					return null;
				}
			});

			return lstReturn;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	@Override
	public boolean updatePieza(Integer idPieza, String instrucciones) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("UPDATE Pieza SET Instrucciones = :instrucciones WHERE idPieza = :idPieza \n");
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idPieza", idPieza);
			map.put("instrucciones", instrucciones);
			jdbcTemplate.update(sbQuery.toString(), map);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	@Override
	public boolean updateInspeccion(String idPCompra, String idPPedido) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("UPDATE PPedidos SET Estado = 'En inspección' WHERE idPPedido = :idPPedido \n");
			sbQuery.append("UPDATE PCompras SET Estado = 'En inspección' WHERE idPcompra = :idPCompra \n");
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idPPedido", idPPedido);
			map.put("idPCompra", idPCompra);
			jdbcTemplate.update(sbQuery.toString(), map);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	@Override
	public boolean insertarReclamo(Map<String, Object> param, String Folio ) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("INSERT INTO RECLAMO (idPieza, Fecha, MEnvio, Notas, Contacto, FolioRA) \n");
			sbQuery.append("VALUES (:idPieza, GETDATE(), :MEnvio, :Notas, :Contacto,:Folio) \n");
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("idPieza", param.get("idPieza"));
			parametros.put("MEnvio", param.get("MEnvio"));
			parametros.put("Notas", param.get("Notas"));
			parametros.put("Contacto", param.get("Contacto"));
			parametros.put("Folio", Folio);
			
			jdbcTemplate.update(sbQuery.toString(), parametros);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public Rechazos obtenerDatosJasper(String idPcompra,String idPieza) {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("SELECT P.Concepto pedido, R.Notas causa, R.FolioRA codigo, IO.ImagenRechazo imagenRechazo, \n");
			sbQuery.append("replace(CONVERT(NVARCHAR, getdate(), 106), ' ', '/') feeFormato, PC.Compra compra, \n");
			sbQuery.append("E.RazonSocial proveedor, PROV.RSocial destino \n");
			sbQuery.append("FROM PCOMPRAS PC \n");
			sbQuery.append("INNER JOIN Compras C ON C.clave = PC.Compra \n");
			sbQuery.append("INNER JOIN EMPRESA E ON  E.Alias =  C.FacturarA COLLATE Modern_Spanish_CI_AS \n");
			sbQuery.append("INNER JOIN PPEDIDOS PP ON PP.idPPedido = PC.FK03_PPEDIDO \n");
			sbQuery.append("INNER JOIN PRODUCTOS P ON P.idProducto = PP.FK02_Producto \n");
			sbQuery.append("INNER JOIN PROVEEDORES PROV ON PROV.Clave = P.Proveedor \n");
			sbQuery.append("INNER JOIN PIEZA PZ ON PZ.idPCompra = PC.idPCompra \n");
			sbQuery.append("INNER JOIN RECLAMO R ON R.idPieza = PZ.IdPieza \n");
			sbQuery.append("INNER JOIN INSPEccionOC IO ON IO.idPCompra = PC.idPCompra \n");
			sbQuery.append("WHERE PC.idPCompra = :idPcompra AND PZ.IdPieza = :idPieza \n");
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("idPcompra", idPcompra);
			param.put("idPieza", idPieza);
			
			return jdbcTemplate.queryForObject(sbQuery.toString(), param,  new BeanPropertyRowMapper<>(Rechazos.class));
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Map<String, Object> obtenerTotales(String idUsuario) throws ProquifaNetException{
		try {
			StringBuilder sbQuery = new StringBuilder();
			sbQuery.append("SELECT 'ProductoReclamo' Tipo, COALESCE(COUNT(Folio), 0) Total  \n");
			sbQuery.append("FROM Pendiente \n");
			sbQuery.append("WHERE Tipo = 'Producto a reclamo' AND FFin IS NULL AND Responsable = :idUsuario \n");
			sbQuery.append("UNION ALL \n");
			sbQuery.append("SELECT 'ArriboDocumentos' Tipo, COALESCE(COUNT(Folio), 0) Total \n");
			sbQuery.append("FROM Pendiente \n");
			sbQuery.append("WHERE Tipo = 'Rechazo por Documentacion' AND FFin IS NULL AND Responsable = :idUsuario \n");
			sbQuery.append("UNION ALL \n");
			sbQuery.append("SELECT 'Nota' Tipo, COALESCE(COUNT(PK_SaldoAFavor), 0) Total  FROM SaldoAFavor WHERE Tipo = 'Nota' AND Estado IS NULL AND Habilitado = 1 \n");
			sbQuery.append("UNION ALL \n");
			sbQuery.append("SELECT 'Saldo' Tipo, COALESCE(COUNT(PK_SaldoAFavor), 0) Total  FROM SaldoAFavor WHERE Tipo = 'Saldo' AND Estado IS NULL AND Habilitado = 1 \n");
			sbQuery.append(" \n");
			
			Map<String, Object> param = new HashMap<String, Object>();
			Map<String, Object> mapReturn = new HashMap<String, Object>();
			param.put("idUsuario", idUsuario);
			getJdbcTemplate().query(sbQuery.toString(), param, new RowMapper<Object>() {
				@Override
				 public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					try {
						mapReturn.put(rs.getString("Tipo"), rs.getInt("Total"));
					} catch (Exception e) {
						// TODO: handle exception
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

} 
