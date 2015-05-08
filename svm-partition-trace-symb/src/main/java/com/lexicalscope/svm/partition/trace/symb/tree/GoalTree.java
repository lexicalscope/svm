package com.lexicalscope.svm.partition.trace.symb.tree;

import static com.google.common.base.Joiner.on;
import static com.lexicalscope.svm.j.instruction.symbolic.pc.PcBuilder.and;
import static java.lang.String.format;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matcher;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.FalseSymbol;
import com.lexicalscope.svm.partition.trace.Trace;
import com.lexicalscope.svm.partition.trace.symb.tree.GoalMap.SubtreeFactory;
import com.lexicalscope.svm.search.Randomiser;
import com.lexicalscope.svm.vm.j.JState;
import com.lexicalscope.svm.z3.FeasibilityChecker;

public final class GoalTree<T,S> implements InputSubset {
   private final GoalMap<Trace, GoalTree<Trace, JState>> children;
   private final OpenNodes<S> openNodes;
   private final FeasibilityChecker feasibilityChecker;
   private final SubtreeFactory<GoalTree<Trace, JState>> childFactory;
   private BoolSymbol coveredPc;
   private BoolSymbol childrenCoverPc;

    public GoalTree(final GoalMapFactory<Trace> goalMapFactory) {
      this(goalMapFactory, new FeasibilityChecker());
   }

   public GoalTree(final GoalMapFactory<Trace> goalMapFactory, final FeasibilityChecker feasibilityChecker) {
      this.feasibilityChecker = feasibilityChecker;
      this.coveredPc = new FalseSymbol();
      this.childrenCoverPc = new FalseSymbol();
      this.openNodes = new OpenNodes<>(feasibilityChecker);
      this.children = goalMapFactory.newGoalMap();
      this.childFactory = new SubtreeFactory<GoalTree<Trace, JState>>() {
         @Override public GoalTree<Trace, JState> create() {
            return new GoalTree<Trace, JState>(goalMapFactory, feasibilityChecker);
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

   public GoalTree<Trace, JState> reached(final Trace goal, final JState state, final BoolSymbol childPc) {
      childrenCoverPc = childrenCoverPc.or(childPc);

      final GoalTree<Trace, JState> child = children.get(goal, childFactory);
      child.increaseCovers(childPc);
      child.increaseOpenNodes(state);
      return child;
   }

   public S randomOpenNode(final Randomiser randomiser) {
      return openNodes.random(randomiser);
   }

   public boolean hasReached(final Trace goal) {
      return children.containsGoal(goal);
   }

   public void increaseOpenNodes(final S state) {
      openNodes.push(state);
   }

   public boolean hasChild(final Matcher<? super GoalTree<Trace, JState>> childMatcher) {
      return children.containsMatching(childMatcher);
   }

   public boolean isChildForGoal(final GoalTree<Trace, JState> child, final Trace goal) {
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

   public List<GoalTree<Trace, JState>> coveredChilden(final BoolSymbol pc) {
      final List<GoalTree<Trace, JState>> result = new ArrayList<>();
      for (final GoalTree<Trace, JState> child : children) {
         if(child.covers(pc)) {
            result.add(child);
         }
      }
      return result;
   }

   public List<GoalTree<Trace, JState>> overlappingChildGoals(final BoolSymbol pc) {
      final List<GoalTree<Trace, JState>> result = new ArrayList<>();
      for (final GoalTree<Trace, JState> child : children) {
         if(child.overlaps(pc)) {
            result.add(child);
         }
      }
      return result;
   }

   public GoalTree<Trace, JState> childForGoal(final Trace goal) {
      return children.get(goal);
   }

   @Override public String toString() {
      return format("(node (covers %s) (children cover %s) %s %s)",
               coveredPc,
               childrenCoverPc,
               openNodes,
               on(" ").join(children));
   }

//   public static <S> GoalTree<Object, S> goalTree(final S initial) {
//      final GoalTree<Object, S> result = new GoalTree<Object, S>(new ObjectGoalMapFactory());
//      result.increaseOpenNodes(initial);
//      return result;
//   }
}
