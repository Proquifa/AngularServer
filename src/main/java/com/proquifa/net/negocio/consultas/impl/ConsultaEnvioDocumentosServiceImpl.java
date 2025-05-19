package com.proquifa.net.negocio.consultas.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.ventas.enviodocumentos.EnvioDocumentos;
import com.proquifa.net.negocio.consultas.ConsultaEnvioDocumentosService;
import com.proquifa.net.persistencia.consultas.ConsultaEnvioDocumentosDAO;

/**
 * @author miguelangeldamianlopez
 *
 */
@Service("consultaEnvioDocumentosService")
public class ConsultaEnvioDocumentosServiceImpl implements
		ConsultaEnvioDocumentosService {

	@Autowired
	ConsultaEnvioDocumentosDAO enviodocDAO;
	
	final Logger log = LoggerFactory.getLogger(ConsultaEnvioDocumentosServiceImpl.class);

	private Funcion funcion;
//	private Logger logger= Logger.getLogger(ConsultaEnvioDocumentosService.class);

	@Override
	public List<EnvioDocumentos> obtenerEnvioCorreoDocumentos(Date finicio,
			Date ffin, int destino, String origen, String tipo,
			String folioDocumento) throws ProquifaNetException {
		try{
		log.info("Folio Documento------------ "+ folioDocumento);
//			String carpeta = "";
			String condiciones = "";
//			String vWhere = " WHERE ";
//			String vAnd = "";
			//logger.info("Entro");
			//logger.info("finicio"+finicio);
			//logger.info("ffin"+ffin);
			//logger.info(folioDocumento);
			SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd");
			if (finicio != null && ffin != null) {
				funcion = new Funcion();
				condiciones += "coalesce(FInicio,co.FechaInicio)>="
						+ funcion.convertirDosFechasAString(finicio, ffin, "coalesce(FFin,co.FechaInicio)");

				if (destino > 0) {
					condiciones += " AND co.Destino = '" + destino + "'";
				}
				if (origen != null) {
					if (!origen.equals("")) {
						condiciones += " AND co.Origen = '" + origen + "'";
					}
				}

				if (tipo != null) {
					if (!tipo.equals("")) {
						condiciones += " AND co.Tipo = '" + tipo + "'";
					}
				}
			} else if (folioDocumento != null) {
				if (!folioDocumento.equals("")) {
					condiciones += " co.FolioDocumento like '%" + folioDocumento + "%'";
				}
			}
			if(condiciones.length()<=10){
				if(finicio!=null && ffin!=null){
					condiciones += " coalesce(FInicio,co.FechaInicio)>= '" + formatoFecha.format(finicio) + "' and coalesce(FFin,co.FechaInicio)>= '" + formatoFecha.format(ffin) + "'  "; //"  1=1 ";
				}
				else{
					condiciones += "1=1";
				}

			}
			//logger.info(condiciones);
			List <EnvioDocumentos> envioDocumentos = this.enviodocDAO.findDocumentosEnviados(condiciones);

			for(EnvioDocumentos envioDocto : envioDocumentos){
				String folioResultado = "";
				String adjuntoResultado = "";


				String cadena = " " + envioDocto.getFolioDocumento();
				int resultado = cadena.indexOf("Aviso");
				int resultado2 = cadena.indexOf("Carta");
				if(resultado > 0 || resultado2 > 0){

					while(cadena.indexOf(",")>0){
						String cadena2;
						if (cadena.indexOf(",")==1){
							cadena = cadena.substring(cadena.indexOf(",")+1, cadena.length());
							if(cadena.indexOf(",")==-1){
								cadena2 = cadena.substring(0,cadena.length());
								cadena = "";
							}else{
								cadena2 = cadena.substring(0,cadena.indexOf(",") );
								cadena = " " +cadena.substring(cadena.indexOf(","), cadena.length());
							}
						} else  {
							cadena2 = cadena.substring(1,cadena.indexOf(",") );
							cadena = " " +cadena.substring(cadena.indexOf(","), cadena.length());
						}
						resultado = cadena2.indexOf("Aviso");
						resultado2 = cadena2.indexOf("Carta");
						if(resultado !=-1 || resultado2 !=-1){

							adjuntoResultado += cadena2 + ", ";
						}else{
							folioResultado += cadena2 + ", ";
						}

					}
					folioResultado = folioResultado.substring(0, folioResultado.length()-1);
					adjuntoResultado = adjuntoResultado.substring(0, adjuntoResultado.length()-1);
				}else {
					if(cadena.indexOf(",")>0){
						cadena = cadena.substring(1, cadena.length());
							String cadena2 = cadena.substring(0, cadena.indexOf(","));
							cadena = cadena.substring(cadena.indexOf(",")+1, cadena.length());
							folioResultado += cadena2;
							adjuntoResultado += cadena;
					
					}
					else{
						if(envioDocto.getClaveProfor()!=null){
							folioResultado = envioDocto.getClaveProfor();
							adjuntoResultado = "";
						}else{
							folioResultado = envioDocto.getFolioDocumento();
							adjuntoResultado = "";
						}
					}
				}


//				log.info(envioDocto.getTipo());
				envioDocto.setFolioDocumento(folioResultado);
				envioDocto.setAdjuntosDocumento(adjuntoResultado);
				if(envioDocto.getTipo() == null){
					envioDocto.setTipo("ND");
				}
				
			}
//			if(envioDocumentos!=null){
//				for(EnvioDocumentos lista:envioDocumentos){
//					if(lista.getTipo().equals("Notificaciones por enviar")){
//						File f = new File(Funcion.RUTA_DOCUMENTOS + "Pedidos/" + lista.getFolioDocumento() + ".pdf");
//						if(f.exists()){
//							lista.setTipo("Pedidos por enviar");
//							continue;
//						}
//						f = new File(Funcion.RUTA_DOCUMENTOS + "Cotizaciones/" + lista.getFolioDocumento() + ".pdf");
//						if(f.exists()){
//							lista.setTipo("Cotizaciones por enviar");
//							continue;
//						}
//						f = new File(Funcion.RUTA_DOCUMENTOS + "Facturas/" + lista.getFacturaFpor() + "/" + lista.getFolioDocumento() + ".pdf");
//						if(f.exists()){
//							lista.setTipo("Facturas por enviar");
//							continue;
//						}
//						f = new File(Funcion.RUTA_DOCUMENTOS + "Proforma/" + lista.getProformaFpor() + "/" + lista.getFolioDocumento() + ".pdf");
//						if(f.exists()){
//							lista.setTipo("Proforma por enviar");
//							continue;
//						}
//						f = new File(Funcion.RUTA_DOCUMENTOS + "Aviso de Cambios/" + lista.getFolioDocumento() + ".pdf");
//						if(f.exists()){
//							lista.setTipo("Proforma por enviar");
//							continue;
//						}
//					}
//				}
//			}

			return envioDocumentos;
		} catch (ProquifaNetException e) {
			e.printStackTrace();
			return null;
		}
	}

}
