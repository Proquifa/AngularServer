package com.proquifa.net.persistencia.cartaPorte;

import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.Producto;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.facturaElectronica.CartaPorte;
import com.proquifa.net.modelo.comun.facturaElectronica.PFacturaElectronica;

import java.util.List;

public interface cartaPorteDAO {
    public List<CartaPorte> obtenerEventosMensajero() throws ProquifaNetException;
    public List<CartaPorte> obtenerInfoMensajero() throws ProquifaNetException;
    public List <CartaPorte> obtenerVehiculos()throws ProquifaNetException;
    public List <Cliente> obtenerClientesPorMensajero(String mensajero)throws ProquifaNetException;
    public List <Producto> obtenerProductosPorPackingList(String packingList)throws ProquifaNetException;

    public boolean insertarCartaPorte(String vehiculo,String mensajero, String FE,String packingList);
    public CartaPorte obtenerInfoCartaPorte(int FElectronica);

    public List <PFacturaElectronica> obtenerPartidasFElectronica(int FElectronica);
    public List<String> obtenerListadoPackingList(String responsable);

    public boolean aplicaUpdateConsecutivo();
}
