package hct.huaripaucar.jesus.alquiler.service;

import hct.huaripaucar.jesus.alquiler.model.Alquiler;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AlquilerService {

    Flux<Alquiler> listar();

    Mono<Alquiler> obtener(Long id);

    Mono<Alquiler> crear(Alquiler alquiler);

    Mono<Alquiler> actualizar(Long id, Alquiler cambios);

    Mono<Alquiler> eliminar(Long id);

    Mono<Alquiler> restaurar(Long id);
}
