package edu.pe.vallegrande.practica.infrastructure.adapter.out.persistency;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import edu.pe.vallegrande.practica.domain.model.Practica;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PracticaRepository extends ReactiveCrudRepository<Practica, Long> {

    Flux<Practica> findByActiveTrue();

    Flux<Practica> findByActiveFalse();

    @Modifying
    @Query("UPDATE practica SET active = false WHERE id = :id")
    Mono<Void> logicalDeleteById(Long id);

    @Modifying
    @Query("UPDATE practica SET active = true WHERE id = :id")
    Mono<Void> restoreById(Long id);
}