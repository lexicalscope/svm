package com.lexicalscope.svm.partition.trace.symb.search2;

import com.lexicalscope.svm.vm.FlowNode;

public class FakeFlowNode implements FlowNode<FakeFlowNode> {
   @Override public FakeFlowNode state() {
      return this;
   }

   @Override public void eval() {
      // does nothing
   }
}
