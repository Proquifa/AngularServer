package com.proquifa.net.persistencia.finanzas.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.finanzas.Account;
import com.proquifa.net.modelo.finanzas.Paybook;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.finanzas.PaybookDAO;

@Repository
public class PaybookDAOImpl extends DataBaseDAO implements PaybookDAO {
	
	final Logger log = LoggerFactory.getLogger(PaybookDAOImpl.class);

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Paybook> obtenerTransactions(Date inicio, Date fin) throws ProquifaNetException{
		try{
			String query = "SELECT ts.* , acs.account, acs.description as company FROM transactions ts LEFT JOIN Accounts acs ON ts.id_account = acs.id_account "
					+ " WHERE dt_refresh BETWEEN " + (inicio.getTime() / 1000) + " AND " + (fin.getTime() / 1000);

			Map<String, Object> parametros = new HashMap<String, Object>();
			//parametros.put("FInicio", inicio.getTime() / 1000);
			//parametros.put("FFin", fin.getTime() / 1000);
			
			log.info(query);

			
			List<Paybook> mapReturn = new ArrayList<Paybook>();

			getJdbcTemplate().query(query,parametros ,new RowMapper() {

				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					Paybook paybook = new Paybook();
					paybook.setIdTransaction(rs.getString("id_transaction"));
					paybook.setIdUser(rs.getString("id_user"));
					paybook.setIdSite(rs.getString("id_site"));
					paybook.setIdSiteOrganization(rs.getString("id_site_organization"));
					paybook.setIdSiteOrganizationType(rs.getString("id_site_organization_type"));
					paybook.setIdAccount(rs.getString("id_account"));
					paybook.setIdAccountType(rs.getString("id_account_type"));
					paybook.setDisable(rs.getBoolean("is_disable"));
					paybook.setDescription(rs.getString("description"));
					paybook.setAmount(rs.getDouble("amount"));
					paybook.setDtTransaction(rs.getLong("dt_transaction"));
					paybook.setDtRefresh(rs.getLong("dt_refresh"));
					paybook.setCaptionExtra(rs.getString("caption_Extra"));
					paybook.setOrderExtra(rs.getInt("order_Extra"));
					paybook.setActivo(rs.getBoolean("Activo"));
					paybook.setIdRegistroDiario(rs.getInt("id_RegistroDiario"));
					paybook.setCurrency(rs.getString("currency"));
					
					Account account = new Account();
					account.setAccount(rs.getString("account"));
					account.setDescription(rs.getString("company"));
					
					paybook.setAccount(account);					
					
					mapReturn.add(paybook);
					return null;
				}    	   
			});

			return mapReturn; 	
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		} 	
	}

}
