package Concurrente.Monitores

class BloquesSinc {
  private var dato = 0

  //Estas funciones hacen exclusion mutua, son lo mismo, pero this puede sustituirse por el monitor(Lock basico) de otra clase, funciona como un semaforo del objeto
  def leer() = synchronized {
    //wait-> suspende la hebra en la que se encuentre y libera el monitor
    if (dato==0) wait()
    dato
  }
  def escribir(nValor: Int) = synchronized {
    //notify/notifyAll-> manda las hebras suspendidas a la cola de nuevo
    dato = nValor
    notify()
  }
  def incrementar(inc: Int) = synchronized {
    this.synchronized {
      dato += inc
    }
  }
  def decrementar(inc: Int) = {
    synchronized {
      dato -= inc
    }
  }
}
