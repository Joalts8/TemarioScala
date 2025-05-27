package Concurrente.Monitores.Practica7

import java.util.concurrent.locks.ReentrantLock
import scala.util.Random

object Barca{
  private var nIPhone = 0
  private var nAndroid = 0
  private val l = new ReentrantLock(true)
  val cIphone=l.newCondition()
  val cAndriod=l.newCondition()

  def paseoIphone(id:Int) =  {
    l.lock()
    try {
      while (nAndroid>=3) cIphone.await()
      nIPhone+=1
      log(s"Estudiante IPhone $id se sube a la barca. Hay: iphone=$nIPhone,android=$nAndroid ")
    } finally {
      l.unlock()
    }
    
      //log(s"Empieza el viaje....")
      //Thread.sleep(Random.nextInt(200))
      //log(s"fin del viaje....")

    l.lock()
    try {
      nIPhone -= 1
      log(s"Estudiante IPhone $id se baja de la barca. Hay: iphone=$nIPhone,android=$nAndroid ")
    } finally {
      l.unlock()
    }
  }

  def paseoAndroid(id:Int) =  {
    l.lock()
    try {
      while (nIPhone >= 3) cAndriod.await()
      nAndroid += 1
      log(s"Estudiante IPhone $id se sube a la barca. Hay: iphone=$nIPhone,android=$nAndroid ")
    } finally {
      l.unlock()
    }
    log(s"Estudiante Android $id se sube a la barca. Hay: iphone=$nIPhone,android=$nAndroid ")
    
      //log(s"Empieza el viaje....")
      //Thread.sleep(Random.nextInt(200))
      //log(s"fin del viaje....")

    l.lock()
    try {
      nAndroid -= 1
      log(s"Estudiante Android $id se baja de la barca. Hay: iphone=$nIPhone,android=$nAndroid ")
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
