package com.lexicalscope.svm.partition.trace;

import static com.lexicalscope.MatchersAdditional.has;
import static com.lexicalscope.svm.partition.trace.PartitionBuilder.partition;
import static com.lexicalscope.svm.partition.trace.PartitionInstrumentation.instrumentPartition;
import static com.lexicalscope.svm.partition.trace.TraceBuilder.trace;
import static com.lexicalscope.svm.partition.trace.TraceMatchers.*;
import static com.lexicalscope.svm.partition.trace.TraceMetaKey.TRACE;
import static com.lexicalscope.svm.vm.j.JavaConstants.*;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.svm.vm.conc.junit.TestEntryPoint;
import com.lexicalscope.svm.vm.conc.junit.VmRule;
import com.lexicalscope.svm.vm.j.code.AsmSMethodName;

public class TestMethodCallInstrumentation {

   @Rule public final VmRule vm = new VmRule();
   {
      instrumentPartition(partition().ofClass(ClassWithVirtualMethod.class), partition().ofClass(ClassOutSidePartition.class), vm);
      vm.builder().initialState().meta(TRACE, trace().build());
   }

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
            vm.result().getMeta(TRACE),
            has(methodCallOf(ClassWithVirtualMethod.class, INIT, NOARGS_VOID_DESC),
                methodReturnOf(ClassWithVirtualMethod.class, INIT, NOARGS_VOID_DESC),
                methodCallOf(ClassWithVirtualMethod.class, "myVirtualMethod", "()V"),
                methodReturnOf(new AsmSMethodName(ClassWithVirtualMethod.class, "myVirtualMethod", "()V"))).only().inOrder());
   }
}
