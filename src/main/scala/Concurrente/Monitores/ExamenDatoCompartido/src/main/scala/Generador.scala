import scala.util.Random

class Generador(nDatos: Int, dato: DatoCompartido) extends Thread {
  private val MAX_VALOR = 100
  private val rnd = new Random()

  override def run(): Unit = {
    try {
      var d: Int = 0
      for (i <- 0 until nDatos) {
        d = dato.generaDato(rnd.nextInt(MAX_VALOR))
        println(s"Resultado procesamiento = $d")
        println("-------------------------------")
      }
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }
}