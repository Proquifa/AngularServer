package com.proquifa.net.persistencia.despachos.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.proquifa.net.modelo.comun.Pendiente;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.despachos.InspeccionPorFolio;
import com.proquifa.net.modelo.despachos.PartidaInspeccion;
import com.proquifa.net.modelo.despachos.parametrosInspeccion;
import com.proquifa.net.modelo.despachos.totalesInspeccionProducto;
import com.proquifa.net.modelo.despachos.ubicacionPorManejo;
import com.proquifa.net.modelo.ventas.PartidaPedido;
import com.proquifa.net.modelo.ventas.admoncomunicacion.Correo;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.compras.comprador.mapper.OrdenPartidaInspeccionRowMapper;
import com.proquifa.net.persistencia.compras.comprador.mapper.TotalesInspeccionRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.PendienteRowMapper;
import com.proquifa.net.persistencia.despachos.InspeccionDAO;
import com.proquifa.net.persistencia.despachos.impl.mapper.PartidaInspeccionPorPesoRowMapper;
import com.proquifa.net.persistencia.despachos.impl.mapper.PartidaInspeccionxPieza;
import com.proquifa.net.persistencia.despachos.impl.mapper.ubicacionPorExistenciaRowMapper;
import com.proquifa.net.persistencia.despachos.mapper.PartidaInspeccionOCRowMapper;
import com.proquifa.net.persistencia.despachos.mapper.TotalPiezasInspeccionadas_totalTiempo;

@Repository
public class InspeccionDAOImpl extends DataBaseDAO implements InspeccionDAO {
	
	final Logger log = LoggerFactory.getLogger(InspeccionDAOImpl.class);
	
	private SimpleDateFormat formatterSinTiempo = new SimpleDateFormat("yyyyMMdd");

	@SuppressWarnings("unchecked")
	@Override
	public String obtenerResponsableInspeccion() throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("select Usuario from Empleados where FK01_Funcion=11 AND Fase > 0");
			return (String) super.jdbcTemplate.queryForObject(sbQuery.toString(),new HashMap(), String.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public List<PartidaInspeccion> obtenerPartidaInspeccion() throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("select * from PartidadeCompraenInspeccion order by puntos desc;");
			List<PartidaInspeccion> lstResult = jdbcTemplate.query(sbQuery.toString(), new OrdenPartidaInspeccionRowMapper());
			
		   return lstResult;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public PartidaInspeccion obtenerPartidaAInspeccionar() throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			StringBuilder sbQuery = new StringBuilder("select top 1 * from PartidadeCompraenInspeccion as pc where pc.idPcompra not in("
					+ "select distinct pc.idPcompra from PartidadeCompraenInspeccion left join FolioInspeccionTemporal as fi"
					+ " on  pc.idPCompra = fi.fk01_idPcompra where fi.fk01_idPcompra is not null) order by puntos desc;");
			PartidaInspeccion lstResult = new PartidaInspeccion();
			return lstResult = (PartidaInspeccion) jdbcTemplate.queryForObject(sbQuery.toString(), map, new OrdenPartidaInspeccionRowMapper());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pendiente verificaExistePendienteDeCompra(String compra) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("SELECT * FROM Pendiente where Docto = '"+ compra +"' and Tipo = 'Inspeccionar OC' and FFin is null");
			
			Pendiente pen = new Pendiente();
			List<Pendiente> lstResult = jdbcTemplate.query(sbQuery.toString(), new PendienteRowMapper());
			
			if(lstResult.size() > 0)
			{
				pen = lstResult.get(0);
			}
		   return pen;
		   
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}

	@Override
	public Boolean actualizarPendienteCompra(String Partida, String Docto) throws ProquifaNetException  {
//		try {
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("Partida", Partida);
//			map.put("Docto", Docto);
//			String query= "UPDATE Pendiente SET Partida = :Partida WHERE Docto = :Docto and Tipo = 'Inspeccionar OC'";
//			super.jdbcTemplate.update(query,map);
//			return true;
//		
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new ProquifaNetException();
//		}
		
		return null;
	}
	
	
	
	@Override
	public Integer obtenerContadorPartidasEnInspeccion() {
		try {
			String query = "SELECT COALESCE(SUM(Cant),0) from PartidadeCompraenInspeccion where estado = 'En inspección'";
			Integer totales = super.queryForInt(query);
			log.info("=*=*TOTAL*=*=: " + totales.toString());
			return totales;
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public PartidaInspeccion obtenerInspeccionOCxIdCompra(Long idPCompra){
		String query = "";
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idPCompra", idPCompra);
			query = "SELECT top 1 * FROM InspeccionOC WHERE idPCompra = :idPCompra";
			return (PartidaInspeccion) super.jdbcTemplate.queryForObject(query, map, new PartidaInspeccionOCRowMapper());
		}catch (Exception e) {
			//log.info("Error: " + e.getMessage());
			return null;
		}
	}
	
	
	@Override
	public Date obtenerFechaInicioDelEstadoPCompra(Long idPCompra){
		try{
			Date fechaInicio = null;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idPCompra", idPCompra);
			fechaInicio = (Date) super.jdbcTemplate.queryForObject("SELECT top 1 FInicio FROM EstadoPCompra WHERE idPCompra= :idPCompra",map,java.util.Date.class);
			return fechaInicio;
		}catch (Exception e) {
			//log.info("Error: " + e.getMessage());
			return null;
		}
	}
	
	
	@Override
	public Date obtenerFechaInicioDelEstadoPpedido(Long idPpedido){
		try{
			Date fechaInicio = null;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idPpedido", idPpedido);
			fechaInicio = (Date) super.jdbcTemplate.queryForObject("SELECT top 1 FInicio FROM EstadoPPedido WHERE idPPedido= :idPpedido",map,java.util.Date.class);
			return fechaInicio;
		}catch (Exception e) {
//			logger.error("Error: " + e.getMessage());
			return null;
		}
	}
	
	@Override
	public Long insertarEstadoPPedido(Long idPpedido, String tipo, Date fechaInicio)
			throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idPpedido", idPpedido);
			map.put("fechaInicio", fechaInicio);
			map.put("tipo", tipo);
			super.jdbcTemplate.update("INSERT INTO EstadoPPedido(idPPedido,FInicio,Tipo) VALUES(:idPpedido,:fechaInicio,:tipo) ",map);
			Long folio = super.queryForLong("SELECT IDENT_CURRENT ('EstadoPPedido')"); 
			return folio;
		} catch (Exception e) {
			//log.info("Error: " + e.getMessage());
			return -1L;
		}
	}
	
	@Override
	public Boolean insertaPiezasInspeccionadas(List<PartidaInspeccion> lstPieza) throws ProquifaNetException{
		try {
			for (PartidaInspeccion pza : lstPieza) {
				this.insertaPiezas(pza);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
	
	@Override
	public Boolean insertaPiezas(PartidaInspeccion pza) throws ProquifaNetException{
		try {
			
				
				Boolean eCorrecta;
				Boolean icorrecto;
				Boolean caducidadC;
				String mesC = "";
				String anioC = "";
				
				if(pza.getEsPublicacion() != null && pza.getEsPublicacion())
				{
					eCorrecta = pza.getEdicionCorrecta();
					icorrecto = pza.getIdiomaCorecto();
					caducidadC = null;
					mesC = null;
					anioC = null;
					
				}
				else{
					eCorrecta = null;
                    icorrecto = null;
                    caducidadC = pza.getCaducidadCorrecta();
					mesC = pza.getMesCaduca();
					anioC = pza.getAnoCaduca();
				}
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("codigo", pza.getCodigo());
				map.put("fabrica", pza.getFabrica());
				map.put("idCompra", pza.getIdCompra());
				map.put("catalogo", pza.getCatCorrecto());
				map.put("descripcion", pza.getDescripcionCorrecta());
				map.put("edicionC",eCorrecta);
				map.put("idiomaC", icorrecto);
				map.put("fisicamenteC", pza.getFisicamenteCorrecto());
				map.put("reporte", pza.getReporte());
				map.put("presentacionC", pza.getPresentacionCorrecta());
				map.put("loteC", pza.getLoteCorrecto());
				map.put("lote", pza.getLote());
				map.put("caducidadC", caducidadC);
				map.put("mesC", mesC );
				map.put("anioC", anioC);
				map.put("documentacion", pza.getDocumentacionCorrecta());
				map.put("despachable", pza.getDespachable());
				map.put("fFrente", pza.getfFrente());
				map.put("fArriba", pza.getfArriba());
				map.put("fAbajo", pza.getfAbajo());
				map.put("cant", 1);
				map.put("documento", pza.getDocumento());

				StringBuilder sbQuery = new StringBuilder("INSERT INTO Pieza (Codigo,Fabrica,IdPCompra,Catalogo,Descripcion,Edicion,Idioma,FisicamenteC,Rechazos,Presentacion,Lote,LoteTxt,Caducidad,MesCaducidad,AnoCaducidad,Documentacion,Documento,Despachable,FFrente,FArriba,FABajo,Cantidad)  VALUES( ");
				sbQuery.append(":codigo, :fabrica, :idCompra, :catalogo, :descripcion, :edicionC, :idiomaC, :fisicamenteC, :reporte, :presentacionC, :loteC, :lote, :caducidadC, :mesC, :anioC, :documentacion,:documento, :despachable, :fFrente , :fArriba , :fAbajo , :cant ");
				sbQuery.append(") \n");
				jdbcTemplate.update(sbQuery.toString(),map);
				
			return true;
		}catch (Exception e) {
			//log.info(e);
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
			.setRollbackOnly();
			return false;
		}
	}
	
	
	@Override
	public Long insertarEstadoPCompraDeInspeccion(Long idPCompra, String tipo, Date fechaInicio)
			throws ProquifaNetException {
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idPCompra", idPCompra);
			map.put("tipo", tipo);
			map.put("fechaInicio", fechaInicio);
			
			super.jdbcTemplate.update("INSERT INTO EstadoPCompra(idPCompra,FInicio,Tipo) VALUES(:idPCompra,:fechaInicio,:tipo) ",map);
			Long folio = super.queryForLong("SELECT IDENT_CURRENT ('EstadoPCompra')");
			return folio;
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
			.setRollbackOnly();
			return -1L;
		}
	}
	
	
	@Override
	public Long insertarInspeccionOCxInspeccion(PartidaInspeccion pIns, String tabla) {
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idPCompra", pIns.getIdPartidaCompra());
			map.put("pedimento", pIns.getPedimento());
			map.put("pdisponibilidad", pIns.getDisponibilidadPedimento());
			map.put("guia", pIns.getGuia());
			map.put("mesCaduca", pIns.getMesCaduca());
			map.put("anioCaduca", pIns.getAnoCaduca());
			map.put("manejo", pIns.getManejo());
			map.put("lote", pIns.getLote());
			map.put("documento", pIns.getDocumento());
			map.put("comentarios", pIns.getComentarios());
			map.put("idioma", pIns.getIdioma());
			map.put("edicion", pIns.getEdicion());
			map.put("ffInspeccion", pIns.getFechaFinInspeccion());
			map.put("inspector", pIns.getInspector());
			map.put("majejoT", pIns.getManejoTransporte());
			map.put("fPedimento", pIns.getFechaPedimento());
			map.put("aduana", pIns.getAduana());
			map.put("cantP", pIns.getCantPiezas());
			map.put("tInspeccion", pIns.getTiempoInspeccionEnSegundos());
	
			String query = "INSERT INTO "+ tabla +" ("
					+ "idPCompra,Pedimento,DisponibilidadPedimento,Guia,MesCaducidad,AnoCaducidad,Manejo,Lote,Documentacion,Comentarios,Idioma,Edicion,Fecha,Inspector,"
						   + "Manejo_Transporte,FPedimento,Aduana,Piezas,Tiempo_Inspeccion) VALUES ";
			query+= "(:idPCompra,:pedimento,:pdisponibilidad,:guia,:mesCaduca,:anioCaduca,:manejo,:lote,:documento,:comentarios,:idioma,:edicion,:ffInspeccion,:inspector,:majejoT,:fPedimento,:aduana,:cantP,:tInspeccion)";
			super.jdbcTemplate.update(query, map);
			Long pInspeccion = super.queryForLong("SELECT IDENT_CURRENT ('"+ tabla +"')");
			return pInspeccion;
			
		} catch (Exception e) {
			return -1L;
		}
	}
	
	@Override
	public Long insertarInspeccionOC(PartidaInspeccion pIns, String tabla) {
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			//map.put("idPartida", pIns.getIdPartidaCompra());
			map.put("idPartida", pIns.getIdCompra());
			map.put("pedimento", pIns.getPedimento());
			map.put("disponibilidadP", pIns.getDisponibilidadPedimento());
			map.put("guia", pIns.getGuia());
			map.put("mesCaduca", pIns.getMesCaduca());
			map.put("anioCaduca", pIns.getAnoCaduca());
			map.put("manejo", pIns.getManejo());
			map.put("lote", pIns.getLote());
			map.put("documento", pIns.getDocumento());
//			map.put("tipoDoc", pIns.getTipoDocumento());
			map.put("comentarios", pIns.getComentarios());
			map.put("idioma", pIns.getIdioma());
			map.put("edicion", pIns.getEdicion());
			map.put("ffInspeccion", pIns.getFechaFinInspeccion());
			map.put("inspector", pIns.getInspector());
			map.put("video", pIns.getVideoPartida());
			map.put("documento", pIns.getDocumento());
			map.put("documentoSDS", pIns.getDocumentoSDS());
			map.put("prioridad", pIns.getPrioridad().replace("P", ""));
			map.put("aplicaDocumentacion", pIns.isAplicaDocumentacion());
			map.put("ImagenRechazo", pIns.getImagenRechazo());
			map.put("Manejo_Transporte", pIns.getManejoTransporte());
			map.put("piezas", pIns.getCantPiezas());
			map.put("idUsuario", pIns.getIdMarca());
			String query = "INSERT INTO "+ tabla +" (idPCompra,Pedimento,DisponibilidadPedimento,Guia,MesCaducidad,AnoCaducidad,Manejo,Lote,"+
						   "Documentacion,Comentarios,Idioma,Edicion,Fecha,Inspector,Manejo_Transporte, Piezas,videoPartida,DoctoSDS, prioridad,"
						   + "Apartado, AplicaDocumentacion, ImagenRechazo) VALUES ";
			query+= "(:idPartida,:pedimento,:disponibilidadP,:guia,:mesCaduca,:anioCaduca,:manejo,:lote,:documento,:comentarios,:idioma,:edicion,:ffInspeccion,:inspector, :Manejo_Transporte ,"
					+ ":piezas, :video, :documentoSDS, :prioridad, :idUsuario, :aplicaDocumentacion, :ImagenRechazo)";
			//log.info(query);
			super.jdbcTemplate.update(query, map);
			//log.info(query);
			Long pInspeccion = super.queryForLong("SELECT IDENT_CURRENT ('"+ tabla +"')");
			return pInspeccion;
			
		} catch (Exception e) {
			log.info(e.getMessage());
			return -1L;
		}
	}
	
	@Override
	public Boolean actualizarEstadoPCompra(Long idPCompra)
			throws ProquifaNetException {
		try {
			Date fecha = new Date();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("fecha", fecha);
			map.put("idPCompra", idPCompra);
						
			super.jdbcTemplate.update("UPDATE EstadoPCompra SET FFin= :fecha WHERE idPCompra= :idPCompra AND FFin IS NULL",map);
			return true;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public Boolean actualizarEstadoPPedido(Long idPpedido)
			throws ProquifaNetException {
		try {
			Date fecha = new Date();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("fecha", fecha);
			map.put("idPpedido", idPpedido);
			
			super.jdbcTemplate.update("UPDATE EstadoPPedido SET FFin= :fecha WHERE idPPedido= :idPpedido AND FFin IS NULL",map);
			return true;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public Boolean actualizarInspeccionOCPorInspeccion(String manejo,String lote, String inspector, String manejoT,Long piezas, Long tiempoDe_Inspeccion, Long idCompra, String video, String documento, String documentoSDS, String prioridad, boolean aplicaDocumentos, String imagenRechazo, String idioma, String edicion, String comentario) {
		try {
			Date fecha = new Date();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("manejo", manejo);
			map.put("lote", lote);
			map.put("fecha",fecha);
			map.put("inspector", inspector );
			map.put("manejoT", manejoT);
			map.put("piezas", piezas);
			map.put("tiempoDe_Inspeccion", tiempoDe_Inspeccion);
			map.put("idCompra", idCompra);
			map.put("video",video);
			map.put("documento", documento);
			map.put("documentoSDS", documentoSDS);
			map.put("prioridad", prioridad.replace("P", ""));
			map.put("aplicaDocumentacion", aplicaDocumentos);
			map.put("imagenRechazo", imagenRechazo);
			map.put("idioma", idioma);
			map.put("edicion", edicion);
			map.put("comentario", comentario);
			
			super.jdbcTemplate.update("UPDATE InspeccionOC SET Manejo = :manejo, Lote = :lote, Documentacion = :documento, Fecha = :fecha ,Inspector = :inspector, Manejo_Transporte = :manejoT, "
									+ "Piezas = :piezas, Tiempo_Inspeccion = :tiempoDe_Inspeccion, videoPartida = :video, DoctoSDS = :documentoSDS, Prioridad = :prioridad, AplicaDocumentacion = :aplicaDocumentacion, ImagenRechazo = :imagenRechazo, idioma = :idioma, edicion= :edicion,"
									+ "Comentarios = :comentario  where idPCompra = :idCompra ", map);
			return true;
		} catch (Exception e) {
		e.printStackTrace();
			return false;
		}
	}

	@Override
	public String obtenerFolioDeInspecionPoridCompra(Long idPCompra){
		try{
			String folioInspeccion = "";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idPCompra", idPCompra);
			folioInspeccion = (String) super.jdbcTemplate.queryForObject("SELECT top 1 FolioInspeccion FROM PCompraFolioInspeccion WHERE idPCompra= :idPCompra",map, String.class);
			return folioInspeccion;
		}catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Boolean cerrarPendienteOC (String Docto) throws ProquifaNetException
	{
		try {
			Date fechaFin = new Date();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("docto", Docto);
			map.put("fechaFin", fechaFin);
			super.jdbcTemplate.update("UPDATE Pendiente SET FFin= :fechaFin WHERE Tipo = 'Inspeccionar OC' AND FFin IS NULL AND Docto= :docto", map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public totalesInspeccionProducto obtenerTotalesInspeccion (String inspector,Date iQuincena,Date fQuincena) throws ProquifaNetException
	{
		try {
			
			StringBuilder sbQuery = new StringBuilder("SELECT SUM(porAno)t_ano,SUM(porMes) t_mes,SUM(porQuincena) t_quincena,SUM(porDia) t_dia,SUM(t_h) t_hallazgos ");
			sbQuery.append("\n FROM ( ");
			sbQuery.append("\n SELECT CASE WHEN ins1.id IS NOT NULL THEN 1 ELSE 0 END porAno,CASE WHEN ins1.id IS NOT NULL THEN CASE WHEN P.Despachable = 1 THEN 0 ELSE 1 END END t_h, ");
			sbQuery.append("\n CASE WHEN ins2.id IS NOT NULL THEN 1 ELSE 0 END porMes, ");
			sbQuery.append("\n CASE WHEN ins3.id IS NOT NULL THEN 1 ELSE 0 END PorQuincena, ");
			sbQuery.append("\nCASE WHEN ins4.id IS NOT NULL THEN 1 ELSE 0 END porDia ");
			sbQuery.append("\n FROM Pieza P ");
			sbQuery.append("\n LEFT JOIN InspeccionOC ins1 ON p.IdPCompra = ins1.idPCompra AND ins1.piezas IS NOT NULL AND ins1.Inspector = '").append(inspector).append("' AND YEAR(ins1.Fecha) <= YEAR(getdate())");
			sbQuery.append("\n LEFT JOIN InspeccionOC ins2 ON p.IdPCompra = ins2.idPCompra AND ins2.piezas IS NOT NULL AND ins2.Inspector = '").append(inspector).append("' AND MONTH(ins2.Fecha) = MONTH(getdate()) AND YEAR(ins2.Fecha) = YEAR(getdate())");
			sbQuery.append("\n LEFT JOIN InspeccionOC ins3 ON p.IdPCompra = ins3.idPCompra AND ins3.piezas IS NOT NULL AND ins3.Inspector = '").append(inspector).append("'");
			sbQuery.append("AND ins3.fecha >= '").append(formatterSinTiempo.format(iQuincena)).append("'  and ins3.fecha <= '").append(formatterSinTiempo.format(fQuincena)).append("'");
			sbQuery.append("\n LEFT JOIN InspeccionOC ins4 ON p.idPCompra = ins4.idPCompra AND ins4.piezas IS NOT NULL AND ins4.Inspector = '").append(inspector).append("' AND CAST(ins4.fecha as Date) = cast(GETDATE() as DATE) AND MONTH(ins4.Fecha) = MONTH(getdate()) ");
			sbQuery.append("AND YEAR(ins4.Fecha) = YEAR(getdate()) ) piezas ");

			log.info(sbQuery.toString());
		
			return (totalesInspeccionProducto) super.jdbcTemplate.queryForObject(sbQuery.toString(),new HashMap(), new TotalesInspeccionRowMapper());
		  
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PartidaInspeccion> obtenerPiezasPorFecha (String tipo, Date fechaI, Date fechaF, String inspector) throws ProquifaNetException
	{
		try {
			
			StringBuilder sbQuery = new StringBuilder("");
			sbQuery.append(" DECLARE @tipo AS Varchar(max) = '").append(tipo).append("'");

			sbQuery.append(" \n SELECT id,Piezas,Tiempo_Inspeccion,Fecha, Tipo_Producto = @tipo,Prioridad FROM InspeccionOC " );
			if(tipo.equalsIgnoreCase("ano"))
			{
				sbQuery.append(" WHERE YEAR(InspeccionOC.Fecha) = YEAR(getdate()) ");
			}

			else  if(tipo.equalsIgnoreCase("mes"))
			{
				sbQuery.append(" WHERE  MONTH(InspeccionOC.Fecha) = MONTH(getdate()) AND YEAR(InspeccionOC.Fecha) = YEAR(getdate()) ");
			}

			else  if(tipo.equalsIgnoreCase("quincena"))
			{
				sbQuery.append(" WHERE InspeccionOC.fecha >= '").append(formatterSinTiempo.format(fechaI)).append("'  and InspeccionOC.fecha <= '").append(formatterSinTiempo.format(fechaF)).append("'");
			}

			else  if(tipo.equalsIgnoreCase("dia"))
			{
				sbQuery.append(" WHERE CAST(InspeccionOC.fecha as Date) = cast(GETDATE() as DATE) AND MONTH(InspeccionOC.Fecha) = MONTH(getdate()) ");
				sbQuery.append("AND YEAR(InspeccionOC.Fecha) = YEAR(getdate()) ");
			}
			else{
				sbQuery.append(" WHERE CAST(InspeccionOC.fecha as Date) = CAST('").append(formatterSinTiempo.format(fechaF)).append("' as Date)");
			}
				

			sbQuery.append(" AND Inspector = '").append(inspector).append("'");

			log.info(sbQuery.toString());

			
			List<PartidaInspeccion> lista = super.jdbcTemplate.query(sbQuery.toString(), new PartidaInspeccionxPieza());
			return lista;
		  
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PartidaInspeccion> obtenerPiezasEstadisticas(String tipo, Date fechaI, Date fechaF, String inspector) throws ProquifaNetException
	{
		try {
			
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append(" \n");
			
			sbQuery.append("SELECT SUM(COALESCE(Piezas,0)) Piezas, SUM(COALESCE(Tiempo_Inspeccion,0) ) Tiempo_Inspeccion, MONTH(Fecha) Fecha, Tipo_Producto = 'ano', COALESCE(Prioridad,3) Prioridad\n");
			sbQuery.append("FROM InspeccionOC WHERE YEAR(InspeccionOC.Fecha) = YEAR(getdate()) \n");
			sbQuery.append(" AND Inspector = '").append(inspector).append("'");
			sbQuery.append("GROUP BY MONTH(Fecha), Prioridad\n");
					 
			sbQuery.append("UNION ALL\n");
					 
			sbQuery.append("SELECT SUM(COALESCE(Piezas,0)) Piezas, SUM(COALESCE(Tiempo_Inspeccion,0) ) Tiempo_Inspeccion, DAY(Fecha) Fecha, Tipo_Producto = 'mes', COALESCE(Prioridad,3) Prioridad\n");
			sbQuery.append("FROM InspeccionOC \n");
			sbQuery.append("WHERE  MONTH(InspeccionOC.Fecha) = MONTH(getdate()) AND YEAR(InspeccionOC.Fecha) = YEAR(getdate()) \n");
			sbQuery.append(" AND Inspector = '").append(inspector).append("'");
			sbQuery.append("GROUP BY DAY(Fecha), Prioridad\n");
					 
			sbQuery.append("UNION ALL\n");
					 
			sbQuery.append("SELECT SUM(COALESCE(Piezas,0)) Piezas, SUM(COALESCE(Tiempo_Inspeccion,0) ) Tiempo_Inspeccion, DAY(Fecha) Fecha, Tipo_Producto = 'quincena', COALESCE(Prioridad,3) Prioridad\n");
			sbQuery.append("FROM InspeccionOC \n");
			sbQuery.append(" WHERE InspeccionOC.fecha >= '").append(formatterSinTiempo.format(fechaI)).append("'  and InspeccionOC.fecha <= '").append(formatterSinTiempo.format(fechaF)).append("'");
			sbQuery.append(" AND Inspector = '").append(inspector).append("'");
			sbQuery.append("GROUP BY DAY(Fecha), Prioridad\n");
			
			sbQuery.append("UNION ALL\n");
			
			sbQuery.append("SELECT SUM(COALESCE(Piezas,0)) Piezas, SUM(COALESCE(Tiempo_Inspeccion,0) ) Tiempo_Inspeccion, MONTH(Fecha) Fecha, Tipo_Producto = 'prioridades', COALESCE(Prioridad,3) Prioridad\n");
			sbQuery.append("FROM InspeccionOC WHERE YEAR(InspeccionOC.Fecha) <= YEAR(getdate()) \n");
			sbQuery.append(" AND Inspector = '").append(inspector).append("'");
			sbQuery.append("GROUP BY MONTH(Fecha), Prioridad\n");

			log.info(sbQuery.toString());

			
			List<PartidaInspeccion> lista = super.jdbcTemplate.query(sbQuery.toString(), new PartidaInspeccionxPieza());
			return lista;
		  
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	@Override
	public Long obtenerTotalPartidasInspeccionadas(String inspector) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("inspector", inspector);
			String query = "select COUNT(distinct idPCompra) from InspeccionOC where Inspector = :inspector and YEAR(InspeccionOC.Fecha) <= YEAR(getdate()) and piezas is not null ";
			Long numPartidas = super.queryForLong(query,map); 
			return numPartidas;
			
		} catch (Exception e) {
			return -1L;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Date obtenerFechaDePenultimaInspeccion() throws ProquifaNetException
	{
		try {
			StringBuilder sbQuery = new StringBuilder("");
			sbQuery.append("SELECT top(1)Fecha FROM InspeccionOC WHERE Fecha < GETDATE()ORDER BY Fecha DESC");
			return (Date) super.jdbcTemplate.queryForObject(sbQuery.toString(),new HashMap(), Date.class);
		  
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public totalesInspeccionProducto obtenerTotatalesDepiezasInspeccionadasyTiempoTotaldeInspeccionde30diasAtras() throws ProquifaNetException
	{
		try {
			StringBuilder sbQuery = new StringBuilder("");
			sbQuery.append("SELECT SUM(Piezas)t_piezas,SUM(Tiempo_Inspeccion)t_tiempo FROM InspeccionOC ");
			sbQuery.append(" WHERE (DATEDIFF(day, CAST(Fecha as Date),GETDATE())<= 30) ");
			
			return (totalesInspeccionProducto) super.jdbcTemplate.queryForObject(sbQuery.toString(), new HashMap(), new TotalPiezasInspeccionadas_totalTiempo());
		  
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}

	@Override
	public Long obtenerNumPiezasMaximasDeinspeccionPorUsuario(String inspector) throws ProquifaNetException { 
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			StringBuilder sbQuery = new StringBuilder("SELECT MAX(piezas) maxPiezas FROM(");
			sbQuery.append("\n SELECT SUM(P) piezas, fecha from");
			sbQuery.append("\n (SELECT Inspector,CAST(Fecha as Date)Fecha,CASE WHEN Piezas is null THEN 0 ELSE Piezas END P from InspeccionOC where Inspector = '").append(inspector).append("'");
			sbQuery.append("\n and CAST(Fecha as Date) < GETDATE())piezas");
			sbQuery.append("\n group by CAST(Fecha as Date))pzaIns");
			log.info(sbQuery.toString()); 
			Long maxPiezasInspeccionadas = super.queryForLong(sbQuery.toString(),map); 
			return maxPiezasInspeccionadas;
		}catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}

	}
	
	

	@Override
	public Long obtenerPiezasPromedioPorUsuario(String inspector) throws ProquifaNetException { 
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			StringBuilder sbQuery = new StringBuilder("SELECT ROUND(CAST(SUM(pin) as float) / CAST((COUNT(pin))as float),0)promedio from (");
			sbQuery.append("\n SELECT SUM(P) pin, fecha FROM");
			sbQuery.append("\n (SELECT Inspector,CAST(Fecha as Date)Fecha,CASE WHEN Piezas is null THEN 0 ELSE Piezas END P from InspeccionOC where Inspector = '").append(inspector).append("'and piezas is not null");
			sbQuery.append("\n and CAST(Fecha as Date) < GETDATE())piezas");
			sbQuery.append("\n group by CAST(Fecha as Date))PROM");
//			log.info(sbQuery.toString()); 
			
			Long promDeInspeccion = super.queryForLong(sbQuery.toString(),map);
			promDeInspeccion = promDeInspeccion == null ? 0 : promDeInspeccion;
			return promDeInspeccion;
		}catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}

	}
	
	
	@Override
	public Long cantidadDePiezasInspeccionadasPorHoy(String inspector) throws ProquifaNetException { 
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			StringBuilder sbQuery = new StringBuilder("select CASE WHEN SUM(P) is null THEN 0 else SUM(P) end  piezas from");
			sbQuery.append("\n (SELECT Inspector,CAST(Fecha as Date)Fecha,CASE WHEN Piezas is null THEN 0 ELSE Piezas END P from InspeccionOC where Inspector = '").append(inspector).append("'");
			sbQuery.append("\n and CAST(Fecha as Date) = CAST(GETDATE()as Date))piezas");
			sbQuery.append("\n group by CAST(Fecha as Date)");
			
			log.info(sbQuery.toString()); 
			try{
				return super.queryForLong(sbQuery.toString(),map); 
			}catch (Exception e) {
				return 0L;
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}

	}

	

	@Override
	public Long PiezasAInspeccionarHoy() throws ProquifaNetException { 
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			StringBuilder sbQuery = new StringBuilder("SELECT SUM(Cant) FROM PartidadeCompraenInspeccion  WHERE estado = 'En inspección' AND idProducto  IS NOT NULL");
			Long piezasAInspeccionar = super.queryForLong(sbQuery.toString(),map); 
			Long auxPiezasAInpsecionar;
			if(piezasAInspeccionar == null) {
				piezasAInspeccionar = 0L;
				return piezasAInspeccionar;
			}
			else { 
				auxPiezasAInpsecionar  = piezasAInspeccionar;
				return auxPiezasAInpsecionar;
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}

	}
	
	
//
	@SuppressWarnings({ "unchecked", "unused" })
	public String obtenerFolioPorPeso() throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("SELECT TOP 1 SUM (puntos) as sumaPuntos,compra ,NoPedimento COLLATE Modern_Spanish_CI_AS as NoPedimento FROM PartidadeCompraenInspeccion WHERE compra not in ( "
			+ "SELECT DISTINCT folio COLLATE Modern_Spanish_CI_AS FROM FolioInspeccionTemporal WHERE folio IS NOT NULL) GROUP BY Compra, NoPedimento ORDER BY sumaPuntos DESC;");
			String folio = "";
			List<InspeccionPorFolio> lstResult =  super.jdbcTemplate.query(sbQuery.toString(), new PartidaInspeccionPorPesoRowMapper());
				for(InspeccionPorFolio pza : lstResult){
					 folio = pza.getCompra();
					 if(folio != null){
							return folio;
		 			} else{
		 				folio = pza.getPedimento();
		 			}
			}
			return folio ;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}	
	}

	@SuppressWarnings("unchecked")
	public String obtenerTipoInspeccion(String compra) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("");
			sbQuery.append("SELECT TOP 1 CASE WHEN compra is null THEN 'Pedimento' WHEN NoPedimento is null THEN 'Orden de compra' ELSE 'no hay folio por mostrar'END as Tipo FROM PartidadeCompraenInspeccion where compra ='"	+ compra + "'");
			return (String) super.jdbcTemplate.queryForObject(sbQuery.toString(),new HashMap(),String.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	@SuppressWarnings("unchecked")
	public String obtenerTipoInspeccionXidPcompra(int Pcompra) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("");
			sbQuery.append("SELECT TOP 1 CASE WHEN compra is null THEN 'Pedimento' WHEN NoPedimento is null THEN 'Orden de compra' ELSE 'no hay folio por mostrar'END as Tipo FROM PartidadeCompraenInspeccion where idPcompra ='"	+ Pcompra + "'");
			return (String) super.jdbcTemplate.queryForObject(sbQuery.toString(),new HashMap(),String.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}

	@SuppressWarnings("unchecked")
	public String obtenerIdPcompraInspeccionTemporal() throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("");
			sbQuery.append("select top 1 idPCompra from PartidadeCompraenInspeccion as pc where pc.idPcompra not in("
					+ "select distinct pc.idPcompra from PartidadeCompraenInspeccion left join FolioInspeccionTemporal as fi "
					+ "on  pc.idPCompra = fi.fk01_idPcompra where fi.fk01_idPcompra is not null) order by puntos desc");
			return  (String) super.jdbcTemplate.queryForObject(sbQuery.toString(),new HashMap(),String.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	@SuppressWarnings("unchecked")
	public String obtenerFolioInspeccionTemporal() throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("");
			sbQuery.append("select top 1 compra COLLATE Modern_Spanish_CI_AS as NoPedimento from PartidadeCompraenInspeccion where compra not in( "
					+ "SELECT distinct folio COLLATE Modern_Spanish_CI_AS from FolioInspeccionTemporal where folio is not null ) order by puntos desc;");
			return (String) super.jdbcTemplate.queryForObject(sbQuery.toString(),new HashMap(),String.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
		
	@SuppressWarnings("unchecked")
	public String obtenerFolioXInspectorTablaTemp(Long clave) throws ProquifaNetException {
			try {
				StringBuilder sbQuery = new StringBuilder("");
				sbQuery.append("select folio from FolioInspeccionTemporal where fk02_idINspector ='"+ clave + "';");
				return (String) super.jdbcTemplate.queryForObject(sbQuery.toString(),new HashMap(),String.class);
			} catch (Exception e) {
				return "Vacio";
			}
		}
	
	public Boolean borrarRegistrosTablaInspeccionTemporal(Long clave) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			super.jdbcTemplate.update("DELETE FROM FolioInspeccionTemporal WHERE fk02_idINspector ='"+ clave + "';",map);
			return true;
		} catch (Exception e) {
			return null;
		}
	}

	public Boolean insertarTablaInspeccionTempXDocumento(String folio, String tipo, long clave) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("folio", folio);
			map.put("tipo", tipo);
			map.put("clave", clave);
			Object[] params =  {folio,tipo, clave};			
			super.jdbcTemplate.update("INSERT INTO FolioInspeccionTemporal (folio,tipo, fk02_idInspector) VALUES (:folio,:tipo,:clave)",map);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public Boolean insertarTablaInspeccionTempXPrioridad(int idPcompra, String tipo, Long clave) throws ProquifaNetException {
			try {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("idPcompra", idPcompra);
				map.put("tipo", tipo);
				map.put("clave", clave);
				Object[] params =  {idPcompra,tipo, clave};			
				super.jdbcTemplate.update("INSERT INTO FolioInspeccionTemporal (fk01_idPcompra,tipo, fk02_idInspector) VALUES (:idPcompra,:tipo,:clave)",map);
				return true;
			} catch (Exception e) {
				return false;
			}
		}
	
	public Long obtenerClaveInspector(String inspector) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("select clave from empleados where usuario ='"+ inspector + "';");
			Long clave = super.queryForLong(sbQuery.toString()); 
			return clave;
			} catch (Exception e) {
				return -1L;
			}
	}

	public Boolean actualizarTipoDocumentoInspeccion(String tipo, String folio) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("folio", folio);
			Object[] params = { tipo, folio };
			super.jdbcTemplate.update("update FolioInspeccionTemporal set tipo = :tipo where folio = :folio;",map);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Integer obtenerTotalPartidasXCompra(String folio) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("compra", folio);
			String query = "select count(*) from PartidadeCompraenInspeccion where compra ='" + folio + "';";
			int numPartidas = super.queryForInt(query, map);
			return numPartidas;
		} catch (Exception e) {
			return 0;
		}
	}
	
	@SuppressWarnings("unchecked")
	public PartidaInspeccion obtenerPartidaInspeccionPorPuntos(String folio) throws ProquifaNetException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			StringBuilder sbQuery = new StringBuilder("select top 1 * from PartidadeCompraenInspeccion where compra ='" + folio + "';");
			PartidaInspeccion lstResult = (PartidaInspeccion) jdbcTemplate.queryForObject(sbQuery.toString(), map, new OrdenPartidaInspeccionRowMapper());
		   return lstResult;
			}catch (Exception e) {
				e.printStackTrace();
				throw new ProquifaNetException();
			}
	}
	
	
	@SuppressWarnings("unchecked")
	public Integer obtenerPzasPorPrioridad(String prioridad)throws ProquifaNetException {
		try {
			String query = "select sum (cant) from PartidadeCompraenInspeccion where prioridad ='" + prioridad + "';";
			//log.info(query);
			
			return super.queryForInt(query);
		}catch (Exception e) {
			return 0;
		}
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	public String obtenerUbicacion (Map<String, Object> data)throws ProquifaNetException {
		try {
			String manejo = data.get("manejo").toString();
			String idPPedidoAux = data.get("idPPedido").toString();
			//String tipoProducto = data.get("tipoProducto").toString();
			String tarimaAux = data.get("tarima").toString();
			String piezasAux  = data.get("piezas").toString();
			String idProductoAux = data.get("idProducto").toString();
			String fabrica = data.get("proveedor").toString();
			String tipoProducto = data.get("tipoProducto").toString();
			Integer piezas = Integer.parseInt(piezasAux);
			Boolean tarima = Boolean.parseBoolean(tarimaAux);
			Long idPPedido = Long.parseLong(idPPedidoAux);
			
			Boolean volumen;
			Boolean controlados;
			String ubicacion = "";
			Integer cantExistencia = 0;
			Integer total = 0;
			StringBuilder sbQuery = new StringBuilder("");

			log.info("JVRA - SELECT  TOP 1 idUbicacion, Total, Existencia FROM ubicacion WHERE (Existencia +" );
			log.info("JVRA - " + piezas + ") <= Total");
			sbQuery.append("SELECT  TOP 1 idUbicacion, Total, Existencia FROM ubicacion WHERE (Existencia +").append(piezas).append(") <= Total");


			if(manejo.equalsIgnoreCase("Ambiente")){
				String query = "SELECT Stock FROM PPedidos where idPPedido = " + idPPedido +";";
				String tipo = super.queryForString(query);
				if(tipo.equals("0")){
					query = "SELECT tipo FROM PPedidos where idPPedido = " + idPPedido +";";
					tipo = super.queryForString(query);
					if(tipo == null)
						tipo = "Regular";
				} else
					tipo = "Stock";
				
				volumen = convierteAgramos(idProductoAux);
				controlados =  obtenerControlados(idProductoAux);
				if(tarima){
					sbQuery.append(" AND  Manejo = 'Ambiente'  and tipoProducto = '' ORDER BY Existencia  ASC;");
				}else if(volumen && controlados){
					sbQuery.append(" AND total = 15 and tipo is null");	
				}else if(controlados){
					sbQuery.append(" AND Total = 20 AND tipo = '' ORDER BY Existencia  ASC");
				}else if(tipoProducto.equalsIgnoreCase("Estandares")){
					sbQuery.append(" AND tipo ='").append(tipo).append("' AND Manejo ='Ambiente' AND tipoProducto IS NULL  ORDER BY Existencia  ASC");
				}else if(tipo == "Regular" && data.get("tipoProducto").toString().equalsIgnoreCase("Labware")){
					sbQuery.append(" AND tipo ='Regular' AND tipoProducto = 'Labware' AND Manejo ='Ambiente' ORDER BY Existencia  ASC  ");}
				else if(tipo != "Regular" &&  data.get("tipoProducto").toString().equalsIgnoreCase("Publicaciones")){
					sbQuery.append(" AND  tipoProducto = 'Publicaciones' AND Manejo ='Ambiente' ORDER BY Existencia  ASC  ");}
				else if(tipo != "Regular" &&  data.get("tipoProducto").toString().equalsIgnoreCase("Reactivos")){
					sbQuery.append(" AND tipoProducto = 'Reactivos' AND Manejo ='Ambiente' ORDER BY Existencia  ASC  ");}
				else if(tipo == "Stock" &&  data.get("tipoProducto").toString().equalsIgnoreCase("Labware")){
					sbQuery.append(" AND tipo ='Stock' AND tipoProducto = 'Labware' AND Manejo ='Ambiente' ORDER BY Existencia  ASC  ");}
				else if(tipo == "Programado" &&  data.get("tipoProducto").toString().equalsIgnoreCase ("Labware")){
					sbQuery.append(" AND tipo ='Programado' AND tipoProducto = 'Labware' AND Manejo ='Ambiente' ORDER BY Existencia  ASC  ");}
				else if(tipo == "Regular" &&  data.get("tipoProducto").toString() != null){
					sbQuery.append(" AND tipoProducto ='" + data.get("tipoProducto").toString()  +"' AND Manejo ='" + manejo + "' ORDER BY Existencia  ASC");}
				else if (tipo != null &&  data.get("tipoProducto").toString() != "Labware" &&  data.get("tipoProducto").toString() != "Publicaciones" &&  data.get("tipoProducto").toString() != "Reactivos" ){
					sbQuery.append(" AND tipo ='" + tipo +"' AND Manejo ='Ambiente' ORDER BY Existencia  ASC  ");}
				
							
			} else if(manejo.equalsIgnoreCase("Congelación") || manejo.equalsIgnoreCase("Congelacion")){
				sbQuery.append(" AND tipoProducto ='" + data.get("tipoProducto").toString()  + "' AND Manejo = 'Congelación' ORDER BY Existencia  ASC  ");
				
			}else if(manejo.equalsIgnoreCase("Refrigeración") || manejo.equalsIgnoreCase("Refrigeracion")){
				controlados = obtenerControlados(idProductoAux);
				
				if(controlados)
					sbQuery.append(" AND Manejo = 'Refrigeración' AND tipo = 'Controlado   ' ORDER BY Existencia  ASC ;");
				else
					sbQuery.append(" AND Manejo = 'Refrigeración' AND tipo is null  ORDER BY Existencia  ASC ;");
			}

			List<ubicacionPorManejo> lstResult =  super.jdbcTemplate.query(sbQuery.toString(), new ubicacionPorExistenciaRowMapper());
				for(ubicacionPorManejo dato : lstResult){
					 
					 cantExistencia = dato.getExistencias();
					 total = dato.getTotal();
					 
					  Integer lugares = total - cantExistencia;
					 if(piezas <= lugares ){
						 ubicacion = dato.getIdUbicacion();
						 return ubicacion;
					 } else{
						 ubicacion= "No hay ubicación disponible";
						 return ubicacion;
		 			}
			}

			log.info("JVRA - ubicacion " + ubicacion );
			return ubicacion ;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}	
	}
	
	public Boolean convierteAgramos(String dato){
		try {
			Boolean respuesta;
			Long idProducto = Long.parseLong(dato);
			
			String queryUnidad = "SELECT unidad FROM Productos where idProducto = " + idProducto +";";
			String unidad = super.queryForString(queryUnidad);
			String queryCantidad = "SELECT cantidad FROM Productos where idProducto = " + idProducto +";";
			String cantidadAux = super.queryForString(queryCantidad);
			

			Double cantidad = Double.parseDouble(cantidadAux);
			
			if(unidad.equalsIgnoreCase("g") && cantidad > 100)
				respuesta = true;
			else 
				respuesta = false;
			
			if(unidad.equalsIgnoreCase("mcg")){
				double r = cantidad / 1000000 ;
				if(r >100)
					respuesta = true;
				else
					respuesta = false;
			}
			if(unidad.equalsIgnoreCase("mg")){
				double r = cantidad / 1000 ;
				if(r >100)
					respuesta = true;
				else
					respuesta = false;
			}
			if(unidad.equalsIgnoreCase("oz")){
				double r = cantidad / 0.035274 ;
				if(r >100.0)
					respuesta = true;
				else
					respuesta = false;
			}
			if (unidad.equalsIgnoreCase("kg"))
				respuesta = true;
			
		return respuesta;
		} catch (Exception e) {
			return false;
		}
	}
	
	public Boolean obtenerControlados (String dato){
		try {
			Boolean respuesta;
			Long idProducto = Long.parseLong(dato);
			String query = "SELECT control FROM Productos where idProducto = " + idProducto +";";
			String control = super.queryForString(query);
				if(control.equalsIgnoreCase("Mundiales") || control.equalsIgnoreCase("Nacionales") || control.equalsIgnoreCase("Origen"))
					respuesta = true;
				else
					respuesta = false;
					
			return respuesta;
		}catch (Exception e) {
			return false;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public Boolean guardarExistenciaUbicacion(String idUbicacion, int piezas)throws ProquifaNetException {
		try {
			
			Boolean res  = false;
			
			String query = "SELECT existencia FROM ubicacion WHERE idUbicacion ='" + idUbicacion + "';";
			//log.info(query);
			String existenciaAux = super.queryForString(query);
			String query2 = "SELECT total FROM ubicacion WHERE idUbicacion ='" + idUbicacion + "';";
			//log.info(query);
			String totalAux = super.queryForString(query2);
			
			Integer existencia = Integer.parseInt(existenciaAux);
			Integer total = Integer.parseInt(totalAux);
			
			Integer acum = existencia + piezas;
			
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idUbicacion", idUbicacion);
			map.put("acum", acum);
			
			if (acum <= total)
			{
				super.jdbcTemplate.update("UPDATE ubicacion set existencia =:acum WHERE idUbicacion =:idUbicacion;", map);
				res = true;
			}
			else 
				res = false;
			
			return res;
		
		}catch (Exception e) {
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public String obtenerSubCarpeta (String idProducto)throws ProquifaNetException {
		String res = "";
		
		String query = "SELECT FK02_Fabricante FROM PRODUCTOS WHERE idProducto = " + idProducto + ";";
		//log.info(query);
		res = super.queryForString(query);
		
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public Boolean guardarConsumible(Integer cantidad, String tipoBolsa)throws ProquifaNetException {
//		Boolean res;
//		String  existenciaAux;
//		
//		String query = "SELECT EXISTENCIAS FROM Consumibles WHERE Tipo = '" + tipoBolsa + "';";
//		//log.info(query);
//		existenciaAux = super.queryForString(query);
//		
//		Integer  existencia = Integer.parseInt(existenciaAux);
//		Integer total = existencia - cantidad;
		
//		if(total >= 0){
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("total", total);
//			map.put("tipoBolsa", tipoBolsa);
//			super.jdbcTemplate.update("UPDATE Consumibles SET EXISTENCIAS = :total WHERE tipo =  :tipoBolsa", map);
//			res = true;
//			
//		}else
//			res = false;			
//		return res;
		
		return true;
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public PartidaInspeccion obtenerPartida(Integer idUsuario) throws ProquifaNetException{
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("SELECT TOP 1 PCI.*, IOC.id idInspeccionOC,PR.cantidad cantidadProd, PR.unidad unidadProd FROm PArtidadeCompraEnInspeccion PCI \n");
			sbQuery.append("LEFT JOIN InspeccionOC IOC ON IOC.idPCompra = PCI.idPcompra \n");
			sbQuery.append("LEFT JOIN Productos PR ON  PR.idProducto = PCI.idProducto\n"); //Se agrego para unidad y cantida
			sbQuery.append("WHERE PCI.Estado = 'En inspección' AND (IOC.Apartado IS NULL OR IOC.Apartado = :idUsuario) \n");
			sbQuery.append("ORDER BY IOC.Apartado DESC, PCi.Puntos DESC, PCI.DiasRestantes ASC \n");
			sbQuery.append(" \n");
			
			Map <String,Object> map = new HashMap<String, Object>();
			map.put("idUsuario", idUsuario);
			return (PartidaInspeccion) jdbcTemplate.queryForObject(sbQuery.toString(), map, new OrdenPartidaInspeccionRowMapper());
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	
	@Override
	public boolean apartarPartida(Integer idPartidaInspeccionOC, Integer idUsuario) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("UPDATE InspeccionOC SET Apartado = :idUsuario \n");
			sbQuery.append("WHERE id = :idInspeccion \n");
			
			Map <String,Object> map = new HashMap<String, Object>();
			map.put("idUsuario", idUsuario);
			map.put("idInspeccion", idPartidaInspeccionOC);
			super.jdbcTemplate.update(sbQuery.toString(), map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	@Override
	public Integer consultarStock(Long idPPedido) throws ProquifaNetException{
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("SELECT STOck FROM PPEDIDOS WHERE IDPPEDido = :idPPedido");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idPPedido", idPPedido);
			return jdbcTemplate.queryForObject(sbQuery.toString(), map, Integer.class);
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	@Override
	public boolean insertarStock(Integer cantidad, Long idPPedido, Long PCompra) throws ProquifaNetException {
		try {
			Long estado;
			if(cantidad > 1) {
				Map <String,Object> map = new HashMap<String, Object>();
				map.put("idPPedido", idPPedido);
				map.put("idPCompra", PCompra);
				///Obtener tipo de Factu ras
				StringBuilder sbEstado = new StringBuilder(" \n");
				sbEstado.append("SELECT idPFacturaXPagar FROM PFACturaxPagar WHERE idPcompra = :idPCompra \n");
				try {
					estado = jdbcTemplate.queryForObject(sbEstado.toString(), map, Long.class);
				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
					estado = null;
				}
				StringBuilder sbQueryPFactura = new StringBuilder(" \n");
				if(estado !=null) {
					//PFACTURAS
					sbQueryPFactura.append("DECLARE @MaxCompra AS INT \n");
					sbQueryPFactura.append("SELECT @MaxCompra =  MAX(Partida) FROM PCompras WHERE Compra = (SELECT Compra FROM PCompras where idPCompra = :idPCompra) \n");
					sbQueryPFactura.append("INSERT INTO PFACTURAxPagar (idFxPagar, idPCompra, FPor) \n");
					
				}
				
				///PPEDIDOS
				StringBuilder sbQuery = new StringBuilder(" \n");
				sbQuery.append("DECLARE @MaxPPedido AS INT \n");
				sbQuery.append("SELECT @MaxPPedido = MAX(Part) FROM PPEdidos WHERE CPEDIDO = (SELECT CPEDIDO FROM PPEDIDOS WHERE idPPedido = (SELECT FK03_PPedido FROM PCompras where idPCompra = :idPCompra)) \n");
				sbQuery.append("INSERT INTO Ppedidos (CPedido, Part, Cant, Codigo, Precio, Concepto, Estado,Costo, Fabrica, Provee, Faltan, Stock ,Tipo, idComplemento, Reasignada, \n");
				sbQuery.append("Cotiza,LineaDeOrden, FK02_Producto, origenstock)");
				///PCOMPRAS
				StringBuilder sbQueryPC = new StringBuilder(" \n");
				sbQueryPC.append("DECLARE @MaxPPedido AS INT \n");
				sbQueryPC.append("SELECT @MaxPPedido = MAX(Part) FROM PPEdidos WHERE CPEDIDO = (SELECT CPEDIDO FROM PPEDIDOS WHERE idPPedido = (SELECT FK03_PPedido FROM PCompras where idPCompra = :idPCompra)) \n");
				sbQueryPC.append("DECLARE @MaxCompra AS INT \n");
				sbQueryPC.append("SELECT @MaxCompra =  MAX(Partida) FROM PCompras WHERE Compra = (SELECT Compra FROM PCompras where idPCompra = :idPCompra) \n");
				sbQueryPC.append("INSERT INTO PCompras (Compra, Partida, Cpedido, PPedido, Cant, Codigo,Fabrica,Costo,Estado,Factura,FPharma,FProquifa,\n");
				sbQueryPC.append("FCliente, FFacturaP, FacturaP, FRevision, FPago, SCheque, Control, PorCancelar, FolioNT, idComplemento, Evaluada, Pagada, \n");
				sbQueryPC.append("BloqueadaaC, FPharmaUE, EdoCliente,FEENtrega, AC, BackOrder, Comentarios, foLIOiNSPECCION, FolioInspeccionPhs, FolioInspeccionUE,\n");
				sbQueryPC.append("Pedido, Cotiza, ComentariosPhs, Reprogramada, AnaquelNum, AnaquelLetra, Ubicacion, PorEnterarse, FK01_Producto,origen, Lote, fK02_Compra, fk03_PPedido, BolsaInspeccion) \n");
		
				/// INSPECCION OC
				StringBuilder sbQueryOC = new StringBuilder(" \n");
				sbQueryOC.append("DECLARE @MaxCompras AS INT \n");
				sbQueryOC.append("SELECT @MaxCompras = MAX(Partida) FROM PCompras WHERE Compra = (SELECT Compra FROM PCompras where idPCompra = :idPCompra)\n");
				sbQueryOC.append("INSERT INTO INspeccionOc (IDPCompra, Pedimento,DisponibilidadPedimento, Guia, MesCaducidad, AnoCaducidad, Manejo, Lote, \n");
				sbQueryOC.append("Documentacion,Comentarios, Idioma, Edicion, Fecha, Inspector, Manejo_Transporte, FPedimento, Aduana, \n");
				sbQueryOC.append(" Piezas, Tiempo_Inspeccion, DoctoSDS, videoPartida, Prioridad, Apartado, AplicaDocumentacion, ImagenRechazo)\n");
				
				////Inserccion ESTADO PCompras
				StringBuilder sbQueryEPC = new StringBuilder(" \n");
				sbQueryEPC.append("DECLARE @MaxCompras AS INT \n");
				sbQueryEPC.append("SELECT @MaxCompras = MAX(Partida) FROM PCompras WHERE Compra = (SELECT Compra FROM PCompras where idPCompra = :idPCompra) \n");
				sbQueryEPC.append("INSERT INTO EstadoPCompra (idPCompra,FInicio,FFin, Tipo) \n");
				
				
				/*for (int i = 0; i < cantidad -1; i++) {
					///PPEDIDOS
				/*	sbQuery.append("SELECT CPedido, @MaxPPedido +"); sbQuery.append(i+1);
					sbQuery.append(", 1, Codigo, Precio, Concepto, 'STOCK',Costo, Fabrica, Provee, 1, Stock , Tipo ,idComplemento, Reasignada, \n");
					sbQuery.append("Cotiza,LineaDeOrden, FK02_Producto, origenstock \n");
					sbQuery.append("FROM PPedidos WHERE idPPedido = :idPPedido \n");
				*/
					sbQuery.append("SELECT\n" +
							"    OriginalResult.CPedido,\n" +
							"    @MaxPPedido  + ROW_NUMBER() OVER (ORDER BY OriginalResult.CPedido) AS partida,\n" +
							"    OriginalResult.cant,\n" +
							"    OriginalResult.Codigo,\n" +
							"    OriginalResult.Precio,\n" +
							"    OriginalResult.Concepto,\n" +
							"    OriginalResult.Estado,\n" +
							"    OriginalResult.Costo,\n" +
							"    OriginalResult.Fabrica,\n" +
							"    OriginalResult.Provee,\n" +
							"    OriginalResult.faltan,\n" +
							"    OriginalResult.Stock,\n" +
							"    OriginalResult.Tipo,\n" +
							"    OriginalResult.idComplemento,\n" +
							"    OriginalResult.Reasignada,\n" +
							"    OriginalResult.Cotiza,\n" +
							"    OriginalResult.LineaDeOrden,\n" +
							"    OriginalResult.FK02_Producto,\n" +
							"    OriginalResult.origenstock\n" +
							"FROM (\n" +
							"    SELECT\n" +
							"        CPedido,\n" +
							"        @MaxPPedido as partida,\n" +
							"        1 as cant,\n" +
							"        1 as faltan,\n" +
							"        Codigo,\n" +
							"        Precio,\n" +
							"        Concepto,\n" +
							"        'STOCK' as Estado,\n" +
							"        Costo,\n" +
							"        Fabrica,\n" +
							"        Provee,\n" +
							"        Stock,\n" +
							"        Tipo,\n" +
							"        idComplemento,\n" +
							"        Reasignada,\n" +
							"        Cotiza,\n" +
							"        LineaDeOrden,\n" +
							"        FK02_Producto,\n" +
							"        origenstock\n" +
							"    FROM PPedidos\n" +
							"    WHERE idPPedido = :idPPedido\n" +
							") AS OriginalResult\n" +
							"CROSS JOIN (\n" +
							"    SELECT TOP " + cantidad +"\n"+
							"        ROW_NUMBER() OVER (ORDER BY (SELECT NULL)) AS n\n" +
							"    FROM sys.columns AS c1\n" +
							"    CROSS JOIN sys.columns AS c2\n" +
							") AS Repetitions\n");
					System.out.println("Query PPedidos Stock: "+ sbQuery);
				super.jdbcTemplate.update(sbQuery.toString(), map);
					/// PCOMPRAS
				
					/*sbQueryPC.append("SELECT PC.Compra,@MaxCompra +"); sbQueryPC.append(i+1);
					sbQueryPC.append(",PC.CPedido, PP.Part, 1, PC.Codigo,PC.Fabrica,PC.Costo,'Recibido',PC.Factura,PC.FPharma,PC.FProquifa, PC.FCliente, PC.FFacturaP, \n");
					sbQueryPC.append("PC.FacturaP, PC.FRevision, PC.FPago, PC.SCheque, PC.Control, PC.PorCancelar, PC.FolioNT, PC.idComplemento, PC.Evaluada, PC.Pagada, PC.BloqueadaaC, PC.FPharmaUE, \n");
					sbQueryPC.append("PC.EdoCliente,PC.FEENtrega, PC.AC, PC.BackOrder, PC.Comentarios, PC.foLIOiNSPECCION, PC.FolioInspeccionPhs, PC.FolioInspeccionUE, PC.Pedido, PC.Cotiza, PC.ComentariosPhs,\n");
					sbQueryPC.append("PC.Reprogramada, PC.AnaquelNum, PC.AnaquelLetra, PC.Ubicacion, PC.PorEnterarse,PC.FK01_Producto, PC.origen, PC.Lote, PC.fK02_Compra, PP.idPPedido, BolsaInspeccion FROM  PcOMPras AS PC  \n");
					sbQueryPC.append("INNER JOIN PPEDIDOS AS PP ON PP.Cpedido = PC.cpedido \n");
					sbQueryPC.append("WHERE PC.FK03_PPedido = :idPPedido AND (PP.Part = @MaxPPedido -"); sbQueryPC.append(i); sbQueryPC.append(") \n");*/
					sbQueryPC.append("SELECT \n" +
							"    PC.Compra, \n" +
							"    @MaxCompra + ROW_NUMBER() OVER (ORDER BY PC.CPedido) AS NuevoValorConsecutivo, -- Utiliza ROW_NUMBER() para obtener un valor consecutivo\n" +
							"    PC.CPedido, \n" +
							"    PP.Part, \n" +
							"    1, \n" +
							"    PC.Codigo,\n" +
							"    PC.Fabrica,\n" +
							"    PC.Costo,\n" +
							"    'Recibido',\n" +
							"    PC.Factura,\n" +
							"    PC.FPharma,\n" +
							"    PC.FProquifa, \n" +
							"    PC.FCliente, \n" +
							"    PC.FFacturaP,\n" +
							"    PC.FacturaP, \n" +
							"    PC.FRevision, \n" +
							"    PC.FPago, \n" +
							"    PC.SCheque, \n" +
							"    PC.Control, \n" +
							"    PC.PorCancelar, \n" +
							"    PC.FolioNT, \n" +
							"    PC.idComplemento, \n" +
							"    PC.Evaluada, \n" +
							"    PC.Pagada, \n" +
							"    PC.BloqueadaaC, \n" +
							"    PC.FPharmaUE,\n" +
							"    PC.EdoCliente,\n" +
							"    PC.FEENtrega, \n" +
							"    PC.AC, \n" +
							"    PC.BackOrder, \n" +
							"    PC.Comentarios, \n" +
							"    PC.foLIOiNSPECCION, \n" +
							"    PC.FolioInspeccionPhs, \n" +
							"    PC.FolioInspeccionUE, \n" +
							"    PC.Pedido, \n" +
							"    PC.Cotiza, \n" +
							"    PC.ComentariosPhs,\n" +
							"    PC.Reprogramada, \n" +
							"    PC.AnaquelNum, \n" +
							"    PC.AnaquelLetra, \n" +
							"    PC.Ubicacion, \n" +
							"    PC.PorEnterarse,\n" +
							"    PC.FK01_Producto, \n" +
							"    PC.origen, \n" +
							"    PC.Lote, \n" +
							"    PC.fK02_Compra, \n" +
							"    PP.idPPedido, \n" +
							"    BolsaInspeccion \n" +
							"FROM PcOMPras AS PC  \n" +
							"INNER JOIN PPEDIDOS AS PP ON PP.Cpedido = PC.cpedido \n" +
							"WHERE PC.FK03_PPedido = :idPPedido and PP.Part BETWEEN (@MaxPPedido - ("+(cantidad-1)+")) and @MaxPPedido");
							System.out.println("Query PCompras Stock: "+ sbQueryPC);
							super.jdbcTemplate.update(sbQueryPC.toString(), map);
					
					//// INSPECCION OC
						sbQueryOC.append("SELECT PC2.IDPCompra, OC.Pedimento, OC.DisponibilidadPedimento, OC.Guia, OC.MesCaducidad, OC.AnoCaducidad, OC.Manejo, OC.Lote, \n");
						sbQueryOC.append("OC.Documentacion,OC.Comentarios, OC.Idioma, OC.Edicion, getDate(), OC.Inspector, OC.Manejo_Transporte, OC.FPedimento, OC.Aduana, \n");
						sbQueryOC.append(" 1, OC.Tiempo_Inspeccion, OC.DoctoSDS, OC.videoPartida, OC.Prioridad, OC.Apartado, OC.AplicaDocumentacion, \n");
						sbQueryOC.append("OC.ImagenRechazo FROM INSPECCIONOC AS OC \n");
						sbQueryOC.append("INNER JOIN PCompras PC ON PC.idPcompra = OC.idPCompra \n");
						sbQueryOC.append("INNER JOIN PCompras PC2 ON PC.Compra = PC2.Compra \n");
						//sbQueryOC.append("WHERE OC.idPCompra = :idPCompra AND PC2.PARTIDA = @MaxCompras -"); sbQueryOC.append(i);
						sbQueryOC.append("WHERE OC.idPCompra = :idPCompra AND PC2.PARTIDA BETWEEN (@MaxCompras -("+(cantidad-1)+")) and @MaxCompras");
						System.out.println("Query InspeccionOC Stock: " +sbQueryOC);
						super.jdbcTemplate.update(sbQueryOC.toString(), map);
					//// Estado PCompra
						sbQueryEPC.append("SELECT PC2.idPCompra, EPC.FInicio, getDate(), EPC.Tipo \n");
						sbQueryEPC.append("FROM estadoPCompra AS EPC \n");
						sbQueryEPC.append("INNER JOIN PCompras PC ON PC.idPCompra = EPC.idPCompra \n");
						sbQueryEPC.append("INNER JOIN PCompras PC2 ON PC.Compra = PC2.Compra \n");
						//sbQueryEPC.append("WHERE EPC.idPCompra = :idPCompra AND PC2.Partida = @MaxCompras -").append(i).append(" \n");
						sbQueryEPC.append("WHERE EPC.idPCompra = :idPCompra AND PC2.Partida BETWEEN (@MaxCompras -("+(cantidad-1)+")) and @MaxCompras");
						System.out.println("Query PCompras Stock: "+ sbQueryEPC);
						super.jdbcTemplate.update(sbQueryEPC.toString(), map);

						// PFACTURAS
					if(estado !=null) {
						/*sbQueryPFactura.append("SELECT idFxPagar, (SELECT idPCompra FROM PCOMPRAS WHERE Compra = (SELECT Compra  FROM PCompras WHERE idPCompra = :idPCompra)  AND Partida = @MaxCompra -").append(i).append("), FPor \n");
						sbQueryPFactura.append("FROM PFACturaxPagar WHERE idPCompra = :idPCompra \n");*/
						sbQueryPFactura.append("SELECT idFxPagar,pc2.idPcompra , FPor FROM PFACturaxPagar  as pf\n" +
								"inner join pcompras as pc on pc.idpcompra= pf.idpcompra\n" +
								"inner join pcompras as pc2 on pc.compra = pc2.compra\n" +
								"WHERE pf.idPCompra = :idPCompra and pc2.partida BETWEEN (@MaxCompra -("+(cantidad-1)+"))and @MaxCompra");
						System.out.println("Query Pfacturas Stock: "+sbQueryPFactura);
						super.jdbcTemplate.update(sbQueryPFactura.toString(), map);
					}
					
					
					/*if(i < cantidad - 2) {
						sbQuery.append("UNION ALL\n");
						sbQueryPC.append("UNION ALL \n");
						sbQueryOC.append("\nUNION ALL \n");
						sbQueryEPC.append("UNION ALL \n");
						if(estado !=null) {
							sbQueryPFactura.append("UNION ALL \n");
						}
					}
				}*/
				/*
				if(estado !=null) {

				}*/
								
			}
			//// Actualizar Tablas
			this.actualizarTablas(idPPedido, PCompra);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	@Override
	public List<String> obtenerPartidasInsertadasStock(Integer cantidad, Integer idPCompra, String cpedido) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("DECLARE @MaxPPedido AS INT \n");
			sbQuery.append("SELECT @MaxPPedido = MAX(Part) FROM PPEdidos WHERE CPEDIDO = (SELECT CPEDIDO FROM PPEDIDOS WHERE idPPedido = (SELECT FK03_PPedido FROM PCompras where idPCompra = :idPCompra)) \n");
			
			for (int i = 0; i < cantidad -1; i++) {
				sbQuery.append(" \n");
				sbQuery.append("SELECT 'FD-' + '").append(cpedido).append("-' + CAST((@MaxPPedido -").append(i).append(") as VARCHAR)");
				
				if(i < cantidad - 2) {
					sbQuery.append("UNION \n");
				}
				
			}
			
			Map <String,Object> map = new HashMap<String, Object>();
			map.put("idPCompra", idPCompra);
			
			return super.jdbcTemplate.queryForList(sbQuery.toString(), map, String.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	@Override
	public  boolean actualizarTablas(Long idPPedido, Long PCompra) throws ProquifaNetException {
		try {
			// UPDATE PCOMPRAS
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("UPDATE PCOMPRAS SET Cant  = 1, Estado = 'Recibido' \n");
			sbQuery.append("WHERE IdPCompra = :idPCompra\n");
			// UPDATE PPEDIDO
			StringBuilder sbQueryPP = new StringBuilder(" \n");
			sbQueryPP.append("UPDATE PPedidos SET Cant = 1, Estado = 'STOCK', Faltan = 1 \n");
			sbQueryPP.append("WHERE idPPedido = :idPPedido \n");
			/// UPDATE INSPECCION OC
			StringBuilder sbQueryOC = new StringBuilder(" \n");
			sbQueryOC.append("UPDATE InspeccionOC SET Piezas = 1 \n");
			sbQueryOC.append("WHERE idPcompra = :idPCompra \n");
			
			////UPDATE ESTADO PCompra
			 
			StringBuilder sbQueryEPC = new StringBuilder(" \n");
			sbQueryEPC.append("UPDATE estadoPCompra SET FFIN = getDate() \n");
			sbQueryEPC.append("WHERE idPcompra = :idPCompra \n");
			Map <String,Object> map = new HashMap<String, Object>();
			
				map.put("idPPedido", idPPedido);
				map.put("idPCompra", PCompra);
				super.jdbcTemplate.update(sbQuery.toString(), map);
				super.jdbcTemplate.update(sbQueryPP.toString(), map);
				super.jdbcTemplate.update(sbQueryOC.toString(), map);
				super.jdbcTemplate.update(sbQueryEPC.toString(), map);
		
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	@Override
	public boolean  insertarPiezasStock(Long idPCompra, Integer cantidad, List<PartidaInspeccion> lstPieza) throws ProquifaNetException {
		try {
			Integer indice = cantidad -1;
			Boolean eCorrecta;
			Boolean icorrecto;
			Boolean caducidadC;
			String mesC = "";
			String anioC = "";
			Map<String, Object> map = new HashMap<String, Object>();
			StringBuilder sbQuery = new StringBuilder(" \n");
			/*sbQuery.append("DECLARE @MaxCompras AS INT \n");
			sbQuery.append("SELECT @MaxCompras = MAX(Partida) FROM PCompras WHERE Compra = (SELECT Compra FROM PCompras where idPCompra = :idPCompra) \n");
			sbQuery.append("INSERT INTO Pieza (Codigo,Fabrica,IdPCompra,Catalogo,Descripcion,Edicion,Idioma,FisicamenteC,Rechazos,Presentacion,Lote,LoteTxt, \n");
			sbQuery.append("Caducidad,MesCaducidad,AnoCaducidad,Documentacion,Documento,Despachable,FFrente,FArriba,FABajo,Cantidad)\n");*/
			
		///////INSSERTAR EN STOCK
			StringBuilder sbQueryStock = new StringBuilder("\n");
			/*sbQueryStock.append("DECLARE @MaxCompras AS INT \n");
			sbQueryStock.append("SELECT @MaxCompras = MAX(Partida) FROM PCompras WHERE Compra = (SELECT Compra FROM PCompras where idPCompra = :idPCompra) \n");
			sbQueryStock.append("INSERT INTO STock (FEntrada,Codigo,Fabrica,idPCompra,cantDisponible) \n");*/

			Map <String,Object> mapStock = new HashMap<String, Object>();
			mapStock.put("idPCompra", idPCompra);

			if(lstPieza.get(indice).getEsPublicacion() != null && lstPieza.get(indice).getEsPublicacion())
			{
				eCorrecta = lstPieza.get(indice).getEdicionCorrecta();
				icorrecto = lstPieza.get(indice).getIdiomaCorecto();
				caducidadC = null;
				mesC = null;
				anioC = null;

			}
			else{
				eCorrecta = null;
				icorrecto = null;
				caducidadC = lstPieza.get(indice).getCaducidadCorrecta();
				mesC = lstPieza.get(indice).getMesCaduca();
				anioC = lstPieza.get(indice).getAnoCaduca();
			}

			// Llenado de datos
			map.put("codigo", lstPieza.get(indice).getCodigo());
			map.put("fabrica", lstPieza.get(indice).getFabrica());
			map.put("catalogo", lstPieza.get(indice).getCatCorrecto());
			map.put("descripcion", lstPieza.get(indice).getDescripcionCorrecta());
			map.put("edicionC",eCorrecta);
			map.put("idiomaC", icorrecto);
			map.put("fisicamenteC", lstPieza.get(indice).getFisicamenteCorrecto());
			map.put("reporte", lstPieza.get(indice).getReporte());
			map.put("presentacionC", lstPieza.get(indice).getPresentacionCorrecta());
			map.put("loteC", lstPieza.get(indice).getLoteCorrecto());
			map.put("lote", lstPieza.get(indice).getLote());
			map.put("caducidadC", caducidadC);
			map.put("mesC", mesC );
			map.put("anioC", anioC);
			map.put("documentacion", lstPieza.get(indice).getDocumentacionCorrecta());
			map.put("despachable", lstPieza.get(indice).getDespachable());
			map.put("fFrente", lstPieza.get(indice).getfFrente());
			map.put("fArriba", lstPieza.get(indice).getfArriba());
			map.put("fAbajo", lstPieza.get(indice).getfAbajo());
			map.put("cant", 1);
			map.put("idPCompra", idPCompra);
			map.put("documento", lstPieza.get(indice).getDocumento());
			log.info("codigo"+ lstPieza.get(indice).getCodigo()+"fabrica"+ lstPieza.get(indice).getFabrica()+"catalogo"+ lstPieza.get(indice).getCatCorrecto()+"descripcion"+ lstPieza.get(indice).getDescripcionCorrecta()+"edicionC"+eCorrecta+"idiomaC"+ icorrecto+"fisicamenteC"+ lstPieza.get(indice).getFisicamenteCorrecto()+"reporte"+ lstPieza.get(indice).getReporte()+"presentacionC"+ lstPieza.get(indice).getPresentacionCorrecta()+"loteC"+ lstPieza.get(indice).getLoteCorrecto()+"lote"+ lstPieza.get(indice).getLote()+"caducidadC"+ caducidadC+"mesC"+ mesC +"anioC"+ anioC+"documentacion"+ lstPieza.get(indice).getDocumentacionCorrecta()+"despachable"+ lstPieza.get(indice).getDespachable()+"fFrente"+ lstPieza.get(indice).getfFrente()+"fArriba"+ lstPieza.get(indice).getfArriba()+"fAbajo"+ lstPieza.get(indice).getfAbajo()+"cant"+ 1+"idPCompra"+ idPCompra+"documento"+ lstPieza.get(indice).getDocumento());

			sbQuery.append("\n DECLARE @MaxCompras AS INT \n");
			sbQuery.append("\n SELECT @MaxCompras = MAX(Partida) FROM PCompras WHERE Compra = (SELECT Compra FROM PCompras where idPCompra = :idPCompra) \n");
			sbQueryStock.append("\n DECLARE @MaxCompras AS INT \n");
			sbQueryStock.append("\n SELECT @MaxCompras = MAX(Partida) FROM PCompras WHERE Compra = (SELECT Compra FROM PCompras where idPCompra = :idPCompra) \n");
			//for (int i = 0; i < cantidad -1; i++) {
			int pza=0;
			//for (int i = 0; i <= cantidad ; i++) {
				//PIEZA
				pza++;

				sbQuery.append("\n INSERT INTO Pieza (Codigo,Fabrica,IdPCompra,Catalogo,Descripcion,Edicion,Idioma,FisicamenteC,Rechazos,Presentacion,Lote,LoteTxt, \n");
				sbQuery.append("Caducidad,MesCaducidad,AnoCaducidad,Documentacion,Documento,Despachable,FFrente,FArriba,FABajo,Cantidad)\n");
			/*sbQuery.append("SELECT *\n" +
					"FROM ( \n");*/

				sbQuery.append("SELECT 	:codigo as codigo, :fabrica as fabrica, PC2.idPCompra as idpcompra, :catalogo as catalogo, :descripcion as descripcion, :edicionC as edicionC, :idiomaC as idiomaC, :fisicamenteC as fisicamenteC, :reporte as reporte, \n");
				sbQuery.append(":presentacionC as presentacion, :loteC as loteC, :lote as lote, :caducidadC as caducidadC, :mesC as mesC, :anioC as anioC, :documentacion as documentacion,:documento as documento, :despachable as despachable, :fFrente as frente , :fArriba as arriba , :fAbajo as abajo, 1 as cant \n");
				sbQuery.append("FROM PCompras AS PC\n");
				sbQuery.append("INNER JOIN PCompras PC2 ON PC.Compra = PC2.Compra \n");
				//sbQuery.append("WHERE PC.idPCompra = :idPCompra AND PC2.PARTIDA = @MaxCompras -"); sbQuery.append(i);
				sbQuery.append("WHERE PC.idPCompra = :idPCompra AND PC2.PARTIDA BETWEEN (@MaxCompras -("+(cantidad-1)+")) and @MaxCompras \n"); //sbQuery.append(i);

			/*
			sbQuery.append(") AS OriginalResult \n");
			sbQuery.append("CROSS JOIN (\n" +
					"    SELECT TOP "+ cantidad +"\n1 AS n -- Cambia el valor de la variable 'cantidad' por el numero de piezas de la partida\n" +
					"    FROM sys.columns AS c1\n" +
					"    CROSS JOIN sys.columns AS c2\n" +
					") AS Repetitions");
				//sbQuery.append("\n UNION ALL \n");*/
				super.update(sbQuery.toString(), map);
				log.info("Inserta registro en tabla píeza"+pza);
		//	}
		//int stock=0;
		//	for (int i = 0; i <= cantidad ; i++){
				/// STOCK
					//stock ++;
				sbQueryStock.append("\n INSERT INTO Stock (FEntrada,Codigo,Fabrica,idPCompra,cantDisponible) \n");
		/*	sbQueryStock.append("SELECT *\n" +
					"FROM ( \n");*/

				sbQueryStock.append("SELECT getDate	() as fecha, PC2.Codigo as codigo , PC2.Fabrica as fabrica, PC2.idPCompra as idpcompra, 1 as cant \n");
				sbQueryStock.append("FROM PCompras AS PC \n");
				sbQueryStock.append("INNER JOIN PCompras PC2 ON PC.Compra = PC2.Compra \n");
				//sbQueryStock.append("WHERE PC.idPCompra = :idPCompra AND PC2.PARTIDA = @MaxCompras -\n").append(i);
				sbQueryStock.append("WHERE PC.idPCompra = :idPCompra AND PC2.PARTIDA BETWEEN (@MaxCompras -("+(cantidad-1)+")) and @MaxCompras \n");
			/*sbQueryStock.append(") AS OriginalResult \n");
			sbQueryStock.append("CROSS JOIN (\n" +
					"    SELECT TOP "+ cantidad +"\n1 AS n -- Cambia el valor de la variable 'cantidad' por el numero de piezas de la partida\n" +
					"    FROM sys.columns AS c1\n" +
					"    CROSS JOIN sys.columns AS c2\n" +
					") AS Repetitions");*/
				//sbQueryStock.append("\n UNION ALL \n");
				super.jdbcTemplate.update(sbQueryStock.toString(), mapStock);
				log.info("Inserta registro en tabla stock");
			//}
			/*

			sbQuery.append("SELECT 	:codigo, :fabrica, PC.idPCompra, :catalogo, :descripcion, :edicionC, :idiomaC, :fisicamenteC, :reporte, :presentacionC, \n");
			sbQuery.append(":loteC, :lote, :caducidadC, :mesC, :anioC, :documentacion,:documento, :despachable, :fFrente , :fArriba , :fAbajo , 1 \n");
			sbQuery.append("FROM PCompras AS PC\n");
			sbQuery.append("WHERE PC.idPCompra = :idPCompra\n");
			
			/// STOCK
			sbQueryStock.append("SELECT 	getDate	(), PC.Codigo, PC.Fabrica, PC.idPCompra, 1 \n");
			sbQueryStock.append("FROM PCompras AS PC \n");
			sbQueryStock.append("WHERE PC.idPCompra = :idPCompra\n");
			
			*/
			log.info("Query Pzas:"+sbQuery.toString());
			log.info("Query Stock"+sbQueryStock.toString());
			//super.update(sbQuery.toString(),map);//Sbquery
			////datos para la tabla de stock
			//Map <String,Object> mapStock = new HashMap<String, Object>();
			//mapStock.put("idPCompra", idPCompra);
			
			//super.jdbcTemplate.update(sbQueryStock.toString(), mapStock);//ejecuta sbqueryStock
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	
	@Override
	public  Correo obtenerDatosCorreo(Long idPPedido) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("\n");
			sbQuery.append("DECLARE @Dia AS VARCHAR(30) \n");
			sbQuery.append("SELECT @Dia =  DATENAME(dw,GETDATE())  \n");
			sbQuery.append("SELECT Cl.vendedor + '@proquifa.net;' + CASE WHEN C.eMail IS NULL THEN '' ELSE C.eMail END AS correo, \n");
			sbQuery.append("P.pedido AS cuerpoCorreo ,CAST(CASE  \n");
			sbQuery.append("WHEN  @Dia = 'Friday' OR @Dia = 'Viernes' THEN (GETDATE() + 3) \n");
			sbQuery.append("ELSE  GETDATE() + 1 \n");
			sbQuery.append("END AS DATE)  AS FechaInicio, \n");
			sbQuery.append("P.idPedido	AS idContacto \n");
			sbQuery.append("FROM PPEDIDOS AS PP  \n");
			sbQuery.append("INNER JOIN PEDIDOS AS P ON PP.CPedido = P.Cpedido \n");
			sbQuery.append("INNER JOIN CONTactos	AS C ON P.IDContacto = C.idContacto\n");
			sbQuery.append("INNER JOIN CLientes AS Cl ON P.idCliente = Cl.clave \n");
			sbQuery.append("LEFT JOIN (SELECT COUNT(1) AS totalInspeccion, CPedido FROM PPedidos WHERE Estado IN ('En inspección','A facturacion','A programacion', 'Por Embalar') GROUP BY CPedido) AS PPE ON PPE.CPedido=PP.CPedido \n");
			sbQuery.append("LEFT JOIN (SELECT COUNT(1) AS totalCancelada, CPedido FROM PPedidos WHERE Estado IN ('Cancelada') GROUP BY CPedido) AS PPC ON PPC.CPedido=PP.CPedido \n");
			sbQuery.append("LEFT JOIN (SELECT COUNT(1) AS totalEntregada, CPedido FROM PPedidos WHERE Estado IN ('Entregado') GROUP BY CPedido) AS PPEN ON PPEN.CPedido=PP.CPedido \n");
			sbQuery.append("LEFT JOIN (SELECT COUNT(1) AS totalPartidas, CPedido FROM PPedidos GROUP BY CPedido) AS PPT ON PPT.CPedido = PP.CPedido \n");
			sbQuery.append("WHERE  (P.notificacionCorreo <> 1 OR P.notificacionCorreo is NULL)  \n");
			sbQuery.append("AND PP.idPPEdido = :idPPedido \n");
			sbQuery.append("AND P.Destino = 'Recoge en PROQUIFA' \n");
			sbQuery.append("AND CASE WHEN P.Parciales = 0 THEN (PPT.totalPartidas - COALESCE(PPC.totalCancelada,0) - COALESCE(PPEN.totalEntregada,0) ) ELSE PPE.totalInspeccion END = PPE.totalInspeccion \n");
			sbQuery.append("GROUP BY Cl.vendedor, C.eMail, P.pedido, P.idPedido \n");
			
			Map<String, Object> map = new HashMap<>();
			map.put("idPPedido", idPPedido);
			Correo datos = null;
			try {
				datos =  jdbcTemplate.queryForObject(sbQuery.toString(), map, new BeanPropertyRowMapper<>(Correo.class));
			} catch (Exception e) {
				// TODO: handle exception
				datos = null;
			}
			return datos;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Boolean acualizarEnvioCorreo(Long idPedido) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("\n");
			sbQuery.append("UPDATE Pedidos	SET notificacionCorreo = 1 \n");
			sbQuery.append("WHERE idPedido = :idPedido \n");
			
			Map<String, Object> map = new HashMap<>();
			map.put("idPedido", idPedido);
			jdbcTemplate.update(sbQuery.toString(), map);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
	
	
	@Override
	public boolean actualizarPartidaRemision(Long idPPedido) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append(" \n");
			sbQuery.append("SELECT PP.CPedido, PE.Remisionar, COALESCE(PEN.Folio,0) Clave, EMP.Usuario Responsable FROM PPedidos PP \n"); 
			sbQuery.append("INNER JOIN Pedidos PE ON PE.Cpedido = PP.CPedido \n");
			sbQuery.append("INNER JOIN Clientes CL ON CL.Clave = PE.idCliente  \n");
			sbQuery.append("INNER JOIN Empleados EMP ON EMP.Clave = CL.Cobrador \n");
			sbQuery.append("LEFT JOIN Pendiente PEN ON PEN.Docto = PP.CPedido AND PEN.Tipo = 'A Facturar' \n");
			sbQuery.append("WHERE PP.idPPEdido = :idPPedido AND PE.Remisionar = 1 \n");
			Map<String, Object> map = new HashMap<>();
			map.put("idPPedido", idPPedido);
			PartidaPedido ppedido = jdbcTemplate.queryForObject(sbQuery.toString(), map, new BeanPropertyRowMapper<>(PartidaPedido.class));
			if (ppedido.getRemisionar() == 1) {
				sbQuery = new StringBuilder();
				if (ppedido.getClave() == null || ppedido.getClave().intValue() == 0) {
					// INSERTAR PENDIENTE A FACTURAR
					sbQuery.append("INSERT INTO Pendiente (Docto, Partida, FInicio, Responsable, Tipo) values (:cpedido, '0', GETDATE(), :responsable, 'A Facturar') \n");
				}
				//ACTUALIZAR EL ESTADO A FACTURACION
				sbQuery.append("UPDATE PPedidos SET Estado = 'A facturacion' WHERE idPPedido = :idPPedido \n");
				
				map.put("cpedido", ppedido.getCpedido());
				map.put("responsable", ppedido.getResponsable());
				jdbcTemplate.update(sbQuery.toString(), map);
			}
			return true;
		} catch (Exception e) {
			log.info("No hay partida de Remision");
		}
		
		return false;
	}
	
	@Override
	public boolean insertarRestantes(Integer cantidad, Long idPPedido, Long PCompra)  throws ProquifaNetException{
		try {
			// PPedidos
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("DECLARE @MaxPPedido AS INT \n");
			sbQuery.append("SELECT @MaxPPedido = MAX(Part) FROM PPEdidos WHERE CPEDIDO = (SELECT CPEDIDO FROM PPEDIDOS WHERE idPPedido = (SELECT FK03_PPedido FROM PCompras where idPCompra = :idPCompra))\n");
			sbQuery.append("INSERT INTO Ppedidos (CPedido, Part, Cant, Codigo, Precio, Concepto, Estado,Costo, Fabrica, Provee, Faltan, FPEntrega, FaltaPago, Stock ,Tipo, idComplemento, Reasignada, \n");
			sbQuery.append("Cotiza,LineaDeOrden, FK02_Producto, origenstock) \n");
			sbQuery.append("SELECT CPedido, @MaxPPedido + 1 \n");
			sbQuery.append(",:cantidad, Codigo, Precio, Concepto, 'Compra',Costo, Fabrica, Provee, :cantidad, FPEntrega, FaltaPago, Stock, Tipo ,idComplemento, Reasignada,\n");
			sbQuery.append("Cotiza,LineaDeOrden, FK02_Producto, origenstock \n");
			sbQuery.append("FROM PPedidos WHERE idPPedido = :idPPedido \n");
			
			//////PCOMPRAS
			StringBuilder sbQueryPc = new StringBuilder(" \n");
			sbQueryPc.append("DECLARE @MaxPPedido AS INT \n");
			sbQueryPc.append("SELECT @MaxPPedido = MAX(Part) FROM PPEdidos WHERE CPEDIDO = (SELECT CPEDIDO FROM PPEDIDOS WHERE idPPedido = (SELECT FK03_PPedido FROM PCompras where idPCompra = :idPCompra)) \n");
			sbQueryPc.append("DECLARE @MaxCompra AS INT \n");
			sbQueryPc.append("SELECT @MaxCompra =  MAX(Partida) FROM PCompras WHERE Compra = (SELECT Compra FROM PCompras where idPCompra = :idPCompra) \n");
			sbQueryPc.append("INSERT INTO PCompras (Compra, Partida, Cpedido, PPedido, Cant, Codigo,Fabrica,Costo,Estado,Factura,FPharma,FProquifa, \n");
			sbQueryPc.append("FCliente, FFacturaP, FacturaP, FRevision, FPago, SCheque, Control, PorCancelar, FolioNT, idComplemento, Evaluada, Pagada, \n");
			sbQueryPc.append("BloqueadaaC, FPharmaUE, EdoCliente,FEENtrega, AC, BackOrder, Comentarios, foLIOiNSPECCION, FolioInspeccionPhs, FolioInspeccionUE, \n");
			sbQueryPc.append("Pedido, Cotiza, ComentariosPhs, Reprogramada, AnaquelNum, AnaquelLetra, Ubicacion, PorEnterarse, FK01_Producto,origen, Lote, fK02_Compra, fk03_PPedido, BolsaInspeccion) \n");
			sbQueryPc.append("SELECT PC.Compra,@MaxCompra + 1 ,PC.CPedido, PP.Part, :cantidad, PC.Codigo,PC.Fabrica,PC.Costo, \n");
			sbQueryPc.append("(SELECT CASE WHEN AlmacenMatriz = 1 THEN 'En tránsito Matriz' WHEN AlmacenUSA = 1 THEN 'En tránsito Phs-USA' END FROM COMPRAS \n");
			sbQueryPc.append("WHERE CLAVE = (SELECT Compra FROM PCompras where idPCompra = :idPCompra)) AS Estado,PC.Factura,PC.FPharma,PC.FProquifa, PC.FCliente, PC.FFacturaP, \n");
			sbQueryPc.append("PC.FacturaP, PC.FRevision, PC.FPago, PC.SCheque, PC.Control, PC.PorCancelar, PC.FolioNT, PC.idComplemento, PC.Evaluada, PC.Pagada, PC.BloqueadaaC, PC.FPharmaUE, \n");
			sbQueryPc.append("PC.EdoCliente,PC.FEENtrega, PC.AC, PC.BackOrder, PC.Comentarios, NULL, PC.FolioInspeccionPhs, PC.FolioInspeccionUE, PC.Pedido, PC.Cotiza, PC.ComentariosPhs, \n");
			sbQueryPc.append("PC.Reprogramada, PC.AnaquelNum, PC.AnaquelLetra, NULL, PC.PorEnterarse,PC.FK01_Producto, PC.origen, NULL, PC.fK02_Compra, PP.idPPedido, NULL FROM  PcOMPras AS PC  \n");
			sbQueryPc.append("INNER JOIN PPEDIDOS AS PP ON PP.Cpedido = PC.cpedido \n");
			sbQueryPc.append("WHERE PC.FK03_PPedido = :idPPedido AND (PP.Part = @MaxPPedido) \n");
			///////
			Map <String,Object> map = new HashMap<String, Object>();
			map.put("idPPedido", idPPedido);
			map.put("idPCompra", PCompra);
			map.put("cantidad", cantidad);
			super.jdbcTemplate.update(sbQuery.toString(), map);
			super.jdbcTemplate.update(sbQueryPc.toString(), map);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean actualizarRestantes(Integer cantidad, Long idPPedido, Long PCompra) throws ProquifaNetException {
		try {
			// UPDATE PPEDIDO
			StringBuilder sbQueryPP = new StringBuilder(" \n");
			sbQueryPP.append("UPDATE PPedidos SET Cant = cantidad, Estado = 'Compra', Faltan = cantidad \n");
			sbQueryPP.append("WHERE idPPedido = :idPPedido \n");
			
			// UPDATE PCOMPRAS
						StringBuilder sbQuery = new StringBuilder(" \n");
						sbQuery.append("UPDATE PCOMPRAS SET Cant = :cantidad, Estado = (SELECT CASE WHEN AlmacenMatriz = 1 THEN 'En tránsito Matriz' WHEN AlmacenUSA = 1 THEN 'En tránsito Phs-USA' END AS Estado FROM COMPRAS' \n");
						sbQuery.append("WHERE CLAVE = (SELECT Compra FROM PCompras where idPCompra = :idPCompra)) \n");
						sbQuery.append("WHERE IdPCompra = :idPCompra \n");
						
						Map <String,Object> map = new HashMap<String, Object>();
						map.put("idPPedido", idPPedido);
						map.put("idPCompra", PCompra);
						map.put("cantidad", cantidad);
						super.jdbcTemplate.update(sbQueryPP.toString(), map);
						super.jdbcTemplate.update(sbQuery.toString(), map);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}
	
	@Override
	public boolean existePartidaInspeccionOC(Integer idPCompra) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append(" \n");
			sbQuery.append("SELECT COUNT(idPCompra) FROM InspeccionOC WHERE idPCompra = :idPCompra \n");
			
			Map <String,Object> map = new HashMap<String, Object>();
			map.put("idPCompra", idPCompra);
			return super.jdbcTemplate.queryForObject(sbQuery.toString(), map, Integer.class) > 0;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public boolean insertarInspeccionOC(parametrosInspeccion param) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append(" \n");
			sbQuery.append("INSERT INTO InspeccionOC(idPCompra, Manejo, Lote, Documentacion,Comentarios , Fecha, Inspector, Manejo_Transporte, Piezas, \n"); 
			sbQuery.append("Tiempo_Inspeccion, DoctoSDS, VideoPartida, Prioridad, Apartado, AplicaDocumentacion, ImagenRechazo, Idioma, Edicion) VALUES( \n");
			sbQuery.append(":idPcompra, :Manejo, :Lote, :Documentacion, :comentarios , GETDATE(), :Inspector, :manejoTransporte, :Piezas, :Tiempo, :SDS, :Video, :Prioridad,");
			sbQuery.append(":idUsuario, :aplicaDocumentacion, :Imagen, :Idioma, :Edicion");
			sbQuery.append(") \n");
			
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("idPcompra", param.getIdCompra());
			parametros.put("Manejo", param.getUbicacion()); //EL manejo lo estan guardando en la ubicacion
			parametros.put("Lote", param.getLote());
			parametros.put("Documentacion", param.getDocumento());
			parametros.put("Inspector", param.getUsuario());
			parametros.put("manejoTransporte", param.getManejoTransporte());
			parametros.put("Piezas", param.getPiezas());
			parametros.put("Tiempo", param.getTiempo());
			parametros.put("Video", param.getVideoPartida());
			parametros.put("SDS", param.getDocumentosSDS());
			parametros.put("Prioridad", param.getPrioridad().replace("P", ""));
			parametros.put("aplicaDocumentacion", param.isAplicaDocumentacion());
			parametros.put("Imagen", param.getImagenRechazo());
			parametros.put("Idioma", param.getIdioma());
			parametros.put("Edicion", param.getEdicion());
			parametros.put("idUsuario",param.getIdUsuario());
			if(param.getComentarios() != null && param.getComentarios().size() > 0) {
				parametros.put("comentarios", param.getComentarios().get(0));
			} else {
				parametros.put("comentarios", null);
			}
			
			super.jdbcTemplate.update(sbQuery.toString(), parametros);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	@Override
	public boolean actualizarPCompraStock(parametrosInspeccion param) {
	 try {
		StringBuilder sbQuery = new StringBuilder();
		sbQuery.append("UPDATE PCOMPRAS SET Ubicacion = :ubicacion \n");
		sbQuery.append("WHERE IDPCOMPRA = :idPCompra \n");
				
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idPCompra", param.getIdCompra());
		parametros.put("ubicacion", param.getUbicacionAsignada());
				
		jdbcTemplate.update(sbQuery.toString(), parametros);
		return true;
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
		return false;
	}
	
	@Override
	public Integer consultaPrioridad1() throws ProquifaNetException{
		try {
			StringBuilder sbQuery = new StringBuilder("\n");
			sbQuery.append("SELECT COUNT(idPCompra)  \n");
			sbQuery.append("FROM PARTidadeCompraenInspeccion \n");
			sbQuery.append("WHERE Prioridad = 'P1' AND ESTADO = 'En Inspección' \n");
			Map<String, Object> param = new HashMap<>();
			return jdbcTemplate.queryForObject(sbQuery.toString(), param, Integer.class);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return -1;
	}
	
	@Override
	public Boolean verificarEstado(Long idPCompra) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("\n");
			sbQuery.append("SELECT PC.ESTADO FROM PCOMPRAS PC \n");
			sbQuery.append("INNER JOIN PPEDIDOS PP ON PP.idPPEDIDo = PC.FK03_PPedido \n");
			sbQuery.append("WHERE PC.idPCompra =  :idPCompra \n");
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("idPCompra", idPCompra);
			String estado = jdbcTemplate.queryForObject(sbQuery.toString(), param, String.class);
			if(!estado.equalsIgnoreCase("En inspección")) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public Boolean updateApartado(parametrosInspeccion inspeccion) {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("UPDATE INSPECCIONOC SET INSPECTOR = :usuario, Apartado = :idUsuario \n");
			sbQuery.append("WHERE idPCompra = :idPCompra \n");
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("usuario", inspeccion.getUsuario());
			param.put("idUsuario", inspeccion.getIdUsuario());
			param.put("idPCompra", inspeccion.getIdCompra());
			
			jdbcTemplate.update(sbQuery.toString(), param);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

}
