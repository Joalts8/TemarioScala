package Concurrente.Monitores.Practica7

import java.util.concurrent.locks.ReentrantLock
import scala.util.Random

object Parejas{
  private val l= new ReentrantLock(true)
  private var hombre=false
  private var mujer=false
  private val cMujer=l.newCondition()
  private val cHombre=l.newCondition()
  private val esperar=l.newCondition()

  def llegaHombre(id:Int) = {
    l.lock()
    try{
      while(hombre) cHombre.await()
      hombre=true
      log(s"Hombre $id quiere formar pareja")
      if(!mujer) esperar.await() else{
        esperar.signalAll()
        log(s"Se ha formado una pareja!!!")
      }
      hombre = false
      cHombre.signal()
      mujer = false
      cMujer.signal()
    }finally {
      l.unlock()
    }
  }

  def llegaMujer(id: Int) =  {
    l.lock()
    try {
      while (mujer) cMujer.await()
      mujer = true
      log(s"Mujer $id quiere formar pareja")
      if(!hombre) esperar.await()else {
        esperar.signalAll()
        log(s"Se ha formado una pareja!!!")
      }
    } finally {
      l.unlock()
    }
  }
}
object Ejercicio3 {

  def main(args:Array[String]):Unit = {
    val NP = 10
    val mujer = new Array[Thread](NP)
    val hombre = new Array[Thread](NP)
    for (i<-mujer.indices)
      mujer(i) = thread{
        Parejas.llegaMujer(i)
      }
    for (i <- hombre.indices)
      hombre(i) = thread {
        Parejas.llegaHombre(i)
      }
  }

}
