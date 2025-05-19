package com.proquifa.net.persistencia.comun.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.comun.Cartera;
import com.proquifa.net.modelo.comun.CarteraCliente;
import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.NivelIngreso;
import com.proquifa.net.modelo.comun.ValorCombo;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.comun.CatalogoClienteDAO;
import com.proquifa.net.persistencia.comun.impl.mapper.CarterasClientesRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.CarterasRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.ClienteSinCarteraRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.LongMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.MontosGeneralesCarterasRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.ValorComboRowMapper;


@Repository 
public class CatalogoClienteDAOImpl extends DataBaseDAO implements CatalogoClienteDAO {
	Funcion f;
	
	final Logger log = LoggerFactory.getLogger(CatalogoClienteDAOImpl.class);

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
//			if (cartera.getIdCorporativo() != 0) {
//				
//				if(cartera.getArea().equals("Direccion")){
//					if (cartera.isCart_updateESAC()) {
//						nombreCartera = "  \n ( select  upper((select apellido  from Empleados where Clave =  @idEsac) + ' · '+  (select nombre from Corporativo where PK_Corporativo = @idCorporativo ))) ";
//					} else {
//						nombreCartera = "  \n  (select nombre from cartera where pk_cartera = @idcartera ) ";
//					}
//				}
//				
//				else if(cartera.getArea().equals("ESAC")){
//					if (cartera.isCart_updateESAC() && cartera.getArea().equals(cartera.getAreaActual())) {
//						nombreCartera = "  \n ( select  upper((select apellido  from Empleados where Clave =  @idEsac) + ' · '+  (select nombre from Corporativo where PK_Corporativo = @idCorporativo ))) ";
//					} else {
//						nombreCartera = "  \n  (select nombre from cartera where pk_cartera = @idcartera ) ";
//					}
//					
//				}
//				else if(cartera.getArea().equals("Ventas")){
//					if (cartera.isCart_updateEV() && cartera.getArea().equals(cartera.getAreaActual())) {
//						nombreCartera = "  \n ( select  upper((select apellido  from Empleados where Clave =  @idEv) + ' · '+  (select nombre from Corporativo where PK_Corporativo = @idCorporativo ))) ";
//					} else {
//						nombreCartera = "  \n  (select nombre from cartera where pk_cartera = @idcartera ) ";
//					}
//				}
//				
//				else if(cartera.getArea().equals("Finanzas")){
//					if (cartera.isCart_updateCOBRADOR() && cartera.getArea().equals(cartera.getAreaActual())) {
//						nombreCartera = "  \n ( select  upper((select apellido  from Empleados where Clave =  @idCobrador) + ' · '+  (select nombre from Corporativo where PK_Corporativo = @idCorporativo ))) ";
//					} else {
//						nombreCartera = "  \n  (select nombre from cartera where pk_cartera = @idcartera ) ";
//					}
//					
//				}
//				
//			} else {
//				
//				if (cartera.getArea().equals("Direccion")){
//					nombreCartera = "  \n 	(select UPPER( COALESCE(apellido, 'SINAPELLIDO') +'. SECTOR' + CONVERT (VARCHAR (15),CONSECUTIVO) ) NUEVONOMBRE  from Empleados  "
//							+ "  \n LEFT JOIN (SELECT COUNT (1)+1 CONSECUTIVO , FK02_Esac  FROM Cartera  where publicada  = 1 and corporativo  = 0  GROUP BY FK02_Esac  ) AS CART ON CART.FK02_Esac = Empleados.Clave  "
//							+ "  \n where Nivel in (8, 41) and Fase >0 and Empleados.Clave  = @idEsac ) ";
//					
//				}
//				else if(cartera.getArea().equals("ESAC") && cartera.getArea().equals(cartera.getAreaActual())){
//					nombreCartera = "  \n 	(select UPPER( COALESCE(apellido, 'SINAPELLIDO') +'. SECTOR' + CONVERT (VARCHAR (15),CONSECUTIVO) ) NUEVONOMBRE  from Empleados  "
//							+ "  \n LEFT JOIN (SELECT COUNT (1)+1 CONSECUTIVO , FK02_Esac  FROM Cartera  where publicada  = 1 and corporativo  = 0  GROUP BY FK02_Esac  ) AS CART ON CART.FK02_Esac = Empleados.Clave  "
//							+ "  \n where Nivel in (8, 41) and Fase >0 and Empleados.Clave  = @idEsac ) ";
//				}
//				
//				else if(cartera.getArea().equals("Ventas") && cartera.getArea().equals(cartera.getAreaActual())){
//					nombreCartera = "  \n 	(select UPPER( COALESCE(apellido, 'SINAPELLIDO') +'. SECTOR' + CONVERT (VARCHAR (15),CONSECUTIVO) ) NUEVONOMBRE  from Empleados  "
//							+ "  \n LEFT JOIN (SELECT COUNT (1)+1 CONSECUTIVO , FK01_EV  FROM Cartera  where publicada  = 1 and corporativo  = 0  GROUP BY FK01_EV  ) AS CART ON CART.FK01_EV = Empleados.Clave  "
//							+ "  \n where Fase >0 and Empleados.Clave  = @idEv ) ";
//					
//				}
//				else if(cartera.getArea().equals("Finanzas") && cartera.getArea().equals(cartera.getAreaActual())){
//					nombreCartera = "  \n 	(select UPPER( COALESCE(apellido, 'SINAPELLIDO') +'. SECTOR' + CONVERT (VARCHAR (15),CONSECUTIVO) ) NUEVONOMBRE  from Empleados  "
//							+ "  \n LEFT JOIN (SELECT COUNT (1)+1 CONSECUTIVO , FK03_Cobrador  FROM Cartera  where publicada  = 1 and corporativo  = 0  GROUP BY FK03_Cobrador  ) AS CART ON CART.FK03_Cobrador = Empleados.Clave  "
//							+ "  \n where  Fase >0 and Empleados.Clave  = @idCobrador ) ";
//				}
//				else{
//					nombreCartera = "  \n  (select nombre from cartera where pk_cartera = @idcartera ) ";
//				}
//				
//			}

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
//			if (cartera.isCart_updateESAC()) {
//
//				sbQuery.append("\n DECLARE @NombreCartera AS varchar (99)  ");
//				sbQuery.append("\n  select @NombreCartera = (select UPPER( apellido +' · SECTORB' + CONVERT (VARCHAR (15),CONSECUTIVO) ) NUEVONOMBRE  from Empleados  ");
//				sbQuery.append("\n  INNER JOIN (SELECT COUNT (1)+1 CONSECUTIVO , FK02_Esac  FROM Cartera where publicada = 0 GROUP BY FK02_Esac  ) AS CART ON CART.FK02_Esac = Empleados.Clave  ");
//				sbQuery.append("\n  where Nivel in (8, 41) and Fase >0 and Fk02_Esac =  "
//						+ cartera.getEsac() + " ) ");
//				nombreCartera = "@NombreCartera";
//			} 
//			
//			
//			else if (cartera.isCart_updateEV()){
//				sbQuery.append("\n DECLARE @NombreCartera AS varchar (99)  ");
//				sbQuery.append("\n  select @NombreCartera = (select UPPER( apellido +' · SECTORB' + CONVERT (VARCHAR (15),CONSECUTIVO) ) NUEVONOMBRE  from Empleados  ");
//				sbQuery.append("\n  INNER JOIN (SELECT COUNT (1)+1 CONSECUTIVO , FK01_EV  FROM Cartera where publicada = 0 GROUP BY FK01_EV  ) AS CART ON CART.FK01_EV = Empleados.Clave  ");
//				sbQuery.append("\n  where Fase >0 and FK01_EV =  "
//						+ cartera.getEv() + " ) ");
//				nombreCartera = "@NombreCartera";
//			
//			}
//			
//			
//			else if(cartera.isCart_updateCOBRADOR()){
//				sbQuery.append("\n DECLARE @NombreCartera AS varchar (99)  ");
//				sbQuery.append("\n  select @NombreCartera = (select UPPER( apellido +' · SECTORB' + CONVERT (VARCHAR (15),CONSECUTIVO) ) NUEVONOMBRE  from Empleados  ");
//				sbQuery.append("\n  INNER JOIN (SELECT COUNT (1)+1 CONSECUTIVO , FK03_Cobrador  FROM Cartera where publicada = 0 GROUP BY FK03_Cobrador  ) AS CART ON CART.FK03_Cobrador = Empleados.Clave  ");
//				sbQuery.append("\n  where Fase >0 and FK03_Cobrador =  "
//						+ cartera.getCobrador() + " ) ");
//				nombreCartera = "@NombreCartera";
//				
//			}
//				
//			else{
				nombreCartera = "'" + cartera.getNombre() + "'";
//			}
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
}
