package Concurrente.Semaforos.Practica6

import java.util.concurrent.*
import scala.util.Random

class Cadena(n: Int) {
  var empaquetador= Array.fill(3)(new Semaphore(0))
  @volatile var libre=n
  private val tipo = Array.fill(3)(0) // el buffer
  private var cuentaTotal = 0
  private val esperaCol = Semaphore(1) // CS- Colocalor*
  val mutex= new Semaphore(1)

  def retirarProducto(p: Int) = {
    empaquetador(p).acquire()
    mutex.acquire()
    log(s"Empaquetador $p retira un producto. Quedan ${tipo.mkString("[",",","]")}")
    cuentaTotal+=1
    log(s"Total de productos empaquetados $cuentaTotal")
    tipo(p)-=1
    libre+=1
    if (tipo(p) >0) empaquetador(p).release()
    if (libre==1) esperaCol.release()
    mutex.release()
  }

  def nuevoProducto(p:Int) = {
    esperaCol.acquire()
    mutex.acquire()
    log(s"Colocador pone un producto $p. Quedan ${tipo.mkString("[",",","]")}")
    tipo(p)+=1
    libre-=1
    if(tipo(p)==1)     empaquetador(p).release()
    if (libre>0)   esperaCol.release()
    mutex.release()}
}

object Ejercicio2 {
  def main(args:Array[String]) = {
    val cadena = new Cadena(6)
    val empaquetador = new Array[Thread](3)
    for (i <- 0 until empaquetador.length)
      empaquetador(i) = thread {
        while (true)
          cadena.retirarProducto(i)
          Thread.sleep(Random.nextInt(500)) // empaquetando
      }

    val colocador = thread {
      while (true)
        Thread.sleep(Random.nextInt(100)) // recogiendo el producto
        cadena.nuevoProducto(Random.nextInt(3))
    }
  }
}
