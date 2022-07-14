package ec.edu.espe.arquitectura.sistema1.dao;

import ec.edu.espe.arquitectura.sistema1.model.Cliente;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClienteRepository extends MongoRepository<Cliente, String> {
  Optional<Cliente> findByCedula(String cedula);
}
