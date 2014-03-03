package com.lexicalscope.svm.vm.conc.junit;

import com.lexicalscope.svm.vm.j.StateTag;

public class ClassStateTag implements StateTag {
   private final Class<?> klass;

   public ClassStateTag(final Class<?> klass) {
      this.klass = klass;
   }

   @Override public boolean equals(final Object obj) {
      return obj != null && obj.getClass().equals(this.getClass()) && ((ClassStateTag) obj).klass.equals(this.klass);
   }

   public static StateTag tag(final Class<?> klass) {
      return new ClassStateTag(klass);
   }
}
