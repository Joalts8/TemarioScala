import scala.collection.mutable

// Creacion de clase, parecido a java pero sin constructores-> se se inicializa como var/val pero usando new Clase
class Clase {
  private var suma=0

  def inc(a: Int) = suma += a
  def valor = suma
  override def toString = s"La suma es: $suma" // metodo toString se puede especificar tipo
}

class Clase2(n:Int,i:Int) {
  require(n>4)//esto hace q n deba cumplir esa condicion
  private var a=n
  private var b=i
  def this(n:Int) = this(n,1)//otro constructor a parte del normal
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
  val ejemplo=new Clase2(5,6)
  Contador.calcula("En scala cada valor es un objeto")
  Contador.calcula("Hola Mundo")
  Contador.calcula("función estática")
  Contador.log()
}