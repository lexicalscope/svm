package com.lexicalscope.symb.vm;

public class MissingClassDefinitionException extends RuntimeException {
   public MissingClassDefinitionException(final String klassName) {
      super(klassName);
   }
}
