/**
 * 
 */
package com.proquifa.net.modelo.catalogos.cobrosypagos.facturista;

import java.io.Serializable;
import java.util.List;
/**
 * @autor ssanchez
 */
public class ComplementoPago implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = -2688237016028699785L;
private String clave;
private Integer cantidad;
private String version;
private String claveUnidad;
private String descripcion;
private String valorUnitario;
private String importe;
private String fechaP;
private String formaP;
private String moneda;
private Float monto;
private List<ComplementoPago> listaPagos;
private String idDocumento;
private String monedaDR;
private String serie;
private String numParcialidad;
private String metodoP;
private String tipoCambio;
private String folio;
private String importeA;
private String importeIns;
private String nomBanco;
private String rfcCuenta;
private String numCuenta;
private String rfcEmisor;
private String MontoDoc;
private String tipoCambioDR;
private String impBase;
private String unidad;
private String impuesto;
private String tipoFactor;
private String tasa;
private String impTranslado;
private String identificador;
private String comentario;
private String numOperacion;
private String baseIVA16;
private String impIVA16;
private String baseDR;
private String impDR;
private String tipoFactorDR;
private String importeDR;
private String tasaCuotaDR;
private String baseP;
private String importeP;
private String impuestoP;
private String tipoFacP;
private String tasaCuotaP;

	public String getBaseP() {
		return baseP;
	}

	public void setBaseP(String baseP) {
		this.baseP = baseP;
	}

	public String getImporteP() {
		return importeP;
	}

	public void setImporteP(String importeP) {
		this.importeP = importeP;
	}

	public String getImpuestoP() {
		return impuestoP;
	}

	public void setImpuestoP(String impuestoP) {
		this.impuestoP = impuestoP;
	}

	public String getTipoFacP() {
		return tipoFacP;
	}

	public void setTipoFacP(String tipoFacP) {
		this.tipoFacP = tipoFacP;
	}

	public String getTasaCuotaP() {
		return tasaCuotaP;
	}

	public void setTasaCuotaP(String tasaCuotaP) {
		this.tasaCuotaP = tasaCuotaP;
	}

	public String getBaseDR() {
		return baseDR;
	}

	public void setBaseDR(String baseDR) {
		this.baseDR = baseDR;
	}

	public String getImpDR() {
		return impDR;
	}

	public void setImpDR(String impDR) {
		this.impDR = impDR;
	}

	public String getTipoFactorDR() {
		return tipoFactorDR;
	}

	public void setTipoFactorDR(String tipoFactorDR) {
		this.tipoFactorDR = tipoFactorDR;
	}

	public String getImporteDR() {
		return importeDR;
	}

	public void setImporteDR(String importeDR) {
		this.importeDR = importeDR;
	}

	public String getTasaCuotaDR() {
		return tasaCuotaDR;
	}

	public void setTasaCuotaDR(String tasaCuotaDR) {
		this.tasaCuotaDR = tasaCuotaDR;
	}

	public String getBaseIVA16() {
		return baseIVA16;
	}
	public void setBaseIVA16(String baseIVA16) {
		this.baseIVA16 = baseIVA16;
	}
	public String getImpIVA16() {
		return impIVA16;
	}
	public void setImpIVA16(String impIVA16) {
		this.impIVA16 = impIVA16;
	}

	public String getClave() {
	return clave;
}
public void setClave(String clave) {
	this.clave = clave;
}

public String getVersion() {
	return version;
}
public void setVersion(String version) {
	this.version = version;
}
public String getClaveUnidad() {
	return claveUnidad;
}
public void setClaveUnidad(String claveUnidad) {
	this.claveUnidad = claveUnidad;
}
public String getDescripcion() {
	return descripcion;
}
public void setDescripcion(String descripcion) {
	this.descripcion = descripcion;
}

public Integer getCantidad() {
	return cantidad;
}
public void setCantidad(Integer cantidad) {
	this.cantidad = cantidad;
}
public String getFechaP() {
	return fechaP;
}
public void setFechaP(String fechaP) {
	this.fechaP = fechaP;
}
public String getFormaP() {
	return formaP;
}
public void setFormaP(String formaP) {
	this.formaP = formaP;
}
public String getMoneda() {
	return moneda;
}
public void setMoneda(String moneda) {
	this.moneda = moneda;
}
public Float getMonto() {
	return monto;
}
public void setMonto(Float monto) {
	this.monto = monto;
}
public List<ComplementoPago> getListaPagos() {
	return listaPagos;
}
public void setListaPagos(List<ComplementoPago> listaPagos) {
	this.listaPagos = listaPagos;
}
public String getIdDocumento() {
	return idDocumento;
}
public void setIdDocumento(String idDocumento) {
	this.idDocumento = idDocumento;
}
public String getMonedaDR() {
	return monedaDR;
}
public void setMonedaDR(String monedaDR) {
	this.monedaDR = monedaDR;
}
public String getSerie() {
	return serie;
}
public void setSerie(String serie) {
	this.serie = serie;
}
public String getNumParcialidad() {
	return numParcialidad;
}
public void setNumParcialidad(String numParcialidad) {
	this.numParcialidad = numParcialidad;
}
public String getMetodoP() {
	return metodoP;
}
public void setMetodoP(String metodoP) {
	this.metodoP = metodoP;
}
public String getFolio() {
	return folio;
}
public void setFolio(String folio) {
	this.folio = folio;
}
public String getImporteA() {
	return importeA;
}
public void setImporteA(String importeA) {
	this.importeA = importeA;
}
public String getImporteIns() {
	return importeIns;
}
public void setImporteIns(String importeIns) {
	this.importeIns = importeIns;
}
public String getNomBanco() {
	return nomBanco;
}
public void setNomBanco(String nomBanco) {
	this.nomBanco = nomBanco;
}
public String getRfcCuenta() {
	return rfcCuenta;
}
public void setRfcCuenta(String rfcCuenta) {
	this.rfcCuenta = rfcCuenta;
}
public String getNumCuenta() {
	return numCuenta;
}
public void setNumCuenta(String numCuenta) {
	this.numCuenta = numCuenta;
}
public String getRfcEmisor() {
	return rfcEmisor;
}
public void setRfcEmisor(String rfcEmisor) {
	this.rfcEmisor = rfcEmisor;
}
public String getMontoDoc() {
	return MontoDoc;
}
public void setMontoDoc(String montoDoc) {
	MontoDoc = montoDoc;
}
public String getImporte() {
	return importe;
}
public void setImporte(String importe) {
	this.importe = importe;
}
public String getValorUnitario() {
	return valorUnitario;
}
public void setValorUnitario(String valorUnitario) {
	this.valorUnitario = valorUnitario;
}
public String getTipoCambio() {
	return tipoCambio;
}
public void setTipoCambio(String tipoCambio) {
	this.tipoCambio = tipoCambio;
}
public String getTipoCambioDR() {
	return tipoCambioDR;
}
public void setTipoCambioDR(String tipoCambioDR) {
	this.tipoCambioDR = tipoCambioDR;
}
public String getImpBase() {
	return impBase;
}
public void setImpBase(String impBase) {
	this.impBase = impBase;
}
public String getUnidad() {
	return unidad;
}
public void setUnidad(String unidad) {
	this.unidad = unidad;
}
public String getImpuesto() {
	return impuesto;
}
public void setImpuesto(String impuesto) {
	this.impuesto = impuesto;
}
public String getTipoFactor() {
	return tipoFactor;
}
public void setTipoFactor(String tipoFactor) {
	this.tipoFactor = tipoFactor;
}
public String getTasa() {
	return tasa;
}
public void setTasa(String tasa) {
	this.tasa = tasa;
}
public String getImpTranslado() {
	return impTranslado;
}
public void setImpTranslado(String impTranslado) {
	this.impTranslado = impTranslado;
}
public String getIdentificador() {
	return identificador;
}
public void setIdentificador(String identificador) {
	this.identificador = identificador;
}
public String getComentario() {
	return comentario;
}
public void setComentario(String comentario) {
	this.comentario = comentario;
}
/**
 * @return the numOperacion
 */
public String getNumOperacion() {
	return numOperacion;
}
/**
 * @param numOperacion the numOperacion to set
 */
public void setNumOperacion(String numOperacion) {
	this.numOperacion = numOperacion;
}

}
