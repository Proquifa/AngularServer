package com.proquifa.net.persistencia.compras.comprador.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.despachos.PartidaInspeccion;

/**
 * @author heri.hernandez
 *
 */
public class OrdenPartidaInspeccionRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {

		Integer idFabrica = 0;
		PartidaInspeccion partidaInspeccion = new PartidaInspeccion();
		partidaInspeccion.setIdPartidaCompra(rs.getLong("idPCompra"));
		partidaInspeccion.setIdPPedido(rs.getLong("idPPedido"));
		partidaInspeccion.setEstado(rs.getString("Estado"));
		partidaInspeccion.setCantidadCompra(rs.getInt("Cant"));
		partidaInspeccion.setCompra(rs.getString("Compra"));
		partidaInspeccion.setCpedido(rs.getString("CPedido"));
		partidaInspeccion.setIdComplemento(rs.getLong("idComplemento"));
		partidaInspeccion.setFolioOrdenDespacho(rs.getString("ODFolio"));
		partidaInspeccion.setFolioListaArribo(rs.getString("Folio"));
		partidaInspeccion.setPedimento(rs.getString("NoPedimento"));
		partidaInspeccion.setGuia(rs.getString("GuiaEmbarque"));
		partidaInspeccion.setIdProducto(rs.getLong("idProducto"));
		partidaInspeccion.setCodigo(rs.getString("Codigo"));
		partidaInspeccion.setConcepto(rs.getString("Descripcion"));
		partidaInspeccion.setFabrica(rs.getString("Fabrica"));
		partidaInspeccion.setLote(rs.getString("Lote"));
		partidaInspeccion.setManejo(rs.getString("Manejo"));

		if(rs.getLong("idComplemento") > 0) {
			partidaInspeccion.setTipo("Publicaciones");
			
		} else {
			partidaInspeccion.setTipo(rs.getString("Tipo"));
		}
		partidaInspeccion.setSubtipo(rs.getString("Subtipo"));
		if (rs.getString("Control") == null) {
			partidaInspeccion.setControl("Normal");
		} else {
			partidaInspeccion.setControl(rs.getString("Control"));
		}
		partidaInspeccion.setLoteAnterior(rs.getString("Lote_Anterior"));
		partidaInspeccion.setFolioEtiqueta(rs.getString("FolioEtiqueta"));
		partidaInspeccion.setMontoTotal(rs.getDouble("Monto"));
		
		partidaInspeccion.setManejoTransporte(rs.getString("Manejo_Transporte"));
		partidaInspeccion.setTipoPresentacion(rs.getString("tipoPresentacion"));
		partidaInspeccion.setPaisProveedor(rs.getString("Pais"));
		partidaInspeccion.setEsImportado(rs.getBoolean("EsImportado"));
		partidaInspeccion.setAceptaParciales(rs.getInt("Parciales"));
		partidaInspeccion.setNombreCliente(rs.getString("Nombre"));
		partidaInspeccion.setIdCliente(rs.getInt("clave"));
		partidaInspeccion.setFechaEstimadaEntrega(rs.getDate("FPEntrega"));
		partidaInspeccion.setRuta(rs.getString("Ruta"));
		partidaInspeccion.setZonaMensajeria(rs.getString("ZonaMensajeria"));
//		partidaInspeccion.setFrecuencia(rs.getString("Frecuencia"));
		partidaInspeccion.setDiasRestantes(rs.getInt("DiasRestantes"));
		partidaInspeccion.setPrioridad(rs.getString("Prioridad"));
//		partidaInspeccion.setPrioridadControl(rs.getString("PrioridadControl"));
//		partidaInspeccion.setPrioridadFEE(rs.getString("PrioridadFEE"));
//		partidaInspeccion.setPrioridadRDestino(rs.getString("PrioridadRDestino"));
//		partidaInspeccion.setPrioridadRParciales(rs.getString("PrioridadRParciales"));
//		partidaInspeccion.setPrioridadManejo(rs.getString("PrioridadManejo"));
//		partidaInspeccion.setPrioridadUDArribo(rs.getString("PrioridadUDArribo"));
		partidaInspeccion.setPuntos(rs.getString("Puntos"));
//		partidaInspeccion.setPuntosControl(rs.getInt("PuntosControl"));
		partidaInspeccion.setPuntosFEE(rs.getInt("PuntosFEE"));
//		partidaInspeccion.setPuntosRDestino(rs.getInt("PuntosRDestino"));
		partidaInspeccion.setPuntosRParciales(rs.getInt("PuntosRParciales"));
//		partidaInspeccion.setPuntosManejo(rs.getInt("PuntosManejo"));
//		partidaInspeccion.setPuntosUDArribo(rs.getInt("PuntosUDArribo"));
//		partidaInspeccion.setCriterio(rs.getString("Criterio"));
		partidaInspeccion.setTipoPartida(rs.getString("TipoPartida"));
		
		
		if (partidaInspeccion.getTipo() != null && partidaInspeccion.getTipo().equalsIgnoreCase("publicaciones")) {
			partidaInspeccion.setManejo("Ambiente");
		}

		try {
			partidaInspeccion.setFea(rs.getDate("FEA"));
		} catch (Exception e) {

		}

		try {
			partidaInspeccion.setUbicacion(rs.getString("Ubicacion"));
		} catch (Exception e) {

		}
		try {
			partidaInspeccion.setReferenciaOC(rs.getInt("Ref_OC"));
		} catch (Exception e) {

		}
		try {
			partidaInspeccion.setReferenciaFact(rs.getInt("Ref_Fact"));
		} catch (Exception e) {

		}

		try {
			partidaInspeccion.setIdMarca(rs.getLong("idFabricante"));

		} catch (Exception e) {

		}

		try {
			partidaInspeccion.setPartidaPedido(rs.getInt("Part"));
		} catch (Exception e) {

		}

		try {
			partidaInspeccion.setIdFabricante(rs.getString("idFabricante"));
			partidaInspeccion.setNombreProveedor(rs.getString("Proveedor"));
			partidaInspeccion.setIdProveedor(rs.getInt("idProveedor"));
			partidaInspeccion.setSds(rs.getString("SDS"));
			partidaInspeccion.setDocumentacion(rs.getString("Documentacion"));

			partidaInspeccion.setPuntosRuta(rs.getInt("PuntosRuta"));
			partidaInspeccion.setPrioridadRuta(rs.getString("PrioridadRuta"));
		} catch (Exception e) {
		}

		try {
			partidaInspeccion.setIdPartidaInspeccion(rs.getInt("idInspeccionOC"));
		} catch (Exception e) {
		}
		try {
			partidaInspeccion.setCantidad(rs.getString("cantidadProd")); // Se agrego 
			partidaInspeccion.setUnidad(rs.getString("unidadProd")); // Se agrego
		} catch (Exception e) {
		}
		return partidaInspeccion;
	}
}
