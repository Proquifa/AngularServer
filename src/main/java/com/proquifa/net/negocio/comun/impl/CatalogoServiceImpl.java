/**
 * 
 */
package com.proquifa.net.negocio.comun.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proquifa.net.modelo.comun.CatalogoItem;
import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.comun.Funcion;
import com.proquifa.net.modelo.comun.NominaCatalogo;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.negocio.comun.CatalogoService;
import com.proquifa.net.persistencia.comun.CatalogoDAO;
import com.proquifa.net.persistencia.comun.EmpleadoDAO;
import com.proquifa.net.persistencia.comun.FuncionDAO;


/**
 * @author ernestogonzalezlozada
 *
 */
@Service("catalogoService")
public class CatalogoServiceImpl implements CatalogoService {
	@Autowired
	CatalogoDAO catalogoDAO; 
	@Autowired
	EmpleadoDAO empleadoDAO;
	@Autowired
	FuncionDAO funcionDAO;
	
	com.proquifa.net.modelo.comun.util.Funcion tools = new  com.proquifa.net.modelo.comun.util.Funcion();
	
	/* (non-Javadoc)
	 * @see mx.com.proquifa.proquifanet.negocio.comun.CatalogoService#obtenerUnidades()
	 */
	public List<CatalogoItem> obtenerUnidades(String valorAdicional) throws ProquifaNetException {
		return catalogoDAO.obtenerUnidades(valorAdicional);
	}

	public List<CatalogoItem> obtenerTipoProductos(String valorAdicional) throws ProquifaNetException {
		return this.catalogoDAO.obtenerTipoProductos(valorAdicional);
	}

	public List<CatalogoItem> obtenerFabricantes(String valorAdicional) throws ProquifaNetException {
		List<CatalogoItem> resp = this.catalogoDAO.obtenerFabricantes(valorAdicional);
		return resp;
	}

	public List<CatalogoItem> obtenerClientes(String valorAdicional) throws ProquifaNetException {
		List<CatalogoItem> respClientes = this.catalogoDAO.obtenerClientes(valorAdicional);
		return respClientes;
	}

	public List<CatalogoItem> obtenerProveedores(String valorAdicional) throws ProquifaNetException {
		List<CatalogoItem> respClientes = this.catalogoDAO.obtenerProveedores(valorAdicional);
		return respClientes;
	}
	
	public List<CatalogoItem> obtenerProveedoresPorTipo(String valorAdicional, String tipo) throws ProquifaNetException {
		List<CatalogoItem> respProveedores = this.catalogoDAO.obtenerProveedoresPorTipo(valorAdicional, tipo);
		return respProveedores;
	}

	public List<CatalogoItem> obtenerEmpleados(String valorAdicional) throws ProquifaNetException {
		List<CatalogoItem> resp = this.catalogoDAO.obtenerEmpleados(valorAdicional);
		return resp;
	}

	public List<CatalogoItem> obtenerEmpleadosPorTipo(String tipo, String valorAdicional)
			throws ProquifaNetException {
		List<CatalogoItem> resp = this.catalogoDAO.obtenerEmpleadosPorTipo(tipo, valorAdicional);
		return resp;
	}

	public CatalogoItem obtenerVendedorPorNombreEmpresa(String idCliente)
			throws ProquifaNetException {
		return this.catalogoDAO.obtenerVendedorPorEmpresa(idCliente);
	}

	public List<CatalogoItem> obtenerProductosXIdProveedor(Long idProveedor,
			String valorAdicional) throws ProquifaNetException {
		return this.catalogoDAO.obtenerProductosXIdProveedor(idProveedor, valorAdicional);
	}

	public List<CatalogoItem> obtenerSubprocesos(String valorAdicional) throws ProquifaNetException {
		return this.catalogoDAO.obtenerSubProcesos(valorAdicional);
	}

	@Transactional
	public List<CatalogoItem> obtenerEmpleadosPorNivel(String nivel,
			String valorAdicional) throws ProquifaNetException {
		List<Empleado> empleados = this.empleadoDAO.obtenerEmpleadosPorNivel(nivel,0L);
		CatalogoItem item = null;
		ArrayList<CatalogoItem> items = new ArrayList<CatalogoItem>();
		for(Empleado e : empleados){
			item = new CatalogoItem();
			item.setLlave(e.getIdEmpleado());
			item.setValor(e.getUsuario());
			items.add(item);
		}
		if(valorAdicional != null){
			if(valorAdicional.equals("--TODOS--") || valorAdicional.equals("--NINGUNO--")){
				CatalogoItem ninguno = new CatalogoItem();
				ninguno.setLlave(0L);
				ninguno.setValor(valorAdicional);
				items.add(0, ninguno);
			}
		}
		return items;
	}
	
	public List<CatalogoItem> obtenerEmpleadosPorTipoTablero(String tipo,
			String valorAdicional) throws ProquifaNetException {
		List<CatalogoItem> resp = this.catalogoDAO.obtenerEmpleadosPorTipoTablero(tipo, valorAdicional);
		return resp;
	}
	public List<CatalogoItem> findPais(String valorAdicional) throws ProquifaNetException {
		List<CatalogoItem> paises = this.catalogoDAO.findPais(valorAdicional);
		return paises;
	}

	public List<CatalogoItem> listarRutas(String valorAdicional) throws ProquifaNetException {
		String query = "";
		if(valorAdicional == null || valorAdicional.equals("")){
			query = "";
		}else if(valorAdicional.equals("--TODOS--")){
			query = "SELECT 0 PK_Folio, '--TODOS--' Valor,'Ruta' AS Tipo , '0' AS Cont UNION ";
		}else if(valorAdicional.equals("--TODAS--")){
			query = "SELECT 0 PK_Folio, '--TODAS--' Valor,'Ruta' AS Tipo , '0' AS Cont UNION ";
		}else if(valorAdicional.equals("--NINGUNA--")){
			query = "SELECT 0 PK_Folio, '--NINGUNA--' Valor,'Ruta' AS Tipo , '0' AS Cont UNION ";
		}else if(valorAdicional.equals("--NINGUNO--")){
			query = "SELECT 0 PK_Folio, '--NINGUNO--' Valor,'Ruta' AS Tipo , '0' AS Cont UNION ";
		}
		
		List<CatalogoItem> rutas = this.catalogoDAO.findRutas(query);
		return rutas; 
	}
	
	public List<CatalogoItem>  obtenerClientesEstado(String valorAdicional,String tipo) throws ProquifaNetException {
		List<CatalogoItem> respClientes = this.catalogoDAO.obtenerClientesEstado(valorAdicional, tipo);
		return respClientes;
	}
	
	public List<CatalogoItem>  obtenerCatalogoProductosProveedores(String idProducto) throws ProquifaNetException {
		String condicion="";
		if (idProducto == null || idProducto == ""){
			condicion="";
		}else{
			condicion=" AND PRO.Proveedor= '"+idProducto+"' ";
		}
		List<CatalogoItem> catalogo = this.catalogoDAO.obtenerCatalogoPorProveedor(condicion);
		return catalogo;
	}
	
	public List<CatalogoItem> obtenerCatalogoTiemposEntrega(String valorAdicional) throws ProquifaNetException {
		List<CatalogoItem> catalogo = this.catalogoDAO.obtenerCatalogoTiempoEntrega(valorAdicional);
		return catalogo;
	}

	@Override
	public List<CatalogoItem> obtenerClientesPorIdUsuarioRol(
			Long idUsuarioLogeado) throws ProquifaNetException {
		
		
		String condiciones = "";
		
		Empleado e = empleadoDAO.obtenerEmpleadoPorId(idUsuarioLogeado);
		
		if(e.getIdFuncion() == 5){
		
			condiciones = " AND Vendedor = '" + e.getUsuario()+ "'";
		
		}else if(e.getIdFuncion() == 37){
		
			
			List<String> esacs = empleadoDAO.finEquipoESAC(e.getIdEmpleado());
			
			condiciones = " AND Vendedor in ('" + e.getUsuario() + "'";
			for(String v : esacs){
				condiciones += ",'" + v + "'";
			}
			condiciones += ") "; 
					
		}else if(e.getIdFuncion() == 7){
			
			condiciones = " AND FK01_EV = " + idUsuarioLogeado;
		
		}else if(e.getIdFuncion() == 40){
		
			condiciones = " AND Cobrador = " + idUsuarioLogeado;
		}
		
		
		return this.catalogoDAO.getClientesPorIdUsuarioRol(condiciones);
	}

	@Override
	public List<CatalogoItem> obtenerProveedoresPorIdUsuarioRol(
			Long idUsuarioLogeado) throws ProquifaNetException {

			String condiciones = "";
		
		Empleado e = empleadoDAO.obtenerEmpleadoPorId(idUsuarioLogeado);
		
		if(e.getIdFuncion() == 10){
			
			condiciones = " AND FK01_Empleado = " + idUsuarioLogeado;
		
		}else if(e.getIdFuncion() == 40){
		
			condiciones = " AND Pagador = " + idUsuarioLogeado;
		}
		return this.catalogoDAO.getProveedoresPorIdUsuarioRol(condiciones);
	}

	@Override
	public List<CatalogoItem> obtenerCondicionesDePagoProveedor()
			throws ProquifaNetException {
		
		return this.catalogoDAO.getCondicionesDePagoProveedor();
	}

	@Override
	public List<CatalogoItem> obtenerBancosClientes(String fpor) throws ProquifaNetException {
		
		String condiciones = "";
		
		if(fpor!=null && !fpor.equals("")){
			
			condiciones = "WHERE Beneficiario COLLATE Modern_Spanish_CI_AS = (SELECT Prefijo FROM Empresa WHERE Alias = '"+ fpor +"')";
		}
		if(fpor.equals("--TODOS--")){
			condiciones = "";
		}
		
		return this.catalogoDAO.getBancosClientes(condiciones);
	}
	
	
	@Override
	public List<CatalogoItem> obtenerCuentasBancoClientes(String banco, String fpor) throws ProquifaNetException {
		
		return this.catalogoDAO.getCuentasBancoClientes(banco, fpor);
	}
	
	@Override
	public List<CatalogoItem> obtenerBancos() throws ProquifaNetException {
		
		return this.catalogoDAO.getBancos();
	}
	
	@Override
	public List<CatalogoItem> obtenerCuentas(String banco) throws ProquifaNetException {
		
		return this.catalogoDAO.getCuentas(banco);
	}


	@Override
	public List<CatalogoItem> obtenerCorporativo(String usuario) throws ProquifaNetException {
		return catalogoDAO.getCorporativos(usuario);
	}

	@Override
	public List<CatalogoItem> obtenerIndustrias() throws ProquifaNetException {
		List<CatalogoItem> respIndustrias = this.catalogoDAO.obtenerIndustrias();
		return respIndustrias;
	}
	
	@Override
	public List<CatalogoItem> obtenerEmpresas() throws ProquifaNetException {
		List<CatalogoItem> respEmpresas = this.catalogoDAO.obtenerEmpresas();
		return respEmpresas;
	}
	
	@Override
	public List<CatalogoItem>obtenerEmpresaCompra(Boolean bCompra, String valorAdicional) throws ProquifaNetException{
		List<CatalogoItem> respEmpresas = this.catalogoDAO.obtenerEmpresaCompra(bCompra, valorAdicional);
		return respEmpresas;
	}

	@Override
	public List<CatalogoItem> obtenerClientesDistribuidores(Boolean habilitado,
			Empleado empleado) throws ProquifaNetException {
		String condiciones = " C.Rol = 'DISTRIBUIDOR' ";
		
		
		if(habilitado == true){
			condiciones +=" AND C.Habilitado = 1";
		}else{
			condiciones +=" AND C.Habilitado = 0";
		}
		Funcion funcionEmpleado = funcionDAO.getFuncionPorIdEmpleado(empleado.getIdEmpleado());
		
		if(!funcionEmpleado.getNivel().equals("Directivo") && !funcionEmpleado.getNivel().equals("Gerente")){
			if(funcionEmpleado.getNombre().equals("Ejecutivo de Servicio a Clientes")){				
				condiciones +=" AND C.Vendedor = '" + empleado.getUsuario() + "'";
			}else if(funcionEmpleado.getNombre().equals("Ejecutivo de Servicio a Clientes Master")){
				condiciones += "AND (JS.FK01_Jefe = " + empleado.getIdEmpleado() + "  OR E.Clave = " + empleado.getIdEmpleado() + ")";
			}else if(funcionEmpleado.getNombre().equals("Ejecutivo de Ventas")){
				condiciones +=" AND C.FK01_EV = " + empleado.getIdEmpleado();
			}else if(funcionEmpleado.getNombre().equals("Responsable de cobros y pagos")){
				condiciones +=" AND C.Cobrador =" + empleado.getIdEmpleado();
			}
		}
		 
		return this.catalogoDAO.listClientesDistribuidores(condiciones);
	}
	@Override
	public List<CatalogoItem> obtenerNivelIngresos()
			throws ProquifaNetException {
		List<CatalogoItem> lista = this.catalogoDAO.obtenerNivelIngreso();
		for (CatalogoItem concepto : lista){
			concepto.setValor(tools.convertirPalabrasClaves(concepto.getValor()));  
		}
		return lista; 
	}

	@Override
	public List<CatalogoItem> obtenerAgentesAduanales(String valorAdicional)
			throws ProquifaNetException {
		String condicion = "";
		String condicionDos = "";
		
		if(valorAdicional.equals("--TODOS--")){
			condicion = " SELECT 0 clave,'--TODOS--' nombre, 0 posicion UNION ";
			condicionDos = " ORDER BY posicion,nombre";
		}else if(valorAdicional.equals("--NINGUNO--")){
			condicion = " SELECT 0 clave,'--NINGUNO--' nombre, 0 posicion UNION";
			condicionDos = " ORDER BY posicion,nombre";
		}else if(valorAdicional.equals("hab")){
			condicionDos = " WHERE Habilitado = 1 ORDER BY posicion,nombre";
		}else if(valorAdicional.equals("des")){
			condicionDos = " WHERE Habilitado = 0 ORDER BY posicion,nombre";
		}else if(valorAdicional.equals("")){
			condicionDos = " ORDER BY posicion,nombre";
		}
		return this.catalogoDAO.getAgentesAduanales(condicion,condicionDos);
	}
	
	@Override
	public List<CatalogoItem> obtenerInspectores(String valorAdicional) throws ProquifaNetException{
		String condicion = "";
		if(valorAdicional.equals("--TODOS--")){
			condicion = "\n SELECT 0 idUsuario,'--TODOS--' Usuario, 0 posicion UNION ";			
		}else if(valorAdicional.equals("--NINGUNO--")){
			condicion = "\n SELECT 0 idUsuario,'--NINGUNO--' Usuario, 0 posicion UNION  ";
		}
		return this.catalogoDAO.getInspectores(condicion);
	}

	@Override
	public List<CatalogoItem> obtenerFamilias(Integer idProveedor,Integer idAgenteAduanal, Integer tipo) throws ProquifaNetException {
		try {
			List<CatalogoItem> listaFamilias;
			if (idProveedor > -1) {
				listaFamilias =  this.catalogoDAO.obtenerFamiliasPorProveedor(idProveedor);
			} else if (idAgenteAduanal > -1) {
				listaFamilias =  this.catalogoDAO.obtenerFamiliasPorAgenteAduanal(idAgenteAduanal);
			} else if (tipo > -1) {
				listaFamilias =  this.catalogoDAO.obtenerFamiliasPorTipo(tipo);
			} else {
				listaFamilias =  new ArrayList<CatalogoItem>();
			}
			
			for (int i = 0 ; i < listaFamilias.size() ; i++) {
				
				if (listaFamilias.get(i) == null || listaFamilias.get(i).getValor() == null) {
					listaFamilias.remove(i);
					continue;
				}
				
				String[] familia = listaFamilias.get(i).getValor().split("·");
				
				String tipoS = familia[0].trim();
				String subTipo = familia[1].trim();
				String control = familia[2].trim();
				
				if (subTipo.equals("") && control.equals("")) {
					listaFamilias.get(i).setValor(tipoS);
				}
				
			}
			
			return listaFamilias;
		} catch (Exception e) {
			tools = new  com.proquifa.net.modelo.comun.util.Funcion();
			tools.enviarCorreoAvisoExepcion(e);
			return new ArrayList<CatalogoItem>();
		}
	}
	
	public List<CatalogoItem> obtenerOrigenProductos(String valorAdicional) throws ProquifaNetException {
		try {
			List<CatalogoItem> oridenProductos = new ArrayList<CatalogoItem>();
			
			if (valorAdicional != null && !valorAdicional.equals("") && (valorAdicional.equals("--NINGUNO--") || valorAdicional.equals("--TODOS--"))) {
				CatalogoItem catalogoItem = new CatalogoItem();
					catalogoItem.setValor(valorAdicional);
					catalogoItem.setLlave(0L);
					oridenProductos.add(catalogoItem);
			}
			oridenProductos.addAll(catalogoDAO.getOrigenProductos());
			
			for (CatalogoItem catalogo : oridenProductos) {
				catalogo.setValor(catalogo.getValor().toUpperCase());
			}
			
			return oridenProductos;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public List<CatalogoItem> obtenerPaisesCodPost() throws ProquifaNetException {
		try {
			return catalogoDAO.getPaisesCodPost();
		}catch(Exception e) {
			e.printStackTrace();
			return new ArrayList<CatalogoItem>();
		}
	}
	
	@Override
	public List<CatalogoItem> obtenerEmpresasContabilidad() throws ProquifaNetException {
		try {
			return catalogoDAO.getEmpresasContabilidad();
		}catch(Exception e) {
			e.printStackTrace();
			return new ArrayList<CatalogoItem>();
		}
	}
	
	public NominaCatalogo obtenerNominaCatalogoID(Integer idNominaCatalogo) throws ProquifaNetException {
		try {
			return catalogoDAO.getNominaCatalogoID(idNominaCatalogo);
		}catch(Exception e) {
			e.printStackTrace();
			return new NominaCatalogo();
		}
	}
	
	@Override
	public List<NominaCatalogo> obtenerNominaCatalogo(String tipo) throws ProquifaNetException {
		try {
			return catalogoDAO.getNominaCatalogoTipo(tipo);
		}catch(Exception e) {
			e.printStackTrace();
			return new ArrayList<NominaCatalogo>();
		}
	}
}