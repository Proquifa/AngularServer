/**
 * 
 */
package com.proquifa.net.modelo.comun.util.documentoPDF;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;

import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.cobrosypagos.facturista.PartidaFactura;
import com.proquifa.net.modelo.comun.DoctoCotizacion;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author bryan.magana
 *
 */
public class CotizaCampana {

	static final String RUTA_FIRMAS = Funcion.RUTA_FIRMAS;	
	static final String FILE = Funcion.RUTA_DOCUMENTOS + "Cotizaciones/";
	static final String RUTA_IMAGENES = Funcion.RUTA_DOCUMENTOS + "Imagenes/";
	static final String RUTA_FIRMASDIGITALES = Funcion.RUTA_FIRMASDIGITALES;
	
//	static final String FILE = "/Volumes/DATOS/glassfish/domains/domain1/docroot/SAP/Cotizaciones/";
//	static final String RUTA_IMAGENES = "/Volumes/DATOS/glassfish/domains/domain1/docroot/SAP/Imagenes/";
//	static final String RUTA_FIRMASDIGITALES = "/Volumes/DATOS/glassfish/domains/domain1/docroot/SAP/Imagenes/FirmasDigitales/";
//	
/*
	// Variables Estaticas
	static final String FILE = "/Users/bryan.magana/Desktop/glassfish4/glassfish/domains/domain1/docroot/SAP/Cotizaciones/";
	static final String RUTA_IMAGENES = "/Users/bryan.magana/Desktop/glassfish4/glassfish/domains/domain1/docroot/SAP/Imagenes/";
	static final String RUTA_FIRMAS = "/Users/oscar.cardona/Desktop/Pruebas/Imagenes/";
	static final String RUTA_FIRMASDIGITALES = "/Users/bryan.magana/Desktop/glassfish4/glassfish/domains/domain1/docroot/SAP/Imagenes/FirmasDigitales/";
	/*
	/Users/oscar.cardona/glassfish4/glassfish/domains/domain1/docroot/SAP
	
	static final String FILE = "/Users/oscar.cardona/glassfish4/glassfish/domains/domain1/docroot/SAP/Cotizaciones/";
	static final String RUTA_IMAGENES = "/Users/oscar.cardona/glassfish4/glassfish/domains/domain1/docroot/SAP/Imagenes/";
	static final String RUTA_FIRMAS = "/Users/oscar.cardona/glassfish4/glassfish/domains/domain1/docroot/SAP/Firmas_SAC/";
	static final String RUTA_FIRMASDIGITALES = "/Users/oscar.cardona/glassfish4/glassfish/domains/domain1/docroot/SAP/Imagenes/FirmasDigitales/";
	*/
	
	
	// Variables para los Constructores del Documento
	private PdfWriter writer = null;
	private Document documento = null;
	
	// Colores
	private Color negro = new Color(0,0,0);
	private Color colorLetras = new Color(115,115,115); // Gris para Letras
	private Color rojo  = new Color(255,0,0);
	// Objetos
	private DoctoCotizacion cot;
	private ToolsPDF tools = new ToolsPDF();	
	private Funcion funcion = new Funcion();
	
	// Variables Globales
	private String sMoneda;
	private String sSimboloMoneda;	
	
	public void CotizacionCampana (DoctoCotizacion c){
		try {
			cot =  c;			
			String folio = c.getCcotiza();	
			
			if (c.getMoneda().equals("Dolares")){
				sMoneda= "USD";
				sSimboloMoneda = "$";
			} else if (c.getMoneda().equals("Pesos")){
				sMoneda = "Pesos";
				sSimboloMoneda = "$";
			} else if (c.getMoneda().equals("Euros")){
				sMoneda = "EUR";
				sSimboloMoneda = "€";
			}
			
			documento = new Document(PageSize.LETTER.rotate()); // Se Crea el Documento		
			documento.addAuthor("Proquifa");
			documento.addCreator("Proquifa");
			File carpeta = new File(FILE);
			if (!carpeta.exists())
				carpeta.mkdirs();
			
			writer = PdfWriter.getInstance(documento, new FileOutputStream(FILE + folio + ".pdf")); // Se crea la escritura

//			PdfPageEventHelper pfg = new PdfPageEventHelper(); // Instancia a la clase que hereda los eventos de PdfPageEventHelper
//			writer.setPageEvent(pfg);			
			ManejadorEventos manejadorEventos = new ManejadorEventos(); // Instancia a la clase que hereda los eventos de PdfPageEventHelper para hacer la sobrecarga de los metodos que se necesiten
			writer.setPageEvent(manejadorEventos);
			
			documento.open(); // Abrimos una Nueva Pagina
			
			imprimirPartidas();
			
			imprimirTotales();
			
			documento.close();//Cerramos el documento	        
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	private void imprimirPartidas(){
		try{
			PdfContentByte cb = writer.getDirectContent();	
			float fInicY = 0;
			float fInicX = 0;	
			float fAltoRenglon =   0.62f; // Definimos la altura del Renglon para la descirpcion de la Partida
			float fFinYrec =  0.62f;
			float YImp = 0; // Se usa para la posicion en Y de la funcion imprimirTexto
			int iContPartidas = 0;
			Font fuente = new Font(Font.HELVETICA,9,Font.NORMAL,negro);			
			Font fuenteNotada = new Font(Font.HELVETICA,7,Font.NORMAL,negro);			
					
			fInicY = 11f;
			YImp = 11f;
			for (PartidaFactura partida : cot.getPartidas()){
				// --- Descripcion del Producto ---
				String sTexto = partida.getConceptoPartida();
				String sNota = partida.getNota();
				int iCabe = 0;							
				fFinYrec = 0.62f;
				fInicX = 4.8f;
				YImp -= (iContPartidas*2) ;
				fInicY = YImp - 0.575f;
				ColumnText ct = new ColumnText(cb);				
				do{					
					ct.setText(null);
					ct.addText(new Phrase(sTexto  , fuente));	
					if ( sNota != null && !sNota.equals("")){
						ct.addText(new Phrase(sNota, fuenteNotada));
					}
					ct.setSimpleColumn(tools.convertCmsToPoints(fInicX + 11.2f), tools.convertCmsToPoints(fInicY+fFinYrec+0.2f),tools.convertCmsToPoints(fInicX), tools.convertCmsToPoints(fInicY+0.2f), tools.convertCmsToPoints(0.5f), Element.ALIGN_JUSTIFIED);	
					iCabe = ct.go(true); //Simular escritura para verificar que el Texto cabe en el Rectangulo
					
					if (iCabe == ColumnText.NO_MORE_COLUMN){
						fFinYrec += (fAltoRenglon);
					}					
				}while(iCabe == ColumnText.NO_MORE_COLUMN); // Si no cabe el Texto se hace mas alto el Rectangulo
				ct.setText(null);
				ct.addText(new Phrase(sTexto  , fuente));	
				if ( sNota != null && !sNota.equals("")){
					ct.addText(new Phrase(", "+ sNota, fuenteNotada));
				}
				ct.setSimpleColumn(tools.convertCmsToPoints(fInicX + 11.2f), tools.convertCmsToPoints(fInicY+fFinYrec-0.15f),tools.convertCmsToPoints(fInicX), tools.convertCmsToPoints(fInicY-0.15f), tools.convertCmsToPoints(0.5f), Element.ALIGN_JUSTIFIED);				
				iCabe = ct.go();
				// --- FIN Descripcion del Producto ---				
				
				fInicX = 1.4f;						
				// Linea
				tools.imprimirTexto(cb, BaseFont.HELVETICA, 9f, negro, fInicX, YImp, partida.getPartidaFactura().toString(),"",0);
				
				fInicX += 1.65f;
				// Catalogo
				tools.imprimirTexto(cb, BaseFont.HELVETICA, 9f, negro, fInicX, YImp, partida.getCantidad().toString(),"",0);
				
				fInicX += 15.75f;
				// PrecioUnitario
				tools.imprimirTexto(cb, BaseFont.HELVETICA, 9f, negro, fInicX, YImp, sSimboloMoneda + " " + funcion.formatoMoneda(partida.getImporte()),"RIGHT",0);

				fInicX += 3.1f;
				// Subtotal
				tools.imprimirTexto(cb, BaseFont.HELVETICA, 9f, negro, fInicX, YImp, sSimboloMoneda + " " + funcion.formatoMoneda(partida.getCantidad() * partida.getImporte()),"RIGHT",0);
				
				fInicX += 2.5f;
				// iva
				tools.imprimirTexto(cb, BaseFont.HELVETICA, 9f, negro, fInicX, YImp, sSimboloMoneda + " " + funcion.formatoMoneda(0D),"RIGHT",0);
				
				fInicX += 2.5f;
				// Precio Total
				tools.imprimirTexto(cb, BaseFont.HELVETICA, 9f, negro, fInicX, YImp, sSimboloMoneda + " " + funcion.formatoMoneda((partida.getCantidad() * partida.getImporte())),"RIGHT",0);
				
				iContPartidas ++;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void dibujarPlantilla(){
		// Dibuja los Cuadros, lineas y el logo para la Factura de RM Trading. Se dibuja en cada Pagina
		// Posteriormente el Texto Fijo
		try {
			PdfContentByte cb = writer.getDirectContent();			
			
			//Logo de RM Trading			
			tools.dibujarImagen(cb, RUTA_IMAGENES + "Fondo-cot.jpg", 0f, 0.0f, 48f);
			//firma de esac
			tools.dibujarImagen(cb, RUTA_FIRMASDIGITALES + cot.getUsuarioEsac()  + ".jpg", 20.5f, 14.9f, 88f);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void imprimirInformacionCotizacion(DoctoCotizacion c){
		//Dibuja el texto de la Factura
		try{
			PdfContentByte cb = writer.getDirectContent();			
			Rectangle rect = writer.getPageSize();
			float fAnchoPagina = tools.convertPointsToCms(rect.getWidth()); // Convertimos el ancho en CM
			float fAltoPagina = tools.convertPointsToCms(rect.getHeight()); // Convertimos el ancho en CM
			float fInicY = 0;
			float fInicX = 0;
			
//			SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
			String sfecha = "27/02/2015";   //formatoFecha.format(c.getVigencia());
			
			fInicX = (fAnchoPagina/2) - 3.64f; 
			fInicY = fAltoPagina - 2.53f;	
			//fInicX = fAnchoPagina - 4.93f;
			//fInicY =  fAltoPagina - 1.94f;
			Font fuente = new Font(Font.HELVETICA,8,Font.NORMAL, negro);
			//COTIZA
			fInicX = fAnchoPagina - 0.75f;
			fInicY =  fAltoPagina - 1.7f;
			tools.imprimirTexto(cb, BaseFont.HELVETICA_BOLD, 9f, colorLetras, fInicX, fInicY, c.getCcotiza(),"RIGHT",0);
			// FECHA VIGENCIA
			fInicX = fAnchoPagina - 0.75f;
			fInicY =  fAltoPagina - 2.08f;
			tools.imprimirTexto(cb, BaseFont.HELVETICA, 8f, colorLetras, fInicX, fInicY, sfecha,"RIGHT",0);
			//CPAGO
			fInicX = fAnchoPagina - 7.75f;
			fInicY =  fAltoPagina - 3.45f;
			tools.imprimirTexto(cb, BaseFont.HELVETICA_BOLD, 9f, rojo, fInicX, fInicY, c.getCpago(),"",0);
			//NOMBRE CLIENTE
			fInicX = 2.25f;
			fInicY =  fAltoPagina - 3.935f;
			tools.imprimirTexto(cb, BaseFont.HELVETICA, 8f, negro, fInicX, fInicY, c.getNombreCliente(),"",0);
			//DIRECCION 
			fInicY =  fAltoPagina - 4.46f;
			tools.imprimirParrafo(cb ,fInicX, 18f, fInicY, fInicY- 1.25f, 0.4f, Element.ALIGN_JUSTIFIED,c.getDireccionCliente(), fuente);
			//NOMBRE VENDEDOR
			fInicX = fAnchoPagina -8.2f; 
			fInicY =  fAltoPagina - 4.85f;
			tools.imprimirTexto(cb, BaseFont.HELVETICA, 8f, negro, fInicX, fInicY, c.getNombreEsac(),"",0);
			//NOMBRETITULO
			fInicX = 2.25f;
			fInicY =  fAltoPagina - 6.575f;
			//tools.imprimirTexto(cb, BaseFont.HELVETICA, 8f, negro, fInicX, fInicY, c.getNombreContacto(),"",0);
			
			
			//DIRECCION
			ColumnText ct = new ColumnText(cb);		
			int iCabe = 0;
			ct.setText(null);
			ct.addText(new Phrase(c.getNombreContacto() , fuente));	
			ct.setSimpleColumn(tools.convertCmsToPoints(fInicX), tools.convertCmsToPoints(fInicY),tools.convertCmsToPoints(fInicX + 6.2f ), tools.convertCmsToPoints(fInicY - 3f), tools.convertCmsToPoints(0.325f), Element.ALIGN_JUSTIFIED);						
			iCabe = ct.go(true); //Simular escritura para verificar que el Texto cabe en el Rectangulo
					
			if (iCabe == ColumnText.NO_MORE_COLUMN){
				tools.imprimirTexto(cb, BaseFont.HELVETICA, 8f, negro, fInicX, fInicY, c.getNombreContacto() ,"",0);
			}else {
				fInicY = fInicY+ 0.625f;
				ct.setText(null);
				ct.addText(new Phrase(c.getNombreContacto() , fuente));	
				ct.setSimpleColumn(tools.convertCmsToPoints(fInicX), tools.convertCmsToPoints(fInicY),tools.convertCmsToPoints(fInicX + 6.2f ), tools.convertCmsToPoints(fInicY - 3f), tools.convertCmsToPoints(0.325f), Element.ALIGN_JUSTIFIED);		
				iCabe = ct.go();
				fInicY = fInicY- 0.625f;
			}
			//TEL 
			fInicX =(fAnchoPagina/2) - 2.64f;
			fInicY =  fAltoPagina - 6.575f;
			tools.imprimirTexto(cb, BaseFont.HELVETICA, 8f, negro, fInicX, fInicY, c.getTelContacto(),"",0);
			//FAX
			fInicX = 2.25f;
			fInicY =  fAltoPagina - 7.327f;
			tools.imprimirTexto(cb, BaseFont.HELVETICA, 8f, negro, fInicX, fInicY, c.getFaxContacto() ,"",0);
			//EMAIL
			fInicX = (fAnchoPagina/2) - 2.64f;
			tools.imprimirTexto(cb, BaseFont.HELVETICA, 8f, negro, fInicX, fInicY, c.getMailContacto(),"",0);
			//MONEDA
			fInicX = fAnchoPagina - 2f;
			fInicY =  fAltoPagina - 7.275f;
			tools.imprimirTexto(cb, BaseFont.HELVETICA, 8f, colorLetras, fInicX, fInicY, sMoneda,"CENTER",0);

		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	private void imprimirTotales(){
		try{
			PdfContentByte cb = writer.getDirectContent();				 
			Rectangle rect = writer.getPageSize();
			float fAnchoPagina = tools.convertPointsToCms(rect.getWidth()); // Convertimos el ancho en CM
			float fInicY = 0;
			float fInicX = 0;
			// ------------------------------------------- IMPRIME SIMBOLO MONEDA -------------------------------------------			
			fInicX = fAnchoPagina - 2.5f;
			fInicY = 6.6f;
			// Simbolo Moneda para SUB-TOTAL
			tools.imprimirTexto(cb, BaseFont.HELVETICA, 10f, negro, fInicX, fInicY, sSimboloMoneda,"",0);
			
			fInicY -= 1.1f;
			// Simbolo Moneda para FREIGHT / FLETE
			tools.imprimirTexto(cb, BaseFont.HELVETICA, 10f, negro, fInicX, fInicY, sSimboloMoneda,"",0);
			
			fInicY -= 1.2f;
			// Simbolo Moneda para TOTAL
			tools.imprimirTexto(cb, BaseFont.HELVETICA, 10f, negro, fInicX, fInicY, sSimboloMoneda,"",0);
			
			// ------------------------------------------- IMPRIME CANTIDADES -------------------------------------------
			Double dImporte = (double) cot.getSubtotal();
			Double dIVA =  (dImporte * cot.getIvaTotal());
			Double dTotal = dImporte + dIVA;
			
			String sImporte = funcion.formatoMoneda(dImporte);
			String sIVA = funcion.formatoMoneda(dIVA);
			String sTotal = funcion.formatoMoneda(dTotal);
			
			fInicX = fAnchoPagina - 0.74f;
			fInicY = 6.6f;
			// SUB-TOTAL
			tools.imprimirTexto(cb, BaseFont.HELVETICA, 10f, negro, fInicX, fInicY, sImporte,"RIGHT",0);
			
			fInicY -= 1.075f;
			// TAX / IVA
			tools.imprimirTexto(cb, BaseFont.HELVETICA, 10f, negro, fInicX, fInicY, sIVA,"RIGHT",0);
			
			fInicY -= 1.2f;
			// TOTAL
			tools.imprimirTexto(cb, BaseFont.HELVETICA, 10f, negro, fInicX, fInicY, sTotal,"RIGHT",0);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	class ManejadorEventos extends PdfPageEventHelper{
		/** =========== Documentacion =========== 
		  * Clase para hacer Sobrecarga de los Eventos que desencadena al generar un PDF
		  * Al abrir un documento con documento.open(); desencadena el evento  onOpenDocument
		  * Al cerrar el documento con documento.close(); desencadena el evento onCloseDocument
		**/
		
		PdfTemplate totalPaginas;
		
		public void onOpenDocument(PdfWriter writer, Document document) {
			try {
				// 
				totalPaginas = writer.getDirectContent().createTemplate(100,20);				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public void onStartPage(PdfWriter writer, Document document) { // Evento que se desencadena al iniciar una pagina Nueva
			dibujarPlantilla();
			imprimirInformacionCotizacion(cot);
		}
		
		public void onEndPage(PdfWriter writer, Document document) {
			try {
				// Se imprime la Paginacion y se prepara el template para al final imprimir el total de paginas
				PdfContentByte cb = writer.getDirectContent();			
				Rectangle rect = writer.getPageSize();
				float fAnchoPagina = tools.convertPointsToCms(rect.getWidth()); // Convertimos el ancho en CM
				float fAltoPagina = tools.convertPointsToCms(rect.getHeight()); // Convertimos el ancho en CM
				float fInicY = 0;
				float fInicX = 0;
				fInicX = (fAnchoPagina/2) -1.09f;
				fInicY = fAltoPagina - 1.69f;
					
				// Se agrega el Template para posterirmente pinte el Total de Paginas
				cb.addTemplate(totalPaginas, tools.convertCmsToPoints(fInicX + 1.21f), tools.convertCmsToPoints(fInicY - 0.07f));				
	        }
	        catch(Exception e) {
	        	e.printStackTrace();
	        }
	    }
		
		 public void onCloseDocument(PdfWriter writer, Document document) { // Evento que se desencdena al cerrar el documento
			 try{
				 // Pinta el Total de Paginas en cada pagina, esto es a traves del PdfTemplate totalPaginas	
				 //tools.imprimirTexto(totalPaginas, BaseFont.HELVETICA_BOLD, 25f, colorEmpresa, tools.convertPointsToCms(2), tools.convertPointsToCms(2), Integer.toString(writer.getPageNumber()-1),"",0);
			 } catch (Exception e) {
				 e.printStackTrace();
			}
		 }
		
	} // Fin de la Clase ManejadorEventos	
} //Fin de la Clase cotizacampana

