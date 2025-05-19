package com.proquifa.net.persistencia.consultas;

import java.util.List;
import java.util.Map;

public interface ConsultasVentasDigitalDAO {

    List<Map<String, Object>> trackingOrderByItems(String CPedido);
}
