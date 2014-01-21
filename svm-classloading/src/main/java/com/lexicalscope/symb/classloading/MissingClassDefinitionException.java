package com.lexicalscope.symb.classloading;

import java.util.Map;

import com.lexicalscope.symb.klass.SClass;

public class MissingClassDefinitionException extends RuntimeException {
   public MissingClassDefinitionException(final String klassName, final Map<String, SClass> defined) {
      super(klassName + " not in " + defined);
   }
}
