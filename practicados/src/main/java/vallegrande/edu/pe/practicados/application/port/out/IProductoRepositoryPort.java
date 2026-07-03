package vallegrande.edu.pe.practicados.application.port.out;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vallegrande.edu.pe.practicados.domain.model.Producto;

public interface IProductoRepositoryPort {
    Flux<Producto> findAll();
    Flux<Producto> findAllActive();
    Flux<Producto> findAllInactive();
    Mono<Producto> findById(String id);
    Mono<Producto> save(Producto producto);
}

/** 
package vallegrande.edu.pe.practicados.application.port.out;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vallegrande.edu.pe.practicados.domain.model.Producto;

public interface IProductoRepositoryPort {
    Flux<Producto> findAll();
    Flux<Producto> findAllActive();
    Flux<Producto> findAllInactive();
    Mono<Producto> findById(Long id);
    Mono<Producto> save(Producto producto);
}
    */