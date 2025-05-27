package Concurrente.Monitores.Practica7

import java.util.concurrent.locks.ReentrantLock
import scala.util.Random

object gestorAgua {
  private val l = new ReentrantLock(true)
  private val cH = l.newCondition()
  private val cO = l.newCondition()
  private var nH = 0
  private var nO = 0

  def hidrogeno(id: Int): Unit = {
    l.lock()
    try {
      while (nH == 2) cH.await()
      log(s"Hidrogeno $id quiere formar una molécula")
      nH += 1
      if (nH == 2 && nO == 1) {
        log("     Molécula formada!!!")
        nH = 0
        nO = 0
        cH.signalAll()
        cO.signalAll()
      } else {
        cH.await() // espera a que se forme la molécula
      }
    } finally {
      l.unlock()
    }
  }

  def oxigeno(id: Int): Unit = {
    l.lock()
    try {
      while (nO == 1) cO.await()
      log(s"Oxígeno $id quiere formar una molécula")
      nO += 1

      if (nH == 2 && nO == 1) {
        log("     Molécula formada!!!")
        nH = 0
        nO = 0
        cH.signalAll()
        cO.signalAll()
      } else {
        cO.await() // espera a que se forme la molécula
      }
    } finally {
      l.unlock()
    }
  }
}

object Ejercicio8 {
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
