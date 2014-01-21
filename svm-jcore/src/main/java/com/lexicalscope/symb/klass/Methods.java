package com.lexicalscope.symb.klass;

import java.util.LinkedHashMap;
import java.util.Map;

import com.lexicalscope.symb.stack.trace.SMethodName;
import com.lexicalscope.symb.vm.SVirtualMethodName;

public final class Methods {
   private final Map<SMethodDescriptor, SMethod> methodMap;
   private final Map<SVirtualMethodName, SMethod> virtuals;
   private final String name;

   private Methods(final String name, final Map<SMethodDescriptor, SMethod> methodMap, final Map<SVirtualMethodName, SMethod> virtuals) {
      this.name = name;
      this.methodMap = methodMap;
      this.virtuals = virtuals;
   }

   public Methods(final String name) {
      this(name, new LinkedHashMap<SMethodDescriptor, SMethod>(), new LinkedHashMap<SVirtualMethodName, SMethod>());
   }

   public Methods extend(final String name, final DeclaredMethods declaredMethods) {
      final LinkedHashMap<SVirtualMethodName, SMethod> virtualsCopy = new LinkedHashMap<>(virtuals);
      for (final SMethod smethod : declaredMethods.methods().values()) {
         virtualsCopy.put(smethod.name().virtualName(), smethod);
      }
      return new Methods(name, declaredMethods.methods(), virtualsCopy);
   }

   public SMethodName resolve(final SMethodDescriptor sMethodName) {
      final SVirtualMethodName methodKey = sMethodName.virtualName();
      assert virtuals.containsKey(methodKey) : methodKey + " not in " + name + " " + virtuals;
      return virtuals.get(methodKey).name();
   }

   public SMethod findDefined(final SMethodName sMethodName) {
      assert methodMap.containsKey(sMethodName) : sMethodName + " not in " + name + " " + methodMap;
      return methodMap.get(sMethodName);
   }

   public boolean hasStatic(final SMethodName sMethodName) {
      return  methodMap.containsKey(sMethodName);
   }
}
