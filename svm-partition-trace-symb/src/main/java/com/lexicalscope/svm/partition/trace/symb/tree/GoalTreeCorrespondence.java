package com.lexicalscope.svm.partition.trace.symb.tree;

import java.util.HashMap;
import java.util.Map;

import com.lexicalscope.svm.z3.FeasibilityChecker;


public class GoalTreeCorrespondence<T, S> {
   private final GoalTree<T, S> pside;
   private final GoalTree<T, S> qside;
   private final Map<T, GoalTreeCorrespondence<T, S>> children = new HashMap<>();

   private GoalTreeCorrespondence(final GoalTree<T, S> pside, final GoalTree<T, S> qside) {
      this.pside = pside;
      this.qside = qside;
   }

   public boolean hasChildren() {
      return !children.isEmpty();
   }

   public boolean isOpen() {
      return pside.isOpen() || qside.isOpen();
   }

   public static <T, S> GoalTreeCorrespondence<T, S> root(
         final S pstate0,
         final S qstate0,
         final FeasibilityChecker feasibilityChecker) {
      final GoalTree<T, S> pside = new GoalTree<>(feasibilityChecker);
      final GoalTree<T, S> qside = new GoalTree<>(feasibilityChecker);

      pside.increaseOpenNodes(pstate0);
      qside.increaseOpenNodes(qstate0);

      return new GoalTreeCorrespondence<>(pside, qside);
   }
}
