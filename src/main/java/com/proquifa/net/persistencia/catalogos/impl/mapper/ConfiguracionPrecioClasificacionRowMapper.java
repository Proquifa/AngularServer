package com.proquifa.net.persistencia.catalogos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.catalogos.proveedores.ConfiguracionPrecioProducto;
import com.proquifa.net.modelo.catalogos.proveedores.CostoFactor;
import com.proquifa.net.modelo.comun.Producto;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author viviana.romero
 *
 */
public class ConfiguracionPrecioClasificacionRowMapper implements RowMapper {
	
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		ConfiguracionPrecioProducto configCompleta = new ConfiguracionPrecioProducto();
		configCompleta.setIdConfiguracion(rs.getLong("PK_Configuracion_Precio"));

		configCompleta.setIdProveedor(rs.getLong("FK01_Proveedor"));
		configCompleta.setPorciento(rs.getDouble("Porciento"));
		configCompleta.setIdConfiguracionFamilia(rs.getLong("idConfigFamilia"));
		configCompleta.setIdConfiguracionClasificacion(rs.getLong("PK_Configuracion_Precio"));
		configCompleta.setCostoCompra(rs.getDouble("costoCompra"));
		configCompleta.setCantProductos(rs.getInt("CANT_PROD"));
		configCompleta.setCompuestaCostoF(rs.getBoolean("CompuestaCostoF"));
		configCompleta.setCompuestaFactorU(rs.getBoolean("CompuestaFactorU"));

		Producto producto = new Producto();
		producto.setIdProducto(rs.getLong("idProducto"));

		producto.setCosto(rs.getDouble("Costo"));
		producto.setMoneda(rs.getString("MonedaVenta"));

		producto.setCategoriaPrecioLista(rs.getLong("PrecioLista"));
		producto.setIndustria(rs.getString("Industria"));
		producto.setLicencia(rs.getString("Licencia"));
		
		CostoFactor costos = new CostoFactor();
		costos.setIdCostoFactor(rs.getLong("idCostoFactor"));

		costos.setStockDisable(rs.getBoolean("Stock_Disable"));
		costos.setFleteExpressDisable(rs.getBoolean("FleteExpress_Disable"));
		costos.setPiezas(rs.getInt("Piezas"));
			
		costos.setFactorCostoFijo(rs.getDouble("Factor_CostoFijo"));


		costos.setFactor_AAplus(rs.getDouble("factor_AAplus"));
		costos.setFactor_AA(rs.getDouble("factor_AA"));
		costos.setFactor_AM(rs.getDouble("factor_AM"));
		costos.setFactor_AB(rs.getDouble("factor_AB"));
		costos.setFactor_MA(rs.getDouble("factor_MA"));
		costos.setFactor_MM(rs.getDouble("factor_MM"));
		costos.setFactor_MB(rs.getDouble("factor_MB"));
		costos.setFactor_Bajo(rs.getDouble("factor_Bajo"));
		costos.setFactor_FExpress(rs.getDouble("factor_FExpress"));
		costos.setFactor_Stock(rs.getDouble("factor_Stock"));
		costos.setFactorDistribuidor(rs.getDouble("Distribuidor"));

		// Valor - Precio de Lista con descuento
		costos.setValor(rs.getDouble("VA"));	
		// CV -- Costo Venta
		costos.setCv(rs.getDouble("CV"));
		// Num -- CANTIDAD DE PRODUCTOS
		costos.setCantidad((double) Math.ceil((rs.getDouble("Num"))));
		// Obtiene la base para definir el Diferencial
		costos.setDiferencial(Math.rint(((rs.getDouble("DIFERENCIAL"))* 1e2) / 1e2));
		
		
		configCompleta.setCostoFactorProducto(costos);
		configCompleta.setProducto(producto);
		
		return configCompleta;
	}
}
