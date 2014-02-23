package com.lexicalscope.svm.partition.trace.symb.tree;

import static com.lexicalscope.svm.j.instruction.symbolic.pc.PcBuilder.*;
import static org.hamcrest.Matchers.both;
import static org.junit.Assert.assertThat;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.ISymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.TrueSymbol;
import com.lexicalscope.svm.vm.symb.junit.Fresh;
import com.lexicalscope.svm.vm.symb.junit.SolverRule;

public class TestGoalTree {
   @Rule public final SolverRule solver = new SolverRule();
   final GoalTree<Object> goalTree = new GoalTree<>(solver.checker());
   final BoolSymbol rootPc = new TrueSymbol();

   @Fresh ISymbol symbol;

   final Object goal1 = new Object();
   final Object goal2 = new Object();

   private BoolSymbol betweenThreeAndFifteen;
   private BoolSymbol betweenTwentyFourAndThirty;

   @Before public void createPcs() {
      betweenThreeAndFifteen = icmpge(symbol, asISymbol(3)).and(icmple(symbol, asISymbol(15)));
      betweenTwentyFourAndThirty = icmpge(symbol, asISymbol(24)).and(icmple(symbol, asISymbol(30)));
   }

   @Test public void rootHasEmptyPc() throws Exception {
      assertThat(goalTree, covers(rootPc));
   }

   @Test public void firstGoalCreatesFirstChild() throws Exception {
      goalTree.reached(goal1, betweenThreeAndFifteen);
      assertThat(goalTree, hasChild(covers(betweenThreeAndFifteen)));
   }

   @Test public void parentKeepsTrackOfTheCoveredSetOfItsChildren() throws Exception {
      goalTree.reached(goal1, betweenTwentyFourAndThirty);
      goalTree.reached(goal2, betweenThreeAndFifteen);

      assertThat(goalTree, both(childrenCover(betweenThreeAndFifteen))
                           .and(childrenCover(betweenTwentyFourAndThirty)));
   }

   @Test public void reachingGoalTwoWaysUpdatesCoveredSetOfItsChildre() throws Exception {
      goalTree.reached(goal1, betweenThreeAndFifteen);
      goalTree.reached(goal1, betweenTwentyFourAndThirty);

      assertThat(goalTree, childrenCover(betweenThreeAndFifteen.or(betweenTwentyFourAndThirty)));
      assertThat(goalTree, hasChild(
             both(covers(betweenThreeAndFifteen))
             .and(covers(betweenTwentyFourAndThirty))));
   }

   private Matcher<? super GoalTree<Object>> childrenCover(final BoolSymbol pc) {
      return new TypeSafeDiagnosingMatcher<GoalTree<?>>() {
         @Override public void describeTo(final Description description) {
            description.appendText("node with chidren that cover ").appendValue(pc);
         }

         @Override protected boolean matchesSafely(final GoalTree<?> item, final Description mismatchDescription) {
            mismatchDescription.appendValue(item);
            return item.childrenCover(pc);
         }
      };
   }

   private Matcher<GoalTree<?>> hasChild(final Matcher<? super GoalTree<?>> childMatcher) {
      return new TypeSafeDiagnosingMatcher<GoalTree<?>>() {
         @Override public void describeTo(final Description description) {
            description.appendText("child matching ").appendDescriptionOf(childMatcher);
         }

         @Override protected boolean matchesSafely(final GoalTree<?> item, final Description mismatchDescription) {
            mismatchDescription.appendValue(item);
            return item.hasChild(childMatcher);
         }
      };
   }

   private Matcher<GoalTree<?>> covers(final BoolSymbol pc) {
      return new TypeSafeDiagnosingMatcher<GoalTree<?>>() {
         @Override public void describeTo(final Description description) {
            description.appendText("goal tree that covers ").appendValue(pc);
         }

         @Override protected boolean matchesSafely(final GoalTree<?> item, final Description mismatchDescription) {
            mismatchDescription.appendText("goal tree that covers ").appendValue(item.pc());
            return item.covers(pc);
         }
      };
   }
}
