package com.lexicalscope.symb.classloading;

public class SClassNotFoundException extends RuntimeException {
   public SClassNotFoundException(final String name) {
      super(name);
   }
}
