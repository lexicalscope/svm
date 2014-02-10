package com.lexicalscope.svm.classloading;


public class SClassLoadingFailException extends RuntimeException {
   public SClassLoadingFailException(final String message, final Exception e) {
      super(message, e);
   }
}
