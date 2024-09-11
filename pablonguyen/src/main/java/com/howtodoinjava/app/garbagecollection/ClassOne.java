package com.howtodoinjava.app.garbagecollection;

public class ClassOne {
  public ClassOne() {
  }

  public void method1() {
    // talk about memory size of foo, Int: byte, short, Int, long

    // here reference count of foo is 1
    Foo foo = new Foo("xyz", 1234);
    method2(foo);
    // return here reference count of foo is 1

    // after this function exits, reference count is 0
    // garbage collected
  }

  public int method2(Foo foo) {
    // here reference count of foo is 2
    ClassTwo classTwo = new ClassTwo();

    // call into classTwo.method(foo), reference count for foo is 3
    classTwo.method(foo);
    // return from classTwo.method, reference count is 2
    return 0;
  }

  public void referenceCountWithClass() {
    Foo foo = new Foo("xyz", 1234);
    // ref count is 1
    ClassThree classThree = new ClassThree(foo);
    // passing to constructor of a class, ref count is 2

    // when this function referenceCountWithClass return
    // classThree is out of scope, ref count is 1
    // and is also out of scape ref to 0
    // garbage collected
  }
}
