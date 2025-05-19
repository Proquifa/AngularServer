/**
 * 
 */
package com.proquifa.net.persistencia.operaciones.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.comun.Pendiente;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.operaciones.Prioridades;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.operaciones.PrioridadesDAO;

/**
 * @author ymendez
 *
 */
@Repository
public class PrioridadesDAOImpl extends DataBaseDAO implements PrioridadesDAO {

	final Logger log = LoggerFactory.getLogger(PrioridadesDAOImpl.class);
	
	@Override
	public List<Prioridades> obtenerBotoneraPrioridades() throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("SELECT SUM(TotalClientes) TotalClientes, Zona, SUM(Orden) Orden  \n");
			sbQuery.append("FROM (  \n");
			sbQuery.append("SELECT 0 TotalClientes, 'NORTE' Zona, 1 Orden \n"); 
			sbQuery.append("UNION SELECT 0, 'SUR' Zona, 2 Orden  \n");
			sbQuery.append("UNION SELECT 0, 'TOLUCA' Zona, 3 Orden  \n");
			sbQuery.append("UNION SELECT 0, 'CUERNAVACA' Zona, 4 Orden  \n");
			sbQuery.append("UNION SELECT 0, 'FORANEO' Zona, 5 Orden  \n");
			sbQuery.append("UNION SELECT 0, 'PAQUETERIA' Zona, 6 Orden \n"); 
			sbQuery.append("UNION SELECT 0, 'RECOGE EN PROQUIFA' Zona, 7 Orden \n");  
			sbQuery.append("UNION ALL  \n");
			sbQuery.append("SELECT COUNT(idCliente) TotalClientes, Zona, 0 \n"); 
			sbQuery.append("FROM (  \n");
			sbQuery.append("SELECT * FROM ( \n"); 
			sbQuery.append("SELECT COALESCE(PCE.idCliente, PI.Clave, PFR.idCliente, PPM.idCliente) idCliente, \n"); 
			sbQuery.append("CASE WHEN COALESCE(PCE.Ruta, PI.Ruta, PFR.Ruta, PPM.Ruta) = 'Foraneo' THEN 'FORANEO'   \n");
			sbQuery.append("WHEN COALESCE(PCE.ZonaMensajeria,PI.ZonaMensajeria, PFR.ZonaMensajeria, PPM.ZonaMensajeria) = 'NORTE' THEN 'NORTE' \n"); 
			sbQuery.append("WHEN COALESCE(PCE.ZonaMensajeria,PI.ZonaMensajeria, PFR.ZonaMensajeria, PPM.ZonaMensajeria) = 'SUR' THEN 'SUR'  \n");
			sbQuery.append("WHEN COALESCE(PCE.ZonaMensajeria,PI.ZonaMensajeria, PFR.ZonaMensajeria, PPM.ZonaMensajeria) = 'CUERNAVACA' THEN 'CUERNAVACA' \n"); 
			sbQuery.append("WHEN COALESCE(PCE.ZonaMensajeria,PI.ZonaMensajeria, PFR.ZonaMensajeria, PPM.ZonaMensajeria) = 'TOLUCA' THEN 'TOLUCA'  \n");
			sbQuery.append("WHEN COALESCE(PCE.ZonaMensajeria,PI.ZonaMensajeria, PFR.ZonaMensajeria, PPM.ZonaMensajeria) = 'DHL' OR   \n");
			sbQuery.append("COALESCE(PCE.ZonaMensajeria,PI.ZonaMensajeria, PFR.ZonaMensajeria, PPM.ZonaMensajeria) = 'UPS' OR  \n");
			sbQuery.append("COALESCE(PCE.ZonaMensajeria,PI.ZonaMensajeria, PFR.ZonaMensajeria, PPM.ZonaMensajeria) = 'FEDEX' OR  \n");
			sbQuery.append("COALESCE(PCE.ZonaMensajeria,PI.ZonaMensajeria, PFR.ZonaMensajeria, PPM.ZonaMensajeria) = 'ESTAFETA' THEN 'FORANEO' \n"); 
			sbQuery.append("WHEN COALESCE(PCE.ZonaMensajeria,PI.ZonaMensajeria, PFR.ZonaMensajeria, PPM.ZonaMensajeria) = 'Almacén' THEN 'RECOGE EN PROQUIFA' \n"); 
			sbQuery.append("ELSE 'OTROS' END Zona  \n");
			sbQuery.append("FROM (SELECT * FROM PartidadeCompraenInspeccion WHERE Estado = 'En Inspección') PI \n");  
//			sbQuery.append("FULL JOIN (SELECT * FROM getPartidaCompraPorEmbalar('Programado') WHERE idPPedido NOT IN (SELECT FK01_PPedido FROM EmbalarPEdido)) PPR ON PPR.idPPEdido = PI.idPPedido \n");  
			sbQuery.append("FULL JOIN (SELECT * FROM getPartidaCompraPorEmbalar('Metodo') WHERE idPPedido NOT IN (SELECT FK01_PPedido FROM EmbalarPEdido)) PPM ON PPM.idPPEdido = PI.idPPedido \n");
			sbQuery.append("FULL JOIN (SELECT * FROM getPartidaCompraPorEmbalar('FacturaRemision') WHERE idPPedido NOT IN (SELECT FK01_PPedido FROM EmbalarPEdido)) PFR ON PFR.idPPEdido = PI.idPPedido \n");  
			sbQuery.append("FULL JOIN (SELECT * FROM getPartidaCompraPorEmbalar('') WHERE idPPedido NOT IN (SELECT FK01_PPedido FROM EmbalarPEdido)) PCE ON PI.idPPedido = PCE.idPPedido ) Partidas \n");
			sbQuery.append("GROUP BY idCliente, Zona ) Botonera \n");
			sbQuery.append("GROUP BY Zona ) Botonera GROUP BY Zona \n");
			sbQuery.append("HAVING	SUM(TotalClientes) > 0 \n");
			sbQuery.append("ORDER BY Orden \n");
			sbQuery.append(" \n");
			
			log.info(sbQuery.toString());
			
			Map<String, Object> param = new HashMap<String, Object>();
			return jdbcTemplate.query(sbQuery.toString(), param, new BeanPropertyRowMapper<>(Prioridades.class));
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	
	@Override
	public Map<String,List<Prioridades>> obtenerListado() throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("SELECT PP.*, COALESCE(HyL.Calle, PE.Calle) Calle, COALESCE(HyL.CP, PE.CP) CP, DATEDIFF(DAY,GETDATE(), FEE) Dias, EM.Nombre Cobrador, LOWER(EM.Usuario + '@proquifa.net') emailCobrador, \n"); 
			sbQuery.append("EM.Ext ExtCobrador, ES.Nombre ESAC, LOWER(ES.Usuario + '@proquifa.net') emailEsac, ES.Ext ExtEsac \n");   
			sbQuery.append("FROM (    \n");
			sbQuery.append("SELECT COALESCE(PCE.idCliente, PI.Clave, PPR.idCliente, PFR.idCliente, PPM.idCliente) idCliente, COALESCE(PCE.Cliente, PI.Nombre, PPR.Cliente, PFR.Cliente, PPM.Cliente) Cliente, \n");   
			sbQuery.append("CASE WHEN COALESCE(PCE.Ruta, PI.Ruta, PPR.Ruta, PFR.Ruta, PPM.Ruta)  = 'Foraneo' THEN 'FORANEO'     \n");
			sbQuery.append("WHEN COALESCE(PCE.ZonaMensajeria,PI.ZonaMensajeria, PPR.ZonaMensajeria, PFR.ZonaMensajeria, PPM.ZonaMensajeria) = 'NORTE' THEN 'NORTE' \n");   
			sbQuery.append("WHEN COALESCE(PCE.ZonaMensajeria,PI.ZonaMensajeria, PPR.ZonaMensajeria, PFR.ZonaMensajeria, PPM.ZonaMensajeria) = 'SUR' THEN 'SUR'    \n");
			sbQuery.append("WHEN COALESCE(PCE.ZonaMensajeria,PI.ZonaMensajeria, PPR.ZonaMensajeria, PFR.ZonaMensajeria, PPM.ZonaMensajeria) = 'CUERNAVACA' THEN 'CUERNAVACA' \n");   
			sbQuery.append("WHEN COALESCE(PCE.ZonaMensajeria,PI.ZonaMensajeria, PPR.ZonaMensajeria, PFR.ZonaMensajeria, PPM.ZonaMensajeria) = 'TOLUCA' THEN 'TOLUCA'    \n");
			sbQuery.append("WHEN COALESCE(PCE.ZonaMensajeria,PI.ZonaMensajeria, PPR.ZonaMensajeria, PFR.ZonaMensajeria, PPM.ZonaMensajeria) = 'DHL' OR     \n");
			sbQuery.append("COALESCE(PCE.ZonaMensajeria,PI.ZonaMensajeria, PPR.ZonaMensajeria, PFR.ZonaMensajeria, PPM.ZonaMensajeria) = 'UPS' OR    \n");
			sbQuery.append("COALESCE(PCE.ZonaMensajeria,PI.ZonaMensajeria, PPR.ZonaMensajeria, PFR.ZonaMensajeria, PPM.ZonaMensajeria) = 'FEDEX' OR    \n");
			sbQuery.append("COALESCE(PCE.ZonaMensajeria,PI.ZonaMensajeria, PPR.ZonaMensajeria, PFR.ZonaMensajeria, PPM.ZonaMensajeria) = 'ESTAFETA' THEN 'FORANEO' \n");   
			sbQuery.append("WHEN COALESCE(PCE.ZonaMensajeria,PI.ZonaMensajeria, PPR.ZonaMensajeria, PFR.ZonaMensajeria, PPM.ZonaMensajeria) = 'Almacén' THEN 'RECOGE EN PROQUIFA' \n");   
			sbQuery.append("ELSE 'OTROS' END Etiqueta, COALESCE(PCE.ZonaMensajeria,PI.ZonaMensajeria, PPR.ZonaMensajeria, PFR.ZonaMensajeria, PPM.ZonaMensajeria) Zona,    \n");
			sbQuery.append("COALESCE(PCE.Ruta,PI.Ruta, PPR.Ruta, PFR.Ruta, PPM.Ruta) Ruta, COALESCE(PCE.horario,PI.horario, PPR.horario, PFR.horario, PPM.horario) idHorario,    \n");
			sbQuery.append("COALESCE(PCE.CPedido,PI.CPedido, PPR.CPedido, PFR.Cpedido, PPM.CPEdido) CPedido, COALESCE(PCE.Contacto,PI.Contacto, PPR.Contacto, PFR.Contacto, PPM.Contacto) Contacto, \n");   
			sbQuery.append("COALESCE(PCE.FPEntrega,PI.FPEntrega, PPR.FPEntrega, PFR.FPEntrega, PPM.FPEntrega) FEE, COALESCE(PCE.idPRoducto,PI.idPRoducto, PPR.idProducto, PFR.idProducto, PPM.idProducto) idPRoducto, \n");   
			sbQuery.append("COALESCE(PCE.Codigo,PI.Codigo, PPR.Codigo, PFR.Codigo, PPM.Codigo) Codigo, CAST(COALESCE(PCE.Concepto,PI.Descripcion, PPR.Concepto, PFR.Concepto, PPM.Concepto) as VARCHAR(MAX)) Descripcion, \n");   
			sbQuery.append("COALESCE(PCE.Manejo_transporte,PI.Manejo_transporte, PPR.Manejo_Transporte, PPM.Manejo_Transporte) ManejoTransporte, COALESCE(PCE.Fabrica,PI.Fabrica, PPR.Fabrica, PFR.Fabrica, PPM.Fabrica) Fabrica, \n");   
			sbQuery.append("COALESCE(PCE.Cant,PI.Cant, PPR.Cant, PFR.Cant, PPM.Cant) Piezas, COALESCE(PCE.Puesto,PI.Puesto, PPR.Puesto, PFR.Puesto, PPM.Puesto) Puesto,    \n");

			sbQuery.append("CASE WHEN PI.idPPedido IS NOT NULL THEN 'Inspección' ELSE 'Embalaje' END Tipo,    \n");
			sbQuery.append("COALESCE(PCE.idPPedido, PI.idPPedido, PPR.IdPPedido, PFR.idPPedido, PPM.idPPEdido) idPPedido, COALESCE(PCE.Urgencia, PI.Urgencia, PPR.Urgencia, PFR.Urgencia, PPM.URgencia) Urgencia, \n");   
			sbQuery.append("CASE WHEN PPR.IdPPEdido IS NOT NULL THEN 1 ELSE 0 END Programado,    \n");
			sbQuery.append("COALESCE((PCE.Monto * PCE.Cant), (PI.Monto * PI.Cant), (PPR.Monto * PPR.Cant), (PFR.Monto * PFR.Cant), (PPM.Monto * PPM.Cant)) Monto, \n");   
			sbQuery.append("COALESCE(PCE.Pausado, PI.Pausado, PPR.Pausado, PFR.Pausado, PPM.Pausado, 0) Pausado,   \n");

			sbQuery.append("CASE WHEN PFR.idPPedido IS NOT NULL THEN 1 ELSE 0 END Restriccion,  \n");
			sbQuery.append("COALESCE(PCE.Factura_Remision, PPR.Factura_Remision, PFR.Factura_Remision, PPM.Factura_Remision, 0) FacturaRemision, \n"); 
			sbQuery.append("COALESCE(PCE.Remisionar, PPR.Remisionar, PFR.Remisionar, PPM.Remisionar, 0) isRemision,  \n");
			sbQuery.append("COALESCE(PCE.ValidarMetodo, PPR.ValidarMetodo, PFR.ValidarMetodo, PPM.ValidarMetodo, 0) isMetodo, \n");
			sbQuery.append("CASE WHEN PPM.idPPedido IS NOT NULL THEN 1 ELSE 0 END vieneMetodo, COALESCE(PCE.idCobrador, PPR.idCobrador, PFR.idCobrador, PPM.idCobrador) idCobrador, \n");
			sbQuery.append("COALESCE(PCE.ESAC, PPR.ESAC, PFR.ESAC, PPM.ESAC) Esac \n");

			sbQuery.append("FROM (    \n");
			sbQuery.append("SELECT * FROM PartidadeCompraenInspeccion WHERE Estado = 'En Inspección') PI \n");   
			sbQuery.append("FULL JOIN (SELECT * FROM getPartidaCompraPorEmbalar('Programado') WHERE idPPedido NOT IN (SELECT FK01_PPedido FROM EmbalarPEdido)) PPR ON PPR.idPPEdido = PI.idPPedido \n");   
   
			sbQuery.append("FULL JOIN (SELECT * FROM getPartidaCompraPorEmbalar('Metodo') WHERE idPPedido NOT IN (SELECT FK01_PPedido FROM EmbalarPEdido)) PPM ON PPM.idPPEdido = PI.idPPedido    \n");
			sbQuery.append("FULL JOIN (SELECT * FROM getPartidaCompraPorEmbalar('FacturaRemision') WHERE idPPedido NOT IN (SELECT FK01_PPedido FROM EmbalarPEdido)) PFR ON PFR.idPPEdido = PI.idPPedido \n");  
			sbQuery.append("FULL JOIN (SELECT * FROM getPartidaCompraPorEmbalar('') WHERE idPPedido NOT IN (SELECT FK01_PPedido FROM EmbalarPEdido)) PCE ON PI.idPPedido = PCE.idPPedido ) PP    \n");

			sbQuery.append("INNER JOIN Pedidos PE ON PE.CPedido = PP.CPedido    \n");
			sbQuery.append("LEFT JOIN HorarioyLugar HyL ON HyL.idHorario = PP.idHorario \n");
			sbQuery.append("LEFT JOIN Empleados EM ON EM.Clave = PP.idCobrador \n");
			sbQuery.append("LEFT JOIN Empleados ES ON ES.Usuario = PP.Esac \n");
			sbQuery.append("ORDER BY Urgencia DESC,FEE, CPedido, isMetodo \n");
			sbQuery.append(" \n");	
			
			log.info(sbQuery.toString());
			
			Map<String, Object> param = new HashMap<String, Object>();
			List<Prioridades> lstReturn = new ArrayList<Prioridades>();
			Map<String, Prioridades> map = new HashMap<String, Prioridades>();
			Map<String, List<Prioridades>> mapReturn = new HashMap<String, List<Prioridades>>();
			Map<String, String> cpedidos = new HashMap<String, String>();
			mapReturn.put("TODAS", new ArrayList<Prioridades>());
			mapReturn.put("EMBALAR", new ArrayList<Prioridades>());
			mapReturn.put("INSPECCIÓN", new ArrayList<Prioridades>());
			jdbcTemplate.query(sbQuery.toString(), param, new RowMapper<Prioridades>() {
				Integer numPrio = 0;
				@Override
				public Prioridades mapRow(ResultSet rs, int rowNum) throws SQLException {
					Prioridades prioridad = new Prioridades();
					
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMMM/yyyy", new  Locale("es","ES"));
					SimpleDateFormat dateFormato = new SimpleDateFormat("dd/MM/yyyy", new  Locale("es","ES"));
					prioridad.setCalle(rs.getString("Calle"));
					prioridad.setCliente(rs.getString("Cliente"));
					prioridad.setCodigo(rs.getString("Codigo"));
					prioridad.setContacto(rs.getString("Contacto"));
					prioridad.setCp(rs.getString("CP"));
					prioridad.setCpedido(rs.getString("CPEdido"));
					prioridad.setDescripcion(rs.getString("Descripcion"));
					prioridad.setEtiqueta(rs.getString("Etiqueta"));
					prioridad.setFabrica(rs.getString("Fabrica"));
					prioridad.setProgramado(rs.getInt("Programado"));
					if (rs.getTimestamp("FEE") != null) {
						prioridad.setFee(dateFormat.format(rs.getTimestamp("FEE")));
						prioridad.setFechaPartida(dateFormato.format(rs.getTimestamp("FEE")));
					}
					prioridad.setIdCliente(rs.getInt("idCliente"));
					prioridad.setIdHorario(rs.getInt("idHorario"));
					prioridad.setIdPPedido(rs.getInt("idPPedido"));
					prioridad.setIdProducto(rs.getInt("idProducto"));
					prioridad.setManejoTransporte(rs.getString("ManejoTransporte"));
					prioridad.setPiezas(rs.getInt("Piezas"));
					prioridad.setPuesto(rs.getString("Puesto"));
					prioridad.setTipo(rs.getString("Tipo"));
					prioridad.setZona(rs.getString("Zona"));
					prioridad.setRuta(rs.getString("Ruta"));
					prioridad.setDias(rs.getInt("Dias"));
					prioridad.setTotalPartidas(1);
					prioridad.setTotalPiezas(prioridad.getPiezas());
					prioridad.setUrgencia(rs.getInt("Urgencia"));
					prioridad.setPausado(rs.getInt("Pausado"));
					prioridad.setRestriccion(rs.getInt("Restriccion"));
					prioridad.setFacturaRemision(rs.getInt("FacturaRemision"));
					prioridad.setRemision(rs.getInt("isRemision"));
					prioridad.setMetodo(rs.getInt("isMetodo"));
					prioridad.setRestriccionMetodo(rs.getInt("vieneMetodo"));
					prioridad.setNombreCobrador(rs.getString("Cobrador"));
					prioridad.setEmailCobrador(rs.getString("emailCobrador"));
					prioridad.setNombreEsac(rs.getString("ESAC"));
					prioridad.setEmailEsac(rs.getString("emailEsac"));
					prioridad.setExtEsac(rs.getString("ExtEsac"));
					prioridad.setExtCobrador(rs.getString("ExtCobrador"));
					prioridad.setIndicePrioridad(numPrio);
					prioridad.setCpedidos(new ArrayList<String>());
					
					prioridad.setMonto(rs.getFloat("Monto"));
					
					StringBuilder etiqueta = new StringBuilder(prioridad.getIdCliente().toString());
					etiqueta.append("-").append(prioridad.getIdHorario()).append("-").append(prioridad.getContacto());
					etiqueta.append("-").append(prioridad.getZona()).append("-").append(prioridad.getRuta());
					
					if (map.get(etiqueta.toString()) != null) {
						Prioridades partida = map.get(etiqueta.toString());
						partida.setTotalPartidas(partida.getTotalPartidas() + 1);
						partida.setTotalPiezas(partida.getTotalPiezas() + prioridad.getPiezas());
						partida.setIdPPedidos(partida.getIdPPedidos() + "," + prioridad.getIdPPedido().toString());
						partida.getPartidas().add(prioridad);
						
						try {							
							if (dateFormat.parse(prioridad.getFee()).before(dateFormat.parse(partida.getFee()))) {								
								partida.setFee(prioridad.getFee());
							}
						}catch (ParseException e) {
				            e.printStackTrace();
				        }
						
						partida.setMonto(prioridad.getMonto() + partida.getMonto());
						
						if (prioridad.getProgramado() == 1) {
							partida.setProgramado(prioridad.getProgramado());
						}
						
						if (cpedidos.get(prioridad.getCpedido()) == null ) {
							partida.setTotalPedidos(partida.getTotalPedidos() + 1);
							cpedidos.put(prioridad.getCpedido(), prioridad.getCpedido());
							
							if (prioridad.getMetodo() == 1) {
								partida.getCpedidos().add(prioridad.getCpedido());
							}
						}
						
						if (prioridad.getTipo().equals("Embalaje")) {
							partida.setTotalEmbalar(partida.getTotalEmbalar() + 1);
							if (mapReturn.get("EMBALAR").indexOf(partida) == -1 ) {
								mapReturn.get("EMBALAR").add(partida);
							}
						} else {
							partida.setTotalInspeccion(partida.getTotalInspeccion() + 1);
							if (mapReturn.get("INSPECCIÓN").indexOf(partida) == -1 ) {
								mapReturn.get("INSPECCIÓN").add(partida);
							}
						}
						
					} else {
						numPrio ++;
						prioridad.setIndicePrioridad(numPrio);
						Prioridades partida = new Prioridades(prioridad);
						prioridad.setPartidas(new ArrayList<Prioridades>());
						prioridad.getPartidas().add(partida);
						prioridad.setIdPPedidos(prioridad.getIdPPedido().toString());
						map.put(etiqueta.toString(), prioridad);
						lstReturn.add(prioridad);
						mapReturn.get("TODAS").add(prioridad);
						
						if (prioridad.getTipo().equals("Embalaje")) {
							prioridad.setTotalEmbalar(1);
							mapReturn.get("EMBALAR").add(prioridad);
						} else {
							prioridad.setTotalInspeccion(1);
							mapReturn.get("INSPECCIÓN").add(prioridad);
						}
						
						if (cpedidos.get(prioridad.getCpedido()) == null ) {
							prioridad.setTotalPedidos(1);
							cpedidos.put(prioridad.getCpedido(), prioridad.getCpedido());
							
							if (prioridad.getMetodo() == 1) {
								prioridad.getCpedidos().add(prioridad.getCpedido());
							}
						}
						
						if (mapReturn.get(prioridad.getEtiqueta()) != null) {
							mapReturn.get(prioridad.getEtiqueta()).add(prioridad);
						} else {
							List<Prioridades> lstAgrupada = new ArrayList<Prioridades>();
							lstAgrupada.add(prioridad);
							mapReturn.put(prioridad.getEtiqueta(), lstAgrupada);
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
	public boolean guardarUrgencia(String idPedidos, Integer urgencia) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("UPDATE PPedidos SET Urgencia = :urgencia WHERE idPPedido IN ( ");
			sbQuery.append(idPedidos).append(") \n");
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("urgencia", urgencia);
			jdbcTemplate.update(sbQuery.toString(), param);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	@Override
	public boolean guardarPausa(String idPedidos, Integer urgencia) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("UPDATE PPedidos SET Pausado = :urgencia WHERE idPPedido IN ( ");
			sbQuery.append(idPedidos).append(") \n");
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("urgencia", urgencia);
			jdbcTemplate.update(sbQuery.toString(), param);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	
	@Override
	public Map<String,List<Prioridades>> obtenerListadoEnvio() throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("SELECT P.idCliente, CL.Nombre Cliente, '' Etiqueta, P.ZonaMensajeria Zona, P.Ruta, P.FK01_Horario idHorario, PEN.Folio idPPedido, \n"); 
			sbQuery.append("P.Contacto, MIN(PP.FPEntrega) FEE,  P.Puesto, '' Tipo, CASE WHEN PEN.Partida IS NULL OR PEN.Partida = 0 THEN 0 ELSE 1 END Urgencia,  \n");
			sbQuery.append("COALESCE(HyL.Calle, P.Calle) Calle, COALESCE(HyL.CP, P.CP) CP, DATEDIFF(DAY,GETDATE(), MIN(PP.FPEntrega)) Dias, PL.Folio Codigo \n");
			sbQuery.append("FROM Pendiente PEN \n");
			sbQuery.append("INNER JOIN PAckingList PL ON PL.Folio = PEN.Docto \n");
			sbQuery.append("INNER JOIN PPackingList PPL ON PPl.FK01_PAckingList = PL.PK_PackingList \n");
			sbQuery.append("INNER JOIN FacturaElectronica AS FE ON FE.PK_Factura = PPL.FK03_FacturaElectronica \n");
			sbQuery.append("INNER JOIN Factura_FElectronica AS FFE ON FFE.FK02_FacturaElectronica = FE.PK_Factura \n");
			sbQuery.append("INNER JOIN Facturas AS F ON F.idFactura = FFE.FK01_Factura \n");
			sbQuery.append("INNER JOIN EmbalarPedido EP ON EP.PK_EmbalarPEdido = PPL.FK02_EmbalarPedido \n");
			sbQuery.append("INNER JOIN PPedidos PP ON PP.idPPEdido = EP.FK01_PPedido \n");
			sbQuery.append("INNER JOIN PEdidos P ON P.CPedido = PP.CPedido \n");
			sbQuery.append("INNER JOIN Clientes CL ON CL.Clave = P.idCliente \n");
			sbQuery.append("LEFT JOIN Productos PR ON PR.idProducto = PP.FK02_Producto \n");
			sbQuery.append("LEFT JOIN HorarioyLugar HyL ON HyL.idHorario = P.FK01_Horario  \n");
			sbQuery.append("WHERE PEN.FFin IS NULL AND PEN.Tipo = 'Por Enviar' AND (P.GuiaXCliente = 0 OR P.GuiaXCliente IS NULL) \n");
			sbQuery.append("AND CASE WHEN F.CPago IN ('PAGO CONTRA ENTREGA', 'PREPAGO 100%') THEN 1 ELSE 0 END = CASE WHEN F.Estado = 'Cobrada' THEN 1 ELSE 0 END \n");
			sbQuery.append("GROUP BY P.idCliente, CL.Nombre, P.ZonaMensajeria, P.Ruta, P.FK01_Horario, P.Contacto, \n");
			sbQuery.append("P.Puesto,PEN.Partida, COALESCE(HyL.Calle, P.Calle), COALESCE(HyL.CP, P.CP), PEN.Folio, PL.Folio \n");
			sbQuery.append("ORDER BY Urgencia DESC,FEE \n");
			
			log.info(sbQuery.toString());
			
			Map<String, Object> param = new HashMap<String, Object>();
			List<Prioridades> lstReturn = new ArrayList<Prioridades>();
			Map<String, Prioridades> map = new HashMap<String, Prioridades>();
			Map<String, List<Prioridades>> mapReturn = new HashMap<String, List<Prioridades>>();
			mapReturn.put("TODAS", new ArrayList<Prioridades>());
			jdbcTemplate.query(sbQuery.toString(), param, new RowMapper<Prioridades>() {
				Integer numPrio = 0;
				@Override
				public Prioridades mapRow(ResultSet rs, int rowNum) throws SQLException {
					Prioridades prioridad = new Prioridades();
					
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMMM/yyyy", new  Locale("es","ES"));
					SimpleDateFormat dateFormato = new SimpleDateFormat("dd/MM/yyyy", new  Locale("es","ES"));
					prioridad.setIdCliente(rs.getInt("idCliente"));
					prioridad.setCliente(rs.getString("Cliente"));
					prioridad.setCalle(rs.getString("Calle"));
					prioridad.setContacto(rs.getString("Contacto"));
					prioridad.setCp(rs.getString("CP"));
					prioridad.setEtiqueta(rs.getString("Etiqueta"));
					prioridad.setCodigo(rs.getString("Codigo"));
					prioridad.setIdPPedido(rs.getInt("IdPPedido"));
					if (rs.getTimestamp("FEE") != null) {
						prioridad.setFee(dateFormat.format(rs.getTimestamp("FEE")));
						prioridad.setFechaPartida(dateFormato.format(rs.getTimestamp("FEE")));
					}
					prioridad.setIdHorario(rs.getInt("idHorario"));
					prioridad.setPuesto(rs.getString("Puesto"));
					prioridad.setTipo(rs.getString("Tipo"));
					prioridad.setZona(rs.getString("Zona"));
					prioridad.setRuta(rs.getString("Ruta"));
					prioridad.setDias(rs.getInt("Dias"));
					prioridad.setTotalPartidas(1);
					prioridad.setUrgencia(rs.getInt("Urgencia"));
					prioridad.setIndicePrioridad(numPrio);
					
					StringBuilder etiqueta = new StringBuilder(prioridad.getIdCliente().toString());
					etiqueta.append("-").append(prioridad.getIdHorario()).append("-").append(prioridad.getContacto());
					etiqueta.append("-").append(prioridad.getZona()).append("-").append(prioridad.getRuta());
					
					if (map.get(etiqueta.toString()) != null) {
						Prioridades partida = map.get(etiqueta.toString());
						partida.setTotalPartidas(partida.getTotalPartidas() + 1);
						partida.setIdPPedidos(partida.getIdPPedidos() + "," + prioridad.getIdPPedido().toString());
						partida.getPartidas().add(prioridad);
					} else {
						numPrio ++;
						prioridad.setIndicePrioridad(numPrio);
						Prioridades partida = new Prioridades(prioridad);
						prioridad.setPartidas(new ArrayList<Prioridades>());
						prioridad.getPartidas().add(partida);
						prioridad.setIdPPedidos(prioridad.getIdPPedido().toString());
						map.put(etiqueta.toString(), prioridad);
						lstReturn.add(prioridad);
						mapReturn.get("TODAS").add(prioridad);
						
						if (mapReturn.get(prioridad.getEtiqueta()) != null) {
							mapReturn.get(prioridad.getEtiqueta()).add(prioridad);
						} else {
							List<Prioridades> lstAgrupada = new ArrayList<Prioridades>();
							lstAgrupada.add(prioridad);
							mapReturn.put(prioridad.getEtiqueta(), lstAgrupada);
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
	public boolean guardarUrgenciaEnvio(String idPedidos, Integer urgencia) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("UPDATE Pendiente SET Partida = :urgencia WHERE Folio IN ( ");
			sbQuery.append(idPedidos).append(") \n");
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("urgencia", urgencia);
			jdbcTemplate.update(sbQuery.toString(), param);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	
	@Override
	public boolean guardarFacturaRemision(String idPedidos, Integer facturaRemision, Integer remisionar) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("UPDATE PPedidos SET Factura_Remision = :facturaRemision WHERE idPPedido IN ( ");
			sbQuery.append(idPedidos).append(") \n");
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("facturaRemision", facturaRemision);
			jdbcTemplate.update(sbQuery.toString(), param);
			
			if (facturaRemision == 1) {
				sbQuery = new StringBuilder("");
				sbQuery.append("UPDATE PED SET PED.Remisionar = :remisionar FROM Pedidos PED \n");
				sbQuery.append("INNER JOIN PPEdidos PP ON PP.CPedido = PED.CPEdido \n");
				sbQuery.append("WHERE PP.idPPEdido IN (");
				sbQuery.append(idPedidos).append(") \n");
				param.put("remisionar", remisionar);
				jdbcTemplate.update(sbQuery.toString(), param);
			}
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	@Override
	public Map<String, List<Prioridades>> obtenerListadoStock() throws ProquifaNetException{
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("SELECT St.idstock, PC.idpcompra, Prod.idProducto, Prod.Codigo,\n");
			sbQuery.append("CASE WHEN Prod.Tipo = 'Publicaciones' THEN Ins.Edicion ELSE Ins.Lote END Lote, Prod.Concepto, \n");
			sbQuery.append("COALESCE(Prod.Cantidad, '') Cantidad, Prod.Unidad, Prod.Moneda, Prod.costo, Prod.Tipo, \n");
			sbQuery.append("COALESCE(Prod.Control, '') AS Control ,PZ.idPieza, \n");
			sbQuery.append("CASE WHEN Prod.Fabrica='USP' THEN CASE WHEN Ins.Lote COLLATE Modern_Spanish_CI_AS = Prod.Lote THEN 'VIGENTE' ELSE 'POR EXPIRAR' END \n");
			sbQuery.append("WHEN Ins.MesCaducidad IS NULL OR Ins.MesCaducidad  = '--ND--'  THEN 'OTROS' \n");
			sbQuery.append("WHEN ((MONTH(GETDATE()) - M.numberMonth) >= 6) AND (Ins.AnoCaducidad = YEAR(GETDATE())) THEN 'VIGENTE' \n");
			sbQuery.append("WHEN (Ins.AnoCaducidad > YEAR(GETDATE())) AND ((12 - (MONTH(GETDATE())) + M.numberMonth) >= 6) THEN 'VIGENTE' \n");
			sbQuery.append("ELSE 'POR EXPIRAR' END TipoLote, \n");
			sbQuery.append("MONTH(GETDATE()) AS MonthNow, M.numberMonth, COALESCE(Prod.Manejo, '') Manejo, \n");
			sbQuery.append("Ins.MesCaducidad + ' ' + Ins.AnoCaducidad AS Caducidad, LA.Fecha_ValidoHasta , \n");
			sbQuery.append("'FD-' + PP.CPedido + '-'+ cast(PP.Part as varchar(2)) AS Etiqueta, Prod.Fabrica  \n");
			sbQuery.append("FROM Stock AS St \n");
			sbQuery.append("LEFT JOIN PCompras AS PC ON PC.idpCompra = St.idpCompra \n");
			sbQuery.append("INNER JOIN PIEZA AS PZ ON PZ.idPCOMPRA = PC.idPCompra \n");
			sbQuery.append("INNER JOIN PPedidos AS PP ON PP.idPPedido = PC.FK03_PPedido \n");
			sbQuery.append("INNER JOIN InspeccionOC AS Ins ON Ins.idPCompra = PC.idpCompra \n");
			//sbQuery.append("LEFT JOIN Productos AS Prod ON Prod.Codigo = PC.Codigo AND Prod.Fabrica = PC.Fabrica \n");
			sbQuery.append("LEFT JOIN Productos AS Prod ON Prod.idProducto = PC.FK01_Producto \n");
			sbQuery.append("LEFT JOIN Lote_Anterior AS LA ON LA.FK01_Producto = Prod.idProducto AND LA.Lote_Anterior COLLATE Modern_Spanish_CI_AS = Ins.Lote \n");
			sbQuery.append("LEFT JOIN Meses AS M ON Ins.MesCaducidad =  M.mes COLLATE SQL_Latin1_General_CP1_CI_AS \n");
			sbQuery.append("WHERE St.cantDisponible > 0 \n");
			
			Map<String, Object> param = new HashMap<String, Object>();
			List<Prioridades> lstReturn = new ArrayList<Prioridades>();
			Map<String, Prioridades> map = new HashMap<String, Prioridades>();
			Map<String, List<Prioridades>> mapReturn = new HashMap<String, List<Prioridades>>();
			mapReturn.put("TODAS", new ArrayList<Prioridades>());
			System.out.println(sbQuery);
			jdbcTemplate.query(sbQuery.toString(), param, new RowMapper<Prioridades>() {
				@Override
				public Prioridades mapRow(ResultSet rs, int rowNum) throws SQLException  {
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMMM/yyyy", new  Locale("es","ES"));
					SimpleDateFormat dateFormato = new SimpleDateFormat("dd/MM/yyyy", new  Locale("es","ES"));
					Prioridades prioridad = new Prioridades();
					prioridad.setFabrica(rs.getString("Fabrica"));
					prioridad.setEtiqueta(rs.getString("TipoLote"));
					prioridad.setCodigo(rs.getString("Codigo"));
					prioridad.setControl(rs.getString("Control"));
					prioridad.setCliente(rs.getString("Lote"));
					prioridad.setTipo(rs.getString("etiqueta"));
					prioridad.setManejoTransporte(rs.getString("Manejo"));
					prioridad.setIdProducto(rs.getInt("idstock"));
					prioridad.setMonto(rs.getDouble("costo"));
					prioridad.setCpedido(rs.getString("Tipo"));
					prioridad.setRuta(rs.getString("Moneda"));
					prioridad.setIdPCompra(rs.getInt("idpcompra"));
					prioridad.setPiezas(rs.getInt("idPieza"));;
					if(prioridad.getFabrica().equalsIgnoreCase("USP") && rs.getTimestamp("Fecha_ValidoHasta") != null) {
						prioridad.setFee(dateFormat.format(rs.getTimestamp("Fecha_ValidoHasta")));
					} else if (rs.getString("caducidad") != null){
						prioridad.setFee(rs.getString("caducidad"));
					} else {
						prioridad.setFee("N/A");
					}
					if(prioridad.getFabrica() != null && prioridad.getFabrica() != "USP") {
						prioridad.setDescripcion(rs.getString("Concepto") + "(" + rs.getString("Cantidad") + rs.getString("Unidad") + ")");
					} else {
						prioridad.setDescripcion(rs.getString("Concepto"));
					}
					
					if(prioridad.getEtiqueta() != null) {
					if (mapReturn.get(prioridad.getEtiqueta()) != null) {
						mapReturn.get("TODAS").add(prioridad);
						mapReturn.get(prioridad.getEtiqueta()).add(prioridad);
					} else {
						List<Prioridades> lstAgrupada = new ArrayList<Prioridades>();
						lstAgrupada.add(prioridad);
						mapReturn.put(prioridad.getEtiqueta(), lstAgrupada);
						mapReturn.get("TODAS").add(prioridad);
					}
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
	
	@Override
	public List<Prioridades> obtenerMarcasStock() throws ProquifaNetException{
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("SELECT DISTINCT P.Fabrica \n");
			sbQuery.append("FROM ( \n");
			sbQuery.append("SELECT St.idstock, PC.idpcompra, Prod.idProducto, Prod.Codigo, Ins.Lote, Prod.Concepto, COALESCE(Prod.Cantidad, '') Cantidad, Prod.Unidad, Prod.Control, \n");
			sbQuery.append("CASE WHEN Prod.Fabrica='USP' THEN CASE WHEN Ins.Lote COLLATE Modern_Spanish_CI_AS = Prod.Lote THEN 'VIGENTE' ELSE 'POR EXPIRAR' END  \n");
			sbQuery.append("WHEN Ins.MesCaducidad  IS NULL THEN 'OTROS' \n");
			sbQuery.append("WHEN (MONTH(GETDATE()) - M.numberMonth) >= 6 THEN 'VIGENTE' ELSE 'POR EXPIRAR' END TipoLote, \n");
			sbQuery.append("MONTH(GETDATE()) AS MonthNow, M.numberMonth, Prod.Manejo, \n");
			sbQuery.append("LA.Fecha_ValidoHasta, 'FD-' + PP.CPedido + '-'+ cast(PP.Part as varchar(2)) AS Etiqueta,Ins.MesCaducidad + ' ' + Ins.AnoCaducidad AS Caducidad, Prod.Fabrica \n");
			sbQuery.append("FROM Stock AS St \n");
			sbQuery.append("LEFT JOIN PCompras AS PC ON PC.idpCompra = St.idpCompra \n");
			sbQuery.append("INNER JOIN PPedidos AS PP ON PP.idPPedido = PC.FK03_PPedido \n");
			sbQuery.append("INNER JOIN InspeccionOC AS Ins ON Ins.idPCompra = PC.idpCompra \n");
			sbQuery.append("LEFT JOIN Productos AS Prod ON Prod.Codigo = PC.Codigo AND Prod.Fabrica = PC.Fabrica \n");
			sbQuery.append("LEFT JOIN Lote_Anterior AS LA ON LA.FK01_Producto = Prod.idProducto AND LA.Lote_Anterior COLLATE Modern_Spanish_CI_AS = Ins.Lote \n");
			sbQuery.append("LEFT JOIN Meses AS M ON Ins.MesCaducidad =  M.mes COLLATE SQL_Latin1_General_CP1_CI_AS \n");
			sbQuery.append("WHERE St.cantDisponible > 0) P \n");
			Map<String, Object> param = new HashMap<String, Object>();
			return jdbcTemplate.query(sbQuery.toString(), param, new BeanPropertyRowMapper<>(Prioridades.class));
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ProquifaNetException();
		}
		
	}
	
	@Override
	public Boolean deleteStock(Prioridades producto) throws ProquifaNetException{
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("UPDATE STOCK SET FSalida = GETDATE(), Razon = :descripcion, cantDisponible = 0 \n");
			sbQuery.append("WHERE  idStock = :idStock \n");
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("idStock", producto.getIdProducto());
			param.put("descripcion", producto.getDescripcion());
			System.out.println("Ya se modifico del stock");
			jdbcTemplate.update(sbQuery.toString(), param);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ProquifaNetException();
		}
		
	}
	
	@Override
	public Boolean insertarPendiente(Pendiente pendiente) throws ProquifaNetException{
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("DECLARE @usuario varchar(30) = (SELECT TOP 1 Usuario From Empleados WHERE nivel = 31 AND fase > 0 AND Fk01_Funcion = 11) \n");
			sbQuery.append("INSERT INTO PENDIENTE (Docto, Partida, FInicio, Responsable, Tipo) \n");
			sbQuery.append("VALUES(:docto, :part, GETDATE(), @usuario, 'Producto a destrucción') \n");
			
			param.put("docto", pendiente.getDocto());
			param.put("part", pendiente.getPartida());
			jdbcTemplate.update(sbQuery.toString(), param);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	@Override
	public List<Prioridades> obtenerListadoDestruccion() throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("SELECT P.Docto idPCompra,P.Partida piezas,PROD.codigo, PROD.fabrica, ST.Razon contacto, PROD.Concepto descripcion, PROD.Control, \n");
			sbQuery.append("CASE WHEN PROD.Tipo = 'Publicaciones' THEN Ins.Edicion ELSE Ins.Lote END puesto, \n");
			sbQuery.append("P.Docto,'FD-' + PP.CPedido + '-'+ cast(PP.Part as varchar(2)) AS Etiqueta, \n");
			sbQuery.append("PROD.Moneda ruta, PROD.costo monto, Ins.MesCaducidad + ' ' + Ins.AnoCaducidad AS Caducidad, \n");
			sbQuery.append("LA.Fecha_ValidoHasta, PROD.Tipo, PP.Cpedido, PP.idPPedido, 0 active, COALESCE(Prod.Manejo, '') manejoTransporte, PC.PPEDIDO CP, C.Clave fee \n");
			sbQuery.append("FROM PENDIENTE P \n");
			sbQuery.append("INNER JOIN PIEZA AS PZ ON PZ.idPCOMPRA = P.Docto AND PZ.idPieza = P.Partida \n");
			sbQuery.append("INNER JOIN PCompras AS PC ON PC.idpCompra = P.Docto \n");
			sbQuery.append("INNER JOIN COMPRAS AS C ON C.PK_Compra = PC.FK02_Compra \n");
			sbQuery.append("INNER JOIN PPEDIDOS AS PP ON PP.idPPedido = PC.FK03_PPedido \n");
			sbQuery.append("INNER JOIN PRODUCTOS AS PROD ON PROD.idProducto = PP.FK02_Producto \n");
			sbQuery.append("INNER JOIN STOCK AS ST ON ST.idPCompra = PC.idPCompra \n");
			sbQuery.append("INNER JOIN InspeccionOC AS Ins ON Ins.idPCompra = PC.idpCompra \n");
			sbQuery.append("LEFT JOIN Lote_Anterior AS LA ON LA.FK01_Producto = Prod.idProducto AND LA.Lote_Anterior COLLATE Modern_Spanish_CI_AS = Ins.Lote \n");
			sbQuery.append("WHERE P.FFIN IS NULL AND P.TIPO = 'Producto a destrucción' \n");
			Map<String, Object> param = new HashMap<String, Object>();
			List<Prioridades> lstReturn = jdbcTemplate.query(sbQuery.toString(), param, new BeanPropertyRowMapper<>(Prioridades.class));
			return lstReturn;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	@Override
	public List<Prioridades> obtenerMarcasDestruccion() throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("SELECT DISTINCT VAL.Fabrica \n");
			sbQuery.append("FROM ( \n");
			sbQuery.append("SELECT PROD.Codigo, PROD.Fabrica, ST.Razon, PROD.Concepto, PROD.Control, \n");
			sbQuery.append("P.Docto,'FD-' + PP.CPedido + '-'+ cast(PP.Part as varchar(2)) AS Etiqueta, \n");
			sbQuery.append("PROD.Moneda, PROD.costo, Ins.MesCaducidad + ' ' + Ins.AnoCaducidad AS Caducidad, \n");
			sbQuery.append("LA.Fecha_ValidoHasta, PROD.Tipo, PP.Cpedido, PP.idPPedido, COALESCE(Prod.Manejo, '') manejoTransporte \n");
			sbQuery.append("FROM PENDIENTE P \n");
			sbQuery.append("INNER JOIN PIEZA AS PZ ON PZ.idPCOMPRA = P.Docto AND PZ.idPieza = P.Partida \n");
			sbQuery.append("INNER JOIN PCompras AS PC ON PC.idpCompra = P.Docto \n");
			sbQuery.append("INNER JOIN PPEDIDOS AS PP ON PP.idPPedido = PC.FK03_PPedido \n");
			sbQuery.append("INNER JOIN PRODUCTOS AS PROD ON PROD.idProducto = PP.FK02_Producto \n");
			sbQuery.append("INNER JOIN STOCK AS ST ON ST.idPCompra = PC.idPCompra \n");
			sbQuery.append("INNER JOIN InspeccionOC AS Ins ON Ins.idPCompra = PC.idpCompra \n");
			sbQuery.append("LEFT JOIN Lote_Anterior AS LA ON LA.FK01_Producto = Prod.idProducto AND LA.Lote_Anterior COLLATE Modern_Spanish_CI_AS = Ins.Lote \n");
			sbQuery.append("WHERE P.FFIN IS NULL AND P.TIPO = 'Producto a destrucción') VAL \n");
			Map<String, Object> param = new HashMap<String, Object>();
			return jdbcTemplate.query(sbQuery.toString(), param, new BeanPropertyRowMapper<>(Prioridades.class));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	@Override
	public Boolean cerrarPendiente(Prioridades result, String folio) throws ProquifaNetException{
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("BEGIN TRAN \n");
			sbQuery.append("DECLARE @FacturaXPagar AS Integer, @PartidasXPagar AS Integer, @PartidasCanceladas AS Integer \n");
			sbQuery.append("INSERT INTO ProductoDestruido (Fecha,idPieza,Inspector,FolioDS) VALUES (GETDATE(), :idPieza,'AHernandezM', :folioDS)  \n");
			sbQuery.append("IF @@ERROR <>0 BEGIN ROLLBACK TRAN RETURN END \n");
			sbQuery.append("UPDATE PCompras SET Estado='Cancelada' WHERE idPCompra = :idPCompra \n");
			sbQuery.append("IF @@ERROR <>0 BEGIN ROLLBACK TRAN RETURN END \n");
			sbQuery.append("UPDATE PPedidos SET Estado='Cancelada' WHERE CPedido = :cpedido AND Part = :ppedido \n");
			sbQuery.append("IF @@ERROR <>0 BEGIN ROLLBACK TRAN RETURN END \n");
			sbQuery.append("INSERT INTO PCompraHistorial (Fecha, Origen, Comentarios,idCompra, idPCompra, CPedido, PPedido, Tipo, Gestor) \n");
			sbQuery.append("VALUES (GETDATE(),'Matriz','Producto destruido',:compra, :idPCompra, :cpedido, :ppedido,'Producto destruido','AHernandezM') \n");
			sbQuery.append("IF @@ERROR <>0 BEGIN ROLLBACK TRAN RETURN END \n");
			sbQuery.append("UPDATE Pendiente SET FFin = GETDATE() WHERE Docto = :idPCompra AND Partida = :idPieza AND Tipo='Producto a destrucción' AND FFin IS NULL \n");
			sbQuery.append("IF @@ERROR <> 0 \n");
			sbQuery.append("BEGIN ROLLBACK TRAN RETURN END \n");
			sbQuery.append("SELECT  @FacturaXPagar= (FXP.idFxPagar) ,   @PartidasXPagar = (SELECT COUNT(*) FROM PFacturaxPagar \n");
			sbQuery.append("WHERE PFacturaxPagar.idFxPagar=FXP.idFxPagar ) ,   @PartidasCanceladas=  (SELECT COUNT(*) FROM PFacturaxPagar AS PFC \n");
			sbQuery.append("LEFT JOIN Pcompras PCC ON PFC.idPCompra=PCC.idPCompra \n");
			sbQuery.append("WHERE PFC.idFxPagar = PFC.idFxPagar AND PCC.Estado = 'Cancelada' \n");
			sbQuery.append("AND PFC.idFxPagar = PFXP.idFxPagar) \n");
			sbQuery.append("FROM PFacturaxPagar AS PFXP \n");
			sbQuery.append("LEFT JOIN FacturaxPagar AS FXP ON PFXP.idFxPagar = FXP.idFxPagar \n");
			sbQuery.append("LEFT JOIN PCompras AS PC ON PFXP.idPCompra =PC.idPCompra \n");
			sbQuery.append("WHERE PFXP.idPCompra = :idPCompra \n");
			sbQuery.append("IF @PartidasXPagar = @PartidasCanceladas \n");
			sbQuery.append("BEGIN \n");
			sbQuery.append("IF @PartidasXPagar > 0 \n");
			sbQuery.append("BEGIN \n");
			sbQuery.append("DELETE FROM Pendiente WHERE Docto = @FacturaXPagar AND TIPO='RevisarFactura' AND Responsable='JHernandez' AND FFIN IS NULL \n");
			sbQuery.append("IF @@ERROR <>0 \n");
			sbQuery.append("BEGIN \n");
			sbQuery.append("ROLLBACK TRAN RETURN END  END END \n");
			sbQuery.append("IF @@ERROR <>0 BEGIN ROLLBACK TRAN RETURN END \n");
			sbQuery.append("COMMIT TRAN \n");
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("idPCompra", result.getIdPCompra());
			param.put("usuario", "");
			param.put("idPieza", result.getPiezas());
			param.put("cpedido", result.getCpedido());
			param.put("ppedido", result.getCp());
			param.put("folioDS", folio);
			param.put("compra", result.getFee());
			System.out.println(sbQuery.toString());
			System.out.println("=========================");
			jdbcTemplate.update(sbQuery.toString(), param);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ProquifaNetException();
			
		}
	}
}
