package Concurrente.Monitores.Practica7

import scala.collection.mutable.ListBuffer
import scala.util.Random
import java .util.concurrent.locks.*

class Recursos(rec:Int) {
  private val l= new ReentrantLock(true)
  private var numRec = rec
  private var procesos=0
  private val cBlock=l.newCondition()
  private var list=new ListBuffer[Int]
  
  def pidoRecursos(id:Int,num:Int) =  {
    l.lock()
    try {
      procesos+=1
      list.append(id)
      log(s"Proceso $id pide $num recursos. Esperando $procesos")
      while(list(0)!=id || num>numRec) cBlock.await()
      numRec-=num
      list=list.tail
      procesos-=1
      log(s"Proceso $id coge $num recursos. Quedan $numRec")
    }finally{
      l.unlock()
    }
  }

  def libRecursos(id:Int,num:Int) =  {
    //proceso id devuelve num recursos
    l.lock()
    try {
      numRec+=num
      log(s"Proceso $id devuelve $num recursos. Quedan $numRec")
    } finally {
      cBlock.signalAll()
      l.unlock()
    }

  }
}
object Ejercicio2 {

  def main(args:Array[String]):Unit = {
    val rec = 5
    val numProc = 10
    val recursos = new Recursos(rec)
    val proceso = new Array[Thread](numProc)
    for (i<-proceso.indices)
      proceso(i) = thread{
        while (true){
          val r = Random.nextInt(rec)+1
          recursos.pidoRecursos(i,r)
          Thread.sleep(Random.nextInt(300))
          recursos.libRecursos(i,r)
        }
      }
  }
}
