package com.lexicalscope.svm.partition.trace.symb.tree;


public class GoalTreePair<T, S> {
   private final GoalTree<T, S> pside;
   private final GoalTree<T, S> qside;

   private GoalTreePair(final GoalTree<T, S> pside, final GoalTree<T, S> qside) {
      this.pside = pside;
      this.qside = qside;
   }

   public static <T, S> GoalTreePair<T, S> goalTreePairWithPSide(final GoalTree<T, S> pside) {
      return new GoalTreePair<T, S>(pside, null);
   }

   public static <T, S> GoalTreePair<T, S> goalTreePairWithQSide(final GoalTree<T, S> qside) {
      return new GoalTreePair<T, S>(null, qside);
   }

   public boolean hasQSide() {
      return qside != null;
   }

   public boolean hasPSide() {
      return pside != null;
   }
}
