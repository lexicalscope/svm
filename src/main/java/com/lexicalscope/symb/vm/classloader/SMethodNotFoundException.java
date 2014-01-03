package com.lexicalscope.symb.vm.classloader;

public class SMethodNotFoundException extends RuntimeException {
   public SMethodNotFoundException(final AsmSClass asmSClass, final String name, final String desc) {
      super(name + desc + " not found in " + asmSClass);
   }
}
