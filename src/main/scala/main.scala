object Main {
  // tools-> scala REPL para ejecutar codigo sin escribirlo en fichero, ; no es obligatorio
  def main(args: Array[String]): Unit = {
    println("Hello world")
    // declaracion de constante y variables(se puede indicar o no el tipo)
    val entero=34
    var palabra="Hello world"
    var entero2: Int = 10

    entero2=entero+entero2
    entero==entero2 //comparacion en tipos basicos similar a equals; entre objetos es como eq->si son el mismo objeto

    //Interpolacion de String-> En vez palabra= "bla + palabra+ ", " + entero2; si se opera-> ${val+bla}
    palabra= s"bla $palabra, $entero2" //s= string; f= variar formato ; raw= quita \n
    //mostrar en pantalla
    println(palabra)

    // array, se puede no poner new, val hace const al array, no a sus valores
    val saludos = new Array[Int](3) // Array[tipo](tam/valores)
    saludos(0) = 0 // da valor a index
    saludos(1) = 1
    saludos(2) = 2
    println(saludos.apply(0)) // muestra valor en index
    saludos.update(0, 8) //mod valor
    println(saludos.apply(0))
    val saludosComa=saludos.mkString(",") //separa los elementos eso

    //lista, siempre inmutable sus elementos
    val l = List(1,2,3)// si ()-> lista vacia; nil= lista nothin
    val  l2= 1::l     // une un entero y lista anterior o varios enteros y nil
    val  l3= l:::l2  // une 2 listas

    //tuplas, 2 elementos
    var par =(99,"burbuja")
    println(par._1) // imprime 1er valor, ._2 segundo, sirve (index)

    //set inmutable(elem)
    var c = Set(1,2,3)
    c += 4          // añade 4 al set, con ++->set(elems)
    c --= Set(1,2) // quita elementos dado un set(1,2), con - ->elimina un elemento
    //set mutable
    import scala.collection.mutable // arriba
    val set = mutable.Set(1, 2, 3)

    //mapas, se muetra k con (v)
    var mapa = Map(1->"a",2->"b",3->"c")
    mapa += (4->"ac") //añade al map
  }

  //declaracion de funcion ({}(si 1 ln de cod) y tipo devuelto no necesario), se puede usar en repl con load dirArchivo->import clase
  /*def funcion(parametros):tipoDevuelto={
      blabla (devuelve ultimo valor)
  }*/
  def max(x: Int, y: Int) = {
    if (x > y) x else y
  }
  def buclesSuma()={// while es igual
    val r=(1 to 10)
    for(elem<- r){ // estructura for(elemento<- coleccion)  foreach(a veces es funcion)
      println(elem)
    }
  }
  def switch(x: Int)={
    val palabra = x match { // puede usarse como switch o para asignar como en el ejemplo
      case 1=> "Lunes"
      case 3=> {
        println(x)
        "msi"
      }
      case c if (c>3 && c<8)=> "aña"
      case 2|9|8=> "QUE"
      case _=> "mondongo"// caso default
    }
  }

}