package com.lexicalscope.svm.vm.conc;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

@Retention(RUNTIME)
public @interface LoadFrom {
   Class<?> value();
}
