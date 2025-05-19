/**
 * 
 */
package com.proquifa.net.scheduled;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.LinkContentHandler;
import org.apache.tika.sax.TeeContentHandler;
import org.apache.tika.sax.ToHTMLContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.ContentHandler;

import com.proquifa.net.modelo.comun.Pendiente;
import com.proquifa.net.modelo.comun.Producto;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.persistencia.comun.FuncionDAO;
import com.proquifa.net.persistencia.comun.PendienteDAO;
import com.proquifa.net.persistencia.comun.ProductoDAO;

/**
 * @author ymendez
 *
 */

@Component
public class DisponibilidadProducto {

	@Autowired
	ProductoDAO productoDAO;
	
	@Autowired
	FuncionDAO funcionDAO;
	
	@Autowired
	PendienteDAO pendienteDAO;
	
	final Logger log = LoggerFactory.getLogger(DisponibilidadProducto.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	public void disponibilidadUSP() {
		log.info("Disponibilidad de USP se ejecuto a las;  " + dateFormat.format(new Date()));

		URL url;

		try {
			
			String responsable = funcionDAO.getEmpleadoXIdFuncion(44L);
			
			int section = 10548;
			List<Producto> lstProductosActualizar = new ArrayList<Producto>();
			List<Producto> lstProductosSinActualizar = new ArrayList<Producto>();
			List<Producto> lstPendiente = new ArrayList<Producto>();
			List<Producto> lstQuitarPendiente = new ArrayList<Producto>();
			
			List<Producto> lstProductos = productoDAO.obtenerProductoParaDisponibilidad(44, "");
			String html = "";
			for (int i = 0; i < 26; i++) {
				Character letra = (char) ('A' + i);
				if (!Funcion.PRODUCCION)
					log.info("",letra);


				url = new URL("https://store.usp.org/OA_HTML/ibeCCtpSctDspRte.jsp?section=" + (section + i) + "&beginIndex=0&navTotals=500&navPageSize=500&navBeginIndex=1&navEndIndex=500");
				InputStream input = url.openStream();
				LinkContentHandler linkHandler = new LinkContentHandler();
				ContentHandler textHandler = new BodyContentHandler(-1);
				ToHTMLContentHandler toHTMLHandler = new ToHTMLContentHandler();
				TeeContentHandler teeHandler = new TeeContentHandler(linkHandler, textHandler, toHTMLHandler);

				Metadata metadata = new Metadata();
				ParseContext parseContext = new ParseContext();
				HtmlParser parser = new HtmlParser();
				parser.parse(input, teeHandler, metadata, parseContext);

				html += toHTMLHandler.toString();
				
//				FileWriter fileWriter = new FileWriter("/Users/admin/Documents/" + letra.toString() + ".html");
//			    PrintWriter printWriter = new PrintWriter(fileWriter);
//			    printWriter.print(toHTMLHandler.toString());
//			    printWriter.close();
			    

			}
			
//			FileWriter fileWriter = new FileWriter("/Users/admin/Documents/usp.txt");
//		    PrintWriter printWriter = new PrintWriter(fileWriter);
//		    printWriter.print(html);
//		    printWriter.close();
			
			log.info("Recorrer: " + lstProductos.size());
			for (Producto producto : lstProductos) {
				if (producto.getTipo().equalsIgnoreCase("Estandares")) {
					if (html.toString().contains("<td>\n      " + producto.getCodigo() + "\n      </td>")) {
						String[] array = html.split("<td>\n      "+ producto.getCodigo() + "\n      </td>");
						array = array[1].split("      </td></tr>");
						array = array[0].split("	</td>	<td>");
						String disponible = array[1].trim().toUpperCase().contains("NO") ? "BackOrder"	: "Disponible";
						if (producto.getDisponibilidad().equals("Disponible") && !producto.getDisponibilidad().equals(disponible)) {
							lstPendiente.add(producto);
						} else if(producto.getDisponibilidad().equals("BackOrder") && !producto.getDisponibilidad().equals(disponible) ) {
							lstQuitarPendiente.add(producto);
						}
						producto.setDisponibilidad(disponible);
						lstProductosActualizar.add(producto);
					} else {
						lstProductosSinActualizar.add(producto);
					}
				}
			}
			
			
			for (Producto producto : lstQuitarPendiente) {
				log.info(producto.getCodigo());
				pendienteDAO.cerrarPendiente_angular(producto.getIdProducto().toString(), null, "Gestionar Producto en BO");
				pendienteDAO.actualizarFfinProductosBO(producto.getIdProducto(), true);
			}
			
			for (Producto producto : lstPendiente) {
				Pendiente pendiente = new Pendiente(null, "Gestionar Producto en BO", producto.getIdProducto().toString(), new Date(), responsable, null);
				pendienteDAO.guardarPendiente_angular(pendiente);
				pendienteDAO.actualizarFfinProductosBO(producto.getIdProducto(), false);
			}
			

			productoDAO.updateProductoDisponibilidad(lstProductosActualizar);
//			productoDAO.updateProductoVigencia(lstProductosSinActualizar, 1);
//			productoDAO.updateProductoVigencia(lstProductosSinActualizar, 0);


			log.info("FIN");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void disponibilidadEP() {
		log.info("Disponibilidad de EP se ejecuto a las;  " + dateFormat.format(new Date()));
		try {
			URL url;
			String responsable = funcionDAO.getEmpleadoXIdFuncion(44L);

			List<Producto> lstProductosSinActualizar = new ArrayList<Producto>();
			List<Producto> lstProductosActualizar = new ArrayList<Producto>();
			List<Producto> lstPendiente = new ArrayList<Producto>();
			List<Producto> lstCerrarPendiente = new ArrayList<Producto>();
			List<Producto> lstProductos = productoDAO.obtenerProductoParaDisponibilidad(45, "");
			for (Producto producto : lstProductos) {
				if (producto.getTipo().equalsIgnoreCase("Estandares")) {
					InputStream input;
					try {
						url = new URL("https://crs.edqm.eu/db/4DCGI/View=" + producto.getCodigo());
						input = url.openStream();
					} catch (Exception e) {
						lstProductosSinActualizar.add(producto);
						continue;
					}

					LinkContentHandler linkHandler = new LinkContentHandler();
					ContentHandler textHandler = new BodyContentHandler(-1);
					ToHTMLContentHandler toHTMLHandler = new ToHTMLContentHandler();
					TeeContentHandler teeHandler = new TeeContentHandler(linkHandler, textHandler, toHTMLHandler);
					Metadata metadata = new Metadata();
					ParseContext parseContext = new ParseContext();
					HtmlParser parser = new HtmlParser();
					parser.parse(input, teeHandler, metadata, parseContext);
					String html = toHTMLHandler.toString();
					if (html.toString().contains("<td>Availability</td>")) {
						String[] array = html.split("<td>Availability</td>");
						array = array[1].split("</table>");
						array = array[0].split("</td>");
						String disponible = array[0].split("<td>")[1].trim();

						//					log.info();
						//					System.out.print(producto.getCodigo() + " " + producto.getDisponibilidad() + " ");
						//					System.out.print(disponible);
						if (disponible.contains("No longer")) {
							lstProductosSinActualizar.add(producto);
							continue;
						} else if (disponible.contains("Ask for") || disponible.contains("Expected")) {
							if (producto.getDisponibilidad().equals("Disponible")) {
								lstPendiente.add(producto);
							}
							producto.setDisponibilidad("BackOrder");
						} else if (disponible.contains("Available")) {
							if(producto.getDisponibilidad().equals("BackOrder")) {
								lstCerrarPendiente.add(producto);
							}
							producto.setDisponibilidad("Disponible");
						} else {
							producto.setDisponibilidad("BackOrder");
						}

						lstProductosActualizar.add(producto);
					}
				}
			}

			productoDAO.updateProductoDisponibilidad(lstProductosActualizar);
			
			for (Producto producto : lstCerrarPendiente) {
				log.info(producto.getCodigo());
				pendienteDAO.cerrarPendiente_angular(producto.getIdProducto().toString(), null, "Gestionar Producto en BO");
				pendienteDAO.actualizarFfinProductosBO(producto.getIdProducto(), true);
			}

			for (Producto producto : lstPendiente) {
				log.info("",producto.getIdProducto());
				Pendiente pendiente = new Pendiente(null, "Gestionar Producto en BO", producto.getIdProducto().toString(), new Date(), responsable, null);
				pendienteDAO.guardarPendiente_angular(pendiente);
				pendienteDAO.actualizarFfinProductosBO(producto.getIdProducto(), false);
			}

//			for (Producto producto : lstProductosSinActualizar) {
//				log.info(producto.getCodigo() + " -- " + producto.getDisponibilidad());
//			}

			productoDAO.updateProductoVigencia(lstProductosSinActualizar, 0);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
