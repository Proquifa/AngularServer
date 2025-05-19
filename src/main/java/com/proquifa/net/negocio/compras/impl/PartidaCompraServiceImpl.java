/**
 * 
 */
package com.proquifa.net.negocio.compras.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.proquifa.net.modelo.compras.EstadoPCompra;
import com.proquifa.net.modelo.compras.PartidaCompra;
//import com.proquifa.net.modelo.comun.Complemento;
import com.proquifa.net.modelo.comun.TiempoProceso;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.negocio.compras.PartidaCompraService;
import com.proquifa.net.persistencia.compras.CompraDAO;
//import com.proquifa.net.persistencia.compras.EstadoPCompraDAO;
import com.proquifa.net.persistencia.compras.PartidaCompraDAO;
import com.proquifa.net.persistencia.comun.ClienteDAO;
//import com.proquifa.net.persistencia.comun.ComplementoDAO;
import com.proquifa.net.persistencia.comun.ProductoDAO;
import com.proquifa.net.persistencia.consultas.ConsultaComprasDAO;

/**
 * @author sarivera
 **/
@Service("partidaCompraService")
public class PartidaCompraServiceImpl implements PartidaCompraService {
	private Funcion funcion;
	@Autowired
	PartidaCompraDAO partidaCompraDAO;
	@Autowired
	CompraDAO compraDAO;
	@Autowired
	ClienteDAO clienteDAO;
	@Autowired
	ProductoDAO productoDAO;
	//@Autowired
	//ComplementoDAO complementoDAO;
	//@Autowired
	//EstadoPCompraDAO estadoPCompraDAO;
	@Autowired
	ConsultaComprasDAO consultaComprasDAO;
	

	public List<PartidaCompra> obtenerPartidasCompraRechazadas(
			Long idProveedor, Long anio, String periodo,
			Integer tipoPeriodo, Long mes) throws ProquifaNetException {
		funcion = new Funcion();
		try{
			String fechas = funcion.obtenerFechas(anio, periodo, tipoPeriodo, mes, "fecha");
			List<PartidaCompra> partidas = this.partidaCompraDAO.obtenerPartidasCompraRechazadas(idProveedor, fechas);
			for(PartidaCompra p : partidas){
				p.setDescripcionProducto(productoDAO.obtenerDescripcionProducto(p.getCodigo(), p.getFabrica()));
			}
			return partidas;
		}catch(Exception e){
			funcion.enviarCorreoAvisoExepcion(e, "idProveedor: " + idProveedor, "anio:" + anio, "periodo:" + periodo, "tipoPeriodo:" + tipoPeriodo, "mes" + mes);
			return null;
		}
	}

	public List<PartidaCompra> obtenerPatidasCompraParaCEspecifica(
			String ordenCompra) {
		try{
			List<PartidaCompra> paList = this.consultaComprasDAO.obtenerPatidasCompraParaCEspecifica(ordenCompra);

			for( PartidaCompra p:paList){	
				p.setDescripcionProducto(productoDAO.obtenerDescripcionProducto(p.getCodigo(),p.getFabrica()));
				if(p.getDescripcionProducto() == null){

					p.setDescripcionProducto(productoDAO.obtenerDescripcionProductoComplemento(p.getIdComplemento()));
				}	
				//p.setTiempoProceso(this.obtenerTiempoProcesoPorPartida(p.getIdPartidaCompra(), p.getCompra()));
			}

			return paList;
		}catch(Exception e){
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "ordenCompra: " + ordenCompra);
			return null;
		}
	}

	public List<TiempoProceso> obtenerTiempoProcesoPorPartida(Long idPCompra, String ordenCompra) {
		try{
			return this.consultaComprasDAO.obtenerTiempoProcesoPorPartida(idPCompra);
		}catch(Exception e){
			funcion.enviarCorreoAvisoExepcion(e, "idPCompra: " + idPCompra, "ordenCompra:" + ordenCompra);
			return null;
		}
	}

	@Override
	public PartidaCompra obtenerPartidaCompraXId(Long idPCompra)
			throws ProquifaNetException {
		try{
			return this.partidaCompraDAO.getPCompraXid(idPCompra);
			
		}catch(Exception e){
			return new PartidaCompra();
		}
	}
}