package com.lexicalscope.svm.partition.trace.symb.search2;

import static com.lexicalscope.svm.partition.trace.symb.tree.GoalTree.goalTree;
import static com.lexicalscope.svm.partition.trace.symb.tree.GoalTreePair.pair;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.svm.partition.trace.symb.tree.GoalTreeCorrespondence;
import com.lexicalscope.svm.partition.trace.symb.tree.GoalTreePair;
import com.lexicalscope.svm.search.Randomiser;
import com.lexicalscope.svm.search2.GoalTreeGuidedSearchStrategy;

public class TestGoalTreeGuidedSearchStrategy {
   @Rule public final JUnitRuleMockery context = new JUnitRuleMockery();
   @Mock private GoalTreeCorrespondence<Object, FakeVmState> correspondence;
   @Mock private Randomiser randomiser;

   GoalTreeGuidedSearchStrategy<Object, FakeVmState> searchStrategy;

   @Before public void createStrategy() {
      searchStrategy = new GoalTreeGuidedSearchStrategy<Object, FakeVmState>(correspondence, randomiser);
   }

   @Test public void searchingIfOpenNodes() throws Exception {
      final FakeVmState pstate = new FakeVmState();
      final FakeVmState qstate = new FakeVmState();

      final GoalTreePair<Object, FakeVmState> pair = pair(goalTree(pstate), goalTree(qstate));

      context.checking(new Expectations(){{
         exactly(2).of(randomiser).random(1); will(returnValue(0));
         oneOf(correspondence).randomOpenCorrespondence(randomiser); will(returnValue(pair));
      }});
      assertThat(searchStrategy.pendingState(), equalTo(pstate));
      assertThat(searchStrategy.pendingState(), equalTo(qstate));
   }
}
