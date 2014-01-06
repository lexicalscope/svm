package com.lexicalscope.symb.vm;

import java.util.Map;

import com.lexicalscope.symb.vm.classloader.SClass;

public class MissingClassDefinitionException extends RuntimeException {
   public MissingClassDefinitionException(final String klassName, final Map<String, SClass> defined) {
      super(klassName + " not in " + defined);
   }
}
