package com.proquifa.net.persistencia.despachos.mensajero.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.comun.Documento;
import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.despachos.mensajero.AsignarMensajero;
import com.proquifa.net.modelo.despachos.mensajero.ColectarMensajero;
import com.proquifa.net.modelo.despachos.mensajero.ComentaiosRutaDP;
import com.proquifa.net.modelo.despachos.mensajero.ConfirmacionEntrega;
import com.proquifa.net.modelo.despachos.mensajero.PendientesMensajero;
import com.proquifa.net.modelo.despachos.mensajero.PersonalAlmacenCliente;
import com.proquifa.net.modelo.despachos.mensajero.Recorrido;
import com.proquifa.net.modelo.despachos.mensajero.TotalMensajero;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.despachos.impl.mapper.ColectarMensajerosRowMapper;
import com.proquifa.net.persistencia.despachos.mensajero.MensajeroDAO;
import com.proquifa.net.persistencia.despachos.mensajero.impl.mapper.AsignarMensajeroDetalleRowMapper;
import com.proquifa.net.persistencia.despachos.mensajero.impl.mapper.ConfirmacionEntregaResumenRowMapper;
import com.proquifa.net.persistencia.despachos.mensajero.impl.mapper.ConfirmacionEntregaRowMapper;
import com.proquifa.net.persistencia.despachos.mensajero.impl.mapper.DocumentosDeRutaRowMapper;
import com.proquifa.net.persistencia.despachos.mensajero.impl.mapper.PendienteRutaTrabajarRowMapper;
import com.proquifa.net.persistencia.despachos.mensajero.impl.mapper.PendientesCerradosRowMapper;
import com.proquifa.net.persistencia.despachos.mensajero.impl.mapper.PendientesMensajeroPLRowMapper;
import com.proquifa.net.persistencia.despachos.mensajero.impl.mapper.PendientesMensajeroRowMapper;
import com.proquifa.net.persistencia.despachos.mensajero.impl.mapper.PersonalAlmacenClienteRowMapper;


@Repository
public class MensajeroDAOImpl extends DataBaseDAO implements MensajeroDAO{
	
	final Logger log = LoggerFactory.getLogger(MensajeroDAOImpl.class);

	@SuppressWarnings("unchecked")
	public List<PendientesMensajero> findPendientesDeRutas(String responsable, String tipoPendiente) throws ProquifaNetException {
		try{
			String query=
					" \n  SELECT HL.Latitud,HL.Longitud,HL.Altitud,RUTA.Latitud AS RLatitud,  RUTA.Longitud AS RLongitud, RUTA.Altitud AS RAltitud,RUTA.FK01_Direccion,"+
							" \n  (CASE WHEN contactoUno.Contacto='--NINGUNO--' OR contactoUno.Contacto IS NULL OR contactoUno.Contacto='' THEN contactoUno.Destino "+
							" \n  ELSE (CASE WHEN contactoUno.Contacto  = 'ALMACEN' THEN 'Almacén' ELSE contactoUno.Contacto  END) END) AS Contacto1,contactoUno.Titulo AS Titulo1,contactoUno.Puesto AS Puesto1 ,contactoUno.Departamento AS Depto1,"+
							" \n  contactoUno.Tel AS Tel1,NULL AS Contacto2 ,NULL AS Titulo2,NULL AS Puesto2,NULL AS Depto2,NULL AS Tel2,RUTA.idEntrega, PEN.Folio,'Entrega' AS TEvento,CLI.Clave,CLI.Nombre,RUTA.ZonaMensajeria, Prioridad,"+
							" \n  RUTA.Calle + ' ' + RUTA.Delegacion + ' ' + RUTA.CP as Direccion, RUTA.Mapa,RUTA.idDP as folioEvento, OrdenPlan,chkDiferente AS DifDireccion, CAST(ComentariosAdicionales AS VARCHAR(500)) AS ComentariosAdicionales,"+
							" \n  CASE WHEN chkDiferente = 0 THEN HL.Diario ELSE RUTA.Diario END AS Diario, CASE WHEN chkDiferente = 0 THEN HL.DiaDe1 ELSE RUTA.DiaDe1 END AS DiaDe1,"+
							" \n  CASE WHEN chkDiferente = 0 THEN HL.DiaA1 ELSE RUTA.DiaA1 END AS DiaA1,CASE WHEN chkDiferente = 0 THEN HL.DiaDe2 ELSE RUTA.DiaDe2 END AS DiaDe2,"+
							" \n  CASE WHEN chkDiferente = 0 THEN HL.DiaA2 ELSE RUTA.DiaA2 END AS DiaA2,CASE WHEN chkDiferente = 0 THEN HL.Lunes ELSE RUTA.Lunes END AS Lunes,"+
							" \n  CASE WHEN chkDiferente = 0 THEN HL.LuDe1 ELSE RUTA.LuDe1 END AS LuDe1,CASE WHEN chkDiferente = 0 THEN HL.LuA1 ELSE RUTA.LuA1 END AS LuA1,"+
							" \n  CASE WHEN chkDiferente = 0 THEN HL.LuDe2 ELSE RUTA.LuDe2 END AS LuDe2,CASE WHEN chkDiferente = 0 THEN HL.LuA2 ELSE RUTA.LuA2 END AS LuA2,"+
							" \n  CASE WHEN chkDiferente = 0 THEN HL.Martes ELSE RUTA.Martes END AS Martes,CASE WHEN chkDiferente = 0 THEN HL.MaDe1 ELSE RUTA.MaDe1 END AS MaDe1,"+
							" \n  CASE WHEN chkDiferente = 0 THEN HL.MaA1 ELSE RUTA.MaA1 END AS MaA1,CASE WHEN chkDiferente = 0 THEN HL.MaDe2 ELSE RUTA.MaDe2 END AS MaDe2,"+
							" \n  CASE WHEN chkDiferente = 0 THEN HL.MaA2 ELSE RUTA.MaA2 END AS MaA2,CASE WHEN chkDiferente = 0 THEN HL.Miercoles ELSE RUTA.Miercoles END AS Miercoles,"+
							" \n  CASE WHEN chkDiferente = 0 THEN HL.MiDe1 ELSE RUTA.MiDe1 END AS MiDe1,CASE WHEN chkDiferente = 0 THEN HL.MiA1 ELSE RUTA.MiA1 END AS MiA1,"+
							" \n  CASE WHEN chkDiferente = 0 THEN HL.MiDe2 ELSE RUTA.MiDe2 END AS MiDe2,CASE WHEN chkDiferente = 0 THEN HL.MiA2 ELSE RUTA.MiA2 END AS MiA2,"+
							" \n  CASE WHEN chkDiferente = 0 THEN HL.Jueves ELSE RUTA.Jueves END AS Jueves,CASE WHEN chkDiferente = 0 THEN HL.JuDe1 ELSE RUTA.JuDe1 END AS JuDe1,"+
							" \n  CASE WHEN chkDiferente = 0 THEN HL.JuA1 ELSE RUTA.JuA1 END AS JuA1,CASE WHEN chkDiferente = 0 THEN HL.JuDe2 ELSE RUTA.JuDe2 END AS JuDe2,"+
							" \n  CASE WHEN chkDiferente = 0 THEN HL.JuA2 ELSE RUTA.JuA2 END AS JuA2,CASE WHEN chkDiferente = 0 THEN HL.Viernes ELSE RUTA.Viernes END AS Viernes,"+
							" \n  CASE WHEN chkDiferente = 0 THEN HL.ViDe1 ELSE RUTA.ViDe1 END AS ViDe1,CASE WHEN chkDiferente = 0 THEN HL.ViA1 ELSE RUTA.ViA1 END AS ViA1,"+
							" \n  CASE WHEN chkDiferente = 0 THEN HL.ViDe2 ELSE RUTA.ViDe2 END AS ViDe2,CASE WHEN chkDiferente = 0 THEN HL.ViA2 ELSE RUTA.ViA2 END AS ViA2, 0 as idProveedor "+
							" \n  FROM RutaDP AS RUTA "+
							" \n  LEFT JOIN(SELECT * FROM Clientes) AS CLI ON CLI.Clave = RUTA.idCliente "+
							" \n  LEFT JOIN(SELECT * FROM Pendiente) AS PEN ON PEN.Docto = RUTA.idRuta "+
							" \n  LEFT JOIN(SELECT * FROM HorarioyLugar WHERE Tipo='ENTREGA') AS HL ON HL.idCliente = RUTA.idCliente AND HL.Destino=RUTA.Destino AND HL.idHorario=RUTA.FK01_Direccion "+
							" \n  LEFT JOIN(SELECT RutaDP.idDP,Pedidos.Destino,Pedidos.Contacto,Pedidos.Titulo,Pedidos.Puesto,Pedidos.Departamento,Pedidos.Tel FROM Pedidos,Facturas,RutaDP WHERE Pedidos.CPedido=Facturas.CPedido AND "+
							" \n  Facturas.Factura=RutaDP.Factura AND Facturas.FPor=RutaDP.FPor AND Facturas.Cliente=RutaDP.idCliente) AS contactoUno ON contactoUno.idDP=RUTA.idDP "+
							" \n  WHERE PEN.Tipo = '" + tipoPendiente + "' AND RUTA.EstadoRuta = 'AEjecutar' AND Responsable = '" + responsable + "' " +
							" \n   UNION" +





						" \n   SELECT  HL.Latitud,HL.Longitud,HL.Altitud,RUTA.Latitud AS RLatitud,  RUTA.Longitud AS RLongitud, RUTA.Altitud AS RAltitud,RUTA.FK01_Direccion,NULL AS Contacto1 ,NULL AS Titulo1,NULL AS Puesto1 ,NULL AS Depto1 ,NULL AS Tel1 ,NULL AS Contacto2 ,NULL AS Titulo2,NULL AS Puesto2,NULL AS Depto2 ,NULL AS Tel2 ," +
						" \n   NULL AS idEntrega, PEN.Folio, 'Revision' AS TEvento, CLI.Clave, Nombre, RUTA.Zona, Prioridad," +
						" \n   RUTA.Calle + ' ' + RUTA.Delegacion + ' ' + RUTA.CP  as Direccion, RUTA.Mapa, idPR as folioEvento, OrdenPlan," +
						" \n   DifDireccion, CAST(Descripcion AS VARCHAR(500)) As ComentariosAdicionales," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.Diario ELSE RUTA.Diario END AS Diario, CASE WHEN DifDireccion = 0 THEN HL.DiaDe1 ELSE RUTA.DiaDe1 END AS DiaDe1," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.DiaA1 ELSE RUTA.DiaA1 END AS DiaA1, CASE WHEN DifDireccion = 0 THEN HL.DiaDe2 ELSE RUTA.DiaDe2 END AS DiaDe2," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.DiaA2 ELSE RUTA.DiaA2 END AS DiaA2, CASE WHEN DifDireccion = 0 THEN HL.Lunes ELSE RUTA.Lunes END AS Lunes," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.LuDe1 ELSE RUTA.LuDe1 END AS LuDe1, CASE WHEN DifDireccion = 0 THEN HL.LuA1 ELSE RUTA.LuA1 END AS LuA1," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.LuDe2 ELSE RUTA.LuDe2 END AS LuDe2, CASE WHEN DifDireccion = 0 THEN HL.LuA2 ELSE RUTA.LuA2 END AS LuA2," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.Martes ELSE RUTA.Martes END AS Martes, CASE WHEN DifDireccion = 0 THEN HL.MaDe1 ELSE RUTA.MaDe1 END AS MaDe1," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.MaA1 ELSE RUTA.MaA1 END AS MaA1, CASE WHEN DifDireccion = 0 THEN HL.MaDe2 ELSE RUTA.MaDe2 END AS MaDe2," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.MaA2 ELSE RUTA.MaA2 END AS MaA2, CASE WHEN DifDireccion = 0 THEN HL.Miercoles ELSE RUTA.Miercoles END AS Miercoles," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.MiDe1 ELSE RUTA.MiDe1 END AS MiDe1, CASE WHEN DifDireccion = 0 THEN HL.MiA1 ELSE RUTA.MiA1 END AS MiA1," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.MiDe2 ELSE RUTA.MiDe2 END AS MiDe2, CASE WHEN DifDireccion = 0 THEN HL.MiA2 ELSE RUTA.MiA2 END AS MiA2," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.Jueves ELSE RUTA.Jueves END AS Jueves, CASE WHEN DifDireccion = 0 THEN HL.JuDe1 ELSE RUTA.JuDe1 END AS JuDe1," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.JuA1 ELSE RUTA.JuA1 END AS JuA1, CASE WHEN DifDireccion = 0 THEN HL.JuDe2 ELSE RUTA.JuDe2 END AS JuDe2," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.JuA2 ELSE RUTA.JuA2 END AS JuA2, CASE WHEN DifDireccion = 0 THEN HL.Viernes ELSE RUTA.Viernes END AS Viernes," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.ViDe1 ELSE RUTA.ViDe1 END AS ViDe1, CASE WHEN DifDireccion = 0 THEN HL.ViA1 ELSE RUTA.ViA1 END AS ViA1," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.ViDe2 ELSE RUTA.ViDe2 END AS ViDe2, CASE WHEN DifDireccion = 0 THEN HL.ViA2 ELSE RUTA.ViA2 END AS ViA2, 0 as idProveedor" +
						" \n   FROM RutaPR AS RUTA" +
						" \n   LEFT JOIN (SELECT * FROM Clientes) AS CLI ON CLI.Clave = RUTA.idCliente" +
						" \n   LEFT JOIN (SELECT * FROM Pendiente) AS PEN ON PEN.Docto = RUTA.idRuta" +
						" \n   LEFT JOIN (SELECT * FROM HorarioyLugar WHERE Tipo='Revision') AS HL ON HL.idCliente = RUTA.idCliente AND HL.idHorario=RUTA.FK01_Direccion " +
						" \n   WHERE PEN.Tipo = '" + tipoPendiente + "' AND EstadoRuta = 'AEjecutar'   AND Responsable = '" + responsable + "'" +
						" \n   UNION" +






						" \n   SELECT  HL.Latitud,HL.Longitud,HL.Altitud,RUTA.Latitud AS RLatitud, RUTA.Longitud AS RLongitud, RUTA.Altitud AS RAltitud,RUTA.FK01_Direccion,NULL AS Contacto1 ,NULL AS Titulo1,NULL AS Puesto1 ,NULL AS Depto1 ,NULL AS Tel1 ,NULL AS Contacto2 ,NULL AS Titulo2,NULL AS Puesto2,NULL AS Depto2 ,NULL AS Tel2 ," +
						" \n   NULL AS idEntrega, PEN.Folio, 'Cobro' AS TEvento, CLI.Clave, Nombre, RUTA.Zona, Prioridad, " +
						" \n   RUTA.Calle + ' ' + RUTA.Delegacion + ' ' + RUTA.CP  as Direccion, RUTA.Mapa, idPC as folioEvento, OrdenPlan," +
						" \n   DifDireccion, CAST(Descripcion AS VARCHAR(500)) As ComentariosAdicionales," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.Diario ELSE RUTA.Diario END AS Diario, CASE WHEN DifDireccion = 0 THEN HL.DiaDe1 ELSE RUTA.DiaDe1 END AS DiaDe1," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.DiaA1 ELSE RUTA.DiaA1 END AS DiaA1, CASE WHEN DifDireccion = 0 THEN HL.DiaDe2 ELSE RUTA.DiaDe2 END AS DiaDe2," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.DiaA2 ELSE RUTA.DiaA2 END AS DiaA2, CASE WHEN DifDireccion = 0 THEN HL.Lunes ELSE RUTA.Lunes END AS Lunes," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.LuDe1 ELSE RUTA.LuDe1 END AS LuDe1, CASE WHEN DifDireccion = 0 THEN HL.LuA1 ELSE RUTA.LuA1 END AS LuA1," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.LuDe2 ELSE RUTA.LuDe2 END AS LuDe2, CASE WHEN DifDireccion = 0 THEN HL.LuA2 ELSE RUTA.LuA2 END AS LuA2," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.Martes ELSE RUTA.Martes END AS Martes, CASE WHEN DifDireccion = 0 THEN HL.MaDe1 ELSE RUTA.MaDe1 END AS MaDe1," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.MaA1 ELSE RUTA.MaA1 END AS MaA1, CASE WHEN DifDireccion = 0 THEN HL.MaDe2 ELSE RUTA.MaDe2 END AS MaDe2," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.MaA2 ELSE RUTA.MaA2 END AS MaA2, CASE WHEN DifDireccion = 0 THEN HL.Miercoles ELSE RUTA.Miercoles END AS Miercoles," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.MiDe1 ELSE RUTA.MiDe1 END AS MiDe1, CASE WHEN DifDireccion = 0 THEN HL.MiA1 ELSE RUTA.MiA1 END AS MiA1," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.MiDe2 ELSE RUTA.MiDe2 END AS MiDe2, CASE WHEN DifDireccion = 0 THEN HL.MiA2 ELSE RUTA.MiA2 END AS MiA2," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.Jueves ELSE RUTA.Jueves END AS Jueves, CASE WHEN DifDireccion = 0 THEN HL.JuDe1 ELSE RUTA.JuDe1 END AS JuDe1," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.JuA1 ELSE RUTA.JuA1 END AS JuA1, CASE WHEN DifDireccion = 0 THEN HL.JuDe2 ELSE RUTA.JuDe2 END AS JuDe2," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.JuA2 ELSE RUTA.JuA2 END AS JuA2, CASE WHEN DifDireccion = 0 THEN HL.Viernes ELSE RUTA.Viernes END AS Viernes," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.ViDe1 ELSE RUTA.ViDe1 END AS ViDe1, CASE WHEN DifDireccion = 0 THEN HL.ViA1 ELSE RUTA.ViA1 END AS ViA1," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.ViDe2 ELSE RUTA.ViDe2 END AS ViDe2, CASE WHEN DifDireccion = 0 THEN HL.ViA2 ELSE RUTA.ViA2 END AS ViA2, 0 as idProveedor" +
						" \n   FROM RutaPC AS RUTA " +
						" \n   LEFT JOIN (SELECT * FROM Clientes) AS CLI ON CLI.Clave = RUTA.idCliente" +
						" \n   LEFT JOIN (SELECT * FROM Pendiente) AS PEN ON PEN.Docto = RUTA.idRuta" +
						" \n   LEFT JOIN (SELECT * FROM HorarioyLugar WHERE Tipo='Cobro') AS HL ON HL.idCliente = RUTA.idCliente AND HL.idHorario=RUTA.FK01_Direccion " +
						" \n   WHERE PEN.Tipo = '" + tipoPendiente + "' AND RUTA.EstadoRuta = 'AEjecutar'   AND Responsable = '" + responsable + "'" +
						" \n   UNION" +





						" \n   SELECT HL.Latitud,HL.Longitud,HL.Altitud,RUTA.Latitud AS RLatitud ,RUTA.Longitud AS RLongitud, RUTA.Altitud AS RAltitud,RUTA.FK01_Direccion,RUTA.Contacto1,RUTA.Titulo1,RUTA.Puesto1,RUTA.Depto1,RUTA.Tel1,RUTA.Contacto2,RUTA.Titulo2,RUTA.Puesto2,RUTA.Depto2,RUTA.Tel2," +
						" \n   NULL AS idEntrega, PEN.Folio, 'Recolección' AS TEvento, PRO.Clave, Nombre, RUTA.Zona, Prioridad," +
						" \n   RUTA.Calle + ' ' + RUTA.Delegacion + ' ' + RUTA.CP  as Direccion, RUTA.Mapa, idRM as folioEvento, OrdenPlan," +
						" \n   DifDireccion, '' As ComentariosAdicionales, RUTA.Diario AS Diario, RUTA.DiaDe1 AS DiaDe1, RUTA.DiaA1 AS DiaA1, " +
						" \n   RUTA.DiaDe2 AS DiaDe2, RUTA.DiaA2 AS DiaA2, RUTA.Lunes AS Lunes, RUTA.LuDe1 AS LuDe1, RUTA.LuA1 AS LuA1, RUTA.LuDe2 AS LuDe2, " +
						" \n   RUTA.LuA2 AS LuA2, RUTA.Martes AS Martes, RUTA.MaDe1 AS MaDe1, RUTA.MaA1 AS MaA1, RUTA.MaDe2 AS MaDe2, RUTA.MaA2 AS MaA2, " +
						" \n   RUTA.Miercoles AS Miercoles, RUTA.MiDe1 AS MiDe1, RUTA.MiA1 AS MiA1, RUTA.MiDe2 AS MiDe2, RUTA.MiA2 AS MiA2, RUTA.Jueves AS Jueves, " +
						" \n   RUTA.JuDe1 AS JuDe1, RUTA.JuA1 AS JuA1, RUTA.JuDe2 AS JuDe2, RUTA.JuA2 AS JuA2, RUTA.Viernes AS Viernes, RUTA.ViDe1 AS ViDe1, " +
						" \n   RUTA.ViA1 AS ViA1, RUTA.ViDe2 AS ViDe2, RUTA.ViA2 AS ViA2, RUTA.idProveedor " +
						" \n   FROM RutaRM AS RUTA" +
						" \n   LEFT JOIN (SELECT * FROM Proveedores) AS PRO ON PRO.Clave = RUTA.idProveedor" +
						" \n   LEFT JOIN (SELECT * FROM Pendiente) AS PEN ON PEN.Docto = RUTA.idRuta" +
						" \n   LEFT JOIN (SELECT * FROM HorarioyLugar WHERE Tipo='Pago') AS HL ON HL.idProveedor = RUTA.idProveedor AND HL.idHorario=RUTA.FK01_Direccion " +
						" \n   WHERE PEN.Tipo = '" + tipoPendiente + "' AND RUTA.EstadoRuta = 'AEjecutar'   AND Responsable = '" + responsable + "'" +
						" \n   UNION" +
						" \n   SELECT HL.Latitud,HL.Longitud,HL.Altitud,RUTA.Latitud AS RLatitud, RUTA.Longitud AS RLongitud, RUTA.Altitud AS RAltitud,RUTA.FK01_Direccion,RUTA.Contacto1,RUTA.Titulo1,RUTA.Puesto1,RUTA.Depto1,RUTA.Tel1,RUTA.Contacto2,RUTA.Titulo2,RUTA.Puesto2,RUTA.Depto2,RUTA.Tel2," +
						" \n   NULL AS idEntrega, PEN.Folio, 'Recoleccion especial' AS TEvento, CLI.Clave, Nombre, RUTA.Zona, Prioridad," +
						" \n   RUTA.Calle + ' ' + RUTA.Delegacion + ' ' + RUTA.CP as Direccion, RUTA.Mapa, idRE as folioEvento, OrdenPlan, " +
						" \n   DifDireccion, '' As ComentariosAdicionales," +
						" \n   RUTA.Diario AS Diario, RUTA.DiaDe1 AS DiaDe1, RUTA.DiaA1 AS DiaA1, RUTA.DiaDe2 AS DiaDe2, RUTA.DiaA2 AS DiaA2, RUTA.Lunes AS Lunes," +
						" \n   RUTA.LuDe1 AS LuDe1, RUTA.LuA1 AS LuA1, RUTA.LuDe2 AS LuDe2, RUTA.LuA2 AS LuA2, RUTA.Martes AS Martes, RUTA.MaDe1 AS MaDe1," +
						" \n   RUTA.MaA1 AS MaA1, RUTA.MaDe2 AS MaDe2, RUTA.MaA2 AS MaA2, RUTA.Miercoles AS Miercoles, RUTA.MiDe1 AS MiDe1, RUTA.MiA1 AS MiA1," +
						" \n   RUTA.MiDe2 AS MiDe2, RUTA.MiA2 AS MiA2, RUTA.Jueves AS Jueves, RUTA.JuDe1 AS JuDe1, RUTA.JuA1 AS JuA1, RUTA.JuDe2 AS JuDe2," +
						" \n   RUTA.JuA2 AS JuA2, RUTA.Viernes AS Viernes, RUTA.ViDe1 AS ViDe1, RUTA.ViA1 AS ViA1, RUTA.ViDe2 AS ViDe2, RUTA.ViA2 AS ViA2, 0 as idProveedor" +
						" \n   FROM RutaRE AS RUTA" +
						" \n   LEFT JOIN (SELECT * FROM Clientes) AS CLI ON CLI.Clave = RUTA.idCliente" +
						" \n   LEFT JOIN (SELECT * FROM Pendiente) AS PEN ON PEN.Docto = RUTA.idRuta" +
						" \n   LEFT JOIN (SELECT * FROM HorarioyLugar WHERE Tipo='Recoleccion') AS HL ON HL.idCliente = RUTA.idCliente AND HL.idHorario=RUTA.FK01_Direccion " +
						" \n   WHERE PEN.Tipo = '" + tipoPendiente + "' AND RUTA.EstadoRuta = 'AEjecutar'   AND Responsable = '" + responsable + "'" +
						" \n   UNION" +






						" \n  SELECT HL.Latitud,HL.Longitud,HL.Altitud,RUTA.Latitud AS RLatitud, RUTA.Longitud AS RLongitud, RUTA.Altitud AS RAltitud,RUTA.FK01_Direccion,RUTA.Contacto1,RUTA.Titulo1,RUTA.Puesto1,RUTA.Depto1,RUTA.Tel1,RUTA.Contacto2,RUTA.Titulo2,RUTA.Puesto2,RUTA.Depto2,RUTA.Tel2," +
						" \n  NULL AS idEntrega, PEN.Folio, 'Entrega especial' AS TEvento, CLI.Clave, Nombre, RUTA.Zona, Prioridad," +
						" \n  RUTA.Calle + ' ' + RUTA.Delegacion + ' ' + RUTA.CP as Direccion, RUTA.Mapa, idES as folioEvento, OrdenPlan," +
						" \n  DifDireccion, '' As ComentariosAdicionales," + 
						" \n  CASE WHEN DifDireccion = 0 THEN HL.Diario ELSE RUTA.Diario END AS Diario, CASE WHEN DifDireccion = 0 THEN HL.DiaDe1 ELSE RUTA.DiaDe1 END AS DiaDe1," +
						" \n  CASE WHEN DifDireccion = 0 THEN HL.DiaA1 ELSE RUTA.DiaA1 END AS DiaA1, CASE WHEN DifDireccion = 0 THEN HL.DiaDe2 ELSE RUTA.DiaDe2 END AS DiaDe2," +
						" \n  CASE WHEN DifDireccion = 0 THEN HL.DiaA2 ELSE RUTA.DiaA2 END AS DiaA2, CASE WHEN DifDireccion = 0 THEN HL.Lunes ELSE RUTA.Lunes END AS Lunes," +
						" \n  CASE WHEN DifDireccion = 0 THEN HL.LuDe1 ELSE RUTA.LuDe1 END AS LuDe1, CASE WHEN DifDireccion = 0 THEN HL.LuA1 ELSE RUTA.LuA1 END AS LuA1," +
						" \n  CASE WHEN DifDireccion = 0 THEN HL.LuDe2 ELSE RUTA.LuDe2 END AS LuDe2, CASE WHEN DifDireccion = 0 THEN HL.LuA2 ELSE RUTA.LuA2 END AS LuA2," +
						" \n  CASE WHEN DifDireccion = 0 THEN HL.Martes ELSE RUTA.Martes END AS Martes, CASE WHEN DifDireccion = 0 THEN HL.MaDe1 ELSE RUTA.MaDe1 END AS MaDe1," +
						" \n  CASE WHEN DifDireccion = 0 THEN HL.MaA1 ELSE RUTA.MaA1 END AS MaA1, CASE WHEN DifDireccion = 0 THEN HL.MaDe2 ELSE RUTA.MaDe2 END AS MaDe2," +
						" \n  CASE WHEN DifDireccion = 0 THEN HL.MaA2 ELSE RUTA.MaA2 END AS MaA2, CASE WHEN DifDireccion = 0 THEN HL.Miercoles ELSE RUTA.Miercoles END AS Miercoles," +
						" \n  CASE WHEN DifDireccion = 0 THEN HL.MiDe1 ELSE RUTA.MiDe1 END AS MiDe1, CASE WHEN DifDireccion = 0 THEN HL.MiA1 ELSE RUTA.MiA1 END AS MiA1," +
						" \n  CASE WHEN DifDireccion = 0 THEN HL.MiDe2 ELSE RUTA.MiDe2 END AS MiDe2, CASE WHEN DifDireccion = 0 THEN HL.MiA2 ELSE RUTA.MiA2 END AS MiA2," +
						" \n  CASE WHEN DifDireccion = 0 THEN HL.Jueves ELSE RUTA.Jueves END AS Jueves, CASE WHEN DifDireccion = 0 THEN HL.JuDe1 ELSE RUTA.JuDe1 END AS JuDe1," +
						" \n  CASE WHEN DifDireccion = 0 THEN HL.JuA1 ELSE RUTA.JuA1 END AS JuA1, CASE WHEN DifDireccion = 0 THEN HL.JuDe2 ELSE RUTA.JuDe2 END AS JuDe2," +
						" \n  CASE WHEN DifDireccion = 0 THEN HL.JuA2 ELSE RUTA.JuA2 END AS JuA2, CASE WHEN DifDireccion = 0 THEN HL.Viernes ELSE RUTA.Viernes END AS Viernes," +
						" \n  CASE WHEN DifDireccion = 0 THEN HL.ViDe1 ELSE RUTA.ViDe1 END AS ViDe1, CASE WHEN DifDireccion = 0 THEN HL.ViA1 ELSE RUTA.ViA1 END AS ViA1," +
						" \n  CASE WHEN DifDireccion = 0 THEN HL.ViDe2 ELSE RUTA.ViDe2 END AS ViDe2, CASE WHEN DifDireccion = 0 THEN HL.ViA2 ELSE RUTA.ViA2 END AS ViA2, 0 as idProveedor" +
						" \n  FROM RutaES AS RUTA" +
						" \n  LEFT JOIN (SELECT * FROM Clientes) AS CLI ON CLI.Clave = RUTA.idCliente" +
						" \n  LEFT JOIN (SELECT * FROM Pendiente ) AS PEN ON PEN.Docto = RUTA.idRuta" +
						" \n  LEFT JOIN (SELECT * FROM HorarioyLugar WHERE Tipo='Entrega especial') AS HL ON HL.idCliente = RUTA.idCliente AND HL.idHorario=RUTA.FK01_Direccion " +
						" \n  WHERE PEN.Tipo = '" + tipoPendiente + "' AND RUTA.EstadoRuta = 'AEjecutar'   AND Responsable = '" + responsable + "'";
			log.info(query);
			return super.jdbcTemplate.query(query, new PendientesMensajeroRowMapper());
		}catch(Exception e){
			log.info(e.getMessage());
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	public List<PendientesMensajero> findPendientesDeRutasPL(Empleado responsable, String tipoPendiente, String estadoRuta) throws ProquifaNetException {
		try{
			
			String resp = "";
			
			if (responsable.getNivel() == 10 || responsable.getNivel() == 46) {
				resp = " AND Responsable ='"+ responsable.getUsuario() +"' ";
			}//Si el responsable es nivel 10 o nivel 46 a la consulta le agrega la condicion de SQL
			
			String query=
					" \n  SELECT distinct HL.Latitud,HL.Longitud,HL.Altitud, RUTA.Latitud AS RLatitud, RUTA.Longitud AS RLongitud, RUTA.Altitud AS RAltitud,RUTA.FK01_Direccion, HL.Actualizar," + 
							" \n  (CASE WHEN contactoUno.Contacto='--NINGUNO--' OR contactoUno.Contacto IS NULL OR contactoUno.Contacto='' THEN contactoUno.Destino " + 
							" \n  ELSE (CASE WHEN contactoUno.Contacto  = 'ALMACEN' THEN 'Almacén' ELSE contactoUno.Contacto  END) END) AS Contacto1,contactoUno.Titulo AS Titulo1,contactoUno.Puesto AS Puesto1 ,contactoUno.Departamento AS Depto1," + 
							" \n  contactoUno.Tel AS Tel1,NULL AS Contacto2 ,NULL AS Titulo2,NULL AS Puesto2,NULL AS Depto2,NULL AS Tel2,ppkl.folio collate SQL_Latin1_General_CP1_CI_AS  as idEntrega ,PEN.Folio,'Entrega' AS TEvento,CLI.Clave,CLI.Nombre,RUTA.ZonaMensajeria, RUTA.Prioridad, P.Manejo," + 
							" \n  RUTA.Calle + ' ' + RUTA.Delegacion + ' ' + RUTA.CP as Direccion, RUTA.Mapa, pkl.folio collate SQL_Latin1_General_CP1_CI_AS as folioEvento, OrdenPlan,chkDiferente AS DifDireccion, " +
							" \n  CAST(ComentariosAdicionales AS VARCHAR(500)) AS  ComentariosAdicionales, AM.orden, " +
							" \n  CASE WHEN chkDiferente = 0 AND RUTA.FK01_Direccion  IS NOT NULL THEN HL.Diario ELSE RUTA.Diario END AS Diario, CASE WHEN chkDiferente = 0 AND RUTA.FK01_Direccion  IS NOT NULL THEN HL.DiaDe1 ELSE RUTA.DiaDe1 END AS DiaDe1," + 
							" \n  CASE WHEN chkDiferente = 0 AND RUTA.FK01_Direccion  IS NOT NULL THEN HL.DiaA1 ELSE RUTA.DiaA1 END AS DiaA1,CASE WHEN chkDiferente = 0 AND RUTA.FK01_Direccion  IS NOT NULL THEN HL.DiaDe2 ELSE RUTA.DiaDe2 END AS DiaDe2, " +
							" \n  CASE WHEN chkDiferente = 0 AND RUTA.FK01_Direccion  IS NOT NULL THEN HL.DiaA2 ELSE RUTA.DiaA2 END AS DiaA2,CASE WHEN chkDiferente = 0 AND RUTA.FK01_Direccion  IS NOT NULL THEN HL.Lunes ELSE RUTA.Lunes END AS Lunes, " +
							" \n  CASE WHEN chkDiferente = 0 AND RUTA.FK01_Direccion  IS NOT NULL THEN HL.LuDe1 ELSE RUTA.LuDe1 END AS LuDe1,CASE WHEN chkDiferente = 0 AND RUTA.FK01_Direccion  IS NOT NULL THEN HL.LuA1 ELSE RUTA.LuA1 END AS LuA1, " +
							" \n  CASE WHEN chkDiferente = 0 AND RUTA.FK01_Direccion  IS NOT NULL THEN HL.LuDe2 ELSE RUTA.LuDe2 END AS LuDe2,CASE WHEN chkDiferente = 0 AND RUTA.FK01_Direccion  IS NOT NULL THEN HL.LuA2 ELSE RUTA.LuA2 END AS LuA2, " +
							" \n  CASE WHEN chkDiferente = 0 AND RUTA.FK01_Direccion  IS NOT NULL THEN HL.Martes ELSE RUTA.Martes END AS Martes,CASE WHEN chkDiferente = 0 AND RUTA.FK01_Direccion  IS NOT NULL THEN HL.MaDe1 ELSE RUTA.MaDe1 END AS MaDe1," + 
							" \n  CASE WHEN chkDiferente = 0 AND RUTA.FK01_Direccion  IS NOT NULL THEN HL.MaA1 ELSE RUTA.MaA1 END AS MaA1,CASE WHEN chkDiferente = 0 AND RUTA.FK01_Direccion  IS NOT NULL THEN HL.MaDe2 ELSE RUTA.MaDe2 END AS MaDe2, " +
							" \n  CASE WHEN chkDiferente = 0 AND RUTA.FK01_Direccion  IS NOT NULL THEN HL.MaA2 ELSE RUTA.MaA2 END AS MaA2,CASE WHEN chkDiferente = 0 AND RUTA.FK01_Direccion  IS NOT NULL THEN HL.Miercoles ELSE RUTA.Miercoles END AS Miercoles," + 
							" \n  CASE WHEN chkDiferente = 0 AND RUTA.FK01_Direccion  IS NOT NULL THEN HL.MiDe1 ELSE RUTA.MiDe1 END AS MiDe1,CASE WHEN chkDiferente = 0 AND RUTA.FK01_Direccion  IS NOT NULL THEN HL.MiA1 ELSE RUTA.MiA1 END AS MiA1, " +
							" \n  CASE WHEN chkDiferente = 0 AND RUTA.FK01_Direccion  IS NOT NULL THEN HL.MiDe2 ELSE RUTA.MiDe2 END AS MiDe2,CASE WHEN chkDiferente = 0 AND RUTA.FK01_Direccion  IS NOT NULL THEN HL.MiA2 ELSE RUTA.MiA2 END AS MiA2, " +
							" \n  CASE WHEN chkDiferente = 0 AND RUTA.FK01_Direccion  IS NOT NULL THEN HL.Jueves ELSE RUTA.Jueves END AS Jueves,CASE WHEN chkDiferente = 0 AND RUTA.FK01_Direccion  IS NOT NULL THEN HL.JuDe1 ELSE RUTA.JuDe1 END AS JuDe1," + 
							" \n  CASE WHEN chkDiferente = 0 AND RUTA.FK01_Direccion  IS NOT NULL THEN HL.JuA1 ELSE RUTA.JuA1 END AS JuA1,CASE WHEN chkDiferente = 0 AND RUTA.FK01_Direccion  IS NOT NULL THEN HL.JuDe2 ELSE RUTA.JuDe2 END AS JuDe2, " +
							" \n  CASE WHEN chkDiferente = 0 AND RUTA.FK01_Direccion  IS NOT NULL THEN HL.JuA2 ELSE RUTA.JuA2 END AS JuA2,CASE WHEN chkDiferente = 0 AND RUTA.FK01_Direccion  IS NOT NULL THEN HL.Viernes ELSE RUTA.Viernes END AS Viernes," + 
							" \n  CASE WHEN chkDiferente = 0 AND RUTA.FK01_Direccion  IS NOT NULL THEN HL.ViDe1 ELSE RUTA.ViDe1 END AS ViDe1,CASE WHEN chkDiferente = 0 AND RUTA.FK01_Direccion  IS NOT NULL THEN HL.ViA1 ELSE RUTA.ViA1 END AS ViA1, " +
							" \n  CASE WHEN chkDiferente = 0 AND RUTA.FK01_Direccion  IS NOT NULL THEN HL.ViDe2 ELSE RUTA.ViDe2 END AS ViDe2,CASE WHEN chkDiferente = 0 AND RUTA.FK01_Direccion  IS NOT NULL THEN HL.ViA2 ELSE RUTA.ViA2 END AS ViA2, 0 as idProveedor, RUTA.idDP, RUTA.idRuta" +
							" \n  FROM RutaDP AS RUTA  " +
							" \n  LEFT JOIN(SELECT * FROM PackingList) AS PKL  ON RUTA.FK02_PackingList = PKL.PK_PackingList" +
							" \n  LEFT JOIN Empresa AS Emp ON Emp.Alias COLLATE SQL_Latin1_General_CP1_CI_AS = RUTA.FPor"+
							" \n  LEFT JOIN (SELECT * FROM PPackingList) AS PPKL ON PKL.PK_PackingList = PPKL.FK01_PackingList AND (PPKL.Fk03_facturaElectronica = (select PK_Factura from FacturaElectronica  FE1 where FE1.Folio collate SQL_Latin1_General_CP1_CI_AS = " +
							" \n  RUTA.Factura collate SQL_Latin1_General_CP1_CI_AS AND TipoComprobante = 'I' AND FE1.EmpresaEmisor = Emp.PK_Empresa ) OR PPKL.Fk04_Remision = (select PK_Remision from Remisiones REM where REM.Factura collate SQL_Latin1_General_CP1_CI_AS = RUTA.Factura collate SQL_Latin1_General_CP1_CI_AS))  " + 
							" \n  LEFT JOIN (SELECT * FROM EmbalarPedido)  EP ON  PPKL.FK02_EmbalarPEdido  = EP.PK_EmbalarPEdido " +
							" \n  LEFT JOIN (SELECT * FROM PPedidos) PP ON EP.FK01_PPEdido =  PP.IdPPEdido " +
							" \n  LEFT JOIN (SELECT CPEdido, FK01_Horario FROM PEdidos ) PE ON PE.CPedido = PP.CPedido " +
							" \n  LEFT JOIN (SELECT * FROM Productos) P ON  P.idProducto = PP.FK02_Producto " +
							" \n  LEFT JOIN (SELECT * FROM AsignarMensajero) AM ON AM.Evento = RUTA.idDP" +
							" \n  LEFT JOIN(SELECT * FROM Clientes) AS CLI ON CLI.Clave = RUTA.idCliente  " +
							" \n  LEFT JOIN(SELECT * FROM Pendiente) AS PEN ON PEN.Docto collate SQL_Latin1_General_CP1_CI_AS = RUTA.idRuta collate SQL_Latin1_General_CP1_CI_AS" + 
							" \n  LEFT JOIN(SELECT * FROM HorarioyLugar) AS HL ON HL.idCliente = RUTA.idCliente AND HL.idHorario = PE.FK01_Horario " + 
							" \n  LEFT JOIN(SELECT RUTADP.IDDP ,Pedidos.Destino,Pedidos.Contacto,Pedidos.Titulo,Pedidos.Puesto,Pedidos.Departamento,Pedidos.Tel FROM Pedidos,Facturas,RutaDP WHERE Pedidos.CPedido=Facturas.CPedido AND  " +
							" \n  Facturas.Factura=RutaDP.Factura AND Facturas.FPor=RutaDP.FPor AND Facturas.Cliente=RutaDP.idCliente ) AS contactoUno ON contactoUno.idDP collate SQL_Latin1_General_CP1_CI_AS  = RUTA.idDP  collate SQL_Latin1_General_CP1_CI_AS" + 
							" \n  WHERE PEN.Tipo = '"+ tipoPendiente +"' AND RUTA.EstadoRuta = '"+ estadoRuta +"' " + resp + " AND PEN.FFin IS NULL AND RUTA.FK02_PackingList IS NOT NULL"  +
							" \n   UNION " +			

						" \n   SELECT  HL.Latitud,HL.Longitud,HL.Altitud,RUTA.Latitud AS RLatitud,  RUTA.Longitud AS RLongitud, RUTA.Altitud AS RAltitud,RUTA.FK01_Direccion, HL.Actualizar, NULL AS Contacto1 ,NULL AS Titulo1,NULL AS Puesto1 ,NULL AS Depto1 ,NULL AS Tel1 ,NULL AS Contacto2 ,NULL AS Titulo2,NULL AS Puesto2,NULL AS Depto2 ,NULL AS Tel2 ," +
						" \n   NULL AS idEntrega, PEN.Folio, 'Revision' AS TEvento, CLI.Clave, Nombre, RUTA.Zona, Prioridad, null," +
						" \n   RUTA.Calle + ' ' + RUTA.Delegacion + ' ' + RUTA.CP  as Direccion, RUTA.Mapa, idPR as folioEvento, OrdenPlan," +
						" \n   DifDireccion, CAST(Descripcion AS VARCHAR(500)) As ComentariosAdicionales, AM.orden, " +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.Diario ELSE RUTA.Diario END AS Diario, CASE WHEN DifDireccion = 0 THEN HL.DiaDe1 ELSE RUTA.DiaDe1 END AS DiaDe1," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.DiaA1 ELSE RUTA.DiaA1 END AS DiaA1, CASE WHEN DifDireccion = 0 THEN HL.DiaDe2 ELSE RUTA.DiaDe2 END AS DiaDe2," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.DiaA2 ELSE RUTA.DiaA2 END AS DiaA2, CASE WHEN DifDireccion = 0 THEN HL.Lunes ELSE RUTA.Lunes END AS Lunes," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.LuDe1 ELSE RUTA.LuDe1 END AS LuDe1, CASE WHEN DifDireccion = 0 THEN HL.LuA1 ELSE RUTA.LuA1 END AS LuA1," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.LuDe2 ELSE RUTA.LuDe2 END AS LuDe2, CASE WHEN DifDireccion = 0 THEN HL.LuA2 ELSE RUTA.LuA2 END AS LuA2," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.Martes ELSE RUTA.Martes END AS Martes, CASE WHEN DifDireccion = 0 THEN HL.MaDe1 ELSE RUTA.MaDe1 END AS MaDe1," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.MaA1 ELSE RUTA.MaA1 END AS MaA1, CASE WHEN DifDireccion = 0 THEN HL.MaDe2 ELSE RUTA.MaDe2 END AS MaDe2," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.MaA2 ELSE RUTA.MaA2 END AS MaA2, CASE WHEN DifDireccion = 0 THEN HL.Miercoles ELSE RUTA.Miercoles END AS Miercoles," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.MiDe1 ELSE RUTA.MiDe1 END AS MiDe1, CASE WHEN DifDireccion = 0 THEN HL.MiA1 ELSE RUTA.MiA1 END AS MiA1," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.MiDe2 ELSE RUTA.MiDe2 END AS MiDe2, CASE WHEN DifDireccion = 0 THEN HL.MiA2 ELSE RUTA.MiA2 END AS MiA2," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.Jueves ELSE RUTA.Jueves END AS Jueves, CASE WHEN DifDireccion = 0 THEN HL.JuDe1 ELSE RUTA.JuDe1 END AS JuDe1," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.JuA1 ELSE RUTA.JuA1 END AS JuA1, CASE WHEN DifDireccion = 0 THEN HL.JuDe2 ELSE RUTA.JuDe2 END AS JuDe2," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.JuA2 ELSE RUTA.JuA2 END AS JuA2, CASE WHEN DifDireccion = 0 THEN HL.Viernes ELSE RUTA.Viernes END AS Viernes," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.ViDe1 ELSE RUTA.ViDe1 END AS ViDe1, CASE WHEN DifDireccion = 0 THEN HL.ViA1 ELSE RUTA.ViA1 END AS ViA1," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.ViDe2 ELSE RUTA.ViDe2 END AS ViDe2, CASE WHEN DifDireccion = 0 THEN HL.ViA2 ELSE RUTA.ViA2 END AS ViA2, 0 as idProveedor, null, RUTA.idRuta" +
						" \n   FROM RutaPR AS RUTA" +
						" \n   INNER JOIN (SELECT * FROM AsignarMensajero) AM ON AM.Evento = RUTA.idPR" +
						" \n   LEFT JOIN (SELECT * FROM Clientes) AS CLI ON CLI.Clave = RUTA.idCliente" +
						" \n   LEFT JOIN (SELECT * FROM Pendiente) AS PEN ON PEN.Docto = RUTA.idRuta" +
						" \n   LEFT JOIN (SELECT * FROM HorarioyLugar) AS HL ON HL.idCliente = RUTA.idCliente AND HL.idHorario=RUTA.FK01_Direccion " +
						" \n   WHERE PEN.Tipo = '"+ tipoPendiente +"' AND EstadoRuta = '"+ estadoRuta +"' "  + resp + 
						" \n   UNION" +

						" \n   SELECT  HL.Latitud,HL.Longitud,HL.Altitud,RUTA.Latitud AS RLatitud, RUTA.Longitud AS RLongitud, RUTA.Altitud AS RAltitud,RUTA.FK01_Direccion, HL.Actualizar, NULL AS Contacto1 ,NULL AS Titulo1,NULL AS Puesto1 ,NULL AS Depto1 ,NULL AS Tel1 ,NULL AS Contacto2 ,NULL AS Titulo2,NULL AS Puesto2,NULL AS Depto2 ,NULL AS Tel2 ," +
						" \n   NULL AS idEntrega, PEN.Folio, 'Cobro' AS TEvento, CLI.Clave, Nombre, RUTA.Zona, Prioridad, null, " +
						" \n   RUTA.Calle + ' ' + RUTA.Delegacion + ' ' + RUTA.CP  as Direccion, RUTA.Mapa, idPC as folioEvento, OrdenPlan," +
						" \n   DifDireccion, CAST(Descripcion AS VARCHAR(500)) As ComentariosAdicionales, AM.orden, " +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.Diario ELSE RUTA.Diario END AS Diario, CASE WHEN DifDireccion = 0 THEN HL.DiaDe1 ELSE RUTA.DiaDe1 END AS DiaDe1," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.DiaA1 ELSE RUTA.DiaA1 END AS DiaA1, CASE WHEN DifDireccion = 0 THEN HL.DiaDe2 ELSE RUTA.DiaDe2 END AS DiaDe2," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.DiaA2 ELSE RUTA.DiaA2 END AS DiaA2, CASE WHEN DifDireccion = 0 THEN HL.Lunes ELSE RUTA.Lunes END AS Lunes," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.LuDe1 ELSE RUTA.LuDe1 END AS LuDe1, CASE WHEN DifDireccion = 0 THEN HL.LuA1 ELSE RUTA.LuA1 END AS LuA1," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.LuDe2 ELSE RUTA.LuDe2 END AS LuDe2, CASE WHEN DifDireccion = 0 THEN HL.LuA2 ELSE RUTA.LuA2 END AS LuA2," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.Martes ELSE RUTA.Martes END AS Martes, CASE WHEN DifDireccion = 0 THEN HL.MaDe1 ELSE RUTA.MaDe1 END AS MaDe1," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.MaA1 ELSE RUTA.MaA1 END AS MaA1, CASE WHEN DifDireccion = 0 THEN HL.MaDe2 ELSE RUTA.MaDe2 END AS MaDe2," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.MaA2 ELSE RUTA.MaA2 END AS MaA2, CASE WHEN DifDireccion = 0 THEN HL.Miercoles ELSE RUTA.Miercoles END AS Miercoles," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.MiDe1 ELSE RUTA.MiDe1 END AS MiDe1, CASE WHEN DifDireccion = 0 THEN HL.MiA1 ELSE RUTA.MiA1 END AS MiA1," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.MiDe2 ELSE RUTA.MiDe2 END AS MiDe2, CASE WHEN DifDireccion = 0 THEN HL.MiA2 ELSE RUTA.MiA2 END AS MiA2," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.Jueves ELSE RUTA.Jueves END AS Jueves, CASE WHEN DifDireccion = 0 THEN HL.JuDe1 ELSE RUTA.JuDe1 END AS JuDe1," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.JuA1 ELSE RUTA.JuA1 END AS JuA1, CASE WHEN DifDireccion = 0 THEN HL.JuDe2 ELSE RUTA.JuDe2 END AS JuDe2," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.JuA2 ELSE RUTA.JuA2 END AS JuA2, CASE WHEN DifDireccion = 0 THEN HL.Viernes ELSE RUTA.Viernes END AS Viernes," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.ViDe1 ELSE RUTA.ViDe1 END AS ViDe1, CASE WHEN DifDireccion = 0 THEN HL.ViA1 ELSE RUTA.ViA1 END AS ViA1," +
						" \n   CASE WHEN DifDireccion = 0 THEN HL.ViDe2 ELSE RUTA.ViDe2 END AS ViDe2, CASE WHEN DifDireccion = 0 THEN HL.ViA2 ELSE RUTA.ViA2 END AS ViA2, 0 as idProveedor, null, RUTA.idRuta" +
						" \n   FROM RutaPC AS RUTA " +
						" \n   INNER JOIN (SELECT * FROM AsignarMensajero) AM ON AM.Evento = RUTA.idPC" +
						" \n   LEFT JOIN (SELECT * FROM Clientes) AS CLI ON CLI.Clave = RUTA.idCliente" +
						" \n   LEFT JOIN (SELECT * FROM Pendiente) AS PEN ON PEN.Docto = RUTA.idRuta" +
						" \n   LEFT JOIN (SELECT * FROM HorarioyLugar) AS HL ON HL.idCliente = RUTA.idCliente AND HL.idHorario=RUTA.FK01_Direccion " +
						" \n   WHERE PEN.Tipo = '"+ tipoPendiente +"' AND RUTA.EstadoRuta = '"+ estadoRuta +"' " + resp + 
						" \n   UNION" +

						" \n   SELECT HL.Latitud,HL.Longitud,HL.Altitud,RUTA.Latitud AS RLatitud ,RUTA.Longitud AS RLongitud, RUTA.Altitud AS RAltitud,RUTA.FK01_Direccion, HL.Actualizar, RUTA.Contacto1,RUTA.Titulo1,RUTA.Puesto1,RUTA.Depto1,RUTA.Tel1,RUTA.Contacto2,RUTA.Titulo2,RUTA.Puesto2,RUTA.Depto2,RUTA.Tel2," +
						" \n   NULL AS idEntrega, PEN.Folio, 'Recolección' AS TEvento, PRO.Clave, Nombre, RUTA.Zona, Prioridad, null," +
						" \n   RUTA.Calle + ' ' + RUTA.Delegacion + ' ' + RUTA.CP  as Direccion, RUTA.Mapa, idRM as folioEvento,  OrdenPlan," +
						" \n   DifDireccion, '' As ComentariosAdicionales, AM.orden, RUTA.Diario AS Diario, RUTA.DiaDe1 AS DiaDe1, RUTA.DiaA1 AS DiaA1, " +
						" \n   RUTA.DiaDe2 AS DiaDe2, RUTA.DiaA2 AS DiaA2, RUTA.Lunes AS Lunes, RUTA.LuDe1 AS LuDe1, RUTA.LuA1 AS LuA1, RUTA.LuDe2 AS LuDe2, " +
						" \n   RUTA.LuA2 AS LuA2, RUTA.Martes AS Martes, RUTA.MaDe1 AS MaDe1, RUTA.MaA1 AS MaA1, RUTA.MaDe2 AS MaDe2, RUTA.MaA2 AS MaA2, " +
						" \n   RUTA.Miercoles AS Miercoles, RUTA.MiDe1 AS MiDe1, RUTA.MiA1 AS MiA1, RUTA.MiDe2 AS MiDe2, RUTA.MiA2 AS MiA2, RUTA.Jueves AS Jueves, " +
						" \n   RUTA.JuDe1 AS JuDe1, RUTA.JuA1 AS JuA1, RUTA.JuDe2 AS JuDe2, RUTA.JuA2 AS JuA2, RUTA.Viernes AS Viernes, RUTA.ViDe1 AS ViDe1, " +
						" \n   RUTA.ViA1 AS ViA1, RUTA.ViDe2 AS ViDe2, RUTA.ViA2 AS ViA2, RUTA.idProveedor, null, RUTA.idRuta " +
						" \n   FROM RutaRM AS RUTA" +
						" \n   INNER JOIN (SELECT * FROM AsignarMensajero) AM ON AM.Evento = RUTA.idRM" +
						" \n   LEFT JOIN (SELECT * FROM Proveedores) AS PRO ON PRO.Clave = RUTA.idProveedor" +
						" \n   LEFT JOIN (SELECT * FROM Pendiente) AS PEN ON PEN.Docto = RUTA.idRuta" +
						" \n   LEFT JOIN (SELECT * FROM HorarioyLugar) AS HL ON HL.idProveedor = RUTA.idProveedor AND HL.idHorario=RUTA.FK01_Direccion " +
						" \n   WHERE PEN.Tipo = '"+ tipoPendiente +"' AND RUTA.EstadoRuta = '"+ estadoRuta +"' " + resp + 
						" \n   UNION" +
						" \n   SELECT HL.Latitud,HL.Longitud,HL.Altitud,RUTA.Latitud AS RLatitud, RUTA.Longitud AS RLongitud, RUTA.Altitud AS RAltitud,RUTA.FK01_Direccion, HL.Actualizar, RUTA.Contacto1,RUTA.Titulo1,RUTA.Puesto1,RUTA.Depto1,RUTA.Tel1,RUTA.Contacto2,RUTA.Titulo2,RUTA.Puesto2,RUTA.Depto2,RUTA.Tel2," +
						" \n   NULL AS idEntrega, PEN.Folio, 'Recoleccion especial' AS TEvento, CLI.Clave, Nombre, RUTA.Zona, Prioridad, null," +
						" \n   RUTA.Calle + ' ' + RUTA.Delegacion + ' ' + RUTA.CP as Direccion, RUTA.Mapa, idRE as folioEvento,  OrdenPlan, " +
						" \n   DifDireccion, '' As ComentariosAdicionales, AM.orden," +
						" \n   RUTA.Diario AS Diario, RUTA.DiaDe1 AS DiaDe1, RUTA.DiaA1 AS DiaA1, RUTA.DiaDe2 AS DiaDe2, RUTA.DiaA2 AS DiaA2, RUTA.Lunes AS Lunes," +
						" \n   RUTA.LuDe1 AS LuDe1, RUTA.LuA1 AS LuA1, RUTA.LuDe2 AS LuDe2, RUTA.LuA2 AS LuA2, RUTA.Martes AS Martes, RUTA.MaDe1 AS MaDe1," +
						" \n   RUTA.MaA1 AS MaA1, RUTA.MaDe2 AS MaDe2, RUTA.MaA2 AS MaA2, RUTA.Miercoles AS Miercoles, RUTA.MiDe1 AS MiDe1, RUTA.MiA1 AS MiA1," +
						" \n   RUTA.MiDe2 AS MiDe2, RUTA.MiA2 AS MiA2, RUTA.Jueves AS Jueves, RUTA.JuDe1 AS JuDe1, RUTA.JuA1 AS JuA1, RUTA.JuDe2 AS JuDe2," +
						" \n   RUTA.JuA2 AS JuA2, RUTA.Viernes AS Viernes, RUTA.ViDe1 AS ViDe1, RUTA.ViA1 AS ViA1, RUTA.ViDe2 AS ViDe2, RUTA.ViA2 AS ViA2, 0 as idProveedor, null, RUTA.idRuta" +
						" \n   FROM RutaRE AS RUTA" +
						" \n   INNER JOIN (SELECT * FROM AsignarMensajero) AM ON AM.Evento = RUTA.idRE" +
						" \n   LEFT JOIN (SELECT * FROM Clientes) AS CLI ON CLI.Clave = RUTA.idCliente" +
						" \n   LEFT JOIN (SELECT * FROM Pendiente) AS PEN ON PEN.Docto = RUTA.idRuta" +
						" \n   LEFT JOIN (SELECT * FROM HorarioyLugar) AS HL ON HL.idCliente = RUTA.idCliente AND HL.idHorario=RUTA.FK01_Direccion " +
						" \n   WHERE PEN.Tipo = '"+ tipoPendiente +"' AND RUTA.EstadoRuta = '"+ estadoRuta +"' " + resp + 
						" \n   UNION" +

						" \n  SELECT HL.Latitud,HL.Longitud,HL.Altitud,RUTA.Latitud AS RLatitud, RUTA.Longitud AS RLongitud, RUTA.Altitud AS RAltitud,RUTA.FK01_Direccion, HL.Actualizar, RUTA.Contacto1,RUTA.Titulo1,RUTA.Puesto1,RUTA.Depto1,RUTA.Tel1,RUTA.Contacto2,RUTA.Titulo2,RUTA.Puesto2,RUTA.Depto2,RUTA.Tel2," +
						" \n  NULL AS idEntrega, PEN.Folio, 'Entrega especial' AS TEvento, CLI.Clave, Nombre, RUTA.Zona, Prioridad, null," +
						" \n  RUTA.Calle + ' ' + RUTA.Delegacion + ' ' + RUTA.CP as Direccion, RUTA.Mapa, idES as folioEvento, OrdenPlan,  " +
						" \n  DifDireccion, '' As ComentariosAdicionales,AM.orden, " +
						" \n  CASE WHEN DifDireccion = 0 THEN HL.Diario ELSE RUTA.Diario END AS Diario, CASE WHEN DifDireccion = 0 THEN HL.DiaDe1 ELSE RUTA.DiaDe1 END AS DiaDe1," +
						" \n  CASE WHEN DifDireccion = 0 THEN HL.DiaA1 ELSE RUTA.DiaA1 END AS DiaA1, CASE WHEN DifDireccion = 0 THEN HL.DiaDe2 ELSE RUTA.DiaDe2 END AS DiaDe2," +
						" \n  CASE WHEN DifDireccion = 0 THEN HL.DiaA2 ELSE RUTA.DiaA2 END AS DiaA2, CASE WHEN DifDireccion = 0 THEN HL.Lunes ELSE RUTA.Lunes END AS Lunes," +
						" \n  CASE WHEN DifDireccion = 0 THEN HL.LuDe1 ELSE RUTA.LuDe1 END AS LuDe1, CASE WHEN DifDireccion = 0 THEN HL.LuA1 ELSE RUTA.LuA1 END AS LuA1," +
						" \n  CASE WHEN DifDireccion = 0 THEN HL.LuDe2 ELSE RUTA.LuDe2 END AS LuDe2, CASE WHEN DifDireccion = 0 THEN HL.LuA2 ELSE RUTA.LuA2 END AS LuA2," +
						" \n  CASE WHEN DifDireccion = 0 THEN HL.Martes ELSE RUTA.Martes END AS Martes, CASE WHEN DifDireccion = 0 THEN HL.MaDe1 ELSE RUTA.MaDe1 END AS MaDe1," +
						" \n  CASE WHEN DifDireccion = 0 THEN HL.MaA1 ELSE RUTA.MaA1 END AS MaA1, CASE WHEN DifDireccion = 0 THEN HL.MaDe2 ELSE RUTA.MaDe2 END AS MaDe2," +
						" \n  CASE WHEN DifDireccion = 0 THEN HL.MaA2 ELSE RUTA.MaA2 END AS MaA2, CASE WHEN DifDireccion = 0 THEN HL.Miercoles ELSE RUTA.Miercoles END AS Miercoles," +
						" \n  CASE WHEN DifDireccion = 0 THEN HL.MiDe1 ELSE RUTA.MiDe1 END AS MiDe1, CASE WHEN DifDireccion = 0 THEN HL.MiA1 ELSE RUTA.MiA1 END AS MiA1," +
						" \n  CASE WHEN DifDireccion = 0 THEN HL.MiDe2 ELSE RUTA.MiDe2 END AS MiDe2, CASE WHEN DifDireccion = 0 THEN HL.MiA2 ELSE RUTA.MiA2 END AS MiA2," +
						" \n  CASE WHEN DifDireccion = 0 THEN HL.Jueves ELSE RUTA.Jueves END AS Jueves, CASE WHEN DifDireccion = 0 THEN HL.JuDe1 ELSE RUTA.JuDe1 END AS JuDe1," +
						" \n  CASE WHEN DifDireccion = 0 THEN HL.JuA1 ELSE RUTA.JuA1 END AS JuA1, CASE WHEN DifDireccion = 0 THEN HL.JuDe2 ELSE RUTA.JuDe2 END AS JuDe2," +
						" \n  CASE WHEN DifDireccion = 0 THEN HL.JuA2 ELSE RUTA.JuA2 END AS JuA2, CASE WHEN DifDireccion = 0 THEN HL.Viernes ELSE RUTA.Viernes END AS Viernes," +
						" \n  CASE WHEN DifDireccion = 0 THEN HL.ViDe1 ELSE RUTA.ViDe1 END AS ViDe1, CASE WHEN DifDireccion = 0 THEN HL.ViA1 ELSE RUTA.ViA1 END AS ViA1," +
						" \n  CASE WHEN DifDireccion = 0 THEN HL.ViDe2 ELSE RUTA.ViDe2 END AS ViDe2, CASE WHEN DifDireccion = 0 THEN HL.ViA2 ELSE RUTA.ViA2 END AS ViA2, 0 as idProveedor, null, RUTA.idRuta" +
						" \n  FROM RutaES AS RUTA" +
						" \n  INNER JOIN (SELECT * FROM AsignarMensajero) AM ON AM.Evento = RUTA.idES" +
						" \n  LEFT JOIN (SELECT * FROM Clientes) AS CLI ON CLI.Clave = RUTA.idCliente" +
						" \n  LEFT JOIN (SELECT * FROM Pendiente ) AS PEN ON PEN.Docto = RUTA.idRuta" +
						" \n  LEFT JOIN (SELECT * FROM HorarioyLugar) AS HL ON HL.idCliente = RUTA.idCliente AND HL.idHorario=RUTA.FK01_Direccion " +
						" \n  WHERE PEN.Tipo = '"+ tipoPendiente +"' AND RUTA.EstadoRuta = '"+ estadoRuta +"' "  + resp ;

			log.info(query);
			return super.jdbcTemplate.query(query, new PendientesMensajeroPLRowMapper());
		}catch(Exception e){
			log.info(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<PendientesMensajero> findPendientesEnCierrePL(Empleado usuario, String tipoPendiente) throws ProquifaNetException{
	  List<PendientesMensajero> PendientesEnCierre = new ArrayList<PendientesMensajero>();
	  StringBuilder sbQuery = new StringBuilder(" \n");
	  try{

		String responsable = "";
		if (usuario.getNivel() == 10 || usuario.getNivel() == 46) {
			responsable = " AND Responsable ='"+usuario.getUsuario()+"'\n";
		}
		  
	    sbQuery.append("SELECT DISTINCT Ruta.Prioridad, chkDiferente AS DifDireccion, PEN.Folio,RUTA.Calle + ' ' + RUTA.Delegacion + ' ' + RUTA.CP as Direccion, RUTA.ZonaMensajeria, 'Entrega' AS TEvento, CLI.Clave, CLI.Nombre,idDP as folioEvento, \n");
	    sbQuery.append("Ruta.idRuta, 0 AS idProveedor, RUTA.Persona_Recibio, CASE WHEN RUTA.FacturaORemision = 0 THEN FE.Folio collate SQL_Latin1_General_CP1_CI_AS ELSE REM.Factura collate SQL_Latin1_General_CP1_CI_AS END as FolioFactura, PKL.folio as folioPKL, CLI.EntregayRevision,HL.latitud, HL.longitud, RUTA.FacturaORemision  \n");
	    sbQuery.append("FROM RutaDP AS RUTA \n");
	    sbQuery.append("LEFT JOIN (SELECT * FROM PackingList)  AS PKL ON  RUTA.FK02_PackingList = PKL.PK_PackingList \n");
	    sbQuery.append("LEFT JOIN Empresa AS Emp ON Emp.Alias COLLATE SQL_Latin1_General_CP1_CI_AS = RUTA.FPor  \n");
	    sbQuery.append("LEFT JOIN (SELECT * FROM PPAckingList) AS PPKL ON PKL.PK_PackingList = PPKL.FK01_PAckingList AND (PPKL.Fk03_facturaElectronica = (select PK_Factura from FacturaElectronica  FE1 where FE1.Folio collate SQL_Latin1_General_CP1_CI_AS = RUTA.Factura collate SQL_Latin1_General_CP1_CI_AS AND TipoComprobante = 'I' AND FE1.EmpresaEmisor = Emp.PK_Empresa ) \n");
	    sbQuery.append("OR PPKL.Fk04_Remision = (select PK_Remision from Remisiones REM where REM.Factura collate SQL_Latin1_General_CP1_CI_AS = RUTA.Factura collate SQL_Latin1_General_CP1_CI_AS)) \n");
	    sbQuery.append("LEFT JOIN (SELECT * FROM Clientes) AS CLI ON CLI.Clave = RUTA.idCliente \n");
	    sbQuery.append("LEFT JOIN (SELECT * FROM Pendiente) AS PEN ON PEN.Docto = RUTA.idRuta \n");
	    sbQuery.append("LEFT JOIN (SELECT * FROM FacturaElectronica ) AS FE ON PPKL.FK03_FacturaElectronica = FE.PK_Factura \n");
	    sbQuery.append("LEFT JOIN (SELECT * FROM Remisiones ) AS REM ON PPKL.Fk04_Remision = REM.PK_Remision \n");
	    sbQuery.append("LEFT JOIN HorarioyLugar AS HL ON ruta.FK01_Direccion = HL.idHorario \n");
	    sbQuery.append("WHERE PEN.Tipo = '" + tipoPendiente + "' AND RUTA.FK02_PackingList IS NOT NULL AND RUTA.EstadoRuta = 'EnEjecucion' " + responsable);
	    sbQuery.append("UNION \n");
	    sbQuery.append("SELECT Ruta.Prioridad, DifDireccion, PEN.Folio,RUTA.Calle + ' ' + RUTA.Delegacion + ' ' + RUTA.CP as Direccion, RUTA.Zona AS ZonaMensajeria, 'Revision' AS TEvento, CLI.Clave, Nombre,idPR as folioEvento, Ruta.idRuta, 0 AS idProveedor, RUTA.Persona_Recibio,null,null,0,HL.latitud, HL.longitud, 0 \n");
	    sbQuery.append("FROM RutaPR AS RUTA LEFT JOIN (SELECT * FROM Clientes) AS CLI ON CLI.Clave = RUTA.idCliente \n");
	    sbQuery.append("LEFT JOIN (SELECT * FROM Pendiente) AS PEN ON PEN.Docto = RUTA.idRuta \n");
	    sbQuery.append("LEFT JOIN HorarioyLugar AS HL ON ruta.FK01_Direccion = HL.idHorario \n");
	    sbQuery.append("WHERE PEN.Tipo = '" + tipoPendiente + "' AND EstadoRuta = 'EnEjecucion' " + responsable);
	    sbQuery.append("UNION \n");
	    sbQuery.append("SELECT Ruta.Prioridad, DifDireccion, PEN.Folio,RUTA.Calle + ' ' + RUTA.Delegacion + ' ' + RUTA.CP as Direccion, RUTA.Zona AS ZonaMensajeria,'Cobro' AS TEvento, CLI.Clave, Nombre,idPC as folioEvento, Ruta.idRuta, 0 AS idProveedor, RUTA.Persona_Recibio,null,null,0,HL.latitud, HL.longitud, 0 \n");
	    sbQuery.append("FROM RutaPC AS RUTA  \n");
	    sbQuery.append("LEFT JOIN (SELECT * FROM Clientes) AS CLI ON CLI.Clave = RUTA.idCliente \n");
	    sbQuery.append("LEFT JOIN (SELECT * FROM Pendiente) AS PEN ON PEN.Docto = RUTA.idRuta \n");
	    sbQuery.append("LEFT JOIN HorarioyLugar AS HL ON ruta.FK01_Direccion = HL.idHorario \n");
	    sbQuery.append("WHERE PEN.Tipo = '" + tipoPendiente + "' AND RUTA.EstadoRuta = 'EnEjecucion' " + responsable);
	    sbQuery.append("UNION \n");
	    sbQuery.append("SELECT Ruta.Prioridad, DifDireccion, PEN.Folio,RUTA.Calle + ' ' + RUTA.Delegacion + ' ' + RUTA.CP as Direccion, RUTA.Zona AS ZonaMensajeria, 'Recolección' AS TEvento, PRO.Clave, Nombre,idRM as folioEvento, Ruta.idRuta, RUTA.idProveedor, RUTA.Persona_Recibio,null,null,0,HL.latitud, HL.longitud, 0 \n");
	    sbQuery.append("FROM RutaRM AS RUTA \n");
	    sbQuery.append("LEFT JOIN (SELECT * FROM Proveedores) AS PRO ON PRO.Clave = RUTA.idProveedor \n");
	    sbQuery.append("LEFT JOIN (SELECT * FROM Pendiente) AS PEN ON PEN.Docto = RUTA.idRuta \n");
	    sbQuery.append("LEFT JOIN (SELECT * FROM HorarioyLugar) AS HL ON HL.idProveedor = RUTA.idProveedor \n");
	    sbQuery.append("WHERE PEN.Tipo = '" + tipoPendiente + "' AND RUTA.EstadoRuta = 'EnEjecucion' " + responsable);
	    sbQuery.append("UNION \n");
	    sbQuery.append("SELECT Ruta.Prioridad, DifDireccion, PEN.Folio,RUTA.Calle + ' ' + RUTA.Delegacion + ' ' + RUTA.CP as Direccion,RUTA.Zona AS ZonaMensajeria, 'Recoleccion especial' AS TEvento, CLI.Clave, Nombre,idRE as folioEvento, Ruta.idRuta, 0 AS idProveedor, RUTA.Persona_Recibio,null,null,0,HL.latitud, HL.longitud, 0 \n");
	    sbQuery.append("FROM RutaRE AS RUTA \n");
	    sbQuery.append("LEFT JOIN (SELECT * FROM Clientes) AS CLI ON CLI.Clave = RUTA.idCliente \n");
	    sbQuery.append("LEFT JOIN (SELECT * FROM Pendiente) AS PEN ON PEN.Docto = RUTA.idRuta \n");
	    sbQuery.append("LEFT JOIN HorarioyLugar AS HL ON ruta.FK01_Direccion = HL.idHorario \n");
	    sbQuery.append("WHERE PEN.Tipo = '" + tipoPendiente + "' AND RUTA.EstadoRuta = 'EnEjecucion' " + responsable);
	    sbQuery.append("UNION SELECT Ruta.Prioridad, DifDireccion, PEN.Folio,RUTA.Calle + ' ' + RUTA.Delegacion + ' ' + RUTA.CP as Direccion,RUTA.Zona AS ZonaMensajeria, 'Entrega especial' AS TEvento, CLI.Clave, Nombre,idES as folioEvento, Ruta.idRuta, RUTA.idProveedor, RUTA.Persona_Recibio,null,null,0,HL.latitud, HL.longitud, 0 \n");
	    sbQuery.append("FROM RutaES AS RUTA \n");
	    sbQuery.append("LEFT JOIN (SELECT * FROM Clientes) AS CLI ON CLI.Clave = RUTA.idCliente \n");
	    sbQuery.append("LEFT JOIN (SELECT * FROM Pendiente) AS PEN ON PEN.Docto = RUTA.idRuta \n");
	    sbQuery.append("LEFT JOIN HorarioyLugar AS HL ON ruta.FK01_Direccion = HL.idHorario \n");
	    sbQuery.append("WHERE PEN.Tipo = '" + tipoPendiente + "' AND RUTA.EstadoRuta = 'EnEjecucion' " + responsable);
		System.out.print("AQUI----"+sbQuery.toString());
	      log.info(sbQuery.toString());
	    PendientesEnCierre = super.jdbcTemplate.query(sbQuery.toString(),new RowMapper(){

	    @Override
	    public Object mapRow(ResultSet rs, int arg1) throws SQLException{
	        PendientesMensajero pendiente = new PendientesMensajero();
	        try{
	          pendiente.setFolio(rs.getString("Folio"));
	          pendiente.setEvento(rs.getString("TEvento"));
	          pendiente.setDiferenteDireccion(rs.getBoolean("DifDireccion"));
	          pendiente.setIdCliente(rs.getLong("Clave"));
	          pendiente.setEmpresa(rs.getString("Nombre"));
	          pendiente.setDireccion(rs.getString("Direccion"));
	          pendiente.setPersonaRecibio(rs.getString("Persona_Recibio"));
	          pendiente.setFolioProducto(rs.getString("FolioFactura"));
	          pendiente.setFolioEvento(rs.getString("FolioEvento"));
	          pendiente.setIdProveedor(rs.getLong("idProveedor"));
	          pendiente.setRuta(rs.getString("ZonaMensajeria"));
	          pendiente.setIdRuta(rs.getString("idRuta"));
	          pendiente.setLatitud(rs.getDouble("latitud"));
	          pendiente.setLongitud(rs.getDouble("longitud"));
	          pendiente.setFacturaORemision(rs.getBoolean("FacturaORemision"));
	          if(pendiente.getEvento().equals("Entrega")) {
	            pendiente.setFolioDocumento(rs.getString("folioPKL"));
	          }
	          if(rs.getString("Prioridad")!=null){
	            pendiente.setPrioridad(rs.getString("Prioridad"));
	          }else{
	            pendiente.setPrioridad("Normal");
	          }
	          pendiente.setAceptaEyR(rs.getBoolean("EntregayRevision"));

	          return pendiente;
	        }
	        catch (Exception e) {
	        }
	        return pendiente;
	      }

	    });

	    log.info("",PendientesEnCierre.size());
	    return PendientesEnCierre;
	  }catch (Exception e) {
	    e.printStackTrace();
	    throw new ProquifaNetException();
	  }

	}





	public List<PendientesMensajero> findPendientesCerradosPL(String Usuario, String tipoPendiente) throws ProquifaNetException{
		try{
			String queryFind = "";     
			//		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd"); // AND FInicio>='" + formatoFecha.format(fecha) + "'
			//        Date fecha = new Date();
			queryFind="SELECT Ruta.EstadoRuta,CAST(Ruta.RazonesEntrega AS VARCHAR(MAX)) AS RazonesEntrega,CAST(Ruta.TipoJustificacion AS VARCHAR(MAX)) AS TipoJustificacion,Ruta.Prioridad,OrdenPlan,idruta,"+
					" \n RUTA.Calle + ' ' + RUTA.Delegacion + ' ' + RUTA.CP as Direccion,ruta.ZonaMensajeria, Ruta.Entrega, chkDiferente AS DifDireccion,'Entrega' AS TEvento, CLI.Clave, CLI.Nombre,idDP as folioEvento,0 AS idProveedor,  PKL.folio as folioPKL,PPKL.folio as folioPPKL "+
					" \n FROM RutaDP AS RUTA "+
					" \n LEFT JOIN (SELECT * FROM PackingList)  AS PKL ON  RUTA.FK02_PackingList = PKL.PK_PackingList "+
					" \n LEFT JOIN (SELEct * FROM PPAckingList) AS PPKL ON PKL.PK_PackingList = PPKL.FK01_PAckingList "+
					" \n LEFT JOIN (SELECT * FROM Clientes) AS CLI ON CLI.Clave = RUTA.idCliente "+
					" \n LEFT JOIN (SELECT * FROM HorarioyLugar WHERE Tipo='ENTREGA')AS HL ON HL.idCliente = RUTA.idCliente "+
					" \n WHERE Ruta.EstadoRuta='ACerrar' AND idruta in (SELECT Docto FROM Pendiente WHERE Responsable='"+ Usuario +"' AND TIPO='" + tipoPendiente + "') "+
					" \n UNION "+
					" \n SELECT Ruta.EstadoRuta,CAST(Ruta.RazonesEntrega AS VARCHAR(MAX))AS RazonesEntrega,CAST(Ruta.TipoJustificacion AS VARCHAR(MAX)) AS TipoJustificacion,Ruta.Prioridad,OrdenPlan,idruta,"+
					" \n RUTA.Calle + ' ' + RUTA.Delegacion + ' ' + RUTA.CP as Direccion, ruta.Zona, Ruta.Entrega, DifDireccion,'Revision' AS TEvento, CLI.Clave, Nombre,idPR as folioEvento,0 AS idProveedor, null, null "+
					" \n FROM RutaPR AS RUTA "+
					" \n LEFT JOIN (SELECT * FROM Clientes) AS CLI ON CLI.Clave = RUTA.idCliente "+
					" \n LEFT JOIN (SELECT * FROM HorarioyLugar WHERE Tipo='Revision') AS HL ON HL.idCliente = RUTA.idCliente "+
					" \n WHERE Ruta.EstadoRuta='ACerrar' AND idruta in (SELECT Docto FROM Pendiente WHERE Responsable='"+Usuario+"' AND TIPO='" + tipoPendiente + "' ) "+
					" \n UNION "+
					" \n SELECT Ruta.EstadoRuta,CAST(Ruta.RazonesEntrega AS VARCHAR(MAX)) AS RazonesEntrega,CAST(Ruta.TipoJustificacion AS VARCHAR(MAX)) AS TipoJustificacion,Ruta.Prioridad,OrdenPlan,idruta,"+
					" \n RUTA.Calle + ' ' + RUTA.Delegacion + ' ' + RUTA.CP as Direccion,ruta.Zona, Ruta.Entrega, DifDireccion,'Cobro' AS TEvento, CLI.Clave, Nombre,idPC as folioEvento,0 AS idProveedor, null ,null  "+
					" \n FROM RutaPC AS RUTA "+
					" \n LEFT JOIN (SELECT * FROM Clientes) AS CLI ON CLI.Clave = RUTA.idCliente "+
					" \n LEFT JOIN (SELECT * FROM HorarioyLugar WHERE Tipo='Pago') AS HL ON HL.idCliente = RUTA.idCliente "+
					" \n WHERE Ruta.EstadoRuta='ACerrar' AND idruta in (SELECT Docto FROM Pendiente WHERE Responsable='"+Usuario+"' AND TIPO='" + tipoPendiente + "' ) "+
					" \n UNION "+
					" \n SELECT Ruta.EstadoRuta,CAST(Ruta.RazonesEntrega AS VARCHAR(MAX)) AS RazonesEntrega,CAST(Ruta.TipoJustificacion AS VARCHAR(MAX)) AS TipoJustificacion,Ruta.Prioridad,OrdenPlan,idruta,"+
					" \n RUTA.Calle + ' ' + RUTA.Delegacion + ' ' + RUTA.CP as Direccion,RUTA.ZOna, Ruta.Entrega , DifDireccion, 'Recolección' AS TEvento, PRO.Clave, Nombre,idRM as folioEvento,RUTA.idProveedor, null, null "+
					" \n FROM RutaRM AS RUTA "+
					" \n LEFT JOIN (SELECT * FROM Proveedores) AS PRO ON PRO.Clave = RUTA.idProveedor "+
					" \n LEFT JOIN (SELECT * FROM HorarioyLugar WHERE Tipo='Pago') AS HL ON HL.idProveedor = RUTA.idProveedor "+
					" \n WHERE Ruta.EstadoRuta='ACerrar' AND idruta in (SELECT Docto FROM Pendiente WHERE Responsable='"+Usuario+"' AND TIPO='" + tipoPendiente + "' ) "+
					" \n UNION "+
					" \n SELECT Ruta.EstadoRuta,CAST(Ruta.RazonesEntrega AS VARCHAR(MAX)) AS RazonesEntrega,CAST(Ruta.TipoJustificacion AS VARCHAR(MAX)) AS TipoJustificacion,Ruta.Prioridad,OrdenPlan,idruta,"+
					" \n RUTA.Calle + ' ' + RUTA.Delegacion + ' ' + RUTA.CP as Direccion,ruta.Zona, Ruta.Entrega, DifDireccion, 'Recoleccion especial' AS TEvento, CLI.Clave, Nombre,idRE as folioEvento,0 AS idProveedor,null, null "+
					" \n FROM RutaRE AS RUTA "+
					" \n LEFT JOIN (SELECT * FROM Clientes) AS CLI ON CLI.Clave = RUTA.idCliente "+
					" \n LEFT JOIN (SELECT * FROM HorarioyLugar WHERE Tipo='Pago') AS HL ON HL.idCliente = RUTA.idCliente "+
					" \n WHERE Ruta.EstadoRuta='ACerrar' AND idruta in (SELECT Docto FROM Pendiente WHERE Responsable='"+Usuario+"' AND TIPO='" + tipoPendiente + "' ) "+
					" \n UNION "+
					" \n SELECT Ruta.EstadoRuta,CAST(Ruta.RazonesEntrega AS VARCHAR(MAX)) AS RazonesEntrega,CAST(Ruta.TipoJustificacion AS VARCHAR(MAX)) AS TipoJustificacion,Ruta.Prioridad,OrdenPlan,idruta,"+
					" \n RUTA.Calle + ' ' + RUTA.Delegacion + ' ' + RUTA.CP as Direccion,ruta.Zona, Ruta.Entrega, DifDireccion, 'Entrega especial' AS TEvento, CLI.Clave, Nombre,idES as folioEvento,RUTA.idProveedor,null,null "+
					" \n FROM RutaES AS RUTA "+
					" \n LEFT JOIN (SELECT * FROM Clientes) AS CLI ON CLI.Clave = RUTA.idCliente "+
					" \n LEFT JOIN (SELECT * FROM HorarioyLugar WHERE Tipo='Pago') AS HL ON HL.idCliente = RUTA.idCliente "+
					" \n WHERE Ruta.EstadoRuta='ACerrar' AND idruta in (SELECT Docto FROM Pendiente WHERE Responsable='"+Usuario+"' AND TIPO='" + tipoPendiente + "' )";

			//		//log.info(queryFind);
			List<PendientesMensajero> PendientesCerrados= new ArrayList<PendientesMensajero>();
			PendientesCerrados = super.jdbcTemplate.query(queryFind	, new PendientesCerradosRowMapper() );

			return PendientesCerrados;
		}catch (Exception e) {
			log.info(e.getMessage());
			return null;// TODO: handle exception
		}
	}

	//	@Override
	public Boolean ejecutarRutaMensajeroPL(PendientesMensajero pendiente)  throws ProquifaNetException{		
		String queryide = "";

		Map<String, Object> map = new HashMap<String, Object>();
		try{
			if (pendiente.getFolioEvento().contains("DP-")) {
				queryide = "UPDATE RutaDP SET EstadoRuta = '"+ pendiente.getEstadoPendiente() + "' WHERE idCliente = " + pendiente.getIdCliente() + " AND idDP = '" + pendiente.getFolioEvento() + "' ";
			} else if (pendiente.getFolioEvento().contains("PR-")) {
				queryide = "UPDATE RutaPR SET EstadoRuta = '"+ pendiente.getEstadoPendiente() + "' WHERE idCliente = " + pendiente.getIdCliente() + " AND idPR = '" + pendiente.getFolioEvento() + "' ";
			} else if (pendiente.getFolioEvento().contains("PC-")) {
				queryide = "UPDATE RutaPC SET EstadoRuta = '"+ pendiente.getEstadoPendiente() +"' WHERE idCliente = " + pendiente.getIdCliente() + " AND idPC = '" + pendiente.getFolioEvento() + "' ";
			} else if (pendiente.getFolioEvento().contains("RM-")) {
				queryide = "UPDATE RutaRM SET EstadoRuta = '"+ pendiente.getEstadoPendiente() +"' WHERE idProveedor = " + pendiente.getIdCliente() + " AND idRM = '" + pendiente.getFolioEvento() + "' ";
			}else if (pendiente.getFolioEvento().contains("ES-")) {
				queryide = "UPDATE RutaES SET EstadoRuta = '"+ pendiente.getEstadoPendiente() +"' WHERE idCliente = " + pendiente.getIdCliente() + " AND idES = '" + pendiente.getFolioEvento() + "' ";
			}else if (pendiente.getFolioEvento().contains("RE-")) {
				queryide = "UPDATE RutaRE SET EstadoRuta = '"+ pendiente.getEstadoPendiente() +"' WHERE idCliente = " + pendiente.getIdCliente() + " AND idRE = '" + pendiente.getFolioEvento() + "' ";
			}
			if(queryide != ""){
				////log.info("idEvento "+idEvento);
				////log.info("ide "+queryide);
				super.jdbcTemplate.update(queryide, map);

				if (pendiente.getFolioEvento().contains("DP-")) {
					map = new HashMap<String, Object>();
					String estado = "";
					if(pendiente.getEstadoPendiente().equals("Colectado")) {
						estado = "En ruta";
					}else if(pendiente.getEstadoPendiente().equals("EnEjecucion")) {
						estado = "En entrega";
					}
					if(!estado.equals("")) {
						String queryUpdate = "update ppedidos set estado = '" + estado + "' where idPPedido in( \n" + 
								"select ep.FK01_PPedido from RutaDP rdp \n" + 
								"INNER JOIN Facturas AS F ON F.Factura = rdp.Factura AND F.FPor = rdp.Fpor \n" +
								"INNER JOIN Factura_FElectronica AS FE ON FE.FK01_Factura = F.idFactura \n" +
								"left join PPackingList pp on pp.FK01_PackingList = rdp.FK02_PackingList AND PP.FK03_FacturaElectronica = FE.FK02_FacturaElectronica \n" + 
								"left join embalarPedido ep on ep.PK_EmbalarPedido = pp.FK02_EmbalarPedido \n" + 
								"where idDP = '"+pendiente.getFolioEvento()+"')";
						log.info(queryUpdate);
						super.jdbcTemplate.update(queryUpdate, map);
					}
				}
			}
			return true;
		}catch(Exception e){
			log.info(e.getMessage());
			//log.info(e.getCause() + " " + e.getMessage() + " " + e.getLocalizedMessage() );
			return false;
		}
	}





	@Override
	public List<ColectarMensajero> colectarMensajero(Parametro parametro){
		StringBuilder sbQuery = new StringBuilder(" \n");


		sbQuery.append("SELECT  PKL.folio collate SQL_Latin1_General_CP1_CI_AS AS folioPKL,  PPKL.Folio AS folioPPKL,RPR.idPR, RES.idES, RPC.idPC, RRM.idRM,  P.calle,P.Delegacion,p.CP\n");    
		sbQuery.append("FROM Pendiente PEN \n");
		sbQuery.append("LEFT JOIN  Ruta_Evento RE ON  RE.AK01_Ruta = PEN.docto  COLLATE Modern_Spanish_CI_AS\n"); 
		sbQuery.append("LEFT JOIN  RutaDP RDP ON  RDP.idRuta =  RE.AK01_Ruta COLLATE Modern_Spanish_CI_AS \n");
		sbQuery.append("LEFT JOIN (SELECT * FROM PackingList) PKL ON  RDP.FK02_PackingList = PKL.PK_PackingList\n"); 
		sbQuery.append("LEFT JOIN (SELECT * FROM PPackingList) PPKL ON  PPKL.FK01_PackingList = PKL.PK_PackingList \n");
		sbQuery.append("LEFT JOIN (SELECT * FROM EmbalarPedido) EP ON PPKL.FK02_EmbalarPedido = EP.PK_EmbalarPedido \n");
		sbQuery.append("LEFT JOIN (SELECT * FROM PPedidos) PP ON EP.FK01_PPEdido = PP.idPPedido \n");
		sbQuery.append("LEFT JOIN (SELECT * FROM Pedidos)P ON  PP.CPedido = P.CPedido \n");
		sbQuery.append("LEFT JOIN (SELECT * FROM RutaPR) RPR ON  PEN.Docto = RPR.idPR  \n");
		sbQuery.append("LEFT JOIN (SELECT * FROM RutaES) RES ON PEN.Docto = RES.idES  \n");
		sbQuery.append("LEFT JOIN (SELECT * FROM RutaPC ) RPC ON PEN.Docto = RPC.idPC \n");
		sbQuery.append("LEFT JOIN (SELECT * FROM RutaRM) RRM ON PEN.Docto = RRM.idRM \n");
		sbQuery.append("WHERE PEN.Tipo = 'Ruta a ejecutar' AND PEN.FFin IS NULL AND PEN.Responsable = '" + parametro.getValor() + "' AND   RDP.EstadoRuta = 'EnEjecucion'  AND PEN.FFin IS NULL\n");
		sbQuery.append("GROUP BY P.calle,P.Delegacion,p.CP, PKL.folio collate SQL_Latin1_General_CP1_CI_AS,  PPKL.Folio ,RPR.idPR, RES.idES, RPC.idPC, RRM.idRM  \n");

		Map<String, Object> map = new HashMap<String, Object>();
		return super.jdbcTemplate.query(sbQuery.toString(), new ColectarMensajerosRowMapper());
	}



	@SuppressWarnings("unchecked")
	public List<Documento> findDocumentosCobro(Long idCliente,
			int diferente, String responsable,String folio, String tipoPendiente)  throws ProquifaNetException{
		try{
			String query = "SELECT idPC as idDP, 'NA' as Destino, 'NA' as Evento  FROM RutaPC WHERE idCliente = " + idCliente + " AND EstadoRuta = 'AEjecutar' AND DifDireccion = " + diferente + 
					" \n  AND idRuta IN (SELECT Docto FROM Pendiente WHERE Tipo = '" + tipoPendiente + "' AND Responsable = '" + responsable + "' ) AND idPC='"+folio+"'";
			return super.jdbcTemplate.query(query, new DocumentosDeRutaRowMapper());

		}catch(Exception e){
			log.info(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Documento> findDocumentosEntrega(Long idCliente,
			int diferente, String responsable,String folio, String tipoPendiente)  throws ProquifaNetException{
		try{
			String query = "SELECT idDP + '-D' as idDP, Destino,'Entrega' AS Evento FROM RutaDP WHERE idCliente = " + idCliente + " AND EstadoRuta = 'AEjecutar' AND chkDiferente =" + diferente +
					" \n  AND idRuta IN (SELECT Docto FROM Pendiente WHERE Tipo = '" + tipoPendiente + "' AND Responsable = '" + responsable + "' ) AND idDp='"+folio+"'" +
					" \n  UNION" +
					" \n  SELECT idEntrega, Destino,'Entrega' AS Evento FROM RutaDP WHERE idCliente = " + idCliente + " AND EstadoRuta = 'AEjecutar' AND chkDiferente = " + diferente +
					" \n  AND idRuta IN (SELECT Docto FROM Pendiente WHERE Tipo = '" + tipoPendiente + "' AND Responsable = '" + responsable + "' ) AND idDp='"+folio+"'" +
					" \n  UNION " +
					" \n  SELECT idES, 'NA' as Destino,'Entrega' AS Evento FROM RutaES WHERE idCliente = " + idCliente + " AND EstadoRuta = 'AEjecutar' AND DifDireccion = " + diferente +
					" \n  AND idRuta IN (SELECT Docto FROM Pendiente WHERE Tipo = '" + tipoPendiente + "' AND Responsable = '" + responsable + "' ) AND idES='"+folio+"'";
			return super.jdbcTemplate.query(query, new DocumentosDeRutaRowMapper());
		}catch(Exception e){
			log.info(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Documento> findDocumentosRecoleccion(Long idCliente,
			int diferente, String responsable,String folio, String tipoPendiente)  throws ProquifaNetException{
		try{
			String query = "SELECT idRM as idDP, 'NA' as Destino,'Recoleccion' AS Evento FROM RutaRM WHERE idProveedor = " + idCliente + "  AND EstadoRuta = 'AEjecutar' AND DifDireccion = " + diferente +
					" \n  AND idRuta IN (SELECT Docto FROM Pendiente WHERE Tipo = '" + tipoPendiente + "' AND Responsable = '" + responsable + "' ) AND idRM='"+folio+"'";
			return super.jdbcTemplate.query(query, new DocumentosDeRutaRowMapper());

		}catch(Exception e){
			log.info(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Documento> findDocumentosRecoleccionEspecial(Long idCliente,
			int diferente, String responsable,String folio, String tipoPendiente)  throws ProquifaNetException{
		try{
			String query = "SELECT idRE as idDP, 'NA' as Destino,'Recoleccion Especial' AS Evento FROM RutaRE WHERE idCliente= " + idCliente + " AND EstadoRuta = 'AEjecutar' AND DifDireccion = " + diferente +
					" \n  AND idRuta IN (SELECT Docto FROM Pendiente WHERE Tipo = '" + tipoPendiente + "' AND Responsable = '" + responsable + "') AND idRE='"+folio+"'";
			return super.jdbcTemplate.query(query, new DocumentosDeRutaRowMapper());

		}catch(Exception e){
			log.info(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Documento> findDocumentosRevision(Long idCliente,
			int diferente, String responsable,String folio, String tipoPendiente)  throws ProquifaNetException{
		try{
			String query = "SELECT idPR as idDP, 'NA' as Destino,'Revision' AS Evento FROM RutaPR WHERE idCliente = " + idCliente + " AND EstadoRuta = 'AEjecutar' AND DifDireccion = " + diferente +
					" \n  AND idRuta IN (SELECT Docto FROM Pendiente WHERE Tipo = '" + tipoPendiente + "' AND Responsable = '" + responsable + "')AND idPR='"+folio+"'";
			return super.jdbcTemplate.query(query, new DocumentosDeRutaRowMapper());

		}catch(Exception e){
			log.info(e.getMessage());
			return null;
		}
	}

	public Boolean ejecutarRutaMensajero(String evento ,String idEvento, Long idCliente, String Usuario)  throws ProquifaNetException{
		String queryevt = "";
		String queryide = "";

		Map<String, Object> map = new HashMap<String, Object>();
		try{
			if (idEvento.contains("DP-")) {
				queryide = "UPDATE RutaDP SET EstadoRuta = 'EnEjecucion' WHERE idCliente = " + idCliente + " AND idDP = '" + idEvento + "' ";
			} else if (idEvento.contains("PR-")) {
				queryide = "UPDATE RutaPR SET EstadoRuta = 'EnEjecucion' WHERE idCliente = " + idCliente + " AND idPR = '" + idEvento + "' ";
			} else if (idEvento.contains("PC-")) {
				queryide = "UPDATE RutaPC SET EstadoRuta = 'EnEjecucion' WHERE idCliente = " + idCliente + " AND idPC = '" + idEvento + "' ";
			} else if (idEvento.contains("RM-")) {
				queryide = "UPDATE RutaRM SET EstadoRuta = 'EnEjecucion' WHERE idProveedor = " + idCliente + " AND idRM = '" + idEvento + "' ";
			}else if (idEvento.contains("ES-")) {
				queryide = "UPDATE RutaES SET EstadoRuta = 'EnEjecucion' WHERE idCliente = " + idCliente + " AND idES = '" + idEvento + "' ";
			}else if (idEvento.contains("RE-")) {
				queryide = "UPDATE RutaRE SET EstadoRuta = 'EnEjecucion' WHERE idCliente = " + idCliente + " AND idRE = '" + idEvento + "' ";
			}
			if(queryevt != ""){
				////log.info("Evento "+ evento);
				////log.info("evt "+queryevt);
				super.jdbcTemplate.update(queryevt, map);
			}
			if(queryide != ""){
				////log.info("idEvento "+idEvento);
				////log.info("ide "+queryide);
				super.jdbcTemplate.update(queryide, map);
			}
			return true;
		}catch(Exception e){
			//log.info(e.getCause() + " " + e.getMessage() + " " + e.getLocalizedMessage() );
			return false;
		}
	}

	@Override
	public Boolean updateCoordenadasGPS(PendientesMensajero pendiente)  throws ProquifaNetException{
		String queryUpdate = "";
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			queryUpdate = "UPDATE HorarioyLugar SET latitud = '" + pendiente.getLatitud() + "', longitud = '" + pendiente.getLongitud() + "', actualizar = 0  WHERE idHorario = " + pendiente.getIdHorario();
			super.jdbcTemplate.update(queryUpdate, map);
			return true;
		}catch (Exception e) {
			log.info(e.toString());
			return false;
		}
	}

	public Boolean insertarCoordenadasGPS(PendientesMensajero pendiente)  throws ProquifaNetException{
		String queryUpdate= "";
		Long idHorario=0L;
		Map<String, Object> map = new HashMap<String, Object>();
		if (pendiente.getFolioEvento().contains("DP-")) {
			if(pendiente.getIdHorario() == null || pendiente.getIdHorario() == 0 ){

				queryUpdate="INSERT HorarioyLugar(idCliente,Tipo,Destino,Pais,Estado,Calle,Municipio,CP,Ruta,ZonaMensajeria,Mapa,Igual,Diario,DiaDe1,DiaA1,DiaDe2,DiaA2,"+
						" \n Lunes,LuDe1,LuA1,LuDe2,LuA2,Martes,MaDe1,MaA1,MaDe2,MaA2,Miercoles,MiDe1,MiA1,MiDe2,MiA2,Jueves,JuDe1,JuA1,JuDe2,JuA2,"+
						" \n Viernes,ViDe1,ViA1,ViDe2,ViA2,Comentarios,idProveedor,Latitud,Longitud,Altitud) " +
						" \n SELECT idCliente,'Entrega' AS Tipo,Destino,Pais,Estado,Calle,Delegacion AS Municipio,CP,Ruta,ZonaMensajeria,Mapa,chkDiferente as Igual,Diario,DiaDe1,DiaA1,DiaDe2,DiaA2,"+
						" \n Lunes,LuDe1,LuA1,LuDe2,LuA2,Martes,MaDe1,MaA1,MaDe2,MaA2,Miercoles,MiDe1,MiA1,MiDe2,MiA2,Jueves,JuDe1,JuA1,JuDe2,JuA2,"+
						" \n Viernes,ViDe1,ViA1,ViDe2,ViA2,Comentarios,NULL AS idProveedor," + pendiente.getLatitud() + ","+pendiente.getLongitud()+ ","+pendiente.getAltitud()+ " FROM RutaDP WHERE idDP='"+pendiente.getFolioEvento()+"'";
				//				//log.info(queryUpdate);
				super.jdbcTemplate.update(queryUpdate, map);

				idHorario = super.queryForLong("SELECT IDENT_CURRENT ('HorarioyLugar')");
				super.jdbcTemplate.update("UPDATE RutaDP SET FK01_Direccion ="+idHorario+",Latitud =" + pendiente.getLatitud() + ", Longitud="+pendiente.getLongitud()+ ", Altitud="+pendiente.getAltitud()+ " WHERE idDP='"+pendiente.getFolioEvento()+"'",map);
				//				queryUpdate="UPDATE HorarioyLugar SET Latitud =" + pendiente.getLatitud() + ", Longitud="+pendiente.getLongitud()+ ", Altitud="+pendiente.getAltitud()+ " WHERE idCliente="+pendiente.getIdCliente()+" AND Tipo='Entrega'";
				//				//log.info(queryUpdate);
				super.jdbcTemplate.update(queryUpdate, map);
				return true;
			}
		}else if (pendiente.getFolioEvento().contains("PR-")) {
			if(pendiente.getIdHorario() == null || pendiente.getIdHorario() == 0 ){
				queryUpdate="INSERT HorarioyLugar(idCliente,Tipo,Destino,Pais,Estado,Calle,Municipio,CP,Ruta,ZonaMensajeria,Mapa,Igual,Diario,DiaDe1,DiaA1,DiaDe2,DiaA2,"+
						" \n Lunes,LuDe1,LuA1,LuDe2,LuA2,Martes,MaDe1,MaA1,MaDe2,MaA2,Miercoles,MiDe1,MiA1,MiDe2,MiA2,Jueves,JuDe1,JuA1,JuDe2,JuA2,"+
						" \n Viernes,ViDe1,ViA1,ViDe2,ViA2,Comentarios,idProveedor,Latitud,Longitud,Altitud) "
						+"SELECT idCliente,'Revision' AS Tipo,NULL AS Destino,Pais,Estado,Calle,Delegacion AS Municipio,CP,Ruta,Zona AS ZonaMensajeria,Mapa,DifDireccion as Igual,Diario,DiaDe1,DiaA1,DiaDe2,DiaA2,"+
						" \n Lunes,LuDe1,LuA1,LuDe2,LuA2,Martes,MaDe1,MaA1,MaDe2,MaA2,Miercoles,MiDe1,MiA1,MiDe2,MiA2,Jueves,JuDe1,JuA1,JuDe2,JuA2,"+
						" \n Viernes,ViDe1,ViA1,ViDe2,ViA2,NULL AS Comentarios,NULL AS idProveedor," + pendiente.getLatitud() + ","+pendiente.getLongitud()+ ","+pendiente.getAltitud()+ " FROM RutaPR Where idPR='"+pendiente.getFolioEvento()+"'";
				//				//log.info(queryUpdate);
				super.jdbcTemplate.update(queryUpdate, map);

				idHorario = super.queryForLong("SELECT IDENT_CURRENT ('HorarioyLugar')");
				super.jdbcTemplate.update("UPDATE RutaPR SET FK01_Direccion ="+idHorario+",Latitud =" + pendiente.getLatitud() + ", Longitud="+pendiente.getLongitud()+ ", Altitud="+pendiente.getAltitud()+ " WHERE idPR='"+pendiente.getFolioEvento()+"'", map);
				//				queryUpdate="UPDATE HorarioyLugar SET Latitud =" + pendiente.getLatitud() + ", Longitud="+pendiente.getLongitud()+ ", Altitud="+pendiente.getAltitud()+ " WHERE idCliente="+pendiente.getIdCliente()+" AND Tipo='Revision'";
				//				//log.info(queryUpdate);
				super.jdbcTemplate.update(queryUpdate, map);
				return true;
			}
		}
		else if (pendiente.getFolioEvento().contains("PC-")) {
			if(pendiente.getIdHorario() == null || pendiente.getIdHorario() == 0 ){
				queryUpdate="INSERT HorarioyLugar(idCliente,Tipo,Destino,Pais,Estado,Calle,Municipio,CP,Ruta,ZonaMensajeria,Mapa,Igual,Diario,DiaDe1,DiaA1,DiaDe2,DiaA2,"+
						" \n Lunes,LuDe1,LuA1,LuDe2,LuA2,Martes,MaDe1,MaA1,MaDe2,MaA2,Miercoles,MiDe1,MiA1,MiDe2,MiA2,Jueves,JuDe1,JuA1,JuDe2,JuA2,"+
						" \n Viernes,ViDe1,ViA1,ViDe2,ViA2,Comentarios,idProveedor,Latitud,Longitud,Altitud) "+
						" \n Select idCliente,'Pago' AS Tipo,NULL AS Destino,Pais,Estado,Calle,Delegacion AS Municipio,CP,Ruta,Zona AS ZonaMensajeria,Mapa,DifDireccion as Igual,Diario,DiaDe1,DiaA1,DiaDe2,DiaA2,"+
						" \n Lunes,LuDe1,LuA1,LuDe2,LuA2,Martes,MaDe1,MaA1,MaDe2,MaA2,Miercoles,MiDe1,MiA1,MiDe2,MiA2,Jueves,JuDe1,JuA1,JuDe2,JuA2,"+
						" \n Viernes,ViDe1,ViA1,ViDe2,ViA2,NULL AS Comentarios,NUll AS idProveedor," + pendiente.getLatitud() + ","+pendiente.getLongitud()+ ","+pendiente.getAltitud()+ " FROM RutaPC WHERE idPC='"+pendiente.getFolioEvento()+"'";
				//				//log.info(queryUpdate);
				super.jdbcTemplate.update(queryUpdate, map);

				idHorario = super.queryForLong("SELECT IDENT_CURRENT ('HorarioyLugar')");
				super.jdbcTemplate.update("UPDATE RutaPC SET FK01_Direccion ="+idHorario+",Latitud =" + pendiente.getLatitud() + ", Longitud="+pendiente.getLongitud()+ ", Altitud="+pendiente.getAltitud()+ " WHERE idPC='"+pendiente.getFolioEvento()+"'",map);
				//				queryUpdate="UPDATE HorarioyLugar SET Latitud =" + pendiente.getLatitud() + ", Longitud="+pendiente.getLongitud()+ ", Altitud="+pendiente.getAltitud()+ " WHERE idCliente="+pendiente.getIdCliente()+" AND Tipo='Pago'";
				//				//log.info(queryUpdate);
				super.jdbcTemplate.update(queryUpdate, map);
				return true;
			}
		} else if (pendiente.getFolioEvento().contains("RM-")) {
			if(pendiente.getIdHorario() == null || pendiente.getIdHorario() == 0 ){
				queryUpdate="INSERT HorarioyLugar(idCliente,Tipo,Destino,Pais,Estado,Calle,Municipio,CP,Ruta,ZonaMensajeria,Mapa,Igual,Diario,DiaDe1,DiaA1,DiaDe2,DiaA2,"+
						" \n Lunes,LuDe1,LuA1,LuDe2,LuA2,Martes,MaDe1,MaA1,MaDe2,MaA2,Miercoles,MiDe1,MiA1,MiDe2,MiA2,Jueves,JuDe1,JuA1,JuDe2,JuA2,"+
						" \n Viernes,ViDe1,ViA1,ViDe2,ViA2,Comentarios,idProveedor,Latitud,Longitud,Altitud) "+
						" \n Select NULL AS idCliente,'Recoleccion' AS Tipo,NULL AS Destino,Pais,Estado,Calle,Delegacion AS Municipio,CP,NULL AS Ruta,Zona AS ZonaMensajeria,Mapa,DifDireccion AS Igual,Diario,DiaDe1,DiaA1,DiaDe2,DiaA2,"+
						" \n Lunes,LuDe1,LuA1,LuDe2,LuA2,Martes,MaDe1,MaA1,MaDe2,MaA2,Miercoles,MiDe1,MiA1,MiDe2,MiA2,Jueves,JuDe1,JuA1,JuDe2,JuA2,"+
						" \n Viernes,ViDe1,ViA1,ViDe2,ViA2,Comentarios,idProveedor," + pendiente.getLatitud() + ","+pendiente.getLongitud()+ ","+pendiente.getAltitud()+ " FROM RutaRM WHERE idRM='"+pendiente.getFolioEvento()+"'";
				//				//log.info(queryUpdate);
				super.jdbcTemplate.update(queryUpdate, map);	

				idHorario = super.queryForLong("SELECT IDENT_CURRENT ('HorarioyLugar')");
				super.jdbcTemplate.update("UPDATE RutaRM SET FK01_Direccion ="+idHorario+",Latitud =" + pendiente.getLatitud() + ", Longitud="+pendiente.getLongitud()+ ", Altitud="+pendiente.getAltitud()+ " WHERE idRM='"+pendiente.getFolioEvento()+"'",map);
				//				queryUpdate="UPDATE HorarioyLugar SET Latitud =" + pendiente.getLatitud() + ", Longitud="+pendiente.getLongitud()+ ", Altitud="+pendiente.getAltitud()+ " WHERE idCliente="+pendiente.getIdCliente()+" AND Tipo='Recoleccion'";
				//				//log.info(queryUpdate);
				super.jdbcTemplate.update(queryUpdate, map);
				return true;
			}
		}else if (pendiente.getFolioEvento().contains("ES-")) {
			if(pendiente.getIdHorario() == null || pendiente.getIdHorario() == 0 ){
				queryUpdate = "INSERT HorarioyLugar(idCliente,Tipo,Destino,Pais,Estado,Calle,Municipio,CP,Ruta,ZonaMensajeria,Mapa,Igual,Diario,DiaDe1,DiaA1,DiaDe2,DiaA2,"+
						" \n Lunes,LuDe1,LuA1,LuDe2,LuA2,Martes,MaDe1,MaA1,MaDe2,MaA2,Miercoles,MiDe1,MiA1,MiDe2,MiA2,Jueves,JuDe1,JuA1,JuDe2,JuA2,"+
						" \n Viernes,ViDe1,ViA1,ViDe2,ViA2,Comentarios,idProveedor,Latitud,Longitud,Altitud) " +
						" \n SELECT idCliente,'Entrega especial' AS Tipo,NULL AS Destino,Pais,Estado,Calle,Delegacion AS Municipio,CP,Ruta,Zona as ZonaMensajeria,Mapa,DifDireccion as Igual,Diario,DiaDe1,DiaA1,DiaDe2,DiaA2,"+
						" \n Lunes,LuDe1,LuA1,LuDe2,LuA2,Martes,MaDe1,MaA1,MaDe2,MaA2,Miercoles,MiDe1,MiA1,MiDe2,MiA2,Jueves,JuDe1,JuA1,JuDe2,JuA2,"+
						" \n Viernes,ViDe1,ViA1,ViDe2,ViA2,Comentarios,idProveedor," + pendiente.getLatitud() + ","+pendiente.getLongitud()+ ","+pendiente.getAltitud()+ " FROM RutaES WHERE idES='"+pendiente.getFolioEvento()+"'";
				//				//log.info(queryUpdate);
				super.jdbcTemplate.update(queryUpdate, map);

				idHorario = super.queryForLong("SELECT IDENT_CURRENT ('HorarioyLugar')");
				super.jdbcTemplate.update("UPDATE RutaES SET FK01_Direccion ="+idHorario+",Latitud =" + pendiente.getLatitud() + ", Longitud="+pendiente.getLongitud()+ ", Altitud="+pendiente.getAltitud()+ " WHERE idES='"+pendiente.getFolioEvento()+"'", map);
				//				queryUpdate="UPDATE HorarioyLugar SET Latitud =" + pendiente.getLatitud() + ", Longitud="+pendiente.getLongitud()+ ", Altitud="+pendiente.getAltitud()+ " WHERE idCliente="+pendiente.getIdCliente()+" AND Tipo='Entrega especial'";
				//				//log.info(queryUpdate);
				super.jdbcTemplate.update(queryUpdate, map);
				return true;
			}
		}else if (pendiente.getFolioEvento().contains("RE-")) {
			if(pendiente.getIdHorario() == null || pendiente.getIdHorario() == 0 ){
				queryUpdate="INSERT HorarioyLugar(idCliente,Tipo,Destino,Pais,Estado,Calle,Municipio,CP,Ruta,ZonaMensajeria,Mapa,Igual,Diario,DiaDe1,DiaA1,DiaDe2,DiaA2,"+
						" \n Lunes,LuDe1,LuA1,LuDe2,LuA2,Martes,MaDe1,MaA1,MaDe2,MaA2,Miercoles,MiDe1,MiA1,MiDe2,MiA2,Jueves,JuDe1,JuA1,JuDe2,JuA2,"+
						" \n Viernes,ViDe1,ViA1,ViDe2,ViA2,Comentarios,idProveedor,Latitud,Longitud,Altitud) "+
						" \n Select idCliente,'Recoleccion' AS Tipo,NUll AS Destino,Pais,Estado,Calle,Delegacion AS Municipio,CP,Ruta,Zona AS  ZonaMensajeria,Mapa,DifDireccion as Igual,Diario,DiaDe1,DiaA1,DiaDe2,DiaA2,"+
						" \n Lunes,LuDe1,LuA1,LuDe2,LuA2,Martes,MaDe1,MaA1,MaDe2,MaA2,Miercoles,MiDe1,MiA1,MiDe2,MiA2,Jueves,JuDe1,JuA1,JuDe2,JuA2,"+
						" \n Viernes,ViDe1,ViA1,ViDe2,ViA2,Comentarios,NULL AS idProveedor," + pendiente.getLatitud() + ","+pendiente.getLongitud()+ ","+pendiente.getAltitud()+ " from RutaRE WHERE idRE ='"+pendiente.getFolioEvento()+"'";
				//				//log.info(queryUpdate);
				super.jdbcTemplate.update(queryUpdate, map);

				idHorario = super.queryForLong("SELECT IDENT_CURRENT ('HorarioyLugar')");
				super.jdbcTemplate.update("UPDATE RutaRE SET FK01_Direccion ="+idHorario+",Latitud =" + pendiente.getLatitud() + ", Longitud="+pendiente.getLongitud()+ ", Altitud="+pendiente.getAltitud()+ " WHERE idRE='"+pendiente.getFolioEvento()+"'", map);
				//				queryUpdate="UPDATE HorarioyLugar SET Latitud =" + pendiente.getLatitud() + ", Longitud="+pendiente.getLongitud()+ ", Altitud="+pendiente.getAltitud()+ " WHERE idCliente="+pendiente.getIdCliente()+" AND Tipo='Recoleccion'";
				//				//log.info(queryUpdate);
				super.jdbcTemplate.update(queryUpdate, map);
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public List<PendientesMensajero> findPendientesEnCierre(String usuario, String tipoPendiente) throws ProquifaNetException{
		List<PendientesMensajero> PendientesEnCierre= new ArrayList<PendientesMensajero>();
		String queryCierre = "SELECT Ruta.Prioridad, chkDiferente AS DifDireccion, PEN.Folio,RUTA.Calle + ' ' + RUTA.Delegacion + ' ' + RUTA.CP as Direccion, RUTA.ZonaMensajeria, 'Entrega' AS TEvento, CLI.Clave, CLI.Nombre,idDP as folioEvento, 0 AS idProveedor, RUTA.Persona_Recibio FROM RutaDP AS RUTA LEFT JOIN (SELECT * FROM Clientes) AS CLI ON CLI.Clave = RUTA.idCliente LEFT JOIN (SELECT * FROM Pendiente) AS PEN ON PEN.Docto = RUTA.idRuta LEFT JOIN (SELECT * FROM HorarioyLugar WHERE Tipo='ENTREGA') AS HL ON HL.idCliente = RUTA.idCliente WHERE PEN.Tipo = '" + tipoPendiente + "' AND RUTA.EstadoRuta = 'EnEjecucion'   AND Responsable ='"+usuario+"' "+ 
				"UNION SELECT Ruta.Prioridad, DifDireccion, PEN.Folio,RUTA.Calle + ' ' + RUTA.Delegacion + ' ' + RUTA.CP as Direccion, RUTA.Zona AS ZonaMensajeria, 'Revision' AS TEvento, CLI.Clave, Nombre,idPR as folioEvento, 0 AS idProveedor, RUTA.Persona_Recibio FROM RutaPR AS RUTA LEFT JOIN (SELECT * FROM Clientes) AS CLI ON CLI.Clave = RUTA.idCliente LEFT JOIN (SELECT * FROM Pendiente) AS PEN ON PEN.Docto = RUTA.idRuta LEFT JOIN (SELECT * FROM HorarioyLugar WHERE Tipo='Revision') AS HL ON HL.idCliente = RUTA.idCliente WHERE PEN.Tipo = '" + tipoPendiente + "' AND EstadoRuta = 'EnEjecucion'   AND Responsable = '"+usuario+"' "+  
				"UNION SELECT Ruta.Prioridad, DifDireccion, PEN.Folio,RUTA.Calle + ' ' + RUTA.Delegacion + ' ' + RUTA.CP as Direccion, RUTA.Zona AS ZonaMensajeria,'Cobro' AS TEvento, CLI.Clave, Nombre,idPC as folioEvento, 0 AS idProveedor, RUTA.Persona_Recibio FROM RutaPC AS RUTA  LEFT JOIN (SELECT * FROM Clientes) AS CLI ON CLI.Clave = RUTA.idCliente LEFT JOIN (SELECT * FROM Pendiente) AS PEN ON PEN.Docto = RUTA.idRuta LEFT JOIN (SELECT * FROM HorarioyLugar WHERE Tipo='Pago') AS HL ON HL.idCliente = RUTA.idCliente WHERE PEN.Tipo = '" + tipoPendiente + "' AND RUTA.EstadoRuta = 'EnEjecucion'   AND Responsable = '"+usuario+"' "+ 
				"UNION SELECT Ruta.Prioridad, DifDireccion, PEN.Folio,RUTA.Calle + ' ' + RUTA.Delegacion + ' ' + RUTA.CP as Direccion, RUTA.Zona AS ZonaMensajeria, 'Recolección' AS TEvento, PRO.Clave, Nombre,idRM as folioEvento, RUTA.idProveedor, RUTA.Persona_Recibio FROM RutaRM AS RUTA LEFT JOIN (SELECT * FROM Proveedores) AS PRO ON PRO.Clave = RUTA.idProveedor LEFT JOIN (SELECT * FROM Pendiente) AS PEN ON PEN.Docto = RUTA.idRuta LEFT JOIN (SELECT * FROM HorarioyLugar WHERE Tipo='Pago') AS HL ON HL.idProveedor = RUTA.idProveedor WHERE PEN.Tipo = '" + tipoPendiente + "' AND RUTA.EstadoRuta = 'EnEjecucion'   AND Responsable = '"+usuario+"' "+  
				"UNION SELECT Ruta.Prioridad, DifDireccion, PEN.Folio,RUTA.Calle + ' ' + RUTA.Delegacion + ' ' + RUTA.CP as Direccion,RUTA.Zona AS ZonaMensajeria, 'Recoleccion especial' AS TEvento, CLI.Clave, Nombre,idRE as folioEvento, 0 AS idProveedor, RUTA.Persona_Recibio FROM RutaRE AS RUTA LEFT JOIN (SELECT * FROM Clientes) AS CLI ON CLI.Clave = RUTA.idCliente LEFT JOIN (SELECT * FROM Pendiente) AS PEN ON PEN.Docto = RUTA.idRuta LEFT JOIN (SELECT * FROM HorarioyLugar WHERE Tipo='Pago') AS HL ON HL.idCliente = RUTA.idCliente WHERE PEN.Tipo = '" + tipoPendiente + "' AND RUTA.EstadoRuta = 'EnEjecucion'   AND Responsable = '"+usuario+"' "+  
				"UNION SELECT Ruta.Prioridad, DifDireccion, PEN.Folio,RUTA.Calle + ' ' + RUTA.Delegacion + ' ' + RUTA.CP as Direccion,RUTA.Zona AS ZonaMensajeria, 'Entrega especial' AS TEvento, CLI.Clave, Nombre,idES as folioEvento,RUTA.idProveedor, RUTA.Persona_Recibio FROM RutaES AS RUTA LEFT JOIN (SELECT * FROM Clientes) AS CLI ON CLI.Clave = RUTA.idCliente LEFT JOIN (SELECT * FROM Pendiente) AS PEN ON PEN.Docto = RUTA.idRuta LEFT JOIN (SELECT * FROM HorarioyLugar WHERE Tipo='Pago') AS HL ON HL.idCliente = RUTA.idCliente WHERE PEN.Tipo = '" + tipoPendiente + "' AND RUTA.EstadoRuta = 'EnEjecucion'   AND Responsable = '"+ usuario + "'";
		log.info(queryCierre);
		PendientesEnCierre = super.jdbcTemplate.query(queryCierre
				,new RowMapper(){public Object mapRow(ResultSet rs, int rowNum) throws SQLException{PendientesMensajero pendiente = new PendientesMensajero();
				pendiente.setFolio(rs.getString("Folio"));
				pendiente.setEvento(rs.getString("TEvento"));
				pendiente.setDiferenteDireccion(rs.getBoolean("DifDireccion"));
				pendiente.setIdCliente(rs.getLong("Clave"));
				pendiente.setEmpresa(rs.getString("Nombre"));
				if(rs.getString("Prioridad")!=null){
					pendiente.setPrioridad(rs.getString("Prioridad"));            
				}else{
					pendiente.setPrioridad("Normal");
				}
				pendiente.setFolioEvento(rs.getString("FolioEvento"));
				pendiente.setIdProveedor(rs.getLong("idProveedor"));
				pendiente.setRuta(rs.getString("ZonaMensajeria"));
				pendiente.setDireccion(rs.getString("Direccion"));
				pendiente.setPersonaRecibio(rs.getString("Persona_Recibio"));
				return pendiente;
				}   }   );

		return PendientesEnCierre;

	}

	public Boolean concluirEjecucionDeRuta(PendientesMensajero pendiente, String Usuario, String tipoPendiente)  throws ProquifaNetException{
		String queryUpdate = ""; 
		String query = "";
		String Entrega = "";
		Map<String, Object> map = new HashMap<String, Object>();

		if(pendiente.getRealizado()){
			Entrega="Realizada";
			pendiente.setJustificacion("");
			pendiente.setTipoJustificacion("");
		}else{
			Entrega="No realizada";
			if(pendiente.getTipoJustificacion().equals("Mensajero") && pendiente.getJustificacion().equals("")){
				pendiente.setJustificacion("No se llegó a la ubicación y por tanto no fue posible trabajar el pendiente.");
			}
		}
		if (pendiente.getFolioEvento().contains("DP-")) {
			queryUpdate = "UPDATE RutaDP SET Entrega='"+ Entrega + "', TipoJustificacion='" + pendiente.getTipoJustificacion()+
					" \n ', RazonesEntrega='"+pendiente.getJustificacion()+"', EstadoRuta = 'ACerrar', Persona_Recibio = '" + pendiente.getPersonaRecibio() + "', Puesto_Recibio = '" + pendiente.getPuestoPersonaRecibio() + "',  OrdenPlan = '" + pendiente.getOrdenPlan() + "' WHERE idDP='"+pendiente.getFolioEvento()+"'";
			updateRutaDP(pendiente);
			query = "select Count(idRuta) from RutaDP where (EstadoRuta = 'AEjecutar' OR EstadoRuta = 'EnEjecucion') AND idRuta in (select idRuta from RutaDP where idDP= '" + pendiente.getFolioEvento() + "')";
		} else if (pendiente.getFolioEvento().contains("PR-")) {
			queryUpdate = "UPDATE RutaPR SET Entrega='"+ Entrega + "', TipoJustificacion='" + pendiente.getTipoJustificacion()+
					" \n ', RazonesEntrega='"+pendiente.getJustificacion()+"', EstadoRuta = 'ACerrar',  OrdenPlan = '" + pendiente.getOrdenPlan() + "' WHERE idPR='"+pendiente.getFolioEvento()+"'";
			query = "select Count(idRuta) from RutaPR where (EstadoRuta = 'AEjecutar' OR EstadoRuta = 'EnEjecucion') AND idRuta in (select idRuta from RutaPR where idPR= '" + pendiente.getFolioEvento() + "')";
		} else if (pendiente.getFolioEvento().contains("PC-")) {
			queryUpdate = "UPDATE RutaPC SET Entrega='"+ Entrega + "', TipoJustificacion='" + pendiente.getTipoJustificacion()+
					" \n ', RazonesEntrega='"+pendiente.getJustificacion()+"', EstadoRuta = 'ACerrar',  OrdenPlan = '" + pendiente.getOrdenPlan() + "'  WHERE idPC='"+pendiente.getFolioEvento()+"'";
			query = "select Count(idRuta) from RutaPC where (EstadoRuta = 'AEjecutar' OR EstadoRuta = 'EnEjecucion') AND idRuta in (select idRuta from RutaPC where idPC= '" + pendiente.getFolioEvento() + "')";
		} else if (pendiente.getFolioEvento().contains("RM-")) {
			queryUpdate = "UPDATE RutaRM SET Entrega='"+ Entrega + "', TipoJustificacion='" + pendiente.getTipoJustificacion()+
					" \n ', RazonesEntrega='"+pendiente.getJustificacion()+"', EstadoRuta = 'ACerrar', OrdenPlan = '" + pendiente.getOrdenPlan() + "' WHERE idRM='"+pendiente.getFolioEvento()+"'";
			query = "select Count(idRuta) from RutaRM where (EstadoRuta = 'AEjecutar' OR EstadoRuta = 'EnEjecucion') AND idRuta in (select idRuta from RutaRM where idRM= '" + pendiente.getFolioEvento() + "')";
		}else if (pendiente.getFolioEvento().contains("ES-")) {
			queryUpdate = "UPDATE RutaES SET Entrega='"+ Entrega + "', TipoJustificacion='" + pendiente.getTipoJustificacion()+
					" \n ', RazonesEntrega='"+pendiente.getJustificacion()+"', EstadoRuta = 'ACerrar', OrdenPlan = '" + pendiente.getOrdenPlan() + "' WHERE idES='"+pendiente.getFolioEvento()+"'";
			query = "select Count(idRuta) from RutaES where (EstadoRuta = 'AEjecutar' OR EstadoRuta = 'EnEjecucion') AND idRuta in (select idRuta from RutaES where idES= '" + pendiente.getFolioEvento() + "')";
		}else if (pendiente.getFolioEvento().contains("RE-")) {
			queryUpdate = "UPDATE RutaRE SET Entrega='"+ Entrega + "', TipoJustificacion='" + pendiente.getTipoJustificacion()+
					" \n ', RazonesEntrega='"+pendiente.getJustificacion()+"', EstadoRuta = 'ACerrar', OrdenPlan = '" + pendiente.getOrdenPlan() + "' WHERE idRE='"+pendiente.getFolioEvento()+"'";
			query = "select Count(idRuta) from RutaRE where (EstadoRuta = 'AEjecutar' OR EstadoRuta = 'EnEjecucion') AND idRuta in (select idRuta from RutaRE where idRE= '" + pendiente.getFolioEvento() + "')";
		}
		log.info(queryUpdate);
		log.info(query);
		try{
			super.jdbcTemplate.update(queryUpdate, map);
			Long result = super.queryForLong(query);
			if (result>0) {
				return true;
			}
			else{
				cierraPendienteDeRuta(pendiente,Usuario, tipoPendiente);
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Boolean concluirEjecucionDeRutaPL(PendientesMensajero pendiente, String Usuario, String usuarioNombre, String tipoPendiente)  throws ProquifaNetException{
		String queryUpdate = ""; 
		String query = "";
		String Entrega = "";
		String inconforme = "";
		Map<String, Object> map = new HashMap<String, Object>();
		Entrega=pendiente.getRealizadoTxt();

		pendiente.setOrdenPlan(obtenerNumeroOrden(usuarioNombre) + 1);

		if(!pendiente.getRealizadoTxt().equals("No realizada")){
			pendiente.setJustificacion("");
			pendiente.setTipoJustificacion("");
			if(pendiente.getConforme() != null && pendiente.getConforme()) {
				if(!pendiente.getConforme()){
					inconforme = pendiente.getRealizadoTxt();
				}
			}
		}else{
			if(pendiente.getTipoJustificacion().equals("Mensajero") && pendiente.getJustificacion().equals("")){
				pendiente.setJustificacion("No se llegó a la ubicación y por tanto no fue posible trabajar el pendiente.");
			}
		}
		if (pendiente.getFolioEvento().contains("DP-")) {
			//map.put("conforme", pendiente.getConforme());
			int conforme = 0;
			int entregayRevision = 0;
			if(pendiente.getConforme() != null && pendiente.getConforme() ) {
				conforme = 1;
			}

			if(pendiente.getEntregaRevision() != null && pendiente.getEntregaRevision() ) {
				entregayRevision = 1;
			}

			queryUpdate = "UPDATE RutaDP SET FFin=getdate(), Entrega='"+ Entrega + "', TipoJustificacion='" + pendiente.getTipoJustificacion()+ "' , Conforme = '"+conforme+ "' , EntregayRevision = '"+ entregayRevision +
					"' \n , RazonesEntrega='"+pendiente.getJustificacion()+"', EstadoRuta = 'ACerrar', Persona_Recibio = '" + pendiente.getPersonaRecibio() + "', Puesto_Recibio = '" + pendiente.getPuestoPersonaRecibio() + "',  OrdenPlan = '" + pendiente.getOrdenPlan() + "' WHERE idDP='"+pendiente.getFolioEvento()+"'";
			updateRutaDPPL(pendiente);
			query = "select Count(idRuta) from RutaDP where (EstadoRuta = 'AEjecutar' OR EstadoRuta = 'EnEjecucion') AND idRuta in (select idRuta from RutaDP where idDP= '" + pendiente.getFolioEvento() + "')";
		} else if (pendiente.getFolioEvento().contains("PR-")) {
			queryUpdate = "UPDATE RutaPR SET FFin=getdate(), Entrega='"+ Entrega + "', TipoJustificacion='" + pendiente.getTipoJustificacion()+
					" \n ', RazonesEntrega='"+pendiente.getJustificacion()+"', EstadoRuta = 'ACerrar',  OrdenPlan = '" + pendiente.getOrdenPlan() + "' WHERE idPR='"+pendiente.getFolioEvento()+"'";
			query = "select Count(idRuta) from RutaPR where (EstadoRuta = 'AEjecutar' OR EstadoRuta = 'EnEjecucion') AND idRuta in (select idRuta from RutaPR where idPR= '" + pendiente.getFolioEvento() + "')";
		} else if (pendiente.getFolioEvento().contains("PC-")) {
			queryUpdate = "UPDATE RutaPC SET FFin=getdate(), Entrega='"+ Entrega + "', TipoJustificacion='" + pendiente.getTipoJustificacion()+
					" \n ', RazonesEntrega='"+pendiente.getJustificacion()+"', EstadoRuta = 'ACerrar',  OrdenPlan = '" + pendiente.getOrdenPlan() + "'  WHERE idPC='"+pendiente.getFolioEvento()+"'";
			query = "select Count(idRuta) from RutaPC where (EstadoRuta = 'AEjecutar' OR EstadoRuta = 'EnEjecucion') AND idRuta in (select idRuta from RutaPC where idPC= '" + pendiente.getFolioEvento() + "')";
		} else if (pendiente.getFolioEvento().contains("RM-")) {
			queryUpdate = "UPDATE RutaRM SET FFin=getdate(), Entrega='"+ Entrega + "', TipoJustificacion='" + pendiente.getTipoJustificacion()+
					" \n ', RazonesEntrega='"+pendiente.getJustificacion()+"', EstadoRuta = 'ACerrar', OrdenPlan = '" + pendiente.getOrdenPlan() + "' WHERE idRM='"+pendiente.getFolioEvento()+"'";
			query = "select Count(idRuta) from RutaRM where (EstadoRuta = 'AEjecutar' OR EstadoRuta = 'EnEjecucion') AND idRuta in (select idRuta from RutaRM where idRM= '" + pendiente.getFolioEvento() + "')";
		}else if (pendiente.getFolioEvento().contains("ES-")) {
			queryUpdate = "UPDATE RutaES SET FFin=getdate(), Entrega='"+ Entrega + "', TipoJustificacion='" + pendiente.getTipoJustificacion()+
					" \n ', RazonesEntrega='"+pendiente.getJustificacion()+"', EstadoRuta = 'ACerrar', OrdenPlan = '" + pendiente.getOrdenPlan() + "' WHERE idES='"+pendiente.getFolioEvento()+"'";
			query = "select Count(idRuta) from RutaES where (EstadoRuta = 'AEjecutar' OR EstadoRuta = 'EnEjecucion') AND idRuta in (select idRuta from RutaES where idES= '" + pendiente.getFolioEvento() + "')";
		}else if (pendiente.getFolioEvento().contains("RE-")) {
			queryUpdate = "UPDATE RutaRE SET FFin=getdate(), Entrega='"+ Entrega + "', TipoJustificacion='" + pendiente.getTipoJustificacion()+
					" \n ', RazonesEntrega='"+pendiente.getJustificacion()+"', EstadoRuta = 'ACerrar', OrdenPlan = '" + pendiente.getOrdenPlan() + "' WHERE idRE='"+pendiente.getFolioEvento()+"'";
			query = "select Count(idRuta) from RutaRE where (EstadoRuta = 'AEjecutar' OR EstadoRuta = 'EnEjecucion') AND idRuta in (select idRuta from RutaRE where idRE= '" + pendiente.getFolioEvento() + "')";
		} 
		log.info(queryUpdate);
		log.info(query);
		try{
			super.jdbcTemplate.update(queryUpdate, map);

			if (pendiente.getFolioEvento().contains("DP-")) {
				map = new HashMap<String, Object>();
				String estado = "";
				if(!pendiente.getRealizadoTxt().equals("No realizada")) {
					estado = "Entregado";
				} else {
					estado = "No entregado";
				}	
				queryUpdate = "DECLARE @idDP AS varchar(14)	= '"+ pendiente.getFolioEvento() +"'\n" + 
						"\n" + 
						"update ppedidos set estado = '"+ estado +"' where idPPedido in (\n" + 
						"	select ep.FK01_PPedido from RutaDP AS rdp \n" + 
						"	left join PPackingList pp on pp.FK01_PackingList = rdp.FK02_PackingList \n" + 
						"	left join embalarPedido ep on ep.PK_EmbalarPedido = pp.FK02_EmbalarPedido \n" + 
						"	where rdp.idDP = @idDP\n" + 
						"	UNION\n" + 
						"	SELECT Partidas.idPPedido FROM RutaDP AS RDP\n" + 
						"	left join PPackingList pp on pp.FK01_PackingList = rdp.FK02_PackingList \n" + 
						"	left join embalarPedido ep on ep.PK_EmbalarPedido = pp.FK02_EmbalarPedido \n" + 
						"	LEFT JOIN PPedidos AS PartidaP ON PartidaP.idPPedido = ep.FK01_PPedido\n" + 
						"	LEFT JOIN PPedidos AS Partidas ON Partidas.Cpedido = PartidaP.CPedido\n" + 
						"	where RDP.idDP = @idDP\n" + 
						"	AND Partidas.Fabrica = 'Fletes' \n" + 
						"	AND Partidas.Estado NOT IN ('Cancelada', 'Enviado', 'Entregado')\n" + 
						")";
				log.info(queryUpdate);
				super.jdbcTemplate.update(queryUpdate, map);
			}

			Long result = super.queryForLong(query);
			if (result>0) {
				return true;
			}
			else{
				cierraPendienteDeRuta(pendiente,Usuario, tipoPendiente);
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean finalizoRuta(String ruta) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ruta", ruta);
		String query = "SELECT COUNT(*) AS NumPendientes FROM \n" + 
				"(SELECT idDP FROM RutaDP WHERE EstadoRuta <> 'Cerrada' AND idRuta = :ruta \n" + 
				"UNION \n" + 
				"SELECT idES FROM RutaES WHERE EstadoRuta <> 'Cerrada' AND idRuta = :ruta \n" + 
				"UNION \n" + 
				"SELECT idPC FROM RutaPC WHERE EstadoRuta <> 'Cerrada' AND idRuta = :ruta \n" + 
				"UNION \n" + 
				"SELECT idPR FROM RutaPR WHERE EstadoRuta <> 'Cerrada' AND idRuta = :ruta \n" + 
				"UNION \n" + 
				"SELECT idRE FROM RutaRE WHERE EstadoRuta <> 'Cerrada' AND idRuta = :ruta \n" + 
				"UNION \n" + 
				"SELECT idRM FROM RutaRM WHERE EstadoRuta <> 'Cerrada' AND idRuta = :ruta) AS Tabla";
		try{
			log.info(query);
			Integer numRow = (Integer) super.jdbcTemplate.queryForObject(query.toString(), map, Integer.class);
			if(numRow == 0) {
				return true;
			}else {
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}

	}


	@Override
	public int obtenerNumeroOrden(String Usuario) {
		try{
			int orden = 0;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("usuario", Usuario);

			String query = "SELECT TOP(1) consulta.OrdenPlan AS OrdenPlan FROM (\n" + 
					"SELECT Ruta.EstadoRuta,CAST(Ruta.RazonesEntrega AS VARCHAR(MAX)) AS RazonesEntrega,CAST(Ruta.TipoJustificacion AS VARCHAR(MAX)) AS TipoJustificacion,Ruta.Prioridad,OrdenPlan,idruta,\n" + 
					"RUTA.Calle + ' ' + RUTA.Delegacion + ' ' + RUTA.CP as Direccion,ruta.ZonaMensajeria, Ruta.Entrega, chkDiferente AS DifDireccion,'Entrega' AS TEvento, CLI.Clave, CLI.Nombre,idDP as folioEvento,0 AS idProveedor,  PKL.folio as folioPKL,PPKL.folio as folioPPKL \n" + 
					"FROM RutaDP AS RUTA \n" + 
					"LEFT JOIN (SELECT * FROM PackingList)  AS PKL ON  RUTA.FK02_PackingList = PKL.PK_PackingList \n" + 
					"LEFT JOIN (SELEct * FROM PPAckingList) AS PPKL ON PKL.PK_PackingList = PPKL.FK01_PAckingList \n" + 
					"LEFT JOIN (SELECT * FROM Clientes) AS CLI ON CLI.Clave = RUTA.idCliente \n" + 
					"LEFT JOIN (SELECT * FROM HorarioyLugar WHERE Tipo='ENTREGA')AS HL ON HL.idCliente = RUTA.idCliente \n" + 
					"WHERE Ruta.EstadoRuta='ACerrar' AND CAST(Ruta.FFin AS DATE) = CAST(getdate() AS DATE) AND idruta in (SELECT Docto FROM Pendiente WHERE Responsable=:usuario AND TIPO='Ruta a ejecutar') \n" + 
					"UNION \n" + 
					"SELECT Ruta.EstadoRuta,CAST(Ruta.RazonesEntrega AS VARCHAR(MAX))AS RazonesEntrega,CAST(Ruta.TipoJustificacion AS VARCHAR(MAX)) AS TipoJustificacion,Ruta.Prioridad,OrdenPlan,idruta,\n" + 
					"RUTA.Calle + ' ' + RUTA.Delegacion + ' ' + RUTA.CP as Direccion, ruta.Zona, Ruta.Entrega, DifDireccion,'Revision' AS TEvento, CLI.Clave, Nombre,idPR as folioEvento,0 AS idProveedor, null, null \n" + 
					"FROM RutaPR AS RUTA \n" + 
					"LEFT JOIN (SELECT * FROM Clientes) AS CLI ON CLI.Clave = RUTA.idCliente \n" + 
					"LEFT JOIN (SELECT * FROM HorarioyLugar WHERE Tipo='Revision') AS HL ON HL.idCliente = RUTA.idCliente \n" + 
					"WHERE Ruta.EstadoRuta='ACerrar' AND CAST(Ruta.FFin AS DATE) = CAST(getdate() AS DATE) AND idruta in (SELECT Docto FROM Pendiente WHERE Responsable=:usuario AND TIPO='Ruta a ejecutar' ) \n" + 
					"UNION \n" + 
					"SELECT Ruta.EstadoRuta,CAST(Ruta.RazonesEntrega AS VARCHAR(MAX)) AS RazonesEntrega,CAST(Ruta.TipoJustificacion AS VARCHAR(MAX)) AS TipoJustificacion,Ruta.Prioridad,OrdenPlan,idruta,\n" + 
					"RUTA.Calle + ' ' + RUTA.Delegacion + ' ' + RUTA.CP as Direccion,ruta.Zona, Ruta.Entrega, DifDireccion,'Cobro' AS TEvento, CLI.Clave, Nombre,idPC as folioEvento,0 AS idProveedor, null ,null  \n" + 
					"FROM RutaPC AS RUTA \n" + 
					"LEFT JOIN (SELECT * FROM Clientes) AS CLI ON CLI.Clave = RUTA.idCliente \n" + 
					"LEFT JOIN (SELECT * FROM HorarioyLugar WHERE Tipo='Pago') AS HL ON HL.idCliente = RUTA.idCliente \n" + 
					"WHERE Ruta.EstadoRuta='ACerrar' AND CAST(Ruta.FFin AS DATE) = CAST(getdate() AS DATE) AND idruta in (SELECT Docto FROM Pendiente WHERE Responsable=:usuario AND TIPO='Ruta a ejecutar' ) \n" + 
					"UNION \n" + 
					"SELECT Ruta.EstadoRuta,CAST(Ruta.RazonesEntrega AS VARCHAR(MAX)) AS RazonesEntrega,CAST(Ruta.TipoJustificacion AS VARCHAR(MAX)) AS TipoJustificacion,Ruta.Prioridad,OrdenPlan,idruta,\n" + 
					"RUTA.Calle + ' ' + RUTA.Delegacion + ' ' + RUTA.CP as Direccion,RUTA.ZOna, Ruta.Entrega , DifDireccion, 'Recolección' AS TEvento, PRO.Clave, Nombre,idRM as folioEvento,RUTA.idProveedor, null, null \n" + 
					"FROM RutaRM AS RUTA \n" + 
					"LEFT JOIN (SELECT * FROM Proveedores) AS PRO ON PRO.Clave = RUTA.idProveedor \n" + 
					"LEFT JOIN (SELECT * FROM HorarioyLugar WHERE Tipo='Pago') AS HL ON HL.idProveedor = RUTA.idProveedor \n" + 
					"WHERE Ruta.EstadoRuta='ACerrar' AND CAST(Ruta.FFin AS DATE) = CAST(getdate() AS DATE) AND idruta in (SELECT Docto FROM Pendiente WHERE Responsable=:usuario AND TIPO='Ruta a ejecutar' ) \n" + 
					"UNION \n" + 
					"SELECT Ruta.EstadoRuta,CAST(Ruta.RazonesEntrega AS VARCHAR(MAX)) AS RazonesEntrega,CAST(Ruta.TipoJustificacion AS VARCHAR(MAX)) AS TipoJustificacion,Ruta.Prioridad,OrdenPlan,idruta,\n" + 
					"RUTA.Calle + ' ' + RUTA.Delegacion + ' ' + RUTA.CP as Direccion,ruta.Zona, Ruta.Entrega, DifDireccion, 'Recoleccion especial' AS TEvento, CLI.Clave, Nombre,idRE as folioEvento,0 AS idProveedor,null, null \n" + 
					"FROM RutaRE AS RUTA \n" + 
					"LEFT JOIN (SELECT * FROM Clientes) AS CLI ON CLI.Clave = RUTA.idCliente \n" + 
					"LEFT JOIN (SELECT * FROM HorarioyLugar WHERE Tipo='Pago') AS HL ON HL.idCliente = RUTA.idCliente \n" + 
					"WHERE Ruta.EstadoRuta='ACerrar' AND CAST(Ruta.FFin AS DATE) = CAST(getdate() AS DATE) AND idruta in (SELECT Docto FROM Pendiente WHERE Responsable=:usuario AND TIPO='Ruta a ejecutar' ) \n" + 
					"UNION \n" + 
					"SELECT Ruta.EstadoRuta,CAST(Ruta.RazonesEntrega AS VARCHAR(MAX)) AS RazonesEntrega,CAST(Ruta.TipoJustificacion AS VARCHAR(MAX)) AS TipoJustificacion,Ruta.Prioridad,OrdenPlan,idruta,\n" + 
					"RUTA.Calle + ' ' + RUTA.Delegacion + ' ' + RUTA.CP as Direccion,ruta.Zona, Ruta.Entrega, DifDireccion, 'Entrega especial' AS TEvento, CLI.Clave, Nombre,idES as folioEvento,RUTA.idProveedor,null,null \n" + 
					"FROM RutaES AS RUTA \n" + 
					"LEFT JOIN (SELECT * FROM Clientes) AS CLI ON CLI.Clave = RUTA.idCliente \n" + 
					"LEFT JOIN (SELECT * FROM HorarioyLugar WHERE Tipo='Pago') AS HL ON HL.idCliente = RUTA.idCliente \n" + 
					"WHERE Ruta.EstadoRuta='ACerrar' AND CAST(Ruta.FFin AS DATE) = CAST(getdate() AS DATE) AND idruta in (SELECT Docto FROM Pendiente WHERE Responsable=:usuario AND TIPO='Ruta a ejecutar' )\n" + 
					") consulta ORDER BY OrdenPlan DESC";

			SqlRowSet result = super.jdbcTemplate.queryForRowSet(query, map);
			while (result.next()) {
				orden = result.getInt("OrdenPlan");
			}
			return orden;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public Boolean insertComentariosRutaDP(ComentaiosRutaDP comentaiosRutaDP) throws ProquifaNetException{
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("razonesEntrega", comentaiosRutaDP.getRazonesEntrega());
			map.put("tipoJustificacion", comentaiosRutaDP.getTipoJustificacion());
			map.put("rutadP", comentaiosRutaDP.getRutaDP());
			map.put("folioFactura", comentaiosRutaDP.getFolioFactura());

			String query = "INSERT INTO ComentariosRutaDP(RazonesEntrega, TipoJustificacion, RutadP, FolioFactura) "
					+ " VALUES (:razonesEntrega, :tipoJustificacion, :rutadP, :folioFactura)";

			super.jdbcTemplate.update(query, map);
			return true;
		}catch(Exception e){
			log.info(e.getMessage());
			return false;
		}
	}
	
	public Boolean updateRutaDP(PendientesMensajero pendiente) throws ProquifaNetException{
		String tabla ="";
        String CPedido = "";
        int PPedido ;
        String Estado= "";
        Map<String, Object> map = new HashMap<String, Object>();
        //Revisar si el producto pertenece a una Factura o Remision
        String query ="SELECT FacturaORemision FROM RutaDP WHERE idDP='"+ pendiente.getFolioEvento() +"'";
        Boolean Remision = (Boolean) super.jdbcTemplate.queryForObject(query, map, Boolean.class);        
            if (Remision){
                tabla = "PRemisiones";
            }else{
                tabla = "PFacturas";
            }
        query ="\nSELECT PP.idPPedido, PP.Cpedido, PP.Part, CAST(PP.Cant AS int) Cant, PP.Estado, P.FK02_Inventario, R.idCliente, Prod.Proveedor, Prod.idProducto, CASE WHEN Sup.idComplemento IS NOT NULL THEN 'Publicaciones' ELSE Prod.Tipo END AS Tipo,"; 
        query +="\nProd.Cantidad, Prod.Unidad, COALESCE(InsSt.Lote,Ins.Lote) AS LoteInspeccionado, Prod.Lote, ";
        query +="\nCASE WHEN (COALESCE(InsSt.Lote,Ins.Lote) COLLATE Modern_Spanish_CI_AS )=Prod.Lote THEN 'VIGENTE' ELSE 'PREVIO' END TipoLote, ";
        query +="\nCASE WHEN Prod.Proveedor IN (12,25,44,45,46,50,191,233,485,1048,1089) THEN 1 ELSE 0 END AS PConnect, ";        		
        query +="\nCOALESCE(LA.Fecha_ValidoHasta, CASE WHEN PROD.Proveedor = 50 THEN DATEADD(MONTH, 12, C.Fecha) ";
        query +="\nWHEN PROD.Proveedor = 46 THEN DATEADD(MONTH, 12, C.Fecha) ";
        query +="\nWHEN PROD.Proveedor <> 44 AND PROD.Proveedor <> 45 AND (IOC.MesCaducidad = '--ND--' OR IOC.MesCaducidad = '--ND--') THEN NULL  ";
        query +="\nWHEN PROD.Proveedor <> 44 AND PROD.Proveedor <> 45 THEN CAST(DATEADD(month, ((IOC.AnoCaducidad - 1900) * 12) + DATEPART(MM, IOC.MesCaducidad + ' 01 2016'), -1) as DATE) ";
        query +="\nEND) FechaValida, PND.Folio, CASE WHEN CAST(GETDATE() AS DATE) <= CAST(PP.FPEntrega AS DATE) THEN PND.Finicio ELSE GETDATE() END AS FechaCierre ";        
        query +="\nFROM PPedidos AS PP ";
        query +="\nLEFT JOIN (SELECT * FROM PRutaDP) AS PR ON PR.idDP='"+ pendiente.getFolioEvento() +"' ";
        query +="\nLEFT JOIN (SELECT * FROM RutaDP) AS R ON R.idDP='"+ pendiente.getFolioEvento() +"' "; 
        query +="\nLEFT JOIN (SELECT * FROM "+tabla+") AS PF ON PF.Factura=pr.Factura AND PF.FPor=pr.FPor AND PF.Part=pr.Part ";
        query +="\nLEFT JOIN (SELECT * FROM Pedidos) AS P ON P.CPedido=PP.CPedido AND P.idCliente=R.idCliente ";
        query +="\nLEFT JOIN (SELECT * FROM Productos) AS Prod ON Prod.Codigo=PP.Codigo AND Prod.Fabrica=PP.Fabrica ";
        query +="\nLEFT JOIN (SELECT * FROM Complemento) AS Sup ON Sup.idComplemento=PP.idComplemento ";
        query +="\nLEFT JOIN (SELECT * FROM PCompras WHERE Estado<>'Cancelada') AS PC ON PC.CPedido=pp.CPedido AND PC.PPedido=PP.Part ";
        query +="\nLEFT JOIN (SELECT * FROM MovimientoStock) AS MV ON MV.idPPedido=pp.idPPedido ";
        query +="\nLEFT JOIN (SELECT * FROM Stock) AS ST ON ST.idStock=MV.idStock "; 
        query +="\nLEFT JOIN (SELECT * FROM InspeccionOC) AS Ins ON Ins.idPCompra=PC.idPCompra ";
        query +="\nLEFT JOIN (SELECT * FROM InspeccionOC) AS InsSt ON InsSt.idPCompra=St.idPCompra ";        
        query +="\nLEFT JOIN (SELECT MAX(PK_LoteAnterior) PK, FK01_Producto, Lote_Anterior FROM Lote_Anterior GROUP BY FK01_Producto, Lote_Anterior) AS LA_PK ON LA_PK.FK01_Producto = PROd.idProducto AND LA_PK.Lote_Anterior collate SQL_Latin1_General_CP1_CI_AS = Ins.Lote collate SQL_Latin1_General_CP1_CI_AS ";
        query +="\nLEFT JOIN Lote_Anterior AS LA ON LA.PK_LoteAnterior = LA_PK.PK ";
        query +="\nLEFT JOIN (SELECT MAX(idPCompra) idPCompra, CPedido, PPedido, Estado FROM PCompras WHERE Estado = 'Recibido' GROUP BY CPedido, PPedido, Estado) PC2 ON PC2.CPedido = PP.CPedido AND PC2.PPedido = PP.Part AND PC2.Estado = 'Recibido' ";
        query +="\nLEFT JOIN PCompras PCompra ON PCompra.idPCompra = PC2.idPCompra ";
        query +="\nLEFT JOIN Compras C ON C.Clave = PCompra.Compra ";
        query +="\nLEFT JOIN (SELECT MAX(id) id, idPCompra FROM InspeccionOC GROUP BY idPCompra) INOC ON INOC.idPCompra = PC2.idPCompra ";
        query +="\nLEFT JOIN InspeccionOC IOC ON IOC.id = INOC.id ";
        query +="\nLEFT JOIN (SELECT Folio, FInicio, FFin, Partida, Tipo FROM Pendiente WHERE Tipo='AC por realizar' AND FFin IS NULL ) AS PND ON PND.Partida=PC.idPCompra ";
        query +="\nWHERE pp.CPedido=pf.CPedido AND pp.Part=pf.PPedido AND PP.Estado='En entrega'";
        log.info(query);          
        SqlRowSet result = super.jdbcTemplate.queryForRowSet(query, map);
        while (result.next()) {
          	CPedido = result.getString("CPedido");
        	PPedido = result.getInt("Part");
        	
        	if (pendiente.getRealizado()){
        		Estado = "Entregado";
        		String pconnect="";
        		// Si es Estandar y de las marcas: USP, SIGMA, CHROMADEX, EP, BP, FEUM, PROMOCHEM, SYNFINE RESEARCH 
        		//TORONTO RESEARCH CHEMICALS INC., PHARMAFFILIATES, TLC PHARMACEUTICAL
        		if (result.getString("Tipo").equals("Estandares") && result.getBoolean("PConnect")){        		        	
	        		int i=0;
	        		String[] valores = result.getString("Cantidad").split(" ");	        		
	        		float existencia=0.0f;
	        		DecimalFormat df = new DecimalFormat("#.########");
	        		if (result.getString("Cantidad").contains(" x ") || result.getString("Cantidad").contains(" X ")){
						try{
							existencia = Float.parseFloat(valores[0])* Float.parseFloat(valores[2]);							
						}catch(NumberFormatException e){
							log.info(e.getStackTrace().toString());
						}
						
					}else{
						try{
							//existencia = result.getFloat("Cantidad");
							existencia = 0.0F;
						}catch(NumberFormatException e){
							log.info(e.getStackTrace().toString());
						}
					}
					
	        		int idInventario = result.getInt("FK02_Inventario");
	        		String fk04Inventario;
	        		if (idInventario>0){
	        			fk04Inventario = Integer.toString(idInventario);
	        		}else{
	        			fk04Inventario ="null";
	        		}
	        		
	        		Date validoHasta = null;
	        		String fValidoHasta="";
	        		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	        		try {
	        			if (result.getDate("FechaValida") != null) {
	        				validoHasta = result.getTimestamp("FechaValida");
	        				
	        				fValidoHasta = "'" + sdf.format(validoHasta) +"'";
	        			}else{
	        				fValidoHasta = "null";
	        			}
						
					} catch (Exception e) {
						log.info(e.getStackTrace().toString());
					}

	        		for (i=0; i<result.getInt("Cant"); i++){      			
	        			pconnect = "INSERT INTO PConnect_PzaInventario (Fecha, FK01_Producto, FK02_PPedido, FK03_Cliente, FK04_Inventario, Existencia, Presentacion, Unidad, Lote, TipoLote, Estado, ValidoHasta) values (";
	        			pconnect +="GETDATE(),"+result.getLong("idProducto")+","+ result.getLong("idPPedido") +","+ result.getLong("idCliente") +","+ fk04Inventario +",'"+ df.format(existencia) +"',";
	        			pconnect += "'"+ result.getString("Cantidad") +"','"+ result.getString("Unidad") +"','"+ result.getString("LoteInspeccionado") +"',";
	        			pconnect += "'"+ result.getString("TipoLote") +"','EN RESERVA', " + fValidoHasta + ") ";
	        			log.info(pconnect);
	        			super.jdbcTemplate.update(pconnect, map);
	        		}
	        		
	        		long idPendiente;
	        		idPendiente = result.getLong("Folio");
	        		if (idPendiente>0){
	        			String ffin= sdf.format(result.getTimestamp("FechaCierre"));
	        			
	        			pconnect ="UPDATE Pendiente SET FFIN='"+ ffin +"' WHERE Folio="+ idPendiente;
	        			log.info(pconnect);
	        			super.jdbcTemplate.update(pconnect, map);
	        		}
        		}
        	}else{
        		Estado = "No entregado";
        	}
        	query = "UPDATE PPedidos SET Estado = '" + Estado + "' where CPedido = '" + CPedido + "' and Part = " + PPedido + " ";
        	//log.info(query);
        	super.jdbcTemplate.update(query, map);
        }
		return true;
	}

	public Boolean updateRutaDPPL(PendientesMensajero pendiente) throws ProquifaNetException{
		String tabla ="";
		String CPedido = "";
		int PPedido ;
		String Estado= "";
		Map<String, Object> map = new HashMap<String, Object>();
		//Revisar si el producto pertenece a una Factura o Remision
		String query ="SELECT FacturaORemision FROM RutaDP WHERE idDP='"+ pendiente.getFolioEvento() +"'";
		Boolean Remision = (Boolean) super.jdbcTemplate.queryForObject(query, map, Boolean.class);        
		if (Remision){
			tabla = "PRemisiones";
		}else{
			tabla = "PFacturas";
		}
		query ="\nSELECT PP.idPPedido, PP.Cpedido, PP.Part, CAST(PP.Cant AS int) Cant, PP.Estado, P.FK02_Inventario, R.idCliente, Prod.Proveedor, Prod.idProducto, CASE WHEN Sup.idComplemento IS NOT NULL THEN 'Publicaciones' ELSE Prod.Tipo END AS Tipo,"; 
		query +="\nProd.Cantidad, Prod.Unidad, COALESCE(InsSt.Lote,Ins.Lote) AS LoteInspeccionado, Prod.Lote, ";
		query +="\nCASE WHEN (COALESCE(InsSt.Lote,Ins.Lote) COLLATE Modern_Spanish_CI_AS )=Prod.Lote THEN 'VIGENTE' ELSE 'PREVIO' END TipoLote, ";
		query +="\nCASE WHEN Prod.Proveedor IN (12,25,44,45,46,50,191,233,485,1048,1089) THEN 1 ELSE 0 END AS PConnect, ";        		
		query +="\nCOALESCE(LA.Fecha_ValidoHasta, CASE WHEN PROD.Proveedor = 50 THEN DATEADD(MONTH, 12, C.Fecha) ";
		query +="\nWHEN PROD.Proveedor = 46 THEN DATEADD(MONTH, 12, C.Fecha) ";
		query +="\nWHEN PROD.Proveedor <> 44 AND PROD.Proveedor <> 45 AND (IOC.MesCaducidad = '--ND--' OR IOC.MesCaducidad = '--ND--') THEN NULL  ";
		query +="\nWHEN PROD.Proveedor <> 44 AND PROD.Proveedor <> 45 THEN CAST(DATEADD(month, ((IOC.AnoCaducidad - 1900) * 12) + DATEPART(MM, IOC.MesCaducidad + ' 01 2016'), -1) as DATE) ";
		query +="\nEND) FechaValida, PND.Folio, CASE WHEN CAST(GETDATE() AS DATE) <= CAST(PP.FPEntrega AS DATE) THEN PND.Finicio ELSE GETDATE() END AS FechaCierre ";        
		query +="\nFROM PPedidos AS PP "; 
		//query +="\nLEFT JOIN (SELECT * FROM PRutaDP) AS PR ON PR.idDP='"+ pendiente.getFolioEvento() +"' ";
		query +="\nLEFT JOIN (SELECT * FROM RutaDP) AS R ON R.idDP='"+ pendiente.getFolioEvento() +"' ";
		query +="\nLEFT JOIN Empresa AS Emp ON Emp.Alias COLLATE Modern_Spanish_CI_AS = R.Fpor";
		query +="\nLEFT JOIN PackingList PL ON R.FK02_PackingList = PL.PK_PackingList ";
		if (Remision){
			query +="\nLEFT JOIN PPackingList PPL ON PPL.FK01_PackingList = PL.PK_PackingList AND PPL.Fk04_Remision  = (select PK_Remision from Remisiones  REM1 where REM1.Factura collate SQL_Latin1_General_CP1_CI_AS  = R.Factura collate SQL_Latin1_General_CP1_CI_AS )";
		}else {
			query += "\nLEFT JOIN PPackingList PPL ON PPL.FK01_PackingList = PL.PK_PackingList AND PPL.Fk03_facturaElectronica  = (select PK_Factura from FacturaElectronica AS FE1 WHERE TipoComprobante = 'I' ";
			query += "\nAND FE1.Folio collate SQL_Latin1_General_CP1_CI_AS = R.Factura collate SQL_Latin1_General_CP1_CI_AS";
			query += "\nAND FE1.EmpresaEmisor = Emp.PK_Empresa)";
		}
//		query +="\nSQL_Latin1_General_CP1_CI_AS ) ";
		query +="\nLEFT JOIN EmbalarPedido EP ON EP.PK_EmbalarPedido = PPL.FK02_EmbalarPedido ";
		
		if (Remision){
			query += "\nLEFT JOIN Remisiones REM ON REM.Factura = R.Factura ";
			query += "\nLEFT JOIN PRemisiones PF ON PF.FK01_Remision = REM.PK_Remision AND PF.FPor = R.FPor AND PF.PPedido = PP.Part AND R.FacturaORemision = 1 AND PF.CPedido IS NOT NULL";
		}else {
			query += "\nLEFT JOIN PFacturas PF ON PF.Factura = R.Factura AND PF.FPor = R.FPor AND PF.PPedido = PP.Part AND R.FacturaORemision = 0 AND PF.CPedido IS NOT NULL";
		}
		
		query +="\nLEFT JOIN (SELECT * FROM Pedidos) AS P ON P.CPedido=PP.CPedido AND P.idCliente=R.idCliente ";
		query +="\nLEFT JOIN (SELECT * FROM Productos) AS Prod ON Prod.Codigo=PP.Codigo AND Prod.Fabrica=PP.Fabrica ";
		query +="\nLEFT JOIN (SELECT * FROM Complemento) AS Sup ON Sup.idComplemento=PP.idComplemento ";
		query +="\nLEFT JOIN (SELECT * FROM PCompras WHERE Estado<>'Cancelada') AS PC ON PC.CPedido=pp.CPedido AND PC.PPedido=PP.Part ";
		query +="\nLEFT JOIN (SELECT * FROM MovimientoStock) AS MV ON MV.idPPedido=pp.idPPedido ";
		query +="\nLEFT JOIN (SELECT * FROM Stock) AS ST ON ST.idStock=MV.idStock "; 
		query +="\nLEFT JOIN (SELECT * FROM InspeccionOC) AS Ins ON Ins.idPCompra=PC.idPCompra ";
		query +="\nLEFT JOIN (SELECT * FROM InspeccionOC) AS InsSt ON InsSt.idPCompra=St.idPCompra ";        
		query +="\nLEFT JOIN (SELECT MAX(PK_LoteAnterior) PK, FK01_Producto, Lote_Anterior FROM Lote_Anterior GROUP BY FK01_Producto, Lote_Anterior) AS LA_PK ON LA_PK.FK01_Producto = PROd.idProducto AND LA_PK.Lote_Anterior collate SQL_Latin1_General_CP1_CI_AS = Ins.Lote collate SQL_Latin1_General_CP1_CI_AS ";
		query +="\nLEFT JOIN Lote_Anterior AS LA ON LA.PK_LoteAnterior = LA_PK.PK ";
		query +="\nLEFT JOIN (SELECT MAX(idPCompra) idPCompra, CPedido, PPedido, Estado FROM PCompras WHERE Estado = 'Recibido' GROUP BY CPedido, PPedido, Estado) PC2 ON PC2.CPedido = PP.CPedido AND PC2.PPedido = PP.Part AND PC2.Estado = 'Recibido' ";
		query +="\nLEFT JOIN PCompras PCompra ON PCompra.idPCompra = PC2.idPCompra ";
		query +="\nLEFT JOIN Compras C ON C.Clave = PCompra.Compra ";
		query +="\nLEFT JOIN (SELECT MAX(id) id, idPCompra FROM InspeccionOC GROUP BY idPCompra) INOC ON INOC.idPCompra = PC2.idPCompra ";
		query +="\nLEFT JOIN InspeccionOC IOC ON IOC.id = INOC.id ";
		query +="\nLEFT JOIN (SELECT Folio, FInicio, FFin, Partida, Tipo FROM Pendiente WHERE Tipo='AC por realizar' AND FFin IS NULL ) AS PND ON PND.Partida=PC.idPCompra ";
		query +="\nWHERE pp.CPedido=pf.CPedido AND pp.Part=pf.PPedido AND PP.Estado='En entrega'";
		log.info(query);          
		SqlRowSet result = super.jdbcTemplate.queryForRowSet(query, map);
		while (result.next()) {
			CPedido = result.getString("CPedido");
			PPedido = result.getInt("Part");

			if (pendiente.getRealizadoTxt().equals("Realizada")){
				Estado = "Entregado";
				String pconnect="";
				// Si es Estandar y de las marcas: USP, SIGMA, CHROMADEX, EP, BP, FEUM, PROMOCHEM, SYNFINE RESEARCH 
				//TORONTO RESEARCH CHEMICALS INC., PHARMAFFILIATES, TLC PHARMACEUTICAL
				if (result.getString("Tipo").equals("Estandares") && result.getBoolean("PConnect")){        		        	
					int i=0;
					String cantidad = result.getString("Cantidad");
					cantidad = cantidad.replace(" ","");
					cantidad = cantidad.replace("X","x");			
					String[] valores = cantidad.split("x");
											        	
					float existencia=0.0f;
					DecimalFormat df = new DecimalFormat("#.########");
					if (cantidad.contains("x") ){
						try{
							existencia = Float.parseFloat(valores[0])* Float.parseFloat(valores[1]);							
						}catch(NumberFormatException e){
							log.info(e.getStackTrace().toString());
						}

					}else{
						try{
							//existencia = result.getFloat("Cantidad");
							existencia =0.0F;
						}catch(NumberFormatException e){
							log.info(e.getStackTrace().toString());
						}
					}

					int idInventario = result.getInt("FK02_Inventario");
					String fk04Inventario;
					if (idInventario>0){
						fk04Inventario = Integer.toString(idInventario);
					}else{
						fk04Inventario ="null";
					}

					Date validoHasta = null;
					String fValidoHasta="";
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					try {
						if (result.getDate("FechaValida") != null) {
							validoHasta = result.getTimestamp("FechaValida");

							fValidoHasta = "'" + sdf.format(validoHasta) +"'";
						}else{
							fValidoHasta = "null";
						}

					} catch (Exception e) {
						log.info(e.getStackTrace().toString());
					}

					for (i=0; i<result.getInt("Cant"); i++){      			
						pconnect = "INSERT INTO PConnect_PzaInventario (Fecha, FK01_Producto, FK02_PPedido, FK03_Cliente, FK04_Inventario, Existencia, Presentacion, Unidad, Lote, TipoLote, Estado, ValidoHasta) values (";
						pconnect +="GETDATE(),"+result.getLong("idProducto")+","+ result.getLong("idPPedido") +","+ result.getLong("idCliente") +","+ fk04Inventario +",'"+ df.format(existencia) +"',";
						pconnect += "'"+ result.getString("Cantidad") +"','"+ result.getString("Unidad") +"','"+ result.getString("LoteInspeccionado") +"',";
						pconnect += "'"+ result.getString("TipoLote") +"','EN RESERVA', " + fValidoHasta + ") ";
						log.info(pconnect);
						super.jdbcTemplate.update(pconnect, map);
					}

					long idPendiente;
					idPendiente = result.getLong("Folio");
					if (idPendiente>0){
						String ffin= sdf.format(result.getTimestamp("FechaCierre"));

						pconnect ="UPDATE Pendiente SET FFIN='"+ ffin +"' WHERE Folio="+ idPendiente;
						log.info(pconnect);
						super.jdbcTemplate.update(pconnect, map);
					}
				}
			}else{
				Estado = "No entregado";
			}
			query = "UPDATE PPedidos SET Estado = '" + Estado + "' where CPedido = '" + CPedido + "' and Part = " + PPedido + " ";
			//log.info(query);
			super.jdbcTemplate.update(query, map);
		}
		return true;
	}

	public Boolean cierraPendienteDeRuta(PendientesMensajero pendiente,String Usuario, String tipoPendiente) throws ProquifaNetException{
		Map<String, Object> map = new HashMap<String, Object>();

		String query2 = "SELECT Docto FROM Pendiente WHERE Tipo = '" + tipoPendiente + "' AND Responsable = '" + Usuario + "' AND FFin IS NULL and Docto IN ";
		if (pendiente.getFolioEvento().contains("DP-")) {
			query2 += "(SELECT idRuta FROM RutaDP WHERE EstadoRuta = 'ACerrar' AND idDP='"+pendiente.getFolioEvento()+"' )";
		} else if (pendiente.getFolioEvento().contains("PR-")) {
			query2 += "(SELECT idRuta FROM RutaPR WHERE EstadoRuta = 'ACerrar' AND idPR='"+pendiente.getFolioEvento()+"' )";
		} else if (pendiente.getFolioEvento().contains("PC-")) {
			query2 += "(SELECT idRuta FROM RutaPC WHERE EstadoRuta = 'ACerrar' AND idPC='"+pendiente.getFolioEvento()+"' )";
		} else if (pendiente.getFolioEvento().contains("RM-")) {
			query2 += "(SELECT idRuta FROM RutaRM WHERE EstadoRuta = 'ACerrar' AND idRM='"+pendiente.getFolioEvento()+"' )";
		}else if (pendiente.getFolioEvento().contains("ES-")) {
			query2 += "(SELECT idRuta FROM RutaES WHERE EstadoRuta = 'ACerrar' AND idES='"+pendiente.getFolioEvento()+"' )";
		}else if (pendiente.getFolioEvento().contains("RE-")) {
			query2 += "(SELECT idRuta FROM RutaRE WHERE EstadoRuta = 'ACerrar' AND idRE='"+pendiente.getFolioEvento()+"' )";
		}
		String queryUpdate = "";
		try{

			String Docto = (String) super.jdbcTemplate.queryForObject(query2, map, String.class);

			queryUpdate = "UPDATE pendiente SET FFin = GETDATE() WHERE Ffin IS NULL AND Tipo = '" + tipoPendiente + "' AND docto = '" + Docto + "' ";
			super.jdbcTemplate.update(queryUpdate, map);


			return true;
		}catch(Exception e){
			log.info(e.getMessage());
			return false;
		}
	}
	/**
	 * 
	 * @param Usuario
	 * @param TipoConsulta
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PendientesMensajero> findPendientesCerrados(String Usuario, String tipoPendiente) throws ProquifaNetException{
		try{
			String queryFind = "";     
			//		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd"); // AND FInicio>='" + formatoFecha.format(fecha) + "'
			//        Date fecha = new Date();
			queryFind="SELECT Ruta.EstadoRuta,CAST(Ruta.RazonesEntrega AS VARCHAR(MAX)) AS RazonesEntrega,CAST(Ruta.TipoJustificacion AS VARCHAR(MAX)) AS TipoJustificacion,Ruta.Prioridad,OrdenPlan,idruta,"+
					" \n RUTA.Calle + ' ' + RUTA.Delegacion + ' ' + RUTA.CP as Direccion,ruta.ZonaMensajeria, Ruta.Entrega, chkDiferente AS DifDireccion,'Entrega' AS TEvento, CLI.Clave, CLI.Nombre,idDP as folioEvento,0 AS idProveedor "+
					" \n FROM RutaDP AS RUTA "+
					" \n LEFT JOIN (SELECT * FROM Clientes) AS CLI ON CLI.Clave = RUTA.idCliente "+
					" \n LEFT JOIN (SELECT * FROM HorarioyLugar WHERE Tipo='ENTREGA')AS HL ON HL.idCliente = RUTA.idCliente "+
					" \n WHERE Ruta.EstadoRuta='ACerrar' AND idruta in (SELECT Docto FROM Pendiente WHERE Responsable='"+ Usuario +"' AND TIPO='" + tipoPendiente + "') "+
					" \n UNION "+
					" \n SELECT Ruta.EstadoRuta,CAST(Ruta.RazonesEntrega AS VARCHAR(MAX))AS RazonesEntrega,CAST(Ruta.TipoJustificacion AS VARCHAR(MAX)) AS TipoJustificacion,Ruta.Prioridad,OrdenPlan,idruta,"+
					" \n RUTA.Calle + ' ' + RUTA.Delegacion + ' ' + RUTA.CP as Direccion, ruta.Zona, Ruta.Entrega, DifDireccion,'Revision' AS TEvento, CLI.Clave, Nombre,idPR as folioEvento,0 AS idProveedor "+
					" \n FROM RutaPR AS RUTA "+
					" \n LEFT JOIN (SELECT * FROM Clientes) AS CLI ON CLI.Clave = RUTA.idCliente "+
					" \n LEFT JOIN (SELECT * FROM HorarioyLugar WHERE Tipo='Revision') AS HL ON HL.idCliente = RUTA.idCliente "+
					" \n WHERE Ruta.EstadoRuta='ACerrar' AND idruta in (SELECT Docto FROM Pendiente WHERE Responsable='"+Usuario+"' AND TIPO='" + tipoPendiente + "' ) "+
					" \n UNION "+
					" \n SELECT Ruta.EstadoRuta,CAST(Ruta.RazonesEntrega AS VARCHAR(MAX)) AS RazonesEntrega,CAST(Ruta.TipoJustificacion AS VARCHAR(MAX)) AS TipoJustificacion,Ruta.Prioridad,OrdenPlan,idruta,"+
					" \n RUTA.Calle + ' ' + RUTA.Delegacion + ' ' + RUTA.CP as Direccion,ruta.Zona, Ruta.Entrega, DifDireccion,'Cobro' AS TEvento, CLI.Clave, Nombre,idPC as folioEvento,0 AS idProveedor "+
					" \n FROM RutaPC AS RUTA "+
					" \n LEFT JOIN (SELECT * FROM Clientes) AS CLI ON CLI.Clave = RUTA.idCliente "+
					" \n LEFT JOIN (SELECT * FROM HorarioyLugar WHERE Tipo='Pago') AS HL ON HL.idCliente = RUTA.idCliente "+
					" \n WHERE Ruta.EstadoRuta='ACerrar' AND idruta in (SELECT Docto FROM Pendiente WHERE Responsable='"+Usuario+"' AND TIPO='" + tipoPendiente + "' ) "+
					" \n UNION "+
					" \n SELECT Ruta.EstadoRuta,CAST(Ruta.RazonesEntrega AS VARCHAR(MAX)) AS RazonesEntrega,CAST(Ruta.TipoJustificacion AS VARCHAR(MAX)) AS TipoJustificacion,Ruta.Prioridad,OrdenPlan,idruta,"+
					" \n RUTA.Calle + ' ' + RUTA.Delegacion + ' ' + RUTA.CP as Direccion,RUTA.ZOna, Ruta.Entrega , DifDireccion, 'Recolección' AS TEvento, PRO.Clave, Nombre,idRM as folioEvento,RUTA.idProveedor "+
					" \n FROM RutaRM AS RUTA "+
					" \n LEFT JOIN (SELECT * FROM Proveedores) AS PRO ON PRO.Clave = RUTA.idProveedor "+
					" \n LEFT JOIN (SELECT * FROM HorarioyLugar WHERE Tipo='Pago') AS HL ON HL.idProveedor = RUTA.idProveedor "+
					" \n WHERE Ruta.EstadoRuta='ACerrar' AND idruta in (SELECT Docto FROM Pendiente WHERE Responsable='"+Usuario+"' AND TIPO='" + tipoPendiente + "' ) "+
					" \n UNION "+
					" \n SELECT Ruta.EstadoRuta,CAST(Ruta.RazonesEntrega AS VARCHAR(MAX)) AS RazonesEntrega,CAST(Ruta.TipoJustificacion AS VARCHAR(MAX)) AS TipoJustificacion,Ruta.Prioridad,OrdenPlan,idruta,"+
					" \n RUTA.Calle + ' ' + RUTA.Delegacion + ' ' + RUTA.CP as Direccion,ruta.Zona, Ruta.Entrega, DifDireccion, 'Recoleccion especial' AS TEvento, CLI.Clave, Nombre,idRE as folioEvento,0 AS idProveedor "+
					" \n FROM RutaRE AS RUTA "+
					" \n LEFT JOIN (SELECT * FROM Clientes) AS CLI ON CLI.Clave = RUTA.idCliente "+
					" \n LEFT JOIN (SELECT * FROM HorarioyLugar WHERE Tipo='Pago') AS HL ON HL.idCliente = RUTA.idCliente "+
					" \n WHERE Ruta.EstadoRuta='ACerrar' AND idruta in (SELECT Docto FROM Pendiente WHERE Responsable='"+Usuario+"' AND TIPO='" + tipoPendiente + "' ) "+
					" \n UNION "+
					" \n SELECT Ruta.EstadoRuta,CAST(Ruta.RazonesEntrega AS VARCHAR(MAX)) AS RazonesEntrega,CAST(Ruta.TipoJustificacion AS VARCHAR(MAX)) AS TipoJustificacion,Ruta.Prioridad,OrdenPlan,idruta,"+
					" \n RUTA.Calle + ' ' + RUTA.Delegacion + ' ' + RUTA.CP as Direccion,ruta.Zona, Ruta.Entrega, DifDireccion, 'Entrega especial' AS TEvento, CLI.Clave, Nombre,idES as folioEvento,RUTA.idProveedor "+
					" \n FROM RutaES AS RUTA "+
					" \n LEFT JOIN (SELECT * FROM Clientes) AS CLI ON CLI.Clave = RUTA.idCliente "+
					" \n LEFT JOIN (SELECT * FROM HorarioyLugar WHERE Tipo='Pago') AS HL ON HL.idCliente = RUTA.idCliente "+
					" \n WHERE Ruta.EstadoRuta='ACerrar' AND idruta in (SELECT Docto FROM Pendiente WHERE Responsable='"+Usuario+"' AND TIPO='" + tipoPendiente + "' )";

			//		//log.info(queryFind);
			List<PendientesMensajero> PendientesCerrados= new ArrayList<PendientesMensajero>();
			PendientesCerrados = super.jdbcTemplate.query(queryFind	, new PendientesCerradosRowMapper() );

			return PendientesCerrados;
		}catch (Exception e) {
			log.info(e.getMessage());
			return null;// TODO: handle exception
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConfirmacionEntrega> obtenerInformacionEntrega(String ruta)
			throws ProquifaNetException {
		try{
			String query = 	
					"\n SELECT R.RegistroDEntrega, CAST(CLI.Nombre AS VARCHAR(MAX)) Nombre, P.Pedido, PP.CPedido, COALESCE(PREMI.FACTURA, F.Factura) FACTURA, PP.Codigo, CAST(PP.Concepto AS VARCHAR(MAX)) Concepto," +
							"\n SUM(PP.Cant) Cant, coalesce(C.Titulo,'') Titulo, coalesce (C.Contacto,'') Contacto, C.eMail, CLI.Vendedor, coalesce(E.Usuario,'') Usuario, " +
							"\n coalesce(R.Persona_Recibio,'') Persona_Recibio, coalesce(R.Puesto_Recibio,'') Puesto_Recibio, PP.FPEntrega , p.FPedido, R.Entrega, " +
							"\n coalesce(CAST(R.TipoJustificacion AS VARCHAR(MAX)),'') TipoJustificacion, CAST(R.RazonesEntrega AS VARCHAR(MAX)) RazonesEntrega, getdate() fecha," +
							"\n CASE	WHEN	(SELECT DATEDIFF(D,PP.FPEntrega, GETDATE()) - DATEDIFF(week,PP.FPEntrega,GETDATE()) * 2  - " +
							"\n CASE WHEN DATENAME(dw, PP.FPEntrega) <> 'Saturday' AND DATENAME(dw, GETDATE()) = 'Saturday' THEN 1  " +
							"\n WHEN DATENAME(dw, PP.FPEntrega) = 'Saturday' AND DATENAME(dw, GETDATE()) <> 'Saturday' THEN -1 ELSE 0 END) > 0" +
							"\n THEN	'Fuera' ELSE 'Dentro' END TIEMPO, " +
							"\n  (SELECT DATEDIFF(D,PP.FPEntrega, GETDATE()) - DATEDIFF(week,PP.FPEntrega,GETDATE()) * 2  - " +
							"\n CASE WHEN DATENAME(dw, PP.FPEntrega) <> 'Saturday' AND DATENAME(dw, GETDATE()) = 'Saturday' THEN 1  " +
							"\n WHEN DATENAME(dw, PP.FPEntrega) = 'Saturday' AND DATENAME(dw, GETDATE()) <> 'Saturday' THEN -1 ELSE 0 END) DIAS, PRD.FK02_Fabricante, PRD.Codigo, PRD.Lote" +
							"\n FROM PRutaDP PR" +
							"\n LEFT JOIN (SELECT * FROM RutaDP) R ON R.idDP = PR.idDP" +
							"\n LEFT JOIN (SELECT * FROM PRemisiones) PREMI ON PREMI.Factura = R.Factura AND PREMI.FPor = R.FPor AND PREMI.Part = PR.Part AND R.FacturaORemision = 1" +
							"\n LEFT JOIN (SELECT * FROM PFacturas) PF ON PF.Factura = PR.Factura AND PF.FPor = PR.FPor AND PF.Part = PR.Part AND R.FacturaORemision = 0 AND PF.CPedido IS NOT NULL" +
							"\n LEFT JOIN (SELECT * FROM Facturas where desistema = 1) F ON F.Factura = PF.Factura AND F.FPor = PF.FPor " +
							"\n LEFT JOIN (SELECT * FROM PPedidos) PP ON (PP.CPedido = PF.CPedido AND PP.Part = PF.PPedido) OR (PP.CPedido = PREMI.CPedido AND PP.Part = PREMI.PPedido)" +
							"\n LEFT JOIN (SELECT * FROM Productos) PRD ON PP.FK02_Producto = PRD.idProducto" +
							"\n LEFT JOIN (SELECT * FROM Pedidos) P ON P.CPedido = PP.CPedido" +
							"\n LEFT JOIN (SELECT * FROM Contactos) C ON C.idContacto = P.idContacto" +
							"\n LEFT JOIN (SELECT * FROM Clientes) CLI ON CLI.CLAVE = P.idCliente" +
							"\n LEFT JOIN (select * from Empleados) E ON E.clave = CLI.FK01_EV" +
							"\n WHERE PR.idDP = '" + ruta + "' and p.cpedido is not null AND (P.CPedido=F.CPedido OR P.Cpedido=PREMI.CPedido) " +
							"\n GROUP BY R.RegistroDEntrega, CAST(CLI.Nombre AS VARCHAR(MAX)), P.Pedido, PP.CPedido, COALESCE(PREMI.FACTURA, F.Factura), PP.Codigo,  CAST(PP.Concepto AS VARCHAR(MAX)) ," +
							"\n coalesce(C.Titulo,'') , PRD.FK02_Fabricante, PRD.Codigo, PRD.Lote,  coalesce (C.Contacto,'') , C.eMail, CLI.Vendedor, coalesce(E.Usuario,'') , " +
							"\n coalesce(R.Persona_Recibio,'') , coalesce(R.Puesto_Recibio,'') , PP.FPEntrega , p.FPedido, R.Entrega, coalesce(CAST(R.TipoJustificacion AS VARCHAR(MAX)),'') , " +
							"\n CAST(R.RazonesEntrega AS VARCHAR(MAX)), PP.FPEntrega";

			log.info(query);

			return super.jdbcTemplate.query(query, new ConfirmacionEntregaRowMapper());

		}catch(Exception e){
			log.info(e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ConfirmacionEntrega> obtenerInformacionEntregaPL(String ruta)
			throws ProquifaNetException {
		try{
			
			String query = 	
					"\n SELECT R.RegistroDEntrega, CAST(CLI.Nombre AS VARCHAR(MAX)) Nombre, P.Pedido, PP.CPedido, COALESCE(PREMI.FACTURA, F.Factura) FACTURA, PP.Codigo, CAST(PP.Concepto AS VARCHAR(MAX)) Concepto," +
							"\n SUM(PP.Cant) Cant, coalesce(C.Titulo,'') Titulo, coalesce (C.Contacto,'') Contacto, C.eMail, CLI.Vendedor, coalesce(E.Usuario,'') Usuario, " +
							"\n coalesce(R.Persona_Recibio,'') Persona_Recibio, coalesce(R.Puesto_Recibio,'') Puesto_Recibio, PP.FPEntrega , p.FPedido, R.Entrega, " +
							"\n coalesce(CAST(R.TipoJustificacion AS VARCHAR(MAX)),'') TipoJustificacion, CAST(R.RazonesEntrega AS VARCHAR(MAX)) RazonesEntrega, getdate() fecha," +
							"\n CASE	WHEN	(SELECT DATEDIFF(D,PP.FPEntrega, GETDATE()) - DATEDIFF(week,PP.FPEntrega,GETDATE()) * 2  - " +
							"\n CASE WHEN DATENAME(dw, PP.FPEntrega) <> 'Saturday' AND DATENAME(dw, GETDATE()) = 'Saturday' THEN 1  " +
							"\n WHEN DATENAME(dw, PP.FPEntrega) = 'Saturday' AND DATENAME(dw, GETDATE()) <> 'Saturday' THEN -1 ELSE 0 END) > 0" +
							"\n THEN	'Fuera' ELSE 'Dentro' END TIEMPO, " +
							"\n  (SELECT DATEDIFF(D,PP.FPEntrega, GETDATE()) - DATEDIFF(week,PP.FPEntrega,GETDATE()) * 2  - " +
							"\n CASE WHEN DATENAME(dw, PP.FPEntrega) <> 'Saturday' AND DATENAME(dw, GETDATE()) = 'Saturday' THEN 1  " +
							"\n WHEN DATENAME(dw, PP.FPEntrega) = 'Saturday' AND DATENAME(dw, GETDATE()) <> 'Saturday' THEN -1 ELSE 0 END) DIAS, PRD.FK02_Fabricante, PRD.Codigo, PRD.Lote" +
							"\n FROM RutaDP R" +
							"\n LEFT JOIN PackingList PL ON R.FK02_PackingList = PL.PK_PackingList" +
							"\n LEFT JOIN PPackingList PPL ON PPL.FK01_PackingList = PL.PK_PackingList" +
							"\n LEFT JOIN EmbalarPedido EP ON EP.PK_EmbalarPedido = PPL.FK02_EmbalarPedido" +
							"\n LEFT JOIN PPedidos PP ON PP.idPPedido = EP.FK01_PPedido" +
							"\n LEFT JOIN Remisiones REMI ON REMI.Factura = R.Factura " +
							"\n LEFT JOIN PRemisiones PREMI ON PREMI.FK01_Remision = REMI.PK_Remision AND PREMI.FPor = R.FPor AND PREMI.PPedido = PP.Part AND R.FacturaORemision = 1" +
							"\n LEFT JOIN PFacturas PF ON PF.Factura = R.Factura AND PF.FPor = R.FPor AND PF.PPedido = PP.Part AND R.FacturaORemision = 0 AND PF.CPedido IS NOT NULL" +
							"\n LEFT JOIN (SELECT * FROM Facturas where desistema = 1) F ON F.Factura = PF.Factura AND F.FPor = PF.FPor" +
							"\n LEFT JOIN (SELECT * FROM Productos) PRD ON PP.FK02_Producto = PRD.idProducto" +
							"\n LEFT JOIN (SELECT * FROM Pedidos) P ON P.CPedido = PP.CPedido" +
							"\n LEFT JOIN (SELECT * FROM Contactos) C ON C.idContacto = P.idContacto" +
							"\n LEFT JOIN (SELECT * FROM Clientes) CLI ON CLI.CLAVE = P.idCliente" +
							"\n LEFT JOIN (select * from Empleados) E ON E.clave = CLI.FK01_EV" +
							"\n WHERE R.idDP = '" + ruta + "' and p.cpedido is not null AND (P.CPedido=F.CPedido OR P.Cpedido=PREMI.CPedido) " +
							"\n GROUP BY R.RegistroDEntrega, CAST(CLI.Nombre AS VARCHAR(MAX)), P.Pedido, PP.CPedido, COALESCE(PREMI.FACTURA, F.Factura), PP.Codigo,  CAST(PP.Concepto AS VARCHAR(MAX)) ," +
							"\n coalesce(C.Titulo,'') , PRD.FK02_Fabricante, PRD.Codigo, PRD.Lote,  coalesce (C.Contacto,'') , C.eMail, CLI.Vendedor, coalesce(E.Usuario,'') , " +
							"\n coalesce(R.Persona_Recibio,'') , coalesce(R.Puesto_Recibio,'') , PP.FPEntrega , p.FPedido, R.Entrega, coalesce(CAST(R.TipoJustificacion AS VARCHAR(MAX)),'') , " +
							"\n CAST(R.RazonesEntrega AS VARCHAR(MAX)), PP.FPEntrega";

			log.info(query);

			return super.jdbcTemplate.query(query, new ConfirmacionEntregaRowMapper());

		}catch(Exception e){
			log.info(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConfirmacionEntrega> obtenerSumaPiezasFabrica(String ruta)
			throws ProquifaNetException {
		try{
			String query = 
					"\n SELECT " +
							"\n sum(PP.Cant) cantidad, PP.Fabrica" +
							"\n FROM PRutaDP PR" +
							"\n LEFT JOIN (SELECT * FROM RutaDP) R ON R.idDP = PR.idDP" +
							"\n LEFT JOIN (SELECT * FROM PFacturas) PF ON PF.Factura = PR.Factura AND PF.FPor = PR.FPor AND PF.Part = PR.Part AND PF.CPedido IS NOT NULL " +
							"\n LEFT JOIN (SELECT * FROM Facturas) F ON F.Factura = PF.Factura AND F.FPor = PF.FPor " +
							"\n LEFT JOIN (SELECT * FROM PPedidos) PP ON PP.CPedido = PF.CPedido AND PP.Part = PF.PPedido" +
							"\n LEFT JOIN (SELECT * FROM Pedidos) P ON P.CPedido = PP.CPedido" +
							"\n WHERE PR.idDP = '" + ruta + "' AND F.Cpedido=P.CPedido" +
							"\n group by PP.Fabrica order by cantidad";
			return super.jdbcTemplate.query(query, new ConfirmacionEntregaResumenRowMapper());

		}catch (Exception e) {
			log.info(e.getMessage());
			return null;// TODO: handle exception
		}
	}

	@Override
	public void updateRutaDPfolioNE(String ruta, String folio)
			throws ProquifaNetException {
		Map<String, Object> map = new HashMap<String, Object>();

		String query = "UPDATE RutaDP SET FolioNE = '" + folio + "' where idDP = '" + ruta + "'";
		super.jdbcTemplate.update(query, map);
	}


	//	metodos para la insercion de un Receptor
	@Override
	public Boolean insertPersonalAlmacenCliente(PersonalAlmacenCliente contacto)
			throws ProquifaNetException {
		Map<String, Object> map = new HashMap<String, Object>();

		try{
			String query = "INSERT INTO PersonalAlmacen_Cliente(FK01_Cliente, Puesto, Nombre) VALUES( " + contacto.getIdCliente() + ", '" + contacto.getPuesto() + "', '" + contacto.getNombre() + "')";

			super.jdbcTemplate.update(query, map);

			return true;
		}catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}


	}

	@Override
	public Boolean updatePersonalAlmacenCliente(PersonalAlmacenCliente contacto)
			throws ProquifaNetException {
		Map<String, Object> map = new HashMap<String, Object>();

		try{
			String query = "UPDATE PersonalAlmacen_Cliente SET Puesto = '" + contacto.getPuesto() + "', Nombre = '" + contacto.getNombre() + "' WHERE PK_PersonalAC = " + contacto.getIdPersonal();
			super.jdbcTemplate.update(query, map);
			return true;

		}catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PersonalAlmacenCliente> getPersonalAlmacenCliente(
			Long idCliente) throws ProquifaNetException {
		try{
			String sql = "SELECT * FROM PersonalAlmacen_Cliente WHERE FK01_Cliente =" + idCliente;
			return super.jdbcTemplate.query(sql, new PersonalAlmacenClienteRowMapper());
		}catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}

	@Override
	public Boolean deletePersonalAlmacenCliente(Long idPersonal)throws ProquifaNetException {
		Map<String, Object> map = new HashMap<String, Object>();

		try{
			super.jdbcTemplate.update("DELETE PersonalAlmacen_Cliente WHERE PK_PersonalAC = " + idPersonal, map);
			return true;
		}catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getContactosExtrasNotificacion(String ruta)
			throws ProquifaNetException {
		Map<String, Object> map = new HashMap<String, Object>();

		try{
			String query ="SELECT DISTINCT coalesce(Cont.eMail,'') FROM RutaDP AS R " +  
					"LEFT JOIN PackingList AS PL ON PL.pk_PackingList = R.Fk02_PackingList " +
					"LEFT JOIN PPackingList AS PPL ON PPL.FK01_PackingList = PL.PK_PackingList " +
					"LEFT JOIN EmbalarPedido AS EP ON EP.PK_EmbalarPedido = PPL.FK02_EmbalarPedido " +
					"LEFT JOIN PPedidos AS PP ON PP.idPPedido = EP.FK01_PPedido " +
					"LEFT JOIN Pedidos AS P ON P.Cpedido = PP.Cpedido " +
					"LEFT JOIN Pedido_Contacto AS PedCont ON PedCont.FK01_Pedido = P.idPedido " +
					"LEFT JOIN Contactos AS Cont ON Cont.idContacto = PedCont.FK02_Contacto " +
					"WHERE R.idDP='" + ruta +"' AND Cont.Habilitado = 1 GROUP BY Cont.eMail";
			
			return super.jdbcTemplate.queryForList(query, map, String.class);
		}catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PendientesMensajero>findPendientesPorUsuario(String condiciones) throws ProquifaNetException {
		try{

			String query = 
					"SELECT 'DP' AS tipoRuta,idDP,P.FInicio AS FechaAsignacion,R.Fecha AS FechaRealizacion,C.nombre AS Cliente,R.Factura,R.ZonaMensajeria AS Zona,R.FPEntrega AS FEE, R.Prioridad," +
							"\n(CASE WHEN R.Moneda = 'Pesos' OR R.Moneda = 'M.N.' THEN R.Importe / COALESCE(R.TCambio,M.PDolar) " +
							"\nWHEN R.Moneda = 'Euros' THEN R.Importe * COALESCE(R.TCambio,M.EDolar) ELSE R.Importe END) AS Monto " +
							"\nFROM Pendiente AS P " +
							"\nINNER JOIN (" +
							"\nSELECT RDP.idDP, RDP.Fecha,RDP.Factura,RDP.ZonaMensajeria,RDP.Prioridad,RDP.idRuta,RDP.idCliente,RDP.EstadoRuta,PP.FPEntrega,F.Moneda,F.Importe,F.TCambio FROM RutaDP AS RDP" +
							"\nINNER JOIN PRutaDP PRDP ON RDP.idDP = PRDP.idDP" +
							"\nINNER JOIN PFacturas PF ON PRDP.Factura = PF.Factura AND PRDP.FPor = PF.FPor AND PRDP.Part = PF.Part AND PF.CPedido IS NOT NULL" +
							"\nINNER JOIN PPedidos PP ON PF.CPedido = PP.CPedido " +
							"\nLEFT JOIN Facturas F ON RDP.Factura = F.factura AND PRDP.FPor = F.FPor" +
							"\n) AS R ON  P.Docto = R.idRuta " +
							"\nINNER JOIN Clientes AS C ON  R.idCliente = C.Clave " +
							"\nLEFT JOIN Monedas M ON R.Fecha = M.Fecha "+
							"\nWHERE  " + condiciones + " AND P.FFin IS NULL" +
							"\nUNION" +
							"\nSELECT 'RM' AS tipoRuta,idRM,P.FInicio AS FechaAsignacion,R.Fecha AS FechaRealizacion,Pro.nombre AS Cliente,NULL AS Factura,R.Zona,R.Fecha AS FEE,R.Prioridad,NULL AS Monto " +
							"\nFROM Pendiente AS P " +
							"\nINNER JOIN RutaRM AS R ON  P.Docto = R.idRuta " +
							"\nINNER JOIN Proveedores AS Pro ON  R.idProveedor = Pro.Clave " +
							"\nWHERE  " + condiciones + " AND P.FFin IS NULL" +
							"\nUNION" +
							"\nSELECT 'PC' AS tipoRuta,idPC,P.FInicio AS FechaAsignacion,R.Fecha AS FechaRealizacion,C.nombre AS Cliente,R.Factura,R.Zona,R.Fecha AS FEE,R.Prioridad," +
							"\n(CASE WHEN F.Moneda = 'Pesos' OR F.Moneda = 'M.N.' THEN F.Importe / COALESCE(F.TCambio,M.PDolar) " +
							"\nWHEN F.Moneda = 'Euros' THEN F.Importe * COALESCE(F.TCambio,M.EDolar) ELSE F.Importe END) AS Monto"+
							"\nFROM Pendiente AS P " +
							"\nINNER JOIN RutaPC AS R ON  P.Docto = R.idRuta " +
							"\nINNER JOIN Clientes AS C ON  R.idCliente = C.Clave " +
							"\nLEFT JOIN Facturas F ON R.Factura = F.factura AND R.FPor = F.FPor " +
							"\nLEFT JOIN Monedas M ON R.Fecha = M.Fecha"+
							"\nWHERE  " + condiciones + " AND P.FFin IS NULL" +
							"\nUNION" +
							"\nSELECT 'PR' AS tipoRuta,idPR,P.FInicio AS FechaAsignacion,R.Fecha AS FechaRealizacion,C.nombre AS Cliente,R.Factura,R.Zona,R.Fecha AS FEE,R.Prioridad," +
							"\n(CASE WHEN F.Moneda = 'Pesos' OR F.Moneda = 'M.N.' THEN F.Importe / COALESCE(F.TCambio,M.PDolar) " +
							"\nWHEN F.Moneda = 'Euros' THEN F.Importe * COALESCE(F.TCambio,M.EDolar) ELSE F.Importe END) AS Monto"+
							"\nFROM Pendiente AS P " +
							"\nINNER JOIN RutaPR AS R ON  P.Docto = R.idRuta " +
							"\nINNER JOIN Clientes AS C ON  R.idCliente = C.Clave " +
							"\nLEFT JOIN Facturas F ON R.Factura = F.factura AND R.FPor = F.FPor " +
							"\nLEFT JOIN Monedas M ON R.Fecha = M.Fecha" +
							"\nWHERE  " + condiciones + " AND P.FFin IS NULL" +
							"\nUNION" +
							"\nSELECT 'ES' AS tipoRuta,idES,P.FInicio AS FechaAsignacion,R.Fecha AS FechaRealizacion,C.nombre AS Cliente,R.Factura,R.Zona,R.Fecha AS FEE,R.Prioridad,NULL AS Monto " +
							"\nFROM Pendiente AS P " +
							"\nINNER JOIN RutaES AS R ON  P.Docto = R.idRuta " +
							"\nINNER JOIN Clientes AS C ON  R.idCliente = C.Clave " +
							"\nWHERE  " + condiciones + " AND P.FFin IS NULL" +
							"\nUNION" +
							"\nSELECT 'RE' AS tipoRuta,idRE,P.FInicio AS FechaAsignacion,R.Fecha AS FechaRealizacion,C.nombre AS Cliente,R.Factura,R.Zona,R.Fecha AS FEE,R.Prioridad,NULL AS Monto " +
							"\nFROM Pendiente AS P " +
							"\nINNER JOIN RutaRE AS R ON  P.Docto = R.idRuta " +
							"\nINNER JOIN Clientes AS C ON  R.idCliente = C.Clave " +
							"\nWHERE  " + condiciones + " AND P.FFin IS NULL" +
							"\nUNION" +
							"\nSELECT 'AD' AS tipoRuta,idAD,P.FInicio AS FechaAsignacion,R.Fecha AS FechaRealizacion,C.nombre AS Cliente,R.Factura,R.Zona,R.Fecha AS FEE,R.Prioridad,NULL AS Monto " +
							"\nFROM Pendiente AS P " +
							"\nINNER JOIN RutaAD AS R ON  P.Docto = R.idRuta " +
							"\nINNER JOIN Clientes AS C ON  R.idCliente = C.Clave " +
							"\nWHERE  " + condiciones + " AND P.FFin IS NULL" +
							"\nORDER BY Zona";
			log.info("Query: " + query);	            		    
			return super.jdbcTemplate.query(query, new PendienteRutaTrabajarRowMapper());
		}

		catch (Exception e)
		{
			log.info(e.getMessage());
		}
		return null;
	}



	@Override
	public Boolean updateCerrarPendiente(PendientesMensajero pendientesMensajero,String tipoActual,String tipoActualizar,String estadoActualRuta) throws ProquifaNetException {

		try{


			String estadoActual = "";
			String estadoActualizar = "";
			String tabla = "Ruta";
			String idNombre = "id";


			tabla += pendientesMensajero.getTipoRuta();
			idNombre += pendientesMensajero.getTipoRuta();

			if (pendientesMensajero.getTipo().equals("Ruta a trabajar")) {
				estadoActual = "AColectar";
				estadoActualizar = "APlanear";
			} else if(pendientesMensajero.getTipo().equals("Ruta a planear")){
				estadoActual = "APlanear";
				estadoActualizar = "AEjecutar";
			}

			this.updateRutaPorPerndiente(tabla,estadoActual,estadoActualizar,idNombre,pendientesMensajero.getStDocto());


			int total = super.queryForInt("SELECT SUM(id) AS total FROM " +
					" \n(SELECT COUNT(idRM) AS id FROM RutaRM WHERE RutaRM.idRM = '" + pendientesMensajero.getStDocto() + "' AND EstadoRuta = '" + estadoActualRuta + "'" +
					" \nUNION SELECT COUNT(idPC) AS id FROM RutaPC WHERE RutaPC.idPC = '" + pendientesMensajero.getStDocto() + "' AND EstadoRuta = '" + estadoActualRuta + "'" +
					" \nUNION SELECT COUNT(idPR) AS id FROM RutaPR WHERE RutaPR.idPR = '" + pendientesMensajero.getStDocto() + "' AND EstadoRuta = '" + estadoActualRuta + "'" +
					" \nUNION SELECT COUNT(idES) AS id FROM RutaES WHERE RutaES.idES = '" + pendientesMensajero.getStDocto() + "' AND EstadoRuta = '" + estadoActualRuta + "'" +
					" \nUNION SELECT COUNT(idRE) AS id FROM RutaRE WHERE RutaRE.idRE = '" + pendientesMensajero.getStDocto() + "' AND EstadoRuta = '" + estadoActualRuta + "'" +
					" \nUNION SELECT COUNT(idAD) AS id FROM RutaAD WHERE RutaAD.idAD = '" + pendientesMensajero.getStDocto() + "' AND EstadoRuta = '" + estadoActualRuta + "') AS rutas");

			if (total == 0) {
				if(this.updatePendienteFechaFin(pendientesMensajero.getStDocto(),tipoActual)){
					return this.insertPendiente(pendientesMensajero.getStDocto(), pendientesMensajero.getPartida(), pendientesMensajero.getNombre(),tipoActualizar);
				}
			}
			return true;
		} catch(Exception e){
			e.printStackTrace();	
			return false;
		}
	}

	@Override
	public boolean updatePendienteFechaFin(String docto,String tipo) throws ProquifaNetException{
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			super.jdbcTemplate.update("UPDATE pendiente SET FFin = GETDATE() WHERE Ffin IS NULL AND Tipo = '" + tipo +"' AND docto = '" + docto + "'", map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return false;
		}
	}

	@Override
	public boolean insertPendiente(String docto, String partida, String responsable, String tipo) throws ProquifaNetException{
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			super.jdbcTemplate.update("INSERT INTO pendiente (docto, partida, fInicio, responsable, tipo) VALUES ('" + docto + "', '" + partida + "', GETDATE(), '" + responsable + "', '" + tipo + "')", map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return false;
		}
	}

	@Override
	public boolean updateRutaPorPerndiente(String nombreTabla,String estadoActual, String estadoRuta,String idNombre,String docto) throws ProquifaNetException{
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			super.jdbcTemplate.update("UPDATE " + nombreTabla + " SET EstadoRuta = '" + estadoRuta + "' "
					+ "WHERE  " + idNombre + " = '" + docto +"' AND EstadoRuta = '" + estadoActual + "'", map);
			return true;
		} catch (Exception e) {
			return false;
		}
	}


	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, List<TotalMensajero>> findPendientesMensajero(Integer idResponsable) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append(" \n");
			sbQuery.append("DECLARE @RESPONSABLE VARCHAR(50) = (SELECT Usuario FROM Empleados WHERE Clave = :idResponsable) \n");
			sbQuery.append("DECLARE @TABLA TABLE (Cliente varchar(300), Proveedor varchar(300), Evento varchar(100), Prioridad varchar(10), idMensajero int, Zona varchar(100)) \n");
			sbQuery.append("insert into @TABLA SELECT EM.Cliente, EM.Proveedor, EM.Evento, EM.Prioridad, ISNULL(EM.idMensajero,CASE WHEN @RESPONSABLE = 'GestorRutaGDL' THEN 191 ELSE 100 END), EM.Zona  FROM EventoMensajero EM WHERE EM.idResponsable = :idResponsable \n");
			sbQuery.append("DECLARE @TABLA_MENSAJERO TABLE (Etiqueta varchar(100), Cliente int, Proveedor int, Evento int, Mensajero int, Tipo varchar(500), Orden int) \n");
			sbQuery.append("insert into @TABLA_MENSAJERO SELECT Etiqueta, COUNT(Cliente) Cliente, COUNT(Proveedor) Proveedor, SUM(Evento) Evento, Clave Mensajero, Mensajero COLLATE Modern_Spanish_CI_AS Tipo, 0 FROM ( \n"); 
			sbQuery.append("SELECT 'Mensajero' Etiqueta, EM.Cliente, EM.Proveedor, COUNT(EM.Evento) Evento, E.Clave Clave, E.Nombre Mensajero  \n");
			sbQuery.append("FROM @TABLA EM  \n");
			sbQuery.append("LEFT JOIN Empleados E ON E.Clave = COALESCE(EM.idMensajero, CASE WHEN @RESPONSABLE = 'GestorRutaGDL' THEN 191 ELSE 100 END) \n");  
			sbQuery.append("GROUP BY EM.Cliente, E.Clave, E.Nombre, EM.Proveedor ) Mensajero  \n");
			sbQuery.append("GROUP BY Etiqueta, Mensajero, Clave \n");
			sbQuery.append("SELECT * FROM @TABLA_MENSAJERO  \n");
			sbQuery.append("UNION ALL \n");
			sbQuery.append("SELECT 'Mensajero' Etiqueta, 0, 0, 0, E.Clave, E.Nombre COLLATE Modern_Spanish_CI_AS Tipo, 0 FROM Empleados E \n");  
			sbQuery.append("WHERE (SELECT count(Tipo) FROM @TABLA_MENSAJERO) <> 0 AND Nivel = CASE WHEN @RESPONSABLE = 'GestorRutaGDL' THEN 46 ELSE 10 END  AND Fase <> 0 AND E.Nombre NOT IN (SELECT Tipo COLLATE Modern_Spanish_CI_AS FROM @TABLA_MENSAJERO) \n"); 
			sbQuery.append("UNION ALL \n");
			sbQuery.append("SELECT 'Zonas' Etiqueta, 0 Cliente, 0 Proveedor, 0 Evento, 0 Mensajero, '' Tipo, 0 Orden \n");
			sbQuery.append("UNION ALL \n");
			sbQuery.append("SELECT 'Zonas' Etiqueta, SUM(Cliente) Cliente, SUM(Proveedor),  SUM(Evento) Evento, COUNT(Mensajero) Mensajero, Zona, 0 FROM ( \n"); 
			sbQuery.append("SELECT COUNT(Cliente) Cliente, COUNT(Proveedor) Proveedor, SUM(Evento) Evento, Mensajero, Zona FROM (   \n");
			sbQuery.append("SELECT EM.Cliente, EM.Proveedor, COUNT(EM.Evento) Evento, E.Nombre AS Mensajero, EM.Zona FROM @TABLA EM  \n");
			sbQuery.append("LEFT JOIN Empleados E ON E.Clave = EM.idMensajero   \n");
			sbQuery.append("GROUP BY EM.Cliente, E.Nombre, EM.Zona, EM.Proveedor) Zona \n"); 
			sbQuery.append("GROUP BY  Mensajero, Zona) Zona   \n");
			sbQuery.append("GROUP BY Zona   \n");
			sbQuery.append("UNION ALL \n");
			sbQuery.append("SELECT Etiqueta, SUM(Cliente), SUM(Proveedor), SUM(Evento), SUM(Mensajero), Tipo, Orden FROM ( \n");
			sbQuery.append("SELECT 'Prioridades' Etiqueta, 0 Cliente, 0 Proveedor, 0 Evento, 0 Mensajero, 'P1' Tipo, 1 Orden \n");
			sbQuery.append("UNION ALL  \n");
			sbQuery.append("SELECT 'Prioridades', 0, 0, 0 ,0, 'P2', 2 \n");
			sbQuery.append("UNION ALL  \n");
			sbQuery.append("SELECT 'Prioridades', 0, 0, 0 ,0, 'P3', 3 \n");
			sbQuery.append("UNION ALL  \n");
			sbQuery.append("SELECT 'Prioridades' Etiqueta, COUNT(Cliente) Cliente, COUNT(Proveedor), SUM(Evento) Evento, 0 Mensajero, Prioridad, Orden FROM ( \n"); 
			sbQuery.append("SELECT EM.Cliente, EM.Proveedor, COUNT(EM.Evento) Evento, EM.Prioridad , CASE WHEN EM.Prioridad = 'P1' THEN 1 WHEN EM.Prioridad = 'P2' THEN 2 ELSE 3 END Orden \n");
			sbQuery.append("FROM @TABLA EM    \n");
			sbQuery.append("GROUP BY EM.Cliente, EM.Prioridad, EM.Proveedor ) Prioridades \n"); 
			sbQuery.append("GROUP BY Prioridad, Orden ) Prioridades \n");
			sbQuery.append("GROUP BY Etiqueta, Tipo,Orden \n");
			sbQuery.append("UNION ALL  \n");
			sbQuery.append("SELECT 'Eventos' Etiqueta, 0 Cliente, 0 Proveedor, 0 Evento, 0 Mensajero, '' Tipo, 0 Orden \n");
			sbQuery.append("UNION ALL \n");
			sbQuery.append("SELECT Etiqueta, COUNT(Cliente) Cliente, COUNT(Proveedor), 1, 0, Evento, 0 FROM ( \n"); 
			sbQuery.append("SELECT 'Eventos' Etiqueta, EM.Cliente, EM.Proveedor, EM.Evento   \n");
			sbQuery.append("FROM @TABLA EM  GROUP BY EM.Cliente, EM.Evento, EM.Proveedor ) Eventos \n"); 
			sbQuery.append("GROUP BY Etiqueta, Evento   \n");
			sbQuery.append("UNION ALL   \n");
			sbQuery.append("SELECT 'Clientes' Etiqueta, 0 Cliente, 0 Proveedor, 0 Evento, 0 Mensajero, '' Tipo, 0 Orden \n");
			sbQuery.append("UNION ALL \n");
			sbQuery.append("SELECT Etiqueta, 0, 0, COUNT(Evento) Evento, 0, Cliente, 0 FROM ( \n"); 
			sbQuery.append("SELECT 'Clientes' Etiqueta, EM.Evento, EM.Cliente   \n");
			sbQuery.append("FROM @TABLA EM WHERE EM.Cliente IS NOT NULL ) Cliente   \n");
			sbQuery.append("GROUP BY Etiqueta, Cliente  \n");
			sbQuery.append("UNION ALL \n");
			sbQuery.append("SELECT 'Totales', COUNT(Cliente) Cliente, COUNT(Proveedor) Proveedor, (SELECT COUNT(EM.Evento) Evento FROM @TABLA EM) Evento, \n");
			sbQuery.append("(SELECT COUNT(idMensajero) Mensajero fROM (SELECT idMensajero FROM @TABLA EM GROUP BY idMEnsajero ) Mensajero) Mensajero, '', 0 \n");
			sbQuery.append("FROM ( \n");
			sbQuery.append("SELECT EM.Cliente, EM.Proveedor \n");
			sbQuery.append("FROM @TABLA EM \n");
			sbQuery.append("GROUP BY EM.Cliente, EM.Proveedor ) Clientes \n");
			sbQuery.append("ORDER BY Orden, Etiqueta DESC, Evento DESC, Cliente DESC \n");

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idResponsable", idResponsable);
			Map<String, List<TotalMensajero>> mapReturn = new HashMap<String, List<TotalMensajero>>();
			Map<String, Object> mapMensajero = new HashMap<String, Object>();
			mapMensajero.put("mensajero", true);
			jdbcTemplate.query(sbQuery.toString(), map, new RowMapper() {
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					TotalMensajero total = new TotalMensajero();
					total.setTotalClientes(rs.getInt("Cliente"));
					total.setTotalEventos(rs.getInt("Evento"));
					total.setTotalMensajeros(rs.getInt("Mensajero"));
					total.setTotalProveedores(rs.getInt("Proveedor"));

					String etiqueta = rs.getString("Etiqueta");
					String tipo = rs.getString("Tipo");

					if (etiqueta.equals("Mensajero")) {
						total.setMensajero(tipo);
						mapMensajero.put("mensajero", false);
					}

					if (etiqueta.equals("Zonas")) {
						total.setZona(tipo);
					}

					if (etiqueta.equals("Eventos")) {
						total.setEvento(tipo);
					}

					if (etiqueta.equals("Clientes")) {
						total.setCliente(tipo);
					}

					if (etiqueta.equals("Prioridades")) {
						total.setPrioridad(tipo);
					}

					if (mapReturn.get(etiqueta) != null) {
						mapReturn.get(etiqueta).add(total);
					} else {
						mapReturn.put(etiqueta, new ArrayList<TotalMensajero>());
						mapReturn.get(etiqueta).add(total);
					}

					return null;
				}
			});

			if ((boolean) mapMensajero.get("mensajero")) {
				TotalMensajero total = new TotalMensajero();
				total.setTotalClientes(0);
				total.setTotalEventos(0);
				total.setTotalMensajeros(0);
				total.setTotalProveedores(0);
				total.setMensajero("");
				mapReturn.put("Mensajero", new ArrayList<TotalMensajero>());
				mapReturn.get("Mensajero").add(total);
			}

			return mapReturn;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}


	@Override
	public List<AsignarMensajero> obtenerDatosDetalleAsignarMensajero(Integer idUsuario, String estado) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			DateFormat formatoHora = new SimpleDateFormat("HH");
			Date hoy = new Date();
			Integer hora = Integer.parseInt(formatoHora.format(hoy));
			sbQuery.append(" \n");
			if(hora < 10 || idUsuario == 1015 ) {
				sbQuery.append("DECLARE @DAY as VARCHAR(30) = DATENAME(dw,GETDATE())\n");
			} else {
				sbQuery.append("DECLARE @DAY as VARCHAR(30) = CASE WHEN DATENAME(dw,DATEADD(DAY,1,GETDATE())) = 'Sábado' OR DATENAME(dw,DATEADD(DAY,1,GETDATE())) = 'Saturday' THEN DATENAME(dw,DATEADD(DAY,3,GETDATE())) ELSE DATENAME(dw,DATEADD(DAY,1,GETDATE())) END \n");
	
			}
			sbQuery.append("SELECT VISTA.ID, VISTA.idCliente, VISTA.Cliente, VISTA.idMensajero, VISTA.Nombre, VISTA.Usuario, VISTA.Folio, VISTA.Zona, SUM(VISTA.Monto) Monto, VISTA.Calle, VISTA.Estado, VISTA.Pais, \n");
			sbQuery.append("VISTA.Dia, VISTA.Evento, VISTA.Diario, VISTA.Altitud, VISTA.Longitud, VISTA.Latitud, VISTA.Orden, VISTA.De, VISTA.A, VISTA.Prioridad, VISTA.idDP, VISTA.Dias, VISTA.Activo,  convert(nvarchar(30),(MIN(VISTA.fee)) , 103) fee    FROM ( \n");
			sbQuery.append("SELECT COALESCE(AM.PK_AsignarMensajero,0) ID, COALESCE(EM.idCliente, EM.idProveedor) as idCliente, COALESCE(EM.Cliente, EM.Proveedor) as Cliente, EMP.Clave idMensajero, EMP.Nombre, EMP.Usuario, EM.Folio, CASE WHEN EM.Ruta IS NULL THEN EM.Ruta ELSE EM.Zona END Zona, COALESCE(EM.Monto,0) Monto,  \n");
			sbQuery.append("HyL.Calle, HyL.Estado, HyL.Pais, @DAY Dia, EM.Evento, HyL.Diario, HyL.Altitud, HyL.Longitud, HyL.Latitud, COALESCE(AM.Orden,0) Orden,  \n");
			sbQuery.append("CASE WHEN HyL.Diario = 1 THEN HyL.DiaDe1 + '-' + HyL.DiaA1  \n");
			sbQuery.append("WHEN  (@DAY = 'Monday' OR @DAY = 'Lunes') AND HyL.Lunes = 1 THEN HyL.LuDe1 + '-' + HyL.LuA1 \n");  
			sbQuery.append("WHEN  (@DAY = 'Tuesday' OR @DAY = 'Martes') AND HyL.Martes = 1 THEN HyL.MaDe1 + '-' + HyL.MAa1   \n");
			sbQuery.append("WHEN  (@DAY = 'Wednesday' OR @DAY = 'Miércoles') AND HyL.Miercoles = 1 THEN HyL.MiDe1 + '-' + HyL.MiA1 \n");  
			sbQuery.append("WHEN  (@DAY = 'Thursday' OR @DAY = 'Jueves') AND HyL.Jueves = 1 THEN HyL.JuDe1 + '-' + HyL.JuA1   \n");
			sbQuery.append("WHEN  (@DAY = 'Friday' OR @DAY = 'Viernes') AND HyL.Viernes = 1 THEN HyL.Vide1 + '-' + HyL.ViA1 ELSE '' END De, \n"); 
			sbQuery.append("CASE WHEN HyL.Diario = 1 THEN HyL.DiaDe2 + '-' + HyL.DiaA2  \n");
			sbQuery.append("WHEN  (@DAY = 'Monday' OR @DAY = 'Lunes') AND HyL.Lunes = 1 THEN HyL.LuDe2 + '-' + HyL.LuA2 \n"); 
			sbQuery.append("WHEN  (@DAY = 'Tuesday' OR @DAY = 'Martes') AND HyL.Martes = 1 THEN HyL.MaDe2 + '-' + HyL.MAa2   \n");
			sbQuery.append("WHEN  (@DAY = 'Wednesday' OR @DAY = 'Miércoles') AND HyL.Miercoles = 1 THEN HyL.MiDe2 + '-' + HyL.MiA2 \n"); 
			sbQuery.append("WHEN  (@DAY = 'Thursday' OR @DAY = 'Jueves') AND HyL.Jueves = 1 THEN HyL.JuDe2 + '-' + HyL.JuA2  \n");
			sbQuery.append("WHEN  (@DAY = 'Friday' OR @DAY = 'Viernes') AND HyL.Viernes = 1 THEN HyL.Vide2 + '-' + HyL.ViA2 ELSE '' END A, EM.Prioridad, EM.idDP, '' AS Dias, 1 AS Activo, EM.fee \n");
			sbQuery.append("FROM EventoMensajero EM  \n");
			sbQuery.append("LEFT JOIN AsignarMensajero AM ON AM.Evento = EM.Folio COLLATE Modern_Spanish_CI_AS AND (AM.Estado IS NULL OR AM.Estado <> 'Publicado') \n"); 
			sbQuery.append("LEFT JOIN Empleados EMP ON EMP.Clave = COALESCE(AM.FK01_Mensajero,EM.idMensajero, 0)  \n");
			sbQuery.append("LEFT JOIN Facturas F ON F.Factura = EM.Factura AND F.Fpor = EM.FPor \n");
			sbQuery.append("LEFT JOIN Pedidos PED ON PED.CPedido = F.CPedido \n");
			sbQuery.append("LEFT JOIN HorarioyLugar HyL ON HyL.idHorario = COALESCE(EM.idHorario,PED.FK01_Horario) \n");
			sbQuery.append("WHERE 1=1 AND CASE WHEN HyL.Diario = 1 THEN 'OK' \n");
			sbQuery.append("WHEN  (@DAY = 'Monday' OR @DAY = 'Lunes') AND HyL.Lunes = 1 THEN 'OK' \n");
			sbQuery.append("WHEN  (@DAY = 'Tuesday' OR @DAY = 'Martes') AND HyL.Martes = 1 THEN 'OK' \n");
			sbQuery.append("WHEN  (@DAY = 'Wednesday' OR @DAY = 'Miércoles') AND HyL.Miercoles = 1 THEN 'OK' \n");
			sbQuery.append("WHEN  (@DAY = 'Thursday' OR @DAY = 'Jueves') AND HyL.Jueves = 1 THEN 'OK' \n");
			sbQuery.append("WHEN  (@DAY = 'Friday' OR @DAY = 'Viernes') AND HyL.Viernes = 1 THEN 'OK' ELSE 'NOT' END = 'OK' \n");

			if (estado != null && !estado.equals("")) {
				sbQuery.append(" AND AM.Estado = :estado ");
			}
			if (idUsuario != null && idUsuario > 0) {
				sbQuery.append(" AND EM.idResponsable = :idResponsable ");
			}
			
			sbQuery.append("\n UNION ALL \n");
			sbQuery.append("SELECT COALESCE(AM.PK_AsignarMensajero,0) ID, COALESCE(EM.idCliente, EM.idProveedor) as idCliente, COALESCE(EM.Cliente, EM.Proveedor) as Cliente, 0 idMensajero, EMP.Nombre, EMP.Usuario, EM.Folio, CASE WHEN EM.Ruta IS NULL THEN EM.Ruta ELSE EM.Zona END Zona, COALESCE(EM.Monto,0) Monto,  \n");
			sbQuery.append("HyL.Calle, HyL.Estado, HyL.Pais, @DAY Dia, EM.Evento, HyL.Diario, HyL.Altitud, HyL.Longitud, HyL.Latitud, COALESCE(AM.Orden,0) Orden,  \n");
			sbQuery.append("CASE WHEN HyL.Diario = 1 THEN HyL.DiaDe1 + '-' + HyL.DiaA1  \n");
			sbQuery.append("WHEN HyL.Lunes = 1 THEN HyL.LuDe1 + '-' + HyL.LuA1 \n");  
			sbQuery.append("WHEN HyL.Martes = 1 THEN HyL.MaDe1 + '-' + HyL.MAa1   \n");
			sbQuery.append("WHEN HyL.Miercoles = 1 THEN HyL.MiDe1 + '-' + HyL.MiA1 \n");  
			sbQuery.append("WHEN HyL.Jueves = 1 THEN HyL.JuDe1 + '-' + HyL.JuA1   \n");
			sbQuery.append("WHEN HyL.Viernes = 1 THEN HyL.Vide1 + '-' + HyL.ViA1 ELSE '' END De, \n"); 
			sbQuery.append("CASE WHEN HyL.Diario = 1 THEN HyL.DiaDe2 + '-' + HyL.DiaA2  \n");
			sbQuery.append("WHEN HyL.Lunes = 1 THEN HyL.LuDe2 + '-' + HyL.LuA2 \n"); 
			sbQuery.append("WHEN HyL.Martes = 1 THEN HyL.MaDe2 + '-' + HyL.MAa2   \n");
			sbQuery.append("WHEN HyL.Miercoles = 1 THEN HyL.MiDe2 + '-' + HyL.MiA2 \n"); 
			sbQuery.append("WHEN HyL.Jueves = 1 THEN HyL.JuDe2 + '-' + HyL.JuA2  \n");
			sbQuery.append("WHEN HyL.Viernes = 1 THEN HyL.Vide2 + '-' + HyL.ViA2 ELSE '' END A, EM.Prioridad, EM.idDP, ");
			sbQuery.append("CONCAT(CASE WHEN HyL.Lunes = 1 THEN 'Lunes ' ELSE '' END,\n");
			sbQuery.append("CASE WHEN HyL.Martes = 1 THEN 'Martes ' ELSE '' END,\n");
			sbQuery.append("CASE WHEN HyL.Miercoles = 1 THEN 'Miércoles ' ELSE '' END,\n");
			sbQuery.append("CASE WHEN HyL.Jueves = 1 THEN 'Jueves ' ELSE '' END,\n"); 
			sbQuery.append("CASE WHEN HyL.Viernes = 1 THEN 'Viernes ' ELSE '' END ) AS Dias, 0 AS Activo, EM.fee \n");
			sbQuery.append("FROM EventoMensajero EM  \n");
			sbQuery.append("LEFT JOIN AsignarMensajero AM ON AM.Evento = EM.Folio COLLATE Modern_Spanish_CI_AS AND (AM.Estado IS NULL OR AM.Estado <> 'Publicado') \n"); 
			sbQuery.append("LEFT JOIN Empleados EMP ON EMP.Clave = COALESCE(AM.FK01_Mensajero,EM.idMensajero, 0)  \n");
			sbQuery.append("LEFT JOIN Facturas F ON F.Factura = EM.Factura AND F.Fpor = EM.FPor \n");
			sbQuery.append("LEFT JOIN Pedidos PED ON PED.CPedido = F.CPedido \n");
			sbQuery.append("LEFT JOIN HorarioyLugar HyL ON HyL.idHorario = COALESCE(EM.idHorario,PED.FK01_Horario) \n");
			sbQuery.append("WHERE 1=1 AND CASE WHEN HyL.Diario = 1 THEN 'NOT' \n");
			sbQuery.append("WHEN  (@DAY = 'Monday' OR @DAY = 'Lunes') AND HyL.Lunes = 0 THEN 'OK' \n");
			sbQuery.append("WHEN  (@DAY = 'Tuesday' OR @DAY = 'Martes') AND HyL.Martes = 0 THEN 'OK' \n");
			sbQuery.append("WHEN  (@DAY = 'Wednesday' OR @DAY = 'Miércoles') AND HyL.Miercoles = 0 THEN 'OK' \n");
			sbQuery.append("WHEN  (@DAY = 'Thursday' OR @DAY = 'Jueves') AND HyL.Jueves = 0 THEN 'OK' \n");
			sbQuery.append("WHEN  (@DAY = 'Friday' OR @DAY = 'Viernes') AND HyL.Viernes = 0 THEN 'OK' ELSE 'NOT' END = 'OK' \n");

			if (estado != null && !estado.equals("")) {
				sbQuery.append(" AND AM.Estado = :estado ");
			}
			if (idUsuario != null && idUsuario > 0) {
				sbQuery.append(" AND EM.idResponsable = :idResponsable ");
			}
			sbQuery.append(") VISTA \n");
			sbQuery.append("GROUP BY VISTA.ID, VISTA.idCliente, VISTA.idMensajero, VISTA.Cliente, VISTA.Folio, VISTA.idMensajero, VISTA.Nombre, VISTA.Usuario, VISTA.Zona, VISTA.Calle, \n");
			sbQuery.append("VISTA.Estado, VISTA.Pais, VISTA.Dia, VISTA.Evento,  VISTA.Diario, VISTA.Altitud, VISTA.Longitud, VISTA.Latitud, VISTA.Orden, VISTA.De, VISTA.A, VISTA.Prioridad, VISTA.idDP, VISTA.Dias, VISTA.Activo ");

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("estado", estado);
			map.put("idResponsable", idUsuario);
			
			log.info(sbQuery.toString());
			log.info(estado);
			log.info("",idUsuario);

			return jdbcTemplate.query(sbQuery.toString(), map, new AsignarMensajeroDetalleRowMapper());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	@Override
	public boolean guardarAsignarMensajero(AsignarMensajero ruta ) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("INSERT INTO AsignarMensajero(Fecha, FK01_Mensajero, FK02_Cliente, Evento, Estado, Orden) VALUES(GETDATE(), :idMensajero, :idCliente, :evento, 'Abierto', :orden) \n");
			sbQuery.append(" \n");

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idMensajero", ruta.getIdMensajero());
			map.put("idCliente", ruta.getIdCliente());
			map.put("evento", ruta.getFolio());
			map.put("orden", ruta.getOrden());
			jdbcTemplate.update(sbQuery.toString(), map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}


	@Override
	public boolean actualizarAsignarMensajero(AsignarMensajero ruta ) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("UPDATE AsignarMensajero SET FK01_Mensajero = :idMensajero, FK02_Cliente = :idCliente, Evento = :Folio, Estado = :Estado, Orden = :Orden \n");
			sbQuery.append("WHERE PK_AsignarMensajero = :idAsignarMensajero \n");

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idMensajero", ruta.getIdMensajero());
			map.put("idCliente", ruta.getIdCliente());
			map.put("Folio", ruta.getFolio());
			map.put("Estado", ruta.getEstado());
			map.put("Orden", ruta.getOrden());
			map.put("idAsignarMensajero", ruta.getIdAsignarMensajero());
			jdbcTemplate.update(sbQuery.toString(), map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}

	@Override
	public boolean actualizarEstadoAsignarMensajero(AsignarMensajero ruta, String folio ) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("UPDATE AsignarMensajero SET Estado = :Estado \n");
			sbQuery.append("WHERE Evento = :folio \n");

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Estado", ruta.getEstado());
			map.put("folio", folio);
			jdbcTemplate.update(sbQuery.toString(), map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}


	@Override
	public boolean actualizarAlmacen(AsignarMensajero ruta) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("");
			sbQuery.append(" \n");

			if (ruta.getFolio().contains("ET")) {
				sbQuery.append("UPDATE RutaDP SET Ruta = :ruta WHERE idEntrega = :Folio AND EstadoRuta IS NULL \n");

			} else {
				sbQuery.append("UPDATE PackingList SET Ruta = :ruta WHERE Folio = :Folio \n");

			}

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ruta", ruta.getZona());
			map.put("Folio", ruta.getFolio());

			jdbcTemplate.update(sbQuery.toString(), map);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}

	@Override
	public boolean insertarRecorrido(Recorrido recorrido) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("");
			sbQuery.append(" \n");
			sbQuery.append("INSERT INTO Recorridos VALUES(:latitud, :longitud, GETDATE(), :idRuta, :idCliente,:direccion,:tipo) \n");

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("latitud", recorrido.getLatitud());
			map.put("longitud", recorrido.getLongitud());
			map.put("idRuta", recorrido.getIdRuta());
			map.put("idCliente", recorrido.getIdCliente());
			map.put("direccion", recorrido.getDireccion());
			map.put("tipo", recorrido.getTipo());

			jdbcTemplate.update(sbQuery.toString(), map);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	@Override
	public List<String> obtenerFacturasFolio(String folio) {
		try {
			String squery = "select Folio from facturaElectronica where pk_factura in (select fk03_FacturaElectronica from ppackingList where fk01_packingList in (select pk_packingList from packingList where folio = :folio))";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("folio", folio);
			
			return jdbcTemplate.queryForList(squery.toString(), map ,String.class);
		}catch (Exception e) {
			e.printStackTrace();
			//throw new ProquifaNetException();
		}
		return null;
	}
	@Override
	public Map<String, Integer> totalesGeneral(Integer idResponsable) throws ProquifaNetException{
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("DECLARE @TABLA TABLE (Cliente varchar(300), Proveedor varchar(300), Evento varchar(100), Prioridad varchar(10), idMensajero int, Zona varchar(100)) \n");
			sbQuery.append("INSERT INTO @TABLA SELECT EM.Cliente, EM.Proveedor, EM.Evento, EM.Prioridad, ISNULL(EM.idMensajero,100), EM.Zona  FROM EventoMensajero EM WHERE EM.idResponsable = :idResponsable \n");
			sbQuery.append("DECLARE @TABLA_MENSAJERO TABLE (Etiqueta varchar(100), Cliente int, Proveedor int, Evento int, Mensajero int, Tipo varchar(500), Orden int) \n");
			sbQuery.append("INSERT INTO @TABLA_MENSAJERO SELECT Etiqueta, COUNT(Cliente) Cliente, COUNT(Proveedor) Proveedor, SUM(Evento) Evento, Clave Mensajero, Mensajero COLLATE Modern_Spanish_CI_AS Tipo, 0 FROM (  \n");
			sbQuery.append("SELECT 'Mensajero' Etiqueta, EM.Cliente, EM.Proveedor, COUNT(EM.Evento) Evento, E.Clave Clave, E.Nombre Mensajero \n");
			sbQuery.append("FROM @TABLA EM \n");
			sbQuery.append("LEFT JOIN Empleados E ON E.Clave = COALESCE(EM.idMensajero, 100) \n");
			sbQuery.append("GROUP BY EM.Cliente, E.Clave, E.Nombre, EM.Proveedor ) Mensajero \n");
			sbQuery.append("GROUP BY Etiqueta, Mensajero, Clave \n");
			sbQuery.append("SELECT 'AsignarRuta' Tipo , (SELECT COALESCE(COUNT(EM.Evento), 0) Total FROM @TABLA EM) 	Total \n");
			sbQuery.append("UNION ALL \n");
			sbQuery.append("SELECT 'CerrarRuta' Tipo, COALESCE(COUNT(CR1.idMensajero),0) Total FROM ( \n");
			sbQuery.append("SELECT CR.idMensajero, COUNT(idCliente) totalClientes, COUNT(CR.Zona) totalZonas, COUNT(CR.Ruta) totalRutas\n");
			sbQuery.append("FROM ( \n");
			sbQuery.append("SELECT CR.idMensajero, CR.Mensajero, CR.idCliente, CR.Zona, CR.Ruta \n");
			sbQuery.append("FROM CerrarRuta CR \n");
			sbQuery.append("GROUP BY CR.idMensajero, CR.Mensajero, CR.idCliente, CR.EstadoRuta, CR.Zona, CR.Ruta, CR.Entrega ) CR \n");
			sbQuery.append("GROUP BY CR.idMensajero, CR.Mensajero) CR1 \n");
			
			Map<String, Object> parametros = new HashMap<String, Object>();
			Map<String, Integer> mapReturn = new HashMap<String, Integer>();
			parametros.put("idResponsable", idResponsable);
			jdbcTemplate.query(sbQuery.toString(), parametros, new RowMapper<Object>() {
				@Override
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException{
					try {
						mapReturn.put(rs.getString("Tipo"), rs.getInt("Total"));
					} catch (Exception e) {
						// TODO: handle exception
					}
					return null;
				}
			});
			return mapReturn;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
}
