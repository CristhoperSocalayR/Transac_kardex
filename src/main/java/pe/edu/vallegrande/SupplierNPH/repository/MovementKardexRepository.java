package pe.edu.vallegrande.SupplierNPH.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.SupplierNPH.model.MovementKardex;

@Repository
public interface MovementKardexRepository extends ReactiveCrudRepository<MovementKardex, Long> {
    // Puedes agregar métodos adicionales si es necesario (ej. búsquedas personalizadas)
}
