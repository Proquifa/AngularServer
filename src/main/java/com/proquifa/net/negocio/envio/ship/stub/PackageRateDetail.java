/**
 * PackageRateDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.proquifa.net.negocio.envio.ship.stub;


/**
 * Data for a package's rates, as calculated per a specific rate type.
 */
public class PackageRateDetail  implements java.io.Serializable {
    /* Type used for this specific set of rate data. */
    private com.proquifa.net.negocio.envio.ship.stub.ReturnedRateType rateType;

    /* Indicates which weight was used. */
    private com.proquifa.net.negocio.envio.ship.stub.RatedWeightMethod ratedWeightMethod;

    /* INTERNAL FEDEX USE ONLY. */
    private com.proquifa.net.negocio.envio.ship.stub.MinimumChargeType minimumChargeType;

    private com.proquifa.net.negocio.envio.ship.stub.Weight billingWeight;

    /* The dimensional weight of this package (if greater than actual). */
    private com.proquifa.net.negocio.envio.ship.stub.Weight dimWeight;

    /* The oversize weight of this package (if the package is oversize). */
    private com.proquifa.net.negocio.envio.ship.stub.Weight oversizeWeight;

    /* The transportation charge only (prior to any discounts applied)
     * for this package. */
    private com.proquifa.net.negocio.envio.ship.stub.Money baseCharge;

    /* The sum of all discounts on this package. */
    private com.proquifa.net.negocio.envio.ship.stub.Money totalFreightDiscounts;

    /* This package's baseCharge - totalFreightDiscounts. */
    private com.proquifa.net.negocio.envio.ship.stub.Money netFreight;

    /* The sum of all surcharges on this package. */
    private com.proquifa.net.negocio.envio.ship.stub.Money totalSurcharges;

    /* This package's netFreight + totalSurcharges (not including
     * totalTaxes). */
    private com.proquifa.net.negocio.envio.ship.stub.Money netFedExCharge;

    /* The sum of all taxes on this package. */
    private com.proquifa.net.negocio.envio.ship.stub.Money totalTaxes;

    /* This package's netFreight + totalSurcharges + totalTaxes. */
    private com.proquifa.net.negocio.envio.ship.stub.Money netCharge;

    private com.proquifa.net.negocio.envio.ship.stub.Money totalRebates;

    /* All rate discounts that apply to this package. */
    private com.proquifa.net.negocio.envio.ship.stub.RateDiscount[] freightDiscounts;

    /* All rebates that apply to this package. */
    private com.proquifa.net.negocio.envio.ship.stub.Rebate[] rebates;

    /* All surcharges that apply to this package (either because of
     * characteristics of the package itself, or because it is carrying per-shipment
     * surcharges for the shipment of which it is a part). */
    private com.proquifa.net.negocio.envio.ship.stub.Surcharge[] surcharges;

    /* All taxes applicable (or distributed to) this package. */
    private com.proquifa.net.negocio.envio.ship.stub.Tax[] taxes;

    private com.proquifa.net.negocio.envio.ship.stub.VariableHandlingCharges variableHandlingCharges;

    public PackageRateDetail() {
    }

    public PackageRateDetail(
           com.proquifa.net.negocio.envio.ship.stub.ReturnedRateType rateType,
           com.proquifa.net.negocio.envio.ship.stub.RatedWeightMethod ratedWeightMethod,
           com.proquifa.net.negocio.envio.ship.stub.MinimumChargeType minimumChargeType,
           com.proquifa.net.negocio.envio.ship.stub.Weight billingWeight,
           com.proquifa.net.negocio.envio.ship.stub.Weight dimWeight,
           com.proquifa.net.negocio.envio.ship.stub.Weight oversizeWeight,
           com.proquifa.net.negocio.envio.ship.stub.Money baseCharge,
           com.proquifa.net.negocio.envio.ship.stub.Money totalFreightDiscounts,
           com.proquifa.net.negocio.envio.ship.stub.Money netFreight,
           com.proquifa.net.negocio.envio.ship.stub.Money totalSurcharges,
           com.proquifa.net.negocio.envio.ship.stub.Money netFedExCharge,
           com.proquifa.net.negocio.envio.ship.stub.Money totalTaxes,
           com.proquifa.net.negocio.envio.ship.stub.Money netCharge,
           com.proquifa.net.negocio.envio.ship.stub.Money totalRebates,
           com.proquifa.net.negocio.envio.ship.stub.RateDiscount[] freightDiscounts,
           com.proquifa.net.negocio.envio.ship.stub.Rebate[] rebates,
           com.proquifa.net.negocio.envio.ship.stub.Surcharge[] surcharges,
           com.proquifa.net.negocio.envio.ship.stub.Tax[] taxes,
           com.proquifa.net.negocio.envio.ship.stub.VariableHandlingCharges variableHandlingCharges) {
           this.rateType = rateType;
           this.ratedWeightMethod = ratedWeightMethod;
           this.minimumChargeType = minimumChargeType;
           this.billingWeight = billingWeight;
           this.dimWeight = dimWeight;
           this.oversizeWeight = oversizeWeight;
           this.baseCharge = baseCharge;
           this.totalFreightDiscounts = totalFreightDiscounts;
           this.netFreight = netFreight;
           this.totalSurcharges = totalSurcharges;
           this.netFedExCharge = netFedExCharge;
           this.totalTaxes = totalTaxes;
           this.netCharge = netCharge;
           this.totalRebates = totalRebates;
           this.freightDiscounts = freightDiscounts;
           this.rebates = rebates;
           this.surcharges = surcharges;
           this.taxes = taxes;
           this.variableHandlingCharges = variableHandlingCharges;
    }


    /**
     * Gets the rateType value for this PackageRateDetail.
     * 
     * @return rateType   * Type used for this specific set of rate data.
     */
    public com.proquifa.net.negocio.envio.ship.stub.ReturnedRateType getRateType() {
        return rateType;
    }


    /**
     * Sets the rateType value for this PackageRateDetail.
     * 
     * @param rateType   * Type used for this specific set of rate data.
     */
    public void setRateType(com.proquifa.net.negocio.envio.ship.stub.ReturnedRateType rateType) {
        this.rateType = rateType;
    }


    /**
     * Gets the ratedWeightMethod value for this PackageRateDetail.
     * 
     * @return ratedWeightMethod   * Indicates which weight was used.
     */
    public com.proquifa.net.negocio.envio.ship.stub.RatedWeightMethod getRatedWeightMethod() {
        return ratedWeightMethod;
    }


    /**
     * Sets the ratedWeightMethod value for this PackageRateDetail.
     * 
     * @param ratedWeightMethod   * Indicates which weight was used.
     */
    public void setRatedWeightMethod(com.proquifa.net.negocio.envio.ship.stub.RatedWeightMethod ratedWeightMethod) {
        this.ratedWeightMethod = ratedWeightMethod;
    }


    /**
     * Gets the minimumChargeType value for this PackageRateDetail.
     * 
     * @return minimumChargeType   * INTERNAL FEDEX USE ONLY.
     */
    public com.proquifa.net.negocio.envio.ship.stub.MinimumChargeType getMinimumChargeType() {
        return minimumChargeType;
    }


    /**
     * Sets the minimumChargeType value for this PackageRateDetail.
     * 
     * @param minimumChargeType   * INTERNAL FEDEX USE ONLY.
     */
    public void setMinimumChargeType(com.proquifa.net.negocio.envio.ship.stub.MinimumChargeType minimumChargeType) {
        this.minimumChargeType = minimumChargeType;
    }


    /**
     * Gets the billingWeight value for this PackageRateDetail.
     * 
     * @return billingWeight
     */
    public com.proquifa.net.negocio.envio.ship.stub.Weight getBillingWeight() {
        return billingWeight;
    }


    /**
     * Sets the billingWeight value for this PackageRateDetail.
     * 
     * @param billingWeight
     */
    public void setBillingWeight(com.proquifa.net.negocio.envio.ship.stub.Weight billingWeight) {
        this.billingWeight = billingWeight;
    }


    /**
     * Gets the dimWeight value for this PackageRateDetail.
     * 
     * @return dimWeight   * The dimensional weight of this package (if greater than actual).
     */
    public com.proquifa.net.negocio.envio.ship.stub.Weight getDimWeight() {
        return dimWeight;
    }


    /**
     * Sets the dimWeight value for this PackageRateDetail.
     * 
     * @param dimWeight   * The dimensional weight of this package (if greater than actual).
     */
    public void setDimWeight(com.proquifa.net.negocio.envio.ship.stub.Weight dimWeight) {
        this.dimWeight = dimWeight;
    }


    /**
     * Gets the oversizeWeight value for this PackageRateDetail.
     * 
     * @return oversizeWeight   * The oversize weight of this package (if the package is oversize).
     */
    public com.proquifa.net.negocio.envio.ship.stub.Weight getOversizeWeight() {
        return oversizeWeight;
    }


    /**
     * Sets the oversizeWeight value for this PackageRateDetail.
     * 
     * @param oversizeWeight   * The oversize weight of this package (if the package is oversize).
     */
    public void setOversizeWeight(com.proquifa.net.negocio.envio.ship.stub.Weight oversizeWeight) {
        this.oversizeWeight = oversizeWeight;
    }


    /**
     * Gets the baseCharge value for this PackageRateDetail.
     * 
     * @return baseCharge   * The transportation charge only (prior to any discounts applied)
     * for this package.
     */
    public com.proquifa.net.negocio.envio.ship.stub.Money getBaseCharge() {
        return baseCharge;
    }


    /**
     * Sets the baseCharge value for this PackageRateDetail.
     * 
     * @param baseCharge   * The transportation charge only (prior to any discounts applied)
     * for this package.
     */
    public void setBaseCharge(com.proquifa.net.negocio.envio.ship.stub.Money baseCharge) {
        this.baseCharge = baseCharge;
    }


    /**
     * Gets the totalFreightDiscounts value for this PackageRateDetail.
     * 
     * @return totalFreightDiscounts   * The sum of all discounts on this package.
     */
    public com.proquifa.net.negocio.envio.ship.stub.Money getTotalFreightDiscounts() {
        return totalFreightDiscounts;
    }


    /**
     * Sets the totalFreightDiscounts value for this PackageRateDetail.
     * 
     * @param totalFreightDiscounts   * The sum of all discounts on this package.
     */
    public void setTotalFreightDiscounts(com.proquifa.net.negocio.envio.ship.stub.Money totalFreightDiscounts) {
        this.totalFreightDiscounts = totalFreightDiscounts;
    }


    /**
     * Gets the netFreight value for this PackageRateDetail.
     * 
     * @return netFreight   * This package's baseCharge - totalFreightDiscounts.
     */
    public com.proquifa.net.negocio.envio.ship.stub.Money getNetFreight() {
        return netFreight;
    }


    /**
     * Sets the netFreight value for this PackageRateDetail.
     * 
     * @param netFreight   * This package's baseCharge - totalFreightDiscounts.
     */
    public void setNetFreight(com.proquifa.net.negocio.envio.ship.stub.Money netFreight) {
        this.netFreight = netFreight;
    }


    /**
     * Gets the totalSurcharges value for this PackageRateDetail.
     * 
     * @return totalSurcharges   * The sum of all surcharges on this package.
     */
    public com.proquifa.net.negocio.envio.ship.stub.Money getTotalSurcharges() {
        return totalSurcharges;
    }


    /**
     * Sets the totalSurcharges value for this PackageRateDetail.
     * 
     * @param totalSurcharges   * The sum of all surcharges on this package.
     */
    public void setTotalSurcharges(com.proquifa.net.negocio.envio.ship.stub.Money totalSurcharges) {
        this.totalSurcharges = totalSurcharges;
    }


    /**
     * Gets the netFedExCharge value for this PackageRateDetail.
     * 
     * @return netFedExCharge   * This package's netFreight + totalSurcharges (not including
     * totalTaxes).
     */
    public com.proquifa.net.negocio.envio.ship.stub.Money getNetFedExCharge() {
        return netFedExCharge;
    }


    /**
     * Sets the netFedExCharge value for this PackageRateDetail.
     * 
     * @param netFedExCharge   * This package's netFreight + totalSurcharges (not including
     * totalTaxes).
     */
    public void setNetFedExCharge(com.proquifa.net.negocio.envio.ship.stub.Money netFedExCharge) {
        this.netFedExCharge = netFedExCharge;
    }


    /**
     * Gets the totalTaxes value for this PackageRateDetail.
     * 
     * @return totalTaxes   * The sum of all taxes on this package.
     */
    public com.proquifa.net.negocio.envio.ship.stub.Money getTotalTaxes() {
        return totalTaxes;
    }


    /**
     * Sets the totalTaxes value for this PackageRateDetail.
     * 
     * @param totalTaxes   * The sum of all taxes on this package.
     */
    public void setTotalTaxes(com.proquifa.net.negocio.envio.ship.stub.Money totalTaxes) {
        this.totalTaxes = totalTaxes;
    }


    /**
     * Gets the netCharge value for this PackageRateDetail.
     * 
     * @return netCharge   * This package's netFreight + totalSurcharges + totalTaxes.
     */
    public com.proquifa.net.negocio.envio.ship.stub.Money getNetCharge() {
        return netCharge;
    }


    /**
     * Sets the netCharge value for this PackageRateDetail.
     * 
     * @param netCharge   * This package's netFreight + totalSurcharges + totalTaxes.
     */
    public void setNetCharge(com.proquifa.net.negocio.envio.ship.stub.Money netCharge) {
        this.netCharge = netCharge;
    }


    /**
     * Gets the totalRebates value for this PackageRateDetail.
     * 
     * @return totalRebates
     */
    public com.proquifa.net.negocio.envio.ship.stub.Money getTotalRebates() {
        return totalRebates;
    }


    /**
     * Sets the totalRebates value for this PackageRateDetail.
     * 
     * @param totalRebates
     */
    public void setTotalRebates(com.proquifa.net.negocio.envio.ship.stub.Money totalRebates) {
        this.totalRebates = totalRebates;
    }


    /**
     * Gets the freightDiscounts value for this PackageRateDetail.
     * 
     * @return freightDiscounts   * All rate discounts that apply to this package.
     */
    public com.proquifa.net.negocio.envio.ship.stub.RateDiscount[] getFreightDiscounts() {
        return freightDiscounts;
    }


    /**
     * Sets the freightDiscounts value for this PackageRateDetail.
     * 
     * @param freightDiscounts   * All rate discounts that apply to this package.
     */
    public void setFreightDiscounts(com.proquifa.net.negocio.envio.ship.stub.RateDiscount[] freightDiscounts) {
        this.freightDiscounts = freightDiscounts;
    }

    public com.proquifa.net.negocio.envio.ship.stub.RateDiscount getFreightDiscounts(int i) {
        return this.freightDiscounts[i];
    }

    public void setFreightDiscounts(int i, com.proquifa.net.negocio.envio.ship.stub.RateDiscount _value) {
        this.freightDiscounts[i] = _value;
    }


    /**
     * Gets the rebates value for this PackageRateDetail.
     * 
     * @return rebates   * All rebates that apply to this package.
     */
    public com.proquifa.net.negocio.envio.ship.stub.Rebate[] getRebates() {
        return rebates;
    }


    /**
     * Sets the rebates value for this PackageRateDetail.
     * 
     * @param rebates   * All rebates that apply to this package.
     */
    public void setRebates(com.proquifa.net.negocio.envio.ship.stub.Rebate[] rebates) {
        this.rebates = rebates;
    }

    public com.proquifa.net.negocio.envio.ship.stub.Rebate getRebates(int i) {
        return this.rebates[i];
    }

    public void setRebates(int i, com.proquifa.net.negocio.envio.ship.stub.Rebate _value) {
        this.rebates[i] = _value;
    }


    /**
     * Gets the surcharges value for this PackageRateDetail.
     * 
     * @return surcharges   * All surcharges that apply to this package (either because of
     * characteristics of the package itself, or because it is carrying per-shipment
     * surcharges for the shipment of which it is a part).
     */
    public com.proquifa.net.negocio.envio.ship.stub.Surcharge[] getSurcharges() {
        return surcharges;
    }


    /**
     * Sets the surcharges value for this PackageRateDetail.
     * 
     * @param surcharges   * All surcharges that apply to this package (either because of
     * characteristics of the package itself, or because it is carrying per-shipment
     * surcharges for the shipment of which it is a part).
     */
    public void setSurcharges(com.proquifa.net.negocio.envio.ship.stub.Surcharge[] surcharges) {
        this.surcharges = surcharges;
    }

    public com.proquifa.net.negocio.envio.ship.stub.Surcharge getSurcharges(int i) {
        return this.surcharges[i];
    }

    public void setSurcharges(int i, com.proquifa.net.negocio.envio.ship.stub.Surcharge _value) {
        this.surcharges[i] = _value;
    }


    /**
     * Gets the taxes value for this PackageRateDetail.
     * 
     * @return taxes   * All taxes applicable (or distributed to) this package.
     */
    public com.proquifa.net.negocio.envio.ship.stub.Tax[] getTaxes() {
        return taxes;
    }


    /**
     * Sets the taxes value for this PackageRateDetail.
     * 
     * @param taxes   * All taxes applicable (or distributed to) this package.
     */
    public void setTaxes(com.proquifa.net.negocio.envio.ship.stub.Tax[] taxes) {
        this.taxes = taxes;
    }

    public com.proquifa.net.negocio.envio.ship.stub.Tax getTaxes(int i) {
        return this.taxes[i];
    }

    public void setTaxes(int i, com.proquifa.net.negocio.envio.ship.stub.Tax _value) {
        this.taxes[i] = _value;
    }


    /**
     * Gets the variableHandlingCharges value for this PackageRateDetail.
     * 
     * @return variableHandlingCharges
     */
    public com.proquifa.net.negocio.envio.ship.stub.VariableHandlingCharges getVariableHandlingCharges() {
        return variableHandlingCharges;
    }


    /**
     * Sets the variableHandlingCharges value for this PackageRateDetail.
     * 
     * @param variableHandlingCharges
     */
    public void setVariableHandlingCharges(com.proquifa.net.negocio.envio.ship.stub.VariableHandlingCharges variableHandlingCharges) {
        this.variableHandlingCharges = variableHandlingCharges;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PackageRateDetail)) return false;
        PackageRateDetail other = (PackageRateDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.rateType==null && other.getRateType()==null) || 
             (this.rateType!=null &&
              this.rateType.equals(other.getRateType()))) &&
            ((this.ratedWeightMethod==null && other.getRatedWeightMethod()==null) || 
             (this.ratedWeightMethod!=null &&
              this.ratedWeightMethod.equals(other.getRatedWeightMethod()))) &&
            ((this.minimumChargeType==null && other.getMinimumChargeType()==null) || 
             (this.minimumChargeType!=null &&
              this.minimumChargeType.equals(other.getMinimumChargeType()))) &&
            ((this.billingWeight==null && other.getBillingWeight()==null) || 
             (this.billingWeight!=null &&
              this.billingWeight.equals(other.getBillingWeight()))) &&
            ((this.dimWeight==null && other.getDimWeight()==null) || 
             (this.dimWeight!=null &&
              this.dimWeight.equals(other.getDimWeight()))) &&
            ((this.oversizeWeight==null && other.getOversizeWeight()==null) || 
             (this.oversizeWeight!=null &&
              this.oversizeWeight.equals(other.getOversizeWeight()))) &&
            ((this.baseCharge==null && other.getBaseCharge()==null) || 
             (this.baseCharge!=null &&
              this.baseCharge.equals(other.getBaseCharge()))) &&
            ((this.totalFreightDiscounts==null && other.getTotalFreightDiscounts()==null) || 
             (this.totalFreightDiscounts!=null &&
              this.totalFreightDiscounts.equals(other.getTotalFreightDiscounts()))) &&
            ((this.netFreight==null && other.getNetFreight()==null) || 
             (this.netFreight!=null &&
              this.netFreight.equals(other.getNetFreight()))) &&
            ((this.totalSurcharges==null && other.getTotalSurcharges()==null) || 
             (this.totalSurcharges!=null &&
              this.totalSurcharges.equals(other.getTotalSurcharges()))) &&
            ((this.netFedExCharge==null && other.getNetFedExCharge()==null) || 
             (this.netFedExCharge!=null &&
              this.netFedExCharge.equals(other.getNetFedExCharge()))) &&
            ((this.totalTaxes==null && other.getTotalTaxes()==null) || 
             (this.totalTaxes!=null &&
              this.totalTaxes.equals(other.getTotalTaxes()))) &&
            ((this.netCharge==null && other.getNetCharge()==null) || 
             (this.netCharge!=null &&
              this.netCharge.equals(other.getNetCharge()))) &&
            ((this.totalRebates==null && other.getTotalRebates()==null) || 
             (this.totalRebates!=null &&
              this.totalRebates.equals(other.getTotalRebates()))) &&
            ((this.freightDiscounts==null && other.getFreightDiscounts()==null) || 
             (this.freightDiscounts!=null &&
              java.util.Arrays.equals(this.freightDiscounts, other.getFreightDiscounts()))) &&
            ((this.rebates==null && other.getRebates()==null) || 
             (this.rebates!=null &&
              java.util.Arrays.equals(this.rebates, other.getRebates()))) &&
            ((this.surcharges==null && other.getSurcharges()==null) || 
             (this.surcharges!=null &&
              java.util.Arrays.equals(this.surcharges, other.getSurcharges()))) &&
            ((this.taxes==null && other.getTaxes()==null) || 
             (this.taxes!=null &&
              java.util.Arrays.equals(this.taxes, other.getTaxes()))) &&
            ((this.variableHandlingCharges==null && other.getVariableHandlingCharges()==null) || 
             (this.variableHandlingCharges!=null &&
              this.variableHandlingCharges.equals(other.getVariableHandlingCharges())));
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
        if (getRateType() != null) {
            _hashCode += getRateType().hashCode();
        }
        if (getRatedWeightMethod() != null) {
            _hashCode += getRatedWeightMethod().hashCode();
        }
        if (getMinimumChargeType() != null) {
            _hashCode += getMinimumChargeType().hashCode();
        }
        if (getBillingWeight() != null) {
            _hashCode += getBillingWeight().hashCode();
        }
        if (getDimWeight() != null) {
            _hashCode += getDimWeight().hashCode();
        }
        if (getOversizeWeight() != null) {
            _hashCode += getOversizeWeight().hashCode();
        }
        if (getBaseCharge() != null) {
            _hashCode += getBaseCharge().hashCode();
        }
        if (getTotalFreightDiscounts() != null) {
            _hashCode += getTotalFreightDiscounts().hashCode();
        }
        if (getNetFreight() != null) {
            _hashCode += getNetFreight().hashCode();
        }
        if (getTotalSurcharges() != null) {
            _hashCode += getTotalSurcharges().hashCode();
        }
        if (getNetFedExCharge() != null) {
            _hashCode += getNetFedExCharge().hashCode();
        }
        if (getTotalTaxes() != null) {
            _hashCode += getTotalTaxes().hashCode();
        }
        if (getNetCharge() != null) {
            _hashCode += getNetCharge().hashCode();
        }
        if (getTotalRebates() != null) {
            _hashCode += getTotalRebates().hashCode();
        }
        if (getFreightDiscounts() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFreightDiscounts());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFreightDiscounts(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getRebates() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRebates());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRebates(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSurcharges() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSurcharges());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSurcharges(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTaxes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTaxes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTaxes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getVariableHandlingCharges() != null) {
            _hashCode += getVariableHandlingCharges().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PackageRateDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PackageRateDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rateType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "RateType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ReturnedRateType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ratedWeightMethod");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "RatedWeightMethod"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "RatedWeightMethod"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("minimumChargeType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "MinimumChargeType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "MinimumChargeType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("billingWeight");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "BillingWeight"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Weight"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dimWeight");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DimWeight"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Weight"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oversizeWeight");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "OversizeWeight"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Weight"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("baseCharge");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "BaseCharge"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Money"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalFreightDiscounts");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TotalFreightDiscounts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Money"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("netFreight");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "NetFreight"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Money"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalSurcharges");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TotalSurcharges"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Money"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("netFedExCharge");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "NetFedExCharge"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Money"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalTaxes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TotalTaxes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Money"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("netCharge");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "NetCharge"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Money"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalRebates");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TotalRebates"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Money"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("freightDiscounts");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FreightDiscounts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "RateDiscount"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rebates");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Rebates"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Rebate"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("surcharges");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Surcharges"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Surcharge"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Taxes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Tax"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("variableHandlingCharges");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "VariableHandlingCharges"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "VariableHandlingCharges"));
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
