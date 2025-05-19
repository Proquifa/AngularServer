package com.proquifa.net.negocio.comun;

import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.Producto;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.facturaElectronica.CartaPorte;
import com.proquifa.net.modelo.comun.facturaElectronica.FacturaElectronica;
import com.proquifa.net.modelo.comun.facturaElectronica.PFacturaElectronica;
import net.sf.jasperreports.engine.JRException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

public interface CartaPorteService {
    public Document generarXMLCartaPorte(FacturaElectronica cartaPorte) throws IOException, TransformerException;
    public List<CartaPorte> obtenerPendientesMensajero();
    public List <CartaPorte>obtenerInfoMensajero();
    public List <CartaPorte>obtenerVehiculos();
    public List<Cliente> obtenerClientesPorMensajero(String mensajero);

    public boolean pruebaGenerarCartaPortePDF(FacturaElectronica FElectronica) throws JRException, FileNotFoundException, ProquifaNetException;
    public List <Producto> obtenerProductosXPackingList(String PackingList) ;
    public Object insertarFacturaElectronicaCartaPorte(FacturaElectronica f, Boolean isSap, Boolean isFlex, String id_vehiculo,String id_mensajero, String packingList) throws ProquifaNetException;
    public List <PFacturaElectronica> obtenerPartidasFElectronica (int FElectronica);
    public String obtenerListadoPackingList(String responsable);
    public String generarCodigo();
    public String leerArchivoProperties();
    public boolean actualizarValoresConsecutivamente();
    public String pruebasFacturas();
}
