/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.proquifa.net.modelo.catalogos.clientes.ParametrosOfertaCliente;
import com.proquifa.net.modelo.catalogos.cobrosypagos.facturista.ReferenciasBancarias;
import com.proquifa.net.modelo.catalogos.proveedores.ConfiguracionPrecioProducto;
import com.proquifa.net.modelo.catalogos.proveedores.TiempoEntrega;
import com.proquifa.net.modelo.comun.Cartera;
import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.ConfiguracionContrato;
import com.proquifa.net.modelo.comun.Contrato;
import com.proquifa.net.modelo.comun.NivelIngreso;
import com.proquifa.net.modelo.comun.Producto;
import com.proquifa.net.modelo.comun.Proveedor;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.tableros.cliente.EntregaCatClientes;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.catalogos.impl.mapper.CarteraXidRowMapper;
import com.proquifa.net.persistencia.catalogos.impl.mapper.ConfiguracionContratoRowMapper;
import com.proquifa.net.persistencia.catalogos.impl.mapper.DatosConfProRowmapper;
import com.proquifa.net.persistencia.catalogos.impl.mapper.DatosEntregaClienteRowMapper;
import com.proquifa.net.persistencia.catalogos.impl.mapper.ProductoContratosRowMapper;
import com.proquifa.net.persistencia.catalogos.impl.mapper.ProveedorContratosRowMapper;
import com.proquifa.net.persistencia.catalogos.impl.mapper.TiempoEntregaRutaProveedorRowMapper;
import com.proquifa.net.persistencia.comun.ClienteDAO;
import com.proquifa.net.persistencia.comun.impl.mapper.ClienteMysuiteRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.ClienteRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.ClientesCotizacionesRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.ClientesRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.ClientesVentasPorMesRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.ConsultaContactoRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.ContratoClienteRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.ReferenciasBancariasClienteRowMapper;

/**
 * @author ernestogonzalezlozada
 *
 */
@Repository
public class ClienteDAOImpl extends DataBaseDAO implements ClienteDAO {
	Funcion funcion;
	
	final Logger log = LoggerFactory.getLogger(ClienteDAOImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.com.proquifa.proquifanet.persistencia.comun.ClientesDAO#
	 * obtenerClientePorNombre(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public Cliente obtenerClientePorNombre(String nombre) {
		String query = "";
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nombre", nombre);
			query = "SELECT * FROM Clientes AS C "
					+ " LEFT JOIN (SELECT Usuario AS NOM_EV, Clave FROM Empleados) AS E ON E.Clave = C.FK01_EV"
					+ " WHERE C.nombre = :nombre";
			return (Cliente) super.jdbcTemplate.queryForObject(query, map ,
					new ClienteRowMapper());
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "query: " + query, "nombre: "
					+ nombre);
			return new Cliente();
		}

	}

	@SuppressWarnings("unchecked")
	public Cliente obtenerClienteXId(Long idcliente) {
		String query = "";
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idcliente", idcliente);
			query = "SELECT *,(CASE WHEN Importancia IS NULL THEN 0 ELSE Importancia END) AS IMPORTANCIA_NUM FROM Clientes AS C "
					+ " LEFT JOIN (SELECT Usuario AS NOM_EV, Clave FROM Empleados) AS E ON E.Clave = C.FK01_EV"
					+ " LEFT JOIN (SELECT ROUND(AVG(CONVERT(FLOAT,(CASE WHEN Dificultad IS NULL THEN 0 ELSE Dificultad END))),0) AS Dificultad, ROUND(AVG(CONVERT(FLOAT,(CASE WHEN Mantenimiento IS NULL THEN 0 ELSE Mantenimiento END))),0) AS Mantenimiento,FK02_Cliente "
					+ " FROM Contactos WHERE FK02_Cliente = :idcliente"
					+ " AND Habilitado = 1 GROUP BY FK02_Cliente ) PROM ON PROM.FK02_Cliente = C.Clave "
					+ " WHERE C.clave = :idcliente";
			return (Cliente) super.jdbcTemplate.queryForObject(query, map,
					new ClienteRowMapper());

		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "query: " + query,
					"idcliente: " + idcliente);
			return new Cliente();
		}
	}

	@SuppressWarnings("unchecked")
	public String obtenerClientePorID(Long idcliente) {
		String query = "";
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idcliente", idcliente);
			query = "SELECT Nombre FROM Clientes AS C WHERE C.clave = :idcliente";
			return (String) super.jdbcTemplate.queryForObject(query, map,
					String.class);
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "query: " + query,
					"idcliente: " + idcliente);
			return "";
		}
	}

	@SuppressWarnings("unchecked")
	public Cliente obtenerClienteXFolioPedido(String cPedido) {
		String query = "";
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cPedido", cPedido);
			query = "SELECT * FROM Clientes AS C "
					+ " LEFT JOIN (SELECT Usuario AS NOM_EV, Clave FROM Empleados) AS E ON E.Clave = C.FK01_EV"
					+ " WHERE C.clave IN (SELECT idCliente FROM pedidos WHERE cpedido = :cPedido)";
			return (Cliente) super.jdbcTemplate.queryForObject(query, map,
					new ClienteRowMapper());
		} catch (RuntimeException rte) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(rte, "query: " + query,
					"cPedido: " + cPedido);
			return new Cliente();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> obtenerClientesPorUsuario(Long idUsuarioLogueado) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idUsuarioLogueado", idUsuarioLogueado);
			return super.jdbcTemplate.query(
					"SELECT *,NOM_EV='', "
							+ " CASE WHEN (C.FK04_UsoCFDI = 190) THEN 'P01 Por definir' "
							+ " WHEN (C.FK04_UsoCFDI = 191) THEN 'G01 Adquisición de mercancias' "
							+ " WHEN (C.FK04_UsoCFDI = 192) THEN 'G02 Devoluciones, descuentos o bonificaciones' "
							+ " WHEN (C.FK04_UsoCFDI = 193) THEN 'G03 Gastos en general' "
							+ " END AS UsoCFDI, "
							+ " CASE WHEN (C.FK05_MetodoPago = 194) THEN 'PUE Pago en una sola exhibición' "
							+ " WHEN (C.FK05_MetodoPago = 195) THEN 'PPD Pago en parcialidades o diferido' "
							+ "END AS MetodoPago "
							+ "  FROM Clientes AS C where Habilitado = '1' AND Cobrador= :idUsuarioLogueado"
							+ " ORDER BY Nombre ASC", map,
							new ClienteRowMapper());
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "idUsuarioLogueado: "
					+ idUsuarioLogueado);
			return new ArrayList<Cliente>();
		}
	}

	public Float obtenerTotalVentasPorPerido(String periodo, Long idCliente) {
		String query = "";
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCliente", idCliente);
			funcion = new Funcion();
			query = "SELECT COALESCE(SUM("
					+ funcion.sqlConversionMonedasADolar("F.Moneda", "1",
							"F.Importe", "M", "F.TCambio", "", false)
					+ " ), 0 )as VentasUSD"
					+ " FROM Facturas as F"
					+ " LEFT JOIN (SELECT * FROM Monedas) AS M ON M.Fecha = F.Fecha"
					+ " WHERE F.Cliente= :idCliente"
					+ " AND F.Estado<>'Cancelada' AND ";

			if (periodo.equals("Anual")) {
				query = query + " YEAR(F.fecha)=DATEPART(year, GETDATE())-1 ";
			} else if (periodo.equals("Acumulado")) {
				query = query + " YEAR(f.fecha)=DATEPART(year, GETDATE())";
			}
			return (Float) super.jdbcTemplate
					.queryForObject(query, map, Float.class);
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "query: " + query, "periodo: "
					+ periodo, "idCliente: " + idCliente);
			return -1F;
		}

	}

	public int obtenerOperacionesRealizadas(Long idCliente) {
		String query = "";
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCliente", idCliente);
			query = "SELECT (COALESCE(CO.COTIZACIONES, 0) + COALESCE(PED.PEDIDOS,0)) As noOperaciones FROM Clientes AS C "
					+ " LEFT JOIN (SELECT COUNT(1) AS COTIZACIONES, Cliente FROM Cotizas WHERE YEAR(Fecha)=(YEAR(GETDATE())-1) GROUP BY Cliente) AS CO ON CO.Cliente = C.Nombre"
					+ " LEFT JOIN (SELECT COUNT(1) AS PEDIDOS, idCliente FROM Pedidos WHERE YEAR(FPedido) = (YEAR(GETDATE())-1) GROUP BY idCliente) AS PED ON PED.idCliente = C.Clave"
					+ " Where C.Clave = :idCliente";
			return super.queryForInt(query, map);
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "query: " + query,
					"idCliente: " + idCliente);
			return -1;
		}
	}

	public String determinarNivelIngresosCliente(Long idCliente,
			List<NivelIngreso> niveles) {
		try {
			String niAnual = "", niCorriente = "", niAcumulado = "";
			Float anual = this.obtenerTotalVentasPorPerido("Anual", idCliente);
			Float acumulado = this.obtenerTotalVentasPorPerido("Acumulado",
					idCliente);

			niAnual = defineNivelIngresos(anual, idCliente, niveles);
			niCorriente = "-NA";
			niAcumulado = "-"
					+ defineNivelIngresos(acumulado, idCliente, niveles);

			return niAnual + niCorriente + niAcumulado;
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "idCliente: " + idCliente);
			return "";
		}
	}

	private String defineNivelIngresos(Float ventas, Long idCliente,
			List<NivelIngreso> niveles) {
		try {
			for (NivelIngreso n : niveles) {
				if ((n.getMin() < ventas) && (n.getMax() > ventas)) {
					return n.getNivel();
				}
			}
			return "Bajo";
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "ventas: " + ventas,
					"idCliente: " + idCliente);
			return "";
		}
	}

	@SuppressWarnings("unchecked")
	public Cliente getdatosParaCFDI(Long idcliente) {
		String query = "";
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idcliente", idcliente);
			query = "SELECT "
					+ "(CASE WHEN C.NumCtaPago IS NOT NULL AND C.MedioPago='Efectivo' THEN '01' "
					+ " WHEN C.NumCtaPago IS NOT NULL AND C.MedioPago='Cheque' THEN '02' "
					+ " WHEN C.NumCtaPago IS NOT NULL AND C.MedioPago='Transferencia' THEN '03' "
					+ " WHEN C.NumCtaPago	 IS NOT NULL AND C.MedioPago='Tarjeta' THEN '04' "
					+ " WHEN C.NumCtaPago IS NOT NULL AND (C.MedioPago='NO IDENTIFICADO' OR C.MedioPago='NO APLICA' OR C.MedioPago='DEPÓSITO BANCARIO' OR C.MedioPago='NA') THEN '05' "
					+ " WHEN C.NumCtaPago IS NOT NULL AND C.MedioPago='OTROS' THEN '99' ELSE '99'END) AS MedioDePago, "
					+ " (CASE WHEN c.NumCtaPago IS NOT NULL AND (c.MedioPago='Cheque' OR c.MedioPago='Transferencia') THEN c.NumCtaPago ELSE 'NO IDENTIFICADO' END) AS NumeroDeCuenta, "
					+ "(CASE WHEN c.Pais<>'MEXICO' THEN 'XEXX010101000' ELSE c.CURP END) AS RFC,"
					+ "(CASE WHEN c.Pais<>'MEXICO' THEN REPLACE(c.CURP,' ','') ELSE '' END) AS TaxID,"
					+ "(CASE WHEN c.FElectronica IS NOT NULL AND c.FElectronica=1 THEN c.MailFElectronica ELSE '' END) AS MailFElectronica ,c.Nombre,c.CP,c.Pais,c.Estado,c.Delegacion,c.calle "
					+ "FROM Clientes AS c " + "WHERE c.Clave= :idcliente";
			return (Cliente) super.jdbcTemplate.queryForObject(query, map, new ClienteMysuiteRowMapper());
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "query: " + query,
					"idcliente: " + idcliente);
			return new Cliente();
		}
	}

	@SuppressWarnings("unchecked")
	public List<ReferenciasBancarias> findReferenciasBancariasCliente(
			String proveedor, Long idCliente) {
		String query = "";
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCliente", idCliente);
			map.put("proveedor", proveedor);
			String valor = (String) super.jdbcTemplate
					.queryForObject(
							"SELECT (CASE WHEN cb.Banco<>'Ixe' AND COALESCE(cc.Registros,'0')=0 THEN 'Sin referencias' ELSE 'Con referencias' END) AS Valor "
									+ "FROM Empresa AS e "
									+ "LEFT JOIN(SELECT DISTINCT(Banco),Beneficiario FROM CuentaBanco) AS cb ON cb.Beneficiario=(e.Prefijo COLLATE Modern_Spanish_CI_AS) "
									+ "LEFT JOIN(SELECT claveCliente,COUNT(*) AS Registros FROM CuentaCliente GROUP BY claveCliente) AS cc ON cc.claveCliente= :idCliente"
									+ " "
									+ "WHERE e.Alias= :proveedor", map, String.class);

			if (valor.equals("Sin referencias")) {
				return null;
			}

			query = "DECLARE @idCliente AS Int= :idCliente"
					+ " "
					+ "DECLARE @quienFactura AS Varchar(30)= :proveedor"
					+ " "
					+ "DECLARE @formato varchar(10) = '0000' "
					+ "SELECT (CASE WHEN cb.Moneda='M.N.' THEN 'MXN' WHEN cb.Moneda='DLS' THEN 'USD' END) AS Moneda,cb.Banco,cb.Sucursal, "
					+ "cb.NoCuenta,cb.Clabe,(CASE WHEN cb.Banco='Ixe' THEN cl.Nombre ELSE ( "
					+ "CASE WHEN codCliente.Cod1=' ' THEN 'X' ELSE codCliente.Cod1 END + "
					+ "CASE WHEN codCliente.Cod2=' ' THEN 'X' ELSE codCliente.Cod2 END + "
					+ "CASE WHEN codCliente.Cod3=' ' THEN 'X' ELSE codCliente.Cod3 END + "
					+ "RIGHT(@formato + CAST(codCliente.Clave AS varchar), 4) + codCliente.Cod + CASE WHEN codCliente.Moneda='M' THEN 'P' ELSE 'D' END + codCliente.CodValidador)END) AS refCliente "
					+ "FROM CuentaBanco AS cb "
					+ "LEFT JOIN(SELECT * FROM Empresa) AS emp ON emp.Alias=@quienFactura "
					+ "LEFT JOIN(SELECT Clave,Nombre, ObjetivoCrecimiento, objetivoCrecimientoFundamental FROM Clientes) AS cl ON cl.Clave=@idCliente "
					+ "LEFT JOIN(SELECT * FROM CuentaCliente) AS cc ON cc.claveCliente=@idCliente AND cc.idCuenta=cb.idCuenta "
					+ "LEFT JOIN(SELECT SUBSTRING(UPPER(SUBSTRING(Clientes.Nombre,1,1)),1,1) as Cod1, SUBSTRING(UPPER(SUBSTRING(Clientes.Nombre,2,2)),1,1) as Cod2, SUBSTRING(UPPER(SUBSTRING "
					+ "(Clientes.Nombre,3,3)),1,1) as Cod3, Clientes.Clave, Bancos.Codigo as Cod, SUBSTRING(CuentaBanco.Moneda,1,1) as Moneda,codValidador,CuentaBanco.idCuenta "
					+ "FROM CuentaCliente JOIN Clientes on Clientes.Clave = claveCliente JOIN CuentaBanco ON CuentaBanco.idCuenta = CuentaCliente.idCuenta JOIN Bancos ON Bancos.Banco = CuentaBanco.Banco) "
					+ "AS codCliente ON codCliente.Clave=@idCliente AND codCliente.idCuenta=CB.idCuenta "
					+ "WHERE cb.activo=1 AND cb.Beneficiario=(emp.Prefijo COLLATE Modern_Spanish_CI_AS)";
			return super.jdbcTemplate.query(query, map,
					new ReferenciasBancariasClienteRowMapper());
		} catch (Exception e) {
			return null;
		}
	}

	public Long insertarCliente(Cliente cli) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nombre", cli.getNombre());
		map.put("vendedor", cli.getVendedor());
		map.put("sector", cli.getSector());
		map.put("industria", cli.getIndustria());
		map.put("rol", cli.getRol());
		map.put("idEjecutivoVenta", cli.getIdEjecutivoVenta());
		map.put("pais", cli.getPais());
		map.put("estado", cli.getEstado());
		map.put("calle", cli.getCalle());
		map.put("delegacion", cli.getDelegacion());
		map.put("codigoPostal", cli.getCodigoPostal());
		map.put("agregadoExpo", cli.isAgregadoExpo());
		map.put("origenRegistro", cli.getOrigenRegistro());
		map.put("razonSocial", cli.getRazonSocial());
		map.put("nivelPrecio", cli.getNivelPrecio());
		map.put("comentarios", cli.getComentarios());
		map.put("objectivoCrecimiento", cli.getObjectivoCrecimiento());
		map.put("importancia", cli.getImportancia());
		map.put("objetivoCrecimientoFundamental", cli.getObjetivoCrecimientoFundamental());
		map.put("idCobrador", cli.getIdCobrador());

		try {
			String sql = "INSERT INTO Clientes(Nombre,Vendedor,Habilitado,FechaRegistro,Sector,Industria,Rol,FK01_EV,Pais,Estado,Calle,Delegacion,CP,AgregadoExpo,OrigenRegistro, RSocial, NivelPrecio, Comenta, ObjetivoCrecimiento,Importancia, ObjetivoCrecimientoFundamental,Cobrador)"
					+ "VALUES (:nombre,:vendedor,'1',GETDATE(),:sector,:industria,:rol,:idEjecutivoVenta,:pais,:estado,:calle,:delegacion,:codigoPostal,:agregadoExpo,:origenRegistro,:razonSocial,:nivelPrecio,:comentarios,:objectivoCrecimiento,:importancia,:objetivoCrecimientoFundamental,:idCobrador)";
			super.jdbcTemplate.update(sql, map);
			return super.queryForLong("SELECT IDENT_CURRENT ('Clientes')");
		} catch (RuntimeException e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, cli);
			return -1L;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Cliente> FindObtenerContactosXCliente() {
		try {
			/*
			 * String query =
			 * "SELECT clientes.*,num.numContactos FROM Clientes AS clientes " +
			 * " RIGHT JOIN(SELECT COUNT(idContacto) AS numContactos,FK02_Cliente FROM Contactos "
			 * +
			 * " WHERE Habilitado=1 GROUP BY FK02_Cliente) AS num ON num.FK02_Cliente=clientes.Clave "
			 * + " WHERE clientes.Habilitado=1 order by Nombre asc ";
			 */
			String query = "SELECT clientes.*,num.numContactos FROM Clientes AS clientes "
					+ " LEFT JOIN(SELECT COUNT(idContacto) AS numContactos,Empresa,FK02_Cliente FROM Contactos  "
					+ " GROUP BY Empresa, FK02_Cliente) AS num ON num.FK02_Cliente=clientes.Clave  "
					+ " ORDER BY Nombre asc ";
			/*
			 * Se hace una sub busqueda del numero de contactos que hay para
			 * cada cliente, y se agrupan afuera.
			 */
			List<Cliente> cliente = super.jdbcTemplate.query(query,
					new ConsultaContactoRowMapper());
			return cliente;
		} catch (RuntimeException e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			return new ArrayList<Cliente>();
		}
	}

	@Override
	public Long insertarCliente(String nombre) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> map2 = new HashMap<String, Object>();
			map.put("nombre", nombre);
			StringBuilder sbQuery = new StringBuilder(
					"INSERT INTO Clientes(nombre,Habilitado) VALUES(:nombre,1)");
			this.jdbcTemplate.update(sbQuery.toString(), map);
			return super.queryForLong("SELECT IDENT_CURRENT ('Clientes')", map2);
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "nombre: " + nombre);
			return -1L;
		}
	}

	/**
	 * Obtien el Cobrador del Cliente, recibe como paramentro el id del Cliente
	 * Si el cliente no tiene asignado un Cobrador se manda Fijo Paola Adriana
	 * M-ndez Estrada --- PMendez
	 * 
	 * @param idcliente
	 * @return
	 */
	public String obtenerCobradorCliente(Long idcliente) {
		String query = "";
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idcliente", idcliente);
			query = "SELECT (CASE WHEN E.NOM_COBRADOR IS NULL THEN 'PMendez' ELSE E.NOM_COBRADOR END) AS NOM_COBRADOR FROM Clientes AS C "
					+ " LEFT JOIN (SELECT Usuario AS NOM_COBRADOR, Clave AS CLAVE_COBRADOR FROM Empleados) AS E ON E.CLAVE_COBRADOR = C.Cobrador "
					+ " WHERE C.clave = :idcliente";
			return (String) super.jdbcTemplate.queryForObject(query, map,
					String.class);
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "query: " + query,
					"idcliente: " + idcliente);
			return "";
		}
	}

	public Double getMontoAnualAnteriorPorCliente(Long cliente)
			throws ProquifaNetException {
		StringBuilder sbHistoVentas = new StringBuilder("");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cliente", cliente);
			sbHistoVentas = new StringBuilder(
					"SELECT montoVenta FROM HistorialVenta WHERE FK01_Cliente = :cliente")
					.append(" AND Anio=YEAR(GETDATE())-1 AND Periodo ='Anual'");
			return (Double) this.jdbcTemplate.queryForObject(
					sbHistoVentas.toString(), map, Double.class);
		} catch (Exception e) {
			// funcion = new Funcion();
			// funcion.enviarCorreoAvisoExepcion(e, "sbHistoVentas: " +
			// sbHistoVentas, "cliente: " + cliente);
			return -1D;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> findObtenerClientes(List<NivelIngreso> niveles,
			String condiciones) throws ProquifaNetException {
		String query = "";
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("condiciones", condiciones);

			funcion = new Funcion();
			query = " SELECT C.*, "
					+ " \n  STUFF(( "
					+ " \n  SELECT ',' + CAST(PP.NombreCartera AS VARCHAR(500)) FROM ("
					+ " \n 	SELECT * FROM Clientes Cli"
					+ " \n  LEFT JOIN Cliente_Cartera CliCart ON CliCart.FK01_Cliente = Cli.Clave"
					+ " \n  LEFT JOIN (select Nombre NombreCartera, PK_Cartera from Cartera) Cart ON Cart.PK_Cartera = CliCart.FK02_Cartera ) PP"
					+ " \n 	WHERE PP.Clave = C.Clave   FOR XML PATH('')"
					+ " \n 	 ), 1, 1, '') NombresCarteras"
					+ " \n  FROM ("
					+ "\nSELECT C.ComentaCredito, C.FElectronica,CON.TotalContactos, C.Clave, COALESCE(C.Nombre, '') Nombre, COALESCE(C.Rol, '') Rol, COALESCE(C.Sector, '') Sector, COALESCE(C.Industria, '') Industria, COALESCE (TIENE_CART.carteras, 0) totalCarteras , "
					+ " \n CASE WHEN Sector = 'PUBLICO' THEN 'Publico' ELSE CASE WHEN Rol = 'DISTRIBUIDOR' THEN 'Distribuidor' ELSE "
					+ funcion.sqlLimitesNivelIngreso(niveles, "NIVEL.VentasUSD")
					+ " END END NIVEL, "
					+ " \n COALESCE(C.Estado, '') Estado, COALESCE(C.Pais, '') Pais, COALESCE(C.RSocial, '') RSocial, COALESCE(C.FUActual, '') FUActual, COALESCE(ESAC.Clave, 0) ClaveESAC, "
					+ " \n COALESCE(C.Vendedor, '') ESAC, COALESCE(EV.Clave,0) ClaveEV, COALESCE(EV.Usuario, '') EV, C.NivelPrecio, COALESCE(C.ObjetivoCrecimiento, '') ObjetivoCrecimiento,"
					+ " \n COALESCE(C.ObjetivoCrecimientoFundamental, '') ObjetivoCrecimientoFundamental,"
					+ " \n COALESCE(C.CPago, '') CPago, CAST(COALESCE(C.Comenta, '') AS VARCHAR(1200)) Comenta, C.MedioPago,  C.NumCtaPago, CASE WHEN C.LimiteCredito = '' THEN '0' ELSE COALESCE(C.LimiteCredito, '0') END LimiteCredito, "
					+ " \n CASE WHEN C.LineaCredito = '' THEN '0' ELSE COALESCE(C.LineaCredito,'0') END LineaCredito, COALESCE(C.CURP, '') RFC, "
					+ " \n COALESCE(C.MonedaFactura, '') MonedaFactura, COALESCE(C.Factura, '') Factura, COALESCE(C.RSPais, '') RSPais, COALESCE(C.RSEstado, '') RSEstado, "
					+ " \n COALESCE(C.RSCalle, '') RSCalle, COALESCE(C.RSDel, '') RSDel, COALESCE(C.RSCP, '') RSCP, COALESCE(C.FacturaPortal,0) FacturaPortal, "
					+ " \n COALESCE(C.MailFElectronica, '') MailFElectronica, COALESCE(C.ComentaFacturacion, '') ComentaFacturacion, C.Habilitado, COALESCE(C.FK01_EV,0) FK01_EV, "
					+ " \n C.imagen, C.cobrador,C.Importancia, COALESCE(COBRADOR.Usuario, '') COBRA, C.Ruta,Corp.NombreCorporativo , corp.idCorporativo , EV.Nombre ev_Nombre, ESAC.Nombre esac_Nombre, COBRADOR.Nombre COBRADOR_Nombre, VI.total_Visitas_ano  "
					+ " \n FROM Clientes C "
					+ " \n LEFT JOIN (SELECT COALESCE(SUM("
					+ funcion.sqlConversionMonedasADolar("F.Moneda", "1",
							"F.Importe", "M", "F.TCambio", "", false)
					+ "), 0) VentasUSD, Cliente FROM Facturas F "
					+ " \n LEFT JOIN Monedas M ON CAST(M.Fecha AS DATE) = CAST(F.Fecha AS DATE) "
					+ " \n WHERE YEAR(F.Fecha) = DATEPART(year,GETDATE()) - 1 AND F.Estado NOT LIKE 'Cancela%' GROUP BY F.Cliente) NIVEL ON NIVEL.Cliente = C.Clave "
					+ " \n LEFT JOIN (SELECT (COALESCE(CO.Cotizaciones,0) + COALESCE(PED.Pedidos,0)) noOperaciones, Clave FROM Clientes C "
					+ " \n LEFT JOIN (SELECT COUNT(1) Cotizaciones, Cliente FROM Cotizas WHERE YEAR(Fecha) = (YEAR(GETDATE()) - 1) GROUP BY Cliente) CO ON CO.Cliente = C.Nombre "
					+ " \n LEFT JOIN (SELECT COUNT(1) Pedidos, idCliente FROM Pedidos WHERE YEAR(FPedido) = (YEAR(GETDATE()) - 1) "
					+ " \n GROUP BY idCliente) PED ON PED.idCliente = C.Clave) OPER ON OPER.Clave = C.Clave "
					+ " \n LEFT JOIN Empleados EV ON EV.Clave = C.FK01_EV "
					+ " \n LEFT JOIN Empleados ESAC ON ESAC.Usuario = C.Vendedor "
					+ " \n LEFT JOIN Empleados COBRADOR ON COBRADOR.Clave = C.cobrador "
					+ " \n LEFT JOIN (select COUNT(1) as carteras, FK01_Cliente from Cliente_Cartera GROUP BY FK01_Cliente) as TIENE_CART ON TIENE_CART.FK01_Cliente = C.Clave "
					+ " \n LEFT JOIN (SELECT Nombre nombreCorporativo, PK_Corporativo as idCorporativo FROM Corporativo )as corp on corp.idCorporativo =  c.fk02_Corporativo "
					+ " \n LEFT JOIN (select COUNT(1) total_Visitas_ano,FK01_Cliente from VisitaCliente where YEAR(Fecha)= YEAR(GETDATE())GROUP BY FK01_Cliente ) as VI on VI.FK01_Cliente = C.Clave "
					+ " \n LEFT JOIN (select distinct FK02_Cartera cartera , Cliente_Cartera.FK01_Cliente from Clientes , cliente_cartera , cartera where Cliente_Cartera.FK01_Cliente =  Clientes.Clave and cartera.PK_Cartera = Cliente_Cartera.FK02_Cartera and Cartera.Publicada = 1) as cli on cli.FK01_Cliente =  c.Clave  "
					+ " \n LEFT JOIN (select COUNT(1) TotalContactos,FK02_Cliente,Habilitado FROM Contactos WHERE Habilitado = 1 GROUP BY FK02_Cliente,Habilitado) CON ON CON.FK02_Cliente = C.Clave "
					//+ " \n WHERE :condiciones"
					+ " \n WHERE " + condiciones
					+ "\n ) C " 
					+ "\n	GROUP BY C.COBRA,C.COBRADOR_Nombre,C.CPago,C.Clave,C.ClaveESAC,C.ClaveEV,C.Cobrador,C.Comenta,C.ComentaCredito,C.ComentaFacturacion,C.ESAC,C.EV,C.Estado,C.FElectronica,C.FK01_EV,C.FUActual,C.Factura,C.FacturaPortal, C.totalCarteras ," 
					+ "\n	C.Habilitado,C.Importancia,C.Industria,C.LimiteCredito,C.LineaCredito,C.MailFElectronica,C.MedioPago,  C.NumCtaPago, C.MonedaFactura,C.NIVEL,C.NivelPrecio,C.Nombre,C.ObjetivoCrecimiento,C.ObjetivoCrecimientoFundamental,C.Pais," 
					+ "\n	C.RFC,C.RSCP,C.RSCalle,C.RSDel,C.RSEstado,C.RSPais,C.RSocial,C.Rol,C.Ruta,C.Sector,C.esac_Nombre,C.ev_Nombre,C.idCorporativo,C.imagen,C.nombreCorporativo, C.total_Visitas_ano,C.TotalContactos" 
					+ "\n	ORDER by C.Clave";
			log.info(query);
			return jdbcTemplate.query(query,new ClientesRowMapper());
		} catch (Exception e) {
			log.info("El error es: \n" + e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "query: " + query,
					"condiciones: " + condiciones);
			return new ArrayList<Cliente>();
		}

	}

	@Override
	public Boolean updateFechaCreacionCliente(Long idClienete)
			throws ProquifaNetException {
		String query = "";
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idClienete", idClienete);
			query = "UPDATE Clientes SET FechaRegistro = GETDATE() WHERE Clave = :idClienete";

			this.jdbcTemplate.update(query, map);

			return true;

		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "query: " + query,
					"idClienete: " + idClienete);
			return false;
		}

	}

	@Override
	public boolean asignarClienteACartera(long idCliente, long idCartera)
			throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCliente", idCliente);
			map.put("idCartera", idCartera);

			this.jdbcTemplate
			.update("INSERT INTO Cliente_Cartera (FK01_Cliente,FK02_Cartera) VALUES(:idCliente, :idCartera)", map);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			throw new ProquifaNetException();
		}
	}


	@Override
	public boolean eliminarClienteDeCartera(Long idCliente, Long idCartera)
			throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCliente", idCliente);
			map.put("idCartera", idCartera);

			String query = " delete from cliente_cartera where fk01_cliente = :idCliente and FK02_Cartera = :idCartera" ;
			super.jdbcTemplate.update(query, map);
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}

	@Override
	public boolean updateResponsablesClienteCartera(long idCliente,
			long idESAC, long idEV, long idCobrador)
					throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCliente", idCliente);
			map.put("idESAC", idESAC);
			map.put("idEV", idEV);
			map.put("idCobrador", idCobrador);

			this.jdbcTemplate
			.update("DECLARE @vendedor AS VARCHAR(45) = (SELECT usuario FROM Empleados WHERE Clave = :idESAC"
					+ ")"
					+ "\n UPDATE clientes SET vendedor = @vendedor, FK01_EV = :idEV"
					+ ", Cobrador = :idCobrador"
					+ "\n WHERE Clave = :idCliente", map);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			throw new ProquifaNetException();
		}
	}

	@Override
	public boolean actualizarPendientesAsignarClienteACartera(
			String pendientes, long idCliente, long idResponsable) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pendientes", pendientes);
			map.put("idCliente", idCliente);
			map.put("idResponsable", idResponsable);

			String query = "DECLARE @idClientes table (Id int) insert into @idClientes VALUES (:idCliente"
					+ ")"
					+ "\nDECLARE @vendedor AS VARCHAR(45) = (SELECT usuario FROM Empleados WHERE Clave = :idResponsable"
					+ ")"
					+ "\nUPDATE Pendiente SET Responsable = @vendedor WHERE Folio IN ( :pendientes)";

			this.jdbcTemplate.update(query, map);
			return true;
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			TransactionAspectSupport.currentTransactionStatus()
			.setRollbackOnly();
			return false;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Cliente> getCotizacionClientes() throws ProquifaNetException {
		String query = "";
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			funcion = new Funcion();
			query = "SELECT CLI.Clave, CLI.Nombre, SUM(PC.Cant *(CASE WHEN C.IMoneda = 'Pesos' OR C.IMoneda = 'M.N.' THEN PC.Precio / M.PDolar  "
					+ "\nWHEN C.IMoneda = 'Euros' THEN PC.Precio * M.EDolar ELSE PC.Precio END)) AS Monto "
					+ "\nFROM (SELECT Clave,Nombre FROM Clientes WHERE Habilitado = 1) AS CLI"
					+ "\nLEFT JOIN (SELECT Clave,IMoneda,Fecha,FK01_idCliente FROM Cotizas "
					+ "\nWHERE  YEAR(Fecha) = YEAR(GETDATE()) -1 AND Estado <> 'Cancelable' AND Estado <> 'Cancelada' AND Estado <> 'Recotizada') AS C ON CLI.Clave = C.FK01_idCliente"
					+ "\nLEFT JOIN (SELECT * FROM PCotizas) AS PC ON C.Clave = PC.Clave"
					+ "\nLEFT JOIN (SELECT * FROM Monedas) AS M ON M.Fecha =C.Fecha "
					+ "\nWHERE CLI.Clave NOT IN ("
					+ "\n		SELECT DISTINCT C.Clave FROM Clientes AS C"
					+ "\n		INNER JOIN (SELECT Cliente FROM Facturas WHERE YEAR(Fecha) = YEAR(GETDATE())-1 AND FPor = 'Ryndem' OR FPor = 'Mungen') AS F ON C.Clave = F.Cliente"
					+ "\n)" + "\nGROUP BY CLI.Clave ,CLI.Nombre";

			return jdbcTemplate.query(query, map,
					new ClientesCotizacionesRowMapper());
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			return new ArrayList<Cliente>();
		}
	}

	@Override
	public boolean deshabilitarClientes(List<Cliente> clientes)
			throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			if (clientes != null && !clientes.isEmpty()) {
				String query = "UPDATE Clientes SET Habilitado = 0 WHERE Clave IN (";
				query += clientes.get(0).getIdCliente();
				for (int i = 1; i < clientes.size(); i++) {
					query += "," + clientes.get(i).getIdCliente();
				}
				query += ")";
				this.jdbcTemplate.update(query, map);
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			TransactionAspectSupport.currentTransactionStatus()
			.setRollbackOnly();
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Cliente> getVentasPorMesesCliente() throws ProquifaNetException {
		//Este metodo solo sera usado para el metodo 'reclasificacionDeClientes'. No usar
		String query = "";
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			funcion = new Funcion();
			query = "SELECT CLI.Clave,CLI.Nombre, SUM(PF.Cant * ( CASE WHEN F.Moneda = 'Pesos' OR F.Moneda = 'MXN' OR F.Moneda = 'M.N.' OR F.Moneda = 'M.N'THEN ((PF.Importe)" +
					"\n/COALESCE(CASE WHEN F.TCambio = 0 THEN MON.PDolar ELSE F.TCambio END , MON.PDolar)) " +
					"\nWHEN F.Moneda = 'Euros' OR F.Moneda = 'EUR' THEN PF.Importe * (MON.EDolar) " +
					"\nWHEN F.Moneda = 'Libras' THEN PF.Importe * (MON.LDolar) " +
					"\nWHEN F.Moneda = 'DlCan'THEN PF.Importe * (MON.DDolar)" +
					"\nWHEN F.Moneda = 'Yenes'THEN PF.Importe * (MON.YDolar)" +
					"\nELSE PF.Importe END )) AS Monto,MONTH(F.Fecha) AS Mes,YEAR(GETDATE())-1 AS Anio,'Facturas' AS ETIQUETA," +
					"\nCOUNT(PF.Cant) AS Partidas, SUM(PF.Cant) AS Piezas"+
					"\nFROM Clientes AS CLI" +
					"\nLEFT JOIN (SELECT * FROM Facturas) AS F ON F.Cliente = CLI.Clave " +
					"\nLEFT JOIN (SELECT * FROM Monedas) AS MON ON cast(MON.Fecha as date) = cast(F.Fecha as date)" +
					"\nLEFT JOIN (SELECT * FROM Remisiones) RE ON CAST(RE.Nota AS VARCHAR(100)) = F.Factura AND F.DeRemision = 1" +
					"\nLEFT JOIN (SELECT * FROM PRemisiones) PRE ON PRE.Factura = RE.Factura AND PRE.FPor = RE.FPor AND PRE.CPedido = RE.CPedido " +
					"\nLEFT JOIN (SELECT Importe, Cant, PPedido, Factura, FPor, CPedido FROM PFacturas) AS PF ON PF.Factura = F.Factura AND PF.FPor = F.FPor" +
					"\nAND CASE WHEN F.DeRemision = 1 THEN PRE.CPedido ELSE F.CPedido END = PF.CPedido" +
					"\nAND CASE WHEN F.DeRemision = 1 THEN PRE.PPedido ELSE PF.PPedido END = PF.PPedido " +
					"\nWHERE F.FPor<>'ProquifaServicios' AND F.Estado<>'Cancelada' AND F.Estado<>'Por Cancelar' AND YEAR(F.Fecha) = YEAR(GETDATE())-1 AND F.Cliente IS NOT NULL" +
					"\nAND CLI.Clave NOT IN (201,347,348,987,1595,2281,2322,2321,2523,2675)" +
					"\nGROUP BY CLI.Clave,CLI.Nombre,MONTH(F.Fecha)" +
					"\nUNION " +
					"\nSELECT CLI.Clave,CLI.Nombre,COALESCE(SUM(CASE WHEN NC.Moneda= 'Dolares' OR NC.Moneda = 'USD'" +
					"\nTHEN NC.Importe WHEN NC.Moneda = 'EUR' OR NC.Moneda = 'Euros' THEN NC.Importe * MON.EDolar ELSE NC.Importe / NC.TCambio END),0) " +
					"\nAS Monto,MONTH(F.Fecha) AS Mes,YEAR(GETDATE())-1 AS Anio,'Nota' AS ETIQUETA,0 AS Partidas, 0 AS Piezas" +
					"\nFROM NotaCredito Nc" +
					"\nLEFT JOIN (SELECT * FROM Monedas) AS MON ON MON.Fecha = NC.Fecha" +
					"\nLEFT JOIN (SELECT * FROM Facturas) F ON F.idFactura = NC.FK02_Factura" +
					"\nLEFT JOIN (SELECT * FROM Clientes) AS CLI ON CLI.Clave = F.Cliente" +
					"\nLEFT JOIN (SELECT * FROM Empleados) AS E ON E.Usuario = CLI.Vendedor " +
					"\nWHERE F.DeSistema=1 AND F.Importe > 0 AND YEAR(F.Fecha) = YEAR(GETDATE())-1 AND F.Estado<>'Cancelada' AND F.Estado<>'Por Cancelar' " +
					"\nAND CLI.Clave NOT IN (201,347,348,987,1595,2281,2322,2321,2523,2675) AND NC.Estado IS NULL" +
					"\nGROUP BY CLI.Clave,CLI.Nombre,MONTH(F.Fecha)" +
					"\nORDER BY ETIQUETA DESC";

			return jdbcTemplate.query(query, map, new ClientesVentasPorMesRowMapper());
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			return new ArrayList<Cliente>();
		}
	}

	@Override
	public boolean insetarHistorialDeVentaClientes(List<Cliente> clientesVentas)
			throws ProquifaNetException {
		//Este metodo solo sera usado para el metodo 'reclasificacionDeClientes'. No usar
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String[] meses = new String[]{"","Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre"
					,"Octubre","Noviembre","Diciembre"};
			if (clientesVentas != null && !clientesVentas.isEmpty()) {

				for (int i = 1; i < clientesVentas.size(); i++) {
					String query = "INSERT INTO HistorialVenta (FK01_Cliente,Fecha,MontoVenta,Periodo,Mes,Anio,Partidas,Piezas) VALUES ";
					query += "(" + clientesVentas.get(i).getIdCliente() ;
					query += ",GETDATE()";
					query += "," + clientesVentas.get(i).getMonto();
					query += ",'Mensual'";
					query += ",'"+ meses[clientesVentas.get(i).getCantidad()] +"'";
					query += ",YEAR(GETDATE())-1";
					query += "," + clientesVentas.get(i).getCorporativo();
					query += "," + clientesVentas.get(i).getDificultad() + ")";
					this.jdbcTemplate.update(query, map);
				}

				String query = "INSERT INTO HistorialVenta (FK01_Cliente,Fecha,MontoVenta,Periodo,Anio,Partidas,Piezas)" +
						"\nSELECT FK01_Cliente,GETDATE() AS Fecha,SUM(MontoVenta),'Anual',YEAR(GETDATE()) -1,SUM(Partidas),SUM(Piezas)" +
						"\nFROM HistorialVenta WHERE Anio = YEAR(GETDATE()) -1 AND Periodo = 'Mensual' GROUP BY FK01_Cliente";

				this.jdbcTemplate.update(query, map);
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Cartera obtenerInformacionDeCarteraPorId(Long idCartera)
			throws ProquifaNetException {	
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCartera", idCartera);
			Cartera cartera = new Cartera();

			String query = "select *,EMP.Usuario from Cartera Cart "
					+ "\nLEFT JOIN Empleados as EMP on EMP.Clave = Cart.FK02_Esac "
					+ "\nwhere PK_Cartera = :idCartera" ;

			cartera = (Cartera) super.jdbcTemplate.queryForObject(query, map, new CarteraXidRowMapper());

			return cartera;

		} catch (Exception e) {
			e.printStackTrace();
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "idCartera: " + idCartera);
			throw new ProquifaNetException();
		}
	}


	@Override
	public boolean actualizarResponsableCartera(String condicion)
			throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("condicion", condicion);

			log.info("UPDATE Clientes set" + condicion);
			super.update("UPDATE Clientes set " + condicion);


			return true;
		} catch (Exception e) {
			e.printStackTrace();
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			throw new ProquifaNetException();
		}
	}



	@Override
	public boolean insertarAutorizacion(String autorizo, String solicitante, String tipo, String razones, String docto)
			throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("autorizo", autorizo);
			map.put("solicitante", solicitante);
			map.put("tipo", tipo);
			map.put("razones", razones);
			map.put("docto", docto);

			this.jdbcTemplate.update("INSERT INTO Autorizacion (Docto,Fecha,Razones,Autorizo,Solicitante,Tipo) VALUES(:docto, GETDATE(), :razones, :autorizo, :solicitante, :tipo)", map);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			TransactionAspectSupport.currentTransactionStatus()
			.setRollbackOnly();
			return false;
		}
	}


	@Override
	public boolean insertaEntregaCliente(EntregaCatClientes entregaCliente)
			throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("facturaOriginal", entregaCliente.isFacturaOriginal());
			map.put("pedidoOriginal", entregaCliente.isPedidoOriginal());
			map.put("numCopiasFactura", entregaCliente.getNum_copiasFactura());
			map.put("numCopiasPedidos", entregaCliente.getNum_copiasPedidos());
			map.put("certificado", entregaCliente.isCertificado());
			map.put("hojaSeguridad", entregaCliente.isHojaSeguridad());
			map.put("idCliente", entregaCliente.getIdCliente());
			map.put("comentarios", entregaCliente.getComentarios());
			map.put("copiaFactura", entregaCliente.isCopiaFactura());
			map.put("copiaPedido", entregaCliente.isCopiaPedido());

			jdbcTemplate.update("INSERT INTO Entrega VALUES(:facturaOriginal, :pedidoOriginal, "
					+ ":numCopiasFactura, :numCopiasPedidos, :certificado, :hojaSeguridad, "
					+ ":idCliente, :comentarios, :copiaFactura, :copiaPedido)", map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			TransactionAspectSupport.currentTransactionStatus()
			.setRollbackOnly();
			return false;
		}
	}


	@Override
	public boolean actualizarDatosEntregaCliente(EntregaCatClientes entregaCliente)
			throws ProquifaNetException {
		try {

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("facturaOriginal", entregaCliente.isFacturaOriginal());
			map.put("pedidoOriginal", entregaCliente.isPedidoOriginal());
			map.put("numCopiasFactura", entregaCliente.getNum_copiasFactura());
			map.put("numCopiasPedidos", entregaCliente.getNum_copiasPedidos());
			map.put("certificado", entregaCliente.isCertificado());
			map.put("hojaSeguridad", entregaCliente.isHojaSeguridad());
			map.put("comentarios", entregaCliente.getComentarios());
			map.put("copiaFactura", entregaCliente.isCopiaFactura());
			map.put("copiaPedido", entregaCliente.isCopiaPedido());
			map.put("idEntregaCliente", entregaCliente.getIdEntregaCliente());			

			StringBuilder sbQuery = new StringBuilder("UPDATE Entrega set Factura_Original = "
					+ ":facturaOriginal, Pedido_Original = :pedidoOriginal, NumCopias_Factura = "
					+ ":numCopiasFactura, NumCopias_Pedidos = :numCopiasPedidos, Certificados = :certificado, "
					+ "Hojas_Seguridad = :hojaSeguridad, Comentarios = :comentarios, Copia_Factura = :copiaFactura, "
					+ "Copia_Pedido = :copiaPedido  where PK_FORMULARIO = :idEntregaCliente ");

			jdbcTemplate.update(sbQuery.toString(), map);
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			throw new ProquifaNetException();
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public EntregaCatClientes obtenerInformacionDeEntregaCliente(Long idCliente)
			throws ProquifaNetException {	
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCliente",idCliente);

			EntregaCatClientes entrega = new EntregaCatClientes();

			String query = "select * from Entrega where FK01_Cliente = :idCliente" ;

			entrega = (EntregaCatClientes) super.jdbcTemplate.queryForObject(query, map, new DatosEntregaClienteRowMapper());


			return entrega;

		} catch (EmptyResultDataAccessException e){
			return new EntregaCatClientes();
		}
		catch (Exception e) {
			e.printStackTrace();
			funcion = new Funcion();
			throw new ProquifaNetException();
		}
	}



	@Override
	public boolean actualizarEntregayRevicionyParcialesCliente(boolean entregayRevicion, boolean parciales, String destino, Long idCliente )
			throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("entregayRevicion",entregayRevicion);
			map.put("parciales",parciales);
			map.put("destino",destino);
			map.put("idCliente",idCliente);            

			StringBuilder sbQuery = new StringBuilder("UPDATE Clientes set Parciales = :parciales, EntregayRevision = :entregayRevicion, Destino = :destino where clave = :idCliente");

			jdbcTemplate.update(sbQuery.toString(), map);
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			throw new ProquifaNetException();
		}
	}


	@Override
	public Long insertaContrato(Contrato contrato)
			throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("fechaInicio",contrato.getFechaInicio());
			map.put("fechaFin",contrato.getFechaFin());
			map.put("idCliente",contrato.getIdCliente());
			map.put("generado",contrato.isGenerado());
			map.put("condionesPago",contrato.getCondionesPago());
			map.put("folio",contrato.getFolio());
			map.put("finalizado",contrato.isFinalizado());

			StringBuilder sbQuery = new StringBuilder("INSERT INTO Contratos VALUES( ");
			sbQuery.append(":fechaInicio, :fechaFin, :idCliente, GETDATE(),:generado,:condionesPago,:folio,:finalizado");
			sbQuery.append(")");

			jdbcTemplate.update(sbQuery.toString(), map);

			return super.queryForLong("SELECT IDENT_CURRENT ('Contratos')");
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException(); 
		}
	}


	@Override
	public Long actualizarContratoCliente(Contrato contrato)
			throws ProquifaNetException {
		try {

			log.info("se actualizara el contrato con los siguientes datos:" + "fecha_inicio" + contrato.getFechaInicio()+ "fecha_fin" + contrato.getFechaFin() + "Generado"+ contrato.isGenerado() +"id_Contrato"+ contrato.getIdContrato());

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("fechaInicio",contrato.getFechaInicio());
			map.put("fechaFin",contrato.getFechaFin());
			map.put("generado",contrato.isGenerado());
			map.put("condionesPago",contrato.getCondionesPago());
			map.put("finalizado",contrato.isFinalizado());
			map.put("idContrato",contrato.getIdContrato());

			StringBuilder sbQuery = new StringBuilder("UPDATE Contratos set fechaInicio = :fechaInicio, "
					+ "FechaFin = :fechaFin,FUA = GETDATE(), Generado = :generado,Condicion_pago = :condionesPago, "
					+ "Finalizado = :finalizado where PK_Contrato = :idContrato");
			jdbcTemplate.update(sbQuery.toString(), map);

			return super.queryForLong("SELECT IDENT_CURRENT ('Contratos')");
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}


	@Override
	public boolean insertaMarcasContrato(Proveedor proveedor, Long idcontrato)
			throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idcontrato",idcontrato);
			map.put("idProveedor",proveedor.getIdProveedor());

			log.info("se insertan las marcas" + idcontrato+ " " + proveedor.getIdProveedor());

			StringBuilder sbQuery = new StringBuilder("INSERT INTO contrato_proveedor VALUES(");
			sbQuery.append(":idcontrato, :idProveedor");
			sbQuery.append(") \n");

			jdbcTemplate.update(sbQuery.toString(), map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}


	@Override
	public boolean eliminarMarcas(Contrato contrato)
			throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idContrato", contrato.getIdContrato());

			String query = " delete from Contrato_Proveedor where FK01_Contrato = :idContrato";
			super.jdbcTemplate.update(query, map);
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}


	@Override
	public boolean eliminarContratoCliente(Contrato contrato)
			throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idContrato", contrato.getIdContrato());

			String query = " DELETE FROM Contratos WHERE PK_Contrato = :idContrato" +
					"\n  DELETE FROM Contrato_Proveedor WHERE FK01_Contrato = :idContrato";
			super.jdbcTemplate.update(query, map);
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Contrato> obtenerContratosPorIdCliente(Long idCliente) throws ProquifaNetException{
		try {
			String sQuery = "";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCliente",idCliente);

			sQuery = "SELECT * FROM Contratos CON " +
					" INNER JOIN ( " +
					" SELECT MAX(FUA) UltimaActualizacion, FK01_Cliente  FROM Contratos GROUP BY FK01_Cliente ) FUA ON FUA.FK01_Cliente = CON.FK01_Cliente " +
					" WHERE CON.FK01_Cliente = :idCliente";

			log.info(sQuery);
			return super.jdbcTemplate.query(sQuery, map, new ContratoClienteRowMapper());


		} catch (Exception e) {
			return null;
		}
	}




	@SuppressWarnings("unchecked")
	@Override
	public List<Proveedor> getMarcasContratoCliente(Long idContrato) {
		try {
			String query = 	"";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idContrato",idContrato);

			query = "select * from Contrato_Proveedor CON "
					+ "LEFT JOIN Proveedores AS PRO On PRO.Clave = CON.FK02_Proveedor "
					+ " where CON.FK01_Contrato = :idContrato";

			return jdbcTemplate.query(query, map, new ProveedorContratosRowMapper());
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConfiguracionPrecioProducto> obtenerConfiguracionesDeContrato(Long idContrato) throws ProquifaNetException {
		StringBuilder sql=new StringBuilder("");
		try{

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idContrato",idContrato);

			String query = " SELECT CASE WHEN CCP.NivelConfigPrecio = 'Producto' THEN CPP.PK_ConfigPrecio_Producto ELSE CCP.AK_ClienteConfigPrecio END idConfiguracion, CCP.NivelConfigPrecio,CPC.FK03_idProveedor idProveedor  FROM Contactos C "+
					"\n INNER JOIN ConfiguracionPrecio_Contrato CPC ON CPC.FK01_Contrato = C.idContacto " +
					"\n INNER JOIN Cliente_ConfiguracionPrecio CCP ON CCP.PK_ClienteConfiguracionPrecio = CPC.FK04_Configuracion " +
					"\n LEFT JOIN ConfiguracionPrecio_Producto CPP ON CCP.AK_ClienteConfigPrecio = CASE WHEN CCP.NivelConfigPrecio = 'Familia' THEN CPP.FK05_CliFamilia " +
					"\n WHEN CCP.NivelConfigPrecio = 'Costo' THEN CPP.FK06_CliCosto WHEN CCP.NivelConfigPrecio = 'Producto' THEN CPP.FK07_CliProducto WHEN CCP.NivelConfigPrecio = 'Clasificacion' THEN CPP.FK09_CliClasificacion END " +
					"\n WHERE C.idContacto = :idContrato";


			//			String query = "Select conf.AK_ClienteConfigPrecio as idConfiguracion,conf.NivelConfigPrecio, conf.FK03_idProveedor as idProveedor from (" +
			//					"\n select * from ConfiguracionPrecio_Contrato as CONFC"+
			//					"\n LEFT JOIN Cliente_ConfiguracionPrecio as CLICONF on CLICONF.PK_ClienteConfiguracionPrecio = CONFC.FK04_Configuracion"+
			//					"\n where FK01_Contrato = "+idContrato+") conf";

			return jdbcTemplate.query(query, map, new DatosConfProRowmapper());
		}catch(Exception e){
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "sql: " + sql);
			e.printStackTrace();
			throw new ProquifaNetException();
		}

	}


	@Override
	public boolean insertarConfContrato(Long idContrato,ParametrosOfertaCliente parOferta, Long idConf)
			throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idContrato",idContrato);
			map.put("idCliente",parOferta.getIdCliente());
			map.put("idProveedor",parOferta.getIdProveedor());
			map.put("idConf",idConf);
			map.put("idConfigFamilia",parOferta.getIdConfigFamilia());

			log.info("idFamilia:" + parOferta.getIdConfigFamilia());

			StringBuilder sbQuery = new StringBuilder("INSERT INTO ConfiguracionPrecio_Contrato VALUES(");
			sbQuery.append(":idContrato, :idCliente, :idProveedor, :idConf, :idConfigFamilia");
			sbQuery.append(") \n");

			jdbcTemplate.update(sbQuery.toString(), map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}


	@Override
	public boolean eliminarConfiguracionesContrato(Contrato contrato, ParametrosOfertaCliente parOferta)
			throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idContrato",contrato.getIdContrato());
			map.put("idProveedor",parOferta.getIdProveedor());
			map.put("idConfigFamilia",parOferta.getIdConfigFamilia());

			String query = "DELETE FROM ConfiguracionPrecio_Contrato WHERE FK01_Contrato = :idContrato AND FK03_idProveedor = :idProveedor AND  FK05_Familia = :idConfigFamilia";
			super.jdbcTemplate.update(query, map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConfiguracionContrato>obtenerMarcasContrato(Long idContrato) {
		try {
			String query = 	"";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idContrato",idContrato);

			query = "select * from Contratos CON " +
					"\n LEFT JOIN  Contrato_Proveedor as CPRO on CPRO.FK01_Contrato = CON.PK_Contrato " +
					"\n LEFT JOIN (SELECT Nombre,Clave FROM Proveedores) PRO ON  PRO.Clave = CPRO.FK02_Proveedor " +
					"\n where PK_Contrato = :idContrato";

			return jdbcTemplate.query(query, map, new ConfiguracionContratoRowMapper());
		} catch (Exception e) {
			return null;
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<ConfiguracionPrecioProducto> obtenerIdConfProducto(Long idProveedor , Long idContrato)
			throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idContrato",idContrato);
			map.put("idProveedor",idProveedor);

			String query= "SELECT distinct * FROM ( " +
					" \n SELECT CASE WHEN CCP.NivelConfigPrecio = 'Familia' THEN CPC.FK05_Familia " +
					" \n WHEN CCP.NivelConfigPrecio = 'Costo' THEN CPP.FK06_CliCosto WHEN CCP.NivelConfigPrecio = 'Producto' THEN CPP.FK07_CliProducto WHEN CCP.NivelConfigPrecio = 'Clasificacion' THEN CPP.FK09_CliClasificacion END idConfiguracion, CCP.NivelConfigPrecio FROM Contratos C " +
					" \n INNER JOIN ConfiguracionPrecio_Contrato CPC ON CPC.FK01_Contrato = C.PK_Contrato " +
					" \n INNER JOIN Cliente_ConfiguracionPrecio CCP ON CCP.PK_ClienteConfiguracionPrecio = CPC.FK04_Configuracion " +
					" \n LEFT JOIN ConfiguracionPrecio_Producto CPP ON CCP.AK_ClienteConfigPrecio = CASE WHEN CCP.NivelConfigPrecio = 'Familia' THEN CPP.FK05_CliFamilia " +
					" \n WHEN CCP.NivelConfigPrecio = 'Costo' THEN CPP.FK06_CliCosto WHEN CCP.NivelConfigPrecio = 'Producto' THEN CPP.FK07_CliProducto WHEN CCP.NivelConfigPrecio = 'Clasificacion' THEN CPP.FK09_CliClasificacion END " +
					" \n WHERE C.PK_Contrato = :idContrato AND CPC.FK03_idProveedor = :idProveedor"+ 
					" \n GROUP BY CCP.NivelConfigPrecio, CPP.FK05_CliFamilia, CPP.FK06_CliCosto, CPP.FK07_CliProducto, CPP.FK09_CliClasificacion, CPC.FK05_Familia  " +
					" \n ) Configuracion";

			return jdbcTemplate.query(query, map, new DatosConfProRowmapper());
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Producto>obtieneProductosContrato(ConfiguracionPrecioProducto confPro) {
		try {
			String query = 	"";
			Map<String, Object> map = new HashMap<String, Object>();

			if(confPro.getNivel().equalsIgnoreCase("Costo")){
				map.put("idConfiguracionCosto",confPro.getIdConfiguracionCosto());
				query = "select * from Productos where idProducto in (select fk01_producto from ConfiguracionPrecio_Producto where FK06_CliCosto =" +confPro.getIdConfiguracionCosto()+")";

			}else if(confPro.getNivel().equalsIgnoreCase("Familia")){
				map.put("idConfiguracionFamilia",confPro.getIdConfiguracionFamilia());
				query = "select * from Productos where idProducto in (select fk01_producto from ConfiguracionPrecio_Producto where FK05_CliFamilia =" +confPro.getIdConfiguracionFamilia()+")";

			}else if(confPro.getNivel().equalsIgnoreCase("Clasificacion")){
				map.put("idConfiguracionClasificacion",confPro.getIdConfiguracionClasificacion());
				query = "select * from Productos where idProducto in (select fk01_producto from ConfiguracionPrecio_Producto where FK09_CliClasificacion =" +confPro.getIdConfiguracionClasificacion()+")";

			}else if(confPro.getNivel().equalsIgnoreCase("Producto")){
				map.put("idConfiguracionPrecioProducto",confPro.getIdConfiguracionPrecioProducto());
				query = "select * from Productos where idproducto = "+ confPro.getIdConfiguracionPrecioProducto();
			}
			return jdbcTemplate.query(query, map, new ProductoContratosRowMapper());
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public TiempoEntrega obtenerTiempoEntregaDeProducto(Long idconf,Long idCliente)
			throws ProquifaNetException {	
		try {
			TiempoEntrega entrega = new TiempoEntrega();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idconf",idconf);
			map.put("idCliente",idCliente);

			String query = " \n select * from Clientes CLI " +
					" \n  LEFT JOIN Cliente_Tiempo_Entrega_Ruta TE on  TE.FK01_ClienteConfiguracionPrecio = :idconf and TE.Ruta = CLI.Ruta COLLATE sql_latin1_general_cp1_ci_as " +
					" \n where Clave = :idCliente";

			entrega = (TiempoEntrega) super.jdbcTemplate.queryForObject(query, map, new TiempoEntregaRutaProveedorRowMapper());


			return entrega;

		} catch (EmptyResultDataAccessException e){
			return new TiempoEntrega();
		}
		catch (Exception e) {
			e.printStackTrace();
			funcion = new Funcion();
			throw new ProquifaNetException();
		}
	}


	public String obtenerEsacDelCliente(Long idcliente) {
		String query = "";
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idcliente",idcliente);
			
			query = "SELECT Vendedor FROM Clientes AS C WHERE C.clave = :idcliente";
			return (String) super.jdbcTemplate.queryForObject(query, map,
					String.class);
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "query: " + query,
					"idcliente: " + idcliente);
			return "";
		}
	}


}


