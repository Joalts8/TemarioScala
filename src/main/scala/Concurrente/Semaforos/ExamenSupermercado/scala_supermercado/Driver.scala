package Concurrente.Semaforos.ExamenSupermercado.scala_supermercado

import scala.util.Random

object Driver {
  def main(args: Array[String]): Unit = {
    val NC = 15
    val r = new Random()
    val mkt = new SupermercadoSemaforos() // Comment this line to test the monitor version
    // val mkt = new SupermercadoMonitores() // Uncomment this line to test the monitor version

    val c1 = Array.tabulate(NC)(i => new Cliente(mkt, i))
    c1.foreach { cliente =>
      Thread.sleep(r.nextInt(1500))
      cliente.start()
    }

    Thread.sleep(r.nextInt(1500))

    val c2 = Array.tabulate(NC)(i => new Cliente(mkt, i + 15))
    c2.foreach { cliente =>
      Thread.sleep(r.nextInt(1500))
      cliente.start()
    }

    Thread.sleep(2000)
    mkt.fin()
  }
}
