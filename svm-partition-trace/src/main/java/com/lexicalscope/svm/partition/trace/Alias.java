package com.lexicalscope.svm.partition.trace;

public class Alias {
   private final int index;

   public Alias(final int index) {
      this.index = index;
   }

   @Override public boolean equals(final Object obj) {
      return obj != null
            && obj.getClass().equals(this.getClass())
            && ((Alias) obj).index == this.index;
   }

   @Override public int hashCode() {
      return index;
   }

   @Override public String toString() {
      return "Alias-" + index;
   }
}
