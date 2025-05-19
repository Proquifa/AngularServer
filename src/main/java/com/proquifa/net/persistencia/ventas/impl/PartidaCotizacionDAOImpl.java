package com.proquifa.net.persistencia.ventas.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.comun.TiempoProceso;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.ventas.PartidaCotizacion;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.comun.EmpleadoDAO;
import com.proquifa.net.persistencia.comun.FuncionDAO;
import com.proquifa.net.persistencia.ventas.PartidaCotizacionDAO;
import com.proquifa.net.persistencia.ventas.impl.mapper.ConsultaACRowMapper;
import com.proquifa.net.persistencia.ventas.impl.mapper.PartidaCotizacionConsultasMapper;
import com.proquifa.net.persistencia.ventas.impl.mapper.PartidaCotizacionResumenGraficaETyFTmapper;
import com.proquifa.net.persistencia.ventas.impl.mapper.PartidaCotizacionRowMapper;
import com.proquifa.net.persistencia.ventas.impl.mapper.PartidaCotizacionTiempoProcesoRowMapper;
import com.proquifa.net.persistencia.ventas.impl.mapper.tiempoProcesoRowMapper;

/**
 * @author ernestogonzalezlozada
 * 
 */
@Repository
public class PartidaCotizacionDAOImpl extends DataBaseDAO implements
		PartidaCotizacionDAO {
	Funcion f;
	
	final Logger log = LoggerFactory.getLogger(PartidaCotizacionDAOImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.com.proquifa.proquifanet.persistencia.ventas.PartidaCotizacionDAO#
	 * registrarPartidaCotizacion
	 * (mx.com.proquifa.proquifanet.modelo.ventas.PartidaCotizacion)
	 */
	@Autowired
	EmpleadoDAO empleadoDAO;
	@Autowired
	private FuncionDAO funcionDAO;
	private Funcion util = null;

	public Long registrarPartidaCotizacion(PartidaCotizacion partidaCotizacion) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("partidaCotizacion",partidaCotizacion);
		
			Object[] params = { partidaCotizacion.getCotizacion(),
					partidaCotizacion.getPartida(),
					partidaCotizacion.getCantidad(), partidaCotizacion.getCodigo(),
					partidaCotizacion.getProducto().getConcepto(),
					partidaCotizacion.getProducto().getFabrica(),
					partidaCotizacion.getProducto().getCantidad(),
					partidaCotizacion.getProducto().getUnidad(),
					partidaCotizacion.getProducto().getTiempoEntrega(),
					partidaCotizacion.getVariasMarcas() };
			String query = "INSERT INTO PCotizas (Clave,Partida,Cant,Codigo,Precio,Concepto,Estado,IVA,Costo,Fabrica,Nota,Clasif,Folio,ObservaE,Presentacion,Unidades,TEntrega,FS,PrecioI, variasMarcas) ";
			query += " VALUES (?, ?, ?, ?, 0, ?,'Cotización', 0, 0, ?, '','A', 0, '',? , ?, ?, 0, 0, ?) ";
			super.jdbcTemplate.update(query, map);
			Long idPCotiza = super.queryForLong("SELECT IDENT_CURRENT ('PCotizas')");
			return idPCotiza;
		}catch(Exception e){
			new Funcion().enviarCorreoAvisoExepcion(e);
			return 0L;
		}
		
	}

	public void actualizarPartidaCotizacion(PartidaCotizacion partidaCotizacion) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Codigo",partidaCotizacion.getCodigo());
			map.put("Concepto",partidaCotizacion.getConcepto());
			map.put("Presentacion",partidaCotizacion.getPresentacion());
			map.put("Unidades",partidaCotizacion.getUnidades());
			map.put("Fabrica",partidaCotizacion.getFabrica());
			map.put("VariasMarcas(",partidaCotizacion.getVariasMarcas() );
	
			
		
			Object[] params = { partidaCotizacion.getCodigo(),
					partidaCotizacion.getConcepto(),
					partidaCotizacion.getPresentacion(),
					partidaCotizacion.getUnidades(),
					partidaCotizacion.getFabrica(),
					partidaCotizacion.getVariasMarcas() };
			String query = "UPDATE pcotizas SET codigo = ?, concepto = ?, presentacion = ?, ";
			query += "unidades = ?, fabrica = ?, variasMarcas = ? WHERE idPCotiza = "
					+ partidaCotizacion.getIdPartidaCotizacion();
			super.jdbcTemplate.update(query, map);
		}catch(Exception e){
			new Funcion().enviarCorreoAvisoExepcion(e, partidaCotizacion);
		}
	}

	public void borrarPartidaCotizacion(PartidaCotizacion partidaCotizacion) {
		//logger.debug("idPCotiza :: " + partidaCotizacion.getIdPartidaCotizacion());
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("partidaCotizacion",partidaCotizacion);
			
			final String query = "DELETE FROM PCotizas WHERE idPCotiza= "
					+ partidaCotizacion.getIdPartidaCotizacion();
			super.jdbcTemplate.update(query, map);
		}catch(Exception e){
			new Funcion().enviarCorreoAvisoExepcion(e, partidaCotizacion);
		}
	}

	@SuppressWarnings("unchecked")	
	public List<PartidaCotizacion> obtenerPartidasCotizacion( String folioCotizacion, String tipo) {
		String queryString  = "";
		if(tipo.toLowerCase().equals("confirmacion")){
			queryString = "SELECT PCotizas.idPCotiza,PCotizas.Clave,PCotizas.Codigo,PCotizas.Fabrica,PCotizas.Cant,PCotizas.Precio,PCotizas.Partida,PCotizas.Concepto,(PCotizas.Cant*Pcotizas.Precio) "+
						  "AS 'Total','Confirmación' AS Estado FROM SegCotiza,Cotizas,PCotizas WHERE PCotizas.Clave = SegCotiza.Cotiza AND SegCotiza.Cotiza = Cotizas.Clave AND PCotizas.Folio = 99 AND "+
						  "PCotizas.Clave = '"+ folioCotizacion +"' AND FRealizo IS NULL ORDER BY SegCotiza.Fecha";
		}else{
			queryString = "SELECT PCotizas.idPCotiza,PCotizas.Clave,PCotizas.Codigo,PCotizas.Fabrica,PCotizas.Cant,PCotizas.Precio,PCotizas.Partida,PCotizas.Concepto,(PCotizas.Cant*Pcotizas.Precio) AS 'Total',"+
						  "PCotizaEnSeguimiento.Estado FROM PCotizas,PCotizaEnSeguimiento where PCotizas.idPCotiza = PCotizaEnSeguimiento.idPCotiza AND PCotizas.Folio = 99 AND PCotizas.Clave = '"+ folioCotizacion +"'";
		}
		//logger.debug(queryString);
		return super.jdbcTemplate.query(queryString, new ConsultaACRowMapper());
	}

	@SuppressWarnings("unchecked")
	public List<PartidaCotizacion> obtenPCotizasCeroONoventaYNueve(String cotizacion, Integer folio) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("folio",folio);
		map.put("cotizacion",cotizacion);
		
		Object[] params = { cotizacion, folio};
		String query  = "SELECT * FROM PCotizas WHERE Clave = ? AND Folio = ?";
		return super.jdbcTemplate.query(query, map, new PartidaCotizacionRowMapper()) ;
	}

	public String obtenerPCotizaClasificacionOrigen(String cotiza, String codigo, String fabrica){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("fabrica",fabrica);
			map.put("codigo",codigo);
			map.put("cotiza",cotiza);
			
			String query = "SELECT Clasif FROM PCotizas WHERE Clave IN (SELECT Cotiza FROM PCotPharma WHERE Codigo = '"+ codigo +"' AND Fabrica = '"+ fabrica +"'"+
						   "AND Cotiza = '"+ cotiza +"') AND Partida IN (SELECT Part FROM PCotPharma WHERE Codigo = '"+ codigo +"' AND Fabrica = '"+ fabrica +"'"+
						   "AND Cotiza = '"+ cotiza +"') AND Folio = 0";
			return (String) super.jdbcTemplate.queryForObject(query, map,String.class);
		}catch (RuntimeException e){
			return null;
		}		
	}

	@SuppressWarnings("unchecked")
	public List<TiempoProceso> obtenTiempoProcesoPCotiza(PartidaCotizacion pcotiza, Boolean completo) {
		String query = "";
		util = new Funcion();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pcotiza",pcotiza);
			map.put("completo",completo);
			
		if(completo){
				query =	" \n SELECT 'Registro' AS Proceso,COALESCE(dr.FHOrigen,dr.Fecha) AS FechaInicio,dr.Fecha AS FechaFin,dr.Medio,c.Nombre AS ClienteProveedor,COALESCE((con.Titulo +' '+ con.Contacto),'No disponible') AS Contacto,dr.Ingreso AS Responsable,"+
						" \n CAST(dr.Folio AS Varchar(10)) AS Referencia," +
						" \n (CASE WHEN pcot.Folio=0 THEN pcot.Clasif ELSE CASE WHEN pcot.Clasif='A' THEN (SELECT Clasif FROM PCotizas WHERE Codigo='" + pcotiza.getCodigo() + " ' AND Fabrica='" + pcotiza.getFabrica() + "' AND Clave='" + pcotiza.getCotizacion() + "' AND Folio = 0 AND idPCotiza = "+ pcotiza.getIdPartidaCotizacion() +") ELSE "+
						" \n (SELECT Clasif FROM PCotizas WHERE Clave IN (SELECT Cotiza FROM PCotPharma WHERE Codigo = '" + pcotiza.getCodigo() + "' AND Fabrica = '" + pcotiza.getFabrica() +"' AND Cotiza = '" + pcotiza.getCotizacion() + "') AND Partida IN (SELECT top 1 Part FROM PCotPharma WHERE Codigo = '" + pcotiza.getCodigo() + "' "+
						" \n AND Fabrica = '" + pcotiza.getFabrica()+ "' AND Cotiza = '" + pcotiza.getCotizacion() + "') AND Folio = 0) END END) AS ClasificacionInicial," +
						" \n 'No Aplica' AS ClasificacionFinal,'No Aplica' AS TProvee,'No Aplica' AS Tipo,'No Aplica' AS Catalogo,'No Aplica' AS Concepto,"+
						" \n 'No Aplica' AS Presentacion,'No Aplica' AS Marca,'0' AS Precio,'No Aplica' AS Disponibilidad,'No Aplica' AS Manejo,'No Aplica' AS TEntrega,'No Aplica' AS CargosEnviosAdicionales,'No Aplica' AS HieloSeco,"+
						" \n 'No Aplica' AS ComentariosAdicionales,'No Aplica' AS RechazoESAC,'No Aplica' AS RechazoPharma,'No Aplica' AS EnSTOCK,'No Aplica' AS UltimaEdicion,'No Aplica' AS CostosAdicionales,"+
						util.obtenerTiempoTranscurridoSQL("COALESCE(dr.FHOrigen,dr.Fecha)", "dr.Fecha", "Dias") + "  AS TT,'' AS SituacionPedido "+
						" \n FROM DoctosR AS dr "+
						" \n LEFT JOIN(SELECT Clave,Nombre FROM Clientes) AS c ON c.Clave=dr.Empresa "+
						" \n LEFT JOIN(SELECT * FROM PCotizas)AS pcot ON pcot.idPCotiza = '"+pcotiza.getIdPartidaCotizacion() +"' "+
						" \n LEFT JOIN(SELECT * FROM Cotizas) AS coti ON coti.Clave=pcot.Clave " +
						" \n LEFT JOIN(SELECT idContacto,Titulo,Contacto FROM Contactos) AS con ON con.idContacto=coti.idContacto " +
						" \n WHERE dr.Numero='"+ pcotiza.getCotizacion() +"' "+
						" \n UNION ALL "+
						" \n SELECT 'Clasificación' AS Proceso,dr.Fecha AS FechaInicio,c.FechaClasif AS FechaFin,'No Aplica' AS Medio,'No Aplica' AS ClienteProveedor,(cont.Titulo +' '+ cont.Contacto) AS Contacto,c.Vendedor AS Responsable,c.Clave AS Referencia,"+
						" \n (CASE WHEN pcot.Folio=0 THEN pcot.Clasif ELSE CASE WHEN pcot.Clasif='A' THEN (SELECT Clasif FROM PCotizas WHERE Codigo='" + pcotiza.getCodigo() + " ' AND Fabrica='" + pcotiza.getFabrica() + "' AND Clave='" + pcotiza.getCotizacion() + "' AND Folio = 0 AND idPCotiza = "+ pcotiza.getIdPartidaCotizacion() +") ELSE "+
						" \n (SELECT Clasif FROM PCotizas WHERE Clave IN (SELECT Cotiza FROM PCotPharma WHERE Codigo = '" + pcotiza.getCodigo() + "' AND Fabrica = '" + pcotiza.getFabrica() +"' AND Cotiza = '" + pcotiza.getCotizacion() + "') AND Partida IN (SELECT top 1 Part FROM PCotPharma WHERE Codigo = '" + pcotiza.getCodigo() + "' "+
						" \n AND Fabrica = '" + pcotiza.getFabrica()+ "' AND Cotiza = '" + pcotiza.getCotizacion() + "') AND Folio = 0) END END) AS ClasificacionInicial," +
						" \n (CASE WHEN pcot.Folio=0 THEN CASE WHEN (pcot.Destino='Gestionando' OR pcotPHS.Estado='Por Gestionar') THEN 'D' ELSE 'Pendiente' END ELSE pcot.Clasif END) AS ClasificacionFinal,'No Aplica' AS TProvee,'No Aplica' AS Tipo,"+
						" \n 'No Aplica' AS Catalogo,'No Aplica' AS Concepto,'No Aplica' AS Presentacion,'No Aplica' AS Marca,'0' AS Precio,'No Aplica' AS Disponibilidad,'No Aplica' AS Manejo,'No Aplica' AS TEntrega,"+
						" \n 'No Aplica' AS CargosEnviosAdicionales,'No Aplica' AS HieloSeco,'No Aplica' AS ComentariosAdicionales,'No Aplica' AS RechazoESAC,'No Aplica' AS RechazoPharma,'No Aplica' AS EnSTOCK,pcotPHS.RE AS UltimaEdicion,"+
						" \n 'No Aplica' AS CostosAdicionales," + util.obtenerTiempoTranscurridoSQL("dr.Fecha", "c.FechaClasif", "Dias") + "  AS TT,'' AS SituacionPedido "+
						" \n FROM PCotizas AS pcot "+
						" \n LEFT JOIN(SELECT * FROM Cotizas) AS c ON c.Clave=pcot.Clave "+
						" \n LEFT JOIN(SELECT * FROM Contactos) AS cont ON cont.idContacto=c.idContacto "+
						" \n LEFT JOIN(SELECT * FROM DoctosR) AS dr ON dr.Numero=pcot.Clave "+
						" \n LEFT JOIN(SELECT pcot.RE,p.idPCotiza,pcot.FInicio,pcot.FInvestiga,pcot.FValida,pcot.FFin,pcot.Observa,pcot.Estado FROM PCotPharma AS pcot,PCotizas AS p WHERE pcot.Cotiza=p.Clave AND "+
						" \n pcot.Part=CASE WHEN p.Folio=0 THEN (SELECT top 1 Part FROM PCotPharma WHERE Cotiza=p.Clave AND Part=p.Partida) ELSE (SELECT top 1 Part FROM PCotPharma WHERE Codigo=p.Codigo AND Fabrica=p.Fabrica AND Cotiza=p.Clave) END) AS pcotPHS ON pcotPHS.idPCotiza=pcot.idPCotiza " +
						" \n WHERE pcot.idPCotiza="+ pcotiza.getIdPartidaCotizacion() + " "+
						" \n UNION ALL " +
						" \n SELECT 'Investigación' AS Proceso,pcotPHS.FInicio AS FechaInicio,pcotPHS.FInvestiga AS FechaFin,'No Aplica' AS Medio,CASE WHEN (CASE WHEN pcotPHS.TProvee='R' THEN prove.Nombre ELSE proveT.Nombre END) IS NULL AND pcotPHS.FInvestiga IS NULL THEN 'Pendiente' ELSE (CASE WHEN pcotPHS.TProvee='R' THEN prove.Nombre ELSE proveT.Nombre END)END AS ClienteProveedor,'No Aplica' AS Contacto,"+
						" \n cot.Vendedor AS Responsable,'No Aplica' AS Referencia,(CASE WHEN pc.Folio=0 THEN pc.Clasif ELSE CASE WHEN pc.Clasif='A' THEN (SELECT Clasif FROM PCotizas WHERE Codigo= '"+ pcotiza.getCodigo() + "' AND Fabrica= '"+ pcotiza.getFabrica() +"' AND Clave= '"+ pcotiza.getCotizacion() + "' AND Folio = 0 AND idPCotiza = "+ pcotiza.getIdPartidaCotizacion() +") ELSE " +
						" \n  (SELECT Clasif FROM PCotizas WHERE Clave IN (SELECT Cotiza FROM PCotPharma WHERE Codigo = '"+ pcotiza.getCodigo() + "' AND Fabrica = '"+ pcotiza.getFabrica() +"' AND Cotiza =  '"+ pcotiza.getCotizacion() +"') AND Partida IN (SELECT top 1 Part FROM PCotPharma WHERE Codigo = '"+ pcotiza.getCodigo() + "'	AND Fabrica = '"+ pcotiza.getFabrica() +"' AND Cotiza =  '"+ pcotiza.getCotizacion() +"') AND Folio = 0) END END) AS ClasificacionInicial," +
						" \n (CASE WHEN pc.Folio=0 THEN CASE WHEN (pc.Destino='Gestionando' OR pcotPHS.Estado='Por Gestionar') THEN 'D' ELSE 'Pendiente' END ELSE pc.Clasif END) AS ClasificacionFinal," +
						" \n pcotPHS.TProvee, CASE WHEN pcotPHS.Tipo IS NULL OR pcotPHS.Tipo = '' OR pcotPHS.Tipo = 'ND' THEN CASE WHEN pcotPHS.FInvestiga IS NULL THEN 'Pendiente' ELSE 'ND' END ELSE pcotPHS.Tipo END AS Tipo,CASE WHEN pcotPHS.Codigo IS NULL OR pcotPHS.Codigo = '' THEN CASE WHEN pcotPHS.FInvestiga IS NULL THEN 'Pendiente' ELSE 'ND' END ELSE pcotPHS.Codigo END AS Catalogo, " +
						" \n CASE WHEN pcotPHS.Concepto IS NULL OR DATALENGTH(pcotPHS.Concepto)=0 THEN CASE WHEN pcotPHS.FInvestiga IS NULL THEN 'Pendiente' ELSE 'ND' END ELSE pcotPHS.Concepto END AS Concepto," +
						" \n CASE WHEN pcotPHS.Presentacion IS NULL OR pcotPHS.Presentacion = '' THEN CASE WHEN pcotPHS.FInvestiga IS NULL THEN 'Pendiente' ELSE 'ND' END ELSE pcotPHS.Presentacion END AS Presentacion,CASE WHEN pcotPHS.Fabrica IS NULL OR pcotPHS.Fabrica = '' THEN CASE WHEN pcotPHS.FInvestiga IS NULL THEN 'Pendiente' ELSE 'ND' END ELSE pcotPHS.Fabrica END AS Marca,"+
						" \n COALESCE(pcotPHS.PrecioI,'0') AS Precio,CASE WHEN pcotPHS.ST IS NULL OR pcotPHS.ST = '' THEN CASE WHEN pcotPHS.FInvestiga IS NULL THEN 'Pendiente' ELSE 'ND' END ELSE pcotPHS.ST END AS Disponibilidad,CASE WHEN pcotPHS.Manejo IS NULL OR pcotPHS.Manejo = '' THEN CASE WHEN pcotPHS.FInvestiga IS NULL THEN 'Pendiente' ELSE 'ND' END ELSE pcotPHS.Manejo END AS Manejo," +
						" \n CASE WHEN pcotPHS.DT IS NULL OR pcotPHS.DT = '' THEN CASE WHEN pcotPHS.FInvestiga IS NULL THEN 'Pendiente' ELSE 'ND' END ELSE pcotPHS.DT END AS TEntrega, CASE WHEN pcotPHS.SC IS NULL OR DATALENGTH(pcotPHS.SC)=0 THEN CASE WHEN pcotPHS.FInvestiga IS NULL THEN 'Pendiente' ELSE 'ND' END ELSE pcotPHS.SC END AS CargosEnviosAdicionales,CASE WHEN pcotPHS.IC IS NULL OR pcotPHS.IC = '' THEN CASE WHEN pcotPHS.FInvestiga IS NULL THEN 'Pendiente' ELSE 'ND' END ELSE pcotPHS.IC END AS HieloSeco," +
						" \n CASE WHEN pcotPHS.AC IS NULL OR pcotPHS.AC = '' THEN CASE WHEN pcotPHS.FInvestiga IS NULL THEN 'Pendiente' ELSE 'ND' END ELSE pcotPHS.AC END AS ComentariosAdicionales,'No Aplica' AS RechazoESAC,"+
						" \n 'No Aplica' AS RechazoPharma,'No Aplica' AS EnSTOCK, CASE WHEN pcotPHS.RE IS NULL OR pcotPHS.RE = '' THEN CASE WHEN pcotPHS.FInvestiga IS NULL THEN 'Pendiente' ELSE 'ND' END ELSE pcotPHS.RE END AS UltimaEdicion,'No Aplica' AS CostosAdicionales," + util.obtenerTiempoTranscurridoSQL("pcotPHS.FInicio", "pcotPHS.FInvestiga", "Dias") + " AS TT,'' AS SituacionPedido "+
						" \n FROM PCotizas AS pc "+
						" \n LEFT JOIN(SELECT pcot.Estado,pcot.RE,p.idPCotiza,pcot.FInicio,pcot.FInvestiga,pcot.Proveedor,pcot.TProvee,pcot.Codigo,pcot.Fabrica,pcot.Tipo,pcot.Presentacion,pcot.PrecioI,pcot.Concepto,pcot.SC,pcot.IC,pcot.AC,pcot.ST,pcot.Manejo,pcot.DT FROM PCotPharma AS pcot,PCotizas AS p "+
						" \n WHERE pcot.Cotiza=p.Clave AND pcot.Part=CASE WHEN p.Folio=0 THEN (SELECT top 1 Part FROM PCotPharma WHERE Cotiza=p.Clave AND Part=p.Partida) ELSE (SELECT top 1 Part FROM PCotPharma WHERE Codigo=p.Codigo AND Fabrica=p.Fabrica AND Cotiza=p.Clave) END) AS pcotPHS "+
						" \n ON pcotPHS.idPCotiza=pc.idPCotiza "+
						" \n LEFT JOIN(SELECT Clave,Vendedor FROM Cotizas) AS cot ON cot.Clave=pc.Clave "+
						" \n LEFT JOIN(SELECT Clave,Nombre FROM Proveedores) AS prove ON prove.Clave=pcotPHS.Proveedor "+
						" \n LEFT JOIN(SELECT Clave,Nombre FROM ProveedorTemp) AS proveT ON proveT.Clave=pcotPHS.Proveedor "+
						" \n WHERE pc.idPCotiza=" + pcotiza.getIdPartidaCotizacion() + " " +
						" \n UNION ALL ";
						if(pcotiza.getClasifOrigen() != null && pcotiza.getClasifOrigen().equals("B")){
								query += "SELECT 'Confirmar Datos PHS' AS Proceso,pcotPHS.FInvestiga AS Finicio,pcotPHS.FPharma AS FechaFin,'No Aplica' AS Medio,'No Aplica' AS ClienteProveedor,'No Aplica' AS Contacto,'Comphs-USA' AS Responsable,'No Aplica' AS Referencia,'No Aplica' AS ClasificacionInicial,"+
										 "'No Aplica' AS ClasificacionFinal,'No Aplica' AS TProvee,pcotPHS.Tipo,'No Aplica' AS Catalogo,'No Aplica' AS Concepto,'No Aplica' AS Presentacion,'No Aplica' AS Marca,COALESCE(pcotPHS.Precio,'0') AS Precio,'No Aplica' AS Disponibilidad,'No Aplica' AS Manejo,"+
										 "CASE WHEN pcotPHS.DT IS NULL OR pcotPHS.DT = '' THEN CASE WHEN pcotPHS.FPharma IS NULL THEN 'Pendiente' ELSE 'ND' END ELSE pcotPHS.DT END AS TEntrega,'No Aplica' AS CargosEnviosAdicionales,CASE WHEN pcotPHS.IC IS NULL OR pcotPHS.IC = '' THEN CASE WHEN pcotPHS.FPharma IS NULL THEN 'Pendiente' ELSE 'ND' END ELSE pcotPHS.IC END AS HieloSeco," +
										 "CASE WHEN pcotPHS.AC IS NULL OR DATALENGTH(pcotPHS.AC) = 0 THEN CASE WHEN pcotPHS.FPharma IS NULL THEN 'Pendiente' ELSE 'ND' END ELSE pcotPHS.AC END AS ComentariosAdicionales,'No Aplica' AS RechazoESAC,CASE WHEN pcotPHS.Rechazo IS NULL OR pcotPHS.Rechazo = '' THEN CASE WHEN pcotPHS.FPharma IS NULL THEN 'Pendiente' ELSE 'ND' END ELSE pcotPHS.Rechazo END AS RechazoPharma,CASE WHEN pcotPHS.ST IS NULL OR pcotPHS.ST = '' THEN CASE WHEN pcotPHS.FPharma IS NULL THEN 'Pendiente' ELSE 'ND' END ELSE pcotPHS.ST END AS EnSTOCK," +
										 "CASE WHEN pcotPHS.RE IS NULL OR pcotPHS.RE = '' THEN CASE WHEN pcotPHS.FPharma IS NULL THEN 'Pendiente' ELSE 'ND' END ELSE pcotPHS.RE END AS UltimaEdicion,CASE WHEN pcotPHS.SC IS NULL OR DATALENGTH(pcotPHS.SC) = 0 THEN CASE WHEN pcotPHS.FPharma IS NULL THEN 'Pendiente' ELSE 'ND' END ELSE pcotPHS.SC END AS CostosAdicionales," + 
										 util.obtenerTiempoTranscurridoSQL("pcotPHS.FInvestiga", "pcotPHS.FPharma", "Dias") + " AS TT,'' AS SituacionPedido "+
										 "FROM PCotizas AS pc "+
										 "LEFT JOIN(SELECT pcot.Tipo,p.idPCotiza,pcot.FInvestiga,pcot.FPharma,pcot.Precio,pcot.Codigo,pcot.Fabrica,pcot.DT,pcot.ST,pcot.SC,pcot.IC,pcot.RE,pcot.AC,pcot.Rechazo FROM PCotPharma AS pcot,PCotizas AS p WHERE pcot.Cotiza=p.Clave AND pcot.Part=CASE WHEN p.Folio=0 THEN "+
										 "(SELECT top 1 Part FROM PCotPharma WHERE Cotiza=p.Clave AND Part=p.Partida) ELSE (SELECT top 1 Part FROM PCotPharma WHERE Codigo=p.Codigo AND Fabrica=p.Fabrica AND Cotiza=p.Clave) END) AS pcotPHS ON pcotPHS.idPCotiza=pc.idPCotiza "+
										 "LEFT JOIN(SELECT Codigo,Fabrica,TEntrega FROM Productos) AS prod ON prod.Codigo=pcotPHS.Codigo AND prod.Fabrica=pcotPHS.Fabrica "+
										 "WHERE pc.idPCotiza=" + pcotiza.getIdPartidaCotizacion() + " "+
										 "UNION ALL " +
										 "SELECT 'Evaluar respuesta' AS Proceso,pcotPHS.FPharma AS FechaInicio,pcotPHS.FValida AS FechaFin,'No Aplica' AS Medio,CASE WHEN (CASE WHEN pcotPHS.TProvee='R' THEN prove.Nombre ELSE proveT.Nombre END) IS NULL AND pcotPHS.FValida IS NULL THEN 'Pendiente' ELSE (CASE WHEN pcotPHS.TProvee='R' THEN prove.Nombre ELSE proveT.Nombre END) END AS ClienteProveedor,'No Aplica' AS Contacto,cot.Vendedor AS Responsable,"+
										 "'No Aplica' AS Referencia,pcotPHS.Clasif AS ClasificacionInicial,'No Aplica' AS ClasificacionFinal,pcotPHS.TProvee, CASE WHEN pcotPHS.Tipo IS NULL OR pcotPHS.Tipo = '' OR pcotPHS.Tipo = 'ND' THEN CASE WHEN pcotPHS.FValida IS NULL THEN 'Pendiente' ELSE 'ND' END ELSE pcotPHS.Tipo END AS Tipo,pcotPHS.Codigo AS Catalogo,pcotPHS.Concepto AS Concepto,pcotPHS.Presentacion,pcotPHS.Fabrica AS Marca,COALESCE(pcotPHS.Precio,'0') AS Precio, "+
										 "CASE WHEN pcotPHS.ST IS NULL OR pcotPHS.ST = '' OR pcotPHS.ST = 'ND' THEN CASE WHEN pcotPHS.FValida IS NULL THEN 'Pendiente' ELSE 'ND' END ELSE pcotPHS.ST END AS Disponibilidad,CASE WHEN pcotPHS.Manejo IS NULL OR pcotPHS.Manejo = '' THEN CASE WHEN  pcotPHS.FValida IS NULL THEN 'Pendiente' ELSE 'ND' END ELSE pcotPHS.Manejo END AS Manejo,CASE WHEN pcotPHS.DT IS NULL OR pcotPHS.DT = '' OR pcotPHS.DT = 'ND' THEN CASE WHEN pcotPHS.FValida IS NULL THEN 'Pendiente' ELSE 'ND' END ELSE pcotPHS.DT END AS TEntrega," +
										 "CASE WHEN pcotPHS.AC IS NULL OR pcotPHS.AC = '' THEN CASE WHEN pcotPHS.FValida IS NULL THEN 'Pendiente' ELSE 'ND' END ELSE pcotPHS.AC END AS CargosEnviosAdicionales,CASE WHEN pcotPHS.IC IS NULL OR pcotPHS.IC = '' THEN CASE WHEN pcotPHS.FValida IS NULL THEN 'Pendiente' ELSE 'ND' END ELSE pcotPHS.IC END AS HieloSeco,CASE WHEN pcotPHS.Observa IS  NULL OR pcotPHS.Observa = '' THEN CASE WHEN pcotPHS.FValida IS NULL THEN  'Pendiente' ELSE 'ND' END ELSE pcotPHS.Observa END AS ComentariosAdicionales," +
										 "CASE WHEN pcotPHS.ObservaP IS NULL OR pcotPHS.ObservaP = '' THEN CASE WHEN pcotPHS.FValida IS NULL THEN 'Pendiente' ELSE 'ND' END ELSE pcotPHS.ObservaP END AS RechazoESAC,CASE WHEN pcotPHS.Rechazo IS NULL OR pcotPHS.Rechazo = '' THEN CASE WHEN pcotPHS.FValida IS NULL THEN 'Pendiente' ELSE 'ND' END ELSE pcotPHS.Rechazo END AS RechazoPharma," +
										 "'No Aplica' AS EnSTOCK,"+
										 "CASE WHEN pcotPHS.RE IS NULL OR pcotPHS.RE = '' OR pcotPHS.RE = 'ND' THEN CASE WHEN pcotPHS.FValida IS NULL THEN 'Pendiente' ELSE 'ND' END ELSE pcotPHS.RE END AS UltimaEdicion,'No Aplica' AS CostosAdicionales," + util.obtenerTiempoTranscurridoSQL("pcotPHS.FPharma", "pcotPHS.FValida", "Dias") + "  AS TT,COT.IMoneda AS SituacionPedido "+
										 "FROM PCotizas AS pc "+
										 "LEFT JOIN(SELECT pcot.Tipo,pcot.RE,p.idPCotiza,pcot.FPharma,pcot.FValida,pcot.Clasif,pcot.Observa,pcot.Proveedor,pcot.TProvee,pcot.Codigo,pcot.Concepto,pcot.Precio,pcot.Fabrica,pcot.Presentacion,pcot.DT,pcot.ST,pcot.AC,pcot.Manejo,pcot.IC,pcot.Rechazo,pcot.ObservaP "+
										 "FROM PCotPharma AS pcot,PCotizas AS p WHERE pcot.Cotiza=p.Clave AND pcot.Part=CASE WHEN p.Folio=0 THEN (SELECT top 1 Part FROM PCotPharma WHERE Cotiza=p.Clave AND Part=p.Partida) ELSE (SELECT top 1 Part FROM PCotPharma WHERE Codigo=p.Codigo AND Fabrica=p.Fabrica AND Cotiza=p.Clave) END) "+
										 "AS pcotPHS ON pcotPHS.idPCotiza=pc.idPCotiza "+
										 "LEFT JOIN(SELECT Clave,Vendedor,IMoneda FROM Cotizas) AS cot ON cot.Clave=pc.Clave "+
										 "LEFT JOIN(SELECT Clave,Nombre FROM Proveedores) AS prove ON prove.Clave=pcotPHS.Proveedor "+
										 "LEFT JOIN(SELECT Clave,Nombre FROM ProveedorTemp) AS proveT ON proveT.Clave=pcotPHS.Proveedor "+
										 "WHERE pc.idPCotiza=" + pcotiza.getIdPartidaCotizacion() + " "+
										 "UNION ALL ";
						}else{
							query += " \nSELECT 'Evaluar respuesta' AS Proceso,pcotPHS.FInvestiga AS FechaInicio,pcotPHS.FValida AS FechaFin,'No Aplica' AS Medio,CASE WHEN (CASE WHEN pcotPHS.TProvee='R' THEN prove.Nombre ELSE proveT.Nombre END) IS NULL AND pcotPHS.FValida IS NULL THEN 'Pendiente' ELSE (CASE WHEN pcotPHS.TProvee='R' THEN prove.Nombre ELSE proveT.Nombre END) END AS ClienteProveedor,'No Aplica' AS Contacto,cot.Vendedor AS Responsable,"+
									 " \n'No Aplica' AS Referencia,pcotPHS.Clasif AS ClasificacionInicial,'No Aplica' AS ClasificacionFinal,pcotPHS.TProvee,CASE WHEN pcotPHS.Tipo IS NULL OR pcotPHS.Tipo = '' OR pcotPHS.Tipo = 'ND' THEN CASE WHEN pcotPHS.FValida IS NULL THEN 'Pendiente' ELSE 'ND' END ELSE pcotPHS.Tipo END AS Tipo,pcotPHS.Codigo AS Catalogo,pcotPHS.Concepto AS Concepto,pcotPHS.Presentacion,pcotPHS.Fabrica AS Marca,COALESCE(pcotPHS.Precio,'0') AS Precio, "+
									 " \n CASE WHEN pcotPHS.ST IS NULL OR pcotPHS.ST = '' OR pcotPHS.ST = 'ND' THEN CASE WHEN pcotPHS.FValida IS NULL THEN 'Pendiente' ELSE 'ND' END ELSE pcotPHS.ST END AS Disponibilidad,CASE WHEN pcotPHS.Manejo IS NULL OR pcotPHS.Manejo = '' THEN CASE WHEN  pcotPHS.FValida IS NULL THEN 'Pendiente' ELSE 'ND' END ELSE pcotPHS.Manejo END AS Manejo,CASE WHEN pcotPHS.DT IS NULL OR pcotPHS.DT = '' OR pcotPHS.DT = 'ND' THEN CASE WHEN pcotPHS.FValida IS NULL THEN 'Pendiente' ELSE 'ND' END ELSE pcotPHS.DT END AS TEntrega," +
									 " \n CASE WHEN pcotPHS.AC IS NULL OR pcotPHS.AC = '' THEN CASE WHEN pcotPHS.FValida IS NULL THEN 'Pendiente' ELSE 'ND' END ELSE pcotPHS.AC END AS CargosEnviosAdicionales,CASE WHEN pcotPHS.IC IS NULL OR pcotPHS.IC = '' THEN CASE WHEN pcotPHS.FValida IS NULL THEN 'Pendiente' ELSE 'ND' END ELSE pcotPHS.IC END AS HieloSeco,CASE WHEN pcotPHS.Observa IS  NULL OR pcotPHS.Observa = '' THEN CASE WHEN pcotPHS.FValida IS NULL THEN  'Pendiente' ELSE 'ND' END ELSE pcotPHS.Observa END AS ComentariosAdicionales," +
									 " \n CASE WHEN pcotPHS.ObservaP IS NULL OR pcotPHS.ObservaP = '' THEN CASE WHEN pcotPHS.FValida IS NULL THEN 'Pendiente' ELSE 'ND' END ELSE pcotPHS.ObservaP END AS RechazoESAC,CASE WHEN pcotPHS.Rechazo IS NULL OR pcotPHS.Rechazo = '' THEN CASE WHEN pcotPHS.FValida IS NULL THEN 'Pendiente' ELSE 'ND' END ELSE pcotPHS.Rechazo END AS RechazoPharma," +
									 " \n 'No Aplica' AS EnSTOCK,"+
									 " \n CASE WHEN pcotPHS.RE IS NULL OR pcotPHS.RE = '' OR pcotPHS.RE = 'ND' THEN CASE WHEN pcotPHS.FValida IS NULL THEN 'Pendiente' ELSE 'ND' END ELSE pcotPHS.RE END AS UltimaEdicion,'No Aplica' AS CostosAdicionales," + util.obtenerTiempoTranscurridoSQL("pcotPHS.FInvestiga", "pcotPHS.FValida", "Dias") + "  AS T,COT.IMoneda AS SituacionPedido "+
									 " \n FROM PCotizas AS pc "+
									 " \n LEFT JOIN(SELECT pcot.Tipo,pcot.RE,p.idPCotiza,pcot.FInvestiga,pcot.FValida,pcot.Clasif,pcot.Observa,pcot.Proveedor,pcot.TProvee,pcot.Codigo,pcot.Concepto,pcot.Precio,pcot.Fabrica,pcot.Presentacion,pcot.DT,pcot.ST,pcot.AC,pcot.Manejo,pcot.IC,pcot.Rechazo,pcot.ObservaP "+
									 " \n FROM PCotPharma AS pcot,PCotizas AS p WHERE pcot.Cotiza=p.Clave AND pcot.Part=CASE WHEN p.Folio=0 THEN (SELECT top 1 Part FROM PCotPharma WHERE Cotiza=p.Clave AND Part=p.Partida) ELSE (SELECT top 1 Part FROM PCotPharma WHERE Codigo=p.Codigo AND Fabrica=p.Fabrica AND Cotiza=p.Clave) END) "+
									 " \n AS pcotPHS ON pcotPHS.idPCotiza=pc.idPCotiza "+
									 "\n LEFT JOIN(SELECT Clave,Vendedor,IMoneda FROM Cotizas) AS cot ON cot.Clave=pc.Clave "+
									 " \n LEFT JOIN(SELECT Clave,Nombre FROM Proveedores) AS prove ON prove.Clave=pcotPHS.Proveedor "+
									 " \n LEFT JOIN(SELECT Clave,Nombre FROM ProveedorTemp) AS proveT ON proveT.Clave=pcotPHS.Proveedor "+
									 " \n WHERE pc.idPCotiza=" + pcotiza.getIdPartidaCotizacion() + " "+
									 " \n UNION ALL ";
						}
						query += " \n SELECT 'Validación pDp' AS Proceso,"+
								" \n (CASE WHEN pcotPHS.FInvestiga IS NULL AND pcotPHS.FValida IS NULL THEN pcotPHS.FInicio ELSE CASE WHEN pcotPHS.FValida IS NULL THEN pcotPHS.FInvestiga ELSE pcotPHS.FValida END END ) AS FechaInicio ,"+
								" \n pcotPHS.FFin AS FechaFin,'No Aplica' AS Medio,'No Aplica' AS ClienteProveedor,'No Aplica' AS Contacto,c.Vendedor AS Responsable,'No Aplica' AS Referencia,'No Aplica' AS ClasificacionInicial,'No Aplica' AS ClasificacionFinal,'No Aplica' AS TProvee,"+
								" \n 'No Aplica' AS Tipo,'No Aplica' AS Catalogo,'No Aplica' AS Concepto,'No Aplica' AS Presentacion,'No Aplica' AS Marca,'0' AS Precio,'No Aplica' AS Disponibilidad,'No Aplica' AS Manejo,'No Aplica' AS TEntrega,'No Aplica' AS CargosEnviosAdicionales,"+
								" \n 'No Aplica' AS HieloSeco,'No Aplica' AS ComentariosAdicionales,'No Aplica' AS RechazoESAC,pcotPHS.Observa AS RechazoPharma,'No Aplica' AS EnSTOCK,pcotPHS.RE AS UltimaEdicion,'No Aplica' AS CostosAdicionales,"+
								 util.obtenerTiempoTranscurridoSQL("(CASE WHEN pcotPHS.FInvestiga IS NULL AND pcotPHS.FValida IS NULL THEN pcotPHS.FInicio ELSE CASE WHEN pcotPHS.FValida IS NULL THEN pcotPHS.FInvestiga ELSE "+
							    " \n pcotPHS.FValida END END ) ","pcotPHS.FFin", "Dias") + "  AS TT,'' AS SituacionPedido "+
							    " \n FROM PCotizas AS pc "+
							    " \n LEFT JOIN(SELECT Clave,Vendedor FROM Cotizas) AS c ON c.Clave=pc.Clave "+
							    " \n LEFT JOIN(SELECT p.idPCotiza,pcot.FInicio,pcot.FInvestiga,pcot.FValida,pcot.FFin,pcot.Observa,pcot.RE FROM PCotPharma AS pcot,PCotizas AS p WHERE pcot.Cotiza=p.Clave AND "+
							    " \n pcot.Part=CASE WHEN p.Folio=0 THEN (SELECT top 1 Part FROM PCotPharma WHERE Cotiza=p.Clave AND Part=p.Partida) ELSE (SELECT top 1 Part FROM PCotPharma WHERE Codigo=p.Codigo AND Fabrica=p.Fabrica AND Cotiza=p.Clave) END) AS pcotPHS ON pcotPHS.idPCotiza=pc.idPCotiza "+
							    " \n WHERE pc.idPCotiza=" + pcotiza.getIdPartidaCotizacion() + " "+
							    " \n UNION ALL "+
							    " \n SELECT 'Ingresó en catálogo' AS Proceso,pcotPHS.FValida AS FechaInicio,pcotPHS.FFin AS FechaFin,'No Aplica' AS Medio,'No Aplica' AS ClienteProveedor,'No Aplica' AS Contacto,'"+ this.funcionDAO.getEmpleadoXIdFuncion(27L) +"' AS Responsable,'No Aplica' AS Referencia,'No Aplica' AS ClasificacionInicial,"+
							    " \n 'No Aplica' AS ClasificacionFinal,'No Aplica' AS TProvee,'No Aplica' AS Tipo,'No Aplica' AS Catalogo,'No Aplica' AS Concepto,'No Aplica' AS Presentacion,'No Aplica' AS Marca,'0' AS Precio,'No Aplica' AS Disponibilidad,'No Aplica' AS Manejo,"+
							    " \n 'No Aplica' AS TEntrega,'No Aplica' AS CargosEnviosAdicionales,'No Aplica' AS HieloSeco,'No Aplica' AS ComentariosAdicionales,'No Aplica' AS RechazoESAC,'No Aplica' AS RechazoPharma,'No Aplica' AS EnSTOCK,'No Aplica' AS UltimaEdicion,'No Aplica' AS CostosAdicionales,"+
								 util.obtenerTiempoTranscurridoSQL("pcotPHS.FValida", "pcotPHS.FFin", "Dias") + "  AS TT,'' AS SituacionPedido " +
								 " \n FROM PCotizas AS pc "+
								 " \n LEFT JOIN(SELECT p.idPCotiza,pcoT.FValida,pcoT.FFin,pcoT.Clasif FROM PCotPharma AS pcot,PCotizas AS p WHERE pcot.Cotiza=p.Clave AND pcot.Part=CASE WHEN p.Folio=0 THEN (SELECT top 1 Part FROM PCotPharma WHERE Cotiza=p.Clave AND Part=p.Partida) ELSE "+
								 " \n (SELECT top 1 Part FROM PCotPharma WHERE Codigo=p.Codigo AND Fabrica=p.Fabrica AND Cotiza=p.Clave) END) AS pcotPHS ON pcotPHS.idPCotiza=pc.idPCotiza WHERE pc.idPCotiza=" + pcotiza.getIdPartidaCotizacion() + " "+					
								 " \n UNION ALL " +								 
								 " \n SELECT 'Envío' AS Proceso,CASE WHEN pc.Folio=0 THEN NULL WHEN pc.Folio=99 THEN pc.FGeneracion END AS FechaInicio,CAST(CASE WHEN pc.Folio=0 THEN NULL WHEN pc.Folio=99 THEN CASE WHEN pc.Clasif='A' THEN COALESCE(pc.HEnvio,pc.FGeneracion) ELSE COALESCE((SELECT HEnvio FROM PCotizas WHERE Codigo=pc.Codigo AND Fabrica=pc.Fabrica AND Folio<>0 AND Folio<>99 AND "+
								 " \n Clave=pc.Clave),pc.FGeneracion) END END AS DATETIME) AS FechaFin,'No Aplica' AS Medio,'No Aplica' AS ClienteProveedor,'No Aplica' AS Contacto,c.Vendedor AS Responsable,'No Aplica' AS Referencia,'No Aplica' AS ClasificacionInicial,pc.Clasif AS ClasificacionFinal,'No Aplica' AS TProvee,"+
								 " \n 'No Aplica' AS Tipo,'No Aplica' AS Catalogo,'No Aplica' AS Concepto,'No Aplica' AS Presentacion,'No Aplica' AS Marca,'0' AS Precio,'No Aplica' AS Disponibilidad,'No Aplica' AS Manejo,'No Aplica' AS TEntrega,'No Aplica' AS CargosEnviosAdicionales,'No Aplica' AS HieloSeco,"+
								 " \n 'No Aplica' AS ComentariosAdicionales,'No Aplica' AS RechazoESAC,'No Aplica' AS RechazoPharma,'No Aplica' AS EnSTOCK,'No Aplica' AS UltimaEdicion,'No Aplica' AS CostosAdicionales," + util.obtenerTiempoTranscurridoSQL("CASE WHEN pc.Folio=0 THEN NULL WHEN pc.Folio=99 THEN pc.FGeneracion END ","CAST(CASE WHEN pc.Folio=0 THEN NULL WHEN pc.Folio=99 THEN CASE "+
								" \n WHEN pc.Clasif='A' THEN COALESCE(pc.HEnvio,pc.FGeneracion) ELSE COALESCE((SELECT HEnvio FROM PCotizas WHERE Codigo=pc.Codigo AND Fabrica=pc.Fabrica AND Folio<>0 AND Folio<>99 AND Clave=pc.Clave),pc.FGeneracion) END END AS DATETIME)", "Dias") + "  AS TT,'' AS SituacionPedido " +
								" \n FROM PCotizas AS pc "+
								" \n LEFT JOIN(SELECT Clave,Vendedor FROM Cotizas) AS c ON c.Clave=pc.Clave "+
								" \n LEFT JOIN(SELECT Numero,Fecha FROM DoctosR) AS dr ON dr.Numero=c.Clave "+
								" \n WHERE pc.idPCotiza=" + pcotiza.getIdPartidaCotizacion() +" "+
								" \n UNION ALL "+
								" \n SELECT 'Confirmación' AS Proceso,seg.Fecha AS FechaInicio,seg.FRealizo AS FechaFin,seg.Medio AS Medio,'No Aplica' AS ClienteProveedor,CASE WHEN seg.Contacto IS NULL OR seg.Contacto ='' THEN CASE WHEN seg.FRealizo IS NULL THEN 'Pendiente' ELSE 'ND' END ELSE seg.Contacto END AS Contacto," +
								" \n CASE WHEN seg.Vendedor IS NULL OR seg.Vendedor = '' THEN CASE WHEN seg.FRealizo IS NULL THEN 'Pendiente' ELSE 'ND' END ELSE seg.Vendedor END AS Responsable,'No Aplica' AS Referencia,"+
								" \n 'No Aplica' AS ClasificacionInicial,'No Aplica' AS ClasificacionFinal,'No Aplica' AS TProvee,'No Aplica' AS Tipo,'No Aplica' AS Catalogo,'No Aplica' AS Concepto,'No Aplica' AS Presentacion,'No Aplica' AS Marca,'0' AS Precio,"+
								" \n 'No Aplica' AS Disponibilidad,'No Aplica' AS Manejo,'No Aplica' AS TEntrega,'No Aplica' AS CargosEnviosAdicionales,'No Aplica' AS HieloSeco,CASE WHEN seg.Comenta IS NULL OR DATALENGTH(seg.Comenta) = 0 THEN CASE WHEN seg.FRealizo IS NULL THEN 'Pendiente' ELSE 'ND' END ELSE seg.Comenta END AS ComentariosAdicionales,'No Aplica' AS RechazoESAC,'No Aplica' AS RechazoPharma,"+
								" \n 'No Aplica' AS EnSTOCK,'No Aplica' AS UltimaEdicion,'No Aplica' AS CostosAdicionales," + util.obtenerTiempoTranscurridoSQL("seg.Fecha","seg.FRealizo", "Dias") + "  AS TT,'' AS SituacionPedido "+
								" \n FROM SegCotiza AS seg "+
								" \n WHERE seg.Cotiza='"+ pcotiza.getCotizacion() +"' "+
								" \n UNION ALL "+
								" \n SELECT 'Seguimiento' AS Proceso,bita.Fecha1 AS FechaInicio,seguimiento.FFin AS FechaFin,'No Aplica' AS Medio,'No Aplica' AS ClienteProveedor,'No Aplica' AS Contacto,seguimiento.Responsable AS Responsable,'No Aplica' AS Referencia,'No Aplica' AS ClasificacionInicial,"+
								" \n 'No Aplica' AS ClasificacionFinal,'No Aplica' AS TProvee,'No Aplica' AS Tipo,'No Aplica' AS Catalogo,'No Aplica' AS Concepto,'No Aplica' AS Presentacion,'No Aplica' AS Marca,'0' AS Precio,'No Aplica' AS Disponibilidad,'No Aplica' AS Manejo,'No Aplica' AS TEntrega,"+
								" \n 'No Aplica' AS CargosEnviosAdicionales,'No Aplica' AS HieloSeco,'No Aplica' AS ComentariosAdicionales,'No Aplica' AS RechazoESAC,'No Aplica' AS RechazoPharma,'No Aplica' AS EnSTOCK,'No Aplica' AS UltimaEdicion,"+
								" \n 'No Aplica' AS CostosAdicionales," + util.obtenerTiempoTranscurridoSQL("bita.Fecha1","seguimiento.FFin", "Dias") + "  AS TT,'' AS SituacionPedido "+
								" \n FROM Bitacora AS bita " +
								" \n LEFT JOIN(SELECT Docto,FFin,Responsable FROM Pendiente WHERE Tipo='Seguimiento') AS seguimiento ON seguimiento.Docto=bita.Cotiza " +
								" \n WHERE bita.Cotiza='"+ pcotiza.getCotizacion() +"' " +
								" \n UNION ALL " +
								" \n SELECT 'Tramitación' AS Proceso,(CASE WHEN dr.FHOrigen IS NULL THEN dr.Fecha ELSE dr.FHOrigen END ) AS FechaInicio,pend.FFin AS FechaFin,'No Aplica' AS Medio,'No Aplica' AS ClienteProveedor,contacto.Nombre AS Contacto,pend.Responsable," +
								" \n 'No Aplica' AS Referencia,'No Aplica' AS ClasificacionInicial,'No Aplica' AS ClasificacionFinal,'No Aplica' AS TProvee,'No Aplica' AS Tipo,'No Aplica' AS Catalogo,'No Aplica' AS Concepto,'No Aplica' AS Presentacion,'No Aplica' AS Marca," +
								" \n '0' AS Precio,'No Aplica' AS Disponibilidad,'No Aplica' AS Manejo,'No Aplica' AS TEntrega,'No Aplica' AS CargosEnviosAdicionales,'No Aplica' AS HieloSeco,p.CPedido AS ComentariosAdicionales,'No Aplica' AS RechazoESAC,'No Aplica' AS RechazoPharma," +
								" \n 'No Aplica' AS EnSTOCK,'No Aplica' AS UltimaEdicion,'No Aplica' AS CostosAdicionales,"+ util.obtenerTiempoTranscurridoSQL("(CASE WHEN dr.FHOrigen IS NULL THEN dr.Fecha ELSE dr.FHOrigen END )","pend.FFin", "Dias") + " AS TT,"+
								" \n (CASE WHEN COALESCE(countPP.T,0)>0 THEN 'Abierto' ELSE 'Cerrado' END) AS SituacionPedido " +
								" \n FROM Pedidos AS p " +
								" \n LEFT JOIN(SELECT PPedidos.CPedido,PCotizas.idPCotiza FROM PPedidos,PCotizas WHERE PPedidos.Cotiza=PCotizas.Clave AND PPedidos.Codigo=PCotizas.Codigo " +
								" \n AND PPedidos.Fabrica=PCotizas.Fabrica) AS pp ON pp.idPCotiza="+ pcotiza.getIdPartidaCotizacion() + " " +
								" \n LEFT JOIN(SELECT Folio,FHOrigen,Fecha FROM DoctosR) AS dr ON dr.Folio=p.DoctoR " +
								" \n LEFT JOIN(SELECT idContacto,(Titulo + ' ' + Contacto) AS Nombre FROM Contactos) AS contacto ON contacto.idContacto=p.idContacto " +
								" \n LEFT JOIN(SELECT Docto,MAX(FFin) AS FFin,Responsable FROM Pendiente WHERE (Tipo='Pedido por tramitar' OR Tipo='Pedido por Tramitar PSC') GROUP BY Docto,Responsable) AS pend ON CAST(pend.Docto AS VARCHAR(100))=CAST(dr.Folio AS VARCHAR(100)) " +
								" \n LEFT JOIN (SELECT COUNT(idPPedido) AS T, CPedido FROM PPedidos WHERE Estado <> 'Cancelada' AND Estado <> 'Entregado' AND Estado <> 'Enviado PHS' AND Estado <> 'Enviado' " +
								" \n AND Estado<> 'A destrucción' GROUP BY CPedido) AS countPP ON countPP.CPedido=PP.CPedido " +
								" \n WHERE p.CPedido=PP.CPedido";
			}else{
				String determinarFechaFin="CAST((SELECT COALESCE("+
						"(CASE WHEN pc.Folio=0 AND pc.Clasif='A' THEN (SELECT HEnvio FROM PCotizas WHERE idPCotiza="+ pcotiza.getIdPartidaCotizacion() +") END),"+
						"(CASE WHEN pc.Folio=0 AND pc.Clasif<>'A' THEN (SELECT HEnvio FROM PCotizas WHERE Codigo IN (SELECT Codigo FROM PCotPharma WHERE Cotiza='"+ pcotiza.getCotizacion() +"' AND Part="+ pcotiza.getPartida() +") AND Fabrica IN (SELECT Fabrica FROM PCotPharma WHERE Cotiza='"+ pcotiza.getCotizacion() +"' AND Part="+ pcotiza.getPartida() +") AND (Folio<>0 AND Folio <>99) AND Clave='"+ pcotiza.getCotizacion() +"') END),"+
						"(CASE WHEN pc.Folio=99 AND pc.Clasif='A' THEN (SELECT FGeneracion FROM PCotizas WHERE Codigo='"+ pcotiza.getCodigo() +"' AND Fabrica='"+ pcotiza.getFabrica() +"' AND Clave='"+ pcotiza.getCotizacion() +"' AND Folio=99 AND idPCotiza="+pcotiza.getIdPartidaCotizacion()+") END)," +
						"(CASE WHEN pc.Folio=99 AND pc.Clasif<>'A' THEN COALESCE((SELECT HEnvio FROM PCotizas WHERE Codigo='"+ pcotiza.getCodigo() +"' AND Fabrica='"+ pcotiza.getFabrica() +"' AND (Folio<>0 AND Folio<>99) AND Clave='"+ pcotiza.getCotizacion() +"'),pc.FGeneracion,GETDATE()) END))) AS DATETIME)";

				query = "SELECT 'General' AS Proceso,FechaInicio,"+ determinarFechaFin +" AS FechaFin,"+
						"'No Aplica' AS Medio,'No Aplica' AS ClienteProveedor,'No Aplica' AS Contacto,c.Vendedor AS Responsable,'No Aplica' AS Referencia,'No Aplica' AS ClasificacionInicial,'No Aplica' AS ClasificacionFinal,'No Aplica' AS TProvee,'No Aplica' AS Tipo,'No Aplica' AS Catalogo,'No Aplica' AS Concepto,"+
						"'No Aplica' AS Presentacion,'No Aplica' AS Marca,'0' AS Precio,'No Aplica' AS Disponibilidad,'No Aplica' AS Manejo,'No Aplica' AS TEntrega,'No Aplica' AS CargosEnviosAdicionales,'No Aplica' AS HieloSeco,'No Aplica' AS ComentariosAdicionales,'No Aplica' AS RechazoESAC,'No Aplica' AS RechazoPharma,"+
						"'No Aplica' AS EnSTOCK,'No Aplica' AS UltimaEdicion,'No Aplica' AS CostosAdicionales,"+ util.obtenerTiempoTranscurridoSQL("FechaInicio",determinarFechaFin, "Dias") + " AS TT, '' AS SituacionPedido  "+
						"FROM PCotizas AS pc "+
						"LEFT JOIN(SELECT Numero,Fecha AS FechaInicio FROM DoctosR) AS dc ON dc.Numero=pc.Clave "+
						"LEFT JOIN(SELECT Clave,Vendedor FROM Cotizas) AS c ON c.Clave=pc.Clave "+
						"WHERE pc.idPCotiza="+ pcotiza.getIdPartidaCotizacion();
			}
			log.info(query);
			return super.jdbcTemplate.query(query,map,new PartidaCotizacionTiempoProcesoRowMapper());
		} catch (RuntimeException e) {
			//log.info("Error: " + e.getMessage());
			return null;
		}
	}

	public PartidaCotizacion obtenerPCotizaXid(Long idPCotiza) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idPCotiza",idPCotiza);
		
			
			return (PartidaCotizacion) super.jdbcTemplate.queryForObject("SELECT * FROM Pcotizas WHERE idPCotiza = '" + idPCotiza+ "'",map, new PartidaCotizacionRowMapper());
		} catch (Exception e) {
			//log.info("Erro: " + e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<PartidaCotizacion> finConsultaPCotizas(String cotizacion) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cotizacion",cotizacion);
		
			f = new Funcion();
			String query = " \n SELECT cero.Clave,cero.Clasif,"+
						   " \n (CASE WHEN cero.Clasif='A' THEN nueve.Partida ELSE COALESCE(partidaBC.Partida,cero.Partida) END) AS Partida,"+
						   " \n (CASE WHEN cero.Clasif='A' THEN nueve.idPCotiza ELSE COALESCE(partidaBC.idPCotiza,cero.idPCotiza) END) AS idPCotiza,"+
						   " \n (CASE WHEN cero.Clasif='A' THEN nueve.Clasif ELSE COALESCE(partidaBC.Clasif,cero.Clasif) END) AS Clasificacion,"+
						   " \n (CASE WHEN cero.Clasif='A' THEN nueve.Folio ELSE COALESCE(partidaBC.Folio,cero.Folio) END) AS Folio,"+
						   " \n (CASE WHEN cero.Clasif='A' THEN nueve.Cant ELSE COALESCE(partidaBC.Cant,cero.Cant) END) AS Cantidad,"+
						   " \n (CASE WHEN cero.Clasif='A' THEN nueve.Precio ELSE COALESCE(partidaBC.Precio,cero.Precio) END) AS Precio,"+
						   " \n (CASE WHEN cero.Clasif='A' THEN (nueve.Precio*nueve.Cant) ELSE COALESCE((partidaBC.Precio*partidaBC.Cant),(cero.Precio*cero.Cant)) END) AS Importe,"+
						   " \n (CASE WHEN cero.Clasif='A' THEN nueve.Codigo ELSE partidaBC.Codigo END) AS Codigo,"+
						   " \n (CASE WHEN cero.Clasif='A' THEN nueve.Fabrica ELSE partidaBC.Fabrica END) AS Fabrica,"+
						   " \n (CASE WHEN cero.Clasif='A' THEN "+ f.sqlDescripcionProductosCotiza("PROD","cero")+" ELSE "+ f.sqlDescripcionProductosCotiza("PRODU","cero")+"  END) AS Descripcion,"+
						   " \n (CASE WHEN cero.Clasif='A' THEN nueve.Estado ELSE (CASE WHEN partidaBC.Folio=99 AND partidaBC.Estado<>'Por Gestionar' THEN partidaBC.Estado ELSE pcotPHS.Estado END)END) AS Estado,"+
						   " \n (CASE WHEN cero.Clasif='A' THEN CASE WHEN (nueve.Estado='Cancelada' OR nueve.Estado='Pedido' OR nueve.Estado='Pedido cotizado' OR nueve.Estado= 'Recotizada') OR (nueve.Destino='Gestionando' OR nueve.Destino='No Cotizar') THEN 'CERRADO' ELSE 'ABIERTO' END ELSE "+
						   " \n CASE WHEN partidaBC.Folio IS NULL THEN CASE WHEN pcotPHS.Estado='Gestionando' OR pcotPHS.Estado='No Cotizar' THEN 'CERRADO' ELSE 'ABIERTO' END ELSE "+
						   " \n CASE WHEN (partidaBC.Estado IS NULL OR partidaBC.Estado='Cancelada' OR partidaBC.Estado='Pedido' OR partidaBC.Estado='Pedido cotizado') OR (partidaBC.Destino='Gestionando' OR partidaBC.Destino='No Cotizar') " +
						   " \n THEN 'CERRADO' ELSE CASE WHEN partidaBC.Estado IS NOT NULL AND partidaBC.Estado='Recotizada' THEN 'CERRADO' ELSE 'ABIERTO' END END END END) AS EstadoFinal "+
						   " \n FROM PCotizas AS cero "+
						   " \n LEFT JOIN(SELECT * FROM PCotizas WHERE Folio=99)AS nueve ON nueve.Fabrica=cero.Fabrica AND nueve.Codigo=cero.Codigo AND nueve.Clave=cero.Clave "+
						   " \n LEFT JOIN (SELECT * FROM Productos) AS PROD ON PROD.Fabrica = nueve.Fabrica AND PROD.Codigo = nueve.Codigo " +
						   " \n LEFT JOIN(SELECT Cotiza,Part,Codigo,Fabrica,Estado FROM PCotPharma) AS pcotPHS ON pcotPHS.Cotiza=cero.Clave AND pcotPHS.Part=cero.Partida "+
						   " \n LEFT JOIN(SELECT * FROM PCotizas WHERE Folio=99) AS partidaBC ON partidaBC.Codigo=pcotPHS.Codigo AND partidaBC.Fabrica=pcotPHS.Fabrica AND partidaBC.Clave=pcotPHS.Cotiza "+
						   " \n LEFT JOIN (SELECT * FROM Productos) AS PRODU ON PRODU.Fabrica = partidaBC.Fabrica AND PRODU.Codigo = partidaBC.Codigo " +
						   " \n WHERE cero.Folio=0 AND cero.Clave='"+ cotizacion +"' ORDER BY cero.Partida ASC";
//			log.info(query);
			return super.jdbcTemplate.query(query, map, new PartidaCotizacionConsultasMapper());
		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	public List<TiempoProceso> findConsultaTiempoPedCancRecot(PartidaCotizacion pcotiza) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pcotiza",pcotiza);
			
			String query = "SELECT TOP 1 CASE WHEN PC.Estado='Cancelada' THEN 'CANCELADA' WHEN PC.Estado='Pedido' THEN 'PEDIDO' WHEN PC.Estado='Recotizada' THEN 'RECOTIZADA' END " +
					 " AS Proceso,DR.Fecha AS FechaInicio,PCHIS.Fecha AS FechaFin,'' AS Medio,'' AS ClienteProveedor,'' AS Contacto,PCHIS.Realizo AS Responsable, " +
					 " CASE WHEN PC.Estado='Pedido' THEN CASE WHEN PPED.CPedido IS NULL OR PPED.CPedido = '' THEN 'ND' ELSE PPED.CPedido END  WHEN PC.Estado='Recotizada' THEN CASE WHEN REFAC.Clave IS NULL OR REFAC.Clave = '' THEN 'ND' ELSE REFAC.Clave END  END  AS Referencia,'' AS ClasificacionInicial, " +
					 " JST.RazonesESAC AS ClasificacionFinal,'' AS TProvee,'' AS Tipo,'' AS Catalogo,'' AS Concepto,'' AS Presentacion,'' AS Marca," + util.obtenerTiempoTranscurridoSQL("DR.Fecha","PCHIS.Fecha", "Dias") + " AS TT, CU.SUMA AS Precio,'' AS Disponibilidad,'' AS Manejo,'' AS TEntrega, " +
					 " CAST(JST.RazonCancelacion AS VARCHAR(MAX))+ ' - '+ CAST(JST.RazonesMonitor AS VARCHAR(MAX)) AS CargosEnviosAdicionales,'' AS HieloSeco,PCHIS.Comentarios AS ComentariosAdicionales,'' AS RechazoESAC,'' AS RechazoPharma,'' AS EnSTOCK,'' AS UltimaEdicion,'' AS CostosAdicionales,'' AS SituacionPedido  " +
					 " FROM PCotizaHistorial AS PCHIS  " +
					 " LEFT JOIN(SELECT * FROM PCotizas) AS PC ON PC.idPCotiza=PCHIS.idPCotiza " +
					 " LEFT JOIN(SELECT Numero,Fecha FROM DoctosR) AS dr ON dr.Numero=pc.Clave  " +
					 " LEFT JOIN(SELECT * FROM PPedidos) AS PPED ON PPED.Cotiza = PC.Clave AND PPED.Codigo=PC.Codigo " +
					 " LEFT JOIN(SELECT * FROM PCotizas WHERE Folio=99) AS REFAC ON REFAC.FK01_PCotizaOrigen = PC.idPCotiza " +
					 " LEFT JOIN(SELECT COUNT(*)AS SUMA, Clave FROM PCotizas WHERE Folio=99 GROUP BY Clave) AS CU ON CU.Clave = REFAC.Clave " +
					 " LEFT JOIN(SELECT * FROM HistorialDecidirCancelacion WHERE Aceptado='1' ) AS JST ON JST.idPCotiza=PCHIS.idPCotiza " +
					 " WHERE  pc.idPCotiza="+ pcotiza.getIdPartidaCotizacion() + " AND (pc.Estado = 'Cancelada' OR pc.Estado = 'Pedido' OR pc.Estado = 'Recotizada') " +
					 " ORDER BY PCHIS.Fecha DESC " ;
//			log.info(query);
			return super.jdbcTemplate.query(query,map,new PartidaCotizacionTiempoProcesoRowMapper());
		} catch (Exception e) {
			//log.info("Erro: " + e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<PartidaCotizacion> findConsultaPartidaResumenGrafica(String condiciones) throws ProquifaNetException {

		
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			
			String query = 
					" \n DECLARE @PDOLAR22	float = (SELECT TOP  1 PDolar FROM Monedas ORDER BY Fecha DESC)		DECLARE @EDOLAR22 	float = (SELECT TOP  1 EDolar FROM Monedas ORDER BY Fecha DESC) " +
					" \n DECLARE @LDOLAR22  float = (SELECT TOP  1 LDolar FROM Monedas ORDER BY Fecha DESC)	DECLARE @YDOLAR22   float = (SELECT TOP  1 YDolar FROM Monedas ORDER BY Fecha DESC) " +
					" \n DECLARE @DDOLAR22  float = (SELECT TOP  1 DDolar FROM Monedas ORDER BY Fecha DESC) " +
					" \n SELECT " +
					" \n cero.Clasif,cero.Clave, '1' AS PARTIDAS, CLI.Nombre AS CLIENTE, " +
					" \n COALESCE(nueve.Cant,partidaBC.Cant,pcotPHS.Cantidad,cero.Cant) PIEZAS, " +
					" \n COALESCE(nueve.idPCotiza,partidaBC.idPCotiza,cero.idPCotiza) idPCotiza,  " +
					" \n COALESCE(nueve.Folio,partidaBC.Folio,cero.Folio) Folio, COALESCE(nueve.Clasif,partidaBC.Clasif,cero.Clasif) Clasificacion,   " +
					" \n COALESCE(nueve.Fabrica,partidaBC.Fabrica,pcotPHS.Fabrica,cero.Fabrica) Fabrica, COALESCE(nueve.Codigo,partidaBC.Codigo,pcotPHS.Codigo,cero.Codigo) Codigo,   " +
					" \n (CASE WHEN cero.Clasif='A' THEN nueve.Estado ELSE (CASE WHEN partidaBC.Folio=99 AND partidaBC.Estado<>'Por Gestionar' THEN COALESCE(partidaBC.Estado,CERO.ESTADO) ELSE COALESCE(pcotPHS.Estado,CERO.ESTADO) END)END) AS ESTATUS, " + 
					" \n (CASE WHEN cero.Clasif='A' THEN CASE WHEN (nueve.Estado='Cancelada' OR nueve.Estado='Pedido' OR nueve.Estado='Pedido cotizado' OR nueve.Estado= 'Recotizada') OR (nueve.Destino='Gestionando' OR nueve.Destino='No Cotizar') " +  
					" \n THEN 'CERRADO' ELSE 'ABIERTO' END ELSE CASE WHEN partidaBC.Folio IS NULL THEN CASE WHEN pcotPHS.Estado='Gestionando' OR pcotPHS.Estado='No Cotizar' THEN 'CERRADO' ELSE 'ABIERTO' END ELSE     " +
					" \n CASE WHEN (partidaBC.Estado IS NULL OR partidaBC.Estado='Cancelada' OR partidaBC.Estado='Pedido' OR partidaBC.Estado='Pedido cotizado') OR (partidaBC.Destino='Gestionando' OR partidaBC.Destino='No Cotizar')   THEN 'CERRADO'  " + 
					" \n ELSE CASE WHEN partidaBC.Estado IS NOT NULL AND partidaBC.Estado='Recotizada' THEN 'CERRADO' ELSE 'ABIERTO' END END END END) AS ESTADO, COTI.VENDEDOR AS COTIZO,    " +
					" \n (CASE WHEN PCOT_0.PART_0 > COALESCE(PCOT99_A.PART_A,'0') THEN CASE WHEN COTI.MSalida='F' THEN 'Fax' ELSE 'Correo' END ELSE 'Pendiente' END) AS MSalida,   " +
					" \n COALESCE(nueve.Cant,partidaBC.Cant,pcotPHS.Cantidad,cero.Cant) *" +
					" \n 	CASE " +
					" \n		WHEN NUEVE.Precio IS NOT NULL THEN" +
					" \n			CASE" +
					" \n 		WHEN COTI.IMoneda = 'Euros' THEN nueve.Precio * COALESCE(MON.EDolar,@EDOLAR22)" +
					" \n 		WHEN COTI.IMoneda = 'Yenes' THEN nueve.Precio * COALESCE(MON.YDolar,@YDOLAR22)" +
					" \n 		WHEN COTI.IMoneda = 'DlCan' THEN nueve.Precio * COALESCE(MON.DDolar,@DDOLAR22)" +
					" \n 		WHEN COTI.IMoneda = 'Libras' THEN nueve.Precio * COALESCE(MON.LDolar,@LDOLAR22) " +
					" \n 		WHEN COTI.IMoneda = 'Pesos' THEN nueve.Precio / COALESCE(MON.PDolar,@PDOLAR22) " +
					" \n 			ELSE nueve.Precio END" +
					" \n 	WHEN NUEVE.Precio IS NULL  AND partidaBC.Precio IS NOT NULL THEN partidaBC.Precio" +
					" \n 	WHEN COALESCE(nueve.Precio,partidaBC.Precio) IS NULL THEN pcotPHS.Precio" +
					" \n 	ELSE CASE " +
					" \n	WHEN COTI.IMoneda = 'Euros' THEN cero.Precio * COALESCE(MON.EDolar,@EDOLAR22)" +
					" \n	WHEN COTI.IMoneda = 'Yenes' THEN cero.Precio * COALESCE(MON.YDolar,@YDOLAR22)" +
					" \n	WHEN COTI.IMoneda = 'DlCan' THEN cero.Precio * COALESCE(MON.DDolar,@DDOLAR22)" +
					" \n	WHEN COTI.IMoneda = 'Libras' THEN cero.Precio * COALESCE(MON.LDolar,@LDOLAR22) " +
					" \n	WHEN COTI.IMoneda = 'Pesos' THEN cero.Precio / COALESCE(MON.PDolar,@PDOLAR22) " +
					" \n 			ELSE cero.Precio END" +
					" \n 	END AS MONTO," +
					" \n DC.FechaInicio,FE.FechaFin,  " +
					" \n CASE WHEN CERO.Clasif = 'A'  " +
					" \n 	THEN		 " +
					" \n 	CASE WHEN (DATEDIFF(SS, FechaInicio, FE.FechaFin)<=10800)THEN 1  " +
					" \n 				ELSE  " +
					" \n 				(CASE WHEN (DATEDIFF(DAY, FechaInicio, FE.FechaFin) >1)  " +
					" \n 						THEN  " +
					" \n 							(CASE WHEN DATENAME(DW, FechaInicio)='Viernes'  " +
					" \n 								THEN (CASE WHEN ((DATEDIFF(SS, FechaInicio, FE.FechaFin)-223200)<=10800) THEN 1 ELSE 0  END)  " +
					" \n 								ELSE 0  " +
					" \n 							END)  " +
					" \n 						ELSE  " +
					" \n 						CASE WHEN (CASE WHEN DATEDIFF(SS,FechaInicio,FHFIN.FECHA)>0 THEN  DATEDIFF(SS,FechaInicio,FHFIN.FECHA) ELSE 0 END + DATEDIFF(SS,LIMHRS_A.FMAX_A,FE.FechaFin))<=10800 THEN 1 ELSE 0 END  " +
					" \n 					END)  " +
					" \n 				END		   " +
					" \n 		ELSE  " +
					" \n 		CASE WHEN DATEDIFF(SS, FechaInicio,FE.FechaFin)<=259200  " +
					" \n 				THEN 1  " +
					" \n 				ELSE  " +
					" \n 				CASE WHEN DATEDIFF(SS,FechaInicio,FE.FechaFin)>259200 AND (DATEPART(WEEKDAY,FechaInicio)=1 OR DATEPART(WEEKDAY,FechaInicio)= 2)  " +
					" \n 						THEN 0  " +
					" \n 						ELSE  " +
					" \n 							CASE  " +
					" \n 									WHEN((DATEDIFF(SS,FechaInicio,FE.FechaFin)-172800)<=259200)  " +
					" \n 										THEN 1  " +
					" \n 										ELSE 0  " +
					" \n 								END  " +
					" \n 					END  " +
					" \n 		END  " +
					" \n 	END AS ET,COALESCE(prod.Tipo,'Desconocido') AS TIPO  " +
					" \n  FROM PCotizas AS cero" +
					" \n LEFT JOIN (SELECT * FROM Cotizas) AS COTI ON COTI.Clave = cero.Clave     " +
					" \n LEFT JOIN (SELECT * FROM PCotizas WHERE Folio=99)AS nueve ON nueve.Fabrica=cero.Fabrica AND nueve.Codigo=cero.Codigo AND nueve.Clave=cero.Clave    " + 
					" \n LEFT JOIN (SELECT PCOT.Cotiza,PCOT.Part,PCOT.Codigo,PCOT.Fabrica,PCOT.Estado, sum(PCOT.Cantidad) Cantidad," +
					" \n 			sum(CASE	WHEN PCOT.Moneda = 'Yenes' THEN PCOT.Precio * COALESCE(M.YDolar,@YDOLAR22)" +
					" \n 					WHEN PCOT.Moneda = 'DlCan' THEN PCOT.Precio * COALESCE(M.DDolar,@DDOLAR22)" +
					" \n 					WHEN PCOT.Moneda = 'Euros' THEN PCOT.Precio * COALESCE(M.EDolar,@EDOLAR22)" +
					" \n 					WHEN PCOT.Moneda = 'Libras' THEN PCOT.Precio * COALESCE(M.LDolar,@LDOLAR22)" +
					" \n 					WHEN PCOT.Moneda = 'Pesos' THEN PCOT.Precio / COALESCE(M.PDolar,@PDOLAR22)" +
					" \n 					ELSE PCOT.Precio" +
					" \n 			END) Precio" +
					" \n 			FROM PCotPharma PCOT" +
					" \n 			LEFT JOIN (SELECT Clave, Fecha FROM Cotizas) C ON C.Clave = PCOT.Cotiza " +
					" \n 		LEFT JOIN (SELECT * FROM Monedas) M ON CAST(M.Fecha AS DATE) = CAST(C.Fecha AS DATE)" +
					" \n 		GROUP BY PCOT.Cotiza,PCOT.Part,PCOT.Codigo,PCOT.Fabrica,PCOT.Estado" +
					" \n 		) AS pcotPHS ON pcotPHS.Cotiza=cero.Clave AND pcotPHS.Part=cero.Partida     " +
					" \n LEFT JOIN (SELECT Codigo, Fabrica, PC.Clave, Cant, sum(idPCotiza) idPCotiza, Folio,Clasif, Estado, Destino, " +
					" \n 			sum(CASE	WHEN C.IMoneda = 'Yenes' THEN PC.Precio * COALESCE(M.YDolar,@YDOLAR22)" +
					" \n 					WHEN C.IMoneda = 'DlCan' THEN PC.Precio * COALESCE(M.DDolar,@DDOLAR22)" +
					" \n 					WHEN C.IMoneda = 'Euros' THEN PC.Precio * COALESCE(M.EDolar,@EDOLAR22)" +
					" \n 					WHEN C.IMoneda = 'Libras' THEN PC.Precio * COALESCE(M.LDolar,@LDOLAR22)" +
					" \n 					WHEN C.IMoneda = 'Pesos' THEN PC.Precio / COALESCE(M.PDolar,@PDOLAR22)" +
					" \n 					ELSE PC.Precio" +
					" \n 			END) Precio" +
					" \n 			FROM PCotizas PC" +
					" \n 			LEFT JOIN (SELECT Clave, Fecha, IMoneda FROM Cotizas) C ON C.Clave = PC.Clave" +
					" \n 			LEFT JOIN (SELECT * FROM Monedas) M ON CAST(M.Fecha AS DATE) = CAST(C.Fecha AS DATE)" +
					" \n 			WHERE Folio=99  GROUP BY Codigo,Fabrica,PC.Clave, Cant,Folio,Clasif, Estado,Destino) " +
					" \n 			AS partidaBC ON partidaBC.Codigo=pcotPHS.Codigo AND partidaBC.Fabrica=pcotPHS.Fabrica AND partidaBC.Clave=pcotPHS.Cotiza      " +  
					" \n LEFT JOIN (SELECT * FROM Monedas) AS MON ON CAST(MON.Fecha AS DATE)=CAST(COTI.Fecha AS DATE)     " +
					" \n LEFT JOIN (SELECT Clave,Nombre,Vendedor,FK01_EV FROM Clientes) AS CLI ON CLI.Nombre = COTI.Cliente     " +
					" \n LEFT JOIN (SELECT COUNT(*) AS PART_0,Clave FROM PCotizas WHERE Folio = 0 AND ((Destino<>'Gestionando' AND Destino<>'No Cotizar') or Destino IS NULL) GROUP BY Clave) AS PCOT_0 ON PCOT_0.Clave = cero.Clave  " +  
					" \n LEFT JOIN (SELECT COUNT(*) AS PART_A,Clave  FROM PCotizas WHERE Folio = 99 AND Clasif = 'A' GROUP BY Clave) AS PCOT99_A ON PCOT99_A.Clave=cero.Clave     " +
					" \n LEFT JOIN (SELECT Numero,Fecha AS FechaInicio FROM DoctosR) AS dc ON dc.Numero=cero.Clave       " +
					" \n LEFT JOIN (SELECT DATEADD(HH,18, CAST(CAST(MAX(Fecha) AS DATE) AS DATETIME)) AS FECHA, Numero FROM DoctosR GROUP BY Numero ) AS FHFIN ON FHFIN.Numero = CERO.Clave  " +
					" \n LEFT JOIN (SELECT Clave, DATEADD(HH,8, CAST(CAST(MAX(FGeneracion) AS DATE) AS DATETIME)) AS FMAX_A FROM PCotizas  WHERE Clasif='A' AND Folio=99 GROUP BY Clave) AS LIMHRS_A ON LIMHRS_A.Clave = CERO.Clave  " +
					" \n LEFT JOIN (SELECT PCO.idPCotiza, COALESCE (TOTINFO_A.FMAX_A,TOTINFO_B.FMAX_B,(" +
					" \n 			CASE	WHEN PCO.FGeneracion IS NOT NULL THEN PCO.FGeneracion " +
					" \n 					ELSE CASE	WHEN DATALENGTH(PCO.HEnvio)>0 AND PCO.HEnvio IS NOT NULL   " +
					" \n 								THEN  CAST(PHENVIO.HEnvio AS DATETIME)	" +
					" \n 								ELSE COALESCE (PHA.FFin,GETDATE())END END)) " +
					" \n 			AS FechaFin " +
					" \n 			FROM PCotizas AS PCO  " +
					" \n 			LEFT JOIN (SELECT * FROM PCotizas_PCotPharma) AS CON ON CON.FK01_idPCotizaM = PCO.idPCotiza " +
					" \n 			LEFT JOIN (SELECT Folio,FFin,Cotiza,Part FROM PCotPharma) AS PHA ON PHA.Folio=CON.FK02_idPCotPharma  " +
					" \n 			LEFT JOIN (SELECT Destino,Clave,Partida,HEnvio,idPCotiza FROM PCotizas) AS PHENVIO ON PHENVIO.idPCotiza = PCO.idPCotiza   " +
					" \n 			LEFT JOIN(SELECT idPCotiza,COALESCE((FGeneracion),GETDATE()) AS FMAX_A FROM PCotizas WHERE Clasif='A' AND Folio=99 ) AS TOTINFO_A ON TOTINFO_A.idPCotiza = PCO.idPCotiza  " +
					" \n 			LEFT JOIN(SELECT idPCotiza, COALESCE((FGeneracion),GETDATE()) AS FMAX_B FROM PCotizas WHERE Clasif<>'A' AND Folio=99 ) AS TOTINFO_B ON TOTINFO_B.idPCotiza = PCO.idPCotiza)   " +
					" \n 			AS FE ON FE.idPCotiza =  COALESCE(nueve.idPCotiza,partidaBC.idPCotiza,cero.idPCotiza)  " +
					" \n LEFT JOIN(SELECT Codigo, Fabrica, Tipo FROM Productos GROUP BY Codigo, Fabrica, Tipo) AS prod ON prod.Codigo= COALESCE(nueve.Codigo,partidaBC.Codigo,pcotPHS.Codigo,cero.Codigo)  " +
					" \n AND prod.Fabrica=COALESCE(nueve.Fabrica,partidaBC.Fabrica,pcotPHS.Fabrica,cero.Fabrica)  " +
					" \n WHERE cero.Folio=0  "+condiciones+" " +
					" \n ORDER BY Cero.Clave" ;
			
//			" \n GROUP BY Cero.Clasif,CERO.Clave,nueve.idPCotiza,partidaBC.idPCotiza,CERO.idPCotiza,NUEVE.Folio,partidaBC.Folio,CERO.Folio,nueve.Clasif,partidaBC.Clasif,CERO.Clasif,NUEVE.Fabrica,partidaBC.Fabrica,CERO.Fabrica, " +
//			" \n CLI.Nombre,NUEVE.Codigo,partidaBC.Codigo,CERO.Codigo,NUEVE.Estado,partidaBC.Estado,CERO.Estado,pcotPHS.Estado,nueve.Destino,partidaBC.Destino,COTI.VENDEDOR,PCOT_0.PART_0,PCOT99_A.PART_A,PCOT99_B.PART_B,COTI.MSalida, " +
//			" \n DC.FechaInicio,FE.FechaFin,FHFIN.FECHA,LIMHRS_A.FMAX_A,suma.monto,prod.Tipo " +
			
			//log.info(query);
			return super.jdbcTemplate.query(query,map,new PartidaCotizacionResumenGraficaETyFTmapper());
		} catch (Exception e1) {
			log.info("Erro: " + e1.getMessage());
			return null;
		}
	}
	
	public int definirFlujoInvestiga(Long idPCotiza){
		//Si la consulta regresa registros entonces es por el Nuevo Flujo de Alta de producto en BD si regresa 0 es el flujo de investigaciones el anterior
		String sQuery, sFiltro = "";
		int folioCotiza;
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idPCotiza",idPCotiza);
			
			folioCotiza = obtenerFolioPCotizas(idPCotiza);
			//log.info(folioCotiza);
			
			if (folioCotiza == 0){
				sFiltro = "PCOT.Part = PC.Partida";
			}
			else if(folioCotiza >=1 && folioCotiza <=99){
				sFiltro = "PCOT.Codigo = PC.Codigo AND PCOT.Fabrica = PC.Fabrica";
			}
			sQuery = "SELECT COUNT(*) TotalRegistros FROM PCotizas PC ";
			sQuery = sQuery + " INNER JOIN PCotPharma PCOT ON PCOT.Cotiza = PC.Clave AND " + sFiltro + " ";
			sQuery = sQuery + " INNER JOIN (SELECT * FROM Pendiente WHERE Tipo = 'Producto por Validar') P ON P.Docto = PCOT.Folio ";
			sQuery = sQuery + " WHERE PCOT.FInicio > '20140312 09:46' and PC.idPCotiza = " + idPCotiza; //Se usa esta fecha por que es cuando se empezo a trabajar con en Nuevo Flujo
			
			//log.info(sQuery);
			return super.queryForInt(sQuery);
			
		}catch (Exception e) {
			//log.info("Erro: " + e.getMessage());
			return 0;
		}		
	}
	
	@SuppressWarnings("unchecked")
	public List<TiempoProceso> obtenerTiempoProcesoNuevo(Long idPCotiza){
		//Obtiene el Tiempo Proceso del Nuevo Flujo de Investigacion
		String sQuery, sFiltro = "";
		int folioCotiza;		
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idPCotiza",idPCotiza);
			folioCotiza = obtenerFolioPCotizas(idPCotiza);
			
			//log.info(folioCotiza);
			if (folioCotiza == 0){
				sFiltro = "PCOT.Part = PC.Partida";						  
			}
			else if(folioCotiza >=1 && folioCotiza <=99){
				sFiltro = "PCOT.Codigo = PC.Codigo AND PCOT.Fabrica = PC.Fabrica";						  
			}
			
			// @partida = FOLIO PCOTPHARMA -- ES EL ID DE LA PARTIDA DE PCOTPHARMA
			sQuery = "Declare @partida as int " + 
					// Asigna a @partida el Folio de PCotPharma
					" SELECT @partida = PCOT.Folio FROM PCotizas PC " +
					" INNER JOIN DoctosR DR ON DR.Numero = PC.Clave OR DR.Numero = (PC.Clave + '-P') " +
					" INNER JOIN PCotPharma PCOT ON PCOT.Cotiza = PC.Clave AND " + sFiltro + " " + 
					" WHERE idPCotiza = " + idPCotiza + " " +
					// GENERALES, SE USA LA PRIMERA COLUMNA ORD SOLO PARA ORDENAR LA CONSULTA
					" SELECT 1 AS ORD,'GENERALES' AS Proceso, DR.Fecha AS FRecepcion, PCOT.FInicio, PCOT.FFin, NULL TimepoTotal ,'No Aplica' Responsable, " +
					" '' idProveedor, '' Proveedor, '' Fabrica, '' Codigo, '' Concepto, '' Manejo, '' Tipo, '' Subtipo, '' Control, '' Clasificacion, '' CAS, '' DepositarioInternacional, '' EdoFisico, '' Precio, " +
					" '' FraccionArancelaria, '' DTA, '' IGI, '' PermisoImp, '' TipoPermiso, NULL FIngreso, '' Observa, '' Moneda, '' ObservaP, '' Estado " +
					" FROM PCotizas PC " +
					" INNER JOIN DoctosR DR ON DR.Numero = PC.Clave OR DR.Numero = (PC.Clave + '-P') " +
					" INNER JOIN PCotPharma PCOT ON PCOT.Cotiza = PC.Clave AND " + sFiltro + " " +
					" WHERE idPCotiza =  " + idPCotiza + " " +
					// ATENDER Investigacion, Se toma el Pendiente 'Producto por Validar' para tomar el Fecha Fin de 'ATENDER Investigacion'
					" union " +
					" select 2 AS ORD, 'ATENDER Investigación' AS Proceso, NULL AS FRecepcion, coalesce ((select max (p.FFin)  from Pendiente as p , pcotpharma, Cotizas  where p.Docto = pcotpharma.Folio and  Cotizas.Clave = PCotPharma.Cotiza and p.Tipo = 'Producto por Confirmar Datos'  " +
					" and (p.FFin is not null or PCotPharma.Estado = 'Por Investigar') and p.Docto =  p0.Docto and p.FFin < P0.FInicio ), pcp.finicio) as fnicio , " +
					" case when P0.Folio IS null then   pcp.FInvestiga  else p0.FInicio  end  ffin, " + 
					" DATEDIFF(DD,coalesce ((select max (p.FFin)  from Pendiente as p , pcotpharma, Cotizas  where p.Docto = pcotpharma.Folio and  Cotizas.Clave = PCotPharma.Cotiza and p.Tipo = 'Producto por Confirmar Datos' " +					
					" and (p.FFin is not null or PCotPharma.Estado = 'Por Investigar') and p.Docto =  p0.Docto and p.FFin < P0.FInicio ), pcp.finicio), coalesce(case when P0.Folio IS null then   pcp.FInvestiga  else p0.FInicio  end,getdate())) TimepoTotal, " + 
					" c.Vendedor responsable,  " +
					" pcp.Proveedor idProveedor, Prov.Nombre Proveedor, pcp.Fabrica, pcp.Codigo, convert(varchar,pcp.Concepto) Concepto, pcp.Manejo, pcp.Tipo, pcp.Subtipo, pcp.Control, pcp.Clasificacion, " +
					" pcp.CAS, pcp.DepositarioInternacional, pcp.EdoFisico, pcp.Precio, pcp.FraccionArancelaria, pcp.DTA, pcp.IGI, pcp.PermisoImp, pcp.TipoPermiso, NULL FIngreso, '' Observa, pcp.Moneda, '' ObservaP, '' Estado " +
					" from Cotizas as c, Proveedores Prov, PCotPharma as pcp " +
					" left join (select * from Pendiente )as p0  on p0.Tipo = 'Producto por Validar' and  p0.docto = pcp.folio " +
					" where pcp.folio = @partida and c.Clave = pcp.Cotiza  and Prov.Clave = pcp.Proveedor " +
					// ATENDER ALTA DE PRODUCTO
					" union " +
					" select 3 AS ORD, 'ATENDER ALTA DE PRODUCTO' AS Proceso,NULL AS FRecepcion, coalesce (p.finicio , pcp.finvestiga )as FInicio, coalesce (p.ffin , pcp.fvalida )as ffin, " +
					" DATEDIFF(DD,coalesce (p.finicio , pcp.finvestiga ),coalesce(coalesce (p.ffin , pcp.fvalida ), getdate())) TimepoTotal, responsable,  " +
					" pcp.Proveedor idProveedor, Prov.Nombre Proveedor, pcp.Fabrica, pcp.Codigo, convert(varchar,pcp.Concepto) Concepto, pcp.Manejo, pcp.Tipo, pcp.Subtipo, pcp.Control, pcp.Clasificacion, " +
					" pcp.CAS, pcp.DepositarioInternacional, pcp.EdoFisico, pcp.Precio, pcp.FraccionArancelaria, pcp.DTA, pcp.IGI, pcp.PermisoImp, pcp.TipoPermiso, pcp.FFin FIngreso, '' Observa, pcp.Moneda, '' ObservaP, '' Estado " +
					" from pcotpharma pcp " +
					" inner join ( select * from Pendiente where  Tipo = 'Producto por Validar' ) as p on  p.Docto = pcp.Folio " +
					" inner join (Select Clave, Nombre from Proveedores) Prov on Prov.Clave = pcp.Proveedor  " +
					" where pcp.Folio = @partida " +
					// EVALUAR RESULTADOS
					" union " +
					" select 4 AS ORD, 'EVALUAR RESULTADOS' AS Proceso, NULL AS FRecepcion, coalesce (p.finicio , pcp.fvalida ) FInicio, coalesce (p.ffin ,coalesce ( pcp.fPharma ,pcp.ffin)) ffin, " +
					" DATEDIFF(DD,coalesce (p.finicio , pcp.fvalida ),coalesce(coalesce (p.ffin ,coalesce ( pcp.fPharma ,pcp.ffin)),getdate())) TimepoTotal, Responsable, " +
					" pcp.Proveedor idProveedor, Prov.Nombre Proveedor, pcp.Fabrica, pcp.Codigo, convert(varchar,pcp.Concepto) Concepto, pcp.Manejo, pcp.Tipo, pcp.Subtipo, pcp.Control, pcp.Clasificacion, " +
					" pcp.CAS, pcp.DepositarioInternacional, pcp.EdoFisico, pcp.Precio, pcp.FraccionArancelaria, pcp.DTA, pcp.IGI, pcp.PermisoImp, pcp.TipoPermiso, NULL FIngreso, '' Observa, pcp.Moneda, pcp.ObservaP, '' Estado  " +
					" from pcotpharma pcp " +
					" inner  join ( select * from Pendiente where  Tipo = 'Producto por Confirmar Datos') as p on  p.Docto = pcp.Folio " +
					" inner join (Select Clave, Nombre from Proveedores) Prov on Prov.Clave = pcp.Proveedor " +
					" where pcp.Folio = @partida " +
					// CONFIRMAR DATOS INTERNACIONAL, ABIERTO
					" union " +
					" select 5 AS ORD, 'CONFIRMAR DATOS INTERNACIONAL' AS Proceso, NULL AS FRecepcion, coalesce (p3.FFin, pcp.fvalida ) finicio , NULL ffin, DATEDIFF(DD, coalesce (p3.FFin, pcp.fvalida ), GETDATE()) TimepoTotal, " +
					" (select top 1 usuario from Empleados where Usuario =  'ComPHS-USA' and Fase > 0 ) responsable,  " +
					" pcp.Proveedor idProveedor, Prov.Nombre Proveedor, pcp.Fabrica, pcp.Codigo, convert(varchar,pcp.Concepto) Concepto, pcp.Manejo, pcp.Tipo, pcp.Subtipo, pcp.Control, pcp.Clasificacion, " +
					" pcp.CAS, pcp.DepositarioInternacional, pcp.EdoFisico, pcp.Precio, pcp.FraccionArancelaria, pcp.DTA, pcp.IGI, pcp.PermisoImp, pcp.TipoPermiso, NULL FIngreso, '' Observa, pcp.Moneda, '' ObservaP, '' Estado " +
					" from PCotPharma as pcp " +
					" left join (select max(ffin ) as ffin , Docto  from Pendiente where Tipo in ('Producto por Confirmar Datos','Producto por Validar')group by Docto)as p3 on p3.docto = pcp.folio " +
					" inner join (Select Clave, Nombre from Proveedores) Prov on Prov.Clave = pcp.Proveedor  " +
					" where Estado = 'En Pharma' and folio = @partida " +
					" union " +
					// CONFIRMAR DATOS INTERNACIONAL, CERRADO
					" select 5 AS ORD, 'CONFIRMAR DATOS INTERNACIONAL' AS Proceso, NULL AS FRecepcion, (select top 1 p5.FFin  from  Pendiente  p4 " +
					" 	left join (select * from Pendiente ) as p5 on p5.Docto = p4.Docto and p5.Folio< p4.folio and p5.Tipo in ('Producto por Confirmar Datos','Producto por Validar')  " +
					" 	where p4.Docto = @partida and p4.folio = p1.Folio  and p4.Finicio <> p5.ffin   group by p5.Folio , p5.ffin  order by p5.FFin desc) as  finicio, p1.FInicio  ffin2, " +
					" DATEDIFF(DD, (select top 1 p5.FFin  from  Pendiente  p4 " +
					" left join (select * from Pendiente ) as p5 on p5.Docto = p4.Docto and p5.Folio< p4.folio and p5.Tipo in ('Producto por Confirmar Datos','Producto por Validar') " +
					" where p4.Docto = @partida and p4.folio = p1.Folio  and p4.Finicio <> p5.ffin   group by p5.Folio , p5.ffin  order by p5.FFin desc), coalesce(p1.FInicio,getdate())) TimepoTotal, " +					
					" (select top 1 usuario from Empleados where Usuario =  'ComPHS-USA' and Fase > 0 ) responsable, " +
					" pcp.Proveedor idProveedor, Prov.Nombre Proveedor, pcp.Fabrica, pcp.Codigo, convert(varchar,pcp.Concepto) Concepto, pcp.Manejo, pcp.Tipo, pcp.Subtipo, pcp.Control, pcp.Clasificacion,  " +
					" pcp.CAS, pcp.DepositarioInternacional, pcp.EdoFisico, pcp.Precio, pcp.FraccionArancelaria, pcp.DTA, pcp.IGI, pcp.PermisoImp, pcp.TipoPermiso, NULL FIngreso, '' Observa, pcp.Moneda, '' ObservaP, '' Estado " +
					" from  Pendiente p1 " +
					" INNER JOIN PCotPharma pcp on pcp.Folio = p1.Docto  " +
					" INNER JOIN (Select Clave, Nombre from Proveedores) Prov on Prov.Clave = pcp.Proveedor  " +
					" where p1.Docto = @partida and p1.Tipo = 'Producto por Confirmar Datos' " +
					" and not exists (select * from Pendiente where Tipo in ('Producto por Confirmar Datos','Producto por Validar') and FFin = p1.FInicio and Docto = p1.Docto ) " +
					// PDP
					" union " +
					" select 6 AS ORD, 'VALIDAR pDp' AS Proceso, NULL AS FRecepcion, case when FValida IS null and finvestiga IS not null then FInvestiga when FValida IS not null then FValida end finicio, pcp.FFin, " +
					" DATEDIFF(DD,case when FValida IS null and finvestiga IS not null then FInvestiga when FValida IS not null then FValida end,coalesce(pcp.FFin,getdate())) TimepoTotal, vendedor responsable,  " +
					" pcp.Proveedor idProveedor, Prov.Nombre Proveedor, pcp.Fabrica, pcp.Codigo, convert(varchar,pcp.Concepto) Concepto, pcp.Manejo, pcp.Tipo, pcp.Subtipo, pcp.Control, pcp.Clasificacion, " +
					" pcp.CAS, pcp.DepositarioInternacional, pcp.EdoFisico, pcp.Precio, pcp.FraccionArancelaria, pcp.DTA, pcp.IGI, pcp.PermisoImp, pcp.TipoPermiso, NULL FIngreso, pcp.Observa, pcp.Moneda, '' ObservaP, pcp.Estado " +
					" from cotizas, PCotPharma as pcp, Proveedores Prov " +
					" where cotizas.Clave  = pcp.Cotiza and  pcp.Estado in('Por Gestionar', 'Gestionando') and pcp.Folio  =  @partida and Prov.Clave = pcp.Proveedor " +
					" order by 1,4 ";
			//log.info(sQuery);
			return super.jdbcTemplate.query(sQuery,map, new tiempoProcesoRowMapper());			
		}catch (Exception e) {
			//log.info("Erro: " + e.getMessage());
			return null;
		}		
	}
	
	public int obtenerFolioPCotizas(Long idPCotiza){
		//Obtiene el Folio de PCotizas 0, 1 ... 99, 0 no cuenta con Codigo ni fabrica, y lo de 1 ... 99 si los tiene
		String sQuery;
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idPCotiza",idPCotiza);
			sQuery = "SELECT Folio FROM PCotizas WHERE idPCotiza = " + idPCotiza;
			
			//log.info(sQuery);
			return super.queryForInt(sQuery);
			
		}catch (Exception e) {
			//log.info("Erro: " + e.getMessage());
			return 0;
		}	
	}
}