package Concurrente.Semaforos.ExamenSupermercado.scala_supermercado

class SupermercadoMonitores extends Supermercado {
  private val permanente: Cajero = new Cajero(this, true) // creates the first cashier (permanent)
  permanente.start()

  override def fin(): Unit = {
    // TODO: Implement
  }

  override def nuevoCliente(id: Int): Unit = {
    // TODO: Implement
  }

  override def permanenteAtiendeCliente(id: Int): Boolean = {
    // TODO: Implement
    false // temporary return value
  }

  override def ocasionalAtiendeCliente(id: Int): Boolean = {
    // TODO: Implement
    false // temporary return value
  }
}
