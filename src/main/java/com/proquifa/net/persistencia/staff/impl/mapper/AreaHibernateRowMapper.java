package com.proquifa.net.persistencia.staff.impl.mapper;

import java.util.ArrayList;
import java.util.List;

import com.proquifa.net.modelo.staff.Area;

public class AreaHibernateRowMapper {
	
	public static List<Area> mapearArea(List<Object[]> list){
		List<Area> areas = new ArrayList<Area>();
		for (Object[] object : list){
			Area area = new Area();
			area.setClaveArea(object[0].toString());
			area.setNombreArea(object[1].toString());
			areas.add(area);
		}
		return areas;
	}

}
