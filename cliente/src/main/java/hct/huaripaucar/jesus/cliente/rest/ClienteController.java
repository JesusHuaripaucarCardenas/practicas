package hct.huaripaucar.jesus.cliente.rest;

import hct.huaripaucar.jesus.cliente.model.Cliente;
import hct.huaripaucar.jesus.cliente.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ClienteController {

    private final ClienteService service;

    @GetMapping
    public Flux<Cliente> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Mono<Cliente> obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Cliente> crear(@Valid @RequestBody Cliente cliente) {
        return service.crear(cliente);
    }

    @PutMapping("/{id}")
    public Mono<Cliente> actualizar(@PathVariable Long id, @Valid @RequestBody Cliente cliente) {
        return service.actualizar(id, cliente);
    }

    @PatchMapping("/{id}/eliminar")
    public Mono<Cliente> eliminar(@PathVariable Long id) {
        return service.eliminar(id);
    }

    @PatchMapping("/{id}/restaurar")
    public Mono<Cliente> restaurar(@PathVariable Long id) {
        return service.restaurar(id);
    }
}
