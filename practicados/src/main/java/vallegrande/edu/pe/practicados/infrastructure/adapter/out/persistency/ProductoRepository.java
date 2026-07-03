package vallegrande.edu.pe.practicados.infrastructure.adapter.out.persistency;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import vallegrande.edu.pe.practicados.domain.model.Producto;

public interface ProductoRepository extends ReactiveCrudRepository<Producto, Long> {
    Flux<Producto> findByActiveTrue();
    Flux<Producto> findByActiveFalse();
}