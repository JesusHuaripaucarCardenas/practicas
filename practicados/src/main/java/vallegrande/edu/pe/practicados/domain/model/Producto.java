package vallegrande.edu.pe.practicados.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("producto")
public class Producto {
    @Id
    private String id;
    private String nombre;
    private Double precio;
    private Integer stock;
    private Boolean active;
}

/**
 * postgreSQL
 package vallegrande.edu.pe.practicados.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("producto")
public class Producto {
    @Id
    private Long id;
    private String nombre;
    private Double precio;
    private Integer stock;
    private Boolean active;
}
    */