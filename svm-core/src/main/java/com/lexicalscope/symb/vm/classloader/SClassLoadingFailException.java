package com.lexicalscope.symb.vm.classloader;

import java.io.IOException;

public class SClassLoadingFailException extends RuntimeException {
   public SClassLoadingFailException(final String name, final IOException e) {
      super(name, e);
   }
}
