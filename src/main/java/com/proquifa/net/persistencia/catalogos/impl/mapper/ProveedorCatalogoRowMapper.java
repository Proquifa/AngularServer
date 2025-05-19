package com.proquifa.net.persistencia.catalogos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.Proveedor;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author orosales
 *
 */
public class ProveedorCatalogoRowMapper implements RowMapper {

	/* (non-Javadoc) 
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Proveedor proveedor = new Proveedor();

		proveedor.setHabilitado(rs.getBoolean("habilitado"));
		proveedor.setNombre(rs.getString("Nombre"));
		proveedor.setIdProveedor(rs.getLong("clave"));
		proveedor.setObserva(rs.getString("Observa"));
		proveedor.setPais(rs.getString("Pais"));
		proveedor.setColocarPhs(rs.getBoolean("ColocarPhs"));
		proveedor.setRSCalle(rs.getString("RSCalle"));
		proveedor.setCalle(rs.getString("RSCalle"));


		proveedor.setDelegacion(rs.getString("RSDel"));
		proveedor.setEstado(rs.getString("Estado") );
		proveedor.setCp(rs.getString("CP"));
		proveedor.setRazonSocial(rs.getString("RSocial"));
		proveedor.setMoneda(rs.getString("Moneda"));
		proveedor.setCPago(rs.getString("cpago"));
		proveedor.setCheque(rs.getBoolean("cheque"));
		proveedor.setTransferencia(rs.getBoolean("transferencia"));
		proveedor.setTarjeta(rs.getBoolean("tarjeta"));
		proveedor.setDeposito(rs.getBoolean("deposito"));
		proveedor.setDescripcionAmpliada(rs.getString("descripcionAmpliada"));
		proveedor.setSegundaDireccion(rs.getString("Direccion2"));
		proveedor.setCiudad(rs.getString("Ciudad"));
		proveedor.setSocioComercial(rs.getBoolean("existeRelacionComercial"));
		proveedor.setImagen(rs.getString("imagen"));
		proveedor.setFechaActualizacion(rs.getTimestamp("FUActual"));
		proveedor.setFUAProductos(rs.getTimestamp("FUA_Productos"));
		proveedor.setMonedaCompra(rs.getString("moneda"));
		proveedor.setMonedaVenta(rs.getString("monedaventa"));
		proveedor.setTipoCambio(rs.getDouble("Tipo_Cambio"));
		proveedor.setFUATipoCambio(rs.getTimestamp("FUA_TipoCambio"));
		proveedor.setProductos(rs.getInt("PRODUCTOS"));
		proveedor.setFamilias(rs.getInt("FAMILIAS"));
		proveedor.setPagador(rs.getLong("Pagador"));
		proveedor.setComprador(rs.getLong("FK01_Empleado"));
		proveedor.setObjectivoCrecimiento(rs.getString("ObjetivoCrecimiento"));
		proveedor.setRol(rs.getString("rol"));
		proveedor.setRanking(rs.getLong("ranking"));
		proveedor.setInspector(rs.getLong("FK03_Inspector"));
		proveedor.setObjetivoCrecimientoFundamental(rs.getString("objetivoCrecimientoFundamental"));
		proveedor.setMesInicioFiscal(rs.getInt("MesInicioFiscal"));
		try {proveedor.setTaxId(rs.getString("TaxID"));} catch (Exception e) {}

		try {
			proveedor.setPagina(rs.getString("Pagina"));
			proveedor.setPassword(rs.getString("Contraseña"));
			proveedor.setUsuario(rs.getString("Usuario"));
			proveedor.setPrecioWeb(rs.getBoolean("PrecioWeb"));
			proveedor.setCompraLinea(rs.getBoolean("CompraLinea"));
		} catch (Exception e) {
		}

		return proveedor;
	}

}
