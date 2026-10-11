package com.hospitalvidasalud.msatencion.entity;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("atencion")
public class Atencion {

    @Id
    private Long id;
    private Long citaId;
    private LocalDateTime fechaAtencion;
    private String estado;

    public Atencion() {
    }

    public Atencion(Long id, Long citaId, LocalDateTime fechaAtencion, String estado) {
        this.id = id;
        this.citaId = citaId;
        this.fechaAtencion = fechaAtencion;
        this.estado = estado;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getCitaId() { return citaId; }
    public void setCitaId(Long citaId) { this.citaId = citaId; }
    public LocalDateTime getFechaAtencion() { return fechaAtencion; }
    public void setFechaAtencion(LocalDateTime fechaAtencion) { this.fechaAtencion = fechaAtencion; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}