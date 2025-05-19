package com.proquifa.net.modelo.comun.util.documentoPDF;

import java.awt.Color;
import java.io.IOException;
import java.net.MalformedURLException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;

/**
 * 
 * @author isaac.garcia
 *
 */

public class ToolsPDF {
	
	public void createLine(Document documento, PdfContentByte canvas, float xini, float yini, float xfin, float yfin,float lineWidth, Color color) {
		// Dibuja Linea o Recuadro
		xini = convertCmsToPoints(xini);
		yini = convertCmsToPoints(yini);
		xfin = convertCmsToPoints(xfin);
		yfin = convertCmsToPoints(yfin);
		
		canvas.setLineWidth(lineWidth);
		if(yini==0)
			yini=documento.getPageSize().getHeight();
		else 
			yini=documento.getPageSize().getHeight()-yini;
		
		if(yfin ==0)
			yfin=documento.getPageSize().getHeight();
		else
			yfin=documento.getPageSize().getHeight()-yfin;
		
		canvas.setColorStroke(color);
		canvas.moveTo(xini, yini);
		canvas.lineTo(xfin,yfin);
		canvas.stroke();
	}
	
	public void imprimirTexto(PdfContentByte cb, String sFuente, float fSize, Color colorText, float X, float Y, String sTexto, String sAlinear, float fRotar){
		// Punto de partida es de la Esquina inferior izquierda,  Medida en Points
		try {			
			BaseFont bf = BaseFont.createFont(sFuente,BaseFont.CP1252,BaseFont.EMBEDDED); //Crea la fuente, sFuente es una Constante de BaseFont			
			cb.beginText(); // Indico que comienzo a escribir texto			
			cb.setColorFill(colorText); // Color del Texto
			cb.setFontAndSize(bf, fSize); // Indico la fuente a utilizar
			
			// Escribe el texto
			if (sAlinear.equals("CENTER")){
				cb.showTextAligned(PdfContentByte.ALIGN_CENTER,sTexto, convertCmsToPoints(X), convertCmsToPoints(Y), fRotar); 
			}else if (sAlinear.equals("LEFT")){
				cb.showTextAligned(PdfContentByte.ALIGN_LEFT,sTexto, convertCmsToPoints(X), convertCmsToPoints(Y), fRotar);
			}else if (sAlinear.equals("RIGHT")){
				cb.showTextAligned(PdfContentByte.ALIGN_RIGHT,sTexto, convertCmsToPoints(X), convertCmsToPoints(Y), fRotar);
			}else{ // Imprime el texto igual que la alineacion a la Izquierda 
				cb.setTextMatrix(convertCmsToPoints(X), convertCmsToPoints(Y)); // Es la posicion en la que va a ser colocado el texto
				cb.showText(sTexto);
			}
						
			cb.endText(); // Indica que se termino de agregar texto
			
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void imprimirParrafo(PdfContentByte cb ,float inicioX, float finX, float inicioY, float finY, float fInterlineado, int iAlineacion, String sTexto, Font fuente){
		// Crea un rectangulo en el que se va a escribir. Punto de partida es de la Esquina inferior izquierda,  Medida en Points
		try {
			ColumnText ct = new ColumnText(cb);
			ct.addText(new Phrase(sTexto, fuente));
			ct.setSimpleColumn(convertCmsToPoints(finX), convertCmsToPoints(finY), convertCmsToPoints(inicioX), convertCmsToPoints(inicioY), convertCmsToPoints(fInterlineado), iAlineacion);		
			ct.go();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	public void dibujarImagen(PdfContentByte cb, String sRutaImagen, float X, float Y, float fScalePorcent){
		//Dibuja la imagen en el PDF
		try {
			Image image = Image.getInstance(sRutaImagen);			
			image.setAbsolutePosition( convertCmsToPoints(X), convertCmsToPoints(Y)); // Inicia en la esquina inferior izquierda -- Medida en Points
			image.scalePercent(fScalePorcent);		
			cb.addImage(image, false);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void dibujarImagenEscalarFit(PdfContentByte cb, String sRutaImagen, float X, float Y, float fitWidth, float fitHeight ){
		//Dibuja la imagen en el PDF
		try {
			Image image = Image.getInstance(sRutaImagen);	
			image.scaleToFit(fitWidth, fitHeight);
			image.setOriginalType(Image.ORIGINAL_JPEG);
			
			image.setAbsolutePosition( convertCmsToPoints(X), convertCmsToPoints(Y)); // Inicia en la esquina inferior izquierda -- Medida en Points
//			image.setAbsolutePosition( convertCmsToPoints((fitWidth - image.getWidth())/2), convertCmsToPoints(Y)); // Inicia en la esquina inferior izquierda -- Medida en Points
			cb.addImage(image, false);
			
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public float convertPointsToCms(float points) { // Convierte Points a CM
     	return points / 28.4527559067f;
     }
	
	public float convertCmsToPoints(float cm) { // Convierte CM a Points
     	return cm * 28.4527559067f;
     }
}
