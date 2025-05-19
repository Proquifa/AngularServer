package com.proquifa.net.persistencia.comun.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.comun.Fabricante;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.comun.FabricanteDAO;
import com.proquifa.net.persistencia.comun.impl.mapper.FabricanteDetallesRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.FabricantePorIdRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.FabricanteRowMapper;

@Repository
public class FabricanteDAOImpl extends DataBaseDAO implements FabricanteDAO {
	
	final Logger log = LoggerFactory.getLogger(FabricanteDAOImpl.class);
	
	@SuppressWarnings("unchecked")
	public List<Fabricante> consultarTodos() {
		List<Fabricante> resp = super.jdbcTemplate
		.query(
				"SELECT * FROM fabricantes",
				new FabricanteRowMapper());
		return resp;
	}
	@SuppressWarnings("unchecked")
	public List<Fabricante> consultarHabilitados(Long idProvee) {
		
		StringBuilder sql=new StringBuilder("")
		.append(" SELECT FAB.idFabricante,FAB.Nombre, ")
		.append(" (SELECT COUNT(Tipo) FROM Productos AS P WHERE P.Fabrica=FAB.Nombre AND P.Tipo='Estandares' ) AS productosEstandares, ")
		.append(" (SELECT COUNT(Tipo) FROM Productos AS P WHERE P.Fabrica=FAB.Nombre AND P.Tipo='Reactivos') AS productosReactivos, ")
		.append(" (SELECT COUNT(Tipo) FROM Productos AS P WHERE P.Fabrica=FAB.Nombre AND P.Tipo='Medicamentos') AS productosMedicamentos, ")
		.append(" (SELECT COUNT(Tipo) FROM Productos AS P WHERE P.Fabrica=FAB.Nombre AND P.Tipo='Labware') AS productosLabware, ")
		.append(" (SELECT COUNT(Tipo) FROM Productos AS P WHERE P.Fabrica=FAB.Nombre AND P.Tipo='Publicaciones') AS productosPublicaciones, ")
		.append(" CASE WHEN PF.PK_ProveedorFabricante IS NOT NULL THEN 1 ELSE 0 END Asociado ,CASE WHEN RELAC.idFabricante IS NOT NULL THEN 1 ELSE 0 END Relacionado,")
		.append(" PROV.FUA_Marcas, FAB.Habilitado, Pais_compra, Pais_manufactura, FAB.logo_ext, FAB.RazonSocial, FAB.TaxID, FAB.Direccion ")
		.append(" FROM Fabricantes AS FAB ")
		.append(" LEFT JOIN (SELECT * FROM ProveedorFabricante WHERE FK01_Proveedor = " + idProvee + ") AS PF ON PF.FK02_Fabricante = FAB.idFabricante ") 
		.append(" LEFT JOIN (SELECT * FROM Proveedores) AS PROV ON PROV.Clave = ").append(idProvee) 
		.append(" LEFT JOIN ( SELECT DISTINCT FAB.idFabricante, CONF.FK01_Proveedor ")
		.append(" FROM Configuracion_Precio AS CONF ")
		.append(" LEFT JOIN(SELECT * FROM ConfiguracionPrecio_Producto) AS CONFPROD ON CONF.PK_Configuracion_Precio = CONFPROD.FK02_ConfFamilia ")
		.append(" LEFT JOIN(SELECT * FROM Productos) AS PROD ON PROD.idProducto = CONFPROD.FK01_Producto ")
		.append(" LEFT JOIN(SELECT * FROM Fabricantes) AS FAB ON FAB.idFabricante = PROD.FK02_Fabricante ")
		.append(" WHERE CONF.FK01_Proveedor = "+idProvee+" ) AS RELAC ON RELAC.idFabricante = FAB.idFabricante ")
		.append(" ORDER BY Nombre ");
//		log.info(sql.toString());
		List<Fabricante> resp = super.jdbcTemplate.query(sql.toString(),new FabricanteRowMapper());
		return resp;
	}
	
	public Fabricante obtenerPorId(Long idFabricante) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			return (Fabricante) super.jdbcTemplate.queryForObject("SELECT * FROM Fabricantes WHERE idFabricante = " + idFabricante, map, new FabricantePorIdRowMapper());
		} catch (Exception e) {
			//logger.info("Error: " + e.getMessage());
			return null;
		}
	}
	@Override
	public Boolean updateFabricante(Fabricante f) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nombre", f.getNombre());
			map.put("habilitado", f.getHabilitado());
			map.put("paisManufactura", f.getPaisManufactura());
			map.put("paisCompra", f.getPaisCompra());
			map.put("logoExt", f.getLogoExt());
			map.put("razonSocial", f.getRazonSocial());
			map.put("taxID", f.getTaxID());
			map.put("direccion", f.getDireccion());
			map.put("idFabricante", f.getIdFabricante());
			
			super.jdbcTemplate.update("UPDATE Fabricantes SET Nombre = :nombre, Habilitado = :habilitado, FUActual = GETDATE(), Pais_manufactura = :paisManufactura, Pais_compra = :paisCompra, "
					+ " Logo_Ext = :logoExt, RazonSocial= :razonSocial, TaxID=:taxID, Direccion= :direccion WHERE idFabricante=:idFabricante", map);
			return true;
		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
			return false;
		}
	}
	
	public Fabricante getFabricantePorNombre(String nombre) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			return (Fabricante) super.jdbcTemplate.queryForObject("SELECT * FROM Fabricantes WHERE Nombre = '" + nombre + "'", map, new FabricanteDetallesRowMapper());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}