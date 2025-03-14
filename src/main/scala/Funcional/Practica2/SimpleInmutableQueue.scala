package Funcional.Practica2

trait ImmutableQueue[T] {
  def enqueue(elem: T): ImmutableQueue[T]
  def dequeue(): (T, ImmutableQueue[T])
  def isEmpty: Boolean
}

class SimpleQueue[T] private (private val elems: List[T]) extends ImmutableQueue[T] {
  def this(elems: T*) = {
    this(elems.toList)
  }

  override def enqueue(elem: T): ImmutableQueue[T] = {
    SimpleQueue[T](elems.appended(elem))
  }

  override def dequeue(): (T, ImmutableQueue[T]) = {
    elems match
      case Nil=> throw new RuntimeException("Cola vacía")
      case a::r=> (a,SimpleQueue[T](r))
  }

  def isEmpty: Boolean = {
    if (elems.isEmpty) {
      true
    } else {
      false
    }
  }
  // override def toString: String = ...
  override def equals(obj: Any): Boolean = {
    obj match
      case cola2: SimpleQueue[T] if (cola2.elems == this.elems) => true
      case _ => false
  }

  override def hashCode(): Int = {
    elems.hashCode()
  }
}

@main def testSimpleQueue(): Unit = {
  val squeue = new SimpleQueue[Int]()
  val q = squeue.enqueue(1).enqueue(2).enqueue(3).enqueue(4)
  assert(q.dequeue() == (1, SimpleQueue(2, 3, 4)), s"${q.dequeue()} should be equal to (1, SimpleQueue(List(2, 3, 4)))")
  assert(squeue.isEmpty, s"{q} should be empty")
  assert(!q.isEmpty, s"{q should not be empty")
  val q2 = SimpleQueue(1, 2, 3, 4)
  assert(q == q2, s"${q} and ${q2} should be equal")
  assert(q.hashCode() == q2.hashCode(), s"The hash codes of ${q} and ${q2} should be equal")
}
