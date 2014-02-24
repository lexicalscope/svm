package com.lexicalscope.svm.partition.trace.symb.tree;

import static com.lexicalscope.svm.j.instruction.symbolic.pc.PcBuilder.and;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matcher;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.svm.z3.FeasibilityChecker;


public class GoalTreeCorrespondence<T, S> implements InputSubset {
   private final GoalTree<T, S> pside;
   private final GoalTree<T, S> qside;
//   private final Map<T, GoalTreeCorrespondence<T, S>> children = new HashMap<>();
   private final List<GoalTreeCorrespondence<T, S>> children = new ArrayList<>();

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

   public void reachedP(final T goal, final S state, final BoolSymbol childPc) {
      final GoalTree<T, S> psideChild = pside.reached(goal, state, childPc);
      if(qside.childrenCover(childPc)){
          final GoalTree<T, S> qsideChild = qside.coveredChild(childPc);
          children.add(new GoalTreeCorrespondence<T,S>(psideChild, qsideChild));
      }
   }

   public void reachedQ(final T goal, final S state, final BoolSymbol childPc) {
      final GoalTree<T, S> qsideChild = qside.reached(goal, state, childPc);
      if(qside.childrenCover(childPc)){
          final GoalTree<T, S> psideChild = pside.coveredChild(childPc);
          children.add(new GoalTreeCorrespondence<T,S>(psideChild, qsideChild));
      }
   }

   @Override public String toString() {
      return String.format("(correspondence %s %s)", pside, qside);
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

   @Override public boolean covers(final BoolSymbol pc) {
      return pside.covers(pc) || qside.covers(pc);
   }

   @Override public BoolSymbol pc() {
      return and(pside.pc(), qside.pc());
   }

   public boolean hasChild(final Matcher<? super GoalTreeCorrespondence<T, S>> childMatcher) {
      for (final GoalTreeCorrespondence<T, S> child : children) {
         if(childMatcher.matches(child)) {
            return true;
         }
      }
      return false;
   }
}
