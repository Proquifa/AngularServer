/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.Contacto;
import com.proquifa.net.modelo.comun.Direccion;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author jhidalgo
 *
 */
public class ContactoPorClienteRowMapper implements RowMapper {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Contacto conta= new Contacto();
		conta.setDireccion(new Direccion());
		
		//para cliente
		conta.setSector(rs.getString("Sector"));
		conta.setNivelIngresos(rs.getString("NivelIngreso"));
		conta.setNombreEsac(rs.getString("EsacNombre"));
		conta.setIdEmpleadoEsac(rs.getInt("EsacClave"));

		//para contacto
		conta.setIdContacto(rs.getLong("ContaClave"));
		conta.setNombre(rs.getString("ContactoNombre"));
		conta.setEMail(rs.getString("eMail"));
		conta.setTelefono(rs.getString("Tel1"));
		conta.setTelefonoN(rs.getString("Tel2"));
		conta.setFax(rs.getString("Fax"));
		conta.setIdEmpresa(rs.getLong("idEmpresa"));
		conta.setEmpresa(rs.getString("ClienteNombre"));
		conta.setTotalContactos(rs.getInt("tContactos"));
		conta.setTotalDirecciones(rs.getInt("tDirecciones"));
		conta.setTotalZonas(rs.getInt("tZonas"));
		conta.getDireccion().setIdLugar(rs.getInt("idHorario"));
		conta.getDireccion().setCalle(rs.getString("Calle"));
		conta.getDireccion().setMunicipio(rs.getString("Municipio"));
		conta.getDireccion().setEstado(rs.getString("Estado"));
		conta.getDireccion().setPais(rs.getString("Pais"));
		conta.getDireccion().setCodigoPostal(rs.getString("CP"));
		conta.getDireccion().setDomicilio(rs.getString("Domicilio").trim().substring(1).trim());
		conta.getDireccion().setZonaMensajeria(rs.getString("ZonaDireccion"));
		
		String industriaC="",nDesicionC="",rolC="",nivelPuesto="",puesto="",titulo="",departamento="";
		industriaC=rs.getString("Industria");
		nDesicionC=rs.getString("NivelDecision");
		rolC=rs.getString("Rol");
		nivelPuesto=rs.getString("NivelPuesto");
		puesto=rs.getString("Puesto");
		titulo=rs.getString("Titulo");
		departamento=rs.getString("Depto");
		
		if(industriaC==null || industriaC.equals("") || industriaC.equals("--NINGUNO--") ){conta.setIndustria("ND");}else{conta.setIndustria(industriaC);}
		if(nDesicionC==null || nDesicionC.equals("")){conta.setNivelDecision("ND");}else{conta.setNivelDecision(nDesicionC);}
		if(rolC==null || rolC.equals("") || rolC.equals("--NINGUNO--")){conta.setRol("ND");}else{conta.setRol(rolC);}
		if(puesto==null || puesto.equals("")){conta.setPuesto("ND");}else{conta.setPuesto(puesto);}
		if(nivelPuesto==null || nivelPuesto.equals("")){conta.setNivelPuesto("ND");}else{conta.setNivelPuesto(nivelPuesto);}
		if(titulo==null || titulo.equals("")){conta.setTitulo("ND");}else{conta.setTitulo(titulo);}
		if(departamento==null || departamento.equals("")){conta.setDepartamento("ND");}else{conta.setDepartamento(departamento);}

		return conta;
	}
}