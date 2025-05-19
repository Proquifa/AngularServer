package com.proquifa.net;

import com.proquifa.net.persistencia.consultas.impl.ConsultasPromsyDAOImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProquifaNetApplicationTests {

	@Test
	public void contextLoads() {
		ConsultasPromsyDAOImpl consulta = new ConsultasPromsyDAOImpl();
		consulta.productosACaducar();
	}

}
