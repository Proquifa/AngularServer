package com.proquifa.net.persistencia.despachos.mensajero.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.comun.Contacto;
import com.proquifa.net.modelo.comun.Horario;
import com.proquifa.net.modelo.despachos.mensajero.PendientesMensajero;

public class PendientesMensajeroRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		//PEN.Folio, TEvento, CLI.Clave, Nombre, RUTA.Zona, Prioridad, Direccion, RUTA.Mapa, folioEvento, OrdenPlan,  
		//ComentariosAdicionales, Diario,DiaDe1, DiaA1, DiaDe2, DiaA2, Lunes, LuDe1, LuA1, LuDe2, LuA2, Martes, MaDe1, 
		//MaA1, MaDe2, MaA2, Miercoles,  MiDe1, MiA1, MiDe2, MiA2, Jueves, JuDe1, JuA1, JuDe2, JuA2, Viernes, ViDe1, ViA1, ViDe2, ViA2
		
		PendientesMensajero mensajero = new PendientesMensajero();
		Horario horario = new Horario();
		Double RutaAltitud=0.0,RutaLatitud=0.0,RutaLongitud=0.0;

		mensajero.setFolio(rs.getString("Folio"));
		mensajero.setEvento(rs.getString("TEvento"));
		mensajero.setIdCliente(rs.getLong("Clave"));
		mensajero.setIdProveedor(rs.getLong("idProveedor"));
		mensajero.setEmpresa(rs.getString("Nombre"));
		mensajero.setRuta(rs.getString("ZonaMensajeria"));
		mensajero.setEstadoEntrega("Pendiente");
		mensajero.setRealizado(false);
		mensajero.setEstadoPendiente("Abierto");
		if(rs.getString("Prioridad")!=null){
			mensajero.setPrioridad(rs.getString("Prioridad"));			
		}else{
			mensajero.setPrioridad("Normal");
		}
		mensajero.setDireccion(rs.getString("Direccion"));
		mensajero.setMapa(rs.getString("Mapa"));
		mensajero.setDiferenteDireccion(rs.getBoolean("DifDireccion"));
		mensajero.setFolioEvento(rs.getString("folioEvento"));
		
		mensajero.setIdHorario(rs.getLong("FK01_Direccion"));
		if(rs.getString("idEntrega")!=null){
			mensajero.setFolioProducto(rs.getString("idEntrega"));
		}else{
			mensajero.setFolioProducto("N/A");
		}
//		if(rs.getString("folioPL")!=null){
//			 mensajero.setFoliosPL(rs.getString("folioPL"));
//		}else{
//			mensajero.setFolioProducto("N/A");
//		}
		if(rs.getString("folioEvento").contains("DP-")){
			mensajero.setFolioDocumento(rs.getString("folioEvento")+"-D");
		}else{
			mensajero.setFolioDocumento("N/A");
		}		
		mensajero.setOrdenPlan(rs.getInt("OrdenPlan"));
		mensajero.setComentarios(rs.getString("ComentariosAdicionales"));
		
		RutaLatitud = rs.getDouble("RLatitud");
		if(rs.wasNull()){
			mensajero.setLatitud(null);
		}else{
			if(RutaLatitud==0){
				mensajero.setLatitud(rs.getDouble("Latitud"));
			}else{
				mensajero.setLatitud(RutaLatitud);
			}
		}
		
		RutaLongitud=rs.getDouble("RLongitud");
		if(rs.wasNull()){
			mensajero.setLongitud(null);
		}else{
			if(RutaLongitud==0){
				mensajero.setLongitud(rs.getDouble("Longitud"));
			}else{
				mensajero.setLongitud(RutaLongitud);
			}
		}
		
		RutaAltitud=rs.getDouble("RAltitud");
		if(rs.wasNull()){
			mensajero.setAltitud(null);
		}else{
			if(RutaLongitud==0){
				mensajero.setAltitud(rs.getDouble("Altitud"));
			}else{
				mensajero.setAltitud(RutaAltitud);
			}
		}

		horario.setDiario(rs.getBoolean("Diario"));
		horario.setDiaA1(rs.getString("DiaA1"));
		horario.setDiaDe1(rs.getString("DiaDe1"));
		horario.setDiaA2(rs.getString("DiaA2"));
		horario.setDiaDe2(rs.getString("DiaDe2"));
		horario.setLunes(rs.getBoolean("Lunes"));
		horario.setLuDe1(rs.getString("LuDe1"));
		horario.setLuA1(rs.getString("LuA1"));
		horario.setLuDe2(rs.getString("LuDe2"));
		horario.setLuA2(rs.getString("LuA2"));
		horario.setMartes(rs.getBoolean("Martes"));
		horario.setMaA1(rs.getString("MaA1"));
		horario.setMaA2(rs.getString("MaA2"));
		horario.setMaDe1(rs.getString("MaDe1"));
		horario.setMaDe2(rs.getString("MaDe2"));
		horario.setMiercoles(rs.getBoolean("Miercoles"));
		horario.setMiA1(rs.getString("MiA1"));
		horario.setMiA2(rs.getString("MiA2"));
		horario.setMiDe1(rs.getString("MiDe1"));
		horario.setMiDe2(rs.getString("MiDe2"));
		horario.setJueves(rs.getBoolean("Jueves"));
		horario.setJuA1(rs.getString("JuA1"));
		horario.setJuA2(rs.getString("JuA2"));
		horario.setJuDe1(rs.getString("JuDe1"));
		horario.setJuDe2(rs.getString("JuDe2"));
		horario.setViernes(rs.getBoolean("Viernes"));
		horario.setViA1(rs.getString("ViA1"));
		horario.setViA2(rs.getString("ViA2"));
		horario.setViDe1(rs.getString("ViDe1"));
		horario.setViDe2(rs.getString("ViDe2"));
		
		Contacto co1= new Contacto();
		Contacto co2= new Contacto();
		List<Contacto> ListaContactos= new ArrayList<Contacto>();
		int check = 0;
		if(rs.getString("Contacto1")!=null && rs.getString("Contacto1")!="" ){
			co1.setNombre(rs.getString("Contacto1"));
			check=1;
		} 
		if(rs.getString("Titulo1")!=null && rs.getString("Titulo1")!=""){
			co1.setTitulo(rs.getString("Titulo1"));
			check=1;
		} 
		if(rs.getString("Puesto1")!=null && rs.getString("Puesto1")!=""){
			co1.setPuesto(rs.getString("Puesto1"));
			check=1;
		} 
		if(rs.getString("Depto1")!=null && rs.getString("Depto1")!=""){
			co1.setDepartamento(rs.getString("Depto1"));
			check=1;
		}		
		if(rs.getString("Tel1")!=null && rs.getString("Tel1")!=""){
			co1.setTelefono(rs.getString("Tel1"));
			check=1;
		} 
		
		if(rs.getString("Contacto2")!=null && rs.getString("Contacto2")!="" ){
			co2.setNombre(rs.getString("Contacto2"));
			check=1;
		} 
		if(rs.getString("Titulo2")!=null && rs.getString("Titulo2")!=""){
			co2.setTitulo(rs.getString("Titulo2"));
			check=1;
		} 
		if(rs.getString("Puesto2")!=null && rs.getString("Puesto2")!=""){
			co2.setPuesto(rs.getString("Puesto2"));
			check=1;
		} 
		if(rs.getString("Depto2")!=null && rs.getString("Depto2")!=""){
			co2.setDepartamento(rs.getString("Depto2"));
			check=1;
		} 
		if(rs.getString("Tel2")!=null && rs.getString("Tel2")!=""){
			co2.setTelefono(rs.getString("Tel2"));
			check=1;
		} 
		if (check==1){
			ListaContactos.add(co1);
			ListaContactos.add(co2);		
			mensajero.setContactos(ListaContactos);
		}else{
			mensajero.setContactos(null);
		}
		mensajero.setHorario(horario);
		return mensajero;
	}

}
