package com.hospitalvidasalud.mspago.dto;

import java.math.BigDecimal;

public class PagoRequest {

    private Long atencionId;
    private BigDecimal monto;

    public PagoRequest() {
    }

    public PagoRequest(Long atencionId, BigDecimal monto) {
        this.atencionId = atencionId;
        this.monto = monto;
    }

    public Long getAtencionId() { return atencionId; }
    public void setAtencionId(Long atencionId) { this.atencionId = atencionId; }
    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }
}