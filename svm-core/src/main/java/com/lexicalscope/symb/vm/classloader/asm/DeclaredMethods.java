package com.lexicalscope.symb.vm.classloader.asm;

import java.util.LinkedHashMap;
import java.util.Map;

import com.lexicalscope.symb.vm.SMethod;
import com.lexicalscope.symb.vm.SMethodDescriptor;
import com.lexicalscope.symb.vm.classloader.AsmSMethod;

public class DeclaredMethods {
   private final Map<SMethodDescriptor, SMethod> methodMap = new LinkedHashMap<>();

   public void add(final AsmSMethod smethod) {
      methodMap.put(smethod.name(), smethod);
   }

   public Map<SMethodDescriptor, SMethod> methods() {
      return methodMap;
   }
}
