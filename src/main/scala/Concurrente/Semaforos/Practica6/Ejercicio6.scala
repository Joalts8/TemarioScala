package Concurrente.Semaforos.Practica6

import java.util.concurrent.*
import scala.util.Random

object mesa {
  var fumadores= Array.fill(3)(new Semaphore(0))
  val Agente = new Semaphore(1)
  private val mutex = new Semaphore(1) //Exclusion mutua

  private var ingr = -1 // el ingrediente que no está-- -1=mesa vacía, 0=no tabaco, 1=no papel, 2=no cerillas

  def quieroFumar(i: Int) = {
    fumadores(i).acquire()
    log(s"Fumador $i fuma")
  }

  def finFumar(i: Int) = {
    log(s"Fumador $i termina de fumar")
    Agente.release()
  }

  def nuevosIngr(ingr: Int) = {
    Agente.acquire()
    log(s"El agente no pone ingrediente $ingr")
    fumadores(ingr).release()
  }
}

object Ejercicio6 {
  def main(args: Array[String]): Unit =
    val fumador = new Array[Thread](3)
    for (i <- fumador.indices)
      fumador(i) = thread {
        while (true)
          Thread.sleep(Random.nextInt(500))
          mesa.quieroFumar(i)
          Thread.sleep(Random.nextInt(200))
          mesa.finFumar(i)
      }
    val agente = thread {
      while (true)
        Thread.sleep(Random.nextInt(500))
        mesa.nuevosIngr(Random.nextInt(3))
    }
}
