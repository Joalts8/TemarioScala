package Funcional

//definicion de lista, que puede ser nula o valor, sublista(cola)
enum EDPolimorfica[+A]:
  case Nula
  case Cons(h: A, t: EDPolimorfica[A])


  def suma(ints: EDPolimorfica[Int]): Int =
    ints match
      case Nula => 0
      case Cons(x, resto) => x + suma(resto)

  def sumal(l: EDPolimorfica[Int]): Int = {
    def bucle(l: EDPolimorfica[Int], acc: Int): Int = {
      l match
        case Nula => acc
        case Cons(a, r) => bucle(r, a + acc)
    }

    bucle(l, 0)
  }

  def producto(l: EDPolimorfica[Int]): Int = {
    def bucle(l: EDPolimorfica[Int], acc: Int): Int = {
      l match
        case Nula => acc
        case Cons(a, r) => bucle(r, a * acc)
    }

    bucle(l, 1)
  }

  def operar[A](l: EDPolimorfica[A], inic: A, f: (A, A) => A) = {
    def bucle(l: EDPolimorfica[A], acc: A): A = {
      l match
        case Nula => acc
        case Cons(a, r) => bucle(r, f(a, acc))
    }

    bucle(l, inic)
  }

  def longitud[A](l: EDPolimorfica[A]) =
    foldRight[A, Int](l, 0, (x, y) => y + 1)


  def foldRight[A, B](l: EDPolimorfica[A], acc: B, f: (A, B) => B): B = l match
    case Nula => acc
    case Cons(a, r) => f(a, foldRight(r, acc, f)) //resuelve de derecha a izda

  def foldLeft[A, B](l: EDPolimorfica[A], acc: B, f: (A, B) => B): B =
    l match
      case Nula => acc
      case Cons(a, r) => foldLeft(r, f(a, acc), f) //acumula al revés


  def map[A, B](l: EDPolimorfica[A], f: A => B): EDPolimorfica[B] = l match
    case Nula => Nula
    case Cons(a, r) => Cons(f(a), map(r, f))

  def map1[A, B](l: EDPolimorfica[A], f: A => B): EDPolimorfica[B] =
    foldRight[A, EDPolimorfica[B]](l, Nula, (x, y) => Cons(f(x), y))


  def filter[A](l: EDPolimorfica[A], f: A => Boolean): EDPolimorfica[A] = l match
    case Nula => Nula
    case Cons(a, r) => if (f(a)) Cons(a, filter(r, f)) else filter(r, f)

  def filter1[A](l: EDPolimorfica[A], f: A => Boolean): EDPolimorfica[A] =
    foldRight[A, EDPolimorfica[A]](l, Nula, (x, y) => if (f(x)) Cons(x, y) else y)


// define comportamiento del enum
object EDPolimorfica:
  //metodo apply(visto en clase) dado tantos elems tipo A, coge el primero y hace la sublista con la cola de args(1 a n)
  def apply[A](args: A*): EDPolimorfica[A] =
    if (args.isEmpty) Nula
    else Cons(args(0), apply(args.tail *))

