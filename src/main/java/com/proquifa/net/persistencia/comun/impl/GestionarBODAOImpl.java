/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.comun.GestionarBO;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.comun.GestionarBODAO;

/**
 * @author ymendez
 *
 */
@Repository
public class GestionarBODAOImpl extends DataBaseDAO implements GestionarBODAO {

	
	public List<GestionarBO> obtenerProductosBO() throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			viewGestionarBO(sbQuery);
			sbQuery.append("SELECT * FROM @TABLA \n");
			Map<String, Object> map = new HashMap<String, Object>();
			return super.jdbcTemplate.query(sbQuery.toString(), map, new BeanPropertyRowMapper<>(GestionarBO.class));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	
	private void viewGestionarBO(StringBuilder sbQuery) throws ProquifaNetException{
		try {
			sbQuery.append("DECLARE @TABLA TABLE(idProveedor INT, Proveedor VARCHAR(MAx), TotalProductos INT, TotalControlados INT, TotalNoControlados INT) \n");
			sbQuery.append("INSERT INTO @TABLA \n");
			sbQuery.append("SELECT BO.idProveedor, BO.Nombre Proveedor, COUNT(idProducto) TotalProducto,\n");
			sbQuery.append("SUM(CASE WHEN BO.Control = 'Controlados' THEN 1 ELSE 0 END) TotalControlados, \n");
			sbQuery.append("SUM(CASE WHEN BO.Control = 'No Controlados' THEN 1 ELSE 0 END) TotalNoControlados \n");
			sbQuery.append("FROM ( \n");
			sbQuery.append("SELECT PRO.idProducto, PRO.Proveedor idProveedor, PROV.Nombre, \n");
			sbQuery.append("CASE WHEN PRO.Control IS NULL OR PRO.Control = 'Normal' THEN 'No Controlados' ELSE 'Controlados' END Control \n");
			sbQuery.append("FROM Pendiente PE \n");
			sbQuery.append("INNER JOIN Productos PRO ON PRO.idProducto = PE.Docto \n");
			sbQuery.append("INNER JOIN Proveedores PROV ON PROV.Clave = PRO.Proveedor \n");
			sbQuery.append("WHERE PE.Tipo = 'Gestionar producto en BO' AND PE.FFIN IS NULL ) BO \n");
			sbQuery.append("GROUP BY BO.Nombre, BO.idProveedor \n");
			sbQuery.append(" \n");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public Map<String, Object> obtenerGraficaProveedor() throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			viewGestionarBO(sbQuery);
			sbQuery.append("SELECT 'Proveedor' Etiqueta, Proveedor, TotalProductos, 1 TotalProveedores FROM @TABLA ORDER BY TotalProductos DESC \n");
			
			Map<String, Object> map = new HashMap<String, Object>();
			
			Map<String, Object> mapReturn = new HashMap<String, Object>();
			List<GestionarBO> lstGrafica = super.jdbcTemplate.query(sbQuery.toString(), map, new BeanPropertyRowMapper<>(GestionarBO.class));
			mapReturn.put("grafica", lstGrafica);
			
			sbQuery = new StringBuilder(" \n");
			viewGestionarBO(sbQuery);
			sbQuery.append("SELECT 'Total' Etiqueta, SUM(TotalProductos) TotalProductos, COUNT(idPRoveedor) TotalProveedores FROM @TABLA \n");
			GestionarBO totalGrafica = super.jdbcTemplate.queryForObject(sbQuery.toString(), map, new BeanPropertyRowMapper<>(GestionarBO.class));
			
			mapReturn.put("totales", totalGrafica);
			
			sbQuery = new StringBuilder(" \n");
			viewGestionarBO(sbQuery);
			sbQuery.append("SELECT 'Controlados' Control, SUM(TotalControlados) TotalControlados FROM @TABLA \n");
			sbQuery.append("UNION \n");
			sbQuery.append("SELECT 'No Controlados', SUM(TotalNoControlados) TotalNoControlados FROM @TABLA \n");
			sbQuery.append(" \n");
			List<GestionarBO> lstGraficaBarra = super.jdbcTemplate.query(sbQuery.toString(), map, new BeanPropertyRowMapper<>(GestionarBO.class));
			
			mapReturn.put("barra", lstGraficaBarra);
			
			mapReturn.put("lista", obtenerProductosBO());

			mapReturn.put("porFamilia", obtenerGraficaFamilia());
			
			return mapReturn;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	public List<GestionarBO> obtenerGraficaFamilia() throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("SELECT BO.Etiqueta, COUNT(BO.Etiqueta) totalProductos FROM ( \n");
			sbQuery.append("SELECT PRO.Tipo + ' ' + PRO.SubTipo COLLATE SQL_Latin1_General_CP1_CI_AS Etiqueta \n");
			sbQuery.append("FROM Pendiente PE \n");
			sbQuery.append("INNER JOIN Productos PRO ON PRO.idProducto = PE.Docto \n");
			sbQuery.append("INNER JOIN Proveedores PROV ON PROV.Clave = PRO.Proveedor \n");
			sbQuery.append("WHERE PE.Tipo = 'Gestionar producto en BO' AND PE.FFIN IS NULL ) BO \n");
			sbQuery.append("GROUP BY BO.Etiqueta \n");
			sbQuery.append("ORDER BY totalProductos DESC \n");
			sbQuery.append(" \n");
			
			Map<String, Object> map = new HashMap<String, Object>();
			return super.jdbcTemplate.query(sbQuery.toString(), map, new BeanPropertyRowMapper<>(GestionarBO.class));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	@Override
	public List<GestionarBO> obtenerProductosBOPorProveedor(Integer idProveedor) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("SELECT PROV.Clave idProveedor, PROV.Nombre Proveedor, PROD.Codigo, PROD.idProducto, COALESCE(PBO.PK_ProductoBO,0) idProductoBO, \n"); 
			sbQuery.append("CASE WHEN PROD.Control IS NULL OR PROD.Control = 'Normal' THEN 'No Controlados' ELSE 'Controlados' END Control, \n");
			sbQuery.append("CASE WHEN PROV.Clave = 44 THEN CAST(PROD.Concepto as VARCHAR(MAX)) ELSE CAST(PROD.Concepto as VARCHAR(MAX)) + ' (' + PROD.Cantidad + ' ' + PROD.Unidad + ')' END Descripcion, \n");
			sbQuery.append("PROD.Unidad, PROD.Cantidad, COALESCE(PROD.tipoPresentacion, 'ND') presentacion, \n");
			sbQuery.append("PROD.Tipo, PROD.SubTipo, CAST(PEN.FInicio as DATE) FInicio, PEN.FInicio fechaInicio \n");
			sbQuery.append("FROM Pendiente PEN \n");
			sbQuery.append("INNER JOIN Productos PROD ON PROD.idProducto = PEN.Docto \n");
			sbQuery.append("INNER JOIN Proveedores PROV ON PROV.Clave = PROD.Proveedor \n");
			sbQuery.append("LEFT JOIN PRoductoBO PBO ON PBO.FK01_PRoducto = PROD.idProducto \n");
			sbQuery.append("WHERE PEN.Tipo = 'Gestionar producto en BO' AND PEN.FFIN IS NULL \n");
			sbQuery.append("AND PROV.Clave = :proveedor \n");
			sbQuery.append(" \n");
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("proveedor", idProveedor);
			return super.jdbcTemplate.query(sbQuery.toString(), map, new BeanPropertyRowMapper<>(GestionarBO.class));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	@Override
	public boolean finalizarProductoBO(Map<String, Object> data) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			if (data.get("idProductoBO").toString().equals("0")) {
				sbQuery.append("INSERT INTO ProductoBO(FK01_Producto, FUA, FEDisponibilidad, Razon, FInicio) VALUES(:idProducto, :fua, :disponibilidad, :razon, \n");
				sbQuery.append("(SELECT FInicio FROM PENDIENTE WHERE DOCTO = :idProducto AND Tipo = 'Gestionar producto en BO' AND FFIN IS NULL)) \n");
			}else {
				sbQuery.append("UPDATE ProductoBO SET FUA = :fua, FEDisponibilidad = :disponibilidad, razon = :razon WHERE PK_ProductoBO = :idProductoBO \n");
			}
			sbQuery.append("UPDATE Pendiente SET FFin = :fua WHERE Docto = :idProducto AND Tipo = 'Gestionar producto en BO' \n");
			
			if (data.get("razon").toString().equals("Descontinuado")) {
				sbQuery.append("UPDATE Productos SET Disponibilidad = 'Descontinuado', Vigente = 0 WHERE idProducto = :idProducto");
			}
			
			jdbcTemplate.update(sbQuery.toString(), data);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
}
