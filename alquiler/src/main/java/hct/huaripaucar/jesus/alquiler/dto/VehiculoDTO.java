package hct.huaripaucar.jesus.alquiler.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehiculoDTO {
    private Long id;
    private String estado;
}