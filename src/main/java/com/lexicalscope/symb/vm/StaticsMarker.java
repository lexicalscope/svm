package com.lexicalscope.symb.vm;

import com.lexicalscope.symb.vm.classloader.SClass;

public class StaticsMarker {
   private final SClass klass;

   public StaticsMarker(final SClass klass) {
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
      return "Statics " + klass;
   }
}
