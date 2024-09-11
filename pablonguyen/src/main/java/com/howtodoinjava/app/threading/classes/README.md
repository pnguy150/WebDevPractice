
# Memory for an object is from a heap when the object is created through "new"
## For example: Foo foo = new Foo(); The memory for object "foo" is from the heap
## The variable "foo" is a reference or pointer to the heap memory
## if reference "foo" is passed and used among multiple threads then the implementation of class Foo needs to be threadsafe
#### for example 
Foo foo = new Foo()
Thread1(foo)
  in thread1 foo.function

Thread2(foo)
in thread2 foo.function

In this example Foo.function() need to be thread safe.

## but if each thread allocates its own instance of Foo then there is no concurrency problem
For example
   Thread1.method1() {
      Foo foo = new Foo()
      foo.function
   }

   Thread2.method2() {
     Foo foo = new Foo()
     foo.function
    }


Each "foo" reference is inside a method of a thread therefore the "reference" is on a stack of each thread. Each thread has a separate stack,
therefore the "foo" reference of the two threads are not the same


# the UnsafeHashMap is a unsafe implementation of a class that uses non-concurrent HashMap implementation
# the SafeHashMap is a safe implementation of a class that uses non-concurrent HashMap implementation via Java "synchronized"
# the SafeConcurrentHashMap is a safe implementation of a class that uses ConcurrentHashMap that has a built-in concurrency


 




