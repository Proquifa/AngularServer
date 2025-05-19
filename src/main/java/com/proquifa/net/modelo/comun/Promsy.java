package com.proquifa.net.modelo.comun;



import java.math.BigDecimal;

public class Promsy {
    private Integer Piezas;
    private BigDecimal monto;
    private String periodo;
    private String proveedor;

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public Integer getPiezas() {
        return Piezas;
    }

    public void setPiezas(Integer piezas) {
        Piezas = piezas;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
}
