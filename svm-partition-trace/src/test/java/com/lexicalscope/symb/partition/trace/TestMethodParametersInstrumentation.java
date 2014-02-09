package com.lexicalscope.symb.partition.trace;

import static com.lexicalscope.MatchersAdditional.has;
import static com.lexicalscope.symb.partition.trace.PartitionBuilder.partition;
import static com.lexicalscope.symb.partition.trace.TraceMatchers.*;
import static com.lexicalscope.symb.partition.trace.TraceMetaKey.TRACE;
import static com.lexicalscope.symb.partition.trace.TraceMethodCalls.methodCallsAndReturnsThatCross;
import static com.lexicalscope.symb.vm.conc.JvmBuilder.jvm;
import static com.lexicalscope.symb.vm.j.JavaConstants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.symb.vm.conc.junit.TestEntryPoint;
import com.lexicalscope.symb.vm.conc.junit.VmRule;

public class TestMethodParametersInstrumentation {
   private final PartitionBuilder partition = partition().ofClass(ClassInsidePartition.class);

   @Rule public final VmRule vm = new VmRule(
         jvm().instrument(partition.staticOverApproximateMatcher(),
                          methodCallsAndReturnsThatCross(partition)).
                meta(TRACE, new Trace()));

   public static class ClassInsidePartition {
      public int myMethod(final int x){ return x*2; }
   }

   public static class ClassOutSidePartition {
      public int entry(final int x) {
         return new ClassInsidePartition().myMethod(x);
      }
   }

   @TestEntryPoint public static void callSomeMethods() {
      new ClassOutSidePartition().entry(5);
   }

   @Test public void collectArgumentsInTrace() throws Exception {
      vm.execute();

      assertThat(
            vm.result().state().getMeta(TRACE),
            has(methodCallOf(ClassInsidePartition.class, INIT, NOARGS_VOID_DESC),
                methodReturnOf(ClassInsidePartition.class, INIT, NOARGS_VOID_DESC),
                methodCallOf(ClassInsidePartition.class, "myMethod", "(I)I", any(Object.class), equalTo((Object) 5)),
                methodReturnOf(ClassInsidePartition.class, "myMethod", "(I)I", equalTo((Object) 10))).only().inOrder());
   }
}
