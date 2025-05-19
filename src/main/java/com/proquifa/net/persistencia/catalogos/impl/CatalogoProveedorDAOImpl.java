/**
 * 
 */
package com.proquifa.net.persistencia.catalogos.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.proquifa.net.modelo.catalogos.FormulaPrecio;
import com.proquifa.net.modelo.catalogos.MedioPago;
import com.proquifa.net.modelo.catalogos.proveedores.ClasificacionConfiguracionPrecio;
import com.proquifa.net.modelo.catalogos.proveedores.ConfiguracionPrecio;
import com.proquifa.net.modelo.catalogos.proveedores.ConfiguracionPrecioProducto;
import com.proquifa.net.modelo.catalogos.proveedores.CostoFactor;
import com.proquifa.net.modelo.catalogos.proveedores.Flete;
import com.proquifa.net.modelo.catalogos.proveedores.InformacionPagos;
import com.proquifa.net.modelo.catalogos.proveedores.Licencia;
import com.proquifa.net.modelo.catalogos.proveedores.MultiusosValores;
import com.proquifa.net.modelo.catalogos.proveedores.TiempoEntrega;
import com.proquifa.net.modelo.comun.Campana;
import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.comun.Empresa;
import com.proquifa.net.modelo.comun.Fabricante;
import com.proquifa.net.modelo.comun.Producto;
import com.proquifa.net.modelo.comun.Proveedor;
import com.proquifa.net.modelo.comun.ValorAdicional;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.catalogos.CatalogoProveedorDAO;
import com.proquifa.net.persistencia.catalogos.impl.mapper.CampanaComercialRowMapper;
import com.proquifa.net.persistencia.catalogos.impl.mapper.ConfigPrecioClasificacionRowMapper;
import com.proquifa.net.persistencia.catalogos.impl.mapper.ConfiguracionPrecioArbolRowMapper;
import com.proquifa.net.persistencia.catalogos.impl.mapper.ConfiguracionPrecioClasificacionRowMapper;
import com.proquifa.net.persistencia.catalogos.impl.mapper.ConfiguracionPrecioCostoRowMapper;
import com.proquifa.net.persistencia.catalogos.impl.mapper.ConfiguracionPrecioMontoRowMapper;
import com.proquifa.net.persistencia.catalogos.impl.mapper.ConfiguracionPrecioRowMapper;
import com.proquifa.net.persistencia.catalogos.impl.mapper.EmpresasProveedorRowMapper;
import com.proquifa.net.persistencia.catalogos.impl.mapper.FabricanteProveedorRowMapper;
import com.proquifa.net.persistencia.catalogos.impl.mapper.FleteProveedorRowMapper;
import com.proquifa.net.persistencia.catalogos.impl.mapper.FormulaPrecioRowMapper;
import com.proquifa.net.persistencia.catalogos.impl.mapper.InformacionPagosRowMapper;
import com.proquifa.net.persistencia.catalogos.impl.mapper.LicenciaRowMapper;
import com.proquifa.net.persistencia.catalogos.impl.mapper.ListaConfiguracionPrecioProveedorCampanaRowMapper;
import com.proquifa.net.persistencia.catalogos.impl.mapper.ListarConfiguracionPrecioProveedorRowMapper;
import com.proquifa.net.persistencia.catalogos.impl.mapper.MediosPagosProveedorRowMapper;
import com.proquifa.net.persistencia.catalogos.impl.mapper.MultiusosMixProveedorRowMapper;
import com.proquifa.net.persistencia.catalogos.impl.mapper.ProductosClasificacionRowMapper;
import com.proquifa.net.persistencia.catalogos.impl.mapper.ProveedorCatalogoRowMapper;
import com.proquifa.net.persistencia.catalogos.impl.mapper.ProveedorPQNetRowMapper;
import com.proquifa.net.persistencia.catalogos.impl.mapper.TiempoEntregaRutaProveedorRowMapper;
import com.proquifa.net.persistencia.comun.ProveedorDAO;
import com.proquifa.net.persistencia.comun.impl.mapper.ProductoRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.ValorAdicionalRowMapper;

/**
 * @author orosales
 *
 */
@Repository
public class CatalogoProveedorDAOImpl extends DataBaseDAO implements CatalogoProveedorDAO {

	final Logger log = LoggerFactory.getLogger(CatalogoProveedorDAOImpl.class);
	
	@Autowired
	ProveedorDAO proveedorDAO;

	
	Funcion funcion;

	public StringBuilder obtenerConusultaFormulaCatalogo(String tipo){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tipo", tipo);
	
		StringBuilder consulta = new StringBuilder("");
		//		A = Precio de lista				G = Factor de utilidad		L = Honorarios Agente Aduanal				R = descuento
		//		B = flete						H = Descuento conocido		M = Permiso
		//		C = Costo consularizacion		I = Almacen destino			N = Numero de piezas 
		//		D = Flete documentacion 		J = Factor IGI				O = Minimo de Orden Compra moneda venta
		//		F = Factor Costo fijo			K = FactorDTA				Q = factor de licenciamiento

		try {
			String B = "", C = "", D= "", J = "", K = "0", L = "0", M = "", I = "", N = "", R = "", O= "", Q = ""; //
			String VA = "", Imp = "", PrecioL = "", CV = "", Valor="";


			Q = 	" 	CASE WHEN CFL.Monto is not null and CFL.Monto > 0  " +
					"					THEN CFL.Monto " +
					"				 WHEN (CFL.Monto is null or CFL.Monto < 1 ) and CFL.Porcentaje > 0 " +
					"					THEN (1 + (CFL.Porcentaje/100)) " +
					" 				else" +
					"					 (1 + ((CASE WHEN LICE.Factor = -1 OR  LICE.Factor IS NULL THEN 0 ELSE LICE.Factor /100 END))) " +
					"				END ";	

			B = 	" (CASE WHEN CF.FLETE = 0 OR  CF.FLETE IS NULL THEN 0 ELSE CF.FLETE END )";
			C = 	" (CASE WHEN CF.Costo_Consularizacion = -1 OR  CF.Costo_Consularizacion IS NULL THEN 0 ELSE CF.Costo_Consularizacion END)";
			D = 	" (CASE WHEN CF.Flete_Documentacion = -1 OR  CF.Flete_Documentacion IS NULL THEN 0 ELSE CF.Flete_Documentacion END)";
			J = 	" ((CASE WHEN CF.Factor_IGI = -1 OR  CF.Factor_IGI IS NULL THEN 0 ELSE CF.Factor_IGI END)/100) ";
			K = 	" ((CASE WHEN CF.FactorDTA = -1 OR  CF.FactorDTA IS NULL THEN 0 ELSE CF.FactorDTA END)/100) ";
			R = 	" (CASE WHEN CF.DESCUENTO IS NULL THEN 1 ELSE (1 - (CF.Descuento/100)) END) ";	

			PrecioL = "round (((case when (PROD.Costo * PROVEE.TC) = 0 then 1 else (PROD.Costo * PROVEE.TC) end) * (" + R + ")),3)";
			O = " (CASE WHEN CF.VALORENADUANA = 0 OR CF.VALORENADUANA IS NULL THEN 1 ELSE CF.VALORENADUANA END) ";

			N  = 	"\n CASE WHEN CF.VALORENADUANA <= 0 OR CF.VALORENADUANA IS NULL THEN case when CF.Piezas is null or CF.Piezas = 0 then 1 else CF.Piezas end " +
					" ELSE " +
					"	CASE WHEN  (CFL.Monto is not null and CFL.Monto > 0)  THEN " +
					"		CEILING((" + O + " - " + B + ")/(" + PrecioL + " +  " + Q +"))  " +
					"	ELSE " +
					"		CEILING((" + O + " - " + B + ")/(" + PrecioL + " *  " + Q +"))  " +
					"	END " +
					" END \n";

			N = " case when " + N + " = 0 then 1 else " + N + " end ";

			VA =    " CASE WHEN (CFL.Monto is not null and CFL.Monto > 0)  " +
					" THEN ( (" + N + " * (" + PrecioL + "  +  " + Q +") ) + " + B +") " +
					" ELSE ( (" + N + " * " + PrecioL + "  * " + Q +" ) + " + B +") END	";
			
			Valor = "(" + PrecioL + ") * (" + N + ")";
			
			L = 	" (CASE WHEN (CASE WHEN LAAC.Monto = 0  OR  LAAC.Monto IS NULL THEN LAAC.Porcentaje ELSE LAAC.Monto END) IS NULL THEN 0 ELSE CASE WHEN LAAC.Monto = 0  OR  LAAC.Monto IS NULL THEN (" + VA + ") * ((LAAC.Porcentaje)/100) ELSE LAAC.Monto END END)";
			M = 	" (CASE WHEN CF.Permiso = -1 OR  CF.Permiso IS NULL THEN 0 ELSE CF.Permiso END)";
			I = 	" (CASE WHEN CF.AlmacenDestino = -1 OR  CF.AlmacenDestino IS NULL  THEN 0 ELSE CF.AlmacenDestino END) ";

			Imp =	 "  ((" + VA + " * (" + J + " + " + K + ")) + " + L + ") ";	
			CV = 	  " ( (" + VA + ") + (" + Imp + ") + (" + C + ") + (" + D + ") + (" + M + ") + (" + I + ")) ";

			consulta.append(N).append(" Num").append(", ");
			consulta.append(CV).append(" CV").append(", ");
			consulta.append(Valor).append(" VA").append(", ");	
			consulta.append("  (PROD.Costo * PROVEE.TC)  DIFERENCIAL,");


			return consulta;
		} catch (Exception e) {
		//	logger.error(e.getMessage());
		 funcion = new Funcion();
		 funcion.enviarCorreoAvisoExepcion(e, "consulta: " + consulta, "tipo: " + tipo);
		 return new StringBuilder("");
		}
		
	}



	public Boolean actualizarProveedor(Proveedor proveedor) {
		StringBuilder sql = new StringBuilder();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("proveedor", proveedor);
	
		try {
			Object[] params =  {
					proveedor.getHabilitado(),
					proveedor.getNombre(),
					proveedor.getRazonSocial(),
					proveedor.getDescripcionAmpliada(),
					proveedor.getPais(),
					proveedor.getCiudad(),
					proveedor.getEstado(),
					proveedor.getCp(),
					proveedor.getCalle(),
					proveedor.getDelegacion(),
					proveedor.getSegundaDireccion(),
					proveedor.getImagen(),
					proveedor.getSocioComercial(),
					proveedor.getMonedaCompra(), 
					proveedor.getMonedaVenta(),
					proveedor.getPagador(),
					proveedor.getComprador(),
					proveedor.getColocarPhs(),					
					proveedor.getRol(),
					proveedor.getRanking(),
					proveedor.getInspector(),
					proveedor.getPrecioWeb(),
					proveedor.getPagina(),
					proveedor.getUsuario(),
					proveedor.getPassword(),
					proveedor.isCompraLinea(),
					proveedor.getIdProveedor()
			};

			sql.append(" UPDATE Proveedores SET ");
			sql.append(" FUActual=GETDATE(),");
			sql.append(" Habilitado=?,");
			sql.append(" Nombre=?,");
			sql.append(" RSocial=?,");
			sql.append(" DescripcionAmpliada=?,");
			sql.append(" Pais=?,");
			sql.append(" Ciudad=?,");
			sql.append(" Estado=?,");
			sql.append(" CP=?,");
			sql.append(" RSCalle=?,");
			sql.append(" RSDel=?,");
			sql.append(" Direccion2=?,");
			sql.append(" imagen=?,");
			sql.append(" existeRelacionComercial=?,");
			sql.append(" Moneda=?,");
			sql.append(" MonedaVenta=?,");
			sql.append(" Pagador=?,");
			sql.append(" FK01_Empleado=?,");
			sql.append(" ColocarPhs=?,");
			if(proveedor.getCambioEnTipoCambio() != null){
				if(proveedor.getCambioEnTipoCambio()){
					sql.append(" Tipo_Cambio=").append(proveedor.getTipoCambio()).append(",");
					sql.append(" FUA_TipoCambio=GETDATE(),");
				}
			}
			if (proveedor.getObjectivoCrecimiento()!=null){
				sql.append(" ObjetivoCrecimiento = '").append(proveedor.getObjectivoCrecimiento()).append("', ");
			}
			if (proveedor.getObjetivoCrecimientoFundamental()!=null){
				sql.append(" ObjetivoCrecimientoFundamental = '").append(proveedor.getObjetivoCrecimientoFundamental()).append("', ");
			}
			if (proveedor.getMesInicioFiscal() > 0){
				sql.append(" MesInicioFiscal = ").append(proveedor.getMesInicioFiscal()).append(" ,");
			}
			
			if (proveedor.getTaxId() != null) {
				sql.append(" TaxID = '").append(proveedor.getTaxId()).append("' ,");
			}
			
			sql.append("  rol=?,");
			sql.append("  ranking=?,");
			sql.append("  FK03_Inspector=?,");
			sql.append("  PrecioWeb=?,");
			sql.append("  Pagina=?,");
			sql.append("  Usuario=?,");
			sql.append("  Contraseña=?,");
			sql.append("  CompraLinea=?");
			sql.append(" WHERE Clave=?");

			//logger.info(sql.toString()+" CLAVE: "+proveedor.getIdProveedor());

			super.jdbcTemplate.update(sql.toString(), map);

			if (proveedor.getHabilitado()){
				if(!habilitarProveedor(proveedor.getIdProveedor(),proveedor.getSocioComercial())){
					deshabilitarProveedor(proveedor.getIdProveedor());
				}
			}

			return true;
		} catch (Exception e) {
		//	logger.error("Error: " + e.getMessage());
			funcion = new Funcion();
			e.printStackTrace();
			funcion.enviarCorreoAvisoExepcion(e, "sql: " + sql, "proveedor: " + proveedor);
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Proveedor> findProveedores(String parametro) {
		String query="";
		try{
			String condicion = "";
			String industria = "";
			if( (parametro.length() > 0) && (!parametro.equals("Todos"))) {
				condicion = "WHERE";
			}
			for (String retval: parametro.split("-")){
				
				if(retval.equalsIgnoreCase("Habilitado")){
					condicion += " Habilitado=1 AND";	
				}else if(retval.equalsIgnoreCase("Deshabilitado")){
					condicion += " Habilitado=0 AND";
				}else if(retval.equalsIgnoreCase("Nacional")){
					condicion += " pais = 'MEXICO' AND";
				}else if(retval.equalsIgnoreCase("Extranjero")){
					condicion += " pais <> 'MEXICO' AND";
				}else if(retval.equalsIgnoreCase("ALIMENTOS Y BEBIDAS")){
					industria = "ALIMENTOS";
					condicion += " PROD.Proveedor IS NOT NULL AND ";
				}else if(retval.equalsIgnoreCase("CLINICA HOSPITALARIA")){
					industria = "CLÍNICO";
					condicion += " PROD.Proveedor IS NOT NULL AND ";
				}else if(retval.equalsIgnoreCase("FARMACEUTICA")){
					industria = "FARMA";
					condicion += " PROD.Proveedor IS NOT NULL AND ";
				}else if(retval.equalsIgnoreCase("FARMA")){
					industria = "FARMA";
					condicion += " PROD.Proveedor IS NOT NULL AND ";
				}
			}
			if( (parametro.length() > 0) && (!parametro.equals("Todos"))) {
				condicion += " 1 = 1 ";
			}


			query  =" \n SELECT  PRO.clave, PRO.habilitado, PRO.Nombre, PRO.Observa,PRO.Pais,PRO.rscalle,PRO.Calle,PRO.RSDel,PRO.Estado,PRO.TaxID,";
			query +=" \n PRO.CP,PRO.RSocial,PRO.Moneda,PRO.cpago,PRO.cheque,PRO.transferencia,PRO.tarjeta,PRO.deposito,";
			query +=" \n PRO.descripcionAmpliada,PRO.Direccion2,PRO.Ciudad,PRO.existeRelacionComercial,PRO.imagen,PRO.FUActual,PRO.FUA_Productos,";
			query +=" \n PRO.moneda,PRO.monedaventa,PRO.Tipo_Cambio,PRO.FUA_TipoCambio, PRO.objetivoCrecimientoFundamental,";
			query +=" \n SUM(CPP.TPTODUCTO) PRODUCTOS, COUNT( PK_Configuracion_Precio ) FAMILIAS, PRO.Pagador, PRO.FK01_Empleado, PRO.ColocarPhs, PRO.ObjetivoCrecimiento,PRO.rol, PRO.ranking, PRO.FK03_Inspector,PRO.MesInicioFiscal, PRO.CompraLinea ";
			query +=" \n FROM Proveedores AS PRO";
			query +=" \n LEFT JOIN (SELECT *	FROM configuracion_precio WHERE nivel='Familia') AS CP ON CP.FK01_Proveedor =  PRO.Clave";
			query +=" \n LEFT JOIN (SELECT COUNT(FK02_ConfFamilia) AS TPTODUCTO, FK02_ConfFamilia FROM configuracionprecio_producto INNER JOIN productos on fk01_producto = idproducto WHERE vigente = 1 GROUP BY FK02_ConfFamilia) AS CPP ON CPP.FK02_ConfFamilia = CP.PK_Configuracion_Precio \n ";
			if (!industria.equals("")){
				query +=" \n LEFT JOIN (SELECT PRO.Proveedor FROM Productos AS PRO ";
				query +=" \n 		LEFT JOIN (SELECT * FROM IndustriaProducto) AS INPRO ON INPRO.FK02_Producto = PRO.idProducto";
				query +=" \n 		LEFT JOIN (SELECT PK_Folio, Valor FROM ValorCombo WHERE Valor = '" + industria + "') AS VC ON VC.PK_Folio = INPRO.FK01_Industria";
				query +=" \n 		WHERE VC.PK_Folio IS NOT NULL GROUP BY PRO.Proveedor) ";
				query +=" \n 	 AS PROD ON PROD.Proveedor = PRO.Clave ";
			}
			query +=  condicion;
			query +=" \n GROUP BY  PRO.clave, PRO.habilitado, PRO.Nombre, PRO.Observa,PRO.Pais,PRO.rscalle,PRO.Calle,PRO.RSDel,PRO.Estado,";
			query +=" \n PRO.CP,PRO.RSocial,PRO.Moneda,PRO.cpago,PRO.cheque,PRO.transferencia,PRO.tarjeta,PRO.deposito,PRO.TaxID,";
			query +=" \n PRO.descripcionAmpliada,PRO.Direccion2,PRO.Ciudad,PRO.existeRelacionComercial,PRO.imagen,PRO.FUActual,PRO.FUA_Productos,";
			query +=" \n PRO.moneda,PRO.monedaventa,PRO.Tipo_Cambio,PRO.FUA_TipoCambio, PRO.Pagador, PRO.FK01_Empleado, PRO.ColocarPhs, PRO.ObjetivoCrecimiento,PRO.objetivoCrecimientoFundamental,PRO.rol, PRO.ranking, PRO.FK03_Inspector	,PRO.MesInicioFiscal, PRO.CompraLinea ";
			query +=" \n ORDER BY Nombre ASC";

		//	logger.info(query);
			return super.jdbcTemplate.query(query, new ProveedorCatalogoRowMapper());
		}catch(Exception e){
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "query: " + query, "parametro: " + parametro);
			return new ArrayList<Proveedor>();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Proveedor> findProveedoresXUsuario(Empleado usuario) {
		String query="";
		try{
			String condicion = "";
			if(usuario.getIdFuncion()==10 ){//10 Es comprador      y      8 que es Coord<->Gerente de Compras junto con directores verian todos
				condicion=" WHERE PRO.FK01_Empleado = "+usuario.getIdEmpleado();
			}

			query  =" \n SELECT  PRO.clave, PRO.habilitado, PRO.Nombre, PRO.Observa,PRO.Pais,PRO.rscalle,PRO.Calle,PRO.RSDel,PRO.Estado,PRO.TaxID,";
			query +=" \n PRO.CP,PRO.RSocial,PRO.Moneda,PRO.cpago,PRO.cheque,PRO.transferencia,PRO.tarjeta,PRO.deposito,";
			query +=" \n PRO.descripcionAmpliada,PRO.Direccion2,PRO.Ciudad,PRO.existeRelacionComercial,PRO.imagen,PRO.FUActual,PRO.FUA_Productos,";
			query +=" \n PRO.moneda,PRO.monedaventa,PRO.Tipo_Cambio,PRO.FUA_TipoCambio,PRO.ObjetivoCrecimientoFundamental, PRO.CompraLinea, ";
			query +=" \n SUM(CPP.TPTODUCTO) PRODUCTOS, COUNT( PK_Configuracion_Precio ) FAMILIAS, PRO.Pagador, PRO.FK01_Empleado, PRO.ColocarPhs, PRO.ObjetivoCrecimiento,PRO.rol, PRO.ranking, PRO.FK03_Inspector,PRO.MesInicioFiscal, PRO.Pagina, PRO.Contraseña, PRO.Usuario, PRO.PrecioWeb	";
			query +=" \n FROM Proveedores AS PRO";
			query +=" \n LEFT JOIN (SELECT *	FROM configuracion_precio WHERE nivel='Familia') AS CP ON CP.FK01_Proveedor =  PRO.Clave";
			query +=" \n LEFT JOIN (SELECT COUNT(FK02_ConfFamilia) AS TPTODUCTO, FK02_ConfFamilia FROM configuracionprecio_producto GROUP BY FK02_ConfFamilia) AS CPP ON CPP.FK02_ConfFamilia = CP.PK_Configuracion_Precio \n ";
			query +=  condicion;
			query +=" \n GROUP BY  PRO.clave, PRO.habilitado, PRO.Nombre, PRO.Observa,PRO.Pais,PRO.rscalle,PRO.Calle,PRO.RSDel,PRO.Estado,";
			query +=" \n PRO.CP,PRO.RSocial,PRO.Moneda,PRO.cpago,PRO.cheque,PRO.transferencia,PRO.tarjeta,PRO.deposito,PRO.TaxID,";
			query +=" \n PRO.descripcionAmpliada,PRO.Direccion2,PRO.Ciudad,PRO.existeRelacionComercial,PRO.imagen,PRO.FUActual,PRO.FUA_Productos,PRO.ObjetivoCrecimientoFundamental,";
			query +=" \n PRO.moneda,PRO.monedaventa,PRO.Tipo_Cambio,PRO.FUA_TipoCambio, PRO.Pagador, PRO.FK01_Empleado, PRO.ColocarPhs, PRO.ObjetivoCrecimiento, PRO.rol , PRO.ranking, PRO.FK03_Inspector,PRO.MesInicioFiscal, PRO.Pagina, PRO.Contraseña, PRO.Usuario, PRO.PrecioWeb, PRO.CompraLinea ";
			query +=" \n ORDER BY Nombre ASC";
		//	logger.info(query);

			log.info(query);
			return super.jdbcTemplate.query(query, new ProveedorCatalogoRowMapper());
		}catch(Exception e){
		//	logger.error(e.getMessage());
			e.printStackTrace();
			funcion = new Funcion();
//			funcion.enviarCorreoAvisoExepcion(e, "query: " + query, usuario);
			return new ArrayList<Proveedor>();
		}
	}


	public Proveedor getInformacionGeneralProveedor(Long idProveedor) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idProveedor", idProveedor);
		String queryFind = "";
		try {
//			queryFind =	"SELECT * FROM Proveedores AS PROV INNER JOIN Pais AS P ON P.PK_Pais = PROV.FK05_Pais WHERE Clave=" + idProveedor;
			queryFind =	"SELECT * FROM Proveedores WHERE Clave=" + idProveedor;
			return (Proveedor) super. jdbcTemplate.queryForObject(queryFind,map,  new ProveedorPQNetRowMapper());
		} catch (Exception e) {
			funcion = new Funcion();
			e.printStackTrace();
			funcion.enviarCorreoAvisoExepcion(e, "queryFind: " + queryFind, "idProveedor: " + idProveedor);
			return new Proveedor();
		}
	}

	public Long insertarProveedor(Proveedor proveedor) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("proveedor", proveedor);
		try {
			if(proveedor.getNombre() .equals("") && proveedor.getRazonSocial().equals("")){
				return 0L;
			}else{
				if(proveedor.getComprador() == null || proveedor.getComprador() == 0){
					proveedor.setComprador(null);
				}
				if(proveedor.getPagador() == null || proveedor.getPagador() == 0 ){
					proveedor.setPagador(null);
				}
				if(proveedor.getInspector() == null || proveedor.getInspector() == 0 ){
					proveedor.setInspector(null);
				}
				
				Object[] params = {proveedor.getNombre(),proveedor.getRazonSocial(),proveedor.getHabilitado(),proveedor.getDescripcionAmpliada(),proveedor.getCalle(), proveedor.getDelegacion(),
						proveedor.getSegundaDireccion(),proveedor.getPais(),proveedor.getCiudad(),proveedor.getEstado(),proveedor.getCp(),proveedor.getImagen(), proveedor.getComprador(), 
						proveedor.getPagador(), proveedor.getColocarPhs(),proveedor.getRol(), proveedor.getRanking(), proveedor.getInspector(),
						proveedor.getObjectivoCrecimiento(),proveedor.getObjetivoCrecimientoFundamental(),proveedor.getMesInicioFiscal(),proveedor.getTaxId()};
				
				Integer Numprovee = super.queryForInt("SELECT COUNT(*) AS Total FROM Proveedores WHERE (Nombre = '"+proveedor.getNombre()+"' OR RSocial = '"+proveedor.getRazonSocial()+"') AND Habilitado="+(proveedor.getHabilitado()?1:0) );
				if(Numprovee!=0){
					////logger.info("Ya existe el proveedor.");
					return 0L;
				}
				super.jdbcTemplate.update("INSERT INTO Proveedores(Nombre,RSocial,Habilitado,DescripcionAmpliada,RSCalle,RSDel,Direccion2,Pais,Ciudad,Estado,CP,imagen, FK01_Empleado, Pagador, FUActual,ColocarPhs,rol,ranking, FK03_Inspector,ObjetivoCrecimiento,ObjetivoCrecimientoFundamental,MesInicioFiscal,TaxID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,GETDATE(),?,?,?,?,?,?,?,?)",map);
				return super.queryForLong("SELECT IDENT_CURRENT ('Proveedores')");
			}
		} catch (Exception e) {
			//logger.error("Error: " + e.getMessage());
			e.printStackTrace();
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, proveedor);
			return -1L;
		}
	}

	public Boolean deshabilitarProveedor(Long idProveedor) {
		try{
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idProveedor", idProveedor);
			
			String sql = "UPDATE Proveedores SET Habilitado=0, existeRelacionComercial=0,FUActual=GETDATE() WHERE Clave="+ idProveedor;
			super.jdbcTemplate.update(sql,map);
			return true;
		}catch(RuntimeException rte){
		//	logger.error("Error: " + rte.getMessage());
			return false;
		}
	}

	public Boolean habilitarProveedor(Long idProveedor, Boolean relacion) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idProveedor", idProveedor);
			map.put("relacion", relacion);
			
			Long valor = relacion == true ? 1L:0L;
			//Habilitar el proveedor solo cuando tenga al menos un contacto
			Long numContactos=super.queryForLong("Select COUNT(*) As cuantos from Contactos where FK01_Proveedor =" + idProveedor);
			if (numContactos>0) { //Debe haber un contacto como minimo para habilitar el proveedor
				super.jdbcTemplate.update("UPDATE Proveedores SET Habilitado=1, FUActual=GETDATE(), existeRelacionComercial="+valor+" WHERE Clave="+ idProveedor, map);
				return true;
			}else{
				return false;
			}
		}catch(RuntimeException rte){
		//	logger.error("Error: " + rte.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(rte, "idProveedor: " + idProveedor,"relacion: " + relacion);
			return false;
		}
	}

	public Long insertarConfiguracionProductosArbol(ConfiguracionPrecioProducto config, Boolean actualizarProductos) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("actualizarProductos", actualizarProductos);
			map.put("config", config);
			
			Long idConfig = config.getIdConfiguracion();

			if (idConfig== null || idConfig == 0){  
				// SI NO ME MANDA UNA CONFIGURACION, SIGNIFICA QUE NO HAY NINGUNA CONFIGURACION DE LA FAMILIA
				Object[] params =  {config.getProducto().getTipo(),	config.getProducto().getSubtipo(),	config.getProducto().getControl(),
						config.getProducto().getIndustria(), config.getNivel(),	config.getIdProveedor()};
				try{
					StringBuilder sql = new StringBuilder("");
					sql.append(" \n DECLARE	@varTipo AS integer = (SELECT PK_Folio FROM ValorCombo WHERE Concepto='TipoProducto' AND Valor='" + config.getProducto().getTipo() + "') ");
					sql.append(" \n DECLARE	@varSubTipo AS integer = (SELECT PK_Folio FROM ValorCombo WHERE Concepto='SubTipoProducto' AND Valor='" + config.getProducto().getSubtipo() + "')");
					sql.append(" \n DECLARE	@varControl AS integer = (SELECT PK_Folio FROM ValorCombo WHERE Concepto='Control' AND Valor='" + config.getProducto().getControl() + "') ");
					sql.append(" \n DECLARE	@varIndus AS integer = (SELECT PK_Folio FROM ValorCombo WHERE Concepto='Industrial' AND Valor='" + config.getProducto().getIndustria() + "') ");
					sql.append(" \n UPDATE  Configuracion_Precio SET Tipo  = @varTipo");
					sql.append(" \n WHERE ");
					if(config.getProducto().getTipo() == null){
						sql.append(" Tipo IS NULL  AND ");
					}else{
						sql.append(" Tipo = @varTipo  AND ");
					}
					if(config.getProducto().getSubtipo() == null){
						sql.append(" Subtipo IS NULL AND ");
					}else{
						sql.append(" Subtipo = @varSubTipo  AND "); 
					}
					if(config.getProducto().getControl() == null){
						sql.append(" Control IS NULL AND ");
					}else{
						sql.append(" Control = @varControl AND ");
					}
					sql.append(" \n Nivel = '" + config.getNivel() + "'  AND FK01_Proveedor  = "+ config.getIdProveedor() + "  AND Industria = @varIndus ");

					//////logger.info(sql.toString());

					int i = super.jdbcTemplate.update(sql.toString(),map);

					if(i == 0){
						StringBuilder sbQuery = new StringBuilder("");
						sbQuery.append("DECLARE	@varTipo AS integer = (SELECT PK_Folio FROM ValorCombo WHERE Concepto='TipoProducto' AND Valor=?) ");
						sbQuery.append("DECLARE	@varSubTipo AS integer = (SELECT PK_Folio FROM ValorCombo WHERE Concepto='SubTipoProducto' AND Valor=?)");
						sbQuery.append("DECLARE	@varControl AS integer = (SELECT PK_Folio FROM ValorCombo WHERE Concepto='Control' AND Valor=?) ");
						sbQuery.append("DECLARE	@varIndus AS integer = (SELECT PK_Folio FROM ValorCombo WHERE Concepto='Industrial' AND Valor=?) ");
						sbQuery.append("INSERT INTO Configuracion_Precio(Tipo,Subtipo,Control,Nivel,FK01_Proveedor,Industria,FUA) ");	
						sbQuery.append("VALUES(@varTipo,@varSubTipo,@varControl,?,?,@varIndus,GETDATE()) ");

						super.jdbcTemplate.update(sbQuery.toString(), map);
						idConfig = super.queryForLong("SELECT IDENT_CURRENT ('Configuracion_Precio')");
						//////logger.info("insertar productos: "+idConfig);

						if(actualizarProductos){
							if( this.insertarProductosConfiguracion_Productos(config.getProducto(), idConfig,config.getIdProveedor())){
								if(this.actualizarProveedor_FUAProductos(config.getIdProveedor())){
									return idConfig;
								}
							}
						}
					}
				}catch(Exception e){
				//	logger.error(e.getMessage());
					funcion = new Funcion();
					funcion.enviarCorreoAvisoExepcion(e, config, "actualizarProductos: " + actualizarProductos);
					return -1L;
				}

			}else{
				// EN CASO DE QUE SE MANDE UN ID DE CONFIGURACION, SIGNIFICA QUE SE TIENE QUE ELIMINAR, SE ACTUALIZAN LOS PRODUCTOS Y SE ELIMINA LA CONFIG
				//////logger.info("Entro para actualizar los productos");
				StringBuilder query = new StringBuilder("UPDATE Productos SET FK01_Configuracion_Precio = null WHERE FK01_Configuracion_Precio="+idConfig);
				//				////logger.info(query);
				super.jdbcTemplate.update(query.toString(), map);
				if (this.eliminarConfiguracionProductoVende(idConfig)){
					this.actualizarProveedor_FUAProductos(config.getIdProveedor());					
					return idConfig;
				}
			}

			return -1L;

		} catch (Exception e) {
			//logger.error("Error: " + e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, config,"actualizarProductos: " + actualizarProductos);
			return -1L;
		}
	}

	@SuppressWarnings("unchecked")
	public Long insertarConfiguracionProductosVende( ConfiguracionPrecioProducto config, Boolean actualizarProductos ) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("actualizarProductos", actualizarProductos);
			map.put("config", config);
			
			Long idconfigP = 0L;
			Boolean actualizar=false;

			if (config.getNivel().equalsIgnoreCase("Familia")){
				idconfigP = config.getIdConfiguracionFamilia();
				actualizar = true;

			}
			if (config.getNivel().equalsIgnoreCase("Costo")){
				if (config.getIdConfiguracionCosto()>0){
					actualizar=true;
					idconfigP = config.getIdConfiguracionCosto();

				}else{
					actualizar=false;
					idconfigP = config.getIdConfiguracionFamilia();

				}
			}
			if(config.getNivel().equals("Clasificacion")){
				if(config.getIdConfiguracion() != null){

					if(config.getIdConfiguracion().compareTo(config.getIdConfiguracionFamilia()) == 0 ){
						actualizar= false;
						idconfigP = config.getIdConfiguracionFamilia();
						////logger.info("entro a Clasificacion y su IdConfiguracionFamilia es: "+idconfigP+" INSERTA");

					}else{
						actualizar = true;
						idconfigP = config.getIdConfiguracion();
						////logger.info("entro a Clasificacion y su IdConfiguracionClasificacion es: "+idconfigP+" ACTUALIZA");
					}
				}else{
					actualizar= false;
					idconfigP = config.getIdConfiguracionFamilia();
					////logger.info("entro a Clasificacion y su IdConfiguracionFamilia es: "+idconfigP+" INSERTA");
				}
			}
			if (config.getNivel().equalsIgnoreCase("Producto")){
				idconfigP = config.getIdConfiguracion();
				List<MultiusosValores> valores= this.findNivelConfiguracionPrecio(idconfigP);
				MultiusosValores current = valores.get(0);
				String nivel = current.getValorString1();
				//////logger.info("el nivel que viene es: " + config.getNivel() + " y el nivel que se tiene en BD: " + nivel);
				//////logger.info("el idconfiguracion es: " + idconfigP);

				if(config.getNivel().equalsIgnoreCase(nivel) && !config.getNivel().equalsIgnoreCase("Familia")){
					actualizar=true;
				}else{
					actualizar=false;
				}
				//////logger.info("entro a Producto: "+idconfigP+" idCONFIGURACION: "+config.getIdConfiguracion()+ ", idCONFAMILIA: "+config.getIdConfiguracionFamilia());
			}

			if (actualizar){

				Long idCosto = 0L;
				Long idConfig = 0L;

				StringBuilder sbQuery = new StringBuilder("DECLARE @idDelete AS Integer=" + idconfigP + " ");
				sbQuery.append("SELECT cp.PK_Configuracion_Precio valorLong4,cp.FK02_Costo_Factor valorLong5,cp.FK03_Tiempo_Entrega valorLong6, null valorDouble7, null valorDouble8,null valorString1, null valorString2, null valorString3 ");
				sbQuery.append("FROM Configuracion_Precio AS cp ");
				sbQuery.append("WHERE cp.PK_Configuracion_Precio=@idDelete ");

				List<MultiusosValores> datosABorrar = super.jdbcTemplate.query(sbQuery.toString(),new MultiusosMixProveedorRowMapper());
				for (int i=0; i<datosABorrar.size(); i++){
					MultiusosValores obj = datosABorrar.get(i);
					idCosto = obj.getValorLong5();
					idConfig = obj.getValorLong4();
				}

				CostoFactor costo = config.getCostoFactorProducto();
				costo.setIdCostoFactor(idCosto);

				Boolean licencia = false;
				if((config.getProducto().getLicencia() == null)||(config.getProducto().getLicencia().equals(""))){
					licencia = false;
				}else{
					licencia = true;
					this.actualizarMontoPorcentajeLicenciaCosto(idCosto, config.getProducto().getLicencia(), costo.getMontoLicencia(), costo.getPorcentajeLicencia());
				}


				if(this.actualizarCostoFactor(costo,idConfig,licencia, config.getCompuestaCostoF(), config.getCompuestaFactorU() )){
					//actualizo la lista de tiempos de entrega por ruta
					List<TiempoEntrega> TiemposEntregaPorRuta=config.getTiempoEntregaRuta();
					//Recorremos la lista de tiempos de entrega para verificar si lleva un idConfiguracion
					for(TiempoEntrega tiempoEntregaRuta:TiemposEntregaPorRuta){
						//						////logger.info("ACTUALIZAR recorriendo el for");
						tiempoEntregaRuta.setIdConfiguracionPrecio(idconfigP);
						//si cada uno de los registros trae un idTiempoEntregaRuta en este caso usaremos el idTiempoentrega		
						if(tiempoEntregaRuta.getIdTiempoEntrega()!=null && tiempoEntregaRuta.getIdTiempoEntrega()!=0){
							if(!this.actualizarTiempoEntregaRuta(tiempoEntregaRuta,0L)){
								throw new ProquifaNetException("No se ACTUALIZO el registro de la tabla TIEMPO ENTREGA RUTA");
							}
						}else{
							if(!this.insertarTiempoEntregaRuta(tiempoEntregaRuta,0L)){
								throw new ProquifaNetException("No se INSERTO el registro de la tabla TIEMPO ENTREGA RUTA");
							}
						}
					}

					return idConfig;
				}else{
					//////logger.info("No se ACTUALIZO el registro de la tabla COSTO FACTOR");
					throw new ProquifaNetException("No se ACTUALIZO el registro de la tabla COSTO FACTOR");
				}

			}else{
				//////logger.info("+++++++++++ ENTRO PARA INSERTAR +++++++++++++"+config.getNivel()+"--"+config.getProducto().getCategoriaPrecioLista());
				//	SE GENERA UN REGISTRO NUEVO EN LA TABLA DE COSTO FACTOR, PARA QUE ESTE SE VINCULE CON CONFIGURACION PRECIO, PERO CON NIVEL DE COSTO
				//	EL REGISTRO VA EN NULL TODOS SUS CAMPOS
				CostoFactor costoFactor = new CostoFactor();
				costoFactor = config.getCostoFactorProducto();

				Boolean licencia = false;
				if((config.getProducto().getLicencia() == null)||(config.getProducto().getLicencia().equals("")) || (config.getProducto().getLicencia().length() < 2)){
					licencia = false;
				}else{
					licencia = true;
				}

				Long idCostoFactor = insertarCostoFactor(costoFactor, licencia, config.getCompuestaCostoF(), config.getCompuestaFactorU());
				if (idCostoFactor< 0) {
					throw new ProquifaNetException("No se creo el registro de la tabla COSTO FACTOR");
				}
				if(licencia){
					this.actualizarMontoPorcentajeLicenciaCosto(idCostoFactor, config.getProducto().getLicencia(), costoFactor.getMontoLicencia(), costoFactor.getPorcentajeLicencia());
				}
				//	SE GENERA UN REGISTRO NUEVO EN LA TABLA DE TIEMPO ENTREGA, PARA QUE ESTE SE VINCULE CON CONFIGURACION PRECIO, PERO CON NIVEL DE COSTO
				//	EL REGISTRO VA EN NULL TODOS SUS CAMPOS

				Long idConfigQQ = 0L;
				Long idtiempoEntrega=null;

				//	SE INSERTA UNA NUEVA CONFIGURACION DE PRECIO, DE NIVEL COSTO, CON LOS VALORES DE LA FAMILIA 
				//	( TIPO, SUBTIPO, CONTROL, NIVEL, PROVEEDOR, IDCOSTOFACTOR, IDTIEMPOENTREGA )


//				StringBuilder sbQueryPrint = new StringBuilder(" ");
//				sbQueryPrint.append(" DECLARE	@varTipo 	AS integer = (SELECT PK_Folio FROM ValorCombo WHERE Concepto='TipoProducto' AND Valor="+config.getProducto().getTipo()+") ");
//				sbQueryPrint.append(" DECLARE	@varSubTipo AS integer = (SELECT PK_Folio FROM ValorCombo WHERE Concepto='SubTipoProducto' AND Valor="+config.getProducto().getSubtipo()+")");
//				sbQueryPrint.append(" DECLARE	@varControl AS integer = (SELECT PK_Folio FROM ValorCombo WHERE Concepto='Control' AND Valor="+config.getProducto().getControl()+") ");
//				sbQueryPrint.append(" DECLARE	@varIndus AS integer = (SELECT PK_Folio FROM ValorCombo WHERE Concepto='Industrial' AND Valor="+config.getProducto().getIndustria()+") ");
//				sbQueryPrint.append(" INSERT INTO Configuracion_Precio(Tipo, Subtipo, Control, Nivel, FK01_Proveedor, FK02_Costo_Factor, FK03_Tiempo_Entrega, FUA,Industria) ");	
//				sbQueryPrint.append(" VALUES(@varTipo, @varSubTipo, @varControl, "+config.getNivel()+", "+config.getIdProveedor()+", "+idCostoFactor+", "+idtiempoEntrega+", GETDATE(),@varIndus) ");
//				//////logger.info("Query que truena: " + sbQueryPrint.toString());

				Object[] params =  {config.getProducto().getTipo(),	config.getProducto().getSubtipo(),	config.getProducto().getControl(),
						config.getProducto().getIndustria(),config.getNivel(),	config.getIdProveedor(),idCostoFactor,	idtiempoEntrega};

				StringBuilder sbQuery = new StringBuilder(" ");
				sbQuery.append(" DECLARE	@varTipo 	AS integer = (SELECT PK_Folio FROM ValorCombo WHERE Concepto='TipoProducto' AND Valor=?) ");
				sbQuery.append(" DECLARE	@varSubTipo AS integer = (SELECT PK_Folio FROM ValorCombo WHERE Concepto='SubTipoProducto' AND Valor=?)");
				sbQuery.append(" DECLARE	@varControl AS integer = (SELECT PK_Folio FROM ValorCombo WHERE Concepto='Control' AND Valor=?) ");
				sbQuery.append(" DECLARE	@varIndus AS integer = (SELECT PK_Folio FROM ValorCombo WHERE Concepto='Industrial' AND Valor=?) ");
				sbQuery.append(" INSERT INTO Configuracion_Precio(Tipo, Subtipo, Control, Nivel, FK01_Proveedor, FK02_Costo_Factor, FK03_Tiempo_Entrega, FUA,Industria) ");	
				sbQuery.append(" VALUES(@varTipo, @varSubTipo, @varControl, ?, ?, ?, ?, GETDATE(),@varIndus) ");
				super.jdbcTemplate.update(sbQuery.toString(), map);
				idConfigQQ = super.queryForLong("SELECT IDENT_CURRENT ('Configuracion_Precio') ");


				//////logger.info("Este es el idConfigQQ: "+idConfigQQ);
				//////logger.info("ESTE ES LA CATEGORIAPRECIO: "+config.getProducto().getCategoriaPrecioLista());
				if (idConfigQQ > 0) {

					//Insertar los tiempos de entrega por ruta
					//////logger.info("INSERTAR tiempos de entrega por ruta");
					List<TiempoEntrega> tiemposEntregaPorRuta=config.getTiempoEntregaRuta();
					//Recorremos la lista de tiempos de entrega para verificar si lleva un idConfiguracion
					//recorremos la lista de tiempos de entrega por ruta
					
					for(TiempoEntrega tiempoEntregaRuta:tiemposEntregaPorRuta){
						//////logger.info("INSERTAR Recorriendo el for");
						tiempoEntregaRuta.setIdConfiguracionPrecio(idConfigQQ);
						//si cada uno de los registros trae un idTiempoEntregaRuta en este caso usaremos el idTiempoentrega
						if(!this.insertarTiempoEntregaRuta(tiempoEntregaRuta,0L)){
							//////logger.info("No se ACTUALIZO el registro de la tabla TIEMPO ENTREGA");
							throw new ProquifaNetException("No se CREO el registro de la tabla TIEMPO ENTREGA RUTA");
						}

					}

					if(actualizarProductos){
						if(config.getProducto().getSubtipo()!=null){
							if (config.getNivel().equalsIgnoreCase("Costo")){
								//////logger.info("ENTRO A COSTO: ");
								if (!this.actualizarProductos_ConfigPrecioCosto(config, idConfigQQ)){
									throw new ProquifaNetException("No se actualizaron los PRODUCTOS en la categoria COSTO");
								}
							} else if (config.getNivel().equalsIgnoreCase("Clasificacion")){
								//////logger.info("ENTRO A Clasificacion: ");
								if (!this.actualizarProductos_ConfigPrecioClasificacion(config, idConfigQQ)){
									throw new ProquifaNetException("No se actualizaron los Clasificacion en la categoria PRODUCTO");
								}else{
									this.actualizarIdConfigClasificacion(config, idConfigQQ);
								}
							}else if (config.getNivel().equalsIgnoreCase("Producto")){
								//////logger.info("ENTRO A PRODUCTO: ");
								if (!this.actualizarProductosPorProducto(config, idConfigQQ)){
									throw new ProquifaNetException("No se actualizaron los PRODUCTOS en la categoria PRODUCTO");
								}
							}
						}
					}
					return idConfigQQ;

				}else{
					throw new ProquifaNetException("No se creo el registro de la CONFIGURACION PRECIO");
				}
			}

		}catch(ProquifaNetException e){
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, config,"actualizarProductos: " + actualizarProductos);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		//	logger.error(e.getMessage());
			return -1L;
		}
	}

/*
 * (non-Javadoc)
 * @see mx.com.proquifa.proquifanet.persistencia.catalogos.CatalogoProveedorDAO#findConfiguracionProductosVende(java.lang.Long)
 StringBuilder sbQuery = new StringBuilder(" \n SELECT 0 FactorClienteConf,CP.PK_Configuracion_Precio,IND.Valor AS Industria,VCTP.Valor AS Tipo,	COALESCE(VCST.Valor,'') AS SubTipo,	COALESCE(VCCTRL.Valor,'') AS Control, ");
			sbQuery.append(" \n CP.Nivel, CP.FK01_Proveedor AS idProveedor, CF.PK_Costo_Factor AS idCostoFactor, CF.Costo_Consularizacion,CF.FactorDTA,CF.Honorarios, ");
			sbQuery.append(" \n CF.Flete_Documentacion,	NULL Porciento, CP.FUA,CF.Factor_IGI, CF.Factor_CostoFijo, CF.Factor_Utilidad, TE.PK_Tiempo_Entrega AS idTEntrega, NULL IDConfCosto, ");
			sbQuery.append(" \n CF.Permiso, CF.AlmacenDestino,CF.FactorDTA,CF.Honorarios, ");
			sbQuery.append(" \n NULL AS TEntrega, NULL AS idConfigFamilia, PROD.idProducto, (PROD.Costo * PROVEE.TC) Costo, PROD.Moneda,PROD.Codigo, PROD.Fabrica, CF.Piezas,");
			sbQuery.append(" \n CF.Factor_Distribuidor AS Distribuidor,CF.Factor_Publico AS Publico, PROD.fk01_Configuracion_precio ConfiguracionPrecio, PROD.fk03_Categoria_PrecioLista PrecioLista, ");
			sbQuery.append(" \n CF.factor_AAplus, CF.factor_AA,CF.factor_AM,CF.factor_AB,CF.factor_MA,CF.factor_MM,CF.factor_MB,CF.factor_FExpress,CF.factor_Stock,CF.factor_Comision, CF.FK01_AgenteAduanal, CF.VALORENADUANA, CF.DESCUENTO, CF.FLETE,");
			sbQuery.append(" \n CF.FK02_LugarAgenteAduanal, CF.FK03_LAAConcepto, 1 Factor, CNFPROD.PK_ConfigPrecio_Producto, 0 Restablecer, CF.Factor_Bajo,  0 RESTABLECER_COSTO,  PROD.Costo costoCompra, ");
			sbQuery.append(" \n null montoLicencia, null PorcentajeLicencia, null idClasificacion, null Stock_Disable, null FleteExpress_Disable, null CompuestaCostoF, null CompuestaFactorU ");
			sbQuery.append(" \n FROM Configuracion_Precio AS CP ");
			sbQuery.append(" \n LEFT JOIN(SELECT * FROM ConfiguracionPrecio_Producto) AS CNFPROD ON CNFPROD.FK02_ConfFamilia = CP.PK_Configuracion_Precio ");
			sbQuery.append(" \n LEFT JOIN(SELECT * FROM Productos) AS PROD ON PROD.idProducto=CNFPROD.FK01_Producto ");
			sbQuery.append(" \n LEFT JOIN(SELECT * FROM Costo_Y_Factor) AS CF ON CF.PK_Costo_Factor = CP.FK02_Costo_Factor ");  
			sbQuery.append(" \n LEFT JOIN(SELECT * FROM Tiempo_Entrega) AS TE ON TE.PK_Tiempo_Entrega = CP.FK03_Tiempo_Entrega "); 
			sbQuery.append(" \n LEFT JOIN(SELECT * FROM ValorCombo WHERE Concepto='TipoProducto') AS VCTP ON VCTP.PK_Folio = CP.Tipo ");  
			sbQuery.append(" \n LEFT JOIN(SELECT * FROM ValorCombo WHERE Concepto='SubTipoProducto') AS VCST ON VCST.PK_Folio = CP.Subtipo ");  
			sbQuery.append(" \n LEFT JOIN(SELECT * FROM ValorCombo WHERE Concepto='Control') AS VCCTRL ON VCCTRL.PK_Folio = CP.Control   ");
			sbQuery.append(" \n LEFT JOIN(SELECT * FROM ValorCombo WHERE Concepto='Industrial') AS IND ON IND.PK_Folio = cp.Industria ");  
			sbQuery.append(" LEFT JOIN(SELECT Clave, CASE WHEN Moneda = MonedaVenta THEN 1 ELSE CASE WHEN Tipo_Cambio IS NULL OR Tipo_Cambio = 0 THEN 1 ELSE Tipo_Cambio END END  AS TC, MonedaVenta FROM Proveedores) AS PROVEE ON PROVEE.Clave = CP.FK01_Proveedor "); 
			sbQuery.append(" WHERE Nivel='Familia'  AND cp.FK01_Proveedor=" + idProveedor);//AND  PROD.Vigente = 1 
 */

	@SuppressWarnings("unchecked")
	public List<ConfiguracionPrecioProducto> findConfiguracionProductosVende(
			Long idProveedor) {
		String sql = "";
		try {
			sql = 	" \n SELECT DISTINCT CP.PK_Configuracion_Precio,IND.Valor AS Industria,VCTP.Valor AS Tipo,	COALESCE(VCST.Valor,'') AS SubTipo,	" +
					" \n COALESCE(VCCTRL.Valor,'') AS Control, CP.Nivel, CP.FK01_Proveedor AS idProveedor,PROD.idProducto, prod.Costo " +
					" \n FROM Configuracion_Precio AS CP  	" +
					" \n LEFT JOIN(SELECT * FROM ConfiguracionPrecio_Producto) AS CNFPROD ON CNFPROD.FK02_ConfFamilia = CP.PK_Configuracion_Precio  	" +
					" \n LEFT JOIN(SELECT * FROM Productos) AS PROD ON PROD.idProducto=CNFPROD.FK01_Producto  	" +
					" \n LEFT JOIN(SELECT * FROM ValorCombo WHERE Concepto='TipoProducto') AS VCTP ON VCTP.PK_Folio = CP.Tipo  	" +
					" \n LEFT JOIN(SELECT * FROM ValorCombo WHERE Concepto='SubTipoProducto') AS VCST ON VCST.PK_Folio = CP.Subtipo  	" +
					" \n LEFT JOIN(SELECT * FROM ValorCombo WHERE Concepto='Control') AS VCCTRL ON VCCTRL.PK_Folio = CP.Control    	" +
					" \n LEFT JOIN(SELECT * FROM ValorCombo WHERE Concepto='Industrial') AS IND ON IND.PK_Folio = cp.Industria  	" +
					" \n LEFT JOIN(SELECT Clave, CASE WHEN Moneda = MonedaVenta THEN 1 ELSE CASE WHEN Tipo_Cambio IS NULL OR Tipo_Cambio = 0 THEN 1 ELSE Tipo_Cambio END END  AS TC, 	" +
					" \n MonedaVenta FROM Proveedores) AS PROVEE ON PROVEE.Clave = CP.FK01_Proveedor  	" +
					" \n WHERE Nivel='Familia'  AND cp.FK01_Proveedor = " + idProveedor;
		//	logger.info(sql);

			return super.jdbcTemplate.query(sql, new ConfiguracionPrecioArbolRowMapper());
		} catch (Exception e) {
		//	logger.error("Error:" + e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sbQuery: " + sql, "idProveedor: " + idProveedor);
			return new ArrayList<ConfiguracionPrecioProducto>();
		}
	}

	@SuppressWarnings("unchecked")
	public Boolean eliminarConfiguracionProductoVende(Long idConfiguracionPrecio) {
		StringBuilder sbQuery = new StringBuilder("DECLARE @idDelete AS Integer=" + idConfiguracionPrecio + " ");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idConfiguracionPrecio", idConfiguracionPrecio);
		
			
			//////logger.info("Entro para actualizar los productos");
			StringBuilder sbQ = new StringBuilder(" " );
			sbQ.append(" DELETE FROM ConfiguracionPrecio_Producto WHERE FK02_ConfFamilia =" + idConfiguracionPrecio);
			super.jdbcTemplate.update(sbQ.toString(),map);
			// SE ELIMINAN LOS PRODUCTOS DE LA TABLA ConfiguracionPrecio_Producto DEACUERDO AL ID, PARA QUE NO ESTEN DISPONIBLES
			sbQuery.append(" SELECT cp.PK_Configuracion_Precio valorLong4,cp.FK02_Costo_Factor valorLong5,cp.FK03_Tiempo_Entrega valorLong6, null valorDouble7, null valorDouble8,CP.FK01_Proveedor valorString1, null valorString2, null valorString3 ");
			sbQuery.append(" FROM Configuracion_Precio AS cp ");
			sbQuery.append(" LEFT JOIN(SELECT * FROM Configuracion_Precio)AS dt ON dt.PK_Configuracion_Precio=@idDelete ");
			sbQuery.append(" LEFT JOIN(SELECT * FROM ValorCombo) AS COMBO ON COMBO.PK_Folio = dt.Industria  ");
			sbQuery.append(" WHERE cp.FK01_Proveedor=dt.FK01_Proveedor AND cp.Tipo=dt.Tipo  ");
			sbQuery.append(" AND COALESCE(CP.Subtipo,1)=COALESCE(dt.Subtipo,1) AND COALESCE(CP.Control,1)=COALESCE(dt.Control,1)  ");
			sbQuery.append(" AND CP.Industria = COMBO.PK_Folio  ");
			//////logger.info(sbQuery.toString());

			List<MultiusosValores> datosABorrar = super.jdbcTemplate.query(sbQuery.toString(),new MultiusosMixProveedorRowMapper());
			// SE OBTIENE UNA LISTA DE TODAS LAS CONFIGURACIONES A BORRAR DE ACUERDO A: TIPO, SUBTIPO, CONTROL Y PROVEEDOR 
			for (int i=0; i<datosABorrar.size(); i++){
				MultiusosValores current = datosABorrar.get(i);
				Long idCosto = current.getValorLong5();
				Long idTiempo = current.getValorLong6();
				Long idConfig = current.getValorLong4();
				String idProvee = current.getValorString1();
				//////logger.info("++++++++++++++++++++++++++++++++++++++  idConfig: "+idConfig+"  costo : "+idCosto+"  tiempo: "+idTiempo);


				// ACTUALIZAMOS LA ConNFIGURACION, PONIENDO EN NULL TANTO EL COSTO COMO EL TIEMPO ENTREGA PARA PODER BORRARLO
				StringBuilder query = new StringBuilder(" UPDATE Configuracion_Precio SET FK02_Costo_Factor = NULL, FK03_Tiempo_Entrega= NULL WHERE PK_Configuracion_Precio="+idConfig);
				super.jdbcTemplate.update(query.toString(),map);

				// SE BORRA EL REGISTRO DE LA TABLA DE COSTO
				if (idCosto>0){
					//////logger.info("entro a eliminar costo"); 
					this.eliminarRegistroCostoFactor(idCosto);
				}
				// SE ELIMINA EL REGISTRO DE LA TABLA DE TIEMPO ENTREGA
				if (idTiempo>0){
					//////logger.info("Entro a eliminar tiempo");
					this.eliminarRegistroTiempoEntrega(idTiempo);
				}
				// SE ELIMINA POR ULTIMO EL REGISTRO DE Cliente, Agente y CONFIGURACION DE PRECIO
				if (idConfiguracionPrecio>0){

					//////logger.info("Entro a eliminar Cliente_Config");
					this.eliminarRegistroClienteConfigPrecio(idConfig);
					//////logger.info("Entro a eliminar AgenteA_Config");
					this.eliminarRegistroAAConfigPrecio(idConfig);
					//////logger.info("Entro a eliminar configPrecio");
					this.eliminarRegistroConfigPrecio(idConfig);
				}
				if (idProvee!= null || idProvee!= ""){
					//////logger.info("Entro para acrualizar el FUA_Productos en Proveedor");
					this.actualizarProveedor_FUAProductos(Long.parseLong(idProvee));
				}
			}

			return true;
		} catch (Exception e) {
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sbQuery: " + sbQuery, "idConfiguracionPrecio: " + idConfiguracionPrecio);
			return false;
		}
	}


	@SuppressWarnings("unchecked")
	public Boolean eliminarConfiguraciones(Long idConfiguracionPrecio) {
		StringBuilder sbQuery = new StringBuilder("DECLARE @idDelete AS Integer=" + idConfiguracionPrecio + " ");
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idConfiguracionPrecio", idConfiguracionPrecio);
		
			sbQuery.append(" SELECT cp.PK_Configuracion_Precio valorLong4,cp.FK02_Costo_Factor valorLong5,cp.FK03_Tiempo_Entrega valorLong6, null valorDouble7, null valorDouble8,CP.FK01_Proveedor valorString1, null valorString2, null valorString3 ");
			sbQuery.append(" FROM Configuracion_Precio AS cp ");
			sbQuery.append(" LEFT JOIN(SELECT * FROM ValorCombo) AS COMBO ON COMBO.PK_Folio = CP.Industria  ");
			sbQuery.append(" WHERE CP.PK_Configuracion_Precio=@idDelete  ");
			List<MultiusosValores> datosABorrar = super.jdbcTemplate.query(sbQuery.toString(),new MultiusosMixProveedorRowMapper());
			// SE OBTIENE UNA LISTA DE TODAS LAS CONFIGURACIONES A BORRAR DE ACUERDO A: TIPO, SUBTIPO, CONTROL Y PROVEEDOR 
			for (int i=0; i<datosABorrar.size(); i++){
				MultiusosValores current = datosABorrar.get(i);
				Long idCosto = current.getValorLong5();
				Long idTiempo = current.getValorLong6();
				Long idConfig = current.getValorLong4();
				String idProvee = current.getValorString1();
				//////logger.info("++++++++++++++++++++++++++++++++++++++  idConfig: "+idConfig+"  costo : "+idCosto+"  tiempo: "+idTiempo);

				// ACTUALIZAMOS LA CANFIGURACION, PONIENDO EN NULL TANTO EL COSTO COMO EL TIEMPO ENTREGA PARA PODER BORRARLO
				StringBuilder query = new StringBuilder(" UPDATE Configuracion_Precio SET FK02_Costo_Factor = NULL, FK03_Tiempo_Entrega= NULL WHERE PK_Configuracion_Precio="+idConfig);
				super.jdbcTemplate.update(query.toString(), map);

				// SE BORRA EL REGISTRO DE LA TABLA DE COSTO
				if (idCosto>0){
					//////logger.info("entro a eliminar costo"); 
					this.eliminarRegistroCostoFactor(idCosto);
				}
				// SE ELIMINA EL REGISTRO DE LA TABLA DE TIEMPO ENTREGA
				if (idTiempo>0){
					//////logger.info("Entro a eliminar tiempo");
					this.eliminarRegistroTiempoEntrega(idTiempo);
				}
				// SE ELIMINA POR ULTIMO EL REGISTRO DE LA CONFIGURACION DE PRECIO
				if (idConfiguracionPrecio>0){
					//////logger.info("Entro a eliminar Cliente_Config");
					this.eliminarRegistroClienteConfigPrecio(idConfig);
					//////logger.info("Entro a eliminar AgenteA_Config");
					this.eliminarRegistroAAConfigPrecio(idConfig);
					//////logger.info("Entro a eliminar configPrecio");
					this.eliminarRegistroConfigPrecio(idConfig);
				}
				if (idProvee!= null || idProvee!= ""){
					//////logger.info("Entro para acrualizar el FUA_Productos en Proveedor");
					this.actualizarProveedor_FUAProductos(Long.parseLong(idProvee));
				}
			}

			return true;
		} catch (Exception e) {
			//logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sbQuery: " + sbQuery, "idConfiguracionPrecio: " + idConfiguracionPrecio);
			return false;
		}
	}


	@SuppressWarnings("unchecked")
	public List<ConfiguracionPrecioProducto> findConfiguracionOferta(Long idConfigPrecio) throws ProquifaNetException {
		String sbQuery = " " ;
		try {
			sbQuery += " \n DECLARE @idConfigPrecio AS Integer=" + idConfigPrecio + " "
					+ " \n SELECT Distinct PROD.idProducto,PROD.Codigo,COALESCE(CPP.FK04_ConfProducto,CPP.FK03_ConfCosto,CPP.FK02_ConfFamilia) PK_Configuracion_Precio,COALESCE(CPP.FK03_ConfCosto,0) FK03_ConfCosto, Prod.TransitoMandatorioMexico, "
					+ " \n CPP.FK02_ConfFamilia  AS idConfigFamilia,IND.Valor AS Industria,VCT.Valor AS Tipo,COALESCE(VCST.Valor,'') AS SubTipo,COALESCE(VCCTRL.Valor,'') AS Control, " 
					+ " \n CP.FUA,CP.Nivel,CP.FK01_Proveedor AS idProveedor,CF.PK_Costo_Factor AS idCostoFactor,CF.Costo_Consularizacion,CF.Flete_Documentacion,CF.Factor_IGI, CF.Permiso,CF.AlmacenDestino, " 
					+ " \n CF.FactorDTA,CF.Honorarios, CF.Factor_CostoFijo,CF.Factor_Utilidad,"
					+ " \n TE.PK_Tiempo_Entrega AS idTEntrega,TE.FK01_RequierePermiso_ExisTE,TE.FK02_RequierePermiso_NoExisTE,TE.FK03_RequierePermiso_No, TE.FK04_FExpress,  "
					+ " \n prod.idProducto,(PROD.Costo * PROVEE.TC) Costo,ROUND( PORC.PORCIENTO,1) AS Porciento,(CASE WHEN prod.Moneda='Pesos' THEN 'MXN' WHEN prod.Moneda='Euros' THEN 'EUR' WHEN Moneda='Dolares' THEN 'USD'   "
					+ " \n WHEN prod.Moneda='Libras' THEN 'LBS' WHEN prod.Moneda='DlCan' THEN 'CAD'END) AS Moneda,prod.Codigo,prod.Fabrica, " 
					+ " \n CF.Factor_Distribuidor AS Distribuidor,CF.Factor_Publico AS Publico, CPP.FK02_ConfFamilia ConfiguracionPrecio, (prod.fk03_Categoria_PrecioLista) PrecioLista, "
					+ " \n CF.factor_AAplus,CF.factor_AA,CF.factor_AM,CF.factor_AB,CF.factor_MA,CF.factor_MM,CF.factor_MB,CF.factor_FExpress,CF.factor_Stock,CF.factor_Comision, CF.Factor_Bajo, CF.VALORENADUANA, CF.DESCUENTO, CF.FLETE, "
					+ " \n CF.FK01_AgenteAduanal, CF.FK02_LugarAgenteAduanal, CF.FK03_LAAConcepto, PROVEE.MonedaVenta, CASE WHEN LICE.Factor = -1 OR  LICE.Factor IS NULL THEN '' ELSE PROD.DepositarioInternacional END Licencia,"
					+ " \n CASE WHEN CPP.FK03_ConfCosto IS NULL THEN 0 ELSE 1 END RESTABLECER_COSTO, PROD.Costo costoCompra, CF.Stock_Disable, CF.FleteExpress_Disable,CF.Piezas, "//
					+ " \n coalesce(CFL.monto,CF.montoLicencia, 0) montoLicencia, case when CPP.FK04_ConfProducto is not null or CPP.FK03_ConfCosto is not null then CFL.Porcentaje  else LICE.Factor end  PorcentajeLicencia,"
					+ " \n coalesce(CompuestaCostoF,1) CompuestaCostoF, coalesce(CompuestaFactorU,1) CompuestaFactorU, "
					+ " \n " +  obtenerConusultaFormulaCatalogo("Producto") + " 1"// la funcion obtenerConsultaFormulaCatalogo siempre arroja un string con una ',' al final. Se pone esta columna para evitar un error en la consulta
					+ " \n FROM ConfiguracionPrecio_Producto AS CPP  "
					+ " \n LEFT JOIN(SELECT * FROM Configuracion_Precio) AS CP ON CP.PK_Configuracion_Precio = COALESCE(CPP.FK04_ConfProducto,CPP.FK08_ConfClasificacion,CPP.FK03_ConfCosto,CPP.FK02_ConfFamilia) "
					+ " \n LEFT JOIN(SELECT Clave, CASE WHEN Moneda = MonedaVenta THEN 1 ELSE CASE WHEN Tipo_Cambio IS NULL OR Tipo_Cambio = 0 THEN 1 ELSE Tipo_Cambio END END  AS TC, MonedaVenta FROM Proveedores) AS PROVEE ON PROVEE.Clave = CP.FK01_Proveedor "
					+ " \n LEFT JOIN(SELECT * FROM Productos) AS PROD ON PROD.idProducto= CPP.FK01_Producto "
					+ " \n LEFT JOIN(SELECT * FROM Costo_Y_Factor) AS CF ON CF.PK_Costo_Factor=CP.FK02_Costo_Factor    "
					+ " \n LEFT JOIN(SELECT * FROM Tiempo_Entrega) AS TE ON TE.PK_Tiempo_Entrega = CP.FK03_Tiempo_Entrega   "
					+ " \n LEFT JOIN(SELECT * FROM ValorCombo WHERE Concepto='TipoProducto') AS VCT ON VCT.PK_Folio=CP.Tipo    "
					+ " \n LEFT JOIN(SELECT * FROM ValorCombo WHERE Concepto='SubTipoProducto') AS VCST ON VCST.PK_Folio=CP.Subtipo    "
					+ " \n LEFT JOIN(SELECT * FROM ValorCombo WHERE Concepto='Control') AS VCCTRL ON VCCTRL.PK_Folio=CP.Control    "
					+ " \n LEFT JOIN(SELECT * FROM ValorCombo WHERE Concepto='Industrial') AS IND ON IND.PK_Folio = CP.Industria  "
					+ " \n LEFT JOIN (SELECT PK_Lugar_AgenteAduanal, FK01_AgenteAduanal, Monto, Porcentaje FROM Lugar_AgenteAduanal) AS LAA ON LAA.PK_Lugar_AgenteAduanal = CF.FK02_LugarAgenteAduanal"
					+ " \n LEFT JOIN (SELECT * FROM LAA_Concepto) AS LAAC ON LAAC.PK_AAConcepto = CF.FK03_LAAConcepto"
					+ " \n LEFT JOIN(SELECT PROD.idProducto,PROD.Proveedor, PROD.FK01_Configuracion_Precio, PROD.Costo,((((SUM(CPP.CostoNuevo - CPP.CostoAnTErior))/case when (COUNT(CPP.CostoNuevo)) = 0 then 1 else COUNT(CPP.CostoNuevo) end)*100 )/(CPP.CostoNuevo "   
					+ " \n          -((SUM(CPP.CostoNuevo - CPP.CostoAnTErior))/case when (COUNT(CPP.CostoNuevo)) = 0 then 1 else COUNT(CPP.CostoNuevo) end))) AS PORCIENTO   "
					+ " \n         FROM Productos AS PROD   "
					+ " \n          LEFT JOIN(SELECT MAX (PK_CambioPrecioProducto) ID, FK01_Producto FROM CambioPrecioProducto GROUP BY FK01_Producto) AS ID ON ID.FK01_Producto =PROD.idProducto "  
					+ " \n          LEFT JOIN(SELECT * FROM CambioPrecioProducto) AS CPP ON CPP.PK_CambioPrecioProducto = ID.ID    "
					+ " \n          GROUP BY CPP.CostoNuevo,PROD.Proveedor,PROD.FK01_Configuracion_Precio, PROD.Costo,PROD.idProducto) " 
					+ " \n          AS PORC ON PORC.idProducto = PROD.idProducto  "
					+ " \n LEFT JOIN(SELECT * FROM Licencia) AS LICE ON LICE.FK01_Proveedor = CP.FK01_Proveedor AND LICE.Tipo = PROD.DepositarioInternacional"
					+ " \n LEFT JOIN (SELECT * FROM CostoFactor_Licencia) CFL ON CFL.FK01_CostoFactor = CF.PK_Costo_Factor AND CFL.Licencia = PROD.DepositarioInternacional"
					+ " \n WHERE CPP.FK02_ConfFamilia = @idConfigPrecio  AND PROD.Vigente = 1 "
					+ " \n  ORDER BY Tipo,SubTipo,Control ASC";

		//	logger.info(sbQuery.toString());

			 return super.jdbcTemplate.query(sbQuery, new ConfiguracionPrecioMontoRowMapper());

		} catch (Exception e) {
		//	logger.error("Error:" + e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sbQuery: " + sbQuery, "idConfigPrecio: " + idConfigPrecio);
			return new ArrayList<ConfiguracionPrecioProducto>();
		}
	}

	


	public Long insertarCostoFactor(CostoFactor costoFactor, Boolean licencia, Boolean compuestaCostoF, Boolean compuestaFactorU) throws ProquifaNetException {
		StringBuilder sbQuery = new StringBuilder("INSERT INTO Costo_Y_Factor ("); //Factor_Utilidad,
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("compuestaFactorU", compuestaFactorU);
			map.put("compuestaCostoF", compuestaCostoF);
			map.put("licencia", licencia);
			map.put("costoFactor", costoFactor);
			
			Object[] params = {costoFactor.getCostoConsularizacion(),costoFactor.getFleteDocumentacion(),costoFactor.getFactorIGI(),
					costoFactor.getFactorCostoFijo(),				//costoFactor.getFactorUtilidad(),
					costoFactor.getFactorDistribuidor(),costoFactor.getFactorPublico(),costoFactor.getPermiso(),
					costoFactor.getAlmacenDestino(),costoFactor.getFactorDTA(), costoFactor.getFactor_AAplus(),		//costoFactor.getHonorarios(),costoFactor.getFactor_FExpress(),
					costoFactor.getFactor_AA(),costoFactor.getFactor_AM(),costoFactor.getFactor_AB(),costoFactor.getFactor_MA(),costoFactor.getFactor_MM(),costoFactor.getFactor_MB(),
					costoFactor.getFactor_Stock(), costoFactor.getFactor_Comision(), costoFactor.getFactor_Bajo(), costoFactor.getFactorValorEnAduana(),
					costoFactor.getFactorDescuento(), costoFactor.getFactorFletePC(), costoFactor.getStockDisable(), costoFactor.getFleteExpressDisable(), costoFactor.getPiezas(), compuestaCostoF, compuestaFactorU};
			sbQuery.append(" Costo_Consularizacion, Flete_Documentacion, Factor_IGI, Factor_CostoFijo,  "); //Factor_Utilidad,
			sbQuery.append(" Factor_Distribuidor, Factor_Publico, Permiso, AlmacenDestino, FactorDTA,  "); //Honorarios,
			sbQuery.append(" factor_AAplus,factor_AA,factor_AM,factor_AB,factor_MA,factor_MM,factor_MB, ");//factor_FExpress,
			sbQuery.append(" factor_Stock,factor_Comision, Factor_Bajo, ValorEnAduana, Descuento, Flete, ");
			sbQuery.append(" Stock_Disable, FleteExpress_Disable, Piezas, CompuestaCostoF, CompuestaFactorU ");
			if(costoFactor.getIdAgenteAduanal() != null){
				if(costoFactor.getIdAgenteAduanal() > 0){
					sbQuery.append(" ,FK01_AgenteAduanal, FK02_LugarAgenteAduanal, FK03_LAAConcepto ");
				}
			}
			if(licencia == false){
				sbQuery.append(" , montoLicencia,PorcentajeLicencia  ");
			}

			sbQuery.append(") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?");
			if(costoFactor.getIdAgenteAduanal() != null){
				if(costoFactor.getIdAgenteAduanal() > 0){
					sbQuery.append(" ," + costoFactor.getIdAgenteAduanal() +"," + costoFactor.getIdLugarAgenteAduanal()+","+ costoFactor.getIdLugarConcepto());
				}
			}
			if(licencia == false){
				sbQuery.append(" , " + costoFactor.getMontoLicencia() + ", " + costoFactor.getPorcentajeLicencia() );
			}
			sbQuery.append(" )");

			//////logger.info(sbQuery.toString());
			super.jdbcTemplate.update(sbQuery.toString(), map);
			return super.queryForLong("SELECT IDENT_CURRENT ('Costo_Y_Factor')");

		} catch (Exception e) {
			//logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sbQuery: " + sbQuery, "costoFactor: " + costoFactor, "licencia: " + licencia,
					"compuestaCostoF: " + compuestaCostoF, "compuestaFactorU: " + compuestaFactorU);
			return -1L;
		}
	}

	public Long insertarTiempoEntrega(TiempoEntrega tiempoEntrega) {
		StringBuilder sbQuery = new StringBuilder("INSERT INTO Tiempo_Entrega(FK01_RequierePermiso_ExisTE,FK02_RequierePermiso_NoExisTE,FK03_RequierePermiso_No) ");
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tiempoEntrega", tiempoEntrega);
			
			Object[] params={ tiempoEntrega.getRequierePExiste(), tiempoEntrega.getRequierePNoExiste(), tiempoEntrega.getRequierePNo()	};

			//// Se comparte el metodo entre Cat.Proveedor y Cat. Clientes

			//			StringBuilder sbQuery = new StringBuilder("INSERT INTO Tiempo_Entrega(RequierePermiso_Existe,RequierePermiso_NoExiste,RequierePermiso_No, FExpress) ");
			sbQuery.append("VALUES(?,?,?) ");
			super.jdbcTemplate.update(sbQuery.toString(), map);

			Long idTE = super.queryForLong("SELECT IDENT_CURRENT ('Tiempo_Entrega')");
			return idTE;
		} catch (Exception e) {
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sbQuery: " + sbQuery, tiempoEntrega);
			return -1L;
		}
	}

	@Override
	public Boolean insertarTiempoEntregaRuta(TiempoEntrega tiempoEntrega,Long idCliente) {
		StringBuilder sbQuery = new StringBuilder("");
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCliente", idCliente);
			map.put("tiempoEntrega", tiempoEntrega);
			Object[] params={ tiempoEntrega.getIdConfiguracionPrecio(),tiempoEntrega.getRuta(), tiempoEntrega.getRequierePExiste(), tiempoEntrega.getRequierePNoExiste(), tiempoEntrega.getRequierePNo(), tiempoEntrega.getFactorFlexibilidad()};

			//// Se comparte entre Cat.Proveedor y Cat. Clientes
			if(idCliente>0){//cat. clientes
				sbQuery = new StringBuilder("INSERT INTO Cliente_Tiempo_Entrega_Ruta(FK01_ClienteConfiguracionPrecio,Ruta,RequierePermiso_Existe,RequierePermiso_NoExiste,RequierePermiso_No, FactorFlexibilidad) ");
				sbQuery.append("VALUES(?,?,?,?,?,?) ");
			}else{//Cat. proveedores
				sbQuery = new StringBuilder("INSERT INTO Tiempo_Entrega_Ruta(FK01_Configuracion_Precio,Ruta,RequierePermiso_Existe,RequierePermiso_NoExiste,RequierePermiso_No, FactorFlexibilidad) ");
				sbQuery.append("VALUES(?,?,?,?,?,?) ");
			}


			//logger.info(sbQuery.toString());
			super.jdbcTemplate.update(sbQuery.toString(), map);
			//logger.info(sbQuery.toString());
			return true;
		} catch (Exception e) {
			//logger.error("INSERT INTO Tiempo_Entrega_Ruta(FK01_Configuracion_Precio,Ruta,RequierePermiso_Existe,RequierePermiso_NoExiste,RequierePermiso_No,FactorFlexibilidad) VALUES("+tiempoEntrega.getIdConfiguracionPrecio()+","+tiempoEntrega.getRuta()+","+tiempoEntrega.getRequierePExiste()+","+tiempoEntrega.getRequierePNoExiste()+","+tiempoEntrega.getRequierePNo()+","+tiempoEntrega.getFactorFlexibilidad()+") ");
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sbQuery: " + sbQuery,tiempoEntrega);
			e.printStackTrace();
			return false;
		}
	}


	public Boolean actualizarProductos_ConfigPrecioCosto(ConfiguracionPrecioProducto config, Long idConfig) {
		StringBuilder query = new StringBuilder(" ");
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idConfig", idConfig);
			map.put("config", config);
			
			//////logger.info("----------------         actualizarProductos_ConfigPrecioCosto");
			List<Producto> prod = this.findProductosPorCosto(config.getProducto()); 
			for (Producto r:prod){
				query.append(" UPDATE ConfiguracionPrecio_Producto SET FK03_ConfCosto="+idConfig );
				query.append(" WHERE FK01_Producto="+r.getIdProducto()+" AND FK02_ConfFamilia="+config.getIdConfiguracion());
				//////logger.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++    "+query.toString());
				super.jdbcTemplate.update(query.toString(),map);
			}

			return true;
		} catch (Exception e) {
			//logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"query: " + query,config,"idConfig: " + idConfig);
			return false;
		}
	}

	public Boolean actualizarProductos_ConfigPrecioClasificacion(ConfiguracionPrecioProducto config, Long idConfig) {
		StringBuilder query = new StringBuilder(" ");
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("config", config);
			map.put("idConfig", idConfig);
			//////logger.info("----------------         actualizarProductos_ConfigPrecioClasificacion");
			query.append( "UPDATE ConfiguracionPrecio_Producto SET FK08_ConfClasificacion = " + idConfig );
			query.append(" WHERE FK01_Producto in ( select idProducto from Productos where FK04_Clasificacion_ConfiguracionPrecio ="+config.getIdClasificacion() + ")");
			query.append(" AND FK02_ConfFamilia="+config.getIdConfiguracion());
			//////logger.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++    "+query.toString());
			super.jdbcTemplate.update(query.toString(), map);


			return true;
		} catch (Exception e) {
		//	logger.error("Error: " + e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"query: " + query,config,"idConfig: " + idConfig);
			return false;
		}
	}

	public Boolean actualizarIdConfigClasificacion(ConfiguracionPrecioProducto config, Long idConfig) {
		StringBuilder query = new StringBuilder(" ");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("config", config);
			map.put("idConfig", idConfig);
			//////logger.info("----------------         actualizarIdConfigClasificacion");
			query.append(" UPDATE ConfiguracionPrecio_Clasificacion SET FK02_ConfigClasificacion="+idConfig );
			query.append(" WHERE PK_ConfiguracionPrecioClasificacion="+config.getIdClasificacion() );
			super.jdbcTemplate.update(query.toString(), map);
			//////logger.info(query.toString());
			return true;
		} catch (Exception e) {
			//logger.error("Error: " + e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"query: " + query,config,"idConfig: " + idConfig);
			return false;
		}
	}

	public Boolean actualizarProductosPorProducto(ConfiguracionPrecioProducto config, Long idConfig) {
		StringBuilder query = new StringBuilder(" ");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("config", config);
			map.put("idConfig", idConfig);
			//////logger.info("FK04_ConfProducto: "+idConfig+",FK01_Producto: "+config.getProducto().getIdProducto()+",FK02_ConfFamilia: "+config.getIdConfiguracionFamilia());
			query.append(" UPDATE ConfiguracionPrecio_Producto SET FK04_ConfProducto="+idConfig );
			query.append(" WHERE FK01_Producto="+config.getProducto().getIdProducto()+" AND FK02_ConfFamilia="+config.getIdConfiguracionFamilia());
			super.jdbcTemplate.update(query.toString(), map);
			return true;
		} catch (Exception e) {
			////logger.info("Error: " + e.getMessage());
		//	logger.error("Error: " + e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"query: " + query,config,"idConfig: " + idConfig);
			return false;
		}
	}
	public Boolean actualizarTiempoEntrega(TiempoEntrega tiempoEntrega, Long idConfigPrecio) {
		StringBuilder sbQuery = new StringBuilder("");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idConfigPrecio", idConfigPrecio);
			map.put("tiempoEntrega", tiempoEntrega);
			//// Se comparte el metodo entre Cat.Proveedor y Cat. Clientes
			//			sbQuery.append("  UPDATE Tiempo_Entrega SET RequierePermiso_Existe= '").append(tiempoEntrega.getRequierePExiste()).append("'");
			//			sbQuery.append(" ,RequierePermiso_NoExiste= '").append(tiempoEntrega.getRequierePNoExiste()).append("'");
			//			sbQuery.append(" ,RequierePermiso_No= '").append(tiempoEntrega.getRequierePNo()).append("'");
			//			sbQuery.append(" ,FExpress= '").append(tiempoEntrega.getFleteExpress()).append("'");
			//			sbQuery.append("  WHERE PK_Tiempo_Entrega = ").append(tiempoEntrega.getIdTiempoEntrega());

			if(idConfigPrecio != 0){
				this.actualizarFUAConfigPrecio(idConfigPrecio);
			}
			//			,,
			sbQuery.append("  UPDATE Tiempo_Entrega SET FK01_RequierePermiso_ExisTE= '").append(tiempoEntrega.getRequierePExiste()).append("'");
			sbQuery.append(" ,FK02_RequierePermiso_NoExisTE= '").append(tiempoEntrega.getRequierePNoExiste()).append("'");
			sbQuery.append(" ,FK03_RequierePermiso_No= '").append(tiempoEntrega.getRequierePNo()).append("'");
			//			sbQuery.append(" ,FExpress= '").append(tiempoEntrega.getFleteExpress()).append("'");
			sbQuery.append("  WHERE PK_Tiempo_Entrega = ").append(tiempoEntrega.getIdTiempoEntrega());
			//////logger.info(sbQuery.toString());
			super.jdbcTemplate.update(sbQuery.toString(), map);
			return true;
		} catch (Exception e) {
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sbQuery: " + sbQuery,tiempoEntrega,"idConfigPrecio: " + idConfigPrecio);
			return false;
		}
	}

	@Override
	public Boolean actualizarTiempoEntregaRuta(TiempoEntrega tiempoEntrega,Long idCliente) throws ProquifaNetException {
		StringBuilder sbQuery = new StringBuilder("");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCliente", idCliente);
			map.put("tiempoEntrega", tiempoEntrega);
			//Se comparte el metodo entre Cat.Proveedores y Cat.Clientes
			if(tiempoEntrega.getIdConfiguracionPrecio() != 0){
				this.actualizarFUAConfigPrecio(tiempoEntrega.getIdConfiguracionPrecio());
			}
			if(idCliente>0){//cat. clientes
				sbQuery=new StringBuilder("");
				sbQuery.append("  UPDATE Cliente_Tiempo_Entrega_Ruta SET RequierePermiso_Existe= '").append(tiempoEntrega.getRequierePExiste()).append("'");
				sbQuery.append(" ,RequierePermiso_NoExiste= '").append(tiempoEntrega.getRequierePNoExiste()).append("'");
				sbQuery.append(" ,RequierePermiso_No= '").append(tiempoEntrega.getRequierePNo()).append("'");
//				sbQuery.append(" ,FactorFlexibilidad= '").append(tiempoEntrega.getFactorFlexibilidad()).append("'");
				sbQuery.append("  WHERE PK_Cliente_Tiempo_Entrega_Ruta = ").append(tiempoEntrega.getIdTiempoEntrega());
			}else{//Cat. proveedores
				sbQuery=new StringBuilder("");
				sbQuery.append("  UPDATE Tiempo_Entrega_Ruta SET RequierePermiso_Existe= '").append(tiempoEntrega.getRequierePExiste()).append("'");
				sbQuery.append(" ,RequierePermiso_NoExiste= '").append(tiempoEntrega.getRequierePNoExiste()).append("'");
				sbQuery.append(" ,RequierePermiso_No= '").append(tiempoEntrega.getRequierePNo()).append("'");
//				sbQuery.append(" ,FactorFlexibilidad= '").append(tiempoEntrega.getFactorFlexibilidad()).append("'");
				sbQuery.append("  WHERE PK_Tiempo_Entrega_Ruta = ").append(tiempoEntrega.getIdTiempoEntrega());
			}
			//////logger.info(sbQuery.toString());
			super.jdbcTemplate.update(sbQuery.toString(), map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sbQuery: " + sbQuery,tiempoEntrega,"idCliente: " + idCliente);
			return false;
		}
	}

	public Boolean actualizarMontoPorcentajeLicenciaCosto(Long idCostoFactor, String licencia, Double monto, Double porcentaje){
		String sql  = "";
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("porcentaje", porcentaje);
			map.put("monto", monto);
			map.put("licencia", licencia);
			map.put("idCostoFactor", idCostoFactor);
			sql = "UPDATE CostoFactor_Licencia SET Monto = " + monto + ", Porcentaje = " + porcentaje + " WHERE FK01_CostoFactor = " + idCostoFactor + " AND Licencia = '" + licencia + "' ";
			//////logger.info(sql);
			int r = 0;

			r = super.jdbcTemplate.update(sql, map);

			if(r == 0){
				sql = " INSERT INTO CostoFactor_Licencia (FK01_CostoFactor,Licencia, Monto, Porcentaje) VALUES (" + idCostoFactor + ",'" + licencia + "'," + monto + ", " + porcentaje + ")";
				r =	super.jdbcTemplate.update(sql, map);
				//////logger.info(sql);
			}

			return true;
		}catch(Exception e){
			//logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sql: " + sql,"idCostoFactor: " + idCostoFactor,"licencia: " + licencia,
					"monto: " + monto, "porcentaje: " + porcentaje);
			return false;
		}

	}

	public Boolean actualizarConfiguracionPrecio(Long idConfig, String nivel, Long costoFactor, Long tiempoEntrega) {
		StringBuilder sbQuery = new StringBuilder("");
		
		String sql  = "";
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tiempoEntrega", tiempoEntrega);
			map.put("idConfig", idConfig);
			
			map.put("nivel", nivel);
			map.put("costoFactor", costoFactor);
			String campos="";
			if(!nivel.equals("")){
				campos = campos + "Nivel='" + nivel + "',";
			}
			if(costoFactor!=0){
				campos = campos + "FK02_Costo_Factor='" + costoFactor + "',";
			}
			/*if(tiempoEntrega!=0){
				campos = campos + "FK03_Tiempo_Entrega='" + tiempoEntrega +"',";
			}*/

			campos = campos.substring(0,campos.length() - 1);

			sql=("UPDATE Configuracion_Precio SET FUA=GETDATE(), " + campos +" WHERE PK_Configuracion_Precio="+ idConfig);
			//			//log.info(sbQuery);
			super.jdbcTemplate.update(sql, map);

			return true;
		} catch (Exception e) {
			//logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sbQuery: " + sbQuery,"idConfig: " + idConfig, "nivel: " + nivel,
					"costoFactor: " + costoFactor, "tiempoEntrega: " + tiempoEntrega);
			return false;
		}
	}



	public Boolean actualizarCostoFactor(CostoFactor costoFactor, Long idConfiguracion, Boolean licencia, 
			Boolean compuestaCostoF, Boolean compuestaFactorU) throws ProquifaNetException {
		StringBuilder sbQuery = new StringBuilder("");
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("compuestaFactorU", compuestaFactorU);
			map.put("compuestaCostoF", compuestaCostoF);
			map.put("licencia", licencia);
			map.put("idConfiguracion", idConfiguracion);
			map.put("costoFactor", costoFactor);
			
			//			sbQuery.append(" , Honorarios= ").append(costoFactor.getHonorarios());
			//			sbQuery.append(" , Factor_Utilidad= ").append(costoFactor.getFactorUtilidad());   
			this.actualizarFUAConfigPrecio(idConfiguracion);
			Object[] params={ costoFactor.getStockDisable(),costoFactor.getFleteExpressDisable(), compuestaCostoF, compuestaFactorU};
			sbQuery.append(" UPDATE Costo_Y_Factor SET ");  
			sbQuery.append("   Costo_Consularizacion= ").append(costoFactor.getCostoConsularizacion());  
			sbQuery.append(" , Flete_Documentacion= ").append(costoFactor.getFleteDocumentacion());  
			sbQuery.append(" , Factor_IGI=").append(costoFactor.getFactorIGI());  
			sbQuery.append(" , Factor_CostoFijo= ").append(costoFactor.getFactorCostoFijo());  
			sbQuery.append(" , Factor_Distribuidor= ").append(costoFactor.getFactorDistribuidor());  
			sbQuery.append(" , Factor_Publico= ").append(costoFactor.getFactorPublico());  
			sbQuery.append(" , Permiso= ").append(costoFactor.getPermiso());  
			sbQuery.append(" , AlmacenDestino= ").append(costoFactor.getAlmacenDestino());  
			sbQuery.append(" , FactorDTA= ").append(costoFactor.getFactorDTA());  
			sbQuery.append(" , factor_AAplus= ").append(costoFactor.getFactor_AAplus());
			sbQuery.append(" , factor_AA= ").append(costoFactor.getFactor_AA());
			sbQuery.append(" , factor_AM= ").append(costoFactor.getFactor_AM());
			sbQuery.append(" , factor_AB= ").append(costoFactor.getFactor_AB());
			sbQuery.append(" , factor_MA= ").append(costoFactor.getFactor_MA());
			sbQuery.append(" , factor_MM= ").append(costoFactor.getFactor_MM());
			sbQuery.append(" , factor_MB= ").append(costoFactor.getFactor_MB());
			sbQuery.append(" , factor_FExpress= ").append(costoFactor.getFactor_FExpress());
			sbQuery.append(" , factor_Stock= ").append(costoFactor.getFactor_Stock());
			sbQuery.append(" , factor_Comision= ").append(costoFactor.getFactor_Comision());
			sbQuery.append(" , Factor_Bajo= ").append(costoFactor.getFactor_Bajo());
			sbQuery.append(" , ValorEnAduana= ").append(costoFactor.getFactorValorEnAduana());
			sbQuery.append(" , Descuento= ").append(costoFactor.getFactorDescuento());
			sbQuery.append(" , Flete= ").append(costoFactor.getFactorFletePC());
			sbQuery.append(" , FK01_AgenteAduanal= ").append(costoFactor.getIdAgenteAduanal());
			sbQuery.append(" , FK02_LugarAgenteAduanal= ").append(costoFactor.getIdLugarAgenteAduanal());
			sbQuery.append(" , FK03_LAAConcepto = ").append(costoFactor.getIdLugarConcepto());
			sbQuery.append(" , Stock_Disable = ?");
			sbQuery.append(" , FleteExpress_Disable = ?");
			sbQuery.append(" , CompuestaCostoF = ?");
			sbQuery.append(" , CompuestaFactorU = ?");
			sbQuery.append(" , Piezas = ").append(costoFactor.getPiezas());
			if(licencia == false){
				sbQuery.append(" , montoLicencia = ").append(costoFactor.getMontoLicencia());
				sbQuery.append(" , PorcentajeLicencia = ").append(costoFactor.getPorcentajeLicencia());
			}
			sbQuery.append(" WHERE PK_Costo_Factor=").append(costoFactor.getIdCostoFactor());
			//////logger.info(sbQuery.toString());

			super.jdbcTemplate.update(sbQuery.toString(), map);
			return true;
		} catch (Exception e) {
			//logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sbQuery: " + sbQuery,"costoFactor: " + costoFactor,
					"idConfiguracion: " + idConfiguracion, "licencia: " + licencia, "compuestaCostoF: " + compuestaCostoF,
					"compuestaFactorU: " + compuestaFactorU);
			return false;
		}
	}

	public Boolean setConfiguracionPrecioAProducto(Long idProducto, Long idConfiguracion) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idProducto", idProducto);
			map.put("idConfiguracion", idConfiguracion);
			
			super.jdbcTemplate.update("UPDATE Productos SET FK01_Configuracion_Precio=" + idConfiguracion + " WHERE idProducto=" + idProducto, map);
			return true;
		} catch (Exception e) {
			//logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"idProducto: " + idProducto,"idConfiguracion: " + idConfiguracion);
			return false;
		}
	}


	@SuppressWarnings("unchecked")
	public Boolean reintegrarConfiguracion(Long idConfiguracion) {
		StringBuilder sbQuery = new StringBuilder(" ");
		try {
			//////logger.info("EL IDConfiguracion que me llega es: " + idConfiguracion);
			sbQuery.append("\n SELECT CONF.PK_Configuracion_Precio AS valorLong4, ");
			sbQuery.append("\n CASE WHEN CONF.Nivel = 'Producto' THEN COALESCE(COSTO.PK_Configuracion_Precio,FAMILY.PK_Configuracion_Precio) ELSE FAMILY.PK_Configuracion_Precio END valorLong5, ");
			sbQuery.append("\n CONF.FK02_Costo_Factor valorLong6, CONF.FK03_Tiempo_Entrega valorString1, CONF.Nivel valorString2, NULL valorString3, NULL valorDouble7, NULL valorDouble8 ");
			sbQuery.append("\n FROM Configuracion_Precio AS CONF  ");
			sbQuery.append("\n LEFT JOIN (SELECT * FROM Configuracion_Precio WHERE Nivel='Familia') AS FAMILY ON FAMILY.Tipo = CONF.Tipo AND COALESCE(FAMILY.Subtipo,1)=COALESCE(CONF.Subtipo,1) AND  COALESCE(FAMILY.Control,1)=COALESCE(CONF.Control,1) ");   
			sbQuery.append("\n 	AND FAMILY.FK01_Proveedor= CONF.FK01_Proveedor AND FAMILY.Industria=CONF.Industria ");
			sbQuery.append("\n LEFT JOIN (SELECT * FROM Configuracion_Precio WHERE Nivel='Costo') AS COSTO ON COSTO.Tipo = CONF.Tipo AND COALESCE(COSTO.Subtipo,1)=COALESCE(CONF.Subtipo,1) AND  COALESCE(COSTO.Control,1)=COALESCE(CONF.Control,1) ");   
			sbQuery.append("\n 	AND COSTO.FK01_Proveedor= CONF.FK01_Proveedor AND COSTO.Industria=CONF.Industria ");
			sbQuery.append("\n WHERE CONF.PK_Configuracion_Precio=").append(idConfiguracion);
			//logger.info(sbQuery.toString());
			List<MultiusosValores> datosABorrar =  super.jdbcTemplate.query(sbQuery.toString(), new MultiusosMixProveedorRowMapper());

			MultiusosValores valor = datosABorrar.get(0);

			Long idConfBorrar = valor.getValorLong4();
			Long idConfNuevo = valor.getValorLong5();
			Long idCostoFactor = valor.getValorLong6();
			/*Long idTiempoEntrega = 0L;
			if(valor.getValorString1() != null){
				idTiempoEntrega = Long.parseLong(valor.getValorString1());
			}*/
			String nivel = valor.getValorString2();

			//////logger.info("idConfBorrar: "+idConfBorrar);
			//////logger.info("idConfNuevo: "+idConfNuevo);
			//////logger.info("idCostoFactor: "+idCostoFactor);
			//////logger.info("idTiempoEntrega: "+idTiempoEntrega);
			//////logger.info("nivel: "+nivel);


			if (this.actualizarProductosAntiguaConfiguracion(idConfBorrar,idConfNuevo,nivel)){
				if (this.actualizarConfigPrecioNULL(idConfBorrar)){
					if (this.eliminarRegistroCostoFactorLicencia(idCostoFactor)){
						if (this.eliminarRegistroCostoFactor(idCostoFactor)){
							//if (this.eliminarRegistroTiempoEntrega(idTiempoEntrega)){
							if (this.eliminarRegistroTiempoEntregaRuta(idConfiguracion)){
									if(this.eliminarRegistroAAConfigPrecio(idConfBorrar)){
										if(nivel.equals("Clasificacion")){
											this.actualizarConfiguracionPrecio_Clasificacion(idConfBorrar);
										}
										if (this.eliminarRegistroConfigPrecio(idConfBorrar)){
											return true;
										}else
											throw new ProquifaNetException("Error al intentar eliminar el registro en CONFIGURACION PRECIO");
									}else
										throw new ProquifaNetException("Error al intentar eliminar el registro en REGISTRO AGENTE");							
							}else
								throw new ProquifaNetException("Error al intentar eliminar el registro en TIEMPO ENTREGA");
						}else
							throw new ProquifaNetException("Error al intentar eliminar el registro en COSTO FACTOR");
					}else
						throw new ProquifaNetException("Error al intentar eliminar el registro en COSTO FACTOR LICENCIA");
				}else
					throw new ProquifaNetException("Error al intentar actualizar la CONFIGURACION PRECIO a NULL");
			}else 
				throw new ProquifaNetException("Error al intentar actualizar el PRODUCTO");

		}catch(ProquifaNetException e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sbQuery: " + sbQuery,"idConfiguracion: " + idConfiguracion);
			return false;
		}
	}

	public Boolean actualizarConfiguracionProducto(Long idConfigActual,String nivel) throws ProquifaNetException {
		StringBuilder sql = new StringBuilder("");
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nivel", nivel);
			map.put("idConfigActual", idConfigActual);
		
			if (nivel.equalsIgnoreCase("Costo")){
				sql.append(" UPDATE ConfiguracionPrecio_Producto SET FK03_ConfCosto= NULL ");
				sql.append(" WHERE FK02_ConfFamilia= ").append(idConfigActual);
				sql.append(" AND FK01_Producto= ").append(idConfigActual);

			}


			//////logger.info(sql);
			super.jdbcTemplate.update(sql.toString(), map);
			return true;
		}catch(Exception e){
		//	logger.error("Error: " + e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sql: " + sql,"idConfigActual: " + idConfigActual,
					"nivel: " + nivel);
			return false;
		}
	}



	public Boolean actualizarProductosAntiguaConfiguracion(Long idConfigActual,Long idConfigAntiguo, String nivel) throws ProquifaNetException {
		String query = "";
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nivel", nivel);
			map.put("idConfigActual", idConfigActual);
			map.put("idConfigAntiguo", idConfigAntiguo);		
			String co="";
			if (nivel.equalsIgnoreCase("Costo")){
				co= "FK03_ConfCosto = NULL WHERE FK02_ConfFamilia="+idConfigAntiguo+" AND FK03_ConfCosto="+idConfigActual;
			}else if (nivel.equalsIgnoreCase("Producto")){
				co= "FK04_ConfProducto = NULL WHERE FK04_ConfProducto="+idConfigActual;
			}else if(nivel.equalsIgnoreCase("Clasificacion")){
				co= "FK08_ConfClasificacion = NULL WHERE FK08_ConfClasificacion="+idConfigActual;
			}
			query=" UPDATE ConfiguracionPrecio_Producto SET " +co;

			//////logger.info(query);
			super.jdbcTemplate.update(query, map);
			return true;
		}catch(Exception e){
			//logger.error("Error: " + e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"query: " + query,"idConfigActual: " + idConfigActual,
					"nivel: " + nivel,"idConfigAntiguo: " + idConfigAntiguo);
			return false;
		}
	}

	public Boolean actualizarConfigPrecioNULL(Long idConfigPrecio) throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idConfigPrecio", idConfigPrecio);
		
			Object[] args={idConfigPrecio};
			super.jdbcTemplate.update(" UPDATE Configuracion_Precio SET FK02_Costo_Factor=NULL, FK03_Tiempo_Entrega=NULL WHERE PK_Configuracion_Precio=?", map);
			return true;
		}catch(Exception e){
			//logger.error("Error: " + e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"idConfigPrecio: " + idConfigPrecio);
			return false;
		}
	}

	public Boolean actualizarFUAConfigPrecio(Long idConfigPrecio) throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idConfigPrecio", idConfigPrecio);
			
			super.jdbcTemplate.update(" UPDATE Configuracion_Precio SET FUA=GETDATE() WHERE PK_Configuracion_Precio="+idConfigPrecio, map);
			return true;
		}catch(Exception e){
			//logger.error("Error: " + e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"idConfigPrecio: " + idConfigPrecio);
			return false;
		}
	}

	public Boolean eliminarRegistroCostoFactor(Long idCostoFactor) throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCostoFactor", idCostoFactor);
			
			super.jdbcTemplate.update(" DELETE Costo_Y_Factor WHERE PK_Costo_Factor="+idCostoFactor+"", map);
			return true;
		}catch(Exception e){
		//	logger.error("Error: " + e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"idCostoFactor: " + idCostoFactor);
			return false;
		}
	}
	public Boolean eliminarRegistroCostoFactorLicencia(Long idCostoFactor) throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCostoFactor", idCostoFactor);
			
			super.jdbcTemplate.update(" DELETE CostoFactor_Licencia WHERE FK01_CostoFactor="+idCostoFactor+"", map);
			return true;
		}catch(Exception e){
		//	logger.error("Error: " + e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"idCostoFactor: " + idCostoFactor);
			return false;
		}
	}

	public Boolean eliminarRegistroAAConfigPrecio(Long idConfigPrecio) throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idConfigPrecio", idConfigPrecio);
			super.jdbcTemplate.update(" DELETE AA_ConfiguracionPrecio WHERE FK02_ConfiguracionPrecio="+idConfigPrecio+"", map);
			return true;
		}catch(Exception e){
		//	logger.error("Error: " + e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"idConfigPrecio: " + idConfigPrecio);
			return false;
		}
	}
	public Boolean eliminarRegistroClienteConfigPrecio(Long idConfigPrecio) throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idConfigPrecio", idConfigPrecio);
			super.jdbcTemplate.update(" DELETE Cliente_ConfiguracionPrecio WHERE FK02_ConfigPrecio="+idConfigPrecio+"", map);
			return true;
		}catch(Exception e){
			//logger.error("Error: " + e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"idCostoFactor: " + idConfigPrecio);
			return false;
		}
	}

	public Boolean eliminarRegistroTiempoEntrega(Long idTiempoEntrega) throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idTiempoEntrega", idTiempoEntrega);
			super.jdbcTemplate.update(" DELETE Tiempo_Entrega WHERE PK_Tiempo_Entrega="+idTiempoEntrega+"", map);
			return true;
		}catch(Exception e){
			//logger.error("Error: " + e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"idTiempoEntrega: " + idTiempoEntrega);
			return false;
		}
	}

	public Boolean eliminarRegistroTiempoEntregaRuta(Long idConfiguracion) throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idConfiguracion", idConfiguracion);
			super.jdbcTemplate.update(" DELETE Tiempo_Entrega_Ruta WHERE FK01_Configuracion_Precio="+idConfiguracion+"", map);
			return true;
		}catch(Exception e){
			//logger.error("Error: " + e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"idConfiguracion: " + idConfiguracion);
			return false;
		}
	}

	public Boolean eliminarRegistroConfigPrecio(Long idConfigPrecio) throws ProquifaNetException {
		try{
			//////logger.info("id config a eliminaar: "+idConfigPrecio); 
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idConfigPrecio", idConfigPrecio);
			super.jdbcTemplate.update(" DELETE Configuracion_Precio WHERE PK_Configuracion_Precio="+idConfigPrecio+"", map);
			return true;
		}catch(Exception e){
		//	logger.error("Error: " + e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"idConfigPrecio: " + idConfigPrecio);
			return false;
		}
	}
	public Boolean actualizarConfiguracionPrecio_Clasificacion(Long idConfigPrecio) throws ProquifaNetException {
		try{
			//////logger.info("id config a update ConfiguracionPrecio_Clasificacion: "+idConfigPrecio); 
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idConfigPrecio", idConfigPrecio);
			super.jdbcTemplate.update(" UPDATE ConfiguracionPrecio_Clasificacion SET FK02_ConfigClasificacion = NULL WHERE FK02_ConfigClasificacion="+idConfigPrecio+"", map);
			return true;
		}catch(Exception e){
			//logger.error("Error: " + e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"idConfigPrecio: " + idConfigPrecio);
			return false;
		}
	}

	public Boolean eliminarFabricanteProveedor(Long idProveedor, Long idFabricante) throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idFabricante", idFabricante);
			map.put("idProveedor", idProveedor);
			Object[] args={idProveedor,idFabricante};
			super.jdbcTemplate.update("DELETE FROM ProveedorFabricante WHERE FK01_Proveedor= ? AND FK02_Fabricante= ? ", map);
			this.actualizarProveedor_FUAMarcas(idProveedor);
			return true;
		}catch(Exception e){
			//logger.error("Error: " + e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"idProveedor: " + idProveedor, "idFabricante: " + idFabricante);
			return false;
		}
	}

	public Boolean existeFabricanteProveedor(Long idProveedor, Long idFabricante) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idFabricante", idFabricante);
			map.put("idProveedor", idProveedor);
			if (idProveedor>0 && idFabricante>0)
			{
				Object[] args={idProveedor,idFabricante};
				Long existeFabricante=super.queryForLong("Select COUNT(*) AS cuantos from ProveedorFabricante where FK01_Proveedor= ? AND FK02_Fabricante= ? ", map);
				return existeFabricante>0? true : false;
			}else{
				return null;
			}
		} catch (Exception e) {
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"idProveedor: " + idProveedor, "idFabricante: " + idFabricante);
			return false;
		}
	}

	public Boolean insertarFabricanteProveedor(Long idProveedor, Long idFabricante) throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idFabricante", idFabricante);
			map.put("idProveedor", idProveedor);
			Object[] args={idProveedor,idFabricante};
			super.jdbcTemplate.update(" INSERT INTO ProveedorFabricante(FK01_Proveedor,FK02_Fabricante) VALUES(?,?)", map);
			this.actualizarProveedor_FUAMarcas(idProveedor);
			return true;
		}catch(Exception e){
		//	logger.error("Error: " + e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"idProveedor: " + idProveedor, "idFabricante: " + idFabricante);
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Fabricante> findFabricantesProveedor(Long idProveedor) throws ProquifaNetException {
		StringBuilder sql=new StringBuilder("");
		try{
		
		Map<String, Object> map = new HashMap<String, Object>();
				map.put("idProveedor", idProveedor);
			
			sql=new StringBuilder(" SELECT PF.FK02_Fabricante")
			.append(" FROM ProveedorFabricante AS PF ")
			.append(" WHERE PF.FK01_Proveedor=").append(idProveedor);

			return super.jdbcTemplate.query(sql.toString(),map, new FabricanteProveedorRowMapper());
		}catch(Exception e){
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "sql: " + sql,"idProveedor: " + idProveedor);
			return new ArrayList<Fabricante>();
		}

	}

	@SuppressWarnings("unchecked")
	public List<ConfiguracionPrecioProducto> findConfiguracionFamilia(Long idConfig, Long idCliente, String tipoNivel) throws ProquifaNetException {
		StringBuilder sql=new StringBuilder("");
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tipoNivel", tipoNivel);
			map.put("idCliente", idCliente);
			map.put("idConfig", idConfig);
		
			//////logger.info("++++++++++++ findConfiguracionFamilia +++++++++++++++++ "+idConfig);
			sql.append(" \n DECLARE @idCliente AS Integer=" + idCliente + "\n ");
			sql.append(" \n SELECT Distinct CP.PK_Configuracion_Precio,IND.Valor AS Industria,VCT.Valor AS Tipo,CP.PK_Configuracion_Precio idConfigFamilia,COALESCE(VCST.Valor,'') AS SubTipo,COALESCE(VCCTRL.Valor,'') AS Control, " ); 
			sql.append(" \n CP.Nivel,CP.FK01_Proveedor AS idProveedor,CF.PK_Costo_Factor AS idCostoFactor,CF.Costo_Consularizacion,CF.Flete_Documentacion,CF.Factor_IGI,CF.Permiso, CF.AlmacenDestino,CF.FactorDTA,CF.Honorarios,  " );
			sql.append(" \n CF.Factor_Utilidad, null montoLicencia, null PorcentajeLicencia, " );
			sql.append(" \n CF.Factor_Distribuidor AS Distribuidor,CF.Factor_Publico AS Publico, CF.VALORENADUANA, CF.DESCUENTO, CF.FLETE, CF.factor_AAplus,CF.factor_AA,CF.factor_AM,CF.factor_AB," );
			sql.append(" \n CF.factor_MA,CF.factor_MM,CF.factor_MB,CF.factor_FExpress,CF.factor_Stock,CF.factor_Comision, CF.Factor_Bajo, PROD.Costo costoCompra,CF.Stock_Disable, CF.FleteExpress_Disable,CF.Piezas, 0 idClasificacion,");
			if(idCliente > 0){
				sql.append(" \n CASE WHEN CliCP.Factor IS NOT NULL THEN 1 ELSE 0 END FactorClienteConf");
				sql.append(" \n ,CASE WHEN CliCP.Factor is not null THEN CliCP.Factor ELSE CF.Factor_"+ tipoNivel +" END  Factor, cpp.PK_ConfigPrecio_Producto, CASE WHEN CliCP.Factor is not null  THEN 1 ELSE 0 END  Restablecer, CliCP.FUA,");
				sql.append(" \n 0 RESTABLECER_COSTO, CASE WHEN CliCP.Factor is not null  THEN 1 ELSE 0 END  Restablecer,");
				sql.append(" \n CASE WHEN CliCP.FK04_AgenteAduanal is not null THEN CliCP.FK04_AgenteAduanal ELSE CF.FK01_AgenteAduanal END FK01_AgenteAduanal, ");
				sql.append(" \n CASE WHEN CliCP.FK05_LugarAgenteAduanal is not null THEN CliCP.FK05_LugarAgenteAduanal ELSE CF.FK02_LugarAgenteAduanal END FK02_LugarAgenteAduanal,  ");
				sql.append(" \n CASE WHEN CliCP.Fk06_LAAConcepto  is not null THEN CliCP.Fk06_LAAConcepto ELSE CF.FK03_LAAConcepto END FK03_LAAConcepto, ");
				sql.append(" \n CASE WHEN CliCP.CostoFijo IS NOT NULL THEN CliCP.CostoFijo ELSE CF.Factor_CostoFijo END Factor_CostoFijo,");
				sql.append(" \n coalesce(CliCP.CompuestaCostoF, CF.CompuestaCostoF, 1 ) CompuestaCostoF,");
				sql.append(" \n coalesce( CliCP.CompuestaFactorU, CF.CompuestaFactorU, 1 ) CompuestaFactorU,");
				sql.append("\n (SELECT Ruta  from Clientes  WHERE  Clave=@idCliente ) AS RutaCliente , ");
			}else{
				sql.append("\n 0 FactorClienteConf, 1 Factor, 1 PK_ConfigPrecio_Producto, 0 Restablecer, CP.FUA, 0 RESTABLECER_COSTO,");
				sql.append("\n CF.FK01_AgenteAduanal, CF.FK02_LugarAgenteAduanal, CF.FK03_LAAConcepto , CF.Factor_CostoFijo, coalesce(CF.CompuestaCostoF , 1 ) CompuestaCostoF, coalesce( CF.CompuestaFactorU , 1 ) CompuestaFactorU,");
			}
			sql.append(" \n CPP.FK01_Producto idProducto,NULL Costo,NULL Porciento,NULL AS Moneda,NULL Codigo,NULL Fabrica,NULL Codigo,NULL idProducto, NULL ConfiguracionPrecio,");
			sql.append(" \n NULL PrecioLista,  NULL IDConfCosto" );
			sql.append(" \n FROM Configuracion_Precio AS CP " );
			sql.append(" \n LEFT JOIN(SELECT * FROM Tiempo_Entrega) AS TE ON TE.PK_Tiempo_Entrega = CP.FK03_Tiempo_Entrega " );
			sql.append(" \n LEFT JOIN(SELECT * FROM Costo_Y_Factor) AS CF ON CF.PK_Costo_Factor = CP.FK02_Costo_Factor " );
			sql.append(" \n LEFT JOIN (SELECT FK02_ConfFamilia, MAX(FK01_Producto) FK01_Producto, MAX(PK_ConfigPrecio_Producto) PK_ConfigPrecio_Producto FROM ConfiguracionPrecio_Producto GROUP BY FK02_ConfFamilia) CPP ON CPP.FK02_ConfFamilia = CP.PK_Configuracion_Precio" );
			sql.append(" \n LEFT JOIN(SELECT * FROM Productos) AS PROD ON PROD.FK01_Configuracion_Precio = CPP.FK01_Producto  " );
			sql.append(" \n LEFT JOIN(SELECT * FROM IndustriaProducto) AS INDPROD ON INDPROD.FK02_Producto = PROD.idProducto " );
			sql.append(" \n LEFT JOIN(SELECT * FROM ValorCombo WHERE Concepto='TipoProducto') AS VCT ON VCT.PK_Folio=CP.Tipo " );  
			sql.append(" \n LEFT JOIN(SELECT * FROM ValorCombo WHERE Concepto='SubTipoProducto') AS VCST ON VCST.PK_Folio=CP.Subtipo " );  
			sql.append(" \n LEFT JOIN(SELECT * FROM ValorCombo WHERE Concepto='Control') AS VCCTRL ON VCCTRL.PK_Folio=CP.Control   " );
			sql.append(" \n LEFT JOIN(SELECT * FROM ValorCombo WHERE Concepto='Industrial') AS IND ON IND.PK_Folio = INDPROD.FK01_Industria   " );
			if(idCliente > 0){
				sql.append(" \n LEFT JOIN (SELECT FK02_ConfFamilia, FK05_CliFamilia FROM ConfiguracionPrecio_Producto) AS CPP1 ON CPP1.FK02_ConfFamilia = CP.PK_Configuracion_Precio");
				sql.append(" \n LEFT JOIN (SELECT * FROM Cliente_ConfiguracionPrecio) AS CliCP ON CliCP.AK_ClienteConfigPrecio = CPP1.FK05_CliFamilia AND CliCP.FK01_Cliente = @idCliente \n");// AND CliCP.tipo = '" + tipoNivel + "'
				sql.append(" \n LEFT JOIN(SELECT * FROM Tiempo_Entrega) AS TEC ON TEC.PK_Tiempo_Entrega = CliCP.FK03_TiempoEntrega " );
			}
			sql.append("\n  WHERE CP.PK_Configuracion_Precio=").append(idConfig);//.append(" AND PROD.Vigente = 1");
//			logger.info("Consulta de proveedor"+sql.toString());

			List<ConfiguracionPrecioProducto> list = super.jdbcTemplate.query(sql.toString(),map, new ConfiguracionPrecioRowMapper());
			if(idCliente > 0){//cat. clientes

				for (ConfiguracionPrecioProducto configuracionPrecio : list) {					
					if(!configuracionPrecio.getFactorClienteConfiguracion()){//Cuando el factor es nulo
						sql = new StringBuilder("");
						sql.append(" \n SELECT PK_Tiempo_Entrega_Ruta, FK01_Configuracion_Precio,Ruta, RequierePermiso_Existe,RequierePermiso_NoExiste,RequierePermiso_No, FactorFlexibilidad ");
						sql.append(" \n FROM Tiempo_Entrega_Ruta ");
						sql.append(" \n WHERE FK01_Configuracion_Precio=").append(configuracionPrecio.getIdConfiguracion());
					}else{
						sql = new StringBuilder("");
						sql.append(" \n SELECT PK_Cliente_Tiempo_Entrega_Ruta AS PK_Tiempo_Entrega_Ruta,FK01_ClienteConfiguracionPrecio AS FK01_Configuracion_Precio,Ruta,RequierePermiso_Existe,RequierePermiso_NoExiste,RequierePermiso_No, FactorFlexibilidad ");
						sql.append(" \n FROM ConfiguracionPrecio_Producto ");
						sql.append(" \n LEFT JOIN Cliente_ConfiguracionPrecio ON ConfiguracionPrecio_Producto.FK05_CliFamilia=Cliente_ConfiguracionPrecio.AK_ClienteConfigPrecio ");
						sql.append(" \n LEFT JOIN Cliente_Tiempo_Entrega_Ruta ON Cliente_Tiempo_Entrega_Ruta.FK01_ClienteConfiguracionPrecio=Cliente_ConfiguracionPrecio.PK_ClienteConfiguracionPrecio ");
						sql.append(" \n WHERE  PK_ConfigPrecio_Producto=").append(configuracionPrecio.getIdConfiguracionPrecioProducto()).append(" AND Cliente_ConfiguracionPrecio.FK01_Cliente=").append(idCliente).append(" AND PK_Cliente_Tiempo_Entrega_Ruta IS NOT NULL");
					}
					List<TiempoEntrega> listTE = super.jdbcTemplate.query(sql.toString(),map, new TiempoEntregaRutaProveedorRowMapper());
					configuracionPrecio.setTiempoEntregaRuta(listTE);
					//////logger.info(configuracionPrecio.getIdConfiguracionPrecioProducto()+">--->"+idCliente+"-----"+sql.toString());
				}
			}



			return list;
		}catch(Exception e){
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "sql: " + sql,"idConfig: " + idConfig,"idCliente: " + idCliente,
					"tipoNivel: " + tipoNivel);
			return new ArrayList<ConfiguracionPrecioProducto>();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TiempoEntrega> findTiempoEntregaPorConfiguracion(Long idConfiguracion)
			throws ProquifaNetException {
		String sbQuery = "";
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idConfiguracion", idConfiguracion);
			
			sbQuery = "" 
					+ " \n SELECT PK_Tiempo_Entrega_Ruta, FK01_Configuracion_Precio,Ruta, RequierePermiso_Existe,RequierePermiso_NoExiste,RequierePermiso_No, FactorFlexibilidad "
					+ " \n FROM Tiempo_Entrega_Ruta "
					+ " \n WHERE FK01_Configuracion_Precio=" + idConfiguracion;

			return super.jdbcTemplate.query(sbQuery,map, new TiempoEntregaRutaProveedorRowMapper());
		}catch(Exception e){
			//logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "sbQuery: " + sbQuery,"idConfiguracion: " + idConfiguracion);
			return new ArrayList<TiempoEntrega>();
		}
	}

	@SuppressWarnings("unchecked")
	public List<MultiusosValores> getIdCategoriaPreciolista(Long idCategoria) throws ProquifaNetException {
		StringBuilder sql = new StringBuilder("");
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCategoria", idCategoria);
			
		    sql=new StringBuilder(" SELECT PK_Categoria valorLong4, PrecioLista valorDouble7, NULL valorDouble8, NULL valorLong5,NULL valorLong6,null valorString1, null valorString2, null valorString3 ");
			sql.append(" FROM categoria_Preciolista WHERE PK_Categoria ="+idCategoria+"");

			
			return super.jdbcTemplate.query(sql.toString(),map,  new MultiusosMixProveedorRowMapper());

		}catch(Exception e){
			//logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "sql: " + sql,"idCategoria: " + idCategoria);
			return new ArrayList<MultiusosValores>();
		}
	}

	@SuppressWarnings("unchecked")
	public List<MultiusosValores> getListaCategoriaPreciolista(ConfiguracionPrecioProducto config, String costo) throws ProquifaNetException {
		StringBuilder sql = new StringBuilder("");
		try{
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("costo", costo);
			map.put("config", config);
	
			
			Producto prod = config.getProducto();
			Object[] args={prod.getTipo(),prod.getSubtipo(),prod.getControl(),config.getIdProveedor()};

		    sql=new StringBuilder("DECLARE	@varTipo AS integer = (SELECT PK_Folio FROM ValorCombo WHERE Concepto='TipoProducto' AND Valor=?) " );
			sql.append(" DECLARE	@varSubTipo AS integer = (SELECT PK_Folio FROM ValorCombo WHERE Concepto='SubTipoProducto' AND Valor=?) ");
			sql.append(" DECLARE	@varControl AS integer = (SELECT PK_Folio FROM ValorCombo WHERE Concepto='Control' AND Valor=?) ");
			sql.append(" SELECT PK_Categoria valorLong4,null valorDouble7, PrecioLista valorDouble8,NULL valorLong5,NULL valorLong6,null valorString1, null valorString2, null valorString3");
			sql.append(" FROM categoria_Preciolista WHERE " );
			if(prod.getTipo() == null || prod.getTipo().equals("")){
				sql.append(" FK02_Tipo IS NULL  AND ");
			}else{
				sql.append(" FK02_Tipo = @varTipo  AND ");
			}
			if(prod.getSubtipo() == null || prod.getSubtipo().equals("")){
				sql.append(" FK03_Subtipo IS NULL AND ");
			}else{
				sql.append(" FK03_Subtipo = @varSubTipo  AND "); 
			}
			if(prod.getControl() == null || prod.getControl().equals("")){
				sql.append(" FK04_Control IS NULL  ");
			}else{
				sql.append(" FK04_Control = @varControl  ");
			}
			sql.append(" AND fk01_proveedor=? ");

			

			List<MultiusosValores> n = this.jdbcTemplate.query(sql.toString(),map, new MultiusosMixProveedorRowMapper());
			return  n;


		}catch(Exception e){
			//logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "sql: " + sql,config, "costo: " + costo);
			return new ArrayList<MultiusosValores>();
		}
	}

	public Boolean deleteCategoriaPrecioLista(Long idCategoria) throws ProquifaNetException {
		StringBuilder 	sql = new StringBuilder("");
		try{
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCategoria", idCategoria);
			
			sql =new StringBuilder(" DELETE Categoria_PrecioLista WHERE PK_Categoria = "+idCategoria+" AND");
			sql.append(" \n (select COUNT(idProducto) t from Productos where FK03_Categoria_PrecioLista = "+idCategoria+" group by FK03_Categoria_PrecioLista ) is null");
			//////logger.info(sql.toString());
			super.jdbcTemplate.update(sql.toString(), map);
			return true;
		}catch(Exception e){
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "sql: " + sql, "idCategoria: " + idCategoria);
			return false;
		}
	}

	public Boolean actualizarCategoriaPrecioLista(Long idCategoria, String costo) throws ProquifaNetException {
		StringBuilder sql = new StringBuilder("");
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("costo", costo);
			map.put("idCategoria", idCategoria);
			
			
			sql=new StringBuilder(" UPDATE Categoria_PrecioLista SET PrecioLista="+costo+" WHERE PK_Categoria="+idCategoria+" ");
			super.jdbcTemplate.update(sql.toString(), map);
			return true;
		}catch(Exception e){
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "sql: " + sql, "idCategoria: " + idCategoria,
					"costo: " + costo);
			return false;
		}
	}

	public Boolean actualizarProductosConfiguracionMonto(Long idConfigActual,Long idConfigAntiguo,String monto) throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("monto", monto);
			map.put("idConfigAntiguo", idConfigAntiguo);
			map.put("idConfigActual", idConfigActual);
			Object[] args={idConfigAntiguo,monto,idConfigActual};
			super.jdbcTemplate.update(" UPDATE Productos SET FK03_Categoria_PrecioLista=?, Costo=?, Fecha= GETDATE() WHERE FK03_Categoria_PrecioLista=?", map);
			return true;
		}catch(Exception e){
		//	logger.error("Error: " + e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"idConfigActual: " + idConfigActual,
					"idConfigAntiguo: " + idConfigAntiguo, "monto: " + monto);
			return false;
		}
	}

	public Boolean actualizarProductosMontoPorClasificacion(Long idClasificacion,Long idConfigAntiguo,Double monto) throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("monto", monto);
			map.put("idConfigAntiguo", idConfigAntiguo);
			map.put("idClasificacion", idClasificacion);
			Object[] args={idConfigAntiguo,monto,idClasificacion};
			super.jdbcTemplate.update(" UPDATE Productos SET FK03_Categoria_PrecioLista=?, Costo=?, Fecha= GETDATE() WHERE FK04_Clasificacion_ConfiguracionPrecio=?", map);
			return true;
		}catch(Exception e){
		//	logger.error("Error: " + e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"idClasificacion: " + idClasificacion,
					"idConfigAntiguo: " + idConfigAntiguo, "monto: " + monto);
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public List<MultiusosValores> getListaProductosXCategoriaPrecio(Long idCategoria) throws ProquifaNetException {
		StringBuilder sql = new StringBuilder("");
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCategoria", idCategoria);

			
			sql=new StringBuilder(" SELECT idProducto valorLong4,null valorDouble7, NULL valorDouble8,NULL valorLong5,NULL valorLong6,null valorString1, null valorString2, null valorString3 FROM Productos WHERE FK03_Categoria_PrecioLista=").append(idCategoria);
			//			//log.info(sql.toString());
			return  this.jdbcTemplate.query(sql.toString(),map, new MultiusosMixProveedorRowMapper());

		}catch(Exception e){
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"idCategoria: " + idCategoria, "idCategoria: " + idCategoria);
			return new ArrayList<MultiusosValores>();
		}
	}

	public Boolean modificarHistorialCambioPrecio(Double costoAnterior, Double costoActual, Long idProducto) throws ProquifaNetException {
		String qw  = "";
		try{
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idProducto", idProducto);
			map.put("costoAnterior", costoAnterior);
			map.put("costoActual", costoActual);

			qw = " SELECT * FROM CambioPrecioProducto WHERE FK01_Producto ="+idProducto+"";
			//			//log.info(qw);
			Long idTE = super.queryForLong(qw);
			if(idTE>0){
				StringBuilder sql=new StringBuilder(" UPDATE CambioPrecioProducto SET Fecha = GETDATE(), CostoAnterior="+costoAnterior+", CostoNuevo="+costoActual+" WHERE FK01_Producto=").append(idProducto);
				//////logger.info(sql.toString());
				super.jdbcTemplate.update(sql.toString(), map);
				return true;
			}
			return false;
		}catch(Exception e){
			//logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"qw: " + qw, "costoAnterior: " + costoAnterior, "costoActual: " + costoActual,
					"idProducto: " + idProducto);
			return false;
		}
	}

	public boolean insertarHistorialCambioPrecio(Double costoAnterior, Double costoActual, Long idProducto) throws ProquifaNetException {
		StringBuilder sql = new StringBuilder("");
		try{	
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idProducto", idProducto);
			map.put("costoAnterior", costoAnterior);
			map.put("costoActual", costoActual);
			
			if (costoAnterior != costoActual){
				sql=new StringBuilder(" INSERT INTO CambioPrecioProducto (Fecha, FK01_Producto, CostoAnterior, CostoNuevo) VALUES (GETDATE(),"+idProducto+","+costoAnterior+","+costoActual+")");			
				super.jdbcTemplate.update(sql.toString(), map);
			}
			return true;

		}catch(Exception e){
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sql: " + sql, "costoAnterior: " + costoAnterior, "costoActual: " + costoActual,
					"idProducto: " + idProducto);
			return false;
		}
	}

	public long obtenerIdCategoriaConPrecioNuevo(ConfiguracionPrecioProducto config, Double costo) throws ProquifaNetException {
		StringBuilder sql = new StringBuilder("");
		Producto prod = config.getProducto();
		try{	
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("costo", costo);
			map.put("config", config);
		
			
			Object[] args={prod.getTipo(),prod.getSubtipo(),prod.getControl(),prod.getProveedor(),costo};
			log.info ("Tipo: "+prod.getTipo()+ " Subtipo: "+prod.getSubtipo()+ " Control: "+prod.getControl() + " idProv: "+ config.getIdProveedor() + " Costo: "+costo);
			sql=new StringBuilder("DECLARE	@varTipo AS integer = (SELECT PK_Folio FROM ValorCombo WHERE Concepto='TipoProducto' AND Valor=?) " );
			sql.append(" DECLARE	@varSubTipo AS integer = (SELECT PK_Folio FROM ValorCombo WHERE Concepto='SubTipoProducto' AND Valor=?) ");
			sql.append(" DECLARE	@varControl AS integer = (SELECT PK_Folio FROM ValorCombo WHERE Concepto='Control' AND Valor=?) ");
			sql.append(" SELECT top 1 PK_Categoria FROM Categoria_PrecioLista WHERE FK01_Proveedor=? AND PrecioLista=? AND ");
			if(prod.getTipo() != null && prod.getTipo().length() > 0){
				sql.append(" FK02_Tipo = @varTipo  AND ");
			}else{
				sql.append(" FK02_Tipo IS NULL  AND ");
			}

			if(prod.getSubtipo() != null && prod.getSubtipo().length() > 0){
				sql.append(" FK03_Subtipo = @varSubTipo  AND "); 
			}else{
				sql.append(" FK03_Subtipo IS NULL AND ");
			}
			if(prod.getControl() != null && prod.getControl().length() > 0){
				sql.append(" FK04_Control = @varControl  ");
			}else{
				sql.append(" FK04_Control IS NULL  ");
			}

			//logger.info(sql.toString());
			long i = this.queryForLong(sql.toString(),map);
			log.info("i:" + i);
			return i;

		}catch(Exception e){
		//	logger.error(e.getMessage());
//			funcion = new Funcion();
//			funcion.enviarCorreoAvisoExepcion(e,"sql: " + sql,config, "costo: " + costo);
			return -1L;
		}
	}
	public boolean existCategoriaConPrecioNuevo(ConfiguracionPrecioProducto config, Double costo) throws ProquifaNetException {
		try{
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("costo", costo);
			map.put("config", config);
		
			Producto prod = config.getProducto();
			Object[] args={prod.getTipo(),prod.getSubtipo(),prod.getControl(),prod.getProveedor(),costo};
			
			StringBuilder sql=new StringBuilder("DECLARE	@varTipo AS integer = (SELECT PK_Folio FROM ValorCombo WHERE Concepto='TipoProducto' AND Valor=?) " );
			sql.append(" DECLARE	@varSubTipo AS integer = (SELECT PK_Folio FROM ValorCombo WHERE Concepto='SubTipoProducto' AND Valor=?) ");
			sql.append(" DECLARE	@varControl AS integer = (SELECT PK_Folio FROM ValorCombo WHERE Concepto='Control' AND Valor=?) ");
			sql.append(" SELECT count(PK_Categoria) FROM Categoria_PrecioLista WHERE FK01_Proveedor=? AND PrecioLista=? AND ");
			if(prod.getTipo() != null && prod.getTipo().length() > 0){
				sql.append(" FK02_Tipo = @varTipo  AND ");
			}else{
				sql.append(" FK02_Tipo IS NULL  AND ");
			}
			
			if(prod.getSubtipo() != null && prod.getSubtipo().length() > 0){
				sql.append(" FK03_Subtipo = @varSubTipo  AND "); 
			}else{
				sql.append(" FK03_Subtipo IS NULL AND ");
			}
			if(prod.getControl() != null && prod.getControl().length() > 0){
				sql.append(" FK04_Control = @varControl  ");
			}else{
				sql.append(" FK04_Control IS NULL  ");
			}

			int i = this.queryForInt(sql.toString(),map);
			
			if(i == 0){
			//	logger.info("Existen categorias: false " + i);
				return false;
			}else{
			//	logger.info("Existen categorias: true" + i);
				return true;
			}
			
		}catch(Exception e){
//			logger.error(e.getMessage());
			return false;
		}
	}

	public boolean actualizarProductoyCategoriaPrecioxProducto(Long idProducto,Long idCambiar, String monto) throws ProquifaNetException {
		StringBuilder query = new StringBuilder(" ");
		try {	
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("monto", monto);
			map.put("idCambiar", idCambiar);
			map.put("idProducto", idProducto);
			
			Object [] obj = {idCambiar,monto,idProducto};	
//			logger.info("idCambiar:"+idCambiar + " monto:"+monto + " idProducto:"+idProducto);
			query.append(" UPDATE Productos SET FK03_Categoria_PrecioLista = ?, Costo = ? WHERE idProducto = ? ");
			super.jdbcTemplate.update(query.toString(), map);			
			return true;
		} catch (Exception e) {
		//	logger.error("Error: " + e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"query: " + query, "idProducto: " + idProducto, "idCambiar: " + idCambiar,
					"monto: " + monto);
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public List<MultiusosValores> verificarExistenHermanosCategoriaPrecio(Long idCategoriaPrecio) throws ProquifaNetException {
		StringBuilder query = new StringBuilder();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCategoriaPrecio", idCategoriaPrecio);
		
			
			query = new StringBuilder("SELECT idProducto valorLong4,NULL valorDouble7, NULL valorDouble8,NULL valorLong5,NULL valorLong6,null valorString1, null valorString2, null valorString3 FROM Productos WHERE FK03_Categoria_PrecioLista="+idCategoriaPrecio+"");

			return super.jdbcTemplate.query(query.toString(),map,  new MultiusosMixProveedorRowMapper());

		} catch (Exception e) {
		//	logger.error("Error: " + e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"query: " + query, "idCategoriaPrecio: " + idCategoriaPrecio);
			return new ArrayList<MultiusosValores>();
		}
	}


	public long insertarCategoriaPrecioLista(ConfiguracionPrecioProducto config, Double costo) throws ProquifaNetException {
		StringBuilder query = new StringBuilder("");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("costo", costo);
			map.put("config", config);
			Producto prod = config.getProducto();
			Object[] args={prod.getTipo(),prod.getSubtipo(),prod.getControl(),costo,prod.getProveedor()};
			
			query = new StringBuilder(" DECLARE	@varTipo AS integer = (SELECT PK_Folio FROM ValorCombo WHERE Concepto='TipoProducto' AND Valor=?) " );
			query.append(" DECLARE	@varSubTipo AS integer = (SELECT PK_Folio FROM ValorCombo WHERE Concepto='SubTipoProducto' AND Valor=?) ");
			query.append(" DECLARE	@varControl AS integer = (SELECT PK_Folio FROM ValorCombo WHERE Concepto='Control' AND Valor=?) ");
			query.append(" INSERT INTO Categoria_PrecioLista VALUES(?,?,@varTipo,@varSubTipo,@varControl)");	
			log.info(query.toString());
			super.jdbcTemplate.update(query.toString(), map);			

			long idCP = super.queryForLong("SELECT IDENT_CURRENT ('Categoria_PrecioLista')");
			return idCP;
		} catch (Exception e) {
		//	logger.error("Error: " + e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"query: " + query, config, "costo: " + costo);
			return -1L;
		}
	}

	@SuppressWarnings("unchecked")
	public List<MultiusosValores> findNivelConfiguracionPrecio(Long idConfigPrecio) throws ProquifaNetException {
		StringBuilder query = new StringBuilder("");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idConfigPrecio", idConfigPrecio);
			
			query.append(" SELECT Nivel valorString1,NULL valorLong4,NULL valorDouble7, NULL valorDouble8,NULL valorLong5,NULL valorLong6,null valorString1,");
			query.append(" null valorString2, null valorString3 FROM Configuracion_Precio WHERE PK_Configuracion_Precio="+idConfigPrecio+"");
			//////logger.info("aqui: " + query.toString());
			return super.jdbcTemplate.query(query.toString(), map, new MultiusosMixProveedorRowMapper());

		} catch (Exception e) {
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"query: " + query, "idConfigPrecio: " + idConfigPrecio);
			return new ArrayList<MultiusosValores>();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Producto> findProductosPorFamilia(Producto prod) throws ProquifaNetException {
		StringBuilder query = new StringBuilder();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("prod", prod);
			
			String subTipo="";
			if(prod.getSubtipo()!=null){
				subTipo = "AND PROD.Subtipo='" + prod.getSubtipo() + "' ";
			}
			query.append(" DECLARE	@varIndus AS int = (SELECT PK_Folio FROM ValorCombo WHERE Concepto='Industrial' AND Valor='"+prod.getIndustria()+"') ");
			query.append(" SELECT * FROM Productos AS PROD ");
			query.append(" LEFT JOIN (SELECT * FROM INDUSTRIAPRODUCTO) AS IND ON IND.FK02_PRODUCTO = PROD.idProducto ");
			query.append(" WHERE PROD.Tipo='"+prod.getTipo()+"'"+subTipo);
			query.append(" AND PROD.Control='"+prod.getControl()+"' AND IND.FK01_INDUSTRIA=@varIndus ");
			return super.jdbcTemplate.query(query.toString(),map,  new ProductoRowMapper());

		} catch (Exception e) {
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"query: " + query, prod);
			return new ArrayList<Producto>();
		}
	}
	

	@SuppressWarnings("unchecked")
	public List<Producto> findProductosPorCosto(Producto prod) throws ProquifaNetException {
		StringBuilder query = new StringBuilder();
		try {
			//////logger.info("----------------         findProductosPorCosto ++++++" + prod.getCategoriaPrecioLista());
			query.append(" SELECT * FROM Productos WHERE FK03_Categoria_PrecioLista="+prod.getCategoriaPrecioLista());
			return super.jdbcTemplate.query(query.toString(), new ProductoRowMapper());

		} catch (Exception e) {
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"query: " + query, prod);
			return new ArrayList<Producto>();
		}
	}


	public Boolean insertarProductosConfiguracion_Productos(Producto prod, Long idConfig,Long prov) throws ProquifaNetException {
		StringBuilder query = new StringBuilder();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("prov", prov);
			map.put("idConfig", idConfig);
			map.put("prod", prod);
			String subTipo="";
			String control="";
			if(prod.getSubtipo()!=null){
				subTipo = " AND PROD.Subtipo='" + prod.getSubtipo() + "' ";
			}
			if(prod.getControl()!=null){
				control = " AND PROD.Control='" + prod.getControl() + "' ";
			}
			query.append(" DECLARE	@varIndus AS int = (SELECT PK_Folio FROM ValorCombo WHERE Concepto='Industrial' AND Valor='"+prod.getIndustria()+"') ");
			query.append(" INSERT INTO configuracionPrecio_producto (FK01_Producto, FK02_ConfFamilia)");
			query.append(" SELECT idProducto,"+idConfig+" FROM Productos AS PROD ");
			query.append(" LEFT JOIN (SELECT * FROM INDUSTRIAPRODUCTO) AS IND ON IND.FK02_PRODUCTO = PROD.idProducto ");
			query.append(" WHERE PROD.Tipo='"+prod.getTipo()+"'"+ subTipo + control);
			query.append(" AND IND.FK01_INDUSTRIA=@varIndus AND PROD.Proveedor="+prov+"");
			//////logger.info("+++insertarProductosConfiguracion_Productos:  "+  query.toString());
			super.jdbcTemplate.update(query.toString(), map);
			return true;

		} catch (Exception e) {
			//logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"query: " + query, prod);
			return false;
		}
	}


	public Boolean actualizarProveedor_FUAMarcas(Long idProve) throws ProquifaNetException {
		StringBuilder query = new StringBuilder(" ");
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idProve", idProve);
			
			query.append(" UPDATE Proveedores SET FUA_Marcas=GETDATE() WHERE Clave =" + idProve);
			super.jdbcTemplate.update(query.toString(), map);
			return true;
		} catch (Exception e) {
			//logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"query: " + query, "idProve: " + idProve);
			return false;
		}
	}

	public Boolean actualizarProveedor_FUAProductos(Long idProve) throws ProquifaNetException {
		StringBuilder query = new StringBuilder(" ");
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idProve", idProve);
			
			query.append(" UPDATE Proveedores SET FUA_Productos=GETDATE() WHERE Clave =" + idProve);
			super.jdbcTemplate.update(query.toString(), map);
			return true;
		} catch (Exception e) {
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"query: " + query, "idProve: " + idProve);
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public List<MultiusosValores> getListaBorrarConfiguracion( Long idConfiguracion, Long idCliente, String tipoNivelIngreso) throws ProquifaNetException {
		StringBuilder sql=new StringBuilder("");
		try{
			
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tipoNivelIngreso", tipoNivelIngreso);
			map.put("idCliente", idCliente);
			map.put("idConfiguracion", idConfiguracion);
			
			
			
			sql.append(" SELECT CONF1.PK_Configuracion_Precio valorLong4,null valorDouble7, NULL valorDouble8,NULL valorLong5, ");
			sql.append(" NULL valorLong6,null valorString1, null valorString2, null valorString3, CASE WHEN CONF1.Nivel='Familia' THEN 1  "); 
			sql.append(" WHEN CONF1.Nivel='Costo' THEN 2  WHEN CONF1.Nivel='Producto' THEN 3 END ORD ");
			sql.append(" FROM Configuracion_Precio AS CONF ");
			sql.append(" LEFT JOIN(SELECT * FROM Configuracion_Precio)AS CONF1 ON CONF1.Tipo=CONF.Tipo AND CONF.Industria = CONF1.Industria"); 
			sql.append(" AND COALESCE(CONF1.Subtipo,1)=COALESCE(CONF.Subtipo,1) AND COALESCE(CONF1.Control,1)=COALESCE(CONF.Control,1) AND CONF1.FK01_Proveedor=CONF.FK01_Proveedor ");
			sql.append(" WHERE CONF.PK_Configuracion_Precio=").append(idConfiguracion);
			sql.append(" ORDER BY ORD DESC ");

			//			//log.info(sql.toString());
			return this.jdbcTemplate.query(sql.toString(),map, new MultiusosMixProveedorRowMapper());

		}catch(Exception e){
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sql: " + sql, "idConfiguracion: " + idConfiguracion, "idCliente: " + idCliente,
					"tipoNivelIngreso: " + tipoNivelIngreso);
			return new ArrayList<MultiusosValores>();
		}
	}

	@SuppressWarnings("unchecked")
	public List<ConfiguracionPrecioProducto> obtenerConfiguracionPrecioXId(
			Long idConfiguracion, Long idCliente, String tipoNivelIngreso, Long CategoriaPrecio, String licencia) throws ProquifaNetException {
		StringBuilder sql=new StringBuilder("");
		try{
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("licencia", licencia);
			map.put("CategoriaPrecio", CategoriaPrecio);
			map.put("tipoNivelIngreso", tipoNivelIngreso);
			map.put("idCliente", idCliente);
			map.put("idConfiguracion", idConfiguracion);
			
			sql.append(" \n DECLARE @idCliente AS Integer=" + idCliente + " ");
			sql.append(" \n DECLARE @idCategoriaPrecio AS Integer=" + CategoriaPrecio );
			sql.append(" \n DECLARE @idLicencia AS varchar(10) = '" + licencia + "'");
			sql.append(" \n SELECT distinct CP.PK_Configuracion_Precio,IND.Valor AS Industria,VCTP.Valor AS Tipo,COALESCE(VCST.Valor,'') AS SubTipo,COALESCE(CONF.idConfigFamilia,CP.PK_Configuracion_Precio) AS idConfigFamilia,");
			sql.append(" \n COALESCE(VCCTRL.Valor,'') AS Control,CP.Nivel,CP.FK01_Proveedor AS idProveedor, "); 
			sql.append(" \n CF.PK_Costo_Factor AS idCostoFactor,CASE WHEN CP.Nivel='Costo' THEN CP.PK_Configuracion_Precio ELSE 0 END IDConfCosto, CF.Costo_Consularizacion,CF.AlmacenDestino,CF.FactorDTA,CF.Honorarios,");
			sql.append(" \n CF.Flete_Documentacion,NULL Porciento, CF.Factor_IGI,  ");
			sql.append(" \n CF.Factor_Utilidad,PROD.Costo costoCompra,CF.Stock_Disable, CF.FleteExpress_Disable,");
			sql.append(" \n CF.Permiso,CF.FactorDTA, CF.VALORENADUANA, CF.DESCUENTO, CF.FLETE, ");
			sql.append(" \n CF.Honorarios, NULL AS TEntrega,PROD.idProducto idProducto, NULL Costo, NULL Moneda,NULL MonedaVenta, NULL Codigo, NULL Fabrica, CF.Piezas, ");
			sql.append(" \n CF.factor_AAplus,CF.factor_AA,CF.factor_AM,CF.factor_AB,CF.factor_MA,CF.factor_MM,CF.factor_MB,CF.factor_FExpress,CF.factor_Stock,CF.factor_Comision,  CF.Factor_Bajo, ");
			sql.append(" \n CF.Factor_Distribuidor AS Distribuidor,CF.Factor_Publico AS Publico,NULL ConfiguracionPrecio, PLISTA.CATGLISTA PrecioLista, 0 idClasificacion, ");
			sql.append(" \n CASE WHEN CP.Nivel='Costo' then CASE WHEN CFL.Monto IS NOT NULL THEN CFL.Monto ELSE COALESCE(CF.montoLicencia,0) END else 0 end montoLicencia,");
			sql.append(" \n CASE WHEN CP.Nivel='Costo' then CASE WHEN CFL.Porcentaje IS NOT NULL THEN CFL.Porcentaje WHEN CF.PorcentajeLicencia IS NOT NULL and CF.PorcentajeLicencia > 0 THEN CF.PorcentajeLicencia ELSE LICE.Factor END else LICE.Factor end PorcentajeLicencia");
			if(idCliente > 0){
				sql.append(" \n , CASE WHEN CliCP.Factor IS NOT NULL THEN 1 ELSE 0 END FactorClienteConf");
				sql.append(" \n , CASE WHEN CliCP.Factor IS NOT NULL  THEN CliCP.Factor ELSE CF.Factor_"+ tipoNivelIngreso +" END  Factor");
				sql.append(" \n , CASE WHEN CliCP.FactorTemp IS NOT NULL  THEN CliCP.FactorTemp ELSE 0 END  FactorTemp");
				sql.append(" \n , CPP.PK_ConfigPrecio_Producto ");
				sql.append(" \n , CASE WHEN (CliCP.Factor IS NOT NULL AND CliCP.NivelConfigPrecio = 'Producto') THEN 1 ELSE 0 END Restablecer, CliCP.FUA  ");
				sql.append(" \n , CASE WHEN (CliCP.Factor IS NOT NULL AND CliCP.NivelConfigPrecio = 'Costo') THEN 1 ELSE 0 END RESTABLECER_COSTO ");	
				sql.append(" \n , CASE WHEN (CliCP.FactorTemp IS NOT NULL AND CliCP.NivelConfigPrecio = 'Producto') THEN 1 ELSE 0 END RestablecerTemporal  ");
				sql.append(" \n , CASE WHEN (CliCP.FactorTemp IS NOT NULL AND CliCP.NivelConfigPrecio = 'Costo') THEN 1 ELSE 0 END RESTABLECER_COSTOTemporal ");	
				sql.append(" \n , coalesce(CliCP.FK04_AgenteAduanal, CF.FK01_AgenteAduanal) FK01_AgenteAduanal, coalesce(CliCP.FK05_LugarAgenteAduanal, CF.FK02_LugarAgenteAduanal) FK02_LugarAgenteAduanal ");
				sql.append(" \n , coalesce(CliCP.Fk06_LAAConcepto, CF.FK03_LAAConcepto) FK03_LAAConcepto ");
				sql.append(" \n , (SELECT Ruta  from Clientes  WHERE  Clave=@idCliente ) AS RutaCliente ");
				sql.append(" \n , CASE WHEN CliCP.CostoFijo IS NOT NULL THEN CliCP.CostoFijo ELSE CF.Factor_CostoFijo END Factor_CostoFijo");
				sql.append(" \n , CASE WHEN CliCP.CostoFijoTemp IS NOT NULL THEN CliCP.CostoFijoTemp ELSE 0 END CostoFijoTemp");
				sql.append(" \n , CASE WHEN CliCP.Caduca IS NOT NULL THEN CliCP.Caduca ELSE null END Caduca");
				sql.append(" \n , coalesce( CliCP.CompuestaCostoF, CF.CompuestaCostoF, 1 ) CompuestaCostoF");
				sql.append(" \n , coalesce( CliCP.CompuestaFactorU, CF.CompuestaFactorU, 1 ) CompuestaFactorU");
				sql.append(" \n , coalesce( CliCP.CompuestaCostoFTemp, 1 ) CompuestaCostoFTemp");
				sql.append(" \n , coalesce( CliCP.CompuestaFactorUTemp, 1 ) CompuestaFactorUTemp");
			}else{
				sql.append(" ,0 FactorClienteConf,1 Factor, 1 PK_ConfigPrecio_Producto, 0 Restablecer, CP.FUA, 0 RESTABLECER_COSTO, 0 RestablecerTemporal, 0 RESTABLECER_COSTOTemporal ");
				sql.append(" , CF.Factor_CostoFijo, CF.FK01_AgenteAduanal, CF.FK02_LugarAgenteAduanal, CF.FK03_LAAConcepto, 1 Factor_CostoFijo, coalesce(CF.CompuestaCostoF,1) CompuestaCostoF, coalesce(CF.CompuestaFactorU,1) CompuestaFactorU");
			}
			sql.append(" \n FROM Configuracion_Precio AS CP  ");
			sql.append(" \n LEFT JOIN(SELECT * FROM ConfiguracionPrecio_Producto) AS CPP ON CP.PK_Configuracion_Precio = (case when Nivel = 'Familia' then CPP.FK02_ConfFamilia when Nivel = 'Costo' then CPP.FK03_ConfCosto when Nivel = 'Producto' then CPP.FK04_ConfProducto end  ) ");
			if(idCliente > 0){
				sql.append(" \n LEFT JOIN (SELECT * FROM Configuracion_Precio) AS CP1 ON CP1.PK_Configuracion_Precio = COALESCE(CPP.FK03_ConfCosto,CPP.FK02_ConfFamilia)");  
				sql.append(" \n LEFT JOIN(SELECT * FROM Costo_Y_Factor) AS CF ON CF.PK_Costo_Factor = CP1.FK02_Costo_Factor ");  
			}else{
				sql.append(" \n LEFT JOIN(SELECT * FROM Costo_Y_Factor) AS CF ON CF.PK_Costo_Factor = CP.FK02_Costo_Factor ");  
			}
			sql.append(" \n LEFT JOIN(SELECT * FROM Tiempo_Entrega) AS TE ON TE.PK_Tiempo_Entrega = CP.FK03_Tiempo_Entrega ");  
			sql.append(" \n LEFT JOIN(SELECT * FROM ValorCombo WHERE Concepto='TipoProducto') AS VCTP ON VCTP.PK_Folio = CP.Tipo ");   
			sql.append(" \n LEFT JOIN(SELECT * FROM ValorCombo WHERE Concepto='SubTipoProducto') AS VCST ON VCST.PK_Folio = CP.Subtipo ");   
			sql.append(" \n LEFT JOIN(SELECT * FROM ValorCombo WHERE Concepto='Control') AS VCCTRL ON VCCTRL.PK_Folio = CP.Control    ");
			sql.append(" \n LEFT JOIN(SELECT * FROM ValorCombo WHERE Concepto='Industrial') AS IND ON IND.PK_Folio = cp.Industria    ");
			sql.append(" \n LEFT JOIN (SELECT * FROM AA_ConfiguracionPrecio) AS AACP ON AACP.FK02_ConfiguracionPrecio = CP.PK_Configuracion_Precio ");
			sql.append(" \n LEFT JOIN (SELECT * FROM AgenteAduanal) AS AA ON AA.PK_AgenteAduanal = CF.FK01_AgenteAduanal ");
			sql.append(" \n LEFT JOIN (SELECT PK_Lugar_AgenteAduanal, FK01_AgenteAduanal, Monto, Porcentaje FROM Lugar_AgenteAduanal) AS LAA ON LAA.PK_Lugar_AgenteAduanal = CF.FK02_LugarAgenteAduanal ");
			sql.append(" \n LEFT JOIN (SELECT * FROM LAA_Concepto) AS LAAC ON LAAC.PK_AAConcepto = CF.FK03_LAAConcepto");
			sql.append(" \n LEFT JOIN(SELECT CONF.PK_Configuracion_Precio AS idConfig,FAMILY.PK_Configuracion_Precio idConfigFamilia ");  
			sql.append(" \n          FROM Configuracion_Precio AS CONF   ");
			sql.append(" \n          LEFT JOIN (SELECT * FROM Configuracion_Precio WHERE Nivel='Familia') AS FAMILY ON FAMILY.Tipo = CONF.Tipo AND FAMILY.Subtipo=CONF.Subtipo AND ");    
			sql.append(" \n          FAMILY.Control=CONF.Control AND FAMILY.FK01_Proveedor= CONF.FK01_Proveedor AND FAMILY.Industria=CONF.Industria) AS CONF ON CONF.idConfig = cp.PK_Configuracion_Precio     ");
			sql.append(" \n LEFT JOIN(SELECT DISTINCT FK03_Categoria_PrecioLista AS CATGLISTA,CFP.FK03_ConfCosto, PROD.Costo ");
			sql.append(" \n          FROM ConfiguracionPrecio_Producto AS CFP ");
			sql.append(" \n          LEFT JOIN (SELECT * FROM Productos) AS PROD ON PROD.idProducto = CFP.FK01_Producto) AS PLISTA ON PLISTA.FK03_ConfCosto=CP.PK_Configuracion_Precio");
			sql.append(" \n LEFT JOIN(SELECT MAX(FK01_Producto) AS ID, FK03_ConfCosto FROM ConfiguracionPrecio_Producto GROUP BY FK03_ConfCosto) AS PRO ON PRO.FK03_ConfCosto=CP.PK_Configuracion_Precio ");		
			sql.append(" \n LEFT JOIN(SELECT Costo,  idProducto, FK03_Categoria_PrecioLista, Vigente FROM Productos) AS PROD ON PROD.idProducto = CPP.FK01_Producto AND PROD.FK03_Categoria_PrecioLista = CASE WHEN CP.Nivel = 'Familia' THEN  @idCategoriaPrecio ELSE PROD.FK03_Categoria_PrecioLista  END" );
			sql.append(" \n LEFT JOIN (SELECT * FROM CostoFactor_Licencia) CFL ON CFL.FK01_CostoFactor = CF.PK_Costo_Factor AND CFL.Licencia = @idLicencia" );
			sql.append(" \n LEFT JOIN(SELECT * FROM Licencia) AS LICE ON LICE.FK01_Proveedor = CP.FK01_Proveedor AND LICE.Tipo = @idLicencia" );

			if(idCliente > 0){
				sql.append(" \n LEFT JOIN (SELECT * FROM Cliente_ConfiguracionPrecio) AS CliCP ON CliCP.AK_ClienteConfigPrecio = CPP.FK05_CliFamilia  AND CliCP.FK01_Cliente = @idCliente \n"); //AND CliCP.tipo = '" + tipoNivelIngreso + "'
				sql.append(" \n LEFT JOIN (SELECT * FROM Tiempo_Entrega) AS TEC ON TEC.PK_Tiempo_Entrega = CliCP.FK03_TiempoEntrega ");  
			}
			sql.append(" \n WHERE CP.PK_Configuracion_Precio=").append(idConfiguracion).append(" AND PROD.Costo IS NOT NULL");
		//	logger.info(sql.toString());

			List<ConfiguracionPrecioProducto> list = super.jdbcTemplate.query(sql.toString(),map,  new ConfiguracionPrecioRowMapper());

			if(idCliente > 0){//cat. clientes
				for (ConfiguracionPrecioProducto configuracionPrecio : list) {
					if(!configuracionPrecio.getFactorClienteConfiguracion()){//Cuando el factor es nulo
						sql = new StringBuilder("");
						sql.append(" \n SELECT PK_Tiempo_Entrega_Ruta, FK01_Configuracion_Precio,Ruta, RequierePermiso_Existe,RequierePermiso_NoExiste,RequierePermiso_No, FactorFlexibilidad ");
						sql.append(" \n FROM ConfiguracionPrecio_Producto CPP");
						sql.append(" \n LEFT JOIN Tiempo_Entrega_Ruta TER ON TER.FK01_Configuracion_Precio = COALESCE(CPP.FK03_ConfCosto,CPP.FK02_ConfFamilia)");
						sql.append(" \n WHERE CPP.PK_ConfigPrecio_Producto =").append(configuracionPrecio.getIdConfiguracionPrecioProducto());
					}else{
						sql = new StringBuilder("");
						sql.append(" \n SELECT PK_Cliente_Tiempo_Entrega_Ruta AS PK_Tiempo_Entrega_Ruta,FK01_ClienteConfiguracionPrecio AS FK01_Configuracion_Precio,Ruta,RequierePermiso_Existe,RequierePermiso_NoExiste,RequierePermiso_No, FactorFlexibilidad ");
						sql.append(" \n FROM ConfiguracionPrecio_Producto ");
						sql.append(" \n LEFT JOIN Cliente_ConfiguracionPrecio ON ConfiguracionPrecio_Producto.FK05_CliFamilia=Cliente_ConfiguracionPrecio.AK_ClienteConfigPrecio ");
						sql.append(" \n LEFT JOIN Cliente_Tiempo_Entrega_Ruta ON Cliente_Tiempo_Entrega_Ruta.FK01_ClienteConfiguracionPrecio=Cliente_ConfiguracionPrecio.PK_ClienteConfiguracionPrecio ");
						sql.append(" \n WHERE  PK_ConfigPrecio_Producto=").append(configuracionPrecio.getIdConfiguracionPrecioProducto()).append(" AND Cliente_ConfiguracionPrecio.FK01_Cliente=").append(idCliente).append(" AND PK_Cliente_Tiempo_Entrega_Ruta IS NOT NULL");
					}

					List<TiempoEntrega> listTE = super.jdbcTemplate.query(sql.toString(), map, new TiempoEntregaRutaProveedorRowMapper());

					configuracionPrecio.setTiempoEntregaRuta(listTE);
				}
			}



			return list;

		}catch(Exception e){
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sql: " + sql, "idConfiguracion: " + idConfiguracion, "idCliente: " + idCliente,
					"tipoNivelIngreso: " + tipoNivelIngreso, "licencia: " + licencia);
			return new ArrayList<ConfiguracionPrecioProducto>();
		}
	}

	public Double getCostoPorIdProducto( Long idProducto) throws ProquifaNetException {
		StringBuilder sql=new StringBuilder("");
		try{
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idProducto", idProducto);
			
			sql.append(" SELECT Costo FROM Productos ");
			sql.append(" WHERE idProducto =").append(idProducto);

			//			//log.info(sql.toString());
			return (Double)this.jdbcTemplate.queryForObject(sql.toString(),map,  Double.class);

		}catch(Exception e){
			//logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sql: " + sql, "idProducto: " + idProducto);
			return -1D;
		}
	}

	public Double getCostoXCategoriaPrecioLista( Long idCategoria) throws ProquifaNetException {
		StringBuilder sql=new StringBuilder("");
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCategoria", idCategoria);
		
			
			sql.append(" SELECT (PrecioLista * P.TP) PrecioLista FROM Categoria_PrecioLista CPL");
			sql.append(" LEFT JOIN (SELECT Clave, CASE WHEN Moneda = MonedaVenta THEN 1 ELSE CASE WHEN Tipo_Cambio IS NULL OR Tipo_Cambio = 0 THEN 1 ELSE Tipo_Cambio END END TP FROM Proveedores) AS P ON P.Clave = CPL.FK01_Proveedor ");
			sql.append(" WHERE PK_Categoria=").append(idCategoria);

			//////logger.info(sql.toString());
			return (Double)this.jdbcTemplate.queryForObject(sql.toString(),map,  Double.class);

		}catch(Exception e){
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sql: " + sql, "idCategoria: " + idCategoria);
			return -1D;
		}
	}

	public boolean updateConfPrecioProducto_cambioPrecio(ConfiguracionPrecioProducto config) throws ProquifaNetException{
		StringBuilder sql=new StringBuilder("");
		try{
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("config", config);
			sql.append(" UPDATE ConfiguracionPrecio_Producto SET FK03_ConfCosto=NULL WHERE FK01_Producto= ");
			sql.append(config.getProducto().getIdProducto());

			this.jdbcTemplate.update(sql.toString(), map);

			return true;


		}catch(Exception e){
			//logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sql: " + sql, config);
			return false;
		}
	}
	public Boolean updateConfPrecioCosto_cambioPrecio(Long idConfCosto, Long idConfFamilia, 
			String costoNuevo, Long idProveedor) throws ProquifaNetException{
		StringBuilder sql=new StringBuilder("");
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idProveedor", idProveedor);
			map.put("costoNuevo", costoNuevo);
			map.put("idConfFamilia", idConfFamilia);
			map.put("idConfCosto", idConfCosto);
			
			sql.append(" update ConfiguracionPrecio_Producto set FK03_ConfCosto =").append(idConfCosto);
			sql.append(" where FK02_ConfFamilia = ").append(idConfFamilia);
			sql.append(" and FK01_Producto in (select idProducto from Productos where Costo = ").append(costoNuevo);
			sql.append(" and Proveedor = ").append(idProveedor).append(")");


			this.queryForLong(sql.toString(), map);
			return true;

		}catch(Exception e){
			//logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sql: " + sql, "idConfCosto: " + idConfCosto, "idConfFamilia: " + idConfFamilia,
					"costoNuevo: " + costoNuevo, "idProveedor: " + idProveedor);
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public List<MultiusosValores> obtenerDatosConfigXidProducto(Long idProducto, ConfiguracionPrecioProducto config ) throws ProquifaNetException{
		StringBuilder sql=new StringBuilder("");
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idProducto", idProducto);
			map.put("config", config);
			sql.append(" SELECT FK02_ConfFamilia valorLong4, FK03_ConfCosto valorLong5, ");
			sql.append(" FK04_ConfProducto valorLong6,NULL valorString1,NULL valorString2,NULL valorString3, ");
			sql.append(" FK04_ConfProducto valorDouble7, NULL valorDouble8 ");
			sql.append(" FROM ConfiguracionPrecio_Producto ");
			sql.append(" WHERE FK01_Producto = ").append(idProducto);
			sql.append(" AND FK02_ConfFamilia = ").append(config.getIdConfiguracionFamilia());
	//		logger.info(sql.toString());
			return this.jdbcTemplate.query(sql.toString(),map, new MultiusosMixProveedorRowMapper());

		}catch(Exception e){
			//logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sql: " + sql, "idProducto: " + idProducto,config);
			return new ArrayList<MultiusosValores>();
		}
	}

	public Boolean updateProductoConfig(Long idProducto,Long idFamilia,Long idCosto) throws ProquifaNetException{
		StringBuilder query = new StringBuilder(" ");
		try {
			//////logger.info("----------------         updateProductoConfig");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idFamilia", idFamilia);
			map.put("idCosto", idCosto);
			map.put("idProducto", idProducto);
			if (idCosto==0)
				idCosto=null;

			query.append(" UPDATE ConfiguracionPrecio_Producto SET FK03_ConfCosto="+idCosto+", FK02_ConfFamilia="+idFamilia);
			query.append(" WHERE FK01_Producto="+idProducto);
			query.append(" AND FK02_ConfFamilia = "+idFamilia);
			//////logger.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++    "+query.toString());
			super.jdbcTemplate.update(query.toString(), map);

			return true;
		} catch (Exception e) {
			//logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"query: " + query, "idProducto: " + idProducto,"idFamilia: " + idFamilia,
					"idCosto: " + idCosto);
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public List<MultiusosValores> obtenerProductosXIdCosto(Long idCosto ) throws ProquifaNetException{
		StringBuilder sql=new StringBuilder("");
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCosto", idCosto);
			sql.append(" SELECT FK02_ConfFamilia valorLong4, FK03_ConfCosto valorLong5,FK01_Producto valorLong6,NULL valorString1,NULL valorString2,NULL valorString3, ");
			sql.append(" FK04_ConfProducto valorDouble7, NULL valorDouble8 ");
			sql.append(" FROM ConfiguracionPrecio_Producto WHERE FK03_ConfCosto = ").append(idCosto);
			//////logger.info(sql.toString());
			return this.jdbcTemplate.query(sql.toString(),map, new MultiusosMixProveedorRowMapper());

		}catch(Exception e){
			//logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sql: " + sql, "idCosto: " + idCosto);
			return new ArrayList<MultiusosValores>();
		}
	}

	
	public Long insertarFabricanteMarca(Fabricante info) throws ProquifaNetException {
		StringBuilder sql = new StringBuilder("");
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("info", info);
			Object[] args={info.getNombre(),info.getPaisCompra(),info.getPaisManufactura(),info.getLogoExt()};
			
			sql.append(" INSERT INTO Fabricantes(Nombre,Pais_compra,Pais_manufactura,Logo_Ext,FUActual, Habilitado) ");
			sql.append(" VALUES(?,?,?,?,GETDATE(),1) ");
			//////logger.info(sql.toString() + " - " + info.getNombre() + " - " + info.getPaisCompra() + " - " + info.getPaisManufactura() + " - " + info.getLogoExt() + " - " + info.getHabilitado());
			super.jdbcTemplate.update(sql.toString(), map);
			return super.queryForLong("SELECT IDENT_CURRENT ('Fabricantes')", map);

		}catch(Exception e){
		//	logger.error("Error: " + e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sql: " + sql, info);
			return -1L;
		}
	}


	@Override
	public FormulaPrecio getInformacionFormulaPrecio(Long idProveedor,
			Long idProducto, int stock_flete, String nivel, Long idCliente, Long idConfig) throws ProquifaNetException {
		StringBuilder sql = new StringBuilder("");
		try{

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idConfig", idConfig);
			map.put("nivel", nivel);
			map.put("idCliente", idCliente);
			map.put("stock_flete", stock_flete);
			map.put("idProveedor", idProveedor);
			map.put("idProducto", idProducto);
			
			String Q = 	" 	CASE WHEN CFL.Monto is not null and CFL.Monto > 0  " +
					"					THEN CFL.Monto " +
					"				 WHEN (CFL.Monto is null or CFL.Monto < 1 ) and CFL.Porcentaje > 0 " +
					"					THEN (1 + (CFL.Porcentaje/100)) " +
					" 				else" +
					"					 (1 + ((CASE WHEN LICE.Factor = -1 OR  LICE.Factor IS NULL THEN 0 ELSE LICE.Factor /100 END))) " +
					"				END ";	

			String O = 	" (CASE WHEN CF.ValorEnAduana = 0 OR CF.ValorEnAduana IS NULL THEN 1 ELSE CF.ValorEnAduana END) ";
			String R = 	" (CASE WHEN CF.Descuento IS NULL THEN 1 ELSE (1 - (CF.Descuento/100)) END)";	
			String PrecioL = "((PROD.Costo * PROVEE.TC) * (" + R + ")) ";
			String B = 	" (CASE WHEN CF.Flete = 0 OR  CF.Flete IS NULL THEN 0 ELSE CF.Flete END )";
			String N  = 	"\n CASE WHEN CF.VALORENADUANA <= 0 OR CF.VALORENADUANA IS NULL THEN case when CF.Piezas is null or CF.Piezas = 0 then 1 else CF.Piezas end " +
					" ELSE " +
					"	CASE WHEN  (CFL.Monto is not null and CFL.Monto > 0)  THEN " +
					"		CEILING((" + O + " - " + B + ")/(" + PrecioL + " +  " + Q +"))  " +
					"	ELSE " +
					"		CEILING((" + O + " - " + B + ")/(" + PrecioL + " *  " + Q +"))  " +
					"	END " +
					" END \n";

			N = " case when " + N + " = 0 then 1 else " + N + " end ";

			String VA =    " CASE WHEN (CFL.Monto is not null and CFL.Monto > 0)  " +
					" THEN ( (" + N + " * (" + PrecioL + "  +  " + Q +") ) + " + B +") " +
					" ELSE ( (" + N + " * " + PrecioL + "  * " + Q +" ) + " + B +") END	";
			String Valor = "(" + PrecioL + ") * (" + N + ")";
			String L = 	" ROUND( (CASE WHEN (CASE WHEN LAAC.Monto = 0  OR  LAAC.Monto IS NULL THEN LAAC.Porcentaje ELSE LAAC.Monto END) IS NULL THEN 0 " +
					" ELSE CASE WHEN LAAC.Monto = 0  OR  LAAC.Monto IS NULL THEN (" + VA + ") * ((LAAC.Porcentaje)/100) ELSE LAAC.Monto END END) ,3,1)";
			String F = 	" ROUND( ((CASE WHEN CF.Factor_CostoFijo = -1 OR CF.Factor_CostoFijo IS NULL THEN 0 ELSE CF.Factor_CostoFijo END)) ,3,1)";
			String M = 	" ROUND( (CASE WHEN CF.Permiso = -1 OR  CF.Permiso IS NULL THEN 0 ELSE CF.Permiso END) ,3,1)";
			String I = 	" ROUND( (CASE WHEN CF.AlmacenDestino = -1 OR  CF.AlmacenDestino IS NULL  THEN 0 ELSE CF.AlmacenDestino END) ,3,1)";

			////logger.info("\n Cliente: " + idCliente);

			sql.append(" \n DECLARE @idProveedor AS Integer= " + idProveedor);
			sql.append(" \n DECLARE @idProducto AS INTEGER = " + idProducto);
			sql.append(" \n DECLARE @FLETE_STOCK AS INTEGER = " + stock_flete);
			sql.append(" \n DECLARE @idCliente AS Integer=" + idCliente + "\n ");
			sql.append(" \n DECLARE @idConfigFamilia AS Integer=" + idConfig + "\n ");
			sql.append(" \n SELECT DISTINCT (CASE WHEN CF.FLETE = 0 OR  CF.FLETE IS NULL THEN 0 ELSE CF.FLETE END ) B,");
			sql.append(" \n (CASE WHEN CF.Costo_Consularizacion = -1 OR  CF.Costo_Consularizacion IS NULL THEN 0 ELSE CF.Costo_Consularizacion END) C,");
			sql.append(" \n (CASE WHEN CF.Flete_Documentacion = -1 OR  CF.Flete_Documentacion IS NULL THEN 0 ELSE CF.Flete_Documentacion END) D,");
			sql.append(" \n (CASE WHEN CF.Factor_IGI = -1 OR  CF.Factor_IGI IS NULL THEN 0 ELSE CF.Factor_IGI END) J,");
			sql.append(" \n (CASE WHEN CF.FactorDTA = -1 OR  CF.FactorDTA IS NULL THEN 0 ELSE CF.FactorDTA END) K,");
			sql.append(" \n " + PrecioL  + " PRECIOLISTA,");
			sql.append(" \n CF.Descuento R,");
			sql.append(" \n " + Q  + " Q,");	
			sql.append(" \n " + O  + " O,");	
			sql.append(" \n " + N  + " N,");
			sql.append(" \n " + VA + " VA,");
			sql.append(" \n " + L  + " L,");
			sql.append(" \n " + Valor  + " Valor,");
			
			sql.append(" \n " + M  + " M,");
			sql.append(" \n " + I  + " I,");
			if(idCliente > 0){
				sql.append(" \n CASE WHEN CliCP2.Factor is not null THEN CliCP2.Factor	WHEN  CliCP3.Factor is not null THEN CliCP3.Factor ELSE CF.Factor_"+ nivel +"  END G,");
				sql.append(" \n CASE WHEN CliCP2.CostoFijo is not null THEN CliCP2.CostoFijo	WHEN  CliCP3.CostoFijo is not null THEN CliCP3.CostoFijo ELSE CF.Factor_CostoFijo  END F,");
				sql.append(" \n coalesce(CliCP2.CompuestaCostoF , CliCP3.CompuestaCostoF, CF.CompuestaCostoF, 1 )  CompuestaCostoF,  ");
				sql.append(" \n coalesce(CliCP2.CompuestaFactorU , CliCP3.CompuestaFactorU, CF.CompuestaFactorU, 1 )  CompuestaFactorU, COALESCE(CliCP3.precioListaAnterior,CliCP2.precioListaAnterior , CliCP1.precioListaAnterior, CF.precioListaAnterior, 0 )  PrecioListaAnterior ");
			}else{
				sql.append(" \n ((CASE WHEN CF.factor_").append(nivel).append(" = -1 OR CF.factor_").append(nivel).append(" IS NULL THEN 0 ELSE CF.factor_").append(nivel).append(" END)) G,");
				sql.append(" \n " + F  + " F,");
				sql.append(" \n coalesce(CF.CompuestaCostoF , 1 )  CompuestaCostoF,  ");
				sql.append(" \n coalesce(CF.CompuestaFactorU, 1 )  CompuestaFactorU, 0 AS PrecioListaAnterior ");
			}
			sql.append(" \n FROM ConfiguracionPrecio_Producto AS CPP  ");
			sql.append(" \n LEFT JOIN(SELECT * FROM Configuracion_Precio) AS CP ON CP.PK_Configuracion_Precio = COALESCE(CPP.FK03_ConfCosto,CPP.FK02_ConfFamilia) ");
			sql.append(" \n LEFT JOIN(SELECT Clave, CASE WHEN Moneda = MonedaVenta THEN 1 ELSE CASE WHEN Tipo_Cambio IS NULL OR Tipo_Cambio = 0 THEN 1 ELSE Tipo_Cambio END END  AS TC, MonedaVenta FROM Proveedores) AS PROVEE ON PROVEE.Clave = CP.FK01_Proveedor ");
			sql.append(" \n LEFT JOIN(SELECT * FROM Productos) AS PROD ON PROD.idProducto= CPP.FK01_Producto ");
			sql.append(" \n LEFT JOIN(SELECT * FROM Costo_Y_Factor) AS CF ON CF.PK_Costo_Factor=CP.FK02_Costo_Factor    ");
			if(idCliente > 0){
				sql.append(" LEFT JOIN (SELECT * FROM Cliente_ConfiguracionPrecio) AS CliCP1 ON CliCP1.AK_ClienteConfigPrecio = CPP.FK09_CliClasificacion AND CliCP1.FK01_Cliente = @idCliente \n");
				sql.append(" LEFT JOIN (SELECT * FROM Cliente_ConfiguracionPrecio) AS CliCP2 ON CliCP2.AK_ClienteConfigPrecio = CPP.FK06_CliCosto AND CliCP2.FK01_Cliente = @idCliente \n");
				sql.append(" LEFT JOIN (SELECT * FROM Cliente_ConfiguracionPrecio) AS CliCP3 ON CliCP3.AK_ClienteConfigPrecio = CPP.FK05_CliFamilia AND CliCP3.FK01_Cliente = @idCliente \n");
				sql.append(" \n LEFT JOIN (SELECT * FROM LAA_Concepto) AS LAAC ");
				sql.append(" \n		ON LAAC.PK_AAConcepto = CASE WHEN CliCP2.Fk06_LAAConcepto <> 0 THEN CliCP2.Fk06_LAAConcepto	WHEN  CliCP3.Fk06_LAAConcepto <> 0 THEN CliCP3.Fk06_LAAConcepto ELSE CF.FK03_LAAConcepto END");
			}else{
				sql.append(" \n LEFT JOIN (SELECT * FROM LAA_Concepto) AS LAAC ON LAAC.PK_AAConcepto = CF.FK03_LAAConcepto");
			}
			sql.append(" \n LEFT JOIN(SELECT PROD.idProducto,PROD.Proveedor, PROD.FK01_Configuracion_Precio, PROD.Costo,((((SUM(CPP.CostoNuevo - CPP.CostoAnTErior))/case when (COUNT(CPP.CostoNuevo)) = 0 then 1 else COUNT(CPP.CostoNuevo) end)*100 )/(CPP.CostoNuevo ");   
			sql.append(" \n          -((SUM(CPP.CostoNuevo - CPP.CostoAnTErior))/case when (COUNT(CPP.CostoNuevo)) = 0 then 1 else COUNT(CPP.CostoNuevo) end))) AS PORCIENTO   ");
			sql.append(" \n          FROM Productos AS PROD   ");
			sql.append(" \n          LEFT JOIN(SELECT MAX (PK_CambioPrecioProducto) ID, FK01_Producto FROM CambioPrecioProducto GROUP BY FK01_Producto) AS ID ON ID.FK01_Producto =PROD.idProducto ");  
			sql.append(" \n          LEFT JOIN(SELECT * FROM CambioPrecioProducto) AS CPP ON CPP.PK_CambioPrecioProducto = ID.ID    ");
			sql.append(" \n          GROUP BY CPP.CostoNuevo,PROD.Proveedor,PROD.FK01_Configuracion_Precio, PROD.Costo,PROD.idProducto) "); 
			sql.append("  \n         AS PORC ON PORC.idProducto = PROD.idProducto  ");
			sql.append(" \n LEFT JOIN(SELECT * FROM Licencia) AS LICE ON LICE.FK01_Proveedor = CP.FK01_Proveedor AND LICE.Tipo = PROD.DepositarioInternacional");
			sql.append(" \n LEFT JOIN (SELECT * FROM CostoFactor_Licencia) CFL ON CFL.FK01_CostoFactor = CF.PK_Costo_Factor AND CFL.Licencia = PROD.DepositarioInternacional" );
			sql.append("  \n WHERE CP.FK01_Proveedor=@idProveedor  AND PROD.idProducto = @idProducto AND CPP.FK02_ConfFamilia = @idConfigFamilia");


			log.info("getInformacionFormulaPrecio" +sql.toString());
			return (FormulaPrecio) this.jdbcTemplate.queryForObject (sql.toString(),map, new FormulaPrecioRowMapper());

		}catch(Exception e){
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sql: " + sql, "idProveedor: " + idProveedor, "idProducto: " + idProducto,
					"stock_flete: " + stock_flete, "nivel: " + nivel, "idCliente: " + idCliente, "idConfig: " + idConfig);
			return new FormulaPrecio();
		}

	}

	@Override
	public Licencia getLicenciasProveedor(Long idProveedor)
			throws ProquifaNetException {
		StringBuilder sql = new StringBuilder("");
		try{
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idProveedor", idProveedor);
		
			sql.append(" SELECT	DISTINCT L.FK01_Proveedor, COALESCE(NCTC.Factor,0.00) NCTC, COALESCE(CIP.Factor,0.00) CIP, COALESCE(ATCC.Factor,0.00) ATCC, COALESCE(IMI.Factor,0.00) IMI,");
			sql.append(" COALESCE(NCPF.Factor,0.00) NCPF, COALESCE(NBRC.Factor,0.00) NBRC, COALESCE(NCIMB.Factor,0.00) NCIMB, COALESCE(NCYC.Factor,0.00) NCYC ");
			sql.append(" FROM Licencia AS L ");
			sql.append(" LEFT JOIN (SELECT FK01_Proveedor, Factor  FROM Licencia WHERE Tipo = 'NCTC')  AS NCTC  ON NCTC.FK01_Proveedor  = L.FK01_Proveedor ");
			sql.append(" LEFT JOIN (SELECT FK01_Proveedor, Factor  FROM Licencia WHERE Tipo = 'CIP')   AS CIP   ON CIP.FK01_Proveedor   = L.FK01_Proveedor ");
			sql.append(" LEFT JOIN (SELECT FK01_Proveedor, Factor  FROM Licencia WHERE Tipo = 'ATCC')  AS ATCC  ON ATCC.FK01_Proveedor  = L.FK01_Proveedor ");
			sql.append(" LEFT JOIN (SELECT FK01_Proveedor, Factor  FROM Licencia WHERE Tipo = 'IMI')   AS IMI   ON IMI.FK01_Proveedor   = L.FK01_Proveedor ");
			sql.append(" LEFT JOIN (SELECT FK01_Proveedor, Factor  FROM Licencia WHERE Tipo = 'NCPF')  AS NCPF  ON NCPF.FK01_Proveedor  = L.FK01_Proveedor ");
			sql.append(" LEFT JOIN (SELECT FK01_Proveedor, Factor  FROM Licencia WHERE Tipo = 'NBRC')  AS NBRC  ON NBRC.FK01_Proveedor  = L.FK01_Proveedor ");
			sql.append(" LEFT JOIN (SELECT FK01_Proveedor, Factor  FROM Licencia WHERE Tipo = 'NCIMB') AS NCIMB ON NCIMB.FK01_Proveedor = L.FK01_Proveedor ");
			sql.append(" LEFT JOIN (SELECT FK01_Proveedor, Factor  FROM Licencia WHERE Tipo = 'NCYC')  AS NCYC  ON NCYC.FK01_Proveedor  = L.FK01_Proveedor ");
			sql.append(" WHERE L.FK01_Proveedor =" + idProveedor);


			//logger.info(sql.toString());
			return (Licencia) this.jdbcTemplate.queryForObject (sql.toString(),map, new LicenciaRowMapper());

		}catch(Exception e){
//			logger.error(e.getMessage());
//			funcion = new Funcion();
//			funcion.enviarCorreoAvisoExepcion(e,"sql: " + sql, "idProveedor: " + idProveedor);
//			return new Licencia();
			return null;
		}
	}

	@Override
	public Boolean updateLicencuasProveedor(Licencia licencia)
			throws ProquifaNetException {
		StringBuilder sql = new StringBuilder("");
		
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("licencia", licencia);
			int i = 0;

			sql.append(" UPDATE L	SET L.Factor =").append(licencia.getNCTC()).append("	FROM Licencia L");
			sql.append(" WHERE L.PK_Licencia = (SELECT PK_Licencia FROM Licencia L2 WHERE  L2.Tipo = 'NCTC' AND L2.FK01_Proveedor =").append(licencia.getIdProveedor()).append(")");

			////logger.info(sql.toString());
			i = super.jdbcTemplate.update(sql.toString(), map);
			sql.delete(0, sql.length());

			if(i == 0){
				sql.append("INSERT INTO Licencia (FK01_Proveedor, Tipo, Factor) VALUES(").append(licencia.getIdProveedor()).append(", 'NCTC', ").append(licencia.getNCTC()).append(")");
				////logger.info(sql.toString());
				i = super.jdbcTemplate.update(sql.toString(), map);
				sql.delete(0, sql.length());
			}
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			sql.append(" UPDATE L	SET L.Factor =").append(licencia.getCIP()).append("	FROM Licencia L");
			sql.append(" WHERE L.PK_Licencia = (SELECT PK_Licencia FROM Licencia L2 WHERE  L2.Tipo = 'CIP' AND L2.FK01_Proveedor =").append(licencia.getIdProveedor()).append(")");

			////logger.info(sql.toString());
			i = super.jdbcTemplate.update(sql.toString(), map);
			sql.delete(0, sql.length());

			if(i == 0){
				sql.append("INSERT INTO Licencia (FK01_Proveedor, Tipo, Factor) VALUES(").append(licencia.getIdProveedor()).append(", 'CIP', ").append(licencia.getCIP()).append(")");
				////logger.info(sql.toString());
				i = super.jdbcTemplate.update(sql.toString(), map);
				sql.delete(0, sql.length());
			}
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			sql.append(" UPDATE L	SET L.Factor =").append(licencia.getATCC()).append("	FROM Licencia L");
			sql.append(" WHERE L.PK_Licencia = (SELECT PK_Licencia FROM Licencia L2 WHERE  L2.Tipo = 'ATCC' AND L2.FK01_Proveedor =").append(licencia.getIdProveedor()).append(")");

			////logger.info(sql.toString());
			i = super.jdbcTemplate.update(sql.toString(), map);
			sql.delete(0, sql.length());

			if(i == 0){
				sql.append("INSERT INTO Licencia (FK01_Proveedor, Tipo, Factor) VALUES(").append(licencia.getIdProveedor()).append(", 'ATCC', ").append(licencia.getATCC()).append(")");
				////logger.info(sql.toString());
				i = super.jdbcTemplate.update(sql.toString(), map);
				sql.delete(0, sql.length());
			}
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			sql.append(" UPDATE L	SET L.Factor =").append(licencia.getIMI()).append("	FROM Licencia L");
			sql.append(" WHERE L.PK_Licencia = (SELECT PK_Licencia FROM Licencia L2 WHERE  L2.Tipo = 'IMI' AND L2.FK01_Proveedor =").append(licencia.getIdProveedor()).append(")");

			////logger.info(sql.toString());
			i = super.jdbcTemplate.update(sql.toString(), map);
			sql.delete(0, sql.length());

			if(i == 0){
				sql.append("INSERT INTO Licencia (FK01_Proveedor, Tipo, Factor) VALUES(").append(licencia.getIdProveedor()).append(", 'IMI', ").append(licencia.getIMI()).append(")");
				////logger.info(sql.toString());
				i = super.jdbcTemplate.update(sql.toString(), map);
				sql.delete(0, sql.length());
			}
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			sql.append(" UPDATE L	SET L.Factor =").append(licencia.getNCPF()).append("	FROM Licencia L");
			sql.append(" WHERE L.PK_Licencia = (SELECT PK_Licencia FROM Licencia L2 WHERE  L2.Tipo = 'NCPF' AND L2.FK01_Proveedor =").append(licencia.getIdProveedor()).append(")");

			////logger.info(sql.toString());
			i = super.jdbcTemplate.update(sql.toString(), map );
			sql.delete(0, sql.length());

			if(i == 0){
				sql.append("INSERT INTO Licencia (FK01_Proveedor, Tipo, Factor) VALUES(").append(licencia.getIdProveedor()).append(", 'NCPF', ").append(licencia.getNCPF()).append(")");
				////logger.info(sql.toString());
				i = super.jdbcTemplate.update(sql.toString(), map);
				sql.delete(0, sql.length());
			}
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			sql.append(" UPDATE L	SET L.Factor =").append(licencia.getNBRC()).append("	FROM Licencia L");
			sql.append(" WHERE L.PK_Licencia = (SELECT PK_Licencia FROM Licencia L2 WHERE  L2.Tipo = 'NBRC' AND L2.FK01_Proveedor =").append(licencia.getIdProveedor()).append(")");

			////logger.info(sql.toString());
			i = super.jdbcTemplate.update(sql.toString(), map);
			sql.delete(0, sql.length());

			if(i == 0){
				sql.append("INSERT INTO Licencia (FK01_Proveedor, Tipo, Factor) VALUES(").append(licencia.getIdProveedor()).append(", 'NBRC', ").append(licencia.getNBRC()).append(")");
				////logger.info(sql.toString());
				i = super.jdbcTemplate.update(sql.toString(), map);
				sql.delete(0, sql.length());
			}	
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			sql.append(" UPDATE L	SET L.Factor =").append(licencia.getNCIMB()).append("	FROM Licencia L");
			sql.append(" WHERE L.PK_Licencia = (SELECT PK_Licencia FROM Licencia L2 WHERE  L2.Tipo = 'NCIMB' AND L2.FK01_Proveedor =").append(licencia.getIdProveedor()).append(")");

			////logger.info(sql.toString());
			i = super.jdbcTemplate.update(sql.toString(), map);
			sql.delete(0, sql.length());

			if(i == 0){
				sql.append("INSERT INTO Licencia (FK01_Proveedor, Tipo, Factor) VALUES(").append(licencia.getIdProveedor()).append(", 'NCIMB', ").append(licencia.getNCIMB()).append(")");
				////logger.info(sql.toString());
				i = super.jdbcTemplate.update(sql.toString(), map);
				sql.delete(0, sql.length());
			}		
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			sql.append(" UPDATE L	SET L.Factor =").append(licencia.getNCYC()).append("	FROM Licencia L");
			sql.append(" WHERE L.PK_Licencia = (SELECT PK_Licencia FROM Licencia L2 WHERE  L2.Tipo = 'NCYC' AND L2.FK01_Proveedor =").append(licencia.getIdProveedor()).append(")");

			////logger.info(sql.toString());
			i = super.jdbcTemplate.update(sql.toString(), map);
			sql.delete(0, sql.length());

			if(i == 0){
				sql.append("INSERT INTO Licencia (FK01_Proveedor, Tipo, Factor) VALUES(").append(licencia.getIdProveedor()).append(", 'NCYC', ").append(licencia.getNCYC()).append(")");
				////logger.info(sql.toString());
				i = super.jdbcTemplate.update(sql.toString(), map);
				sql.delete(0, sql.length());
			}				


			return true;
		} catch (Exception e) {
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sql: " + sql,licencia);
			return false;
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<ConfiguracionPrecio> getListaConfiguracionPrecioProveedor(
			Long idProveedor) throws ProquifaNetException {
		StringBuilder sbQuery = new StringBuilder(" ");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idProveedor", idProveedor);
			sbQuery.append(" \n SELECT PK_Configuracion_Precio, VCT.TIPO, VCST.SUBTIPO, CONTR, INDUSTIAL, CP.FUA, CPP.SUMA, ");
			sbQuery.append(" \n CP.Restringir_Distribuidor, CP.Restringir_FExpress, CP.Restringir_Stock, CPP.Caduca, COALESCE(CPC.TCLASIFI,0) TCLASIFI");
			sbQuery.append(" \n FROM Configuracion_Precio AS CP");
			sbQuery.append(" \n LEFT JOIN(SELECT PK_Folio, Valor AS TIPO FROM ValorCombo WHERE Concepto='TipoProducto') AS VCT ON VCT.PK_Folio=CP.Tipo     ");
			sbQuery.append(" \n LEFT JOIN(SELECT PK_Folio, Valor AS SUBTIPO FROM ValorCombo WHERE Concepto='SubTipoProducto') AS VCST ON VCST.PK_Folio=CP.Subtipo    "); 
			sbQuery.append(" \n LEFT JOIN(SELECT PK_Folio, Valor AS CONTR FROM ValorCombo WHERE Concepto='Control') AS VCCTRL ON VCCTRL.PK_Folio=CP.Control     ");
			sbQuery.append(" \n LEFT JOIN(SELECT PK_Folio, Valor AS INDUSTIAL FROM ValorCombo WHERE Concepto='Industrial') AS IND ON IND.PK_Folio = CP.Industria    ");
			sbQuery.append(" \n LEFT JOIN (SELECT FK02_ConfFamilia, COUNT(FK02_ConfFamilia) AS SUMA, MAX(p.Caduca) Caduca FROM ConfiguracionPrecio_Producto PPC ");
			sbQuery.append(" \n LEFT JOIN (SELECT idProducto, Caduca FROM Productos WHERE Vigente = 1) p on p.idProducto = PPC.FK01_Producto GROUP BY FK02_ConfFamilia) AS CPP ON CPP.FK02_ConfFamilia = CP.PK_Configuracion_Precio ");
			sbQuery.append(" \n LEFT JOIN (SELECT COUNT(PK_ConfiguracionPrecioClasificacion) TCLASIFI, FK01_ConfigFamilia FROM ConfiguracionPrecio_Clasificacion GROUP BY FK01_ConfigFamilia) CPC ON CPC.FK01_ConfigFamilia = CP.PK_Configuracion_Precio");
			sbQuery.append(" \n WHERE  Nivel = 'Familia' AND FK01_Proveedor =").append(idProveedor);

			//logger.info(sbQuery.toString());

			return super.jdbcTemplate.query(sbQuery.toString(),map, new ListarConfiguracionPrecioProveedorRowMapper());

		} catch (Exception e) {
		//	logger.error("Error:" + e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sbQuery: " + sbQuery,"idProveedor: " + idProveedor);
			return new ArrayList<ConfiguracionPrecio>();
		}
	}


	@Override
	public Integer updateProveedorFlete(Flete flete)
			throws ProquifaNetException {
		StringBuilder sql = new StringBuilder(" ");
		try{
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("flete", flete);
			
			sql.append(" \n UPDATE Productos SET ");
			sql.append(" \n Codigo='").append(flete.getConcepto()).append("'");
			sql.append(" \n ,Concepto = '").append(flete.getLeyenda()).append("'");
			sql.append(" \n ,Costo = ").append(flete.getMonto());
			sql.append(" \n ,TEntrega = '").append(flete.getTiempoEntrega()).append("'");		
			sql.append(" \n ,Nota = '").append(flete.getConcatenaRuta()).append("'");
			sql.append(" \n ,Fecha = GETDATE()");
			if(flete.getHabilitado() == false){
				sql.append(" \n ,Vigente = 0");
			}else{
				sql.append(" \n ,Vigente = 1");
			}
			sql.append(" \n WHERE idProducto = ").append(flete.getIdFlete());


			//////logger.info(sql.toString());



			return super.jdbcTemplate.update(sql.toString(), map);
		}catch (Exception e) {
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sql: " + sql,flete);
			return -1;
		}
	}



	@Override
	public Long insertProveedorFlete(Flete flete, Long idProveedor) throws ProquifaNetException {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder(" ");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("flete", flete);
			map.put("idProveedor", idProveedor);
			Object[] params = {flete.getConcepto(), flete.getMonto(), flete.getLeyenda(), idProveedor, flete.getTiempoEntrega(), flete.getConcatenaRuta()};

			sql.append(" \n INSERT INTO Productos (Tipo, Fabrica, Codigo, Costo, Moneda, Concepto, Proveedor, Fecha, Vigente, Caduca, Subgrupo, TEntrega,EstadoPermiso, Disponibilidad,Nota)");
			sql.append(" \n VALUES ('Fletes', 'Fletes', ?, ?, 'Dolares', ?,?, GETDATE(), 1,");
			sql.append(" \n DATEADD(yy, DATEDIFF(yy,0,getdate()) + 1, -1), 'Flete', ?, 'Sin permiso', 'Disponible',?)");


			super.jdbcTemplate.update(sql.toString(), map);
			return super.queryForLong("SELECT IDENT_CURRENT ('Productos')", map);

		} catch (Exception e) {
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sql: " + sql,flete, "idProveedor: " + idProveedor);
			return -1L;
		}
	}

	public Long insertProveedorFleteExpress(Flete flete, Long idProveedor) throws ProquifaNetException {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder(" ");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idProveedor", idProveedor);
			map.put("flete", flete);

			Object[] params = {flete.getConcepto(), flete.getMonto(), flete.getLeyenda(), idProveedor, flete.getTiempoEntrega(), flete.getConcatenaRuta()};

			//////logger.info("INSERT INTO Productos (Tipo, Fabrica, Codigo, Costo, Moneda, Concepto, Proveedor, Fecha, Vigente, Caduca, Subgrupo, TEntrega,EstadoPermiso, Disponibilidad,Nota)" + 
			//					"VALUES ('Fletes', 'Fletes', '"+ flete.getConcepto() + "', " + flete.getMonto() + ", 'Dolares', '"+ flete.getLeyenda() +"', "+idProveedor+", GETDATE(), 1,"+
			//					" DATEADD(yy, DATEDIFF(yy,0,getdate()) + 1, -1), 'FleteExpress', '" + flete.getTiempoEntrega() + "', 'Sin permiso', 'Disponible','" + flete.getConcatenaRuta() + "')");
			
			sql.append(" \n INSERT INTO Productos (Tipo, Fabrica, Codigo, Costo, Moneda, Concepto, Proveedor, Fecha, Vigente, Caduca, Subgrupo, TEntrega,EstadoPermiso, Disponibilidad, Nota)");
			sql.append(" \n VALUES ('Fletes', 'Fletes', ?, ?, 'Dolares', ?,?, GETDATE(), 1,");
			sql.append(" \n  DATEADD(yy, DATEDIFF(yy,0,getdate()) + 1, -1), 'FleteExpress', ?, 'Sin permiso', 'Disponible',?)");


			super.jdbcTemplate.update(sql.toString(), map);
			return super.queryForLong("SELECT IDENT_CURRENT ('Productos')", map);

		} catch (Exception e) {
			//logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sql: " + sql,flete, "idProveedor: " + idProveedor);
			return -1L;
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Flete> findFleteProveedor(Long idProveedor)
			throws ProquifaNetException {
		StringBuilder sql = new StringBuilder(" ");
		//		
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idProveedor", idProveedor);
			
			sql.append(" \n SELECT P.Codigo, P.Concepto, P.Costo Monto, P.TEntrega, P.Subgrupo, FUA.FUA_M, p.idproducto PK_Flete, p.Vigente, p.Nota");
			sql.append(" \n FROM Productos P");
			sql.append(" \n LEFT JOIN (	SELECT Proveedor, MAX(Fecha) AS FUA_M FROM Productos WHERE Proveedor = ").append(idProveedor).append(" AND Tipo = 'Fletes'");
			sql.append(" \n 			AND Fabrica = 'Fletes' AND FK03_Categoria_PrecioLista IS NULL AND Subgrupo = 'Flete' GROUP BY Proveedor) FUA ON FUA.Proveedor = P.Proveedor");
			sql.append(" \n WHERE P.Proveedor = ").append(idProveedor).append(" AND Tipo = 'Fletes' AND Fabrica = 'Fletes' AND FK03_Categoria_PrecioLista IS NULL AND Subgrupo = 'Flete'");

			//////logger.info(sql.toString());
			return super.jdbcTemplate.query(sql.toString(),map, new FleteProveedorRowMapper());
		}catch(Exception e){
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sql: " + sql, "idProveedor: " + idProveedor);
			return new ArrayList<Flete>();
		}
	}



	@Override
	public Boolean updateRestringirConfiguracionPrecio(
			ConfiguracionPrecio configuracionPrecio)
					throws ProquifaNetException {
		StringBuilder sql = new StringBuilder(" ");
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("configuracionPrecio", configuracionPrecio);
			Object[] params = {configuracionPrecio.getRestringirDistribuidor(), configuracionPrecio.getRestringirFExpress(), configuracionPrecio.getRestringirStock(), configuracionPrecio.getIdConfiguracionPrecio()};

			sql.append(" \n UPDATE Configuracion_Precio SET ");
			sql.append(" \n Restringir_Distribuidor = ?, Restringir_FExpress = ?, Restringir_Stock = ? ");
			sql.append(" \n WHERE PK_Configuracion_Precio = ?");

			//////logger.info(sql.toString());

			super.jdbcTemplate.update(sql.toString(), map);
			return true;

		}catch (Exception e) {
			//logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sql: " + sql, configuracionPrecio);
			return false;
		}
	}

	@Override
	public Boolean updateCaducidadProductosConfiguracionPrecio(
			ConfiguracionPrecio configuracionPrecio)
					throws ProquifaNetException {
		StringBuilder sql = new StringBuilder(" ");
		try{
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("configuracionPrecio", configuracionPrecio);
			
			Object[] params = {configuracionPrecio.getCaducidad(), configuracionPrecio.getIdConfiguracionPrecio()};

			sql.append(" \n UPDATE Productos SET ");
			sql.append(" \n Caduca = ?, Fecha = GETDATE() ");
			sql.append(" \n WHERE idProducto IN (");
			sql.append(" \n 	SELECT idProducto FROM Productos AS P");
			sql.append(" \n 	LEFT JOIN (SELECT * FROM ConfiguracionPrecio_Producto ) AS CPP ON CPP.FK01_Producto = P.idProducto");
			sql.append(" \n 	WHERE P.Vigente = 1 AND CPP.FK02_ConfFamilia = ?)");

			//////logger.info(sql.toString());

			super.jdbcTemplate.update(sql.toString(), map);
			return true;

		}catch (Exception e) {
			//logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sql: " + sql, configuracionPrecio);
			return false;
		}

	}

	@SuppressWarnings("unchecked")
	public List<ConfiguracionPrecioProducto> findConfiguracionesPrecioCosto(Long idConfigFam) {
		StringBuilder sbQuery = new StringBuilder("");
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idConfigFam", idConfigFam);
			sbQuery = new StringBuilder("\n DECLARE @idConfigPrecio AS Integer=" + idConfigFam + " ");		

			sbQuery.append(" \n  SELECT DISTINCT COALESCE(CPP.FK03_ConfCosto,CPP.FK02_ConfFamilia) PK_Configuracion_Precio,");
			sbQuery.append(" \n  COALESCE(CPP.FK03_ConfCosto,0) FK03_ConfCosto, CPP.FK02_ConfFamilia  AS idConfigFamilia, ");
			sbQuery.append(" \n  CF.PK_Costo_Factor AS idCostoFactor,CAST((PROD.Costo * PROVEE.TC) AS DECIMAL (9,2))Costo,");
			sbQuery.append(" \n  ROUND( PORC.PORCIENTO,1) AS Porciento,");
			sbQuery.append(" \n  (CASE WHEN PROVEE.Moneda='Pesos' THEN 'MXN' WHEN PROVEE.Moneda='Euros' THEN 'EUR' WHEN PROVEE.Moneda='Dolares' THEN 'USD' ");
			sbQuery.append(" \n  	WHEN PROVEE.Moneda='Libras' THEN 'LBS' WHEN PROVEE.Moneda='DlCan' THEN 'CAD'END) AS Moneda,");
			sbQuery.append(" \n  CPP.FK02_ConfFamilia ConfiguracionPrecio, (CAT.PK_Categoria) PrecioLista,PROVEE.MonedaVenta,");
			sbQuery.append(" \n  CASE WHEN LICE.Factor = -1 OR  LICE.Factor IS NULL THEN '' ELSE PROD.DepositarioInternacional END Licencia, ");
			sbQuery.append(" \n  CASE WHEN CPP.FK03_ConfCosto IS NULL THEN 0 ELSE 1 END RESTABLECER_COSTO, PROD.Costo costoCompra, ");
			sbQuery.append(" \n  CF.Stock_Disable, CF.FleteExpress_Disable,CF.Piezas, P.TOTALPRO CANT_PROD, CP.FK01_Proveedor,  max(P.id) idProducto, CF.Factor_CostoFijo,");
			sbQuery.append(" \n  PROVEE.MonedaVenta, IND.Valor AS Industria, CAT.PK_Categoria PrecioLista,");
			sbQuery.append(" \n CF.factor_AAplus,CF.factor_AA,CF.factor_AM,CF.factor_AB,CF.factor_MA,CF.factor_MM,CF.factor_MB,CF.factor_FExpress,CF.factor_Stock, CF.Factor_Bajo, ");
			sbQuery.append(" \n CF.Factor_Distribuidor AS Distribuidor, ");
			sbQuery.append(" \n ").append( obtenerConusultaFormulaCatalogo("Costo"));
			sbQuery.append(" CPP.FK03_ConfCosto ,PROD.Costo,  coalesce(CompuestaCostoF,1) CompuestaCostoF, coalesce(CompuestaFactorU,1) CompuestaFactorU  ");
			sbQuery.append(" \n FROM Categoria_PrecioLista CAT ");
			sbQuery.append(" \n LEFT JOIN (SELECT null FK04_ConfProducto, FK02_ConfFamilia, FK03_ConfCosto, FK01_Producto  FROM ConfiguracionPrecio_Producto) AS CPP ON CPP.FK02_ConfFamilia =  @idConfigPrecio");
			sbQuery.append(" \n LEFT JOIN (SELECT * FROM Configuracion_Precio) CP ON cp.PK_Configuracion_Precio = coalesce(cpp.FK03_ConfCosto, cpp.FK02_ConfFamilia)"); 
			sbQuery.append(" \n LEFT JOIN (SELECT Clave, CASE WHEN Moneda = MonedaVenta THEN 1 ELSE CASE WHEN Tipo_Cambio IS NULL OR Tipo_Cambio = 0 THEN 1 ELSE Tipo_Cambio END END  AS TC, MonedaVenta, Moneda FROM Proveedores) AS PROVEE ON PROVEE.Clave = CP.FK01_Proveedor ");  
			sbQuery.append(" \n LEFT JOIN (SELECT * FROM Productos) PROD ON PROD.idProducto = CPP.FK01_Producto  ");
			sbQuery.append(" \n LEFT JOIN (SELECT FK03_Categoria_PrecioLista, MAX(idProducto) id, COUNT(idProducto) TOTALPRO, DepositarioInternacional FROM Productos WHERE Vigente =1" );
			sbQuery.append(" \n GROUP BY FK03_Categoria_PrecioLista, DepositarioInternacional) as p on p.FK03_Categoria_PrecioLista = cat.PK_Categoria AND COALESCE(P.DepositarioInternacional,'') = COALESCE(PROD.DepositarioInternacional ,'') ");
			sbQuery.append(" \n LEFT JOIN(SELECT * FROM Costo_Y_Factor) AS CF ON CF.PK_Costo_Factor=CP.FK02_Costo_Factor   ");
			sbQuery.append(" \n LEFT JOIN( SELECT CPL.PK_Categoria, ");
			sbQuery.append(" \n 			((SUM((CPP.CostoNuevo * 100)/case when CPP.CostoAnterior = 0 then 1 else cpp.CostoAnterior end)-100)/case when (COUNT(CPP.CostoNuevo)) = 0 then 1 else COUNT(CPP.CostoNuevo) end ) AS PORCIENTO  ");
			sbQuery.append(" \n 			 FROM Categoria_PrecioLista CPL");
			sbQuery.append(" \n 			 LEFT JOIN (SELECT FK03_Categoria_PrecioLista, MAX(idProducto) idProducto FROM Productos WHERE  idProducto IN (SELECT FK01_Producto FROM CambioPrecioProducto) ");
			sbQuery.append(" \n 			 GROUP BY FK03_Categoria_PrecioLista) AS PROD ON PROD.FK03_Categoria_PrecioLista = CPL.PK_Categoria");
			sbQuery.append(" \n 			 LEFT JOIN(SELECT MAX (PK_CambioPrecioProducto) ID, FK01_Producto FROM CambioPrecioProducto GROUP BY FK01_Producto ) AS ID ON ID.FK01_Producto =PROD.idProducto ");
			sbQuery.append(" \n 			 LEFT JOIN(SELECT * FROM CambioPrecioProducto) AS CPP ON CPP.PK_CambioPrecioProducto = ID.ID ");
			sbQuery.append(" \n 			 GROUP BY CPL.PK_Categoria, CPP.CostoNuevo , CPP.CostoAnTErior, PROD.idProducto  ) ");
			sbQuery.append(" \n 			AS PORC ON PORC.PK_Categoria = CAT.PK_Categoria ");
			sbQuery.append(" \n  LEFT JOIN (SELECT PK_Lugar_AgenteAduanal, FK01_AgenteAduanal, Monto, Porcentaje FROM Lugar_AgenteAduanal) AS LAA ON LAA.PK_Lugar_AgenteAduanal = CF.FK02_LugarAgenteAduanal  ");
			sbQuery.append(" \n  LEFT JOIN (SELECT * FROM LAA_Concepto) AS LAAC ON LAAC.PK_AAConcepto = CF.FK03_LAAConcepto  ");
			sbQuery.append(" \n  LEFT JOIN (SELECT * FROM Licencia) AS LICE ON LICE.FK01_Proveedor = CP.FK01_Proveedor AND LICE.Tipo = PROD.DepositarioInternacional ");
			sbQuery.append(" \n  LEFT JOIN(SELECT * FROM ValorCombo WHERE Concepto='Industrial') AS IND ON IND.PK_Folio = CP.Industria   ");
			sbQuery.append(" \n LEFT JOIN (SELECT * FROM CostoFactor_Licencia) CFL ON CFL.FK01_CostoFactor = CF.PK_Costo_Factor AND CFL.Licencia = PROD.DepositarioInternacional" );
			sbQuery.append(" \n  WHERE CPP.FK02_ConfFamilia = @idConfigPrecio  AND CAT.PK_Categoria = PROD.FK03_Categoria_PrecioLista  AND   PROD.Vigente = 1   ");
			sbQuery.append(" \n GROUP BY    CP.PK_Configuracion_Precio,CAT.PK_Categoria, CP.FK01_Proveedor, CPP.FK03_ConfCosto, CPP.FK02_ConfFamilia, PROVEE.Moneda,  ");
			sbQuery.append(" \n PROD.Costo,PROVEE.TC,  PROVEE.MonedaVenta, CF.PK_Costo_Factor, CF.Stock_Disable, CF.FleteExpress_Disable, CF.Piezas, IND.Valor, ");
			sbQuery.append(" \n PORC.PORCIENTO, LICE.Factor,PROD.DepositarioInternacional, CF.ValorEnAduana, CF.Flete, CF.Descuento, CF.Factor_Distribuidor, CF.Factor_Publico,");
			sbQuery.append(" \n CF.Factor_CostoFijo,CF.Factor_CostoFijo,CF.factor_AAplus,CF.Factor_AA, CF.Factor_AM, CF.Factor_AB,CF.Factor_MA,CF.Factor_MM,CF.Factor_MB,");
			sbQuery.append(" \n CF.Factor_FExpress,CF.Factor_Stock,CF.Factor_Bajo,CF.Costo_Consularizacion,CF.Flete_Documentacion,CF.Permiso,CF.AlmacenDestino, ");
			sbQuery.append(" \n CF.Factor_IGI, CF.FactorDTA,LAAC.Monto, LAAC.Monto, LAAC.Porcentaje,CF.MontoLicencia,CF.PorcentajeLicencia, CPP.FK04_ConfProducto,");
			sbQuery.append(" \n CFL.Monto,CFL.Porcentaje,P.TOTALPRO, coalesce(CompuestaCostoF,1) , coalesce(CompuestaFactorU,1) ");
			sbQuery.append(" \n ORDER BY PK_Configuracion_Precio ,CF.Piezas DESC,PROD.Costo ASC");

						//logger.info(sbQuery.toString());

			return super.jdbcTemplate.query(sbQuery.toString(), map, new ConfiguracionPrecioCostoRowMapper());

		} catch (Exception e) {
		//	logger.error("Error:" + e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sbQuery: " + sbQuery, "idConfigFam: " + idConfigFam);
			return new ArrayList<ConfiguracionPrecioProducto>();
		}
	}

	@Override
	public Flete findFleteExpressProveedor(Long idProveedor)
			throws ProquifaNetException {
		Flete f = new Flete();
		StringBuilder sql = new StringBuilder(" ");
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idProveedor", idProveedor);
			sql.append(" \n SELECT top 1 P.Codigo, P.Concepto, P.Costo Monto, P.TEntrega, P.Subgrupo, FUA.FUA_M, p.idproducto PK_Flete, P.Vigente, p.Nota");
			sql.append(" \n FROM Productos P");
			sql.append(" \n LEFT JOIN (	SELECT Proveedor, MAX(Fecha) AS FUA_M FROM Productos WHERE Proveedor = ").append(idProveedor).append(" AND Tipo = 'Fletes'");
			sql.append(" \n 			AND Fabrica = 'Fletes' AND FK03_Categoria_PrecioLista IS NULL AND Subgrupo = 'FleteExpress' GROUP BY Proveedor) FUA ON FUA.Proveedor = P.Proveedor");
			sql.append(" \n WHERE P.Proveedor = ").append(idProveedor).append(" AND Tipo = 'Fletes' AND Fabrica = 'Fletes' AND FK03_Categoria_PrecioLista IS NULL AND Subgrupo = 'FleteExpress'");

			////logger.info(sql.toString());
			f = (Flete) super.jdbcTemplate.queryForObject(sql.toString(),map, new FleteProveedorRowMapper());
			return f;
		}catch(EmptyResultDataAccessException e){
			return new Flete();
		}
		catch(Exception e){
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sbQuery: " + sql, "idProveedor: " + idProveedor);
			return new Flete();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ClasificacionConfiguracionPrecio> findConceptoClasifConfigPrecio(
			Long idConfigFam, Long idCliente) throws ProquifaNetException {
		StringBuilder sql = new StringBuilder(" ");
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCliente", idCliente);
			map.put("idConfigFam", idConfigFam);
	
			sql.append("\n SELECT CPC.FK01_ConfigFamilia, COALESCE( CPC.FK02_ConfigClasificacion, CPC.FK01_ConfigFamilia ) CONFIG, ");
			sql.append("\n CPC.PK_ConfiguracionPrecioClasificacion CLASIFICACION, Concepto, COALESCE(T,0) TPRODUCTOS, CASE WHEN Factor IS NULL THEN 0 ELSE 1 END RES_CLIE,");
			sql.append("\n CASE WHEN CCP.FactorTemp IS NOT NULL THEN 1 ELSE 0 END temporal");
			sql.append("\n FROM ConfiguracionPrecio_Clasificacion CPC");
			sql.append("\n LEFT JOIN (SELECT COUNT(idProducto) AS T, FK04_Clasificacion_ConfiguracionPrecio,MAX(idProducto) idProducto  FROM Productos WHERE Vigente = 1  GROUP BY FK04_Clasificacion_ConfiguracionPrecio )AS P ");
			sql.append("\n 		ON P.FK04_Clasificacion_ConfiguracionPrecio = CPC.PK_ConfiguracionPrecioClasificacion");
			sql.append("\n LEFT JOIN (SELECT FK01_Producto, FK09_CliClasificacion, FK02_ConfFamilia FROM ConfiguracionPrecio_Producto) CPP ON CPP.FK01_Producto = P.idProducto and cpp.FK02_ConfFamilia = cpc.FK01_ConfigFamilia");
			sql.append("\n LEFT JOIN (SELECT * FROM Cliente_ConfiguracionPrecio  ) CCP ON AK_ClienteConfigPrecio = CPP.FK09_CliClasificacion AND CCP.FK01_Cliente =" + idCliente);
			sql.append("\n WHERE CPC.FK01_ConfigFamilia = ").append(idConfigFam);

		//	logger.info(sql.toString());
			return super.jdbcTemplate.query(sql.toString(),map, new ConfigPrecioClasificacionRowMapper());

		}catch(Exception e){
		//logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sbQuery: " + sql, "idConfigFam: " + idConfigFam,"idCliente: " + idCliente);
			return new ArrayList<ClasificacionConfiguracionPrecio>();
		}

	}

	@Override
	public Integer insertConceptoClasifConfigPrecio(
			ClasificacionConfiguracionPrecio clasificacion)
					throws ProquifaNetException {
		StringBuilder sql = new StringBuilder(" "); 
		try{
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("IdConfigFamilia", clasificacion.getIdConfigFamilia());
			map.put("Concepto", clasificacion.getConcepto());
	
			Object[] params = {clasificacion.getIdConfigFamilia(), clasificacion.getConcepto()};

			sql.append("\n INSERT INTO ConfiguracionPrecio_Clasificacion");
			sql.append("\n (FK01_ConfigFamilia,Concepto)");
			sql.append("\n VALUES(?, ?)");


			return super.jdbcTemplate.update(sql.toString(), map);

		}catch (Exception e) {
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sbQuery: " + sql, clasificacion);
			return -1;
		}

	}

	@Override
	public Integer updateConceptoClasifConfigPrecio(
			ClasificacionConfiguracionPrecio clasificacion)
					throws ProquifaNetException {
		StringBuilder sql = new StringBuilder(" "); 
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Clasificacion", clasificacion.getIdClasificacion());
			map.put("Concepto", clasificacion.getConcepto());
			Object[] params = {clasificacion.getConcepto(), clasificacion.getIdClasificacion()};

			sql.append("\n UPDATE ConfiguracionPrecio_Clasificacion SET");
			sql.append("\n Concepto= ?");
			sql.append("\n  WHERE PK_ConfiguracionPrecioClasificacion = ?");

			return super.jdbcTemplate.update(sql.toString(), map);

		}catch (Exception e) {
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sql: " + sql, clasificacion);
			return -1;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConfiguracionPrecioProducto> obtenerConfiguracionClasificacion(
			Long idConfiguracion, Long idCliente, String tipoNivelIngreso,
			Long idClasificacion) throws ProquifaNetException {
		StringBuilder sql=new StringBuilder("");
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idConfiguracion", idConfiguracion);
			map.put("idCliente", idCliente);
			map.put("tipoNivelIngreso", tipoNivelIngreso);
			
			map.put("idClasificacion", idClasificacion);
			
			
			sql.append("\n DECLARE @idCliente AS Integer = ").append(idCliente);
			sql.append("\n DECLARE @Clasificacion AS Integer = ").append(idClasificacion);
			sql.append("\n DECLARE @idConfig AS Integer = ").append(idConfiguracion);
			sql.append("\n SELECT Top 1 0 FactorClienteConf,CP.PK_Configuracion_Precio,IND.Valor AS Industria,VCTP.Valor AS Tipo,COALESCE(VCST.Valor,'') AS SubTipo, ");
			sql.append("\n COALESCE(CONF.idConfigFamilia,CP.PK_Configuracion_Precio) AS idConfigFamilia, COALESCE(VCCTRL.Valor,'') AS Control,'Clasificacion' Nivel,CP.FK01_Proveedor AS idProveedor, ");
			sql.append("\n CF.Flete_Documentacion,NULL Porciento, CF.Factor_IGI, CF.Factor_CostoFijo, CF.Factor_Utilidad,PROD.Costo costoCompra,CF.Stock_Disable, CF.FleteExpress_Disable, ");
			sql.append("\n CF.Permiso,CF.FactorDTA, CF.VALORENADUANA, CF.DESCUENTO, CF.FLETE, CF.Honorarios, NULL AS TEntrega,PROD.idProducto idProducto, NULL Costo, NULL Moneda, NULL Codigo,  ");
			sql.append("\n NULL Fabrica, CF.Piezas, CF.factor_AAplus,CF.factor_AA,CF.factor_AM,CF.factor_AB,CF.factor_MA,CF.factor_MM,CF.factor_MB,CF.factor_FExpress,CF.factor_Stock,CF.factor_Comision,   ");
			sql.append("\n CF.Factor_Bajo, CF.Factor_Distribuidor AS Distribuidor,CF.Factor_Publico AS Publico,NULL ConfiguracionPrecio, prod.FK03_Categoria_PrecioLista PrecioLista ,1 Factor, ");
			sql.append("\n 1 PK_ConfigPrecio_Producto, 0 Restablecer, CP.FUA, 0 RESTABLECER_COSTO,");
			sql.append("\n CF.FK01_AgenteAduanal, CF.FK02_LugarAgenteAduanal, CF.FK03_LAAConcepto,");
			sql.append("\n null IDConfCosto, null idCostoFactor, null Costo_Consularizacion,  cf.AlmacenDestino,  ");
			sql.append("\n case when CPROD.FK08_ConfClasificacion is Not null then 1 else 0 end RestaClasif,  @Clasificacion idClasificacion,");
			sql.append("\n null montoLicencia, null PorcentajeLicencia, coalesce(CompuestaCostoF,1) CompuestaCostoF, coalesce(CompuestaFactorU,1) CompuestaFactorU ");
			sql.append("\n FROM Configuracion_Precio AS CP ");
			sql.append("\n LEFT JOIN(SELECT * FROM ConfiguracionPrecio_Producto) AS CPROD ON CP.PK_Configuracion_Precio =");
			sql.append("\n (case when Nivel = 'Familia' then CPROD.FK02_ConfFamilia when Nivel = 'Costo' then CPROD.FK03_ConfCosto when Nivel = 'Clasificacion' then CPROD.FK08_ConfClasificacion when Nivel = 'Producto' then CPROD.FK04_ConfProducto end)"); 
			sql.append("\n LEFT JOIN(SELECT * FROM Costo_Y_Factor) AS CF ON CF.PK_Costo_Factor = CP.FK02_Costo_Factor");
			sql.append("\n LEFT JOIN(SELECT * FROM Tiempo_Entrega) AS TE ON TE.PK_Tiempo_Entrega = CP.FK03_Tiempo_Entrega");
			sql.append("\n LEFT JOIN(SELECT * FROM ValorCombo WHERE Concepto='TipoProducto') AS VCTP ON VCTP.PK_Folio = CP.Tipo  ");
			sql.append("\n LEFT JOIN(SELECT * FROM ValorCombo WHERE Concepto='SubTipoProducto') AS VCST ON VCST.PK_Folio = CP.Subtipo  ");
			sql.append("\n LEFT JOIN(SELECT * FROM ValorCombo WHERE Concepto='Control') AS VCCTRL ON VCCTRL.PK_Folio = CP.Control     ");
			sql.append("\n  LEFT JOIN(SELECT * FROM ValorCombo WHERE Concepto='Industrial') AS IND ON IND.PK_Folio = cp.Industria     ");
			sql.append("\n LEFT JOIN (SELECT * FROM AA_ConfiguracionPrecio) AS AACP ON AACP.FK02_ConfiguracionPrecio = CP.PK_Configuracion_Precio  ");
			sql.append("\n LEFT JOIN (SELECT * FROM AgenteAduanal) AS AA ON AA.PK_AgenteAduanal = CF.FK01_AgenteAduanal");
			sql.append("\n LEFT JOIN (SELECT PK_Lugar_AgenteAduanal, FK01_AgenteAduanal, Monto, Porcentaje FROM Lugar_AgenteAduanal) AS LAA ON LAA.PK_Lugar_AgenteAduanal = CF.FK02_LugarAgenteAduanal ");
			sql.append("\n LEFT JOIN (SELECT * FROM LAA_Concepto) AS LAAC ON LAAC.PK_AAConcepto = CF.FK03_LAAConcepto ");
			sql.append("\n LEFT JOIN(SELECT CONF.PK_Configuracion_Precio AS idConfig,FAMILY.PK_Configuracion_Precio idConfigFamilia ");
			sql.append("\n 			FROM Configuracion_Precio AS CONF");
			sql.append("\n 			LEFT JOIN (SELECT * FROM Configuracion_Precio WHERE Nivel='Familia') AS FAMILY ON FAMILY.Tipo = CONF.Tipo AND coalesce(FAMILY.Subtipo,1)=coalesce(CONF.Subtipo,1) AND  ");
			sql.append("\n 			coalesce( FAMILY.Control,1)= coalesce(CONF.Control,1) AND FAMILY.FK01_Proveedor= CONF.FK01_Proveedor AND FAMILY.Industria=CONF.Industria) AS CONF ON CONF.idConfig = cp.PK_Configuracion_Precio");
			sql.append("\n LEFT JOIN(SELECT MAX(FK01_Producto) AS ID, FK03_ConfCosto FROM ConfiguracionPrecio_Producto GROUP BY FK03_ConfCosto) AS PRO ON PRO.FK03_ConfCosto=CP.PK_Configuracion_Precio  ");
			sql.append("\n  LEFT JOIN(SELECT Costo,  idProducto, fk04_Clasificacion_ConfiguracionPrecio, Vigente, FK03_Categoria_PrecioLista FROM Productos) AS PROD ON PROD.idProducto = CPROD.FK01_Producto  " +
					"		AND  ((cp.Nivel = 'Clasificacion' AND PROD.fk04_Clasificacion_ConfiguracionPrecio = @Clasificacion ) )  ");
			sql.append("\n WHERE CP.PK_Configuracion_Precio=@idConfig ");
			sql.append("");

//			logger.info(sql.toString());
			
			return super.jdbcTemplate.query(sql.toString(), map, new ConfiguracionPrecioRowMapper());
	

		}catch(Exception e){
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sql: " + sql, "idConfiguracion: " + idConfiguracion, "idCliente: " + idCliente,
					"tipoNivelIngreso: " + tipoNivelIngreso, "idClasificacion: " + idClasificacion);
			return new ArrayList<ConfiguracionPrecioProducto>();
		}
	}

	@Override
	public Integer deleteConceptoClasifConfigPrecio(
			ClasificacionConfiguracionPrecio clasificacion)
					throws ProquifaNetException {
		StringBuilder sql = new StringBuilder(" ");
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("clasificacion", clasificacion);
			Object[] params = {clasificacion.getIdClasificacion(), clasificacion.getIdClasificacion()};

			sql.append("\n DELETE ConfiguracionPrecio_Clasificacion ");
			sql.append("\n WHERE PK_ConfiguracionPrecioClasificacion = ? ");
			sql.append("\n AND (SELECT COALESCE(COUNT(idProducto),0) T FROM Productos WHERE FK04_Clasificacion_ConfiguracionPrecio = ? GROUP BY FK04_Clasificacion_ConfiguracionPrecio) IS NULL");

			//////logger.info(sql.toString());

			return super.jdbcTemplate.update(sql.toString(), map);

		}catch (Exception e) {
			//logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sql: " + sql, clasificacion);
			return -1;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConfiguracionPrecioProducto> findClasificacionPrecioProductoPorCatPrecio(
			Long idClasificacion) throws ProquifaNetException {
		StringBuilder sbQuery = new StringBuilder("");	
		try {
			sbQuery.append(" \n DECLARE @idClasificacion AS Integer=").append(idClasificacion);
			sbQuery.append(" \n SELECT DISTINCT COALESCE(CPP.FK08_ConfClasificacion,CPP.FK02_ConfFamilia) PK_Configuracion_Precio,");
			sbQuery.append(" \n coalesce(CPP.FK08_ConfClasificacion,0) idClasificacion, CPP.FK02_ConfFamilia  AS idConfigFamilia,");
			sbQuery.append(" \n CF.PK_Costo_Factor AS idCostoFactor,CAST((PROD.Costo * PROVEE.TC) AS DECIMAL (9,2))Costo,");
			sbQuery.append(" \n 1 AS Porciento, 0 RESTABLECER_COSTO, PROD.Costo costoCompra, ");
			sbQuery.append(" \n (CASE WHEN PROD.Moneda='Pesos' THEN 'MXN' WHEN PROD.Moneda='Euros' THEN 'EUR' WHEN Moneda='Dolares' THEN 'USD'");
			sbQuery.append(" \n 	WHEN PROD.Moneda='Libras' THEN 'LBS' WHEN PROD.Moneda='DlCan' THEN 'CAD'END) AS Moneda,");
			sbQuery.append(" \n CASE WHEN LICE.Factor = -1 OR  LICE.Factor IS NULL THEN '' ELSE PROD.DepositarioInternacional END Licencia,  ");
			sbQuery.append(" \n CF.Stock_Disable, CF.FleteExpress_Disable,CF.Piezas, COUNT(CPP.FK01_Producto) CANT_PROD, CP.FK01_Proveedor, PROD.idProducto, ");
			sbQuery.append(" \n PROVEE.MonedaVenta, IND.Valor AS Industria, CAT.PK_Categoria PrecioLista,");
			sbQuery.append(" \n CF.Factor_Distribuidor Distribuidor, CF.Factor_Publico,CF.Factor_CostoFijo,CF.Factor_AAplus,CF.Factor_AA, CF.factor_Bajo, CF.factor_FExpress, CF.factor_Stock,");
			sbQuery.append(" \n CF.Factor_AM, CF.Factor_AB,CF.Factor_MA,CF.Factor_MM,CF.Factor_MB, coalesce(CompuestaCostoF,1) CompuestaCostoF, coalesce(CompuestaFactorU,1) CompuestaFactorU,");
			sbQuery.append(" \n ").append( obtenerConusultaFormulaCatalogo("Clasificacion"));
			sbQuery.append(" 1 ");// la funcion obtenerConsultaFormulaCatalogo siempre arroja un string con una ',' al final. Se pone esta columna para evitar un error en la consulta
			sbQuery.append(" \n FROM ConfiguracionPrecio_Clasificacion CPC");
			sbQuery.append(" \n LEFT JOIN (SELECT FK04_Clasificacion_ConfiguracionPrecio, FK03_Categoria_PrecioLista, DepositarioInternacional, Moneda, Costo, MAX(idProducto) idProducto, Vigente "); 
			sbQuery.append(" \n				FROM Productos GROUP BY FK04_Clasificacion_ConfiguracionPrecio, FK03_Categoria_PrecioLista, DepositarioInternacional, Moneda, Costo, Vigente) PROD ON PROD.FK04_Clasificacion_ConfiguracionPrecio = CPC.PK_ConfiguracionPrecioClasificacion ");
			sbQuery.append(" \n LEFT JOIN (SELECT * FROM Categoria_PrecioLista) CAT ON CAT.PK_Categoria = PROD.FK03_Categoria_PrecioLista");
			sbQuery.append(" \n LEFT JOIN (SELECT * FROM Configuracion_Precio) AS CP ON CP.PK_Configuracion_Precio = case when CPC.FK02_ConfigClasificacion is not null then CPC.FK02_ConfigClasificacion else CPC.FK01_ConfigFamilia end ");
			sbQuery.append(" \n LEFT JOIN (SELECT * FROM ConfiguracionPrecio_Producto) AS CPP ON CPP.FK01_Producto = PROD.idProducto and cpp.FK02_ConfFamilia = CPC.FK01_ConfigFamilia");
			sbQuery.append(" \n LEFT JOIN (SELECT Clave, CASE WHEN Moneda = MonedaVenta THEN 1 ELSE CASE WHEN Tipo_Cambio IS NULL OR Tipo_Cambio = 0 THEN 1 ELSE Tipo_Cambio END END  AS TC, ");
			sbQuery.append(" \n 		MonedaVenta FROM Proveedores) AS PROVEE ON PROVEE.Clave = CP.FK01_Proveedor");
			sbQuery.append(" \n LEFT JOIN (select FK03_Categoria_PrecioLista, MAX(idProducto) id, Vigente from Productos where Vigente = 1  group by FK03_Categoria_PrecioLista,Vigente) as p on p.FK03_Categoria_PrecioLista = cat.PK_Categoria");			
			sbQuery.append(" \n LEFT JOIN(SELECT * FROM Costo_Y_Factor) AS CF ON CF.PK_Costo_Factor=CP.FK02_Costo_Factor");
			sbQuery.append(" \n LEFT JOIN (SELECT PK_Lugar_AgenteAduanal, FK01_AgenteAduanal, Monto, Porcentaje FROM Lugar_AgenteAduanal) AS LAA ON LAA.PK_Lugar_AgenteAduanal = CF.FK02_LugarAgenteAduanal");
			sbQuery.append(" \n LEFT JOIN (SELECT * FROM LAA_Concepto) AS LAAC ON LAAC.PK_AAConcepto = CF.FK03_LAAConcepto   ");
			sbQuery.append(" \n LEFT JOIN (SELECT * FROM Licencia) AS LICE ON LICE.FK01_Proveedor = CP.FK01_Proveedor AND LICE.Tipo = PROD.DepositarioInternacional");
			sbQuery.append(" \n LEFT JOIN(SELECT * FROM ValorCombo WHERE Concepto='Industrial') AS IND ON IND.PK_Folio = CP.Industria    ");
			sbQuery.append(" \n LEFT JOIN (SELECT * FROM CostoFactor_Licencia) CFL ON CFL.FK01_CostoFactor = CF.PK_Costo_Factor AND CFL.Licencia = PROD.DepositarioInternacional  ");
			sbQuery.append(" \n WHERE CPC.PK_ConfiguracionPrecioClasificacion = @idClasificacion and p.vigente = 1 AND Prod.Vigente=1 ");
			sbQuery.append(" \n GROUP BY    CP.PK_Configuracion_Precio,CAT.PK_Categoria, CP.FK01_Proveedor, CPP.FK08_ConfClasificacion, CPP.FK02_ConfFamilia, PROD.Moneda, ");
			sbQuery.append(" \n PROD.Costo,PROVEE.TC,  PROVEE.MonedaVenta, PROD.idProducto, CF.PK_Costo_Factor, CF.Stock_Disable, CF.FleteExpress_Disable, CF.Piezas, IND.Valor,  ");
			sbQuery.append(" \n LICE.Factor,PROD.DepositarioInternacional, CF.ValorEnAduana, CF.Flete, CF.Descuento, CF.Factor_Distribuidor, CF.Factor_Publico,");
			sbQuery.append(" \n CF.Factor_CostoFijo,CF.Factor_AAplus,CF.Factor_AA, CF.Factor_AM, CF.Factor_AB,CF.Factor_MA,CF.Factor_MM,CF.Factor_MB,");
			sbQuery.append(" \n CF.Factor_FExpress,CF.Factor_Stock,CF.Factor_Bajo,CF.Costo_Consularizacion,CF.Flete_Documentacion,CF.Permiso,CF.AlmacenDestino, ");
			sbQuery.append(" \n CF.Factor_IGI, CF.FactorDTA,LAAC.Monto, LAAC.Monto, LAAC.Porcentaje, CPP.FK04_ConfProducto, CFL.Monto,CFL.Porcentaje, coalesce(CompuestaCostoF,1) , coalesce(CompuestaFactorU,1)  ");
			sbQuery.append(" \n ORDER BY PK_Configuracion_Precio ,CF.Piezas DESC,PROD.Costo ASC");

			//logger.info(sbQuery.toString());

			return super.jdbcTemplate.query(sbQuery.toString(), new ConfiguracionPrecioClasificacionRowMapper());

		} catch (Exception e) {
	//		logger.error("Error:" + e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sql: " + sbQuery, "idClasificacion: " + idClasificacion);
			return new ArrayList<ConfiguracionPrecioProducto>();
		}
	}

	@Override
	public FormulaPrecio getInformacionFormulaPrecioClasificacion(
			Long idProveedor, Long idProducto, int stock_flete, String nivel,
			Long idCliente, Long idConfig) throws ProquifaNetException {
		StringBuilder sql = new StringBuilder("");
		try{
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idProveedor", idProveedor);
			map.put("idProducto", idProducto);	
			map.put("stock_flete", stock_flete);
			map.put("nivel", nivel);
			map.put("idCliente", idCliente);
			map.put("idConfig", idConfig);
			
			
			String Q = 	" 	CASE WHEN CFL.Monto is not null and CFL.Monto > 0  " +
					"					THEN CFL.Monto " +
					"				 WHEN (CFL.Monto is null or CFL.Monto < 1 ) and CFL.Porcentaje > 0 " +
					"					THEN (1 + (CFL.Porcentaje/100)) " +
					" 				else" +
					"					 (1 + ((CASE WHEN LICE.Factor = -1 OR  LICE.Factor IS NULL THEN 0 ELSE LICE.Factor /100 END))) " +
					"				END ";	

			String O = 	" (CASE WHEN CF.ValorEnAduana = 0 OR CF.ValorEnAduana IS NULL THEN 1 ELSE CF.ValorEnAduana END) ";
			String R = 	" (CASE WHEN CF.Descuento IS NULL THEN 1 ELSE (1 - (CF.Descuento/100)) END)";	
			String PrecioL = "((PROD.Costo * PROVEE.TC) * (" + R + ")) ";
			String B = 	" (CASE WHEN CF.Flete = 0 OR  CF.Flete IS NULL THEN 0 ELSE CF.Flete END )";
			String N  = 	"\n CASE WHEN CF.VALORENADUANA <= 0 OR CF.VALORENADUANA IS NULL THEN case when CF.Piezas is null or CF.Piezas = 0 then 1 else CF.Piezas end " +
					" ELSE " +
					"	CASE WHEN  (CFL.Monto is not null and CFL.Monto > 0)  THEN " +
					"		CEILING((" + O + " - " + B + ")/(" + PrecioL + " +  " + Q +"))  " +
					"	ELSE " +
					"		CEILING((" + O + " - " + B + ")/(" + PrecioL + " *  " + Q +"))  " +
					"	END " +
					" END \n";

			N = " case when " + N + " = 0 then 1 else " + N + " end ";

			String VA =    " CASE WHEN (CFL.Monto is not null and CFL.Monto > 0)  " +
					" THEN ( (" + N + " * (" + PrecioL + "  +  " + Q +") ) + " + B +") " +
					" ELSE ( (" + N + " * " + PrecioL + "  * " + Q +" ) + " + B +") END	";
			String Valor = "(" + PrecioL + ") * (" + N + ")";
			String L = 	" ROUND( (CASE WHEN (CASE WHEN LAAC.Monto = 0  OR  LAAC.Monto IS NULL THEN LAAC.Porcentaje ELSE LAAC.Monto END) IS NULL THEN 0 " +
					" ELSE CASE WHEN LAAC.Monto = 0  OR  LAAC.Monto IS NULL THEN (" + VA + ") * ((LAAC.Porcentaje)/100) ELSE LAAC.Monto END END) ,3,1)";
			String F = 	" ROUND( ((CASE WHEN CF.Factor_CostoFijo = -1 OR CF.Factor_CostoFijo IS NULL THEN 0 ELSE CF.Factor_CostoFijo END)) ,3,1)";
			String M = 	" ROUND( (CASE WHEN CF.Permiso = -1 OR  CF.Permiso IS NULL THEN 0 ELSE CF.Permiso END) ,3,1)";
			String I = 	" ROUND( (CASE WHEN CF.AlmacenDestino = -1 OR  CF.AlmacenDestino IS NULL  THEN 0 ELSE CF.AlmacenDestino END) ,3,1)";


			sql.append(" \n DECLARE @idProveedor AS Integer= " + idProveedor);
			sql.append(" \n DECLARE @idProducto AS INTEGER = " + idProducto);
			sql.append(" \n DECLARE @FLETE_STOCK AS INTEGER = " + stock_flete);
			sql.append(" \n DECLARE @idCliente AS Integer=" + idCliente + "\n ");
			sql.append(" \n DECLARE @idConfigFamilia AS Integer=" + idConfig + "\n ");
			sql.append(" \n SELECT DISTINCT (CASE WHEN CF.FLETE = 0 OR  CF.FLETE IS NULL THEN 0 ELSE CF.FLETE END ) B,");
			sql.append(" \n (CASE WHEN CF.Costo_Consularizacion = -1 OR  CF.Costo_Consularizacion IS NULL THEN 0 ELSE CF.Costo_Consularizacion END) C,");
			sql.append(" \n (CASE WHEN CF.Flete_Documentacion = -1 OR  CF.Flete_Documentacion IS NULL THEN 0 ELSE CF.Flete_Documentacion END) D,");
			sql.append(" \n (CASE WHEN CF.Factor_IGI = -1 OR  CF.Factor_IGI IS NULL THEN 0 ELSE CF.Factor_IGI END) J,");
			sql.append(" \n (CASE WHEN CF.FactorDTA = -1 OR  CF.FactorDTA IS NULL THEN 0 ELSE CF.FactorDTA END) K,");
			sql.append(" \n " + PrecioL  + " PRECIOLISTA,");
			sql.append(" \n CF.Descuento R,");
			sql.append(" \n " + Q  + " Q,");	
			sql.append(" \n " + O  + " O,");	
			sql.append(" \n " + N  + " N,");
			sql.append(" \n " + VA + " VA,");
			sql.append(" \n " + Valor + " Valor,");
			sql.append(" \n " + L  + " L,");
			sql.append(" \n " + M  + " M,");
			sql.append(" \n " + I  + " I,");
			if(idCliente > 0){
				sql.append(" \n CASE WHEN CliCP2.Factor is not null THEN CliCP2.Factor	WHEN  CliCP3.Factor is not null THEN CliCP3.Factor ELSE CF.Factor_"+ nivel +"  END G,");
				sql.append(" \n CASE WHEN CliCP2.CostoFijo is not null THEN CliCP2.CostoFijo	WHEN  CliCP3.CostoFijo is not null THEN CliCP3.CostoFijo ELSE CF.Factor_CostoFijo  END F,");
				sql.append(" \n coalesce( CliCP2.CompuestaCostoF , CliCP3.CompuestaCostoF, CF.CompuestaCostoF, 1 )  CompuestaCostoF,  ");
				sql.append(" \n coalesce( CliCP2.CompuestaFactorU , CliCP3.CompuestaFactorU, CF.CompuestaFactorU, 1 )  CompuestaFactorU,  ");
			}else{
				sql.append(" \n ((CASE WHEN CF.factor_").append(nivel).append(" = -1 OR CF.factor_").append(nivel).append(" IS NULL THEN 0 ELSE CF.factor_").append(nivel).append(" END)) G,");
				sql.append(" \n " + F  + " F,");
				sql.append(" \n coalesce( CF.CompuestaCostoF , CF.CompuestaCostoF , 1 )  CompuestaCostoF,  ");
				sql.append(" \n coalesce( CF.CompuestaFactorU , CF.CompuestaFactorU , 1 )  CompuestaFactorU,  ");
			}
			sql.append(" \n ROUND( (CASE WHEN CF.Permiso = -1 OR  CF.Permiso IS NULL THEN 0 ELSE CF.Permiso END), 2,1) M,");
			sql.append(" \n ROUND( (CASE WHEN CF.AlmacenDestino = -1 OR  CF.AlmacenDestino IS NULL  THEN 0 ELSE CF.AlmacenDestino END), 2,1) I");
			sql.append(" \n FROM ConfiguracionPrecio_Producto AS CPP  ");
			sql.append(" \n LEFT JOIN(SELECT * FROM Configuracion_Precio) AS CP ON CP.PK_Configuracion_Precio = case when CPP.FK08_ConfClasificacion is Not null then CPP.FK08_ConfClasificacion else cpp.FK02_ConfFamilia end");
			sql.append(" \n LEFT JOIN(SELECT Clave, CASE WHEN Moneda = MonedaVenta THEN 1 ELSE CASE WHEN Tipo_Cambio IS NULL OR Tipo_Cambio = 0 THEN 1 ELSE Tipo_Cambio END END  AS TC, MonedaVenta FROM Proveedores) AS PROVEE ON PROVEE.Clave = CP.FK01_Proveedor ");
			sql.append(" \n LEFT JOIN(SELECT * FROM Productos) AS PROD ON PROD.idProducto= CPP.FK01_Producto ");
			sql.append(" \n LEFT JOIN(SELECT * FROM Costo_Y_Factor) AS CF ON CF.PK_Costo_Factor=CP.FK02_Costo_Factor    ");
			if(idCliente > 0){
				sql.append(" LEFT JOIN (SELECT * FROM Cliente_ConfiguracionPrecio) AS CliCP2 ON CliCP2.AK_ClienteConfigPrecio = CPP.FK09_CliClasificacion AND CliCP2.FK01_Cliente = @idCliente  \n");
				sql.append(" LEFT JOIN (SELECT * FROM Cliente_ConfiguracionPrecio) AS CliCP3 ON CliCP3.AK_ClienteConfigPrecio = CPP.FK05_CliFamilia AND CliCP3.FK01_Cliente = @idCliente  \n");
				sql.append(" \n LEFT JOIN (SELECT * FROM LAA_Concepto) AS LAAC ");
				sql.append(" \n		ON LAAC.PK_AAConcepto = CASE WHEN CliCP2.Fk06_LAAConcepto <> 0 THEN CliCP2.Fk06_LAAConcepto	WHEN  CliCP3.Fk06_LAAConcepto <> 0 THEN CliCP3.Fk06_LAAConcepto ELSE CF.FK03_LAAConcepto END");
			}else{
				sql.append(" \n LEFT JOIN (SELECT * FROM LAA_Concepto) AS LAAC ON LAAC.PK_AAConcepto = CF.FK03_LAAConcepto");
			}
			sql.append(" \n LEFT JOIN(SELECT PROD.idProducto,PROD.Proveedor, PROD.FK01_Configuracion_Precio, PROD.Costo,((((SUM(CPP.CostoNuevo - CPP.CostoAnTErior))/case when (COUNT(CPP.CostoNuevo)) = 0 then 1 else COUNT(CPP.CostoNuevo) end)*100 )/(CPP.CostoNuevo ");   
			sql.append(" \n          -((SUM(CPP.CostoNuevo - CPP.CostoAnTErior))/case when (COUNT(CPP.CostoNuevo)) = 0 then 1 else COUNT(CPP.CostoNuevo) end))) AS PORCIENTO   ");
			sql.append(" \n          FROM Productos AS PROD   ");
			sql.append(" \n          LEFT JOIN(SELECT MAX (PK_CambioPrecioProducto) ID, FK01_Producto FROM CambioPrecioProducto GROUP BY FK01_Producto) AS ID ON ID.FK01_Producto =PROD.idProducto ");  
			sql.append(" \n          LEFT JOIN(SELECT * FROM CambioPrecioProducto) AS CPP ON CPP.PK_CambioPrecioProducto = ID.ID    ");
			sql.append(" \n          GROUP BY CPP.CostoNuevo,PROD.Proveedor,PROD.FK01_Configuracion_Precio, PROD.Costo,PROD.idProducto) "); 
			sql.append("  \n         AS PORC ON PORC.idProducto = PROD.idProducto  ");
			sql.append(" \n LEFT JOIN (SELECT * FROM Licencia) AS LICE ON LICE.FK01_Proveedor = CP.FK01_Proveedor AND LICE.Tipo = PROD.DepositarioInternacional");
			sql.append(" \n LEFT JOIN (SELECT * FROM CostoFactor_Licencia) CFL ON CFL.FK01_CostoFactor = CF.PK_Costo_Factor AND CFL.Licencia = PROD.DepositarioInternacional" );
			if(idCliente > 0){
				sql.append("  \n WHERE CP.FK01_Proveedor=@idProveedor  AND PROD.idProducto = @idProducto AND ");
				sql.append("  \n  CASE WHEN CliCP2.Factor is Not null then CPP.FK09_CliClasificacion WHEN CliCP3.Factor is Not null then  CPP.FK05_CliFamilia   ");
				sql.append("  \n  WHEN CPP.FK08_ConfClasificacion is Not null then CPP.FK08_ConfClasificacion else cpp.FK02_ConfFamilia END  = @idConfigFamilia");
			}else{
				sql.append("  \n WHERE CP.FK01_Proveedor=@idProveedor  AND PROD.idProducto = @idProducto AND case when CPP.FK08_ConfClasificacion is Not null then CPP.FK08_ConfClasificacion else cpp.FK02_ConfFamilia end  = @idConfigFamilia AND Prod.Vigente=1");
			}
			//logger.info(sql.toString());
			return (FormulaPrecio) this.jdbcTemplate.queryForObject (sql.toString(),map, new FormulaPrecioRowMapper());

		}catch(Exception e){
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sql: " + sql, "idProveedor: " + idProveedor,"idProducto: " + idProducto,
					"stock_flete: " + stock_flete, "nivel: " + nivel, "idCliente: " + idCliente, "idConfig: ", idConfig);
			return new FormulaPrecio();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Empresa> findRelacionEmpresasProveedor(Long idProveedor)
			throws ProquifaNetException {
		StringBuilder sql = new StringBuilder("");

		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idProveedor", idProveedor);
		

			
			sql.append("\n SELECT DISTINCT *, COALESCE(EP.NumAsignado,'') Asignado, " + idProveedor + " Prov FROM Empresa E");
			sql.append("\n LEFT JOIN (SELECT * FROM Empresas_Proveedor WHERE FK01_Proveedor =").append(idProveedor).append(") EP ON EP.FK02_Empresa = E.PK_Empresa");
			sql.append("\n WHERE E.Vende = 1");

			////logger.info(sql.toString());
			return super.jdbcTemplate.query(sql.toString(),map,  new EmpresasProveedorRowMapper());
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sql: " + sql, "idProveedor: " + idProveedor);
			return new ArrayList<Empresa>();
		}
	}

	@Override
	public Boolean updateRelacionEmpresasProveedor(Empresa empresas) throws ProquifaNetException {
		StringBuilder sql = new StringBuilder();
		try{

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("empresas", empresas);
		
			Object[] params =  {empresas.getRelacionProveedor(), empresas.getNumAsigCliente(), empresas.getIdEmpresaProveedor()};

			sql.append(" UPDATE Empresas_Proveedor SET ");
			sql.append(" Habilitada=?,");
			sql.append(" NumAsignado=?");
			sql.append(" WHERE PK_Empresas_Proveedor=?");



			//////logger.info(sql.toString());

			super.jdbcTemplate.update(sql.toString(),map);


			return true;
		}catch(Exception e){
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sql: " + sql,empresas);
			return false;
		}
	}

	@Override
	public Boolean insertRelacionEmpresasProveedor(Empresa empresas)
			throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("empresas", empresas);
			
			Object[] params =  {empresas.getIdProveedor(), empresas.getIdEmpresa(), empresas.getRelacionProveedor(), empresas.getNumAsigCliente()};
			//////logger.info("INSERT INTO Empresas_Proveedor(FK01_Proveedor, FK02_Empresa, Habilitada, NumAsignado,FUA) VALUES (?,?,?,?,GETDATE())");
			super.jdbcTemplate.update("INSERT INTO Empresas_Proveedor(FK01_Proveedor, FK02_Empresa, Habilitada, NumAsignado,FUA) VALUES (?,?,?,?,GETDATE())", map);
			return true;
		}catch (Exception e){
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,empresas);
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Producto> getProductosPorClasificacion(Long idClasificacion)
			throws ProquifaNetException {
		String sql = "";
		try{
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idClasificacion", idClasificacion);
			sql = "SELECT idProducto, FK03_Categoria_PrecioLista, Costo FROM Productos WHERE FK04_Clasificacion_ConfiguracionPrecio =" + idClasificacion;
			//////logger.info(sql);
			return super.jdbcTemplate.query(sql,map,  new ProductosClasificacionRowMapper());
		}catch(Exception e){
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sql: " + sql,"idClasificacion: " + idClasificacion);
			return new ArrayList<Producto>();
		}
	}

	@Override
	public Boolean cleanConfiguracionPrecioCosto() throws ProquifaNetException {
		String sql = "";
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("null", null);
			sql = 	"\n DELETE Configuracion_Precio WHERE PK_Configuracion_Precio IN (" +
					"\n SELECT  cp.PK_Configuracion_Precio FROM Configuracion_Precio cp " +
					"\n LEFT JOIN (select  COUNT(FK01_Producto) total, FK03_ConfCosto from ConfiguracionPrecio_Producto group by FK03_ConfCosto) cpp ON cpp.FK03_ConfCosto = cp.PK_Configuracion_Precio " +
					"\n WHERE cp.Nivel = 'Costo' AND cpp.total IS NULL)";
			//////logger.info(sql);
			super.jdbcTemplate.update(sql, map);

			return true;
		}catch(Exception e){
			//logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sql: " + sql);
			return false;
		}
	}

	@Override
	public InformacionPagos getInformacionPagosProveedor(Long idProveedor)
			throws ProquifaNetException {
		String sql = "";
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idProveedor", idProveedor);
			sql = "SELECT Clave, LineaCredito, LimiteCredito, ObservaPago, CPago, FUA_Pagos  FROM Proveedores WHERE Clave = " + idProveedor;

			return (InformacionPagos) super.jdbcTemplate.queryForObject(sql, map, new InformacionPagosRowMapper());
		}catch(Exception e){
			//logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sql: " + sql,"idProveedor: " + idProveedor);
			return new InformacionPagos();
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<MedioPago> getMediosDePagoProveedor(Long idProveedor)
			throws ProquifaNetException {
		String sql = "";
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idProveedor", idProveedor);
			
			sql = "select * from CuentaProveedor where FK01_Proveedor = " + idProveedor;

			return super.jdbcTemplate.query(sql,map,  new MediosPagosProveedorRowMapper());
		}catch(Exception e){
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sql: " + sql,"idProveedor: " + idProveedor);
			return new ArrayList<MedioPago>();
		}
	}

	@Override
	public Boolean updateInformacionPagoProveeedor(InformacionPagos informacion)
			throws ProquifaNetException {
		StringBuilder sql = new StringBuilder();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("informacion", informacion);

			Object[] params =  {informacion.getComentarios(), informacion.getCondicionesPago(), informacion.getLimiteCredito(), informacion.getLineaCredito(), informacion.getIdProveedor()};

			sql.append(" UPDATE Proveedores SET ");
			sql.append(" ObservaPago = ?,");
			sql.append(" CPago = ?,");
			sql.append(" LimiteCredito = ?,");
			sql.append(" LineaCredito = ?,");
			sql.append(" FUA_Pagos = GETDATE()");
			sql.append(" WHERE Clave = ?");

			//////logger.info(sql.toString());

			super.jdbcTemplate.update(sql.toString(),map);

			return true;
		} catch (Exception e) {
		//	logger.error("Error: " + e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sql: " + sql,informacion);
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> getPendientesRespCobroPraPagador(String idProveedor) 
			throws ProquifaNetException {
		StringBuilder sql = new StringBuilder(" ");
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idProveedor", idProveedor);
			sql.append(" \n DECLARE @idProveedor int = "+idProveedor+" ");
			//---------  Factura por asociar  ---------
			sql.append(" \n SELECT pendiente.Folio ");
			sql.append(" \n FROM FacturaxPagar,Pendiente,Proveedores ");  
			sql.append(" \n WHERE Pendiente.Tipo='Factura por asociar' AND Pendiente.FFin IS NULL "); 
			sql.append(" \n AND Proveedores.Clave=Pendiente.Docto AND FacturaxPagar.idFxPagar=Pendiente.Partida ");  
			sql.append(" \n AND Proveedores.Clave = @idProveedor ");
			sql.append(" \n UNION ");
			//---------------------  RevisarFactura   ---------
			sql.append(" \n	SELECT Pendiente.Folio ");
			sql.append(" \n FROM Pendiente, FacturaxPagar , Proveedores   ");
			sql.append(" \n LEFT JOIN (SELECT * FROM empleados )AS emp ON emp.Clave =  Proveedores.pagador "); 
			sql.append(" \n WHERE Tipo='RevisarFactura' AND FFin is null  ");
			sql.append(" \n AND idFxPagar=Pendiente.Docto AND  proveedores.clave = FacturaxPagar.idProveedor "); 
			sql.append(" \n AND Proveedores.Clave = @idProveedor ");
			sql.append(" \n UNION   ");
			//---------------------  VerificarPago  y prepago  ------------
			sql.append(" \n SELECT Pnd.Folio ");
			sql.append(" \n FROM Pendiente AS pnd   ");
			sql.append(" \n LEFT JOIN(SELECT DISTINCT (Proveedores.Nombre) nombre, clave,FacturaxPagar.idFxPagar, Proveedores.Pagador , Proveedores.Clave prove FROM FacturaxPagar,Proveedores "); 
			sql.append(" \n WHERE Proveedores.Clave=FacturaxPagar.idProveedor  GROUP BY Clave, idFxPagar , Pagador, nombre ) AS fxp ON fxp.idFxPagar=pnd.Docto AND pnd.Partida='TC'  ");
			sql.append(" \n LEFT JOIN(SELECT DISTINCT ( Proveedores.Nombre) nombre , clave,Pago.PK_FolioPG, proveedores.pagador, Proveedores.Clave prove FROM Pago,FacturaxPagar,Proveedores WHERE "); 
			sql.append(" \n Proveedores.Clave=FacturaxPagar.idProveedor AND FacturaxPagar.FolioPG=Pago.FolioPG GROUP BY Clave, idFxPagar , Pagador , nombre , PK_FolioPG  )   ");
			sql.append(" \n AS pg ON pg.PK_FolioPG=pnd.Docto AND pnd.Partida='TI&DB'  ");
			sql.append(" \n LEFT JOIN(SELECT * FROM empleados )AS emp ON emp.Clave =  fxp.pagador or emp.Clave =  pg.Pagador "); 
			sql.append(" \n WHERE pnd.Tipo='VerificarPago' AND FFin IS NULL   ");
			sql.append(" \n AND ( fxp.prove = @idProveedor or pg.prove=@idProveedor  ) ");
			sql.append(" \n UNION  ");
			//---------------------  Elaborar cheque     ---------------------------------------
			sql.append(" \n SELECT pnd.Folio  ");
			sql.append(" \n FROM Pendiente pnd ,Pago, FacturaxPagar fxp , Proveedores ");  
			sql.append(" \n WHERE Pago.PK_FolioPG = pnd.Docto AND fxp.FolioPG = pago.FolioPG "); 
			sql.append(" \n AND Proveedores.Clave = fxp.idProveedor AND  pnd.Tipo='ElaborarCheque' AND pnd.FFin IS NULL "); 
			sql.append(" \n AND Proveedores.Clave=@idProveedor " );
			sql.append(" \n UNION  ");
			//---------------------  Controlar Deposito     ---------------------------------------
			sql.append(" \n SELECT pnd.Folio ");
			sql.append(" \n FROM Pendiente AS pnd ");
			sql.append(" \n LEFT JOIN(SELECT PK_Deposito,Etiqueta,FK01_Pago FROM Deposito) AS dp ON dp.PK_Deposito=pnd.Docto "); 
			sql.append(" \n LEFT JOIN(SELECT PK_FolioPG,FolioPG FROM Pago) AS pg ON pg.PK_FolioPG=dp.FK01_Pago ");
			sql.append(" \n LEFT JOIN(SELECT DISTINCT(idProveedor) AS idProveedor,FolioPG FROM FacturaxPagar) AS fxp ON fxp.FolioPG=pg.FolioPG ");
			sql.append(" \n LEFT JOIN(SELECT Clave,Nombre, Pagador  FROM Proveedores) AS prov ON prov.Clave=fxp.idProveedor ");
			sql.append(" \n LEFT JOIN (SELECT usuario , clave from Empleados )as emp on emp.Clave = prov.pagador ");
			sql.append(" \n WHERE pnd.Tipo='ControlarDeposito' AND pnd.FFin IS NULL "); 
			sql.append(" \n AND prov.Clave=@idProveedor  ");



			//////logger.info(sql.toString());
			return super.jdbcTemplate.queryForList(sql.toString(),map, String.class);

		}catch(Exception e){
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sql: " + sql,"idProveedor: " + idProveedor);
			return new ArrayList<String>();
		}
	}



	public Boolean actualizarResponsablePendienteXfolio(String _folio, String _responsable) 
			throws ProquifaNetException {
		String sql = "";
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("_responsable", _responsable);
			map.put("_folio", _folio);
			sql = 	" UPDATE Pendiente SET Responsable ='"+_responsable+"' WHERE Folio in ( "+_folio+" )";
			//////logger.info(sql);
			super.jdbcTemplate.update(sql, map);
			return true;

		}catch(Exception e){
			//(logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sql: " + sql,"_folio: " + _folio, "_responsable: " + _responsable);
			return false;
		}
	}

	@Override
	public Boolean updateMediosDePagoProveedor(MedioPago medio)
			throws ProquifaNetException {
		StringBuilder sql = new StringBuilder();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("medio", medio);

			Object[] params =  {medio.getBeneficiario(), medio.getNumCuenta(), medio.getSucursal(), medio.getClabe(), medio.getBanco(), medio.getIdMedioPago()};

			sql.append(" UPDATE CuentaProveedor SET ");
			sql.append(" Beneficiario = ?,");
			sql.append(" NoCuenta = ?,");
			sql.append(" Sucursal = ?,");
			sql.append(" Clabe = ?,");
			sql.append(" Banco = ?,");
			sql.append(" FUA = GETDATE()");
			sql.append(" WHERE PK_Cuenta = ?");


			//////logger.info(sql.toString());

			super.jdbcTemplate.update(sql.toString(),map);


			return true;
		} catch (Exception e) {
			//logger.error("Error: " + e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sql: " + sql,medio);
			return false;
		}
	}

	@Override
	public Boolean insertMediosDePagoProveedor(MedioPago medio)
			throws ProquifaNetException {
		StringBuilder sql = new StringBuilder();
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Banco", medio.getBanco());
			map.put("Beneficiario", medio.getBeneficiario());
			map.put("NumCuenta", medio.getNumCuenta());
			map.put("Sucursal", medio.getSucursal());
			map.put("Clabe", medio.getClabe());
			map.put("IdProveedor", medio.getIdProveedor());
			map.put("Tipo", medio.getTipo());
			Object[] params =  {medio.getBanco(),medio.getBeneficiario(),medio.getNumCuenta(),medio.getSucursal(),medio.getClabe(),medio.getIdProveedor(),medio.getTipo()};

			sql.append(" INSERT INTO CuentaProveedor  ");
			sql.append(" (Banco,Beneficiario,NoCuenta,Sucursal,Clabe,FK01_Proveedor,Tipo,FUA)");
			sql.append(" VALUES(?,?,?,?,?,?,?, GETDATE())");


			//////logger.info(sql.toString());

			super.jdbcTemplate.update(sql.toString(),map);


			return true;
		} catch (Exception e) {
		//	logger.error("Error: " + e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sql: " + sql,medio);
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> findListaConfiguracionesNivel(String nivel,
			Long idConfigFamilia) throws ProquifaNetException {
		String sql = "";
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idConfigFamilia", idConfigFamilia);
			map.put("nivel", nivel);
		
			sql = 
					" SELECT CP.PK_Configuracion_Precio " +
							" FROM Configuracion_Precio CP" +
							" LEFT JOIN (SELECT PK_Configuracion_Precio, Tipo, Subtipo, Control, FK01_Proveedor, Industria FROM Configuracion_Precio WHERE PK_Configuracion_Precio = " + idConfigFamilia + " ) F " +
							"		ON  (case when F.Tipo is null then 1 else F.Tipo end) = (case when CP.Tipo is null then 1 else CP.Tipo end) " +
							"		AND (case when F.Subtipo is null then 1 else F.Subtipo end) = (case when CP.Subtipo is null then 1 else CP.Subtipo end) " +
							" 		AND (case when F.Control is null then 1 else F.Control end)  = (case when CP.Control is null then 1 else CP.Control end) " +
							"		AND F.FK01_Proveedor = CP.FK01_Proveedor AND F.Industria = CP.Industria" +
							" WHERE F.PK_Configuracion_Precio = " + idConfigFamilia + " AND Nivel = '" + nivel +"'";
			//////logger.info(sql);
			return super.jdbcTemplate.queryForList(sql,map, Long.class);
		}catch(Exception e){
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sql: " + sql,"nivel: " + nivel, "idConfigFamilia: " + idConfigFamilia);
			return new ArrayList<Long>();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getPendientesRespComprador(Long proveedor)
			throws ProquifaNetException {
		StringBuilder sbQuery = new StringBuilder();
		try {

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("proveedor", proveedor);
			sbQuery = new StringBuilder("DECLARE @idProveedor int = ").append(proveedor).append(" \n");
			//--------OC por confirmar---------//
			sbQuery.append("SELECT P.Folio FROM Pendiente P \n");
			sbQuery.append("LEFT JOIN Compras C ON C.Clave = P.Docto \n");
			sbQuery.append("LEFT JOIN Proveedores PROV ON PROV.Clave = C.Provee \n");
			sbQuery.append("WHERE P.Tipo = 'OC por confirmar' AND P.FFin IS NULL \n");
			sbQuery.append("AND PROV.Clave = @idProveedor \n");
			sbQuery.append("UNION \n");
			//-------Monitorear OC----------//
			sbQuery.append("SELECT P.Folio FROM Pendiente P \n");
			sbQuery.append("LEFT JOIN Compras C ON C.Clave = P.Docto \n");
			sbQuery.append("LEFT JOIN Proveedores PROV ON PROV.Clave = C.Provee \n");
			sbQuery.append("WHERE P.Tipo = 'Monitorear OC' AND P.FFin IS NULL \n");
			sbQuery.append("AND PROV.Clave = @idProveedor \n");
			sbQuery.append("UNION \n");
			//--------Rechazo por Docto---------//
			sbQuery.append("SELECT P.Folio FROM Pendiente P \n");
			sbQuery.append("LEFT JOIN PCompras PC ON PC.idPCompra = P.Docto \n");
			sbQuery.append("LEFT JOIN Compras C ON C.Clave = PC.Compra \n");
			sbQuery.append("LEFT JOIN Proveedores PROV ON PROV.Clave = C.Provee \n");
			sbQuery.append("WHERE P.Tipo = 'Rechazo por documentacion' AND P.FFin IS NULL \n");
			sbQuery.append("AND PROV.Clave = @idProveedor \n");
			sbQuery.append("UNION \n");
			//--------Producto a reclamo---------//
			sbQuery.append("SELECT P.Folio FROM Pendiente P \n");
			sbQuery.append("LEFT JOIN PCompras PC ON PC.idPCompra = P.Docto \n");
			sbQuery.append("LEFT JOIN Compras C ON C.Clave = PC.Compra \n");
			sbQuery.append("LEFT JOIN Proveedores PROV ON PROV.Clave = C.Provee \n");
			sbQuery.append("WHERE P.Tipo = 'Producto a reclamo' AND P.FFin IS NULL \n");
			sbQuery.append("AND PROV.Clave = @idProveedor \n");
			sbQuery.append("UNION \n");
			//---------Reclamo abierto--------//
			sbQuery.append("SELECT P.Folio FROM Pendiente P \n");
			sbQuery.append("LEFT JOIN PCompras PC ON PC.idPCompra = P.Docto \n");
			sbQuery.append("LEFT JOIN Compras C ON C.Clave = PC.Compra \n");
			sbQuery.append("LEFT JOIN Proveedores PROV ON PROV.Clave = C.Provee \n");
			sbQuery.append("WHERE P.Tipo = 'Reclamo abierto' AND P.FFin IS NULL \n");
			sbQuery.append("AND PROV.Clave = @idProveedor \n");
			sbQuery.append("UNION \n");
			//--------Monitorear reposicion---------//
			sbQuery.append("SELECT P.Folio FROM Pendiente P \n");
			sbQuery.append("LEFT JOIN PCompras PC ON PC.idPCompra = P.Docto \n");
			sbQuery.append("LEFT JOIN Compras C ON C.Clave = PC.Compra \n");
			sbQuery.append("LEFT JOIN Proveedores PROV ON PROV.Clave = C.Provee \n");
			sbQuery.append("WHERE P.Tipo='Monitorear reposici������n' AND P.FFin IS NULL \n");
			sbQuery.append("AND PROV.Clave = @idProveedor \n");
			sbQuery.append("UNION \n");
			//--------Monitorear reclamo PHS---------//
			sbQuery.append("SELECT P.Folio FROM Pendiente P \n");
			sbQuery.append("LEFT JOIN PCompras PC ON PC.idPCompra = P.Docto \n");
			sbQuery.append("LEFT JOIN Compras C ON C.Clave = PC.Compra \n");
			sbQuery.append("LEFT JOIN Proveedores PROV ON PROV.Clave = C.Provee \n");
			sbQuery.append("WHERE P.Tipo = 'Monitorear reclamo' AND P.FFin IS NULL \n");
			sbQuery.append("AND PROV.Clave = @idProveedor \n");
			sbQuery.append("UNION \n");
			//--------Evaluar envio---------//
			sbQuery.append("SELECT P.Folio FROM Pendiente P \n");
			sbQuery.append("LEFT JOIN Pedidos PED ON PED.CPedido = P.Docto \n");
			sbQuery.append("LEFT JOIN (SELECT DISTINCT PP.CPedido, PP.Provee FROM PPedidos PP) PP ON PP.CPedido = PED.CPedido \n");
			sbQuery.append("LEFT JOIN Proveedores PROV ON PROV.Clave = PP.Provee \n");
			sbQuery.append("WHERE P.Tipo = 'Evaluar env������o' AND P.FFin IS NULL \n");
			sbQuery.append("AND PROV.Clave = @idProveedor \n");
			sbQuery.append("UNION \n");
			//-------Programar solicitud RP----------//
			sbQuery.append("SELECT P.Folio FROM Pendiente P \n");
			sbQuery.append("LEFT JOIN PCompras PC ON PC.Compra = P.Docto AND PC.Partida = P.Partida \n");
			sbQuery.append("LEFT JOIN Compras C ON C.Clave = PC.Compra \n");
			sbQuery.append("LEFT JOIN Proveedores PROV ON PROV.Clave = C.Provee \n");
			sbQuery.append("WHERE P.Tipo='Producto a recoleccion' AND P.FFin IS NULL \n");
			sbQuery.append("AND PROV.Clave = @idProveedor \n");
			sbQuery.append("UNION \n");
			//--------Cargar factura TI/DB---------//
			sbQuery.append("SELECT P.Folio FROM Pendiente P \n");
			sbQuery.append("LEFT JOIN ( SELECT C.Clave Compra, PROV.Clave, PROV.Nombre, COALESCE(C.MedioPago, PROV.Medio) MedioPago FROM Compras C \n");
			sbQuery.append("\tLEFT JOIN (SELECT PROV.Clave, PROV.Nombre ,CASE WHEN PROV.Cheque = 1 THEN 'Cheque' WHEN PROV.Transferencia = 1 THEN 'Transferencia' \n");
			sbQuery.append("\tWHEN PROV.Tarjeta = 1 THEN 'Tarjeta' WHEN PROV.Deposito = 1 THEN 'Deposito' END Medio FROM Proveedores PROV) PROV ON PROV.Clave = C.Provee ) PROV ON PROV.Compra = P.Docto \n");
			sbQuery.append("WHERE (P.Tipo = 'OC por pagar' OR P.Tipo = 'Prepagar OC' ) \n");
			sbQuery.append("AND P.FFin IS NULL AND PROV.MedioPago <> 'Tarjeta' \n");
			sbQuery.append("AND PROV.Clave = @idProveedor \n");
			sbQuery.append("UNION \n");
			//--------Cargar factura TC---------//
			sbQuery.append("SELECT P.Folio FROM Pendiente P \n");
			sbQuery.append("LEFT JOIN ( SELECT C.Clave Compra, PROV.Clave, PROV.Nombre, COALESCE(C.MedioPago, PROV.Medio) MedioPago FROM Compras C \n");
			sbQuery.append("\tLEFT JOIN (SELECT PROV.Clave, PROV.Nombre, CASE WHEN PROV.Cheque = 1 THEN 'Cheque' WHEN PROV.Transferencia = 1 THEN 'Transferencia' \n");
			sbQuery.append("\tWHEN PROV.Tarjeta = 1 THEN 'Tarjeta' WHEN PROV.Deposito = 1 THEN 'Deposito' END Medio FROM Proveedores PROV) PROV ON PROV.Clave = C.Provee ) PROV ON PROV.Compra = P.Docto \n");
			sbQuery.append("WHERE (P.Tipo = 'OC por pagar' OR P.Tipo = 'Prepagar OC' ) \n");
			sbQuery.append("AND P.FFin IS NULL AND PROV.MedioPago = 'Tarjeta' \n");
			sbQuery.append("AND PROV.Clave = @idProveedor \n");
			sbQuery.append("UNION \n");
			//--------Atender inconsistencias---------//
			sbQuery.append("SELECT P.Folio FROM Pendiente P \n");
			sbQuery.append("LEFT JOIN FacturaxPagar FxP ON FxP.idFxPagar = P.Docto \n");
			sbQuery.append("LEFT JOIN Proveedores PROV ON PROV.Clave = FxP.idProveedor \n");
			sbQuery.append("WHERE P.Tipo = 'AtenderInconsistencias' AND P.FFin IS NULL \n");
			sbQuery.append("AND PROV.Clave = @idProveedor \n");
			sbQuery.append("UNION \n");
			//--------Confirmar pago---------//
			sbQuery.append("SELECT P.Folio FROM Pendiente P \n");
			sbQuery.append("LEFT JOIN ( SELECT DISTINCT PG.PK_FolioPG, PROV.Clave, PROV.Nombre FROM Pago PG \n");
			sbQuery.append("\tLEFT JOIN FacturaxPagar FxP ON FxP.FolioPG = PG.FolioPG \n");
			sbQuery.append("\tLEFT JOIN Proveedores PROV ON PROV.Clave = FxP.idProveedor ) PG ON PG.PK_FolioPG = P.Docto AND P.Partida = 'TI&DB' \n");
			sbQuery.append("LEFT JOIN ( SELECT DISTINCT FxP.idFxPagar, PROV.Clave, PROV.Nombre FROM FacturaxPagar FxP  \n");
			sbQuery.append("\tLEFT JOIN Proveedores PROV ON PROV.Clave = FxP.idProveedor ) FxP ON FxP.idFxPagar = P.Docto AND P.Partida = 'TC' \n");
			sbQuery.append("WHERE P.Tipo = 'ConfirmarPago' AND P.FFin IS NULL  \n");
			sbQuery.append("AND (PG.Clave = @idProveedor OR FxP.Clave = @idProveedor ) \n");
			sbQuery.append("UNION \n");
			//--------Especificar trafico---------//
			sbQuery.append("SELECT P.Folio FROM Pendiente P \n");
			sbQuery.append("LEFT JOIN Compras C ON C.Clave = P.Docto \n");
			sbQuery.append("LEFT JOIN Proveedores PROV ON PROV.Clave = C.Provee \n");
			sbQuery.append("WHERE (P.Tipo = 'Especificar tr������fico' OR P.Tipo = 'Especificar tr������fico UE' ) \n");
			sbQuery.append("AND P.FFin IS NULL AND P.FFin IS NULL \n");
			sbQuery.append("UNION \n");
			//--------Atender alta producto---------//
			sbQuery.append("SELECT P.Folio FROM Pendiente P \n");
			sbQuery.append("LEFT JOIN PCotPharma PCP ON PCP.Folio = P.Docto \n");
			sbQuery.append("LEFT JOIN Proveedores PROV ON PROV.Clave = PCP.Proveedor \n");
			sbQuery.append("WHERE P.Tipo = 'Producto por Validar' AND P.FFin IS NULL \n");
			sbQuery.append("AND PROV.Clave = @idProveedor \n");
			sbQuery.append("UNION \n");
			//--------Evaluar resultados---------//
			sbQuery.append("SELECT P.Folio FROM Pendiente P \n");
			sbQuery.append("LEFT JOIN PCotPharma PCP ON PCP.Folio = P.Docto \n");
			sbQuery.append("LEFT JOIN Proveedores PROV ON PROV.Clave = PCP.Proveedor \n");
			sbQuery.append("WHERE P.Tipo = 'Producto por Confirmar Datos' AND P.FFin IS NULL \n");
			sbQuery.append("AND PROV.Clave = @idProveedor \n");
			sbQuery.append(" \n");

			return super.jdbcTemplate.queryForList(sbQuery.toString(),map, String.class);
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sbQuery: " + sbQuery,"proveedor: " + proveedor);
			return new ArrayList<String>();
		}
	}

	@Override
	@Transactional
	public boolean insertObjetivoCrecimientoProveedor(Proveedor proveedor) throws ProquifaNetException{
		String sQuery = "";
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("proveedor", proveedor);
			Funcion function = new Funcion();
			Double montoAnualAnterior = proveedor.getMontoAnualAnterior();
			Double objetivoTranscurrido =  proveedor.getObjetivoTranscurrido();

			int mes = 1;
			int mesRestantes = 12;

			for (int i = mes; i <= 12; i++){
				sQuery = " INSERT INTO ObjetivoCrecimiento (FK02_Proveedor, Fecha, Objetivo, ObjetivoMonto, ObjetivoFundamental, ObjetivoMontoFundamental,Mes, Anio) VALUES( \n ";
				sQuery += proveedor.getIdProveedor() + ", GETDATE(), ";
				sQuery += " ROUND("+ (Double.valueOf(proveedor.getObjectivoCrecimiento()) - objetivoTranscurrido) / mesRestantes + ",2), ";
				sQuery += " ROUND(" + ((montoAnualAnterior * (1 + (Double.valueOf(proveedor.getObjectivoCrecimiento()) / 100))) / mesRestantes ) + ",2), ";
				sQuery += " ROUND("+ (Double.valueOf(proveedor.getObjetivoCrecimientoFundamental()) - objetivoTranscurrido) / mesRestantes + ",2), ";
				sQuery += " ROUND(" + ((montoAnualAnterior * (1 + (Double.valueOf(proveedor.getObjetivoCrecimientoFundamental()) / 100))) / mesRestantes ) + ",2), ";
				sQuery += "'" + function.obtenerMesConLetra(i) + "', YEAR(GETDATE()) ) ";

				this.jdbcTemplate.update(sQuery, map);
				////logger.info(sQuery);
			}

			return true;
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sQuery: " + sQuery,"proveedor: " + proveedor);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			e.printStackTrace();
			return false;
		}
	}

	@Override
	@Transactional
	public boolean updateObjetivoCreciminetoProveedor(Proveedor proveedor) throws ProquifaNetException{
		String sQuery = "";
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("proveedor", proveedor);
			Funcion function = new Funcion();
			Double montoAnualAnterior = proveedor.getMontoAnualAnterior();
			Double objetivoTranscurrido =  proveedor.getObjetivoTranscurrido();
			Double dObjetivoMontoTranscurrido = proveedor.getObjetivoMontoTranscurrido();

			int mes = Calendar.getInstance().get(Calendar.MONTH) + 1;
			int mesRestantes = 12 - (mes) ;

			for (int i = mes+1; i <= 12; i++){		

				sQuery = "UPDATE ObjetivoCrecimiento SET Fecha = GETDATE(), ";
				sQuery += "\n Objetivo = ROUND(" + (Double.valueOf(proveedor.getObjectivoCrecimiento()) - objetivoTranscurrido) / (mesRestantes) + ",2), " ;
				sQuery += "\n ObjetivoMonto = ROUND(" + (((montoAnualAnterior * (1 + (Double.valueOf(proveedor.getObjectivoCrecimiento())))) - dObjetivoMontoTranscurrido) / (mesRestantes) ) + ",2) ";
				sQuery += "\n WHERE FK02_Proveedor = " +  proveedor.getIdProveedor() + " AND Mes = '" + function.obtenerMesConLetra(i) + "' AND Anio = YEAR(GETDATE()) ";

				this.jdbcTemplate.update(sQuery, map);
				////logger.info(sQuery);
			}

			return true;
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sQuery: " + sQuery,"proveedor: " + proveedor);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			e.printStackTrace();
			return false;
		}
	}

	@Override
	@Transactional
	public boolean updateObjetivoCrecimientoFundamentalProveedor(Proveedor proveedor) throws ProquifaNetException{
		String sQuery = "";
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("proveedor", proveedor);
			Funcion function = new Funcion();
			Double montoAnualAnterior = proveedor.getMontoAnualAnterior();
			Double objetivoTranscurrido =  proveedor.getObjetivoTranscurrido();
			Double dObjetivoMontoTranscurrido = proveedor.getObjetivoMontoTranscurrido();
			
			int mes = Calendar.getInstance().get(Calendar.MONTH) + 1;
			int mesRestantes = 12 - (mes) ;
			
			for (int i = mes+1; i <= 12; i++){		
				
				sQuery = "UPDATE ObjetivoCrecimiento SET Fecha = GETDATE(), ";
				sQuery += "\n ObjetivoFundamental = ROUND(" + (Double.valueOf(proveedor.getObjetivoCrecimientoFundamental()) - objetivoTranscurrido) / (mesRestantes) + ",2), " ;
				sQuery += "\n ObjetivoMontoFundamental = ROUND(" + (((montoAnualAnterior * (1 + (Double.valueOf(proveedor.getObjetivoCrecimientoFundamental())))) - dObjetivoMontoTranscurrido) / (mesRestantes) ) + ",2) ";
				sQuery += "\n WHERE FK02_Proveedor = " +  proveedor.getIdProveedor() + " AND Mes = '" + function.obtenerMesConLetra(i) + "' AND Anio = YEAR(GETDATE()) ";
				
				this.jdbcTemplate.update(sQuery, map);
				//logger.info(sQuery);
			}
			
			return true;
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sQuery: " + sQuery,"proveedor: " + proveedor);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			e.printStackTrace();
			return false;
		}
	}
	


	public Double comprasAnualAnteriorxProveedor(Long idProveedor) throws ProquifaNetException{
		String sQuery = "";
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idProveedor", idProveedor);
			sQuery = "SELECT COALESCE(SUM( ";
			sQuery +="\n         CASE WHEN C.Moneda = 'Dolares' OR C.Moneda ='USD' THEN (PC.Costo * PC.Cant) WHEN C.Moneda = 'EUR' OR C.Moneda = 'Euros' THEN (PC.Costo * PC.Cant)* M.EDolar ";
			sQuery += "\n         WHEN C.Moneda = 'Pesos' OR C.Moneda = 'M.N.' THEN (PC.Costo * PC.Cant)/M.PDolar WHEN C.Moneda = 'Libras' THEN (PC.Costo * PC.Cant) * M.LDolar WHEN C.Moneda = 'DlCan' THEN (PC.Costo * PC.Cant)/(1/M.DDolar) END ";
			sQuery += "\n ), 0) AS ComprasUSD ";
			sQuery += "\n FROM Compras C ";
			sQuery += "\n INNER JOIN (SELECT * FROM PCompras WHERE Estado <> 'Cancelada') PC ON PC.Compra = C.Clave ";
			sQuery += "\n LEFT JOIN Monedas M ON M.Fecha = CONVERT(VARCHAR,C.Fecha,112) ";
			sQuery += "\n WHERE C.Provee = " + idProveedor + " AND C.Estado <> 'Cancelada' AND YEAR(C.Fecha) = YEAR(GETDATE()) - 1 ";

			////logger.info(sQuery);
			return (Double) this.jdbcTemplate.queryForObject(sQuery,map, Double.class);			

		} catch (Exception e) {
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sQuery: " + sQuery,"idProveedor: " + idProveedor);
			return -1D;
		}
	}

	public Double obtenerObjetivoTranscurrido(Long idProveedor) throws ProquifaNetException{
		String sQuery = "";
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idProveedor", idProveedor);
			String in = " ";
			Funcion function = new Funcion();
			int mes = Calendar.getInstance().get(Calendar.MONTH) + 1;

			for (int i = mes; i> 0; i--){
				in += "'" + function.obtenerMesConLetra(i) + "',";
			}			
			in = in.substring(0, in.length() - 1); //Se le quita la ultima coma

			sQuery = "SELECT COALESCE( ROUND(SUM(Objetivo), 2),0) Objetivo FROM ObjetivoCrecimiento ";
			sQuery += "\n WHERE FK02_Proveedor = " + idProveedor + " AND Anio = YEAR(GETDATE()) AND Mes IN(" + in + ") ";			

			////logger.info(sQuery);
			return (Double) this.jdbcTemplate.queryForObject(sQuery,map,  Double.class);			

		} catch (Exception e) {
			//logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sQuery: " + sQuery,"idProveedor: " + idProveedor);
			return -1D;
		}		
	}
	public Double obtenerObjetivoFundamentalTranscurrido(Long idProveedor) throws ProquifaNetException{
		String sQuery = "";
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idProveedor", idProveedor);
			String in = " ";
			Funcion function = new Funcion();
			int mes = Calendar.getInstance().get(Calendar.MONTH) + 1;
			
			for (int i = mes; i> 0; i--){
				in += "'" + function.obtenerMesConLetra(i) + "',";
			}			
			in = in.substring(0, in.length() - 1); //Se le quita la ultima coma
			
			sQuery = "SELECT COALESCE( ROUND(SUM(Objetivo), 2),0) ObjetivoFundamental FROM ObjetivoCrecimiento ";
			sQuery += "\n WHERE FK02_Proveedor = " + idProveedor + " AND Anio = YEAR(GETDATE()) AND Mes IN(" + in + ") ";			
			
			////logger.info(sQuery);
			return (Double) this.jdbcTemplate.queryForObject(sQuery,map,  Double.class);			
			
		} catch (Exception e) {
			//logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sQuery: " + sQuery,"idProveedor: " + idProveedor);
			return -1D;
		}		
	}

	public Double obtenerObjetivoMontoTranscurrido(Long idProveedor) throws ProquifaNetException{
		String sQuery = "";
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idProveedor", idProveedor);
			String in = " ";
			Funcion function = new Funcion();
			int mes = Calendar.getInstance().get(Calendar.MONTH) + 1;

			for (int i = mes; i> 0; i--){
				in += "'" + function.obtenerMesConLetra(i) + "',";
			}			
			in = in.substring(0, in.length() - 1); //Se le quita la ultima coma

			sQuery = "SELECT COALESCE( ROUND(SUM(ObjetivoMonto), 2),0) ObjetivoMonto FROM ObjetivoCrecimiento ";
			sQuery += "\n WHERE FK02_Proveedor = " + idProveedor + " AND Anio = YEAR(GETDATE()) AND Mes IN(" + in + ") ";			

			//////logger.info(sQuery);
			return (Double) this.jdbcTemplate.queryForObject(sQuery,map,  Double.class);			

		} catch (Exception e) {
			//logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sQuery: " + sQuery,"idProveedor: " + idProveedor);
			return -1D;
		}		
	}
	public Double obtenerObjetivoMontoFundamentalTranscurrido(Long idProveedor) throws ProquifaNetException{
		String sQuery = "";
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idProveedor", idProveedor);
			String in = " ";
			Funcion function = new Funcion();
			int mes = Calendar.getInstance().get(Calendar.MONTH) + 1;
			
			for (int i = mes; i> 0; i--){
				in += "'" + function.obtenerMesConLetra(i) + "',";
			}			
			in = in.substring(0, in.length() - 1); //Se le quita la ultima coma
			
			sQuery = "SELECT COALESCE( ROUND(SUM(ObjetivoMontoFundamental), 2),0) ObjetivoMontoFundamental FROM ObjetivoCrecimiento ";
			sQuery += "\n WHERE FK02_Proveedor = " + idProveedor + " AND Anio = YEAR(GETDATE()) AND Mes IN(" + in + ") ";			
			
			//////logger.info(sQuery);
			return (Double) this.jdbcTemplate.queryForObject(sQuery,map, Double.class);			
			
		} catch (Exception e) {
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sQuery: " + sQuery,"idProveedor: " + idProveedor);
			return -1D;
		}		
	}

	@SuppressWarnings("rawtypes")
	public boolean existeObjetivoAnioActual(Long idProveedor) throws ProquifaNetException{
		String sObjetivoExiste = "";
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idProveedor", idProveedor);
			//Busca si existe Objetivo de Crecimiento del Anio Actual
			sObjetivoExiste = "SELECT * FROM ObjetivoCrecimiento WHERE FK02_Proveedor = " + idProveedor +  " AND Anio=YEAR(GETDATE()) ";
			//////logger.info(sObjetivoExiste);

			List existe = this.jdbcTemplate.queryForList(sObjetivoExiste.toString(), map);

			if (existe.isEmpty()){
				return false; //Si esta vacio indica que NO existe
			}else{
				return true; //Si trae datos indica que SI existe
			}				

		} catch (Exception e) {
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sQuery: " + sObjetivoExiste,"idProveedor: " + idProveedor);
			return false;
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ValorAdicional> findValorAdicional(String parametro)
			throws ProquifaNetException {
		StringBuilder sbQuery = new StringBuilder() ;
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("parametro", parametro);
			if (parametro.equals( "TipoProducto")) {
				//--------TipoProductos---------//
				sbQuery.append(" select proveedores.Nombre , clave idProveedor ,  Tipo Valor, null ValorSecundario , 'Tipo Producto' Tipo   from Proveedores  \n");
				sbQuery.append(" inner join (select distinct Tipo, Proveedor   from productos  where Vigente =  1  group by Proveedor , Tipo ) as prod on prod.Proveedor = Proveedores.Clave  order by proveedores.nombre \n");
			}else if (parametro.equals("AgenteAduanal")) {
				//--------Agente Aduanal---------//
				sbQuery.append(" select proveedores.Nombre , Proveedores.Clave idProveedor,NombreComercial Valor , NombreLegal ValorSecundario, 'Agente Aduanal' Tipo  from Proveedores  \n");
				sbQuery.append(" inner join (select PK_Configuracion_Precio, FK01_Proveedor   from Configuracion_Precio)as cp on cp.FK01_Proveedor = proveedores.Clave  \n");
				sbQuery.append(" inner join (select PK_AAConfiguracionPrecio , FK01_AgenteAduanal, FK02_ConfiguracionPrecio   from AA_ConfiguracionPrecio)as aa on aa.FK02_ConfiguracionPrecio = cp.PK_Configuracion_Precio  \n");
				sbQuery.append(" inner join (select NombreComercial , NombreLegal , PK_AgenteAduanal  from AgenteAduanal) as agn on agn.PK_AgenteAduanal = aa.FK01_AgenteAduanal order by proveedores.nombre \n");
			}
			//////logger.info(sbQuery.toString());
			
			return (List<ValorAdicional>) super.jdbcTemplate.queryForObject(sbQuery.toString(),map, new ValorAdicionalRowMapper());
		}catch(Exception e){
			//logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sQuery: " + sbQuery,"parametro: " + parametro);
			return new ArrayList<ValorAdicional>();
		}
	}

	@Override
	public long getIdFabricantexNombreFabricante(String Nombre)
			throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Nombre", Nombre);
			Integer Numexiste = super.queryForInt("SELECT COUNT(*) AS Total FROM fabricantes WHERE Nombre = '"+ Nombre.trim() +"' ", map);
			if(Numexiste!=0){
				Long numFabricante =  super.queryForLong("SELECT idfabricante  FROM fabricantes WHERE Nombre = '"+ Nombre.trim() +"'", map);

				if (numFabricante ==0L){
					return 0L;
				}else {
					return numFabricante;
				}
			}else{
				return 0L;
			}


		}catch(Exception e){
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"Nombre: " + Nombre);
			return -1L;
		}


	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getPendientesInspector(Long proveedor) throws ProquifaNetException {
		String sQuery = "";
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("proveedor", proveedor);
			
			sQuery = "\n DECLARE @idProveedor int = " + proveedor + " " ;
			// OC A INSPECCIONAR PROGRAMADO
			sQuery += "\n SELECT Pendiente.Folio ";
			sQuery += "\n FROM Pendiente, compras  ";
			sQuery += "\n WHERE tipo='OC a inspeccionar' AND compras.clave = pendiente.docto AND FFin IS NULL ";
			sQuery += "\n and exists ( select * from PCompras  as pc where Compra = pendiente.Docto and  (pc.Estado = 'En inspección' OR pc.Estado = 'A stock') ";
			sQuery += "\n and exists (select Tipo , * from PPedidos as pr where  pr.CPedido = pc.cpedido and pr.part = pc.ppedido and pr.tipo = 'Programado')) ";
			sQuery += "\n AND Compras.Provee = @idProveedor ";
			// OC A INSPECCIONAR REGULAR
			sQuery += "\n UNION ";
			sQuery += "\n SELECT Pendiente.Folio ";
			sQuery += "\n FROM Pendiente, compras  ";
			sQuery += "\n WHERE tipo='OC a inspeccionar' AND compras.clave = pendiente.docto AND FFin IS NULL ";
			sQuery += "\n and exists ( select * from PCompras  as pc where Compra = pendiente.Docto and  (pc.Estado = 'En inspección' OR pc.Estado = 'A stock')  ";
			sQuery += "\n and exists (select Tipo , * from PPedidos as pr where  pr.CPedido = pc.cpedido and pr.part = pc.ppedido and ( pr.tipo = 'Regular' or pr.tipo is  null )))  ";
			sQuery += "\n AND Compras.Provee = @idProveedor ";
			// OC A INSPECCIONAR FLETE EXPRESS
			sQuery += "\n UNION ";
			sQuery += "\n SELECT Pendiente.Folio ";
			sQuery += "\n FROM Pendiente, compras WHERE tipo='OC a inspeccionar' AND compras.clave = pendiente.docto AND FFin IS NULL ";
			sQuery += "\n and exists ( select * from PCompras  as pc where Compra = pendiente.Docto and  (pc.Estado = 'En inspección' OR pc.Estado = 'A stock') ";
			sQuery += "\n and exists (select Tipo , * from PPedidos as pr where  pr.CPedido = pc.cpedido and pr.part = pc.ppedido and pr.tipo = 'Flete Express')) ";
			sQuery += "\n AND Compras.Provee = @idProveedor ";
			// PRODUCTO A DESTRUCCION
			sQuery += "\n UNION ";
			sQuery += "\n SELECT Pendiente.Folio ";
			sQuery += "\n FROM Pendiente, Compras, PCompras ";
			sQuery += "\n WHERE Pendiente.Tipo='Producto a destrucción' AND Pendiente.FFin IS NULL AND Pendiente.Docto=PCompras.idPCompra AND PCompras.Compra=Compras.Clave ";
			sQuery += "\n AND Compras.Provee = @idProveedor ";				

			//////logger.info(sQuery);
			return super.jdbcTemplate.queryForList(sQuery,map, String.class);

		}catch (Exception e) {
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sQuery: " + sQuery, "proveedor: " + proveedor);
			return new ArrayList<String>();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ClasificacionConfiguracionPrecio> findConceptoClasifConfigPrecioXidProveedor(
			Long idProoveedor, Long idCliente) throws ProquifaNetException {
		StringBuilder sql = new StringBuilder(" ");
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCliente", idCliente);
			map.put("idProoveedor", idProoveedor);
			
			sql.append("\n SELECT CPC.FK01_ConfigFamilia, COALESCE( CPC.FK02_ConfigClasificacion, CPC.FK01_ConfigFamilia ) CONFIG, ");
			sql.append("\n CPC.PK_ConfiguracionPrecioClasificacion CLASIFICACION, Concepto, COALESCE(T,0) TPRODUCTOS, CASE WHEN Factor IS NULL THEN 0 ELSE 1 END RES_CLIE,");
			sql.append("\n CASE WHEN CCP.FactorTemp IS NOT NULL THEN 1 ELSE 0 END temporal, VCT.TIPO TipoFamilia, VCST.SUBTIPO Subtipo, CONTR Control  ");
			sql.append("\n FROM ConfiguracionPrecio_Clasificacion CPC");
			sql.append("\n LEFT JOIN (SELECT COUNT(idProducto) AS T, FK04_Clasificacion_ConfiguracionPrecio,MAX(idProducto) idProducto  FROM Productos WHERE Vigente = 1  GROUP BY FK04_Clasificacion_ConfiguracionPrecio )AS P ");
			sql.append("\n 		ON P.FK04_Clasificacion_ConfiguracionPrecio = CPC.PK_ConfiguracionPrecioClasificacion");
			sql.append("\n LEFT JOIN (SELECT FK01_Producto, FK09_CliClasificacion, FK02_ConfFamilia FROM ConfiguracionPrecio_Producto) CPP ON CPP.FK01_Producto = P.idProducto and cpp.FK02_ConfFamilia = cpc.FK01_ConfigFamilia");
			sql.append("\n LEFT JOIN (SELECT * FROM Cliente_ConfiguracionPrecio  ) CCP ON AK_ClienteConfigPrecio = CPP.FK09_CliClasificacion AND CCP.FK01_Cliente =" + idCliente);
			sql.append("\n LEFT JOIN (SELECT * FROM Configuracion_Precio ) CP ON CP.PK_Configuracion_Precio = CPC.FK01_ConfigFamilia ");
			sql.append("\n LEFT JOIN(SELECT PK_Folio, Valor AS TIPO FROM ValorCombo WHERE Concepto='TipoProducto') AS VCT ON VCT.PK_Folio=  CP.Tipo ");
			sql.append("\n LEFT JOIN(SELECT PK_Folio, Valor AS SUBTIPO FROM ValorCombo WHERE Concepto='SubTipoProducto') AS VCST ON VCST.PK_Folio=CP.Subtipo ");    
			sql.append("\n LEFT JOIN(SELECT PK_Folio, Valor AS CONTR FROM ValorCombo WHERE Concepto='Control') AS VCCTRL ON VCCTRL.PK_Folio=CP.Control ");     
			sql.append("\n WHERE CP.FK01_Proveedor =  ").append(idProoveedor);

			//logger.info(sql.toString());
			return super.jdbcTemplate.query(sql.toString(),map, new ConfigPrecioClasificacionRowMapper());

		}catch(Exception e){
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sbQuery: " + sql, "idConfigFam: " + idProoveedor,"idCliente: " + idCliente);
			return new ArrayList<ClasificacionConfiguracionPrecio>();
		}

	}
	

	@Override
	public Long insertarCampanaComercial(Campana cam) throws ProquifaNetException {
		StringBuilder sql = new StringBuilder("");
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cam", cam);
			Object[] args={cam.getFechaInicio(),cam.getFechaFin(),cam.getNombre(),cam.getTipo(),cam.getComision(),cam.getObjetivo(),cam.getIdProvee(),cam.getTipo_Comision(),cam.getFuaCampana()};
			
			sql.append(" INSERT INTO Campana_Comercial(FechaInicio,FechaFin,Nombre,Tipo,Comision, Objetivo,FK01_idProveedor, Tipo_Comision,FUA) ");
			sql.append(" VALUES(?,?,?,?,?,?,?,?,?) ");
			
			super.jdbcTemplate.update(sql.toString(), map);
			return super.queryForLong("SELECT IDENT_CURRENT ('Campana_Comercial')", map);

		}catch(Exception e){
		//	logger.error("Error: " + e.getMessage());
			e.printStackTrace();
			throw new ProquifaNetException();
			
		}
	}

	
	@Override
	public boolean actualizaCampanaComercial(Campana cam) throws ProquifaNetException {
	try{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cam", cam);
			Object[] args={cam.getFechaInicio(),cam.getFechaFin(),cam.getNombre(),cam.getTipo(),cam.getComision(),cam.getObjetivo(), cam.getTipo_Comision(), cam.getId_Camapana()};
			
			StringBuilder sbQuery = new StringBuilder("UPDATE Campana_Comercial set FechaInicio = ?, FechaFin = ?, Nombre = ?, Tipo = ?, Comision = ?, Objetivo = ?, Tipo_Comision = ?, FUA = GETDATE() where PK_Campana = ? ");
			jdbcTemplate.update(sbQuery.toString(), map);
			return true;
		}catch(Exception e){
			//xlogger.error("Error: " + e.getMessage());
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	

	@Override
	public boolean insertarListaCampana(Campana cam, int size) throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cam", cam);
			map.put("size", size);
			
	
			for (int i = 0; i < size; i++) {
				StringBuilder sql = new StringBuilder("");
				Object[] args={cam.getId_Camapana(),cam.getIdProvee(),cam.getFamilias() == null ? null : cam.getFamilias().get(i).getIdConfiguracionPrecio(),cam.getClasificaciones() == null ? null : cam.getClasificaciones().get(i).getIdClasificacion(), cam.getProductos() == null ? null : cam.getProductos().get(i).getIdProducto() };
				sql.append(" INSERT INTO Campana_Configuracion(FK01_Campana,FK02_Proveedor,FK03_Familia,FK04_Clasificacion,FK05_Producto) ");
				sql.append(" VALUES(?,?,?,?,?) ");
				super.jdbcTemplate.update(sql.toString(), map);
			}
			
			return true;

		}catch(Exception e){
			//logger.error("Error: " + e.getMessage());
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	@Override
	public boolean eliminarConfiguracionCampana(Long idCampana) throws ProquifaNetException {
		try{
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCampana", idCampana);
			

			 log.info("idCampaña:" + idCampana );
			super.jdbcTemplate.update(" DELETE Campana_Configuracion WHERE FK01_Campana="+idCampana+"", map);
			return true;
		}catch(Exception e){
		//	logger.error("Error: " + e.getMessage());
//			funcion = new Funcion();
//			funcion.enviarCorreoAvisoExepcion(e,"idCostoFactor: " + idCampana);
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	@Override
	public boolean eliminarCampanaComercalProveedor(Long idCampana) throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCampana", idCampana);

			
			super.jdbcTemplate.update(" DELETE Campana_Comercial WHERE PK_Campana="+idCampana+"", map);
			return true;
		}catch(Exception e){
			//logger.error("Error: " + e.getMessage());
//			funcion = new Funcion();
//			funcion.enviarCorreoAvisoExepcion(e,"idCostoFactor: " + idCampana);
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	

	
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	 public List<Campana> obtenerCampanasDeProveedor(Long idProveedor ) throws ProquifaNetException {
		StringBuilder sbQuery = new StringBuilder(" ");
	
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idProveedor", idProveedor);
		   sbQuery.append(" SELECT * FROM Campana_Comercial CAMPCO ");
		   sbQuery.append(" \nINNER JOIN ( " );
		   sbQuery.append(" \n SELECT MAX(FUA) UltimaActualizacion, FK01_idProveedor  FROM Campana_Comercial  GROUP BY FK01_idProveedor ) FUA ON FUA.FK01_idProveedor = CAMPCO.FK01_idProveedor " );
		    if (idProveedor > 0)
		   sbQuery.append(" \n WHERE CAMPCO.FK01_idProveedor = ").append(idProveedor);
		    else 
		    	 sbQuery.append(" \n WHERE CAMPCO.FechaFin >= GETDATE() ");
		   
		   //logger.info(sbQuery.toString());
		   
		   return super.jdbcTemplate.query(sbQuery.toString(),map,  new CampanaComercialRowMapper());
		}catch(Exception e){
			//logger.error("Error: " + e.getMessage());
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	 public List<Campana> obtenerTodasLasCampanasComerciales() throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idProveedor", null);
			
			String sQuery = "";
			sQuery = " select CASE WHEN FK02_Proveedor IS NOT NULL THEN FK02_Proveedor WHEN FK03_Familia IS not null THEN FK03_Familia "+
					" \n WHEN FK04_Clasificacion is not null THEN FK04_Clasificacion WHEN FK05_Producto is not null " +
			        " \n THEN FK05_Producto END PK_Campana,camp.Tipo  from Campana_Comercial  as camp "+
					" \n LEFT JOIN Campana_Configuracion CampConf on CampConf.FK01_Campana = camp.PK_Campana";
		    log.info(sQuery);
		    
			return super.jdbcTemplate.query(sQuery, map, new CampanaComercialRowMapper());
		}catch(Exception e){
			//logger.error("Error: " + e.getMessage());
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	 public String obtenerProductosConCampana(Long idCampana) throws ProquifaNetException {
		
		StringBuilder sbQuery = new StringBuilder(" ");
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCampana", idCampana);
			  sbQuery.append(" SELECT Producto = STUFF((" );
			  sbQuery.append(" \n SELECT ',' + CAST(P.idProducto as varchar(max)) FROM (");
			  sbQuery.append(" \n SELECT PROD.idProducto" );
			  sbQuery.append(" \n FROM (");
			  sbQuery.append(" \n SELECT CASE WHEN FK02_Proveedor IS NOT NULL THEN FK02_Proveedor WHEN FK03_Familia IS not null THEN FK03_Familia " );
			  sbQuery.append(" \n WHEN FK04_Clasificacion is not null THEN FK04_Clasificacion WHEN FK05_Producto is not null " );
			  sbQuery.append(" \n THEN FK05_Producto END idConfig,camp.Tipo  from Campana_Comercial  as camp " );
			  sbQuery.append(" \n LEFT JOIN Campana_Configuracion CampConf on CampConf.FK01_Campana = camp.PK_Campana " );
			  sbQuery.append(" \n WHERE camp.FechaFin >=GETDATE() " );
			  if(idCampana > 0)
				  sbQuery.append(" and camp.PK_Campana =").append(idCampana);
			  sbQuery.append(" \n GROUP BY FK02_Proveedor,FK03_Familia ,FK03_Familia,FK04_Clasificacion, FK05_Producto,Tipo " );
		      sbQuery.append(" \n )camp" );
			  sbQuery.append(" \n LEFT JOIN Productos PROD ON CASE WHEN camp.Tipo = 'Proveedor' THEN PROD.Proveedor  " );
			  sbQuery.append(" \n WHEN camp.Tipo = 'Clasificación' THEN PROD.FK04_Clasificacion_ConfiguracionPrecio " );
			  sbQuery.append(" \n WHEN camp.Tipo = 'Producto' THEN PROD.idProducto " );
			  sbQuery.append(" \n ELSE 0 END =  camp.idConfig " ); 
			  sbQuery.append(" \n WHERE PROD.idProducto is not null and PROD.Vigente = 1 " );
			  sbQuery.append(" \n GROUP BY PROD.idProducto " );
			  sbQuery.append(" \n UNION " );
			  sbQuery.append(" \n SELECT PROD.idProducto " );
			  sbQuery.append(" \n FROM ( " );
			  sbQuery.append(" \n SELECT CASE WHEN FK02_Proveedor IS NOT NULL THEN FK02_Proveedor WHEN FK03_Familia IS not null THEN FK03_Familia " ); 
		      sbQuery.append(" \n WHEN FK04_Clasificacion is not null THEN FK04_Clasificacion WHEN FK05_Producto is not null " );
			  sbQuery.append(" \n THEN FK05_Producto END idConfig,camp.Tipo  from Campana_Comercial  as camp " );
			  sbQuery.append(" \n LEFT JOIN Campana_Configuracion CampConf on CampConf.FK01_Campana = camp.PK_Campana " );
			  sbQuery.append(" \n WHERE camp.Tipo = 'Familia' and camp.FechaFin >=GETDATE() " );
			  if(idCampana > 0)
				  sbQuery.append(" \n and camp.PK_Campana = ").append(idCampana);
			  sbQuery.append(" \n GROUP BY FK02_Proveedor,FK03_Familia ,FK03_Familia,FK04_Clasificacion, FK05_Producto,Tipo " );
			  sbQuery.append(" \n )camp " );
			  sbQuery.append(" \n LEFT JOIN ConfiguracionPrecio_Producto CCP ON CCP.FK02_ConfFamilia = camp.idConfig " );
			  sbQuery.append(" \n LEFT JOIN Productos PROD ON PROD.idProducto = CCP.FK01_Producto " );
			  sbQuery.append(" \n WHERE PROD.idProducto is not null and PROD.Vigente = 1 and PROD.Caduca >= GETDATE()" );
			  sbQuery.append(" \n GROUP BY PROD.idProducto ) P " );
			  sbQuery.append(" \n FOR XML PATH('') ), 1, 1, '') ");
			  
			  log.info(sbQuery.toString());
			String lista = (String) super.jdbcTemplate.queryForObject(sbQuery.toString(),map,  String.class);
			
			return  lista;
		}catch(Exception e){
		//	logger.error("Error: " + e.getMessage());
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ConfiguracionPrecio> obtenerInformacionDeCampanaComercialTipoFamilia(
			Long idCampana) throws ProquifaNetException {
		StringBuilder sbQuery = new StringBuilder(" ");
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCampana", idCampana);
			sbQuery.append(" \n SELECT CAM.PK_Campana, CONF.PK_Configuracion_Precio,VCT.Tipo, VCST.Subtipo, CONTR,INDUSTIAL, CPP.SUMA from campana_comercial as CAM ");
			sbQuery.append(" \n LEFT JOIN Campana_Configuracion as CCON on CCON.FK01_Campana = CAM.PK_Campana");
			sbQuery.append(" \n LEFT JOIN Configuracion_Precio as CONF on CONF.PK_Configuracion_Precio = CCON.FK03_Familia");
			sbQuery.append(" \n LEFT JOIN(SELECT PK_Folio, Valor AS TIPO FROM ValorCombo WHERE Concepto='TipoProducto') AS VCT ON VCT.PK_Folio=CONF.Tipo ");
			sbQuery.append(" \n LEFT JOIN(SELECT PK_Folio, Valor AS SUBTIPO FROM ValorCombo WHERE Concepto='SubTipoProducto') AS VCST ON VCST.PK_Folio=CONF.Subtipo    "); 
			sbQuery.append(" \n LEFT JOIN(SELECT PK_Folio, Valor AS CONTR FROM ValorCombo WHERE Concepto='Control') AS VCCTRL ON VCCTRL.PK_Folio=CONF.Control     ");
			sbQuery.append(" \n LEFT JOIN(SELECT PK_Folio, Valor AS INDUSTIAL FROM ValorCombo WHERE Concepto='Industrial') AS IND ON IND.PK_Folio = CONF.Industria    ");
			sbQuery.append(" \n LEFT JOIN (SELECT FK02_ConfFamilia, COUNT(FK02_ConfFamilia) AS SUMA, MAX(p.Caduca) Caduca FROM ConfiguracionPrecio_Producto PPC  ");
			sbQuery.append(" \n LEFT JOIN (SELECT idProducto, Caduca FROM Productos WHERE Vigente = 1) p on p.idProducto = PPC.FK01_Producto GROUP BY FK02_ConfFamilia) AS CPP ON CPP.FK02_ConfFamilia = CONF.PK_Configuracion_Precio  ");  
			sbQuery.append(" \n where CAM.PK_Campana = ").append(idCampana);
			sbQuery.append(" \n and CONF.PK_Configuracion_Precio is not null");
			
       //     logger.info(sbQuery.toString());

			return super.jdbcTemplate.query(sbQuery.toString(),map,  new ListaConfiguracionPrecioProveedorCampanaRowMapper());

		} catch (Exception e) {
		//	logger.error("Error:" + e.getMessage());
			funcion = new Funcion();
//			funcion.enviarCorreoAvisoExepcion(e,"sbQuery: " + sbQuery,"idProveedor: " + idProveedor);
			return new ArrayList<ConfiguracionPrecio>();
		}
	}

	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ClasificacionConfiguracionPrecio> obtenerInformacionDeCampanaTipoClasificacion(
			Long idCampana) throws ProquifaNetException {
		StringBuilder sbQuery = new StringBuilder(" ");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCampana", idCampana);
			
			sbQuery.append(" \n Select CAM.PK_Campana,CAM.Tipo, CONFCLA.FK01_ConfigFamilia, CONFCLA.FK01_ConfigFamilia CONFIG, CONF.FK04_Clasificacion CLASIFICACION, CONFCLA.Concepto, COALESCE(T,0) TPRODUCTOS , RES_CLIE = 0, temporal = 0, VCT.TIPO TipoFamilia, VCST.SUBTIPO Subtipo, CONTR Control  from Campana_Comercial CAM ");
			sbQuery.append(" \n LEFT JOIN (select * from Campana_Configuracion ) as CONF on CONF.FK01_Campana = CAM.PK_Campana ");
			sbQuery.append(" \n LEFT JOIN (select * from  ConfiguracionPrecio_Clasificacion)as CONFCLA on CONFCLA.PK_ConfiguracionPrecioClasificacion = CONF.FK04_Clasificacion ");
			sbQuery.append(" \n LEFT JOIN (select * from  Configuracion_Precio)as CONFP on CONFP.PK_Configuracion_Precio = CONFCLA.FK01_ConfigFamilia  ");
			sbQuery.append(" \n LEFT JOIN(SELECT PK_Folio, Valor AS TIPO FROM ValorCombo WHERE Concepto='TipoProducto') AS VCT ON VCT.PK_Folio=  CONFP.Tipo ");
			sbQuery.append(" \n LEFT JOIN(SELECT PK_Folio, Valor AS SUBTIPO FROM ValorCombo WHERE Concepto='SubTipoProducto') AS VCST ON VCST.PK_Folio=CONFP.Subtipo ");    
			sbQuery.append(" \n  LEFT JOIN(SELECT PK_Folio, Valor AS CONTR FROM ValorCombo WHERE Concepto='Control') AS VCCTRL ON VCCTRL.PK_Folio=CONFP.Control ");
			sbQuery.append(" \n  LEFT JOIN (SELECT COUNT(idProducto) AS T, FK04_Clasificacion_ConfiguracionPrecio,MAX(idProducto) idProducto  FROM Productos WHERE Vigente = 1  GROUP BY FK04_Clasificacion_ConfiguracionPrecio )AS P ");
			sbQuery.append(" \n  ON P.FK04_Clasificacion_ConfiguracionPrecio = CONFCLA.PK_ConfiguracionPrecioClasificacion  ");
			sbQuery.append(" \n   where CAM.Tipo = 'Clasificación' and CAM.PK_Campana =  ").append(idCampana);

			
        //    logger.info(sbQuery.toString());

			return super.jdbcTemplate.query(sbQuery.toString(), map, new ConfigPrecioClasificacionRowMapper());

		} catch (Exception e) {
	//		logger.error("Error:" + e.getMessage());
			funcion = new Funcion();
//			funcion.enviarCorreoAvisoExepcion(e,"sbQuery: " + sbQuery,"idProveedor: " + idProveedor);
			return new ArrayList<ClasificacionConfiguracionPrecio>();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Producto> obtenerInformacionDeCampañaTipoProducto(
			Long idCampana) throws ProquifaNetException {
		StringBuilder sbQuery = new StringBuilder(" ");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCampana", idCampana);
			
			sbQuery.append(" \n SELECT *, PRO.Tipo as TipoProducto FROM Campana_Comercial CAM ");
			sbQuery.append(" \n LEFT JOIN (select * from Campana_Configuracion ) as CONF on CONF.FK01_Campana = CAM.PK_Campana ");
			sbQuery.append(" \n LEFT JOIN (select * from Productos) as PRO on PRO.idProducto = CONF.FK05_Producto");
			sbQuery.append(" \n where CAM.Tipo = 'Producto' and CAM.PK_Campana = ").append(idCampana);

			
           // logger.info(sbQuery.toString());

			return super.jdbcTemplate.query(sbQuery.toString(),map,  new ProductoRowMapper());

		} catch (Exception e) {
		//	logger.error("Error:" + e.getMessage());
			funcion = new Funcion();
//			funcion.enviarCorreoAvisoExepcion(e,"sbQuery: " + sbQuery,"idProveedor: " + idProveedor);
			return new ArrayList<Producto>();
		}
	}
	

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Empresa> obtenerEmpresasParaCotizar()
			throws ProquifaNetException {
		StringBuilder sql = new StringBuilder("");

		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCampana", null);
			sql.append("\n SELECT * FROM Empresa WHERE Vende=1 ORDER BY Alias ASC");
		  //  logger.info(sql.toString());
			return super.jdbcTemplate.query(sql.toString(),map,  new EmpresasProveedorRowMapper());
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			return new ArrayList<Empresa>();
		}
	}
} 