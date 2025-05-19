package com.proquifa.net.negocio.consultas;

import java.util.List;
import java.util.Map;

public interface ConsultaVentaDigitalService {

    List<Map<String, Object>>trackingOrderByItems(String CPedido);
}
