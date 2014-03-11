package com.lexicalscope.svm.partition.trace;

import static com.lexicalscope.MatchersAdditional.has;
import static com.lexicalscope.svm.partition.trace.PartitionBuilder.partition;
import static com.lexicalscope.svm.partition.trace.PartitionInstrumentation.instrumentPartition;
import static com.lexicalscope.svm.partition.trace.TraceBuilder.trace;
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

   @Rule public final VmRule vm = new VmRule();
   {
      instrumentPartition(partition().ofClass(ClassInsidePartition.class), partition().ofClass(ClassOutsidePartition.class), vm);
      vm.builder().initialState().meta(TRACE, trace().build());
   }

   public static class ClassInsidePartition {
      public int myMethod(final ClassOutsidePartition callback){
         return callback.callingBack();
      }
   }

   public static class ClassOutsidePartition {
      public void entry() {
         new ClassInsidePartition().myMethod(this);
      }

      public int callingBack() {
         return 4;
      }
   }

   @TestEntryPoint public static void callSomeMethods() {
      new ClassOutsidePartition().entry();
   }

   @Test public void collectCallbackInTrace() throws Exception {
      vm.execute();


      assertThat(
            vm.result().getMeta(TRACE),
            has(methodCallOf(defaultConstructor(ClassInsidePartition.class)),
                methodReturnOf(defaultConstructor(ClassInsidePartition.class)),
                methodCallOf(ClassInsidePartition.class, "myMethod", "(L"+ getInternalName(ClassOutsidePartition.class)  +";)I", any(Object.class), any(Object.class)),
                methodCallOf(ClassOutsidePartition.class, "callingBack", "()I", any(Object.class)),
                methodReturnOf(ClassOutsidePartition.class, "callingBack", "()I", equalTo((Object) 4)),
                methodReturnOf(ClassInsidePartition.class, "myMethod", "(L"+ getInternalName(ClassOutsidePartition.class)  +";)I", equalTo((Object) 4))).only().inOrder());
   }
}
