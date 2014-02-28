package com.lexicalscope.svm.partition.trace.symb.tree;

import static com.lexicalscope.svm.j.instruction.symbolic.pc.PcBuilder.*;
import static com.lexicalscope.svm.partition.trace.symb.tree.GoalTreeCorrespondenceImpl.root;
import static com.lexicalscope.svm.partition.trace.symb.tree.GoalTreeMatchers.*;
import static com.lexicalscope.svm.search.ConstantRandomiser.constant;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.CombinableMatcher.both;
import static org.junit.rules.ExpectedException.none;

import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.ISymbol;
import com.lexicalscope.svm.vm.symb.junit.Fresh;
import com.lexicalscope.svm.vm.symb.junit.SolverRule;

public class TestGoalTreeCorrespondence {
   @Rule public final JUnitRuleMockery context = new JUnitRuleMockery();
   @Rule public final SolverRule solver = new SolverRule();
   @Rule public final ExpectedException exception = none();

   private final Object pstate0 = new Object();
   private final Object qstate0 = new Object();

   private final Object rootGoal = new Object();

   final GoalTreeCorrespondence<Object, Object> correspondence =
         root(rootGoal, pstate0, qstate0, solver.checker(), new ObjectGoalMapFactory());
   GoalTreePair<Object, Object> rootCorrespondence = correspondence.correspondence(rootGoal);

   private final Object goal1 = new Goal("goal1");
   private final Object goal2 = new Goal("goal2");

   private final Object statep1 = new Object();
   private final Object statep2 = new Object();

   private final Object stateq1 = new Object();
   private final Object stateq2 = new Object();

   @Fresh ISymbol symbol;
   private BoolSymbol betweenThreeAndSix;
   private BoolSymbol betweenThreeAndFifteen;
   private BoolSymbol betweenSevenAndEighteen;
   private BoolSymbol betweenSixteenAndThirty;

   @Before public void createPcs() {
      betweenThreeAndSix = icmpge(symbol, asISymbol(3)).and(icmple(symbol, asISymbol(6)));
      betweenThreeAndFifteen = icmpge(symbol, asISymbol(3)).and(icmple(symbol, asISymbol(15)));
      betweenSevenAndEighteen = icmpge(symbol, asISymbol(7)).and(icmple(symbol, asISymbol(18)));
      betweenSixteenAndThirty = icmpge(symbol, asISymbol(16)).and(icmple(symbol, asISymbol(30)));
   }

   @Test public void rootStartsAsAnOpenLeaf() throws Exception {
      assertThat(correspondence, isOpen());
   }

   @Test public void reachingAGoalInPRemainsAnOpenLeaf() throws Exception {
      correspondence.reachedP(rootCorrespondence, goal1, statep1, betweenThreeAndFifteen);
      assertThat(correspondence, isOpen());
      assertThat(correspondence, hasCorrespondences(1));
   }

   @Test public void reachingAGoalInQRemainsAnOpenLeaf() throws Exception {
      correspondence.reachedP(rootCorrespondence, goal1, statep1, betweenThreeAndFifteen);
      assertThat(correspondence, isOpen());
      assertThat(correspondence, hasCorrespondences(1));
   }

   @Test public void reachingCorrespondingGoalsNoLongerALeaf() throws Exception {
      correspondence.reachedP(rootCorrespondence, goal1, statep1, betweenThreeAndFifteen);
      correspondence.reachedQ(rootCorrespondence, goal1, stateq1, betweenSevenAndEighteen);

      assertThat(correspondence, isOpen());
      assertThat(correspondence, hasCorrespondences(2));
      assertThat(correspondence,
            hasCorrespondence(
                  both(covers(betweenThreeAndFifteen))
                  .and(covers(betweenSevenAndEighteen))));
   }

   @Test public void reachingFurtherCorrespondingGoalsDoesNotCreateMoreChildren() throws Exception {
      correspondence.reachedP(rootCorrespondence, goal1, statep1, betweenThreeAndFifteen);
      correspondence.reachedQ(rootCorrespondence, goal1, stateq1, betweenSevenAndEighteen);

      correspondence.reachedQ(rootCorrespondence, goal1, stateq2, betweenSixteenAndThirty);

      assertThat(correspondence, hasCorrespondences(2));
      assertThat(correspondence,
            hasCorrespondence(
                  both(covers(betweenThreeAndFifteen))
                  .and(covers(betweenSevenAndEighteen))
                  .and(covers(betweenSixteenAndThirty))));
   }

   @Test public void reachingNonCorrespondingGoalsCreatesMoreChildren() throws Exception {
      correspondence.reachedP(rootCorrespondence, goal1, statep1, betweenThreeAndFifteen);
      correspondence.reachedQ(rootCorrespondence, goal1, stateq1, betweenThreeAndFifteen);

      correspondence.reachedQ(rootCorrespondence, goal2, stateq2, betweenSixteenAndThirty);
      correspondence.reachedP(rootCorrespondence, goal2, statep2, betweenSixteenAndThirty);

      assertThat(correspondence, hasCorrespondences(3));
      assertThat(correspondence,
            hasCorrespondence(covers(betweenThreeAndFifteen)));
      assertThat(correspondence,
            hasCorrespondence(covers(betweenSixteenAndThirty)));


   }

   @Test public void reachingNonCorrespondingGoalsThatOverlapIsAnErrorInQ() throws Exception {
      correspondence.reachedP(rootCorrespondence, goal1, statep1, betweenThreeAndFifteen);
      correspondence.reachedQ(rootCorrespondence, goal1, stateq1, betweenThreeAndSix);

      exception.expectMessage("unbounded");
      correspondence.reachedQ(rootCorrespondence, goal2, stateq2, betweenSevenAndEighteen);
   }

   @Test public void reachingNonCorrespondingGoalsThatOverlapIsAnErrorInP() throws Exception {
      correspondence.reachedQ(rootCorrespondence, goal1, stateq1, betweenThreeAndFifteen);
      correspondence.reachedP(rootCorrespondence, goal1, statep1, betweenThreeAndSix);

      exception.expectMessage("unbounded");
      correspondence.reachedP(rootCorrespondence, goal2, statep2, betweenSevenAndEighteen);
   }

   @Test public void canPickRandomCorrespondence() throws Exception {
      correspondence.reachedP(rootCorrespondence, goal1, statep1, betweenThreeAndFifteen);
      final GoalTreePair<Object, Object> firstChild = correspondence.reachedQ(rootCorrespondence, goal1, stateq1, betweenThreeAndFifteen);

      correspondence.reachedQ(rootCorrespondence, goal2, stateq2, betweenSixteenAndThirty);
      final GoalTreePair<Object, Object> secondChild = correspondence.reachedP(rootCorrespondence, goal2, statep2, betweenSixteenAndThirty);

      assertThat(correspondence.randomOpenCorrespondence(constant(1)), equalTo(firstChild));
      assertThat(correspondence.randomOpenCorrespondence(constant(2)), equalTo(secondChild));
   }
}
