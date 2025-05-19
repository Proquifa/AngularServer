package com.proquifa.net.persistencia.consultas.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.ventas.enviodocumentos.EnvioDocumentos;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.consultas.ConsultaEnvioDocumentosDAO;
import com.proquifa.net.persistencia.consultas.impl.mapper.ConsultaEnvioDocumentosRowMapper;

@Repository
public class ConsultaEnviaDocumentosDAOImpl extends DataBaseDAO implements
		ConsultaEnvioDocumentosDAO {
	
	final Logger log = LoggerFactory.getLogger(ConsultaEnviaDocumentosDAOImpl.class);

	@SuppressWarnings("unchecked")
	public List<EnvioDocumentos> findDocumentosEnviados(String condiciones)
			throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			//map.put("condiciones", condiciones);
			String query = 
					"SELECT coalesce(FInicio,co.FechaInicio) as FInicio,coalesce(FFin,co.FechaInicio) as FFin, co.idCorreo, co.Destino AS cliente, co.Origen, PEND.Tipo COLLATE Modern_Spanish_CI_AS AS tipoCorreo , co.FK01_Empleado AS empleado, co.FolioDocumento, Pend.Tipo , Pend.Folio, cliente.Nombre, c.Contacto, f.FPor AS Facturado, pf.FPor, Pend.Docto, pf.Clave  " + 
			        "\n FROM Correos AS co " +
			        "\n INNER JOIN (SELECT * FROM Pendiente where Tipo in ( 'Confirmacion por enviar','Cotizaciones por enviar','Facturas por enviar','Notificaciones por enviar','Pedidos por enviar','Proforma por enviar','Seguimiento por enviar')) AS Pend ON Pend.Partida = CAST(co.idCorreo AS varchar) " +
			        "\n LEFT JOIN (SELECT cl.Clave, cl.Nombre FROM Clientes AS cl) AS cliente ON cliente.Clave = co.Destino " + 
			        "\n LEFT JOIN (SELECT idContacto,Contacto FROM Contactos) AS c ON c.idContacto = co.idContacto " + 
			        "\n LEFT JOIN (SELECT * FROM Facturas)AS f ON f.CPedido = Pend.Partida AND F.Factura = Pend.Docto " + 
			        "\n LEFT JOIN (SELECT * FROM Pedidos) AS pd ON pd.CPedido = Pend.Docto " + 
			        "\n LEFT JOIN (SELECT * FROM Proforma) AS pf ON pf.FK02_Pedido = pd.idPedido " + 
					"\n LEFT JOIN (SELECT f.Nombre, e.Clave, e.Usuario FROM Empleados AS e, Funcion AS f WHERE e.FK01_Funcion = f.PK_Funcion) AS emp ON emp.Usuario = co.Origen " +
			        "\n WHERE " + condiciones + " \n UNION " +
			        "\n SELECT COALESCE (FInicio,co.FechaInicio) as FInicio,coalesce(FFin,co.FechaInicio) as FFin, co.idCorreo, co.Destino AS cliente, co.Origen, co.Tipo AS tipoCorreo, co.FK01_Empleado AS empleado, co.FolioDocumento, Pend.Tipo , Pend.Folio, cliente.Nombre, c.Contacto, f.FPor AS Facturado, PF.FPor, Pend.Docto, PF.Clave " +
			        "\n FROM Correos AS co " +
			        "\n LEFT JOIN (SELECT case when FolioDocumento like '%,%' then SUBSTRING(FolioDocumento, 0, CHARINDEX (',',FolioDocumento,1))else FolioDocumento end as Folio,idCorreo from Correos) as c2 on c2.idCorreo = co.idCorreo " +
			        "\n LEFT JOIN (SELECT * FROM Pendiente WHERE Tipo IN ('Confirmacion por enviar','Cotizaciones por enviar','Facturas por enviar','Notificaciones por enviar','Pedidos por enviar','Proforma por enviar','Seguimiento por enviar')) AS Pend ON (Pend.Partida = CAST(co.idCorreo AS varchar)) " +
			        "\n LEFT JOIN (SELECT cl.Clave, cl.Nombre FROM Clientes AS cl) AS cliente ON cliente.Clave = co.Destino " +
			        "\n LEFT JOIN (SELECT idContacto,Contacto FROM Contactos) AS c ON c.idContacto = co.idContacto " +
			        "\n LEFT JOIN (SELECT CPedido, idPedido,idCliente FROM Pedidos) AS PD ON c2.Folio = PD.CPedido AND PD.idCliente = co.Destino " +
			        "\n LEFT JOIN (SELECT * FROM Facturas)AS f ON f.Factura = c2.Folio AND F.FPor = co.FacturadaPor COLLATE SQL_Latin1_General_CP1_CI_AS " +
			        "\n LEFT JOIN (SELECT Clave FROM Cotizas)AS CT ON c2.Folio = CT.Clave " +
			        "\n LEFT JOIN (SELECT PK_Proforma, FPor,Clave FROM Proforma) AS PF ON c2.Folio = CAST(PF.PK_Proforma as varchar) " +
			        "\n LEFT JOIN (SELECT f.Nombre, e.Clave, e.Usuario FROM Empleados AS e, Funcion AS f WHERE e.FK01_Funcion = f.PK_Funcion) AS emp ON emp.Usuario = co.Origen " +
			        "\n WHERE " + condiciones + "  AND Pend.Folio IS NULL" +
			        "\n ORDER BY co.idCorreo DESC";
//			logger.info(query);
			return super.jdbcTemplate.query(query,map, new ConsultaEnvioDocumentosRowMapper());
		} catch (Exception e) {
		
			log.info(e.toString());
			return null;
		}
	}

}
