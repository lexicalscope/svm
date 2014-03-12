package com.lexicalscope.svm.classloading;

import static com.lexicalscope.svm.vm.j.klass.SClassMatchers.loadedFromSamePlaceAs;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class TestThreadIsLoadedFromLocal {
   @Test public void javaLangThreadLocalVersionIsLoaded() {
      assertThat(
            new AsmSClassLoader().load(Thread.class),
            loadedFromSamePlaceAs(AsmSClassLoader.class));
   }
}
