package com.proquifa.net.modelo.comun;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.catalogos.cobrosypagos.facturista.Factura;
import com.proquifa.net.modelo.catalogos.cobrosypagos.facturista.NotaCredito;
import com.proquifa.net.modelo.catalogos.cobrosypagos.facturista.ReferenciasBancarias;


public class Cliente {
		
	private Long idCliente;   
	private Long numContactos;
	private Long idCobrador;
	private Double monto;
	private Long idCorporativo;

	private Integer cantidad;
	private Integer idEjecutivoVenta;
	
	private Double limiteCredito;
	private Double lineaCredito;
	private Double morosidad;
	
	private Boolean aceptaParciales;
	private Boolean entregaYRevision;
	private Boolean enviarEmail;
	private Boolean facturaPortal;
	private Boolean agregadoExpo;
	private Boolean habilitado;

	private Date fechaActualizacion;
	private Date fechaRegistro;
	
	private String calle;
	private String calleFiscal;
	private String nombre;
	private String codigoPostal;
	private String codigoPostalFiscal;
	private String comentaFacturacion;
	private String comentarios;
	private String comentariosCredito;
	private String condicionesPago;
	private String correoElectronico;
	private String delegacion;
	private String delegacionFiscal;
	private String ejecutivoVenta;
	private String empresaFactura;
	private String estado;
	private String estadoFiscal;
	private String numeroDeCuenta;
	private String objectivoCrecimiento;
	private String objetivoCrecimientoFundamental;
	private String origenRegistro;
	private String pais;
	private String paisFiscal;
	private String razonSocial;
	private String rfc;
	private String rol;
	private String ruta;
	private String sector;
	private String taxID;
	private String tipo;
	private String vendedor;
	private String zona;	
	private String folio;
	private String imagen;
	private String industria;
	private String mapa;
	private String medioPago;
	private String monedaFactura;
	private String nivelIngreso;
	private String nivelIngresos;
	private String nivelPrecio;
	private String cobrador;
	private String nombreCorporativo;
	private String nombreEjecutivoVenta;
	private String nombreESAC;
	private String nombreCobrador;

	private List<Factura> facturasAdeudo;
	private List<NotaCredito> notasC;
	private List<ReferenciasBancarias> referenciasBancarias;
	
	private byte[] bytes;
	private Integer importancia;
	private Integer dificultad;
	private Integer mantenimiento;
	
	private Long corporativo;
	private Long numeroDeCarteras;
	
	private List<Comentario> listaComentarios;
	private Direccion direccion ; // direccionEmpresa
	private Direccion direccionFacturacion;
	private Direccion direccionVisita;
	private Long idcartera ; 
	
	private String colonia;
	
	private int totalContactos;
	
	private boolean tieneCartera;
	
	private String nombreCarteras;
	private String parciales;
	
	private Long idEsacCli;
	private Long idEVCli;
	private String nomCobrador;
	private Long num_visitas_ano;
	
	private String destino;
	private Long numeroDeContratos;  
	private boolean tieneContrato;
	
	//UsoCFDI 
	private String usoCFDI;
	private String metodoDePago;

	private String RegimenFiscal;
	private String RegimenSocietario;

	private String PLXCliente;


	public String getPLXCliente() {
		return PLXCliente;
	}

	public void setPLXCliente(String PLXCliente) {
		this.PLXCliente = PLXCliente;
	}

	public String getRegimenSocietario() {
		return this.RegimenSocietario;
	}

	public void setRegimenSocietario(String regimenSocietario) {
		this.RegimenSocietario = regimenSocietario;
	}

	public String getRegimenFiscal() {
		return this.RegimenFiscal;
	}

	public void setRegimenFiscal(String regimenFiscal) {
		this.RegimenFiscal = regimenFiscal;
	}



	/**
	 * 
	 */
	public Cliente() {
		super();
		listaComentarios = new ArrayList<Comentario>();
	}

	public Cliente(Long idCliente) {
		super();
		this.idCliente = idCliente;
	}
	
	/**
	 * @return the listaComentarios
	 */
	public List<Comentario> getListaComentarios() {
		return listaComentarios;
	}
	/**
	 * @param listaComentarios the listaComentarios to set
	 */
	public void setListaComentarios(List<Comentario> listaComentarios) {
		this.listaComentarios = listaComentarios;
	}
	/**
	 * @return the aceptaParciales
	 */
	public Boolean getAceptaParciales() {
		return aceptaParciales;
	}
	/**
	 * @return the bytes
	 */
	public byte [] getBytes() {
		return bytes;
	}
	/**
	 * @return the calle
	 */
	public String getCalle() {
		return calle;
	}
	/**
	 * @return the calleFiscal
	 */
	public String getCalleFiscal() {
		return calleFiscal;
	}
	/**
	 * @return the cantidad
	 */
	public Integer getCantidad() {
		return cantidad;
	}
	/**
	 * @return the codigoPostal
	 */
	public String getCodigoPostal() {
		return codigoPostal;
	}
	/**
	 * @return the codigoPostalFiscal
	 */
	public String getCodigoPostalFiscal() {
		return codigoPostalFiscal;
	}
	public String getComentaFacturacion() {
		return comentaFacturacion;
	}
	/**
	 * @return the comentarios
	 */
	public String getComentarios() {
		return comentarios;
	}
	/**
	 * @return the comentariosCredito
	 */
	public String getComentariosCredito() {
		return comentariosCredito;
	}
	/**
	 * @return the condicionesPago
	 */
	public String getCondicionesPago() {
		return condicionesPago;
	}
	/**
	 * @return the correoElectronico
	 */
	public String getCorreoElectronico() {
		return correoElectronico;
	}
	/**
	 * @return the delegacion
	 */
	public String getDelegacion() {
		return delegacion;
	}
	/**
	 * @return the delegacionFiscal
	 */
	public String getDelegacionFiscal() {
		return delegacionFiscal;
	}
	/**
	 * @return the ejecutivoVenta
	 */
	public String getEjecutivoVenta() {
		return ejecutivoVenta;
	}
	/**
	 * @return the empresaFactura
	 */
	public String getEmpresaFactura() {
		return empresaFactura;
	}
	/**
	 * @return the entregaYRevision
	 */
	public Boolean getEntregaYRevision() {
		return entregaYRevision;
	}
	/**
	 * @return the enviarEmail
	 */
	public Boolean getEnviarEmail() {
		return enviarEmail;
	}
	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}
/**
	 * @return the estadoFiscal
	 */
	public String getEstadoFiscal() {
		return estadoFiscal;
	}
	/**
	 * @return the facturaPortal
	 */
	public Boolean getFacturaPortal() {
		return facturaPortal;
	}
	/**
	 * @return the facturasAdeudo
	 */
	public List<Factura> getFacturasAdeudo() {
		return facturasAdeudo;
	}
	/**
	 * @return the fechaActualizacion
	 */
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}
	/**
	 * @return the fechaRegistro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	/**
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}
	/**
	 * @return the habilitado
	 */
	public Boolean getHabilitado() {
		return habilitado;
	}
	/**
	 * @return the idCliente
	 */
	public Long getIdCliente() {
		return idCliente;
	}
	/**
	 * @return the idEjecutivoVenta
	 */
	public Integer getIdEjecutivoVenta() {
		return idEjecutivoVenta;
	}
	/**
	 * @return the imagen
	 */
	public String getImagen() {
		return imagen;
	}
	/**
	 * @return the industria
	 */
	public String getIndustria() {
		return industria;
	}
	/**
	 * @return the limiteCredito
	 */
	public Double getLimiteCredito() {
		return limiteCredito;
	}
	/**
	 * @return the lineaCredito
	 */
	public Double getLineaCredito() {
		return lineaCredito;
	}
	/**
	 * @return the mapa
	 */
	public String getMapa() {
		return mapa;
	}
	/**
	 * @return the medioPago
	 */
	public String getMedioPago() {
		return medioPago;
	}
	/**
	 * @return the monedaFactura
	 */
	public String getMonedaFactura() {
		return monedaFactura;
	}
	/**
	 * @return the monto
	 */
	public Double getMonto() {
		return monto;
	}
	/**
	 * @return the morosidad
	 */
	public Double getMorosidad() {
		return morosidad;
	}
	/**
	 * @return the nivelIngreso
	 */
	public String getNivelIngreso() {
		return nivelIngreso;
	}
	/**
	 * @return the nivelIngresos
	 */
	public String getNivelIngresos() {
		return nivelIngresos;
	}
	/**
	 * @return the nivelPrecio
	 */
	public String getNivelPrecio() {
		return nivelPrecio;
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @return the notasC
	 */
	public List<NotaCredito> getNotasC() {
		return notasC;
	}
	/***
	 * 
	 * @return
	 */
	public Long getNumContactos() {
		return numContactos;
	}
	/**
	 * @return the numeroDeCuenta
	 */
	public String getNumeroDeCuenta() {
		return numeroDeCuenta;
	}
	/**
	 * @return the objectivoCrecimiento
	 */
	public String getObjectivoCrecimiento() {
		return objectivoCrecimiento;
	}
	/**
	 * @return the origenRegistro
	 */
	public String getOrigenRegistro() {
		return origenRegistro;
	}
	/**
	 * @return the pais
	 */
	public String getPais() {
		return pais;
	}
	/**
	 * @return the paisFiscal
	 */
	public String getPaisFiscal() {
		return paisFiscal;
	}
	/**
	 * @return the razonSocial
	 */
	public String getRazonSocial() {
		return razonSocial;
	}
	/**
	 * @return the referenciasBancarias
	 */
	public List<ReferenciasBancarias> getReferenciasBancarias() {
		return referenciasBancarias;
	}
	/**
	 * @return the rfc
	 */
	public String getRfc() {
		return rfc;
	}
	/**
	 * @return the rol
	 */
	public String getRol() {
		return rol;
	}
	/**
	 * @return the ruta
	 */
	public String getRuta() {
		return ruta;
	}
	/**
	 * @return the sector
	 */
	public String getSector() {
		return sector;
	}
	/**
	 * @return the taxID
	 */
	public String getTaxID() {
		return taxID;
	}
	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * @return the vendedor
	 */
	public String getVendedor() {
		return vendedor;
	}
	//	/**
//	 * @return the codigoDelPais
//	 */
//	public String getCodigoDelPais() {
//		return codigoDelPais;
//	}
//	/**
//	 * @param codigoDelPais the codigoDelPais to set
//	 */
//	public void setCodigoDelPais(String codigoDelPais) {
//		this.codigoDelPais = codigoDelPais;
//	}
	/**
	 * @return the zona
	 */
	public String getZona() {
		return zona;
	}
	/**
	 * @return the agregadoExpo
	 */
	public Boolean isAgregadoExpo() {
		return agregadoExpo;
	}
	/**
	 * @param aceptaParciales the aceptaParciales to set
	 */
	public void setAceptaParciales(Boolean aceptaParciales) {
		this.aceptaParciales = aceptaParciales;
	}
	/**
	 * @param agregadoExpo the agregadoExpo to set
	 */
	public void setAgregadoExpo(Boolean agregadoExpo) {
		this.agregadoExpo = agregadoExpo;
	}
	/**
	 * @param bytes the bytes to set
	 */
	public void setBytes(byte [] bytes) {
		this.bytes = bytes;
	}
	/**
	 * @param calle the calle to set
	 */
	public void setCalle(String calle) {
		this.calle = calle;
	}
	/**
	 * @param calleFiscal the calleFiscal to set
	 */
	public void setCalleFiscal(String calleFiscal) {
		this.calleFiscal = calleFiscal;
	}
	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * @param codigoPostal the codigoPostal to set
	 */
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	/**
	 * @param codigoPostalFiscal the codigoPostalFiscal to set
	 */
	public void setCodigoPostalFiscal(String codigoPostalFiscal) {
		this.codigoPostalFiscal = codigoPostalFiscal;
	}
	public void setComentaFacturacion(String comentaFacturacion) {
		this.comentaFacturacion = comentaFacturacion;
	}
	/**
	 * @param comentarios the comentarios to set
	 */
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
	/**
	 * @param comentariosCredito the comentariosCredito to set
	 */
	public void setComentariosCredito(String comentariosCredito) {
		this.comentariosCredito = comentariosCredito;
	}
	/**
	 * @param condicionesPago the condicionesPago to set
	 */
	public void setCondicionesPago(String condicionesPago) {
		this.condicionesPago = condicionesPago;
	}
	/**
	 * @param correoElectronico the correoElectronico to set
	 */
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
	/**
	 * @param delegacion the delegacion to set
	 */
	public void setDelegacion(String delegacion) {
		this.delegacion = delegacion;
	}
	/**
	 * @param delegacionFiscal the delegacionFiscal to set
	 */
	public void setDelegacionFiscal(String delegacionFiscal) {
		this.delegacionFiscal = delegacionFiscal;
	}
	/**
	 * @param ejecutivoVenta the ejecutivoVenta to set
	 */
	public void setEjecutivoVenta(String ejecutivoVenta) {
		this.ejecutivoVenta = ejecutivoVenta;
	}
	/**
	 * @param empresaFactura the empresaFactura to set
	 */
	public void setEmpresaFactura(String empresaFactura) {
		this.empresaFactura = empresaFactura;
	}
	/**
	 * @param entregaYRevision the entregaYRevision to set
	 */
	public void setEntregaYRevision(Boolean entregaYRevision) {
		this.entregaYRevision = entregaYRevision;
	}
	/**
	 * @param enviarEmail the enviarEmail to set
	 */
	public void setEnviarEmail(Boolean enviarEmail) {
		this.enviarEmail = enviarEmail;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	/**
	 * @param estadoFiscal the estadoFiscal to set
	 */
	public void setEstadoFiscal(String estadoFiscal) {
		this.estadoFiscal = estadoFiscal;
	}
	/**
	 * @param facturaPortal the facturaPortal to set
	 */
	public void setFacturaPortal(Boolean facturaPortal) {
		this.facturaPortal = facturaPortal;
	}
	/**
	 * @param facturasAdeudo the facturasAdeudo to set
	 */
	public void setFacturasAdeudo(List<Factura> facturasAdeudo) {
		this.facturasAdeudo = facturasAdeudo;
	}
	/**
	 * @param fechaActualizacion the fechaActualizacion to set
	 */
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	/**
	 * @param fechaRegistro the fechaRegistro to set
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	/**
	 * @param folio the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}
	/**
	 * @param habilitado the habilitado to set
	 */
	public void setHabilitado(Boolean habilitado) {
		this.habilitado = habilitado;
	}
	/**
	 * @param idCliente the idCliente to set
	 */
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}
	/**
	 * @param idEjecutivoVenta the idEjecutivoVenta to set
	 */
	public void setIdEjecutivoVenta(Integer idEjecutivoVenta) {
		this.idEjecutivoVenta = idEjecutivoVenta;
	}
	/**
	 * @param imagen the imagen to set
	 */
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	/**
	 * @param industria the industria to set
	 */
	public void setIndustria(String industria) {
		this.industria = industria;
	}
	/**
	 * @param limiteCredito the limiteCredito to set
	 */
	public void setLimiteCredito(Double limiteCredito) {
		this.limiteCredito = limiteCredito;
	}
	/**
	 * @param lineaCredito the lineaCredito to set
	 */
	public void setLineaCredito(Double lineaCredito) {
		this.lineaCredito = lineaCredito;
	}
	/**
	 * @param mapa the mapa to set
	 */
	public void setMapa(String mapa) {
		this.mapa = mapa;
	}
	/**
	 * @param medioPago the medioPago to set
	 */
	public void setMedioPago(String medioPago) {
		this.medioPago = medioPago;
	}
	/**
	 * @param monedaFactura the monedaFactura to set
	 */
	public void setMonedaFactura(String monedaFactura) {
		this.monedaFactura = monedaFactura;
	}
	/**
	 * @param monto the monto to set
	 */
	public void setMonto(Double monto) {
		this.monto = monto;
	}
	/**
	 * @param morosidad the morosidad to set
	 */
	public void setMorosidad(Double morosidad) {
		this.morosidad = morosidad;
	}
	/**
	 * @param nivelIngreso the nivelIngreso to set
	 */
	public void setNivelIngreso(String nivelIngreso) {
		this.nivelIngreso = nivelIngreso;
	}
	/**
	 * @param nivelIngresos the nivelIngresos to set
	 */
	public void setNivelIngresos(String nivelIngresos) {
		this.nivelIngresos = nivelIngresos;
	}
	/**
	 * @param nivelPrecio the nivelPrecio to set
	 */
	public void setNivelPrecio(String nivelPrecio) {
		this.nivelPrecio = nivelPrecio;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @param notasC the notasC to set
	 */
	public void setNotasC(List<NotaCredito> notasC) {
		this.notasC = notasC;
	}
	/***
	 * 
	 * @param num
	 */
	public void setNumContactos(Long num) {
		this.numContactos = num;
	}
	/**
	 * @param numeroDeCuenta the numeroDeCuenta to set
	 */
	public void setNumeroDeCuenta(String numeroDeCuenta) {
		this.numeroDeCuenta = numeroDeCuenta;
	}
	/**
	 * @param objectivoCrecimiento the objectivoCrecimiento to set
	 */
	public void setObjectivoCrecimiento(String objectivoCrecimiento) {
		this.objectivoCrecimiento = objectivoCrecimiento;
	}
	/**
	 * @param origenRegistro the origenRegistro to set
	 */
	public void setOrigenRegistro(String origenRegistro) {
		this.origenRegistro = origenRegistro;
	}
	/**
	 * @param pais the pais to set
	 */
	public void setPais(String pais) {
		this.pais = pais;
	}
	/**
	 * @param paisFiscal the paisFiscal to set
	 */
	public void setPaisFiscal(String paisFiscal) {
		this.paisFiscal = paisFiscal;
	}
	/**
	 * @param razonSocial the razonSocial to set
	 */
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	/**
	 * @param referenciasBancarias the referenciasBancarias to set
	 */
	public void setReferenciasBancarias(
			List<ReferenciasBancarias> referenciasBancarias) {
		this.referenciasBancarias = referenciasBancarias;
	}
	/**
	 * @param rfc the rfc to set
	 */
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	/**
	 * @param rol the rol to set
	 */
	public void setRol(String rol) {
		this.rol = rol;
	}
	/**
	 * @param ruta the ruta to set
	 */
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	/**
	 * @param sector the sector to set
	 */
	public void setSector(String sector) {
		this.sector = sector;
	}
	/**
	 * @param taxID the taxID to set
	 */
	public void setTaxID(String taxID) {
		this.taxID = taxID;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	/**
	 * @param vendedor the vendedor to set
	 */
	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}
	/**
	 * @param zona the zona to set
	 */
	public void setZona(String zona) {
		this.zona = zona;
	}
	/**
	 * @return the idCobrador
	 */
	public Long getIdCobrador() {
		return idCobrador;
	}
	/**
	 * @param idCobrador the idCobrador to set
	 */
	public void setIdCobrador(Long idCobrador) {
		this.idCobrador = idCobrador;
	}
	/**
	 * @return the importancia
	 */
	public Integer getImportancia() {
		return importancia;
	}
	/**
	 * @param importancia the importancia to set
	 */
	public void setImportancia(Integer importancia) {
		this.importancia = importancia;
	}
	/**
	 * @return the dificultad
	 */
	public Integer getDificultad() {
		return dificultad;
	}
	/**
	 * @param dificultad the dificultad to set
	 */
	public void setDificultad(Integer dificultad) {
		this.dificultad = dificultad;
	}
	/**
	 * @return the mantenimiento
	 */
	public Integer getMantenimiento() {
		return mantenimiento;
	}
	/**
	 * @param mantenimiento the mantenimiento to set
	 */
	public void setMantenimiento(Integer mantenimiento) {
		this.mantenimiento = mantenimiento;
	}

	/**
	 * @return the corporativo
	 */
	public Long getCorporativo() {
		return corporativo;
	}
	/**
	 * @param corporativo the corporativo to set
	 */
	public void setCorporativo(Long corporativo) {
		this.corporativo = corporativo;
	}

	/**
	 * @return the cobrador
	 */
	public String getCobrador() {
		return cobrador;
	}

	/**
	 * @param cobrador the cobrador to set
	 */
	public void setCobrador(String cobrador) {
		this.cobrador = cobrador;
	}
	
	/**
	 * @return the idCorporativo
	 */
	public Long getIdCorporativo() {
		return idCorporativo;
	}

	/**
	 * @param idCorporativo the idCorporativo to set
	 */
	public void setIdCorporativo(Long idCorporativo) {
		this.idCorporativo = idCorporativo;
	}

	/**
	 * @return the nombreCorporativo
	 */
	public String getNombreCorporativo() {
		return nombreCorporativo;
	}

	/**
	 * @param nombreCorporativo the nombreCorporativo to set
	 */
	public void setNombreCorporativo(String nombreCorporativo) {
		this.nombreCorporativo = nombreCorporativo;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public String getObjetivoCrecimientoFundamental() {
		return objetivoCrecimientoFundamental;
	}

	public void setObjetivoCrecimientoFundamental(
			String objetivoCrecimientoFundamental) {
		this.objetivoCrecimientoFundamental = objetivoCrecimientoFundamental;
	}

	/**
	 * @return the nombreEjecutivoVenta
	 */
	public String getNombreEjecutivoVenta() {
		return nombreEjecutivoVenta;
	}

	/**
	 * @param nombreEjecutivoVenta the nombreEjecutivoVenta to set
	 */
	public void setNombreEjecutivoVenta(String nombreEjecutivoVenta) {
		this.nombreEjecutivoVenta = nombreEjecutivoVenta;
	}

	/**
	 * @return the nombreESAC
	 */
	public String getNombreESAC() {
		return nombreESAC;
	}

	/**
	 * @param nombreESAC the nombreESAC to set
	 */
	public void setNombreESAC(String nombreESAC) {
		this.nombreESAC = nombreESAC;
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
	 * @return the direccionFacturacion
	 */
	public Direccion getDireccionFacturacion() {
		return direccionFacturacion;
	}

	/**
	 * @param direccionFacturacion the direccionFacturacion to set
	 */
	public void setDireccionFacturacion(Direccion direccionFacturacion) {
		this.direccionFacturacion = direccionFacturacion;
	}

	/**
	 * @return the idcartera
	 */
	public Long getIdcartera() {
		return idcartera;
	}

	/**
	 * @param idcartera the idcartera to set
	 */
	public void setIdcartera(Long idcartera) {
		this.idcartera = idcartera;
	}

	public String getColonia() {
		return colonia;
	}

	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	public int getTotalContactos() {
		return totalContactos;
	}

	public void setTotalContactos(int totalContactos) {
		this.totalContactos = totalContactos;
	}

	public boolean isTieneCartera() {
		return tieneCartera;
	}

	public void setTieneCartera(boolean tieneCartera) {
		this.tieneCartera = tieneCartera;
	}

	public String getNombreCarteras() {
		return nombreCarteras;
	}

	public void setNombreCarteras(String nombreCarteras) {
		this.nombreCarteras = nombreCarteras;
	}

	public Long getNumeroDeCarteras() {
		return numeroDeCarteras;
	}

	public void setNumeroDeCarteras(Long numeroDeCarteras) {
		this.numeroDeCarteras = numeroDeCarteras;
	}

	/**
	 * @return the parciales
	 */
	public String getParciales() {
		return parciales;
	}

	/**
	 * @param parciales the parciales to set
	 */
	public void setParciales(String parciales) {
		this.parciales = parciales;
	}

	public Direccion getDireccionVisita() {
		return direccionVisita;
	}

	public void setDireccionVisita(Direccion direccionVisita) {
		this.direccionVisita = direccionVisita;
	}

	public String getNomCobrador() {
		return nomCobrador;
	}

	public void setNomCobrador(String nomCobrador) {
		this.nomCobrador = nomCobrador;
	}

	public Long getIdEsacCli() {
		return idEsacCli;
	}

	public void setIdEsacCli(Long idEsacCli) {
		this.idEsacCli = idEsacCli;
	}

	public Long getIdEVCli() {
		return idEVCli;
	}

	public void setIdEVCli(Long idEVCli) {
		this.idEVCli = idEVCli;
	}

	public Long getNum_visitas_ano() {
		return num_visitas_ano;
	}

	public void setNum_visitas_ano(Long num_visitas_ano) {
		this.num_visitas_ano = num_visitas_ano;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public boolean isTieneContrato() {
		return tieneContrato;
	}

	public void setTieneContrato(boolean tieneContrato) {
		this.tieneContrato = tieneContrato;
	}

	public Long getNumeroDeContratos() {
		return numeroDeContratos;
	}

	public void setNumeroDeContratos(Long numeroDeContratos) {
		this.numeroDeContratos = numeroDeContratos;
	}

	/**
	 * @return the usoCFDI
	 */
	public String getUsoCFDI() {
		return usoCFDI;
	}

	/**
	 * @param usoCFDI the usoCFDI to set
	 */
	public void setUsoCFDI(String usoCFDI) {
		this.usoCFDI = usoCFDI;
	}

	/**
	 * @return the metodoDePago
	 */
	public String getMetodoDePago() {
		return metodoDePago;
	}

	/**
	 * @param metodoDePago the metodoDePago to set
	 */
	public void setMetodoDePago(String metodoDePago) {
		this.metodoDePago = metodoDePago;
	}

	
}
