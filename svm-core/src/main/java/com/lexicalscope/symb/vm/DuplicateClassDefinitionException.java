package com.lexicalscope.symb.vm;


public class DuplicateClassDefinitionException extends RuntimeException {
   public DuplicateClassDefinitionException(final SClass klass) {
      super(klass.name());
   }
}
