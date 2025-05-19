/**
 * 
 */
package com.proquifa.net.negocio.compras.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proquifa.net.modelo.compras.RegistroConfirmacion;
import com.proquifa.net.modelo.compras.SaldoAFavor;
import com.proquifa.net.modelo.comun.Complemento;
import com.proquifa.net.modelo.comun.Empresa;
import com.proquifa.net.modelo.comun.Folio;
import com.proquifa.net.modelo.comun.Producto;
import com.proquifa.net.modelo.comun.Proveedor;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.negocio.compras.SaldoAFavorService;
import com.proquifa.net.persistencia.compras.SaldoAFavorDAO;
import com.proquifa.net.persistencia.comun.EmpresaDAO;
import com.proquifa.net.persistencia.comun.FolioDAO;
import com.proquifa.net.persistencia.comun.ProveedorDAO;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 * @author ymendez
 *
 */
@Service("saldoAFavorService")
public class SaldoAFavorServiceImpl implements SaldoAFavorService {

	@Autowired
	SaldoAFavorDAO saldoAFavorDAO;
	
	@Autowired
	ProveedorDAO proveedorDAO;
	
	@Autowired
	EmpresaDAO empresaDAO;
	
	@Autowired
	FolioDAO folioDAO;
	
	@Override
	public String generarSaldo(SaldoAFavor saldo) throws ProquifaNetException {
		try {
			Folio folio = folioDAO.obtenerFolioPorConcepto("SaldoAFavor", true);
			saldo.setFolioDocto(folio.getFolioCompleto());
			return saldoAFavorDAO.generarSaldo(saldo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	@Override
	public List<Proveedor> obtenerProveedores(Integer habilitado) throws ProquifaNetException {
		try {
			return proveedorDAO.getProveedores(habilitado);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public List<Empresa> obtenerEmpresas(Integer compra) throws ProquifaNetException {
		try {
			return empresaDAO.getEmpresa(compra);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public Map<String, List<SaldoAFavor>> obtenerListaSaldo(String tipo) throws ProquifaNetException {
		try {
			return saldoAFavorDAO.obtenerListaSaldo(tipo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, List<SaldoAFavor>> mapReturn = new HashMap<String, List<SaldoAFavor>>();
		mapReturn.put("TODAS", new ArrayList<SaldoAFavor>());
		return mapReturn;
	}
	
	
	@Override
	public boolean habilitarSaldo(SaldoAFavor saldo) throws ProquifaNetException {
		try {
			return saldoAFavorDAO.habilitarSaldo(saldo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	
	@Override
	public void crearPdfAvisoDeCambios(List<RegistroConfirmacion> info) throws ProquifaNetException{

		try {
			Funcion f = new Funcion();
			String rutaPlantilla = f.obtenerRutaServidor("plantillaAvisoDeCambios", "");
			String rutaAvisoDeCambios = f.obtenerRutaServidor("avisoDeCambios", "");

			InputStream inputStream = new FileInputStream(rutaPlantilla+"/plantillaAvisoDeCambios.jrxml");
			JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			Map<String, Object> parametros = new HashMap<String, Object>();


			System.out.println("Recorre cada item de la lista info...");

			int cont=0;
			for(RegistroConfirmacion r : info){
				cont++;

				System.out.println("\n---------------------------------------------------------------\n");
				System.out.println("idProducto: "+r.getIdProducto());
				System.out.println("\n---------------------------------------------------------------\n");
				System.out.println("idComplemento: "+r.getIdComplemento());

				if(r.getIdProducto() != null && r.getIdProducto() != 0){
					Producto p = new Producto();
					p = saldoAFavorDAO.obtenerDescProducto(r.getIdProducto());
					System.out.println("Descripcion del producto: "+p.getDescripcion());
					r.setDescripcionProducto(p.getDescripcion());
				}
				else{
					Complemento c = new Complemento();
					c = saldoAFavorDAO.obtenerDescComplemento(r.getIdComplemento());
					System.out.println("Descripcion del producto: "+c.getDescripcion());
					r.setDescripcionProducto(c.getDescripcion());
				}

				System.out.println("Descripcion del producto seteado en la lista: "+r.getDescripcionProducto());
				parametros.put("cont", cont);
				parametros.put("totalPaginas", cont);

			}

			parametros.put("empresaCondicion", info.get(0).getFporCliente().toLowerCase());
			parametros.put("ruta", rutaPlantilla);
			parametros.put("esac", info.get(0).getNombreVendedor());
			//		parametros.put("email", registrarCompraDAO.obtenerUsuario(info.get(0).getNombreVendedor()).toLowerCase());
				parametros.put("ext", info.get(0).getExt());
			parametros.put("notificado",info.get(0).getFolioNT());
			parametros.put("cliente", info.get(0).getCliente());
			parametros.put("cpedido", info.get(0).getCpedido());
			parametros.put("contacto", info.get(0).getContacto().getNombre());


			String folderDate = "";
			File theDir = new File(rutaAvisoDeCambios + folderDate);

			// if the directory does not exist, create it
			if (!theDir.exists()) {
				System.out.println("creating directory: " + rutaAvisoDeCambios);
				boolean result = false;

				try{
					theDir.mkdirs();
					result = true;
				} 
				catch(SecurityException se){
					//handle it
				}        
				if(result) {    
					System.out.println("DIR created");  
				}
			}

			JRDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(info, true);

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, beanCollectionDataSource);
			JasperExportManager.exportReportToPdfFile(jasperPrint, rutaAvisoDeCambios + folderDate +info.get(0).getFolioNT()+".pdf");//ruta destino
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	
}
