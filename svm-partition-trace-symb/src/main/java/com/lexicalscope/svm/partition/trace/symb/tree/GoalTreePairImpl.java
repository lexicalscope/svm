package com.lexicalscope.svm.partition.trace.symb.tree;

import static com.lexicalscope.svm.j.instruction.symbolic.pc.PcBuilder.and;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.svm.search.Randomiser;

public class GoalTreePairImpl<T, S> implements GoalTreePair<T, S> {
   final GoalTree<T, S> pside;
   final GoalTree<T, S> qside;

   public GoalTreePairImpl(final GoalTree<T, S> pside, final GoalTree<T, S> qside) {
      this.pside = pside;
      this.qside = qside;
   }

   @Override public boolean isOpen() {
      return pside.isOpen() || qside.isOpen();
   }

   @Override public boolean covers(final BoolSymbol pc) {
      return pside.covers(pc) || qside.covers(pc);
   }

   @Override public BoolSymbol pc() {
      // TODO[tim]: is this right? what does it mean?
      return and(pside.pc(), qside.pc());
   }

   @Override
   public S openPNode(final Randomiser randomiser) {
      return pside.openNode(randomiser);
   }

   @Override
   public S openQNode(final Randomiser randomiser) {
      return qside.openNode(randomiser);
   }

   @Override public GoalTree<T, S> pside() {
      return pside;
   }

   @Override public GoalTree<T, S> qside() {
      return qside;
   }

   public static <T, S> GoalTreePair<T, S> pair(
         final GoalTree<T, S> pside,
         final GoalTree<T, S> qside) {
      return new GoalTreePairImpl<>(pside, qside);
   }

   @Override public void expandP(final S[] states) {
      for (final S s : states) {
         pside.increaseOpenNodes(s);
      }
   }

   @Override public void expandQ(final S[] states) {
      for (final S s : states) {
         qside.increaseOpenNodes(s);
      }
   }

   @Override public void reachedGoalP(final T goal, final S state, final BoolSymbol pc) {
      pside.reached(goal, state, pc);
   }

   @Override public void reachedGoalQ(final T goal, final S state, final BoolSymbol pc) {
      qside.reached(goal, state, pc);
   }
}