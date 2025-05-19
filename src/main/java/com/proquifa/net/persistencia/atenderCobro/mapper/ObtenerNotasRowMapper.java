package com.proquifa.net.persistencia.atenderCobro.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.ibm.icu.text.DecimalFormat;
import com.proquifa.net.modelo.catalogos.cobrosypagos.facturista.Factura;
import com.proquifa.net.modelo.cobrosypagos.facturista.NotaCredito;

public class ObtenerNotasRowMapper implements RowMapper {
	
	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		NotaCredito n = new NotaCredito();
		DecimalFormat df = new DecimalFormat("#.##");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy", new Locale("es", "ES"));
		Map<String, List<NotaCredito>> mapReturn = new HashMap<String, List<NotaCredito>>();
		String empresa =  rs.getString("Fpor");
		n.setFactura(rs.getLong("FK02_Factura"));
		if(rs.getDouble("TotalM") > 0 )
//		n.setmOMoneda(Double.parseDouble(df.format(rs.getDouble("TotalM"))));
		if(rs.getString("Moneda") != null && (rs.getString("Moneda").equalsIgnoreCase("Dolares") || rs.getString("Moneda").equalsIgnoreCase("Dolar")) ) {
			n.setMoneda("USD");
		}else {
			n.setMoneda("MXN");
		}
		if(rs.getDouble("TCambio") > 0 ) {
			n.setTCambio(Double.parseDouble(rs.getString("TCambio")));
		}else {
			n.setTCambio(0.0);
		}
		if(rs.getString("Fecha") != null ) 
			n.setFechaNota(dateFormat.format(rs.getDate("Fecha")));
		if(rs.getDouble("TDolar") > 0)
//		n.setmDolar(Double.parseDouble(rs.getString("TDolar"))); 
		if(rs.getDouble("TotalDolares") > 0)
//		n.settDolares(Double.parseDouble(rs.getString("TotalDolares")));
		
		
		 if( empresa != null && empresa.equalsIgnoreCase("Proquifa") ){
			   if(mapReturn.get("Proquifa") != null){
				   mapReturn.get("Proquifa").add(n);
			   }
  		   else {
  			   List<NotaCredito> list = new ArrayList<NotaCredito>();
  			   mapReturn.put("Proquifa", list );
  			   mapReturn.get("Proquifa").add(n);
  		   }
		   }
		   if(empresa != null && empresa.equalsIgnoreCase("Proveedora")){
			   if(mapReturn.get("Proveedora") != null){
				   mapReturn.get("Proveedora").add(n);
			   }
  		   else {
  			   List<NotaCredito> list = new ArrayList<NotaCredito>();
  			   mapReturn.put("Proveedora", list );
  			   mapReturn.get("Proveedora").add(n);
  		   }
		   }
		   if(empresa != null && empresa.equalsIgnoreCase("Golocaer")){
			   if(mapReturn.get("Golocaer") != null){
				   mapReturn.get("Golocaer").add(n);
			   }
  		   else {
  			   List<NotaCredito> list = new ArrayList<NotaCredito>();
  			   mapReturn.put("Golocaer", list );
  			   mapReturn.get("Golocaer").add(n);
  		   }
		   }
		   if(empresa != null && empresa.equalsIgnoreCase("Mungen")){
			   if(mapReturn.get("Mungen") != null){
				   mapReturn.get("Mungen").add(n);
			   }
  		   else {
  			   List<NotaCredito> list = new ArrayList<NotaCredito>();
  			   mapReturn.put("Mungen", list );
  			   mapReturn.get("Mungen").add(n);
  		   }
		   }
		   if(empresa != null && empresa.equalsIgnoreCase("Pharma")){
			   if(mapReturn.get("Pharma") != null){
				   mapReturn.get("Pharma").add(n);
			   }
  		   else {
  			   List<NotaCredito> list = new ArrayList<NotaCredito>();
  			   mapReturn.put("Pharma", list );
  			   mapReturn.get("Pharma").add(n);
  		   }
		   }
		   if(empresa != null && empresa.equalsIgnoreCase("Pharma")){
			   if(mapReturn.get("Pharma") != null){
				   mapReturn.get("Pharma").add(n);
			   }
  		   else {
  			   List<NotaCredito> list = new ArrayList<NotaCredito>();
  			   mapReturn.put("Pharma", list );
  			   mapReturn.get("Pharma").add(n);
  		   }
		   }

		
		return mapReturn;
	}

}
