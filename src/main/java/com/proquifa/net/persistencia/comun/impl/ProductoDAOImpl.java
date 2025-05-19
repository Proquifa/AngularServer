
package com.proquifa.net.persistencia.comun.impl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.catalogos.proveedores.ConfiguracionPrecioProducto;
import com.proquifa.net.modelo.comun.Producto;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.comun.util.documentoXls.ProductoMicrobiologics;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.comun.ProductoDAO;
import com.proquifa.net.persistencia.comun.impl.mapper.ProductoDescripcionRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.ProductoRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.ProductosUSPDescargaDocumentosRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.ReporteRowMapper;




/**
 * @author amartinez
 *
 */
@Repository
public class ProductoDAOImpl extends DataBaseDAO implements ProductoDAO {

	Funcion funcion;
	
	final Logger log = LoggerFactory.getLogger(ProductoDAOImpl.class);
	
	public Producto obtenerProducto (String codigo, String fabrica){
		String query = "";
	  try{	
		  Map<String, Object> map = new HashMap<String, Object>();
			map.put("codigo",codigo);
			map.put("fabrica",fabrica);
		query = "SELECT TOP 1 * FROM productos WHERE codigo=:codigo AND fabrica= :fabrica ORDER BY idProducto DESC";
		return (Producto)super.jdbcTemplate.queryForObject(query,map, new ProductoRowMapper());
  	  }catch(RuntimeException rte){
  		funcion = new Funcion();
		funcion.enviarCorreoAvisoExepcion(rte,"query: " + query,"codigo: " + codigo, "fabrica: " + fabrica);
		return new Producto();
	  }
	}

	public String obtenerDescripcionProducto(String codigo, String fabrica) {
		String query = "";
		try{	
			
			  Map<String, Object> map = new HashMap<String, Object>();
				map.put("codigo",codigo);
				map.put("fabrica",fabrica);
				
			Funcion funcion = new Funcion();
			String descripcion = "";
			query = "SELECT TOP 1 Tipo,Fabrica,Codigo,Concepto,Pureza,Cantidad,Unidad,TEntrega, proveedor  FROM Productos WHERE Codigo= :codigo AND Fabrica= :fabrica  ORDER BY idProducto DESC";
			Producto producto = (Producto) super.jdbcTemplate.queryForObject(query, map, new ProductoDescripcionRowMapper());
			descripcion = funcion.obtenerDescripcionProducto(producto);
			return descripcion;
		}catch(RuntimeException rte){		
//			funcion = new Funcion();
//			funcion.enviarCorreoAvisoExepcion(rte,"query: " + query,"codigo: " + codigo, "fabrica: " + fabrica);
//			return "";
			return null;
		}
	}
	
	
	public String obtenerDescripcionProductoPorId(Long idProducto) {
		String query = "";
		try{	
			  Map<String, Object> map = new HashMap<String, Object>();
					map.put("idProducto",idProducto);
					
			Funcion funcion = new Funcion();
			String descripcion = "";
			query = "SELECT TOP 1 Tipo,Fabrica,Codigo,Concepto,Pureza,Cantidad,Unidad,TEntrega, proveedor  FROM Productos WHERE idProducto= :idProducto  ORDER BY idProducto DESC";
			Producto producto = (Producto) super.jdbcTemplate.queryForObject(query,map,  new ProductoDescripcionRowMapper());
			descripcion = funcion.obtenerDescripcionProducto(producto);
			return descripcion;
		}catch(RuntimeException rte){		
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(rte,"query: " + query);
			return "";
		}
	}
	
	public String obtenerDescripcionProductoComplemento(Long idComplemento) {
		String query = "";
		try{	
			  Map<String, Object> map = new HashMap<String, Object>();
				map.put("idComplemento",idComplemento);
				
			String descripcion = "";
			query = "SELECT Descripcion FROM Complemento WHERE idComplemento = :idComplemento";
			 descripcion = (String) super.jdbcTemplate.queryForObject(query,map, String.class);
			
			return descripcion;
	  	  }catch(RuntimeException rte){	
	  	  //  logger.error(rte.getMessage());
	  		funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(rte,"query: " + query,"idComplemento: " + idComplemento);
			return "";
		  }
	}

	@SuppressWarnings("unchecked")
	public List<Producto> obtenerProductosXProveedor(Long idProveedor,
			String periodo) {
		String query = "";
		try {
			  Map<String, Object> map = new HashMap<String, Object>();
				map.put("idProveedor",idProveedor);
				map.put("periodo",periodo);
			
			query = "select PPedidos.Fabrica, PPedidos.Codigo, SUM(ppedidos.cant) as cantidad, SUM(Cant*Precio) as total from PPedidos, Pedidos " +
					"where ppedidos.CPedido = pedidos.CPedido and ppedidos.Provee = :idProveedor and pedidos.FPedido >= :periodo  and ppedidos.Estado <> 'cancelado' " +
					"and ppedidos.Fabrica is not null and ppedidos.Codigo is not null and ppedidos.idComplemento = 0 group by ppedidos.Fabrica, ppedidos.Codigo order by cantidad desc";
			return super.jdbcTemplate.query(query,map, new ReporteRowMapper());
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"query: " + query,"idProveedor: " + idProveedor, "periodo: " + periodo);
			return new ArrayList<Producto>();
		}
	}

	public Producto obtenerProductoXId(Long idProducto)
			throws ProquifaNetException {
		try {
			  Map<String, Object> map = new HashMap<String, Object>();
					map.put("idProducto",idProducto);
			return (Producto) super.jdbcTemplate.queryForObject("SELECT * FROM Productos WHERE idProducto = :idProducto", map, new ProductoRowMapper());
		} catch (RuntimeException e) {
			//logger.error("Erro: " + e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"idProducto: " + idProducto);
			return new Producto();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Producto> obtenerProductosXMarca(Long idFabricante) throws ProquifaNetException {
		try {
			  Map<String, Object> map = new HashMap<String, Object>();
				map.put("idFabricante",idFabricante);
			return super.jdbcTemplate.query("SELECT P.* FROM Productos AS P,Fabricantes AS F WHERE P.Fabrica=F.Nombre AND F.idFabricante= :idFabricante",map, new ProductoRowMapper()); 
		} catch (Exception e) {
		//	logger.error("Error: " + e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"idFabricante: " + idFabricante);
			return new ArrayList<Producto>();
		}
	}
	

	public Boolean actualizarProducto(Producto prod)
			throws ProquifaNetException {
		try {		
			  Map<String, Object> map = new HashMap<String, Object>();
					map.put("Documentacion",prod.getDocumentacion());
					map.put("Moneda",prod.getMoneda());
					map.put("Descuento",prod.getDescuento());
					map.put("Concepto",prod.getConcepto());
					map.put("Proveedor",prod.getProveedor());
					map.put("Costo",prod.getCosto());
					map.put("CostoMinimo",prod.getCostoMinimo());
					map.put("Iva",prod.getIva());
					map.put("Cant",prod.getCant());
					map.put("Fecha",prod.getFecha());
					map.put("Vigente",prod.getVigente());
					map.put("Control",prod.getControl());
					map.put("Disponibilidad",prod.getDisponibilidad());
					map.put("TiempoEntrega",prod.getTiempoEntrega());
					map.put("Manejo",prod.getManejo());
					map.put("Tipo",prod.getTipo());
					map.put("PrecioFijo",prod.getPrecioFijo());
					map.put("Unidad",prod.getUnidad());
					map.put("Caduca",prod.getCaduca());
					map.put("TipoPresentacion",prod.getTipoPresentacion());
					map.put("Origen",prod.getOrigen());
					map.put("IdProducto",prod.getIdProducto());
			
					
		/*	Object[] params =  {prod.getDocumentacion(),prod.getMoneda(),prod.getDescuento(),prod.getConcepto(),prod.getProveedor(),prod.getCosto(),prod.getCostoMinimo(),prod.getIva(),
					prod.getCant(),prod.getFecha(),prod.getVigente(),prod.getControl(),prod.getDisponibilidad(),prod.getTiempoEntrega(),prod.getManejo(),prod.getTipo(),prod.getPrecioFijo(),
					prod.getUnidad(),prod.getCaduca(),prod.getTipoPresentacion(),prod.getOrigen(),prod.getIdProducto()};*/
			super.jdbcTemplate.update("UPDATE Productos SET Documentacion= :Documentacion,Moneda= :Moneda ,Descuento= :Descuento,Concepto= :Concepto,Proveedor= :Proveedor,Costo= :Costo,"+
					"CMinimo= :CostoMinimo,IVA= :Iva,Cantidad= :Cant ,Fecha= :Fecha,Vigente= :Vigente,Control= :Control,Disponibilidad= :Disponibilidad,TEntrega= :TiempoEntrega,Manejo= :Manejo,Tipo= :Tipo,PFijo= :PrecioFijo,"+
					"Unidad= :Unidad,Caduca= :Caduca,tipoPresentacion= :TipoPresentacion, origen= :Origen  WHERE idProducto= :IdProducto", map);
			return true;
		} catch (Exception e) {
		//	logger.error("Error: " + e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,prod);
			return false;
		}
	}
	
	@Override
	public boolean actualizarPrecioProductoPorCatalogo(long idProducto, String fechaCaducidad) throws ProquifaNetException {
		try {		
			
			  Map<String, Object> map = new HashMap<String, Object>();
				map.put("idProducto",idProducto);
				map.put("fechaCaducidad",fechaCaducidad);
//			log.info ("UPDATE Productos SET Fecha = getdate(), Caduca='"+ fechaCaducidad +"' WHERE idProducto = " + idProducto);
			super.jdbcTemplate.update("UPDATE Productos SET Fecha = getdate(), Caduca= :fechaCaducidad  WHERE idProducto = :idProducto",map);
			return true;
		} catch (Exception e) {
		//	logger.error("Error: " + e.getMessage());			
			return false;
		}
	}

	@Override
	public Boolean existCodigoProducto(String codigo, String fabrica)
			throws ProquifaNetException {
			try {
				  Map<String, Object> map = new HashMap<String, Object>();
					map.put("codigo",codigo);
					map.put("fabrica",fabrica);
				int i = super.queryForInt("SELECT COUNT(*) AS T FROM Productos WHERE Codigo = :codigo AND Fabrica = :fabrica ");
				
				if(i > 0){
					return true; 
				}else{
					return false;
				}
			} catch (Exception e) {
				funcion = new Funcion();
				funcion.enviarCorreoAvisoExepcion(e,"codigo: " + codigo, "fabrica: " + fabrica);
				return false;
			}
	}

	@Override
	public String getDescripcionProdUSP(long idproducto, String codigo)
			throws ProquifaNetException {
		try {
			 Map<String, Object> map = new HashMap<String, Object>();
				map.put("idproducto",idproducto);
				map.put("codigo",codigo);
				
			String descripcion = "";
			if (idproducto >0 ){ 
				descripcion =   " and codigo  = :idproducto ";
			} else if (codigo != null && !codigo.equals("")) {
				descripcion = " or idproducto  = :codigo " ;
			}
			if (descripcion.length() > 0 ){
				String  concepto = (String) super.jdbcTemplate.queryForObject("select top 1 Concepto  from Productos where Proveedor  =  44  ", map,String.class);
			return concepto;
			}else { 
				return null;
			}
			
		} catch (Exception e) {
		//	logger.error("Error: " + e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"idproducto: " + idproducto, "codigo: " + codigo);
			return "";
		}
	}
	
	
	@Override
	public long insertarProductoUSP(Producto producto)
			throws ProquifaNetException {
		try {
			 Map<String, Object> map = new HashMap<String, Object>();
				map.put("producto",producto);
			String query = "";
				query += "INSERT INTO Productos (Costo,Codigo,Concepto,Tipo,Subtipo,Control, Clasificacion, Manejo, Manejo_Transporte,";
				query += "FraccionArancelaria, PermisoImp,ClasificacionProducto,TipoPermiso, EstadoFisico,Aplicacion, Disponibilidad, Vigente,";
				query += "CAS, Moneda, Fecha, Caduca, Fabrica, FK02_Fabricante, Lote,tipoPresentacion,origen,Cantidad,Unidad)";
				query += "VALUES (" + producto.getCosto() + ",'" + producto.getCodigo() + "','" + producto.getConcepto() + "','" + producto.getTipo() + "','" + producto.getSubtipo() + "','" + producto.getControl()
						 + "','" + producto.getClasificacion() + "','" + producto.getManejo() + "','" + producto.getManejoTransporte() + "','" + producto.getFraccionArancelaria()
						 + "','" + producto.getPermisoImp() + "','" + producto.getClasificacionProducto() + "','" + producto.getTipoPermiso()
						 + "','" + producto.getEstadoFisico() + "','" + producto.getAplicacion() + "', '" + producto.getDisponibilidad() 
						 + "', 1, '" + producto.getCas() + "', 'Dolares', GETDATE(), CAST(YEAR(GETDATE()) AS VARCHAR(4)) + '1231 00:00'"
						 + ",'USP',2," + (producto.getLote() == null ? "null" : "'" + producto.getLote() + "'") + ","+ 
						 (producto.getTipoPresentacion() == null ? "null" : "'" + producto.getTipoPresentacion() + "'")+ "," +
						 (producto.getOrigen() == null ? "null" : "'" + producto.getOrigen() + "'") + "," +  (producto.getCantidad() == null ? "NULL" :"'" + producto.getCantidad() + "'") + "," +
						 (producto.getUnidad() == null ? "NULL" :"'" + producto.getUnidad() + "'") +" )";
				
				super.jdbcTemplate.update(query,map);
				return    super.queryForLong("SELECT IDENT_CURRENT ('Productos')");
				
		} catch (Exception e) {
		//	logger.error(e.getMessage());
			return -1L;
		}
	}
	
	@Override
	public long insertarProductoPharmaffiliates(Producto producto)
			throws ProquifaNetException {
		try {
			
			 Map<String, Object> map = new HashMap<String, Object>();
				map.put(" Proveedor", producto.getProveedor() );
				map.put(" Costo",producto.getCosto());
				map.put(" Codigo",producto.getCodigo() );
				map.put(" producto.getConcepto()", producto.getConcepto());
				map.put(" producto.getTipo() ", producto.getTipo() );
				
				map.put("Subtipo", producto.getSubtipo());
				map.put("Control",producto.getControl() );
				map.put("Clasificacion", producto.getClasificacion()  );
				map.put("Manejo ", producto.getManejo());
				map.put("ManejoTransporte ",producto.getManejoTransporte());
				
				map.put("FraccionArancelaria", producto.getFraccionArancelaria());
				map.put("PermisoImp",producto.getPermisoImp() );
				map.put("ClasificacionProducto",  producto.getClasificacionProducto());
				map.put("TipoPermiso ",producto.getTipoPermiso());
				map.put("EstadoFisico ",producto.getEstadoFisico() );
				
				map.put("Aplicacion", producto.getAplicacion());
				map.put("Disponibilidad", producto.getDisponibilidad() );
				map.put("Cas",  producto.getCas());
				map.put("Lote ",producto.getLote());
				map.put("Origen",producto.getOrigen()  );	
				
				map.put("Cantidad", producto.getCantidad() );
				map.put("Unidad", producto.getUnidad());
				map.put("Subgrupo",  producto.getSubgrupo() );
				map.put("TiempoEntrega",producto.getTiempoEntrega() );
				map.put("Documentacion",producto.getDocumentacion());	
				
				map.put("DatosToxicologicos",producto.getDatosToxicologicos());
				map.put("Linea",producto.getLinea() );
				map.put("EstadoPermiso",  producto.getEstadoPermiso());
				map.put("FichaTecnica",producto.getFichaTecnica());
				map.put("Clasificacion_ConfiguracionPrecio",producto.getClasificacion_ConfiguracionPrecio());	
				
				map.put("Sds",producto.getSds());
				map.put("SdsVersion",producto.getSdsVersion());
				map.put("DocumentacionVersion",producto.getDocumentacionVersion());
				map.put("TipoPresentacion",producto.getTipoPresentacion());
				
			String query = "";
				query += "INSERT INTO Productos (Proveedor, Costo,Codigo,Concepto,Tipo,Subtipo,Control, Clasificacion, Manejo, Manejo_Transporte,";
				query += "FraccionArancelaria, PermisoImp,ClasificacionProducto,TipoPermiso, EstadoFisico,Aplicacion, Disponibilidad, Vigente,";
				query += "CAS, Moneda, Fecha, Caduca, Fabrica, FK02_Fabricante, Lote, origen,Cantidad,Unidad, Subgrupo, TEntrega, ";
				query += "Documentacion, DatosToxicologicos, Linea, EstadoPermiso, FichaTecnica, FK04_Clasificacion_ConfiguracionPrecio, sds, sdsVersion, DocumentacionVersion, TipoPresentacion)";
				query += "VALUES ("+ producto.getProveedor() +"," + producto.getCosto() + ",'" + producto.getCodigo() + "','" + producto.getConcepto() + "','" + producto.getTipo() + "','" + producto.getSubtipo() + "','" + producto.getControl()
						 + "','" + producto.getClasificacion() + "','" + producto.getManejo() + "'," + (producto.getManejoTransporte().equals("NULL") ? "null" : "'"+producto.getManejoTransporte()+"'") + ",'" + producto.getFraccionArancelaria()
						 + "','" + producto.getPermisoImp() + "','" + producto.getClasificacionProducto() + "','" + producto.getTipoPermiso()
						 + "','" + producto.getEstadoFisico() + "'," + (producto.getAplicacion().equals("NULL") ? "null" : "'"+producto.getAplicacion()+"'") + ", '" + producto.getDisponibilidad() 
						 + "', 1, '" + producto.getCas() + "', 'Dolares', GETDATE(), CAST(YEAR(GETDATE()) AS VARCHAR(4)) + '1231 00:00'"
						 + ",'EP_',3," + (producto.getLote() == null ? "null" : "'" + producto.getLote() + "'") + ","+ 						
						 (producto.getOrigen().equals("NULL") ? "null" : "'" + producto.getOrigen() + "'") + "," + (producto.getCantidad().equals("NULL")? "null" :"'" + producto.getCantidad() + "'") + "," +
						 (producto.getUnidad().equals("NULL") ? "null" : "'" + producto.getUnidad() + "'") + "," + (producto.getSubgrupo().equals("NULL") ? "null" : "'" + producto.getSubgrupo() + "'") + "," +
						 (producto.getTiempoEntrega().equals("NULL") ? "null" : "'" + producto.getTiempoEntrega() + "'") + "," + (producto.getDocumentacion().equals("NULL") ? "null" : "'" + producto.getDocumentacion() + "'") + "," +
						 (producto.getDatosToxicologicos().equals("NULL") ? "null" : "'" + producto.getDatosToxicologicos() + "'") + "," + (producto.getLinea().equals("NULL") ? "null" : "'" + producto.getLinea() + "'") + ","+
						 (producto.getEstadoPermiso().equals("NULL") ? "null" : "'" + producto.getEstadoPermiso() + "'") + "," + (producto.getFichaTecnica().equals("NULL") ? "null" : "'" + producto.getFichaTecnica() + "'") + ","+
						 (producto.getClasificacion_ConfiguracionPrecio() == 0 ? "null" : producto.getClasificacion_ConfiguracionPrecio()) + "," + (producto.getSds().equals("NULL") ? "null" : "'" + producto.getSds() + "'") + ","+
						 (producto.getSdsVersion() + ",") + (producto.getDocumentacionVersion() +",") +
						 (producto.getTipoPresentacion().equals("NULL") ? "null" : "'" + producto.getTipoPresentacion() + "'")+ ") ";
				
				super.jdbcTemplate.update(query,map);
				return super.queryForLong("SELECT IDENT_CURRENT ('Productos')");
				
		} catch (Exception e) {
			//logger.error(e.getMessage());
			return -1L;
		}
	}
	
	@Override
	public boolean borrarProductoMicrobiologics() throws ProquifaNetException{
		try{
			
			 Map<String, Object> map = new HashMap<String, Object>();
			 
			String query="DELETE FROM Producto_Microbiologics";
			super.jdbcTemplate.update(query,map);
			return true;
		}catch (Exception e) {
		//	logger.error(e.getMessage());
			return false;
		}
	}
	
	@Override
	public boolean insertarProductoMicrobiologics(ProductoMicrobiologics producto) 
		throws ProquifaNetException {
		try{
			 Map<String, Object> map = new HashMap<String, Object>();
			map.put("Linea",producto.getLinea());
			map.put("Codigo",producto.getCodigo());
			map.put("Concepto ",producto.getConcepto() );
			map.put("CodigoPrecio",producto.getCodigoPrecio());
			map.put("getCosto",producto.getCosto());
			map.put("Comentario",producto.getComentario());	
			String query  = "";
			query = "INSERT INTO Producto_Microbiologics (Linea, Catalogo, Descripcion, CodigoPrecio, PrecioLista, Comentario, Fecha)" +
					" VALUES (:Linea , :Codigo, :Concepto, :CodigoPrecio, :Costo, :Comentario ,getdate()) ";			
			super.jdbcTemplate.update(query, map);
			return true;
		}catch (Exception e) {
		//	logger.error(e.getMessage());
			return false;
		}
	}
	@Override
	public long insertarLoteAnterior(Long idProducto, String lote)
			throws ProquifaNetException {
		try {
			 Map<String, Object> map = new HashMap<String, Object>();
				map.put("idProducto",idProducto);
				map.put("lote",lote);
				
			String query = "";
			query += "INSERT INTO Lote_Anterior (FK01_Producto, Lote_Anterior, Fecha) ";
			query += "VALUES (:idProducto, " +(lote == null ? "null" : "'" + lote + "'") + ", GETDATE())";
			
			super.jdbcTemplate.update(query,map);
			return super.queryForLong("SELECT IDENT_CURRENT ('Lote_Anterior')");
			
		} catch (Exception e) {
		//	logger.error(e.getMessage());
			return -1L;
		}
	}

	@Override
	public boolean insertarConfiguracionPrecioPorCategoriaPrecio(long idCP,long idProducto) throws ProquifaNetException {
		try {
			 Map<String, Object> map = new HashMap<String, Object>();
				map.put("idCP",idCP);
				map.put("idProducto",idProducto);
			
			String query = "INSERT INTO ConfiguracionPrecio_Producto (FK01_Producto,FK02_ConfFamilia,FK03_ConfCosto, FK05_CliFamilia,FK06_CliCosto) " +
					" SELECT :idProducto  AS FK01_Producto, FK02_ConfFamilia,FK03_ConfCosto, FK05_CliFamilia,FK06_CliCosto FROM ConfiguracionPrecio_Producto " +
					" WHERE FK01_Producto = (SELECT max(idProducto) FROM Productos WHERE FK03_Categoria_PrecioLista = :idCP AND Vigente = 1)";
			super.jdbcTemplate.update(query,map);
			log.info("Se Inserto la ConfiguracionPrecio_Producto para el producto: " + idProducto);
			return true;
		} catch (Exception e) {
		//	logger.error(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean actualizarFKCategoriaPrecioListaDeProducto(long idConfiguracionPrecio, long idProducto) throws ProquifaNetException {
		try {
			 Map<String, Object> map = new HashMap<String, Object>();
				map.put("idConfiguracionPrecio",idConfiguracionPrecio);
				map.put("idProducto",idProducto);
				
			super.jdbcTemplate.update("UPDATE productos SET FK03_Categoria_PrecioLista = :idConfiguracionPrecio, Proveedor = 1048 WHERE idProducto = :idProducto",map);
			log.info("Se asigno la Categoria precio: " + idConfiguracionPrecio + " a el producto: "+ idProducto);
			return true;
		} catch (Exception e) {
		//	logger.error(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean insertarConfiguracionPrecioNuevoProductoUSP(ConfiguracionPrecioProducto config) throws ProquifaNetException {
		try {
			 Map<String, Object> map = new HashMap<String, Object>();
			 Producto producto = config.getProducto();	
			 	map.put("Tipo",producto.getTipo() );
				map.put("Subtipo",producto.getSubtipo() );
				map.put("Control",producto.getControl() );
				map.put("Producto",producto.getIdProducto());
				map.put("Proveedor",config.getIdProveedor());
				
			String query ="DECLARE	@varTipo AS integer = (SELECT PK_Folio FROM ValorCombo WHERE Concepto='TipoProducto' AND Valor= :Tipo) ";
			
			if(producto.getSubtipo() != null && producto.getSubtipo().length() > 0){
				query += " DECLARE	@varSubTipo AS integer = (SELECT PK_Folio FROM ValorCombo WHERE Concepto='SubTipoProducto' AND Valor = :Subtipo) ";
			}
			
			if(producto.getControl() != null && producto.getControl().length() > 0){
				query += " DECLARE	@varControl AS integer = (SELECT PK_Folio FROM ValorCombo WHERE Concepto='Control' AND Valor= :Control) ";
			}
			
			query += "INSERT INTO ConfiguracionPrecio_Producto (FK01_Producto, FK02_ConfFamilia,FK05_CliFamilia) " +
					" SELECT distinct :Producto  AS FK01_Producto, CP.PK_Configuracion_Precio,CPP.FK05_CliFamilia FROM Configuracion_Precio AS CP" +
					"	INNER JOIN (SELECT FK02_ConfFamilia,FK05_CliFamilia FROM ConfiguracionPrecio_Producto) AS CPP ON CP.PK_Configuracion_Precio = CPP.FK02_ConfFamilia" +
					"	WHERE  CP.FK01_Proveedor = :IdProveedor AND";
			//CPP.FK05_CliFamilia IS NOT NULL AND se elimin������������������ para ingresar productos Pharmaffiliates
		
			if(producto.getTipo() != null && producto.getTipo().length() > 0){
				query += " Tipo = :Tipo  AND ";
			}else{
				query += " Tipo IS NULL  AND ";
			}
			if(producto.getSubtipo() != null && producto.getSubtipo().length() > 0){
				query += " Subtipo = :SubTipo  AND "; 
			}else{
				query += " Subtipo IS NULL AND ";
			}
			if(producto.getControl() != null && producto.getControl().length() > 0){
				query += " Control = :Control  ";
			}else{
				query += " Control IS NULL  ";
			}
			log.info("Query---------- " + query);	
			super.jdbcTemplate.update(query,map);
			log.info("Se Inserto una nueva ConfiguracionPrecio_Producto para el producto: " + config.getProducto().getIdProducto());
			return true;
		} catch (Exception e) {
		//	logger.error(e.getMessage());
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Producto> obtenerProductosDescargaDocumentos(int idProveedor)throws ProquifaNetException {
		try {
			
			 Map<String, Object> map = new HashMap<String, Object>();
				map.put("idProveedor",idProveedor);
			String query ="";
			switch (idProveedor){
			
			case 44: 	//USP
						query = "SELECT idProducto,Codigo,Lote,sds,sdsVersion,DocumentacionVersion FROM Productos WHERE Proveedor = :idProveedor  AND Vigente = 1 AND Tipo='Estandares' AND Lote IS NOT NULL";
						break;
						
			case 25:	//Chromadex
						query ="SELECT idproducto, codigo, SUBSTRING (codigo, 0, CHARINDEX('-', codigo)) AS codigoInterno, REPLACE (lote, '-', '_') AS Lote, sdsVersion, DocumentacionVersion FROM Productos WHERE proveedor= :idProveedor  AND LOTE IS NOT NULL";
						break;
			}
			
			return super.jdbcTemplate.query(query, map,new ProductosUSPDescargaDocumentosRowMapper());
		} catch (Exception e) {
			e.printStackTrace();
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			return new ArrayList<Producto>();
		}
	}
	
	@Override
	public boolean actualizarDocumentacionPructos(long idProducto, String documento,String campo, String version) throws ProquifaNetException{
		try {
			 Map<String, Object> map = new HashMap<String, Object>();
				map.put("idProducto",idProducto);
				map.put("documento",documento);
				map.put("campo",campo);
				map.put("version",version);
			String query = "";
			
			if (!version.equals("")) {
				query = "UPDATE Productos SET :campo = :documento - :version, :campo" + "Version = :version";
			}else{
				query = "UPDATE Productos SET :campo = :documento";
			}
			
			query += " WHERE idProducto = :idProducto";
			log.info(query);
			super.jdbcTemplate.update(query,map);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			return false;
		}
	}
	
	@Override
	public boolean insertUpdateProductosPharmaffiliates(Producto producto)throws ProquifaNetException{
		try {
			 Map<String, Object> map = new HashMap<String, Object>();
				map.put("producto",producto);
				map.put("Fabrica",producto.getFabrica() );
				map.put("Codigo",producto.getCodigo() );
				map.put("Concepto",producto.getConcepto() );
				map.put("Cas",producto.getCas());
				map.put("Costo",producto.getCosto());
				map.put("Proveedor",producto.getProveedor());
				map.put("Moneda",producto.getMoneda() );
				map.put("Tipo",producto.getTipo());
				map.put("Subtipo",producto.getSubtipo() );
				map.put("Moneda",producto.getMoneda() );
				
				
			
			String query  = "";
			boolean existe = super.queryForInt("SELECT COUNT(*) FROM Productos  WHERE Codigo = :Codigo AND Fabrica = :Fabrica"+ 
					    "AND CAST(Concepto AS VARCHAR(1000)) = :Concepto") > 0;
			if (existe) {
				query = "UPDATE  Productos SET  CAS = Cas, Costo = Costo" +
						",Proveedor =  Proveedor,Caduca = CAST((CAST(YEAR(GETDATE()) AS VARCHAR(4)) + '-12-23') AS DATE),"+
						"Moneda = :Moneda, Tipo = :Tipo, SubTipo = :Subtipo" +  
						"WHERE Codigo = '" +
						":Codigo AND Fabrica = :Fabrica" +
						" AND CAST(Concepto AS VARCHAR(1000)) = :Concepto";
						log.info(query);
				super.jdbcTemplate.update(query,map);
			}else{
				query = "INSERT INTO Productos (Codigo,Concepto,CAS,Costo,Proveedor, Fabrica, Fecha, Vigente, Caduca, Moneda,Tipo,SubTipo)" +
						" VALUES (:Codigo,:Concepto" +
						",Cas() ,:Costo,:Proveedor," +
						":Fabrica, GETDATE(),1,CAST((CAST(YEAR(GETDATE()) AS VARCHAR(4)) + '-12-23') AS DATE),'" +
						":Moneda,:Tipo,:Subtipo)";
				log.info(query);
				super.jdbcTemplate.update(query,map);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			return false;
		}
	}
	
	//------------------------------------------------------------------------------------
	//-------------------------------- Carta De Uso --------------------------------------
	//------------------------------------------------------------------------------------
	
	
	@Override
	public boolean updateProductoCartaUso(Producto prodcuto)  throws ProquifaNetException{
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(" CaracteristicasFisicas", prodcuto.getCaracteristicasFisicas() );
			map.put(" Composicion", prodcuto.getComposicion() );
			map.put(" FormulaQuimica", prodcuto.getFormulaQuimica() );
			map.put(" Peligrosidad", prodcuto.getPeligrosidad() );
			map.put(" IdProducto", prodcuto.getIdProducto());
			
			
			String query = "UPDATE Productos SET CaracteristicasFisicas = :CaracteristicasFisicas"+ 
					", Composicion = :Composicion(), FormulaQuimica = :FormulaQuimica" + 
					"' , Peligrosidad = :Peligrosidad WHERE idProducto = :IdProducto";
			
			return jdbcTemplate.update(query,map) > 0;
		} catch (Exception e) {
			e.printStackTrace();
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			return false;
		}
	}
	
	@Override
	public boolean updateLoteProductos(List<Producto> productos) throws ProquifaNetException{
		try {
			Map<String, Object> map = new HashMap<String, Object>();
		
			StringBuilder sbQuery = new StringBuilder();
			for (Producto producto : productos) {
				if (producto.getLote() == null)
					sbQuery.append("UPDATE Productos set Lote = NULL \n");
				else
					sbQuery.append("UPDATE Productos set Lote = '").append(producto.getLote()).append("' \n");
				sbQuery.append("WHERE idProducto = '").append(producto.getIdProducto()).append("' \n");
				
				sbQuery.append("UPDATE PzaInventario set TipoLote = 'PREVIO' \n");
				sbQuery.append("FROM PConnect_PzaInventario PzaInventario \n");
				sbQuery.append("INNER JOIN Productos PROD ON PROD.idProducto = PzaInventario.FK01_Producto \n");
				sbQuery.append("WHERE PzaInventario.FK01_Producto = '").append(producto.getIdProducto()).append("' \n");
				sbQuery.append("AND  PROD.Proveedor = '").append(producto.getProveedor()).append("' \n");
				
			}
			
			return jdbcTemplate.update(sbQuery.toString(),map) > 0;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	@Override
	public boolean updateValidoHasta() throws ProquifaNetException {
		try {

			 Map<String, Object> map = new HashMap<String, Object>();
			 
			StringBuilder sbQuery = new StringBuilder("UPDATE Pieza SET Pieza.ValidoHasta = Fecha.FechaValida \n");
			sbQuery.append("FROM PConnect_PZaInventario Pieza \n");
			sbQuery.append("LEFT JOIN (SELECT PZI.PK_PzaInventario, COALESCE(LA.Fecha_ValidoHasta, CASE WHEN PROD.Proveedor = 50 THEN DATEADD(MONTH, 12, C.Fecha) \n");
			sbQuery.append("WHEN PROD.Proveedor = 46 THEN DATEADD(MONTH, 12, C.Fecha)  \n");
			sbQuery.append("WHEN PROD.Proveedor <> 44 AND PROD.Proveedor <> 45 AND (IOC.MesCaducidad = '--ND--' OR IOC.MesCaducidad = '--ND--') THEN NULL  \n");
			sbQuery.append("WHEN PROD.Proveedor <> 44 AND PROD.Proveedor <> 45 THEN CAST(DATEADD(month, ((IOC.AnoCaducidad - 1900) * 12) + DATEPART(MM, IOC.MesCaducidad + ' 01 2016'), -1) as DATE) END) FechaValida, Prod.Proveedor \n");
			sbQuery.append("FROM PConnect_PZaInventario PZI \n");
			sbQuery.append("INNER JOIN Productos PROD ON PROD.idProducto = PZI.FK01_Producto \n");
			sbQuery.append("LEFT JOIN PPedidos PP ON PP.idPPedido = PZI.FK02_PPedido \n");
			sbQuery.append("LEFT JOIN (SELECT MAX(PK_LoteAnterior) PK, FK01_Producto, Lote_Anterior FROM Lote_Anterior GROUP BY FK01_Producto, Lote_Anterior) AS LA_PK ON LA_PK.FK01_Producto = PROD.idProducto AND LA_PK.Lote_Anterior = PZI.Lote \n");
			sbQuery.append("LEFT JOIN Lote_Anterior AS LA ON LA.PK_LoteAnterior = LA_PK.PK \n");
			sbQuery.append("LEFT JOIN (SELECT MAX(idPCompra) idPCompra, CPedido, PPedido, Estado FROM PCompras WHERE Estado = 'Recibido' GROUP BY CPedido, PPedido, Estado) PC ON PC.CPedido = PP.CPedido AND PC.PPedido = PP.Part AND PC.Estado = 'Recibido' \n");
			sbQuery.append("LEFT JOIN PCompras PCompra ON PCompra.idPCompra = PC.idPCompra \n");
			sbQuery.append("LEFT JOIN Compras C ON C.Clave = PCompra.Compra \n");
			sbQuery.append("LEFT JOIN (SELECT MAX(id) id, idPCompra FROM InspeccionOC GROUP BY idPCompra) INOC ON INOC.idPCompra = PC.idPCompra \n");
			sbQuery.append("LEFT JOIN InspeccionOC IOC ON IOC.id = INOC.id ) Fecha ON Fecha.PK_PzaInventario = Pieza.PK_PzaInventario \n");
			sbQuery.append("WHERE ValidoHasta IS NULL AND FechaValida IS NOT NULL \n");
			sbQuery.append(" \n");
			return jdbcTemplate.update(sbQuery.toString(),map) > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Producto> obtenerProductoParaDisponibilidad(Integer idProveedor, String letra)
			throws ProquifaNetException {
		try {

			 Map<String, Object> map = new HashMap<String, Object>();
			StringBuilder sbQuery = new StringBuilder("SELECT * FROM Productos \n");
			sbQuery.append("WHERE Proveedor = ").append(idProveedor).append(" AND Vigente = 1 \n");
			sbQuery.append("AND Concepto LIKE '").append(letra).append("%' \n");
			sbQuery.append(" \n");
			
			log.info(sbQuery.toString());
			return jdbcTemplate.query(sbQuery.toString(), new RowMapper() {
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					Producto producto = new Producto();
					producto.setIdProducto(rs.getLong("idProducto"));
					producto.setLote(rs.getString("Lote"));
					if (rs.getString("Codigo") != null)
					producto.setCodigo(rs.getString("Codigo").trim());
					producto.setConcepto(rs.getString("Concepto"));
					producto.setUnidad(rs.getString("Unidad"));
					producto.setCantidad(rs.getString("Cantidad"));
					producto.setTipo(rs.getString("Tipo"));
					producto.setSubtipo(rs.getString("Subtipo"));
					producto.setControl(rs.getString("Control"));
					producto.setFabrica(rs.getString("Fabrica"));
					producto.setDisponibilidad(rs.getString("Disponibilidad"));
					return producto;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean updateProductoDisponibilidad(List<Producto> productos) throws ProquifaNetException {
		try {
			
			 Map<String, Object> map = new HashMap<String, Object>();
		
			
			StringBuilder sbQuery = new StringBuilder("");
			for (Producto producto : productos) {
				if (producto.getDisponibilidad() == null)
					sbQuery.append("UPDATE Productos SET Disponibilidad = NULL \n");
				else
					sbQuery.append("UPDATE Productos SET Disponibilidad = '").append(producto.getDisponibilidad()).append("' \n");
				sbQuery.append("WHERE idProducto = ").append(producto.getIdProducto()).append("\n");
			}
			jdbcTemplate.update(sbQuery.toString(),map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	
	@Override
	public boolean updateProductoVigencia(List<Producto> productos, Integer vigente) throws ProquifaNetException {
		try {
			
			 Map<String, Object> map = new HashMap<String, Object>();
			 map.put("Vigente", vigente);
			
			StringBuilder sbQuery = new StringBuilder("");
			for (Producto producto : productos) {
				sbQuery.append("UPDATE Productos SET Vigente = :vigente \n");
				sbQuery.append("WHERE idProducto = ").append(producto.getIdProducto()).append("\n");
			}
			jdbcTemplate.update(sbQuery.toString(),map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	@Override
	public Long obtenerProductosEnStock(String catalogo, String fabrica) throws ProquifaNetException{
		Long numProductos=0L;
		try {
			 Map<String, Object> map = new HashMap<String, Object>();
				map.put("catalogo",catalogo);
				map.put("fabrica",fabrica);
				
			numProductos = super.queryForLong("SELECT SUM(cantDisponible) AS cantidad FROM Stock WHERE Codigo = :catalogo AND Fabrica = :fabrica ");
//				logger.info("SELECT SUM(cantDisponible) AS cantidad FROM Stock WHERE Codigo = '"+catalogo+"'"+" AND Fabrica = '"+fabrica +"'");
			return numProductos;
		} catch (Exception e) {
			return -1L;
		}

	}
	
	@Override
	public boolean actualizarManejoyPresencacionProducto(Producto prod) throws ProquifaNetException {
		try {
			String fabrica;
			// Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cantidad", prod.getCantidad());
			map.put("unidad", prod.getUnidad());
			map.put("manejo", prod.getManejo());
			map.put("manejoTransporte", prod.getManejoTransporte());
			map.put("idProducto", prod.getIdProducto());
			map.put("lote", prod.getLote());
			map.put("tipoPresentacion", prod.getTipoPresentacion());
			
			
			fabrica = super.jdbcTemplate.queryForObject("SELECT Fabrica FROM Productos WHERE idProducto = :idProducto \n", map, String.class);
			
			StringBuilder sbQuery = new StringBuilder("UPDATE Productos SET Manejo = :manejo, Manejo_Transporte = :manejoTransporte, tipoPresentacion = :tipoPresentacion \n");
			if (!fabrica.equals("USP")) {
				sbQuery.append(", Lote = :lote \n");
			}
			if (prod.getCantidad() != null) {
				sbQuery.append(", Cantidad = :cantidad \n");
			}
			if (prod.getUnidad() != null) {
				sbQuery.append(", Unidad = :unidad \n");
			}
			
			sbQuery.append("WHERE idProducto = :idProducto \n");
			sbQuery.append(" \n");
			jdbcTemplate.update(sbQuery.toString(),map);
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public boolean actualizarCertificado(Producto prod) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("documento", prod.getDocumentacion());
			map.put("idProducto", prod.getIdProducto());
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("UPDATE Productos SET Documentacion = :documento \n");
			sbQuery.append("WHERE idProducto = :idProducto \n");
			jdbcTemplate.update(sbQuery.toString(), map);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}
	
}