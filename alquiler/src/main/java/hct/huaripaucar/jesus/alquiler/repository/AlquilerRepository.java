package hct.huaripaucar.jesus.alquiler.repository;

import hct.huaripaucar.jesus.alquiler.model.Alquiler;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface AlquilerRepository extends ReactiveCrudRepository<Alquiler, Long> {
}
