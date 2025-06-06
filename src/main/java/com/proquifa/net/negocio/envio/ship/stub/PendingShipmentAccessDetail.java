/**
 * PendingShipmentAccessDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.proquifa.net.negocio.envio.ship.stub;


/**
 * This information describes how and when a pending shipment may
 * be accessed for completion.
 */
public class PendingShipmentAccessDetail  implements java.io.Serializable {
    private com.proquifa.net.negocio.envio.ship.stub.PendingShipmentAccessorDetail[] accessorDetails;

    public PendingShipmentAccessDetail() {
    }

    public PendingShipmentAccessDetail(
           com.proquifa.net.negocio.envio.ship.stub.PendingShipmentAccessorDetail[] accessorDetails) {
           this.accessorDetails = accessorDetails;
    }


    /**
     * Gets the accessorDetails value for this PendingShipmentAccessDetail.
     * 
     * @return accessorDetails
     */
    public com.proquifa.net.negocio.envio.ship.stub.PendingShipmentAccessorDetail[] getAccessorDetails() {
        return accessorDetails;
    }


    /**
     * Sets the accessorDetails value for this PendingShipmentAccessDetail.
     * 
     * @param accessorDetails
     */
    public void setAccessorDetails(com.proquifa.net.negocio.envio.ship.stub.PendingShipmentAccessorDetail[] accessorDetails) {
        this.accessorDetails = accessorDetails;
    }

    public com.proquifa.net.negocio.envio.ship.stub.PendingShipmentAccessorDetail getAccessorDetails(int i) {
        return this.accessorDetails[i];
    }

    public void setAccessorDetails(int i, com.proquifa.net.negocio.envio.ship.stub.PendingShipmentAccessorDetail _value) {
        this.accessorDetails[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PendingShipmentAccessDetail)) return false;
        PendingShipmentAccessDetail other = (PendingShipmentAccessDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.accessorDetails==null && other.getAccessorDetails()==null) || 
             (this.accessorDetails!=null &&
              java.util.Arrays.equals(this.accessorDetails, other.getAccessorDetails())));
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
        if (getAccessorDetails() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAccessorDetails());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAccessorDetails(), i);
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
        new org.apache.axis.description.TypeDesc(PendingShipmentAccessDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PendingShipmentAccessDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accessorDetails");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "AccessorDetails"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PendingShipmentAccessorDetail"));
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
