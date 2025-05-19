package com.proquifa.net.negocio.comun.impl;

import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.Empresa;
import com.proquifa.net.modelo.comun.Producto;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.facturaElectronica.*;
import com.proquifa.net.modelo.comun.subReporteCartaPorte;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.negocio.cobrosypagos.facturista.impl.UtilFacturaServiceImpl;
import com.proquifa.net.negocio.comun.CartaPorteService;
import com.proquifa.net.persistencia.cartaPorte.cartaPorteDAO;
import com.proquifa.net.persistencia.comun.facturacion.FacturacionElectronicaDAO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.transform.*;
import java.io.*;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("cartaPorteServiceImpl")
public class CartaPorteServiceImpl implements CartaPorteService {

    private FileXML fileXML = new FileXML();
    private static final String CARACTERES = "ABCDEFabcdef0123456789";
    private static final int LONGITUD = 5;
    @Autowired
    public FacturaElectronicaServiceImpl FE;
    private Funcion funcion = new Funcion();

    private UtilFacturaServiceImpl utilFacturaService= new UtilFacturaServiceImpl() ;
    @Autowired
    cartaPorteDAO cartaPortedao;
    @Autowired
    FacturacionElectronicaDAO facturacionElectronicaDAO;
    final Logger log = LoggerFactory.getLogger(FacturaElectronicaServiceImpl.class);
    @Override
    public Document generarXMLCartaPorte(FacturaElectronica cartaPorte) throws IOException, TransformerException {
        log.info("--------------------------------Generar XML Carta Porte 2.0--------------------------------");
                try {
                    /*****************************/

                   /*****************************/

                    Document cfdi = fileXML.getCfdi();
                    Element comprobante = fileXML.crearComprobante(cartaPorte.getVersion(), "TRASLADO", cartaPorte.getFolio(), cartaPorte.getFecha(),
                            null, cartaPorte.getNoCertificado(), cartaPorte.getCertificado(), null, "0",
                            "XXX", cartaPorte.getTipoCambio(), "0", cartaPorte.getTipoComprobante(), null,
                            cartaPorte.getLugarExpedicion());

                    String razonSocialE = cartaPorte.getEmpresa().getRazonSocial();
                    if(Funcion.FACTURACION_4){
                        // Validación sin expresion regular
                        String[] resultRazonS = cartaPorte.getEmpresa().getRazonSocial().split("S.A. DE C.V.");
                        if(resultRazonS.length > 0 ){
                            System.out.println(resultRazonS[0].trim());
                            razonSocialE = resultRazonS[0].trim();
                        }
                    }
                    if(Funcion.PRODUCCION){
                        if (cartaPorte.getCliente().getRfc().equals("XAXX010101000")){
                            Element infoGlogal =fileXML.crearInformacionGlobal();
                            comprobante.appendChild(infoGlogal);
                        }
                        Element emisor = fileXML.crearEmisor(cartaPorte.getEmpresa().getRfcEmpresa(), cartaPorte.getEmpresa().getRazonSocial(),
                                cartaPorte.getEmpresa().getRegimenFiscal());
                        comprobante.appendChild(emisor);
                    } else {
                        Element emisor = fileXML.crearEmisor("EKU9003173C9", "ESCUELA KEMPER URGATE",
                                "601");
                        comprobante.appendChild(emisor);
                    }
                    String rfc=cartaPorte.getCliente().getRfc();
                    System.out.println("codigo Postal"+ rfc);
                    String cpReceptor = facturacionElectronicaDAO.obtenerCPCliente(rfc);
                    log.info("CP: "+ cpReceptor);
                    Element receptor=null;
                    String regimenF = facturacionElectronicaDAO.getRegimenF(cartaPorte.getCliente().getRfc());
                    String usoCFDI = facturacionElectronicaDAO.obtnerUsoCFDICliente(cartaPorte.getCliente().getRfc());
                    if(Funcion.PRODUCCION){
                        receptor = fileXML.crearReceptor("", "",
                                "S01","", "");
                    }
                    {
                        receptor = fileXML.crearReceptor(cartaPorte.getEmpresa().getRfcEmpresa(), cartaPorte.getEmpresa().getRazonSocial(),
                                "S01","14080", "601");
                    }
                /*    String nameReceptor = facturacionElectronicaDAO.getNameCliente(cartaPorte.getCliente().getRfc()).replaceAll("\\?", "Ñ");
                    log.info("#Cliente: ", cartaPorte.getCliente().getIdCliente());
                    log.info("Nombre Cliente: "+ nameReceptor);
                    String regimenF = facturacionElectronicaDAO.getRegimenF(cartaPorte.getCliente().getRfc());
                    log.info("Uso CFDI Cliente: "+ facturacionElectronicaDAO.obtnerUsoCFDICliente(cartaPorte.getCliente().getRfc()));
                    String usoCFDI=facturacionElectronicaDAO.obtnerUsoCFDICliente(cartaPorte.getCliente().getRfc());
*/
/*
                        Element receptor = fileXML.crearReceptor(cartaPorte.getCliente().getRfc(), nameReceptor,
                                "S01",cpReceptor, regimenF);
*/

                    comprobante.appendChild(receptor);

                    Element conceptos = fileXML.crearConceptos();
                    // Se agrego la siguiente linea para asignar e valor a objectImport
                    String objectImport = cartaPorte.getEmpresa().getRegimenFiscal().equalsIgnoreCase("GOL120717DJ7")?"01":"02";
                    //
                    List<PFacturaElectronica> pfactura=cartaPortedao.obtenerPartidasFElectronica(cartaPorte.getIdFactura());
                    System.out.println("Tamaño lista conseptos"+cartaPorte.getLstConceptos().size());
                    for (PFacturaElectronica item : pfactura) {
                        // Se agrego al nodo crearConcepto el atributo objectImport
                        Element concepto = fileXML.crearConcepto(item, objectImport, cartaPorte.getTipoComprobante());
                        if (item.getAdnNumeroPedimento() != null && !item.getAdnNumeroPedimento().equals("")) {
                            Element infoAduanera = fileXML.crearInformacionAduanera(item.getAdnNumeroPedimento().replace(" ","  "));
                            concepto.appendChild(infoAduanera);

                        }
                        conceptos.appendChild(concepto);
                    }
                        comprobante.appendChild(conceptos);

                    Date hoy = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                /***********************************************************************************/
                    Element complemento = fileXML.crearComplemento();
                    Element cPorte= fileXML.crearCartaPorte(cartaPorte.getDistRecorrida(),generarCodigo());
                    Element ubicaciones = fileXML.crearUbicaciones();
                    for (int x = 0; x <= 1; x++) {
                        Element ubicacion = fileXML.crearUbicacion(cartaPorte.getCliente().getRfc(), dateFormat.format(hoy), cartaPorte.getCliente().getCalleFiscal(), "SN", cartaPorte.getCliente().getCalleFiscal(), cartaPorte.getCliente().getDelegacion(), cartaPorte.getCliente().getEstadoFiscal(), cartaPorte.getCliente().getCodigoPostal(), cartaPorte.getDistRecorrida(), x);
                        ubicaciones.appendChild(ubicacion);
                    }
                    cPorte.appendChild(ubicaciones);
                    double tamaño=cartaPorte.getLstConceptos().size();

                    String resultado;
                    resultado = String.format("%.3f", tamaño/1000); // Formato con 3 decimales
                    System.out.println(resultado);

                    Element Mercancias = fileXML.crearMercancias(resultado,String.valueOf(tamaño).replace(".0",""));



                    for (PFacturaElectronica merc: pfactura) {
                        Element Mercancia = fileXML.crearMercancia(merc.getDescripcion(), merc.getCantidad(), merc.getClaveUnidad(), merc.getUnidad(), "0.001", "MXN", merc.getClaveProdServ(),merc.getValorUnitario());
                        Mercancias.appendChild(Mercancia);
                        /*termina for*/
                    }

                    CartaPorte infoCPorte = new CartaPorte();
                    infoCPorte= cartaPortedao.obtenerInfoCartaPorte(cartaPorte.getIdFactura());

                    Element autoTransporte = fileXML.crearAutoTransporte(infoCPorte.getPermSCT(), infoCPorte.getNumPermSCT(), infoCPorte.getConfigVehicular(), infoCPorte.getPlacaVM(), infoCPorte.getAnioModelo(), infoCPorte.getAseguraRespCivil(),infoCPorte.getPolizaRespCivil());
                    Mercancias.appendChild(autoTransporte);
                    cPorte.appendChild(Mercancias);
                    Element figuraTransporte = fileXML.crearFiguraTransporte(infoCPorte.getRfcMensajero(), infoCPorte.getLicencia(), infoCPorte.getMensajero(), infoCPorte.getNumExtDomicilio(), infoCPorte.getNumInt(),infoCPorte.getCalleDomicilio());

                    cPorte.appendChild(figuraTransporte);
                    complemento.appendChild(cPorte);
                    comprobante.appendChild(complemento);
                    cfdi.appendChild(comprobante);
                    log.info("--------------------------------Fin armado XML Carta Porte 2.0--------------------------------");
                    log.info("--------------------------------incia proceso guardado XML Carta Porte 2.0--------------------------------");

                /*    DOMSource source = new DOMSource(cfdi);
                    String pathCartaPorte = "C:\\Users\\glassfish4\\glassfish\\domains\\domain1\\docroot\\SAP\\CartaPorte";

                    File fileAux = new File(pathCartaPorte);
                    if (!fileAux.exists())
                        fileAux.mkdirs();

                    File tempFile = new File(pathCartaPorte + "cartaPorte_pruebas" + ".xml");
                    FileWriter writer = new FileWriter(tempFile);
                    StreamResult sourceXML = new StreamResult(writer);
                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                    transformer.transform(source, sourceXML);
                    log.info("--------------------------------fin proceso guardado XML Carta Porte 2.0--------------------------------");*/
            return cfdi;
                }catch(Exception e){
                    System.out.println("Error carta porte:"+e);
                }





        return null;
    }

    @Override
    public List<CartaPorte> obtenerPendientesMensajero() {
        try {
            for (CartaPorte carta : cartaPortedao.obtenerEventosMensajero()){
                System.out.println("Responsable\n"+carta.getResponsable()+", Eventos\n"+carta.getEventos());

            }

            return cartaPortedao.obtenerEventosMensajero();
        }catch (Exception e){
            System.out.println("Ocurrio un error"+"\n"+"obtenerPendientesMensajero");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CartaPorte> obtenerInfoMensajero() {

        try{
            return cartaPortedao.obtenerInfoMensajero();
        }catch(Exception e ){
            System.out.println("Ocurrio un error"+"\n"+"obtenerInfoMensajero, cartaPorteServiceImpl");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<CartaPorte> obtenerVehiculos() {
        try{
            return cartaPortedao.obtenerVehiculos();
        }catch (Exception e){
            System.out.println("Ocurrio un error"+"\n"+"obtenerVehiculos, cartaPorteServiceImpl");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Cliente> obtenerClientesPorMensajero(String mensajero) {
        try{
            return cartaPortedao.obtenerClientesPorMensajero(mensajero);
        }catch (Exception e){
            System.out.println("Ocurrio un error"+"\n"+"obtenerClientesPorMensajero, cartaPorteServiceImpl");
        }
        return null;
    }

    @Override
    public boolean pruebaGenerarCartaPortePDF(FacturaElectronica FElectronica) throws JRException, FileNotFoundException, ProquifaNetException {
     try {
         String rutaGeneral = funcion.obtenerRutaServidor("jaspercartaporte", "");
         String rutaPDF= funcion.obtenerRutaServidor("cartaporte","");

         File file = new File(rutaGeneral);

         if (!file.exists()) {
             file.mkdirs();
             log.info("Se crea la carpeta");
         }
        List<PFacturaElectronica> pFactura= cartaPortedao.obtenerPartidasFElectronica(FElectronica.getIdFactura());
         String nameReceptor = facturacionElectronicaDAO.getNameCliente(FElectronica.getCliente().getRfc()).replaceAll("\\?", "Ñ");
         String regimenF = facturacionElectronicaDAO.getRegimenF(FElectronica.getCliente().getRfc());
         CartaPorte infoCPorte = new CartaPorte();
         infoCPorte= cartaPortedao.obtenerInfoCartaPorte(FElectronica.getIdFactura());
         InputStream inputStream = new FileInputStream(rutaGeneral + "CartaPorte.jrxml");
         String datosDigitales= "NÚMERO DE SERIE DEL CERTIFICADO DEL SAT:" + FElectronica.getSatNoCertificadoSAT()
                 + " NÚMERO DE SERIE DEL CSD DEL EMISOR:" + FElectronica.getNoCertificado()
                 + " SELLO DIGITAL DEL SAT:" + FElectronica.getSello()
                 + " SELLO DIGITAL DEL CFDI:"+ FElectronica.getSatSelloCFD()
                 + " CADENA ORIGINAL DEL COMPLEMENTO DE CERTIFICACIÓN DIGITAL DEL SAT:"+ FElectronica.getCadenaOriginal();
         String fe= FElectronica.getSatSelloCFD().substring(FElectronica.getSatSelloCFD().length()-8,FElectronica.getSatSelloCFD().length());
         String codigoQR="https://verificacfdi.facturaelectronica.sat.gob.mx/default.aspx?id="+ FElectronica.getSatUUID() + "&re=" + FElectronica.getEmpresa().getRfcEmpresa()
                 + "&rr=" + FElectronica.getCliente().getRfc() + "&tt="+ FElectronica.getTotal() + "&fe=" + fe;
         utilFacturaService.crearCodigo(codigoQR);
         Date hoy = new Date();
         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'18:00:00");
         double tamaño=pFactura.size();
         String resultado;
         resultado = String.format("%.3f", tamaño/1000); // Formato con 3 decimales
         System.out.println(resultado);

         JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
         JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);


         InputStream inputStream2 = new FileInputStream(rutaGeneral + "cPorte_Ubicaciones.jrxml");
         JasperDesign jasperDesign2 = JRXmlLoader.load(inputStream2);
         JasperReport jasperReport2 = JasperCompileManager.compileReport(jasperDesign2);
         JRSaver.saveObject(jasperReport2, rutaGeneral + "cPorte_Ubicaciones" + ".jasper");

         Map<String, Object> parametros = new HashMap<String, Object>();
         subReporteCartaPorte subRep = new subReporteCartaPorte(FElectronica.getEmpresa().getRfcEmpresa(), FElectronica.getFecha(), "JOSE MARIA MORELOS #164", FElectronica.getCliente().getRfc(), dateFormat.format(hoy),
                 FElectronica.getDistRecorrida(), FElectronica.getCliente().getCalleFiscal(), resultado, "KGM", resultado, String.valueOf(pFactura.size()), pFactura, infoCPorte.getPermSCT(),infoCPorte.getNumPermSCT(), infoCPorte.getConfigVehicular() +" - vehículo ligero de carga (2 llantas en el eje delantero y 2 llantas en el eje trasero)",
                 infoCPorte.getPlacaVM(), infoCPorte.getAnioModelo(), infoCPorte.getAseguraRespCivil(), infoCPorte.getPolizaRespCivil(), infoCPorte.getRfcMensajero(), infoCPorte.getLicencia(), infoCPorte.getMensajero(),infoCPorte.getCalleDomicilio(),
                 rutaGeneral + "edm.png", rutaGeneral + "apacor.png", rutaGeneral + "british.png", rutaGeneral + "chata.png", rutaGeneral + "usp.png",
                 rutaGeneral + "feum.png", rutaGeneral + "micro.png", rutaGeneral + "usp.png");
         System.out.println("Bienes transportados: " + subRep.getListProds());
         parametros.put("rutaSubReporte", rutaGeneral + "cPorte_Ubicaciones.jasper");
         parametros.put("subReporte", subRep);
         parametros.put("logo", rutaGeneral + "Proquifa.jpg");
         parametros.put("amex", rutaGeneral + "amex.png");
         parametros.put("neec", rutaGeneral + "neec.png");
         parametros.put("iso", rutaGeneral + "iso.png");
         parametros.put("nameEmisor", "PROVEEDORA QUIMICO FARMACEUTICA");
         parametros.put("RFCEmisor",FElectronica.getEmpresa().getRfcEmpresa());
         parametros.put("regimenFiscalEmisor","601");
         parametros.put("lugarExp","14080");
         parametros.put("domReceptor"," CALLE: JOSE MARIA MORELOS #164 COLONIA:\n" +"BARRIO NIÑO JESUS ALCALDÍA: TLALPAN C.P. 14080\n" + "CIUDAD: MEXICO PAÍS: MEXICO");
         parametros.put("dateExp",FElectronica.getFecha());
         parametros.put("nameClient", nameReceptor);
         parametros.put("rfcClient", FElectronica.getCliente().getRfc());
         parametros.put("resideClient", FElectronica.getCliente().getCalleFiscal());
         parametros.put("regimenFiscalClient", regimenF);
         parametros.put("usoCFDIClient", FElectronica.getCliente().getUsoCFDI());
         parametros.put("version", FElectronica.getVersion());
         parametros.put("voucherType", "T");
         parametros.put("serie", FElectronica.getSerie());
         parametros.put("paymentConditions", "NA");
         parametros.put("invoice", FElectronica.getFolio());
         parametros.put("export", "01- No aplica");
         parametros.put("folioFiscal", FElectronica.getSatUUID());
         parametros.put("certificationDate", FElectronica.getFecha());
         parametros.put("listProductos", pFactura);
         parametros.put("totalText", FElectronica.getTotalTexto());
         parametros.put("subtotal", "0.00");
         parametros.put("total","0.00");
         parametros.put("currency", "XXX ");
         parametros.put("Distance", "");
         parametros.put("edomBlanco", rutaGeneral + "edm.png");
         parametros.put("feum", rutaGeneral + "feum.png");
         parametros.put("british", rutaGeneral + "british.png");
         parametros.put("micro", rutaGeneral + "micro.png");
         parametros.put("apacor", rutaGeneral + "apacor.png");
         parametros.put("chata", rutaGeneral + "chata.png");
         parametros.put("pharma", rutaGeneral + "pharma.png");
         parametros.put("usp", rutaGeneral + "usp.png");
         parametros.put("codQr", rutaGeneral + "codigoBarras.png");
         parametros.put("datosDigitales", datosDigitales);
         parametros.put("folio", FElectronica.getFolio());
         parametros.put("distance",FElectronica.getDistRecorrida());


         //Crea Reporte principal
         JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());
         //Crear segundo reporte
         JasperReport subReport = (JasperReport) JRLoader.loadObjectFromFile(rutaGeneral + "cPorte_Ubicaciones.jasper");
         JasperPrint subPrint = JasperFillManager.fillReport(subReport, parametros, new JREmptyDataSource());
         //Agregar subReporte al reporte principal
         jasperPrint.getPages().addAll(subPrint.getPages());
         JasperExportManager.exportReportToPdfFile(jasperPrint, rutaPDF + FElectronica.getFolio() + ".pdf");
         return true;
     }catch (Exception e){
         e.printStackTrace();
     }

        return false;
    }

    @Override
    public List<Producto> obtenerProductosXPackingList(String packingList) {
        try {
            return cartaPortedao.obtenerProductosPorPackingList(packingList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Object insertarFacturaElectronicaCartaPorte(FacturaElectronica f, Boolean isSap, Boolean isFlex, String id_vehiculo, String id_mensajero,String Responsable) throws ProquifaNetException {
        try {
            Empresa e = new Empresa();
            e.setIdEmpresa(2);
            e.setAlias("Proveedora");
            e.setRfcEmpresa("PQF910416FB3");
            e.setRazonSocial("PROVEEDORA QUIMICO FARMACEUTICA");
            e.setRegimenFiscal("601");
            e.setCp("14080");
            f.setEmpresa(e);
            System.out.println("PackingList:"+Responsable);
     //   f.setVersion("4.0");
        Date hoy = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        log.info("Insertar FacturaElectronica para CartaPorte:" + dateFormat.format(hoy));
        f.setFecha(dateFormat.format(hoy));
        f.setTotalTexto(funcion.ConvertirNumerosALetras("0.0", "XXX"));
        String rfcInternacional = facturacionElectronicaDAO.validarClienteInternacional(f);

        if (rfcInternacional != null && !rfcInternacional.equals("")) {
            f.getCliente().setRfc(rfcInternacional);
        }
            System.out.println("Alias:"+f.getEmpresa().getAlias()+", tipo comprobante:"+f.getTipoComprobante());
       // if (isFlex) {
            String folio = FE.obtenerFolio(f.getEmpresa().getAlias(), "T");
            if (folio.equals("bloqued")){
                return null;
            }
            f.setFolio(folio);
   //     }

            f.setImpuestosImporte("0.00");
            f.setSubtotal("0.00");
            f.setTotal("0.00");
            f.setMoneda("MXN");
            f.setTipoComprobante("T");
            f.setLugarExpedicion("14080");


        f = facturacionElectronicaDAO.insertarFactura(f);
        String packingList= obtenerListadoPackingList(f.getResponsable());
        List<Producto> conceptos=   obtenerProductosXPackingList(packingList);
         List<PFacturaElectronica> pfactura = new ArrayList<>();
       /* for (PFacturaElectronica pf : f.getLstConceptos()) {
            pf.setFactura(f.getIdFactura());
            pf = facturacionElectronicaDAO.insertarPFactura(pf);
        }*/PFacturaElectronica pf = new PFacturaElectronica();
            for (Producto prod : conceptos){
                pf.setFactura(f.getIdFactura());
                pf.setCantidad(prod.getCant().toString());
                pf.setClaveProdServ(prod.getCodigo());
                //pf.s(Integer.parseInt(f.getFolio()));
                pf.setDescripcion(prod.getDescripcion());
                pf.setClaveUnidad(prod.getClaveUnidad());
                pf.setAdnNumeroPedimento(prod.getPedimento());
                pf.setUnidad(prod.getUnidad());
                pf.setNoIdentificacion(prod.getCodigoInterno());
                pf.setValorUnitario("0.00");
                pf.setImporte("0.00");
                pf.setImpuestoClave("");
                pf.setImpuestoTipoFactor("");
                pf.setImpuestoImporte("");
                pfactura.add(pf);

                 facturacionElectronicaDAO.insertarPFactura(pf);

            }
            String[] lista = packingList.split(",");
            for (String lst: lista) {
                cartaPortedao.insertarCartaPorte(id_vehiculo, id_mensajero, String.valueOf(f.getIdFactura()),lst);
            }
                f.setLstConceptos(pfactura);
            if (!FE.facturar(f, false).equals("Error")) {
                return f.getFolio();
            } else {
                facturacionElectronicaDAO.updateErrorFactura(f.getIdFactura());
                return -f.getIdFactura();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<PFacturaElectronica> obtenerPartidasFElectronica(int FElectronica) {

        try {
            return cartaPortedao.obtenerPartidasFElectronica(FElectronica);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String obtenerListadoPackingList(String responsable) {
        try {
            System.out.println(responsable);
        List <String> listado= cartaPortedao.obtenerListadoPackingList(responsable);
        StringJoiner joiner = new StringJoiner(",");

            for (String valor : listado) {
                System.out.println("PackingList"+valor);
                joiner.add(valor);
            }

            return joiner.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String generarCodigo() {
        // Longitud del código
        //int longitudCodigo = 12;
        int[] valores = {5, 4, 4, 4, 12};
        int c=0;
        // Caracteres permitidos en el código alfanumérico
        String caracteresPermitidos = "ABCDEF0123456789";

        SecureRandom random = new SecureRandom();
        StringBuilder codigo = new StringBuilder();
        for (int valor : valores) {
            c++;
            for (int i = 0; i < valor; i++) {
                int indiceCaracter = random.nextInt(caracteresPermitidos.length());
                codigo.append(caracteresPermitidos.charAt(indiceCaracter));

            }
            if(c!=5){
                codigo.append("-");

            }//cod += codigo+"-";
        }
        System.out.println("CCC"+codigo.toString());
        return codigo.toString();
    }

public String leerArchivoProperties(){
    Properties propiedades = new Properties();
    InputStream entrada = null;

    try {
        // Cargar el archivo properties
        entrada = new FileInputStream("C:\\Users\\Fernando Betanzos\\Documents\\AngularBackEnd\\configuraciones.properties");

        // Cargar las propiedades desde el archivo
        propiedades.load(entrada);

        // Acceder a los valores de las propiedades
        String total = propiedades.getProperty("total");
        String subtotal = propiedades.getProperty("subtotal");
        String totalImpuestosTrasladados = propiedades.getProperty("totalImpuestosTrasladados");
        String importe = propiedades.getProperty("importe");

        // Mostrar los valores
        System.out.println("total = " + total);
        System.out.println("subtotal = " + subtotal);
        System.out.println("totalImpuestosTrasladados = " + totalImpuestosTrasladados);
        System.out.println("importe = " + importe);

    } catch (IOException io) {
        io.printStackTrace();
    } finally {
        if (entrada != null) {
            try {
                entrada.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
return "ok";
}

    @Override
    public boolean actualizarValoresConsecutivamente() {
        cartaPortedao.aplicaUpdateConsecutivo();
        return true;
    }

    @Override
    public String pruebasFacturas() {
        try {
            facturacionElectronicaDAO.impuestos("4d03f48f-6fca-7ef9-bc3b-5396fc3c7e79");
            for (String impuesto: facturacionElectronicaDAO.impuestos("4d03f48f-6fca-7ef9-bc3b-5396fc3c7e79")){
                System.out.println("Entra al for"+ facturacionElectronicaDAO.impuestos("4d03f48f-6fca-7ef9-bc3b-5396fc3c7e79").size());
                System.out.println(impuesto);
            }
                boolean aplicaImpuestos = facturacionElectronicaDAO.impuestos("840b9ee1-cba6-6d08-ce48-c20e9d2f7171,")
                        .stream()
                        .anyMatch(fe -> fe.equals("0.160000"));
            System.out.println(aplicaImpuestos);
            return "Exito";
        } catch (ProquifaNetException e) {
            throw new RuntimeException(e);
        }

    }

}
