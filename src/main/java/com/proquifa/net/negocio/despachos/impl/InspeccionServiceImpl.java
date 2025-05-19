package com.proquifa.net.negocio.despachos.impl;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
//import com.jayway.jsonpath.TypeRef;
import com.proquifa.net.modelo.cobrosypagos.facturista.PartidaFactura;
import com.proquifa.net.modelo.compras.PartidaCompra;
import com.proquifa.net.modelo.comun.Archivo;
import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.comun.Pendiente;
import com.proquifa.net.modelo.comun.Producto;
import com.proquifa.net.modelo.comun.ValorCombo;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.BodyFileEmail;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.despachos.ContadorPiezasXPrioridad;
import com.proquifa.net.modelo.despachos.PartidaInspeccion;
import com.proquifa.net.modelo.despachos.etiquetasYfolios;
import com.proquifa.net.modelo.despachos.parametrosInspeccion;
import com.proquifa.net.modelo.despachos.totalesInspeccionProducto;
import com.proquifa.net.modelo.ventas.PartidaPedido;
import com.proquifa.net.modelo.ventas.admoncomunicacion.Correo;
import com.proquifa.net.negocio.despachos.InspeccionService;
import com.proquifa.net.persistencia.cobrosypagos.facturista.PfacturasDAO;
import com.proquifa.net.persistencia.compras.PartidaCompraDAO;
import com.proquifa.net.persistencia.comun.ComunDAO;
import com.proquifa.net.persistencia.comun.EmpleadoDAO;
import com.proquifa.net.persistencia.comun.FolioDAO;
import com.proquifa.net.persistencia.comun.PendienteDAO;
import com.proquifa.net.persistencia.comun.ProductoDAO;
import com.proquifa.net.persistencia.comun.ValorComboDAO;
import com.proquifa.net.persistencia.despachos.InspeccionDAO;
import com.proquifa.net.persistencia.ventas.PartidaPedidoDAO;

@Service("inspeccionPHService")
public class InspeccionServiceImpl implements InspeccionService {

	final Logger log = LoggerFactory.getLogger(InspeccionServiceImpl.class);
	
	@Autowired
	InspeccionDAO inspeccionDAO;
	
//	@Autowired
//	PendienteDAO pendienDAO;
	
	@Autowired
	FolioDAO folioDAO;
	
	@Autowired
	PartidaCompraDAO partidaCompraDAO;
	
	@Autowired
	PartidaPedidoDAO partidaPedidoDAO;
	
	@Autowired
	PfacturasDAO pfacturasDAO;
	
	@Autowired
	EmpleadoDAO empleadoDAO;
	
	@Autowired
	ProductoDAO productoDAO;
	@Autowired 
	ValorComboDAO valorComboDAO;
	
	@Autowired
	PendienteDAO pendienteDAO;
	
	@Autowired
	ComunDAO comunDAO;
	
	Funcion util = new Funcion();
//	private final static Logger logger = InspeccionServiceImpl.class
	private SimpleDateFormat formatterSinTiempo = new SimpleDateFormat("yyyyMMdd");

	private Archivo tmp;
//	
	@Override
	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public Map<String, Object> consultaPartidasInspeccion() throws ProquifaNetException {
		try {
			List<PartidaInspeccion> partInp = inspeccionDAO.obtenerPartidaInspeccion();
			Map<String, Object> datosPartidas = new HashMap<String, Object>();
			datosPartidas.put("listPart", partInp);
			datosPartidas.put("totP1", inspeccionDAO.consultaPrioridad1());
		 return datosPartidas;
			
		}
		catch (Exception e) {
//			logger.error(e.getMessage());	
			return null;
		}
	}
	
	
	
	public Integer contadorSeccionArribados() {
		try {
			return inspeccionDAO.obtenerContadorPartidasEnInspeccion();	
			
		}
		catch (Exception e) {
//			logger.error(e.getMessage());	
			return null;
		}
	}
	

	@Transactional(transactionManager = "ds1TransactionManager")
	public String obtenerConsecutivoDeLoteInspeccion(String concepto) throws ProquifaNetException {
		try {
			String folio = "";
			log.info("Entro a obtenerConsecutivoDeLoteInspeccion");
			folio = folioDAO.obtenerFolioPorConcepto(concepto, true).getFolioCompleto();	
			return folio;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@Override
	@Transactional(transactionManager = "ds1TransactionManager")
	public Object finalizarInspeccionDePartidas(parametrosInspeccion param) throws ProquifaNetException {
		try {
			log.info("entre service finalizarInspeccionDePartidas ");
			log.info("IDPCOMPRA::::::::::: " + param.getIdCompra());
			etiquetasYfolios listEylistF = new etiquetasYfolios();
			List<byte[]> lstEtiquetas = new ArrayList<byte[]>();	
			List<String> lstFolios = new ArrayList<String>();	
			List<PartidaInspeccion> lstRechazos = new ArrayList<PartidaInspeccion>();	
			List<PartidaInspeccion> listaAuxRechazos = new ArrayList<PartidaInspeccion>();	
			  
			PartidaInspeccion partinsOC = new PartidaInspeccion();
			partinsOC = this.inspeccionDAO.obtenerInspeccionOCxIdCompra(param.getIdCompra());

			PartidaCompra partComOriginal= new PartidaCompra();
			PartidaCompra nuevaPartCom = new PartidaCompra();
			PartidaFactura pf = new PartidaFactura();
			Integer maxPCompra = 0;
			long idPartidaNew = 0L;
			Date fechaNewPartida = null;

			PartidaPedido pPOriginal = new PartidaPedido();
			PartidaPedido pPNuevo = new PartidaPedido();
			Integer maxPPedido = 0;
			long idPpedidoaNew = 0L;
			Date fechaNewPpedido = null;
			  
			Boolean seDividioTiempo = false;
			Long tiempoXpieza = 0L;
			Date fechaFin = new Date();
			Date tiempoInspeccion = new Date();
			Calendar dateInicio = Calendar.getInstance();
			dateInicio.setTime(param.getFechaInicio());
			 
			Calendar dateFin = Calendar.getInstance();
			dateFin.setTime(fechaFin);

			dateFin.add(Calendar.SECOND, (dateInicio.get(Calendar.SECOND) * -1));
			dateFin.add(Calendar.MINUTE, (dateInicio.get(Calendar.MINUTE) * -1));
			dateFin.add(Calendar.HOUR_OF_DAY, (dateInicio.get(Calendar.HOUR_OF_DAY) * -1));

			tiempoInspeccion = dateFin.getTime();                          
           
//	             Empleado empleado = new Empleado();
			Empleado empleado = this.empleadoDAO.obtenerEmpleadoPorUsuario(param.getUsuario());  
			param.setIdUsuario(empleado.getClave());
			List<PartidaInspeccion> partidasAenviar = new ArrayList<PartidaInspeccion>();	
			PartidaInspeccion parIns = new PartidaInspeccion();


			Integer cantPiezas = 0;
			Integer cantPiezasTot = 0;
			partComOriginal = partidaCompraDAO.getPCompraXid(param.getIdCompra());
			fechaNewPartida = this.inspeccionDAO.obtenerFechaInicioDelEstadoPCompra(param.getIdCompra());

			pPOriginal = this.partidaPedidoDAO.getPPedidoXid(param.getIdPPedido(),true,true); 
			fechaNewPpedido = this.inspeccionDAO.obtenerFechaInicioDelEstadoPpedido(param.getIdPPedido());

			cantPiezas = partComOriginal.getCantidadCompra();
			cantPiezasTot = partComOriginal.getCantidadCompra();
			String lote = param.getListaPzas().get(0).getLoteOriginal(); //obtiene lote de lista de piezas
			String manejoT = param.getListaPzas().get(0).getManejoTransporte(); //obtiene manejo de lista de piezas
			String OC = param.getListaPzas().get(0).getCompra(); //obtiene Orden de compra de lista de piezas
			log.info("Compra:" + OC);
			
			Integer stock = this.inspeccionDAO.consultarStock( param.getIdPPedido());	
			//		se crea la partida inspeccion con respecto a la original
			String cliente;
			String control;
			String tipo;
			cliente = param.getListaPzas().get(0).getCliente();
			control = param.getListaPzas().get(0).getControl();
			tipo = param.getListaPzas().get(0).getTipo();
			//  if(numRechazos >0){
			boolean forRechazo = false;
		
				  if((param.isAplicaDocumentacion() && param.getDocumentos() != null && !param.getDocumentos().equals("") || !param.isAplicaDocumentacion())  && param.getNumRechazos() > 0)
				  {		// entro con almenos un rechazo
					  Integer indice = 0;
					  forRechazo = true;
					  if(cantPiezas > param.getNumRechazos()) {
						  indice = 0;
					  } else {
						  indice = 1;
					  }
					  for (int i = 0; i < param.getListaPzas().size(); i++) {
						  if(!param.getListaPzas().get(i).getDespachable()) // si  despachable == false
						  {	
							  lstRechazos.add(param.getListaPzas().get(i)); //agregan a lstRechazos pieza en posicion i
							  param.getListaPzas().remove(i--);  //elimina la pieza con rechazo de lstPart
						  }

					  }
					  seDividioTiempo = true;
					  //	                se divide el tiempo de la inspeccion por pieza
					  tiempoXpieza = calcularTiempo(tiempoInspeccion,cantPiezas,partinsOC,2L);
					  log.info("Tiempo por pieza: 2L "+ tiempoXpieza);
					  nuevaPartCom = this.partidaCompraDAO.copiarPCompra(partComOriginal);
					  pPNuevo = this.partidaPedidoDAO.copiarPPedido(pPOriginal);
					  pf = this.pfacturasDAO.getPFacturaXCPedido(pPOriginal.getCpedido(), pPOriginal.getPart());

					  //Se insertan todas las PCompras de rechazo
					  for (int i = 0; i < param.getNumRechazos() - indice; i++) {

						  parIns = new PartidaInspeccion();
						  idPartidaNew = 0L;
						  listaAuxRechazos = new ArrayList<PartidaInspeccion>();	
						  parIns.setCliente(cliente);
						  parIns.setTipo(tipo);
						  parIns.setControl(control);
						  parIns.setInspector(empleado.getNombre());
						  parIns.setEstado("No Despachable");
						  parIns.setFechaString(param.getFechaInspeccion());

						  maxPPedido = this.partidaPedidoDAO.getMaxPPedido(pPOriginal.getCpedido());  // Obtiene la partida maxima del pedido relacionado
						  pPNuevo.setPart(maxPPedido);
						  //pPNuevo.setEstado("En inspección");
						  pPNuevo.setEstado("Rechazada");
						  pPNuevo.setCantidadPedida(1);
						  pPNuevo.setFaltan(0);
						  idPpedidoaNew = this.partidaPedidoDAO.insertarPPedido(pPNuevo);
						  log.info("",idPpedidoaNew);
						  //this.inspeccionDAO.insertarEstadoPPedido(idPpedidoaNew, "En inspección", fechaNewPpedido);
						  if (fechaNewPpedido == null)
							  fechaNewPpedido = new Date();
						  this.inspeccionDAO.insertarEstadoPPedido(idPpedidoaNew, "Rechazada", fechaNewPpedido);

						  maxPCompra = partidaCompraDAO.getMaxPcompra(partComOriginal.getCompra()); 	 // Obtiene la partida maxima de la compra
						  nuevaPartCom.setPartida(maxPCompra);
						  nuevaPartCom.setPartidaPedido(maxPPedido);
						  nuevaPartCom.setCantidadCompra(1);
						  nuevaPartCom.setEstado("Rechazada"); 
						  nuevaPartCom.setIdPpedido(idPpedidoaNew);
						  nuevaPartCom.setCodQrBolsa(param.getQrBolsaIncidencia());
						  //nuevaPartCom.setEstado("En inspección");


						  if(param.getUbicacion().equalsIgnoreCase("Refrigeración") || param.getUbicacion().equalsIgnoreCase("Refrigeracion") )
						  { 
							  parIns.setManejo("Refrigeración");
							  parIns.setUbicacion("Refrigerador_1G");
						  }
						  else if(param.getUbicacion().equalsIgnoreCase("Congelación") || param.getUbicacion().equalsIgnoreCase("Congelacion")) {
							  nuevaPartCom.setAnaquelLetra("C");
							  nuevaPartCom.setAnaquelLetra(null);
							  parIns.setManejo("Congelador");
							  parIns.setUbicacion("Congelador_C");
						  }
						  else if(param.getUbicacion().equalsIgnoreCase("Ambiente")) {
							  parIns.setManejo("Ambiente");
							  parIns.setUbicacion("Anaquel_8A");
						  }

						  parIns.setCodBarras("FD-"+ nuevaPartCom.getPedido()+"-" + nuevaPartCom.getPartidaPedido());
						  partidasAenviar.add(parIns);
						  lstFolios.add( parIns.getCodBarras());
						  nuevaPartCom.setUbicacion(lstRechazos.get(i).getUbicacion()); // Se agrego para asignar la ubicacion a cada uno de los no despachables
						  idPartidaNew = this.partidaCompraDAO.insertarPCompra(nuevaPartCom); // inserta nueva partida en pcompras retornando idPcompra

						  this.partidaPedidoDAO.verificarFactura(pPNuevo, param.getIdCompra(), stock); 

						  log.info("",idPartidaNew);
						  //						 Se cambia el idPcompra por el nuevo idPCompra  y se inserta la pieza no despachable
						  lstRechazos.get(i).setIdCompra(idPartidaNew);
						  listaAuxRechazos.add(lstRechazos.get(i));
						  inspeccionDAO.insertaPiezasInspeccionadas(listaAuxRechazos); 

						  // this.inspeccionDAO.insertarEstadoPCompraDeInspeccion(idPartidaNew,"En inspección", fechaNewPartida);
						  this.inspeccionDAO.insertarEstadoPCompraDeInspeccion(idPartidaNew,"Rechazada", fechaNewPartida);
						  if (partinsOC == null)
							  partinsOC = new PartidaInspeccion();
						  partinsOC.setIdCompra(idPartidaNew);
						  partinsOC.setManejo(param.getUbicacion());
						  partinsOC.setLoteOriginal(lote);
						  partinsOC.setManejoTransporte(manejoT);
						  partinsOC.setFechaFinInspeccion(fechaFin);
						  partinsOC.setCantPiezas(1L);
						  partinsOC.setTiempoInspeccionEnSegundos(tiempoXpieza);
						  partinsOC.setVideoPartida(param.getVideoPartida());
						  partinsOC.setDocumento(param.getDocumento());
						  partinsOC.setDocumentoSDS(param.getDocumentosSDS());
						  partinsOC.setPrioridad(param.getPrioridad());
						  partinsOC.setAplicaDocumentacion(param.isAplicaDocumentacion());
						  partinsOC.setLote(param.getLote());
						  partinsOC.setIdioma(param.getIdioma());
						  partinsOC.setEdicion(param.getEdicion());
						  String nombre = param.getNombreImagenesRechazo().get(0);
						  partinsOC.setImagenRechazo(nombre);
						  partinsOC.setInspector(param.getUsuario());
						  partinsOC.setManejoTransporte(param.getManejoTransporte());
						  partinsOC.setCantPiezas((long) 1);
						  partinsOC.setIdMarca(empleado.getClave());
						  String comentario = null;
						  if(param.getComentarios() != null && param.getComentarios().size()>0) {
							  comentario = param.getComentarios().get(0);
							  param.getComentarios().remove(0);
						  } 
						  partinsOC.setComentarios(comentario);
						  this.inspeccionDAO.insertarInspeccionOC(partinsOC, "InspeccionOC"); //inserta partida en IspeccionOC retorna pInspeccion
						  param.getNombreImagenesRechazo().remove(0);
						  if(pf!=null){
							  Integer pfacSiguiente = this.pfacturasDAO.getMaxPFacturas(pf.getFactura(),pf.getFpor());
							  pf.setCantidad(param.getNumRechazos());
							  pf.setPartidaFactura(pfacSiguiente);
							  pf.setPpedido(maxPPedido);
							  //								this.pfacturasDAO.insertarPFacturas(pf);

						  }

						  PartidaInspeccion rechazo = lstRechazos.get(i);

						  if (!rechazo.getDocumentacionCorrecta()) {
							  Pendiente pendiente = new Pendiente(null, "Rechazo por documentacion", String.valueOf(idPartidaNew), new Date(), empleadoDAO.getCompradorporProveedor(param.getIdProveedor().longValue()), null);
							  pendienteDAO.guardarPendiente_angular(pendiente);
						  }
						  if (!rechazo.getCaducidadCorrecta() || !rechazo.getCatCorrecto() || !rechazo.getDescripcionCorrecta()
								  || !rechazo.getEdicionCorrecta() || !rechazo.getFisicamenteCorrecto() || !rechazo.getLoteCorrecto()
								  || !rechazo.getPresentacionCorrecta()) {

							  Pendiente pendiente = new Pendiente(comunDAO.obtenerUltimoRegistroInsertado("Pieza").toString(), "Rechazo por inspeccion", String.valueOf(idPartidaNew), new Date(), "PLozada", null);
							  pendienteDAO.guardarPendiente_angular(pendiente);
						  }

					  }

					  if(indice == 1) {
						  //					  Modificaciones para la que queda 
						  this.partidaPedidoDAO.actualizarPFactura(param.getIdCompra(), 1);
						  lstFolios.add(parIns.getCodBarras());
						  //						  Insertar la pieza faltante 
						  listaAuxRechazos = new ArrayList<PartidaInspeccion>();	
						  listaAuxRechazos.add(lstRechazos.get(param.getNumRechazos()-1));
						  inspeccionDAO.insertaPiezasInspeccionadas(listaAuxRechazos); 
						  this.actualizarPiezas(partComOriginal, param, 1, pPOriginal, seDividioTiempo, partinsOC, tiempoInspeccion);

						  /// Crear pedientes de rechazo
						  for (PartidaInspeccion rechazo : lstRechazos) {
							  inspeccionDAO.insertaPiezas(rechazo);

							  if (!rechazo.getDocumentacionCorrecta()) {
								  Pendiente pendiente = new Pendiente(null, "Rechazo por documentacion", String.valueOf(param.getIdCompra()), new Date(), empleadoDAO.getCompradorporProveedor(param.getIdProveedor().longValue()), null);
								  pendienteDAO.guardarPendiente_angular(pendiente);
							  }
							  if (!rechazo.getCaducidadCorrecta() || !rechazo.getCatCorrecto() || !rechazo.getDescripcionCorrecta()
									  || !rechazo.getEdicionCorrecta() || !rechazo.getFisicamenteCorrecto() || !rechazo.getLoteCorrecto()
									  || !rechazo.getPresentacionCorrecta()) {

								  Pendiente pendiente = new Pendiente(comunDAO.obtenerUltimoRegistroInsertado("Pieza").toString(), "Rechazo por inspeccion", String.valueOf(param.getIdCompra()), new Date(), "PLozada", null);
								  pendienteDAO.guardarPendiente_angular(pendiente);
							  }

						  }

					  }
					  //					  
					  cantPiezas = cantPiezas - param.getNumRechazos();

					  if(cantPiezas > 0) {
						  //						 if(pf!=null){
						  //							 pf.setCantidad(cantPiezas);
						  //								this.pfacturasDAO.actualizarPFacturas(pf);
						  //						 }
						  this.partidaPedidoDAO.actualizarPFactura(param.getIdCompra(), cantPiezas);
					  }




				  }
//////////////////// Actualizar, hacer el registro de las piezas no despachables. ///
				  
				  Integer cantRestantes = cantPiezasTot -  (param.getNumRechazos() + param.getCantDespachables());	
				  for (int i = 0; i < cantRestantes; i++) {
					  param.getListaPzas().remove(0);  //elimina la piezas restantes	
				  }	  
				  Integer cantDesp = cantPiezasTot - param.getNumRechazos();
		
		//
				  if (cantRestantes > 0) {
					  if(param.getCantDespachables() > 0) {
						  this.inspeccionDAO.insertarRestantes(cantRestantes, param.getIdPPedido(), param.getIdCompra());
					  } else {
						  this.inspeccionDAO.actualizarRestantes(cantRestantes, param.getIdPPedido(), param.getIdCompra());
					  }
				  }
				  ////		
				  if(param.getCantDespachables() > 0 && stock == 1) {
					  String nombreRechazada = "";
					  if (!forRechazo &&  param.getNumRechazos() > 0) {
						  nombreRechazada = param.getNombreImagenesRechazo().get(0);
					  }
					  this.inspeccionDAO.actualizarPCompraStock(param); 
					  this.inspeccionDAO.actualizarInspeccionOCPorInspeccion(param.getUbicacion(), param.getLote(), param.getUsuario(), manejoT,cantPiezas.longValue(),tiempoXpieza,param.getIdCompra(),param.getVideoPartida(), param.getDocumentos(), param.getDocumentosSDS(), param.getPrioridad(), param.isAplicaDocumentacion(),nombreRechazada,param.getIdioma(), param.getEdicion(), null);  //tabla inspeccionOC
					  this.inspeccionDAO.insertarStock(param.getCantDespachables(),param.getIdPPedido(), param.getIdCompra());
					  this.inspeccionDAO.insertarPiezasStock(param.getIdCompra(), param.getCantDespachables(), param.getListaPzas());

					  //Se retorna la lista de partidas para que sean impresas.
					  if(param.getCantDespachables() > 1) {
						  return inspeccionDAO.obtenerPartidasInsertadasStock(param.getCantDespachables(), param.getIdCompra().intValue(), pPOriginal.getCpedido());
					  }
				  } else {
					  ///////// 
					  parIns = new PartidaInspeccion();
					  parIns.setCliente(cliente);
					  parIns.setTipo(tipo);
					  parIns.setControl(control);
					  parIns.setCodBarras("FD-"+ partComOriginal.getPedido()+"-" + partComOriginal.getPartidaPedido());
					  lstFolios.add(parIns.getCodBarras());
					  parIns.setFechaString(param.getFechaInspeccion());
					  parIns.setInspector(param.getUsuario());
					  parIns.setVideoPartida(param.getVideoPartida());
					  parIns.setDocumento(param.getDocumentos());
					  parIns.setDocumentoSDS(param.getDocumentosSDS());
					  parIns.setPrioridad(param.getPrioridad());



					  if((param.isAplicaDocumentacion() && (param.getDocumentos() == null || param.getDocumentos().equals(""))))
					  {


						  parIns.setManejo(param.getUbicacion());
						  parIns.setUbicacion(param.getUbicacionAsignada());
						  parIns.setEstado("No Despachable");
						  partComOriginal.setEstado("Rechazada");
						  partComOriginal.setUbicacion(param.getUbicacionAsignada()); // Se agrego para asignar ubicacion de no despachables	
						  //se actualizar la partidaCompra
						  partComOriginal.setCantidadCompra(cantPiezas);
						  partComOriginal.setFolioInspeccion(this.inspeccionDAO.obtenerFolioDeInspecionPoridCompra(param.getIdCompra()));
						  partComOriginal.setCodQrBolsa(param.getQrBolsaIncidencia());
						  this.partidaCompraDAO.actualizarPCompra(partComOriginal); 

						  //			// se actualiza el partida pedido
						  pPOriginal.setCantidadPedida(cantPiezas);
						  pPOriginal.setEstado("Rechazada"); //tabla ppedidos
						  this.partidaPedidoDAO.actualizarPPedido(pPOriginal); 

						  //			// se actualiza fecha fin estadoPCompra
						  this.inspeccionDAO.actualizarEstadoPCompra(param.getIdCompra()); 

						  //			// se actualiza fecha fin del estadoPPedido
						  this.inspeccionDAO.actualizarEstadoPPedido(param.getIdPPedido()); 

						  //			// se actualiza INSPECCIONOC
						  if(seDividioTiempo)
							  tiempoXpieza = calcularTiempo(tiempoInspeccion,cantPiezas,partinsOC,1L);
						  else
							  tiempoXpieza = calcularTiempo(tiempoInspeccion,cantPiezas,partinsOC,0L);

						  String nombreRechazada = "";
						  if ( param.isAplicaDocumentacion() && param.getDocumentos() != null && !param.getDocumentos().equals("") ) {
							  nombreRechazada = param.getNombreImagenesRechazo().get(0);
						  }

						  if (inspeccionDAO.existePartidaInspeccionOC(param.getIdCompra().intValue()))
							  this.inspeccionDAO.actualizarInspeccionOCPorInspeccion(param.getUbicacion(), param.getLote(), param.getUsuario(), manejoT,cantPiezas.longValue(),tiempoXpieza,param.getIdCompra(),param.getVideoPartida(), param.getDocumentos(), param.getDocumentosSDS(), param.getPrioridad(), param.isAplicaDocumentacion(),nombreRechazada,param.getIdioma(), param.getEdicion(), null);  //tabla inspeccionOC
						  else {
							  param.setPiezas(cantPiezas);
							  param.setTiempo(tiempoXpieza.intValue());
							  param.setImagenRechazo(nombreRechazada);
							  this.inspeccionDAO.insertarInspeccionOC(param);  //tabla inspeccionOC
						  }



						  partidasAenviar.add(parIns);

						  //			se agrega codigo de la partida original a la lista de folio
						  lstEtiquetas = generarCodigos(partidasAenviar);


						  if ((param.isAplicaDocumentacion() && (param.getDocumentos() == null || param.getDocumentos().equals("")))) {
							  Pendiente pendiente = new Pendiente(null, "Rechazo por documentacion", String.valueOf(param.getIdCompra()), new Date(), empleadoDAO.getCompradorporProveedor(param.getIdProveedor().longValue()), null);
							  pendienteDAO.guardarPendiente_angular(pendiente);
						  } else {
							  //			inserta todas las piezas
							  for (PartidaInspeccion rechazo : param.getListaPzas()) {
								  inspeccionDAO.insertaPiezas(rechazo);

								  if (!rechazo.getDocumentacionCorrecta()) {
									  Pendiente pendiente = new Pendiente(null, "Rechazo por documentacion", String.valueOf(param.getIdCompra()), new Date(), empleadoDAO.getCompradorporProveedor(param.getIdProveedor().longValue()), null);
									  pendienteDAO.guardarPendiente_angular(pendiente);
								  }
								  if (!rechazo.getCaducidadCorrecta() || !rechazo.getCatCorrecto() || !rechazo.getDescripcionCorrecta()
										  || !rechazo.getEdicionCorrecta() || !rechazo.getFisicamenteCorrecto() || !rechazo.getLoteCorrecto()
										  || !rechazo.getPresentacionCorrecta()) {

									  Pendiente pendiente = new Pendiente(comunDAO.obtenerUltimoRegistroInsertado("Pieza").toString(), "Rechazo por inspeccion", String.valueOf(param.getIdCompra()), new Date(), "PLozada", null);
									  pendienteDAO.guardarPendiente_angular(pendiente);
								  }

							  }
						  }

						  if(param.isFinalizarPendiente())
						  {
							  log.info("OC Compra:" + OC);
							  log.info("Actualizo fecha fin pendiente");
							  inspeccionDAO.cerrarPendienteOC(OC);
						  }

						  listEylistF.setEtiquetas(lstEtiquetas);
						  listEylistF.setFolios(lstFolios);
					  }else{
						  if(param.getCantDespachables() > 0) {
							  partComOriginal.setEstado("Recibido");
							  //partComOriginal.setAnaquelNumero(numAnaquel);
							  //partComOriginal.setAnaquelLetra(letraAnaquel);
							  partComOriginal.setUbicacion(param.getUbicacionAsignada());
							  partComOriginal.setCodQrBolsa(param.getCodigoQrBolsa());
							  if(param.getUbicacion().equalsIgnoreCase("Refrigeración") || param.getUbicacion().equalsIgnoreCase("Refrigeracion") )
							  {
								  parIns.setManejo(param.getUbicacion());
								  parIns.setUbicacion(param.getUbicacionAsignada());
							  }
							  else if(param.getUbicacion().equalsIgnoreCase("Congelación") || param.getUbicacion().equalsIgnoreCase("Congelacion")) {
								  parIns.setManejo(param.getUbicacion());
								  parIns.setUbicacion(param.getUbicacionAsignada());
							  }
							  else if(param.getUbicacion().equalsIgnoreCase("Ambiente")) {
								  parIns.setManejo(param.getUbicacion());
								  parIns.setUbicacion(param.getUbicacionAsignada());
							  }

							  parIns.setEstado("Despachable");


							  //			se actualizar la partidaCompra
							  partComOriginal.setCantidadCompra(param.getCantDespachables());
							  partComOriginal.setFolioInspeccion(this.inspeccionDAO.obtenerFolioDeInspecionPoridCompra(param.getIdCompra())); 
							  this.partidaCompraDAO.actualizarPCompra(partComOriginal); 

							  //			se actualiza el partida pedido
							  pPOriginal.setCantidadPedida(param.getCantDespachables());
							  pPOriginal.setEstado("Por embalar");
							  this.partidaPedidoDAO.actualizarPPedido(pPOriginal); 

							  //			se actualiza fecha fin estadoPCompra
							  this.inspeccionDAO.actualizarEstadoPCompra(param.getIdCompra()); 

							  //			se actualiza fecha fin del estadoPPedido
							  this.inspeccionDAO.actualizarEstadoPPedido(param.getIdPPedido()); 

							  //			se actualiza INSPECCIONOC
							  if(seDividioTiempo){
								  tiempoXpieza = calcularTiempo(tiempoInspeccion,param.getCantDespachables(),partinsOC,1L);
								  log.info("tiempo por pieza 1L: " + tiempoXpieza + "cantidad de piezas:" + param.getCantDespachables());}
							  else{
								  tiempoXpieza = calcularTiempo(tiempoInspeccion,param.getCantDespachables(),partinsOC,0L);
								  log.info("tiempo por pieza: 0L " + tiempoXpieza+ "cantidad de piezas:" + param.getCantDespachables());
							  }
							  String nombreRechazada = "";
							  if (!forRechazo &&  param.getNumRechazos() > 0) {
								  nombreRechazada = param.getNombreImagenesRechazo().get(0);
							  }
							  this.inspeccionDAO.actualizarInspeccionOCPorInspeccion(param.getUbicacion(), lote, param.getUsuario(), manejoT,param.getCantDespachables().longValue(),tiempoXpieza,param.getIdCompra(),param.getVideoPartida(), param.getDocumentos(), param.getDocumentosSDS(), param.getPrioridad(), param.isAplicaDocumentacion(), nombreRechazada, param.getIdioma(), param.getEdicion(), null); 


							  if (inspeccionDAO.existePartidaInspeccionOC(param.getIdCompra().intValue()))
								  this.inspeccionDAO.actualizarInspeccionOCPorInspeccion(param.getUbicacion(), param.getLote(), param.getUsuario(), manejoT,cantPiezas.longValue(),tiempoXpieza,param.getIdCompra(),param.getVideoPartida(), param.getDocumentos(), param.getDocumentosSDS(), param.getPrioridad(), param.isAplicaDocumentacion(),nombreRechazada,param.getIdioma(), param.getEdicion(), null);  //tabla inspeccionOC
							  else {
								  param.setPiezas(cantPiezas);
								  param.setTiempo(tiempoXpieza.intValue());
								  param.setImagenRechazo(nombreRechazada);
								  this.inspeccionDAO.insertarInspeccionOC(param);  //tabla inspeccionOC
							  }


							  partidasAenviar.add(parIns);

							  //			se agrega codigo de la partida original a la lista de folio
							  lstEtiquetas = generarCodigos(partidasAenviar);

							  //			inserta todas las piezas
							  inspeccionDAO.insertaPiezasInspeccionadas(param.getListaPzas()); 


							  if(param.isFinalizarPendiente())
							  {
								  log.info("OC Compra:" + OC);
								  log.info("Actualizo fecha fin pendiente");
								  inspeccionDAO.cerrarPendienteOC(OC);
							  }

							  listEylistF.setEtiquetas(lstEtiquetas);
							  listEylistF.setFolios(lstFolios);
						  }
					  }

					  String unidad = param.getUnidadProducto();
					  String cantidadP = param.getCantidadProducto();
					  if(param.getCantDespachables() > 0) {
						  if (param.getIdProducto() != null && param.getIdProducto() > 0 && param.getTipo() != null && !param.getTipo().equalsIgnoreCase("Publicaciones")) {
							  Producto prod = new Producto();
							  StringBuilder cuerpoCorreo = new StringBuilder(" \n");
							  if (param.getCantidadProducto() == null ) {
								  prod.setCantidad(param.getCantidadCambio());
								  cantidadP = "0";
							  }
							  if (param.getUnidadProducto() == null) {
								  prod.setUnidad(param.getUnidadCambio());
								  unidad = "";
							  }
							  prod.setIdProducto(param.getIdProducto().longValue());
							  prod.setManejo(param.getManejo());
							  prod.setManejoTransporte(param.getManejoTransporte());
							  prod.setLote(param.getLote());
							  prod.setTipoPresentacion(param.getTipoPresentacion());
							  productoDAO.actualizarManejoyPresencacionProducto(prod);
							  Funcion funcion = new Funcion();
							  Correo correo = new Correo();

							  if(param.getCantidadProducto() != null || param.getUnidadProducto() != null) {
								  if((param.getCantidadCambio() != null && !param.getCantidadCambio().equalsIgnoreCase(param.getCantidadProducto())) || (param.getUnidadCambio() != null && !param.getUnidadCambio().equalsIgnoreCase(param.getUnidadProducto()))) { 						
									  if(Funcion.PRODUCCION) {
										  correo.setCorreo("content@proquifa.net");  
										  correo.setCocorreo("oscar.cardona@ryndem.mx");
									  } else {
										  correo.setCorreo("oscar.cardona@ryndem.mx");
									  }
									  correo.setArchivoAdjunto("");
									  correo.setAsunto("Cambio de Presentación en Inspección");
									  correo.setOrigen("serviciosti");
									  cuerpoCorreo.append("El producto ").append(param.getCodigoProd()).append("-").append(param.getPresentacionProd()).append("-").append(param.getMarca()).append(" \n");
									  cuerpoCorreo.append("con la presentación en catálogo era: " ).append(cantidadP).append(" ").append(unidad).append(", \n");
									  cuerpoCorreo.append("y durante la inspección se detectó: ");
									  cuerpoCorreo.append(param.getCantidadCambio()).append(" ").append(param.getUnidadCambio()).append(" \n");
									  cuerpoCorreo.append(" \n");
									  cuerpoCorreo.append("“Favor de validar dicha información y si es necesario, aplicar la modificación en el catálogo de productos.”");
									  correo.setCuerpoCorreo(cuerpoCorreo.toString());
									  funcion.enviarCorreo(correo);
								  }

							  }
						  }
					  }


				  }
		
				  if(param.isCargaDoc()) {
					  Producto prod = new Producto();
					  prod.setDocumentacion(param.getDocumentos());
					  prod.setIdProducto(param.getIdProducto().longValue());
					  productoDAO.actualizarCertificado(prod);

				  }				
				  return true;
				  
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return false;
	}
	
	boolean actualizarPiezas( PartidaCompra partComOriginal, parametrosInspeccion param, Integer cantPiezas, PartidaPedido pPOriginal, Boolean seDividioTiempo, PartidaInspeccion partinsOC, Date tiempo)throws ProquifaNetException{
		  String nombreRechazada = "";
		  String comentario = null;
		  Long tiempoXpieza = 0L;
		try {
			partComOriginal.setEstado("Rechazada");
			partComOriginal.setUbicacion(param.getUbicacionAsignada()); // Se agrego para asignar ubicacion de no despachables	
			//se actualizar la partidaCompra
			partComOriginal.setCantidadCompra(cantPiezas);
			partComOriginal.setFolioInspeccion(this.inspeccionDAO.obtenerFolioDeInspecionPoridCompra(param.getIdCompra()));
			partComOriginal.setCodQrBolsa(param.getQrBolsaIncidencia());
			this.partidaCompraDAO.actualizarPCompra(partComOriginal); 
			
			// se actualiza el partida pedido
			pPOriginal.setCantidadPedida(cantPiezas);			
			pPOriginal.setEstado("Rechazada"); //tabla ppedidos
			this.partidaPedidoDAO.actualizarPPedido(pPOriginal); 
			
//			// se actualiza fecha fin estadoPCompra
			this.inspeccionDAO.actualizarEstadoPCompra(param.getIdCompra()); 
			
//			// se actualiza fecha fin del estadoPPedido
			this.inspeccionDAO.actualizarEstadoPPedido(param.getIdPPedido()); 
//			// se actualiza INSPECCIONOC
			if(seDividioTiempo)
				tiempoXpieza = calcularTiempo(tiempo,cantPiezas,partinsOC,1L);
			else
				tiempoXpieza = calcularTiempo(tiempo,cantPiezas,partinsOC,0L);
			if(param.getNombreImagenesRechazo() != null && param.getNombreImagenesRechazo().size() > 0) {
			 nombreRechazada = param.getNombreImagenesRechazo().get(0);
			}
			if(param.getComentarios() != null && param.getComentarios().size() > 0) {
				comentario = param.getComentarios().get(0);
			}
			if (inspeccionDAO.existePartidaInspeccionOC(param.getIdCompra().intValue())) {
				this.inspeccionDAO.actualizarInspeccionOCPorInspeccion(param.getUbicacion(), param.getLote(), param.getUsuario(), param.getManejoTransporte(),cantPiezas.longValue(),tiempoXpieza,param.getIdCompra(),param.getVideoPartida(), param.getDocumentos(), param.getDocumentosSDS(), param.getPrioridad(), param.isAplicaDocumentacion(),nombreRechazada,param.getIdioma(), param.getEdicion(), comentario);  //tabla inspeccionOC
			} else {
				param.setPiezas(cantPiezas);
				param.setTiempo(tiempoXpieza.intValue());
				param.setImagenRechazo(nombreRechazada);
				this.inspeccionDAO.insertarInspeccionOC(param);  //tabla inspeccionOC
			}
			return true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}
	
	
	@Transactional
	private Long calcularTiempo (Date tiempo, Integer numR,PartidaInspeccion part, Long operacion) throws ProquifaNetException {
		try {
		
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
			String fecha = simpleDateFormat.format(tiempo);
			Integer horas = 0;
			Integer min = 0;
			Integer seg = 0;
			Integer horasSeg = 0; 
			Integer minSeg = 0;
			Integer totalSeg = 0;
			Integer tixPieza = 0;
		
			
			String[] time = fecha.split(":");
			
			horas = horas.valueOf(time[0]);
			min = min.valueOf(time[1]);
			seg = seg.valueOf(time[2]);

			if(horas > 0)
			{
				horasSeg = (horas*60) * 60;
			}
			if(min > 0)
			{
				minSeg = min * 60;
			}
			
			totalSeg = horasSeg + minSeg + seg;

//			si opcion es igual a 2 el tiempo se divide entre el numero piezas, si operacion es igual 1 el tiempo que ya se habia dividido se miltiplica 
//			por el numero piezas requeridas, si operacion es igual a 0 solo se convierte el tiempo en long.
			if(operacion == 2) 
			{
				tixPieza = totalSeg / numR;
			}
			else if(operacion == 1)
			{
				tixPieza = totalSeg * numR;
			}
			else if(operacion == 0)
			{
                tixPieza = totalSeg;
			}

			  
//			  if(tixPieza > 3600) para calcular el tiempo en horas min y segundos 
//			  {
//				  horas = tixPieza /3600;
//				  min = (tixPieza%3600) / 60;
//				  seg = tixPieza%60;
//			  }
//			  else{
//				  horas = 0;
//				  min = tixPieza /60;
//				  seg = tixPieza%60;
//			  }
//			  
//			 
//			  
//			  Date tiempoR = new Date();
//				Calendar diff = Calendar.getInstance();
//				diff.setTime(tiempoR);
//				diff.set(Calendar.HOUR_OF_DAY, horas);
//				diff.set(Calendar.MINUTE, min);
//				diff.set(Calendar.SECOND, seg);
//				
//				
//				tiempoR = diff.getTime();
		
			return tixPieza.longValue();
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return null;
	}
	
	@Transactional
	private List<byte[]> generarCodigos(List<PartidaInspeccion> pIns) throws ProquifaNetException {
		try {
			
			List<byte[]> listaImagenes = new ArrayList<byte[]>();
				
			return listaImagenes;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}	
	
	
	private boolean crearCodigo(String codigo){
		try{
			Funcion funcion = new Funcion();
			BitMatrix matrix;

				
				matrix = new Code128Writer().encode(codigo, BarcodeFormat.CODE_128, 0, 47, null);
		 
		        BufferedImage image = new BufferedImage(matrix.getWidth(), matrix.getHeight(), BufferedImage.TYPE_INT_RGB);
		        image.createGraphics();
		        String rutaGuardar = funcion.obtenerRutaServidor("etiqueta", "");
		 
		        Graphics2D graphics = (Graphics2D) image.getGraphics();
//		        graphics.setColor(Color.WHITE);
		        graphics.fillRect(0, 0, matrix.getWidth(), matrix.getHeight());
//		        graphics.setColor(Color.BLACK);
		 
		        for (int i = 0; i < matrix.getWidth(); i++) {
		            for (int j = 0; j < matrix.getHeight(); j++) {
		                if (matrix.get(i, j)) {
		                    graphics.fillRect(i, j, 1, 1);
		                }
		            }
		        }
		 
		        
		        File file = new File(rutaGuardar);
				if (!file.exists())
					file.mkdirs();
		        file = new File(rutaGuardar + codigo+".png");
		        ImageIO.write(image, "png", file);
		        

		
	        return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} 
	}
	
	
	@Override
	@SuppressWarnings("deprecation")
	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public totalesInspeccionProducto consultaDeTotalesPorInspector(String inspector) throws ProquifaNetException {
		try {
			
			 totalesInspeccionProducto totales = new totalesInspeccionProducto();
			 List<PartidaInspeccion> list = new ArrayList<PartidaInspeccion>();
			 List<PartidaInspeccion> listP = new ArrayList<PartidaInspeccion>();
             Long mes= 0L;
             Date fechaHoy = new Date();
             Integer numDias = 0;
             Date fechaInicioQuincena = new Date();
             Date fechaFinQuincena = new Date();
             Date fechaInicioMes = new Date();
             Date fechaFinMes = new Date();
             Long cantPieza = 0L;
             Long minutos = 0L;
            
			 
			   switch (fechaHoy.getMonth()) {
	            case 0: case 2: case 4: case 6: case 7: case 9: case 11:
	                numDias = 31;
	                break;
	            case 3: case 5: case 8: case 10:
	                numDias = 30;
	                break;
	            case 1:
	                if((fechaHoy.getYear()%4==0 && fechaHoy.getYear()%100!=0) || fechaHoy.getYear()%400==0){
	                    numDias = 29;
	                }
	                else{
	                    numDias = 28;
	                }
	                break;
	            default:
	                log.info("\nEl mes " + mes + " es incorrecto.");
	                break;
	        }
			   
			   Calendar diffInicioQ = Calendar.getInstance();
			  
		if(fechaHoy.getDay()<= 15)
		{
			
			diffInicioQ.setTime(fechaInicioQuincena);
			diffInicioQ.set(Calendar.HOUR_OF_DAY, 00);
			diffInicioQ.set(Calendar.MINUTE, 00);
			diffInicioQ.set(Calendar.DATE, 1);
			
			fechaInicioQuincena = diffInicioQ.getTime();
			
			diffInicioQ.setTime(fechaFinQuincena);
			diffInicioQ.set(Calendar.HOUR_OF_DAY, 23);
			diffInicioQ.set(Calendar.MINUTE, 59);
			diffInicioQ.set(Calendar.DATE, 15);
			fechaFinQuincena = diffInicioQ.getTime();
		}
		else{
			diffInicioQ.setTime(fechaInicioQuincena);
			diffInicioQ.set(Calendar.HOUR_OF_DAY, 00);
			diffInicioQ.set(Calendar.MINUTE, 00);
			diffInicioQ.set(Calendar.DATE, 16);
			
			fechaInicioQuincena = diffInicioQ.getTime();
			
			diffInicioQ.setTime(fechaFinQuincena);
			diffInicioQ.set(Calendar.HOUR_OF_DAY, 23);
			diffInicioQ.set(Calendar.MINUTE, 59);
			diffInicioQ.set(Calendar.DATE, numDias);
			fechaFinQuincena = diffInicioQ.getTime();
		}
		
		diffInicioQ.setTime(fechaInicioMes);
		diffInicioQ.set(Calendar.HOUR_OF_DAY, 00);
		diffInicioQ.set(Calendar.MINUTE, 00);
		diffInicioQ.set(Calendar.DATE, 1);
		fechaInicioMes = diffInicioQ.getTime();
		
		diffInicioQ.setTime(fechaFinMes);
		diffInicioQ.set(Calendar.HOUR_OF_DAY, 23);
		diffInicioQ.set(Calendar.MINUTE, 59);
		diffInicioQ.set(Calendar.DATE, numDias);
		fechaFinMes = diffInicioQ.getTime();
		
		
		totales = inspeccionDAO.obtenerTotalesInspeccion(inspector, fechaInicioMes, fechaFinMes);
		
		list.addAll(inspeccionDAO.obtenerPiezasEstadisticas("", fechaInicioMes, fechaFinMes, inspector));
		//list.addAll(inspeccionDAO.obtenerPiezasPorFecha("dia", null, null, inspector));
		
		totales.setList_Inspeccion(list);
		
		totales.setT_partidas(inspeccionDAO.obtenerTotalPartidasInspeccionadas(inspector));
		Date penultima_ins = inspeccionDAO.obtenerFechaDePenultimaInspeccion();
		listP = inspeccionDAO.obtenerPiezasPorFecha("", null, penultima_ins, inspector);
		for (int i = 0; i < listP.size(); i++) {
			cantPieza = cantPieza + listP.get(i).getTotalPiezas();
			minutos = minutos + listP.get(i).getTiempoInspeccionEnSegundos();
		}

		Long horas = 0L;
		
		  if(minutos > 3600)  
		  {
			  horas = minutos /3600;
		  }
		
		  Long prom = 0L; 
		if(horas > 0)
		prom = cantPieza/ horas;
		
		if(prom == 0)
		{
			prom = 1L;
		}
		
		totales.setPromXpieza(prom);
	
			return totales;
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return null;

	}
	
	

	@SuppressWarnings("deprecation")
	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public String obtenerModoInspeccion() throws ProquifaNetException {
		try {
			totalesInspeccionProducto totales = inspeccionDAO.obtenerTotatalesDepiezasInspeccionadasyTiempoTotaldeInspeccionde30diasAtras();
			List<PartidaInspeccion> partInp = inspeccionDAO.obtenerPartidaInspeccion();
			double totalPiezasXinspeccionar = 0;
			Date tiempxPieza = new Date();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
			String modoInspeccion = "";
			
			Integer horas = 0;
			Integer min = 0;
			Integer seg = 0;
			Integer horasSeg = 0; 
			Integer minSeg = 0;
			Integer totalSeg = 0;

			Date tiempoRestante = new Date();
			Date fechaHoy = new Date();
			Calendar tiempoReal = Calendar.getInstance();
			tiempoReal.setTime(fechaHoy);
//			tiempoReal.set(Calendar.HOUR_OF_DAY, 16);
//			tiempoReal.set(Calendar.MINUTE, 30);
//			tiempoReal.set(Calendar.SECOND, 0);
			
			
			Calendar fechaSeisHoras = Calendar.getInstance();
			fechaSeisHoras.setTime(fechaHoy);
			fechaSeisHoras.set(Calendar.HOUR_OF_DAY, 18);
			fechaSeisHoras.set(Calendar.MINUTE, 0);
			fechaSeisHoras.set(Calendar.SECOND, 0);
			
			
			 log.info("",tiempoReal.get(Calendar.SECOND) * -1);
			 fechaSeisHoras.add(Calendar.SECOND, (tiempoReal.get(Calendar.SECOND) * -1));
			 fechaSeisHoras.add(Calendar.MINUTE, (tiempoReal.get(Calendar.MINUTE) * -1));
			 fechaSeisHoras.add(Calendar.HOUR_OF_DAY, (tiempoReal.get(Calendar.HOUR_OF_DAY) * -1));
             
			  
             log.info("",fechaSeisHoras.getTime());
             log.info(fechaSeisHoras.get(Calendar.HOUR_OF_DAY) + ":" + fechaSeisHoras.get(Calendar.MINUTE) + ":" + fechaSeisHoras.get(Calendar.SECOND));
             tiempxPieza = fechaSeisHoras.getTime();
             String fecha = simpleDateFormat.format(tiempxPieza);
             
             String[] time = fecha.split(":");
				
				horas = horas.valueOf(time[0]);
				min = min.valueOf(time[1]);
				seg = seg.valueOf(time[2]);

				if(horas > 0)
				{
					horasSeg = (horas*60) * 60;
				}
				if(min > 0)
				{
					minSeg = min * 60;
				}
				
				totalSeg = horasSeg + minSeg + seg;
             
			
		
			double tiempoAproximadoDeinspeccion = 0;
			for (PartidaInspeccion partidaInspeccion : partInp) {
				totalPiezasXinspeccionar = totalPiezasXinspeccionar + partidaInspeccion.getCantidadCompra();
			}
			double segXpieza  = Math.round(totales.getTotalTiempo_Ensegundos()/totales.getTotal_piezas());
			
			tiempoAproximadoDeinspeccion = totalPiezasXinspeccionar * segXpieza;
			
			if(tiempoAproximadoDeinspeccion <= totalSeg)
			{
				// Se comentó el modo de Pedimento ya que se solicito hacer el modo solo por Prioridad 
//				modoInspeccion ="Por pedimento";
				modoInspeccion ="Por prioridad";
			}
			else{
				modoInspeccion ="Por prioridad";
			}
			
			log.info("Modo inspeccion: " + modoInspeccion);
			return modoInspeccion;	
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@SuppressWarnings("deprecation")
	@Override
	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public totalesInspeccionProducto obtenerTotalesDeinspecionPorUsuario(String inspector) throws ProquifaNetException {
		try {
			Long auxPiezas = 0L;
			 totalesInspeccionProducto to = new totalesInspeccionProducto();
			 to.setPiezasProm(inspeccionDAO.obtenerPiezasPromedioPorUsuario(inspector));
			 to.setMaxPiezasInsp(inspeccionDAO.obtenerNumPiezasMaximasDeinspeccionPorUsuario(inspector));
			 to.setTotal_piezasInspeccionadas(inspeccionDAO.cantidadDePiezasInspeccionadasPorHoy(inspector));
			 auxPiezas = to.getTotal_piezasInspeccionadas();
			 auxPiezas = auxPiezas + inspeccionDAO.PiezasAInspeccionarHoy();
			 to.setPzaAInspeccion(auxPiezas);
			 
			 return to;
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return null;
	}
	
	@SuppressWarnings("deprecation")
	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public Long  piezasInspeccionadasHoy(String inspector) throws ProquifaNetException {
		try {
			Long auxPiezas = 0L;
			auxPiezas = inspeccionDAO.cantidadDePiezasInspeccionadasPorHoy(inspector);
			 
			 return auxPiezas;
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return null;
	}



	@SuppressWarnings("deprecation")
	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public Boolean guardarTablaInspeccionTemporal(String inspector) throws ProquifaNetException {
		String folio, tipo;
			try{
			long clave = inspeccionDAO.obtenerClaveInspector(inspector);
			folio= inspeccionDAO.obtenerFolioPorPeso();
			tipo = inspeccionDAO.obtenerTipoInspeccion(folio);
			inspeccionDAO.insertarTablaInspeccionTempXDocumento(folio, tipo, clave);
			return true;
				} catch (Exception e) {
				log.info("error al guardar los datos en la tabla temporal: " + e.getMessage());
				return false;
			}
		}


	@SuppressWarnings("deprecation")
	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public  PartidaInspeccion ordenarPartidaPorFolio(String folio) throws ProquifaNetException {
		Integer partidasXFolio = inspeccionDAO.obtenerTotalPartidasXCompra(folio);
		
		try {
			 PartidaInspeccion partInsp = inspeccionDAO.obtenerPartidaInspeccionPorPuntos(folio);
			return partInsp;
		} catch (Exception e) {
			log.info("Error al obtener la partida de compra: " + e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("deprecation")
	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public PartidaInspeccion ordenarPartidasInspeccion(String inspector) throws ProquifaNetException {
		log.info("==================================");
		String folio, tipo, modoInspeccion;
		int idPcompra;
		modoInspeccion = obtenerModoInspeccion();
		long clave = inspeccionDAO.obtenerClaveInspector(inspector);
		// modoInspeccion = "Por prioridad"; //Pruebas
		if (modoInspeccion == "Por prioridad") {
			try {
				idPcompra = Integer.parseInt(inspeccionDAO.obtenerIdPcompraInspeccionTemporal());
				tipo = inspeccionDAO.obtenerTipoInspeccionXidPcompra(idPcompra);
				inspeccionDAO.obtenerPartidaAInspeccionar();
				PartidaInspeccion partInsp= inspeccionDAO.obtenerPartidaAInspeccionar();
				inspeccionDAO.insertarTablaInspeccionTempXPrioridad(idPcompra, tipo, clave);
				return  partInsp;
			} catch (Exception e) {
				log.info("Error al obtener la partida de inspeccion por Prioridad: " + e.getMessage());
				return null;
			}
			// Fin IF
		} else if (modoInspeccion == "Por pedimento") {
			String folioTemp = inspeccionDAO.obtenerFolioXInspectorTablaTemp(clave);
			
			if (folioTemp == "Vacio") {
				folio = inspeccionDAO.obtenerFolioPorPeso();
				tipo = inspeccionDAO.obtenerTipoInspeccion(folio);
				guardarTablaInspeccionTemporal(inspector);
				try {
					PartidaInspeccion partInsp = inspeccionDAO.obtenerPartidaInspeccionPorPuntos(folio);
					return partInsp;
				} catch (Exception e) {
					log.info("Error al obtener la partida de inspeccion por Folio: " + e.getMessage());
					return null;
				}
			} else {
				Integer partidas = inspeccionDAO.obtenerTotalPartidasXCompra(folioTemp);
//				Integer partidas = 0; //Pruebas 
			
				while (partidas != 0) {
					try {
						PartidaInspeccion partInsp = inspeccionDAO.obtenerPartidaInspeccionPorPuntos(folioTemp);
						return partInsp;
					} catch (Exception e) {
						log.info("Error al obtener la partida de inspeccion por Folio: " + e.getMessage());
						return null;
					}
				}
				inspeccionDAO.borrarRegistrosTablaInspeccionTemporal(clave);	
				ordenarPartidasInspeccion(inspector);
			}
		}
		else {
			log.info("A ocurrido un error inesperado, intente nuevamente");
		}
		return null;
	}
	
	
	@SuppressWarnings("deprecation")
	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public Long  sumaPiezasInspeccionadasyPorInspeccion(String inspector) throws ProquifaNetException {
		try {
			Long auxPiezas = 0L;
			auxPiezas = inspeccionDAO.cantidadDePiezasInspeccionadasPorHoy(inspector) + inspeccionDAO.PiezasAInspeccionarHoy();
			 return auxPiezas;
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return null;
	}


	@SuppressWarnings("deprecation")
	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public ContadorPiezasXPrioridad obtenerPiezasPorPrioridad() throws ProquifaNetException {
		try {
			ContadorPiezasXPrioridad partPrioridad = new ContadorPiezasXPrioridad();
			Integer pzasP1, pzasP2, pzasP3;
			pzasP1 = inspeccionDAO.obtenerPzasPorPrioridad("P1");
			pzasP2 = inspeccionDAO.obtenerPzasPorPrioridad("P2");
			pzasP3 = inspeccionDAO.obtenerPzasPorPrioridad("P3");
			partPrioridad.setP1(pzasP1);
			partPrioridad.setP2(pzasP2);
			partPrioridad.setP3(pzasP3);

			return partPrioridad;
		}
		catch (Exception e) {
//			logger.error(e.getMessage());	
			return null;
		}
	}
	
	@Override
	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public List<PartidaInspeccion> obtenerPiezasXFecha() throws ProquifaNetException{
		SimpleDateFormat sdt = new SimpleDateFormat("YYYY-MM-DD");
		try {
			String tipo = "ano";
			Date fechaI = sdt.parse("2018-01-01");
			Date fechaF = sdt.parse("2018-01-31");
			String inspector = "aHernandezM";
			
			List<PartidaInspeccion> partInp = inspeccionDAO.obtenerPiezasPorFecha(tipo, fechaI, fechaF, inspector);
		
		 return partInp;
			
		}
		catch (Exception e) {
//			logger.error(e.getMessage());	
			return null;
		}
	}
	public static void main(String[] args) {
		byte[] bytes = Base64.getDecoder().decode("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");
		Funcion util = new Funcion();
		try {
			util.copiarArchivo(bytes, "nombre" +".png", "imagenRechazo");
		} catch (ProquifaNetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public String  convierteArr(Map<String, Object> documento) throws ProquifaNetException{
		String frente = "";
		String arriba = "";
		String abajo = "";
		String video = "";
		//String nomCertificado = "";
		//String nomHojaSeg = "";
		String aux = "";
		String consecutivo = "";
		String concepto = documento.get("concepto").toString();
			
		try{
	
			if(concepto.equals("Folio rechazo")){
				frente = obtenerConsecutivoDeLoteInspeccion(documento.get("concepto").toString()); 
				util.copiarArchivo((byte[]) Base64.getDecoder().decode(documento.get("frente").toString()), frente +".png", "imagenRechazo");
				arriba = obtenerConsecutivoDeLoteInspeccion(documento.get("concepto").toString());
				util.copiarArchivo((byte[]) Base64.getDecoder().decode(documento.get("arriba").toString()), arriba +".png", "imagenRechazo");
				abajo = obtenerConsecutivoDeLoteInspeccion(documento.get("concepto").toString());
				util.copiarArchivo((byte[]) Base64.getDecoder().decode(documento.get("abajo").toString()), abajo +".png", "imagenRechazo");
				aux = frente +"|" +arriba + "|" + abajo;
					
			} else if(concepto.equals("Grabacion Lote Inspeccion")){
				video = obtenerConsecutivoDeLoteInspeccion(documento.get("concepto").toString());
				util.copiarArchivo((byte[]) Base64.getDecoder().decode(documento.get("video").toString()), video +".webm", "videoPartida");
				aux =video;
			} else if(concepto.equals("Grabacion Embalar")){
				video = obtenerConsecutivoDeLoteInspeccion(documento.get("concepto").toString());
				util.copiarArchivo((byte[]) Base64.getDecoder().decode(documento.get("video").toString()), video +".webm", "videoEmbalar");
				aux =video;
			} else{
			
				if(concepto.equals("Certificado")){
				consecutivo= documento.get("codigo").toString()+"-"+documento.get("lote").toString();
				String subcarpeta = documento.get("idProveedor").toString();
				try{	
					String direccionDocumento = util.obtenerRutaServidor("certificados",subcarpeta) + consecutivo + ".pdf";
					//Validar si existe carpeta, sino crearla
					File dirreccionServerFile = new File(util.obtenerRutaServidor("certificados",subcarpeta));										
					if (!dirreccionServerFile.exists()) {
						dirreccionServerFile.mkdirs();
						log.info("Carpeta creada");
					}
					
					OutputStream out = new FileOutputStream(direccionDocumento);
					out.write((byte[]) Base64.getDecoder().decode(documento.get("documento").toString()));
					out.close();
					
				} catch (FileNotFoundException e) {
					log.info(e.getMessage());
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				//util.copiarArchivo((byte[]) Base64.decodeBase64(documento.get("documento").toString()), consecutivo +".pdf", "certificados");
				aux= consecutivo;
				}
				if(concepto.equals("HojaSeguridad")){
					consecutivo= documento.get("codigo").toString();
					String subcarpeta = documento.get("idProveedor").toString();
				try{	
					String direccionDocumento = util.obtenerRutaServidor("hojasseguridad",subcarpeta) + consecutivo + ".pdf";
					//Validar si existe carpeta, sino crearla
					File dirreccionServerFile = new File(util.obtenerRutaServidor("hojasseguridad",subcarpeta));										
					if (!dirreccionServerFile.exists()) {
						dirreccionServerFile.mkdirs();
						log.info("Carpeta creada");
					}
					
					OutputStream out = new FileOutputStream(direccionDocumento);
					out.write((byte[]) Base64.getDecoder().decode(documento.get("documento").toString()));
					out.close();
					
				} catch (FileNotFoundException e) {
					log.info(e.getMessage());
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}	
					aux= consecutivo;
					}
			}
		
		}
		catch (ProquifaNetException e){
			log.info(e.getMessage());
		}

		log.info(">>respuesta del metodo convierteArr "+ aux);
		return aux ;
		
	}
	
	
	@SuppressWarnings("deprecation")
	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public String enviaArchivo(InputStream archivo, String folioDocumentacion, String subCarpeta, String tipoArchivo)  throws ProquifaNetException{
			String carpeta ="";
			
			carpeta  = inspeccionDAO.obtenerSubCarpeta(subCarpeta); 
			
			if(tipoArchivo.equals ("Certificado")){
		
				try {
					String dir = util.obtenerRutaServidor("certificados", carpeta) ;
					File file = new File(dir);
					File targetFile = new File(dir + folioDocumentacion +".pdf");
					if (!file.exists()) {
					file.mkdirs();
					}
					   OutputStream salida=new FileOutputStream(targetFile);
					   byte[] buf =new byte[1024];
					int len;
					   while((len=archivo.read(buf))>0){
					      salida.write(buf,0,len);
					   }
					   salida.close();
					   archivo.close();
					   log.info("certificado: "+ folioDocumentacion);
					
					
				
					
					} catch (Exception e) {
					log.info(e.getMessage());
					e.printStackTrace();
					TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
					throw new ProquifaNetException();
					}
				
				
			}
			if(tipoArchivo.equals ("HojaSeguridad")){
				try {
					String dir = util.obtenerRutaServidor("hojasseguridad", carpeta) ;
					File file = new File(dir);
					File targetFile = new File(dir + folioDocumentacion +".pdf");
					if (!file.exists()) {
					file.mkdirs();
					}
					   OutputStream salida=new FileOutputStream(targetFile);
					   byte[] buf =new byte[1024];
					int len;
					   while((len=archivo.read(buf))>0){
					      salida.write(buf,0,len);
					   }
					   salida.close();
					   archivo.close();
					   log.info("Hoja de seguridad: "+ folioDocumentacion);
					
					} catch (Exception e) {
					log.info(e.getMessage());
					e.printStackTrace();
					TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
					throw new ProquifaNetException();
					}			
			}
		

		return "Archivo guardado" ;
	}
	
	@SuppressWarnings("deprecation")
	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public String  obtenerUbicacionInspeccion(Map<String, Object> data) throws ProquifaNetException {

		try {
			String ubicacion = "";
			ubicacion = inspeccionDAO.obtenerUbicacion(data);
			 return ubicacion;
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public Boolean guardarExistenciaUbicacion(Map<String, Object> data) throws ProquifaNetException{
		try {
			Boolean ubicacion  =  false;
			String idUbicacion = data.get("idUbicacion").toString();
			String piezasAux = data.get("pzasDespachables").toString();
			String idUbicacionI = data.get("idUbicacionIncidencia").toString();
			String pzasIAux = data.get("pzasIncidencias").toString();
			
			Integer piezas = Integer.parseInt(piezasAux);
			Integer piezasI = Integer.parseInt(pzasIAux);
			
			if(piezas > 0){
				ubicacion = inspeccionDAO.guardarExistenciaUbicacion(idUbicacion, piezas);
			}
			if(piezasI > 0)
			{
				ubicacion = inspeccionDAO.guardarExistenciaUbicacion(idUbicacionI, piezasI);
			}
							
			return ubicacion;
				
			
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return null;
	}

	

	@Transactional(readOnly=true, transactionManager = "ds1TransactionManager")
	public Boolean guardarConsumible(Map<String, Object> data) throws ProquifaNetException{
		Boolean respuesta;
			try{
		
			String cantidadAux = data.get("cantidad").toString();
			String tipoBolsa = data.get("tipoBolsa").toString();
			
			Integer cantidad =  Integer.parseInt(cantidadAux);
			
			respuesta = inspeccionDAO.guardarConsumible(cantidad, tipoBolsa);
			} catch (Exception e) {
				respuesta = false;
			}
		return respuesta;
		}
	
	
	
	@Override
	@Transactional(readOnly=false, transactionManager = "ds1TransactionManager")
	public PartidaInspeccion obtenerPartida(Integer idUsuario) throws ProquifaNetException{
		try {
			PartidaInspeccion partida = inspeccionDAO.obtenerPartida(idUsuario);
			inspeccionDAO.apartarPartida(partida.getIdPartidaInspeccion(), idUsuario);
			return partida;
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		
		return null;
	}
	@Override
	@Transactional(readOnly=false, transactionManager = "ds1TransactionManager")
	public List<ValorCombo> obtenerComboUbicaciones(String concepto) throws ProquifaNetException{
		try {
			return valorComboDAO.obtenerValorCombo(concepto); 
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		
		 return null;
	}
	
	@Override
	@Transactional(transactionManager = "ds1TransactionManager")
	public Object verificarApartado(parametrosInspeccion param) throws ProquifaNetException {
		try {
			PartidaInspeccion partinsOC = new PartidaInspeccion();
			  partinsOC = this.inspeccionDAO.obtenerInspeccionOCxIdCompra(param.getIdCompra());
			  
			if(inspeccionDAO.verificarEstado(param.getIdCompra())) {
				return inspeccionDAO.obtenerInspeccionOCxIdCompra(param.getIdCompra());
			} else {
				PartidaInspeccion partida = new PartidaInspeccion();
				inspeccionDAO.updateApartado(param);
				
				return true;
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

}
