package Concurrente.Semaforos.Practica6

import java.util.concurrent.*
import scala.util.Random

class Coche(C: Int) extends Thread {
  val pasajeroSubir= new Semaphore(1)
  val pasajeroBajar= new Semaphore(0)
  val coche = new Semaphore(0)
  val mutex = new Semaphore(1)
  private var numPas = 0

  def nuevoPaseo(id: Int) = {
    pasajeroSubir.acquire()
    mutex.acquire()
    numPas+=1
    log(s"El pasajero $id se sube al coche. Hay $numPas pasajeros.")
    if(numPas<C) pasajeroSubir.release() else coche.release()
    mutex.release()

    pasajeroBajar.acquire()
    mutex.acquire()
    numPas-=1
    log(s"El pasajero $id se baja del coche. Hay $numPas pasajeros.")
    if(numPas>0) pasajeroBajar.release() else pasajeroSubir.release()
    mutex.release()
  }

  def esperaLleno = {
    coche.acquire()
    log(s"        Coche lleno!!! empieza el viaje....")
  }

  def finViaje = {
    log(s"        Fin del viaje... :-(")
    pasajeroBajar.release()
  }

  override def run = {
    while (true) {
      esperaLleno
      Thread.sleep(Random.nextInt(Random.nextInt(500))) // el coche da una vuelta
      finViaje
    }
  }
}

object Ejercicio4 {
  def main(args: Array[String]) =
    val coche = new Coche(5)
    val pasajero = new Array[Thread](12)
    coche.start()
    for (i <- 0 until pasajero.length)
      pasajero(i) = thread {
        while (true)
          Thread.sleep(Random.nextInt(500)) // el pasajero se da una vuelta por el parque
          coche.nuevoPaseo(i)
      }
}
