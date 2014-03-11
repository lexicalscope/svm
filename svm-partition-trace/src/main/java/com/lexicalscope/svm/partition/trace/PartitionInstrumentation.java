package com.lexicalscope.svm.partition.trace;

import static com.lexicalscope.svm.partition.trace.TraceMethodCalls.methodCallsAndReturnsThatCross;
import static com.lexicalscope.svm.partition.trace.TrackPartitionAtConstruction.constructionOf;
import static com.lexicalscope.svm.vm.j.klass.SMethodDescriptorMatchers.anyMethod;

import com.lexicalscope.svm.vm.conc.junit.VmRule;


public class PartitionInstrumentation {
   public static void instrumentPartition(
         final PartitionBuilder aPart,
         final PartitionBuilder mPart,
         final VmRule vm) {
      vm.initialStateBuilder().instrument(anyMethod(), constructionOf(aPart, mPart));
      vm.initialStateBuilder().instrument(aPart.staticOverApproximateMatcher(),
            methodCallsAndReturnsThatCross(aPart));
   }
}
