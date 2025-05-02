package Concurrente.Semaforos.Practica6

import java.util.concurrent.*
import scala.util.Random
object aseo{
  val Cliente= new Semaphore(1)
  val EquipoLimpieza= new Semaphore(1)
  var numClientes=0
  val mutex= new Semaphore(1)

  def entraCliente(id:Int)={
    Cliente.acquire()
    mutex.acquire()
    numClientes+=1
    if (numClientes==1) EquipoLimpieza.acquire()
    log(s"Entra cliente $id. Hay $numClientes clientes.")
    mutex.release()
    Cliente.release()
  }
  def saleCliente(id:Int)={
    mutex.acquire()
    numClientes-=1
    if (numClientes==0) EquipoLimpieza.release()
    log(s"Sale cliente $id. Hay $numClientes clientes.")
    mutex.release()
  }
  def entraEquipoLimpieza ={
    mutex.acquire()
    Cliente.acquire()
    mutex.release()
    EquipoLimpieza.acquire()
    log(s"        Entra el equipo de limpieza.")
  }
  def saleEquipoLimpieza = {
    mutex.acquire()
    Cliente.release()
    mutex.release()
    EquipoLimpieza.release()
    log(s"        Sale el equipo de limpieza.")
  }
}

object Ejercicio3 {
  def main(args: Array[String]) = {
    val cliente = new Array[Thread](10)
    for (i <- 0 until cliente.length)
      cliente(i) = thread {
        while (true)
          Thread.sleep(Random.nextInt(500))
          aseo.entraCliente(i)
          Thread.sleep(Random.nextInt(50))
          aseo.saleCliente(i)
      }
    val equipoLimpieza = thread {
      while (true)
        Thread.sleep(Random.nextInt(500))
        aseo.entraEquipoLimpieza
        Thread.sleep(Random.nextInt(100))
        aseo.saleEquipoLimpieza
    }
  }
}
