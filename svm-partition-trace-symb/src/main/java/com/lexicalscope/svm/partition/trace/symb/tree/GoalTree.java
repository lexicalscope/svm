package com.lexicalscope.svm.partition.trace.symb.tree;

import static com.google.common.base.Joiner.on;
import static com.lexicalscope.svm.j.instruction.symbolic.pc.PcBuilder.and;
import static java.lang.String.format;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matcher;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.FalseSymbol;
import com.lexicalscope.svm.partition.trace.symb.tree.GoalMap.SubtreeFactory;
import com.lexicalscope.svm.z3.FeasibilityChecker;

public final class GoalTree<T, S> implements InputSubset {
   private final GoalMap<T, GoalTree<T, S>> children;
   private final OpenNodes<S> openNodes;
   private final FeasibilityChecker feasibilityChecker;
   private final SubtreeFactory<GoalTree<T, S>> childFactory;
   private BoolSymbol coveredPc;
   private BoolSymbol childrenCoverPc;

   public GoalTree(final GoalMapFactory<T> goalMapFactory) {
      this(goalMapFactory, new FeasibilityChecker());
   }

   public GoalTree(final GoalMapFactory<T> goalMapFactory, final FeasibilityChecker feasibilityChecker) {
      this.feasibilityChecker = feasibilityChecker;
      this.coveredPc = new FalseSymbol();
      this.childrenCoverPc = new FalseSymbol();
      this.openNodes = new OpenNodes<>(feasibilityChecker);
      this.children = goalMapFactory.newGoalMap();
      this.childFactory = new SubtreeFactory<GoalTree<T,S>>() {
         @Override public GoalTree<T, S> create() {
            return new GoalTree<T, S>(goalMapFactory, feasibilityChecker);
         }
      };
   }

   @Override
   public BoolSymbol pc() {
      return coveredPc;
   }

   @Override
   public boolean covers(final BoolSymbol pc) {
      return feasibilityChecker.implies(pc, coveredPc);
   }

   private boolean overlaps(final BoolSymbol pc) {
      return feasibilityChecker.satisfiable(and(pc, coveredPc));
   }

   public BoolSymbol covers() {
      return coveredPc;
   }

   private void increaseCovers(final BoolSymbol disjunct) {
      coveredPc = coveredPc.or(disjunct);
   }

   public GoalTree<T, S> reached(final T goal, final S state, final BoolSymbol childPc) {
      childrenCoverPc = childrenCoverPc.or(childPc);

      final GoalTree<T, S> child = children.get(goal, childFactory);
//      if(children.containsKey(goal)) {
//         child = children.get(goal);
//      } else {
//         child = new GoalTree<T, S>(feasibilityChecker);
//         children.put(goal, child);
//      }
      child.increaseCovers(childPc);
      child.increaseOpenNodes(state);

      return child;
   }

   public boolean hasReached(final T goal) {
      return children.containsGoal(goal);
   }

   public void increaseOpenNodes(final S state) {
      openNodes.push(state);
   }

   public boolean hasChild(final Matcher<? super GoalTree<T, S>> childMatcher) {
      return children.containsMatching(childMatcher);
   }

   public boolean isChildForGoal(final GoalTree<T, S> child, final T goal) {
      return children.isChildForGoal(child, goal);
   }

   public boolean hasOpenNode(final Matcher<S> openNodeMatcher) {
      return openNodes.hasMatching(openNodeMatcher);
   }

   public boolean childrenCover(final BoolSymbol pc) {
      return feasibilityChecker.implies(pc, childrenCoverPc);
   }

   public boolean isOpen() {
      return !openNodes.isEmpty();
   }

//   public GoalTree<T, S> coveredChild(final BoolSymbol pc) {
//      for (final GoalTree<T, S> child : children) {
//         if(child.covers(pc)) {
//            return child;
//         }
//      }
//      throw new IllegalStateException("no child covers " + pc);
//   }

   public List<GoalTree<T, S>> coveredChilden(final BoolSymbol pc) {
      final List<GoalTree<T, S>> result = new ArrayList<>();
      for (final GoalTree<T, S> child : children) {
         if(child.covers(pc)) {
            result.add(child);
         }
      }
      return result;
   }

   public List<GoalTree<T, S>> overlappingChildGoals(final BoolSymbol pc) {
      final List<GoalTree<T, S>> result = new ArrayList<>();
      for (final GoalTree<T, S> child : children) {
         if(child.overlaps(pc)) {
            result.add(child);
         }
      }
      return result;
   }

   public GoalTree<T, S> childForGoal(final T goal) {
      return children.get(goal);
   }

   @Override public String toString() {
      return format("(node (covers %s) (children cover %s) %s %s)",
               coveredPc,
               childrenCoverPc,
               openNodes,
               on(" ").join(children));
   }
}
