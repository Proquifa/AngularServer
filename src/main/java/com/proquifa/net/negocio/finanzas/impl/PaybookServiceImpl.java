package com.proquifa.net.negocio.finanzas.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csvreader.CsvWriter;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.finanzas.Paybook;
import com.proquifa.net.modelo.ventas.admoncomunicacion.Correo;
import com.proquifa.net.negocio.finanzas.PaybookService;
import com.proquifa.net.persistencia.finanzas.PaybookDAO;

@Service("paybookService")
public class PaybookServiceImpl implements PaybookService {
	@Autowired
	PaybookDAO paybookDAO;
	
	final Logger log = LoggerFactory.getLogger(PaybookServiceImpl.class);

	@Override
	public List<Paybook> obtenerTransactions(Date inicio, Date fin) throws ProquifaNetException {
		try {
			Date _inicio = inicio;
			Date _fin = fin;
			if (_inicio == null) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				calendar.add(Calendar.DATE, -1);
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
				_inicio = calendar.getTime();

				calendar.set(Calendar.HOUR_OF_DAY, 23);
				calendar.set(Calendar.MINUTE, 59);
				calendar.set(Calendar.SECOND, 59);
				_fin = calendar.getTime();

			}
			
			log.info(_inicio.toString());
			log.info(_fin.toString());
			List<Paybook> transactions = paybookDAO.obtenerTransactions(_inicio, _fin);

		    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");  
		    String strDate = dateFormat.format(_fin);  
		    
			String outputFile = "transactions_"+ strDate +".csv";
			boolean alreadyExists = new File(outputFile).exists();

			if (alreadyExists) {
				File ficheroUsuarios = new File(outputFile);
				ficheroUsuarios.delete();
			}

			try {

				CsvWriter csvOutput = new CsvWriter(new FileWriter(outputFile, true), ',');
				csvOutput.write("date");
				csvOutput.write("account");
				csvOutput.write("company");
				csvOutput.write("description");
				csvOutput.write("amount");
				csvOutput.write("caption");
				csvOutput.endRecord();

				for (Paybook registro : transactions) {
					Date date = new Date(registro.getDtTransaction() * 1000);
					DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
					String formatted = format.format(date);

					log.info(formatted);

					csvOutput.write(formatted);
					csvOutput.write(registro.getAccount().getAccount());
					csvOutput.write(registro.getAccount().getDescription());
					csvOutput.write(registro.getDescription());
					csvOutput.write("$" + registro.getAmount());
					csvOutput.write(registro.getCaptionExtra());

					csvOutput.endRecord();
				}

				csvOutput.close();

				File fileXMLAux = new File(outputFile);
				if (fileXMLAux.exists()) {
					Correo correo = new Correo();
					correo.setArchivoAdjunto(outputFile);
					correo.setCorreo("oscar.cardona@ryndem.mx");
					correo.setOrigen("notificaciones");					
					correo.setAsunto("CSV transaction");
					correo.setCuerpoCorreo("Archivo de transacciones: ");
					correo.setTipo("");

					Funcion funcion = new Funcion();
					funcion.enviarCorreo(correo);
				}
				fileXMLAux.delete();

			} catch (IOException e) {
				e.printStackTrace();
			}

			return transactions;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}

}
