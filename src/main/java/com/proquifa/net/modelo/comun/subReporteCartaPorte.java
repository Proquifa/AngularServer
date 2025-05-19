package com.proquifa.net.modelo.comun;

import com.proquifa.net.modelo.comun.facturaElectronica.PFacturaElectronica;

import java.util.List;

public class subReporteCartaPorte {
    private String rfcEmisor;
    private String dateOrigen;
    private String domOrigen;

    private String rfcReceptor;
    private String dateDestination;
    private String distance;
    private String domDestination;

    private String weight;
    private String unity;
    private String netWeight;
    private String totalItems;

    private List<PFacturaElectronica> listProds;

   private String SCT;
   private String numPerm;
   private String configVehicular;
   private String placaVM;
   private String anio;
   private String aseguradora;
   private String poliza;

   private String rfcMensjaero;
   private String licencia;
   private String nombreMensajero;
   private String domMensajero;
   private String edomBlanco;
   private String feum;
   private String british;
   private String usp;
   private String micro;
   private String apacor;
   private String chata;
   private String pharma;

    public String getEdomBlanco() {
        return edomBlanco;
    }

    public void setEdomBlanco(String edomBlanco) {
        this.edomBlanco = edomBlanco;
    }

    public String getFeum() {
        return feum;
    }

    public void setFeum(String feum) {
        this.feum = feum;
    }

    public String getBritish() {
        return british;
    }

    public void setBritish(String british) {
        this.british = british;
    }

    public String getUsp() {
        return usp;
    }

    public void setUsp(String usp) {
        this.usp = usp;
    }

    public String getMicro() {
        return micro;
    }

    public void setMicro(String micro) {
        this.micro = micro;
    }

    public String getApacor() {
        return apacor;
    }

    public void setApacor(String apacor) {
        this.apacor = apacor;
    }

    public String getChata() {
        return chata;
    }

    public void setChata(String chata) {
        this.chata = chata;
    }

    public String getPharma() {
        return pharma;
    }

    public void setPharma(String pharma) {
        this.pharma = pharma;
    }

    public subReporteCartaPorte(String rfcEmisor, String dateOrigen, String domOrigen, String rfcReceptor, String dateDestination, String distance,
                                String domDestination, String weight, String unity, String netWeight, String totalItems, List<PFacturaElectronica> listProds,
                                String SCT, String numPerm, String configVehicular, String placaVM, String anio, String aseguradora, String poliza,
                                String rfcMensjaero, String licencia, String nombreMensajero, String domMensajero,String edomBlanco,String apacor,
                                String british,String chata,String usp,String feum,String micro,String pharma) {
        this.rfcEmisor = rfcEmisor;
        this.dateOrigen = dateOrigen;
        this.domOrigen = domOrigen;
        this.rfcReceptor = rfcReceptor;
        this.dateDestination = dateDestination;
        this.distance = distance;
        this.domDestination = domDestination;
        this.weight = weight;
        this.unity = unity;
        this.netWeight = netWeight;
        this.totalItems = totalItems;
        this.listProds=listProds;
        this.SCT = SCT;
        this.numPerm = numPerm;
        this.configVehicular = configVehicular;
        this.placaVM = placaVM;
        this.anio = anio;
        this.aseguradora = aseguradora;
        this.poliza = poliza;
        this.rfcMensjaero = rfcMensjaero;
        this.licencia = licencia;
        this.nombreMensajero = nombreMensajero;
        this.domMensajero = domMensajero;
        this.edomBlanco = edomBlanco;
        this.apacor=apacor;
        this.british=british;
        this.chata=chata;
        this.usp=usp;
        this.feum=feum;
        this.micro=micro;
        this.pharma=pharma;

    }

    public String getRfcEmisor() {
        return this.rfcEmisor;
    }

    public void setRfcEmisor(String rfcEmisor) {
        this.rfcEmisor = rfcEmisor;
    }

    public String getDateOrigen() {
        return this.dateOrigen;
    }

    public void setDateOrigen(String dateOrigen) {
        this.dateOrigen = dateOrigen;
    }

    public String getDomOrigen() {
        return this.domOrigen;
    }

    public void setDomOrigen(String domOrigen) {
        this.domOrigen = domOrigen;
    }

    public String getRfcReceptor() {
        return this.rfcReceptor;
    }

    public void setRfcReceptor(String rfcReceptor) {
        this.rfcReceptor = rfcReceptor;
    }

    public String getDateDestination() {
        return this.dateDestination;
    }

    public void setDateDestination(String dateDestination) {
        this.dateDestination = dateDestination;
    }

    public String getDistance() {
        return this.distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDomDestination() {
        return this.domDestination;
    }

    public void setDomDestination(String domDestination) {
        this.domDestination = domDestination;
    }

    public String getWeight() {
        return this.weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getUnity() {
        return this.unity;
    }

    public void setUnity(String unity) {
        this.unity = unity;
    }

    public String getNetWeight() {
        return this.netWeight;
    }

    public void setNetWeight(String netWeight) {
        this.netWeight = netWeight;
    }

    public String getTotalItems() {
        return this.totalItems;
    }

    public void setTotalItems(String totalItems) {
        this.totalItems = totalItems;
    }



    public String getSCT() {
        return this.SCT;
    }

    public void setSCT(String SCT) {
        this.SCT = SCT;
    }

    public String getNumPerm() {
        return this.numPerm;
    }

    public void setNumPerm(String numPerm) {
        this.numPerm = numPerm;
    }

    public String getConfigVehicular() {
        return this.configVehicular;
    }

    public void setConfigVehicular(String configVehicular) {
        this.configVehicular = configVehicular;
    }

    public String getPlacaVM() {
        return this.placaVM;
    }

    public void setPlacaVM(String placaVM) {
        this.placaVM = placaVM;
    }

    public String getAnio() {
        return this.anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getAseguradora() {
        return this.aseguradora;
    }

    public void setAseguradora(String aseguradora) {
        this.aseguradora = aseguradora;
    }

    public String getPoliza() {
        return this.poliza;
    }

    public void setPoliza(String poliza) {
        this.poliza = poliza;
    }

    public String getRfcMensjaero() {
        return this.rfcMensjaero;
    }

    public void setRfcMensjaero(String rfcMensjaero) {
        this.rfcMensjaero = rfcMensjaero;
    }

    public String getLicencia() {
        return this.licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }

    public String getNombreMensajero() {
        return this.nombreMensajero;
    }

    public void setNombreMensajero(String nombreMensajero) {
        this.nombreMensajero = nombreMensajero;
    }

    public String getDomMensajero() {
        return this.domMensajero;
    }

    public void setDomMensajero(String domMensajero) {
        this.domMensajero = domMensajero;
    }

    public List<PFacturaElectronica> getListProds() {
        return this. listProds;
    }

    public void setListProds(List<PFacturaElectronica> listProds) {
        this.listProds = listProds;
    }
}
