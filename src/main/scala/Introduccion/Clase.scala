package Introduccion

import scala.collection.mutable

// Creacion de clase, parecido a java pero sin constructores definidos-> se se inicializa como var/val pero usando new Clase
class Clase {
  private var suma=0

  def inc(a: Int) = suma += a
  def valor = suma
}

//clase donde el constructor necesita parametros-> son private val de predeterminado
class Clase2(n:Int,var i:Int) {
  require(n>4)// n deba cumplir esa condicion
  def this(n:Int) = this(n,1)//otro constructor a parte del predeterminado

  def ne: Int = n
  def ia = i

  //funcion q cambia +-*/  ejemplo en main
  def +(rac: Clase2): Clase2 =
    new Clase2(rac.ne * i + n * rac.ne, i * rac.ia)
  //cambio de la funcion apply-> puedes crear como con array(1,2,3)*Otro ejemplo en EDPolimorfica*
  def apply(num: Int, den: Int): Clase2 = {
    new Clase2(num, den)
  }

  // metodo toString se puede especificar tipo(ejemplo de override)
  override def toString = s"La clase es: $n, $i"

  //transformador de tipo(ejemplo con int) a clase2
  implicit def intToClase2(x: Int): Clase2 = Clase2(x)
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
  val ejemplo2=new Clase2(5,6)  //ejemplo clase 2, y de +
  println(ejemplo+ejemplo2)
  Contador.calcula("En scala cada valor es un objeto")
  Contador.calcula("Hola Mundo")
  Contador.calcula("función estática")
  Contador.log()
}