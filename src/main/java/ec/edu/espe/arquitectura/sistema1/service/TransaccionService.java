package ec.edu.espe.arquitectura.sistema1.service;

import ec.edu.espe.arquitectura.sistema1.dao.ClienteRepository;
import ec.edu.espe.arquitectura.sistema1.dao.CuentaRepository;
import ec.edu.espe.arquitectura.sistema1.enums.EstadoEnum;
import ec.edu.espe.arquitectura.sistema1.exception.TransferenciaException;
import ec.edu.espe.arquitectura.sistema1.model.Cliente;
import ec.edu.espe.arquitectura.sistema1.model.Cuenta;
import ec.edu.espe.arquitectura.sistema1.model.Narcotraficante;
import ec.edu.espe.arquitectura.sistema1.model.Transaccion;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TransaccionService {

  private static final String BASE_URL = "http://localhost:8081/narco";
  private final CuentaRepository cuentaRepository;
  private final ClienteRepository clienteRepository;
  private final RestTemplate restTemplate;

  public TransaccionService(
      CuentaRepository cuentaRepository, ClienteRepository clienteRepository) {
    this.restTemplate = new RestTemplate(getClientHttpRequestFactory());
    this.cuentaRepository = cuentaRepository;
    this.clienteRepository = clienteRepository;
  }

  public void transferencia(String origen, String destino, BigDecimal valor)
      throws TransferenciaException {

    Optional<Cuenta> cuentaOrPOT = this.cuentaRepository.findByCodigoInterno(origen);
    if (!cuentaOrPOT.isPresent()) {
      throw new TransferenciaException("La cuenta de origen no existe");
    }
    Cuenta cuentaOrigen = cuentaOrPOT.get();
    Optional<Cuenta> cuentaDesOPT = this.cuentaRepository.findByCodigoInterno(destino);
    if (!cuentaDesOPT.isPresent()) {
      throw new TransferenciaException("La cuenta de destino no existe");
    }
    Cuenta cuentaDestino = cuentaDesOPT.get();
    Optional<Cliente> clienteDestino =
        this.clienteRepository.findByCedula(cuentaDestino.getIdCliente());
    String estado = EstadoEnum.EJECUTADO.getValue();
    if (valor.compareTo(new BigDecimal(1000)) == 1) {
      List<Narcotraficante> listado = buscar(clienteDestino.get().getNombreCompleto());
      estado =
          listado.isEmpty() ? EstadoEnum.EJECUTADO.getValue() : EstadoEnum.BLOQUEADO.getValue();
    }
    if (cuentaOrigen.getSaldo().compareTo(valor) == -1) {
      throw new TransferenciaException("Saldo insuficiente");
    }
    Transaccion transaccion =
        Transaccion.builder()
            .codigoInterno(UUID.randomUUID().toString())
            .cuentaDestino(cuentaOrigen.getCodigoInterno())
            .cuentaOrigen(cuentaDestino.getCodigoInterno())
            .fecha(new Date())
            .estado(estado)
            .valor(valor)
            .build();

    cuentaOrigen.setSaldo(cuentaOrigen.getSaldo().subtract(valor));
    this.cuentaRepository.save(cuentaOrigen);
  }

  private List<Narcotraficante> buscar(String nombre) {
    ResponseEntity<Narcotraficante[]> response =
        this.restTemplate.getForEntity(BASE_URL, Narcotraficante[].class);
    Narcotraficante[] objectArray = response.getBody();

    assert objectArray != null;
    return Arrays.asList(objectArray);
  }

  private static ClientHttpRequestFactory getClientHttpRequestFactory() {
    HttpComponentsClientHttpRequestFactory clientHttpRequestFactory =
        new HttpComponentsClientHttpRequestFactory();
    int connectTimeout = 5000;
    int readTimeout = 5000;
    clientHttpRequestFactory.setConnectTimeout(connectTimeout);
    clientHttpRequestFactory.setReadTimeout(readTimeout);
    return clientHttpRequestFactory;
  }
}
