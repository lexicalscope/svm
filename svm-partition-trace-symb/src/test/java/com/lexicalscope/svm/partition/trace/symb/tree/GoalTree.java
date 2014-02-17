package com.lexicalscope.svm.partition.trace.symb.tree;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matcher;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.TrueSymbol;
import com.lexicalscope.svm.z3.FeasibilityChecker;

public class GoalTree<T> {
   private final Map<T, GoalTree<T>> children = new HashMap<>();
   private final FeasibilityChecker feasibilityChecker;
   private final BoolSymbol pc;

   public GoalTree() {
      this(new FeasibilityChecker());
   }

   public GoalTree(final FeasibilityChecker feasibilityChecker) {
      this(feasibilityChecker, new TrueSymbol());
   }

   public GoalTree(final FeasibilityChecker feasibilityChecker, final BoolSymbol pc) {
      this.feasibilityChecker = feasibilityChecker;
      this.pc = pc;
   }

   public BoolSymbol pc() {
      return pc;
   }

   public boolean covers(final BoolSymbol smallerPc) {
      return feasibilityChecker.covers(pc, smallerPc);
   }

   public void reached(final T goal, final BoolSymbol pc) {
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
