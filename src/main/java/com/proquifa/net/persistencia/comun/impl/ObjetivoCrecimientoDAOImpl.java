package com.proquifa.net.persistencia.comun.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.NivelIngreso;
import com.proquifa.net.modelo.comun.ObjetivoCrecimiento;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.tableros.cliente.ResumenVentasESAC;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.comun.ObjetivoCrecimientoDAO;
//import mx.com.proquifa.proquifanet.persistencia.comun.impl.mapper.MontoPeriodoProveedorRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.MontosxNIAnteriorRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.ObjetivoCrecimientoNIRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.ObjetivoCrecimientoporCarteraRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.ObjetivoCrecimientoporESACRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.ObjetivoCrecimientoxEVRowMapper;

@Repository
public class ObjetivoCrecimientoDAOImpl extends DataBaseDAO implements ObjetivoCrecimientoDAO {
	
	Funcion funcion;
	
	final Logger log = LoggerFactory.getLogger(ObjetivoCrecimientoDAOImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<ObjetivoCrecimiento> findObjetivosCrecimientoPorNivelIngreso()
			throws ProquifaNetException {
		String sql = "";
		try{
			sql = "SELECT * FROM ObjetivoCrecimiento_NI";
			log.info(sql);
			
			return super.jdbcTemplate.query(sql,new ObjetivoCrecimientoNIRowMapper());
			
		}catch (Exception e) {
			log.info(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "sql: " + sql);
			return new ArrayList<ObjetivoCrecimiento>();
		}
	}

	@Override
	public Boolean insertObjetivosCrecimientoPorNivelIngreso(
			List<ObjetivoCrecimiento> objetivos, String usuario) throws ProquifaNetException {
		String sql = "";
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("usuario",usuario);
			map.put("objetivos",objetivos);
			for(ObjetivoCrecimiento oc : objetivos){
				sql += " Insert into ObjetivoCrecimiento_NI (NivelIngreso, PorcentajeAnual, PorcentajeAnualFundamental, Usuario, FUA) values('" + oc.getNivelIngreso() + "'," + oc.getPorcentajeAnual()+ "'," + oc.getPorcentajeAnualFundamental() + ", '" + usuario + "',GETDATE())";
			}
			
			log.info(sql);
			
			super.jdbcTemplate.update(sql, map);
			return true;
			
		}catch(Exception e){
			log.info(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "sql: " + sql,"usuario: " + usuario);
			return false;
		}
	}

	@Override
	public Boolean updateObjetivosCrecimientoPorNivelIngreso(
			List<ObjetivoCrecimiento> objetivos, String usuario) throws ProquifaNetException {
		String sql = "";
		try{
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("usuario",usuario);
			map.put("objetivos",objetivos);
			for(ObjetivoCrecimiento oc : objetivos){
				String nivel = oc.getNivelIngreso();
				Double porcentaje = oc.getPorcentajeAnual() ;
				Double porcentajeFundamental = oc.getPorcentajeAnualFundamental();
				sql = " UPDATE ObjetivoCrecimiento_NI set PorcentajeAnual = " + porcentaje + ", Fundamental = " + porcentajeFundamental + ", Usuario ='" + usuario + "', FUA = GETDATE() where NivelIngreso ='" + nivel + "' ";
				sql += " UPDATE Clientes SET ObjetivoCrecimiento = " + porcentaje + ", ObjetivoCrecimientoFundamental = " + porcentajeFundamental;
				if(!nivel.equals("Distribuidor")){
					sql +=  " WHERE (Clave IN (SELECT FK01_Cliente FROM HistorialVenta HV	" +
							" LEFT JOIN (select Nivel, nivelingreso.MIN mi, nivelingreso.MAX ma   from NivelIngreso ) ni on ni.Nivel ='" + nivel+ "' " +
					   		" where ni.mi <= hv.MontoVenta and ni.ma >= hv.MontoVenta and hv.Anio = year(GETDATE()) -1 and Periodo = 'ANUAL'   GROUP BY FK01_Cliente) ";	
					if(nivel.equals("Bajo")){
						sql += " OR Clave NOT IN (SELECT FK01_Cliente FROM HistorialVenta HV WHERE hv.Anio = year(GETDATE()) -1) ";
					}
					sql += " ) and Rol <> 'DISTRIBUIDOR'";
					
						log.info(sql);
					
				}else{
					sql += " WHERE Rol = 'DISTRIBUIDOR'";
					log.info(sql);
				}
				
				super.jdbcTemplate.update(sql, map);
				
				
			}
			return true;
		}catch(Exception e){
			log.info(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "sql: " + sql,"usuario: " + usuario);
			return false;
		}
	}

	@Override
	public Boolean updateObjetivosCrecimientoCliente()
			throws ProquifaNetException {
		String query =  "";
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("usuario",null);
			query = 
						" \n UPDATE OBC " +
						" \n SET    OBC.Objetivo = (SELECT  ((obni.PorcentajeAnual - coalesce(obc2.objetivo,0.00)) / (12 - MONTH(GETDATE()))) nuevoObj " +
						" \n					FROM Clientes c " +
						" \n					LEFT JOIN (SELECT montoVenta, FK01_Cliente FROM HistorialVenta WHERE Anio=YEAR(GETDATE())-1 AND Periodo ='Anual')  hv on hv.FK01_Cliente = c.Clave " +
						" \n					LEFT JOIN (select * from NivelIngreso) ni on ni.MIN <= hv.MontoVenta and ni.MAX >= hv.MontoVenta " +
						" \n					LEFT JOIN (select * from ObjetivoCrecimiento_NI) obni on obni.NivelIngreso = COALESCE(CASE WHEN C.ROL = 'DISTRIBUIDOR' THEN C.ROL ELSE NULL END ,ni.Nivel,'Bajo')  " +
						" \n					LEFT JOIN (SELECT FK01_Cliente, SUM(Objetivo) objetivo, SUM(ObjetivoMonto) ObjetivoMonto " +
						" \n								FROM ObjetivoCrecimiento " +
						" \n								WHERE	Anio = YEAR(GETDATE()) AND  " +
						" \n								Mes IN( CASE WHEN month(getdate()) >= 1  THEN 'Enero'		END,CASE WHEN MONTH(getdate()) >= 2  THEN 'Febrero'   END, " +
						" \n										CASE WHEN month(getdate()) >= 3  THEN 'Marzo'		END,CASE WHEN month(getdate()) >= 4  THEN 'Abril'	  END, " +
						" \n										CASE WHEN month(getdate()) >= 5  THEN 'Mayo'		END,CASE WHEN month(getdate()) >= 6  THEN 'Junio'	  END, " +
						" \n										CASE WHEN month(getdate()) >= 7  THEN 'Julio'		END,CASE WHEN month(getdate()) >= 8  THEN 'Agosto'	  END, " +
						" \n										CASE WHEN month(getdate()) >= 9  THEN 'Septiembre'	END,CASE WHEN month(getdate()) >= 10 THEN 'Octubre'   END, " +
						" \n										CASE WHEN month(getdate()) >= 11 THEN 'Noviembre'	END,CASE WHEN month(getdate()) >= 12 THEN 'Diciembre' END) " +
						" \n							GROUP BY FK01_Cliente ) ObC2 ON ObC2.FK01_Cliente = c.Clave  " +
						" \n					WHERE  OBC.FK01_Cliente = C.Clave " +
						" \n					), " +
						" \n		OBC.ObjetivoMonto = (SELECT  ((COALESCE(hv.MontoVenta,600 ) + (( COALESCE(hv.MontoVenta,600)  * obni.PorcentajeAnual) / 100 )) - obc2.ObjetivoMonto)/ (12 - MONTH(GETDATE())) nuevoMon  " +
						" \n					FROM Clientes c " +
						" \n					LEFT JOIN (SELECT montoVenta, FK01_Cliente FROM HistorialVenta WHERE Anio=YEAR(GETDATE())-1 AND Periodo ='Anual')  hv on hv.FK01_Cliente = c.Clave " +
						" \n					left join (select * from NivelIngreso) ni on ni.MIN <= hv.MontoVenta and ni.MAX >= hv.MontoVenta " +
						" \n					left join (select * from ObjetivoCrecimiento_NI) obni on obni.NivelIngreso = COALESCE(CASE WHEN C.ROL = 'DISTRIBUIDOR' THEN C.ROL ELSE NULL END ,ni.Nivel,'Bajo')  " +
						" \n					LEFT JOIN (SELECT FK01_Cliente, SUM(Objetivo) objetivo, SUM(ObjetivoMonto) ObjetivoMonto  " +
						" \n								FROM ObjetivoCrecimiento  " +
						" \n								WHERE	Anio = YEAR(GETDATE()) AND   " +
						" \n								Mes IN( CASE WHEN month(getdate()) >= 1  THEN 'Enero'		END,CASE WHEN MONTH(getdate()) >= 2  THEN 'Febrero'   END, " +
						" \n										CASE WHEN month(getdate()) >= 3  THEN 'Marzo'		END,CASE WHEN month(getdate()) >= 4  THEN 'Abril'	  END, " +
						" \n										CASE WHEN month(getdate()) >= 5  THEN 'Mayo'		END,CASE WHEN month(getdate()) >= 6  THEN 'Junio'	  END, " +
						" \n										CASE WHEN month(getdate()) >= 7  THEN 'Julio'		END,CASE WHEN month(getdate()) >= 8  THEN 'Agosto'	  END, " +
						" \n										CASE WHEN month(getdate()) >= 9  THEN 'Septiembre'	END,CASE WHEN month(getdate()) >= 10 THEN 'Octubre'   END, " +
						" \n										CASE WHEN month(getdate()) >= 11 THEN 'Noviembre'	END,CASE WHEN month(getdate()) >= 12 THEN 'Diciembre' END) " +
						" \n							GROUP BY FK01_Cliente ) ObC2 ON ObC2.FK01_Cliente = c.Clave  " +
						" \n					WHERE  OBC.FK01_Cliente = C.Clave " +
						" \n					), " +
						" \n	OBC.ObjetivoFundamental = (SELECT  case when ((obni.Fundamental - coalesce(obc2.objetivoFundamental,0.00)) / (12 - MONTH(GETDATE())))> 0 then ((obni.Fundamental - obc2.objetivoFundamental) / (12 - MONTH(GETDATE()))) else 0 end  nuevoObj   " +
						" \n							FROM Clientes c  " +
						" \n							LEFT JOIN (SELECT montoVenta, FK01_Cliente FROM HistorialVenta WHERE Anio=YEAR(GETDATE())-1 AND Periodo ='Anual')  hv on hv.FK01_Cliente = c.Clave  " +
						" \n							LEFT JOIN (select * from NivelIngreso) ni on ni.MIN <= hv.MontoVenta and ni.MAX >= hv.MontoVenta  " +
						" \n							LEFT JOIN (select * from ObjetivoCrecimiento_NI) obni on obni.NivelIngreso = COALESCE(CASE WHEN C.ROL = 'DISTRIBUIDOR' THEN C.ROL ELSE NULL END ,ni.Nivel,'Bajo')   " +
						" \n							LEFT JOIN (SELECT FK01_Cliente, SUM(Objetivofundamental) objetivoFundamental, SUM(ObjetivoMontofundamental) ObjetivoMontoFundamental " +
						" \n										FROM ObjetivoCrecimiento  " +
						" \n										WHERE	Anio = YEAR(GETDATE()) AND   " +
						" \n										Mes IN( CASE WHEN month(getdate()) >= 1  THEN 'Enero'		END,CASE WHEN MONTH(getdate()) >= 2  THEN 'Febrero'   END,  " +
						" \n												CASE WHEN month(getdate()) >= 3  THEN 'Marzo'		END,CASE WHEN month(getdate()) >= 4  THEN 'Abril'	  END,  " +
						" \n												CASE WHEN month(getdate()) >= 5  THEN 'Mayo'		END,CASE WHEN month(getdate()) >= 6  THEN 'Junio'	  END,  " +
						" \n												CASE WHEN month(getdate()) >= 7  THEN 'Julio'		END,CASE WHEN month(getdate()) >= 8  THEN 'Agosto'	  END,  " +
						" \n												CASE WHEN month(getdate()) >= 9  THEN 'Septiembre'	END,CASE WHEN month(getdate()) >= 10 THEN 'Octubre'   END,  " +
						" \n												CASE WHEN month(getdate()) >= 11 THEN 'Noviembre'	END,CASE WHEN month(getdate()) >= 12 THEN 'Diciembre' END)  " +
						" \n									GROUP BY FK01_Cliente ) ObC2 ON ObC2.FK01_Cliente = c.Clave   " +
						" \n							WHERE  OBC.FK01_Cliente = C.Clave  " +
						" \n							),  " +
						" \n	OBC.ObjetivoMontoFundamental = (SELECT  case when((COALESCE(hv.MontoVenta,600 ) + (( COALESCE(hv.MontoVenta,600)  * obni.Fundamental) / 100 )) - obc2.ObjetivoMontoFundamental)/ (12 - MONTH(GETDATE())) > 0 then  " +
						" \n		((COALESCE(hv.MontoVenta,600 ) + (( COALESCE(hv.MontoVenta,600)  * obni.Fundamental) / 100 )) - obc2.ObjetivoMontoFundamental)/ (12 - MONTH(GETDATE())) else 0 end 	nuevoMon     " +
						" \n							FROM Clientes c  " +
						" \n							LEFT JOIN (SELECT montoVenta, FK01_Cliente FROM HistorialVenta WHERE Anio=YEAR(GETDATE())-1 AND Periodo ='Anual')  hv on hv.FK01_Cliente = c.Clave  " +
						" \n							left join (select * from NivelIngreso) ni on ni.MIN <= hv.MontoVenta and ni.MAX >= hv.MontoVenta  " +
						" \n							left join (select * from ObjetivoCrecimiento_NI) obni on obni.NivelIngreso = COALESCE(CASE WHEN C.ROL = 'DISTRIBUIDOR' THEN C.ROL ELSE NULL END ,ni.Nivel,'Bajo')   " +
						" \n							LEFT JOIN (SELECT FK01_Cliente, SUM(Objetivofundamental) objetivoFundamental, SUM(ObjetivoMontofundamental) ObjetivoMontoFundamental   " +
						" \n										FROM ObjetivoCrecimiento   " +
						" \n										WHERE	Anio = YEAR(GETDATE()) AND    " +
						" \n										Mes IN( CASE WHEN month(getdate()) >= 1  THEN 'Enero'		END,CASE WHEN MONTH(getdate()) >= 2  THEN 'Febrero'   END,  " +
						" \n												CASE WHEN month(getdate()) >= 3  THEN 'Marzo'		END,CASE WHEN month(getdate()) >= 4  THEN 'Abril'	  END,  " +
						" \n												CASE WHEN month(getdate()) >= 5  THEN 'Mayo'		END,CASE WHEN month(getdate()) >= 6  THEN 'Junio'	  END,  " +
						" \n												CASE WHEN month(getdate()) >= 7  THEN 'Julio'		END,CASE WHEN month(getdate()) >= 8  THEN 'Agosto'	  END,  " +
						" \n												CASE WHEN month(getdate()) >= 9  THEN 'Septiembre'	END,CASE WHEN month(getdate()) >= 10 THEN 'Octubre'   END,  " +
						" \n												CASE WHEN month(getdate()) >= 11 THEN 'Noviembre'	END,CASE WHEN month(getdate()) >= 12 THEN 'Diciembre' END)  " +
						" \n									GROUP BY FK01_Cliente ) ObC2 ON ObC2.FK01_Cliente = c.Clave   " +
						" \n							WHERE  OBC.FK01_Cliente = C.Clave  " +
						" \n							), " +
						" \n		OBC.Fecha = GETDATE() " +
						" \n FROM ObjetivoCrecimiento OBC " +
						" \n WHERE OBC.FK01_Cliente IS NOT NULL AND OBC.Anio = YEAR(GETDATE()) and  OBC.Mes IN(  case when month(getdate()) < 1 then 'Enero' end,case when month(getdate()) < 2 then 'Febrero' end, " +
						" \n							case when month(getdate()) < 3 then 'Marzo' end,case when month(getdate()) < 4 then 'Abril' end, " +
						" \n							case when month(getdate()) < 5 then 'Mayo' end,case when month(getdate()) < 6 then 'Junio' end, " +
						" \n							case when month(getdate()) < 7 then 'Julio' end,case when month(getdate()) < 8 then 'Agosto' end, " +
						" \n							case when month(getdate()) < 9 then 'Septiembre' end,case when month(getdate()) < 10 then 'Octubre' end, " +
						" \n							case when month(getdate()) < 11 then 'Noviembre' end,case when month(getdate()) < 12 then 'Diciembre' end)";
			 
			//log.info(query);
			
			super.jdbcTemplate.update(query, map);
			return true;
		}catch(Exception e){
			log.info(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "query: " + query);
			return false;
		}
	}

	@Override
	public Boolean insertClientesNuevosEnObjetivosCrecimiento()
			throws ProquifaNetException {
		String query = "";
		try{  // aqui se pone un monto base, solo es para insertar los registros para los cliententes nuevos, ya despues se deberan actualizar los montos con la funcion updateObjetivosCrecimientoCliente()
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("usuario",null);
			query =
			" INSERT INTO ObjetivoCrecimiento (FK01_Cliente, Fecha, Objetivo, ObjetivoMonto, ObjetivoFundamental,ObjetivoMontoFundamental, Anio, Mes) ( SELECT Clave, GETDATE(), 0, 600/12, 0, 600/12, YEAR(GETDATE()), 'Enero' 		FROM Clientes where Clave not in (select FK01_Cliente from ObjetivoCrecimiento where Anio = YEAR(GETDATE()) AND Mes = 'Enero'  AND FK01_Cliente IS NOT NULL) and Habilitado = 1 ) " +
			" INSERT INTO ObjetivoCrecimiento (FK01_Cliente, Fecha, Objetivo, ObjetivoMonto, ObjetivoFundamental,ObjetivoMontoFundamental, Anio, Mes) ( SELECT Clave, GETDATE(), 0, 600/12, 0, 600/12, YEAR(GETDATE()), 'Febrero' 		FROM Clientes where Clave not in (select FK01_Cliente from ObjetivoCrecimiento where Anio = YEAR(GETDATE()) AND Mes = 'Febrero' AND FK01_Cliente IS NOT NULL) and Habilitado = 1 ) " +
			" INSERT INTO ObjetivoCrecimiento (FK01_Cliente, Fecha, Objetivo, ObjetivoMonto, ObjetivoFundamental,ObjetivoMontoFundamental, Anio, Mes) ( SELECT Clave, GETDATE(), 0, 600/12, 0, 600/12, YEAR(GETDATE()), 'Marzo'			FROM Clientes where Clave not in (select FK01_Cliente from ObjetivoCrecimiento where Anio = YEAR(GETDATE()) AND Mes = 'Marzo' AND FK01_Cliente IS NOT NULL) and Habilitado = 1 ) " +
			" INSERT INTO ObjetivoCrecimiento (FK01_Cliente, Fecha, Objetivo, ObjetivoMonto, ObjetivoFundamental,ObjetivoMontoFundamental, Anio, Mes) ( SELECT Clave, GETDATE(), 0, 600/12, 0, 600/12, YEAR(GETDATE()), 'Abril' 		FROM Clientes where Clave not in (select FK01_Cliente from ObjetivoCrecimiento where Anio = YEAR(GETDATE()) AND Mes = 'Abril' AND FK01_Cliente IS NOT NULL) and Habilitado = 1 ) " +
			" INSERT INTO ObjetivoCrecimiento (FK01_Cliente, Fecha, Objetivo, ObjetivoMonto, ObjetivoFundamental,ObjetivoMontoFundamental, Anio, Mes) ( SELECT Clave, GETDATE(), 0, 600/12, 0, 600/12, YEAR(GETDATE()), 'Mayo' 			FROM Clientes where Clave not in (select FK01_Cliente from ObjetivoCrecimiento where Anio = YEAR(GETDATE()) AND Mes = 'Mayo' AND FK01_Cliente IS NOT NULL) and Habilitado = 1 ) " +
			" INSERT INTO ObjetivoCrecimiento (FK01_Cliente, Fecha, Objetivo, ObjetivoMonto, ObjetivoFundamental,ObjetivoMontoFundamental, Anio, Mes) ( SELECT Clave, GETDATE(), 0, 600/12, 0, 600/12, YEAR(GETDATE()), 'Junio'			FROM Clientes where Clave not in (select FK01_Cliente from ObjetivoCrecimiento where Anio = YEAR(GETDATE()) AND Mes = 'Junio' AND FK01_Cliente IS NOT NULL) and Habilitado = 1 ) " +
			" INSERT INTO ObjetivoCrecimiento (FK01_Cliente, Fecha, Objetivo, ObjetivoMonto, ObjetivoFundamental,ObjetivoMontoFundamental, Anio, Mes) ( SELECT Clave, GETDATE(), 0, 600/12, 0, 600/12, YEAR(GETDATE()), 'Julio'			FROM Clientes where Clave not in (select FK01_Cliente from ObjetivoCrecimiento where Anio = YEAR(GETDATE()) AND Mes = 'Julio' AND FK01_Cliente IS NOT NULL) and Habilitado = 1 ) " +
			" INSERT INTO ObjetivoCrecimiento (FK01_Cliente, Fecha, Objetivo, ObjetivoMonto, ObjetivoFundamental,ObjetivoMontoFundamental, Anio, Mes) ( SELECT Clave, GETDATE(), 0, 600/12, 0, 600/12, YEAR(GETDATE()), 'Agosto'		FROM Clientes where Clave not in (select FK01_Cliente from ObjetivoCrecimiento where Anio = YEAR(GETDATE()) AND Mes = 'Agosto' AND FK01_Cliente IS NOT NULL) and Habilitado = 1 ) " +
			" INSERT INTO ObjetivoCrecimiento (FK01_Cliente, Fecha, Objetivo, ObjetivoMonto, ObjetivoFundamental,ObjetivoMontoFundamental, Anio, Mes) ( SELECT Clave, GETDATE(), 0, 600/12, 0, 600/12, YEAR(GETDATE()), 'Septiembre'	FROM Clientes where Clave not in (select FK01_Cliente from ObjetivoCrecimiento where Anio = YEAR(GETDATE()) AND Mes = 'Septiembre' AND FK01_Cliente IS NOT NULL) and Habilitado = 1 ) " +
			" INSERT INTO ObjetivoCrecimiento (FK01_Cliente, Fecha, Objetivo, ObjetivoMonto, ObjetivoFundamental,ObjetivoMontoFundamental, Anio, Mes) ( SELECT Clave, GETDATE(), 0, 600/12, 0, 600/12, YEAR(GETDATE()), 'Octubre' 		FROM Clientes where Clave not in (select FK01_Cliente from ObjetivoCrecimiento where Anio = YEAR(GETDATE()) AND Mes = 'Octubre' AND FK01_Cliente IS NOT NULL) and Habilitado = 1 ) " +
			" INSERT INTO ObjetivoCrecimiento (FK01_Cliente, Fecha, Objetivo, ObjetivoMonto, ObjetivoFundamental,ObjetivoMontoFundamental, Anio, Mes) ( SELECT Clave, GETDATE(), 0, 600/12, 0, 600/12, YEAR(GETDATE()), 'Noviembre' 	FROM Clientes where Clave not in (select FK01_Cliente from ObjetivoCrecimiento where Anio = YEAR(GETDATE()) AND Mes = 'Noviembre' AND FK01_Cliente IS NOT NULL) and Habilitado = 1 ) " +
			" INSERT INTO ObjetivoCrecimiento (FK01_Cliente, Fecha, Objetivo, ObjetivoMonto, ObjetivoFundamental,ObjetivoMontoFundamental, Anio, Mes) ( SELECT Clave, GETDATE(), 0, 600/12, 0, 600/12, YEAR(GETDATE()), 'Diciembre' 	FROM Clientes where Clave not in (select FK01_Cliente from ObjetivoCrecimiento where Anio = YEAR(GETDATE()) AND Mes = 'Diciembre' AND FK01_Cliente IS NOT NULL) and Habilitado = 1 ) " ;
			 
	//		//log.info(query);
			
			super.jdbcTemplate.update(query,map);
			return true;
		}catch(Exception e){
			log.info(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "query: " + query);
			return false;
		}
	}

	@Override
	public Double getObjetivoCrecimientoCliente(Long idCliente)
			throws ProquifaNetException {
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCliente",idCliente);
			return (Double) super.jdbcTemplate.queryForObject("SELECT ObjetivoCrecimiento FROM Clientes WHERE Clave = " + idCliente, map, Double.class);
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "idCliente: " + idCliente);
			return -1D;
		}
	}
	
	@Override
	public Double getObjetivoCrecimientoProveedor(Long idProveedor) throws ProquifaNetException {		
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idProveedor",idProveedor);
			return (Double) super.jdbcTemplate.queryForObject("SELECT ObjetivoCrecimiento FROM Proveedores WHERE Clave = " + idProveedor,map,  Double.class);
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "idProveedor: " + idProveedor);
			return -1D;
		}
	}
	@Override
	public Double getObjetivoCrecimientoFundamentalProveedor(Long idProveedor) throws ProquifaNetException {		
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idProveedor",idProveedor);
			return (Double) super.jdbcTemplate.queryForObject("SELECT ObjetivoCrecimientoFundamental FROM Proveedores WHERE Clave = " + idProveedor,map,  Double.class);
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "idProveedor: " + idProveedor);
			return -1D;
		}
	}
	
	
	public Boolean agregarObjetivoCrecimiento(Cliente cliente)
			throws ProquifaNetException {

		try {
//			Funcion function = new Funcion();
//			Double montoAnualAnterior = montoAnualAnteriorXCliente(cliente.getIdCliente());
//			log.info("Monto: " + montoAnualAnterior);
//
//			Double objetivoPasado = obtenerObjetivoTranscurrido(cliente.getIdCliente());
//			log.info("ObjetivoPasado: " + objetivoPasado);

//			StringBuilder sbObjetivoExiste = new StringBuilder("SELECT * FROM ObjetivoCrecimiento WHERE FK01_Cliente = ").append(cliente.getIdCliente()).append(" AND Anio=YEAR(GETDATE()) \n");
//			log.info(sbObjetivoExiste.toString());
//			List existe = this.jdbcTemplate.queryForList(sbObjetivoExiste.toString());
//
//			int mes = Calendar.getInstance().get(Calendar.MONTH) + 1;
//			int mesRestantes = 12 - ( mes -1 ) ;
//			if (existe.isEmpty()){
//				mes = 1;
//
//				for (int i = mes; i <= 12; i++){
//					StringBuilder sbQuery = new StringBuilder("INSERT INTO ObjetivoCrecimiento (FK01_Cliente, Fecha, Objetivo, ObjetivoMonto, Mes, Anio) VALUES( \n");
//					sbQuery.append(cliente.getIdCliente()).append(", GETDATE(), ROUND(").append((Double.valueOf(cliente.getObjectivoCrecimiento()) - objetivoPasado) /mesRestantes).append(",2), ");
//					sbQuery.append("ROUND(").append(((montoAnualAnterior * (1 + (Double.valueOf(cliente.getObjectivoCrecimiento()) / 100))) / mesRestantes ) ).append(" ,2), ");
//					sbQuery.append("'").append(function.obtenerMesConLetra(i)).append("', YEAR(GETDATE()) ) \n");
//
//					this.jdbcTemplate.update(sbQuery.toString());
//					log.info(sbQuery.toString());
//				}
//			}
//			else{
//				for (int i = mes; i <= 12; i++){
//					StringBuilder sbQuery = new StringBuilder("UPDATE ObjetivoCrecimiento SET Fecha = GETDATE(), ");
//					sbQuery.append("Objetivo = ROUND(").append( (Double.valueOf(cliente.getObjectivoCrecimiento()) - objetivoPasado) /mesRestantes ).append(",2),");
//					sbQuery.append("ObjetivoMonto = ROUND(").append( ((montoAnualAnterior * (1 + (Double.valueOf(cliente.getObjectivoCrecimiento()) / 100))) / mesRestantes ) ).append(",2) \n");
//					sbQuery.append("WHERE FK01_Cliente = ").append(cliente.getIdCliente()).append(" AND Mes = '").append(function.obtenerMesConLetra(i)).append("' AND Anio = YEAR(GETDATE()) \n");
//
//					this.jdbcTemplate.update(sbQuery.toString());
//					log.info(sbQuery.toString());
//				}
//			}

			return true;
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,cliente);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			e.printStackTrace();
			return false;
		}
	}

	private String obtenerMontoInicial(String mes, String objetivo){
		try {
			String monto = " CASE WHEN MONTH(GETDATE()) <=  " + mes + "  THEN (((600.00 * " + objetivo + ") / 100.00) + 600.00 )/12.00 ELSE 0 END";
			return monto;
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"mes: " + mes, "objetivo: " + objetivo);
			return "";
		}
	}
	
	@Override
	public Boolean insertObjetivoCrecimientoPorCliente(Long idCliente, String objetivo, String objetivoFundamental)
			throws ProquifaNetException {
		String query  = "";
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("objetivoFundamental",objetivoFundamental);
			map.put("objetivo",objetivo);
			map.put("idCliente",idCliente);
			funcion = new Funcion();
			 DecimalFormat df = new DecimalFormat("#.##");
			Double objetivodbl = 0D ;
			Double objetivoFundamentaldbl = 0D;
			if (objetivo !=null && !objetivo.equals("0")  ){
				objetivodbl = Double.parseDouble(objetivo);
				
				if (objetivodbl <= 0) 
					objetivodbl = 0.00;
				objetivo =  df.format(objetivodbl).toString();
				objetivo = objetivo.replace(',','.');
			} else {
				objetivo = "0.00";
			}
			if (objetivoFundamental != null && !objetivoFundamental.equals("0") ){
				objetivoFundamentaldbl = Double.parseDouble(objetivoFundamental);
				
				if (objetivoFundamentaldbl <= 0) 
						objetivoFundamentaldbl = 0.00;
				objetivoFundamental =  df.format(objetivoFundamentaldbl).toString();
				objetivoFundamental = objetivoFundamental.replace(',','.');
			} else {
				objetivoFundamental = "0.00";
			}
		query =		
				" INSERT INTO ObjetivoCrecimiento (FK01_Cliente, Fecha, Objetivo, ObjetivoMonto, ObjetivoFundamental, ObjetivoMontoFundamental, Anio, Mes) " +
				" VALUES( " + idCliente + ", GETDATE(), " + objetivo + "/12.00, " + obtenerMontoInicial("1", objetivo) + "," + objetivoFundamental + "/12.00, " + obtenerMontoInicial("1", objetivoFundamental) + ", YEAR(GETDATE()), 'Enero') 		 " +
				" INSERT INTO ObjetivoCrecimiento (FK01_Cliente, Fecha, Objetivo, ObjetivoMonto,ObjetivoFundamental , ObjetivoMontoFundamental, Anio, Mes)" +
				" VALUES( " + idCliente + ", GETDATE(), " + objetivo + "/12.00, " + obtenerMontoInicial("2", objetivo) + "," + objetivoFundamental + "/12.00, " + obtenerMontoInicial("2", objetivoFundamental) + ", YEAR(GETDATE()), 'Febrero') 	 " +
				" INSERT INTO ObjetivoCrecimiento (FK01_Cliente, Fecha, Objetivo, ObjetivoMonto,ObjetivoFundamental , ObjetivoMontoFundamental, Anio, Mes)" +
				" VALUES( " + idCliente + ", GETDATE(), " + objetivo + "/12.00, " + obtenerMontoInicial("3", objetivo) + "," + objetivoFundamental + "/12.00, " + obtenerMontoInicial("3", objetivoFundamental) + ", YEAR(GETDATE()), 'Marzo') 		" +
				" INSERT INTO ObjetivoCrecimiento (FK01_Cliente, Fecha, Objetivo, ObjetivoMonto,ObjetivoFundamental , ObjetivoMontoFundamental, Anio, Mes)" +
				" VALUES( " + idCliente + ", GETDATE(), " + objetivo + "/12.00, " + obtenerMontoInicial("4", objetivo) + "," + objetivoFundamental + "/12.00, " + obtenerMontoInicial("4", objetivoFundamental) + ", YEAR(GETDATE()), 'Abril') 		" +
				" INSERT INTO ObjetivoCrecimiento (FK01_Cliente, Fecha, Objetivo, ObjetivoMonto,ObjetivoFundamental , ObjetivoMontoFundamental, Anio, Mes)" +
				" VALUES( " + idCliente + ", GETDATE(), " + objetivo + "/12.00, " + obtenerMontoInicial("5", objetivo) + "," + objetivoFundamental + "/12.00, " + obtenerMontoInicial("5", objetivoFundamental) + ", YEAR(GETDATE()), 'Mayo') 		" +
				" INSERT INTO ObjetivoCrecimiento (FK01_Cliente, Fecha, Objetivo, ObjetivoMonto,ObjetivoFundamental , ObjetivoMontoFundamental, Anio, Mes)" +
				" VALUES( " + idCliente + ", GETDATE(), " + objetivo + "/12.00, " + obtenerMontoInicial("6", objetivo) + "," + objetivoFundamental + "/12.00, " + obtenerMontoInicial("6", objetivoFundamental) + ", YEAR(GETDATE()), 'Junio') 		 " +
				" INSERT INTO ObjetivoCrecimiento (FK01_Cliente, Fecha, Objetivo, ObjetivoMonto,ObjetivoFundamental , ObjetivoMontoFundamental, Anio, Mes)" +
				" VALUES( " + idCliente + ", GETDATE(), " + objetivo + "/12.00, " + obtenerMontoInicial("7", objetivo) + "," + objetivoFundamental + "/12.00, " + obtenerMontoInicial("7", objetivoFundamental) + ", YEAR(GETDATE()), 'Julio')		 " +
				" INSERT INTO ObjetivoCrecimiento (FK01_Cliente, Fecha, Objetivo, ObjetivoMonto,ObjetivoFundamental , ObjetivoMontoFundamental, Anio, Mes)" +
				" VALUES( " + idCliente + ", GETDATE(), " + objetivo + "/12.00, " + obtenerMontoInicial("8", objetivo) + "," + objetivoFundamental + "/12.00, " + obtenerMontoInicial("8", objetivoFundamental) + ", YEAR(GETDATE()), 'Agosto') 		 " +
				" INSERT INTO ObjetivoCrecimiento (FK01_Cliente, Fecha, Objetivo, ObjetivoMonto,ObjetivoFundamental , ObjetivoMontoFundamental, Anio, Mes)" +
				" VALUES( " + idCliente + ", GETDATE(), " + objetivo + "/12.00, " + obtenerMontoInicial("9", objetivo) + "," + objetivoFundamental + "/12.00, " + obtenerMontoInicial("9", objetivoFundamental) + ", YEAR(GETDATE()), 'Septiembre') 	 " +
				" INSERT INTO ObjetivoCrecimiento (FK01_Cliente, Fecha, Objetivo, ObjetivoMonto,ObjetivoFundamental , ObjetivoMontoFundamental, Anio, Mes)" +
				" VALUES( " + idCliente + ", GETDATE(), " + objetivo + "/12.00, " + obtenerMontoInicial("10", objetivo) + "," + objetivoFundamental + "/12.00, " + obtenerMontoInicial("10", objetivoFundamental) + ", YEAR(GETDATE()), 'Octubre') 	 " +
				" INSERT INTO ObjetivoCrecimiento (FK01_Cliente, Fecha, Objetivo, ObjetivoMonto,ObjetivoFundamental , ObjetivoMontoFundamental, Anio, Mes)" +
				" VALUES( " + idCliente + ", GETDATE(), " + objetivo + "/12.00, " + obtenerMontoInicial("11", objetivo) + "," + objetivoFundamental + "/12.00, " + obtenerMontoInicial("11", objetivoFundamental) + ", YEAR(GETDATE()), 'Noviembre') 	" +
				" INSERT INTO ObjetivoCrecimiento (FK01_Cliente, Fecha, Objetivo, ObjetivoMonto,ObjetivoFundamental , ObjetivoMontoFundamental, Anio, Mes)" +
				" VALUES( " + idCliente + ", GETDATE(), " + objetivo + "/12.00, " + obtenerMontoInicial("11", objetivo) + "," + objetivoFundamental + "/12.00, " + obtenerMontoInicial("12", objetivoFundamental) + ", YEAR(GETDATE()), 'Diciembre') 	 " ;
		
				 
				//log.info(query);
				super.jdbcTemplate.update(query, map);
				log.info("objetivoCrecimientoInsertadoCorrectamente");
		}catch (Exception e) {
			log.info(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"query: " + query, "idCliente: " + idCliente,
					"objetivo: " + objetivo, "objetivoFundamental: " + objetivoFundamental);
			return false;
		}
				return true;
	}

	
	
	@Override
	public Boolean updatePorcentajeDeCrecimientoPorTipoProveedor(
			Double objetivoCrecimientoFundametal,Double objetivoCrecimiento, String condicion) throws ProquifaNetException {
		String query = "";
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("objetivoCrecimiento",objetivoCrecimiento);
			map.put("condicion",condicion);
			query = " UPDATE Proveedores SET ObjetivoCrecimiento = " + objetivoCrecimiento + ", ObjetivoCrecimientoFundamental = "+objetivoCrecimientoFundametal+"   WHERE " + condicion;
			
			//log.info(query);
			super.jdbcTemplate.update(query, map);
			
			return true;
		}catch (Exception e) {
			log.info(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"query: " + query, "objetivoCrecimiento: " + objetivoCrecimiento,"objetivoCrecimientoFundametal: "+objetivoCrecimientoFundametal,
					"condicion: " + condicion);
			return false;
		}
	}

	@Override
	public Boolean updateObjetivosCrecimientoPorProveedor(String condicion) throws ProquifaNetException {
		String query = "";
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("condicion",condicion);
			query = 
					" \n UPDATE OBC  " +
					" \n SET    OBC.Objetivo = (SELECT  ((P.ObjetivoCrecimiento - obc2.objetivo) / (12 - MONTH(GETDATE()))) nuevoObj  " +
//					" \n SET    OBC.Objetivo = (SELECT  (P.ObjetivoCrecimiento / 12) nuevoObj  " +
					" \n 					FROM Proveedores P   " +
					" \n 					LEFT JOIN (SELECT FK02_Proveedor, SUM(Objetivo) objetivo  " +
					" \n 								FROM ObjetivoCrecimiento  " +
					" \n 								WHERE	Anio = YEAR(GETDATE()) AND   " +
					" \n 								Mes IN( CASE WHEN month(getdate()) >= 1  THEN 'Enero'		END,CASE WHEN MONTH(getdate()) >= 2  THEN 'Febrero'   END,  " +
					" \n 										CASE WHEN month(getdate()) >= 3  THEN 'Marzo'		END,CASE WHEN month(getdate()) >= 4  THEN 'Abril'	  END,  " +
					" \n 										CASE WHEN month(getdate()) >= 5  THEN 'Mayo'		END,CASE WHEN month(getdate()) >= 6  THEN 'Junio'	  END,  " +
					" \n 										CASE WHEN month(getdate()) >= 7  THEN 'Julio'		END,CASE WHEN month(getdate()) >= 8  THEN 'Agosto'	  END,  " +
					" \n 										CASE WHEN month(getdate()) >= 9  THEN 'Septiembre'	END,CASE WHEN month(getdate()) >= 10 THEN 'Octubre'   END,  " +
					" \n 										CASE WHEN month(getdate()) >= 11 THEN 'Noviembre'	END,CASE WHEN month(getdate()) >= 12 THEN 'Diciembre' END)  " +
					" \n 							GROUP BY FK02_Proveedor ) ObC2 ON ObC2.FK02_Proveedor = p.Clave   " +
					" \n 					WHERE  OBC.FK02_Proveedor = P.Clave  " +
					" \n 					),  " +
					" \n		OBC.ObjetivoFundamental = (SELECT  case when (( P.ObjetivoCrecimientoFundamental - coalesce(obc2.objetivoFundamental,0.00)) / (12 - MONTH(GETDATE())))> 0 " +
					" \n		 		then ((P.ObjetivoCrecimientoFundamental - obc2.objetivoFundamental) / (12 - MONTH(GETDATE()))) else 0 end  nuevoObj   " +
//					" \n		OBC.ObjetivoFundamental = (SELECT  case when (( P.ObjetivoCrecimientoFundamental) / (12)) > 0 " +
//					" \n		 		then (( P.ObjetivoCrecimientoFundamental) / (12)) else 0 end  nuevoObj   " +
					" \n										FROM Proveedores P    " +
					" \n										LEFT JOIN (SELECT FK02_Proveedor, SUM(Objetivofundamental) objetivoFundamental" +
					" \n													FROM ObjetivoCrecimiento  " +
					" \n													WHERE	Anio = YEAR(GETDATE()) AND   " +
					" \n													Mes IN( CASE WHEN month(getdate()) >= 1  THEN 'Enero'		END,CASE WHEN MONTH(getdate()) >= 2  THEN 'Febrero'   END,  " +
					" \n															CASE WHEN month(getdate()) >= 3  THEN 'Marzo'		END,CASE WHEN month(getdate()) >= 4  THEN 'Abril'	  END,  " +
					" \n															CASE WHEN month(getdate()) >= 5  THEN 'Mayo'		END,CASE WHEN month(getdate()) >= 6  THEN 'Junio'	  END,  " +
					" \n															CASE WHEN month(getdate()) >= 7  THEN 'Julio'		END,CASE WHEN month(getdate()) >= 8  THEN 'Agosto'	  END,  " +
					" \n															CASE WHEN month(getdate()) >= 9  THEN 'Septiembre'	END,CASE WHEN month(getdate()) >= 10 THEN 'Octubre'   END,  " +
					" \n															CASE WHEN month(getdate()) >= 11 THEN 'Noviembre'	END,CASE WHEN month(getdate()) >= 12 THEN 'Diciembre' END)  " +
					" \n												GROUP BY FK02_Proveedor ) ObC2 ON ObC2.FK02_Proveedor = p.Clave   " +
					" \n		 					WHERE  OBC.FK02_Proveedor = P.Clave " +
					" \n							), " +
					" \n 		OBC.ObjetivoMonto = (SELECT  ((COALESCE(hv.MONTO,600 ) + (( COALESCE(hv.MONTO,600)  * P.ObjetivoCrecimiento) / 100 )) - obc2.ObjetivoMonto)/ (12 - MONTH(GETDATE())) nuevoMon   " +
//					" \n 		OBC.ObjetivoMonto = (SELECT  (COALESCE(hv.MONTO,600 ) + (( COALESCE(hv.MONTO,600)  * P.ObjetivoCrecimiento) / 100 ))/ (12) nuevoMon   " +
					" \n 					FROM Proveedores P  " +
					" \n 					LEFT JOIN (SELECT COALESCE(SUM(  " +
					" \n  								CASE WHEN C.Moneda = 'Dolares' OR C.Moneda ='USD' THEN (PC.Costo * PC.Cant) WHEN C.Moneda = 'EUR' OR C.Moneda = 'Euros' THEN (PC.Costo * PC.Cant)* M.EDolar  " +
					" \n  								WHEN C.Moneda = 'Pesos' OR C.Moneda = 'M.N.' THEN (PC.Costo * PC.Cant)/M.PDolar WHEN C.Moneda = 'Libras' THEN (PC.Costo * PC.Cant) * M.LDolar WHEN C.Moneda = 'DlCan' THEN (PC.Costo * PC.Cant)/(1/M.DDolar) END  " +
					" \n  								), 0) AS MONTO, C.Provee " +
					" \n  								FROM Compras C  " +
					" \n  								INNER JOIN (SELECT * FROM PCompras WHERE Estado <> 'Cancelada') PC ON PC.Compra = C.Clave  " +
					" \n  								LEFT JOIN Monedas M ON CAST(M.Fecha AS DATE) = CAST(C.Fecha AS DATE)  " +
					" \n  								WHERE C.Estado <> 'Cancelada' AND YEAR(C.Fecha) = YEAR(GETDATE()) - 1 " +
					" \n  								GROUP BY C.Provee)  hv on hv.Provee = P.Clave  " +
					" \n 					LEFT JOIN (SELECT FK02_Proveedor, SUM(ObjetivoMonto) ObjetivoMonto   " +
					" \n 								FROM ObjetivoCrecimiento   " +
					" \n 								WHERE	Anio = YEAR(GETDATE()) AND    " +
					" \n 								Mes IN( CASE WHEN month(getdate()) >= 1  THEN 'Enero'		END,CASE WHEN MONTH(getdate()) >= 2  THEN 'Febrero'   END,  " +
					" \n 										CASE WHEN month(getdate()) >= 3  THEN 'Marzo'		END,CASE WHEN month(getdate()) >= 4  THEN 'Abril'	  END,  " +
					" \n 										CASE WHEN month(getdate()) >= 5  THEN 'Mayo'		END,CASE WHEN month(getdate()) >= 6  THEN 'Junio'	  END,  " +
					" \n 										CASE WHEN month(getdate()) >= 7  THEN 'Julio'		END,CASE WHEN month(getdate()) >= 8  THEN 'Agosto'	  END,  " +
					" \n 										CASE WHEN month(getdate()) >= 9  THEN 'Septiembre'	END,CASE WHEN month(getdate()) >= 10 THEN 'Octubre'   END,  " +
					" \n 										CASE WHEN month(getdate()) >= 11 THEN 'Noviembre'	END,CASE WHEN month(getdate()) >= 12 THEN 'Diciembre' END)  " +
					" \n 							GROUP BY FK02_Proveedor ) ObC2 ON ObC2.FK02_Proveedor = P.Clave   " +
					" \n 					WHERE  OBC.FK02_Proveedor = P.Clave  " +
					" \n 					),  " +
					" \n		OBC.ObjetivoMontoFundamental = (SELECT  case when((COALESCE(hv.Monto,600 ) + (( COALESCE(hv.Monto,600)  * P.ObjetivoCrecimientoFundamental) / 100 )) - obc2.ObjetivoMontoFundamental)/ (12 - MONTH(GETDATE())) > 0 then  " +
					" \n				((COALESCE(hv.Monto,600 ) + (( COALESCE(hv.Monto,600)  * P.ObjetivoCrecimientoFundamental) / 100 )) - obc2.ObjetivoMontoFundamental)/ (12 - MONTH(GETDATE())) else 0 end 	nuevoMon     " +
//					" \n		OBC.ObjetivoMontoFundamental = (SELECT  case when((COALESCE(hv.Monto,600 ) + (( COALESCE(hv.Monto,600)  * P.ObjetivoCrecimientoFundamental) / 100 )) )/ (12 ) > 0 then  " +
//					" \n				((COALESCE(hv.Monto,600 ) + (( COALESCE(hv.Monto,600)  * P.ObjetivoCrecimientoFundamental) / 100 )))/ (12) else 0 end 	nuevoMon     " +
					" \n									FROM Proveedores P  " +
					" \n									LEFT JOIN (SELECT COALESCE(SUM(  " +
					" \n	  								CASE WHEN C.Moneda = 'Dolares' OR C.Moneda ='USD' THEN (PC.Costo * PC.Cant) WHEN C.Moneda = 'EUR' OR C.Moneda = 'Euros' THEN (PC.Costo * PC.Cant)* M.EDolar  " +
					" \n	  								WHEN C.Moneda = 'Pesos' OR C.Moneda = 'M.N.' THEN (PC.Costo * PC.Cant)/M.PDolar WHEN C.Moneda = 'Libras' THEN (PC.Costo * PC.Cant) * M.LDolar WHEN C.Moneda = 'DlCan' THEN (PC.Costo * PC.Cant)/(1/M.DDolar) END  " +
					" \n	  								), 0) AS MONTO, C.Provee " +
					" \n	  								FROM Compras C  " +
					" \n	  								INNER JOIN (SELECT * FROM PCompras WHERE Estado <> 'Cancelada') PC ON PC.Compra = C.Clave  " +
					" \n	  								LEFT JOIN Monedas M ON CAST(M.Fecha AS DATE) = CAST(C.Fecha AS DATE)  " +
					" \n	  								WHERE C.Estado <> 'Cancelada' AND YEAR(C.Fecha) = YEAR(GETDATE()) - 1 " +
					" \n	  								GROUP BY C.Provee)  hv on hv.Provee = P.Clave    " +
					" \n									LEFT JOIN (SELECT FK02_Proveedor, SUM(ObjetivoMontofundamental) ObjetivoMontoFundamental   " +
					" \n												FROM ObjetivoCrecimiento   " +
					" \n												WHERE	Anio = YEAR(GETDATE()) AND    " +
					" \n												Mes IN( CASE WHEN month(getdate()) >= 1  THEN 'Enero'		END,CASE WHEN MONTH(getdate()) >= 2  THEN 'Febrero'   END,  " +
					" \n														CASE WHEN month(getdate()) >= 3  THEN 'Marzo'		END,CASE WHEN month(getdate()) >= 4  THEN 'Abril'	  END,  " +
					" \n														CASE WHEN month(getdate()) >= 5  THEN 'Mayo'		END,CASE WHEN month(getdate()) >= 6  THEN 'Junio'	  END,  " +
					" \n														CASE WHEN month(getdate()) >= 7  THEN 'Julio'		END,CASE WHEN month(getdate()) >= 8  THEN 'Agosto'	  END,  " +
					" \n														CASE WHEN month(getdate()) >= 9  THEN 'Septiembre'	END,CASE WHEN month(getdate()) >= 10 THEN 'Octubre'   END,  " +
					" \n														CASE WHEN month(getdate()) >= 11 THEN 'Noviembre'	END,CASE WHEN month(getdate()) >= 12 THEN 'Diciembre' END)  " +
					" \n											GROUP BY FK02_Proveedor ) ObC2 ON ObC2.FK02_Proveedor = P.Clave   " +
					" \n	 					WHERE  OBC.FK02_Proveedor = P.Clave " +
					" \n									)," +
					" \n 		OBC.Fecha = GETDATE()						  " +
					" \n  FROM ObjetivoCrecimiento OBC  " + 
					" \n  WHERE OBC.FK02_Proveedor IS NOT NULL AND OBC.Anio = YEAR(GETDATE()) " + condicion +
					" \n  AND  OBC.Mes IN(  case when month(getdate()) < 1 then 'Enero' end,case when month(getdate()) < 2 then 'Febrero' end,  " +
					" \n 							case when month(getdate()) < 3 then 'Marzo' end,case when month(getdate()) < 4 then 'Abril' end,  " +
					" \n 							case when month(getdate()) < 5 then 'Mayo' end,case when month(getdate()) < 6 then 'Junio' end,  " +
					" \n 							case when month(getdate()) < 7 then 'Julio' end,case when month(getdate()) < 8 then 'Agosto' end,  " +
					" \n 							case when month(getdate()) < 9 then 'Septiembre' end,case when month(getdate()) < 10 then 'Octubre' end,  " +
					" \n 							case when month(getdate()) < 11 then 'Noviembre' end,case when month(getdate()) < 12 then 'Diciembre' end)";
			 
			log.info(query);
			
			super.jdbcTemplate.update(query, map);
			return true;
		}catch (Exception e) {
			log.info(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"query: " + query);
			return false;
		}
	}

	@Override
	public Boolean insertObjetivosCrecimientoPorProveedor() throws ProquifaNetException {
		String query = "";
		try{  
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("condicion",null);
			query =
			" INSERT INTO ObjetivoCrecimiento (FK02_Proveedor, Fecha, Objetivo, ObjetivoMonto, ObjetivoFundamental, ObjetivoMontoFundamental, Anio, Mes) ( SELECT Clave, GETDATE(), 0, 0, 0, 0, YEAR(GETDATE()), 'Enero' 		FROM Proveedores where Clave not in (select FK02_Proveedor from ObjetivoCrecimiento where Anio = YEAR(GETDATE()) AND Mes = 'Enero' AND FK02_Proveedor IS NOT NULL) 	and Habilitado = 1 ) " +
			" INSERT INTO ObjetivoCrecimiento (FK02_Proveedor, Fecha, Objetivo, ObjetivoMonto, ObjetivoFundamental, ObjetivoMontoFundamental, Anio, Mes) ( SELECT Clave, GETDATE(), 0, 0, 0, 0, YEAR(GETDATE()), 'Febrero' 		FROM Proveedores where Clave not in (select FK02_Proveedor from ObjetivoCrecimiento where Anio = YEAR(GETDATE()) AND Mes = 'Febrero' AND FK02_Proveedor IS NOT NULL) 	and Habilitado = 1 ) " +
			" INSERT INTO ObjetivoCrecimiento (FK02_Proveedor, Fecha, Objetivo, ObjetivoMonto, ObjetivoFundamental, ObjetivoMontoFundamental, Anio, Mes) ( SELECT Clave, GETDATE(), 0, 0, 0, 0, YEAR(GETDATE()), 'Marzo'		FROM Proveedores where Clave not in (select FK02_Proveedor from ObjetivoCrecimiento where Anio = YEAR(GETDATE()) AND Mes = 'Marzo' AND FK02_Proveedor IS NOT NULL) 	and Habilitado = 1 ) " +
			" INSERT INTO ObjetivoCrecimiento (FK02_Proveedor, Fecha, Objetivo, ObjetivoMonto, ObjetivoFundamental, ObjetivoMontoFundamental, Anio, Mes) ( SELECT Clave, GETDATE(), 0, 0, 0, 0, YEAR(GETDATE()), 'Abril' 		FROM Proveedores where Clave not in (select FK02_Proveedor from ObjetivoCrecimiento where Anio = YEAR(GETDATE()) AND Mes = 'Abril' AND FK02_Proveedor IS NOT NULL) 	and Habilitado = 1 ) " +
			" INSERT INTO ObjetivoCrecimiento (FK02_Proveedor, Fecha, Objetivo, ObjetivoMonto, ObjetivoFundamental, ObjetivoMontoFundamental, Anio, Mes) ( SELECT Clave, GETDATE(), 0, 0, 0, 0, YEAR(GETDATE()), 'Mayo' 		FROM Proveedores where Clave not in (select FK02_Proveedor from ObjetivoCrecimiento where Anio = YEAR(GETDATE()) AND Mes = 'Mayo' AND FK02_Proveedor IS NOT NULL) 		and Habilitado = 1 ) " +
			" INSERT INTO ObjetivoCrecimiento (FK02_Proveedor, Fecha, Objetivo, ObjetivoMonto, ObjetivoFundamental, ObjetivoMontoFundamental, Anio, Mes) ( SELECT Clave, GETDATE(), 0, 0, 0, 0, YEAR(GETDATE()), 'Junio'		FROM Proveedores where Clave not in (select FK02_Proveedor from ObjetivoCrecimiento where Anio = YEAR(GETDATE()) AND Mes = 'Junio' AND FK02_Proveedor IS NOT NULL) 	and Habilitado = 1 ) " +
			" INSERT INTO ObjetivoCrecimiento (FK02_Proveedor, Fecha, Objetivo, ObjetivoMonto, ObjetivoFundamental, ObjetivoMontoFundamental, Anio, Mes) ( SELECT Clave, GETDATE(), 0, 0, 0, 0, YEAR(GETDATE()), 'Julio'		FROM Proveedores where Clave not in (select FK02_Proveedor from ObjetivoCrecimiento where Anio = YEAR(GETDATE()) AND Mes = 'Julio' AND FK02_Proveedor IS NOT NULL) 	and Habilitado = 1 ) " +
			" INSERT INTO ObjetivoCrecimiento (FK02_Proveedor, Fecha, Objetivo, ObjetivoMonto, ObjetivoFundamental, ObjetivoMontoFundamental, Anio, Mes) ( SELECT Clave, GETDATE(), 0, 0, 0, 0, YEAR(GETDATE()), 'Agosto'		FROM Proveedores where Clave not in (select FK02_Proveedor from ObjetivoCrecimiento where Anio = YEAR(GETDATE()) AND Mes = 'Agosto' AND FK02_Proveedor IS NOT NULL) 	and Habilitado = 1 ) " +
			" INSERT INTO ObjetivoCrecimiento (FK02_Proveedor, Fecha, Objetivo, ObjetivoMonto, ObjetivoFundamental, ObjetivoMontoFundamental, Anio, Mes) ( SELECT Clave, GETDATE(), 0, 0, 0, 0, YEAR(GETDATE()), 'Septiembre' 	FROM Proveedores where Clave not in (select FK02_Proveedor from ObjetivoCrecimiento where Anio = YEAR(GETDATE()) AND Mes = 'Septiembre' AND FK02_Proveedor IS NOT NULL) and Habilitado = 1 ) " +
			" INSERT INTO ObjetivoCrecimiento (FK02_Proveedor, Fecha, Objetivo, ObjetivoMonto, ObjetivoFundamental, ObjetivoMontoFundamental, Anio, Mes) ( SELECT Clave, GETDATE(), 0, 0, 0, 0, YEAR(GETDATE()), 'Octubre' 		FROM Proveedores where Clave not in (select FK02_Proveedor from ObjetivoCrecimiento where Anio = YEAR(GETDATE()) AND Mes = 'Octubre' AND FK02_Proveedor IS NOT NULL) 	and Habilitado = 1 ) " +
			" INSERT INTO ObjetivoCrecimiento (FK02_Proveedor, Fecha, Objetivo, ObjetivoMonto, ObjetivoFundamental, ObjetivoMontoFundamental, Anio, Mes) ( SELECT Clave, GETDATE(), 0, 0, 0, 0, YEAR(GETDATE()), 'Noviembre' 	FROM Proveedores where Clave not in (select FK02_Proveedor from ObjetivoCrecimiento where Anio = YEAR(GETDATE()) AND Mes = 'Noviembre' AND FK02_Proveedor IS NOT NULL) and Habilitado = 1 ) " +
			" INSERT INTO ObjetivoCrecimiento (FK02_Proveedor, Fecha, Objetivo, ObjetivoMonto, ObjetivoFundamental, ObjetivoMontoFundamental, Anio, Mes) ( SELECT Clave, GETDATE(), 0, 0, 0, 0, YEAR(GETDATE()), 'Diciembre' 	FROM Proveedores where Clave not in (select FK02_Proveedor from ObjetivoCrecimiento where Anio = YEAR(GETDATE()) AND Mes = 'Diciembre' AND FK02_Proveedor IS NOT NULL) and Habilitado = 1 ) " ;
			 
			//log.info(query);
			
			super.jdbcTemplate.update(query,map);
			return true;
		}catch(Exception e){
			log.info(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"query: " + query);
			return false;
		}
	}

	public Double obtenerObjetivoTranscurrido(Long cliente) throws ProquifaNetException{
		StringBuilder sbObjetivo = new StringBuilder("");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cliente",cliente);
			Funcion function = new Funcion();
			int mes = Calendar.getInstance().get(Calendar.MONTH) + 1;
			String in = " ";
			for (int i = mes; i> 0; i--){
				in += "'" + function.obtenerMesConLetra(i) + "',";
			}
			in = in.substring(0, in.length() - 1);
		    sbObjetivo = new StringBuilder("SELECT  COALESCE(ROUND(SUM(Objetivo), 2),0) Objetivo FROM ObjetivoCrecimiento \n");
			sbObjetivo.append("WHERE FK01_Cliente = ").append(cliente).append(" AND Anio = YEAR(GETDATE()) AND Mes IN(").append(in).append(") \n");
			log.info(sbObjetivo.toString());
			return (Double) this.jdbcTemplate.queryForObject(sbObjetivo.toString(), map, Double.class);
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sbObjetivo: " + sbObjetivo, "cliente: " + cliente);
			return -1D;
		}
	}
	
	public Double obtenerObjetivoMontoTranscurrido(Long cliente) throws ProquifaNetException{
		StringBuilder sbObjetivo = new StringBuilder("");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cliente",cliente);
			Funcion function = new Funcion();
			int mes = Calendar.getInstance().get(Calendar.MONTH) + 1;
			String in = " ";
			for (int i = mes; i> 0; i--){
				in += "'" + function.obtenerMesConLetra(i) + "',";
			}
			in = in.substring(0, in.length() - 1);
			sbObjetivo = new StringBuilder("SELECT  COALESCE(ROUND(SUM(ObjetivoMonto), 2),0) ObjetivoMonto FROM ObjetivoCrecimiento \n");
			sbObjetivo.append("WHERE FK01_Cliente = ").append(cliente).append(" AND Anio = YEAR(GETDATE()) AND Mes IN(").append(in).append(") \n");
			log.info(sbObjetivo.toString());
			return (Double) this.jdbcTemplate.queryForObject(sbObjetivo.toString(), map, Double.class);
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sbObjetivo: " + sbObjetivo, "cliente: " + cliente);
			return -1D;
		}
	}

	@Override
	public Boolean updateObjetivoCrecimientoPorCliente(Long idCliente,  Double objetivoCrecimiento,
			Double montoAnualAnterior, Double objetivoTranscurrido, Double objetivoMontoTranscurrido)
			throws ProquifaNetException {
		String query = "";
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCliente",idCliente);
			map.put("montoAnualAnterior",montoAnualAnterior);
			map.put("objetivoTranscurrido",objetivoTranscurrido);
			map.put("objetivoMontoTranscurrido",objetivoMontoTranscurrido);
		
			
			funcion = new Funcion();
			int mes = Calendar.getInstance().get(Calendar.MONTH) + 1;
			int mesRestantes = 12 - (mes) ;
			if(montoAnualAnterior <= 0){
				montoAnualAnterior = 600.00;
			}
				for (int i = mes + 1; i <= 12; i++){
					double objetivo =  (((objetivoCrecimiento) - objetivoTranscurrido) /mesRestantes );
					if (objetivo <=  0){
						objetivo  = 0; 
					}
					double objetivoMonto  =   (((montoAnualAnterior * (1 + (objetivoCrecimiento / 100))) - objetivoMontoTranscurrido) / mesRestantes );
					if (objetivoMonto <= 0 ){
						objetivoMonto = 0;
					}
					query = "UPDATE ObjetivoCrecimiento SET Fecha = GETDATE(), " +
							"Objetivo = ROUND(" + objetivo + ",6)," +
							"ObjetivoMonto = ROUND(" + objetivoMonto + ",6) \n" +
							"WHERE FK01_Cliente = " + idCliente +" AND Mes = '"+ funcion.obtenerMesConLetra(i) + "' AND Anio = YEAR(GETDATE()) \n";
	//log.info(query);
					this.jdbcTemplate.update(query, map);
				}
				return true;
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"query: " + query, "idCliente: " + idCliente, "objetivoCrecimiento: " + objetivoCrecimiento,
					"montoAnualAnterior: " + montoAnualAnterior, "objetivoTranscurrido: " + objetivoTranscurrido,
					"objetivoMontoTranscurrido: " + objetivoMontoTranscurrido);
			return false;
		}
		
		
		
	}

	@Override
	public Double obtenerObjetivoFundamentalTranscurrido(Long cliente)
			throws ProquifaNetException {
		StringBuilder sbObjetivo = new StringBuilder();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cliente",cliente);
			Funcion function = new Funcion();
			int mes = Calendar.getInstance().get(Calendar.MONTH) + 1;
			String in = " ";
			for (int i = mes; i> 0; i--){
				in += "'" + function.obtenerMesConLetra(i) + "',";
			}
			in = in.substring(0, in.length() - 1);
			sbObjetivo = new StringBuilder("SELECT  COALESCE(ROUND(SUM(ObjetivoFundamental), 6),0) Objetivo FROM ObjetivoCrecimiento \n");
			sbObjetivo.append("WHERE FK01_Cliente = ").append(cliente).append(" AND Anio = YEAR(GETDATE()) AND Mes IN(").append(in).append(") \n");
//			log.info(sbObjetivo.toString());
			return (Double) this.jdbcTemplate.queryForObject(sbObjetivo.toString(),map,  Double.class);
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sbObjetivo: " + sbObjetivo, "cliente: " + cliente);
			return -1D;
		}
	}
	@Override
	public Double obtenerObjetivoMontoFundamentalTranscurrido(Long cliente) throws ProquifaNetException{
		StringBuilder sbObjetivo = new StringBuilder("");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cliente",cliente);
			Funcion function = new Funcion();
			int mes = Calendar.getInstance().get(Calendar.MONTH) + 1;
			String in = " ";
			for (int i = mes; i> 0; i--){
				in += "'" + function.obtenerMesConLetra(i) + "',";
			}
			in = in.substring(0, in.length() - 1);
			sbObjetivo = new StringBuilder("SELECT  COALESCE(ROUND(SUM(ObjetivoMontoFundamental), 6),0) ObjetivoMonto FROM ObjetivoCrecimiento \n");
			sbObjetivo.append("WHERE FK01_Cliente = ").append(cliente).append(" AND Anio = YEAR(GETDATE()) AND Mes IN(").append(in).append(") \n");
//			log.info(sbObjetivo.toString());
			return (Double) this.jdbcTemplate.queryForObject(sbObjetivo.toString(),map,  Double.class);
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"sbObjetivo: " + sbObjetivo, "cliente: " + cliente);
			return -1D;
		}
	}	
	
	@Override
	public Boolean updateObjetivoCrecimientoFundamentalPorCliente(Long idCliente,  Double objetivoCrecimiento,
			Double montoAnualAnterior, Double objetivoTranscurrido, Double objetivoMontoTranscurrido)
			throws ProquifaNetException {
		String query = "";
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCliente",idCliente);
			map.put("objetivoCrecimiento",objetivoCrecimiento);
			map.put("montoAnualAnterior",montoAnualAnterior);
			map.put("objetivoTranscurrido",objetivoTranscurrido);
			map.put("objetivoMontoTranscurrido",objetivoMontoTranscurrido);
			funcion = new Funcion();
			int mes = Calendar.getInstance().get(Calendar.MONTH) + 1;
			int mesRestantes = 12 - ( mes ) ;
			if(montoAnualAnterior <= 0){
				montoAnualAnterior = 600.0;
			}
				for (int i = mes+1; i <= 12; i++){
					double objetivo  =  ((objetivoCrecimiento - objetivoTranscurrido) /mesRestantes );
							if (objetivo <= 0) {
								objetivo  = 0;
							}
					double objetivoMonto =  (((montoAnualAnterior * (1 + (objetivoCrecimiento / 100))) - objetivoMontoTranscurrido) / mesRestantes );
						if (objetivoMonto <=  0){
							objetivoMonto = 0 ;
						}
					 query = "UPDATE ObjetivoCrecimiento SET Fecha = GETDATE(), " +
							"ObjetivoFundamental = ROUND(" + objetivo + ",6)," +
							"ObjetivoMontoFundamental = ROUND(" + objetivoMonto  + ",6) \n" +
							"WHERE FK01_Cliente = " + idCliente +" AND Mes = '"+ funcion.obtenerMesConLetra(i) + "' AND Anio = YEAR(GETDATE()) \n";
	//log.info(query);
					this.jdbcTemplate.update(query, map);
				}
			
			return true;
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"query: " + query, "idCliente: " + idCliente,
					"objetivoCrecimiento: " + objetivoCrecimiento, "montoAnualAnterior: " + montoAnualAnterior,
					"objetivoTranscurrido: " + objetivoTranscurrido, "objetivoMontoTranscurrido: " + objetivoMontoTranscurrido);
			return false;
		}
		
		
		
	}
	
	
	@Override
	public Double getObjetivoCrecimientoFundamentalCliente(Long idCliente)
			throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCliente",idCliente);
			
			return (Double) super.jdbcTemplate.queryForObject("SELECT coalesce (ObjetivoCrecimientoFundamental, 0) FROM Clientes WHERE Clave = " + idCliente,map,  Double.class);
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "idCliente: " + idCliente);
			return -1D;
		}
		
	}

	@Override
	public Boolean existObjetivoCrecimientoActual(Long idCliente)
			throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCliente",idCliente);
			String query = "SELECT COUNT(*) T FROM ObjetivoCrecimiento WHERE Anio = YEAR(GETDATE()) AND FK01_Cliente = " + idCliente;
			
			Integer i = super.queryForInt(query, map);
			
			if(i > 0){
				return true;
			}else{
				return false;
			}
			 
		}catch (Exception e) {
			log.info(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "idCliente: " + idCliente);
			return false;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ObjetivoCrecimiento>getMontosxNIAnterior(List<NivelIngreso> niveles, String sCondiciones) throws ProquifaNetException{
		String query = "";
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("sCondiciones",sCondiciones);
			map.put("niveles",niveles);
			funcion = new Funcion();//NIVEL.VentasUSD
			query = 
					" \n  SELECT * FROM ( SELECT  "+

							" \n SUM( " + funcion.sqlLimitesNivelIngresoCClientesNuevos(niveles, "TVentas.MontoVenta", "YEAR(GETDATE()) - 1",true, true  ) + ") AS VentasUSD, " +
							" \n  " + funcion.sqlLimitesNivelIngresoCClientesNuevos(niveles, "TVentas.MontoVenta", "YEAR(GETDATE()) - 1" , false, true ) +" AS NivelIngreso, count(cli.clave) noClientes " +
							" \n FROM Clientes AS CLI  " +
							" \n LEFT JOIN (SELECT * FROM HistorialVenta WHERE Periodo = 'Anual') TVentas ON TVentas.FK01_Cliente = CLI.Clave AND TVentas.Anio = YEAR(GETDATE()) - 1    " +
							" \n WHERE "+ funcion.obtenerEmpresasProquifa("CLI.Clave") + " AND YEAR(CLI.FechaRegistro) <> year(getdate())  " + sCondiciones + "  and cli.Rol is not null  " +
							" \n GROUP BY  " +
							" \n  " + funcion.sqlLimitesNivelIngresoCClientesNuevos(niveles, "TVentas.MontoVenta" , "YEAR(GETDATE()) - 1", false, true  )  +
							" \n   ) as a  where NivelIngreso = 'ClientesNuevos' " +
							
							" \n  union SELECT * FROM ( SELECT  " +

							" \n SUM( " + funcion.sqlLimitesNivelIngresoCClientesNuevos(niveles, "TVentas.MontoVenta", "YEAR(GETDATE()) - 1",true, false ) + ") AS VentasUSD, " +
							" \n  " + funcion.sqlLimitesNivelIngresoCClientesNuevos(niveles, "TVentas.MontoVenta", "YEAR(GETDATE()) - 1" , false, false) +" AS NivelIngreso, count(cli.clave) noClientes " +
							" \n FROM Clientes AS CLI  " +
							" \n LEFT JOIN (SELECT * FROM HistorialVenta WHERE Periodo = 'Anual') TVentas ON TVentas.FK01_Cliente = CLI.Clave AND TVentas.Anio = YEAR(GETDATE()) - 1    " +
							" \n WHERE " + funcion.obtenerEmpresasProquifa("cli.clave") + "  AND YEAR(CLI.FechaRegistro) <> year(getdate())  " + sCondiciones + "  and cli.Rol is not null  " +
							" \n GROUP BY  " +
							" \n  " + funcion.sqlLimitesNivelIngresoCClientesNuevos(niveles, "TVentas.MontoVenta" , "YEAR(GETDATE()) - 1", false, false )  +
							" \n   ) as a  where NivelIngreso <> 'ClientesNuevos' " +
							" \n ORDER BY NivelIngreso";
			log.info(query);
			return super.jdbcTemplate.query(query,map, new MontosxNIAnteriorRowMapper());
		} catch (Exception e) {
			log.info(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "query: " + query, "sCondiciones: " + sCondiciones);
			return new ArrayList<ObjetivoCrecimiento>();
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ObjetivoCrecimiento>getMontosxNIActual(List<NivelIngreso> niveles, String sCondiciones) throws ProquifaNetException{
		String sQuery = "";
		try {
			funcion = new Funcion();//NIVEL.VentasUSD
			
			sQuery = " \n SELECT " + funcion.sqlLimitesNivelIngresoCClientesNuevos(niveles, "HV.MontoVenta", "", false, true ) + " AS NivelIngreso , " ;
			sQuery += " \n CAST(COALESCE((SUM(" + funcion.sqlConversionMonedasADolar("F.Moneda", "PF.Cant", "PF.Importe", "MON", "F.TCambio", "", false) + ") ";
			sQuery += " \n - SUM(  CASE WHEN PNC.FK02_PFactura IS NOT NULL THEN " + funcion.sqlConversionMonedasADolar("NC.Moneda", "PF.Cant", "PF.Importe", "MON2", "NC.TCambio", "", false) + " ELSE 0 END ) ";
			sQuery += " \n ),0.00)  AS DECIMAL(9,2)) AS VentasUSD ,COUNT (DISTINCT (CLI.Clave )) NoClientes ";
			sQuery += " \n FROM Facturas AS F ";
			sQuery += " \n LEFT JOIN (SELECT * FROM Monedas) AS MON ON cast(MON.Fecha as date) = cast(F.Fecha as date) ";
			sQuery += " \n LEFT JOIN (SELECT * FROM Remisiones) RE ON CAST(RE.Nota AS VARCHAR(100)) = F.Factura AND F.DeRemision = 1 ";
			sQuery += " \n LEFT JOIN (SELECT * FROM PRemisiones) PRE ON PRE.Factura = RE.Factura AND PRE.FPor = RE.FPor AND PRE.CPedido = RE.CPedido ";
			sQuery += " \n LEFT JOIN (SELECT Importe, Cant, PPedido, Factura, FPor, CPedido, idPFactura FROM PFacturas) AS PF ON PF.Factura = F.Factura AND PF.FPor = F.FPor ";
			sQuery += " \n 			AND CASE WHEN F.DeRemision = 1 THEN PRE.CPedido ELSE F.CPedido END = PF.CPedido ";
			sQuery += " \n  		AND CASE WHEN F.DeRemision = 1 THEN PRE.PPedido ELSE PF.PPedido END = PF.PPedido ";
			sQuery += " \n  LEFT JOIN (SELECT * FROM PPedidos) PP ON PP.CPedido = PF.CPedido AND PP.Part = PF.PPedido ";
			sQuery += " \n  LEFT JOIN (SELECT * FROM PNotaCredito) PNC ON PNC.FK02_PFactura = PF.idPFactura  ";
			sQuery += " \n  LEFT JOIN (SELECT * FROM NotaCredito) NC ON NC.PK_Nota = PNC.FK01_Nota ";
			sQuery += " \n  LEFT JOIN (SELECT * FROM Monedas) AS MON2 ON cast(MON2.Fecha as date) = cast(NC.Fecha as date) ";
			sQuery += " \n  LEFT JOIN (SELECT * FROM Clientes) AS CLI ON CLI.Clave = F.Cliente ";
			sQuery += " \n  LEFT JOIN (SELECT SUM(ObjetivoMonto) MONTO_D, SUM(ObjetivoMontoFundamental) MONTO_F, FK01_Cliente, Anio  ";
			sQuery += " \n  		FROM ObjetivoCrecimiento GROUP BY FK01_Cliente, Anio) AS objC ON objC.FK01_Cliente = CLI.Clave AND objC.Anio = YEAR(F.Fecha)  ";
			sQuery += " \n  LEFT JOIN (SELECT * FROM HistorialVenta WHERE Periodo = 'Anual') HV ON HV.FK01_Cliente = CLI.Clave AND HV.Anio = YEAR(F.Fecha) - 1 ";
			sQuery += " \n  WHERE F.DeSistema=1 AND  F.Importe > 0 AND F.Fecha > '20091231 23:59' AND F.Estado<>'Cancelada' AND F.Estado<>'Por Cancelar' ";
			sQuery += " \n  AND " + funcion.obtenerEmpresasProquifa("F.Cliente") + " ";
			sQuery += " \n  AND F.Fecha >= CONVERT(VARCHAR,YEAR(GETDATE())) + '0101 00:00' AND F.Fecha <= CONVERT(VARCHAR,YEAR(GETDATE())) + '1231 23:59' ";  
			sQuery += " \n " + sCondiciones + "  and cli.Rol is not null  ";
			sQuery += " \n GROUP BY " + funcion.sqlLimitesNivelIngresoCClientesNuevos(niveles, "HV.MontoVenta", "", false, true  ) + " ";
			
			log.info(sQuery);
			
			return super.jdbcTemplate.query(sQuery, new MontosxNIAnteriorRowMapper());
		} catch (Exception e) {
			log.info(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "sQuery: " + sQuery, "sCondiciones: " + sCondiciones);
			return new ArrayList<ObjetivoCrecimiento>();
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ResumenVentasESAC> findobjetivoCrecimientoporESAC(String condiciones ,List<NivelIngreso> niveles,Long anio,String condicionesPeriodoHistorialVenta)
			throws ProquifaNetException {
		        String query = "";
				try {
					funcion = new Funcion();//NIVEL.VentasUSD

					query ="  DECLARE @ANIO AS INT = " + anio + "  " +
					" \n select DISTINCT  vendedor AS Usuario, g.NivelIngreso, SUM (objetivofundamental) objFundamental , SUM (Objetivodeseado ) objDeseado, SUM (VENTAS ) MontoVentaAnt from ( " +
					" \n select  f.NivelIngreso, Vendedor   ,COALESCE (f.montoVenta, 0) VENTAS , OBJ.FUNDAMENTAL/100 OBJFUNDAMENTAL, OBJ.PorcentajeAnual /100 PORCENTAJEANUAL  " +
					" \n ,coalesce((((Obj.Fundamental /100)+1)* f.montoVenta ),0)objetivoFundamental, " +
					" \n coalesce ((((Obj.PorcentajeAnual /100)+1) * f.montoVenta ),0)objetivoDeseado  , Clave   from ( " +
					" \n select cli.Clave , cli.Vendedor  , " +
					" \n SUM( " + funcion.sqlLimitesNivelIngresoCClientesNuevos(niveles, "TVentas.MontoVenta", "@anio - 1",true, false ) + ") AS MontoVenta, " +
					" \n  " + funcion.sqlLimitesNivelIngresoCClientesNuevos(niveles, "TVentas.MontoVenta", "YEAR(GETDATE()) - 1" , false,false ) +" AS NivelIngreso" +
					" \n from Clientes as cli " +
					" \n INNER join (select * from HistorialVenta where " + condicionesPeriodoHistorialVenta + " ) as TVentas on TVentas.FK01_Cliente = cli.clave AND Anio = YEAR(GETDATE()) - 1   " +   
					" \n  LEFT JOIN (SELECT * FROM Empleados) AS E ON E.Usuario = CLI.Vendedor " +
					" \n  LEFT JOIN (SELECT * FROM Jefe_Subordinado JS " +
					" \n  	LEFT JOIN (SELECT Clave, FK01_Funcion FROM Empleados WHERE FK01_Funcion <> 37) E2 ON E2.Clave = JS.FK02_Subordinado WHERE E2.Clave IS NOT NULL ) AS JS ON JS.FK02_Subordinado = E.Clave    " +   
					" \n WHERE "  + funcion.obtenerEmpresasProquifa("cli.clave") + " "+ condiciones + " " + 
					" \n AND CLI.Habilitado = 1   and cli.Rol is not null AND YEAR(CLI.FechaRegistro) <> @anio and  E.FK01_Funcion IN (5,37)  " +
					" \n GROUP BY  " + funcion.sqlLimitesNivelIngresoCClientesNuevos(niveles, "TVentas.MontoVenta" , "YEAR(GETDATE()) - 1", false , false)  +
					" \n , cli.clave, vendedor, montoVenta, cli.rol, CLI.fecharegistro " + 
					" \n ) as f left join (select * from ObjetivoCrecimiento_NI ) as obj on obj.NivelIngreso   =  f.nivelIngreso " +
					" \n ) as g GROUP BY vendedor,g.NivelIngreso";
					 
					
					
//					log.info(query);
					return super.jdbcTemplate.query(query, new ObjetivoCrecimientoporESACRowMapper());
				} catch (Exception e) {
					log.info(e.getMessage());
					funcion = new Funcion();
					funcion.enviarCorreoAvisoExepcion(e, "query: " + query, "condiciones: " + condiciones,"anio: " + anio);
					return new ArrayList<ResumenVentasESAC>();
				}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ResumenVentasESAC>findobjetivoCrecimientoporEV(String condiciones ,List<NivelIngreso> niveles,Long anio) throws ProquifaNetException {
			String query = "";	
			try {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("condiciones",condiciones);
				map.put("niveles",niveles);
				map.put("anio",anio);
				
					funcion = new Funcion();//NIVEL.VentasUSD

					query ="  DECLARE @ANIO AS INT = " + anio + "  " +
					" \n select DISTINCT  EV, g.NivelIngreso, SUM (objetivofundamental) objFundamental , SUM (Objetivodeseado ) objDeseado, SUM (VENTAS ) MontoVentaAnt from ( " +
					" \n select  f.NivelIngreso, EV ,COALESCE (f.montoVenta, 0) VENTAS , OBJ.FUNDAMENTAL/100 OBJFUNDAMENTAL, OBJ.PorcentajeAnual /100 PORCENTAJEANUAL  " +
					" \n ,coalesce((((Obj.Fundamental /100)+1)* f.montoVenta ),0)objetivoFundamental, " +
					" \n coalesce ((((Obj.PorcentajeAnual /100)+1) * f.montoVenta ),0)objetivoDeseado  , Clave   from ( " +
					" \n select cli.Clave , (CASE WHEN (cli.FK01_EV = 0 OR cli.FK01_EV IS NULL) THEN 'Sin Asignación' ELSE EV.Usuario END) EV  , " +
					" \n SUM( " + funcion.sqlLimitesNivelIngresoCClientesNuevos(niveles, "TVentas.MontoVenta", "@anio - 1",true, false ) + ") AS MontoVenta, " +
					" \n  " + funcion.sqlLimitesNivelIngresoCClientesNuevos(niveles, "TVentas.MontoVenta", "YEAR(GETDATE()) - 1" , false,false ) +" AS NivelIngreso" +
					" \n from Clientes as cli " +
					" \n left join (select * from HistorialVenta where Periodo  =  'Anual' ) as TVentas on TVentas.FK01_Cliente = cli.clave AND Anio = YEAR(GETDATE()) - 1   " +   
					" \n  LEFT JOIN (SELECT * FROM Empleados) AS EV ON EV.Clave = CLI.FK01_EV " +					 
					" \n WHERE "  + funcion.obtenerEmpresasProquifa("cli.clave") + " "+ condiciones + " " + 
					" \n AND CLI.Habilitado = 1   and cli.Rol is not null AND YEAR(CLI.FechaRegistro) <> @anio  " +
					" \n GROUP BY  " + funcion.sqlLimitesNivelIngresoCClientesNuevos(niveles, "TVentas.MontoVenta" , "YEAR(GETDATE()) - 1", false , false)  +
					" \n , cli.clave, (CASE WHEN (cli.FK01_EV = 0 OR cli.FK01_EV IS NULL) THEN 'Sin Asignación' ELSE EV.Usuario END), montoVenta, cli.rol, CLI.fecharegistro " + 
					" \n ) as f left join (select * from ObjetivoCrecimiento_NI ) as obj on obj.NivelIngreso   =  f.nivelIngreso " +
					" \n ) as g GROUP BY EV,g.NivelIngreso";				
					
//					log.info(query);
					return super.jdbcTemplate.query(query,map, new ObjetivoCrecimientoxEVRowMapper());
				} catch (Exception e) {
					log.info(e.getMessage());
					funcion = new Funcion();
					funcion.enviarCorreoAvisoExepcion(e, "query: " + query, "condiciones: " + condiciones,"anio: " + anio);
					return new ArrayList<ResumenVentasESAC>();
				}
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ResumenVentasESAC> findobjetivoCrecimientoporCartera(String condiciones ,List<NivelIngreso> niveles,Long anio,String condicionesPeriodoHistorialVentas)
			throws ProquifaNetException {
				String query  = "";
				try {
					funcion = new Funcion();//NIVEL.VentasUSD

					query ="  DECLARE @ANIO AS INT = " + anio + "  " +
					" \n select DISTINCT  nombre  AS nombre,idcartera , g.NivelIngreso, SUM (objetivofundamental) objFundamental , SUM (Objetivodeseado ) objDeseado, SUM (VENTAS ) MontoVentaAnt from ( " +
					" \n select  f.NivelIngreso, nombre, idcartera   ,COALESCE (f.montoVenta, 0) VENTAS , OBJ.FUNDAMENTAL/100 OBJFUNDAMENTAL, OBJ.PorcentajeAnual /100 PORCENTAJEANUAL  " +
					" \n ,coalesce((((Obj.Fundamental /100)+1)* f.montoVenta ),0)objetivoFundamental, " +
					" \n coalesce ((((Obj.PorcentajeAnual /100)+1) * f.montoVenta ),0)objetivoDeseado  , Clave   from ( " +
					" \n select cartera.nombre , cartera.idcartera  ,CLI.Clave , CLI.Nombre NOMBRECLIENTE  , " +
					" \n SUM( " + funcion.sqlLimitesNivelIngresoCClientesNuevos(niveles, "TVentas.MontoVenta", "@anio - 1",true, false ) + ") AS MontoVenta, " +
					" \n  " + funcion.sqlLimitesNivelIngresoCClientesNuevos(niveles, "TVentas.MontoVenta", "YEAR(GETDATE()) - 1" , false,false ) +" AS NivelIngreso" +
					" \n from Clientes as cli " +
					" \n LEFT join (select * from HistorialVenta where " + condicionesPeriodoHistorialVentas + " ) as TVentas on TVentas.FK01_Cliente = cli.clave AND Anio = (@ANIO - 1)   " +   
					" \n  LEFT JOIN (SELECT * FROM Empleados) AS E ON E.Usuario = CLI.Vendedor " +
					" \n  LEFT JOIN (SELECT * FROM Jefe_Subordinado JS " +
					" \n  	LEFT JOIN (SELECT Clave, FK01_Funcion FROM Empleados WHERE FK01_Funcion <> 37) E2 ON E2.Clave = JS.FK02_Subordinado WHERE E2.Clave IS NOT NULL ) AS JS ON JS.FK02_Subordinado = E.Clave    " + 
					" \n INNER JOIN (SELECT Nombre , PK_Cartera IDCartera , Folio , fk01_cliente   FROM Cliente_Cartera cc, Cartera c where  c.PK_Cartera  = cc.FK02_Cartera and c.Publicada =  1 ) AS cartera ON cartera.FK01_Cliente   = CLI.Clave  " +
					" \n WHERE "  + funcion.obtenerEmpresasProquifa("cli.clave") + " "+ condiciones + " " + 
					" \n AND CLI.Habilitado = 1   and cli.Rol is not null AND YEAR(CLI.FechaRegistro) <> @anio and  E.FK01_Funcion IN (5,37)  " +
					" \n GROUP BY  " + funcion.sqlLimitesNivelIngresoCClientesNuevos(niveles, "TVentas.MontoVenta" , "YEAR(GETDATE()) - 1", false , false)  +
					" \n , cli.clave, cli.nombre , vendedor, cartera.nombre , cartera.idcartera, montoVenta, cli.rol, CLI.fecharegistro " + 
					" \n ) as f left join (select * from ObjetivoCrecimiento_NI ) as obj on obj.NivelIngreso   =  f.nivelIngreso " +
					" \n ) as g GROUP BY nombre , idcartera,g.NivelIngreso";
					 
					
//					log.info(query);
					return super.jdbcTemplate.query(query, new ObjetivoCrecimientoporCarteraRowMapper());
				} catch (Exception e) {
					log.info(e.getMessage());
					funcion = new Funcion();
					funcion.enviarCorreoAvisoExepcion(e, "query: " + query, "condiciones: " + condiciones,"anio: " + anio);
					return new ArrayList<ResumenVentasESAC>();
				}
	}

	@Override
	public Boolean insertObjetivoCrecimientoProveedores(
			ObjetivoCrecimiento oc)
			throws ProquifaNetException {
		String query = "";
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("oc",oc);
		

			query ="INSERT INTO ObjetivoCrecimiento_Proveedor (Descripcion,Deseado,Fundamental,FUA) VALUES ('"+oc.getDescripcion()+"',"+oc.getPorcentaje()+","+oc.getPorcentajeFundamental()+",GETDATE())";
			super.jdbcTemplate.update(query, map);
			log.info(query);
			return true;
		}
		catch(Exception e){
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "query: " + query, oc);
			return false;
		}
	}

	@Override
	public Boolean updateObjetivoCrecimientoProveedores(
			ObjetivoCrecimiento oc)
			throws ProquifaNetException {
		String query="";
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("oc",oc);
			query ="UPDATE ObjetivoCrecimiento_Proveedor SET Deseado = "+oc.getPorcentaje()+", Fundamental = "+oc.getPorcentajeFundamental()+", FUA = GETDATE() WHERE Descripcion = '"+oc.getTipoProveedor()+"'";
			super.jdbcTemplate.update(query, map);
			log.info(query);
			return true;
		}catch(Exception e){
			new Funcion().enviarCorreoAvisoExepcion(e, "query: " + query, oc);
			return false;
		}
	}

	@Override
	public Long existObjetivoCrecimientoProveedorXTipo(String tipo)
			throws ProquifaNetException {
		String query="";
		try{
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tipo",tipo);
			query ="SELECT PK_ObjetivoCrecimientoProveedor FROM ObjetivoCrecimiento_Proveedor WHERE Descripcion = 'Normal'";
			return super.queryForLong(query, map); 
		}catch(EmptyResultDataAccessException e){
			return 0L;
		}
		catch(Exception e){
			new Funcion().enviarCorreoAvisoExepcion(e, "query: " + query, "\nTipo: "+tipo);
			return 0L;
		}
	}

	@Override
	public Double obtenerObjetivoCrecimientoProveedorXObjetivo(String tipoObjetivo, String tipoProveedor)
			throws ProquifaNetException {
		String query="";
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tipoProveedor",tipoProveedor);
			map.put("tipoObjetivo",tipoObjetivo);
		
			query ="SELECT "+tipoObjetivo+" FROM ObjetivoCrecimiento_Proveedor WHERE Descripcion = '" + tipoProveedor + "'";
			return (Double) super.jdbcTemplate.queryForObject(query,map, Double.class); 
		}catch(EmptyResultDataAccessException e){
			return 0.0;
		}
		catch(Exception e){
			new Funcion().enviarCorreoAvisoExepcion(e, "query: " + query);
			return 0.0;
		}
	}

	@Override
	public List<ObjetivoCrecimiento> getMontosPeriodoProveedor(
			String sCondiciones) throws ProquifaNetException {
		String query="";
		try{
			query =	" SELECT SUM(CASE	WHEN C.Moneda='Pesos' THEN ((PC.importe) ) / MON.PDolar " + 
 				" \n WHEN C.Moneda = 'Euros' THEN  ((PC.importe) ) * MON.EDolar   " +
 				" \n WHEN C.Moneda = 'Libras' THEN ((PC.importe) ) * MON.LDolar   " +
 				" \n WHEN C.Moneda = 'Yenes' THEN  ((PC.importe) ) * MON.YDolar   " +
 				" \n ELSE ((PC.importe) ) END) importeCompra, CASE WHEN existeRelacionComercial = 1 THEN 'ESTRATEGICO' ELSE 'NORMAL' END TIPO," +
 				" \n COUNT(DISTINCT PROVE.Clave) NoProveedor" +
 				" \n from Compras as C " +
 				" \n INNER JOIN (select (pc.Cant * pc.Costo) importe, Compra   from PCompras as PC   " +
 				" \n  			where pc.Estado <> 'Cancelado')as pc on pc.Compra = C.clave   " +
 				" \n LEFT JOIN (SELECT * FROM Monedas) AS MON ON CONVERT(DATE,MON.Fecha,112)= CONVERT(DATE,C.Fecha,112)  " +
 				" \n LEFT JOIN (SELECT Clave, existeRelacionComercial, Habilitado FROM Proveedores) PROVE ON PROVE.Clave = C.Provee" +
 				" \n WHERE " + sCondiciones +
 				" \n GROUP BY PROVE.existeRelacionComercial";
			
			//return super.jdbcTemplate.query(query, new MontoPeriodoProveedorRowMapper());
			
		}catch(Exception e){
			log.info(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "sQuery: " + query, "sCondiciones: " + sCondiciones);
			return new ArrayList<ObjetivoCrecimiento>();
		}
		return null;
	}

	
}
