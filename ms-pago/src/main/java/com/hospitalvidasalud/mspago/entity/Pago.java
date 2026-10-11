package com.hospitalvidasalud.mspago.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("pago")
public class Pago {

    @Id
    private Long id;
    private Long atencionId;
    private BigDecimal monto;
    private LocalDateTime fechaPago;
    private String estado;

    public Pago() {
    }

    public Pago(Long id, Long atencionId, BigDecimal monto,
                LocalDateTime fechaPago, String estado) {
        this.id = id;
        this.atencionId = atencionId;
        this.monto = monto;
        this.fechaPago = fechaPago;
        this.estado = estado;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getAtencionId() { return atencionId; }
    public void setAtencionId(Long atencionId) { this.atencionId = atencionId; }
    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }
    public LocalDateTime getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDateTime fechaPago) { this.fechaPago = fechaPago; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}