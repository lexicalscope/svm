package com.lexicalscope.symb.stack;

/**
 * Padding for longs and doubles in the stack
 *
 * @author tim
 */
public final class Padding {
   public static final Padding padding = new Padding();

   @Override public String toString() {
      return "pad";
   }
}
