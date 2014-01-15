package com.lexicalscope.symb.vm;

public class StaticField {
   public static int x = 5;

   public static int getX() {
      return x;
   }

   public static void setX(final int x) {
      StaticField.x = x;
   }
}
