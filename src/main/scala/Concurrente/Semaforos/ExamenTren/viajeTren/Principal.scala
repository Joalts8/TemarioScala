package Concurrente.Semaforos.ExamenTren.viajeTren

object Principal {
  def main(args: Array[String]): Unit = {
    val tren = new Tren()
    val m = new Maquinista(tren)
    val pas = new Array[Pasajero](20)

    for (i <- 0 until pas.length)
      pas(i) = new Pasajero(tren, i)

    m.start()
    for (i <- 0 until pas.length)
      pas(i).start()
  }
}