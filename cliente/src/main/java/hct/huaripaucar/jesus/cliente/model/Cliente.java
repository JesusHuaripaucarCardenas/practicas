package hct.huaripaucar.jesus.cliente.model;

import jakarta.validation.constraints.*;
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
@Table("cliente")
public class Cliente {

    @Id
    private Long id;

    @NotBlank @Pattern(regexp = "\\d{8}", message = "El DNI debe tener 8 dígitos")
    private String dni;

    @NotBlank(message = "Los nombres son obligatorios")
    private String nombres;

    @NotBlank(message = "Los apellidos son obligatorios")
    private String apellidos;

    @NotBlank @Pattern(regexp = "\\d{9}", message = "El celular debe tener 9 dígitos")
    private String celular;

    @NotBlank @Email(message = "El correo no es válido")
    private String correo;

    @NotBlank(message = "La licencia es obligatoria")
    private String licencia;

    @Builder.Default
    private String estado = "ACTIVO";
}
