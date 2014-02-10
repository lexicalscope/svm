package com.lexicalscope.svm.classloading;

import java.util.Map;

import com.lexicalscope.svm.vm.j.klass.SClass;

public class MissingClassDefinitionException extends RuntimeException {
   public MissingClassDefinitionException(final String klassName, final Map<String, SClass> defined) {
      super(klassName + " not in " + defined);
   }
}
