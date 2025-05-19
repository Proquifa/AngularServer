/**
 * 
 */
package com.proquifa.net.negocio.ventas.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.comun.util.GenerarPDF;
import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.Contacto;
import com.proquifa.net.modelo.comun.DocumentoRecibido;
import com.proquifa.net.modelo.ventas.PartidaCotizacion;
import com.proquifa.net.modelo.ventas.admoncomunicacion.Llamada;
import com.proquifa.net.negocio.ventas.LlamadaService;
import com.proquifa.net.persistencia.comun.ClienteDAO;
import com.proquifa.net.persistencia.comun.ContactoDAO;
import com.proquifa.net.persistencia.comun.DocumentoRecibidoDAO;
import com.proquifa.net.persistencia.ventas.admoncomunicacion.LlamadaDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ernestogonzalezlozada
 *
 */
@Service("llamadaService")
public class LlamadaServiceImpl implements LlamadaService {
	@Autowired
	private LlamadaDAO llamadaDAO;
	@Autowired
	private DocumentoRecibidoDAO documentoRecibidoDAO;
	@Autowired
	private ClienteDAO clienteDAO;
	@Autowired
	private ContactoDAO contactoDAO;
	
	/* (non-Javadoc)
	 * @see mx.com.proquifa.proquifanet.negocio.admoncomunicacion.LlamadaService#registrarLlamada(mx.com.proquifa.proquifanet.modelo.admoncomunicacion.Llamada)
	 */
	public void registrarLlamada(Llamada llamada) {
		this.llamadaDAO.guardarLlamada(llamada);
	}

	public List<Llamada> obtenerLlamadas(Llamada llamada) {
		return this.llamadaDAO.obtenerLlamadas(llamada);
	}


	public String actualizarRegistroLlamada(Llamada llamada,
			List<PartidaCotizacion> pcotizas) {
		String folio = "NA";
		if(pcotizas==null || pcotizas.isEmpty()){
			//Solo se actualiza la llamada.
			this.llamadaDAO.actulizarLLamada(llamada);
		}else{
			//Insertar en DoctosR
			DocumentoRecibido doctoR = new DocumentoRecibido();
			Cliente cliente = new Cliente();
			Contacto contacto = new Contacto();
			doctoR.setPartida(0L);
			doctoR.setTipo("");
			doctoR.setManejo("Entrante");
			doctoR.setOrigen("Cliente");
			cliente = this.clienteDAO.obtenerClientePorNombre(llamada.getEmpresa());
			if(cliente==null){
				doctoR.setEmpresa(null);
			}else{
				doctoR.setEmpresa(cliente.getIdCliente());
			}
			doctoR.setRPor(llamada.getRecibio());
			doctoR.setMedio("Tel");
			doctoR.setDocto("Requisición");
			doctoR.setNumero(null);
			doctoR.setObservaciones("Para cotizar ver documento correspondiente");
			doctoR.setIngreso(llamada.getDestino());
			doctoR.setFOrigen(0L);
			doctoR.setFechaOrigen(new Date());
			contacto = this.contactoDAO.obtenerPorNombre(llamada.getContacto());
			if(contacto!=null){
				doctoR.setIdContacto(contacto.getIdContacto());	
			}else{
				doctoR.setIdContacto(null);	
			}					
			Long folioInsertado = this.documentoRecibidoDAO.insertarDocumentoRecibido(doctoR);
			folio = String.valueOf(folioInsertado);
			//Insertar partidas de la requisicion.
			for(PartidaCotizacion p:pcotizas){
				llamadaDAO.insertarPartidaLlamada(p, folioInsertado);
			}
			//Actualizar llamada.
			this.llamadaDAO.actulizarLLamada(llamada);
			//Generar docuemnto PDF
			GenerarPDF pdf = new GenerarPDF(folio);
			pdf.requisicionTelefonica(pcotizas,llamada.getAtendio());			
		}
		return folio;
	}
	
	
	public Integer generarPendienteClienteDeshabilitado(String DoctoR){
		Date fecha = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm");
		
		Integer pend = this.documentoRecibidoDAO.generarPendienteClienteDeshabilitado(DoctoR, format.format(fecha));
		return pend;
	}
	
}