package com.lexicalscope.symb.vm.conc;

import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.symb.vm.conc.junit.TestEntryPoint;
import com.lexicalscope.symb.vm.conc.junit.VmRule;

public class TestMethodCallInstrumentation {
   @Rule public final VmRule vm = new VmRule(new JvmBuilder());

   @TestEntryPoint public static void foo() {}

   @Test public void collectVirtualMethodInTrace() throws Exception {

   }
}
