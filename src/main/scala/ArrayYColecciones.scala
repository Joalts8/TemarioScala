class ArrayYColecciones {
  def array(): Unit = {
    // array, se puede no poner new, val hace const al array, no a sus valores
    val saludos = new Array[Int](3) // Array[tipo](tam/valores)
    saludos(0) = 0 // da valor a index
    saludos(1) = 1
    saludos(2) = 2
    println(saludos.apply(0)) // muestra valor en index
    saludos.update(0, 8) //mod valor
    println(saludos.apply(0))
    val saludosComa = saludos.mkString(",") //separa los elementos eso
  }
  
  def lista(): Unit = {
    //lista, siempre inmutable sus elementos
    val l = List(1, 2, 3) // si ()-> lista vacia; nil= lista nothin
    val l2 = 1 :: l // une un entero y lista anterior o varios enteros y nil
    val l3 = l ::: l2 // une 2 listas
  }
  
  def set(): Unit = {
    //set inmutable(elem)
    var c = Set(1, 2, 3)
    c += 4 // añade 4 al set, con ++->set(elems)
    c --= Set(1, 2) // quita elementos dado un set(1,2), con - ->elimina un elemento
    //set mutable
    import scala.collection.mutable // arriba
    val set = mutable.Set(1, 2, 3)
  }
  
  def tupla(): Unit = {
    //tuplas, 2 elementos
    var par = (99, "burbuja")
    println(par._1) // imprime 1er valor, ._2 segundo, sirve (index)
  }
  
  def mapa(): Unit = {
    //mapas
    var mapa = Map(1 -> "a", 2 -> "b", 3 -> "c")
    mapa += (4 -> "ac") //añade al map
    println(mapa(4))
  }

}