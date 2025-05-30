/**
 * ShipmentOperationalDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.proquifa.net.negocio.envio.ship.stub;

public class ShipmentOperationalDetail  implements java.io.Serializable {
    private java.lang.String ursaPrefixCode;

    private java.lang.String ursaSuffixCode;

    private java.lang.String originLocationId;

    private java.lang.Integer originLocationNumber;

    private java.lang.String originServiceArea;

    private java.lang.String destinationLocationId;

    private java.lang.Integer destinationLocationNumber;

    private java.lang.String destinationServiceArea;

    /* This is the state of the destination location ID, and is not
     * necessarily the same as the postal state. */
    private java.lang.String destinationLocationStateOrProvinceCode;

    /* Expected/estimated date of delivery. */
    private java.util.Date deliveryDate;

    /* Expected/estimated day of week of delivery. */
    private com.proquifa.net.negocio.envio.ship.stub.DayOfWeekType deliveryDay;

    /* Delivery time, as published in Service Guide. */
    private org.apache.axis.types.Time publishedDeliveryTime;

    /* Committed date of delivery. */
    private java.util.Date commitDate;

    /* Committed day of week of delivery. */
    private com.proquifa.net.negocio.envio.ship.stub.DayOfWeekType commitDay;

    /* Standard transit time per origin, destination, and service. */
    private com.proquifa.net.negocio.envio.ship.stub.TransitTimeType transitTime;

    /* Maximum expected transit time */
    private com.proquifa.net.negocio.envio.ship.stub.TransitTimeType maximumTransitTime;

    /* Transit time based on customer eligibility. */
    private com.proquifa.net.negocio.envio.ship.stub.TransitTimeType customTransitTime;

    /* Indicates that this shipment is not eligible for money back
     * guarantee. */
    private java.lang.Boolean ineligibleForMoneyBackGuarantee;

    /* FedEx Ground delivery features for which this shipment may
     * be eligible. */
    private com.proquifa.net.negocio.envio.ship.stub.GroundDeliveryEligibilityType[] deliveryEligibilities;

    /* Text describing planned delivery. */
    private java.lang.String astraPlannedServiceLevel;

    private java.lang.String astraDescription;

    private java.lang.String postalCode;

    private java.lang.String stateOrProvinceCode;

    private java.lang.String countryCode;

    private java.lang.String airportId;

    private java.lang.String serviceCode;

    private java.lang.String packagingCode;

    private java.lang.String scac;

    public ShipmentOperationalDetail() {
    }

    public ShipmentOperationalDetail(
           java.lang.String ursaPrefixCode,
           java.lang.String ursaSuffixCode,
           java.lang.String originLocationId,
           java.lang.Integer originLocationNumber,
           java.lang.String originServiceArea,
           java.lang.String destinationLocationId,
           java.lang.Integer destinationLocationNumber,
           java.lang.String destinationServiceArea,
           java.lang.String destinationLocationStateOrProvinceCode,
           java.util.Date deliveryDate,
           com.proquifa.net.negocio.envio.ship.stub.DayOfWeekType deliveryDay,
           org.apache.axis.types.Time publishedDeliveryTime,
           java.util.Date commitDate,
           com.proquifa.net.negocio.envio.ship.stub.DayOfWeekType commitDay,
           com.proquifa.net.negocio.envio.ship.stub.TransitTimeType transitTime,
           com.proquifa.net.negocio.envio.ship.stub.TransitTimeType maximumTransitTime,
           com.proquifa.net.negocio.envio.ship.stub.TransitTimeType customTransitTime,
           java.lang.Boolean ineligibleForMoneyBackGuarantee,
           com.proquifa.net.negocio.envio.ship.stub.GroundDeliveryEligibilityType[] deliveryEligibilities,
           java.lang.String astraPlannedServiceLevel,
           java.lang.String astraDescription,
           java.lang.String postalCode,
           java.lang.String stateOrProvinceCode,
           java.lang.String countryCode,
           java.lang.String airportId,
           java.lang.String serviceCode,
           java.lang.String packagingCode,
           java.lang.String scac) {
           this.ursaPrefixCode = ursaPrefixCode;
           this.ursaSuffixCode = ursaSuffixCode;
           this.originLocationId = originLocationId;
           this.originLocationNumber = originLocationNumber;
           this.originServiceArea = originServiceArea;
           this.destinationLocationId = destinationLocationId;
           this.destinationLocationNumber = destinationLocationNumber;
           this.destinationServiceArea = destinationServiceArea;
           this.destinationLocationStateOrProvinceCode = destinationLocationStateOrProvinceCode;
           this.deliveryDate = deliveryDate;
           this.deliveryDay = deliveryDay;
           this.publishedDeliveryTime = publishedDeliveryTime;
           this.commitDate = commitDate;
           this.commitDay = commitDay;
           this.transitTime = transitTime;
           this.maximumTransitTime = maximumTransitTime;
           this.customTransitTime = customTransitTime;
           this.ineligibleForMoneyBackGuarantee = ineligibleForMoneyBackGuarantee;
           this.deliveryEligibilities = deliveryEligibilities;
           this.astraPlannedServiceLevel = astraPlannedServiceLevel;
           this.astraDescription = astraDescription;
           this.postalCode = postalCode;
           this.stateOrProvinceCode = stateOrProvinceCode;
           this.countryCode = countryCode;
           this.airportId = airportId;
           this.serviceCode = serviceCode;
           this.packagingCode = packagingCode;
           this.scac = scac;
    }


    /**
     * Gets the ursaPrefixCode value for this ShipmentOperationalDetail.
     * 
     * @return ursaPrefixCode
     */
    public java.lang.String getUrsaPrefixCode() {
        return ursaPrefixCode;
    }


    /**
     * Sets the ursaPrefixCode value for this ShipmentOperationalDetail.
     * 
     * @param ursaPrefixCode
     */
    public void setUrsaPrefixCode(java.lang.String ursaPrefixCode) {
        this.ursaPrefixCode = ursaPrefixCode;
    }


    /**
     * Gets the ursaSuffixCode value for this ShipmentOperationalDetail.
     * 
     * @return ursaSuffixCode
     */
    public java.lang.String getUrsaSuffixCode() {
        return ursaSuffixCode;
    }


    /**
     * Sets the ursaSuffixCode value for this ShipmentOperationalDetail.
     * 
     * @param ursaSuffixCode
     */
    public void setUrsaSuffixCode(java.lang.String ursaSuffixCode) {
        this.ursaSuffixCode = ursaSuffixCode;
    }


    /**
     * Gets the originLocationId value for this ShipmentOperationalDetail.
     * 
     * @return originLocationId
     */
    public java.lang.String getOriginLocationId() {
        return originLocationId;
    }


    /**
     * Sets the originLocationId value for this ShipmentOperationalDetail.
     * 
     * @param originLocationId
     */
    public void setOriginLocationId(java.lang.String originLocationId) {
        this.originLocationId = originLocationId;
    }


    /**
     * Gets the originLocationNumber value for this ShipmentOperationalDetail.
     * 
     * @return originLocationNumber
     */
    public java.lang.Integer getOriginLocationNumber() {
        return originLocationNumber;
    }


    /**
     * Sets the originLocationNumber value for this ShipmentOperationalDetail.
     * 
     * @param originLocationNumber
     */
    public void setOriginLocationNumber(java.lang.Integer originLocationNumber) {
        this.originLocationNumber = originLocationNumber;
    }


    /**
     * Gets the originServiceArea value for this ShipmentOperationalDetail.
     * 
     * @return originServiceArea
     */
    public java.lang.String getOriginServiceArea() {
        return originServiceArea;
    }


    /**
     * Sets the originServiceArea value for this ShipmentOperationalDetail.
     * 
     * @param originServiceArea
     */
    public void setOriginServiceArea(java.lang.String originServiceArea) {
        this.originServiceArea = originServiceArea;
    }


    /**
     * Gets the destinationLocationId value for this ShipmentOperationalDetail.
     * 
     * @return destinationLocationId
     */
    public java.lang.String getDestinationLocationId() {
        return destinationLocationId;
    }


    /**
     * Sets the destinationLocationId value for this ShipmentOperationalDetail.
     * 
     * @param destinationLocationId
     */
    public void setDestinationLocationId(java.lang.String destinationLocationId) {
        this.destinationLocationId = destinationLocationId;
    }


    /**
     * Gets the destinationLocationNumber value for this ShipmentOperationalDetail.
     * 
     * @return destinationLocationNumber
     */
    public java.lang.Integer getDestinationLocationNumber() {
        return destinationLocationNumber;
    }


    /**
     * Sets the destinationLocationNumber value for this ShipmentOperationalDetail.
     * 
     * @param destinationLocationNumber
     */
    public void setDestinationLocationNumber(java.lang.Integer destinationLocationNumber) {
        this.destinationLocationNumber = destinationLocationNumber;
    }


    /**
     * Gets the destinationServiceArea value for this ShipmentOperationalDetail.
     * 
     * @return destinationServiceArea
     */
    public java.lang.String getDestinationServiceArea() {
        return destinationServiceArea;
    }


    /**
     * Sets the destinationServiceArea value for this ShipmentOperationalDetail.
     * 
     * @param destinationServiceArea
     */
    public void setDestinationServiceArea(java.lang.String destinationServiceArea) {
        this.destinationServiceArea = destinationServiceArea;
    }


    /**
     * Gets the destinationLocationStateOrProvinceCode value for this ShipmentOperationalDetail.
     * 
     * @return destinationLocationStateOrProvinceCode   * This is the state of the destination location ID, and is not
     * necessarily the same as the postal state.
     */
    public java.lang.String getDestinationLocationStateOrProvinceCode() {
        return destinationLocationStateOrProvinceCode;
    }


    /**
     * Sets the destinationLocationStateOrProvinceCode value for this ShipmentOperationalDetail.
     * 
     * @param destinationLocationStateOrProvinceCode   * This is the state of the destination location ID, and is not
     * necessarily the same as the postal state.
     */
    public void setDestinationLocationStateOrProvinceCode(java.lang.String destinationLocationStateOrProvinceCode) {
        this.destinationLocationStateOrProvinceCode = destinationLocationStateOrProvinceCode;
    }


    /**
     * Gets the deliveryDate value for this ShipmentOperationalDetail.
     * 
     * @return deliveryDate   * Expected/estimated date of delivery.
     */
    public java.util.Date getDeliveryDate() {
        return deliveryDate;
    }


    /**
     * Sets the deliveryDate value for this ShipmentOperationalDetail.
     * 
     * @param deliveryDate   * Expected/estimated date of delivery.
     */
    public void setDeliveryDate(java.util.Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }


    /**
     * Gets the deliveryDay value for this ShipmentOperationalDetail.
     * 
     * @return deliveryDay   * Expected/estimated day of week of delivery.
     */
    public com.proquifa.net.negocio.envio.ship.stub.DayOfWeekType getDeliveryDay() {
        return deliveryDay;
    }


    /**
     * Sets the deliveryDay value for this ShipmentOperationalDetail.
     * 
     * @param deliveryDay   * Expected/estimated day of week of delivery.
     */
    public void setDeliveryDay(com.proquifa.net.negocio.envio.ship.stub.DayOfWeekType deliveryDay) {
        this.deliveryDay = deliveryDay;
    }


    /**
     * Gets the publishedDeliveryTime value for this ShipmentOperationalDetail.
     * 
     * @return publishedDeliveryTime   * Delivery time, as published in Service Guide.
     */
    public org.apache.axis.types.Time getPublishedDeliveryTime() {
        return publishedDeliveryTime;
    }


    /**
     * Sets the publishedDeliveryTime value for this ShipmentOperationalDetail.
     * 
     * @param publishedDeliveryTime   * Delivery time, as published in Service Guide.
     */
    public void setPublishedDeliveryTime(org.apache.axis.types.Time publishedDeliveryTime) {
        this.publishedDeliveryTime = publishedDeliveryTime;
    }


    /**
     * Gets the commitDate value for this ShipmentOperationalDetail.
     * 
     * @return commitDate   * Committed date of delivery.
     */
    public java.util.Date getCommitDate() {
        return commitDate;
    }


    /**
     * Sets the commitDate value for this ShipmentOperationalDetail.
     * 
     * @param commitDate   * Committed date of delivery.
     */
    public void setCommitDate(java.util.Date commitDate) {
        this.commitDate = commitDate;
    }


    /**
     * Gets the commitDay value for this ShipmentOperationalDetail.
     * 
     * @return commitDay   * Committed day of week of delivery.
     */
    public com.proquifa.net.negocio.envio.ship.stub.DayOfWeekType getCommitDay() {
        return commitDay;
    }


    /**
     * Sets the commitDay value for this ShipmentOperationalDetail.
     * 
     * @param commitDay   * Committed day of week of delivery.
     */
    public void setCommitDay(com.proquifa.net.negocio.envio.ship.stub.DayOfWeekType commitDay) {
        this.commitDay = commitDay;
    }


    /**
     * Gets the transitTime value for this ShipmentOperationalDetail.
     * 
     * @return transitTime   * Standard transit time per origin, destination, and service.
     */
    public com.proquifa.net.negocio.envio.ship.stub.TransitTimeType getTransitTime() {
        return transitTime;
    }


    /**
     * Sets the transitTime value for this ShipmentOperationalDetail.
     * 
     * @param transitTime   * Standard transit time per origin, destination, and service.
     */
    public void setTransitTime(com.proquifa.net.negocio.envio.ship.stub.TransitTimeType transitTime) {
        this.transitTime = transitTime;
    }


    /**
     * Gets the maximumTransitTime value for this ShipmentOperationalDetail.
     * 
     * @return maximumTransitTime   * Maximum expected transit time
     */
    public com.proquifa.net.negocio.envio.ship.stub.TransitTimeType getMaximumTransitTime() {
        return maximumTransitTime;
    }


    /**
     * Sets the maximumTransitTime value for this ShipmentOperationalDetail.
     * 
     * @param maximumTransitTime   * Maximum expected transit time
     */
    public void setMaximumTransitTime(com.proquifa.net.negocio.envio.ship.stub.TransitTimeType maximumTransitTime) {
        this.maximumTransitTime = maximumTransitTime;
    }


    /**
     * Gets the customTransitTime value for this ShipmentOperationalDetail.
     * 
     * @return customTransitTime   * Transit time based on customer eligibility.
     */
    public com.proquifa.net.negocio.envio.ship.stub.TransitTimeType getCustomTransitTime() {
        return customTransitTime;
    }


    /**
     * Sets the customTransitTime value for this ShipmentOperationalDetail.
     * 
     * @param customTransitTime   * Transit time based on customer eligibility.
     */
    public void setCustomTransitTime(com.proquifa.net.negocio.envio.ship.stub.TransitTimeType customTransitTime) {
        this.customTransitTime = customTransitTime;
    }


    /**
     * Gets the ineligibleForMoneyBackGuarantee value for this ShipmentOperationalDetail.
     * 
     * @return ineligibleForMoneyBackGuarantee   * Indicates that this shipment is not eligible for money back
     * guarantee.
     */
    public java.lang.Boolean getIneligibleForMoneyBackGuarantee() {
        return ineligibleForMoneyBackGuarantee;
    }


    /**
     * Sets the ineligibleForMoneyBackGuarantee value for this ShipmentOperationalDetail.
     * 
     * @param ineligibleForMoneyBackGuarantee   * Indicates that this shipment is not eligible for money back
     * guarantee.
     */
    public void setIneligibleForMoneyBackGuarantee(java.lang.Boolean ineligibleForMoneyBackGuarantee) {
        this.ineligibleForMoneyBackGuarantee = ineligibleForMoneyBackGuarantee;
    }


    /**
     * Gets the deliveryEligibilities value for this ShipmentOperationalDetail.
     * 
     * @return deliveryEligibilities   * FedEx Ground delivery features for which this shipment may
     * be eligible.
     */
    public com.proquifa.net.negocio.envio.ship.stub.GroundDeliveryEligibilityType[] getDeliveryEligibilities() {
        return deliveryEligibilities;
    }


    /**
     * Sets the deliveryEligibilities value for this ShipmentOperationalDetail.
     * 
     * @param deliveryEligibilities   * FedEx Ground delivery features for which this shipment may
     * be eligible.
     */
    public void setDeliveryEligibilities(com.proquifa.net.negocio.envio.ship.stub.GroundDeliveryEligibilityType[] deliveryEligibilities) {
        this.deliveryEligibilities = deliveryEligibilities;
    }

    public com.proquifa.net.negocio.envio.ship.stub.GroundDeliveryEligibilityType getDeliveryEligibilities(int i) {
        return this.deliveryEligibilities[i];
    }

    public void setDeliveryEligibilities(int i, com.proquifa.net.negocio.envio.ship.stub.GroundDeliveryEligibilityType _value) {
        this.deliveryEligibilities[i] = _value;
    }


    /**
     * Gets the astraPlannedServiceLevel value for this ShipmentOperationalDetail.
     * 
     * @return astraPlannedServiceLevel   * Text describing planned delivery.
     */
    public java.lang.String getAstraPlannedServiceLevel() {
        return astraPlannedServiceLevel;
    }


    /**
     * Sets the astraPlannedServiceLevel value for this ShipmentOperationalDetail.
     * 
     * @param astraPlannedServiceLevel   * Text describing planned delivery.
     */
    public void setAstraPlannedServiceLevel(java.lang.String astraPlannedServiceLevel) {
        this.astraPlannedServiceLevel = astraPlannedServiceLevel;
    }


    /**
     * Gets the astraDescription value for this ShipmentOperationalDetail.
     * 
     * @return astraDescription
     */
    public java.lang.String getAstraDescription() {
        return astraDescription;
    }


    /**
     * Sets the astraDescription value for this ShipmentOperationalDetail.
     * 
     * @param astraDescription
     */
    public void setAstraDescription(java.lang.String astraDescription) {
        this.astraDescription = astraDescription;
    }


    /**
     * Gets the postalCode value for this ShipmentOperationalDetail.
     * 
     * @return postalCode
     */
    public java.lang.String getPostalCode() {
        return postalCode;
    }


    /**
     * Sets the postalCode value for this ShipmentOperationalDetail.
     * 
     * @param postalCode
     */
    public void setPostalCode(java.lang.String postalCode) {
        this.postalCode = postalCode;
    }


    /**
     * Gets the stateOrProvinceCode value for this ShipmentOperationalDetail.
     * 
     * @return stateOrProvinceCode
     */
    public java.lang.String getStateOrProvinceCode() {
        return stateOrProvinceCode;
    }


    /**
     * Sets the stateOrProvinceCode value for this ShipmentOperationalDetail.
     * 
     * @param stateOrProvinceCode
     */
    public void setStateOrProvinceCode(java.lang.String stateOrProvinceCode) {
        this.stateOrProvinceCode = stateOrProvinceCode;
    }


    /**
     * Gets the countryCode value for this ShipmentOperationalDetail.
     * 
     * @return countryCode
     */
    public java.lang.String getCountryCode() {
        return countryCode;
    }


    /**
     * Sets the countryCode value for this ShipmentOperationalDetail.
     * 
     * @param countryCode
     */
    public void setCountryCode(java.lang.String countryCode) {
        this.countryCode = countryCode;
    }


    /**
     * Gets the airportId value for this ShipmentOperationalDetail.
     * 
     * @return airportId
     */
    public java.lang.String getAirportId() {
        return airportId;
    }


    /**
     * Sets the airportId value for this ShipmentOperationalDetail.
     * 
     * @param airportId
     */
    public void setAirportId(java.lang.String airportId) {
        this.airportId = airportId;
    }


    /**
     * Gets the serviceCode value for this ShipmentOperationalDetail.
     * 
     * @return serviceCode
     */
    public java.lang.String getServiceCode() {
        return serviceCode;
    }


    /**
     * Sets the serviceCode value for this ShipmentOperationalDetail.
     * 
     * @param serviceCode
     */
    public void setServiceCode(java.lang.String serviceCode) {
        this.serviceCode = serviceCode;
    }


    /**
     * Gets the packagingCode value for this ShipmentOperationalDetail.
     * 
     * @return packagingCode
     */
    public java.lang.String getPackagingCode() {
        return packagingCode;
    }


    /**
     * Sets the packagingCode value for this ShipmentOperationalDetail.
     * 
     * @param packagingCode
     */
    public void setPackagingCode(java.lang.String packagingCode) {
        this.packagingCode = packagingCode;
    }


    /**
     * Gets the scac value for this ShipmentOperationalDetail.
     * 
     * @return scac
     */
    public java.lang.String getScac() {
        return scac;
    }


    /**
     * Sets the scac value for this ShipmentOperationalDetail.
     * 
     * @param scac
     */
    public void setScac(java.lang.String scac) {
        this.scac = scac;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ShipmentOperationalDetail)) return false;
        ShipmentOperationalDetail other = (ShipmentOperationalDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ursaPrefixCode==null && other.getUrsaPrefixCode()==null) || 
             (this.ursaPrefixCode!=null &&
              this.ursaPrefixCode.equals(other.getUrsaPrefixCode()))) &&
            ((this.ursaSuffixCode==null && other.getUrsaSuffixCode()==null) || 
             (this.ursaSuffixCode!=null &&
              this.ursaSuffixCode.equals(other.getUrsaSuffixCode()))) &&
            ((this.originLocationId==null && other.getOriginLocationId()==null) || 
             (this.originLocationId!=null &&
              this.originLocationId.equals(other.getOriginLocationId()))) &&
            ((this.originLocationNumber==null && other.getOriginLocationNumber()==null) || 
             (this.originLocationNumber!=null &&
              this.originLocationNumber.equals(other.getOriginLocationNumber()))) &&
            ((this.originServiceArea==null && other.getOriginServiceArea()==null) || 
             (this.originServiceArea!=null &&
              this.originServiceArea.equals(other.getOriginServiceArea()))) &&
            ((this.destinationLocationId==null && other.getDestinationLocationId()==null) || 
             (this.destinationLocationId!=null &&
              this.destinationLocationId.equals(other.getDestinationLocationId()))) &&
            ((this.destinationLocationNumber==null && other.getDestinationLocationNumber()==null) || 
             (this.destinationLocationNumber!=null &&
              this.destinationLocationNumber.equals(other.getDestinationLocationNumber()))) &&
            ((this.destinationServiceArea==null && other.getDestinationServiceArea()==null) || 
             (this.destinationServiceArea!=null &&
              this.destinationServiceArea.equals(other.getDestinationServiceArea()))) &&
            ((this.destinationLocationStateOrProvinceCode==null && other.getDestinationLocationStateOrProvinceCode()==null) || 
             (this.destinationLocationStateOrProvinceCode!=null &&
              this.destinationLocationStateOrProvinceCode.equals(other.getDestinationLocationStateOrProvinceCode()))) &&
            ((this.deliveryDate==null && other.getDeliveryDate()==null) || 
             (this.deliveryDate!=null &&
              this.deliveryDate.equals(other.getDeliveryDate()))) &&
            ((this.deliveryDay==null && other.getDeliveryDay()==null) || 
             (this.deliveryDay!=null &&
              this.deliveryDay.equals(other.getDeliveryDay()))) &&
            ((this.publishedDeliveryTime==null && other.getPublishedDeliveryTime()==null) || 
             (this.publishedDeliveryTime!=null &&
              this.publishedDeliveryTime.equals(other.getPublishedDeliveryTime()))) &&
            ((this.commitDate==null && other.getCommitDate()==null) || 
             (this.commitDate!=null &&
              this.commitDate.equals(other.getCommitDate()))) &&
            ((this.commitDay==null && other.getCommitDay()==null) || 
             (this.commitDay!=null &&
              this.commitDay.equals(other.getCommitDay()))) &&
            ((this.transitTime==null && other.getTransitTime()==null) || 
             (this.transitTime!=null &&
              this.transitTime.equals(other.getTransitTime()))) &&
            ((this.maximumTransitTime==null && other.getMaximumTransitTime()==null) || 
             (this.maximumTransitTime!=null &&
              this.maximumTransitTime.equals(other.getMaximumTransitTime()))) &&
            ((this.customTransitTime==null && other.getCustomTransitTime()==null) || 
             (this.customTransitTime!=null &&
              this.customTransitTime.equals(other.getCustomTransitTime()))) &&
            ((this.ineligibleForMoneyBackGuarantee==null && other.getIneligibleForMoneyBackGuarantee()==null) || 
             (this.ineligibleForMoneyBackGuarantee!=null &&
              this.ineligibleForMoneyBackGuarantee.equals(other.getIneligibleForMoneyBackGuarantee()))) &&
            ((this.deliveryEligibilities==null && other.getDeliveryEligibilities()==null) || 
             (this.deliveryEligibilities!=null &&
              java.util.Arrays.equals(this.deliveryEligibilities, other.getDeliveryEligibilities()))) &&
            ((this.astraPlannedServiceLevel==null && other.getAstraPlannedServiceLevel()==null) || 
             (this.astraPlannedServiceLevel!=null &&
              this.astraPlannedServiceLevel.equals(other.getAstraPlannedServiceLevel()))) &&
            ((this.astraDescription==null && other.getAstraDescription()==null) || 
             (this.astraDescription!=null &&
              this.astraDescription.equals(other.getAstraDescription()))) &&
            ((this.postalCode==null && other.getPostalCode()==null) || 
             (this.postalCode!=null &&
              this.postalCode.equals(other.getPostalCode()))) &&
            ((this.stateOrProvinceCode==null && other.getStateOrProvinceCode()==null) || 
             (this.stateOrProvinceCode!=null &&
              this.stateOrProvinceCode.equals(other.getStateOrProvinceCode()))) &&
            ((this.countryCode==null && other.getCountryCode()==null) || 
             (this.countryCode!=null &&
              this.countryCode.equals(other.getCountryCode()))) &&
            ((this.airportId==null && other.getAirportId()==null) || 
             (this.airportId!=null &&
              this.airportId.equals(other.getAirportId()))) &&
            ((this.serviceCode==null && other.getServiceCode()==null) || 
             (this.serviceCode!=null &&
              this.serviceCode.equals(other.getServiceCode()))) &&
            ((this.packagingCode==null && other.getPackagingCode()==null) || 
             (this.packagingCode!=null &&
              this.packagingCode.equals(other.getPackagingCode()))) &&
            ((this.scac==null && other.getScac()==null) || 
             (this.scac!=null &&
              this.scac.equals(other.getScac())));
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
        if (getUrsaPrefixCode() != null) {
            _hashCode += getUrsaPrefixCode().hashCode();
        }
        if (getUrsaSuffixCode() != null) {
            _hashCode += getUrsaSuffixCode().hashCode();
        }
        if (getOriginLocationId() != null) {
            _hashCode += getOriginLocationId().hashCode();
        }
        if (getOriginLocationNumber() != null) {
            _hashCode += getOriginLocationNumber().hashCode();
        }
        if (getOriginServiceArea() != null) {
            _hashCode += getOriginServiceArea().hashCode();
        }
        if (getDestinationLocationId() != null) {
            _hashCode += getDestinationLocationId().hashCode();
        }
        if (getDestinationLocationNumber() != null) {
            _hashCode += getDestinationLocationNumber().hashCode();
        }
        if (getDestinationServiceArea() != null) {
            _hashCode += getDestinationServiceArea().hashCode();
        }
        if (getDestinationLocationStateOrProvinceCode() != null) {
            _hashCode += getDestinationLocationStateOrProvinceCode().hashCode();
        }
        if (getDeliveryDate() != null) {
            _hashCode += getDeliveryDate().hashCode();
        }
        if (getDeliveryDay() != null) {
            _hashCode += getDeliveryDay().hashCode();
        }
        if (getPublishedDeliveryTime() != null) {
            _hashCode += getPublishedDeliveryTime().hashCode();
        }
        if (getCommitDate() != null) {
            _hashCode += getCommitDate().hashCode();
        }
        if (getCommitDay() != null) {
            _hashCode += getCommitDay().hashCode();
        }
        if (getTransitTime() != null) {
            _hashCode += getTransitTime().hashCode();
        }
        if (getMaximumTransitTime() != null) {
            _hashCode += getMaximumTransitTime().hashCode();
        }
        if (getCustomTransitTime() != null) {
            _hashCode += getCustomTransitTime().hashCode();
        }
        if (getIneligibleForMoneyBackGuarantee() != null) {
            _hashCode += getIneligibleForMoneyBackGuarantee().hashCode();
        }
        if (getDeliveryEligibilities() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDeliveryEligibilities());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDeliveryEligibilities(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getAstraPlannedServiceLevel() != null) {
            _hashCode += getAstraPlannedServiceLevel().hashCode();
        }
        if (getAstraDescription() != null) {
            _hashCode += getAstraDescription().hashCode();
        }
        if (getPostalCode() != null) {
            _hashCode += getPostalCode().hashCode();
        }
        if (getStateOrProvinceCode() != null) {
            _hashCode += getStateOrProvinceCode().hashCode();
        }
        if (getCountryCode() != null) {
            _hashCode += getCountryCode().hashCode();
        }
        if (getAirportId() != null) {
            _hashCode += getAirportId().hashCode();
        }
        if (getServiceCode() != null) {
            _hashCode += getServiceCode().hashCode();
        }
        if (getPackagingCode() != null) {
            _hashCode += getPackagingCode().hashCode();
        }
        if (getScac() != null) {
            _hashCode += getScac().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ShipmentOperationalDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShipmentOperationalDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ursaPrefixCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "UrsaPrefixCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ursaSuffixCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "UrsaSuffixCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("originLocationId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "OriginLocationId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("originLocationNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "OriginLocationNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("originServiceArea");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "OriginServiceArea"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destinationLocationId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DestinationLocationId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destinationLocationNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DestinationLocationNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destinationServiceArea");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DestinationServiceArea"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destinationLocationStateOrProvinceCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DestinationLocationStateOrProvinceCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deliveryDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DeliveryDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deliveryDay");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DeliveryDay"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DayOfWeekType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("publishedDeliveryTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PublishedDeliveryTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "time"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("commitDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CommitDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("commitDay");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CommitDay"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DayOfWeekType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transitTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TransitTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TransitTimeType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maximumTransitTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "MaximumTransitTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TransitTimeType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customTransitTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CustomTransitTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TransitTimeType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ineligibleForMoneyBackGuarantee");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "IneligibleForMoneyBackGuarantee"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deliveryEligibilities");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DeliveryEligibilities"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "GroundDeliveryEligibilityType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("astraPlannedServiceLevel");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "AstraPlannedServiceLevel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("astraDescription");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "AstraDescription"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("postalCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PostalCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stateOrProvinceCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "StateOrProvinceCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("countryCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CountryCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("airportId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "AirportId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ServiceCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packagingCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PackagingCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scac");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Scac"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
