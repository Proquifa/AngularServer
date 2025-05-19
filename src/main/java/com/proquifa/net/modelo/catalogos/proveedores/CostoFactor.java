/**
 * 
 */
package com.proquifa.net.modelo.catalogos.proveedores;

import java.util.Date;

/**
 * @author orosales
 *
 */
public class CostoFactor {
	
//	private Double descuento;
//	private Double honorarios;
//	private Double almacenTransito;	
//	private Double factorUtilidad;
	
	private Long idCostoFactor;
	private Double costoConsularizacion;
	private Double fleteDocumentacion;
	private Double factorIGI;
	private Double factorCostoFijo;
	private Double permiso;
	private Double almacenDestino;
	private Double factorDTA;
	private Long idAgenteAduanal;
	private Long idLugarAgenteAduanal;
	private Long idLugarConcepto;	
	private Double factorValorEnAduana;
	private Double factorDescuento;
	private Double factorFletePC; //Punto Consolidacion
	private Boolean stockDisable;
	private Boolean fleteExpressDisable;
	private Integer piezas;
	private Double montoLicencia;
	private Double porcentajeLicencia;

	private Double montoAAplus;
	private Double montoAA;
	private Double montoAM;
	private Double montoAB;
	private Double montoMA;
	private Double montoMM;
	private Double montoMB;
	private Double montoBajo;
	private Double montoFExpress;
	private Double montoStock;
	private Double montoDistribuidor;
	private Double montoComision;
	private Double montoPublico;
	
	private Double precioUAAplus;
	private Double precioUAA;
	private Double precioUAM;
	private Double precioUAB;
	private Double precioUMA;
	private Double precioUMM;
	private Double precioUMB;
	private Double precioUBajo;
	private Double precioUFExpress;
	private Double precioUStock;
	private Double precioUDistribuidor;
	private Double precioUComision;
	private Double precioUPublico;

	private Double cantidadAAplus;
	private Double cantidadAA;
	private Double cantidadAM;
	private Double cantidadAB;
	private Double cantidadMA;
	private Double cantidadMM;
	private Double cantidadMB;
	private Double cantidadBajo;
	private Double cantidadFExpress;
	private Double cantidadStock;
	private Double cantidadDistribuidor;
	private Double cantidadComision;
	private Double cantidadPublico;
	
	private Double valor;
	private Double cv;
	private Double cantidad;
	private Double diferencial;
	
	
	private Double diferencialAAplus;
	private Double diferencialAA;
	private Double diferencialAM;
	private Double diferencialAB;
	private Double diferencialMA;
	private Double diferencialMM;
	private Double diferencialMB;
	private Double diferencialBajo;
	private Double diferencialFExpress;
	private Double diferencialStock;
	private Double diferencialDistribuidor;
	private Double diferencialComision;
	private Double diferencialPublico;
	
	private Double factor_AAplus;
	private Double factor_AA;
	private Double factor_AM;
	private Double factor_AB;
	private Double factor_MA;
	private Double factor_MM;
	private Double factor_MB;
	private Double factor_Bajo;
	private Double factor_FExpress;
	private Double factor_Stock;
	private Double factorDistribuidor;
	private Double factor_Comision;
	private Double factorPublico;
	
	//Cliente
	private Double montoCliente;
	private Double precioUCliente;
	private Double cvCliente;
	private Double vaCliente;
	private Double cantidadCliente;
	private Double diferencialCliente;
	private Double factorCliente;
	private Long idClienteConfig;
	private Double factorNivelProveedor;
	private Boolean restablecer;
	private Boolean clienteAgente;
	private Boolean clienteLugar;
	private Boolean clienteConcepto;
	
	//Configuraci��n temporal
	private Double factorTempCliente;
	private Double factorTempCostoFijo;
	private Date caduca;
	private Double precioUTemporalCliente;
	private Double diferencialTemporalCliente;
	private Double costoMinTemporal;
	private Double costoMinFijo;

	
	/**
	 * @return the montoBajo
	 */
	public Double getMontoBajo() {
		return montoBajo;
	}
	/**
	 * @param montoBajo the montoBajo to set
	 */
	public void setMontoBajo(Double montoBajo) {
		this.montoBajo = montoBajo;
	}
	/**
	 * @return the precioUBajo
	 */
	public Double getPrecioUBajo() {
		return precioUBajo;
	}
	/**
	 * @param precioUBajo the precioUBajo to set
	 */
	public void setPrecioUBajo(Double precioUBajo) {
		this.precioUBajo = precioUBajo;
	}
	/**
	 * @return the cantidadBajo
	 */
	public Double getCantidadBajo() {
		return cantidadBajo;
	}
	/**
	 * @param cantidadBajo the cantidadBajo to set
	 */
	public void setCantidadBajo(Double cantidadBajo) {
		this.cantidadBajo = cantidadBajo;
	}
	/**
	 * @return the diferencialBajo
	 */
	public Double getDiferencialBajo() {
		return diferencialBajo;
	}
	/**
	 * @param diferencialBajo the diferencialBajo to set
	 */
	public void setDiferencialBajo(Double diferencialBajo) {
		this.diferencialBajo = diferencialBajo;
	}
	/**
	 * @return the factor_Bajo
	 */
	public Double getFactor_Bajo() {
		return factor_Bajo;
	}
	/**
	 * @param factor_Bajo the factor_Bajo to set
	 */
	public void setFactor_Bajo(Double factor_Bajo) {
		this.factor_Bajo = factor_Bajo;
	}

	
	/**
	 * @return the montoCliente
	 */
	public Double getMontoCliente() {
		return montoCliente;
	}
	/**
	 * @param montoCliente the montoCliente to set
	 */
	public void setMontoCliente(Double montoCliente) {
		this.montoCliente = montoCliente;
	}
	/**
	 * @return the precioUCliente
	 */
	public Double getPrecioUCliente() {
		return precioUCliente;
	}
	/**
	 * @param precioUCliente the precioUCliente to set
	 */
	public void setPrecioUCliente(Double precioUCliente) {
		this.precioUCliente = precioUCliente;
	}
	/**
	 * @return the cantidadCliente
	 */
	public Double getCantidadCliente() {
		return cantidadCliente;
	}
	/**
	 * @param cantidadCliente the cantidadCliente to set
	 */
	public void setCantidadCliente(Double cantidadCliente) {
		this.cantidadCliente = cantidadCliente;
	}
	/**
	 * @return the diferencialCliente
	 */
	public Double getDiferencialCliente() {
		return diferencialCliente;
	}
	/**
	 * @param diferencialCliente the diferencialCliente to set
	 */
	public void setDiferencialCliente(Double diferencialCliente) {
		this.diferencialCliente = diferencialCliente;
	}
	/**
	 * @return the factorCliente
	 */
	public Double getFactorCliente() {
		return factorCliente;
	}
	/**
	 * @param factorCliente the factorCliente to set
	 */
	public void setFactorCliente(Double factorCliente) {
		this.factorCliente = factorCliente;
	}
	/**
	 * @return the precioUFExpress
	 */
	public Double getPrecioUFExpress() {
		return precioUFExpress;
	}
	/**
	 * @param precioUFExpress the precioUFExpress to set
	 */
	public void setPrecioUFExpress(Double precioUFExpress) {
		this.precioUFExpress = precioUFExpress;
	}
	/**
	 * @return the precioUStock
	 */
	public Double getPrecioUStock() {
		return precioUStock;
	}
	/**
	 * @param precioUStock the precioUStock to set
	 */
	public void setPrecioUStock(Double precioUStock) {
		this.precioUStock = precioUStock;
	}
	/**
	 * @return the precioUDistribuidor
	 */
	public Double getPrecioUDistribuidor() {
		return precioUDistribuidor;
	}
	/**
	 * @param precioUDistribuidor the precioUDistribuidor to set
	 */
	public void setPrecioUDistribuidor(Double precioUDistribuidor) {
		this.precioUDistribuidor = precioUDistribuidor;
	}
	/**
	 * @return the precioUComision
	 */
	public Double getPrecioUComision() {
		return precioUComision;
	}
	/**
	 * @param precioUComision the precioUComision to set
	 */
	public void setPrecioUComision(Double precioUComision) {
		this.precioUComision = precioUComision;
	}
	/**
	 * @return the precioUPublico
	 */
	public Double getPrecioUPublico() {
		return precioUPublico;
	}
	/**
	 * @param precioUPublico the precioUPublico to set
	 */
	public void setPrecioUPublico(Double precioUPublico) {
		this.precioUPublico = precioUPublico;
	}
	
	
	/**
	 * @return the cantidadFExpress
	 */
	public Double getCantidadFExpress() {
		return cantidadFExpress;
	}
	/**
	 * @param cantidadFExpress the cantidadFExpress to set
	 */
	public void setCantidadFExpress(Double cantidadFExpress) {
		this.cantidadFExpress = cantidadFExpress;
	}
	/**
	 * @return the cantidadStock
	 */
	public Double getCantidadStock() {
		return cantidadStock;
	}
	/**
	 * @param cantidadStock the cantidadStock to set
	 */
	public void setCantidadStock(Double cantidadStock) {
		this.cantidadStock = cantidadStock;
	}
	/**
	 * @return the cantidadDistribuidor
	 */
	public Double getCantidadDistribuidor() {
		return cantidadDistribuidor;
	}
	/**
	 * @param cantidadDistribuidor the cantidadDistribuidor to set
	 */
	public void setCantidadDistribuidor(Double cantidadDistribuidor) {
		this.cantidadDistribuidor = cantidadDistribuidor;
	}
	/**
	 * @return the cantidadComision
	 */
	public Double getCantidadComision() {
		return cantidadComision;
	}
	/**
	 * @param cantidadComision the cantidadComision to set
	 */
	public void setCantidadComision(Double cantidadComision) {
		this.cantidadComision = cantidadComision;
	}
	/**
	 * @return the cantidadPublico
	 */
	public Double getCantidadPublico() {
		return cantidadPublico;
	}
	/**
	 * @param cantidadPublico the cantidadPublico to set
	 */
	public void setCantidadPublico(Double cantidadPublico) {
		this.cantidadPublico = cantidadPublico;
	}

	/**
	 * @return the diferencialFExpress
	 */
	public Double getDiferencialFExpress() {
		return diferencialFExpress;
	}
	/**
	 * @param diferencialFExpress the diferencialFExpress to set
	 */
	public void setDiferencialFExpress(Double diferencialFExpress) {
		this.diferencialFExpress = diferencialFExpress;
	}
	/**
	 * @return the diferencialStock
	 */
	public Double getDiferencialStock() {
		return diferencialStock;
	}
	/**
	 * @param diferencialStock the diferencialStock to set
	 */
	public void setDiferencialStock(Double diferencialStock) {
		this.diferencialStock = diferencialStock;
	}
	/**
	 * @return the diferencialDistribuidor
	 */
	public Double getDiferencialDistribuidor() {
		return diferencialDistribuidor;
	}
	/**
	 * @param diferencialDistribuidor the diferencialDistribuidor to set
	 */
	public void setDiferencialDistribuidor(Double diferencialDistribuidor) {
		this.diferencialDistribuidor = diferencialDistribuidor;
	}
	/**
	 * @return the diferencialComision
	 */
	public Double getDiferencialComision() {
		return diferencialComision;
	}
	/**
	 * @param diferencialComision the diferencialComision to set
	 */
	public void setDiferencialComision(Double diferencialComision) {
		this.diferencialComision = diferencialComision;
	}
	/**
	 * @return the diferencialPublico
	 */
	public Double getDiferencialPublico() {
		return diferencialPublico;
	}
	/**
	 * @param diferencialPublico the diferencialPublico to set
	 */
	public void setDiferencialPublico(Double diferencialPublico) {
		this.diferencialPublico = diferencialPublico;
	}

	
	
	
	
	
	/**
	 * @return the idCostoFactor
	 */
	public Long getIdCostoFactor() {
		return idCostoFactor;
	}
	/**
	 * @param idCostoFactor the idCostoFactor to set
	 */
	public void setIdCostoFactor(Long idCostoFactor) {
		this.idCostoFactor = idCostoFactor;
	}
	
	/**
	 * @return the costoConsularizacion
	 */
	public Double getCostoConsularizacion() {
		return costoConsularizacion;
	}
	/**
	 * @param costoConsularizacion the costoConsularizacion to set
	 */
	public void setCostoConsularizacion(Double costoConsularizacion) {
		this.costoConsularizacion = costoConsularizacion;
	}
	/**
	 * @return the fleteDocumentacion
	 */
	public Double getFleteDocumentacion() {
		return fleteDocumentacion;
	}
	/**
	 * @param fleteDocumentacion the fleteDocumentacion to set
	 */
	public void setFleteDocumentacion(Double fleteDocumentacion) {
		this.fleteDocumentacion = fleteDocumentacion;
	}
	/**
	 * @return the factorIGI
	 */
	public Double getFactorIGI() {
		return factorIGI;
	}
	/**
	 * @param factorIGI the factorIGI to set
	 */
	public void setFactorIGI(Double factorIGI) {
		this.factorIGI = factorIGI;
	}
	/**
	 * @return the factorCostoFijo
	 */
	public Double getFactorCostoFijo() {
		return factorCostoFijo;
	}
	/**
	 * @param factorCostoFijo the factorCostoFijo to set
	 */
	public void setFactorCostoFijo(Double factorCostoFijo) {
		this.factorCostoFijo = factorCostoFijo;
	}
	/**
	 * @return the permiso
	 */
	public Double getPermiso() {
		return permiso;
	}
	/**
	 * @param permiso the permiso to set
	 */
	public void setPermiso(Double permiso) {
		this.permiso = permiso;
	}
	/**
	 * @return the almacenDestino
	 */
	public Double getAlmacenDestino() {
		return almacenDestino;
	}
	/**
	 * @param almacenDestino the almacenDestino to set
	 */
	public void setAlmacenDestino(Double almacenDestino) {
		this.almacenDestino = almacenDestino;
	}
	/**
	 * @return the factorDTA
	 */
	public Double getFactorDTA() {
		return factorDTA;
	}
	/**
	 * @param factorDTA the factorDTA to set
	 */
	public void setFactorDTA(Double factorDTA) {
		this.factorDTA = factorDTA;
	}
	/**
	 * @return the idAgenteAduanal
	 */
	public Long getIdAgenteAduanal() {
		return idAgenteAduanal;
	}
	/**
	 * @param idAgenteAduanal the idAgenteAduanal to set
	 */
	public void setIdAgenteAduanal(Long idAgenteAduanal) {
		this.idAgenteAduanal = idAgenteAduanal;
	}
	/**
	 * @return the idLugarAgenteAduanal
	 */
	public Long getIdLugarAgenteAduanal() {
		return idLugarAgenteAduanal;
	}
	/**
	 * @param idLugarAgenteAduanal the idLugarAgenteAduanal to set
	 */
	public void setIdLugarAgenteAduanal(Long idLugarAgenteAduanal) {
		this.idLugarAgenteAduanal = idLugarAgenteAduanal;
	}
	/**
	 * @return the montoFExpress
	 */
	public Double getMontoFExpress() {
		return montoFExpress;
	}
	/**
	 * @param montoFExpress the montoFExpress to set
	 */
	public void setMontoFExpress(Double montoFExpress) {
		this.montoFExpress = montoFExpress;
	}
	/**
	 * @return the montoStock
	 */
	public Double getMontoStock() {
		return montoStock;
	}
	/**
	 * @param montoStock the montoStock to set
	 */
	public void setMontoStock(Double montoStock) {
		this.montoStock = montoStock;
	}
	/**
	 * @return the montoDistribuidor
	 */
	public Double getMontoDistribuidor() {
		return montoDistribuidor;
	}
	/**
	 * @param montoDistribuidor the montoDistribuidor to set
	 */
	public void setMontoDistribuidor(Double montoDistribuidor) {
		this.montoDistribuidor = montoDistribuidor;
	}
	/**
	 * @return the montoComision
	 */
	public Double getMontoComision() {
		return montoComision;
	}
	/**
	 * @param montoComision the montoComision to set
	 */
	public void setMontoComision(Double montoComision) {
		this.montoComision = montoComision;
	}
	/**
	 * @return the montoPublico
	 */
	public Double getMontoPublico() {
		return montoPublico;
	}
	/**
	 * @param montoPublico the montoPublico to set
	 */
	public void setMontoPublico(Double montoPublico) {
		this.montoPublico = montoPublico;
	}
	/**
	 * @return the factor_FExpress
	 */
	public Double getFactor_FExpress() {
		return factor_FExpress;
	}
	/**
	 * @param factor_FExpress the factor_FExpress to set
	 */
	public void setFactor_FExpress(Double factor_FExpress) {
		this.factor_FExpress = factor_FExpress;
	}
	/**
	 * @return the factor_Stock
	 */
	public Double getFactor_Stock() {
		return factor_Stock;
	}
	/**
	 * @param factor_Stock the factor_Stock to set
	 */
	public void setFactor_Stock(Double factor_Stock) {
		this.factor_Stock = factor_Stock;
	}
	/**
	 * @return the factorDistribuidor
	 */
	public Double getFactorDistribuidor() {
		return factorDistribuidor;
	}
	/**
	 * @param factorDistribuidor the factorDistribuidor to set
	 */
	public void setFactorDistribuidor(Double factorDistribuidor) {
		this.factorDistribuidor = factorDistribuidor;
	}
	/**
	 * @return the factor_Comision
	 */
	public Double getFactor_Comision() {
		return factor_Comision;
	}
	/**
	 * @param factor_Comision the factor_Comision to set
	 */
	public void setFactor_Comision(Double factor_Comision) {
		this.factor_Comision = factor_Comision;
	}
	/**
	 * @return the factorPublico
	 */
	public Double getFactorPublico() {
		return factorPublico;
	}
	/**
	 * @param factorPublico the factorPublico to set
	 */
	public void setFactorPublico(Double factorPublico) {
		this.factorPublico = factorPublico;
	}
	/**
	 * @return the montoAA
	 */
	public Double getMontoAA() {
		return montoAA;
	}
	/**
	 * @param montoAA the montoAA to set
	 */
	public void setMontoAA(Double montoAA) {
		this.montoAA = montoAA;
	}
	/**
	 * @return the montoAM
	 */
	public Double getMontoAM() {
		return montoAM;
	}
	/**
	 * @param montoAM the montoAM to set
	 */
	public void setMontoAM(Double montoAM) {
		this.montoAM = montoAM;
	}
	/**
	 * @return the montoAB
	 */
	public Double getMontoAB() {
		return montoAB;
	}
	/**
	 * @param montoAB the montoAB to set
	 */
	public void setMontoAB(Double montoAB) {
		this.montoAB = montoAB;
	}
	/**
	 * @return the montoMA
	 */
	public Double getMontoMA() {
		return montoMA;
	}
	/**
	 * @param montoMA the montoMA to set
	 */
	public void setMontoMA(Double montoMA) {
		this.montoMA = montoMA;
	}
	/**
	 * @return the montoMM
	 */
	public Double getMontoMM() {
		return montoMM;
	}
	/**
	 * @param montoMM the montoMM to set
	 */
	public void setMontoMM(Double montoMM) {
		this.montoMM = montoMM;
	}
	/**
	 * @return the montoMB
	 */
	public Double getMontoMB() {
		return montoMB;
	}
	/**
	 * @param montoMB the montoMB to set
	 */
	public void setMontoMB(Double montoMB) {
		this.montoMB = montoMB;
	}
	/**
	 * @return the precioUAA
	 */
	public Double getPrecioUAA() {
		return precioUAA;
	}
	/**
	 * @param precioUAA the precioUAA to set
	 */
	public void setPrecioUAA(Double precioUAA) {
		this.precioUAA = precioUAA;
	}
	/**
	 * @return the precioUAM
	 */
	public Double getPrecioUAM() {
		return precioUAM;
	}
	/**
	 * @param precioUAM the precioUAM to set
	 */
	public void setPrecioUAM(Double precioUAM) {
		this.precioUAM = precioUAM;
	}
	/**
	 * @return the precioUAB
	 */
	public Double getPrecioUAB() {
		return precioUAB;
	}
	/**
	 * @param precioUAB the precioUAB to set
	 */
	public void setPrecioUAB(Double precioUAB) {
		this.precioUAB = precioUAB;
	}
	/**
	 * @return the precioUMA
	 */
	public Double getPrecioUMA() {
		return precioUMA;
	}
	/**
	 * @param precioUMA the precioUMA to set
	 */
	public void setPrecioUMA(Double precioUMA) {
		this.precioUMA = precioUMA;
	}
	/**
	 * @return the precioUMM
	 */
	public Double getPrecioUMM() {
		return precioUMM;
	}
	/**
	 * @param precioUMM the precioUMM to set
	 */
	public void setPrecioUMM(Double precioUMM) {
		this.precioUMM = precioUMM;
	}
	/**
	 * @return the precioUMB
	 */
	public Double getPrecioUMB() {
		return precioUMB;
	}
	/**
	 * @param precioUMB the precioUMB to set
	 */
	public void setPrecioUMB(Double precioUMB) {
		this.precioUMB = precioUMB;
	}
	/**
	 * @return the cantidadAA
	 */
	public Double getCantidadAA() {
		return cantidadAA;
	}
	/**
	 * @param cantidadAA the cantidadAA to set
	 */
	public void setCantidadAA(Double cantidadAA) {
		this.cantidadAA = cantidadAA;
	}
	/**
	 * @return the cantidadAM
	 */
	public Double getCantidadAM() {
		return cantidadAM;
	}
	/**
	 * @param cantidadAM the cantidadAM to set
	 */
	public void setCantidadAM(Double cantidadAM) {
		this.cantidadAM = cantidadAM;
	}
	/**
	 * @return the cantidadAB
	 */
	public Double getCantidadAB() {
		return cantidadAB;
	}
	/**
	 * @param cantidadAB the cantidadAB to set
	 */
	public void setCantidadAB(Double cantidadAB) {
		this.cantidadAB = cantidadAB;
	}
	/**
	 * @return the cantidadMA
	 */
	public Double getCantidadMA() {
		return cantidadMA;
	}
	/**
	 * @param cantidadMA the cantidadMA to set
	 */
	public void setCantidadMA(Double cantidadMA) {
		this.cantidadMA = cantidadMA;
	}
	/**
	 * @return the cantidadMM
	 */
	public Double getCantidadMM() {
		return cantidadMM;
	}
	/**
	 * @param cantidadMM the cantidadMM to set
	 */
	public void setCantidadMM(Double cantidadMM) {
		this.cantidadMM = cantidadMM;
	}
	/**
	 * @return the diferencialAA
	 */
	public Double getDiferencialAA() {
		return diferencialAA;
	}
	/**
	 * @param diferencialAA the diferencialAA to set
	 */
	public void setDiferencialAA(Double diferencialAA) {
		this.diferencialAA = diferencialAA;
	}
	/**
	 * @return the diferencialAM
	 */
	public Double getDiferencialAM() {
		return diferencialAM;
	}
	/**
	 * @param diferencialAM the diferencialAM to set
	 */
	public void setDiferencialAM(Double diferencialAM) {
		this.diferencialAM = diferencialAM;
	}
	/**
	 * @return the diferencialAB
	 */
	public Double getDiferencialAB() {
		return diferencialAB;
	}
	/**
	 * @param diferencialAB the diferencialAB to set
	 */
	public void setDiferencialAB(Double diferencialAB) {
		this.diferencialAB = diferencialAB;
	}
	/**
	 * @return the diferencialMA
	 */
	public Double getDiferencialMA() {
		return diferencialMA;
	}
	/**
	 * @param diferencialMA the diferencialMA to set
	 */
	public void setDiferencialMA(Double diferencialMA) {
		this.diferencialMA = diferencialMA;
	}
	/**
	 * @return the diferencialMM
	 */
	public Double getDiferencialMM() {
		return diferencialMM;
	}
	/**
	 * @param diferencialMM the diferencialMM to set
	 */
	public void setDiferencialMM(Double diferencialMM) {
		this.diferencialMM = diferencialMM;
	}
	/**
	 * @return the diferencialMB
	 */
	public Double getDiferencialMB() {
		return diferencialMB;
	}
	/**
	 * @param diferencialMB the diferencialMB to set
	 */
	public void setDiferencialMB(Double diferencialMB) {
		this.diferencialMB = diferencialMB;
	}
	/**
	 * @return the factor_AA
	 */
	public Double getFactor_AA() {
		return factor_AA;
	}
	/**
	 * @param factor_AA the factor_AA to set
	 */
	public void setFactor_AA(Double factor_AA) {
		this.factor_AA = factor_AA;
	}
	/**
	 * @return the factor_AM
	 */
	public Double getFactor_AM() {
		return factor_AM;
	}
	/**
	 * @param factor_AM the factor_AM to set
	 */
	public void setFactor_AM(Double factor_AM) {
		this.factor_AM = factor_AM;
	}
	/**
	 * @return the factor_AB
	 */
	public Double getFactor_AB() {
		return factor_AB;
	}
	/**
	 * @param factor_AB the factor_AB to set
	 */
	public void setFactor_AB(Double factor_AB) {
		this.factor_AB = factor_AB;
	}
	/**
	 * @return the factor_MA
	 */
	public Double getFactor_MA() {
		return factor_MA;
	}
	/**
	 * @param factor_MA the factor_MA to set
	 */
	public void setFactor_MA(Double factor_MA) {
		this.factor_MA = factor_MA;
	}
	/**
	 * @return the factor_MM
	 */
	public Double getFactor_MM() {
		return factor_MM;
	}
	/**
	 * @param factor_MM the factor_MM to set
	 */
	public void setFactor_MM(Double factor_MM) {
		this.factor_MM = factor_MM;
	}
	/**
	 * @return the factor_MB
	 */
	public Double getFactor_MB() {
		return factor_MB;
	}
	/**
	 * @param factor_MB the factor_MB to set
	 */
	public void setFactor_MB(Double factor_MB) {
		this.factor_MB = factor_MB;
	}
	/**
	 * @return the cantidadMB
	 */
	public Double getCantidadMB() {
		return cantidadMB;
	}
	/**
	 * @param cantidadMB the cantidadMB to set
	 */
	public void setCantidadMB(Double cantidadMB) {
		this.cantidadMB = cantidadMB;
	}
	/**
	 * @return the idClienteConfig
	 */
	public Long getIdClienteConfig() {
		return idClienteConfig;
	}
	/**
	 * @param idClienteConfig the idClienteConfig to set
	 */
	public void setIdClienteConfig(Long idClienteConfig) {
		this.idClienteConfig = idClienteConfig;
	}
	/**
	 * @return the factorNivelProveedor
	 */
	public Double getFactorNivelProveedor() {
		return factorNivelProveedor;
	}
	/**
	 * @param factorNivelProveedor the factorNivelProveedor to set
	 */
	public void setFactorNivelProveedor(Double factorNivelProveedor) {
		this.factorNivelProveedor = factorNivelProveedor;
	}
	/**
	 * @return the restablecer
	 */
	public Boolean getRestablecer() {
		return restablecer;
	}
	/**
	 * @param restablecer the restablecer to set
	 */
	public void setRestablecer(Boolean restablecer) {
		this.restablecer = restablecer;
	}
	/**
	 * @return the factorValorEnAduana
	 */
	public Double getFactorValorEnAduana() {
		return factorValorEnAduana;
	}
	/**
	 * @param factorValorEnAduana the factorValorEnAduana to set
	 */
	public void setFactorValorEnAduana(Double factorValorEnAduana) {
		this.factorValorEnAduana = factorValorEnAduana;
	}
	/**
	 * @return the factorDescuento
	 */
	public Double getFactorDescuento() {
		return factorDescuento;
	}
	/**
	 * @param factorDescuento the factorDescuento to set
	 */
	public void setFactorDescuento(Double factorDescuento) {
		this.factorDescuento = factorDescuento;
	}
	
	/**
	 * @return the factorFletePC
	 */
	public Double getFactorFletePC() {
		return factorFletePC;
	}
	/**
	 * @param factorFletePC the factorFletePC to set
	 */
	public void setFactorFletePC(Double factorFletePC) {
		this.factorFletePC = factorFletePC;
	}
	/**
	 * @return the idLugarConcepto
	 */
	public Long getIdLugarConcepto() {
		return idLugarConcepto;
	}
	/**
	 * @param idLugarConcepto the idLugarConcepto to set
	 */
	public void setIdLugarConcepto(Long idLugarConcepto) {
		this.idLugarConcepto = idLugarConcepto;
	}
	/**
	 * @return the stockDisable
	 */
	public Boolean getStockDisable() {
		return stockDisable;
	}
	/**
	 * @param stockDisable the stockDisable to set
	 */
	public void setStockDisable(Boolean stockDisable) {
		this.stockDisable = stockDisable;
	}
	/**
	 * @return the fleteExpressDisable
	 */
	public Boolean getFleteExpressDisable() {
		return fleteExpressDisable;
	}
	/**
	 * @param fleteExpressDisable the fleteExpressDisable to set
	 */
	public void setFleteExpressDisable(Boolean fleteExpressDisable) {
		this.fleteExpressDisable = fleteExpressDisable;
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
	 * @return the montoLicencia
	 */
	public Double getMontoLicencia() {
		return montoLicencia;
	}
	/**
	 * @param montoLicencia the montoLicencia to set
	 */
	public void setMontoLicencia(Double montoLicencia) {
		this.montoLicencia = montoLicencia;
	}
	/**
	 * @return the porcentajeLicencia
	 */
	public Double getPorcentajeLicencia() {
		return porcentajeLicencia;
	}
	/**
	 * @param porcentajeLicencia the porcentajeLicencia to set
	 */
	public void setPorcentajeLicencia(Double porcentajeLicencia) {
		this.porcentajeLicencia = porcentajeLicencia;
	}
	/**
	 * @return the clienteAgente
	 */
	public Boolean getClienteAgente() {
		return clienteAgente;
	}
	/**
	 * @param clienteAgente the clienteAgente to set
	 */
	public void setClienteAgente(Boolean clienteAgente) {
		this.clienteAgente = clienteAgente;
	}
	/**
	 * @return the clienteLugar
	 */
	public Boolean getClienteLugar() {
		return clienteLugar;
	}
	/**
	 * @param clienteLugar the clienteLugar to set
	 */
	public void setClienteLugar(Boolean clienteLugar) {
		this.clienteLugar = clienteLugar;
	}
	/**
	 * @return the clienteConcepto
	 */
	public Boolean getClienteConcepto() {
		return clienteConcepto;
	}
	/**
	 * @param clienteConcepto the clienteConcepto to set
	 */
	public void setClienteConcepto(Boolean clienteConcepto) {
		this.clienteConcepto = clienteConcepto;
	}
	/**
	 * @return the montoAAplus
	 */
	public Double getMontoAAplus() {
		return montoAAplus;
	}
	/**
	 * @param montoAAplus the montoAAplus to set
	 */
	public void setMontoAAplus(Double montoAAplus) {
		this.montoAAplus = montoAAplus;
	}
	/**
	 * @return the precioUAAplus
	 */
	public Double getPrecioUAAplus() {
		return precioUAAplus;
	}
	/**
	 * @param precioUAAplus the precioUAAplus to set
	 */
	public void setPrecioUAAplus(Double precioUAAplus) {
		this.precioUAAplus = precioUAAplus;
	}
	/**
	 * @return the cantidadAAplus
	 */
	public Double getCantidadAAplus() {
		return cantidadAAplus;
	}
	/**
	 * @param cantidadAAplus the cantidadAAplus to set
	 */
	public void setCantidadAAplus(Double cantidadAAplus) {
		this.cantidadAAplus = cantidadAAplus;
	}
	/**
	 * @return the diferencialAAplus
	 */
	public Double getDiferencialAAplus() {
		return diferencialAAplus;
	}
	/**
	 * @param diferencialAAplus the diferencialAAplus to set
	 */
	public void setDiferencialAAplus(Double diferencialAAplus) {
		this.diferencialAAplus = diferencialAAplus;
	}
	/**
	 * @return the factor_AAplus
	 */
	public Double getFactor_AAplus() {
		return factor_AAplus;
	}
	/**
	 * @param factor_AAplus the factor_AAplus to set
	 */
	public void setFactor_AAplus(Double factor_AAplus) {
		this.factor_AAplus = factor_AAplus;
	}
	public Double getCvCliente() {
		return cvCliente;
	}
	public void setCvCliente(Double cvCliente) {
		this.cvCliente = cvCliente;
	}
	public Double getVaCliente() {
		return vaCliente;
	}
	public void setVaCliente(Double vaCliente) {
		this.vaCliente = vaCliente;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public Double getCv() {
		return cv;
	}
	public void setCv(Double cv) {
		this.cv = cv;
	}
	public Double getCantidad() {
		return cantidad;
	}
	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}
	public Double getDiferencial() {
		return diferencial;
	}
	public void setDiferencial(Double diferencial) {
		this.diferencial = diferencial;
	}
	public Double getFactorTempCliente() {
		return factorTempCliente;
	}
	public void setFactorTempCliente(Double factorTempCliente) {
		this.factorTempCliente = factorTempCliente;
	}
	public Double getFactorTempCostoFijo() {
		return factorTempCostoFijo;
	}
	public void setFactorTempCostoFijo(Double factorTempCostoFijo) {
		this.factorTempCostoFijo = factorTempCostoFijo;
	}
	public Date getCaduca() {
		return caduca;
	}
	public void setCaduca(Date caduca) {
		this.caduca = caduca;
	}
	public Double getPrecioUTemporalCliente() {
		return precioUTemporalCliente;
	}
	public void setPrecioUTemporalCliente(Double precioUTemporalCliente) {
		this.precioUTemporalCliente = precioUTemporalCliente;
	}
	public Double getDiferencialTemporalCliente() {
		return diferencialTemporalCliente;
	}
	public void setDiferencialTemporalCliente(Double diferencialTemporalCliente) {
		this.diferencialTemporalCliente = diferencialTemporalCliente;
	}
	public Double getCostoMinTemporal() {
		return costoMinTemporal;
	}
	public void setCostoMinTemporal(Double costoMinTemporal) {
		this.costoMinTemporal = costoMinTemporal;
	}
	public Double getCostoMinFijo() {
		return costoMinFijo;
	}
	public void setCostoMinFijo(Double costoMinFijo) {
		this.costoMinFijo = costoMinFijo;
	}

		
}