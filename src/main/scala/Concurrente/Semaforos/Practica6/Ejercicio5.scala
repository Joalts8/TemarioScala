package Concurrente.Semaforos.Practica6

import java.util.concurrent.*
import scala.util.Random

object gestorAgua {
  private val mutex = new Semaphore(1) //Exclusion mutua
  private val HFormar = new Semaphore(1) //Exclusion mutua
  private val HEsperar = new Semaphore(0) //Exclusion mutua
  private val OFormar = new Semaphore(1) //Exclusion mutua
  private val OEsperar = new Semaphore(0) //Exclusion mutua
  private var H=0

  def oxigeno(id: Int) = {
    OFormar.acquire()
    log(s"Oxígeno $id quiere formar una molécula")
    OEsperar.acquire()
    log(s"      Molécula formada!!!")
    mutex.acquire()
    H=0
    mutex.release()
    OFormar.release()
    HFormar.release()
  }

  def hidrogeno(id: Int) = {
    HFormar.acquire()
    log(s"Hidrógeno $id quiere formar una molécula")
    mutex.acquire()
    H+=1
    if(H<2) HFormar.release()
    mutex.release()
    if (H==2) OEsperar.release()
  }
}


object Ejercicio5 {
  def main(args:Array[String]) =
    val N = 5
    val hidrogeno = new Array[Thread](2*N)
    for (i<-0 until hidrogeno.length)
      hidrogeno(i) = thread{
        Thread.sleep(Random.nextInt(500))
        gestorAgua.hidrogeno(i)
      }
    val oxigeno = new Array[Thread](N)
    for(i <- 0 until oxigeno.length)
      oxigeno(i) = thread {
        Thread.sleep(Random.nextInt(500))
        gestorAgua.oxigeno(i)
      }
    hidrogeno.foreach(_.join())
    oxigeno.foreach(_.join())
    log("Fin del programa")
}
