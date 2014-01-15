package com.lexicalscope.symb.vm.classloader.asm;

import java.util.LinkedHashMap;
import java.util.Map;

import com.lexicalscope.symb.vm.classloader.AsmSMethod;
import com.lexicalscope.symb.vm.classloader.SMethod;
import com.lexicalscope.symb.vm.classloader.SMethodName;

public class DeclaredMethods {
   private final Map<SMethodName, SMethod> methodMap = new LinkedHashMap<>();

   public void add(final AsmSMethod smethod) {
      methodMap.put(smethod.name(), smethod);
   }

   public Map<SMethodName, SMethod> methods() {
      return methodMap;
   }
}
