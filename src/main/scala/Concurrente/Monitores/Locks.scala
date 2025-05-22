package Concurrente.Monitores

import java.util.concurrent.locks.*
import Concurrente.Introduccion.*

//Los locks permiten tener varios monitores en el mismo objeto, pero no funcionen implicitamente=> No se pone Sycroniced
//También se pueden usar condiciones como en VarsCondicion.scala pero se llaman await y signal/signalAll
//Usar siemptre Try para la SC; Finally para el unlock Catch si usa el thread interrup()-> :InterruptedException
object lectorEscritorLocks {
  private var nLectores = 0
  private var escribiendo = false
  private var nEscritores = 0

  private val l = new ReentrantLock(true) //Lock reentrante, el hilo puede blockear varias veces. true=Justo-> FIFO
  private val okLeer = l.newCondition()
  private val okEscribir = l.newCondition()

  def entraLector(id: Int) = {
    l.lock()
    try {
      while (escribiendo || nEscritores > 0) okLeer.await
      nLectores += 1
      log(s"Lector $id entra en la BD")
    } finally {
      l.unlock()
    }
  }

  def entraEscritor(id: Int) = {
    l.lock()
    try {
      nEscritores += 1
      while (escribiendo || nLectores > 0) okEscribir.await
      escribiendo = true
      log(s" Escritor $id entra en la BD")
    } finally {
      l.unlock()
    }
  }

  def saleLector(id: Int) = {
    l.lock()
    try {
      nLectores -= 1
      if (nLectores == 0) okEscribir.signal()
      log(s"Lector $id sale de la BD")
    } finally {
      l.unlock()
    }
  }

  def saleEscritor(id: Int) = {
    l.lock()
    try {
      nEscritores -= 1
      escribiendo = false
      if (nEscritores > 0) okEscribir.signal()
      else okLeer.signalAll()
      log(s"Escritor $id sale de la BD")
    } finally {
      l.unlock()
    }
  }
}