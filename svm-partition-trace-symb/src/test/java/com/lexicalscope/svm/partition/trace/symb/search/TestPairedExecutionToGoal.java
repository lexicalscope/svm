package com.lexicalscope.svm.partition.trace.symb.search;

import static com.lexicalscope.svm.partition.trace.symb.search.fakes.FakeExecutionBuilder.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.lexicalscope.svm.partition.trace.symb.PartitionStatePairs;
import com.lexicalscope.svm.partition.trace.symb.search.fakes.ExecutionStrategy;
import com.lexicalscope.svm.partition.trace.symb.search.fakes.FakeState;
import com.lexicalscope.svm.partition.trace.symb.search.fakes.PStrategy;
import com.lexicalscope.svm.partition.trace.symb.search.fakes.QStrategy;

public class TestPairedExecutionToGoal {
   private final PartitionStatePairs<FakeState> pairs = new PartitionStatePairs<>();
   private final ExecutionStrategy pvm = new PStrategy(pairs);
   private final ExecutionStrategy qvm = new QStrategy(pairs);


   @Test public void pairedExecutionWithNoGoalCorresponds() throws Exception {
      final FakeState pexecution = branch(branch(term("P1"), term("P2")),
                                          branch(term("P3"), term("P4")));

      final FakeState qexecution = branch(branch(term("Q1"), term("Q2")),
                                          branch(term("Q3"), term("Q4")));

      pairs.initial(pexecution, qexecution);

      final Map<FakeState, FakeState> result = new HashMap<>();
      while(pairs.hasPending()) {
         result.put(pairs.ppending().execute(pvm),
                    pairs.qpending().execute(qvm));
      }

      assertThat(result, hasEntry(term("P1"), term("Q1")));
      assertThat(result, hasEntry(term("P2"), term("Q2")));
      assertThat(result, hasEntry(term("P3"), term("Q3")));
      assertThat(result, hasEntry(term("P4"), term("Q4")));
   }

   @Test public void pairedExecutionCorrespondsAtGoal() throws Exception {
      final FakeState pexecution = branch(branch(term("P1"), term("P2")),
                                          goal("P3", term("P4")));

      final FakeState qexecution = branch(branch(term("Q1"), term("Q2")),
                                          goal("Q3", term("Q4")));

      pairs.initial(pexecution, qexecution);

      final Map<FakeState, FakeState> result = new HashMap<>();
      while(pairs.hasPending()) {
         result.put(pairs.ppending().execute(pvm),
                    pairs.qpending().execute(qvm));
      }

      assertThat(result, hasEntry(term("P1"), term("Q1")));
      assertThat(result, hasEntry(term("P2"), term("Q2")));
      assertThat(result, hasEntry(goalCalled("P3"), goalCalled("Q3")));
      assertThat(result, hasEntry(term("P4"), term("Q4")));
   }
}

