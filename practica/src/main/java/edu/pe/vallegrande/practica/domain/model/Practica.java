package edu.pe.vallegrande.practica.domain.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("practica")

public class Practica {

    @Id
    private Long id;
    private Integer dni;
    private String firstname;
    private String lastname;
    private String email;
    private LocalDateTime date;
    private Boolean active;

}