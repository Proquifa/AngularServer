/**
 * 
 */
package com.proquifa.net.modelo.comun.util.documentoXls;

import java.util.Date;

/**
 * @author yosimar.mendez
 *
 */
public class ProductoEP {

	private Integer pkEP;
	private String codigo;
	private String concepto;
	private Integer lote;
	private String cas;
	private String quantity;
	private double costo;

	private String unidad;
	private double cantidad;

	private Integer idProducto;
	private boolean vigenteProd;
	private String codigoProd;
	private String conceptoProd;
	private String casProd;
	private Date caducaProd;
	private double costoProd;
	
	private String estado;
	private String fabrica;
	private String moneda;
	private Integer proveedor;
	private Date fecha;
	
	private Date validoHasta;
	

	/**
	 * 
	 */
	 public ProductoEP() {
		 super();
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
	  * @return the lote
	  */
	 public Integer getLote() {
		 return lote;
	 }

	 /**
	  * @param lote the lote to set
	  */
	 public void setLote(Integer lote) {
		 this.lote = lote;
	 }

	 /**
	  * @return the quantity
	  */
	 public String getQuantity() {
		 return quantity;
	 }

	 /**
	  * @param quantity the quantity to set
	  */
	 public void setQuantity(String quantity) {
		 this.quantity = quantity;

		 if (quantity.equals("n/a"))
		 {
			 cantidad = 0.0;
			 unidad = "";
		 }
		 else
		 {
			 String[] valor = quantity.split(" ");
			 if (valor.length == 1)
				 cantidad = Double.parseDouble(valor[0]);
			 else if (valor.length == 2)
			 {
				 cantidad = Double.parseDouble(valor[0]);
				 unidad = valor[1];
			 }
		 }
	 }

	 /**
	  * @return the costo
	  */
	 public double getCosto() {
		 return costo;
	 }

	 /**
	  * @param costo the costo to set
	  */
	 public void setCosto(double costo) {
		 this.costo = costo;
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
	  * @return the cantidad
	  */
	 public double getCantidad() {
		 return cantidad;
	 }

	 /**
	  * @param cantidad the cantidad to set
	  */
	 public void setCantidad(double cantidad) {
		 this.cantidad = cantidad;
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
	  * @return the pkEP
	  */
	 public Integer getPkEP() {
		 return pkEP;
	 }

	 /**
	  * @param pkEP the pkEP to set
	  */
	 public void setPkEP(Integer pkEP) {
		 this.pkEP = pkEP;
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
	 * @return the vigenteProd
	 */
	public boolean isVigenteProd() {
		return vigenteProd;
	}

	/**
	 * @param vigenteProd the vigenteProd to set
	 */
	public void setVigenteProd(boolean vigenteProd) {
		this.vigenteProd = vigenteProd;
	}

	/**
	 * @return the codigoProd
	 */
	public String getCodigoProd() {
		return codigoProd;
	}

	/**
	 * @param codigoProd the codigoProd to set
	 */
	public void setCodigoProd(String codigoProd) {
		this.codigoProd = codigoProd;
	}

	/**
	 * @return the conceptoProd
	 */
	public String getConceptoProd() {
		return conceptoProd;
	}

	/**
	 * @param conceptoProd the conceptoProd to set
	 */
	public void setConceptoProd(String conceptoProd) {
		this.conceptoProd = conceptoProd;
	}

	/**
	 * @return the casProd
	 */
	public String getCasProd() {
		return casProd;
	}

	/**
	 * @param casProd the casProd to set
	 */
	public void setCasProd(String casProd) {
		this.casProd = casProd;
	}

	/**
	 * @return the caducaProd
	 */
	public Date getCaducaProd() {
		return caducaProd;
	}

	/**
	 * @param caducaProd the caducaProd to set
	 */
	public void setCaducaProd(Date caducaProd) {
		this.caducaProd = caducaProd;
	}

	/**
	 * @return the costoProd
	 */
	public double getCostoProd() {
		return costoProd;
	}

	/**
	 * @param costoProd the costoProd to set
	 */
	public void setCostoProd(double costoProd) {
		this.costoProd = costoProd;
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
	 * @return the proveedor
	 */
	public Integer getProveedor() {
		return proveedor;
	}

	/**
	 * @param proveedor the proveedor to set
	 */
	public void setProveedor(Integer proveedor) {
		this.proveedor = proveedor;
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
	 * @return the validoHasta
	 */
	public Date getValidoHasta() {
		return validoHasta;
	}

	/**
	 * @param validoHasta the validoHasta to set
	 */
	public void setValidoHasta(Date validoHasta) {
		this.validoHasta = validoHasta;
	}
}
