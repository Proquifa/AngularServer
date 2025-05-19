/**
 * 
 */
package com.proquifa.net.persistencia.comun;


import com.proquifa.net.modelo.comun.Comentario;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;

/**
 * @author misael.camanos
 *
 */
public interface ComentarioDAO {
	/**
	 * @param comentario
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean insertarComentario(Comentario comentario) throws ProquifaNetException;
	/**
	 * @param comentario
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean actualizarComentario(Comentario comentario) throws ProquifaNetException;
	/**
	 * @param idComentario
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean eliminarComentario(Long idComentario) throws ProquifaNetException;
}
