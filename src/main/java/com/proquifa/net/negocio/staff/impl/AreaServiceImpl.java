package com.proquifa.net.negocio.staff.impl;

import java.util.ArrayList;
import java.util.List;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.staff.Area;
import com.proquifa.net.negocio.staff.AreaService;
import com.proquifa.net.persistencia.staff.AreaDAO;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("areaService")
public class AreaServiceImpl implements AreaService{
	
	@Autowired
	AreaDAO areaDAO;
	
	public List<Area> getConsultaArea() throws ProquifaNetException{
		
		List<Area> areas = new ArrayList<Area>();
		
		List<Area> areasProquifa = this.areaDAO.consultaArea();
		List<Area> areasRyndem = this.areaDAO.consultaAreaRyndem();
		List<Area> areasGDL = this.areaDAO.consultaAreaGDL();
		
		try {
			areasProquifa = this.areaDAO.consultaArea();
			areasRyndem = this.areaDAO.consultaAreaRyndem();
			areasGDL = this.areaDAO.consultaAreaGDL();
		} catch (Exception e) {}
		
		
		
		if (areasProquifa != null && areasProquifa.size() > 0) {
			areas.addAll(areasProquifa);
		}
		
		if (areasRyndem != null && areasRyndem.size() > 0) {
			for (Area areaRyndem: areasRyndem) {
				boolean repetido = false;
				for (Area area : areas) {
					if (areaRyndem.getClaveArea().equals(area.getClaveArea()) 
							&& areaRyndem.getNombreArea().equals(area.getNombreArea())){
						repetido = true;
					}
				}
				if (!repetido) {
					areas.add(areaRyndem);
				}
			}
		}
		
		if (areasGDL != null && areasGDL.size() > 0) {
			for (Area areaGDL: areasGDL) {
				boolean repetido = false;
				for (Area area : areas) {
					if (areaGDL.getClaveArea().equals(area.getClaveArea()) 
							&& areaGDL.getNombreArea().equals(area.getNombreArea())){
						repetido = true;
					}
				}
				if (!repetido) {
					areas.add(areaGDL);
				}
			}
		}
		
		
		
		
		return areas;
	}
	
}
