/**
 * ShipmentSpecialServicesRequested.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.proquifa.net.negocio.envio.ship.stub;


/**
 * These special services are available at the shipment level for
 * some or all service types. If the shipper is requesting a special
 * service which requires additional data (such as the COD amount), the
 * shipment special service type must be present in the specialServiceTypes
 * collection, and the supporting detail must be provided in the appropriate
 * sub-object below.
 */
public class ShipmentSpecialServicesRequested  implements java.io.Serializable {
    /* The types of all special services requested for the enclosing
     * shipment (or other shipment-level transaction). */
    private com.proquifa.net.negocio.envio.ship.stub.ShipmentSpecialServiceType[] specialServiceTypes;

    private com.proquifa.net.negocio.envio.ship.stub.CodDetail codDetail;

    private com.proquifa.net.negocio.envio.ship.stub.DeliveryOnInvoiceAcceptanceDetail deliveryOnInvoiceAcceptanceDetail;

    private com.proquifa.net.negocio.envio.ship.stub.HoldAtLocationDetail holdAtLocationDetail;

    /* This replaces eMailNotificationDetail */
    private com.proquifa.net.negocio.envio.ship.stub.ShipmentEventNotificationDetail eventNotificationDetail;

    private com.proquifa.net.negocio.envio.ship.stub.ReturnShipmentDetail returnShipmentDetail;

    /* This field should be populated for pending shipments (e.g.
     * e-mail label) It is required by a PENDING_SHIPMENT special service
     * type. */
    private com.proquifa.net.negocio.envio.ship.stub.PendingShipmentDetail pendingShipmentDetail;

    private com.proquifa.net.negocio.envio.ship.stub.InternationalControlledExportDetail internationalControlledExportDetail;

    private com.proquifa.net.negocio.envio.ship.stub.InternationalTrafficInArmsRegulationsDetail internationalTrafficInArmsRegulationsDetail;

    private com.proquifa.net.negocio.envio.ship.stub.ShipmentDryIceDetail shipmentDryIceDetail;

    private com.proquifa.net.negocio.envio.ship.stub.HomeDeliveryPremiumDetail homeDeliveryPremiumDetail;

    private com.proquifa.net.negocio.envio.ship.stub.FreightGuaranteeDetail freightGuaranteeDetail;

    /* Electronic Trade document references. */
    private com.proquifa.net.negocio.envio.ship.stub.EtdDetail etdDetail;

    /* Specification for date or range of dates on which delivery
     * is to be attempted. */
    private com.proquifa.net.negocio.envio.ship.stub.CustomDeliveryWindowDetail customDeliveryWindowDetail;

    public ShipmentSpecialServicesRequested() {
    }

    public ShipmentSpecialServicesRequested(
           com.proquifa.net.negocio.envio.ship.stub.ShipmentSpecialServiceType[] specialServiceTypes,
           com.proquifa.net.negocio.envio.ship.stub.CodDetail codDetail,
           com.proquifa.net.negocio.envio.ship.stub.DeliveryOnInvoiceAcceptanceDetail deliveryOnInvoiceAcceptanceDetail,
           com.proquifa.net.negocio.envio.ship.stub.HoldAtLocationDetail holdAtLocationDetail,
           com.proquifa.net.negocio.envio.ship.stub.ShipmentEventNotificationDetail eventNotificationDetail,
           com.proquifa.net.negocio.envio.ship.stub.ReturnShipmentDetail returnShipmentDetail,
           com.proquifa.net.negocio.envio.ship.stub.PendingShipmentDetail pendingShipmentDetail,
           com.proquifa.net.negocio.envio.ship.stub.InternationalControlledExportDetail internationalControlledExportDetail,
           com.proquifa.net.negocio.envio.ship.stub.InternationalTrafficInArmsRegulationsDetail internationalTrafficInArmsRegulationsDetail,
           com.proquifa.net.negocio.envio.ship.stub.ShipmentDryIceDetail shipmentDryIceDetail,
           com.proquifa.net.negocio.envio.ship.stub.HomeDeliveryPremiumDetail homeDeliveryPremiumDetail,
           com.proquifa.net.negocio.envio.ship.stub.FreightGuaranteeDetail freightGuaranteeDetail,
           com.proquifa.net.negocio.envio.ship.stub.EtdDetail etdDetail,
           com.proquifa.net.negocio.envio.ship.stub.CustomDeliveryWindowDetail customDeliveryWindowDetail) {
           this.specialServiceTypes = specialServiceTypes;
           this.codDetail = codDetail;
           this.deliveryOnInvoiceAcceptanceDetail = deliveryOnInvoiceAcceptanceDetail;
           this.holdAtLocationDetail = holdAtLocationDetail;
           this.eventNotificationDetail = eventNotificationDetail;
           this.returnShipmentDetail = returnShipmentDetail;
           this.pendingShipmentDetail = pendingShipmentDetail;
           this.internationalControlledExportDetail = internationalControlledExportDetail;
           this.internationalTrafficInArmsRegulationsDetail = internationalTrafficInArmsRegulationsDetail;
           this.shipmentDryIceDetail = shipmentDryIceDetail;
           this.homeDeliveryPremiumDetail = homeDeliveryPremiumDetail;
           this.freightGuaranteeDetail = freightGuaranteeDetail;
           this.etdDetail = etdDetail;
           this.customDeliveryWindowDetail = customDeliveryWindowDetail;
    }


    /**
     * Gets the specialServiceTypes value for this ShipmentSpecialServicesRequested.
     * 
     * @return specialServiceTypes   * The types of all special services requested for the enclosing
     * shipment (or other shipment-level transaction).
     */
    public com.proquifa.net.negocio.envio.ship.stub.ShipmentSpecialServiceType[] getSpecialServiceTypes() {
        return specialServiceTypes;
    }


    /**
     * Sets the specialServiceTypes value for this ShipmentSpecialServicesRequested.
     * 
     * @param specialServiceTypes   * The types of all special services requested for the enclosing
     * shipment (or other shipment-level transaction).
     */
    public void setSpecialServiceTypes(com.proquifa.net.negocio.envio.ship.stub.ShipmentSpecialServiceType[] specialServiceTypes) {
        this.specialServiceTypes = specialServiceTypes;
    }

    public com.proquifa.net.negocio.envio.ship.stub.ShipmentSpecialServiceType getSpecialServiceTypes(int i) {
        return this.specialServiceTypes[i];
    }

    public void setSpecialServiceTypes(int i, com.proquifa.net.negocio.envio.ship.stub.ShipmentSpecialServiceType _value) {
        this.specialServiceTypes[i] = _value;
    }


    /**
     * Gets the codDetail value for this ShipmentSpecialServicesRequested.
     * 
     * @return codDetail
     */
    public com.proquifa.net.negocio.envio.ship.stub.CodDetail getCodDetail() {
        return codDetail;
    }


    /**
     * Sets the codDetail value for this ShipmentSpecialServicesRequested.
     * 
     * @param codDetail
     */
    public void setCodDetail(com.proquifa.net.negocio.envio.ship.stub.CodDetail codDetail) {
        this.codDetail = codDetail;
    }


    /**
     * Gets the deliveryOnInvoiceAcceptanceDetail value for this ShipmentSpecialServicesRequested.
     * 
     * @return deliveryOnInvoiceAcceptanceDetail
     */
    public com.proquifa.net.negocio.envio.ship.stub.DeliveryOnInvoiceAcceptanceDetail getDeliveryOnInvoiceAcceptanceDetail() {
        return deliveryOnInvoiceAcceptanceDetail;
    }


    /**
     * Sets the deliveryOnInvoiceAcceptanceDetail value for this ShipmentSpecialServicesRequested.
     * 
     * @param deliveryOnInvoiceAcceptanceDetail
     */
    public void setDeliveryOnInvoiceAcceptanceDetail(com.proquifa.net.negocio.envio.ship.stub.DeliveryOnInvoiceAcceptanceDetail deliveryOnInvoiceAcceptanceDetail) {
        this.deliveryOnInvoiceAcceptanceDetail = deliveryOnInvoiceAcceptanceDetail;
    }


    /**
     * Gets the holdAtLocationDetail value for this ShipmentSpecialServicesRequested.
     * 
     * @return holdAtLocationDetail
     */
    public com.proquifa.net.negocio.envio.ship.stub.HoldAtLocationDetail getHoldAtLocationDetail() {
        return holdAtLocationDetail;
    }


    /**
     * Sets the holdAtLocationDetail value for this ShipmentSpecialServicesRequested.
     * 
     * @param holdAtLocationDetail
     */
    public void setHoldAtLocationDetail(com.proquifa.net.negocio.envio.ship.stub.HoldAtLocationDetail holdAtLocationDetail) {
        this.holdAtLocationDetail = holdAtLocationDetail;
    }


    /**
     * Gets the eventNotificationDetail value for this ShipmentSpecialServicesRequested.
     * 
     * @return eventNotificationDetail   * This replaces eMailNotificationDetail
     */
    public com.proquifa.net.negocio.envio.ship.stub.ShipmentEventNotificationDetail getEventNotificationDetail() {
        return eventNotificationDetail;
    }


    /**
     * Sets the eventNotificationDetail value for this ShipmentSpecialServicesRequested.
     * 
     * @param eventNotificationDetail   * This replaces eMailNotificationDetail
     */
    public void setEventNotificationDetail(com.proquifa.net.negocio.envio.ship.stub.ShipmentEventNotificationDetail eventNotificationDetail) {
        this.eventNotificationDetail = eventNotificationDetail;
    }


    /**
     * Gets the returnShipmentDetail value for this ShipmentSpecialServicesRequested.
     * 
     * @return returnShipmentDetail
     */
    public com.proquifa.net.negocio.envio.ship.stub.ReturnShipmentDetail getReturnShipmentDetail() {
        return returnShipmentDetail;
    }


    /**
     * Sets the returnShipmentDetail value for this ShipmentSpecialServicesRequested.
     * 
     * @param returnShipmentDetail
     */
    public void setReturnShipmentDetail(com.proquifa.net.negocio.envio.ship.stub.ReturnShipmentDetail returnShipmentDetail) {
        this.returnShipmentDetail = returnShipmentDetail;
    }


    /**
     * Gets the pendingShipmentDetail value for this ShipmentSpecialServicesRequested.
     * 
     * @return pendingShipmentDetail   * This field should be populated for pending shipments (e.g.
     * e-mail label) It is required by a PENDING_SHIPMENT special service
     * type.
     */
    public com.proquifa.net.negocio.envio.ship.stub.PendingShipmentDetail getPendingShipmentDetail() {
        return pendingShipmentDetail;
    }


    /**
     * Sets the pendingShipmentDetail value for this ShipmentSpecialServicesRequested.
     * 
     * @param pendingShipmentDetail   * This field should be populated for pending shipments (e.g.
     * e-mail label) It is required by a PENDING_SHIPMENT special service
     * type.
     */
    public void setPendingShipmentDetail(com.proquifa.net.negocio.envio.ship.stub.PendingShipmentDetail pendingShipmentDetail) {
        this.pendingShipmentDetail = pendingShipmentDetail;
    }


    /**
     * Gets the internationalControlledExportDetail value for this ShipmentSpecialServicesRequested.
     * 
     * @return internationalControlledExportDetail
     */
    public com.proquifa.net.negocio.envio.ship.stub.InternationalControlledExportDetail getInternationalControlledExportDetail() {
        return internationalControlledExportDetail;
    }


    /**
     * Sets the internationalControlledExportDetail value for this ShipmentSpecialServicesRequested.
     * 
     * @param internationalControlledExportDetail
     */
    public void setInternationalControlledExportDetail(com.proquifa.net.negocio.envio.ship.stub.InternationalControlledExportDetail internationalControlledExportDetail) {
        this.internationalControlledExportDetail = internationalControlledExportDetail;
    }


    /**
     * Gets the internationalTrafficInArmsRegulationsDetail value for this ShipmentSpecialServicesRequested.
     * 
     * @return internationalTrafficInArmsRegulationsDetail
     */
    public com.proquifa.net.negocio.envio.ship.stub.InternationalTrafficInArmsRegulationsDetail getInternationalTrafficInArmsRegulationsDetail() {
        return internationalTrafficInArmsRegulationsDetail;
    }


    /**
     * Sets the internationalTrafficInArmsRegulationsDetail value for this ShipmentSpecialServicesRequested.
     * 
     * @param internationalTrafficInArmsRegulationsDetail
     */
    public void setInternationalTrafficInArmsRegulationsDetail(com.proquifa.net.negocio.envio.ship.stub.InternationalTrafficInArmsRegulationsDetail internationalTrafficInArmsRegulationsDetail) {
        this.internationalTrafficInArmsRegulationsDetail = internationalTrafficInArmsRegulationsDetail;
    }


    /**
     * Gets the shipmentDryIceDetail value for this ShipmentSpecialServicesRequested.
     * 
     * @return shipmentDryIceDetail
     */
    public com.proquifa.net.negocio.envio.ship.stub.ShipmentDryIceDetail getShipmentDryIceDetail() {
        return shipmentDryIceDetail;
    }


    /**
     * Sets the shipmentDryIceDetail value for this ShipmentSpecialServicesRequested.
     * 
     * @param shipmentDryIceDetail
     */
    public void setShipmentDryIceDetail(com.proquifa.net.negocio.envio.ship.stub.ShipmentDryIceDetail shipmentDryIceDetail) {
        this.shipmentDryIceDetail = shipmentDryIceDetail;
    }


    /**
     * Gets the homeDeliveryPremiumDetail value for this ShipmentSpecialServicesRequested.
     * 
     * @return homeDeliveryPremiumDetail
     */
    public com.proquifa.net.negocio.envio.ship.stub.HomeDeliveryPremiumDetail getHomeDeliveryPremiumDetail() {
        return homeDeliveryPremiumDetail;
    }


    /**
     * Sets the homeDeliveryPremiumDetail value for this ShipmentSpecialServicesRequested.
     * 
     * @param homeDeliveryPremiumDetail
     */
    public void setHomeDeliveryPremiumDetail(com.proquifa.net.negocio.envio.ship.stub.HomeDeliveryPremiumDetail homeDeliveryPremiumDetail) {
        this.homeDeliveryPremiumDetail = homeDeliveryPremiumDetail;
    }


    /**
     * Gets the freightGuaranteeDetail value for this ShipmentSpecialServicesRequested.
     * 
     * @return freightGuaranteeDetail
     */
    public com.proquifa.net.negocio.envio.ship.stub.FreightGuaranteeDetail getFreightGuaranteeDetail() {
        return freightGuaranteeDetail;
    }


    /**
     * Sets the freightGuaranteeDetail value for this ShipmentSpecialServicesRequested.
     * 
     * @param freightGuaranteeDetail
     */
    public void setFreightGuaranteeDetail(com.proquifa.net.negocio.envio.ship.stub.FreightGuaranteeDetail freightGuaranteeDetail) {
        this.freightGuaranteeDetail = freightGuaranteeDetail;
    }


    /**
     * Gets the etdDetail value for this ShipmentSpecialServicesRequested.
     * 
     * @return etdDetail   * Electronic Trade document references.
     */
    public com.proquifa.net.negocio.envio.ship.stub.EtdDetail getEtdDetail() {
        return etdDetail;
    }


    /**
     * Sets the etdDetail value for this ShipmentSpecialServicesRequested.
     * 
     * @param etdDetail   * Electronic Trade document references.
     */
    public void setEtdDetail(com.proquifa.net.negocio.envio.ship.stub.EtdDetail etdDetail) {
        this.etdDetail = etdDetail;
    }


    /**
     * Gets the customDeliveryWindowDetail value for this ShipmentSpecialServicesRequested.
     * 
     * @return customDeliveryWindowDetail   * Specification for date or range of dates on which delivery
     * is to be attempted.
     */
    public com.proquifa.net.negocio.envio.ship.stub.CustomDeliveryWindowDetail getCustomDeliveryWindowDetail() {
        return customDeliveryWindowDetail;
    }


    /**
     * Sets the customDeliveryWindowDetail value for this ShipmentSpecialServicesRequested.
     * 
     * @param customDeliveryWindowDetail   * Specification for date or range of dates on which delivery
     * is to be attempted.
     */
    public void setCustomDeliveryWindowDetail(com.proquifa.net.negocio.envio.ship.stub.CustomDeliveryWindowDetail customDeliveryWindowDetail) {
        this.customDeliveryWindowDetail = customDeliveryWindowDetail;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ShipmentSpecialServicesRequested)) return false;
        ShipmentSpecialServicesRequested other = (ShipmentSpecialServicesRequested) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.specialServiceTypes==null && other.getSpecialServiceTypes()==null) || 
             (this.specialServiceTypes!=null &&
              java.util.Arrays.equals(this.specialServiceTypes, other.getSpecialServiceTypes()))) &&
            ((this.codDetail==null && other.getCodDetail()==null) || 
             (this.codDetail!=null &&
              this.codDetail.equals(other.getCodDetail()))) &&
            ((this.deliveryOnInvoiceAcceptanceDetail==null && other.getDeliveryOnInvoiceAcceptanceDetail()==null) || 
             (this.deliveryOnInvoiceAcceptanceDetail!=null &&
              this.deliveryOnInvoiceAcceptanceDetail.equals(other.getDeliveryOnInvoiceAcceptanceDetail()))) &&
            ((this.holdAtLocationDetail==null && other.getHoldAtLocationDetail()==null) || 
             (this.holdAtLocationDetail!=null &&
              this.holdAtLocationDetail.equals(other.getHoldAtLocationDetail()))) &&
            ((this.eventNotificationDetail==null && other.getEventNotificationDetail()==null) || 
             (this.eventNotificationDetail!=null &&
              this.eventNotificationDetail.equals(other.getEventNotificationDetail()))) &&
            ((this.returnShipmentDetail==null && other.getReturnShipmentDetail()==null) || 
             (this.returnShipmentDetail!=null &&
              this.returnShipmentDetail.equals(other.getReturnShipmentDetail()))) &&
            ((this.pendingShipmentDetail==null && other.getPendingShipmentDetail()==null) || 
             (this.pendingShipmentDetail!=null &&
              this.pendingShipmentDetail.equals(other.getPendingShipmentDetail()))) &&
            ((this.internationalControlledExportDetail==null && other.getInternationalControlledExportDetail()==null) || 
             (this.internationalControlledExportDetail!=null &&
              this.internationalControlledExportDetail.equals(other.getInternationalControlledExportDetail()))) &&
            ((this.internationalTrafficInArmsRegulationsDetail==null && other.getInternationalTrafficInArmsRegulationsDetail()==null) || 
             (this.internationalTrafficInArmsRegulationsDetail!=null &&
              this.internationalTrafficInArmsRegulationsDetail.equals(other.getInternationalTrafficInArmsRegulationsDetail()))) &&
            ((this.shipmentDryIceDetail==null && other.getShipmentDryIceDetail()==null) || 
             (this.shipmentDryIceDetail!=null &&
              this.shipmentDryIceDetail.equals(other.getShipmentDryIceDetail()))) &&
            ((this.homeDeliveryPremiumDetail==null && other.getHomeDeliveryPremiumDetail()==null) || 
             (this.homeDeliveryPremiumDetail!=null &&
              this.homeDeliveryPremiumDetail.equals(other.getHomeDeliveryPremiumDetail()))) &&
            ((this.freightGuaranteeDetail==null && other.getFreightGuaranteeDetail()==null) || 
             (this.freightGuaranteeDetail!=null &&
              this.freightGuaranteeDetail.equals(other.getFreightGuaranteeDetail()))) &&
            ((this.etdDetail==null && other.getEtdDetail()==null) || 
             (this.etdDetail!=null &&
              this.etdDetail.equals(other.getEtdDetail()))) &&
            ((this.customDeliveryWindowDetail==null && other.getCustomDeliveryWindowDetail()==null) || 
             (this.customDeliveryWindowDetail!=null &&
              this.customDeliveryWindowDetail.equals(other.getCustomDeliveryWindowDetail())));
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
        if (getSpecialServiceTypes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSpecialServiceTypes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSpecialServiceTypes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCodDetail() != null) {
            _hashCode += getCodDetail().hashCode();
        }
        if (getDeliveryOnInvoiceAcceptanceDetail() != null) {
            _hashCode += getDeliveryOnInvoiceAcceptanceDetail().hashCode();
        }
        if (getHoldAtLocationDetail() != null) {
            _hashCode += getHoldAtLocationDetail().hashCode();
        }
        if (getEventNotificationDetail() != null) {
            _hashCode += getEventNotificationDetail().hashCode();
        }
        if (getReturnShipmentDetail() != null) {
            _hashCode += getReturnShipmentDetail().hashCode();
        }
        if (getPendingShipmentDetail() != null) {
            _hashCode += getPendingShipmentDetail().hashCode();
        }
        if (getInternationalControlledExportDetail() != null) {
            _hashCode += getInternationalControlledExportDetail().hashCode();
        }
        if (getInternationalTrafficInArmsRegulationsDetail() != null) {
            _hashCode += getInternationalTrafficInArmsRegulationsDetail().hashCode();
        }
        if (getShipmentDryIceDetail() != null) {
            _hashCode += getShipmentDryIceDetail().hashCode();
        }
        if (getHomeDeliveryPremiumDetail() != null) {
            _hashCode += getHomeDeliveryPremiumDetail().hashCode();
        }
        if (getFreightGuaranteeDetail() != null) {
            _hashCode += getFreightGuaranteeDetail().hashCode();
        }
        if (getEtdDetail() != null) {
            _hashCode += getEtdDetail().hashCode();
        }
        if (getCustomDeliveryWindowDetail() != null) {
            _hashCode += getCustomDeliveryWindowDetail().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ShipmentSpecialServicesRequested.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShipmentSpecialServicesRequested"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("specialServiceTypes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "SpecialServiceTypes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShipmentSpecialServiceType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CodDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CodDetail"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deliveryOnInvoiceAcceptanceDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DeliveryOnInvoiceAcceptanceDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DeliveryOnInvoiceAcceptanceDetail"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("holdAtLocationDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "HoldAtLocationDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "HoldAtLocationDetail"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("eventNotificationDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "EventNotificationDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShipmentEventNotificationDetail"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("returnShipmentDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ReturnShipmentDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ReturnShipmentDetail"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pendingShipmentDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PendingShipmentDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PendingShipmentDetail"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("internationalControlledExportDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "InternationalControlledExportDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "InternationalControlledExportDetail"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("internationalTrafficInArmsRegulationsDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "InternationalTrafficInArmsRegulationsDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "InternationalTrafficInArmsRegulationsDetail"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shipmentDryIceDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShipmentDryIceDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShipmentDryIceDetail"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("homeDeliveryPremiumDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "HomeDeliveryPremiumDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "HomeDeliveryPremiumDetail"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("freightGuaranteeDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FreightGuaranteeDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FreightGuaranteeDetail"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("etdDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "EtdDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "EtdDetail"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customDeliveryWindowDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CustomDeliveryWindowDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CustomDeliveryWindowDetail"));
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
