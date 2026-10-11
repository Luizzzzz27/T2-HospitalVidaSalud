package com.hospitalvidasalud.mscita.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("cita")
public class Cita {

    @Id
    private Long id;
    private Long pacienteId;
    private String especialidad;
    private LocalDateTime fechaHora;
    private String estado;
}