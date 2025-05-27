package Concurrente.Monitores

import java.util.concurrent.locks.*
import Concurrente.Introduccion.*

//Los locks permiten tener varios monitores en el mismo objeto, pero no funcionen implicitamente=> No se pone Sycroniced
//También se pueden usar condiciones como en VarsCondicion.scala pero se llaman await y signal/signalAll
//Usar siemptre Try para la SC; Finally para el unlock Catch si usa el thread interrup()-> :InterruptedException
/*
* l.lock()
    try {

    } finally {
      l.unlock()
    }
* */

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

//Ejemplo con la barbería donde se usan mas condiciones y bools para comprobar en vez int
object barberia {
  private val l = new ReentrantLock(true)
  private var bLibre = false // Indica si la silla del barbero está libre para sentarse
  private val cbLibre = l.newCondition()
  private var sOcupada = false // Indica que un cliente se ha sentado y está esperando pelo
  private val csOcupada = l.newCondition()
  private var pAbierta = false // Indica que el barbero ha terminado de pelar
  private val cpAbierta = l.newCondition()
  // pAbierta   coordinar el reinicio de ciclo
  private val cciclo = l.newCondition()

  def pelar(id: Int) = {
    l.lock()
    try {
      //1) Esperar a que la silla del barbero esté libre
      while (!bLibre) cbLibre.await()
      bLibre = false
      // 2) Avisar que la silla ya está ocupada
      sOcupada = true
      csOcupada.signal()
      log(s"El cliente $id se sienta en la silla")
      // 3) Esperar a que el barbero termine de pelar
      while (!pAbierta) cpAbierta.await()
      log(s"El cliente $id se marcha")
      // 4) Cerrar el paso y despertar al barbero para el siguiente ciclo
      pAbierta = false
      cciclo.signal()

    } finally {
      l.unlock()
    }
  }

  def siguiente = {
    l.lock()
    try {
      // 1) Avisar que la silla está libre
      bLibre = true
      cbLibre.signal()
      // 2) Esperar a que un cliente ocupe la silla
      while (!sOcupada) csOcupada.await()
      sOcupada = false
      log(s"El barbero pela a un cliente")
    } finally {
      l.unlock()
    }
  }

  def finPelar() = {
    l.lock()
    try {
      // 1) Avisar al cliente que ya ha terminado
      pAbierta = true
      cpAbierta.signal()
      // 2) Esperar a que el cliente salga y restablezca pAbierta = false
      while (pAbierta) cciclo.await()
    } finally {
      l.unlock()
    }
  }
}