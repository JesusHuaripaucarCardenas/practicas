package hct.huaripaucar.jesus.cliente.repository;

import hct.huaripaucar.jesus.cliente.model.Cliente;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ClienteRepository extends ReactiveCrudRepository<Cliente, Long> {
}
