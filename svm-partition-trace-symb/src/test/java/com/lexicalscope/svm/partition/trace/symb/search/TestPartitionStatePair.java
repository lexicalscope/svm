package com.lexicalscope.svm.partition.trace.symb.search;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.svm.partition.trace.symb.PartitionStatePairs;

public class TestPartitionStatePair {
   @Rule public final JUnitRuleMockery context = new JUnitRuleMockery();

   private final PartitionStatePairs<FakeState> pairs = new PartitionStatePairs<>();

   @Mock FakeState pstate0;
   @Mock FakeState qstate0;

   @Mock FakeState pstate1;
   @Mock FakeState qstate1;

   @Mock FakeState pstate2;
   @Mock FakeState qstate2;

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

