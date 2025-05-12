package Concurrente.Semaforos.ExamenSupermercado.scala_supermercado

import java.util.concurrent.atomic.{AtomicBoolean, AtomicInteger}
import scala.util.Random

class Cajero(mkt: Supermercado, permanente: Boolean) extends Thread {
  private val rnd = new Random()
  


  private val id = Cajero.currentId.getAndIncrement()

  if (Cajero.algunoCreado && permanente)
    throw new RuntimeException("Solo el primer cajero puede ser permanente")
  if (!Cajero.algunoCreado && !permanente)
    throw new RuntimeException("El primer cajero tiene que ser permanente")

  Cajero.algunoCreado = true
  Cajero.cajerosCount.incrementAndGet()

  override def run(): Unit = {
    try {
      if (!permanente) {
        println(s"El nuevo cajero $id comienza a servir a un cliente.")
        Thread.sleep(500 + rnd.nextInt(400))
      }

      if (permanente) {
        while (mkt.permanenteAtiendeCliente(id)) {
          Thread.sleep(500 + rnd.nextInt(400))
        }
      } else {
        while (mkt.ocasionalAtiendeCliente(id)) {
          Thread.sleep(500 + rnd.nextInt(400))
        }
      }
    } catch {
      case e: InterruptedException => e.printStackTrace()
    }

    Cajero.cajerosCount.decrementAndGet()
  }


  def cajeroId(): Int = id
}

object Cajero {
  private var algunoCreado = false
  private val cajerosCount = new AtomicInteger(0)
  private val currentId = new AtomicInteger(0)

  def numCajeros(): Int = cajerosCount.get()
}
