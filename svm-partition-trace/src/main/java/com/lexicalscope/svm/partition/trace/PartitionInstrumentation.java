package com.lexicalscope.svm.partition.trace;

import static com.lexicalscope.svm.partition.trace.TraceMethodCalls.methodCallsAndReturnsThatCross;

import com.lexicalscope.svm.vm.conc.junit.VmRule;


public class PartitionInstrumentation {
   public static void instrumentPartition(final PartitionBuilder partition, final VmRule vm) {
      vm.initialStateBuilder().instrument(partition.staticOverApproximateMatcher(),
            methodCallsAndReturnsThatCross(partition));
   }
}
