package com.lexicalscope.symb.vm;

public class StaticAbsMethod {
   // with overflow bug
   public static int abs(final int x) {
	  if(x < 0) return x * -1;
      return x;
   }
}
