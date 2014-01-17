package com.lexicalscope.symb.vm;

import java.util.Map;

public class MissingClassDefinitionException extends RuntimeException {
   public MissingClassDefinitionException(final String klassName, final Map<String, SClass> defined) {
      super(klassName + " not in " + defined);
   }
}
