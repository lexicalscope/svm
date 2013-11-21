package com.lexicalscope.symb.vm;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.lexicalscope.symb.vm.classloader.SClassLoader;

public class TestClassLoading {
   @Test public void loadAClass()  {
      assertNotNull(new SClassLoader().load("com.lexicalscope.symb.vm.EmptyClass"));
   }
}
