/**
 * 
 */
package com.proquifa.net.negocio.compras.impl;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proquifa.net.modelo.compras.Compra;
import com.proquifa.net.modelo.compras.ResumenCompra;
import com.proquifa.net.modelo.compras.ResumenPCompra;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.negocio.compras.CompraService;
import com.proquifa.net.persistencia.compras.CompraDAO;
import com.proquifa.net.persistencia.compras.PartidaCompraDAO;
import com.proquifa.net.persistencia.compras.impl.CompraDAOImpl;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;



/**
 * @author amartinez
 *x
 */
@Service("compraService")
public class CompraServiceImpl implements CompraService {
	@Autowired
	CompraDAO compraDAO;
	@Autowired
	PartidaCompraDAO partidaCompraDAO;
	
	Funcion funcion = new Funcion();

	public static final Logger LOGGER = LoggerFactory.getLogger(CompraServiceImpl.class);
	
	/* (non-Javadoc)
	 * @see mx.com.proquifa.proquifanet.negocio.compras.CompraService#obtenerComprasConfirmadasXProveedor(java.lang.Long, java.lang.Long, java.lang.String, java.lang.Integer, java.lang.Long)
	 */
	public List<Compra> obtenerReporteCompra(Date FechaInicio, Date FechaFin,
			Long proveedor, Integer estado, Integer coloco, String ordenCompra)
					throws ProquifaNetException {
		Funcion funcion = new Funcion();
		try{
			String periodo = new String();
			String proveedorS = new String();
			String estadoS = new String();
			String colocoS = new String();
			String ordenCompraS = new String();
			String vAND = new String();
			SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyyMMdd");
			if(FechaInicio != null){
				periodo = " Compras.Fecha >= '"+formatoDeFecha.format(FechaInicio)+" 00:00' ";
				vAND = " AND ";
			}
			if(FechaInicio != null){
				periodo = periodo + vAND + " Compras.Fecha <= '"+formatoDeFecha.format(FechaFin)+" 23:59' ";
				vAND = " AND ";
			}
			if(proveedor > 0){
				proveedorS = vAND + " Proveedores.Clave = " + proveedor.toString();
				vAND = " AND ";
			}
			switch(estado){
			case 1 :	estadoS = "";
			break;
			case 2 :	estadoS = vAND + " tblPcompras.cuantos is NULL ";
			vAND = " AND ";
			break;
			case 3 :	estadoS = vAND + " tblPcompras.cuantos > 0";
			vAND = " AND ";
			break;
			}
			switch(coloco){
			case 1 :	colocoS = "";
			break;
			case 2 :	colocoS = vAND + " Compras.ColocarDesde = 'Ciudad de México' ";
			vAND = " AND ";
			break;
			case 3 :	colocoS = vAND + " Compras.ColocarDesde = 'Laredo'";
			vAND = " AND ";
			break;
			case 4 :	colocoS = vAND + " Compras.ColocarDesde = ''";
			vAND = " AND ";
			break;
			}
			if(!ordenCompra.equals("")){
				ordenCompraS = vAND + " Compras.clave like '%" + ordenCompra + "%'";
			}
			List<Compra> compras = this.compraDAO.obtenerReporteCompra(periodo, proveedorS, estadoS, colocoS, ordenCompraS);
			return compras;
		}catch(Exception e){
			//logger.error(e.getMessage());
			funcion.enviarCorreoAvisoExepcion(e, "FechaInicio" + FechaInicio, "FechaFin" +  FechaFin,
			"proveedor" +  proveedor, "estado" +  estado, "coloco" +  coloco, "ordenCompra" +  ordenCompra);
			return null;
		}
	}
	
	
	@Override
	public String validarHorarioCliente(String idPCompra) throws ProquifaNetException {
		try {
			return compraDAO.validarHorarioCliente(idPCompra);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	
	@Override
	public List<String> obtenerAsuetos() throws ProquifaNetException {
		try {
			return compraDAO.obtenerAsuetos();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Override
	public String obtenerDiaFinMes(String idCliente) throws ProquifaNetException {
		try {
			return compraDAO.obtenerDiaFinMes(idCliente);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean generarDocumentoOC(String folioOC) throws ProquifaNetException {

		ResumenCompra rc = new ResumenCompra();

		try {

			rc = compraDAO.getResumenCompra(folioOC);
			int idProveedor = rc.getIdProveedor();

			if (rc != null && rc.getCompra() != null) {

				List<ResumenPCompra> rpc = new ArrayList<ResumenPCompra>();

				for (ResumenPCompra item : rc.getResumenPCompra()) {
					rpc.add(item);
				}

				boolean ingles = rc.isEsIngles();

				String reporteAux = "ROC.jrxml";

				String ruta = funcion.obtenerRutaServidor("jasperordenescompra", "");
				String rutaguardar = funcion.obtenerRutaServidor("rutapdfordenescompra", "");
				InputStream inputStream = null;
				JasperPrint jasperPrint = null;
				boolean esUsoInterno = false;

				inputStream = new FileInputStream(ruta + reporteAux);
				JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
				JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("esUsoInterno", esUsoInterno);
				parametros.put("orden", rc.getCompra());
				parametros.put("ruta", ruta);
				parametros.put("compra", rc.getCompra());
				parametros.put("nombreP", rc.getNombreP());
				parametros.put("idProveedor", rc.getIdProveedor());
				parametros.put("num_cliente", rc.getNum_cliente());
				parametros.put("nombreR", rc.getNombreR());
				parametros.put("fechaR", rc.getFechaR());
				parametros.put("contacto", rc.getContacto());
				parametros.put("fax", rc.getFax());
				parametros.put("TEL", rc.getTEL());
				parametros.put("email", rc.getEmail());
				parametros.put("moneda", rc.getMoneda());
				parametros.put("simboloMoneda", rc.getSimboloMoneda());
				parametros.put("direccion", rc.getDireccion());
				parametros.put("SUBTOTAL", rc.getSUBTOTAL());
				parametros.put("TOTAL", rc.getTOTAL());
				parametros.put("IVA", rc.getIVA());
				parametros.put("facturara", rc.getFacturara());
				parametros.put("Dirfactu", rc.getDirfactu());
				parametros.put("empresa", rc.getEmpresa());
				parametros.put("rSocialEmpresa", rc.getrSocialEmpresa());
				parametros.put("simboloMoneda", rc.getSimboloMoneda());
				parametros.put("listData", rpc);
				parametros.put("Ingles", ingles);
				parametros.put("empresaCondicion", rc.getAliasFacturarA());

				File theDir = new File(rutaguardar);

				// if the directory does not exist, create it
				if (!theDir.exists()) {
					LOGGER.info("Creando directorio..." + rutaguardar);
					boolean result = false;

					try {
						theDir.mkdir();
						result = true;
					} catch (SecurityException se) {
						// handle it
					}
					if (result) {
						LOGGER.info("Directorio creado: " + rutaguardar);
					}
				}

				jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());
				JasperExportManager.exportReportToPdfFile(jasperPrint, rutaguardar + folioOC + "-P.pdf");

				// Genera documento para USP, DISFARMA y MICROBIOLOGICS
				if (idProveedor == 44 || idProveedor == 1954603426 || idProveedor == 28) {
					String rutacsv = rutaguardar + folioOC + ".csv";
					File archivo = new File(rutacsv);
					BufferedWriter bw;
					if (!archivo.exists()) {
						bw = new BufferedWriter(new FileWriter(archivo));
						String contenido = "";
						for (ResumenPCompra x : rpc) {
							String[] temp = x.getCodigo().split(" ");
							if (idProveedor == 28) {// El csv para Microbiologics lleva la columna de Decripción del
													// producto
								contenido += "\"" + x.getCodigoProducto() + "\"," + x.getCantidad() + ",\""
										+ x.getCodigo() + "\"\n";
							} else {
								contenido += temp[0] + "," + x.getCantidad() + "\n";
							}
						}
						bw.write(contenido);
						bw.close();
					}
				}

			} else {
				LOGGER.error("NO EXISTE LA OC");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
}