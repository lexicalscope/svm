package com.lexicalscope.svm.partition.trace.symb.search2;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.svm.partition.trace.symb.tree.GoalTreeCorrespondence;
import com.lexicalscope.svm.search.Randomiser;
import com.lexicalscope.svm.search2.GoalTreeGuidedSearchStrategy;

public class TestGoalTreeSearchStrategy {
   @Rule public final JUnitRuleMockery context = new JUnitRuleMockery();
   @Mock private GoalTreeCorrespondence<Object, FakeVmState> correspondence;
   @Mock private Randomiser randomiser;

   GoalTreeGuidedSearchStrategy<Object, FakeVmState> searchStrategy;

   @Before public void createStrategy() {
      searchStrategy = null; //new GoalTreeGuidedSearchStrategy<Object, FakeFlowNode>(correspondence);
   }

   @Test @Ignore public void searchingIfOpenNodes() throws Exception {
      final FakeVmState state = new FakeVmState();
      context.checking(new Expectations(){{
         oneOf(correspondence).randomOpenCorrespondence(randomiser); will(returnValue(state));
      }});
      //assertThat(searchStrategy.pendingState(), equalTo(state));
   }
}
