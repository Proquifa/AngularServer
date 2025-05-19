package com.proquifa.net.persistencia.cartaPorte.impl;

import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.Producto;
import com.proquifa.net.modelo.comun.ValidarCFDI;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.facturaElectronica.CartaPorte;
import com.proquifa.net.modelo.comun.facturaElectronica.PFacturaElectronica;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.cartaPorte.cartaPorteDAO;
import com.proquifa.net.persistencia.comun.facturacion.impl.mapper.FacturacionElectronicaRowMapper;
import com.proquifa.net.persistencia.despachos.impl.EmbalarDAOImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class cartaPorteDAOImpl extends DataBaseDAO implements cartaPorteDAO {
    final Logger log = LoggerFactory.getLogger(EmbalarDAOImpl.class);
    @Override
    public List<CartaPorte> obtenerEventosMensajero()throws ProquifaNetException {
        try{
            String tipo="Ruta a ejecutar";
            String estadoRuta="AEjecutar";
            String edoRuta="AColectar";
            String condicionRuta="(HL.cp BETWEEN '44000' and '49999' or HL.cp BETWEEN '62000' and '62999'or concat (HL.Estado,',',HL.Municipio,',',HL.ZonaMensajeria) like '%toluca%' and HL.cp not in ('4650'))";
            StringBuilder sbQuery = new StringBuilder("\n");
            sbQuery.append(" SELECT Responsable, COUNT(distinct (PEN.Docto)) AS eventos, FECPorte.Folio as folioTimbrado \n" +
                    "FROM RutaDP AS RUTA\n" +
                    "LEFT JOIN PackingList AS PKL ON RUTA.FK02_PackingList = PKL.PK_PackingList\n" +
                    "LEFT JOIN Empresa AS Emp ON Emp.Alias COLLATE SQL_Latin1_General_CP1_CI_AS = RUTA.FPor\n" +
                    "LEFT JOIN PPackingList AS PPKL ON PKL.PK_PackingList = PPKL.FK01_PackingList AND\n" +
                    "\n" +
                    "(PPKL.Fk03_facturaElectronica IN (\n" +
                    "SELECT PK_Factura FROM FacturaElectronica FE1\n" +
                    "WHERE FE1.Folio COLLATE SQL_Latin1_General_CP1_CI_AS = RUTA.Factura COLLATE SQL_Latin1_General_CP1_CI_AS \n" +
                    "AND TipoComprobante = 'I' AND FE1.EmpresaEmisor = Emp.PK_Empresa)\n" +
                    "\n" +
                    "OR PPKL.Fk04_Remision IN\n" +
                    "(\n" +
                    "SELECT PK_Remision FROM Remisiones REM WHERE REM.Factura COLLATE SQL_Latin1_General_CP1_CI_AS = RUTA.Factura COLLATE SQL_Latin1_General_CP1_CI_AS)\n" +
                    ")\n" +
                    "\n" +
                    "\n" +
                    "LEFT JOIN EmbalarPedido AS EP ON PPKL.FK02_EmbalarPEdido = EP.PK_EmbalarPEdido\n" +
                    "LEFT JOIN PPedidos AS PP ON EP.FK01_PPEdido = PP.IdPPEdido\n" +
                    "LEFT JOIN PEdidos AS PE ON PE.CPedido = PP.CPedido\n" +
                    "LEFT JOIN AsignarMensajero AS AM ON AM.Evento = RUTA.idDP\n" +
                    "LEFT JOIN Clientes AS CLI ON CLI.Clave = RUTA.idCliente\n" +
                    "LEFT JOIN Pendiente AS PEN ON PEN.Docto COLLATE SQL_Latin1_General_CP1_CI_AS = RUTA.idRuta COLLATE SQL_Latin1_General_CP1_CI_AS\n" +
                    "LEFT JOIN HorarioyLugar AS HL ON HL.idCliente = RUTA.idCliente AND HL.idHorario = PE.FK01_Horario\n" +
                    "LEFT JOIN CartaPorte as cPorte on cPorte.FK04_packingList=PKL.PK_PackingList\n" +
                    "LEFT JOIN (SELECT * FROM FacturaElectronica where folio <> 'bloqued_Error') as FECPorte on FECPorte.PK_Factura=cPorte.FK01_FE\n"+
                    "WHERE PEN.Tipo = :tipo AND RUTA.EstadoRuta in(:estadoRuta,:edoRuta) AND " + condicionRuta+
                    "AND PEN.FFin IS NULL AND RUTA.FK02_PackingList IS NOT NULL\n" +
                    "GROUP BY Responsable,FECPorte.Folio" );

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("tipo", tipo);
            map.put("estadoRuta", estadoRuta);
            map.put("edoRuta",edoRuta);
            log.info(sbQuery.toString());
            return  jdbcTemplate.query(sbQuery.toString(), map, new BeanPropertyRowMapper<>(CartaPorte.class));
        }catch(Exception e){
            System.out.println("Ocurrio un error"+"\n"+"cartaPorteDAOImpl:");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CartaPorte> obtenerInfoMensajero() throws ProquifaNetException {
        try {
            StringBuilder sbQuery = new StringBuilder("\n");

            sbQuery.append("select concat (nombre , ' ', apellido_paterno, ' ', apellido_materno) as mensajero, RFC as rfcMensajero,licencia,calle as calleDomicilio,\n" +
                    "num_Ext as numExtDomicilio, num_int as numInt, estado,pais,codigo_postal as cPostalDomicilio,pk_mensajero as idMensajero from Mensajero");
            Map<String, Object> map = new HashMap<>();
            log.info("Query obtenerInfoMensajero:" + sbQuery);

            return  jdbcTemplate.query(sbQuery.toString(), map, new BeanPropertyRowMapper<>(CartaPorte.class));
        }catch (Exception e){
            System.out.println("Ocurrio un error"+"\n"+"cartaPorteDAOImpl:");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CartaPorte> obtenerVehiculos() throws ProquifaNetException {
        try {
            StringBuilder sbQuery = new StringBuilder("\n");

            sbQuery.append("select placaVM,modelo,permisoSCT as permSCT, numPermiso as numPermSCT, configuracion_vehicular as configVehicular, anio_modelo as anioModelo,aseguradora as aseguraRespCivil, poliza as polizaRespCivil,pk_vehiculo as idVehiculo from vehiculo");
            Map<String, Object> map = new HashMap<>();
            log.info("Query obtenerVehiculos:" + sbQuery);

            return  jdbcTemplate.query(sbQuery.toString(), map, new BeanPropertyRowMapper<>(CartaPorte.class));
        }catch (Exception e){
            System.out.println("Ocurrio un error"+"\n"+"cartaPorteDAOImpl:");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Cliente> obtenerClientesPorMensajero(String mensajero) throws ProquifaNetException {
        try {
            StringBuilder sbQuery = new StringBuilder("\n");
            sbQuery.append("SELECT\n" +
                    "  CLI.Nombre,\n" +
                    "  CLI.Clave AS idCliente,\n" +
                    "  CLI.CURP AS rfc,\n" +
                    "  CLI.CP AS codigoPostal,\n" +
                    "  CLI.RegimenFiscal,\n" +
                    "  VC.tipo AS UsoCFDI,\n" +
                    " CLI.Estado ,\n" +
                    "  CLI.RSCalle as calleFiscal,\n" +
                    "  CLI.RegimenSocietario,"
                    +
                    "  STUFF((\n" +
                    "  \n" +
                    "    SELECT Distinct(', ' + CAST(PL.PK_PackingList AS VARCHAR(MAX)))\n" +
                    "    FROM RutaDP AS R\n" +
                    "    inner JOIN PackingList AS PL ON PL.PK_PackingList = R.FK02_PackingList\n" +
                    "    WHERE   R.idCliente=CLI.Clave   and R.EstadoRuta='AEjecutar' FOR XML PATH('')\n" +
                    "  \n" +
                    "  ), 1, 2, ''\n" +
                    "  \n" +
                    "  \n" +
                    "  ) AS PLXCliente\n" +
                    "FROM RutaDP AS RUTA\n" +
                    "LEFT JOIN Empresa AS Emp ON Emp.Alias COLLATE SQL_Latin1_General_CP1_CI_AS = RUTA.FPor\n" +
                    "LEFT JOIN PPackingList AS PPKL ON RUTA.FK02_PackingList = PPKL.FK01_PackingList\n" +
                    "  AND (\n" +
                    "    PPKL.Fk03_facturaElectronica IN (\n" +
                    "      SELECT PK_Factura\n" +
                    "      FROM FacturaElectronica FE1\n" +
                    "      WHERE FE1.Folio COLLATE SQL_Latin1_General_CP1_CI_AS = RUTA.Factura COLLATE SQL_Latin1_General_CP1_CI_AS\n" +
                    "      AND TipoComprobante = 'I'\n" +
                    "      AND FE1.EmpresaEmisor = Emp.PK_Empresa\n" +
                    "    )\n" +
                    "    OR PPKL.Fk04_Remision IN (\n" +
                    "      SELECT PK_Remision\n" +
                    "      FROM Remisiones REM\n" +
                    "      WHERE REM.Factura COLLATE SQL_Latin1_General_CP1_CI_AS = RUTA.Factura COLLATE SQL_Latin1_General_CP1_CI_AS\n" +
                    "    )\n" +
                    "  )\n" +
                    "LEFT JOIN EmbalarPedido AS EP ON PPKL.FK02_EmbalarPEdido = EP.PK_EmbalarPEdido\n" +
                    "LEFT JOIN PPedidos AS PP ON EP.FK01_PPEdido = PP.IdPPEdido\n" +
                    "LEFT JOIN PEdidos AS PE ON PE.CPedido = PP.CPedido\n" +
                    "LEFT JOIN AsignarMensajero AS AM ON AM.Evento = RUTA.idDP\n" +
                    "LEFT JOIN Clientes AS CLI ON CLI.Clave = RUTA.idCliente\n" +
                    "LEFT JOIN ValorCombo AS VC ON VC.PK_Folio = CLI.FK04_UsoCFDI\n" +
                    "LEFT JOIN Pendiente AS PEN ON PEN.Docto COLLATE SQL_Latin1_General_CP1_CI_AS = RUTA.idRuta COLLATE SQL_Latin1_General_CP1_CI_AS\n" +
                    "LEFT JOIN HorarioyLugar AS HL ON HL.idCliente = RUTA.idCliente\n" +
                    "  AND HL.idHorario = PE.FK01_Horario\n" +
                    "WHERE\n" +
                    "  PEN.Tipo = 'Ruta a ejecutar'\n" +
                    "  AND RUTA.EstadoRuta = 'AEjecutar'\n" +
                    "  AND \n" +
                    "((HL.cp BETWEEN '44000' and '49999' or HL.cp BETWEEN '62000' and '62999') or concat (HL.Estado,',',HL.Municipio,',',HL.ZonaMensajeria) like '%toluca%' and HL.cp not in ('4650'))"+


                    "  AND PEN.FFin IS NULL\n" +
                    "  AND RUTA.FK02_PackingList IS NOT NULL and PEN.Responsable=:mensajero\n" +
                    "GROUP BY\n" +
                    "  CLI.Nombre,\n" +
                    "  CLI.Clave,\n" +
                    "  CLI.CURP,\n" +
                    "  CLI.CP,\n" +
                    "  CLI.RegimenFiscal,\n" +
                    "  VC.tipo,"+" CLI.Estado,\n" +
                    "  CLI.RSCalle,\n" +
                    "  CLI.RegimenSocietario");

           /* sbQuery.append("SELECT CLI.Nombre, CLI.Clave as idCliente\n" +
                    "FROM RutaDP AS RUTA\n" +
                    "LEFT JOIN PackingList AS PKL ON RUTA.FK02_PackingList = PKL.PK_PackingList\n" +
                    "LEFT JOIN Empresa AS Emp ON Emp.Alias COLLATE SQL_Latin1_General_CP1_CI_AS = RUTA.FPor\n" +
                    "LEFT JOIN PPackingList AS PPKL ON PKL.PK_PackingList = PPKL.FK01_PackingList AND\n" +
                    "(PPKL.Fk03_facturaElectronica IN (\n" +
                    "SELECT PK_Factura FROM FacturaElectronica FE1\n" +
                    "WHERE FE1.Folio COLLATE SQL_Latin1_General_CP1_CI_AS = RUTA.Factura COLLATE SQL_Latin1_General_CP1_CI_AS\n" +
                    "AND TipoComprobante = 'I' AND FE1.EmpresaEmisor = Emp.PK_Empresa)\n" +
                    "OR PPKL.Fk04_Remision IN\n" +
                    "(\n" +
                    "SELECT PK_Remision FROM Remisiones REM WHERE REM.Factura COLLATE SQL_Latin1_General_CP1_CI_AS = RUTA.Factura COLLATE SQL_Latin1_General_CP1_CI_AS)\n" +
                    ")\n" +
                    "LEFT JOIN EmbalarPedido AS EP ON PPKL.FK02_EmbalarPEdido = EP.PK_EmbalarPEdido\n" +
                    "LEFT JOIN PPedidos AS PP ON EP.FK01_PPEdido = PP.IdPPEdido\n" +
                    "LEFT JOIN PEdidos AS PE ON PE.CPedido = PP.CPedido\n" +
                    "LEFT JOIN AsignarMensajero AS AM ON AM.Evento = RUTA.idDP\n" +
                    "LEFT JOIN Clientes AS CLI ON CLI.Clave = RUTA.idCliente\n" +
                    "LEFT JOIN Pendiente AS PEN ON PEN.Docto COLLATE SQL_Latin1_General_CP1_CI_AS = RUTA.idRuta COLLATE SQL_Latin1_General_CP1_CI_AS\n" +
                    "LEFT JOIN HorarioyLugar AS HL ON HL.idCliente = RUTA.idCliente AND HL.idHorario = PE.FK01_Horario\n" +
                    "WHERE PEN.Tipo = 'Ruta a ejecutar' AND RUTA.EstadoRuta = 'AEjecutar' AND  (HL.cp BETWEEN '44000' and '49999' or HL.cp BETWEEN '62000' and '62999') and\n" +
                    "(HL.cp not in ('4650')   or concat (HL.Estado,',',HL.Municipio,',',HL.ZonaMensajeria) like '%toluca%')\n" +
                    "AND PEN.FFin IS NULL AND RUTA.FK02_PackingList IS NOT NULL and PEN.Responsable=:mensajero" );*/
            /*
            sbQuery.append("SELECT  CLI.Nombre, CLI.Clave as idCliente\n" +
                    "   FROM RutaPR AS RUTA\n" +
                    "   INNER JOIN (SELECT * FROM AsignarMensajero) AM ON AM.Evento = RUTA.idPR\n" +
                    "   LEFT JOIN (SELECT * FROM Clientes) AS CLI ON CLI.Clave = RUTA.idCliente\n" +
                    "   LEFT JOIN (SELECT * FROM Pendiente) AS PEN ON PEN.Docto = RUTA.idRuta\n" +
                    "   LEFT JOIN (SELECT * FROM HorarioyLugar) AS HL ON HL.idCliente = RUTA.idCliente AND HL.idHorario=RUTA.FK01_Direccion\n" +
                    "   WHERE PEN.Tipo = 'Ruta a ejecutar' AND EstadoRuta = 'AEjecutar' and  (HL.cp BETWEEN '44000' and '49999' or HL.cp BETWEEN '62000' and '62999') and\n" +
                    "\t(HL.cp not in ('4650')   or concat (HL.Estado,',',HL.Municipio,',',HL.ZonaMensajeria) like '%toluca%')\n" +
                    "and PEN.Responsable=:mensajero"+
                    "   UNION\n");
            sbQuery.append("  SELECT CLI.Nombre, CLI.Clave as idCliente\n" +
                    "   FROM RutaPC AS RUTA\n" +
                    "   INNER JOIN (SELECT * FROM AsignarMensajero) AM ON AM.Evento = RUTA.idPC\n" +
                    "   LEFT JOIN (SELECT * FROM Clientes) AS CLI ON CLI.Clave = RUTA.idCliente\n" +
                    "   LEFT JOIN (SELECT * FROM Pendiente) AS PEN ON PEN.Docto = RUTA.idRuta\n" +
                    "   LEFT JOIN (SELECT * FROM HorarioyLugar) AS HL ON HL.idCliente = RUTA.idCliente AND HL.idHorario=RUTA.FK01_Direccion\n" +
                    "   WHERE PEN.Tipo = 'Ruta a ejecutar' AND RUTA.EstadoRuta = 'AEjecutar' and  (HL.cp BETWEEN '44000' and '49999' or HL.cp BETWEEN '62000' and '62999') and\n" +
                    "(HL.cp not in ('4650')   or concat (HL.Estado,',',HL.Municipio,',',HL.ZonaMensajeria) like '%toluca%')\n" +
                    "and PEN.Responsable=:mensajero "+
                    "   UNION\n");
            sbQuery.append("  SELECT CLI.Nombre, CLI.Clave as idCliente\n" +
                    "   FROM RutaRE AS RUTA\n" +
                    "   INNER JOIN (SELECT * FROM AsignarMensajero) AM ON AM.Evento = RUTA.idRE\n" +
                    "   LEFT JOIN (SELECT * FROM Clientes) AS CLI ON CLI.Clave = RUTA.idCliente\n" +
                    "   LEFT JOIN (SELECT * FROM Pendiente) AS PEN ON PEN.Docto = RUTA.idRuta\n" +
                    "   LEFT JOIN (SELECT * FROM HorarioyLugar) AS HL ON HL.idCliente = RUTA.idCliente AND HL.idHorario=RUTA.FK01_Direccion\n" +
                    "   WHERE PEN.Tipo = 'Ruta a ejecutar' AND RUTA.EstadoRuta = 'AEjecutar' and  (HL.cp BETWEEN '44000' and '49999' or HL.cp BETWEEN '62000' and '62999') and\n" +
                    "(HL.cp not in ('4650')   or concat (HL.Estado,',',HL.Municipio,',',HL.ZonaMensajeria) like '%toluca%')\n" +
                    "and PEN.Responsable=:mensajero"+
                    "   UNION\n");
            sbQuery.append(" SELECT CLI.Nombre, CLI.Clave as idCliente\n" +
                    "  FROM RutaES AS RUTA\n" +
                    "  INNER JOIN (SELECT * FROM AsignarMensajero) AM ON AM.Evento = RUTA.idES\n" +
                    "  LEFT JOIN (SELECT * FROM Clientes) AS CLI ON CLI.Clave = RUTA.idCliente\n" +
                    "  LEFT JOIN (SELECT * FROM Pendiente ) AS PEN ON PEN.Docto = RUTA.idRuta\n" +
                    "  LEFT JOIN (SELECT * FROM HorarioyLugar) AS HL ON HL.idCliente = RUTA.idCliente AND HL.idHorario=RUTA.FK01_Direccion\n" +
                    "  WHERE PEN.Tipo = 'Ruta a ejecutar' AND RUTA.EstadoRuta = 'AEjecutar' and  (HL.cp BETWEEN '44000' and '49999' or HL.cp BETWEEN '62000' and '62999') and\n" +
                    "  (HL.cp not in ('4650')   or concat (HL.Estado,',',HL.Municipio,',',HL.ZonaMensajeria) like '%toluca%') " +
                    "and PEN.Responsable=:mensajero");*/
            Map<String, Object> map = new HashMap<>();
            map.put("mensajero",mensajero);
            log.info("Query obtenerClientesPorMensajero:" + sbQuery);

            return  jdbcTemplate.query(sbQuery.toString(), map, new BeanPropertyRowMapper<>(Cliente.class));
        }catch (Exception e){
            System.out.println("Ocurrio un error"+"\n"+"cartaPorteDAOImpl:");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Producto> obtenerProductosPorPackingList(String packingList) throws ProquifaNetException {
        try {
            StringBuilder sbQuery = new StringBuilder("\n");
            sbQuery.append(" select \n" +
                    "\n" +
                    "case WHEN prod.Tipo = 'Fletes' THEN  'ACT'\n" +
                    "WHEN prod.Tipo = 'Publicaciones' THEN  'H87'\n" +
                    "WHEN Datos.Tipo = 'Capacitaciones' THEN  'E48' \n" +
                    "WHEN prod.unidad IS NOT NULL THEN\n" +
                    "CASE WHEN prod.concepto LIKE 'Frasco' THEN 'H87'\n" +
                    "WHEN prod.unidad = 'cm'  THEN 'H87'\n" +
                    "WHEN prod.unidad = 'g'  THEN 'H87'\n" +
                    "WHEN prod.unidad = 'kg' THEN 'H87'\n" +
                    "WHEN prod.unidad = 'L' THEN 'H87'\n" +
                    "WHEN prod.unidad = 'mcg' THEN 'H87'\n" +
                    "WHEN prod.unidad = 'mg' THEN 'H87'\n" +
                    "WHEN prod.unidad = 'mL' THEN 'H87'\n" +
                    "WHEN prod.unidad = 'UI' THEN 'H87'\n" +
                    "WHEN prod.unidad = 'uL' THEN 'H87'\n" +
                    "WHEN prod.unidad = 'Units' THEN 'H87'\n" +
                    "WHEN prod.unidad = 'Wells' THEN 'H87'\n" +
                    "WHEN prod.unidad = 'tablets' THEN 'H87'\n" +
                    "WHEN prod.unidad = 'Fletes' THEN '' ELSE 'H87'\n" +
                    "END ELSE 'H87' END claveUnidad,\n" +
                    "\n" +
                    "CASE\n" +
                    "WHEN prod.Tipo = 'Fletes' THEN  'Evento'\n" +
                    "WHEN prod.Tipo = 'Publicaciones' THEN  'Pieza'\n" +
                    "WHEN prod.unidad IS NOT NULL THEN\n" +
                    "CASE WHEN prod.concepto LIKE 'Frasco' THEN  'Frasco'\n" +
                    "WHEN prod.unidad = 'cm'  THEN 'Pieza'\n" +
                    "WHEN prod.unidad = 'g'  THEN 'Pieza'\n" +
                    "WHEN prod.unidad = 'kg' THEN 'Pieza'\n" +
                    "WHEN prod.unidad = 'L' THEN 'Pieza'\n" +
                    "WHEN prod.unidad = 'mcg' THEN 'Pieza'\n" +
                    "WHEN prod.unidad = 'mg' THEN 'Pieza'\n" +
                    "WHEN prod.unidad = 'mL' THEN 'Pieza'\n" +
                    "WHEN prod.unidad = 'UI' THEN 'Pieza'\n" +
                    "WHEN prod.unidad = 'uL' THEN 'Pieza'\n" +
                    "WHEN prod.unidad = 'Units' THEN 'Pieza'\n" +
                    "WHEN prod.unidad = 'Wells' THEN 'Pieza'\n" +
                    "WHEN prod.unidad = 'tablets' THEN 'Pieza'\n" +
                    "WHEN prod.unidad = 'Fletes' THEN 'Pieza' ELSE 'Pieza'\n" +
                    "END  ELSE 'Pieza' END unidad,\n" +
                    "CASE WHEN prod.subtipo = 'Biológico' THEN '41116132'\n" +
                    "WHEN prod.Tipo = 'Estandares' THEN  '41116107'\n" +
                    "WHEN prod.Tipo = 'Reactivos' THEN '41116105'\n" +
                    "WHEN prod.Tipo = 'Publicaciones' THEN '55101500'\n" +
                    "WHEN prod.Tipo = 'Capacitaciones' THEN '86101600'\n" +
                    "WHEN prod.Tipo = 'Labware' THEN '41116100'\n" +
                   // "WHEN prod.Tipo = 'Fletes' THEN  '78121603'\n" + La claveProdServ se cambia por indicacion del SAT, esta clave ya no es aceptada
                    "WHEN prod.Tipo = 'Fletes' THEN  '78102205'\n" +
                    "\n" +
                    "END codigo,CONCAT (CAST(year(ins.FPedimento)%100 as varchar(2)),' ',Aduana.Numero,' ',ins.Pedimento) as pedimento,pp.Cant as cant, pp.Concepto as descripcion,pp.Precio as valorUnitario,pp.codigo  as codigoInterno\n" +
                    "\n" +
                    "\n" +
                    "from productos as  prod \n" +
                    " left join PPedidos as pp on pp.FK02_Producto= prod.idProducto\n" +
                    " left join EmbalarPedido as ep on ep.FK01_PPedido =pp.idPPedido\n" +
                    " left join PPackingList as ppl on ppl.FK02_EmbalarPedido = ep.PK_EmbalarPedido\n" +
                    " left join PCompras as pc on pc.FK03_PPedido = pp.idPPedido\n" +
                    " left join  InspeccionOC as ins on ins.idPCompra=pc.idPCompra\n" +
                    " left join PListaArribo as pla on pla.FK02_PCompra=ins.idPCompra\n" +
                    " left join OrdenDespacho_ListaArribo as odla on odla.FK02_ListaArribo= pla.FK01_ListaArribo\n" +
                    " left join OrdenDespacho as OD on OD.PK_OrdenDespacho=odla.FK01_OrdenDespacho\n" +
                    " left join aduana as Aduana on Aduana.PK_Aduana= OD.FK05_Aduana\n" +
                    " where FK01_PackingList in (:packingListValues)");
            String[] packingListValues = packingList.split(",");
            List<Integer> packingListIntegers = new ArrayList<>();
            for (String value : packingListValues) {
                packingListIntegers.add(Integer.parseInt(value));
            }
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("packingListValues", packingListIntegers);
            log.info("Query obtenerProductosXPackingList:" + sbQuery+"PackingList:"+packingList);
            return  jdbcTemplate.query(sbQuery.toString(), parameters, new BeanPropertyRowMapper<>(Producto.class));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insertarCartaPorte(String vehiculo, String mensajero, String FE,String packingList) {
            try {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("vehiculo",vehiculo);
                map.put("mensajero",mensajero);
                map.put("FE",FE);
                map.put("packingList",packingList);

                String query = "INSERT INTO cartaPorte(FK01_FE,FK02_vehiculo,FK03_mensajero,FK04_packingList)"+
                        "VALUES(:FE, :vehiculo,:mensajero,:packingList)";
                super.jdbcTemplate.update(query,map);
                return true;
            }catch(Exception e){
                e.printStackTrace();
            }


        return false;
    }

    @Override
    public CartaPorte obtenerInfoCartaPorte(int FElectronica) {
        try {
            CartaPorte cPorte = new CartaPorte();
            String sbQuery =
            "select vh.placaVM ,\n" +
                    "vh.permisoSCT as permSCT,\n" +
                    "vh.configuracion_vehicular as configVehicular,\n" +
                    "vh.aseguradora as aseguraRespCivil,\n" +
                    "vh.anio_modelo as anioModelo,\n" +
                    "vh.poliza as polizaRespCivil, \n" +
                    "vh.numPermiso as numPermSCT, \n" +
                    "concat (msj.nombre,' ',msj.apellido_paterno,' ',msj.apellido_materno) as mensajero,\n" +
                    "msj.RFC as rfcMensajero,\n" +
                    "msj.licencia,\n" +
                    "concat (msj.estado,',calle:',msj.calle,',CP:',msj.codigo_postal) as calleDomicilio,\n" +
                    "COALESCE(msj.num_ext,0)as numExtDomicilio,\n" +
                    "COALESCE(msj.num_int,0)as numInt, \n" +
                    "msj.estado,\n" +
                    "msj.pais,\n" +
                    "msj.codigo_postal as cPostalDomicilio from facturaElectronica as FE \n" +
                    "left join cartaPorte as cPorte on FE.pk_factura = cPorte.FK01_FE\n" +
                    "left join mensajero as msj on msj.pk_mensajero = cPorte.FK03_mensajero\n" +
                    "left join vehiculo as vh on vh.pk_vehiculo = cPorte.FK02_vehiculo\n" +
                    "where FE.pk_factura=:FElectronica";

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("FElectronica",FElectronica);
            List<CartaPorte> lstCPorte = super.jdbcTemplate.query(sbQuery, map,new BeanPropertyRowMapper<>(CartaPorte.class));
            cPorte= lstCPorte.get(0);
            return  cPorte;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<PFacturaElectronica> obtenerPartidasFElectronica(int FElectronica) {
        try{
            StringBuilder sbQuery = new StringBuilder("\n");

            sbQuery.append("select descripcion,cantidad,claveUnidad,unidad,claveProdServ,importe,noIdentificacion,valorUnitario,adnNumeroPedimento from pfacturaElectronica where FK01_FacturaElectronica=:FElectronica");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("FElectronica",FElectronica);
            return  jdbcTemplate.query(sbQuery.toString(), map, new BeanPropertyRowMapper<>(PFacturaElectronica.class));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<String> obtenerListadoPackingList(String responsable) {
        try {
            String sbQuery = "\n";
            sbQuery +="SELECT\n" +
                    "  STUFF((\n" +
                    "  \n" +
                    "    SELECT Distinct(', ' + CAST(PL.PK_PackingList AS VARCHAR(MAX)))\n" +
                    "    FROM RutaDP AS R\n" +
                    "    inner JOIN PackingList AS PL ON PL.PK_PackingList = R.FK02_PackingList\n" +
                    "    WHERE   R.idCliente=CLI.Clave   and R.EstadoRuta='AEjecutar' FOR XML PATH('')\n" +
                    "  \n" +
                    "  ), 1, 2, ''\n" +
                    "  \n" +
                    "  \n" +
                    "  ) AS PLXCliente\n" +
                    "FROM RutaDP AS RUTA\n" +
                    "LEFT JOIN Empresa AS Emp ON Emp.Alias COLLATE SQL_Latin1_General_CP1_CI_AS = RUTA.FPor\n" +
                    "LEFT JOIN PPackingList AS PPKL ON RUTA.FK02_PackingList = PPKL.FK01_PackingList\n" +
                    "  AND (\n" +
                    "    PPKL.Fk03_facturaElectronica IN (\n" +
                    "      SELECT PK_Factura\n" +
                    "      FROM FacturaElectronica FE1\n" +
                    "      WHERE FE1.Folio COLLATE SQL_Latin1_General_CP1_CI_AS = RUTA.Factura COLLATE SQL_Latin1_General_CP1_CI_AS\n" +
                    "      AND TipoComprobante = 'I'\n" +
                    "      AND FE1.EmpresaEmisor = Emp.PK_Empresa\n" +
                    "    )\n" +
                    "    OR PPKL.Fk04_Remision IN (\n" +
                    "      SELECT PK_Remision\n" +
                    "      FROM Remisiones REM\n" +
                    "      WHERE REM.Factura COLLATE SQL_Latin1_General_CP1_CI_AS = RUTA.Factura COLLATE SQL_Latin1_General_CP1_CI_AS\n" +
                    "    )\n" +
                    "  )\n" +
                    "LEFT JOIN EmbalarPedido AS EP ON PPKL.FK02_EmbalarPEdido = EP.PK_EmbalarPEdido\n" +
                    "LEFT JOIN PPedidos AS PP ON EP.FK01_PPEdido = PP.IdPPEdido\n" +
                    "LEFT JOIN PEdidos AS PE ON PE.CPedido = PP.CPedido\n" +
                    "LEFT JOIN AsignarMensajero AS AM ON AM.Evento = RUTA.idDP\n" +
                    "LEFT JOIN Clientes AS CLI ON CLI.Clave = RUTA.idCliente\n" +
                    "LEFT JOIN ValorCombo AS VC ON VC.PK_Folio = CLI.FK04_UsoCFDI\n" +
                    "LEFT JOIN Pendiente AS PEN ON PEN.Docto COLLATE SQL_Latin1_General_CP1_CI_AS = RUTA.idRuta COLLATE SQL_Latin1_General_CP1_CI_AS\n" +
                    "LEFT JOIN HorarioyLugar AS HL ON HL.idCliente = RUTA.idCliente\n" +
                    "  AND HL.idHorario = PE.FK01_Horario\n" +
                    "WHERE\n" +
                    "  PEN.Tipo = 'Ruta a ejecutar'\n" +
                    "  AND RUTA.EstadoRuta = 'AEjecutar'\n" +
                    "  AND (\n" +
                    "    HL.cp BETWEEN '44000' AND '49999'\n" +
                    "    OR HL.cp BETWEEN '62000' AND '62999'\n" +
                    "  )\n" +
                    "  AND (\n" +
                    "    HL.cp NOT IN ('4650')\n" +
                    "    OR CONCAT(HL.Estado, ',', HL.Municipio, ',', HL.ZonaMensajeria) LIKE '%toluca%'\n" +
                    "  )\n" +
                    "  AND PEN.FFin IS NULL\n" +
                    "  AND RUTA.FK02_PackingList IS NOT NULL and PEN.Responsable=:responsable\n" +
                    "GROUP BY\n" +
                    "  CLI.Nombre,\n" +
                    "  CLI.Clave,\n" +
                    "  CLI.CURP,\n" +
                    "  CLI.CP,\n" +
                    "  CLI.RegimenFiscal,\n" +
                    "  VC.tipo,"+" CLI.Estado,\n" +
                    "  CLI.RSCalle,\n" +
                    "  CLI.RegimenSocietario";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("responsable",responsable);
            log.info("QueryListado:"+sbQuery.toString());
            return  super.jdbcTemplate.queryForList(sbQuery,map, String.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean aplicaUpdateConsecutivo() {
            int cont=0;
        try {
                List <String> valores= obtenerValores();
                for (String valor: valores){
                    cont ++;
                    System.out.println(valor + " contador:"+ cont);

            StringBuilder sbQuery = new StringBuilder("UPDATE Stock SET idpcompra = idpcompra -"+cont+" where idStock="+valor);

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("idMensajero", "");

            jdbcTemplate.update(sbQuery.toString(), map);
                }
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    public List<String> obtenerValores(){
       try {
           String sbQuery = "\n";

           sbQuery +="select top 366 idstock from Stock where idpcompra=:idpcompra order by idStock desc";
           Map<String, Object> map = new HashMap<String, Object>();
           map.put("idpcompra", "1955018630");
           return  super.jdbcTemplate.queryForList(sbQuery,map, String.class);
       }catch(Exception e){
           e.printStackTrace();
       }
       return null;
    }



}
