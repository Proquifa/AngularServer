/**
 * 
 */
package com.proquifa.net.persistencia.catalogos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.catalogos.proveedores.ConfiguracionPrecioProducto;
import com.proquifa.net.modelo.catalogos.proveedores.CostoFactor;
import com.proquifa.net.modelo.comun.Producto;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author orosales
 *
 */
public class ConfiguracionPrecioClienteCostoRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		ConfiguracionPrecioProducto configCompleta = new ConfiguracionPrecioProducto();
		configCompleta.setIdConfiguracion(rs.getLong("PK_Configuracion_Precio"));
		configCompleta.setNivel(rs.getString("Nivel"));
		configCompleta.setIdProveedor(rs.getLong("idProveedor"));
		configCompleta.setPorciento(rs.getDouble("Porciento"));
		configCompleta.setIdConfiguracionFamilia(rs.getLong("idConfigFamilia"));
		configCompleta.setIdConfiguracionCosto(rs.getLong("FK03_ConfCosto"));			// es necesario para que vista decida si traer una Configuracion de Familia o una Configuracion de Costo
//		configCompleta.setFua(rs.getTimestamp("FUA"));
//		configCompleta.setIdConfiguracionPrecioProducto(rs.getLong("PK_ConfigPrecio_Producto"));
		configCompleta.setRestablecer(rs.getBoolean("Restablecer"));
		configCompleta.setRestablecerCosto(rs.getBoolean("Restablece_Costo"));
		
		try{
			configCompleta.setRestablecerTemporal(rs.getBoolean("RestablecerTemporal"));
			configCompleta.setRestablecerCostoTemporal(rs.getBoolean("Restablece_CostoTemporal")); 
		}catch(Exception e){
			
		}
		
		configCompleta.setCostoCompra(rs.getDouble("costoCompra"));
		configCompleta.setCantProductos(rs.getInt("totalProd"));
		try{
			configCompleta.setRutaCliente(rs.getString("RutaCliente"));
			configCompleta.setCompuestaCostoF(rs.getBoolean("CompuestaCostoF"));
			configCompleta.setCompuestaFactorU(rs.getBoolean("CompuestaFactorU"));
		}catch(Exception e){
		}
		Producto producto = new Producto();
		producto.setIdProducto(rs.getLong("idProducto"));
		
		producto.setCosto(rs.getDouble("Costo"));
		producto.setMoneda(rs.getString("MonedaVenta"));
		
		producto.setConfiguracion_Precio(rs.getLong("ConfiguracionPrecio"));
		producto.setCategoriaPrecioLista(rs.getLong("PrecioLista"));
		
		producto.setLicencia(rs.getString("Licencia"));
		
		CostoFactor costos = new CostoFactor();
		costos.setIdCostoFactor(rs.getLong("idCostoFactor"));
		
		costos.setCostoConsularizacion(rs.getDouble("Costo_Consularizacion"));
		costos.setFleteDocumentacion(rs.getDouble("Flete_Documentacion"));
		costos.setFactorIGI(rs.getDouble("Factor_IGI")); 
		costos.setFactorCostoFijo(rs.getDouble("Factor_CostoFijo"));
		costos.setFactorDistribuidor(rs.getDouble("Distribuidor"));
		costos.setFactorPublico(rs.getDouble("Publico"));
		costos.setPermiso(rs.getDouble("Permiso"));
		costos.setAlmacenDestino(rs.getDouble("AlmacenDestino"));
		costos.setFactorDTA(rs.getDouble("FactorDTA"));
		costos.setFactorValorEnAduana(rs.getDouble("VALORENADUANA"));
		costos.setFactorDescuento(rs.getDouble("DESCUENTO"));
		costos.setFactorFletePC(rs.getDouble("FLETE"));
			

		costos.setCvCliente((rs.getDouble("CV")));
		costos.setVaCliente((rs.getDouble("VA")));
		costos.setCantidadCliente((double) Math.ceil((rs.getDouble("Num"))));
		costos.setDiferencialCliente(Math.rint(((rs.getDouble("DIFERENCIAL"))* 1e2) / 1e2));
		costos.setFactorNivelProveedor(rs.getDouble("Factor_orig"));
		
		costos.setFactorCliente(rs.getDouble("Factor"));
		costos.setIdClienteConfig(rs.getLong("idClienteConfig"));

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
		
		configCompleta.setCostoFactorProducto(costos);
		configCompleta.setProducto(producto);
		
		return configCompleta;
	}
}