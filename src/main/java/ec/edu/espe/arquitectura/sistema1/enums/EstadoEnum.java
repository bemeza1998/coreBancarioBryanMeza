package ec.edu.espe.arquitectura.sistema1.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum EstadoEnum {
  EJECUTADO("EJE", "Ejecutado"),
  BLOQUEADO("BLO", "Bloqueado");

  private final String value;
  private final String text;
}
