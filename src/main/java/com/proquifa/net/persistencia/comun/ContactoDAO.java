/**
 * 
 */
package com.proquifa.net.persistencia.comun;

import java.util.List;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.Contacto;
import com.proquifa.net.modelo.comun.NivelIngreso;

/**
 * @author ernestogonzalezlozada
 *
 */
public interface ContactoDAO {
	/**
	 * Metodo que registra un contacto en la base de datos 
	 * @param nuevoContacto
	 * @return
	 */
	Boolean registrar(Contacto nuevoContacto);
	/**
	 * Metodo que actualiza un contacto 
	 * @param nuevoContacto
	 * @return
	 */
	Boolean actualizar(Contacto contacto);
	/**
	 * Metodo que borra un contacto en la base de datos
	 * @param idContacto
	 * @return
	 */
	Boolean borrar(Long idContacto);
	/**
	 * Metodo que obtiene un contacto mediante su Id
	 * @param idContacto
	 * @return
	 */
	Contacto obtenerPorId(Long idContacto);
	/**
	 * Metodo que obtiene solo el contacto por Id del Contacto
	 * @param idContacto
	 * @return
	 */
	String obtenerContactoXId (Long idContacto);
	/**
	 * Metodo que obtiene un contacto mediante los parametros especificados
	 * @param origen
	 * @param empresa
	 * @param nombre
	 * @param eMail
	 * @return
	 */
	List<Contacto> obtener(String origen, String empresa, String nombre, String eMail);
	/**
	 * Metodo que obtiene un contacto mediante su nombre
	 * @param nombre
	 * @return
	 */
	Contacto obtenerPorNombre(String nombre);
	/**
	 * Metodo que obtiene contactos mediante su descripcion 
	 * @param descripcion
	 * @return
	 */
	List<Contacto> obtenerPorDescripcion(String descripcion);
	/***
	 * te devuelde todos los contactos relacionados a un proveedor
	 * @param proveedor
	 * @return List<Contacto>
	 */
	List<Contacto> obtenerContactosProveedorPorId(int proveedor, String tipo, Boolean habilitado);
	
	Contacto obtenerContactoNAFTAporIdProveedor (long idProveedor);
	Contacto obtenerContactoNAFTAporIdEmpresa (long idEmpresa);
	/***
	 * te devuelde todos los contactos relacionados a un Agente Aduanal
	 * @param Long Agente
	 * @return List<Contacto>
	 */
	List<Contacto> obtenerContactosAgentePorId(Long Agente, Boolean habilitado);
	/***
	 * Busqueda de contactos ya sea por nombre de proveedor, nombre del contacto o email
	 * @param condicion
	 * @param tipoProveedor
	 * @return
	 */
	List<Contacto> consultarContactosPorString(String condicion, String tabla, String consulta);
	/***
	 * 
	 * @param idContacto
	 * @return
	 */
	Boolean deshabilitarContacto(Long idContacto);
	/***
	 * Obtener todos los contactos habilitados para los clientes habilitados pertenecientes a cada EV
	 * @param idEmpleado
	 * @return List<Requisicion>
	 * @throws ProquifaNetException
	 */
	List<Contacto> findContactosPorIdEmpleado(Integer idEmpleado, List<NivelIngreso> niveles);
	/**
	 * 
	 * @return
	 */
	public List<Contacto> findContactosHabilitados();
	/**
	 * Metodo que registra un contacto en la base de datos 
	 * @param nombre
	 * @param correo
	 * @param cliente
	 * @param empresa
	 * @return
	 */
	Long registrar(String nombre, String correo, Long cliente, String empresa); 
	
	/***
	 * 
	 * @return
	 */
	List<Contacto> findObtenerContactos(List<NivelIngreso> niveles);
	/**
	 * 
	 * @param niveles
	 * @return
	 */
	List<Contacto> findObtenerContactosExpoMed(List<NivelIngreso> niveles);
	
	/***
	 * Regresa una lista de contactos dado un cliente.
	 * @param idCliente
	 * @return
	 */
	List<Contacto> findContactosXidCliente(Long idCliente, int habilitados);
	/**
	 * 
	 * @param contacto
	 * @return
	 */
	Boolean actualizarExpo(Contacto contacto);
	/**
	 * 
	 * @param tipo
	 * @param habilitados
	 * @return
	 */
	public List<Contacto> obtenerContactosPorTipo(String tipo,boolean habilitados);
}