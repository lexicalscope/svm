package com.lexicalscope.svm.partition.trace.symb.search;

import static com.lexicalscope.MatchersAdditional.has;
import static com.lexicalscope.svm.j.instruction.symbolic.pc.PcBuilder.truth;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.lexicalscope.svm.partition.trace.symb.tree.GoalTreeCorrespondence;
import com.lexicalscope.svm.partition.trace.symb.tree.GoalTreePair;
import com.lexicalscope.svm.search.GoalExtractor;
import com.lexicalscope.svm.search.GoalTreeGuidedSearch;
import com.lexicalscope.svm.search.Randomiser;

public class TestGoalTreeGuidedSearch {
   @Rule public final ExpectedException exception = ExpectedException.none();
   @Rule public final JUnitRuleMockery context = new JUnitRuleMockery();
   @Mock private GoalTreeCorrespondence<Object, FakeVmState> correspondence;
   @Mock private Randomiser randomiser;
   @Mock private GoalTreePair<Object, FakeVmState> pair;
   @Mock private GoalExtractor<Object, FakeVmState> goalExtractor;

   final FakeVmState pstate = new FakeVmState("p");
   final FakeVmState pstate1 = new FakeVmState("p1");
   final FakeVmState pstate2 = new FakeVmState("p2");

   final FakeVmState qstate = new FakeVmState("q");
   final FakeVmState qstate1 = new FakeVmState("q1");
   final FakeVmState qstate2 = new FakeVmState("q2");

   GoalTreeGuidedSearch<Object, FakeVmState> searchStrategy;

   @Before public void createStrategy() {
      searchStrategy = new GoalTreeGuidedSearch<Object, FakeVmState>(
            correspondence,
            goalExtractor,
            randomiser);
   }

   @Test public void searchPThenQ() throws Exception {
//      final GoalTreePair<Object, FakeVmState> pair = pair(goalTree(pstate), goalTree(qstate));

      context.checking(new Expectations(){{
         oneOf(correspondence).randomOpenChild(randomiser); will(returnValue(pair));

         oneOf(pair).psideIsOpen(); will(returnValue(true));
         oneOf(pair).openPNode(randomiser); will(returnValue(pstate));

         oneOf(pair).qsideIsOpen(); will(returnValue(true));
         oneOf(pair).openQNode(randomiser); will(returnValue(qstate));
      }});
      assertThat(searchStrategy.pendingState(), equalTo(pstate));
      searchStrategy.reachedLeaf();
      assertThat(searchStrategy.pendingState(), equalTo(qstate));
   }

   @Test public void firstInitialStateIsTakenAsPSecondAsQSubsequentRejected() throws Exception {
      final FakeVmState spuriousstate = new FakeVmState("spurious");

      context.checking(new Expectations(){{
         oneOf(correspondence).pInitial(pstate);
         oneOf(correspondence).qInitial(qstate);
      }});

      searchStrategy.consider(pstate);
      searchStrategy.consider(qstate);

      exception.expectMessage("only 2 initial states can be considered");
      searchStrategy.consider(spuriousstate);
   }

   @Test public void forkExtendsOpenNodesOfSideBeingSearched() throws Exception {
      context.checking(new Expectations(){{
         oneOf(correspondence).randomOpenChild(randomiser); will(returnValue(pair));
         oneOf(pair).isOpen(); will(returnValue(true));
         oneOf(pair).psideIsOpen(); will(returnValue(true));
         oneOf(pair).openPNode(randomiser); will(returnValue(pstate));
      }});

      assertThat(searchStrategy.pendingState(), equalTo(pstate));

      context.checking(new Expectations(){{
         oneOf(pair).expandP(new FakeVmState[]{pstate1, pstate2});
         oneOf(pair).isOpen(); will(returnValue(true));
         oneOf(pair).qsideIsOpen(); will(returnValue(true));
         oneOf(pair).openQNode(randomiser); will(returnValue(qstate));
      }});
      searchStrategy.fork(new FakeVmState[]{pstate1, pstate2});
      assertThat(searchStrategy.pendingState(), equalTo(qstate));

      context.checking(new Expectations(){{
         oneOf(pair).expandQ(new FakeVmState[]{qstate1, qstate2});

         oneOf(pair).isOpen(); will(returnValue(true));
         oneOf(correspondence).stillOpen(pair);

         oneOf(correspondence).hasOpenChildren(); will(returnValue(true));
         oneOf(correspondence).randomOpenChild(randomiser); will(returnValue(pair));
         oneOf(pair).psideIsOpen(); will(returnValue(true));
         oneOf(pair).openPNode(randomiser); will(returnValue(pstate));
      }});

      searchStrategy.fork(new FakeVmState[]{qstate1, qstate2});
   }

   @Test public void resultCreatedAtLeaf() throws Exception {
      context.checking(new Expectations(){{
         oneOf(correspondence).randomOpenChild(randomiser); will(returnValue(pair));
         oneOf(pair).psideIsOpen(); will(returnValue(true));
         oneOf(pair).openPNode(randomiser); will(returnValue(pstate));

         oneOf(pair).qsideIsOpen(); will(returnValue(true));
         oneOf(pair).openQNode(randomiser); will(returnValue(qstate));
      }});

      assertThat(searchStrategy.pendingState(), equalTo(pstate));
      searchStrategy.reachedLeaf();
      assertThat(searchStrategy.firstResult(), equalTo(pstate));
      assertThat(searchStrategy.results(), has(equalTo(pstate)).only().inOrder());
   }

   @Test public void goalIsNotifiedToCorrespondence() throws Exception {
      final Object goal = new Object();

      context.checking(new Expectations(){{
         oneOf(correspondence).randomOpenChild(randomiser); will(returnValue(pair));
         oneOf(pair).psideIsOpen(); will(returnValue(true));
         oneOf(pair).openPNode(randomiser); will(returnValue(pstate));
      }});
      assertThat(searchStrategy.pendingState(), equalTo(pstate));

      context.checking(new Expectations(){{
         oneOf(correspondence).reachedP(pair, goal, pstate, truth());
         oneOf(goalExtractor).goal(pstate); will(returnValue(goal));
         oneOf(goalExtractor).pc(pstate); will(returnValue(truth()));

         oneOf(pair).qsideIsOpen(); will(returnValue(true));
         oneOf(pair).openQNode(randomiser); will(returnValue(qstate));
      }});

      searchStrategy.goal();
      assertThat(searchStrategy.pendingState(), equalTo(qstate));

      context.checking(new Expectations(){{
         oneOf(correspondence).reachedQ(pair, goal, qstate, truth());
         oneOf(goalExtractor).goal(qstate); will(returnValue(goal));
         oneOf(goalExtractor).pc(qstate); will(returnValue(truth()));

         oneOf(pair).isOpen(); will(returnValue(true));
         oneOf(correspondence).stillOpen(pair);
         oneOf(correspondence).hasOpenChildren(); will(returnValue(true));
         oneOf(correspondence).randomOpenChild(randomiser); will(returnValue(pair));
         oneOf(pair).psideIsOpen(); will(returnValue(true));
         oneOf(pair).openPNode(randomiser); will(returnValue(pstate));
      }});
      searchStrategy.goal();
   }
}

