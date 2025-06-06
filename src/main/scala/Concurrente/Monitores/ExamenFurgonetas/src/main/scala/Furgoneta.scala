import scala.util.Random

// Furgoneta con tecnologia V2X 
// Tiene que unirse aa convoy para ir del origen al destino.
// Si es la primera en unirse al convoy se convierte en la lider.
// Si no es la primera, se une como seguidora.

class Furgoneta(id: Int, convoy: Convoy) extends Thread {
  private var lider: Int = -1
  private val r = new Random()

  override def run(): Unit = {
    try {
      Thread.sleep(r.nextInt(50))
      // La furgoneta se une al convoy
      lider = convoy.unir(id)
      if (lider == id) { // Es la furgoneta lider
        convoy.calcularRuta(id)
        Thread.sleep(1000)
        convoy.destino(id)
      } else { // No es la furgoneta lider		
        convoy.seguirLider(id)
      }
    } catch {
      case e: InterruptedException => e.printStackTrace()
    }
  }
}