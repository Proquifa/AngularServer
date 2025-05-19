package com.proquifa.net.negocio.consultas.impl;


import com.proquifa.net.negocio.consultas.ConsultaVentaDigitalService;
import com.proquifa.net.persistencia.consultas.ConsultasVentasDigitalDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service("consultaVentaDigitalServiceImpl")
public class ConsultaVentaDigitalServiceImpl implements ConsultaVentaDigitalService {
    @Autowired
    ConsultasVentasDigitalDAO consultasVentasDigitalDAO;
    @Override
    public List<Map<String, Object>> trackingOrderByItems(String CPedido) {
        try{
            return  consultasVentasDigitalDAO.trackingOrderByItems(CPedido);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
