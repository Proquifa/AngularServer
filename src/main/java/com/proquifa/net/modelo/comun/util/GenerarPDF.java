/**
 * 
 */
package com.proquifa.net.modelo.comun.util;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import com.proquifa.net.modelo.compras.gestorproductosphs.productoaconfirmar.PartidaConfirmacion;
import com.proquifa.net.modelo.compras.tramitadorocphs.NotasProforma;
import com.proquifa.net.modelo.compras.tramitadorocphs.PartidaProforma;
import com.proquifa.net.modelo.compras.tramitadorocphs.Proforma;
import com.proquifa.net.modelo.comun.Contacto;
import com.proquifa.net.modelo.comun.Proveedor;
import com.proquifa.net.modelo.comun.TablaPDF;
import com.proquifa.net.modelo.despachos.mensajero.ConfirmacionEntrega;
import com.proquifa.net.modelo.ventas.PartidaCotizacion;
import com.proquifa.net.modelo.ventas.requisicion.PRequisicion;
import com.proquifa.net.modelo.ventas.requisicion.Requisicion;


/**
 * @author fmartinez
 *
 */
public class GenerarPDF {
	public float RASPECTOX;
	public float RASPECTOY;
	
	Funcion f;
	
	final Logger log = LoggerFactory.getLogger(GenerarPDF.class);
//	
	static final String RUTA_FIRMAS = Funcion.RUTA_FIRMAS;
	static final String RUTA_FUENTE = Funcion.RUTA_FUENTE;
	static final String RUTA_CONFIRMACION = Funcion.RUTA_DOCUMENTOS + "ConfirmacionEntrega/";
	
//	static final String RUTA_CONFIRMACION = "/Volumes/DATOS/glassfish/domains/domain1/docroot/SAP/ConfirmacionEntrega/";
	
//	static final String RUTA_FIRMAS = "/Users/josephat.reyes/Desktop/Firmas_SAC/";
//	static final String RUTA_FUENTE = "/Users/josephat.reyes/Desktop/Fuentes/";
//	static final String RUTA_CONFIRMACION = "/Users/josephat.reyes/Desktop/";
//	static final String RUTA_FIRMAS = "/Users/viviana.romero/Desktop/notificacion/";
//	static final String RUTA_CONFIRMACION = "/Users/viviana.romero/Desktop/notificacion/";
//	static final String RUTA_FUENTE = "/Users/viviana.romero/Desktop/notificacion/";
	
	private Document document = new Document(PageSize.LETTER);
	private Document documentHorizontal = new Document(PageSize.LETTER.rotate());

/***
 * COLORES
 */
	private Color azul = new Color(0,0,139);
	private Color Proquifa = new Color(23,126,145); 
//	private Color Proquifa = new Color(45,126,156);
	private Color rojo = new Color(255,0,0);
	private Color blanco = new Color(255,255,255);
	private Color negro = new Color(0,0,0);
	private Color gris2 = new Color(105,105,105);
	private Color gris3 = new Color(137,137,137);
	private Color gris = new Color(139,139,139);
/***
 * FUENTES
 */
	
	private Font fuenteTitulo = new Font(Font.HELVETICA,20,Font.BOLDITALIC,azul);
	private Font fuenteSubTitulo = new Font(Font.HELVETICA,16,Font.BOLD, azul);
	private Font fuenteSubTitulo2 = new Font(Font.HELVETICA,12,Font.BOLD,negro);
	private Font fuenteSubTituloGris = new Font(Font.HELVETICA,8,Font.BOLD, gris3);
	private Font fuenteFolio = new Font(Font.HELVETICA,12,Font.BOLD,rojo);
	private Font fuenteFolio3 = new Font(Font.HELVETICA,9,Font.BOLD,rojo);
	private Font fuenteFolio2 = new Font(Font.HELVETICA,9,Font.BOLD,gris2);
	private Font fuenteEncabezadosTabla = new Font(Font.HELVETICA,12,Font.BOLD,blanco);
	private Font fuenteEncabezadosTablaPeque = new Font(Font.HELVETICA,8,Font.BOLD,blanco);
	private Font fuenteNumeroDePagina= new Font(Font.HELVETICA,18,Font.BOLD,blanco);
	private Font fuentePiePaginaDatos= new Font(Font.HELVETICA,7,Font.BOLD,blanco);
	private Font fuenteTextoNormal = new Font(Font.HELVETICA,11,Font.NORMAL,negro);
	private Font fuenteTextoNormal8pBold = new Font(Font.HELVETICA,8,Font.BOLD,negro);
	private Font fuenteTextoNormal8p = new Font(Font.HELVETICA,8,Font.NORMAL,negro);
	private Font fuenteTextoNormal9p = new Font(Font.HELVETICA,9,Font.NORMAL,negro);
	private Font fuenteTextoNormal7p = new Font(Font.HELVETICA,8,Font.NORMAL,negro);
	private Font fuenteTextoNormalPequeProquifa = new Font(Font.HELVETICA,8,Font.BOLD,Proquifa);
	private Font fuenteTextoDatosEncabezado = new Font(Font.HELVETICA,8,Font.BOLD,gris2);
	private Font fuenteTextoNormalTitulo = new Font(Font.HELVETICA,15,Font.BOLD,negro);
////////////////////////////////////////////
/////////// CONFIRMACION DE ENTREGA	CE
////////////////////////////////////////////
	
	private Font fuenteTextoBold8p = new Font(Font.HELVETICA,8,Font.BOLD,negro);
	private Font fuenteTituloTablaCE = new Font(Font.HELVETICA,7,Font.BOLD,blanco);
	private Font fuenteProductosCE = new Font(Font.HELVETICA,6,Font.NORMAL,gris);
	private Font fuenteTiempoCE = new Font(Font.HELVETICA,10,Font.NORMAL,negro);
	private Font fuenteTiempoNegritaCE = new Font(Font.HELVETICA,10,Font.BOLD,negro);
	

/***
 * TABLAS
 */
	private List<TablaPDF> partidas = null;
	private TablaPDF partida = null;
	private ArrayList<String> titulos = new ArrayList<String>();
	private float[] widthsRT={.5f, 1f, 3f, 3f};
	private float[] widthsRTRequisicion={.5f, 4f, 3f};
	private float[] widthsRTProforma={.9f, 1.5f, 8f, 1.5f, 1.5f};
	private float[] widthsRTConfirmacionE={.9f, 1.5f, 1.5f, 8f, 1.5f, 1.5f, 1.5f};
	private float[] widthsRTPrequi={1f, 2.5f, 8.5f, 2.5f,2.5f, 2.5f};
/***
 * 	CONTENIDO
 */
	private PdfWriter writer = null;
	private Paragraph parrafo = null;
	private PdfPCell cell = null;
	private String folioF;
	private Image image = null;
	private Image asterisco = null;
	private Image notas = null;
	private Double importeTotal=0.00;
////////////////////////////////////////////
/////////// PROFORMAS
////////////////////////////////////////////
	private Proveedor proveedorF;
	private Proforma proformaF;
	private Contacto contactoF;
	private String realizoF;
	private Requisicion requiF;
	private Contacto contaF;
////////////////////////////////////////////
/////////// CONFIRMACION DE ENTREGA	CE
////////////////////////////////////////////
	private ConfirmacionEntrega ce;
	private int partidasUltima= 1;

	
	
	public GenerarPDF(String folio){
		try {
			final String FILE = Funcion.RUTA_DOCUMENTOS + "Doctos/"+ folio +".pdf";
//			final String FILE = "/Volumes/DATOS/glassfish3/glassfish/domains/domain1/docroot/SAP/Doctos/"+ folio +".pdf";
			//final String FILE = "/Users/david.garcia/Desktop/glassfish4/glassfish/domains/domain1/docroot/SAP/Doctos/"+ folio +".pdf";
			writer = PdfWriter.getInstance(document, new FileOutputStream(FILE));
			document.addAuthor("Proquifa");
			document.addCreator("Proquifa");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}		
	}
	public GenerarPDF(String folio, String tipo){
		try {
			
//			final String FILE = "/Users/viviana.romero/Desktop/"+ folio +".pdf";  
			final String FILE = Funcion.RUTA_DOCUMENTOS + "Doctos/"+ folio +".pdf";
//			final String FILE = "/Volumes/DATOS/glassfish3/glassfish/domains/domain1/docroot/SAP/Doctos/"+ folio +".pdf";
			//final String FILE = "/Users/david.garcia/Desktop/glassfish4/glassfish/domains/domain1/docroot/SAP/Doctos/"+ folio +".pdf";
			
			if(tipo.equals("Proforma")){
				writer = PdfWriter.getInstance(documentHorizontal, new FileOutputStream(FILE));
				documentHorizontal.addAuthor("Proquifa");
				documentHorizontal.addCreator("Proquifa");
				
				HeaderFooterProforma event = new HeaderFooterProforma();
				
				documentHorizontal.setMargins(35, 35, 118, 108);	
				//documentHorizontal.setMarginMirroring(true);
		        writer.setBoxSize("art", new Rectangle(35, 54, 772.5f, 108));
		        writer.setPageEvent(event);
		        
		        
			}else if(tipo.toLowerCase().equals("confirmacionentrega")){
				writer = PdfWriter.getInstance(document, new FileOutputStream(RUTA_CONFIRMACION + folio +".pdf"));
				document.addAuthor("Proquifa");
				document.addCreator("Proquifa");
				
				HeaderFooterConfirmacion event = new HeaderFooterConfirmacion();
				
				document.setMargins(25f, 25, 25, 25);
		        writer.setBoxSize("art", new Rectangle(25, 25, 25, 25));
		        
		        writer.setPageEvent(event);
		        
			}else if(tipo.toLowerCase().equals("requisicionmovil") || tipo.toLowerCase().equals("requisiciónmóvil")){
				
				writer = PdfWriter.getInstance(documentHorizontal, new FileOutputStream(FILE));
				documentHorizontal.addAuthor("Proquifa");
				documentHorizontal.addCreator("Proquifa");
				
				HeaderFooterRequisicionMovil event = new HeaderFooterRequisicionMovil();
				
				documentHorizontal.setMargins(21.7f, 35, 118, 108);
				//documentHorizontal.setMarginMirroring(true);
		        writer.setBoxSize("art", new Rectangle(21.7f, 54, 770.3f, 108));
		        
		        writer.setPageEvent(event);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}		
	}
	
	class HeaderFooterConfirmacion extends PdfPageEventHelper{
		PdfTemplate total;
		
		
		public void onCloseDocument(PdfWriter writer, Document document) {
			BaseFont Novecentowide;
			try {
			Novecentowide = BaseFont.createFont(RUTA_FUENTE + "NovecentosansNormal.ttf",  BaseFont.WINANSI, BaseFont.EMBEDDED);
			 Font fuentePaginasCE = new Font(Novecentowide,7,Font.NORMAL,Proquifa);
			 
			 ColumnText.showTextAligned(total, Element.ALIGN_LEFT,
	                    new Phrase(String.valueOf(writer.getPageNumber() - 1), fuentePaginasCE),
	                    0, 0, 0);

			} catch (DocumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
		public void onOpenDocument(PdfWriter writer, Document document) {
			 total = writer.getDirectContent().createTemplate(30, 16);
	    }
		
		public void onEndPage(PdfWriter writer, Document document) {
			
			BaseFont Novecentowide;
			f = new Funcion();
			
			try {
					Novecentowide = BaseFont.createFont(RUTA_FUENTE + "NovecentosansNormal.ttf",  BaseFont.WINANSI, BaseFont.EMBEDDED);

					 Font fuenteTituloCE = new Font(Novecentowide,10,Font.NORMAL,negro);
					 Font fuenteSubTituloCE = new Font(Novecentowide,8,Font.NORMAL,blanco);
					 Font fuenteClienteCE = new Font(Novecentowide,12,Font.NORMAL,gris);
					 Font fuenteContactoCE = new Font(Novecentowide,12,Font.NORMAL,Proquifa);
					 Font fuentePaginasCE = new Font(Novecentowide,7,Font.NORMAL,Proquifa);
					 Font fuenteDatosGrisCE = new Font(Novecentowide,6,Font.NORMAL,gris);
					 Font fuenteDatosNegroCE = new Font(Font.HELVETICA,8,Font.BOLD,negro);
						
			            PdfContentByte cb = writer.getDirectContent();
			            float alto = document.getPageSize().getHeight() - 25;
						float ancho = document.getPageSize().getWidth();
						float renglon = 0;
			            try{    			
			    			image = Image.getInstance(RUTA_FIRMAS + "logo.jpg");
			    			renglon = 42;
			    			image.setAbsolutePosition( 29, alto - renglon );
			    			image.scalePercent(40f);
			    			//(191.04f, 49.92f);
			    			cb.addImage(image, false);
			    			
			    		}catch (Exception e) {
			    			log.info(e.getLocalizedMessage());
			    			return;
			    		}     
			           
			            renglon = 20;
			            ColumnText.showTextAligned(writer.getDirectContent(),
			                    Element.ALIGN_RIGHT, new Paragraph(String.format("NOTIFICADO DE ENTREGA", document.getPageNumber()), fuenteTituloCE), ancho - 29, alto - renglon, 0);
			            
			            renglon = 32;
//			            ColumnText.showTextAligned(writer.getDirectContent(),
//			            		Element.ALIGN_RIGHT, new Paragraph(String.format("FOLIO: ", document.getPageNumber()), fuenteDatosGrisCE), ancho - 84, alto - renglon, 0);
			            ColumnText.showTextAligned(writer.getDirectContent(),
			            		Element.ALIGN_RIGHT, new Paragraph(String.format(folioF, document.getPageNumber()), fuenteTextoBold8p), ancho -29, alto - renglon, 0);
			            
					
			            renglon = renglon + 18;
						cb.setColorStroke(Proquifa);
			
						cb.setLineWidth(14f);//Linea horizontal cliente
						cb.moveTo(29,alto - renglon);
						cb.lineTo(ancho -29, alto - renglon);
						cb.stroke();
						
						renglon = renglon + 3;
						ColumnText.showTextAligned(writer.getDirectContent(),
			                    Element.ALIGN_CENTER, new Paragraph(String.format("CLIENTE", document.getPageNumber()), fuenteSubTituloCE), (ancho/2), alto - renglon, 0);
						renglon = renglon + 23;
						ColumnText.showTextAligned(writer.getDirectContent(),
								Element.ALIGN_CENTER, new Paragraph(String.format(ce.getCliente(), document.getPageNumber()), fuenteClienteCE), (ancho/2), alto - renglon, 0);
						
						renglon = renglon + 19;
						cb.setLineWidth(14f);//Linea horizontal contacto
						cb.moveTo(29,alto - renglon);
						cb.lineTo(ancho -29, alto - renglon);
						cb.stroke();
						
						renglon = renglon + 3;
						ColumnText.showTextAligned(writer.getDirectContent(),
			                    Element.ALIGN_CENTER, new Paragraph(String.format("CONTACTO", document.getPageNumber()), fuenteSubTituloCE), (ancho/2), alto - renglon, 0);
						renglon = renglon + 25;
						ColumnText.showTextAligned(writer.getDirectContent(),
								Element.ALIGN_CENTER, new Paragraph(String.format(ce.getContacto(), document.getPageNumber()), fuenteContactoCE), (ancho/2), alto - renglon, 0);
						
						renglon = renglon + 13;
						cb.setLineWidth(2f);//Linea horizontal datos
						cb.moveTo(29,alto - renglon);
						cb.lineTo(ancho -29, alto -renglon);
						cb.stroke();
						
						renglon = renglon + 17;
						ColumnText.showTextAligned(writer.getDirectContent(),
			                    Element.ALIGN_RIGHT, new Paragraph(String.format("ORDEN DE COMPRA:", document.getPageNumber()), fuenteDatosGrisCE), 100, alto - renglon, 0);
						ColumnText.showTextAligned(writer.getDirectContent(),
								Element.ALIGN_LEFT, new Paragraph(String.format(ce.getPedidoCliente(), document.getPageNumber()), fuenteDatosNegroCE), 101, alto - renglon, 0);
						ColumnText.showTextAligned(writer.getDirectContent(),
								Element.ALIGN_RIGHT, new Paragraph(String.format("REFERENCIA:", document.getPageNumber()), fuenteDatosGrisCE), (ancho/2), alto - renglon, 0);
						ColumnText.showTextAligned(writer.getDirectContent(),
								Element.ALIGN_LEFT, new Paragraph(String.format(ce.getCpedido(), document.getPageNumber()), fuenteDatosNegroCE), (ancho/2) + 1, alto - renglon, 0);
						ColumnText.showTextAligned(writer.getDirectContent(),
								Element.ALIGN_RIGHT, new Paragraph(String.format("ENTREGADO:", document.getPageNumber()), fuenteDatosGrisCE), ancho - 140, alto - renglon, 0);
						ColumnText.showTextAligned(writer.getDirectContent(),
								Element.ALIGN_LEFT, new Paragraph(String.format(f.obtenerFormatoFecha(ce.getFecha()), document.getPageNumber()), fuenteDatosNegroCE), ancho - 139, alto - renglon, 0);
						
						renglon = renglon + 16;
						ColumnText.showTextAligned(writer.getDirectContent(),
								Element.ALIGN_RIGHT, new Paragraph(String.format("REPORTE DE PIEZAS:", document.getPageNumber()), fuenteDatosGrisCE), 100, alto - renglon, 0);
						ColumnText.showTextAligned(writer.getDirectContent(),
								Element.ALIGN_LEFT, new Paragraph(String.format(ce.getTotalPiezas().toString(), document.getPageNumber()), fuenteDatosNegroCE), 101, alto - renglon, 0);
						ColumnText.showTextAligned(writer.getDirectContent(),
								Element.ALIGN_RIGHT, new Paragraph(String.format("FACTURA:", document.getPageNumber()), fuenteDatosGrisCE), (ancho/2), alto - renglon, 0);
						ColumnText.showTextAligned(writer.getDirectContent(),
								Element.ALIGN_LEFT, new Paragraph(String.format(ce.getFactura(), document.getPageNumber()), fuenteDatosNegroCE), (ancho/2) + 1, alto - renglon, 0);
						ColumnText.showTextAligned(writer.getDirectContent(),
								Element.ALIGN_RIGHT, new Paragraph(String.format("RECIBIÓ:", document.getPageNumber()), fuenteDatosGrisCE), ancho - 140, alto - renglon, 0);
						
						
							ColumnText.showTextAligned(writer.getDirectContent(),
									Element.ALIGN_LEFT, new Paragraph(String.format(ce.getPersonaRecibio(), document.getPageNumber()), fuenteDatosNegroCE), ancho - 139, alto - renglon, 0);
						
						
						
						renglon = renglon + 19;
						cb.setLineWidth(14f);//Linea horizontal encabezado de tabla
						cb.moveTo(29,alto - renglon);
						cb.lineTo(ancho -29, alto - renglon);
						if(partidasUltima >= writer.getCurrentPageNumber()){
							cb.stroke();
						}
						
						renglon = renglon + 3;
						ColumnText.showTextAligned(writer.getDirectContent(),
			                    Element.ALIGN_CENTER, new Paragraph(String.format("#", document.getPageNumber()), fuenteTituloTablaCE), 46, alto - renglon, 0);
						ColumnText.showTextAligned(writer.getDirectContent(),
								Element.ALIGN_CENTER, new Paragraph(String.format("Cantidad", document.getPageNumber()), fuenteTituloTablaCE), 86, alto - renglon, 0);
						ColumnText.showTextAligned(writer.getDirectContent(),
								Element.ALIGN_LEFT, new Paragraph(String.format("Descripción", document.getPageNumber()), fuenteTituloTablaCE), 123, alto - renglon, 0);
						ColumnText.showTextAligned(writer.getDirectContent(),
								Element.ALIGN_CENTER, new Paragraph(String.format("FEE", document.getPageNumber()), fuenteTituloTablaCE), ancho - 141, alto - renglon, 0);
						ColumnText.showTextAligned(writer.getDirectContent(),
								Element.ALIGN_CENTER, new Paragraph(String.format("Días de atraso", document.getPageNumber()), fuenteTituloTablaCE), ancho - 76, alto - renglon, 0);
						
						
						try{    
							renglon= 75;
				    			image = Image.getInstance(RUTA_FIRMAS + "franja_Marcas.jpg");
				    			image.setAbsolutePosition( 29, renglon );
//				    			image.scalePercent(43f);
				    			image.scaleAbsolute(ancho - 58, 30f);
				    			//(191.04f, 49.92f);
				    			cb.addImage(image, false);
				    		}catch (Exception e) {
				    			log.info(e.getLocalizedMessage());
				    			return;
				    		}     
						
						
						
						renglon = 45;
						cb.setLineWidth(2f);//Linea pie de pagina
						cb.moveTo(29,renglon);
						cb.lineTo((ancho/2) -29, renglon);
						cb.stroke();
						cb.setLineWidth(2f);//Linea pie de pagina
						cb.moveTo((ancho/2) + 26,renglon);
						cb.lineTo(ancho - 29, renglon);
						cb.stroke();
						
					try{    
						renglon= 25;
			    			image = Image.getInstance(RUTA_FIRMAS + "pie_pagina.png");
			    			image.setAbsolutePosition( (ancho/2) - 20, renglon );
			    			image.scalePercent(55f);
			    			//(191.04f, 49.92f);
			    			cb.addImage(image, false);
			    		}catch (Exception e) {
			    			log.info(e.getLocalizedMessage());
			    			return;
			    		}     
					
						renglon = 42;
					ColumnText.showTextAligned(writer.getDirectContent(),
							Element.ALIGN_RIGHT, new Paragraph(document.getPageNumber() + " /" , fuentePaginasCE), ancho/2 , renglon, 0);

					 try{    			
			    			
			    			image = Image.getInstance(total);
			    			image.setAbsolutePosition(ancho/2 + 3, renglon);  	    			
			    			cb.addImage(image);
			    			
			    			
			    			
			    		}catch (Exception e) {
			    			log.info(e.getLocalizedMessage());
			    			return;
			    		}     
           
			} catch (DocumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
		
	}
	
	class HeaderFooterProforma extends PdfPageEventHelper{
		
		PdfTemplate total;

		 public void onCloseDocument(PdfWriter writer, Document document) {
			 //Rectangle rect = writer.getBoxSize("art");
			 ColumnText.showTextAligned(total, Element.ALIGN_LEFT,
	                    new Phrase(String.valueOf(writer.getPageNumber() - 1), fuenteNumeroDePagina),
	                    2, 2, 0);
	        }
		 
		 public void onOpenDocument(PdfWriter writer, Document document) {
			 total = writer.getDirectContent().createTemplate(30, 16);
	        }

		 private void encabezadoTabla(int posicion){
			 PdfContentByte cb = writer.getDirectContent();
			 
			 	cb.setColorStroke(gris3);
				cb.setLineWidth(18f); // Encabezado de tabla
				cb.moveTo(35, posicion + 2);
				cb.lineTo(757, posicion + 2);
				cb.stroke();
				
				ColumnText.showTextAligned(writer.getDirectContent(),Element.ALIGN_JUSTIFIED, new Paragraph(String.format("#", document.getPageNumber()),
						fuenteEncabezadosTablaPeque),58,posicion, 0);
				ColumnText.showTextAligned(writer.getDirectContent(),
	                    Element.ALIGN_JUSTIFIED, new Paragraph(String.format("Cantidad", document.getPageNumber()), fuenteEncabezadosTablaPeque),
	                    		105,posicion, 0);
				ColumnText.showTextAligned(writer.getDirectContent(),
	                    Element.ALIGN_JUSTIFIED, new Paragraph(String.format("Concepto", document.getPageNumber()), fuenteEncabezadosTablaPeque),
	                    		167,posicion, 0);
				ColumnText.showTextAligned(writer.getDirectContent(),
	                    Element.ALIGN_JUSTIFIED, new Paragraph(String.format("Precio U", document.getPageNumber()), fuenteEncabezadosTablaPeque),
	                    		617,posicion, 0);
				ColumnText.showTextAligned(writer.getDirectContent(),
	                    Element.ALIGN_JUSTIFIED, new Paragraph(String.format("Importe", document.getPageNumber()), fuenteEncabezadosTablaPeque),
	                    		702,posicion, 0);
				ColumnText.showTextAligned(writer.getDirectContent(),
	                    Element.ALIGN_RIGHT, new Paragraph(String.format(proveedorF.getMoneda().toUpperCase(), document.getPageNumber()), fuenteSubTituloGris),
	                    		757,posicion + 17, 0);
				
		 }
		 
		 private void tablaInformacion(){
//			 log.info("tablainformacion");
			 Rectangle rect = writer.getBoxSize("art");
	         Rectangle cabeza = new Rectangle(34, 545, 757, 590);
			 Rectangle proveeRectangle = new Rectangle(35,320,((rect.getLeft() + rect.getRight()) / 2) + 40,525);
			 Rectangle elaboracionRectangle = new Rectangle(proveeRectangle.getRight() + 20,320,cabeza.getRight(),525);
	         Date fecha = new Date();
	         PdfContentByte cb = writer.getDirectContent();
	            

				cb.setColorStroke(gris3);
				
				int yNombre = 499;
				int yDireccion = 458;
				int yAtencion = 438;
				int yEmail = 417;
				
				cb.setLineWidth(14f); // Banda proveedor
				cb.moveTo(proveeRectangle.getLeft(), 525);
				cb.lineTo(proveeRectangle.getRight(), 525);
				cb.stroke();
				
				cb.setLineWidth(1f); // Linea Nombre
				cb.moveTo(proveeRectangle.getLeft(), yNombre);
				cb.lineTo(proveeRectangle.getRight(), yNombre);
				cb.stroke();
				
				
				cb.setLineWidth(1f); // Linea Direccion
				cb.moveTo(proveeRectangle.getLeft(), yDireccion);
				cb.lineTo(proveeRectangle.getRight(), yDireccion);
				cb.stroke();
				
				cb.setLineWidth(1f); // Linea Atencion
				cb.moveTo(proveeRectangle.getLeft(), yAtencion);
				cb.lineTo(proveeRectangle.getRight(), yAtencion);
				cb.stroke();
				
				cb.setLineWidth(1f); // Linea Telefono y email
				cb.moveTo(proveeRectangle.getLeft(), yEmail);
				cb.lineTo(proveeRectangle.getRight(), yEmail);
				cb.stroke();
				ColumnText.showTextAligned(writer.getDirectContent(),
	                    Element.ALIGN_CENTER, new Paragraph(String.format("Proveedor", document.getPageNumber()), fuenteEncabezadosTablaPeque),
	                    		(proveeRectangle.getRight() + proveeRectangle.getLeft())/2 , proveeRectangle.getTop() - 3, 0);
				ColumnText.showTextAligned(writer.getDirectContent(),
	                    Element.ALIGN_JUSTIFIED, new Paragraph(String.format("Nombre", document.getPageNumber()), fuenteTextoDatosEncabezado),
	                    		proveeRectangle.getLeft() , (525+yNombre)/2 -5 , 0);
				ColumnText.showTextAligned(writer.getDirectContent(),
	                    Element.ALIGN_JUSTIFIED, new Paragraph(String.format(proveedorF.getNombre(), document.getPageNumber()), fuenteTextoNormal8pBold),
	                    		proveeRectangle.getLeft() + 80 , (525+yNombre)/2 -5 , 0);
				ColumnText.showTextAligned(writer.getDirectContent(),
	                    Element.ALIGN_JUSTIFIED, new Paragraph(String.format("Dirección", document.getPageNumber()), fuenteTextoDatosEncabezado),
	                    		proveeRectangle.getLeft() , yNombre -12 , 0);
				ColumnText.showTextAligned(writer.getDirectContent(),
	                    Element.ALIGN_JUSTIFIED, new Paragraph(String.format(proveedorF.getCalle() + " CP " + proveedorF.getCp(), document.getPageNumber()), fuenteTextoNormal8pBold),
	                    		proveeRectangle.getLeft() + 80 , yNombre -12 , 0);
				ColumnText.showTextAligned(writer.getDirectContent(),
	                    Element.ALIGN_JUSTIFIED, new Paragraph(String.format(proveedorF.getDelegacion() + " " + proveedorF.getEstado() + " " + proveedorF.getPais(), document.getPageNumber()), fuenteTextoNormal8pBold),
	                    		proveeRectangle.getLeft() + 80 , yNombre -24 , 0);
				
				ColumnText.showTextAligned(writer.getDirectContent(),
	                    Element.ALIGN_JUSTIFIED, new Paragraph(String.format("Atención", document.getPageNumber()), fuenteTextoDatosEncabezado),
	                    		proveeRectangle.getLeft() , (yDireccion+yAtencion)/2 -2 , 0);
				ColumnText.showTextAligned(writer.getDirectContent(),
	                    Element.ALIGN_JUSTIFIED, new Paragraph(String.format(contactoF.getNombre(), document.getPageNumber()), fuenteTextoNormal8pBold),
	                    		proveeRectangle.getLeft() + 80, (yDireccion+yAtencion)/2 -2 , 0);
				ColumnText.showTextAligned(writer.getDirectContent(),
	                    Element.ALIGN_JUSTIFIED, new Paragraph(String.format("Email", document.getPageNumber()), fuenteTextoDatosEncabezado),
	                    		proveeRectangle.getLeft() , (yAtencion+yEmail)/2 -2 , 0);
				if(proformaF.getOtroCorreo() == null){
					ColumnText.showTextAligned(writer.getDirectContent(),
		                    Element.ALIGN_JUSTIFIED, new Paragraph(String.format(contactoF.getEMail(), document.getPageNumber()), fuenteTextoNormal8pBold),
		                    		proveeRectangle.getLeft() + 80, (yAtencion+yEmail)/2 -2 , 0);
				}else if(proformaF.getOtroCorreo().equals("")){
				ColumnText.showTextAligned(writer.getDirectContent(),
	                    Element.ALIGN_JUSTIFIED, new Paragraph(String.format(contactoF.getEMail(), document.getPageNumber()), fuenteTextoNormal8pBold),
	                    		proveeRectangle.getLeft() + 80, (yAtencion+yEmail)/2 -2 , 0);
				}else{
					ColumnText.showTextAligned(writer.getDirectContent(),
		                    Element.ALIGN_JUSTIFIED, new Paragraph(String.format(proformaF.getOtroCorreo(), document.getPageNumber()), fuenteTextoNormal8pBold),
		                    		proveeRectangle.getLeft() + 80, (yAtencion+yEmail)/2 -2 , 0);
				}
				ColumnText.showTextAligned(writer.getDirectContent(),
	                    Element.ALIGN_JUSTIFIED, new Paragraph(String.format("Teléfono", document.getPageNumber()), fuenteTextoDatosEncabezado),
	                    		(proveeRectangle.getLeft() + proveeRectangle.getRight()) / 2 , (yAtencion+yEmail)/2 -2 , 0);
				ColumnText.showTextAligned(writer.getDirectContent(),
	                    Element.ALIGN_JUSTIFIED, new Paragraph(String.format(contactoF.getTelefono(), document.getPageNumber()), fuenteTextoNormal8pBold),
	                    (proveeRectangle.getLeft() + proveeRectangle.getRight()) / 2 + 80, (yAtencion+yEmail)/2 -2 , 0);
				
				
				cb.setColorStroke(gris3);
				cb.setLineWidth(14f); // Banda Elaboracin
				cb.moveTo(elaboracionRectangle.getLeft(), 525);
				cb.lineTo(elaboracionRectangle.getRight(), 525);
				cb.stroke();
				
				cb.setLineWidth(1f); // Linea Realizo
				cb.moveTo(elaboracionRectangle.getLeft(), yNombre);
				cb.lineTo(elaboracionRectangle.getRight(), yNombre);
				cb.stroke();
				
				cb.setLineWidth(1f); // Linea Cliente
				cb.moveTo(elaboracionRectangle.getLeft(), (yNombre + yDireccion)/2);
				cb.lineTo(elaboracionRectangle.getRight(), (yNombre + yDireccion)/2);
				cb.stroke();
				
				cb.setLineWidth(1f); // Linea fecha
				cb.moveTo(elaboracionRectangle.getLeft(), yDireccion);
				cb.lineTo(elaboracionRectangle.getRight(), yDireccion);
				cb.stroke();
				
				ColumnText.showTextAligned(writer.getDirectContent(),
	                    Element.ALIGN_CENTER, new Paragraph(String.format("Elaboración", document.getPageNumber()), fuenteEncabezadosTablaPeque),
	                    		(elaboracionRectangle.getRight() + elaboracionRectangle.getLeft())/2 , elaboracionRectangle.getTop()-3, 0);
				ColumnText.showTextAligned(writer.getDirectContent(),
	                    Element.ALIGN_JUSTIFIED, new Paragraph(String.format("Realizó", document.getPageNumber()), fuenteTextoDatosEncabezado),
	                    elaboracionRectangle.getLeft() , (525+yNombre)/2 -6 , 0);
				ColumnText.showTextAligned(writer.getDirectContent(),
	                    Element.ALIGN_JUSTIFIED, new Paragraph(String.format(realizoF, document.getPageNumber()), fuenteTextoNormal8pBold),
	                    elaboracionRectangle.getLeft() + 80 , (525+yNombre)/2 -6 , 0);
				ColumnText.showTextAligned(writer.getDirectContent(),
	                    Element.ALIGN_JUSTIFIED, new Paragraph(String.format("# Cliente", document.getPageNumber()), fuenteTextoDatosEncabezado),
	                    elaboracionRectangle.getLeft() , (yNombre+((yNombre + yDireccion)/2))/2 - 1  , 0);
				ColumnText.showTextAligned(writer.getDirectContent(),
	                    Element.ALIGN_JUSTIFIED, new Paragraph(String.format(proveedorF.getObserva(), document.getPageNumber()), fuenteTextoNormal8pBold),
	                    elaboracionRectangle.getLeft() + 80 , (yNombre+((yNombre + yDireccion)/2))/2 - 1 , 0);
				ColumnText.showTextAligned(writer.getDirectContent(),
	                    Element.ALIGN_JUSTIFIED, new Paragraph(String.format("Fecha elaboración", document.getPageNumber()), fuenteTextoDatosEncabezado),
	                    elaboracionRectangle.getLeft() , (((yNombre + yDireccion)/2)+yDireccion)/2 -1 , 0);
				ColumnText.showTextAligned(writer.getDirectContent(),
	                    Element.ALIGN_JUSTIFIED, new Paragraph(fecha.toString(), fuenteTextoNormal8pBold),
	                    elaboracionRectangle.getLeft() + 80 , (((yNombre + yDireccion)/2)+yDireccion)/2 -1 , 0);
				
//				log.info("termina tablainformacion");
		 }
		 
		public void onEndPage(PdfWriter writer, Document document) {
            Rectangle rect = writer.getBoxSize("art");
            Rectangle cabeza = new Rectangle(34, 545, 757, 590);
            PdfContentByte canvas = writer.getDirectContent();
            
	            PdfContentByte cb = writer.getDirectContent();
	            
				cb.setColorStroke(Proquifa);
				cb.setLineWidth(14f); // Banda de la parte superior
				cb.moveTo(35, 610);
				cb.lineTo(757, 610);
				cb.stroke();
								
				cb.setLineWidth(3f);//Linea horizontal de la derecha arriba
				cb.moveTo(689, 549);
				cb.lineTo(757, 549);
				cb.stroke();
				
				cb.setLineWidth(50f); //Banda de abajo
				cb.moveTo(0, 25);
				cb.lineTo(800, 25);
				cb.stroke();
				
				cb.setColorStroke(blanco);// Linea vertical en la parte de abajo
				cb.setLineWidth(1.5f);
				cb.moveTo(650, 45);
				cb.lineTo(650, 5);
				cb.stroke();
				
			if(writer.getPageNumber() == 1){
				
				tablaInformacion();
				encabezadoTabla(378);
                
            }else{
            	encabezadoTabla(502);
            }

    		
            //tipo de documento
            ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_CENTER, new Paragraph(String.format("Proforma", document.getPageNumber()), fuenteTextoNormalTitulo),
                    		cabeza.getRight() - 33 , (cabeza.getTop() + cabeza.getBottom())/2, 0);
            // folio
            ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_CENTER, new Paragraph(String.format(folioF, document.getPageNumber()), fuenteFolio2),
                    		cabeza.getRight() - 33 , ((cabeza.getTop() + cabeza.getBottom())/2) - 10, 0);
                
            //Numero de paginas
            ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_RIGHT, new Paragraph(String.format("%d  / ", document.getPageNumber()), fuenteNumeroDePagina),
                     rect.getRight() - 20, rect.getBottom() - 35, 0);
                        
            ColumnText.showTextAligned(total, Element.ALIGN_LEFT,
                    new Phrase(String.valueOf(writer.getPageNumber() - 1), fuenteFolio),
                    rect.getRight() - 43, rect.getTop() + 35, 0);
            
            ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_CENTER, new Paragraph(String.format("Proveedora Químico Farmacética SA de CV una empresa de GRUPO PROQUIFA", document.getPageNumber()), fuentePiePaginaDatos),
                    		((rect.getLeft() + rect.getRight()) / 2) - 70, rect.getTop() -70 , 0);
            ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_CENTER, new Paragraph(String.format("José María Morelos #164 Barrio Niño Jesús, Tlalpan. México DF, México, CP 14080", document.getPageNumber()), fuentePiePaginaDatos),
                    		((rect.getLeft() + rect.getRight()) / 2) - 70, rect.getTop() -77 , 0);
            ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_CENTER, new Paragraph(String.format("Contacto: Ciudad de México [13151427 / 13151498 / 29760031 / 29760033 / 29760035 / 29760036] ", document.getPageNumber()), fuentePiePaginaDatos),
                    		((rect.getLeft() + rect.getRight()) / 2) - 70, rect.getTop() -87 , 0);
            ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_CENTER, new Paragraph(String.format("Interior [01 800 8235313] Resto del mundo [ventas@proquifa.com.mx]", document.getPageNumber()), fuentePiePaginaDatos),
                    		((rect.getLeft() + rect.getRight()) / 2) - 70, rect.getTop() -94 , 0);
           
            try{  
            	image = Image.getInstance(Funcion.RUTA_DOCUMENTOS + "Doctos/logo.png");
//            	image = Image.getInstance("/Volumes/DATOS/glassfish/domains/domain1/docroot/SAP/Doctos/logo.png");
    			//image = Image.getInstance("/Users/ocardona/Desktop/ReqMovil/logo.png");
            	//image = Image.getInstance("/Users/ogarcia/glassfish/domains/domain1/docroot/SAP/Pruebas/logo.png");
    			
    			image.setAbsolutePosition(35f, 541f);
    			image.scaleToFit(120f, 50f);
    			
    			canvas.addImage(image, false);
    			image = Image.getInstance(total);
    			image.setAbsolutePosition(rect.getRight() - 10, 17);  	    			
    			canvas.addImage(image);
    		}catch (Exception e) {
    			log.info(e.getLocalizedMessage());
    			return;
    		}     
            
        }

	}
	private void createLine(PdfContentByte canvas, float xini, float yini, float xfin, float yfin,float lineWidth) {
		
		canvas.setLineWidth(lineWidth);
		if(yini==0)
			yini=documentHorizontal.getPageSize().getHeight();
		else 
			yini=documentHorizontal.getPageSize().getHeight()-yini;
		
		if(yfin ==0)
			yfin=documentHorizontal.getPageSize().getHeight();
		else
			yfin=documentHorizontal.getPageSize().getHeight()-yfin;
		
		canvas.moveTo(xini, yini);
		canvas.lineTo(xfin,yfin);
		canvas.stroke();
	}
	@SuppressWarnings("static-access")
	private void createText(PdfContentByte canvas,float x,float y,String text,Font f,int aling){
		if(y==0)
			y=documentHorizontal.getPageSize().getHeight();
		else
			y=documentHorizontal.getPageSize().getHeight()-y;
		
		Phrase phrase = new Phrase(text,f);
		ColumnText tex = new ColumnText(canvas);
		tex.setRightIndent(5);
		tex.showTextAligned(canvas, aling, phrase, x, y,0);
	}
	class HeaderFooterRequisicionMovil extends PdfPageEventHelper{
		
		PdfTemplate total;

		 public void onCloseDocument(PdfWriter writer, Document document) {
			 //Rectangle rect = writer.getBoxSize("art");
			 ColumnText.showTextAligned(total, Element.ALIGN_LEFT,
	                    new Phrase(String.valueOf(writer.getPageNumber() - 1), fuenteNumeroDePagina),
	                    2, 2, 0);
	        }
		 
		 public void onOpenDocument(PdfWriter writer, Document document) {
			 total = writer.getDirectContent().createTemplate(30,16);
	        }

		 private void encabezadoTabla(int posicion){
			 PdfContentByte cb = writer.getDirectContent();
			 Rectangle  rect=writer.getBoxSize("art");
			 
			 	cb.setColorStroke(gris3);
				cb.setLineWidth(18f); // Encabezado de tabla
				cb.moveTo(rect.getLeft(), posicion + 2);
				cb.lineTo(rect.getRight(), posicion + 2);
				cb.stroke();
			
				ColumnText.showTextAligned(writer.getDirectContent(),Element.ALIGN_JUSTIFIED, new Paragraph(String.format("#", document.getPageNumber()),
						fuenteEncabezadosTablaPeque),40,posicion, 0);
				ColumnText.showTextAligned(writer.getDirectContent(),
	                    Element.ALIGN_JUSTIFIED, new Paragraph(String.format("Cantidad", document.getPageNumber()), fuenteEncabezadosTablaPeque),
	                    		88,posicion, 0);
				ColumnText.showTextAligned(writer.getDirectContent(),
	                    Element.ALIGN_JUSTIFIED, new Paragraph(String.format("Concepto", document.getPageNumber()), fuenteEncabezadosTablaPeque),
	                    		160,posicion, 0);
				ColumnText.showTextAligned(writer.getDirectContent(),
	                    Element.ALIGN_JUSTIFIED, new Paragraph(String.format("Piezas", document.getPageNumber()), fuenteEncabezadosTablaPeque),
	                    		508,posicion, 0);
				ColumnText.showTextAligned(writer.getDirectContent(),
	                    Element.ALIGN_JUSTIFIED, new Paragraph(String.format("Importe U", document.getPageNumber()), fuenteEncabezadosTablaPeque),
	                    		606,posicion, 0);
				ColumnText.showTextAligned(writer.getDirectContent(),
	                    Element.ALIGN_JUSTIFIED, new Paragraph(String.format("Total", document.getPageNumber()), fuenteEncabezadosTablaPeque),
	                    		704,posicion, 0);
			
		 }
		 
		 private void tablaInformacion(){
			 Rectangle rect = writer.getBoxSize("art");
	         Rectangle cabeza = new Rectangle(21.7f, 545, 770.3f, 590);
			 Rectangle clienteRectangle = new Rectangle(21.7f,320,rect.getLeft()+rect.getRight()/2+53,525);
			 Rectangle elaboracionRectangle = new Rectangle(clienteRectangle.getRight() + 12,320,cabeza.getRight(),525);
	         PdfContentByte cb = writer.getDirectContent();
	         	
				cb.setColorStroke(gris3);
				
				int yNombre = 499;
				int yDireccion = 458;
				int yAtencion = 438;
//				int yEmail = 417;
				
				cb.setLineWidth(14f); // Banda cliente
				cb.moveTo(clienteRectangle.getLeft(), 525);
				cb.lineTo(clienteRectangle.getRight(), 525);
				cb.stroke();
				
				cb.setLineWidth(1f); // Linea Nombre
				cb.moveTo(clienteRectangle.getLeft(), yNombre);
				cb.lineTo(clienteRectangle.getRight(), yNombre);
				cb.stroke();
				
				
				cb.setLineWidth(1f); // Linea Direccion
				cb.moveTo(clienteRectangle.getLeft(), yDireccion);
				cb.lineTo(clienteRectangle.getRight(), yDireccion);
				cb.stroke();
				
				cb.setLineWidth(1f); // Linea Atencion
				cb.moveTo(clienteRectangle.getLeft(), yAtencion);
				cb.lineTo(clienteRectangle.getRight(), yAtencion);
				cb.stroke();
				
//				ColumnText.showTextAligned(writer.getDirectContent(),
//	                    Element.ALIGN_CENTER, new Paragraph(String.format("Cliente", document.getPageNumber()), fuenteEncabezadosTablaPeque),
//	                    		(clienteRectangle.getRight() + clienteRectangle.getLeft())/2 , clienteRectangle.getTop() - 3, 0);
				ColumnText.showTextAligned(writer.getDirectContent(),
	                    Element.ALIGN_JUSTIFIED, new Paragraph(String.format("Nombre", document.getPageNumber()), fuenteTextoDatosEncabezado),
	                    		clienteRectangle.getLeft() , (525+yNombre)/2 -5 , 0);
				ColumnText.showTextAligned(writer.getDirectContent(),
	                    Element.ALIGN_JUSTIFIED, new Paragraph(String.format(requiF.getNombreCliente().toUpperCase(), document.getPageNumber()), fuenteTextoNormal8p),
	                    		clienteRectangle.getLeft() + 48 , (525+yNombre)/2 -5 , 0);
				ColumnText.showTextAligned(writer.getDirectContent(),
	                    Element.ALIGN_JUSTIFIED, new Paragraph(String.format("Dirección", document.getPageNumber()), fuenteTextoDatosEncabezado),
	                    		clienteRectangle.getLeft() , yNombre -12 , 0);
				ColumnText.showTextAligned(writer.getDirectContent(),
	                    Element.ALIGN_JUSTIFIED, new Paragraph(String.format(contaF.getDireccion().getCalle().toUpperCase() + " CP " + contaF.getDireccion().getCodigoPostal().toUpperCase(), document.getPageNumber()), fuenteTextoNormal8p),
	                    		clienteRectangle.getLeft() + 48 , yNombre -12 , 0);
				ColumnText.showTextAligned(writer.getDirectContent(),
	                    Element.ALIGN_JUSTIFIED, new Paragraph(String.format(contaF.getDireccion().getMunicipio().toUpperCase() + " " + contaF.getDireccion().getEstado().toUpperCase() + " " + contaF.getDireccion().getPais().toUpperCase(), document.getPageNumber()), fuenteTextoNormal8p),
	                    		clienteRectangle.getLeft() + 48 , yNombre -24 , 0);
				
				ColumnText.showTextAligned(writer.getDirectContent(),
	                    Element.ALIGN_JUSTIFIED, new Paragraph(String.format("Atención", document.getPageNumber()), fuenteTextoDatosEncabezado),
	                    		clienteRectangle.getLeft() , (yDireccion+yAtencion)/2 -2 , 0);
				ColumnText.showTextAligned(writer.getDirectContent(),
	                    Element.ALIGN_JUSTIFIED, new Paragraph(String.format(contaF.getNombre().toUpperCase(), document.getPageNumber()), fuenteTextoNormal8p),
	                    		clienteRectangle.getLeft() + 48, (yDireccion+yAtencion)/2 -2 , 0);
				
				
				cb.setColorStroke(gris3);
				cb.setLineWidth(14f); // Banda Elaboración
				cb.moveTo(elaboracionRectangle.getLeft(), 525);
				cb.lineTo(elaboracionRectangle.getRight(), 525);
				cb.stroke();
				
				cb.setLineWidth(1f); // Linea Realizo
				cb.moveTo(elaboracionRectangle.getLeft(), yNombre);
				cb.lineTo(elaboracionRectangle.getRight(), yNombre);
				cb.stroke();
				
				cb.setLineWidth(1f); // Linea moneda
				cb.moveTo(elaboracionRectangle.getLeft(), yDireccion+20);
				cb.lineTo(elaboracionRectangle.getRight(), yDireccion+20);
				cb.stroke();
				
				cb.setLineWidth(1f); // Linea fecha
				cb.moveTo(elaboracionRectangle.getLeft(), yDireccion);
				cb.lineTo(elaboracionRectangle.getRight(), yDireccion);
				cb.stroke();
				
//				ColumnText.showTextAligned(writer.getDirectContent(),
//	                    Element.ALIGN_CENTER, new Paragraph(String.format("Elaboración", document.getPageNumber()), fuenteEncabezadosTablaPeque),
//	                    		(elaboracionRectangle.getRight() + elaboracionRectangle.getLeft())/2 , elaboracionRectangle.getTop()-3, 0);
				ColumnText.showTextAligned(writer.getDirectContent(),
	                    Element.ALIGN_JUSTIFIED, new Paragraph(String.format("Realizó", document.getPageNumber()), fuenteTextoDatosEncabezado),
	                    elaboracionRectangle.getLeft() , (525+yNombre)/2 -6 , 0);
				ColumnText.showTextAligned(writer.getDirectContent(),
	                    Element.ALIGN_JUSTIFIED, new Paragraph(String.format(requiF.getNombreEmpleadoEv().toUpperCase(), document.getPageNumber()), fuenteTextoNormal8p),
	                    elaboracionRectangle.getLeft() + 48 , (525+yNombre)/2 -6 , 0);
				ColumnText.showTextAligned(writer.getDirectContent(),
	                    Element.ALIGN_JUSTIFIED, new Paragraph(String.format("Fecha elaboración", document.getPageNumber()), fuenteTextoDatosEncabezado),
	                    elaboracionRectangle.getLeft() , yNombre -12, 0);
				//(((yNombre + yDireccion)/2)+yDireccion)/2 -1
				
				Calendar ftemp = Calendar.getInstance();
				ftemp.setTime(requiF.getFecha());
				SimpleDateFormat formato = new SimpleDateFormat("dd - MM - yyyy");
				
				String ftem;
				ftem =formato.format(ftemp.getTime());
				ColumnText.showTextAligned(writer.getDirectContent(),
	                    Element.ALIGN_JUSTIFIED, new Paragraph(ftem, fuenteFolio3),
	                    elaboracionRectangle.getLeft() + 84 , yNombre -12 , 0);
				
				ColumnText.showTextAligned(writer.getDirectContent(),
	                    Element.ALIGN_JUSTIFIED, new Paragraph(String.format("Moneda", document.getPageNumber()), fuenteTextoDatosEncabezado),
	                    elaboracionRectangle.getLeft() , yNombre -34, 0);
				
				ColumnText.showTextAligned(writer.getDirectContent(),
	                    Element.ALIGN_JUSTIFIED, new Paragraph("DOLARES", fuenteTextoNormal8p),
	                    elaboracionRectangle.getLeft() + 48 , yNombre -34 , 0);
		 }
		 
		public void onEndPage(PdfWriter writer, Document document) {
            Rectangle rect = writer.getBoxSize("art");
            PdfContentByte canvas = writer.getDirectContent();
            
            	//Azul
	            canvas.setRGBColorStroke(45,126,156); 
	    		createLine(canvas,22,5,772,5,10);
	    		createLine(canvas,666,63,772,63,3);
	    		createLine(canvas,0,588,792,588,48);
 		
	    		canvas.setRGBColorStroke(255,255,255);
	    		createLine(canvas,665,571,665,608,1.5f);
				
			if(writer.getPageNumber() == 1){
				
				tablaInformacion();
				encabezadoTabla(405);
                
            }else{
            	encabezadoTabla(522);
            }
                    
            createText(canvas, 772, 42.5f, "Requisición Móvil", fuenteSubTitulo2, Element.ALIGN_RIGHT);
            createText(canvas, 772, 55, requiF.getFolio(), fuenteFolio3, Element.ALIGN_RIGHT);

            createText(canvas, 733, 593, String.format("%d  / ", document.getPageNumber()), fuenteNumeroDePagina, Element.ALIGN_RIGHT);
 
            ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_CENTER, new Paragraph(String.format("Proveedora Químico Farmacéutica SA de CV una empresa de GRUPO PROQUIFA", document.getPageNumber()), fuentePiePaginaDatos),
                    		((rect.getLeft() + rect.getRight()) / 2) - 70, rect.getTop() -70 , 0);
            ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_CENTER, new Paragraph(String.format("José María Morelos #164 Barrio Niño Jesús, Tlalpan. México DF, México, CP 14080", document.getPageNumber()), fuentePiePaginaDatos),
                    		((rect.getLeft() + rect.getRight()) / 2) - 70, rect.getTop() -77 , 0);
            ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_CENTER, new Paragraph(String.format("Contacto: Ciudad de México [13151427 / 13151498 / 29760031 / 29760033 / 29760035 / 29760036] ", document.getPageNumber()), fuentePiePaginaDatos),
                    		((rect.getLeft() + rect.getRight()) / 2) - 70, rect.getTop() -87 , 0);
            ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_CENTER, new Paragraph(String.format("Interior [01 800 8235313] Resto del mundo [ventas@proquifa.com.mx]", document.getPageNumber()), fuentePiePaginaDatos),
                    		((rect.getLeft() + rect.getRight()) / 2) - 70, rect.getTop() -94 , 0);
           
            try{    			
    			//image = Image.getInstance("/Users/ogarcia/glassfish/domains/domain1/docroot/SAP/logo.png");
    			image = Image.getInstance(Funcion.RUTA_DOCUMENTOS + "Doctos/logo.png");
//    			image = Image.getInstance("/Volumes/DATOS/glassfish/domains/domain1/docroot/SAP/Doctos/logo.png");
    			
            	//image = Image.getInstance("/Users/ocardona/Desktop/ReqMovil/logo.png");
            	//image = Image.getInstance("/Users/jhidalgo/Desktop/logo.png");
    			
    			image.setAbsolutePosition(10f, 548f);
    			image.scaleAbsolute(157.02f, 47.02f);
    			
    			canvas.addImage(image, false);
    			
    			
    			image = Image.getInstance(total);
    			image.setAbsolutePosition(742,17);
    			canvas.addImage(image);
    		}catch (Exception e) {
    			log.info(e.getLocalizedMessage());
    			return;
    		}     
            
        }

	}
	
	
	private PdfPTable dibujarTabla (ArrayList<String> encabezados, List<TablaPDF> datos, String Doc,float[] anchos, Boolean conEncabezados, List<PartidaProforma> partidaProforma,List<PRequisicion> prequi) throws DocumentException{
		PdfPTable table = new PdfPTable(encabezados.size());
		table.setWidths(anchos);
		table.setWidthPercentage(100f);
		
		if(Doc.toLowerCase().equals("requisicionTelefonica")||Doc.toLowerCase().equals("proforma") || Doc.toLowerCase().equals("productoAConfirmar")){
			try{    			
				asterisco = Image.getInstance("/Users/vromero/Desktop/aterisco.png");
				//asterisco = Image.getInstance("/Users/ogarcia/glassfish/domains/domain1/docroot/SAP/Pruebas/aterisco.png");
				asterisco.scaleToFit(1f, 1f);
				
				notas = Image.getInstance("/Users/vromero/Desktop/notas.png");
				//notas = Image.getInstance("/Users/ogarcia/glassfish/domains/domain1/docroot/SAP/Pruebas/notas.png");
				notas.scaleToFit(1f, 1f);
	
			}catch (Exception e) {
				log.info(e.getLocalizedMessage());
				return null;
			}     
		}
		
//		log.info("encabezado");
		if(conEncabezados == true){
			for(int i=0; i<encabezados.size(); i++){
				cell = new PdfPCell();
				cell.setBackgroundColor(Proquifa);
				cell.setBorderColor(Proquifa);
				parrafo = new Paragraph(encabezados.get(i),fuenteEncabezadosTabla);
				parrafo.setAlignment(Element.ALIGN_CENTER);
				cell.addElement(parrafo);
				cell.setFixedHeight(30L);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE  - 5);
				table.addCell(cell);
			}
		}

		if(Doc.equals("requisicionTelefonica")){
			for(int j=0; j<datos.size(); j++){
				table.addCell(new Paragraph(String.valueOf(j+1),fuenteTextoNormal));
				table.addCell(new Paragraph(datos.get(j).getCantidad().toString(),fuenteTextoNormal));
				table.addCell(new Paragraph(datos.get(j).getDescripcion().toString(),fuenteTextoNormal));
				table.addCell(new Paragraph(datos.get(j).getComentarios().toString(),fuenteTextoNormal));
			}
		}
		if(Doc.equals("proforma")){
//			log.info("proforma");
			int k = 0;
			float anchos1[] ={0.135f, 5f};
			for(PartidaProforma p:partidaProforma){
//				log.info("empieza a obtener datos");
				PdfPTable tableDescripcion = new PdfPTable(1);
				PdfPTable tableExtra = new PdfPTable(2);
				tableDescripcion.setWidthPercentage(100f);
				tableExtra.setWidthPercentage(100f);
				
				tableExtra.setWidths(anchos1);
				k++;
				parrafo = new Paragraph(String.valueOf(k),fuenteTextoNormal8pBold);
				parrafo.setAlignment(Element.ALIGN_CENTER);
				table.addCell(celdaParrafo(parrafo, blanco, blanco, 0L, Element.ALIGN_TOP, 0f, 0f, 0f, 0f, 15f, 0f, 0f, 5f));
				parrafo = new Paragraph(p.getCantidadPedida()+"",fuenteTextoNormal8pBold);
				parrafo.setAlignment(Element.ALIGN_CENTER);
				table.addCell(celdaParrafo(parrafo, blanco, blanco, 0L, Element.ALIGN_TOP, 0f, 0f, 0f, 0f, 15f, 0f, 0f, 5f));
				parrafo = new Paragraph(p.getConceptoProducto()+"",fuenteTextoNormal8pBold);
				parrafo.setAlignment(Element.ALIGN_JUSTIFIED);
				tableDescripcion.addCell(celdaParrafo(parrafo, blanco, blanco, 0L, Element.ALIGN_TOP, 0f, 0f, 15f, 0f, 0f, 0f, 0f, 5f));
				if(p.getCaducidad() != null){
					if(!p.getCaducidad().equals("No especificado")){
//						log.info("caducidad");
					// Caducidad
						cell = new PdfPCell();
						cell.addElement(asterisco);
						cell.setBorderWidthLeft(0);
						cell.setBorderWidthBottom(0);
						cell.setBorderWidthRight(0);
						cell.setBorderWidthTop(0);
						cell.setPaddingBottom(0f);
						cell.setPaddingLeft(5f);
						cell.setPaddingRight(0);
						cell.setPaddingTop(0f);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						tableExtra.addCell(cell);
						
						parrafo = new Paragraph("Caducidad: " + p.getCaducidad(),fuenteTextoNormal8pBold);
						parrafo.setAlignment(Element.ALIGN_JUSTIFIED);
						tableExtra.addCell(celdaParrafo(parrafo, blanco, blanco, 0L, Element.ALIGN_BOTTOM , 0f, 0f, 0f, 0f, 3f, 5f, 0f, 3f));
					}
				}
//				log.info("pasa caducidad");
				if(p.getLote() != null){
					if(!p.getLote().equals("No especificado")){
						// Lote
							cell = new PdfPCell();
							cell.addElement(asterisco);
							cell.setBorderWidthLeft(0);
							cell.setBorderWidthBottom(0);
							cell.setBorderWidthRight(0);
							cell.setBorderWidthTop(0);
							cell.setPaddingBottom(0f);
							cell.setPaddingLeft(5f);
							cell.setPaddingRight(0);
							cell.setPaddingTop(0f);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							tableExtra.addCell(cell);
							
							parrafo = new Paragraph("Lote: " + p.getLote(),fuenteTextoNormal8pBold);
							parrafo.setAlignment(Element.ALIGN_JUSTIFIED);
							tableExtra.addCell(celdaParrafo(parrafo, blanco, blanco, 0L, Element.ALIGN_BOTTOM , 0f, 0f, 0f, 0f, 3f, 5f, 0f, 3f));
					}
				}
				
					if(p.getEdicion() != null){
					// Edicon
						cell = new PdfPCell();
						cell.addElement(asterisco);
						cell.setBorderWidthLeft(0);
						cell.setBorderWidthBottom(0);
						cell.setBorderWidthRight(0);
						cell.setBorderWidthTop(0);
						cell.setPaddingBottom(0f);
						cell.setPaddingLeft(5f);
						cell.setPaddingRight(0);
						cell.setPaddingTop(0f);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						tableExtra.addCell(cell);
						
						parrafo = new Paragraph("Edición: " + p.getCaducidad(),fuenteTextoNormal8pBold);
						parrafo.setAlignment(Element.ALIGN_JUSTIFIED);
						tableExtra.addCell(celdaParrafo(parrafo, blanco, blanco, 0L, Element.ALIGN_BOTTOM , 0f, 0f, 0f, 0f, 3f, 5f, 0f, 3f));
					}	
				
				for(NotasProforma n:p.getNotas()){
					cell = new PdfPCell();
					cell.addElement(notas);
					cell.setBorderWidthLeft(0);
					cell.setBorderWidthBottom(0);
					cell.setBorderWidthRight(0);
					cell.setBorderWidthTop(0);
					cell.setPaddingBottom(0f);
					cell.setPaddingLeft(5f);
					cell.setPaddingRight(0);
					cell.setPaddingTop(0f);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					tableExtra.addCell(cell);
					if(n.getNota() != null){
						parrafo = new Paragraph(n.getNota(),fuenteTextoNormal8pBold);
					}else{
						parrafo = new Paragraph(" ",fuenteTextoNormal8pBold);
					}
					parrafo.setAlignment(Element.ALIGN_JUSTIFIED);
					tableExtra.addCell(celdaParrafo(parrafo, blanco, blanco, 0L, Element.ALIGN_BOTTOM , 0f, 0f, 0f, 0f, 3f, 5f, 0f, 3f));
				}
				
				cell = new PdfPCell();
				cell.addElement(tableExtra);
				cell.setBorderWidthLeft(0);
				cell.setBorderWidthBottom(0);
				cell.setBorderWidthRight(0);
				cell.setBorderWidthTop(0);
				cell.setPaddingBottom(0);
				cell.setPaddingLeft(0);
				cell.setPaddingRight(0);
				cell.setPaddingTop(0);	
				tableDescripcion.addCell(cell);

				cell = new PdfPCell();
				cell.addElement(tableDescripcion);
				cell.setBorderWidthLeft(0);
				cell.setBorderWidthBottom(0);
				cell.setBorderWidthRight(0);
				cell.setBorderWidthTop(0);
				cell.setPaddingBottom(15);
				cell.setPaddingLeft(0);
				cell.setPaddingRight(0);
				cell.setPaddingTop(0);				
				table.addCell(cell);

				BigDecimal big = new BigDecimal(p.getCostoCDesc()+"");
				
				parrafo = new Paragraph(big.setScale(2, RoundingMode.HALF_UP) +"",fuenteTextoNormal8pBold);
				parrafo.setAlignment(Element.ALIGN_RIGHT);
				table.addCell(celdaParrafo(parrafo, blanco, blanco, 0L, Element.ALIGN_TOP, 0f, 0f, 0f, 0f, 15f, 0f, 30f, 5f));

				importeTotal = importeTotal + (p.getCantidadPedida() * p.getCostoCDesc());
				
				big = new BigDecimal((p.getCantidadPedida() * p.getCostoCDesc()) +"");
				parrafo = new Paragraph(big.setScale(2, RoundingMode.HALF_UP) +"",fuenteTextoNormal8pBold);
				parrafo.setAlignment(Element.ALIGN_RIGHT);
				table.addCell(celdaParrafo(parrafo, blanco, blanco, 0L, Element.ALIGN_TOP, 0f, 0f, 0f, 0f, 15f, 0f, 30f, 5f));
				
				
			}
			
		}
		if(Doc.equals("requisicion")){
			int k = 0;
			String concept = "";
			for(PRequisicion p:prequi){
				k++;
				parrafo = new Paragraph(String.valueOf(k),fuenteTextoNormal9p);
				parrafo.setAlignment(Element.ALIGN_CENTER);
				table.addCell(celdaParrafo(parrafo, blanco, blanco, 0L, Element.ALIGN_TOP, 0f, 0f, 0f, 0f, 15f, 0f, 0f, 5f));
				if(p.getCantidad()!=0){
					parrafo = new Paragraph(p.getCantidad()+" "+p.getUnidad(),fuenteTextoNormal9p);
					parrafo.setAlignment(Element.ALIGN_CENTER);
					table.addCell(celdaParrafo(parrafo, blanco, blanco, 0L, Element.ALIGN_TOP, 0f, 0f, 15f, 0f, 0f, 0f, 0f, 5f));
				}else{
					parrafo = new Paragraph(p.getUnidad(),fuenteTextoNormal9p);
					parrafo.setAlignment(Element.ALIGN_CENTER);
					table.addCell(celdaParrafo(parrafo, blanco, blanco, 0L, Element.ALIGN_TOP, 0f, 0f, 15f, 0f, 0f, 0f, 0f, 5f));							
				}
				
				if(p.getMarca().trim().length()!=0 && p.getMarca()!=null){
					concept = p.getConcepto()+" - "+ p.getMarca().trim();
				}else{
					concept = p.getConcepto();
				}
				
				parrafo = new Paragraph(concept,fuenteTextoNormal9p);
				parrafo.setAlignment(Element.ALIGN_LEFT);
				table.addCell(celdaParrafo(parrafo, blanco, blanco, 0L, Element.ALIGN_TOP, 0f, 0f, 15f, 0f, 0f, 5f, 5f, 5f));

				parrafo = new Paragraph(p.getPiezasACotizar()+" ",fuenteTextoNormal9p);
				parrafo.setAlignment(Element.ALIGN_CENTER);
				table.addCell(celdaParrafo(parrafo, blanco, blanco, 0L, Element.ALIGN_TOP, 0f, 0f, 0f, 0f, 15f, 0f, 5f, 5f));

				BigDecimal big = new BigDecimal(p.getPrecioU()+"");
				
				parrafo = new Paragraph(big.setScale(2, RoundingMode.HALF_UP) +"",fuenteTextoNormal9p);
				parrafo.setAlignment(Element.ALIGN_RIGHT);
				table.addCell(celdaParrafo(parrafo, blanco, blanco, 0L, Element.ALIGN_TOP, 0f, 0f, 0f, 0f, 15f, 0f, 30f, 5f));

				importeTotal = importeTotal + (p.getPrecioU()*p.getPiezasACotizar());
				
				big = new BigDecimal((p.getPrecioU()*p.getPiezasACotizar()) +"");
				parrafo = new Paragraph(big.setScale(2, RoundingMode.HALF_UP) +"",fuenteTextoNormal9p);
				parrafo.setAlignment(Element.ALIGN_RIGHT);
				table.addCell(celdaParrafo(parrafo, blanco, blanco, 0L, Element.ALIGN_TOP, 0f, 0f, 0f, 0f, 15f, 0f, 30f, 5f));
			}
		}
		
		table.setHorizontalAlignment(Element.ALIGN_CENTER);
		if(Doc.equals("productoAConfirmar")){
			for(int j=0; j<datos.size(); j++){
				
				cell = new PdfPCell();
				cell.setFixedHeight(30L);
				parrafo = new Paragraph(new Paragraph(String.valueOf(j+1),fuenteTextoNormal));
				parrafo.setAlignment(Element.ALIGN_CENTER);
				cell.addElement(parrafo);
				table.addCell(cell);
				
				cell = new PdfPCell();
				//cell.setFixedHeight(30L);
				parrafo = new Paragraph(datos.get(j).getDescripcion().toString(),fuenteTextoNormal);
				parrafo.setAlignment(Element.ALIGN_JUSTIFIED);
				cell.addElement(parrafo);
				table.addCell(cell);
				
				cell = new PdfPCell();
				parrafo = new Paragraph(new Paragraph(datos.get(j).getCantidad().toString(),fuenteTextoNormal));
				parrafo.setAlignment(Element.ALIGN_CENTER);
				cell.addElement(parrafo);
				table.addCell(cell);
			}
		}
		
		if(Doc.equals("confirmacionEntrega")){
for(int j=0; j<datos.size(); j++){
				
				cell = new PdfPCell();
				cell.setFixedHeight(30L);
				parrafo = new Paragraph(new Paragraph(String.valueOf(j+1),fuenteTextoNormal));
				parrafo.setAlignment(Element.ALIGN_CENTER);
				cell.addElement(parrafo);
				table.addCell(cell);
				
				cell = new PdfPCell();
				//cell.setFixedHeight(30L);
				parrafo = new Paragraph(datos.get(j).getDescripcion().toString(),fuenteTextoNormal);
				parrafo.setAlignment(Element.ALIGN_JUSTIFIED);
				cell.addElement(parrafo);
				table.addCell(cell);
				
				cell = new PdfPCell();
				parrafo = new Paragraph(new Paragraph(datos.get(j).getCantidad().toString(),fuenteTextoNormal));
				parrafo.setAlignment(Element.ALIGN_CENTER);
				cell.addElement(parrafo);
				table.addCell(cell);
			}
		}
		
		return table;
	}


	public void requisicionTelefonica (List<PartidaCotizacion> pcotiza, String usuario) {	
		partidas = new ArrayList<TablaPDF>();
		cell = new PdfPCell();
		//los tamaños para las columnas de las tablas
		
		try {
			document.open();
			
			PdfContentByte cb = writer.getDirectContent();
			Paragraph tituloProquifa = new Paragraph("PROQUIFA",fuenteTitulo);
			tituloProquifa.setAlignment(Element.ALIGN_RIGHT);
			document.add(tituloProquifa);
			
			cb.setColorStroke(azul);
			cb.moveTo(30,710);
			cb.lineTo(585,710);
			cb.stroke();
			
			document.add(new Paragraph(" "));		
			PdfPTable tituloUsuario = new PdfPTable(2);
			tituloUsuario.setWidthPercentage(100f);

			parrafo = new Paragraph("Requisición telefónica",fuenteSubTitulo);
			cell = new PdfPCell(); cell.setBorder(0);
	        cell.addElement(parrafo);
			tituloUsuario.addCell(cell);

	        parrafo = new Paragraph(usuario,fuenteFolio);
	        parrafo.setAlignment(Element.ALIGN_RIGHT);
	        cell = new PdfPCell(); cell.setBorder(0);
	        cell.addElement(parrafo);
	        tituloUsuario.addCell(cell);

	        document.add(tituloUsuario);
	        
	        cb.setColorStroke(azul);
			cb.moveTo(30,665);
			cb.lineTo(585,665);
			cb.stroke();

			document.add(Chunk.NEWLINE);
			titulos.add("#");
			titulos.add("Cantidad");
			titulos.add("Descripción");
			titulos.add("Comentarios");
	        
			for(PartidaCotizacion p:pcotiza){
				partida = new TablaPDF();
				partida.setCantidad(p.getCantidad());
				partida.setDescripcion(p.getConcepto());
				partida.setComentarios(p.getComentariosRequisicion());
				partidas.add(partida);
			}
	        
			document.add(dibujarTabla(titulos,partidas,"requisicionTelefonica",widthsRT, true, null,null));
			document.close();

		} catch (DocumentException de) {
            System.err.println(de.getMessage());
        } 
	}
	
	public void productoAConfirmar (List<PartidaConfirmacion> partidasConfirmacion, String folio) {	
		partidas = new ArrayList<TablaPDF>();
		cell = new PdfPCell();
		widthsRTRequisicion[0]=.5f;
		widthsRTRequisicion[1]=6f;
		widthsRTRequisicion[2]=1f;
		try {
			
			document.open();
			
			PdfContentByte cb = writer.getDirectContent();
			Paragraph tituloProquifa = new Paragraph("PHARMA SCIENTIFIC, INC.",fuenteTitulo);
			tituloProquifa.setAlignment(Element.ALIGN_RIGHT);
			document.add(tituloProquifa);
			document.add(new Paragraph(" "));

			cb.setColorStroke(azul);
			cb.moveTo(30,720);
			cb.lineTo(585,720);
			cb.stroke();
			
			
			PdfPTable tituloUsuario = new PdfPTable(2);
			tituloUsuario.setWidthPercentage(100f);
			
			parrafo = new Paragraph(folio,fuenteFolio);
			parrafo.setAlignment(Element.ALIGN_LEFT);
			cell = new PdfPCell(); cell.setBorder(0);
	        cell.addElement(parrafo);
	        tituloUsuario.addCell(cell);
			
			Paragraph datosProquifa = new Paragraph("2820 San Bernardo Ave., Suite #9 \n Laredo Tx. 78040\n PH: (956) 729 - 8110\n FAX: (956) 729 - 8112",fuenteTextoNormal);
			datosProquifa.setAlignment(Element.ALIGN_RIGHT);
			cell = new PdfPCell(); cell.setBorder(0);
	        cell.addElement(datosProquifa);
			tituloUsuario.addCell(cell);
			
			
	        document.add(tituloUsuario);
			
			document.add(new Paragraph(" "));	
			
			titulos.add("#");
			titulos.add("DESCRIPTION");
			titulos.add("QTY");
			document.add(new Paragraph(" "));	
	        
			for(PartidaConfirmacion p:partidasConfirmacion){
				partida = new TablaPDF();
				partida.setCantidad(p.getNoPiezas());
				partida.setDescripcion(p.getCodigo() + " " + p.getDescripcionProducto());
				partidas.add(partida);
			}
	        
			partida = new TablaPDF();
			document.add(dibujarTabla(titulos,partidas,"productoAConfirmar",widthsRTRequisicion,true, null,null));
			Paragraph cierre = new Paragraph(	"WE WILL APPRECIATED IF YOU  CAN PROVIDE US THE FOLLOWING INFORMATION FOR EACH ITEM \n" +
												"- LIST PRICE \n" +
												"- DELIVERY TIME \n" +
												"- CAN WE FIND IT IN STOCK? \n" +
												"- SHIPPING CHARGES OR ANY OTHER ADDITIONAL COST \n" +
												"- DRY ICE? (if it apply) \n" +
												"- ONLY FOR BOOKS: THE MOST RECENTLY EDITION \n" +
												"- ADDITIONAL COMMENTS \n", fuenteTextoNormal);
			cierre.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(new Paragraph(" "));
			document.add(cierre);
			document.close();

		} catch (DocumentException de) {
            System.err.println(de.getMessage());
        } 
	}
	

	public void proforma(Proforma proforma, List<PartidaProforma> partidaProforma, String folio, Proveedor proveedor, 
			Contacto contacto, String realizo, String datosFactura, String datosEntrega) {
		partidas = new ArrayList<TablaPDF>();
		cell = new PdfPCell();

		folioF = folio;
		proveedorF = proveedor;
		proformaF = proforma;
		contactoF = contacto;
		realizoF = realizo;
		
		try {
			documentHorizontal.open();			
			documentHorizontal.add(new Paragraph(" "));	
			documentHorizontal.add(new Paragraph(" "));	
			documentHorizontal.add(new Paragraph(" "));	
			documentHorizontal.add(new Paragraph(" "));	
			documentHorizontal.add(new Paragraph(" "));	
			documentHorizontal.add(new Paragraph(" "));	
			
			titulos.add("#");
			titulos.add("Cantidad");
			titulos.add("Decripción");
			titulos.add("Precio U");
			titulos.add("Importe");
			documentHorizontal.add(new Paragraph(" "));	
			documentHorizontal.add(dibujarTabla(titulos,partidas,"proforma",widthsRTProforma, false,partidaProforma,null));
			documentHorizontal.add(new Paragraph(" "));	
			documentHorizontal.add(new Paragraph(" "));	
			documentHorizontal.add(finalProforma(proforma, importeTotal, datosFactura, datosEntrega));
			documentHorizontal.close();
		} catch (DocumentException de) {
            System.err.println(de.getMessage());
        } 
	}

	public void requicisionMovil(Requisicion requi, List<PRequisicion> prequi,Contacto contacto) {
		partidas = new ArrayList<TablaPDF>();
		cell = new PdfPCell();

		folioF = requi.getIdRequi().toString();
		//ClienteF = proveedor;
		//proformaF = proforma;
		//contactoF = contacto;
		//realizoF = realizo;
		requiF = requi;
		contaF = contacto;

		try {
			documentHorizontal.open();			
			documentHorizontal.add(new Paragraph(" "));	
			documentHorizontal.add(new Paragraph(" "));	
			documentHorizontal.add(new Paragraph(" "));	
			documentHorizontal.add(new Paragraph(" "));	
			documentHorizontal.add(new Paragraph(" "));	
			documentHorizontal.add(new Paragraph(" "));	
			
			titulos.add("#");
			titulos.add("Cantidad");
			titulos.add("Decripción");
			titulos.add("Piezas");
			titulos.add("Precio U");
			titulos.add("Importe");

			documentHorizontal.add(dibujarTabla(titulos,null,"requisicion",widthsRTPrequi, false,null,prequi));
			documentHorizontal.add(new Paragraph(" "));	
			documentHorizontal.add(finalRequisicion(requi, importeTotal));
			
			documentHorizontal.close();
		} catch (DocumentException de) {
            System.err.println(de.getMessage());
        } 
	}
	
	private PdfPCell celdaParrafo(Paragraph parrafo, Color background, Color borde, Long height, int vertical,  float bl, float bb, 
			float br, float bt, float pb, float pl, float pr, float pt ){
		cell = new PdfPCell();
		cell.setBackgroundColor(background);
		cell.setBorderColor(borde);
		cell.setFixedHeight(height);
		cell.addElement(parrafo);
		cell.setVerticalAlignment(vertical);
		cell.setBorderWidthLeft(bl);
		cell.setBorderWidthBottom(bb);
		cell.setBorderWidthRight(br);
		cell.setBorderWidthTop(bt);
		cell.setPaddingBottom(pb);
		cell.setPaddingLeft(pl);
		cell.setPaddingRight(pr);
		cell.setPaddingTop(pt);
//		cell.setBorderColor(negro);
//		cell.setBorderWidthLeft(0.1f);
//		cell.setBorderWidthRight(0.1f);
		return cell;
	}
	
	private PdfPTable finalProforma(Proforma proforma, Double importeTotal, String datosFactura, String datosEntrega){
		try {
			
//			log.info(proforma.getQuienCompra());
			
			PdfPTable table1 = new PdfPTable(2);
			PdfPTable table2 = new PdfPTable(1);
			PdfPTable table3 = new PdfPTable(3);
			PdfPTable table4 = new PdfPTable(2);
			PdfPTable table = new PdfPTable(4);
			
			float anchos1[] ={24f, 5f};
			float anchos2[] ={25f};
			float anchos3[] ={.1f, 2.5f, 3.5f};
			float anchos4[] ={.1f, 5f};
			float anchos[] ={7f, 7f, 9f, .5f};
			
			table1.setWidths(anchos1);
			table2.setWidths(anchos2);
			table3.setWidths(anchos3);
			table4.setWidths(anchos4);
			table.setWidths(anchos);
			
			table1.setWidthPercentage(100f);
			table2.setWidthPercentage(100f);
			table3.setWidthPercentage(100f);
			table4.setWidthPercentage(100f);
			table.setWidthPercentage(100f);

			parrafo = new Paragraph("                       FACTURACIÓN",fuenteEncabezadosTablaPeque);
			parrafo.setAlignment(Element.ALIGN_LEFT);
			table.addCell(celdaParrafo(parrafo, gris3, blanco, 15L, Element.ALIGN_TOP, 0f, 0f, 17f, 0f, 0f, 0f, 0f, 0f));
			parrafo = new Paragraph("                             ENTREGA",fuenteEncabezadosTablaPeque);
			parrafo.setAlignment(Element.ALIGN_LEFT);
			table.addCell(celdaParrafo(parrafo, gris3, blanco, 15L, Element.ALIGN_TOP, 0f, 0f, 17f, 0f, 0f, 0f, 0f, 0f));
			parrafo = new Paragraph("                                     ADICIONALES",fuenteEncabezadosTablaPeque);
			parrafo.setAlignment(Element.ALIGN_LEFT);
			table.addCell(celdaParrafo(parrafo, gris3, blanco, 15L, Element.ALIGN_TOP, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f));
			parrafo = new Paragraph(" ",fuenteEncabezadosTablaPeque);
			parrafo.setAlignment(Element.ALIGN_LEFT);
			table.addCell(celdaParrafo(parrafo, blanco, blanco, 15L, Element.ALIGN_TOP, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f));
			
			parrafo = new Paragraph(datosFactura,fuenteTextoNormal7p);
			parrafo.setAlignment(Element.ALIGN_JUSTIFIED);
			table.addCell(celdaParrafo(parrafo, blanco, gris3, 0L, Element.ALIGN_TOP, 0f, 1f, 0f, 0f, 5f, 0f, 17f, 1f));
			parrafo = new Paragraph(datosEntrega,fuenteTextoNormal7p);
			parrafo.setAlignment(Element.ALIGN_JUSTIFIED);
			table.addCell(celdaParrafo(parrafo, blanco, gris3, 0L, Element.ALIGN_TOP, 0f, 1f, 0f, 0f, 5f, 0f, 17f, 1f));
			parrafo = new Paragraph("Enviar junto con los productos toda la documentación correspondiente (Hojas de seguridad, certificados, instructivos de uso) de lo contrario la orden no será recibida. Recepción de material de las 8.00 a las 13.00 horas y de las 14.00 a las.17.30 horas",fuenteTextoNormal7p);
			parrafo.setAlignment(Element.ALIGN_JUSTIFIED);
			table.addCell(celdaParrafo(parrafo, blanco, gris3, 0L, Element.ALIGN_TOP, 0f, 1f, 0f, 0f, 10f, 0f, 17f, 1f));
			parrafo = new Paragraph(" ",fuenteEncabezadosTablaPeque);
			parrafo.setAlignment(Element.ALIGN_LEFT);
			table.addCell(celdaParrafo(parrafo, blanco, blanco, 0L, Element.ALIGN_TOP, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f));
		
		
	
		cell = new PdfPCell();
		cell.setBackgroundColor(blanco);
		cell.setBorderColor(blanco);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setBorderWidthLeft(0f);
		cell.setBorderWidthBottom(0f);
		cell.setBorderWidthRight(0f);
		cell.setBorderWidthTop(0f);
		cell.setPaddingBottom(0f);
		cell.setPaddingLeft(0f);
		cell.setPaddingRight(0f);
		cell.setPaddingTop(0f);
		cell.addElement(table);		
		table2.addCell(cell);

		////Aqui esta la notas		
	//	table2.addCell(celdaParrafo(new Paragraph(proforma.getComentarios(), fuenteTextoNormal8p), blanco, blanco, 0L, Element.ALIGN_TOP, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f));
		
		cell = new PdfPCell();
		cell.setBackgroundColor(blanco);
		cell.setBorderColor(blanco);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setBorderWidthLeft(0f);
		cell.setBorderWidthBottom(0f);
		cell.setBorderWidthRight(0f);
		cell.setBorderWidthTop(0f);
		cell.setPaddingBottom(0f);
		cell.setPaddingLeft(0f);
		cell.setPaddingRight(0f);
		cell.setPaddingTop(0f);
		cell.addElement(notas);
		table4.addCell(cell);
		table4.addCell(celdaParrafo(new Paragraph(proforma.getComentarios(), fuenteTextoNormal8p), blanco, blanco, 0L, Element.ALIGN_TOP, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f));
		
		cell = new PdfPCell();
		cell.setBackgroundColor(blanco);
		cell.setBorderColor(blanco);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setBorderWidthLeft(0f);
		cell.setBorderWidthBottom(0f);
		cell.setBorderWidthRight(0f);
		cell.setBorderWidthTop(0f);
		cell.setPaddingBottom(0f);
		cell.setPaddingLeft(0f);
		cell.setPaddingRight(0f);
		cell.setPaddingTop(0f);
		cell.addElement(table4);
		
		table2.addCell(cell);
		
		cell = new PdfPCell();
		cell.setBackgroundColor(blanco);
		cell.setBorderColor(blanco);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setBorderWidthLeft(0f);
		cell.setBorderWidthBottom(0f);
		cell.setBorderWidthRight(0f);
		cell.setBorderWidthTop(0f);
		cell.setPaddingBottom(0f);
		cell.setPaddingLeft(0f);
		cell.setPaddingRight(0f);
		cell.setPaddingTop(0f);
		cell.addElement(table2);
		
		table1.addCell(cell);
		BigDecimal big, big2, big3;
		Double iva = 0.00;
		Double total=0.00;
		if(proveedorF.getPais().equals("México")){
			iva = 0.16 * importeTotal;
			 
		}else{
			iva = 0.00;
		}
		total = importeTotal + iva;
		big = new BigDecimal(iva+"");
		big2 = new BigDecimal(total+"");
		big3 = new BigDecimal(importeTotal+"");		
		
		table3.addCell(celdaParrafo(new Paragraph("\n\n\n\n\n\n"), Proquifa, blanco, 0L, Element.ALIGN_TOP, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f));			
		parrafo = new Paragraph("\nSub-Total\n\nI.V.A.\n\nTOTAL" ,fuenteTextoNormalPequeProquifa);
		parrafo.setAlignment(Element.ALIGN_RIGHT);
		table3.addCell(celdaParrafo(parrafo, blanco, blanco, 0L, Element.ALIGN_TOP, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f));
		parrafo = new Paragraph("\n$ " + big3.setScale(2, RoundingMode.HALF_UP)  + "\n\n$ " + big.setScale(2, RoundingMode.HALF_UP) + "\n\n$ " + big2.setScale(2, RoundingMode.HALF_UP) ,fuenteTextoNormal8pBold);
		parrafo.setAlignment(Element.ALIGN_RIGHT);
		table3.addCell(celdaParrafo(parrafo, blanco, blanco, 0L, Element.ALIGN_TOP, 0f, 0f, 0f, 0f, 0f, 10f, 0f, 0f));

		
		cell = new PdfPCell();
		cell.setBackgroundColor(blanco);
		cell.setBorderColor(blanco);
		cell.setBorderColorRight(Proquifa);
		
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setBorderWidth(0);
		cell.setPaddingBottom(0f);
		cell.setPaddingLeft(5f);
		cell.setPaddingRight(0f);
		cell.setPaddingTop(0f);
		table.addCell(cell);
		
		cell = new PdfPCell();
		cell.setBackgroundColor(blanco);
		cell.setBorderColor(blanco);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setBorderWidthLeft(0f);
		cell.setBorderWidthBottom(0f);
		cell.setBorderWidthRight(0f);
		cell.setBorderWidthTop(0f);
		cell.setPaddingBottom(0f);
		cell.setPaddingLeft(0f);
		cell.setPaddingRight(0f);
		cell.setPaddingTop(0f);
		cell.addElement(table3);
		table1.addCell(cell);
		
		return table1;
		} catch (DocumentException e) {
			return null;
		}
		
	}
	private PdfPTable finalRequisicion(Requisicion requi, Double importeTotal){
		try {
			
//			log.info(requi.getNombreCliente());
			
			PdfPTable table1 = new PdfPTable(2);
			PdfPTable table2 = new PdfPTable(1);
			PdfPTable table3 = new PdfPTable(3);
			PdfPTable table4 = new PdfPTable(2);
			PdfPTable table = new PdfPTable(4);
			
			float anchos1[] ={24f, 5f};
			float anchos2[] ={25f};
			float anchos3[] ={.1f, 2.5f, 3.5f};
			float anchos4[] ={.1f, 5f};
			float anchos[] ={7f, 7f, 9f, .5f};
			
			table1.setWidths(anchos1);
			table2.setWidths(anchos2);
			table3.setWidths(anchos3);
			table4.setWidths(anchos4);
			table.setWidths(anchos);
			
			table1.setWidthPercentage(100f);
			table2.setWidthPercentage(100f);
			table3.setWidthPercentage(100f);
			table4.setWidthPercentage(100f);
			table.setWidthPercentage(100f);

	
		cell = new PdfPCell();
		cell.setBackgroundColor(blanco);
		cell.setBorderColor(blanco);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setBorderWidthLeft(0f);
		cell.setBorderWidthBottom(0f);
		cell.setBorderWidthRight(0f);
		cell.setBorderWidthTop(0f);
		cell.setPaddingBottom(0f);
		cell.setPaddingLeft(0f);
		cell.setPaddingRight(0f);
		cell.setPaddingTop(0f);
		cell.addElement(table);		
		table2.addCell(cell);

		
		cell = new PdfPCell();
		cell.setBackgroundColor(blanco);
		cell.setBorderColor(blanco);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setBorderWidthLeft(0f);
		cell.setBorderWidthBottom(0f);
		cell.setBorderWidthRight(0f);
		cell.setBorderWidthTop(0f);
		cell.setPaddingBottom(0f);
		cell.setPaddingLeft(0f);
		cell.setPaddingRight(0f);
		cell.setPaddingTop(0f);
		cell.addElement(table4);
		
		table2.addCell(cell);
		
		cell = new PdfPCell();
		cell.setBackgroundColor(blanco);
		cell.setBorderColor(blanco);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setBorderWidthLeft(0f);
		cell.setBorderWidthBottom(0f);
		cell.setBorderWidthRight(0f);
		cell.setBorderWidthTop(0f);
		cell.setPaddingBottom(0f);
		cell.setPaddingLeft(0f);
		cell.setPaddingRight(0f);
		cell.setPaddingTop(0f);
		cell.addElement(table2);
		
		table1.addCell(cell);


		cell = new PdfPCell();
		cell.setBackgroundColor(blanco);
		cell.setBorderColor(blanco);
		cell.setBorderColorRight(Proquifa);
		
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setBorderWidth(0);
		cell.setPaddingBottom(0f);
		cell.setPaddingLeft(5f);
		cell.setPaddingRight(0f);
		cell.setPaddingTop(0f);
		table.addCell(cell);
		
		cell = new PdfPCell();
		cell.setBackgroundColor(blanco);
		cell.setBorderColor(blanco);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setBorderWidthLeft(0f);
		cell.setBorderWidthBottom(0f);
		cell.setBorderWidthRight(0f);
		cell.setBorderWidthTop(0f);
		cell.setPaddingBottom(0f);
		cell.setPaddingLeft(0f);
		cell.setPaddingRight(0f);
		cell.setPaddingTop(0f);
		cell.addElement(table3);
		table1.addCell(cell);
		
		return table1;
		} catch (DocumentException e) {
			return null;
		}
	}
	
	
	public void confirmacionEntrega (List<ConfirmacionEntrega> con, String folio, int totalPiezas, int fuera, int dentro, int porciento)  {	
		// derecho, izquierdo, superior,inferior
		document.setMargins(29f, 29f, 219f, 120f);
		
		partidas = new ArrayList<TablaPDF>();
		cell = new PdfPCell();
										//ancho de la columnas en la tabla
		widthsRTConfirmacionE[0]=1f;	//numero
		widthsRTConfirmacionE[1]=2f;	//cantidad
		widthsRTConfirmacionE[2]=0.5f;	//espacio_blanco
		widthsRTConfirmacionE[3]=12f;	//descripcion
		widthsRTConfirmacionE[4]=2.5f;	//fee
		widthsRTConfirmacionE[5]=2.5f;	//dias
		widthsRTConfirmacionE[6]=0.5f;	//indicador verde/rojo
		
	
		folioF = folio;
		log.info(folio);
		ce = new ConfirmacionEntrega();
		ce.setCliente(con.get(0).getCliente());
		ce.setContacto(con.get(0).getContacto());
		ce.setPedidoCliente(con.get(0).getPedidoCliente());
		ce.setCpedido(con.get(0).getCpedido());
		ce.setFecha(con.get(0).getFecha());
		ce.setFactura(con.get(0).getFactura());
		ce.setPersonaRecibio(con.get(0).getPersonaRecibio());
		ce.setTotalPiezas(totalPiezas);
 	
		try {
			document.open();

			titulos.add("#");
			titulos.add("Cantidad");
			titulos.add(" ");
			titulos.add("Decripción");
			titulos.add("FEE");
			titulos.add("Días de Atraso");
			titulos.add(" ");
			// no pinta los encabezados de la tabla 'false',  por que se necesita que se pinten en todas las hojas y no solo en la primera

			document.add(dibujarTablaConfimacion(titulos,partidas,widthsRTConfirmacionE, false,con));
		
			partidasUltima = writer.getCurrentPageNumber();
//			log.info( writer.getCurrentPageNumber());
			document.add(new Paragraph(" "));	
			document.add(new Paragraph(" "));	
			
			// Resumen de entrega
			document.add(dibujarTablaConfimacionResumen(dentro, fuera, porciento));

			document.close();

		} catch (DocumentException de) {
            System.err.println(de.getMessage());
        } 
	}
	
	private PdfPTable dibujarTablaConfimacionResumen (int enTiempo, int fueraTiempo, int efectividad) throws DocumentException{
		PdfPTable tableResumen = new PdfPTable(3);
		tableResumen.setWidthPercentage(100F);
		tableResumen.setWidths(new float[]{ 1f,1f,1f});
		String sEnTiempo, sFueraTiempo;
		
		for(int i=0; i< 3; i++){
			cell = new PdfPCell();
			cell.setBackgroundColor(Proquifa);
			cell.setBorderColor(Proquifa);
			if(i == 0){
				parrafo = new Paragraph("En Tiempo",fuenteTituloTablaCE);
			}
			if(i == 1){
				parrafo = new Paragraph("Fuera de Tiempo",fuenteTituloTablaCE);
			}
			if(i == 2){
				parrafo = new Paragraph("Efectividad",fuenteTituloTablaCE);
			}
			parrafo.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(parrafo);
			cell.setVerticalAlignment(Element.ALIGN_CENTER );
			cell.setPaddingBottom(6);
			cell.setPaddingTop(0);
			tableResumen.addCell(cell);
		}
		
		if(enTiempo == 1){
			sEnTiempo = enTiempo + " pieza";
		}else{
			sEnTiempo = enTiempo + " piezas";
		}
		if(fueraTiempo == 1){
			sFueraTiempo = fueraTiempo + " pieza";
		}else{
			sFueraTiempo = fueraTiempo + " piezas";
		}

			//En la funcion 'celdaParrafo', se configura la presentacion de la celda, bordes, color, alineacion, etc
			parrafo = new Paragraph(sEnTiempo,fuenteTiempoCE);
			parrafo.setAlignment(Element.ALIGN_CENTER);
			tableResumen.addCell(celdaParrafo(parrafo, blanco, gris, 0L, Element.ALIGN_TOP, 0f, 1f, 0f, 0f, 10f, 0f, 0f, 2f));
			
			parrafo = new Paragraph(sFueraTiempo,fuenteTiempoCE);
			parrafo.setAlignment(Element.ALIGN_CENTER);
			tableResumen.addCell(celdaParrafo(parrafo, blanco, gris, 0L, Element.ALIGN_TOP, 0f, 1f, 0f, 0f, 10f, 0f, 0f, 2f));
			
			parrafo = new Paragraph(efectividad +"%",fuenteTiempoNegritaCE);
			parrafo.setAlignment(Element.ALIGN_CENTER);
			tableResumen.addCell(celdaParrafo(parrafo, blanco, gris, 0L, Element.ALIGN_TOP, 0f, 1f, 0f, 0f, 10f, 0f, 0f, 2f));
			
	
		
		
		return tableResumen;
	}
	private PdfPTable dibujarTablaConfimacion (ArrayList<String> encabezados, List<TablaPDF> datos, float[] anchos, Boolean conEncabezados, List<ConfirmacionEntrega> confirmacion) throws DocumentException{
		
		PdfPTable table = new PdfPTable(encabezados.size());
		table.setWidths(anchos);
		table.setWidthPercentage(100f); 
		f = new Funcion();
		

			int k = 0;
			int dias = 0;
			for(int i = 0; i < confirmacion.size() ; i++){
				k++;	
				if(confirmacion.get(i).getDiasAtraso() > 0){
					dias = confirmacion.get(i).getDiasAtraso();
				}else{
					dias = 0;
				}
				parrafo = new Paragraph(String.valueOf(k),fuenteProductosCE);
				parrafo.setAlignment(Element.ALIGN_CENTER);
				table.addCell(celdaParrafo(parrafo, blanco, gris, 0L, Element.ALIGN_TOP, 0f, 1f, 0f, 0f, 10f, 0f, 0f, 2f));
				
				parrafo = new Paragraph(confirmacion.get(i).getCantidad()+"",fuenteProductosCE);
				parrafo.setAlignment(Element.ALIGN_CENTER);
				table.addCell(celdaParrafo(parrafo, blanco, gris, 0L, Element.ALIGN_TOP, 0f, 1f, 0f, 0f, 10f, 0f, 0f, 2f));
				
				parrafo = new Paragraph(" ",fuenteProductosCE);
				parrafo.setAlignment(Element.ALIGN_CENTER);
				table.addCell(celdaParrafo(parrafo, blanco, gris, 0L, Element.ALIGN_TOP, 0f, 1f, 0f, 0f, 10f, 0f, 0f, 2f));
				
				parrafo = new Paragraph( confirmacion.get(i).getProducto()+"",fuenteProductosCE);
				parrafo.setAlignment(Element.ALIGN_JUSTIFIED);
				table.addCell(celdaParrafo(parrafo, blanco, gris, 0L, Element.ALIGN_TOP, 0f, 1f, 0f, 0f, 10f, 0f, 10f, 2f));

				parrafo = new Paragraph(f.obtenerFormatoFecha(confirmacion.get(i).getFechaEsperadaEntrega()) + "",fuenteProductosCE);
				parrafo.setAlignment(Element.ALIGN_CENTER);
				table.addCell(celdaParrafo(parrafo, blanco, gris, 0L, Element.ALIGN_TOP, 0f, 1f, 0f, 0f, 10f, 0f, 0f, 2f));
				
				parrafo = new Paragraph(dias + "",fuenteProductosCE);
				parrafo.setAlignment(Element.ALIGN_CENTER);
				table.addCell(celdaParrafo(parrafo, blanco, gris, 0L, Element.ALIGN_TOP, 0f, 1f, 0f, 0f, 10f, 0f, 0f, 2f));

				
				
					try{   
						
						if(confirmacion.get(i).getTiempoEntrega().equals("Dentro")){
							image = Image.getInstance(RUTA_CONFIRMACION + "VERDE.png");
						}else{
							image = Image.getInstance(RUTA_CONFIRMACION + "ROJO.png");
						}
//		    			image.scalePercent(10f);
		    			image.scaleToFit(4f, 4f);
		    			image.setBorderColor(blanco);
		    			
		    			cell = new PdfPCell();
		    			cell.setBackgroundColor(blanco);
		    			cell.setBorderColor(gris);
		    			cell.setFixedHeight(0);
		    			cell.addElement(image);
		    			cell.setVerticalAlignment(Element.ALIGN_CENTER);
		    			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		    			cell.setBorderWidthLeft(0);
		    			cell.setBorderWidthBottom(1);
		    			cell.setBorderWidthRight(0);
		    			cell.setBorderWidthTop(0);
		    			cell.setPaddingBottom(5);
		    			cell.setPaddingLeft(5);
		    			cell.setPaddingRight(5);
		    			cell.setPaddingTop(7);
		    			
		    			table.addCell(cell);
	
		    		}catch (Exception e) {
		    			log.info(e.getLocalizedMessage());
		    			parrafo = new Paragraph("o",fuenteProductosCE);
		    			parrafo.setAlignment(Element.ALIGN_JUSTIFIED);
		    			table.addCell(celdaParrafo(parrafo, blanco, gris, 0L, Element.ALIGN_TOP, 0f, 1f, 0f, 0f, 10f, 0f, 0f, 2f));
		    		}     			

			}
	
		return table;
	}

	

	
}