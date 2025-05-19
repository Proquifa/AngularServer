package com.proquifa.net.modelo.comun;

import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.catalogos.proveedores.TiempoEntrega;



public class Producto {	
	private Long idProducto;
	private Long proveedor;
	private String tipo;
	private String subtipo;
	private String codigo;
	private String fabrica;
	private String concepto;
	private String descripcion;	
	private String cantidad;
	private Integer cant;	
	private String unidad;	
	private String control;
	private String manejo;
	private String moneda;
	private String tipoDocumento;
	private String documentacion;
	private String disponibilidad;
	private Boolean vigente;
	private String tiempoEntrega;
	private Long pureza;
	private Date caduca;
	private Date fecha;
	private String idioma;
	private String edicion;
	private String estado;
	private Double totalVentas;
	private Double costo;
	private Double costoMinimo;
	private Double precioFijo;
	private Long descuento;
	private Double iva;
	private String total;
	private Long configuracion_Precio;
	private Long categoriaPrecioLista;
	private String linea;
	private String clasificacion;
	private String industria;
	private String licencia;
	private String cas;
	private String depositarioInter;
	private String estadoFisico;
	private String fraccionArancelaria;
	private Double DTA;
	private Double IGI;
	private String requierePermisoImp;
	private String tipoPermiso;
	private String permisoImp;
	private String aplicacion;
	private String manejoTransporte;
	private String lote;
	private String loteAnterior;
	private String clasificacionProducto;
	private String subgrupo;
	private String datosToxicologicos;
	private String estadoPermiso;
	private String fichaTecnica;
	private Long clasificacion_ConfiguracionPrecio;
	private String sds;
	private String familiaString;
	
	private String idFabricante;
	private Long idMarca;
	
	private String origen;
	
	private double documentacionVersion;
	private double sdsVersion;
	
	private String tipoPresentacion;
	
	private String caracteristicasFisicas;
	private String composicion;
	private String formulaQuimica;
	private String peligrosidad;
	private boolean certificadoNoDisponible;
	private boolean transitoMandatorioMexico;
	
	private Integer idInventario;
	private String codigoInterno;
	
	private String ConceptoCo;
	
	private List<TiempoEntrega> tiempoEntregaRuta;
	private String rutaCli;
	private double costoMin;
	
	private Long numStock;
	private double precioMX;
	private double precioEuro;
	private double precioDolar;
	
	private double costoMinimoMX;
	private double costoMinimoEuro;
	
	private double pdolar;
	private double edolar;
	
	
//	variable para pdf contrato
	private String costoString;
	private Long numFila;
	
	private Long cantPiezas;
	private Double costoAux;
	private  String claveUnidad;
	private String valorUnitario;
	private String importe;
	private String Pedimento;

	public String getClaveUnidad() {
		return this.claveUnidad;
	}

	public void setClaveUnidad(String claveUnidad) {
		this.claveUnidad = claveUnidad;
	}

	public String getValorUnitario() {
		return this.valorUnitario;
	}

	public void setValorUnitario(String valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public String getImporte() {
		return this.importe;
	}

	public void setImporte(String importe) {
		this.importe = importe;
	}

	public String getPedimento() {
		return this.Pedimento;
	}

	public void setPedimento(String pedimento) {
		Pedimento = pedimento;
	}

	/**
	 * @return the idProducto
	 */
	public Long getIdProducto() {
		return idProducto;
	}
	/**
	 * @param idProducto the idProducto to set
	 */
	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}
	/**
	 * @return the proveedor
	 */
	public Long getProveedor() {
		return proveedor;
	}
	/**
	 * @param proveedor the proveedor to set
	 */
	public void setProveedor(Long proveedor) {
		this.proveedor = proveedor;
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
	 * @return the subtipo
	 */
	public String getSubtipo() {
		return subtipo;
	}
	/**
	 * @param subtipo the subtipo to set
	 */
	public void setSubtipo(String subtipo) {
		this.subtipo = subtipo;
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
	 * @return the concepto
	 */
	public String getConcepto() {
		return concepto;
	}
	/**
	 * @param concepto the concepto to set
	 */
	public void setConcepto(String concepto) {
		this.concepto = concepto;
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
	 * @return the cantidad
	 */
	public String getCantidad() {
		return cantidad;
	}
	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}
	/**
	 * @return the cant
	 */
	public Integer getCant() {
		return cant;
	}
	/**
	 * @param cant the cant to set
	 */
	public void setCant(Integer cant) {
		this.cant = cant;
	}
	/**
	 * @return the unidad
	 */
	public String getUnidad() {
		return unidad;
	}
	/**
	 * @param unidad the unidad to set
	 */
	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}
	/**
	 * @return the control
	 */
	public String getControl() {
		return control;
	}
	/**
	 * @param control the control to set
	 */
	public void setControl(String control) {
		this.control = control;
	}
	/**
	 * @return the manejo
	 */
	public String getManejo() {
		return manejo;
	}
	/**
	 * @param manejo the manejo to set
	 */
	public void setManejo(String manejo) {
		this.manejo = manejo;
	}
	/**
	 * @return the moneda
	 */
	public String getMoneda() {
		return moneda;
	}
	/**
	 * @param moneda the moneda to set
	 */
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
	/**
	 * @return the tipoDocumento
	 */
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	/**
	 * @param tipoDocumento the tipoDocumento to set
	 */
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	/**
	 * @return the documentacion
	 */
	public String getDocumentacion() {
		return documentacion;
	}
	/**
	 * @param documentacion the documentacion to set
	 */
	public void setDocumentacion(String documentacion) {
		this.documentacion = documentacion;
	}
	/**
	 * @return the disponibilidad
	 */
	public String getDisponibilidad() {
		return disponibilidad;
	}
	/**
	 * @param disponibilidad the disponibilidad to set
	 */
	public void setDisponibilidad(String disponibilidad) {
		this.disponibilidad = disponibilidad;
	}
	/**
	 * @return the vigente
	 */
	public Boolean getVigente() {
		return vigente;
	}
	/**
	 * @param vigente the vigente to set
	 */
	public void setVigente(Boolean vigente) {
		this.vigente = vigente;
	}
	/**
	 * @return the tiempoEntrega
	 */
	public String getTiempoEntrega() {
		return tiempoEntrega;
	}
	/**
	 * @param tiempoEntrega the tiempoEntrega to set
	 */
	public void setTiempoEntrega(String tiempoEntrega) {
		this.tiempoEntrega = tiempoEntrega;
	}
	/**
	 * @return the pureza
	 */
	public Long getPureza() {
		return pureza;
	}
	/**
	 * @param pureza the pureza to set
	 */
	public void setPureza(Long pureza) {
		this.pureza = pureza;
	}
	/**
	 * @return the caduca
	 */
	public Date getCaduca() {
		return caduca;
	}
	/**
	 * @param caduca the caduca to set
	 */
	public void setCaduca(Date caduca) {
		this.caduca = caduca;
	}
	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}
	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	/**
	 * @return the idioma
	 */
	public String getIdioma() {
		return idioma;
	}
	/**
	 * @param idioma the idioma to set
	 */
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	/**
	 * @return the edicion
	 */
	public String getEdicion() {
		return edicion;
	}
	/**
	 * @param edicion the edicion to set
	 */
	public void setEdicion(String edicion) {
		this.edicion = edicion;
	}
	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	/**
	 * @return the totalVentas
	 */
	public Double getTotalVentas() {
		return totalVentas;
	}
	/**
	 * @param totalVentas the totalVentas to set
	 */
	public void setTotalVentas(Double totalVentas) {
		this.totalVentas = totalVentas;
	}
	/**
	 * @return the costo
	 */
	public Double getCosto() {
		return costo;
	}
	/**
	 * @param costo the costo to set
	 */
	public void setCosto(Double costo) {
		this.costo = costo;
	}
	/**
	 * @return the costoMinimo
	 */
	public Double getCostoMinimo() {
		return costoMinimo;
	}
	/**
	 * @param costoMinimo the costoMinimo to set
	 */
	public void setCostoMinimo(Double costoMinimo) {
		this.costoMinimo = costoMinimo;
	}
	/**
	 * @return the precioFijo
	 */
	public Double getPrecioFijo() {
		return precioFijo;
	}
	/**
	 * @param precioFijo the precioFijo to set
	 */
	public void setPrecioFijo(Double precioFijo) {
		this.precioFijo = precioFijo;
	}
	/**
	 * @return the descuento
	 */
	public Long getDescuento() {
		return descuento;
	}
	/**
	 * @param descuento the descuento to set
	 */
	public void setDescuento(Long descuento) {
		this.descuento = descuento;
	}
	/**
	 * @return the iva
	 */
	public Double getIva() {
		return iva;
	}
	/**
	 * @param iva the iva to set
	 */
	public void setIva(Double iva) {
		this.iva = iva;
	}
	/**
	 * @return the total
	 */
	public String getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(String total) {
		this.total = total;
	}
	/**
	 * @return the configuracion_Precio
	 */
	public Long getConfiguracion_Precio() {
		return configuracion_Precio;
	}
	/**
	 * @param configuracion_Precio the configuracion_Precio to set
	 */
	public void setConfiguracion_Precio(Long configuracion_Precio) {
		this.configuracion_Precio = configuracion_Precio;
	}
	/**
	 * @return the categoriaPrecioLista
	 */
	public Long getCategoriaPrecioLista() {
		return categoriaPrecioLista;
	}
	/**
	 * @param categoriaPrecioLista the categoriaPrecioLista to set
	 */
	public void setCategoriaPrecioLista(Long categoriaPrecioLista) {
		this.categoriaPrecioLista = categoriaPrecioLista;
	}
	/**
	 * @return the linea
	 */
	public String getLinea() {
		return linea;
	}
	/**
	 * @param linea the linea to set
	 */
	public void setLinea(String linea) {
		this.linea = linea;
	}
	/**
	 * @return the clasificacion
	 */
	public String getClasificacion() {
		return clasificacion;
	}
	/**
	 * @param clasificacion the clasificacion to set
	 */
	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}
	/**
	 * @return the industria
	 */
	public String getIndustria() {
		return industria;
	}
	/**
	 * @param industria the industria to set
	 */
	public void setIndustria(String industria) {
		this.industria = industria;
	}
	/**
	 * @return the licencia
	 */
	public String getLicencia() {
		return licencia;
	}
	/**
	 * @param licencia the licencia to set
	 */
	public void setLicencia(String licencia) {
		this.licencia = licencia;
	}
	/**
	 * @return the cas
	 */
	public String getCas() {
		return cas;
	}
	/**
	 * @param cas the cas to set
	 */
	public void setCas(String cas) {
		this.cas = cas;
	}
	/**
	 * @return the depositarioInter
	 */
	public String getDepositarioInter() {
		return depositarioInter;
	}
	/**
	 * @param depositarioInter the depositarioInter to set
	 */
	public void setDepositarioInter(String depositarioInter) {
		this.depositarioInter = depositarioInter;
	}
	/**
	 * @return the estadoFisico
	 */
	public String getEstadoFisico() {
		return estadoFisico;
	}
	/**
	 * @param estadoFisico the estadoFisico to set
	 */
	public void setEstadoFisico(String estadoFisico) {
		this.estadoFisico = estadoFisico;
	}
	/**
	 * @return the fraccionArancelaria
	 */
	public String getFraccionArancelaria() {
		return fraccionArancelaria;
	}
	/**
	 * @param fraccionArancelaria the fraccionArancelaria to set
	 */
	public void setFraccionArancelaria(String fraccionArancelaria) {
		this.fraccionArancelaria = fraccionArancelaria;
	}
	/**
	 * @return the dTA
	 */
	public Double getDTA() {
		return DTA;
	}
	/**
	 * @param dTA the dTA to set
	 */
	public void setDTA(Double dTA) {
		DTA = dTA;
	}
	/**
	 * @return the iGI
	 */
	public Double getIGI() {
		return IGI;
	}
	/**
	 * @param iGI the iGI to set
	 */
	public void setIGI(Double iGI) {
		IGI = iGI;
	}
	/**
	 * @return the requierePermisoImp
	 */
	public String getRequierePermisoImp() {
		return requierePermisoImp;
	}
	/**
	 * @param requierePermisoImp the requierePermisoImp to set
	 */
	public void setRequierePermisoImp(String requierePermisoImp) {
		this.requierePermisoImp = requierePermisoImp;
	}
	/**
	 * @return the tipoPermiso
	 */
	public String getTipoPermiso() {
		return tipoPermiso;
	}
	/**
	 * @param tipoPermiso the tipoPermiso to set
	 */
	public void setTipoPermiso(String tipoPermiso) {
		this.tipoPermiso = tipoPermiso;
	}
	
	public String getPermisoImp() {
		return permisoImp;
	}
	
	public void setPermisoImp(String permisoImp) {
		this.permisoImp = permisoImp;
	}
	
	public String getAplicacion() {
		return aplicacion;
	}
	
	public void setAplicacion(String aplicacion) {
		this.aplicacion = aplicacion;
	}
	
	public String getManejoTransporte() {
		return manejoTransporte;
	}
	
	public void setManejoTransporte(String manejoTransporte) {
		this.manejoTransporte = manejoTransporte;
	}
	public String getLote() {
		return lote;
	}
	public void setLote(String lote) {
		this.lote = lote;
	}
	public String getClasificacionProducto() {
		return clasificacionProducto;
	}
	public void setClasificacionProducto(String clasificacionProducto) {
		this.clasificacionProducto = clasificacionProducto;
	}
	public double getDocumentacionVersion() {
		return documentacionVersion;
	}
	public void setDocumentacionVersion(double documentacionVersion) {
		this.documentacionVersion = documentacionVersion;
	}
	public double getSdsVersion() {
		return sdsVersion;
	}
	public void setSdsVersion(double sdsVersion) {
		this.sdsVersion = sdsVersion;
	}
	public String getLoteAnterior() {
		return loteAnterior;
	}
	public void setLoteAnterior(String loteAnterior) {
		this.loteAnterior = loteAnterior;
	}
	public String getTipoPresentacion() {
		return tipoPresentacion;
	}
	public void setTipoPresentacion(String tipoPresentacion) {
		this.tipoPresentacion = tipoPresentacion;
	}
	public String getOrigen() {
		return origen;
	}
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	public String getCaracteristicasFisicas() {
		return caracteristicasFisicas;
	}
	public void setCaracteristicasFisicas(String caracteristicasFisicas) {
		this.caracteristicasFisicas = caracteristicasFisicas;
	}
	public String getComposicion() {
		return composicion;
	}
	public void setComposicion(String composicion) {
		this.composicion = composicion;
	}
	public String getFormulaQuimica() {
		return formulaQuimica;
	}
	public void setFormulaQuimica(String formulaQuimica) {
		this.formulaQuimica = formulaQuimica;
	}
	public String getPeligrosidad() {
		return peligrosidad;
	}
	public void setPeligrosidad(String peligrosidad) {
		this.peligrosidad = peligrosidad;
	}
	public boolean isCertificadoNoDisponible() {
		return certificadoNoDisponible;
	}
	public void setCertificadoNoDisponible(boolean certificadoNoDisponible) {
		this.certificadoNoDisponible = certificadoNoDisponible;
	}
	
	/**
	 * @return the idFabricante
	 */
	public String getIdFabricante() {
	return idFabricante;
	}
	/**
	 * @param idFabricante the idFabricante to set
	 */
	public void setIdFabricante(String idFabricante) {
	this.idFabricante = idFabricante;
	}
	public boolean isTransitoMandatorioMexico() {
		return transitoMandatorioMexico;
	}
	public void setTransitoMandatorioMexico(boolean transitoMandatorioMexico) {
		this.transitoMandatorioMexico = transitoMandatorioMexico;
	}
	public String getSubgrupo() {
		return subgrupo;
	}
	public void setSubgrupo(String subgrupo) {
		this.subgrupo = subgrupo;
	}
	public String getDatosToxicologicos() {
		return datosToxicologicos;
	}
	public void setDatosToxicologicos(String datosToxicologicos) {
		this.datosToxicologicos = datosToxicologicos;
	}
	public String getEstadoPermiso() {
		return estadoPermiso;
	}
	public void setEstadoPermiso(String estadoPermiso) {
		this.estadoPermiso = estadoPermiso;
	}
	public String getFichaTecnica() {
		return fichaTecnica;
	}
	public void setFichaTecnica(String fichaTecnica) {
		this.fichaTecnica = fichaTecnica;
	}
	public Long getClasificacion_ConfiguracionPrecio() {
		return clasificacion_ConfiguracionPrecio;
	}
	public void setClasificacion_ConfiguracionPrecio(
			Long clasificacion_ConfiguracionPrecio) {
		this.clasificacion_ConfiguracionPrecio = clasificacion_ConfiguracionPrecio;
	}
	public String getSds() {
		return sds;
	}
	public void setSds(String sds) {
		this.sds = sds;
	}
	/**
	 * @return the idInventario
	 */
	public Integer getIdInventario() {
		return idInventario;
	}
	/**
	 * @param idInventario the idInventario to set
	 */
	public void setIdInventario(Integer idInventario) {
		this.idInventario = idInventario;
	}
	/**
	 * @return the codigoInterno
	 */
	public String getCodigoInterno() {
		return codigoInterno;
	}
	/**
	 * @param codigoInterno the codigoInterno to set
	 */
	public void setCodigoInterno(String codigoInterno) {
		this.codigoInterno = codigoInterno;
	}
	public String getCostoString() {
		return costoString;
	}
	public void setCostoString(String costoString) {
		this.costoString = costoString;
	}
	public Long getNumFila() {
		return numFila;
	}
	public void setNumFila(Long numFila) {
		this.numFila = numFila;
	}
	public String getConceptoCo() {
		return ConceptoCo;
	}
	public void setConceptoCo(String conceptoCo) {
		ConceptoCo = conceptoCo;
	}
	public String getFamiliaString() {
		return familiaString;
	}
	public void setFamiliaString(String familiaString) {
		this.familiaString = familiaString;
	}
	public List<TiempoEntrega> getTiempoEntregaRuta() {
		return tiempoEntregaRuta;
	}
	public void setTiempoEntregaRuta(List<TiempoEntrega> tiempoEntregaRuta) {
		this.tiempoEntregaRuta = tiempoEntregaRuta;
	}
	public String getRutaCli() {
		return rutaCli;
	}
	public void setRutaCli(String rutaCli) {
		this.rutaCli = rutaCli;
	}
	public double getCostoMin() {
		return costoMin;
	}
	public void setCostoMin(double costoMin) {
		this.costoMin = costoMin;
	}
	public Long getNumStock() {
		return numStock;
	}
	public void setNumStock(Long numStock) {
		this.numStock = numStock;
	}
	public double getPrecioMX() {
		return precioMX;
	}
	public void setPrecioMX(double precioMX) {
		this.precioMX = precioMX;
	}
	public double getPrecioEuro() {
		return precioEuro;
	}
	public void setPrecioEuro(double precioEuro) {
		this.precioEuro = precioEuro;
	}
	public double getPrecioDolar() {
		return precioDolar;
	}
	public void setPrecioDolar(double precioDolar) {
		this.precioDolar = precioDolar;
	}
	public double getCostoMinimoMX() {
		return costoMinimoMX;
	}
	public void setCostoMinimoMX(double costoMinimoMX) {
		this.costoMinimoMX = costoMinimoMX;
	}
	public double getCostoMinimoEuro() {
		return costoMinimoEuro;
	}
	public void setCostoMinimoEuro(double costoMinimoEuro) {
		this.costoMinimoEuro = costoMinimoEuro;
	}
	public double getPdolar() {
		return pdolar;
	}
	public void setPdolar(double pdolar) {
		this.pdolar = pdolar;
	}
	public double getEdolar() {
		return edolar;
	}
	public void setEdolar(double edolar) {
		this.edolar = edolar;
	}
	public Long getCantPiezas() {
		return cantPiezas;
	}
	public void setCantPiezas(Long cantPiezas) {
		this.cantPiezas = cantPiezas;
	}
	public Double getCostoAux() {
		return costoAux;
	}
	public void setCostoAux(Double costoAux) {
		this.costoAux = costoAux;
	}
	public Long getIdMarca() {
		return idMarca;
	}
	public void setIdMarca(Long idMarca) {
		this.idMarca = idMarca;
	}


}
