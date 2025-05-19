package com.proquifa.net.negocio.cobrosypagos.facturista.impl;
import com.proquifa.net.modelo.comun.util.Funcion;
//import com.proquifa.net.modelo.ventas.PDFConfirmacionPedido;
import com.proquifa.net.negocio.cobrosypagos.facturista.UtilPedidoService;
import com.proquifa.net.persistencia.ventas.impl.PDFConfirmacionPedido;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.persistencia.ventas.PedidoDAO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("utilPedidoService")
public class UtilPedidoServiceImpl implements UtilPedidoService {

    @Autowired
    PedidoDAO pedidoDAO;
    final Logger log = LoggerFactory.getLogger(UtilFacturaServiceImpl.class);

    public static float redondear(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    public boolean generarPDFPedido(String pedido) throws ProquifaNetException, FileNotFoundException, JRException {
        Funcion f = new Funcion();
        //String rutaPlantilla = f.obtenerRutaServidor("plantillaConfirmarPedido", "");
        //String rutaPedidos = f.obtenerRutaServidor("Pedidos", "");

        // RUTAS
        String rutaPlantilla = f.obtenerRutaServidor("plantillaconfirmarpedido", "");
        String rutaPedidos = f.obtenerRutaServidor("pedidos", "");
        // Ruta para guardar el documento
        InputStream inputStream = new FileInputStream(rutaPlantilla + "ConfirmacionPedido.jrxml");
        JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        Map<String, Object> parametros = new HashMap<String, Object>();

        List<PDFConfirmacionPedido> pdf = new ArrayList<PDFConfirmacionPedido>();
        pdf = this.pedidoDAO.obtenerDatosPdfConfirmarPedido(pedido);

        float subtotal = 0;
        float montoiva = 0;
        float granTotal = 0;
        String simboloMoneda = "";

        if(pdf.get(0).getMoneda().equals("EUR")){
            simboloMoneda = "€";
        }else if(pdf.get(0).getMoneda().equals("USD")){
            simboloMoneda = "$";
        }else if(pdf.get(0).getMoneda().equals("M.N.")){
            simboloMoneda = "$";
        }else if(pdf.get(0).getMoneda().equals("Libras")){
            simboloMoneda = "£";
        }

        int cont = 0;
        for (PDFConfirmacionPedido item : pdf) {
            cont++;
            item.setPrecioU(redondear(item.getPrecioU(),2));
            item.setImporte(redondear(item.getImporte(),2));
            subtotal = subtotal + item.getImporte();
            item.setNum(cont);
        }

        montoiva = subtotal * pdf.get(0).getIva();
        granTotal = subtotal+montoiva;

        parametros.put("cliente", pdf.get(0).getCliente());
        parametros.put("tabla", pdf);
        parametros.put("rutaImagenes", rutaPlantilla);
        switch (pdf.get(0).getCondicion())
        {
            case "Proquifa":
                parametros.put("UrlInfoContacto", rutaPlantilla + "info_contacto_PROQUIFA.jpg");
                parametros.put("UrlFooter", rutaPlantilla + "FooterMarcas_Horizontal_PQF.png");
                break;
            case "Proveedora":
                parametros.put("UrlInfoContacto", rutaPlantilla + "info_contacto.jpg");
                parametros.put("UrlFooter", rutaPlantilla + "FooterMarcas_Horizontal_PQF.png");
                break;
            case "Pharma":
                break;
            case "Golocaer":
                parametros.put("UrlInfoContacto", rutaPlantilla + "info_contacto_GOLOCAER.jpg");
                parametros.put("UrlFooter", rutaPlantilla + "Footer_Marcas_Horizontal_GOLOCAER.png");
                break;
            case "Mungen":
                parametros.put("UrlInfoContacto", rutaPlantilla + "info_contacto_MUNGEN.jpg");
                parametros.put("UrlFooter", rutaPlantilla + "Footer_Marcas_Horizontal_MUNGEN.png");
                break;

        }
        parametros.put("cPedido", pdf.get(0).getCpedido());
        parametros.put("nombreContacto", pdf.get(0).getContactoArriba());
        parametros.put("puesto", pdf.get(0).getPuesto());
        parametros.put("area", pdf.get(0).getArea());
        parametros.put("moneda", pdf.get(0).getMoneda());
        parametros.put("Subtotal", redondear(subtotal,2));
        parametros.put("Iva", pdf.get(0).getIva()*100);
        parametros.put("montoIva", redondear(montoiva,2));
        parametros.put("total", redondear(granTotal,2));
        parametros.put("condPago", pdf.get(0).getCondicionPago());
        parametros.put("RFC", pdf.get(0).getRfcFiscal());
        parametros.put("razonSocial", pdf.get(0).getRazonSocial());
        parametros.put("direccionFiscal", pdf.get(0).getDireccionFiscal());
        parametros.put("Parciales", pdf.get(0).getParciales());
        parametros.put("Contacto", pdf.get(0).getContactoEntrega());
        parametros.put("Lugar", pdf.get(0).getDireccionEntrega());
        parametros.put("Referencia", pdf.get(0).getReferencia());
        parametros.put("simboloMoneda", simboloMoneda);
        parametros.put("Frecepcion", pdf.get(0).getFechaRecepcion());
        parametros.put("Ftramitacion", pdf.get(0).getFechaTramitacion());
        parametros.put("tramito", pdf.get(0).getUsuario());
        parametros.put("Condicion", pdf.get(0).getCondicion());

        File theDir = new File(rutaPedidos);
        // if the directory does not exist, create it
        if (!theDir.exists()) {
            boolean result = false;

            try{
                theDir.mkdir();
                result = true;
            }
            catch(SecurityException se){
                //handle it
            }
            if(result) {
                log.info("DIR created");
            }
        }

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());
        JasperExportManager.exportReportToPdfFile(jasperPrint, rutaPedidos+pdf.get(0).getCpedido()+".pdf");//ruta destino
        log.info("PDF de pedido generadO correctamente");

        return true;

    }
}
