/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl;



import com.proquifa.net.modelo.comun.Comentario;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.comun.ComentarioDAO;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * @author misael.camanos
 *
 */
@Repository
public class ComentarioDAOImpl extends DataBaseDAO  implements ComentarioDAO{

	Funcion funcion;
	
	@Override
	public boolean insertarComentario(Comentario comentario) throws ProquifaNetException {
		StringBuilder sbQuery = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("comentario", comentario);
			Object[] param=new Object[0];
			if(comentario.getIdCliente()>0){//Comentarios para clientes
				param=new Object[]{comentario.getIdCliente(),comentario.getSeccion(),comentario.getTema(),comentario.getContenido()};
				sbQuery = new StringBuilder("INSERT INTO Comentarios(FK01_Cliente, Seccion,Tema,Contenido) VALUES(?,?,?,?)");
			}
//			log.info(sbQuery);
			super.jdbcTemplate.update(sbQuery.toString(), map);
			return true;
		} catch (Exception e) {
			//logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "sbQuery: " + sbQuery, "comentario: " + comentario);
			return false;
		}
	}

	@Override
	public boolean actualizarComentario(Comentario comentario) throws ProquifaNetException {
		StringBuilder sbQuery = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("comentario", comentario);
			Object[] param=new Object[0];
			if(comentario.getIdCliente()>0){//Comentarios para clientes
				param = new Object[]{comentario.getTema(),comentario.getContenido(),comentario.getIdComentario()};
				sbQuery = new StringBuilder("UPDATE Comentarios SET  Tema = ?, Contenido = ? WHERE PK_Comentario = ?");
			}
			super.jdbcTemplate.update(sbQuery.toString(), map);
			return true;
		} catch (Exception e) {
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "sbQuery: " + sbQuery, "comentario: " + comentario);
			return false;
		}
	}

	@Override
	public boolean eliminarComentario(Long idComentario) throws ProquifaNetException {
		StringBuilder sbQuery= new StringBuilder("");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idComentario", idComentario);
			sbQuery = new StringBuilder("DELETE FROM Comentarios WHERE PK_Comentario = ").append(idComentario);
			super.jdbcTemplate.update(sbQuery.toString(), map);
			return true;
		} catch (Exception e) {
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "sbQuery: " + sbQuery, "idComentario: " + idComentario);
			return false;
		}
	}
}
