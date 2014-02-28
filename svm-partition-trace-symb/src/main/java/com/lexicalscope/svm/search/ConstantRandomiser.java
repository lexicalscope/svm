package com.lexicalscope.svm.search;

public class ConstantRandomiser implements Randomiser {
   private final int result;

   public ConstantRandomiser(final int result) {
      this.result = result;
   }

   @Override public int random(final int count) {
      assert result < count;
      return result;
   }

   public static Randomiser constant(final int result) {
      return new ConstantRandomiser(result);
   }
}
