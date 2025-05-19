package com.proquifa.net.persistencia.comun;

import java.util.List;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.cobrosypagos.facturista.AsignacionFolio;
import com.proquifa.net.modelo.cobrosypagos.facturista.ConceptoFactura;
import com.proquifa.net.modelo.comun.Empresa;
import com.proquifa.net.modelo.comun.Importacion;

/**
 * @author Jhidalgo
 *
 */


public interface EmpresaDAO {
	
	/**
	 * Obtiene la informacion de las todas las empresas del grupo
	 * @return <List>Empresa 
	 */

	List<Empresa> findInfoEmpresa();
	
	
	/**
	 * Obtiene los folios  de la empresa, los que estan como asignados, el folio inicial, el folio final, quien los asigno, de que empresa son, el tipo
	 * @param idEmpresa
	 * @return <List>AsignacionFolio 
	 */
	
	List<AsignacionFolio> findObtenerFolios(Integer idEmpresa);	
	
	/**
	 * Asigna folios a la empresa 
	 * @param idEmpresa, idEmpleado, folioInicial,folioFinal
	 * @return void
	 */
	
	Boolean asignarFolioEmpresa(AsignacionFolio folio);
	
	/**
	 * Obtiene los conceptos de la empresa
	 * @param idEmpresa
	 * @return <List> 
	 */
	
	List<ConceptoFactura> findObtenerConceptoEmpresa(Integer idEmpresa);
	
	/**
	 * Agregar concepto a empresa
	 * @param idEmpresa, descripcion
	 * @return 
	 */
	
	Integer agregarConceptoEmpresa(ConceptoFactura concepto);
	
	/**
	 * Eliminar concepto factura
	 * @param idEmpresa,
	 * @return 
	 */
	
	Boolean eliminarConceptoEmpresa(int idConcepto);
	
	/**
	 * Asigna el tipo de faturacion para la empresa
	 * @param idEmpresa,
	 * @return boolean
	 */
	Boolean asignarTipoDeFacturacion(Integer idEmpresa, Boolean facElecronica,
			Boolean facMatriz);
	
	/**
	 * @param idEmpresa
	 * @param vendedor
	 * @param comprador
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean asignarRoles(Importacion importacion) throws ProquifaNetException;
	/***
	 * 
	 * @param idEmpresa
	 * @return
	 * @throws ProquifaNetException
	 */
	Empresa getInformacionEmpresaPorId(Long idEmpresa) throws ProquifaNetException;
	/***
	 * 
	 * @param empresa
	 * @return
	 * @throws ProquifaNetException
	 */
	Empresa getInformacionEmpresaPorAlias(String empresa) throws ProquifaNetException;
	/***
	 * 
	 * @param rol
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Empresa> findEmpresasPorRol(String rol) throws ProquifaNetException;


	/**
	 * @param compra 
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Empresa> getEmpresa(Integer compra) throws ProquifaNetException;

}
