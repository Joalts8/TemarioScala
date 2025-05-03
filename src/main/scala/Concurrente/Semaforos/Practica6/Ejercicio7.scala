package Concurrente.Semaforos.Practica6

import java.util.concurrent.*
import scala.util.Random

class Nido(B: Int) {
  private val mutex = new Semaphore(1) //Exclusion mutua
  private val bebe = new Semaphore(0)
  private val padre = new Semaphore(1)
  // CS-bebé i: no puede coger un bichito del plato si está vacío
  // CS-papá/mamá: no puede dejar un bichito en el plato si está lleno
  private var plato = 0

  def cojoBichito(i: Int) = {
    bebe.acquire()
    mutex.acquire()
    plato-=1
    log(s"Bebé $i coge un bichito. Quedan $plato bichitos")
    if(plato>0) bebe.release()
    if (plato==B-1) padre.release()
    mutex.release()
  }

  def pongoBichito(i: Int) = {
    padre.acquire()
    mutex.acquire()
    plato+=1
    log(s"Papá $i pone un bichito. Quedan $plato bichitos")
    if(plato<B) padre.release()
    if (plato==1)bebe.release()
    mutex.release()
  }
}


object Ejercicio7 {
  def main(args: Array[String]): Unit =
    val N = 10
    val nido = new Nido(5)
    val bebe = new Array[Thread](N)
    for (i <- bebe.indices)
      bebe(i) = thread {
        while (true)
          nido.cojoBichito(i)
          Thread.sleep(Random.nextInt(600))
      }
    val papa = new Array[Thread](2)
    for (i <- papa.indices)
      papa(i) = thread {
        while (true)
          Thread.sleep(Random.nextInt(100))
          nido.pongoBichito(i)
      }
}
