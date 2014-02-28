package com.lexicalscope.svm.partition.trace.symb.tree;

import static com.lexicalscope.svm.j.instruction.symbolic.pc.PcBuilder.and;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;

class GoalTreePair<T, S> implements InputSubset {
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
}