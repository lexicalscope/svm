package com.lexicalscope.svm.partition.trace.symb;

import java.util.List;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

public class PartitionStatePairs<S> {
   private final ListMultimap<S, S> statePairs = ArrayListMultimap.create();
   private final ListMultimap<S, S> pleadsTo = ArrayListMultimap.create();

   private S pstate;
   private S qstate;

   public final void put(final S pstate, final S qstate) {
      if(statePairs.isEmpty()) {
         this.pstate = pstate;
      }
      statePairs.put(pstate, qstate);
   }

   public S ppending() {
      return pstate;
   }

   public S qpending() {
      final List<S> candidates = statePairs.get(pstate);
      final S result = candidates.remove(0);
      assert !(candidates.isEmpty() && statePairs.containsKey(pstate));
      qstate = result;
      return result;
   }

   public boolean hasPending() {
      return !statePairs.isEmpty();
   }

   public void pexecution(final S pstate1, final S pstate2) {
      pleadsTo.put(pstate, pstate1);
      pleadsTo.put(pstate, pstate2);

   }

   public void qexecution(final S qstate1, final S qstate2) {
      final List<S> pleadTo = pleadsTo.get(pstate);

      final S pstate1 = pleadTo.get(0);
      final S pstate2 = pleadTo.get(1);
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
