package com.proquifa.net.persistencia.consultas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.TiempoProceso;

import org.springframework.jdbc.core.RowMapper;

public class ConsultaHistorialPhsMatrizRowMapper implements RowMapper {

 @Override
 public Object mapRow(ResultSet rs, int arg1) throws SQLException {
  
  TiempoProceso tp = new TiempoProceso();
 
  tp.setEtapa(rs.getString("ETAPA"));
  tp.setNivel(rs.getInt("NIVEL"));
  tp.setId(rs.getInt("ID"));
  tp.setPadre(rs.getInt("PADRE"));
  tp.setFechaInicio(rs.getTimestamp("Fecha"));
  tp.setOrigen(rs.getString("Origen"));
  tp.setResponsable(rs.getString("Gestor"));
  tp.setComentarios(rs.getString("Comentarios"));
  tp.setTipo(rs.getString("Tipo"));
  tp.setCommentRevision(rs.getString("Razones"));
  tp.setFPhsUSA(rs.getDate("FPhsUSA"));
  tp.setFPhsUSAAnterio(rs.getDate("FPhsUSAAnterior"));
  tp.setFMatriz(rs.getDate("FMatriz"));
  tp.setFMatrizAnterior(rs.getDate("FMatrizAnterior"));
  tp.setFolio(rs.getString("idNotificado"));
  return tp;
 }

}
