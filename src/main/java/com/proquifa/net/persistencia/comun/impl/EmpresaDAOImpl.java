package com.proquifa.net.persistencia.comun.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.cobrosypagos.facturista.AsignacionFolio;
import com.proquifa.net.modelo.cobrosypagos.facturista.ConceptoFactura;
import com.proquifa.net.modelo.comun.Empresa;
import com.proquifa.net.modelo.comun.Importacion;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.cobrosypagos.facturista.impl.mapper.ConceptofacturaRowMapper;
import com.proquifa.net.persistencia.cobrosypagos.facturista.impl.mapper.infoEmpresaFacturaRowMapper;
import com.proquifa.net.persistencia.comun.EmpresaDAO;
import com.proquifa.net.persistencia.comun.impl.mapper.AsignacionFolioRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.InformacionEmpresaRowMapper;
/**
 * @author Jhidalgo
 *
 */

@Repository
public class EmpresaDAOImpl extends DataBaseDAO implements EmpresaDAO {

	final Logger log = LoggerFactory.getLogger(EmpresaDAOImpl.class);

	public Integer agregarConceptoEmpresa(ConceptoFactura concepto) {
		try{
            Map<String, Object> map = new HashMap<String, Object>();
			Object params []={concepto.getDescripcion(),concepto.getIdEmpresa(),concepto.getClaveUnidad(),concepto.getClaveProdServ()};

			String query ="INSERT INTO ConceptoFactura (Descripcion,FK01_Empresa,FK02_ClaveUnidad,FK03_ClaveProdServ) VALUES ";
			query +="(?,?,?,?)";
			super.jdbcTemplate.update(query, map);

			int idConcepto = super.queryForInt("SELECT IDENT_CURRENT ('ConceptoFactura')");

			return idConcepto;
		}catch(RuntimeException e){
			return -1;
		}
	}


	public Boolean asignarFolioEmpresa(AsignacionFolio folio) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			Object params []= {folio.getFolioInicio(),folio.getFolioFinal(),folio.getIdEmpresa(),folio.getIdEmpleado(),folio.getTipo()};
			String query = "INSERT INTO AsignacionFolio (Fecha,FolioInicio,FolioFinal,FK01_Empresa,FK02_Empleado,Tipo,Asignado) VALUES ";
			query+="(GETDATE(),?,?,?,?,?,'1')";
			super.jdbcTemplate.update(query, map);
			int idFolio = super.queryForInt("SELECT IDENT_CURRENT ('AsignacionFolio')");
			//			log.info("idFolio: " + idFolio);
			Map<String, Object> map2 = new HashMap<String, Object>();
			super.jdbcTemplate.update("UPDATE AsignacionFolio SET Asignado=0 WHERE FK01_Empresa ="+folio.getIdEmpresa()+" AND PK_Asignacion <>"+idFolio +"AND Tipo='"+folio.getTipo()+"'", map2);

			return true;
		}catch(RuntimeException e){
			return false;
		}
	}



	@SuppressWarnings("unchecked")
	public List<ConceptoFactura> findObtenerConceptoEmpresa(Integer idEmpresa) {
		try{
			String query="SELECT * FROM ConceptoFactura WHERE FK01_Empresa ="+ idEmpresa; 

			List<ConceptoFactura> con = super.jdbcTemplate.query(query,new ConceptofacturaRowMapper());
			return con;
		}catch(RuntimeException e){
			//logger.info("Error: " + e.getMessage());
			return null;
		}
	}


	@SuppressWarnings("unchecked")
	public List<AsignacionFolio> findObtenerFolios(Integer idEmpresa) {

		try{
			String query="" +
					"	SELECT EMP.PK_Empresa,EMP.Alias,CONF.Valor-(ASIF.FolioInicio-1) AS FConsumidos,(ASIF.FolioFinal-ASIF.FolioInicio)+1 AS RFAsignados,ASIF.*,EMPLF.Nombre AS NEmpleado" +
					"	FROM Empresa AS EMP" +
					"	LEFT JOIN(SELECT PK_Empresa,CASE WHEN Alias='Proquifa Servicios'" +
					"								THEN 'FProquifaServicios'" +
					"								WHEN Alias = 'Proquifa El Salvador'" +
					"								THEN 'FProquifaElSalvador'" +
					"								ELSE" +
					"									'F'+ Alias" +
					"								END COLLATE Modern_Spanish_CI_AS AS PF FROM EMPRESA ) AS CPCONSE ON CPCONSE.PK_Empresa = EMP.PK_Empresa" +
					"	LEFT JOIN(SELECT Concepto,Valor FROM Consecutivos) AS CONF ON CONF.Concepto= CPCONSE.PF" +
					"	LEFT JOIN (SELECT * FROM AsignacionFolio WHERE Tipo = 'Factura') AS ASIF ON ASIF.FK01_Empresa = EMP.PK_Empresa" +
					"	LEFT JOIN(SELECT Nombre,Clave FROM Empleados) AS EMPLF ON ASIF.FK02_Empleado= EMPLF.Clave" +
					"	WHERE ASIF.FK01_Empresa = '"+idEmpresa+"'" +
					"	UNION" +
					"	SELECT EMP.PK_Empresa,EMP.Alias,CONNC.Valor-(ASINC.FolioInicio-1) AS FConsumidosF,(ASINC.FolioFinal-ASINC.FolioInicio)+1 AS RFAsignadosF,ASINC.*,EMPLNC.Nombre AS NEmpleadoF" +
					"	FROM Empresa AS EMP" +
					"	LEFT JOIN(SELECT PK_Empresa,CASE WHEN Alias='Proquifa Servicios'" +
					"								THEN 'Nota de credito ProquifaServicios'" +
					"								WHEN Alias = 'Proquifa El Salvador'" +
					"								THEN 'Nota de credito ProquifaElSalvador'" +
					"								ELSE" +
					"									'Nota de credito '+ Alias" +
					"								END COLLATE Modern_Spanish_CI_AS AS PNC FROM EMPRESA ) AS CPCONSE ON CPCONSE.PK_Empresa = EMP.PK_Empresa" +
					"	LEFT JOIN(SELECT Concepto,Valor FROM Consecutivos) AS CONNC ON CONNC.Concepto = CPCONSE.PNC" +
					"	LEFT JOIN (SELECT * FROM AsignacionFolio WHERE Tipo = 'Nota credito') AS ASINC ON ASINC.FK01_Empresa = EMP.PK_Empresa" +
					"	LEFT JOIN(SELECT Nombre,Clave FROM Empleados) AS EMPLNC ON ASINC.FK02_Empleado= EMPLNC.Clave" +
					"	WHERE ASINC.FK01_Empresa = '"+idEmpresa+"' ORDER BY Asignado DESC";

			List<AsignacionFolio> folios = super.jdbcTemplate.query(query, new AsignacionFolioRowMapper());


			return folios;
		}catch(RuntimeException e){

			//logger.info("Error: " + e.getMessage());
			return null;
		}

	}


	public Boolean eliminarConceptoEmpresa(int idConcepto) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			String query ="DELETE ConceptoFactura WHERE PK_Concepto ="+idConcepto;

			super.jdbcTemplate.update(query, map);

			return true;
		}catch(RuntimeException e){
			return false;
		}
	}

	public Boolean asignarTipoDeFacturacion(Integer idEmpresa,
			Boolean facElecronica, Boolean facMatriz) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			String query=
					"UPDATE Empresa SET FacturacionElectronica ='"+facElecronica+"' , FacturacionMatriz ='"+facMatriz+"' WHERE PK_Empresa = '"+idEmpresa+"'";

			super.jdbcTemplate.update(query, map);

			return true;
		}catch(RuntimeException e){
			e.printStackTrace();
			return false;
		}
	}

	public Boolean asignarRoles(Importacion importacion) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			String query=
					"UPDATE Empresa SET Vende ='"+importacion.getVende()+"' , Compra ='"+importacion.getCompra()+"'  "
							+ " ,Importador ='" + importacion.getImporta() + "' ,Exportador ='"  + importacion.getExporta() + "' , Padron_Importador = '" + importacion.getPadronImportacion()
							+ "' WHERE PK_Empresa = " + importacion.getIdEmpresa();

			log.info(query);
			super.jdbcTemplate.update(query, map);

			return true;
		}catch(RuntimeException e){
			e.printStackTrace();
			return false;
		}
	}


	@SuppressWarnings("unchecked")
	public List<Empresa> findInfoEmpresa() {
		//logger.info("Seleccionando empresas: SELECT *,NULL AS FActual,NULL AS FUsadosMes, NULL AS TOTALMONTODLL, NULL AS TOTCANCELADAS, NULL AS FoliosAsignados, NULL AS FacHab FROM Empresa");
		return super.jdbcTemplate.query("SELECT *,NULL AS FActual,NULL AS FUsadosMes, NULL AS TOTALMONTODLL, NULL AS TOTCANCELADAS, NULL AS FoliosAsignados, NULL AS FacHab FROM Empresa", new infoEmpresaFacturaRowMapper());
	}

	@Override
	public Empresa getInformacionEmpresaPorId(Long idEmpresa)
			throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			String query = "SELECT *,NULL AS FActual,NULL AS FUsadosMes, NULL AS TOTALMONTODLL, NULL AS TOTCANCELADAS, NULL AS FoliosAsignados, NULL AS FacHab FROM Empresa where PK_Empresa IS NULL OR PK_Empresa = " + idEmpresa;
			//logger.info(query);
			return (Empresa) super.jdbcTemplate.queryForObject(query, map, new infoEmpresaFacturaRowMapper());	
		}catch(Exception e){
			// logger.error(e.getMessage());
			return null;
		}
	}


	@Override
	public Empresa getInformacionEmpresaPorAlias(String empresa)
			throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			String query = "SELECT *,NULL AS FActual,NULL AS FUsadosMes, NULL AS TOTALMONTODLL, NULL AS TOTCANCELADAS, NULL AS FoliosAsignados, NULL AS FacHab FROM Empresa where Alias = '" + empresa + "'";
			//logger.info(query);
			return (Empresa) super.jdbcTemplate.queryForObject(query, map, new infoEmpresaFacturaRowMapper());
		}catch(Exception e){
			//logger.error(e.getMessage());
			return null;
		}

	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Empresa> findEmpresasPorRol(String rol)
			throws ProquifaNetException {
		try{

			String query = "SELECT * FROM Empresa where  " + rol + " = 1";
			//logger.info(query);
			return super.jdbcTemplate.query(query, new InformacionEmpresaRowMapper());
		}catch(Exception e){
			// logger.error(e.getMessage());
			return null;
		}
	}

	
	@Override
	public List<Empresa> getEmpresa(Integer compra) throws ProquifaNetException {
		try {
			Map<String, Object> parametros = new HashMap<String, Object>();
			StringBuilder sbQuery = new StringBuilder("SELECT PK_Empresa idEmpresa, Prefijo nomenclaturaEmpresa, Alias, RFC rfcEmpresa, RazonSocial FROM Empresa \n");
			sbQuery.append("WHERE Activo = 1 AND Compra = :compra \n");
			sbQuery.append("ORDER BY Alias \n");
			parametros.put("compra", compra);
			return jdbcTemplate.query(sbQuery.toString(), parametros, new BeanPropertyRowMapper<>(Empresa.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


}
