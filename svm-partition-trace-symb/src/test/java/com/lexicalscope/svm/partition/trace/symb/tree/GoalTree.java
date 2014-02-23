package com.lexicalscope.svm.partition.trace.symb.tree;

import static com.google.common.base.Joiner.on;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matcher;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.FalseSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.TrueSymbol;
import com.lexicalscope.svm.z3.FeasibilityChecker;

public class GoalTree<T> {
   private final Map<T, GoalTree<T>> children = new HashMap<>();
   private final FeasibilityChecker feasibilityChecker;
   private BoolSymbol coveredPc;
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
      this.childrenCoverPc = new FalseSymbol();
   }

   public BoolSymbol pc() {
      return coveredPc;
   }

   public boolean covers(final BoolSymbol pc) {
      return feasibilityChecker.implies(pc, coveredPc);
   }

   private void increaseCovers(final BoolSymbol conjunct) {
      coveredPc = coveredPc.or(conjunct);
   }

   public void reached(final T goal, final BoolSymbol childPc) {
      childrenCoverPc = childrenCoverPc.or(childPc);
      if(children.containsKey(goal)) {
         children.get(goal).increaseCovers(childPc);
      } else {
         children.put(goal, new GoalTree<T>(feasibilityChecker, childPc));
      }
   }

   public boolean hasChild(final Matcher<? super GoalTree<?>> childMatcher) {
      for (final GoalTree<T> child : children.values()) {
         if(childMatcher.matches(child)) {
            return true;
         }
      }
      return false;
   }

   public boolean childrenCover(final BoolSymbol pc) {
      return feasibilityChecker.implies(pc, childrenCoverPc);
   }

   @Override public String toString() {
      return String.format("(node (covers %s) (children cover %s) %s)", coveredPc, childrenCoverPc, on(" ").join(children.values()));
   }
}
