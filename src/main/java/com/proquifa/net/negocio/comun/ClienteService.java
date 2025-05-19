package com.proquifa.net.negocio.comun;

import java.util.List;

import com.proquifa.net.modelo.comun.CarteraCliente;
import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.Contacto;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;

public interface ClienteService {
	
	Cliente obtenerClienteXId(Long idCliente) throws ProquifaNetException;

	/***
	 * Inserta un nuevo cliente por medio de vista requisicion movil
	 * @param Cliente,Direccion,Contacto
	 * @return true | false 
	 * @throws ProquifaNetException
	 */
	Long insertarCliente(Cliente cliente) throws ProquifaNetException;
	/***
	 * Regresa una lista de los contactos por cliente
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Cliente> listarNumeroDeCliente ();

	/**
	 * Metodo que registra un cliente en la base de datos 
	 * @param nombre
	 * @return
	 */
	Long insertarCliente(String nombre, String nombreContacto, String correo, String empresa) throws ProquifaNetException;

	/***
	 * Inserta un nuevo contacto con cliente
	 * @param Contacto contacto
	 * @param Cliente cliente
	 * @return Integer idContacto was true other null
	 * @throws ProquifaNetException
	 */
	Long insertarNuevoClienteContacto(Contacto contacto, Cliente cliente) throws ProquifaNetException; 

	/**
	 * @param habilitado
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Cliente> obtenerClientes(Long habilitado) throws ProquifaNetException;

	/**
	 * Para usarse en responsable de cobros y pagos
	 * @param idUsuarioLogeado
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Cliente> obtenerClientesPorUsuario(Long idUsuarioLogeado) throws ProquifaNetException;
	/***
	 * Cuando se cambia el cliente de deshabilitado a habilitado la fecha de creacion se actualiza con la fecha actual
	 * @param idClienete
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean actualizarFechaCreacionCliente(Long idClienete) throws ProquifaNetException;
}
