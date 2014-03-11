package com.lexicalscope.svm.partition.trace;

import static com.lexicalscope.svm.partition.trace.TraceMethodCalls.methodCallsAndReturnsThatCross;

import com.lexicalscope.svm.vm.conc.junit.VmRule;


public class PartitionInstrumentation {
   public static void instrumentPartition(
         final PartitionBuilder aPart,
         final PartitionBuilder mPart,
         final VmRule vm) {
      vm.initialStateBuilder().instrument(aPart.staticOverApproximateMatcher(),
            methodCallsAndReturnsThatCross(aPart));
   }
}
