package hct.huaripaucar.jesus.vehiculo.repository;

import hct.huaripaucar.jesus.vehiculo.model.Vehiculo;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface VehiculoRepository extends ReactiveCrudRepository<Vehiculo, Long> {
    Mono<Boolean> existsByPlaca(String placa);
}
