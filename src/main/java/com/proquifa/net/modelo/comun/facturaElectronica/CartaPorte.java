package com.proquifa.net.modelo.comun.facturaElectronica;

public class CartaPorte {
    /********************Vehiculo********************/
    String permSCT;
    String numPermSCT;
    String configVehicular;
    String placaVM;
    String aseguraRespCivil;
    String polizaRespCivil;
    String anioModelo;
    String idVehiculo;
    /***********************************************/

    /***********InfoMensajero******/
    String rfcMensajero;
    String licencia;
    String mensajero;
    String calleDomicilio;
    String cPostalDomicilio;
    String numExtDomicilio;
    String numInt;

    String responsable;
    int eventos;
    String idMensajero;
    /***********InfoMensajero******/

    /***********CartaPorte datos generales*********/
    String pedimento;
    String distRecorrida;
    String horaSalida;
    String calle;
    String calleDestino;
    String numExtDestino;
    String colonia;
    String municipio;
    String estado;
    String cPostal;
    String Descripcion;
    String cant;
    String cUnidad;
    String unidad;
    String peso;
    String moneda;
    String bienesTrans;
    String folioTimbrado;

    public String getFolioTimbrado() {
        return folioTimbrado;
    }

    public void setFolioTimbrado(String folioTimbrado) {
        this.folioTimbrado = folioTimbrado;
    }

    public String getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(String idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public String getIdMensajero() {
        return idMensajero;
    }

    public void setIdMensajero(String idMensajero) {
        this.idMensajero = idMensajero;
    }

    /***********CartaPorte datos generales*********/






    public CartaPorte() {
        super();
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public int getEventos() {
        return eventos;
    }

    public void setEventos(int eventos) {
        this.eventos = eventos;
    }

    public String getNumExtDomicilio() {
        return this.numExtDomicilio;
    }

    public void setNumExtDomicilio(String numExtDomicilio) {
        this.numExtDomicilio = numExtDomicilio;
    }

    public String getNumExtDestino() {
        return this.numExtDestino;
    }

    public void setNumExtDestino(String numExtDestino) {
        this.numExtDestino = numExtDestino;
    }

    public String getcPostal() {
        return this.cPostal;
    }

    public void setcPostal(String cPostal) {
        this.cPostal = cPostal;
    }

    public String getCalleDestino() {
        return this.calleDestino;
    }

    public void setCalleDestino(String calleDestino) {
        this.calleDestino = calleDestino;
    }

    public String getCalleDomicilio() {
        return this.calleDomicilio;
    }

    public void setCalleDomicilio(String calleDomicilio) {
        this.calleDomicilio = calleDomicilio;
    }

    public String getcPostalDomicilio() {
        return this.cPostalDomicilio;
    }

    public void setcPostalDomicilio(String cPostalDomicilio) {
        this.cPostalDomicilio = cPostalDomicilio;
    }

    public String getBienesTrans() {
        return this.bienesTrans;
    }

    public void setBienesTrans(String bienesTrans) {
        this.bienesTrans = bienesTrans;
    }

    public String getAnioModelo() {
        return this.anioModelo;
    }

    public void setAnioModelo(String anioModelo) {
        this.anioModelo = anioModelo;
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

    public String getAseguraRespCivil() {
        return this.aseguraRespCivil;
    }

    public void setAseguraRespCivil(String aseguraRespCivil) {
        this.aseguraRespCivil = aseguraRespCivil;
    }

    public String getPolizaRespCivil() {
        return this.polizaRespCivil;
    }

    public void setPolizaRespCivil(String polizaRespCivil) {
        this.polizaRespCivil = polizaRespCivil;
    }

    public String getPedimento() {
        return this.pedimento;
    }

    public void setPedimento(String pedimento) {
        this.pedimento = pedimento;
    }

    public String getDistRecorrida() {
        return this.distRecorrida;
    }

    public void setDistRecorrida(String distRecorrida) {
        this.distRecorrida = distRecorrida;
    }

    public String getHoraSalida() {
        return this.horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getPermSCT() {
        return this.permSCT;
    }

    public void setPermSCT(String permSCT) {
        this.permSCT = permSCT;
    }

    public String getNumPermSCT() {
        return this.numPermSCT;
    }

    public void setNumPermSCT(String numPermSCT) {
        this.numPermSCT = numPermSCT;
    }

    public String getCalle() {
        return this.calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }


    public String getColonia() {
        return this.colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getMunicipio() {
        return this.municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return this.Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getCant() {
        return this.cant;
    }

    public void setCant(String cant) {
        this.cant = cant;
    }

    public String getcUnidad() {
        return this.cUnidad;
    }

    public void setcUnidad(String cUnidad) {
        this.cUnidad = cUnidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getPeso() {
        return this.peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getMoneda() {
        return this.moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getRfcMensajero() {
        return this.rfcMensajero;
    }

    public void setRfcMensajero(String rfcMensajero) {
        this.rfcMensajero = rfcMensajero;
    }

    public String getLicencia() {
        return this.licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }

    public String getMensajero() {
        return this.mensajero;
    }

    public void setMensajero(String mensajero) {
        this.mensajero = mensajero;
    }

    public String getNumInt() {
        return this.numInt;
    }

    public void setNumInt(String numInt) {
        this.numInt = numInt;
    }
}
