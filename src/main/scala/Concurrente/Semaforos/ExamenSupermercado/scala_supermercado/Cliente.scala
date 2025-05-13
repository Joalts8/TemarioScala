package Concurrente.Semaforos.ExamenSupermercado.scala_supermercado

import scala.util.Random

class Cliente(mkt: Supermercado, id: Int) extends Thread {
  private val r = new Random()

  override def run(): Unit = {
    for (_ <- 0 until 2) {
      try {
        Thread.sleep(r.nextInt(2000))
        mkt.nuevoCliente(id)
      } catch {
        case e: InterruptedException => e.printStackTrace()
      }
    }
  }
}
