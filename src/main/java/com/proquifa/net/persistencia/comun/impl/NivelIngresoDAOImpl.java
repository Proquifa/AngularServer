package com.proquifa.net.persistencia.comun.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.comun.NivelIngreso;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.comun.NivelIngresoDAO;
import com.proquifa.net.persistencia.comun.impl.mapper.NivelIngresoRowMapper;


@Repository
public class NivelIngresoDAOImpl extends DataBaseDAO implements NivelIngresoDAO {
	private Funcion f;
	Funcion funcion;
	
	final Logger log = LoggerFactory.getLogger(NivelIngresoDAOImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<NivelIngreso> findLimitesNivelIngreso() throws ProquifaNetException {
		String query = "";
		try{
			f = new Funcion();
			 query =	"SELECT * FROM NivelIngreso ORDER BY MAX DESC";
			 //logger.info(query);
			return super.jdbcTemplate.query(query, new NivelIngresoRowMapper());
		
		}catch (Exception e){
			log.info(e.getMessage());
			f.enviarCorreoAvisoExepcion(e, "");
			return new ArrayList<NivelIngreso>();
		}
	}

	@Override
	public boolean crearNivelIngreso(NivelIngreso nivelIngreso) throws ProquifaNetException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateNivelIngreso(NivelIngreso nivelIngreso) throws ProquifaNetException {
		// TODO Auto-generated method stub
		return false;
	}

}
