package com.lexicalscope.svm.partition.trace.symb.tree;

import static com.lexicalscope.svm.j.instruction.symbolic.pc.PcBuilder.truth;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hamcrest.Matcher;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.svm.search.ListRandomPool;
import com.lexicalscope.svm.search.Randomiser;
import com.lexicalscope.svm.vm.j.JState;
import com.lexicalscope.svm.z3.FeasibilityChecker;


public final class GoalTreeCorrespondenceImpl<T, S> implements GoalTreeCorrespondence<T, S> {
   // should be flat not tree
   // map should map to a pair, each trace should reach one GoalTree in each side
   // this also makes it easier to find a place to search from...
   private final GoalMap<T, GoalTreePair<T, JState>> children;
   private final ListRandomPool<GoalTreePair<T, JState>> openChildren;
   private final GoalTree<T, JState> pside;
   private final GoalTree<T, JState> qside;

   private GoalTreeCorrespondenceImpl(
         final T rootGoal,
         final GoalTree<T, JState> pside,
         final GoalTree<T, JState> qside,
         final GoalMapFactory<T> goalMapFactory) {
      this.pside = pside;
      this.qside = qside;
      children = goalMapFactory.newGoalMap();
      openChildren = new ListRandomPool<GoalTreePair<T, JState>>();
      put(rootGoal, new GoalTreePairImpl<T, JState>(pside, qside));
   }

   private void put(final T goal, final GoalTreePair<T, JState> node) {
      children.put(goal, node);
      openChildren.add(node);
   }

   @Override public void stillOpen(final GoalTreePair<T, JState> node) {
      openChildren.add(node);
   }

   @Override public GoalTreePair<T, JState> randomOpenChild(final Randomiser randomiser) {
      return openChildren.randomElement(randomiser);
   }

   @Override public boolean hasOpenChildren() {
      return !openChildren.isEmpty();
   }

   @Override public boolean hasChildren() {
      return !children.isEmpty();
   }

   @Override
   public boolean isOpen() {
      for (final GoalTreePair<T, JState> child : children) {
         if(child.isOpen()) {
            return true;
         }
      }
      return false;
   }

   @Override
   public GoalTreePair<T, JState> reachedP(
         final GoalTreePair<T, JState> parent,
         final T goal,
         final JState state,
         final BoolSymbol childPc) {
      return reached(goal, state, childPc, new PqChildFactory(), parent.pside(), parent.qside());
   }

   @Override
   public GoalTreePair<T, JState> reachedQ(
         final GoalTreePair<T, JState> parent,
         final T goal,
         final JState state,
         final BoolSymbol childPc) {
      return reached(goal, state, childPc, new QpChildFactory(), parent.qside(), parent.pside());
   }

   private GoalTreePair<T, JState> reached(
         final T goal,
         final JState state,
         final BoolSymbol childPc,
         final ChildFactory childFactory,
         final GoalTree<T, JState> thisSide,
         final GoalTree<T, JState> otherSide) {
      final boolean reachedBefore = thisSide.hasReached(goal);

      final GoalTree<T, JState> thisSideChild = thisSide.reached(goal, state, childPc);
      assert thisSide.overlappingChildGoals(childPc).size() == 1 : this;

      final List<GoalTree<T, JState>> otherSideOverlappingChildGoals =
            otherSide.overlappingChildGoals(childPc);
      if(otherSideOverlappingChildGoals.size() > 1||
            otherSideOverlappingChildGoals.size() == 1 &&
            !otherSide.isChildForGoal(otherSideOverlappingChildGoals.get(0), goal)) {
         if(otherSideOverlappingChildGoals.size() == 1) {
            throw new RuntimeException(String.format("unbounded%n\t%s%n\t%s", goal, otherSideOverlappingChildGoals.get(0)));
         } else {
            throw new RuntimeException(String.format("unbounded%n\t%s%n\t%s", goal, otherSideOverlappingChildGoals));
         }

      }

      if(!reachedBefore && otherSide.hasReached(goal)){
         final GoalTreePair<T, JState> result = childFactory.create(thisSideChild, otherSide.childForGoal(goal));
         put(goal, result);
         return result;
      }
      return null;
   }

   private interface ChildFactory {
      <T, S> GoalTreePair<T, JState> create(GoalTree<T, JState> thisSide, GoalTree<T, JState> otherSide);
   }

   private static class PqChildFactory implements ChildFactory {
      @Override public <T, S> GoalTreePair<T, JState> create(
            final GoalTree<T, JState> thisSide,
            final GoalTree<T, JState> otherSide) {
         return new GoalTreePairImpl<T, JState>(thisSide, otherSide);
      }
   }

   private static class QpChildFactory implements ChildFactory {
      @Override public <T, S> GoalTreePair<T, JState> create(final GoalTree<T, JState> thisSide, final GoalTree<T, JState> otherSide) {
         return new GoalTreePairImpl<T, JState>(otherSide, thisSide);
      }
   }

   @Override public String toString() {
      return String.format("(correspondence %s)", children);
   }

   @Override public void pInitial(final JState pstate0) {
      pside.increaseOpenNodes(pstate0);
   }

   @Override public void qInitial(final JState qstate0) {
      qside.increaseOpenNodes(qstate0);
   }

   @Override public boolean hasChild(final Matcher<? super GoalTreePair<T, JState>> childMatcher) {
      return children.containsMatching(childMatcher);
   }

   @Override
   public int childCount() {
      return children.size();
   }

   @Override public GoalTreePair<T, JState> correspondence(final T goal) {
      return children.get(goal);
   }

   @Override public Iterator<GoalTreePair<T, JState>> iterator() {
      return children.iterator();
   }

   @Override public Collection<GoalTreePair<T, JState>> children() {
      return children.values();
   }

   public static <T, S> GoalTreeCorrespondence<T, JState> root(
         final T rootGoal,
         final FeasibilityChecker feasibilityChecker,
         final GoalMapFactory<T> goalMapFactory,
         final Class<S> bindGenerics) {
      return root(rootGoal, feasibilityChecker, goalMapFactory);
   }

   public static <T, S> GoalTreeCorrespondence<T, JState> root(
         final T rootGoal,
         final FeasibilityChecker feasibilityChecker,
         final GoalMapFactory<T> goalMapFactory) {
      final GoalTree<T, JState> pside = new GoalTree<>(goalMapFactory, feasibilityChecker);
      final GoalTree<T, JState> qside = new GoalTree<>(goalMapFactory, feasibilityChecker);
      pside.covers(truth());
      qside.covers(truth());
      return new GoalTreeCorrespondenceImpl<>(rootGoal, pside, qside, goalMapFactory);
   }

   public static <T, S> GoalTreeCorrespondence<T, JState> root(
         final T rootGoal,
         final JState pstate0,
         final JState qstate0,
         final FeasibilityChecker feasibilityChecker,
         final GoalMapFactory<T> goalMapFactory) {
      final GoalTreeCorrespondence<T, JState> result =
            root(rootGoal, feasibilityChecker, goalMapFactory);

      result.pInitial(pstate0);
      result.qInitial(qstate0);

      return result;
   }
}
