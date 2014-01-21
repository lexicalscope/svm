package com.lexicalscope.symb.classloading;


public class SClassLoadingFailException extends RuntimeException {
   public SClassLoadingFailException(final String message, final Exception e) {
      super(message, e);
   }
}
