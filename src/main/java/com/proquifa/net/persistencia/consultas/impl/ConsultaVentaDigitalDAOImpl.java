package com.proquifa.net.persistencia.consultas.impl;

import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.consultas.ConsultasVentasDigitalDAO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.*;
@Repository
public class ConsultaVentaDigitalDAOImpl extends DataBaseDAO implements ConsultasVentasDigitalDAO {
    @Override
    public List<Map<String, Object>> trackingOrderByItems(String CPedido) {
        try {
            String Query="select 'True' as PedidoConfirmado, P.FPedido as FechaPedidoConfirmado, case when  C.Fecha  is null then 'False' else 'True' end as EnCompra, C.Fecha as FechaEnCompra,\n" +
                    "\n" +
                    "case when COALESCE(CASE WHEN C.AlmacenUSA = 0 THEN PEN.FFin ELSE EPC2.FFin END,C.FECHA) is null then 'False' else 'True' end as EnCaminoAlmacenMatriz,\n" +
                    "COALESCE(CASE WHEN C.AlmacenUSA = 0 THEN PEN.FFin ELSE EPC2.FFin END,C.FECHA) AS FechaEnCaminoAlmacenMatriz,\n" +
                    "case when COALESCE(EPC1.FInicio, INS.Fecha) is null then 'False' else 'True' end as AlmacenMatriz,\n" +
                    "COALESCE(EPC1.FInicio, INS.Fecha) AS FechaAlmacenMatriz,\n" +
                    "case when (EPC1.Tipo='Rechazada' or EPC2.Tipo='Rechazada')  then  'True' else 'False' end as RechazadoEnInspección,\n" +
                    "case when (EPC1.Tipo='Rechazada' or EPC2.Tipo='Rechazada')  then COALESCE(EPC1.FFin,EPC2.FFin) else null end as FechaRechazadoEnInspección,\n" +
                    "Case When PAC.FUA is null then 'False' else 'True' end as GenerarAvisoCambios,\n" +
                    "PAC.FUA as FechaGenerarAvisoCambios,\n" +
                    "Case When PP.Estado='Entregado' Then 'True' else 'False' end as PedidoEntregado,\n" +
                    "Coalesce (Pend.FFin,RutaEjecutar.FFin,RutaTrabajar.FFin) as FechaEntregado,\n" +
                    "PP.Part as Partida\n" +
                    "\t\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "from Pedidos as P\n" +
                    "Left Join PPedidos as PP on PP.CPedido= P.CPedido\n" +
                    "Left Join PCompras as PC on PC.CPedido = PP.CPedido and PC.PPedido = PP.Part\n" +
                    "Left Join Compras as C on C.PK_Compra= PC.FK02_Compra\n" +
                    "LEFT JOIN (SELECT idPCompra, FInicio,FFin,tipo FROM EstadoPCompra WHERE Tipo='En inspección') AS EPC1 ON EPC1.idPCompra = PC.idPCompra\n" +
                    " LEFT JOIN (SELECT idPCompra, FFin, tipo FROM EstadoPCompra WHERE Tipo='En inspección Phs' ) AS EPC2 ON EPC2.idPCompra = PC.idPCompra\n" +
                    " LEFT JOIN (SELECT Fecha, idPCompra FROM InspeccionOC) AS INS ON INS.idPCompra = PC.idPCompra\n" +
                    " LEFT JOIN (SELECT Docto, FFin, Responsable FROM Pendiente WHERE Tipo = 'OC por confirmar' AND Responsable IS NOT NULL AND Responsable <> 'null') AS PEN ON PEN.Docto = C.Clave\n" +
                    " LEFT JOIN PAviso_Cambios as PAC on PAC.FK01_PCompras=PC.idPCompra\n" +
                    " LEFT JOIN EmbalarPedido as EP on EP.FK01_PPedido = PP.idppedido\n" +
                    " LEFT JOIN PackingList as PL ON PL.Folio COLLATE SQL_Latin1_General_CP1_CI_AS = EP.FolioTemporal\n" +
                    " LEFT JOIN RutaDP as rdp on rdp.FK02_PackingList = PL.PK_PackingList\n" +
                    "\n" +
                    " \n" +
                    " \n" +
                    " LEFT JOIN Pendiente as Pend ON   Pend.Docto=PL.Folio\n" +
                    " LEFT JOIN (SELECT Docto,FFin,Tipo, Responsable FROM Pendiente WHERE (Tipo like 'Ruta a ejecutar%')) AS RutaEjecutar ON  RutaEjecutar.Docto IN (rdp.idEntrega,rdp.idRuta)\n" +
                    " LEFT JOIN (SELECT Docto,FFin,Tipo FROM Pendiente WHERE FFin IS NULL AND Tipo LIKE 'Ruta a trabajar%') AS RutaTrabajar ON   RutaTrabajar.Docto=rdp.idRuta\n" +
                    " \n" +
                    "where P.CPedido=:CPedido" +
                    "\n" +
                    "order by PP.Part ";


            Map<String, Object> map = new HashMap<String, Object>();
            map.put("CPedido", CPedido);

            List<Map<String, Object>> resultados = new ArrayList<>();
            jdbcTemplate.query(Query, map, new RowMapper() {
                @Override
                public List<Map<String,Object>> mapRow(ResultSet rs, int rowNum) throws SQLException {

                    Map<String, Object> fila = new LinkedHashMap<>();
                  

                    fila.put("PedidoConfirmado", rs.getString("PedidoConfirmado"));
                    fila.put("FechaPedidoConfirmado",fechaUTC( rs.getString("FechaPedidoConfirmado")));
                    fila.put("EnCompra", rs.getString("EnCompra"));
                    fila.put("FechaEnCompra",fechaUTC(rs.getString("FechaEnCompra")));
                    fila.put("EnCaminoAlmacenMatriz",rs.getString("EnCaminoAlmacenMatriz"));
                    fila.put("FechaEnCaminoAlmacenMatriz",fechaUTC(rs.getString("FechaEnCaminoAlmacenMatriz")));
                    fila.put("AlmacenMatriz",rs.getString("AlmacenMatriz"));
                    fila.put("FechaAlmacenMatriz",fechaUTC(rs.getString("FechaAlmacenMatriz")));
                    fila.put("RechazadoEnInspección",rs.getString("RechazadoEnInspección"));
                    fila.put("FechaRechazadoEnInspección",fechaUTC(rs.getString("FechaRechazadoEnInspección")));
                    fila.put("GenerarAvisoCambios",rs.getString("GenerarAvisoCambios"));
                    fila.put("FechaGenerarAvisoCambios",fechaUTC(rs.getString("FechaGenerarAvisoCambios")));
                    fila.put("PedidoEntregado",rs.getString("PedidoEntregado"));
                    fila.put("FechaEntregado",fechaUTC(rs.getString("FechaEntregado")));
                    fila.put("Partida",rs.getString("Partida"));




                    resultados.add(fila);

                    //mapReturn.add(producto);
                    return resultados;
                }
            });
        return resultados;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String fechaUTC(String fecha){
        System.out.println("Fecha: " + fecha);
        String fechaFormateada="";
        if (fecha==null || fecha.equals("null")){
           fechaFormateada="";
        }else{
        LocalDateTime localDateTime = LocalDateTime.parse(fecha.replace(" ", "T"));

        // Convertir a UTC
        ZonedDateTime FechaUTC = localDateTime.atZone(ZoneOffset.UTC);
        fechaFormateada=FechaUTC.toString();}

        return  fechaFormateada;
    }
}
