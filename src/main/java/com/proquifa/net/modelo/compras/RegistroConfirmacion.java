/**
 * 
 */
package com.proquifa.net.modelo.compras;

import java.util.Date;

import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.Contacto;
import com.proquifa.net.modelo.ventas.Pedido;


/**
 * @author yosimar.mendez
 *
 */
public class RegistroConfirmacion extends PartidaCompra implements Cloneable{

	private long idProveedor;
	private String nombreProveedor;
    private int totalOC;
	private int totalProductos;
	private int piezas;
	private double monto;
	private TipoPPedido regular;
	private TipoPPedido programado;
	private TipoPPedido flete;
	private int urgente;
	private int enTiempo;
	private int fueraTiempo;
	private int dia1;
	private int dia2;
	private int dia3;
	private int dia4; //Mayor a 3 dias
	private int totalPPedidos;
	private Date fee;
	private int totalConfirmacion;
	private int totalMonitoreo;
	
	private Contacto contacto;
	private Cliente clientes;
	private String entregarEn;
	private Date fechaTransito;
	private String tipoPartida;
	private Date fechaAlmacen;
	private Date fechaAlmacenAnterior;
	
	private Date fechaProveedor;
	private Date fechaMonitoreo;
	private Date fechaNueva;
	private String motivo;
	
	private String confirma;
	private String referencia;
	private byte[] documento;
	
	private String nombreVendedor;
	private String alisasVendedor;
	private String ext;
	
	private Pedido pedidos;
	//Si es 0 es Primera Gestion, Si es mayor entonces ya fue gestionada
	private int gestionar;
	
	private Double montoDolar;
	private String estadoCliente;
	private String estadoPedido;
	private String MedioPagoProveedor;
	private String CPagoProveedor;

	
	public RegistroConfirmacion() {
		super();
	}
	
	public RegistroConfirmacion(RegistroConfirmacion registro) {
		super();
		this.idProveedor = registro.idProveedor;
		this.nombreProveedor = registro.nombreProveedor;
		this.totalOC = registro.totalOC;
		this.totalProductos = registro.totalProductos;
		this.piezas = registro.piezas;
		this.monto = registro.monto;
		this.regular = registro.regular;
		this.programado = registro.programado;
		this.flete = registro.flete;
		this.urgente = registro.urgente;
		this.enTiempo = registro.enTiempo;
		this.fueraTiempo = registro.fueraTiempo;
		this.dia1 = registro.dia1;
		this.dia2 = registro.dia2;
		this.dia3 = registro.dia3;
		this.dia4 = registro.dia4;
		this.totalPPedidos = registro.totalPPedidos;
		this.fee = registro.fee;
		this.contacto = registro.contacto;
		this.entregarEn = registro.entregarEn;
		this.fechaTransito = registro.fechaTransito;
		this.tipoPartida = registro.tipoPartida;
		this.fechaAlmacen = registro.fechaAlmacen;
		this.pedidos = registro.pedidos;
	}

	/**
	 * @return the idProveedor
	 */
	public long getIdProveedor() {
		return idProveedor;
	}
	/**
	 * @param idProveedor the idProveedor to set
	 */
	public void setIdProveedor(long idProveedor) {
		this.idProveedor = idProveedor;
	}
	/**
	 * @return the nombreProveedor
	 */
	public String getNombreProveedor() {
		return nombreProveedor;
	}
	/**
	 * @param nombreProveedor the nombreProveedor to set
	 */
	public void setNombreProveedor(String nombreProveedor) {
		this.nombreProveedor = nombreProveedor;
	}
	/**
	 * @return the totalOC
	 */
	public int getTotalOC() {
		return totalOC;
	}
	/**
	 * @param totalOC the totalOC to set
	 */
	public void setTotalOC(int totalOC) {
		this.totalOC = totalOC;
	}
	/**
	 * @return the totalProductos
	 */
	public int getTotalProductos() {
		return totalProductos;
	}
	/**
	 * @param totalProductos the totalProductos to set
	 */
	public void setTotalProductos(int totalProductos) {
		this.totalProductos = totalProductos;
	}
	/**
	 * @return the piezas
	 */
	public int getPiezas() {
		return piezas;
	}
	/**
	 * @param piezas the piezas to set
	 */
	public void setPiezas(int piezas) {
		this.piezas = piezas;
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
	 * @return the urgente
	 */
	public int getUrgente() {
		return urgente;
	}
	/**
	 * @param urgente the urgente to set
	 */
	public void setUrgente(int urgente) {
		this.urgente = urgente;
	}
	/**
	 * @return the enTiempo
	 */
	public int getEnTiempo() {
		return enTiempo;
	}
	/**
	 * @param enTiempo the enTiempo to set
	 */
	public void setEnTiempo(int enTiempo) {
		this.enTiempo = enTiempo;
	}
	/**
	 * @return the fueraTiempo
	 */
	public int getFueraTiempo() {
		return fueraTiempo;
	}
	/**
	 * @param fueraTiempo the fueraTiempo to set
	 */
	public void setFueraTiempo(int fueraTiempo) {
		this.fueraTiempo = fueraTiempo;
	}
	/**
	 * @return the dia1
	 */
	public int getDia1() {
		return dia1;
	}
	/**
	 * @param dia1 the dia1 to set
	 */
	public void setDia1(int dia1) {
		this.dia1 = dia1;
	}
	/**
	 * @return the dia2
	 */
	public int getDia2() {
		return dia2;
	}
	/**
	 * @param dia2 the dia2 to set
	 */
	public void setDia2(int dia2) {
		this.dia2 = dia2;
	}
	/**
	 * @return the dia3
	 */
	public int getDia3() {
		return dia3;
	}
	/**
	 * @param dia3 the dia3 to set
	 */
	public void setDia3(int dia3) {
		this.dia3 = dia3;
	}
	/**
	 * @return the dia4
	 */
	public int getDia4() {
		return dia4;
	}
	/**
	 * @param dia4 the dia4 to set
	 */
	public void setDia4(int dia4) {
		this.dia4 = dia4;
	}
	/**
	 * @return the totalPPedidos
	 */
	public int getTotalPPedidos() {
		return totalPPedidos;
	}
	/**
	 * @param totalPPedidos the totalPPedidos to set
	 */
	public void setTotalPPedidos(int totalPPedidos) {
		this.totalPPedidos = totalPPedidos;
	}
	/**
	 * @return the fee
	 */
	public Date getFee() {
		return fee;
	}
	/**
	 * @param fee the fee to set
	 */
	public void setFee(Date fee) {
		this.fee = fee;
	}

	/**
	 * @return the regular
	 */
	public TipoPPedido getRegular() {
		return regular;
	}

	/**
	 * @param regular the regular to set
	 */
	public void setRegular(TipoPPedido regular) {
		this.regular = regular;
	}

	/**
	 * @return the programado
	 */
	public TipoPPedido getProgramado() {
		return programado;
	}

	/**
	 * @param programado the programado to set
	 */
	public void setProgramado(TipoPPedido programado) {
		this.programado = programado;
	}

	/**
	 * @return the flete
	 */
	public TipoPPedido getFlete() {
		return flete;
	}

	/**
	 * @param flete the flete to set
	 */
	public void setFlete(TipoPPedido flete) {
		this.flete = flete;
	}

	/**
	 * @return the contacto
	 */
	public Contacto getContacto() {
		return contacto;
	}

	/**
	 * @param contacto the contacto to set
	 */
	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}

	/**
	 * @return the entregarEn
	 */
	public String getEntregarEn() {
		return entregarEn;
	}

	/**
	 * @param entregarEn the entregarEn to set
	 */
	public void setEntregarEn(String entregarEn) {
		this.entregarEn = entregarEn;
	}

	/**
	 * @return the fechaTransito
	 */
	public Date getFechaTransito() {
		return fechaTransito;
	}

	/**
	 * @param fechaTransito the fechaTransito to set
	 */
	public void setFechaTransito(Date fechaTransito) {
		this.fechaTransito = fechaTransito;
	}

	/**
	 * @return the tipoPartida
	 */
	public String getTipoPartida() {
		return tipoPartida;
	}

	/**
	 * @param tipoPartida the tipoPartida to set
	 */
	public void setTipoPartida(String tipoPartida) {
		this.tipoPartida = tipoPartida;
	}

	/**
	 * @return the fechaAlmacen
	 */
	public Date getFechaAlmacen() {
		return fechaAlmacen;
	}

	/**
	 * @param fechaAlmacen the fechaAlmacen to set
	 */
	public void setFechaAlmacen(Date fechaAlmacen) {
		this.fechaAlmacen = fechaAlmacen;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

	/**
	 * @return the fechaProveedor
	 */
	public Date getFechaProveedor() {
		return fechaProveedor;
	}

	/**
	 * @param fechaProveedor the fechaProveedor to set
	 */
	public void setFechaProveedor(Date fechaProveedor) {
		this.fechaProveedor = fechaProveedor;
	}

	/**
	 * @return the fechaMonitoreo
	 */
	public Date getFechaMonitoreo() {
		return fechaMonitoreo;
	}

	/**
	 * @param fechaMonitoreo the fechaMonitoreo to set
	 */
	public void setFechaMonitoreo(Date fechaMonitoreo) {
		this.fechaMonitoreo = fechaMonitoreo;
	}

	/**
	 * @return the motivo
	 */
	public String getMotivo() {
		return motivo;
	}

	/**
	 * @param motivo the motivo to set
	 */
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	/**
	 * @return the clientes
	 */
	public Cliente getClientes() {
		return clientes;
	}

	/**
	 * @param clientes the clientes to set
	 */
	public void setClientes(Cliente clientes) {
		this.clientes = clientes;
	}

	/**
	 * @return the pedidos
	 */
	public Pedido getPedidos() {
		return pedidos;
	}

	/**
	 * @param pedidos the pedidos to set
	 */
	public void setPedidos(Pedido pedidos) {
		this.pedidos = pedidos;
	}

	/**
	 * @return the confirma
	 */
	public String getConfirma() {
		return confirma;
	}

	/**
	 * @param confirma the confirma to set
	 */
	public void setConfirma(String confirma) {
		this.confirma = confirma;
	}

	/**
	 * @return the referencia
	 */
	public String getReferencia() {
		return referencia;
	}

	/**
	 * @param referencia the referencia to set
	 */
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	/**
	 * @return the gestionar
	 */
	public int getGestionar() {
		return gestionar;
	}

	/**
	 * @param gestionar the gestionar to set
	 */
	public void setGestionar(int gestionar) {
		this.gestionar = gestionar;
	}

	/**
	 * @return the fechaNueva
	 */
	public Date getFechaNueva() {
		return fechaNueva;
	}

	/**
	 * @param fechaNueva the fechaNueva to set
	 */
	public void setFechaNueva(Date fechaNueva) {
		this.fechaNueva = fechaNueva;
	}

	/**
	 * @return the documento
	 */
	public byte[] getDocumento() {
		return documento;
	}

	/**
	 * @param documento the documento to set
	 */
	public void setDocumento(byte[] documento) {
		this.documento = documento;
	}

	/**
	 * @return the nombreVendedor
	 */
	public String getNombreVendedor() {
		return nombreVendedor;
	}

	/**
	 * @param nombreVendedor the nombreVendedor to set
	 */
	public void setNombreVendedor(String nombreVendedor) {
		this.nombreVendedor = nombreVendedor;
	}

	/**
	 * @return the ext
	 */
	public String getExt() {
		return ext;
	}

	/**
	 * @param ext the ext to set
	 */
	public void setExt(String ext) {
		this.ext = ext;
	}

	/**
	 * @return the fechaAlmacenAnterior
	 */
	public Date getFechaAlmacenAnterior() {
		return fechaAlmacenAnterior;
	}

	/**
	 * @param fechaAlmacenAnterior the fechaAlmacenAnterior to set
	 */
	public void setFechaAlmacenAnterior(Date fechaAlmacenAnterior) {
		this.fechaAlmacenAnterior = fechaAlmacenAnterior;
	}

	/**
	 * @return the totalConfirmacion
	 */
	public int getTotalConfirmacion() {
		return totalConfirmacion;
	}

	/**
	 * @param totalConfirmacion the totalConfirmacion to set
	 */
	public void setTotalConfirmacion(int totalConfirmacion) {
		this.totalConfirmacion = totalConfirmacion;
	}

	/**
	 * @return the totalMonitoreo
	 */
	public int getTotalMonitoreo() {
		return totalMonitoreo;
	}

	/**
	 * @param totalMonitoreo the totalMonitoreo to set
	 */
	public void setTotalMonitoreo(int totalMonitoreo) {
		this.totalMonitoreo = totalMonitoreo;
	}

	/**
	 * @return the montoDolar
	 */
	public Double getMontoDolar() {
		return montoDolar;
	}

	/**
	 * @param montoDolar the montoDolar to set
	 */
	public void setMontoDolar(Double montoDolar) {
		this.montoDolar = montoDolar;
	}

	/**
	 * @return the estadoCliente
	 */
	public String getEstadoCliente() {
		return estadoCliente;
	}

	/**
	 * @param estadoCliente the estadoCliente to set
	 */
	public void setEstadoCliente(String estadoCliente) {
		this.estadoCliente = estadoCliente;
	}

	/**
	 * @return the estadoPedido
	 */
	public String getEstadoPedido() {
		return estadoPedido;
	}

	/**
	 * @param estadoPedido the estadoPedido to set
	 */
	public void setEstadoPedido(String estadoPedido) {
		this.estadoPedido = estadoPedido;
	}

	public String getMedioPagoProveedor() {
		return MedioPagoProveedor;
	}

	public void setMedioPagoProveedor(String medioPagoProveedor) {
		MedioPagoProveedor = medioPagoProveedor;
	}

	public String getCPagoProveedor() {
		return CPagoProveedor;
	}

	public void setCPagoProveedor(String cPagoProveedor) {
		CPagoProveedor = cPagoProveedor;
	}

	public String getAlisasVendedor() {
		return alisasVendedor;
	}

	public void setAlisasVendedor(String alisasVendedor) {
		this.alisasVendedor = alisasVendedor;
	}
}
