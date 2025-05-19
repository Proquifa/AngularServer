package com.proquifa.net.modelo.comun.facturaElectronica;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
public class CartaPorteXML {
    Document cfdi;
    public CartaPorteXML() {
        try {
            cfdi = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public Document getCfdi(){
        try {
            cfdi = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        }catch(Exception e){
            e.printStackTrace();
        }
        return cfdi;
    }


    public  Element crearInformacionAduanera(String Pedimento){
        Element infoAduanera= cfdi.createElement("NumeroPedimento");
        infoAduanera.setAttribute("NumeroPedimento:",Pedimento);
        return  infoAduanera;
    }

    public Element crearCartaPorte(String distRecorrida){
        Element cartaPorte = cfdi.createElement("cartaporte20:CartaPorte");
        cartaPorte.setAttribute("Version","2.0");
        cartaPorte.setAttribute("TranspInternac","No");
        cartaPorte.setAttribute("TotalDistRec", distRecorrida);
        return cartaPorte;
    }

    public Element crearUbicaciones (){
        Element ubicaciones= cfdi.createElement("cartaporte20:Ubicaciones");
        return ubicaciones;
    }
    public Element crearUbicacion(String RFCRemitente,String horaSalida,String calle,String numExt,String colonia,String municipio,String estado,String cp,String distRecorrida,int tipoUbicacion){
        Element ubicacion= cfdi.createElement("cartaporte20:Ubicacion");
            Element domicilio = cfdi.createElement("cartaporte20:Domicilio");
             if(tipoUbicacion==0) {
                 ubicacion.setAttribute("TipoUbicacion","Origen");
                 ubicacion.setAttribute("FechaHoraSalidaLlegada",horaSalida);
                 ubicacion.setAttribute("RFCRemitenteDestinatario","PQF910416FB3");

                 domicilio.setAttribute("calle", "JOSE MARIA MORELOS");
                 domicilio.setAttribute("NumeroExterior", "164");
                 domicilio.setAttribute("Colonia", "2052");
                 domicilio.setAttribute("Municipio", "012");
                 domicilio.setAttribute("Estado", "CMX");
                 domicilio.setAttribute("CodigoPostal", "14080");
             }else{
                 ubicacion.setAttribute("TipoUbicacion","Destino");
                 ubicacion.setAttribute("RFCRemitenteDestinatario",RFCRemitente);
                 ubicacion.setAttribute("FechaHoraSalidaLlegada","T18:00:00");
                 ubicacion.setAttribute("DistanciaRecorrida",distRecorrida);

                 domicilio.setAttribute("calle", calle);
                 domicilio.setAttribute("NumeroExterior", numExt);
                 domicilio.setAttribute("Colonia", colonia);
                 domicilio.setAttribute("Municipio", municipio);
                 domicilio.setAttribute("Estado", estado);
                 domicilio.setAttribute("CodigoPostal", cp);

             }
        domicilio.setAttribute("Pais", "MEX");

        ubicacion.appendChild(domicilio);
        return ubicacion;
    }
    public Element crearMercancias (){
        Element mercancias = cfdi.createElement("cartaporte20:Mercancias");
        mercancias.setAttribute("PesoBrutoTotal","");
        mercancias.setAttribute("UnidadPeso","KGM");
        mercancias.setAttribute("PesoNetoTotal","");
        mercancias.setAttribute("NumTotalMercancias","");
        return mercancias;
    }
    public Element crearMercancia (String descripcion,String cant,String cUnidad,String unidad,String peso,String moneda){
        Element mercancia = cfdi.createElement("cartaporte20:Mercancia");
        mercancia.setAttribute("BienesTransp","");
        mercancia.setAttribute("Descripcion",descripcion);
        mercancia.setAttribute("cantidad",cant);
        mercancia.setAttribute("ClaveUnidad",cUnidad);
        mercancia.setAttribute("Unidad",unidad);
        mercancia.setAttribute("PesoEnKg",peso);
        mercancia.setAttribute("Moneda",moneda);
        return mercancia;
    }
    public Element crearAutoTransporte(String permSCT,String numPermSCT){
        Element autoTransporte= cfdi.createElement("cartaporte20:AutoTransporte");
        Element identificacionVehicular =cfdi.createElement("cartaporte20:IdentificacionVehicular");
        Element seguros=cfdi.createElement("cartaporte20:Seguros");
        autoTransporte.setAttribute("PermSCT",permSCT);
        autoTransporte.setAttribute("NumPermisoSCT",numPermSCT);
        identificacionVehicular.setAttribute("ConfigVehicular","");
        identificacionVehicular.setAttribute("PlacaVM","");
        identificacionVehicular.setAttribute("AnioModelo","");
        autoTransporte.appendChild(identificacionVehicular);
        seguros.setAttribute("AseguraRespCivil","");
        seguros.setAttribute("PolizaRespCivil","");
        autoTransporte.appendChild(seguros);
        return autoTransporte;
    }
    public Element crearFiguraTransporte(String rfcMensajero,String licencia,String nombreMensajero,String numExt,String numInt){
        Element figuraTransporte = cfdi.createElement("cartaaporte20:FiguraTransporte");
        Element tiposFigura =cfdi.createElement("cartaporte20:TiposFigura");
        Element domicilio =cfdi.createElement("cartaporte20:Domicilio");
        tiposFigura.setAttribute("TipoFigura","");
        tiposFigura.setAttribute("RFCFigura",rfcMensajero);
        tiposFigura.setAttribute("NumLicencia",licencia);
        tiposFigura.setAttribute("NombreFigura",nombreMensajero);

        domicilio.setAttribute("Calle","");
        domicilio.setAttribute("NumeroExterior",numExt);
        domicilio.setAttribute("NumeroInterior",numInt);
        domicilio.setAttribute("Estado","CMX");
        domicilio.setAttribute("Pais","MX");
        domicilio.setAttribute("CodigoPostal","14080");

        tiposFigura.appendChild(domicilio);
        figuraTransporte.appendChild(tiposFigura);
        return figuraTransporte;
    }
}
