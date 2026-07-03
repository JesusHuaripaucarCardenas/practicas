package edu.pe.vallegrande.practica.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import edu.pe.vallegrande.practica.application.port.in.IPracticaServicePort;
import edu.pe.vallegrande.practica.application.port.out.IPracticaRepositoryPort;
import edu.pe.vallegrande.practica.domain.model.Practica;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PracticaService implements IPracticaServicePort {

    private final IPracticaRepositoryPort repositoryPort;

    @Override
    public Flux<Practica> findAll() {
        return repositoryPort.findAll();
    }

    @Override
    public Flux<Practica> findAllActive() {
        return repositoryPort.findAllActive();
    }

    @Override
    public Flux<Practica> findAllInactive() {
        return repositoryPort.findAllInactive();
    }

    @Override
    public Mono<Practica> findById(Long id) {
        return repositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Practica no encontrada")));
    }

    @Override
    public Mono<Practica> create(Practica practica) {
        practica.setActive(true);
        return repositoryPort.create(practica);
    }

    @Override
    public Mono<Practica> update(Long id, Practica practica) {
        return findById(id)
                .flatMap(existing -> {
                    existing.setDni(practica.getDni());
                    existing.setFirstname(practica.getFirstname());
                    existing.setLastname(practica.getLastname());
                    existing.setEmail(practica.getEmail());
                    return repositoryPort.update(id, existing);
                });
    }

    @Override
    public Mono<Void> logicalDelete(Long id) {
        return findById(id)
                .flatMap(p -> repositoryPort.logicalDelete(id));
    }

    @Override
    public Mono<Void> restore(Long id) {
        return findById(id)
                .flatMap(p -> repositoryPort.restore(id));
    }
}