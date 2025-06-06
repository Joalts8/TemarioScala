import java.util.concurrent.locks.ReentrantLock

class Convoy(tam: Int) {

  /**
   * Las furgonetas se unen al convoy
   * La primera es la lider, el resto son seguidoras
   **/

  private var lider = -1;
  private var furgonetas = 0;
  private var puertasAbiertas = true
  private var destino = false
  private val l = new ReentrantLock(true)
  private val liderCond = l.newCondition()
  private val furgonetasCond = l.newCondition()

  def unir(id: Int): Int = {
    l.lock()
    try{
      if (lider == -1) {
        lider = id
        println(s"** Furgoneta $id lidera del convoy **")
        furgonetas += 1
      } else {
        println(s"Furgoneta $id seguidora")
        furgonetas += 1
        if(furgonetas == tam) liderCond.signal()
      }
    }finally {
      l.unlock()
    }
    lider
  }

  /**
   * La furgoneta lider espera a que todas las furgonetas se unan al convoy
   * Cuando esto ocurre calcula la ruta y se pone en marcha
   **/
  def calcularRuta(id: Int): Unit = {
    l.lock()
    try{
      if(furgonetas != tam) liderCond.await()
      println(s"** Furgoneta $id lider:  ruta calculada, nos ponemos en marcha **")
    } finally {
      l.unlock()
    }
  }

  /**
   * La furgoneta lider avisa al las furgonetas seguidoras que han llegado al destino y deben abandonar el convoy
   * La furgoneta lider espera a que todas las furgonetas abandonen el convoy
   **/
  def destino(id: Int): Unit = {
    l.lock()
    try{
      println(s"** Furgoneta $id lider: hemos llegado al destino **")
      destino = true
      furgonetasCond.signalAll()
      if(furgonetas != 1) liderCond.await()
      println(s"** Furgoneta $id lider abandona el convoy **")
      furgonetas -= 1
      lider = -1
    }finally {
      l.unlock()
    }
  }

  /**
   * Las furgonetas seguidoras hasta que la lider avisa de que han llegado al destino
   * y abandonan el convoy
   **/
  def seguirLider(id: Int): Unit = {
    l.lock()
    try{
      if(!destino) furgonetasCond.await()
      println(s"Furgoneta $id abandona el convoy")
      furgonetas -= 1
      if(furgonetas == 1) liderCond.signal()
    }finally {
      l.unlock()
    }
  }
}

/**
 * Programa principal. No modificar
 **/
object Convoy extends App {
  val NUM_FURGO = 10
  val c = new Convoy(NUM_FURGO)
  val flota = Array.ofDim[Furgoneta](NUM_FURGO)

  for (i <- 0 until NUM_FURGO)
    flota(i) = new Furgoneta(i, c)

  for (i <- 0 until NUM_FURGO) {
    flota(i).start()
  }
}