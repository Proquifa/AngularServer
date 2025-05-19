package com.proquifa.net.modelo.cobrosypagos.cfdi;

import TurboPac.ArrayOfstring;
import io.swagger.models.auth.In;

import java.util.ArrayList;

public class FacturaTurboPac {
    String XMLTimbrado;
    String UUID;
    String SelloDigital;
    String FechaSello;
    Integer Incidencias;
    ArrayList<String> Errores;
    public ArrayList<String> getErrores() {
        return Errores;
    }

    public void setErrores(ArrayList<String> errores) {
        Errores = errores;
    }


    public Integer getIncidencias() {
        return Incidencias;
    }

    public void setIncidencias(Integer incidencias) {
        Incidencias = incidencias;
    }


    public String getXMLTimbrado() {
        return XMLTimbrado;
    }

    public void setXMLTimbrado(String XMLTimbrado) {
        this.XMLTimbrado = XMLTimbrado;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getSelloDigital() {
        return SelloDigital;
    }

    public void setSelloDigital(String selloDigital) {
        SelloDigital = selloDigital;
    }

    public String getFechaSello() {
        return FechaSello;
    }

    public void setFechaSello(String fechaSello) {
        FechaSello = fechaSello;
    }

}
