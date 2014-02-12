package com.lexicalscope.svm.partition.trace.symb.search;

import static com.lexicalscope.svm.partition.trace.symb.search.FakeExecutionBuilder.from;

import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.svm.partition.trace.symb.PartitionStatePairs;
import com.lexicalscope.svm.vm.j.State;

public class TestPairedExecutionToGoal {
   @Rule public final JUnitRuleMockery context = new JUnitRuleMockery();

   private final PartitionStatePairs pairs = new PartitionStatePairs();


   @Mock State pstate0;
   @Mock State pstateL;
   @Mock State pstateLL;
   @Mock State pstateLR;
   @Mock State pstateR;
   @Mock State pstateRL;
   @Mock State pstateRR;

   @Mock State qstate0;
   @Mock State qstateL;
   @Mock State qstateLL;
   @Mock State qstateLR;
   @Mock State qstateR;
   @Mock State qstateRL;
   @Mock State qstateRR;

   @Before public void initialState() {
      pairs.put(pstate0, qstate0);
   }

   @Test public void pairedExecutionWIthNoGoalCorresponds() throws Exception {
      final FakeExecution pexecution = FakeExecutionBuilder.p(pstate0)
                                        .to(from(pstateL)
                                             .to(pstateLL, pstateLR),
                                            from(pstateR)
                                             .to(pstateRL, pstateRR)).build();

      final FakeExecution qexecution = from(qstate0)
                                        .to(from(qstateL)
                                             .to(qstateLL, qstateLR),
                                            from(qstateR)
                                             .to(qstateRL, qstateRR)).build();

      final PartitionStatePairs pairs = new PartitionStatePairs();

//      pexecution.execute(pairs);

   }
}

