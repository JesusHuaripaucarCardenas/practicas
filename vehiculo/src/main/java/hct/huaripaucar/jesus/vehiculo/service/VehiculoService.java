package hct.huaripaucar.jesus.vehiculo.service;

import hct.huaripaucar.jesus.vehiculo.model.Vehiculo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VehiculoService {

    Flux<Vehiculo> listar();

    Mono<Vehiculo> obtener(Long id);

    Mono<Vehiculo> crear(Vehiculo vehiculo);

    Mono<Vehiculo> actualizar(Long id, Vehiculo cambios);

    Mono<Vehiculo> eliminar(Long id);

    Mono<Vehiculo> restaurar(Long id);
    
    Mono<Vehiculo> cambiarEstado(Long id, String estado);
}
