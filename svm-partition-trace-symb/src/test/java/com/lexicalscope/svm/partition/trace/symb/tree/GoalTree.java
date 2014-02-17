package com.lexicalscope.svm.partition.trace.symb.tree;

import static com.google.common.base.Joiner.on;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matcher;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.TrueSymbol;
import com.lexicalscope.svm.z3.FeasibilityChecker;

public class GoalTree<T> {
   private final Map<T, GoalTree<T>> children = new HashMap<>();
   private final FeasibilityChecker feasibilityChecker;
   private final BoolSymbol coveredPc;
   private BoolSymbol childrenCoverPc;

   public GoalTree() {
      this(new FeasibilityChecker());
   }

   public GoalTree(final FeasibilityChecker feasibilityChecker) {
      this(feasibilityChecker, new TrueSymbol());
   }

   public GoalTree(final FeasibilityChecker feasibilityChecker, final BoolSymbol pc) {
      this.feasibilityChecker = feasibilityChecker;
      this.coveredPc = pc;
      this.childrenCoverPc = new TrueSymbol();
   }

   public BoolSymbol pc() {
      return coveredPc;
   }

   public boolean covers(final BoolSymbol smallerPc) {
      return feasibilityChecker.covers(coveredPc, smallerPc);
   }

   public void reached(final T goal, final BoolSymbol pc) {
      childrenCoverPc = childrenCoverPc.or(pc);
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

   public boolean childrenCover(final BoolSymbol smallerPc) {
      return feasibilityChecker.covers(childrenCoverPc, smallerPc);
   }

   @Override public String toString() {
      return String.format("(node %s %s)", coveredPc, on(" ").join(children.values()));
   }
}
