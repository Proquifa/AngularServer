package com.proquifa.net.persistencia.ventas.impl;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PDFConfirmacionPedidoRowMapper implements RowMapper {

    public Object mapRow(ResultSet rs, int arg1) throws SQLException {

        PDFConfirmacionPedido pdf = new PDFConfirmacionPedido();

        pdf.setCantidad(rs.getInt("Pza"));
        pdf.setDescripcion(rs.getString("Concepto"));
        pdf.setEstatus(rs.getString("Tipo"));

        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("d/MMM/y");
        Date d = rs.getDate("FEE");
        String date;
        if (d != null) {
            date = formatter.format(d);
            pdf.setFee(date);
        }else {
            pdf.setFee("N/A");
        }


        formatter = new SimpleDateFormat("d/MMM/y H:mm");
        d = rs.getTimestamp("Recepcion");
        date = formatter.format(d);
        pdf.setFechaRecepcion(date);

        formatter = new SimpleDateFormat("d/MMM/y H:mm");
        d = rs.getTimestamp("Tramitacion");
        date = formatter.format(d);
        pdf.setFechaTramitacion(date);

        pdf.setPrecioU(rs.getFloat("PU"));
        pdf.setImporte(rs.getFloat("PT"));

        pdf.setCliente(rs.getString("cliente"));
        pdf.setContacto(rs.getString("contacto"));
        pdf.setContactoArriba(rs.getString("contacto"));
        pdf.setCpedido(rs.getString("cpedido"));
//		pdf.setFdp(rs.getString("FDP"));
//		pdf.setFnueva(rs.getString("fnueva"));
        pdf.setUsuario(rs.getString("Tramito"));
//		pdf.setIdPcompra(rs.getLong("idPCompra"));
//		pdf.setOrdenCompra(rs.getString("ordenCompra"));
        pdf.setCondicionPago(rs.getString("condicionPago"));
        pdf.setMoneda(rs.getString("Moneda"));
        pdf.setIva(rs.getFloat("IVA"));
        pdf.setRfcFiscal(rs.getString("RFCFiscalP"));
        pdf.setDireccionFiscal(rs.getString("direccionFiscal"));
        pdf.setParciales(rs.getInt("Parciales"));
        pdf.setContactoEntrega(rs.getString("contactoEntrega"));

        pdf.setDireccionEntrega(rs.getString("direccionEntrega"));
        pdf.setReferencia(rs.getString("Referencia"));
        pdf.setPuesto(rs.getString("Puesto"));
        pdf.setArea(rs.getString("Departamento"));
        pdf.setRazonSocial(rs.getString("razonSocial"));
        pdf.setFabrica(rs.getString("Fabrica"));
        pdf.setCondicion(rs.getString("condicion"));


        return pdf;

    }

}
