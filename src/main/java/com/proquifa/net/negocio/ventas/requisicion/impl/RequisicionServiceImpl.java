/**
 * 
 */
package com.proquifa.net.negocio.ventas.requisicion.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.proquifa.net.modelo.comun.Contacto;
import com.proquifa.net.modelo.comun.Folio;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.ventas.requisicion.PRequisicion;
import com.proquifa.net.modelo.ventas.requisicion.Requisicion;
import com.proquifa.net.negocio.ventas.requisicion.RequisicionService;
import com.proquifa.net.persistencia.comun.FolioDAO;
import com.proquifa.net.persistencia.ventas.requisicion.RequisicionDAO;


/**
 * @author fmartinez
 *
 */
@Service("requisicionService")
public class RequisicionServiceImpl implements RequisicionService {
	
	@Autowired
	RequisicionDAO requisicionDAO;

	
	@Autowired
	FolioDAO folioDAO;
	
	final Logger log = LoggerFactory.getLogger(RequisicionServiceImpl.class);

	public List<Requisicion> filtrarRequisiciones(Long idEmpleado)
			throws ProquifaNetException {
		List<Requisicion> requisiciones = this.requisicionDAO.finRequisiciones(idEmpleado);
		return requisiciones;
	}
	@Transactional
	public String insertarRequisicionMovil(Requisicion requisicion, List<PRequisicion> prequisicion, Contacto contacto)
			throws ProquifaNetException {
		try{		
			Boolean preq=true;
			Integer i=0;
			requisicion.setIdContacto(contacto.getIdContacto());
			if(requisicion.getComentarios()==null){
				requisicion.setComentarios("");
			}
			
			int idDoctoR = 0;
			
			if(requisicion.getIdDoctoR() == 0)
			{
				idDoctoR = this.requisicionDAO.insertaDoctoR(requisicion);
			}
			else if(requisicion.getIdDoctoR() != null && requisicion.getIdDoctoR() > 0){
				
				idDoctoR = requisicion.getIdDoctoR();
			}
			
			Folio folio = this.folioDAO.obtenerFolioPorConcepto("RequisicionMovil",false);
			this.folioDAO.actualizarValorConsecutivo("RequisicionMovil", Long.valueOf(folio.getValor()));
			
			if(idDoctoR > 0){
				requisicion.setIdDoctoR(idDoctoR);
				requisicion.setFolio(folio.getFolioCompleto());
				boolean requi = this.requisicionDAO.insertarRequisicionMovil(requisicion);
				if(requi){
					for (PRequisicion req : prequisicion) {
						if(preq){
							i++;
							req.setIdDoctor(requisicion.getIdDoctoR());
							preq=this.requisicionDAO.insertarPrequisicionMovil(req);
						}else{
							throw new ProquifaNetException("Error al intentar registar la partida "+i+" de la Requisición");
						}
					}
					if(preq){
				//		GenerarPDF gen = new GenerarPDF(requisicion.getIdDoctoR().toString(),"RequisiciónMóvil");
						log.info(requisicion.getFolio());
						log.info("",prequisicion.size());
						log.info("",contacto.getIdContacto());
				//		gen.requicisionMovil(requisicion, prequisicion,contacto);
					}
					return requisicion.getIdDoctoR().toString();
				}else{
					throw new ProquifaNetException("Error al intentar registar la Requisición");
				}
			}else{
				throw new ProquifaNetException("Error al intentar registrar el DoctoR");
			}
		}catch(ProquifaNetException e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.info(e.getMessage());
			return null;
		}
	}

	@Transactional
	public String actualizaRequisicionMovil(Requisicion requisicion, List<PRequisicion> prequisicion, Contacto contacto)
			throws ProquifaNetException {
		try{		
			Boolean preq=true;
//			Integer i=0;
			requisicion.setIdContacto(contacto.getIdContacto());
			if(requisicion.getComentarios()==null){
				requisicion.setComentarios("");
			}
			
			int idDoctoR = 0;
			
			if(requisicion.getIdDoctoR() == 0)
			{
				idDoctoR = this.requisicionDAO.insertaDoctoR(requisicion);
			}
			else if(requisicion.getIdDoctoR() != null && requisicion.getIdDoctoR() > 0){
				
				idDoctoR = requisicion.getIdDoctoR();
			}
			
			Folio folio = this.folioDAO.obtenerFolioPorConcepto("RequisicionMovil",false);
			this.folioDAO.actualizarValorConsecutivo("RequisicionMovil", Long.valueOf(folio.getValor()));
			
			if(idDoctoR > 0){
				requisicion.setIdDoctoR(idDoctoR);
				requisicion.setFolio(folio.getFolioCompleto());
				boolean requi = false;
				if(requisicion.getIdVisita() != null && requisicion.getIdVisita() > 0)
					requi = this.requisicionDAO.actualizaRequisicionMovil(requisicion);
				if(requi){
//					for (PRequisicion req : prequisicion) {
//						if(preq){
//							i++;
//							req.setIdDoctor(requisicion.getIdDoctoR());
//							preq=this.requisicionDAO.insertarPrequisicionMovil(req);
//						}else{
//							throw new ProquifaNetException("Error al intentar registar la partida "+i+" de la Requisición");
//						}
//					}
					if(preq){
						//GenerarPDF gen = new GenerarPDF(requisicion.getIdDoctoR().toString(),"RequisiciónMóvil");
						log.info(requisicion.getFolio());
						log.info("",prequisicion.size());
						log.info("",contacto.getIdContacto());
					//	gen.requicisionMovil(requisicion, prequisicion,contacto);
					}
					return requisicion.getIdDoctoR().toString();
				}else{
					throw new ProquifaNetException("Error al intentar registar la Requisición");
				}
			}else{
				throw new ProquifaNetException("Error al intentar registrar el DoctoR");
			}
		}catch(ProquifaNetException e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.info(e.getMessage());
			return null;
		}
	}
	
}