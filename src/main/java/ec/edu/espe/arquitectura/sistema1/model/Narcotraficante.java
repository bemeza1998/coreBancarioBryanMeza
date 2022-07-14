package ec.edu.espe.arquitectura.sistema1.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "narcotraficantes")
@TypeAlias("narcotraficantes")
@Data
@Builder
public class Narcotraficante {

  @Id private String id;

  private String nombreCompleto;

  private String sancionado;
}
