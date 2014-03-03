package com.lexicalscope.svm.partition.trace.symb.tree;

import java.util.List;

import org.hamcrest.Matcher;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.svm.search.Randomiser;
import com.lexicalscope.svm.z3.FeasibilityChecker;


public final class GoalTreeCorrespondenceImpl<T, S> implements GoalTreeCorrespondence<T, S> {
   // should be flat not tree
   // map should map to a pair, each trace should reach one GoalTree in each side
   // this also makes it easier to find a place to search from...
   private final GoalMap<T, GoalTreePair<T, S>> children;
   private final GoalTree<T, S> pside;
   private final GoalTree<T, S> qside;

   private GoalTreeCorrespondenceImpl(
         final T rootGoal,
         final GoalTree<T, S> pside,
         final GoalTree<T, S> qside,
         final GoalMapFactory<T> goalMapFactory) {
      this.pside = pside;
      this.qside = qside;
      children = goalMapFactory.newGoalMap();
      children.put(rootGoal, new GoalTreePair<T, S>(pside, qside));
   }

   @Override
   public boolean hasChildren() {
      return !children.isEmpty();
   }

   @Override
   public boolean isOpen() {
      // TODO[tim]: very expensive method
      for (final GoalTreePair<T, S> child : children) {
         if(child.pside.isOpen() || child.qside.isOpen()) {
            return true;
         }
      }
      return false;
   }

   @Override
   public GoalTreePair<T, S> reachedP(final GoalTreePair<T, S> parent, final T goal, final S state, final BoolSymbol childPc) {
      return reached(goal, state, childPc, new PqChildFactory(), parent.pside, parent.qside);
   }

   @Override
   public GoalTreePair<T, S> reachedQ(final GoalTreePair<T, S> parent, final T goal, final S state, final BoolSymbol childPc) {
      return reached(goal, state, childPc, new QpChildFactory(), parent.qside, parent.pside);
   }

   private GoalTreePair<T, S> reached(
         final T goal,
         final S state,
         final BoolSymbol childPc,
         final ChildFactory childFactory,
         final GoalTree<T, S> thisSide,
         final GoalTree<T, S> otherSide) {

      final boolean reachedBefore = thisSide.hasReached(goal);

      final GoalTree<T, S> thisSideChild = thisSide.reached(goal, state, childPc);
      assert thisSide.overlappingChildGoals(childPc).size() == 1;

      final List<GoalTree<T, S>> otherSideOverlappingChildGoals =
            otherSide.overlappingChildGoals(childPc);
      if(otherSideOverlappingChildGoals.size() > 1||
            otherSideOverlappingChildGoals.size() == 1 &&
            !otherSide.isChildForGoal(otherSideOverlappingChildGoals.get(0), goal)) {
         throw new RuntimeException("unbounded");
      }

      if(!reachedBefore && otherSide.hasReached(goal)){
         final GoalTreePair<T, S> result = childFactory.create(thisSideChild, otherSide.childForGoal(goal));
         children.put(goal, result);
         return result;
      }
      return null;
   }

   private interface ChildFactory {
      <T, S> GoalTreePair<T, S> create(GoalTree<T, S> thisSide, GoalTree<T, S> otherSide);
   }

   private static class PqChildFactory implements ChildFactory {
      @Override public <T, S> GoalTreePair<T, S> create(final GoalTree<T, S> thisSide, final GoalTree<T, S> otherSide) {
         return new GoalTreePair<T, S>(thisSide, otherSide);
      }
   }

   private static class QpChildFactory implements ChildFactory {
      @Override public <T, S> GoalTreePair<T, S> create(final GoalTree<T, S> thisSide, final GoalTree<T, S> otherSide) {
         return new GoalTreePair<T,S>(otherSide, thisSide);
      }
   }

   @Override public String toString() {
      return String.format("(correspondence %s)", children);
   }

   public static <T, S> GoalTreeCorrespondenceImpl<T, S> root(
         final T rootGoal,
         final FeasibilityChecker feasibilityChecker,
         final GoalMapFactory<T> goalMapFactory) {
      final GoalTree<T, S> pside = new GoalTree<>(goalMapFactory, feasibilityChecker);
      final GoalTree<T, S> qside = new GoalTree<>(goalMapFactory, feasibilityChecker);
      return new GoalTreeCorrespondenceImpl<>(rootGoal, pside, qside, goalMapFactory);
   }

   public static <T, S> GoalTreeCorrespondenceImpl<T, S> root(
         final T rootGoal,
         final S pstate0,
         final S qstate0,
         final FeasibilityChecker feasibilityChecker,
         final GoalMapFactory<T> goalMapFactory) {
      final GoalTreeCorrespondenceImpl<T, S> result =
            root(rootGoal, feasibilityChecker, goalMapFactory);

      result.pInitial(pstate0);
      result.qInitial(qstate0);

      return result;
   }

   private void pInitial(final S pstate0) {
      pside.increaseOpenNodes(pstate0);
   }

   private void qInitial(final S qstate0) {
      qside.increaseOpenNodes(qstate0);
   }

   @Override
   public boolean hasChild(final Matcher<? super GoalTreePair<T, S>> childMatcher) {
      return children.containsMatching(childMatcher);
   }

   @Override
   public int childCount() {
      return children.size();
   }

   @Override public GoalTreePair<T, S> correspondence(final T goal) {
      return children.get(goal);
   }

   @Override public GoalTreePair<T, S> randomOpenCorrespondence(final Randomiser randomiser) {
      return children.getRandom(randomiser);
   }
}
