package com.proquifa.net.persistencia.despachos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.proquifa.net.modelo.compras.Pieza;
import com.proquifa.net.modelo.comun.ComplementosPago;
import com.proquifa.net.modelo.comun.Empleado;
//TEST
import com.proquifa.net.modelo.comun.Folio;
import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.ValidarCFDI;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.facturaElectronica.FacturaElectronica;
import com.proquifa.net.modelo.despachos.DocumentoXLS;
import com.proquifa.net.modelo.despachos.EmbalarPedido;
import com.proquifa.net.modelo.despachos.EstadisticaUsuarioEmbalar;
import com.proquifa.net.modelo.despachos.OrdenEntrega;
import com.proquifa.net.modelo.despachos.PRemision;
import com.proquifa.net.modelo.despachos.PackingListJasper;
import com.proquifa.net.modelo.despachos.PartidaInspeccion;
import com.proquifa.net.modelo.despachos.Remision;
import com.proquifa.net.modelo.despachos.TotalEmbalar;
import com.proquifa.net.modelo.despachos.colectarPartidas;
import com.proquifa.net.modelo.despachos.totalesInspeccionProducto;
import com.proquifa.net.modelo.despachos.packinglist.PackingList;
import com.proquifa.net.modelo.despachos.packinglist.PartidaPackingList;
import com.proquifa.net.modelo.despachos.trabajarRuta.TrPackingList;
import com.proquifa.net.modelo.ventas.admoncomunicacion.Correo;

public interface EmbalarDAO {

	/***
	 * Regresa la lista completa de todas las ordenes de compra por
	 * inspeccionar.
	 * 
	 * @param responsable
	 * @return
	 */
	List<PartidaInspeccion> finPartidasAInspeccionar(String responsable, String funcion, String compra);

	/****
	 * Determina si la partida seleccionada se puede borrar y que a su vez que
	 * partida de pedido se regresara.
	 * 
	 * @param idPCompra
	 * @return
	 */
	PartidaInspeccion getPCAMonitoreo(Long idPCompra);

	/****
	 * Elimina el registro de cuando la partida se mando a inspeccion.
	 * 
	 * @param idPCompra
	 */
	void eliminarEstadoPCompraI(Long idPCompra);

	/***
	 * Elimina el registro del pedido cuando fue enviado a inspeccion
	 * 
	 * @param idPPedido
	 */
	void eliminarEstadoPPedidoI(Long idPPedido);

	/***
	 * Elimina el registro de inspeccionOC, datos como la guia, el pedimento, la
	 * caducidad etc. Dependiendo del almacenista de salidas es la tabla que
	 * borraremos
	 * 
	 * @param idPCompra
	 * @param tabla
	 */
	void eliminarInspeccionOC(Long idPCompra, String funcionEmpleado);

	/****
	 * 
	 * @param folioOC
	 * @param responsable
	 * @return
	 */
	Boolean cerrarPendienteInspeccionOC(String folioOC, String funcion);

	/****
	 * 
	 * @param idPCompra
	 * @param parametros
	 * @param tabla
	 * @return
	 */
	Boolean actualizarInspeccionOC(PartidaInspeccion pIns, String tabla);

	/***
	 * 
	 * @param pIns
	 * @param tabla
	 * @return
	 */
	Long insertarInspeccionOC(PartidaInspeccion pIns, String tabla);

	/***
	 * 
	 * @param idPCompra
	 * @return
	 */
	Boolean validarPartidaInspeccionOC(Long idPCompra, String tabla);

	/***
	 * 
	 * @param piezas
	 * @return
	 */
	Long insertarPiezas(String tabla, Pieza pieza);

	/***
	 * 
	 * @param idPCompra
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Pieza> finPiezasPCompra(Long idPCompra, String tabla) throws ProquifaNetException;

	/***
	 * 
	 * @param idPieza
	 * @param tabla
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean actualizarPieza(Pieza pieza, String tabla) throws ProquifaNetException;

	/***
	 * 
	 * @param idPieza
	 * @param tabla
	 * @param documento
	 * @return
	 * @throws ProquifaNetException
	 */
	String getFolioDocPieza(Long idPieza, String tabla, String documento) throws ProquifaNetException;

	/***
	 * 
	 * @param idPCompra
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean actualizarEstadoPCompra(Long idPCompra) throws ProquifaNetException;

	/***
	 * 
	 * @param idPCompra
	 * @param folioInspeccion
	 * @param edoPrevio
	 * @return
	 * @throws ProquifaNetException
	 */
	void insertarPCompraFolioInspeccion(Long idPCompra, String folioInspeccion, String edoPrevio)
			throws ProquifaNetException;

	/***
	 * 
	 * @param idPCompra
	 * @param tipo
	 * @return
	 * @throws ProquifaNetException
	 */
	Long insertarEstadoPCompra(Long idPCompra, String tipo) throws ProquifaNetException;

	public List<PartidaInspeccion> obtenerPartidaEmbalar(String estadoPedido) throws ProquifaNetException;
	
	public List<PartidaInspeccion> obtenerPartidaColectar() throws ProquifaNetException;

	public Boolean cambiaEstadoPedido(List<PartidaInspeccion> partidas, String estadoPedido)throws ProquifaNetException;

	public Integer obtenerContadorPartidasAEmbalar(String estadoPedido) throws ProquifaNetException;

	int generaOrdenEntrega(Folio folio, Long tiempoEmbalaje, String usuario, String zona, String ruta, String pklist,Integer idCliente) throws ProquifaNetException;

	Boolean generaPOrdenEntrega(Integer clave, List<PartidaInspeccion> Partidas, Long id_bolsa)throws ProquifaNetException;

	Long insertarBolsa(String folio, String manejo, int id_ordenEntrega) throws ProquifaNetException;

	Boolean cambiaEstadoPedidoaPorColectar(ArrayList<Integer> idsPPedidos, String estadoPedido)throws ProquifaNetException;

	totalesInspeccionProducto obtenerTotalesEmbalaje(String reEmbalaje, Date iQuincena, Date fQuincena)throws ProquifaNetException;

	List<PartidaInspeccion> obtenerPiezasPorFecha(String tipo, Date fechaI, Date fechaF, String resEmbalaje)throws ProquifaNetException;

	Long obtenerTotalPartidasEmpaquetadas(String resEmbalaje) throws ProquifaNetException;

	PartidaInspeccion obtenerIdOrdenCompraDePenultimoEmbalaje() throws ProquifaNetException;

	Long obtenerTotalDePiezasPorOrdenEntrega(Long pkOrden) throws ProquifaNetException;

	List<PartidaInspeccion> probarobtenerTotalesPorfecha(String tipo, Date fechaI, Date fechaF, String resEmbalaje)	throws ProquifaNetException;

	List<PartidaInspeccion> obtenerPartidasPoridOrdenEntrega(Long idOrdenEntrega) throws ProquifaNetException;

	OrdenEntrega obtenerOrdenEntregaXid(Long idOrdenEntrega) throws ProquifaNetException;
	
	
	
	
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
	public Map<String, List<EstadisticaUsuarioEmbalar>> obtenerEstadisticaUsuario(Empleado usuario) throws ProquifaNetException;
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
	 * @param parametro
	 * @return
	 * @throws ProquifaNetException
	 */
	public List <EmbalarPedido> obtenerFolio(Integer idUsuario ) throws ProquifaNetException ;
	/**
	 * @param parametro
	 * @return
	 * @throws ProquifaNetException
	 */
	
	public Map<String, List<DocumentoXLS>> generarDocumentos(Parametro parametro) throws ProquifaNetException;
	
	/**
	 * @param parametro
	 * @return
	 * @throws ProquifaNetException
	 */
	public Boolean actualizarEstado( Parametro parametro) throws ProquifaNetException;
	
	/**
	 * @param idUsuario
	 * @return
	 * @throws ProquifaNetException
	 */
	public String consultarEstado(Integer idUsuario) throws ProquifaNetException;

	/**
	 * @param packingList
	 * @return
	 * @throws ProquifaNetException
	 */
	PackingList savePackingList(PackingList packingList) throws ProquifaNetException;

	/**
	 * @param packingList
	 * @return
	 * @throws ProquifaNetException
	 */
	PackingList savePartidaPackingList(PartidaPackingList packingList) throws ProquifaNetException;
	
	/**
	 * @param idUsuario
	 * @return
	 * @throws ProquifaNetException
	 */
	public String consultarPedidosEmbalar(Integer idUsuario ) throws ProquifaNetException ;
	
	public Boolean actualizarAEmbalarPendiente( String idPPedidos, String Folio, Integer idusuario ) throws ProquifaNetException ;
	
	public Integer consultarIDPackingList( ) throws ProquifaNetException ;

	/**
	 * @param folio
	 * @return
	 */
	List<PackingListJasper> obtenerDatosJasperPackingList(String folio, boolean generarPDF, boolean soloControlados) throws ProquifaNetException;
	
	List<PackingListJasper> obtenerDatosRegistroEntregaControlados(String folioPL) throws ProquifaNetException;
	
	/**
	 * @param idUsuario
	 * @param estado
	 * @return
	 * @throws ProquifaNetException
	 */
	public String obtenerFilePedido( Integer idUsuario, String estado ) throws ProquifaNetException;
	
	public String obtenerSubCarpetaPed ( Long idPedido )  throws ProquifaNetException ;

	public List<FacturaElectronica> generarFacturaEmbalar(String idsEmbalarPedido) throws ProquifaNetException;

	Boolean actualizarPackingList(Integer idFactura, String idPedidos);

	/**
	 * @param idPPedido
	 * @return
	 * @throws ProquifaNetException
	 */
	public List<String> obtenerPedidoDoctoR(Integer idUsuario, String estado) throws ProquifaNetException;

	Map<String, Integer> numeroCopias(Integer idUsuario, String estado) throws ProquifaNetException;

	public String obtenerDatosCliente(Integer idCliente) throws ProquifaNetException;

	public Long obtenerIdFactura(Integer id) throws ProquifaNetException;

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
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean finalizarGDL(String noGuia) throws ProquifaNetException;

	boolean guardarUbicacion(Integer idPPedido) throws ProquifaNetException;

	public List<ValidarCFDI> obtenerCFDI(String estado, Long idUsuario) throws ProquifaNetException;

	/**
	 * @param idsEmbalarPedido
	 * @return
	 * @throws ProquifaNetException
	 */
	Map<String, String> obtenerPartidasYaFacturadas(String idsEmbalarPedido) throws ProquifaNetException;

	/**
	 * @param idPPedidos
	 * @return
	 * @throws ProquifaNetException
	 */
	Integer saveFacturaElectronicaPrepago(String idPPedidos) throws ProquifaNetException;
	public boolean actualizarFactura(String idFactura) throws ProquifaNetException;

	/**
	 * @param idsEmbalar
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Remision> obtenerDatosRemision(String idsEmbalar) throws ProquifaNetException;

	/**
	 * @param remision
	 * @return
	 * @throws ProquifaNetException
	 */
	int guardarRemision(Remision remision) throws ProquifaNetException;

	/**
	 * @param partidaRemision
	 * @return
	 * @throws ProquifaNetException
	 */
	int guardarPartidaRemision(PRemision partidaRemision) throws ProquifaNetException;

	/**
	 * @param idRemision
	 * @param idPedidos
	 * @return
	 */
	Boolean actualizarPackingListRemision(Integer idRemision, String idPedidos);

	/**
	 * @param idsFacturaElectronica
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean generarPendienteContraEntrega(String idsFacturaElectronica) throws ProquifaNetException;

	/**
	 * @param factura
	 * @param felectronica
	 * @return
	 * @throws ProquifaNetException
	 */
	Integer saveFactura_FElectronicaPrepago(Integer factura, Integer felectronica) throws ProquifaNetException;

	/**
	 * @param idPedidos
	 * @return
	 * @throws ProquifaNetException
	 */
	String obtenerCobradorPorCliente(String idPedidos) throws ProquifaNetException;

	List<String[]> obtenerDatosGDL(String numGuia) throws ProquifaNetException;

	Map<String, Integer> totalesHoy() throws ProquifaNetException;

	/**
	 * @param complementos
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean insertarComplementosXML(List<ComplementosPago> complementos) throws ProquifaNetException;

	/**
	 * @param packingList
	 * @return
	 * @throws ProquifaNetException
	 */
	PackingList obtenerPackingListxFolio(String packingList) throws ProquifaNetException;
	
	
	/**
	 * @param Folio del Packinglist
	 * @return True cuando hay al menos una partida de control: Mundiales o Nacionales dentro del PL; Falso en caso de que no haya ninguna partida
	 * @throws ProquifaNetException
	 */
	boolean esPLdeControlado(String folioPL) throws ProquifaNetException;
	
	boolean isPLRecogeEnPROQUIFA(String folioPL) throws ProquifaNetException;
	
	Correo obtenerDatosCorreoRecogeEnProquifa(String folioPL) throws ProquifaNetException;
	
	Boolean actualizarEnvioCorreo_RecogeEnProquifa(Long idPedido) throws ProquifaNetException;
	Boolean actualizaInfoCFDI(String RegimenSocietario, String RegimenFical, String curp,String RSocial) throws ProquifaNetException;
	Boolean actualizaEdoPpedidos(String cpedido, String codigo) throws ProquifaNetException;
	Boolean actualizaEdoPcompras(String cpedido, String codigo) throws ProquifaNetException;
	Boolean pedidoConProdsDeStock(Integer usuarioEmbalar) throws ProquifaNetException;
	String mandarAStock(Integer id) throws  ProquifaNetException;
}
