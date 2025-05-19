/**
 * 
 */
package com.proquifa.net.negocio.ventas.admoncomunicacion.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.proquifa.net.modelo.cobrosypagos.facturista.PartidaFactura;
import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.Contacto;
import com.proquifa.net.modelo.comun.DoctoCotizacion;
import com.proquifa.net.modelo.comun.DocumentoRecibido;
import com.proquifa.net.modelo.comun.ModificacionDocumentoRecibido;
import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.Pendiente;
import com.proquifa.net.modelo.comun.Proveedor;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.comun.util.documentoPDF.CotizaCampana;
import com.proquifa.net.modelo.ventas.Pedido;
import com.proquifa.net.modelo.ventas.PedidoPago;
import com.proquifa.net.modelo.ventas.admoncomunicacion.Actividad;
import com.proquifa.net.negocio.ventas.admoncomunicacion.RecepcionDocumentosService;
import com.proquifa.net.persistencia.comun.ClienteDAO;
import com.proquifa.net.persistencia.comun.ContactoDAO;
import com.proquifa.net.persistencia.comun.DocumentoRecibidoDAO;
import com.proquifa.net.persistencia.comun.FuncionDAO;
import com.proquifa.net.persistencia.comun.ModificacionDocumentoDAO;
import com.proquifa.net.persistencia.comun.NivelIngresoDAO;
import com.proquifa.net.persistencia.comun.PendienteDAO;
import com.proquifa.net.persistencia.comun.ProveedorDAO;
import com.proquifa.net.persistencia.ventas.CotizacionDAO;
import com.proquifa.net.persistencia.ventas.PedidoDAO;
import com.proquifa.net.persistencia.ventas.PedidoPagoDAO;
import com.proquifa.net.persistencia.ventas.admoncomunicacion.ActividadDAO;

/**
 * @author ernestogonzalezlozada
 *
 */
@Service("recepcionDocumentosService")
public class RecepcionDocumentosServiceImpl implements
		RecepcionDocumentosService {
	@Autowired
	DocumentoRecibidoDAO documentoRecibidoDAO;
	@Autowired
	PendienteDAO pendienteDAO;
	@Autowired
	ModificacionDocumentoDAO modificacionDoctoR;
	@Autowired
	PedidoDAO pedidoDAO;
	@Autowired
	ActividadDAO actividadDAO;
	@Autowired
	ClienteDAO clienteDAO;
	@Autowired
	ProveedorDAO proveedorDAO;
	@Autowired
	ContactoDAO contactoDAO;
	@Autowired
	PedidoPagoDAO pedidoPagoDAO;
	@Autowired
	FuncionDAO funcionDAO;
	@Autowired 
	NivelIngresoDAO nivelIngresoDAO;
	@Autowired
	CotizacionDAO cotizacionDAO;
	Funcion f;
	CotizaCampana pdf;
	//private final static Logger logger = Logger.getLogger(RecepcionDocumentosServiceImpl.class);
	@SuppressWarnings("unused")
	private Boolean trabajado;
	
	final Logger log = LoggerFactory.getLogger(RecepcionDocumentosServiceImpl.class);
		
	/* (non-Javadoc)
	 * @see mx.com.proquifa.proquifanet.negocio.admoncomunicacion.RecepcionDocumentosService#registrarDocumentoRecibido(mx.com.proquifa.proquifanet.modelo.comun.DocumentoRecibido)
	 */
	//@Transactional
	public Long registrarDocumentoRecibido(DocumentoRecibido documentoRecibido)
			throws ProquifaNetException {
		Long idEmpresa = 0L;
		Long idCliente=0L;
		Cliente cliente=null;
		Long idContacto=documentoRecibido.getIdContacto();
//		String responsable =  "" ;
		
		log.info("Empresa:"+documentoRecibido.getNombreEmpresa());
		log.info("NombreContacto:"+documentoRecibido.getNombreContacto());
		log.info("Correo:"+documentoRecibido.getEmailContacto());
		
		try{
			if(documentoRecibido.getOrigen().equals("Cliente")){
				 cliente = this.clienteDAO.obtenerClientePorNombre(documentoRecibido.getNombreEmpresa());
				 if(cliente==null){//ES UN NUEVO CLIENTE
					 	documentoRecibido.setRPor(funcionDAO.getEsacJRXCargaClientes()); 
						idCliente = this.clienteDAO.insertarCliente(documentoRecibido.getNombreEmpresa());
						idContacto=contactoDAO.registrar(documentoRecibido.getNombreContacto(), documentoRecibido.getEmailContacto(), idCliente, documentoRecibido.getNombreEmpresa());
						log.info("Nuevo contacto ok.." + idContacto);
						log.info("Nueva empresa ok.." + idCliente);
				 }else{//YA EXISTE EL CLIENTE
					 //SI NO HAY CONTACTO SE INSERTA
					 if(documentoRecibido.getIdContacto()==0){
						idContacto=contactoDAO.registrar(documentoRecibido.getNombreContacto(), documentoRecibido.getEmailContacto(), cliente.getIdCliente(), documentoRecibido.getNombreEmpresa());
						log.info("Nuevo contacto ok.." + idContacto);
					 }
				 }
				 idEmpresa = cliente!=null?cliente.getIdCliente():idCliente;
			}else{
				Proveedor proveedor = this.proveedorDAO.obtenerProveedorPorNombre(documentoRecibido.getNombreEmpresa());
				idEmpresa = proveedor.getIdProveedor();
			}
			log.info("IdEmpresa: " + idEmpresa);
			documentoRecibido.setEmpresa(idEmpresa);
			documentoRecibido.setIdContacto(idContacto);
			Long nuevoDoctoR = 0L;
			
			
			
			if(documentoRecibido.getCampana() == null || documentoRecibido.getCampana().equals("")){
			nuevoDoctoR = this.documentoRecibidoDAO.insertarDocumentoRecibido(documentoRecibido);
			documentoRecibido.setFolio(nuevoDoctoR);
			log.info("Insertar documento ok...");
			Pendiente pendiente = new Pendiente();
			pendiente.setDocto(nuevoDoctoR.toString());
			pendiente.setFechaInicio(new Date());		
			pendiente.setResponsable(documentoRecibido.getRPor());
			if(documentoRecibido.getDocto().toLowerCase().equals("pedido")){
				pendiente.setTipoPendiente("Pedido por tramitar");
				this.pendienteDAO.guardarPendiente(pendiente);
			}else if(documentoRecibido.getDocto().toLowerCase().equals("otros")){
				pendiente.setTipoPendiente("OTROS a trabajar");
				this.pendienteDAO.guardarPendiente(pendiente);
			}
			log.info("Insertar pendiente ok...");
			}
			else{
				nuevoDoctoR = this.generaCotizacionCampana(documentoRecibido);
				documentoRecibido.setFolio(nuevoDoctoR);
				log.info("Insertar documento ok...");
			}
			if(cliente==null && documentoRecibido.getOrigen().equals("Cliente")){
				Pendiente pendienteC = new Pendiente();
				pendienteC.setDocto(nuevoDoctoR.toString());
				pendienteC.setFechaInicio(new Date());		
				pendienteC.setResponsable(documentoRecibido.getRPor());
				pendienteC.setTipoPendiente("Agregar cliente");
				this.pendienteDAO.guardarPendiente(pendienteC);
				log.info("Insertar pendiente cliente ok...");
			}
			
			log.info("Finalizar servicio...");
			log.info("Nuevo folio insertado: " + nuevoDoctoR);
			return nuevoDoctoR;
			
		}catch(Exception e){
			new Funcion().enviarCorreoAvisoExepcion(e,documentoRecibido);
			return 0L;
		}
	}
	
	
	public List<DocumentoRecibido> buscarDocumentoRecibidoPorFolio(String folio,boolean porFolio)
			throws ProquifaNetException {
		try{
			List<DocumentoRecibido> listRecibido = this.documentoRecibidoDAO.obtenerDocumentoRecibidoPorFolio(folio,porFolio);
			
			
			for (DocumentoRecibido documentoRecibido: listRecibido) {
				
				log.info("Folio: " + folio);
				if(documentoRecibido.getIdContacto() == null || documentoRecibido.getIdContacto() == 0){
					documentoRecibido.setNombreContacto("N/D");
				}else{
					log.info("IdContacto doctoR: " + documentoRecibido.getIdContacto());
					Contacto contacto = this.contactoDAO.obtenerPorId(documentoRecibido.getIdContacto());
					if(contacto != null){
						documentoRecibido.setNombreContacto(contacto.getNombre());
					}else{
						documentoRecibido.setNombreContacto("N/D");
					}
				}

				//Pendiente Cerrado o Abierto
				if(documentoRecibido.getDocto().equals("Requisición")){
					if(documentoRecibido.getNumero() == null || documentoRecibido.getNumero().equals("")){
						documentoRecibido.setNumero("Ninguna");
						documentoRecibido.setCerradoAbierto("Abierto");
					}else{
						documentoRecibido.setDocumentoCierre(documentoRecibido.getNumero());
						documentoRecibido.setCerradoAbierto("Cerrado (C/D)");
					} 
				}else if(documentoRecibido.getDocto().equals("Pedido")){
					if(documentoRecibido.getFechaProceso() == null){
						documentoRecibido.setCerradoAbierto("Abierto");
					}else{
						//Determinar si se encuentra cerrado con documento o sin documento
						Pedido pedido =  this.pedidoDAO.obtenerPedidoXDocumentoRecibido(documentoRecibido.getFolio());
						if(pedido == null){
							documentoRecibido.setCerradoAbierto("Cerrado (S/D)");
						}else{
							documentoRecibido.setDocumentoCierre(pedido.getCpedido());
							documentoRecibido.setCerradoAbierto("Cerrado (C/D)");
						}					
					}
				}else if(documentoRecibido.getDocto().equals("Pago")){
					PedidoPago pedidoPago = this.pedidoPagoDAO.obtenerPedidoPago(documentoRecibido.getFolio());
					if(pedidoPago == null){
						documentoRecibido.setCerradoAbierto("Abierto");
					}else{
						documentoRecibido.setCerradoAbierto("Cerrado (S/D)");
					}	
				}else if(documentoRecibido.getDocto().equals("Queja")){
					documentoRecibido.setCerradoAbierto("Cerrado (S/D)");
				}else if(documentoRecibido.getDocto().equals("Otros") || documentoRecibido.getDocto().equals("OTROS")){
					Pendiente pendiente = this.pendienteDAO.obtenerPendienteXTipoDoctoResponsable(documentoRecibido.getFolio().toString(), documentoRecibido.getRPor(), "OTROS a trabajar");
					if(pendiente == null){
						documentoRecibido.setCerradoAbierto("Abierto");
					}else{
						if(pendiente.getFechaFin() == null){
							documentoRecibido.setCerradoAbierto("Abierto");
						}else{
							documentoRecibido.setCerradoAbierto("Cerrado (S/D)");
						}
					}
				}
				
			}

			return listRecibido;
		}catch(Exception e){
			new Funcion().enviarCorreoAvisoExepcion(e, "\nFolio: "+folio);
			return new ArrayList<DocumentoRecibido>();
		}
	}
	
	//@Transactional
	public void actualizarDocumentoRecibido(DocumentoRecibido documentoNuevo) throws ProquifaNetException {
		log.info("Iniciando servicio...");
		Long idEmpresa = 0L;
		trabajado = false;
		try{
			
			if (documentoNuevo != null && documentoNuevo.getIdContacto() != -1L) {
				if(documentoNuevo.getOrigen().equals("Cliente")){
					Cliente cliente = this.clienteDAO.obtenerClientePorNombre(documentoNuevo.getNombreEmpresa());
					idEmpresa = cliente.getIdCliente();
					log.info("Origen...cliente: " + cliente.getNombre());
				}else{
					Proveedor proveedor = this.proveedorDAO.obtenerProveedorPorNombre(documentoNuevo.getNombreEmpresa());
					idEmpresa = proveedor.getIdProveedor();
					log.info("Origen...proveedor: " + proveedor.getNombre());
				}
				documentoNuevo.setEmpresa(idEmpresa);
				DocumentoRecibido temp = this.documentoRecibidoDAO.obtenerDocumentoRecibidoPorFolio(documentoNuevo.getFolio() + "",true).get(0);
				
				if (temp != null && temp.getIdContacto() != -1L) {
					log.info("Documento temp ok con folio: " + temp.getFolio());
					String cambios = "";
					
					String tipoDocumento = temp.getDocto(); 
					log.info("Tipo documento existente: " + tipoDocumento);
					if(tipoDocumento.equals("Requisición") || tipoDocumento.equals("Pedido") || tipoDocumento.equals("Pago") || tipoDocumento.equals("Queja") || tipoDocumento.equals("Otros") ){
						if(temp.getFechaProceso() != null){
							trabajado = true;
						}
					}

					ModificacionDocumentoRecibido modificacionDocumento = new ModificacionDocumentoRecibido();
					modificacionDocumento.setIdDocumento(temp.getFolio());
					modificacionDocumento.setFecha(new Date());
					modificacionDocumento.setOrigen(temp.getOrigen());
					modificacionDocumento.setEmpresa(temp.getEmpresa());
					modificacionDocumento.setRecibio(temp.getRPor());
					modificacionDocumento.setMedio(temp.getManejo());
					modificacionDocumento.setTipo(temp.getDocto());
					modificacionDocumento.setNumero(temp.getNumero());
					modificacionDocumento.setObservacion(temp.getObservaciones());
					modificacionDocumento.setFechaOrigen(temp.getFechaOrigen());
					modificacionDocumento.setRealizo(temp.getIngreso());
					modificacionDocumento.setMontoDocto(temp.getMontoDocumento());
					modificacionDocumento.setExisteReferencia(temp.getExisteReferencia());

					this.documentoRecibidoDAO.actualizarDocumentoRecibido(documentoNuevo);
					log.info("Actualizacion documento recibido ok...");
					this.modificacionDoctoR.insertarModificacionDocumento(modificacionDocumento);
					log.info("Insert modificacion documento ok...");
					if(documentoNuevo.getDocto().equals("Pedido")){
						log.info("Documento tipo pedido...");
						Integer numeroPedidos = this.pedidoDAO.obtenerNumeroPedidosXDoctoR(documentoNuevo.getFolio());
						if(numeroPedidos > 0){
							Pedido pedido =  this.pedidoDAO.obtenerPedidoXDocumentoRecibido(documentoNuevo.getFolio());
							pedido.setPedido(documentoNuevo.getNumero());
							this.pedidoDAO.actualizarPedido(pedido);
							log.info("Actualizar pedido ok...");
						}
					}
					
					cambios = "Se modificó: ";					
					if(!documentoNuevo.getOrigen().equals(temp.getOrigen())){
						cambios += "- Origen a: " + documentoNuevo.getOrigen();
					}
					if(!documentoNuevo.getEmpresa().equals(temp.getEmpresa())){
						cambios += "- Empresa a: " + documentoNuevo.getOrigen() + " ";
					}
					if(!documentoNuevo.getNumero().equals(temp.getNumero())){
						cambios += "- Referencia a: " + documentoNuevo.getNumero() + " ";
					}
					if(!documentoNuevo.getDocto().equals(temp.getDocto())){
						cambios += "- Tipo a: " + documentoNuevo.getDocto() + " ";
					}
					if(!documentoNuevo.getRPor().equals(temp.getRPor())){
						cambios += "- Destinatario a: " + documentoNuevo.getRPor() + " ";
					}
					cambios += "- FH Origen a: " + documentoNuevo.getFechaOrigen();
					Actividad actividad = new Actividad();
					actividad.setUsuario("Recepción");
					actividad.setTipoDocumento(temp.getDocto());
					actividad.setDocumento(temp.getFolio().toString());
					actividad.setSujeto(temp.getEmpresa().toString());
					actividad.setObservaciones(cambios);
					log.info("Cambios: " + cambios);
					this.actividadDAO.insertarActividades(actividad);
					log.info("Insert actividades ok...");
					Pendiente pendiente = new Pendiente();
					Pendiente pendienteAnterior = new Pendiente();
					log.info("Verificar tipos...docto nuevo: " + documentoNuevo.getDocto() + " docto anterior : " + temp.getDocto());
					if(!documentoNuevo.getDocto().equals(temp.getDocto())){
						log.info("if tipos");
						if(documentoNuevo.getDocto().equals("Pedido")){
							pendiente.setDocto(documentoNuevo.getFolio().toString());
							pendiente.setFechaInicio(new Date());
							pendiente.setResponsable(documentoNuevo.getRPor());
							pendiente.setTipoPendiente("Pedido por tramitar");
							this.pendienteDAO.guardarPendiente(pendiente);
							log.info("Pendiente guardado pedido por tramitar");
						}else if(temp.getDocto().equals("Pedido")){
							pendienteAnterior.setDocto(temp.getFolio().toString());
							pendienteAnterior.setTipoPendiente("Pedido por tramitar");
							this.pendienteDAO.borrarPendiente(pendienteAnterior);
							log.info("Borrar pendiente pedido por tramitar ok...");
						}
						if(documentoNuevo.getDocto().equals("Requisición")){
							DocumentoRecibido documentoRecibido = new DocumentoRecibido();
							Long numeroDoctosR = this.documentoRecibidoDAO.obtenerNumeroDoctoRXFolio(documentoNuevo.getFolio());
							if(numeroDoctosR > 0){
								documentoRecibido = this.documentoRecibidoDAO.obtenerDocumentoRecibidoPorFolio(documentoNuevo.getFolio() + "",true).get(0);
								if (documentoRecibido != null && documentoRecibido.getIdContacto() != -1L) {
									documentoRecibido.setNumero("");
									documentoRecibido.setFechaProceso(null);
									this.documentoRecibidoDAO.actualizarDocumentoRecibido(documentoRecibido);
									log.info("Actualizar documento recibido...");
								}
							}
						}
						
						if(documentoNuevo.getDocto().equals("Otros")){
							pendiente.setDocto(documentoNuevo.getFolio().toString());
							pendiente.setFechaInicio(new Date());
							pendiente.setResponsable(documentoNuevo.getRPor());
							pendiente.setTipoPendiente("OTROS a trabajar");
							this.pendienteDAO.guardarPendiente(pendiente);
							log.info("Guardar pendiente OTROS a trabajar ok...");
						}else if(temp.getDocto().equals("Otros")){
							pendienteAnterior.setDocto(temp.getFolio().toString());
							pendienteAnterior.setTipoPendiente("OTROS a trabajar");
							this.pendienteDAO.borrarPendiente(pendienteAnterior);
							log.info("Borrar pendiente OTROS a trabajar ok...");
						}
						
					}
					log.info("Comparando RPor...Documento nuevo: " + documentoNuevo.getRPor() + " Documento anterior: " + temp.getRPor());
					if(!documentoNuevo.getRPor().equals(temp.getRPor())){
						pendienteAnterior.setResponsable(documentoNuevo.getRPor());
						pendienteAnterior.setDocto(documentoNuevo.getFolio().toString());
						log.info("Pendiente anterior..");
						log.info("Folio pendiente: " + pendienteAnterior.getFolio());
						log.info("Docto pendiente: " + pendienteAnterior.getDocto());
						log.info("Responsable pendiente: " + pendienteAnterior.getResponsable());
						this.pendienteDAO.actualizarResponsablePendiente(pendienteAnterior);
						log.info("Actualizar responsable pendiente ok...");
					}
				}
			}
			
			
		}catch(Exception e){
			new Funcion().enviarCorreoAvisoExepcion(e,documentoNuevo);
		}
		
	}
	
	public List<DocumentoRecibido> buscarDocumentosXBA(Parametro paramBusqueda) throws ProquifaNetException {
		log.info("Obteniendo documentos recibidos...");
		paramBusqueda.setNiveles(nivelIngresoDAO.findLimitesNivelIngreso());
		try{
			String restriccion= "";
			String abierCerrado1="",abierCerrado2="",abierCerrado3="",abierCerrado4="";
			//SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyyMMdd");
			//Object params [] = {formatoDeFecha.format(finicio) + " 00:00",formatoDeFecha.format(ffin) + " 23:59"};

			if (paramBusqueda.getReferencia() != null && !paramBusqueda.getReferencia().equals("")){
				restriccion = restriccion + " AND Numero LIKE '%" + paramBusqueda.getReferencia() + "%' ";
			}
			if (paramBusqueda.getTipo() != null && !paramBusqueda.getTipo().equals("--TODOS--") && !paramBusqueda.getTipo().equals("")){
				restriccion = restriccion + " AND DoctosR.Docto = '" + paramBusqueda.getTipo() + "' ";
			}
			if (paramBusqueda.getDestinatario() != null && !paramBusqueda.getDestinatario().equals("--TODOS--")){
				restriccion = restriccion + " AND RPor = '" + paramBusqueda.getDestinatario() + "' ";
			}
			if (paramBusqueda.getEmpresa() != null && !paramBusqueda.getEmpresa().equals("--TODOS--") && !paramBusqueda.getEmpresa().equals("")){
				restriccion = restriccion + " AND Empresa = '" + paramBusqueda.getEmpresa() + "' ";
			}
			if (paramBusqueda.getcPago() != null && !paramBusqueda.getcPago().equals("--TODOS--") && !paramBusqueda.getcPago().equals("")){
				restriccion = restriccion + " AND Clientes.CPago = '" + paramBusqueda.getcPago() + "' ";
			}
			if (paramBusqueda.getAbiertoCerrado() != null && !paramBusqueda.getAbiertoCerrado().equals("--TODOS--")){
				if(paramBusqueda.getAbiertoCerrado().equals("Abierto")){
					abierCerrado1 =  " AND 'Abierto'= (CASE WHEN DoctosR.Estado='Cancelada'  OR DoctosR.Numero='Recotizada' THEN 'Cerrado' WHEN Numero IS NULL OR Numero ='' THEN 'Abierto' WHEN DoctosR.FProceso IS NULL OR DoctosR.FProceso='' THEN 'Abierto' ELSE 'Cerrado (C/D)' END) ";
					abierCerrado2 =	" AND 'Abierto'= CASE WHEN DoctosR.FProceso IS NULL THEN 'Abierto' WHEN DoctosR.Numero  IS NULL THEN 'Cerrado (S/D)' ELSE CASE WHEN Pedidos.CPedido IS NOT NULL THEN 'Cerrado (C/D)' ELSE 'Cerrado (S/D)' END END  ";
					abierCerrado3 =  " AND 'Abierto'= CASE WHEN DoctosR.FProceso IS NULL THEN 'Abierto' ELSE 'Cerrado (S/D)' END ";
					abierCerrado4 =  " AND 'Abierto'= CASE WHEN CONVERT(VARCHAR(20), PENDIENTE.FFin, 112) IS NOT NULL THEN 'Cerrado (S/D)' ELSE 'Abierto' END  ";
				}else{
					abierCerrado1 =  " AND 'Abierto'<> (CASE WHEN DoctosR.Estado='Cancelada' THEN 'Cerrado (S/D)' WHEN Numero IS NULL OR Numero ='' THEN 'Abierto' WHEN DoctosR.FProceso IS NULL OR DoctosR.FProceso='' THEN 'Abierto'	ELSE 'Cerrado (C/D)' END) ";
					abierCerrado2 =	" AND 'Abierto'<> CASE WHEN DoctosR.FProceso IS NULL THEN 'Abierto' WHEN DoctosR.Numero  IS NULL THEN 'Cerrado (S/D)' ELSE CASE WHEN Pedidos.CPedido IS NOT NULL THEN 'Cerrado (C/D)' ELSE 'Cerrado (S/D)' END END  ";
					abierCerrado3 =  " AND 'Abierto'<> CASE WHEN DoctosR.FProceso IS NULL THEN 'Abierto'ELSE 'Cerrado (S/D)' END ";
					abierCerrado4 =  " AND 'Abierto'<> CASE WHEN CONVERT(VARCHAR(20), PENDIENTE.FFin, 112) IS NOT NULL THEN 'Cerrado (S/D)' ELSE 'Abierto' END ";
				}
			}
			log.info("Restriccion: " + restriccion);
			if(paramBusqueda.getFfin() != null && paramBusqueda.getFfin() != null && paramBusqueda.getAbiertoCerrado() != null && paramBusqueda.getNiveles() != null){
				List<DocumentoRecibido> documentosRecibidos = this.documentoRecibidoDAO.obtenerDocumentosXBA(paramBusqueda.getFinicio(),  paramBusqueda.getFfin(), restriccion,abierCerrado1,abierCerrado2,abierCerrado3,abierCerrado4,paramBusqueda.getAbiertoCerrado(), paramBusqueda.getNiveles());
				return documentosRecibidos;
			}
			else{
				log.info("Algun parametro getFfin,Ffin,AbiertoCerrado o Niveles es NULL");
				return null;
			}
		}
		catch(Exception e){
			log.info(e.getMessage());
			new Funcion().enviarCorreoAvisoExepcion(e,paramBusqueda);
			return new ArrayList<DocumentoRecibido>();
		}
	}
	
	
	public DocumentoRecibido buscarDocumentoRecibidoPorNumero(String numero) {
		try{
			DocumentoRecibido documentoRecibido = this.documentoRecibidoDAO.obtenerDocumentoRecibidoPorNumero(numero);
			if(documentoRecibido.getIdContacto() == null || documentoRecibido.getIdContacto() == 0){
				documentoRecibido.setNombreContacto("N/D");
			}else{
				Contacto contacto = this.contactoDAO.obtenerPorId(documentoRecibido.getIdContacto());
				if(contacto != null){
					documentoRecibido.setNombreContacto(contacto.getNombre());
				}else{
					documentoRecibido.setNombreContacto("N/D");
				}
			}
		return documentoRecibido;
		}catch(Exception e){
			new Funcion().enviarCorreoAvisoExepcion(e,"\nNumero: "+numero);
			return new DocumentoRecibido();
		}
	}

	public List<DocumentoRecibido> buscarDocumentosRecibidosPorReferencia(
			String Referencia) throws ProquifaNetException {
		try{
			List<DocumentoRecibido> documentosRecibidos = this.documentoRecibidoDAO.obtenerDocumentosRecibidosPorReferencia(Referencia);
			log.info("Referencia: " + Referencia);
			for(DocumentoRecibido documentoRecibido  : documentosRecibidos ){
				
			if(documentoRecibido.getIdContacto() == null || documentoRecibido.getIdContacto() == 0){
				documentoRecibido.setNombreContacto("N/D");
			}else{
				log.info("IdContacto doctoR: " + documentoRecibido.getIdContacto());
				Contacto contacto = this.contactoDAO.obtenerPorId(documentoRecibido.getIdContacto());
				if(contacto != null){
					documentoRecibido.setNombreContacto(contacto.getNombre());
				}else{
					documentoRecibido.setNombreContacto("N/D");
				}
			}
			if(documentoRecibido.getOrigen() != null){
				if(documentoRecibido.getOrigen().equals("Cliente")){
					log.info("Origen cliente");
					Cliente cliente = this.clienteDAO.obtenerClienteXId(documentoRecibido.getEmpresa());
					if(cliente!=null){
						documentoRecibido.setNombreEmpresa(cliente.getNombre());
					}else{
						documentoRecibido.setNombreEmpresa("ND");
					}
				}else{
					log.info("Origen proveedor");
					Proveedor proveedor = this.proveedorDAO.obtenerProveedorPorId(documentoRecibido.getEmpresa());
					log.info("Nombre..." + proveedor.getNombre());
					documentoRecibido.setNombreEmpresa(proveedor.getNombre());
				}
			}else{
				log.info("Origen null");
				Cliente cliente = this.clienteDAO.obtenerClienteXId(documentoRecibido.getEmpresa());
				documentoRecibido.setNombreEmpresa(cliente.getNombre());
				log.info("Nombre del cliente: " + documentoRecibido.getNombreEmpresa());
			}
			//Pendiente Cerrado o Abierto
			if(documentoRecibido.getDocto().equals("Requisición")){
				if(documentoRecibido.getNumero() == null || documentoRecibido.getNumero().equals("")){
					documentoRecibido.setNumero("Ninguna");
					documentoRecibido.setCerradoAbierto("Abierto");
				}else{
					documentoRecibido.setDocumentoCierre(documentoRecibido.getNumero());
					documentoRecibido.setCerradoAbierto("Cerrado (C/D)");
				}
			}else if(documentoRecibido.getDocto().equals("Pedido")){
				if(documentoRecibido.getFechaProceso() == null){
					documentoRecibido.setCerradoAbierto("Abierto");
				}else{
					//Determinar si se encuentra cerrado con documento o sin documento
					Pedido pedido =  this.pedidoDAO.obtenerPedidoXDocumentoRecibido(documentoRecibido.getFolio());
					if(pedido == null){
						documentoRecibido.setCerradoAbierto("Cerrado (S/D)");
					}else{
						documentoRecibido.setDocumentoCierre(pedido.getCpedido());
						documentoRecibido.setCerradoAbierto("Cerrado (C/D)");
					}					
				}
			}else if(documentoRecibido.getDocto().equals("Pago")){
				PedidoPago pedidoPago = this.pedidoPagoDAO.obtenerPedidoPago(documentoRecibido.getFolio());
				if(pedidoPago == null){
					documentoRecibido.setCerradoAbierto("Abierto");
				}else{
					documentoRecibido.setCerradoAbierto("Cerrado (S/D)");
				}	
			}else if(documentoRecibido.getDocto().equals("Queja")){
				documentoRecibido.setCerradoAbierto("Cerrado (S/D)");
			}else if(documentoRecibido.getDocto().equals("Otros") || documentoRecibido.getDocto().equals("OTROS")){
				Pendiente pendiente = this.pendienteDAO.obtenerPendienteXTipoDoctoResponsable(documentoRecibido.getFolio().toString(), documentoRecibido.getRPor(), "OTROS a trabajar");
				if(pendiente == null){
					documentoRecibido.setCerradoAbierto("Abierto");
				}else{
					if(pendiente.getFechaFin() == null){
						documentoRecibido.setCerradoAbierto("Abierto");
					}else{
						documentoRecibido.setCerradoAbierto("Cerrado (S/D)");
					}
				}
			}
			}
			return documentosRecibidos;
		}catch(Exception e){
			new Funcion().enviarCorreoAvisoExepcion(e,"\nReferencia: "+Referencia);
			return new ArrayList<DocumentoRecibido>();
		}
		
	}
	
	public List<DocumentoRecibido> buscarDocumentosRecibidosPorFolio(String Folio) throws ProquifaNetException {
		try{
			List<DocumentoRecibido> documentosRecibidos = this.documentoRecibidoDAO.obtenerDocumentosRecibidosPorFolio(Folio);
			//logger.debug("Referencia: " + Folio);
			for(DocumentoRecibido documentoRecibido  : documentosRecibidos ){
				if(documentoRecibido.getIdContacto() == null || documentoRecibido.getIdContacto() == 0){
					documentoRecibido.setNombreContacto("N/D");
				}else{
					//logger.debug("IdContacto doctoR: " + documentoRecibido.getIdContacto());
					Contacto contacto = this.contactoDAO.obtenerPorId(documentoRecibido.getIdContacto());
					if(contacto != null){
						documentoRecibido.setNombreContacto(contacto.getNombre());
					}else{
						documentoRecibido.setNombreContacto("N/D");
					}
				}
				if(documentoRecibido.getOrigen() != null){
					if(documentoRecibido.getOrigen().equals("Cliente")){
						log.info("Origen cliente");
						Cliente cliente = this.clienteDAO.obtenerClienteXId(documentoRecibido.getEmpresa());
						if(cliente!=null){
							documentoRecibido.setNombreEmpresa(cliente.getNombre());
						}else{
							documentoRecibido.setNombreEmpresa("ND");
						}
					}else{
						log.info("Origen proveedor");
						Proveedor proveedor = this.proveedorDAO.obtenerProveedorPorId(documentoRecibido.getEmpresa());
						log.info("Nombre..." + proveedor.getNombre());
						documentoRecibido.setNombreEmpresa(proveedor.getNombre());
					}
				}else{
					log.info("Origen null");
					Cliente cliente = this.clienteDAO.obtenerClienteXId(documentoRecibido.getEmpresa());
					documentoRecibido.setNombreEmpresa(cliente.getNombre());
					log.info("Nombre del cliente: " + documentoRecibido.getNombreEmpresa());
				}
				//Pendiente Cerrado o Abierto
				if(documentoRecibido.getDocto().equals("Requisición")){
					if(documentoRecibido.getNumero() == null || documentoRecibido.getNumero().equals("")){
						documentoRecibido.setNumero("Ninguna");
						documentoRecibido.setCerradoAbierto("Abierto");
					}else{
						documentoRecibido.setDocumentoCierre(documentoRecibido.getNumero());
						documentoRecibido.setCerradoAbierto("Cerrado (C/D)");
					}
				}else if(documentoRecibido.getDocto().equals("Pedido")){
					if(documentoRecibido.getFechaProceso() == null){
						documentoRecibido.setCerradoAbierto("Abierto");
					}else{
						//Determinar si se encuentra cerrado con documento o sin documento
						Pedido pedido =  this.pedidoDAO.obtenerPedidoXDocumentoRecibido(documentoRecibido.getFolio());
						if(pedido == null){
							documentoRecibido.setCerradoAbierto("Cerrado (S/D)");
						}else{
							documentoRecibido.setDocumentoCierre(pedido.getCpedido());
							documentoRecibido.setCerradoAbierto("Cerrado (C/D)");
						}					
					}
				}else if(documentoRecibido.getDocto().equals("Pago")){
					PedidoPago pedidoPago = this.pedidoPagoDAO.obtenerPedidoPago(documentoRecibido.getFolio());
					if(pedidoPago == null){
						documentoRecibido.setCerradoAbierto("Abierto");
					}else{
						documentoRecibido.setCerradoAbierto("Cerrado (S/D)");
					}	
				}else if(documentoRecibido.getDocto().equals("Queja")){
					documentoRecibido.setCerradoAbierto("Cerrado (S/D)");
				}else if(documentoRecibido.getDocto().equals("Otros") || documentoRecibido.getDocto().equals("OTROS")){
					Pendiente pendiente = this.pendienteDAO.obtenerPendienteXTipoDoctoResponsable(documentoRecibido.getFolio().toString(), documentoRecibido.getRPor(), "OTROS a trabajar");
					if(pendiente == null){
						documentoRecibido.setCerradoAbierto("Abierto");
					}else{
						if(pendiente.getFechaFin() == null){
							documentoRecibido.setCerradoAbierto("Abierto");
						}else{
							documentoRecibido.setCerradoAbierto("Cerrado (S/D)");
						}
					}
				}
			}
			return documentosRecibidos;
		}catch(Exception e){
			new Funcion().enviarCorreoAvisoExepcion(e, "\nFolio"+Folio);
			return new ArrayList<DocumentoRecibido>();
		}
		
	}
	
	public boolean generarPendienteClienteDeshabilitado(String DoctoR){
		Date fecha = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm");
		try{
			Integer operacion = this.documentoRecibidoDAO.generarPendienteClienteDeshabilitado(DoctoR, format.format(fecha));
			if (operacion>0){
				return true;
			}else{
				throw new ProquifaNetException("Error al intentar Crear el Pendiente");
			}
		}catch(ProquifaNetException e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.info(e.getMessage());
			new Funcion().enviarCorreoAvisoExepcion(e, "DoctoR"+DoctoR);
			return false;
		}
	}

	@Override
	public int validarFolioCampana(String campana, String folio)
			throws ProquifaNetException {
		try{
			return this.documentoRecibidoDAO.getValidarFolioCampana(campana , folio );
		}catch (Exception e) {
			log.info(e.getMessage());
			new Funcion().enviarCorreoAvisoExepcion(e, "\nCampana: "+campana,"\nFolio: "+folio);
			return -1;
		}
	}

	@Override
	public Long generaCotizacionCampana (DocumentoRecibido documentoRecibido) throws ProquifaNetException {
		try{
			//Long idcotiza  = this.documentoRecibidoDAO.agregarCotizacionCampana(documentoRecibido);
			//logger.debug("Insertar cotiza ok... folio = " + idcotiza );
			//documentoRecibido.setCcotiza(idcotiza);
			Long idcotiza  = 118247L;
			documentoRecibido.setCcotiza(118247L);
			idcotiza=null;
			DoctoCotizacion docto = this.documentoRecibidoDAO.obtenerInfoCotizacionCampana(idcotiza);
			List<PartidaFactura> partidas = this.documentoRecibidoDAO.obtenerInfoPartidasCampana(idcotiza);
			List<PartidaFactura> complemento = this.documentoRecibidoDAO.obtenerInfoComplementoCampana(idcotiza);
		
			//determinar totales 
			Double subtotal = 0D;
			for (PartidaFactura partida : partidas){
				String notasE = "";
				// busca los complementos de cada partida 
				for (PartidaFactura comp_partida : complemento){
					if (partida.getIdPFactura().equals(comp_partida.getIdPFactura())){
						if (notasE.equals("")){
							notasE = comp_partida.getConceptoPartida(); 
						}else{
							notasE += ", " + comp_partida.getConceptoPartida(); 
						}
					}
				}
				if (!notasE.equals("")){
					partida.setNota("Incluye los siguiente complementos: " + notasE);
				}
				partida.setSubTotal(partida.getCantidad() * partida.getImporte());
				subtotal += partida.getSubTotal(); 
			}
			docto.setSubtotal(subtotal);
			docto.setIvaTotal(0D);
			docto.setTotal(subtotal);
			docto.setPartidas(partidas);
			//determinarFecha de Vigencia
			Calendar calendario = Calendar.getInstance();
			calendario.setTime(docto.getFcreacion()); // Configuramos la fecha que se recibe
			calendario.add(Calendar.DAY_OF_YEAR, 30);  // numero de dias a anadir, o restar en caso de dias<0
			if(calendario.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) calendario.add(Calendar.DAY_OF_WEEK, 3);// si cae la fecha en fin de semana la avanza al dia lunes
			else calendario.add(Calendar.DAY_OF_WEEK, 1);
			docto.setVigencia(calendario.getTime()); 
			this.pdf =  new CotizaCampana();
			  this.pdf.CotizacionCampana(docto);
			return docto.getFolioDorctoR();
		}catch (Exception e) {
			log.info(e.getMessage());
			new Funcion().enviarCorreoAvisoExepcion(e, "\nDocumentoRecibido: "+documentoRecibido);
			return -1L;
		}
	}
}
