package com.proquifa.net.negocio.cobrosypagos.facturista;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;

public interface UtilPedidoService {
        boolean generarPDFPedido(String pedido) throws ProquifaNetException, FileNotFoundException, JRException;
}
