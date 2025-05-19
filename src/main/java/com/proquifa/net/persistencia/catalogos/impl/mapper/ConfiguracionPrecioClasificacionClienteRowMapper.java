/**
 * 
 */
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
public class ConfiguracionPrecioClasificacionClienteRowMapper implements RowMapper {

	/***
	 * Este RowMapper se utiliza en dos metodos findConfiguracionClasificacionCliente	y 	findClasificacionPrecioProductoPorCatPrecioCliente
	 */

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		ConfiguracionPrecioProducto configCompleta = new ConfiguracionPrecioProducto();
		configCompleta.setIdConfiguracion(rs.getLong("PK_Configuracion_Precio"));
		configCompleta.setIdConfiguracionCosto(rs.getLong("IDConfCosto"));
		configCompleta.setNivel(rs.getString("Nivel"));
		configCompleta.setIdProveedor(rs.getLong("idProveedor"));
		configCompleta.setPorciento(rs.getDouble("Porciento"));
		configCompleta.setIdConfiguracionFamilia(rs.getLong("idConfigFamilia"));
		if(rs.getString("FUA") != null)
			configCompleta.setFua(rs.getTimestamp("FUA"));
		
		configCompleta.setIdConfiguracionPrecioProducto(rs.getLong("PK_ConfigPrecio_Producto"));
		
		configCompleta.setRestablecer(rs.getBoolean("Restablecer"));
		configCompleta.setRestablecerCosto(rs.getBoolean("RESTABLECER_COSTO"));
		try{
			configCompleta.setRestablecerTemporal(rs.getBoolean("RestablecerTemporal"));
			configCompleta.setRestablecerCostoTemporal(rs.getBoolean("Restablece_CostoTemporal")); 
		}catch(Exception e){
			
		}
		configCompleta.setCostoCompra(rs.getDouble("costoCompra"));
		configCompleta.setIdClasificacion(rs.getLong("idClasificacion"));
		try{
			configCompleta.setRutaCliente(rs.getString("RutaCliente"));
			configCompleta.setCompuestaCostoF(rs.getBoolean("CompuestaCostoF"));
			configCompleta.setCompuestaFactorU(rs.getBoolean("CompuestaFactorU"));
		}catch(Exception e){
		}
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
		producto.setLicencia(rs.getString("DepositarioInternacional"));


		CostoFactor costos = new CostoFactor();
		costos.setIdCostoFactor(rs.getLong("idCostoFactor"));
		costos.setCostoConsularizacion(rs.getDouble("Costo_Consularizacion"));
		costos.setFleteDocumentacion(rs.getDouble("Flete_Documentacion"));
		costos.setFactorIGI(rs.getDouble("Factor_IGI"));
		costos.setFactorCostoFijo(rs.getDouble("Factor_CostoFijo"));
		//		costos.setFactorUtilidad(rs.getDouble("Factor_Utilidad"));
		costos.setFactorDistribuidor(rs.getDouble("Distribuidor"));
		costos.setFactorPublico(rs.getDouble("Publico"));
		costos.setPermiso(rs.getDouble("Permiso"));
		costos.setAlmacenDestino(rs.getDouble("AlmacenDestino"));
		costos.setFactorDTA(rs.getDouble("FactorDTA"));
		//		costos.setHonorarios(rs.getDouble("Honorarios"));
		costos.setFactor_Comision(rs.getDouble("factor_Comision"));
		costos.setFactorValorEnAduana(rs.getDouble("VALORENADUANA"));
		costos.setFactorDescuento(rs.getDouble("DESCUENTO"));
		costos.setFactorFletePC(rs.getDouble("FLETE"));
		costos.setFactor_AA(rs.getDouble("factor_AA"));
		costos.setFactor_AM(rs.getDouble("factor_AM"));
		costos.setFactor_AB(rs.getDouble("factor_AB"));
		costos.setFactor_MA(rs.getDouble("factor_MA"));
		costos.setFactor_MM(rs.getDouble("factor_MM"));
		costos.setFactor_MB(rs.getDouble("factor_MB"));
		costos.setFactor_Bajo(rs.getDouble("factor_Bajo"));
		costos.setFactor_FExpress(rs.getDouble("factor_FExpress"));
		costos.setFactor_Stock(rs.getDouble("factor_Stock"));
		costos.setFactorCliente(rs.getDouble("Factor"));
		costos.setStockDisable(rs.getBoolean("Stock_Disable"));
		costos.setFleteExpressDisable(rs.getBoolean("FleteExpress_Disable"));
		costos.setPiezas(rs.getInt("Piezas"));

		costos.setIdAgenteAduanal(rs.getLong("FK01_AgenteAduanal"));
		costos.setIdLugarAgenteAduanal(rs.getLong("FK02_LugarAgenteAduanal"));
		costos.setIdLugarConcepto(rs.getLong("FK03_LAAConcepto"));

		costos.setClienteAgente(rs.getBoolean("CLI_AA"));
		costos.setClienteLugar(rs.getBoolean("CLI_LAA"));
		costos.setClienteConcepto(rs.getBoolean("CLI_LAAC"));

		//		costos.setPrecioUCliente(rs.getDouble("PrecioU"));
		try{
			costos.setCantidadCliente((double) Math.ceil(rs.getDouble("Num")));
			costos.setCvCliente(rs.getDouble("CV"));
			costos.setVaCliente(rs.getDouble("VA"));
		}catch(Exception e){

		}
		
		try{
			costos.setFactorTempCliente(rs.getDouble("FactorTemp"));
			costos.setFactorTempCostoFijo(rs.getDouble("CostoFijoTemp"));
			if(rs.getTimestamp("Caduca") != null){
				costos.setCaduca(rs.getTimestamp("Caduca"));
			}
			configCompleta.setCompuestaCostoFTemporal(rs.getBoolean("CompuestaCostoFTemp"));
			configCompleta.setCompuestaFactorUTemporal(rs.getBoolean("CompuestaFactorUTemp"));
		}catch(Exception e){
			
		}
		costos.setDiferencialCliente(Math.rint(((rs.getDouble("DIFERENCIAL"))* 1e2) / 1e2));


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
	}
}