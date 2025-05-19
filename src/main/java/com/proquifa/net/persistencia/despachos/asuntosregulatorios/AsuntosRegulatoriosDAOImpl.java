/**
 * 
 */
package com.proquifa.net.persistencia.despachos.asuntosregulatorios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.despachos.asuntosregulatorios.GestionarPap;
import com.proquifa.net.modelo.despachos.asuntosregulatorios.PermisoAdquisicion;
import com.proquifa.net.modelo.despachos.asuntosregulatorios.TotalesPAP;
import com.proquifa.net.persistencia.DataBaseDAO;

/**
 * @author ymendez
 *
 */
@Repository
public class AsuntosRegulatoriosDAOImpl extends DataBaseDAO implements AsuntosRegulatoriosDAO {

	/* (non-Javadoc)
	 * @see com.proquifa.net.persistencia.despachos.asuntosregulatorios.AsuntosRegulatoriosDAO#obtenerPendientesPap(java.lang.Integer)
	 */
	@Override
	public List<GestionarPap> obtenerPendientesPap(Integer idUsuario) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			viewPAP(sbQuery);
			sbQuery.append(" \n");
			
			Map<String, Object> map = new HashMap<String, Object>();
			
			return jdbcTemplate.query(sbQuery.toString(), map, new BeanPropertyRowMapper<>(GestionarPap.class));
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}

	@Override
	public Map<String, List<TotalesPAP>> obtenerDatosGrafica(Integer idUsuario) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("DECLARE @TABLA TABLE(cpedido varchar(50), idCliente int, Cliente varchar(MAX), Cantidad varchar(50), Unidad varchar(5), \n");
			sbQuery.append("idProducto int, Concepto varchar(MAX), Monto real, Piezas real, idPPedido int, FTramite DATE, FEE DATE,idPedido int ,Precio real) \n");
			sbQuery.append("INSERT INTO @TABLA \n");
			viewPAP(sbQuery);
			sbQuery.append("SELECT 'Clientes' Etiqueta, Cliente Nombre, COUNT(idProducto) totalProductos, SUM(Piezas) totalPiezas, SUM(Monto) Monto \n");
			sbQuery.append("FROM @TABLA \n");
			sbQuery.append("GROUP BY Cliente \n");

			sbQuery.append("UNION ALL \n");

			sbQuery.append("SELECT 'Productos' Etiqueta, Concepto, COUNT(idProducto), SUM(Piezas) totalPiezas, SUM(Monto) Monto \n");
			sbQuery.append("FROM @TABLA \n");
			sbQuery.append("GROUP BY Concepto \n");
			sbQuery.append("ORDER BY Monto DESC, totalPiezas DESC \n");
			sbQuery.append(" \n");
			
			
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, List<TotalesPAP>> mapReturn = new HashMap<String, List<TotalesPAP>>();
			
			jdbcTemplate.query(sbQuery.toString(), map, new RowMapper<TotalesPAP>() {
				@Override
				public TotalesPAP mapRow(ResultSet rs, int arg1) throws SQLException {
					
					TotalesPAP totales = new TotalesPAP();
					totales.setEtiqueta(rs.getString("Etiqueta"));
					totales.setMonto(rs.getDouble("Monto"));
					totales.setNombre(rs.getString("Nombre"));
					totales.setTotalPiezas(rs.getInt("totalPiezas"));
					totales.setTotalProductos(rs.getInt("totalProductos"));
					
					if (mapReturn.get(totales.getEtiqueta()) != null) {
						mapReturn.get(totales.getEtiqueta()).add(totales);
					} else {
						List<TotalesPAP> list = new ArrayList<TotalesPAP>();
						mapReturn.put(totales.getEtiqueta(), list);
						mapReturn.get(totales.getEtiqueta()).add(totales);
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
	
	private void viewPAP(StringBuilder sbQuery) throws ProquifaNetException {
		sbQuery.append("SELECT *, Piezas.Precio * Piezas.Piezas Monto FROM ( \n");
		sbQuery.append("SELECT PP.CPedido cpedido, CL.Clave idCliente, CL.Nombre Cliente, PROD.Cantidad, PROD.Unidad, \n");
		sbQuery.append("PROD.idProducto, CAST(PROD.Concepto as VARCHAR(MAx)) Concepto,  \n");
		sbQuery.append("CASE WHEN (PE.TCambio = 0 or PE.TCambio is null) THEN  \n");
		sbQuery.append("CASE WHEN PE.Moneda = 'Pesos' THEN ROUND(PP.Precio/MON.PDolar, 0) \n");
		sbQuery.append("WHEN PE.Moneda = 'Dolares' THEN ROUND(PP.Precio,0) \n");
		sbQuery.append("WHEN PE.Moneda = 'Euros' THEN ROUND(PP.Precio*MON.EDolar, 0) END \n");
		sbQuery.append("ELSE CASE WHEN PE.Moneda = 'Pesos' THEN ROUND(PP.Precio/PE.TCambio,0) \n");
		sbQuery.append("WHEN PE.Moneda = 'Dolares' THEN ROUND(PP.Precio,0) \n");
		sbQuery.append("WHEN PE.Moneda = 'Euros' THEN ROUND(PP.Precio*MON.EDolar, 0) END END AS Precio, \n");
		sbQuery.append("PP.Cant piezas, PP.idPPedido, PE.FPedido FTramite, PP.FPEntrega FEE, PE.idPedido \n");
		sbQuery.append("FROM Pendiente PEN \n");
		sbQuery.append("INNER JOIN PPEdidos PP ON PP.idPPedido = PEN.Partida \n");
		sbQuery.append("INNER JOIN Pedidos PE ON PE.idPedido = PEN.Docto \n");
		sbQuery.append("INNER JOIN Clientes CL ON CL.Clave = PE.idCliente \n");
		sbQuery.append("INNER JOIN Productos PROD ON PROD.idProducto = PP.FK02_Producto \n");
		sbQuery.append("LEFT JOIN Monedas AS MON ON CAST(MON.Fecha as date) = CAST(PE.FPedido as DATE) \n");
		sbQuery.append("WHERE PEN.Tipo Like 'PAP por cargar' AND PEN.FFin IS NULL AND PP.FPEntrega IS NOT NULL ) Piezas \n");
		sbQuery.append(" \n");
	}
	
	
	@Override
	public Integer saveGestionarPAP(PermisoAdquisicion permiso) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("INSERT INTO GestionPAP(FK01_PPedido, NoPermisoAdquisicion, FechaPermiso, FechaVencimiento, NoActaLiberacion, TipoPermiso) \n");
			sbQuery.append("VALUES(:idPPedido, :noPermiso, :fechaPermiso, :fechaVencimiento, :noActaLiberacion, :tipoPermiso) \n");
			sbQuery.append(" \n");
			SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
			Date fechaPersmiso = fecha.parse(permiso.getFechaPermiso());
			Date fechaVencimiento = fecha.parse(permiso.getFechaVencimiento());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idPPedido", permiso.getIdPPedido());
			map.put("noPermiso", permiso.getNoPermisoAdquisicion());
			map.put("fechaPermiso", fechaPersmiso);
			map.put("fechaVencimiento", fechaVencimiento);
			map.put("noActaLiberacion", permiso.getActaLiberacion());
			map.put("tipoPermiso", permiso.getTipoPermiso());
			
			jdbcTemplate.update(sbQuery.toString(), map);
			
			StringBuilder sbQuery1 = new StringBuilder(" \n  ");
			sbQuery1.append("SELECT IDENT_CURRENT ('GestionPAP') \n");
			Map<String, Object> parametros = new HashMap<String, Object>();
			return super.jdbcTemplate.queryForObject(sbQuery1.toString(),parametros, Integer.class);
			// return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}

}
