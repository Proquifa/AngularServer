package com.proquifa.net.negocio.despachos;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.proquifa.net.modelo.compras.Pieza;
import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.comun.Etiqueta;
import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.ValidarCFDI;

//import org.json.simple.JSONObject;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.despachos.BolsaParaEmbalar;
import com.proquifa.net.modelo.despachos.DocumentoXLS;
import com.proquifa.net.modelo.despachos.EmbalarPedido;
import com.proquifa.net.modelo.despachos.EstadisticaUsuarioEmbalar;
import com.proquifa.net.modelo.despachos.PartidaInspeccion;
import com.proquifa.net.modelo.despachos.TotalEmbalar;
import com.proquifa.net.modelo.despachos.colectarPartidas;
import com.proquifa.net.modelo.despachos.etiquetasYfolios;
import com.proquifa.net.modelo.despachos.totalesInspeccionProducto;
import com.proquifa.net.modelo.despachos.packinglist.PackingList;
import com.proquifa.net.modelo.despachos.trabajarRuta.TrPackingList;
import com.proquifa.net.modelo.ventas.admoncomunicacion.Correo;
import org.springframework.web.multipart.MultipartFile;

public interface EmbalarService {

	/***
	 * Regresa la lista completa de todas las ordenes de compra por
	 * inspeccionar.
	 * 
	 * @param responsable
	 * @return
	 * @throws ProquifaNetException
	 */
	List<PartidaInspeccion> listarPartidasAInspeccion(Long idEmpleado, String compra) throws ProquifaNetException;

	/****
	 * Registrar las piezas evaluadas de la OC
	 * 
	 * @param piezaCompra
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean guardarPiezasCompra(List<Pieza> piezasCompra, String accion, String inspector) throws ProquifaNetException;

	/****
	 * La funcion regresa la partida de la compra al monitoreo.
	 * 
	 * @param idPCompra
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean regresarPartidAMonitoreo(Long idPCompra) throws ProquifaNetException;

	/***
	 * La funcion cierra el pendiente de inspeccion OC y del monitoreo si es que
	 * ya fueron inspeccionadas todas las partidas.
	 * 
	 * @param folioOC
	 * @param funcionUsurio
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean cerrarPendientesInspeccionMonitoreo(String folioOC, String funcionUsurio) throws ProquifaNetException;

	/****
	 * Guarda parcialmente la edicion de la partida de la compra, mientras esta
	 * en el proceso de inspeccion.
	 * 
	 * @param pcompraInspeccion
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean actualizarPartidaInspeccion(PartidaInspeccion partidaEnInspeccion, Boolean rechazoDocto, byte[] bytes)
			throws ProquifaNetException;

	/***
	 * 
	 * @param idPCompra
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Pieza> listarPiezasPCompra(Long idPCompra, String inspector) throws ProquifaNetException;

	/***
	 * 
	 * @param pinspeccion
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Etiqueta> finalizarInspeccionPharma(PartidaInspeccion partida) throws ProquifaNetException;

	public List<PartidaInspeccion> consultaPartidasEmbalar(String estadoPedido) throws ProquifaNetException;
	public List<PartidaInspeccion> consultaPartidasColectar() throws ProquifaNetException;
	

	public Boolean cambiaEstadoPedido(ArrayList<Integer> idsPPedidos, String estadoPedido) throws ProquifaNetException;
	//
	// public Boolean generaOrdenEntrega(ArrayList<Integer> idsPPedidos, String
	// estadoPedido) throws ProquifaNetException;

	public Integer contadorSeccionEmbalar(String estadoPedido) throws ProquifaNetException;

	public String obtenerConsecutivoDeLoteInspeccion() throws ProquifaNetException;

	etiquetasYfolios generarEtiquetaET(PartidaInspeccion part) throws ProquifaNetException;

	public Boolean finalizarEmbalar(List<BolsaParaEmbalar> bolsas, String estadoPedido, Date fechaInicioEmbalaje,String usuario) throws ProquifaNetException;

	totalesInspeccionProducto consultaDeTotalesPorRespoEmbalaje(String resEmbalaje) throws ProquifaNetException;

	boolean validarUsuarioEmbalar(String usuario, String pass) throws ProquifaNetException;
	/**
	 * @param 
	 * @return
	 * @throws ProquifaNetException
	 */
	public Map<String, List<TotalEmbalar>> obtenerTotalEmbalar() throws ProquifaNetException;
	/**
	 * @param idusuario
	 * @return
	 * @throws ProquifaNetException
	 */
	public Map<String, List<EstadisticaUsuarioEmbalar>> obtenerEstadisticasUsuarioEmbalar(Empleado idusuario) throws ProquifaNetException;
	/**
	 * @param parametro
	 * @return
	 * @throws ProquifaNetException
	 */
	public Map<String, List<colectarPartidas>> colectarPartidas(Parametro parametro) throws ProquifaNetException;
	/**
	 * @param parametro
	 * @return
	 * @throws ProquifaNetException
	 */
	public boolean registrarEmbalarPedido(Parametro parametro) throws ProquifaNetException;
	
	/**
	 * @param idUsuario
	 * @return
	 * @throws ProquifaNetException
	 */
	public List <EmbalarPedido> ObtenerFolioPorUsuario(Integer idUsuario) throws ProquifaNetException ;
	/**
	 * @param parametro
	 * @return
	 * @throws ProquifaNetException
	 */
	public boolean cambiarEstado(Parametro parametro) throws ProquifaNetException;
	
	/**
	 * @param idUsuario
	 * @return
	 * @throws ProquifaNetException
	 */
	public String consultarEstado(Integer idUsuario) throws ProquifaNetException;
	/**
	 * @param parametro
	 * @return
	 * @throws ProquifaNetException
	 */
	public Map<String, List<DocumentoXLS>> generarDocumentos(Parametro parametro) throws ProquifaNetException;

	/**
	 * @param packingList
	 * @return
	 * @throws ProquifaNetException
	 */
	public Object insertarPackingList(PackingList packingList, Map<String, String> idPedidosFactura) throws ProquifaNetException;

	/**
	 * @param packingList 
	 * @return {@value = PackingList}
	 * @throws ProquifaNetException
	 */
	PackingList insertarPPackingList(PackingList packingList) throws ProquifaNetException;
	/**
	 * @param parametro
	 * @return
	 * @throws ProquifaNetException
	 */
	Long insertarPendiente(Parametro parametro) throws ProquifaNetException;
	
	/**
	 * @param idUsuario
	 * @param estadoo
	 * @return
	 * @throws ProquifaNetException
	 */
	public Map<String, Object>  pathFilePedidos (Integer idUsuario, String estadoo) throws ProquifaNetException;
	
	/**
	 * @param idUusuario
	 * @param estado
	 * @return
	 * @throws ProquifaNetException
	 */
	public Map<String, Integer> numeroCopias(Integer idUusuario, String estado) throws ProquifaNetException;

	/**
	 * @param parametro
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean pedidosGDL(Parametro parametro) throws ProquifaNetException;

	/**
	 * @return
	 * @throws ProquifaNetException
	 */
	TrPackingList obtenerDatosContacto() throws ProquifaNetException;

	/**
	 * @param noGuia
	 * @param mensajeria 
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean finalizarGDL(String noGuia, String mensajeria) throws ProquifaNetException;

	public void generarPackingList(List<String> lstPackingList);
	
	//Genera el PDF del Registro de Entrega para productos controlados dado un folio de PackingList
	public boolean generarRegistroEntregaControlados (String folioPL);
	
	public List<ValidarCFDI> obtenerCDF(String estado, Long idUsuario) throws ProquifaNetException;

	public boolean actualizarFactura(String idFactura);

	public boolean enviarCorreo(Correo datosC);

	/**
	 * @param packingList
	 * @return
	 * @throws ProquifaNetException
	 */
	Map<String, String> generarFacturaElectronica(PackingList packingList) throws ProquifaNetException;

	void generarPDFRemision(String idsPedidosRemisionar, List<Integer> facturas, List<Integer> idRemisiones);

	Map<String, Integer> obtenerTot();
		
	/**
		 * @param folioPackingList: folio del PackingList
		 * @return True si el PL con tiene partidas de tipo controlado: Mundiales o Nacionales; Falso si no cuenta con productos controlados dentro del PL
		 * @throws ProquifaNetException
		 */
	public boolean esPLdeControlado(String folioPackingList) throws ProquifaNetException;	
	
	public boolean esPLRecogeEnPROQUIFA(String folioPackingList) throws ProquifaNetException;
	
	public void enviarCorreoRecogeEnProquifa(String folioPL) throws ProquifaNetException;
	public boolean cargarArchivoClientesCFDI(MultipartFile csv) throws ProquifaNetException, IOException;

	public boolean pedidoConProdsDeStock(Integer usuarioEmbalar) throws  ProquifaNetException;
	public boolean mandarAStock(List<Integer> idPPedido) throws ProquifaNetException;
}
