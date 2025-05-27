package Concurrente.Monitores.Practica7

import java.util.concurrent.locks.ReentrantLock
import scala.util.Random

object Barca{
  private var nIPhone = 0
  private var nAndroid = 0
  private var n=0
  private val l = new ReentrantLock(true)
  val cIphone=l.newCondition()
  val cAndriod=l.newCondition()
  val cpaseo=l.newCondition()
  val cbajar=l.newCondition()

  def paseoIphone(id:Int) =  {
    l.lock()
    try {
      while(nAndroid+nIPhone>=4) cbajar.await()
      while (nAndroid>=3 || (nIPhone==2 && nAndroid==1)) cIphone.await()
      nIPhone+=1
      n+=1
      log(s"Estudiante IPhone $id se sube a la barca. Hay: iphone=$nIPhone,android=$nAndroid ")
      if(n==4){
        log(s"Empieza el viaje....")
        Thread.sleep(Random.nextInt(200))
        log(s"fin del viaje....")
        n=0
        cpaseo.signalAll()
      }else{
        cpaseo.await()
      }
    } finally {
      l.unlock()
    }
    l.lock()
    try {
      nIPhone -= 1
      log(s"Estudiante IPhone $id se baja de la barca. Hay: iphone=$nIPhone,android=$nAndroid ")
      if(nIPhone<3)cAndriod.signalAll()
      cbajar.signal()
    } finally {
      l.unlock()
    }
  }

  def paseoAndroid(id:Int) =  {
    l.lock()
    try {
      while(nAndroid+nIPhone>=4) cbajar.await()
      while (nIPhone >= 3  || (nIPhone==1 && nAndroid==2)) cAndriod.await()
      nAndroid += 1
      n+=1
      log(s"Estudiante Android $id se sube a la barca. Hay: iphone=$nIPhone,android=$nAndroid ")
      if (n == 4) {
        log(s"Empieza el viaje....")
        Thread.sleep(Random.nextInt(200))
        log(s"fin del viaje....")
        n = 0
        cpaseo.signalAll()
      }else{
        cpaseo.await()
      }
    } finally {
      l.unlock()
    }
    l.lock()
    try {
      nAndroid -= 1
      log(s"Estudiante Android $id se baja de la barca. Hay: iphone=$nIPhone,android=$nAndroid ")
      if(nAndroid<3)cIphone.signalAll()
      cbajar.signal()
    } finally {
      l.unlock()
    }
  }
}
object Ejercicio5 {

  def main(args:Array[String]) = {
    val NPhones = 10
    val NAndroid = 10
    val iphone = new Array[Thread](NPhones)
    val android = new Array[Thread](NAndroid)
    for (i<-iphone.indices)
      iphone(i) = thread{
     //   while (true){
          Thread.sleep(Random.nextInt(400))
          Barca.paseoIphone(i)
        //    }
      }
    for (i <- android.indices)
      android(i) = thread {
     //   while (true) {
          Thread.sleep(Random.nextInt(400))
          Barca.paseoAndroid(i)
     //   }
      }
  }
}
