object Funcional extends App{
  //Pueden existir funciones en funciones
  //funcion polimorfica-> para tipo generico usando [T], y usando funcion A-> bollean
  def busca[A](v: Array[A], p: A => Boolean) = {  // esta condicion se puede definir _condicion(_ es generico) o elem=> condicion
    def bucle(n: Int): Int =
      if (n >= v.length) -1
      else if (p(v(n))) n
      else bucle(n + 1)

    bucle(0)
  }

  // edit de una funcion, en este caso, dado A y f(A,B)=C, devuelve g(B)=C
  def partial1[A,B,C](a:A,f:(A,B)=>C):B => C =
    y => f(a,y)

  //ejemplo sintasis en funcion polimorfica
  val array= Array[Int](1,2,3,4,5)
  println(busca(array, _%2==0))
  println(busca(array, x=>x%3==0))
}
