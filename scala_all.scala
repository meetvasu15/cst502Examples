// oo - Hello world Example

 object HelloWorld {
    def main(args: Array[String]) {
      println("Hello, world!")
    }
  }
  
  
  HelloWorld.main(null)

// Collections
val colors = List("red", "blue", "green")

colors.head

colors.tail

// map
import scala.collection.mutable
val map = mutable.Map.empty[String, Int]
map("hello") = 1
 map("there") = 2
 map

// Stack in scala

import scala.collection.mutable.Stack
val stack = new Stack[Int] 
stack.push(1)
stack
stack.push(2)
 stack.top
 stack.pop
 
 // lazy initializations
 lazy val a =  1; lazy val b = a + 1;
 a
 b
 
val a =  1; val b = a + 1;
