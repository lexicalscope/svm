package com.lexicalscope.symb.vm.classloader;

public class SMethodNotFoundException extends RuntimeException {
   public SMethodNotFoundException(final String name, final String desc) {
      super(name + desc);
   }
}
