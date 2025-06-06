object Driver extends App {
  val NPROCESADORES = 10
  val NDATOS = 3

  val dato = new DatoCompartido(NPROCESADORES)
  val e = new Generador(NDATOS, dato)
  e.start()

  val l = Array.ofDim[Procesador](NPROCESADORES)
  for (i <- 0 until NPROCESADORES) {
    l(i) = new Procesador(i, NDATOS, dato)
    l(i).start()
  }
}