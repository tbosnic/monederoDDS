package dds.monedero.model.movimiento;

import dds.monedero.model.Cuenta;

import java.time.LocalDate;

public class Extraccion extends Movimiento{

  public Extraccion(LocalDate fecha, double monto){
    super(fecha, monto);
  }

  @Override
  public boolean isDeposito() {
    return false;
  }

  public double signoMonto(){
    return (this.getMonto() * (-1));
  }
}
