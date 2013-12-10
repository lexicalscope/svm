package com.lexicalscope.symb.vm.classloader;

import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.symb.vm.State;

public class TestSClass {
   @Rule public JUnitRuleMockery context = new JUnitRuleMockery();
   @Mock State state;

   @Test public void klassIsInitialisedIfItIsAllocated() {

   }
}
