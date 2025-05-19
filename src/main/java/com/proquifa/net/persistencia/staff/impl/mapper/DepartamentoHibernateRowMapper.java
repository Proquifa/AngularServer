package com.proquifa.net.persistencia.staff.impl.mapper;

import java.util.ArrayList;
import java.util.List;

import com.proquifa.net.modelo.staff.Departamento;

public class DepartamentoHibernateRowMapper {

	public static List<Departamento> mapearDepartamento(List<Object[]> list){
		List<Departamento> departamentos = new ArrayList<Departamento>();
		for (Object[] object : list){
			Departamento departamento = new Departamento();
			departamento.setClaveDepartamento(object[0].toString());
			departamento.setNombreDepartamento(object[1].toString());
			departamentos.add(departamento);
		}
		return departamentos;
	}
	
}
