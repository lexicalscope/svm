package com.lexicalscope.svm.partition.trace.symb.tree;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matcher;

import com.lexicalscope.svm.j.instruction.symbolic.pc.Pc;
import com.lexicalscope.svm.z3.FeasibilityChecker;

public class GoalTree<T> {
   private final Map<T, GoalTree<T>> children = new HashMap<>();
   private final FeasibilityChecker feasibilityChecker;
   private final Pc pc;

   public GoalTree() {
      this(new FeasibilityChecker());
   }

   public GoalTree(final FeasibilityChecker feasibilityChecker) {
      this(feasibilityChecker, new Pc());
   }

   public GoalTree(final FeasibilityChecker feasibilityChecker, final Pc pc) {
      this.feasibilityChecker = feasibilityChecker;
      this.pc = pc;
   }

   public Pc pc() {
      return pc;
   }

   public boolean covers(final Pc smallerPc) {
      return feasibilityChecker.covers(pc, smallerPc);
   }

   public void reached(final T goal, final Pc pc) {
      children.put(goal, new GoalTree<T>(feasibilityChecker, pc));
   }

   public boolean hasChild(final Matcher<? super GoalTree<?>> childMatcher) {
      for (final GoalTree<T> child : children.values()) {
         if(childMatcher.matches(child)) {
            return true;
         }
      }
      return false;
   }
}
