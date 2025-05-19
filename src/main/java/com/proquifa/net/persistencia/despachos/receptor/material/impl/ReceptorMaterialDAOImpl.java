/**
 * 
 */
package com.proquifa.net.persistencia.despachos.receptor.material.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.despachos.Guias;
import com.proquifa.net.modelo.despachos.ReceptorMaterial;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.despachos.receptor.material.ReceptorMaterialDAO;

/**
 * @author ymendez
 *
 */
@Repository
public class ReceptorMaterialDAOImpl extends DataBaseDAO implements ReceptorMaterialDAO {

	/* (non-Javadoc)
	 * @see com.proquifa.net.persistencia.despachos.receptor.material.ReceptorMaterialDAO#getDatosGrafica(java.lang.Integer)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map<String, List<ReceptorMaterial>> getDatosGrafica(Integer idUsuario) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append(" \n");
			viewMaterial(sbQuery);
			sbQuery.append("SELECT 'Mensajeria' Etiqueta, COUNT(Guia) totalGuias, SUM(totalClientes) totalClientes, \n");
			sbQuery.append("SUM(totalFacturas) totalFacturas, 1 totalMensajeria, Mensajeria Concepto FROM ( \n");
			sbQuery.append("SELECT Guia, Mensajeria, COUNT(idCliente) totalClientes, \n");
			sbQuery.append("COUNT(Factura) totalFacturas, COUNT(FPor) totalFpor \n");
			sbQuery.append("FROM @Tabla \n");
			sbQuery.append("GROUP BY Guia, Mensajeria ) Mensajeria \n");
			sbQuery.append("GROUP BY Mensajeria \n");

			sbQuery.append("UNION ALL \n");

			sbQuery.append("SELECT 'Cliente' Etiqueta, SUM(totalGuias) totalGuias, 1, SUM(totalFacturas) totalFacturas, SUM(totalMensajeria) totalMensajeria, Nombre COLLATE Modern_Spanish_CI_AS FROM ( \n");
			sbQuery.append("SELECT CL.Nombre, COUNT(TB.Guia) totalGuias, COUNT(TB.Mensajeria) totalMensajeria, COUNT(TB.Factura) totalFacturas, COUNT(TB.FPor) totalFpor \n");
			sbQuery.append("FROM @Tabla TB \n");
			sbQuery.append("INNER JOIN Clientes CL ON CL.clave = TB.idCliente \n");
			sbQuery.append("GROUP BY CL.Nombre ) CL \n");
			sbQuery.append("GROUP BY Nombre \n");

			sbQuery.append("UNION ALL  \n");

			sbQuery.append("SELECT 'Totales' Etiqueta, (SELECT COUNT(Guia) totalGuia FROM ( SELECT Guia FROM @Tabla GROUP BY Guia ) Guia) totalGuias, \n");
			sbQuery.append("(SELECT COUNT(idCliente) totalClientes FROM ( SELECT idCliente FROM @Tabla GROUP BY idCliente ) idCliente) totalClientes, \n");
			sbQuery.append("(SELECT SUM(totalFacturas) totalFacturas FROM (SELECT COUNT(Factura) totalFacturas FROM ( SELECT Factura FROM @Tabla GROUP BY Factura,FPor ) Factura) Factura) totalFacturas, \n");
			sbQuery.append("(SELECT COUNT(Mensajeria) totalMensajeria FROM ( SELECT Mensajeria FROM @Tabla GROUP BY Mensajeria ) Mensajeria) totalMensajeria, '' \n");
			sbQuery.append("ORDER BY Etiqueta, totalGuias DESC, totalClientes DESC, Concepto  \n");
			
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idResponsable", idUsuario);
			Map<String, List<ReceptorMaterial>> mapReturn = new HashMap<String, List<ReceptorMaterial>>();
			jdbcTemplate.query(sbQuery.toString(), map, new RowMapper() {
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					
					ReceptorMaterial total = new ReceptorMaterial();
					
					String etiqueta = rs.getString("Etiqueta");
					
					total.setConcepto(rs.getString("Concepto"));
					total.setTotalClientes(rs.getInt("totalClientes"));
					total.setTotalFacturas(rs.getInt("totalFacturas"));
					total.setTotalGuias(rs.getInt("totalGuias"));
					total.setTotalMensajeria(rs.getInt("totalMensajeria"));
					
					if (mapReturn.get(etiqueta) != null) {
						mapReturn.get(etiqueta).add(total);
					} else {
						List<ReceptorMaterial> lst = new ArrayList<ReceptorMaterial>();
						mapReturn.put(etiqueta, lst);
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
	
	
	@Override
	public List<?> getGuias(Integer idUsuario) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			viewMaterial(sbQuery);

			sbQuery.append("SELECT Guia, Mensajeria, COUNT(idCliente) totalClientes, COUNT(Factura) totalFacturas, Fecha FechaEnvio, Hora, idPendiente \n");
			sbQuery.append("FROM @Tabla \n");
			sbQuery.append("GROUP BY Guia, Mensajeria, Fecha, Hora, idPendiente \n");
			sbQuery.append(" \n");
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idResponsable", idUsuario);
			
			return getJdbcTemplate().query(sbQuery.toString(), map, new BeanPropertyRowMapper<>(Guias.class));
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	
	private void viewMaterial(StringBuilder sbQuery) {
		sbQuery.append("DECLARE @TABLA TABLE (Guia varchar(200), idCliente int, Factura int, FPor varchar(300), Mensajeria varchar(300), Fecha DATE, Hora Time(0), idPendiente INT) \n");
		sbQuery.append("INSERT INTO @TABLA \n");
		sbQuery.append("SELECT PEN.Docto Guia, PE.idCliente, PF.Factura, PF.FPor, PEN.Partida Mensajeria, PEN.FInicio, PEN.FInicio, PEN.Folio \n");
		sbQuery.append("FROM PEndiente PEN  \n");
		sbQuery.append("INNER JOIN EMbalarPEdido EP ON EP.NoGuia = PEN.Docto COLLATE Modern_Spanish_CI_AS \n");
		sbQuery.append("INNER JOIN PPedidos PP ON PP.idPPedido = EP.FK01_PPedido \n");
		sbQuery.append("INNER JOIN PEdidos PE ON PE.CPedido = PP.CPedido \n");
		sbQuery.append("INNER JOIN PFacturas PF ON PF.CPedido = PP.CPedido AND PP.Part = PF.PPedido \n");
		sbQuery.append("WHERE PEN.Tipo = 'Receptor de Materiales' AND PEN.FFIN IS NULL \n");
		sbQuery.append("GROUP BY PEN.Docto, PE.idCliente, PF.Factura, PF.FPor, PEN.PArtida, PEN.FInicio, PEN.Folio \n");
	}
	
	
	@Override
	public List<String> obtenerFolio(String guia) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append(" \n");
			
			sbQuery.append("SELECT PL.Folio \n");
			sbQuery.append("FROM PEndiente PEN  \n");
			sbQuery.append("INNER JOIN EMbalarPEdido EP ON EP.NoGuia = PEN.Docto COLLATE Modern_Spanish_CI_AS \n");
			sbQuery.append("INNER JOIN PPackingList PPL ON PPL.FK02_EmbalarPEdido = EP.PK_EmbalarPedido \n");
			sbQuery.append("INNER JOIN PackingList PL ON PL.PK_PackingList = PPL.FK01_PAckingList \n");
			sbQuery.append("WHERE PEN.Tipo = 'Receptor de Materiales' \n");
			sbQuery.append("AND PEN.Docto = :guia \n");
			sbQuery.append("GROUP BY PL.Folio \n");
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("guia", guia);
			
			return getJdbcTemplate().queryForList(sbQuery.toString(), map, String.class);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	

}
