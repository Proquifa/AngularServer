/**
 * 
 */
package com.proquifa.net.persistencia.compras.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;


import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.compras.PartidaCompra;

/**
 * @author sarivera
 *
 */
public class PartidaCompraRowMapper implements RowMapper {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		PartidaCompra partidaCompra = new PartidaCompra();
		String ubicacion="";
		
		partidaCompra.setIdPartidaCompra(rs.getLong("idPCompra"));
		partidaCompra.setIdCompra(rs.getLong("FK02_Compra"));
		partidaCompra.setCompra(rs.getString("compra"));
		partidaCompra.setPartida(rs.getInt("partida"));
		partidaCompra.setPedido(rs.getString("cpedido"));
		partidaCompra.setPartidaPedido(rs.getInt("ppedido"));
		partidaCompra.setCantidadCompra(rs.getInt("cant"));
		partidaCompra.setCodigo(rs.getString("codigo"));
		partidaCompra.setFabrica(rs.getString("fabrica"));
		partidaCompra.setCosto(rs.getDouble("costo"));
		partidaCompra.setEstado(rs.getString("estado"));
		partidaCompra.setFactura(rs.getString("factura"));
		partidaCompra.setFechaRealArriboUSA(rs.getDate("fpharma"));
		partidaCompra.setFechaRealArriboPQF(rs.getDate("fproquifa"));
		partidaCompra.setControl(rs.getString("control"));
		partidaCompra.setPorCancelar(rs.getBoolean("porCancelar"));
		partidaCompra.setFolioNT(rs.getString("folioNT"));
		partidaCompra.setIdComplemento(rs.getLong("idcomplemento"));
		partidaCompra.setPagada(rs.getBoolean("pagada"));
		partidaCompra.setFechaEstimadaEntrega(rs.getDate("feentrega"));
		partidaCompra.setBackOrder(rs.getBoolean("backOrder"));
		partidaCompra.setComentarios(rs.getString("comentarios"));
		partidaCompra.setFolioInspeccion(rs.getString("folioInspeccion"));
		partidaCompra.setFolioInspeccionPHS(rs.getString("folioInspeccion"));
		partidaCompra.setFolioInspeccionUE(rs.getString("folioInspeccionUE"));
		partidaCompra.setCotizacion(rs.getString("cotiza"));
		partidaCompra.setComentariosPHS(rs.getString("comentariosPHS"));
		partidaCompra.setAnaquelNumero(rs.getInt("anaquelNum"));
		partidaCompra.setAnaquelLetra(rs.getString("anaquelLetra"));
		partidaCompra.setProveedor(rs.getLong("Proveedor"));		
		partidaCompra.setCas(rs.getString("CAS"));
		partidaCompra.setIdProducto(rs.getLong("idProducto"));
		partidaCompra.setTipo(rs.getString("tipo"));
		partidaCompra.setSubtipo(rs.getString("subtipo"));
		partidaCompra.setControlProducto(rs.getString("ControlProducto"));
		
		try {partidaCompra.setNoFactura(rs.getString("NoFactura"));} catch (Exception e) {}
		try {partidaCompra.setFolioFP(rs.getString("FolioFP"));} catch (Exception e) {}
		try {partidaCompra.setFolioPG(rs.getString("FolioPG"));} catch (Exception e) {}		
		
		try {partidaCompra.setCant(rs.getInt("Cant"));} catch (Exception e) {}
		try {partidaCompra.setDescripcion(rs.getString("Concepto"));} catch (Exception e) {}
		try {partidaCompra.setFraccionArancelaria(rs.getString("FraccionArancelaria"));} catch (Exception e) {}
		try {partidaCompra.setOrigen(rs.getString("origen"));} catch (Exception e) {}
		try {partidaCompra.setOrigen(rs.getString("OrigenProducto"));} catch (Exception e) {}
		try {partidaCompra.setMontoTotal(rs.getDouble("Monto"));} catch (Exception e) {}
		try {partidaCompra.setNoFactura(rs.getString("NoFactura"));} catch (Exception e) {}
		try {partidaCompra.setCantidad(rs.getString("Cantidad"));} catch (Exception e) {}
		try {partidaCompra.setUnidad(rs.getString("Unidad"));} catch (Exception e) {}
		
		try {partidaCompra.setCaracteristicasFisicas(rs.getString("CaracteristicasFisicas"));} catch (Exception e) {}
		try {partidaCompra.setComposicion(rs.getString("Composicion"));} catch (Exception e) {}
		try {partidaCompra.setFormulaQuimica(rs.getString("FormulaQuimica"));} catch (Exception e) {}
		try {partidaCompra.setPeligrosidad(rs.getString("Peligrosidad"));} catch (Exception e) {}
		try {partidaCompra.setCertificadoNoDisponible(rs.getBoolean("CertificadoNoDisponible"));} catch (Exception e) {}
		
		
		ubicacion = rs.getString("Ubicacion");
		if(rs.wasNull()){
			partidaCompra.setUbicacion(null);
		}else{
			if(ubicacion.equals("Anaquel")){
				partidaCompra.setUbicacion(partidaCompra.getAnaquelLetra() + "-" + partidaCompra.getAnaquelNumero());
			}else{			
				partidaCompra.setUbicacion(ubicacion);
			}
		}
		try {partidaCompra.setPorEnterarse(rs.getBoolean("porEnterarse"));} catch (Exception e) {}
		try {partidaCompra.setFechaRealArriboUE(rs.getDate("fpharmaue"));} catch (Exception e) {}
		
		try {partidaCompra.setNombreEmpresaAFacturar(rs.getString("EmpresaAFacturar"));} catch (Exception e) {}
		try {partidaCompra.setPaisEmpresaAFacturar(rs.getString("PaisEmpresaAFacturar"));} catch (Exception e) {}
		try {partidaCompra.setIdEmpresaAFacturar(rs.getLong("idEmpresaAFacturar"));} catch (Exception e) {}
		try {partidaCompra.setIdFabricante(rs.getString("idFabricante"));} catch (Exception e) {}
		try {partidaCompra.setLote(rs.getString("Lote"));} catch (Exception e) {}
		
		return partidaCompra;
	}
}