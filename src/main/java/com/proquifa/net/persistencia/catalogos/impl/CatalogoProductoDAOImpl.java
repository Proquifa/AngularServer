package com.proquifa.net.persistencia.catalogos.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.comun.Producto;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.catalogos.CatalogoProductoDAO;

@Repository
public class CatalogoProductoDAOImpl extends DataBaseDAO implements CatalogoProductoDAO{
	
	final Logger log = LoggerFactory.getLogger(CatalogoProductoDAOImpl.class);
	
	public boolean actualizarTransitoMexico(Producto producto) throws ProquifaNetException{
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("transitoMandatorioMexico", producto.isTransitoMandatorioMexico());
			map.put("idProducto", producto.getIdProducto());
			
			log.info("Entró actualizarTransitoMexico");
			log.info ("Variable: " + producto.isTransitoMandatorioMexico());
			log.info ("idProducto: " + producto.getIdProducto());
			StringBuilder sbQuery = new StringBuilder("UPDATE Productos SET TransitoMandatorioMexico= :transitoMandatorioMexico WHERE idProducto= :idProducto");
			super.jdbcTemplate.update(sbQuery.toString(), map);			
			return true;
		} catch (Exception e) {
			log.info(e.getMessage());			
			return false;
		}
	}

}
