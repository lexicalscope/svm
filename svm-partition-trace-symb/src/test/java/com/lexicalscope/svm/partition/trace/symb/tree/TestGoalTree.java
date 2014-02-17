package com.lexicalscope.svm.partition.trace.symb.tree;

import static com.lexicalscope.svm.j.instruction.symbolic.pc.PcBuilder.*;
import static org.junit.Assert.assertThat;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.junit.Ignore;
import org.junit.Test;

import com.lexicalscope.svm.j.instruction.symbolic.pc.Pc;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.ISymbol;
import com.lexicalscope.svm.vm.symb.junit.Fresh;
import com.lexicalscope.svm.vm.symb.junit.SolverRule;

public class TestGoalTree {
   public final SolverRule solver = new SolverRule();
   final GoalTree<Object> goalTree = new GoalTree<>(solver.checker());
   final Pc rootPc = new Pc();

   @Fresh ISymbol symbol;

   @Test public void rootHasEmptyPc() throws Exception {
      assertThat(goalTree, covers(rootPc));
   }

   @Test @Ignore public void firstGoalCreatesFirstChild() throws Exception {
      goalTree.reached(new Object(), rootPc.and(icmplt(symbol, asISymbol(3))));

      assertThat(goalTree, hasChild(covers(new Pc())));
   }

   private Matcher<GoalTree<?>> hasChild(final Matcher<? super GoalTree<?>> childMatcher) {
      return new TypeSafeDiagnosingMatcher<GoalTree<?>>() {
         @Override public void describeTo(final Description description) {
            // TODO Auto-generated method stub

         }

         @Override protected boolean matchesSafely(final GoalTree<?> item, final Description mismatchDescription) {
            // TODO Auto-generated method stub
            return false;
         }
      };
   }

   private Matcher<GoalTree<?>> covers(final Pc pc) {
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
