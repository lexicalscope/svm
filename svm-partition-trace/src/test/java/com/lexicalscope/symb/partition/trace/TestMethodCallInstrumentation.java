package com.lexicalscope.symb.partition.trace;

import static com.lexicalscope.MatchersAdditional.has;
import static com.lexicalscope.symb.partition.trace.PartitionBuilder.partition;
import static com.lexicalscope.symb.partition.trace.TraceMatchers.methodCallOf;
import static com.lexicalscope.symb.partition.trace.TraceMetaKey.TRACE;
import static com.lexicalscope.symb.partition.trace.TraceMethodCalls.methodCallsAt;
import static com.lexicalscope.symb.vm.conc.JvmBuilder.jvm;
import static com.lexicalscope.symb.vm.j.JavaConstants.*;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.symb.vm.conc.junit.TestEntryPoint;
import com.lexicalscope.symb.vm.conc.junit.VmRule;
import com.lexicalscope.symb.vm.j.j.code.AsmSMethodName;

public class TestMethodCallInstrumentation {
   @Rule public final VmRule vm = new VmRule(
         jvm().instrument("methodentry",
                  methodCallsAt(
                        partition().ofClass(ClassWithVirtualMethod.class).build())).
                //followedBy(Matchers.startsWith("return"), methodReturn()).
                meta(TRACE, new Trace()));

   public interface WithVirtualMethod { void myVirtualMethod(); }
   public static class ClassWithVirtualMethod implements WithVirtualMethod {
      @Override public void myVirtualMethod(){}
   }

   public static class ClassOutSidePartition {
      public void entry() {
         new ClassWithVirtualMethod().myVirtualMethod();
      }
   }

   @TestEntryPoint public static void callSomeMethods() {
      new ClassOutSidePartition().entry();
   }

   @Test public void collectVirtualMethodInTrace() throws Exception {
      vm.execute();

      assertThat(
            vm.result().state().getMeta(TRACE),
            has(methodCallOf(new AsmSMethodName(ClassWithVirtualMethod.class, INIT, NOARGS_VOID_DESC)),
                methodCallOf(new AsmSMethodName(ClassWithVirtualMethod.class, "myVirtualMethod", "()V"))).only().inOrder());
   }
}
