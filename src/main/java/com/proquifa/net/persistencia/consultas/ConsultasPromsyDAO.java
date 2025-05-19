package com.proquifa.net.persistencia.consultas;

import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.Producto;
import com.proquifa.net.modelo.comun.Promsy;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import org.apache.xmlbeans.impl.tool.Extension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ConsultasPromsyDAO {
    int unidadesVendidasTrimestre (Parametro parametro) throws ProquifaNetException;
    String obtenerIdProveedor(Parametro parametro)throws ProquifaNetException;

    BigDecimal montosVendidosTrimestre(Parametro parametro) throws ProquifaNetException;

   List<Promsy> comparativaTrimestres(Parametro parametro) throws ProquifaNetException;
   List<Promsy> comparativaProveedorVSTodos(Parametro parametro)throws ProquifaNetException;
   List<Map<String,Object>>top5ProductosMasVendidos(Parametro parametro)throws  ProquifaNetException;
   List <Map<String,Object>>unidadesVendidasPorMes(Parametro parametro)throws  ProquifaNetException;
   Boolean descontarProductosCaducadosStock ()throws ProquifaNetException;

   List <Map <String, Object>> reporteComercial()throws ProquifaNetException;

}
