/**
 * PackageOperationalDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.proquifa.net.negocio.envio.ship.stub;


/**
 * Package-level data required for labeling and/or movement.
 */
public class PackageOperationalDetail  implements java.io.Serializable {
    /* Human-readable text for pre-January 2011 clients. */
    private java.lang.String astraHandlingText;

    /* Human-readable content for use on a label. */
    private com.proquifa.net.negocio.envio.ship.stub.OperationalInstruction[] operationalInstructions;

    /* The operational barcodes pertaining to the current package. */
    private com.proquifa.net.negocio.envio.ship.stub.PackageBarcodes barcodes;

    /* The FedEx internal code that represents the service and/or
     * features of service for the current package moving under a FedEx Ground
     * service. */
    private java.lang.String groundServiceCode;

    public PackageOperationalDetail() {
    }

    public PackageOperationalDetail(
           java.lang.String astraHandlingText,
           com.proquifa.net.negocio.envio.ship.stub.OperationalInstruction[] operationalInstructions,
           com.proquifa.net.negocio.envio.ship.stub.PackageBarcodes barcodes,
           java.lang.String groundServiceCode) {
           this.astraHandlingText = astraHandlingText;
           this.operationalInstructions = operationalInstructions;
           this.barcodes = barcodes;
           this.groundServiceCode = groundServiceCode;
    }


    /**
     * Gets the astraHandlingText value for this PackageOperationalDetail.
     * 
     * @return astraHandlingText   * Human-readable text for pre-January 2011 clients.
     */
    public java.lang.String getAstraHandlingText() {
        return astraHandlingText;
    }


    /**
     * Sets the astraHandlingText value for this PackageOperationalDetail.
     * 
     * @param astraHandlingText   * Human-readable text for pre-January 2011 clients.
     */
    public void setAstraHandlingText(java.lang.String astraHandlingText) {
        this.astraHandlingText = astraHandlingText;
    }


    /**
     * Gets the operationalInstructions value for this PackageOperationalDetail.
     * 
     * @return operationalInstructions   * Human-readable content for use on a label.
     */
    public com.proquifa.net.negocio.envio.ship.stub.OperationalInstruction[] getOperationalInstructions() {
        return operationalInstructions;
    }


    /**
     * Sets the operationalInstructions value for this PackageOperationalDetail.
     * 
     * @param operationalInstructions   * Human-readable content for use on a label.
     */
    public void setOperationalInstructions(com.proquifa.net.negocio.envio.ship.stub.OperationalInstruction[] operationalInstructions) {
        this.operationalInstructions = operationalInstructions;
    }

    public com.proquifa.net.negocio.envio.ship.stub.OperationalInstruction getOperationalInstructions(int i) {
        return this.operationalInstructions[i];
    }

    public void setOperationalInstructions(int i, com.proquifa.net.negocio.envio.ship.stub.OperationalInstruction _value) {
        this.operationalInstructions[i] = _value;
    }


    /**
     * Gets the barcodes value for this PackageOperationalDetail.
     * 
     * @return barcodes   * The operational barcodes pertaining to the current package.
     */
    public com.proquifa.net.negocio.envio.ship.stub.PackageBarcodes getBarcodes() {
        return barcodes;
    }


    /**
     * Sets the barcodes value for this PackageOperationalDetail.
     * 
     * @param barcodes   * The operational barcodes pertaining to the current package.
     */
    public void setBarcodes(com.proquifa.net.negocio.envio.ship.stub.PackageBarcodes barcodes) {
        this.barcodes = barcodes;
    }


    /**
     * Gets the groundServiceCode value for this PackageOperationalDetail.
     * 
     * @return groundServiceCode   * The FedEx internal code that represents the service and/or
     * features of service for the current package moving under a FedEx Ground
     * service.
     */
    public java.lang.String getGroundServiceCode() {
        return groundServiceCode;
    }


    /**
     * Sets the groundServiceCode value for this PackageOperationalDetail.
     * 
     * @param groundServiceCode   * The FedEx internal code that represents the service and/or
     * features of service for the current package moving under a FedEx Ground
     * service.
     */
    public void setGroundServiceCode(java.lang.String groundServiceCode) {
        this.groundServiceCode = groundServiceCode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PackageOperationalDetail)) return false;
        PackageOperationalDetail other = (PackageOperationalDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.astraHandlingText==null && other.getAstraHandlingText()==null) || 
             (this.astraHandlingText!=null &&
              this.astraHandlingText.equals(other.getAstraHandlingText()))) &&
            ((this.operationalInstructions==null && other.getOperationalInstructions()==null) || 
             (this.operationalInstructions!=null &&
              java.util.Arrays.equals(this.operationalInstructions, other.getOperationalInstructions()))) &&
            ((this.barcodes==null && other.getBarcodes()==null) || 
             (this.barcodes!=null &&
              this.barcodes.equals(other.getBarcodes()))) &&
            ((this.groundServiceCode==null && other.getGroundServiceCode()==null) || 
             (this.groundServiceCode!=null &&
              this.groundServiceCode.equals(other.getGroundServiceCode())));
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
        if (getAstraHandlingText() != null) {
            _hashCode += getAstraHandlingText().hashCode();
        }
        if (getOperationalInstructions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getOperationalInstructions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getOperationalInstructions(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getBarcodes() != null) {
            _hashCode += getBarcodes().hashCode();
        }
        if (getGroundServiceCode() != null) {
            _hashCode += getGroundServiceCode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PackageOperationalDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PackageOperationalDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("astraHandlingText");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "AstraHandlingText"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operationalInstructions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "OperationalInstructions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "OperationalInstruction"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("barcodes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Barcodes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PackageBarcodes"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("groundServiceCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "GroundServiceCode"));
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
