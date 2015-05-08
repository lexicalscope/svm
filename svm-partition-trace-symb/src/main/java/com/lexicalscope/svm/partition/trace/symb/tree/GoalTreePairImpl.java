package com.lexicalscope.svm.partition.trace.symb.tree;

import static com.lexicalscope.svm.j.instruction.symbolic.pc.PcBuilder.and;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.svm.search.Randomiser;
import com.lexicalscope.svm.vm.j.JState;

public class GoalTreePairImpl<T, S> implements GoalTreePair<T, S> {
   final GoalTree<T, JState> pside;
   final GoalTree<T, JState> qside;

   public GoalTreePairImpl(final GoalTree<T, JState> pside, final GoalTree<T, JState> qside) {
      this.pside = pside;
      this.qside = qside;
   }

   @Override public boolean isOpen() {
      return psideIsOpen() || qsideIsOpen();
   }

   @Override public boolean qsideIsOpen() {
      return qside.isOpen();
   }

   @Override public boolean psideIsOpen() {
      return pside.isOpen();
   }

   @Override public boolean covers(final BoolSymbol pc) {
      return pside.covers(pc) || qside.covers(pc);
   }

   @Override public BoolSymbol pc() {
      // TODO[tim]: is this right? what does it mean?
      return and(pside.pc(), qside.pc());
   }

   @Override
   public JState openPNode(final Randomiser randomiser) {
      return pside.randomOpenNode(randomiser);
   }

   @Override
   public JState openQNode(final Randomiser randomiser) {
      return qside.randomOpenNode(randomiser);
   }

   @Override public GoalTree<T, JState> pside() {
      return pside;
   }

   @Override public GoalTree<T, JState> qside() {
      return qside;
   }

   public static <T, S> GoalTreePair<T, JState> pair(
         final GoalTree<T, JState> pside,
         final GoalTree<T, JState> qside) {
      return new GoalTreePairImpl<>(pside, qside);
   }

   @Override public void expandP(final JState[] states) {
      for (final JState s : states) {
         pside.increaseOpenNodes(s);
      }
   }

   @Override public void expandQ(final JState[] states) {
      for (final JState s : states) {
         qside.increaseOpenNodes(s);
      }
   }

   @Override public String toString() {
      return String.format("(pair %s %s)", pside, qside);
   }
}