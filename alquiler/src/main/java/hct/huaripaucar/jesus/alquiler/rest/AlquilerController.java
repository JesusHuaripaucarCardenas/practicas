package hct.huaripaucar.jesus.alquiler.rest;

import hct.huaripaucar.jesus.alquiler.model.Alquiler;
import hct.huaripaucar.jesus.alquiler.service.AlquilerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/alquileres")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AlquilerController {

    private final AlquilerService service;

    @GetMapping
    public Flux<Alquiler> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Mono<Alquiler> obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Alquiler> crear(@Valid @RequestBody Alquiler alquiler) {
        return service.crear(alquiler);
    }

    @PutMapping("/{id}")
    public Mono<Alquiler> actualizar(@PathVariable Long id, @Valid @RequestBody Alquiler alquiler) {
        return service.actualizar(id, alquiler);
    }

    @PatchMapping("/{id}/eliminar")
    public Mono<Alquiler> eliminar(@PathVariable Long id) {
        return service.eliminar(id);
    }

    @PatchMapping("/{id}/restaurar")
    public Mono<Alquiler> restaurar(@PathVariable Long id) {
        return service.restaurar(id);
    }
}
