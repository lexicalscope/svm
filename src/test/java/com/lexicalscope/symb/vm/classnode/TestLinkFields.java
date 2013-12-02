package com.lexicalscope.symb.vm.classnode;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

import com.lexicalscope.symb.vm.classloader.SClass;
import com.lexicalscope.symb.vm.classloader.SClassLoader;

public class TestLinkFields {
   private final SClassLoader sClassLoader = new SClassLoader();
   @Test public void linkFields()
   {
      final SClass loaded = sClassLoader.load("com/lexicalscope/symb/vm/classnode/ClassWith5Fields");
      assertThat(loaded.fieldCount(), equalTo(5));
   }
}
