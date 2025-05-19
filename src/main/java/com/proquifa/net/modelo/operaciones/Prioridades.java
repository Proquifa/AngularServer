/**
 * 
 */
package com.proquifa.net.modelo.operaciones;

import java.io.Serializable;
import java.util.List;

/**
 * @author ymendez
 *
 */
public class Prioridades implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2220931925265224197L;
	private Integer totalClientes;
	private Integer totalPartidas;
	private Integer totalPiezas;
	private Integer totalPedidos;
	private Integer totalEmbalar;
	private Integer totalInspeccion;
	private String ruta;
	private String zona;
	
	private Integer idCliente;
	private String cliente;
	private String etiqueta;
	private Integer idHorario;
	private String cpedido;
	private String contacto;
	private String fee;
	private String fechaPartida;
	private Integer idProducto;
	private String codigo;
	private String descripcion;
	private String manejoTransporte;
	private String fabrica;
	private String puesto;
	private Integer piezas;
	private String tipo;
	private Integer idPPedido;
	private String calle;
	private String cp;
	
	private Integer dias;
	private Integer urgencia;
	
	private List<Prioridades> partidas;
	
	private String idPPedidos;
	private Integer indicePrioridad;
	
	private int programado;
	private double monto;
	
	private Integer pausado;
	
	private Integer restriccion;
	private Integer facturaRemision;
	private Integer remision;
	
	private Integer restriccionMetodo;
	private Integer metodo;
	
	private List<String> cpedidos;
	
	private String nombreEsac;
	private String nombreCobrador;
	private String emailEsac;
	private String emailCobrador;
	private String extEsac;
	private String extCobrador;
	private String control;
	private Integer idPCompra;
	private Boolean active;
	/**
	 * 
	 */
	public Prioridades() {
		super();
		this.totalEmbalar = 0;
		this.totalInspeccion = 0;
		// TODO Auto-generated constructor stub
	}
	
	

	/**
	 * @param totalClientes
	 * @param ruta
	 * @param zona
	 * @param idCliente
	 * @param cliente
	 * @param etiqueta
	 * @param idHorario
	 * @param cpedido
	 * @param contacto
	 * @param fee
	 * @param idProducto
	 * @param codigo
	 * @param descripcion
	 * @param manejoTransporte
	 * @param fabrica
	 * @param puesto
	 * @param piezas
	 * @param tipo
	 * @param idPPedido
	 * @param calle
	 * @param cp
	 * @param partidas
	 */
	public Prioridades(Prioridades prioridad) {
		super();
		this.totalClientes = prioridad.totalClientes;
		this.ruta = prioridad.ruta;
		this.zona = prioridad.zona;
		this.idCliente = prioridad.idCliente;
		this.cliente = prioridad.cliente;
		this.etiqueta = prioridad.etiqueta;
		this.idHorario = prioridad.idHorario;
		this.cpedido = prioridad.cpedido;
		this.contacto = prioridad.contacto;
		this.fee = prioridad.fee;
		this.idProducto = prioridad.idProducto;
		this.codigo = prioridad.codigo;
		this.descripcion = prioridad.descripcion;
		this.manejoTransporte = prioridad.manejoTransporte;
		this.fabrica = prioridad.fabrica;
		this.puesto = prioridad.puesto;
		this.piezas = prioridad.piezas;
		this.tipo = prioridad.tipo;
		this.idPPedido = prioridad.idPPedido;
		this.calle = prioridad.calle;
		this.cp = prioridad.cp;
		this.urgencia = prioridad.urgencia;
		this.fechaPartida = prioridad.fechaPartida;
		this.indicePrioridad = prioridad.indicePrioridad;
		this.programado = prioridad.programado;
		this.monto = prioridad.monto;
		this.pausado = prioridad.pausado;
		this.restriccion = prioridad.restriccion;
		this.facturaRemision = prioridad.facturaRemision;
		this.remision = prioridad.remision;
		this.metodo = prioridad.metodo;
		this.nombreCobrador = prioridad.nombreCobrador;
		this.nombreEsac = prioridad.nombreEsac;
		this.emailCobrador = prioridad.emailCobrador;
		this.emailEsac = prioridad.emailEsac;
		this.extCobrador = prioridad.extCobrador;
		this.extEsac = prioridad.extEsac;
	}



	/**
	 * @return the totalClientes
	 */
	public Integer getTotalClientes() {
		return totalClientes;
	}

	/**
	 * @param totalClientes the totalClientes to set
	 */
	public void setTotalClientes(Integer totalClientes) {
		this.totalClientes = totalClientes;
	}

	/**
	 * @return the ruta
	 */
	public String getRuta() {
		return ruta;
	}

	/**
	 * @param ruta the ruta to set
	 */
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	/**
	 * @return the zona
	 */
	public String getZona() {
		return zona;
	}

	/**
	 * @param zona the zona to set
	 */
	public void setZona(String zona) {
		this.zona = zona;
	}

	/**
	 * @return the idCliente
	 */
	public Integer getIdCliente() {
		return idCliente;
	}

	/**
	 * @param idCliente the idCliente to set
	 */
	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	/**
	 * @return the cliente
	 */
	public String getCliente() {
		return cliente;
	}

	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	/**
	 * @return the etiqueta
	 */
	public String getEtiqueta() {
		return etiqueta;
	}

	/**
	 * @param etiqueta the etiqueta to set
	 */
	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	/**
	 * @return the idHorario
	 */
	public Integer getIdHorario() {
		return idHorario;
	}

	/**
	 * @param idHorario the idHorario to set
	 */
	public void setIdHorario(Integer idHorario) {
		this.idHorario = idHorario;
	}

	/**
	 * @return the cpedido
	 */
	public String getCpedido() {
		return cpedido;
	}

	/**
	 * @param cpedido the cpedido to set
	 */
	public void setCpedido(String cpedido) {
		this.cpedido = cpedido;
	}

	/**
	 * @return the contacto
	 */
	public String getContacto() {
		return contacto;
	}

	/**
	 * @param contacto the contacto to set
	 */
	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	/**
	 * @return the idProducto
	 */
	public Integer getIdProducto() {
		return idProducto;
	}

	/**
	 * @param idProducto the idProducto to set
	 */
	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the manejoTransporte
	 */
	public String getManejoTransporte() {
		return manejoTransporte;
	}

	/**
	 * @param manejoTransporte the manejoTransporte to set
	 */
	public void setManejoTransporte(String manejoTransporte) {
		this.manejoTransporte = manejoTransporte;
	}

	/**
	 * @return the fabrica
	 */
	public String getFabrica() {
		return fabrica;
	}

	/**
	 * @param fabrica the fabrica to set
	 */
	public void setFabrica(String fabrica) {
		this.fabrica = fabrica;
	}

	/**
	 * @return the puesto
	 */
	public String getPuesto() {
		return puesto;
	}

	/**
	 * @param puesto the puesto to set
	 */
	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	/**
	 * @return the piezas
	 */
	public Integer getPiezas() {
		return piezas;
	}

	/**
	 * @param piezas the piezas to set
	 */
	public void setPiezas(Integer piezas) {
		this.piezas = piezas;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the idPPedido
	 */
	public Integer getIdPPedido() {
		return idPPedido;
	}

	/**
	 * @param idPPedido the idPPedido to set
	 */
	public void setIdPPedido(Integer idPPedido) {
		this.idPPedido = idPPedido;
	}

	/**
	 * @return the calle
	 */
	public String getCalle() {
		return calle;
	}

	/**
	 * @param calle the calle to set
	 */
	public void setCalle(String calle) {
		this.calle = calle;
	}

	/**
	 * @return the cp
	 */
	public String getCp() {
		return cp;
	}

	/**
	 * @param cp the cp to set
	 */
	public void setCp(String cp) {
		this.cp = cp;
	}

	/**
	 * @return the partidas
	 */
	public List<Prioridades> getPartidas() {
		return partidas;
	}

	/**
	 * @param partidas the partidas to set
	 */
	public void setPartidas(List<Prioridades> partidas) {
		this.partidas = partidas;
	}



	/**
	 * @return the idPPedidos
	 */
	public String getIdPPedidos() {
		return idPPedidos;
	}



	/**
	 * @param idPPedidos the idPPedidos to set
	 */
	public void setIdPPedidos(String idPPedidos) {
		this.idPPedidos = idPPedidos;
	}



	/**
	 * @return the dias
	 */
	public Integer getDias() {
		return dias;
	}



	/**
	 * @param dias the dias to set
	 */
	public void setDias(Integer dias) {
		this.dias = dias;
	}



	/**
	 * @return the totalPartidas
	 */
	public Integer getTotalPartidas() {
		return totalPartidas;
	}



	/**
	 * @param totalPartidas the totalPartidas to set
	 */
	public void setTotalPartidas(Integer totalPartidas) {
		this.totalPartidas = totalPartidas;
	}



	/**
	 * @return the totalPiezas
	 */
	public Integer getTotalPiezas() {
		return totalPiezas;
	}



	/**
	 * @param totalPiezas the totalPiezas to set
	 */
	public void setTotalPiezas(Integer totalPiezas) {
		this.totalPiezas = totalPiezas;
	}



	/**
	 * @return the totalPedidos
	 */
	public Integer getTotalPedidos() {
		return totalPedidos;
	}



	/**
	 * @param totalPedidos the totalPedidos to set
	 */
	public void setTotalPedidos(Integer totalPedidos) {
		this.totalPedidos = totalPedidos;
	}



	/**
	 * @return the urgencia
	 */
	public Integer getUrgencia() {
		return urgencia;
	}



	/**
	 * @param urgencia the urgencia to set
	 */
	public void setUrgencia(Integer urgencia) {
		this.urgencia = urgencia;
	}



	/**
	 * @return the totalEmbalar
	 */
	public Integer getTotalEmbalar() {
		return totalEmbalar;
	}



	/**
	 * @param totalEmbalar the totalEmbalar to set
	 */
	public void setTotalEmbalar(Integer totalEmbalar) {
		this.totalEmbalar = totalEmbalar;
	}



	/**
	 * @return the totalInspeccion
	 */
	public Integer getTotalInspeccion() {
		return totalInspeccion;
	}



	/**
	 * @param totalInspeccion the totalInspeccion to set
	 */
	public void setTotalInspeccion(Integer totalInspeccion) {
		this.totalInspeccion = totalInspeccion;
	}



	/**
	 * @return the fee
	 */
	public String getFee() {
		return fee;
	}



	/**
	 * @param fee the fee to set
	 */
	public void setFee(String fee) {
		this.fee = fee;
	}



	/**
	 * @return the fechaPartida
	 */
	public String getFechaPartida() {
		return fechaPartida;
	}



	/**
	 * @param fechaPartida the fechaPartida to set
	 */
	public void setFechaPartida(String fechaPartida) {
		this.fechaPartida = fechaPartida;
	}



	public Integer getIndicePrioridad() {
		return indicePrioridad;
	}



	public void setIndicePrioridad(Integer indicePrioridad) {
		this.indicePrioridad = indicePrioridad;
	}



	/**
	 * @return the programado
	 */
	public int getProgramado() {
		return programado;
	}



	/**
	 * @param programado the programado to set
	 */
	public void setProgramado(int programado) {
		this.programado = programado;
	}



	/**
	 * @return the monto
	 */
	public double getMonto() {
		return monto;
	}



	/**
	 * @param monto the monto to set
	 */
	public void setMonto(double monto) {
		this.monto = monto;
	}



	/**
	 * @return the pausado
	 */
	public Integer getPausado() {
		return pausado;
	}



	/**
	 * @param pausado the pausado to set
	 */
	public void setPausado(Integer pausado) {
		this.pausado = pausado;
	}



	/**
	 * @return the restriccion
	 */
	public Integer getRestriccion() {
		return restriccion;
	}



	/**
	 * @param restriccion the restriccion to set
	 */
	public void setRestriccion(Integer restriccion) {
		this.restriccion = restriccion;
	}



	/**
	 * @return the facturaRemision
	 */
	public Integer getFacturaRemision() {
		return facturaRemision;
	}



	/**
	 * @param facturaRemision the facturaRemision to set
	 */
	public void setFacturaRemision(Integer facturaRemision) {
		this.facturaRemision = facturaRemision;
	}



	/**
	 * @return the remision
	 */
	public Integer getRemision() {
		return remision;
	}



	/**
	 * @param remision the remision to set
	 */
	public void setRemision(Integer remision) {
		this.remision = remision;
	}



	/**
	 * @return the restriccionMetodo
	 */
	public Integer getRestriccionMetodo() {
		return restriccionMetodo;
	}



	/**
	 * @param restriccionMetodo the restriccionMetodo to set
	 */
	public void setRestriccionMetodo(Integer restriccionMetodo) {
		this.restriccionMetodo = restriccionMetodo;
	}



	/**
	 * @return the metodo
	 */
	public Integer getMetodo() {
		return metodo;
	}



	/**
	 * @param metodo the metodo to set
	 */
	public void setMetodo(Integer metodo) {
		this.metodo = metodo;
	}



	/**
	 * @return the cpedidos
	 */
	public List<String> getCpedidos() {
		return cpedidos;
	}



	/**
	 * @param cpedidos the cpedidos to set
	 */
	public void setCpedidos(List<String> cpedidos) {
		this.cpedidos = cpedidos;
	}



	/**
	 * @return the nombreEsac
	 */
	public String getNombreEsac() {
		return nombreEsac;
	}



	/**
	 * @param nombreEsac the nombreEsac to set
	 */
	public void setNombreEsac(String nombreEsac) {
		this.nombreEsac = nombreEsac;
	}



	/**
	 * @return the nombreCobrador
	 */
	public String getNombreCobrador() {
		return nombreCobrador;
	}



	/**
	 * @param nombreCobrador the nombreCobrador to set
	 */
	public void setNombreCobrador(String nombreCobrador) {
		this.nombreCobrador = nombreCobrador;
	}



	/**
	 * @return the emailEsac
	 */
	public String getEmailEsac() {
		return emailEsac;
	}



	/**
	 * @param emailEsac the emailEsac to set
	 */
	public void setEmailEsac(String emailEsac) {
		this.emailEsac = emailEsac;
	}



	/**
	 * @return the emailCobrador
	 */
	public String getEmailCobrador() {
		return emailCobrador;
	}



	/**
	 * @param emailCobrador the emailCobrador to set
	 */
	public void setEmailCobrador(String emailCobrador) {
		this.emailCobrador = emailCobrador;
	}



	/**
	 * @return the extEsac
	 */
	public String getExtEsac() {
		return extEsac;
	}



	/**
	 * @param extEsac the extEsac to set
	 */
	public void setExtEsac(String extEsac) {
		this.extEsac = extEsac;
	}



	/**
	 * @return the extCobrador
	 */
	public String getExtCobrador() {
		return extCobrador;
	}



	/**
	 * @param extCobrador the extCobrador to set
	 */
	public void setExtCobrador(String extCobrador) {
		this.extCobrador = extCobrador;
	}



	public String getControl() {
		return control;
	}



	public void setControl(String control) {
		this.control = control;
	}



	public Integer getIdPCompra() {
		return idPCompra;
	}



	public void setIdPCompra(Integer idPCompra) {
		this.idPCompra = idPCompra;
	}



	public Boolean getActive() {
		return active;
	}



	public void setActive(Boolean active) {
		this.active = active;
	}

}
