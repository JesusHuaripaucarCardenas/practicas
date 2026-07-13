package hct.huaripaucar.jesus.cliente.service;

import hct.huaripaucar.jesus.cliente.model.Cliente;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClienteService {

    Flux<Cliente> listar();

    Mono<Cliente> obtener(Long id);

    Mono<Cliente> crear(Cliente cliente);

    Mono<Cliente> actualizar(Long id, Cliente cambios);

    Mono<Cliente> eliminar(Long id);

    Mono<Cliente> restaurar(Long id);
}
