package com.lexicalscope.symb.vm.j.j.klass;

import java.util.LinkedHashMap;
import java.util.Map;

public final class DeclaredMethods {
   private final Map<SMethodDescriptor, SMethod> methodMap = new LinkedHashMap<>();

   public void add(final SMethod smethod) {
      methodMap.put(smethod.name(), smethod);
   }

   public Map<SMethodDescriptor, SMethod> methods() {
      return methodMap;
   }
}
