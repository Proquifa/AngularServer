package com.proquifa.net.negocio.finanzas.impl;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;

import com.proquifa.net.negocio.finanzas.TipoCambioService;
import com.proquifa.net.persistencia.finanzas.TipoCambioDAO;

import java.net.URL;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Service ("tipoCambioService")
public class TipoCambioServiceImpl implements TipoCambioService{
	@Autowired
	TipoCambioDAO tipoCambioDAO;
	
	
	final Logger log = LoggerFactory.getLogger(PaybookServiceImpl.class);
	
	@Override
	public boolean obtenerTCambioDOF() throws ProquifaNetException {
		try{	
			
			String url = "https://dof.gob.mx/indicadores.xml";				
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new URL(url).openStream());

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("item");
			String tcambio = new String();
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				Element eElement = (Element) nNode;
				if ( eElement.getElementsByTagName("title").item(0).getTextContent().equals("DOLAR")){
					tcambio = eElement.getElementsByTagName("description").item(0).getTextContent();
					break;
				}				
			}
			
			Date fechaActual = new Date();					
			
			if (tipoCambioDAO.existTCambioDOF(fechaActual)){
				tipoCambioDAO.updateTCambioDOF(fechaActual, tcambio);
			} else{
				tipoCambioDAO.insertTCambioDOF(fechaActual, tcambio);
			}
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return false;
			
		}		
	}
}
