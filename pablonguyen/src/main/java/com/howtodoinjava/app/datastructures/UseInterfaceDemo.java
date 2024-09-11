package com.howtodoinjava.app.datastructures;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class UseInterfaceDemo {
  private List<String> strings;

  public UseInterfaceDemo() {
    strings = new LinkedList<>();
  }

  public void bar(List<String> useInterface) {
  }

  private void foo() {
    String string = strings.get(0);
  }
}
