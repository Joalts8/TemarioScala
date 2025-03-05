package Funcional

//definicion de lista, que puede ser nula o valor, sublista(cola); al usar +A puede usar subtipos de A
enum EDPolimorfica[+A]:
  case Nula
  case Cons(h: A, t: EDPolimorfica[A])

  //operaciones con recursividad generica y de cola; usa +, pero sirve para * / - sustituyendo lo necesario
  def suma(ints: EDPolimorfica[Int]): Int =
    ints match
      case Nula => 0
      case Cons(x, resto) => x + suma(resto)
  //Recursividad de cola
  def suma_cola(l: EDPolimorfica[Int]): Int = { //otra forma es llamando directamente a recursion(l,acc), igual q la funcion operar pero sin decir f(x,y)
    def recursion(l: EDPolimorfica[Int], acc: Int): Int = {
      l match
        case Nula => acc
        case Cons(a, r) => recursion(r, a + acc)
    }
    recursion(l, 0)
  }
  //funcion para operacion cualquiera definida con f(x, y)(En funciones polinomicas ejemplo de sintais); recursividad de cola
  def operar[A](l: EDPolimorfica[A], inic: A, f: (A, A) => A) = {
    def bucle(l: EDPolimorfica[A], acc: A): A = {
      l match
        case Nula => acc
        case Cons(a, r) => bucle(r, f(a, acc))
    }
    bucle(l, inic)
  }
  //ejecucion de una funcion en distinto orden, la 1a de derecha a izquierda;   se puede aplicar a operar tambien
  def foldRight[A, B](l: EDPolimorfica[A], acc: B, f: (A, B) => B): B = {
    l match
      case Nula => acc
      case Cons(a, r) => f(a, foldRight(r, acc, f))
  }
  //resuelve de izquierdsa a derecha(como operar y anteriores por cola)
  def foldLeft[A, B](l: EDPolimorfica[A], acc: B, f: (A, B) => B): B = {
    l match
      case Nula => acc
      case Cons(a, r) => foldLeft(r, f(a, acc), f)
  }

  //ejemplo del uso de foldRigth
  def longitud[A](l: EDPolimorfica[A]) = foldRight[A, Int](l, 0, (x, y) => y + 1)
  //Uso para crear values de un map=f(k) donde Key son los valores de EDP
  def map[A, B](l: EDPolimorfica[A], f: A => B): EDPolimorfica[B] = {
    l match
      case Nula => Nula
      case Cons(a, r) => Cons(f(a), map(r, f))
  }
  def map1[A, B](l: EDPolimorfica[A], f: A => B): EDPolimorfica[B] =foldRight[A, EDPolimorfica[B]](l, Nula, (x, y) => Cons(f(x), y))
  //creacion de un flitro, funcion que devuelbe boolean
  def filter[A](l: EDPolimorfica[A], f: A => Boolean): EDPolimorfica[A] = {
    l match
      case Nula => Nula
      case Cons(a, r) => if (f(a)) Cons(a, filter(r, f)) else filter(r, f)
  }
  def filter1[A](l: EDPolimorfica[A], f: A => Boolean): EDPolimorfica[A] = foldRight[A, EDPolimorfica[A]](l, Nula, (x, y) => if (f(x)) Cons(x, y) else y)

// define comportamiento del enum
object EDPolimorfica {
  //metodo apply(visto en clase) dado tantos elems tipo A, coge el primero y hace la sublista con la cola de args(1 a n)
  def apply[A](args: A*): EDPolimorfica[A] =
    if (args.isEmpty) Nula
    else Cons(args(0), apply(args.tail *))
}

object pruebas extends App {
  val p: EDPolimorfica[Int] = EDPolimorfica(1, 2, 3, 4)
  println(p)
  println(p.suma(p))
  println(p.suma_cola(p))
  println(p.operar(p,1,(x,y)=>x*y))
  println(p.longitud(p))
  println(p.map(p,(x)=>2*x))
  println(p.filter1(p,_%2==0))
}