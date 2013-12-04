package com.lexicalscope.symb.vm;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.lexicalscope.symb.vm.classloader.AsmSClassLoader;

public class TestClassLoading {
   @Test public void loadAClass()  {
      assertNotNull(new AsmSClassLoader().load("com.lexicalscope.symb.vm.EmptyClass"));
   }
}
