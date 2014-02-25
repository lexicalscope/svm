package com.lexicalscope.svm.partition.trace.symb.tree;

import static com.lexicalscope.svm.j.instruction.symbolic.pc.PcBuilder.and;

import java.util.List;

import org.hamcrest.Matcher;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.svm.z3.FeasibilityChecker;


public final class GoalTreeCorrespondence<T, S> implements InputSubset {
   private final GoalTree<T, S> pside;
   private final GoalTree<T, S> qside;
   private final GoalMap<T, GoalTreeCorrespondence<T, S>> children;
   private final GoalMapFactory<T> goalMapFactory;

   private GoalTreeCorrespondence(
         final GoalTree<T, S> pside,
         final GoalTree<T, S> qside,
         final GoalMapFactory<T> goalMapFactory) {
      this.pside = pside;
      this.qside = qside;
      this.goalMapFactory = goalMapFactory;
      children = goalMapFactory.newGoalMap();
   }

   public boolean hasChildren() {
      return !children.isEmpty();
   }

   public boolean isOpen() {
      return pside.isOpen() || qside.isOpen();
   }

   public void reachedP(final T goal, final S state, final BoolSymbol childPc) {
      reached(goal, state, childPc, new PqChildFactory(), pside, qside);
   }

   public void reachedQ(final T goal, final S state, final BoolSymbol childPc) {
      reached(goal, state, childPc, new QpChildFactory(), qside, pside);
   }

   private void reached(
         final T goal,
         final S state,
         final BoolSymbol childPc,
         final ChildFactory childFactory,
         final GoalTree<T, S> thisSide,
         final GoalTree<T, S> otherSide) {

      final GoalTree<T, S> thisSideChild = thisSide.reached(goal, state, childPc);
      if(thisSide.overlappingChildGoals(childPc).size() > 1) {
         throw new RuntimeException("unbounded");
      }

      final List<GoalTree<T, S>> otherSideOverlappingChildGoals = otherSide.overlappingChildGoals(childPc);
      if(otherSideOverlappingChildGoals.size() > 1) {
         throw new RuntimeException("unbounded");
      } else if(otherSideOverlappingChildGoals.size() == 1 &&
         !otherSide.isChildForGoal(otherSideOverlappingChildGoals.get(0), goal)) {
         throw new RuntimeException("unbounded");
      }

      if(otherSide.hasReached(goal)){
          children.put(goal, childFactory.create(thisSideChild, otherSide.childForGoal(goal), goalMapFactory));
      }
   }

   private interface ChildFactory {
      <T, S> GoalTreeCorrespondence<T, S> create(GoalTree<T, S> thisSide, GoalTree<T, S> otherSide, GoalMapFactory<T> goalMapFactory);
   }

   private static class PqChildFactory implements ChildFactory {
      @Override public <T, S> GoalTreeCorrespondence<T, S> create(final GoalTree<T, S> thisSide, final GoalTree<T, S> otherSide, final GoalMapFactory<T> goalMapFactory) {
         return new GoalTreeCorrespondence<T,S>(thisSide, otherSide, goalMapFactory);
      }
   }

   private static class QpChildFactory implements ChildFactory {
      @Override public <T, S> GoalTreeCorrespondence<T, S> create(final GoalTree<T, S> thisSide, final GoalTree<T, S> otherSide, final GoalMapFactory<T> goalMapFactory) {
         return new GoalTreeCorrespondence<T,S>(otherSide, thisSide, goalMapFactory);
      }
   }

   @Override public String toString() {
      return String.format("(correspondence %s %s)", pside, qside);
   }

   public static <T, S> GoalTreeCorrespondence<T, S> root(
         final S pstate0,
         final S qstate0,
         final FeasibilityChecker feasibilityChecker,
         final GoalMapFactory<T> goalMapFactory) {
      final GoalTree<T, S> pside = new GoalTree<>(goalMapFactory, feasibilityChecker);
      final GoalTree<T, S> qside = new GoalTree<>(goalMapFactory, feasibilityChecker);

      pside.increaseOpenNodes(pstate0);
      qside.increaseOpenNodes(qstate0);

      return new GoalTreeCorrespondence<>(pside, qside, goalMapFactory);
   }

   @Override public boolean covers(final BoolSymbol pc) {
      return pside.covers(pc) || qside.covers(pc);
   }

   @Override public BoolSymbol pc() {
      return and(pside.pc(), qside.pc());
   }

   public boolean hasChild(final Matcher<? super GoalTreeCorrespondence<T, S>> childMatcher) {
      return children.containsMatching(childMatcher);
   }

   public int childCount() {
      return children.size();
   }
}
