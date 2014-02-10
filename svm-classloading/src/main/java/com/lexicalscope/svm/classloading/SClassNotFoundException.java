package com.lexicalscope.svm.classloading;

public class SClassNotFoundException extends RuntimeException {
   public SClassNotFoundException(final String name) {
      super(name);
   }
}
