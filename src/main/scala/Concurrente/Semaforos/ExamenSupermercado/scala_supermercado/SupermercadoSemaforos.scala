package Concurrente.Semaforos.ExamenSupermercado.scala_supermercado

import java.util.concurrent.Semaphore

class SupermercadoSemaforos extends Supermercado {
  private val permanente: Cajero = new Cajero(this, true) // creates the first cashier (permanent)
  var cerrado = false
  val mutex = new Semaphore(1)
  val mutex2 = new Semaphore(1)
  val cajeros = new Semaphore(0)
  val mutexCajeros = scala.collection.mutable.ListBuffer[Semaphore]().empty
  var nClientes = 0
  var nCajeros = 0
  permanente.start()

  override def fin(): Unit = {
    mutex.acquire()
    cerrado = true
    System.out.println("----Supermercado cerrado!!!----")
    mutex.release()
  }

  override def nuevoCliente(id: Int): Unit = {
    mutex.acquire()
    if(cerrado) {
      mutex.release()
      return
    }
    mutex.release()
    mutex2.acquire()
    nClientes += 1
    System.out.println(s"Llega cliente  $id. Hay $nClientes")
    if(nClientes > 3*Cajero.numCajeros()) {
      val cajero = new Cajero(this, false)
      val mutexCajero = new Semaphore(0)
      mutexCajeros += mutexCajero
      nCajeros += 1
      System.out.println(s"---Se crea un cajero nuevo ${cajero.cajeroId()}---")
      cajero.start()
    }
    mutex2.release()
    cajeros.release()
  }

  override def permanenteAtiendeCliente(id: Int): Boolean = {
    mutex.acquire()
    mutex2.acquire()
    if(cerrado && nClientes == 0) {
      System.out.println("Cajero permanente termina: 0")
      mutex2.release()
      mutex.release()
      return false
    }
    mutex2.release()
    mutex.release()

    mutex2.acquire()
    if (nClientes == 0) {
      System.out.println("---Cajero permanente espera---")
    }
    mutex2.release()
    cajeros.acquire()
    mutex2.acquire()
    nClientes -= 1
    System.out.println(s"Cajero permanente atiende a un cliente. Quedan $nClientes")
    mutex2.release()
    true
  }

  override def ocasionalAtiendeCliente(id: Int): Boolean = {

    mutex2.acquire()
    if (nClientes == 0) {
      System.out.println(s"---No hay clientes. Cajero $id termina: $nClientes---")
      mutex2.release()
      return false
    }
    mutex2.release()
    cajeros.acquire()
    mutex2.acquire()
    nClientes -= 1
    System.out.println(s"Cajero $id atiende a un cliente: $nClientes")
    mutex2.release()
    true
  }
}
