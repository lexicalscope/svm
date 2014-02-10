package com.lexicalscope.svm.vm.j;

import com.lexicalscope.svm.stack.trace.SMethodName;
import com.lexicalscope.svm.vm.j.klass.SClass;
import com.lexicalscope.svm.vm.j.klass.SMethod;
import com.lexicalscope.svm.vm.j.klass.SMethodDescriptor;


public class StaticsMarker implements MethodResolver {
   private final SClass klass;
   private final SClass klassKlass;

   public StaticsMarker(final SClass klassKlass, final SClass klass) {
      this.klassKlass = klassKlass;
      this.klass = klass;
   }

   @Override public boolean equals(final Object obj) {
      if(obj == this) {
         return true;
      }
      if(obj != null && this.getClass().equals(obj.getClass())) {
         return this.klass.equals(((StaticsMarker) obj).klass);
      }
      return false;
   }

   @Override public String toString() {
      return "Statics for " + klass;
   }

   @Override public SMethod virtualMethod(final SMethodDescriptor sMethodName) {
      return klassKlass.virtualMethod(sMethodName);
   }

   @Override public SMethod declaredMethod(final SMethodName sMethodName) {
      return klassKlass.declaredMethod(sMethodName);
   }

   public SClass klass() {
      return klass;
   }
}
