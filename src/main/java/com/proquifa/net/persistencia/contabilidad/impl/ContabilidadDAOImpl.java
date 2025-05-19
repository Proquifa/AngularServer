package com.proquifa.net.persistencia.contabilidad.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.comun.CatalogoItem;
import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.Proveedor;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.cuentaContable.CentroCosto;
import com.proquifa.net.modelo.cuentaContable.ContableCaracteristica;
import com.proquifa.net.modelo.cuentaContable.CuentaContable;
import com.proquifa.net.modelo.cuentaContable.PPoliza;
import com.proquifa.net.modelo.cuentaContable.Poliza;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.comun.impl.mapper.ClienteRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.ProveedorRowMapper;
import com.proquifa.net.persistencia.contabilidad.ContabilidadDAO;
import com.proquifa.net.persistencia.contabilidad.impl.mapper.CentroCostoRowMapper;
import com.proquifa.net.persistencia.contabilidad.impl.mapper.ClienteCatalogoRowMapper;
import com.proquifa.net.persistencia.contabilidad.impl.mapper.ContableCaracteristicaRowMapper;
import com.proquifa.net.persistencia.contabilidad.impl.mapper.CuentaContableRowMapper;
import com.proquifa.net.persistencia.contabilidad.impl.mapper.PPolizaRowMapper;
import com.proquifa.net.persistencia.contabilidad.impl.mapper.PolizaRowMapper;
import com.proquifa.net.persistencia.contabilidad.impl.mapper.ProveedorCatalogoRowMapper;

@Repository
public class ContabilidadDAOImpl extends DataBaseDAO implements ContabilidadDAO {
	
	final Logger log = LoggerFactory.getLogger(ContabilidadDAOImpl.class);
	
	private Funcion funcion = new Funcion();
	
	/*-----------------------------Cuentas Contables-----------------------------*/

	@Override
	public CuentaContable getCuentaContable(Integer idCuentaContable) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String sql =
					"SELECT cCont.*, " + 
							"(SELECT COUNT(cBanco.PK_CuentaCBanco) FROM CuentaCBanco AS cBanco WHERE cCont.PK_CuentaContable = cBanco.FK01_CuentaContable) AS countB, " + 
							"(SELECT COUNT(cCli.PK_CuentaCCliente) FROM CuentaCCliente AS cCli WHERE cCont.PK_CuentaContable = cCli.FK01_CuentaContable) AS countC, " + 
							"(SELECT COUNT(cProv.PK_CuentaCProveedor) FROM CuentaCProveedor AS cProv WHERE cCont.PK_CuentaContable = cProv.FK01_CuentaContable ) AS countP, " + 
							"(SELECT COUNT(pPol.PK_PPoliza) FROM PPoliza AS pPol WHERE cCont.PK_CuentaContable = pPol.FK02_CuentaContable) AS countPol " + 
							"FROM CuentaContable AS cCont " + 
							"WHERE cCont.Activo = 1 AND cCont.PK_CuentaContable = :idCuentaContable " + 
							"ORDER BY cCont.Nivel_1, cCont.Nivel, cCont.Nivel_2, cCont.Nivel_3";
			map.put("idCuentaContable", idCuentaContable);
			List<CuentaContable> lst = new ArrayList<CuentaContable>();
			lst = super.jdbcTemplate.query(sql, map, new CuentaContableRowMapper());
			if (lst.size() > 0) {
				return lst.get(0);
			} else {
				return new CuentaContable();
			}
		}catch(Exception e) {
			e.printStackTrace();
			return new CuentaContable();
		}
	}

	@Override
	public List<ContableCaracteristica> getContableCaracteristica() throws ProquifaNetException {
		try {
			//String sql = "SELECT * FROM ContableCaracteristica";
			String sql = "SELECT tabla.Descripcion, ROW_NUMBER() OVER(ORDER BY Descripcion ASC) as PK_ContablesC FROM (SELECT Distinct (Origen + ' ' + Naturaleza) as Descripcion "
					+ " FROM CuentaContable WHERE Origen is not null and Naturaleza is not null) as tabla";
			Map<String, Object> map = new HashMap<String, Object>();
			List<ContableCaracteristica> lst = new ArrayList<ContableCaracteristica>();
			lst = super.jdbcTemplate.query(sql, map, new ContableCaracteristicaRowMapper());
			return lst;
		}catch(Exception e) {
			e.printStackTrace();
			return new ArrayList<ContableCaracteristica>();
		}
	}

	@Override
	public List<CatalogoItem> getClientesCuentasContables(String tipoCliente) throws ProquifaNetException {
		try {
			String sql = "SELECT Cli.Clave AS llave, Cli.Nombre AS nombre, Cli.Pais AS cliPais, Cli.Clave_CuentaContable AS claveCC FROM Clientes AS Cli WHERE Cli.Habilitado = 1 ";
			if(tipoCliente.equals("Nacional")) {
				sql += " AND Pais = 'MEXICO' ";
			}else if(tipoCliente.equals("Extranjero")) {
				sql += " AND Pais <> 'MEXICO' ";
			}
			sql += " ORDER BY nombre ";
			log.info(sql);
			Map<String, Object> map = new HashMap<String, Object>();
			List<CatalogoItem> lst = new ArrayList<CatalogoItem>();
			lst = super.jdbcTemplate.query(sql, map, new ClienteCatalogoRowMapper());
			return lst;
		}catch(Exception e) {
			e.printStackTrace();
			return new ArrayList<CatalogoItem>();
		}
	}

	@Override
	public List<CatalogoItem> getProveedoresCuentasContables(String tipoCliente) throws ProquifaNetException {
		try {
			String sql = "SELECT Prov.Clave AS llave, Prov.Nombre AS nombre, Prov.Pais as cliPais, Prov.Clave_CuentaContable AS claveCC FROM Proveedores AS Prov WHERE Prov.Habilitado = 1 ";
			if(tipoCliente.equals("Nacional")) {
				sql += " AND Pais = 'MEXICO' ";
			}else if(tipoCliente.equals("Extranjero")) {
				sql += " AND Pais <> 'MEXICO' ";
			}
			sql += " ORDER BY nombre ";
			log.info(sql);
			Map<String, Object> map = new HashMap<String, Object>();
			List<CatalogoItem> lst = new ArrayList<CatalogoItem>();
			lst = super.jdbcTemplate.query(sql, map, new ProveedorCatalogoRowMapper());
			return lst;
		}catch(Exception e) {
			e.printStackTrace();
			return new ArrayList<CatalogoItem>();
		}
	}

	@Override
	public boolean disableCuentaContable(Integer idCuenta) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String sql = "UPDATE CuentaContable SET Activo = 0 WHERE PK_CuentaContable = :idCuenta";
			map.put("idCuenta", idCuenta);
			return (super.jdbcTemplate.update(sql,map) > 0) ? true : false;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Integer addCuentaContable(CuentaContable cuenta) throws ProquifaNetException {
		try {
			String sql = "";
			String nivel3 = "";
			Map<String, Object> map = new HashMap<String, Object>();
			if(cuenta.getNivel3().equals("")) {
				sql = "SELECT Max(cuentaCont.Nivel_3) FROM dbo.CuentaContable AS cuentaCont WHERE cuentaCont.Nivel_1 = :Nivel_1 AND Nivel_2 = :Nivel_2 AND cuentaCont.Activo = 1";
				map.put("Nivel_1", cuenta.getNivel1());
				map.put("Nivel_2", cuenta.getNivel2());
				nivel3 = ((Integer) super.jdbcTemplate.queryForObject(sql, map, Integer.class) + 1) + "";
				map.clear();
			}else {
				nivel3 = cuenta.getNivel3();
			}
			sql = "INSERT INTO CuentaContable( "
					+ "FK01_Empresa, Nivel, Nivel_1, Nivel_2, Nivel_3, Descripcion, Tipo, Detalle, Activo) VALUES( "
					+ ":Empresa, :Nivel, :Nivel_1, :Nivel_2, :Nivel_3, :Descripcion, :Tipo, :Detalle, 1)";
			map.put("Nivel", 3);
			map.put("Nivel_1", cuenta.getNivel1());
			map.put("Nivel_2", cuenta.getNivel2());
			map.put("Nivel_3", nivel3);
			map.put("Descripcion", cuenta.getDescripcion());
			map.put("Tipo", cuenta.getTipo().getIdContableCaracteristica());
			map.put("Detalle", cuenta.getDetalle().getIdContableCaracteristica());
			map.put("Empresa", cuenta.getEmpresa().getIdEmpresa());
			super.jdbcTemplate.update(sql,map);
			int id = super.queryForInt("SELECT IDENT_CURRENT ('CuentaContable')");
			return id;
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public Integer addCuentaContableBanco(CuentaContable cuenta) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String sql = "INSERT INTO CuentaCBanco( "
					+ "FK01_CuentaContable, FK02_Banco) VALUES( "
					+ ":cuentaContable, :banco)";
			map.put("cuentaContable", cuenta.getIdCuentaContable());
			map.put("banco", cuenta.getIdBanco());
			super.jdbcTemplate.update(sql,map);
			int id = super.queryForInt("SELECT IDENT_CURRENT ('CuentaCBanco')");
			return id;
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public Integer addCuentaContableCliente(CuentaContable cuenta) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String sql = "INSERT INTO CuentaCCliente( "
					+ "FK01_CuentaContable, FK02_Cliente) VALUES( "
					+ ":cuentaContable, :cliente)";
			map.put("cuentaContable", cuenta.getIdCuentaContable());
			map.put("cliente", cuenta.getIdCliente());
			super.jdbcTemplate.update(sql,map);
			int id = super.queryForInt("SELECT IDENT_CURRENT ('CuentaCCliente')");
			return id;
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public Integer addCuentaContableProveedor(CuentaContable cuenta) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String sql = "INSERT INTO CuentaCProveedor( "
					+ "FK01_CuentaContable, FK02_Proveedor) VALUES( "
					+ ":cuentaContable, :proveedor)";
			map.put("cuentaContable", cuenta.getIdCuentaContable());
			map.put("proveedor", cuenta.getIdProveedor());
			super.jdbcTemplate.update(sql,map);
			int id = super.queryForInt("SELECT IDENT_CURRENT ('CuentaCProveedor')");
			return id;
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public boolean updateCuentaContable(CuentaContable cuenta) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String sql = "UPDATE CuentaContable SET "
					+ "Descripcion = :Descripcion "
					+ "WHERE PK_CuentaContable = :idCuenta";
			map.put("Descripcion", cuenta.getDescripcion());
			map.put("idCuenta", cuenta.getIdCuentaContable());
			if (super.jdbcTemplate.update(sql,map) > 0) {
				return true;
			} else {
				return false;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public List<CuentaContable> getCuentasContablesArbol(Integer idEmpresa) throws ProquifaNetException {
		try {
			String sql = "SELECT cCont.* FROM CuentaContable AS cCont WHERE cCont.FK01_Empresa = :idEmpresa AND cCont.Activo = 1 ORDER BY cCont.Nivel_1, cCont.Nivel_2, cCont.Nivel_3";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idEmpresa", idEmpresa);
			List<CuentaContable> lst = new ArrayList<CuentaContable>();
			lst = super.jdbcTemplate.query(sql, map, new CuentaContableRowMapper());
			return lst;
		}catch(Exception e) {
			e.printStackTrace();
			return new ArrayList<CuentaContable>();
		}
	}
	
	@Override
	public double calculateSaldoCuentaContable(Integer idCuentaContable, Date fechaInicial, Date fechaFinal) throws ProquifaNetException {
		try {
			double monto = 0.0;
			return monto;
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public CuentaContable getCuentaContableNivel(Integer idEmpresa, Integer nivel1, Integer nivel2, Integer nivel3) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String sql =
					"SELECT cCont.* FROM CuentaContable AS cCont WHERE " +
							"cCont.Activo = 1 AND cCont.FK01_Empresa = :idEmpresa " +
							"AND cCont.Nivel_1 = :nivel1 AND " +
							"cCont.Nivel_2 = :nivel2 AND " +
							"cCont.Nivel_3 = :nivel3 " +
							"ORDER BY cCont.Nivel_1, cCont.Nivel, cCont.Nivel_2, cCont.Nivel_3";
			map.put("idEmpresa", idEmpresa);
			map.put("nivel1", nivel1);
			map.put("nivel2", nivel2);
			map.put("nivel3", nivel3);
			List<CuentaContable> lst = new ArrayList<CuentaContable>();
			lst = super.jdbcTemplate.query(sql, map, new CuentaContableRowMapper());
			if (lst.size() > 0) {
				return lst.get(0);
			} else {
				return new CuentaContable();
			}
		}catch(Exception e) {
			e.printStackTrace();
			return new CuentaContable();
		}
	}

	@Override
	public CuentaContable getCuentaContableIdObjeto(Integer nivel1, Integer idEmpresa, Long id, String objeto) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String sql = null;
			switch(objeto) {
				case "Cliente":
					sql = "SELECT cCont.* FROM CuentaContable AS cCont "
							+ "WHERE cCont.Activo = 1 AND cCont.FK01_Empresa = :idEmpresa AND cCont.Nivel_1 = :nivel1 AND "
							+ "cCont.PK_CuentaContable IN( "
							+ "SELECT cCCli.FK01_CuentaContable FROM CuentaCCliente AS cCCli WHERE cCCli.FK02_Banco = :id )";
					break;
				case "Banco":
					sql = "SELECT cCont.* FROM CuentaContable AS cCont "
							+ "WHERE cCont.Activo = 1 AND cCont.FK01_Empresa = :idEmpresa AND cCont.Nivel_1 = :nivel1 AND "
							+ "cCont.PK_CuentaContable IN("
							+ "	SELECT cCBan.FK01_CuentaContable FROM CuentaCBanco AS cCBan WHERE cCBan.FK02_Banco = :id )";
					break;
				case "Proveedor":
					sql = "SELECT cCont.* FROM CuentaContable AS cCont "
							+ "WHERE cCont.Activo = 1 AND cCont.FK01_Empresa = :idEmpresa AND cCont.Nivel_1 = :nivel1 AND "
							+ "cCont.PK_CuentaContable IN( "
							+ "SELECT cCProv.FK01_CuentaContable FROM CuentaCProveedor AS cCProv WHERE cCProv.FK02_Proveedor = :id )";
					break;
			}
			if (sql != null) {
				map.put("idEmpresa", idEmpresa);
				map.put("id", id);
				map.put("nivel1", nivel1);
				List<CuentaContable> lst = new ArrayList<CuentaContable>();
				lst = super.jdbcTemplate.query(sql, map, new CuentaContableRowMapper());
				if (lst.size() > 0) {
					return lst.get(0);
				} else {
					return new CuentaContable();
				}
			} else {
				return new CuentaContable();
			}
		}catch(Exception e) {
			e.printStackTrace();
			return new CuentaContable();
		}
	}

	@Override
	public List<CuentaContable> getCuentasContablesSaldoInicial(Integer idEmpresa, Date fechaInicial) throws ProquifaNetException {
		try {
			String sql =
					"SELECT " + 
					"	cCont.*, " + 
					"	(SELECT ISNULL(SUM(pPol.Monto), 0) FROM PPoliza AS pPol WHERE pPol.Tipo = 0 AND pPol.FK01_Poliza IN (SELECT pol.PK_Poliza FROM Poliza AS pol WHERE pol.Fecha < :fechaInicial ) AND pPol.FK02_CuentaContable = cCont.PK_CuentaContable ) AS totalCargo, " + 
					"	(SELECT ISNULL(SUM(pPol.Monto), 0) FROM PPoliza AS pPol WHERE pPol.Tipo = 1 AND pPol.FK01_Poliza IN (SELECT pol.PK_Poliza FROM Poliza AS pol WHERE pol.Fecha < :fechaInicial ) AND pPol.FK02_CuentaContable = cCont.PK_CuentaContable ) AS totalAbono " + 
					"FROM " + 
					"	CuentaContable AS cCont " + 
					"WHERE " + 
					"	(cCont.FK01_Empresa IS NULL OR cCont.FK01_Empresa = :idEmpresa) AND cCont.Activo = 1 " + 
					"ORDER BY " + 
					"	cCont.Nivel_1, cCont.Nivel_2, cCont.Nivel_3;";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idEmpresa", idEmpresa);
			map.put("fechaInicial", fechaInicial);
			List<CuentaContable> lst = new ArrayList<CuentaContable>();
			lst = super.jdbcTemplate.query(sql, map, new CuentaContableRowMapper());
			return lst;
		}catch(Exception e) {
			e.printStackTrace();
			return new ArrayList<CuentaContable>();
		}
	}
	
	@Override
	public List<CuentaContable> getCuentasContablesSaldoAcumulado(Integer idEmpresa, Date fechaInicial) throws ProquifaNetException {
		try {
			String sql =
					"DECLARE @TablaAux TABLE (PK_CuentaContable int, Descripcion varchar(200), Nivel_1 int, Nivel_2 int, Nivel_3 varchar(4), Monto Decimal(18,2))\n" + 
					"INSERT INTO @TablaAux (PK_CuentaContable, Descripcion, Nivel_1, Nivel_2, Nivel_3, Monto) \n" + 
					"SELECT cCont.PK_CuentaContable, cCont.Descripcion, cCont.Nivel_1, cCont.Nivel_2, cCont.Nivel_3,\n" + 
					"SUM(CASE WHEN pPol.Tipo = 0 THEN -1 * ISNULL(pPol.Monto, 0) ELSE ISNULL(pPol.Monto, 0) END) AS Monto\n" + 
					"FROM CuentaContable cCont\n" + 
					"LEFT JOIN PPoliza pPol ON pPol.FK02_CuentaContable = cCont.PK_CuentaContable\n" + 
					"LEFT JOIN Poliza pol ON pPol.FK01_Poliza = pol.PK_Poliza AND pol.FK01_Empresa = :idEmpresa \n" + 
					"WHERE (cCont.FK01_Empresa IS NULL OR cCont.FK01_Empresa = 1) AND cCont.Activo = :idEmpresa AND (pol.Fecha < :fechaInicial OR pol.Fecha IS NULL)\n" + 
					"GROUP BY cCont.PK_CuentaContable, cCont.Descripcion, cCont.Nivel_1, cCont.Nivel_2, cCont.Nivel_3\n" + 
					"ORDER BY cCont.Nivel_1, cCont.Nivel_2, cCont.Nivel_3\n" + 
					"SELECT * FROM (\n" + 
					"SELECT cCont.*, (SELECT Monto FROM (SELECT Nivel_1, SUM(Monto) AS Monto FROM @TablaAux tblA WHERE tblA.Nivel_1 = cCont.Nivel_1 GROUP BY Nivel_1) AS Tabla) AS Monto \n" + 
					"FROM CuentaContable cCont\n" + 
					"WHERE (FK01_Empresa IS NULL OR FK01_Empresa = :idEmpresa) AND Activo = 1 AND Nivel_2 = 0 AND Nivel_3 = '0'\n" + 
					"UNION ALL\n" + 
					"SELECT cCont.*, (SELECT Monto FROM (SELECT Nivel_1, Nivel_2, SUM(Monto) AS Monto FROM @TablaAux tblA WHERE tblA.Nivel_1 = cCont.Nivel_1 AND tblA.Nivel_2 = cCont.Nivel_2 GROUP BY Nivel_1, Nivel_2) AS Tabla) AS Monto \n" + 
					"FROM CuentaContable cCont\n" + 
					"WHERE (FK01_Empresa IS NULL OR FK01_Empresa = :idEmpresa) AND Activo = 1 AND Nivel_2 != 0 AND Nivel_3 = '0' \n" + 
					"UNION ALL\n" + 
					"SELECT cCont.*, (SELECT Monto FROM (SELECT Nivel_1, Nivel_2, Nivel_3, SUM(Monto) AS Monto FROM @TablaAux tblA WHERE tblA.Nivel_1 = cCont.Nivel_1 AND tblA.Nivel_2 = cCont.Nivel_2 AND tblA.Nivel_3 = cCont.Nivel_3 GROUP BY Nivel_1, Nivel_2, Nivel_3) AS Tabla) AS Monto  \n" + 
					"FROM CuentaContable cCont\n" + 
					"WHERE (FK01_Empresa IS NULL OR FK01_Empresa = :idEmpresa) AND Activo = 1 AND Nivel_2 != 0 AND Nivel_3 != '0' \n" + 
					") AS cCont ORDER BY cCont.Nivel_1, cCont.Nivel_2, cCont.Nivel_3";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idEmpresa", idEmpresa);
			map.put("fechaInicial", fechaInicial);
			List<CuentaContable> lst = new ArrayList<CuentaContable>();
			lst = super.jdbcTemplate.query(sql, map, new CuentaContableRowMapper());
			return lst;
		}catch(Exception e) {
			e.printStackTrace();
			return new ArrayList<CuentaContable>();
		}
	}
	
	@Override
	public List<CuentaContable> getCuentasContablesEmpresa(Integer idEmpresa) throws ProquifaNetException {
		try {
			String sql =
					"SELECT cCont.*, " + 
							"(SELECT COUNT(cBanco.PK_CuentaCBanco) FROM CuentaCBanco AS cBanco WHERE cCont.PK_CuentaContable = cBanco.FK01_CuentaContable) AS countB, " + 
							"(SELECT COUNT(cCli.PK_CuentaCCliente) FROM CuentaCCliente AS cCli WHERE cCont.PK_CuentaContable = cCli.FK01_CuentaContable) AS countC, " + 
							"(SELECT COUNT(cProv.PK_CuentaCProveedor) FROM CuentaCProveedor AS cProv WHERE cCont.PK_CuentaContable = cProv.FK01_CuentaContable ) AS countP, " + 
							"(SELECT COUNT(pPol.PK_PPoliza) FROM PPoliza AS pPol WHERE cCont.PK_CuentaContable = pPol.FK02_CuentaContable) AS countPol " + 
							"FROM CuentaContable AS cCont " +
							"WHERE cCont.Activo = 1 AND (cCont.FK01_Empresa = :idEmpresa OR cCont.FK01_Empresa IS NULL) " +
							"ORDER BY cCont.Nivel_1, cCont.Nivel, cCont.Nivel_2, cCont.Nivel_3";
			log.info(sql);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idEmpresa", idEmpresa);
			List<CuentaContable> lst = new ArrayList<CuentaContable>();
			lst = super.jdbcTemplate.query(sql, map, new CuentaContableRowMapper());
			return lst;
		}catch(Exception e) {
			e.printStackTrace();
			return new ArrayList<CuentaContable>();
		}
	}
	
	@Override
	public List<CuentaContable> getCuentasContablesEstadosFinan(Integer idEmpresa, String NEFinanciero, String PEFinanciero, Date fechaInicial, Date fechaFinal) throws ProquifaNetException {
		try {
			String sql =
					"SELECT " + 
					"	cCont.*, " + 
					"	(SELECT ISNULL( SUM(pPol.Monto + pPol.MontoIVA), 0 ) " + 
					"	FROM " + 
					"		PPoliza AS pPol " + 
					"	WHERE " + 
					"		pPol.Tipo = 0 AND " + 
					"		pPol.FK02_CuentaContable = cCont.pK_CuentaContable AND " + 
					"		pPol.FK01_Poliza IN " + 
					"			(SELECT pol.PK_Poliza " + 
					"			FROM " + 
					"				Poliza AS pol " + 
					"			WHERE " + 
					"				pol.Activa = 1 AND " + 
					"				pol.Fecha >= :fechaInicial AND " + 
					"				pol.Fecha <= :fechaFinal " + 
					"			) " + 
					"	) AS totalCargo, " + 
					"	(SELECT ISNULL( SUM(pPol.Monto + pPol.MontoIVA), 0 ) " + 
					"	FROM " + 
					"		PPoliza AS pPol " + 
					"	WHERE " + 
					"		pPol.Tipo = 1 AND " + 
					"		pPol.FK02_CuentaContable = cCont.pK_CuentaContable AND " + 
					"		pPol.FK01_Poliza IN " + 
					"			(SELECT pol.PK_Poliza " + 
					"			FROM " + 
					"				Poliza AS pol " + 
					"			WHERE " + 
					"				pol.Activa = 1 AND " + 
					"				pol.Fecha >= :fechaInicial AND " + 
					"				pol.Fecha <= :fechaFinal " + 
					"			) " + 
					"	) AS totalAbono " + 
					"FROM " + 
					"	CuentaContable AS cCont " + 
					"WHERE " + 
					"	cCont.Activo = 1 AND " + 
					"	cCont.FK01_Empresa = :idEmpresa AND  " + 
					"	cCont.NEstadoFinanciero = :NEFinanciero AND " + 
					"	cCont.PEstadoFinanciero = :PEFinanciero";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idEmpresa", idEmpresa);
			map.put("NEFinanciero", NEFinanciero);
			map.put("PEFinanciero", PEFinanciero);
			map.put("fechaInicial", fechaInicial);
			map.put("fechaFinal", fechaFinal);
			log.info(sql);
			List<CuentaContable> lst = new ArrayList<CuentaContable>();
			lst = super.jdbcTemplate.query(sql, map, new CuentaContableRowMapper());
			return lst;
		}catch(Exception e) {
			e.printStackTrace();
			return new ArrayList<CuentaContable>();
		}
	}
	
	/*-----------------------------Pólizas-----------------------------*/

	@Override
	public Poliza addPoliza(Poliza poliza) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String sql = "INSERT INTO Poliza( "
					+ "FK01_Empresa, FK02_Cliente, FK03_Proveedor, Tipo, Folio, "
					+ "Descripcion, Fecha, Monto, Iva, Total, Aplicada, Activa, Referencia) "
					+ "VALUES("
					+ ":empresa, :cliente, :proveedor, :tipo, :folio, "
					+ ":descripcion, :fecha, :monto, :iva, :total, :aplicada, :activa, :referencia)";
			if (poliza.getEmpresa() != null) {
				map.put("empresa", poliza.getEmpresa().getIdEmpresa());
			} else {
				map.put("empresa", null);
			}
			if (poliza.getCliente() != null) {
				map.put("cliente", poliza.getCliente().getIdCliente());
			} else {
				map.put("cliente", null);
			}
			if (poliza.getProveedor() != null) {
				map.put("proveedor", poliza.getProveedor().getIdProveedor()); 
			} else {
				map.put("proveedor", null);
			}
			map.put("tipo", poliza.getTipo());
			map.put("folio", poliza.getFolio());
			map.put("descripcion", poliza.getDescripcion());
			map.put("fecha", poliza.getFecha());
			map.put("monto", poliza.getMonto());
			map.put("iva", poliza.getIva());
			map.put("total", poliza.getTotal());
			map.put("aplicada", poliza.isAplicada());
			map.put("activa", poliza.isActiva());
			map.put("referencia", poliza.getReferencia());
			super.jdbcTemplate.update(sql, map);
			poliza.setIdPoliza(super.queryForInt("SELECT IDENT_CURRENT ('Poliza')"));
			if (poliza.getIdPoliza() > 0) {
				return poliza;
			} else {
				return new Poliza();
			}
		}catch(Exception e) {
			e.printStackTrace();
			return new Poliza();
		}
	}

	@Override
	public Integer addPPoliza(PPoliza pp) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String sql = "INSERT INTO PPoliza( "
					+ "FK01_Poliza, FK02_CuentaContable, FK03_CentroCosto, "
					+ "Descripcion, Monto, MontoIVA, TipoIVA, Tipo) "
					+ "VALUES( "
					+ ":poliza, :cuentaContable, :centroCosto, "
					+ ":descripcion, :monto, :montoIVA, :tipoIVA, :tipo)";
			if (pp.getPoliza() != null) {
				map.put("poliza", pp.getPoliza().getIdPoliza());
			} else {
				map.put("poliza", null);
			}
			if (pp.getCuentaContable() != null) {
				map.put("cuentaContable", pp.getCuentaContable().getIdCuentaContable());
			} else {
				map.put("cuentaContable", null);
			}
			if (pp.getCentroCosto() != null) {
				map.put("centroCosto", pp.getCentroCosto().getIdCentroCosto());
			} else {
				map.put("centroCosto", null);
			}
			map.put("descripcion", pp.getDescripcion());
			map.put("monto", pp.getMonto());
			map.put("montoIVA", pp.getMontoIVA());
			map.put("tipoIVA", pp.isTipoIVA());
			map.put("tipo", pp.isTipo());
			super.jdbcTemplate.update(sql, map);
			return super.queryForInt("SELECT IDENT_CURRENT ('PPoliza')");
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public boolean deletePPolizas(Integer idPoliza) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String sql = "DELETE FROM PPoliza WHERE FK01_Poliza = :idPoliza";
			map.put("idPoliza", idPoliza);
			if ( super.jdbcTemplate.update(sql, map) > 0 ) {
				return true;
			} else {
				return false;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Poliza updatePoliza(Poliza poliza) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String sql = "UPDATE Poliza SET "
					+ "FK01_Empresa = :empresa, FK02_Cliente = :cliente, FK03_Proveedor = :proveedor, Tipo = :tipo, Folio = :folio, "
					+ "Descripcion = :descripcion, Fecha = :fecha, Monto = :monto, Iva = :iva, Total = :total, Aplicada = :aplicada, "
					+ "Activa = :activa, Referencia = :referencia "
					+ "WHERE PK_Poliza = :idPoliza";
			if (poliza.getEmpresa() != null) {
				map.put("empresa", poliza.getEmpresa().getIdEmpresa());
			} else {
				map.put("empresa", null);
			}
			if (poliza.getCliente() != null) {
				map.put("cliente", poliza.getCliente().getIdCliente());
			} else {
				map.put("cliente", null);
			}
			if (poliza.getProveedor() != null) {
				map.put("proveedor", poliza.getProveedor().getIdProveedor()); 
			} else {
				map.put("proveedor", null);
			}
			map.put("tipo", poliza.getTipo());
			map.put("folio", poliza.getFolio());
			map.put("descripcion", poliza.getDescripcion());
			map.put("fecha", poliza.getFecha());
			map.put("monto", poliza.getMonto());
			map.put("iva", poliza.getIva());
			map.put("total", poliza.getTotal());
			map.put("aplicada", poliza.isAplicada());
			map.put("activa", poliza.isActiva());
			map.put("referencia", poliza.getReferencia());
			map.put("idPoliza", poliza.getIdPoliza());
			super.jdbcTemplate.update(sql, map);
			poliza.setIdPoliza(super.queryForInt("SELECT IDENT_CURRENT ('Poliza')"));
			if (poliza.getIdPoliza() > 0) {
				return poliza;
			} else {
				return new Poliza();
			}
		}catch(Exception e) {
			e.printStackTrace();
			return new Poliza();
		}
	}

	@Override
	public Poliza getPoliza(Integer idPoliza) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String sql = "SELECT * FROM Poliza WHERE PK_Poliza = :idPoliza";
			map.put("idPoliza", idPoliza);
			List<Poliza> lst = super.jdbcTemplate.query(sql, map, new PolizaRowMapper());
			if (lst.size() > 0) {
				return lst.get(0);
			} else {
				return new Poliza();
			}
		}catch(Exception e) {
			e.printStackTrace();
			return new Poliza();
		}
	}

	@Override
	public List<PPoliza> getPPoliza(Integer idPoliza) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String sql = "SELECT * FROM PPoliza WHERE FK01_Poliza = :idPoliza";
			map.put("idPoliza", idPoliza);
			List<PPoliza> lst = new ArrayList<PPoliza>();
			lst = super.jdbcTemplate.query(sql, map, new PPolizaRowMapper());
			return lst;
		}catch(Exception e) {
			e.printStackTrace();
			return new ArrayList<PPoliza>();
		}
	}

	@Override
	public List<CentroCosto> getCentroCostos() throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String sql = "SELECT cCost.* FROM CentroCosto as cCost WHERE cCost.Activo = 1 ORDER BY cCost.Descripcion";
			List<CentroCosto> lst = new ArrayList<CentroCosto>();
			lst = super.jdbcTemplate.query(sql, map, new CentroCostoRowMapper());
			return lst;
		}catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<CentroCosto>();
		}
	}

	@Override
	public CentroCosto getCentroCosto(Integer idCentroCosto) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String sql = "SELECT cCost.* FROM CentroCosto as cCost WHERE cCost.Activo = 1 AND cCost.PK_CentroCosto = :idCentroCosto";
			map.put("idCentroCosto", idCentroCosto);
			List<CentroCosto> lst = new ArrayList<CentroCosto>();
			lst = super.jdbcTemplate.query(sql, map, new CentroCostoRowMapper());
			if (lst.size() > 0) {
				return lst.get(0);
			} else {
				return new CentroCosto();
			}
		}catch (Exception e) {
			e.printStackTrace();
			return new CentroCosto();
		}
	}

	@Override
	public List<PPoliza> getLstPPolizaCuentaContable(Integer idCuentaContable, Integer idEmpresa, Date fechaInicial, Date fechaFinal) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String sql =
					"SELECT pPol.* FROM PPoliza as pPol WHERE pPol.FK01_Poliza IN ( " +
							"SELECT pol.PK_Poliza FROM Poliza AS pol " +
							"WHERE pol.Fecha >= :fechaInicial AND pol.Fecha <= :fechaFinal AND pol.FK01_Empresa = :idEmpresa ) AND " +
							"pPol.FK02_CuentaContable = :idCuentaContable";
			map.put("idCuentaContable", idCuentaContable);
			map.put("idEmpresa", idEmpresa);
			map.put("fechaInicial", fechaInicial);
			map.put("fechaFinal", fechaFinal);
			List<PPoliza> lst = new ArrayList<PPoliza>();
			lst = super.jdbcTemplate.query(sql, map, new PPolizaRowMapper());
			return lst;
		}catch(Exception e) {
			e.printStackTrace();
			return new ArrayList<PPoliza>();
		}
	}

	@Override
	public List<Poliza> getLstPolizasEmpresa(Integer idEmpresa) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String sql = "SELECT * FROM Poliza AS Pol WHERE Pol.Activa = 1 AND Pol.FK01_Empresa = :idEmpresa";
			map.put("idEmpresa", idEmpresa);
			List<Poliza> lst = super.jdbcTemplate.query(sql, map, new PolizaRowMapper());
			return lst;
		}catch(Exception e) {
			e.printStackTrace();
			return new ArrayList<Poliza>();
		}
	}

	@Override
	public int createFolioPolizaEmpresa(Integer tipo, Integer idEmpresa) throws ProquifaNetException {
		try {
			String sql = "SELECT ISNULL( COUNT(*), 0) AS polizasPorTipo FROM Poliza AS pol WHERE pol.Tipo = :tipo AND pol.FK01_Empresa = :idEmpresa";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tipo", tipo);
			map.put("idEmpresa", idEmpresa);
			return super.queryForInt(sql, map);
		}catch(Exception e) {
			return 1;
		}
	}
	
	@Override
	public List<Cliente> obtenerClientes() {
		try {
			String sql = "SELECT * FROM Clientes ORDER BY Clave";
			Map<String, Object> map = new HashMap<String, Object>();
			List<Cliente> lst =  super.jdbcTemplate.query(sql, map, new ClienteRowMapper());
			return lst;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<Proveedor> obtenerProveedores() {
		try {
			String sql = "SELECT * FROM Proveedores ORDER BY Clave";
			Map<String, Object> map = new HashMap<String, Object>();
			List<Proveedor> lst =  super.jdbcTemplate.query(sql, map, new ProveedorRowMapper());
			return lst;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public boolean crearClavesClientes(String claveCuentaContable, String clave) {
		try {
			String sql = "UPDATE clientes SET Clave_CuentaContable = :claveCuentaContable WHERE Clave = :clave";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("claveCuentaContable", claveCuentaContable);
			map.put("clave", clave);
			super.jdbcTemplate.update(sql, map);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public boolean crearClavesProveedores(String claveCuentaContable, String clave) {
		try {
			String sql = "UPDATE proveedores SET Clave_CuentaContable = :claveCuentaContable WHERE Clave = :clave";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("claveCuentaContable", claveCuentaContable);
			map.put("clave", clave);
			super.jdbcTemplate.update(sql, map);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
