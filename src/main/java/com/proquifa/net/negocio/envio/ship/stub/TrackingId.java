/**
 * TrackingId.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.proquifa.net.negocio.envio.ship.stub;

public class TrackingId  implements java.io.Serializable {
    private com.proquifa.net.negocio.envio.ship.stub.TrackingIdType trackingIdType;

    private java.lang.String formId;

    /* For use with SmartPost tracking IDs only */
    private java.lang.String uspsApplicationId;

    private java.lang.String trackingNumber;

    public TrackingId() {
    }

    public TrackingId(
           com.proquifa.net.negocio.envio.ship.stub.TrackingIdType trackingIdType,
           java.lang.String formId,
           java.lang.String uspsApplicationId,
           java.lang.String trackingNumber) {
           this.trackingIdType = trackingIdType;
           this.formId = formId;
           this.uspsApplicationId = uspsApplicationId;
           this.trackingNumber = trackingNumber;
    }


    /**
     * Gets the trackingIdType value for this TrackingId.
     * 
     * @return trackingIdType
     */
    public com.proquifa.net.negocio.envio.ship.stub.TrackingIdType getTrackingIdType() {
        return trackingIdType;
    }


    /**
     * Sets the trackingIdType value for this TrackingId.
     * 
     * @param trackingIdType
     */
    public void setTrackingIdType(com.proquifa.net.negocio.envio.ship.stub.TrackingIdType trackingIdType) {
        this.trackingIdType = trackingIdType;
    }


    /**
     * Gets the formId value for this TrackingId.
     * 
     * @return formId
     */
    public java.lang.String getFormId() {
        return formId;
    }


    /**
     * Sets the formId value for this TrackingId.
     * 
     * @param formId
     */
    public void setFormId(java.lang.String formId) {
        this.formId = formId;
    }


    /**
     * Gets the uspsApplicationId value for this TrackingId.
     * 
     * @return uspsApplicationId   * For use with SmartPost tracking IDs only
     */
    public java.lang.String getUspsApplicationId() {
        return uspsApplicationId;
    }


    /**
     * Sets the uspsApplicationId value for this TrackingId.
     * 
     * @param uspsApplicationId   * For use with SmartPost tracking IDs only
     */
    public void setUspsApplicationId(java.lang.String uspsApplicationId) {
        this.uspsApplicationId = uspsApplicationId;
    }


    /**
     * Gets the trackingNumber value for this TrackingId.
     * 
     * @return trackingNumber
     */
    public java.lang.String getTrackingNumber() {
        return trackingNumber;
    }


    /**
     * Sets the trackingNumber value for this TrackingId.
     * 
     * @param trackingNumber
     */
    public void setTrackingNumber(java.lang.String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TrackingId)) return false;
        TrackingId other = (TrackingId) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.trackingIdType==null && other.getTrackingIdType()==null) || 
             (this.trackingIdType!=null &&
              this.trackingIdType.equals(other.getTrackingIdType()))) &&
            ((this.formId==null && other.getFormId()==null) || 
             (this.formId!=null &&
              this.formId.equals(other.getFormId()))) &&
            ((this.uspsApplicationId==null && other.getUspsApplicationId()==null) || 
             (this.uspsApplicationId!=null &&
              this.uspsApplicationId.equals(other.getUspsApplicationId()))) &&
            ((this.trackingNumber==null && other.getTrackingNumber()==null) || 
             (this.trackingNumber!=null &&
              this.trackingNumber.equals(other.getTrackingNumber())));
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
        if (getTrackingIdType() != null) {
            _hashCode += getTrackingIdType().hashCode();
        }
        if (getFormId() != null) {
            _hashCode += getFormId().hashCode();
        }
        if (getUspsApplicationId() != null) {
            _hashCode += getUspsApplicationId().hashCode();
        }
        if (getTrackingNumber() != null) {
            _hashCode += getTrackingNumber().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TrackingId.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TrackingId"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("trackingIdType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TrackingIdType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TrackingIdType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("formId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FormId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("uspsApplicationId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "UspsApplicationId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("trackingNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TrackingNumber"));
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
