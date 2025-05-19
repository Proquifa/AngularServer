/**
 * 
 */
package com.proquifa.net.persistencia.compras.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.compras.SaldoAFavor;
import com.proquifa.net.modelo.comun.Complemento;
import com.proquifa.net.modelo.comun.Empresa;
import com.proquifa.net.modelo.comun.Producto;
import com.proquifa.net.modelo.comun.Proveedor;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.compras.NestedRowMapper;
import com.proquifa.net.persistencia.compras.SaldoAFavorDAO;
import com.proquifa.net.persistencia.comun.impl.mapper.ProductoRowMapper;

/**
 * @author ymendez
 *
 */
@Repository
public class SaldoAFavorDAOImpl extends DataBaseDAO implements SaldoAFavorDAO {

	
	@Override
	public String generarSaldo(SaldoAFavor saldo) throws ProquifaNetException {
		try {
			Map<String, Object> parametros = new HashMap<String, Object>();
			StringBuilder sbQuery = new StringBuilder("INSERT INTO SaldoAFavor(FK01_Proveedor, FK02_Empresa, Fecha, Folio, Serie, UUID, FechaDocto, Monto, FolioDocto, Comentarios, Estado, Habilitado, Tipo) \n");
			sbQuery.append("VALUES(:proveedor, :empresa, GETDATE(), :folio, :serie, :uuid, :fechaDocto, :monto, :folioDocto, :comentarios, :estado, :habilitado, :tipo) \n");
			
			parametros.put("proveedor", saldo.getProveedor().getIdProveedor());
			parametros.put("empresa", saldo.getEmpresa().getIdEmpresa());
			parametros.put("folio", saldo.getFolio());
			parametros.put("serie", saldo.getSerie());
			parametros.put("uuid", saldo.getUuid());
			parametros.put("fechaDocto", saldo.getFfechaDocto());
			parametros.put("monto", saldo.getMonto());
			parametros.put("folioDocto", saldo.getFolioDocto());
			parametros.put("comentarios", saldo.getComentarios());
			parametros.put("estado", saldo.getEstado());
			parametros.put("habilitado", saldo.isHabilitado());
			parametros.put("tipo", saldo.getTipo());
			
			jdbcTemplate.update(sbQuery.toString(), parametros);

			return saldo.getFolioDocto();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	@Override
	public Map<String, List<SaldoAFavor>> obtenerListaSaldo(String tipo) throws ProquifaNetException {
		try {
			Map<String, Object> parametros = new HashMap<String, Object>();
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("SELECT SF.PK_SaldoAFavor idSaldo, SF.*, PROV.Clave 'proveedor.clave', PROV.Nombre 'proveedor.Nombre', PROV.Moneda, PROV.RSocial 'proveedor.razonSocial', \n");
			sbQuery.append("EM.PK_Empresa 'empresa.idEmpresa', EM.RazonSocial  'empresa.RazonSocial', EM.Alias 'empresa.Alias' \n");
			sbQuery.append("FROM SaldoAFavor SF \n");
			sbQuery.append("INNER JOIN Proveedores PROV ON PROV.CLAVE = SF.FK01_Proveedor \n");
			sbQuery.append("INNER JOIN Empresa EM ON EM.PK_Empresa = SF.FK02_Empresa \n");
			sbQuery.append("WHERE SF.Estado IS NULL AND SF.Habilitado = 1 \n");
			if (tipo != null)
				sbQuery.append("AND SF.Tipo = :tipo \n");
			sbQuery.append("ORDER BY EM.PK_Empresa \n");
			
			parametros.put("tipo", tipo);
			
			List<SaldoAFavor> lstBarras = new ArrayList<SaldoAFavor>();
			Map<String, SaldoAFavor> mapBarras = new HashMap<String, SaldoAFavor>();
			Map<String, String> mapProveedores = new HashMap<String, String>();
			Map<String, List<SaldoAFavor>> mapReturn = new HashMap<String, List<SaldoAFavor>>();
			List<SaldoAFavor> saldos = jdbcTemplate.query(sbQuery.toString(), parametros, new NestedRowMapper<>(SaldoAFavor.class));
			List<SaldoAFavor> lstProveedores = new ArrayList<SaldoAFavor>();
			mapReturn.put("TODAS", saldos);
			SaldoAFavor todas = new SaldoAFavor();
			todas.setEmpresa(new Empresa());
			todas.setEtiqueta("TODAS");
			todas.setTotal(saldos.size());
			lstBarras.add(todas);
			
			for (SaldoAFavor saldoAFavor : saldos) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMMM/yyyy", new  Locale("es","ES"));
				SimpleDateFormat dateFormatFilter = new SimpleDateFormat("yyyy/MM/dd", new  Locale("es","ES"));
				saldoAFavor.setFfecha(dateFormat.format(saldoAFavor.getFecha()));
				saldoAFavor.setFfechaDocto(dateFormat.format(saldoAFavor.getFechaDocto()));
				saldoAFavor.setOrdenarFecha(dateFormatFilter.format(saldoAFavor.getFechaDocto()));
				if (mapReturn.get(saldoAFavor.getEmpresa().getAlias()) != null) {
					mapReturn.get(saldoAFavor.getEmpresa().getAlias()).add(saldoAFavor);
					
					mapBarras.get(saldoAFavor.getEmpresa().getAlias()).setTotal(mapBarras.get(saldoAFavor.getEmpresa().getAlias()).getTotal() + 1);
					
				} else {
					List<SaldoAFavor> lstReturn = new ArrayList<SaldoAFavor>();
					mapReturn.put(saldoAFavor.getEmpresa().getAlias(), lstReturn);
					lstReturn.add(saldoAFavor);
					
					mapBarras.put(saldoAFavor.getEmpresa().getAlias(), new SaldoAFavor(saldoAFavor));
					lstBarras.add(mapBarras.get(saldoAFavor.getEmpresa().getAlias()));
					mapBarras.get(saldoAFavor.getEmpresa().getAlias()).setTotal(1);
					
				}
				
				if (mapProveedores.get(saldoAFavor.getProveedor().getNombre()) == null) {
					mapProveedores.put(saldoAFavor.getProveedor().getNombre(), saldoAFavor.getProveedor().getNombre());
					lstProveedores.add(saldoAFavor);
				}
			}
			
			Collections.sort(lstProveedores, (x, y) -> x.getProveedor().getNombre().compareToIgnoreCase(y.getProveedor().getNombre()));
			
			mapReturn.put("BARRAS", lstBarras);
			mapReturn.put("PROVEEDORES", lstProveedores);
			
			return mapReturn;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Producto obtenerDescProducto(Long idProducto) throws ProquifaNetException{
		try {
			Map<String, Object> parametros = new HashMap<String, Object>();
			String query ="SELECT * FROM Productos WHERE idProducto='"+idProducto+"'";	
			return (Producto) jdbcTemplate.queryForObject(query, parametros, new ProductoRowMapper());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
		
	}
	
	@Override
	public Complemento obtenerDescComplemento(Long idComplemento) {	
		try {
			Map<String, Object> parametros = new HashMap<String, Object>();
		String query ="SELECT * FROM Complemento WHERE idComplemento='"+idComplemento+"'";	
		return (Complemento) jdbcTemplate.queryForObject(query, parametros, new RowMapper<Complemento>() {

			@Override
			public Complemento mapRow(ResultSet rs, int rowNum) throws SQLException {
				Complemento complemento = new Complemento();
				complemento.setIdComplemento(rs.getLong("idComplemento"));
				complemento.setCodigo(rs.getString("codigo"));
				complemento.setFabrica(rs.getString("fabrica"));
				complemento.setDescripcion(rs.getString("descripcion"));
				return complemento;
			}
		});
		}catch (Exception e) {
			e.printStackTrace();
			return new Complemento();
		}
		
	}
	
	@Override
	public boolean habilitarSaldo(SaldoAFavor saldo) throws ProquifaNetException {
		try {
			Map<String, Object> parametros = new HashMap<String, Object>();
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("UPDATE SaldoAFavor SET Habilitado = :habilitado WHERE PK_SaldoAFavor = :idSaldo \n");
			parametros.put("habilitado", saldo.isHabilitado());
			parametros.put("idSaldo", saldo.getIdSaldo());
			
			jdbcTemplate.update(sbQuery.toString(), parametros);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
