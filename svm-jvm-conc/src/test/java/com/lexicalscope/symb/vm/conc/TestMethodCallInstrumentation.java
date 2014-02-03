package com.lexicalscope.symb.vm.conc;

import static com.lexicalscope.svm.j.instruction.instrumentation.InstrumentationBuilder.traceMethodCalls;

import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.symb.vm.conc.junit.TestEntryPoint;
import com.lexicalscope.symb.vm.conc.junit.VmRule;

public class TestMethodCallInstrumentation {
   @Rule public final VmRule vm = new VmRule(new JvmBuilder().instrument("invokevirtual", traceMethodCalls()));

   public interface WithVirtualMethod { void virtualMethod(); }
   public static class ClassWithVirtualMethod implements WithVirtualMethod {
      @Override public void virtualMethod(){}
   }

   @TestEntryPoint public static void callSomeMethods() {
      new ClassWithVirtualMethod().virtualMethod();
   }

   @Test public void collectVirtualMethodInTrace() throws Exception {
      vm.execute();
   }
}
