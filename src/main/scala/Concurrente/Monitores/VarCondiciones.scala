package Concurrente.Monitores
import Concurrente.Introduccion.*

//Sirve para usar distintos monitores en el objeto-> más parecido a semaforo
class Condition {
  def cwait() = synchronized {
    wait()
  }
  def cnotify() = synchronized {notify()}//En este metodo lo pongo en la misma línea porq solo hace 1 cosa
  def cnotifyAll = synchronized {notifyAll()}//En este metodo, no usa ()=> al llamarlo no hcen falta
}

//Ejemplo con P-C, pero se puede Blockear=> Usar Locks
object productorConsumidor {
  private var nLectores = 0
  private var escribiendo = false
  private var nEscritores = 0
  private val okLeer = Condition()
  private val okEscribir = Condition()

  def entraLector(id: Int) = synchronized {
    while (escribiendo || nEscritores > 0) okLeer.cwait()
    nLectores += 1
    log(s"Lector $id entra en la BD")
  }

  def entraEscritor(id: Int) = synchronized {
    nEscritores += 1
    while (escribiendo || nLectores > 0) okEscribir.cwait()
    escribiendo = true
    log(s"Escritor $id entra en la BD")
  }

  def saleLector(id: Int) = synchronized {
    nLectores -= 1
    if (nLectores == 0) okEscribir.notify()
    log(s"Lector $id sale de la BD")
  }

  def saleEscritor(id: Int) = synchronized {
    nEscritores -= 1
    escribiendo = false
    if (nEscritores > 0) okEscribir.notify()
    else okLeer.cnotifyAll
    log(s" Escritor $id sale de la BD")
  }
}


