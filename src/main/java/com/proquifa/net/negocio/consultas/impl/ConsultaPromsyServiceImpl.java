package com.proquifa.net.negocio.consultas.impl;

import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.Promsy;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.ventas.admoncomunicacion.Correo;
import com.proquifa.net.negocio.consultas.ConsultaPromsyService;
import com.proquifa.net.persistencia.consultas.ConsultasPromsyDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.time.Month;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service("consultaPromsyServiceImpl")
public class ConsultaPromsyServiceImpl implements ConsultaPromsyService {
    @Autowired
    ConsultasPromsyDAO consultasPromsyDAO;
    @Override
    public Integer unidadesVendidasTrimestre(Parametro parametro) throws ProquifaNetException {
        int unidades=0;
        try {
            System.out.println(parametro.getTipoProducto()+" "+parametro.getControl()+" "+parametro.getSubTipo());
           // System.out.println(parametro.getFechaInicio().toString()+","+parametro.getFechaFin().toString());
            unidades= consultasPromsyDAO.unidadesVendidasTrimestre(parametro);
            return unidades;
        }catch (Exception e){
          e.printStackTrace();
        }
        return 0;
    }

    @Override
    public BigDecimal montosVendidosTrimestre(Parametro parametro) throws ProquifaNetException {
        try{
            return consultasPromsyDAO.montosVendidosTrimestre(parametro);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new BigDecimal(0);
    }

    @Override
    public List<Promsy> comparativaTrimestre(Parametro parametro) throws ProquifaNetException {
        try{
            return consultasPromsyDAO.comparativaTrimestres(parametro);
        }catch(Exception e){

        }
        return null;
    }

    @Override
    public List<Promsy> comparativaProveedorVSTodos(Parametro parametro) throws ProquifaNetException {
        try{
            return  consultasPromsyDAO.comparativaProveedorVSTodos(parametro);
        }catch (Exception e){
           e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> top5ProductosMasVendidos(Parametro parametro) throws ProquifaNetException {
        try{
            return  consultasPromsyDAO.top5ProductosMasVendidos(parametro);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> unidadesVendidasPorMes(Parametro parametro) throws ProquifaNetException {
        try{
           /* List <Map<String,Object>> unidades = consultasPromsyDAO.unidadesVendidasPorMes(parametro);
            if (unidades.size()==12){
                Map<String, Object> fila = new HashMap<>();

                Month mesActual = Month.of(Integer.parseInt(parametro.getStartDate().substring(5,6))); // Ejemplo con marzo, puedes usar LocalDate.now().getMonth() para obtener el mes actual
                String nombreMes = mesActual.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
              //  Year añoActual= Year.of(Integer.parseInt(parametro.getStartDate().substring(0,4)));

                fila.put("piezas", 0);
                fila.put("monto", BigDecimal.ZERO);
                fila.put("mes", nombreMes);
                fila.put("año", parametro.getStartDate().substring(0,4));
                unidades.add(fila);
                return unidades;
            }else {*/
                return consultasPromsyDAO.unidadesVendidasPorMes(parametro);
            //}
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean descontarProductosCaducadosStock() throws ProquifaNetException {
        return consultasPromsyDAO.descontarProductosCaducadosStock();
    }

    @Override
    public void pruebaFactura(String uuid) {

    }

    @Override
    public List<Map<String, Object>> reporteComercial() throws ProquifaNetException {
        try{

            List<String[]> cabecera = new ArrayList<>();
            List<String[]> resultado = new ArrayList<>();
            cabecera.add(new String[] {"Cliente", "catalogo", "descripcion", "Marca", "Piezas", "MONTO_COTIZADO","MONTO_PEDIDOS", "MONTO_FACTURADO","Cotizacion","Pedido"});
            resultado= consultasPromsyDAO.reporteComercial().stream()
                    .map(map->map.values().stream()
                            .map(String::valueOf)
                            .toArray(String[]::new))
                    .collect(Collectors.toList());
            cabecera.addAll(resultado);

            sendMailReporteComercial(cabecera);
          //  return consultasPromsyDAO.reporteComercial();
            return null;


        }catch(Exception e){
                e.printStackTrace();
        }
        return null;
    }
    private void sendMailReporteComercial(List<String[]> dataLines) throws ProquifaNetException, IOException {
        Properties propiedades = new Properties();
        InputStream entrada = null;
        entrada = new FileInputStream(Funcion.RUTA_DOCUMENTOS +"configuraciones.properties");

        try {



            propiedades.load(entrada);
            String correos = propiedades.getProperty("correos");
            Funcion funcion = new Funcion();
            String nameFile =  "reporte_Comercial.csv";
            String ubicacion = "/tmp/" ;
            //File csvOutputFile = new File("C:\\Users\\Fernando Betanzos\\Downloads\\" + nameFile);
            File csvOutputFile = new File(ubicacion + nameFile);
            try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
                dataLines.stream().map(this:: convertToCSV).forEach(pw::println);
            }
            Correo correo = new Correo();
            correo.setOrigen("ventas");
            correo.setCuerpoCorreo("");
            correo.setAsunto("Reporte comercial");
            correo.setCorreo(correos);
            correo.setArchivoAdjunto(nameFile);
            correo.setTipo("csvreportecomercial");
            funcion.enviarCorreo(correo);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entrada.close();
        }
    }
    public String convertToCSV(String[] data) {
        return Stream.of(data)
                .map(this::escapeSpecialCharacters)
                .collect(Collectors.joining(","));
    }

    private String escapeSpecialCharacters(String data) {
        String escapedData = data;
        if(data != null) {
            if (data.contains(",") || data.contains("\"") || data.contains("'") || data.contains("\n")) {
                data = data.replace("\"", "\"\"");
                escapedData = "\"" + data + "\"";
            }
        } else {
            escapedData = "";
        }
        return escapedData;
    }
}
