package com.lexicalscope.svm.partition.trace.symb;

import static java.util.Arrays.asList;

import java.util.List;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.lexicalscope.svm.vm.j.State;

public class PartitionStatePairs {
   private final ListMultimap<State, State> statePairs = ArrayListMultimap.create();
   private final ListMultimap<State, State> pleadsTo = ArrayListMultimap.create();

   private State pstate;
   private State qstate;

   public void put(final State pstate, final State ... qstate) {
      if(statePairs.isEmpty()) {
         this.pstate = pstate;
      }
      statePairs.putAll(pstate, asList(qstate));
   }

   public State ppending() {
      return pstate;
   }

   public State qpending() {
      final List<State> candidates = statePairs.get(pstate);
      final State result = candidates.remove(0);
      assert !(candidates.isEmpty() && statePairs.containsKey(pstate));
      qstate = result;
      return result;
   }

   public boolean hasPending() {
      return !statePairs.isEmpty();
   }

   public void pexecution(final State pstate1, final State pstate2) {
      pleadsTo.put(pstate, pstate1);
      pleadsTo.put(pstate, pstate2);

   }

   public void qexecution(final State qstate1, final State qstate2) {
      final List<State> pleadTo = pleadsTo.get(pstate);

      final State pstate1 = pleadTo.get(0);
      final State pstate2 = pleadTo.get(1);
      pleadTo.clear();

      statePairs.put(pstate1, qstate1);
      statePairs.put(pstate2, qstate2);

      // always left (DFS)
      pstate = pstate1;
   }

   public void backtrack() {
      pstate = statePairs.keySet().iterator().next();
   }
}
