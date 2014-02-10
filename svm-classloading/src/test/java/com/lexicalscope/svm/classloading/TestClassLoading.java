package com.lexicalscope.svm.classloading;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.lexicalscope.svm.classloading.AsmSClassLoader;

public class TestClassLoading {
   @Test public void loadAClass()  {
      assertNotNull(new AsmSClassLoader().load("com.lexicalscope.symb.classloading.EmptyClass"));
   }
}
