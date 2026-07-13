package hct.huaripaucar.jesus.vehiculo.service.impl;

import hct.huaripaucar.jesus.vehiculo.model.Vehiculo;
import hct.huaripaucar.jesus.vehiculo.repository.VehiculoRepository;
import hct.huaripaucar.jesus.vehiculo.service.VehiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class VehiculoServiceImpl implements VehiculoService {

    private final VehiculoRepository repository;

    @Override
    public Flux<Vehiculo> listar() {
        return repository.findAll();
    }

    @Override
    public Mono<Vehiculo> obtener(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Vehiculo no encontrado: " + id)));
    }

    @Override
    public Mono<Vehiculo> crear(Vehiculo vehiculo) {
        vehiculo.setId(null);
        return repository.save(vehiculo);
    }

    @Override
    public Mono<Vehiculo> actualizar(Long id, Vehiculo cambios) {
        return obtener(id).flatMap(actual -> {
            actual.setPlaca(cambios.getPlaca());
            actual.setMarca(cambios.getMarca());
            actual.setModelo(cambios.getModelo());
            actual.setAnio(cambios.getAnio());
            actual.setColor(cambios.getColor());
            actual.setPrecioPorDia(cambios.getPrecioPorDia());
            actual.setEstado(cambios.getEstado());
            return repository.save(actual);
        });
    }

    @Override
    public Mono<Vehiculo> eliminar(Long id) {
        return obtener(id).flatMap(v -> {
            v.setEstado("ELIMINADO");
            return repository.save(v);
        });
    }
    @Override
    public Mono<Vehiculo> cambiarEstado(Long id, String estado) {
        return obtener(id).flatMap(v -> {
            v.setEstado(estado);
            return repository.save(v);
        });
    }

    @Override
    public Mono<Vehiculo> restaurar(Long id) {
        return obtener(id).flatMap(v -> {
            v.setEstado("DISPONIBLE");
            return repository.save(v);
        });
    }
}
