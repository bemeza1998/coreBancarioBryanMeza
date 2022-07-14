package ec.edu.espe.arquitectura.sistema1.model;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cuenta")
@TypeAlias("cuenta")
@Data
@Builder
public class Cuenta {

  @Id private String id;

  private String idCliente;

  @Indexed(name = "idxu_cuenta_codigoInterno", unique = true)
  private String codigoInterno;

  private BigDecimal saldo;

  private Date fechaUltimoMovimiento;

  private String estado;
}
