package com.proquifa.net.persistencia.despachos.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.proquifa.net.modelo.comun.util.Funcion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.proquifa.net.modelo.compras.Pieza;
import com.proquifa.net.modelo.comun.Archivo;
import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.ComplementosPago;
import com.proquifa.net.modelo.comun.Direccion;
import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.comun.Empresa;
import com.proquifa.net.modelo.comun.Folio;
import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.ValidarCFDI;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.facturaElectronica.FacturaElectronica;
import com.proquifa.net.modelo.comun.facturaElectronica.PFacturaElectronica;
import com.proquifa.net.modelo.despachos.DocumentoXLS;
import com.proquifa.net.modelo.despachos.EmbalarPedido;
import com.proquifa.net.modelo.despachos.EstadisticaUsuarioEmbalar;
import com.proquifa.net.modelo.despachos.OrdenEntrega;
import com.proquifa.net.modelo.despachos.PRemision;
import com.proquifa.net.modelo.despachos.PackingListJasper;
import com.proquifa.net.modelo.despachos.PartidaInspeccion;
import com.proquifa.net.modelo.despachos.Remision;
import com.proquifa.net.modelo.despachos.TotalEmbalar;
import com.proquifa.net.modelo.despachos.colectarPartidas;
//import com.proquifa.net.persistencia.impl.base.BaseJdbcDAOImpl;
import com.proquifa.net.modelo.despachos.totalesInspeccionProducto;
import com.proquifa.net.modelo.despachos.packinglist.PackingList;
import com.proquifa.net.modelo.despachos.packinglist.PartidaPackingList;
import com.proquifa.net.modelo.despachos.trabajarRuta.TrPackingList;
import com.proquifa.net.modelo.ventas.admoncomunicacion.Correo;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.compras.comprador.mapper.OrdenEntregaRowMapper;
import com.proquifa.net.persistencia.compras.comprador.mapper.OrdenPartidaInspeccionRowMapper;
import com.proquifa.net.persistencia.compras.comprador.mapper.TotalesInspeccionRowMapper;
import com.proquifa.net.persistencia.comun.EmpleadoDAO;
import com.proquifa.net.persistencia.comun.FolioDAO;
import com.proquifa.net.persistencia.comun.FuncionDAO;
import com.proquifa.net.persistencia.despachos.EmbalarDAO;
import com.proquifa.net.persistencia.despachos.impl.mapper.PartidARegresarMapper;
import com.proquifa.net.persistencia.despachos.impl.mapper.PartidaInspeccionRowMapper;
import com.proquifa.net.persistencia.despachos.impl.mapper.PartidaInspeccionxPieza;
import com.proquifa.net.persistencia.despachos.impl.mapper.PartidasParaPackingList;
import com.proquifa.net.persistencia.despachos.impl.mapper.PiezaRowMapper;
import com.proquifa.net.persistencia.despachos.mapper.ObtenerFolioRowMapper;

import net.itsystema.ConvertNumber;

@Repository
public class EmbalarDAOImpl extends DataBaseDAO implements EmbalarDAO {

	@Autowired
	FolioDAO folioDAO;
	@Autowired
	EmpleadoDAO empleadoDAO;
	@Autowired
	FuncionDAO funcionDAO;
	private SimpleDateFormat formatterSinTiempo = new SimpleDateFormat("yyyyMMdd");
	private Integer id=0;
	
	final Logger log = LoggerFactory.getLogger(EmbalarDAOImpl.class);

	@SuppressWarnings("unchecked")
	public List<PartidaInspeccion> finPartidasAInspeccionar(String responsable, String funcion, String compra) {
		String query = "";
		String estado = "";
		String estadoPrevio = "";
		String partidasXCompra = " ";
		try {
			if (funcion.equals("Almacenista de Entradas")) {
				estado = "En inspección";
				estadoPrevio = "En tránsito Matriz";
			} else if (funcion.equals("Almacenista de Entradas PHS")) {
				estado = "En inspección Phs";
				estadoPrevio = "En tránsito PhS-USA";
			}
			if (compra != null && !compra.equals("")) {
				partidasXCompra = " AND cmp.Clave='" + compra + "' ";
			}
			query = "SELECT (CASE WHEN pc.Estado='En inspección Phs' THEN 'Pharma' ELSE 'Matriz' END) AS Inspector,pc.idPCompra,cmp.Clave AS Compra,pdd.FacturFiscalP AS FacturadoPor,pc.Partida AS PartidaPCompra,pp.idPPedido,pp.CPedido,pp.Part AS PartidaPPedido,pdd.Parciales,"
					+ "(CASE WHEN pp.FK02_Producto IS NULL THEN spt.idComplemento ELSE pp.FK02_Producto END) AS idProducto,(CASE WHEN pc.idComplemento>0 THEN spt.Tipo ELSE prod.Tipo END) AS TipoProducto,COALESCE(prod.Control,'No especificado') AS Control,"
					+ "(CASE WHEN prod.Cantidad IS NULL THEN 'NA' ELSE prod.Cantidad+' '+prod.Unidad END) AS Presentacion,"
					+ "(CASE WHEN pc.Estado='En inspección Phs' THEN COALESCE(iphsOC.Documentacion,prod.Documentacion,'No especificado') ELSE COALESCE(iOC.Documentacion,prod.Documentacion,'No especificado') END) AS Documento,"
					+ "(CASE WHEN pc.Estado='En inspección Phs' THEN COALESCE(iphsOC.TipoDocumento,prod.TipoDocumento,'No especificado') ELSE COALESCE(iOC.TipoDocumento,prod.TipoDocumento,'No especificado') END) AS TipoDocumento,"
					+ "(CASE WHEN pc.Estado='En inspección Phs' THEN COALESCE(iphsOC.Lote,pp.Lote,'No especificado') ELSE COALESCE(iOC.Lote,pp.Lote,'No especificado') END) AS Lote,"
					+ "(CASE WHEN pc.Estado='En inspección Phs' THEN COALESCE(iphsOC.MesCaducidad,pp.MesCaducidad,'No especificado') ELSE COALESCE(iOC.MesCaducidad,pp.MesCaducidad,'No especificado') END) AS MesCaducidad,"
					+ "(CASE WHEN pc.Estado='En inspección Phs' THEN COALESCE(iphsOC.AnoCaducidad,pp.AnoCaducidad,'No especificado') ELSE COALESCE(iOC.AnoCaducidad,pp.AnoCaducidad,'No especificado') END) AS AnoCaducidad,"
					+ "(CASE WHEN pc.Estado='En inspección Phs' THEN COALESCE(iphsOC.Edicion,pp.Edicion COLLATE Modern_Spanish_CI_AS,'No especificado') ELSE COALESCE(iOC.Edicion,pp.Edicion COLLATE Modern_Spanish_CI_AS,'No especificado') END) AS Edicion,"
					+ "(CASE WHEN cmp.AlmacenUSA=1 THEN 'PHS-USA' ELSE CASE WHEN cmp.AlmacenMatriz=1 AND prc.Numero>0 THEN 'Ruta Local' ELSE CASE WHEN cmp.AlmacenMatriz=1 THEN 'Proveedor' END END END) AS Origen,"
					+ "(CASE WHEN rchzXD.TotalPendientes>0 THEN 1 ELSE 0 END) AS RechazoXDoc,"
					+ "(CASE WHEN rchzXI.TotalPendientes>0 THEN 1 ELSE 0 END) AS RechazoXIns,"
					+ "(CASE WHEN rchzXDClose.TotalPendientes>0 THEN 'Abierto' ELSE 'Cerrado' END) AS EstadoRechazoXDoc,"
					+ "(CASE WHEN rchzXIClose.TotalPendientes>0 THEN 'Abierto' ELSE 'Cerrado' END) AS EstadoRechazoXIns,"
					+ "pc.Cant,(CASE WHEN PC.idComplemento>0 THEN spt.Manejo ELSE prod.Manejo END) AS Manejo,"
					+ "pr.Clave AS idProveedor,pr.Nombre AS Proveedor,pr.Pais AS PaisProveedor,COALESCE(cl.nombre,'STOCK') AS Cliente,pc.Codigo,pc.Fabrica,pc.idComplemento,"
					+ "pp.FPEntrega AS FEE,pp.Tipo AS TipoFlete,"
					+ "COALESCE(programado.Cuantos,'0') AS partidasProgramadas,"
					+ "COALESCE(regular.Cuantos ,'0') AS partidasRegulares,"
					+ "COALESCE(fex.Cuantos,'0') AS partidasFE,"
					+ "(CASE WHEN pc.Estado='En inspección Phs' THEN COALESCE(iphsOC.revisoPieza,'0') ELSE COALESCE(iOC.revisoPieza,'0') END) AS revisoPieza,"
					+ "(CASE WHEN pc.Estado='En inspección Phs' THEN COALESCE(iphsOC.revisoCaducidad,'0') ELSE COALESCE(iOC.revisoCaducidad,'0') END) AS revisoCaducidad,"
					+ "(CASE WHEN pc.Estado='En inspección Phs' THEN COALESCE(iphsOC.revisoLote,'0') ELSE COALESCE(iOC.revisoLote,'0') END) AS revisoLote,"
					+ "(CASE WHEN pc.Estado='En inspección Phs' THEN COALESCE(iphsOC.revisoDocumento,'0') ELSE COALESCE(iOC.revisoDocumento,'0') END) AS revisoDocumento,"
					+ "(CASE WHEN pc.Estado='En inspección Phs' THEN COALESCE(pPHS.NumPiezas,'0') ELSE COALESCE(pieza.NumPiezas,'0') END) AS partidasEnPieza "
					+ "FROM Pendiente AS pd " + "LEFT JOIN(SELECT * FROM Compras) AS cmp ON cmp.Clave=pd.Docto "
					+ "LEFT JOIN(SELECT * FROM PCompras) AS pc ON pc.Compra=cmp.Clave "
					+ "LEFT JOIN(SELECT * FROM Pedidos) AS pdd ON pdd.CPedido=pc.CPedido LEFT JOIN(SELECT * FROM PPedidos) AS pp ON pp.CPedido=pc.CPedido AND pp.Part=pc.PPedido "
					+ "LEFT JOIN(SELECT * FROM Proveedores) AS pr ON pr.Clave=cmp.Provee LEFT JOIN(SELECT * FROM Clientes) AS cl ON cl.Clave=pdd.idCliente "
					+ "LEFT JOIN(SELECT * FROM Productos) AS prod ON prod.Codigo=pc.Codigo AND prod.Fabrica=pc.Fabrica "
					+ "LEFT JOIN(SELECT Productos.*,Complemento.idComplemento FROM Complemento,Productos WHERE Complemento.Fabrica=Productos.Fabrica AND Complemento.Codigo=Productos.Codigo) AS spt ON spt.idComplemento=pc.idComplemento "
					+ "LEFT JOIN(SELECT * FROM InspeccionOCPhs) AS iphsOC ON iphsOC.idPCompra=pc.idPCompra "
					+ "LEFT JOIN(SELECT * FROM InspeccionOC) AS iOC ON iOC.idPCompra=pc.idPCompra "
					+ "LEFT JOIN(SELECT Count(*) TotalPendientes,Docto FROM Pendiente WHERE (Tipo='Rechazo por documentacion' or Tipo='Rechazo por documentacion PHS-USA') GROUP BY Docto) AS rchzXD ON rchzXD.Docto=pc.idPCompra "
					+ "LEFT JOIN(SELECT Count(*) TotalPendientes,Docto FROM Pendiente WHERE (Tipo='Rechazo por inspeccion' or Tipo='Rechazo por inspeccion PHS-USA') GROUP BY Docto) AS rchzXI ON rchzXI.Docto=pc.idPCompra "
					+ "LEFT JOIN(SELECT Count(*) TotalPendientes,Docto FROM Pendiente WHERE (Tipo='Rechazo por documentacion' or Tipo='Rechazo por documentacion PHS-USA') AND FFin IS NULL GROUP BY Docto) AS rchzXDClose ON rchzXDClose.Docto=pc.idPCompra "
					+ "LEFT JOIN(SELECT Count(*) TotalPendientes,Docto FROM Pendiente WHERE (Tipo='Rechazo por inspeccion' or Tipo='Rechazo por inspeccion PHS-USA') AND FFin IS NULL GROUP BY Docto) AS rchzXIClose ON rchzXIClose.Docto=pc.idPCompra "
					+ "LEFT JOIN(SELECT COUNT(*) AS Numero,Docto FROM Pendiente WHERE Tipo = 'Producto a recoleccion' GROUP BY Docto) AS prc ON prc.Docto=cmp.Clave "
					+ "LEFT JOIN(SELECT COUNT(*) AS Cuantos,CPedido FROM PPedidos WHERE Tipo='Programado' GROUP BY CPedido) AS programado ON programado.CPedido=pp.CPedido "
					+ "LEFT JOIN(SELECT COUNT(*) AS Cuantos,CPedido FROM PPedidos WHERE (Tipo='Regular' OR Tipo IS NULL) GROUP BY CPedido) AS regular ON regular.CPedido=pp.CPedido "
					+ "LEFT JOIN(SELECT COUNT(*) AS Cuantos,CPedido FROM PPedidos WHERE Tipo='Flete Express' GROUP BY CPedido) AS fex ON fex.CPedido=pp.CPedido "
					+ "LEFT JOIN(SELECT COUNT(*) AS NumPiezas,IdPCompra FROM PiezaPHS GROUP BY IdPCompra) AS pPHS ON pPHS.IdPCompra=pc.idPCompra "
					+ "LEFT JOIN(SELECT COUNT(*) AS NumPiezas,IdPCompra FROM Pieza GROUP BY IdPCompra) AS pieza ON pieza.IdPCompra=pc.idPCompra "
					+ "WHERE pd.Tipo='OC a inspeccionar' AND (pc.Estado='" + estado
					+ "' OR pc.Estado='STOCK') AND pd.FFin IS NULL AND pd.Responsable='" + responsable + "'"
					+ partidasXCompra + "ORDER BY pr.Nombre,cmp.Clave ASC";

			List<PartidaInspeccion> lista = super.jdbcTemplate.query(query, new PartidaInspeccionRowMapper());
			for (PartidaInspeccion pIns : lista) {
				// logger.info(pIns.getIdPartidaCompra());
				Date finicio = (Date) super.jdbcTemplate.queryForObject("SELECT FInicio FROM EstadoPCompra WHERE idPCompra=" + pIns.getIdPartidaCompra()
								+ " AND Folio= (SELECT MAX(Folio) FROM EstadoPCompra WHERE idPCompra="
								+ pIns.getIdPartidaCompra() + " AND FFin IS NULL)", new HashMap<String, Object>(), java.util.Date.class);
				String folioInspeccion = (String) super.jdbcTemplate.queryForObject("SELECT TOP 1 FolioInspeccion FROM PCompraFolioInspeccion WHERE idPCompra="
								+ pIns.getIdPartidaCompra() + " AND EstadoPrevio='" + estadoPrevio+ "' ORDER BY FolioInspeccion DESC", new HashMap<String, Object>(), String.class);
				if (funcion.equals("Almacenista de Entradas")) {
					pIns.setFolioInspeccion(folioInspeccion);
				} else if (funcion.equals("Almacenista de Entradas PHS")) {
					pIns.setFolioInspeccionPHS(folioInspeccion);
				}
				pIns.setFechaInicioInspeccion(finicio);
			}
			return lista;
		} catch (Exception e) {
//			logger.error("Error: " + e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public PartidaInspeccion getPCAMonitoreo(Long idPCompra) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String query = "SELECT pc.Compra,pc.idPCompra,ppedidos.idPPedido,'Compra' AS EstadoPrevioPPedido,"
					+ "(CASE WHEN pc.Estado='En inspección Phs' THEN 'En tránsito PhS-USA' ELSE 'En tránsito Matriz' END) AS EstadoPrevioPCompra,"
					+ "(CASE WHEN pc.Estado='En inspección Phs' THEN 'Almacenista de Entradas PHS' ELSE 'Almacenista de Entradas' END) AS FuncionUsuario "
					+ "FROM PCompras AS pc "
					+ "LEFT JOIN(SELECT * FROM Pendiente WHERE (Tipo='Rechazo por documentacion' OR Tipo='Rechazo por inspeccion' OR Tipo='Producto a reclamo' OR Tipo='Reclamo abierto')) AS rechazo ON rechazo.Docto=pc.idPCompra "
					+ "LEFT JOIN(SELECT idPPedido,CPedido,Part FROM PPedidos) AS ppedidos ON ppedidos.CPedido=pc.CPedido AND ppedidos.Part=pc.PPedido "
					+ "WHERE pc.idPCompra=" + idPCompra + " AND rechazo.Folio IS NULL ";
			return (PartidaInspeccion) super.jdbcTemplate.queryForObject(query.toString(), map, new PartidARegresarMapper());
		} catch (Exception e) {
//			logger.error("Error | InspeccionPHSDAOImpl | DAO : " + e.getMessage());
			return null;
		}
	}

	public void eliminarEstadoPCompraI(Long idPCompra) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			super.jdbcTemplate.update("DELETE EstadoPCompra WHERE FFin IS NULL AND idPCompra ='" + idPCompra + "';", map);
		} catch (Exception e) {
			log.info("error: " + e );
		}
		
	}

	public void eliminarEstadoPPedidoI(Long idPPedido) {
		Map<String, Object> map = new HashMap<String, Object>();
		final String query = "DELETE EstadoPPedido WHERE FFin IS NULL AND idPPedido=" + idPPedido;
		super.jdbcTemplate.update(query, map);
	}

	public void eliminarInspeccionOC(Long idPCompra, String funcionEmpleado) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String tabla = "";
			if (funcionEmpleado.equals("Almacenista de Entradas")) {
				tabla = "InspeccionOC";
			} else if (funcionEmpleado.equals("Almacenista de Entradas PHS")) {
				tabla = "InspeccionOCPhs";
			}
			final String query = "DELETE " + tabla + " WHERE idPCompra=" + idPCompra;
			super.jdbcTemplate.update(query,map);
		} catch (Exception e) {
//			logger.error("Error: " + e.getMessage());
		}
	}

	public Boolean cerrarPendienteInspeccionOC(String folioOC, String funcion) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			Boolean cerrarPendiente = true;
			String parametros = "", query = "", estado = "";
			Long idFuncion = this.funcionDAO.getidFuncionXNombre(funcion);
			String empleado = this.funcionDAO.getEmpleadoXIdFuncion(idFuncion);
			if (funcion.equals("Almacenista de Entradas PHS")) {
				parametros = "(Tipo = 'Rechazo por inspeccion PHS-USA' OR Tipo = 'Rechazo por documentacion PHS-USA')";
				estado = "En inspección Phs";
			} else {
				parametros = "(Tipo = 'Rechazo por inspeccion' OR Tipo = 'Rechazo por documentacion')";
				estado = "En inspección";
			}
			query = "SELECT SUM(COALESCE(pend.NumPen,'0')) AS totalPendientes FROM Compras AS c "
					+ "LEFT JOIN(SELECT * FROM PCompras WHERE Estado='" + estado + "') AS pc ON pc.Compra=c.Clave "
					+ "LEFT JOIN(SELECT COUNT(*)AS NumPen,Docto FROM Pendiente WHERE " + parametros
					+ " AND FFin IS NULL GROUP BY Docto) AS pend " + "ON pend.Docto=pc.idPCompra " + "WHERE c.Clave='"
					+ folioOC + "'";
			Integer pendientesXPCompra = (Integer) super.jdbcTemplate.queryForObject(query.toString(), map, Integer.class);
			if (pendientesXPCompra > 0) {
				cerrarPendiente = false;
			}

			if (cerrarPendiente) {
				Date fechaFin = new Date();
				map.put("fechaFin", fechaFin);
				map.put("empleado", empleado);
				map.put("folioOC", folioOC);
//				Object[] params = { fechaFin, empleado, folioOC };
				super.jdbcTemplate.update("UPDATE Pendiente SET FFin= :fechaFin WHERE Tipo='OC a inspeccionar' AND FFin IS NULL AND Responsable= :empleado AND Docto= :folioOC;",map);
				return true;
			} else {
//				logger.error("Existen pendientes de rechazos que aun estan abiertos.");
				return false;
			}
		} catch (Exception e) {
//			logger.error("Error: " + e.getMessage());
			return false;
		}
	}

	public Boolean actualizarInspeccionOC(PartidaInspeccion pIns, String tabla) {
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pedimento", pIns.getPedimento());
			map.put("DisponibilidadPedimento", pIns.getDisponibilidadPedimento());
			map.put("Guia", pIns.getGuia());
			map.put("MesCaducidad", pIns.getMesCaduca());
			map.put("AnoCaducidad", pIns.getAnoCaduca());
			map.put("Manejo", pIns.getManejo());
			map.put("Lote", pIns.getLote());
			map.put("Documentacion", pIns.getDocumento());
			map.put("TipoDocumento", pIns.getTipoDocumento());
			map.put("Comentarios", pIns.getComentarios());
			map.put("Idioma", pIns.getIdioma());
			map.put("Edicion", pIns.getEdicion());
			map.put("revisoPieza", pIns.getRevisoNumPiezas());
			map.put("revisoCaducidad", pIns.getRevisoCaducidad());
			map.put("revisoLote", pIns.getRevisoLote());
			map.put("revisoDocumento", pIns.getRevisoDoc());
			map.put("fecha", pIns.getFechaFinInspeccion());
			map.put("Inspector", pIns.getInspector());
			map.put("idPCompra", pIns.getIdPartidaCompra());

			super.jdbcTemplate.update("UPDATE " + tabla	+ " SET Pedimento= :pedimento,DisponibilidadPedimento= :DisponibilidadPedimento,Guia= :Guia,MesCaducidad=MesCaducidad,AnoCaducidad= :AnoCaducidad,Manejo= :Manejo,Lote= :Lote,"
			+ "Documentacion= :Documentacion,TipoDocumento= :TipoDocumento,Comentarios= :Comentarios,Idioma= :Idioma,Edicion= :Edicion,revisoPieza= :revisoPieza,revisoCaducidad= :revisoCaducidad,revisoLote= :revisoLote,revisoDocumento= :revisoDocumento,Fecha= :fecha,Inspector= :Inspector WHERE idPCompra= :idPCompra",
					map);
			return true;
		} catch (Exception e) {
//			logger.error("Error: " + e.getMessage());
			return false;
		}
	}

	public Long insertarInspeccionOC(PartidaInspeccion pIns, String tabla) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idPartida",pIns.getIdPartidaCompra()); 
			map.put("Pedimento",pIns.getPedimento()); 
			map.put("DisponibilidadPedimento",pIns.getDisponibilidadPedimento());
			map.put("Guia",pIns.getGuia());
			map.put("MesCaducidad",pIns.getMesCaduca()); 
			map.put("AnoCaducidad",pIns.getAnoCaduca()); 
			map.put("Manejo",pIns.getManejo()); 
			map.put("Lote",pIns.getLote());
			map.put("Documentacion",pIns.getDocumento()); 
			map.put("TipoDocumento",pIns.getTipoDocumento()); 
			map.put("Comentarios",pIns.getComentarios()); 
			map.put("Idioma",pIns.getIdioma());
			map.put("Edicion",pIns.getEdicion()); 
			map.put("Fecha",pIns.getFechaFinInspeccion()); 
			map.put("Inspector",pIns.getInspector());
			
			String query = "INSERT INTO " + tabla
					+ " (idPCompra,Pedimento,DisponibilidadPedimento,Guia,MesCaducidad,AnoCaducidad,Manejo,Lote,"
					+ "Documentacion,TipoDocumento,Comentarios,Idioma,Edicion,Fecha,Inspector) VALUES ";
			query += "( :idPartida, :Pedimento, :DisponibilidadPedimento, :Guia, :MesCaducidad, :AnoCaducidad, :Manejo, :Lote, :Documentacion,"
					+" :TipoDocumento, :Comentarios, :Idioma, :Edicion, :Fecha, :Inspector)";
			super.jdbcTemplate.update(query, map);
			Long pInspeccion = super.queryForLong("SELECT IDENT_CURRENT ('" + tabla + "')");
			return pInspeccion;
		} catch (Exception e) {
//			logger.error("Error: " + e.getMessage());
			return -1L;
		}
	}

	public Boolean validarPartidaInspeccionOC(Long idPCompra, String tabla) {
		Boolean existe = false;
		int registros = super.queryForInt("SELECT COUNT(*) AS CUANTOS FROM " + tabla + " WHERE idPCompra=" + idPCompra);
		if (registros > 0) {
			existe = true;
		}
		return existe;
	}

	// ---------------------------------------------------------
	@SuppressWarnings("unused")
	public Long insertarPiezas(String tabla, Pieza pieza) {
		try {
			String certificado = null, FFrente = null, FArriba = null, FABajo = null, FEtiquetaLote = null,
					FEtiquetaCaducidad = null, FEtiquetaCCP = null;
			String tipoDocPrincipal = "";
			if (pieza.getListArchivos() != null) {
				for (Archivo foto : pieza.getListArchivos()) {
					if (foto.getDescripcion().equals("Certificado")
							|| foto.getDescripcion().equals("Hoja de seguridad")) {
						certificado = foto.getFolio();
						tipoDocPrincipal = foto.getDescripcion();
					}
					if (foto.getDescripcion().equals("Foto de arriba")) {
						FArriba = foto.getFolio();
					}
					if (foto.getDescripcion().equals("Foto de abajo")) {
						FABajo = foto.getFolio();
					}
					if (foto.getDescripcion().equals("Foto de frente")) {
						FFrente = foto.getFolio();
					}
					if (foto.getDescripcion().equals("Foto de etiqueta con lote")) {
						FEtiquetaLote = foto.getFolio();
					}
					if (foto.getDescripcion().equals("Foto de etiqueta con caducidad")) {
						FEtiquetaCaducidad = foto.getFolio();
					}
					if (foto.getDescripcion().equals("Foto de etiqueta con catalogo, concepto o presentacion")) {
						FEtiquetaCCP = foto.getFolio();
					}
				}
			}

			// Object[] params =
			// {pieza.getCodigo(),pieza.getFabrica(),pieza.getIdPCompra(),pieza.getRevisoCatalogo(),pieza.getRevisoDescripcion(),pieza.getRevisoEdicion(),
			// pieza.getRevisoIdioma(),pieza.getRevisoFisicamenteC(),pieza.getComentarioRechazo(),pieza.getRevisoPresentacion(),pieza.getRevisoLote(),pieza.getLoteTxt(),
			// pieza.getRevisoCaducidad(),pieza.getMesCaduca(),pieza.getAnoCaduca(),pieza.getRevisoDocumentacion(),tipoDocPrincipal,pieza.getDespachable(),
			// pieza.getAStock(),certificado,FFrente,FArriba,FABajo,FEtiquetaLote,FEtiquetaCaducidad,FEtiquetaCCP};
			// String query = "INSERT INTO "+ tabla +"
			// (Codigo,Fabrica,IdPCompra,Catalogo,Descripcion,Edicion,Idioma,FisicamenteC,Rechazos,Presentacion,Lote,LoteTxt,Caducidad,"+
			// "MesCaducidad,AnoCaducidad,Documentacion,Documento,Despachable,FFrente,FArriba,FABajo,AStock,Documento,FEtiquetaLote,FEtiquetaCaducidad,FEtiquetaCCP)
			// VALUES ";
			// query+= "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Codigo",pieza.getCodigo());
			map.put("Fabrica",pieza.getFabrica()); 
			map.put("IdPCompra",pieza.getIdPCompra()); 
			map.put("Catalogo",pieza.getRevisoCatalogo());
			map.put("Descripcion",pieza.getRevisoDescripcion());
			map.put("Edicion",pieza.getRevisoEdicion()); 
			map.put("Idioma",pieza.getRevisoIdioma());
			map.put("FisicamenteC",pieza.getRevisoFisicamenteC());
			map.put("Rechazos",pieza.getComentarioRechazo()); 
			map.put("Presentacion",pieza.getRevisoPresentacion());
			map.put("Lote",pieza.getRevisoLote()); 
			map.put("LoteTxt",pieza.getLoteTxt()); 
			map.put("Caducidad",pieza.getRevisoCaducidad());
			map.put("MesCaducidad",pieza.getMesCaduca());
			map.put("AnoCaducidad",pieza.getAnoCaduca());
			map.put("Documentacion",pieza.getRevisoDocumentacion()); 
			map.put("Documento",tipoDocPrincipal); 
			map.put("Despachable",pieza.getDespachable());
			map.put("FFrente",FFrente); 
			map.put("FArriba",FArriba); 
			map.put("FABajo",FABajo); 
			map.put("Instrucciones",pieza.getInstrucciones());
			map.put("AStock",pieza.getAStock());
			map.put("ADestruccion",null);
			String query = "INSERT INTO " + tabla
					+ " (Codigo,Fabrica,IdPCompra,Catalogo,Descripcion,Edicion,Idioma,FisicamenteC,Rechazos,Presentacion,Lote,LoteTxt,Caducidad,"
					+ "MesCaducidad,AnoCaducidad,Documentacion,Documento,Despachable,FFrente,FArriba,FABajo,Instrucciones,AStock,ADestruccion) VALUES ";
			query += "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			super.jdbcTemplate.update(query,  map);
			Long idPieza = super.queryForLong("SELECT IDENT_CURRENT ('" + tabla + "')");
			return idPieza;
		} catch (Exception e) {
//			logger.error("Error: " + e.getMessage());
			return -1L;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Pieza> finPiezasPCompra(Long idPCompra, String tabla) throws ProquifaNetException {
		try {
			List<Pieza> piezas = super.jdbcTemplate.query("SELECT * FROM " + tabla + " WHERE IdPCompra=" + idPCompra, new PiezaRowMapper());
			return piezas;
		} catch (Exception e) {
//			logger.error("Error: " + e.getMessage());
			return null;
		}
	}

	// -----------------------------------------------------------
	@SuppressWarnings("unused")
	public Boolean actualizarPieza(Pieza pieza, String tabla) throws ProquifaNetException {
		try {
			String certificado = null, FFrente = null, FArriba = null, FABajo = null, FEtiquetaLote = null,
					FEtiquetaCaducidad = null, FEtiquetaCCP = null;
			String tipoDocPrincipal = "";
			if (pieza.getListArchivos() != null) {
				for (Archivo foto : pieza.getListArchivos()) {
					if (foto.getDescripcion().equals("Certificado")
							|| foto.getDescripcion().equals("Hoja de seguridad")) {
						certificado = foto.getFolio();
						tipoDocPrincipal = foto.getDescripcion();
					}
					if (foto.getDescripcion().equals("Foto de arriba")) {
						FArriba = foto.getFolio();
					}
					if (foto.getDescripcion().equals("Foto de abajo")) {
						FABajo = foto.getFolio();
					}
					if (foto.getDescripcion().equals("Foto de frente")) {
						FFrente = foto.getFolio();
					}
					if (foto.getDescripcion().equals("Foto de etiqueta con lote")) {
						FEtiquetaLote = foto.getFolio();
					}
					if (foto.getDescripcion().equals("Foto de etiqueta con caducidad")) {
						FEtiquetaCaducidad = foto.getFolio();
					}
					if (foto.getDescripcion().equals("Foto de etiqueta con catalogo, concepto o presentacion")) {
						FEtiquetaCCP = foto.getFolio();
					}
				}
			}
			// Object[] params =
			// {pieza.getCodigo(),pieza.getRevisoDescripcion(),pieza.getRevisoEdicion(),pieza.getRevisoIdioma(),pieza.getRevisoFisicamenteC(),pieza.getComentarioRechazo(),
			// pieza.getRevisoPresentacion(),pieza.getRevisoLote(),pieza.getLoteTxt(),pieza.getRevisoCaducidad(),pieza.getMesCaduca(),pieza.getAnoCaduca(),pieza.getRevisoDocumentacion(),tipoDocPrincipal,pieza.getDespachable(),
			// certificado,FFrente,FArriba,FABajo,FEtiquetaLote,FEtiquetaCaducidad,FEtiquetaCCP,pieza.getInstrucciones(),pieza.getAStock(),pieza.getVerificoPieza(),pieza.getIdPieza()};
			// super.jdbcTemplate.update("UPDATE "+ tabla +" SET
			// IdPCompra=?,Catalogo=?,Descripcion=?,Edicion=?,Idioma=?,FisicamenteC=?,Rechazos=?,Presentacion=?,Lote=?,LoteTxt=?,"
			// +
			// "Caducidad=?,MesCaducidad=?,AnoCaducidad=?,Documentacion=?,Documento=?,Despachable=?,Documento=?,FFrente=?,FArriba=?,FAbajo=?,FEtiquetaLote=?,FEtiquetaCaducidad=?,"
			// +
			// "FEtiquetaCCP=?,Instrucciones=?,AStock=?,DespachoSinEditar=?
			// WHERE idPieza=?",params);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Codigo",pieza.getCodigo());
			map.put("Fabrica",pieza.getFabrica());
			map.put("IdPCompra",pieza.getIdPCompra());
			map.put("Catalogo",pieza.getRevisoCatalogo());
			map.put("Descripcion",pieza.getRevisoDescripcion()); 
			map.put("Edicion",pieza.getRevisoEdicion());
			map.put("Idioma",pieza.getRevisoIdioma());
			map.put("FisicamenteC",pieza.getRevisoFisicamenteC()); 
			map.put("Rechazos",pieza.getComentarioRechazo()); 
			map.put("Presentacion",pieza.getRevisoPresentacion());
			map.put("Lote",pieza.getRevisoLote());
			map.put("LoteTxt",pieza.getLoteTxt());
			map.put("Caducidad",pieza.getRevisoCaducidad()); 
			map.put("MesCaducidad",pieza.getMesCaduca());
			map.put("AnoCaducidad",pieza.getAnoCaduca()); 
			map.put("Documentacion",pieza.getRevisoDocumentacion()); 
			map.put("Documento",tipoDocPrincipal); 
			map.put("Despachable",pieza.getDespachable());
			map.put("FFrente",FFrente);
			map.put("FArriba",FArriba); 
			map.put("FAbajo",FABajo);
			map.put("Instrucciones",pieza.getInstrucciones());
			map.put("ADestruccion",pieza.getAStock()); 
			map.put("idPieza",null);
			
			super.jdbcTemplate.update("UPDATE " + tabla + " SET  Codigo= :Codigo,Fabrica= :Fabrica,IdPCompra= :IdPCompra,Catalogo= :Catalogo,Descripcion= :Descripcion,"
					+ "Edicion= :Edicion,Idioma= :Idioma,FisicamenteC= :FisicamenteC, Rechazos= :Rechazos,Presentacion=:Presentacion,Lote= :Lote,LoteTxt= :LoteTxt,Caducidad= :Caducidad,MesCaducidad= :MesCaducidad"
					+ ",AnoCaducidad= :AnoCaducidad,Documentacion= :Documentacion,Documento= :Documento,Despachable= :Despachable,FFrente= :FFrente,FArriba= :FArriba, "
					+ "FAbajo= :FAbajo,Instrucciones= :Instrucciones,AStock= :AStock,ADestruccion= :ADestruccion WHERE idPieza= :idPieza", map);
			// super.jdbcTemplate.update("UPDATE "+ tabla +" SET
			// Codigo='1234567', Fabrica='USPP', IdPCompra='42979',
			// Catalogo='2', Descripcion='2', Edicion='2', Idioma='2',
			// FisicamenteC='2', "+
			// "Rechazos='reporte rechazo2222', Presentacion='2', Lote='2',
			// LoteTxt='I1F315', Caducidad='NULL', MesCaducidad='NULL',
			// AnoCaducidad='NULL', Documentacion='NULL', Documento='NULL',
			// Despachable='2', "+
			// "FFrente='NULL', FArriba='NULL', FAbajo='NULL',
			// Instrucciones='NULL', AStock='1', ADestruccion='NULL', WHERE
			// idPieza='87' ",params);
			return true;
		} catch (Exception e) {
//			logger.error("Error: " + e.getMessage());
			return false;
		}
	}
	
	public String getFolioDocPieza(Long idPieza, String tabla, String documento) throws ProquifaNetException {
		try {
			String columna = "";
			if (documento.equals("Certificado") || documento.equals("Hoja de seguridad")) {
				columna = "Documento";
			}
			if (documento.equals("Foto de arriba")) {
				columna = "FArriba";
			}
			if (documento.equals("Foto de abajo")) {
				columna = "FABajo";
			}
			if (documento.equals("Foto de frente")) {
				columna = "FFrente";
			}
			if (documento.equals("Foto de etiqueta con lote")) {
				columna = "FEtiquetaLote";
			}
			if (documento.equals("Foto de etiqueta con caducidad")) {
				columna = "FEtiquetaCaducidad";
			}
			if (documento.equals("Foto de etiqueta con catalogo, concepto o presentacion")) {
				columna = "FEtiquetaCCP";
			}
			String folio = (String) super.jdbcTemplate.queryForObject("SELECT " + columna + " FROM " + tabla + " WHERE idPieza=" + idPieza, new HashMap<String, Object>(), String.class);
			return folio;
		} catch (Exception e) {
			return null;
		}
	}

	public Boolean actualizarEstadoPCompra(Long idPCompra) throws ProquifaNetException {
		try {
			Date fecha = new Date();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("fecha", fecha);
			map.put("idPCompra", idPCompra);
			super.jdbcTemplate.update("UPDATE EstadoPCompra SET FFin= :fecha WHERE idPCompra= :idPCompra AND FFin IS NULL", map);
			return true;
		} catch (Exception e) {
//			logger.error("Error: " + e.getMessage());
			return null;
		}
	}

	public void insertarPCompraFolioInspeccion(Long idPCompra, String folioInspeccion, String edoPrevio)
			throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idPCompra", idPCompra);
			map.put("folioInspeccion", folioInspeccion);
			map.put("edoPrevio", edoPrevio);
			super.jdbcTemplate.update("INSERT INTO PCompraFolioInspeccion (idPCompra,FolioInspeccion,EstadoPrevio) VALUES (:idPCompra, :folioInspeccion, :edoPrevio)",map);
		} catch (Exception e) {
//			logger.error("Error: " + e.getMessage());
		}
	}

	public Long insertarEstadoPCompra(Long idPCompra, String tipo) throws ProquifaNetException {
		try {
			Date fechaInicio = new Date();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idPCompra", idPCompra);
			map.put("fechaInicio", fechaInicio);
			map.put("tipo", tipo);
			super.jdbcTemplate.update("INSERT INTO EstadoPCompra(idPCompra,FInicio,Tipo) VALUES( :idPCompra, :fechaInicio, :tipo) ", map);
			Long folio = super.queryForLong("SELECT IDENT_CURRENT ('EstadoPCompra')");
			;
			return folio;
		} catch (Exception e) {
//			logger.error("Error: " + e.getMessage());
			return -1L;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PartidaInspeccion> obtenerPartidaEmbalar(String estadoPedido) throws ProquifaNetException {
		try {
			
			StringBuilder sbQuery = new StringBuilder("select * from PartidadeCompraAPorEmbalar where estadoPedido = '").append(estadoPedido + "' and Tipo not like 'Capacitaciones'");
			List<PartidaInspeccion> lstResult = jdbcTemplate.query(sbQuery.toString(),
					new OrdenPartidaInspeccionRowMapper());
			return lstResult;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}

	@Override
	public int generaOrdenEntrega(Folio folio, Long tiempoEmbalaje, String usuario, String zona, String ruta,
			String packing, Integer idCliente) throws ProquifaNetException {
		int clave = 0;
		String sQuery = "";
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			sQuery = "INSERT INTO OrdenEntrega (Folio, Fecha,Tiempo_embalaje,ResEmbalaje,Zona,Ruta,Packing_List,FK01_Cliente) VALUES ('"
					+ folio.getFolioCompleto() + "', GETDATE()," + tiempoEmbalaje + ",'" + usuario + "','" + zona
					+ "','" + ruta + "','" + packing + "'," + idCliente + ")";

//			logger.info(sQuery);
			super.jdbcTemplate.update(sQuery.toString(), map);
			// return true;
			// super.jdbcTemplate.update("INSERT INTO OrdenEntrega (Folio,
			// Fecha,Tiempo_embalaje,ResEmbalaje,Zona,Ruta,Packing_List,FK01_Cliente)
			// VALUES ('" + folio.getFolioCompleto() + "',
			// GETDATE(),"+tiempoEmbalaje+",'"+usuario+"','"+zona+"','"+ruta+"','"+packing+"'"+idCliente+")");
			return super.queryForInt("SELECT IDENT_CURRENT ('OrdenEntrega')");
		} catch (Exception e) {
			return clave;
		}
	}

	@Override
	public Boolean generaPOrdenEntrega(Integer clave, List<PartidaInspeccion> partidas, Long id_bolsa)
			throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			for (Integer i = 0; i < partidas.size(); i++) {
				Long prioridadNum = 0L;
				if (partidas.get(i).getPrioridad().equals("P1"))
					prioridadNum = 1L;
				else if (partidas.get(i).getPrioridad().equals("P2"))
					prioridadNum = 2L;
				else if (partidas.get(i).getPrioridad().equals("P3"))
					prioridadNum = 3L;
				super.jdbcTemplate.update("INSERT INTO POrdenEntrega (FK01_OrdenEntrega, FK02_PPedido,FK03_OrdenEntrega_Empaque,Prioridad) VALUES ("
								+ clave + ", " + partidas.get(i).getIdPPedido() + "," + id_bolsa + "," + prioridadNum + ")", map);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Boolean cambiaEstadoPedidoaPorColectar(ArrayList<Integer> idsPPedidos, String estadoPedido)throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			for (Integer i = 0; i < idsPPedidos.size(); i++) {
				super.jdbcTemplate.update("UPDATE PPedidos SET Estado = '" + estadoPedido + "' WHERE idPPedido = " + idsPPedidos.get(i), map);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Boolean cambiaEstadoPedido(List<PartidaInspeccion> partidas, String estadoPedido)throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			for (Integer i = 0; i < partidas.size(); i++) {
				super.jdbcTemplate.update("UPDATE PPedidos SET Estado = '" + estadoPedido + "' WHERE idPPedido = "+ partidas.get(i).getIdPPedido(), map);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Integer obtenerContadorPartidasAEmbalar(String estadoPedido) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String query = "select COUNT(1) from PartidadeCompraAPorEmbalar where estadoPedido = '" + estadoPedido
					+ "' and Tipo not like 'Capacitaciones'";
			int partidasAEmbalar = super.queryForInt(query.toString(), map);
			return partidasAEmbalar;
		} catch (Exception e) {
//			logger.error(e.getMessage());
			return 0;
		}
	}
	
	

	@Override
	public Long insertarBolsa(String folio, String manejo, int id_ordenEntrega) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			super.jdbcTemplate.update("INSERT INTO OrdenEntrega_Empaque (Folio_bolsa, Manejo,FK01_OrdenEntrega) VALUES ('" + folio
							+ "', '" + manejo + "'," + id_ordenEntrega + ")", map);
			Long idBolsa = super.queryForLong("SELECT IDENT_CURRENT ('OrdenEntrega_Empaque')");
			return idBolsa;
		} catch (Exception e) {
//			logger.error("Error: " + e.getMessage());
			return -1L;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public totalesInspeccionProducto obtenerTotalesEmbalaje(String reEmbalaje, Date iQuincena, Date fQuincena)
			throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			StringBuilder sbQuery = new StringBuilder(
					" SELECT SUM(nio)t_ano,SUM(mes)t_mes, SUM(quincena)t_quincena,SUM(dia)t_dia from ( ");
			sbQuery.append("\n SELECT CASE WHEN PPA.idPPedido IS Not NULL THEN PPA.Cant ELSE 0 END nio,CASE WHEN PPM.idPPedido IS Not NULL THEN PPM.Cant ELSE 0 END mes, CASE WHEN PPQ.idPPedido IS NOT NULL THEN PPQ.Cant ELSE 0 END quincena,");
			sbQuery.append("\n CASE WHEN PPD.idPPedido IS NOT NULL THEN PPD.Cant ELSE 0 END dia ");
			sbQuery.append("\n FROM OrdenEntrega OE ");
			sbQuery.append("\n LEFT JOIN(SELECT FK02_PPedido,FK01_OrdenEntrega FROM POrdenEntrega GROUP BY FK01_OrdenEntrega,FK02_PPedido) AS PO ON PO.FK01_OrdenEntrega= OE.PK_OrdenEntrega ");
			sbQuery.append("\n LEFT JOIN PPedidos PPA ON PPA.idPPedido = PO.FK02_PPedido and YEAR(Fecha) = YEAR(getdate()) ");
			sbQuery.append("\n LEFT JOIN PPedidos PPM ON PPM.idPPedido = PO.FK02_PPedido and MONTH(Fecha) = MONTH(getdate()) AND YEAR(Fecha) = YEAR(getdate()) ");
			sbQuery.append("\n LEFT JOIN PPedidos PPQ ON PPQ.idPPedido = PO.FK02_PPedido and fecha >='")
					.append(formatterSinTiempo.format(iQuincena)).append("' and fecha <= '")
					.append(formatterSinTiempo.format(fQuincena)).append("'");
			sbQuery.append("\n LEFT JOIN PPedidos PPD ON PPD.idPPedido = PO.FK02_PPedido and CAST(Fecha as Date) = cast(GETDATE() as DATE) AND MONTH(Fecha) = MONTH(getdate()) AND YEAR(Fecha) = YEAR(getdate()) ");
			sbQuery.append("\n where PO.FK02_PPedido is not null) P ");

			// log.info(sbQuery.toString());
			return (totalesInspeccionProducto) super.jdbcTemplate.queryForObject(sbQuery.toString(),map, new TotalesInspeccionRowMapper());

		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PartidaInspeccion> obtenerPiezasPorFecha(String tipo, Date fechaI, Date fechaF, String resEmbalaje)
			throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("");
			sbQuery.append(" DECLARE @tipo AS Varchar(max) = '").append(tipo).append("'");
			sbQuery.append("\n SELECT SUM(cant) Piezas ");
			sbQuery.append("\n FROM ( ");
			sbQuery.append("\n SELECT CASE WHEN PO.FK01_OrdenEntrega is not null THEN 1 ELSE 0 END cant");
			sbQuery.append("\n FROM OrdenEntrega OE ");
			sbQuery.append("\n LEFT JOIN POrdenEntrega PO on OE.PK_OrdenEntrega = PO.FK01_OrdenEntrega ");

			if (tipo.equalsIgnoreCase("ano")) {
				sbQuery.append("\n WHERE YEAR(OE.Fecha) = YEAR(getdate())) Pieza ");
			}

			else if (tipo.equalsIgnoreCase("mes")) {
				sbQuery.append(
						"\n WHERE  MONTH(OE.Fecha) = MONTH(getdate()) AND YEAR(OE.Fecha) = YEAR(getdate())) Pieza ");
			}

			else if (tipo.equalsIgnoreCase("quincena")) {
				sbQuery.append("\n WHERE OE.fecha >= '").append(formatterSinTiempo.format(fechaI))
						.append("'  and OE.fecha <= '").append(formatterSinTiempo.format(fechaF)).append("') Pieza");
			}

			else if (tipo.equalsIgnoreCase("dia")) {
				sbQuery.append(
						"\n WHERE CAST(OE.fecha as Date) = cast(GETDATE() as DATE) AND MONTH(OE.Fecha) = MONTH(getdate()) ");
				sbQuery.append("\n AND YEAR(OE.Fecha) = YEAR(getdate()) ) Pieza ");
			} else {
				sbQuery.append("\n WHERE CAST(OE.fecha as Date) = CAST('").append(formatterSinTiempo.format(fechaF)).append("' as Date)) Pieza");
			}

			// log.info(sbQuery.toString());
			List<PartidaInspeccion> lista = super.jdbcTemplate.query(sbQuery.toString(), new PartidaInspeccionxPieza());
			return lista;

		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}

	@Override
	public Long obtenerTotalPartidasEmpaquetadas(String resEmbalaje) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			StringBuilder sbQuery = new StringBuilder("select COUNT(1)PK_OrdenEntrega from OrdenEntrega where YEAR(Fecha) = YEAR(getdate())");
			Long numPartidas = super.queryForLong(sbQuery.toString(), map);
			return numPartidas;
		} catch (Exception e) {
//			logger.error("Error: " + e.getMessage());
			return -1L;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public PartidaInspeccion obtenerIdOrdenCompraDePenultimoEmbalaje() throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("");
			sbQuery.append("SELECT top(1)PK_OrdenEntrega ordenEntrega ,Tiempo_embalaje  FROM OrdenEntrega WHERE Fecha < GETDATE() ORDER BY Fecha DESC");
			// log.info(sbQuery.toString());
			PartidaInspeccionxPieza mapper = new PartidaInspeccionxPieza();
			super.jdbcTemplate.query(sbQuery.toString(), mapper);
			PartidaInspeccion partida = mapper.getPartidaIns();
			return partida;

		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}

	@Override
	public Long obtenerTotalDePiezasPorOrdenEntrega(Long pkOrden) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("");
			sbQuery.append("SELECT PO.FK02_PPedido from OrdenEntrega OE ");
			sbQuery.append("\n LEFT JOIN(SELECT FK02_PPedido,FK01_OrdenEntrega FROM POrdenEntrega WHERE FK01_OrdenEntrega = "
							+ pkOrden
							+ "  GROUP BY FK01_OrdenEntrega,FK02_PPedido) AS PO ON PO.FK01_OrdenEntrega= OE.PK_OrdenEntrega ");
			sbQuery.append("\n WHERE YEAR(Fecha) = YEAR(getdate()) and OE.PK_OrdenEntrega = ").append(pkOrden);
			List<Long> listIdPedidos = super.jdbcTemplate.queryForList(sbQuery.toString(),new HashMap<String, Object>(), Long.class);
			log.info(sbQuery.toString());
			Long cantPza = 0L;
			if (listIdPedidos.size() > 0) {
				for (Long idPP : listIdPedidos) {
					Long cant = (Long) super.jdbcTemplate.queryForObject("SELECT Cant FROM PPedidos WHERE idPPedido =" + idPP,new HashMap<String, Object>(), Long.class);
					cantPza = cantPza + cant;
				}
			}
			return cantPza;

		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PartidaInspeccion> probarobtenerTotalesPorfecha(String tipo, Date fechaI, Date fechaF,
			String resEmbalaje) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("");
			sbQuery.append(" DECLARE @tipo AS Varchar(max) = '").append(tipo).append("'");
			sbQuery.append(
					"\n SELECT OE.PK_OrdenEntrega ordenEntrega,OE.Tiempo_embalaje, PP.Cant Piezas,Tipo_Producto = @tipo,PO.Prioridad,Fecha  FROM OrdenEntrega OE ");
			sbQuery.append(
					"\n LEFT JOIN(SELECT FK02_PPedido,FK01_OrdenEntrega,Prioridad FROM POrdenEntrega GROUP BY FK01_OrdenEntrega,FK02_PPedido,Prioridad) AS PO ON PO.FK01_OrdenEntrega= OE.PK_OrdenEntrega ");
			if (tipo.equalsIgnoreCase("ano")) {
				sbQuery.append(
						"\n LEFT JOIN PPedidos PP ON PP.idPPedido = PO.FK02_PPedido and YEAR(Fecha) = YEAR(getdate()) ");
			} else if (tipo.equalsIgnoreCase("mes")) {
				sbQuery.append(
						"\n LEFT JOIN PPedidos PP ON PP.idPPedido = PO.FK02_PPedido and MONTH(Fecha) = MONTH(getdate()) AND YEAR(Fecha) = YEAR(getdate()) ");
			} else if (tipo.equalsIgnoreCase("quincena")) {
				sbQuery.append("\n LEFT JOIN PPedidos PP ON PP.idPPedido = PO.FK02_PPedido and Fecha >= '")
						.append(formatterSinTiempo.format(fechaI)).append("'  and Fecha <= '")
						.append(formatterSinTiempo.format(fechaF)).append("'");
			} else if (tipo.equalsIgnoreCase("dia")) {
				sbQuery.append(
						"\n LEFT JOIN PPedidos PP ON PP.idPPedido = PO.FK02_PPedido AND  CAST(Fecha as Date) = cast(GETDATE() as DATE) AND MONTH(Fecha) = MONTH(getdate()) ");
				sbQuery.append("AND YEAR( Fecha ) = YEAR(getdate()) ");
			}
			sbQuery.append("\n  where PO.FK02_PPedido is not null ");
			log.info(sbQuery.toString());
			List<PartidaInspeccion> lista = super.jdbcTemplate.query(sbQuery.toString(), new PartidaInspeccionxPieza());
			return lista;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PartidaInspeccion> obtenerPartidasPoridOrdenEntrega(Long idOrdenEntrega) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("");
			sbQuery.append(
					"SELECT PO.PK_POrdenEntrega idOrdenEntrega,PO.FK02_PPedido idPPedido,PP.Factura,Folio_bolsa,PP.Cant,PP.Concepto,PP.Codigo,OEMP.Manejo,PP.CPedido,PE.Pedido FROM OrdenEntrega OE ");
			sbQuery.append("\n LEFT JOIN POrdenEntrega PO ON PO.FK01_OrdenEntrega = OE.PK_OrdenEntrega ");
			sbQuery.append(
					"\n LEFT JOIN OrdenEntrega_Empaque OEMP ON OEMP.PK_OrdenEntrega_Empaque = PO.FK03_OrdenEntrega_Empaque ");
			sbQuery.append("\n LEFT JOIN PPedidos PP ON PP.idPPedido = PO.FK02_PPedido");
			sbQuery.append("\n LEFT JOIN Pedidos PE ON PE.CPedido = PP.CPedido ");
			sbQuery.append("\n WHERE OE.PK_OrdenEntrega =").append(idOrdenEntrega);
			log.info(sbQuery.toString());
			List<PartidaInspeccion> lista = super.jdbcTemplate.query(sbQuery.toString(), new PartidasParaPackingList());
			return lista;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	

	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, List<TotalEmbalar>> obtenerTotalEmbalar() throws ProquifaNetException{
	try{
		StringBuilder sbQuery = new StringBuilder(" \n");
		sbQuery.append(" \n");
		sbQuery.append( "DECLARE @MAXIMO as INT = (SELECT MAX(Maximo.Total)FROM (SELECT SUM(pp.Cant) Total "); 
		sbQuery.append( "FROM PPedidos pp INNER JOIN PCompras pc  ON   pp.idppedido = pc.FK03_PPedido INNER JOIN EmbalarPedido ep ON ep.fk01_PPedido = pp.idPPedido \n");
		sbQuery.append( "WHERE ep.Estado = 'Embalado' AND CAST(PC.FProquifa as Date) < CAST(GETDATE() as Date) \n");
		sbQuery.append( "GROUP BY CAST(PC.FProquifa as Date)) Maximo) \n"); 
		
		sbQuery.append("SELECT null Maximo, null TotalEmbalar, null TotalEmbalada, null MinimoEmbalaje, SUM(Embalar.Monto) Monto, SUM(Embalar.Puntos) Puntos, Embalar.Clave,Embalar.Nombre, Embalar.Prioridad, Embalar.Estado,  \n");
		sbQuery.append(" SUM(Embalar.PorEmbalar) porEmbalar, SUM(Embalar.EmbaladoHoy) embaladoHoy,  \n");
		sbQuery.append(" SUM(Embalar.PorEmbalar) Piezas, \n");
		sbQuery.append(" CASE WHEN Embalar.Tiempo = 'Hoy' THEN COUNT(Embalar.Clave) ELSE NULL END PartidasHoy, \n");
		sbQuery.append(" CASE WHEN Embalar.Tiempo = 'Mañana' THEN COUNT(Embalar.Clave) ELSE NULL END PartidasMañana, \n");
		sbQuery.append(" CASE WHEN Embalar.Tiempo = 'PasadoMañana' THEN COUNT(Embalar.Clave) ELSE NULL END PartidasPMañana, \n");
		sbQuery.append(" CASE WHEN Embalar.Tiempo = 'Futuro' THEN COUNT(Embalar.Clave) ELSE NULL END PartidasFuturo, \n");
		sbQuery.append(" Embalar.Tiempo \n");
		sbQuery.append(" FROM  \n");
		sbQuery.append(" (SELECT SUM(CASE WHEN Total.Estado <> 'Embalado' THEN Total.Cantidad  ElSE 0 END ) PorEmbalar, \n");
		sbQuery.append(" SUM(CASE WHEN Total.Estado  = 'Embalado'AND  CAST(Total.FechaEmbalado AS DATE) = CAST(GETDATE()  AS DATE)  THEN Total.Cantidad ELSE 0 END) EmbaladoHoy, \n");
		sbQuery.append(" Total.IdPPedido, Total.Estado,Total.Clave, Total.Nombre,Total.Prioridad, Total.Puntos,Total.Monto,Total.Cantidad, \n");
		sbQuery.append(" CASE WHEN CAST(Total.FProquifa as Date) <= CAST(GETDATE() as DATE) THEN 'Hoy'  \n");
		sbQuery.append(" WHEN CAST(Total.FProquifa as DATE) =  CAST(DATEADD(day,1,GETDATE()) as DATE)  THEN 'Mañana' \n");
		sbQuery.append(" WHEN CAST(Total.FProquifa as DATE) =  CAST(DATEADD(day,2,GETDATE()) as DATE)  THEN 'PasadoMañana' \n");
		sbQuery.append(" WHEN CAST(Total.FProquifa as DATE) >=  CAST(DATEADD(day,3,GETDATE()) as DATE)  THEN 'PasadoMañana'  \n");
		sbQuery.append(" ELSE 'Hoy' END Tiempo \n");
		sbQuery.append(" FROM  \n");
		sbQuery.append(" (SELECT pp.idPPedido,(pp.Cant) Cantidad, \n");
		sbQuery.append(" CASE WHEN (p.TCambio = 0 or p.TCambio is null) THEN \n");
		sbQuery.append(" CASE WHEN p.Moneda = 'Pesos' THEN ROUND(pp.Costo/mon.PDolar, 0) \n");
		sbQuery.append(" WHEN p.Moneda = 'Dolares' THEN ROUND(pp.Costo,0) \n");
		sbQuery.append(" WHEN p.Moneda = 'Euros' THEN ROUND(pp.Costo*MON.EDolar, 0) END \n");
		sbQuery.append(" ELSE CASE WHEN p.Moneda = 'Pesos' THEN ROUND(pp.Costo/TCambio,0) \n");
		sbQuery.append(" WHEN p.Moneda = 'Dolares' THEN ROUND(pp.Costo,0) \n");
		sbQuery.append(" WHEN p.Moneda = 'Euros' THEN ROUND(pp.Costo*mon.EDolar, 0) END END AS Monto,  \n");
		sbQuery.append(" CASE WHEN pp.Estado = 'Embalado' THEN 'Embalado' ELSE 'Por Embalar' END Estado, pc.FProquifa, pe.Prioridad, pe.Puntos,c.Clave, c.Nombre,  ep.FechaEmbalado  \n" + " \n");
		sbQuery.append(" FROM PPedidos pp \n");
		sbQuery.append(" INNER JOIN Pedidos p on p.CPedido = pp.CPedido  \n");
		sbQuery.append(" INNER JOIN Clientes c on c.Clave = p.idCliente  \n");
		sbQuery.append(" LEFT JOIN   PartidaCompraPorEmbalar pe ON pp.idPPedido = pe.idPPedido \n");
		sbQuery.append(" INNER JOIN PCompras pc on pe.idPCompra = pc.idPCompra \n");
		sbQuery.append(" LEFT JOIN Monedas  mon ON CAST(mon.Fecha as date) = CAST(P.FPedido as DATE)  \n");
		sbQuery.append(" LEFT JOIN EmbalarPEdido  ep ON  pp.idPPedido = ep.FK01_PPEdido \n");
		sbQuery.append(" WHERE (pp.Estado = 'Por embalar' OR pp.Estado = 'A embalar' OR pp.Estado = 'Por colectar'  OR pp.Estado = 'Embalado' OR pp.Estado = 'STOCK') AND (PP.Pausado IS NULL OR PP.Pausado = 0) AND PC.Estado <> 'Cancelada') Total  \n");
		sbQuery.append(" GROUP BY Total.Estado, CAST(Total.FProquifa as DATE), Total.Clave, Total.Nombre, Total.Prioridad , Total.Puntos, Total.Monto, Total.Cantidad, Total.IdPPedido) Embalar  \n");
		sbQuery.append(" GROUP BY Embalar.Nombre,Embalar.Clave,Embalar.Prioridad, Embalar.Tiempo, Embalar.Estado, Embalar.PorEmbalar, Embalar.EmbaladoHoy \n");
		sbQuery.append(" HAVING  (Embalar.Estado = 'Embalado' AND  Embalar.embaladoHoy > 0)  OR  (Embalar.Estado = 'Por Embalar')  \n");
		sbQuery.append( "UNION ALL \n" );
		
		sbQuery.append( "SELECT COALESCE(@MAXIMO,0) Maximo , COALESCE(SUM(CASE WHEN PCE.Estado <> 'Embalado' THEN pp.Cant ELSE 0 END),0) TotalEmbalar, \n");
		sbQuery.append( "COALESCE(SUM(CASE WHEN  ep.Estado = 'Embalado' AND  CAST(ep.FechaEmbalado as Date) = CAST(GETDATE() as Date) THEN pp.Cant ELSE 0 END),0) as Totalembalada, \n" );
		sbQuery.append( "COALESCE(CASE WHEN @MAXIMO/2 = 0 THEN 1 ELSE @MAXIMO/2 END,0) AS MinimoEmbalaje, \n" );
		sbQuery.append( "null,null,null,null,null,null,null, null, null, null,null,null,null, 'Totales'  FROM PPedidos pp  \n" );
		sbQuery.append( "LEFT JOIN PARTIDACompraPorEmbalar PCE  ON pp.idPPedido = PCE.idPPedido \n");
		sbQuery.append( "INNER JOIN PCompras pc  ON   pc.FK03_PPedido = pp.idPPedido   \n" );
		sbQuery.append( "LEFT JOIN EmbalarPedido ep ON ep.fk01_PPedido = pp.idPPedido \n" );
		sbQuery.append( "WHERE (pp.Estado = 'Por embalar' OR pp.Estado = 'A embalar' OR pp.Estado = 'Por colectar' OR pp.Estado = 'Embalado' OR pp.Estado = 'STOCK') AND (PP.Pausado IS NULL OR PP.Pausado = 0) \n" );
		
		Map<String, List<TotalEmbalar>> mapReturn = new HashMap<String, List<TotalEmbalar>>();
	    getJdbcTemplate().query(sbQuery.toString(), new RowMapper() {
	    	   
	    	@Override
	           public Object mapRow(ResultSet rs, int arg1) throws SQLException {
	    		   TotalEmbalar total = new TotalEmbalar();
	    		   String tiempo = rs.getString("Tiempo");
	    		   try{	      				    			   
	    			   if(tiempo.equals("Totales")){
	    				   total.setTotalAEmbalar(rs.getInt("TotalEmbalar"));
		    			   total.setTotalEmbalada(rs.getInt("TotalEmbalada"));
		    			   total.setMaximoVendido(rs.getInt("Maximo"));
		    			   total.setMinimoEmbalar(rs.getInt("MinimoEmbalaje"));
	    			   }else{	    				  
	    				   total.setPiezas(rs.getInt("Piezas"));
	    				   total.setIdCliente(rs.getInt("Clave"));
		    			   total.setNombreCliente(rs.getString("Nombre"));
		    			   total.setMonto(rs.getDouble("Monto"));
		    			   total.setPrioridad(rs.getString("Prioridad"));
		    			   total.setPuntosPrioridad(rs.getInt("Puntos"));
	    				   total.setTotalAEmbalar(rs.getInt("porEmbalar"));
		    			   total.setTotalEmbalada(rs.getInt("embaladoHoy"));
		    			   total.setPartidasHoy(rs.getInt("PartidasHoy"));
		    			   total.setEstado(rs.getString("Estado"));
		    			   total.setPartidasMañana(rs.getInt("PartidasMañana"));
		    			   total.setPartidasPMañana(rs.getInt("PartidasPMañana"));
		    			   total.setPartidasFuturo(rs.getInt("PartidasFuturo"));  
	    			   }
	    		   }catch (Exception e) {
	               }
	    		   if(tiempo.equals("Hoy")){
	    			   if(mapReturn.get("Hoy") != null){
	    				   mapReturn.get("Hoy").add(total);
	    			   }
	    			   else {
		    			   List<TotalEmbalar> list = new ArrayList<TotalEmbalar>();
		    			   mapReturn.put("Hoy", list );
		    			   mapReturn.get("Hoy").add(total);
		    		   }
	    		   } 
	    		   
	    		   if(tiempo.equals("Mañana")){
	    			   if(mapReturn.get("Mañana") != null){
	    				   mapReturn.get("Mañana").add(total);
	    			   } else {
		    			   List<TotalEmbalar> list = new ArrayList<TotalEmbalar>();
		    			   mapReturn.put("Mañana", list );
		    			   mapReturn.get("Mañana").add(total);
		    		   }
	    		   }
	    		   if(tiempo.equals("PasadoMañana")){
	    			   if(mapReturn.get("PasadoMañana") != null){
	    				   mapReturn.get("PasadoMañana").add(total);
	    			   }else {
		    			   List<TotalEmbalar> list = new ArrayList<TotalEmbalar>();
		    			   mapReturn.put("PasadoMañana", list );
		    			   mapReturn.get("PasadoMañana").add(total);
		    		   }	    			   
	    		   } 
	    		   if(tiempo.equals("Futuro")){
	    			   if(mapReturn.get("Futuro") != null){
	    				   mapReturn.get("Futuro").add(total);
	    			   } else {
		    			   List<TotalEmbalar> list = new ArrayList<TotalEmbalar>();
		    			   mapReturn.put("Futuro", list );
		    			   mapReturn.get("Futuro").add(total);
		    		   }
	    		   }
	    		   if(tiempo.equals("Totales")){
	    			   if(mapReturn.get("Totales") != null){
	    				   mapReturn.get("Totales").add(total);
	    			   } else {
		    			   List<TotalEmbalar> list = new ArrayList<TotalEmbalar>();
		    			   mapReturn.put("Totales", list );
		    			   mapReturn.get("Totales").add(total);
		    		   }
	    		   }
	    		   return null;
	    	}    	   
	      });	    
	    return mapReturn; 	 
	} catch (Exception e) {
		e.printStackTrace();
		throw new ProquifaNetException();
	} 	
}
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })	
	public Map<String, List<EstadisticaUsuarioEmbalar>> obtenerEstadisticaUsuario(Empleado  usuario) throws ProquifaNetException{
		try{
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("SELECT 'Quincena' Tiempo, DAY(ep.FechaEmbalado)  dia, COUNT(ep.FK01_PPEdido) TotalPartida, SUM(pp.Cant) TotalPiezas, null Prioridad, null Total    \n");
			sbQuery.append("FROM PPedidos pp   INNER JOIN EmbalarPedido ep ON   pp.idPPedido =  ep.FK01_PPedido \n");
			sbQuery.append("WHERE ep.Estado = 'Embalado'   AND YEAR(ep.FechaEmbalado) = YEAR(GETDATE())  AND MONTH(ep.FechaEmbalado) = MONTH(GETDATE()) AND ep.FK03_UsuarioEmbalar = :idUsuario  \n");
			sbQuery.append("AND CASE WHEN DAY(ep.FechaEmbalado) <= 15 THEN 1 ELSE 2 END = CASE WHEN DAY(GETDATE()) <= 15 THEN 1 ELSE 2 END GROUP BY DAY(ep.FechaEmbalado) \n");
			sbQuery.append("UNION ALL \n");
			sbQuery.append("SELECT 'Mes', DAY(ep.FechaEmbalado) dia, COUNT(pp.IdPPEdido) TotalPartida, SUM(pp.Cant) TotalPiezas, null, null \n");
			sbQuery.append("FROM PPedidos pp   INNER JOIN EmbalarPedido ep ON   pp.idPPedido =  ep.FK01_PPedido \n");
			sbQuery.append("WHERE  ep.Estado = 'Embalado'   AND MONTH(ep.FechaEmbalado ) = MONTH(GETDATE())  AND ep.FK03_UsuarioEmbalar = :idUsuario AND YEAR(ep.FechaEmbalado ) = YEAR(GETDATE()) \n");
			sbQuery.append("GROUP BY DAY(ep.FechaEmbalado)  \n");
			sbQuery.append(" UNION ALL \n");
			sbQuery.append("SELECT 'AllYears', MONTH(ep.FechaEmbalado) Mes, COUNT(pp.IdPPEdido) TotalPartida, SUM(pp.Cant) TotalPiezas,null,null  \n");
			sbQuery.append("FROM PPedidos pp   INNER JOIN EmbalarPedido ep ON   pp.idPPedido =  ep.FK01_PPedido\n");
			sbQuery.append("WHERE  ep.Estado = 'Embalado' AND ep.FK03_UsuarioEmbalar = :idUsuario \n");
			sbQuery.append("GROUP BY MONTH(ep.FechaEmbalado) \n");
			sbQuery.append("UNION ALL \n");
			sbQuery.append("SELECT 'Year', MONTH(ep.FechaEmbalado) Mes, COUNT(pp.IdPPEdido) TotalPartida, SUM(pp.Cant) TotalPiezas,null,null  \n");
			sbQuery.append("FROM PPedidos pp   INNER JOIN   EmbalarPedido ep ON   pp.idPPedido =  ep.FK01_PPedido \n");
			sbQuery.append("WHERE  ep.Estado = 'Embalado' AND YEAR(ep.FechaEmbalado)  = YEAR(GETDATE())  AND ep.FK03_UsuarioEmbalar = :idUsuario \n");
			sbQuery.append("GROUP BY MONTH(ep.FechaEmbalado) \n");
			sbQuery.append("UNION ALL \n");
			sbQuery.append("SELECT 'Prioridad', null, null, SUM(pp.Cant), ep.Prioridad, COUNT(ep.Prioridad)Total FROM EmbalarPedido ep INNER JOIN PPedidos pp ON ep.FK01_PPedido = pp.idPPedido  \n");
			sbQuery.append("WHERE  ep.Estado = 'Embalado'  AND ep.FK03_UsuarioEmbalar = :idUsuario   GROUP BY  ep.Prioridad \n");
			
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("idUsuario", usuario.getIdEmpleado());
			Map<String, List<EstadisticaUsuarioEmbalar>> mapReturn = new HashMap<String, List<EstadisticaUsuarioEmbalar>>();
		    getJdbcTemplate().query(sbQuery.toString(),parametros,new RowMapper() {
		    	@Override
		           public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    		EstadisticaUsuarioEmbalar usuario = new EstadisticaUsuarioEmbalar();
		    		   String tiempo = rs.getString("Tiempo");
		    		   try{	      				    			   
		    			   if(tiempo.equals("Prioridad")){
		    				  usuario.setPrioridad(rs.getString("Prioridad"));
		    				  usuario.setTotalPrioridad(rs.getInt("Total"));
		    				  usuario.setTotalPiezas(rs.getInt("TotalPiezas"));
		    			   }else{
		    				   usuario.setTiempo(rs.getInt("dia"));  
		    				   usuario.setTotalPartidas(rs.getInt("TotalPartida"));
		    				   usuario.setTotalPiezas(rs.getInt("TotalPiezas"));
		    			   }
		    		   }catch (Exception e) {
		               }
		    		   if(tiempo.equals("Quincena")){
		    			   if(mapReturn.get("Quincena") != null){
		    				   mapReturn.get("Quincena").add(usuario);
		    			   }
		    			   else {
			    			   List<EstadisticaUsuarioEmbalar> list = new ArrayList<EstadisticaUsuarioEmbalar>();
			    			   mapReturn.put("Quincena", list );
			    			   mapReturn.get("Quincena").add(usuario);
			    		   }
		    		   } 
		    		   
		    		   if(tiempo.equals("Mes")){
		    			   if(mapReturn.get("Mes") != null){
		    				   mapReturn.get("Mes").add(usuario);
		    			   } else {
			    			   List<EstadisticaUsuarioEmbalar> list = new ArrayList<EstadisticaUsuarioEmbalar>();
			    			   mapReturn.put("Mes", list );
			    			   mapReturn.get("Mes").add(usuario);
			    		   }
		    		   }
		    		   if(tiempo.equals("Year")){
		    			   if(mapReturn.get("Year") != null){
		    				   mapReturn.get("Year").add(usuario);
		    			   }else {
			    			   List<EstadisticaUsuarioEmbalar> list = new ArrayList<EstadisticaUsuarioEmbalar>();
			    			   mapReturn.put("Year", list );
			    			   mapReturn.get("Year").add(usuario);
			    		   }	    			   
		    		   }  
		    		   if(tiempo.equals("AllYears")){
		    			   if(mapReturn.get("AllYears") != null){
		    				   mapReturn.get("AllYears").add(usuario);
		    			   }else {
			    			   List<EstadisticaUsuarioEmbalar> list = new ArrayList<EstadisticaUsuarioEmbalar>();
			    			   mapReturn.put("AllYears", list );
			    			   mapReturn.get("AllYears").add(usuario);
			    		   }	    			   
		    		   } 
		    		   if(tiempo.equals("Prioridad")){
		    			   if(mapReturn.get("Prioridad") != null){
		    				   mapReturn.get("Prioridad").add(usuario);
		    			   } else {
			    			   List<EstadisticaUsuarioEmbalar> list = new ArrayList<EstadisticaUsuarioEmbalar>();
			    			   mapReturn.put("Prioridad", list );
			    			   mapReturn.get("Prioridad").add(usuario);
			    		   }
		    		   }

		    		   return null;
		    	}    	   
		      });	    
		    return mapReturn; 	 
		}catch (Exception e){
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })	
	public Map<String, List<colectarPartidas>> colectarPartidas(Parametro parametro) throws ProquifaNetException{
		try{
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("SELECT SUM(CASE WHEN pe.Prioridad = 'P1' THEN pe.Cant ELSE  0 END) Prioridad1, \n");
			sbQuery.append("SUM(CASE WHEN pe.Prioridad = 'P2' THEN pe.Cant ELSE 0 END) Prioridad2, \n");
			sbQuery.append("SUM(CASE WHEN pe.Prioridad = 'P3' THEN pe.Cant ELSE 0 END) Prioridad3, \n");
			sbQuery.append("null Prioridad, null Tiempo, null 'PartidasCongelacion' , null 'PartidasRefrigeracion', null 'PartidasAmbiente', null 'idProducto', null 'Cliente', null 'Contacto', null 'Puesto', null 'ZonaMensajeria', null 'Ruta', \n");
			sbQuery.append( "null 'Destino', 'TotalPiezasPrioridad' etiqueta, null idCliente, null Cobrador \n");
			sbQuery.append("FROM PartidaCompraPorEmbalar pe   \n");
			sbQuery.append("WHERE (pe.Estado <> 'Embalado' )  \n");
			sbQuery.append("AND CAST(pe.FProquifa as Date) <= CAST(GETDATE() as Date) \n");
			sbQuery.append("UNION ALL \n");
			
			sbQuery.append("SELECT null,null,null, ep.Prioridad,SUM(DATEDIFF(SECOND, ep.Fecha, ep.FechaEmbalado))/ COUNT(ep.PK_EmbalarPedido) /60,null,null,null,null,null,null,null,null,null,null, 'TiempoPrioridad', null idCliente, null Cobrador \n");
			sbQuery.append("FROM EmbalarPedido ep \n");
			sbQuery.append("INNER JOIN PartidaCompraPorEmbalar pe ON  ep.FK01_PPedido = pe.idPPedido GROUP BY ep.Prioridad\n");

				
			sbQuery.append("UNION ALL \n");
			sbQuery.append("SELECT null, null, null,null,null, \n");
			sbQuery.append("COUNT(CASE WHEN pe.Manejo = 'Congelacion' OR pe.Manejo = 'Congelación' THEN pe.idPCompra ELSE null END) PartidasCongelacion, \n");
			sbQuery.append("COUNT(CASE WHEN pe.Manejo = 'Refrigeración' OR pe.Manejo = 'Refrigeracion' THEN pe.idPCompra ELSE null END) PartidasRefrigeracion,  \n");
			sbQuery.append("COUNT(CASE WHEN pe.Manejo = 'Ambiente' OR pe.Manejo = '--NA--' THEN pe.idPCompra ELSE null END) PartidasAmbiente, \n");
			sbQuery.append("null idPPedido, null Cliente, null Contacto, null Puesto, null ZonaMensajeria, null Ruta, null ,  'Cantidades' etiqueta, null idCliente, null Cobrador \n");
			sbQuery.append("FROM  PartidaCompraPorEmbalar pe   \n");
			sbQuery.append("INNER JOIN  EmbalarPedido ep on pe.idPPedido = ep.FK01_PPedido \n");
			if(parametro.getEstado().equalsIgnoreCase("Nuevo")) {
				sbQuery.append("WHERE (pe.Estado <> 'Embalado')  AND CAST(pe.FProquifa as Date) <= CAST(GETDATE() as Date)  AND pe.idPPedido = ( SELECT TOP 1 pe.idPPedido FROM PartidaCompraPorEmbalar pe  \n");
				sbQuery.append("WHERE pe.Puntos = (SELECT  MAX(pe.Puntos) FROM PartidaCompraPorEmbalar pe INNER JOIN PPedidos pp on pp.idPPedido = pe.idPPedido \n");
				sbQuery.append("WHERE pe.idPPedido NOT IN (SELECT ep.FK01_PPedido FROM EmbalarPedido ep  )))  \n");
			}else {
				sbQuery.append("WHERE (pe.Estado <> 'Embalado' AND ep.Estado <> 'Embalado') AND ep.FK03_UsuarioEmbalar = :idUsuario \n");
			}
			sbQuery.append("UNION ALL \n");
			sbQuery.append("SELECT TOP 1 pe.CondicionAlmacenaje, null, null, C.Vendedor COLLATE Modern_Spanish_CI_AS, pe.horario, null, null, null, pe.idPPedido, pe.Cliente, pe.Contacto, pe.Puesto, pe.ZonaMensajeria,pe.Ruta, pe.Destino,  'Cliente', pe.idCliente, E.usuario Cobrador \n");
			sbQuery.append("FROM  PartidaCompraPorEmbalar pe \n");
			sbQuery.append("LEFT JOIN  EmbalarPedido ep on pe.idPPedido = ep.FK01_PPedido\n");
			sbQuery.append("INNER JOIN CLIENTES C ON C.Clave = pe.idCliente \n");
			sbQuery.append("LEFT JOIN EMPLEADOS E ON E.CLAVE = C.Cobrador \n");
			if(parametro.getEstado().equalsIgnoreCase("Nuevo")) {
				sbQuery.append("WHERE pe.Prioridad = 'P1' \n");
				sbQuery.append("AND  pe.idPPedido = (SELECT TOP 1 pe.idPPedido FROM PartidaCompraPorEmbalar pe  \n");
				sbQuery.append("WHERE pe.Puntos = (SELECT  MAX(pe.Puntos) FROM PartidaCompraPorEmbalar pe INNER JOIN PPedidos pp on pp.idPPedido = pe.idPPedido  \n");
				sbQuery.append("WHERE pe.idPPedido NOT IN (SELECT ep.FK01_PPedido FROM EmbalarPedido ep  )))  \n");
				sbQuery.append("GROUP BY pe.Cliente, pe.Contacto, pe.Puesto, pe.ZonaMensajeria, pe.Ruta, pe.Manejo, pe.idProducto,ep.Tiempo, pe.idPPedido, pe.Destino, pe.Horario, C.Vendedor, E.usuario \n");
			}else {
				sbQuery.append("WHERE ep.FK03_UsuarioEmbalar = :idUsuario AND ep.Estado <> 'Embalado' \n");
				sbQuery.append("GROUP BY pe.Cliente, pe.Contacto, pe.Puesto, pe.ZonaMensajeria, pe.Ruta,pe.Manejo, pe.idProducto, pe.idPPedido, pe.Destino, pe.Horario, pe.CondicionAlmacenaje, pe.idCliente, C.Vendedor, E.usuario \n");				
			}
			
			log.info(sbQuery.toString());
			
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("idUsuario", parametro.getIdUsuarioLogueado());
			Map<String, List<colectarPartidas>> mapReturn = new HashMap<String, List<colectarPartidas>>();
		    getJdbcTemplate().query(sbQuery.toString(), parametros,new RowMapper() {
		    	@Override
		           public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    		colectarPartidas partida = new colectarPartidas();
		    		   String tiempo = rs.getString("etiqueta");
		    		   try{	      				    			   
		    			   if(tiempo.equals("Cantidades")){
		    				  partida.setNumPartidasCongelacion(rs.getInt("PartidasCongelacion"));
		    				  partida.setNumPartidasRefrigeracion(rs.getInt("PartidasRefrigeracion"));
		    				  partida.setNumPartidasAmbiente(rs.getInt("PartidasAmbiente"));		    				 
		    			   }else if(tiempo.equals("TotalPiezasPrioridad")){
			    				  partida.setNumPiezasP1(rs.getInt("Prioridad1"));
			    				  partida.setNumPiezasp2(rs.getInt("Prioridad2"));
			    				  partida.setNumPiezasP3(rs.getInt("Prioridad3"));
		    			   }else if(tiempo.equals("Cliente")){
			    				  partida.setIdPedido(rs.getInt("idProducto"));
			    				  partida.setCliente(rs.getString("Cliente"));
			    				  partida.setContacto(rs.getString("Contacto"));
			    				  partida.setPuesto(rs.getString("Puesto"));
			    				  partida.setZonaMensajeria(rs.getString("ZonaMensajeria"));
			    				  partida.setRuta(rs.getString("Ruta"));
			    				  partida.setDestino(rs.getString("Destino"));
			    				  partida.setIdHorario(rs.getInt("Tiempo"));
			    				  partida.setCondicionAlmacenaje(rs.getInt("Prioridad1"));
			    				  partida.setIdCliente(rs.getInt("idCliente"));
			    				  partida.setPrioridad(rs.getString("Prioridad"));
			    				  partida.setCobrador(rs.getString("Cobrador"));
			    			   }else if (tiempo.equals("TiempoPrioridad")) {
			    				   partida.setTiempo(rs.getInt("Tiempo"));
			    				   partida.setPrioridad(rs.getString("Prioridad"));			    				   
			    			   }
		    			  
		    		   }catch (Exception e) {
		               }
		    		   if(tiempo.equals("Cantidades")){
		    			   if(mapReturn.get("Cantidades") != null){
		    				   mapReturn.get("Cantidades").add(partida);
		    			   }
		    			   else {
			    			   List<colectarPartidas> list = new ArrayList<colectarPartidas>();
			    			   mapReturn.put("Cantidades", list );
			    			   mapReturn.get("Cantidades").add(partida);
			    		   }
		    		   } 		    		   		  
		    		   if(tiempo.equals("Cliente")){
		    			   if(mapReturn.get("Cliente") != null){
		    				   mapReturn.get("Cliente").add(partida);
		    			   } else {
			    			   List<colectarPartidas> list = new ArrayList<colectarPartidas>();
			    			   mapReturn.put("Cliente", list );
			    			   mapReturn.get("Cliente").add(partida);
			    		   }
		    		   }
		    		   if(tiempo.equals("TotalPiezasPrioridad")){
		    			   if(mapReturn.get("PiezasPrioridad") != null){
		    				   mapReturn.get("PiezasPrioridad").add(partida);
		    			   } else {
			    			   List<colectarPartidas> list = new ArrayList<colectarPartidas>();
			    			   mapReturn.put("PiezasPrioridad", list );
			    			   mapReturn.get("PiezasPrioridad").add(partida);
			    		   }
		    		   }
		    		   if(tiempo.equals("TiempoPrioridad")){
		    			   if(mapReturn.get("TiempoPrioridad") != null){
		    				   mapReturn.get("TiempoPrioridad").add(partida);
		    			   } else {
			    			   List<colectarPartidas> list = new ArrayList<colectarPartidas>();
			    			   mapReturn.put("TiempoPrioridad", list );
			    			   mapReturn.get("TiempoPrioridad").add(partida);
			    		   }
		    		   }
		    		   return null;
		    	}    	   
		      });	    
		    return mapReturn; 	
			
		}catch (Exception e){
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	
	}
	
	
	
	
	@Override
	public List <EmbalarPedido> obtenerFolio(Integer idUsuario ) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("SELECT pe.FolioEtiqueta, pc.BolsaInspeccion, CASE WHEN pe.CondicionAlmacenaje = 0 THEN pe.Manejo_Transporte ELSE \n");
			sbQuery.append("CASE WHEN pe.Manejo = 'Congelación' THEN 'Congelación' \n"); 
			sbQuery.append("\t WHEN pe.Manejo = 'Refrigeracion' OR pe.Manejo = 'Refrigeración' THEN 'Refrigeración'\n"); 
			sbQuery.append("\t ELSE 'Ambiente'\n");
			sbQuery.append("END COLLATE Modern_Spanish_CS_AS END AS Manejo, \n"); 
			sbQuery.append("pp.idPPedido, pe.Cant, ep.PK_EmbalarPedido, ep.FolioTemporal, insp.videoPartida, pe.Remisionar, CAST(pe.ComentariosEntrega as VARCHAR(MAX)), pe.CondicionAlmacenaje, pe.ComentariosEntrega, \n");
			sbQuery.append("CONVERT( VARCHAR , pe.FPEntrega , 105 ) AS fee, PED.idPedido, CASE WHEN PED.GuiaxCliente = 1 AND PEC.PK_Folio IS NULL THEN 1 WHEN PED.GuiaxCliente = 1 AND PEC.PK_Folio IS NOT NULL THEN 2 ELSE 0 END GuiaCliente \n");
			sbQuery.append("FROM PartidaCompraPorEmbalar pe  \n");
			sbQuery.append("INNER JOIN EmbalarPedido ep  ON  pe.idPPedido = ep.FK01_PPedido \n"); 
			sbQuery.append("INNER JOIN  PPEdidos pp ON ep.FK01_PPedido =  pp.idPPedido \n"); 
			sbQuery.append("INNER JOIN  PCompras pc ON pe.idPCompra = pc.idPCompra AND PC.Estado <> 'Cancelada' \n"); 
			sbQuery.append("LEFT JOIN  InspeccionOC insp ON pc.idPCompra = insp.idPCompra  \n");
			sbQuery.append("LEFT JOIN Pedidos PED ON PEd.CPedido = PE.CPedido \n");
			sbQuery.append("LEFT JOIN Pedido_EnvioXCliente PEC ON PEC.FK02_Pedido = PED.idPedido \n");
			sbQuery.append("WHERE pe.Estado = 'A Embalar' AND ep.FK03_UsuarioEmbalar = :idUsuario \n");
			sbQuery.append("GROUP BY pe.FolioEtiqueta, pc.BolsaInspeccion, pe.Manejo_Transporte, pp.idPPedido, pe.Cant, ep.PK_EmbalarPedido, ep.FolioTemporal, insp.videoPartida, \n"); 
			sbQuery.append("pe.Remisionar, CAST(pe.ComentariosEntrega as VARCHAR(MAX)), CondicionAlmacenaje, pe.Manejo, pe.FPEntrega, PED.idPedido, CASE WHEN PED.GuiaxCliente = 1 AND PEC.PK_Folio IS NULL THEN 1 WHEN PED.GuiaxCliente = 1 AND PEC.PK_Folio IS NOT NULL THEN 2 ELSE 0 END \n");
			
			log.info(sbQuery.toString());
			
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("idUsuario", idUsuario);
			 List <EmbalarPedido> ltsResult =  jdbcTemplate.query(sbQuery.toString(),parametros, new ObtenerFolioRowMapper());			
			 return ltsResult;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	
	

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, Integer> numeroCopias(Integer idUsuario, String estado) throws ProquifaNetException {
		Map<String, Integer> mapReturn = new HashMap<String, Integer>();
		try {
			StringBuilder sbQuery = new StringBuilder("SELECT \n");
			sbQuery.append("CASE WHEN  Entrega.Copia_Pedido = 1  THEN Entrega.NumCopias_Pedidos  ELSE 0 END NumCopiasPedido, \n");
			sbQuery.append("CASE WHEN  Entrega.Copia_Factura = 1  THEN Entrega.NumCopias_Factura  ELSE 0 END NumCopiasFac \n");
			sbQuery.append("FROM EmbalarPedido EmbaP \n");
			sbQuery.append("LEFT JOIN Ppedidos PP ON PP.idPPedido = EmbaP.FK01_PPedido \n");
			sbQuery.append("LEFT JOIN Pedidos Ped ON Ped.CPedido = PP.CPedido \n");
			sbQuery.append("LEFT JOIN Entrega ON Entrega.FK01_Cliente = Ped.idCliente \n");
			sbQuery.append("INNER JOIN PartidaCompraPorEmbalar PCP ON PCP.idPPedido = PP.idPPedido \n");
			sbQuery.append("LEFT JOIN PCompras PC ON PC.idPCompra = PCP.idPcompra \n");
			sbQuery.append("LEFT JOIN InspeccionOC IOC ON IOC.idPCompra = PC.idPCompra WHERE EmbaP.FK03_UsuarioEmbalar = :idUsuario AND EmbaP.Estado = :estado \n");
			sbQuery.append("GROUP BY Entrega.NumCopias_Factura, Entrega.Copia_Pedido, Entrega.NumCopias_Pedidos, Entrega.Copia_Factura");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idUsuario", idUsuario);
			map.put("estado", estado);
			jdbcTemplate.query(sbQuery.toString(), map,new RowMapper() {
		    	@Override
		           public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    		mapReturn.put("numCopiasFactura", rs.getInt("NumCopiasFac"));
		    		mapReturn.put("numCopiasPedido", rs.getInt("NumCopiasPedido"));
		    		return null;
		     	}    	   
		      });	
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return mapReturn;
		// return null;
	}
	
	@Override
	public Map<String, List<DocumentoXLS>> generarDocumentos(Parametro parametro) throws ProquifaNetException{
		try{
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("SELECT pe.idProducto,pe.lote, p.codigo, p.Fabrica, pe.idFabricante, null idPedido , null Cpedido, null idCliente, null idPackingList, null Folio, null Fpor, null Factura, 'Certificado' Etiqueta \n"); 
			sbQuery.append("FROM   EmbalarPedido ep   \n");
			sbQuery.append("INNER JOIN PartidaCompraPorEmbalar pe ON ep.FK01_PPedido = pe.idPPedido \n");  
			sbQuery.append("INNER JOIN PPedidos pp ON ep.FK01_PPedido = pp.idPPedido  \n");
			sbQuery.append("INNER JOIN Productos p ON pp.FK02_Producto = p.idProducto \n");
			sbQuery.append("LEFT JOIN Entrega ET ON ET.FK01_Cliente = PE.idCliente \n");
			sbQuery.append("INNER JOIN InspeccionOC OC ON OC.idPCompra = PE.idPCompra \n");
			sbQuery.append("WHERE ep.Estado = :Estado AND ep.FK03_UsuarioEmbalar = :idUsuario \n");
			sbQuery.append("AND (OC.AplicaDocumentacion IS NULL OR OC.AplicaDocumentacion = 1) \n");
			sbQuery.append("AND CASE WHEN P.PRoveedor = 44 OR P.PRoveedor = 45 THEN ET.Certificados ELSE 1 END = 1  \n");
			sbQuery.append("UNION ALL \n");
			sbQuery.append("SELECT pe.idProducto,pe.lote, p.codigo, p.Fabrica, pe.idFabricante, null idPedido , null Cpedido, null idCliente, null idPackingList, null Folio, null Fpor, null Factura, 'Hoja' Etiqueta \n"); 
			sbQuery.append("FROM   EmbalarPedido ep   \n");
			sbQuery.append("INNER JOIN PartidaCompraPorEmbalar pe ON ep.FK01_PPedido = pe.idPPedido \n");  
			sbQuery.append("INNER JOIN PPedidos pp ON ep.FK01_PPedido = pp.idPPedido  \n");
			sbQuery.append("INNER JOIN Productos p ON pp.FK02_Producto = p.idProducto \n");
			sbQuery.append("LEFT JOIN Entrega ET ON ET.FK01_Cliente = PE.idCliente \n");
			sbQuery.append("INNER JOIN InspeccionOC OC ON OC.idPCompra = PE.idPCompra \n");
			sbQuery.append("WHERE ep.Estado = :Estado AND ep.FK03_UsuarioEmbalar = :idUsuario \n");
			sbQuery.append("AND (OC.AplicaDocumentacion IS NULL OR OC.AplicaDocumentacion = 1) \n");
			sbQuery.append("AND CASE WHEN P.PRoveedor = 44 OR P.PRoveedor = 45 THEN ET.Hojas_Seguridad ELSE 1 END = 1  \n");
			sbQuery.append("UNION ALL \n");
			sbQuery.append("SELECT null,null, null, null, null,pc.idPPedido, pc.Cpedido,  pc.idCliente, null, null, null, null, 'Pedido'\n"); 
			sbQuery.append("FROM PPedidos  pp  \n");
			sbQuery.append("INNER JOIN EmbalarPedido ep ON pp.idPPedido =  ep.FK01_PPedido \n");
			sbQuery.append("INNER JOIN  PartidaCompraPorEmbalar pc ON   ep.FK01_PPedido = pc.idPPedido \n");
			sbQuery.append("WHERE ep.Estado = :Estado AND ep.FK03_UsuarioEmbalar = :idUsuario \n");
			sbQuery.append("GROUP BY pc.idPPedido, pc.Cpedido,  pc.idCliente \n");
			sbQuery.append("UNION ALL  \n");
			sbQuery.append("SELECT null, null, null, null, null, null, null, null, pl.PK_PackingList, pl.Folio, null, null, 'PackingList' etiqueta \n");  
			sbQuery.append("FROM PackingList pl \n");  
			sbQuery.append("INNER JOIN  PPackingList ppl ON  pl.PK_PackingList = ppl.FK01_PackingList \n"); 
			sbQuery.append("INNER JOIN EmbalarPedido ep  ON  ppl.FK02_EmbalarPedido = ep.PK_EmbalarPedido  \n");
			sbQuery.append("WHERE ep.Estado = :Estado AND ep.FK03_UsuarioEmbalar = :idUsuario GROUP BY pl.PK_PackingList, pl.Folio  \n");
			sbQuery.append("UNION ALL  \n");
			sbQuery.append("SELECT null, null, null, null, null, null, null, null, pl.PK_PackingList, REPLACE (pl.Folio, 'PL', 'RE') AS Folio, null, null, 'RegistroEntregaControlado' AS etiqueta \n");  
			sbQuery.append("FROM PackingList pl \n");  
			sbQuery.append("INNER JOIN  PPackingList ppl ON  pl.PK_PackingList = ppl.FK01_PackingList \n"); 
			sbQuery.append("INNER JOIN EmbalarPedido ep  ON  ppl.FK02_EmbalarPedido = ep.PK_EmbalarPedido  \n");
			sbQuery.append("INNER JOIN Productos AS Prod ON Prod.idProducto = EP.FK02_Producto \n");
			sbQuery.append("WHERE ep.Estado = :Estado AND ep.FK03_UsuarioEmbalar = :idUsuario AND Prod.Control IN ('Mundiales', 'Nacionales') GROUP BY pl.PK_PackingList, pl.Folio  \n");
			sbQuery.append("UNION ALL \n");
			sbQuery.append("SELECT null, null, null, null, null, null, null, null,null, null, f.Fpor, f.Factura, 'Factura' etiqueta  \n"); 
			sbQuery.append("FROM EmbalarPEdido ep \n");
			sbQuery.append("INNER JOIN   PPackingList pp ON  ep.pk_embalarPEdido = pp.FK02_EmbalarPEdido \n");
			sbQuery.append("INNER JOIN   Factura_FELectronica  fe ON pp.FK03_FacturaElectronica  =  fe.FK02_FacturaElectronica \n");
			sbQuery.append("INNER JOIN  facturas f ON fe.fk01_Factura = f.idFactura \n");
			sbQuery.append("WHERE ep.Estado = :Estado AND ep.FK03_UsuarioEmbalar = :idUsuario GROUP BY f.Fpor, f.Factura\n");			
			sbQuery.append("UNION \n");			
			sbQuery.append("SELECT null, null, null, null, null, null, null, null,null, null, pf.Fpor, pf.Factura AS Factura, 'Factura' etiqueta\n");
			sbQuery.append("FROM EmbalarPEdido ep \n");
			sbQuery.append("INNER JOIN   PPackingList pp ON  ep.pk_embalarPEdido = pp.FK02_EmbalarPEdido\n");  
			sbQuery.append("INNER JOIN   PPedidos PPE  ON ep.FK01_PPedido = PPE.idPPedido\n");
			sbQuery.append("INNER JOIN  (SELECT PF.* FROM Facturas F INNER JOIN PFacturas PF ON F.Factura = PF.Factura AND F.FPor = PF.Fpor WHERE F.Estado IS NULL OR (F.Estado <> 'Por Cancelar' AND F.Estado <> 'Cancelada')) pf  ON  PPE.CPedido  = pf.CPedido  AND  PPE.Part = pf.PPedido\n");
			sbQuery.append("WHERE ep.Estado = :Estado AND ep.FK03_UsuarioEmbalar = :idUsuario AND (PF.Estado IS NULL OR (PF.Estado <> 'A cancelar' AND PF.Estado <> 'Cancelada') ) \n");
			sbQuery.append("GROUP BY  PF.Factura, PF.Estado, pf.Fpor \n");
			
			sbQuery.append("UNION \n");
			sbQuery.append("SELECT null, null, null, null, null, null, null, null,null, null, RE.Fpor, RE.Factura AS Factura, 'Remision' etiqueta \n");
			sbQuery.append("FROM EmbalarPEdido EP \n");
			sbQuery.append("INNER JOIN PPAckingList PPL ON PPl.FK02_EmbalarPEdido = EP.PK_EmbalarPEdido AND FK04_Remision IS NOT NULL \n");
			sbQuery.append("INNER JOIN Remisiones as RE ON RE.pK_Remision = PPL.FK04_Remision \n");
			sbQuery.append("WHERE ep.Estado = :Estado AND ep.FK03_UsuarioEmbalar = :idUsuario \n");
			
			sbQuery.append("UNION \n");
			sbQuery.append("SELECT NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, PF.Fpor, PF.Factura, 'Evidencia' \n"); 
			sbQuery.append("FROM EMbalarPedido EP  \n");
			sbQuery.append("INNER JOIN PPedidos PP ON PP.idPPedido = EP.FK01_PPedido \n");
			sbQuery.append("INNER JOIN Pedidos P ON PP.CPEdido = P.CPEdido \n");
			sbQuery.append("INNER JOIN (SELECT PF.*, F.Moneda, F.UUID, F.EvidenciaFac FROM Facturas F INNER JOIN PFacturas PF ON F.Factura = PF.Factura AND F.FPor = PF.Fpor WHERE F.Estado IS NULL OR (F.Estado <> 'Por Cancelar' AND F.Estado <> 'Cancelada')) AS PF ON PP.cpedido  = PF.CPEdido AND PP.PArt = PF.PPedido \n");
			sbQuery.append("WHERE EP.FK03_UsuarioEmbalar = :idUsuario AND EP.EStado = :Estado  \n");
			sbQuery.append("AND PF.EvidenciaFac = 1 \n");
			sbQuery.append("GROUP BY PF.Fpor, PF.Factura \n");		
			
			log.info(sbQuery.toString());

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("idUsuario", parametro.getIdUsuarioLogueado());
			parametros.put("Estado", parametro.getEstado());
			
			Map<String, List<DocumentoXLS>> mapReturn = new HashMap<String, List<DocumentoXLS>>();
			getJdbcTemplate().query(sbQuery.toString(), parametros,new RowMapper<DocumentoXLS>() {
		    	@Override
		           public DocumentoXLS mapRow(ResultSet rs, int arg1) throws SQLException {
		    		DocumentoXLS documento = new DocumentoXLS();
		    		   String etiqueta = rs.getString("etiqueta");
		    		   try{	      				    			   
		    			   if(etiqueta.equals("Certificado") || etiqueta.equals("Hoja")){
		    				   documento.setIdProducto(rs.getInt("idProducto")); 
		    				   documento.setLote(rs.getString("lote"));
		    				   documento.setCodigo(rs.getString("codigo"));
		    				   documento.setFabrica(rs.getString("Fabrica"));
		    				   documento.setIdFabricante(rs.getInt("idFabricante"));
		    			   }else if(etiqueta.equals("Pedido")){
		    				   documento.setIdPPedido(rs.getInt("idPedido"));
		    				   documento.setCpedido(rs.getString("Cpedido"));
		    				   documento.setIdCliente(rs.getInt("idCliente"));
		    			   }else if(etiqueta.equals("PackingList")){
		    				   documento.setIdPackingList(rs.getInt("idPackingList"));
		    				   documento.setFolio(rs.getString("Folio"));
		    			   }else if(etiqueta.equals("RegistroEntregaControlado")){
		    				   documento.setIdPackingList(rs.getInt("idPackingList"));
		    				   documento.setFolio(rs.getString("Folio"));
		    			   }else if(etiqueta.equals("Remision")){
		    				   documento.setNumeroFactura(rs.getString("Factura"));
		    				   documento.setFacturadoPor(rs.getString("Fpor"));
		    			   }else if(etiqueta.equals("Evidencia")){
		    				   documento.setNumeroFactura(rs.getString("Factura"));
		    				   documento.setFacturadoPor(rs.getString("Fpor"));
		    			   }else if(etiqueta.equals("Factura")) {
		    				   documento.setNumeroFactura(rs.getString("Factura"));
		    				   documento.setFacturadoPor(rs.getString("Fpor"));
		    			   }   
		    			   
		    			   if(etiqueta.equals("Factura")){
		    				   if(mapReturn.get("Factura") != null){
		    					   mapReturn.get("Factura").add(documento);
		    				   } else {
		    					   List<DocumentoXLS> list = new ArrayList<DocumentoXLS>();
		    					   mapReturn.put("Factura", list );
		    					   mapReturn.get("Factura").add(documento);
		    				   }
		    			   }
		    			   
		    			   if(etiqueta.equals("Remision")){
		    				   if(mapReturn.get("Remision") != null){
		    					   mapReturn.get("Remision").add(documento);
		    				   } else {
		    					   List<DocumentoXLS> list = new ArrayList<DocumentoXLS>();
		    					   mapReturn.put("Remision", list );
		    					   mapReturn.get("Remision").add(documento);
		    				   }
		    			   }

		    		   }catch (Exception e) {
		               }
		    		   if(etiqueta.equals("Certificado")){
		    			   if(mapReturn.get("Certificado") != null){
		    				   mapReturn.get("Certificado").add(documento);
		    			   }
		    			   else {
			    			   List<DocumentoXLS> list = new ArrayList<DocumentoXLS>();
			    			   mapReturn.put("Certificado", list );
			    			   mapReturn.get("Certificado").add(documento);
			    		   }
		    		   } 		    		   		  
		    		   if(etiqueta.equals("Hoja")){
		    			   if(mapReturn.get("Hoja") != null){
		    				   mapReturn.get("Hoja").add(documento);
		    			   }
		    			   else {
		    				   List<DocumentoXLS> list = new ArrayList<DocumentoXLS>();
		    				   mapReturn.put("Hoja", list );
		    				   mapReturn.get("Hoja").add(documento);
		    			   }
		    		   } 		    		   		  
		    		   if(etiqueta.equals("Pedido")){
		    			   if(mapReturn.get("Pedido") != null){
		    				   mapReturn.get("Pedido").add(documento);
		    			   } else {
			    			   List<DocumentoXLS> list = new ArrayList<DocumentoXLS>();
			    			   mapReturn.put("Pedido", list );
			    			   mapReturn.get("Pedido").add(documento);
			    		   }
		    		   }
		    		   if(etiqueta.equals("PackingList")){
		    			   if(mapReturn.get("PackingList") != null){
		    				   mapReturn.get("PackingList").add(documento);
		    			   } else {
			    			   List<DocumentoXLS> list = new ArrayList<DocumentoXLS>();
			    			   mapReturn.put("PackingList", list );
			    			   mapReturn.get("PackingList").add(documento);
			    		   }
		    		   }
		    		   if(etiqueta.equals("RegistroEntregaControlado")){
		    			   if(mapReturn.get("RegistroEntregaControlado") != null){
		    				   mapReturn.get("RegistroEntregaControlado").add(documento);
		    			   } else {
			    			   List<DocumentoXLS> list = new ArrayList<DocumentoXLS>();
			    			   mapReturn.put("RegistroEntregaControlado", list );
			    			   mapReturn.get("RegistroEntregaControlado").add(documento);
			    		   }
		    		   }
		    		   if(etiqueta.equals("Evidencia")){
		    			   if(mapReturn.get("Evidencia") != null){
		    				   mapReturn.get("Evidencia").add(documento);
		    			   } else {
			    			   List<DocumentoXLS> list = new ArrayList<DocumentoXLS>();
			    			   mapReturn.put("Evidencia", list );
			    			   mapReturn.get("Evidencia").add(documento);
			    		   }
		    		   }

		    		   return null;
		     	}    	   
		      });	    
		    return mapReturn; 	
			
		}catch (Exception e){
			e.printStackTrace();
			throw new ProquifaNetException();
		}	
	}
	
	
	
	

	
	
	

	
	@Override
	public String consultarEstado(Integer idUsuario ) throws ProquifaNetException {
		try {
			Map<String, Object> parametros = new HashMap<String, Object>();
			StringBuilder sbQuery = new StringBuilder(" \n");
			parametros.put("idUsuario", idUsuario);	
			sbQuery.append(" \n");
			sbQuery.append("SELECT COUNT(*) FROM ( \n");
			sbQuery.append("SELECT SUM(CASE WHEN Estado = 'Embalado' THEN 1 ELSE 0 END) Embalado, \n");
			sbQuery.append("SUM(CASE WHEN Estado <> 'Embalado' THEN 1 ELSE 0 END) NoEmbalado \n");
			sbQuery.append("FROM EMbalarPEdido WHERE PorEnviar = 1 AND FK03_UsuarioEmbalar = :idUsuario ) E \n");
			sbQuery.append("WHERE NoEmbalado = 0 AND Embalado > 0 \n");
			Integer pzDisponibles = super.jdbcTemplate.queryForObject(sbQuery.toString(),parametros, Integer.class);

			if (pzDisponibles == 0) {
				sbQuery = new StringBuilder(" \n");
				sbQuery.append("SELECT TOP 1  Embalar.Estado FROM \n");
				sbQuery.append("(SELECT TOP 1 Estado, CASE WHEN Estado = 'Por Colectar' THEN 1 \n");
				sbQuery.append("WHEN Estado = 'Generar' THEN 2  \n");
				sbQuery.append("WHEN Estado = 'A Embalar' THEN 3 \n");
				sbQuery.append("ELSE 4 END  AS prioridad \n");
				sbQuery.append("FROM EmbalarPEdido  WHERE Estado <> 'Embalado' AND FK03_UsuarioEmbalar = :idUsuario ORDER BY  prioridad ASC) Embalar \n");

				return super.jdbcTemplate.queryForObject(sbQuery.toString(),parametros, String.class);
			}
			else
				return "GDLEnvio";
		} catch (Exception e) {
			throw new ProquifaNetException();
		}
	}
	

	
	

	
	
	@Override
	public boolean registrarEmbalarPedido(Parametro parametro) throws ProquifaNetException{
		try{			
			StringBuilder sbQuery = new StringBuilder(" \n");
			Map<String, Object> parametros = new HashMap<String, Object>();
			Integer horario = 0;
			if (parametro.getIdHorario() != null && parametro.getIdHorario() > 0) {
				sbQuery = new StringBuilder("SELECT COUNT(Horario) FROM PartidaCompraPorEmbalar WHERE Horario = ").append(parametro.getIdHorario()).append(" AND (Pausado IS NULL OR Pausado = 0) \n");
				horario = super.jdbcTemplate.queryForObject(sbQuery.toString(),parametros, Integer.class);
			}
			
			sbQuery = new StringBuilder("SELECT COUNT(PK_EmbalarPedido) FROM EmbalarPedido WHERE PorEnviar = 1 \n");
			Integer gdlApartada = super.jdbcTemplate.queryForObject(sbQuery.toString(),parametros, Integer.class);
			sbQuery = new StringBuilder(" \n");
			sbQuery.append("SELECT CounT(idPPEdido) FROM PartidaCompraPorEmbalar WHERE Estado IN ('Por Embalar', 'STOCK') AND idPPEdido NOT IN (SELECT ep.FK01_PPedido FROM EmbalarPedido ep) AND (Pausado IS NULL OR Pausado = 0) \n");
			if (gdlApartada > 0) {
				sbQuery.append("AND Ruta <> 'Guadalajara' \n");
			}
			Integer pzDisponibles = super.jdbcTemplate.queryForObject(sbQuery.toString(),parametros, Integer.class);
			if(pzDisponibles > 0) {
				sbQuery = new StringBuilder(" \n");
				
				sbQuery.append("DECLARE @TABLA TABLE (idCliente INT, ZonaMensajeria VARCHAR(50), Ruta VARCHAR(50), Destino VARCHAR(50), idContacto VARCHAR(500), FK01_Horario INT, PorFacturar bit, CPedido VARCHAR(50), Guia VaRCHAR(100) )\n");
				sbQuery.append("INSERT INTO @TABLA \n");
				sbQuery.append("SELECT PCEmbalar.idCliente, PCEmbalar.ZonaMensajeria, PCEmbalar.Ruta, CASE WHEN PCEmbalar.Ruta = 'Local' AND PCEmbalar.ZonaMensajeria <> 'Almancen' THEN 'Entrega' WHEN PCEmbalar.Destino = 'Recoge en PROQUIFA' THEN 'PorEntregar' ELSE 'PorEnviar' END Destino, P.Contacto, P.FK01_Horario, E.PorFactura, P.CPedido, CASE WHEN P.GuiaxCliente = 1 THEN CASE WHEN PEC.PK_Folio IS NULL THEN 'Guia' ELSE 'Cuenta' END ELSE 'Normal' END GuiaCliente  \n");
				sbQuery.append("FROM PartidaCompraPorEmbalar AS PCEmbalar \n");
				sbQuery.append("INNER JOIN PEDIDOS AS P ON P.cpedido = PCEmbalar.cpedido \n");
				sbQuery.append("LEFT JOIN Entrega AS E ON E.FK01_Cliente = P.idCliente \n");
				sbQuery.append("LEFT JOIN Pedido_EnvioXCliente PEC ON PEC.FK02_PEdido = P.idPedido \n");
				sbQuery.append("WHERE  Puntos = (SELECT MAX(pe.Puntos) FROM PartidaCompraPorEmbalar pe  INNER JOIN PPedidos pp ON pe.idPPedido = pp.idPPedido \n");
				sbQuery.append("WHERE pe.idPPedido NOT IN (SELECT ep.FK01_PPedido FROM EmbalarPedido ep) AND (PP.Pausado IS NULL OR PP.Pausado = 0) AND pe.Estado IN( 'Por Embalar', 'STOCK') ");
				if (gdlApartada > 0)
					sbQuery.append("AND pe.Ruta <> 'Guadalajara' ");
				if (horario > 0) 
					sbQuery.append("AND P.FK01_Horario = ").append(parametro.getIdHorario()).append(" ");
				
				sbQuery.append(") AND (PCEmbalar.Pausado IS NULL OR PCEmbalar.Pausado = 0) AND idPPEdido NOT IN (SELECT ep.FK01_PPedido FROM EmbalarPedido ep) ");
				if (gdlApartada > 0)
					sbQuery.append(" AND PCEmbalar.Ruta <> 'Guadalajara' \n");
				
				sbQuery.append("ORDER BY diasRestantes ASC \n");
				
				sbQuery.append("INSERT INTO  EmbalarPedido (Fecha, FK01_PPedido, FK02_Producto, FK03_UsuarioEmbalar, Lote, FechaEmbalado, Tiempo,Prioridad, Estado, FolioTemporal, PorEnviar)  \n");
				sbQuery.append("SELECT  GETDATE(), idPPedido, idProducto, :idUsuario, Lote, null,null, Prioridad, 'Por Embalar', :Folio, CASE WHEN P.Ruta = 'Guadalajara' THEN 1 ELSE NULL END  FROM PartidaCompraPorEmbalar PCE \n");
				sbQuery.append("INNER JOIN PEDIDOS P ON P.Cpedido = PCE.Cpedido \n");
				sbQuery.append("INNER JOIN ( \n");
				sbQuery.append("SELECT TOP 1 * FROM ( SELECT * FROM @TABLA ) PuntosMaximos \n");
				
				sbQuery.append("GROUP BY idCliente, ZonaMensajeria, Ruta, Destino, idContacto, FK01_Horario, PorFacturar, CPedido, Guia ) PM ON PM.idCliente = PCE.idCliente  \n");
				sbQuery.append("AND PM.Ruta = PCE.Ruta COLLATE Modern_Spanish_CI_AS AND PM.idContacto = P.Contacto COLLATE Modern_Spanish_CI_AS AND PM.Destino = PCE.EstadoDestino COLLATE Modern_Spanish_CI_AS AND PM.ZonaMensajeria = PCE.ZonaMensajeria COLLATE Modern_Spanish_CI_AS AND CASE WHEN P.FK01_Horario IS NULL THEN 0 ELSE P.FK01_Horario  END = CASE WHEN PM.FK01_Horario IS NULL THEN 0 ELSE PM.FK01_Horario  END \n");
				sbQuery.append("AND CASE WHEN PM.PorFacturar = 1 THEN PM.CPedido ELSE '1' END = CASE WHEN PM.PorFacturar = 1 THEN P.CPedido ELSE '1' END COLLATE Modern_Spanish_CI_AS \n"); 
				sbQuery.append("WHERE PCE.Estado IN( 'Por Embalar', 'STOCK') AND (PCE.Pausado IS NULL OR PCE.Pausado = 0) AND PCE.idPPedido NOT IN (SELECT ep.FK01_PPedido FROM EmbalarPedido ep) \n");
				if (gdlApartada > 0)
					sbQuery.append("AND PCE.Ruta <> 'Guadalajara' \n");
				
				if(parametro.getIdUsuarioLogueado() != null ){
					parametros.put("idUsuario", parametro.getIdUsuarioLogueado());
				}else{
					parametros.put("idUsuario", null);
				}
				parametros.put("Folio", parametro.getFolio());

				super.jdbcTemplate.update(sbQuery.toString(),parametros);
				return true;
			}
			return false;
		}catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
		
	}
	
	
	@Override
	@Transactional(readOnly=false, transactionManager = "ds1TransactionManager", rollbackFor = Exception.class)
	public boolean pedidosGDL(Parametro parametro) throws ProquifaNetException{
		try{
			StringBuilder sbQuery = new StringBuilder(" \n");
			Map<String, Object> parametros = new HashMap<String, Object>();
		
			sbQuery = new StringBuilder("SELECT CounT(idPPEdido) FROM PartidaCompraPorEmbalar WHERE Ruta = 'Guadalajara' AND (Pausado IS NULL OR Pausado = 0) AND idPPedido NOT IN (SELECT FK01_PPedido FROM EmbalarPedido) \n");
			Integer pzDisponibles = super.jdbcTemplate.queryForObject(sbQuery.toString(),parametros, Integer.class);
			if(pzDisponibles > 0) {
				sbQuery = new StringBuilder(" \n");
				sbQuery.append("DECLARE @TABLA TABLE (idCliente INT, ZonaMensajeria VARCHAR(50), Ruta VARCHAR(50), Destino VARCHAR(50), idContacto VARCHAR(500), FK01_Horario INT, PorFactura bit, CPedido VARCHAR(50) ) \n");
				sbQuery.append("INSERT INTO @TABLA \n");
				sbQuery.append("SELECT TOP 1 PCEmbalar.idCliente, PCEmbalar.ZonaMensajeria, PCEmbalar.Ruta, CASE WHEN PCEmbalar.Ruta = 'Local' AND PCEmbalar.ZonaMensajeria <> 'Almancen' THEN 'Entrega' WHEN PCEmbalar.Destino = 'Recoge en PROQUIFA' THEN 'PorEntregar' ELSE 'PorEnviar' END Destino, P.Contacto idContacto, P.FK01_Horario, E.PorFactura, P.CPedido \n");
				sbQuery.append("FROM PartidaCompraPorEmbalar AS PCEmbalar  \n");
				sbQuery.append("INNER JOIN PEDIDOS AS P ON P.cpedido = PCEmbalar.cpedido \n"); 
				sbQuery.append("LEFT JOIN Entrega AS E ON E.FK01_Cliente = P.idCliente  \n");
				sbQuery.append("WHERE  PCEmbalar.Ruta = 'Guadalajara' AND (PCEmbalar.Pausado IS NULL OR PCEmbalar.Pausado = 0) AND idPPEdido NOT IN (SELECT ep.FK01_PPedido FROM EmbalarPedido ep) \n"); 
				sbQuery.append("ORDER BY PCEmbalar.Puntos DESC \n");
				
				sbQuery.append("INSERT INTO  EmbalarPedido (Fecha, FK01_PPedido, FK02_Producto, FK03_UsuarioEmbalar, Lote, FechaEmbalado, Tiempo,Prioridad, Estado, FolioTemporal, PorEnviar)  \n");
				sbQuery.append("SELECT  GETDATE(), idPPedido, idProducto, :idUsuario, Lote, null,null, Prioridad, 'Por Embalar', :Folio, 1  FROM PartidaCompraPorEmbalar PCE \n");
				sbQuery.append("INNER JOIN PEDIDOS P ON P.Cpedido = PCE.Cpedido \n");
				sbQuery.append("INNER JOIN ( \n");
				sbQuery.append("SELECT * FROM @TABLA ) PM ON PM.idCliente = PCE.idCliente  \n");
				sbQuery.append("AND PM.Ruta = PCE.Ruta COLLATE Modern_Spanish_CI_AS AND PM.idContacto = P.Contacto COLLATE Modern_Spanish_CI_AS AND PM.Destino = PCE.EstadoDestino COLLATE Modern_Spanish_CI_AS \n");
				sbQuery.append("AND PM.ZonaMensajeria = PCE.ZonaMensajeria  COLLATE Modern_Spanish_CI_AS AND CASE WHEN P.FK01_Horario IS NULL THEN 0 ELSE P.FK01_Horario  END = CASE WHEN PM.FK01_Horario IS NULL THEN 0 ELSE PM.FK01_Horario  END \n");
				sbQuery.append("WHERE PCE.Estado IN( 'Por Embalar', 'STOCK') AND (PCE.Pausado IS NULL OR PCE.Pausado = 0) AND PCE.idPPedido NOT IN (SELECT ep.FK01_PPedido FROM EmbalarPedido ep) \n");
				sbQuery.append("AND CASE WHEN PM.PorFactura = 1 THEN PM.CPedido ELSE '1' END = CASE WHEN PM.PorFactura = 1 THEN P.CPedido ELSE '1' END COLLATE Modern_Spanish_CI_AS \n");
				
				
				if(parametro.getIdUsuarioLogueado() != null ){
					parametros.put("idUsuario", parametro.getIdUsuarioLogueado());
				}else{
					parametros.put("idUsuario", null);
				}
				parametros.put("Folio", parametro.getFolio());

				log.info(sbQuery.toString());
				super.jdbcTemplate.update(sbQuery.toString(),parametros);
				return true;
			}
			return false;
		}catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	
	
	@Override
	public Boolean actualizarEstado( Parametro parametro ) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String ids = "";
			if(parametro.getLista().size() > 0 && parametro.getLista() != null) {				
				for(int i = 0; i < parametro.getLista().size(); i++) {
					ids += parametro.getLista().get(i).toString()+ ',';
					if(i == parametro.getLista().size() -1 ) {
						ids +=  "0";
					}					
				}
			}	
				map.put("Estado", parametro.getEstado());
				map.put("idUsuario", parametro.getIdUsuarioLogueado());
				if(parametro.getEstado().equalsIgnoreCase("Embalado")) {
//					map.put("Tiempo", parametro.getTiempo());
					super.jdbcTemplate.update("UPDATE EmbalarPedido SET Estado = :Estado, FechaEmbalado = GETDATE()  WHERE Estado = 'Generar' AND FK03_UsuarioEmbalar = :idUsuario AND FK01_PPedido in(" + ids + ")", map);
				}else if( parametro.getEstado().equalsIgnoreCase("Generar")){
					super.jdbcTemplate.update("UPDATE EmbalarPedido SET Estado = :Estado  WHERE FK03_UsuarioEmbalar = :idUsuario AND FK01_PPedido in (" + ids + ")", map);
				}else {
				map.put("Estado", parametro.getEstado());
				map.put("idUsuario", parametro.getIdUsuarioLogueado());
				super.jdbcTemplate.update("UPDATE EmbalarPedido SET Estado = :Estado  WHERE FK03_UsuarioEmbalar = :idUsuario AND Estado <> 'Embalado'", map);
			
				}
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	@Override
	public Boolean actualizarAEmbalarPendiente( String idPPedidos, String Folio, Integer idusuario ) throws ProquifaNetException {
		try {
				Map<String, Object> map = new HashMap<String, Object>();
				idPPedidos += ",0";
				map.put("idUsuario", idusuario);
				map.put("Folio", Folio);
				super.jdbcTemplate.update("UPDATE EmbalarPedido  SET FolioTemporal = :Folio WHERE FK03_UsuarioEmbalar = :idUsuario AND  FK01_PPedido IN ("+ idPPedidos +")", map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}

	@Override
	public String consultarPedidosEmbalar(Integer idUsuario ) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n  "); //Estado = 'A Embalar' AND
			sbQuery.append("SELECT STUFF( (select ',' + CAST(FK01_PPedido as VARCHAR(MAX)) from EmbalarPedido WHERE Estado = 'A Embalar' AND FK03_UsuarioEmbalar = :idUsuario FOR XML PATH ('')), 1, 1, '')  \n");
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("idUsuario", idUsuario);	
			log.info(sbQuery.toString());
			return super.jdbcTemplate.queryForObject(sbQuery.toString(),parametros, String.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	@Override
	public Integer consultarIDPackingList( ) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n  ");
			sbQuery.append("SELECT IDENT_CURRENT ('PackingList') \n");
			Map<String, Object> parametros = new HashMap<String, Object>();
			return super.jdbcTemplate.queryForObject(sbQuery.toString(),parametros, Integer.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	


	@Override
	@SuppressWarnings("unchecked")
	public OrdenEntrega obtenerOrdenEntregaXid(Long idOrdenEntrega) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("");
			sbQuery.append("SELECT * FROM OrdenEntrega where PK_OrdenEntrega =").append(idOrdenEntrega);
			log.info(sbQuery.toString());
			return (OrdenEntrega) super.jdbcTemplate.query(sbQuery.toString(), new OrdenEntregaRowMapper());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PartidaInspeccion> obtenerPartidaColectar() throws ProquifaNetException {
		
		try {
			
			StringBuilder sbQuery = new StringBuilder("select * from PartidadeCompraAPorColectar ");
			List<PartidaInspeccion> lstResult = jdbcTemplate.query(sbQuery.toString(),
					new OrdenPartidaInspeccionRowMapper());
			return lstResult;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	

	@Override
	public PackingList savePackingList(PackingList packingList) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("INSERT INTO PackingList(Fecha, Folio, Ruta, Zona, Video) VALUES( \n");
			sbQuery.append("GETDATE(), :folio, :Ruta, :zona, :video) \n");
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("folio", packingList.getFolio());
			parametros.put("Ruta", packingList.getRuta());
			parametros.put("zona", packingList.getZona());
			parametros.put("video" , packingList.getVideo());
			super.jdbcTemplate.update(sbQuery.toString(), parametros);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	

	@Override
	public PackingList savePartidaPackingList(PartidaPackingList packingList) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("INSERT INTO PPackingList(Fecha, Folio, Tipo, FK01_PackingList, FK02_EmbalarPedido) VALUES( \n");
			sbQuery.append("GETDATE(), :folio, :tipo, :idPackingList, :idEmbalar) \n");			
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("folio", packingList.getFolio());
			parametros.put("idPackingList", packingList.getPackingList().getIdPackingList());
			parametros.put("tipo", packingList.getTipo());
			parametros.put("idEmbalar", packingList.getEmbalar().getIdEmbalarPedido());
			
			super.jdbcTemplate.update(sbQuery.toString(), parametros);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	
	@Override
	public List<PackingListJasper> obtenerDatosJasperPackingList(String folio, boolean generarPDF, boolean soloControlados) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");			
			sbQuery.append("DECLARE @FolioPL AS varchar(14) = :folio \n");
			sbQuery.append("DECLARE @almacen as INT = (SELECT TOP 1 COALESCE(CondicionAlmacenaje,0) CondicionAlmacenaje FROM PackingList PL \n"); 
			sbQuery.append("INNER JOIN PPackingList PPL ON PPL.FK01_PackingList = PL.PK_PackingList \n");
			sbQuery.append("INNER JOIN EmbalarPedido EP ON EP.PK_EmbalarPedido = PPL.FK02_EmbalarPedido \n"); 
			sbQuery.append("INNER JOIN PPedidos PP ON PP.idPPedido = EP.FK01_PPedido \n");
			sbQuery.append("INNER JOIN Pedidos P ON P.CPedido = PP.CPedido \n");
			sbQuery.append("LEFT JOIN Entrega EN ON EN.FK01_Cliente = P.idCliente WHERE PL.Folio =  @FolioPL) \n");
			
			sbQuery.append("SELECT PL.Folio PackingList, ");
			sbQuery.append("Emp.RazonSocial AS EmpRSocial, Emp.RFC AS EmpRFC, Emp.Calle AS EmpCalle, Emp.Colonia AS EmpColonia, Emp.Ciudad AS EmpCiudad, Emp.Delegacion AS EmpDel, Emp.Estado AS EmpEdo, Emp.Pais AS EmpPais, Emp.CP AS EmpCP, \n");
			sbQuery.append("P.NombreFiscalP AS CliRSocial, P.Calle AS CliCalle, P.Delegacion AS CliDel, P.Estado AS CliEdo, P.CP AS CliCP, P.Pais AS CliPais, \n");
			sbQuery.append("CL.Nombre Cliente, P.Calle, P.Destino, C.Titulo, CASE WHEN P.Destino = 'Recoge en PROQUIFA' THEN P.Destino \n");
			sbQuery.append("WHEN P.Destino = 'Almacén' AND P.Contacto = '--NINGUNO--' THEN UPPER(P.Destino) ELSE P.Contacto END Contacto, COALESCE(PROD.Codigo, PP.Codigo) Codigo, COALESCE( PF.Factura,F.Factura,RE.Factura) AS Factura, \n");			
			sbQuery.append("COALESCE(CAST(PROD.Concepto AS VARCHAR(MAX)),CAST(COM.Descripcion AS VARCHAR(MAX)) ) + CASE WHEN PP.idComplemento > 0 THEN '' WHEN IOC.Lote IS NULL THEN '' ELSE '  Lote: ' + IOC.Lote COLLATE Modern_Spanish_CI_AS + \n");
			sbQuery.append("CASE WHEN PROD.Control IS NOT NULL AND PROD.Control <> 'Normal' THEN ' Caducidad: ' + \n");
			sbQuery.append("CASE WHEN IOC.AnoCaducidad IS NULL OR IOC.AnoCaducidad ='--ND--' THEN 'No Aplica' \n");
			sbQuery.append("WHEN IOC.AnoCaducidad IS NOT NULL AND IOC.AnoCaducidad <>'--ND--' AND (IOC.MesCaducidad IS NULL OR IOC.MesCaducidad = '--ND--') THEN IOC.AnoCaducidad \n");
			sbQuery.append("WHEN IOC.AnoCaducidad IS NOT NULL AND IOC.AnoCaducidad <>'--ND--' AND IOC.MesCaducidad IS NOT NULL AND IOC.MesCaducidad <> '--ND-' THEN IOC.MesCaducidad + ' ' + IOC.AnoCaducidad END ELSE '' END \n");
			sbQuery.append("END Concepto, PPL.Folio FolioEmpaque, SUM(PP.Cant) Cant, P.Pedido, TPL.Ambiente, TPL.Refrigeracion, TPL.Congelacion, PP.Cpedido \n");
			sbQuery.append("FROM PackingList PL  \n");
			sbQuery.append("INNER JOIN PPackingList PPL ON PPL.FK01_PackingList = PL.PK_PackingList \n");
			sbQuery.append("INNER JOIN EmbalarPedido EP ON EP.PK_EmbalarPedido = PPL.FK02_EmbalarPedido  \n");
			sbQuery.append("INNER JOIN PPedidos PP ON PP.idPPedido = EP.FK01_PPedido  \n");
			if (generarPDF) {
				sbQuery.append("LEFT JOIN MovimientoStock MS ON MS.idPPEdido = PP.idPPEdido \n");
				sbQuery.append("LEFT JOIN Stock STK ON STK.idStocK = MS.idStock \n");
				sbQuery.append("LEFT JOIN PCompras PC ON PP.idPPedido = PC.FK03_PPedido AND PC.Estado <> 'Cancelada' \n");
				sbQuery.append("LEFT JOIN PCompras PC1 ON PC1.idPCompra = STK.idPcompra \n");
				sbQuery.append("LEFT JOIN (SELECT MAX(id) AS idMaxIns, idPCompra FROM InspeccionOC GROUP BY idPCompra) AS MaxId ON MaxId.idPCompra = COALESCE(PC1.idPCompra, PC.idPCompra) \n");
				sbQuery.append("LEFT JOIN InspeccionOC IOC ON IOC.id = MaxId.idMaxIns \n");
			} else {
				sbQuery.append("INNER JOIN PartidaCompraPorEmbalar PCP ON PCP.idPPedido = PP.idPPedido \n");
				sbQuery.append("INNER JOIN PCompras PC ON PCP.idPCompra = PC.idPCompra AND PC.Estado <> 'Cancelada' \n");
				sbQuery.append("LEFT JOIN (SELECT MAX(id) AS idMaxIns, idPCompra FROM InspeccionOC GROUP BY idPCompra) AS MaxId ON MaxId.idPCompra = PC.idPCompra \n");
				sbQuery.append("LEFT JOIN InspeccionOC IOC ON IOC.id = MaxId.idMaxIns \n");
			}
			sbQuery.append("INNER JOIN Pedidos P ON P.CPedido = PP.CPedido  \n");
			sbQuery.append("INNER JOIN Clientes CL ON CL.Clave = P.idCliente  \n");
			sbQuery.append("INNER JOIN Contactos C ON C.idContacto = P.idContacto  \n");
			sbQuery.append("LEFT JOIN Productos PROD ON PROD.idProducto = PP.FK02_Producto \n"); 
			sbQuery.append("LEFT JOIN Complemento COM ON COM.idComplemento = PP.idComplemento \n");
			sbQuery.append("LEFT JOIN  Factura_FELectronica  FE ON PPL.FK03_FacturaElectronica  =  FE.FK02_FacturaElectronica\n");
			sbQuery.append("LEFT JOIN FacturaElectronica AS FEL ON FEL.PK_Factura = FE.FK02_FacturaElectronica \n");
			sbQuery.append("LEFT JOIN Empresa AS Emp ON Emp.PK_Empresa = FEL.EmpresaEmisor \n");
			sbQuery.append("LEFT JOIN  facturas  F ON FE.fk01_Factura = F.idFactura AND (F.Estado <> 'Por cancelar'  OR F.Estado <> 'Cancelada' )  \n");
			sbQuery.append("LEFT JOIN  (SELECT PF.* FROM Facturas F INNER JOIN PFacturas PF ON F.Factura = PF.Factura AND F.FPor = PF.Fpor WHERE F.Estado IS NULL OR (F.Estado <> 'Por Cancelar' AND F.Estado <> 'Cancelada')) PF ON PF.CPedido = PP.CPEdido AND PP.Part = PF.PPedido AND (PF.Estado IS NULL OR ( PF.Estado <> 'A cancelar' AND PF.Estado <> 'Cancelada' )) \n");
			sbQuery.append("LEFT JOIN (SELECT PK_Remision, 'R-' + Factura as Factura FROM Remisiones) RE ON RE.PK_Remision = PPL.FK04_Remision \n");
			sbQuery.append("INNER JOIN (SELECT PL.Folio, SUM (CASE WHEN PROD.Tipo = 'Publicaciones' OR PROD.Tipo = 'Labware' OR COM.idComplemento > 0 THEN PP.Cant WHEN PROD.Manejo_Transporte = 'Ambiente' THEN PP.Cant ELSE 0 END) Ambiente, \n"); 
			sbQuery.append("SUM (CASE WHEN PROD.Manejo_Transporte = 'Congelación' OR PROD.Manejo_Transporte = 'Congelacion' THEN PP.Cant ELSE 0 END) Congelacion, \n"); 
			sbQuery.append("SUM (CASE WHEN PROD.Manejo_Transporte = 'Refrigeración' OR PROD.Manejo_Transporte = 'Refrigeracion' THEN PP.Cant ELSE 0 END) Refrigeracion  \n");
			sbQuery.append("FROM PackingList PL  \n");
			sbQuery.append("INNER JOIN PPackingList PPL ON PPL.FK01_PackingList = PL.PK_PackingList \n"); 
			sbQuery.append("INNER JOIN EmbalarPedido EP ON EP.PK_EmbalarPedido = PPL.FK02_EmbalarPedido  \n");
			sbQuery.append("INNER JOIN PPedidos PP ON PP.idPPedido = EP.FK01_PPedido  \n");
			sbQuery.append("INNER JOIN Pedidos P ON P.CPedido = PP.CPedido  \n");
			sbQuery.append("INNER JOIN Clientes CL ON CL.Clave = P.idCliente  \n");
			sbQuery.append("INNER JOIN Contactos C ON C.idContacto = P.idContacto  \n");
			sbQuery.append("LEFT JOIN (SELECT idProducto, Tipo, CASE WHEN @almacen = 1 THEN Manejo ELSE Manejo_Transporte COLLATE Modern_Spanish_CS_AS END Manejo_Transporte, Control FROM Productos) PROD ON PROD.idProducto = PP.FK02_Producto \n"); 
			sbQuery.append("LEFT JOIN Complemento COM ON COM.idComplemento = PP.idComplemento \n");
			sbQuery.append("LEFT JOIN  Factura_FELectronica  FE ON PPL.FK03_FacturaElectronica  =  FE.FK02_FacturaElectronica\n");  
			sbQuery.append("LEFT JOIN  facturas  F ON FE.fk01_Factura = F.idFactura  AND (F.Estado <> 'Por cancelar'  OR F.Estado <> 'Cancelada' )  \n");
			sbQuery.append("LEFT JOIN  (SELECT PF.* FROM Facturas F INNER JOIN PFacturas PF ON F.Factura = PF.Factura AND F.FPor = PF.Fpor WHERE F.Estado IS NULL OR (F.Estado <> 'Por Cancelar' AND F.Estado <> 'Cancelada')) PF ON PF.CPedido = PP.CPEdido AND PP.Part = PF.PPedido AND (PF.Estado IS NULL OR ( PF.Estado <> 'A cancelar' AND PF.Estado <> 'Cancelada' )) \n");			
			sbQuery.append("WHERE PL.Folio = @FolioPL \n");
			if (soloControlados)
				sbQuery.append("AND Prod.Control IN ('Mundiales', 'Nacionales')  \n");
			sbQuery.append("GROUP BY  PL.Folio ) TPL ON TPL.Folio = PL.Folio \n");
			sbQuery.append("WHERE PL.Folio = @FolioPL \n");
			if (soloControlados)
				sbQuery.append("AND Prod.Control IN ('Mundiales', 'Nacionales') \n");
			sbQuery.append("GROUP BY  PL.Folio, Emp.RazonSocial, Emp.RFC, Emp.Calle, Emp.Colonia, Emp.Ciudad, Emp.Delegacion, Emp.Estado, Emp.Pais, Emp.CP, \n");
			sbQuery.append("P.NombreFiscalP, P.Calle, P.Delegacion, P.Estado, P.CP, P.Pais,CL.Nombre, P.Calle, P.Destino, C.Titulo, P.Contacto, PROD.Codigo, CAST(PROD.Concepto AS VARCHAR(MAX)), PP.Codigo, PP.idComplemento, \n"); 
			sbQuery.append("PPL.Folio, PP.Cant, P.Pedido, F.Factura, TPL.Ambiente, TPL.Refrigeracion, TPL.Congelacion, IOC.Lote, IOC.MesCaducidad, IOC.AnoCaducidad, Prod.Control, PF.Factura, CAST(COM.Descripcion AS VARCHAR(MAX)), RE.Factura, PP.Cpedido \n");
			sbQuery.append("ORDER BY PPL.Folio, Factura, PROD.Codigo DESC   \n");
			
			Map<String, Object>  parametros = new HashMap<String, Object>();
			parametros.put("folio", folio);
			Map<String, PackingListJasper> mapReturn = new HashMap<String, PackingListJasper>();
			List<PackingListJasper> list = new ArrayList<PackingListJasper>();
						
			log.info(sbQuery.toString());
			super.jdbcTemplate.query(sbQuery.toString(), parametros, new RowMapper<Object>() {

				@Override
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					
					
					
					PackingListJasper pack = new PackingListJasper();
					PackingListJasper pack2 = new PackingListJasper();
					
					Empresa emisor = new Empresa();
					emisor.setRazonSocial(rs.getString("EmpRSocial"));
					emisor.setRfcEmpresa(rs.getString("EmpRFC"));
					emisor.setCalle(rs.getString("EmpCalle"));
					emisor.setColonia(rs.getString("EmpColonia"));
					emisor.setCiudad(rs.getString("EmpCiudad"));
					emisor.setDelegacion(rs.getString("EmpDel"));					
					emisor.setEstado(rs.getString("EmpEdo"));
					emisor.setCp(rs.getString("EmpCP"));
					emisor.setPais(rs.getString("EmpPais"));
					pack.setEmpresa(emisor);
					
					Cliente cli = new Cliente();
					cli.setRazonSocial(rs.getString("CliRSocial"));
					Direccion dirEntrega = new Direccion();
					dirEntrega.setCalle(rs.getString("CliCalle"));
					dirEntrega.setMunicipio(rs.getString("CliDel"));
					dirEntrega.setEstado(rs.getString("CliEdo"));
					dirEntrega.setCodigoPostal(rs.getString("CliCP"));
					dirEntrega.setPais(rs.getString("CliPais"));
					cli.setDireccion(dirEntrega);
					
					pack.setCli(cli);
					
					pack.setAmbiente(rs.getInt("Ambiente"));
					pack2.setAmbiente(rs.getInt("Ambiente"));
					pack.setCalle(rs.getString("Calle"));
					pack2.setCalle(rs.getString("Calle"));
					pack.setCant(rs.getInt("Cant"));
					pack2.setCant(rs.getInt("Cant"));
					pack.setCliente(rs.getString("Cliente"));
					pack2.setCliente(rs.getString("Cliente"));
					pack.setCodigo(rs.getString("Codigo"));
					pack2.setCodigo(rs.getString("Codigo"));
					pack.setDescripcion(rs.getString("Concepto"));
					pack2.setDescripcion(rs.getString("Concepto"));
					pack.setCongelacion(rs.getInt("Congelacion"));
					pack2.setCongelacion(rs.getInt("Congelacion"));
					pack.setContacto(rs.getString("Contacto"));
					pack2.setContacto(rs.getString("Contacto"));
					try {
						if (pack.getContacto() != null) {
							String contacto = pack.getContacto().replace("--", "");
							pack.setContacto(contacto);
						}
						if (pack2.getContacto() != null) {
							String contacto = pack2.getContacto().replace("--", "");
							pack2.setContacto(contacto);
						}
					} catch (Exception e) {
					}
					
					pack.setDestino(rs.getString("Destino"));
					pack2.setDestino(rs.getString("Destino"));
					pack.setFolioEmpaque(rs.getString("FolioEmpaque"));
					pack2.setFolioEmpaque(rs.getString("FolioEmpaque"));
					
					String folio = rs.getString("PackingList");
					if (soloControlados){
						folio = folio.replace("PL", "RE");						
					}
					pack.setFolio(folio);
					pack2.setFolio(folio);
					pack.setPedido(rs.getString("Pedido"));
					pack2.setPedido(rs.getString("Pedido"));
					pack.setTitulo(rs.getString("Titulo"));
					pack2.setTitulo(rs.getString("Titulo"));
					pack.setRefrigeracion(rs.getInt("Refrigeracion"));
					pack2.setRefrigeracion(rs.getInt("Refrigeracion"));
					pack.setFactura(rs.getString("Factura"));
					pack2.setFactura(rs.getString("Factura"));
					pack.setPedidoI(rs.getString("Cpedido"));
					pack2.setPedidoI(rs.getString("Cpedido"));
					
					if (mapReturn.get(folio) != null) {						
						mapReturn.get(folio).getListaPartidas().add(pack);
						Integer cant = mapReturn.get(folio).getTotalPiezas();
						mapReturn.get(folio).setTotalPiezas(cant + pack.getCant());
					} else {						
						List<PackingListJasper> lst = new ArrayList<PackingListJasper>();
						pack.setTotalPiezas(pack.getCant());
						mapReturn.put(folio, pack);
						pack.setListaPartidas(lst);
						lst.add(pack2);
						list.add(pack);
					}
					
					return null;
				}
			});
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<PackingListJasper> obtenerDatosRegistroEntregaControlados(String folioPL) {
		PackingListJasper prueba = new PackingListJasper();
		
		return null;
	}
	
	@Override
	public String obtenerFilePedido(Integer idUsuario, String estado )  throws ProquifaNetException {
		String archivos =""; 
		try {

			StringBuilder sbQuery = new StringBuilder("");	

			sbQuery.append(" \n");

			sbQuery.append("SELECT STUFF((SELECT DISTINCT '|' + STUFF((SELECT '|' + CD.NombreArchivo FROM core_documentoadj CD WHERE CD.doctoCorreo_id = DR.Folio AND CD.NombreArchivo LIKE '%.pdf' FOR XML PATH ('')), 1, 1, '' ) \n"); 
			sbQuery.append("FROM ( \n");
			sbQuery.append("SELECT P.DoctoR Folio \n");
			sbQuery.append("FROM PPedidos pp \n");
			sbQuery.append("INNER JOIN Pedidos P ON P.CPedido = PP.CPedido \n");
			sbQuery.append("INNER JOIN EmbalarPedido ep ON pp.idPPedido = ep.FK01_PPedido \n");
			sbQuery.append("INNER JOIN PartidaCompraPorEmbalar pc ON ep.FK01_PPedido = pc.idPPedido \n");
			sbQuery.append("INNER JOIN DoctosR DR ON DR.Folio = P.DoctoR \n");
			sbQuery.append("WHERE ep.Estado = '" + estado + "' AND ep.FK03_UsuarioEmbalar = " + idUsuario + " ) DR FOR XML PATH ('')), 1, 1, '' ) \n");

			archivos = super.queryForString(sbQuery.toString()); 

			return archivos; 	 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Override
	public List<String> obtenerPedidoDoctoR(Integer idUsuario, String estado) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("SELECT P.DoctoR Folio \n");
			sbQuery.append("FROM PPedidos pp \n");
			sbQuery.append("INNER JOIN Pedidos P ON P.CPedido = PP.CPedido \n");
			sbQuery.append("INNER JOIN EmbalarPedido ep ON pp.idPPedido = ep.FK01_PPedido \n");
			sbQuery.append("INNER JOIN PartidaCompraPorEmbalar pc ON ep.FK01_PPedido = pc.idPPedido \n");
			sbQuery.append("INNER JOIN DoctosR DR ON DR.Folio = P.DoctoR \n");
			sbQuery.append("WHERE ep.Estado = :estado AND ep.FK03_UsuarioEmbalar = :idUsuario \n");
			sbQuery.append("GROUP BY P.DoctoR \n");
			
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("estado", estado);
			parametros.put("idUsuario", idUsuario);
			
			return jdbcTemplate.queryForList(sbQuery.toString(), parametros, String.class);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String obtenerSubCarpetaPed ( Long idPedido )  throws ProquifaNetException {
		try {
		
			String query = "SELECT cpedido FROM Pedidos WHERE idPedido =" + idPedido + ";";
			String cpedido = super.queryForString(query.toString()); 
			return cpedido;
		} catch (Exception e) {
			return null;
		}
	}
	



	@Override
	public  List<FacturaElectronica> generarFacturaEmbalar(String idsEmbalarPedido) throws ProquifaNetException{
		try{
			String version = "3.3";

			if(Funcion.FACTURACION_4){
				version = "4.0";
			}
			StringBuilder sbQuery = new StringBuilder(" \n");
			// Cambiar el 3.3 a 4.0 para el cambio de Facturacion 4.0 line-2266
			sbQuery.append("SELECT  * FROM ( SELECT  \n");
			sbQuery.append("Factura1.NoIdentificacion, Factura1.claveProdServ,Factura1.ClaveUnidad, Factura1.UnidadSAT, Factura1.Descripcion + CASE WHEN Factura1.Lote IS NOT NULL AND Factura1.Lote <> '' THEN ' Lote: ' + Factura1.Lote COLLATE SQL_Latin1_General_CP1_CI_AS ELSE '' END +  CASE WHEN Factura1.NoIdentificacion IS NOT NULL AND Factura1.NoIdentificacion <> '' THEN ' Cátalogo: ' + Factura1.NoIdentificacion ELSE '' END + \n");
			sbQuery.append("CASE WHEN Factura1.Control IS NOT NULL AND Factura1.Control <> 'Normal' THEN ' Caducidad: ' + \n");
			sbQuery.append("CASE WHEN Factura1.AnoCaducidad IS NULL OR Factura1.AnoCaducidad ='--ND--' THEN 'No Aplica' \n");  
			sbQuery.append("WHEN Factura1.AnoCaducidad IS NOT NULL AND Factura1.AnoCaducidad <>'--ND--' AND (Factura1.MesCaducidad IS NULL OR Factura1.MesCaducidad = '--ND--') THEN Factura1.AnoCaducidad \n");
			sbQuery.append("WHEN Factura1.AnoCaducidad IS NOT NULL AND Factura1.AnoCaducidad <>'--ND--' AND Factura1.MesCaducidad IS NOT NULL AND Factura1.MesCaducidad <> '--ND-' THEN Factura1.MesCaducidad + ' ' + Factura1.AnoCaducidad END ELSE '' END + \n");
			sbQuery.append("CASE WHEN Factura1.Ruta = 'Centroamerica' AND Factura1.Tipo <> 'Fletes' THEN ' Origen: ' + Factura1.Origen ELSE '' END + \n");
			sbQuery.append("CASE WHEN Factura1.Suplementos IS NOT NULL AND Factura1.Suplementos <> '' THEN ' Incluye los siguiente complementos: ' + Factura1.Suplementos ELSE '' END Descripcion, (Factura1.PrecioUnitario) ValorUnitario, \n");
			sbQuery.append("(ROUND(Factura1.PrecioUnitario, 2) * Factura1.cant) AS Importe, \n");
			sbQuery.append("(ROUND(Factura1.PrecioUnitario, 2) * Factura1.cant) AS impBase, \n"); 
			sbQuery.append("Factura1.ImpTipoFactor, (CASE WHEN Factura1.IVA = 1 THEN 0 ELSE Factura1.IVA END)  ImpTasaOCuota, (Factura1.PrecioUnitario * Factura1.cant   * Factura1.iva) AS ImpImporte,  Factura1.AdnNumeroPedimento, Factura1.Cant, \n");
			sbQuery.append("Factura1.impImpuesto,Factura1.version, Factura1.Serie, Factura1.fecha, Factura1.FormaPago, Factura1.CondicionesPago, (ROUND(Factura1.PrecioUnitario, 2) * Factura1.cant) AS SubTotal, \n");
			sbQuery.append("Factura1.Moneda, Factura1.UsoCFDI, Factura1.isComplemento, \n");
			sbQuery.append("CAST(CAST((ROUND(Factura1.PrecioUnitario,2) * Factura1.cant )as DECIMAL(16,2))  + CAST((ROUND(Factura1.PrecioUnitario, 2) * Factura1.cant  * Factura1.iva)as DECIMAL(16,2)) as DECIMAL(16,2)) Total, \n"); 
			sbQuery.append("Factura1.TipoComprobante, Factura1.MetodoPago, Factura1.PermisoPAP, \n");
			sbQuery.append("Factura1.LugarExpedicion,  Factura1.EmpresaEmisor, Factura1.idEmpresa, Factura1.RazonSocial, ROUND(Factura1.PrecioUnitario * Factura1.cant * Factura1.iva, 2) AS ImpuestosTotalTraslados, Factura1.ImpuestosClave,  Factura1.ImpuestosTipoFactor, \n"); 
			sbQuery.append("(CASE WHEN Factura1.IVA = 1 THEN 0 ELSE Factura1.IVA END) ImpuestosTasaOCuota, Factura1.ImpuestosImporte, Factura1.FolioPedidoCliente, Factura1.Cliente, Factura1.TipoCambio, COALESCE(Factura1.TC,1) TC, 'Sin Timbrar' Estado, Factura1.RFC, Factura1.CPedido, Factura1.Pedido, Factura1.EtiquetaControl, Factura1.idPPedido, Factura1.Part, Factura1.RFCEmpresa , Factura1.RegimenFiscal, Factura1.RazonEmpresa, Factura1.FolioCompleto, Factura1.Ped1, Factura1.FolioCompleto PedimentoCompleto, Factura1.LineaDeOrden, Factura1.CorreoAddenda \n");  
			sbQuery.append("FROM  \n");
			sbQuery.append("(SELECT Datos.*,\n"); 
			sbQuery.append("CASE WHEN  Datos.etiqueta = 'Sin Operacion' THEN  Datos.precio\n"); 
			sbQuery.append("WHEN  Datos.etiqueta = 'division' THEN  (Datos.precio / Datos.TipoCambio)\n");
			sbQuery.append("WHEN Datos.etiqueta= 'multiplicar' THEN (Datos.precio * Datos.TipoCambio) ELSE 0 END PrecioUnitario,\n"); 
			sbQuery.append("CASE WHEN Datos.Tipo = 'Fletes' THEN \n"); 
			sbQuery.append("\t CASE WHEN (Datos.ControlFactura = 'Normal' OR Datos.ControlFactura IS NULL) THEN (Datos.CPedido + '-N' ) \n"); 
			sbQuery.append("\t ELSE (Datos.CPedido + '-C') END \n");
			sbQuery.append("ELSE \n");
			sbQuery.append("\t CASE WHEN (Datos.Control = 'Normal' OR Datos.Control IS NULL) THEN (Datos.CPedido + '-N' ) \n"); 
			sbQuery.append("\t WHEN Datos.idMovimiento IS NOT NULL THEN (Datos.CPedido + '-S' + Datos.idProducto ) \n");  
			sbQuery.append("\t ELSE (Datos.CPedido + '-C') END \n");
			sbQuery.append("END AS EtiquetaControl, \n");

			sbQuery.append("CASE \n");
			sbQuery.append("WHEN Datos.Tipo = 'Fletes' THEN  'ACT'\n");
			sbQuery.append("WHEN Datos.Tipo = 'Publicaciones' THEN  'H87' \n");
			sbQuery.append("WHEN Datos.Tipo = 'Capacitaciones' THEN  'E48' \n");/*Se agrega nuevo codigo SAT para facturar capacitaciones*/
			sbQuery.append("WHEN Datos.unidad IS NOT NULL THEN \n");
			sbQuery.append("CASE WHEN Datos.Descripcion LIKE 'Frasco' THEN 'H87'\n");
			sbQuery.append("WHEN Datos.unidad = 'cm'  THEN 'H87'\n");
			sbQuery.append("WHEN Datos.unidad = 'g'  THEN 'H87'  \n");
			sbQuery.append("WHEN Datos.unidad = 'kg' THEN 'H87'  \n");
			sbQuery.append("WHEN Datos.unidad = 'L' THEN 'H87' \n");
			sbQuery.append("WHEN Datos.unidad = 'mcg' THEN 'H87' \n");
			sbQuery.append("WHEN Datos.unidad = 'mg' THEN 'H87' \n");
			sbQuery.append("WHEN Datos.unidad = 'mL' THEN 'H87' \n");
			sbQuery.append("WHEN Datos.unidad = 'UI' THEN 'H87'\n");
			sbQuery.append("WHEN Datos.unidad = 'uL' THEN 'H87' \n");
			sbQuery.append("WHEN Datos.unidad = 'Units' THEN 'H87' \n");
			sbQuery.append("WHEN Datos.unidad = 'Wells' THEN 'H87' \n");
			sbQuery.append("WHEN Datos.unidad = 'tablets' THEN 'H87' \n");
			sbQuery.append("WHEN Datos.unidad = 'Fletes' THEN '' ELSE 'H87' \n");
			sbQuery.append("END ELSE 'H87' END ClaveUnidad,\n");

			sbQuery.append("CASE \n");
			sbQuery.append("WHEN Datos.Tipo = 'Fletes' THEN  'Evento'\n");
			sbQuery.append("WHEN Datos.Tipo = 'Publicaciones' THEN  'Pieza' \n");
			sbQuery.append("WHEN Datos.Tipo = 'Capacitaciones' THEN  'Unidad de servicio' \n");
			sbQuery.append("WHEN Datos.unidad IS NOT NULL THEN \n");
			sbQuery.append("CASE WHEN Datos.Descripcion LIKE 'Frasco' THEN  'Frasco'\n");
			sbQuery.append("WHEN Datos.unidad = 'cm'  THEN 'Pieza' \n");
			sbQuery.append("WHEN Datos.unidad = 'g'  THEN 'Pieza'  \n");
			sbQuery.append("WHEN Datos.unidad = 'kg' THEN 'Pieza' \n");
			sbQuery.append("WHEN Datos.unidad = 'L' THEN 'Pieza' \n");
			sbQuery.append("WHEN Datos.unidad = 'mcg' THEN 'Pieza' \n");
			sbQuery.append("WHEN Datos.unidad = 'mg' THEN 'Pieza' \n");
			sbQuery.append("WHEN Datos.unidad = 'mL' THEN 'Pieza' \n");
			sbQuery.append("WHEN Datos.unidad = 'UI' THEN 'Pieza'\n");
			sbQuery.append("WHEN Datos.unidad = 'uL' THEN 'Pieza' \n");
			sbQuery.append("WHEN Datos.unidad = 'Units' THEN 'Pieza' \n");
			sbQuery.append("WHEN Datos.unidad = 'Wells' THEN 'Pieza' \n");
			sbQuery.append("WHEN Datos.unidad = 'tablets' THEN 'Pieza' \n");
			sbQuery.append("WHEN Datos.unidad = 'Fletes' THEN 'Pieza' ELSE 'Pieza' \n");
			sbQuery.append("END  ELSE 'Pieza' END UnidadSAT,\n");
			sbQuery.append("CASE WHEN Datos.subtipo = 'Biológico' THEN '41116132'\n");
			sbQuery.append("WHEN Datos.Tipo = 'Estandares' THEN  '41116107'\n");
			sbQuery.append("WHEN Datos.Tipo = 'Reactivos' THEN '41116105'\n");
			sbQuery.append("WHEN Datos.Tipo = 'Publicaciones' THEN '55101500'\n");
			sbQuery.append("WHEN Datos.Tipo = 'Capacitaciones' THEN '86101600'\n");
			sbQuery.append("WHEN Datos.Tipo = 'Labware' THEN '41116100'\n");
			//sbQuery.append("WHEN Datos.Tipo = 'Fletes' THEN  '78121603'\n");La claveProdServ se cambia por indicacion del SAT, esta clave ya no es aceptada
			sbQuery.append("WHEN Datos.Tipo = 'Fletes' THEN  '78102205'\n");
			sbQuery.append("END claveProdServ, \n");
			sbQuery.append("CASE WHEN (Datos.Control = 'Normal' OR Datos.Control IS NULL) THEN NULL ELSE datos.Pedimento END  AdnNumeroPedimento, datos.Pedimento Ped1 \n");
			sbQuery.append("FROM \n");
			sbQuery.append("(SELECT  pr.codigo NoIdentificacion, CAST(pr.Concepto AS VARCHAR(MAX)) + CASE WHEN pr.Proveedor <> 44 AND pr.Cantidad IS NOT NULL AND pr.Unidad IS NOT NULL THEN ' (' + pr.Cantidad + ' ' + pr.unidad + ') ' + pr.Fabrica ELSE ' ' + pr.Fabrica END Descripcion, Ins.Lote, Ins.MesCaducidad, Ins.AnoCaducidad, null ValorUnitario, null Importe, pp.Precio,pp.Cant, PP.LineaDeOrden, p.CorreoAddenda, \n");
			sbQuery.append("(CASE WHEN  p.IVA  > 0 THEN p.IVA ELSE 0 END) IVA, MS.idMovimiento, CAST(PR.IdProducto as VARCHAR(MAX)) IdProducto, \n");
			sbQuery.append("(CASE WHEN p.IVA > 0 THEN  '002' ELSE '002'  END) impImpuesto,\n");
			sbQuery.append("(CASE WHEN  p.IVA > 0 THEN 'Tasa' ELSE 'Tasa' END) ImpTipoFactor,  \n");
			sbQuery.append("p.iva ImpTasaOCuota, Ins.Pedimento, em.RFC RFCEmpresa, em.RazonSocial RazonEmpresa, em.RegimenFiscal, \n");
			sbQuery.append("--FACTURA-- \n");
			sbQuery.append(version+" Version, 'A' Serie,null fecha, CASE WHEN CL.MedioPago = 'Efectivo' THEN '01' WHEN CL.MedioPago = 'Tarjeta' THEN '04' WHEN CL.MedioPago = 'Cheque' THEN '02' WHEN CL.MedioPago = 'Otros' OR CL.MedioPago = 'Depósito bancario' THEN '99' WHEN CL.MedioPago = 'Transferencia' THEN '03' ELSE '99' END   FormaPago, p.CPago CondicionesPago, NULL subtotal, p.MonedaFactura Moneda,\n");
			sbQuery.append("null  Total, COALESCE(vcb.Tipo, 'G03') UsoCFDI,\n");
			sbQuery.append("'I' TipoComprobante, COALESCE(vc.Tipo, 'PPD') MetodoPago, em.CP LugarExpedicion, em.Alias EmpresaEmisor, em.PK_Empresa idEmpresa, p.NombreFiscalP RazonSocial, p.RFCFiscalP RFC, p.cPedido CPedido, p.pedido Pedido, pr.control Control, pp.Part,p.idcliente, pr.unidad, pr.tipo, pr.subtipo,pp.idPPEdido,\n");
			sbQuery.append("(CASE WHEN p.IVA > 0 THEN  '002' ELSE '002'  END) ImpuestosClave, \n");
			sbQuery.append("(CASE WHEN  p.IVA > 0 THEN 'Tasa'ELSE 'Tasa' END)ImpuestosTipoFactor,   \n");
			sbQuery.append("null  ImpuestosTasaOCuota, \n");
			sbQuery.append("null ImpuestosImporte, p.pedido FolioPedidoCliente, NULL TotalTExto,p.idCliente Cliente,\n");
			sbQuery.append("CASE WHEN  p.Moneda collate SQL_Latin1_General_CP1_CI_AS = p.MonedaFactura collate SQL_Latin1_General_CP1_CI_AS THEN 1 ELSE \n");
			sbQuery.append("CASE WHEN p.Tcambio <> 0 AND p.Tcambio IS NOT NULL THEN p.TCambio\n"); 
			sbQuery.append("WHEN  p.moneda = 'Pesos' AND  p.MonedaFactura  = 'Dolares' THEN m.Pdolar\n"); 
			sbQuery.append("WHEN  p.moneda = 'Dolares' AND  p.MonedaFactura  = 'Pesos' THEN m.Pdolar \n");
			sbQuery.append("WHEN  p.moneda = 'Dolares' AND  p.MonedaFactura  = 'Euros' THEN m.Edolar \n");
			sbQuery.append("WHEN  p.moneda = 'Euros' AND  p.MonedaFactura  = 'Dolares' THEN m.Edolar END  END TipoCambio,\n");
			sbQuery.append("CASE WHEN  p.MonedaFactura <> 'Pesos' THEN \n");
			sbQuery.append("CASE WHEN p.TcDof = 1 THEN m2.DOF WHEN p.Tcambio <> 0 AND p.Tcambio IS NOT NULL THEN p.TCambio \n");   
			sbQuery.append("WHEN  p.MonedaFactura  = 'Dolares' THEN m.Pdolar \n");			
			sbQuery.append("WHEN  p.MonedaFactura  = 'Euros' THEN ROUND( m.Edolar * m.pdolar, 4) \n");
			sbQuery.append("END ELSE 1 END TC, \n");
			sbQuery.append("CASE WHEN  p.moneda = 'Pesos' AND  p.MonedaFactura  = 'Dolares' THEN 'division' \n");
			sbQuery.append("WHEN  p.moneda = 'Dolares' AND  p.MonedaFactura  = 'Pesos' THEN 'multiplicar' \n");
			sbQuery.append("WHEN  p.moneda = 'Dolares' AND  p.MonedaFactura  = 'Euros' THEN 'division' \n");
			sbQuery.append("WHEN  p.moneda = 'Euros' AND  p.MonedaFactura  = 'Dolares' THEN 'multiplicar' \n");
			sbQuery.append("WHEN p.moneda = 'Euros' AND p.MonedaFactura = 'Pesos' AND p.TCambio > 0 THEN 'multiplicar' ELSE 'Sin Operacion' END etiqueta, \n");
			sbQuery.append("CASE WHEN PAP.NoPermisoAdquisicion IS NOT NULL THEN 'PERMISO DE ADQUISICIÓN EN PLAZA DE ' + PAP.TipoPermiso + ' NO. ' + PAP.NoPermisoAdquisicion ELSE NULL END PermisoPAP, \n");
			sbQuery.append("COALESCE(CAST(year(OD.FPedimento)%100 as varchar(2)) +'  '+ CAST(Ad.Numero AS varchar(2)) +'  ', CAST(year(Ins.FPedimento)%100 as varchar(2)) +'  ' + cast(AdIns.Numero as varchar(2)) +'  ') FolioCompleto,\n");
			sbQuery.append("STUFF( (SELECT ',' + CAST(PP1.Concepto AS VARCHAR(MAX)) \n");
			sbQuery.append("FROM PPedidos PP1 \n");
			sbQuery.append("LEFT JOIN Complemento COM ON COM.idComplemento = PP1.idComplemento \n");
			sbQuery.append("LEFT JOIN Productos PROD ON PROD.Codigo = COM.Codigo AND PROD.Fabrica = COM.Fabrica \n");
			sbQuery.append("LEFT JOIN PPedidos PPR ON PPR.FK02_Producto = PROD.idProducto AND PPR.CPedido = PP1.CPedido AND PPR.idComplemento = 0 \n");
			sbQuery.append("WHERE PPR.idPPedido = PP.idPPedido FOR XML PATH ('')), 1, 1, '') Suplementos, CASE WHEN PP.idComplemento > 0 THEN 1 ELSE 0 END isComplemento, ped.Ruta, COALESCE(PC.Origen, PR.Origen) AS Origen, ControlFactura.Control AS ControlFactura, idProductoControl.idProducto AS idProdControlado \n");
			sbQuery.append("FROM   \n");
			sbQuery.append(" (SELECT PK_EmbalarPEdido, Fecha, FK01_PPedido, FK02_Producto, FK03_UsuarioEmbalar, Lote, FechaEmbalado, Tiempo, Prioridad, Estado, FolioTemporal FROM EmbalarPEdido \n");
			sbQuery.append("UNION \n");
			sbQuery.append("SELECT 0, NULL Fecha, PPComplemento.idPPedido, PPComplemento.Fk02_Producto, 0, NULL, NULL, NULL, '', '', ''FROM PPedidos AS PPComplemento WHERE FK02_PPedido IN (SELECT FK01_PPedido FROM EmbalarPedido WHERE PK_EmbalarPedido IN ("+ idsEmbalarPedido + ") ) \n");
			sbQuery.append("UNION \n");
			sbQuery.append("SELECT 0, NULL Fecha, PP1.idPPedido, PP1.Fk02_Producto, 0, PROD.Lote, NULL, NULL, '', '', '' \n");
			sbQuery.append("FROM EmbalarPEdido EP  \n");
			sbQuery.append("INNER JOIN PPEdidos PP ON PP.idPPEdido = FK01_PPEdido \n");
			sbQuery.append("INNER JOIN PPedidos PP1 ON PP1.Cpedido = PP.CPedido  \n");
			sbQuery.append("INNER JOIN Productos PROD ON PROD.idProducto = PP1.FK02_Producto \n");
			sbQuery.append("WHERE ep.PK_EmbalarPEdido in (" + idsEmbalarPedido + ") AND PP1.Estado <> 'Cancelada' AND PROD.Tipo = 'Fletes' AND (PP1.FleteFacturado IS NULL OR PP1.FleteFacturado = 0 or PP1.FleteFacturado = 1) ) EP \n");
			sbQuery.append("INNER JOIN PPedidos  pp  ON ep.FK01_PPedido = pp.IdPPedido\n");
			sbQuery.append("INNER JOIN Pedidos p ON pp.CPedido = p.CPedido \n");
			sbQuery.append("LEFT JOIN Productos pr ON pp.FK02_Producto =  pr.idProducto\n");
			sbQuery.append("INNER JOIN Pedidos ped ON pp.CPedido = ped.CPedido \n");
			sbQuery.append("LEFT JOIN MovimientoStock MS ON MS.idPPedido = PP.idPPEdido \n");

			sbQuery.append("LEFT JOIN PartidaCompraPorEmbalar PCE ON PCE.idPPedido = PP.idPPedido \n");
			sbQuery.append("LEFT JOIN PCompras PC  ON  PC.idPCompra = PCE.idPCompra AND PC.Estado <> 'Cancelada' \n");
			sbQuery.append("INNER JOIN clientes CL ON  CL.Clave =   p.idCliente \n");
			sbQuery.append("LEFT JOIN InspeccionOC  Ins ON Ins.idpCompra =  PC.idpCompra\n");
			sbQuery.append("LEFT JOIN PListaArribo PLA ON PLA.FK02_PCompra = PC.idPCompra \n");
			sbQuery.append("LEFT JOIN ListaArribo LA ON LA.PK_ListaArribo = PLA.FK01_ListaArribo \n");
			sbQuery.append("LEFT JOIN OrdenDespacho OD ON OD.PK_OrdenDespacho = FK02_OrdenDespacho \n");
			sbQuery.append("LEFT JOIN Aduana AS Ad ON Ad.PK_Aduana = OD.FK05_Aduana\n");
			sbQuery.append("LEFT JOIN Aduana AS AdIns ON AdIns.Nombre = Ins.Aduana \n");

			sbQuery.append("LEFT JOIN  Valorcombo vc ON vc.PK_Folio = COALESCE(p.FK04_MetodoPago,CL.FK05_MetodoPAgo ) AND vc.Concepto = 'MetodoPago'\n");
			sbQuery.append("LEFT JOIN  Valorcombo vcb ON vcb.PK_Folio = COALESCE(p.FK03_UsoCFDI, CL.FK04_UsoCFDI) AND vcb.Concepto = 'UsoCFDI'\n");
			sbQuery.append("LEFT JOIN(SELECT TOP 1 * FROM Monedas ORDER BY Fecha DESC) m ON PP.idPPedido = PP.idPPedido \n");
			sbQuery.append("LEFT JOIN(SELECT TOP 1 * FROM Monedas WHERE CAST(Fecha as DATE) <= CAST(DATEADD(DAY, -1, GETDATE()) as DATE) ORDER BY Fecha DESC) m2 ON PP.idPPedido = PP.idPPedido \n");
			sbQuery.append("INNER JOIN Empresa em ON  p.facturfiscalp =  em.Alias collate SQL_Latin1_General_CP1_CI_AS\n");
			sbQuery.append("LEFT JOIN GestionPAP PAP ON PAP.FK01_PPedido = PP.idPPEdido \n");

			sbQuery.append("LEFT JOIN (SELECT PF.* FROM Facturas F INNER JOIN PFacturas PF ON F.Factura = PF.Factura AND F.FPor = PF.Fpor WHERE F.Estado IS NULL OR (F.Estado <> 'Por Cancelar' AND F.Estado <> 'Cancelada')) PF ON PF.CPedido = PP.CPedido AND PF.PPedido = PP.Part \n");
			
			sbQuery.append("LEFT JOIN (	SELECT Prod.Control FROM PPedidos AS PP \n");
			sbQuery.append("\t LEFT JOIN Productos AS Prod ON Prod.idProducto = PP.FK02_Producto WHERE CPedido IN (\n");			
			sbQuery.append("\t SELECT DISTINCT CPedido FROM PPedidos WHERE idPPedido in (\n");
			sbQuery.append("\t\t SELECT FK01_PPedido from EmbalarPedido AS EP WHERE ep.PK_EmbalarPedido in ("+ idsEmbalarPedido +") ) ) AND Prod.Tipo <> 'Fletes' \n");
			sbQuery.append(") AS ControlFactura ON 1 = 1 \n");
			
			sbQuery.append("LEFT JOIN (SELECT Prod.idProducto FROM PPedidos AS PP \n");
			sbQuery.append("\t LEFT JOIN Productos AS Prod ON Prod.idProducto = PP.FK02_Producto \n"); 
			sbQuery.append("\t WHERE PP.idPPedido IN ( \n"); 
			sbQuery.append("\t SELECT FK01_PPedido from EmbalarPedido AS EP WHERE ep.PK_EmbalarPedido in ("+ idsEmbalarPedido +") )  AND Prod.Tipo <> 'Fletes' \n"); 
			sbQuery.append(") AS idProductoControl ON 1 = 1 \n");
			
			sbQuery.append("WHERE ep.PK_EmbalarPEdido in ("+ idsEmbalarPedido +")  AND PF.Factura IS NULL \n");
			sbQuery.append("GROUP BY pr.codigo, Ins.MesCaducidad, Ins.AnoCaducidad, Ins.Lote, pp.cant, CAST(pr.Concepto AS VARCHAR(MAX)), pp.Precio, p.IVA, p.CPago, em.CP, em.Alias, p.Pedido, p.Moneda, p.MonedaFactura, m.PDolar,p.TCambio, m.Edolar, pp.cant, p.MonedaFactura, p.idCliente, em.PK_Empresa, PP.idComplemento, PAP.NoPermisoAdquisicion, PAP.TipoPermiso, pr.Proveedor, pr.Fabrica, pr.Cantidad, MS.idMovimiento, PR.IdProducto, p.TcDof, m2.DOF, \n");
			sbQuery.append("p.NombreFiscalP, p.RFCFiscalP, p.cPedido, pr.control, pp.Part, p.idcliente, pr.unidad, pr.tipo, pr.subtipo, pp.idPPedido, vc.Tipo, Ad.Numero, Ins.FPedimento, Ins.Aduana, OD.FPedimento, vcb.Tipo, Ins.Pedimento, em.RFC, em.RazonSocial, em.RegimenFiscal, Ad.Numero, AdIns.Numero, PP.LineaDeOrden, p.CorreoAddenda, ped.Ruta, COALESCE(PC.Origen, PR.Origen), CASE WHEN CL.MedioPago = 'Efectivo' THEN '01' WHEN CL.MedioPago = 'Tarjeta' THEN '04' WHEN CL.MedioPago = 'Cheque' THEN '02' WHEN CL.MedioPago = 'Otros' OR CL.MedioPago = 'Depósito bancario' THEN '99' WHEN CL.MedioPago = 'Transferencia' THEN '03' ELSE '99' END, ControlFactura.Control, idProductoControl.idProducto \n) AS Datos) \n AS Factura1) \n AS Factura\n");
			sbQuery.append("GROUP BY NoIdentificacion, claveProdServ, ClaveUnidad, UnidadSAT, Descripcion, ValorUnitario, Importe, ImpBase, ImpTipoFactor, ImpTasaOcuota, ImpImporte, AdnNumeroPedimento,Cant, ImpImpuesto, version, Serie, Fecha, formaPago, CondicionesPAgo, SubTotal, Moneda, UsoCFDI, PermisoPAP, \n");
			sbQuery.append("Total,TipoComprobante, MetodoPago, LugarExpedicion, EmpresaEmisor, idEmpresa, RazonSocial, ImpuestosTotalTraslados,ImpuestosClave, ImpuestosTipoFactor, ImpuestosTasaOCuota, FolioPedidoCliente, Cliente, TipoCambio, TC, Estado, RFC, CPedido, Pedido, EtiquetaControl,idPPedido, \n");
			sbQuery.append("Part, RFCEmpresa, RegimenFiscal, RazonEmpresa, FolioCompleto, Ped1, PedimentoCompleto,ImpuestosImporte, LineaDeOrden, CorreoAddenda, isComplemento \n");
			sbQuery.append("ORDER BY Part, EtiquetaControl DESC, isComplemento \n");


			log.info(sbQuery.toString());
			
			Map<String, Object> parametros = new HashMap<String, Object>();		
			Map<String, FacturaElectronica> mapReturn = new HashMap<String, FacturaElectronica>();
			List<FacturaElectronica> listaReturn = new ArrayList<FacturaElectronica>();
			getJdbcTemplate().query(sbQuery.toString(), parametros,new RowMapper<FacturaElectronica>() {
				@Override
				public FacturaElectronica mapRow(ResultSet rs, int arg1) throws SQLException {
					FacturaElectronica f = new FacturaElectronica();
					Empresa empresa = new Empresa();
					Cliente cliente = new Cliente();
					ConvertNumber convert = new ConvertNumber();
					DecimalFormat df2 = new DecimalFormat( "####0.00" );


					PFacturaElectronica pfe = new PFacturaElectronica();
					String etiqueta1 = rs.getString("EtiquetaControl");
					try{
						int isComplemento = rs.getInt("isComplemento");
						pfe.setLineaDeOrden(rs.getString("LineaDeOrden"));
						pfe.setClaveProdServ(rs.getString("claveProdServ"));
						pfe.setNoIdentificacion(rs.getString("NoIdentificacion"));
						pfe.setCantidad(rs.getString("Cant").toString());
						pfe.setClaveUnidad(rs.getString("ClaveUnidad"));
						pfe.setUnidad(rs.getString("UnidadSAT"));
						pfe.setDescripcion(rs.getString("Descripcion"));
						pfe.setValorUnitario(df2.format(rs.getDouble("ValorUnitario")));
						pfe.setImporte(df2.format(rs.getDouble("Importe")));
						pfe.setImpuestoBase(df2.format(rs.getDouble("impBase")));
						pfe.setImpuestoClave(rs.getString("impImpuesto"));
						pfe.setImpuestoTipoFactor(rs.getString("ImpTipoFactor"));
						pfe.setPpedido(rs.getInt("idPPedido"));
						pfe.setPart(rs.getInt("Part"));
						if(rs.getFloat("ImpTasaOCuota") > 0) {
							pfe.setImpuestoTasaOCuota(rs.getString("ImpTasaOCuota").toString()+ "0000");
						}else {
							pfe.setImpuestoTasaOCuota("0.000000");
						}	    

						pfe.setImpuestoImporte(df2.format(rs.getDouble("ImpImporte")));
						String pedimentoCompleto = "";
						try {
							String noPedimento = rs.getString("Ped1");
							if (noPedimento != null && !noPedimento.equals("")) {
								String[] pedimentoArray = noPedimento.split(" {1,2}");
								pedimentoCompleto = rs.getString("PedimentoCompleto");
								if (pedimentoCompleto != null && !pedimentoCompleto.equals(""))
									pedimentoCompleto += pedimentoArray[0] + "  " + pedimentoArray[1];
								else
									pedimentoCompleto = null;
							} else {
								pedimentoCompleto = null;
							}
						} catch (Exception e) {
							pedimentoCompleto = null;
						}
						pfe.setAdnNumeroPedimento(pedimentoCompleto);

						if (isComplemento == 0) {
							if(mapReturn.get(etiqueta1) == null){
								f.setFormaPago(rs.getString("FormaPago"));
								f.setCondicionesPago(rs.getString("CondicionesPago"));
								System.out.println("df2.format(rs.getDouble(Subtotal): "+ df2.format(rs.getDouble("SubTotal")));
								f.setSubtotal(df2.format(rs.getDouble("SubTotal")));
								System.out.println("f.getSubtotal: "+ f.getSubtotal());

								if (rs.getString("Moneda").equalsIgnoreCase("Dolares")) {
									f.setMoneda("USD");
								}else if (rs.getString("Moneda").equalsIgnoreCase("Euros")) {
									f.setMoneda("EUR");
								} else {
									f.setMoneda("MXN");
								}
								
								f.setTotal(df2.format(rs.getDouble("Total")));

								f.setTotalTexto(convert.convertir(f.getTotal().toString(), true, f.getMoneda()));
								f.setTipoComprobante(rs.getString("TipoComprobante"));
								f.setMetodoPago(rs.getString("MetodoPago"));
								f.setLugarExpedicion(rs.getString("LugarExpedicion"));
								//Empresa
								empresa.setIdEmpresa(rs.getInt("idEmpresa"));
								empresa.setAlias(rs.getString("EmpresaEmisor"));
								empresa.setRegimenFiscal(rs.getString("RegimenFiscal"));
								empresa.setRazonSocial(rs.getString("RazonEmpresa"));
								empresa.setRfcEmpresa(rs.getString("RFCEmpresa"));
								f.setEmpresa(empresa);
								f.setImpuestosTotalTraslados(df2.format(rs.getDouble("ImpuestosTotalTraslados")));

								f.setImpuestosClave(rs.getString("ImpuestosClave"));
								f.setImpuestosTipoFactor(rs.getString("ImpuestosTipoFactor"));
								f.setNotaFE(rs.getString("PermisoPAP"));

								f.setImpuestosTasaOCuota(pfe.getImpuestoTasaOCuota());
								f.setImpuestosImporte(f.getImpuestosTotalTraslados());
								//CLIENTE		    					
								cliente.setIdCliente(Long.parseLong(rs.getString("Cliente").toString()));
								cliente.setUsoCFDI(rs.getString("UsoCFDI"));
								cliente.setRazonSocial(rs.getString("RazonSocial"));
								cliente.setRfc(rs.getString("RFC"));
								f.setCliente(cliente);
								f.setEstado(rs.getString("Estado"));
								if (rs.getString("TC") != null)
									f.setTipoCambio(rs.getString("TC").toString());
								else 
									f.setTipoCambio("1");
								f.setPedido(rs.getString("Pedido"));
								f.setCpedido(rs.getString("CPedido"));
								f.setLstConceptos(new ArrayList<PFacturaElectronica>());
								f.setLstComplementos(new ArrayList<PFacturaElectronica>());
								f.setIdPPedidos(rs.getString("idPPedido"));
								f.setCorreoAddenda(rs.getString("CorreoAddenda"));
								mapReturn.put(etiqueta1, f);
								listaReturn.add(f);
								f.getLstConceptos().add(pfe);
							} else {
							
								mapReturn.get(etiqueta1).setImpuestosTotalTraslados(df2.format(((Double)(Double.parseDouble(mapReturn.get(etiqueta1).getImpuestosTotalTraslados())  + rs.getDouble("ImpuestosTotalTraslados")))));								
								mapReturn.get(etiqueta1).setImpuestosImporte(df2.format(Double.parseDouble(mapReturn.get(etiqueta1).getImpuestosTotalTraslados())));
							
								
								System.out.println("subTotal: "+ df2.format((Double)(Double.parseDouble(mapReturn.get(etiqueta1).getSubtotal()) + rs.getDouble("SubTotal"))));
								System.out.println("sumar: "+ rs.getDouble("SubTotal"));
								
								mapReturn.get(etiqueta1).setSubtotal(df2.format((Double)(Double.parseDouble(mapReturn.get(etiqueta1).getSubtotal()) + rs.getDouble("SubTotal"))));
								
								System.out.println("getSubtotal: " + mapReturn.get(etiqueta1).getSubtotal());
								
								mapReturn.get(etiqueta1).setTotal(df2.format(((Double)(Double.parseDouble(mapReturn.get(etiqueta1).getTotal())  + rs.getDouble("Total")))));
								//mapReturn.get(etiqueta1).setTotal(df2.format(154018.94));
								mapReturn.get(etiqueta1).getLstConceptos().add(pfe);
								mapReturn.get(etiqueta1).setIdPPedidos(mapReturn.get(etiqueta1).getIdPPedidos()+ ","+ rs.getString("idPPedido"));

							}
						} else {
							if (mapReturn.get(etiqueta1) != null) {
								mapReturn.get(etiqueta1).setIdPPedidos(mapReturn.get(etiqueta1).getIdPPedidos()+ ","+ rs.getString("idPPedido"));
								mapReturn.get(etiqueta1).getLstComplementos().add(pfe);
							}
						}

					}catch (Exception e) {
						e.printStackTrace();
					}

					return null; 	
				}    	   
			});

			int count = 0;
			for (Entry<String, FacturaElectronica> fact : mapReturn.entrySet()) {

				log.info((++count) + "-===========================================================================");
				log.info("Total: " + fact.getValue().getTotal());
				log.info("Subtotal: " + fact.getValue().getSubtotal());
				log.info("ImpuestosTotal: " + fact.getValue().getImpuestosTotalTraslados());
				log.info("Impuesto: " + fact.getValue().getImpuestosImporte());
				log.info("===========================================================================");

				if (fact.getValue().getNotaFE() != null && !fact.getValue().getNotaFE().equals("")) {
					sbQuery = new StringBuilder();
					sbQuery.append("UPDATE Pedidos SET NotaFE = '").append(fact.getValue().getNotaFE()).append("' WHERE CPedido = '").append(fact.getValue().getCpedido()).append("' \n");
					sbQuery.append(" \n");
					jdbcTemplate.update(sbQuery.toString(), parametros);
				}
			}

			sbQuery = new StringBuilder();
			sbQuery.append(" \n");
			sbQuery.append("UPDATE PPedidos SET FleteFacturado = 1 WHERE idPPedido IN( \n");
			sbQuery.append("SELECT PP1.idPPedido \n");
			sbQuery.append("FROM EmbalarPEdido EP \n"); 
			sbQuery.append("INNER JOIN PPEdidos PP ON PP.idPPEdido = FK01_PPEdido \n");
			sbQuery.append("INNER JOIN PPedidos PP1 ON PP1.Cpedido = PP.CPedido  \n");
			sbQuery.append("INNER JOIN Productos PROD ON PROD.idProducto = PP1.FK02_Producto \n");
			sbQuery.append("WHERE ep.PK_EmbalarPEdido in (" + idsEmbalarPedido + ") AND PROD.Tipo = 'Fletes' AND (PP1.FleteFacturado IS NULL OR PP1.FleteFacturado = 0) ) \n");

			jdbcTemplate.update(sbQuery.toString(), parametros);

			return listaReturn;
			
		}catch (Exception e){
			e.printStackTrace();
			throw new ProquifaNetException();
		}	
	}
	
	@Override
	public  Boolean actualizarPackingList(Integer idFactura, String idPedidos) {
		try {
			Map <String,Object> map = new HashMap<String, Object>();
			map.put("idFactura", idFactura);
			StringBuilder sbQuery = new StringBuilder("\n");
			sbQuery.append("");
			sbQuery.append("UPDATE PP SET FK03_FActuraElectronica = :idFactura FROM PPackingList PP\n");
			sbQuery.append("INNER JOIN EmbalarPEdido EP ON PP.FK02_EmbalarPEdido = EP.PK_EmbalarPEdido\n");
			sbQuery.append("WHERE EP.FK01_PPEdido IN (" +idPedidos + ")\n");
			jdbcTemplate.update(sbQuery.toString(), map);
			return true;
		}catch (Exception e) {
			return false;
			// TODO: handle exception
		}
	}
	
	
	@Override
	public String obtenerDatosCliente(Integer idCpedido) throws ProquifaNetException{
		try {
			StringBuilder sbQuery = new StringBuilder("\n");
			sbQuery.append("");
			sbQuery.append("SELECT EMP.usuario FROM CLIENTES CL\n");
			sbQuery.append("INNER JOIN (SELECT usuario, clave FROM EMPLEados) EMP ON COALESCE(CL.Cobrador, 74) = EMP.CLAVE\n");
			sbQuery.append("WHERE  CL.FacturaPortal = 1 AND CL.clave = (SELECT P.idCliente FROM PPEDidos PP\n");
			sbQuery.append("INNER JOIN PEDIDOS P  ON PP.CPEDIDO = P.CPEDIDO\n");
			sbQuery.append("WHERE IDPPEDIDO = :idCpedido) \n");
			Map<String, Object>  parametros = new HashMap<String, Object>();
			parametros.put("idCpedido", idCpedido);
			return jdbcTemplate.queryForObject(sbQuery.toString(),parametros, String.class);
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public Long obtenerIdFactura(Integer id) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("\n");
			sbQuery.append("");
			sbQuery.append("SELECT FK01_FACtura FROM FACTURA_FElectronica WHERE FK02_FacturaElectronica = :idFacturaElectronica ");
			Map<String, Object>  parametros = new HashMap<String, Object>();
			parametros.put("idFacturaElectronica", id);
			return jdbcTemplate.queryForObject(sbQuery.toString(), parametros, Long.class);
		} catch (Exception e){
			e.printStackTrace();
			throw new ProquifaNetException(); 
		}
	}
	
	@Override
	public TrPackingList obtenerDatosContacto() throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("SELECT TOP 1 Nombre contacto, Puesto, Departamento, TelCel tel, usuario + '@proquifa.net' mail \n");
			sbQuery.append("FROM Empleados WHERE FK01_Funcion = 41 \n");
			Map<String, Object>  parametros = new HashMap<String, Object>();
			return jdbcTemplate.queryForObject(sbQuery.toString(), parametros, new BeanPropertyRowMapper<>(TrPackingList.class));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException(); 
		}
	}
	
	@Override
	public String obtenerCobradorPorCliente(String idPedidos) throws ProquifaNetException{
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append(" \n");
			sbQuery.append("SELECT Usuario FROM Empleados WHERE Clave = (SELECT Cobrador FROM Clientes WHERE CLave = ( \n");
			sbQuery.append("SELECT idCliente FROM Pedidos WHERE CPEdido IN (SELECT CPedido FROM PPedidos WHERE idPPEdido IN (" + idPedidos +") ))) \n");
			Map<String, Object>  parametros = new HashMap<String, Object>();
			parametros.put("idPedidos", idPedidos);
			return jdbcTemplate.queryForObject(sbQuery.toString(),parametros, String.class);
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public boolean finalizarGDL(String noGuia) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("UPDATE EmbalarPedido SET NoGuia = :noGuia, PorEnviar = 0 \n");
			sbQuery.append("WHERE PorEnviar = 1 \n");
			sbQuery.append(" \n");
			
			Map<String, Object>  parametros = new HashMap<String, Object>();
			parametros.put("noGuia", noGuia);
			jdbcTemplate.update(sbQuery.toString(), parametros);
			
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException(); 
		}
		
	}
	@Override
	public boolean guardarUbicacion(Integer idPPedido) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("\n");
			sbQuery.append("UPDATE UB SET UB.Existencia = (CASE WHEN (UB.Existencia - PC.CAnt) >= 0 THEN UB.Existencia - PC.CAnt ELSE 0 END) FROM UBICACION UB \n");
			sbQuery.append("INNER JOIN PartidaCompraPorEmbalar AS PC ON Ub.IDUbicacion = PC.Ubicacion COLLATE Modern_Spanish_CI_AS \n");
			sbQuery.append("WHERE idPpedido =  :idPPedido\n");
			Map<String, Object>  parametros = new HashMap<String, Object>();
			parametros.put("idPPedido", idPPedido);
			jdbcTemplate.update(sbQuery.toString(), parametros);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}

	@Override
	public Map<String, String> obtenerPartidasYaFacturadas(String idsEmbalarPedido) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append(" \n");
			sbQuery.append("SELECT PF.idFactura, PF.Factura, FE.FK02_FacturaElectronica idElectronica, STUFF( \n");
			sbQuery.append("(SELECT ',' + CAST(idPPEdido AS VARCHAR(MAX)) FROM EmbalarPEdido EP1 \n");
			sbQuery.append("INNER JOIN PPedidos  pp1  ON ep1.FK01_PPedido = pp1.IdPPedido \n");
			sbQuery.append("INNER JOIN (SELECT PF.* FROM Facturas F INNER JOIN PFacturas PF ON F.Factura = PF.Factura AND F.FPor = PF.Fpor WHERE F.Estado IS NULL OR (F.Estado <> 'Por Cancelar' AND F.Estado <> 'Cancelada')) PF1 ON PF1.CPedido = PP1.CPedido AND PF1.PPedido = PP1.Part   \n"); 
			sbQuery.append("WHERE EP1.PK_EmbalarPEdido in (" + idsEmbalarPedido + ") \n");
			sbQuery.append("AND (PF1.Estado IS NULL OR (PF1.Estado <> 'Por Cancelar' AND PF1.Estado <> 'Cancelada')) \n");
			sbQuery.append("AND PF1.Factura = PF.Factura FOR XML PATH ('')), 1, 1, '') idPedidos \n");
			sbQuery.append("FROM EmbalarPEdido EP \n");
			sbQuery.append("INNER JOIN PPedidos  pp  ON ep.FK01_PPedido = pp.IdPPedido \n");
			sbQuery.append("INNER JOIN (SELECT PF.*, F.idFactura FROM Facturas F INNER JOIN PFacturas PF ON F.Factura = PF.Factura AND F.FPor = PF.Fpor WHERE F.Estado IS NULL OR (F.Estado <> 'Por Cancelar' AND F.Estado <> 'Cancelada')) PF ON PF.CPedido = PP.CPedido AND PF.PPedido = PP.Part   \n"); 
			sbQuery.append("LEFT JOIN Factura_FElectronica FE ON FE.FK01_Factura = PF.idFactura \n");
			sbQuery.append("WHERE EP.PK_EmbalarPEdido in (" + idsEmbalarPedido + ") \n");
			sbQuery.append("AND (PF.Estado IS NULL OR (PF.Estado <> 'Por Cancelar' AND PF.Estado <> 'Cancelada')) \n");
			sbQuery.append("GROUP BY PF.idFactura, PF.Factura, FE.FK02_FacturaElectronica \n");
			
			Map<String, Object> parametros = new HashMap<String, Object>();
			Map<String, String> mapReturn = new HashMap<String, String>();
			jdbcTemplate.query(sbQuery.toString(), parametros, new RowMapper<Object>() {

				@Override
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					try {
						if (rs.getString("idElectronica") != null) {
							mapReturn.put(rs.getString("Factura") + "-" + rs.getString("idElectronica"), rs.getString("idPedidos"));
						} else {
							mapReturn.put(rs.getString("Factura") + "|" + rs.getString("idFactura"), rs.getString("idPedidos"));
						}
					} catch (Exception e) {
						mapReturn.put(rs.getString("Factura") + "|" + rs.getString("idFactura"), rs.getString("idPedidos"));
					}
					return null;
				}
			});
			
			return mapReturn;
		} catch (Exception e) {
			e.printStackTrace();
			return new HashMap<String, String>();
		}
	}
	
	
	@Override
	public Integer saveFacturaElectronicaPrepago(String idPPedidos) throws ProquifaNetException {
		try {
			// Cambiar el 3.3 a 4.0 para el cambio de Facturacion 4.0
			String version = "3.3";

			if(Funcion.FACTURACION_4){
				version = "4.0";
			}

			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("INSERT INTO FacturaElectronica( Version, Serie, Folio, Fecha, FormaPago, NoCertificado, CondicionesPago, Subtotal, Moneda, Total, TipoComprobante, MetodoPago, LugarExpedicion, Sello, CadenaOriginal, EmpresaEmisor, ImpuestosTotalTraslados, ImpuestosClave, ImpuestosTipoFactor, ImpuestosTasaOCuota, ImpuestosImporte, SatVersion, SatUUID, SatFechaTimbrado, SatRfcProvCertif, SatSelloCFD, SatNoCertificadoSAT, SatSelloSAT, SatCadenaOriginal, CodeQR, FolioPedidoCliente, TotalTexto, Cliente, [XML], Estado, Certificado, TipoCambio, UsoCFDI)  \n");
			sbQuery.append("SELECT "+version+", 'A', PF.Factura, CONVERT(VARCHAR(19),GETDATE(),126), '', '', '', '', '', '', 'I', '', '', '', '',COALESCE(E.PK_Empresa,1), '', '', '', '', '', '', '', '', '', '', '', '', '', '',NULL, '',0, '', '', '', '', NULL \n");
			sbQuery.append("FROM EmbalarPEdido EP \n");
			sbQuery.append("INNER JOIN PPedidos  pp  ON ep.FK01_PPedido = pp.IdPPedido \n");
			sbQuery.append("INNER JOIN (SELECT PF.* FROM Facturas F INNER JOIN PFacturas PF ON F.Factura = PF.Factura AND F.FPor = PF.Fpor WHERE F.Estado IS NULL OR (F.Estado <> 'Por Cancelar' AND F.Estado <> 'Cancelada')) PF ON PF.CPedido = PP.CPedido AND PF.PPedido = PP.Part \n"); 
			sbQuery.append("LEFT JOIN Empresa E ON E.Alias= PF.FPor COLLATE Modern_Spanish_CI_AS \n");
			sbQuery.append("WHERE EP.FK01_PPedido in (" + idPPedidos + ") \n");
			sbQuery.append("AND PF.Factura IS NOT NULL AND (PF.Estado IS NULL OR (PF.Estado <> 'Por Cancelar' AND PF.Estado <> 'Cancelada')) \n");
			sbQuery.append("GROUP BY PF.Factura, E.PK_Empresa \n");
			
			Map<String, Object> parametros = new HashMap<String, Object>();
			jdbcTemplate.update(sbQuery.toString(), parametros);
			
			sbQuery = new StringBuilder(" \n");
			sbQuery.append("SELECT IDENT_CURRENT ('FacturaElectronica') \n");
			return super.jdbcTemplate.queryForObject(sbQuery.toString(),parametros, Integer.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	@Override
	public Integer saveFactura_FElectronicaPrepago(Integer factura, Integer felectronica) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("INSERT INTO Factura_FElectronica (FK01_Factura, FK02_FacturaElectronica) \n");
			sbQuery.append("VALUES(:factura, :felectronica) \n");
			
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("factura", factura);
			parametros.put("felectronica", felectronica);
			jdbcTemplate.update(sbQuery.toString(), parametros);
			
			sbQuery = new StringBuilder(" \n");
			sbQuery.append("SELECT IDENT_CURRENT ('Factura_FElectronica') \n");
			return super.jdbcTemplate.queryForObject(sbQuery.toString(),parametros, Integer.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	@Override 
	public List<ValidarCFDI>  obtenerCFDI(String estado, Long idUsuario) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("\n");
			sbQuery.append("SELECT PF.fK01_Factura as idFactura, CF.Tipo, CF.Usuario, CF.Password, CF.URL, CF.Correos, PF.FActura, SUM(PF.IMporte) AS Importe, PF.FPor, ");
			sbQuery.append("P.RFCFiscalP AS RfcReceptor, Em.RFC AS RfcEmisor, PF.UUID AS FolioF, PF.Moneda, \n");
			sbQuery.append("CASE WHEN FE.Sello IS NULL OR FE.Sello = '' THEN '1' ELSE SUBSTRING(FE.Sello, LEN(FE.Sello) - 7, LEN(FE.Sello)-1) END AS Sello, FE.Total\n");
			sbQuery.append("FROM EMbalarPedido AS EP \n");
			sbQuery.append("INNER JOIN PPedidos AS PP ON EP.FK01_PPedido = PP.idPPedido \n");
			sbQuery.append("INNER JOIN PEDIDOS AS P ON PP.CPEdido  = P.CPEdido \n");
			sbQuery.append("INNER JOIN EMpresa AS Em ON  P.FacturFiscalP COLLATE Modern_Spanish_CI_AS = Em.Alias \n");
			sbQuery.append("INNER JOIN (SELECT PF.*, F.Moneda, F.UUID, F.EvidenciaFac FROM Facturas F INNER JOIN PFacturas PF ON F.Factura = PF.Factura AND F.FPor = PF.Fpor WHERE F.Estado IS NULL OR (F.Estado <> 'Por Cancelar' AND F.Estado <> 'Cancelada')) AS PF ON PP.cpedido  = PF.CPEdido AND PP.PArt = PF.PPedido \n");
			sbQuery.append("INNER JOIN VAlidarCFDI AS CF ON P.IDCliente = CF.FK01_Cliente \n");
			sbQuery.append("INNER JOIN PPackingList AS PPK ON  EP.pk_EmbalarPedido = PPK.FK02_EmbalarPedido \n");
			sbQuery.append("INNER JOIN FACturaElectronica AS FE ON PPK.FK03_FacturaElectronica = FE.PK_Factura \n");
			sbQuery.append("WHERE EP.FK03_UsuarioEmbalar = :idUsuario AND EP.EStado = :estado \n");
			sbQuery.append("AND (PF.EvidenciaFac is NULL OR(PF.EvidenciaFac <> 1)) AND CF.Tipo IS NOT NULL AND CF.Tipo <> '' ");
			sbQuery.append("GROUP BY PF.fK01_Factura,CF.Tipo, CF.Usuario, CF.Password, CF.URL, CF.Correos, PF.FActura, PF.FPor, \n");
			sbQuery.append("P.RFCFiscalP , Em.RFC , PF.UUID , PF.Moneda, FE.Sello, FE.Total \n");
			Map<String, Object>  map = new HashMap<String, Object>();
			map.put("estado", estado);
			map.put("idUsuario", idUsuario);
			
			return  jdbcTemplate.query(sbQuery.toString(), map, new BeanPropertyRowMapper<>(ValidarCFDI.class));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean actualizarFactura(String idFactura) throws ProquifaNetException{
		try {
			StringBuilder sbQuery = new StringBuilder("\n");
			sbQuery.append("UPDATE Facturas SET EvidenciaFac = 1 \n");
			sbQuery.append("WHERE idFactura = :idFactura \n"); 
			Map<String, Object>  map = new HashMap<String, Object>();
			map.put("idFactura", idFactura);
			jdbcTemplate.update(sbQuery.toString(), map);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return true;
	}
	
	@Override
	public List<Remision> obtenerDatosRemision(String idsEmbalar) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("SELECT RE.Fecha, RE.CPago, RE.idCliente, RE.Cliente, RE.MonedaFactura, RE.Moneda, 0 IVA, RE.Estado, RE.FPor,  \n");
			sbQuery.append("RE.Pedido, RE.CPEdido, RE.TCambio, RE.Calle, RE.Importe, RE.Etiqueta, RE.idPPedido, RE.PPedido, RE.Cant, RE.RFC, \n");
			sbQuery.append("RE.Suplementos, RE.Importe * RE.Cant SubTotal, \n");
			sbQuery.append("RE.Descripcion + CASE WHEN Re.Suplementos IS NOT NULL AND RE.Suplementos <> '' \n"); 
			sbQuery.append("THEN ' Incluye los siguiente complementos: ' + RE.Suplementos ELSE '' END Descripcion, RE.isComplemento \n");
			sbQuery.append("FROM ( \n");
			sbQuery.append("SELECT RE.Fecha, RE.CPago, RE.idCliente, RE.Cliente, RE.MonedaFactura, RE.Moneda, RE.IVA, RE.Estado, RE.FPor, \n"); 
			sbQuery.append("RE.Pedido, RE.CPEdido, RE.TCambio, RE.Calle, RE.Descripcion + CASE WHEN RE.Lote IS NOT NULL AND RE.Lote <> '' THEN ' Lote: ' + RE.Lote ELSE '' END + ");
			sbQuery.append("CASE WHEN RE.Codigo IS NOT NULL THEN ' Cátalogo: ' + RE.Codigo ELSE '' END Descripcion, \n");
			sbQuery.append("CASE WHEN RE.Moneda = 'Pesos' AND RE.MonedaFactura = 'Dolares' THEN (RE.Precio / RE.TCambio) \n"); 
			sbQuery.append("WHEN RE.Moneda = 'Dolares' AND RE.MonedaFactura = 'Pesos' THEN (RE.Precio * RE.TCambio)  \n");
			sbQuery.append("WHEN RE.Moneda = 'Dolares' AND RE.MonedaFactura = 'Euros' THEN (Re.Precio / Re.TCambio)  \n");
			sbQuery.append("WHEN RE.Moneda = 'Euros' AND RE.MonedaFactura = 'Dolares' THEN (RE.Precio * RE.TCambio)   \n");
			sbQuery.append("ELSE RE.Precio END Importe, RE.Etiqueta, RE.idPPedido, RE.Part PPedido, RE.Cant, RE.RFC, \n");
			sbQuery.append("STUFF( (SELECT ',' + CAST(PP1.Concepto AS VARCHAR(MAX)) \n");
			sbQuery.append("FROM PPedidos PP1 \n");
			sbQuery.append("LEFT JOIN Complemento COM ON COM.idComplemento = PP1.idComplemento \n");
			sbQuery.append("LEFT JOIN PRoductos PROD ON PROD.Codigo = COM.Codigo AND PROD.Fabrica = COM.Fabrica \n");
			sbQuery.append("LEFT JOIN PPedidos PPR ON PPR.FK02_Producto = PROD.idProducto AND PPR.CPedido = PP1.CPedido AND PPR.idComplemento = 0 \n");
			sbQuery.append("WHERE PPR.idPPedido = RE.idPPedido FOR XML PATH ('')), 1, 1, '') Suplementos, CASE WHEN RE.idComplemento > 0 THEN 1 ELSE 0 END isComplemento \n");
			sbQuery.append("FROM (  \n");
			sbQuery.append("SELECT CAST(GETDATE() as DATE)  Fecha, PE.CPago, PE.idCliente, PE.MonedaFactura, PE.Moneda, PE.ivA, 'Por Cobrar' Estado, PE.facturFiscalP FPor, PE.Pedido, \n"); 
			sbQuery.append("CASE WHEN PE.TCambio IS NOT NULL AND PE.TCambio > 0 THEN PE.TCambio WHEN PE.MonedaFactura = 'Euros' THEN M.EDolar  \n");
			sbQuery.append("ELSE M.PDolar END TCambio, PE.CPedido, PP.Precio Precio, PP.idPPedido, PP.Part, CLI.Nombre Cliente, \n");
			sbQuery.append("CASE WHEN PROD.Control IS NULL OR PROD.Control = 'Normal' THEN (PP.CPEdido + '-N') ELSE (PP.CPedido + '-C-' + CAST(PP.Part as VARCHAR)) END Etiqueta, \n");
			sbQuery.append("PE.Calle, PE.RFCFiscalP RFC, PP.Cant, PP.idComplemento, CAST(PROD.Concepto as VARCHAR(MAX)) Descripcion, PROD.Lote COLLATE SQL_Latin1_General_CP1_CI_AS Lote, PROD.Codigo \n");
			sbQuery.append("FROM EmbalarPedido EP  \n");
			sbQuery.append("INNER JOIN PPedidos PP ON PP.idPPedido = EP.FK01_PPedido \n"); 
			sbQuery.append("INNER JOIN PEdidos as PE ON PE.CPedido = PP.CPedido  \n");
			sbQuery.append("INNER JOIN Clientes CLI ON ClI.Clave = PE.idCliente \n");
			sbQuery.append("LEFT JOIN Productos PROD ON PROD.idProducto = PP.FK02_Producto \n"); 
			sbQuery.append("LEFT JOIN Monedas M ON M.Fecha = CAST(GETDATE() as DATE)  \n");
			sbQuery.append("LEFT JOIN (SELECT PF.* FROM Facturas F INNER JOIN PFacturas PF ON F.Factura = PF.Factura AND F.FPor = PF.Fpor WHERE F.Estado IS NULL OR (F.Estado <> 'Por Cancelar' AND F.Estado <> 'Cancelada')) PF ON PF.CPedido = PP.CPedido AND PF.PPedido = PP.Part \n");
			sbQuery.append("WHERE EP.PK_EmbalarPEdido IN (" + idsEmbalar + ") AND PF.Factura IS NULL ) RE ) RE \n");
			sbQuery.append("ORDER BY Etiqueta DESC, isComplemento \n");
			sbQuery.append(" \n");
			
			Map<String, Object> parametros = new HashMap<String, Object>();
			Map<String, Remision> mapReturn = new HashMap<String, Remision>();
			List<Remision> lstRemision = new ArrayList<Remision>();
			jdbcTemplate.query(sbQuery.toString(), parametros, new RowMapper<Remision>() {

				@Override
				public Remision mapRow(ResultSet rs, int rowNum) throws SQLException {
					String etiqueta = rs.getString("Etiqueta");
					DecimalFormat df2 = new DecimalFormat( "#,##0.00" );
					
					Remision remision = new Remision();
					remision.setFecha(rs.getDate("Fecha"));
					remision.setCpago(rs.getString("CPago"));
					remision.setIdCliente(rs.getInt("idCliente"));
					remision.setCliente(rs.getString("Cliente"));
					String moneda = rs.getString("MonedaFactura");
					if (moneda.equals("Pesos")) {
						moneda = "M.N.";
					} else if (moneda.equals("Dolares")) {
						moneda = "USD";
					}
					else if (moneda.equals("Euros")) {
						moneda = "EURO";
					}
					remision.setMoneda(moneda);
					remision.setDireccion(rs.getString("Calle"));
					remision.setRfc(rs.getString("RFC"));
					remision.setIva(rs.getDouble("IVA"));
					remision.setEstado(rs.getString("Estado"));
					remision.setFpor(rs.getString("FPor"));
					remision.setPedido(rs.getString("Pedido"));
					remision.setCpedido(rs.getString("CPedido"));
					remision.setTipoCambio(df2.format(rs.getFloat("TCambio")));
					remision.setIdPPedidos(rs.getString("idPPedido"));
					
					PRemision premision = new PRemision();
					premision.setFpor(rs.getString("FPor"));
					premision.setImporte(df2.format(rs.getFloat("Importe")));
					premision.setCpedido(rs.getString("CPedido"));
					premision.setPpedido(rs.getInt("PPedido"));
					premision.setCant(rs.getInt("Cant"));
					premision.setSubTotal(df2.format(rs.getFloat("SubTotal")));
					premision.setDescripcion(rs.getString("Descripcion"));
					
					remision.setTotal(premision.getSubTotal());
					remision.setImporte(premision.getSubTotal());
					
					
					if (mapReturn.get(etiqueta) != null) { 
						mapReturn.get(etiqueta).getPartidaRemisiones().add(premision);
						mapReturn.get(etiqueta).setIdPPedidos(mapReturn.get(etiqueta).getIdPPedidos() + "," + rs.getString("idPPedido"));
						mapReturn.get(etiqueta).setImporte(df2.format(Double.parseDouble(mapReturn.get(etiqueta).getImporte().replace(",", "")) + rs.getFloat("SubTotal")));
					} else {
						List<PRemision> partidaRemisiones = new ArrayList<PRemision>();
						partidaRemisiones.add(premision);
						remision.setPartidaRemisiones(partidaRemisiones);
						lstRemision.add(remision);
						mapReturn.put(etiqueta, remision);
					}
					
					return null;
				}
				
			});
			
			return lstRemision;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	@Override
	public int guardarRemision(Remision remision) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("INSERT INTO Remisiones (Factura, Fecha, CPago, Cliente, Moneda, Importe, IVA, Estado, FPor, Cotiza, Pedido, TCambio, Flete, CFlete, CPedido, ImprimirTC) VALUES \n");
			sbQuery.append("(:factura, CAST(GETDATE() as DATE), :cpago, :idCliente, :moneda, :importe, :iva, :estado, :fpor, '', :pedido, :tcambio, 0, '', :cpedido, 0) \n");
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("factura", remision.getFactura());
			parametros.put("cpago", remision.getCpago());
			parametros.put("idCliente", remision.getIdCliente());
			parametros.put("moneda", remision.getMoneda());
			parametros.put("importe", remision.getImporte().replace(",", ""));
			parametros.put("iva", remision.getIva());
			parametros.put("estado", remision.getEstado());
			parametros.put("fpor", remision.getFpor());
			parametros.put("pedido", remision.getPedido());
			parametros.put("tcambio", remision.getTipoCambio());
			parametros.put("cpedido", remision.getCpedido());
			
			jdbcTemplate.update(sbQuery.toString(), parametros);
			
			sbQuery = new StringBuilder("SELECT IDENT_CURRENT ('Remisiones') \n");
			return super.jdbcTemplate.queryForObject(sbQuery.toString(),parametros, Integer.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	@Override
	public int guardarPartidaRemision(PRemision partidaRemision) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("INSERT INTO PRemisiones (Factura, FPor, Importe, CPedido, PPedido, Part, FK01_Remision) VALUES \n");
			sbQuery.append("(:factura, :fpor, :importe, :cpedido, :ppedido, :part, :idRemision) \n");
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("factura", partidaRemision.getFactura());
			parametros.put("fpor", partidaRemision.getFpor());
			parametros.put("importe", partidaRemision.getImporte().replace(",", ""));
			parametros.put("cpedido", partidaRemision.getCpedido());
			parametros.put("ppedido", partidaRemision.getPpedido());
			parametros.put("part", partidaRemision.getPart());
			parametros.put("idRemision", partidaRemision.getIdRemision());
			
			jdbcTemplate.update(sbQuery.toString(), parametros);

			sbQuery = new StringBuilder("SELECT IDENT_CURRENT ('PRemisiones') \n");
			return super.jdbcTemplate.queryForObject(sbQuery.toString(),parametros, Integer.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	
	@Override
	public  Boolean actualizarPackingListRemision(Integer idRemision, String idPedidos) {
		try {
			Map <String,Object> map = new HashMap<String, Object>();
			map.put("idRemision", idRemision);
			StringBuilder sbQuery = new StringBuilder("\n");
			sbQuery.append("");
			sbQuery.append("UPDATE PP SET FK04_Remision = :idRemision FROM PPackingList PP \n");
			sbQuery.append("INNER JOIN EmbalarPEdido EP ON PP.FK02_EmbalarPEdido = EP.PK_EmbalarPEdido\n");
			sbQuery.append("WHERE EP.FK01_PPEdido IN (" +idPedidos + ")\n");
			jdbcTemplate.update(sbQuery.toString(), map);
			return true;
		}catch (Exception e) {
			return false;
			// TODO: handle exception
		}
	}
	
	@Override
	public boolean generarPendienteContraEntrega(String idsFacturaElectronica) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("INSERT INTO PEndiente(Docto, Partida, FInicio, Responsable, Tipo) \n");
			sbQuery.append("SELECT F.Factura, F.FPor, GETDATE(), COALESCE(CLI.Vendedor,'BArias'), 'Datos de pago pendiente' \n");
			sbQuery.append("FROM Facturas F \n");
			sbQuery.append("INNER JOIN Factura_FElectronica FFE ON FFE.FK01_Factura = F.idFactura \n");
			sbQuery.append("INNER JOIN Clientes CLI ON CLI.Clave = F.Cliente \n");
			sbQuery.append("WHERE FFE.FK02_FacturaElectronica IN(").append(idsFacturaElectronica).append(") \n");
			sbQuery.append(" \n");
			
			Map <String,Object> map = new HashMap<String, Object>();
			jdbcTemplate.update(sbQuery.toString(), map);
			return true;
			
		} catch (Exception e) {
			return false;
		}
	}

	
	@Override
	public List<String[]> obtenerDatosGDL(String numGuia) throws ProquifaNetException {
		try {
	
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("SELECT ROW_NUMBER() OVER(ORDER BY EM.FOlioTemporal ASC) AS #, C.Nombre AS Cliente, COALESCE(P.CP, 'ND') AS CP, CONVERT(char, MIN(PP.FPEntrega), 105) FPEntrega, \n");
			sbQuery.append("CONCAT('CALLE ' , P.Calle collate SQL_Latin1_General_CP1_CI_AS , ', CP.', P.CP, ', ', P.Delegacion, ' ', P.Estado)  AS DEntrega, \n");
			sbQuery.append("HyL.Diario,  HyL.DiaDe1 , HyL.DiaA1, HyL.DiaDe2, HyL.DiaA2, HyL.Lunes,  HyL.LuDe1, HyL.LuA1, HyL.LuDe2, HyL.LuA2, \n");
			sbQuery.append("HyL.Martes, HyL.MaDe1, HyL.MaA1, HyL.MaDe2, HyL.MaA2, HyL.Miercoles, HyL.MiDe1, HyL.MiA1, HyL.MiDe2, HyL.MiA2, \n");
			sbQuery.append("HyL.Jueves, HyL.JuDe1, HyL.JuA1, HyL.JuDe2, HyL.JuA2, HyL.Viernes, HyL.ViDe1, HyL.ViA1, HyL.ViDe2, HyL.ViA2, \n");
			sbQuery.append("STUFF( (select ',' + Datos.Manejo_Transporte \n");
			sbQuery.append("FROM ( SELECT PK1.Folio, COALESCE(PROD1.Manejo_Transporte, 'Ambiente') Manejo_Transporte  \n");
			sbQuery.append("FROM PackingList PK1 \n");
			sbQuery.append("INNER JOIN EMBALARPedido EM1 ON PK1.Folio =  EM1.FolioTemporal COLLATE Modern_Spanish_CI_AS \n");
			sbQuery.append("INNER JOIN PRODUCTOS PROD1 ON PROD1.idProducto = EM1.FK02_Producto \n");
			sbQuery.append("WHERE EM1.FolioTemporal = EM.FolioTemporal \n");
			sbQuery.append("GROUP BY PK1.FOLIO, PROD1.Manejo_Transporte \n");
			sbQuery.append(") Datos \n");
			sbQuery.append("GROUP BY Manejo_Transporte \n");
			sbQuery.append("FOR XML PATH ('')), 1, 1, '') AS Manejo, EM.FolioTemporal \n");
			sbQuery.append("FROM EMBALARPedido EM \n");
			sbQuery.append("INNER JOIN PPEDIDOS PP ON PP.idPPedido = EM.FK01_ppedido \n");
			sbQuery.append("INNER JOIN PEDIDOS P ON P.CPEDIDO = PP.CPEDIDO \n");
			sbQuery.append("INNER JOIN CLIENTES C ON C.Clave = P.idCliente \n");
			sbQuery.append("INNER JOIN HORarioyLugar HyL ON HyL.idHorario = P.FK01_Horario \n");
			sbQuery.append("INNER JOIN PRODUCTOS PROD ON PROD.idProducto = EM.FK02_Producto \n");
			sbQuery.append("WHERE EM.NoGuia = :numGuia \n"); 
			sbQuery.append("GROUP BY EM.FolioTemporal, C.Nombre, P.CP, P.Calle, P.Delegacion, P.Estado, EM.NoGuia, \n");
			sbQuery.append("HyL.Diario,  HyL.DiaDe1, HyL.DiaA1, HyL.DiaDe2, HyL.DiaA2 , HyL.Lunes, \n");
			sbQuery.append("HyL.LuDe1, HyL.LuA1,  HyL.LuDe2, HyL.LuA2, HyL.Martes, HyL.MaDe1, HyL.MaA1, \n");
			sbQuery.append("HyL.MaDe2, HyL.MaA2, HyL.Miercoles, HyL.MiDe1, HyL.MiA1, HyL.MiDe2,HyL.MiDe1, HyL.MiA1, \n");
			sbQuery.append("HyL.MiDe2, HyL.MiA2, HyL.Jueves, HyL.JuDe1, HyL.JuA1, HyL.JuDe2,HyL.JuA2, \n");
			sbQuery.append("HyL.Viernes, HyL.ViDe1, HyL.ViA1, HyL.ViDe2, HyL.ViA2 \n");
			sbQuery.append("ORDER BY # ASC");
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("numGuia", numGuia);
			return super.jdbcTemplate.query(sbQuery.toString(), param, new RowMapper<String[]>(){
				@Override
				public String[] mapRow(ResultSet rs, int arg1) throws SQLException{
					StringBuilder horario = new StringBuilder();
					if(rs.getInt("Diario") == 1) {
						horario.append("Diario de: ").append(rs.getString("DiaDe1")).append("-").append(rs.getString("DiaA1"));
						if(!rs.getString("DiaDe2").equals("00:00") && !rs.getString("DiaDe1").equals(rs.getString("DiaDe2")) && !rs.getString("DiaA1").equals(rs.getString("DiaA2"))) {
							horario.append(" y ").append(rs.getString("DiaDe2")).append("-").append(rs.getString("DiaA2"));
						}
					}  else {
						if(rs.getInt("Lunes") == 1) {
							horario.append("Lunes de: ").append(rs.getString("LuDe1")).append("-").append(rs.getString("LuA1"));
							if(!rs.getString("LuDe2").equals("00:00") && !rs.getString("LuDe1").equals(rs.getString("LuDe2")) && !rs.getString("LuA1").equals(rs.getString("LuA2"))) {
								horario.append(" y ").append(rs.getString("LuDe2")).append("-").append(rs.getString("LuA2"));
							}
							horario.append("\n");
						}
						if(rs.getInt("Martes") == 1) {
							horario.append("Martes de: ").append(rs.getString("MaDe1")).append("-").append(rs.getString("MaA1"));
							if(!rs.getString("MaDe2").equals("00:00") && !rs.getString("MaDe1").equals(rs.getString("MaDe2")) && !rs.getString("MaA1").equals(rs.getString("MaA2"))) {
								horario.append(" y ").append(rs.getString("MaDe2")).append("-").append(rs.getString("MaA2"));
							}
							horario.append("\n");
						}
						if(rs.getInt("Miercoles") == 1) {
							horario.append("Miercoles de: ").append(rs.getString("MiDe1")).append("-").append(rs.getString("MiA1"));
							if(!rs.getString("MiDe2").equals("00:00") && !rs.getString("MiDe1").equals(rs.getString("MiDe2")) && !rs.getString("MiA1").equals(rs.getString("MiA2"))) {
								horario.append(" y ").append(rs.getString("MiDe2")).append("-").append(rs.getString("MiA2"));
							}
							horario.append("\n");
						}
						if(rs.getInt("Jueves") == 1) {
							horario.append("Jueves de: ").append(rs.getString("JuDe1")).append("-").append(rs.getString("JuA1"));
							if(!rs.getString("JuDe2").equals("00:00") && !rs.getString("JuDe1").equals(rs.getString("JuDe2")) && !rs.getString("JuA1").equals(rs.getString("JuA2"))) {
								horario.append(" y ").append(rs.getString("JuDe2")).append("-").append(rs.getString("JuA2"));
							}
							horario.append("\n");
						}
						if(rs.getInt("Viernes") == 1) {
							horario.append("Viernes de: ").append(rs.getString("ViDe1")).append("-").append(rs.getString("ViA1"));
							if(!rs.getString("ViDe2").equals("00:00") && !rs.getString("ViDe1").equals(rs.getString("ViDe2")) && !rs.getString("ViA1").equals(rs.getString("ViA2"))) {
								horario.append(" y ").append(rs.getString("ViDe2")).append("-").append(rs.getString("ViA2"));
							}
							   horario.append("\n");
						}
//						if(horario.length() > 1)
//						horario.substring(2, horario.length()-1);
					}
					String[] arreglo = new String [] { 
							rs.getString("#"),
							rs.getString("FolioTemporal"),
							rs.getString("Cliente"),
							rs.getString("CP"),
							rs.getString("Manejo"),
							rs.getString("FPEntrega"),
							rs.getString("DEntrega"),
							horario.toString()
					};
					return arreglo;
				}
			});	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Map<String, Integer> totalesHoy() throws ProquifaNetException{
		try {
			StringBuilder sbQuery = new StringBuilder("\n");
			sbQuery.append("DECLARE @TABLA TABLE (Folio varchar(100), FPEntrega datetime, FFin datetime) \n");
			sbQuery.append("INSERT INTO @TABLA  \n");
			sbQuery.append("SELECT PL.Folio, CAST(PP.FPEntrega as DATE) FPentrega, PEN.FFin  \n");
			sbQuery.append("FROM Pendiente PEN \n");
			sbQuery.append("INNER JOIN PackingList PL ON PL.Folio = PEN.Docto COLLATE Modern_Spanish_CI_AS \n");
			sbQuery.append("INNER JOIN PPackingList PPL ON PPL.FK01_PackingList = PL.PK_PackingList \n");
			sbQuery.append("INNER JOIN FacturaElectronica AS FE ON FE.PK_Factura = PPL.FK03_FacturaElectronica \n");
			sbQuery.append("INNER JOIN Factura_FElectronica AS FFE ON FFE.FK02_FacturaElectronica = FE.PK_Factura \n");
			sbQuery.append("INNER JOIN Facturas AS F ON F.idFactura = FFE.FK01_Factura \n");
			sbQuery.append("INNER JOIN EmbalarPedido EP ON  EP.Estado = 'Embalado' AND EP.PK_EmbalarPedido = PPL.FK02_EmbalarPEdido \n");
			sbQuery.append("INNER JOIN PPedidos PP ON PP.idPPedido = EP.FK01_PPedido \n");
			sbQuery.append("INNER JOIN PEDIDOS P ON P.Cpedido = PP.Cpedido \n");
			sbQuery.append("WHERE PEN.Tipo = 'Por Enviar' AND (PEN.Partida IS NULL OR PEN.Partida = 0) AND (P.GuiaXCliente = 0 OR P.GuiaXCliente IS NULL) \n");
			sbQuery.append("AND CASE WHEN F.CPago IN ('PAGO CONTRA ENTREGA', 'PREPAGO 100%') AND F.Estado = 'Cobrada' THEN 1 ELSE 0 END = CASE WHEN F.CPago IN ('PAGO CONTRA ENTREGA', 'PREPAGO 100%') THEN 1 ELSE 0 END \n");
			sbQuery.append("GROUP BY PL.Folio, CAST(PP.FPEntrega as DATE), PEN.FFin \n");
			sbQuery.append("SELECT  'Embalar' Tipo,COALESCE(SUM(Cant), 0)  Total from PartidaCompraPorEmbalar \n");
			sbQuery.append("where (Pausado IS NULL OR Pausado = 0) \n");
			sbQuery.append("UNION ALL \n");
			sbQuery.append("SELECT 'Almacen' Tipo, COALESCE(COUNT(PL.Folio), 0) Total \n");
			sbQuery.append("FROM Pendiente PEN \n");
			sbQuery.append("INNER JOIN PackingList PL ON PL.Folio = PEN.Docto COLLATE Modern_Spanish_CI_AS \n");
			sbQuery.append("WHERE PEN.Tipo = 'Por Entregar' AND (PEN.FFin IS NULL) \n");
			sbQuery.append("UNION ALL \n");
			sbQuery.append("SELECT 'EnvioXCliente' Tipo, CASE WHEN COUNT(Folio)  IS NULL THEN 0 ELSE COUNT(Folio) END Total \n");
			sbQuery.append("FROM( \n");
			sbQuery.append("SELECT PL.Folio, CAST(PEN.FFin as Date) FFin, \n");
			sbQuery.append("CASE WHEN PEC.PK_Folio IS NOT NULL AND PEN1.Folio IS NOT NULL THEN '1' \n");  
			sbQuery.append("WHEN PEXC.PK_Folio IS NOT NULL AND PEXC.Guia IS NOT NULL THEN '1' ELSE '0' END PendienteCerrado \n"); 
			sbQuery.append("FROM Pendiente PEN  \n");
			sbQuery.append("INNER JOIN PackingList PL ON PL.Folio = PEN.Docto COLLATE Modern_Spanish_CI_AS \n");
			sbQuery.append("INNER JOIN PPackingList PPL ON PPL.FK01_PackingList = PL.PK_PackingList \n");
			sbQuery.append("INNER JOIN EmbalarPedido EP ON EP.PK_EmbalarPedido = PPL.FK02_EmbalarPedido \n");
			sbQuery.append("INNER JOIN Ppedidos PP ON PP.idPPedido = EP.FK01_PPedido \n");
			sbQuery.append("INNER JOIN Pedidos PD ON PD.Cpedido = PP.Cpedido AND PD.GuiaXCliente = 1 \n");
			sbQuery.append("LEFT JOIN Pendiente PEN1 ON PEN1.Docto = PL.Folio AND PEN1.Tipo = 'Cargar Guia Envio' AND PEN1.FFIN IS NOT NULL\n"); 
			sbQuery.append("LEFT JOIN Pedido_EnvioXCliente PEC ON PEC.FK01_PackingList = PL.PK_PackingList \n");
			sbQuery.append("LEFT JOIN Pedido_EnvioXCliente PEXC ON PEC.FK02_Pedido = PD.idPedido \n");
			sbQuery.append("WHERE PEN.Tipo = 'Por Enviar' AND (PEN.FFin IS NULL) \n");
			sbQuery.append("GROUP BY PL.Folio, CAST(PEN.FFin as DATE), CASE WHEN PEC.PK_Folio IS NOT NULL AND PEN1.Folio IS NOT NULL THEN '1'  \n");
			sbQuery.append("WHEN PEXC.PK_Folio IS NOT NULL AND PEXC.Guia IS NOT NULL THEN '1' ELSE '0' END\n");
			sbQuery.append(") PL WHERE PendienteCerrado = '1' \n");
			sbQuery.append("UNION ALL \n");
			sbQuery.append("SELECT 'Envio' AS tipo, COALESCE(COUNT(Folio), 0) Total FROM @Tabla \n");
			sbQuery.append("WHERE (FFin IS NULL) \n");
		
			log.info(sbQuery.toString());
			
			Map<String, Object> parametros = new HashMap<String, Object>();
			Map<String, Integer> mapReturn = new HashMap<String, Integer>();
			jdbcTemplate.query(sbQuery.toString(), parametros, new RowMapper<Object>() {

				@Override
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					try {
						mapReturn.put(rs.getString("Tipo"), rs.getInt("Total"));
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;
				}
			});
			
			return mapReturn;
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	
	@Override
	public boolean insertarComplementosXML(List<ComplementosPago> complementos) throws ProquifaNetException {
		try {
			for (ComplementosPago complementosPago : complementos) {
				log.info(complementosPago.getRfcReceptor());
				StringBuilder sbQuery = new StringBuilder(" \n");
				Map<String, Object> map = new HashMap<String, Object>();
				sbQuery.append("INSERT INTO ComplementoAux VALUES(:rfcEmisor, :nombreEmisor, :regimenFiscalEmisor, :rfcReceptor, :nombreReceptor, ");
				sbQuery.append(":usoReceptor, :uuid, :fechaTimbrado, :rfcProv, :sello, :noCertificado, :selloSat, :fpago, :forma, :moneda, :monto, ");
				sbQuery.append(":cta, :banco, :rfcCta, :idDocumento, :serie, :folio, :monedaDR, :metodo, :tc, :pagado, :parcialidad) ");
				map.put("rfcEmisor", complementosPago.getRfcEmisor());
				map.put("nombreEmisor", complementosPago.getNombreEmisor());
				map.put("regimenFiscalEmisor", complementosPago.getRegimenFiscal());
				map.put("rfcReceptor", complementosPago.getRfcReceptor());
				map.put("nombreReceptor", complementosPago.getNombreReceptor());
				map.put("usoReceptor", complementosPago.getUsoCFDI());
				map.put("uuid", complementosPago.getUuid());
				map.put("fechaTimbrado", complementosPago.getFechaTimbrado());
				map.put("rfcProv", complementosPago.getRfcProvCertif());
				map.put("sello", complementosPago.getSelloCFD());
				map.put("noCertificado", complementosPago.getNoCertificadoSAT());
				map.put("selloSat", complementosPago.getSelloSAT());
				map.put("fpago", complementosPago.getFechaPago());
				map.put("forma", complementosPago.getFormaDePago());
				map.put("moneda", complementosPago.getMoneda());
				map.put("monto", complementosPago.getMonto());
				map.put("cta", complementosPago.getCtaOrdenante());
				map.put("banco", complementosPago.getNomBancoOrdExt());
				map.put("rfcCta", complementosPago.getRfcEmisorCtaOrd());
				map.put("idDocumento", complementosPago.getIdDocumento());
				map.put("serie", complementosPago.getSerie());
				map.put("folio", complementosPago.getFolio());
				map.put("monedaDR", complementosPago.getMonedaDR());
				map.put("metodo", complementosPago.getMetodoDePagoDR());
				map.put("tc", complementosPago.getTipoCambioDR());
				map.put("pagado", complementosPago.getImpPagado());
				map.put("parcialidad", complementosPago.getNumParcialidad());
				
				jdbcTemplate.update(sbQuery.toString(), map);
			}
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	@Override
	public PackingList obtenerPackingListxFolio(String packingList) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("SELECT PK_PackingList idPackingList, * FROM PackingList WHERE Folio = :folio \n");
			map.put("folio", packingList);
			
			return  jdbcTemplate.queryForObject(sbQuery.toString(), map, new BeanPropertyRowMapper<>(PackingList.class));
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	@Override
	public boolean esPLdeControlado( String folioPL) throws ProquifaNetException {	
		StringBuilder sbQuery = new StringBuilder(" \n");
		sbQuery.append("SELECT COUNT(*) AS Cuantos FROM PPackingList AS PPL \n");
		sbQuery.append("LEFT JOIN EmbalarPedido AS EP ON EP.PK_EmbalarPedido = PPL.FK02_EmbalarPedido \n");
		sbQuery.append("LEFT JOIN PPedidos AS PP ON PP.idPPedido = EP.FK01_PPedido \n");
		sbQuery.append("LEFT JOIN Productos AS Prod ON Prod.idProducto = PP.FK02_Producto \n");
		sbQuery.append("LEFT JOIN PackingList AS PL ON PL.PK_PackingList = PPL.FK01_PackingList \n");
		sbQuery.append("WHERE PL.Folio='"+ folioPL +"' \n");
		sbQuery.append("AND Prod.Control IN ('Mundiales', 'Nacionales')");
		System.out.println(sbQuery.toString());
		int numControlados = super.queryForInt(sbQuery.toString());
		System.out.println("numControlados: " + numControlados);
		if (numControlados > 0) {
			return true;
		}
		return false;
	}
	@Override
	public boolean isPLRecogeEnPROQUIFA(String folioPL) throws ProquifaNetException{
		try {
			
			StringBuilder sbQuery = new StringBuilder("\n");
			sbQuery.append("SELECT COUNT(*) \n"); 
			sbQuery.append("FROM PackingList AS PL\n" ); 
			sbQuery.append("INNER JOIN PPackingList AS PPL ON PPL.FK01_PackingList = PL.PK_PackingList\n" ); 
			sbQuery.append("INNER JOIN EmbalarPedido AS EP ON EP.PK_EmbalarPedido = PPL.Fk02_EmbalarPedido\n" ); 
			sbQuery.append("INNER JOIN PPedidos AS PP ON PP.idPPedido = EP.FK01_PPedido\n" );
			sbQuery.append("INNER JOIN Pedidos AS P ON P.CPedido = PP.CPedido\n" );
			sbQuery.append("WHERE PL.Folio = :folioPL \n" );
			sbQuery.append("AND P.Destino = 'Recoge en PROQUIFA'");
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("folioPL", folioPL);						
			
			Integer cnt = jdbcTemplate.queryForObject(sbQuery.toString(), parametros, Integer.class);
			
			return cnt != null && cnt > 0;
			
			
		} catch (Exception e){
			e.printStackTrace();
			throw new ProquifaNetException(); 
		}
	}
	
	@Override
	public Correo obtenerDatosCorreoRecogeEnProquifa(String folioPL) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("\n");
			
			sbQuery.append("SELECT Cl.vendedor + '@proquifa.net;' + CASE WHEN C.eMail IS NULL THEN '' ELSE C.eMail END AS correo, \n");
			sbQuery.append("P.pedido AS cuerpoCorreo, P.idPedido AS idContacto, CAST(GETDATE() AS DATE)  AS FechaInicio \n");			
			sbQuery.append("FROM PackingList AS PL \n");
			sbQuery.append("INNER JOIN PPackingList AS PPL ON PPL.FK01_PackingList = PL.PK_PackingList\n");
			sbQuery.append("INNER JOIN EmbalarPedido AS EP ON EP.PK_EmbalarPedido = PPL.Fk02_EmbalarPedido\n"); 
			sbQuery.append("INNER JOIN PPedidos AS PP ON PP.idPPedido = EP.FK01_PPedido\n"); 
			sbQuery.append("INNER JOIN Pedidos AS P ON P.CPedido = PP.CPedido\n" ); 
			sbQuery.append("INNER JOIN Contactos	AS C ON P.IDContacto = C.idContacto\n" ); 
			sbQuery.append("INNER JOIN Clientes AS Cl ON P.idCliente = Cl.clave \n"); 
			sbQuery.append("LEFT JOIN (SELECT COUNT(1) AS totalInspeccion, CPedido FROM PPedidos WHERE Estado IN ('En inspección','A facturacion','A programacion', 'Por Embalar', 'A Embalar') GROUP BY CPedido) AS PPE ON PPE.CPedido=PP.CPedido \n"); 
			sbQuery.append("LEFT JOIN (SELECT COUNT(1) AS totalCancelada, CPedido FROM PPedidos WHERE Estado IN ('Cancelada') GROUP BY CPedido) AS PPC ON PPC.CPedido=PP.CPedido \n"); 
			sbQuery.append("LEFT JOIN (SELECT COUNT(1) AS totalEntregada, CPedido FROM PPedidos WHERE Estado IN ('Entregado') GROUP BY CPedido) AS PPEN ON PPEN.CPedido=PP.CPedido \n"); 
			sbQuery.append("LEFT JOIN (SELECT COUNT(1) AS totalPartidas, CPedido FROM PPedidos GROUP BY CPedido) AS PPT ON PPT.CPedido = PP.CPedido \n"); 
			sbQuery.append("WHERE PL.Folio = :folioPL \n"); 
			sbQuery.append("AND P.Destino = 'Recoge en PROQUIFA'-- AND (P.notificacionCorreo <> 1 OR P.notificacionCorreo is NULL) \n" ); 
			sbQuery.append("AND CASE WHEN P.Parciales = 0 THEN (PPT.totalPartidas - COALESCE(PPC.totalCancelada,0) - COALESCE(PPEN.totalEntregada,0) ) ELSE PPE.totalInspeccion END = PPE.totalInspeccion \n"); 
			sbQuery.append("GROUP BY Cl.vendedor, C.eMail, P.pedido, P.idPedido ");
			
			Map<String, Object> map = new HashMap<>();
			map.put("folioPL", folioPL);
			Correo datos = null;
			try {
				datos =  jdbcTemplate.queryForObject(sbQuery.toString(), map, new BeanPropertyRowMapper<>(Correo.class));
			} catch (Exception e) {
				datos = null;
			}
			return datos;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Boolean actualizarEnvioCorreo_RecogeEnProquifa(Long idPedido) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("\n");
			sbQuery.append("UPDATE Pedidos	SET notificacionCorreo = 1 \n");
			sbQuery.append("WHERE idPedido = :idPedido \n");
			
			Map<String, Object> map = new HashMap<>();
			map.put("idPedido", idPedido);
			jdbcTemplate.update(sbQuery.toString(), map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public Boolean actualizaInfoCFDI(String RegimenSocietario, String RegimenFiscal, String curp,String RSocial) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("\n");
			sbQuery.append("UPDATE Clientes	SET RegimenSocietario=:RegimenSocietario,RegimenFiscal=:RegimenFiscal,RSocial=:RSocial \n");
			sbQuery.append("WHERE curp = :curp \n");

			Map<String, Object> map = new HashMap<>();
			map.put("curp",curp);
			map.put("RegimenSocietario",RegimenSocietario);
			map.put("RegimenFiscal",RegimenFiscal);
			map.put("RSocial",RSocial);
			jdbcTemplate.update(sbQuery.toString(), map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Boolean actualizaEdoPpedidos(String CPedido, String Codigo) throws ProquifaNetException {
		try {
			String Estado="En inspección";
			StringBuilder sbQuery = new StringBuilder("\n");
			sbQuery.append("UPDATE ppedidos SET Estado=:Estado\n");
			sbQuery.append("WHERE CPedido = :CPedido \n");
			sbQuery.append("and Codigo = :Codigo \n");

			Map<String, Object> map = new HashMap<>();
			map.put("CPedido",CPedido);
			map.put("Codigo",Codigo);
			map.put("Estado",Estado);
			jdbcTemplate.update(sbQuery.toString(), map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	@Override
	public Boolean actualizaEdoPcompras(String CPedido, String Codigo) throws ProquifaNetException {
		try {
			String Estado="En inspección";
			StringBuilder sbQuery = new StringBuilder("\n");
			sbQuery.append("UPDATE pcompras SET Estado=:Estado\n");
			sbQuery.append("WHERE CPedido = :CPedido \n");
			sbQuery.append("and Codigo = :Codigo \n");

			Map<String, Object> map = new HashMap<>();
			map.put("CPedido",CPedido);
			map.put("Codigo",Codigo);
			map.put("Estado",Estado);
			jdbcTemplate.update(sbQuery.toString(), map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Boolean pedidoConProdsDeStock(Integer usuarioEmbalar) throws ProquifaNetException {
		try{
			StringBuilder sbQuery = new StringBuilder("\n");
			sbQuery.append("SELECT count (*) FROM PPedidos AS PP \n");
			sbQuery.append("INNER JOIN MovimientoStock AS MS on MS.IdPPedido = PP.IdPPedido  \n");
			sbQuery.append("LEFT JOIN EmbalarPedido AS EP on EP.FK01_PPedido = PP.idPPedido  \n");
			sbQuery.append("WHERE EP.FK03_UsuarioEmbalar="+ usuarioEmbalar +" and EP.estado='A Embalar' and EP.FechaEmbalado is NULL  \n");


			System.out.println(sbQuery.toString());
			int num = super.queryForInt(sbQuery.toString());

			if (num > 0) {
				return true;
			}
			return false;
		}catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String mandarAStock(Integer  idPedido) throws ProquifaNetException {
		String resultado=null;
		id=idPedido;
		try {
			String inicio ="SELECT STUFF(( \n";
			String Fin="\n FOR XML PATH('')\n" +
					"), 1, 0, '')\n";

		StringBuilder sbQuery = new StringBuilder("\n");
		sbQuery.append(query());

			sbQuery.append(inicio);
			sbQuery.append("select concat('Insert into PcompraFolioInspeccion (idpcompra,folioInspeccion,EstadoPrevio) Values',' (',idpcompra,',',folioInspeccion,',',EstadoPrevio,')') as resultado from PCompraFolioInspeccion where idPCompra in ( @idPCompra) and FolioInspeccion in (@FolioInspeccion)\n");
			sbQuery.append(Fin);
			resultado=super.queryForString(sbQuery.toString());
			sbQuery = new StringBuilder("");

			sbQuery.append(query());
			sbQuery.append(inicio);
			sbQuery.append("select concat('Insert into PPedidoFolioInspeccion (idppedido,folioInspeccion,EstadoPrevio) Values(',\n" +
				"idppedido,',',folioInspeccion,',',EstadoPrevio,')') from PPedidoFolioInspeccion where idPPedido in ( @idPPedido) and FolioInspeccion in (@FolioInspeccion )");
			sbQuery.append(Fin);
			resultado= resultado+ "\n"+super.queryForString(sbQuery.toString());

			sbQuery = new StringBuilder("");
		 	sbQuery.append(query());
			sbQuery.append(inicio);
			sbQuery.append("select concat ('Insert into inspeccionOC (id,idpcompra,pedimento,DisponibilidadPedimento,Guia,MesCaducidad,AnoCaducidad,Manejo,lote,Documentacion,comentarios,idioma,fecha,Inspector,Manejo_Transporte,FPedimento,Aduana,rowguid,piezas,Tiempo_Inspeccion,DoctoSDS,videoPartida,Prioridad,Apartado,AplicaDocumentacion,imagenRechazo) values (',id,',',idPCompra,',',Pedimento,',',DisponibilidadPedimento, ',',Guia,',',MesCaducidad,',',AnoCaducidad,',',Manejo,',',lote,',',Documentacion,',',comentarios,',',idioma,',',fecha,',',Inspector COLLATE SQL_Latin1_General_CP1_CI_AS,',',Manejo_Transporte,',',FPedimento,',',Aduana,',',rowguid,',',piezas,',',Tiempo_Inspeccion,',',DoctoSDS,',',videoPartida,',',Prioridad,',',Apartado,',',AplicaDocumentacion,',',imagenRechazo,')') as resultado from inspeccionOC where idpcompra= @idPCompra \n");
			sbQuery.append(Fin);
			resultado= resultado+ "\n"+super.queryForString(sbQuery.toString());

			sbQuery = new StringBuilder("");
			sbQuery.append(query());
			sbQuery.append(inicio);
			sbQuery.append("select concat ('Insert into Pieza (IdPieza,Codigo,Fabrica,IdPCompra,Catalogo,Descripcion,Edicion,Idioma,FisicamenteC,Rechazos,Presentacion,Lote,LoteTxt,Caducidad,MesCaducidad,AnoCaducidad,Documentacion,Documento,Despachable,FFrente,FArriba,FABajo,Instrucciones,AStock,ADestruccion,Cantidad,idPCompraPadre) values (',IdPieza,',',Codigo,',',Fabrica,',',IdPCompra,',',Catalogo,',',Descripcion,',',Edicion,',',Idioma,',',FisicamenteC,',',Rechazos,',',Presentacion,',',Lote,',',LoteTxt,',',Caducidad,',',MesCaducidad,',',AnoCaducidad,',',Documentacion,',',Documento,',',Despachable,',',FFrente,',',FArriba,',',FABajo,',',Instrucciones,',',AStock,',',ADestruccion,',',Cantidad,',',idPCompraPadre,')') from pieza where idpcompra= @idPCompra \n");
			sbQuery.append(Fin);
			resultado= resultado+ "\n"+super.queryForString(sbQuery.toString());

			sbQuery = new StringBuilder("");
			sbQuery.append(query());
			sbQuery.append(inicio);
			sbQuery.append("select concat ('Insert into EstadoPCompra (Folio,IdPcompra,FInicio,FFin,Tipo) values (',Folio,',',IdPCompra,',',FInicio,',',FFin,',',Tipo,')') from EstadoPCompra where idPCompra= @idPCompra and Tipo ='En inspección'");
			sbQuery.append(Fin);
			resultado= resultado+ "\n"+super.queryForString(sbQuery.toString());

			sbQuery = new StringBuilder("");
			sbQuery.append(query());
			sbQuery.append(inicio);
			sbQuery.append("select concat ('Insert into EstadoPPedido (Folio,IdPPedido,FInicio,FFin,Tipo) values (',Folio,',',IdPPedido,',',FInicio,',',FFin,',',Tipo,')') from EstadoPPedido where idPPedido= @idPPedido and Tipo ='En inspección'");
			sbQuery.append(Fin);
			resultado= resultado+ "\n"+super.queryForString(sbQuery.toString());

			sbQuery = new StringBuilder("");
			sbQuery.append(query());
			sbQuery.append(inicio);
			sbQuery.append("select concat ('Insert into PackingListFolioInspeccion (id,packingList,FolioInspeccion,Status,OrdenCompra,FolioPacking,Pedimento,DisponibilidadPedimento,Guia,FPedimento,Aduana) values (',id,',',PackingList,',',FolioInspeccion,',',Status,',',OrdenCompra,',',FolioPacking,',',Pedimento,',',DisponibilidadPedimento,',',Guia COLLATE SQL_Latin1_General_CP1_CI_AS,',',FPedimento,',',Aduana,')') from PackingListFolioInspeccion where OrdenCompra= @idCompra and FolioInspeccion= @FolioInspeccion");
			sbQuery.append(Fin);
			resultado= resultado+ "\n"+super.queryForString(sbQuery.toString());

			sbQuery = new StringBuilder("");
			sbQuery.append(query());
			sbQuery.append(inicio);
			sbQuery.append("select concat ('Insert Pendiente (folio,docto,partida,ffinicio,ffin,responsable,tipo) values (',folio,',',Docto,',',Partida,',',FInicio,',',FFin,',',Responsable,',',tipo,')') from pendiente where docto in ( @idCompra) and Tipo ='Monitorear OC'--Monitorear OC");
			sbQuery.append(Fin);
			resultado= resultado+ "\n"+super.queryForString(sbQuery.toString());
try {
			sbQuery = new StringBuilder("");
			sbQuery.append(query());
			sbQuery.append("update ppedidos set estado='Compra' where idppedido in (@idPPedido)\n");
			sbQuery.append("update pcompras set estado='En tránsito Matriz' ,FolioInspeccion=NULL where idpcompra in (@idPCompra)\n");
			sbQuery.append("delete from inspeccionOC where idPCompra in (@idPCompra)\n");
			sbQuery.append("delete from pieza where idPCompra in (@idPCompra)\n");
			sbQuery.append("delete from EstadoPCompra where Folio =@edoFPCompra\n");
			sbQuery.append("delete from estadoppedido where Folio =@edoFPPedido\n");
			sbQuery.append("delete from PCompraFolioInspeccion where idPCompra in (@idPCompra) and FolioInspeccion in( @FolioInspeccion)\n");
			sbQuery.append("delete from PPedidoFolioInspeccion where idPPedido in (@idPPedido) and FolioInspeccion in( @FolioInspeccion)\n");
			sbQuery.append("update pendiente set ffin=null where folio =@Pendiente\n");
			Map<String, Object> map = new HashMap<>();
			map.put("","");
			sbQuery.append(" insert into historialSistema (fecha,Usuario,Transaccion,VentanaOrigen,Subrutina) values (GetDate(),'Soporte','"+resultado.replaceAll("<*.resultado>","\n")+ "\n"+sbQuery.toString().replaceAll("'","")+"','Postman','embalar/mandarAStock')");
			resultado= resultado+ "\n";

			jdbcTemplate.update(sbQuery.toString(),map);

}
catch (Exception e){
	e.printStackTrace();
}
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		return	resultado;
	}
	public StringBuilder query(){
		StringBuilder sbQuery = new StringBuilder("\n");
		sbQuery.append("declare @idPPedido as int ="+id+" \n");
		sbQuery.append("declare @idPCompra as int\n");
		sbQuery.append("declare @FolioInspeccion as varchar(14)\n");
		sbQuery.append("declare @idCompra as varchar(11) \n");
		sbQuery.append("declare @edoFPCompra as  int\n");
		sbQuery.append("Declare @edoFPPedido as int \n");
		sbQuery.append("declare @Pendiente as int \n");
		sbQuery.append("Select @idPCompra= idPCompra from PCompras where FK03_PPedido= @idPPedido \n");
		sbQuery.append("select @FolioInspeccion=  FolioInspeccion from PCompras where idpcompra=@idPCompra \n");
		sbQuery.append("select @idCompra= compra from pcompras where idpcompra=@idPCompra \n");
		sbQuery.append("select @edoFPCompra = folio from EstadoPCompra where idPCompra= @idPCompra and Tipo ='En inspección'\n");
		sbQuery.append("select @edoFPPedido= folio from EstadoPPedido where idPPedido= @idPPedido and Tipo ='En inspección'\n");
		sbQuery.append("select @pendiente = folio from pendiente where docto in ( @idCompra) and Tipo ='Monitorear OC'\n");
		return sbQuery;
	}

}
