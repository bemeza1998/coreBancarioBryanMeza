package ec.edu.espe.arquitectura.sistema1.dao;

import ec.edu.espe.arquitectura.sistema1.model.Cuenta;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CuentaRepository extends MongoRepository<Cuenta, String> {

  Optional<Cuenta> findByCodigoInterno(String idCliente);
}
