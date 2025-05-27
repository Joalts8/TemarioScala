package Concurrente.Monitores.Practica7

import java.util.concurrent.locks.ReentrantLock
import scala.util.Random

class Bandeja(R:Int){
  private var raciones = 0
  private val l = new ReentrantLock(true)
  private val ctarta=l.newCondition()
  private val cnueva=l.newCondition()

  def quieroRacion(id:Int)= {
    l.lock()
    try {
      while(raciones==0)ctarta.await()
      raciones-=1
      log(s"Niño $id ha cogido una ración. Quedan $raciones")
      if(raciones==0)cnueva.signal()
    } finally {
      l.unlock()
    }
  }
  def tarta()= {
    l.lock()
    try {
      while (raciones>0)cnueva.await()
      raciones=R
      log(s"El pastelero pone una nueva tarta. Raciones= $R")
      ctarta.signalAll()
    } finally {
      l.unlock()
    }
  }
}

object Ejercicio6 {
  def main(args:Array[String]):Unit = {
    val R = 5
    val N = 10
    val bandeja = new Bandeja(R)
    var niño = new Array[Thread](N)
    for (i<-niño.indices)
      niño(i) = thread{
        while (true){
          Thread.sleep(Random.nextInt(500))
          bandeja.quieroRacion(i)
        }
      }
    val pastelero = thread{
      while (true){
        Thread.sleep(Random.nextInt(100))
        bandeja.tarta()
      }
    }
  }


}
