package hct.huaripaucar.jesus.vehiculo.rest;

import hct.huaripaucar.jesus.vehiculo.model.Vehiculo;
import hct.huaripaucar.jesus.vehiculo.rest.dto.EstadoRequest;
import hct.huaripaucar.jesus.vehiculo.service.VehiculoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/vehiculos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class VehiculoController {

    private final VehiculoService service;

    @GetMapping
    public Flux<Vehiculo> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Mono<Vehiculo> obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Vehiculo> crear(@Valid @RequestBody Vehiculo vehiculo) {
        return service.crear(vehiculo);
    }

    @PutMapping("/{id}")
    public Mono<Vehiculo> actualizar(@PathVariable Long id, @Valid @RequestBody Vehiculo vehiculo) {
        return service.actualizar(id, vehiculo);
    }

    @PatchMapping("/{id}/eliminar")
    public Mono<Vehiculo> eliminar(@PathVariable Long id) {
        return service.eliminar(id);
    }

    @PatchMapping("/{id}/restaurar")
    public Mono<Vehiculo> restaurar(@PathVariable Long id) {
        return service.restaurar(id);
    }
    @PutMapping("/{id}/estado")
    public Mono<Vehiculo> cambiarEstado(@PathVariable Long id, @RequestBody EstadoRequest request) {
        return service.cambiarEstado(id, request.getEstado());
    }
}
