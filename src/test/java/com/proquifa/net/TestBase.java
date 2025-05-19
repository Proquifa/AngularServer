package com.proquifa.net;

import static org.junit.Assert.*;

import com.proquifa.net.persistencia.consultas.impl.ConsultasPromsyDAOImpl;
import org.junit.Test;

public class TestBase {

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	@Test
	public void pruebas (){
		ConsultasPromsyDAOImpl consulta = new ConsultasPromsyDAOImpl();
		consulta.productosACaducar();
	}
}
