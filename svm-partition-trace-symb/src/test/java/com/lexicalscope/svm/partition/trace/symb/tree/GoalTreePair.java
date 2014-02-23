package com.lexicalscope.svm.partition.trace.symb.tree;


public class GoalTreePair<T> {
   private final GoalTree<T> pside;
   private final GoalTree<T> qside;

   private GoalTreePair(final GoalTree<T> pside, final GoalTree<T> qside) {
      this.pside = pside;
      this.qside = qside;
   }

   public static <T> GoalTreePair<T> goalTreePairWithPSide(final GoalTree<T> pside) {
      return new GoalTreePair<T>(pside, null);
   }

   public static <T> GoalTreePair<T> goalTreePairWithQSide(final GoalTree<T> qside) {
      return new GoalTreePair<T>(null, qside);
   }

   public boolean hasQSide() {
      return qside != null;
   }

   public boolean hasPSide() {
      return pside != null;
   }
}
