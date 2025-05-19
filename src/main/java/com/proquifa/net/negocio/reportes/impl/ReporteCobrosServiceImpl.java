/**
 * 
 */
package com.proquifa.net.negocio.reportes.impl;

import java.util.ArrayList;
import java.util.List;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.cobrosypagos.Cobros;
import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.comun.NivelIngreso;
import com.proquifa.net.negocio.reportes.ReporteCobrosService;
import com.proquifa.net.persistencia.comun.EmpleadoDAO;
import com.proquifa.net.persistencia.comun.NivelIngresoDAO;
import com.proquifa.net.persistencia.reportes.ReporteCobrosDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ymendez
 *
 */
@Service("reporteCobrosService")
public class ReporteCobrosServiceImpl implements ReporteCobrosService{

	@Autowired
	ReporteCobrosDAO reportecobrosDAO;
	@Autowired
	NivelIngresoDAO nivelIngresoDAO;
	@Autowired
	EmpleadoDAO empleadoDAO;
	
	Funcion f ;
	
	public List<Cobros> reporteCobros(String factura, String cpedido, Long idUsuarioLogueado) throws ProquifaNetException {
		try{
		String sbCondicion = " WHERE 1=1 ";
		f = new Funcion();
		
		sbCondicion += " AND F.DeSistema = 1  AND YEAR(COALESCE(PA.FFin,F.Fecha)) > 2009 AND (F.CPedido IS NOT NULL OR F.DeRemision = 1)  \n";
		if (factura != null && !factura.equals("")){
			sbCondicion +=" AND F.Factura = '"+factura+"' \n";
		}
		else if (cpedido != null && cpedido.equals("")){
			sbCondicion +=" AND P.Tipo='Monitorear cobro' AND P.FFin IS NULL ";
		}
		else{
			sbCondicion +=" AND F.Cpedido = '" + cpedido + "' \n";
		}
		
		Empleado e = empleadoDAO.obtenerEmpleadoPorId(idUsuarioLogueado);

		if(e.getIdFuncion() == 40){
			sbCondicion += " AND C.Cobrador = " + idUsuarioLogueado;
		}else if(e.getIdFuncion() == 37){
			List<String> equipo = empleadoDAO.finEquipoESAC(idUsuarioLogueado);
			sbCondicion += " AND C.Vendedor in (" ;
			for(String eq : equipo){
				sbCondicion += "'" + eq + "',";
			}
			sbCondicion += "'" + e.getUsuario() + "')" ;
		}else if(e.getIdFuncion() == 5){
			sbCondicion += " AND C.Vendedor = '" + e.getUsuario() + "'";
		}
		
		sbCondicion += " AND " + f.obtenerEmpresasProquifa("F.Cliente");
		
		List<NivelIngreso> niveles = nivelIngresoDAO.findLimitesNivelIngreso();
		
		return this.reportecobrosDAO.findCobros(sbCondicion,niveles, idUsuarioLogueado);
		}
		catch(Exception e){
			f.enviarCorreoAvisoExepcion(e, factura,cpedido,"idusuariologueado"+idUsuarioLogueado);
			return new ArrayList<Cobros>();
		}
	}

}
