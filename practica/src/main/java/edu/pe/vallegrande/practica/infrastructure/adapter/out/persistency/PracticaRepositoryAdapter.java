package edu.pe.vallegrande.practica.infrastructure.adapter.out.persistency;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import edu.pe.vallegrande.practica.application.port.out.IPracticaRepositoryPort;
import edu.pe.vallegrande.practica.domain.model.Practica;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PracticaRepositoryAdapter implements IPracticaRepositoryPort {

    private final PracticaRepository repository;

    @Override
    public Flux<Practica> findAll() {
        return repository.findAll();
    }

    @Override
    public Flux<Practica> findAllActive() {
        return repository.findByActiveTrue();
    }

    @Override
    public Flux<Practica> findAllInactive() {
        return repository.findByActiveFalse();
    }

    @Override
    public Mono<Practica> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Mono<Practica> create(Practica practica) {
        return repository.save(practica);
    }

    @Override
    public Mono<Practica> update(Long id, Practica practica) {
        return repository.save(practica);
    }

    @Override
    public Mono<Void> logicalDelete(Long id) {
        return repository.logicalDeleteById(id);
    }

    @Override
    public Mono<Void> restore(Long id) {
        return repository.restoreById(id);
    }
}