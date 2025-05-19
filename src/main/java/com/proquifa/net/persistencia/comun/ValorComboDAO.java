package com.proquifa.net.persistencia.comun;

import java.util.List;

import com.proquifa.net.modelo.comun.ValorCombo;

public interface ValorComboDAO {
	/***
	 * 
	 * @param tipo
	 * @param concepto
	 * @return
	 */
	ValorCombo obtenerValorCombo(String tipo, String concepto);

	List<ValorCombo> obtenerValorCombo(String concepto); 

}
