package hct.huaripaucar.jesus.alquiler.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("alquiler")
public class Alquiler {

    @Id
    private Long id;

    @NotNull(message = "El cliente es obligatorio")
    private Long clienteId;

    @NotNull(message = "El vehiculo es obligatorio")
    private Long vehiculoId;

    @NotNull @Positive
    private Integer dias;

    @NotNull
    private LocalDate fechaInicio;

    @NotNull
    private LocalDate fechaFin;

    @NotNull @PositiveOrZero
    private BigDecimal total;

    @Builder.Default
    private String estado = "ACTIVO";
}
