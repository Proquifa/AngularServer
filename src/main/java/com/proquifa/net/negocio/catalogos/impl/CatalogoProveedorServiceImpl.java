/**
 * 
 */
package com.proquifa.net.negocio.catalogos.impl;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.proquifa.net.modelo.catalogos.FormulaPrecio;
import com.proquifa.net.modelo.catalogos.MedioPago;
import com.proquifa.net.modelo.catalogos.proveedores.ClasificacionConfiguracionPrecio;
import com.proquifa.net.modelo.catalogos.proveedores.ConfiguracionPrecio;
import com.proquifa.net.modelo.catalogos.proveedores.ConfiguracionPrecioProducto;
import com.proquifa.net.modelo.catalogos.proveedores.CostoFactor;
import com.proquifa.net.modelo.catalogos.proveedores.Flete;
import com.proquifa.net.modelo.catalogos.proveedores.InformacionPagos;
import com.proquifa.net.modelo.catalogos.proveedores.Licencia;
import com.proquifa.net.modelo.catalogos.proveedores.Logistica;
import com.proquifa.net.modelo.catalogos.proveedores.MultiusosValores;
import com.proquifa.net.modelo.catalogos.proveedores.TiempoEntrega;
import com.proquifa.net.modelo.comun.Campana;
import com.proquifa.net.modelo.comun.Direccion;
import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.comun.Empresa;
import com.proquifa.net.modelo.comun.Fabricante;
import com.proquifa.net.modelo.comun.Horario;
import com.proquifa.net.modelo.comun.Producto;
import com.proquifa.net.modelo.comun.Proveedor;
import com.proquifa.net.modelo.comun.Referencia;
import com.proquifa.net.modelo.comun.RutaFlete;
import com.proquifa.net.modelo.comun.ValorAdicional;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.negocio.catalogos.CatalogoProveedorService;
import com.proquifa.net.persistencia.catalogos.CatalogoClientesDAO;
import com.proquifa.net.persistencia.catalogos.CatalogoProductoDAO;
import com.proquifa.net.persistencia.catalogos.CatalogoProveedorDAO;
import com.proquifa.net.persistencia.comun.DireccionDAO;
import com.proquifa.net.persistencia.comun.EmpleadoDAO;
import com.proquifa.net.persistencia.comun.FabricanteDAO;
import com.proquifa.net.persistencia.comun.ObjetivoCrecimientoDAO;
import com.proquifa.net.persistencia.comun.ProductoDAO;

/**
 * @author orosales
 *
 */
@Service("catalogoProveedorService")
public class CatalogoProveedorServiceImpl implements CatalogoProveedorService {

	final Logger log = LoggerFactory.getLogger(CatalogoProveedorServiceImpl.class);
	
	@Autowired
	EmpleadoDAO empleadoDAO;
	@Autowired
	CatalogoProveedorDAO catalogoProveedorDAO;
	@Autowired
	ProductoDAO productoDAO;
	@Autowired
	FabricanteDAO fabricanteDAO;
	@Autowired
	ObjetivoCrecimientoDAO objetivoDAO;
	@Autowired
	DireccionDAO direccionDAO;
	@Autowired
	CatalogoProductoDAO catalogoProductoDAO;
	@Autowired
	CatalogoClientesDAO catalogoClienteDAO;


	Funcion funcion = new Funcion();


	public Boolean actualizarProveedor(Proveedor infoProveedor)	throws ProquifaNetException {
		try {
			boolean actualizo=false;
			if (infoProveedor.getReferencia() != null ){
			}
			// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			Proveedor proveOriginal = obtenerInformacionGeneralProveedor(infoProveedor.getIdProveedor());
			// SE VERIFICA SI EL PAGADOR QUE SE RECIBE ES DIFERENTE AL QUE YA SE TENIA
			if (proveOriginal.getPagador().longValue() != infoProveedor.getPagador().longValue())
			{
				// SE OBTIENE EL USUARIO DEL NUEVO PAGADOR
				Empleado empleadoTmp =  empleadoDAO.obtenerEmpleadoPorId(infoProveedor.getPagador());
				String nuevoPagador =  empleadoTmp.getUsuario();
				// SE OBTIENEN TODOS LOS PENDIENTES QUE PUEDEN ESTAR ABIERTOS DEL ANTIGUO PAGADOR
				List<String> pend =  catalogoProveedorDAO.getPendientesRespCobroPraPagador(infoProveedor.getIdProveedor().toString());
				if (pend.size()>0)
				{				
					String valor = pend.toString();
					valor = valor.replace("[", "");
					valor = valor.replace("]", "");
					catalogoProveedorDAO.actualizarResponsablePendienteXfolio(valor, nuevoPagador);
				}
			}

			//SI EL COMPRADOR ES DIFERENTE
			if (proveOriginal.getComprador().longValue() != infoProveedor.getComprador().longValue()){
				Empleado empleadoTmp = empleadoDAO.obtenerEmpleadoPorId(infoProveedor.getComprador());
				String nuevoComprador = empleadoTmp.getUsuario();

				List<String> pendientes = catalogoProveedorDAO.getPendientesRespComprador(infoProveedor.getIdProveedor());
				if (pendientes.size() > 0){
					String valor = pendientes.toString();
					valor = valor.replace("[", "");
					valor = valor.replace("]", "");
					catalogoProveedorDAO.actualizarResponsablePendienteXfolio(valor, nuevoComprador);
				}
				//log.info(pendientes.size());
			}

			//VERIFICA QUE EL INSPECTOR ES DIFERENTE AL QUE TIENE
			if(proveOriginal.getInspector().longValue() != infoProveedor.getInspector().longValue()){
				Empleado empleadoTmp = empleadoDAO.obtenerEmpleadoPorId(infoProveedor.getInspector());
				String nuevoInspector = empleadoTmp.getUsuario();

				//OBTIENE TODOS LOS PENDIENTES ASIGNADOS DEL ANTIGUE INSPECTOR
				List<String> pendientes = catalogoProveedorDAO.getPendientesInspector(infoProveedor.getIdProveedor());
				if (pendientes.size() > 0){
					String valor = pendientes.toString();
					valor = valor.replace("[", "");
					valor = valor.replace("]", "");
					catalogoProveedorDAO.actualizarResponsablePendienteXfolio(valor, nuevoInspector); //REASIGNA LOS PENDIENTES AL NUEVO INSPECTOR
				}			
			}

			// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			

			actualizo = this.catalogoProveedorDAO.actualizarProveedor(infoProveedor);		
			if (actualizo){
				if (infoProveedor.getReferencia() != null ){
					Referencia ref = infoProveedor.getReferencia();
					funcion.copiarArchivo(ref.getBytes(), ref.getNombre() + "." + ref.getExtensionArchivo(), "imagenProveedor");
					infoProveedor.setImagen(infoProveedor.getReferencia().getExtensionArchivo());
				}
				
				Double dObjetivoCrecimiento = 0.0;//objetivoDAO.getObjetivoCrecimientoProveedor(infoProveedor.getIdProveedor()); //Se obtiene el Objetivo Crecimiento antes de que se actualice para evaluar
				Double montoAnualAnterior = this.catalogoProveedorDAO.comprasAnualAnteriorxProveedor(infoProveedor.getIdProveedor());
				infoProveedor.setMontoAnualAnterior(montoAnualAnterior);
				objetivoDAO.insertObjetivosCrecimientoPorProveedor();
				
				//*********			Actualiza o Inserta el Objetivo de Crecimineto del Proveedor
				if(infoProveedor.getObjectivoCrecimiento() != null && !infoProveedor.getObjectivoCrecimiento().isEmpty()){ // Solo Si se mando el Objetivo Crecimiento
					if(dObjetivoCrecimiento == null || (dObjetivoCrecimiento != Double.parseDouble(infoProveedor.getObjectivoCrecimiento()))){ //Si No tiene asignado Objetivo - cambio el Objetivo de Crecimineto al que ya estaba				

						boolean bRes =  false;
						Double objetivoTranscurrido = this.catalogoProveedorDAO.obtenerObjetivoTranscurrido(infoProveedor.getIdProveedor());
						infoProveedor.setObjetivoTranscurrido(objetivoTranscurrido);
						//logger.info("Objetivo % Transcurrido: " + objetivoTranscurrido);

						boolean bExisteObjetivo = this.catalogoProveedorDAO.existeObjetivoAnioActual(infoProveedor.getIdProveedor());

						if (bExisteObjetivo){ //Si existe el objetivo de Crecimiento del Anio Actual, Actualiza
							Double dObjetivoMontoTranscurrido = this.catalogoProveedorDAO.obtenerObjetivoMontoTranscurrido(infoProveedor.getIdProveedor());
							infoProveedor.setObjetivoMontoTranscurrido(dObjetivoMontoTranscurrido);
							//logger.info("Objetivo Monto Transcurrido $: " + dObjetivoMontoTranscurrido);
							bRes =  this.catalogoProveedorDAO.updateObjetivoCreciminetoProveedor(infoProveedor);
						}else{//Si no existe el Crecimiento del a-o actual, lo inserta
							bRes =  this.catalogoProveedorDAO.insertObjetivoCrecimientoProveedor(infoProveedor);
						}				
						return bRes;				
					}
				}
				
//				dObjetivoCrecimiento = objetivoDAO.getObjetivoCrecimientoFundamentalProveedor(infoProveedor.getIdProveedor());
				
				if(infoProveedor.getObjetivoCrecimientoFundamental() != null && !infoProveedor.getObjetivoCrecimientoFundamental().isEmpty()){ // Solo Si se mando el Objetivo Crecimiento
					if(dObjetivoCrecimiento == null || (dObjetivoCrecimiento != Double.parseDouble(infoProveedor.getObjetivoCrecimientoFundamental()))){ //Si No tiene asignado Objetivo - cambio el Objetivo de Crecimineto al que ya estaba				
						
						boolean bRes =  false;
						
						Double objetivoTranscurrido = this.catalogoProveedorDAO.obtenerObjetivoFundamentalTranscurrido(infoProveedor.getIdProveedor());
						infoProveedor.setObjetivoTranscurrido(objetivoTranscurrido);
						boolean bExisteObjetivo = this.catalogoProveedorDAO.existeObjetivoAnioActual(infoProveedor.getIdProveedor());
						
						if (bExisteObjetivo){ //Si existe el objetivo de Crecimiento del Anio Actual, Actualiza
							Double dObjetivoMontoTranscurrido = this.catalogoProveedorDAO.obtenerObjetivoMontoFundamentalTranscurrido(infoProveedor.getIdProveedor());
							infoProveedor.setObjetivoMontoTranscurrido(dObjetivoMontoTranscurrido);
							bRes =  this.catalogoProveedorDAO.updateObjetivoCrecimientoFundamentalProveedor(infoProveedor);
						}else{//Si no existe el Crecimiento del a-o actual, lo inserta
							bRes =  this.catalogoProveedorDAO.insertObjetivoCrecimientoProveedor(infoProveedor);
						}				
						return bRes;				
					}
				}
				//**********

				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			funcion.enviarCorreoAvisoExepcion(e, infoProveedor);
			return false;
		}
	}

	public Long agregarNuevoProveedor(Proveedor nuevoProveedor)	throws ProquifaNetException {
		try {
			Long prov = 0L;

			if (nuevoProveedor.getReferencia() != null ){
				nuevoProveedor.setImagen(nuevoProveedor.getReferencia().getExtensionArchivo());
			}
			
			if(nuevoProveedor.getObjectivoCrecimiento()==null || nuevoProveedor.getObjectivoCrecimiento().equals(""))
			{ nuevoProveedor.setObjectivoCrecimiento(this.objetivoDAO.obtenerObjetivoCrecimientoProveedorXObjetivo("Deseado", "Normal").toString()); }
			
			if(nuevoProveedor.getObjetivoCrecimientoFundamental()==null || nuevoProveedor.getObjetivoCrecimientoFundamental().equals(""))
			{ nuevoProveedor.setObjetivoCrecimientoFundamental(this.objetivoDAO.obtenerObjetivoCrecimientoProveedorXObjetivo("Fundamental", "Normal").toString()); }
			
			prov = this.catalogoProveedorDAO.insertarProveedor(nuevoProveedor);
			this.objetivoDAO.insertObjetivosCrecimientoPorProveedor();
			this.objetivoDAO.updateObjetivosCrecimientoPorProveedor("AND OBC.FK02_Proveedor = "+prov);
			
			if (prov != 0){
				if (nuevoProveedor.getReferencia() != null ){
					Referencia ref = nuevoProveedor.getReferencia();
					funcion.copiarArchivo(ref.getBytes(), ref.getNombre() + "." + ref.getExtensionArchivo(), "imagenProveedor");
				}
			}
			
			return prov;
		} catch (Exception e) {
			funcion.enviarCorreoAvisoExepcion(e, nuevoProveedor);
			return -1L;
		}
	}

	public List<Proveedor> listarProveedores(String parametro) throws ProquifaNetException {
		try {
			return this.catalogoProveedorDAO.findProveedores(parametro);
		} catch (Exception e) {
			funcion.enviarCorreoAvisoExepcion(e, "parametro: " + parametro);
			return new ArrayList<Proveedor>();
		}
	}


	public Proveedor obtenerInformacionGeneralProveedor(Long idProveedor)
			throws ProquifaNetException {
		try {
			return this.catalogoProveedorDAO.getInformacionGeneralProveedor(idProveedor);
		} catch (Exception e) {
			funcion.enviarCorreoAvisoExepcion(e, "idProveedor: " + idProveedor);
			return new Proveedor();
		}
	}

	public Boolean deshabilitarProveedor(Long idProveedor)
			throws ProquifaNetException {
		try {
			return this.catalogoProveedorDAO.deshabilitarProveedor(idProveedor);
		} catch (Exception e) {
			funcion.enviarCorreoAvisoExepcion(e, "idProveedor: " + idProveedor);
			return false;
		}
	}

	public Boolean habilitarProveedor(Long idProveedor,Boolean relacionComercial)
			throws ProquifaNetException {
		try {
			return this.catalogoProveedorDAO.habilitarProveedor(idProveedor,relacionComercial);
		} catch (Exception e) {
			funcion.enviarCorreoAvisoExepcion(e, "idProveedor: " + idProveedor, "relacionComercial: " + relacionComercial);
			return false;
		}
	}

	public Boolean guardarProductosVende(ConfiguracionPrecioProducto productos) throws ProquifaNetException {
		try {
			
			this.catalogoProveedorDAO.insertarConfiguracionProductosArbol(productos, true);
			
			return true;
		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
			funcion.enviarCorreoAvisoExepcion(e, productos);
			return false;
		}
	}

	public List<ConfiguracionPrecioProducto> listarConfiguracionProductosVende(	Long idProveedor) throws ProquifaNetException {
		try {
			log.info("listarConfiguracionProductosVende");
			List<ConfiguracionPrecioProducto> result  = this.catalogoProveedorDAO.findConfiguracionProductosVende(idProveedor);
			return result;
		} catch (Exception e) {
			funcion.enviarCorreoAvisoExepcion(e, "idProveedor: " + idProveedor);
			return new ArrayList<ConfiguracionPrecioProducto>();
		}
	}

	public Boolean eliminarConfiguracionProducto(Long idConfiguracionPrecio) throws ProquifaNetException {
		try {
			Boolean valor=false;
			//			List<MultiusosValores> lista = this.catalogoProveedorDAO.getListaBorrarConfiguracion(idConfiguracionPrecio);
			//			for (MultiusosValores r:lista){
			if(this.catalogoProveedorDAO.eliminarConfiguracionProductoVende(idConfiguracionPrecio)){
				valor=true;
			}else{
				return false;
			}
			//			}
			return valor;
		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
			funcion.enviarCorreoAvisoExepcion(e, "idConfiguracionPrecio: " + idConfiguracionPrecio);
			return false;
		}
	}

	public List<ConfiguracionPrecioProducto> listarConfiguracionOferta(	Long idConfigPrecio) throws ProquifaNetException {
		try {
			List<ConfiguracionPrecioProducto> result  = configurarElementos(this.catalogoProveedorDAO.findConfiguracionOferta(idConfigPrecio), true, true,true);

			return result;
		} catch (Exception e) {
			//logger.info("Error:" + e.getMessage());
			funcion.enviarCorreoAvisoExepcion(e, "idConfigPrecio: " + idConfigPrecio);
			return new ArrayList<ConfiguracionPrecioProducto>();
		}
	}

	private List<ConfiguracionPrecioProducto> configurarElementos(
			List<ConfiguracionPrecioProducto> elementos, Boolean descripcion, Boolean precioU, Boolean tiempoE) throws ProquifaNetException {
		try {
//			logger.info("ConfigurarElementos");
			for (ConfiguracionPrecioProducto elemento : elementos) {
				if(descripcion){
					Producto producto = new Producto();
					producto.setIdProducto(elemento.getProducto().getIdProducto());
					producto.setTipo(elemento.getProducto().getTipo());
					producto.setSubtipo(elemento.getProducto().getSubtipo());
					producto.setControl(elemento.getProducto().getControl());
					producto.setCosto(elemento.getProducto().getCosto());
					producto.setMoneda(elemento.getProducto().getMoneda());
					producto.setCodigo(elemento.getProducto().getCodigo());
					producto.setFabrica(elemento.getProducto().getFabrica());
					producto.setCategoriaPrecioLista(elemento.getProducto().getCategoriaPrecioLista());
					producto.setConfiguracion_Precio(elemento.getProducto().getConfiguracion_Precio());
					producto.setIndustria(elemento.getProducto().getIndustria());
					producto.setLicencia(elemento.getProducto().getLicencia());	
					producto.setProveedor(elemento.getIdProveedor());
					producto.setTransitoMandatorioMexico(elemento.getProducto().isTransitoMandatorioMexico());
					if(elemento.getProducto().getCodigo() != null || elemento.getProducto().getFabrica() != null){
						producto.setConcepto(this.productoDAO.obtenerDescripcionProducto(elemento.getProducto().getCodigo(), elemento.getProducto().getFabrica()));
					}
					elemento.setProducto(producto);
				}
//				logger.info("Descripcion");
				if(tiempoE){
					elemento.setTiempoEntregaRuta(this.catalogoProveedorDAO.findTiempoEntregaPorConfiguracion(elemento.getIdConfiguracion()));		
				}
//				logger.info("Tiempo");
				if(precioU){
					Double costoFijo = elemento.getCostoFactorProducto().getFactorCostoFijo() / 100;
					Double factorUtilAAplus = elemento.getCostoFactorProducto().getFactor_AAplus() / 100;
					Double factorUtilAA = elemento.getCostoFactorProducto().getFactor_AA() / 100;
					Double factorUtilAM = elemento.getCostoFactorProducto().getFactor_AM() / 100;
					Double factorUtilAB = elemento.getCostoFactorProducto().getFactor_AB() / 100;
					Double factorUtilMA = elemento.getCostoFactorProducto().getFactor_MA() / 100;
					Double factorUtilMM = elemento.getCostoFactorProducto().getFactor_MM() / 100;
					Double factorUtilMB = elemento.getCostoFactorProducto().getFactor_MB() / 100;
					Double factorUtilBajo = elemento.getCostoFactorProducto().getFactor_Bajo() / 100;
					Double factorUtilStock = elemento.getCostoFactorProducto().getFactor_Stock() / 100;
					Double factorUtilDistribuidor = elemento.getCostoFactorProducto().getFactorDistribuidor() / 100;



					Double costoVentaUnitario = (elemento.getCostoFactorProducto().getCv()) / elemento.getCostoFactorProducto().getCantidad();
					Double costoFijoTotal = 0.00;

					Double utilidadTotalAAplus = 0.00, utilidadTotalAA = 0.00, utilidadTotalAM = 0.00, utilidadTotalAB = 0.00, utilidadTotalMA = 0.00, utilidadTotalMM = 0.00,
							utilidadTotalMB = 0.00, utilidadTotalBajo = 0.00, utilidadTotalStock = 0.00, utilidadTotalDistribuidor = 0.00;


					if(elemento.getCompuestaCostoF()){
						costoFijoTotal = (costoFijo * elemento.getCostoFactorProducto().getCv()) / elemento.getCostoFactorProducto().getCantidad();
					}else{
						costoFijoTotal = (costoFijo * elemento.getCostoFactorProducto().getValor()) / elemento.getCostoFactorProducto().getCantidad();
					}

					if(elemento.getCompuestaFactorU()){

						utilidadTotalAAplus = (factorUtilAAplus * elemento.getCostoFactorProducto().getCv()) / elemento.getCostoFactorProducto().getCantidad();
						utilidadTotalAA = (factorUtilAA * elemento.getCostoFactorProducto().getCv()) / elemento.getCostoFactorProducto().getCantidad();
						utilidadTotalAM = (factorUtilAM * elemento.getCostoFactorProducto().getCv()) / elemento.getCostoFactorProducto().getCantidad();
						utilidadTotalAB = (factorUtilAB * elemento.getCostoFactorProducto().getCv()) / elemento.getCostoFactorProducto().getCantidad();
						utilidadTotalMA = (factorUtilMA * elemento.getCostoFactorProducto().getCv()) / elemento.getCostoFactorProducto().getCantidad();
						utilidadTotalMM = (factorUtilMM * elemento.getCostoFactorProducto().getCv()) / elemento.getCostoFactorProducto().getCantidad();
						utilidadTotalMB = (factorUtilMB * elemento.getCostoFactorProducto().getCv()) / elemento.getCostoFactorProducto().getCantidad();
						utilidadTotalBajo = (factorUtilBajo * elemento.getCostoFactorProducto().getCv()) / elemento.getCostoFactorProducto().getCantidad();
						utilidadTotalStock = (factorUtilStock * elemento.getCostoFactorProducto().getCv()) / elemento.getCostoFactorProducto().getCantidad();
						utilidadTotalDistribuidor = (factorUtilDistribuidor * elemento.getCostoFactorProducto().getCv()) / elemento.getCostoFactorProducto().getCantidad();

					}else{

						utilidadTotalAAplus = (factorUtilAAplus * elemento.getCostoFactorProducto().getValor()) / elemento.getCostoFactorProducto().getCantidad();
						utilidadTotalAA = (factorUtilAA * elemento.getCostoFactorProducto().getValor()) / elemento.getCostoFactorProducto().getCantidad();
						utilidadTotalAM = (factorUtilAM * elemento.getCostoFactorProducto().getValor()) / elemento.getCostoFactorProducto().getCantidad();
						utilidadTotalAB = (factorUtilAB * elemento.getCostoFactorProducto().getValor()) / elemento.getCostoFactorProducto().getCantidad();
						utilidadTotalMA = (factorUtilMA * elemento.getCostoFactorProducto().getValor()) / elemento.getCostoFactorProducto().getCantidad();
						utilidadTotalMM = (factorUtilMM * elemento.getCostoFactorProducto().getValor()) / elemento.getCostoFactorProducto().getCantidad();
						utilidadTotalMB = (factorUtilMB * elemento.getCostoFactorProducto().getValor()) / elemento.getCostoFactorProducto().getCantidad();
						utilidadTotalBajo = (factorUtilBajo * elemento.getCostoFactorProducto().getValor()) / elemento.getCostoFactorProducto().getCantidad();
						utilidadTotalStock = (factorUtilStock * elemento.getCostoFactorProducto().getValor()) / elemento.getCostoFactorProducto().getCantidad();
						utilidadTotalDistribuidor = (factorUtilDistribuidor * elemento.getCostoFactorProducto().getValor()) / elemento.getCostoFactorProducto().getCantidad();

					}

					elemento.getCostoFactorProducto().setPrecioUAAplus((double) Math.round (utilidadTotalAAplus + costoFijoTotal + costoVentaUnitario));
					elemento.getCostoFactorProducto().setPrecioUAA((double) Math.round (utilidadTotalAA + costoFijoTotal + costoVentaUnitario));
					elemento.getCostoFactorProducto().setPrecioUAM((double) Math.round(utilidadTotalAM + costoFijoTotal + costoVentaUnitario));	
					elemento.getCostoFactorProducto().setPrecioUAB((double) Math.round(utilidadTotalAB + costoFijoTotal + costoVentaUnitario));
					elemento.getCostoFactorProducto().setPrecioUMA((double) Math.round(utilidadTotalMA + costoFijoTotal + costoVentaUnitario));
					elemento.getCostoFactorProducto().setPrecioUMM((double) Math.round(utilidadTotalMM + costoFijoTotal + costoVentaUnitario));
					elemento.getCostoFactorProducto().setPrecioUMB((double) Math.round(utilidadTotalMB + costoFijoTotal + costoVentaUnitario));
					elemento.getCostoFactorProducto().setPrecioUBajo((double) Math.round(utilidadTotalBajo + costoFijoTotal + costoVentaUnitario));
					elemento.getCostoFactorProducto().setPrecioUStock((double) Math.round(utilidadTotalStock + costoFijoTotal + costoVentaUnitario));
					elemento.getCostoFactorProducto().setPrecioUDistribuidor((double) Math.round(utilidadTotalDistribuidor + costoFijoTotal + costoVentaUnitario));
					
					elemento.getCostoFactorProducto().setCantidadAA(elemento.getCostoFactorProducto().getCantidad()) ;
					
						if(elemento.getCostoFactorProducto().getDiferencial() == 0){
							elemento.getCostoFactorProducto().setDiferencial(1.0);
						}
						// en la variable de diferencial traigo el costo del producto (PROD.Costo * PROVEE.TC).
						// ((PU/Costo) * 100) - 100
						elemento.getCostoFactorProducto().setDiferencialAAplus			(Math.rint(((((elemento.getCostoFactorProducto().getPrecioUAAplus()) / (elemento.getCostoFactorProducto().getDiferencial())) * 100) - 100) * 1e2) / 1e2 );
						elemento.getCostoFactorProducto().setDiferencialAA				(Math.rint(((((elemento.getCostoFactorProducto().getPrecioUAA())	 	/ (elemento.getCostoFactorProducto().getDiferencial())) * 100) - 100) * 1e2) / 1e2 );
						elemento.getCostoFactorProducto().setDiferencialAM				(Math.rint(((((elemento.getCostoFactorProducto().getPrecioUAM()) 	/ (elemento.getCostoFactorProducto().getDiferencial())) * 100) - 100) * 1e2) / 1e2 );	
						elemento.getCostoFactorProducto().setDiferencialAB				(Math.rint(((((elemento.getCostoFactorProducto().getPrecioUAB()) 	/ (elemento.getCostoFactorProducto().getDiferencial())) * 100) - 100) * 1e2) / 1e2 );
						elemento.getCostoFactorProducto().setDiferencialMA				(Math.rint(((((elemento.getCostoFactorProducto().getPrecioUMA()) 	/ (elemento.getCostoFactorProducto().getDiferencial())) * 100) - 100) * 1e2) / 1e2 );
						elemento.getCostoFactorProducto().setDiferencialMM				(Math.rint(((((elemento.getCostoFactorProducto().getPrecioUMM()) 	/ (elemento.getCostoFactorProducto().getDiferencial())) * 100) - 100) * 1e2) / 1e2 );
						elemento.getCostoFactorProducto().setDiferencialMB				(Math.rint(((((elemento.getCostoFactorProducto().getPrecioUMB()) 	/ (elemento.getCostoFactorProducto().getDiferencial())) * 100) - 100) * 1e2) / 1e2  );
						elemento.getCostoFactorProducto().setDiferencialBajo			(Math.rint(((((elemento.getCostoFactorProducto().getPrecioUBajo()) 	/ (elemento.getCostoFactorProducto().getDiferencial())) * 100) - 100) * 1e2) / 1e2  );
						elemento.getCostoFactorProducto().setDiferencialStock			(Math.rint(((((elemento.getCostoFactorProducto().getPrecioUStock()) 			/ (elemento.getCostoFactorProducto().getDiferencial())) * 100) - 100) * 1e2) / 1e2 );
						elemento.getCostoFactorProducto().setDiferencialDistribuidor	(Math.rint(((((elemento.getCostoFactorProducto().getPrecioUDistribuidor()) 	/ (elemento.getCostoFactorProducto().getDiferencial())) * 100) - 100) * 1e2) / 1e2 );

					
					
					
				}			
			}
			
			return elementos;
		} catch (Exception e) {
			funcion.enviarCorreoAvisoExepcion(e, "elementos: " + elementos,"descripcion: " + descripcion,
					"precioU: " + precioU, "tiempoE: " + tiempoE);
			return new ArrayList<ConfiguracionPrecioProducto>();
		}
	}

//	private List<ConfiguracionPrecioProducto> colocarDescripcionProducto (List<ConfiguracionPrecioProducto> elementos){
//		try {
//			for (ConfiguracionPrecioProducto elemento:elementos) {
//				Producto producto = new Producto();
//				producto.setIdProducto(elemento.getProducto().getIdProducto());
//				producto.setTipo(elemento.getProducto().getTipo());
//				producto.setSubtipo(elemento.getProducto().getSubtipo());
//				producto.setControl(elemento.getProducto().getControl());
//				producto.setCosto(elemento.getProducto().getCosto());
//				producto.setMoneda(elemento.getProducto().getMoneda());
//				producto.setCodigo(elemento.getProducto().getCodigo());
//				producto.setFabrica(elemento.getProducto().getFabrica());
//				producto.setCategoriaPrecioLista(elemento.getProducto().getCategoriaPrecioLista());
//				producto.setConfiguracion_Precio(elemento.getProducto().getConfiguracion_Precio());
//				producto.setIndustria(elemento.getProducto().getIndustria());
//				producto.setLicencia(elemento.getProducto().getLicencia());
//
//				producto.setConcepto(this.productoDAO.obtenerDescripcionProducto(elemento.getProducto().getCodigo(), elemento.getProducto().getFabrica()));
//				elemento.setProducto(producto);
//			}
//			return elementos;
//		} catch (Exception e) {
//			funcion.enviarCorreoAvisoExepcion(e);
//			return new ArrayList<ConfiguracionPrecioProducto>();
//		}
//	}

	@Override
	public Boolean asignarCostoFactorAConfiguracionPrecio(ConfiguracionPrecioProducto configProducto) throws ProquifaNetException {
		try {
			Long idCostoF=0L;
			Long idtiempoEntrega=0L;
			Boolean result=true;
			CostoFactor costoFactor = configProducto.getCostoFactorProducto();

			//logger.info("costo factor: "+costoFactor.getIdCostoFactor());
			//SE COMPRUEBA SI EL ID COSTOFACTOR EXISTE, SI ES ASI, SOLO SE ACTUALIZA LA BD CON LOS VALORES QUE TIENE EL OBJETO 
			if(costoFactor.getIdCostoFactor()!=null && costoFactor.getIdCostoFactor()!=0){
				result = this.catalogoProveedorDAO.actualizarCostoFactor(costoFactor,configProducto.getIdConfiguracion(),false, configProducto.getCompuestaCostoF(), configProducto.getCompuestaFactorU());
				idCostoF = costoFactor.getIdCostoFactor();
			}else{ //SI EL ID COSTOFACTOR ES NULL, ENTONCES SE INSERTA UN REGISTRO EN COSTOFACTOR, Y SE OBTIENE EL "ID" PARA ACTUALIZAR CONFIGURACIONPRECIO
				idCostoF = this.catalogoProveedorDAO.insertarCostoFactor(costoFactor, false, configProducto.getCompuestaCostoF(), configProducto.getCompuestaFactorU());
			}
			//logger.info("Tiempos de entrega por ruta en configuracion Precio");
			List<TiempoEntrega> tiemposEntregaPorRuta=configProducto.getTiempoEntregaRuta();
			//Recorremos la lista de tiempos de entrega para verificar si lleva un idConfiguracion
			//recorremos la lista de tiempos de entrega por ruta
			for(TiempoEntrega tiempoEntregaRuta:tiemposEntregaPorRuta){
				tiempoEntregaRuta.setIdConfiguracionPrecio(configProducto.getIdConfiguracion());
				//si cada uno de los registros trae un idTiempoEntregaRuta en este caso usaremos el idTiempoentrega
				if(tiempoEntregaRuta.getIdTiempoEntrega()!=null && tiempoEntregaRuta.getIdTiempoEntrega()!=0){
					result = this.catalogoProveedorDAO.actualizarTiempoEntregaRuta(tiempoEntregaRuta,0L);
					//idtiempoEntrega = tiempoEntrega.getIdTiempoEntrega();
				}else{
					result=this.catalogoProveedorDAO.insertarTiempoEntregaRuta(tiempoEntregaRuta,0L);
				}
			}
			result = this.catalogoProveedorDAO.actualizarConfiguracionPrecio(configProducto.getIdConfiguracion(), configProducto.getNivel(), idCostoF, idtiempoEntrega);
			//									actualizarConfiguracionPrecio(Long idConfig, String nivel, Long costoF, Long TE);
			return result;
		} catch (Exception e) {
			//logger.info("Error: " + e.getMessage());
			funcion.enviarCorreoAvisoExepcion(e, configProducto);
			return false;
		}
	}

	@Transactional
	public Boolean guardarConfiguracionOferta( ConfiguracionPrecioProducto productos)	throws ProquifaNetException {
		try {
			//logger.info("ENTRO AQUIIIIIIIIIIII    guardarConfiguracionOferta");
			Boolean result = true;
			if(productos != null){
				//   SE INSERTA LA MISMA ESTRUCTURA DE CONFIGURACION PRECIO, CON TIEMPO ENTREGA Y COSTO FACTOR VINCULADOS, PERO ESTOS
				//   TIENEN OTRO NIVEL
				Long idConfigNuevo = this.catalogoProveedorDAO.insertarConfiguracionProductosVende(productos,true);
				if (idConfigNuevo != 0 && idConfigNuevo != null){
					result = true;
				}
			}
			return result;

		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			//logger.info("Error:" + e.getMessage());
			funcion.enviarCorreoAvisoExepcion(e, productos);
			return false;
		}
	}

	public Boolean reintegrarConfiguracion(Long idConfiguracion, String nivel) throws ProquifaNetException {
		try {
			return this.catalogoProveedorDAO.reintegrarConfiguracion(idConfiguracion);
		} catch (Exception e) {
			funcion.enviarCorreoAvisoExepcion(e, "idConfiguracion: " + idConfiguracion, "nivel: " + nivel);
			return false;
		}
	}

	public List<Fabricante> consultarFabricantes(Long idProveedor) throws ProquifaNetException {
		try {
			List<Fabricante> FProveedor = null;
			FProveedor=this.fabricanteDAO.consultarHabilitados(idProveedor);
			return FProveedor;
		} catch (Exception e) {
			funcion.enviarCorreoAvisoExepcion(e, "idProveedor: " + idProveedor);
			return new ArrayList<Fabricante>();
		}
	}

	public Boolean verificarFabricante(Long idProveedor, Long idFabricante, Boolean estado) throws ProquifaNetException {
		try{
			Boolean existeFabricanteProveedor;
			//Checar si el fabricante ya esta registrado en ProveedorFabricante
			Object validaExistencia=this.catalogoProveedorDAO.existeFabricanteProveedor(idProveedor, idFabricante);	
			if(validaExistencia==null)
				throw new ProquifaNetException ("Error al verificar el fabricante");
			else
				existeFabricanteProveedor=(Boolean) validaExistencia;

			if(estado) {//Se va a insertar el fabricante
				if (!existeFabricanteProveedor){
					if(!this.catalogoProveedorDAO.insertarFabricanteProveedor(idProveedor, idFabricante))
						throw new ProquifaNetException ("Error al insertar el fabricante");
				}
			}else{//Se va a eliminar el fabricante
				if(existeFabricanteProveedor){
					if(!this.catalogoProveedorDAO.eliminarFabricanteProveedor(idProveedor, idFabricante))
						throw new ProquifaNetException ("Error al eliminar el fabricante");
				}
			}
			return true;
		}catch(ProquifaNetException e){
			//logger.info(e.getMessage());
			funcion.enviarCorreoAvisoExepcion(e, "idProveedor: " + idProveedor, "idFabricante: " + idFabricante,
					"estado: " + estado);
			return false;
		}
	}

	@Override
	public List<ConfiguracionPrecioProducto> cargarConfiguracionFamilia(Long idConfig) throws ProquifaNetException {
		try {
			return configurarElementos(this.catalogoProveedorDAO.findConfiguracionFamilia(idConfig, 0L, ""), false, false, true);
		} catch (Exception e) {
			funcion.enviarCorreoAvisoExepcion(e, "idConfig: " + idConfig);
			return new ArrayList<ConfiguracionPrecioProducto>();
		}
	}



	public Boolean actualizarCostoPorcentajeNivel (ConfiguracionPrecioProducto config, Boolean tipo, String costo, boolean editarCosto, boolean editarTransito) throws ProquifaNetException {
		try{
			Producto prod = config.getProducto();					
			String nivel = config.getNivel();
			Boolean terminado = false;
			List<MultiusosValores> lista = null;
			List<MultiusosValores> productos = null;
			List<MultiusosValores> productos2 = null;
			Long idConFamilia = 0L;
			Long idConCosto = 0L;
//			Long idConProducto = 0L;
			Long idConCostoPropio = 0L;
			Long idConProductoPropio = 0L;
			
			//+++++++++++++++++++++++++++++++++++++++++++++++TRANSITO MEXICO+++++++++++++++++++++++++++++++++++++++++++++++++++++
			if (editarTransito){
				log.info ("transitoMexico: " + prod.isTransitoMandatorioMexico());
				catalogoProductoDAO.actualizarTransitoMexico(prod);
				return true;
			}

			if (editarCosto){
				//  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//  ++++++++++++++++++++++++++++++++++++++++++++++++++ F A M I L I A +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				if (nivel.equalsIgnoreCase("Familia")){
					// SE OBTIENEN TODAS LAS 'CATEGORIAS DE PRECIO' DE ACUERDO A: TIPO, SUBTIPO, CONTROL, PROVEEDOR 
					lista = this.catalogoProveedorDAO.getListaCategoriaPreciolista(config, costo);
					if (lista != null){
						// EN CASO DE QUE HAYA CATEGORIAS SE VA A RELIZAR LAS OPERACIONES
						for (int i=0; i< lista.size(); i++){
							MultiusosValores currentValor = lista.get(i);
							Double porcierto = (Double.parseDouble(costo)/100)+1;
							Double costoActual = Double.parseDouble(currentValor.getValorDouble8().toString());
							Double costoFinal = Math.rint(( currentValor.getValorDouble8()*porcierto ) * 1e2) / 1e2;
	
							// SE ACTUALIZA LA 'CATEGORIA PRECIO[i]' EN SU CAMPO 'PRECIOLISTA' CON EL PORCIENTO APLICADO    
							if (this.catalogoProveedorDAO.actualizarCategoriaPrecioLista(currentValor.getValorLong4(), costoFinal.toString())){
								// SE ACTUALIZAN TODOS LOS PRODUCTOS QUE TIENEN LA MISMA 'CATEGORIA PRECIO' EN SU CAMPO COSTO
								if (this.catalogoProveedorDAO.actualizarProductosConfiguracionMonto(currentValor.getValorLong4(), currentValor.getValorLong4(), costoFinal.toString())){
									//SE OBTIENEN LOS ID DE LOS PRODUCTOS QUE PERTENECEN A LA 'CATEGORIA PRECIO'
									productos = this.catalogoProveedorDAO.getListaProductosXCategoriaPrecio(currentValor.getValorLong4());
									// SE RECORRE CADA UNO DE LOS ID PARA MODIFICAR O INSERTAR EN 'CAMBIOPRECIOPRODUCTO' (HISTORIAL DE PRECIO)
									for (MultiusosValores r:productos){
										// COMO NO SE ENCONTRO UN REGISTRO CON ESE ID DE PRODUCTO, SE HACE UNA INSERCION EN 'CAMBIOPRECIOPRODUCTO'
										if (this.catalogoProveedorDAO.insertarHistorialCambioPrecio(costoActual, costoFinal, r.getValorLong4())){
											terminado= true;
										}else{
											throw new ProquifaNetException("Error al INSERTAR un nuevo registro en 'CambioPrecioProducto'");
										}
									}
									terminado=true;
	
								}else{
									throw new ProquifaNetException("Error al ACTUALIZAR los productos en el COSTO  'Productos'");
								}
							}else{
								throw new ProquifaNetException("Error al ACTUALIZAR el MONTO  'Categoria_PrecioLista'");
							}
						}
						return terminado;
					}else{
						throw new ProquifaNetException("Error AL CONSULTAR las 'Categoria_PrecioLista'");
					}
				}
				//  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//  ++++++++++++++++++++++++++++++++++++++++++++++ C O S T O  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
				if (nivel.equalsIgnoreCase("Costo")){
					//logger.info("+++++++++++++ENTRO A COSTOO ++++++  NIVEL: " +config.getNivel());
					List<MultiusosValores> categoria = this.catalogoProveedorDAO.getIdCategoriaPreciolista( prod.getCategoriaPrecioLista());
					MultiusosValores objSeleccionado = categoria.get(0);
					Double cantidad = objSeleccionado.getValorDouble7();
					Double total = null;
					terminado=false;
	
					if (tipo){ // TRUE ES PRECIO.... FALSE ES PORCENTAJE
						total = Math.rint(( Double.parseDouble(costo)) * 1e2) / 1e2 ;//Math.rint(( ) * 1e2) / 1e2
					}else{
						total = Math.rint(( cantidad * ((Double.parseDouble(costo)/100)+1)) * 1e2) / 1e2;
	
					}
					// SE BUSCA SI ES QUE HAY UNA CATEGORIA DE LA MISMA FAMILIA, PERO CON EL NUEVO COSTO (TOTAL)
	//				log.info("SE BUSCA SI ES QUE HAY UNA CATEGORIA DE LA MISMA FAMILIA, PERO CON EL NUEVO COSTO (TOTAL)");
					Long idCategoria = this.catalogoProveedorDAO.obtenerIdCategoriaConPrecioNuevo(config,total);
					if (idCategoria > 0){  
	
						// SE OBTIENEN LOS VALORES 
						productos2 = this.catalogoProveedorDAO.verificarExistenHermanosCategoriaPrecio(idCategoria);
						if (!productos2.isEmpty()){
							MultiusosValores current = productos2.get(0);
							Long id= current.getValorLong4();
							List<MultiusosValores> arreglo = this.catalogoProveedorDAO.obtenerDatosConfigXidProducto(id, config);
							if(arreglo.isEmpty() == false){
								current = arreglo.get(0);
								idConFamilia = current.getValorLong4();
								idConCosto = current.getValorLong5();
	//							idConProducto = current.getValorLong6();
								//logger.info("+++++++++++++idConFamilia: "+idConFamilia+"   idConCosto: "+idConCosto+" idConProducto: "+idConProducto +"idProducto: "+prod.getIdProducto());
	
								if(prod.getIdProducto()>0){
									arreglo = this.catalogoProveedorDAO.obtenerDatosConfigXidProducto(prod.getIdProducto(),config);
									current = arreglo.get(0);
									idConCostoPropio = current.getValorLong5();
									idConProductoPropio = current.getValorLong6();
									//logger.info("+++++++++++++idConCostoPropio: "+idConCostoPropio+"   idConProductoPropio: "+idConProductoPropio );
								}
							}
						}
	
	
						//SE OBTIENEN LOS ID DE LOS PRODUCTOS QUE PERTENECEN A LA 'CATEGORIA PRECIO'
						productos = this.catalogoProveedorDAO.getListaProductosXCategoriaPrecio(prod.getCategoriaPrecioLista());
						List<Long> ListaIdProducto = new ArrayList<Long>();
						for (MultiusosValores j:productos){
							List<MultiusosValores>  arreglo1 = this.catalogoProveedorDAO.obtenerDatosConfigXidProducto(j.getValorLong4(), config);
							MultiusosValores current1 = arreglo1.get(0);
							Long idProducto = current1.getValorLong6();
							if(idProducto >0)
								ListaIdProducto.add(idProducto);
						}
						//SI SE TIENE LA CONFIGURACION CON EL NUEVO COSTO SE HACE LO SIGUIENTE
						if (this.catalogoProveedorDAO.actualizarProductosConfiguracionMonto( prod.getCategoriaPrecioLista(),idCategoria,total.toString())){
							//SE OBTIENEN LOS ID DE LOS PRODUCTOS QUE PERTENECEN A LA 'CATEGORIA PRECIO'
							if (this.catalogoProveedorDAO.deleteCategoriaPrecioLista(prod.getCategoriaPrecioLista())){
								// SE RECORRE CADA UNO DE LOS ID PARA INSERTAR EN 'CAMBIOPRECIOPRODUCTO' (HISTORIAL DE PRECIO)
								for (MultiusosValores r:productos){
									// SE HACE UNA INSERCION EN 'CAMBIOPRECIOPRODUCTO' DEACUERDO A LOS PRODUCTOS CON LA MISMA CATEGORIA
									if (this.catalogoProveedorDAO.insertarHistorialCambioPrecio(cantidad, total, r.getValorLong4())){
										terminado= true;
									}else{
										throw new ProquifaNetException("Error al INSERTAR un nuevo registro en 'CambioPrecioProducto'");
									}
									this.catalogoProveedorDAO.updateProductoConfig(r.getValorLong4(), idConFamilia, idConCosto);
									//logger.info("+++++++++++++idConCosto: "+idConCosto+"+-----  IDPRODUCTO: "+r.getValorLong4());
	
								}
								//logger.info("-------------------  idConCosto: "+idConCosto);
	
								//							Se modifica la Configuracion Precio Producto para que tenga la configuracion nueva en costo
	
								if (idConCosto>0){
									if(idConCostoPropio>0){
										//logger.info("+++++++++++++idConCostoPropio: "+idConCostoPropio);
										if(this.catalogoProveedorDAO.eliminarConfiguraciones(idConCostoPropio))
											terminado=true;
										else
											terminado=false;
									}
									if(ListaIdProducto.size()>0){
										for (Long valor:ListaIdProducto){
	
											//logger.info("+++++++++++++idConProductoPropio: "+valor.longValue()+"..... size "+ListaIdProducto.size());
	
											if(this.catalogoProveedorDAO.eliminarConfiguraciones(valor.longValue()))
												terminado=true;
											else
												terminado=false;
										}
									}
								}else{
									if(idConCostoPropio > 0){
										this.catalogoProveedorDAO.updateConfPrecioCosto_cambioPrecio(idConCostoPropio, idConFamilia, total.toString(), config.getIdProveedor());
									}
									//								
								}
	
								return terminado;
	
							}else{
								throw new ProquifaNetException("Error al ELIMINAR la catergoria en 'Categoria_PrecioLista'");
							}
						}else{
							throw new ProquifaNetException("Error al ACTUALIZAR los productos al nuevo 'Categoria_PrecioLista'");
						}
	
					}else{
						// SI NO SE ENCUENTRA UNA CATEGORIA, SE MODIFICA LA QUE SE TIENE EN EL PRECIO LISTA
						if (this.catalogoProveedorDAO.actualizarCategoriaPrecioLista(prod.getCategoriaPrecioLista(), total.toString())){
							// SE ACTUALIZAN LOS COSTOS DE LOS PRODUCTOS 
							if (this.catalogoProveedorDAO.actualizarProductosConfiguracionMonto(prod.getCategoriaPrecioLista(), prod.getCategoriaPrecioLista(),total.toString())){
								//SE OBTIENEN LOS ID DE LOS PRODUCTOS QUE PERTENECEN A LA 'CATEGORIA PRECIO'
								productos = this.catalogoProveedorDAO.getListaProductosXCategoriaPrecio(prod.getCategoriaPrecioLista());
								// SE RECORRE CADA UNO DE LOS ID PARA INSERTAR EN 'CAMBIOPRECIOPRODUCTO' (HISTORIAL DE PRECIO)
								for (MultiusosValores r:productos){
									// SE HACE UNA INSERCION EN 'CAMBIOPRECIOPRODUCTO' DEACUERDO A LOS PRODUCTOS CON LA MISMA CATEGORIA
									if (this.catalogoProveedorDAO.insertarHistorialCambioPrecio(cantidad, total, r.getValorLong4())){
										terminado= true;
									}else{
										throw new ProquifaNetException("Error al INSERTAR un nuevo registro en 'CambioPrecioProducto'");
									}
								}
								return terminado;
							}else
								throw new ProquifaNetException("Error al ACTUALIZAR el costo de los productos en 'Productos'");
						}else
							throw new ProquifaNetException("Error al ACTUALIZAR el preciolista al nuevo 'Categoria_PrecioLista'");
					}
				}
				//  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//  +++++++++++++++++++++++++++++++++++++++++++ C L A S I F I C A C I O N ++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				if (nivel.equalsIgnoreCase("Clasificacion")){
	
					//- LISTAR PRODUCTOS  DE LA CLASIFICACION
					List<Producto> pro = this.catalogoProveedorDAO.getProductosPorClasificacion(config.getIdClasificacion());
					Double total = null;
	
					if (tipo){ // TRUE ES PRECIO.... FALSE ES PORCENTAJE
						total = Math.rint((Double.parseDouble(costo) ) * 1e2) / 1e2;					
						// SE BUSCA SI ES QUE HAY UNA CATEGORIA DE LA MISMA FAMILIA, PERO CON EL NUEVO COSTO (TOTAL)
						Long idCategoria = this.catalogoProveedorDAO.obtenerIdCategoriaConPrecioNuevo(config,total);				
						if((idCategoria == null) || (idCategoria <= 0)){
							// Si no se encontro una categoria con el nuevo costo, se crea una nueva
							idCategoria = this.catalogoProveedorDAO.insertarCategoriaPrecioLista(config, total);
							idConCosto = 0L;
							idConFamilia = config.getIdConfiguracionFamilia();
						}						
						// Se busca en ConfiguracionPrecioProducto un producto que tenga la categoria nueva, obtiene la familia y el costo 
						productos2 = this.catalogoProveedorDAO.verificarExistenHermanosCategoriaPrecio(idCategoria);
						if (!productos2.isEmpty()){//------- Se debe de encontrar aunquesea la familia del los productos
							MultiusosValores current = productos2.get(0);
							Long id= current.getValorLong4();
							List<MultiusosValores> arreglo = this.catalogoProveedorDAO.obtenerDatosConfigXidProducto(id, config);
							if(arreglo.isEmpty() == false){
								current = arreglo.get(0);
								idConFamilia = current.getValorLong4();
								idConCosto = current.getValorLong5();
	//							idConProducto = current.getValorLong6();
							}
						} 
	
						if(this.catalogoProveedorDAO.actualizarProductosMontoPorClasificacion(config.getIdClasificacion(), idCategoria, total)){ // Actualiza los productos que pertenecen a la clasificacion
							for(Producto p : pro){
								if(this.catalogoProveedorDAO.updateProductoConfig(p.getIdProducto(), idConFamilia, idConCosto)){
									this.catalogoProveedorDAO.insertarHistorialCambioPrecio(p.getCosto(), total, p.getIdProducto());//Para guardar la historia
									this.catalogoProveedorDAO.deleteCategoriaPrecioLista(p.getCategoriaPrecioLista()); // Se borra las clasificaciones anteriores asociadas a los productos de la Clasificacion que ya no se utilizan, no estan asociadas a productos
									this.catalogoProveedorDAO.cleanConfiguracionPrecioCosto();// Se borran todas la ConfiguracionesPrecio 'Costo' que no tengan asociados ConfiguracionPrecioProducto
									return true;
								}
	
							}
						}
	
					}else{
	
						for(Producto p : pro){
							total = Math.rint(( p.getCosto() * ((Double.parseDouble(costo)/100)+1) ) * 1e2) / 1e2;
							Long idCategoria = this.catalogoProveedorDAO.obtenerIdCategoriaConPrecioNuevo(config,total);	
							if((idCategoria == null) || (idCategoria <= 0)){
								// Si no se encontro una categoria con el nuevo costo, se crea una nueva
								idCategoria = this.catalogoProveedorDAO.insertarCategoriaPrecioLista(config, total);
								idConCosto = 0L;
								idConFamilia = config.getIdConfiguracionFamilia();
								productos2 = this.catalogoProveedorDAO.verificarExistenHermanosCategoriaPrecio(idCategoria);
								if (!productos2.isEmpty()){//------- Se debe de encontrar aunquesea la familia del los productos
									MultiusosValores current = productos2.get(0);
									Long id= current.getValorLong4();
									List<MultiusosValores> arreglo = this.catalogoProveedorDAO.obtenerDatosConfigXidProducto(id, config);
									if(arreglo.isEmpty() == false){
										current = arreglo.get(0);
										idConFamilia = current.getValorLong4();
										idConCosto = current.getValorLong5();
	//									idConProducto = current.getValorLong6();
									}
								} 
							}
							if(this.catalogoProveedorDAO.actualizarProductoyCategoriaPrecioxProducto(p.getIdProducto(), idCategoria, total.toString())){
								if(this.catalogoProveedorDAO.updateProductoConfig(p.getIdProducto(), idConFamilia, idConCosto)){
									this.catalogoProveedorDAO.insertarHistorialCambioPrecio(p.getCosto(), total, p.getIdProducto());//Para guardar la historia
									this.catalogoProveedorDAO.deleteCategoriaPrecioLista(p.getCategoriaPrecioLista()); // Se borra las clasificaciones anteriores asociadas a los productos de la Clasificacion que ya no se utilizan, no estan asociadas a productos
									this.catalogoProveedorDAO.cleanConfiguracionPrecioCosto();// Se borran todas la ConfiguracionesPrecio 'Costo' que no tengan asociados ConfiguracionPrecioProducto
	
								}
							}
						}
						return true;
					}
	
	
	
					//- SI SE VA A CAMBIAR EL COSTO POR MONTO
					//- BUSCAR CATEGORIA PRECIO PARA EL NUEVO PRECIO, SI NO HAY CREAR UNA
	
					//- BUSCAR CATEGORIAS PRECIOS PARA DESPUES BORRAR TODAS LAS QUE NO ESTEN ASOCIADAS A OTROS PRODUCTOS QUE NO ESTEN EN LA LISTA ORIGINAL
					//- SI YA ESTABA LA CATEGORIA BUSCAR EN CONFIGPRECIO_PRODUCTO SI HAY CONFIGURACIONES COSTO TANTO PROVEEDOR COMO CLIENTE
					//- ACTUALIZAR PRECIO Y CATEGORIA  EN PRODUCTOS
					//- ACTUALIZAR CONFIGURACIONPRECIO_PRODUCTO FK COSTO PROVEEDOR Y CLENTE
	
	
				}
	
	
				//  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//  +++++++++++++++++++++++++++++++++++++++++++++++ P R O D U C T O ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	
	
				if (nivel.equalsIgnoreCase("Producto")){
	
					Double costoProd = prod.getCosto();
					Double total = null;
					//logger.info("++++++++++++++++++++++++++++++  ENTRO A PRODUCTO CAMBIO PRECIO" );
					if (tipo){ // TRUE ES PRECIO.... FALSE ES PORCENTAJE
						total = Math.rint((Double.parseDouble(costo) ) * 1e2) / 1e2;//  Math.rint(( ) * 1e2) / 1e2
					}else{
						total = Math.rint(( costoProd * ((Double.parseDouble(costo)/100)+1) ) * 1e2) / 1e2;
					}
					// SE BUSCA SI HAY UNA CATEGORIA_PRECIOLISTA CON EL NUEVO COSTO (total)
					Long idCategoria = this.catalogoProveedorDAO.obtenerIdCategoriaConPrecioNuevo(config,total);
					if (idCategoria > 0){  
						//logger.info("++  SE OBTUVO UNA CATEGORIA PRECIOLISTA: "+idCategoria );
						Boolean fin=false;
	
						// SE OBTIENEN LOS VALORES 
						productos2 = this.catalogoProveedorDAO.verificarExistenHermanosCategoriaPrecio(idCategoria);
						if (!productos2.isEmpty()){
							MultiusosValores current = productos2.get(0);
							Long id= current.getValorLong4();
							List<MultiusosValores> arreglo = this.catalogoProveedorDAO.obtenerDatosConfigXidProducto(id, config);
							if(arreglo.isEmpty() == false){
								current = arreglo.get(0);
								idConFamilia = current.getValorLong4();
								idConCosto = current.getValorLong5();
	//							idConProducto = current.getValorLong6();
								//logger.info("+++++++++++++idConFamilia: "+idConFamilia+"   idConCosto: "+idConCosto+" idConProducto: "+idConProducto );
	
								arreglo = this.catalogoProveedorDAO.obtenerDatosConfigXidProducto(prod.getIdProducto(),config);
								current = arreglo.get(0);
								idConCostoPropio = current.getValorLong5();
								idConProductoPropio = current.getValorLong6();
								//logger.info("+++++++++++++idConCostoPropio: "+idConCostoPropio+"   idConProductoPropio: "+idConProductoPropio );
							}
						}
	
						// SE ACTUALIZAN LOS COSTOS DE LOS PRODUCTOS Y LA NUEVA CATEGORIA
						if (this.catalogoProveedorDAO.actualizarProductoyCategoriaPrecioxProducto(prod.getIdProducto(), idCategoria, total.toString())){
							// SE HACE UNA INSERCION EN 'CAMBIOPRECIOPRODUCTO' DE ACUERDO AL PRODUCTO QUE SE MODIFICA
							if (this.catalogoProveedorDAO.insertarHistorialCambioPrecio(costoProd, total, prod.getIdProducto())){
								// SE VERIFICA SI ES QUE CUENTA CON MAS HERMANOS DE CATEGORIAPRECIOLISTA
								productos = this.catalogoProveedorDAO.verificarExistenHermanosCategoriaPrecio(prod.getCategoriaPrecioLista());
								if (productos.isEmpty()){
									// EN CASO DE QUE NO TENGA HERMANOS SE ELIMINA LA CATEGORIA, 'NADIE LA USA YA'
									if (this.catalogoProveedorDAO.deleteCategoriaPrecioLista(prod.getCategoriaPrecioLista())){
										fin = true;
									}else{
										throw new ProquifaNetException("Error al BORRAR el registro de 'CategoriaPrecioLista");}
								}else{
									fin = true;
								}
								//  ------------------------------------------------------------------------------------
								this.catalogoProveedorDAO.updateProductoConfig(prod.getIdProducto(), idConFamilia, idConCosto);
								if(idConCostoPropio>0){
									List <MultiusosValores> herm = this.catalogoProveedorDAO.obtenerProductosXIdCosto(idConCostoPropio);
									if(herm.size()==0){
										//logger.info("NOOOOOOO TIENE HERMANOS EN COSTO  " +herm.size());
										if(this.catalogoProveedorDAO.eliminarConfiguraciones(idConCostoPropio))
											fin=true;
										else
											fin=false;
									}else{
										//logger.info(" TIENE HERMANOS EN COSTO, NO SE ELIMINA  "+herm.size());
									}
									fin=true;
								}
								if(idConProductoPropio>0){
									if(this.catalogoProveedorDAO.eliminarConfiguraciones(idConProductoPropio))
										fin=true;
									else
										fin=false;
								}
								return fin;
								// ------------------------------------------------------------------------------------
							}else{
								throw new ProquifaNetException("Error al INSERTAR el registro de 'CambioPrecioLista' (Historial)  "  );}
						}else{
							throw new ProquifaNetException("Error al ACTUALIZAR el 'Producto' en sus campos de Costo y CategoriaPrecioLista");	}
					}else{
						Boolean retorno=false;
						// SE VERIFICA SI ES QUE CUENTA CON MAS HERMANOS DE CATEGORIAPRECIOLISTA
						productos = this.catalogoProveedorDAO.verificarExistenHermanosCategoriaPrecio(prod.getCategoriaPrecioLista());
						if (productos.isEmpty()){
							// SE ACTUALIZA LA 'CATEGORIA' EN SU CAMPO 'PRECIOLISTA' CON EL TOTAL
							if (this.catalogoProveedorDAO.actualizarCategoriaPrecioLista(prod.getCategoriaPrecioLista(), total.toString())){
								// SE ACTUALIZA EL MONTO DEL PRODUCTO
								if (this.catalogoProveedorDAO.actualizarProductoyCategoriaPrecioxProducto(prod.getIdProducto(), prod.getCategoriaPrecioLista(), total.toString())){
									// SE HACE UNA INSERCION EN 'CAMBIOPRECIOPRODUCTO' DE ACUERDO AL PRODUCTO QUE SE MODIFICA (HISTORIAL)
									if (this.catalogoProveedorDAO.insertarHistorialCambioPrecio(costoProd, total, prod.getIdProducto())){
										return true;
									}else{
										throw new ProquifaNetException("Error al INSERTAR el registro de 'CambioPrecioLista' (Historial)  "  );}
								}else{
									throw new ProquifaNetException("Error al ACTUALIZAR el 'Producto' en sus campos de Costo");	}
							}else{
								throw new ProquifaNetException("Error al ACTUALIZAR el MONTO  'Categoria_PrecioLista'");}
						}else{
							// COMO TIENE HERMANOS EN SU CATEGORIA, SE CREA UNA NUEVA CATEGORIA QUE LO CONTENGA
							Long nuevaCategoria = this.catalogoProveedorDAO.insertarCategoriaPrecioLista(config, total);
							if(nuevaCategoria>0){
								// SE ACTUALIZA EL MONTO DEL PRODUCTO Y LA NUEVA CATEGORIA PRECIO QUE SE CREO Y SE LE ASIGNA
								if (this.catalogoProveedorDAO.actualizarProductoyCategoriaPrecioxProducto(prod.getIdProducto(), nuevaCategoria, total.toString())){
									// SE HACE UNA INSERCION EN 'CAMBIOPRECIOPRODUCTO' DE ACUERDO AL PRODUCTO QUE SE MODIFICA  (HISTORIAL)
									if (this.catalogoProveedorDAO.insertarHistorialCambioPrecio(costoProd, total, prod.getIdProducto())){
										retorno = true;
									}else{
										throw new ProquifaNetException("Error al INSERTAR el registro de 'CambioPrecioLista' (Historial)  "  );}
								}else{
									throw new ProquifaNetException("Error al ACTUALIZAR el 'Producto' en sus campos de Costo Y CategoriaPrecioLista");	}
							}else{
								throw new ProquifaNetException("Error al INSERTAR una nueva CategoriaPrecioLista, no se genero");	}
						}
						this.catalogoProveedorDAO.updateConfPrecioProducto_cambioPrecio(config);
						return retorno;
					}
				}
			}//Fin editarCosto
			return false;

		}catch(ProquifaNetException e){
			funcion.enviarCorreoAvisoExepcion(e, config,"tipo: " + tipo,  "costo: " + costo);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			//logger.info("Error: " + e.getMessage());
			return false;
		}
	}

	public ConfiguracionPrecioProducto getConfiguracionPrecio(Long idConfiguracion, Long CategoriaPrecio, String licencia) throws ProquifaNetException {
		try {
			List<ConfiguracionPrecioProducto> result  = configurarElementos((this.catalogoProveedorDAO.obtenerConfiguracionPrecioXId(idConfiguracion,0L,"", CategoriaPrecio, licencia)), true, false, true);
			ConfiguracionPrecioProducto r = result.get(0);

			Producto pro = r.getProducto();
			pro.setCategoriaPrecioLista(CategoriaPrecio);
			pro.setCosto((double) this.catalogoProveedorDAO.getCostoXCategoriaPrecioLista(CategoriaPrecio));
			r.setProducto(pro);
			return r;
		} catch (Exception e) {
			funcion.enviarCorreoAvisoExepcion(e,"idConfiguracion: " + idConfiguracion,  "CategoriaPrecio: " + CategoriaPrecio,
					"licencia: " + licencia);
			return new ConfiguracionPrecioProducto();
		}
	}


	public Boolean operacionesFabricante(Fabricante infoFabricante, String operacion) throws ProquifaNetException {
		try {
			boolean actualizo=false;
			Long idFabricante=0L;

			if (operacion.equalsIgnoreCase("insert")){
				Long idf = catalogoProveedorDAO.getIdFabricantexNombreFabricante(infoFabricante.getNombre());
				if (idf == 0L ){

					idFabricante = this.catalogoProveedorDAO.insertarFabricanteMarca(infoFabricante);

					actualizo=true;
				}else {
					log.info("El idFabricante ya existe con el idfabricante: "+idf);
					actualizo = false;
				}
			}

			if (actualizo){
				if (infoFabricante.getBytes() != null ){
					funcion.copiarArchivo(infoFabricante.getBytes(), idFabricante + "." + infoFabricante.getLogoExt(), "imagenMarca");
				}
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			funcion.enviarCorreoAvisoExepcion(e,infoFabricante,  "operacion: " + operacion);
			return false;
		}
	}

	//	@Override
	//	public Boolean actualizarMontoMinimoOCMV(Long idConfig, Double monto, Double flete, Double descuento)
	//			throws ProquifaNetException {
	//		return this.catalogoProveedorDAO.updateMontoMinimoOCMV( idConfig,  monto,  flete,  descuento);
	//		 
	//	}

	//	@Override
	//	public ConfiguracionPrecio obtenerMontoMinimoOCMV(Long idConfig)
	//			throws ProquifaNetException {
	//		return this.catalogoProveedorDAO.getMontoMinimoOCMV(idConfig);
	//	}

	@Override
	public FormulaPrecio obtenerInformacionFormulaPrecio(Long idProveedor,
			Long idProducto, String nivel, Long idConfig) throws ProquifaNetException {
		try{
			//logger.info("Entra a obtenerInformacionFormulaPrecio");

			int stock_flete = 0;

			//			if (nivel.toUpperCase().equals("STOCK") || nivel.toUpperCase().equals("FEXPRESS") ){
			if (nivel.toUpperCase().equals("FEXPRESS") ){
				stock_flete = 1;
			}else{
				stock_flete = 0;

			}

			return this.catalogoProveedorDAO.getInformacionFormulaPrecio(idProveedor, idProducto, stock_flete, nivel, 0L, idConfig);

		}catch(Exception e){
			log.info(e.getMessage());
			funcion.enviarCorreoAvisoExepcion(e,"idProducto: " + idProducto,  "nivel: " + nivel, "idConfig: " + idConfig);
			return new FormulaPrecio();
		}
	}

	@Override
	public Licencia obtenerLicenciasProveedor(Long idProveedor)
			throws ProquifaNetException {
		try{
			return this.catalogoProveedorDAO.getLicenciasProveedor(idProveedor);
		}catch(Exception e){
			log.info(e.getMessage());
			funcion.enviarCorreoAvisoExepcion(e,"idProveedor: " + idProveedor);
			return new Licencia();
		}
	}

	@Override
	public Boolean actualizarLicenciasProveedor(Licencia licencia)
			throws ProquifaNetException {
		try {
			return this.catalogoProveedorDAO.updateLicencuasProveedor(licencia);
		} catch (Exception e) {
			funcion.enviarCorreoAvisoExepcion(e,licencia);
			return false;
		}
	}

	@Override
	public List<ConfiguracionPrecio> listarConfiguracionPrecioProveedor(
			Long idProveedor) throws ProquifaNetException {
		try {
			return this.catalogoProveedorDAO.getListaConfiguracionPrecioProveedor(idProveedor);
		} catch (Exception e) {
			funcion.enviarCorreoAvisoExepcion(e,"idProveedor: " + idProveedor);
			return new ArrayList<ConfiguracionPrecio>();
		}
	}

	@Override
	public List<ConfiguracionPrecioProducto> listarConfiguracionPrecioProductoCosto(
			Long idConfiguracion) throws ProquifaNetException {
		try {
			return null;
		} catch (Exception e) {
			funcion.enviarCorreoAvisoExepcion(e,"idConfiguracion: " + idConfiguracion);
			return new ArrayList<ConfiguracionPrecioProducto>();
		}
	}

	@Override
	public Double obtenerCostoProductoCompra(Long idProducto)
			throws ProquifaNetException {
		try {
			return (double) this.catalogoProveedorDAO.getCostoPorIdProducto(idProducto);	
		} catch (Exception e) {
			funcion.enviarCorreoAvisoExepcion(e,"idProducto: " + idProducto);
			return -1D;
		}
	}

	@Override
	public Boolean actualizarLogistica(Logistica logistica)
			throws ProquifaNetException {
		try {
			Boolean r = false,  r3 = false;//r2 = false,

			// FLETE NORMAL
			if(logistica.getFlete().isEmpty() == false){
				for (Flete f : logistica.getFlete()){

					f.setConcatenaRuta(concatenarRutas(f.getRuta()));

					if((f.getIdFlete() != null) && (f.getIdFlete() > 0)){
						if(this.catalogoProveedorDAO.updateProveedorFlete(f) == null) { r = false;}
						else{ r = true;}
					}else{
						if(this.catalogoProveedorDAO.insertProveedorFlete(f,logistica.getIdProveedor()) == 0){ r = false;}
						else {r= true;}
					}
				}
			}else{
				r= true;
			}

			// TIEMPO DE ENTREGA
			//		if(logistica.getTiempoEntrega().isEmpty() == false){
			//			for(ProveedorTiempoEntrega te : logistica.getTiempoEntrega()){
			//				if((te.getIdProveedorTiempoEntrega() != null)&& (te.getIdProveedorTiempoEntrega() > 0)){
			//					if(this.catalogoProveedorDAO.updateProveedorTiempoEntrega(te) == null){	r2 = false;}
			//					else {r2 = true;}
			//				}else{
			//					if(this.catalogoProveedorDAO.insertProveedorTiempoEntrega(te,logistica.getIdProveedor()) == 0){	r2 =false;}
			//					else {r2 = true;}
			//				}
			//			}
			//		}else{
			//			r2 = true;
			//		}
			//	FLETE EXPRESS
			if(logistica.getFleteExpress() == null){
				r3= true;
			}else{
				logistica.getFleteExpress().setConcatenaRuta(concatenarRutas(logistica.getFleteExpress().getRuta()));
				if(logistica.getFleteExpress().getIdFlete() == null){
					if(this.catalogoProveedorDAO.insertProveedorFleteExpress(logistica.getFleteExpress(), logistica.getIdProveedor()) == 0){r3 = false;}
					else{ r3 = true;}

				}else{
					if(logistica.getFleteExpress().getIdFlete() > 0){
						if(this.catalogoProveedorDAO.updateProveedorFlete(logistica.getFleteExpress()) == null){r3= false;}
						else{r3 = true;}	
					}else{
						if(this.catalogoProveedorDAO.insertProveedorFleteExpress(logistica.getFleteExpress(), logistica.getIdProveedor()) == 0){r3 = false;}
						else{ r3 = true;}
					}
				}
			}


			if ((r == true)&&( r3 == true)){
				return true;
			}else{
				return false;
			}

		} catch (Exception e) {
			funcion.enviarCorreoAvisoExepcion(e,"logistica: " + logistica);
			return false;
		}
	}

	@Override
	public List<Flete> listarFletesProveedor(Long idProveedor)
			throws ProquifaNetException {
		try {
			return this.catalogoProveedorDAO.findFleteProveedor(idProveedor);
		} catch (Exception e) {
			funcion.enviarCorreoAvisoExepcion(e,"idProveedor: " + idProveedor);
			return new  ArrayList<Flete>();
		}
	}


	List<RutaFlete> convertirRutaStringLista(String rutas){
		try {
			List<RutaFlete> lrf = new ArrayList<RutaFlete>();

			if(!rutas.equals("")){
				for (String retval: rutas.split("%-%")){
					if(!retval.equals("")){
						RutaFlete rf = new RutaFlete();
						String[] s = new String[2]; 
						s = retval.split("%:%");
						rf.setDestino(s[0]);
						rf.setTiempoEntrega(s[1]);
						lrf.add(rf);
					}
				}
			}
			//		int i = 0;
			//		for (String retval: rutas.split("%:%")){
			//			lrf.get(i).setTiempoEntrega(retval);
			//			i++;
			//		}

			return lrf;
		} catch (Exception e) {
			funcion.enviarCorreoAvisoExepcion(e,"rutas: " + rutas);
			return new  ArrayList<RutaFlete>();
		}
	}

	@Override
	public Logistica obtenerLogisticaProveedor(Long idProveedor)
			throws ProquifaNetException {
		try{
			Logistica logistica = new Logistica();

			logistica.setFleteExpress(this.catalogoProveedorDAO.findFleteExpressProveedor(idProveedor));

			if(logistica.getFleteExpress() != null){
				if(logistica.getFleteExpress().getConcatenaRuta() != null && logistica.getFleteExpress().getConcatenaRuta().length() > 6){
					logistica.getFleteExpress().setRuta(convertirRutaStringLista(logistica.getFleteExpress().getConcatenaRuta()));
				}
			}

			logistica.setFlete(this.listarFletesProveedor(idProveedor));

			for(Flete f : logistica.getFlete()){
				if(f.getConcatenaRuta() != null && f.getConcatenaRuta().length() > 6){
					f.setRuta(convertirRutaStringLista(f.getConcatenaRuta()));
				}
			}
			if(logistica.getFlete().isEmpty() == false){
				logistica.setFua(logistica.getFlete().get(0).getFua());
			}

			if(logistica.getFleteExpress() == null){}
			else{
				if(logistica.getFleteExpress().getFua() == null){}
				else{
					if(logistica.getFua() != null){
						if(logistica.getFua().before(logistica.getFleteExpress().getFua()) == true){
							logistica.setFua(logistica.getFleteExpress().getFua());
						}
					}else{
						logistica.setFua(logistica.getFleteExpress().getFua());
					}
				}
			}

			logistica.setIdProveedor(idProveedor);

			return logistica;
		}catch (Exception e) {
			log.info(e.getMessage());
			funcion.enviarCorreoAvisoExepcion(e,"idProveedor: " + idProveedor);
			return new  Logistica();
		}
	}

	@Override
	public Boolean actualizarPropiedadesConfiguracionPrecio(
			ConfiguracionPrecio configuracionPrecio)
					throws ProquifaNetException {
		try {
			Boolean regresa = false, regresa2 = false;
			if(configuracionPrecio.getCaducidad() != null){
				regresa = this.catalogoProveedorDAO.updateCaducidadProductosConfiguracionPrecio(configuracionPrecio);
			}else{
				regresa = true;
			}

			regresa2 = this.catalogoProveedorDAO.updateRestringirConfiguracionPrecio(configuracionPrecio);

			if((regresa == true) && (regresa2 == true)){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			funcion.enviarCorreoAvisoExepcion(e,configuracionPrecio);
			return false;
		}
	}

	@Override
	public List<ConfiguracionPrecioProducto> obtenerConfiguracionesPrecioCosto(
			Long idConfigFam) throws ProquifaNetException {
		try {
			return configurarElementos(this.catalogoProveedorDAO.findConfiguracionesPrecioCosto(idConfigFam), false, true, true);
		} catch (Exception e) {
			//logger.info("Error:" + e.getMessage());
			funcion.enviarCorreoAvisoExepcion(e,"idConfigFam: " + idConfigFam);
			return new ArrayList<ConfiguracionPrecioProducto>();
		}
	}

	@Override
	public List<ClasificacionConfiguracionPrecio> listarConceptoClasifConfigPrecio(
			Long idConfigFam) throws ProquifaNetException {
		try {
			return this.catalogoProveedorDAO.findConceptoClasifConfigPrecio(idConfigFam,0L);
		} catch (Exception e) {
			funcion.enviarCorreoAvisoExepcion(e,"idConfigFam: " + idConfigFam);
			return new ArrayList<ClasificacionConfiguracionPrecio>();
		}
	}

	@Override
	public Integer actualizarConceptoClasifConfigPrecio(
			List<ClasificacionConfiguracionPrecio> conceptos)
					throws ProquifaNetException {
		try {
			Integer r = 0, r1 = -2, r2 = -2, r3 = -2;

			for(ClasificacionConfiguracionPrecio c:conceptos){
				if(c.getEliminar() == true){

					r1 = this.catalogoProveedorDAO.deleteConceptoClasifConfigPrecio(c);
				}else{
					if(c.getIdClasificacion() != null){
						if(c.getIdClasificacion() > 0){
							r2 = this.catalogoProveedorDAO.updateConceptoClasifConfigPrecio(c);
						}else{
							r3 = this.catalogoProveedorDAO.insertConceptoClasifConfigPrecio(c);
						}
					}else{
						r3 = this.catalogoProveedorDAO.insertConceptoClasifConfigPrecio(c);
					}
				}
				if( (r == 1)||(r == 0)) {
					if(r1 == 0){
						r = -3;
					}else if(r2 == 0){
						r = -2;
					}else if(r3 == 0){
						r = -1;
					}else{
						r = 1;
					}

				}
			}
			return r;
		} catch (Exception e) {
			funcion.enviarCorreoAvisoExepcion(e);
			return -1;
		}
	}

	@Override
	public ConfiguracionPrecioProducto obtenerConfiguracionPrecioClasificacion(
			Long idConfiguracion, Long idClasificacion)
					throws ProquifaNetException {
		try {
			List<ConfiguracionPrecioProducto> result  = configurarElementos(this.catalogoProveedorDAO.obtenerConfiguracionClasificacion(idConfiguracion,0L,"", idClasificacion), true, false, true);
			ConfiguracionPrecioProducto r = result.get(0);

			//		Producto pro = r.getProducto();
			//		pro.setCategoriaPrecioLista(CategoriaPrecio);
			//		pro.setCosto((double) this.catalogoProveedorDAO.getCostoXCategoriaPrecioLista(CategoriaPrecio));
			//		r.setProducto(pro);
			return r;
		} catch (Exception e) {
			funcion.enviarCorreoAvisoExepcion(e);
			return new ConfiguracionPrecioProducto();
		}
	}

	@Override
	public List<ClasificacionConfiguracionPrecio> obtenerConfiguracionesPrecioClasificacion(
			Long idConfigFamilia) throws ProquifaNetException {
		try {
			return this.catalogoProveedorDAO.findConceptoClasifConfigPrecio(idConfigFamilia, 0L);
		} catch (Exception e) {
			funcion.enviarCorreoAvisoExepcion(e,"idConfigFamilia: " + idConfigFamilia);
			return new ArrayList<ClasificacionConfiguracionPrecio>();
		}
	}

	@Override
	public List<ConfiguracionPrecioProducto> obtenerClasificacionPrecioProductoPorCatPrecio(
			Long idClasificacion) throws ProquifaNetException {
		try {
			return configurarElementos(this.catalogoProveedorDAO.findClasificacionPrecioProductoPorCatPrecio(idClasificacion), false, true, false) ;
		} catch (Exception e) {
			//logger.info("Error:" + e.getMessage());
			funcion.enviarCorreoAvisoExepcion(e, "idClasificacion: " + idClasificacion);
			return new ArrayList<ConfiguracionPrecioProducto>();
		}
	}

	@Override
	public FormulaPrecio obtenerInformacionFormulaPrecioClasificacion(
			Long idProveedor, Long idProducto, String nivel, Long idConfig)
					throws ProquifaNetException {
		try{
			return this.catalogoProveedorDAO.getInformacionFormulaPrecioClasificacion(idProveedor, idProducto, 0, nivel, 0L, idConfig);
		}catch(Exception e){
			log.info(e.getMessage());
			funcion.enviarCorreoAvisoExepcion(e, "idProveedor: " + idProveedor,"idProducto: " + idProducto,
					"nivel: " + nivel);
			return new FormulaPrecio();
		}
	}

	@Override
	public List<Empresa> obtenerRelacionEmpresasProveedor(Long idProveedor)
			throws ProquifaNetException {
		try {
			return this.catalogoProveedorDAO.findRelacionEmpresasProveedor(idProveedor);
		} catch (Exception e) {
			funcion.enviarCorreoAvisoExepcion(e, "idProveedor: " + idProveedor);
			return new ArrayList<Empresa>();
		}
	}

	@Override
	public Boolean actualizarRelacionEmpresasProveedor(List<Empresa> empresas)
			throws ProquifaNetException {
		try {
			for(Empresa e : empresas){
				if(e.getIdEmpresaProveedor() > 0){
					this.catalogoProveedorDAO.updateRelacionEmpresasProveedor(e);
				}else{

					this.catalogoProveedorDAO.insertRelacionEmpresasProveedor(e);
				}
			}
			return true;
		} catch (Exception e) {
			funcion.enviarCorreoAvisoExepcion(e);
			return false;
		}
	}

	private String concatenarRutas (List<RutaFlete> ruta){
		try {
			String concatena = "";
			for(RutaFlete r : ruta){
				concatena += "%-%" + r.getDestino() + "%:%" + r.getTiempoEntrega();
			}
			return concatena;
		} catch (Exception e) {
			funcion.enviarCorreoAvisoExepcion(e);
			return "";
		}
	}

	@Override
	public InformacionPagos obtenerInformacionPagosProveedor(Long idProveedor)
			throws ProquifaNetException {
		try {
			InformacionPagos  ip = this.catalogoProveedorDAO.getInformacionPagosProveedor(idProveedor);

			if(ip != null){
				ip.setMedios(this.catalogoProveedorDAO.getMediosDePagoProveedor(idProveedor));
			}
			return ip;
		} catch (Exception e) {
			funcion.enviarCorreoAvisoExepcion(e, "idProveedor: " + idProveedor);
			return new InformacionPagos();
		}
	}

	@Override
	public Boolean actualizarInformacionPagoProveeedor(
			InformacionPagos informacion) throws ProquifaNetException {
		try {
			this.catalogoProveedorDAO.updateInformacionPagoProveeedor(informacion);

			if(informacion.getMedios() != null){
				for(MedioPago m : informacion.getMedios()){
					if(m.getIdMedioPago() != null && m.getIdMedioPago() > 0){
						log.info("Update");
						this.catalogoProveedorDAO.updateMediosDePagoProveedor(m);

					}else{
						log.info("Insert");
						this.catalogoProveedorDAO.insertMediosDePagoProveedor(m);
					}		
				}
			}		
			return true;
		} catch (Exception e) {
			funcion.enviarCorreoAvisoExepcion(e, informacion);
			return false;
		}

	}

	@Override
	public Boolean restableceTodasConfiguracionesPorNivel(String nivelConfig,
			Long idConfigFamilia) throws ProquifaNetException {
		try {
			Boolean res = true;

			for(Long idConfig : this.catalogoProveedorDAO.findListaConfiguracionesNivel(nivelConfig, idConfigFamilia)){
				log.info("Config: " + idConfig);
				if(!reintegrarConfiguracion(idConfig,nivelConfig)){
					res = false;
				}
			}		

			return res;
		} catch (Exception e) {
			funcion.enviarCorreoAvisoExepcion(e, "nivelConfig: " + nivelConfig, "idConfigFamilia: " + idConfigFamilia);
			return false;
		}
	}

	public List<Proveedor> listarProveedoresXUsuario(Empleado usuario) throws ProquifaNetException{
		try {
			log.info("------------------------------");
			log.info("idFuncion:"+usuario.getIdFuncion() + "   idEmpleado:"+ usuario.getIdEmpleado());
			if(usuario.getIdFuncion() == 10){
				log.info("inicia for");
				if(usuario.getRoles().size() > 0){
					for(String rol: usuario.getRoles()) {
						log.info(rol);
						if(rol.equalsIgnoreCase("Comprador_Master")){
							usuario.setNumrol(15);
						}
					}

				}else{
					log.info("comprador sin roles");
					usuario.setNumrol(0);
				}
			}
			log.info("numrol:"+ usuario.getNumrol());
			log.info("Empezaremos con el query");
			return this.catalogoProveedorDAO.findProveedoresXUsuario(usuario);
		} catch (Exception e) {
//			funcion.enviarCorreoAvisoExepcion(e, usuario);
			return new ArrayList<Proveedor>();
		}
	}

	
	@Override
	public Double comprasAnualAnteriorxProveedor(Long idProveedor) throws ProquifaNetException{
		try {
			return this.catalogoProveedorDAO.comprasAnualAnteriorxProveedor(idProveedor);
		} catch (Exception e) {
			funcion.enviarCorreoAvisoExepcion(e, "idProveedor:");
			return -1D;
		}
	}

	@Override
	public Double obtenerObjetivoTranscurrido(Long idProveedor) throws ProquifaNetException{
		try {
			return this.catalogoProveedorDAO.obtenerObjetivoTranscurrido(idProveedor);
		} catch (Exception e) {
			funcion.enviarCorreoAvisoExepcion(e, "idProveedor:" + idProveedor);
			return -1D;
		}
	}

	@Override
	public Double obtenerObjetivoMontoTranscurrido(Long idProveedor) throws ProquifaNetException{
		try {
			return this.catalogoProveedorDAO.obtenerObjetivoMontoTranscurrido(idProveedor);
			
		} catch (Exception e) {
			funcion.enviarCorreoAvisoExepcion(e);
			return -1D;
		}
	}

	@Override
	public List<ValorAdicional> listarProveedoresAgenteAduanal() throws ProquifaNetException {
		try {
			return this.catalogoProveedorDAO.findValorAdicional("AgenteAduanal");
		} catch (Exception e) {
			funcion.enviarCorreoAvisoExepcion(e);
			return new ArrayList<ValorAdicional>();
		}
	}

	@Override
	public List<ValorAdicional> listarProveedoresTipoProducto() throws ProquifaNetException {
		try {
			return this.catalogoProveedorDAO.findValorAdicional("TipoProducto");
		} catch (Exception e) {
			funcion.enviarCorreoAvisoExepcion(e);
			return new ArrayList<ValorAdicional>();
		}
	}

	public List<String> obtenerFoliosPendientesInspector(Long idProveedor)  throws ProquifaNetException{
		try {
			return this.catalogoProveedorDAO.getPendientesInspector(idProveedor);
		} catch (Exception e) {
			funcion.enviarCorreoAvisoExepcion(e, "idProveedor: " + idProveedor);
			return new ArrayList<String>();
		}
	}

	@Override
	public Direccion obtenerDireccionRecoleccion(Long idProveedor) throws ProquifaNetException {
		try{
			Direccion d = this.direccionDAO.getDireccionRecoleccion(idProveedor);

			if(d!= null){
				if(d.getIdDireccion() != null && d.getIdDireccion() > 0){
					List<Horario> horarios = listarHorariosDireccionRecoleccion(d.getIdDireccion(), "Recoleccion");

					if(horarios != null && horarios.size() > 0){											
						d.setHorarios(horarios);
					}
					return d;
				} else{
					return null;
				}
			} else {
				return null;
			}		

		}catch (Exception e) {
			log.info(e.getMessage());
			funcion.enviarCorreoAvisoExepcion(e, "idProveedor: " + idProveedor);
			return new Direccion();
		}
	}

	@Override
	@Transactional
	public boolean actualizarDireccionRecoleccion(Direccion d) throws ProquifaNetException{
		try {
			boolean bRes = true;			
			if(d.getBorrar() != null && d.getBorrar() == true){
				bRes =  direccionDAO.deleteHorariosDireccion(d.getIdDireccion()); //Se eliminan primero todas los Horarios de esa direccion				

				if(bRes){
					bRes = direccionDAO.deleteDireccion(d.getIdDireccion());
				}

			}else{				
				if(d.getIdDireccion() != null && d.getIdDireccion() > 0){
					bRes = direccionDAO.updateDireccion(d);							
				}else{
					Long idDireccion = 0L;
					idDireccion = direccionDAO.insertDireccion(d);					
					if(idDireccion > 0){
						if(d.getHorarios() != null && d.getHorarios().size()>0){
							for(Horario h : d.getHorarios()){
								h.setIdDireccion(idDireccion);
							}
						}
					}
				}

				if(d.getHorarios() != null && d.getHorarios().size()>0){
					actualizarHorarioRecoleccion(d.getHorarios());
				}
			}			
			return true;
		} catch (Exception e) {
			log.info(e.getMessage());
			funcion.enviarCorreoAvisoExepcion(e, d);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.info(e.getMessage());
			return false;
		}
	}

	@Override
	@Transactional
	public boolean actualizarHorarioRecoleccion(List<Horario> horarios) throws ProquifaNetException{
		try{			
			for(Horario h : horarios){

				if(h.getBorrar() != null && h.getBorrar() == true){ // Eliminar Horario
					direccionDAO.deleteHorario(h.getIdHorario());
				} else{

					h = funcion.obtenerHorarioPorPuntos(h);					

					if(h.getIdHorario() !=  null && h.getIdHorario().longValue() > 0){ // Actualizar Horario						
						direccionDAO.updateHorario(h);		

					} else{ // Insertar Horario						
						direccionDAO.insertHorario(h);						
					}
				}
			}

			return true;
		} catch (Exception e) {
			funcion.enviarCorreoAvisoExepcion(e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.info(e.getMessage());
			return false;
		}
	}

	@Override
	public List<Horario> listarHorariosDireccionRecoleccion(Long idDireccion, String tipo) throws ProquifaNetException {
		try {
			List<Horario> horario = direccionDAO.getHorario(idDireccion, tipo);

			for(Horario h : horario){
				Point tmp = new Point();
				Point tmp2 = new Point();
				ArrayList<Integer> lunes = new ArrayList<Integer>();
				ArrayList<Integer> martes = new ArrayList<Integer>();
				ArrayList<Integer> miercoles = new ArrayList<Integer>();
				ArrayList<Integer> jueves = new ArrayList<Integer>();
				ArrayList<Integer> viernes = new ArrayList<Integer>();		

				if(h.getLunes()){
					if(h.getLuDe1() != null & h.getLuA1() != null){
						tmp = funcion.obtenerCoordenadaHorario(h.getLuDe1(), h.getLuA1());
					}
					if(h.getLuDe2() != null & h.getLuA2() != null){
						tmp2 = funcion.obtenerCoordenadaHorario(h.getLuDe2(), h.getLuA2());
					}
					for(int i = (int) tmp.getX(); i <= (int)tmp.getY(); i++ ){			// Primer periodo		
						lunes.add(i);
					}
					for(int i = (int) tmp2.getX(); i <= (int)tmp2.getY(); i++ ){		//Segundo periodo
						lunes.add(i);
					}
					h.setPlunes(lunes);

				}
				if(h.getMartes()){
					if(h.getMaDe1() != null & h.getMaA1() != null){
						tmp = funcion.obtenerCoordenadaHorario(h.getMaDe1(), h.getMaA1());
					}
					if(h.getMaDe2() != null & h.getMaA2() != null){
						tmp2 = funcion.obtenerCoordenadaHorario(h.getMaDe2(), h.getMaA2());
					}
					for(int i = (int) tmp.getX(); i <= (int)tmp.getY(); i++ ){			// Primer periodo		
						martes.add(i);
					}
					for(int i = (int) tmp2.getX(); i <= (int)tmp2.getY(); i++ ){		//Segundo periodo
						martes.add(i);
					}
					h.setPmartes(martes);

				}
				if(h.getMiercoles()){
					if(h.getMiDe1() != null & h.getMiA1() != null){
						tmp = funcion.obtenerCoordenadaHorario(h.getMiDe1(), h.getMiA1());
					}
					if(h.getMiDe2() != null & h.getMiA2() != null){
						tmp2 = funcion.obtenerCoordenadaHorario(h.getMiDe2(), h.getMiA2());
					}
					for(int i = (int) tmp.getX(); i <= (int)tmp.getY(); i++ ){			// Primer periodo		
						miercoles.add(i);
					}
					for(int i = (int) tmp2.getX(); i <= (int)tmp2.getY(); i++ ){		//Segundo periodo
						miercoles.add(i);
					}
					h.setPmiercoles(miercoles);

				}
				if(h.getJueves()){
					if(h.getJuDe1() != null & h.getJuA1() != null){
						tmp = funcion.obtenerCoordenadaHorario(h.getJuDe1(), h.getJuA1());
					}
					if(h.getJuDe2() != null & h.getJuA2() != null){
						tmp2 = funcion.obtenerCoordenadaHorario(h.getJuDe2(), h.getJuA2());		
					}
					for(int i = (int) tmp.getX(); i <= (int)tmp.getY(); i++ ){			// Primer periodo		
						jueves.add(i);
					}
					for(int i = (int) tmp2.getX(); i <= (int)tmp2.getY(); i++ ){		//Segundo periodo
						jueves.add(i);
					}
					h.setPjueves(jueves);

				}
				if(h.getViernes()){
					if(h.getViDe1() != null & h.getViA1() != null){
						tmp = funcion.obtenerCoordenadaHorario(h.getViDe1(), h.getViA1());
					}
					if(h.getViDe2() != null & h.getViA2() != null){
						tmp2 = funcion.obtenerCoordenadaHorario(h.getViDe2(), h.getViA2());
					}

					for(int i = (int) tmp.getX(); i <= (int)tmp.getY(); i++ ){			// Primer periodo		
						viernes.add(i);
					}
					for(int i = (int) tmp2.getX(); i <= (int)tmp2.getY(); i++ ){		//Segundo periodo
						viernes.add(i);
					}
					h.setPviernes(viernes);

				}
			}		

			return horario;
		} catch (Exception e) {
			funcion.enviarCorreoAvisoExepcion(e, "idDireccion: " + idDireccion, "tipo: " + tipo);
					return new ArrayList<Horario>();
		}
	}
	
	public String prueba(){
		log.info("pasaa pruebaa");
		return "pasaa pruebaa";
	}
	
	
	@Override
	public List<ClasificacionConfiguracionPrecio> obtenerClasificacionesXidProveedor(
			Long idProveedor) throws ProquifaNetException {
		try {
			return this.catalogoProveedorDAO.findConceptoClasifConfigPrecioXidProveedor(idProveedor, 0L);
		} catch (Exception e) {
			funcion.enviarCorreoAvisoExepcion(e,"idConfigFamilia: " + idProveedor);
			return new ArrayList<ClasificacionConfiguracionPrecio>();
		}
	}
	
	@Override
	public List<Producto> obtenerTodosLosProductosDelProveedor(
			Integer idProveedor) throws ProquifaNetException {
		try {
			
			List<Producto> prod = this.productoDAO.obtenerProductoParaDisponibilidad(idProveedor, "");
			for(Producto p : prod){
				p.setConcepto(this.productoDAO.obtenerDescripcionProducto(p.getCodigo(), p.getFabrica()));
			}
			
			
			return prod;
		} catch (Exception e) {
			
			return new ArrayList<Producto>();
		}
	}
	
	@Transactional
	@Override
	public boolean agregarActualizarCampana(Campana campana) throws ProquifaNetException {
		try {
			
			if(campana.getId_Camapana() == 0)
			{
			campana.setId_Camapana((this.catalogoProveedorDAO.insertarCampanaComercial(campana)));
			}
			else if(campana.getId_Camapana() > 0)
			{
				
				this.catalogoProveedorDAO.actualizaCampanaComercial(campana);
			}
				
			
			this.catalogoProveedorDAO.eliminarConfiguracionCampana(campana.getId_Camapana());
			

			if(campana.getTipo().equalsIgnoreCase("proveedor"))
			{
				campana.setClasificaciones(null);
				campana.setFamilias(null);
				campana.setProductos(null);
				this.catalogoProveedorDAO.insertarListaCampana(campana, 1);
			}
			else if(campana.getTipo().equalsIgnoreCase("Familia"))
			{
				campana.setClasificaciones(null);
				campana.setIdProvee(null);
				campana.setProductos(null);
				this.catalogoProveedorDAO.insertarListaCampana(campana, campana.getFamilias().size());
			}
			else if(campana.getTipo().equalsIgnoreCase("Clasificación"))
			{
				campana.setFamilias(null);
				campana.setIdProvee(null);
				campana.setProductos(null);
				this.catalogoProveedorDAO.insertarListaCampana(campana, campana.getClasificaciones().size());
			}
			else if(campana.getTipo().equalsIgnoreCase("Producto"))
			{
				campana.setFamilias(null);
				campana.setIdProvee(null);
				campana.setClasificaciones(null);
				this.catalogoProveedorDAO.insertarListaCampana(campana, campana.getProductos().size());
			}
			
		return  true;
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
	}
	
	
	@Transactional
	@Override
	public boolean eliminarCampanaComercial(Campana campA) throws ProquifaNetException {
		try {
				this.catalogoProveedorDAO.eliminarConfiguracionCampana(campA.getId_Camapana());
				this.catalogoProveedorDAO.eliminarCampanaComercalProveedor(campA.getId_Camapana());
            return true;

		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
	}
	
	
	@Transactional
	@Override
	public List<Campana>obtenerCampanasComercialesPorIdProveedor(Long idProveedor) throws ProquifaNetException {
		try {
			
			List<Campana> campanasCo = new ArrayList<Campana>();
			
			campanasCo = this.catalogoProveedorDAO.obtenerCampanasDeProveedor(idProveedor);
			

			
				for (Campana campanaA : campanasCo) {

					if(campanaA.getTipo().equals("Proveedor")){

						campanaA.setFamilias(this.catalogoProveedorDAO.getListaConfiguracionPrecioProveedor(idProveedor));
					}

					else if(campanaA.getTipo().equals("Familia")){

						campanaA.setFamilias(this.catalogoProveedorDAO.obtenerInformacionDeCampanaComercialTipoFamilia(campanaA.getId_Camapana()));
					}
					else if(campanaA.getTipo().equals("Clasificación")){

						campanaA.setClasificaciones(this.catalogoProveedorDAO.obtenerInformacionDeCampanaTipoClasificacion(campanaA.getId_Camapana()));
					}
					else if(campanaA.getTipo().equals("Producto")){
						campanaA.setProductos(this.catalogoProveedorDAO.obtenerInformacionDeCampañaTipoProducto(campanaA.getId_Camapana()));

					}


				}
			
	
			
            return campanasCo;

		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new ArrayList<Campana>();
		}
	}
	
	
	
	@Override
	public List<Producto> obtenerProductosConCampana(Long idCliente,String nivel) throws ProquifaNetException {
		try {
			
			String productos = "";
			productos = this.catalogoProveedorDAO.obtenerProductosConCampana(0L);
			List<Producto> list = new ArrayList<Producto>();
			if(productos != null && productos.length() > 0)
			{							
				list =	configurarElementos(catalogoClienteDAO.findConfiguracionPrecioProductoClientePorConfiguracion(0L,idCliente, nivel, 0L,productos,0L,"" ),true,true);
			}
						
            return list;

		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return null;
		}
	}
	
	
	private List<Producto> configurarElementos(
			List<ConfiguracionPrecioProducto> elementos, Boolean descripcion, Boolean precioU) throws ProquifaNetException {
		try{
			
			List<Producto> listProducto = new ArrayList<Producto>();
			Producto auxProducto = new Producto();
			double costoEnDorlar = 0;
			double costoMinEnDolar = 0;
			for (ConfiguracionPrecioProducto elemento : elementos) {
				//Colocar descripcion de producto
				if(descripcion){
					Producto producto = new Producto();
					String fam = "";
					auxProducto = new Producto();
					costoEnDorlar = 0;
					costoMinEnDolar = 0;
					producto.setIdProducto(elemento.getProducto().getIdProducto());
					producto.setTipo(elemento.getProducto().getTipo());
					producto.setSubtipo(elemento.getProducto().getSubtipo());
					producto.setControl(elemento.getProducto().getControl());
					producto.setCosto(elemento.getProducto().getCosto());
					producto.setMoneda(elemento.getProducto().getMoneda());
					producto.setCodigo(elemento.getProducto().getCodigo());
					producto.setFabrica(elemento.getProducto().getFabrica());
					producto.setCategoriaPrecioLista(elemento.getProducto()
							.getCategoriaPrecioLista());
					producto.setConfiguracion_Precio(elemento.getProducto()
							.getConfiguracion_Precio());
					producto.setIndustria(elemento.getProducto().getIndustria());
					producto.setLicencia(elemento.getProducto().getLicencia());
					producto.setLote(elemento.getProducto().getLote());
					producto.setCas(elemento.getProducto().getCas());
					producto.setTiempoEntregaRuta(elemento.getTiempoEntregaRuta());
					producto.setRutaCli(elemento.getRutaCliente());
					producto.setIdFabricante(elemento.getProducto().getIdFabricante());
					producto.setPdolar(elemento.getProducto().getPdolar());
					producto.setEdolar(elemento.getProducto().getEdolar());
					producto.setProveedor(elemento.getProducto().getProveedor());
					producto.setTipoPresentacion(elemento.getProducto().getTipoPresentacion());
					producto.setOrigen(elemento.getProducto().getOrigen());

					if(producto.getTipo() != null && !producto.getTipo().equals("")){
						fam = producto.getTipo();

						if(!producto.getSubtipo().equals(null) && !producto.getSubtipo().equals("")){

							fam = fam + " · " + producto.getSubtipo();

						}

						if(!producto.getControl().equals(null) && !producto.getControl().equals("")){

							fam = fam + " · " + producto.getControl();

						}

					}
					
					producto.setFamiliaString(fam);

					producto.setConcepto(this.productoDAO.obtenerDescripcionProductoPorId(elemento.getProducto().getIdProducto()) );
					producto.setNumStock(this.productoDAO.obtenerProductosEnStock(producto.getCodigo(), producto.getFabrica()));

					elemento.setProducto(producto);
					auxProducto = producto;
				}
				//Calcular Precio Unitario
				if(precioU){

					Double costoFijo = elemento.getCostoFactorProducto().getFactorCostoFijo() / 100;
					Double factorUtil = elemento.getCostoFactorProducto().getFactorCliente() / 100;
					Double costoVentaUnitario = (elemento.getCostoFactorProducto().getCvCliente()) / elemento.getCostoFactorProducto().getCantidadCliente();
					Double costoFijoTotal = 0.00;
					Double utilidadTotal = 0.00;
					

					if(elemento.getCompuestaCostoF()){
						costoFijoTotal = (costoFijo * elemento.getCostoFactorProducto().getCvCliente()) / elemento.getCostoFactorProducto().getCantidadCliente();
					}else{
						costoFijoTotal = (costoFijo * elemento.getCostoFactorProducto().getVaCliente()) / elemento.getCostoFactorProducto().getCantidadCliente();
					}

					if(elemento.getCompuestaFactorU()){
						utilidadTotal = (factorUtil * elemento.getCostoFactorProducto().getCvCliente()) / elemento.getCostoFactorProducto().getCantidadCliente();
					}else{
						utilidadTotal = (factorUtil * elemento.getCostoFactorProducto().getVaCliente()) / elemento.getCostoFactorProducto().getCantidadCliente();
					}

					Double precioUCliente = utilidadTotal + costoFijoTotal + costoVentaUnitario;
					Double precioMin = costoFijoTotal + costoVentaUnitario;

					elemento.getCostoFactorProducto().setPrecioUCliente((double) Math.round(precioUCliente));
					elemento.getCostoFactorProducto().setCostoMinFijo((double) Math.round(precioMin));
					

					if(elemento.getCostoFactorProducto().getDiferencialCliente() == 0){
						elemento.getCostoFactorProducto().setDiferencialCliente(1.0);
					}

					elemento.getCostoFactorProducto().setDiferencialCliente	(Math.rint(((((elemento.getCostoFactorProducto().getPrecioUCliente()) / (elemento.getCostoFactorProducto().getDiferencialCliente())) * 100) - 100) * 1e2) / 1e2 );
				}

				//Calcular Precio Unitario Temporal
				if(elemento.getCostoFactorProducto().getCaduca() != null){

					Double costoFijo = elemento.getCostoFactorProducto().getFactorTempCostoFijo() / 100;
					Double factorUtil = elemento.getCostoFactorProducto().getFactorTempCliente() / 100;
					Double costoVentaUnitario = (elemento.getCostoFactorProducto().getCvCliente()) / elemento.getCostoFactorProducto().getCantidadCliente();
					Double costoFijoTotal = 0.00;
					Double utilidadTotal = 0.00;

					if(elemento.getCompuestaCostoFTemporal()){
						costoFijoTotal = (costoFijo * elemento.getCostoFactorProducto().getCvCliente()) / elemento.getCostoFactorProducto().getCantidadCliente();
					}else{
						costoFijoTotal = (costoFijo * elemento.getCostoFactorProducto().getVaCliente()) / elemento.getCostoFactorProducto().getCantidadCliente();
					}

					if(elemento.getCompuestaFactorUTemporal()){
						utilidadTotal = (factorUtil * elemento.getCostoFactorProducto().getCvCliente()) / elemento.getCostoFactorProducto().getCantidadCliente();
					}else{
						utilidadTotal = (factorUtil * elemento.getCostoFactorProducto().getVaCliente()) / elemento.getCostoFactorProducto().getCantidadCliente();
					}

					Double precioUCliente = utilidadTotal + costoFijoTotal + costoVentaUnitario;
					Double precioMinTem = costoFijoTotal + costoVentaUnitario;
					
					
					elemento.getCostoFactorProducto().setPrecioUTemporalCliente((double) Math.round(precioUCliente));
					elemento.getCostoFactorProducto().setCostoMinTemporal((double) Math.round(precioMinTem));
					if(elemento.getCostoFactorProducto().getDiferencialTemporalCliente() == null || elemento.getCostoFactorProducto().getDiferencialTemporalCliente() == 0){
						elemento.getCostoFactorProducto().setDiferencialTemporalCliente(1.0);
					}

					elemento.getCostoFactorProducto().setDiferencialTemporalCliente	(Math.rint(((((elemento.getCostoFactorProducto().getPrecioUTemporalCliente()) / (elemento.getCostoFactorProducto().getDiferencialTemporalCliente())) * 100) - 100) * 1e2) / 1e2 );
				
				}
				
				if (elemento.getCostoFactorProducto().getPrecioUTemporalCliente() != null && elemento.getCostoFactorProducto().getPrecioUTemporalCliente() > 0) {
					auxProducto.setCostoString(funcion.formatoMoneda(elemento.getCostoFactorProducto().getPrecioUTemporalCliente().doubleValue()));
					if(auxProducto.getMoneda().equalsIgnoreCase("pesos"))
					{
						costoEnDorlar = (double) Math.round( elemento.getCostoFactorProducto().getPrecioUTemporalCliente() / elemento.getProducto().getPdolar());
						costoMinEnDolar = (double) Math.round(elemento.getCostoFactorProducto().getCostoMinTemporal() / elemento.getProducto().getPdolar());
					}
					else{
						costoEnDorlar = elemento.getCostoFactorProducto().getPrecioUTemporalCliente().doubleValue();
						costoMinEnDolar = elemento.getCostoFactorProducto().getCostoMinTemporal();
					}
					
					auxProducto.setCosto(costoEnDorlar); 
					auxProducto.setCostoMinimo(costoMinEnDolar);
//					auxProducto.setCostoMinimoMX((double) Math.round(auxProducto.getCostoMin() * elemento.getProducto().getPdolar()));
//					auxProducto.setCostoMinimoEuro((double) Math.round(auxProducto.getCostoMin() / elemento.getProducto().getEdolar()));
				}
				else {
					auxProducto.setCostoString(funcion.formatoMoneda(elemento.getCostoFactorProducto().getPrecioUCliente().doubleValue()));
					if(auxProducto.getMoneda().equalsIgnoreCase("pesos"))
					{
						costoEnDorlar = (double) Math.round(elemento.getCostoFactorProducto().getPrecioUCliente() / elemento.getProducto().getPdolar());
						costoMinEnDolar = (double) Math.round(elemento.getCostoFactorProducto().getCostoMinFijo() / elemento.getProducto().getPdolar());
					}
					else {
						costoEnDorlar = elemento.getCostoFactorProducto().getPrecioUCliente().doubleValue();
						costoMinEnDolar = elemento.getCostoFactorProducto().getCostoMinFijo();
						
					}
					auxProducto.setCosto(costoEnDorlar);
					auxProducto.setCostoAux(costoEnDorlar);
					auxProducto.setCostoMinimo(costoMinEnDolar);
//					auxProducto.setCostoMinimoMX((double) Math.round(auxProducto.getCostoMin() * elemento.getProducto().getPdolar()));
//					auxProducto.setCostoMinimoEuro((double) Math.round(auxProducto.getCostoMin() / elemento.getProducto().getEdolar()));
				}	
				
				listProducto.add(auxProducto);
			}
			
			return listProducto;
		}catch(Exception e){
			log.info(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"descripcion: " + descripcion, "precioU: " + precioU);
			return null;

		}
	}
	
	
	@Override
	public List<Campana>obtenerCampanasConTodosSusProductos(Long idCliente,String nivelCliente) throws ProquifaNetException {
		try {
			
			List<Campana> campanasCo = new ArrayList<Campana>();
			
			campanasCo = this.catalogoProveedorDAO.obtenerCampanasDeProveedor(0L);
			
				for (Campana campanaA : campanasCo) {
					String productos = this.catalogoProveedorDAO.obtenerProductosConCampana(campanaA.getId_Camapana());
					List<Producto> list = null;
					list =	configurarElementos(catalogoClienteDAO.findConfiguracionPrecioProductoClientePorConfiguracion(0L,idCliente, nivelCliente, 0L,productos,0L,"" ),true,true);
					campanaA.setProductos(list);
				}
			
            return campanasCo;

		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new ArrayList<Campana>();
		}
	}
	
	
	@Override
	public List<Empresa> obtenerListaEmpresasParaCotizar()
			throws ProquifaNetException {
		try {
			return this.catalogoProveedorDAO.obtenerEmpresasParaCotizar();
		} catch (Exception e) {
			funcion.enviarCorreoAvisoExepcion(e);
			return new ArrayList<Empresa>();
		}
	}
	
	@Override
	public Producto obtenerPrecioMinYMaximoDeProducto(
			Long idCliente,String nivel,String producto) throws ProquifaNetException {
		try {
			
			Producto pro = new Producto();
			List<Producto> list = null;
			list =	configurarElementos(catalogoClienteDAO.findConfiguracionPrecioProductoClientePorConfiguracion(0L,idCliente, nivel, 0L,producto,0L,"" ),true,true);
			
			pro = list.get(0);
            return pro;
		} catch (Exception e) {
			
			return null;
		}
	}

}
