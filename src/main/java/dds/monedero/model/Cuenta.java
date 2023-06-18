package dds.monedero.model;

import dds.monedero.exceptions.*;
import dds.monedero.model.movimiento.Deposito;
import dds.monedero.model.movimiento.Extraccion;
import dds.monedero.model.movimiento.Movimiento;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cuenta {

  private double saldo = 0;
  private List<Movimiento> movimientos = new ArrayList<>();

  public Cuenta() {
    saldo = 0;
  }

  public Cuenta(double montoInicial) {
    saldo = montoInicial;
  }

  public List<Movimiento> getMovimientos() {
    return movimientos;
  }

  public double getSaldo() {
    return saldo;
  }

  public void setSaldo(double saldo) {
    this.saldo = saldo;
  }

  public void depositar(double monto) {
    chequearMontoNegativo(monto);
    chequearCantidadDepositosDiarios();
    agregarMovimiento(new Deposito(LocalDate.now(), monto));
  }

  public void extraer(double monto) {
    chequearMontoNegativo(monto);
    chequearMontoExtraccionDisponible(monto);
    chequearLimiteExtraccionDiario(monto);
    agregarMovimiento(new Extraccion(LocalDate.now(), monto));
  }

  public void agregarMovimiento(Movimiento movimiento) {
    movimientos.add(movimiento);
    saldo += movimiento.signoMonto();
  }

  public void chequearMontoNegativo(Double monto){
    if (monto < 0) {
      throw new CuentaException(monto + ": el monto a ingresar debe ser un valor positivo");
    }
  }

  public void chequearCantidadDepositosDiarios() {
    if (movimientos.stream().filter(Movimiento::isDeposito).count() >= 3) {
      throw new CuentaException("Ya excedio los " + 3 + " depositos diarios");
    }
  }

  public void chequearMontoExtraccionDisponible(double monto) {
    if (saldo - monto < 0) {
      throw new CuentaException("No puede sacar mas de " + saldo + " $");
    }
  }

  public void chequearLimiteExtraccionDiario(double monto) {
    double limite = this.getMontoDeExtraccionPosibleHoy();
    if (monto > limite) {
      throw new CuentaException("No puede extraer mas de $ " + 1000
          + " diarios, límite: " + limite);
    }
  }

  public double getMontoDeExtraccionPosibleHoy() {
      return 1000 - getMontoExtraidoA(LocalDate.now());
    }

  public double getMontoExtraidoA(LocalDate fecha) {
    return getMovimientos().stream()
        .filter(movimiento -> !movimiento.isDeposito() && movimiento.getFecha().equals(fecha))
        .mapToDouble(Movimiento::getMonto)
        .sum();
  }
}
