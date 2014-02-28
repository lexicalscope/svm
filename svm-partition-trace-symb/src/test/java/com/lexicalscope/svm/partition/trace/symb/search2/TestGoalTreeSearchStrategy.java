package com.lexicalscope.svm.partition.trace.symb.search2;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.svm.partition.trace.symb.tree.GoalTreeCorrespondence;
import com.lexicalscope.svm.search2.GoalTreeGuidedSearchStrategy;

public class TestGoalTreeSearchStrategy {
   @Rule public final JUnitRuleMockery context = new JUnitRuleMockery();
   @Mock private GoalTreeCorrespondence<Object, Object> correspondence;

   GoalTreeGuidedSearchStrategy<Object, Object> searchStrategy;

   @Before public void createStrategy() {
      searchStrategy = new GoalTreeGuidedSearchStrategy<>(correspondence);
   }

   @Test @Ignore public void searchingIfOpenNodes() throws Exception {
      context.checking(new Expectations(){{
//         oneOf(correspondence).
      }});
      assertThat(searchStrategy.pendingState().state(), equalTo(new Object()));
   }
}
