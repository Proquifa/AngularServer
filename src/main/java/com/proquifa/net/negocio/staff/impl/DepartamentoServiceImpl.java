package com.proquifa.net.negocio.staff.impl;

import java.util.ArrayList;
import java.util.List;
 
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.staff.Departamento;
import com.proquifa.net.negocio.staff.DepartametoService;
import com.proquifa.net.persistencia.staff.DepartamentoDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("departamentoService")
public class DepartamentoServiceImpl implements DepartametoService{
	
	@Autowired
	DepartamentoDAO departamentoDAO;
	
	public List<Departamento> getConsultaDepartamento() throws ProquifaNetException{
		
		List<Departamento> departamentos = new ArrayList<Departamento>();

		List<Departamento> departamentosProquifa = departamentoDAO.consultaDepartamento();
		List<Departamento> departamentosRyndem = new ArrayList<Departamento>();
		List<Departamento> departamentosGDL = new ArrayList<Departamento>();
		
		departamentos.addAll(departamentosProquifa);
		
		for (Departamento departamentoRyndem : departamentosRyndem) {
			boolean repetido = false;
			for (Departamento departamento : departamentos) {
				if (departamentoRyndem.getClaveDepartamento().equals(departamento.getClaveDepartamento())
						&& departamentoRyndem.getNombreDepartamento().equals(departamento.getNombreDepartamento())) {
					repetido = true;
					
				}
			}
			if (!repetido) {
				departamentos.add(departamentoRyndem);
			}
		}
		
		for (Departamento departamentoGDL : departamentosGDL) {
			boolean repetido = false;
			for (Departamento departamento : departamentos) {
				if (departamentoGDL.getClaveDepartamento().equals(departamento.getClaveDepartamento())
						&& departamentoGDL.getNombreDepartamento().equals(departamento.getNombreDepartamento())) {
					repetido = true;
					
				}
			}
			if (!repetido) {
				departamentos.add(departamentoGDL);
			}
		}
		
		
		
		
		return this.departamentoDAO.consultaDepartamento();
		
		
	}
	
}
