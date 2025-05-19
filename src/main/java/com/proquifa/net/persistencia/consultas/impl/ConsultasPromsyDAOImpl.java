package com.proquifa.net.persistencia.consultas.impl;

import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.Producto;
import com.proquifa.net.modelo.comun.Promsy;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.consultas.ConsultasPromsyDAO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class ConsultasPromsyDAOImpl extends DataBaseDAO implements ConsultasPromsyDAO {
    @Override
    public int unidadesVendidasTrimestre(Parametro parametro) throws ProquifaNetException {
        try {

            String sbQuery = "\n";
            sbQuery += "SELECT  SUM(PF.Cant) AS PIEZAS\n" +
                        consulta(parametro);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("tipoProducto", parametro.getTipoProducto());
            map.put("subtipo", parametro.getSubTipo());
            map.put("control", parametro.getControl());
            map.put("Proveedor", obtenerIdProveedor(parametro));
            System.out.println(sbQuery);
            return super.jdbcTemplate.queryForObject(sbQuery,map, Integer.class);
        }catch(Exception e){
e.printStackTrace();
        }
        return 0;
    }

    @Override
    public String obtenerIdProveedor(Parametro parametro) throws ProquifaNetException {
        try {
            String sbQuery = "\n";

            sbQuery +="select top 1 clave from Proveedores where Nombre=:Nombre";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("Nombre", parametro.getNombreProveedor());
            return  super.jdbcTemplate.queryForObject(sbQuery,map, String.class);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public BigDecimal montosVendidosTrimestre(Parametro parametro) throws ProquifaNetException {
        try {

            String sbQuery = "\n";
            sbQuery += "SELECT  SUM(PF.Cant * ( CASE  WHEN F.Moneda = 'Pesos' OR F.Moneda = 'MXN' OR F.Moneda = 'M.N.' OR F.Moneda = 'M.N'\tTHEN ((PF.Importe) /  COALESCE(CASE WHEN F.TCambio = 0 THEN MON.PDolar ELSE F.TCambio END , MON.PDolar))\n" +
                    " WHEN F.Moneda = 'Euros' OR F.Moneda = 'EUR'\t\t THEN PF.Importe * (MON.EDolar)\n" +
                    " WHEN F.Moneda = 'Libras' THEN PF.Importe * (MON.LDolar)\n" +
                    " WHEN F.Moneda = 'DlCan'  THEN PF.Importe * (MON.DDolar)\n" +
                    " WHEN F.Moneda = 'Yenes'  THEN PF.Importe * (MON.YDolar)\n" +
                    " ELSE PF.Importe END )) AS MONTO\n" +
                    consulta(parametro);
                /*   " FROM Facturas AS F\n" +
                    " LEFT JOIN (SELECT * FROM Remisiones) RE ON CAST(RE.Nota AS VARCHAR(100)) = F.Factura AND F.DeRemision = 1\n" +
                    " LEFT JOIN (SELECT * FROM PRemisiones) PRE ON PRE.Factura = RE.Factura AND PRE.FPor = RE.FPor AND PRE.CPedido = RE.CPedido\n" +
                    " LEFT JOIN (SELECT * FROM PFacturas) AS PF ON PF.Factura = F.Factura AND PF.FPor = F.FPor\n" +
                    "\t\tAND CASE WHEN F.DeRemision = 1 THEN PRE.CPedido ELSE F.CPedido END = PF.CPedido\n" +
                    "\t\tAND CASE WHEN F.DeRemision = 1 THEN PRE.PPedido ELSE PF.PPedido END = PF.PPedido\n" +
                    " LEFT JOIN (SELECT * FROM Monedas) AS MON ON MON.Fecha = F.Fecha\n" +
                    " LEFT JOIN (SELECT * FROM PPedidos) AS PP ON PP.CPedido = PF.CPedido AND PP.Part = PF.PPedido\n" +
                    " LEFT JOIN Productos as Prod on Prod.idProducto=PP.FK02_Producto\n" +
                    " WHERE  PF.Importe > 0\n" +
                    " AND F.Estado<>'Cancelada' AND F.Estado<>'Por Cancelar'\n" +
                    " AND F.Fecha >='"+parametro.getStartDate()+ "' and F.Fecha <= '"+parametro.getFinalDate()+"' \n" ;
            if(parametro.getTipoProducto().equals("Labware")){
                sbQuery += " AND Prod.tipo =:tipoProducto and Prod.subtipo is Null and Prod.control is Null and Prod.Proveedor=:Proveedor";
            }else {
                sbQuery += " AND Prod.tipo =:tipoProducto and Prod.subtipo=:subtipo and Prod.control=:control and Prod.Proveedor=:Proveedor";
            }*/
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("tipoProducto", parametro.getTipoProducto());
            map.put("subtipo", parametro.getSubTipo());
            map.put("control", parametro.getControl());
            map.put("Proveedor", obtenerIdProveedor(parametro));
            System.out.println(sbQuery);

           return new BigDecimal( super.jdbcTemplate.queryForObject(sbQuery, map, String.class));
        }catch(Exception e){
            e.printStackTrace();

        }
        return new BigDecimal(0);
    }

    @Override
    public List<Promsy> comparativaTrimestres(Parametro parametro) {
        try {
            String proveedor = obtenerIdProveedor(parametro);

                String sbQuery = "\n";
                sbQuery += "SELECT  SUM(PF.Cant) as PIEZAS,SUM(PF.Cant * ( CASE  WHEN F.Moneda = 'Pesos' OR F.Moneda = 'MXN' OR F.Moneda = 'M.N.' OR F.Moneda = 'M.N'\tTHEN ((PF.Importe) /  COALESCE(CASE WHEN F.TCambio = 0 THEN MON.PDolar ELSE F.TCambio END , MON.PDolar))\n" +
                        " WHEN F.Moneda = 'Euros' OR F.Moneda = 'EUR'\t\t THEN PF.Importe * (MON.EDolar)\n" +
                        " WHEN F.Moneda = 'Libras' THEN PF.Importe * (MON.LDolar)\n" +
                        " WHEN F.Moneda = 'DlCan'  THEN PF.Importe * (MON.DDolar)\n" +
                        " WHEN F.Moneda = 'Yenes'  THEN PF.Importe * (MON.YDolar)\n" +
                        " ELSE PF.Importe END )) AS MONTO,'Actual' as Periodo\n" +
                        consulta(parametro);
                sbQuery += "\nUNION ALL\n";
                sbQuery += "SELECT  SUM(PF.Cant) as PIEZAS,SUM(PF.Cant * ( CASE  WHEN F.Moneda = 'Pesos' OR F.Moneda = 'MXN' OR F.Moneda = 'M.N.' OR F.Moneda = 'M.N'\tTHEN ((PF.Importe) /  COALESCE(CASE WHEN F.TCambio = 0 THEN MON.PDolar ELSE F.TCambio END , MON.PDolar))\n" +
                        " WHEN F.Moneda = 'Euros' OR F.Moneda = 'EUR'\t\t THEN PF.Importe * (MON.EDolar)\n" +
                        " WHEN F.Moneda = 'Libras' THEN PF.Importe * (MON.LDolar)\n" +
                        " WHEN F.Moneda = 'DlCan'  THEN PF.Importe * (MON.DDolar)\n" +
                        " WHEN F.Moneda = 'Yenes'  THEN PF.Importe * (MON.YDolar)\n" +
                        " ELSE PF.Importe END )) AS MONTO,'anioAnterior' as Periodo\n" +
                        " FROM Facturas AS F\n" +
                        /*" LEFT JOIN (SELECT * FROM Remisiones) RE ON CAST(RE.Nota AS VARCHAR(100)) = F.Factura AND F.DeRemision = 1\n" +
                        " LEFT JOIN (SELECT * FROM PRemisiones) PRE ON PRE.Factura = RE.Factura AND PRE.FPor = RE.FPor AND PRE.CPedido = RE.CPedido\n" +*/
                        " LEFT JOIN (SELECT * FROM PFacturas) AS PF ON PF.Factura = F.Factura AND PF.FPor = F.FPor\n" +
                        /*"\t\tAND CASE WHEN F.DeRemision = 1 THEN PRE.CPedido ELSE F.CPedido END = PF.CPedido\n" +
                        "\t\tAND CASE WHEN F.DeRemision = 1 THEN PRE.PPedido ELSE PF.PPedido END = PF.PPedido\n" +*/
                        " LEFT JOIN (SELECT * FROM Monedas) AS MON ON MON.Fecha = F.Fecha\n" +
                        " LEFT JOIN (SELECT * FROM PPedidos) AS PP ON PP.CPedido = PF.CPedido AND PP.Part = PF.PPedido\n" +
                        " LEFT JOIN Productos as Prod on Prod.idProducto=PP.FK02_Producto\n" +
                        " WHERE  PF.Importe > 0\n" +
                        " AND F.Estado<>'Cancelada' AND F.Estado<>'Por Cancelar'\n" +
                        " AND F.Fecha >= DATEADD(YEAR,-1,'" + parametro.getStartDate() + "') and F.Fecha <= DATEADD(YEAR,-1,'" + parametro.getFinalDate() + "') \n" +
                        " AND Prod.tipo =:tipoProducto and Prod.subtipo=:subtipo and Prod.control=:control and Prod.Proveedor=" + Integer.parseInt(proveedor);


                Map<String, Object> map = new HashMap<String, Object>();
                map.put("tipoProducto", parametro.getTipoProducto());
                map.put("subtipo", parametro.getSubTipo());
                map.put("control", parametro.getControl());
                map.put("Proveedor", proveedor);
                System.out.println(sbQuery);
                List<Promsy> mapReturn = new ArrayList<>();
                jdbcTemplate.query(sbQuery, map, new RowMapper() {
                    @Override
                    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Promsy prom = new Promsy();
                        prom.setPiezas(rs.getInt("PIEZAS"));
                        prom.setProveedor(parametro.getNombreProveedor());
                        prom.setMonto(new BigDecimal(rs.getString("Monto")));
                        prom.setPeriodo(rs.getString("Periodo"));
                        mapReturn.add(prom);
                        return mapReturn;
                    }
                });
            System.out.println(sbQuery);
                return mapReturn;

        }catch(Exception e){
            e.printStackTrace();
        }


        return null;
    }

    @Override
    public List<Promsy> comparativaProveedorVSTodos(Parametro parametro) throws ProquifaNetException {
    try{
        String sbQuery = "\n";
        sbQuery += "SELECT  SUM(PF.Cant) as PIEZAS,SUM(PF.Cant * ( CASE  WHEN F.Moneda = 'Pesos' OR F.Moneda = 'MXN' OR F.Moneda = 'M.N.' OR F.Moneda = 'M.N'\tTHEN ((PF.Importe) /  COALESCE(CASE WHEN F.TCambio = 0 THEN MON.PDolar ELSE F.TCambio END , MON.PDolar))\n" +
                " WHEN F.Moneda = 'Euros' OR F.Moneda = 'EUR'\t\t THEN PF.Importe * (MON.EDolar)\n" +
                " WHEN F.Moneda = 'Libras' THEN PF.Importe * (MON.LDolar)\n" +
                " WHEN F.Moneda = 'DlCan'  THEN PF.Importe * (MON.DDolar)\n" +
                " WHEN F.Moneda = 'Yenes'  THEN PF.Importe * (MON.YDolar)\n" +
                " ELSE PF.Importe END )) AS MONTO,'"+parametro.getNombreProveedor()+"' as Proveedor\n" +
                consulta(parametro);
        sbQuery += "\nUNION ALL\n";
        sbQuery+="SELECT  SUM(PF.Cant) as PIEZAS,SUM(PF.Cant * ( CASE  WHEN F.Moneda = 'Pesos' OR F.Moneda = 'MXN' OR F.Moneda = 'M.N.' OR F.Moneda = 'M.N'\tTHEN ((PF.Importe) /  COALESCE(CASE WHEN F.TCambio = 0 THEN MON.PDolar ELSE F.TCambio END , MON.PDolar))\n" +
                " WHEN F.Moneda = 'Euros' OR F.Moneda = 'EUR'\t\t THEN PF.Importe * (MON.EDolar)\n" +
                " WHEN F.Moneda = 'Libras' THEN PF.Importe * (MON.LDolar)\n" +
                " WHEN F.Moneda = 'DlCan'  THEN PF.Importe * (MON.DDolar)\n" +
                " WHEN F.Moneda = 'Yenes'  THEN PF.Importe * (MON.YDolar)\n" +
                " ELSE PF.Importe END )) AS MONTO,'Todos' as Proveedor\n" +
                " FROM Facturas AS F\n" +
               /* " LEFT JOIN (SELECT * FROM Remisiones) RE ON CAST(RE.Nota AS VARCHAR(100)) = F.Factura AND F.DeRemision = 1\n" +
                " LEFT JOIN (SELECT * FROM PRemisiones) PRE ON PRE.Factura = RE.Factura AND PRE.FPor = RE.FPor AND PRE.CPedido = RE.CPedido\n" +*/
                " LEFT JOIN (SELECT * FROM PFacturas) AS PF ON PF.Factura = F.Factura AND PF.FPor = F.FPor\n" +
                /*"\t\tAND CASE WHEN F.DeRemision = 1 THEN PRE.CPedido ELSE F.CPedido END = PF.CPedido\n" +
                "\t\tAND CASE WHEN F.DeRemision = 1 THEN PRE.PPedido ELSE PF.PPedido END = PF.PPedido\n" +*/
                " LEFT JOIN (SELECT * FROM Monedas) AS MON ON MON.Fecha = F.Fecha\n" +
                " LEFT JOIN (SELECT * FROM PPedidos) AS PP ON PP.CPedido = PF.CPedido AND PP.Part = PF.PPedido\n" +
                " LEFT JOIN Productos as Prod on Prod.idProducto=PP.FK02_Producto\n" +
                " WHERE  PF.Importe > 0\n" +
                " AND F.Estado<>'Cancelada' AND F.Estado<>'Por Cancelar'\n" +
                " AND F.Fecha >='"+parametro.getStartDate()+ "' and F.Fecha <= '"+parametro.getFinalDate()+"' \n" ;
        if(parametro.getTipoProducto().equals("Labware")){
            sbQuery += " AND Prod.tipo =:tipoProducto and Prod.subtipo is Null and Prod.control is Null ";
        }else {
            sbQuery += " AND Prod.tipo =:tipoProducto and Prod.subtipo=:subtipo and Prod.control=:control ";
        }


        Map<String, Object> map = new HashMap<String, Object>();
        map.put("tipoProducto", parametro.getTipoProducto());
        map.put("subtipo", parametro.getSubTipo());
        map.put("control", parametro.getControl());
        map.put("Proveedor", obtenerIdProveedor(parametro));
       // System.out.println(sbQuery);
        List<Promsy> mapReturn = new ArrayList<>();
        jdbcTemplate.query(sbQuery, map, new RowMapper() {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                Promsy prom = new Promsy();
                prom.setPiezas(rs.getInt("PIEZAS"));
                prom.setMonto(new BigDecimal(rs.getString("Monto")));
                prom.setProveedor(rs.getString("Proveedor"));
                prom.setPeriodo("TrimActual");
                mapReturn.add(prom);
                return null;
            }
        });
        System.out.println(sbQuery);
        return mapReturn;
    }catch (Exception e){
        e.printStackTrace();
    }
        return null;
    }

    @Override
    public List<Map<String,Object>> top5ProductosMasVendidos(Parametro parametro) throws ProquifaNetException {
        try{
            String proveedor = obtenerIdProveedor(parametro);
            String query="SELECT  top 5 SUM(PF.Cant) as PIEZAS_VENDIDAS,Prod.Codigo,CAST (Prod.Concepto AS Varchar(MAX)) as Concepto, Prod.tipoPresentacion,Prod.idProducto\n" +
                    " FROM Facturas AS F\n" +
                    /*" LEFT JOIN (SELECT * FROM Remisiones) RE ON CAST(RE.Nota AS VARCHAR(100)) = F.Factura AND F.DeRemision = 1\n" +
                    " LEFT JOIN (SELECT * FROM PRemisiones) PRE ON PRE.Factura = RE.Factura AND PRE.FPor = RE.FPor AND PRE.CPedido = RE.CPedido\n" +*/
                    " LEFT JOIN (SELECT * FROM PFacturas) AS PF ON PF.Factura = F.Factura AND PF.FPor = F.FPor\n" +
                   /* "\t\tAND CASE WHEN F.DeRemision = 1 THEN PRE.CPedido ELSE F.CPedido END = PF.CPedido\n" +
                    "\t\tAND CASE WHEN F.DeRemision = 1 THEN PRE.PPedido ELSE PF.PPedido END = PF.PPedido\n" +*/
                    " LEFT JOIN (SELECT * FROM Monedas) AS MON ON MON.Fecha = F.Fecha\n" +
                    " LEFT JOIN (SELECT * FROM PPedidos) AS PP ON PP.CPedido = PF.CPedido AND PP.Part = PF.PPedido\n" +
                    " LEFT JOIN Productos as Prod on Prod.idProducto=PP.FK02_Producto\n" +
                    " WHERE  PF.Importe > 0\n" +
                    " AND F.Estado<>'Cancelada' AND F.Estado<>'Por Cancelar'\n" +
                    " AND F.Fecha >='"+parametro.getStartDate()+ "' and F.Fecha <= '"+parametro.getFinalDate()+"' \n" +
                    " AND Prod.Proveedor=:Proveedor" +
                    " \ngroup by Prod.Codigo,CAST (Prod.Concepto AS Varchar(MAX)), Prod.tipoPresentacion,Prod.idProducto\n" +
                    " order by SUM(PF.Cant) desc";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("Proveedor", proveedor);

            List<Map<String, Object>> resultados = new ArrayList<>();
            System.out.println(query);
            jdbcTemplate.query(query, map, new RowMapper() {
                @Override
                public List<Map<String,Object>> mapRow(ResultSet rs, int rowNum) throws SQLException {

                    Map<String, Object> fila = new HashMap<>();

                    fila.put("piezas_vendidas", rs.getInt("piezas_vendidas"));
                    fila.put("codigo", rs.getString("codigo"));
                    fila.put("concepto", rs.getString("concepto"));
                    fila.put("tipoPresentacion",rs.getString("tipoPresentacion"));
                    fila.put("tipoPresentacion",rs.getString("tipoPresentacion"));
                    fila.put("idProducto",rs.getString("idProducto"));

                    resultados.add(fila);

                    //mapReturn.add(producto);
                    return resultados;
                }
            });
            System.out.println(query);
            return resultados;

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> unidadesVendidasPorMes(Parametro parametro) throws ProquifaNetException {
        try{
            String proveedor = obtenerIdProveedor(parametro);
            String query="SELECT  SUM(PF.Cant) AS PIEZAS,SUM(PF.Cant * ( CASE  WHEN F.Moneda = 'Pesos' OR F.Moneda = 'MXN' OR F.Moneda = 'M.N.' OR F.Moneda = 'M.N'\tTHEN ((PF.Importe) /  COALESCE(CASE WHEN F.TCambio = 0 THEN MON.PDolar ELSE F.TCambio END , MON.PDolar))\n" +
                    " WHEN F.Moneda = 'Euros' OR F.Moneda = 'EUR'\t\t THEN PF.Importe * (MON.EDolar)\n" +
                    " WHEN F.Moneda = 'Libras' THEN PF.Importe * (MON.LDolar)\n" +
                    " WHEN F.Moneda = 'DlCan'  THEN PF.Importe * (MON.DDolar)\n" +
                    " WHEN F.Moneda = 'Yenes'  THEN PF.Importe * (MON.YDolar)\n" +
                    " ELSE PF.Importe END )) AS MONTO,FORMAT(F.Fecha, 'MMMM', 'en-us') AS MES,year(F.Fecha) as AÑO\n" +
                    " FROM Facturas AS F\n" +
                    /*" LEFT JOIN (SELECT * FROM Remisiones) RE ON CAST(RE.Nota AS VARCHAR(100)) = F.Factura AND F.DeRemision = 1\n" +
                    " LEFT JOIN (SELECT * FROM PRemisiones) PRE ON PRE.Factura = RE.Factura AND PRE.FPor = RE.FPor AND PRE.CPedido = RE.CPedido\n" +*/
                    " LEFT JOIN (SELECT * FROM PFacturas) AS PF ON PF.Factura = F.Factura AND PF.FPor = F.FPor\n" +
                   /* "\t\tAND CASE WHEN F.DeRemision = 1 THEN PRE.CPedido ELSE F.CPedido END = PF.CPedido\n" +
                    "\t\tAND CASE WHEN F.DeRemision = 1 THEN PRE.PPedido ELSE PF.PPedido END = PF.PPedido\n" +*/
                    " LEFT JOIN (SELECT * FROM Monedas) AS MON ON MON.Fecha = F.Fecha\n" +
                    " LEFT JOIN (SELECT * FROM PPedidos) AS PP ON PP.CPedido = PF.CPedido AND PP.Part = PF.PPedido\n" +
                    " LEFT JOIN Productos as Prod on Prod.idProducto=PP.FK02_Producto\n" +
                    " WHERE  PF.Importe > 0\n" +
                    " AND F.Estado<>'Cancelada' AND F.Estado<>'Por Cancelar'\n" +
                    " AND F.Fecha >=DATEADD(YEAR,-1,'"+parametro.getStartDate().substring(0,6)+"01"+"')  and F.Fecha <= '"+parametro.getStartDate()+"'\n" +
                    " AND  Prod.Proveedor=:Proveedor\n" +
                    " GROUP BY  MONTH(F.Fecha),FORMAT(F.Fecha, 'MMMM', 'en-us'),year(F.Fecha)";
            System.out.println(parametro.getStartDate().substring(0,6));
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("Proveedor", proveedor);
            List<Map<String, Object>> resultados = new ArrayList<>();
            jdbcTemplate.query(query, map, new RowMapper() {
                @Override
                public List<Map<String,Object>> mapRow(ResultSet rs, int rowNum) throws SQLException {

                    Map<String, Object> fila = new HashMap<>();

                    fila.put("piezas", rs.getInt("PIEZAS"));
                    fila.put("monto", rs.getBigDecimal("MONTO"));
                    fila.put("mes", rs.getString("MES"));
                    fila.put("año", rs.getString("AÑO"));
                    resultados.add(fila);

                    return resultados;
                }
            });
            System.out.println(query);
            return resultados;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean descontarProductosCaducadosStock() throws ProquifaNetException {

        if (productosACaducar().size()>0){

            Map<String, Object> param = new HashMap<String, Object>();

            for (Map<String,Object> stock: productosACaducar()){
                StringBuilder sbQuery = new StringBuilder(" \n");
                sbQuery.append("DECLARE @usuario varchar(30) = (SELECT TOP 1 Usuario From Empleados WHERE nivel = 31 AND fase > 0 AND Fk01_Funcion = 11) \n");
                  // sbQuery.append("UPDATE STOCK SET FSalida = GETDATE(), Razon = 'Caducidad', cantDisponible = 0 \n");
                    sbQuery.append("UPDATE STOCK SET FSalida = GETDATE(), Razon = 'Caducidad' \n");
                   sbQuery.append("WHERE  idStock = :idStock \n");

                   sbQuery.append("INSERT INTO PENDIENTE (Docto, Partida, FInicio, Responsable, Tipo) \n");
                   sbQuery.append("VALUES(:idPcompra, :idStock, GETDATE(), @usuario, 'Producto a destrucción') \n");

                //System.out.println(stock.get("idStock"));
                //System.out.println(stock.get("idPcompra"));

                   param.put("idStock",stock.get("idStock"));
                   param.put("idPcompra",stock.get("idPcompra"));
                   System.out.println(sbQuery);
                   jdbcTemplate.update(sbQuery.toString(), param);



            }

            return true;
        }else{
            return false;
        }
    }

    @Override
    public List<Map<String, Object>> reporteComercial() throws ProquifaNetException {
        try {
        String query="\n" +
                "SELECT  C.Cliente as Cliente,Prod.Codigo as catalogo ,CAST(Prod.Concepto as varchar(max)) as descripcion ,Prod.Fabrica as Marca\n" +
                ", Pcot.Cant as Piezas,\n" +
                "Concat ('$',(Pcot.Cant * ( CASE  WHEN C.Moneda = 'Pesos' OR C.Moneda = 'MXN' OR C.Moneda = 'M.N.' OR C.Moneda = 'M.N'\tTHEN (Pcot.Precio /  Mon.PDolar)\n" +
                "WHEN C.Moneda = 'Euros' OR C.Moneda = 'EUR'\t\t THEN Pcot.Precio * (MON.EDolar)\n" +
                "WHEN C.Moneda = 'Libras' THEN Pcot.Precio * (MON.LDolar)\n" +
                "WHEN C.Moneda = 'DlCan'  THEN Pcot.Precio * (MON.DDolar)\n" +
                "WHEN C.Moneda = 'Yenes'  THEN Pcot.Precio * (MON.YDolar)\n" +
                "ELSE Pcot.Precio END )),' USD' )AS MONTO_COTIZADO,\n" +
                "\n" +
                "Concat ('$',COALESCE(PP.Cant * ( CASE  WHEN P.Moneda = 'Pesos' OR P.Moneda = 'MXN' OR P.Moneda = 'M.N.' OR P.Moneda = 'M.N'\tTHEN (PP.Precio /  MONP.PDolar)\n" +
                "WHEN P.Moneda = 'Euros' OR P.Moneda = 'EUR'\t\t THEN PP.Precio * (MONP.EDolar)\n" +
                "WHEN P.Moneda = 'Libras' THEN PP.Precio * (MONP.LDolar)\n" +
                "WHEN P.Moneda = 'DlCan'  THEN PP.Precio * (MONP.DDolar)\n" +
                "WHEN P.Moneda = 'Yenes'  THEN PP.Precio * (MONP.YDolar)\n" +
                "ELSE PP.Precio END ),0),' USD' )AS MONTO_PEDIDOS,\n" +
                "\n" +
                "\n" +
                "\n" +
                "Concat ('$',COALESCE(PF.Cant * ( CASE  WHEN F.Moneda = 'Pesos' OR F.Moneda = 'MXN' OR F.Moneda = 'M.N.' OR F.Moneda = 'M.N'\tTHEN ((PF.Importe) /  COALESCE(CASE WHEN F.TCambio = 0 THEN MONF.PDolar ELSE F.TCambio END , MONF.PDolar))\n" +
                "WHEN F.Moneda = 'Euros' OR F.Moneda = 'EUR'\t\t THEN PF.Importe * (MONF.EDolar)\n" +
                "WHEN F.Moneda = 'Libras' THEN PF.Importe * (MONF.LDolar)\n" +
                "WHEN F.Moneda = 'DlCan'  THEN PF.Importe * (MONF.DDolar)\n" +
                "WHEN F.Moneda = 'Yenes'  THEN PF.Importe * (MONF.YDolar)\n" +
                "ELSE PF.Importe END ),0),' USD' )AS MONTO_FACTURADO\n" +
                ",C.Clave as Cotizacion, COALESCE(PP.CPedido,'ND') As Pedido\n" +
                "FROM Cotizas AS C\n" +
                "LEFT JOIN PCotizas as Pcot on Pcot.FK02_Cotiza= C.PK_Folio\n" +
                "LEFT JOIN PPedidos as PP on PP.Cotiza=Pcot.Clave and (Pcot.Codigo=PP.Codigo and Pcot.Fabrica= PP.Fabrica)\n" +
                "LEft JOIN Pedidos as P on P.CPedido=PP.CPedido\n" +
                "\n" +
                "\n" +
                "LEFT JOIN (SELECT * FROM PFacturas) AS PF ON PF.CPedido=PP.CPedido and PF.PPedido=PP.Part\n" +
                "LEFT JOIN Facturas as F on F.Factura=PF.Factura and F.FPor= PF.Fpor\n" +
                "LEFT JOIN (SELECT * FROM Monedas) AS MON ON  CONVERT(VARCHAR(10),Mon.Fecha, 23) = CONVERT(VARCHAR(10),C.Fecha, 23)\n" +
                "LEFT JOIN (SELECT * FROM Monedas) AS MONF ON  CONVERT(VARCHAR(10),MonF.Fecha, 23) = CONVERT(VARCHAR(10),F.Fecha, 23)\n" +
                "\n" +
                "LEFT JOIN  Monedas AS MONP ON  cast (cast(P.FPedido as date)as dateTime) = MONP.Fecha\n" +
                "\n" +
                "\n" +
                "LEFT JOIN Productos as Prod on Prod.idProducto= Pcot.FK03_idProducto\n" +
                "LEFT JOIN ConfiguracionPrecio_Clasificacion as CPC on CPC.PK_ConfiguracionPrecioClasificacion= PROD.FK04_Clasificacion_ConfiguracionPrecio\n" +
                "WHERE  C.Fecha >='20240101 00:00' \n" +
                "and Prod.Fabrica <> 'Fletes'\n" +
                "GROUP by\n" +
                "C.Cliente,\n" +
                "Prod.Codigo,\n" +
                "cast (Prod.Concepto as varchar(max)) ,\n" +
                "Prod.Fabrica,\n" +
                "Pcot.Cant ,\n" +
                "CPC.Concepto,\n" +
                "C.Clave,\n" +
                "Pcot.Precio,\n" +
                "C.moneda,\n" +
                "Pcot.Codigo,\n" +
                "PP.CPedido,\n" +
                "Concat ('$',(Pcot.Cant * ( CASE  WHEN C.Moneda = 'Pesos' OR C.Moneda = 'MXN' OR C.Moneda = 'M.N.' OR C.Moneda = 'M.N'\tTHEN (Pcot.Precio /  Mon.PDolar)\n" +
                "WHEN C.Moneda = 'Euros' OR C.Moneda = 'EUR'\t\t THEN Pcot.Precio * (MON.EDolar)\n" +
                "WHEN C.Moneda = 'Libras' THEN Pcot.Precio * (MON.LDolar)\n" +
                "WHEN C.Moneda = 'DlCan'  THEN Pcot.Precio * (MON.DDolar)\n" +
                "WHEN C.Moneda = 'Yenes'  THEN Pcot.Precio * (MON.YDolar)\n" +
                "ELSE Pcot.Precio END )),' USD' ),\n" +
                "\n" +
                "Concat ('$',COALESCE(PP.Cant * ( CASE  WHEN P.Moneda = 'Pesos' OR P.Moneda = 'MXN' OR P.Moneda = 'M.N.' OR P.Moneda = 'M.N'\tTHEN (PP.Precio /  MONP.PDolar)\n" +
                "WHEN P.Moneda = 'Euros' OR P.Moneda = 'EUR'\t\t THEN PP.Precio * (MONP.EDolar)\n" +
                "WHEN P.Moneda = 'Libras' THEN PP.Precio * (MONP.LDolar)\n" +
                "WHEN P.Moneda = 'DlCan'  THEN PP.Precio * (MONP.DDolar)\n" +
                "WHEN P.Moneda = 'Yenes'  THEN PP.Precio * (MONP.YDolar)\n" +
                "ELSE PP.Precio END ),0),' USD' )\n" +
                "\n" +
                ",\n" +
                "Concat ('$',COALESCE(PF.Cant * ( CASE  WHEN F.Moneda = 'Pesos' OR F.Moneda = 'MXN' OR F.Moneda = 'M.N.' OR F.Moneda = 'M.N'\tTHEN ((PF.Importe) /  COALESCE(CASE WHEN F.TCambio = 0 THEN MONF.PDolar ELSE F.TCambio END , MONF.PDolar))\n" +
                "WHEN F.Moneda = 'Euros' OR F.Moneda = 'EUR'\t\t THEN PF.Importe * (MONF.EDolar)\n" +
                "WHEN F.Moneda = 'Libras' THEN PF.Importe * (MONF.LDolar)\n" +
                "WHEN F.Moneda = 'DlCan'  THEN PF.Importe * (MONF.DDolar)\n" +
                "WHEN F.Moneda = 'Yenes'  THEN PF.Importe * (MONF.YDolar)\n" +
                "ELSE PF.Importe END ),0),' USD' )\n" ;

                    Map<String, Object> map = new HashMap<String, Object>();
                    List<Map<String, Object>> resultados = new ArrayList<>();
        jdbcTemplate.query(query, map, new RowMapper() {
            @Override
            public List<Map<String,Object>> mapRow(ResultSet rs, int rowNum) throws SQLException {

                Map<String, Object> fila = new LinkedHashMap<>();

                fila.put("Cliente", rs.getString("Cliente"));
                fila.put("catalogo", rs.getString("catalogo"));
                fila.put("descripcion", rs.getString("descripcion"));
                fila.put("Marca", rs.getString("Marca"));
                fila.put("Piezas", rs.getString("Piezas"));
                fila.put("MONTO_COTIZADO", rs.getString("MONTO_COTIZADO"));
                fila.put("MONTO_PEDIDOS", rs.getString("MONTO_PEDIDOS"));
                fila.put("MONTO_FACTURADO", rs.getString("MONTO_FACTURADO"));
                fila.put("Cotizacion", rs.getString("Cotizacion"));
                fila.put("Pedido", rs.getString("Pedido"));

                resultados.add(fila);

                return resultados;
            }
        });
            return resultados;
        }catch (Exception e ){
            e.printStackTrace();
        }

        return null;
    }

    public List <Map<String, Object>> productosACaducar(){
       try {
        String query="select  st.idStock,ioc.idPCompra\n" +
                "from InspeccionOC as IOC\n" +
                "left join ValorCombo as VC on VC.Tipo= IOC.MesCaducidad COLLATE SQL_Latin1_General_CP1_CI_AS\n" +
                "Left Join stock as st on IOC.idPCompra= st.idPCompra\n" +
                "where (MesCaducidad is not null and AnoCaducidad is not null)\n" +
                "and (MesCaducidad <> '--ND--' and AnoCaducidad<> '--ND--')\n" +
                "and DATEDIFF (MONTH,DATEFROMPARTS (coalesce (IOC.AnoCaducidad, YEAR(GETDATE())),coalesce(VC.Valor,MONTH(GETDATE())),'01'),GETDATE()) > 0\n" +
                "and st.cantDisponible > 0";
           Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> resultados = new ArrayList<>();
           System.out.println(query);
           jdbcTemplate.query(query, map,new RowMapper() {
               @Override
               public List<Map<String,Object>> mapRow(ResultSet rs, int rowNum) throws SQLException {

                   Map<String, Object> fila = new HashMap<>();

                   fila.put("idStock", rs.getInt("idStock"));
                   fila.put("idPcompra", rs.getInt("idPcompra"));
                   resultados.add(fila);

                   return resultados;
               }

           });
            return resultados;
       } catch (Exception e){
           e.printStackTrace();
       }
        return null;
    }
    public String consulta(Parametro parametro) throws ProquifaNetException {
       // String proveedor = obtenerIdProveedor(parametro);
       String query= " FROM Facturas AS F\n" +
               /* " LEFT JOIN (SELECT * FROM Remisiones) RE ON CAST(RE.Nota AS VARCHAR(100)) = F.Factura AND F.DeRemision = 1\n" +
                " LEFT JOIN (SELECT * FROM PRemisiones) PRE ON PRE.Factura = RE.Factura AND PRE.FPor = RE.FPor AND PRE.CPedido = RE.CPedido\n" +*/
                " LEFT JOIN (SELECT * FROM PFacturas) AS PF ON PF.Factura = F.Factura AND PF.FPor = F.FPor\n" +
               /* "\t\tAND CASE WHEN F.DeRemision = 1 THEN PRE.CPedido ELSE F.CPedido END = PF.CPedido\n" +
                "\t\tAND CASE WHEN F.DeRemision = 1 THEN PRE.PPedido ELSE PF.PPedido END = PF.PPedido\n" +*/
                " LEFT JOIN (SELECT * FROM Monedas) AS MON ON MON.Fecha = F.Fecha\n" +
                " LEFT JOIN (SELECT * FROM PPedidos) AS PP ON PP.CPedido = PF.CPedido AND PP.Part = PF.PPedido\n" +
                " LEFT JOIN Productos as Prod on Prod.idProducto=PP.FK02_Producto\n" +
                " WHERE  PF.Importe > 0\n" +
                " AND F.Estado<>'Cancelada' AND F.Estado<>'Por Cancelar'\n" +
                " AND F.Fecha >='"+parametro.getStartDate()+ "' and F.Fecha <= '"+parametro.getFinalDate()+"' \n" ;
               if(parametro.getTipoProducto().equals("Labware")){
                   query += " AND Prod.tipo =:tipoProducto and Prod.subtipo is Null and Prod.control is Null and Prod.Proveedor=:Proveedor";
               }else {
                  query += " AND Prod.tipo =:tipoProducto and Prod.subtipo=:subtipo and Prod.control=:control and Prod.Proveedor=:Proveedor";
               }
                return query;
    }

}
