package com.lexicalscope.svm.partition.trace.symb.tree;

import static com.lexicalscope.svm.partition.trace.symb.SymbolicTraceMatchers.equivalentTo;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.hamcrest.Matcher;

import com.lexicalscope.svm.partition.trace.Trace;
import com.lexicalscope.svm.z3.FeasibilityChecker;

public class TraceGoalMap<N> extends AbstractGoalMap<Trace, N> {
   private final Map<Trace, N> children = new LinkedHashMap<>();
   private final FeasibilityChecker checker;

   public TraceGoalMap(final FeasibilityChecker checker) {
      this.checker = checker;
   }

   @Override public void put(final Trace goal, final N node) {
      children.put(goal, node);
   }

   @Override public N get(final Trace goal) {
      // TODO[tim]: we already know the trace prefixes match - try to take advantage of that
      final Matcher<Trace> equivalentTo = equivalentTo(checker, goal);
      for (final Entry<Trace, N> child : children.entrySet()) {
         if(equivalentTo.matches(child.getKey())) {
            return child.getValue();
         }
      }
      return null;
   }

   @Override public N get(final Trace goal, final com.lexicalscope.svm.partition.trace.symb.tree.GoalMap.SubtreeFactory<N> factory) {
      N result = get(goal);
      if(result == null) {
         result = factory.create();
         put(goal, result);
      }
      return result;
   }

   @Override public boolean containsGoal(final Trace goal) {
      return get(goal) != null;
   }

   @Override public int size() {
      return children.size();
   }

   @Override public boolean isEmpty() {
      return children.isEmpty();
   }

   @Override public Iterator<N> iterator() {
      return children.values().iterator();
   }

   @Override public String toString() {
      return String.format("(GoalMap %s)", children);
   }
}
