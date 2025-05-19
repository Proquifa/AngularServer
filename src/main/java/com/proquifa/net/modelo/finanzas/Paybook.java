package com.proquifa.net.modelo.finanzas;

import java.io.Serializable;
import java.util.Date;

public class Paybook implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7800365763226048244L;
	private String idTransaction;
	private String idUser;
	private String idExternal;
	private String idSite;
	private String idSiteOrganization;
	private String idSiteOrganizationType;
	private String idAccount;
	private String idAccountType;
	private boolean isDisable;
	private String description;
	private Double amount;
	private long dtTransaction;
	private long dtRefresh;
	private String reference;
	private String taxIdExtra;
	private String captionExtra;
	private int orderExtra;
	private Date date;
	private Boolean activo;
	private int idRegistroDiario;
	private String currency;
	private Account account;
	
	/**
	 * 
	 * @return
	 */
	public String getIdTransaction() {
		return idTransaction;
	}
	
	/**
	 * 
	 * @param idTransaction
	 */
	public void setIdTransaction(String idTransaction) {
		this.idTransaction = idTransaction;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getIdUser() {
		return idUser;
	}
	
	/**
	 * 
	 * @param idUser
	 */
	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getIdExternal() {
		return idExternal;
	}
	
	/**
	 * 
	 * @param idExternal
	 */
	public void setIdExternal(String idExternal) {
		this.idExternal = idExternal;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getIdSite() {
		return idSite;
	}
	
	/**
	 * 
	 * @param idSite
	 */
	public void setIdSite(String idSite) {
		this.idSite = idSite;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getIdSiteOrganization() {
		return idSiteOrganization;
	}
	
	/**
	 * 
	 * @param idSiteOrganization
	 */
	public void setIdSiteOrganization(String idSiteOrganization) {
		this.idSiteOrganization = idSiteOrganization;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getIdSiteOrganizationType() {
		return idSiteOrganizationType;
	}
	
	/**
	 * 
	 * @param idSiteOrganizationType
	 */
	public void setIdSiteOrganizationType(String idSiteOrganizationType) {
		this.idSiteOrganizationType = idSiteOrganizationType;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getIdAccount() {
		return idAccount;
	}
	
	/**
	 * 
	 * @param idAccount
	 */
	public void setIdAccount(String idAccount) {
		this.idAccount = idAccount;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getIdAccountType() {
		return idAccountType;
	}
	
	/**
	 * 
	 * @param idAccountType
	 */
	public void setIdAccountType(String idAccountType) {
		this.idAccountType = idAccountType;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isDisable() {
		return isDisable;
	}
	
	/**
	 * 
	 * @param isDisable
	 */
	public void setDisable(boolean isDisable) {
		this.isDisable = isDisable;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * 
	 * @return
	 */
	public Double getAmount() {
		return amount;
	}
	
	/**
	 * 
	 * @param amount
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	/**
	 * 
	 * @return
	 */
	public long getDtTransaction() {
		return dtTransaction;
	}
	
	/**
	 * 
	 * @param dtTransaction
	 */
	public void setDtTransaction(long dtTransaction) {
		this.dtTransaction = dtTransaction;
	}
	
	/**
	 * 
	 * @return
	 */
	public long getDtRefresh() {
		return dtRefresh;
	}
	
	/**
	 * 
	 * @param dtRefresch
	 */
	public void setDtRefresh(long dtRefresh) {
		this.dtRefresh = dtRefresh;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getReference() {
		return reference;
	}
	
	/**
	 * 
	 * @param reference
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getTaxIdExtra() {
		return taxIdExtra;
	}
	
	/**
	 * 
	 * @param taxIdExtra
	 */
	public void setTaxIdExtra(String taxIdExtra) {
		this.taxIdExtra = taxIdExtra;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getCaptionExtra() {
		return captionExtra;
	}
	
	/**
	 * 
	 * @param captionExtra
	 */
	public void setCaptionExtra(String captionExtra) {
		this.captionExtra = captionExtra;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getOrderExtra() {
		return orderExtra;
	}
	
	/**
	 * 
	 * @param orderExtra
	 */
	public void setOrderExtra(int orderExtra) {
		this.orderExtra = orderExtra;
	}
	
	/**
	 * 
	 * @return
	 */
	public Date getDate() {
		return date;
	}
	
	/**
	 * 
	 * @param date
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	
	/**
	 * 
	 * @return
	 */
	public Boolean getActivo() {
		return activo;
	}
	
	/**
	 * 
	 * @param activo
	 */
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getIdRegistroDiario() {
		return idRegistroDiario;
	}
	
	/**
	 * 
	 * @param idRegistroDiario
	 */
	public void setIdRegistroDiario(int idRegistroDiario) {
		this.idRegistroDiario = idRegistroDiario;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getCurrency() {
		return currency;
	}
	
	/**
	 * 
	 * @param currency
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the account
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(Account account) {
		this.account = account;
	}

}
