package dds.monedero.model;

import dds.monedero.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MonederoTest {
  private Cuenta cuenta;

  @BeforeEach
  void init() {
    cuenta = new Cuenta();
  }

  @Test
  void SePuedeHacerUnDepositoEnUnaCuentaSiEsteCumpleLasCondiciones() {
    assertEquals(0, cuenta.getSaldo());
    cuenta.depositar( 1500);
    assertEquals(1500, cuenta.getSaldo());
  }

  @Test
  void NoSePuedeRealizarUnDepositoPorUnMontoNegativo() {
    assertThrows(CuentaException.class, () -> cuenta.depositar(-1500));
  }

  @Test
  void SePuedenHacerTresDepositosPorDia() {
    cuenta.depositar(1500);
    cuenta.depositar(456);
    cuenta.depositar(1900);
    assertEquals(3856, cuenta.getSaldo());
  }

  @Test
  void NoSePuedenHacerMasDeTresDepositosPorDia() {
    assertThrows(CuentaException.class, () -> {
      cuenta.depositar(1500);
      cuenta.depositar(1500);
      cuenta.depositar(1500);
      cuenta.depositar(1500);
    });
  }

  @Test
  void NoSePuedeExtraerUnMontoMayorAlSaldoDisponible() {
    assertThrows(CuentaException.class, () -> {
      cuenta.depositar(150);
      cuenta.extraer(500);
    });
  }

  @Test
  public void NoSePuedeExtraerMasDe1000PesosDiarios() {
    assertThrows(CuentaException.class, () -> {
      cuenta.depositar(1500);
      cuenta.extraer(1001);
    });
  }

  @Test
  public void NoSePuedeExtraerUnMontoNegativo() {
    assertThrows(CuentaException.class, () -> cuenta.extraer(-1500));
  }
}