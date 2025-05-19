/**
 * 
 */
package com.proquifa.net.negocio.ventas.admoncomunicacion;

import java.util.List;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.DocumentoRecibido;
import com.proquifa.net.modelo.comun.Parametro;

/**
 * @author ernestogonzalezlozada
 *
 */
/**
 * @author bryan.magana
 *
 */
public interface RecepcionDocumentosService {
	/**
	 * Metodo con el cual se da de alta un documento recibido y se detonan pendientes a partir de el
	 * @param documentoRecibido
	 * @throws ProquifaNetException
	 */
	Long registrarDocumentoRecibido(DocumentoRecibido documentoRecibido) throws ProquifaNetException;
	/**
	 * Metodo que obtiene un documento recibido mediante su numero
	 * @param numero
	 * @return
	 */
	DocumentoRecibido buscarDocumentoRecibidoPorNumero(String numero);
	/**
	 * Metodo con el cual se obtiene un Documento recibido a traves del folio 
	 * @param folio
	 * @throws ProquifaNetException
	 */
	List<DocumentoRecibido> buscarDocumentoRecibidoPorFolio(String folio,boolean porFolio) throws ProquifaNetException;
	/**
	 * Metodo con el cual se actualiza un documento recibido
	 * @param documentoNuevo
	 * @throws ProquifaNetException
	 */
	void actualizarDocumentoRecibido(DocumentoRecibido documentoNuevo) throws ProquifaNetException;
	
	/**
	 * Metodo con el cual se se obtiene un Documento recibido
	 * @param documentoNuevo
	 * @throws ProquifaNetException
	 */
	List<DocumentoRecibido> buscarDocumentosXBA(Parametro paramBusqueda) throws ProquifaNetException;
	/**
	 * 
	 * @param Referencia
	 * @return
	 * @throws ProquifaNetException
	 */
	List<DocumentoRecibido> buscarDocumentosRecibidosPorReferencia(String Referencia) throws ProquifaNetException;
	/**
	 * 
	 * @param Folio
	 * @return
	 * @throws ProquifaNetException
	 */
	List<DocumentoRecibido> buscarDocumentosRecibidosPorFolio(String Folio) throws ProquifaNetException;
	/**
	 * 
	 * @param DoctoR
	 * @return
	 * @throws ProquifaNetException 
	 */
	boolean generarPendienteClienteDeshabilitado(String DoctoR) throws ProquifaNetException;
	
	/**
	 * @param campaa
	 * @param folio
	 * @return
	 * @throws ProquifaNetException
	 */
	int validarFolioCampana (String campana , String folio )throws ProquifaNetException;
	
	/**
	 * @param documentoRecibido
	 * @return
	 * @throws ProquifaNetException
	 */
	Long generaCotizacionCampana (DocumentoRecibido documentoRecibido)throws ProquifaNetException;
}
