package com.lexicalscope.symb.partition.trace;

import static com.lexicalscope.MatchersAdditional.has;
import static com.lexicalscope.symb.partition.trace.TraceMatchers.methodCallOf;
import static com.lexicalscope.symb.partition.trace.TraceMetaKey.TRACE;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.symb.vm.conc.JvmBuilder;
import com.lexicalscope.symb.vm.conc.junit.TestEntryPoint;
import com.lexicalscope.symb.vm.conc.junit.VmRule;
import com.lexicalscope.symb.vm.j.j.code.AsmSMethodName;

public class TestMethodCallInstrumentation {
   @Rule public final VmRule vm = new VmRule(
         new JvmBuilder().
            instrument("methodentry", new TraceMethodCalls()).
            meta(TRACE, new Trace()));

   public interface WithVirtualMethod { void myVirtualMethod(); }
   public static class ClassWithVirtualMethod implements WithVirtualMethod {
      @Override public void myVirtualMethod(){}
   }

   @TestEntryPoint public static void callSomeMethods() {
      new ClassWithVirtualMethod().myVirtualMethod();
   }

   @Test @Ignore public void collectVirtualMethodInTrace() throws Exception {
      vm.execute();

      assertThat(
            vm.result().state().getMeta(TRACE),
            has(methodCallOf(new AsmSMethodName(WithVirtualMethod.class, "myVirtualMethod", "()V"))).only().inOrder());
   }
}
