package com.proquifa.net.persistencia.catalogos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.proquifa.net.modelo.catalogos.proveedores.ConfiguracionPrecioProducto;
import com.proquifa.net.modelo.catalogos.proveedores.CostoFactor;
import com.proquifa.net.modelo.catalogos.proveedores.TiempoEntrega;
import com.proquifa.net.modelo.comun.Producto;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author orosales
 *
 */
public class ConfiguracionPrecioMontoClienteRowMapper implements RowMapper {
	
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		ConfiguracionPrecioProducto configCompleta = new ConfiguracionPrecioProducto();
		configCompleta.setIdConfiguracion(rs.getLong("PK_Configuracion_Precio"));
		configCompleta.setNivel(rs.getString("Nivel"));
		configCompleta.setIdProveedor(rs.getLong("idProveedor"));
		configCompleta.setPorciento(rs.getDouble("Porciento"));
		configCompleta.setIdConfiguracionFamilia(rs.getLong("idConfigFamilia"));
		configCompleta.setIdConfiguracionCosto(rs.getLong("FK03_ConfCosto"));
		configCompleta.setFua(rs.getTimestamp("FUA"));
		configCompleta.setIdConfiguracionPrecioProducto(rs.getLong("PK_ConfigPrecio_Producto"));
	
		configCompleta.setRestablecer(rs.getBoolean("Restablecer"));
		configCompleta.setRestablecerCosto(rs.getBoolean("Restablece_Costo"));
		
		try{
			configCompleta.setRestablecerTemporal(rs.getBoolean("RestablecerTemporal"));
			configCompleta.setRestablecerCostoTemporal(rs.getBoolean("Restablece_CostoTemporal")); 
		}catch(Exception e){
			
		}
		
		configCompleta.setCostoCompra(rs.getDouble("costoCompra"));
		configCompleta.setNivelConfiguracionCliente(rs.getString("NivelClienteConf"));
	
		
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
		producto.setLicencia(rs.getString("Licencia"));
		producto.setPdolar(rs.getDouble("PDolar"));
		producto.setEdolar(rs.getDouble("EDolar"));
		producto.setTipoPresentacion(rs.getString("tipoPresentacion"));
		producto.setOrigen(rs.getString("Origen"));
		try{
		producto.setProveedor(rs.getLong("idProveedor"));
		}
		catch(Exception e){
		}
		
		try{
			Integer idFabricante = Integer.parseInt(rs.getString("FK02_Fabricante"));
			producto.setIdFabricante(idFabricante.toString());
		}catch(Exception e){
		}
		
		try{
			producto.setCas(rs.getString("CAS"));
			}
			catch(Exception e){
			}
		
		try{
			producto.setLote(rs.getString("Lote"));
			}
			catch(Exception e){
			}
		CostoFactor costos = new CostoFactor();
		costos.setIdCostoFactor(rs.getLong("idCostoFactor"));
		
		costos.setCostoConsularizacion(rs.getDouble("Costo_Consularizacion"));
		costos.setFleteDocumentacion(rs.getDouble("Flete_Documentacion"));
		costos.setFactorIGI(rs.getDouble("Factor_IGI")); 
		costos.setFactorCostoFijo(rs.getDouble("Factor_CostoFijo"));
//		costos.setFactorUtilidad(rs.getDouble("Factor_Utilidad");
		costos.setFactorDistribuidor(rs.getDouble("Distribuidor"));
		costos.setFactorPublico(rs.getDouble("Publico"));
		costos.setPermiso(rs.getDouble("Permiso"));
		costos.setAlmacenDestino(rs.getDouble("AlmacenDestino"));
		costos.setFactorDTA(rs.getDouble("FactorDTA"));
//		costos.setHonorarios(rs.getDouble("Honorarios");
		costos.setFactorValorEnAduana(rs.getDouble("VALORENADUANA"));
		costos.setFactorDescuento(rs.getDouble("DESCUENTO"));
		costos.setFactorFletePC(rs.getDouble("FLETE"));
			
//		costos.setMontoCliente(rs.getDouble("MONTO"));
//		costos.setPrecioUCliente((double) Math.round(rs.getDouble("PrecioU")));
		costos.setCvCliente((double) Math.round(rs.getDouble("CV")));
		costos.setVaCliente((double) Math.round(rs.getDouble("VA")));
		costos.setCantidadCliente(rs.getDouble("Num"));
		costos.setDiferencialCliente(Math.rint(((rs.getDouble("DIFERENCIAL"))* 1e2) / 1e2));
		costos.setFactorNivelProveedor(rs.getDouble("Factor_orig"));
		
		costos.setFactorCliente(rs.getDouble("Factor"));
		costos.setIdClienteConfig(rs.getLong("idClienteConfig"));
		costos.setIdAgenteAduanal(rs.getLong("FK01_AgenteAduanal"));
		costos.setIdLugarAgenteAduanal(rs.getLong("FK02_LugarAgenteAduanal"));
		costos.setIdLugarConcepto(rs.getLong("FK03_LAAConcepto"));
		
		try{
			costos.setFactorTempCliente(rs.getDouble("FactorTemp"));
			costos.setFactorTempCostoFijo(rs.getDouble("Factor_TempCostoFijo"));
			if(rs.getTimestamp("Caduca") != null){
				costos.setCaduca(rs.getTimestamp("Caduca"));
			}
			configCompleta.setCompuestaCostoFTemporal(rs.getBoolean("CompuestaCostoFTemp"));
			configCompleta.setCompuestaFactorUTemporal(rs.getBoolean("CompuestaFactorUTemp"));
		}catch(Exception e){
			
		}
	
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
