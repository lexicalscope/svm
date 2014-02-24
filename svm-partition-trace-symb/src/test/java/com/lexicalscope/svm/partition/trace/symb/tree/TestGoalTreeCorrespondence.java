package com.lexicalscope.svm.partition.trace.symb.tree;

import static com.lexicalscope.svm.j.instruction.symbolic.pc.PcBuilder.*;
import static com.lexicalscope.svm.partition.trace.symb.tree.GoalTreeCorrespondence.root;
import static com.lexicalscope.svm.partition.trace.symb.tree.GoalTreeMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.CombinableMatcher.both;

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

   final GoalTreeCorrespondence<Object, Object> correspondenceRoot =
         root(pstate0, qstate0, solver.checker(), new ObjectGoalMapFactory());

   private final Object goal1 = new Object();
   private final Object goal2 = new Object();

   private final Object statep1 = new Object();
   private final Object statep2 = new Object();

   private final Object stateq1 = new Object();
   private final Object stateq2 = new Object();

   @Fresh ISymbol symbol;
   private BoolSymbol betweenThreeAndFifteen;
   private BoolSymbol betweenSevenAndEighteen;
   private BoolSymbol betweenSixteenAndThirty;

   @Before public void createPcs() {
      betweenThreeAndFifteen = icmpge(symbol, asISymbol(3)).and(icmple(symbol, asISymbol(15)));
      betweenSevenAndEighteen = icmpge(symbol, asISymbol(7)).and(icmple(symbol, asISymbol(18)));
      betweenSixteenAndThirty = icmpge(symbol, asISymbol(16)).and(icmple(symbol, asISymbol(30)));
   }

   @Test public void rootStartsAsAnOpenLeaf() throws Exception {
      assertThat(correspondenceRoot, GoalTreeMatchers.isOpenLeaf());
   }

   @Test public void reachingAGoalInPRemainsAnOpenLeaf() throws Exception {
      correspondenceRoot.reachedP(goal1, statep1, betweenThreeAndFifteen);
      assertThat(correspondenceRoot, GoalTreeMatchers.isOpenLeaf());
   }

   @Test public void reachingAGoalInQRemainsAnOpenLeaf() throws Exception {
      correspondenceRoot.reachedP(goal1, statep1, betweenThreeAndFifteen);
      assertThat(correspondenceRoot, GoalTreeMatchers.isOpenLeaf());
   }

   @Test public void reachingCorrespondingGoalsNoLongerALeaf() throws Exception {
      correspondenceRoot.reachedP(goal1, statep1, betweenThreeAndFifteen);
      correspondenceRoot.reachedQ(goal1, stateq1, betweenSevenAndEighteen);

      assertThat(correspondenceRoot, not(isLeaf()));
      assertThat(correspondenceRoot, isOpen());
      assertThat(correspondenceRoot, hasChildCorrespondences(1));
      assertThat(correspondenceRoot,
            hasChildCorrespondence(
                  both(covers(betweenThreeAndFifteen))
                  .and(covers(betweenSevenAndEighteen))));
   }

   @Test public void reachingFurtherCorrespondingGoalsDoesNotCreateMoreChildren() throws Exception {
      correspondenceRoot.reachedP(goal1, statep1, betweenThreeAndFifteen);
      correspondenceRoot.reachedQ(goal1, stateq1, betweenSevenAndEighteen);

      correspondenceRoot.reachedQ(goal1, stateq2, betweenSixteenAndThirty);

      assertThat(correspondenceRoot, hasChildCorrespondences(1));
      assertThat(correspondenceRoot,
            hasChildCorrespondence(
                  both(covers(betweenThreeAndFifteen))
                  .and(covers(betweenSevenAndEighteen))
                  .and(covers(betweenSixteenAndThirty))));
   }

   @Test public void reachingNonCorrespondingGoalsCreatesMoreChildren() throws Exception {
      correspondenceRoot.reachedP(goal1, statep1, betweenThreeAndFifteen);
      correspondenceRoot.reachedQ(goal1, stateq1, betweenThreeAndFifteen);

      correspondenceRoot.reachedQ(goal2, stateq2, betweenSixteenAndThirty);
      correspondenceRoot.reachedP(goal2, statep2, betweenSixteenAndThirty);

      assertThat(correspondenceRoot, hasChildCorrespondences(2));
      assertThat(correspondenceRoot,
            hasChildCorrespondence(covers(betweenThreeAndFifteen)));
      assertThat(correspondenceRoot,
            hasChildCorrespondence(covers(betweenSixteenAndThirty)));
   }
}
