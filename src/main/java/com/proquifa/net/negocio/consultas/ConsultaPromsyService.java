package com.proquifa.net.negocio.consultas;

import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.Promsy;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ConsultaPromsyService {
    Integer unidadesVendidasTrimestre(Parametro parametro)throws ProquifaNetException ;
    BigDecimal montosVendidosTrimestre(Parametro parametro)throws ProquifaNetException;
    List<Promsy>comparativaTrimestre(Parametro parametro)throws ProquifaNetException;
    List<Promsy>comparativaProveedorVSTodos(Parametro parametro)throws ProquifaNetException;
    List<Map<String, Object>> top5ProductosMasVendidos(Parametro parametro)throws ProquifaNetException;
    List<Map<String, Object>> unidadesVendidasPorMes(Parametro parametro)throws ProquifaNetException;
    boolean descontarProductosCaducadosStock () throws  ProquifaNetException;
    void pruebaFactura(String uuid);
    List<Map<String, Object>>reporteComercial()throws ProquifaNetException;
}
