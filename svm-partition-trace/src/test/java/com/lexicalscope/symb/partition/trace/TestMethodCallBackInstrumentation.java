package com.lexicalscope.symb.partition.trace;

import static com.lexicalscope.MatchersAdditional.has;
import static com.lexicalscope.symb.partition.trace.PartitionBuilder.partition;
import static com.lexicalscope.symb.partition.trace.TraceMatchers.*;
import static com.lexicalscope.symb.partition.trace.TraceMetaKey.TRACE;
import static com.lexicalscope.symb.partition.trace.TraceMethodCalls.methodCallsAndReturnsThatCross;
import static com.lexicalscope.symb.vm.conc.JvmBuilder.jvm;
import static com.lexicalscope.symb.vm.j.JavaConstants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.objectweb.asm.Type.getInternalName;

import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.symb.vm.conc.junit.TestEntryPoint;
import com.lexicalscope.symb.vm.conc.junit.VmRule;

public class TestMethodCallBackInstrumentation {
   private final PartitionBuilder partition = partition().ofClass(ClassInsidePartiton.class);

   @Rule public final VmRule vm = new VmRule(
         jvm().instrument(partition.staticOverApproximateMatcher(),
                          methodCallsAndReturnsThatCross(partition)).
                meta(TRACE, new Trace()));

   public static class ClassInsidePartiton {
      public void myMethod(final ClassOutSidePartition callback){
         callback.callingBack();
      }
   }

   public static class ClassOutSidePartition {
      public void entry() {
         new ClassInsidePartiton().myMethod(this);
      }

      public void callingBack() {
         // OK
      }
   }

   @TestEntryPoint public static void callSomeMethods() {
      new ClassOutSidePartition().entry();
   }

   @Test public void collectCallbackInTrace() throws Exception {
      vm.execute();

      assertThat(
            vm.result().state().getMeta(TRACE),
            has(methodCallOf(ClassInsidePartiton.class, INIT, NOARGS_VOID_DESC),
                methodReturnOf(ClassInsidePartiton.class, INIT, NOARGS_VOID_DESC),
                methodCallOf(ClassInsidePartiton.class, "myMethod", "(L"+ getInternalName(ClassOutSidePartition.class)  +";)V"),
                methodCallOf(ClassOutSidePartition.class, "callingBack", NOARGS_VOID_DESC),
                methodReturnOf(ClassOutSidePartition.class, "callingBack", NOARGS_VOID_DESC),
                methodReturnOf(ClassInsidePartiton.class, "myMethod", "(L"+ getInternalName(ClassOutSidePartition.class)  +";)V")).only().inOrder());
   }
}
