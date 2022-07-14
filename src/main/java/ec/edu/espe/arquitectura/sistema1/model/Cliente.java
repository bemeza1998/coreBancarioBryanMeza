package ec.edu.espe.arquitectura.sistema1.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cliente")
@TypeAlias("cliente")
@Data
@Builder
public class Cliente {

  @Id private String id;

  @Indexed(name = "idxu_cliente_cedula", unique = true)
  private String cedula;

  private String nombreCompleto;

  private String estado;
}
