package com.proquifa.net.persistencia.ventas.impl;

public class PDFConfirmacionPedido {
    private int num;
    private int cantidad;
    private String descripcion;
    private float precioU;
    private float importe;
    private String estatus;
    private String fee;

    private String cliente;
    private String contacto;
    private String cpedido;
    private String fdp;
    private String fnueva;
    private String usuario;
    private Long idPcompra;
    private String ordenCompra;
    private String condicionPago;
    private String Moneda;
    private float iva;
    private String rfcFiscal;
    private String direccionFiscal;
    private int parciales;
    private String contactoArriba;
    private String contactoEntrega;
    private String direccionEntrega;
    private String referencia;
    private Float montoIva;
    private Float granTotal;
    private String puesto;
    private String area;
    private String razonSocial;
    private String fabrica;
    private String fechaRecepcion;
    private String fechaTramitacion;
    private String condicion;

    /**
     * @return the num
     */
    public int getNum() {
        return num;
    }
    /**
     * @param num the num to set
     */
    public void setNum(int num) {
        this.num = num;
    }
    /**
     * @return the cantidad
     */
    public int getCantidad() {
        return cantidad;
    }
    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
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
     * @return the precioU
     */
    public float getPrecioU() {
        return precioU;
    }
    /**
     * @param precioU the precioU to set
     */
    public void setPrecioU(float precioU) {
        this.precioU = precioU;
    }
    /**
     * @return the importe
     */
    public float getImporte() {
        return importe;
    }
    /**
     * @param importe the importe to set
     */
    public void setImporte(float importe) {
        this.importe = importe;
    }
    /**
     * @return the estatus
     */
    public String getEstatus() {
        return estatus;
    }
    /**
     * @param estatus the estatus to set
     */
    public void setEstatus(String estatus) {
        this.estatus = estatus;
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
     * @return the fdp
     */
    public String getFdp() {
        return fdp;
    }
    /**
     * @param fdp the fdp to set
     */
    public void setFdp(String fdp) {
        this.fdp = fdp;
    }
    /**
     * @return the fnueva
     */
    public String getFnueva() {
        return fnueva;
    }
    /**
     * @param fnueva the fnueva to set
     */
    public void setFnueva(String fnueva) {
        this.fnueva = fnueva;
    }
    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }
    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    /**
     * @return the idPcompra
     */
    public Long getIdPcompra() {
        return idPcompra;
    }
    /**
     * @param idPcompra the idPcompra to set
     */
    public void setIdPcompra(Long idPcompra) {
        this.idPcompra = idPcompra;
    }
    /**
     * @return the ordenCompra
     */
    public String getOrdenCompra() {
        return ordenCompra;
    }
    /**
     * @param ordenCompra the ordenCompra to set
     */
    public void setOrdenCompra(String ordenCompra) {
        this.ordenCompra = ordenCompra;
    }
    /**
     * @return the condicionPago
     */
    public String getCondicionPago() {
        return condicionPago;
    }
    /**
     * @param condicionPago the condicionPago to set
     */
    public void setCondicionPago(String condicionPago) {
        this.condicionPago = condicionPago;
    }
    /**
     * @return the moneda
     */
    public String getMoneda() {
        return Moneda;
    }
    /**
     * @param moneda the moneda to set
     */
    public void setMoneda(String moneda) {
        Moneda = moneda;
    }
    /**
     * @return the iva
     */
    public float getIva() {
        return iva;
    }
    /**
     * @param iva the iva to set
     */
    public void setIva(float iva) {
        this.iva = iva;
    }
    /**
     * @return the rfcFiscal
     */
    public String getRfcFiscal() {
        return rfcFiscal;
    }
    /**
     * @param rfcFiscal the rfcFiscal to set
     */
    public void setRfcFiscal(String rfcFiscal) {
        this.rfcFiscal = rfcFiscal;
    }
    /**
     * @return the direccionFiscal
     */
    public String getDireccionFiscal() {
        return direccionFiscal;
    }
    /**
     * @param direccionFiscal the direccionFiscal to set
     */
    public void setDireccionFiscal(String direccionFiscal) {
        this.direccionFiscal = direccionFiscal;
    }
    /**
     * @return the parciales
     */
    public int getParciales() {
        return parciales;
    }
    /**
     * @param parciales the parciales to set
     */
    public void setParciales(int parciales) {
        this.parciales = parciales;
    }
    /**
     * @return the contactoArriba
     */
    public String getContactoArriba() {
        return contactoArriba;
    }
    /**
     * @param contactoArriba the contactoArriba to set
     */
    public void setContactoArriba(String contactoArriba) {
        this.contactoArriba = contactoArriba;
    }
    /**
     * @return the contactoEntrega
     */
    public String getContactoEntrega() {
        return contactoEntrega;
    }
    /**
     * @param contactoEntrega the contactoEntrega to set
     */
    public void setContactoEntrega(String contactoEntrega) {
        this.contactoEntrega = contactoEntrega;
    }
    /**
     * @return the direccionEntrega
     */
    public String getDireccionEntrega() {
        return direccionEntrega;
    }
    /**
     * @param direccionEntrega the direccionEntrega to set
     */
    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
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
     * @return the montoIva
     */
    public Float getMontoIva() {
        return montoIva;
    }
    /**
     * @param montoIva the montoIva to set
     */
    public void setMontoIva(Float montoIva) {
        this.montoIva = montoIva;
    }
    /**
     * @return the granTotal
     */
    public Float getGranTotal() {
        return granTotal;
    }
    /**
     * @param granTotal the granTotal to set
     */
    public void setGranTotal(Float granTotal) {
        this.granTotal = granTotal;
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
     * @return the area
     */
    public String getArea() {
        return area;
    }
    /**
     * @param area the area to set
     */
    public void setArea(String area) {
        this.area = area;
    }
    /**
     * @return the razonSocial
     */
    public String getRazonSocial() {
        return razonSocial;
    }
    /**
     * @param razonSocial the razonSocial to set
     */
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
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
     * @return the fechaRecepcion
     */
    public String getFechaRecepcion() {
        return fechaRecepcion;
    }
    /**
     * @param fechaRecepcion the fechaRecepcion to set
     */
    public void setFechaRecepcion(String fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }
    /**
     * @return the fechaTramitacion
     */
    public String getFechaTramitacion() {
        return fechaTramitacion;
    }
    /**
     * @param fechaTramitacion the fechaTramitacion to set
     */
    public void setFechaTramitacion(String fechaTramitacion) {
        this.fechaTramitacion = fechaTramitacion;
    }
    /**
     * @return the condicion
     */
    public String getCondicion() {
        return condicion;
    }
    /**
     * @param condicion the condicion to set
     */
    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }
}
