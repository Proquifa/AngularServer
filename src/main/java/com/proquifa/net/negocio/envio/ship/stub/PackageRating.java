/**
 * PackageRating.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.proquifa.net.negocio.envio.ship.stub;


/**
 * This class groups together for a single package all package-level
 * rate data (across all rate types) as part of the response to a shipping
 * request, which groups shipment-level data together and groups package-level
 * data by package.
 */
public class PackageRating  implements java.io.Serializable {
    /* This rate type identifies which entry in the following array
     * is considered as presenting the "actual" rates for the package. */
    private com.proquifa.net.negocio.envio.ship.stub.ReturnedRateType actualRateType;

    /* The "list" net charge minus "actual" net charge. */
    private com.proquifa.net.negocio.envio.ship.stub.Money effectiveNetDiscount;

    /* Each element of this field provides package-level rate data
     * for a specific rate type. */
    private com.proquifa.net.negocio.envio.ship.stub.PackageRateDetail[] packageRateDetails;

    public PackageRating() {
    }

    public PackageRating(
           com.proquifa.net.negocio.envio.ship.stub.ReturnedRateType actualRateType,
           com.proquifa.net.negocio.envio.ship.stub.Money effectiveNetDiscount,
           com.proquifa.net.negocio.envio.ship.stub.PackageRateDetail[] packageRateDetails) {
           this.actualRateType = actualRateType;
           this.effectiveNetDiscount = effectiveNetDiscount;
           this.packageRateDetails = packageRateDetails;
    }


    /**
     * Gets the actualRateType value for this PackageRating.
     * 
     * @return actualRateType   * This rate type identifies which entry in the following array
     * is considered as presenting the "actual" rates for the package.
     */
    public com.proquifa.net.negocio.envio.ship.stub.ReturnedRateType getActualRateType() {
        return actualRateType;
    }


    /**
     * Sets the actualRateType value for this PackageRating.
     * 
     * @param actualRateType   * This rate type identifies which entry in the following array
     * is considered as presenting the "actual" rates for the package.
     */
    public void setActualRateType(com.proquifa.net.negocio.envio.ship.stub.ReturnedRateType actualRateType) {
        this.actualRateType = actualRateType;
    }


    /**
     * Gets the effectiveNetDiscount value for this PackageRating.
     * 
     * @return effectiveNetDiscount   * The "list" net charge minus "actual" net charge.
     */
    public com.proquifa.net.negocio.envio.ship.stub.Money getEffectiveNetDiscount() {
        return effectiveNetDiscount;
    }


    /**
     * Sets the effectiveNetDiscount value for this PackageRating.
     * 
     * @param effectiveNetDiscount   * The "list" net charge minus "actual" net charge.
     */
    public void setEffectiveNetDiscount(com.proquifa.net.negocio.envio.ship.stub.Money effectiveNetDiscount) {
        this.effectiveNetDiscount = effectiveNetDiscount;
    }


    /**
     * Gets the packageRateDetails value for this PackageRating.
     * 
     * @return packageRateDetails   * Each element of this field provides package-level rate data
     * for a specific rate type.
     */
    public com.proquifa.net.negocio.envio.ship.stub.PackageRateDetail[] getPackageRateDetails() {
        return packageRateDetails;
    }


    /**
     * Sets the packageRateDetails value for this PackageRating.
     * 
     * @param packageRateDetails   * Each element of this field provides package-level rate data
     * for a specific rate type.
     */
    public void setPackageRateDetails(com.proquifa.net.negocio.envio.ship.stub.PackageRateDetail[] packageRateDetails) {
        this.packageRateDetails = packageRateDetails;
    }

    public com.proquifa.net.negocio.envio.ship.stub.PackageRateDetail getPackageRateDetails(int i) {
        return this.packageRateDetails[i];
    }

    public void setPackageRateDetails(int i, com.proquifa.net.negocio.envio.ship.stub.PackageRateDetail _value) {
        this.packageRateDetails[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PackageRating)) return false;
        PackageRating other = (PackageRating) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.actualRateType==null && other.getActualRateType()==null) || 
             (this.actualRateType!=null &&
              this.actualRateType.equals(other.getActualRateType()))) &&
            ((this.effectiveNetDiscount==null && other.getEffectiveNetDiscount()==null) || 
             (this.effectiveNetDiscount!=null &&
              this.effectiveNetDiscount.equals(other.getEffectiveNetDiscount()))) &&
            ((this.packageRateDetails==null && other.getPackageRateDetails()==null) || 
             (this.packageRateDetails!=null &&
              java.util.Arrays.equals(this.packageRateDetails, other.getPackageRateDetails())));
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
        if (getActualRateType() != null) {
            _hashCode += getActualRateType().hashCode();
        }
        if (getEffectiveNetDiscount() != null) {
            _hashCode += getEffectiveNetDiscount().hashCode();
        }
        if (getPackageRateDetails() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPackageRateDetails());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPackageRateDetails(), i);
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
        new org.apache.axis.description.TypeDesc(PackageRating.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PackageRating"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("actualRateType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ActualRateType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ReturnedRateType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("effectiveNetDiscount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "EffectiveNetDiscount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Money"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packageRateDetails");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PackageRateDetails"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PackageRateDetail"));
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
