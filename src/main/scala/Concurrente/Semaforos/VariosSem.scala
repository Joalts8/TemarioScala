package Concurrente.Semaforos

import java.util.concurrent.*
import Concurrente.Introduccion.*
import scala.util.Random

//Problema del Productor-Consumidor para el uso de varios Semaforos:

class Buffer(n: Int) {
  private val b = new Array[Int](n)
  private var i = 0 // índice del productor
  private var j = 0 // índice del consumidor

  private val mutex = new Semaphore(1)//Usado solo para q sea Exclusion mutua, de 1 en 1. Si se usa siempre despues de los otros
  private val hayEspacio = new Semaphore(n)//Usado para saber si el productor puede producir o no hay espacio
  private val hayDatos = new Semaphore(0)// Usado para conocer si hay elementos para consumir

  def almacena(dato: Int) = {
    //Preprotocolo
    hayEspacio.acquire()
    mutex.acquire()
    //Zona Critica
    b(i) = dato
    i = (i + 1) % n  // el buffer se utiliza de forma circular
    //Postprotocolo
    mutex.release()
    hayDatos.release()
  }
  def extrae(): Int = {
    hayDatos.acquire()
    mutex.acquire()
    val dato = b(j)
    j = (j + 1) % n // el buffer se utiliza de forma circular
    mutex.release()
    hayEspacio.release()
    dato
  }
}

@main def ProdCons = {
  val buffer = new Buffer(5)
  val prod = thread {
    for (i <- 0 until 100) {
      Thread.sleep(Random.nextInt(200))
      buffer.almacena(i)
    }
  }
  val cons = thread {
    for (i <- 0 until 100) {
      log(s"Consumidor: ${buffer.extrae()}")
      Thread.sleep(Random.nextInt(200))
    }
  }
}