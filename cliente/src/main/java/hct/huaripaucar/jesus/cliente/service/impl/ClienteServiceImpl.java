package hct.huaripaucar.jesus.cliente.service.impl;

import hct.huaripaucar.jesus.cliente.model.Cliente;
import hct.huaripaucar.jesus.cliente.repository.ClienteRepository;
import hct.huaripaucar.jesus.cliente.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository repository;

    @Override
    public Flux<Cliente> listar() {
        return repository.findAll();
    }

    @Override
    public Mono<Cliente> obtener(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Cliente no encontrado: " + id)));
    }

    @Override
    public Mono<Cliente> crear(Cliente cliente) {
        cliente.setId(null);
        return repository.save(cliente);
    }

    @Override
    public Mono<Cliente> actualizar(Long id, Cliente cambios) {
        return obtener(id).flatMap(actual -> {
            actual.setDni(cambios.getDni());
            actual.setNombres(cambios.getNombres());
            actual.setApellidos(cambios.getApellidos());
            actual.setCelular(cambios.getCelular());
            actual.setCorreo(cambios.getCorreo());
            actual.setLicencia(cambios.getLicencia());
            actual.setEstado(cambios.getEstado());
            return repository.save(actual);
        });
    }

    @Override
    public Mono<Cliente> eliminar(Long id) {
        return obtener(id).flatMap(c -> {
            c.setEstado("INACTIVO");
            return repository.save(c);
        });
    }

    @Override
    public Mono<Cliente> restaurar(Long id) {
        return obtener(id).flatMap(c -> {
            c.setEstado("ACTIVO");
            return repository.save(c);
        });
    }
}
