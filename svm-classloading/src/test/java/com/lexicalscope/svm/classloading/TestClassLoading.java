package com.lexicalscope.svm.classloading;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class TestClassLoading {
   @Test public void loadAClass()  {
      assertNotNull(new AsmSClassLoader().load("com.lexicalscope.svm.classloading.EmptyClass"));
   }
}
