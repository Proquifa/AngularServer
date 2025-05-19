/**
 * 
 */
package com.proquifa.net.negocio.consultas.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proquifa.net.modelo.cobrosypagos.facturista.Facturacion;
import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.comun.TiempoProceso;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.consultas.comun.ArchivosFacturaCliente;
import com.proquifa.net.negocio.consultas.ConsultaFacturacionService;
import com.proquifa.net.persistencia.cobrosypagos.proforma.ProformaDAO;
import com.proquifa.net.persistencia.comun.EmpleadoDAO;
import com.proquifa.net.persistencia.consultas.ConsultaCobrosDAO;
import com.proquifa.net.persistencia.consultas.ConsultaFacturacionDAO;

import utils.ZipDirectory;

/**
 * @author vromero
 *
 */
@Service("consultaFacturacionService")
public class ConsultaFacturacionServiceImpl implements ConsultaFacturacionService {

	@Autowired
	ConsultaFacturacionDAO facturacionDAO;
	@Autowired
	ConsultaCobrosDAO cobrosDAO;
	@Autowired
	EmpleadoDAO empleadoDAO;
	@Autowired
	ProformaDAO proformaDAO;

	private Funcion funcion;
	
	final Logger log = LoggerFactory.getLogger(ConsultaFacturacionServiceImpl.class);

	public List<Facturacion> obtenerFacturacion(Date finicio, Date ffin, Long idCliente, String quienFactura,
			String estado, String tipo, String medio, String factura) throws ProquifaNetException {

		log.info("Pasa por aqui 1");
		String condiciones = "";
		String vWhere = " WHERE ";
		String vAnd = "";

		if (finicio != null && ffin != null) {
			funcion = new Funcion();
			condiciones += vWhere + " F.Fecha >= " + funcion.convertirDosFechasAString(finicio, ffin, "F.Fecha");
			vWhere = "";
			vAnd = " AND ";
		}

		if (idCliente > 0) {
			condiciones += vWhere + vAnd + " F.Cliente =" + idCliente;
			vWhere = "";
			vAnd = " AND ";
		}

		if (!quienFactura.equals("")) {

			condiciones += vWhere + vAnd + " F.FPor = '" + quienFactura.replaceAll(" ", "") + "'";
			vWhere = "";
			vAnd = " AND ";
		}

		if (!estado.equals("")) {
			condiciones += vWhere + vAnd + " F.Estado = '" + estado + "'";
			vWhere = "";
			vAnd = " AND ";
		}

		if (!tipo.equals("")) {
			condiciones += vWhere + vAnd + " F.Tipo = '" + tipo + "'";
			vWhere = "";
			vAnd = " AND ";
		}

		if (!medio.equals("")) {
			condiciones += vWhere + vAnd + " F.Medio = '" + medio + "'";
			vWhere = "";
			vAnd = " AND ";
		}

		if (!factura.equals("")) {
			condiciones += vWhere + vAnd + " F.Factura like '%" + factura + "%'";
			vWhere = "";
			vAnd = " AND ";
		}

		// Empleado e = empleadoDAO.obtenerEmpleadoPorId(idUsuarioLogueado);
		// if(e.getIdFuncion() == 40){
		// condiciones += " AND CLI.Cobrador = " + idUsuarioLogueado;
		// }else if(e.getIdFuncion() == 37){
		// List<String> equipo = empleadoDAO.finEquipoESAC(idUsuarioLogueado);
		// condiciones += "AND CLI.Vendedor in (" ;
		// for(String eq : equipo){
		// condiciones += "'" + eq + "',";
		// }
		// condiciones += "'" + e.getUsuario() + "')" ;
		// }else if(e.getIdFuncion() == 5){
		// condiciones += " AND CLI.Vendedor = '" + e.getUsuario() + "'";
		// }
		//
		// if(cobrador > 0){
		// e = empleadoDAO.obtenerEmpleadoPorId(cobrador);
		// condiciones += " AND CLI.Cobrador = '" + cobrador + "'";
		// }

		return this.facturacionDAO.findConsultaFacturacion(condiciones);
	}

	public List<Facturacion> consultaAvanzadaFacturacion(Date finicio, Date ffin, String cliente, String estado,
			String refacturada, String facturo, String tipo, String medio, String cPago, Long idUsuarioLogueado,
			Long cobrador) throws ProquifaNetException {
		log.info("Pasa por aqui 2");
		String condiciones = "";

		Empleado e = empleadoDAO.obtenerEmpleadoPorId(idUsuarioLogueado);
		if (e.getIdFuncion() == 40) {
			condiciones += " AND c.Cobrador = " + idUsuarioLogueado;
		} else if (e.getIdFuncion() == 37) {
			List<String> equipo = empleadoDAO.finEquipoESAC(idUsuarioLogueado);
			condiciones += "AND c.Vendedor in (";
			for (String eq : equipo) {
				condiciones += "'" + eq + "',";
			}
			condiciones += "'" + e.getUsuario() + "')";
		} else if (e.getIdFuncion() == 5) {
			condiciones += " AND c.Vendedor = '" + e.getUsuario() + "'";
		}

		if (cobrador > 0) {
			e = empleadoDAO.obtenerEmpleadoPorId(cobrador);
			condiciones += " AND c.Cobrador = '" + cobrador + "'";
		}

		return this.facturacionDAO.consultaAvanzadaFacturacion(null, null, null, finicio, ffin, cliente, estado,
				refacturada, facturo, tipo, medio, cPago, "Avanzada", condiciones);
	}

	public List<Facturacion> consultaRapidaFacturacion(String factura, String cPedido, String uuid, String fpor,
			Long idUsuarioLogueado) throws ProquifaNetException {
		String condiciones = "";
		Empleado e = empleadoDAO.obtenerEmpleadoPorId(idUsuarioLogueado);

		if (e.getIdFuncion() == 40) {
			condiciones += " AND c.Cobrador = " + idUsuarioLogueado;
		} else if (e.getIdFuncion() == 37) {
			List<String> equipo = empleadoDAO.finEquipoESAC(idUsuarioLogueado);
			condiciones += "AND c.Vendedor in (";
			for (String eq : equipo) {
				condiciones += "'" + eq + "',";
			}
			condiciones += "'" + e.getUsuario() + "')";
		} else if (e.getIdFuncion() == 5) {
			condiciones += " AND c.Vendedor = '" + e.getUsuario() + "'";
		}
		return this.facturacionDAO.consultaAvanzadaFacturacion(factura, cPedido, uuid, null, null, null, null, null,
				fpor, null, null, null, "rapida", condiciones);
	}

	public List<TiempoProceso> obtenerResumen(String factura, String fpor) throws ProquifaNetException {

		return this.facturacionDAO.findLineaTiempoResumen(factura, fpor);
	}

	public List<TiempoProceso> obtenerResumenEntrega(String factura, String fpor) throws ProquifaNetException {
		try {
			List<TiempoProceso> lista = this.facturacionDAO.findInspectorEntrega(factura, fpor);
			Date fecha = new Date("2000/01/01");
			for (TiempoProceso tiempoProceso : lista) {
				if (!tiempoProceso.getEtapa().equals("CERRAR RUTA")) {
					if (tiempoProceso.getFechaFin() != null) {
						if (tiempoProceso.getFechaFin().after(fecha)) {
							fecha = tiempoProceso.getFechaFin();
						}
					}
				}
			}

			for (TiempoProceso tiempoProceso : lista) {
				if (tiempoProceso.getEtapa().equals("CERRAR RUTA")) {
					tiempoProceso.setFechaInicio(fecha);
				}
			}
			return lista;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<TiempoProceso>();
		}

	}

	public List<TiempoProceso> obtenerResumenRevision(String factura, String fpor) throws ProquifaNetException {
		log.info("Pasa por aqui 6");
		return this.cobrosDAO.findInspectorRevision(factura, fpor);
	}

	public List<TiempoProceso> obtenerResumenCobro(String factura, String fpor, String SC) throws ProquifaNetException {
		log.info("Pasa por aqui 7");
		List<TiempoProceso> cobro = null;
		List<TiempoProceso> cobroTemp = null;

		if (SC.equals("0")) {
			cobro = this.cobrosDAO.findInspectorCobroGral(factura, fpor);
			cobroTemp = this.facturacionDAO.findInspectorCobroProgramacion(factura, fpor);
			for (TiempoProceso r : cobroTemp) {
				cobro.add(r);
			}
			cobroTemp = this.cobrosDAO.findInspectorCobroSolicitud(factura, fpor);
			for (TiempoProceso r : cobroTemp) {
				cobro.add(r);
			}
			cobroTemp = this.cobrosDAO.findInspectorCobroEjecucion(factura, fpor);
			for (TiempoProceso r : cobroTemp) {
				cobro.add(r);
			}
			cobroTemp = this.facturacionDAO.findInspectorCobroMonitoreo(factura, fpor);
			for (TiempoProceso r : cobroTemp) {
				cobro.add(r);
			}
			cobroTemp = this.facturacionDAO.findInspectorSolicitudRecoleccionCheque(factura, fpor);
			for (TiempoProceso r : cobroTemp) {
				cobro.add(r);
			}
			cobroTemp = this.facturacionDAO.findInspectorRecoleccionCheque(factura, fpor);
			for (TiempoProceso r : cobroTemp) {
				cobro.add(r);
			}
			cobroTemp = this.cobrosDAO.findInspectorCobroHistorial(factura, fpor);
			for (TiempoProceso r : cobroTemp) {
				cobro.add(r);
			}
		} else {
			cobro = this.facturacionDAO.findInspectorCobroSC(factura, fpor);
		}
		return cobro;
	}

	public List<Facturacion> obtenerResumenCancelacion(String factura, String fpor) throws ProquifaNetException {
		log.info("Pasa por aqui 8");
		return facturacionDAO.obtenerResumenCancelacion(factura, fpor);
	}

	public List<Facturacion> obtenerResumenFacturaRemision(String factura, String fpor) throws ProquifaNetException {
		log.info("Pasa por aqui 9");
		return facturacionDAO.obtenerResumenFacturaRemision(factura, fpor);
	}

	public List<Facturacion> obtenerResumenRefacturacion(String factura, String fpor) throws ProquifaNetException {
		log.info("Pasa por aqui 10");
		return facturacionDAO.obtenerResumenRefacturacion(factura, fpor);
	}

	@Override
	public List<TiempoProceso> obtenerResumenFactura(String factura, String fpor) throws ProquifaNetException {
		log.info("Pasa por aqui Resumen");
		return facturacionDAO.obtenerResumenFactura(factura, fpor);
	}

	@Override
	public List<Facturacion> obtenerResumenFacturacionXAdelantado(String factura, String fpor)
			throws ProquifaNetException {
		log.info("Pasa por aqui 12");
		return facturacionDAO.obtenerResumenFacturacionXAdelantado(factura, fpor);
	}

	@Override
	public List<TiempoProceso> obtenerLineaTiempoPrepago(String factura, String fpor) throws ProquifaNetException {
		log.info("Pasa por aqui 13");
		String condiciones = "";

		if (factura != null && fpor != null) {
			condiciones += "F.Factura = '" + factura + "' " + " AND F.FPor = '" + fpor + "' ";
		}
		String profor = proformaDAO.findFolioProforma(condiciones);
		return facturacionDAO.obtenerLineaTiempoPrepago(factura, fpor, profor);
	}

	@Override
	public List<Facturacion> obtenerResumenEnvioFactura(String factura, String fpor) throws ProquifaNetException {
		log.info("Pasa por aqui 14");
		return facturacionDAO.obtenerResumenEnvioFactura(factura, fpor);
	}

	@Override
	public List<Facturacion> obtenerResumenMonitoreoCobro(String factura, String fpor) throws ProquifaNetException {
		log.info("Pasa por aqui 15");
		return facturacionDAO.obtenerResumenMonitoreoCobro(factura, fpor);
	}

	@Override
	public List<Facturacion> obtenerResumenFacturaPrepago(String factura, String fpor) {
		log.info("Pasa por aqui 16");
		return facturacionDAO.obtenerResumenFacturaPrepago(factura, fpor);
	}

	@Override
	public String generarZip(ArchivosFacturaCliente[] archivosFacturaCliente, String nombreArchivo) throws Exception {
		String factura = nombreArchivo;
		File facturaDirectory = new File("archivos/" + factura);
		facturaDirectory.mkdir();
		for (ArchivosFacturaCliente afc : archivosFacturaCliente) {
			for (int indexArchivo = 0; indexArchivo < afc.getRutasArchivos().length; indexArchivo++) {
				String clienteDirectory = "archivos/" + factura + "/" + afc.getNombreCliente();
				File directory = new File(clienteDirectory);
				if (!directory.exists()) {
					directory.mkdir();
				}
				URL url = new URL(afc.getRutasArchivos()[indexArchivo]);
				URLConnection urlCon = url.openConnection();
				InputStream is = urlCon.getInputStream();
				File file = new File(clienteDirectory + "/" + afc.getNombresArchivos()[indexArchivo]);
				FileOutputStream fos = new FileOutputStream(file);
				// buffer temporal de lectura.
				byte[] b = new byte[1024];
				int leido = is.read(b);
				while (leido > 0) {
					fos.write(b, 0, leido);
					leido = is.read(b);
				}
				is.close();
				fos.close();
			}
		}

		File directoryToZip = new File("archivos/"+ factura);

		List<File> fileList = new ArrayList<File>();
		ZipDirectory.getAllFiles(directoryToZip, fileList);
		ZipDirectory.writeZipFile(directoryToZip, fileList, factura);
		return factura;
	}

}
