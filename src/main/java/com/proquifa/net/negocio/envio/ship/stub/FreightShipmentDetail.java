/**
 * FreightShipmentDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.proquifa.net.negocio.envio.ship.stub;


/**
 * Data applicable to shipments using FEDEX_FREIGHT_ECONOMY and FEDEX_FREIGHT_PRIORITY
 * services.
 */
public class FreightShipmentDetail  implements java.io.Serializable {
    /* Account number used with FEDEX_FREIGHT service. */
    private java.lang.String fedExFreightAccountNumber;

    /* Used for validating FedEx Freight account number and (optionally)
     * identifying third party payment on the bill of lading. */
    private com.proquifa.net.negocio.envio.ship.stub.ContactAndAddress fedExFreightBillingContactAndAddress;

    /* Used in connection with "Send Bill To" (SBT) identification
     * of customer's account used for billing. */
    private com.proquifa.net.negocio.envio.ship.stub.Party alternateBilling;

    /* Identification values to be printed during creation of a Freight
     * bill of lading. */
    private com.proquifa.net.negocio.envio.ship.stub.PrintedReference[] printedReferences;

    /* Indicates the role of the party submitting the transaction. */
    private com.proquifa.net.negocio.envio.ship.stub.FreightShipmentRoleType role;

    /* Designates the terms of the "collect" payment for a Freight
     * Shipment. */
    private com.proquifa.net.negocio.envio.ship.stub.FreightCollectTermsType collectTermsType;

    /* Identifies the declared value for the shipment */
    private com.proquifa.net.negocio.envio.ship.stub.Money declaredValuePerUnit;

    /* Identifies the declared value units corresponding to the above
     * defined declared value */
    private java.lang.String declaredValueUnits;

    private com.proquifa.net.negocio.envio.ship.stub.LiabilityCoverageDetail liabilityCoverageDetail;

    /* Identifiers for promotional discounts offered to customers. */
    private java.lang.String[] coupons;

    /* Total number of individual handling units in the entire shipment
     * (for unit pricing). */
    private org.apache.axis.types.NonNegativeInteger totalHandlingUnits;

    /* Estimated discount rate provided by client for unsecured rate
     * quote. */
    private java.math.BigDecimal clientDiscountPercent;

    /* Total weight of pallets used in shipment. */
    private com.proquifa.net.negocio.envio.ship.stub.Weight palletWeight;

    /* Overall shipment dimensions. */
    private com.proquifa.net.negocio.envio.ship.stub.Dimensions shipmentDimensions;

    /* Description for the shipment. */
    private java.lang.String comment;

    /* Specifies which party will pay surcharges for any special services
     * which support split billing. */
    private com.proquifa.net.negocio.envio.ship.stub.FreightSpecialServicePayment[] specialServicePayments;

    /* Must be populated if any line items contain hazardous materials. */
    private java.lang.String hazardousMaterialsEmergencyContactNumber;

    private java.lang.String hazardousMaterialsOfferor;

    /* Details of the commodities in the shipment. */
    private com.proquifa.net.negocio.envio.ship.stub.FreightShipmentLineItem[] lineItems;

    public FreightShipmentDetail() {
    }

    public FreightShipmentDetail(
           java.lang.String fedExFreightAccountNumber,
           com.proquifa.net.negocio.envio.ship.stub.ContactAndAddress fedExFreightBillingContactAndAddress,
           com.proquifa.net.negocio.envio.ship.stub.Party alternateBilling,
           com.proquifa.net.negocio.envio.ship.stub.PrintedReference[] printedReferences,
           com.proquifa.net.negocio.envio.ship.stub.FreightShipmentRoleType role,
           com.proquifa.net.negocio.envio.ship.stub.FreightCollectTermsType collectTermsType,
           com.proquifa.net.negocio.envio.ship.stub.Money declaredValuePerUnit,
           java.lang.String declaredValueUnits,
           com.proquifa.net.negocio.envio.ship.stub.LiabilityCoverageDetail liabilityCoverageDetail,
           java.lang.String[] coupons,
           org.apache.axis.types.NonNegativeInteger totalHandlingUnits,
           java.math.BigDecimal clientDiscountPercent,
           com.proquifa.net.negocio.envio.ship.stub.Weight palletWeight,
           com.proquifa.net.negocio.envio.ship.stub.Dimensions shipmentDimensions,
           java.lang.String comment,
           com.proquifa.net.negocio.envio.ship.stub.FreightSpecialServicePayment[] specialServicePayments,
           java.lang.String hazardousMaterialsEmergencyContactNumber,
           java.lang.String hazardousMaterialsOfferor,
           com.proquifa.net.negocio.envio.ship.stub.FreightShipmentLineItem[] lineItems) {
           this.fedExFreightAccountNumber = fedExFreightAccountNumber;
           this.fedExFreightBillingContactAndAddress = fedExFreightBillingContactAndAddress;
           this.alternateBilling = alternateBilling;
           this.printedReferences = printedReferences;
           this.role = role;
           this.collectTermsType = collectTermsType;
           this.declaredValuePerUnit = declaredValuePerUnit;
           this.declaredValueUnits = declaredValueUnits;
           this.liabilityCoverageDetail = liabilityCoverageDetail;
           this.coupons = coupons;
           this.totalHandlingUnits = totalHandlingUnits;
           this.clientDiscountPercent = clientDiscountPercent;
           this.palletWeight = palletWeight;
           this.shipmentDimensions = shipmentDimensions;
           this.comment = comment;
           this.specialServicePayments = specialServicePayments;
           this.hazardousMaterialsEmergencyContactNumber = hazardousMaterialsEmergencyContactNumber;
           this.hazardousMaterialsOfferor = hazardousMaterialsOfferor;
           this.lineItems = lineItems;
    }


    /**
     * Gets the fedExFreightAccountNumber value for this FreightShipmentDetail.
     * 
     * @return fedExFreightAccountNumber   * Account number used with FEDEX_FREIGHT service.
     */
    public java.lang.String getFedExFreightAccountNumber() {
        return fedExFreightAccountNumber;
    }


    /**
     * Sets the fedExFreightAccountNumber value for this FreightShipmentDetail.
     * 
     * @param fedExFreightAccountNumber   * Account number used with FEDEX_FREIGHT service.
     */
    public void setFedExFreightAccountNumber(java.lang.String fedExFreightAccountNumber) {
        this.fedExFreightAccountNumber = fedExFreightAccountNumber;
    }


    /**
     * Gets the fedExFreightBillingContactAndAddress value for this FreightShipmentDetail.
     * 
     * @return fedExFreightBillingContactAndAddress   * Used for validating FedEx Freight account number and (optionally)
     * identifying third party payment on the bill of lading.
     */
    public com.proquifa.net.negocio.envio.ship.stub.ContactAndAddress getFedExFreightBillingContactAndAddress() {
        return fedExFreightBillingContactAndAddress;
    }


    /**
     * Sets the fedExFreightBillingContactAndAddress value for this FreightShipmentDetail.
     * 
     * @param fedExFreightBillingContactAndAddress   * Used for validating FedEx Freight account number and (optionally)
     * identifying third party payment on the bill of lading.
     */
    public void setFedExFreightBillingContactAndAddress(com.proquifa.net.negocio.envio.ship.stub.ContactAndAddress fedExFreightBillingContactAndAddress) {
        this.fedExFreightBillingContactAndAddress = fedExFreightBillingContactAndAddress;
    }


    /**
     * Gets the alternateBilling value for this FreightShipmentDetail.
     * 
     * @return alternateBilling   * Used in connection with "Send Bill To" (SBT) identification
     * of customer's account used for billing.
     */
    public com.proquifa.net.negocio.envio.ship.stub.Party getAlternateBilling() {
        return alternateBilling;
    }


    /**
     * Sets the alternateBilling value for this FreightShipmentDetail.
     * 
     * @param alternateBilling   * Used in connection with "Send Bill To" (SBT) identification
     * of customer's account used for billing.
     */
    public void setAlternateBilling(com.proquifa.net.negocio.envio.ship.stub.Party alternateBilling) {
        this.alternateBilling = alternateBilling;
    }


    /**
     * Gets the printedReferences value for this FreightShipmentDetail.
     * 
     * @return printedReferences   * Identification values to be printed during creation of a Freight
     * bill of lading.
     */
    public com.proquifa.net.negocio.envio.ship.stub.PrintedReference[] getPrintedReferences() {
        return printedReferences;
    }


    /**
     * Sets the printedReferences value for this FreightShipmentDetail.
     * 
     * @param printedReferences   * Identification values to be printed during creation of a Freight
     * bill of lading.
     */
    public void setPrintedReferences(com.proquifa.net.negocio.envio.ship.stub.PrintedReference[] printedReferences) {
        this.printedReferences = printedReferences;
    }

    public com.proquifa.net.negocio.envio.ship.stub.PrintedReference getPrintedReferences(int i) {
        return this.printedReferences[i];
    }

    public void setPrintedReferences(int i, com.proquifa.net.negocio.envio.ship.stub.PrintedReference _value) {
        this.printedReferences[i] = _value;
    }


    /**
     * Gets the role value for this FreightShipmentDetail.
     * 
     * @return role   * Indicates the role of the party submitting the transaction.
     */
    public com.proquifa.net.negocio.envio.ship.stub.FreightShipmentRoleType getRole() {
        return role;
    }


    /**
     * Sets the role value for this FreightShipmentDetail.
     * 
     * @param role   * Indicates the role of the party submitting the transaction.
     */
    public void setRole(com.proquifa.net.negocio.envio.ship.stub.FreightShipmentRoleType role) {
        this.role = role;
    }


    /**
     * Gets the collectTermsType value for this FreightShipmentDetail.
     * 
     * @return collectTermsType   * Designates the terms of the "collect" payment for a Freight
     * Shipment.
     */
    public com.proquifa.net.negocio.envio.ship.stub.FreightCollectTermsType getCollectTermsType() {
        return collectTermsType;
    }


    /**
     * Sets the collectTermsType value for this FreightShipmentDetail.
     * 
     * @param collectTermsType   * Designates the terms of the "collect" payment for a Freight
     * Shipment.
     */
    public void setCollectTermsType(com.proquifa.net.negocio.envio.ship.stub.FreightCollectTermsType collectTermsType) {
        this.collectTermsType = collectTermsType;
    }


    /**
     * Gets the declaredValuePerUnit value for this FreightShipmentDetail.
     * 
     * @return declaredValuePerUnit   * Identifies the declared value for the shipment
     */
    public com.proquifa.net.negocio.envio.ship.stub.Money getDeclaredValuePerUnit() {
        return declaredValuePerUnit;
    }


    /**
     * Sets the declaredValuePerUnit value for this FreightShipmentDetail.
     * 
     * @param declaredValuePerUnit   * Identifies the declared value for the shipment
     */
    public void setDeclaredValuePerUnit(com.proquifa.net.negocio.envio.ship.stub.Money declaredValuePerUnit) {
        this.declaredValuePerUnit = declaredValuePerUnit;
    }


    /**
     * Gets the declaredValueUnits value for this FreightShipmentDetail.
     * 
     * @return declaredValueUnits   * Identifies the declared value units corresponding to the above
     * defined declared value
     */
    public java.lang.String getDeclaredValueUnits() {
        return declaredValueUnits;
    }


    /**
     * Sets the declaredValueUnits value for this FreightShipmentDetail.
     * 
     * @param declaredValueUnits   * Identifies the declared value units corresponding to the above
     * defined declared value
     */
    public void setDeclaredValueUnits(java.lang.String declaredValueUnits) {
        this.declaredValueUnits = declaredValueUnits;
    }


    /**
     * Gets the liabilityCoverageDetail value for this FreightShipmentDetail.
     * 
     * @return liabilityCoverageDetail
     */
    public com.proquifa.net.negocio.envio.ship.stub.LiabilityCoverageDetail getLiabilityCoverageDetail() {
        return liabilityCoverageDetail;
    }


    /**
     * Sets the liabilityCoverageDetail value for this FreightShipmentDetail.
     * 
     * @param liabilityCoverageDetail
     */
    public void setLiabilityCoverageDetail(com.proquifa.net.negocio.envio.ship.stub.LiabilityCoverageDetail liabilityCoverageDetail) {
        this.liabilityCoverageDetail = liabilityCoverageDetail;
    }


    /**
     * Gets the coupons value for this FreightShipmentDetail.
     * 
     * @return coupons   * Identifiers for promotional discounts offered to customers.
     */
    public java.lang.String[] getCoupons() {
        return coupons;
    }


    /**
     * Sets the coupons value for this FreightShipmentDetail.
     * 
     * @param coupons   * Identifiers for promotional discounts offered to customers.
     */
    public void setCoupons(java.lang.String[] coupons) {
        this.coupons = coupons;
    }

    public java.lang.String getCoupons(int i) {
        return this.coupons[i];
    }

    public void setCoupons(int i, java.lang.String _value) {
        this.coupons[i] = _value;
    }


    /**
     * Gets the totalHandlingUnits value for this FreightShipmentDetail.
     * 
     * @return totalHandlingUnits   * Total number of individual handling units in the entire shipment
     * (for unit pricing).
     */
    public org.apache.axis.types.NonNegativeInteger getTotalHandlingUnits() {
        return totalHandlingUnits;
    }


    /**
     * Sets the totalHandlingUnits value for this FreightShipmentDetail.
     * 
     * @param totalHandlingUnits   * Total number of individual handling units in the entire shipment
     * (for unit pricing).
     */
    public void setTotalHandlingUnits(org.apache.axis.types.NonNegativeInteger totalHandlingUnits) {
        this.totalHandlingUnits = totalHandlingUnits;
    }


    /**
     * Gets the clientDiscountPercent value for this FreightShipmentDetail.
     * 
     * @return clientDiscountPercent   * Estimated discount rate provided by client for unsecured rate
     * quote.
     */
    public java.math.BigDecimal getClientDiscountPercent() {
        return clientDiscountPercent;
    }


    /**
     * Sets the clientDiscountPercent value for this FreightShipmentDetail.
     * 
     * @param clientDiscountPercent   * Estimated discount rate provided by client for unsecured rate
     * quote.
     */
    public void setClientDiscountPercent(java.math.BigDecimal clientDiscountPercent) {
        this.clientDiscountPercent = clientDiscountPercent;
    }


    /**
     * Gets the palletWeight value for this FreightShipmentDetail.
     * 
     * @return palletWeight   * Total weight of pallets used in shipment.
     */
    public com.proquifa.net.negocio.envio.ship.stub.Weight getPalletWeight() {
        return palletWeight;
    }


    /**
     * Sets the palletWeight value for this FreightShipmentDetail.
     * 
     * @param palletWeight   * Total weight of pallets used in shipment.
     */
    public void setPalletWeight(com.proquifa.net.negocio.envio.ship.stub.Weight palletWeight) {
        this.palletWeight = palletWeight;
    }


    /**
     * Gets the shipmentDimensions value for this FreightShipmentDetail.
     * 
     * @return shipmentDimensions   * Overall shipment dimensions.
     */
    public com.proquifa.net.negocio.envio.ship.stub.Dimensions getShipmentDimensions() {
        return shipmentDimensions;
    }


    /**
     * Sets the shipmentDimensions value for this FreightShipmentDetail.
     * 
     * @param shipmentDimensions   * Overall shipment dimensions.
     */
    public void setShipmentDimensions(com.proquifa.net.negocio.envio.ship.stub.Dimensions shipmentDimensions) {
        this.shipmentDimensions = shipmentDimensions;
    }


    /**
     * Gets the comment value for this FreightShipmentDetail.
     * 
     * @return comment   * Description for the shipment.
     */
    public java.lang.String getComment() {
        return comment;
    }


    /**
     * Sets the comment value for this FreightShipmentDetail.
     * 
     * @param comment   * Description for the shipment.
     */
    public void setComment(java.lang.String comment) {
        this.comment = comment;
    }


    /**
     * Gets the specialServicePayments value for this FreightShipmentDetail.
     * 
     * @return specialServicePayments   * Specifies which party will pay surcharges for any special services
     * which support split billing.
     */
    public com.proquifa.net.negocio.envio.ship.stub.FreightSpecialServicePayment[] getSpecialServicePayments() {
        return specialServicePayments;
    }


    /**
     * Sets the specialServicePayments value for this FreightShipmentDetail.
     * 
     * @param specialServicePayments   * Specifies which party will pay surcharges for any special services
     * which support split billing.
     */
    public void setSpecialServicePayments(com.proquifa.net.negocio.envio.ship.stub.FreightSpecialServicePayment[] specialServicePayments) {
        this.specialServicePayments = specialServicePayments;
    }

    public com.proquifa.net.negocio.envio.ship.stub.FreightSpecialServicePayment getSpecialServicePayments(int i) {
        return this.specialServicePayments[i];
    }

    public void setSpecialServicePayments(int i, com.proquifa.net.negocio.envio.ship.stub.FreightSpecialServicePayment _value) {
        this.specialServicePayments[i] = _value;
    }


    /**
     * Gets the hazardousMaterialsEmergencyContactNumber value for this FreightShipmentDetail.
     * 
     * @return hazardousMaterialsEmergencyContactNumber   * Must be populated if any line items contain hazardous materials.
     */
    public java.lang.String getHazardousMaterialsEmergencyContactNumber() {
        return hazardousMaterialsEmergencyContactNumber;
    }


    /**
     * Sets the hazardousMaterialsEmergencyContactNumber value for this FreightShipmentDetail.
     * 
     * @param hazardousMaterialsEmergencyContactNumber   * Must be populated if any line items contain hazardous materials.
     */
    public void setHazardousMaterialsEmergencyContactNumber(java.lang.String hazardousMaterialsEmergencyContactNumber) {
        this.hazardousMaterialsEmergencyContactNumber = hazardousMaterialsEmergencyContactNumber;
    }


    /**
     * Gets the hazardousMaterialsOfferor value for this FreightShipmentDetail.
     * 
     * @return hazardousMaterialsOfferor
     */
    public java.lang.String getHazardousMaterialsOfferor() {
        return hazardousMaterialsOfferor;
    }


    /**
     * Sets the hazardousMaterialsOfferor value for this FreightShipmentDetail.
     * 
     * @param hazardousMaterialsOfferor
     */
    public void setHazardousMaterialsOfferor(java.lang.String hazardousMaterialsOfferor) {
        this.hazardousMaterialsOfferor = hazardousMaterialsOfferor;
    }


    /**
     * Gets the lineItems value for this FreightShipmentDetail.
     * 
     * @return lineItems   * Details of the commodities in the shipment.
     */
    public com.proquifa.net.negocio.envio.ship.stub.FreightShipmentLineItem[] getLineItems() {
        return lineItems;
    }


    /**
     * Sets the lineItems value for this FreightShipmentDetail.
     * 
     * @param lineItems   * Details of the commodities in the shipment.
     */
    public void setLineItems(com.proquifa.net.negocio.envio.ship.stub.FreightShipmentLineItem[] lineItems) {
        this.lineItems = lineItems;
    }

    public com.proquifa.net.negocio.envio.ship.stub.FreightShipmentLineItem getLineItems(int i) {
        return this.lineItems[i];
    }

    public void setLineItems(int i, com.proquifa.net.negocio.envio.ship.stub.FreightShipmentLineItem _value) {
        this.lineItems[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FreightShipmentDetail)) return false;
        FreightShipmentDetail other = (FreightShipmentDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.fedExFreightAccountNumber==null && other.getFedExFreightAccountNumber()==null) || 
             (this.fedExFreightAccountNumber!=null &&
              this.fedExFreightAccountNumber.equals(other.getFedExFreightAccountNumber()))) &&
            ((this.fedExFreightBillingContactAndAddress==null && other.getFedExFreightBillingContactAndAddress()==null) || 
             (this.fedExFreightBillingContactAndAddress!=null &&
              this.fedExFreightBillingContactAndAddress.equals(other.getFedExFreightBillingContactAndAddress()))) &&
            ((this.alternateBilling==null && other.getAlternateBilling()==null) || 
             (this.alternateBilling!=null &&
              this.alternateBilling.equals(other.getAlternateBilling()))) &&
            ((this.printedReferences==null && other.getPrintedReferences()==null) || 
             (this.printedReferences!=null &&
              java.util.Arrays.equals(this.printedReferences, other.getPrintedReferences()))) &&
            ((this.role==null && other.getRole()==null) || 
             (this.role!=null &&
              this.role.equals(other.getRole()))) &&
            ((this.collectTermsType==null && other.getCollectTermsType()==null) || 
             (this.collectTermsType!=null &&
              this.collectTermsType.equals(other.getCollectTermsType()))) &&
            ((this.declaredValuePerUnit==null && other.getDeclaredValuePerUnit()==null) || 
             (this.declaredValuePerUnit!=null &&
              this.declaredValuePerUnit.equals(other.getDeclaredValuePerUnit()))) &&
            ((this.declaredValueUnits==null && other.getDeclaredValueUnits()==null) || 
             (this.declaredValueUnits!=null &&
              this.declaredValueUnits.equals(other.getDeclaredValueUnits()))) &&
            ((this.liabilityCoverageDetail==null && other.getLiabilityCoverageDetail()==null) || 
             (this.liabilityCoverageDetail!=null &&
              this.liabilityCoverageDetail.equals(other.getLiabilityCoverageDetail()))) &&
            ((this.coupons==null && other.getCoupons()==null) || 
             (this.coupons!=null &&
              java.util.Arrays.equals(this.coupons, other.getCoupons()))) &&
            ((this.totalHandlingUnits==null && other.getTotalHandlingUnits()==null) || 
             (this.totalHandlingUnits!=null &&
              this.totalHandlingUnits.equals(other.getTotalHandlingUnits()))) &&
            ((this.clientDiscountPercent==null && other.getClientDiscountPercent()==null) || 
             (this.clientDiscountPercent!=null &&
              this.clientDiscountPercent.equals(other.getClientDiscountPercent()))) &&
            ((this.palletWeight==null && other.getPalletWeight()==null) || 
             (this.palletWeight!=null &&
              this.palletWeight.equals(other.getPalletWeight()))) &&
            ((this.shipmentDimensions==null && other.getShipmentDimensions()==null) || 
             (this.shipmentDimensions!=null &&
              this.shipmentDimensions.equals(other.getShipmentDimensions()))) &&
            ((this.comment==null && other.getComment()==null) || 
             (this.comment!=null &&
              this.comment.equals(other.getComment()))) &&
            ((this.specialServicePayments==null && other.getSpecialServicePayments()==null) || 
             (this.specialServicePayments!=null &&
              java.util.Arrays.equals(this.specialServicePayments, other.getSpecialServicePayments()))) &&
            ((this.hazardousMaterialsEmergencyContactNumber==null && other.getHazardousMaterialsEmergencyContactNumber()==null) || 
             (this.hazardousMaterialsEmergencyContactNumber!=null &&
              this.hazardousMaterialsEmergencyContactNumber.equals(other.getHazardousMaterialsEmergencyContactNumber()))) &&
            ((this.hazardousMaterialsOfferor==null && other.getHazardousMaterialsOfferor()==null) || 
             (this.hazardousMaterialsOfferor!=null &&
              this.hazardousMaterialsOfferor.equals(other.getHazardousMaterialsOfferor()))) &&
            ((this.lineItems==null && other.getLineItems()==null) || 
             (this.lineItems!=null &&
              java.util.Arrays.equals(this.lineItems, other.getLineItems())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getFedExFreightAccountNumber() != null) {
            _hashCode += getFedExFreightAccountNumber().hashCode();
        }
        if (getFedExFreightBillingContactAndAddress() != null) {
            _hashCode += getFedExFreightBillingContactAndAddress().hashCode();
        }
        if (getAlternateBilling() != null) {
            _hashCode += getAlternateBilling().hashCode();
        }
        if (getPrintedReferences() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPrintedReferences());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPrintedReferences(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getRole() != null) {
            _hashCode += getRole().hashCode();
        }
        if (getCollectTermsType() != null) {
            _hashCode += getCollectTermsType().hashCode();
        }
        if (getDeclaredValuePerUnit() != null) {
            _hashCode += getDeclaredValuePerUnit().hashCode();
        }
        if (getDeclaredValueUnits() != null) {
            _hashCode += getDeclaredValueUnits().hashCode();
        }
        if (getLiabilityCoverageDetail() != null) {
            _hashCode += getLiabilityCoverageDetail().hashCode();
        }
        if (getCoupons() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCoupons());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCoupons(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTotalHandlingUnits() != null) {
            _hashCode += getTotalHandlingUnits().hashCode();
        }
        if (getClientDiscountPercent() != null) {
            _hashCode += getClientDiscountPercent().hashCode();
        }
        if (getPalletWeight() != null) {
            _hashCode += getPalletWeight().hashCode();
        }
        if (getShipmentDimensions() != null) {
            _hashCode += getShipmentDimensions().hashCode();
        }
        if (getComment() != null) {
            _hashCode += getComment().hashCode();
        }
        if (getSpecialServicePayments() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSpecialServicePayments());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSpecialServicePayments(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getHazardousMaterialsEmergencyContactNumber() != null) {
            _hashCode += getHazardousMaterialsEmergencyContactNumber().hashCode();
        }
        if (getHazardousMaterialsOfferor() != null) {
            _hashCode += getHazardousMaterialsOfferor().hashCode();
        }
        if (getLineItems() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getLineItems());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getLineItems(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FreightShipmentDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FreightShipmentDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fedExFreightAccountNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FedExFreightAccountNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fedExFreightBillingContactAndAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FedExFreightBillingContactAndAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ContactAndAddress"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("alternateBilling");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "AlternateBilling"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Party"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("printedReferences");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PrintedReferences"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PrintedReference"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("role");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Role"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FreightShipmentRoleType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("collectTermsType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CollectTermsType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FreightCollectTermsType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("declaredValuePerUnit");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DeclaredValuePerUnit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Money"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("declaredValueUnits");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DeclaredValueUnits"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("liabilityCoverageDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "LiabilityCoverageDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "LiabilityCoverageDetail"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("coupons");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Coupons"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalHandlingUnits");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TotalHandlingUnits"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "nonNegativeInteger"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientDiscountPercent");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ClientDiscountPercent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("palletWeight");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PalletWeight"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Weight"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shipmentDimensions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShipmentDimensions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Dimensions"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Comment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("specialServicePayments");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "SpecialServicePayments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FreightSpecialServicePayment"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hazardousMaterialsEmergencyContactNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "HazardousMaterialsEmergencyContactNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hazardousMaterialsOfferor");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "HazardousMaterialsOfferor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lineItems");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "LineItems"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FreightShipmentLineItem"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
