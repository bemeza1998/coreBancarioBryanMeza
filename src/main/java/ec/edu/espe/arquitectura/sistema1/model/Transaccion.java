package ec.edu.espe.arquitectura.sistema1.model;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "transaccion")
@TypeAlias("transaccion")
@Data
@Builder
public class Transaccion {

  @Id private String id;

  @Indexed(name = "idxu_transaccion_codigoInterno", unique = true)
  private String codigoInterno;

  private Date fecha;

  private String cuentaOrigen;

  private String cuentaDestino;

  private BigDecimal valor;

  private String estado;
}
