package com.proquifa.net.modelo.cobrosypagos.cfdi;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TokenTurboPac {
   private  String Access_Token;
   private String Type_Token;
   private String Expires_In;

    public ArrayList getErrores() {
        return Errores;
    }

    public void setErrores(ArrayList errores) {
        Errores = errores;
    }

    private ArrayList Errores;
    public String getAccess_Token() {
        return Access_Token;
    }

    public void setAccess_Token(String access_Token) {
        Access_Token = access_Token;
    }

    public String getType_Token() {
        return Type_Token;
    }

    public void setType_Token(String type_Token) {
        Type_Token = type_Token;
    }

    public String getExpires_In() {
        return Expires_In;
    }

    public void setExpires_In(String expires_In) {
        Expires_In = expires_In;
    }

    public String getCliente() {
        return Cliente;
    }

    public void setCliente(String cliente) {
        Cliente = cliente;
    }

    public String getStart_In() {
        return Start_In;
    }

    public void setStart_In(String start_In) {
        Start_In = start_In;
    }

    private String Cliente;
   private String Start_In;


}
