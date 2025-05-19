package com.proquifa.net.persistencia.comun.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.Corporativo;
import com.proquifa.net.modelo.comun.Industria;
import com.proquifa.net.modelo.comun.NivelIngreso;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.comun.CorporativoDAO;
import com.proquifa.net.persistencia.comun.impl.mapper.ClaveClienteRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.ClienteRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.CorporativoRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.IndustriaCorporativoRowMapper;


@Repository
public class CorporativoDAOImpl extends DataBaseDAO implements CorporativoDAO {

	Funcion funcion;
	
	final Logger log = LoggerFactory.getLogger(CorporativoDAOImpl.class);
	
	@Override
	public Long insertarCorporativo(Corporativo corporativo) throws ProquifaNetException{
		StringBuilder sbQuery = new StringBuilder("");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("corporativo", corporativo);
			Object param[] = {corporativo.getNombre(),corporativo.getAreaCorporativo()};
			sbQuery = new StringBuilder("INSERT INTO Corporativo(Nombre, fua,Area) VALUES(?,GETDATE(),?)");
			log.info(sbQuery.toString());
			super.jdbcTemplate.update(sbQuery.toString(), map);
			return super.queryForLong("SELECT IDENT_CURRENT ('Corporativo')", map);
		} catch (Exception e) {
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "sbQuery: " + sbQuery, corporativo);
			return -1L;
		}
		
	}

	@Override
	public boolean updateCorporativo(Corporativo corporativo) throws ProquifaNetException {
		StringBuilder sbQuery = new StringBuilder();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("corporativo", corporativo);
			Object param[] = {corporativo.getNombre(), corporativo.getIdCorporativo()};
			sbQuery = new StringBuilder("UPDATE Corporativo SET Nombre = ?, fua = GETDATE() WHERE PK_Corporativo = ?");
			super.jdbcTemplate.update(sbQuery.toString(), map);
			return true;
		} catch (Exception e) {
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "sbQuery: " + sbQuery, corporativo);
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> obtenerClientesXCorporativo(Long idCorporativo)
			throws ProquifaNetException {
		StringBuilder sbQuery = new StringBuilder("");
		try {
			
		
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCorporativo", idCorporativo);
			sbQuery = new StringBuilder("SELECT clave FROM Clientes WHERE FK02_Corporativo = ").append(idCorporativo);
			//log.info(sbQuery);
			return super.jdbcTemplate.query(sbQuery.toString(), map, new ClaveClienteRowMapper());
		} catch (Exception e) {
			//logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "sbQuery: " + sbQuery, "idCorporativo: " + idCorporativo);
			return new ArrayList<Cliente>();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Corporativo> obtenerCorporativos(List<NivelIngreso> niveles) throws ProquifaNetException {
		String query = "";
		try {
			Funcion f = new Funcion();
			String sql = f.sqlLimitesNivelIngreso(niveles, "NIVEL.VentasUSD");
			
			query = 
							" \n SELECT C.PK_Corporativo, C.Nombre Corporativo, C.fua, C.Area, " +
							" \n CASE WHEN Sector = 'PUBLICO' THEN 'Publico' ELSE CASE WHEN Rol = 'DISTRIBUIDOR' THEN 'Distribuidor' ELSE " + sql + 
							" \n END END NIVEL, CL.*, COB.Usuario UsuarioCobrador, ev.NombreEV " +
							" \n FROM Corporativo C " +
							" \n LEFT JOIN Clientes CL ON CL.FK02_Corporativo = C.PK_Corporativo " +
							" \n LEFT JOIN (SELECT COALESCE(SUM(CASE WHEN F.Moneda = 'Dolares' OR F.Moneda ='USD' THEN F.Importe " +
							" \n			WHEN F.Moneda = 'EUR' OR F.Moneda = 'Euros' THEN F.Importe * M.EDolar " +
							" \n			WHEN F.Moneda = 'Pesos' OR F.Moneda = 'M.N.' THEN F.Importe/CASE WHEN F.TCambio = 0 OR F.TCAMBIO IS NULL THEN 1 ELSE F.TCAMBIO END END ), 0) VentasUSD, Cliente FROM Facturas F " +
							" \n 			LEFT JOIN Monedas M ON M.Fecha = F.Fecha " +
							" \n 			WHERE YEAR(F.Fecha) = DATEPART(year,GETDATE()) - 1 AND F.Estado NOT LIKE 'Cancela%' GROUP BY F.Cliente) NIVEL ON NIVEL.Cliente = Cl.Clave " +
							" \n LEFT JOIN (SELECT Usuario, Clave FROM Empleados) COB ON COB.Clave = CL.Cobrador" +
							" \n LEFT JOIN (SELECT Usuario NombreEV, Clave idev FROM Empleados) EV ON EV.idev = CL.FK01_EV  " +
							" \n ORDER BY C.Nombre \n";
	
				return super.jdbcTemplate.query(query, new CorporativoRowMapper());
			
		} catch (Exception e) {
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "query: " + query);
			return new ArrayList<Corporativo>();
		}
	}

	@Override
	public boolean eliminarCorporativo(Long idCorporativo)
			throws ProquifaNetException {
		StringBuilder sbQuery = new StringBuilder("");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCorporativo", idCorporativo);
			sbQuery = new StringBuilder("DELETE FROM Corporativo WHERE PK_Corporativo = ").append(idCorporativo);
			super.jdbcTemplate.update(sbQuery.toString(), map);
			return true;
		} catch (Exception e) {
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "sbQuery: " + sbQuery);
			return false;
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> obtenerClientesPorCorporativo(Long corporativo, List<NivelIngreso> niveles)
			throws ProquifaNetException {
		String sql = "";
		try {
			Funcion f = new Funcion();
			sql = f.sqlLimitesNivelIngreso(niveles, "NIVEL.VentasUSD");
			StringBuilder sbQuery = new StringBuilder( " SELECT CASE WHEN Sector = 'PUBLICO' THEN 'Publico' ELSE CASE WHEN Rol = 'DISTRIBUIDOR' THEN 'Distribuidor' ELSE ").append(sql).append(" END END NIVEL, * FROM Clientes C " +
					" \n LEFT JOIN (SELECT COALESCE(SUM(CASE WHEN F.Moneda = 'Dolares' OR F.Moneda ='USD' THEN F.Importe " +
					" \n WHEN F.Moneda = 'EUR' OR F.Moneda = 'Euros' THEN F.Importe * M.EDolar " +
					" \n WHEN F.Moneda = 'Pesos' OR F.Moneda = 'M.N.' THEN F.Importe/CASE WHEN F.TCambio = 0 OR F.TCAMBIO IS NULL THEN 1 ELSE F.TCAMBIO END END ), 0) VentasUSD, Cliente FROM Facturas F " +
					" \n LEFT JOIN Monedas M ON M.Fecha = F.Fecha " +
					" \n WHERE YEAR(F.Fecha) = DATEPART(year,GETDATE()) - 1 AND F.Estado NOT LIKE 'Cancela%' GROUP BY F.Cliente) NIVEL ON NIVEL.Cliente = C.Clave " +
					" WHERE C.Habilitado =  1 AND FK02_Corporativo = ").append(corporativo);
			//logger.info("Query busqueda de clientes ------>:   "+sbQuery.toString());
			return super.jdbcTemplate.query(sbQuery.toString(), new ClienteRowMapper());
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "sql: " + sql, "corporativo: " + corporativo);
			return new ArrayList<Cliente>();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Industria> findIndustriasCorporativo(Long idCorporativo)
			throws ProquifaNetException {
		String query = "";
		try{
		  query = 	" \n SELECT * " +
							" \n FROM Industrias_Corporativo " +
							" \n WHERE FK02_Corporativo = " + idCorporativo;
				
			return super.jdbcTemplate.query(query, new IndustriaCorporativoRowMapper());
			
		}catch (Exception e) {
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "query: " + query, "idCorporativo: " + idCorporativo);
			return new ArrayList<Industria>();
		}
	}

	@Override
	public Boolean insertarModificacionesCorporativo(Long usuario,	Long idCorporativo, String modificaciones, String llamadoPor)
			throws ProquifaNetException {
		StringBuilder sbQuery = null;
				try {
					
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("idCorporativo", idCorporativo);
					map.put("modificaciones", modificaciones);
					map.put("llamadoPor", llamadoPor);
					
					if (idCorporativo == 0)  { 
						sbQuery = new  StringBuilder("insert into Modificacion (Fecha , VentanaOrigen , Modificacion , FK01_Empleado) values (GETDATE() ,'").append(llamadoPor).append("','").append(modificaciones).append("',").append(usuario).append(")");
					}else {
						sbQuery = new  StringBuilder("insert into Modificacion (Fecha , VentanaOrigen , Modificacion , FK01_Empleado, Docto ) values (GETDATE() ,'").append(llamadoPor).append("','").append(modificaciones).append("',").append(usuario).append(",").append(idCorporativo).append(")");
					}
					//logger.info(sbQuery.toString());
					super.jdbcTemplate.update(sbQuery.toString(), map);
					return true;
				} catch (Exception e) {
				//	logger.error(e.getMessage());
					funcion = new Funcion();
					funcion.enviarCorreoAvisoExepcion(e, "sbQuery: " + sbQuery, "usuario: " + usuario,"llamadoPor: " + llamadoPor,
							"modificaciones: " + modificaciones, "idCorporativo: " + idCorporativo,"modificaciones: " + modificaciones);
					return false;
				}
	}

	@Override
	public Long getCorporativoPorCliente(Long idCliente)
			throws ProquifaNetException {
		String sql = "";
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCliente", idCliente);
	
			sql = "SELECT FK02_Corporativo FROM Clientes WHERE Clave = " + idCliente ;
			//logger.info(sql);
			return super.queryForLong(sql, map);
			
		}catch(Exception e){
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "sql: " + sql, "idCliente: " + idCliente);
			return -1L;
		}
	}
}
