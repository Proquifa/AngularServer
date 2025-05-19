package com.proquifa.net.negocio.comun.impl;

import java.util.Date;
import java.util.List;

//import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.proquifa.net.modelo.catalogos.cobrosypagos.facturista.Factura;
import com.proquifa.net.modelo.comun.Cartera;
import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.Comentario;
import com.proquifa.net.modelo.comun.Contacto;
import com.proquifa.net.modelo.comun.Direccion;
import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.comun.NivelIngreso;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.persistencia.cobrosypagos.facturista.FacturacionDAO;
import com.proquifa.net.persistencia.comun.ClienteDAO;
import com.proquifa.net.persistencia.comun.EmpleadoDAO;

public class ClienteServiceImpl {
//	@Autowired
//	ClienteDAO clienteDAO;
//	@Autowired
//	FacturacionDAO facturaDAO;
//	@Autowired
//	PagoPendienteDAO pagoPendienteDAO;
//	@Autowired
//	InconsistenciaPedidoDAO inconsistenciaPedidoDAO;
//	@Autowired
//	NotaCreditoDAO creditoDAO;
//	@Autowired
//	ContactoDAO contactoDAO;
//	@Autowired
//	NivelIngresoDAO nivelIngresoDAO;
//	@Autowired
//	DireccionesClienteDAO direccionesDAO;
//	@Autowired
//	ComentarioDAO comentarioDAO;
//	@Autowired
//	ObjetivoCrecimientoDAO objetivoCrecimientoDAO;
//	@Autowired
//	CatalogoClientesDAO catalogoClienteDAO;
//
//	@Autowired
//	EmpleadoDAO empleadoDAO;
//
//	Funcion funcion = new Funcion();
//
//	private final static Logger logger = Logger.getLogger(ClienteServiceImpl.class);
//
//	public Cliente obtenerClienteXId(Long idCliente)
//			throws ProquifaNetException {
//		Cliente cliente = this.clienteDAO.obtenerClienteXId(idCliente);
//		//logger.info("Obteniendo cliente con id: " + cliente.getIdCliente() + "...ok");
//		//logger.info("Determinando informaci-n adicional...");
//		List<Factura> facturasAdeudo = this.facturaDAO.obtenerFacturasPorCobrar(idCliente);
//		cliente.setNotasC(this.creditoDAO.obtenerNotasDeCreditoPorCliente(idCliente));
//		Double morosidad = 0.0;
//		Integer facturasVencidas = 0;
//		List<PagoPendiente> pagosPendientes = null;
//		List<InconsistenciaPedido> inconsistencias = null;
//		if (facturasAdeudo != null) {
//			cliente.setFacturasAdeudo(facturasAdeudo);
//			for (Factura f : facturasAdeudo) {
//				if (f.getCondicionesPago().contains("DIAS")) {
//					pagosPendientes = this.pagoPendienteDAO
//							.obtenerPagoPendienteXFactura(f.getNumeroFactura(),
//									f.getFacturadoPor(), new Date());
//					facturasVencidas += pagosPendientes.size();
//				} else {
//					inconsistencias = this.inconsistenciaPedidoDAO
//							.obtenerInconsistenciaPedido(f
//									.getDocumentoRecibido(), new Date());
//					if (inconsistencias.size() > 0) {
//						facturasVencidas++;
//					}
//				}
//			}
//			if (facturasAdeudo.size() > 0) {
//				morosidad = (facturasVencidas * 100.0) / facturasAdeudo.size();
//			}
//		}
//		cliente.setMorosidad(morosidad);
//		List<NivelIngreso> niveles = this.nivelIngresoDAO.findLimitesNivelIngreso(); 
//		cliente.setNivelIngreso(this.clienteDAO.determinarNivelIngresosCliente(idCliente, niveles));
//
//		return cliente;
//	}
//
//	@Transactional
//	public Long insertarCliente(Cliente cliente) throws ProquifaNetException {	
//
//		try {
//			if (cliente.getBytes() != null ){
//				funcion.copiarArchivo(cliente.getBytes(), cliente.getIdCliente() + "." + cliente.getImagen(), "imagencliente");
//			}
//			if (cliente.getNombre() != null && cliente.getNombre().equals("")) {
//				cliente.setNombre(cliente.getNombre().toUpperCase());
//			}
//			if (cliente.getSector() != null && cliente.getSector().equals("")) {
//				cliente.setSector(cliente.getSector().toUpperCase());
//			}
//			if (cliente.getIndustria() != null && cliente.getIndustria().equals("")) {
//				cliente.setIndustria(cliente.getIndustria().toUpperCase());
//			}
//			if (cliente.getRol() != null && cliente.getRol().equals("")) {
//				cliente.setRol(cliente.getRol().toUpperCase());
//			}
//
//
//			if(cliente.getIdcartera() != null && cliente.getIdcartera() > 0 ){
//
//				Cartera cartera = clienteDAO.obtenerInformacionDeCarteraPorId(cliente.getIdcartera());
//
//				if(cartera.getEsac() > 0)
//				{
//					Empleado e =  empleadoDAO.obtenerEmpleadoPorId(cartera.getEsac());
//					cliente.setVendedor(e.getUsuario());
//				}
//				else{
//					cliente.setVendedor(null);
//				}
//
//				if(cartera.getCobrador() > 0)
//				{
//					cliente.setIdCobrador(cartera.getCobrador());
//				}
//				else{
//					cliente.setIdCobrador(null);
//				}
//
//
//				if(cartera.getEv() > 0)
//				{
//					cliente.setIdEjecutivoVenta(new Long(cartera.getEv()).intValue());
//				}
//				else
//				{
//					cliente.setIdEjecutivoVenta(null);
//				}
//
//
//			}
//
//			Long idCliente = this.clienteDAO.insertarCliente(cliente);
//
//			if(cliente.getIdcartera() != null && cliente.getIdcartera() > 0)
//			{
//				clienteDAO.asignarClienteACartera(idCliente, cliente.getIdcartera());
//			}
//
//			if (cliente.getObjectivoCrecimiento()!=null || cliente.getObjetivoCrecimientoFundamental()  != null) {
//				this.objetivoCrecimientoDAO.insertObjetivoCrecimientoPorCliente(idCliente,cliente.getObjectivoCrecimiento(), cliente.getObjetivoCrecimientoFundamental());
//			}
//			if (cliente.getDireccion()!= null ) {
//				Direccion direccion = cliente.getDireccion();
//				direccion.setIdCliente(Integer.parseInt(idCliente.toString())); 
//				cliente.setDireccion(direccion);
//				direccionesDAO.updateDireccionEmpresa(cliente.getDireccion());
//			}
//			//Insertar la lista de comentarios que trae el cliente
//			if ( cliente.getListaComentarios()!=null ){
//				//logger.info("Insertando la lista de comentarios");
//				List<Comentario> listaComentarios=cliente.getListaComentarios();
//				for(Comentario comentario:listaComentarios){
//					comentario.setIdCliente(idCliente);
//					this.comentarioDAO.insertarComentario(comentario);
//				}
//			} else {
//				//logger.info("cliente sin comentarios");
//			}
//			return idCliente;
//		} catch (Exception e) {
//			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//			logger.error("Error:" + e.getMessage());
//			return null;
//		}
//	}
//
//	public List<Cliente> listarNumeroDeCliente(){
//		List<Cliente> cliente = this.clienteDAO.FindObtenerContactosXCliente();
//		return cliente;
//	}
//	public Long insertarCliente(String nombre, String nombreContacto, String correo, String empresa) 
//			throws ProquifaNetException {
//
//		Long idCliente = this.clienteDAO.insertarCliente(nombre);
//		contactoDAO.registrar(nombreContacto, correo, idCliente, empresa);
//		return idCliente;
//	}
//
//	public Long insertarNuevoClienteContacto(Contacto contacto, Cliente cliente)
//			throws ProquifaNetException {
//
//		Long idCliente = this.clienteDAO.insertarCliente(cliente);
//		contacto.setIdEmpresa(idCliente);
//		if (this.contactoDAO.registrar(contacto)){
//			return contacto.getIdContacto();
//		}
//		return 0L; 
//	}
//
//	public List<Cliente> obtenerClientes(Long habilitado) 
//			throws ProquifaNetException{
//
//		List<NivelIngreso> ni = nivelIngresoDAO.findLimitesNivelIngreso();
//		String condiciones = " C.Habilitado = " + habilitado;
//
//		return clienteDAO.findObtenerClientes(ni, condiciones);
//	}
//
//	public List<Cliente> obtenerClientesPorUsuario(Long idUsuarioLogeado)
//			throws ProquifaNetException {
//
//		List<Cliente> clientes = this.clienteDAO.obtenerClientesPorUsuario(idUsuarioLogeado);
//
//		return clientes;
//	}
//
//	@Override
//	public Boolean actualizarFechaCreacionCliente(Long idClienete)
//			throws ProquifaNetException {
//
//		return clienteDAO.updateFechaCreacionCliente(idClienete);
//	}
}
