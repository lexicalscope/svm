package com.lexicalscope.svm.partition.trace.symb.tree;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.svm.vm.symb.junit.SolverRule;

public class TestOpenNodes {
   @Rule public final SolverRule solver = new SolverRule();
   final OpenNodes<Object> openNodes = new OpenNodes<>(solver.checker());

   private final Object node1 = new Object();

   @Test public void singlePushedNodeIsPopped() throws Exception {
      openNodes.push(node1);
      assertThat(openNodes.pop(), equalTo(node1));
   }
}
