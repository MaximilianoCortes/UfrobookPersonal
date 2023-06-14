package com.ufrobook.isoft.ufrobook.festividades.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "calendario_festividad")
public class CalendarioFestividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Column(name = "nombre_festividad")
    private String nombreFestividad;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @OneToOne
    @JoinColumn(name = "festividad_id")
    private Festividad festividad;

}
