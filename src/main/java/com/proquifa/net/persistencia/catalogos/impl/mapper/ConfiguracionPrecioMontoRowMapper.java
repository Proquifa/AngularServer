package com.proquifa.net.persistencia.catalogos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.catalogos.proveedores.ConfiguracionPrecioProducto;
import com.proquifa.net.modelo.catalogos.proveedores.CostoFactor;
import com.proquifa.net.modelo.catalogos.proveedores.TiempoEntrega;
import com.proquifa.net.modelo.comun.Producto;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author orosales
 *
 */
public class ConfiguracionPrecioMontoRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		ConfiguracionPrecioProducto configCompleta = new ConfiguracionPrecioProducto();
		configCompleta.setIdConfiguracion(rs.getLong("PK_Configuracion_Precio"));
		configCompleta.setNivel(rs.getString("Nivel"));
		configCompleta.setIdProveedor(rs.getLong("idProveedor"));
		configCompleta.setPorciento(rs.getDouble("Porciento"));
		configCompleta.setIdConfiguracionFamilia(rs.getLong("idConfigFamilia"));
		configCompleta.setIdConfiguracionCosto(rs.getLong("FK03_ConfCosto"));
		configCompleta.setFua(rs.getTimestamp("FUA"));
		configCompleta.setCostoCompra(rs.getDouble("costoCompra"));
		configCompleta.setCompuestaCostoF(rs.getBoolean("CompuestaCostoF"));
		configCompleta.setCompuestaFactorU(rs.getBoolean("CompuestaFactorU"));

		Producto producto = new Producto();
		producto.setIdProducto(rs.getLong("idProducto"));
		producto.setTipo(rs.getString("Tipo"));
		producto.setSubtipo(rs.getString("SubTipo"));
		producto.setControl(rs.getString("Control"));
		producto.setCosto(rs.getDouble("Costo"));
		producto.setMoneda(rs.getString("MonedaVenta"));
		producto.setCodigo(rs.getString("Codigo"));
		producto.setFabrica(rs.getString("Fabrica"));
		producto.setConfiguracion_Precio(rs.getLong("ConfiguracionPrecio"));
		producto.setCategoriaPrecioLista(rs.getLong("PrecioLista"));
		producto.setIndustria(rs.getString("Industria"));
		producto.setLicencia(rs.getString("Licencia"));	
		producto.setTransitoMandatorioMexico(rs.getBoolean("TransitoMandatorioMexico"));		

		CostoFactor costos = new CostoFactor();
		costos.setIdCostoFactor(rs.getLong("idCostoFactor"));
		costos.setCostoConsularizacion(rs.getDouble("Costo_Consularizacion"));
		costos.setFleteDocumentacion(rs.getDouble("Flete_Documentacion"));
		costos.setFactorIGI(rs.getDouble("Factor_IGI"));//rs.getDouble("")
		costos.setFactorCostoFijo(rs.getDouble("Factor_CostoFijo"));
		costos.setFactorDistribuidor(rs.getDouble("Distribuidor"));
		costos.setFactorPublico(rs.getDouble("Publico"));
		costos.setPermiso(rs.getDouble("Permiso"));
		costos.setAlmacenDestino(rs.getDouble("AlmacenDestino"));
		costos.setFactorDTA(rs.getDouble("FactorDTA"));
		costos.setStockDisable(rs.getBoolean("Stock_Disable"));
		costos.setFleteExpressDisable(rs.getBoolean("FleteExpress_Disable"));
		costos.setPiezas(rs.getInt("Piezas"));
		costos.setMontoLicencia(rs.getDouble("montoLicencia"));
		costos.setPorcentajeLicencia(rs.getDouble("PorcentajeLicencia"));

		// Valor - Precio de Lista con descuento
		costos.setValor(rs.getDouble("VA"));	
		// CV -- Costo Venta
		costos.setCv(rs.getDouble("CV"));
		// Num -- CANTIDAD DE PRODUCTOS
		costos.setCantidad((double) Math.ceil((rs.getDouble("Num"))));
		// Obtiene la base para definir el Diferencial
		costos.setDiferencial(Math.rint(((rs.getDouble("DIFERENCIAL"))* 1e2) / 1e2));
		
		
		costos.setFactor_AAplus(rs.getDouble("factor_AAplus"));
		costos.setFactor_AA(rs.getDouble("factor_AA"));
		costos.setFactor_AM(rs.getDouble("factor_AM"));
		costos.setFactor_AB(rs.getDouble("factor_AB"));
		costos.setFactor_MA(rs.getDouble("factor_MA"));
		costos.setFactor_MM(rs.getDouble("factor_MM"));
		costos.setFactor_MB(rs.getDouble("factor_MB"));
		costos.setFactor_Bajo(rs.getDouble("Factor_Bajo"));
		costos.setFactor_Stock(rs.getDouble("factor_Stock"));
		costos.setFactor_Comision(rs.getDouble("factor_Comision"));
		costos.setFactorValorEnAduana(rs.getDouble("VALORENADUANA"));
		costos.setFactorDescuento(rs.getDouble("DESCUENTO"));
		costos.setFactorFletePC(rs.getDouble("FLETE"));
		costos.setIdAgenteAduanal(rs.getLong("FK01_AgenteAduanal"));
		costos.setIdLugarAgenteAduanal(rs.getLong("FK02_LugarAgenteAduanal"));
		costos.setIdLugarConcepto(rs.getLong("FK03_LAAConcepto"));

		TiempoEntrega te = new TiempoEntrega();
		te.setIdTiempoEntrega(rs.getLong("idTEntrega"));
		te.setRequierePExiste(rs.getString("FK01_RequierePermiso_ExisTE"));
		te.setRequierePNoExiste(rs.getString("FK02_RequierePermiso_NoExisTE"));
		te.setRequierePNo(rs.getString("FK03_RequierePermiso_No"));
		te.setFleteExpress(rs.getString("FK04_FExpress"));

		configCompleta.setCostoFactorProducto(costos);
		configCompleta.setTentregaProducto(te);
		configCompleta.setProducto(producto);

		return configCompleta;

		// PRECIO UNITARIO
		//		costos.setPrecioUAAplus((double) Math.round (rs.getDouble("PrecioU_AAplus")));
		//		costos.setPrecioUAA((double) Math.round (rs.getDouble("PrecioU_AA")));
		//		costos.setPrecioUAM((double) Math.round(rs.getDouble("PrecioU_AM")));	
		//		costos.setPrecioUAB((double) Math.round(rs.getDouble("PrecioU_AB")));
		//		costos.setPrecioUMA((double) Math.round(rs.getDouble("PrecioU_MA")));
		//		costos.setPrecioUMM((double) Math.round(rs.getDouble("PrecioU_MM")));
		//		costos.setPrecioUMB((double) Math.round(rs.getDouble("PrecioU_MB")));
		//		costos.setPrecioUBajo((double) Math.round(rs.getDouble("PrecioU_Bajo")));
		//		costos.setPrecioUStock((double) Math.round(rs.getDouble("PrecioU_STOCK")));
		////		costos.setPrecioUPublico((double) Math.round(rs.getDouble("PrecioU_PUBLICO")));
		//		costos.setPrecioUDistribuidor((double) Math.round(rs.getDouble("PrecioU_DISTRI")));


		// Num -- CANTIDAD DE PRODUCTOS
		//		costos.setCantidadAAplus((double) Math.ceil((rs.getDouble("Num_AAplus"))));
		//		costos.setCantidadAAplus((double) Math.ceil((rs.getDouble("Num_AAplus"))));
		//		costos.setCantidadAA((double) Math.ceil((rs.getDouble("Num_AA"))));
		//		costos.setCantidadAM((double) Math.ceil((rs.getDouble("Num_AM"))));	
		//		costos.setCantidadAB((double) Math.ceil((rs.getDouble("Num_AB"))));
		//		costos.setCantidadMA((double) Math.ceil((rs.getDouble("Num_MA"))));
		//		costos.setCantidadMM((double) Math.ceil((rs.getDouble("Num_MM"))));
		//		costos.setCantidadMB((double) Math.ceil((rs.getDouble("Num_MB"))));
		//		costos.setCantidadBajo((double) Math.ceil((rs.getDouble("Num_Bajo"))));
		//		costos.setCantidadStock((double) Math.ceil((rs.getDouble("Num_STOCK"))));
		////		costos.setCantidadPublico((double) Math.ceil((rs.getDouble("Num_PUBLICO"))));
		//		costos.setCantidadDistribuidor((double) Math.ceil((rs.getDouble("Num_DISTRI"))));

		// DIFERENCIAL 
		//		costos.setDiferencialAAplus(rs.getDouble("DIFERENCIAL_AAplus"));
		//		costos.setDiferencialAAplus(rs.getDouble("DIFERENCIAL_AAplus"));
		//		costos.setDiferencialAA(rs.getDouble("DIFERENCIAL_AA"));
		//		costos.setDiferencialAM(rs.getDouble("DIFERENCIAL_AM"));	
		//		costos.setDiferencialAB(rs.getDouble("DIFERENCIAL_AB"))  * 1e2) / 1e2);
		//		costos.setDiferencialMA(rs.getDouble("DIFERENCIAL_MA"))  * 1e2) / 1e2);
		//		costos.setDiferencialMM(rs.getDouble("DIFERENCIAL_MM"));
		//		costos.setDiferencialMB(rs.getDouble("DIFERENCIAL_MB"));
		//		costos.setDiferencialBajo(rs.getDouble("DIFERENCIAL_Bajo"));
		//		costos.setDiferencialStock(rs.getDouble("DIFERENCIAL_STOCK"))  * 1e2) / 1e2);
		////		costos.setDiferencialPublico(rs.getDouble("DIFERENCIAL_PUBLICO"));
		//		costos.setDiferencialDistribuidor(rs.getDouble("DIFERENCIAL_DISTRI"));

		
	}
}
