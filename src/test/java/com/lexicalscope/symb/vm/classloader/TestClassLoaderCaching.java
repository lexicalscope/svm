package com.lexicalscope.symb.vm.classloader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;

import org.junit.Test;

public class TestClassLoaderCaching {
   private final SClassLoader sClassLoader = new AsmSClassLoader();
   private final SClass classWithFiveFields = sClassLoader.load(ClassWith5Fields.class);

   @Test public void loadSameObjectTwiceReturnsCachedCopy(){
      assertThat(sClassLoader.load(ClassWith5Fields.class), sameInstance(classWithFiveFields));
   }

   @Test public void superclassIsCachedCopy(){
      sClassLoader.load(SubClassWithAdditionalFields.class);
      assertThat(sClassLoader.load(SubClassWithAdditionalFields.class), SClassMatchers.hasSuperclass(sameInstance(classWithFiveFields)));
   }
}
