package vallegrande.edu.pe.practicados.application.port.in;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vallegrande.edu.pe.practicados.domain.model.Producto;

public interface IProductoServicePort {
    Flux<Producto> findAll();
    Flux<Producto> findAllActive();
    Flux<Producto> findAllInactive();
    Mono<Producto> findById(String id);
    Mono<Producto> create(Producto producto);
    Mono<Producto> update(String id, Producto producto);
    Mono<Void> logicalDelete(String id);
    Mono<Void> restore(String id);
}

/** 
 * postgreSQL
package vallegrande.edu.pe.practicados.application.port.in;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vallegrande.edu.pe.practicados.domain.model.Producto;

public interface IProductoServicePort {
    Flux<Producto> findAll();
    Flux<Producto> findAllActive();
    Flux<Producto> findAllInactive();
    Mono<Producto> findById(Long id);
    Mono<Producto> create(Producto producto);
    Mono<Producto> update(Long id, Producto producto);
    Mono<Void> logicalDelete(Long id);
    Mono<Void> restore(Long id);
}
    */