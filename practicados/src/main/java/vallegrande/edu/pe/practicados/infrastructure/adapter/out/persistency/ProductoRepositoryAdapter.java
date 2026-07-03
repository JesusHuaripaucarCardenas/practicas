package vallegrande.edu.pe.practicados.infrastructure.adapter.out.persistency;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vallegrande.edu.pe.practicados.application.port.out.IProductoRepositoryPort;
import vallegrande.edu.pe.practicados.domain.model.Producto;

@Repository
@RequiredArgsConstructor
public class ProductoRepositoryAdapter implements IProductoRepositoryPort {

    private final ProductoRepository repository;

    @Override
    public Flux<Producto> findAll() {
        return repository.findAll();
    }

    @Override
    public Flux<Producto> findAllActive() {
        return repository.findByActiveTrue();
    }

    @Override
    public Flux<Producto> findAllInactive() {
        return repository.findByActiveFalse();
    }

    @Override
    public Mono<Producto> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Mono<Producto> save(Producto producto) {
        return repository.save(producto);
    }
}