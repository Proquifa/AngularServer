/**
 * HoldAtLocationDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.proquifa.net.negocio.envio.ship.stub;

public class HoldAtLocationDetail  implements java.io.Serializable {
    /* Contact phone number for recipient of shipment. */
    private java.lang.String phoneNumber;

    /* Contact and address of FedEx facility at which shipment is
     * to be held. */
    private com.proquifa.net.negocio.envio.ship.stub.ContactAndAddress locationContactAndAddress;

    /* Type of facility at which package/shipment is to be held. */
    private com.proquifa.net.negocio.envio.ship.stub.FedExLocationType locationType;

    /* Location identification (for facilities identified by an alphanumeric
     * location code). */
    private java.lang.String locationId;

    public HoldAtLocationDetail() {
    }

    public HoldAtLocationDetail(
           java.lang.String phoneNumber,
           com.proquifa.net.negocio.envio.ship.stub.ContactAndAddress locationContactAndAddress,
           com.proquifa.net.negocio.envio.ship.stub.FedExLocationType locationType,
           java.lang.String locationId) {
           this.phoneNumber = phoneNumber;
           this.locationContactAndAddress = locationContactAndAddress;
           this.locationType = locationType;
           this.locationId = locationId;
    }


    /**
     * Gets the phoneNumber value for this HoldAtLocationDetail.
     * 
     * @return phoneNumber   * Contact phone number for recipient of shipment.
     */
    public java.lang.String getPhoneNumber() {
        return phoneNumber;
    }


    /**
     * Sets the phoneNumber value for this HoldAtLocationDetail.
     * 
     * @param phoneNumber   * Contact phone number for recipient of shipment.
     */
    public void setPhoneNumber(java.lang.String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    /**
     * Gets the locationContactAndAddress value for this HoldAtLocationDetail.
     * 
     * @return locationContactAndAddress   * Contact and address of FedEx facility at which shipment is
     * to be held.
     */
    public com.proquifa.net.negocio.envio.ship.stub.ContactAndAddress getLocationContactAndAddress() {
        return locationContactAndAddress;
    }


    /**
     * Sets the locationContactAndAddress value for this HoldAtLocationDetail.
     * 
     * @param locationContactAndAddress   * Contact and address of FedEx facility at which shipment is
     * to be held.
     */
    public void setLocationContactAndAddress(com.proquifa.net.negocio.envio.ship.stub.ContactAndAddress locationContactAndAddress) {
        this.locationContactAndAddress = locationContactAndAddress;
    }


    /**
     * Gets the locationType value for this HoldAtLocationDetail.
     * 
     * @return locationType   * Type of facility at which package/shipment is to be held.
     */
    public com.proquifa.net.negocio.envio.ship.stub.FedExLocationType getLocationType() {
        return locationType;
    }


    /**
     * Sets the locationType value for this HoldAtLocationDetail.
     * 
     * @param locationType   * Type of facility at which package/shipment is to be held.
     */
    public void setLocationType(com.proquifa.net.negocio.envio.ship.stub.FedExLocationType locationType) {
        this.locationType = locationType;
    }


    /**
     * Gets the locationId value for this HoldAtLocationDetail.
     * 
     * @return locationId   * Location identification (for facilities identified by an alphanumeric
     * location code).
     */
    public java.lang.String getLocationId() {
        return locationId;
    }


    /**
     * Sets the locationId value for this HoldAtLocationDetail.
     * 
     * @param locationId   * Location identification (for facilities identified by an alphanumeric
     * location code).
     */
    public void setLocationId(java.lang.String locationId) {
        this.locationId = locationId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof HoldAtLocationDetail)) return false;
        HoldAtLocationDetail other = (HoldAtLocationDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.phoneNumber==null && other.getPhoneNumber()==null) || 
             (this.phoneNumber!=null &&
              this.phoneNumber.equals(other.getPhoneNumber()))) &&
            ((this.locationContactAndAddress==null && other.getLocationContactAndAddress()==null) || 
             (this.locationContactAndAddress!=null &&
              this.locationContactAndAddress.equals(other.getLocationContactAndAddress()))) &&
            ((this.locationType==null && other.getLocationType()==null) || 
             (this.locationType!=null &&
              this.locationType.equals(other.getLocationType()))) &&
            ((this.locationId==null && other.getLocationId()==null) || 
             (this.locationId!=null &&
              this.locationId.equals(other.getLocationId())));
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
        if (getPhoneNumber() != null) {
            _hashCode += getPhoneNumber().hashCode();
        }
        if (getLocationContactAndAddress() != null) {
            _hashCode += getLocationContactAndAddress().hashCode();
        }
        if (getLocationType() != null) {
            _hashCode += getLocationType().hashCode();
        }
        if (getLocationId() != null) {
            _hashCode += getLocationId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(HoldAtLocationDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "HoldAtLocationDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phoneNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PhoneNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("locationContactAndAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "LocationContactAndAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ContactAndAddress"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("locationType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "LocationType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FedExLocationType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("locationId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "LocationId"));
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
