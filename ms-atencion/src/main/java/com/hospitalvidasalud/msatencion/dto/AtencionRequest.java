package com.hospitalvidasalud.msatencion.dto;

public class AtencionRequest {

    private Long citaId;

    public AtencionRequest() {
    }

    public AtencionRequest(Long citaId) {
        this.citaId = citaId;
    }

    public Long getCitaId() { return citaId; }
    public void setCitaId(Long citaId) { this.citaId = citaId; }
}