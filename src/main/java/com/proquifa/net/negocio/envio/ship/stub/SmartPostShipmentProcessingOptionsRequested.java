/**
 * SmartPostShipmentProcessingOptionsRequested.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.proquifa.net.negocio.envio.ship.stub;

public class SmartPostShipmentProcessingOptionsRequested  implements java.io.Serializable {
    private com.proquifa.net.negocio.envio.ship.stub.SmartPostShipmentProcessingOptionType[] options;

    public SmartPostShipmentProcessingOptionsRequested() {
    }

    public SmartPostShipmentProcessingOptionsRequested(
           com.proquifa.net.negocio.envio.ship.stub.SmartPostShipmentProcessingOptionType[] options) {
           this.options = options;
    }


    /**
     * Gets the options value for this SmartPostShipmentProcessingOptionsRequested.
     * 
     * @return options
     */
    public com.proquifa.net.negocio.envio.ship.stub.SmartPostShipmentProcessingOptionType[] getOptions() {
        return options;
    }


    /**
     * Sets the options value for this SmartPostShipmentProcessingOptionsRequested.
     * 
     * @param options
     */
    public void setOptions(com.proquifa.net.negocio.envio.ship.stub.SmartPostShipmentProcessingOptionType[] options) {
        this.options = options;
    }

    public com.proquifa.net.negocio.envio.ship.stub.SmartPostShipmentProcessingOptionType getOptions(int i) {
        return this.options[i];
    }

    public void setOptions(int i, com.proquifa.net.negocio.envio.ship.stub.SmartPostShipmentProcessingOptionType _value) {
        this.options[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SmartPostShipmentProcessingOptionsRequested)) return false;
        SmartPostShipmentProcessingOptionsRequested other = (SmartPostShipmentProcessingOptionsRequested) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.options==null && other.getOptions()==null) || 
             (this.options!=null &&
              java.util.Arrays.equals(this.options, other.getOptions())));
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
        if (getOptions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getOptions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getOptions(), i);
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
        new org.apache.axis.description.TypeDesc(SmartPostShipmentProcessingOptionsRequested.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "SmartPostShipmentProcessingOptionsRequested"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("options");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Options"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "SmartPostShipmentProcessingOptionType"));
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
