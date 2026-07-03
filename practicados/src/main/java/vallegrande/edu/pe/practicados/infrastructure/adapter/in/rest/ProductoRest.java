package vallegrande.edu.pe.practicados.infrastructure.adapter.in.rest;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vallegrande.edu.pe.practicados.application.port.in.IProductoServicePort;
import vallegrande.edu.pe.practicados.domain.model.Producto;

@RestController
@RequestMapping("/api/producto")
@RequiredArgsConstructor
public class ProductoRest {

    private final IProductoServicePort servicePort;

    @GetMapping
    public Flux<Producto> findAll() {
        return servicePort.findAll();
    }

    @GetMapping("/active")
    public Flux<Producto> findAllActive() {
        return servicePort.findAllActive();
    }

    @GetMapping("/inactive")
    public Flux<Producto> findAllInactive() {
        return servicePort.findAllInactive();
    }

    @GetMapping("/{id}")
    public Mono<Producto> findById(@PathVariable String id) {
        return servicePort.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Producto> create(@RequestBody Producto producto) {
        return servicePort.create(producto);
    }

    @PutMapping("/{id}")
    public Mono<Producto> update(@PathVariable String id, @RequestBody Producto producto) {
        return servicePort.update(id, producto);
    }

    @PatchMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> logicalDelete(@PathVariable String id) {
        return servicePort.logicalDelete(id);
    }

    @PatchMapping("/{id}/restore")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> restore(@PathVariable String id) {
        return servicePort.restore(id);
    }
}


/**
 * PostgreSQL
 
package vallegrande.edu.pe.practicados.infrastructure.adapter.in.rest;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vallegrande.edu.pe.practicados.application.port.in.IProductoServicePort;
import vallegrande.edu.pe.practicados.domain.model.Producto;

@RestController
@RequestMapping("/api/producto")
@RequiredArgsConstructor
public class ProductoRest {

    private final IProductoServicePort servicePort;

    @GetMapping
    public Flux<Producto> findAll() {
        return servicePort.findAll();
    }

    @GetMapping("/active")
    public Flux<Producto> findAllActive() {
        return servicePort.findAllActive();
    }

    @GetMapping("/inactive")
    public Flux<Producto> findAllInactive() {
        return servicePort.findAllInactive();
    }

    @GetMapping("/{id}")
    public Mono<Producto> findById(@PathVariable Long id) {
        return servicePort.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Producto> create(@RequestBody Producto producto) {
        return servicePort.create(producto);
    }

    @PutMapping("/{id}")
    public Mono<Producto> update(@PathVariable Long id, @RequestBody Producto producto) {
        return servicePort.update(id, producto);
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
*/