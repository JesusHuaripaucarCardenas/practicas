package edu.pe.vallegrande.practica.application.port.in;

import edu.pe.vallegrande.practica.domain.model.Practica;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IPracticaServicePort {
    Flux<Practica> findAll();
    Flux<Practica> findAllActive();
    Flux<Practica> findAllInactive();
    Mono<Practica> findById(Long id);
    Mono<Practica> create(Practica practica);
    Mono<Practica> update(Long id, Practica practica);
    Mono<Void> logicalDelete(Long id);
    Mono<Void> restore(Long id);
}