package com.proquifa.net.persistencia.catalogos.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.catalogos.FormulaPrecio;
import com.proquifa.net.modelo.catalogos.clientes.ParametrosOfertaCliente;
import com.proquifa.net.modelo.catalogos.proveedores.ConfiguracionPrecioProducto;
import com.proquifa.net.modelo.catalogos.proveedores.TiempoEntrega;
import com.proquifa.net.modelo.comun.Cartera;
import com.proquifa.net.modelo.comun.CarteraCliente;
import com.proquifa.net.modelo.comun.CatalogoItem;
import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.Comentario;
import com.proquifa.net.modelo.comun.Direccion;
import com.proquifa.net.modelo.comun.Horario;
import com.proquifa.net.modelo.comun.NivelIngreso;
import com.proquifa.net.modelo.comun.ValorCombo;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.catalogos.CatalogoClientesDAO;
import com.proquifa.net.persistencia.catalogos.impl.mapper.ClienteCatalogoRowMapper;
import com.proquifa.net.persistencia.catalogos.impl.mapper.ComentarioClienteRowMapper;
import com.proquifa.net.persistencia.catalogos.impl.mapper.ConfiguracionPrecioClasificacionClienteRowMapper;
import com.proquifa.net.persistencia.catalogos.impl.mapper.ConfiguracionPrecioClienteCostoRowMapper;
import com.proquifa.net.persistencia.catalogos.impl.mapper.ConfiguracionPrecioMontoClienteRowMapper;
import com.proquifa.net.persistencia.catalogos.impl.mapper.ConfiguracionPrecioRowMapper;
import com.proquifa.net.persistencia.catalogos.impl.mapper.DireccionRowMapper;
import com.proquifa.net.persistencia.catalogos.impl.mapper.FormulaPrecioRowMapper;
import com.proquifa.net.persistencia.catalogos.impl.mapper.HorarioRowMapper;
import com.proquifa.net.persistencia.catalogos.impl.mapper.TiempoEntregaRutaProveedorRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.CarterasClientesRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.CarterasRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.CatalogoItemRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.ClienteRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.ClienteSinCarteraRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.LongMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.MontosGeneralesCarterasRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.ValorComboRowMapper;


@Repository 
public class CatalogoClientesDAOImpl extends DataBaseDAO implements CatalogoClientesDAO {
	
	final Logger log = LoggerFactory.getLogger(CatalogoClientesDAOImpl.class);
	
	Funcion f;

	public StringBuilder getConsultaFormulaCatalogoCliente(String campo,
			String nivel) { 
		StringBuilder consulta = new StringBuilder("");
		// A = Precio de lista G = Factor de utilidad M = Permiso
		// B = Almacen Transito H = Descuento conocido N = Numero de piezas
		// C = Costo consularizacion I = Almacen destino O = Minimo de Orden
		// Compra moneda venta
		// D = Flete documentacion J = Factor IGI Q = factor de licenciamiento
		// E = Factor de importacion = IGI Y DTA K = FactorDTA R = descuento
		// F = Factor Costo fijo L = Honorarios Agente Aduanal

		String B = "", C = "", D = "", J = "", K = "0", L = "0", M = "1", I = "", N = "", R = "", Q = "", O = ""; //, F = "", G = "" ,
		String VA = "", Imp = "", PrecioL = "1", CV = "",  Valor=""; //PrecioU = "",

		Q = " 	CASE WHEN CFL.Monto is not null and CFL.Monto > 0  "
				+ "					THEN CFL.Monto "
				+ "				 WHEN (CFL.Monto is null or CFL.Monto < 1 ) and CFL.Porcentaje > 0 "
				+ "					THEN (1 + (CFL.Porcentaje/100)) "
				+ " 				else"
				+ "					 (1 + ((CASE WHEN LICE.Factor = -1 OR  LICE.Factor IS NULL THEN 0 ELSE LICE.Factor /100 END))) "
				+ "				END ";
		O = " (CASE WHEN CF.ValorEnAduana = 0 OR CF.ValorEnAduana IS NULL THEN 1 ELSE CF.ValorEnAduana END) ";
		B = " (CASE WHEN CF.Flete = 0 OR  CF.Flete IS NULL THEN 0 ELSE CF.Flete END )";
		C = " (CASE WHEN CF.Costo_Consularizacion = -1 OR  CF.Costo_Consularizacion IS NULL THEN 0 ELSE CF.Costo_Consularizacion END)";
		D = " (CASE WHEN CF.Flete_Documentacion = -1 OR  CF.Flete_Documentacion IS NULL THEN 0 ELSE CF.Flete_Documentacion END)";
		J = " ((CASE WHEN CF.Factor_IGI = -1 OR  CF.Factor_IGI IS NULL THEN 0 ELSE CF.Factor_IGI END)/100)";
		K = " ((CASE WHEN CF.FactorDTA = -1 OR  CF.FactorDTA IS NULL THEN 0 ELSE CF.FactorDTA END)/100)";
		R = " (CASE WHEN CF.Descuento IS NULL THEN 1 ELSE (1 - (CF.Descuento/100)) END)";

		if (nivel.equals("Costo")) {
			PrecioL = "(case when (CAT.PrecioLista * PROVEE.TC) = 0 then 1 else (CAT.PrecioLista * PROVEE.TC) end  * ("
					+ R + ")) ";
		} else {
			//(case when (Prod.Costo * PROVEE.TC) = 0 then 1 else (Prod.Costo * PROVEE.TC) end * (	
			PrecioL ="(case when (CASE WHEN CliCP3.PLAnteriorTemp=1 OR CliCP2.PLAnteriorTemp=1 OR CliCP1.PLAnteriorTemp=1 OR CliCP.PLAnteriorTemp=1 OR CliCP3.PrecioListaAnterior=1 OR CliCP2.PrecioListaAnterior=1 OR CliCP1.PrecioListaAnterior=1 OR CliCP.PrecioListaAnterior= 1 THEN COALESCE(Prod.PL_Anterior, Prod.Costo) ELSE Prod.Costo END * PROVEE.TC) = 0 then 1 " + 
					"else (CASE WHEN CliCP3.PLAnteriorTemp=1 OR CliCP2.PLAnteriorTemp=1 OR CliCP1.PLAnteriorTemp=1 OR CliCP.PLAnteriorTemp=1 OR CliCP3.PrecioListaAnterior=1 OR CliCP2.PrecioListaAnterior=1 OR CliCP1.PrecioListaAnterior=1 OR CliCP.PrecioListaAnterior= 1 THEN COALESCE(Prod.PL_Anterior, Prod.Costo) ELSE Prod.Costo END * PROVEE.TC) end *( " 
					+ R + ")) ";
		}

		N = "\n CASE WHEN CF.VALORENADUANA <= 0 OR CF.VALORENADUANA IS NULL THEN case when CF.Piezas is null or CF.Piezas = 0 then 1 else CF.Piezas end "
				+ " ELSE "
				+ "	CASE WHEN  (CFL.Monto is not null and CFL.Monto > 0)  THEN "
				+ "		CEILING(" 
				+"(" + O + " - " + B + ")/(" + PrecioL + " +  " + Q + "))  "
				+ "	ELSE "
				+ "		CEILING(" 
				+"(" + O + " - " + B + ")/(" + PrecioL + " *  " + Q + "))  " + "	END " + " END \n";

		N = " case when " + N + " = 0 then 1 else " + N + " end ";

		VA = " CASE WHEN (CFL.Monto is not null and CFL.Monto > 0)  "
				+ " THEN ( (" + N + " * (" + PrecioL + "  +  " + Q + ") ) + " + B + ") " 
				+ " ELSE ( (" + N + " * " + PrecioL + "  * " + Q + " ) + " + B + ") END	";

		Valor = "(" + PrecioL + ") * (" + N + ")";

		L = " (CASE WHEN (CASE WHEN LAAC.Monto = 0  OR  LAAC.Monto IS NULL THEN LAAC.Porcentaje ELSE LAAC.Monto END) IS NULL THEN 0 ELSE CASE WHEN LAAC.Monto = 0  OR  LAAC.Monto IS NULL THEN ("
				+ VA + ") * ((LAAC.Porcentaje)/100) ELSE LAAC.Monto END END) ";


		M = "  (CASE WHEN CF.Permiso = -1 OR  CF.Permiso IS NULL THEN 0 ELSE CF.Permiso END) ";
		I = "  (CASE WHEN CF.AlmacenDestino = -1 OR  CF.AlmacenDestino IS NULL  THEN 0 ELSE CF.AlmacenDestino END) ";
		Imp = "   ((" + VA + " * (" + J + " + " + K + ")) + " + L + ")  ";
		CV = "  ( (" + VA + ") + (" + Imp + ") + (" + C + ") + (" + D + ") + (" + M + ") + (" + I + ")) ";

		consulta.append(N).append(" Num").append(", ");
		consulta.append(CV).append(" CV").append(", ");
		consulta.append(Valor).append(" VA").append(", ");

		if (nivel.equals("Costo")) {
			consulta.append(" (CAT.PrecioLista * PROVEE.TC)  DIFERENCIAL,");

		} else {
			consulta.append(" (PROD.Costo * PROVEE.TC) DIFERENCIAL,");
		}
		return consulta;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CarteraCliente> obtenerCarterasyClientes(String parametros,
			List<NivelIngreso> niveles)
	// para poder mostrar carteras con los montos se cambian de modo dinamico y
	// se pone para revizar 2014
					throws ProquifaNetException {
		try {
			String query = "";
			f = new Funcion();

			query = "SELECT cli.Clave idcliente , cli.Nombre ,coalesce (cli.Industria, 'ND') industria  , coalesce (ees.esac,'ND') esac , coalesce (ees.idesac,0) idesac , coalesce (eev.ev,'ND') ev , coalesce (eev.idev,0) idev, coalesce (Eevt.evt,'ND') evt, coalesce (Eevt.idevt,0) idevt, coalesce (eco.cobrador,'ND') cobrador , coalesce (eco.idcobrador,0) idcobrador , cart.folio " +
					" \n,coalesce ( cart.publicada, 0 ) publicada,coalesce ( cart.sistema, 0 ) sistema,coalesce ( cart.corporativo, 0 ) corporativo,coalesce ( cart.internacional, 0 ) Internacional,  cart.nombre cart_Nombre, ccr.creador, cart.pk_cartera idcartera, Cart.fk01_EV cart_idEV, Cart.FK06_EVT cart_idEVT, FK02_esac as cart_idesac, fk03_Cobrador cart_idcobrador, Fk04_usuario cart_idElaboro, cart.fua  cart_fua, Cart.Area  " +
					" \n ,ces.esac cart_esac  " +
					" \n ,cev.ev cart_ev, Cevt.evt cart_evt, cco.cobrador cart_Cobrador, cli.ruta,coalesce ( corp.idcorporativo ,0 ) idcorporativo, idindustria" +
					" \n from Clientes as cli  " +
					" \n left join (select Usuario  esac, clave idesac from Empleados ) as  Ees on ees.esac = cli.vendedor  " +
					" \n left join (select Usuario ev, clave idev from Empleados ) as  Eev on eev.idev  = cli.fk01_ev and cli.FK01_EV <> 0  " +
					" \n left join (select Usuario evt, clave idevt from Empleados ) as  Eevt on Eevt.idevt  = cli.fk03_EVT and cli.FK03_EVT <> 0 " +
					" \n left join (select Usuario cobrador, clave idcobrador from Empleados ) as  Eco on eco.idcobrador  = cli.cobrador and cli.Cobrador <> 0 " +
					//					" \n left join (select * from HistorialVenta where Periodo = 'Anual') as hv on  hv.Anio = year(GETDATE ())-1 and hv.FK01_Cliente = cli.Clave   " +
					" \n left join(select fk01_cliente , fk02_cartera  from Cliente_Cartera )as cc on cc.fk01_cliente = cli.clave " +
					" \n left join (select * from cartera ) AS Cart ON Cart.pk_cartera = cc.fk02_Cartera OR (CART.Sistema = 1 AND CART.Publicada = 1 AND CART.CORPORATIVO = 0 AND CART.FK04_Usuario IS NULL AND ((not factura in ('','Ryndem') and YEAR (CLI.FechaRegistro) = YEAR(GETDATE()) and cart.Nombre = 'clientes Nuevos')  " +
					" \n OR (YEAR (CLI.FechaRegistro) < YEAR(GETDATE()) and cli.pais <>  'MEXICO' and cli.pais <>  '--NINGUNO--' and cart.internacional =1)))  " +
					" \n left join (select Usuario  esac, clave idesac from Empleados ) as  ces on ces.idesac  = cart.fk02_esac " +
					" \n left join (select Usuario ev, clave idev from Empleados ) as  cev on cev.idev  = cart.FK01_EV and Cart.FK01_EV <> 0  " +
					" \n left join (select Usuario evt, clave idevt from Empleados ) as  Cevt on Cevt.idevt  = cart.FK06_EVT and Cart.FK06_EVT <> 0 " +  
					" \n left join (select Usuario cobrador, clave idcobrador from Empleados ) as  cco on cco.idcobrador  = cart.fk03_cobrador and cart.fk03_cobrador <> 0  " +
					" \n left join (select Usuario creador, clave idcreador from Empleados ) as  ccr on ccr.idcreador  = cart.fk04_usuario and cart.fk04_usuario <> 0 " +
					" \n LEFT JOIN (SELECT Nombre AS NombreCorporativo , PK_Corporativo idcorporativo FROM Corporativo ) as corp on corp.idcorporativo =  cli.fk02_corporativo  " 
					+ " \n LEFT JOIN (  SELECT valor, CASE WHEN Valor = 'farma' THEN 'FARMACEUTICA' WHEN Valor = 'ALIMENTOS' THEN 'ALIMENTOS Y BEBIDAS'  "
					+ " \n 	WHEN VALOR = 'CLINICO' OR VALOR= 'Cl?nico' THEN 'CLINICA HOSPITALARIA'ELSE Valor  END VALORINDUSTRIA, PK_Folio Idindustria  from ValorCombo where Concepto = 'industrial'   "
					+ " \n 	)as vc on vc.valorindustria = cli.Industria OR ( VC.VALORINDUSTRIA =  'GOBIERNO' AND CLI.Sector = 'PUBLICO')   "
					+ " \n WHERE cli.Habilitado =  1  " 
					+ " \n and cart.PK_Cartera is not null" 
					+ " \n   AND "
					+  f.obtenerEmpresasProquifa("cli.clave")
					+ " "
					+ parametros
					+ "\n   order by Nombre " ;
			log.info(query);
			//					,coalesce (hv.montoventa ,0) MontoVentaAnt

			return super.jdbcTemplate.query(query, new CarterasClientesRowMapper());
		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CarteraCliente> findMontosGeneralesCarteras(String parametros,
			List<NivelIngreso> niveles) throws ProquifaNetException {
		try {
			String query = "";
			f = new Funcion();

			query = " \n select distinct pre.idcartera  ,pre.publicada ,  sum (noclientes )noclientes, sum(debemos ) debemos , SUM (debe) debe      "
					+ " \n  , sum (pre.MontoVentaAnt) cart_montoAnterior, sum(pre.ConversionUSD ) cart_MontoActual , (sum(pre.ConversionUSD )/MONTH(GETDATE())) cart_PF  "
					+ " \n  ,((sum(pre.ConversionUSD)/MONTH(GETDATE()))*12) cart_PV , max(importancia) importancia , max(dificultad )dificultad   "
					+ " \n  , sum (COALESCE(ob.porcentajeanual ,0)) ObjetivoCrecimiento,SUM( COALESCE( ob.Fundamental ,0))ObjetivoCrecimientoFundamental  "
					+ " \n  ,sum (coalesce((MontoVentaAnt* ( 1.00 + (ob.porcentajeanual/100  ))),0)) MONTO_D ,sum(coalesce((MontoVentaAnt*( 1.00+ (OB.Fundamental/100))),0)) MONTO_F  "
					+ " \n  from (     "
					+ " \n 	select distinct  cli.Clave idcliente, COALESCE (DEBE,0 ) DEBE  ,COALESCE ( DEBEMOS,0) DEBEMOS  "
					+ // sum (debemos )debemos,SUM (debe) debe " +
					" \n 	,coalesce (hv.montoventa ,0) MontoVentaAnt,coalesce ( cart.publicada, 0 ) publicada, cart.pk_cartera idcartera "
					+

					" \n 	,case when contcli.importancia is null or contcli.contador is null or contcli.importancia = 0 or contcli.contador = 0  then 1 else coalesce(round(cast ( contCli.importancia as float) /coalesce (cast ( contcli.contador as float ),1),0),0) end  importancia  "
					+ " \n 	,case when contcont.dificultad is null or contcont.contador is null or contcont.dificultad = 0 or contcont.contador =0 then 1 else coalesce(round(cast ( contCont.dificultad as float) /coalesce (cast ( contCont.contador as float ),1),0),0) end  dificultad   "
					+

					// " \n 	,coalesce(round(cast ( contCli.importancia as float) /cast ( contcli.contador as float ),0),0) importancia   "
					// +
					// " \n 	,coalesce(round(cast ( contCont.dificultad as float) /cast ( contCont.contador as float ),0),0) dificultad "
					// +
					" \n 	,coalesce ( con.contactos,0 ) contactos  "
					+ " \n 	,1 as noclientes    "
					+ " \n    ,coalesce(CAST((SUM("
					+ f.sqlConversionMonedasADolar("F.Moneda", "PF.Cant",
							"PF.Importe", "MON", "F.TCambio", "", false)
					+ ")"
					+ " \n   - SUM(  CASE WHEN PNC.FK02_PFactura IS NOT NULL THEN "
					+ f.sqlConversionMonedasADolar("NC.Moneda", "PF.Cant",
							"PF.Importe", "MON2", "NC.TCambio", "", false)
					+ " ELSE 0 END ) )  AS DECIMAL(9,2)),0)  AS ConversionUSD, "
					+ " \n "
					+ f.sqlLimitesNivelIngresoCClientesNuevos(niveles,
							"HV.MontoVenta", "year(getdate())", false, true)
					+ " AS NIVEL, "
					+ " \n "
					+ f.sqlLimitesNivelIngresoCClientesNuevos(niveles,
							"HV.MontoVenta", "year(getdate())", true, true)
					+ " AS montoVenta "
					+ " \n from Clientes as cli       "
					+ " \n 		left join (select Usuario  esac, clave idesac from Empleados ) as  Ees on ees.esac = cli.vendedor  "
					+ " \n 		left join (select Usuario ev, clave idev from Empleados ) as  Eev on eev.idev  = cli.fk01_ev and cli.FK01_EV <> 0      "
					+ " \n 		left join (select Usuario cobrador, clave idcobrador from Empleados ) as  Eco on eco.idcobrador  = cli.cobrador and cli.Cobrador <> 0       "
					+ " \n 		left join (select * from HistorialVenta where Periodo = 'Anual') as hv on  hv.Anio = year(GETDATE ())-1 and hv.FK01_Cliente = cli.Clave    "
					+ " \n 		left join(select fk01_cliente , Cliente_Cartera.FK02_Cartera from Cliente_Cartera )as cc on cc.fk01_cliente = cli.clave "
					+ " \n 		left join (select * from cartera ) AS Cart ON Cart.pk_cartera = cc.fk02_Cartera     "
					+ " \n 			OR (CART.Sistema = 1 AND CART.Publicada = 1 AND CART.CORPORATIVO = 0 AND CART.FK04_Usuario IS NULL AND ((YEAR (CLI.FechaRegistro) = YEAR(GETDATE()) and cart.Nombre = 'clientes Nuevos')   "
					+ " \n 			OR (YEAR (CLI.FechaRegistro) < YEAR(GETDATE()) and cli.pais <>  'MEXICO' and cli.pais <>  '--NINGUNO--' and cart.Nombre = 'CLIENTES INTERNACIONALES')))  "
					+

					" \n 		left join (select Usuario  esac, clave idesac from Empleados ) as  ces on ces.idesac  = cart.fk02_esac    "
					+ " \n 		left join (select Usuario ev, clave idev from Empleados ) as  cev on cev.idev  = cart.FK01_EV and Cart.FK01_EV <> 0      "
					+ " \n 		left join (select Usuario cobrador, clave idcobrador from Empleados ) as  cco on cco.idcobrador  = cart.fk03_cobrador and cart.fk03_cobrador <> 0       "
					+ " \n 		left join (select Usuario creador, clave idcreador from Empleados ) as  ccr on ccr.idcreador  = cart.fk04_usuario and cart.fk04_usuario <> 0   "
					+ " \n 		LEFT JOIN (SELECT * FROM Facturas as F  where F.DeSistema= 1 AND  F.Importe > 0 AND F.Fecha > '20091231 23:59' AND F.Estado<>'Cancelada' AND F.Estado<>'Por Cancelar'     "
					+ " \n 	    AND year ( F.Fecha) = year(getdate())) AS F ON  F.Cliente = cli.Clave "
					+ " \n 		LEFT JOIN (SELECT * FROM Monedas) AS MON ON cast(MON.Fecha as date) = cast(F.Fecha as date)    "
					+ " \n 		LEFT JOIN (SELECT * FROM Remisiones) RE ON CAST(RE.Nota AS VARCHAR(100)) = F.Factura AND F.DeRemision = 1    "
					+ " \n 		LEFT JOIN (SELECT * FROM PRemisiones) PRE ON PRE.Factura = RE.Factura AND PRE.FPor = RE.FPor AND PRE.CPedido = RE.CPedido     "
					+ " \n 		LEFT JOIN (SELECT Importe, Cant, PPedido, Factura, FPor, CPedido, idPFactura FROM PFacturas) AS PF ON PF.Factura = F.Factura AND PF.FPor = F.FPor    "
					+ " \n   			AND CASE WHEN F.DeRemision = 1 THEN PRE.CPedido ELSE F.CPedido END = PF.CPedido     "
					+ " \n  			AND CASE WHEN F.DeRemision = 1 THEN PRE.PPedido ELSE PF.PPedido END = PF.PPedido     "
					+ " \n 		LEFT JOIN (SELECT * FROM PPedidos) PP ON PP.CPedido = PF.CPedido AND PP.Part = PF.PPedido    "
					+ " \n 		LEFT JOIN (SELECT * FROM PNotaCredito) PNC ON PNC.FK02_PFactura = PF.idPFactura        "
					+ " \n 		LEFT JOIN (SELECT * FROM NotaCredito) NC ON NC.PK_Nota = PNC.FK01_Nota        "
					+ " \n 		LEFT JOIN (SELECT * FROM Monedas) AS MON2 ON cast(MON2.Fecha as date) = cast(NC.Fecha as date)   "
					+ " \n 		LEFT JOIN (SELECT Nombre AS NombreCorporativo , PK_Corporativo idcorporativo FROM Corporativo ) as corp on corp.idcorporativo =  cli.fk02_corporativo    "
					+ " \n 		LEFT JOIN (  SELECT valor, CASE WHEN Valor = 'farma' THEN 'FARMACEUTICA' WHEN Valor = 'ALIMENTOS' THEN 'ALIMENTOS Y BEBIDAS'  "
					+ " \n 			WHEN VALOR = 'CLINICO' OR VALOR= 'Cl?nico' THEN 'CLINICA HOSPITALARIA'ELSE Valor  END VALORINDUSTRIA, PK_Folio Idindustria  from ValorCombo where Concepto = 'industrial'   "
					+ " \n 			)as vc on vc.valorindustria = cli.Industria OR ( VC.VALORINDUSTRIA =  'GOBIERNO' AND CLI.Sector = 'PUBLICO')   "
					+ " \n 		left join (select CAST(( SUM( CASE  WHEN NC.Moneda = 'Pesos' OR NC.Moneda = 'MXN' OR NC.Moneda = 'M.N.' OR NC.Moneda = 'M.N'	THEN ((nc.Importe) /  COALESCE(CASE WHEN NC.TCambio = 0 THEN MON2.PDolar ELSE NC.TCambio END , MON2.PDolar))     "
					+ " \n 			WHEN NC.Moneda = 'Euros' OR NC.Moneda = 'EUR' THEN nc.Importe * (MON2.EDolar)   "
					+ " \n 			WHEN NC.Moneda = 'Libras' THEN nc.Importe * (MON2.LDolar)     "
					+ " \n 			WHEN NC.Moneda = 'DlCan'  THEN nc.Importe * (MON2.DDolar)      "
					+ " \n 			WHEN NC.Moneda = 'Yenes'  THEN nc.Importe * (MON2.YDolar)      "
					+ " \n 			ELSE nc.Importe END ))  AS DECIMAL(9,2)) AS debemos,FK01_Cliente as idcliente      "
					+ " \n 			from NotaCredito as nc      "
					+ " \n 				LEFT JOIN (SELECT * FROM Monedas) AS MON2 ON cast(MON2.Fecha as date) = cast(NC.Fecha as date)     "
					+ " \n 			where estado is null group by FK01_Cliente     "
					+ " \n 		)as debemos on debemos.idcliente = cli.clave     "
					+

					" \n 		left join ( select DISTINCT f.cliente , CAST((SUM(PF.Cant * ( CASE  WHEN F.Moneda = 'Pesos' OR F.Moneda = 'MXN' OR F.Moneda = 'M.N.'	THEN ((PF.Importe) /  COALESCE(CASE WHEN F.TCambio = 0 THEN MON.PDolar ELSE F.TCambio END , MON.PDolar))  "
					+ " \n 			WHEN F.Moneda = 'Euros' OR F.Moneda = 'EUR'		 THEN PF.Importe * (MON.EDolar)  "
					+ " \n 			WHEN F.Moneda = 'Libras' THEN PF.Importe * (MON.LDolar)       "
					+ " \n 			WHEN F.Moneda = 'DlCan'  THEN PF.Importe * (MON.DDolar)        "
					+ " \n 			WHEN F.Moneda = 'Yenes'  THEN PF.Importe * (MON.YDolar)       "
					+ " \n 			ELSE PF.Importe END )))  AS DECIMAL(9,2)) as  debe   "
					+ " \n 			FROM Facturas  as f  "
					+ " \n 				LEFT JOIN (SELECT Importe, Cant, Factura, FPor, Estado  FROM PFacturas) AS PF ON PF.Factura = F.Factura AND PF.FPor = F.FPor  "
					+ " \n 				LEFT JOIN (SELECT * FROM Monedas) AS MON ON cast(MON.Fecha as date) = cast(F.Fecha as date)     "
					+ " \n 			where f.DeSistema = 1 AND "
					+ f.obtenerEmpresasProquifa("F.Cliente")
					+ "  AND year ( f.fecha) =  year(GETDATE()) AND f.Estado = 'Por Cobrar'  "
					+ " \n 			group by  f.Cliente 	 "
					+ " \n 		) as debe on debe.Cliente =  cli.clave  "
					+

					" \n 		LEFT JOIN ( select FK02_Cliente , COUNT (idContacto ) contactos , SUM(coalesce (dificultad,0))dificultad  from contactos where Dificultad is not null and Dificultad >  0  group by FK02_Cliente )  as con on con.FK02_Cliente = CLI.Clave  "
					+ " \n 		LEFT JOIN ( select distinct FK01_Cliente Idcliente,SUM ( Importe)importe   from NotaCredito where Estado is null group by FK01_Cliente ) AS NotaC ON NotaC.idcliente = cli.clave    "
					+ " \n 		LEFT JOIN ( select COUNT (clave) contador, SUM (Importancia )importancia, FK02_Cartera from Cliente_Cartera , Clientes where  importancia >0  and FK01_Cliente = Clave group by FK02_Cartera )AS contCli on contcli.FK02_Cartera = cart.PK_Cartera   "
					+ " \n 		LEFT JOIN ( select COUNT (clave) contador, SUM (dificultad )dificultad, fk02_cliente  from contactos where  Dificultad  >0  group by FK02_Cliente )as contCont on contCont.FK02_Cliente = cli.clave   "
					+ " \n 		where cli.Habilitado =  1  and cart.PK_Cartera is not null	AND "
					+ f.obtenerEmpresasProquifa("cli.clave")
					+ " "
					+ parametros
					+ " \n 		group by cli.Clave, cli.Nombre ,cli.Industria,ees.esac,ees.idesac,eev.ev,eev.idev,eco.cobrador,eco.idcobrador,hv.MontoVenta, Cart.Publicada, cli.FechaRegistro , cli.rol,  "
					+ " \n 		cart.nombre , cart.pk_cartera, FK02_esac , fk03_Cobrador , Fk04_usuario , cart.fua, Cart.fk01_EV,ces.esac , cev.ev, cco.cobrador  , ccr.creador, vc.idindustria, cli.ruta, corp.idCorporativo ,  "
					+ " \n 		corp.nombrecorporativo  ,contCli.importancia , contCli.contador ,contCont.contador,contCont.dificultad, con.contactos, debe , debemos   "
					+

					" \n 	)as pre  left join(select * from ObjetivoCrecimiento_NI)as ob on ob.NivelIngreso = pre.nivel   "
					+ " \n group by pre.publicada ,pre.idcartera "
					+ " \n 	order by idcartera 	 ";

			log.info(query);

			return super.jdbcTemplate.query(query,
					new MontosGeneralesCarterasRowMapper());
		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CarteraCliente> queryCarteraCliente(String parametros,
			List<NivelIngreso> niveles, Long idCartera)
	// para poder mostrar carteras con los montos se cambian de modo dinamico y
	// se pone para revizar 2014
					throws ProquifaNetException {
		try {
			String query = "";
			String condicion = "";
			f = new Funcion();

			if(idCartera > 0)
				condicion = " and Cart.PK_Cartera = " + idCartera;
			else 
				condicion = " ";
			log.info(condicion);

			query = "  select distinct pre.idcliente , pre.Nombre ,pre.industria, pre.esac, pre.idesac , pre.idev , pre.ev , pre.idevt, pre.evt, pre.cobrador , pre.idcobrador , pre.MontoVentaAnt montoVentaAnterior2, pre.montoanreiror , pre.montoventa as MontoVentaAnt , pre.ConversionUSD montoVenta   , pre.publicada, pre.sistema, pre.corporativo, pre.internacional   "
					+ " \n ,pre.cart_Nombre, pre.cart_Area, pre.cart_Justificacion,  pre.idcartera, pre.cart_EV, pre.cart_evt, pre.cart_cobrador, pre.cart_esac , pre.cart_fua , pre.cart_idcobrador, pre.cart_idesac, pre.cart_idev, pre.cart_idEVT, pre.cart_idElaboro , pre.cart_usuario , idindustria, pre.folio  "
					+ " \n ,pre.idcorporativo , pre.nombrecorporativo, debe , debemos, pre.mensajero mensajero, pre.nombre_mensajero "
					+ " \n  ,COALESCE(ob.porcentajeanual ,0) ObjetivoCrecimiento, COALESCE( ob.Fundamental ,0)ObjetivoCrecimientoFundamental ,coalesce (pre.Ruta ,'ND' ) Ruta     "
					+ " \n  ,case when NIVEL = 'ClientesNuevos' then 'NUEVOS' WHEN NIVEL = 'Distribuidor' then 'DISTRIBUIDOR' WHEN NIVEL = 'bajo' then 'BAJO' else NIVEL END NIVEL, CATEGORIA      "
					+ " \n  ,coalesce((MontoVentaAnt* ( 1.00 + (ob.porcentajeanual/100  ))),0) MONTO_D ,coalesce((MontoVentaAnt*( 1.00+ (OB.Fundamental/100))),0) MONTO_F     "
					+ " \n  ,ConversionUSD /MONTH (GETDATE()) * 12 cli_proyeccionventa  ,ConversionUSD /MONTH (GETDATE())  cli_Promediofacturado ,  "
					+

					" \n  (select SUM (conversionUSd) factglobal from (  "
					+ " \n  select  f.cliente , " + " \n coalesce(CAST((SUM("
					+ f.sqlConversionMonedasADolar("F.Moneda", "PF.Cant",
							"PF.Importe", "MON", "F.TCambio", "", false)
					+ ")"
					+ " \n  - SUM(  CASE WHEN PNC.FK02_PFactura IS NOT NULL THEN "
					+ f.sqlConversionMonedasADolar("NC.Moneda", "PF.Cant",
							"PF.Importe", "MON2", "NC.TCambio", "", false)
					+ " ELSE 0 END ) )  AS DECIMAL(9,2)),0)  AS ConversionUSD "
					+ " \n  from Facturas as F   "
					+ " \n  left join clientes as c on c.clave = f.cliente  "
					+ " \n  left join PFacturas as pf on pf.Factura = f.Factura and pf.FPor = f.FPor   "
					+ " \n  left join Monedas as mon on mon.Fecha = f.Fecha  "
					+ " \n  LEFT JOIN (SELECT * FROM PNotaCredito) PNC ON PNC.FK02_PFactura = PF.idPFactura     "
					+ " \n  LEFT JOIN (SELECT * FROM NotaCredito) NC ON NC.PK_Nota = PNC.FK01_Nota     "
					+ " \n  LEFT JOIN (SELECT * FROM Monedas) AS MON2 ON cast(MON2.Fecha as date) = cast(NC.Fecha as date)    "
					+ " \n  where YEAR (f.Fecha ) =  year(GETDATE ())and F.Estado<>'Cancelada' AND F.Estado<>'Por Cancelar' and f.DeSistema = 1 and c.Habilitado = 1   "
					+ " \n  group by f.cliente ) as s) as facturacionGlobal,  "
					+ "pre.publicada publicada, pre.imagen"

					+ " \n  from (    "
					+

					" \n  select cli.Clave idcliente , cli.Nombre, cli.imagen ,coalesce (cli.Industria, 'ND') industria  , coalesce (ees.esac,'ND') esac , coalesce (ees.idesac,0) idesac , coalesce (eev.ev,'ND') ev , coalesce (eev.idev,0) idev, coalesce (eevt.idevt,0) idevt, coalesce (eevt.evt,'ND') evt, coalesce (eco.cobrador,'ND') cobrador , coalesce (eco.idcobrador,0) idcobrador , cart.folio      "
					+ " \n  ,coalesce (hv.montoventa ,0) MontoVentaAnt,coalesce (FPeriodoAnt.MontoAnteiror, 0) montoanreiror ,coalesce ( cart.publicada, 0 ) publicada,coalesce ( cart.sistema, 0 ) sistema,coalesce ( cart.corporativo, 0 ) corporativo,coalesce ( cart.internacional, 0 ) Internacional,  cart.nombre cart_Nombre, cart.Area cart_Area, cart.Justificacion  cart_Justificacion , cart.pk_cartera idcartera, Cart.fk01_EV cart_idEV, cart.FK06_EVT cart_idEVT, FK02_esac as cart_idesac, fk03_Cobrador cart_idcobrador, Fk04_usuario cart_idElaboro, cart.fua  cart_fua, ces.esac cart_esac, msj.mensajero nombre_mensajero, cart.FK07_mensajero mensajero "
					+ " \n  ,cev.ev cart_ev, cevt.evt cart_evt, cco.cobrador cart_Cobrador, cli.ruta, coalesce ( corp.idcorporativo ,0 ) idcorporativo, coalesce ( corp.nombreCorporativo,'NA' ) as nombrecorporativo,coalesce(debemos.debemos , 0) debemos,coalesce(debe.debe, 0) debe  "
					+ " \n  ,ccr.creador cart_usuario ,idindustria ,   "
					+ " \n coalesce ( CAST((SUM("
					+ f.sqlConversionMonedasADolar("F.Moneda", "PF.Cant",
							"PF.Importe", "MON", "F.TCambio", "", false)
					+ ")"
					+ " \n  - SUM(  CASE WHEN PNC.FK02_PFactura IS NOT NULL THEN "
					+ f.sqlConversionMonedasADolar("NC.Moneda", "PF.Cant",
							"PF.Importe", "MON2", "NC.TCambio", "", false)
					+ " ELSE 0 END ) )  AS DECIMAL(9,2)),0)  AS ConversionUSD, "
					+ " \n "
					+ f.sqlLimitesNivelIngresoCClientesNuevos(niveles,
							"HV.MontoVenta", "year(getdate())", false, true)
					+ " AS NIVEL, "
					+ " \n "
					+ f.sqlLimitesNivelIngresoCClientesNuevos(niveles,
							"HV.MontoVenta", "year(getdate())", true, true)
					+ " AS montoVenta, "
					+ " \n "
					+ f.sqlLimitesNivelIngresoClientesCategorias(niveles,
							"HV.MontoVenta")
					+ " CATEGORIA  "
					+

					" \n  from Clientes as cli     "
					+ " \n  left join (select Usuario  esac, clave idesac from Empleados ) as  Ees on ees.esac = cli.vendedor     "
					+ " \n  left join (select Usuario ev, clave idev from Empleados ) as  Eev on eev.idev  = cli.fk01_ev and cli.FK01_EV <> 0    "
					+ " \n  left join (select Usuario evt, clave idevt from Empleados ) as  Eevt on Eevt.idevt  = cli.fk03_evt and cli.FK03_EVT <> 0    "
					+ " \n  left join (select Usuario cobrador, clave idcobrador from Empleados ) as  Eco on eco.idcobrador  = cli.cobrador and cli.Cobrador <> 0     "

					+ " \n  left join (select * from HistorialVenta where Periodo = 'Anual') as hv on  hv.Anio = year(GETDATE ())-1 and hv.FK01_Cliente = cli.Clave    "
					+ " \n left join(select fk01_cliente , fk02_cartera  from Cliente_Cartera )as cc on cc.fk01_cliente = cli.clave "
					+

					" \n   left join (select * from cartera ) AS Cart ON Cart.pk_cartera = cc.fk02_Cartera   "
					+ " \n  		OR (CART.Sistema = 1 AND CART.Publicada = 1 AND CART.CORPORATIVO = 0 AND CART.FK04_Usuario IS NULL AND ((not factura in ('','Ryndem') and YEAR (CLI.FechaRegistro) = YEAR(GETDATE()) and cart.Nombre = 'clientes Nuevos') "
					+ " \n  		OR (YEAR (CLI.FechaRegistro) < YEAR(GETDATE()) and cli.pais <>  'MEXICO' and cli.pais <>  '--NINGUNO--' and cart.internacional =1)))   "
					+

					" \n  left join (select Usuario  esac, clave idesac from Empleados ) as  ces on ces.idesac  = cart.fk02_esac  "
					+ " \n  left join (select Usuario ev, clave idev from Empleados ) as  cev on cev.idev  = cart.FK01_EV and Cart.FK01_EV <> 0    "
					+ " \n  left join (select Usuario evt, clave idevt from Empleados ) as  cevt on cevt.idevt  = cart.fk06_EVT and cart.fk06_EVT <> 0     "
					+ " \n  left join (select Usuario cobrador, clave idcobrador from Empleados ) as  cco on cco.idcobrador  = cart.fk03_cobrador and cart.fk03_cobrador <> 0     "	
					+ " \n  left join (select Usuario mensajero, clave from Empleados ) as  msj on msj.Clave  = cart.FK07_mensajero and cart.FK07_mensajero <> 0  "
					+ " \n  left join (select Usuario creador, clave idcreador from Empleados ) as  ccr on ccr.idcreador  = cart.fk04_usuario and cart.fk04_usuario <> 0    "
					+

					" \n  LEFT JOIN (SELECT * FROM Facturas as f where F.DeSistema= 1 AND  F.Importe > 0 AND F.Fecha > '20091231 23:59' AND F.Estado<>'Cancelada' AND F.Estado<>'Por Cancelar'   "
					+ " \n   	AND  year ( F.Fecha) = year(getdate())) AS F ON  F.Cliente = cli.Clave     "
					+ " \n  LEFT JOIN (SELECT * FROM Monedas) AS MON ON cast(MON.Fecha as date) = cast(F.Fecha as date)      "
					+ " \n  LEFT JOIN (SELECT * FROM Remisiones) RE ON CAST(RE.Nota AS VARCHAR(100)) = F.Factura AND F.DeRemision = 1   "
					+ " \n  LEFT JOIN (SELECT * FROM PRemisiones) PRE ON PRE.Factura = RE.Factura AND PRE.FPor = RE.FPor AND PRE.CPedido = RE.CPedido      "
					+ " \n  LEFT JOIN (SELECT Importe, Cant, PPedido, Factura, FPor, CPedido, idPFactura FROM PFacturas) AS PF ON PF.Factura = F.Factura AND PF.FPor = F.FPor       "
					+ " \n  	AND CASE WHEN F.DeRemision = 1 THEN PRE.CPedido ELSE F.CPedido END = PF.CPedido       "
					+ " \n 	AND CASE WHEN F.DeRemision = 1 THEN PRE.PPedido ELSE PF.PPedido END = PF.PPedido      "
					+ " \n  LEFT JOIN (SELECT * FROM PPedidos) PP ON PP.CPedido = PF.CPedido AND PP.Part = PF.PPedido     "
					+ " \n  LEFT JOIN (SELECT * FROM PNotaCredito) PNC ON PNC.FK02_PFactura = PF.idPFactura      "
					+ " \n  LEFT JOIN (SELECT * FROM NotaCredito) NC ON NC.PK_Nota = PNC.FK01_Nota      "
					+ " \n  LEFT JOIN (SELECT * FROM Monedas) AS MON2 ON cast(MON2.Fecha as date) = cast(NC.Fecha as date)   "
					+ " \n  LEFT JOIN (SELECT Nombre AS NombreCorporativo , PK_Corporativo idcorporativo FROM Corporativo ) as corp on corp.idcorporativo =  cli.fk02_corporativo  "
					+ " \n	LEFT JOIN (  SELECT valor, CASE WHEN Valor = 'farma' THEN 'FARMACEUTICA' WHEN Valor = 'ALIMENTOS' THEN 'ALIMENTOS Y BEBIDAS' WHEN VALOR = 'CLINICO' OR VALOR= 'Clínico' THEN 'CLINICA HOSPITALARIA'ELSE Valor  END VALORINDUSTRIA, PK_Folio Idindustria  from ValorCombo where Concepto = 'industrial' "
					+ " \n		 )as vc on vc.valorindustria = cli.Industria OR ( VC.VALORINDUSTRIA =  'GOBIERNO' AND CLI.Sector = 'PUBLICO') "
					+ " \n left join (select CAST(( SUM( CASE  WHEN NC.Moneda = 'Pesos' OR NC.Moneda = 'MXN' OR NC.Moneda = 'M.N.' OR NC.Moneda = 'M.N'	THEN ((nc.Importe) /  COALESCE(CASE WHEN NC.TCambio = 0 THEN MON2.PDolar ELSE NC.TCambio END , MON2.PDolar))   "
					+ " \n 	WHEN NC.Moneda = 'Euros' OR NC.Moneda = 'EUR' THEN nc.Importe * (MON2.EDolar)   "
					+ " \n 	WHEN NC.Moneda = 'Libras' THEN nc.Importe * (MON2.LDolar)   "
					+ " \n 	WHEN NC.Moneda = 'DlCan'  THEN nc.Importe * (MON2.DDolar)    "
					+ " \n 	WHEN NC.Moneda = 'Yenes'  THEN nc.Importe * (MON2.YDolar)    "
					+ " \n 	ELSE nc.Importe END ))  AS DECIMAL(9,2)) AS debemos,FK01_Cliente as idcliente    "
					+ " \n 	from NotaCredito as nc    "
					+ " \n 		LEFT JOIN (SELECT * FROM Monedas) AS MON2 ON cast(MON2.Fecha as date) = cast(NC.Fecha as date)    "
					+ " \n 	where estado is null group by FK01_Cliente   "
					+ " \n 	)as debemos on debemos.idcliente = cli.clave   "
					+

					" \n left join ( select DISTINCT f.cliente , CAST((SUM(PF.Cant * ( CASE  WHEN F.Moneda = 'Pesos' OR F.Moneda = 'MXN' OR F.Moneda = 'M.N.'	THEN ((PF.Importe) /  COALESCE(CASE WHEN F.TCambio = 0 THEN MON.PDolar ELSE F.TCambio END , MON.PDolar))  "
					+ " \n WHEN F.Moneda = 'Euros' OR F.Moneda = 'EUR'		 THEN PF.Importe * (MON.EDolar)  "
					+ " \n WHEN F.Moneda = 'Libras' THEN PF.Importe * (MON.LDolar)       "
					+ " \n WHEN F.Moneda = 'DlCan'  THEN PF.Importe * (MON.DDolar)        "
					+ " \n WHEN F.Moneda = 'Yenes'  THEN PF.Importe * (MON.YDolar)       "
					+ " \n ELSE PF.Importe END )))  AS DECIMAL(9,2)) as  debe   "
					+ " \n FROM Facturas  as f  "
					+ " \n LEFT JOIN (SELECT Importe, Cant, Factura, FPor, Estado  FROM PFacturas) AS PF ON PF.Factura = F.Factura AND PF.FPor = F.FPor  "
					+ " \n LEFT JOIN (SELECT * FROM Monedas) AS MON ON cast(MON.Fecha as date) = cast(F.Fecha as date)     "
					+ " \n where f.DeSistema = 1 AND "
					+ f.obtenerEmpresasProquifa("F.Cliente")
					+ "  AND year ( f.fecha) =  year(GETDATE()) AND f.Estado = 'Por Cobrar'  "
					+ " \n group by  f.Cliente 	 "
					+ " \n ) as debe on debe.Cliente =  cli.clave  "
					+

					// JOIN PARA OBTENER LO FACTURADO DEL PERIODO ANTERIOR
					" \n LEFT JOIN ( SELECT CAST((SUM("
					+ f.sqlConversionMonedasADolar("F2.Moneda", "PF.Cant",
							"PF.Importe", "MON", "F2.TCambio", "", false)
					+ ")"
					+ " \n          - SUM(  CASE WHEN PNC.FK02_PFactura IS NOT NULL THEN "
					+ " \n          "
					+ f.sqlConversionMonedasADolar("NC.Moneda", "PF.Cant",
							"PF.Importe", "MON2", "NC.TCambio", "", false)
					+ " ELSE 0 END ) )  AS DECIMAL(9,2))  AS MontoAnteiror, F2.Cliente idCliente, CLI.Vendedor ESAC "
					+ " \n        FROM Facturas AS F2  "
					+ " \n 		LEFT JOIN (SELECT * FROM Monedas) AS MON ON cast(MON.Fecha as date) = cast(F2.Fecha as date) "
					+ " \n 		LEFT JOIN (SELECT * FROM Remisiones) RE ON CAST(RE.Nota AS VARCHAR(100)) = F2.Factura AND F2.DeRemision = 1 "
					+ " \n 		LEFT JOIN (SELECT * FROM PRemisiones) PRE ON PRE.Factura = RE.Factura AND PRE.FPor = RE.FPor AND PRE.CPedido = RE.CPedido "
					+ " \n 		LEFT JOIN (SELECT Importe, Cant, PPedido, Factura, FPor, CPedido, idPFactura FROM PFacturas) AS PF ON PF.Factura = F2.Factura AND PF.FPor = F2.FPor "
					+ " \n 			AND CASE WHEN F2.DeRemision = 1 THEN PRE.CPedido ELSE F2.CPedido END = PF.CPedido "
					+ " \n			AND CASE WHEN F2.DeRemision = 1 THEN PRE.PPedido ELSE PF.PPedido END = PF.PPedido "
					+ " \n 		LEFT JOIN (SELECT * FROM PPedidos) PP ON PP.CPedido = PF.CPedido AND PP.Part = PF.PPedido "
					+ " \n 		LEFT JOIN (SELECT * FROM PNotaCredito) PNC ON PNC.FK02_PFactura = PF.idPFactura  "
					+ " \n		LEFT JOIN (SELECT * FROM NotaCredito) NC ON NC.PK_Nota = PNC.FK01_Nota  "
					+ " \n 		LEFT JOIN (SELECT * FROM Monedas) AS MON2 ON cast(MON2.Fecha as date) = cast(NC.Fecha as date) "
					+ " \n 		LEFT JOIN (SELECT * FROM Clientes) AS CLI ON CLI.Clave = F2.Cliente   "
					+ " \n 		WHERE F2.DeSistema=1 AND  F2.Importe > 0 AND F2.Fecha > '20091231 23:59' AND F2.Estado<>'Cancelada'"
					+ " \n 		AND "
					+ f.obtenerEmpresasProquifa("F2.Cliente")
					+ " AND year(F2.Fecha) >= year ((getdate())-1)"
					+ " \n 		GROUP BY F2.Cliente, CLI.Vendedor ) FPeriodoAnt ON FPeriodoAnt.idCliente = CLI.Clave AND FPeriodoAnt.ESAC = CLI.Vendedor "
					+
					// FIN Periodo Anterior

					" \n  where cli.Habilitado =  1  and cart.PK_Cartera is not null "
					+ " \n   AND "
					+ f.obtenerEmpresasProquifa("cli.clave")
					+ " "
					+ parametros
					+ condicion
					+ " \n  group by cli.Clave, cli.Nombre ,cli.Industria,ees.esac,ees.idesac,eev.ev,eev.idev, eevt.idevt, eevt.evt, eco.cobrador,eco.idcobrador,Cart.Area, Cart.Justificacion ,hv.MontoVenta, Cart.Publicada,cart.sistema,cart.corporativo,cart.internacional,  cli.FechaRegistro , cli.rol,FPeriodoAnt.MontoAnteiror,cart.folio,    "
					+ " \n    cart.nombre , cart.pk_cartera, FK02_esac , fk03_Cobrador , Fk04_usuario , cart.fua, Cart.fk01_EV, cart.FK06_EVT, msj.mensajero, cart.FK07_mensajero, ces.esac , cev.ev, cevt.evt, cco.cobrador  , ccr.creador, vc.idindustria, cli.ruta, corp.idCorporativo , corp.nombrecorporativo, DEBE , DEBEMOS , cli.imagen"
					+ " \n  )as pre  left join(select * from ObjetivoCrecimiento_NI)as ob on ob.NivelIngreso = pre.nivel     "
					+ " \n  order by Nombre   ";
			log.info(query);

			return super.jdbcTemplate.query(query, new CarterasRowMapper());
		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}

	@Override
	public Long getidCorporativoporidCartera(Long idCartera)
			throws ProquifaNetException {
		try {
			String query = " \n select top 1 Corporativo from (   "
					+ " \n select top 1 FK02_Corporativo corporativo  from Clientes as c , Cliente_Cartera as cc , Cartera as cart  where cart.PK_Cartera =  cc.FK02_Cartera and cc.FK01_Cliente = c.Clave and cart.Corporativo = 1 and cart.PK_Cartera = "
					+ idCartera.toString()
					+ " \n union select 0 Corporativo   "
					+ " \n )as d order by Corporativo desc  ";

			//logger.info("Query busqueda de corporativo por cartera ------>: "
			//					+ query);
			return super.queryForLong(query);

		} catch (Exception e) {
			log.info(e.getMessage());
			return -1L;// TODO: handle exception
		}
	}


	@Override
	public boolean updateCarteraPublicada(Cartera cartera,
			String actualizaPendientesEsac, String actualizaPendientesEVT, String actualizaPendientesCobrador,
			String clientes, long idUsuario) throws ProquifaNetException {		
		/*
		 * Notas de este query. los unicos datos que requiere para funcionar son
		 * un arreglo con los id de los clientes que contempla la cartera ,
		 * idcartera , idesac , idcobracor y id del ev se autogenera el folio y
		 * se actualiza el consecutivo para el concepto 'Cartera' dicho folio se
		 * forma de la siguiente manera (las primeras 2 letras del usuario que
		 * genero la cartera , el identificador de la funcion que realiza ,
		 * fecha con el formato ( dia, mes ,ano), la sumatoria del total de sus
		 * clientes a dos digitos ,consecutivo a dos digitos en caso que el
		 * folio de la tabla consecutivo sea 99 se actualiza a cero ) si la
		 * variable de al cambiar el esac se actualiza el nombre contando el
		 * numero de carteras publicadas que tiene el esac asignadas o en caso
		 * de ser cartera de corporativo solo cambia el nombre
		 */
		try {
			//logger.info("modifica cartera publicada");
			//logger.info("se modifico esac " + cartera.isCart_updateESAC());
			//logger.info("idcorporativo " + cartera.getIdCorporativo());			
			String infoCliAnt = "";
			for (CarteraCliente cliente : cartera.getClientes()) {
				if (infoCliAnt.equals("")) {
					infoCliAnt = "idCliente= " + cliente.getIdCliente() + " ( ";
				} else {
					infoCliAnt += ",idCliente= " + cliente.getIdCliente()
					+ " ( ";
				}

				if(cartera.getArea().equals("Direccion")){
					infoCliAnt += " idEv= " + cliente.getCli_idEv();
					infoCliAnt += ", idcobrador= " + cliente.getCli_idCobrador();	
					infoCliAnt += ", idEsac= " + cliente.getCli_idEsac();
					infoCliAnt += ", idEVT= " + cliente.getCli_idEVT();
				}

				else if(cartera.getArea().equals("Ventas")){
					infoCliAnt += " idEv= " + cliente.getCli_idEv();					
				}
				else if(cartera.getArea().equals("Finanzas")){ 
					infoCliAnt += ", idcobrador= " + cliente.getCli_idCobrador();	
				}
				else if(cartera.getArea().equals("ESAC")){
					infoCliAnt += ", idEsac= " + cliente.getCli_idEsac();
					infoCliAnt += ", idEVT= " + cliente.getCli_idEVT();
				}

				infoCliAnt += ")";
			}

			String nombreCartera = "'"+cartera.getNombre()+"'";				

			String query = "  \n DECLARE @idClientes table (Id int) insert into @idClientes values "
					+ clientes
					+ "  \n DECLARE @idcartera as int  =   "
					+ cartera.getIdcartera();

			query += "  \n DECLARE @idEsac as int  = "
					+ cartera.getEsac();					
			query += "  \n DECLARE @idCobrador as int  =  "
					+ cartera.getCobrador();					
			query +=  "  \n DECLARE @idEV as int =  "
					+ cartera.getEv();					
			query +=  "  \n DECLARE @idEVT as int = "+ cartera.getEvt();
			query +=  " \n DECLARE @idMensajero as int = " + cartera.getMensajero();
			query +=  "  \n DECLARE @idCorporativo as int = "
					+ cartera.getIdCorporativo()
					+ "  \n DECLARE @idEsacAnt as int = (select FK02_Esac from Cartera where PK_Cartera =  @idcartera) "
					+ "  \n DECLARE @idEvtAnt as int = (select FK06_EVT from Cartera where PK_Cartera =  @idcartera) "
					+ "  \n DECLARE @idCobradorAnt as int  = (select FK03_Cobrador from Cartera where PK_Cartera =  @idcartera) "
					+ "  \n DECLARE @idEVAnt as int  = (select FK01_EV  from Cartera where PK_Cartera =  @idcartera) "					
					+ "  \n DECLARE @nombreEsac as  varchar (20)  SELECT @nombreEsac = (select Usuario  from Empleados where empleados.Clave = @idEsac)  "
					+ "  \n DECLARE @nombreCobrador as  varchar (20)  SELECT @nombreCobrador = (select Usuario  from  Empleados where empleados.Clave = @idCobrador)  "
					+ "  \n DECLARE @nombreEVT as  varchar (20)  SELECT @nombreEVT = (select Usuario  from  Empleados where empleados.Clave = @idEVT)  "
					+ "  \n DECLARE @NombreCartera as varchar (100) = "
					+ nombreCartera
					+ "  \n DECLARE @folioCartera as  varchar (20)  = (select UPPER ( SUBSTRING (empleados.Usuario, 1 , 2)) + Funcion.Identificador COLLATE SQL_Latin1_General_CP1_CI_AS  + replace( (CONVERT (varchar, GETDATE() , 3)),'/','') + noclientes "
					+ "  \n 	+ (select case when valor < 10  then '0'+ CONVERT (varchar,valor) else CONVERT (varchar,valor)   end valor   from Consecutivos where Concepto = 'cartera') folio  from Empleados , Funcion "
					+ "  \n 		left join (select distinct FK02_Cartera cartera,FK04_Usuario usuario ,  case when (count(FK01_Cliente))< 10 then '0'+ convert(varchar, count(FK01_Cliente)) else  convert(varchar,count(FK01_Cliente)) end  noclientes  "
					+ "  \n 	from Cliente_Cartera , cartera where cartera.PK_Cartera = Cliente_Cartera.FK02_Cartera  group by FK02_Cartera ,FK04_Usuario ) as cli on cli.cartera =  @idcartera where cli.usuario = empleados.Clave and funcion.PK_Funcion = empleados.FK01_Funcion)  "
					+ "  \n   IF @folioCartera  is null  BEGIN "
					+ "  \n 	SELECT @folioCartera = (select   UPPER ('PNET' +replace( (CONVERT (varchar, GETDATE() , 3)),'/','') +( case when (count(FK01_Cliente))< 10 then '0'+ convert(varchar, count(FK01_Cliente)) else  convert(varchar,count(FK01_Cliente)) end ) "
					+ "  \n 	+(select case when valor < 10  then '0'+ CONVERT (varchar,valor) else CONVERT (varchar,valor)   end valor   from Consecutivos where Concepto = 'cartera')) as valor  "
					+ "  \n 	from Cliente_Cartera , cartera where cartera.PK_Cartera = Cliente_Cartera.FK02_Cartera and  PK_Cartera =  @idcartera group by FK02_Cartera ,FK04_Usuario ) "
					+ "  \n   end  "
					+ "  \n DECLARE @modificacion as varchar (max)=  (select   "
					+ "  \n 'Se modifico cartera  = ' + convert(varchar , PK_Cartera, 10 ) + ' '   "
					+ "  \n + case when  @idEsac <> @idEsacAnt OR @NombreCartera <> Nombre  then + ' ,info anterior ( nombre = ' + Nombre else ' ' end  "
					+ "  \n + case when @idEsac <> @idEsacAnt  then  + ', esac  = ' + convert(varchar , coalesce ( fk02_esac, 0) , 10 ) else ' '  end  "
					+ "  \n + case when @idEV <> @idEVAnt  then ', ev  = '+ convert(varchar , coalesce ( FK01_EV , 0), 10 )else ' '  "
					+ "  \n + case when @idCobrador <> @idCobradorAnt  then', cobrador  = ' + convert(varchar , coalesce ( FK03_Cobrador, 0) , 10 )else ' '  end  "
					+ "  \n + ', usuario = '+ convert(varchar , coalesce ( FK04_Usuario, 0), 10 )+ ', industria = '+ convert(varchar , coalesce (FK05_Industria, 0), 10 ) "
					+ "  \n + ', folio = ' + rtrim (folio)  +', sistema = ' + convert(varchar ,  coalesce (Sistema, 0), 2 )   end  "
					+ "  \n + ') info actual ( ' "
					+ "  \n + case when  @idEsac <> @idEsacAnt OR @NombreCartera <> Nombre  then ' nombre =' +  @NombreCartera + ', esac ' +  CONVERT (varchar,@idEsac) else ' '  end  "
					+ "  \n + case when @idEV <> @idEVAnt  then', ev = ' + CONVERT (varchar,@idEV)  else ' '  end  "
					+ "  \n + case when @idCobrador <> @idCobradorAnt then + ', cobrador = ' + CONVERT (varchar,@idcobrador)  else ' '  end  "
					+ "  \n + ', folio =' +  @folioCartera + ')'  from cartera where pk_cartera = @idcartera )   "
					+

					"  \n if (select Valor  from Consecutivos where Concepto = 'Cartera') = 99 begin  "
					+ "  \n update Consecutivos set Valor = 0  where Concepto = 'Cartera' "
					+ "  \n end else  "
					+ "  \n update Consecutivos set Valor = (select Valor + 1 from Consecutivos where Concepto = 'Cartera') where Concepto = 'Cartera' "
					+ "  \n insert into Modificacion (fecha ,ventanaOrigen, Modificacion , fk01_empleado , docto   ) values (getdate(),'ProquifaNet -Servicio: catalogoClientesService, Metodo: ActualizarCartera, DAO: UpdateCarteraPublicada' , @modificacion +'. lista de clientes y responsables afectados:  "
					+ infoCliAnt
					+ "', '"
					+ idUsuario
					+ "' , @idCartera ) ";


			if(cartera.getArea().equals("Direccion")){

				query += "  \n update DoctosR set RPor =  @NombreEsac where Folio  in (select DoctosR.folio  from DoctosR , Clientes , Cliente_Cartera , Cartera where Cliente_Cartera.FK02_Cartera = cartera.PK_Cartera and  "
						+ "  \n clientes.Clave = Cliente_Cartera.FK01_Cliente and doctosr.RPor = Clientes.Vendedor and  "
						+ "  \n FProceso IS NULL AND Docto='Requisición' AND DoctosR.Estado IS NULL AND Fecha>'01/01/2008 00:00' "
						+ "  \n AND Clientes.Clave = DoctosR.Empresa  and cartera.PK_Cartera =  @idCartera ) "
						+

								"  \n update SegCotiza set Vendedor = @nombreEVT where Folio in ( "
								+ "  \n select SegCotiza.Folio  from SegCotiza  , Cotizas , Clientes , Cliente_Cartera , Cartera where Cliente_Cartera.FK02_Cartera = cartera.PK_Cartera and  "
								+ "  \n     clientes.Clave = Cliente_Cartera.FK01_Cliente and cotizas.FK01_idCliente = clientes.clave and SegCotiza.Cotiza = cotizas.Clave and SegCotiza.Vendedor = Clientes.Vendedor and  "
								+ "  \n    FRealizo  IS NULL and cartera.PK_Cartera =  @idCartera )  " 

	                            + " \n UPDATE Cartera set nombre =  @NombreCartera , fua = getdate() , PUBLICADA= 1,FK02_ESAC= @idEsac,FK03_COBRADOR= @idCobrador, FK01_EV= @idEV, FK06_EVT = @idEVT, FK07_mensajero = @idMensajero, folio =  @folioCartera WHERE PK_CARTERA = @idcartera   "
	                            + "  \n DELETE FROM  Cliente_Cartera WHERE PK_Cliente_Cartera IN  (SELECT Cliente_Cartera.FK01_Cliente  FROM cartera, Cliente_Cartera "
	                            + "  \n WHERE Cliente_Cartera.FK02_Cartera = Cartera.PK_Cartera AND Cartera.PK_Cartera = @IDCARTERA ) AND  FK02_Cartera <> @IDCARTERA   "
	                            + "  \n UPDATE Clientes set Vendedor = @nombreEsac, Cobrador = @idCobrador, FK01_EV = @idEv  where Clave in (select Id from @idClientes)  ";


			}
			else if(cartera.getArea().equals("ESAC")){

				query += "  \n update DoctosR set RPor =  @NombreEsac where Folio  in (select DoctosR.folio  from DoctosR , Clientes , Cliente_Cartera , Cartera where Cliente_Cartera.FK02_Cartera = cartera.PK_Cartera and  "
						+ "  \n clientes.Clave = Cliente_Cartera.FK01_Cliente and doctosr.RPor = Clientes.Vendedor and  "
						+ "  \n FProceso IS NULL AND Docto='Requisición' AND DoctosR.Estado IS NULL AND Fecha>'01/01/2008 00:00' "
						+ "  \n AND Clientes.Clave = DoctosR.Empresa  and cartera.PK_Cartera =  @idCartera ) "
						+

								"  \n update SegCotiza set Vendedor = @nombreEVT where Folio in ( "
								+ "  \n select SegCotiza.Folio  from SegCotiza  , Cotizas , Clientes , Cliente_Cartera , Cartera where Cliente_Cartera.FK02_Cartera = cartera.PK_Cartera and  "
								+ "  \n     clientes.Clave = Cliente_Cartera.FK01_Cliente and cotizas.FK01_idCliente = clientes.clave and SegCotiza.Cotiza = cotizas.Clave and SegCotiza.Vendedor = Clientes.Vendedor and  "
								+ "  \n    FRealizo  IS NULL and cartera.PK_Cartera =  @idCartera )  " 

	                            + " \n UPDATE Cartera set nombre =  @NombreCartera , fua = getdate() , PUBLICADA= 1,FK02_ESAC= @idEsac, FK06_EVT = @idEVT, folio =  @folioCartera WHERE PK_CARTERA = @idcartera   "
	                            + "  \n DELETE FROM  Cliente_Cartera WHERE PK_Cliente_Cartera IN  (SELECT Cliente_Cartera.FK01_Cliente  FROM cartera, Cliente_Cartera "
	                            + "  \n WHERE Cliente_Cartera.FK02_Cartera = Cartera.PK_Cartera AND Cartera.PK_Cartera = @IDCARTERA ) AND  FK02_Cartera <> @IDCARTERA   "
	                            + "  \n UPDATE Clientes set Vendedor = @nombreEsac, FK03_EVT = @idEVT where Clave in (select Id from @idClientes)  ";

			}

			if(cartera.getArea().equals("Finanzas")){

				query += " \n UPDATE Cartera set nombre =  @NombreCartera , fua = getdate() , PUBLICADA= 1, FK03_COBRADOR= @idCobrador , folio =  @folioCartera WHERE PK_CARTERA = @idcartera   "
						+ "  \n DELETE FROM  Cliente_Cartera WHERE PK_Cliente_Cartera IN  (SELECT Cliente_Cartera.FK01_Cliente  FROM cartera, Cliente_Cartera "
						+ "  \n WHERE Cliente_Cartera.FK02_Cartera = Cartera.PK_Cartera AND Cartera.PK_Cartera = @IDCARTERA ) AND  FK02_Cartera <> @IDCARTERA   "
						+ "  \n UPDATE Clientes set Cobrador = @idCobrador  where Clave in (select Id from @idClientes)  ";
			}

			if(cartera.getArea().equals("Ventas")){
				query += " \n UPDATE Cartera set nombre =  @NombreCartera , fua = getdate() , PUBLICADA= 1, FK01_EV= @idEV , folio =  @folioCartera WHERE PK_CARTERA = @idcartera   "
						+ "  \n DELETE FROM  Cliente_Cartera WHERE PK_Cliente_Cartera IN  (SELECT Cliente_Cartera.FK01_Cliente  FROM cartera, Cliente_Cartera "
						+ "  \n WHERE Cliente_Cartera.FK02_Cartera = Cartera.PK_Cartera AND Cartera.PK_Cartera = @IDCARTERA ) AND  FK02_Cartera <> @IDCARTERA   "
						+ "  \n UPDATE Clientes set FK01_EV = @idEv  where Clave in (select Id from @idClientes)  ";
			}



			if (!actualizaPendientesEsac.equals("")) {
				if(cartera.getEsac() > 0){
					query += "  \n UPDATE Pendiente SET Responsable = @nombreEsac WHERE Folio in ( "
							+ actualizaPendientesEsac + " ) ";
					query += "  \n UPDATE Pendiente SET Responsable = @nombreEVT WHERE Folio in ( "
							+ actualizaPendientesEVT + " ) ";
				}

			}
			if (!actualizaPendientesCobrador.equals("")) {
				if(cartera.getCobrador() > 0)
					query += "  \n UPDATE Pendiente SET Responsable = @nombreCobrador WHERE Folio in ( "
							+ actualizaPendientesCobrador + " ) ";
			}
			//
			//			query += "  \n delete from Cliente_Cartera where fK01_cliente in (select Cliente_Cartera.FK01_Cliente   from cartera, Cliente_Cartera where Cliente_Cartera.FK02_Cartera = Cartera.PK_Cartera and "
			//					+ "  \n Cartera.PK_Cartera = @IDCARTERA) and FK02_Cartera <> @IDCARTERA  "
			//					+ "  \n if (select   count (* ) cartsincli from (select PK_Cartera  from Cartera  "
			//					+ "  \n left join (select * from Cliente_Cartera ) as cc on cc.FK02_Cartera = cartera.PK_Cartera where cc.PK_Cliente_Cartera is null ) as f ) > 0  "
			//					+ "  \n begin  "
			//					+ "  \n 		delete from Cartera where PK_Cartera in (select PK_Cartera  from Cartera  "
			//					+ "  \n 		left join (select * from Cliente_Cartera ) as cc on cc.FK02_Cartera = cartera.PK_Cartera where cc.PK_Cliente_Cartera is null )  "
			//					+ "  \n 	end  ";
			log.info(query);
			super.update(query);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean publicaCartera(long idCartera,
			String actualizaPendientesEsac, String actualizaPendientesEVT, String actualizaPendientesCobrador,
			String clientes, Cartera cartera, Long idUsuario)
					throws ProquifaNetException {
		try {
			/*
			 * Notas de este query. los unicos datos que requiere para funcionar
			 * son un arreglo con los id de los clientes que contempla la
			 * cartera , idcartera los demas datos son autoinsertados de base de
			 * datos se autogenera el folio y se actualiza el consecutivo para
			 * el concepto 'Cartera' dicho folio se forma de la siguiente manera
			 * (las primeras 2 letras del usuario que genero la cartera , el
			 * identificador de la funcion que realiza , fecha con el formato (
			 * dia, mes ,ano), la sumatoria del total de sus clientes a dos
			 * digitos ,consecutivo a dos digitos en caso que el folio de la
			 * tabla consecutivo sea 99 se actualiza a cero ) el nombre de la
			 * cartera cambia al publicar ya que se asigna un sector consecutivo
			 * y este solo cuenta las publicadas El objeto cartera contiene la
			 * informacion del esac ev y cobrador actuales por tal se buscan los
			 * responsables que han cambiado ya que solo se actualizan
			 * pendientes cuando el cambio se hace desde carteras se toma como
			 * validador ya que desde corporativo se agrega registro de
			 * modificacion.
			 */

			String nombreCartera = "";

			nombreCartera = "'"+cartera.getNombre()+"'";

			String infoCliAnt = "";
			if (!actualizaPendientesEsac.equals("")
					&& !actualizaPendientesCobrador.equals("")) {
				infoCliAnt = "Se Publica la cartera no "
						+ cartera.getIdcartera() + "con los responsables: ";
				infoCliAnt += "idEv= " + Long.toString(cartera.getEv());
				infoCliAnt += "idEVT= " + Long.toString(cartera.getEvt());
				infoCliAnt += ",idcobrador= "
						+ Long.toString(cartera.getCobrador());
				infoCliAnt += ",idEsac= " + Long.toString(cartera.getEsac());
				infoCliAnt += ". lista de clientes y responsables afectados:  ";

				for (CarteraCliente cliente : cartera.getClientes()) {
					infoCliAnt += ",idCliente= " + cliente.getIdCliente()
					+ " ( ";
					if (cliente.getCli_idEv() != cartera.getEv()) {
						infoCliAnt += "idEv= " + cliente.getCli_idEv();
					}
					if (cliente.getCli_idEVT() != cartera.getEvt()) {
						infoCliAnt += "idEVT= " + cliente.getCli_idEVT();
					}
					if (cliente.getCli_idCobrador() != cartera.getCobrador()) {
						infoCliAnt += "idcobrador= "
								+ cliente.getCli_idCobrador();
					}
					if (cliente.getCli_idEsac() != cartera.getEsac()) {
						infoCliAnt += "idEsac= " + cliente.getCli_idEsac();
					}

					infoCliAnt += ")";
				}
			}

			//logger.info("publica cartera ");
			Map<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("justificacion", cartera.getJustificacion());
			String query = "  \n DECLARE @idClientes table (Id int) insert into @idClientes values "   
					+ clientes
					+ "  \n DECLARE @idcartera as int  =  "
					+ idCartera
					+ "  \n DECLARE @idEsac as int  = (select FK02_Esac from Cartera where PK_Cartera =  @idcartera) "
					+ "  \n DECLARE @idCobrador as int  = (select FK03_Cobrador from Cartera where PK_Cartera =  @idcartera) "
					+ "  \n DECLARE @idEV as int = (select FK01_EV from Cartera where PK_Cartera =  @idcartera) "
					+ "  \n DECLARE @idEVT as int = (select FK06_EVT FROM Cartera WHERE PK_Cartera =  @idcartera) "
					+ "  \n DECLARE @nombreEsac as  varchar (20)  = (select Usuario  from Cartera, Empleados where empleados.Clave = cartera.FK02_Esac and PK_Cartera = @idcartera) "
					+ "  \n DECLARE @nombreEVT as  varchar (20)  = (select Usuario  from Cartera, Empleados where empleados.Clave = cartera.FK06_EVT and PK_Cartera = @idcartera) "
					+ "  \n DECLARE @nombreCobrador as  varchar (20)  = (select Usuario  from Cartera, Empleados where empleados.Clave = cartera.FK03_Cobrador  and PK_Cartera =  @idcartera) "
					+ "  \n DECLARE @NombreCartera as varchar (100) = "
					+ nombreCartera
					+ "  \n DECLARE @folioCartera as  varchar (20)  = (select UPPER ( SUBSTRING (empleados.Usuario, 1 , 2)) + Funcion.Identificador COLLATE SQL_Latin1_General_CP1_CI_AS  + replace( (CONVERT (varchar, GETDATE() , 3)),'/','') + noclientes "
					+ "  \n 	+ (select case when valor < 10  then '0'+ CONVERT (varchar,valor) else CONVERT (varchar,valor)   end valor   from Consecutivos where Concepto = 'cartera') folio  from Empleados , Funcion "
					+ "  \n 		left join (select distinct FK02_Cartera cartera,FK04_Usuario usuario ,  case when (count(FK01_Cliente))< 10 then '0'+ convert(varchar, count(FK01_Cliente)) else  convert(varchar,count(FK01_Cliente)) end  noclientes  "
					+ "  \n 	from Cliente_Cartera , cartera where cartera.PK_Cartera = Cliente_Cartera.FK02_Cartera  group by FK02_Cartera ,FK04_Usuario ) as cli on cli.cartera =  @idcartera where cli.usuario = empleados.Clave and funcion.PK_Funcion = empleados.FK01_Funcion)  "
					+ "  \n   IF @folioCartera  is null  BEGIN "
					+ "  \n 	SELECT @folioCartera = (select   UPPER ('PNET' +replace( (CONVERT (varchar, GETDATE() , 3)),'/','') +( case when (count(FK01_Cliente))< 10 then '0'+ convert(varchar, count(FK01_Cliente)) else  convert(varchar,count(FK01_Cliente)) end ) "
					+ "  \n 	+(select case when valor < 10  then '0'+ CONVERT (varchar,valor) else CONVERT (varchar,valor)   end valor   from Consecutivos where Concepto = 'cartera')) as valor  "
					+ "  \n 	from Cliente_Cartera , cartera where cartera.PK_Cartera = Cliente_Cartera.FK02_Cartera and  PK_Cartera =  @idcartera group by FK02_Cartera ,FK04_Usuario ) "
					+ "  \n   end  ";
			if (!infoCliAnt.equals("")) {
				query += "  \n insert into Modificacion (fecha ,ventanaOrigen, Modificacion , FK01_Empleado , docto   ) values (getdate(),'ProquifaNet -Servicio: catalogoClientesService, Metodo: ActualizarCartera, DAO: PublicarCartera' , '"
						+ infoCliAnt
						+ "', '"
						+ cartera.getUsuario()
						+ "' , @idCartera ) ";
			}
			query += "  \n if (select Valor  from Consecutivos where Concepto = 'Cartera') = 99 begin  "
					+ "  \n update Consecutivos set Valor = 0  where Concepto = 'Cartera' "
					+ "  \n end else  "
					+ "  \n update Consecutivos set Valor = (select Valor + 1 from Consecutivos where Concepto = 'Cartera') where Concepto = 'Cartera' ";

			if(cartera.getArea().equals("Direccion")){
				query +="  \n update DoctosR set RPor =  @nombreEsac where Folio  in (select cartera.PK_Cartera  from DoctosR , Clientes , Cliente_Cartera , Cartera where Cliente_Cartera.FK02_Cartera = cartera.PK_Cartera and  "
						+ "  \n clientes.Clave = Cliente_Cartera.FK01_Cliente and doctosr.RPor = Clientes.Vendedor and  "
						+ "  \n FProceso IS NULL AND Docto='Requisición' AND DoctosR.Estado IS NULL AND Fecha>'01/01/2008 00:00' "
						+ "  \n AND Clientes.Clave = DoctosR.Empresa  and cartera.PK_Cartera =  @idCartera )  "
						+

	      						"  \n update SegCotiza set Vendedor = @nombreEVT where Folio in ( "
	      						+ "  \n select SegCotiza.Folio  from SegCotiza  , Cotizas , Clientes , Cliente_Cartera , Cartera where Cliente_Cartera.FK02_Cartera = cartera.PK_Cartera and  "
	      						+ "  \n     clientes.Clave = Cliente_Cartera.FK01_Cliente and cotizas.FK01_idCliente = clientes.clave and SegCotiza.Cotiza = cotizas.Clave and SegCotiza.Vendedor = Clientes.Vendedor and  "
	      						+ "  \n    FRealizo  IS NULL and cartera.PK_Cartera =  @idCartera ) "
	      						+

	      						"  \n UPDATE Cartera set nombre =  @NombreCartera , FPUBLICACION= getdate() , fua = getdate() , PUBLICADA= 1, Justificacion = :justificacion , folio =  @folioCartera WHERE PK_CARTERA = @idcartera  "
	      						+" \n UPDATE Clientes set Vendedor = @nombreEsac, FK01_EV = @idEv, FK03_EVT = @idEVT, Cobrador = @idCobrador  where Clave in (select Id from @idClientes)  ";

			}



			if(cartera.getArea().equals("ESAC")){

				query +="  \n update DoctosR set RPor =  @nombreEsac where Folio  in (select cartera.PK_Cartera  from DoctosR , Clientes , Cliente_Cartera , Cartera where Cliente_Cartera.FK02_Cartera = cartera.PK_Cartera and  "
						+ "  \n clientes.Clave = Cliente_Cartera.FK01_Cliente and doctosr.RPor = Clientes.Vendedor and  "
						+ "  \n FProceso IS NULL AND Docto='Requisición' AND DoctosR.Estado IS NULL AND Fecha>'01/01/2008 00:00' "
						+ "  \n AND Clientes.Clave = DoctosR.Empresa  and cartera.PK_Cartera =  @idCartera )  "
						+

      						"  \n update SegCotiza set Vendedor = @nombreEVT where Folio in ( "
      						+ "  \n select SegCotiza.Folio  from SegCotiza  , Cotizas , Clientes , Cliente_Cartera , Cartera where Cliente_Cartera.FK02_Cartera = cartera.PK_Cartera and  "
      						+ "  \n     clientes.Clave = Cliente_Cartera.FK01_Cliente and cotizas.FK01_idCliente = clientes.clave and SegCotiza.Cotiza = cotizas.Clave and SegCotiza.Vendedor = Clientes.Vendedor and  "
      						+ "  \n    FRealizo  IS NULL and cartera.PK_Cartera =  @idCartera ) "
      						+

      						"  \n UPDATE Cartera set nombre =  @NombreCartera , FPUBLICACION= getdate() , fua = getdate() , PUBLICADA= 1,Justificacion = :justificacion , folio =  @folioCartera WHERE PK_CARTERA = @idcartera  "
      						+ "  \n UPDATE Clientes set Vendedor = @nombreEsac, FK03_EVT = @idEVT  WHERE Clave in (select Id from @idClientes)  ";


			}

			if(cartera.getArea().equals("Ventas")){
				query += "  \n UPDATE Cartera set nombre =  @NombreCartera , FPUBLICACION= getdate() , fua = getdate() , PUBLICADA= 1,Justificacion = :justificacion , folio =  @folioCartera WHERE PK_CARTERA = @idcartera  "
						+ "  \n UPDATE Clientes set FK01_EV = @idEv where Clave in (select Id from @idClientes)  ";
			}

			if(cartera.getArea().equals("Finanzas")){

				query +=  "  \n UPDATE Cartera set nombre =  @NombreCartera , FPUBLICACION= getdate() , fua = getdate() , PUBLICADA= 1, Justificacion = :justificacion , folio =  @folioCartera WHERE PK_CARTERA = @idcartera  "
						+ "  \n UPDATE Clientes set Cobrador = @idCobrador  where Clave in (select Id from @idClientes)  ";

			}

			if (!actualizaPendientesEsac.equals("")) {
				query += "  \n UPDATE Pendiente SET Responsable = @nombreEsac WHERE Folio in ( "
						+ actualizaPendientesEsac + " ) ";
			}
			if (!actualizaPendientesEVT.equals("")) {
				query += "  \n UPDATE Pendiente SET Responsable = @nombreEVT WHERE Folio in ( "
						+ actualizaPendientesEVT + " ) ";
			}
			if (!actualizaPendientesCobrador.equals("")) {
				query += "  \n UPDATE Pendiente SET Responsable = @nombreCobrador WHERE Folio in ( "
						+ actualizaPendientesCobrador + " ) ";
			}

			//			query += "  \n delete from Cliente_Cartera where FK01_Cliente in (select Cliente_Cartera.FK01_Cliente  from cartera, Cliente_Cartera where Cliente_Cartera.FK02_Cartera = Cartera.PK_Cartera "
			//					+ "  \n  and Cartera.PK_Cartera = @IDCARTERA) and FK02_Cartera <> @IDCARTERA "
			//					+ " \n IF (select   count (* ) cartsincli from (select PK_Cartera  from Cartera  "
			//					+ "  \n left join (select * from Cliente_Cartera ) as cc on cc.FK02_Cartera = cartera.PK_Cartera where cc.PK_Cliente_Cartera is null ) as f ) > 0  "
			//					+ " \n begin  "
			//					+ "  \n 		delete from Cartera where PK_Cartera in (select PK_Cartera  from Cartera  "
			//					+ "  \n 		left join (select * from Cliente_Cartera ) as cc on cc.FK02_Cartera = cartera.PK_Cartera where cc.PK_Cliente_Cartera is null ) "
			//					+ " \n 	end ";
			log.info(query);
			super.update(query,mapa);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}

	@Override
	public Boolean updateCartera(Cartera cartera) throws ProquifaNetException {
		try {
			Long publicada = 0L;
			StringBuilder sbQuery = new StringBuilder(" ");
			String fpublicacion = "", nombreCartera = "";
			if (cartera.isPublicada()) {
				publicada = 1l;
			} else {
				publicada = 0l;
			}

			nombreCartera = "'" + cartera.getNombre() + "'";

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("idCartera", cartera.getIdcartera());
			params.put("ev", cartera.getEv());
			params.put("esac", cartera.getEsac());
			params.put("cobrador", cartera.getCobrador());
			params.put("usuario", cartera.getUsuario());
			params.put("mensajero", cartera.getMensajero());
			params.put("evt", cartera.getEvt());
			params.put("industria", cartera.getIndustria());
			params.put("publicada", publicada);	

			if (cartera.isPublicada()) {
				fpublicacion = ", FPUBLICACION= getdate() ";
			}
			sbQuery.append("\n declare @idcarteras as int select @idcarteras = :idCartera ");
			sbQuery.append("\n UPDATE Cartera set nombre= "
					+ nombreCartera
					+ " ,FK01_EV= :ev,FK02_ESAC= :esac, FK03_COBRADOR= :cobrador , FK04_USUARIO = :usuario, FK07_mensajero = :mensajero, FK06_EVT= :evt,fua = getdate() ,FK05_INDUSTRIA =  :industria  "
					+ fpublicacion
					+ " , PUBLICADA= :publicada WHERE PK_CARTERA = @idcarteras ");
			sbQuery.append("\n DELETE Cliente_Cartera where FK02_Cartera =  @idcarteras ");

			for (Cliente cliente : cartera.getClientes()) {
				if (cliente.getIdCliente() > 0) {
					sbQuery.append(" insert into Cliente_Cartera (FK01_Cliente, FK02_Cartera) values ("
							+ cliente.getIdCliente() + ", @idcarteras)");
				}
			}

			log.info(sbQuery.toString() + " parametros- ");
			super.jdbcTemplate.update(sbQuery.toString(), params);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Long insertCartera(Cartera cartera) throws ProquifaNetException {    
		try {
			// inserta un registro tipo cartera con la opcion de marcar como
			// publicado o solo borrador. en caso de ser publicar tiene la misma
			// fecha de creacion y publicacion.
			// se valida si la cartera trae un esac, ev, y cobrador
			String query = "", encabezado = "", cuerpo = "";
			if (cartera.getArea().equals("Direccion")) {//cartera creada por Direccion general / representante de direccion / gsistemas

				query = " \n DECLARE @NombreCartera AS varchar (99) = '"
						+ cartera.getNombre() + "'";

				encabezado += " ,FK02_ESAC,FK03_COBRADOR,FK01_EV, FK06_EVT, FK07_mensajero ";
				cuerpo = " @NombreCartera ," + cartera.getEsac() + "," + cartera.getCobrador() + "," + cartera.getEv() + "," + cartera.getEvt() + "," + cartera.getMensajero();
				cartera.setArea(null);
			}

			else if(cartera.getArea().equals("ESAC")){

				if (cartera.getEsac() != 0) {//cartera creada por EsacMaster
					query = " \n DECLARE @NombreCartera AS varchar (99) = '"
							+ cartera.getNombre() + "'";


					encabezado += " ,FK02_ESAC,FK03_COBRADOR,FK01_EV, FK06_EVT ";
					cuerpo = " @NombreCartera ," + cartera.getEsac() + ",null,null," + cartera.getEvt();
				}
			}

			else if(cartera.getArea().equals("Finanzas")){

				if(cartera.getCobrador() != 0) { //cartera creada por Contador
					query = " \n DECLARE @NombreCartera AS varchar (99) = '"
							+ cartera.getNombre() + "'";


					encabezado += " ,FK03_COBRADOR,FK01_EV,FK02_ESAC ";
					cuerpo = " @NombreCartera ," + cartera.getCobrador() + ",null,null ";
				}
			}

			else if(cartera.getArea().equals("Ventas")){
				if(cartera.getEv() != 0){ // cartera creada por EV
					query = " \n DECLARE @NombreCartera AS varchar (99) = '"
							+ cartera.getNombre() + "'";


					encabezado += " ,FK01_EV,FK02_ESAC,FK03_COBRADOR ";
					cuerpo = " @NombreCartera ," + cartera.getEv() + ",null,null ";
				}
			}

			else {
				query = " \n  DECLARE @NombreCartera AS varchar (99) "
						+ " \n  select @NombreCartera = (  SELECT 'SIN ASIGNACION · SECTORB'+ CONVERT (VARCHAR ,(  SELECT COUNT (1)+1 SinEsac from Cartera where sistema  = 0 and publicada  = 0 and (FK02_Esac =  0 or FK02_Esac is null )),4)) ";
				encabezado += " ,FK01_EV,FK02_ESAC ";
				cuerpo = " @NombreCartera ,null ";
			}


			if (cartera.isPublicada()) {
				encabezado += " , PUBLICADA  ";
				cuerpo += " ,1 ";
			} else {
				encabezado += " , PUBLICADA  ";
				cuerpo += " ,0 ";
			}

			Map<String, Object> param = new HashMap<String, Object>();
			param.put("area", cartera.getArea());
			if (cartera.isPublicada()) {
				// solo puede nacer una cartera publicada si es de sistema, ya
				// que es necesario guardar una cartera y posteriormente
				// publicarla. Al nacer un corporativo se crea la cartera con el
				// mismo nombre no lleva industria ni usuario
				query += " \n  DECLARE @idcarteras AS INT "
						+ " \n  insert into cartera (Nombre "
						+ encabezado
						+ " , FK05_industria,FCreacion  , fpublicacion ,sistema , corporativo, Area ) values ( "
						+ cuerpo + " ,NULL,getdate(),getdate(),1, 1, :area)  "
						+ " \n  SELECT @idcarteras =   Scope_Identity() ";

			} else {

				query += " \n  DECLARE @idcarteras AS INT "
						+ " \n  insert into cartera (Nombre "
						+ encabezado
						+ ", FK04_USUARIO , FK05_industria,FCreacion, Area ) values ( "
						+ cuerpo + " , " + cartera.getUsuario() + " , "
						+ cartera.getIndustria() + " , getdate(), :area) "
						+ " \n  SELECT @idcarteras =   Scope_Identity() ";

			}

			String query2 = "";
			for (CarteraCliente cliente : cartera.getClientes()) {
				if (cliente.getIdCliente() > 0) {
					query2 = query2
							+ " insert into Cliente_Cartera (FK01_Cliente, FK02_Cartera) values ("
							+ cliente.getIdCliente() + ", @idcarteras) ";
				}
			}
			query2 += " ";

			log.info(query);
			log.info("\n\n\n\n\n");
			log.info(query2);
			super.update(query + query2 , param);

			Long idcartera = super
					.queryForLong("SELECT IDENT_CURRENT ('Cartera')");
			//logger.info("idcartera nueva ---->" + idcartera.toString());
			return idcartera;
		} catch (RuntimeException e) {
			e.printStackTrace();
			log.info("Error: " + e.getMessage());
			return -1L;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> findClientesCartera(Long idCartera)
			throws ProquifaNetException {
		try {
			String query = " select Cliente_Cartera.fk01_cliente from Cliente_Cartera , Clientes where clientes.Clave =  Cliente_Cartera.FK01_Cliente and clientes.Habilitado = 1 and FK02_Cartera = "
					+ idCartera;
			//logger.info(query);
			return super.jdbcTemplate.query(query, new LongMapper());
		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}


	@Override
	public Boolean deleteCartera(Long idCarteras, Long idCorporativo,
			Long idUsuario, String listaClientes, boolean llamadoCartera)
					throws ProquifaNetException {
		try {
			String modificaciones = "";
			String query = "\n  DECLARE @idcarteras as int SELECT @idcarteras =  "
					+ idCarteras
					+ "\n   DECLARE @modificacion as varchar (max)=  (select   "
					+ "\n  'Eliminar cartera id = ' + convert(varchar , PK_Cartera, 10 )    "
					+ "\n  + case when nombre is not null   then + ' ( nombre = ' + Nombre else ' ' end  "
					+ "\n  + case when FK02_Esac IS not null  then  + ', esac  = ' + convert(varchar , coalesce ( fk02_esac, 0) , 10 )else ' '  end  "
					+ "\n  + case when FK01_EV IS not null   then ', ev  = '+ convert(varchar , coalesce ( FK01_EV , 0), 10 )else ' ' end "
					+ "\n  + case when FK03_Cobrador is not null then', cobrador  = ' + convert(varchar , coalesce ( FK03_Cobrador, 0) , 10 )else ' '  end  "
					+ "\n  + ', usuario = '+ convert(varchar , coalesce ( FK04_Usuario, 0), 10 )+ ', industria = '+ convert(varchar , coalesce (FK05_Industria, 0), 10 ) "
					+ "\n  + ', folio = ' + rtrim (folio)  +', sistema = ' + convert(varchar ,  coalesce (Sistema, 0), 2 )   +', publicada = ' + convert(varchar ,  coalesce (publicada, 0), 2 )    "
					+ "\n  + ') ' from cartera where pk_cartera = @idcarteras )    "
					+ "\n  DELETE Cliente_Cartera where FK02_Cartera =  @idcarteras  ";

			// si es una cartera de corporativo eliminar el corporativo y
			// actualizar clientes mandando a nulo el fk02_corporativo
			if (idCorporativo > 0 && llamadoCartera) {
				query += "\n  UPDATE clientes SET fk02_Corporativo =  null where fk02_Corporativo = "
						+ idCorporativo + "  ";
				query += "\n  delete from Industrias_Corporativo where FK02_Corporativo =  "
						+ idCorporativo + "  ";
				query += "\n  DELETE FROM CORPORATIVO WHERE PK_Corporativo = "
						+ idCorporativo + "  ";
				modificaciones += " correspondiente al corporativo no "
						+ idCorporativo + " ";
			}
			query += "\n  INSERT INTO MODIFICACION (fecha ,ventanaOrigen, Modificacion , fk01_empleado , docto   ) values (getdate(),'ProquifaNet -Servicio: catalogoClientesService, Metodo: EliminarCartera' ,@modificacion + ' "
					+ modificaciones
					+ " ,los clientes de la cartera son "
					+ listaClientes + "','" + idUsuario + "' , @idcarteras )";

			//logger.info(query);
			log.info(query);
			super.update(query);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CarteraCliente> findClientesSinCartera(String parametros,List<NivelIngreso> niveles, String area)
	// para poder mostrar carteras con los montos se cambian de modo dinamico y
	// se pone para revizar 2014
			throws ProquifaNetException {
		try {
			String query = "";
			f = new Funcion();
			String condicion = "";

			if(area != null)
			{
				condicion = "C.Area = '"+area+"'";
			}
			else{
				condicion = "C.Area is null";

			}
			query = " \n select distinct pre.idcliente , pre.Nombre ,pre.industria, pre.esac, pre.idesac , pre.idev , pre.ev , pre.cobrador ,coalesce (idcorporativo,0 ) as idcorporativo ,coalesce ( nombrecorporativo,'' ) as nombrecorporativo "
					+ " \n  ,pre.idcobrador , coalesce (pre.MontoVentaAnt , 0) montoVentaAnt , coalesce (pre.ConversionUSD, 0)  montoVenta   , pre.idindustria ,coalesce (pre.Ruta ,'ND' ) Ruta ,pre.nombrecorporativo , pre.idcorporativo "
					+ " \n  ,COALESCE(ob.porcentajeanual ,0) ObjetivoCrecimiento, COALESCE( ob.Fundamental ,0)ObjetivoCrecimientoFundamental, pre.imagen   "
					+ " \n  ,case when NIVEL = 'ClientesNuevos' then 'NUEVOS' WHEN NIVEL = 'Distribuidor' then 'DISTRIBUIDOR' WHEN NIVEL = 'bajo' then 'BAJO' else NIVEL END NIVEL, categoria   "
					+ " \n  ,coalesce((MontoVentaAnt* ( 1.00 + (ob.porcentajeanual/100  ))),0) MONTO_D ,coalesce((MontoVentaAnt*( 1.00+ (OB.Fundamental/100))),0) MONTO_F  "
					+ " \n  from (  "
					+ " \n  select cli.Clave idcliente , cli.Nombre ,coalesce (cli.Industria, 'ND') industria  , coalesce (ees.esac,'ND') esac , coalesce (ees.idesac,0) idesac , coalesce (eev.ev,'ND') ev , coalesce (eev.idev,0) idev  , coalesce (eco.cobrador,'ND') cobrador , coalesce (eco.idcobrador,0) idcobrador   "
					+ " \n  ,coalesce (hv.montoventa ,0) MontoVentaAnt,idindustria,cli.ruta,CORP.idcorporativo , corp.nombrecorporativo , cli.imagen,  "
					+ " \n coalesce (CAST((SUM("
					+ f.sqlConversionMonedasADolar("F.Moneda", "PF.Cant",
							"PF.Importe", "MON", "F.TCambio", "", false)
					+ ")"
					+ " \n  - SUM(  CASE WHEN PNC.FK02_PFactura IS NOT NULL THEN "
					+ f.sqlConversionMonedasADolar("NC.Moneda", "PF.Cant",
							"PF.Importe", "MON2", "NC.TCambio", "", false)
					+ " ELSE 0 END ) )  AS DECIMAL(9,2)),0)  AS ConversionUSD, "
					+ " \n "
					+ f.sqlLimitesNivelIngresoCClientesNuevos(niveles,
							"HV.MontoVenta", "year(getdate())", false, true)
					+ " AS NIVEL, "
					+ " \n "
					+ f.sqlLimitesNivelIngresoCClientesNuevos(niveles,
							"HV.MontoVenta", "year(getdate())", true, true)
					+ " AS montoVenta \n"
					+ " , CASE WHEN CLI.Rol = 'DISTRIBUIDOR' THEN 'DISTRIBUIDOR' ELSE (CASE WHEN YEAR(FechaRegistro) = YEAR(GETDATE()) THEN 'NUEVOS' WHEN  COALESCE(HV.MontoVenta,0) >= 15000.01 THEN 'ALTOS' WHEN COALESCE(HV.MontoVenta,0) <= 15000.0 AND COALESCE(HV.MontoVenta,0) > 0.0 THEN 'MEDIOS' ELSE 'BAJO' END ) END categoria "
					+ " \n  from Clientes as cli  "
					+ " \n  left join (select Usuario  esac, clave idesac from Empleados ) as  Ees on ees.esac = cli.vendedor  "
					+ " \n  left join (select Usuario ev, clave idev from Empleados ) as  Eev on eev.idev  = cli.fk01_ev and cli.FK01_EV <> 0 "
					+ " \n  left join (select Usuario cobrador, clave idcobrador from Empleados ) as  Eco on eco.idcobrador  = cli.cobrador and cli.Cobrador <> 0  "
					+ " \n  left join (select * from HistorialVenta where Periodo = 'Anual') as hv on  hv.Anio = year(GETDATE ())-1 and hv.FK01_Cliente = cli.Clave  "
					+ " \n  left join(select fk01_cliente , fk02_cartera  from Cliente_Cartera )as cc on cc.fk01_cliente = cli.clave "
					+ " \n  left join (select * from cartera ) AS Cart ON Cart.pk_cartera = cc.fk02_Cartera "
					+

					" \n  LEFT JOIN (SELECT * FROM Facturas as f  where F.DeSistema=1 AND  F.Importe > 0 AND F.Fecha > '20091231 23:59' AND F.Estado<>'Cancelada' AND F.Estado<>'Por Cancelar' AND year ( F.Fecha) = year(getdate())) AS F ON  F.Cliente = cli.Clave  "
					+ " \n  LEFT JOIN (SELECT * FROM Monedas) AS MON ON cast(MON.Fecha as date) = cast(F.Fecha as date)   "
					+ " \n  LEFT JOIN (SELECT * FROM Remisiones) RE ON CAST(RE.Nota AS VARCHAR(100)) = F.Factura AND F.DeRemision = 1    "
					+ " \n  LEFT JOIN (SELECT * FROM PRemisiones) PRE ON PRE.Factura = RE.Factura AND PRE.FPor = RE.FPor AND PRE.CPedido = RE.CPedido   "
					+ " \n  LEFT JOIN (SELECT Importe, Cant, PPedido, Factura, FPor, CPedido, idPFactura FROM PFacturas) AS PF ON PF.Factura = F.Factura AND PF.FPor = F.FPor    "
					+ " \n  	AND CASE WHEN F.DeRemision = 1 THEN PRE.CPedido ELSE F.CPedido END = PF.CPedido    "
					+ " \n  	AND CASE WHEN F.DeRemision = 1 THEN PRE.PPedido ELSE PF.PPedido END = PF.PPedido   "
					+ " \n  LEFT JOIN (SELECT * FROM PPedidos) PP ON PP.CPedido = PF.CPedido AND PP.Part = PF.PPedido  "
					+ " \n  LEFT JOIN (SELECT * FROM PNotaCredito) PNC ON PNC.FK02_PFactura = PF.idPFactura   "
					+ " \n  LEFT JOIN (SELECT * FROM NotaCredito) NC ON NC.PK_Nota = PNC.FK01_Nota   "
					+ " \n  LEFT JOIN (SELECT * FROM Monedas) AS MON2 ON cast(MON2.Fecha as date) = cast(NC.Fecha as date)    "
					+ " \n  LEFT JOIN (  select  Nombre nombreCorporativo , PK_Corporativo idcorporativo from Corporativo ) AS CORP ON CORP.idcorporativo = FK02_Corporativo  "
					+ " \n	LEFT JOIN (  SELECT valor, CASE WHEN Valor = 'farma' THEN 'FARMACEUTICA' WHEN Valor = 'ALIMENTOS' THEN 'ALIMENTOS Y BEBIDAS' WHEN VALOR = 'CLINICO' OR VALOR= 'Clínico' THEN 'CLINICA HOSPITALARIA'ELSE Valor  END VALORINDUSTRIA, PK_Folio Idindustria  from ValorCombo where Concepto = 'industrial' "
					+ " \n		 )as vc on vc.valorindustria = cli.Industria OR ( VC.VALORINDUSTRIA =  'GOBIERNO' AND CLI.Sector = 'PUBLICO') "
					+

					" \n  where cli.Habilitado =  1  and "
					+ f.obtenerEmpresasProquifa("cli.clave") + parametros
					+ "and cli.Clave NOT IN (SELECT CC.FK01_Cliente FROM Cartera C INNER JOIN Cliente_Cartera CC ON CC.FK02_Cartera = C.PK_Cartera WHERE " + condicion + " AND C.Publicada = 1 )"
					+ " \n  group by cli.Clave, cli.Nombre ,cli.Industria,ees.esac,ees.idesac,eev.ev,eev.idev,eco.cobrador,eco.idcobrador,hv.MontoVenta,cli.FechaRegistro , cli.rol, vc.idindustria, cli.ruta,CORP.idcorporativo , corp.nombrecorporativo, cli.imagen   "
					+ " \n  )as pre  left join(select * from ObjetivoCrecimiento_NI)as ob on ob.NivelIngreso = pre.nivel  "
					+ " \n  order by Nombre ";

			log.info(query);

			return super.jdbcTemplate.query(query,
					new ClienteSinCarteraRowMapper());
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ValorCombo> obtenerComboEsacNombreCartera()
			throws ProquifaNetException {
		try {

			String query = " \n select clave pk_folio, usuario Valor, UPPER( COALESCE(apellido, 'SINAPELLIDO') +' · SECTORB' + CONVERT (VARCHAR (15),coalesce (CONSECUTIVO,1)) )  concepto, 'Esac' as tipo  from Empleados  "
					+ " \n LEFT JOIN (SELECT distinct FK02_Esac,  COUNT (1)+1 CONSECUTIVO   FROM Cartera  where publicada  = 0 and corporativo  = 0 and Sistema = 0  GROUP BY FK02_Esac  ) AS CART ON CART.FK02_Esac = Empleados.Clave   "
					+ " \n where Nivel in (8, 41) and Fase >0 order by Usuario ";
			log.info(query);
			return super.jdbcTemplate.query(query, new ValorComboRowMapper());
		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}

	@Override
	public Boolean actualizarMensajeroDeClientes(List<CarteraCliente> clientes, long idMensajero) {
		try {
			String query = "\n update Clientes set FK07_mensajero = " + idMensajero;
			String idClientes = "";
			for(int i = 0; i < clientes.size(); i++) {
				if(i>0) {
					idClientes += "," + clientes.get(i).getIdCliente();
				}else {
					idClientes += clientes.get(i).getIdCliente();
				}
			}
			query += " where clave in (" + idClientes + ")";
			log.info(query);
			super.update(query);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			return false;
		}
	}

	@Override
	public Cliente obtenerInfoCliente(Long idCliente)
			throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String sql = " SELECT *, '' NOM_EV FROM Clientes WHERE Clave="
					+ idCliente;
			//logger.info(sql);
			return (Cliente) super.jdbcTemplate.queryForObject(sql, map,
					new ClienteRowMapper());
		} catch (Exception e) {
			log.info("Error:" + e.getMessage());
			return null;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<String> getPendientesRespCobrador(String idCliente)
			throws ProquifaNetException {
		StringBuilder sql = new StringBuilder(" ");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			sql.append(" \n DECLARE @idCliente int = " + idCliente + " ");
			// ---------------- Facturar por adela ----------------
			sql.append(" \n SELECT PEND.Folio FROM Pendiente AS PEND ");
			sql.append(" \n LEFT JOIN(SELECT * FROM Pedidos) AS PED ON PED.CPedido = PEND.Docto ");
			sql.append(" \n LEFT JOIN(SELECT * FROM Clientes) AS CLI ON CLI.Clave = PED.idCliente ");
			sql.append(" \n WHERE PEND.Tipo='Facturar por adelantado' AND PEND.FFin IS NULL AND CLI.Clave = @idCliente ");
			sql.append(" \n UNION ");
			// ---------------- Refacturar por adela ----------------
			sql.append(" \n SELECT PEND.Folio FROM Pendiente AS PEND ");
			sql.append(" \n LEFT JOIN Facturas AS FAC ON FAC.FPor = PEND.Partida AND FAC.Factura = PEND.Docto ");
			sql.append(" \n LEFT JOIN Clientes AS CLI ON CLI.Clave = FAC.Cliente ");
			sql.append(" \n WHERE PEND.Tipo='A RefacturaciónXAdela' AND PEND.FFin IS NULL AND CLI.Clave = @idCliente ");
			sql.append(" \n UNION ");
			// ---------------- A facturar ----------------
			sql.append(" \n SELECT pnd.Folio FROM Pendiente AS pnd ");
			sql.append(" \n LEFT JOIN(SELECT * FROM Pedidos) AS p ON pnd.Docto = p.CPedido ");
			sql.append(" \n LEFT JOIN(SELECT * FROM DoctosR) AS doc ON p.DoctoR=doc.Folio ");
			sql.append(" \n LEFT JOIN(SELECT * FROM Clientes) AS c ON c.Clave=doc.Empresa ");
			sql.append(" \n LEFT JOIN(SELECT * FROM Empleados) as emp ON emp.Usuario = pnd.Responsable ");
			sql.append(" \n WHERE pnd.Tipo='A facturar' AND pnd.FFin IS NULL  AND emp.FK01_Funcion <> 36 ");
			sql.append(" \n AND c.Clave = @idCliente ");
			sql.append(" \n UNION ");
			// ---------------- Nota de Credito ----------------
			sql.append(" \n SELECT PEND.Folio  ");
			sql.append(" \n FROM pendiente AS PEND ");
			sql.append(" \n LEFT JOIN NotaCredito AS NOTA ON NOTA.PK_Nota = PEND.Partida ");
			sql.append(" \n LEFT JOIN Clientes AS CLI ON CLI.Clave = NOTA.FK01_Cliente ");
			sql.append(" \n WHERE PEND.Tipo='Nota de credito' AND PEND.FFin IS NULL ");
			sql.append(" \n AND CLI.Clave = @idCliente ");
			sql.append(" \n UNION ");
			// ---------------- Facturar Remision ----------------
			sql.append(" \n SELECT PEND.Folio ");
			sql.append(" \n FROM Pendiente AS PEND ");
			sql.append(" \n LEFT JOIN Remisiones AS REM ON REM.Factura = PEND.Docto AND REM.FPor = PEND.Partida ");
			sql.append(" \n LEFT JOIN Clientes AS CLI ON CLI.Clave = REM.Cliente ");
			sql.append(" \n WHERE PEND.Tipo='Facturar remisión' AND PEND.FFin IS NULL ");
			sql.append(" \n AND CLI.Clave = @idCliente ");
			sql.append(" \n UNION ");
			// ---------------- A Refacturacion ----------------
			sql.append(" \n SELECT PEND.Folio ");
			sql.append(" \n FROM Pendiente AS PEND ");
			sql.append(" \n LEFT JOIN Facturas AS FAC ON FAC.Factura = PEND.Docto AND PEND.Partida = FAC.FPor ");
			sql.append(" \n LEFT JOIN Clientes AS CLI ON CLI.Clave = FAC.Cliente ");
			sql.append(" \n WHERE PEND.Tipo='A refacturación' AND PEND.FFin IS NULL ");
			sql.append(" \n AND CLI.Clave = @idCliente ");

			sql.append(" \n UNION ");
			// ---------------- A Facturar Pedido Internacional ----------------
			sql.append(" \n SELECT PEND.Folio ");
			sql.append(" \n FROM Pendiente AS PEND ");
			sql.append(" \n LEFT JOIN Pedidos AS PED ON PED.CPedido = PEND.Docto ");
			sql.append(" \n LEFT JOIN DoctosR AS DOC ON DOC.Folio = PED.DoctoR ");
			sql.append(" \n LEFT JOIN Clientes AS CLI ON CLI.Clave = DOC.Empresa ");
			sql.append(" \n WHERE PEND.Tipo='A facturar phs' AND PEND.FFin IS NULL ");
			sql.append(" \n AND CLI.Clave = @idCliente ");
			sql.append(" \n UNION ");
			// ---------------- Subir Factura al Portal ----------------
			sql.append(" \n SELECT p.Folio FROM Pendiente P LEFT JOIN Facturas F ON F.idFactura = P.Docto ");
			sql.append(" \n LEFT JOIN Clientes C ON C.Clave = F.Cliente LEFT JOIN Empleados emp  ON emp.Clave = c.cobrador ");
			sql.append(" \n WHERE P.Tipo = 'SubirFacturaPortal' AND P.FFin is null AND C.Clave = @idCliente ");
			sql.append(" \n UNION ");
			// ---------------- Amparar Cancelacion ----------------
			sql.append(" \n SELECT PEND.Folio ");
			sql.append(" \n FROM Pendiente AS PEND ");
			sql.append(" \n LEFT JOIN Facturas AS FAC ON FAC.Factura = PEND.Docto AND PEND.Partida = FAC.FPor ");
			sql.append(" \n LEFT JOIN Clientes AS CLI ON CLI.Clave = FAC.Cliente ");
			sql.append(" \n WHERE PEND.Tipo='Amparar Cancelación'  AND PEND.FFin IS NULL ");
			sql.append(" \n AND CLI.Clave = @idCliente ");
			sql.append(" \n UNION ");
			// ---------------- Amparar Cancelacion NC ----------------
			sql.append(" \n SELECT PEND.Folio  ");
			sql.append(" \n FROM Pendiente AS PEND ");
			sql.append(" \n LEFT JOIN NotaCredito AS NOTA ON NOTA.PK_Nota = PEND.Docto ");
			sql.append(" \n LEFT JOIN Clientes AS CLI ON CLI.Clave = NOTA.FK01_Cliente ");
			sql.append(" \n WHERE PEND.Tipo='Amparar cancelacion NC' AND PEND.FFin IS NULL ");
			sql.append(" \n AND CLI.Clave = @idCliente ");
			sql.append(" \n UNION ");
			// ---------------- Programar revicion ----------------
			sql.append(" \n SELECT PEND.Folio  ");
			sql.append(" \n FROM Pendiente AS PEND ");
			sql.append(" \n LEFT JOIN Facturas AS FAC ON FAC.FPor = PEND.Partida AND FAC.Factura = PEND.Docto ");
			sql.append(" \n LEFT JOIN Clientes AS CLI ON CLI.Clave = FAC.Cliente  ");
			sql.append(" \n LEFT JOIN Empleados AS EMP ON EMP.Clave = CLI.Cobrador ");
			sql.append(" \n WHERE PEND.FFin IS NULL AND PEND.Tipo='Programar revisión'  and fac.DeSistema =  1   ");
			sql.append(" \n AND CLI.Clave = @idCliente ");
			sql.append(" \n UNION ");
			// ---------------- Monitorear cobros y pagos ----------------
			sql.append(" \n SELECT PEND.Folio  ");
			sql.append(" \n FROM Pendiente AS PEND ");
			sql.append(" \n LEFT JOIN Facturas AS FAC ON FAC.Factura = PEND.Docto AND FAC.FPor = PEND.Partida ");
			sql.append(" \n LEFT JOIN Clientes AS CLI ON CLI.Clave = FAC.Cliente ");
			sql.append(" \n WHERE PEND.Tipo='Monitorear cobro' AND PEND.FFin IS NULL  and fac.DeSistema =  1   ");
			sql.append(" \n AND CLI.Clave = @idCliente ");
			sql.append(" \n UNION ");
			sql.append(" \n SElECT PEND.Folio ");
			sql.append(" \n FROM Pendiente AS PEND ");
			sql.append(" \n LEFT JOIN DoctosR AS DOC ON DOC.Folio = PEND.Docto ");
			sql.append(" \n LEFT JOIN Clientes AS CLI ON CLI.Clave = DOC.Empresa ");
			sql.append(" \n WHERE PEND.Tipo='Pago por asociar' AND PEND.FFin IS NULL ");
			sql.append(" \n AND CLI.Clave = @idCliente ");
			sql.append(" \n UNION ");
			// ---------------- 'Validar cobro SC ----------------
			sql.append(" \n SELECT PEND.Folio  ");
			sql.append(" \n FROM Pendiente AS PEND ");
			sql.append(" \n LEFT JOIN Pedidos AS PED ON PED.CPedido = PEND.Docto ");
			sql.append(" \n LEFT JOIN Clientes AS CLI ON CLI.Clave = PED.idCliente ");
			sql.append(" \n WHERE PEND.FFin IS NULL  AND PEND.Tipo='Cobro a validar' ");
			sql.append(" \n AND CLI.Clave = @idCliente ");
			sql.append(" \n UNION ");
			// ---------------- 'Programar cobro ----------------
			sql.append(" \n SELECT PEND.Folio ");
			sql.append(" \n FROM Pendiente AS PEND ");
			sql.append(" \n LEFT JOIN Facturas AS FAC ON FAC.Factura = PEND.Docto AND FAC.FPor = PEND.Partida ");
			sql.append(" \n LEFT JOIN Clientes AS CLI ON CLI.Clave = FAC.Cliente ");
			sql.append(" \n WHERE PEND.FFin is null and PEND.Tipo = 'programar cobro' and fac.DeSistema =  1   ");
			sql.append(" \n AND CLI.Clave = @idCliente ");

			return super.jdbcTemplate
					.queryForList(sql.toString(), map, String.class);

		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getPendientesRespESAC(String idCliente)
			throws ProquifaNetException {
		String query = "";
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			query = " \n DECLARE @idCliente int = "
					+ idCliente
					+ " \n SELECT Pendiente.Folio"
					+ " \n From Pendiente, DoctosR, Clientes  "
					+ " \n WHERE Pendiente.Tipo='Pedido a cotizar'  AND FFin IS NULL  "
					+ " \n AND DoctosR.Folio=Pendiente.Docto AND DoctosR.Empresa = Clientes.Clave AND Clientes.Clave = @idCliente"
					+ " \n UNION"
					+ " \n SELECT Pendiente.Folio"
					+ " \n FROM pendiente , DoctosR, Clientes "
					+ " \n WHERE FFin IS NULL AND Pendiente.Tipo='Pedido por Tramitar SC' "
					+ " \n AND DoctosR.Folio=Pendiente.Docto AND Clientes.Clave=DoctosR.Empresa AND Clientes.Clave = @idCliente"
					+ " \n UNION"
					+ " \n SELECT  Pendiente.Folio"
					+ " \n FROM pendiente , DoctosR, Clientes  "
					+ " \n WHERE FFin IS NULL AND Pendiente.Tipo='Pedido por Tramitar'  "
					+ " \n AND DoctosR.Folio=Pendiente.Docto AND Clientes.Clave=DoctosR.Empresa  AND Clientes.Clave = @idCliente"
					+ " \n UNION"
					+ " \n SELECT Pendiente.Folio"
					+ " \n FROM pendiente , DoctosR, Clientes  "
					+ " \n WHERE FFin IS NULL AND Pendiente.Tipo='Pedido por Tramitar PSC' "
					+ " \n AND DoctosR.Folio=Pendiente.Docto AND Clientes.Clave=DoctosR.Empresa  AND Clientes.Clave = @idCliente"
					+ " \n UNION"
					+ " \n SELECT Pendiente.Folio"
					+ " \n FROM Pendiente, DoctosR, Clientes, Pedidos "
					+ " \n WHERE Pendiente.Tipo='PSC sin FEE' AND FFin IS NULL AND Clientes.Clave=DoctosR.Empresa"
					+ " \n AND Pedidos.CPedido=Pendiente.Docto  AND Pedidos.DoctoR=DoctosR.Folio AND Clientes.Clave = @idCliente"
					+ " \n UNION"
					+ " \n SELECT Pendiente.Folio"
					+ " \n FROM pendiente , DoctosR, Clientes  "
					+ " \n WHERE FFin IS NULL AND Pendiente.Tipo='Monitorear inconsistencias' "
					+ " \n AND DoctosR.Folio=Pendiente.Docto AND Clientes.Clave=DoctosR.Empresa AND Clientes.Clave = @idCliente"
					+ " \n UNION"
					+ " \n SELECT Pendiente.Folio"
					+ " \n FROM Pendiente,Cotizas,Bitacora "
					+ " \n WHERE Pendiente.Tipo = 'Seguimiento' AND Pendiente.FFin IS NULL "
					+ " \n  AND Pendiente.Docto = Cotizas.Clave AND Cotizas.Estado <> 'Cancelada' AND Bitacora.Cotiza=Cotizas.Clave "
					+ " \n  AND Cotizas.FK01_idCliente = @idCliente"
					+ " \n UNION"
					+ " \n SELECT Pendiente.Folio "
					+ " \n FROM Pendiente, DoctosR,Clientes "
					+ " \n WHERE Pendiente.tipo='PSC c/problemas'  AND DoctosR.Folio=Pendiente.Docto "
					+ " \n AND Clientes.Clave=DoctosR.Empresa AND FFin IS NULL  AND Clientes.Clave = @idCliente"
					+ " \n UNION"
					+ " \n SELECT Pendiente.Folio"
					+ " \n FROM Pendiente, DoctosR,Clientes "
					+ " \n WHERE Pendiente.tipo='Pago pendiente'  AND DoctosR.Folio=Pendiente.Docto "
					+ " \n AND Clientes.Clave=DoctosR.Empresa AND FFin IS NULL AND Clientes.Clave = @idCliente "
					+ " \n UNION"
					+ " \n SELECT Pendiente.Folio"
					+ " \n FROM Pendiente,Facturas,Clientes "
					+ " \n WHERE Pendiente.Tipo='Factura por enviar'  AND Facturas.Factura=Docto "
					+ " \n AND CPedido = Partida AND Clave=Cliente AND FFin IS NULL AND Clientes.Clave = @idCliente "
					+ " \n UNION"
					+ " \n SELECT Pendiente.Folio"
					+ " \n FROM Pendiente, DoctosR,Clientes "
					+ " \n WHERE Pendiente.tipo='PCC c/problemas'  AND DoctosR.Folio=Pendiente.Docto "
					+ " \n AND Clientes.Clave=DoctosR.Empresa AND FFin IS NULL  AND Clientes.Clave = @idCliente "
					+ " \n UNION"
					+ " \n SELECT Pendiente.Folio "
					+ " \n FROM PENDIENTE , pedidos  "
					+ " \n WHERE cpedido = docto and   pendiente.TIPO = 'Alistar criterios de envio' "
					+ " \n AND FFIN IS NULL AND PedidoS.idCliente = @idCliente "
					+ " \n UNION"
					+ " \n SELECT Pendiente.Folio"
					+ " \n FROM Pendiente,Facturas,Clientes "
					+ " \n WHERE Pendiente.Tipo='Confirmar datos de factura'  "
					+ " \n AND Pendiente.Docto=Facturas.Factura AND Partida=Facturas.FPor "
					+ " \n AND Clientes.Clave=Facturas.Cliente AND Pendiente.FFin IS NULL AND Clientes.Clave = @idCliente "
					+ " \n UNION"
					+ " \n SELECT Pendiente.Folio"
					+ " \n FROM Pendiente,DoctosR,Clientes "
					+ " \n WHERE Pendiente.Tipo='Refacturación' AND Pendiente.Docto=DoctosR.Folio "
					+ " \n AND Clientes.Clave=DoctosR.Empresa AND Pendiente.FFin IS NULL AND Clientes.Clave = @idCliente "
					+ " \n UNION"
					+ " \n SELECT Pendiente.Folio"
					+ " \n FROM Pendiente,DoctosR,Clientes "
					+ " \n WHERE Pendiente.Tipo='Modificaciones de pedido' AND Pendiente.Docto=DoctosR.Folio "
					+ " \n AND Clientes.Clave=DoctosR.Empresa AND Pendiente.FFin IS NULL AND Clientes.Clave = @idCliente "
					+ " \n UNION"
					+ " \n SELECT Pendiente.Folio"
					+ " \n FROM Clientes, DoctosR, Pedidos, PCompras, Pendiente,Notificado  "
					+ " \n WHERE Pendiente.Tipo='Aviso de cambios' AND Pendiente.FFin IS NULL   "
					+ " \n AND Pendiente.Docto=PCompras.Compra AND PCompras.Partida=Pendiente.Partida AND PCompras.CPedido=Pedidos.CPedido "
					+ " \n AND Pedidos.DoctoR=DoctosR.Folio AND DoctosR.Empresa=Clientes.Clave AND PCompras.FolioNT=Notificado.idNotificado  "
					+ " \n AND Clientes.Clave = @idCliente "
					+ " \n UNION"
					+ " \n SELECT Pendiente.Folio"
					+ " \n FROM Clientes, Pendiente, Facturas  "
					+ " \n WHERE Pendiente.Tipo='Datos de pago pendiente' AND Pendiente.FFin IS NULL AND Facturas.Factura=Pendiente.Docto "
					+ " \n AND Facturas.FPor=Pendiente.Partida AND Clientes.Clave=Facturas.Cliente  AND Clientes.Clave = @idCliente "
					+ " \n UNION"
					+ " \n SELECT Pendiente.Folio"
					+ " \n FROM Clientes, DoctosR, Pendiente  "
					+ " \n WHERE Pendiente.Tipo='OTROS a trabajar' AND Pendiente.FFin IS NULL  "
					+ " \n AND DoctosR.Empresa=Clientes.Clave AND Pendiente.Docto=DoctosR.Folio AND Clientes.Clave = @idCliente "
					+ " \n UNION"
					+ " \n SELECT Pendiente.Folio"
					+ " \n FROM Clientes, Facturas, Pendiente  "
					+ " \n WHERE Pendiente.Tipo='Servicio de tráfico programado' AND Pendiente.FFin IS NULL AND Clientes.Clave=Facturas.Cliente "
					+ " \n AND Facturas.Factura=Pendiente.Docto AND Facturas.FPor=Pendiente.Partida AND Clientes.Clave = @idCliente "
					+ " \n UNION"
					+ " \n SELECT Pendiente.folio "
					+ " \n FROM Clientes, RutaEs, Pendiente  "
					+ " \n WHERE Pendiente.Tipo='Servicio de tráfico programado' AND Pendiente.FFin IS NULL AND Clientes.Clave=RutaES.idCliente "
					+ " \n AND RutaES.FolioDoctos=Pendiente.Docto AND Clientes.Clave = @idCliente "
					+ " \n UNION"
					+ " \n SELECT Pendiente.folio "
					+ " \n FROM Clientes, RutaRE, Pendiente  "
					+ " \n WHERE Pendiente.Tipo='Servicio de tráfico programado' AND Pendiente.FFin IS NULL AND Clientes.Clave = @idCliente "
					+ " \n AND Clientes.Clave=RutaRE.idCliente AND RutaRE.FolioDoctos=Pendiente.Docto";

			return super.jdbcTemplate.queryForList(query, map, String.class);

		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}

	}

	@Override
	public Boolean updatePendientesCotizacionESAC(String idCliente, String esac)
			throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String query = "\n UPDATE DoctosR SET RPor = '"
					+ esac
					+ "' WHERE Folio IN ( SELECT DISTINCT DoctosR.Folio FROM DoctosR,Clientes "
					+ "\n WHERE   FProceso IS NULL AND Docto='Requisición' AND DoctosR.Estado IS NULL"
					+ "\n AND Fecha>'01/01/2008 00:00'  AND Clientes.Clave = DoctosR.Empresa AND Clientes.Clave = "
					+ idCliente
					+ ")"
					+

					"\n UPDATE Cotizas SET Vendedor = '"
					+ esac
					+ "' WHERE Clave IN ( SELECT DISTINCT Cotizas.Clave FROM PCotPharma,Cotizas "
					+ "\n WHERE PCotPharma.Estado='Por Investigar' AND Cotiza=Cotizas.Clave AND Cotizas.FK01_idCliente="
					+ idCliente
					+ ")"
					+

					"\n UPDATE Cotizas SET Vendedor = '"
					+ esac
					+ "' WHERE Clave IN (SELECT  DISTINCT Cotizas.Clave"
					+ "\n From Cotizas, PCotizas, PCotPharma  WHERE PCotizas.Destino IS NULL AND PCotizas.Estado='Cotización' "
					+ "\n AND PCotizas.Folio<>'00' AND PCotizas.Folio<>'99'  AND PCotPharma.Estado='En Catálogo' "
					+ "\n AND PCotizas.Codigo=PCotPharma.Codigo AND PCotizas.Fabrica=PCotPharma.Fabrica  AND PCotizas.Clave=Cotizas.Clave "
					+ "\n AND Cotizas.Estado<>'Cancelada' AND Cotizas.FK01_idCliente = "
					+ idCliente
					+ ")"
					+

					"\n UPDATE SegCotiza SET Vendedor = '"
					+ esac
					+ "' WHERE Folio IN (SELECT  DISTINCT SegCotiza.Folio FROM SegCotiza,Cotizas "
					+ "\n WHERE SegCotiza.Cotiza = Cotizas.Clave  AND FRealizo IS NULL AND Cotizas.FK01_idCliente = "
					+ idCliente + ")";
			// //logger.info(query);
			this.jdbcTemplate.update(query, map);

			return true;
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> obteneridsCarterasPoridCliente(Long idCliente)
			throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String query = " Select FK02_Cartera from Cliente_Cartera where FK01_Cliente = " + idCliente;
			log.info(query);
			return (List<Long>) super.jdbcTemplate.queryForList(query, map, Long.class);
		} catch (Exception e) {
			log.info(e.getMessage());
			throw new ProquifaNetException();
		}
	}

	@Override
	public boolean deleteClienteCartera(Long idcliente, Long idcartera)
			throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String query = " delete from cliente_cartera where fk01_cliente = " + idcliente;
			//logger.info("Query busqueda de cartera, por corporativo ------>: "
			//					+ query);
			super.jdbcTemplate.update(query, map);
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}

	@Override
	public Boolean updateCliente(Cliente cliente) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nombre", cliente.getNombre());
			map.put("rol", cliente.getRol());
			map.put("sector", cliente.getSector());
			map.put("industria", cliente.getIndustria());
			map.put("estado", cliente.getEstado());
			map.put("pais", cliente.getPais());
			map.put("razonSocial", cliente.getRazonSocial());
			map.put("vendedor", cliente.getVendedor());
			map.put("idEjecutivoVenta", cliente.getIdEjecutivoVenta());
			map.put("objetivoCrecimiento", cliente.getObjectivoCrecimiento());
			map.put("condicionesPago", cliente.getCondicionesPago());
			map.put("comentarios", cliente.getComentarios());
			map.put("limiteCredito", cliente.getLimiteCredito());
			map.put("lineaCredito", cliente.getLineaCredito());
			map.put("rfc", cliente.getRfc());
			map.put("monedaFactura", cliente.getMonedaFactura());
			map.put("empresaFactura", cliente.getEmpresaFactura());
			map.put("paisFiscal", cliente.getPaisFiscal());
			map.put("estadoFiscal", cliente.getEstadoFiscal());
			map.put("calleFiscal", cliente.getCalleFiscal());
			map.put("delegacionFiscal", cliente.getDelegacionFiscal());
			map.put("codigoPostalFiscal", cliente.getCodigoPostalFiscal());
			map.put("facturaPortal", cliente.getFacturaPortal());
			map.put("correoElectronico", cliente.getCorreoElectronico());
			map.put("comentaFacturacion", cliente.getComentaFacturacion());
			map.put("habilitado", cliente.getHabilitado());
			map.put("nivelPrecio", cliente.getNivelPrecio());
			map.put("comentariosCredito", cliente.getComentariosCredito());
			map.put("imagen", cliente.getImagen());
			map.put("enviarEmail", cliente.getEnviarEmail());
			map.put("idCobrador", cliente.getIdCobrador());
			map.put("importancia", cliente.getImportancia());
			map.put("objetivoCrecimientoFundamental", cliente.getObjetivoCrecimientoFundamental());
			map.put("idCliente", cliente.getIdCliente());

			StringBuilder sbQuery = new StringBuilder(" ");
			sbQuery.append("UPDATE Clientes SET Nombre = :nombre, Rol = :rol, sector = :sector, Industria = :industria,");
			sbQuery.append(" Estado = :estado, Pais = :pais, RSocial = :razonSocial,  Vendedor = :vendedor, FK01_EV = :idEjecutivoVenta,  ");
			sbQuery.append(" objetivoCrecimiento = :objetivoCrecimiento, CPago = :condicionesPago, Comenta = :comentarios, ");
			sbQuery.append(" LimiteCredito = :limiteCredito, LineaCredito = :lineaCredito, CURP = :rfc, MonedaFactura = :monedaFactura,");
			sbQuery.append(" Factura = :empresaFactura, RSPais = :paisFiscal, RSEstado = :estadoFiscal, RSCalle = :calleFiscal, RSDel = :delegacionFiscal, RSCP = :codigoPostalFiscal, ");
			sbQuery.append(" FacturaPortal = :facturaPortal, MailFElectronica = :correoElectronico, ComentaFacturacion = :comentaFacturacion, ");
			sbQuery.append(" Habilitado = :habilitado, NivelPrecio= :nivelPrecio, ComentaCredito = :comentariosCredito, imagen = :imagen, FElectronica = :enviarEmail,");
			sbQuery.append(" Cobrador = :idCobrador, FUActual = GETDATE(), Importancia = :importancia,ObjetivoCrecimientoFundamental = :objetivoCrecimientoFundamental WHERE clave = :idCliente");

			// //logger.info(sbQuery.toString());
			super.jdbcTemplate.update(sbQuery.toString(), map);
			return true;
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

	@Override
	public Boolean updateClienteCredito(Cliente cliente) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("limiteCredito", cliente.getLimiteCredito());
			map.put("lineaCredito", cliente.getLineaCredito());
			map.put("condicionesPago", cliente.getCondicionesPago());
			map.put("comentarios", cliente.getComentarios());
			map.put("medioPago", cliente.getMedioPago());
			map.put("numeroDeCuenta", cliente.getNumeroDeCuenta());
			map.put("idCliente", cliente.getIdCliente());

			StringBuilder sbQuery = new StringBuilder(
					"UPDATE Clientes set limiteCredito = :limiteCredito, lineaCredito = :lineaCredito, CPago = :condicionesPago, Comenta = :comentarios, "
							+ " MedioPago = :medioPago, NumCtaPago = :numeroDeCuenta, FUActual = GETDATE() WHERE clave = :idCliente");
			jdbcTemplate.update(sbQuery.toString(), map);
			return true;
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

	@Override
	public Boolean updateClienteFacturacion(Cliente cliente) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("razonSocial", cliente.getRazonSocial());
			map.put("rfc", cliente.getRfc());
			map.put("monedaFactura", cliente.getMonedaFactura());
			map.put("empresaFactura", cliente.getEmpresaFactura());
			map.put("facturaPortal", cliente.getFacturaPortal());
			map.put("entregaYRevision", cliente.getEntregaYRevision());
			map.put("correoElectronico", cliente.getCorreoElectronico());
			map.put("comentaFacturacion", cliente.getComentaFacturacion());
			map.put("usoCFDI", cliente.getUsoCFDI());
			map.put("metodoDePago", cliente.getMetodoDePago());
			map.put("idCliente", cliente.getIdCliente());

			String query = "UPDATE Clientes set RSocial = :razonSocial, CURP = :rfc, MonedaFactura = :monedaFactura, Factura = :empresaFactura, FUActual = GETDATE(), "
					+ " FacturaPortal = :facturaPortal, EntregayRevision = :entregaYRevision, MailFELectronica = :correoElectronico, ComentaFacturacion = :comentaFacturacion, "
					+ " FK04_UsoCFDI = :usoCFDI, FK05_MetodoPago = :metodoDePago WHERE clave = :idCliente";

			super.jdbcTemplate.update(query, map);
			log.info(query);

			return true;
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

	@Override
	public Boolean updateClienteCorporativo(Cliente cliente)
			throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("corporativo", cliente.getCorporativo());
			map.put("idCliente", cliente.getIdCliente());

			StringBuilder sbQuery = new StringBuilder(
					"UPDATE Clientes SET FK02_Corporativo = :corporativo WHERE clave = :idCliente");
			//logger.info(sbQuery);
			super.jdbcTemplate.update(sbQuery.toString(), map);
			return true;
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

	@Override
	public Boolean deleteHorarioCliente(Long idHorario)
			throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			super.jdbcTemplate.update("DELETE FROM Horario WHERE PK_Horario = " + idHorario, map);
			return true;
		} catch (Exception e) {
			f = new Funcion();
			f.enviarCorreoAvisoExepcion(e, "idHorario: " + idHorario);
			log.info(e.getMessage());
			return false;
		}
	}

	@Override
	public Boolean updateClienteHorario(Horario horario)
			throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("lunes", horario.getLunes());
			map.put("luDe1", horario.getLuDe1());
			map.put("luDe2", horario.getLuDe2());
			map.put("luA1", horario.getLuA1());
			map.put("luA2", horario.getLuA2());
			map.put("martes", horario.getMartes());
			map.put("maDe1", horario.getMaDe1());
			map.put("maDe2", horario.getMaDe2());
			map.put("maA1", horario.getMaA1());
			map.put("maA2", horario.getMaA2());
			map.put("miercoles", horario.getMiercoles());
			map.put("miDe1", horario.getMiDe1());
			map.put("miDe2", horario.getMiDe2());
			map.put("miA1", horario.getMiA1());
			map.put("miA2", horario.getMiA2());
			map.put("jueves", horario.getJueves());
			map.put("juDe1", horario.getJuDe1());
			map.put("juDe2", horario.getJuDe2());
			map.put("juA1", horario.getJuA1());
			map.put("juA2", horario.getJuA2());
			map.put("viernes", horario.getViernes());
			map.put("viDe1", horario.getViDe1());
			map.put("viDe2", horario.getViDe2());
			map.put("viA1", horario.getViA1());
			map.put("viA2", horario.getViA2());
			map.put("idHorario", horario.getIdHorario());

			StringBuilder sbQuery = new StringBuilder("UPDATE Horario SET ");
			sbQuery.append("lunes = :lunes, lude1 = :luDe1, lude2 = :luDe2, lua1 = :luA1, lua2 = :luA2, martes = :martes, made1 = :maDe1, made2 = :maDe2,");
			sbQuery.append("maa1 = :maA1, maa2 = :maA2, miercoles = :miercoles, mide1 = :miDe1, mide2 = :miDe2, mia1 = :miA1, mia2 = :miA2, ");
			sbQuery.append("jueves = :jueves, jude1 = :juDe1, jude2 = :juDe2, jua1 = :juA1, jua2 = :juA2, viernes = :viernes, vide1 = :viDe1, vide2 = :viDe2,");
			sbQuery.append("via1 = :viA1, via2 = :viA2 WHERE PK_Horario = :idHorario");

			jdbcTemplate.update(sbQuery.toString(), map);
			//logger.info(sbQuery.toString());

			return true;

		} catch (Exception e) {
			f = new Funcion();
			f.enviarCorreoAvisoExepcion(e, horario);
			log.info(e.getMessage());
			return false;
		}
	}

	@Override
	public Boolean agregarHorarioCliente(Horario horario)
			throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("lunes", horario.getLunes());
			map.put("luDe1", horario.getLuDe1());
			map.put("luDe2", horario.getLuDe2());
			map.put("luA1", horario.getLuA1());
			map.put("luA2", horario.getLuA2());
			map.put("martes", horario.getMartes());
			map.put("maDe1", horario.getMaDe1());
			map.put("maDe2", horario.getMaDe2());
			map.put("maA1", horario.getMaA1());
			map.put("maA2", horario.getMaA2());
			map.put("miercoles", horario.getMiercoles());
			map.put("miDe1", horario.getMiDe1());
			map.put("miDe2", horario.getMiDe2());
			map.put("miA1", horario.getMiA1());
			map.put("miA2", horario.getMiA2());
			map.put("jueves", horario.getJueves());
			map.put("juDe1", horario.getJuDe1());
			map.put("juDe2", horario.getJuDe2());
			map.put("juA1", horario.getJuA1());
			map.put("juA2", horario.getJuA2());
			map.put("viernes", horario.getViernes());
			map.put("viDe1", horario.getViDe1());
			map.put("viDe2", horario.getViDe2());
			map.put("viA1", horario.getViA1());
			map.put("viA2", horario.getViA2());
			map.put("tipo", horario.getTipo());
			map.put("idDireccion", horario.getIdDireccion());

			String query = " INSERT INTO Horario(lunes,lude1,lude2,lua1,lua2,martes,made1,made2,maa1,maa2,miercoles,mide1,mide2,mia1,mia2,"
					+ " jueves,jude1,jude2,jua1,jua2,viernes,vide1,vide2,via1, via2,Tipo,FK01_Direccion,fua) "
					+ " VALUES (:lunes, :luDe1, :luDe2, :luA1, :luA2, :martes, :maDe1, :maDe2, maA1, :maA2, :miercoles, :miDe1, miDe2,"
					+ " :miA1, :miA2, :jueves, :juDe1, :juDe2, :juA1, :juA2, :viernes, :viDe1, :viDe2, :viA1, :viA2, :tipo, :idDireccion,getdate())";

			log.info(query);

			super.jdbcTemplate.update(query, map);
			return true;

		} catch (RuntimeException e) {
			f = new Funcion();
			f.enviarCorreoAvisoExepcion(e, horario);
			log.info("Error: " + e.getMessage());
			return true;
		}
	}

	@Override
	public Boolean fechaActualizacionCliente(Long idCliente)
			throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			super.jdbcTemplate
			.update("UPDATE Clientes set FUActual = GETDATE() WHERE clave = "
					+ idCliente, map);
			return true;
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Horario> obtenerHorarioCliente(Long idDireccion) {
		try {
			String query = 	"";

			query = "SELECT * FROM ( SELECT 'Entrega' Tipo, 1 ID " +
					" \n UNION " +
					" \n SELECT 'Cobro', 3 " +
					" \n UNION " +
					" \n SELECT 'Revisión', 2 " +
					" \n UNION " +
					" \n SELECT 'Visita', 4) as  Tipo " +
					" \n LEFT JOIN Horario H ON H.Tipo = Tipo.Tipo AND FK01_Direccion ="+ idDireccion +
					" \n order by Tipo.ID ";

			log.info(query);
			return jdbcTemplate.query(query, new HorarioRowMapper());
		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}

	@Override
	public Boolean deleteHorarioClientePorIdDIreccion(Long idDireccion)
			throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			super.jdbcTemplate.update("DELETE FROM Horario WHERE FK01_Direccion = " + idDireccion, map);
			return true;
		} catch (Exception e) {
			f = new Funcion();
			f.enviarCorreoAvisoExepcion(e, "idHorario: " + idDireccion);
			log.info(e.getMessage());
			return false;
		}
	}

	@Override
	public Boolean deleteDireccionCliente(Long idDireccion)
			throws ProquifaNetException {	
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			super.jdbcTemplate.update("delete Direccion where PK_Direccion ="+idDireccion, map);
			//			super.jdbcTemplate.update("UPDATE Direccion SET Habilitado = 0 WHERE PK_Direccion = " + idDireccion);
			return true;
		} catch (Exception e) {
			f = new Funcion();
			f.enviarCorreoAvisoExepcion(e, "idDireccion: " + idDireccion);
			return false;
		}
	}

	public Boolean updateDireccionCliente(Direccion d)
			throws ProquifaNetException {
		try {

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pais", d.getPais());
			map.put("codigoPostal", d.getCodigoPostal());
			map.put("estado", d.getEstado());
			map.put("municipio", d.getMunicipio());
			map.put("ciudad", d.getCiudad());
			map.put("calle", d.getCalle());
			map.put("colonia", d.getColonia());
			map.put("zona", d.getZona());
			map.put("ruta", d.getRuta());
			map.put("mapa", d.getMapa());
			map.put("altitud", d.getAltitud());
			map.put("latitud", d.getLatitud());
			map.put("longitud", d.getLongitud());
			map.put("idCliente", d.getIdCliente());
			map.put("idProveedor", d.getIdProveedor());
			map.put("numInt", d.getNumInt());
			map.put("numExt", d.getNumExt());
			map.put("region", d.getRegion());
			map.put("tipoRegion", d.getTipoRegion());
			map.put("idDireccion", d.getIdDireccion());

			log.info(d.getPais()+" "+ d.getCodigoPostal()+" "+
					d.getEstado()+" "+ d.getMunicipio()+" "+ d.getCiudad()+" "+
					d.getCalle()+" "+ d.getColonia()+" "+ d.getZona()+" "+ d.getRuta()+" "+
					d.getMapa()+" "+ d.getAltitud()+" "+ d.getLatitud()+" "+
					d.getLongitud()+" "+ d.getIdCliente()+" "+d.getIdProveedor()+" "+d.getNumInt()+" "+ d.getNumExt()+" "+d.getRegion()+" "+d.getTipoRegion()+" "+d.getIdDireccion());


			super.jdbcTemplate.update("UPDATE Direccion SET Pais= :pais, CP= :codigoPostal, Estado= :estado, Municipio= :municipio, Ciudad= :ciudad, Calle= :calle, Colonia= :colonia, "
					+ " Zona= :zona, Ruta= :ruta, Mapa= :mapa, Altitud= :altitud, Latitud= :latitud, Longitud= :longitud, "
					+ "FUA=getdate(), FK01_Cliente= :idCliente, FK02_Proveedor = :idProveedor, No_Int = :numInt, No_Ext = :numExt, Region = :region, Tipo_Region = :tipoRegion  WHERE PK_Direccion= :idDireccion", map);
			log.info("=========================================================");
			return true;
		} catch (RuntimeException e) {
			f = new Funcion();
			f.enviarCorreoAvisoExepcion(e, d);
			log.info(e.getMessage());
			return false;
		}
	}

	public Long agregarDireccionCliente(Direccion d)
			throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pais", d.getPais());
			map.put("codigoPostal", d.getCodigoPostal());
			map.put("estado", d.getEstado());
			map.put("municipio", d.getMunicipio());
			map.put("ciudad", d.getCiudad());
			map.put("calle", d.getCalle());
			map.put("colonia", d.getColonia());
			map.put("zona", d.getZona());
			map.put("ruta", d.getRuta());
			map.put("mapa", d.getMapa());
			map.put("altitud", d.getAltitud());
			map.put("latitud", d.getLatitud());
			map.put("longitud", d.getLongitud());
			map.put("idCliente", d.getIdCliente());
			map.put("idProveedor", d.getIdProveedor());
			map.put("numInt", d.getNumInt());
			map.put("numExt", d.getNumExt());
			map.put("region", d.getRegion());
			map.put("tipoRegion", d.getTipoRegion());


			super.jdbcTemplate
			.update("INSERT INTO Direccion (Pais, CP, Estado, Municipio, Ciudad, Calle, Colonia, Zona, Ruta, Mapa, Altitud, Latitud, Longitud, FUA, FK01_Cliente,FK02_Proveedor,No_Int,No_Ext,Region,Tipo_Region)VALUES "
					+ " (:pais, :codigoPostal, :estado, :municipio, :ciudad, :calle, :colonia, :zona, :ruta, :mapa, :altitud, :latitud, :longitud,getdate(), :idCliente, :idProveedor, :numInt, :numExt, :region, :tipoRegion)",
					map);
			return super.queryForLong("SELECT TOP 1 MAX(PK_Direccion) FROM Direccion");
		} catch (RuntimeException e) {
			f = new Funcion();
			f.enviarCorreoAvisoExepcion(e, d);
			log.info(e.getMessage());
			return -1L;
		}
	}

	@Override
	public List<ConfiguracionPrecioProducto> findConfiguracionPrecioProductoCliente(
			Long idProveedor, Long idCliente, String tipoNivelIngreso,
			Long idConfigPrecio) throws ProquifaNetException {
		try {
			return findConfiguracionPrecioProductoClientePorProducto(idProveedor, idCliente, tipoNivelIngreso, idConfigPrecio, 0L);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ConfiguracionPrecioProducto> findConfiguracionPrecioProductoClientePorProducto(
			Long idProveedor, Long idCliente, String tipoNivelIngreso,
			Long idConfigPrecio, Long idProducto) throws ProquifaNetException {
		try {
			return findConfiguracionPrecioProductoClientePorConfiguracion(idProveedor, idCliente, tipoNivelIngreso, idConfigPrecio, idProducto.toString(), 0L, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConfiguracionPrecioProducto> findConfiguracionPrecioProductoClientePorConfiguracion(
			Long idProveedor, Long idCliente, String tipoNivelIngreso,
			Long idConfigPrecio, String idProducto, Long configuracion, String nivel) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("");
			sbQuery.append(" \n DECLARE @idProveedor AS Integer=" + idProveedor);
			sbQuery.append(" \n DECLARE @idCliente AS Integer=" + idCliente);
			sbQuery.append(" \n DECLARE @idConfigPrecio AS Integer="
					+ idConfigPrecio);
			/**@ymendez*/
			sbQuery.append(" DECLARE @idProducto AS Varchar(max) = '").append(idProducto).append("'");
			/**        */
			/**@Neli*/
			sbQuery.append(" DECLARE @idConfiguracion AS Integer = ").append(configuracion);
			/**        */
			sbQuery.append(" \n SELECT DISTINCT CPROD1.PK_ConfigPrecio_Producto, PROD.idProducto, ");

			sbQuery.append(" \n CASE WHEN CliCP.Factor IS NOT NULL THEN CliCP.NivelConfigPrecio ");
			sbQuery.append(" \n WHEN CliCP1.Factor IS NOT NULL THEN CliCP1.NivelConfigPrecio ");
			sbQuery.append(" \n WHEN CliCP2.Factor IS NOT NULL THEN CliCP2.NivelConfigPrecio ");
			sbQuery.append(" \n WHEN CliCP3.Factor IS NOT NULL THEN CliCP3.NivelConfigPrecio ");
			sbQuery.append(" \n ELSE '' END AS NivelClienteConf, ");

			sbQuery.append("\n (SELECT Ruta  from Clientes  WHERE  Clave=@idCliente ) AS RutaCliente ,");

			sbQuery.append(" \n CP.PK_Configuracion_Precio,CPROD1.FK06_CliCosto FK03_ConfCosto, ");
			sbQuery.append(" \n COALESCE(CONF.idConfigFamilia,CP.PK_Configuracion_Precio) AS idConfigFamilia,IND.Valor AS Industria,VCT.Valor AS Tipo,COALESCE(VCST.Valor,'') AS SubTipo,COALESCE(VCCTRL.Valor,'') AS Control, ");
			sbQuery.append(" \n CliCP.FUA,CP.Nivel,CP.FK01_Proveedor AS idProveedor, case when CliCP2.Factor IS not null then CPROD1.FK06_CliCosto else null end AS idCostoFactor,CF.Costo_Consularizacion,CF.Flete_Documentacion,CF.Factor_IGI, CF.Permiso,CF.AlmacenDestino, ");
			sbQuery.append(" \n CF.FactorDTA,CF.Honorarios, ");
			sbQuery.append(" \n CASE WHEN CliCP.CostoFijo IS NOT NULL THEN CliCP.CostoFijo WHEN CliCP2.CostoFijo  IS NOT NULL THEN CliCP2.CostoFijo WHEN CliCP3.CostoFijo  IS NOT NULL THEN CliCP3.CostoFijo ELSE CF.Factor_CostoFijo END Factor_CostoFijo,");
			sbQuery.append(" \n CASE WHEN CliCP.CostoFijoTemp IS NOT NULL THEN CliCP.CostoFijoTemp WHEN CliCP2.CostoFijoTemp  IS NOT NULL THEN CliCP2.CostoFijoTemp WHEN CliCP3.CostoFijoTemp  IS NOT NULL THEN CliCP3.CostoFijoTemp ELSE 0 END Factor_TempCostoFijo,");
			sbQuery.append(" \n coalesce(CliCP.CompuestaCostoF , CliCP2.CompuestaCostoF  ,CliCP3.CompuestaCostoF , CF.CompuestaCostoF, 1 ) CompuestaCostoF,");
			sbQuery.append(" \n coalesce(CliCP.CompuestaFactorU , CliCP2.CompuestaFactorU  , CliCP3.CompuestaFactorU, CF.CompuestaFactorU, 1 ) CompuestaFactorU,");
			sbQuery.append(" \n coalesce(CliCP.CompuestaCostoFTemp , CliCP2.CompuestaCostoFTemp  ,CliCP3.CompuestaCostoFTemp ,  1 ) CompuestaCostoFTemp,");
			sbQuery.append(" \n coalesce(CliCP.CompuestaFactorUTemp , CliCP2.CompuestaFactorUTemp  , CliCP3.CompuestaFactorUTemp,  1 ) CompuestaFactorUTemp,");
			sbQuery.append(" \n CF.Factor_Utilidad, ");
			sbQuery.append(" \n TE.FK01_RequierePermiso_ExisTE,TE.FK02_RequierePermiso_NoExisTE,TE.FK03_RequierePermiso_No, TE.FK04_FExpress,  ");
			sbQuery.append(" \n CASE WHEN CliCP.FK03_TiempoEntrega is Not null AND CliCP.FK03_TiempoEntrega <> 0 THEN TE.PK_Tiempo_Entrega ELSE 0 END AS idTEntrega, ");
			sbQuery.append(" \n prod.idProducto,prod.Lote,prod.CAS,(PROD.Costo * PROVEE.TC) Costo,ROUND( PORC.PORCIENTO,1) AS Porciento,(CASE WHEN prod.Moneda='Pesos' THEN 'MXN' WHEN prod.Moneda='Euros' THEN 'EUR' WHEN Moneda='Dolares' THEN 'USD'   ");
			sbQuery.append(" \n WHEN prod.Moneda='Libras' THEN 'LBS' WHEN prod.Moneda='DlCan' THEN 'CAD'END) AS Moneda,prod.Codigo,prod.Fabrica,prod.tipoPresentacion,prod.Origen,prod.FK02_Fabricante,PROVEE.MonedaVenta,");
			sbQuery.append(" \n CASE WHEN CliCP.FK04_AgenteAduanal <> 0 THEN CliCP.FK04_AgenteAduanal WHEN CliCP2.FK04_AgenteAduanal  <> 0 THEN CliCP2.FK04_AgenteAduanal WHEN CliCP3.FK04_AgenteAduanal  <> 0 THEN CliCP3.FK04_AgenteAduanal ELSE CF.FK01_AgenteAduanal END FK01_AgenteAduanal,");
			sbQuery.append(" \n CASE WHEN CliCP.FK05_LugarAgenteAduanal  <> 0 THEN CliCP.FK05_LugarAgenteAduanal WHEN CliCP2.FK05_LugarAgenteAduanal  <> 0 THEN CliCP2.FK05_LugarAgenteAduanal WHEN CliCP3.FK05_LugarAgenteAduanal  <> 0 THEN CliCP3.FK05_LugarAgenteAduanal ELSE CF.FK02_LugarAgenteAduanal END FK02_LugarAgenteAduanal,");
			sbQuery.append(" \n CASE WHEN CliCP.Fk06_LAAConcepto <> 0 THEN CliCP.Fk06_LAAConcepto WHEN CliCP2.Fk06_LAAConcepto <> 0 THEN CliCP2.Fk06_LAAConcepto WHEN CliCP3.Fk06_LAAConcepto <> 0 THEN CliCP3.Fk06_LAAConcepto ELSE CF.FK03_LAAConcepto END FK03_LAAConcepto,");
			sbQuery.append(" \n CF.Factor_Distribuidor AS Distribuidor,CF.Factor_Publico AS Publico, CPROD1.FK02_ConfFamilia ConfiguracionPrecio, (prod.fk03_Categoria_PrecioLista) PrecioLista, ");
			sbQuery.append(" \n CASE WHEN CliCP.Factor is Not null  THEN CliCP.Factor WHEN CliCP1.Factor is Not null  THEN CliCP1.Factor WHEN CliCP2.Factor is Not null  THEN CliCP2.Factor  ");
			sbQuery.append(" \n WHEN CliCP3.Factor is Not null  THEN CliCP3.Factor ELSE CF.Factor_"+ tipoNivelIngreso + " END  Factor, ");
			sbQuery.append(" \n CASE WHEN CliCP.FactorTemp is Not null  THEN CliCP.FactorTemp WHEN CliCP1.FactorTemp is Not null  THEN CliCP1.FactorTemp WHEN CliCP2.FactorTemp is Not null  THEN CliCP2.FactorTemp  ");
			sbQuery.append(" \n WHEN CliCP3.FactorTemp is Not null  THEN CliCP3.FactorTemp ELSE 0 END  FactorTemp, ");
			sbQuery.append(" \n CASE WHEN CliCP.Caduca is Not null  THEN CliCP.Caduca WHEN CliCP2.Caduca is Not null  THEN CliCP2.Caduca  ");
			sbQuery.append(" \n WHEN CliCP3.Caduca is Not null  THEN CliCP3.Caduca ELSE null END  Caduca, ");
			sbQuery.append(" \n CliCP.PK_ClienteConfiguracionPrecio idClienteConfig, CASE WHEN LICE.Factor = -1 OR  LICE.Factor IS NULL THEN '' ELSE PROD.DepositarioInternacional END  Licencia, CF.Factor_"
					+ tipoNivelIngreso + " Factor_orig, ");
			sbQuery.append(" \n CF.VALORENADUANA, CF.DESCUENTO, CF.FLETE, CASE WHEN CliCP.Factor IS NOT NULL THEN 1 ELSE  0 END   Restablecer, CASE WHEN CliCP2.Factor  IS NULL  THEN 0 ELSE 1 END Restablece_Costo,PROD.Costo costoCompra, \n ");
			sbQuery.append(" \n CASE WHEN CliCP.FactorTemp IS NOT NULL THEN 1 ELSE  0 END   RestablecerTemporal, CASE WHEN CliCP2.FactorTemp  IS NULL  THEN 0 ELSE 1 END Restablece_CostoTemporal,Moneda.*, \n ");
			sbQuery.append(getConsultaFormulaCatalogoCliente(tipoNivelIngreso, "Producto"));
			sbQuery.append(" \n 1");// la funcion obtenerConsultaFormulaCatalogo
			// siempre arroja un string con una ',' al
			// final. Se pone esta columna para evitar
			// un error en la consulta
			sbQuery.append(" \n FROM  ConfiguracionPrecio_Producto AS CPROD1  ");
			sbQuery.append(" \n LEFT JOIN(SELECT * FROM Productos) AS PROD ON PROD.idProducto= CPROD1.FK01_Producto ");
			sbQuery.append(" \n LEFT JOIN(SELECT * FROM Configuracion_Precio WHERE Industria=112) AS CP ON CP.PK_Configuracion_Precio = COALESCE(CPROD1.FK04_ConfProducto,CPROD1.FK08_ConfClasificacion,CPROD1.FK03_ConfCosto,CPROD1.FK02_ConfFamilia)    ");
			if(idProveedor > 0)
				sbQuery.append(" \n LEFT JOIN(SELECT Clave, CASE WHEN Moneda = MonedaVenta THEN 1 ELSE CASE WHEN Tipo_Cambio IS NULL OR Tipo_Cambio = 0 THEN 1 ELSE Tipo_Cambio END END  AS TC, MonedaVenta FROM Proveedores) AS PROVEE ON PROVEE.Clave = @idProveedor");
			else
				sbQuery.append(" \n LEFT JOIN(SELECT Clave, CASE WHEN Moneda = MonedaVenta THEN 1 ELSE CASE WHEN Tipo_Cambio IS NULL OR Tipo_Cambio = 0 THEN 1 ELSE Tipo_Cambio END END  AS TC, MonedaVenta FROM Proveedores) AS PROVEE ON PROVEE.Clave = PROD.proveedor ");
			sbQuery.append(" \n LEFT JOIN(SELECT * FROM Costo_Y_Factor) AS CF ON CF.PK_Costo_Factor=CP.FK02_Costo_Factor    ");
			sbQuery.append(" \n LEFT JOIN(SELECT * FROM ValorCombo WHERE Concepto='TipoProducto') AS VCT ON VCT.PK_Folio=CP.Tipo    ");
			sbQuery.append(" \n LEFT JOIN(SELECT * FROM ValorCombo WHERE Concepto='SubTipoProducto') AS VCST ON VCST.PK_Folio=CP.Subtipo  ");
			sbQuery.append(" \n LEFT JOIN(SELECT * FROM ValorCombo WHERE Concepto='Control') AS VCCTRL ON VCCTRL.PK_Folio=CP.Control   ");
			sbQuery.append(" \n LEFT JOIN(SELECT * FROM ValorCombo WHERE Concepto='Industrial') AS IND ON IND.PK_Folio = CP.Industria ");
			sbQuery.append(" \n LEFT JOIN(SELECT CONF.PK_Configuracion_Precio AS idConfig,FAMILY.PK_Configuracion_Precio idConfigFamilia FROM Configuracion_Precio AS CONF  ");
			sbQuery.append(" \n          LEFT JOIN (SELECT * FROM Configuracion_Precio WHERE Nivel='Familia') AS FAMILY ON FAMILY.Tipo = CONF.Tipo AND FAMILY.Subtipo=CONF.Subtipo AND ");
			sbQuery.append(" \n          FAMILY.Control=CONF.Control AND FAMILY.FK01_Proveedor= CONF.FK01_Proveedor AND FAMILY.Industria=CONF.Industria) AS CONF ON CONF.idConfig = cp.PK_Configuracion_Precio ");
			sbQuery.append(" \n LEFT JOIN(SELECT PROD.idProducto,PROD.Proveedor, PROD.FK01_Configuracion_Precio, PROD.Costo,((((SUM(CPP.CostoNuevo - CPP.CostoAnTErior))/nullif(COUNT(CPP.CostoNuevo),0))*100 )/nullif((CPP.CostoNuevo ");
			sbQuery.append(" \n         -((SUM(CPP.CostoNuevo - CPP.CostoAnTErior))/COUNT(CPP.CostoNuevo))),0)) AS PORCIENTO   ");
			sbQuery.append(" \n           FROM Productos AS PROD             LEFT JOIN(SELECT MAX (PK_CambioPrecioProducto) ID, FK01_Producto FROM CambioPrecioProducto GROUP BY FK01_Producto) AS ID ON ID.FK01_Producto =PROD.idProducto ");
			sbQuery.append(" \n           LEFT JOIN(SELECT * FROM CambioPrecioProducto) AS CPP ON CPP.PK_CambioPrecioProducto = ID.ID    ");
			sbQuery.append(" \n           GROUP BY CPP.CostoNuevo,PROD.Proveedor,PROD.FK01_Configuracion_Precio, PROD.Costo,PROD.idProducto) ");
			sbQuery.append(" \n           AS PORC ON PORC.idProducto = PROD.idProducto  ");
			sbQuery.append(" \n LEFT JOIN (SELECT * FROM Cliente_ConfiguracionPrecio) AS CliCP ON CliCP.AK_ClienteConfigPrecio = CPROD1.FK07_CliProducto AND CliCP.FK01_Cliente = @idCliente  "); 
			sbQuery.append(" \n LEFT JOIN (SELECT * FROM Cliente_ConfiguracionPrecio) AS CliCP1 ON CliCP1.AK_ClienteConfigPrecio = CPROD1.FK09_CliClasificacion AND CliCP1.FK01_Cliente = @idCliente");
			sbQuery.append(" \n LEFT JOIN (SELECT * FROM Cliente_ConfiguracionPrecio) AS CliCP2 ON CliCP2.AK_ClienteConfigPrecio = CPROD1.FK06_CliCosto  AND CliCP2.FK01_Cliente = @idCliente ");
			sbQuery.append(" \n LEFT JOIN (SELECT * FROM Cliente_ConfiguracionPrecio) AS CliCP3 ON CliCP3.AK_ClienteConfigPrecio = CPROD1.FK05_CliFamilia AND CliCP3.FK01_Cliente = @idCliente  ");
			sbQuery.append(" \n LEFT JOIN(SELECT * FROM Licencia) AS LICE ON LICE.FK01_Proveedor = CP.FK01_Proveedor AND LICE.Tipo = PROD.DepositarioInternacional ");
			sbQuery.append(" \n LEFT JOIN (SELECT * FROM CostoFactor_Licencia) CFL ON CFL.FK01_CostoFactor = CF.PK_Costo_Factor AND CFL.Licencia = PROD.DepositarioInternacional");
			sbQuery.append(" \n	LEFT JOIN ( SELECT Mon.* FROM Monedas Mon ");
			sbQuery.append(" \n		      INNER JOIN (SELECT MAX(Fecha) Fecha FROM Monedas WHERE CAST(Fecha as Date) <= CAST(GETDATE() as DATE) ) M ON M.Fecha = Mon.Fecha) Moneda on 1=1");
			sbQuery.append(" \n LEFT JOIN (SELECT * FROM LAA_Concepto) AS LAAC ON LAAC.PK_AAConcepto = COALESCE(CliCP.Fk06_LAAConcepto,CliCP1.Fk06_LAAConcepto, CliCP2.Fk06_LAAConcepto, CliCP3.Fk06_LAAConcepto,CF.FK03_LAAConcepto)");
			sbQuery.append(" \n LEFT JOIN(SELECT * FROM Tiempo_Entrega) AS TE ON TE.PK_Tiempo_Entrega = COALESCE(CliCP.FK03_TiempoEntrega,CliCP1.FK03_TiempoEntrega,CliCP2.FK03_TiempoEntrega,CliCP3.FK03_TiempoEntrega , CP.FK03_Tiempo_Entrega)   ");
			sbQuery.append(" \n WHERE PROD.Vigente = 1 and cp.PK_Configuracion_Precio is not null \n");

			if(idProveedor > 0)
				sbQuery.append("AND CP.FK01_Proveedor = @idProveedor  \n");
			if (idConfigPrecio > 0) 
				sbQuery.append("AND CPROD1.FK02_ConfFamilia = @idConfigPrecio \n");
			if (!idProducto.equals("") && !idProducto.equals("0") )
			{
				if (idProducto.contains(","))
					sbQuery.append("AND CPROD1.FK01_Producto in (").append(idProducto).append(") ");
				else
					sbQuery.append("AND CPROD1.FK01_Producto in (@idProducto) ");
			}
			if (configuracion > 0){
				if (nivel.equals("Costo"))
					sbQuery.append("AND CPROD1.FK06_CliCosto = @idConfiguracion ");
				else if (nivel.equals("Clasificacion"))
					sbQuery.append("AND CPROD1.FK09_CliClasificacion = @idConfiguracion ");
				else if (nivel.equals("Producto"))
					sbQuery.append("AND CPROD1.FK07_CliProducto = @idConfiguracion ");
				else if(nivel.equals("Familia"))
					sbQuery.append("AND CPROD1.FK02_ConfFamilia = @idConfiguracion \n");

			}
			sbQuery.append(" \n ORDER BY Tipo,SubTipo,Control ASC");
			//			logger.info("QUERY ORGIINAL:  "+sbQuery.toString());			

			List<ConfiguracionPrecioProducto> list = super.jdbcTemplate.query(sbQuery.toString(),new ConfiguracionPrecioMontoClienteRowMapper());
			String columnaClienteConf = "";
			for (ConfiguracionPrecioProducto configuracionPrecio : list) {

				if (!configuracionPrecio.getNivelConfiguracionCliente().isEmpty()) {					
					if (configuracionPrecio.getNivelConfiguracionCliente().equalsIgnoreCase("Producto")) {
						columnaClienteConf = "FK07_CliProducto";
					} else if (configuracionPrecio.getNivelConfiguracionCliente().equalsIgnoreCase("Clasificacion")) {
						columnaClienteConf = "FK09_CliClasificacion";
					} else if (configuracionPrecio.getNivelConfiguracionCliente().equalsIgnoreCase("Costo")) {
						columnaClienteConf = "FK06_CliCosto";
					} else if (configuracionPrecio.getNivelConfiguracionCliente().equalsIgnoreCase("Familia")) {
						columnaClienteConf = "FK05_CliFamilia";
					}
					sbQuery = new StringBuilder("");
					sbQuery.append(" \n SELECT PK_Cliente_Tiempo_Entrega_Ruta AS PK_Tiempo_Entrega_Ruta,FK01_ClienteConfiguracionPrecio AS FK01_Configuracion_Precio,Ruta,RequierePermiso_Existe,RequierePermiso_NoExiste,RequierePermiso_No, FactorFlexibilidad, Prod.Disponibilidad, Prod.TEntrega ");
					sbQuery.append(" \n FROM ConfiguracionPrecio_Producto ");
					sbQuery.append(" \n LEFT JOIN Cliente_ConfiguracionPrecio ON ConfiguracionPrecio_Producto." + columnaClienteConf + "=Cliente_ConfiguracionPrecio.AK_ClienteConfigPrecio ");
					sbQuery.append(" \n LEFT JOIN Cliente_Tiempo_Entrega_Ruta ON Cliente_Tiempo_Entrega_Ruta.FK01_ClienteConfiguracionPrecio=Cliente_ConfiguracionPrecio.PK_ClienteConfiguracionPrecio ");
					sbQuery.append(" \n LEFT JOIN Productos AS Prod ON Prod.idProducto=").append(configuracionPrecio.getProducto().getIdProducto()).append(" ");
					sbQuery.append(" \n WHERE  PK_ConfigPrecio_Producto=").append(configuracionPrecio.getIdConfiguracionPrecioProducto()).append(" AND Cliente_ConfiguracionPrecio.FK01_Cliente=").append(idCliente).append(" AND PK_Cliente_Tiempo_Entrega_Ruta IS NOT NULL");

					List<TiempoEntrega> listTE = super.jdbcTemplate.query(sbQuery.toString(),new TiempoEntregaRutaProveedorRowMapper());
					configuracionPrecio.setTiempoEntregaRuta(listTE);

				} else {										
					sbQuery = new StringBuilder("");
					sbQuery.append("SELECT TER.PK_Tiempo_Entrega_Ruta, TER.FK01_Configuracion_Precio, TER.Ruta, TER.RequierePermiso_Existe, TER.RequierePermiso_NoExiste, TER.RequierePermiso_No, TER.FactorFlexibilidad, Prod.Disponibilidad, Prod.TEntrega ");
					sbQuery.append("FROM Tiempo_Entrega_Ruta AS TER LEFT JOIN Productos AS Prod ON Prod.idProducto=").append(configuracionPrecio.getProducto().getIdProducto()).append(" ");
					sbQuery.append("WHERE TER.FK01_Configuracion_Precio=").append(configuracionPrecio.getIdConfiguracion());

					List<TiempoEntrega> listTE = super.jdbcTemplate.query(sbQuery.toString(),new TiempoEntregaRutaProveedorRowMapper());
					configuracionPrecio.setTiempoEntregaRuta(listTE);
				}
			}
			return list;

		} catch (Exception e) {
			log.info("Error:" + e.getMessage());
			return null;
		}
	}

	@Override
	public Boolean existConfiguracionCliente(Long idConfiguracionPProducto,
			Long idCliente, String colNivelConfigPrecio) throws ProquifaNetException {
		try {
			String sql = "SELECT COUNT(*) as Existe " +
					" FROM ConfiguracionPrecio_Producto as CPP, Cliente_ConfiguracionPrecio as CCP" +
					" WHERE CPP.PK_ConfigPrecio_Producto = " + idConfiguracionPProducto + 
					" AND CCP.AK_ClienteConfigPrecio = CPP." + colNivelConfigPrecio + " AND CCP.FK01_Cliente = " + idCliente;

			log.info(sql);

			int r = super.queryForInt(sql);

			if(r > 0){
				return true;
			}else{
				return false;
			}

		} catch (Exception e) {
			log.info("Error:" + e.getMessage());
			return false;
		}
	}

	@Override
	public Boolean existConfiguracionClienteTemporal(Long idConfiguracionPProducto,
			Long idCliente, String colNivelConfigPrecio) throws ProquifaNetException {
		try {
			String sql = "SELECT COUNT(*) as Existe " +
					" FROM ConfiguracionPrecio_Producto as CPP, Cliente_ConfiguracionPrecio as CCP" +
					" WHERE CCP.Caduca is not null and CPP.PK_ConfigPrecio_Producto = " + idConfiguracionPProducto + 
					" AND CCP.AK_ClienteConfigPrecio = CPP." + colNivelConfigPrecio + " AND CCP.FK01_Cliente = " + idCliente;

			log.info(sql);

			int r = super.queryForInt(sql);

			if(r > 0){
				return true;
			}else{
				return false;
			}

		} catch (Exception e) {
			log.info("Error:" + e.getMessage());
			return false;
		}
	}

	@Override
	public Integer updateConfiguracionClienteTemporal(
			ParametrosOfertaCliente p, String colNivelConfigPrecio, Date caduca)
					throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("factor", p.getFactor());
			map.put("costoFijo", p.getCostoFijo());
			map.put("caduca", caduca);
			map.put("compuestaCostoF", p.getCompuestaCostoF());
			map.put("compuestaFactorU", p.getCompuestaFactorU());
			map.put("precioListaAnterior", p.getPrecioListaAnterior());
			map.put("idConfigPrecioProd", p.getIdConfigPrecioProd());
			map.put("idCliente", p.getIdCliente());	

			StringBuilder consulta = new StringBuilder("");
			consulta.append(" UPDATE Cliente_ConfiguracionPrecio SET FactorTemp = :factor, CostoFijoTemp = :costoFijo, Caduca = :caduca, CompuestaCostoFTemp = :compuestaCostoF, CompuestaFactorUTemp = :compuestaFactorU, PLAnteriorTemp = :precioListaAnterior");
			consulta.append(" WHERE (SELECT ")
			.append(colNivelConfigPrecio)
			.append(" FROM ConfiguracionPrecio_Producto WHERE PK_ConfigPrecio_Producto = :idConfigPrecioProd) = AK_ClienteConfigPrecio");
			consulta.append(" AND FK01_Cliente = :idCliente");

			return jdbcTemplate.update(consulta.toString(), map);

		} catch (Exception e) {
			log.info(e.getMessage());
			return 0;
		}
	}

	@Override
	public Boolean insertFactorClienteTemporal(ParametrosOfertaCliente parametros,
			String colNivelConfigPrecio,  Date caducidad)
					throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// Solo se inserta el factor temporal, costo fijo temporal y la fecha de caducidad.
			// NO es necesario insertar lo demas  por que la va a tomar de configuraciones hacia arriba

			map.put("caducidad", caducidad);

			int f = 0;
			if(parametros.getCompuestaCostoF()){
				f=1;
			}else{
				f=0;
			}

			int u = 0;
			if(parametros.getCompuestaFactorU()){
				u=1;
			}else{
				u=0;
			}

			int plat = 0;
			if(parametros.getPrecioListaAnterior()){
				plat=1;
			}else{
				plat=0;
			}

			String consulta = 	""
					+ " INSERT INTO Cliente_ConfiguracionPrecio (AK_ClienteConfigPrecio, FK01_Cliente, NivelConfigPrecio, FactorTemp, Tipo, CostoFijoTemp, CompuestaCostoFTemp, CompuestaFactorUTemp, PLAnteriorTemp, Caduca,FUA) "
					+ " VALUES ((SELECT "+ colNivelConfigPrecio + " FROM ConfiguracionPrecio_Producto WHERE PK_ConfigPrecio_Producto = " + parametros.getIdConfigPrecioProd() + "), "
					+ parametros.getIdCliente() + ", '" + parametros.getNivelConfigPrecio() + "', " + parametros.getFactor() + ", '" + parametros.getTipoNivelIngreso() + "', " + parametros.getCostoFijo() + ", " 
					+ f + "," + u + ","+ plat +", :caducidad, GETDATE() )"
					+ " UPDATE Cliente_ConfiguracionPrecio SET AK_ClienteConfigPrecio = COALESCE((SELECT " + colNivelConfigPrecio + " FROM ConfiguracionPrecio_Producto"
					+ " WHERE PK_ConfigPrecio_Producto = " + parametros.getIdConfigPrecioProd() + "),"
					+ " (SELECT IDENT_CURRENT ('Cliente_ConfiguracionPrecio')))"
					+ " WHERE FK01_Cliente = " + parametros.getIdCliente() + " AND NivelConfigPrecio = '" + parametros.getNivelConfigPrecio() + "' AND AK_ClienteConfigPrecio is null" ;
			log.info(consulta);


			if (parametros.getNivelConfigPrecio().equals("Familia")) {

				consulta+= 	" UPDATE ConfiguracionPrecio_Producto SET FK05_CliFamilia = COALESCE((SELECT FK05_CliFamilia FROM ConfiguracionPrecio_Producto "
						+ " WHERE PK_ConfigPrecio_Producto = " + parametros.getIdConfigPrecioProd()  + "),(SELECT IDENT_CURRENT ('Cliente_ConfiguracionPrecio')))"
						+ " WHERE FK02_ConfFamilia = (SELECT FK02_ConfFamilia FROM ConfiguracionPrecio_Producto WHERE PK_ConfigPrecio_Producto = " + parametros.getIdConfigPrecioProd()  + ") ";

			} else if (parametros.getNivelConfigPrecio().equals("Costo")) {

				consulta+=	" UPDATE ConfiguracionPrecio_Producto SET FK06_CliCosto = COALESCE((SELECT FK06_CliCosto FROM ConfiguracionPrecio_Producto "
						+ " WHERE PK_ConfigPrecio_Producto = " + parametros.getIdConfigPrecioProd() + "),(SELECT IDENT_CURRENT ('Cliente_ConfiguracionPrecio')))"
						+ " WHERE FK02_ConfFamilia = (SELECT FK02_ConfFamilia FROM ConfiguracionPrecio_Producto WHERE PK_ConfigPrecio_Producto = " + parametros.getIdConfigPrecioProd() + ")"
						+ " AND FK01_Producto IN ( SELECT idProducto FROM Productos WHERE FK03_Categoria_PrecioLista = ( "
						+ " SELECT FK03_Categoria_PrecioLista FROM Productos WHERE idProducto = (SELECT FK01_Producto FROM ConfiguracionPrecio_Producto "
						+ " WHERE PK_ConfigPrecio_Producto = " + parametros.getIdConfigPrecioProd()  + ")))";

			} else if (parametros.getNivelConfigPrecio().equals("Clasificacion")) {

				consulta+=	" UPDATE ConfiguracionPrecio_Producto SET FK09_CliClasificacion = COALESCE((SELECT FK09_CliClasificacion "
						+ "FROM ConfiguracionPrecio_Producto WHERE PK_ConfigPrecio_Producto = " + parametros.getIdConfigPrecioProd() 
						+ "),(SELECT IDENT_CURRENT ('Cliente_ConfiguracionPrecio')))"
						+ " WHERE FK02_ConfFamilia = (SELECT FK02_ConfFamilia FROM ConfiguracionPrecio_Producto "
						+ " WHERE PK_ConfigPrecio_Producto = " + parametros.getIdConfigPrecioProd() 
						+ ") AND FK01_Producto IN ( SELECT idProducto FROM Productos WHERE FK04_Clasificacion_ConfiguracionPrecio = ( "
						+ " SELECT FK04_Clasificacion_ConfiguracionPrecio FROM Productos "
						+ " WHERE idProducto = (SELECT FK01_Producto FROM ConfiguracionPrecio_Producto WHERE PK_ConfigPrecio_Producto = " + parametros.getIdConfigPrecioProd() + ")))";

			} else if (parametros.getNivelConfigPrecio().equals("Producto")) {
				consulta+=	" UPDATE ConfiguracionPrecio_Producto SET FK07_CliProducto = COALESCE(("
						+ " SELECT FK07_CliProducto FROM ConfiguracionPrecio_Producto WHERE PK_ConfigPrecio_Producto = " + parametros.getIdConfigPrecioProd() 
						+ "),(SELECT top 1 AK_ClienteConfigPrecio from Cliente_ConfiguracionPrecio where FK01_Cliente = " + parametros.getIdCliente() + " and NivelConfigPrecio = '" + parametros.getNivelConfigPrecio() + "' order by FUA desc)) WHERE PK_ConfigPrecio_Producto = " + parametros.getIdConfigPrecioProd();

			}

			super.jdbcTemplate.update(consulta.toString(), map);
			//			super.jdbcTemplate.update(consulta.toString());

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public Integer updateFactorCliente(ParametrosOfertaCliente parametros,
			String colNivelConfigPrecio, Long idCliente, String nivelIngreso)
					throws ProquifaNetException {
		try {

			// catalogoClienteDAO.updateFactorCliente(parametros.getIdConfigPrecioProd(),
			// c.getIdCliente(), c.getNivelIngreso(), null, colCliente, null,
			// null, null, null);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("p1", parametros.getFactor());
			map.put("p2", nivelIngreso);
			map.put("p3", parametros.getCostoFijo());
			map.put("p4", parametros.getPrecioListaAnterior());
			map.put("p5", parametros.getCompuestaCostoF());
			map.put("p6", parametros.getCompuestaFactorU());
			map.put("p7", parametros.getIdAgente());
			map.put("p8", parametros.getIdLugar());
			map.put("p9", parametros.getIdConcepto());
			map.put("p10", parametros.getIdConfigPrecioProd());
			map.put("p11", idCliente);

			Map<String, Object> map2 = new HashMap<String, Object>();
			map2.put("p1", null);
			map2.put("p2", null);
			map2.put("p3", null);
			map2.put("p4", 0);
			map2.put("p5", null);
			map2.put("p6", null);
			map2.put("p7", null);
			map2.put("p8", null);
			map2.put("p9", null);
			map2.put("p10", parametros.getIdConfigPrecioProd());
			map2.put("p11", idCliente);


			Map<String, Object> map3 = new HashMap<String, Object>();
			map3.put("p1", null);
			map3.put("p2", null);
			map3.put("p3", null);
			map3.put("p4", parametros.getIdConfigPrecioProd());
			map3.put("p5", idCliente);


			StringBuilder consulta = new StringBuilder("");

			consulta.append(" UPDATE Cliente_ConfiguracionPrecio SET Factor = :p1, Tipo= :p2, CostoFijo = :p3, PrecioListaAnterior = :p4,"
					+ " CompuestaCostoF = :p5, CompuestaFactorU = :p6, FUA = GETDATE(), FK04_AgenteAduanal = :p7, FK05_LugarAgenteAduanal = :p8, Fk06_LAAConcepto = :p9, "
					+ " FactorTemp=null, CostoFijoTemp=null, Caduca=null");
			consulta.append(" WHERE (SELECT ")
			.append(colNivelConfigPrecio)
			.append(" FROM ConfiguracionPrecio_Producto WHERE PK_ConfigPrecio_Producto = :p10) = AK_ClienteConfigPrecio");
			consulta.append(" AND FK01_Cliente = :p11");

			StringBuilder consulta2 = new StringBuilder("");
			consulta2.append(" UPDATE Cliente_ConfiguracionPrecio SET FactorTemp=:p1, CostoFijoTemp=:p2, Caduca=:p3");
			consulta2.append(" WHERE (SELECT ").append(colNivelConfigPrecio)
			.append(" FROM ConfiguracionPrecio_Producto WHERE PK_ConfigPrecio_Producto = :p4) = AK_ClienteConfigPrecio");
			consulta2.append(" AND FK01_Cliente = :p5");


			// //logger.info(consulta.toString());
			if (parametros.getRestablecer()) {
				if(parametros.getTemporal()){
					log.info(consulta2.toString());
					return jdbcTemplate.update(consulta2.toString(), map3);
				}
				log.info(consulta.toString());
				return jdbcTemplate.update(consulta.toString(), map2);
			} else {
				log.info(consulta.toString());
				return jdbcTemplate.update(consulta.toString(), map);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public Cliente obtenerClienteXId(Long idCliente, List<NivelIngreso> niveles)
			throws ProquifaNetException {

		try {
			f = new Funcion();
			String sql = f.sqlLimitesNivelIngreso(niveles, "NIVEL.VentasUSD");
			StringBuilder sbQuery = new StringBuilder("");

			sbQuery.append("SELECT C.*,COUNT(CON.idContacto) TotalContactos ,  \n");
			sbQuery.append("  STUFF((  \n");
			sbQuery.append(" SELECT ',' + CAST(PP.NombreCartera AS VARCHAR(500)) FROM ( \n");
			sbQuery.append("	SELECT * FROM Clientes Cli \n");
			sbQuery.append("LEFT JOIN Cliente_Cartera CliCart ON CliCart.FK01_Cliente = Cli.Clave \n");
			sbQuery.append(" LEFT JOIN (select Nombre NombreCartera, PK_Cartera from Cartera) Cart ON Cart.PK_Cartera = CliCart.FK02_Cartera ) PP \n");
			sbQuery.append("	WHERE PP.Clave = C.Clave   FOR XML PATH('') \n");
			sbQuery.append("	 ), 1, 1, '') NombresCarteras from ( \n");
			sbQuery.append("SELECT C.ComentaCredito, C.FElectronica, C.Clave, COALESCE(C.Nombre, '') Nombre, COALESCE(C.Rol, '') Rol, COALESCE(C.Sector, '') Sector, COALESCE(C.Industria, '') Industria,COALESCE (TIENE_CART.carteras, 0) totalCarteras, COALESCE (CONT.NumContratos,0) totalContratos, \n");
			sbQuery.append("CASE WHEN Sector = 'PUBLICO' THEN 'Publico' ELSE CASE WHEN Rol = 'DISTRIBUIDOR' THEN 'Distribuidor' ELSE \n");
			sbQuery.append(sql).append(" END END NIVEL, \n");
			sbQuery.append("COALESCE(C.Estado, '') Estado, COALESCE(C.Pais, '') Pais, COALESCE(C.RSocial, '') RSocial, COALESCE(C.FUActual, '') FUActual, COALESCE(ESAC.Clave, 0) ClaveESAC, COALESCE(C.Vendedor, '') ESAC, COALESCE(EV.Clave,0) ClaveEV, \n");
			sbQuery.append("COALESCE(EV.Usuario, '') EV,COALESCE(CO.Usuario, '') Nombre_cobrador, C.NivelPrecio, COALESCE(C.ObjetivoCrecimientoFundamental, '') ObjetivoCrecimientoFundamental, COALESCE(C.ObjetivoCrecimiento, '') ObjetivoCrecimiento, COALESCE(C.CPago, '') CPago, COALESCE(CAST(C.Comenta as VARCHAR(MAX)), '') Comenta, \n");
			sbQuery.append("CASE WHEN C.LimiteCredito = '' THEN '0' ELSE COALESCE(C.LimiteCredito, '0') END LimiteCredito, CASE WHEN C.LineaCredito = '' THEN '0' ELSE COALESCE(C.LineaCredito,'0') END LineaCredito, COALESCE(C.CURP, '') RFC, \n");
			sbQuery.append("COALESCE(C.MonedaFactura, '') MonedaFactura, COALESCE(C.Factura, '') Factura, COALESCE(C.RSPais, '') RSPais, COALESCE(C.RSEstado, '') RSEstado, COALESCE(C.RSCalle, '') RSCalle, COALESCE(C.RSDel, '') RSDel, COALESCE(C.RSCP, '') RSCP, COALESCE(C.FacturaPortal,0) FacturaPortal, COALESCE(C.EntregayRevision, 0) EntregayRevision, COALESCE(C.Parciales, 0) Parciales, C.Destino , \n");
			sbQuery.append("COALESCE(C.MailFElectronica, '') MailFElectronica,  COALESCE(CAST(C.ComentaFacturacion as VARCHAR(MAX)), '') ComentaFacturacion, C.Habilitado, COALESCE(C.FK01_EV,0) FK01_EV, C.imagen, C.cobrador,C.Importancia, NombreCorporativo , idCorporativo,  \n");
			sbQuery.append("VC_UsoCFDI.valor as UsoCFDI, VC_MetodoPago.valor AS MetodoPago ");
			sbQuery.append("FROM Clientes C \n");
			sbQuery.append("LEFT JOIN (select COUNT(1) as carteras, FK01_Cliente from Cliente_Cartera GROUP BY FK01_Cliente) as TIENE_CART ON TIENE_CART.FK01_Cliente = C.Clave \n");
			sbQuery.append("LEFT JOIN ( select PK_Corporativo idCorporativo, Nombre NombreCorporativo from corporativo) AS CORP ON CORP.idCorporativo = C.FK02_Corporativo  \n");
			sbQuery.append("LEFT JOIN (SELECT COALESCE(SUM(CASE WHEN F.Moneda = 'Dolares' OR F.Moneda ='USD' THEN F.Importe \n");
			sbQuery.append("WHEN F.Moneda = 'EUR' OR F.Moneda = 'Euros' THEN F.Importe * M.EDolar \n");
			sbQuery.append("WHEN F.Moneda = 'Pesos' OR F.Moneda = 'M.N.' THEN F.Importe/CASE WHEN F.TCambio = 0 OR F.TCAMBIO IS NULL THEN 1 ELSE F.TCAMBIO END END ), 0) VentasUSD, Cliente FROM Facturas F \n");
			sbQuery.append("LEFT JOIN Monedas M ON M.Fecha = F.Fecha \n");
			sbQuery.append("WHERE YEAR(F.Fecha) = DATEPART(year,GETDATE()) - 1 AND F.Estado NOT LIKE 'Cancela%' GROUP BY F.Cliente) NIVEL ON NIVEL.Cliente = C.Clave \n");
			sbQuery.append("LEFT JOIN (SELECT (COALESCE(CO.Cotizaciones,0) + COALESCE(PED.Pedidos,0)) noOperaciones, Clave FROM Clientes C \n");
			sbQuery.append("LEFT JOIN (SELECT COUNT(1) Cotizaciones, Cliente FROM Cotizas WHERE YEAR(Fecha) = (YEAR(GETDATE()) - 1) GROUP BY Cliente) CO ON CO.Cliente = C.Nombre \n");
			sbQuery.append("LEFT JOIN (SELECT COUNT(1) Pedidos, idCliente FROM Pedidos WHERE YEAR(FPedido) = (YEAR(GETDATE()) - 1) \n");
			sbQuery.append("GROUP BY idCliente) PED ON PED.idCliente = C.Clave) OPER ON OPER.Clave = C.Clave \n");
			sbQuery.append("LEFT JOIN ValorCombo AS VC_UsoCFDI ON VC_UsoCFDI.PK_Folio = C.FK04_UsoCFDI \n");
			sbQuery.append("LEFT JOIN ValorCombo AS VC_MetodoPago ON VC_MetodoPago.PK_Folio = C.FK05_MetodoPago \n");
			sbQuery.append("LEFT JOIN Empleados EV ON EV.Clave = C.FK01_EV \n");
			sbQuery.append("LEFT JOIN Empleados ESAC ON ESAC.Usuario = C.Vendedor \n");
			sbQuery.append("LEFT JOIN Empleados CO ON CO.Clave = C.Cobrador \n");
			sbQuery.append("LEFT JOIN (SELECT COUNT(1) NumContratos, FK01_Cliente FROM Contratos WHERE Finalizado = 1 GROUP BY FK01_Cliente) CONT ON CONT.FK01_Cliente = C.Clave \n");
			sbQuery.append("WHERE C.Clave = ").append(idCliente).append(") c \n");
			sbQuery.append("LEFT JOIN (SELECT * FROM Contactos WHERE Habilitado = 1) CON ON C.Clave = CON.FK02_Cliente \n");
			sbQuery.append("GROUP BY C.CPago,C.Clave,C.ClaveESAC,C.ClaveEV,C.Cobrador,C.Comenta,C.ComentaCredito,C.ComentaFacturacion,C.ESAC,C.EV,c.Nombre_cobrador,C.Estado,C.FElectronica,C.FK01_EV,C.FUActual,C.Factura,C.FacturaPortal, C.totalCarteras, C.totalContratos, \n");
			sbQuery.append("C.Habilitado,C.Importancia,C.Industria,C.LimiteCredito,C.LineaCredito,C.MailFElectronica,C.MonedaFactura,C.NIVEL,C.NivelPrecio,C.Nombre,C.ObjetivoCrecimiento,C.ObjetivoCrecimientoFundamental,C.Pais, \n");
			sbQuery.append("C.RFC,C.RSCP,C.RSCalle,C.RSDel,C.RSEstado,C.RSPais,C.RSocial,C.Rol,C.Sector,C.idCorporativo,C.imagen,C.nombreCorporativo, C.EntregayRevision, C.Parciales, C.Destino, UsoCFDI, MetodoPago  \n");
			sbQuery.append("ORDER BY C.Clave");

			log.info(sbQuery.toString());
			ClienteCatalogoRowMapper mapper = new ClienteCatalogoRowMapper();
			super.jdbcTemplate.query(sbQuery.toString(), mapper);
			Cliente cliente = mapper.getCliente();

			sbQuery = new StringBuilder(
					"SELECT PK_Comentario,FK01_Cliente,Seccion,Tema,Contenido \n");
			sbQuery.append("FROM Comentarios \n");
			sbQuery.append("WHERE FK01_Cliente=")
			.append(cliente.getIdCliente()).append(" \n");

			@SuppressWarnings("unchecked")
			List<Comentario> listComentarios = super.jdbcTemplate.query(
					sbQuery.toString(), new ComentarioClienteRowMapper());
			cliente.setListaComentarios(listComentarios);

			return cliente;
		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}

	}

	@Override
	public Long getClienteConfiguracionPrecio(Long idConfigPrecioProd,
			Long idCliente, String colCliente) throws ProquifaNetException {
		try {

			StringBuilder query = new StringBuilder(
					" SELECT top 1 Cliente_ConfiguracionPrecio.PK_ClienteConfiguracionPrecio ");
			query.append(" FROM ConfiguracionPrecio_Producto LEFT JOIN Cliente_ConfiguracionPrecio ON ConfiguracionPrecio_Producto."
					+ colCliente
					+ "=Cliente_ConfiguracionPrecio.AK_ClienteConfigPrecio ");
			query.append(" WHERE  PK_ConfigPrecio_Producto= " + idConfigPrecioProd + " AND Cliente_ConfiguracionPrecio.FK01_Cliente=  " + idCliente + " order by Cliente_ConfiguracionPrecio.FUA desc");

			log.info(query.toString());

			Long idClienteConfiguracionPrecio = super.queryForLong(query.toString());
			return idClienteConfiguracionPrecio;
		} catch (Exception e) {
			log.info(" SELECT top 1 Cliente_ConfiguracionPrecio.PK_ClienteConfiguracionPrecio FROM ConfiguracionPrecio_Producto LEFT JOIN Cliente_ConfiguracionPrecio ON ConfiguracionPrecio_Producto."
					+ colCliente
					+ "=Cliente_ConfiguracionPrecio.AK_ClienteConfigPrecio  WHERE  PK_ConfigPrecio_Producto= " + idConfigPrecioProd + " AND Cliente_ConfiguracionPrecio.FK01_Cliente=  " + idCliente + "order by Cliente_ConfiguracionPrecio.FUA desc"
					);
			log.info(e.getMessage());
			return 0L;
		}

	}

	@Override
	public Boolean eliminarRegistroTiempoEntregaRuta(Long idConfiguracion)
			throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			super.jdbcTemplate
			.update(" DELETE Cliente_Tiempo_Entrega_Ruta WHERE FK01_ClienteConfiguracionPrecio="
					+ idConfiguracion + "", map);
			return true;
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

	@Override
	public Long getCantidadTiemposEntrega(Long idConfiguracionPrecio,
			Long idCliente) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idConfiguracionPrecio", idConfiguracionPrecio);

			StringBuilder query;
			Long idClienteConfiguracionPrecio = 0L;
			if (idCliente > 0) {
				query = new StringBuilder(
						" SELECT COUNT(*) AS CANTIDAD FROM Cliente_Tiempo_Entrega_Ruta ");
				query.append(" WHERE FK01_ClienteConfiguracionPrecio = ? ");
				idClienteConfiguracionPrecio = super.queryForLong(
						query.toString(), map);
			}
			return idClienteConfiguracionPrecio;
		} catch (Exception e) {
			log.info(e.getMessage());
			return 0L;
		}
	}

	@Override
	public Boolean insertFactorCliente(ParametrosOfertaCliente parametros,
			String colNivelConfigPrecio, Long idCliente, String nivelIngreso)
					throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// Boolean insertFactorCliente(Long idConfigPrecioProducto, Long
			// idCliente, String tipoNivelIngreso,
			// Double factor, String colNivelConfigPrecio, String
			// nivelConfigPrecio,
			// Long idAgente, Long idLugar, Long idConcepto, Long idTiempo)
			// throws ProquifaNetException;

			int f = 0;
			if(parametros.getCompuestaCostoF()){
				f=1;
			}else{
				f=0;
			}

			int u = 0;
			if(parametros.getCompuestaFactorU()){
				u=1;
			}else{
				u=0;
			}
			int pa = 0;
			if(parametros.getPrecioListaAnterior()){
				pa = 1;
			}else{
				pa = 0;
			}

			String consulta = 	""
					+ " INSERT INTO Cliente_ConfiguracionPrecio (AK_ClienteConfigPrecio, FK01_Cliente, NivelConfigPrecio, PrecioListaAnterior, Factor, Tipo, CostoFijo, "
					+ " CompuestaCostoF, CompuestaFactorU, FUA, FK04_AgenteAduanal, FK05_LugarAgenteAduanal, Fk06_LAAConcepto) "
					+ " VALUES ((SELECT "+ colNivelConfigPrecio + " FROM ConfiguracionPrecio_Producto WHERE PK_ConfigPrecio_Producto = " + parametros.getIdConfigPrecioProd() + "), "
					+ idCliente + ", '" + parametros.getNivelConfigPrecio() + "', "+ pa +"," + parametros.getFactor() + ", '" + parametros.getTipoNivelIngreso() + "', " + parametros.getCostoFijo() + ", " 
					+ f+ ", " + u
					+ ", GETDATE() , " + parametros.getIdAgente() + " , " + parametros.getIdLugar() + " , " + parametros.getIdConcepto() + ")"
					+ " UPDATE Cliente_ConfiguracionPrecio SET AK_ClienteConfigPrecio = COALESCE((SELECT " + colNivelConfigPrecio + " FROM ConfiguracionPrecio_Producto"
					+ " WHERE PK_ConfigPrecio_Producto = " + parametros.getIdConfigPrecioProd() + "),"
					+ " (SELECT IDENT_CURRENT ('Cliente_ConfiguracionPrecio')))"
					+ " WHERE FK01_Cliente = " + idCliente + " AND NivelConfigPrecio = '" + parametros.getNivelConfigPrecio() + "' AND AK_ClienteConfigPrecio is null" ;
			//								+ " WHERE PK_ClienteConfiguracionPrecio = COALESCE((SELECT " + colNivelConfigPrecio + " FROM ConfiguracionPrecio_Producto"
			//								+ " WHERE PK_ConfigPrecio_Producto = " + parametros.getIdConfigPrecioProd() + "),(SELECT IDENT_CURRENT ('Cliente_ConfiguracionPrecio')))";

			if (parametros.getNivelConfigPrecio().equals("Familia")) {

				consulta+= 	" UPDATE ConfiguracionPrecio_Producto SET FK05_CliFamilia = COALESCE((SELECT FK05_CliFamilia FROM ConfiguracionPrecio_Producto "
						+ " WHERE PK_ConfigPrecio_Producto = " + parametros.getIdConfigPrecioProd()  + "),(SELECT IDENT_CURRENT ('Cliente_ConfiguracionPrecio')))"
						+ " WHERE FK02_ConfFamilia = (SELECT FK02_ConfFamilia FROM ConfiguracionPrecio_Producto WHERE PK_ConfigPrecio_Producto = " + parametros.getIdConfigPrecioProd()  + ") ";

			} else if (parametros.getNivelConfigPrecio().equals("Costo")) {

				consulta+=	" UPDATE ConfiguracionPrecio_Producto SET FK06_CliCosto = COALESCE((SELECT FK06_CliCosto FROM ConfiguracionPrecio_Producto "
						+ " WHERE PK_ConfigPrecio_Producto = " + parametros.getIdConfigPrecioProd() + "),(SELECT IDENT_CURRENT ('Cliente_ConfiguracionPrecio')))"
						+ " WHERE FK02_ConfFamilia = (SELECT FK02_ConfFamilia FROM ConfiguracionPrecio_Producto WHERE PK_ConfigPrecio_Producto = " + parametros.getIdConfigPrecioProd() + ")"
						+ " AND FK01_Producto IN ( SELECT idProducto FROM Productos WHERE FK03_Categoria_PrecioLista = ( "
						+ " SELECT FK03_Categoria_PrecioLista FROM Productos WHERE idProducto = (SELECT FK01_Producto FROM ConfiguracionPrecio_Producto "
						+ " WHERE PK_ConfigPrecio_Producto = " + parametros.getIdConfigPrecioProd()  + ")))";

			} else if (parametros.getNivelConfigPrecio().equals("Clasificacion")) {

				consulta+=	" UPDATE ConfiguracionPrecio_Producto SET FK09_CliClasificacion = COALESCE((SELECT FK09_CliClasificacion "
						+ "FROM ConfiguracionPrecio_Producto WHERE PK_ConfigPrecio_Producto = " + parametros.getIdConfigPrecioProd() 
						+ "),(SELECT IDENT_CURRENT ('Cliente_ConfiguracionPrecio')))"
						+ " WHERE FK02_ConfFamilia = (SELECT FK02_ConfFamilia FROM ConfiguracionPrecio_Producto "
						+ " WHERE PK_ConfigPrecio_Producto = " + parametros.getIdConfigPrecioProd() 
						+ ") AND FK01_Producto IN ( SELECT idProducto FROM Productos WHERE FK04_Clasificacion_ConfiguracionPrecio = ( "
						+ " SELECT FK04_Clasificacion_ConfiguracionPrecio FROM Productos "
						+ " WHERE idProducto = (SELECT FK01_Producto FROM ConfiguracionPrecio_Producto WHERE PK_ConfigPrecio_Producto = " + parametros.getIdConfigPrecioProd() + ")))";

			} else if (parametros.getNivelConfigPrecio().equals("Producto")) {
				consulta+=	" UPDATE ConfiguracionPrecio_Producto SET FK07_CliProducto = COALESCE(("
						+ " SELECT FK07_CliProducto FROM ConfiguracionPrecio_Producto WHERE PK_ConfigPrecio_Producto = " + parametros.getIdConfigPrecioProd() 
						+ "),(SELECT top 1 AK_ClienteConfigPrecio from Cliente_ConfiguracionPrecio where FK01_Cliente = " + idCliente + " and NivelConfigPrecio = '" + parametros.getNivelConfigPrecio() + "' order by FUA desc)) WHERE PK_ConfigPrecio_Producto = " + parametros.getIdConfigPrecioProd();
				//				+ "),(SELECT IDENT_CURRENT ('Cliente_ConfiguracionPrecio'))) WHERE PK_ConfigPrecio_Producto = " + parametros.getIdConfigPrecioProd();
			}

			//			 //logger.info(consulta.toString());

			super.jdbcTemplate.update(consulta.toString(), map);

			return true;
		} catch (Exception e) {
			log.info(e.toString());
			return false;
		}

	}

	@Override
	public FormulaPrecio getInformacionFormulaPrecioTemp(Long idProveedor,
			Long idProducto, int stock_flete, String nivel, Long idCliente, Long idConfig) throws ProquifaNetException {
		StringBuilder sql = new StringBuilder("");
		try{

			Map<String, Object> map = new HashMap<String, Object>();
			String Q = 	" 	CASE WHEN CFL.Monto is not null and CFL.Monto > 0  " +
					"					THEN CFL.Monto " +
					"				 WHEN (CFL.Monto is null or CFL.Monto < 1 ) and CFL.Porcentaje > 0 " +
					"					THEN (1 + (CFL.Porcentaje/100)) " +
					" 				else" +
					"					 (1 + ((CASE WHEN LICE.Factor = -1 OR  LICE.Factor IS NULL THEN 0 ELSE LICE.Factor /100 END))) " +
					"				END ";	

			String O = 	" (CASE WHEN CF.ValorEnAduana = 0 OR CF.ValorEnAduana IS NULL THEN 1 ELSE CF.ValorEnAduana END) ";
			String R = 	" (CASE WHEN CF.Descuento IS NULL THEN 1 ELSE (1 - (CF.Descuento/100)) END)";	
			String PrecioL = "((PROD.Costo * PROVEE.TC) * (" + R + ")) ";
			String B = 	" (CASE WHEN CF.Flete = 0 OR  CF.Flete IS NULL THEN 0 ELSE CF.Flete END )";
			String N  = 	"\n CASE WHEN CF.VALORENADUANA <= 0 OR CF.VALORENADUANA IS NULL THEN case when CF.Piezas is null or CF.Piezas = 0 then 1 else CF.Piezas end " +
					" ELSE " +
					"	CASE WHEN  (CFL.Monto is not null and CFL.Monto > 0)  THEN " +
					"		CEILING((" + O + " - " + B + ")/(" + PrecioL + " +  " + Q +"))  " +
					"	ELSE " +
					"		CEILING((" + O + " - " + B + ")/(" + PrecioL + " *  " + Q +"))  " +
					"	END " +
					" END \n";

			N = " case when " + N + " = 0 then 1 else " + N + " end ";

			String VA =    " CASE WHEN (CFL.Monto is not null and CFL.Monto > 0)  " +
					" THEN ( (" + N + " * (" + PrecioL + "  +  " + Q +") ) + " + B +") " +
					" ELSE ( (" + N + " * " + PrecioL + "  * " + Q +" ) + " + B +") END	";
			String Valor = "(" + PrecioL + ") * (" + N + ")";
			String L = 	" ROUND( (CASE WHEN (CASE WHEN LAAC.Monto = 0  OR  LAAC.Monto IS NULL THEN LAAC.Porcentaje ELSE LAAC.Monto END) IS NULL THEN 0 " +
					" ELSE CASE WHEN LAAC.Monto = 0  OR  LAAC.Monto IS NULL THEN (" + VA + ") * ((LAAC.Porcentaje)/100) ELSE LAAC.Monto END END) ,3,1)";

			String M = 	" ROUND( (CASE WHEN CF.Permiso = -1 OR  CF.Permiso IS NULL THEN 0 ELSE CF.Permiso END) ,3,1)";
			String I = 	" ROUND( (CASE WHEN CF.AlmacenDestino = -1 OR  CF.AlmacenDestino IS NULL  THEN 0 ELSE CF.AlmacenDestino END) ,3,1)";

			////logger.info("\n Cliente: " + idCliente);

			sql.append(" \n DECLARE @idProveedor AS Integer= " + idProveedor);
			sql.append(" \n DECLARE @idProducto AS INTEGER = " + idProducto);
			sql.append(" \n DECLARE @FLETE_STOCK AS INTEGER = " + stock_flete);
			sql.append(" \n DECLARE @idCliente AS Integer=" + idCliente + "\n ");
			sql.append(" \n DECLARE @idConfigFamilia AS Integer=" + idConfig + "\n ");
			sql.append(" \n SELECT DISTINCT (CASE WHEN CF.FLETE = 0 OR  CF.FLETE IS NULL THEN 0 ELSE CF.FLETE END ) B,");
			sql.append(" \n (CASE WHEN CF.Costo_Consularizacion = -1 OR  CF.Costo_Consularizacion IS NULL THEN 0 ELSE CF.Costo_Consularizacion END) C,");
			sql.append(" \n (CASE WHEN CF.Flete_Documentacion = -1 OR  CF.Flete_Documentacion IS NULL THEN 0 ELSE CF.Flete_Documentacion END) D,");
			sql.append(" \n (CASE WHEN CF.Factor_IGI = -1 OR  CF.Factor_IGI IS NULL THEN 0 ELSE CF.Factor_IGI END) J,");
			sql.append(" \n (CASE WHEN CF.FactorDTA = -1 OR  CF.FactorDTA IS NULL THEN 0 ELSE CF.FactorDTA END) K,");
			sql.append(" \n " + PrecioL  + " PRECIOLISTA,");
			sql.append(" \n CF.Descuento R,");
			sql.append(" \n " + Q  + " Q,");	
			sql.append(" \n " + O  + " O,");	
			sql.append(" \n " + N  + " N,");
			sql.append(" \n " + VA + " VA,");
			sql.append(" \n " + L  + " L,");
			sql.append(" \n " + Valor  + " Valor,");

			sql.append(" \n " + M  + " M,");
			sql.append(" \n " + I  + " I,");

			sql.append(" \n CASE WHEN CliCP2.FactorTemp is not null THEN CliCP2.FactorTemp	WHEN  CliCP3.FactorTemp is not null THEN CliCP3.FactorTemp ELSE 0  END G,");
			sql.append(" \n CASE WHEN CliCP2.CostoFijoTemp is not null THEN CliCP2.CostoFijoTemp	WHEN  CliCP3.CostoFijoTemp is not null THEN CliCP3.CostoFijoTemp ELSE 0  END F,");
			sql.append(" \n coalesce(CliCP2.CompuestaCostoF , CliCP3.CompuestaCostoF, CF.CompuestaCostoF, 1 )  CompuestaCostoF,  ");
			sql.append(" \n coalesce(CliCP2.CompuestaFactorU , CliCP3.CompuestaFactorU, CF.CompuestaFactorU, 1 )  CompuestaFactorU  ");

			sql.append(" \n FROM ConfiguracionPrecio_Producto AS CPP  ");
			sql.append(" \n LEFT JOIN(SELECT * FROM Configuracion_Precio) AS CP ON CP.PK_Configuracion_Precio = COALESCE(CPP.FK03_ConfCosto,CPP.FK02_ConfFamilia) ");
			sql.append(" \n LEFT JOIN(SELECT Clave, CASE WHEN Moneda = MonedaVenta THEN 1 ELSE CASE WHEN Tipo_Cambio IS NULL OR Tipo_Cambio = 0 THEN 1 ELSE Tipo_Cambio END END  AS TC, MonedaVenta FROM Proveedores) AS PROVEE ON PROVEE.Clave = CP.FK01_Proveedor ");
			sql.append(" \n LEFT JOIN(SELECT * FROM Productos) AS PROD ON PROD.idProducto= CPP.FK01_Producto ");
			sql.append(" \n LEFT JOIN(SELECT * FROM Costo_Y_Factor) AS CF ON CF.PK_Costo_Factor=CP.FK02_Costo_Factor    ");

			sql.append(" LEFT JOIN (SELECT * FROM Cliente_ConfiguracionPrecio) AS CliCP2 ON CliCP2.AK_ClienteConfigPrecio = CPP.FK06_CliCosto AND CliCP2.FK01_Cliente = @idCliente \n");
			sql.append(" LEFT JOIN (SELECT * FROM Cliente_ConfiguracionPrecio) AS CliCP3 ON CliCP3.AK_ClienteConfigPrecio = CPP.FK05_CliFamilia AND CliCP3.FK01_Cliente = @idCliente \n");
			sql.append(" \n LEFT JOIN (SELECT * FROM LAA_Concepto) AS LAAC ");
			sql.append(" \n		ON LAAC.PK_AAConcepto = CASE WHEN CliCP2.Fk06_LAAConcepto <> 0 THEN CliCP2.Fk06_LAAConcepto	WHEN  CliCP3.Fk06_LAAConcepto <> 0 THEN CliCP3.Fk06_LAAConcepto ELSE CF.FK03_LAAConcepto END");

			sql.append(" \n LEFT JOIN(SELECT PROD.idProducto,PROD.Proveedor, PROD.FK01_Configuracion_Precio, PROD.Costo,((((SUM(CPP.CostoNuevo - CPP.CostoAnTErior))/case when (COUNT(CPP.CostoNuevo)) = 0 then 1 else COUNT(CPP.CostoNuevo) end)*100 )/(CPP.CostoNuevo ");   
			sql.append(" \n          -((SUM(CPP.CostoNuevo - CPP.CostoAnTErior))/case when (COUNT(CPP.CostoNuevo)) = 0 then 1 else COUNT(CPP.CostoNuevo) end))) AS PORCIENTO   ");
			sql.append(" \n          FROM Productos AS PROD   ");
			sql.append(" \n          LEFT JOIN(SELECT MAX (PK_CambioPrecioProducto) ID, FK01_Producto FROM CambioPrecioProducto GROUP BY FK01_Producto) AS ID ON ID.FK01_Producto =PROD.idProducto ");  
			sql.append(" \n          LEFT JOIN(SELECT * FROM CambioPrecioProducto) AS CPP ON CPP.PK_CambioPrecioProducto = ID.ID    ");
			sql.append(" \n          GROUP BY CPP.CostoNuevo,PROD.Proveedor,PROD.FK01_Configuracion_Precio, PROD.Costo,PROD.idProducto) "); 
			sql.append("  \n         AS PORC ON PORC.idProducto = PROD.idProducto  ");
			sql.append(" \n LEFT JOIN(SELECT * FROM Licencia) AS LICE ON LICE.FK01_Proveedor = CP.FK01_Proveedor AND LICE.Tipo = PROD.DepositarioInternacional");
			sql.append(" \n LEFT JOIN (SELECT * FROM CostoFactor_Licencia) CFL ON CFL.FK01_CostoFactor = CF.PK_Costo_Factor AND CFL.Licencia = PROD.DepositarioInternacional" );
			sql.append("  \n WHERE CP.FK01_Proveedor=@idProveedor  AND PROD.idProducto = @idProducto AND CPP.FK02_ConfFamilia = @idConfigFamilia");


			////logger.info(sql.toString());
			return (FormulaPrecio) this.jdbcTemplate.queryForObject (sql.toString(), map, new FormulaPrecioRowMapper());

		}catch(Exception e){
			log.info(e.getMessage());
			f = new Funcion();
			f.enviarCorreoAvisoExepcion(e,"sql: " + sql, "idProveedor: " + idProveedor, "idProducto: " + idProducto,
					"stock_flete: " + stock_flete, "nivel: " + nivel, "idCliente: " + idCliente, "idConfig: " + idConfig);
			return new FormulaPrecio();
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConfiguracionPrecioProducto> getConfiguracionPrecioClienteCosto(
			Long idConfiguracionCosto, Long CategoriaPrecio, Long idCliente,
			String tipoNivelIngreso) throws ProquifaNetException {
		try {
			StringBuilder sql = new StringBuilder("");
			sql.append(" DECLARE @idCliente AS Integer=" + idCliente + "\n ");
			sql.append(" DECLARE @idCategoriaPrecio AS Integer="
					+ CategoriaPrecio + "\n ");
			sql.append(" DECLARE @AK_ClienteConfigPrecio AS Integer="
					+ idConfiguracionCosto + "\n ");
			sql.append(" SELECT TOP 1");
			sql.append(" \n 0 FactorClienteConf, ");
			sql.append(" \n CP.PK_Configuracion_Precio,IND.Valor AS Industria,VCTP.Valor AS Tipo,COALESCE(VCST.Valor,'') AS SubTipo,COALESCE(CONF.idConfigFamilia,CP.PK_Configuracion_Precio) AS idConfigFamilia, ");
			sql.append(" \n CPP.PK_ClienteConfiguracionPrecio, ");

			sql.append("\n (SELECT Ruta  from Clientes  WHERE  Clave=@idCliente ) AS RutaCliente , ");

			sql.append(" \n COALESCE(VCCTRL.Valor,'') AS Control,CP.Nivel,CP.FK01_Proveedor AS idProveedor, CF.PK_Costo_Factor AS idCostoFactor,");
			sql.append(" \n CASE WHEN CP.Nivel='Costo' THEN CP.PK_Configuracion_Precio ELSE 0 END IDConfCosto, CF.Costo_Consularizacion,CF.AlmacenDestino,CF.FactorDTA,CF.Honorarios, ");
			sql.append(" \n CF.Flete_Documentacion,NULL Porciento, CF.Factor_IGI,  CF.Factor_Utilidad, PROD.Costo costoCompra,CF.Stock_Disable, CF.FleteExpress_Disable, ");
			sql.append(" \n CF.Permiso,CF.FactorDTA, CF.VALORENADUANA, CF.DESCUENTO, CF.FLETE, CF.Honorarios, NULL AS TEntrega,PROD.idProducto idProducto, NULL Costo, NULL Moneda, NULL Codigo, NULL Fabrica, ");
			sql.append(" \n CF.factor_AAplus,CF.factor_AA,CF.factor_AM,CF.factor_AB,CF.factor_MA,CF.factor_MM,CF.factor_MB,CF.factor_FExpress,CF.factor_Stock,CF.factor_Comision,  CF.Factor_Bajo,");
			sql.append(" \n CF.Factor_Distribuidor AS Distribuidor,CF.Factor_Publico AS Publico,NULL ConfiguracionPrecio, PLISTA.CATGLISTA PrecioLista,");
			sql.append(" \n CASE WHEN CPP.Factor IS NOT NULL THEN CPP.Factor ELSE CF.Factor_" + tipoNivelIngreso + " END  Factor, CPROD.PK_ConfigPrecio_Producto,");
			sql.append(" \n CASE WHEN CPP.FactorTemp IS NOT NULL THEN CPP.FactorTemp ELSE 0 END  FactorTemp,");
			sql.append(" \n CASE WHEN (CPP.Factor IS NOT NULL  AND CPP.NivelConfigPrecio = 'Producto') THEN 1 ELSE 0 END Restablecer,");
			sql.append(" \n CPP.FUA, CASE WHEN CPP.Factor IS NOT NULL  THEN 1 ELSE 0 END RESTABLECER_COSTO, CF.Piezas ");
			sql.append(" \n , CASE WHEN (CPP.FactorTemp IS NOT NULL  AND CPP.NivelConfigPrecio = 'Producto') THEN 1 ELSE 0 END RestablecerTemporal ");
			sql.append(" \n , CASE WHEN CPP.FactorTemp IS NOT NULL  THEN 1 ELSE 0 END RESTABLECER_COSTOTemporal ");
			sql.append(" \n , COALESCE(TEC.PK_Tiempo_Entrega,0) AS idTEntrega,COALESCE(TEC.FK01_RequierePermiso_Existe,TE.FK01_RequierePermiso_Existe) FK01_RequierePermiso_Existe ");
			sql.append(" \n , COALESCE(TEC.FK02_RequierePermiso_NoExiste,TE.FK02_RequierePermiso_NoExiste) FK02_RequierePermiso_NoExiste ");
			sql.append(" \n , COALESCE(TEC.FK03_RequierePermiso_No,TE.FK03_RequierePermiso_No) FK03_RequierePermiso_No, COALESCE(TEC.FK04_FExpress,TE.FK04_FExpress) FK04_FExpress ");
			sql.append(" \n , coalesce(CPP.FK04_AgenteAduanal, CF.FK01_AgenteAduanal) FK01_AgenteAduanal, coalesce(CPP.FK05_LugarAgenteAduanal, CF.FK02_LugarAgenteAduanal) FK02_LugarAgenteAduanal ");
			sql.append(" \n , CASE WHEN CPP.CostoFijo IS NOT NULL  THEN CPP.CostoFijo ELSE CF.Factor_CostoFijo END Factor_CostoFijo");
			sql.append(" \n , CASE WHEN CPP.CostoFijoTemp IS NOT NULL THEN CPP.CostoFijoTemp ELSE 0 END CostoFijoTemp");
			sql.append(" \n , CASE WHEN CPP.Caduca is not null  THEN CPP.Caduca ELSE null END Caduca");
			sql.append(" \n , coalesce( CPP.CompuestaCostoF , 1 ) CompuestaCostoF");
			sql.append(" \n , coalesce( CPP.CompuestaFactorU , 1 ) CompuestaFactorU");
			sql.append(" \n , coalesce( CPP.CompuestaCostoFTemp , 1 ) CompuestaCostoFTemp");
			sql.append(" \n , coalesce( CPP.CompuestaFactorUTemp , 1 ) CompuestaFactorUTemp");
			sql.append(" \n , CPP.PrecioListaAnterior ");
			sql.append(" \n , coalesce(CPP.Fk06_LAAConcepto, CF.FK03_LAAConcepto) FK03_LAAConcepto, null idClasificacion, null montoLicencia, null PorcentajeLicencia ");
			sql.append(" \n FROM Cliente_ConfiguracionPrecio AS CPP ");
			sql.append(" \n LEFT JOIN (SELECT * FROM ConfiguracionPrecio_Producto) AS CPROD ON CPROD.FK06_CliCosto = CPP.AK_ClienteConfigPrecio");
			sql.append(" \n LEFT JOIN (SELECT * FROM Configuracion_Precio) AS CP ON CP.PK_Configuracion_Precio = COALESCE (CPROD.FK03_ConfCosto, FK02_ConfFamilia)");
			sql.append(" \n LEFT JOIN (SELECT * FROM Costo_Y_Factor) AS CF ON CF.PK_Costo_Factor = CP.FK02_Costo_Factor");
			sql.append(" \n LEFT JOIN (SELECT * FROM Tiempo_Entrega) AS TE ON TE.PK_Tiempo_Entrega = CP.FK03_Tiempo_Entrega");
			sql.append(" \n LEFT JOIN (SELECT * FROM Tiempo_Entrega) AS TEC ON TEC.PK_Tiempo_Entrega = CPP.FK03_TiempoEntrega ");
			sql.append(" \n LEFT JOIN (SELECT * FROM ValorCombo WHERE Concepto='TipoProducto') AS VCTP ON VCTP.PK_Folio = CP.Tipo");
			sql.append(" \n LEFT JOIN (SELECT * FROM ValorCombo WHERE Concepto='SubTipoProducto') AS VCST ON VCST.PK_Folio = CP.Subtipo  ");
			sql.append(" \n LEFT JOIN (SELECT * FROM ValorCombo WHERE Concepto='Control') AS VCCTRL ON VCCTRL.PK_Folio = CP.Control ");
			sql.append(" \n LEFT JOIN (SELECT * FROM ValorCombo WHERE Concepto='Industrial') AS IND ON IND.PK_Folio = cp.Industria");
			sql.append(" \n LEFT JOIN (SELECT * FROM AA_ConfiguracionPrecio) AS AACP ON AACP.FK02_ConfiguracionPrecio = CP.PK_Configuracion_Precio  ");
			sql.append(" \n LEFT JOIN(SELECT CONF.PK_Configuracion_Precio AS idConfig,FAMILY.PK_Configuracion_Precio idConfigFamilia  ");
			sql.append(" \n 	FROM Configuracion_Precio AS CONF");
			sql.append(" \n 	LEFT JOIN (SELECT * FROM Configuracion_Precio WHERE Nivel='Familia') AS FAMILY ON FAMILY.Tipo = CONF.Tipo AND FAMILY.Subtipo=CONF.Subtipo AND");
			sql.append(" \n 	FAMILY.Control=CONF.Control AND FAMILY.FK01_Proveedor= CONF.FK01_Proveedor AND FAMILY.Industria=CONF.Industria) AS CONF ON CONF.idConfig = cp.PK_Configuracion_Precio");
			sql.append(" \n LEFT JOIN(SELECT DISTINCT FK03_Categoria_PrecioLista AS CATGLISTA,CFP.FK03_ConfCosto, PROD.Costo");
			sql.append(" \n 	FROM ConfiguracionPrecio_Producto AS CFP");
			sql.append(" \n 	LEFT JOIN (SELECT * FROM Productos) AS PROD ON PROD.idProducto = CFP.FK01_Producto) AS PLISTA ON PLISTA.FK03_ConfCosto=CP.PK_Configuracion_Precio");
			sql.append(" \n LEFT JOIN(SELECT MAX(FK01_Producto) AS ID, FK03_ConfCosto FROM ConfiguracionPrecio_Producto GROUP BY FK03_ConfCosto) AS PRO ON PRO.FK03_ConfCosto=CP.PK_Configuracion_Precio");
			sql.append(" \n LEFT JOIN(SELECT Costo,  idProducto, FK03_Categoria_PrecioLista FROM Productos) AS PROD ON PROD.idProducto = CPROD.FK01_Producto");
			sql.append(" \n WHERE CPP.AK_ClienteConfigPrecio = @AK_ClienteConfigPrecio AND CPP.FK01_Cliente = @idCliente AND CPP.NivelConfigPrecio = 'COSTO' AND PROD.FK03_Categoria_PrecioLista=@idCategoriaPrecio");

			log.info("Mega consulta"+sql.toString());


			List<ConfiguracionPrecioProducto> list = super.jdbcTemplate.query(
					sql.toString(), new ConfiguracionPrecioRowMapper());
			for (ConfiguracionPrecioProducto configuracionPrecio : list) {
				if (!configuracionPrecio.getRestablecerCosto()) {// Cuando el
					// factor es
					// nulo
					sql = new StringBuilder("");
					sql.append(" \n SELECT PK_Tiempo_Entrega_Ruta, FK01_Configuracion_Precio,Ruta, RequierePermiso_Existe,RequierePermiso_NoExiste,RequierePermiso_No, FactorFlexibilidad ");
					sql.append(" \n FROM Tiempo_Entrega_Ruta ");
					sql.append(" \n WHERE FK01_Configuracion_Precio=").append(
							configuracionPrecio.getIdConfiguracion());
				} else {
					sql = new StringBuilder("");
					sql.append(" \n SELECT PK_Cliente_Tiempo_Entrega_Ruta AS PK_Tiempo_Entrega_Ruta,FK01_ClienteConfiguracionPrecio AS FK01_Configuracion_Precio,Ruta,RequierePermiso_Existe,RequierePermiso_NoExiste,RequierePermiso_No, FactorFlexibilidad ");
					sql.append(" \n FROM ConfiguracionPrecio_Producto ");
					sql.append(" \n LEFT JOIN Cliente_ConfiguracionPrecio ON ConfiguracionPrecio_Producto.FK06_CliCosto=Cliente_ConfiguracionPrecio.AK_ClienteConfigPrecio ");
					sql.append(" \n LEFT JOIN Cliente_Tiempo_Entrega_Ruta ON Cliente_Tiempo_Entrega_Ruta.FK01_ClienteConfiguracionPrecio=Cliente_ConfiguracionPrecio.PK_ClienteConfiguracionPrecio ");
					sql.append(" \n WHERE  PK_ConfigPrecio_Producto=")
					.append(configuracionPrecio
							.getIdConfiguracionPrecioProducto())
					.append(" AND Cliente_ConfiguracionPrecio.FK01_Cliente=")
					.append(idCliente)
					.append(" AND PK_Cliente_Tiempo_Entrega_Ruta IS NOT NULL");
				}
				// //logger.info("TIEMPOS DE ENTREGA------->:   "+sql.toString());
				List<TiempoEntrega> listTE = super.jdbcTemplate.query(
						sql.toString(),
						new TiempoEntregaRutaProveedorRowMapper());

				configuracionPrecio.setTiempoEntregaRuta(listTE);
			}

			return list;

		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConfiguracionPrecioProducto> findConfiguracionesPrecioCostoCliente(
			Long idConfigFam, Long idCliente, String tipoNivelIngreso)
					throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("");
			sbQuery.append(" \n DECLARE @idConfigPrecio AS Integer="
					+ idConfigFam);
			sbQuery.append(" \n DECLARE @idCliente AS Integer=" + idCliente);
			sbQuery.append(" \n DECLARE @nivelIngreso as varchar(5) = '"
					+ tipoNivelIngreso + "'");
			sbQuery.append(" \n SELECT  DISTINCT  CAT.PK_Categoria PrecioLista,");
			sbQuery.append(" \n CPROD1.PK_ConfigPrecio_Producto,  PROD.idProducto, ");
			sbQuery.append(" \n null Codigo, COALESCE(CONF.idConfigFamilia,CP.PK_Configuracion_Precio)  idConfigFamilia,  ");
			sbQuery.append(" \n CASE WHEN CliCP2.Factor is Not null THEN CliCP2.AK_ClienteConfigPrecio  ");
			sbQuery.append(" \n ELSE CP.PK_Configuracion_Precio END AS PK_Configuracion_Precio, null AS Industria,null AS Tipo,null AS SubTipo,null AS Control,  ");
			sbQuery.append(" \n CASE WHEN CliCP2.Factor is Not null  THEN CliCP2.AK_ClienteConfigPrecio  ");
			sbQuery.append(" \n ELSE 0 END AS FK03_ConfCosto,  ");

			sbQuery.append("\n (SELECT Ruta  from Clientes  WHERE  Clave=@idCliente ) AS RutaCliente , ");

			sbQuery.append(" \n CASE WHEN CliCP2.FUA is Not null AND CliCP2.FUA <> 0 THEN CliCP2.FUA WHEN CliCP3.FUA is Not null AND CliCP3.FUA <> 0 THEN CliCP3.FUA ELSE GETDATE() END FUA,");
			sbQuery.append(" \n CP.Nivel,CP.FK01_Proveedor AS idProveedor, case when CliCP2.Factor IS not null then CPROD1.FK06_CliCosto else null end AS idCostoFactor,CF.Costo_Consularizacion,");
			sbQuery.append(" \n CF.Flete_Documentacion,CF.Factor_IGI, CF.Permiso,CF.AlmacenDestino,  ");
			sbQuery.append(" \n CF.FactorDTA,CF.Honorarios, CF.Factor_Utilidad, ");
			sbQuery.append(" \n TE.FK01_RequierePermiso_ExisTE,TE.FK02_RequierePermiso_NoExisTE,TE.FK03_RequierePermiso_No, TE.FK04_FExpress,  ");
			// sbQuery.append(" \n TE.RequierePermiso_ExisTE FK01_RequierePermiso_ExisTE,TE.RequierePermiso_NoExisTE FK02_RequierePermiso_NoExisTE,TE.RequierePermiso_No FK03_RequierePermiso_No, TE.FExpress FK04_FExpress,  ");
			sbQuery.append(" \n CASE  WHEN CliCP2.FK03_TiempoEntrega is Not null AND CliCP2.FK03_TiempoEntrega <> 0 THEN TE.PK_Tiempo_Entrega ");
			sbQuery.append(" \n WHEN CliCP3.FK03_TiempoEntrega is Not null AND CliCP3.FK03_TiempoEntrega <> 0 THEN TE.PK_Tiempo_Entrega ELSE 0 END AS idTEntrega,  ");
			sbQuery.append(" \n (CAT.PrecioLista * PROVEE.TC) Costo,ROUND( PORC.PORCIENTO,1) AS Porciento,NULL Codigo, NULL Fabrica, PROVEE.MonedaVenta, ");
			sbQuery.append(" \n (CASE WHEN PROVEE.Moneda='Pesos' THEN 'MXN' WHEN PROVEE.Moneda='Euros' THEN 'EUR' WHEN Moneda='Dolares' THEN 'USD'    ");
			sbQuery.append(" \n WHEN PROVEE.Moneda='Libras' THEN 'LBS' WHEN PROVEE.Moneda='DlCan' THEN 'CAD'END) AS Moneda, ");
			sbQuery.append(" \n CASE WHEN CliCP2.FK04_AgenteAduanal  <> 0 THEN CliCP2.FK04_AgenteAduanal WHEN CliCP3.FK04_AgenteAduanal  <> 0 THEN CliCP3.FK04_AgenteAduanal ELSE CF.FK01_AgenteAduanal END FK01_AgenteAduanal, ");
			sbQuery.append(" \n CASE WHEN CliCP2.FK05_LugarAgenteAduanal  <> 0 THEN CliCP2.FK05_LugarAgenteAduanal WHEN CliCP3.FK05_LugarAgenteAduanal  <> 0 THEN CliCP3.FK05_LugarAgenteAduanal ELSE CF.FK02_LugarAgenteAduanal END FK02_LugarAgenteAduanal,");
			sbQuery.append(" \n CASE WHEN CliCP2.Fk06_LAAConcepto <> 0 THEN CliCP2.Fk06_LAAConcepto WHEN CliCP3.Fk06_LAAConcepto <> 0 THEN CliCP3.Fk06_LAAConcepto ELSE CF.FK03_LAAConcepto END FK03_LAAConcepto, ");
			sbQuery.append(" \n CF.Factor_Distribuidor AS Distribuidor,CF.Factor_Publico AS Publico, CP.PK_Configuracion_Precio ConfiguracionPrecio, ");
			sbQuery.append(" \n CASE WHEN LICE.Factor = -1 OR  LICE.Factor IS NULL THEN '' ELSE PROD.DepositarioInternacional END  Licencia, ");
			sbQuery.append(" \n CAT.PrecioLista costoCompra, null idClienteConfig, ");
			sbQuery.append(" \n CASE WHEN CliCP2.Factor is Not null  THEN CliCP2.Factor WHEN CliCP3.Factor is Not null  THEN CliCP3.Factor ELSE CF.Factor_" + tipoNivelIngreso + " END  Factor,  ");
			sbQuery.append(" \n CASE WHEN CliCP2.FactorTemp is Not null  THEN CliCP2.FactorTemp WHEN CliCP3.FactorTemp is Not null  THEN CliCP3.FactorTemp ELSE 0 END  FactorTemp,  ");
			sbQuery.append(" \n CASE WHEN CliCP2.CostoFijo is Not null  THEN CliCP2.CostoFijo WHEN CliCP3.CostoFijo is Not null  THEN CliCP3.CostoFijo ELSE CF.Factor_CostoFijo END  Factor_CostoFijo,  ");
			sbQuery.append(" \n CASE WHEN CliCP2.CostoFijoTemp is Not null  THEN CliCP2.CostoFijoTemp WHEN CliCP3.CostoFijoTemp is Not null  THEN CliCP3.CostoFijoTemp ELSE 0 END  CostoFijoTemp,  ");
			sbQuery.append(" \n CASE WHEN CliCP2.Caduca is Not null  THEN CliCP2.Caduca WHEN CliCP3.Caduca is Not null  THEN CliCP3.Caduca ELSE null END  Caduca,  ");
			sbQuery.append(" \n coalesce(CliCP2.CompuestaCostoF , CliCP3.CompuestaCostoF, CF.CompuestaCostoF, 1 )  CompuestaCostoF,  ");
			sbQuery.append(" \n coalesce(CliCP2.CompuestaFactorU ,CliCP3.CompuestaFactorU, CF.CompuestaFactorU, 1 )  CompuestaFactorU,  ");
			sbQuery.append(" \n coalesce(CliCP2.CompuestaCostoFTemp , CliCP3.CompuestaCostoFTemp,  1 )  CompuestaCostoFTemp,  ");
			sbQuery.append(" \n coalesce(CliCP2.CompuestaFactorUTemp ,CliCP3.CompuestaFactorUTemp, 1 )  CompuestaFactorUTemp,  ");
			sbQuery.append(" \n CF.Factor_" + tipoNivelIngreso + " Factor_orig,  CF.VALORENADUANA, CF.DESCUENTO, CF.FLETE, 0   Restablecer, CASE WHEN CliCP2.Factor  IS NULL  THEN 0 ELSE 1 END Restablece_Costo, ");
			sbQuery.append(" \n 0   RestablecerTemporal, CASE WHEN CliCP2.FactorTemp  IS NULL  THEN 0 ELSE 1 END Restablece_CostoTemporal, ");
			sbQuery.append(" \n ").append( getConsultaFormulaCatalogoCliente( tipoNivelIngreso, "Costo"));
			sbQuery.append(" \n PROD.totalProd ");
			sbQuery.append(" \n FROM Categoria_PrecioLista AS CAT   ");
			sbQuery.append(" \n LEFT JOIN ( SELECT  MAX(idProducto) idProducto, COUNT(idProducto) totalProd, FK03_Categoria_PrecioLista, ");
			sbQuery.append(" \n				CASE WHEN DepositarioInternacional IS NULL OR DepositarioInternacional = '--NINGUNO--'  THEN '' ELSE DepositarioInternacional END DepositarioInternacional ");
			sbQuery.append(" \n				FROM Productos WHERE Vigente = 1  GROUP BY FK03_Categoria_PrecioLista, CASE WHEN DepositarioInternacional IS NULL OR DepositarioInternacional = '--NINGUNO--'  THEN '' ELSE DepositarioInternacional END) ");
			sbQuery.append(" \n			AS PROD ON PROD.FK03_Categoria_PrecioLista= CAT.PK_Categoria ");
			sbQuery.append(" \n LEFT JOIN ( SELECT * FROM Configuracion_Precio) CP ON CP.FK01_Proveedor = CAT.FK01_Proveedor AND CP.Tipo = CAT.FK02_Tipo AND coalesce(CP.Subtipo,1) = coalesce(CAT.FK03_Subtipo,1) AND coalesce(CP.Control,1) = coalesce(CAT.FK04_Control,1) AND CP.PK_Configuracion_Precio = @idConfigPrecio");
			sbQuery.append(" \n LEFT JOIN ( SELECT * FROM ConfiguracionPrecio_Producto) AS CPROD1 ON CPROD1.FK01_Producto = PROD.idProducto AND CPROD1.FK02_ConfFamilia = CP.PK_Configuracion_Precio");
			sbQuery.append(" \n LEFT JOIN ( SELECT Clave, CASE WHEN Moneda = MonedaVenta THEN 1 ELSE CASE WHEN Tipo_Cambio IS NULL OR Tipo_Cambio = 0 THEN 1 ELSE Tipo_Cambio END END  AS TC, MonedaVenta, Moneda FROM Proveedores) AS PROVEE ON PROVEE.Clave = CP.FK01_Proveedor");
			sbQuery.append(" \n LEFT JOIN ( SELECT * FROM Configuracion_Precio) CP2 ON CP2.PK_Configuracion_Precio = COALESCE (CPROD1.FK03_ConfCosto, CPROD1.FK02_ConfFamilia)");
			sbQuery.append(" \n LEFT JOIN ( SELECT * FROM Costo_Y_Factor) AS CF ON CF.PK_Costo_Factor=CP2.FK02_Costo_Factor ");
			sbQuery.append(" \n LEFT JOIN ( SELECT CONF.PK_Configuracion_Precio AS idConfig,FAMILY.PK_Configuracion_Precio idConfigFamilia FROM Configuracion_Precio AS CONF   ");
			sbQuery.append(" \n          LEFT JOIN (SELECT * FROM Configuracion_Precio WHERE Nivel='Familia') AS FAMILY ON FAMILY.Tipo = CONF.Tipo AND FAMILY.Subtipo=CONF.Subtipo AND  ");
			sbQuery.append(" \n          FAMILY.Control=CONF.Control AND FAMILY.FK01_Proveedor= CONF.FK01_Proveedor AND FAMILY.Industria=CONF.Industria) AS CONF ON CONF.idConfig = cp.PK_Configuracion_Precio ");
			sbQuery.append(" \n LEFT JOIN (SELECT * FROM Cliente_ConfiguracionPrecio) AS CliCP2 ON CliCP2.AK_ClienteConfigPrecio = CPROD1.FK06_CliCosto  AND CliCP2.FK01_Cliente = @idCliente "); 
			sbQuery.append(" \n LEFT JOIN (SELECT * FROM Cliente_ConfiguracionPrecio) AS CliCP3 ON CliCP3.AK_ClienteConfigPrecio = CPROD1.FK05_CliFamilia AND CliCP3.FK01_Cliente = @idCliente "); 
			sbQuery.append(" \n LEFT JOIN (SELECT * FROM LAA_Concepto) AS LAAC ON LAAC.PK_AAConcepto = COALESCE( CliCP2.Fk06_LAAConcepto, CliCP3.Fk06_LAAConcepto,CF.FK03_LAAConcepto) ");
			sbQuery.append(" \n LEFT JOIN(SELECT * FROM Tiempo_Entrega) AS TE ON TE.PK_Tiempo_Entrega = COALESCE(CliCP2.FK03_TiempoEntrega,CliCP3.FK03_TiempoEntrega  , CP.FK03_Tiempo_Entrega)    ");
			sbQuery.append(" \n LEFT JOIN(SELECT PROD.idProducto,PROD.Proveedor, PROD.FK01_Configuracion_Precio, PROD.Costo,((((SUM(CPP.CostoNuevo - CPP.CostoAnTErior))/COUNT(CPP.CostoNuevo))*100 )/(CPP.CostoNuevo  ");
			sbQuery.append(" \n         -((SUM(CPP.CostoNuevo - CPP.CostoAnTErior))/COUNT(CPP.CostoNuevo)))) AS PORCIENTO    ");
			sbQuery.append(" \n           FROM Productos AS PROD             LEFT JOIN(SELECT MAX (PK_CambioPrecioProducto) ID, FK01_Producto FROM CambioPrecioProducto GROUP BY FK01_Producto) AS ID ON ID.FK01_Producto =PROD.idProducto  ");
			sbQuery.append(" \n           LEFT JOIN(SELECT * FROM CambioPrecioProducto) AS CPP ON CPP.PK_CambioPrecioProducto = ID.ID     ");
			sbQuery.append(" \n           GROUP BY CPP.CostoNuevo,PROD.Proveedor,PROD.FK01_Configuracion_Precio, PROD.Costo,PROD.idProducto)  ");
			sbQuery.append(" \n           AS PORC ON PORC.idProducto = PROD.idProducto    ");
			sbQuery.append(" \n LEFT JOIN(SELECT * FROM Licencia) AS LICE ON LICE.FK01_Proveedor = CP.FK01_Proveedor AND LICE.Tipo = PROD.DepositarioInternacional  ");
			sbQuery.append(" \n LEFT JOIN (SELECT * FROM CostoFactor_Licencia) CFL ON CFL.FK01_CostoFactor = CF.PK_Costo_Factor AND CFL.Licencia = PROD.DepositarioInternacional");
			sbQuery.append(" \n WHERE CP.PK_Configuracion_Precio =@idConfigPrecio  AND CPROD1.PK_ConfigPrecio_Producto IS NOT NULL ");
			sbQuery.append(" \n GROUP BY CAT.PK_Categoria,  CP.PK_Configuracion_Precio,CPROD1.FK06_CliCosto, CPROD1.PK_ConfigPrecio_Producto, ");
			sbQuery.append(" \n COALESCE(CONF.idConfigFamilia,CP.PK_Configuracion_Precio) , ");
			sbQuery.append(" \n CASE WHEN CliCP2.FUA is Not null AND CliCP2.FUA <> 0 THEN CliCP2.FUA WHEN CliCP3.FUA is Not null AND CliCP3.FUA <> 0 THEN CliCP3.FUA ELSE GETDATE() END ,");
			sbQuery.append(" \n CP.Nivel,CP.FK01_Proveedor , case when CliCP2.Factor IS not null then CPROD1.FK06_CliCosto else null end ,CF.Costo_Consularizacion,CF.Flete_Documentacion,CF.Factor_IGI, CF.Permiso,CF.AlmacenDestino,  ");
			sbQuery.append(" \n CF.FactorDTA,CF.Honorarios, CF.Factor_CostoFijo,CF.Factor_Utilidad,");
			sbQuery.append(" \n TE.FK01_RequierePermiso_ExisTE,TE.FK02_RequierePermiso_NoExisTE,TE.FK03_RequierePermiso_No, TE.FK04_FExpress,   ");
			// sbQuery.append(" \n TE.RequierePermiso_ExisTE,TE.RequierePermiso_NoExisTE,TE.RequierePermiso_No, TE.FExpress,   ");
			sbQuery.append(" \n  CASE  WHEN CliCP2.FK03_TiempoEntrega is Not null AND CliCP2.FK03_TiempoEntrega <> 0 THEN TE.PK_Tiempo_Entrega WHEN CliCP3.FK03_TiempoEntrega is Not null AND CliCP3.FK03_TiempoEntrega <> 0 THEN TE.PK_Tiempo_Entrega ELSE 0 END ,  ");
			sbQuery.append(" \n (CAT.PrecioLista * PROVEE.TC) ,ROUND( PORC.PORCIENTO,1),PROVEE.MonedaVenta, PROVEE.Moneda,");
			sbQuery.append(" \n CASE WHEN CliCP2.FK04_AgenteAduanal  <> 0 THEN CliCP2.FK04_AgenteAduanal WHEN CliCP3.FK04_AgenteAduanal  <> 0 THEN CliCP3.FK04_AgenteAduanal ELSE CF.FK01_AgenteAduanal END , ");
			sbQuery.append(" \n CASE WHEN CliCP2.FK05_LugarAgenteAduanal  <> 0 THEN CliCP2.FK05_LugarAgenteAduanal WHEN CliCP3.FK05_LugarAgenteAduanal  <> 0 THEN CliCP3.FK05_LugarAgenteAduanal ELSE CF.FK02_LugarAgenteAduanal END , ");
			sbQuery.append(" \n CASE WHEN CliCP2.Fk06_LAAConcepto <> 0 THEN CliCP2.Fk06_LAAConcepto WHEN CliCP3.Fk06_LAAConcepto <> 0 THEN CliCP3.Fk06_LAAConcepto ELSE CF.FK03_LAAConcepto END , ");
			sbQuery.append(" \n CF.Factor_Distribuidor,CF.Factor_Publico, CASE WHEN LICE.Factor = -1 OR  LICE.Factor IS NULL THEN '' ELSE PROD.DepositarioInternacional END,");
			sbQuery.append(" \n CAT.PrecioLista, CF.Piezas,LICE.Factor,LAAC.Monto,LAAC.Porcentaje, CliCP2.Factor, CliCP3.Factor, CF.Factor_" + tipoNivelIngreso + ",  ");
			sbQuery.append(" \n CASE WHEN CliCP2.CostoFijo is Not null  THEN CliCP2.CostoFijo WHEN CliCP3.CostoFijo is Not null  THEN CliCP3.CostoFijo ELSE CF.Factor_CostoFijo END  ,  ");
			sbQuery.append(" \n CASE WHEN CliCP2.FactorTemp is Not null  THEN CliCP2.FactorTemp WHEN CliCP3.FactorTemp is Not null  THEN CliCP3.FactorTemp ELSE 0 END  ,  ");
			sbQuery.append(" \n CASE WHEN CliCP2.CostoFijoTemp is Not null  THEN CliCP2.CostoFijoTemp WHEN CliCP3.CostoFijoTemp is Not null  THEN CliCP3.CostoFijoTemp ELSE 0 END  ,  ");
			sbQuery.append(" \n CASE WHEN CliCP2.Caduca is Not null  THEN CliCP2.Caduca WHEN CliCP3.Caduca is Not null  THEN CliCP3.Caduca ELSE null END  ,  ");
			sbQuery.append(" \n coalesce(CliCP2.CompuestaCostoF , CliCP3.CompuestaCostoF, CF.CompuestaCostoF, 1 )  ,  ");
			sbQuery.append(" \n coalesce(CliCP2.CompuestaFactorU ,CliCP3.CompuestaFactorU, CF.CompuestaFactorU, 1 )  ,  ");
			sbQuery.append(" \n coalesce(CliCP2.CompuestaCostoFTemp , CliCP3.CompuestaCostoFTemp, 1 )  ,  ");
			sbQuery.append(" \n coalesce(CliCP2.CompuestaFactorUTemp ,CliCP3.CompuestaFactorUTemp, 1 )  ,  ");
			sbQuery.append(" \n CF.VALORENADUANA, CF.DESCUENTO, CF.FLETE, CASE WHEN CliCP2.Factor  IS NULL  THEN 0 ELSE 1 END,PROD.totalProd,PROD.idProducto, CliCP2.AK_ClienteConfigPrecio,");
			sbQuery.append(" \n CFL.Monto,CFL.Porcentaje,CliCP3.AK_ClienteConfigPrecio, CPROD1.FK03_ConfCosto, CASE WHEN CliCP2.FactorTemp  IS NULL  THEN 0 ELSE 1 END  ");

			//			logger.info(sbQuery.toString());

			return super.jdbcTemplate.query(sbQuery.toString(), new ConfiguracionPrecioClienteCostoRowMapper());

		} catch (Exception e) {
			log.info("Error:" + e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConfiguracionPrecioProducto> findConfiguracionClasificacionCliente(
			Long idConfigFam, Long idCliente, String tipoNivelIngreso,
			Long idClasificacion) throws ProquifaNetException {
		try {
			StringBuilder sql = new StringBuilder("");

			sql.append("\n DECLARE @idConfigFam AS INTEGER = " + idConfigFam);
			sql.append("\n DECLARE @idCliente AS INTEGER = " + idCliente);
			sql.append("\n DECLARE @idClasificacion AS INTEGER = " + idClasificacion);
			sql.append("\n DECLARE @tipoNivelIngreso AS VARCHAR(5) = '" + tipoNivelIngreso + "'");
			sql.append("\n SELECT distinct ");
			sql.append("\n CP.PK_Configuracion_Precio,  CPP.FK02_ConfFamilia AS idConfigFamilia, ");
			sql.append("\n CP.Industria AS Industria, CAT.FK02_Tipo AS Tipo,COALESCE(CAT.FK03_Subtipo,'') AS SubTipo, COALESCE(CAT.FK04_Control,'') AS Control,");
			sql.append("\n CP.Nivel,CP.FK01_Proveedor AS idProveedor, CASE WHEN CP.Nivel='Costo' THEN CP.PK_Configuracion_Precio ELSE 0 END IDConfCosto,");
			sql.append("\n CF.PK_Costo_Factor AS idCostoFactor, CF.Costo_Consularizacion,CF.AlmacenDestino,CF.FactorDTA,CF.Honorarios, PROD.DepositarioInternacional,   ");
			sql.append("\n CF.Flete_Documentacion, CF.Factor_IGI, CF.Factor_Utilidad, PROD.Costo costoCompra,CF.Stock_Disable, CF.FleteExpress_Disable, ");
			sql.append("\n CF.Permiso,CF.FactorDTA, CF.VALORENADUANA, CF.DESCUENTO, CF.FLETE, CF.Honorarios, CF.Piezas, ");
			sql.append("\n CASE WHEN CCP.Factor IS NOT NULL THEN CCP.Factor WHEN CCP1.Factor IS NOT NULL THEN CCP1.Factor ELSE CF.Factor_" + tipoNivelIngreso + " END  Factor, CPP.PK_ConfigPrecio_Producto, ");
			sql.append("\n CASE WHEN CCP.FactorTemp IS NOT NULL THEN CCP.FactorTemp ELSE 0 END  FactorTemp,");
			sql.append("\n CASE WHEN CCP.Caduca IS NOT NULL THEN CCP.Caduca ELSE null END  Caduca,");
			sql.append("\n (SELECT Ruta  from Clientes  WHERE  Clave=@idCliente ) AS RutaCliente , ");
			sql.append(" \n CASE WHEN CCP.CostoFijo IS NOT NULL THEN CCP.CostoFijo ELSE CF.Factor_CostoFijo END Factor_CostoFijo,");
			sql.append(" \n CASE WHEN CCP.CostoFijoTemp IS NOT NULL THEN CCP.CostoFijoTemp ELSE 0 END CostoFijoTemp,");
			sql.append(" \n coalesce( CCP.CompuestaCostoF , 1 ) CompuestaCostoF,");
			sql.append(" \n coalesce(CCP.CompuestaFactorU , 1 ) CompuestaFactorU,");
			sql.append(" \n coalesce( CCP.CompuestaCostoFTemp , 1 ) CompuestaCostoFTemp,");
			sql.append(" \n coalesce(CCP.CompuestaFactorUTemp , 1 ) CompuestaFactorUTemp,");
			sql.append("\n CASE WHEN (CCP.Factor IS NOT NULL  AND CCP.NivelConfigPrecio = 'Clasificacion') THEN 1 ELSE 0 END Restablecer, ");
			sql.append("\n CASE WHEN (CCP.CostoFijoTemp IS NOT NULL  AND CCP.NivelConfigPrecio = 'Clasificacion') THEN 1 ELSE 0 END RestablecerTemporal, 0 RESTABLECER_COSTOTemporal,");
			sql.append("\n CCP.FUA, NULL RESTABLECER_COSTO, @idClasificacion idClasificacion,");
			sql.append("\n PROD.idProducto idProducto, CAT.PK_Categoria PrecioLista, ");
			sql.append("\n CASE WHEN CCP.FK04_AgenteAduanal IS NOT NULL THEN 1 ELSE 0 END CLI_AA,");
			sql.append("\n CASE WHEN CCP.FK05_LugarAgenteAduanal IS NOT NULL THEN 1 ELSE 0 END CLI_LAA,");
			sql.append("\n CASE WHEN CCP.Fk06_LAAConcepto IS NOT NULL THEN 1 ELSE 0 END CLI_LAAC,");
			sql.append("\n CASE WHEN CPP.FK09_CliClasificacion IS NOT NULL THEN TE.PK_Tiempo_Entrega ELSE 0 END   AS idTEntrega, ");
			sql.append("\n TE.FK01_RequierePermiso_Existe, TE.FK02_RequierePermiso_NoExiste ,TE.FK03_RequierePermiso_No, TE.FK04_FExpress,");
			sql.append("\n AA.PK_AgenteAduanal FK01_AgenteAduanal, LAA.PK_Lugar_AgenteAduanal FK02_LugarAgenteAduanal , LAAC.PK_AAConcepto FK03_LAAConcepto,");
			sql.append("\n NULL Porciento, NULL ConfiguracionPrecio, NULL AS TEntrega, NULL Costo, NULL Moneda, NULL MonedaVenta, NULL Codigo, NULL Fabrica, ");
			sql.append("\n NULL factor_AA, NULL factor_AM, NULL factor_AB, NULL factor_MA, NULL factor_MM, NULL factor_MB, NULL factor_FExpress,");
			sql.append("\n NULL factor_Stock, NULL factor_Comision, NULL Factor_Bajo, NULL Distribuidor, NULL publico, null PrecioU, null DIFERENCIAL");
			sql.append("\n FROM ConfiguracionPrecio_Producto CPP");
			sql.append("\n LEFT JOIN (SELECT * FROM Productos) PROD ON PROD.idProducto = CPP.FK01_Producto ");
			sql.append("\n LEFT JOIN (SELECT * FROM Configuracion_Precio) CP ON CP.PK_Configuracion_Precio = CASE WHEN CPP.FK08_ConfClasificacion IS NOT NULL THEN CPP.FK08_ConfClasificacion ELSE CPP.FK02_ConfFamilia END");
			//			sql.append("\n LEFT JOIN (SELECT * FROM Cliente_ConfiguracionPrecio ) CCP ON CCP.AK_ClienteConfigPrecio = CASE WHEN CPP.FK09_CliClasificacion IS NOT NULL THEN CPP.FK09_CliClasificacion ELSE CPP.FK05_CliFamilia END AND CCP.FK01_Cliente = @idCliente "); // AND																																																															// @tipoNivelIngreso
			sql.append("\n LEFT JOIN (SELECT * FROM Cliente_ConfiguracionPrecio ) CCP ON CCP.AK_ClienteConfigPrecio = CPP.FK09_CliClasificacion AND CCP.FK01_Cliente = @idCliente "); 
			sql.append("\n LEFT JOIN (SELECT * FROM Cliente_ConfiguracionPrecio ) CCP1 ON CCP1.AK_ClienteConfigPrecio = CPP.FK05_CliFamilia  AND CCP1.FK01_Cliente = @idCliente ");
			sql.append("\n LEFT JOIN (SELECT * FROM Costo_Y_Factor) CF ON CF.PK_Costo_Factor = CP.FK02_Costo_Factor");
			sql.append("\n LEFT JOIN (SELECT * FROM Tiempo_Entrega) TE ON TE.PK_Tiempo_Entrega = CASE WHEN CCP.FK03_TiempoEntrega IS NOT NULL THEN CCP.FK03_TiempoEntrega ELSE CP.FK03_Tiempo_Entrega END");
			sql.append("\n LEFT JOIN (SELECT * FROM AgenteAduanal) AA ON AA.PK_AgenteAduanal = CASE WHEN CCP.FK04_AgenteAduanal IS NOT NULL THEN CCP.FK04_AgenteAduanal ELSE CF.FK01_AgenteAduanal END");
			sql.append("\n LEFT JOIN (SELECT * FROM Lugar_AgenteAduanal) LAA ON LAA.PK_Lugar_AgenteAduanal = CASE WHEN CCP.FK05_LugarAgenteAduanal IS NOT NULL THEN CCP.FK05_LugarAgenteAduanal ELSE CF.FK02_LugarAgenteAduanal END");
			sql.append("\n LEFT JOIN (SELECT * FROM LAA_Concepto) LAAC ON LAAC.PK_AAConcepto = CASE WHEN CCP.Fk06_LAAConcepto IS NOT NULL THEN CCP.Fk06_LAAConcepto ELSE CF.FK03_LAAConcepto END");
			sql.append("\n LEFT JOIN (SELECT * FROM Categoria_PrecioLista) CAT ON CAT.PK_Categoria = PROD.FK03_Categoria_PrecioLista");
			sql.append("\n WHERE CPP.FK02_ConfFamilia = @idConfigFam AND PROD.FK04_Clasificacion_ConfiguracionPrecio = @idClasificacion ");
			log.info(sql.toString());

			List<ConfiguracionPrecioProducto> list = this.jdbcTemplate.query(
					sql.toString(),
					new ConfiguracionPrecioClasificacionClienteRowMapper());

			sql = new StringBuilder("");
			sql.append("\n DECLARE @idConfigFam AS INTEGER = " + idConfigFam);
			sql.append("\n DECLARE @idCliente AS INTEGER = " + idCliente);
			sql.append("\n DECLARE @idClasificacion AS INTEGER = "
					+ idClasificacion);
			sql.append("\n DECLARE @tipoNivelIngreso AS VARCHAR(5) = '"
					+ tipoNivelIngreso + "'");
			sql.append(" \n SELECT DISTINCT CTER.PK_Cliente_Tiempo_Entrega_Ruta PK_Tiempo_Entrega_Ruta, COALESCE(CTER.FK01_ClienteConfiguracionPrecio, TER.FK01_Configuracion_Precio) FK01_Configuracion_Precio, COALESCE(CTER.Ruta,TER.RUTA) RUTA, ");
			sql.append(" \n COALESCE(CTER.RequierePermiso_Existe,TER.RequierePermiso_Existe) RequierePermiso_Existe,COALESCE(CTER.RequierePermiso_NoExiste,TER.RequierePermiso_NoExiste)RequierePermiso_NoExiste,");
			sql.append(" \n COALESCE(CTER.RequierePermiso_No,TER.RequierePermiso_No) RequierePermiso_No, COALESCE(CTER.FactorFlexibilidad, TER.FactorFlexibilidad) AS FactorFlexibilidad");
			sql.append(" \n FROM Productos P");
			sql.append(" \n LEFT JOIN (SELECT * FROM ConfiguracionPrecio_Producto ) CPP ON CPP.FK01_Producto = P.idProducto");
			sql.append(" \n LEFT JOIN (SELECT * FROM Tiempo_Entrega_Ruta) TER ON TER.FK01_Configuracion_Precio = COALESCE(CPP.FK08_ConfClasificacion,CPP.FK02_ConfFamilia)");
			sql.append(" \n LEFT JOIN (SELECT * FROM Cliente_ConfiguracionPrecio) CLIP1 ON CLIP1.AK_ClienteConfigPrecio = CPP.FK09_CliClasificacion AND CLIP1.FK01_Cliente = @idCliente");
			sql.append(" \n LEFT JOIN (SELECT * FROM Cliente_ConfiguracionPrecio) CLIP2 ON CLIP2.AK_ClienteConfigPrecio = CPP.FK05_CliFamilia AND CLIP2.FK01_Cliente = @idCliente");
			sql.append(" \n LEFT JOIN (SELECT * FROM Cliente_Tiempo_Entrega_Ruta) CTER ON CTER.FK01_ClienteConfiguracionPrecio = COALESCE(CLIP1.PK_ClienteConfiguracionPrecio, CLIP2.PK_ClienteConfiguracionPrecio)");
			sql.append(" \n WHERE CPP.FK02_ConfFamilia = @idConfigFam AND P.FK04_Clasificacion_ConfiguracionPrecio = @idClasificacion");

			List<TiempoEntrega> listTE = super.jdbcTemplate.query(
					sql.toString(), new TiempoEntregaRutaProveedorRowMapper());

			list.get(0).setTiempoEntregaRuta(listTE);

			return list;
		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConfiguracionPrecioProducto> findClasificacionPrecioProductoPorCatPrecioCliente(
			Long idClasificacion, String tipoNivelIngreso, Long idCliente)
					throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("");

			sbQuery.append(" \n DECLARE @idCliente AS INTEGER = " + idCliente);
			sbQuery.append(" \n DECLARE @idClasificacion AS Integer=").append(
					idClasificacion);
			sbQuery.append(" \n SELECT DISTINCT CAT.PK_Categoria PrecioLista, CP.FK01_Proveedor AS idProveedor, PROD.idProducto,");
			sbQuery.append(" \n CASE WHEN CliCP2.Factor is Not null then CPP.FK09_CliClasificacion WHEN CliCP3.Factor is Not null then  CPP.FK05_CliFamilia   ");
			sbQuery.append(" \n WHEN CPP.FK08_ConfClasificacion is Not null then CPP.FK08_ConfClasificacion else cpp.FK02_ConfFamilia END  AS idConfigFamilia,PROD.DepositarioInternacional, ");

			sbQuery.append("\n (SELECT Ruta  from Clientes  WHERE  Clave=@idCliente ) AS RutaCliente , ");

			sbQuery.append(" \n CAST((PROD.Costo * PROVEE.TC) AS DECIMAL (9,2)) Costo, PROVEE.Moneda,PROVEE.MonedaVenta,");
			sbQuery.append(" \n (CASE WHEN PROVEE.Moneda='Pesos' THEN 'MXN' WHEN PROVEE.Moneda='Euros' THEN 'EUR' WHEN PROVEE.Moneda='Dolares' THEN 'USD' ");
			sbQuery.append(" \n WHEN PROVEE.Moneda='Libras' THEN 'LBS' WHEN PROVEE.Moneda='DlCan' THEN 'CAD'END) AS Moneda, PROVEE.MonedaVenta,");
			sbQuery.append(" \n ").append( getConsultaFormulaCatalogoCliente( tipoNivelIngreso, "Clasificacion"));
			sbQuery.append(" \n NULL PK_Configuracion_Precio,  NULL idClasificacion,  NULL idCostoFactor, ");
			sbQuery.append(" \n 1 AS Porciento, 0 RESTABLECER_COSTO, PROD.Costo costoCompra,  ");
			sbQuery.append(" \n NULL Stock_Disable, NULL FleteExpress_Disable, NULL Piezas, NULL CANT_PROD,  ");
			sbQuery.append(" \n NULL Industria,  null IDConfCosto, NULL Nivel, CASE WHEN CliCP2.Factor IS NOT NULL THEN CliCP2.Factor	ELSE CASE WHEN CliCP3.Factor IS NOT NULL THEN CliCP3.Factor ELSE CF.Factor_"+ tipoNivelIngreso + " END END Factor,  ");
			sbQuery.append(" \n CASE WHEN CliCP2.FactorTemp IS NOT NULL THEN CliCP2.FactorTemp	ELSE CASE WHEN CliCP3.FactorTemp IS NOT NULL THEN CliCP3.FactorTemp ELSE 0 END END FactorTemp,  ");
			sbQuery.append(" \n NULL FUA, NULL PK_ConfigPrecio_Producto, NULL Restablecer, NULL Tipo, NULL Subtipo, NULL Control, NULL Codigo, 0 RESTABLECER_COSTOTemporal, 0 RestablecerTemporal,");
			sbQuery.append(" \n NULL Porciento, NULL ConfiguracionPrecio, NULL AS TEntrega, NULL Costo, NULL Moneda, NULL Codigo, NULL Fabrica,  ");
			sbQuery.append(" \n NULL factor_AA, NULL factor_AM, NULL factor_AB, NULL factor_MA, NULL factor_MM, NULL factor_MB, NULL factor_FExpress, ");
			sbQuery.append(" \n NULL factor_Stock, NULL factor_Comision, NULL Factor_Bajo, NULL Distribuidor, NULL publico, null Costo_Consularizacion, null Flete_Documentacion,");
			sbQuery.append(" \n NULL Factor_IGI, CASE WHEN CliCP2.CostoFijo is Not null  THEN CliCP2.CostoFijo WHEN CliCP3.CostoFijo is Not null  THEN CliCP3.CostoFijo ELSE CF.Factor_CostoFijo END Factor_CostoFijo, NULL Permiso, NULL AlmacenDestino, NULL FactorDTA, null VALORENADUANA, NULL DESCUENTO, NULL FLETE,  ");
			sbQuery.append(" \n CASE WHEN CliCP2.CostoFijoTemp is Not null  THEN CliCP2.CostoFijoTemp WHEN CliCP3.CostoFijoTemp is Not null  THEN CliCP3.CostoFijoTemp ELSE 0 END CostoFijoTemp, ");
			sbQuery.append(" \n CASE WHEN CliCP2.Caduca is Not null  THEN CliCP2.Caduca WHEN CliCP3.Caduca is Not null  THEN CliCP3.Caduca ELSE null END Caduca, ");
			sbQuery.append(" \n NULL FK01_AgenteAduanal, NULL FK02_LugarAgenteAduanal , NULL FK03_LAAConcepto, null CLI_AA, null CLI_LAA, null CLI_LAAC, null idTEntrega, ");
			sbQuery.append(" \n NULL FK01_RequierePermiso_Existe, NULL FK02_RequierePermiso_NoExiste ,NULL FK03_RequierePermiso_No, NULL FK04_FExpress, ");
			sbQuery.append(" \n coalesce(CliCP2.CompuestaCostoF , CliCP3.CompuestaCostoF, CF.CompuestaCostoF, 1 )  CompuestaCostoF,  ");
			sbQuery.append(" \n coalesce(CliCP2.CompuestaFactorU , CliCP3.CompuestaFactorU, CF.CompuestaFactorU, 1 )  CompuestaFactorU,  ");
			sbQuery.append(" \n coalesce(CliCP2.CompuestaCostoFTemp , CliCP3.CompuestaCostoFTemp,  1 )  CompuestaCostoFTemp,  ");
			sbQuery.append(" \n coalesce(CliCP2.CompuestaFactorUTemp , CliCP3.CompuestaFactorUTemp,  1 )  CompuestaFactorUTemp  ");
			sbQuery.append(" \n FROM ConfiguracionPrecio_Clasificacion CPC");
			sbQuery.append(" \n LEFT JOIN (SELECT MAX(idProducto) idProducto, FK04_Clasificacion_ConfiguracionPrecio, FK03_Categoria_PrecioLista, DepositarioInternacional, Costo FROM Productos WHERE Vigente = 1");
			sbQuery.append(" \n 			GROUP BY FK04_Clasificacion_ConfiguracionPrecio, FK03_Categoria_PrecioLista, DepositarioInternacional, Costo) PROD ON PROD.FK04_Clasificacion_ConfiguracionPrecio = CPC.PK_ConfiguracionPrecioClasificacion ");
			sbQuery.append(" \n LEFT JOIN (SELECT * FROM Categoria_PrecioLista) CAT ON CAT.PK_Categoria = PROD.FK03_Categoria_PrecioLista ");
			sbQuery.append(" \n LEFT JOIN (SELECT * FROM Configuracion_Precio) AS CP ON CP.PK_Configuracion_Precio = case when CPC.FK02_ConfigClasificacion is not null then CPC.FK02_ConfigClasificacion else CPC.FK01_ConfigFamilia end  ");
			sbQuery.append(" \n LEFT JOIN (SELECT * FROM ConfiguracionPrecio_Producto) AS CPP ON CPP.FK01_Producto = PROD.idProducto and cpp.FK02_ConfFamilia = CPC.FK01_ConfigFamilia ");
			sbQuery.append(" \n LEFT JOIN ( SELECT Clave, CASE WHEN Moneda = MonedaVenta THEN 1 ELSE CASE WHEN Tipo_Cambio IS NULL OR Tipo_Cambio = 0 THEN 1 ELSE Tipo_Cambio END END  AS TC, MonedaVenta, Moneda FROM Proveedores) AS PROVEE ON PROVEE.Clave = CP.FK01_Proveedor ");
			sbQuery.append(" \n LEFT JOIN (select FK03_Categoria_PrecioLista, MAX(idProducto) id from Productos  group by FK03_Categoria_PrecioLista) as p on p.FK03_Categoria_PrecioLista = cat.PK_Categoria ");
			sbQuery.append(" \n LEFT JOIN (SELECT * FROM Costo_Y_Factor) AS CF ON CF.PK_Costo_Factor=CP.FK02_Costo_Factor");
			sbQuery.append(" \n LEFT JOIN (SELECT PK_Lugar_AgenteAduanal, FK01_AgenteAduanal, Monto, Porcentaje FROM Lugar_AgenteAduanal) AS LAA ON LAA.PK_Lugar_AgenteAduanal = CF.FK02_LugarAgenteAduanal ");
			sbQuery.append(" \n LEFT JOIN (SELECT * FROM LAA_Concepto) AS LAAC ON LAAC.PK_AAConcepto = CF.FK03_LAAConcepto    ");
			sbQuery.append(" \n LEFT JOIN (SELECT * FROM Licencia) AS LICE ON LICE.FK01_Proveedor = CP.FK01_Proveedor AND LICE.Tipo = PROD.DepositarioInternacional ");
			sbQuery.append(" \n LEFT JOIN(SELECT * FROM ValorCombo WHERE Concepto='Industrial') AS IND ON IND.PK_Folio = CP.Industria   ");
			sbQuery.append(" \n LEFT JOIN (SELECT * FROM Cliente_ConfiguracionPrecio) AS CliCP2 ON CliCP2.AK_ClienteConfigPrecio = CPP.FK09_CliClasificacion  AND CliCP2.FK01_Cliente = @idCliente "); 
			sbQuery.append(" \n LEFT JOIN (SELECT * FROM Cliente_ConfiguracionPrecio) AS CliCP3 ON CliCP3.AK_ClienteConfigPrecio = CPP.FK05_CliFamilia AND CliCP3.FK01_Cliente = @idCliente "); 
			sbQuery.append(" \n LEFT JOIN (SELECT * FROM CostoFactor_Licencia) CFL ON CFL.FK01_CostoFactor = CF.PK_Costo_Factor AND CFL.Licencia = PROD.DepositarioInternacional");
			sbQuery.append(" \n WHERE CPC.PK_ConfiguracionPrecioClasificacion = @idClasificacion");
			sbQuery.append(" \n GROUP BY  CAT.PK_Categoria, CP.FK01_Proveedor,PROD.DepositarioInternacional, PROD.idProducto, CPP.FK02_ConfFamilia, CF.ValorEnAduana, CF.Flete, CF.Descuento, CF.Factor_Distribuidor, CF.Factor_Publico,   ");
			sbQuery.append(" \n CASE WHEN CliCP2.CostoFijo is Not null  THEN CliCP2.CostoFijo WHEN CliCP3.CostoFijo is Not null  THEN CliCP3.CostoFijo ELSE CF.Factor_CostoFijo END,CF.Factor_"
					+ tipoNivelIngreso + ",");
			sbQuery.append(" \n CASE WHEN CliCP2.FactorTemp IS NOT NULL THEN CliCP2.FactorTemp	ELSE CASE WHEN CliCP3.FactorTemp IS NOT NULL THEN CliCP3.FactorTemp ELSE 0 END END ,  ");
			sbQuery.append(" \n CASE WHEN CliCP2.CostoFijoTemp is Not null  THEN CliCP2.CostoFijoTemp WHEN CliCP3.CostoFijoTemp is Not null  THEN CliCP3.CostoFijoTemp ELSE 0 END , ");
			sbQuery.append(" \n CASE WHEN CliCP2.Caduca is Not null  THEN CliCP2.Caduca WHEN CliCP3.Caduca is Not null  THEN CliCP3.Caduca ELSE null END , ");
			sbQuery.append(" \n CF.Piezas,CF.Costo_Consularizacion,CF.Flete_Documentacion,CF.Permiso,CF.AlmacenDestino,PROD.Costo,PROVEE.TC,LICE.Factor,");
			sbQuery.append(" \n CF.Factor_IGI, CF.FactorDTA,LAAC.Monto, LAAC.Monto, LAAC.Porcentaje,CliCP2.Factor,CliCP3.Factor,PROVEE.Moneda, PROVEE.MonedaVenta,CPC.FK02_ConfigClasificacion, ");
			sbQuery.append(" \n CPP.FK09_CliClasificacion,CPP.FK05_CliFamilia,CPP.FK08_ConfClasificacion, CFL.Monto,CFL.Porcentaje, ");
			sbQuery.append(" \n coalesce(CliCP2.CompuestaCostoF , CliCP3.CompuestaCostoF, CF.CompuestaCostoF, 1 )  ,  ");
			sbQuery.append(" \n coalesce(CliCP2.CompuestaFactorU , CliCP3.CompuestaFactorU, CF.CompuestaFactorU, 1 )    ,");
			sbQuery.append(" \n coalesce(CliCP2.CompuestaCostoFTemp , CliCP3.CompuestaCostoFTemp,  1 )  ,  ");
			sbQuery.append(" \n coalesce(CliCP2.CompuestaFactorUTemp , CliCP3.CompuestaFactorUTemp,  1 )   ");
			sbQuery.append(" \n ORDER BY PK_Configuracion_Precio,CAST((PROD.Costo * PROVEE.TC) AS DECIMAL (9,2)) ASC");

			//logger.info(sbQuery.toString());

			return super.jdbcTemplate.query(sbQuery.toString(),
					new ConfiguracionPrecioClasificacionClienteRowMapper());

		} catch (Exception e) {
			log.info("Error:" + e.getMessage());
			return null;
		}
	}

	@Override
	public FormulaPrecio getInformacionFormulaPrecioClasificacionTemp(
			Long idProveedor, Long idProducto, int stock_flete, String nivel,
			Long idCliente, Long idConfig) throws ProquifaNetException {
		StringBuilder sql = new StringBuilder("");
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			String Q = 	" 	CASE WHEN CFL.Monto is not null and CFL.Monto > 0  " +
					"					THEN CFL.Monto " +
					"				 WHEN (CFL.Monto is null or CFL.Monto < 1 ) and CFL.Porcentaje > 0 " +
					"					THEN (1 + (CFL.Porcentaje/100)) " +
					" 				else" +
					"					 (1 + ((CASE WHEN LICE.Factor = -1 OR  LICE.Factor IS NULL THEN 0 ELSE LICE.Factor /100 END))) " +
					"				END ";	

			String O = 	" (CASE WHEN CF.ValorEnAduana = 0 OR CF.ValorEnAduana IS NULL THEN 1 ELSE CF.ValorEnAduana END) ";
			String R = 	" (CASE WHEN CF.Descuento IS NULL THEN 1 ELSE (1 - (CF.Descuento/100)) END)";	
			String PrecioL = "((PROD.Costo * PROVEE.TC) * (" + R + ")) ";
			String B = 	" (CASE WHEN CF.Flete = 0 OR  CF.Flete IS NULL THEN 0 ELSE CF.Flete END )";
			String N  = 	"\n CASE WHEN CF.VALORENADUANA <= 0 OR CF.VALORENADUANA IS NULL THEN case when CF.Piezas is null or CF.Piezas = 0 then 1 else CF.Piezas end " +
					" ELSE " +
					"	CASE WHEN  (CFL.Monto is not null and CFL.Monto > 0)  THEN " +
					"		CEILING((" + O + " - " + B + ")/(" + PrecioL + " +  " + Q +"))  " +
					"	ELSE " +
					"		CEILING((" + O + " - " + B + ")/(" + PrecioL + " *  " + Q +"))  " +
					"	END " +
					" END \n";

			N = " case when " + N + " = 0 then 1 else " + N + " end ";

			String VA =    " CASE WHEN (CFL.Monto is not null and CFL.Monto > 0)  " +
					" THEN ( (" + N + " * (" + PrecioL + "  +  " + Q +") ) + " + B +") " +
					" ELSE ( (" + N + " * " + PrecioL + "  * " + Q +" ) + " + B +") END	";
			String Valor = "(" + PrecioL + ") * (" + N + ")";
			String L = 	" ROUND( (CASE WHEN (CASE WHEN LAAC.Monto = 0  OR  LAAC.Monto IS NULL THEN LAAC.Porcentaje ELSE LAAC.Monto END) IS NULL THEN 0 " +
					" ELSE CASE WHEN LAAC.Monto = 0  OR  LAAC.Monto IS NULL THEN (" + VA + ") * ((LAAC.Porcentaje)/100) ELSE LAAC.Monto END END) ,3,1)";

			String M = 	" ROUND( (CASE WHEN CF.Permiso = -1 OR  CF.Permiso IS NULL THEN 0 ELSE CF.Permiso END) ,3,1)";
			String I = 	" ROUND( (CASE WHEN CF.AlmacenDestino = -1 OR  CF.AlmacenDestino IS NULL  THEN 0 ELSE CF.AlmacenDestino END) ,3,1)";


			sql.append(" \n DECLARE @idProveedor AS Integer= " + idProveedor);
			sql.append(" \n DECLARE @idProducto AS INTEGER = " + idProducto);
			sql.append(" \n DECLARE @FLETE_STOCK AS INTEGER = " + stock_flete);
			sql.append(" \n DECLARE @idCliente AS Integer=" + idCliente + "\n ");
			sql.append(" \n DECLARE @idConfigFamilia AS Integer=" + idConfig + "\n ");
			sql.append(" \n SELECT DISTINCT (CASE WHEN CF.FLETE = 0 OR  CF.FLETE IS NULL THEN 0 ELSE CF.FLETE END ) B,");
			sql.append(" \n (CASE WHEN CF.Costo_Consularizacion = -1 OR  CF.Costo_Consularizacion IS NULL THEN 0 ELSE CF.Costo_Consularizacion END) C,");
			sql.append(" \n (CASE WHEN CF.Flete_Documentacion = -1 OR  CF.Flete_Documentacion IS NULL THEN 0 ELSE CF.Flete_Documentacion END) D,");
			sql.append(" \n (CASE WHEN CF.Factor_IGI = -1 OR  CF.Factor_IGI IS NULL THEN 0 ELSE CF.Factor_IGI END) J,");
			sql.append(" \n (CASE WHEN CF.FactorDTA = -1 OR  CF.FactorDTA IS NULL THEN 0 ELSE CF.FactorDTA END) K,");
			sql.append(" \n " + PrecioL  + " PRECIOLISTA,");
			sql.append(" \n CF.Descuento R,");
			sql.append(" \n " + Q  + " Q,");	
			sql.append(" \n " + O  + " O,");	
			sql.append(" \n " + N  + " N,");
			sql.append(" \n " + VA + " VA,");
			sql.append(" \n " + Valor + " Valor,");
			sql.append(" \n " + L  + " L,");
			sql.append(" \n " + M  + " M,");
			sql.append(" \n " + I  + " I,");
			sql.append(" \n CASE WHEN CliCP2.FactorTemp is not null THEN CliCP2.FactorTemp	WHEN  CliCP3.FactorTemp is not null THEN CliCP3.FactorTemp ELSE 0  END G,");
			sql.append(" \n CASE WHEN CliCP2.CostoFijoTemp is not null THEN CliCP2.CostoFijoTemp	WHEN  CliCP3.CostoFijoTemp is not null THEN CliCP3.CostoFijoTemp ELSE 0 END F,");
			sql.append(" \n coalesce( CliCP2.CompuestaCostoF , CliCP3.CompuestaCostoF, CF.CompuestaCostoF, 1 )  CompuestaCostoF,  ");
			sql.append(" \n coalesce( CliCP2.CompuestaFactorU , CliCP3.CompuestaFactorU, CF.CompuestaFactorU, 1 )  CompuestaFactorU,  ");

			sql.append(" \n ROUND( (CASE WHEN CF.Permiso = -1 OR  CF.Permiso IS NULL THEN 0 ELSE CF.Permiso END), 2,1) M,");
			sql.append(" \n ROUND( (CASE WHEN CF.AlmacenDestino = -1 OR  CF.AlmacenDestino IS NULL  THEN 0 ELSE CF.AlmacenDestino END), 2,1) I");
			sql.append(" \n FROM ConfiguracionPrecio_Producto AS CPP  ");
			sql.append(" \n LEFT JOIN(SELECT * FROM Configuracion_Precio) AS CP ON CP.PK_Configuracion_Precio = case when CPP.FK08_ConfClasificacion is Not null then CPP.FK08_ConfClasificacion else cpp.FK02_ConfFamilia end");
			sql.append(" \n LEFT JOIN(SELECT Clave, CASE WHEN Moneda = MonedaVenta THEN 1 ELSE CASE WHEN Tipo_Cambio IS NULL OR Tipo_Cambio = 0 THEN 1 ELSE Tipo_Cambio END END  AS TC, MonedaVenta FROM Proveedores) AS PROVEE ON PROVEE.Clave = CP.FK01_Proveedor ");
			sql.append(" \n LEFT JOIN(SELECT * FROM Productos) AS PROD ON PROD.idProducto= CPP.FK01_Producto ");
			sql.append(" \n LEFT JOIN(SELECT * FROM Costo_Y_Factor) AS CF ON CF.PK_Costo_Factor=CP.FK02_Costo_Factor    ");

			sql.append(" LEFT JOIN (SELECT * FROM Cliente_ConfiguracionPrecio) AS CliCP2 ON CliCP2.AK_ClienteConfigPrecio = CPP.FK09_CliClasificacion AND CliCP2.FK01_Cliente = @idCliente  \n");
			sql.append(" LEFT JOIN (SELECT * FROM Cliente_ConfiguracionPrecio) AS CliCP3 ON CliCP3.AK_ClienteConfigPrecio = CPP.FK05_CliFamilia AND CliCP3.FK01_Cliente = @idCliente  \n");
			sql.append(" \n LEFT JOIN (SELECT * FROM LAA_Concepto) AS LAAC ");
			sql.append(" \n		ON LAAC.PK_AAConcepto = CASE WHEN CliCP2.Fk06_LAAConcepto <> 0 THEN CliCP2.Fk06_LAAConcepto	WHEN  CliCP3.Fk06_LAAConcepto <> 0 THEN CliCP3.Fk06_LAAConcepto ELSE CF.FK03_LAAConcepto END");

			sql.append(" \n LEFT JOIN(SELECT PROD.idProducto,PROD.Proveedor, PROD.FK01_Configuracion_Precio, PROD.Costo,((((SUM(CPP.CostoNuevo - CPP.CostoAnTErior))/case when (COUNT(CPP.CostoNuevo)) = 0 then 1 else COUNT(CPP.CostoNuevo) end)*100 )/(CPP.CostoNuevo ");   
			sql.append(" \n          -((SUM(CPP.CostoNuevo - CPP.CostoAnTErior))/case when (COUNT(CPP.CostoNuevo)) = 0 then 1 else COUNT(CPP.CostoNuevo) end))) AS PORCIENTO   ");
			sql.append(" \n          FROM Productos AS PROD   ");
			sql.append(" \n          LEFT JOIN(SELECT MAX (PK_CambioPrecioProducto) ID, FK01_Producto FROM CambioPrecioProducto GROUP BY FK01_Producto) AS ID ON ID.FK01_Producto =PROD.idProducto ");  
			sql.append(" \n          LEFT JOIN(SELECT * FROM CambioPrecioProducto) AS CPP ON CPP.PK_CambioPrecioProducto = ID.ID    ");
			sql.append(" \n          GROUP BY CPP.CostoNuevo,PROD.Proveedor,PROD.FK01_Configuracion_Precio, PROD.Costo,PROD.idProducto) "); 
			sql.append("  \n         AS PORC ON PORC.idProducto = PROD.idProducto  ");
			sql.append(" \n LEFT JOIN (SELECT * FROM Licencia) AS LICE ON LICE.FK01_Proveedor = CP.FK01_Proveedor AND LICE.Tipo = PROD.DepositarioInternacional");
			sql.append(" \n LEFT JOIN (SELECT * FROM CostoFactor_Licencia) CFL ON CFL.FK01_CostoFactor = CF.PK_Costo_Factor AND CFL.Licencia = PROD.DepositarioInternacional" );

			sql.append("  \n WHERE CP.FK01_Proveedor=@idProveedor  AND PROD.idProducto = @idProducto AND ");
			sql.append("  \n  CASE WHEN CliCP2.Factor is Not null then CPP.FK09_CliClasificacion WHEN CliCP3.Factor is Not null then  CPP.FK05_CliFamilia   ");
			sql.append("  \n  WHEN CPP.FK08_ConfClasificacion is Not null then CPP.FK08_ConfClasificacion else cpp.FK02_ConfFamilia END  = @idConfigFamilia");

			log.info(sql.toString());
			return (FormulaPrecio) this.jdbcTemplate.queryForObject (sql.toString(), map, new FormulaPrecioRowMapper());

		}catch(Exception e){
			log.info(e.getMessage());
			f = new Funcion();
			f.enviarCorreoAvisoExepcion(e,"sql: " + sql, "idProveedor: " + idProveedor,"idProducto: " + idProducto,
					"stock_flete: " + stock_flete, "nivel: " + nivel, "idCliente: " + idCliente, "idConfig: ", idConfig);
			return new FormulaPrecio();
		}
	}

	@Override
	public Boolean reintegrarConfiguracionesPorNivel(String columna,
			Long idCliente, Long idConfigFamilia) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String sql = " UPDATE Cliente_ConfiguracionPrecio SET Factor = NULL, FK05_LugarAgenteAduanal = NULL, Fk06_LAAConcepto = NULL, FK03_TiempoEntrega = NULL, FK04_AgenteAduanal = NULL, CompuestaCostoF = NULL, CompuestaFactorU = NULL"
					+ " WHERE PK_ClienteConfiguracionPrecio IN ("
					+ "			SELECT PK_ClienteConfiguracionPrecio FROM Cliente_ConfiguracionPrecio "
					+ "			WHERE FK01_Cliente = "
					+ idCliente
					+ "  AND AK_ClienteConfigPrecio IN (SELECT "
					+ columna
					+ " FROM ConfiguracionPrecio_Producto WHERE FK02_ConfFamilia = "
					+ idConfigFamilia + "))";
			//logger.info(sql);
			this.jdbcTemplate.update(sql, map);

			sql = "DELETE FROM Tiempo_Entrega WHERE PK_Tiempo_Entrega IN ("
					+ "	 SELECT PK_Tiempo_Entrega FROM Tiempo_Entrega WHERE   PK_Tiempo_Entrega NOT IN (SELECT FK03_TiempoEntrega "
					+ " FROM Cliente_ConfiguracionPrecio WHERE FK03_TiempoEntrega IS NOT NULL) AND PK_Tiempo_Entrega NOT IN (SELECT FK03_Tiempo_Entrega FROM Configuracion_Precio WHERE FK03_Tiempo_Entrega IS NOT NULL ) )";
			//logger.info(sql);
			this.jdbcTemplate.update(sql, map);

			return true;

		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Direccion> findDireccionesCliente(Long idCliente)
			throws ProquifaNetException {
		try {
			String query = "\n SELECT PK_Direccion, Igual, Pais, CP, Estado, Municipio, Ciudad, CalleNum, Colonia, Zona,"
					+ "\n Ruta, Mapa, Altitud, Latitud, Longitud, FUA,visita, FK01_Cliente FROM Direccion WHERE Habilitado = 1 AND  FK01_Cliente = "
					+ idCliente;
			log.info(query);
			return super.jdbcTemplate.query(query, new DireccionRowMapper());
		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	public List<Comentario> findComentariosCliente(Long idCliente)
			throws ProquifaNetException {
		try {
			String query = " \n SELECT PK_Comentario,FK01_Cliente,Seccion,Tema,Contenido "
					+ " \n FROM Comentarios "
					+ " \n WHERE FK01_Cliente= "
					+ idCliente;

			//logger.info("QueryComentarios--->:   "+sbQuery.toString());
			return super.jdbcTemplate.query(query.toString(),
					new ComentarioClienteRowMapper());

		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CarteraCliente> findCarteras(String parametros,
			List<NivelIngreso> niveles)
	//  se redirecciona a queryCarteraCliente

					throws ProquifaNetException {
		return queryCarteraCliente(parametros, niveles, 0L);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CatalogoItem> obtenerUsoCFDI() throws ProquifaNetException {
		try{
			StringBuilder sbQuery = new StringBuilder("select pk_folio as clave,  valor as nombre  from ValorCombo where concepto = 'UsoCFDI' \n");
			//			logger.info(sbQuery.toString());
			return  super.jdbcTemplate.query(sbQuery.toString(), new CatalogoItemRowMapper());
		}catch(Exception e){
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CatalogoItem> obtenerMetodoPago() throws ProquifaNetException {
		// TODO Auto-generated method stub
		try{
			StringBuilder sbQuery = new StringBuilder("select PK_Folio AS clave, valor AS nombre from  ValorCombo where Concepto ='MetodoPago' \n");
			//			logger.info(sbQuery);
			return  super.jdbcTemplate.query(sbQuery.toString(), new CatalogoItemRowMapper());
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<CatalogoItem> obtenerClaveUnidad() throws ProquifaNetException {
		try{
			StringBuilder sbQuery = new StringBuilder ("select PK_Folio as clave, ClaveUnidad + ' ' + Nombre as Nombre from c_ClaveUnidad ");
			return super.jdbcTemplate.query(sbQuery.toString(), new CatalogoItemRowMapper());
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<CatalogoItem> obtenerClaveProdServ() throws ProquifaNetException {
		try{
			StringBuilder sbQuery = new StringBuilder("select PK_Folio as clave, ClaveProdServ + ' ' + convert(varchar(max), Descripcion) as nombre from c_ClaveProdServ");
			return super.jdbcTemplate.query(sbQuery.toString(), new CatalogoItemRowMapper());
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
