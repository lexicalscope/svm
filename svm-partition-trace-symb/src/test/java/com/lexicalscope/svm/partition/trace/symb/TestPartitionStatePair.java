package com.lexicalscope.svm.partition.trace.symb;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.svm.vm.j.State;

public class TestPartitionStatePair {
   @Rule public final JUnitRuleMockery context = new JUnitRuleMockery();

   private final PartitionStatePairs pairs = new PartitionStatePairs();

   @Mock State pstate0;
   @Mock State qstate0;

   @Mock State pstate1;
   @Mock State qstate1;

   @Mock State pstate2;
   @Mock State qstate2;

   @Before public void initialState() {
      pairs.put(pstate0, qstate0);
   }

   @Test public void initalStatesCorrespond() throws Exception {
      assertThat(pairs.qpending(), equalTo(qstate0));
      assertThat("no pending pairs", !pairs.hasPending());
   }

   @Test public void executionToBranchInUnaffectedProduces1to1Correspondence() throws Exception {
      assertThat(pairs.ppending(), equalTo(pstate0));
      pairs.pexecution(pstate1, pstate2);

      assertThat(pairs.qpending(), equalTo(qstate0));
      pairs.qexecution(qstate1, qstate2);

      assertThat(pairs.ppending(), equalTo(pstate1));
      assertThat(pairs.qpending(), equalTo(qstate1));

      pairs.backtrack();

      assertThat(pairs.ppending(), equalTo(pstate2));
      assertThat(pairs.qpending(), equalTo(qstate2));
   }
}

