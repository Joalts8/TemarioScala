package Concurrente.Monitores.Practica7

import scala.util.Random
import java.util.concurrent.locks.*

object Guarderia{
  private var nBebe  = 0
  private var nAdulto = 0
  //nBebe<=3*Adulto (3bebes,1adulto),(4bebes,2adulto)
  private val l= new ReentrantLock(true)
  private val cbebeIn=l.newCondition()
  private val cAdultoOut=l.newCondition()

  def entraBebe(id:Int) =  {
    l.lock()
    try {
      while (nBebe>=3*nAdulto) cbebeIn.await()
      nBebe+=1
      log(s"Ha llegado un bebé. Bebés=$nBebe, Adultos=$nAdulto")
    } finally {
      l.unlock()
    }
  }
  def saleBebe(id:Int) =  {
    l.lock()
    try {
      nBebe-=1
      log(s"Ha salido un bebé. Bebés=$nBebe, Adultos=$nAdulto")
      cAdultoOut.signalAll()
    } finally {
      l.unlock()
    }
  }
  def entraAdulto(id:Int) =  {
    l.lock()
    try {
      nAdulto+=1
      log(s"Ha llegado un adulto. Bebés=$nBebe, Adultos=$nAdulto")
      cbebeIn.signalAll()
    } finally {
      l.unlock()
    }
  }
  def saleAdulto(id:Int) =  {
    l.lock()
    try {
      while(nBebe>3*(nAdulto-1))cAdultoOut.await()
      nAdulto-=1
      log(s"Ha salido un adulto. Bebés=$nBebe, Adultos=$nAdulto")
    } finally {
      l.unlock()
    }
  }
}

object Ejercicio7 {
  def main(args:Array[String]):Unit={
    val NB = 15
    val NA = 5
    val bebe = new Array[Thread](NB)
    val adulto = new Array[Thread](NA)
    for (i <- bebe.indices)
      bebe(i) = thread {
        while (true) {
          Thread.sleep(Random.nextInt(700))
          Guarderia.entraBebe(i)
          Thread.sleep(Random.nextInt(500))
          Guarderia.saleBebe(i)
        }
      }
    for (i <- adulto.indices)
      adulto(i) = thread {
        while (true) {
          Thread.sleep(Random.nextInt(700))
          Guarderia.entraAdulto(i)
          Thread.sleep(Random.nextInt(500))
          Guarderia.saleAdulto(i)
        }
      }
  }

}
