package hct.huaripaucar.jesus.vehiculo.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("vehiculo")
public class Vehiculo {

    @Id
    private Long id;

    @NotBlank(message = "La placa es obligatoria")
    private String placa;

    @NotBlank(message = "La marca es obligatoria")
    private String marca;

    @NotBlank(message = "El modelo es obligatorio")
    private String modelo;

    @NotNull @Min(1990)
    private Integer anio;

    @NotBlank(message = "El color es obligatorio")
    private String color;

    @NotNull @Positive
    private BigDecimal precioPorDia;

    @Builder.Default
    private String estado = "DISPONIBLE";
}
