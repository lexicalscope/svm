package com.lexicalscope.svm.partition.trace.symb.tree;

import static com.lexicalscope.svm.partition.trace.symb.tree.GoalTreePair.*;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.svm.vm.symb.junit.SolverRule;

public class TestGoalTreePair {
   @Rule public final SolverRule solver = new SolverRule();

   final GoalTreePair<Object, Object> goalTreePairWithPSide =
            goalTreePairWithPSide(new GoalTree<Object, Object>(solver.checker()));
   final GoalTreePair<Object, Object> goalTreePairWithQSide =
            goalTreePairWithQSide(new GoalTree<Object, Object>(solver.checker()));

   @Test public void goalTreeCanBeCreatedWhenPSideIsFound() throws Exception {
      assertThat("qside is missing", !goalTreePairWithPSide.hasQSide());
      assertThat("pside is present", goalTreePairWithPSide.hasPSide());
   }

   @Test public void goalTreeCanBeCreatedWhenQSideIsFound() throws Exception {
      assertThat("qside is missing", goalTreePairWithQSide.hasQSide());
      assertThat("pside is present", !goalTreePairWithQSide.hasPSide());
   }
}
