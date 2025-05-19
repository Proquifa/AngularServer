package com.proquifa.net.negocio.envio;

//public class ShipWebServiceClientCopy {

//}

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.axis.types.NonNegativeInteger;
import org.apache.axis.types.PositiveInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.proquifa.net.modelo.comun.ParametroEnvio;
import com.proquifa.net.modelo.comun.StatusMessage;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.negocio.envio.ship.stub.Address;
//import com.proquifa.net.negocio.envio.ship.stub.AddressTk;
import com.proquifa.net.negocio.envio.ship.stub.AssociatedShipmentDetail;
import com.proquifa.net.negocio.envio.ship.stub.ClientDetail;
//import com.proquifa.net.negocio.envio.ship.stub.ClientDetailTk;
import com.proquifa.net.negocio.envio.ship.stub.CodReturnPackageDetail;
import com.proquifa.net.negocio.envio.ship.stub.Commodity;
import com.proquifa.net.negocio.envio.ship.stub.CompletedPackageDetail;
import com.proquifa.net.negocio.envio.ship.stub.CompletedShipmentDetail;
import com.proquifa.net.negocio.envio.ship.stub.Contact;
import com.proquifa.net.negocio.envio.ship.stub.CustomerReference;
import com.proquifa.net.negocio.envio.ship.stub.CustomerReferenceType;
import com.proquifa.net.negocio.envio.ship.stub.CustomsClearanceDetail;
import com.proquifa.net.negocio.envio.ship.stub.Dimensions;
import com.proquifa.net.negocio.envio.ship.stub.DropoffType;
import com.proquifa.net.negocio.envio.ship.stub.EdtRequestType;
import com.proquifa.net.negocio.envio.ship.stub.FreightBaseCharge;
import com.proquifa.net.negocio.envio.ship.stub.FreightRateDetail;
import com.proquifa.net.negocio.envio.ship.stub.FreightRateNotation;
import com.proquifa.net.negocio.envio.ship.stub.InternationalDocumentContentType;
import com.proquifa.net.negocio.envio.ship.stub.LabelFormatType;
import com.proquifa.net.negocio.envio.ship.stub.LabelSpecification;
import com.proquifa.net.negocio.envio.ship.stub.LinearUnits;
import com.proquifa.net.negocio.envio.ship.stub.Money;
import com.proquifa.net.negocio.envio.ship.stub.Notification;
import com.proquifa.net.negocio.envio.ship.stub.NotificationSeverityType;
//import com.proquifa.net.negocio.envio.ship.stub.NotificationSeverityTypeTk;
import com.proquifa.net.negocio.envio.ship.stub.PackageOperationalDetail;
import com.proquifa.net.negocio.envio.ship.stub.PackageRateDetail;
import com.proquifa.net.negocio.envio.ship.stub.PackageRating;
import com.proquifa.net.negocio.envio.ship.stub.PackagingType;
import com.proquifa.net.negocio.envio.ship.stub.Party;
import com.proquifa.net.negocio.envio.ship.stub.Payment;
import com.proquifa.net.negocio.envio.ship.stub.PaymentType;
import com.proquifa.net.negocio.envio.ship.stub.Payor;
import com.proquifa.net.negocio.envio.ship.stub.ProcessShipmentReply;
import com.proquifa.net.negocio.envio.ship.stub.ProcessShipmentRequest;
import com.proquifa.net.negocio.envio.ship.stub.RequestedPackageLineItem;
import com.proquifa.net.negocio.envio.ship.stub.RequestedShipment;
import com.proquifa.net.negocio.envio.ship.stub.ServiceType;
import com.proquifa.net.negocio.envio.ship.stub.ShipPortType;
import com.proquifa.net.negocio.envio.ship.stub.ShipServiceLocator;
import com.proquifa.net.negocio.envio.ship.stub.ShipmentOperationalDetail;
import com.proquifa.net.negocio.envio.ship.stub.ShipmentRateDetail;
import com.proquifa.net.negocio.envio.ship.stub.ShipmentRating;
import com.proquifa.net.negocio.envio.ship.stub.ShippingDocument;
import com.proquifa.net.negocio.envio.ship.stub.ShippingDocumentImageType;
import com.proquifa.net.negocio.envio.ship.stub.ShippingDocumentPart;
import com.proquifa.net.negocio.envio.ship.stub.Surcharge;
//import com.proquifa.net.negocio.envio.ship.stub.TrackServiceLocator;
import com.proquifa.net.negocio.envio.ship.stub.TrackingId;
import com.proquifa.net.negocio.envio.ship.stub.TransactionDetail;
import com.proquifa.net.negocio.envio.ship.stub.VersionId;
import com.proquifa.net.negocio.envio.ship.stub.WebAuthenticationCredential;
//import com.proquifa.net.negocio.envio.ship.stub.WebAuthenticationCredentialTk;
import com.proquifa.net.negocio.envio.ship.stub.WebAuthenticationDetail;
//import com.proquifa.net.negocio.envio.ship.stub.WebAuthenticationDetailTk;
import com.proquifa.net.negocio.envio.ship.stub.Weight;
import com.proquifa.net.negocio.envio.ship.stub.WeightUnits;

/**
 * Sample code to call the FedEx Ship Service
 * <p>
 * com.fedex.ship.stub is generated via WSDL2Java, like this:<br>
 * 
 * <pre>
 * java org.apache.axis.wsdl.WSDL2Java -w -p com.fedex.ship.stub http://www.fedex.com/...../ShipService?wsdl
 * </pre>
 * 
 * This sample code has been tested with JDK 7 and Apache Axis 1.4
 */
//
// Sample code to call the FedEx Ship Service - International Express Shipment
// from United States to Canada
//
@RestController
@CrossOrigin
public class ShipWebServiceClient {
	
	final static Logger log = LoggerFactory.getLogger(ShipWebServiceClient.class);
	//
	@Autowired
	ShipWebServiceClient shipWebServiceClient;
	/* Metodo recuparar la guia */
	static String fedexTrackingNumber = "";
	static File nombreArchivoGenerado;
	static String envioTrackingNumber = "";
	static boolean reintentarEnvio = false;
	static String valorTipoEnvio = null;
	// static int contadorIntentos;
	static String getError;
	static ParametroEnvio ParametrosCrearEnvio = null;
	static Integer internacional = 0;
	private static Party globalRecipientParty = new Party();

	@PostMapping("/crearEnvio")
	public ResponseEntity<StatusMessage> crearEnvio(@RequestBody ParametroEnvio parametro) {
		// contadorIntentos++;
		// log.info("contador"+contadorIntentos);
		log.info("Peso" + parametro.getPeso());
		log.info("idPendiente01" + parametro.getIdPendiente());
		ParametrosCrearEnvio = parametro;
		log.info("parametros");
		log.info("",parametro);
		log.info("",ParametrosCrearEnvio);
		log.info(parametro.getAddress().getStateOrProvinceCode());
		RequestedShipment requestedShipment = new RequestedShipment();
		if(parametro.getAddress().getStateOrProvinceCode().equalsIgnoreCase("JALISCO") || parametro.getAddress().getStateOrProvinceCode().equalsIgnoreCase("JA")){
			requestedShipment.setServiceType(ServiceType.PRIORITY_OVERNIGHT);
		}else {
			if(parametro.getAddress().getCountryCode().equalsIgnoreCase("MX")) {
				requestedShipment.setServiceType(ServiceType.STANDARD_OVERNIGHT);
			} else {
				requestedShipment.setServiceType(ServiceType.INTERNATIONAL_PRIORITY);
			}
		}
		
		/*NonNegativeInteger packageCount = new NonNegativeInteger("1");
		requestedShipment.setPackageCount(packageCount);*/
		
		ProcessShipmentRequest request = buildRequest(reintentarEnvio, parametro.getPeso(), parametro.getLength(),
				parametro.getHeight(), parametro.getWidth(), parametro.getCustomerReferenceClient(),
				parametro.getInvoceNumber(), parametro.getPoNumber(), parametro.getEmisor(), parametro.getAddress().getStateOrProvinceCode(), parametro.getAddress().getCountryCode());
		StatusMessage mensaje = new StatusMessage();
		Party lobalRecipientParty = new Party();
		try {
			mensaje.setStatus(HttpStatus.OK.value());
			mensaje.setMessage("ok");
			// Initialize the service
			Contact recipientContact = new Contact();
			recipientContact.setPhoneNumber(parametro.getContact().getPhoneNumber().equals("")?"0000000000":parametro.getContact().getPhoneNumber());
			recipientContact.setCompanyName(parametro.getContact().getCompanyName());
			// recipientContact.setPhoneNumber(parametro.getContact().getPhoneNumber());
			recipientContact.setPersonName(parametro.getContact().getPersonName());

			Address recipientAddress = new Address();
			String [] direccion = new String [1];
			if(parametro.getAddress().getStreetLines()[0].length() < 35) {
				direccion[0] = parametro.getAddress().getStreetLines()[0];
			}else {
				direccion = new String [2];
				direccion[0] = parametro.getAddress().getStreetLines()[0].substring(0, 35);
				direccion[1] = parametro.getAddress().getStreetLines()[0].substring(35,  parametro.getAddress().getStreetLines()[0].length()-1);
			}
			recipientAddress.setStreetLines(direccion);
			recipientAddress.setCity(parametro.getAddress().getCity());
			recipientAddress.setStateOrProvinceCode(parametro.getAddress().getStateOrProvinceCode());
			recipientAddress.setPostalCode(parametro.getAddress().getPostalCode());
			recipientAddress.setCountryCode(parametro.getAddress().getCountryCode());
			recipientAddress.setResidential(Boolean.valueOf(false));
			globalRecipientParty.setContact(recipientContact);
			globalRecipientParty.setAddress(recipientAddress);
			// Inicializar el servicio
			ShipServiceLocator service;
			ShipPortType port;
			service = new ShipServiceLocator();
			updateEndPoint(service);
			port = service.getShipServicePort();
			ProcessShipmentReply reply = port.processShipment(request); // This is the call to the ship web service passing in a request object and returning a reply object
			//
			// Esta es la llamada al servicio web de envío que pasa en un objeto de solicitud y devuelve un objeto de respuesta
			if (isResponseOk(reply.getHighestSeverity())){ // check if the call was successful
				// verifica si la llamada fue exitosa
				writeServiceOutput(reply, parametro.getPendientes());
				// log.info("Exitoso");
				reintentarEnvio = false;
				
				/*getShipments();
				log.info("/********** Eliminar envio");
				log.info(deleteShipment(envioTrackingNumber).toString());
				getShipments();*/
			} else {
				parametro.setReintentos(parametro.getReintentos() + +1);
				printNotifications(reply.getNotifications());
				// log.info("Errores");
				if(parametro.getAddress().getStateOrProvinceCode().equalsIgnoreCase("JALISCO") || parametro.getAddress().getStateOrProvinceCode().equalsIgnoreCase("JA")){
					requestedShipment.setServiceType(ServiceType.PRIORITY_OVERNIGHT);
				}else {
					if(parametro.getAddress().getCountryCode().equals("MX")) {
						requestedShipment.setServiceType(ServiceType.STANDARD_OVERNIGHT);
					} else {
						requestedShipment.setServiceType(ServiceType.INTERNATIONAL_PRIORITY);
					}
				}
				reintentarEnvio = true;
				if(parametro.getReintentos() < 5)
					return shipWebServiceClient.crearEnvio(parametro);
				else {
					mensaje.setCurrent(false);
					return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
				}
				// log.info("Final de errores");

				// reintentarEnvio=false;
				// contadorIntentos=0;
				// }
			}
			Map<String, Object> mapReturn = new HashMap<String, Object>();
			mapReturn.put("File", nombreArchivoGenerado.getName());
			mapReturn.put("TrackingNumber", envioTrackingNumber);
			mensaje.setCurrent(mapReturn);
			return new ResponseEntity<StatusMessage>(mensaje, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static ProcessShipmentRequest buildRequest(boolean reintentar, Double peso, Integer length, Integer height,
			Integer width, String customerReferenceClient, String invoceNumber, String poNumber, String emisor, String codigoEstado, String codigoPais) {

		log.info("valor del reintentar envio ", reintentar);
		ProcessShipmentRequest request = new ProcessShipmentRequest(); // Build a request object
		request.setClientDetail(createClientDetail());
		request.setWebAuthenticationDetail(createWebAuthenticationDetail());
		//
		TransactionDetail transactionDetail = new TransactionDetail();
		transactionDetail.setCustomerTransactionId("java sample - International Express Shipment"); // The client will get the same value back in the response
		request.setTransactionDetail(transactionDetail);
		//
		VersionId versionId = new VersionId("ship", 23, 0, 0);
		request.setVersion(versionId);
		//
		RequestedShipment requestedShipment = new RequestedShipment();
		requestedShipment.setShipTimestamp(Calendar.getInstance()); // Ship date and time
		requestedShipment.setDropoffType(DropoffType.REGULAR_PICKUP);
		if (reintentar == false) {
			reintentarEnvio = true;

			if(codigoEstado.equalsIgnoreCase("JALISCO") || codigoEstado.equalsIgnoreCase("JA")){
				log.info("Notifications: PRIORITY_OVERNIGHT");
				requestedShipment.setServiceType(ServiceType.PRIORITY_OVERNIGHT);
			}else {
				if(codigoPais.equalsIgnoreCase("MX")) {
					log.info("Notifications: STANDARD_OVERNIGHT");
					requestedShipment.setServiceType(ServiceType.STANDARD_OVERNIGHT);
				} else {
					log.info("Notifications: INTERNATIONAL_PRIORITY");
					requestedShipment.setServiceType(ServiceType.INTERNATIONAL_PRIORITY);
				}
			}
		} else if (reintentar == true) {
			if(codigoEstado.equals("JALISCO") || codigoEstado.equalsIgnoreCase("JA")){
				log.info("Notifications: PRIORITY_OVERNIGHT");
				requestedShipment.setServiceType(ServiceType.PRIORITY_OVERNIGHT);
			}else {
				if(codigoPais.equalsIgnoreCase("MX")) {
					log.info("Notifications: STANDARD_OVERNIGHT");
					requestedShipment.setServiceType(ServiceType.STANDARD_OVERNIGHT);
				} else {
					log.info("Notifications: INTERNATIONAL_PRIORITY");
					requestedShipment.setServiceType(ServiceType.INTERNATIONAL_PRIORITY);
				}
			}
			reintentarEnvio = false;
		}
		// requestedShipment.setServiceType(ServiceType.STANDARD_OVERNIGHT);
		// Service types are STANDARD_OVERNIGHT,// PRIORITY_OVERNIGHT,
		// FEDEX_GROUND ...
		requestedShipment.setPackagingType(PackagingType.YOUR_PACKAGING); // Packaging type FEDEX_BOX, FEDEX_PAK, FEDEX_TUBE, YOUR_PACKAGING, ...
		requestedShipment.setShipper(addShipper(emisor)); // Sender information
		requestedShipment.setRecipient(addRecipient());
		log.info("......addRecipiente.....");
		log.info("",globalRecipientParty);
		requestedShipment.setShippingChargesPayment(addShippingChargesPayment());
		requestedShipment.setCustomsClearanceDetail(addCustomsClearanceDetail());
		requestedShipment.setLabelSpecification(addLabelSpecification());
		requestedShipment.setEdtRequestType(EdtRequestType.ALL);
		requestedShipment.setPackageCount(new NonNegativeInteger("1"));
		RequestedPackageLineItem[] rp = new RequestedPackageLineItem[] { addRequestedPackageLineItem(peso,length,height,width,customerReferenceClient,invoceNumber,poNumber) };
		requestedShipment.setRequestedPackageLineItems(rp);
		request.setRequestedShipment(requestedShipment);
		return request;
	}

	//
	private static void writeServiceOutput(ProcessShipmentReply reply, List<Integer> pendientes) throws Exception {

		try {
			String idPendiente = "";
			if (pendientes != null && pendientes.size() > 0) {
				idPendiente = pendientes.get(0).toString();
			}
			log.info(reply.getTransactionDetail().getCustomerTransactionId());
			CompletedShipmentDetail csd = reply.getCompletedShipmentDetail();
			String masterTrackingNumber = printMasterTrackingNumber(csd);
			printShipmentOperationalDetails(csd.getOperationalDetail());
			printShipmentRating(csd.getShipmentRating());
			CompletedPackageDetail cpd[] = csd.getCompletedPackageDetails();
			if (idPendiente == null || idPendiente.equalsIgnoreCase("")) {
				printPackageDetails(cpd, masterTrackingNumber);
				saveShipmentDocumentsToFile(csd.getShipmentDocuments(), masterTrackingNumber);
			} else {
				for (Integer pend : pendientes) {
					idPendiente = "Guia-" + pend.toString();
					printPackageDetails(cpd, idPendiente);
					saveShipmentDocumentsToFile(csd.getShipmentDocuments(), idPendiente);
				}
			}
			// saveShipmentDocumentsToFile(csd.getShipmentDocuments(), reply.);
			// If Express COD shipment is requested, the COD return label is returned as an Associated Shipment.
			getAssociatedShipmentLabels(csd.getAssociatedShipments());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

	private static boolean isResponseOk(NotificationSeverityType notificationSeverityType) {
		if (notificationSeverityType == null) {
			return false;
		}
		if (notificationSeverityType.equals(NotificationSeverityType.WARNING)
				|| notificationSeverityType.equals(NotificationSeverityType.NOTE)
				|| notificationSeverityType.equals(NotificationSeverityType.SUCCESS)) {
			return true;
		}

		return false;

	}
	
	/*private static boolean isResponseOk(NotificationSeverityTypeTk notificationSeverityType) {
		if (notificationSeverityType == null) {
			return false;
		}
		if (notificationSeverityType.equals(NotificationSeverityTypeTk.WARNING)
				|| notificationSeverityType.equals(NotificationSeverityTypeTk.NOTE)
				|| notificationSeverityType.equals(NotificationSeverityTypeTk.SUCCESS)) {
			return true;
		}

		return false;

	}*/

	private static void printNotifications(Notification[] notifications) {
		log.info("Notifications:");

		log.info("valor de reintar envio " + reintentarEnvio);
		if (notifications == null || notifications.length == 0) {
			log.info("  No notifications returned");
		}
		for (int i = 0; i < notifications.length; i++) {
			Notification n = notifications[i];
			System.out.print("  Notification no. " + i + ": ");
			if (n == null) {
				log.info("null");
				continue;
			} else {
				log.info("");
			}
			NotificationSeverityType nst = n.getSeverity();
			log.info("    Severity: " + (nst == null ? "null" : nst.getValue()));
			log.info("    Code: " + n.getCode());
			getError = n.getCode();
			log.info("Obtener error: " + getError);
			log.info("    Message: " + n.getMessage());

			log.info("    Source: " + n.getSource());

		}

	}

	private static void printMoney(Money money, String description, String space) {
		if (money != null) {
			log.info(space + description + ": " + money.getAmount() + " " + money.getCurrency());
		}
	}

	private static void printWeight(Weight weight, String description, String space) {
		if (weight != null) {
			log.info(space + description + ": " + weight.getValue() + " " + weight.getUnits());
		}
	}

	private static void printString(String value, String description, String space) {
		if (value != null) {
			log.info(space + description + ": " + value);
		}
	}

	private static Money addMoney(String currency, Double value) {
		Money money = new Money();
		money.setCurrency(currency);
		money.setAmount(new BigDecimal(value));
		return money;
	}

	/////
	private static Weight addPackageWeight(Double packageWeight, WeightUnits weightUnits) {
		Weight weight = new Weight();
		weight.setUnits(weightUnits);
		weight.setValue(new BigDecimal(packageWeight));
		return weight;
	}

	private static Dimensions addPackageDimensions(Integer length, Integer height, Integer width,
			LinearUnits linearUnits) {
		Dimensions dimensions = new Dimensions();
		dimensions.setLength(new NonNegativeInteger(length.toString()));
		dimensions.setHeight(new NonNegativeInteger(height.toString()));
		dimensions.setWidth(new NonNegativeInteger(width.toString()));
		dimensions.setUnits(linearUnits);
		return dimensions;
	}

	// Shipment level reply information
	private static void printShipmentOperationalDetails(ShipmentOperationalDetail shipmentOperationalDetail) {
		if (shipmentOperationalDetail != null) {
			log.info("Routing Details");
			printString(shipmentOperationalDetail.getUrsaPrefixCode(), "URSA Prefix", "  ");
			if (shipmentOperationalDetail.getCommitDay() != null)
				printString(shipmentOperationalDetail.getCommitDay().getValue(), "Service Commitment", "  ");
			printString(shipmentOperationalDetail.getAirportId(), "Airport Id", "  ");
			if (shipmentOperationalDetail.getDeliveryDay() != null)
				printString(shipmentOperationalDetail.getDeliveryDay().getValue(), "Delivery Day", "  ");
		}
	}

	private static void printShipmentRating(ShipmentRating shipmentRating) {
		if (shipmentRating != null) {
			log.info("Shipment Rate Details");
			ShipmentRateDetail[] srd = shipmentRating.getShipmentRateDetails();
			for (int j = 0; j < srd.length; j++) {
				log.info("  Rate Type: " + srd[j].getRateType().getValue());
				printWeight(srd[j].getTotalBillingWeight(), "Shipment Billing Weight", "    ");
				printMoney(srd[j].getTotalBaseCharge(), "Shipment Base Charge", "    ");
				printMoney(srd[j].getTotalNetCharge(), "Shipment Net Charge", "    ");
				printMoney(srd[j].getTotalSurcharges(), "Shipment Total Surcharge", "    ");
				if (null != srd[j].getSurcharges()) {
					log.info("    Surcharge Details");
					Surcharge[] s = srd[j].getSurcharges();
					for (int k = 0; k < s.length; k++) {
						printMoney(s[k].getAmount(), s[k].getSurchargeType().getValue(), "      ");
					}
				}
				printFreightDetail(srd[j].getFreightRateDetail());
			}
		}
	}

	// Package level reply information
	private static void printPackageOperationalDetails(PackageOperationalDetail packageOperationalDetail) {
		if (packageOperationalDetail != null) {
			log.info("  Routing Details");
			printString(packageOperationalDetail.getAstraHandlingText(), "Astra", "    ");
			printString(packageOperationalDetail.getGroundServiceCode(), "Ground Service Code", "    ");
		}
	}

	private static void printPackageDetails(CompletedPackageDetail[] cpd, String idPendiente) throws Exception {
		if (cpd != null) {
			log.info("Package Details");
			for (int i = 0; i < cpd.length; i++) { // Package details / Rating information for each package
				String trackingNumber = cpd[i].getTrackingIds()[0].getTrackingNumber();
				printTrackingNumbers(cpd[i]);
				//
				printPackageRating(cpd[i].getPackageRating());
				// Write label buffer to file
				ShippingDocument sd = cpd[i].getLabel();
				// saveLabelToFile(sd, trackingNumber);
				saveLabelToFile(sd, idPendiente);
				printPackageOperationalDetails(cpd[i].getOperationalDetail());
				// If Ground COD shipment is requested, the COD return label is returned as in CodReturnPackageDetail.
				printGroundCodLabel(cpd[i], trackingNumber);
			}
		}
	}

	private static void printPackageRating(PackageRating packageRating) {
		if (packageRating != null) {
			log.info("Package Rate Details");
			PackageRateDetail[] prd = packageRating.getPackageRateDetails();
			for (int j = 0; j < prd.length; j++) {
				log.info("  Rate Type: " + prd[j].getRateType().getValue());
				printWeight(prd[j].getBillingWeight(), "Billing Weight", "    ");
				printMoney(prd[j].getBaseCharge(), "Base Charge", "    ");
				printMoney(prd[j].getNetCharge(), "Net Charge", "    ");
				printMoney(prd[j].getTotalSurcharges(), "Total Surcharge", "    ");
				if (null != prd[j].getSurcharges()) {
					log.info("    Surcharge Details");
					Surcharge[] s = prd[j].getSurcharges();
					for (int k = 0; k < s.length; k++) {
						printMoney(s[k].getAmount(), s[k].getSurchargeType().getValue(), "      ");
					}
				}
			}
		}
	}

	private static void printTrackingNumbers(CompletedPackageDetail completedPackageDetail) {
		if (completedPackageDetail.getTrackingIds() != null) {
			TrackingId[] trackingId = completedPackageDetail.getTrackingIds();
			for (int i = 0; i < trackingId.length; i++) {
				String trackNumber = trackingId[i].getTrackingNumber();
				String trackType = trackingId[i].getTrackingIdType().getValue();
				String formId = trackingId[i].getFormId();
				printString(trackNumber, trackType + " tracking number", "  ");
				printString(formId, "Form Id", "  ");
			}
		}
	}

	private static String getPayorAccountNumber() {
		// See if payor account number is set as system property, if not default it to "XXX"
		String payorAccountNumber = System.getProperty("Payor.AccountNumber");
		if (payorAccountNumber == null) {
			payorAccountNumber = "708302058"; // Replace "XXX" with the payor account number produccion  			
			//payorAccountNumber = "604794501"; // Replace "XXX" with the payor account number pruebas 
		}
		return payorAccountNumber;
	}

	private static Party addShipper(String emisor) {
		Party shipperParty = new Party(); // Sender information
		Contact shipperContact = new Contact();
		shipperContact.setPersonName(emisor);
		shipperContact.setCompanyName("PROQUIFA");
		shipperContact.setPhoneNumber("015513151498");
		Address shipperAddress = new Address();
		shipperAddress.setStreetLines(new String[] { "Jose Maria Morelos 164, Nino Jesus" });
		shipperAddress.setCity("Mexico City");
		shipperAddress.setStateOrProvinceCode("DF");
		shipperAddress.setPostalCode("14080");
		shipperAddress.setCountryCode("MX");
		shipperParty.setContact(shipperContact);
		shipperParty.setAddress(shipperAddress);
		return shipperParty;
	}

	private static Party addRecipient() {
		log.info("",globalRecipientParty);
		return globalRecipientParty;
	}

	private static Payment addShippingChargesPayment() {
		Payment payment = new Payment(); // Payment information
		payment.setPaymentType(PaymentType.SENDER);
		Payor payor = new Payor();
		Party responsibleParty = new Party();
		responsibleParty.setAccountNumber(getPayorAccountNumber());
		Address responsiblePartyAddress = new Address();
		responsiblePartyAddress.setCountryCode("MX");
		responsibleParty.setAddress(responsiblePartyAddress);
		responsibleParty.setContact(new Contact());
		payor.setResponsibleParty(responsibleParty);
		payment.setPayor(payor);
		return payment;
	}

	private static Payment addDutiesPayment() {
		Payment payment = new Payment(); // Payment information
		payment.setPaymentType(PaymentType.SENDER);
		Payor payor = new Payor();
		Party responsibleParty = new Party();
		responsibleParty.setAccountNumber(getPayorAccountNumber());
		Address responsiblePartyAddress = new Address();
		responsiblePartyAddress.setCountryCode("MX");
		responsibleParty.setAddress(responsiblePartyAddress);
		responsibleParty.setContact(new Contact());
		payor.setResponsibleParty(responsibleParty);
		payment.setPayor(payor);
		return payment;
	}

	private static RequestedPackageLineItem addRequestedPackageLineItem(Double pesoPaquete, Integer length,
			Integer height, Integer width, String customerReferenceClient, String invoceNumber, String poNumber) {
		RequestedPackageLineItem requestedPackageLineItem = new RequestedPackageLineItem();
		requestedPackageLineItem.setSequenceNumber(new PositiveInteger("1"));
		requestedPackageLineItem.setGroupPackageCount(new NonNegativeInteger("1"));

		/// Modificas el peso del paquete
		requestedPackageLineItem.setWeight(addPackageWeight(pesoPaquete, WeightUnits.KG));
		requestedPackageLineItem.setDimensions(addPackageDimensions(length, height, width, LinearUnits.CM));
		requestedPackageLineItem.setCustomerReferences(new CustomerReference[] {
				addCustomerReference(CustomerReferenceType.CUSTOMER_REFERENCE.getValue(), customerReferenceClient),
				addCustomerReference(CustomerReferenceType.INVOICE_NUMBER.getValue(), invoceNumber),
				addCustomerReference(CustomerReferenceType.P_O_NUMBER.getValue(), poNumber) });

		log.info("pesos........");     

		log.info("",requestedPackageLineItem.getWeight());

		return requestedPackageLineItem;

	}

	private static CustomerReference addCustomerReference(String customerReferenceType, String customerReferenceValue) {
		CustomerReference customerReference = new CustomerReference();
		customerReference.setCustomerReferenceType(CustomerReferenceType.fromString(customerReferenceType));
		customerReference.setValue(customerReferenceValue);
		return customerReference;
	}

	private static LabelSpecification addLabelSpecification() {
		LabelSpecification labelSpecification = new LabelSpecification(); // Label specification
		labelSpecification.setImageType(ShippingDocumentImageType.PDF);// Image types PDF, PNG, DPL, ...
		labelSpecification.setLabelFormatType(LabelFormatType.COMMON2D); // LABEL_DATA_ONLY, COMMON2D
		// labelSpecification.setLabelStockType(LabelStockType.value2); //
		// STOCK_4X6.75_LEADING_DOC_TAB
		// labelSpecification.setLabelPrintingOrientation(LabelPrintingOrientationType.TOP_EDGE_OF_TEXT_FIRST);
		return labelSpecification;
	}

	private static CustomsClearanceDetail addCustomsClearanceDetail() {
		CustomsClearanceDetail customs = new CustomsClearanceDetail(); // International details
		customs.setDutiesPayment(addDutiesPayment());
		customs.setCustomsValue(addMoney("USD", 100.00));
		customs.setDocumentContent(InternationalDocumentContentType.NON_DOCUMENTS);
		// Set export detail - To be used for Shipments that fall under AES
		// Compliance
		// ExportDetail exportDetail = new ExportDetail();
		// exportDetail.setExportComplianceStatement("AESX20091127123456");
		// intd.setExportDetail(exportDetail);
		customs.setCommodities(new Commodity[] { addCommodity() });// Commodity details
		return customs;
	}

	private static Commodity addCommodity() {
		Commodity commodity = new Commodity();
		commodity.setNumberOfPieces(new NonNegativeInteger("1"));
		commodity.setDescription("Books");
		commodity.setCountryOfManufacture("MX");
		commodity.setWeight(new Weight());
		commodity.getWeight().setValue(new BigDecimal(1.0));
		commodity.getWeight().setUnits(WeightUnits.KG);
		commodity.setQuantity(new BigDecimal(1.0));
		commodity.setQuantityUnits("EA");
		commodity.setUnitPrice(new Money());
		commodity.getUnitPrice().setAmount(new java.math.BigDecimal(400.000000));
		commodity.getUnitPrice().setCurrency("USD");
		commodity.setCustomsValue(new Money());
		commodity.getCustomsValue().setAmount(new java.math.BigDecimal(100.000000));
		commodity.getCustomsValue().setCurrency("USD");
		commodity.setCountryOfManufacture("US");
		commodity.setHarmonizedCode("490199009100");
		return commodity;
	}

	private static void printFreightDetail(FreightRateDetail freightRateDetail) {
		if (freightRateDetail != null) {
			log.info("  Freight Details");
			printFreightNotations(freightRateDetail);
			printFreightBaseCharges(freightRateDetail);

		}
	}

	private static void printFreightNotations(FreightRateDetail frd) {
		if (null != frd.getNotations()) {
			log.info("    Notations");
			FreightRateNotation notations[] = frd.getNotations();
			for (int n = 0; n < notations.length; n++) {
				printString(notations[n].getCode(), "Code", "      ");
				printString(notations[n].getDescription(), "Notification", "      ");
			}
		}
	}

	private static void printFreightBaseCharges(FreightRateDetail frd) {
		if (null != frd.getBaseCharges()) {
			FreightBaseCharge baseCharges[] = frd.getBaseCharges();
			for (int i = 0; i < baseCharges.length; i++) {
				log.info("    Freight Rate Details");
				printString(baseCharges[i].getDescription(), "Description", "      ");
				printString(baseCharges[i].getFreightClass().getValue(), "Freight Class", "      ");
				printString(baseCharges[i].getRatedAsClass().getValue(), "Rated Class", "      ");
				printWeight(baseCharges[i].getWeight(), "Weight", "      ");
				printString(baseCharges[i].getChargeBasis().getValue(), "Charge Basis", "      ");
				printMoney(baseCharges[i].getChargeRate(), "Charge Rate", "      ");
				printMoney(baseCharges[i].getExtendedAmount(), "Extended Amount", "      ");
				printString(baseCharges[i].getNmfcCode(), "NMFC Code", "      ");
			}
		}
	}

	private static String printMasterTrackingNumber(CompletedShipmentDetail csd) {
		String trackingNumber = "";
		if (null != csd.getMasterTrackingId()) {
			trackingNumber = csd.getMasterTrackingId().getTrackingNumber();
			log.info("Master Tracking Number");
			log.info("  Type: " + csd.getMasterTrackingId().getTrackingIdType());
			log.info("  Tracking Number: " + trackingNumber);
			envioTrackingNumber = trackingNumber;
		}
		return trackingNumber;
	}

	// Saving and displaying shipping documents (labels)
	private static void saveLabelToFile(ShippingDocument shippingDocument, String trackingNumber) throws Exception {
		ShippingDocumentPart[] sdparts = shippingDocument.getParts();
		Funcion funcion = new Funcion();
		String ruta = funcion.obtenerRutaCompletaDocumento("", "Fedex", "");
		String ruta2 = funcion.obtenerRutaCompletaDocumento("", "guias", "");
		for (int a = 0; a < sdparts.length; a++) {
			ShippingDocumentPart sdpart = sdparts[a];
			String labelLocation = System.getProperty("file.label.location ");
			log.info("saveLabelToFile " + labelLocation);
			if (labelLocation == null) {
				// labelLocation = "c:\\";
				labelLocation = ruta;
				File fileAux = new File(labelLocation);
				if (!fileAux.exists())
					fileAux.mkdirs();
				
				File fileAux2 = new File(ruta2);
				if (!fileAux2.exists())
					fileAux2.mkdirs();
			}
			//			String shippingDocumentType = shippingDocument.getType().getValue();
			String shippingDocumentType = "Guia-";
			//			String labelFileName = new String(
			//					labelLocation + shippingDocumentType + "." + trackingNumber + "_" + a + ".pdf");
			String labelFileName = new String(
					labelLocation  + trackingNumber +  ".pdf");
			
			String labelFileName2 = new String(
					ruta2  + trackingNumber +  ".pdf");

			File labelFile = new File(labelFileName);
			File labelFile2 = new File(labelFileName2);
			log.info("********************");
			log.info("",labelFile.getAbsoluteFile());

			if (labelFile.getAbsoluteFile() == null) {
				log.info("********************");
				log.info("*****No realizo el envio****");

			}
			FileOutputStream fos = new FileOutputStream(labelFile);
			fos.write(sdpart.getImage());
			fos.close();
			
			FileOutputStream fos2 = new FileOutputStream(labelFile2);
			fos2.write(sdpart.getImage());
			fos2.close();
			log.info("\nlabel file name " + labelFile.getAbsolutePath());
			// Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "
			// +
			// labelFile.getAbsolutePath());
			// log.info(Runtime.getRuntime().exec("rundll32
			// url.dll,FileProtocolHandler " + labelFile.getPath()));

			nombreArchivoGenerado = labelFile;
			log.info("valor de labelFile");
			log.info("",labelFile);
		}
	}

	private static void printGroundCodLabel(CompletedPackageDetail completedPackageDetail, String trackingNumber)
			throws Exception {
		CodReturnPackageDetail codReturnPackageDetail = completedPackageDetail.getCodReturnDetail();
		if (codReturnPackageDetail != null && codReturnPackageDetail.getLabel() != null) {
			codReturnPackageDetail.getLabel();
			String labelLocation = System.getProperty("file.label.location");
			String labelName = codReturnPackageDetail.getLabel().getType().getValue();
			ShippingDocumentPart[] parts = codReturnPackageDetail.getLabel().getParts();
			for (int i = 0; i < parts.length; i++) {
				String codLabelFileName = new String(
						labelLocation + labelName + "." + trackingNumber + "_" + i + ".pdf");
				File codLabelFile = new File(codLabelFileName);
				FileOutputStream fos = new FileOutputStream(codLabelFile);
				fos.write(parts[i].getImage());
				fos.close();
				log.info("\nCod return label file name " + codLabelFile.getAbsolutePath());
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + codLabelFile.getAbsolutePath());
			}
		}
	}

	private static void saveShipmentDocumentsToFile(ShippingDocument[] shippingDocument, String trackingNumber)
			throws Exception {
		if (shippingDocument != null) {
			for (int i = 0; i < shippingDocument.length; i++) {
				ShippingDocumentPart[] sdparts = shippingDocument[i].getParts();
				for (int a = 0; a < sdparts.length; a++) {
					ShippingDocumentPart sdpart = sdparts[a];
					String labelLocation = System.getProperty("file.label.location");
					if (labelLocation == null) {
						labelLocation = "c:\\";
					}
					String labelName = shippingDocument[i].getType().getValue();
					//					String shippingDocumentLabelFileName = new String(
					//							labelLocation + labelName + "." + trackingNumber + "_" + a + ".pdf");
					String shippingDocumentLabelFileName = new String(
							labelLocation +  trackingNumber + ".pdf");

					log.info(
							"Nombre del archivo  shippingDocumentLabelFileName" + shippingDocumentLabelFileName);

					log.info("Nombre del archivo" + nombreArchivoGenerado);
					File shippingDocumentLabelFile = new File(shippingDocumentLabelFileName);
					FileOutputStream fos = new FileOutputStream(shippingDocumentLabelFile);
					fos.write(sdpart.getImage());
					fos.close();
					log.info(
							"\nAssociated shipment label file name " + shippingDocumentLabelFile.getAbsolutePath());
					Runtime.getRuntime().exec(
							"rundll32 url.dll,FileProtocolHandler " + shippingDocumentLabelFile.getAbsolutePath());
				}
			}
		}
	}

	private static void getAssociatedShipmentLabels(AssociatedShipmentDetail[] associatedShipmentDetail)
			throws Exception {
		if (associatedShipmentDetail != null) {
			for (int j = 0; j < associatedShipmentDetail.length; j++) {
				if (associatedShipmentDetail[j].getLabel() != null && associatedShipmentDetail[j].getType() != null) {
					String trackingNumber = associatedShipmentDetail[j].getTrackingId().getTrackingNumber();
					String associatedShipmentType = associatedShipmentDetail[j].getType().getValue();
					ShippingDocument associatedShipmentLabel = associatedShipmentDetail[j].getLabel();
					saveAssociatedShipmentLabelToFile(associatedShipmentLabel, trackingNumber, associatedShipmentType);
				}
			}
		}
	}

	private static void saveAssociatedShipmentLabelToFile(ShippingDocument shippingDocument, String trackingNumber,
			String labelName) throws Exception {
		ShippingDocumentPart[] sdparts = shippingDocument.getParts();
		for (int a = 0; a < sdparts.length; a++) {
			ShippingDocumentPart sdpart = sdparts[a];
			String labelLocation = System.getProperty("file.label.location");
			if (labelLocation == null) {
				labelLocation = "c:\\";
			}
			String associatedShipmentLabelFileName = new String(
					labelLocation + labelName + "." + trackingNumber + "_" + a + ".pdf");
			File associatedShipmentLabelFile = new File(associatedShipmentLabelFileName);
			FileOutputStream fos = new FileOutputStream(associatedShipmentLabelFile);
			fos.write(sdpart.getImage());
			fos.close();
			System.out
			.println("\nAssociated shipment label file name " + associatedShipmentLabelFile.getAbsolutePath());
			Runtime.getRuntime()
			.exec("rundll32 url.dll,FileProtocolHandler " + associatedShipmentLabelFile.getAbsolutePath());
		}
	}

	private static ClientDetail createClientDetail() {
		ClientDetail clientDetail = new ClientDetail();
		String accountNumber = System.getProperty("accountNumber");
		String meterNumber = System.getProperty("meterNumber");

		//
		// See if the accountNumber and meterNumber properties are set,
		// if set use those values, otherwise default them to "XXX"
		//
		if (accountNumber == null) {
			accountNumber = "708302058"; // Replace "XXX" with clients account
			// number produccion
			//accountNumber = "604794501"; // Replace "XXX" with clients account
			// number pruebas
		}
		if (meterNumber == null) {
			meterNumber = "251927737"; // Replace "XXX" with clients meter
			// number produccion
			//meterNumber = "118716786"; // Replace "XXX" with clients meter
			// number pruebas
		}
		clientDetail.setAccountNumber(accountNumber);
		clientDetail.setMeterNumber(meterNumber);
		return clientDetail;
	}
	
	/*private static ClientDetailTk createClientDetailTk() {
		ClientDetailTk clientDetail = new ClientDetailTk();
		String accountNumber = System.getProperty("accountNumber");
		String meterNumber = System.getProperty("meterNumber");

		//
		// See if the accountNumber and meterNumber properties are set,
		// if set use those values, otherwise default them to "XXX"
		//
		if (accountNumber == null) {
			//accountNumber = "867084096"; // Replace "XXX" with clients account
			// number produccion
			accountNumber = "604794501"; // Replace "XXX" with clients account
			// number pruebas
		}
		if (meterNumber == null) {
			//meterNumber = "113528004"; // Replace "XXX" with clients meter
			// number produccion
			meterNumber = "118716786"; // Replace "XXX" with clients meter
			// number pruebas
		}
		clientDetail.setAccountNumber(accountNumber);
		clientDetail.setMeterNumber(meterNumber);
		return clientDetail;
	}*/

	///// Metodo para obtener el numero de tracking
	public static String enviarTrackingNumber() {
		String trackingNumberEnviar = "";
		trackingNumberEnviar = envioTrackingNumber;
		log.info("valor de : trackingNumberEnviar " + trackingNumberEnviar);
		return trackingNumberEnviar;
	}

	//// Metodo para recuperar la guia

	public static File enviarFileGuideShip() {

		return nombreArchivoGenerado;

	}

	private static WebAuthenticationDetail createWebAuthenticationDetail() {
		WebAuthenticationCredential userCredential = new WebAuthenticationCredential();
		String key = System.getProperty("key");
		String password = System.getProperty("password");
		//
		// See if the key and password properties are set,
		// if set use those values, otherwise default them to "XXX"
		//
		if (key == null) {

			key = "8fBZvuHAolwq0Znb"; // Replace "XXX" with clients key produccion 
			//key = "kFLlzTDLtHsDLgVs"; // Replace "XXX" with clients key pruebas
		}
		if (password == null) {

			password = "0uU58K3oLgNw0KzvvHdyQFLyk"; // Replace "XXX" with
			// clients password produccion 
			//password = "fYJV0s6owolKDoocwFQ7TTxC9"; // Replace "XXX" with
			// clients password pruebas 
		}
		userCredential.setKey(key);
		userCredential.setPassword(password);

		WebAuthenticationCredential parentCredential = null;
		Boolean useParentCredential = false; // Set this value to true is using
		// a parent credential
		if (useParentCredential) {

			String parentKey = System.getProperty("parentkey");
			String parentPassword = System.getProperty("parentpassword");
			//
			// See if the parentkey and parentpassword properties are set,
			// if set use those values, otherwise default them to "XXX"
			//
			if (parentKey == null) {
				parentKey = "8fBZvuHAolwq0Znb"; // Replace "XXX" with clients
				// parent key produccion  

				//parentKey = "kFLlzTDLtHsDLgVs"; // Replace "XXX" with clients
				// parent key pruebas 
			}
			if (parentPassword == null) {

				parentPassword = "0uU58K3oLgNw0KzvvHdyQFLyk"; // Replace "XXX"
				// with clients
				// parent
				// password produccion 

				//parentPassword = "fYJV0s6owolKDoocwFQ7TTxC9"; // Replace "XXX"
				// with clients
				// parent
				// password pruebas
			}
			parentCredential = new WebAuthenticationCredential();
			parentCredential.setKey(parentKey);
			parentCredential.setPassword(parentPassword);
		}
		return new WebAuthenticationDetail(parentCredential, userCredential);
	}
	
	/*private static WebAuthenticationDetailTk createWebAuthenticationDetailTk() {
		WebAuthenticationCredentialTk userCredential = new WebAuthenticationCredentialTk();
		String key = System.getProperty("key");
		String password = System.getProperty("password");
		//
		// See if the key and password properties are set,
		// if set use those values, otherwise default them to "XXX"
		//
		if (key == null) {

			//key = "Wqf8XIN6ZVfcEaQw"; // Replace "XXX" with clients key produccion 
			key = "kFLlzTDLtHsDLgVs"; // Replace "XXX" with clients key pruebas
		}
		if (password == null) {

			//password = "g1pdpEnRXu5EmObXhlWlkhdML"; // Replace "XXX" with
			// clients password produccion 
			password = "fYJV0s6owolKDoocwFQ7TTxC9"; // Replace "XXX" with
			// clients password pruebas 
		}
		userCredential.setKey(key);
		userCredential.setPassword(password);

		WebAuthenticationCredentialTk parentCredential = null;
		Boolean useParentCredential = false; // Set this value to true is using
		// a parent credential
		if (useParentCredential) {

			String parentKey = System.getProperty("parentkey");
			String parentPassword = System.getProperty("parentpassword");
			//
			// See if the parentkey and parentpassword properties are set,
			// if set use those values, otherwise default them to "XXX"
			//
			if (parentKey == null) {
				//parentKey = "Wqf8XIN6ZVfcEaQw"; // Replace "XXX" with clients
				// parent key produccion  

				parentKey = "kFLlzTDLtHsDLgVs"; // Replace "XXX" with clients
				// parent key pruebas 
			}
			if (parentPassword == null) {

				//parentPassword = "g1pdpEnRXu5EmObXhlWlkhdML"; // Replace "XXX"
				// with clients
				// parent
				// password produccion 

				parentPassword = "fYJV0s6owolKDoocwFQ7TTxC9"; // Replace "XXX"
				// with clients
				// parent
				// password pruebas
			}
			parentCredential = new WebAuthenticationCredentialTk();
			parentCredential.setKey(parentKey);
			parentCredential.setPassword(parentPassword);
		}
		return new WebAuthenticationDetailTk(parentCredential, userCredential);
	}*/

	private static void updateEndPoint(ShipServiceLocator serviceLocator) {
		String endPoint = System.getProperty("endPoint");
		if (endPoint != null) {

			serviceLocator.setShipServicePortEndpointAddress(endPoint);
		}
	}

	/*public static Boolean deleteShipment(String numTracking) {
		try {
			DeleteShipmentRequest deleteShipmentRequest =  new DeleteShipmentRequest();
			deleteShipmentRequest.setWebAuthenticationDetail(createWebAuthenticationDetail());
			deleteShipmentRequest.setClientDetail(createClientDetail());
			TransactionDetail transactionDetail = new TransactionDetail();
			transactionDetail.setCustomerTransactionId("java sample - International Express Shipment");
			deleteShipmentRequest.setTransactionDetail(transactionDetail);
			VersionId versionId = new VersionId("ship", 23, 0, 0);
			deleteShipmentRequest.setVersion(versionId);
			TrackingId trackingId = new TrackingId();
			trackingId.setTrackingIdType(TrackingIdType.EXPRESS);
			trackingId.setTrackingNumber(numTracking);
			deleteShipmentRequest.setTrackingId(trackingId);;
			deleteShipmentRequest.setDeletionControl(DeletionControlType.DELETE_ALL_PACKAGES);

			ShipServiceLocator service;
			ShipPortType port;
			service = new ShipServiceLocator();
			updateEndPoint(service);
			port = service.getShipServicePort();
			ShipmentReply reply = port.deleteShipment(deleteShipmentRequest ); 

			if (isResponseOk(reply.getHighestSeverity())){
				return true;
			}else{
				return false;
			}

		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}

	}*/

	/*public static void getShipments() {

		try {
			TrackRequest request = new TrackRequest();

			request.setClientDetail(createClientDetailTk());
			request.setWebAuthenticationDetail(createWebAuthenticationDetailTk());
			//
			TransactionDetailTk transactionDetail = new TransactionDetailTk();
			transactionDetail.setCustomerTransactionId("java sample - Tracking Request"); //This is a reference field for the customer.  Any value can be used and will be provided in the response.
			request.setTransactionDetail(transactionDetail);

			//
			VersionIdTk versionId = new VersionIdTk("trck", 16, 0, 0);
			request.setVersion(versionId);
			//
			TrackSelectionDetail selectionDetail=new TrackSelectionDetail();
			TrackPackageIdentifier packageIdentifier=new TrackPackageIdentifier();
			packageIdentifier.setType(TrackIdentifierType.TRACKING_NUMBER_OR_DOORTAG);
			//packageIdentifier.setValue(getSystemProperty("TrackingNumber")); // tracking number
			packageIdentifier.setValue(envioTrackingNumber); // tracking number
			selectionDetail.setPackageIdentifier(packageIdentifier);
			request.setSelectionDetails(new TrackSelectionDetail[] {selectionDetail});
			TrackRequestProcessingOptionType processingOption=TrackRequestProcessingOptionType.INCLUDE_DETAILED_SCANS;
			request.setProcessingOptions(new TrackRequestProcessingOptionType[]{processingOption});


			// Initializing the service
			TrackServiceLocator service;
			TrackPortType port;
			//
			service = new TrackServiceLocator();
			updateEndPointTK(service);
			port = service.getTrackServicePort();
			//
			TrackReply reply = port.track(request); // This is the call to the web service passing in a request object and returning a reply object
			//
			if(printNotificationsTK(reply.getNotifications())){
				printCompletedTrackDetail(reply.getCompletedTrackDetails());
			}
			if (isResponseOk(reply.getHighestSeverity())) // check if the call was successful
			{
				log.info("--Track Reply--");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} 
	}*/

//	private static void updateEndPointTK(TrackServiceLocator serviceLocator) {
//		String endPoint = System.getProperty("endPoint");
//		if (endPoint != null) {
//			serviceLocator.setTrackServicePortEndpointAddress(endPoint);
//		}
//	}

	/*private static boolean printNotificationsTK(Object n) {
		boolean cont=true;
		if(n!=null){
			NotificationTk[] notifications=null;
			NotificationTk notification=null;
			if(n instanceof NotificationTk[]){
				notifications=(NotificationTk[])n;
				if (notifications == null || notifications.length == 0) {
					log.info("  No notifications returned");
				}
				for (int i=0; i < notifications.length; i++){
					printNotification(notifications[i]);
					if(!success(notifications[i])){cont=false;}
				}
			}else if(n instanceof Notification){
				notification=(NotificationTk)n;
				printNotification(notification);
				if(!success(notification)){cont=false;}
			}

		}
		return cont;
	}*/


	/*private static boolean printNotifications(Object n) {
		boolean cont=true;
		if(n!=null){
			NotificationTk[] notifications=null;
			NotificationTk notification=null;
			if(n instanceof NotificationTk[]){
				notifications=(NotificationTk[])n;
				if (notifications == null || notifications.length == 0) {
					log.info("  No notifications returned");
				}
				for (int i=0; i < notifications.length; i++){
					printNotification(notifications[i]);
					if(!success(notifications[i])){cont=false;}
				}
			}else if(n instanceof Notification){
				notification=(NotificationTk)n;
				printNotification(notification);
				if(!success(notification)){cont=false;}
			}

		}
		return cont;
	}*/

	/*private static void printNotification(NotificationTk notification){
		if (notification == null) {
			log.info("null");
		}
		NotificationSeverityTypeTk nst = notification.getSeverity();

		print("  Severity", (nst == null ? "null" : nst.getValue()));
		print("  Code", notification.getCode());
		print("  Message", notification.getMessage());
		print("  Source", notification.getSource());
	}*/

	/*private static void printCompletedTrackDetail(CompletedTrackDetail[] ctd){
		for (int i=0; i< ctd.length; i++) { // package detail information
			boolean cont=true;
			log.info("--Completed Tracking Detail--");
			if(ctd[i].getNotifications()!=null){
				log.info("  Completed Track Detail Notifications--");
				cont=printNotificationsTK(ctd[i].getNotifications());
				log.info("  Completed Track Detail Notifications--");
			}
			if(cont){
				print("DuplicateWayBill", ctd[i].getDuplicateWaybill());
				print("Track Details Count", ctd[i].getTrackDetailsCount());
				if(ctd[i].getMoreData()){
					log.info("  Additional package data not yet retrieved");
					if(ctd[i].getPagingToken()!=null){
						print("  Paging Token", ctd[i].getPagingToken());
					}
				}
				printTrackDetail(ctd[i].getTrackDetails());				
			}
			log.info("--Completed Tracking Detail--");
			log.info();
		}
	}*/

	private static void print(Object k, Object v) {
		if (k == null || v == null) {
			return;
		}
		String key;
		String value;
		if(k instanceof String){
			key=(String)k;
		}else{
			key=k.toString();
		}
		if(v instanceof String){
			value=(String)v;
		}else{
			value=v.toString();
		}
		log.info("  " + key + ": " + value);
	}

	/*private static void print(Object o){
		if(o!=null){
			if(o instanceof String){
				log.info((String)o);
			}else if(o instanceof AddressTk){
				printAddress((AddressTk)o);
			}else if(o instanceof Calendar){
				printTime((Calendar)o);
			}else{
				log.info(o.toString());
			}

		}
	}*/

	/*private static void printTime(Calendar calendar){
		if(calendar!=null){
			int month = calendar.get(Calendar.MONTH)+1;
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			int year = calendar.get(Calendar.YEAR);
			String date = new String(year + "-" + month + "-" + day);
			print("Date", date);
			printDOW(calendar);
		}
	}*/

	/*private static void printDOW(Calendar calendar){
		if(calendar!=null){
			String day;
			switch(calendar.get(Calendar.DAY_OF_WEEK)){
			case 1: day="Sunday";
			break;
			case 2: day="Monday";
			break;
			case 3: day="Tuesday";
			break;
			case 4: day="Wedensday";
			break;
			case 5: day="Thursday";
			break;
			case 6: day="Friday";
			break;
			case 7: day="Saturday";
			break;
			default: day="Invalid Date";
			break;
			}
			print("Day of Week", day);	
		}
	}*/


	/*private static void printAddress(AddressTk address){
		print("__________________________________");
		if(address.getStreetLines()!=null){
			String[] streetLines=address.getStreetLines();
			for(int i=0;i<streetLines.length;i++){
				if(streetLines[i]!=null){
					print("Street", streetLines[i]);

				}
			}
		}		
		print("City", address.getCity());
		print("State or Province Code", address.getStateOrProvinceCode());
		print("Postal Code", address.getPostalCode());
		print("Country Code", address.getCountryCode());
		if(address.getResidential()!=null){
			if(address.getResidential()){
				print("Address Type","Residential");
			}else{
				print("Address Type", "Commercial");
			}
		}
		print("__________________________________");
	}*/

	/*private static void printTrackDetail(TrackDetail[] td){
		for (int i=0; i< td.length; i++) {
			boolean cont=true;
			log.info("--Track Details--");
			if(td[i].getNotification()!=null){
				log.info("  Track Detail Notification--");
				cont=printNotifications(td[i].getNotification());
				log.info("  Track Detail Notification--");
			}
			if(cont){
				print("Tracking Number", td[i].getTrackingNumber());
				print("Carrier code", td[i].getCarrierCode());
				if(td[i].getService()!=null){
					if(td[i].getService().getType()!=null && 
							td[i].getService().getDescription()!=null){
						print("Service", td[i].getService().getType());
						print("Description", td[i].getService().getDescription());
					}
				}
				if(td[i].getOtherIdentifiers()!=null){
					log.info("--Track Package Identifer--");
					printTrackOtherIdentifierDetail(td[i].getOtherIdentifiers());
					log.info("--Track Package Identifer--");
				}
				if(td[i].getStatusDetail()!=null){
					log.info("--Status Details--");
					printStatusDetail(td[i].getStatusDetail());
					log.info("--Status Details--");
				}
				if(td[i].getOriginLocationAddress()!=null){
					log.info("--Origin Location--");
					print(td[i].getOriginLocationAddress());
					log.info("--Origin Location--");
				}
				if(td[i].getDestinationAddress()!=null){
					log.info("--Destination Location--");
					printDestinationInformation(td[i]);
					log.info("--Destination Location--");
				}
				if(td[i].getActualDeliveryAddress()!=null){
					log.info("--Delivery Address--");
					print(td[i].getActualDeliveryAddress());
					log.info("--Delivery Address--");
				}
				if(td[i].getDatesOrTimes()!=null){
					TrackingDateOrTimestamp[] dates = td[i].getDatesOrTimes();
					for(int j=0; j<dates.length; j++){
						print(dates[j].getType().getValue(), dates[j].getDateOrTimestamp());
					}
				}
				if(td[i].getDeliveryAttempts().shortValue()>0){
					log.info("--Delivery Information--");
					printDeliveryInformation(td[i]);
					log.info("--Delivery Information--");
				}
				if(td[i].getCustomerExceptionRequests()!=null){
					log.info("--Customer Exception Information--");
					printCustomerExceptionRequests(td[i].getCustomerExceptionRequests());
					log.info("--Customer Exception Information--");
				}
				if(td[i].getCharges()!=null){
					log.info("--Charges--");
					printCharges(td[i].getCharges());
					log.info("--Charges--");
				}
				if(td[i].getEvents()!=null){
					log.info("--Tracking Events--");
					printTrackEvents(td[i].getEvents());
					log.info("--Tracking Events--");
				}
				log.info("--Track Details--");
				log.info();
			}
		}
	}*/

	/*private static void printTrackOtherIdentifierDetail(TrackOtherIdentifierDetail[] id){
		if(id!=null){
			for(int i=0; i<id.length; i++){
				if(id[i].getPackageIdentifier()!=null){
					print(id[i].getPackageIdentifier().getType(), 
							id[i].getPackageIdentifier().getValue());
				}
			}
		}
	}*/

	/*private static boolean success(NotificationTk notification){
		Boolean cont = true;
		if(notification!=null){
			if(notification.getSeverity()==NotificationSeverityTypeTk.FAILURE || 
					notification.getSeverity()==NotificationSeverityTypeTk.ERROR){
				cont=false;
			}
		}

		return cont;
	}*/

	/*private static void printTrackEvents(TrackEvent[] events){
		if(events!=null){
			for(int i=0; i<events.length;i++){
				TrackEvent event=events[i];
				print("Event no. ", i);
				print(event.getTimestamp());
				if(event.getEventType()!=null){
					print("Type", event.getEventType());
				}
				print("Station Id", event.getStationId());
				print("Exception Code", event.getStatusExceptionCode());
				print("", event.getStatusExceptionDescription());
				print("Description", event.getEventDescription());
				if(event.getAddress()!=null){
					log.info("  Event Address--");
					print(event.getAddress());
					log.info("  Event Address--");
				}
				log.info();
			}
		}
	}*/

	/*private static void printStatusDetail(TrackStatusDetail tsd){
		if(tsd!=null){
			print(tsd.getCreationTime());
			print("Code", tsd.getCode());
			if(tsd.getLocation()!=null){
				log.info("--Location Address Detail--");
				print(tsd.getLocation());
				log.info("--Location Address Detail--");
			}
			if(tsd.getAncillaryDetails()!=null){
				log.info("--Ancillary Details--");
				printAncillaryDetails(tsd.getAncillaryDetails());
				log.info("--Ancillary Details--");
			}
		}
	}*/

	/*private static void printAncillaryDetails(TrackStatusAncillaryDetail[] details){
		if(details!=null){
			for(int i=0; i<details.length;i++){
				if(details[i]!=null){
					if(details[i].getReason()!=null && details[i].getReasonDescription()!=null){
						print(details[i].getReason(), details[i].getReasonDescription());
					}
				}
			}
		}
	}*/

	/*private static void printCharges(TrackChargeDetail[] charges){
		if(charges!=null){
			for(int i=0; i<charges.length; i++){
				print("Charge Type", charges[i].getType());
				printMoney(charges[i].getChargeAmount());
			}
		}
	}

	private static void printMoney(Money money){
		if(money!=null){
			String currency = money.getCurrency();
			String amount = money.getAmount().toString();
			print("Charge", currency + " " + amount);
		}
	}*/

	/*private static void printCustomerExceptionRequests(CustomerExceptionRequestDetail[] exceptions){
		if(exceptions!=null){
			for(int i=0; i<exceptions.length; i++){
				CustomerExceptionRequestDetail exception=exceptions[i];
				print("Exception Id", exception.getId());
				print("Excpetion Status Code", exception.getStatusCode());
				print("Excpetion Status Description", exception.getStatusDescription());
				if(exception.getCreateTime()!=null){
					log.info("  Customer Exception Date--");
					print(exception.getCreateTime());
					log.info("  Customer Exception Date--");
				}
			}
		}
	}

	private static void printDestinationInformation(TrackDetail td){
		if(td.getDestinationAddress()!=null){
			print(td.getDestinationAddress());
		}
		print("Destination Type", td.getDestinationLocationType());
		print("Service Area", td.getDestinationServiceArea());
		print("Service Area Description", td.getDestinationServiceAreaDescription());
		print("Station Id", td.getDestinationStationId());
		print("Destination Timezone Offset", td.getDestinationLocationTimeZoneOffset());
	}

	private static void printDeliveryInformation(TrackDetail td){
		log.info("Delivery attempts: " + td.getDeliveryAttempts());
		print("Delivery Location", td.getDeliveryLocationDescription());
		print("Delivery Signature", td.getDeliverySignatureName());
		if(td.getDeliveryOptionEligibilityDetails()!=null){
			log.info("Delivery Options");
			printDeliveryOptionEligibility(td.getDeliveryOptionEligibilityDetails());
		}
	}

	private static void printDeliveryOptionEligibility(DeliveryOptionEligibilityDetail[] options){
		for(int i=0; i<options.length; i++){
			DeliveryOptionEligibilityDetail option = options[i];
			if(option!=null){
				print(option.getOption(), option.getEligibility());
			}
		}
	}*/

}
