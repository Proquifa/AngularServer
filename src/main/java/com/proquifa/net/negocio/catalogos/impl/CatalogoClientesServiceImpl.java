package com.proquifa.net.negocio.catalogos.impl;

import java.awt.Point;
//import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
//import java.io.FileWriter;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import org.apache.axis2.databinding.types.soapencoding.Array;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.proquifa.net.modelo.catalogos.FormulaPrecio;
import com.proquifa.net.modelo.catalogos.clientes.ParametrosOfertaCliente;
import com.proquifa.net.modelo.catalogos.proveedores.ClasificacionConfiguracionPrecio;
import com.proquifa.net.modelo.catalogos.proveedores.ConfiguracionPrecioProducto;
import com.proquifa.net.modelo.catalogos.proveedores.TiempoEntrega;
//import mx.com.proquifa.proquifanet.modelo.cobrosypagos.facturista.Factura;
//import mx.com.proquifa.proquifanet.modelo.compras.Compra;
//import mx.com.proquifa.proquifanet.modelo.compras.ResumenCompra;
//import mx.com.proquifa.proquifanet.modelo.compras.ResumenPCompra;
import com.proquifa.net.modelo.comun.Cartera;
import com.proquifa.net.modelo.comun.CarteraCliente;
import com.proquifa.net.modelo.comun.CatalogoItem;
import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.Comentario;
import com.proquifa.net.modelo.comun.ConfiguracionContrato;
//import mx.com.proquifa.proquifanet.modelo.comun.Contacto;
import com.proquifa.net.modelo.comun.Contrato;
import com.proquifa.net.modelo.comun.Direccion;
import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.comun.Folio;
import com.proquifa.net.modelo.comun.Horario;
import com.proquifa.net.modelo.comun.NivelIngreso;
import com.proquifa.net.modelo.comun.Producto;
import com.proquifa.net.modelo.comun.Proveedor;
import com.proquifa.net.modelo.comun.ValorCombo;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.tableros.cliente.EntregaCatClientes;
//import mx.com.proquifa.proquifanet.modelo.ventas.PartidaPedido;
import com.proquifa.net.negocio.catalogos.CatalogoClientesService;
import com.proquifa.net.persistencia.catalogos.CatalogoClientesDAO;
import com.proquifa.net.persistencia.catalogos.CatalogoProveedorDAO;
//import mx.com.proquifa.proquifanet.persistencia.catalogos.impl.mapper.CarterasRowMapper;
import com.proquifa.net.persistencia.comun.ClienteDAO;
import com.proquifa.net.persistencia.comun.ComentarioDAO;
import com.proquifa.net.persistencia.comun.CorporativoDAO;
import com.proquifa.net.persistencia.comun.DireccionDAO;
import com.proquifa.net.persistencia.comun.DireccionesClienteDAO;
import com.proquifa.net.persistencia.comun.EmpleadoDAO;
import com.proquifa.net.persistencia.comun.FolioDAO;
import com.proquifa.net.persistencia.comun.NivelIngresoDAO;
import com.proquifa.net.persistencia.comun.ObjetivoCrecimientoDAO;
import com.proquifa.net.persistencia.comun.ProductoDAO;
import com.proquifa.net.persistencia.comun.ValorComboDAO;
/*
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;*/

@Service("catalogoClientesService")
public class CatalogoClientesServiceImpl implements CatalogoClientesService {

	final Logger log = LoggerFactory.getLogger(CatalogoClientesServiceImpl.class);
	
	@Autowired
	CatalogoClientesDAO catalogoClienteDAO;
	@Autowired
	EmpleadoDAO empleadoDAO;
	@Autowired
	ProductoDAO productoDAO;
	@Autowired
	CatalogoProveedorDAO catalogoProveedorDAO;
	@Autowired
	NivelIngresoDAO nivelIngresoDAO;
	@Autowired
	ComentarioDAO comentarioDAO;
	@Autowired
	ObjetivoCrecimientoDAO objetivocrecimientoDAO;
	// ObjetivoCrecimientoDAO objetivoDAO;
	@Autowired
	ClienteDAO clienteDAO;
	@Autowired
	DireccionesClienteDAO direccionesDAO;
	@Autowired
	CorporativoDAO corporativoDAO;
	@Autowired
	DireccionDAO direccionDAO;
	@Autowired
	FolioDAO folioDAO;
	@Autowired
	ValorComboDAO valorComboDAO;
	Funcion funcion = new Funcion();
	
	private Long idClienteConfiguracionPrecio;

	public List<Cliente> obtenerClientes(Long habilitado)
			throws ProquifaNetException {
		try {
			List<NivelIngreso> ni = nivelIngresoDAO.findLimitesNivelIngreso();

			String condiciones = " C.Habilitado = 1";

			return clienteDAO.findObtenerClientes(ni, condiciones);
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "habilitado: " + habilitado);
			return new ArrayList<Cliente>();
		}
	}

	@Override
	public List<Cliente> obtenerClientesSinCorporativo()
			throws ProquifaNetException {
		try {
			List<NivelIngreso> ni = nivelIngresoDAO.findLimitesNivelIngreso();
			String condiciones = "";

			condiciones += " \n C.Habilitado = 1 and c.Pais in ('MEXICO','--NINGUNO--') AND year (C.FechaRegistro) < year(GETDATE()) ";
			condiciones += " \n AND FK02_Corporativo IS NULL AND C.Rol = 'LABORATORIO' AND C.Sector <> 'PUBLICO' ";

			return clienteDAO.findObtenerClientes(ni, condiciones);
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			return new ArrayList<Cliente>();
		}
	}

	@Override
	public List<Cliente> obtenerClientesXUsuario(Empleado usuario,
			Long habilitado) throws ProquifaNetException {

		try {
			List<NivelIngreso> ni = nivelIngresoDAO.findLimitesNivelIngreso();

			String condiciones = "";

			condiciones += " C.Habilitado = " + habilitado;

			if (usuario != null) {
				if (usuario.getIdFuncion() == 5) {// ESAC
					condiciones += " \n AND Vendedor = '"
							+ usuario.getUsuario() + "'";
				} else if (usuario.getIdFuncion() == 37) {// ESAC MASTER
					condiciones += " \n AND EXISTS ( "
							+ " \n SELECT * FROM (  "
							+ " \n 	SELECT EMPL_SUB.Usuario FROM "
							+ " \n		( "
							+ " \n			SELECT JEFE.*,Empleados.Clave,Empleados.Usuario from  Empleados "
							+ " \n			LEFT JOIN  Jefe_Subordinado JEFE ON Empleados.Usuario='"
							+ usuario.getUsuario()
							+ "' AND Empleados.Clave=JEFE.FK01_Jefe "
							+ " \n		)  SUB "
							+ " \n		LEFT JOIN Empleados AS EMPL_SUB ON EMPL_SUB.Clave=SUB.FK02_Subordinado "
							+ " \n		WHERE SUB.PK_Subordinado IS NOT NULL "
							+ " \n	) SUBORDINADOS  WHERE SUBORDINADOS.Usuario=ESAC.Usuario AND C.Vendedor=SUBORDINADOS.Usuario) "
							+ " \n	OR ( C.Vendedor='" + usuario.getUsuario()
							+ "' AND C.Habilitado = " + habilitado + " ) ";
				} else if (usuario.getIdFuncion() == 7) {// EJECUTIVO DE VENTAS
					condiciones += " \n AND C.FK01_EV=(SELECT Clave FROM Empleados WHERE Usuario='"
							+ usuario.getUsuario() + "' ) ";
				} else if (usuario.getIdFuncion() == 40) {// RESPONSABLE DE
					// COBROS Y PAGOS
					boolean master = false;
					if (usuario.getRoles().size() > 0) {
						for (String rol : usuario.getRoles()) {
							if (rol.equals("Cobrador_Master")) {
								master = true;
								break;
							}
						}
					}

					if (!master) {
						condiciones += " \n AND COBRADOR.Usuario = '"
								+ usuario.getUsuario() + "'";
					}
				}

			}

			// //////obtener la direccion desde query
			// for (Cliente cliente : clientes) {
			// Direccion direccion = (Direccion)
			// direccionesDAO.getDatosdeEmpresa(cliente.getIdCliente());
			// if (direccion!=null )
			// cliente.setDireccion(direccion);
			// }

			return clienteDAO.findObtenerClientes(ni, condiciones);
		} catch (Exception e) {
			log.info(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,usuario,"habilitado: " + habilitado);
			return new ArrayList<Cliente>();
		}

	}

	@Override
	@Transactional
	public boolean actualizarClienteEmpresa(Cliente cliente)
			throws ProquifaNetException {


		if (cliente.getBytes() != null) {
			funcion.copiarArchivo(cliente.getBytes(), cliente.getIdCliente()
					+ "." + cliente.getImagen(), "imagencliente");
		} 
		try {
			// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			// SE OBTINE LA INFORMACION QUE HAY DEL CLIENTE EN LA BASE DE DATOS
			Cliente clienteOriginal = catalogoClienteDAO
					.obtenerInfoCliente(cliente.getIdCliente());
			if (clienteOriginal != null) {
				// SE COMPARA EL ID DEL COBRADOR DEL NUEVO CLIENTE Y DEL CLIENTE
				// EN LA BASE DE DATOS
				// se comvierte primero a string despues a double, por que la
				// comparaci-n entre long no la estaba haciendo bien
			
				if (Double.parseDouble(clienteOriginal.getIdCobrador()
						.toString()) != Double.parseDouble(cliente
								.getIdCobrador().toString())) {
					// SE OBTIENE EL USUARIO DEL NUEVO PAGADOR
					Empleado empleadoTmp = new Empleado();
					empleadoTmp = empleadoDAO.obtenerEmpleadoPorId(cliente
							.getIdCobrador());
					String nuevoCobrador = empleadoTmp.getUsuario();
					// SE OBTIENEN TODOS LOS PENDIENTES DEL CLIENTE QUE PUEDEN
					// ESTAR ABIERTOS
					List<String> pend = catalogoClienteDAO
							.getPendientesRespCobrador(cliente.getIdCliente()
									.toString());
					if (pend.size() > 0) {
						String valor = pend.toString();
						valor = valor.replace("[", "");
						valor = valor.replace("]", "");
						catalogoProveedorDAO
						.actualizarResponsablePendienteXfolio(valor,
								nuevoCobrador);
					}
				}

				// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				// SE COMPARA EL ID DEL VENDEDOR DEL NUEVO CLIENTE Y DEL CLIENTE
				// EN LA BASE DE DATOS
				// //log.info("------------- Pendientes ESAC");
				if (!clienteOriginal.getVendedor()
						.equals(cliente.getVendedor())) {
					// //log.info("------------- EntroC");
					// SE OBTIENEN TODOS LOS PENDIENTES ESAC QUE DEL CLIENTE QUE
					// PUEDEN ESTAR ABIERTOS
					List<String> pend = catalogoClienteDAO
							.getPendientesRespESAC(cliente.getIdCliente()
									.toString());
					if (pend.size() > 0) {
						String valor = pend.toString();
						valor = valor.replace("[", "");
						valor = valor.replace("]", "");
						// //log.info(valor);
						catalogoProveedorDAO
						.actualizarResponsablePendienteXfolio(valor,
								cliente.getVendedor());

					}
					catalogoClienteDAO.updatePendientesCotizacionESAC(cliente
							.getIdCliente().toString(), cliente.getVendedor());
				}
				// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

				// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				// SE COMPARA SI ESTABA DESHABILITADO Y SI SE ESTA HABILITANDO
				// PARA MODIFICAR LA FECHA DE CREACION
				String condicion = "";
				if (clienteOriginal.getHabilitado() == false
						&& cliente.getHabilitado() == true) {
					//					//log.info("-------------- Cambio de fecha de creacion");
					clienteDAO.updateFechaCreacionCliente(cliente
							.getIdCliente());
					
					clienteDAO.asignarClienteACartera(cliente.getIdCliente(), cliente.getIdcartera());
					Cartera cartera = clienteDAO.obtenerInformacionDeCarteraPorId(cliente.getIdcartera());
					
					if(cartera.getArea() != null)
					{
						if(cartera.getArea().equalsIgnoreCase("Ventas"))
						{
							condicion = " FK01_EV =" + cartera.getEv() + " where Clave = " + cliente.getIdCliente(); 
						}
							
						else if(cartera.getArea().equalsIgnoreCase("ESAC")){
							
							condicion = " Vendedor = '" + cartera.getNombreEsac() + "' where Clave = " + cliente.getIdCliente(); 
						}
								
						else if(cartera.getArea().equalsIgnoreCase("Finanzas"))
						{
		                  
							condicion = " Cobrador =" + cartera.getCobrador() + " where Clave = " + cliente.getIdCliente(); 
							
						}
						
						
						clienteDAO.actualizarResponsableCartera(condicion);
					}
				}
				
				if(clienteOriginal.getHabilitado() &&  !cliente.getHabilitado()){
					
					List<Long> listaCarteras = catalogoClienteDAO.obteneridsCarterasPoridCliente(clienteOriginal.getIdCliente());
					for (Long idCarteraCliente : listaCarteras ) {
					   
						catalogoClienteDAO.deleteClienteCartera(cliente.getIdCliente(), idCarteraCliente);   
					}
				}

			}
			Boolean existeObjetivo = this.objetivocrecimientoDAO
					.existObjetivoCrecimientoActual(cliente.getIdCliente());
			if (cliente.getObjectivoCrecimiento() != null
					&& !cliente.getObjectivoCrecimiento().isEmpty()
					&& ((clienteOriginal.getObjectivoCrecimiento() != null
					&& !clienteOriginal.getObjectivoCrecimiento()
					.isEmpty() && !cliente
					.getObjectivoCrecimiento().equals(
							clienteOriginal.getObjectivoCrecimiento())) || (clienteOriginal
									.getObjectivoCrecimiento() == null))) {// solo
				// actualizar
				// si hay
				// cambio
				// Double
				// objetivoTranscurrido
				// =
				// objetivocrecimientoDAO.obtenerObjetivoTranscurrido(cliente.getIdCliente());
				Double objetivoTranscurrido = objetivocrecimientoDAO
						.obtenerObjetivoTranscurrido(cliente.getIdCliente());
				Double objetivoMontoTranscurrido = objetivocrecimientoDAO
						.obtenerObjetivoMontoTranscurrido(cliente
								.getIdCliente());

				if (existeObjetivo == false) {
					this.objetivocrecimientoDAO
					.insertObjetivoCrecimientoPorCliente(
							cliente.getIdCliente(),
							cliente.getObjectivoCrecimiento(),
							cliente.getObjetivoCrecimientoFundamental());
				} else {
					Double montoAnualAnterior = clienteDAO
							.getMontoAnualAnteriorPorCliente(cliente
									.getIdCliente());
					this.objetivocrecimientoDAO
					.updateObjetivoCrecimientoPorCliente(cliente
							.getIdCliente(), Double.valueOf(cliente
									.getObjectivoCrecimiento()),
									montoAnualAnterior, objetivoTranscurrido,
									objetivoMontoTranscurrido);
				}
			}
			if (cliente.getObjetivoCrecimientoFundamental() != null
					&& !cliente.getObjetivoCrecimientoFundamental().isEmpty()
					&& ((clienteOriginal.getObjetivoCrecimientoFundamental() != null
					&& !clienteOriginal
					.getObjetivoCrecimientoFundamental()
					.isEmpty() && !cliente
					.getObjetivoCrecimientoFundamental()
					.equals(clienteOriginal
							.getObjetivoCrecimientoFundamental())) || (clienteOriginal
									.getObjetivoCrecimientoFundamental() == null))) {// solo
				// actualizar
				// si
				// hay
				// cambio
				Double objetivoTranscurridoFundamental = objetivocrecimientoDAO
						.obtenerObjetivoFundamentalTranscurrido(cliente
								.getIdCliente());
				Double objetivoMontoTranscurridoFundamental = objetivocrecimientoDAO
						.obtenerObjetivoMontoFundamentalTranscurrido(cliente
								.getIdCliente());
				// Double objetivoTranscurrido =
				// objetivocrecimientoDAO.obtenerObjetivoTranscurrido(cliente.getIdCliente());

				if (existeObjetivo == false) {
					this.objetivocrecimientoDAO
					.insertObjetivoCrecimientoPorCliente(
							cliente.getIdCliente(),
							cliente.getObjectivoCrecimiento(),
							cliente.getObjetivoCrecimientoFundamental());
				} else {
					Double montoAnualAnterior = clienteDAO
							.getMontoAnualAnteriorPorCliente(cliente
									.getIdCliente());
					this.objetivocrecimientoDAO
					.updateObjetivoCrecimientoFundamentalPorCliente(
							cliente.getIdCliente(),
							Double.valueOf(cliente
									.getObjetivoCrecimientoFundamental()),
									montoAnualAnterior,
									objetivoTranscurridoFundamental,
									objetivoMontoTranscurridoFundamental);
				}
			}

			boolean resultado = catalogoClienteDAO.updateCliente(cliente);

			// RECORREMOS LA LISTA DE COMENTARIOS PARA DECIDIR QUE HACER CON
			// CADA COMENTARIO PUEDE SER: INSERTAR, ACTUALIZAR O ELIMINAR
			List<Comentario> listaComentarios = cliente.getListaComentarios();
			if (listaComentarios != null) {
				for (Comentario comentario : listaComentarios) {
					if (comentario.isEliminar()
							&& comentario.getIdComentario() != null
							&& comentario.getIdComentario() > 0) {// Se elimina
						this.comentarioDAO.eliminarComentario(comentario
								.getIdComentario());
					} else if (comentario.getIdComentario() != null
							&& comentario.getIdComentario() > 0) {// Se
						// actualiza
						this.comentarioDAO.actualizarComentario(comentario);
					} else if (comentario.getIdComentario() == null
							|| comentario.getIdComentario() == 0) {// Se inserta
						this.comentarioDAO.insertarComentario(comentario);
					}
				}
			}

			if (cliente.getDireccion() != null) {
				direccionesDAO.updateDireccionEmpresa(cliente.getDireccion());
			}
			return resultado;
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,cliente);
			TransactionAspectSupport.currentTransactionStatus()
			.setRollbackOnly();
			//			//log.info("Error:" + e.getCause());
			return false;
		}
	}

	@Override
	public boolean actulizarClienteCredito(Cliente cliente)
			throws ProquifaNetException {
		try {
			return catalogoClienteDAO.updateClienteCredito(cliente);
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,cliente);
			return false;
		}
	}

	@Override
	public Boolean actualizarClienteFacturacion(Cliente cliente)
			throws ProquifaNetException {
		try {
			if (catalogoClienteDAO.updateClienteFacturacion(cliente)) {
				if (direccionesDAO.updateDireccionFacturacion(cliente
						.getDireccionFacturacion())) {
					List<Comentario> listaComentarios = cliente
							.getListaComentarios();
					if (listaComentarios != null) {
						for (Comentario comentario : listaComentarios) {
							if (comentario.isEliminar()
									&& comentario.getIdComentario() != null
									&& comentario.getIdComentario() > 0) {// Se
								// elimina
								this.comentarioDAO
								.eliminarComentario(comentario
										.getIdComentario());
							} else if (comentario.getIdComentario() != null
									&& comentario.getIdComentario() > 0) {// Se
								// actualiza
								this.comentarioDAO
								.actualizarComentario(comentario);
							} else if (comentario.getIdComentario() == null
									|| comentario.getIdComentario() == 0) {// Se
								// inserta
								this.comentarioDAO
								.insertarComentario(comentario);
							}
						}
					}
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,cliente);
			return false;
		}

	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Boolean actualizarClienteHorario(List<Horario> horarios)
			throws ProquifaNetException {
		Boolean correcto = false;
		try {
			for (Horario horario : horarios) {
				if (horario.getBorrar() != null && horario.getBorrar()) {
					correcto = catalogoClienteDAO.deleteHorarioCliente(horario
							.getIdHorario());
				} else {
					if (horario.getIdHorario() != null && horario.getIdHorario() > 0){
						correcto = catalogoClienteDAO.updateClienteHorario(horario) ;
					}
					else{
						correcto = catalogoClienteDAO.agregarHorarioCliente(horario);
					}
				
						
				
				}
			}
			catalogoClienteDAO.fechaActualizacionCliente(horarios.get(0)
					.getIdCliente());
			return correcto;
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			TransactionAspectSupport.currentTransactionStatus()
			.setRollbackOnly();
			return false;
		}

	}

	@Override
	public Horario obtenerHorarioPorPuntos(Horario horario)
			throws ProquifaNetException {
		// Obtener las fechas de inicio y fin de los dias o de todos los dias de
		// los dos rangos

		try {
			if (!horario.getPlunes().isEmpty()) {
				horario.setLunes(true);
				Integer inicio = horario.getPlunes().get(0);
				Integer fin = inicio, inicio2 = -30, fin2 = 0;
				Integer tmp = inicio - 1;
				for (Integer i : horario.getPlunes()) {
					tmp++;
					if (tmp == i) {
						fin = i;

					} else {
						if (inicio2 == -30) {
							inicio2 = i;
						}
						fin2 = i;
					}

				}
				horario.setLuDe1(obtenerInicioTiempo(inicio));
				horario.setLuA1(obtenerInicioTiempo(fin));
				horario.setLuDe2(obtenerInicioTiempo(inicio2));
				horario.setLuA2(obtenerInicioTiempo(fin2));
			}
			if (!horario.getPmartes().isEmpty()) {
				horario.setMartes(true);
				Integer inicio = horario.getPmartes().get(0);
				Integer fin = inicio, inicio2 = -30, fin2 = 0;
				Integer tmp = inicio - 1;
				for (Integer i : horario.getPmartes()) {
					tmp++;
					if (tmp == i) {
						fin = i;

					} else {
						if (inicio2 == -30) {
							inicio2 = i;
						}
						fin2 = i;
					}

				}
				horario.setMaDe1(obtenerInicioTiempo(inicio));
				horario.setMaA1(obtenerInicioTiempo(fin));
				horario.setMaDe2(obtenerInicioTiempo(inicio2));
				horario.setMaA2(obtenerInicioTiempo(fin2));
			}
			if (!horario.getPmiercoles().isEmpty()) {
				horario.setMiercoles(true);
				Integer inicio = horario.getPmiercoles().get(0);
				Integer fin = inicio, inicio2 = -30, fin2 = 0;
				Integer tmp = inicio - 1;
				for (Integer i : horario.getPmiercoles()) {
					tmp++;
					if (tmp == i) {
						fin = i;

					} else {
						if (inicio2 == -30) {
							inicio2 = i;
						}
						fin2 = i;
					}

				}
				horario.setMiDe1(obtenerInicioTiempo(inicio));
				horario.setMiA1(obtenerInicioTiempo(fin));
				horario.setMiDe2(obtenerInicioTiempo(inicio2));
				horario.setMiA2(obtenerInicioTiempo(fin2));
			}
			if (!horario.getPjueves().isEmpty()) {
				horario.setJueves(true);
				Integer inicio = horario.getPjueves().get(0);
				Integer fin = inicio, inicio2 = -30, fin2 = 0;
				Integer tmp = inicio - 1;
				for (Integer i : horario.getPjueves()) {
					tmp++;
					if (tmp == i) {
						fin = i;

					} else {
						if (inicio2 == -30) {
							inicio2 = i;
						}
						fin2 = i;
					}

				}
				horario.setJuDe1(obtenerInicioTiempo(inicio));
				horario.setJuA1(obtenerInicioTiempo(fin));
				horario.setJuDe2(obtenerInicioTiempo(inicio2));
				horario.setJuA2(obtenerInicioTiempo(fin2));
			}
			if (!horario.getPviernes().isEmpty()) {
				horario.setViernes(true);
				Integer inicio = horario.getPviernes().get(0);
				Integer fin = inicio, inicio2 = -30, fin2 = 0;
				Integer tmp = inicio - 1;
				for (Integer i : horario.getPviernes()) {
					tmp++;
					if (tmp == i) {
						fin = i;

					} else {
						if (inicio2 == -30) {
							inicio2 = i;
						}
						fin2 = i;
					}

				}
				horario.setViDe1(obtenerInicioTiempo(inicio));
				horario.setViA1(obtenerInicioTiempo(fin));
				horario.setViDe2(obtenerInicioTiempo(inicio2));
				horario.setViA2(obtenerInicioTiempo(fin2));
			}

			return horario;
		} catch (Exception e) {
			//			//log.info(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,horario);
			return new Horario();
		}
	}

	public String obtenerInicioTiempo(Integer i) {
		try {
			String hora = "07:00";

			switch (i) {
			case -2:
				hora = "07:00";
				break;
			case -1:
				hora = "07:30";
				break;
			case 0:
				hora = "08:00";
				break;
			case 1:
				hora = "08:30";
				break;
			case 2:
				hora = "09:00";
				break;
			case 3:
				hora = "09:30";
				break;
			case 4:
				hora = "10:00";
				break;
			case 5:
				hora = "10:30";
				break;
			case 6:
				hora = "11:00";
				break;
			case 7:
				hora = "11:30";
				break;
			case 8:
				hora = "12:00";
				break;
			case 9:
				hora = "12:30";
				break;
			case 10:
				hora = "13:00";
				break;
			case 11:
				hora = "13:30";
				break;
			case 12:
				hora = "14:00";
				break;
			case 13:
				hora = "14:30";
				break;
			case 14:
				hora = "15:00";
				break;
			case 15:
				hora = "15:30";
				break;
			case 16:
				hora = "16:00";
				break;
			case 17:
				hora = "16:30";
				break;
			case 18:
				hora = "17:00";
				break;
			case 19:
				hora = "17:30";
				break;
			case 20:
				hora = "18:00";
				break;
			case 21:
				hora = "18:30";
				break;
			case 22:
				hora = "19:00";
				break;
			case 23:
				hora = "19:30";
				break;
			case 24:
				hora = "20:00";
				break;
			case 25:
				hora = "20:30";
				break;
			case 26:
				hora = "21:00";
				break;
			case 27:
				hora = "21:30";
				break;
			case 28:
				hora = "22:00";
				break;
			case 29:
				hora = "22:30";
				break;
			}
			return hora;
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"i: " + i);
			return "07:00";
		}
	}

	@Override
	public List<Horario> listarHorariosDireccionVisitaCliente(Long idDireccion) throws ProquifaNetException {

		try {
			List<Horario> horario = catalogoClienteDAO.obtenerHorarioCliente(idDireccion);

			for (Horario h : horario) {
				Point tmp = new Point();
				Point tmp2 = new Point();

				ArrayList<Integer> lunes = new ArrayList<Integer>();
				ArrayList<Integer> martes = new ArrayList<Integer>();
				ArrayList<Integer> miercoles = new ArrayList<Integer>();
				ArrayList<Integer> jueves = new ArrayList<Integer>();
				ArrayList<Integer> viernes = new ArrayList<Integer>();

				if (h.getLunes()) {
					if (h.getLuDe1() != null & h.getLuA1() != null) {
						tmp = obtenerCoordenadaHorario(h.getLuDe1(), h.getLuA1());
					}
					if (h.getLuDe2() != null & h.getLuA2() != null) {
						tmp2 = obtenerCoordenadaHorario(h.getLuDe2(), h.getLuA2());
					}
					for (int i = (int) tmp.getX(); i <= (int) tmp.getY(); i++) { // Primer
						// periodo
						lunes.add(i);
					}
					for (int i = (int) tmp2.getX(); i <= (int) tmp2.getY(); i++) { // Segundo
						// periodo
						lunes.add(i);
					}
					h.setPlunes(lunes);
				}
				if (h.getMartes()) {
					if (h.getMaDe1() != null & h.getMaA1() != null) {
						tmp = obtenerCoordenadaHorario(h.getMaDe1(), h.getMaA1());
					}
					if (h.getMaDe2() != null & h.getMaA2() != null) {
						tmp2 = obtenerCoordenadaHorario(h.getMaDe2(), h.getMaA2());
					}
					for (int i = (int) tmp.getX(); i <= (int) tmp.getY(); i++) { // Primer
						// periodo
						martes.add(i);
					}
					for (int i = (int) tmp2.getX(); i <= (int) tmp2.getY(); i++) { // Segundo
						// periodo
						martes.add(i);
					}
					h.setPmartes(martes);

				}
				if (h.getMiercoles()) {
					if (h.getMiDe1() != null & h.getMiA1() != null) {
						tmp = obtenerCoordenadaHorario(h.getMiDe1(), h.getMiA1());
					}
					if (h.getMiDe2() != null & h.getMiA2() != null) {
						tmp2 = obtenerCoordenadaHorario(h.getMiDe2(), h.getMiA2());
					}
					for (int i = (int) tmp.getX(); i <= (int) tmp.getY(); i++) { // Primer
						// periodo
						miercoles.add(i);
					}
					for (int i = (int) tmp2.getX(); i <= (int) tmp2.getY(); i++) { // Segundo
						// periodo
						miercoles.add(i);
					}
					h.setPmiercoles(miercoles);

				}
				if (h.getJueves()) {
					if (h.getJuDe1() != null & h.getJuA1() != null) {
						tmp = obtenerCoordenadaHorario(h.getJuDe1(), h.getJuA1());
					}
					if (h.getJuDe2() != null & h.getJuA2() != null) {
						tmp2 = obtenerCoordenadaHorario(h.getJuDe2(), h.getJuA2());
					}
					for (int i = (int) tmp.getX(); i <= (int) tmp.getY(); i++) { // Primer
						// periodo
						jueves.add(i);
					}
					for (int i = (int) tmp2.getX(); i <= (int) tmp2.getY(); i++) { // Segundo
						// periodo
						jueves.add(i);
					}
					h.setPjueves(jueves);
				}
				if (h.getViernes()) {
					if (h.getViDe1() != null & h.getViA1() != null) {
						tmp = obtenerCoordenadaHorario(h.getViDe1(), h.getViA1());
					}
					if (h.getViDe2() != null & h.getViA2() != null) {
						tmp2 = obtenerCoordenadaHorario(h.getViDe2(), h.getViA2());
					}
					for (int i = (int) tmp.getX(); i <= (int) tmp.getY(); i++) { // Primer
						// periodo
						viernes.add(i);
					}
					for (int i = (int) tmp2.getX(); i <= (int) tmp2.getY(); i++) { // Segundo
						// periodo
						viernes.add(i);
					}
					h.setPviernes(viernes);

				}
			}

			return horario;
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"idDireccion: " + idDireccion);
			return new ArrayList<Horario>();
		}

	}

	@Override
	public Point obtenerCoordenadaHorario(String inicio, String fin)
			throws ProquifaNetException {

		try {
			String strHrI = "", strHrF = "";
			Point p = new Point();
			strHrI = inicio.substring(0, 2);
			strHrF = fin.substring(0, 2);
			Double x = 0.0, y = 0.0;// , x1 = 0.0, y1 = 0.0;

			Integer hr = Integer.parseInt(strHrI);
			Integer hr2 = Integer.parseInt(strHrF);
			x = obtenerPunto(hr);
			y = obtenerPunto(hr2);
			if (x != -30.0 || y != -30.0) {

				strHrI = inicio.substring(3, 5);
				strHrF = fin.substring(3, 5);
				hr = Integer.parseInt(strHrI);
				hr2 = Integer.parseInt(strHrF);
				// x1 = obtenerPunto(hr);
				// y1 = obtenerPunto(hr2);

				if (hr > 0) {
					x++;
				}

				if (hr2 > 0) {
					y++;
				}

				p.setLocation(x, y);

				return p;
			} else {
				return null;
			}
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"inicio: " + inicio, "fin: " + fin);
			return new Point();
		}

	}

	public Double obtenerPunto(Integer i) {
		try {
			Double x = -30.0;

			switch (i) {
			case 7:
				x = -2.0;
				break;
			case 8:
				x = 0.0;
				break;
			case 9:
				x = 2.0;
				break;
			case 10:
				x = 4.0;
				break;
			case 11:
				x = 6.0;
				break;
			case 12:
				x = 8.0;
				break;
			case 13:
				x = 10.0;
				break;
			case 14:
				x = 12.0;
				break;
			case 15:
				x = 14.0;
				break;
			case 16:
				x = 16.0;
				break;
			case 17:
				x = 18.0;
				break;
			case 18:
				x = 20.0;
				break;
			case 19:
				x = 22.0;
				break;
			case 20:
				x = 24.0;
				break;
			case 21:
				x = 26.0;
				break;
			}

			return x;
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"i: " + i);
			return -30D;
		}

	}

	public Boolean actualizarDirreccionVisitaCliente(Direccion d)
			throws ProquifaNetException {
		try {
			boolean correcto = false;
			if (d.getBorrar() != null && d.getBorrar()) {
				correcto = catalogoClienteDAO.deleteHorarioClientePorIdDIreccion(d.getIdDireccion());
				correcto = catalogoClienteDAO.deleteDireccionCliente(d.getIdDireccion());
			} else {
				Long idDireccion = -1L;
				if (d.getIdDireccion() != null && d.getIdDireccion() > 0) {
					correcto = catalogoClienteDAO.updateDireccionCliente(d);
					idDireccion = d.getIdDireccion();
				} else {
					idDireccion = catalogoClienteDAO.agregarDireccionCliente(d);
					if(idDireccion > 0)
					correcto = true;
				   log.info("",idDireccion);
				    
				}
				if (d.getHorarios() != null && !d.getHorarios().isEmpty() && !d.isVisita()) {
					for (Horario horario : d.getHorarios()) {
						horario.setIdDireccion(idDireccion);
					}
					this.actualizarClienteHorario(d.getHorarios());
				}
			}
			return correcto;
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,d);
			return false;
		}

	}

	@Override
	public List<ConfiguracionPrecioProducto> obtenerConfiguracionPrecioCliente(
			Long idProveedor, Long idCliente, String tipoNivelIngreso,
			Long idConfigPrecio) throws ProquifaNetException {
		try {
			List<ConfiguracionPrecioProducto> result = null;

			result = configurarElementos(catalogoClienteDAO
					.findConfiguracionPrecioProductoCliente(idProveedor,
							idCliente, tipoNivelIngreso, idConfigPrecio), true,true);

			return result;
		} catch (Exception e) {
			log.info("Error:" + e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"idProveedor: " + idProveedor, "idCliente: " + idCliente
					,"tipoNivelIngreso: " + tipoNivelIngreso, "idConfigPrecio: " + idConfigPrecio);
			return new ArrayList<ConfiguracionPrecioProducto>();
		}
		// return ;
	}

	@Override
	public List<ConfiguracionPrecioProducto> obtenerConfiguracionPrecioClientePorProducto(
			Long idProveedor, Long idCliente, String tipoNivelIngreso,
			Long idConfigPrecio, Long idProducto) throws ProquifaNetException {
		try {
			List<ConfiguracionPrecioProducto> result = null;
			
			result = configurarElementos(catalogoClienteDAO
					.findConfiguracionPrecioProductoClientePorProducto(idProveedor,
							idCliente, tipoNivelIngreso, idConfigPrecio, idProducto), true,true);
			
			return result;
		} catch (Exception e) {
			log.info("Error:" + e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"idProveedor: " + idProveedor, "idCliente: " + idCliente
					,"tipoNivelIngreso: " + tipoNivelIngreso, "idConfigPrecio: " + idConfigPrecio);
			return new ArrayList<ConfiguracionPrecioProducto>();
		}
		// return ;
	}

	private List<ConfiguracionPrecioProducto> configurarElementos(
			List<ConfiguracionPrecioProducto> elementos, Boolean descripcion, Boolean precioU) throws ProquifaNetException {
		try{
			for (ConfiguracionPrecioProducto elemento : elementos) {
				//Colocar descripcion de producto
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
					producto.setCategoriaPrecioLista(elemento.getProducto()
							.getCategoriaPrecioLista());				
					producto.setConfiguracion_Precio(elemento.getProducto()
							.getConfiguracion_Precio());
					producto.setIndustria(elemento.getProducto().getIndustria());
					producto.setLicencia(elemento.getProducto().getLicencia());

					producto.setConcepto(this.productoDAO.obtenerDescripcionProductoPorId(elemento.getProducto().getIdProducto()) );

					elemento.setProducto(producto);
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

					elemento.getCostoFactorProducto().setPrecioUCliente((double) Math.round(precioUCliente));

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

					elemento.getCostoFactorProducto().setPrecioUTemporalCliente((double) Math.round(precioUCliente));

					if(elemento.getCostoFactorProducto().getDiferencialTemporalCliente() == null || elemento.getCostoFactorProducto().getDiferencialTemporalCliente() == 0){
						elemento.getCostoFactorProducto().setDiferencialTemporalCliente(1.0);
					}

					elemento.getCostoFactorProducto().setDiferencialTemporalCliente	(Math.rint(((((elemento.getCostoFactorProducto().getPrecioUTemporalCliente()) / (elemento.getCostoFactorProducto().getDiferencialTemporalCliente())) * 100) - 100) * 1e2) / 1e2 );
				}
			}
			return elementos;
		}catch(Exception e){
			e.printStackTrace();
			log.info(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"descripcion: " + descripcion, "precioU: " + precioU);
			return elementos;

		}
	}

	@Override
	@Transactional
	public Boolean actualizarFactorCliente(ParametrosOfertaCliente parametros)
			throws ProquifaNetException {

		try {
			this.idClienteConfiguracionPrecio = 0L;
			String colCliente = "";


			if (parametros.getNivelConfigPrecio().equals("Familia")) {
				colCliente = "FK05_CliFamilia";
			} else if (parametros.getNivelConfigPrecio().equals("Costo")) {
				colCliente = "FK06_CliCosto";
			} else if (parametros.getNivelConfigPrecio().equals("Producto")) {
				colCliente = "FK07_CliProducto";
			} else if (parametros.getNivelConfigPrecio().equals("Clasificacion")) {
				colCliente = "FK09_CliClasificacion";
			}

			if(parametros.getTemporal()){
				parametros.getDuracionTemp();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");
				Calendar calendar = Calendar.getInstance();				

				calendar.add(Calendar.MINUTE, parametros.getDuracionTemp());
				Date caducidad =  calendar.getTime();

				Boolean existeConfig = catalogoClienteDAO.existConfiguracionCliente(parametros.getIdConfigPrecioProd(), parametros.getIdCliente(), colCliente);
				log.info("Existe Configuración: " + existeConfig);

				if(existeConfig){
					catalogoClienteDAO.updateConfiguracionClienteTemporal(parametros, colCliente, caducidad);
				}else{
					catalogoClienteDAO.insertFactorClienteTemporal(parametros, colCliente, caducidad);
				}

			}else{
				Cliente cli;
				int existeFactor = 0;
				long corporativo = corporativoDAO.getCorporativoPorCliente(parametros.getIdCliente());
				List<NivelIngreso> niveles = nivelIngresoDAO.findLimitesNivelIngreso();
				List<Cliente> clientes = corporativoDAO.obtenerClientesPorCorporativo(corporativo, niveles);

				if(clientes.isEmpty()){
					//				//log.info("no tiene corporativo");
					cli = new Cliente();
					cli.setIdCliente(parametros.getIdCliente());
					cli.setNivelIngreso(parametros.getTipoNivelIngreso());
					clientes = new ArrayList<Cliente>();
					clientes.add(cli);
				}

				for(Cliente c : clientes){
					//				//log.info("Nombre: " + c.getNombre());
					if (parametros.getRestablecer() == false) {

						existeFactor = catalogoClienteDAO.updateFactorCliente(parametros, colCliente, c.getIdCliente(), c.getNivelIngreso());

						if (existeFactor != 0) { // hace bien la actualizacion
							//						//log.info("existe");
							// Obtengo el idClienteConfiguracionPrecio, el array ya debe  de traer los idTiempoEntrega
							Long idClienteConfiguracionPrecio = this.idClienteConfiguracionPrecio =  catalogoClienteDAO.getClienteConfiguracionPrecio(parametros.getIdConfigPrecioProd(),c.getIdCliente(), colCliente);
							// //log.info("Actualizando Ya tiene factor, ClienteConfiguracionPrecio: "+idClienteConfiguracionPrecio);
							// Ahora actualizamos los tiempos de entrega en la tabla
							// tiempoEntregaRuta
							// Validar si aun no existen tiempo de entrega para esa
							// configuracion entonces insertamos la lista que manda
							// vista
							Long existenTiemposEntrega = catalogoClienteDAO.getCantidadTiemposEntrega(idClienteConfiguracionPrecio, c.getIdCliente());
							if (existenTiemposEntrega == 0) {// Insertamos
								// //log.info("La lista es de : "+tiemposEntregaRuta.size()
								// + "Registros...");
								for (TiempoEntrega tiempoEntregaRuta : parametros.getTiemposEntregaRuta()) {
									tiempoEntregaRuta
									.setIdConfiguracionPrecio(idClienteConfiguracionPrecio);
									this.catalogoProveedorDAO
									.insertarTiempoEntregaRuta(
											tiempoEntregaRuta, c.getIdCliente());
								}
							} else {// actualizamos
								for (TiempoEntrega tiempoEntregaRuta : parametros.getTiemposEntregaRuta()) {
									tiempoEntregaRuta.setIdConfiguracionPrecio(idClienteConfiguracionPrecio);
									this.catalogoProveedorDAO
									.actualizarTiempoEntregaRuta(
											tiempoEntregaRuta, c.getIdCliente());
								}
							}

						} else {
							//						//log.info("inserta");
							catalogoClienteDAO.insertFactorCliente( parametros,  colCliente,  c.getIdCliente(),  c.getNivelIngreso());// Inserta
							// Obtengo el idClienteConfiguracionPrecio
							Long idClienteConfiguracionPrecio = this.idClienteConfiguracionPrecio = catalogoClienteDAO
									.getClienteConfiguracionPrecio(parametros.getIdConfigPrecioProd(),
											c.getIdCliente(), colCliente);
							for (TiempoEntrega tiempoEntregaRuta : parametros.getTiemposEntregaRuta()) {
								tiempoEntregaRuta
								.setIdConfiguracionPrecio(idClienteConfiguracionPrecio);
								this.catalogoProveedorDAO.insertarTiempoEntregaRuta(
										tiempoEntregaRuta, c.getIdCliente());
							}

						}


					} else {

						catalogoClienteDAO.updateFactorCliente(parametros, colCliente, c.getIdCliente(), c.getNivelIngreso());

						// Eliminar los tiempos de entrega solo si la configuracion a borrar es definitiva
						if(parametros.getTemporal() == false){
							Long idClienteConfiguracionPrecio = this.idClienteConfiguracionPrecio = catalogoClienteDAO
									.getClienteConfiguracionPrecio(parametros.getIdConfigPrecioProd(),
											c.getIdCliente(), colCliente);
							if (idClienteConfiguracionPrecio > 0) {
								catalogoClienteDAO
								.eliminarRegistroTiempoEntregaRuta(idClienteConfiguracionPrecio);
							}
						}
					}
				}
			}
			return true;
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,parametros);
			TransactionAspectSupport.currentTransactionStatus()
			.setRollbackOnly();
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public ConfiguracionPrecioProducto getConfiguracionPrecioCliente(
			Long idConfiguracion, Long CategoriaPrecio, Long idCliente,
			String tipoNivelIngreso) throws ProquifaNetException {
		try {
			List<ConfiguracionPrecioProducto> result = this.catalogoProveedorDAO
					.obtenerConfiguracionPrecioXId(idConfiguracion, idCliente,
							tipoNivelIngreso, CategoriaPrecio, "");// cambia,true,false)
			List<ConfiguracionPrecioProducto> re = new ArrayList<ConfiguracionPrecioProducto>();
			re.add(result.get(0));
			re = configurarElementos(re, true, false);
			ConfiguracionPrecioProducto r = re.get(0);

			Producto pro = r.getProducto();
			pro.setCategoriaPrecioLista(CategoriaPrecio);
			pro.setCosto((double) this.catalogoProveedorDAO
					.getCostoXCategoriaPrecioLista(CategoriaPrecio));
			r.setProducto(pro);
			return r;
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"idConfiguracion: " + idConfiguracion, "CategoriaPrecio: " + CategoriaPrecio,
					"idCliente: " + idCliente, "tipoNivelIngreso: " + tipoNivelIngreso);
			return new ConfiguracionPrecioProducto();
		}
	}

	@Override
	public List<ConfiguracionPrecioProducto> cargarConfiguracionFamiliaCliente(
			Long idConfig, Long idCliente, String tipoNivel)
					throws ProquifaNetException {
		try {
			return this.catalogoProveedorDAO.findConfiguracionFamilia(idConfig,idCliente, tipoNivel);
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"idConfig: " + idConfig, "idCliente: " + idCliente,
					"tipoNivel: " + tipoNivel);
			return new ArrayList<ConfiguracionPrecioProducto>();
		}
	}

	@Override
	public FormulaPrecio obtenerInformacionFormulaPrecioCliente(
			Long idProveedor, Long idProducto, String nivel, Long idCliente,
			Long idConfig) throws ProquifaNetException {
		try {
			//			//log.info("Entra a obtenerInformacionFormulaPrecio");

			int stock_flete = 0;

			if (nivel.toUpperCase().equals("FEXPRESS")) {
				stock_flete = 1;
			} else {
				stock_flete = 0;

			}
			return this.catalogoProveedorDAO.getInformacionFormulaPrecio(idProveedor, idProducto, stock_flete, nivel, idCliente,idConfig);
		} catch (Exception e) {
			//log.info(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"idConfig: " + idConfig, "idCliente: " + idCliente);
			return new FormulaPrecio();
		}
	}
	@Override
	public FormulaPrecio obtenerInformacionFormulaPrecioClienteTemp(
			Long idProveedor, Long idProducto, String nivel, Long idCliente,
			Long idConfig) throws ProquifaNetException {
		try {
			//			//log.info("Entra a obtenerInformacionFormulaPrecio");

			int stock_flete = 0;

			if (nivel.toUpperCase().equals("FEXPRESS")) {
				stock_flete = 1;
			} else {
				stock_flete = 0;

			}
			return this.catalogoClienteDAO.getInformacionFormulaPrecioTemp(idProveedor, idProducto, stock_flete, nivel, idCliente,idConfig);
		} catch (Exception e) {
			//log.info(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"idConfig: " + idConfig, "idCliente: " + idCliente);
			return new FormulaPrecio();
		}
	}

	@Override
	public ConfiguracionPrecioProducto getConfiguracionPrecioClienteCosto(
			Long idConfiguracionCosto, Long CategoriaPrecio, Long idCliente,
			String tipoNivelIngreso) throws ProquifaNetException {
		try {
			List<ConfiguracionPrecioProducto> result = configurarElementos(this.catalogoClienteDAO.getConfiguracionPrecioClienteCosto(idConfiguracionCosto,CategoriaPrecio, idCliente, tipoNivelIngreso),true,false);// cambia
			ConfiguracionPrecioProducto r = result.get(0);

			Producto pro = r.getProducto();
			pro.setCategoriaPrecioLista(CategoriaPrecio);
			pro.setCosto((double) this.catalogoProveedorDAO
					.getCostoXCategoriaPrecioLista(CategoriaPrecio));
			r.setProducto(pro);
			return r;
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"idConfiguracionCosto: " + idConfiguracionCosto,
					"idCliente: " + idCliente, "CategoriaPrecio: " + CategoriaPrecio);
			return new ConfiguracionPrecioProducto();
		}
	}

	@Override
	public List<ConfiguracionPrecioProducto> obtenerConfiguracionesPrecioCostoCliente(
			Long idConfigFam, Long idCliente, String tipoNivelIngreso)
					throws ProquifaNetException {
		try {

			return configurarElementos(this.catalogoClienteDAO.findConfiguracionesPrecioCostoCliente(idConfigFam,idCliente, tipoNivelIngreso), false,true);
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"idCliente: " + idCliente);
			return new ArrayList<ConfiguracionPrecioProducto>();
		}
	}

	@Override
	public ConfiguracionPrecioProducto obtenerConfiguracionPrecioClasificacionCliente(
			Long idConfigFam, Long idClasificacion, Long idCliente,
			String tipoNivelIngreso) throws ProquifaNetException {
		try {
			List<ConfiguracionPrecioProducto> result = configurarElementos(this.catalogoClienteDAO
					.findConfiguracionClasificacionCliente(idConfigFam, idCliente,
							tipoNivelIngreso, idClasificacion),true,false);
			ConfiguracionPrecioProducto r = result.get(0);
			return r;
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"idConfigFam: " + idConfigFam,"idClasificacion: "+idClasificacion
					,"idCliente: " + idCliente,"tipoNivelIngreso: " + tipoNivelIngreso);
			return new ConfiguracionPrecioProducto();
		}
	}

	@Override
	public List<ConfiguracionPrecioProducto> obtenerClasificacionPrecioProductoPorCatPrecioCliente(
			Long idClasificacion, String tipoNivelIngreso, Long idCliente)
					throws ProquifaNetException {
		// TODO Auto-generated method stub
		try {
			return configurarElementos(this.catalogoClienteDAO.findClasificacionPrecioProductoPorCatPrecioCliente(idClasificacion, tipoNivelIngreso, idCliente),false,true);

		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"idClasificacion: "+idClasificacion
					,"idCliente: " + idCliente,"tipoNivelIngreso: " + tipoNivelIngreso);
			return new ArrayList<ConfiguracionPrecioProducto>();
		}
	}

	@Override
	public FormulaPrecio obtenerInformacionFormulaPrecioClasificacionCliente(
			Long idProveedor, Long idProducto, String nivel, Long idConfigFam,
			Long idCliente) throws ProquifaNetException {
		try {
			return this.catalogoProveedorDAO
					.getInformacionFormulaPrecioClasificacion(idProveedor,
							idProducto, 0, nivel, idCliente, idConfigFam);
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"idProveedor: " + idProveedor,"idProducto:" + idProducto,
					"nivel: " + nivel,"idConfigFam: " + idConfigFam,"idCliente: " + idCliente);
			return new FormulaPrecio();
		}
	}
	@Override
	public FormulaPrecio obtenerInformacionFormulaPrecioClasificacionTempCliente(
			Long idProveedor, Long idProducto, String nivel, Long idConfigFam,
			Long idCliente) throws ProquifaNetException {
		try {
			return this.catalogoClienteDAO
					.getInformacionFormulaPrecioClasificacionTemp(idProveedor,
							idProducto, 0, nivel, idCliente, idConfigFam);
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"idProveedor: " + idProveedor,"idProducto:" + idProducto,
					"nivel: " + nivel,"idConfigFam: " + idConfigFam,"idCliente: " + idCliente);
			return new FormulaPrecio();
		}
	}

	@Override
	public List<ClasificacionConfiguracionPrecio> obtenerConfiguracionesPrecioClasificacionCliente(
			Long idConfigFamilia, Long idCliente) throws ProquifaNetException {
		try {
			return this.catalogoProveedorDAO.findConceptoClasifConfigPrecio(
					idConfigFamilia, idCliente);
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"idCliente: " + idCliente, "idConfigFamilia:" + idConfigFamilia);
			return new ArrayList<ClasificacionConfiguracionPrecio>();
		}
	}

	@Override
	@Transactional
	public Boolean restableceTodasConfiguracionesPorNivel(String nivelConfig,
			Long idConfigFamilia, Long idCliente) throws ProquifaNetException {
		String colCliente = "";
		if (nivelConfig.equals("Familia")) {
			colCliente = "FK05_CliFamilia";
		} else if (nivelConfig.equals("Costo")) {
			colCliente = "FK06_CliCosto";
		} else if (nivelConfig.equals("Producto")) {
			colCliente = "FK07_CliProducto";
		} else if (nivelConfig.equals("Clasificacion")) {
			colCliente = "FK09_CliClasificacion";
		}

		try{
			Cliente cli;
			long corporativo = corporativoDAO.getCorporativoPorCliente(idCliente);
			List<NivelIngreso> niveles = nivelIngresoDAO.findLimitesNivelIngreso();
			List<Cliente> clientes = corporativoDAO.obtenerClientesPorCorporativo(corporativo, niveles);

			if(clientes.isEmpty()){
				//log.info("no tiene corporativo");
				cli = new Cliente();
				cli.setIdCliente(idCliente);
				clientes = new ArrayList<Cliente>();
				clientes.add(cli);
			}

			for(Cliente c : clientes){
				//log.info(c.getNombre());
				this.catalogoClienteDAO.reintegrarConfiguracionesPorNivel(colCliente, c.getIdCliente(), idConfigFamilia);
			}
			return true;
		}catch(Exception e){
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"idCliente: " + idCliente, "idConfigFamilia:" + idConfigFamilia);
			TransactionAspectSupport.currentTransactionStatus()
			.setRollbackOnly();
			log.info(e.getMessage());
			return false;
		}


	}

	// @Override
	// public List<Cliente> obtenerClientesSinCorporativo()
	// throws ProquifaNetException {
	// List<NivelIngreso> ni = nivelIngresoDAO.findLimitesNivelIngreso();
	// return catalogoClienteDAO.findObtenerClientes(1L, ni,true,null);
	// }

	// @Override
	// public List<Cliente> obtenerClientesXUsuario(Empleado usuario,Long
	// habilitado)
	// throws ProquifaNetException {
	// List<NivelIngreso> ni = nivelIngresoDAO.findLimitesNivelIngreso();
	// List<Cliente> clientes =
	// catalogoClienteDAO.findObtenerClientes(habilitado, ni,false,usuario);
	// for (Cliente cliente : clientes) {
	// Direccion direccion = (Direccion)
	// direccionesDAO.getDatosdeEmpresa(cliente.getIdCliente());
	// if (direccion!=null )
	// cliente.setDireccion(direccion);
	// }
	//
	// return clientes;
	// }

	@Override
	public List<Direccion> listarDireccionesVisitaCliente(Long idCliente)
			throws ProquifaNetException {
		try {
			return catalogoClienteDAO.findDireccionesCliente(idCliente);
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"idCliente: " + idCliente, "idConfigFamilia:");
			return new ArrayList<Direccion>();
		}
	}

	@Override
	public Cliente obtenerClienteXId(Long idCliente)
			throws ProquifaNetException {
		try {
			List<NivelIngreso> ni = nivelIngresoDAO.findLimitesNivelIngreso();
			if (idCliente != null && idCliente > 0) {
				Cliente cliente = catalogoClienteDAO.obtenerClienteXId(
						idCliente, ni);

				if (cliente != null) {
					Direccion direccion = (Direccion) direccionesDAO.getDireccionEmpresa(idCliente);
					Direccion dFacturacion = (Direccion) direccionesDAO.getDireccionFacturacion(idCliente);
					cliente.setDireccion(direccion);
					cliente.setDireccionFacturacion(dFacturacion);
					
				}
				
				return cliente;
			} else {
				return null;
			}
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"idCliente: " + idCliente);
			return new Cliente();
		}

	}

	@Override
	public List<Comentario> listarComentariosCliente(Long idCliente)
			throws ProquifaNetException {
		try {
			return this.catalogoClienteDAO.findComentariosCliente(idCliente);
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"idCliente: " + idCliente);
			return new ArrayList<Comentario>();
		}
	}

	@Override
	public List<Direccion> obtenerDireccionCliente(Long idCliente)
			throws ProquifaNetException {
		try {
			List<Direccion> direcciones = new ArrayList<Direccion>();

			Direccion dEmpresa = direccionesDAO.getDireccionEmpresa(idCliente);
			Direccion dFacturacion = direccionesDAO.getDireccionFacturacion(idCliente);

			direcciones.add(dEmpresa);
			direcciones.add(dFacturacion);

			return direcciones;
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"idCliente: " + idCliente);
			return new ArrayList<Direccion>();
		}
	}

	@Override
	public Long ActualizarCartera(Cartera cartera, Long idUsuario)
			throws ProquifaNetException {
		try {
			Long resultado = 0L;
			// determinar si se inserta o se actualiza en base al idcategoria
			if (cartera.getIdcartera() != 0) {
				boolean hecho = false;
				if (cartera.isPublicada()) {
					String clientes = "";
					List<CarteraCliente> cliente2 = cartera.getClientes();
					for (CarteraCliente cliente : cliente2) {
						if (cliente.getIdCliente() != 0
								&& !(cliente.getIdCliente() == null)) {
							if (clientes == "") {
								clientes = "(" + cliente.getIdCliente() + ") ";
							} else {
								clientes += " ,(" + cliente.getIdCliente()
										+ ") ";
							}

						}
					}
					
					String actualizaPendientesESAC = "";
					String actualizaPendientesCobrador = "";
					String actualizaPendientesEVT = "";
					if(cartera.getArea().equals("ESAC") || cartera.getArea().equals("Direccion")){
						actualizaPendientesESAC = this.obtenFoliosPendientesCartera("ESAC");
						actualizaPendientesEVT = this.obtenFoliosPendientesCartera("EVT");
					}
					
					if(cartera.getArea().equals("Finanzas") || cartera.getArea().equals("Direccion")){
						actualizaPendientesCobrador = this.obtenFoliosPendientesCartera("COBRADOR");
					}
					
					if (cartera.isCart_updatePublicada()) {
						cartera.setIdCorporativo(this.catalogoClienteDAO.getidCorporativoporidCartera(cartera.getIdcartera()));
						hecho = this.catalogoClienteDAO.updateCarteraPublicada(cartera, actualizaPendientesESAC, actualizaPendientesEVT, actualizaPendientesCobrador, clientes,idUsuario);
					} else {						
						hecho = this.catalogoClienteDAO.publicaCartera(cartera.getIdcartera(), actualizaPendientesESAC, actualizaPendientesEVT, actualizaPendientesCobrador, clientes, cartera, idUsuario);
					}

				} else {

					// acturaliza
					//log.info("se actualiza una cartera no publicada ");
					hecho = this.catalogoClienteDAO.updateCartera(cartera);
				}
				if (hecho) {
					resultado = cartera.getIdcartera();
				} else {
					resultado = -1L;
				}
			} else {
				// inserta
				//log.info("se guarda un borrador ");
				resultado = this.catalogoClienteDAO.insertCartera(cartera);
			}
			return resultado;
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,cartera, "idUsuario: " + idUsuario);
			return -1L;// TODO: handle exception
		}
	}

	@Override
	public boolean EliminarCartera(long idcartera, long idUsuario)
			throws ProquifaNetException {
		try {
			Long idcorporativo = this.catalogoClienteDAO
					.getidCorporativoporidCartera(idcartera);
			List<Long> clientes = this.catalogoClienteDAO
					.findClientesCartera(idcartera);
			String listaClientes = "";
			for (Long i : clientes) {
				if (listaClientes == "") {
					listaClientes = " " + Long.toString(i);
				} else {
					listaClientes += " , " + Long.toString(i);
				}
			}
			return this.catalogoClienteDAO.deleteCartera(idcartera, idcorporativo,
					idUsuario, listaClientes, true);
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"idcartera: " + idcartera, "idUsuario: " + idUsuario);
			return false;
		}
	}

	@Override
	public List<CarteraCliente> obtenerClientesSinCartera(boolean sinCartera , String area) throws ProquifaNetException {
		try {
			String parametros = "";
			List<NivelIngreso> niveles = nivelIngresoDAO.findLimitesNivelIngreso();
			if (sinCartera) {
				parametros += " AND cli.Clave NOT IN (SELECT FK01_Cliente FROM Cliente_Cartera WHERE FK02_Cartera IS NOT NULL)";
			}
			return this.catalogoClienteDAO.findClientesSinCartera(parametros, niveles, area);
		} catch (Exception e) {
			log.info(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			return new ArrayList<CarteraCliente>();
		}
	}

	@Override
	public List<CarteraCliente> obtenerCarteras() throws ProquifaNetException {
		try {
			List<CarteraCliente> carteras = null;
			List<CarteraCliente> carteras2 = null;
			List<NivelIngreso> niveles = nivelIngresoDAO
					.findLimitesNivelIngreso();
			carteras = this.catalogoClienteDAO.findCarteras("", niveles);
			carteras2 = this.catalogoClienteDAO.findMontosGeneralesCarteras("",
					niveles);
			for (CarteraCliente cartera : carteras) {
				for (CarteraCliente totales : carteras2) {
					if (cartera.getIdCartera() == totales.getIdCartera()) {
						// se envian los datos de la cartera
						cartera.setCart_publicada(totales.isCart_publicada());

						cartera.setImportancia(totales.getImportancia());
						cartera.setDificultad(totales.getDificultad());

						cartera.setCart_debemos(totales.getCart_debemos());
						cartera.setCart_deben(totales.getCart_deben());
						cartera.setCart_facturacionAct(totales
								.getCart_facturacionAct());
						cartera.setCart_facturacionAnt(totales
								.getCart_facturacionAnt());
						cartera.setCart_promedioFacturacion(totales
								.getCart_promedioFacturacion());
						cartera.setCart_proyeccionVenta(totales
								.getCart_proyeccionVenta());
						cartera.setCart_objetivoDeseado(totales
								.getCart_objetivoDeseado());
						cartera.setCart_objetivoFundamental(totales
								.getCart_objetivoFundamental());
						cartera.setCart_montoDeseado(totales
								.getCart_montoDeseado());
						cartera.setCart_montoFundamental(totales
								.getCart_montoFundamental());

					}
				}

			}
			return carteras;
		} catch (Exception e) {
			log.info(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			return new ArrayList<CarteraCliente>();
		}
	}

	@Override
	public String obtenFoliosPendientesCartera(String tipo)
			throws ProquifaNetException {
		String query = "";
		try {
			/*
			 * para poder utilizar la funcion es necesario ingresar en la
			 * transaccion la declaracion y el llenado de la vista declare
			 * @idClientesEsac table (Id int) " + insert into @idClientes values " +
			 * clientes, para actualizar carteras esta declarada en publicarCartera
			 * y updatecarteraPublicada
			 */
			if (tipo.equals("ESAC")) {

				/*
				 * -----------------------------------------------------------------
				 * -----------------------esac -------------------------------------
				 * -----------------------------------------------------------------
				 */
				query = " \n 	SELECT Pendiente.Folio "
						+ " \n 	From Pendiente, DoctosR, Clientes   "
						+ " \n 	WHERE Pendiente.Tipo='Pedido a cotizar'  AND FFin IS NULL   "
						+ " \n 	AND DoctosR.Folio=Pendiente.Docto AND DoctosR.Empresa = Clientes.Clave AND Clientes.Clave in (select Id from @idClientes) "
						+ " \n 	UNION "
						+ " \n 	SELECT Pendiente.Folio "
						+ " \n 	FROM pendiente , DoctosR, Clientes  "
						+ " \n 	WHERE FFin IS NULL AND Pendiente.Tipo='Pedido por Tramitar SC'  "
						+ " \n 	AND DoctosR.Folio=Pendiente.Docto AND Clientes.Clave=DoctosR.Empresa AND Clientes.Clave in (select Id from @idClientes) "
						+ " \n 	UNION "
						+ " \n 	SELECT  Pendiente.Folio "
						+ " \n 	FROM pendiente , DoctosR, Clientes   "
						+ " \n 	WHERE FFin IS NULL AND Pendiente.Tipo='Pedido por Tramitar'   "
						+ " \n 	AND DoctosR.Folio=Pendiente.Docto AND Clientes.Clave=DoctosR.Empresa  AND Clientes.Clave in (select Id from @idClientes) "
						+ " \n 	UNION "
						+ " \n 	SELECT Pendiente.Folio "
						+ " \n 	FROM pendiente , DoctosR, Clientes   "
						+ " \n 	WHERE FFin IS NULL AND Pendiente.Tipo='Pedido por Tramitar PSC'  "
						+ " \n 	AND DoctosR.Folio=Pendiente.Docto AND Clientes.Clave=DoctosR.Empresa  AND Clientes.Clave in (select Id from @idClientes) "
						+ " \n 	UNION "
						+ " \n 	SELECT Pendiente.Folio "
						+ " \n 	FROM Pendiente, DoctosR, Clientes, Pedidos  "
						+ " \n 	WHERE Pendiente.Tipo='PSC sin FEE' AND FFin IS NULL AND Clientes.Clave=DoctosR.Empresa "
						+ " \n 	AND Pedidos.CPedido=Pendiente.Docto  AND Pedidos.DoctoR=DoctosR.Folio AND Clientes.Clave in (select Id from @idClientes) "
						+ " \n 	UNION "
						+ " \n 	SELECT Pendiente.Folio "
						+ " \n 	FROM pendiente , DoctosR, Clientes   "
						+ " \n 	WHERE FFin IS NULL AND Pendiente.Tipo='Monitorear inconsistencias'  "
						+ " \n 	AND DoctosR.Folio=Pendiente.Docto AND Clientes.Clave=DoctosR.Empresa AND Clientes.Clave in (select Id from @idClientes) "
						+ " \n 	UNION "																																														
						+ " \n 	SELECT Pendiente.Folio "
						+ " \n 	FROM Clientes, DoctosR, Pedidos, PCompras, Pendiente,Notificado   "
						+ " \n 	WHERE Pendiente.Tipo='Aviso de cambios' AND Pendiente.FFin IS NULL    "
						+ " \n 	AND Pendiente.Docto=PCompras.Compra AND PCompras.Partida=Pendiente.Partida AND PCompras.CPedido=Pedidos.CPedido  "
						+ " \n 	AND Pedidos.DoctoR=DoctosR.Folio AND DoctosR.Empresa=Clientes.Clave AND PCompras.FolioNT=Notificado.idNotificado   "
						+ " \n 	AND Clientes.Clave in (select Id from @idClientes) "
						+ " \n 	UNION "												
						+ " \n 	SELECT Pendiente.Folio "
						+ " \n 	FROM Clientes, Facturas, Pendiente   "
						+ " \n 	WHERE Pendiente.Tipo='Servicio de tráfico programado' AND Pendiente.FFin IS NULL AND Clientes.Clave=Facturas.Cliente  "
						+ " \n 	AND Facturas.Factura=Pendiente.Docto AND Facturas.FPor=Pendiente.Partida AND Clientes.Clave in (select Id from @idClientes) "
						+ " \n 	UNION "
						+ " \n 	SELECT Pendiente.folio  "
						+ " \n 	FROM Clientes, RutaEs, Pendiente   "
						+ " \n 	WHERE Pendiente.Tipo='Servicio de tráfico programado' AND Pendiente.FFin IS NULL AND Clientes.Clave=RutaES.idCliente  "
						+ " \n 	AND RutaES.FolioDoctos=Pendiente.Docto AND Clientes.Clave in (select Id from @idClientes) ";						

			} else if (tipo.equals("COBRADOR")) {

				/*
				 * ------------------------------------------------------------
				 * ----------------------- cobrador
				 * -------------------------------------
				 * ------------------------------------------------------------
				 */

				// ---------------- Facturar por adela ----------------
				query += " \n SELECT PEND.Folio FROM Pendiente AS PEND "
						+ " \n LEFT JOIN(SELECT * FROM Pedidos) AS PED ON PED.CPedido = PEND.Docto "
						+ " \n LEFT JOIN(SELECT * FROM Clientes) AS CLI ON CLI.Clave = PED.idCliente "
						+ " \n WHERE PEND.Tipo='Facturar por adelantado' AND PEND.FFin IS NULL AND CLI.Clave in (select Id from @idClientes) "
						+ " \n UNION "
						+
						// ---------------- Refacturar por adela ----------------
						" \n SELECT PEND.Folio FROM Pendiente AS PEND "
						+ " \n LEFT JOIN Facturas AS FAC ON FAC.FPor = PEND.Partida AND FAC.Factura = PEND.Docto "
						+ " \n LEFT JOIN Clientes AS CLI ON CLI.Clave = FAC.Cliente "
						+ " \n WHERE PEND.Tipo='A RefacturaciónXAdela' AND PEND.FFin IS NULL AND CLI.Clave in (select Id from @idClientes) "
						+ " \n UNION "
						+
						// ---------------- A facturar ----------------
						" \n SELECT pnd.Folio FROM Pendiente AS pnd "
						+ " \n LEFT JOIN(SELECT * FROM Pedidos) AS p ON pnd.Docto = p.CPedido  "
						+ " \n LEFT JOIN(SELECT * FROM DoctosR) AS doc ON p.DoctoR=doc.Folio  "
						+ " \n LEFT JOIN(SELECT * FROM Clientes) AS c ON c.Clave=doc.Empresa  "
						+ " \n LEFT JOIN(SELECT * FROM Empleados) as emp ON emp.Usuario = pnd.Responsable "
						+ " \n WHERE pnd.Tipo='A facturar' AND pnd.FFin IS NULL  AND emp.FK01_Funcion <> 36 "
						+ " \n AND c.Clave in (select Id from @idClientes) "
						+ " \n UNION "
						+
						// ---------------- Nota de Credito ----------------
						" \n SELECT PEND.Folio  "
						+ " \n FROM pendiente AS PEND "
						+ " \n LEFT JOIN NotaCredito AS NOTA ON NOTA.PK_Nota = PEND.Partida "
						+ " \n LEFT JOIN Clientes AS CLI ON CLI.Clave = NOTA.FK01_Cliente "
						+ " \n WHERE PEND.Tipo='Nota de credito' AND PEND.FFin IS NULL "
						+ " \n AND CLI.Clave in (select Id from @idClientes) "
						+ " \n UNION "
						+
						// ---------------- Facturar Remision ----------------
						" \n SELECT PEND.Folio "
						+ " \n FROM Pendiente AS PEND "
						+ " \n LEFT JOIN Remisiones AS REM ON REM.Factura = PEND.Docto AND REM.FPor = PEND.Partida "
						+ " \n LEFT JOIN Clientes AS CLI ON CLI.Clave = REM.Cliente "
						+ " \n WHERE PEND.Tipo='Facturar remisión' AND PEND.FFin IS NULL "
						+ " \n AND CLI.Clave in (select Id from @idClientes) "
						+ " \n UNION "
						+
						// ---------------- A Refacturacion ----------------
						" \n SELECT PEND.Folio "
						+ " \n FROM Pendiente AS PEND "
						+ " \n LEFT JOIN Facturas AS FAC ON FAC.Factura = PEND.Docto AND PEND.Partida = FAC.FPor "
						+ " \n LEFT JOIN Clientes AS CLI ON CLI.Clave = FAC.Cliente "
						+ " \n WHERE PEND.Tipo='A Refacturación' AND PEND.FFin IS NULL "
						+ " \n AND CLI.Clave in (select Id from @idClientes) "
						+

						" \n UNION "
						+
						// ---------------- A Facturar Pedido Internacional
						// ----------------
						" \n SELECT PEND.Folio "
						+ " \n FROM Pendiente AS PEND "
						+ " \n LEFT JOIN Pedidos AS PED ON PED.CPedido = PEND.Docto  "
						+ " \n LEFT JOIN DoctosR AS DOC ON DOC.Folio = PED.DoctoR "
						+ " \n LEFT JOIN Clientes AS CLI ON CLI.Clave = DOC.Empresa "
						+ " \n WHERE PEND.Tipo='A facturar phs' AND PEND.FFin IS NULL  "
						+ " \n AND CLI.Clave in (select Id from @idClientes) "
						+ " \n UNION "
						+
						// ---------------- Subir Factura al Portal ----------------
						" \n SELECT p.Folio FROM Pendiente P LEFT JOIN Facturas F ON F.idFactura = P.Docto  "
						+ " \n LEFT JOIN Clientes C ON C.Clave = F.Cliente LEFT JOIN Empleados emp  ON emp.Clave = c.cobrador   "
						+ " \n WHERE P.Tipo = 'SubirFacturaPortal' AND P.FFin is null AND C.Clave in (select Id from @idClientes) "
						+ " \n UNION "
						+
						// ---------------- Amparar Cancelacion ----------------
						" \n SELECT PEND.Folio  "
						+ " \n FROM Pendiente AS PEND "
						+ " \n LEFT JOIN Facturas AS FAC ON FAC.Factura = PEND.Docto AND PEND.Partida = FAC.FPor "
						+ " \n LEFT JOIN Clientes AS CLI ON CLI.Clave = FAC.Cliente "
						+ " \n WHERE PEND.Tipo='Amparar Cancelación'  AND PEND.FFin IS NULL  "
						+ " \n AND CLI.Clave in (select Id from @idClientes) "
						+ " \n UNION "
						+
						// ---------------- Amparar Cancelacion NC ----------------
						" \n SELECT PEND.Folio  "
						+ " \n FROM Pendiente AS PEND "
						+ " \n LEFT JOIN NotaCredito AS NOTA ON NOTA.PK_Nota = PEND.Docto "
						+ " \n LEFT JOIN Clientes AS CLI ON CLI.Clave = NOTA.FK01_Cliente "
						+ " \n WHERE PEND.Tipo='Amparar cancelacion NC' AND PEND.FFin IS NULL  "
						+ " \n AND CLI.Clave in (select Id from @idClientes) "
						+ " \n UNION "
						+
						// ---------------- Programar revision ----------------
						" \n SELECT PEND.Folio  "
						+ " \n FROM Pendiente AS PEND "
						+ " \n LEFT JOIN Facturas AS FAC ON FAC.FPor = PEND.Partida AND FAC.Factura = PEND.Docto "
						+ " \n LEFT JOIN Clientes AS CLI ON CLI.Clave = FAC.Cliente  "
						+ " \n LEFT JOIN Empleados AS EMP ON EMP.Clave = CLI.Cobrador "
						+ " \n WHERE PEND.FFin IS NULL AND PEND.Tipo='Programar revisión' and fac.DeSistema =  1    "
						+ " \n AND CLI.Clave in (select Id from @idClientes) "
						+ " \n UNION "
						+
						// ---------------- Monitorear cobros y pagos
						// ----------------
						" \n SELECT PEND.Folio  "
						+ " \n FROM Pendiente AS PEND "
						+ " \n LEFT JOIN Facturas AS FAC ON FAC.Factura = PEND.Docto AND FAC.FPor = PEND.Partida "
						+ " \n LEFT JOIN Clientes AS CLI ON CLI.Clave = FAC.Cliente "
						+ " \n WHERE PEND.Tipo='Monitorear cobro' AND PEND.FFin IS NULL and fac.DeSistema =  1    "
						+ " \n AND CLI.Clave in (select Id from @idClientes) "
						+ " \n UNION "
						+ " \n SElECT PEND.Folio  "
						+ " \n FROM Pendiente AS PEND "
						+ " \n LEFT JOIN DoctosR AS DOC ON DOC.Folio = PEND.Docto "
						+ " \n LEFT JOIN Clientes AS CLI ON CLI.Clave = DOC.Empresa "
						+ " \n WHERE PEND.Tipo='Pago por asociar' AND PEND.FFin IS NULL  "
						+ " \n AND CLI.Clave in (select Id from @idClientes) "
						+ " \n UNION "
						+
						// ---------------- 'Validar cobro SC ----------------
						" \n SELECT PEND.Folio  "
						+ " \n FROM Pendiente AS PEND "
						+ " \n LEFT JOIN Pedidos AS PED ON PED.CPedido = PEND.Docto "
						+ " \n LEFT JOIN Clientes AS CLI ON CLI.Clave = PED.idCliente "
						+ " \n WHERE PEND.FFin IS NULL  AND PEND.Tipo='Cobro a validar'  "
						+ " \n AND CLI.Clave in (select Id from @idClientes) "
						+ " \n UNION "
						+
						// ---------------- 'Programar cobro ----------------
						" \n SELECT PEND.Folio "
						+ " \n FROM Pendiente AS PEND "
						+ " \n LEFT JOIN Facturas AS FAC ON FAC.Factura = PEND.Docto AND FAC.FPor = PEND.Partida "
						+ " \n LEFT JOIN Clientes AS CLI ON CLI.Clave = FAC.Cliente "
						+ " \n WHERE PEND.FFin is null and PEND.Tipo = 'programar cobro' and fac.DeSistema =  1    "
						+ " \n AND CLI.Clave in (select Id from @idClientes) ";
			} else if (tipo.equals("EVT")) { //Vendedor Telemarketing
				query = "\n SELECT Pendiente.Folio ";
				query +="\n FROM Pendiente,Cotizas,Bitacora ";
				query +="\n WHERE Pendiente.Tipo = 'Seguimiento' AND Pendiente.FFin IS NULL ";
				query +="\n AND Pendiente.Docto = Cotizas.Clave AND Cotizas.Estado <> 'Cancelada' AND Bitacora.Cotiza=Cotizas.Clave ";
				query +="\n AND Cotizas.FK01_idCliente in (select Id from @idClientes) ";
				query +="\n UNION ";
				query +="\n SELECT Pendiente.Folio ";
				query +="\n FROM Pendiente, DoctosR,Clientes ";
				query +="\n WHERE Pendiente.tipo='PSC c/problemas'  AND DoctosR.Folio=Pendiente.Docto ";
				query +="\n AND Clientes.Clave=DoctosR.Empresa AND FFin IS NULL  AND Clientes.Clave in (select Id from @idClientes) ";
				query +="\n UNION ";
				query +="\n SELECT Pendiente.Folio ";
				query +="\n FROM Pendiente, DoctosR,Clientes  ";
				query +="\n WHERE Pendiente.tipo='PCC c/problemas'  AND DoctosR.Folio=Pendiente.Docto  ";
				query +="\n AND Clientes.Clave=DoctosR.Empresa AND FFin IS NULL  AND Clientes.Clave in (select Id from @idClientes) ";
				query +="\n	UNION ";
				query +="\n	SELECT Pendiente.Folio ";
				query +="\n	FROM Pendiente, DoctosR,Clientes ";
				query +="\n	WHERE Pendiente.tipo='Pago pendiente'  AND DoctosR.Folio=Pendiente.Docto ";
				query +="\n	AND Clientes.Clave=DoctosR.Empresa AND FFin IS NULL AND Clientes.Clave in (select Id from @idClientes) ";
				query +="\n	UNION ";
				query +="\n SELECT Pendiente.Folio ";
				query +="\n FROM Pendiente,Facturas,Clientes ";
				query +="\n WHERE Pendiente.Tipo='Factura por enviar'  AND Facturas.Factura=Docto ";
				query +="\n AND CPedido = Partida AND Clave=Cliente AND FFin IS NULL AND Clientes.Clave in (select Id from @idClientes) ";
				query +="\n UNION ";	
				query +="\n SELECT Pendiente.Folio ";
				query +="\n FROM PENDIENTE, Pedidos ";
				query +="\n WHERE cpedido = docto and pendiente.TIPO = 'Alistar criterios de envio' ";
				query +="\n AND FFIN IS NULL AND Pedidos.idCliente in (select Id from @idClientes) ";
				query +="\n UNION ";
				query +="\n SELECT Pendiente.Folio ";
				query +="\n FROM Pendiente,Facturas,Clientes ";
				query +="\n WHERE Pendiente.Tipo='Confirmar datos de factura' ";
				query +="\n AND Pendiente.Docto=Facturas.Factura AND Partida=Facturas.FPor ";
				query +="\n AND Clientes.Clave=Facturas.Cliente AND Pendiente.FFin IS NULL AND Clientes.Clave in (select Id from @idClientes) ";
				query +="\n UNION ";
				query +="\n SELECT Pendiente.Folio ";
				query +="\n FROM Pendiente,DoctosR,Clientes ";
				query +="\n WHERE Pendiente.Tipo='Refacturación' AND Pendiente.Docto=DoctosR.Folio ";
				query +="\n AND Clientes.Clave=DoctosR.Empresa AND Pendiente.FFin IS NULL AND Clientes.Clave in (select Id from @idClientes) ";
				query +="\n UNION ";
				query +="\n SELECT Pendiente.Folio ";
				query +="\n FROM Pendiente,DoctosR,Clientes ";
				query +="\n WHERE Pendiente.Tipo='Modificaciones de pedido' AND Pendiente.Docto=DoctosR.Folio ";
				query +="\n AND Clientes.Clave=DoctosR.Empresa AND Pendiente.FFin IS NULL AND Clientes.Clave in (select Id from @idClientes) ";
				query +="\n UNION ";
				query +="\n SELECT Pendiente.Folio ";
				query +="\n FROM Clientes, Pendiente, Facturas ";
				query +="\n WHERE Pendiente.Tipo='Datos de pago pendiente' AND Pendiente.FFin IS NULL AND Facturas.Factura=Pendiente.Docto ";
				query +="\n AND Facturas.FPor=Pendiente.Partida AND Clientes.Clave=Facturas.Cliente  AND Clientes.Clave in (select Id from @idClientes) ";
				query +="\n UNION ";
				query +="\n SELECT Pendiente.Folio ";
				query +="\n FROM Clientes, DoctosR, Pendiente ";
				query +="\n WHERE Pendiente.Tipo='OTROS a trabajar' AND Pendiente.FFin IS NULL ";
				query +="\n AND DoctosR.Empresa=Clientes.Clave AND Pendiente.Docto=DoctosR.Folio AND Clientes.Clave in (select Id from @idClientes) ";
				query +="\n UNION ";
				query +="\n SELECT Pendiente.folio ";
				query +="\n FROM Clientes, RutaRE, Pendiente ";
				query +="\n WHERE Pendiente.Tipo='Servicio de tráfico programado' AND Pendiente.FFin IS NULL AND Clientes.Clave in (select Id from @idClientes) ";
				query +="\n AND Clientes.Clave=RutaRE.idCliente AND RutaRE.FolioDoctos=Pendiente.Docto ";
			}
			return query;
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"tipo: " + tipo);
			return "";
		}

	}

	@Override
	public List<CarteraCliente> obtenerCarterasporColaborador(Long idfuncion,
			Long idResponsable , Long idCartera) throws ProquifaNetException {
		try {
			List<Empleado> subordinados = null;
			String parametros = " and cart.publicada = 1 ", subordinadosEsac = "", subordinadosIdEsac = "";
			if (idfuncion == 8) { // esac
				parametros += "  and (cart.FK02_Esac = "
						+ idResponsable
						+ " or (cart.FK04_Usuario is null and cli.Vendedor = ( select top 1 usuario from empleados where clave = "
						+ idResponsable + " ))) ";
			} else if (idfuncion == 41) { // esac_Master
				
				parametros = "";
				
				
				
//				subordinados = empleadoDAO
//						.getsubordinadosEsacMaster(idResponsable);
//				for (Empleado empleado : subordinados) {
//					if (subordinadosEsac.equals("")) {
//						subordinadosEsac = " '" + empleado.getUsuario() + "' ";
//						subordinadosIdEsac = " "
//								+ empleado.getClave().toString() + " ";
//					} else {
//						subordinadosEsac += ", '" + empleado.getUsuario()
//								+ "' ";
//						subordinadosIdEsac += ", "
//								+ empleado.getClave().toString() + " ";
//					}
//				}
//				parametros += "  and (cart.FK02_Esac in  ("
//						+ subordinadosIdEsac
//						+ ") )";
						//or (cart.FK04_Usuario is null and cli.Vendedor in ( "
						//+ subordinadosEsac + " ))) ";
			} else if (idfuncion == 3) {// ev
				parametros += "  and (cart.FK01_EV = " + idResponsable
						+ " or (cart.FK04_Usuario is null and  cli.FK01_EV = "
						+ idResponsable + " )) ";
			} else if (idfuncion == 45) {// cobrador
				parametros += "  and (cart.FK03_Cobrador = " + idResponsable
						+ " or (cart.FK04_Usuario is null and cli.Cobrador = "
						+ idResponsable + " )) ";
			} else {
				parametros = "";
			}

			List<CarteraCliente> carteras = null;
			List<CarteraCliente> carterasF = null;
//			List<CarteraCliente> carteras2 = null;
			List<NivelIngreso> niveles = nivelIngresoDAO.findLimitesNivelIngreso();
			carteras = this.catalogoClienteDAO.queryCarteraCliente(parametros, niveles, idCartera);
			
//			carteras2 = this.catalogoClienteDAO.findMontosGeneralesCarteras(
//					parametros, niveles);
//			for (CarteraCliente cartera : carteras) {
//				for (CarteraCliente totales : carteras2) {
//					if (cartera.getIdCartera() == totales.getIdCartera()) {
//						// se envian los datos de la cartera
//						cartera.setCart_publicada(totales.isCart_publicada());
//
//						cartera.setImportancia(totales.getImportancia());
//						cartera.setDificultad(totales.getDificultad());
//
//						cartera.setCart_debemos(totales.getCart_debemos());
//						cartera.setCart_deben(totales.getCart_deben());
//						cartera.setCart_facturacionAct(totales
//								.getCart_facturacionAct());
//						cartera.setCart_facturacionAnt(totales
//								.getCart_facturacionAnt());
//						cartera.setCart_promedioFacturacion(totales
//								.getCart_promedioFacturacion());
//						cartera.setCart_proyeccionVenta(totales
//								.getCart_proyeccionVenta());
//						cartera.setCart_objetivoDeseado(totales
//								.getCart_objetivoDeseado());
//						cartera.setCart_objetivoFundamental(totales
//								.getCart_objetivoFundamental());
//						cartera.setCart_montoDeseado(totales
//								.getCart_montoDeseado());
//						cartera.setCart_montoFundamental(totales
//								.getCart_montoFundamental());
//
//					}
//				}
//
//			}
			
			  carterasF = calcularCostosDeCartera(carteras,parametros,niveles);
			return carterasF;
		} catch (Exception e) {
			log.info(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"idfuncion: " + idfuncion, "idResponsable: " + idResponsable);
			return new ArrayList<CarteraCliente>();
		}
	}
	

 @Override
	public List<ValorCombo> obtenerComboEsacNombreCartera()
			throws ProquifaNetException {
		try {
			return catalogoClienteDAO.obtenerComboEsacNombreCartera();
		} catch (Exception e) {
			log.info(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			return new ArrayList<ValorCombo>();
		}
	}

	@Override
	public boolean asignarClienteACartera(Cartera cartera, long idCliente, long idUsuario) throws ProquifaNetException {
		try {

			clienteDAO.asignarClienteACartera(idCliente, cartera.getIdcartera());
			clienteDAO.updateResponsablesClienteCartera(idCliente,cartera.getEsac(),cartera.getEv(),cartera.getCobrador());

			String actualizaPendientesESAC = this.obtenFoliosPendientesCartera("ESAC");
			String actualizaPendientesCobrador = this.obtenFoliosPendientesCartera("COBRADOR");

			clienteDAO.actualizarPendientesAsignarClienteACartera(actualizaPendientesESAC,idCliente, cartera.getEsac());
			clienteDAO.actualizarPendientesAsignarClienteACartera(actualizaPendientesCobrador,idCliente, cartera.getCobrador());

			return true;

		} catch (Exception e) {
			log.info(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
	}
	
	@Override
	public List<CarteraCliente> obtenerCarterasyClientes(
			Long idfuncion,Long idResponsable)
					throws ProquifaNetException {
		try {
			List<Empleado> subordinados = null;
			String parametros = " and cart.publicada = 1 ", subordinadosEsac = "", subordinadosIdEsac = "";
			if (idfuncion == 8) { // esac
				parametros += "  and (cart.FK02_Esac = "
						+ idResponsable
						+ " or (cart.FK04_Usuario is null and cli.Vendedor = ( select top 1 usuario from empleados where clave = "
						+ idResponsable + " ))) ";
			} else if (idfuncion == 41) { // esac_Master
				
				parametros = "";
//				subordinados = empleadoDAO
//						.getsubordinadosEsacMaster(idResponsable);
//				for (Empleado empleado : subordinados) {
//					if (subordinadosEsac.equals("")) {
//						subordinadosEsac = " '" + empleado.getUsuario() + "' ";
//						subordinadosIdEsac = " "
//								+ empleado.getClave().toString() + " ";
//					} else {
//						subordinadosEsac += ", '" + empleado.getUsuario()
//								+ "' ";
//						subordinadosIdEsac += ", "
//								+ empleado.getClave().toString() + " ";
//					}
//				}
//				parametros += "  and (cart.FK02_Esac in  ("
//						+ subordinadosIdEsac
//						+ ") )";
						//or (cart.FK04_Usuario is null and cli.Vendedor in ( "
						//+ subordinadosEsac + " ))) ";
			} else if (idfuncion == 3) {// ev
				parametros += "  and (cart.FK01_EV = " + idResponsable
						+ " or (cart.FK04_Usuario is null and  cli.FK01_EV = "
						+ idResponsable + " )) ";
			} else if (idfuncion == 45) {// cobrador
				parametros += "  and (cart.FK03_Cobrador = " + idResponsable
						+ " or (cart.FK04_Usuario is null and cli.Cobrador = "
						+ idResponsable + " )) ";
			} else {
				parametros = "";
			}

			List<CarteraCliente> carteras = null;
			List<CarteraCliente> carterasFinal = null;
		
			List<NivelIngreso> niveles = nivelIngresoDAO.findLimitesNivelIngreso();
	        carteras = this.catalogoClienteDAO.obtenerCarterasyClientes(parametros, niveles);
	        carterasFinal = calcularCostosDeCartera(carteras,parametros,niveles);
	      
	        return carterasFinal;
		} catch (Exception e) {
			log.info(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"idfuncion: " + idfuncion, "idResponsable: " + idResponsable);
			return new ArrayList<CarteraCliente>();
		}
	}
	
	
	@Override
	public List<CarteraCliente> calcularCostosDeCartera( 
			 List<CarteraCliente> carteras, String parametros, List<NivelIngreso> niveles ) 
					 throws ProquifaNetException {
		
		List<CarteraCliente> carteras2 = null;
		carteras2 = this.catalogoClienteDAO.findMontosGeneralesCarteras(parametros, niveles);
		for (CarteraCliente cartera : carteras) {
			for (CarteraCliente totales : carteras2) {
				if (cartera.getIdCartera() == totales.getIdCartera()) {
					// se envian los datos de la cartera
					cartera.setCart_publicada(totales.isCart_publicada());

					cartera.setImportancia(totales.getImportancia());
					cartera.setDificultad(totales.getDificultad());

					cartera.setCart_debemos(totales.getCart_debemos());
					cartera.setCart_deben(totales.getCart_deben());
					cartera.setCart_facturacionAct(totales
							.getCart_facturacionAct());
					cartera.setCart_facturacionAnt(totales
							.getCart_facturacionAnt());
					cartera.setCart_promedioFacturacion(totales
							.getCart_promedioFacturacion());
					cartera.setCart_proyeccionVenta(totales
							.getCart_proyeccionVenta());
					cartera.setCart_objetivoDeseado(totales
							.getCart_objetivoDeseado());
					cartera.setCart_objetivoFundamental(totales
							.getCart_objetivoFundamental());
					cartera.setCart_montoDeseado(totales
							.getCart_montoDeseado());
					cartera.setCart_montoFundamental(totales
							.getCart_montoFundamental());

				}
			}

		}
		return carteras;
		}

	
	
	@Override
	public 	List<Direccion> obtenerdDireccionesXidCliente(Long idCliente) throws ProquifaNetException {
		try{
			List<Direccion> direccionesC = new ArrayList<Direccion>();
			
			direccionesC = direccionesDAO.getDireccionCliente(idCliente);
			for (Direccion direccion : direccionesC) {
				List<Horario> horario = catalogoClienteDAO.obtenerHorarioCliente(direccion.getIdDireccion());
				direccion.setHorarios(horario);
			}
			
			
			
			return direccionesC;
	

		}catch (Exception e) {
			log.info(e.getMessage());
			funcion.enviarCorreoAvisoExepcion(e, "idProveedor: " + idCliente);
			return new ArrayList<Direccion>();
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
	
	

	
@Override
public Boolean insertarDireciconCliente(Direccion d)
		throws ProquifaNetException {
	try {
		boolean correcto = false;
		     Long idDireccion = -1L;
			idDireccion = catalogoClienteDAO.agregarDireccionCliente(d);
			correcto = true;
	return correcto;
	} catch (Exception e) {
		funcion = new Funcion();
		funcion.enviarCorreoAvisoExepcion(e,d);
		return false;
	}

}


@Override
public 	List<Direccion> obtenerDireccionesDeVisitaXidCliente(Long idCliente) throws ProquifaNetException {
	try{
		List<Direccion> direccionesC = new ArrayList<Direccion>();
		
		direccionesC = direccionesDAO.getDireccionesTipoVisitaPorIdCliente(idCliente);
		return direccionesC;


	}catch (Exception e) {
		log.info(e.getMessage());
		funcion.enviarCorreoAvisoExepcion(e, "idProveedor: " + idCliente);
		return new ArrayList<Direccion>();
	}
}

	
	
	public String prueba(){
		log.info("pasaa pruebaa");
		return "pasaa pruebaa";
	}
	
	
	@Override
	@Transactional()
	public boolean actualizarClienteCartera(Long idCliente, Long idCartera, Long idCarteraAnt) throws ProquifaNetException {
		try {

			String condicion = "";
			
			clienteDAO.eliminarClienteDeCartera(idCliente, idCarteraAnt);
			
			clienteDAO.asignarClienteACartera(idCliente, idCartera);
			
			Cartera cartera = clienteDAO.obtenerInformacionDeCarteraPorId(idCartera);
			
			if(cartera.getArea() != null)
			{
				if(cartera.getArea().equalsIgnoreCase("Ventas"))
				{
					condicion = " FK01_EV =" + cartera.getEv() + " where Clave = " + idCliente; 
				}
					
				else if(cartera.getArea().equalsIgnoreCase("ESAC")){
					
					condicion = " Vendedor = '" + cartera.getNombreEsac() + "' where Clave = " + idCliente; 
				}
						
				else if(cartera.getArea().equalsIgnoreCase("Finanzas"))
				{
                  
					condicion = " Cobrador =" + cartera.getCobrador() + " where Clave = " + idCliente; 
					
				}
				
				
				clienteDAO.actualizarResponsableCartera(condicion);
			}
			else
			{
				clienteDAO.updateResponsablesClienteCartera(idCliente, cartera.getEsac(), cartera.getEv(), cartera.getCobrador());
				
			}
			
			return true;

		} catch (Exception e) {
			log.info(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
	}
	
	
	@Override
	@Transactional()
	public boolean validarContrasena(Empleado empleado, String cliente, String razones, String tipo, String solicitante) throws ProquifaNetException {
		try{
			
			boolean respuesta;
			
			respuesta =  empleadoDAO.validarPassUser(empleado);
			if(respuesta){
				clienteDAO.insertarAutorizacion(empleado.getUsuario(), solicitante, tipo, razones, cliente);
			}
			
		  return true;


		}catch (Exception e) {
			log.info(e.getMessage());
//			funcion.enviarCorreoAvisoExepcion(e, "idProveedor: " + idCliente);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
	}
	
	
	
	
	@Override
	@Transactional()
	public boolean guardarActualizarEntregaCliente(EntregaCatClientes entregaCliente) throws ProquifaNetException {
		try{
			
			boolean respuesta;
			
			if(entregaCliente.getIdEntregaCliente() == 0){
				respuesta = clienteDAO.insertaEntregaCliente(entregaCliente);
			}
			else{
				
				respuesta = clienteDAO.actualizarDatosEntregaCliente(entregaCliente);
				
			}
	       
	       respuesta = clienteDAO.actualizarEntregayRevicionyParcialesCliente(entregaCliente.isEntregayRevicion(), entregaCliente.isAceptaParcial(), entregaCliente.getDestino(), entregaCliente.getIdCliente());
	       
//	       respuesta = clienteDAO.
//	       
	       
		  return respuesta;
		}catch (Exception e) {
			log.info(e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
	}
	
	

	public EntregaCatClientes consultarDatosEntregaPoridCliente (Long idCliente) throws ProquifaNetException {
		try{
			Cliente cli = new Cliente();
			Direccion dir = new Direccion();
			List<NivelIngreso> ni = nivelIngresoDAO.findLimitesNivelIngreso();
			cli =  catalogoClienteDAO.obtenerClienteXId(idCliente, ni);
			EntregaCatClientes datosEntrega = new EntregaCatClientes();
			datosEntrega = clienteDAO.obtenerInformacionDeEntregaCliente(idCliente);
			datosEntrega.setAceptaParcial(cli.getAceptaParciales());
			datosEntrega.setEntregayRevicion(cli.getEntregaYRevision());
			datosEntrega.setDestino(cli.getDestino());
			dir = direccionesDAO.obtenerDireccionPorTipoyidCliente(idCliente);
		    datosEntrega.setDireccionEntrega(dir);
		    
		    List<Horario> horario = new  ArrayList<Horario>();
		    if(datosEntrega.getDireccionEntrega().getHorarioEntrega() != null)
		    {
		        horario.add(datosEntrega.getDireccionEntrega().getHorarioEntrega());
				for (Horario h : horario) {
					Point tmp = new Point();
					Point tmp2 = new Point();

					ArrayList<Integer> lunes = new ArrayList<Integer>();
					ArrayList<Integer> martes = new ArrayList<Integer>();
					ArrayList<Integer> miercoles = new ArrayList<Integer>();
					ArrayList<Integer> jueves = new ArrayList<Integer>();
					ArrayList<Integer> viernes = new ArrayList<Integer>();

					if (h.getLunes()) {
						if (h.getLuDe1() != null & h.getLuA1() != null) {
							tmp = obtenerCoordenadaHorario(h.getLuDe1(), h.getLuA1());
						}
						if (h.getLuDe2() != null & h.getLuA2() != null) {
							tmp2 = obtenerCoordenadaHorario(h.getLuDe2(), h.getLuA2());
						}
						for (int i = (int) tmp.getX(); i <= (int) tmp.getY(); i++) { // Primer
							// periodo
							lunes.add(i);
						}
						for (int i = (int) tmp2.getX(); i <= (int) tmp2.getY(); i++) { // Segundo
							// periodo
							lunes.add(i);
						}
						h.setPlunes(lunes);
					}
					if (h.getMartes()) {
						if (h.getMaDe1() != null & h.getMaA1() != null) {
							tmp = obtenerCoordenadaHorario(h.getMaDe1(), h.getMaA1());
						}
						if (h.getMaDe2() != null & h.getMaA2() != null) {
							tmp2 = obtenerCoordenadaHorario(h.getMaDe2(), h.getMaA2());
						}
						for (int i = (int) tmp.getX(); i <= (int) tmp.getY(); i++) { // Primer
							// periodo
							martes.add(i);
						}
						for (int i = (int) tmp2.getX(); i <= (int) tmp2.getY(); i++) { // Segundo
							// periodo
							martes.add(i);
						}
						h.setPmartes(martes);

					}
					if (h.getMiercoles()) {
						if (h.getMiDe1() != null & h.getMiA1() != null) {
							tmp = obtenerCoordenadaHorario(h.getMiDe1(), h.getMiA1());
						}
						if (h.getMiDe2() != null & h.getMiA2() != null) {
							tmp2 = obtenerCoordenadaHorario(h.getMiDe2(), h.getMiA2());
						}
						for (int i = (int) tmp.getX(); i <= (int) tmp.getY(); i++) { // Primer
							// periodo
							miercoles.add(i);
						}
						for (int i = (int) tmp2.getX(); i <= (int) tmp2.getY(); i++) { // Segundo
							// periodo
							miercoles.add(i);
						}
						h.setPmiercoles(miercoles);

					}
					if (h.getJueves()) {
						if (h.getJuDe1() != null & h.getJuA1() != null) {
							tmp = obtenerCoordenadaHorario(h.getJuDe1(), h.getJuA1());
						}
						if (h.getJuDe2() != null & h.getJuA2() != null) {
							tmp2 = obtenerCoordenadaHorario(h.getJuDe2(), h.getJuA2());
						}
						for (int i = (int) tmp.getX(); i <= (int) tmp.getY(); i++) { // Primer
							// periodo
							jueves.add(i);
						}
						for (int i = (int) tmp2.getX(); i <= (int) tmp2.getY(); i++) { // Segundo
							// periodo
							jueves.add(i);
						}
						h.setPjueves(jueves);
					}
					if (h.getViernes()) {
						if (h.getViDe1() != null & h.getViA1() != null) {
							tmp = obtenerCoordenadaHorario(h.getViDe1(), h.getViA1());
						}
						if (h.getViDe2() != null & h.getViA2() != null) {
							tmp2 = obtenerCoordenadaHorario(h.getViDe2(), h.getViA2());
						}
						for (int i = (int) tmp.getX(); i <= (int) tmp.getY(); i++) { // Primer
							// periodo
							viernes.add(i);
						}
						for (int i = (int) tmp2.getX(); i <= (int) tmp2.getY(); i++) { // Segundo
							// periodo
							viernes.add(i);
						}
						h.setPviernes(viernes);

					}
				}
		    	
		    }
		    
		    datosEntrega.getDireccionEntrega().setHorarios(horario);
		
			
			return datosEntrega;
		}catch (Exception e) {
			log.info(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return null;
		}
	}
	
	
	
	@Override
	@Transactional
	public Contrato registroActualizacionDeContrato(Contrato contrato) throws ProquifaNetException {
		try {
			
			boolean resConf;
			Long auxIdConf = 0L;
			
			if(contrato.getFolio() == null || contrato.getFolio().equals(""))
			{
				Folio folio = folioDAO.obtenerFolioPorConcepto("ContratoCliente", true);
				String folioOC = folio.getFolioCompleto();
				contrato.setFolio(folioOC);
			}
			
			boolean eliminarCofiguraciones = true;
			
			   log.info("folio:" + contrato.getFolio());
			
			if(contrato.getIdContrato() == 0)
			{
				log.info("inserta");
				
				contrato.setIdContrato(clienteDAO.insertaContrato(contrato));
				  log.info("idContrato:" + contrato.getIdContrato());
				
			}
			else if(contrato.getIdContrato() > 0)
			{
				log.info("actualiza");
				log.info("idContrato:" + contrato.getIdContrato());
				clienteDAO.actualizarContratoCliente(contrato);
				
			}
			

			log.info("elimina marcas");	
			clienteDAO.eliminarMarcas(contrato);
			
			for (Proveedor pro : contrato.getMarcas()) {
					clienteDAO.insertaMarcasContrato(pro, contrato.getIdContrato());
				}
			
			
			
			
			if(contrato.getConfiguracionesContrato() != null && contrato.getConfiguracionesContrato().size() > 0)
			{
				for (ParametrosOfertaCliente confC : contrato.getConfiguracionesContrato()) {
					
					if(eliminarCofiguraciones)
					clienteDAO.eliminarConfiguracionesContrato(contrato, confC);
					eliminarCofiguraciones = false;
					
					resConf = false;
					log.info("nivel de configuracion:" + confC.getNivelConfigPrecio());
					resConf = actualizarFactorCliente(confC);
					log.info("respuesta actualiza factor:" + resConf);
					if(resConf)
					{
						log.info("inserta configuracio de contrato");
						log.info("actualiza factor cliente:" + confC.getIdProveedor());
						auxIdConf = this.idClienteConfiguracionPrecio;
						log.info("idConfiguracionFamilia" + confC.getIdConfigFamilia());
						
					    clienteDAO.insertarConfContrato(contrato.getIdContrato(), confC, auxIdConf);
						
					}
					
				}
			}
	
			
			return contrato;

		} catch (Exception e) {
			e.printStackTrace();
			funcion = new Funcion();
//			funcion.enviarCorreoAvisoExepcion(e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return null;
		}
	}
	
	
	@Override
	public 	List<Contrato> obtenerContratosXidCliente(Long idCliente) throws ProquifaNetException {
		try{
			
			List<Contrato> contratos = new ArrayList<Contrato>();

			contratos = clienteDAO.obtenerContratosPorIdCliente(idCliente);
			for (Contrato contratoA : contratos) {
				List<Proveedor> marcas = clienteDAO.getMarcasContratoCliente(contratoA.getIdContrato());
				contratoA.setMarcas(marcas);
				contratoA.setListaDeIdsNivel(clienteDAO.obtenerConfiguracionesDeContrato(contratoA.getIdContrato()));

			}

			return contratos;


		}catch (Exception e) {
			log.info(e.getMessage());
			funcion.enviarCorreoAvisoExepcion(e, "idProveedor: " + idCliente);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new ArrayList<Contrato>();
		}
	}
	
	
	
	
	@Override
	@Transactional
	public boolean eliminarContratoClienteXidCliente(Contrato contrato) throws ProquifaNetException {
		try {
			clienteDAO.eliminarContratoCliente(contrato);
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
	}
	
	
	
	
	@Override
	public Long generarDocumentoContrato(Contrato contrato,String rutaCliente, String nivelIngreso) throws ProquifaNetException {
		try {

			List<ConfiguracionContrato>  marcasContrato = new ArrayList<ConfiguracionContrato>();
			List<ConfiguracionContrato>  marcasContratoAux = new ArrayList<ConfiguracionContrato>();
			List<ConfiguracionPrecioProducto>  listaAuxProducto = new ArrayList<ConfiguracionPrecioProducto>();
			List<Producto> listaProductos = new ArrayList<Producto>();
			Long idFamilia;
			
			
			//Map<idFamilia, Map<idProducto, ConfiguracionPrecioProducto>>
			Map<Long, Map<Long, ConfiguracionPrecioProducto>> map = new HashMap<Long, Map<Long,ConfiguracionPrecioProducto>>();

			log.info("idContrato:" + contrato.getIdContrato());
			marcasContrato = clienteDAO.obtenerMarcasContrato(contrato.getIdContrato());

			for (ConfiguracionContrato cc : marcasContrato) {

				listaAuxProducto = new ArrayList<ConfiguracionPrecioProducto>();

				List<ConfiguracionPrecioProducto> lisIdConfPro = clienteDAO.obtenerIdConfProducto(cc.getIdMarca(), contrato.getIdContrato());

				ConfiguracionContrato ccAux = new ConfiguracionContrato();
				ccAux.setMarca(cc.getMarca());
				if(lisIdConfPro.size() > 0)
				{
					idFamilia = 0L;
					map = new HashMap<Long, Map<Long,ConfiguracionPrecioProducto>>();
					for (ConfiguracionPrecioProducto cp : lisIdConfPro) {

						listaProductos = new ArrayList<Producto>();


						log.info("idConfiguracion:" + cp.getIdConfiguracion());
						log.info("nivelIngreso:" + nivelIngreso);
						log.info("idCliente:" +  contrato.getIdCliente());
						log.info("idCliente:" + cp.getIdConfiguracion()); 
						
						Long idProducto = 0L;

						listaAuxProducto = catalogoClienteDAO.findConfiguracionPrecioProductoClientePorConfiguracion(cc.getIdMarca(), contrato.getIdCliente(), nivelIngreso, 0L, idProducto.toString(), cp.getIdConfiguracion(), cp.getNivel());



						for (ConfiguracionPrecioProducto cp2 : listaAuxProducto) {
							if (map.get(cp2.getIdConfiguracionFamilia()) != null) {
								if (map.get(cp2.getIdConfiguracionFamilia()).get(cp2.getProducto().getIdProducto()) != null) {

									ConfiguracionPrecioProducto conf = map.get(cp2.getIdConfiguracionFamilia()).get(cp2.getProducto().getIdProducto());

									if (!conf.getNivel().equals("Producto")) {
										if (cp2.getNivel().equals("Producto") ) {
											map.get(cp2.getIdConfiguracionFamilia()).put(cp2.getProducto().getIdProducto(), cp2);
										} else if (cp2.getNivel().equals("Clasificacion")) {
											map.get(cp2.getIdConfiguracionFamilia()).put(cp2.getProducto().getIdProducto(), cp2);
										} else if (cp2.getNivel().equals("Costo") && !conf.getNivel().equals("Clasificacion")) {
											map.get(cp2.getIdConfiguracionFamilia()).put(cp2.getProducto().getIdProducto(), cp2);
										} else if (cp2.getNivel().equals("Familia") && !conf.getNivel().equals("Costo") && !conf.getNivel().equals("Clasificacion")) {
											map.get(cp2.getIdConfiguracionFamilia()).put(cp2.getProducto().getIdProducto(), cp2);
										}


									}else {
										map.get(cp2.getIdConfiguracionFamilia()).put(cp2.getProducto().getIdProducto(), cp2);
									}

								} else {
									
									map.get(cp2.getIdConfiguracionFamilia()).put(cp2.getProducto().getIdProducto(), cp2);
								}
							}
							else {
								map.put(cp2.getIdConfiguracionFamilia(), new HashMap<Long, ConfiguracionPrecioProducto>());
								map.get(cp2.getIdConfiguracionFamilia()).put(cp2.getProducto().getIdProducto(), cp2);
							}

						}
					}

					for (Entry<Long, Map<Long, ConfiguracionPrecioProducto>> mapFamilia : map.entrySet()) {
						List<Producto> lstAux = new ArrayList<Producto>();
						ccAux = new ConfiguracionContrato();
						ccAux.setMarca(cc.getMarca());

						for (Entry<Long, ConfiguracionPrecioProducto> mapaProducto : mapFamilia.getValue().entrySet()) {
							Long key = mapaProducto.getKey();
							ConfiguracionPrecioProducto cp2 = mapaProducto.getValue();
							cp2.getProducto().setConcepto(this.productoDAO.obtenerDescripcionProducto(cp2.getProducto().getCodigo(), cp2.getProducto().getFabrica()));

							ccAux.setTipo(cp2.getProducto().getTipo().toUpperCase());
							ccAux.setSubtipo(cp2.getProducto().getSubtipo().toUpperCase());
							ccAux.setControl(cp2.getProducto().getControl().toUpperCase());
							ccAux.setIndustria(cp2.getProducto().getIndustria().toUpperCase());

							for (int i = 0; i < cp2.getTiempoEntregaRuta().size(); i++) {

								if (rutaCliente.equalsIgnoreCase(cp2.getTiempoEntregaRuta().get(i).getRuta()))
								{
									cp2.getProducto().setTiempoEntrega(cp2.getTiempoEntregaRuta().get(i).getRequierePNo());
								}
							}

							lstAux.add(cp2.getProducto());
						}

						ccAux.setListaProductos(lstAux);
						marcasContratoAux.add(ccAux);
					}

				}


			}
			//cc.setListaProductos(listaAuxProducto);


			String reporteContrato="";

			reporteContrato="Contrato.jrxml";


			String ruta = funcion.obtenerRutaServidor("contratos", "");			
			String rutaguardar = funcion.obtenerRutaServidor("contratos", "");
			InputStream inputStream = null;
			//JasperPrint jasperPrint = null;

			try {

				List<NivelIngreso> ni = nivelIngresoDAO.findLimitesNivelIngreso();
				Cliente	cli =  catalogoClienteDAO.obtenerClienteXId(contrato.getIdCliente(), ni);
				String fechaInicioConFOrmato = new SimpleDateFormat("dd MMMM, yyyy").format(contrato.getFechaInicio());
				String fechaFinConFOrmato = new SimpleDateFormat("dd MMMM, yyyy").format(contrato.getFechaFin());

				inputStream = new FileInputStream(ruta+reporteContrato);						
				//JasperDesign jasperDesign = JRXmlLoader.load(inputStream);				
				//JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
				Map<String, Object> parametros = new HashMap<String, Object>();				
				parametros.put( "folio", contrato.getFolio()); 
				//				parametros.put( "ruta", ruta );
				parametros.put("nomEmpresa",cli.getNombre());
				parametros.put( "fechaInicio",fechaInicioConFOrmato);
				parametros.put("fechaFin", fechaFinConFOrmato);
				parametros.put( "condicionesPago", contrato.getCondionesPago());
				parametros.put( "listaMarcas", marcasContratoAux);
				String rutaLogo = ruta + "logo_proquifa.png";
				log.info("LOGOOOOOOO: " + rutaLogo );
				parametros.put("logo", rutaLogo);

				File theDir = new File(rutaguardar);

				// if the directory does not exist, create it
				if (!theDir.exists()) {
					log.info("creating directory..."+ " "+rutaguardar);
					boolean result = false;

					try{
						theDir.mkdir();
						result = true;
					} 
					catch(SecurityException se){
						//handle it
					}        
					if(result) {    
						log.info("DIR created");  
					}
				}

			//	jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());		
			//	JasperExportManager.exportReportToPdfFile( jasperPrint, rutaguardar+contrato.getIdContrato() +".pdf");				


				return contrato.getIdContrato();

			}
			catch (Exception e) {
				log.info(e.getMessage());
				e.printStackTrace();
			}

			return contrato.getIdContrato();

		} catch (Exception e) {
			e.printStackTrace();
			//			funcion.enviarCorreoAvisoExepcion(e);
			return -1L;
		}
	}
	
	
	
	
	@Override
	@Transactional
	public Long finalizarContrato(Contrato contrato,byte[] contratoFirmado,boolean renovarContrato) throws ProquifaNetException {
		try {
			
			String nombreArchivo;
			String rutaContrato = funcion.obtenerRutaServidor("contratos", "");
			nombreArchivo = rutaContrato + contrato.getIdContrato()+"_firmado"+".pdf";
			log.info("Ruta:" + nombreArchivo);	
			File archivo = new File(nombreArchivo);
			boolean buscarNombre = true;
			if (archivo.exists()){
				
				for (int i = 1; buscarNombre ; i++) { 
					
					File archivoAux = new File(rutaContrato + contrato.getIdContrato()+"_"+i+"_firmado"+".pdf");
					log.info("nombreBuscado" + rutaContrato + contrato.getIdContrato()+"_"+i+"_firmado"+".pdf");	
					if(!archivoAux.exists())
					{
							File nuevoNombre = new File(rutaContrato+contrato.getIdContrato()+"_"+i+"_firmado"+".pdf");
							archivo.renameTo(nuevoNombre);
							buscarNombre = false;
						    break;
					}

				}
				
			}
			
		    Funcion.subirArchivo(contratoFirmado, String.valueOf(contrato.getIdContrato())+"_firmado" + ".pdf", rutaContrato);
		    contrato.setFinalizado(true);
			clienteDAO.actualizarContratoCliente(contrato);
		    
			return 1L;
		} catch (Exception e) {
			e.printStackTrace();
			return -1L;
		}
	}
	
	public List<CatalogoItem> obtenerUsoCFDI() throws ProquifaNetException{
		try{
			return catalogoClienteDAO.obtenerUsoCFDI();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<CatalogoItem> obtenerMetodoPago() throws ProquifaNetException {
		try{
			return catalogoClienteDAO.obtenerMetodoPago();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<CatalogoItem> obtenerClaveUnidad() throws ProquifaNetException {
		try{
			return catalogoClienteDAO.obtenerClaveUnidad();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<CatalogoItem> obtenerClaveProdServ() throws ProquifaNetException {
		try{
			return catalogoClienteDAO.obtenerClaveProdServ();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	


	
}
