package edu.pe.vallegrande.practica.infrastructure.adapter.in.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import edu.pe.vallegrande.practica.application.port.in.IPracticaServicePort;
import edu.pe.vallegrande.practica.domain.model.Practica;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/practica")
@RequiredArgsConstructor
public class PracticaRest {

    private final IPracticaServicePort servicePort;

    @GetMapping
    public Flux<Practica> findAll() {
        return servicePort.findAll();
    }

    @GetMapping("/active")
    public Flux<Practica> findAllActive() {
        return servicePort.findAllActive();
    }

    @GetMapping("/inactive")
    public Flux<Practica> findAllInactive() {
        return servicePort.findAllInactive();
    }

    @GetMapping("/{id}")
    public Mono<Practica> findById(@PathVariable Long id) {
        return servicePort.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Practica> create(@RequestBody Practica practica) {
        return servicePort.create(practica);
    }

    @PutMapping("/{id}")
    public Mono<Practica> update(@PathVariable Long id, @RequestBody Practica practica) {
        return servicePort.update(id, practica);
    }

    @PatchMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> logicalDelete(@PathVariable Long id) {
        return servicePort.logicalDelete(id);
    }

    @PatchMapping("/{id}/restore")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> restore(@PathVariable Long id) {
        return servicePort.restore(id);
    }
}