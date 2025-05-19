package com.proquifa.net.persistencia.despachos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.proquifa.net.modelo.despachos.PartidaInspeccion;
import org.springframework.jdbc.core.RowMapper;

public class PartidaInspeccionRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {

		PartidaInspeccion obj = new PartidaInspeccion();

		String anoCaducidad = "", mesCaducidad = "", edicion = "", manejo = "";

		String estadoRD = "", estadiRI = "";

		Boolean rxd = false, rxi = false;

		Boolean rPieza, rCaducidad, rLote, rDoc;

		Integer numPiezas = 0;

		obj.setInspector(rs.getString("Inspector"));

		obj.setIdPartidaCompra(rs.getLong("idPCompra"));

		obj.setCompra(rs.getString("Compra"));

		obj.setPedidoFacturadoPor(rs.getString("FacturadoPor"));

		obj.setPartida(rs.getInt("PartidaPCompra"));

		obj.setIdPPedido(rs.getLong("idPPedido"));

		obj.setPedido(rs.getString("CPedido"));

		obj.setPartidaPedido(rs.getInt("PartidaPPedido"));

		obj.setParciales(rs.getString("Parciales"));

		obj.setIdProducto(rs.getLong("idProducto"));

		obj.setTipo(rs.getString("TipoProducto"));

		obj.setControl(rs.getString("Control"));

		obj.setPresentacion(rs.getString("Presentacion"));

		obj.setDocumento(rs.getString("Documento"));

		obj.setTipoDocumento(rs.getString("TipoDocumento"));

		obj.setLote(rs.getString("Lote"));

		mesCaducidad = rs.getString("MesCaducidad");

		anoCaducidad = rs.getString("AnoCaducidad");

		if (anoCaducidad.equals("") || mesCaducidad.equals("") ||

				anoCaducidad.equals("--NA--") || mesCaducidad.equals("--NA--") ||

				anoCaducidad.equals("No especificado") || mesCaducidad.equals("No especificado")) {

			obj.setCaducidad("No especificado");

		} else {

			obj.setCaducidad(mesCaducidad + "-" + anoCaducidad);

		}

		edicion = rs.getString("Edicion");

		if (edicion.equals("")) {

			obj.setEdicion("No especificado");

		} else {

			obj.setEdicion(edicion);

		}

		obj.setOrigen(rs.getString("Origen"));

		rxd = rs.getBoolean("RechazoXDoc");

		rxi = rs.getBoolean("RechazoXIns");

		obj.setRechazoXDoc(rxd);

		obj.setRechazoXInsp(rxi);

		estadoRD = rs.getString("EstadoRechazoXDoc");

		estadiRI = rs.getString("EstadoRechazoXIns");

		if (rxd) {

			if (estadoRD.equals("Abierto")) {

				obj.setEstadoRechazo(estadoRD);

			} else {

				if (rxi) {

					if (estadiRI.equals("Abierto")) {

						obj.setEstadoRechazo(estadiRI);

					} else {

						obj.setEstadoRechazo("Cerrado");

					}

				} else {

					obj.setEstadoRechazo("Cerrado");

				}

			}

		} else if (rxi) {

			if (estadiRI.equals("Abierto")) {

				obj.setEstadoRechazo(estadiRI);

			} else {

				if (rxd) {

					if (estadoRD.equals("Abierto")) {

						obj.setEstadoRechazo(estadoRD);

					} else {

						obj.setEstadoRechazo("Cerrado");

					}

				} else {

					obj.setEstadoRechazo("Cerrado");

				}

			}

		} else {

			obj.setEstadoRechazo("Cerrado");

		}

		obj.setCantidadCompra(rs.getInt("Cant"));

		manejo = rs.getString("Manejo");

		if (manejo == null || manejo.equals("--NA--")) {

			obj.setManejo("NA");

		} else {

			obj.setManejo(manejo);

		}

		obj.setIdProveedor(rs.getInt("idProveedor"));

		obj.setNombreProveedor(rs.getString("Proveedor"));

		obj.setPaisProveedor(rs.getString("PaisProveedor"));

		obj.setNombreCliente(rs.getString("Cliente"));

		obj.setCodigo(rs.getString("Codigo"));

		obj.setFabrica(rs.getString("Fabrica"));

		obj.setIdComplemento(rs.getLong("idComplemento"));

		obj.setFechaEstimadaEntrega(rs.getTimestamp("FEE"));

		obj.setTipoFlete(rs.getString("TipoFlete"));

		obj.setNumPartidasProgramadas(rs.getInt("partidasProgramadas"));

		obj.setNumPartidasRegulares(rs.getInt("partidasRegulares"));

		obj.setNumPartidasFEx(rs.getInt("partidasFE"));

		rPieza = rs.getBoolean("revisoPieza");

		rCaducidad = rs.getBoolean("revisoCaducidad");

		rLote = rs.getBoolean("revisoLote");

		rDoc = rs.getBoolean("revisoDocumento");

		numPiezas = rs.getInt("partidasEnPieza");

		obj.setRevisoNumPiezas(rPieza);

		obj.setRevisoCaducidad(rCaducidad);

		obj.setRevisoLote(rLote);

		obj.setRevisoDoc(rDoc);

		if (numPiezas != 0) {

			obj.setInsertoPiezas(true);

		} else {

			obj.setInsertoPiezas(false);

		}

		if (rPieza == true || rCaducidad == true || rLote == true || rDoc == true) {

			obj.setEditoPartida(true);

		} else {

			obj.setEditoPartida(false);

		}

		return obj;

	}

	
}