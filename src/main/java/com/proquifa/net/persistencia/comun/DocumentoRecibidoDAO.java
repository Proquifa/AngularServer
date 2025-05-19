/**
 * 
 */
package com.proquifa.net.persistencia.comun;

import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.cobrosypagos.facturista.PartidaFactura;
import com.proquifa.net.modelo.comun.DoctoCotizacion;
import com.proquifa.net.modelo.comun.DocumentoRecibido;
import com.proquifa.net.modelo.comun.NivelIngreso;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;

/**
 * @author ernestogonzalezlozada
 *
 */
public interface DocumentoRecibidoDAO {
	/**validarF
	 * Metodo que registra un DocumentoRecibido en la base de datos
	 * @param doctoRecibido
	 * @return
	 */
	Long insertarDocumentoRecibido(DocumentoRecibido doctoRecibido);
	
	/**
	 * Metodo que obtiene un documento recibido mediante su numero
	 * @param numero
	 * @return
	 */
	DocumentoRecibido obtenerDocumentoRecibidoPorNumero(String numero);
	
	/**
	 * Metodo que obtiene un documento recibido mediante su folio
	 * @param folio
	 * @return
	 */
	
	List<DocumentoRecibido> obtenerDocumentoRecibidoPorFolio(String folio,boolean porFolio);
	/**
	 * Obtener numero de documento recibido mediante un folio 
	 * @param folio
	 * @return
	 */
	
	Long obtenerNumeroDoctoRXFolio(Long folio);
	/**
	 * Metodo que actualiza un documento recibido
	 * @param documentoNuevo
	 */
	
	void actualizarDocumentoRecibido(DocumentoRecibido documentoNuevo);
	/**
	 * Metodo para obtener una lista de documentos recibidos.
	 */
	List<DocumentoRecibido> obtenerDocumentosXBA(Date finicio, Date ffin,String restriccion,String abierCerrado1, String abierCerrado2,String abierCerrado3,String abierCerrado4,String abiertoCerrado, List<NivelIngreso> niveles);
	/**
	 * Metodo que obtiene el tiempo proceso de Recibido
	 * 
	 */
	DocumentoRecibido obtenerTiempoProcesoDoctosR(Long folio);
	/**
	 * 
	 * @param Referencia
	 * @return
	 */
	List<DocumentoRecibido> obtenerDocumentosRecibidosPorReferencia(String Referencia);
	/**
	 * 	
	 * @param Folio
	 * @return
	 */
	List<DocumentoRecibido> obtenerDocumentosRecibidosPorFolio(String Folio) throws ProquifaNetException;
	/**
	 * Se genera el pendiente de Habilitar cliente
	 * @param Folio
	 * @return
	 */
	Integer generarPendienteClienteDeshabilitado(String DoctoR, String fecha);
	
	/**
	 * Se valida si el folio existe en la campaa establecida y si ha sido utilizado con anterioridad.
	 * @param campana
	 * @param folio
	 * @return
	 */
	Integer getValidarFolioCampana (String campana , String folio);
	
	/*
	 * Agregar cotizacion para la campania.
	 * @param documentoRecibido
	 * @return
	 */
	long agregarCotizacionCampana (DocumentoRecibido documentoRecibido);
	
	DoctoCotizacion obtenerInfoCotizacionCampana (Long cotiza);
	List<PartidaFactura> obtenerInfoPartidasCampana(Long cotiza);
	List<PartidaFactura> obtenerInfoComplementoCampana(Long cotiza);
}