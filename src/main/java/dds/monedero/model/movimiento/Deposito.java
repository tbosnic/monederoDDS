package dds.monedero.model.movimiento;

import dds.monedero.model.Cuenta;

import java.time.LocalDate;

public class Deposito extends Movimiento{

  public Deposito(LocalDate fecha, double monto){
    super(fecha, monto);
  }

  @Override
  public boolean isDeposito() {
    return true;
  }

  public double signoMonto(){
    return this.getMonto();
  }
}
