package Concurrente.Introduccion

//Problema del jardin-> 2 puertas por la que entras visitantes=> la variable se modifica a la vez y no se acctualza bien
class JardinesContador {
  private var n = 0;
  def inc = n += 1
  def num: Int = n;
}

//Problema Productor consumidor->el consumidor consume antes q el productoe
object ProdConsVariable {
  private var valor: Int = 0;
  def escribe(nvalor: Int) = {
    valor = nvalor
  }
  def lee = valor
}


object Main {
  def main(args:Array[String]) = {
    val cont = new JardinesContador
    val puerta1 = thread(for (i <- 0 until 100) cont.inc)
    val puerta2 = thread(for (i <- 0 until 100) cont.inc)
    puerta1.join();
    puerta2.join();
    log(s"valor num = ${cont.num}")
  }
}

object mainProductorConsumidor extends App {
  val prod = thread {
    for (i <- 0 until 10) {
      ProdConsVariable.escribe(i)
      log(s"Productor: $i")
    }
  }
  val cons = thread {
    for (i <- 0 until 10)
      log(s"Consumidor: ${ProdConsVariable.lee}")
  }
}





