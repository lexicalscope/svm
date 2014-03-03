package com.lexicalscope.svm.partition.trace.symb.tree;

import static com.lexicalscope.svm.j.instruction.symbolic.pc.PcBuilder.and;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.svm.search.Randomiser;

public class GoalTreePair<T, S> implements InputSubset {
   final GoalTree<T, S> pside;
   final GoalTree<T, S> qside;

   public GoalTreePair(final GoalTree<T, S> pside, final GoalTree<T, S> qside) {
      this.pside = pside;
      this.qside = qside;
   }

   @Override
   public boolean covers(final BoolSymbol pc) {
      return pside.covers(pc) || qside.covers(pc);
   }

   @Override
   public BoolSymbol pc() {
      // TODO[tim]: is this right? what does it mean?
      return and(pside.pc(), qside.pc());
   }

   public S openPNode(final Randomiser randomiser) {
      return pside.openNode(randomiser);
   }

   public S openQNode(final Randomiser randomiser) {
      return qside.openNode(randomiser);
   }

   public static <T, S> GoalTreePair<T, S> pair(
         final GoalTree<T, S> pside,
         final GoalTree<T, S> qside) {
      return new GoalTreePair<>(pside, qside);
   }
}