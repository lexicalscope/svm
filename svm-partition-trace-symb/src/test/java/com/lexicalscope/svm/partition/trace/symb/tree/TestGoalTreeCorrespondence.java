package com.lexicalscope.svm.partition.trace.symb.tree;

import static com.lexicalscope.svm.partition.trace.symb.tree.GoalTreeCorrespondence.root;
import static org.hamcrest.MatcherAssert.assertThat;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.svm.vm.symb.junit.SolverRule;

public class TestGoalTreeCorrespondence {
   @Rule public final SolverRule solver = new SolverRule();

   private final Object pstate0 = new Object();
   private final Object qstate0 = new Object();

   final GoalTreeCorrespondence<Object, Object> correspondenceRoot = root(pstate0, qstate0, solver.checker());

   @Test public void rootStartsAsAnOpenLeaf() throws Exception {
      assertThat(correspondenceRoot, isOpenLeaf());
   }

   private Matcher<? super GoalTreeCorrespondence<?, ?>> isOpenLeaf() {
      return new TypeSafeDiagnosingMatcher<GoalTreeCorrespondence<?, ?>>(){
         @Override public void describeTo(final Description description) {
            description.appendText("correspondence with no children and open nodes");
         }

         @Override protected boolean matchesSafely(final GoalTreeCorrespondence<?, ?> item, final Description mismatchDescription) {
            final boolean result = !item.hasChildren() && item.isOpen();
            if(!result) {
               mismatchDescription.appendValue(item);
            }
            return result;
         }};
   }
}
