package com.proquifa.net.persistencia.cobrosypagos.proforma.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.cobrosypagos.proforma.ProformaDAO;

@Repository
public class ProformaDAOImpl extends DataBaseDAO implements ProformaDAO {

	final Logger log = LoggerFactory.getLogger(ProformaDAOImpl.class);

	@Override
	public String findFolioProforma(String condiciones) throws ProquifaNetException{
		try{
			String query = "SELECT top 1 Pro.Clave FROM Proforma Pro " +
							"LEFT JOIN (SELECT * FROM Pedidos) AS Pd ON Pro.FK02_Pedido = Pd.idPedido " +
							"LEFT JOIN (SELECT * FROM Facturas) AS F ON F.idFactura = pro.FK01_Factura " +
							"WHERE " + condiciones;
			
//			logger.info(query);
		return (String) super.queryForString(query);
		}catch(EmptyResultDataAccessException e){
			return "";
		}
		catch (Exception e){
			log.info(e.getMessage());
			return null;
		}
	}

}
