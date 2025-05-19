package com.proquifa.net.modelo.comun;

public class CarteraCliente extends Cliente {
	private long idCartera;
	private long idCreador;
	private long idIndustria;
	private long cli_idCorporativo;
	private long cli_idEsac;
	private long cli_idEv;
	private long cli_idCobrador;
	private long cli_idEVT;
	private long cli_idMensajero;
	
	private long cart_idEsac;
	private long cart_idEv;
	private long cart_idEVT;
	private long cart_idCobrador;
	private long cart_idMensajero;
	

	private boolean cart_publicada;
	private boolean cart_sistema;
	
	
	private String cart_nombre;
	private String creador;
	private String cli_categoria;
	private String cli_corporativo;
	private String cli_nombreEsac;
	private String cli_nombreEv;
	private String cli_nombreEVT;
	private String cli_nombreCobrador;
	private String cli_nombreMensajero;
	private String cart_nombreEsac;
	private String cart_nombreEv;
	private String cart_nombreEVT;
	private String cart_nombreCobrador;
	private String cart_nombreElaboro;
	private String cart_nombreMensajero;
	
	private double cart_facturacionAct;
	private double cart_facturacionAnt;
	private double cart_objetivoFundamental;
	private double cart_objetivoDeseado;
	private double cart_promedioFacturacion;
	private double cart_proyeccionVenta;
	private double cart_debemos;
	private double cart_deben;
	private double cart_montoDeseado;
	private double cart_montoFundamental;
	
	private double cli_facturacionAct;
	private double cli_facturacionAnt;
	private double cli_objetivoFundamental;
	private double cli_objetivoDeseado;
	private double cli_monto_ObjetivoFundamental;
	private double cli_monto_ObjetivoDeseado;
	private double cli_promedioFacturacion;
	private double cli_proyeccionVenta;
	private double cli_debemos;
	private double cli_deben;
	private double cli_factGlobal;
	
	
	public long getCli_idMensajero() {
		return cli_idMensajero;
	}
	public void setCli_idMensajero(long cli_idMensajero) {
		this.cli_idMensajero = cli_idMensajero;
	}
	public long getCart_idMensajero() {
		return cart_idMensajero;
	}
	public void setCart_idMensajero(long cart_idMensajero) {
		this.cart_idMensajero = cart_idMensajero;
	}
	public String getCli_nombreMensajero() {
		return cli_nombreMensajero;
	}
	public void setCli_nombreMensajero(String cli_nombreMensajero) {
		this.cli_nombreMensajero = cli_nombreMensajero;
	}
	public String getCart_nombreMensajero() {
		return cart_nombreMensajero;
	}
	public void setCart_nombreMensajero(String cart_nombreMensajero) {
		this.cart_nombreMensajero = cart_nombreMensajero;
	}
	private String Area;
	private String justificacion;
	
	private boolean cart_corporativo; 
	private boolean cart_internacional;
	/**
	 * @return the idCartera
	 */
	public long getIdCartera() {
		return idCartera;
	}
	/**
	 * @param idCartera the idCartera to set
	 */
	public void setIdCartera(long idCartera) {
		this.idCartera = idCartera;
	}
	/**
	 * @return the idCreador
	 */
	public long getIdCreador() {
		return idCreador;
	}
	/**
	 * @param idCreador the idCreador to set
	 */
	public void setIdCreador(long idCreador) {
		this.idCreador = idCreador;
	}
	/**
	 * @return the idIndustria
	 */
	public long getIdIndustria() {
		return idIndustria;
	}
	/**
	 * @param idIndustria the idIndustria to set
	 */
	public void setIdIndustria(long idIndustria) {
		this.idIndustria = idIndustria;
	}
	/**
	 * @return the cli_idCorporativo
	 */
	public long getCli_idCorporativo() {
		return cli_idCorporativo;
	}
	/**
	 * @param cli_idCorporativo the cli_idCorporativo to set
	 */
	public void setCli_idCorporativo(long cli_idCorporativo) {
		this.cli_idCorporativo = cli_idCorporativo;
	}
	/**
	 * @return the cli_idEsac
	 */
	public long getCli_idEsac() {
		return cli_idEsac;
	}
	/**
	 * @param cli_idEsac the cli_idEsac to set
	 */
	public void setCli_idEsac(long cli_idEsac) {
		this.cli_idEsac = cli_idEsac;
	}
	/**
	 * @return the cli_idEv
	 */
	public long getCli_idEv() {
		return cli_idEv;
	}
	/**
	 * @param cli_idEv the cli_idEv to set
	 */
	public void setCli_idEv(long cli_idEv) {
		this.cli_idEv = cli_idEv;
	}
	/**
	 * @return the cart_idEsac
	 */
	public long getCart_idEsac() {
		return cart_idEsac;
	}
	/**
	 * @param cart_idEsac the cart_idEsac to set
	 */
	public void setCart_idEsac(long cart_idEsac) {
		this.cart_idEsac = cart_idEsac;
	}
	/**
	 * @return the cart_idEv
	 */
	public long getCart_idEv() {
		return cart_idEv;
	}
	/**
	 * @param cart_idEV the cart_idEv to set
	 */
	public void setCart_idEv(long cart_idEv) {
		this.cart_idEv = cart_idEv;
	}
	/**
	 * @return the cart_idCobrador
	 */
	public long getCart_idCobrador() {
		return cart_idCobrador;
	}
	/**
	 * @param cart_idCobrador the cart_idCobrador to set
	 */
	public void setCart_idCobrador(long cart_idCobrador) {
		this.cart_idCobrador = cart_idCobrador;
	}
	/**
	 * @return the cart_publicada
	 */
	public boolean isCart_publicada() {
		return cart_publicada;
	}
	/**
	 * @return the cart_sistema
	 */
	public boolean isCart_sistema() {
		return cart_sistema;
	}
	/**
	 * @return the cart_nombre
	 */
	public String getCart_nombre() {
		return cart_nombre;
	}
	/**
	 * @param cart_nombre the cart_nombre to set
	 */
	public void setCart_nombre(String cart_nombre) {
		this.cart_nombre = cart_nombre;
	}
	/**
	 * @return the creador
	 */
	public String getCreador() {
		return creador;
	}
	/**
	 * @param creador the creador to set
	 */
	public void setCreador(String creador) {
		this.creador = creador;
	}
	/**
	 * @return the cli_categoria
	 */
	public String getCli_categoria() {
		return cli_categoria;
	}
	/**
	 * @param cli_categoria the cli_categoria to set
	 */
	public void setCli_categoria(String cli_categoria) {
		this.cli_categoria = cli_categoria;
	}
	/**
	 * @return the cli_corporativo
	 */
	public String getCli_corporativo() {
		return cli_corporativo;
	}
	/**
	 * @param cli_corporativo the cli_corporativo to set
	 */
	public void setCli_corporativo(String cli_corporativo) {
		this.cli_corporativo = cli_corporativo;
	}
	/**
	 * @return the cart_nombreEsac
	 */
	public String getCart_nombreEsac() {
		return cart_nombreEsac;
	}
	/**
	 * @param cart_nombreEsac the cart_nombreEsac to set
	 */
	public void setCart_nombreEsac(String cart_nombreEsac) {
		this.cart_nombreEsac = cart_nombreEsac;
	}
	/**
	 * @return the cart_nombreEv
	 */
	public String getCart_nombreEv() {
		return cart_nombreEv;
	}
	/**
	 * @param cart_nombreEv the cart_nombreEv to set
	 */
	public void setCart_nombreEv(String cart_nombreEv) {
		this.cart_nombreEv = cart_nombreEv;
	}
	/**
	 * @return the cart_nombreCobrador
	 */
	public String getCart_nombreCobrador() {
		return cart_nombreCobrador;
	}
	/**
	 * @param cart_nombreCobrador the cart_nombreCobrador to set
	 */
	public void setCart_nombreCobrador(String cart_nombreCobrador) {
		this.cart_nombreCobrador = cart_nombreCobrador;
	}
	/**
	 * @return the cart_facturacionAct
	 */
	public double getCart_facturacionAct() {
		return cart_facturacionAct;
	}
	/**
	 * @param cart_facturacionAct the cart_facturacionAct to set
	 */
	public void setCart_facturacionAct(double cart_facturacionAct) {
		this.cart_facturacionAct = cart_facturacionAct;
	}
	/**
	 * @return the cart_facturacionAnt
	 */
	public double getCart_facturacionAnt() {
		return cart_facturacionAnt;
	}
	/**
	 * @param cart_facturacionAnt the cart_facturacionAnt to set
	 */
	public void setCart_facturacionAnt(double cart_facturacionAnt) {
		this.cart_facturacionAnt = cart_facturacionAnt;
	}
	/**
	 * @return the cart_objetivoFundamental
	 */
	public double getCart_objetivoFundamental() {
		return cart_objetivoFundamental;
	}
	/**
	 * @param cart_objetivoFundamental the cart_objetivoFundamental to set
	 */
	public void setCart_objetivoFundamental(double cart_objetivoFundamental) {
		this.cart_objetivoFundamental = cart_objetivoFundamental;
	}
	/**
	 * @return the cart_objetivoDeseado
	 */
	public double getCart_objetivoDeseado() {
		return cart_objetivoDeseado;
	}
	/**
	 * @param cart_objetivoDeseado the cart_objetivoDeseado to set
	 */
	public void setCart_objetivoDeseado(double cart_objetivoDeseado) {
		this.cart_objetivoDeseado = cart_objetivoDeseado;
	}
	/**
	 * @return the cart_promedioFacturacion
	 */
	public double getCart_promedioFacturacion() {
		return cart_promedioFacturacion;
	}
	/**
	 * @param cart_promedioFacturacion the cart_promedioFacturacion to set
	 */
	public void setCart_promedioFacturacion(double cart_promedioFacturacion) {
		this.cart_promedioFacturacion = cart_promedioFacturacion;
	}
	/**
	 * @return the cart_proyeccionVenta
	 */
	public double getCart_proyeccionVenta() {
		return cart_proyeccionVenta;
	}
	/**
	 * @param cart_proyeccionVenta the cart_proyeccionVenta to set
	 */
	public void setCart_proyeccionVenta(double cart_proyeccionVenta) {
		this.cart_proyeccionVenta = cart_proyeccionVenta;
	}
	/**
	 * @return the cart_debemos
	 */
	public double getCart_debemos() {
		return cart_debemos;
	}
	/**
	 * @param cart_debemos the cart_debemos to set
	 */
	public void setCart_debemos(double cart_debemos) {
		this.cart_debemos = cart_debemos;
	}
	/**
	 * @return the cart_deben
	 */
	public double getCart_deben() {
		return cart_deben;
	}
	/**
	 * @param cart_deben the cart_deben to set
	 */
	public void setCart_deben(double cart_deben) {
		this.cart_deben = cart_deben;
	}
	/**
	 * @return the cart_montoDeseado
	 */
	public double getCart_montoDeseado() {
		return cart_montoDeseado;
	}
	/**
	 * @param cart_montoDeseado the cart_montoDeseado to set
	 */
	public void setCart_montoDeseado(double cart_montoDeseado) {
		this.cart_montoDeseado = cart_montoDeseado;
	}
	/**
	 * @return the cart_montoFundamental
	 */
	public double getCart_montoFundamental() {
		return cart_montoFundamental;
	}
	/**
	 * @param cart_montoFundamental the cart_montoFundamental to set
	 */
	public void setCart_montoFundamental(double cart_montoFundamental) {
		this.cart_montoFundamental = cart_montoFundamental;
	}
	/**
	 * @return the cli_facturacionAct
	 */
	public double getCli_facturacionAct() {
		return cli_facturacionAct;
	}
	/**
	 * @param cli_facturacionAct the cli_facturacionAct to set
	 */
	public void setCli_facturacionAct(double cli_facturacionAct) {
		this.cli_facturacionAct = cli_facturacionAct;
	}
	/**
	 * @return the cli_facturacionAnt
	 */
	public double getCli_facturacionAnt() {
		return cli_facturacionAnt;
	}
	/**
	 * @param cli_facturacionAnt the cli_facturacionAnt to set
	 */
	public void setCli_facturacionAnt(double cli_facturacionAnt) {
		this.cli_facturacionAnt = cli_facturacionAnt;
	}
	/**
	 * @return the cli_objetivoFundamental
	 */
	public double getCli_objetivoFundamental() {
		return cli_objetivoFundamental;
	}
	/**
	 * @param cli_objetivoFundamental the cli_objetivoFundamental to set
	 */
	public void setCli_objetivoFundamental(double cli_objetivoFundamental) {
		this.cli_objetivoFundamental = cli_objetivoFundamental;
	}
	/**
	 * @return the cli_objetivoDeseado
	 */
	public double getCli_objetivoDeseado() {
		return cli_objetivoDeseado;
	}
	/**
	 * @param cli_objetivoDeseado the cli_objetivoDeseado to set
	 */
	public void setCli_objetivoDeseado(double cli_objetivoDeseado) {
		this.cli_objetivoDeseado = cli_objetivoDeseado;
	}
	/**
	 * @return the cli_monto_ObjetivoFundamental
	 */
	public double getCli_monto_ObjetivoFundamental() {
		return cli_monto_ObjetivoFundamental;
	}
	/**
	 * @param cli_monto_ObjetivoFundamental the cli_monto_ObjetivoFundamental to set
	 */
	public void setCli_monto_ObjetivoFundamental(
			double cli_monto_ObjetivoFundamental) {
		this.cli_monto_ObjetivoFundamental = cli_monto_ObjetivoFundamental;
	}
	/**
	 * @return the cli_monto_ObjetivoDeseado
	 */
	public double getCli_monto_ObjetivoDeseado() {
		return cli_monto_ObjetivoDeseado;
	}
	/**
	 * @param cli_monto_ObjetivoDeseado the cli_monto_ObjetivoDeseado to set
	 */
	public void setCli_monto_ObjetivoDeseado(double cli_monto_ObjetivoDeseado) {
		this.cli_monto_ObjetivoDeseado = cli_monto_ObjetivoDeseado;
	}
	/**
	 * @return the cli_promedioFacturacion
	 */
	public double getCli_promedioFacturacion() {
		return cli_promedioFacturacion;
	}
	/**
	 * @param cli_promedioFacturacion the cli_promedioFacturacion to set
	 */
	public void setCli_promedioFacturacion(double cli_promedioFacturacion) {
		this.cli_promedioFacturacion = cli_promedioFacturacion;
	}
	/**
	 * @return the cli_proyeccionVenta
	 */
	public double getCli_proyeccionVenta() {
		return cli_proyeccionVenta;
	}
	/**
	 * @param cli_proyeccionVenta the cli_proyeccionVenta to set
	 */
	public void setCli_proyeccionVenta(double cli_proyeccionVenta) {
		this.cli_proyeccionVenta = cli_proyeccionVenta;
	}
	/**
	 * @return the cli_debemos
	 */
	public double getCli_debemos() {
		return cli_debemos;
	}
	/**
	 * @param cli_debemos the cli_debemos to set
	 */
	public void setCli_debemos(double cli_debemos) {
		this.cli_debemos = cli_debemos;
	}
	/**
	 * @return the cli_deben
	 */
	public double getCli_deben() {
		return cli_deben;
	}
	/**
	 * @param cli_deben the cli_deben to set
	 */
	public void setCli_deben(double cli_deben) {
		this.cli_deben = cli_deben;
	}
	/**
	 * @return the cli_factGlobal
	 */
	public double getCli_factGlobal() {
		return cli_factGlobal;
	}
	/**
	 * @param cli_factGlobal the cli_factGlobal to set
	 */
	public void setCli_factGlobal(double cli_factGlobal) {
		this.cli_factGlobal = cli_factGlobal;
	}
	/**
	 * @return the cli_nombreEsac
	 */
	public String getCli_nombreEsac() {
		return cli_nombreEsac;
	}
	/**
	 * @param cli_nombreEsac the cli_nombreEsac to set
	 */
	public void setCli_nombreEsac(String cli_nombreEsac) {
		this.cli_nombreEsac = cli_nombreEsac;
	}
	/**
	 * @return the cli_nombreEv
	 */
	public String getCli_nombreEv() {
		return cli_nombreEv;
	}
	/**
	 * @param cli_nombreEv the cli_nombreEv to set
	 */
	public void setCli_nombreEv(String cli_nombreEv) {
		this.cli_nombreEv = cli_nombreEv;
	}
	/**
	 * @return the cli_nombreCobrador
	 */
	public String getCli_nombreCobrador() {
		return cli_nombreCobrador;
	}
	/**
	 * @param cli_nombreCobrador the cli_nombreCobrador to set
	 */
	public void setCli_nombreCobrador(String cli_nombreCobrador) {
		this.cli_nombreCobrador = cli_nombreCobrador;
	}
	/**
	 * @return the cli_idCobrador
	 */
	public long getCli_idCobrador() {
		return cli_idCobrador;
	}
	/**
	 * @param cli_idCobrador the cli_idCobrador to set
	 */
	public void setCli_idCobrador(long cli_idCobrador) {
		this.cli_idCobrador = cli_idCobrador;
	}
	/**
	 * @param cart_publicada the cart_publicada to set
	 */
	public void setCart_publicada(boolean cart_publicada) {
		this.cart_publicada = cart_publicada;
	}
	/**
	 * @param cart_sistema the cart_sistema to set
	 */
	public void setCart_sistema(boolean cart_sistema) {
		this.cart_sistema = cart_sistema;
	}
	/**
	 * @return the cart_publicada
	 */
	public boolean getCart_publicada () {
		return cart_publicada;
	}
	/**
	 * @return the cart_sistema
	 */
	public boolean getCart_sistema() {
		return cart_sistema;
	}
	/**
	 * @return the cart_corporativo
	 */
	public boolean isCart_corporativo() {
		return cart_corporativo;
	}
	/**
	 * @param cart_corporativo the cart_corporativo to set
	 */
	public void setCart_corporativo(boolean cart_corporativo) {
		this.cart_corporativo = cart_corporativo;
	}
	/**
	 * @return the cart_internacional
	 */
	public boolean isCart_internacional() {
		return cart_internacional;
	}
	/**
	 * @param cart_internacional the cart_internacional to set
	 */
	public void setCart_internacional(boolean cart_internacional) {
		this.cart_internacional = cart_internacional;
	}
	/**
	 * @return the cart_nombreElaboro
	 */
	public String getCart_nombreElaboro() {
		return cart_nombreElaboro;
	}
	/**
	 * @param cart_nombreElaboro the cart_nombreElaboro to set
	 */
	public void setCart_nombreElaboro(String cart_nombreElaboro) {
		this.cart_nombreElaboro = cart_nombreElaboro;
	}
	public String getArea() {
		return Area;
	}
	public void setArea(String area) {
		Area = area;
	}
	public String getJustificacion() {
		return justificacion;
	}
	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}
	/**
	 * @return the cli_idEVT
	 */
	public long getCli_idEVT() {
		return cli_idEVT;
	}
	/**
	 * @param cli_idEVT the cli_idEVT to set
	 */
	public void setCli_idEVT(long cli_idEVT) {
		this.cli_idEVT = cli_idEVT;
	}
	/**
	 * @return the cart_idEVT
	 */
	public long getCart_idEVT() {
		return cart_idEVT;
	}
	/**
	 * @param cart_idEVT the cart_idEVT to set
	 */
	public void setCart_idEVT(long cart_idEVT) {
		this.cart_idEVT = cart_idEVT;
	}
	/**
	 * @return the cart_nombreEVT
	 */
	public String getCart_nombreEVT() {
		return cart_nombreEVT;
	}
	/**
	 * @param cart_nombreEVT the cart_nombreEVT to set
	 */
	public void setCart_nombreEVT(String cart_nombreEVT) {
		this.cart_nombreEVT = cart_nombreEVT;
	}
	/**
	 * @return the cli_nombreEVT
	 */
	public String getCli_nombreEVT() {
		return cli_nombreEVT;
	}
	/**
	 * @param cli_nombreEVT the cli_nombreEVT to set
	 */
	public void setCli_nombreEVT(String cli_nombreEVT) {
		this.cli_nombreEVT = cli_nombreEVT;
	}
	
}
