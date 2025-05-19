package com.proquifa.net.persistencia.despachos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.proquifa.net.modelo.compras.Pieza;
import com.proquifa.net.modelo.comun.Archivo;
import org.springframework.jdbc.core.RowMapper;

public class PiezaRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {

		Pieza objPieza = new Pieza();
		Boolean piezaDespachable = false;
		Archivo archivo = null;
		String lote = "", mes = "", anoCaduca = "", tipoDocumento = "";
		
		String tipoFotos[] = { "", "Foto de arriba", "Foto de abajo", "Foto de frente", "Foto de etiqueta con lote",
				"Foto de etiqueta con caducidad", "Foto de etiqueta con catalogo, concepto o presentacion" };
		
		String folioArchivo[] = { "Documento", "FArriba", "FAbajo", "FFrente", "FEtiquetaLote", "FEtiquetaCaducidad",
				"FEtiquetaCCP" };

		objPieza.setIdPieza(rs.getLong("idPieza"));
		objPieza.setCodigo(rs.getString("Codigo"));
		objPieza.setFabrica(rs.getString("Fabrica"));
		objPieza.setIdPCompra(rs.getLong("IdPCompra"));
		objPieza.setRevisoCatalogo(rs.getBoolean("Catalogo"));
		objPieza.setRevisoDescripcion(rs.getBoolean("Descripcion"));
		objPieza.setRevisoEdicion(rs.getBoolean("Edicion"));
		objPieza.setRevisoIdioma(rs.getBoolean("Idioma"));
		objPieza.setRevisoFisicamenteC(rs.getBoolean("FisicamenteC"));
		objPieza.setComentarioRechazo(rs.getString("Rechazos"));
		objPieza.setRevisoPresentacion(rs.getBoolean("Presentacion"));
		objPieza.setRevisoLote(rs.getBoolean("Lote"));
		objPieza.setRevisoCaducidad(rs.getBoolean("Caducidad"));
		objPieza.setRevisoDocumentacion(rs.getBoolean("Documentacion"));

		lote = rs.getString("LoteTxt");
		if (rs.wasNull()) {
			objPieza.setLoteTxt("No especificado");
		} else {
			objPieza.setLoteTxt(lote);
		}

		mes = rs.getString("MesCaducidad");
		if (rs.wasNull()) {
			objPieza.setMesCaduca("No especificado");
		} else {
			objPieza.setMesCaduca(mes);
		}

		anoCaduca = rs.getString("AnoCaducidad");
		if (rs.wasNull()) {
			objPieza.setAnoCaduca("No especificado");
		} else {
			objPieza.setAnoCaduca(anoCaduca);
		}

		piezaDespachable = rs.getBoolean("Despachable");
		if (rs.wasNull()) {
			objPieza.setEdoPieza("Pendiente");
			objPieza.setDespachable(false);
		} else {
			if (piezaDespachable) {
				objPieza.setEdoPieza("Despachable");
				objPieza.setDespachable(true);
			} else {
				objPieza.setEdoPieza("No Despachable");
				objPieza.setDespachable(false);
			}
		}

		tipoDocumento = rs.getString("TipoDocumento");
		if (tipoDocumento != null) {
			tipoFotos[0] = tipoDocumento;
		} else {
			tipoFotos[0] = "Certificado";
		}

		ArrayList<Archivo> fotos = new ArrayList<Archivo>();
		for (int i = 0; i < folioArchivo.length; i++) {
			String folioDeLaColumna = rs.getString(folioArchivo[i]);
			if (folioDeLaColumna != null) {
				archivo = new Archivo();
				archivo.setDescripcion(tipoFotos[i]);
				archivo.setFolio(folioDeLaColumna);
				fotos.add(archivo);
			}
		}

		objPieza.setListArchivos(fotos);
		objPieza.setInstrucciones(rs.getString("Instrucciones"));
		objPieza.setAStock(rs.getBoolean("AStock"));
		objPieza.setVerificoPieza(rs.getBoolean("DespachoSinEditar"));
		return objPieza;

	}

}