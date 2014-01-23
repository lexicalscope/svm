package com.lexicalscope.symb.classloading;

import com.lexicalscope.symb.vm.j.j.klass.SClass;


public class DuplicateClassDefinitionException extends RuntimeException {
   public DuplicateClassDefinitionException(final SClass klass) {
      super(klass.name());
   }
}
