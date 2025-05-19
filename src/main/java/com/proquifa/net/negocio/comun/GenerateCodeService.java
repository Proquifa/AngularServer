package com.proquifa.net.negocio.comun;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;

public interface GenerateCodeService {

    /***
     *
     * @param code
     * @return
     * @throws ProquifaNetException
     */
    public String getQRCode(String code) throws ProquifaNetException;

    /***
     *
     * @param code
     * @return
     * @throws ProquifaNetException
     */
    public String getBarcode(String code) throws ProquifaNetException;
}
