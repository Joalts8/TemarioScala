package Concurrente.Semaforos.Practica6

import java.util.concurrent.*
import scala.util.Random

object mediciones {
  var sensores= Array(new Semaphore(1),new Semaphore(1),new Semaphore(1))
  @volatile var almacenado=0
  val almacenar= new Semaphore(1)
  val Trabajador= new Semaphore(0)


  def nuevaMedicion(id: Int) = {
    sensores(id).acquire()
    
    almacenar.acquire()
    log(s"Sensor $id almacena su medición" )
    almacenado+=1
    if(almacenado==sensores.length) Trabajador.release()
    almacenar.release()
  }

  def leerMediciones() = {
    Trabajador.acquire()
    log(s"El trabajador recoge las mediciones")
    almacenado=0
  }

  def finTarea() = {
    log(s"El trabajador ha terminado sus tareas")
    for (i<- 0 until sensores.length){
      sensores(i).release()
    }
  }
}

object Ejercicio1 {
  def main(args: Array[String]) =
    val sensor = new Array[Thread](3)

    for (i <- 0 until sensor.length)
      sensor(i) = thread {
        while (true)
          Thread.sleep(Random.nextInt(100)) // midiendo
          mediciones.nuevaMedicion(i)
      }

    val trabajador = thread {
      while (true)
        mediciones.leerMediciones()
        Thread.sleep(Random.nextInt(100)) // realizando la tarea
        mediciones.finTarea()
    }
}
