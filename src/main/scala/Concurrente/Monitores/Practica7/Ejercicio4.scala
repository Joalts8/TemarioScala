package Concurrente.Monitores.Practica7

import java.util.concurrent.locks.ReentrantLock
import scala.util.Random

class Coche(C:Int) extends Thread{
  private val l = new ReentrantLock(true)
  private val llenar= l.newCondition()
  private val bajar= l.newCondition()
  private val paseo= l.newCondition()
  private var numPas = 0
  private var hopOff = false

  def nuevoPaseo(id:Int)= {
    //el pasajero id  quiere dar un paseo en la montaña rusa
    l.lock()
    try {
      while (numPas==C || hopOff)paseo.await()
      numPas+=1
      log(s"El pasajero $id se sube al coche. Hay $numPas pasajeros.")
      if(numPas==C) llenar.signal()
      bajar.await()
      numPas-=1
      log(s"El pasajero $id se baja del coche. Hay $numPas pasajeros.")
      if(numPas==0){
        hopOff = false
        paseo.signalAll()
      }
    } finally {
      l.unlock()
    }



  }

  def esperaLleno =  {
    //el coche espera a que se llene para dar un paseo
    l.lock()
    try {
      while (numPas<C)llenar.await()
      log(s"        Coche lleno!!! empieza el viaje....")
    } finally {
      l.unlock()
    }

  }

  def finViaje =  {
    //el coche indica que se ha terminado el viaje
    l.lock()
    try {
      log(s"        Fin del viaje... :-(")
      hopOff = true
      bajar.signalAll()
    } finally {
      l.unlock()
    }
  }

  override def run = {
    while (true){
      esperaLleno
      Thread.sleep(Random.nextInt(Random.nextInt(500))) //el coche da una vuelta
      finViaje
    }
  }
}
object Ejercicio4 {
  def main(args:Array[String])= {
    val coche = new Coche(5)
    val pasajero = new Array[Thread](20)
    coche.start()
    for (i <- 0 until pasajero.length)
      pasajero(i) = thread {
        //     while (true)
        Thread.sleep(Random.nextInt(500))
        coche.nuevoPaseo(i)
      }
  }
}
