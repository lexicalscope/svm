package com.lexicalscope.svm.partition.trace.symb.tree;

import static com.lexicalscope.svm.j.instruction.symbolic.pc.PcBuilder.*;
import static com.lexicalscope.svm.partition.trace.symb.tree.GoalTreeCorrespondence.root;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.ISymbol;
import com.lexicalscope.svm.vm.symb.junit.Fresh;
import com.lexicalscope.svm.vm.symb.junit.SolverRule;

public class TestGoalTreeCorrespondence {
   @Rule public final SolverRule solver = new SolverRule();

   private final Object pstate0 = new Object();
   private final Object qstate0 = new Object();

   final GoalTreeCorrespondence<Object, Object> correspondenceRoot = root(pstate0, qstate0, solver.checker());

   private final Object goalp1 = new Object();
   private final Object statep1 = new Object();

   private final Object goalq1 = new Object();
   private final Object stateq1 = new Object();

   @Fresh ISymbol symbol;
   private BoolSymbol betweenThreeAndFifteen;
   private BoolSymbol betweenSevenAndEighteen;
   private BoolSymbol betweenTwentyFourAndThirty;

   @Before public void createPcs() {
      betweenThreeAndFifteen = icmpge(symbol, asISymbol(3)).and(icmple(symbol, asISymbol(15)));
      betweenSevenAndEighteen = icmpge(symbol, asISymbol(7)).and(icmple(symbol, asISymbol(18)));
      betweenTwentyFourAndThirty = icmpge(symbol, asISymbol(24)).and(icmple(symbol, asISymbol(30)));
   }

   @Test public void rootStartsAsAnOpenLeaf() throws Exception {
      assertThat(correspondenceRoot, isOpenLeaf());
   }

   @Test public void reachingAGoalInPRemainsAnOpenLeaf() throws Exception {
      correspondenceRoot.reachedP(goalp1, statep1, betweenThreeAndFifteen);
      assertThat(correspondenceRoot, isOpenLeaf());
   }

   @Test public void reachingAGoalInQRemainsAnOpenLeaf() throws Exception {
      correspondenceRoot.reachedP(goalp1, statep1, betweenThreeAndFifteen);
      assertThat(correspondenceRoot, isOpenLeaf());
   }

   @Test public void reachingOverlappingGoalsNoLongerALeaf() throws Exception {
      correspondenceRoot.reachedP(goalp1, statep1, betweenThreeAndFifteen);
      correspondenceRoot.reachedQ(goalq1, stateq1, betweenSevenAndEighteen);

      assertThat(correspondenceRoot, not(isOpenLeaf()));
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
