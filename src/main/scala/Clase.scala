import scala.collection.mutable

// Creacion de clase, parecido a java pero sin constructores-> se se inicializa como var/val pero usando new Clase
class Clase {
  private var suma=0

  def inc(a: Int) = suma += a
  def valor = suma
}

// creacion de clase y objeto unico->  no se puede usar como una clase normal y tener varias instancias
object Contador{
  private val mapa =
    mutable.Map.empty[String,Int]

  def calcula(s:String):Int = {
    if (mapa.contains(s)) mapa(s)
    else {
      val cont = new Clase  // creacion de objeto Clase
      for (c<-s)
        cont.inc(c.toInt)
      mapa += s->cont.valor
      cont.valor
    }
  }
  def log() =
    println(mapa.mkString("\n"))
}

// objeto unico q extiende de App=> ejecutable y usa el objeto unico contador
object Main2 extends App{
  Contador.calcula("En scala cada valor es un objeto")
  Contador.calcula("Hola Mundo")
  Contador.calcula("función estática")
  Contador.log()
}