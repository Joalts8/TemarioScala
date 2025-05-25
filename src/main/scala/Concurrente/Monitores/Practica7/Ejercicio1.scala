package Concurrente.Monitores.Practica7

import scala.util.Random
import java.util.concurrent.locks.*

class Buffer(ncons:Int,tam:Int){
  //ncons-número de consumidores
  //tam-tamaño del buffer
  private val l = new ReentrantLock(true)
  private val cVacio= l.newCondition()
  private val cLleno=l.newCondition()
  private var n=0
  private var i=0
  private val j = Array.fill(ncons)(0)
  private val consumido = Array.fill(tam)(0)
  private val buffer = new Array[Int](tam)
  
  def nuevoDato(dato:Int) = {
    l.lock()
    try {
      while(n==tam) cLleno.await()
      n+=1
      buffer(i) = dato
      i = (i + 1) % tam
      log(s"Productor almacena $dato: buffer=${buffer.mkString("[", ",", "]")}}")
    }finally {
      cVacio.signalAll()
      l.unlock()
    }
  }

  def extraerDato(id:Int):Int =  {
    l.lock()
    try {
      while (n == 0) cVacio.await()
      val aux = buffer(j(id))
      if(consumido(j(id))==ncons-1){
        buffer(j(id)) = 0
        consumido(j(id))=0
        n -= 1
        cLleno.signal()
      }else{
        consumido(j(id))+=1
      }
      j(id) = (j(id) + 1) % tam
      log(s"Consumidor $id lee:$aux buffer=${buffer.mkString("[", ",", "]")}")
      aux
    }finally {
      l.unlock()
    }
  }
}
object Ejercicio1 {

  def main(args:Array[String]):Unit = {
    val ncons = 4
    val tam = 3
    val nIter = 10
    val buffer  = new Buffer(ncons,tam)
    val consumidor = new Array[Thread](ncons)
    for (i<-consumidor.indices)
      consumidor(i) = thread{
        for (j<-0 until nIter)
          val dato = buffer.extraerDato(i)
          Thread.sleep(Random.nextInt(200))
      }
    val productor = thread{
      for (i<-0 until nIter)
        Thread.sleep(Random.nextInt(50))
        buffer.nuevoDato(i+1)
    }
  }

}
