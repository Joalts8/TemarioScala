class Procesador(id: Int, nDatos: Int, dato: DatoCompartido) extends Thread {

  override def run(): Unit = {
    try {
      var d: Int = 0
      for (i <- 0 until nDatos) {
        // Protocolo de entrada: lee el dato del recurso compartido
        d = dato.leeDato(id)
        // Procesa el dato incrementandolo en 1
        d = d + 1
        // Protocolo de salida: actualiza el dato en el recurso compartido
        dato.actualizaDato(id, d)
      }
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }
}