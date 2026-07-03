package vallegrande.edu.pe.practicados.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vallegrande.edu.pe.practicados.application.port.in.IProductoServicePort;
import vallegrande.edu.pe.practicados.application.port.out.IProductoRepositoryPort;
import vallegrande.edu.pe.practicados.domain.model.Producto;

@Service
@RequiredArgsConstructor
public class ProductoService implements IProductoServicePort {

    private final IProductoRepositoryPort repositoryPort;

    @Override
    public Flux<Producto> findAll() {
        return repositoryPort.findAll();
    }

    @Override
    public Flux<Producto> findAllActive() {
        return repositoryPort.findAllActive();
    }

    @Override
    public Flux<Producto> findAllInactive() {
        return repositoryPort.findAllInactive();
    }

    @Override
    public Mono<Producto> findById(Long id) {
        return repositoryPort.findById(id);
    }

    @Override
    public Mono<Producto> create(Producto producto) {
        producto.setActive(true);
        return repositoryPort.save(producto);
    }

    @Override
    public Mono<Producto> update(Long id, Producto producto) {
        return repositoryPort.findById(id)
                .flatMap(existing -> {
                    existing.setNombre(producto.getNombre());
                    existing.setPrecio(producto.getPrecio());
                    existing.setStock(producto.getStock());
                    return repositoryPort.save(existing);
                });
    }

    @Override
    public Mono<Void> logicalDelete(Long id) {
        return repositoryPort.findById(id)
                .flatMap(existing -> {
                    existing.setActive(false);
                    return repositoryPort.save(existing);
                })
                .then();
    }

    @Override
    public Mono<Void> restore(Long id) {
        return repositoryPort.findById(id)
                .flatMap(existing -> {
                    existing.setActive(true);
                    return repositoryPort.save(existing);
                })
                .then();
    }
}