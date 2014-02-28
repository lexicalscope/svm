package com.lexicalscope.svm.partition.trace;

import static com.lexicalscope.MatchersAdditional.has;
import static com.lexicalscope.svm.partition.trace.PartitionBuilder.partition;
import static com.lexicalscope.svm.partition.trace.PartitionInstrumentation.instrumentPartition;
import static com.lexicalscope.svm.partition.trace.TraceMatchers.*;
import static com.lexicalscope.svm.partition.trace.TraceMetaKey.TRACE;
import static com.lexicalscope.svm.vm.j.code.AsmSMethodName.defaultConstructor;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.objectweb.asm.Type.getInternalName;

import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.svm.vm.conc.junit.TestEntryPoint;
import com.lexicalscope.svm.vm.conc.junit.VmRule;

public class TestMethodCallBackInstrumentation {
   private final PartitionBuilder partition = partition().ofClass(ClassInsidePartiton.class);

   @Rule public final VmRule vm = new VmRule();
   {
      instrumentPartition(partition, vm);
   }

   public static class ClassInsidePartiton {
      public int myMethod(final ClassOutSidePartition callback){
         return callback.callingBack();
      }
   }

   public static class ClassOutSidePartition {
      public void entry() {
         new ClassInsidePartiton().myMethod(this);
      }

      public int callingBack() {
         return 4;
      }
   }

   @TestEntryPoint public static void callSomeMethods() {
      new ClassOutSidePartition().entry();
   }

   @Test public void collectCallbackInTrace() throws Exception {
      vm.execute();


      assertThat(
            vm.result().getMeta(TRACE),
            has(methodCallOf(defaultConstructor(ClassInsidePartiton.class)),
                methodReturnOf(defaultConstructor(ClassInsidePartiton.class)),
                methodCallOf(ClassInsidePartiton.class, "myMethod", "(L"+ getInternalName(ClassOutSidePartition.class)  +";)I", any(Object.class), any(Object.class)),
                methodCallOf(ClassOutSidePartition.class, "callingBack", "()I", any(Object.class)),
                methodReturnOf(ClassOutSidePartition.class, "callingBack", "()I", equalTo((Object) 4)),
                methodReturnOf(ClassInsidePartiton.class, "myMethod", "(L"+ getInternalName(ClassOutSidePartition.class)  +";)I", equalTo((Object) 4))).only().inOrder());
   }
}
