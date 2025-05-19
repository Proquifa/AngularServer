package com.proquifa.net.modelo.tableros.cliente;

public class ResumenVentasESAC {
	//S.ESAC, S.Identificador, S.CLIENTES, S.DISTRIBUCION, S.TotalVenta, S.AAPLUS, S.AAPLUS_C
	private String esac;
	private String funcion;
	private Integer totalClientes;
	private Double distribucionClientes;
	private Double distribucionClientesAnt;
	private Double totalVentaEsac;
	private Double ventaAAplus;
	private Double ventaAA;
	private Double ventaAM;
	private Double ventaAB;
	private Double ventaMA;
	private Double ventaMM;
	private Double ventaMB;
	private Double ventaBajos;
	private Double ventaNuevos;
	private Double ventaDistribuidor;
	private Double montoVentaAnterior;
	private Double objetivoFundamental;
	private Double objetivoDeseado;
	
	private Double diferencialFundamental;  
	private Double diferencialDeseado;
	private String nivelIngreso;
	
	private Integer clientesAAplus;
	private Integer clientesAA;
	private Integer clientesAM;
	private Integer clientesAB;
	private Integer clientesMA;
	private Integer clientesMM;
	private Integer clientesMB;
	private Integer clientesBajos;
	private Integer clientesNuevos;
	private Integer clientesDistribuidor;
	
	private Integer carterasAltos;
	private Integer carterasMedios;
	private Integer carterasBajos;
	private Integer carterasNuevos;
	private Integer carterasDistribuidor;
	
	private Double ventaAAplus_Ant;
	private Double ventaAA_Ant;
	private Double ventaAM_Ant;
	private Double ventaAB_Ant;
	private Double ventaMA_Ant;
	private Double ventaMM_Ant;
	private Double ventaMB_Ant;
	private Double ventaBajos_Ant;
	private Double ventaNuevos_Ant;
	private Double ventaDistribuidor_ant;
	
	private Double objFun_AAplus;
	private Double objFun_AA;
	private Double objFun_AM;
	private Double objFun_AB;
	private Double objFun_MA;
	private Double objFun_MM;
	private Double objFun_MB;
	private Double objFun_Bajo;
	private Double objFun_Distribuidor;
	private Double objDes_AAplus;
	private Double objDes_AA;
	private Double objDes_AM;
	private Double objDes_AB;
	private Double objDes_MA;
	private Double objDes_MM;
	private Double objDes_MB;
	private Double objDes_Bajo;
	private Double objDes_Distribuidor;
	
	private String nombreCartera;
	private long  idcartera;
	private String folioCartera;
	
	private long idEjecutivoVenta;
	private long idEsac;
	private long idCobrador;
	private String nombreEjecutivoVenta;
	private String nombreEsac;
	private String nombreCobrador;
	
	int anio;

	/**
	 * @return the esac
	 */
	public String getEsac() {
		return esac;
	}
	/**
	 * @param esac the esac to set
	 */
	public void setEsac(String esac) {
		this.esac = esac;
	}
	/**
	 * @return the funcion
	 */
	public String getFuncion() {
		return funcion;
	}
	/**
	 * @param funcion the funcion to set
	 */
	public void setFuncion(String funcion) {
		this.funcion = funcion;
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
	 * @return the distribucionClientes
	 */
	public Double getDistribucionClientes() {
		return distribucionClientes;
	}
	/**
	 * @param distribucionClientes the distribucionClientes to set
	 */
	public void setDistribucionClientes(Double distribucionClientes) {
		this.distribucionClientes = distribucionClientes;
	}
	/**
	 * @return the totalVentaEsac
	 */
	public Double getTotalVentaEsac() {
		return totalVentaEsac;
	}
	/**
	 * @param totalVentaEsac the totalVentaEsac to set
	 */
	public void setTotalVentaEsac(Double totalVentaEsac) {
		this.totalVentaEsac = totalVentaEsac;
	}
	/**
	 * @return the ventaAAplus
	 */
	public Double getVentaAAplus() {
		return ventaAAplus;
	}
	/**
	 * @param ventaAAplus the ventaAAplus to set
	 */
	public void setVentaAAplus(Double ventaAAplus) {
		this.ventaAAplus = ventaAAplus;
	}
	/**
	 * @return the ventaAA
	 */
	public Double getVentaAA() {
		return ventaAA;
	}
	/**
	 * @param ventaAA the ventaAA to set
	 */
	public void setVentaAA(Double ventaAA) {
		this.ventaAA = ventaAA;
	}
	/**
	 * @return the ventaAM
	 */
	public Double getVentaAM() {
		return ventaAM;
	}
	/**
	 * @param ventaAM the ventaAM to set
	 */
	public void setVentaAM(Double ventaAM) {
		this.ventaAM = ventaAM;
	}
	/**
	 * @return the ventaAB
	 */
	public Double getVentaAB() {
		return ventaAB;
	}
	/**
	 * @param ventaAB the ventaAB to set
	 */
	public void setVentaAB(Double ventaAB) {
		this.ventaAB = ventaAB;
	}
	/**
	 * @return the ventaMA
	 */
	public Double getVentaMA() {
		return ventaMA;
	}
	/**
	 * @param ventaMA the ventaMA to set
	 */
	public void setVentaMA(Double ventaMA) {
		this.ventaMA = ventaMA;
	}
	/**
	 * @return the ventaMM
	 */
	public Double getVentaMM() {
		return ventaMM;
	}
	/**
	 * @param ventaMM the ventaMM to set
	 */
	public void setVentaMM(Double ventaMM) {
		this.ventaMM = ventaMM;
	}
	/**
	 * @return the ventaMB
	 */
	public Double getVentaMB() {
		return ventaMB;
	}
	/**
	 * @param ventaMB the ventaMB to set
	 */
	public void setVentaMB(Double ventaMB) {
		this.ventaMB = ventaMB;
	}
	/**
	 * @return the ventaDistribuidor
	 */
	public Double getVentaDistribuidor() {
		return ventaDistribuidor;
	}
	/**
	 * @param ventaDistribuidor the ventaDistribuidor to set
	 */
	public void setVentaDistribuidor(Double ventaDistribuidor) {
		this.ventaDistribuidor = ventaDistribuidor;
	}
	/**
	 * @return the clientesAAplus
	 */
	public Integer getClientesAAplus() {
		return clientesAAplus;
	}
	/**
	 * @param clientesAAplus the clientesAAplus to set
	 */
	public void setClientesAAplus(Integer clientesAAplus) {
		this.clientesAAplus = clientesAAplus;
	}
	/**
	 * @return the clientesAA
	 */
	public Integer getClientesAA() {
		return clientesAA;
	}
	/**
	 * @param clientesAA the clientesAA to set
	 */
	public void setClientesAA(Integer clientesAA) {
		this.clientesAA = clientesAA;
	}
	/**
	 * @return the clientesAM
	 */
	public Integer getClientesAM() {
		return clientesAM;
	}
	/**
	 * @param clientesAM the clientesAM to set
	 */
	public void setClientesAM(Integer clientesAM) {
		this.clientesAM = clientesAM;
	}
	/**
	 * @return the clientesAB
	 */
	public Integer getClientesAB() {
		return clientesAB;
	}
	/**
	 * @param clientesAB the clientesAB to set
	 */
	public void setClientesAB(Integer clientesAB) {
		this.clientesAB = clientesAB;
	}
	/**
	 * @return the clientesMA
	 */
	public Integer getClientesMA() {
		return clientesMA;
	}
	/**
	 * @param clientesMA the clientesMA to set
	 */
	public void setClientesMA(Integer clientesMA) {
		this.clientesMA = clientesMA;
	}
	/**
	 * @return the clientesMM
	 */
	public Integer getClientesMM() {
		return clientesMM;
	}
	/**
	 * @param clientesMM the clientesMM to set
	 */
	public void setClientesMM(Integer clientesMM) {
		this.clientesMM = clientesMM;
	}
	/**
	 * @return the clientesMB
	 */
	public Integer getClientesMB() {
		return clientesMB;
	}
	/**
	 * @param clientesMB the clientesMB to set
	 */
	public void setClientesMB(Integer clientesMB) {
		this.clientesMB = clientesMB;
	}
	/**
	 * @return the clientesDistribuidor
	 */
	public Integer getClientesDistribuidor() {
		return clientesDistribuidor;
	}
	/**
	 * @param clientesDistribuidor the clientesDistribuidor to set
	 */
	public void setClientesDistribuidor(Integer clientesDistribuidor) {
		this.clientesDistribuidor = clientesDistribuidor;
	}
	public Double getVentaBajos() {
		return ventaBajos;
	}
	public void setVentaBajos(Double ventaBajos) {
		this.ventaBajos = ventaBajos;
	}
	public Double getVentaNuevos() {
		return ventaNuevos;
	}
	public void setVentaNuevos(Double ventaNuevos) {
		this.ventaNuevos = ventaNuevos;
	}
	public Integer getClientesBajos() {
		return clientesBajos;
	}
	public void setClientesBajos(Integer clientesBajos) {
		this.clientesBajos = clientesBajos;
	}
	public Integer getClientesNuevos() {
		return clientesNuevos;
	}
	public void setClientesNuevos(Integer clientesNuevos) {
		this.clientesNuevos = clientesNuevos;
	}
	public Double getMontoVentaAnterior() {
		return montoVentaAnterior;
	}
	public void setMontoVentaAnterior(Double montoVentaAnterior) {
		this.montoVentaAnterior = montoVentaAnterior;
	}
	public Double getObjetivoFundamental() {
		return objetivoFundamental;
	}
	public void setObjetivoFundamental(Double objetivoFundamental) {
		this.objetivoFundamental = objetivoFundamental;
	}
	public Double getObjetivoDeseado() {
		return objetivoDeseado;
	}
	public void setObjetivoDeseado(Double objetivoDeseado) {
		this.objetivoDeseado = objetivoDeseado;
	}
	public Double getDiferencialFundamental() {
		return diferencialFundamental;
	}
	public void setDiferencialFundamental(Double diferencialFundamental) {
		this.diferencialFundamental = diferencialFundamental;
	}
	public Double getDiferencialDeseado() {
		return diferencialDeseado;
	}
	public void setDiferencialDeseado(Double diferencialDeseado) {
		this.diferencialDeseado = diferencialDeseado;
	}
	public Double getDistribucionClientesAnt() {
		return distribucionClientesAnt;
	}
	public void setDistribucionClientesAnt(Double distribucionClientesAnt) {
		this.distribucionClientesAnt = distribucionClientesAnt;
	}
	public Double getVentaAAplus_Ant() {
		return ventaAAplus_Ant;
	}
	public void setVentaAAplus_Ant(Double ventaAAplus_Ant) {
		this.ventaAAplus_Ant = ventaAAplus_Ant;
	}
	public Double getVentaAA_Ant() {
		return ventaAA_Ant;
	}
	public void setVentaAA_Ant(Double ventaAA_Ant) {
		this.ventaAA_Ant = ventaAA_Ant;
	}
	public Double getVentaAM_Ant() {
		return ventaAM_Ant;
	}
	public void setVentaAM_Ant(Double ventaAM_Ant) {
		this.ventaAM_Ant = ventaAM_Ant;
	}
	public Double getVentaAB_Ant() {
		return ventaAB_Ant;
	}
	public void setVentaAB_Ant(Double ventaAB_Ant) {
		this.ventaAB_Ant = ventaAB_Ant;
	}
	public Double getVentaMA_Ant() {
		return ventaMA_Ant;
	}
	public void setVentaMA_Ant(Double ventaMA_Ant) {
		this.ventaMA_Ant = ventaMA_Ant;
	}
	public Double getVentaMM_Ant() {
		return ventaMM_Ant;
	}
	public void setVentaMM_Ant(Double ventaMM_Ant) {
		this.ventaMM_Ant = ventaMM_Ant;
	}
	public Double getVentaMB_Ant() {
		return ventaMB_Ant;
	}
	public void setVentaMB_Ant(Double ventaMB_Ant) {
		this.ventaMB_Ant = ventaMB_Ant;
	}
	public Double getVentaBajos_Ant() {
		return ventaBajos_Ant;
	}
	public void setVentaBajos_Ant(Double ventaBajos_Ant) {
		this.ventaBajos_Ant = ventaBajos_Ant;
	}
	public Double getVentaNuevos_Ant() {
		return ventaNuevos_Ant;
	}
	public void setVentaNuevos_Ant(Double ventaNuevos_Ant) {
		this.ventaNuevos_Ant = ventaNuevos_Ant;
	}
	public Double getObjFun_AAplus() {
		return objFun_AAplus;
	}
	public void setObjFun_AAplus(Double objFun_AAplus) {
		this.objFun_AAplus = objFun_AAplus;
	}
	public Double getObjFun_AA() {
		return objFun_AA;
	}
	public void setObjFun_AA(Double objFun_AA) {
		this.objFun_AA = objFun_AA;
	}
	public Double getObjFun_AM() {
		return objFun_AM;
	}
	public void setObjFun_AM(Double objFun_AM) {
		this.objFun_AM = objFun_AM;
	}
	public Double getObjFun_AB() {
		return objFun_AB;
	}
	public void setObjFun_AB(Double objFun_AB) {
		this.objFun_AB = objFun_AB;
	}
	public Double getObjFun_MA() {
		return objFun_MA;
	}
	public void setObjFun_MA(Double objFun_MA) {
		this.objFun_MA = objFun_MA;
	}
	public Double getObjFun_MM() {
		return objFun_MM;
	}
	public void setObjFun_MM(Double objFun_MM) {
		this.objFun_MM = objFun_MM;
	}
	public Double getObjFun_MB() {
		return objFun_MB;
	}
	public void setObjFun_MB(Double objFun_MB) {
		this.objFun_MB = objFun_MB;
	}
	public Double getObjFun_Bajo() {
		return objFun_Bajo;
	}
	public void setObjFun_Bajo(Double objFun_Bajo) {
		this.objFun_Bajo = objFun_Bajo;
	}
	public Double getObjDes_AAplus() {
		return objDes_AAplus;
	}
	public void setObjDes_AAplus(Double objDes_AAplus) {
		this.objDes_AAplus = objDes_AAplus;
	}
	public Double getObjDes_AA() {
		return objDes_AA;
	}
	public void setObjDes_AA(Double objDes_AA) {
		this.objDes_AA = objDes_AA;
	}
	public Double getObjDes_AM() {
		return objDes_AM;
	}
	public void setObjDes_AM(Double objDes_AM) {
		this.objDes_AM = objDes_AM;
	}
	public Double getObjDes_AB() {
		return objDes_AB;
	}
	public void setObjDes_AB(Double objDes_AB) {
		this.objDes_AB = objDes_AB;
	}
	public Double getObjDes_MA() {
		return objDes_MA;
	}
	public void setObjDes_MA(Double objDes_MA) {
		this.objDes_MA = objDes_MA;
	}
	public Double getObjDes_MM() {
		return objDes_MM;
	}
	public void setObjDes_MM(Double objDes_MM) {
		this.objDes_MM = objDes_MM;
	}
	public Double getObjDes_MB() {
		return objDes_MB;
	}
	public void setObjDes_MB(Double objDes_MB) {
		this.objDes_MB = objDes_MB;
	}
	public Double getObjDes_Bajo() {
		return objDes_Bajo;
	}
	public void setObjDes_Bajo(Double objDes_Bajo) {
		this.objDes_Bajo = objDes_Bajo;
	}
	public String getNivelIngreso() {
		return nivelIngreso;
	}
	public void setNivelIngreso(String nivelIngreso) {
		this.nivelIngreso = nivelIngreso;
	}
	public Double getObjFun_Distribuidor() {
		return objFun_Distribuidor;
	}
	public void setObjFun_Distribuidor(Double objFun_Distribuidor) {
		this.objFun_Distribuidor = objFun_Distribuidor;
	}
	public Double getObjDes_Distribuidor() {
		return objDes_Distribuidor;
	}
	public void setObjDes_Distribuidor(Double objDes_Distribuidor) {
		this.objDes_Distribuidor = objDes_Distribuidor;
	}
	/**
	 * @return the ventaDistribuidor_ant
	 */
	public Double getVentaDistribuidor_ant() {
		return ventaDistribuidor_ant;
	}
	/**
	 * @param ventaDistribuidor_ant the ventaDistribuidor_ant to set
	 */
	public void setVentaDistribuidor_ant(Double ventaDistribuidor_ant) {
		this.ventaDistribuidor_ant = ventaDistribuidor_ant;
	}
	/**
	 * @return the nombreCartera
	 */
	public String getNombreCartera() {
		return nombreCartera;
	}
	/**
	 * @param nombreCartera the nombreCartera to set
	 */
	public void setNombreCartera(String nombreCartera) {
		this.nombreCartera = nombreCartera;
	}
	/**
	 * @return the idcartera
	 */
	public long getIdcartera() {
		return idcartera;
	}
	/**
	 * @param idcartera the idcartera to set
	 */
	public void setIdcartera(long idcartera) {
		this.idcartera = idcartera;
	}
	/**
	 * @return the folioCartera
	 */
	public String getFolioCartera() {
		return folioCartera;
	}
	/**
	 * @param folioCartera the folioCartera to set
	 */
	public void setFolioCartera(String folioCartera) {
		this.folioCartera = folioCartera;
	}
	/**
	 * @return the idEjecutivoVenta
	 */
	public long getIdEjecutivoVenta() {
		return idEjecutivoVenta;
	}
	/**
	 * @param idEjecutivoVenta the idEjecutivoVenta to set
	 */
	public void setIdEjecutivoVenta(long idEjecutivoVenta) {
		this.idEjecutivoVenta = idEjecutivoVenta;
	}
	/**
	 * @return the idEsac
	 */
	public long getIdEsac() {
		return idEsac;
	}
	/**
	 * @param idEsac the idEsac to set
	 */
	public void setIdEsac(long idEsac) {
		this.idEsac = idEsac;
	}
	/**
	 * @return the idCobrador
	 */
	public long getIdCobrador() {
		return idCobrador;
	}
	/**
	 * @param idCobrador the idCobrador to set
	 */
	public void setIdCobrador(long idCobrador) {
		this.idCobrador = idCobrador;
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
	public int getAnio() {
		return anio;
	}
	public void setAnio(int anio) {
		this.anio = anio;
	}
	

	public Integer getCarterasAltos() {
		return carterasAltos;
	}
	public void setCarterasAltos(Integer carterasAltos) {
		this.carterasAltos = carterasAltos;
	}
	public Integer getCarterasMedios() {
		return carterasMedios;
	}
	public void setCarterasMedios(Integer carterasMedios) {
		this.carterasMedios = carterasMedios;
	}
	public Integer getCarterasBajos() {
		return carterasBajos;
	}
	public void setCarterasBajos(Integer carterasBajos) {
		this.carterasBajos = carterasBajos;
	}
	public Integer getCarterasNuevos() {
		return carterasNuevos;
	}
	public void setCarterasNuevos(Integer carterasNuevos) {
		this.carterasNuevos = carterasNuevos;
	}
	public Integer getCarterasDistribuidor() {
		return carterasDistribuidor;
	}
	public void setCarterasDistribuidor(Integer carterasDistribuidor) {
		this.carterasDistribuidor = carterasDistribuidor;
	}
	
	
}
