package com.proquifa.net.persistencia.ventas.impl;




import com.proquifa.net.modelo.cobrosypagos.facturista.PartidaFactura;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.proquifa.net.persistencia.DataBaseDAO;



@Repository
public class FacturaFisicaDAOImpl {
    final Logger log = LoggerFactory.getLogger(FacturaFisicaDAOImpl.class);
    public List<PartidaFactura> getPartidasFacturaFisica(long idFactura){
        String query = "SELECT PF.Factura AS Folio, PF.FPor AS Alias, COALESCE(PC.Codigo, PPed.Codigo) AS Codigo, \n" +
                "COALESCE(PP.Concepto, PPed.Concepto) AS Descripcion, PF.Cant AS Cantidad, \n" +
                "PF.Importe AS ValorUnitario, PF.part  \n" +
                "FROM PFacturas AS PF\n" +
                "LEFT JOIN PCompras AS PC ON PC.idpCompra = PF.idPCompra\n" +
                "LEFT JOIN PPedidos AS PP ON PP.idPPedido = PC.Fk03_PPedido\n" +
                "LEFT JOIN PPedidos AS PPed ON PPed.CPedido = PF.CPedido AND PPed.Part = PF.PPedido\n" +
                "WHERE PF.FK01_Factura = "+ idFactura + "\n" +
                "ORDER BY PF.Part";

        log.info(query);
        //return super.jdbcTemplate.query(query, new PDFConfirmacionPedidoRowMapper());
        return  null;
    }
}
