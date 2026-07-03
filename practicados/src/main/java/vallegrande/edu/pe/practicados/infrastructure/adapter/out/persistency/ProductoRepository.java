package vallegrande.edu.pe.practicados.infrastructure.adapter.out.persistency;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import vallegrande.edu.pe.practicados.domain.model.Producto;

public interface ProductoRepository extends ReactiveMongoRepository<Producto, String> {
    Flux<Producto> findByActiveTrue();
    Flux<Producto> findByActiveFalse();
}

/**
    PostgreSQL
* package vallegrande.edu.pe.practicados.infrastructure.adapter.out.persistency;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import vallegrande.edu.pe.practicados.domain.model.Producto;

public interface ProductoRepository extends ReactiveCrudRepository<Producto, Long> {
    Flux<Producto> findByActiveTrue();
    Flux<Producto> findByActiveFalse();
}
    /* */